package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface ICdcController extends IBaseCarController<Callback> {
    public static final int CDC_FUNCTION_MODE_COMFORT = 2;
    public static final int CDC_FUNCTION_MODE_SPORT = 3;
    public static final int CDC_FUNCTION_MODE_STANDARD = 1;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onCdcModeChanged(int cdc) {
        }
    }

    int getCdcMode();

    void setCdcMode(int cdc, boolean needSave);
}
