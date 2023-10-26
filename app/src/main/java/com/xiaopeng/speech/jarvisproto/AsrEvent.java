package com.xiaopeng.speech.jarvisproto;

import androidx.core.app.NotificationCompat;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AsrEvent extends JarvisProto {
    public static final String EVENT = "jarvis.asr.event";
    public static final int STATE_ASR_BEGIN = 1;
    public static final int STATE_ASR_END = 2;
    public static final int STATE_START_LISTEN = 0;
    public int mEvent;
    public int soundArea;

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return EVENT;
    }

    public AsrEvent() {
        this.mEvent = -1;
        this.soundArea = -1;
    }

    public AsrEvent(int i) {
        this.mEvent = -1;
        this.soundArea = -1;
        this.mEvent = i;
    }

    public AsrEvent(int i, int i2) {
        this.mEvent = -1;
        this.soundArea = -1;
        this.mEvent = i;
        this.soundArea = i2;
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NotificationCompat.CATEGORY_EVENT, this.mEvent);
            jSONObject.put("soundArea", this.soundArea);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static AsrEvent fromJson(String str) {
        int i;
        JSONObject jSONObject;
        int i2 = -1;
        try {
            jSONObject = new JSONObject(str);
            i = jSONObject.optInt(NotificationCompat.CATEGORY_EVENT);
        } catch (JSONException e) {
            e = e;
            i = -1;
        }
        try {
            i2 = jSONObject.optInt("soundArea");
        } catch (JSONException e2) {
            e = e2;
            e.printStackTrace();
            return new AsrEvent(i, i2);
        }
        return new AsrEvent(i, i2);
    }
}
