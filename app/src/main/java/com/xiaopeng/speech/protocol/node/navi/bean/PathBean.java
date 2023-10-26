package com.xiaopeng.speech.protocol.node.navi.bean;

import android.text.TextUtils;
import com.xiaopeng.speech.common.util.DontProguardClass;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@DontProguardClass
/* loaded from: classes2.dex */
public class PathBean {
    private PoiBean destPoint;
    private int distance;
    private int naviType;
    private String rawAmapJson;
    private int routeSelectRef;
    private PoiBean startPoint;
    private int strategy;
    private int time;
    private int toll;
    private ArrayList<ViaBean> viaPoints;

    public static PathBean fromJson(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject optJSONObject = new JSONObject(str).optJSONObject("pathBean");
            if (optJSONObject != null) {
                PathBean pathBean = new PathBean();
                pathBean.startPoint = PoiBean.fromJson(optJSONObject.optString("startPoint"));
                pathBean.destPoint = PoiBean.fromJson(optJSONObject.optString("destPoint"));
                pathBean.strategy = optJSONObject.optInt("strategy");
                pathBean.distance = optJSONObject.optInt("distance");
                pathBean.time = optJSONObject.optInt("time");
                pathBean.toll = optJSONObject.optInt(NaviPreferenceBean.TOLL);
                pathBean.rawAmapJson = optJSONObject.optString("rawAmapJson");
                pathBean.naviType = optJSONObject.optInt("naviType");
                pathBean.routeSelectRef = optJSONObject.optInt("routeSelectRef");
                JSONArray optJSONArray = optJSONObject.optJSONArray("viaPoints");
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    pathBean.viaPoints = new ArrayList<>(optJSONArray.length());
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        JSONObject jSONObject = optJSONArray.getJSONObject(i);
                        ViaBean viaBean = new ViaBean();
                        viaBean.setPointInfo(PoiBean.fromJson(jSONObject.optString("pointInfo")));
                        viaBean.setViaType(jSONObject.optInt("viaType"));
                        pathBean.viaPoints.add(viaBean);
                    }
                }
                return pathBean;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PoiBean getStartPoint() {
        return this.startPoint;
    }

    public void setStartPoint(PoiBean poiBean) {
        this.startPoint = poiBean;
    }

    public PoiBean getDestPoint() {
        return this.destPoint;
    }

    public void setDestPoint(PoiBean poiBean) {
        this.destPoint = poiBean;
    }

    public ArrayList<ViaBean> getViaPoints() {
        return this.viaPoints;
    }

    public void setViaPoints(ArrayList<ViaBean> arrayList) {
        this.viaPoints = arrayList;
    }

    public int getStrategy() {
        return this.strategy;
    }

    public void setStrategy(int i) {
        this.strategy = i;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int i) {
        this.distance = i;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int i) {
        this.time = i;
    }

    public int getToll() {
        return this.toll;
    }

    public void setToll(int i) {
        this.toll = i;
    }

    public String getRawAmapJson() {
        return this.rawAmapJson;
    }

    public void setRawAmapJson(String str) {
        this.rawAmapJson = str;
    }

    public String toString() {
        return "PathBean{destPoint=" + this.destPoint + ", viaPoints=" + this.viaPoints + ", strategy=" + this.strategy + ", distance=" + this.distance + ", time=" + this.time + ", toll=" + this.toll + ", naviType=" + this.naviType + ", routeSelectRef=" + this.routeSelectRef + ", rawAmapJson='" + this.rawAmapJson + "'}";
    }

    public int getNaviType() {
        return this.naviType;
    }

    public void setNaviType(int i) {
        this.naviType = i;
    }

    public int getRouteSelectRef() {
        return this.routeSelectRef;
    }

    public void setRouteSelectRef(int i) {
        this.routeSelectRef = i;
    }
}
