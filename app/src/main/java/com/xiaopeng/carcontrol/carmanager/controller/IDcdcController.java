package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface IDcdcController extends IBaseCarController<Callback> {

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
    }

    float getDcdcInputCurrent();

    int getDcdcInputVoltage();
}
