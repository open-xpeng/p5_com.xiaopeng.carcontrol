package com.xiaopeng.speech.protocol.node.navi.bean;

import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.protocol.utils.CarTypeUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class NearbySearchBean {
    private static final String TAG = "NearbySearchBean";
    private String destination;
    private String destinationAddr;
    private String destinationType;

    public static NearbySearchBean fromJson(String str) {
        NearbySearchBean nearbySearchBean = new NearbySearchBean();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("destinationPrefix", jSONObject.optString("终点修饰"));
            boolean isOverseasCarType = CarTypeUtils.isOverseasCarType();
            LogUtils.d(TAG, "fromJson, isOverseasCarType: " + isOverseasCarType);
            if (!isOverseasCarType) {
                nearbySearchBean.destination = optString + jSONObject.optString("destination", jSONObject.optString("终点目标"));
            } else {
                nearbySearchBean.destinationAddr = optString;
                nearbySearchBean.destination = jSONObject.optString("destination", jSONObject.optString("终点目标"));
            }
            nearbySearchBean.destinationType = jSONObject.optString("destinationType", jSONObject.optString("终点类型"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nearbySearchBean;
    }

    public String getDestination() {
        return this.destination;
    }

    public String getDestinationAddr() {
        return this.destinationAddr;
    }

    public String getDestinationType() {
        return this.destinationType;
    }

    public String toString() {
        return "NearbySearchBean{destination='" + this.destination + "', destinationAddr='" + this.destinationAddr + "', destinationType='" + this.destinationType + "'}";
    }
}
