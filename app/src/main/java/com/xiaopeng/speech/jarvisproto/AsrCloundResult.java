package com.xiaopeng.speech.jarvisproto;

import com.lzy.okgo.model.Progress;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AsrCloundResult extends JarvisProto {
    public static final String EVENT = "jarvis.asr.cloundresult";
    public String filePath;
    public String text;
    public String token;

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return EVENT;
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("token", this.token);
            jSONObject.put("text", this.text);
            jSONObject.put(Progress.FILE_PATH, this.filePath);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
