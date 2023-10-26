package com.xiaopeng.speech.jarvisproto;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DMWait extends JarvisProto {
    public static final String EVENT = "jarvis.dm.wait";
    public static final String STATUS_END = "end";
    public static final String STATUS_END_ASR = "end_asr";
    public static final String STATUS_ENTER = "enter";
    public static final String STATUS_FEED_NLU = "feednlu";
    public static final String STATUS_INVALID_SPEECH = "invalid_speech";
    public static final String STATUS_START_ASR = "start_asr";
    public static final String STATUS_TIME = "timeout";
    public static final String STATUS_TTS_END = "tts_end";
    public static final String STATUS_UPDATE = "update";
    public String expression;
    public String reason;
    public String sessionId;
    public int soundArea;
    public boolean speaking;
    public String tips;

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return EVENT;
    }

    public DMWait() {
    }

    public DMWait(String str, String str2, String str3, String str4, boolean z) {
        this.reason = str;
        this.tips = str3;
        this.sessionId = str2;
        this.expression = str4;
        this.speaking = z;
    }

    public DMWait(String str, String str2, String str3, String str4, boolean z, int i) {
        this.reason = str;
        this.tips = str3;
        this.sessionId = str2;
        this.expression = str4;
        this.speaking = z;
        this.soundArea = i;
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("reason", this.reason);
            jSONObject.put("tips", this.tips);
            jSONObject.put("sessionId", this.sessionId);
            jSONObject.put("expression", this.expression);
            jSONObject.put("speaking", this.speaking);
            jSONObject.put("soundArea", this.soundArea);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static DMWait fromJson(String str) {
        String str2;
        JSONException jSONException;
        boolean z;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        boolean z2;
        int i;
        String optString;
        String str9 = "";
        String str10 = DMEnd.REASON_NORMAL;
        boolean z3 = false;
        try {
            JSONObject jSONObject = new JSONObject(str);
            str10 = jSONObject.optString("reason");
            str2 = jSONObject.optString("tips");
            try {
                optString = jSONObject.optString("sessionId");
            } catch (JSONException e) {
                jSONException = e;
                z = false;
                str3 = str10;
                str4 = "";
            }
            try {
                str9 = jSONObject.optString("expression");
                z3 = jSONObject.optBoolean("speaking");
                str5 = str2;
                str7 = str9;
                str8 = str10;
                z2 = z3;
                i = jSONObject.optInt("soundArea");
                str6 = optString;
            } catch (JSONException e2) {
                str4 = str9;
                str9 = optString;
                jSONException = e2;
                z = z3;
                str3 = str10;
                jSONException.printStackTrace();
                str5 = str2;
                str6 = str9;
                str7 = str4;
                str8 = str3;
                z2 = z;
                i = -1;
                return new DMWait(str8, str6, str5, str7, z2, i);
            }
        } catch (JSONException e3) {
            str2 = "";
            jSONException = e3;
            z = false;
            str3 = str10;
            str4 = str2;
        }
        return new DMWait(str8, str6, str5, str7, z2, i);
    }
}
