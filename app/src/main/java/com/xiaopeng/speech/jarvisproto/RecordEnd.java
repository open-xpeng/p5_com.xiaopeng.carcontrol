package com.xiaopeng.speech.jarvisproto;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class RecordEnd extends JarvisProto {
    public static final String EVENT = "jarvis.record.end";
    public boolean isStopRecord;

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return EVENT;
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("isStopRecord", this.isStopRecord);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
