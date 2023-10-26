package com.xiaopeng.speech.protocol.node.navi.bean;

import com.xiaopeng.speech.common.util.DontProguardClass;
import org.json.JSONException;
import org.json.JSONObject;

@DontProguardClass
/* loaded from: classes2.dex */
public class ChargeBean {
    private int available;
    private String power;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int i) {
        this.total = i;
    }

    public int getAvailable() {
        return this.available;
    }

    public void setAvailable(int i) {
        this.available = i;
    }

    public String getPower() {
        return this.power;
    }

    public void setPower(String str) {
        this.power = str;
    }

    public static ChargeBean fromJson(String str) {
        ChargeBean chargeBean = new ChargeBean();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("total")) {
                chargeBean.total = jSONObject.optInt("total");
            }
            if (jSONObject.has("available")) {
                chargeBean.available = jSONObject.optInt("available");
            }
            if (jSONObject.has("power")) {
                chargeBean.power = jSONObject.optString("power");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chargeBean;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("total", this.total);
        jSONObject.put("available", this.available);
        jSONObject.put("power", this.power);
        return jSONObject;
    }

    public String toString() {
        return "ChargeBean{total=" + this.total + ", available=" + this.available + ", power=" + this.power + '}';
    }
}
