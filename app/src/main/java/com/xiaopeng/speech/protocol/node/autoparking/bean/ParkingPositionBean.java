package com.xiaopeng.speech.protocol.node.autoparking.bean;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ParkingPositionBean {
    private int position;

    public static final ParkingPositionBean fromJson(String str) {
        ParkingPositionBean parkingPositionBean = new ParkingPositionBean();
        try {
            parkingPositionBean.position = new JSONObject(str).optInt("position");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parkingPositionBean;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public String toString() {
        return "ParkingPositionBean{position=" + this.position + '}';
    }
}
