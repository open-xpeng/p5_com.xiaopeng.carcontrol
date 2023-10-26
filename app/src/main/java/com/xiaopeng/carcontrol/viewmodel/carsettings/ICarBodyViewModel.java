package com.xiaopeng.carcontrol.viewmodel.carsettings;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface ICarBodyViewModel extends IBaseViewModel {
    void cancelAllControlSteer();

    void enterSteerScene();

    void exitSteerScene();

    int getArsWorkMode();

    boolean getAutoPowerOffConfig();

    int getChargePortDisableString(boolean isLeft);

    int getChargePortUnlock(int port);

    boolean getDriveAutoLock();

    boolean getInductionLock();

    boolean getLeaveAutoLock();

    boolean getNearUnlock();

    boolean getParkingAutoUnlock();

    boolean getRearWiperRepairMode();

    boolean getStealthMode();

    boolean getStopNfcCardSw();

    int getUnlockResponse();

    WiperInterval getWiperIntervalValue();

    boolean getWiperRepairMode();

    boolean isChargeGunUnlinked(boolean isLeft);

    boolean isChargePortEnable(boolean isLeft, boolean showToast);

    boolean isChargePortResetable(boolean isLeft);

    boolean isChildModeEnable();

    boolean isCiuRainSwEnable();

    boolean isCwcFRSwEnable();

    boolean isCwcRLSwEnable();

    boolean isCwcRRSwEnable();

    boolean isCwcSwEnable();

    boolean isEbwEnabled();

    boolean isFWiperActiveSt();

    boolean isLeftChildLocked();

    boolean isLeftDoorHotKeyEnable();

    boolean isRearWiperFault();

    boolean isRearWiperSpeedSwitchOff();

    boolean isRightChildLocked();

    boolean isRightDoorHotKeyEnable();

    boolean isWiperFault();

    boolean isWiperSpeedSwitchOff();

    void moveSteerToExhibitionPos();

    boolean onSteerControl(int keyCode);

    void openCarBonnet();

    void resetChargePort(boolean isLeft);

    void setAllChildLock(boolean lock, boolean needSave);

    void setArsWorkMode(int mode);

    void setAutoPowerOffConfig(boolean status);

    void setChargePortUnlock(int port, boolean unlock);

    void setChildLock(boolean leftSide, boolean lock);

    void setChildLock(boolean leftSide, boolean lock, boolean needSave);

    void setChildModeEnable(boolean enable);

    void setCiuRainSwEnable(boolean enable);

    void setCwcFRSwEnable(boolean isChecked);

    void setCwcRLSwEnable(boolean enable);

    void setCwcRRSwEnable(boolean enable);

    void setCwcSwEnable(boolean isChecked);

    void setCwcSwEnable(boolean isChecked, boolean storeEnable);

    void setDriveAutoLock(boolean enable);

    void setEbwEnable(boolean enable);

    void setInductionLock(boolean enable);

    void setLeaveAutoLock(boolean enable);

    void setLeftDoorHotKey(boolean enable);

    void setNearUnlock(boolean enable);

    void setParkingAutoUnlock(boolean enable);

    void setRearWiperRepairMode(boolean mode);

    void setRightDoorHotKey(boolean enable);

    void setStealthMode(boolean enable);

    void setStopNfcCardSw(boolean enable);

    void setUnlockResponse(int type);

    void setWiperInterval(WiperInterval interval);

    void setWiperRepairMode(boolean mode);
}
