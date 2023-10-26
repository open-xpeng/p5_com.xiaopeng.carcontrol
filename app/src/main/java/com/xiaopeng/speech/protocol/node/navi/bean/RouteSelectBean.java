package com.xiaopeng.speech.protocol.node.navi.bean;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class RouteSelectBean {
    public int batteryStatus;
    public String batteryStatusTips;
    public String remainDistance;
    public long remainDistanceValue;
    public String routeLeftDistance;
    public long routeLeftDistanceValue;
    public String routeTypeName;
    public String routeTypeNo;
    public String totalTimeLine1;
    public long totalTimeLine1Value;
    public String totalTimeLine2;
    public String trafficCost;
    public String trafficSignal;

    public static RouteSelectBean fromJson(String str) {
        RouteSelectBean routeSelectBean = new RouteSelectBean();
        try {
            JSONObject jSONObject = new JSONObject(str);
            routeSelectBean.routeTypeNo = jSONObject.optString("routeTypeNo");
            routeSelectBean.routeTypeName = jSONObject.optString("routeTypeName");
            routeSelectBean.trafficSignal = jSONObject.optString("trafficSignal");
            routeSelectBean.trafficCost = jSONObject.optString("trafficCost");
            routeSelectBean.routeLeftDistance = jSONObject.optString("routeLeftDistance");
            routeSelectBean.totalTimeLine1 = jSONObject.optString("totalTimeLine1");
            routeSelectBean.totalTimeLine2 = jSONObject.optString("totalTimeLine2");
            routeSelectBean.remainDistance = jSONObject.optString("remainDistance");
            routeSelectBean.batteryStatus = jSONObject.optInt("batteryStatus");
            routeSelectBean.batteryStatusTips = jSONObject.optString("batteryStatusTips");
            routeSelectBean.routeLeftDistanceValue = jSONObject.optLong("routeLeftDistanceValue");
            routeSelectBean.totalTimeLine1Value = jSONObject.optLong("totalTimeLine1Value");
            routeSelectBean.remainDistanceValue = jSONObject.optLong("remainDistanceValue");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return routeSelectBean;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("routeTypeNo", this.routeTypeNo);
        jSONObject.put("routeTypeName", this.routeTypeName);
        jSONObject.put("trafficSignal", this.trafficSignal);
        jSONObject.put("trafficCost", this.trafficCost);
        jSONObject.put("routeLeftDistance", this.routeLeftDistance);
        jSONObject.put("totalTimeLine1", this.totalTimeLine1);
        jSONObject.put("totalTimeLine2", this.totalTimeLine2);
        jSONObject.put("remainDistance", this.remainDistance);
        jSONObject.put("batteryStatus", this.batteryStatus);
        jSONObject.put("batteryStatusTips", this.batteryStatusTips);
        jSONObject.put("routeLeftDistanceValue", this.routeLeftDistanceValue);
        jSONObject.put("totalTimeLine1Value", this.totalTimeLine1Value);
        jSONObject.put("remainDistanceValue", this.remainDistanceValue);
        return jSONObject;
    }

    public String toString() {
        return "RouteSelectBean{routeTypeName='" + this.routeTypeName + "', routeTypeNo='" + this.routeTypeNo + "', trafficSignal='" + this.trafficSignal + "', trafficCost='" + this.trafficCost + "', routeLeftDistance='" + this.routeLeftDistance + "', totalTimeLine1='" + this.totalTimeLine1 + "', totalTimeLine2='" + this.totalTimeLine2 + "', remainDistance='" + this.remainDistance + "', batteryStatus='" + this.batteryStatus + "', batteryStatusTips='" + this.batteryStatusTips + "', routeLeftDistanceValue='" + this.routeLeftDistanceValue + "', totalTimeLine1Value='" + this.totalTimeLine1Value + "', remainDistanceValue='" + this.remainDistanceValue + "'}";
    }
}
