package com.xiaopeng.carcontrol.viewmodel.service;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IServiceViewModel extends IBaseViewModel {
    float getCarSpeed();

    int getDriveMode();

    float getHvacDriverTemp();

    float getHvacExternalTemp();

    int getHvacInnerPM25();

    float getHvacPsnTemp();

    int getHvacWindSpeedLevel();

    void handleEmergencyIgOff();

    boolean isCentralLocked();

    boolean isDrvSeatOccupied();

    boolean isHvacAutoModeOn();

    boolean isHvacDriverSyncMode();

    boolean isHvacFrontDefrostOn();

    boolean isHvacPowerModeOn();

    boolean isHvacPsnSyncMode();

    boolean isMirrorHeatEnabled();

    boolean isPsnSeatOccupied();

    void onIgLocalOn();

    void onIgOff();

    void onIgRemoteOn();

    void onShowDialog(String dialogScene);

    void requestCarLicensePlate();

    void resumeSaveSettings();

    void setCentralLock(boolean lock);

    void setDriveMode(int driveMode);

    void setHvacDriverSyncMode(boolean enable);

    void setHvacFrontDefrost(boolean enable);

    void setHvacPowerMode(boolean enable);

    void setHvacPsnSyncMode(boolean enable);

    void setHvacTempDriver(float temperature);

    boolean setHvacTempDrvStep(boolean isUp);

    void setHvacTempPsn(float temperature);

    boolean setHvacTempPsnStep(boolean isUp);

    void setMirrorHeatEnable(boolean enable);

    void setSsDoorStateCallback(ISSDoorCallBack callback);
}
