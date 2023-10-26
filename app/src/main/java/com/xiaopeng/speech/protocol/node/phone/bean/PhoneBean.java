package com.xiaopeng.speech.protocol.node.phone.bean;

import android.text.TextUtils;
import com.xiaopeng.speech.common.bean.BaseBean;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class PhoneBean extends BaseBean {
    private String address;
    private String id;
    private String name;
    private String number;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public static PhoneBean fromJson(String str) {
        PhoneBean phoneBean = new PhoneBean();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("联系人");
            phoneBean.name = optString;
            if (TextUtils.isEmpty(optString)) {
                phoneBean.name = jSONObject.optString("任意联系人");
            }
            phoneBean.number = jSONObject.optString("号码");
            phoneBean.address = jSONObject.optString("归属地");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return phoneBean;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String str) {
        this.number = str;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public String toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("任意联系人", this.name).putOpt("号码", this.number).putOpt("归属地", this.address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
