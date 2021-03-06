package com.edinstudio.android.baseframework.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by albert on 15-5-20.
 */
public class TTSHelper implements TextToSpeech.OnInitListener {
    private static TTSHelper instance;
    private static TextToSpeech mTextToSpeech;
    private static Context mContext;
    private List<String> mBufferedMessages;
    private boolean mIsReady;

    private TTSHelper() {
        mBufferedMessages = new ArrayList<>();
    }

    public static TTSHelper getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            instance = new TTSHelper();
        }

        if (mTextToSpeech == null) {
            mTextToSpeech = new TextToSpeech(context, instance);
        }

        return instance;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mTextToSpeech.setLanguage(Locale.CHINESE);
            mIsReady = true;
            for (String msg : mBufferedMessages) {
                speakText(msg);
            }
            mBufferedMessages.clear();
        }
    }

    public void notifyNewMsg(String msg) {
        if (mIsReady) {
            speakText(msg);
        } else {
            mBufferedMessages.add(msg);
        }
    }

    public String[] getEngines() {
        List<TextToSpeech.EngineInfo> engineInfoList = mTextToSpeech.getEngines();
        String[] engines = new String[engineInfoList.size()];
        for (int i = 0; i < engineInfoList.size(); i++) {
            engines[i] = engineInfoList.get(i).name;
        }

        return engines;
    }

    public void setEngine(String engineName) {
        mTextToSpeech = new TextToSpeech(mContext, instance, engineName);
        mIsReady = false;
    }

    public void release() {
        mTextToSpeech.shutdown();
        mTextToSpeech = null;
        mIsReady = false;
    }

    private void speakText(String msg) {
        HashMap<String, String> params = new HashMap<>();
        params.put(TextToSpeech.Engine.KEY_PARAM_STREAM, "STREAM_NOTIFICATION");
        mTextToSpeech.speak(msg, TextToSpeech.QUEUE_ADD, params);
        mTextToSpeech.playSilence(100, TextToSpeech.QUEUE_ADD, params);
    }
}
