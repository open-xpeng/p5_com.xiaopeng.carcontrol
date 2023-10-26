package com.xiaopeng.carcontrol.viewmodel.cabin;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IWindowDoorViewModel extends IBaseViewModel {
    public static final int BCM_TRUNK_FULL_OPEN_POS_LEVEL_1 = 60;
    public static final int BCM_TRUNK_FULL_OPEN_POS_LEVEL_2 = 70;
    public static final int BCM_TRUNK_FULL_OPEN_POS_LEVEL_3 = 80;
    public static final int BCM_TRUNK_FULL_OPEN_POS_LEVEL_4 = 90;
    public static final int BCM_TRUNK_FULL_OPEN_POS_LEVEL_5 = 100;
    public static final int BCM_TRUNK_WORK_MODE_ST_ANTI_PLAY_MODE = 2;
    public static final int BCM_TRUNK_WORK_MODE_ST_MANUAL_MODE = 3;
    public static final int BCM_TRUNK_WORK_MODE_ST_NORMAL = 0;
    public static final int BCM_TRUNK_WORK_MODE_ST_OTA_PROBITY_MODE = 4;
    public static final int BCM_TRUNK_WORK_MODE_ST_SYSTEM_ERROR = 1;
    public static final int BCM_TRUNK_WORK_MODE_ST_VEHICLE_SPEED_PROBITY_MODE = 5;
    public static final int BCM_WINDOW_ALL = 4;
    public static final int BCM_WINDOW_FRONT = 5;
    public static final int BCM_WINDOW_FRONT_LEFT = 0;
    public static final int BCM_WINDOW_FRONT_RIGHT = 1;
    public static final int BCM_WINDOW_LEFT = 7;
    public static final int BCM_WINDOW_REAR = 6;
    public static final int BCM_WINDOW_REAR_LEFT = 2;
    public static final int BCM_WINDOW_REAR_RIGHT = 3;
    public static final int BCM_WINDOW_RIGHT = 8;
    public static final int DOOR_FL = 0;
    public static final int DOOR_FR = 1;
    public static final int DOOR_RL = 2;
    public static final int DOOR_RR = 3;
    public static final int SDC_CONTROL_CLOSE = 1;
    public static final int SDC_CONTROL_OPEN = 2;
    public static final int SDC_CONTROL_PAUSE = 3;
    public static final int SDC_KEY_CTRL_CFG_DRIVER_DOOR = 0;
    public static final int SDC_KEY_CTRL_CFG_FRONT_DOOR = 1;
    public static final int SDC_SYSTEM_RUNNING_STATUS_CLOSED = 1;
    public static final int SDC_SYS_RUNNING_STATUS_CLOSING = 3;
    public static final int SDC_SYS_RUNNING_STATUS_OPENING = 2;
    public static final int SDC_SYS_RUNNING_STATUS_OPEN_FINISHED = 5;
    public static final int SDC_SYS_RUNNING_STATUS_PREVENT_PLAYING_MODE = 6;
    public static final int SLIDE_DOOR_CONTROL_CLOSE = 2;
    public static final int SLIDE_DOOR_CONTROL_OPEN = 1;
    public static final int SLIDE_DOOR_CONTROL_STOP = 3;
    public static final int SLIDE_DOOR_MODE_MANUAL_MODE = 1;
    public static final int SLIDE_DOOR_MODE_POWER_MODE = 0;
    public static final int SLIDE_DOOR_STATE_BRAKEMODE = 7;
    public static final int SLIDE_DOOR_STATE_CLOSED = 2;
    public static final int SLIDE_DOOR_STATE_CLOSING = 4;
    public static final int SLIDE_DOOR_STATE_DOORFREE = 6;
    public static final int SLIDE_DOOR_STATE_FULL_OPEN = 1;
    public static final int SLIDE_DOOR_STATE_OPENING = 3;
    public static final int SLIDE_DOOR_STATE_STOPPED = 5;
    public static final int SLIDE_DOOR_STATE_UNKNOWN = 0;
    public static final float WINDOW_CLOSE_THRESHOLD = 95.0f;
    public static final int WINDOW_FL = 1;
    public static final int WINDOW_FR = 2;
    public static final float WINDOW_OPEN_THRESHOLD = 3.0f;
    public static final int WINDOW_RL = 3;
    public static final int WINDOW_RR = 4;

    static int convertTrunkFullOpenPosLevel2Pos(int level) {
        if (level != 3) {
            if (level != 4) {
                if (level != 5) {
                    return level != 6 ? 60 : 100;
                }
                return 90;
            }
            return 80;
        }
        return 70;
    }

    int checkLeftSlideDoorCloseAvailability();

    int checkLeftSlideDoorOpenAvailability();

    int checkRightSlideDoorCloseAvailability();

    int checkRightSlideDoorOpenAvailability();

    void closeSunShade();

    void controlFLWin(boolean open);

    void controlFRWin(boolean open);

    void controlLeftSlideDoor(int cmd);

    void controlRLWin(boolean open);

    void controlRRWin(boolean open);

    void controlRearTrunk(int type);

    void controlRearTrunk(TrunkType type);

    void controlRightSlideDoor(int cmd);

    void controlSdc(boolean left, int cmd);

    void controlSdc(boolean left, boolean open);

    void controlWindowAuto(boolean open);

    void controlWindowVent();

    boolean getAutoWindowLockSw();

    boolean getBonnetState();

    boolean getDoorLockState();

    int getElcTrunkPos();

    int getElcTrunkState();

    float getFLWinPos();

    float getFRWinPos();

    int getLeftSdcDoorPos();

    int getLeftSdcSysRunningState();

    int getLeftSlideDoorMode();

    int getLeftSlideDoorState();

    String getMixedDoorState();

    float getRLWinPos();

    float getRRWinPos();

    int getRearTrunkState();

    int getRightSdcDoorPos();

    int getRightSdcSysRunningState();

    int getRightSlideDoorMode();

    int getRightSlideDoorState();

    int getSdcBrakeCloseDoorCfg();

    int getSdcKeyCfg();

    default int getSdcMaxAutoDoorOpeningAngle() {
        return 100;
    }

    boolean getSdcWinAutoDown();

    int getSunShadePos();

    int getTrunkFullOpenPos();

    int getTrunkWorkStatus();

    WindowState getWindowStateValue();

    float[] getWindowsPos();

    boolean[] getWindowsState();

    default void initTrunkState() {
    }

    boolean isAllDoorsClosed();

    boolean isAutoDoorHandleEnabled();

    boolean isCentralLocked();

    boolean isDoorClosed(int doorIdx);

    boolean isHighSpdCloseWinEnabled();

    boolean isLeftSlideDoorLocked();

    boolean isRightSlideDoorLocked();

    boolean isSunShadeBlocked();

    boolean isSunShadeHotProtect();

    boolean isSunShadeInitialized();

    boolean isTrunkClosed();

    boolean isTrunkSensorEnable();

    boolean isWindowInitFailed(int index);

    boolean isWindowInitFailed(int index, boolean tts);

    boolean isWindowLockActive();

    boolean isWindowOpened();

    void openRearTrunk();

    void openSunShade();

    void resetSunShade();

    void setAllWinPos(float position);

    void setAutoDoorHandleEnable(boolean enable);

    void setAutoWindowLockSw(boolean enable);

    void setCentralLock(boolean lock);

    void setDrvWinMovePos(float position);

    void setFLWinPos(float position);

    void setFRWinPos(float position);

    void setFrontWinPos(float position);

    void setHighSpdCloseWin(boolean enable);

    void setLeftSlideDoorMode(int mode);

    void setLeftWinPos(float position);

    void setRLWinPos(float position);

    void setRRWinPos(float position);

    void setRearWinPos(float position);

    void setRightSlideDoorMode(int mode);

    void setRightWinPos(float position);

    void setSdcBrakeCloseDoorCfg(int cfg);

    void setSdcKeyCfg(int cfg);

    default void setSdcMaxAutoDoorOpeningAngle(int angle) {
    }

    void setSdcWinAutoDown(boolean open);

    void setSunShadePos(int pos);

    void setTrunkFullOpenPos(int pos);

    void setTrunkSensorEnable(boolean enable);

    void setWindowLockActive(boolean on);

    void stopSunShade();
}
