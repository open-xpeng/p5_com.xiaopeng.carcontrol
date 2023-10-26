package com.xiaopeng.speech.protocol.node.navi.bean;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SelectRouteBean {
    private int num;

    public static SelectRouteBean fromJson(String str) {
        SelectRouteBean selectRouteBean = new SelectRouteBean();
        try {
            selectRouteBean.num = new JSONObject(str).optInt("num");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return selectRouteBean;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int i) {
        this.num = i;
    }

    public String toString() {
        return "selectRouteBean{num=" + this.num + '}';
    }
}
