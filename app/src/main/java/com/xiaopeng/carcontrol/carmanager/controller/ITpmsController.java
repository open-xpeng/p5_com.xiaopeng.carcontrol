package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface ITpmsController extends IBaseCarController<Callback> {
    public static final int TPMS_HIGH_PRESSURE_WARNING = 4;
    public static final int TPMS_LOW_PRESSURE_WARNING = 2;
    public static final int TPMS_NO_WARING = 0;
    public static final int TPMS_PR_FL = 1;
    public static final int TPMS_PR_FR = 2;
    public static final int TPMS_PR_RL = 3;
    public static final int TPMS_PR_RR = 4;
    public static final int TPMS_TIRE_TYPE_FAIL = 3;
    public static final int TPMS_TIRE_TYPE_FIXED = 2;
    public static final int TPMS_TIRE_TYPE_FIXING = 1;
    public static final int TPMS_TIRE_TYPE_NOT_FIX = 0;
    public static final int TPMS_WARNING_INVALID = 7;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        void onTpmsAbnormalWarningStateChanged(boolean abnormal);

        void onTpmsCalibrationStateChanged(int state);

        void onTpmsPressureChanged(int position, float pressure);

        void onTpmsSystemFaultStateChanged(boolean fault);

        void onTpmsTempChanged(int[] temp);

        void onTpmsTempWarningStateChanged(boolean[] states);

        void onTpmsWarningStateChanged(int[] states);
    }

    void calibrateTyrePressure();

    int getTpmsCalibrateState();

    int[] getTpmsTemperature();

    int[] getTpmsWarningState();

    float getTyrePressure(int position);

    boolean[] isTpmsSensorWarning();

    boolean[] isTpmsTempWarning();
}
