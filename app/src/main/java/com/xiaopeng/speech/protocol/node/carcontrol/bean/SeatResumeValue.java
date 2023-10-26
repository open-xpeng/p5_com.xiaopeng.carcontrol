package com.xiaopeng.speech.protocol.node.carcontrol.bean;

import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SeatResumeValue {
    public boolean fromDriveDoor;

    public static SeatResumeValue fromJson(String str) {
        SeatResumeValue seatResumeValue = new SeatResumeValue();
        try {
            seatResumeValue.fromDriveDoor = new JSONObject(str).optBoolean("fromDriveDoor");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seatResumeValue;
    }
}
