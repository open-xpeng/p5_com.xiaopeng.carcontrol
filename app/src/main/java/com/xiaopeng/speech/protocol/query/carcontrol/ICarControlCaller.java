package com.xiaopeng.speech.protocol.query.carcontrol;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface ICarControlCaller extends IQueryCaller {
    int getAtmosphereBrightnessStatus();

    int getAtmosphereColorStatus();

    default int getCapsuleCurrentMode() {
        return 0;
    }

    default int getControlComfortableDrivingModeSupport() {
        return -1;
    }

    int getControlElectricCurtainSupport();

    default int getControlLampSignalSupport() {
        return -1;
    }

    int getControlLowSpeedAnalogSoundSupport();

    int getControlScissorDoorLeftCloseSupport();

    int getControlScissorDoorLeftOpenSupport();

    int getControlScissorDoorLeftRunningSupport();

    int getControlScissorDoorRightCloseSupport();

    int getControlScissorDoorRightOpenSupport();

    int getControlScissorDoorRightRunningSupport();

    int getControlSupportEnergyRecycleReason();

    default float[] getControlWindowsStateSupport() {
        return null;
    }

    int getControlXpedalSupport();

    int getDoorKeyValue();

    int getExtraTrunkStatus();

    int getGuiPageOpenState(String str);

    int getLegHeight();

    int getMirrorStatus();

    default int getNgpStatus() {
        return -1;
    }

    int getStatusChargePortControl(int i, int i2);

    int getSupportCloseTrunk();

    int getSupportOpenTrunk();

    int getSupportPsnSeat();

    int getSupportSeat();

    int getTrunkStatus();

    default int getVipChairStatus() {
        return -1;
    }

    int getWindowStatus();

    int getWiperInterval();

    int isSteeringModeAdjustable();

    boolean isSupportAtmosphere();

    boolean isSupportCloseMirror();

    boolean isSupportControlChargePort(int i, int i2);

    boolean isSupportControlMirror();

    boolean isSupportDrivingMode();

    boolean isSupportEnergyRecycle();

    boolean isSupportUnlockTrunk();

    boolean isTirePressureNormal();
}
