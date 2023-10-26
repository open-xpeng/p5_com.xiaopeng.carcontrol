package com.xiaopeng.carcontrol.viewmodel.scenario;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IScenarioViewModel extends IBaseViewModel {
    public static final String REASON_BATTERY_LOW = "rBatteryLow";
    public static final String REASON_GEAR_NOT_P = "rGearNotP";
    public static final String REASON_IN_DC_CHARGE = "rInDcCharge";
    public static final String RET_FAIL_DOOR_OPEN = "doorOpen";
    public static final String RET_FAIL_GEAR_NOT_P = "gearNotP";
    public static final int SCENARIO_STATE_IDLE = 0;
    public static final int SCENARIO_STATE_RUNNING = 2;

    String getCurrentUserScenario();

    int getUserScenarioStatus(String scenario);

    void registerBinderObserver();

    void reportScenarioStatus(String scenario, int state);

    String startScenario(boolean start, String scenario);
}
