package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface IScenarioController extends IBaseCarController<Callback> {
    public static final String MEDITATION_MODE = "meditation_mode";
    public static final String RET_FAIL_DOOR_OPEN = "doorOpen";
    public static final String RET_FAIL_GEAR_NOT_P = "gearNotP";
    public static final String RET_SUCCESS = "success";
    public static final String SCENARIO_5D_CINEMA_MODE = "5d_cinema_mode";
    public static final String SCENARIO_COSMETIC_SPACE_MODE = "cosmetic_space_mode";
    public static final String SCENARIO_NORMAL = "normal_mode";
    public static final int SCENARIO_STATE_EXITING = 3;
    public static final int SCENARIO_STATE_IDLE = 0;
    public static final int SCENARIO_STATE_INVALID = -1;
    public static final int SCENARIO_STATE_RUNNING = 2;
    public static final int SCENARIO_STATE_STARTING = 1;
    public static final String SCENARIO_TRAILED_MODE = "trailed_mode";
    public static final String SCENARIO_VIPSEAT_MODE = "vipseat_mode";
    public static final String SPACE_CAPSULE_MODE = "spacecapsule_mode";
    public static final String SPACE_CAPSULE_MOVIE_MODE = "spacecapsule_mode_movie";
    public static final String SPACE_CAPSULE_REASON_BATTERY_LOW = "rBatteryLow";
    public static final String SPACE_CAPSULE_REASON_GEAR_NOT_P = "rGearNotP";
    public static final String SPACE_CAPSULE_REASON_IN_DC_CHARGE = "rInDcCharge";
    public static final String SPACE_CAPSULE_SLEEP_MODE = "spacecapsule_mode_sleep";
    public static final String SPACE_CAPSULE_SOURCE_ACTIVITY = "activity";
    public static final String SPACE_CAPSULE_SOURCE_VOICE = "voice";
    public static final String VICE_MEDITATION_MODE = "meditation_passenger_seat_mode";

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onMeditationModeStateChanged(boolean start) {
        }

        default void onSpaceCapsuleCinemaStateChanged(boolean start) {
        }

        default void onSpaceCapsuleSleepStateChanged(boolean start) {
        }
    }

    String getCurrentUserScenario();

    int getUserScenarioStatus(String scenario);

    void registerBinderObserver();

    void reportScenarioState(String scenario, int state);

    String startScenario(boolean enable, String scenario);
}
