package com.xiaopeng.speech.jarvisproto;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DMRecognized extends JarvisProto {
    public static final String EVENT = "jarvis.dm.recognized";
    public int errorId = 0;

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return EVENT;
    }

    public static final DMRecognized fromJson(String str) {
        DMRecognized dMRecognized = new DMRecognized();
        try {
            dMRecognized.errorId = new JSONObject(str).optInt("errorId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dMRecognized;
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("errorId", this.errorId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
