package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface ICarInfoController extends IBaseCarController<Callback> {

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        void onCarLicensePlateChanged(String licensePlate);
    }

    String getCarLicensePlate();

    void requestCarLicensePlate();

    void setCarLicensePlate(String licensePlate);
}
