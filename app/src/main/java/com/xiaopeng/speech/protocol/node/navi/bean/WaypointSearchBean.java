package com.xiaopeng.speech.protocol.node.navi.bean;

import org.eclipse.paho.android.service.MqttServiceConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class WaypointSearchBean {
    private String destinationName;
    private String destinationType;
    private boolean isSetAsDestination;

    public static WaypointSearchBean fromJson(String str) {
        WaypointSearchBean waypointSearchBean = new WaypointSearchBean();
        try {
            JSONObject jSONObject = new JSONObject(str);
            waypointSearchBean.destinationName = jSONObject.optString(MqttServiceConstants.DESTINATION_NAME);
            waypointSearchBean.destinationType = jSONObject.optString("destinationType");
            waypointSearchBean.isSetAsDestination = jSONObject.optInt("isSetAsDestination", 0) == 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return waypointSearchBean;
    }

    public String getDestinationName() {
        return this.destinationName;
    }

    public String getDestinationType() {
        return this.destinationType;
    }

    public void setDestinationName(String str) {
        this.destinationName = str;
    }

    public void setDestinationType(String str) {
        this.destinationType = str;
    }

    public boolean isSetAsDestination() {
        return this.isSetAsDestination;
    }

    public void setSetAsDestination(boolean z) {
        this.isSetAsDestination = z;
    }

    public String toString() {
        return "WaypointSearchBean{destinationName='" + this.destinationName + "', destinationType='" + this.destinationType + "', isSetAsDestination=" + this.isSetAsDestination + '}';
    }
}
