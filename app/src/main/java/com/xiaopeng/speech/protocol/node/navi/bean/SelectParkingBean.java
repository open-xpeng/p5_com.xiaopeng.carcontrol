package com.xiaopeng.speech.protocol.node.navi.bean;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SelectParkingBean {
    private int num;

    public static SelectParkingBean fromJson(String str) {
        SelectParkingBean selectParkingBean = new SelectParkingBean();
        try {
            selectParkingBean.num = new JSONObject(str).optInt("num");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return selectParkingBean;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int i) {
        this.num = i;
    }

    public String toString() {
        return "SelectParkingBean{num=" + this.num + '}';
    }
}
