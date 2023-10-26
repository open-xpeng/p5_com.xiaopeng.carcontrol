package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface IAvmController extends IBaseCarController<Callback> {

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onAvmWorkStateChanged(int state) {
        }
    }

    int getAVMWorkSt();
}
