package com.xiaopeng.speech.jarvisproto;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class WakeupResultBase extends JarvisProto {
    public int angle;
    public boolean autoListen;
    public boolean canWakeup = true;
    public int channel;
    public String command;
    public String param;
    public String reason;
    public String script;
    public String word;

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("reason", this.reason);
            jSONObject.put("word", this.word);
            jSONObject.put(WakeupResult.REASON_COMMAND, this.command);
            jSONObject.put("angle", this.angle);
            jSONObject.put("channel", this.channel);
            jSONObject.put("canWakeup", this.canWakeup);
            jSONObject.put("param", this.param);
            jSONObject.put("script", this.script);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
