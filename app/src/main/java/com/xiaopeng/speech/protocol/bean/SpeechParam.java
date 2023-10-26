package com.xiaopeng.speech.protocol.bean;

import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SpeechParam {
    private boolean isAnimation;
    private boolean isAppTTS;

    public static SpeechParam fromJson(String str) {
        SpeechParam speechParam = new SpeechParam();
        try {
            JSONObject jSONObject = new JSONObject(str);
            speechParam.isAppTTS = jSONObject.optBoolean("app_tts", true);
            speechParam.isAnimation = jSONObject.optBoolean("animation");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return speechParam;
    }

    public boolean isAppTTS() {
        return this.isAppTTS;
    }

    public boolean isAnimation() {
        return this.isAnimation;
    }
}
