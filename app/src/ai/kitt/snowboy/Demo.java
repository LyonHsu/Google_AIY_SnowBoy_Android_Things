package ai.kitt.snowboy;

import ai.kitt.snowboy.GoogleAIY.BoardDefaults;
import ai.kitt.snowboy.GoogleAIY.EmbeddedAssistant;
import ai.kitt.snowboy.Volume.VolumeDialog;
import ai.kitt.snowboy.audio.RecordingThread;
import ai.kitt.snowboy.audio.PlaybackThread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.google.android.things.contrib.driver.voicehat.Max98357A;
import com.google.android.things.contrib.driver.voicehat.VoiceHat;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.google.assistant.embedded.v1alpha2.SpeechRecognitionResult;
import com.google.auth.oauth2.UserCredentials;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ai.kitt.snowboy.audio.AudioDataSaver;
import ai.kitt.snowboy.demo.R;



public class Demo extends Activity implements com.google.android.things.contrib.driver.button.Button.OnButtonEventListener ,VolumeDialog.VolumeAdjustListener {

    String TAG = Demo.class.getSimpleName();
    private Button record_button;
    private Button play_button;
    private TextView log;
    private ScrollView logView;
    static String strLog = null;

    private int preVolume = -1;
    private static long activeTimes = 0;

    private RecordingThread recordingThread;
    private PlaybackThread playbackThread;

    // Peripheral and drivers constants.
    private static final int BUTTON_DEBOUNCE_DELAY_MS = 20;
    // Default on using the Voice Hat on Raspberry Pi 3.
    private static final boolean USE_VOICEHAT_I2S_DAC = Build.DEVICE.equals(BoardDefaults.DEVICE_RPI3);

    // Audio constants.
    private static final String PREF_CURRENT_VOLUME = "current_volume";
    private static final int SAMPLE_RATE = 16000;
    private static final int DEFAULT_VOLUME = 100;

    // Assistant SDK constants.
    private static final String DEVICE_MODEL_ID = "PLACEHOLDER";
    private static final String DEVICE_INSTANCE_ID = "PLACEHOLDER";
    private static final String LANGUAGE_CODE = "en-US";

    // Hardware peripherals.
    private com.google.android.things.contrib.driver.button.Button mButton;
    private android.widget.Button mButtonWidget;
    private Gpio mLed;
    private Max98357A mDac;

    private Handler mMainHandler;

    // List & adapter to store and display the history of Assistant Requests.
    private EmbeddedAssistant mEmbeddedAssistant;
    private ArrayList<String> mAssistantRequests = new ArrayList<>();
    private ArrayAdapter<String> mAssistantRequestsAdapter;
    private CheckBox mHtmlOutputCheckbox;
    private WebView mWebView;

    private TextToSpeech textToSpeech;
    private TextToSpeech textToSpeechEng;
    /* Keyword we are looking for to activate menu */
    private static final String KEYPHRASE = "ok google";

    //volume
    private VolumeDialog dialog;
    private AudioManager mAudioMgr;

    String openS = "Hi Baby 你好寶貝 ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        setUI();
        
        setProperVolume();

        AppResCopy.copyResFromAssetsToSD(this);
        
        activeTimes = 0;
        recordingThread = new RecordingThread(handle, new AudioDataSaver());
        playbackThread = new PlaybackThread();

        if(record_button!=null){
            record_button.callOnClick();
        }

        setGoogleAIY();
    }
    
    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


//====================Snowboy=========================================================================
    private void setUI() {
        record_button = (Button) findViewById(R.id.btn_test1);
        record_button.setOnClickListener(record_button_handle);
        record_button.setEnabled(true);
        
        play_button = (Button) findViewById(R.id.btn_test2);
        play_button.setOnClickListener(play_button_handle);
        play_button.setEnabled(true);

        log = (TextView)findViewById(R.id.log);
        logView = (ScrollView)findViewById(R.id.logView);

        TextView versionTxt = (TextView)findViewById(R.id.versionTxt);
        versionTxt.setText("ver:"+Utils.getAppVersionName(this)+" "+Utils.getAppVersion(this));
    }

    
    private void setMaxVolume() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        preVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> preVolume = "+preVolume, "green");
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> maxVolume = "+maxVolume, "green");
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> currentVolume = "+currentVolume, "green");
    }
    
    private void setProperVolume() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        preVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> preVolume = "+preVolume, "green");
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> maxVolume = "+maxVolume, "green");
        int properVolume = (int) ((float) maxVolume * 0.2); 
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, properVolume, 0);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> currentVolume = "+currentVolume, "green");
    }
    
    private void restoreVolume() {
        if(preVolume>=0) {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, preVolume, 0);
            updateLog(" ----> set preVolume = "+preVolume, "green");
            int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            updateLog(" ----> currentVolume = "+currentVolume, "green");
        }
    }

    private void startRecording() {
        recordingThread.startRecording();
        updateLog(" ----> recording started ...", "green");
        record_button.setText(R.string.btn1_stop);
    }

    private void stopRecording() {
        recordingThread.stopRecording();
        updateLog(" ----> recording stopped ", "green");
        record_button.setText(R.string.btn1_start);
    }

    private void startPlayback() {
        updateLog(" ----> playback started ...", "green");
        play_button.setText(R.string.btn2_stop);
        // (new PcmPlayer()).playPCM();
        playbackThread.startPlayback();
    }

    private void stopPlayback() {
        updateLog(" ----> playback stopped ", "green");
        play_button.setText(R.string.btn2_start);
        playbackThread.stopPlayback();
    }

    private void sleep() {
        try { Thread.sleep(500);
        } catch (Exception e) {}
    }
    
    private OnClickListener record_button_handle = new OnClickListener() {
        // @Override
        public void onClick(View arg0) {
            if(record_button.getText().equals(getResources().getString(R.string.btn1_start))) {
                stopPlayback();
                sleep();
                startRecording();
            } else {
                stopRecording();
                sleep();
            }
        }
    };
    
    private OnClickListener play_button_handle = new OnClickListener() {
        // @Override
        public void onClick(View arg0) {
            if (play_button.getText().equals(getResources().getString(R.string.btn2_start))) {
                stopRecording();
                sleep();
                startPlayback();
            } else {
                stopPlayback();
                mEmbeddedAssistant.destroy();
            }
        }
    };
     
    public Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MsgEnum message = MsgEnum.getMsgEnum(msg.what);
            switch(message) {
                case MSG_ACTIVE:
                    activeTimes++;
                    updateLog(" ----> Detected " + activeTimes + " times", "green");
                    // Toast.makeText(Demo.this, "Active "+activeTimes, Toast.LENGTH_SHORT).show();
                    showToast("Active "+activeTimes);
                    while (true){
                        if(recordingThread.getStatus()!=AudioRecord.RECORDSTATE_RECORDING){
                            stopRecording();
                            buttonEventClick(true);
                            break;
                        }
                    }
                    break;
                case MSG_INFO:
                    updateLog(" ----> "+message);
                    break;
                case MSG_VAD_SPEECH:
                    updateLog(" ----> normal voice", "blue");
                    break;
                case MSG_VAD_NOSPEECH:
                    updateLog(" ----> no speech", "blue");
                    break;
                case MSG_ERROR:
                    updateLog(" ----> " + msg.toString(), "red");
                    break;
                default:
                    super.handleMessage(msg);
                    break;
             }
        }
    };

     public void updateLog(final String text) {

         log.post(new Runnable() {
             @Override
             public void run() {
                 if (currLogLineNum >= MAX_LOG_LINE_NUM) {
                     int st = strLog.indexOf("<br>");
                     strLog = strLog.substring(st+4);
                 } else {
                     currLogLineNum++;
                 }
                 String str = "<font color='white'>"+text+"</font>"+"<br>";
                 strLog = (strLog == null || strLog.length() == 0) ? str : strLog + str;
                 log.setText(Html.fromHtml(strLog));
             }
        });
        logView.post(new Runnable() {
            @Override
            public void run() {
                logView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    static int MAX_LOG_LINE_NUM = 200;
    static int currLogLineNum = 0;

    public void updateLog(final String text, final String color) {
        log.post(new Runnable() {
            @Override
            public void run() {
                if(currLogLineNum>=MAX_LOG_LINE_NUM) {
                    int st = strLog.indexOf("<br>");
                    strLog = strLog.substring(st+4);
                } else {
                    currLogLineNum++;
                }
                String str = "<font color='"+color+"'>"+text+"</font>"+"<br>";
                strLog = (strLog == null || strLog.length() == 0) ? str : strLog + str;
                log.setText(Html.fromHtml(strLog));
            }
        });
        logView.post(new Runnable() {
            @Override
            public void run() {
                logView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void emptyLog() {
        strLog = null;
        log.setText("");
    }

    @Override
     public void onDestroy() {
         restoreVolume();
         recordingThread.stopRecording();
         super.onDestroy();
     }

//==============Google AIY assistant===============================================================================

    private void setGoogleAIY(){
        //set text to speech
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.d(TAG, "TTS init status:" + status);
                if (status != TextToSpeech.ERROR) {
                    LyonTextToSpeech(textToSpeech,openS);

                    getLocalIpAddress(Demo.this);
                }
            }
        });

        final ListView assistantRequestsListView = findViewById(R.id.assistantRequestsListView);
        mAssistantRequestsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                        mAssistantRequests);
        assistantRequestsListView.setAdapter(mAssistantRequestsAdapter);
        mHtmlOutputCheckbox = findViewById(R.id.htmlOutput);
        mHtmlOutputCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean useHtml) {
                mWebView.setVisibility(useHtml ? View.VISIBLE : View.GONE);
                assistantRequestsListView.setVisibility(useHtml ? View.GONE : View.VISIBLE);
                mEmbeddedAssistant.setResponseFormat(useHtml
                        ? EmbeddedAssistant.HTML : EmbeddedAssistant.TEXT);
            }
        });
        mWebView = findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mMainHandler = new Handler(getMainLooper());
        mButtonWidget = findViewById(R.id.assistantQueryButton);
        mButtonWidget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEmbeddedAssistant!=null)
                    mEmbeddedAssistant.startConversation();
            }
        });


        // Audio routing configuration: use default routing.
        AudioDeviceInfo audioInputDevice = null;
        AudioDeviceInfo audioOutputDevice = null;
        if (USE_VOICEHAT_I2S_DAC) {
            audioInputDevice = findAudioDevice(AudioManager.GET_DEVICES_INPUTS, AudioDeviceInfo.TYPE_BUS);
            if (audioInputDevice == null) {
                Log.e(TAG, "failed to find I2S audio input device, using default");
            }
            audioOutputDevice = findAudioDevice(AudioManager.GET_DEVICES_OUTPUTS, AudioDeviceInfo.TYPE_BUS);
            if (audioOutputDevice == null) {
                Log.e(TAG, "failed to found I2S audio output device, using default");
            }
        }

        try {
            if (USE_VOICEHAT_I2S_DAC) {
                Log.i(TAG, "initializing DAC trigger");
                mDac = VoiceHat.openDac();
                mDac.setSdMode(Max98357A.SD_MODE_SHUTDOWN);

                mButton = VoiceHat.openButton();
                mLed = VoiceHat.openLed();
            } else {
                PeripheralManager pioManager = PeripheralManager.getInstance();
                mButton = new com.google.android.things.contrib.driver.button.Button(BoardDefaults.getGPIOForButton(),
                        com.google.android.things.contrib.driver.button.Button.LogicState.PRESSED_WHEN_LOW);
                mLed = pioManager.openGpio(BoardDefaults.getGPIOForLED());
            }

            mButton.setDebounceDelay(BUTTON_DEBOUNCE_DELAY_MS);
            mButton.setOnButtonEventListener(this);

            mLed.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mLed.setActiveType(Gpio.ACTIVE_HIGH);
        } catch (IOException e) {
            Log.e(TAG, "error configuring peripherals:", e);
            return;
        } catch (NoClassDefFoundError e){
            Log.d(TAG,"Exception:"+Utils.FormatStackTrace(e));
        }

        if(mLed!=null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<3;i++){
                        try {
                            mLed.setValue(true);
                            Thread.sleep(500);
                            mLed.setValue(false);
                            Thread.sleep(500);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }  catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }




        // Set volume from preferences
        final String TAG = Demo.class.getSimpleName()+"_mEmbeddedAssistant";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int initVolume = 70;//preferences.getInt(PREF_CURRENT_VOLUME, DEFAULT_VOLUME);
        Log.i(TAG, "setting audio track volume to: " + initVolume);

        UserCredentials userCredentials = null;
        try {
            userCredentials =
                    EmbeddedAssistant.generateCredentials(this, R.raw.credentials);
        } catch (IOException | JSONException e) {
            Log.e(TAG, "error getting user credentials", e);
        }

        if(userCredentials==null){
            Log.e(TAG,"credentials userCredentials==null EmbeddedAssistant build fail!");
            Toast.makeText(this,"this Google Action Credentials is null",Toast.LENGTH_SHORT);
        }else {
            Log.e(TAG,"credentials ="+userCredentials);
            mEmbeddedAssistant = new EmbeddedAssistant.Builder()
                    .setCredentials(userCredentials)
                    .setDeviceInstanceId(DEVICE_INSTANCE_ID)
                    .setDeviceModelId(DEVICE_MODEL_ID)
                    .setLanguageCode(LANGUAGE_CODE)
                    .setAudioInputDevice(audioInputDevice)
                    .setAudioOutputDevice(audioOutputDevice)
                    .setAudioSampleRate(SAMPLE_RATE)
                    .setAudioVolume(initVolume)
                    .setRequestCallback(new EmbeddedAssistant.RequestCallback() {
                        @Override
                        public void onRequestStart() {
                            Log.i(TAG, "starting assistant request, enable microphones");
                            mButtonWidget.setText(R.string.button_listening);
                            mButtonWidget.setEnabled(false);
                        }

                        @Override
                        public void onSpeechRecognition(List<SpeechRecognitionResult> results) {
                            for (final SpeechRecognitionResult result : results) {
                                Log.i(TAG, "assistant request text: " + result.getTranscript() +
                                        " stability: " + Float.toString(result.getStability()));
                                mAssistantRequestsAdapter.add(result.getTranscript()+" stability: " + Float.toString(result.getStability()));
                            }

                            if(results.size()>0)
                                mAssistantRequestsAdapter.add(results.get(0).getTranscript());
                        }
                    })
                    .setConversationCallback(new EmbeddedAssistant.ConversationCallback() {
                        @Override
                        public void onResponseStarted() {
                            super.onResponseStarted();
                            // When bus type is switched, the AudioManager needs to reset the stream volume
                            if (mDac != null) {
                                try {
                                    mDac.setSdMode(Max98357A.SD_MODE_LEFT);
                                } catch (IOException e) {
                                    Log.e(TAG, "error enabling DAC", e);
                                }
                            }
                        }

                        @Override
                        public void onResponseFinished() {
                            super.onResponseFinished();
                            if (mDac != null) {
                                try {
                                    mDac.setSdMode(Max98357A.SD_MODE_SHUTDOWN);
                                } catch (IOException e) {
                                    Log.e(TAG, "error disabling DAC", e);
                                }
                            }
                            if (mLed != null) {
                                try {
                                    mLed.setValue(false);
                                } catch (IOException e) {
                                    Log.e(TAG, "cannot turn off LED", e);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Log.e(TAG, "assist error: " + throwable.getMessage(), throwable);
                        }

                        @Override
                        public void onVolumeChanged(int percentage) {
                            Log.i(TAG, "assistant volume changed: " + percentage);

                            int result = getTextToSpeech().speak("assistant volume changed:  " + percentage, TextToSpeech.QUEUE_FLUSH, null);
                            Log.d(TAG, "speak result:" + result);
                            // Update our shared preferences
                            SharedPreferences.Editor editor = PreferenceManager
                                    .getDefaultSharedPreferences(Demo.this)
                                    .edit();
                            editor.putInt(PREF_CURRENT_VOLUME, percentage);
                            editor.apply();
                        }

                        @Override
                        public void onConversationFinished() {
                            Log.i(TAG, "assistant conversation finished");
                            mButtonWidget.setText(R.string.button_new_request);
                            mButtonWidget.setEnabled(true);

                            while (true){
                                Log.i(TAG, "assistant onConversationFinished finished snowboy :"+mEmbeddedAssistant.getAudioRecordStatus());
                                if(mEmbeddedAssistant.getAudioRecordStatus()!=AudioRecord.RECORDSTATE_RECORDING){

                                    Log.i(TAG, "assistant conversation finished snowboy startRecording");
//                                    stopPlayback();
                                    sleep();
                                    startRecording();
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onAssistantResponse(final String response) {
                            if (!response.isEmpty()) {
                                mMainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAssistantRequestsAdapter.add("Google Assistant: " + response);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onAssistantDisplayOut(final String html) {
                            mMainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // Need to convert to base64
                                    try {
                                        final byte[] data = html.getBytes("UTF-8");
                                        final String base64String =
                                                Base64.encodeToString(data, Base64.DEFAULT);
                                        mWebView.loadData(base64String, "text/html; charset=utf-8",
                                                "base64");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        public void onDeviceAction(String intentName, JSONObject parameters) {
                            if (parameters != null) {
                                Log.d(TAG, "Get device action " + intentName + " with parameters: " +
                                        parameters.toString());
                            } else {
                                Log.d(TAG, "Get device action " + intentName + " with no paramete"
                                        + "rs");
                            }
                            if (intentName.equals("action.devices.commands.OnOff")) {
                                try {
                                    boolean turnOn = parameters.getBoolean("on");
                                    if(mLed!=null)
                                        mLed.setValue(turnOn);
                                } catch (JSONException e) {
                                    Log.e(TAG, "Cannot get value of command", e);
                                } catch (IOException e) {
                                    Log.e(TAG, "Cannot set value of LED", e);
                                }
                            }
                        }
                    })
                    .build();
            mEmbeddedAssistant.connect();


        }
    }

    private AudioDeviceInfo findAudioDevice(int deviceFlag, int deviceType) {
        AudioManager manager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        AudioDeviceInfo[] adis = manager.getDevices(deviceFlag);
        for (AudioDeviceInfo adi : adis) {
            if (adi.getType() == deviceType) {
                Log.d(TAG,"findAudioDevice:"+adi.toString());
                return adi;
            }
        }
        return null;
    }

    @Override
    public void onVolumeAdjust(int volume) {
        if(volume>100)
            volume=100;
        if(volume<0)
            volume=0;
        LyonTextToSpeech(getTextToSpeech(),"噹");

        Log.d(TAG,"调节后的音乐音量大小为：" + volume);
    }

    @Override
    public void onButtonEvent(com.google.android.things.contrib.driver.button.Button button, boolean pressed) {
        buttonEventClick(pressed);
    }

    private void buttonEventClick( boolean pressed){
        try {
            if (mLed != null) {
                String onOff="off";
                if(pressed)
                    onOff="on";
                Log.v(TAG,"Led is "+onOff);
                mLed.setValue(pressed);
            }
        } catch (IOException e) {
            Log.d(TAG, "error toggling LED:", e);
        }
        if (pressed) {
            mAssistantRequestsAdapter.clear();
            LyonTextToSpeech(textToSpeech,"Ouch!");
            mEmbeddedAssistant.startConversation();
        }
    }

    public TextToSpeech getTextToSpeech(){
        //set text to speech
        if(textToSpeech==null) {
            textToSpeech= new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    Log.d(TAG, "TTS init status:" + status);
                    if (status != TextToSpeech.ERROR) {
//                        int result = textToSpeech.setLanguage(Locale.getDefault());//Locale.);
                        int result = textToSpeech.setLanguage(Locale.TAIWAN);

                        Log.d(TAG, "speak result:" + result);
                    }
                }
            });
        }
        return textToSpeech;
    }

    public void LyonTextToSpeech(TextToSpeech textToSpeech, String sss){
        ArrayList<HashMap<String,String>> arrayList = getEngorChingString(sss);
        for(int i =0;i<arrayList.size();i++){
            int result=-1;
            if(arrayList.get(i).get("isEng").equals("true")){
                result =textToSpeech.setLanguage(Locale.ENGLISH);
            }else{
                textToSpeech.setLanguage(Locale.TAIWAN);
            }
            if(i==0){
                textToSpeech.speak( arrayList.get(i).get("word") , TextToSpeech.QUEUE_FLUSH, null);
            }else
                textToSpeech.speak( arrayList.get(i).get("word") , TextToSpeech.QUEUE_ADD, null);
            Log.d(TAG, arrayList.get(i).get("word")+" speak result:" + result);
        }
    }

    private ArrayList<HashMap<String,String>> getEngorChingString(String s){
        Log.d(TAG,"20190605 string:"+s);
        HashMap<String,String> hashMap = new HashMap<>();
        ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
        char[] c = s.toCharArray();
        Log.d(TAG,"20190605 c size:"+c.length);
        String word="";
        boolean isEng=false;
        boolean isoldEng=false;
        for(int i=0;i<c.length;i++){
            String cc = c[i]+"";

            if( cc.matches("[a-zA-Z|\\.]*") )//a-zA-Z0-9
            {
                isEng=true;
            }
            else
            {
                isEng=false;
            }
            Log.d(TAG,"20190605 c:"+cc+" isEng:"+isEng+ " / "+isoldEng);

            if(isoldEng!=isEng){
                hashMap.put("word",word);
                hashMap.put("isEng",isoldEng+"");
                arrayList.add(hashMap);
                isoldEng=isEng;
                word="";
                hashMap = new HashMap<>();
            }
            word=word+cc;
            Log.d(TAG,"20190605 word:"+word);
        }
        hashMap.put("word",word);
        hashMap.put("isEng",isoldEng+"");
        arrayList.add(hashMap);

        for(int i=0;i<arrayList.size();i++){
            Log.d(TAG,"20190605 arrayList:"+arrayList.get(i).get("word")+" / "+arrayList.get(i).get("isEng"));
        }

        return arrayList;
    }

    @SuppressLint("WifiManagerLeak")
    public String getLocalIpAddress(Context context) {

        String ip =  "no connect wifi!";
        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        ip=String.format("%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));

        Log.e(TAG, "20190610 ***** IP="+ ip);
        String theSpeech="已經連結到"+wifiInf.getSSID()+",Ip="+ip.replace(".","點");
        Log.e(TAG, "20190610***** theSpeechIP="+ theSpeech);
        LyonTextToSpeech(textToSpeech,theSpeech);
        return "Wifi:"+ip+"\n ("+wifiInf.getSSID().toString()+") connected\n"+wifiInf.getBSSID();
    }
}
