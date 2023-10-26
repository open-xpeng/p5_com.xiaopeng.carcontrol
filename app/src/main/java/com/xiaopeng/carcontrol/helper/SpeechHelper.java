package com.xiaopeng.carcontrol.helper;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import com.xiaopeng.carcontrol.util.ContextUtils;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class SpeechHelper {
    private final String TAG;
    private TextToSpeech.OnInitListener mOnInitListener;
    private TextToSpeech mTextToSpeech;
    private TtsPlayListener mTtsPlayListener;
    private UtteranceProgressListener mUtteranceProgressListener;

    /* loaded from: classes2.dex */
    public interface TtsPlayListener {
        void onTtsPlayEnd(String tts);
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        static SpeechHelper sInstance = new SpeechHelper();

        private SingletonHolder() {
        }
    }

    public static SpeechHelper getInstance() {
        return SingletonHolder.sInstance;
    }

    private SpeechHelper() {
        this.TAG = "SpeechHelper";
        this.mOnInitListener = new TextToSpeech.OnInitListener() { // from class: com.xiaopeng.carcontrol.helper.SpeechHelper.1
            @Override // android.speech.tts.TextToSpeech.OnInitListener
            public void onInit(int status) {
                if (status == 0) {
                    LogUtils.d("SpeechHelper", "TextToSpeech init Success", false);
                    SpeechHelper.this.mTextToSpeech.setOnUtteranceProgressListener(SpeechHelper.this.mUtteranceProgressListener);
                    return;
                }
                LogUtils.e("SpeechHelper", "TextToSpeech init error", false);
            }
        };
        this.mUtteranceProgressListener = new UtteranceProgressListener() { // from class: com.xiaopeng.carcontrol.helper.SpeechHelper.2
            @Override // android.speech.tts.UtteranceProgressListener
            public void onStart(String utteranceId) {
                LogUtils.d("SpeechHelper", "onStart: " + utteranceId, false);
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public void onDone(String utteranceId) {
                LogUtils.d("SpeechHelper", "onDone: " + utteranceId, false);
                if (SpeechHelper.this.mTtsPlayListener != null) {
                    SpeechHelper.this.mTtsPlayListener.onTtsPlayEnd(utteranceId);
                }
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public void onError(String utteranceId) {
                LogUtils.d("SpeechHelper", "onError: " + utteranceId, false);
            }
        };
    }

    public void initSpeechService() {
        this.mTextToSpeech = new TextToSpeech(ContextUtils.getContext(), this.mOnInitListener);
    }

    public String speak(String text) {
        return speak(text, true);
    }

    public String speak(String text, boolean flush) {
        this.mTextToSpeech.speak(text, 0, null, text);
        return text;
    }

    public void stop(String utteranceId) {
        this.mTextToSpeech.stop();
        this.mTtsPlayListener = null;
    }

    public void stop() {
        this.mTextToSpeech.stop();
        this.mTtsPlayListener = null;
    }

    public void release() {
        this.mTextToSpeech.shutdown();
    }

    public void setTtsPlayListener(TtsPlayListener ttsPlayListener) {
        this.mTtsPlayListener = ttsPlayListener;
    }
}
