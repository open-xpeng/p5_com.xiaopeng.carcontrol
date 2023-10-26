package com.xiaopeng.xvs.xid.sync.api;

/* loaded from: classes2.dex */
public interface ICarDvrSync {
    public static final String KEY_DVR_BT_ST = "DVR_KEY_DVR_BT_ST";
    public static final String KEY_DVR_TYPE = "DVR_KEY_DVR_TYPE";

    String getDvrBtSt(String str);

    String getDvrType(String str);

    void putDvrBtSt(String str);

    void putDvrType(String str);
}
