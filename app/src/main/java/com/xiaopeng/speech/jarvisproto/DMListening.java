package com.xiaopeng.speech.jarvisproto;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DMListening extends Volume {
    public static final String EVENT = "jarvis.dm.listening";

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return EVENT;
    }

    public static final DMListening fromJson(String str) {
        DMListening dMListening = new DMListening(0);
        try {
            JSONObject jSONObject = new JSONObject(str);
            dMListening.volume = jSONObject.optInt("volume");
            dMListening.soundArea = jSONObject.optInt("soundArea");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dMListening;
    }

    public DMListening(int i) {
        super(i, -1);
    }

    public DMListening(int i, int i2) {
        super(i, i2);
    }

    public DMListening copy(Volume volume) {
        this.volume = volume.volume;
        return this;
    }
}
