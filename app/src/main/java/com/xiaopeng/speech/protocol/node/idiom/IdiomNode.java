package com.xiaopeng.speech.protocol.node.idiom;

import android.os.Binder;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.SpeechNode;

/* loaded from: classes2.dex */
public class IdiomNode extends SpeechNode<IIdiomListener> {
    private Binder mBinder = new Binder();

    public void setVadTimeout(long j) {
        SpeechClient.instance().getASREngine().setVadTimeout(j);
    }

    public void setVadPauseTime(long j) {
        SpeechClient.instance().getASREngine().setVadPauseTime(j);
    }

    public void resetVadTime() {
        SpeechClient.instance().getASREngine().setVadPauseTime(500L);
        SpeechClient.instance().getASREngine().setVadTimeout(7000L);
    }

    public void setSingleTTS(boolean z) {
        SpeechClient.instance().getTTSEngine().setSingleTTS(z, this.mBinder);
    }

    public String speakSingleTTS(String str) {
        return SpeechClient.instance().getTTSEngine().speak(str, 3);
    }

    public void shutupTTS(String str) {
        SpeechClient.instance().getTTSEngine().shutup(str);
    }

    public void startListening() {
        SpeechClient.instance().getASREngine().startListen();
    }

    public void cancelListening() {
        SpeechClient.instance().getASREngine().cancelListen();
    }

    public void disableWakeup() {
        SpeechClient.instance().getWakeupEngine().disableWakeupEnhance(this.mBinder);
        SpeechClient.instance().getWakeupEngine().setWheelWakeupEnabled(this.mBinder, false);
    }

    public void enableWakeup() {
        SpeechClient.instance().getWakeupEngine().enableWakeupEnhance(this.mBinder);
        SpeechClient.instance().getWakeupEngine().setWheelWakeupEnabled(this.mBinder, true);
    }
}
