package com.xiaopeng.speech.jarvisproto;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ContextInput extends JarvisProto {
    public static final String EVENT = "jarvis.context.input";
    public boolean isEof;
    public boolean isInterrupted;
    public String pinyin = "";
    public int soundArea;
    public String text;

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return EVENT;
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("text", this.text);
            jSONObject.put("isEof", this.isEof);
            jSONObject.put("isInterrupted", this.isInterrupted);
            jSONObject.put("pinyin", this.pinyin);
            jSONObject.put("soundArea", this.soundArea);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
