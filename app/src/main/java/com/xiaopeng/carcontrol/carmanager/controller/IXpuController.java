package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.bean.xpilot.map.CngpMapItem;
import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;
import java.util.List;

/* loaded from: classes.dex */
public interface IXpuController extends IBaseCarController<Callback> {
    public static final int AS_LOCK_SCENARIO_CAM_CALIB_REQUEST = 1;
    public static final int AS_LOCK_SCENARIO_LIDAR_CALIB_REQUEST = 2;
    public static final int AS_LOCK_SCENARIO_MRR_CALIB_REQUEST = 3;
    public static final int AS_LOCK_SCENARIO_NO_REQUEST = 0;
    public static final int AS_TAR_LVL_NO_REQUEST = 0;
    public static final int LSS_SEN_ST_HIGH = 2;
    public static final int LSS_SEN_ST_LOW = 1;
    public static final int LSS_SEN_ST_MEDIUM = 0;
    public static final int NEDC_OFF = 0;
    public static final int NEDC_ON = 1;
    public static final int NEDC_STATE_OFF = 0;
    public static final int NEDC_STATE_ON = 1;
    public static final String SETTING_KEY_NEDC_SW = "xp_carcontrol_nedc_sw";
    public static final int XPU_CNGP_MODE = 8;
    public static final int XPU_ISLC_DRIVER_SET_AUTOMATIC = 3;
    public static final int XPU_ISLC_DRIVER_SET_ERROR = 0;
    public static final int XPU_ISLC_DRIVER_SET_MANUAL = 2;
    public static final int XPU_ISLC_DRIVER_SET_OFF = 1;
    public static final int XPU_L4_TO_L2_MODE = 9;
    public static final int XPU_NGP_MODE_INDX_NGP_MODE = 5;
    public static final int XPU_SIMPLE_SAS_SW_AUTOMATIC = 3;
    public static final int XPU_SIMPLE_SAS_SW_MANUAL = 2;
    public static final int XPU_SIMPLE_SAS_SW_OFF = 1;
    public static final int XPU_SLWF_SLIF_OFF = 0;
    public static final int XPU_SLWF_SLIF_ON = 1;
    public static final int XPU_STATUS_FAILURE = 2;
    public static final int XPU_STATUS_START_UP_FAILURE = 5;
    public static final int XPU_STATUS_START_UP_IN_PROGRESS = 3;
    public static final int XPU_STATUS_TURN_OFF_IN_PROGRESS = 4;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void handleLccBarriersChanged(int state) {
        }

        default void handleLccTrafficLightChanged(int state) {
        }

        default void onCityNgpSwChanged(int state) {
        }

        default void onCngpMapCtrlResponse(List<CngpMapItem> list) {
        }

        default void onCngpMapFinishNotify(int id, int status) {
        }

        default void onLssSenChanged(int sensitivity) {
        }

        void onNedcStateChanged(int state);

        default void onNgpCustomSpeedKphChanged(int value) {
        }

        default void onNgpCustomSpeedModeChanged(int mode) {
        }

        default void onNgpCustomSpeedPercentChanged(int value) {
        }

        default void onNgpOvertakeChanged(int mode) {
        }

        default void onNgpPreferLaneChanged(int mode) {
        }

        default void onNraStateChanged(int state) {
        }

        default void onNraSwChanged(boolean enabled) {
        }

        default void onRaebSwChanged(int state) {
        }

        default void onRemoteCarCallStChanged(int state) {
        }

        default void onSimpleSasStChanged(int state) {
        }

        default void onSoundSwChanged(boolean enable) {
        }

        default void onVoiceSwChanged(boolean enable) {
        }

        default void onXpuConnectedChanged(boolean connected) {
        }
    }

    void deleteCity(int id);

    void downloadCity(int id);

    void getAllCities();

    int getAsLockScenario();

    int getAsTargetMaxHeightRequest();

    int getAsTargetMinHeightRequest();

    int getCityNgpState();

    int getCngpModeStatus();

    boolean getCngpSafeExamResult(String uid);

    int getLccCrossBarriersSw();

    int getLccTrafficLightSw();

    int getLssSensitivity();

    int getNedcSwitchStatus();

    int getNgpCustomSpeedKph();

    int getNgpCustomSpeedMode();

    int getNgpCustomSpeedPercent();

    int getNgpOvertakeMode();

    int getNgpPreferLaneCfg();

    int getNraState();

    boolean getNraSw();

    int getRaebState();

    int getRemoteCarCallSw();

    int getSimpleSasSw();

    boolean getSoundSw();

    boolean getTurnAssistantSw();

    boolean getVoiceSw();

    boolean getXngpSafeExamResult(String uid);

    boolean getXpuConnected();

    void setCityNgpSw(boolean enable);

    void setCngpSafeExamResult(String uid, boolean result);

    void setLccCrossBarriersSw(boolean enable);

    void setLccTrafficLightSw(boolean enable);

    void setLssSensitivity(int value);

    void setMrrEnable(boolean enable);

    void setNedcSwitch(boolean active);

    void setNgpCustomSpeedKph(int value);

    void setNgpCustomSpeedMode(int mode);

    void setNgpCustomSpeedPercent(int value);

    void setNgpOvertakeMode(int mode);

    void setNgpPreferLaneCfg(int mode);

    void setNraState(int state);

    void setNraSwEnable(boolean enable);

    void setRaebState(boolean enable);

    void setRemoteCalCallSw(boolean enable);

    void setSimpleSasSw(int mode);

    void setSoundSw(boolean enable);

    void setTurnAssistantEnable(boolean enable);

    void setVoiceSw(boolean enable);

    void setXngpSafeExamResult(String uid, boolean result);
}
