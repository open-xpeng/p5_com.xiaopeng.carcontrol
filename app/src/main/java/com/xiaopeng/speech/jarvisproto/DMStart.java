package com.xiaopeng.speech.jarvisproto;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DMStart extends JarvisProto {
    public static final String EVENT = "jarvis.dm.start";
    public static final String REASON_API_START = "api.start";
    public static final String REASON_BOSS_START = "boss.start";
    public static final String REASON_CLICK_START = "click.start";
    public static final String REASON_MUSIC_SEARCH = "music";
    public static final String REASON_REAR_BOSS_START = "rear.boss.start";
    public static final String REASON_SEND_TEXT = "api.sendText";
    public static final String REASON_WAITING = "waiting";
    public static final String REASON_WAKEUP_CMD = "wakeup.command";
    public static final String REASON_WAKEUP_MAJOR = "wakeup.major";
    public static final String REASON_WHEEL_START = "wheel.start";
    public String reason;
    public String sessionId;
    public int soundArea;
    public WakeupResult wakeupResult;

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return "jarvis.dm.start";
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("reason", this.reason);
            jSONObject.put("soundArea", this.soundArea);
            WakeupResult wakeupResult = this.wakeupResult;
            if (wakeupResult != null) {
                jSONObject.put("wakeupResult", wakeupResult.getJsonData());
            } else {
                jSONObject.put("wakeupResult", (Object) null);
            }
            jSONObject.put("sessionId", this.sessionId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
