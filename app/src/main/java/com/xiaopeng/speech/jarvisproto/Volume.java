package com.xiaopeng.speech.jarvisproto;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class Volume extends JarvisProto {
    public int soundArea;
    public int volume;

    public Volume(int i) {
        this.volume = i;
    }

    public Volume(int i, int i2) {
        this.volume = i;
        this.soundArea = i2;
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("volume", this.volume);
            jSONObject.put("soundArea", this.soundArea);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
