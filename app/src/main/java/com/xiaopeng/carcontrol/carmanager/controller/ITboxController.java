package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface ITboxController extends IBaseCarController<Callback> {
    public static final int NETWORK_TYPE_END = 4;
    public static final int NETWORK_TYPE_START = 3;
    public static final int SOLDIER_SW_LEVEL_1 = 1;
    public static final int SOLDIER_SW_LEVEL_2 = 2;
    public static final int SOLDIER_SW_LEVEL_3 = 3;
    public static final int SOLDIER_SW_OFF = 0;
    public static final int TBOX_AC_CHARGE_UNLOCK_ST_APPOINTMENT = 2;
    public static final int TBOX_AC_CHARGE_UNLOCK_ST_UNLOCK = 1;
    public static final int TBOX_IOT_BUS_TYPE_AC_CHARGER = 1;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onAcChargeUnlockStChanged(int state) {
        }

        default void onAutoPowerOffConfigStatusChanged(boolean status) {
        }

        default void onIotBusinessTypeChanged(int state) {
        }

        default void onSoldierStateChanged(int status) {
        }
    }

    boolean getAutoPowerOffConfig();

    int getIotBusinessType();

    int getSoldierSwStatus();

    int getTboxAcChargerSt();

    void setAutoPowerOffConfig(boolean status);

    void setNetWorkType(int type);

    void setSoldierSw(int status);
}
