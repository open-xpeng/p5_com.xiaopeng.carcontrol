package com.xiaopeng.speech.protocol.node.carcontrol.bean;

import android.text.TextUtils;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SeatValue {
    private int xValue;
    private int yValue;
    private int zValue;

    public static SeatValue fromJson(String str) {
        SeatValue seatValue = new SeatValue();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("xtarget");
            String optString2 = jSONObject.optString("vtarget");
            String optString3 = jSONObject.optString("htarget");
            int parseInt = !TextUtils.isEmpty(optString) ? Integer.parseInt(optString) : -1;
            int parseInt2 = !TextUtils.isEmpty(optString2) ? Integer.parseInt(optString2) : -1;
            int parseInt3 = TextUtils.isEmpty(optString3) ? -1 : Integer.parseInt(optString3);
            seatValue.xValue = parseInt;
            seatValue.yValue = parseInt2;
            seatValue.zValue = parseInt3;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seatValue;
    }

    public int getyValue() {
        return this.yValue;
    }

    public int getxValue() {
        return this.xValue;
    }

    public int getzValue() {
        return this.zValue;
    }

    public String toString() {
        return "SeatValue{xValue=" + this.xValue + ", yValue=" + this.yValue + ", zValue=" + this.zValue + '}';
    }
}
