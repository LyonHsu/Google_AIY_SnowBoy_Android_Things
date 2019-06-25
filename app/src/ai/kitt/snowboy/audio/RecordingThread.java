package ai.kitt.snowboy.audio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import ai.kitt.snowboy.Constants;
import ai.kitt.snowboy.MsgEnum;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import ai.kitt.snowboy.SnowboyDetect;
import ai.kitt.snowboy.Utils;

public class RecordingThread {
    static { System.loadLibrary("snowboy-detect-android"); }

    private static final String TAG = RecordingThread.class.getSimpleName();

    private static final String ACTIVE_RES = Constants.ACTIVE_RES;
    private static final String ACTIVE_UMDL = Constants.ACTIVE_UMDL;
    
    private boolean shouldContinue;
    private AudioDataReceivedListener listener = null;
    private Handler handler = null;
    private Thread thread;
    
    private static String strEnvWorkSpace = Constants.DEFAULT_WORK_SPACE;
    private String activeModel = strEnvWorkSpace+ACTIVE_UMDL;    
    private String commonRes = strEnvWorkSpace+ACTIVE_RES;   
    
    private SnowboyDetect detector = new SnowboyDetect(commonRes, activeModel);
    private MediaPlayer player = new MediaPlayer();
    private AudioRecord mAudioRecord;
    public RecordingThread(Handler handler, AudioDataReceivedListener listener) {
        this.handler = handler;
        this.listener = listener;

        detector.SetSensitivity("0.6");
        detector.SetAudioGain(1);
        detector.ApplyFrontend(true);
        try {
            player.setDataSource(strEnvWorkSpace+"ding.wav");
            player.prepare();
        } catch (IOException e) {
            Log.e(TAG, "Playing ding sound error", e);
        }
    }

    private void sendMessage(MsgEnum what, Object obj){
        if (null != handler) {
            Message msg = handler.obtainMessage(what.ordinal(), obj);
            handler.sendMessage(msg);
        }
    }

    public void startRecording() {
        if (thread != null)
            return;

        shouldContinue = true;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                record();
            }
        });
        thread.start();
    }

    public void stopRecording() {
        if (thread == null)
            return;

        shouldContinue = false;
        thread = null;
    }

    public int getStatus(){
        if(mAudioRecord!=null){
            return mAudioRecord.getState();
        }
        return AudioRecord.STATE_UNINITIALIZED;
    }

    private void record() {
        Log.v(TAG, "snowboy Start");
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);

        // Buffer size in bytes: for 0.1 second of audio
        int bufferSize = (int)(Constants.SAMPLE_RATE * 0.1 * 2);
        if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
            bufferSize = Constants.SAMPLE_RATE * 2;
        }

        byte[] audioBuffer = new byte[bufferSize];
        AudioRecord mAudioRecord = new AudioRecord(
            MediaRecorder.AudioSource.VOICE_RECOGNITION,//MediaRecorder.AudioSource.DEFAULT //修改語音輸入位置
            Constants.SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize);



        AudioFormat mAudioInputFormat = new AudioFormat.Builder()
                .setChannelMask(Constants.AUDIO_CHANNEL)
                .setEncoding(Constants.audioEncoding)
                .setSampleRate(Constants.mSampleRate)
                .build();
        mAudioRecord = new AudioRecord.Builder()
                .setAudioSource(Constants.AUDIO_INPUT)
                .setAudioFormat(mAudioInputFormat)
                .setBufferSizeInBytes(bufferSize)
                .build();
//        int playBufSize = AudioTrack.getMinBufferSize(Constants.frequency,
//                Constants.AUDIO_CHANNEL, Constants.audioEncoding);
//        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, Constants.frequency,
//                Constants.AUDIO_CHANNEL, Constants.audioEncoding,
//                playBufSize, AudioTrack.MODE_STREAM);

        Log.d(TAG, "snowboy Audio Record State:"+mAudioRecord.getState());

        if (mAudioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e(TAG, "snowboy Audio Record can't initialize!");
            return;
        }
        mAudioRecord.startRecording();
        if (null != listener) {
            listener.start();
        }
        Log.v(TAG, "snowboy Start recording");

        long shortsRead = 0;
        detector.Reset();
        while (shouldContinue) {
            int resultLyon =mAudioRecord.read(audioBuffer, 0, audioBuffer.length);
//            try {
//                Log.d("snowboy : ", "Hotword Lyon " + Integer.toString(resultLyon) + " detected!");
//            }catch (Exception e){
//                Log.e(TAG,"Snowboy :  "+Utils.FormatStackTrace(e));
//            }
            if (null != listener) {
                listener.onAudioDataReceived(audioBuffer, audioBuffer.length);
            }

            // Converts to short array.
            short[] audioData = new short[audioBuffer.length / 2];
            ByteBuffer.wrap(audioBuffer).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(audioData);

            shortsRead += audioData.length;

            // Snowboy hotword detection.
            int result = detector.RunDetection(audioData, audioData.length);
//            try {
//                Log.i("Snowboy : ", "Hotword " + Integer.toString(result) + " detected!");
//            }catch (Exception e){
//                Log.e(TAG,"Snowboy :  "+Utils.FormatStackTrace(e));
//            }
            if (result == -2) {
                // post a higher CPU usage:
//                 sendMessage(MsgEnum.MSG_VAD_NOSPEECH, null);
            } else if (result == -1) {
                sendMessage(MsgEnum.MSG_ERROR, "Unknown Detection Error");
            } else if (result == 0) {
                // post a higher CPU usage:
                // sendMessage(MsgEnum.MSG_VAD_SPEECH, null);
            } else if (result > 0) {
                sendMessage(MsgEnum.MSG_ACTIVE, null);
                Log.i("Snowboy: ", "Hotword " + Integer.toString(result) + " detected!");
                shouldContinue=false;
                player.start();
            }
        }

        mAudioRecord.stop();
        mAudioRecord.release();

        if (null != listener) {
            listener.stop();
        }
        Log.v(TAG, String.format("Recording stopped. Samples read: %d", shortsRead));

    }
}
