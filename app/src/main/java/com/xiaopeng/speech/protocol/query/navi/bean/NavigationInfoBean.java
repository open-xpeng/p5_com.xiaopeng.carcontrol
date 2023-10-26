package com.xiaopeng.speech.protocol.query.navi.bean;

import com.xiaopeng.speech.common.util.DontProguardClass;
import com.xiaopeng.speech.protocol.node.navi.bean.NaviInfoBean;
import com.xiaopeng.speech.protocol.node.navi.bean.PoiBean;
import java.util.List;

@DontProguardClass
/* loaded from: classes2.dex */
public class NavigationInfoBean {
    public PoiBean destinationPoint;
    public long remainderLift;
    public long remainderRange;
    public long remainderServieArea;
    public long remainderTime;
    public long remainderTollStation;
    public long speedLimit;
    public List<NaviInfoBean> viaPoints;

    public long getRemainderRange() {
        return this.remainderRange;
    }

    public long getRemainderServieArea() {
        return this.remainderServieArea;
    }

    public void setRemainderServieArea(long j) {
        this.remainderServieArea = j;
    }

    public long getRemainderTollStation() {
        return this.remainderTollStation;
    }

    public void setRemainderTollStation(long j) {
        this.remainderTollStation = j;
    }

    public long getSpeedLimit() {
        return this.speedLimit;
    }

    public void setSpeedLimit(long j) {
        this.speedLimit = j;
    }

    public void setRemainderRange(long j) {
        this.remainderRange = j;
    }

    public long getRemainderTime() {
        return this.remainderTime;
    }

    public void setRemainderTime(long j) {
        this.remainderTime = j;
    }

    public long getRemainderLift() {
        return this.remainderLift;
    }

    public void setRemainderLift(long j) {
        this.remainderLift = j;
    }

    public List<NaviInfoBean> getViaPoints() {
        return this.viaPoints;
    }

    public void setViaPoints(List<NaviInfoBean> list) {
        this.viaPoints = list;
    }

    public PoiBean getDestinationPoint() {
        return this.destinationPoint;
    }

    public void setDestinationPoint(PoiBean poiBean) {
        this.destinationPoint = poiBean;
    }

    public String toString() {
        return "NavigationInfoBean{remainderRange=" + this.remainderRange + ", remainderTime=" + this.remainderTime + ", remainderLift=" + this.remainderLift + ", remainderServieArea=" + this.remainderServieArea + ", remainderTollStation=" + this.remainderTollStation + ", speedLimit=" + this.speedLimit + ", viaPoints=" + this.viaPoints + ", destinationPoint=" + this.destinationPoint + '}';
    }
}
