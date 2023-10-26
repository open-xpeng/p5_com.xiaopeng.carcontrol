package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public interface IBcmController extends ILifeCycle, com.xiaopeng.lib.framework.moduleinterface.carcontroller.v2extend.IBcmController {
    public static final int ATWS_STATUS_DEFENCE = 0;
    public static final int ATWS_STATUS_PRE_DEFENCE = 1;
    public static final int ATWS_STATUS_UNDEFENCE = 2;
    public static final int BCM_DOOR_CLOSE = 0;
    public static final int BCM_DOOR_OPEN = 1;
    public static final int BCM_LAMP_LOCATION = 1;
    public static final int BCM_LAMP_STATE_AUTO = 3;
    public static final int BCM_LAMP_STATE_CLOSE = 0;
    public static final int BCM_LAMP_STATE_NEAR = 2;
    public static final int BCM_LIGHT_ALL = 0;
    public static final int BCM_LIGHT_ME_HOME_LOW_BEAM = 1;
    public static final int BCM_LIGHT_ME_HOME_LOW_BEAM_PARKING = 2;
    public static final int BCM_LIGHT_ME_HOME_NOT_ACTIVE = 0;
    public static final int BCM_LIGHT_OUTSIDE = 1;
    public static final int BCM_REAR_TRUNK_CLOSE = 0;
    public static final int BCM_REAR_TRUNK_OPEN = 2;
    public static final int BCM_REAR_TRUNK_STOP = 1;
    public static final int BCM_REAR_VIEW_MIRROR_TYPE_CLOSE = 0;
    public static final int BCM_REAR_VIEW_MIRROR_TYPE_OPEN = 1;
    public static final int BCM_SEAT_TYPE_ANGLE = 3;
    public static final int BCM_SEAT_TYPE_HEIGHT = 2;
    public static final int BCM_SEAT_TYPE_LEVEL_LOCATION = 1;
    public static final int BCM_UNLOCK_LIGHT_AND_HORN = 0;
    public static final int BCM_UNLOCK_RESPONSE_LIGHT = 1;
    public static final int BCM_WHEEL_DEFINED_BUTTON_HIGH = 3;
    public static final int BCM_WHEEL_DEFINED_BUTTON_LOW = 1;
    public static final int BCM_WHEEL_DEFINED_BUTTON_MIDDLE = 2;
    public static final int BCM_WINDOW_TYPE_DOWN_AUTO = 4;
    public static final int BCM_WINDOW_TYPE_DOWN_MANUALLY = 3;
    public static final int BCM_WINDOW_TYPE_INVALID = 0;
    public static final int BCM_WINDOW_TYPE_UP_AUTO = 2;
    public static final int BCM_WINDOW_TYPE_UP_MANUALLY = 1;
    public static final int BCM_WIPER_INTERNAL_GEAR_1 = 3;
    public static final int BCM_WIPER_INTERNAL_GEAR_2 = 2;
    public static final int BCM_WIPER_INTERNAL_GEAR_3 = 1;
    public static final int BCM_WIPER_INTERNAL_GEAR_4 = 0;
    public static final int HEADLAMPS_STATUS_LAMP_AUTO = 3;
    public static final int HEADLAMPS_STATUS_LAMP_FAR = 4;
    public static final int HEADLAMPS_STATUS_LAMP_LOCATION = 1;
    public static final int HEADLAMPS_STATUS_LAMP_NEAR = 2;
    public static final int HEADLAMPS_STATUS_LAMP_OFF = 0;
    public static final int OLED_STATUS_LIGHT_ALL = 0;
    public static final int OLED_STATUS_LIGHT_OUTSIDE = 1;
    public static final int REARVIEW_AUTODOWN_CFG_CLOSE = 1;
    public static final int REARVIEW_AUTODOWN_CFG_OPEN = 2;
    public static final int REAR_TRUNK_STATUS_CLOSED = 0;
    public static final int REAR_TRUNK_STATUS_OPENED = 2;
    public static final int REAR_TRUNK_STATUS_OPENING = 1;
    public static final int SEAT_MOVE_DIRECTION_BACKWARD = 2;
    public static final int SEAT_MOVE_DIRECTION_FORWARD = 1;
    public static final int SEAT_MOVE_DIRECTION_NOT_MOVE = 0;
    public static final int UNLOCK_STATUS_LIGHT = 1;
    public static final int UNLOCK_STATUS_LIGHT_AND_HORN = 0;
    public static final int WIN_LOCK_STATUS_ACTIVE = 1;
    public static final int WIN_LOCK_STATUS_INACTIVE = 0;
    public static final int WIPER_INTERMITTENT_ACTIVE = 1;
    public static final int WIPER_INTERMITTENT_NOT_ACTIVE = 0;

    /* loaded from: classes2.dex */
    public static class AtwsStateEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class BCMBackDefrostModeEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class BCMBackMirrorHeatModeEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class BCMDriveDoorEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class BCMSeatBlowLevelEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class BCMSeatFrHeatLevelEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class BCMSeatHeatLevelEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class BcmFrontWiperOutputSTEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class BcmFrontWiperOutputStatusEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class ChairDirectionEventMsg extends AbstractEventMsg<int[]> {
    }

    /* loaded from: classes2.dex */
    public static class ChairLocationEventMsg extends AbstractEventMsg<int[]> {
    }

    /* loaded from: classes2.dex */
    public static class ChairWelcomeModeEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class ChargeGunLockStEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class ChargePortEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class DoorLockStateEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class DoorsStateEventMsg extends AbstractEventMsg<int[]> {
    }

    /* loaded from: classes2.dex */
    public static class DriveAutoLockEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class DriveSeatStateEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class DriverBeltWarningEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class ElectricSeatBeltEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class EmergencyBrakeWarningEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class FarLampEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class FrontBonnetStatusEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class HeadLampGroupEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class InternalLightEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class LightMeHomeEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class LocationLampStateEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class NearLampStateEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class OledEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class ParkingAutoUnlockEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class PollingOpenCfgEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class PowerModeStateEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class RearFogLampEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class RearSeatBeltWarningEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class RearViewAutoDownCfgEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class TrunkStateEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class UnlockResponseEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class WelcomeModeBackStatusEventMsg extends AbstractEventMsg<Boolean> {
    }

    /* loaded from: classes2.dex */
    public static class WinLockStateEventMsg extends AbstractEventMsg<Integer> {
    }

    /* loaded from: classes2.dex */
    public static class WindowsStateEventMsg extends AbstractEventMsg<float[]> {
    }

    /* loaded from: classes2.dex */
    public static class WiperIntermittentModeEventMsg extends AbstractEventMsg<Integer> {
    }

    int getATWSState() throws Exception;

    boolean getBCMBackDefrostMode() throws Exception;

    @Deprecated
    boolean getBCMBackMirrorHeatMode() throws Exception;

    int getBCMIgStatus() throws Exception;

    int getBCMSeatBlowLevel() throws Exception;

    int getBCMSeatHeatLevel() throws Exception;

    int getBcmFrontWiperOutputStatus() throws Exception;

    int[] getChairDirection() throws Exception;

    int[] getChairLocationValue() throws Exception;

    boolean getChairWelcomeMode() throws Exception;

    boolean getChargeGunLockSt() throws Exception;

    boolean getChargePortStatus() throws Exception;

    boolean getDoorLockState() throws Exception;

    int[] getDoorsState() throws Exception;

    boolean getDriveAutoLock() throws Exception;

    int getDriveSeatState() throws Exception;

    boolean getDriverBeltWarning() throws Exception;

    boolean getElectricSeatBelt() throws Exception;

    boolean getEmergencyBrakeWarning() throws Exception;

    boolean getFarLampState() throws Exception;

    boolean getFrontBonnetStatus() throws Exception;

    int getHeadLampGroup() throws Exception;

    boolean getInternalLight() throws Exception;

    int getLightMeHome() throws Exception;

    boolean getLocationLampState() throws Exception;

    int[] getMsmErrorInfo() throws Exception;

    boolean getNearLampState() throws Exception;

    int getOled() throws Exception;

    boolean getParkingAutoUnlock() throws Exception;

    boolean getPollingOpenCfg() throws Exception;

    boolean getRearFogLamp() throws Exception;

    boolean getRearSeatBeltWarning() throws Exception;

    Integer getRearViewAutoDownCfg() throws Exception;

    boolean getSeatErrorState() throws Exception;

    int getSeatFrHeatLevel() throws Exception;

    int getTrunk() throws Exception;

    int getUnlockResponse() throws Exception;

    boolean getWelcomeModeBackStatus() throws Exception;

    int getWinLockStatus() throws Exception;

    float[] getWindowsState() throws Exception;

    int getWiperIntermittentMode() throws Exception;

    boolean isAirbagFault() throws Exception;

    boolean isHighBeamFail() throws Exception;

    boolean isLeftTurnLampFail() throws Exception;

    boolean isLowBeamFail() throws Exception;

    boolean isRightTurnLampFail() throws Exception;

    boolean isSystemError() throws Exception;

    boolean isWasherFluidWarning() throws Exception;

    void setAllWindowManualOrAuto(int i) throws Exception;

    void setBCMBackDefrostMode() throws Exception;

    void setBCMBackDefrostMode(boolean z) throws Exception;

    @Deprecated
    void setBCMBackMirrorHeatMode() throws Exception;

    @Deprecated
    void setBCMBackMirrorHeatMode(boolean z) throws Exception;

    void setBCMSeatBlowLevel() throws Exception;

    void setBCMSeatBlowLevel(int i) throws Exception;

    void setBCMSeatHeatLevel() throws Exception;

    void setBCMSeatHeatLevel(int i) throws Exception;

    void setBackWindows(boolean z);

    void setChairPositionEnd() throws Exception;

    void setChairPositionStart(int i, int i2, int i3) throws Exception;

    void setChairSlowlyAhead(int i) throws Exception;

    void setChairSlowlyBack(int i) throws Exception;

    void setChairSlowlyEnd(int i) throws Exception;

    void setChairWelcomeMode(boolean z) throws Exception;

    void setChargeGunLock() throws Exception;

    void setChargingPortUnlock() throws Exception;

    void setCopilotWindowAuto(boolean z) throws Exception;

    void setDoorLock() throws Exception;

    void setDoorUnlocked() throws Exception;

    void setDriveAutoLock(boolean z) throws Exception;

    void setDriverWindowAuto(boolean z) throws Exception;

    void setElectricSeatBelt(boolean z) throws Exception;

    void setEmergencyBrakeWarning(boolean z) throws Exception;

    void setFactoryOledData(byte[] bArr) throws Exception;

    void setFactoryOledDisplayMode(int i) throws Exception;

    void setFrontWindows(boolean z);

    void setHazardLight(boolean z) throws Exception;

    void setHeadLampGroup(int i) throws Exception;

    void setInternalLight(boolean z) throws Exception;

    void setLeftBackWindow(boolean z) throws Exception;

    void setLightMeHome(boolean z) throws Exception;

    void setOled(int i) throws Exception;

    void setParkingAutoUnlock(boolean z) throws Exception;

    void setPollingOpenCfg(boolean z) throws Exception;

    void setRearFogLamp(boolean z) throws Exception;

    void setRearSeatBeltWarning(boolean z) throws Exception;

    void setRearViewAutoDownCfg(int i) throws Exception;

    void setRearViewMirror(int i) throws Exception;

    void setRightBackWindow(boolean z) throws Exception;

    void setSeatFrHeatLevel(int i) throws Exception;

    void setTrunk(int i) throws Exception;

    void setUnlockResponse(int i) throws Exception;

    void setVentilate() throws Exception;

    void setWheelDefinedButton(int i) throws Exception;

    void setWiperInterval(int i) throws Exception;
}
