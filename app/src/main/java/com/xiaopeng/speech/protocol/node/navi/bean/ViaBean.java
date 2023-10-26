package com.xiaopeng.speech.protocol.node.navi.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ViaBean implements Parcelable {
    protected static final Parcelable.Creator<ViaBean> CREATOR = new Parcelable.Creator<ViaBean>() { // from class: com.xiaopeng.speech.protocol.node.navi.bean.ViaBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ViaBean createFromParcel(Parcel parcel) {
            return new ViaBean(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ViaBean[] newArray(int i) {
            return new ViaBean[i];
        }
    };
    public static final int WAYPOINT_TYPE_CHARGING_STATION = 1;
    public static final int WAYPOINT_TYPE_EXTRA = 0;
    public static final int WAYPOINT_TYPE_USER_POI = 2;
    public static final int WAYPOINT_TYPE_USER_ROAD = 3;
    private PoiBean pointInfo;
    private int viaType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ViaBean() {
    }

    public ViaBean(Parcel parcel) {
        this.pointInfo = (PoiBean) parcel.readParcelable(PoiBean.class.getClassLoader());
        this.viaType = parcel.readInt();
    }

    public PoiBean getPointInfo() {
        return this.pointInfo;
    }

    public void setPointInfo(PoiBean poiBean) {
        this.pointInfo = poiBean;
    }

    public int getViaType() {
        return this.viaType;
    }

    public void setViaType(int i) {
        this.viaType = i;
    }

    public String toString() {
        return "ViaBean{pointInfo=" + this.pointInfo + ", viaType=" + this.viaType + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.pointInfo, i);
        parcel.writeInt(this.viaType);
    }
}
