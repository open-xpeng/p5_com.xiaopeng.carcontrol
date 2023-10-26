package com.xiaopeng.libconfig.ipc.wheel;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class WheelEventValue {
    private int eventCode;
    private int eventValue;
    private int keyCode;
    private int keyMode;

    public WheelEventValue() {
    }

    public WheelEventValue(int i, int i2, int i3, int i4) {
        this.keyCode = i;
        this.eventCode = i2;
        this.eventValue = i3;
        this.keyMode = i4;
    }

    public WheelEventValue(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.keyCode = jSONObject.getInt(WheelEvent.KEY_ACTION_KEY_CODE);
            this.eventCode = jSONObject.getInt(WheelEvent.KEY_ACTION_EVENT_CODE);
            this.eventValue = jSONObject.getInt(WheelEvent.KEY_ACTION_EVENT_VALUE);
            this.keyMode = jSONObject.getInt(WheelEvent.KEY_ACTION_KEY_MODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public void setKeyCode(int i) {
        this.keyCode = i;
    }

    public int getEventCode() {
        return this.eventCode;
    }

    public void setEventCode(int i) {
        this.eventCode = i;
    }

    public int getEventValue() {
        return this.eventValue;
    }

    public void setEventValue(int i) {
        this.eventValue = i;
    }

    public int getKeyMode() {
        return this.keyMode;
    }

    public void setKeyMode(int i) {
        this.keyMode = i;
    }

    public String toString() {
        return "WheelEventValue{keyCode=" + this.keyCode + ", eventCode=" + this.eventCode + ", eventValue=" + this.eventValue + ", keyMode=" + this.keyMode + '}';
    }
}
