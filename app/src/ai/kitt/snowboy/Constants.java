package ai.kitt.snowboy;
import java.io.File;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Environment;

public class Constants {
    public static final String ASSETS_RES_DIR = "snowboy";
    public static final String DEFAULT_WORK_SPACE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/snowboy/";
    public static final String ACTIVE_UMDL = "alexa.umdl";
    public static final String ACTIVE_RES = "common.res";
    public static final String SAVE_AUDIO = Constants.DEFAULT_WORK_SPACE + File.separatorChar + "recording.pcm";
    public static final int SAMPLE_RATE = 16000;

//    /*
//    /**默认声音**/
//    public static final int DEFAULT = 0;
//    /**麦克风声音*/
//    public static final int MIC = 1;
//    /**通话上行声音*/
//    public static final int VOICE_UPLINK = 2;
//    /**通话下行声音*/
//    public static final int VOICE_DOWNLINK = 3;
//    /**通话上下行声音*/
//    public static final int VOICE_CALL = 4;
//    /**根据摄像头转向选择麦克风*/
//    public static final int CAMCORDER = 5;
//    /**对麦克风声音进行声音识别，然后进行录制*/
//    public static final int VOICE_RECOGNITION = 6;
//    /**对麦克风中类似ip通话的交流声音进行识别，默认会开启回声消除和自动增益*/
//    public static final int VOICE_COMMUNICATION = 7;
//    /**录制系统内置声音*/
//    public static final int REMOTE_SUBMIX = 8;
    // 音频源：音频输入-麦克风
    public static final  int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;//MediaRecorder.AudioSource.VOICE_RECOGNITION
    // 音频通道 单声道
    public static final  int AUDIO_CHANNEL_IN = AudioFormat.CHANNEL_IN_MONO;
    public static final  int AUDIO_CHANNEL_OUT = AudioFormat.CHANNEL_OUT_MONO;
    // 音频格式：PCM编码
    public static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    // 采样率
    // 44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    // 采样频率一般共分为22.05KHz、44.1KHz、48KHz三个等级
    public static int mSampleRate=16000;
    public static final int frequency = 44100;
    // 录音状态
    public static int status = Status.STATUS_NO_READY;
}
