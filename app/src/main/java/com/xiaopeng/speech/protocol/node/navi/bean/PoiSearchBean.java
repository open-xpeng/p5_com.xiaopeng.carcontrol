package com.xiaopeng.speech.protocol.node.navi.bean;

import android.text.TextUtils;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.protocol.utils.CarTypeUtils;
import org.eclipse.paho.android.service.MqttServiceConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class PoiSearchBean {
    private static final String TAG = "PoiSearchBean";
    private String destination;
    private String destinationAddr;
    private String destinationType;
    private String pathPref;
    private String travelMode;

    public static PoiSearchBean fromJson(String str) {
        PoiSearchBean poiSearchBean = new PoiSearchBean();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("destinationPrefix");
            String optString2 = jSONObject.optString("destinationTarget");
            String optString3 = jSONObject.optString(MqttServiceConstants.DESTINATION_NAME);
            boolean isOverseasCarType = CarTypeUtils.isOverseasCarType();
            LogUtils.d(TAG, "fromJson, isOverseasCarType: " + isOverseasCarType);
            if (!isOverseasCarType) {
                poiSearchBean.destination = optString;
                StringBuilder append = new StringBuilder().append(poiSearchBean.destination);
                if (TextUtils.isEmpty(optString2)) {
                    optString2 = optString3;
                }
                poiSearchBean.destination = append.append(optString2).toString();
            } else {
                poiSearchBean.destinationAddr = optString;
                if (TextUtils.isEmpty(optString2)) {
                    optString2 = optString3;
                }
                poiSearchBean.destination = optString2;
            }
            poiSearchBean.travelMode = jSONObject.optString("travelMode");
            poiSearchBean.destinationType = jSONObject.optString("destinationType");
            poiSearchBean.pathPref = jSONObject.optString("pref");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return poiSearchBean;
    }

    public String getDestination() {
        return this.destination;
    }

    public String getDestinationAddr() {
        return this.destinationAddr;
    }

    public String getTravelMode() {
        return this.travelMode;
    }

    public String getDestinationType() {
        return this.destinationType;
    }

    public String getPathPref() {
        return this.pathPref;
    }

    public String toString() {
        return "PoiSearchBean{destination='" + this.destination + "', destinationAddr='" + this.destinationAddr + "', travelMode='" + this.travelMode + "', destinationType='" + this.destinationType + "', pathPref='" + this.pathPref + "'}";
    }
}
