package com.xiaopeng.carcontrol.viewmodel.service;

/* loaded from: classes2.dex */
public interface ISSDoorCallBack {
    void onAvmWorkStateChanged(int state);

    void onDoorsStateChanged(int[] states);

    void onInitSsDoor();

    void onLeftSdcDoorPosChanged(int pos);

    void onLeftSdcSysStateChanged(int state);

    void onRightSdcDoorPosChanged(int pos);

    void onRightSdcSysStateChanged(int state);

    void onUnInitSsDoor();

    void onVcuGearChanged(int gear);
}
