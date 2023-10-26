package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface IMcuController extends IBaseCarController<Callback> {
    public static final int MCU_BLE_START_FAILED = 2;
    public static final int MCU_IG_STATUS_IG_OFF = 0;
    public static final int MCU_IG_STATUS_LOCAL_IG_ON = 1;
    public static final int MCU_IG_STATUS_REMOTE_IG_ON = 2;
    public static final int MCU_REMOTE_CONTROL_KEY_START_FAILED = 1;
    public static final int MCU_START_SUCCESSFUL = 0;
    public static final int MCU_WATCH_START_FAILED = 4;
    public static final int REMIND_WARNING_NO = 0;
    public static final int REMIND_WARNING_YES = 1;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onAutoPowerOffConfig(boolean enable) {
        }

        default void onCiuStateChanged(boolean isExisted) {
        }

        default void onIgStatusChanged(int state) {
        }

        default void onKeyOpenFailed(int value) {
        }

        default void onRemindWarning(int state) {
        }

        default void onWelcomeModeChanged(boolean enabled) {
        }
    }

    boolean getCiuState();

    int getIgStatusFromMcu();

    boolean getSeatWelcomeMode();

    boolean isAutoPowerOffEnable();

    boolean isFactoryModeActive();

    void setAutoPowerOffSwitch(boolean enable);

    void setGeoFenceStatus(boolean enter);

    void setSeatWelcomeMode(boolean enable);
}
