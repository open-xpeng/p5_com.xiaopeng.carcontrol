package com.xiaopeng.speech.jarvisproto;

import com.xiaopeng.speech.protocol.bean.stats.SceneSwitchStatisticsBean;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class HotWordResult extends JarvisProto {
    public static final String EVENT = "jarvis.hotword.result";
    public String command;
    public String scene;
    public String tts;
    public String word;

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return EVENT;
    }

    public HotWordResult(String str, String str2, String str3, String str4) {
        this.word = str;
        this.scene = str2;
        this.command = str3;
        this.tts = str4;
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("word", this.word);
            jSONObject.put(SceneSwitchStatisticsBean.NAME_SCENE, this.scene);
            jSONObject.put(WakeupResult.REASON_COMMAND, this.command);
            jSONObject.put("tts", this.tts);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static HotWordResult fromJson(String str) {
        String str2;
        String str3;
        String str4;
        JSONObject jSONObject;
        String str5 = "";
        try {
            jSONObject = new JSONObject(str);
            str2 = jSONObject.optString("word");
        } catch (JSONException e) {
            e = e;
            str2 = "";
            str3 = str2;
        }
        try {
            str3 = jSONObject.optString(SceneSwitchStatisticsBean.NAME_SCENE);
            try {
                str4 = jSONObject.optString(WakeupResult.REASON_COMMAND);
            } catch (JSONException e2) {
                e = e2;
                str4 = "";
            }
        } catch (JSONException e3) {
            e = e3;
            str3 = "";
            str4 = str3;
            e.printStackTrace();
            return new HotWordResult(str2, str3, str4, str5);
        }
        try {
            str5 = jSONObject.optString("tts");
        } catch (JSONException e4) {
            e = e4;
            e.printStackTrace();
            return new HotWordResult(str2, str3, str4, str5);
        }
        return new HotWordResult(str2, str3, str4, str5);
    }
}
