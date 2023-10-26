package com.xiaopeng.speech.speechwidget;

/* loaded from: classes2.dex */
public class SupportWidget extends SpeechWidget {
    public static final String KEY_SUPPORT = "status";
    public static final String KEY_TTS = "tts";
    public static final String STATUS_NOT_SUPPORT = "0";
    public static final String STATUS_SUPPORT = "1";

    public SupportWidget() {
        super("custom");
    }

    @Override // com.xiaopeng.speech.speechwidget.SpeechWidget
    public SupportWidget fromJson(String str) {
        return (SupportWidget) super.fromJson(str);
    }

    public SupportWidget setSupport(boolean z) {
        return (SupportWidget) super.addContent("status", z ? "1" : "0");
    }

    public boolean isSupport() {
        return getContent("status").equals("1");
    }

    public SupportWidget setTTS(String str) {
        return (SupportWidget) super.addContent("tts", str);
    }

    public String getTTS() {
        return getContent("tts");
    }
}
