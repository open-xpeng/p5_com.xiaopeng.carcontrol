package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface IScuController extends IBaseCarController<Callback> {
    public static final int AUTO_PARK_WARNING_SOUND = 0;
    public static final int AUTO_PARK_WARNING_TTS = 1;
    public static final int AUTO_PILOT_TTS_OFF = 0;
    public static final int AUTO_PILOT_TTS_ON = 1;
    public static final int DSM_ST_FAULT = 4;
    public static final int DSM_ST_NORMAL = 1;
    public static final int DSM_ST_OFF = 0;
    public static final int DSM_ST_STARTING = 6;
    public static final String KEY_AUTO_PILOT_TTS = "AutoPilotTts";
    public static final int MEMORY_PARK_OFF = 1;
    public static final int MEMORY_PARK_ON = 2;
    public static final int NGP_CHANGE_LANE_OFF = 0;
    public static final int NGP_CHANGE_LANE_RADICAL = 2;
    public static final int NGP_CHANGE_LANE_SENSITIVE = 1;
    public static final int NGP_CHANGE_LANE_STANDARD = 1;
    public static final int NGP_CMD_OFF = 2;
    public static final int NGP_CMD_ON = 1;
    public static final int NGP_FEATURE_OFF = 0;
    public static final int NGP_FEATURE_ON = 1;
    public static final int NGP_MODE_STATUS_DRIVER_MODE = 1;
    public static final int NGP_MODE_STATUS_NGP_MODE = 5;
    public static final String NGP_REMIND_MODE = "ngp_broadcast_type";
    public static final int NGP_REMIND_SOUND = 0;
    public static final int NGP_REMIND_SOUND_CAR = 1;
    public static final int NGP_REMIND_TTS = 1;
    public static final int NGP_REMIND_TTS_CAR = 0;
    public static final String NGP_VOICE_CHANGE_LANE = "ngp_voice_lc";
    public static final int REAR_MIRROR_FOLD_STATE = 1;
    public static final int REAR_MIRROR_NO_OPERATION = 0;
    public static final int REAR_MIRROR_UNFOLD_STATE = 2;
    public static final int SCU_ACC_ACTIVE = 3;
    public static final int SCU_ACC_BRAKE_ONLY = 4;
    public static final int SCU_ACC_OFF = 0;
    public static final int SCU_ACC_OVERRIDE = 5;
    public static final int SCU_ACC_PASSIVE = 1;
    public static final int SCU_ACC_PERMANENT_FAILURE = 9;
    public static final int SCU_ACC_STANDACTIVE = 6;
    public static final int SCU_ACC_STANDBY = 2;
    public static final int SCU_ACC_STANDWAIT = 7;
    public static final int SCU_ACC_TEMPORARY_FAILURE = 8;
    public static final int SCU_ALC_SW_ACTIVE = 1;
    public static final int SCU_ALC_SW_CANCEL = 4;
    public static final int SCU_ALC_SW_FAILURE = 7;
    public static final int SCU_ALC_SW_FINISH = 5;
    public static final int SCU_ALC_SW_INACTIVE = 0;
    public static final int SCU_ALC_SW_OFF = 6;
    public static final int SCU_ALC_SW_WAIT = 2;
    public static final int SCU_ALC_SW_WAIT_TIMEOUT = 3;
    public static final int SCU_BUTTON_OFF = 0;
    public static final int SCU_BUTTON_ON = 1;
    public static final int SCU_COMMONFB_SW_ACTIVE = 3;
    public static final int SCU_COMMONFB_SW_NO_FAULT = 6;
    public static final int SCU_COMMONFB_SW_OFF = 0;
    public static final int SCU_COMMONFB_SW_PASSIVE = 1;
    public static final int SCU_COMMONFB_SW_PERMANENT_FAIL = 5;
    public static final int SCU_COMMONFB_SW_STANDBY = 2;
    public static final int SCU_COMMONFB_SW_TEMPORARY_FAIL = 4;
    public static final int SCU_FCWAEB_BLINDNESS = 7;
    public static final int SCU_FCWAEB_INITIALIZATION_ST = 8;
    public static final int SCU_FCWAEB_NO_FAULT = 5;
    public static final int SCU_FCWAEB_OFF_BY_RADAR_EMISSION_OFF = 9;
    public static final int SCU_FCW_SW_OFF = 0;
    public static final int SCU_FCW_SW_ON = 1;
    public static final int SCU_FCW_SW_PERMANENT_BLOCK = 4;
    public static final int SCU_FCW_SW_PERMANENT_FAIL = 3;
    public static final int SCU_FCW_SW_TEMPORARY_FAIL = 2;
    public static final int SCU_IHB_SW_ACTIVE = 3;
    public static final int SCU_IHB_SW_OFF = 0;
    public static final int SCU_IHB_SW_PASSIVE = 1;
    public static final int SCU_IHB_SW_PERMANENT_FAIL = 5;
    public static final int SCU_IHB_SW_STANDBY = 2;
    public static final int SCU_IHB_SW_TEMPORARY_FAIL = 4;
    public static final int SCU_ISLA_CONFIRM_MODE_AUTO = 1;
    public static final int SCU_ISLA_CONFIRM_MODE_USER = 2;
    public static final int SCU_ISLA_DRIVER_CONFIRM_FAIL = 0;
    public static final int SCU_ISLA_DRIVER_CONFIRM_NO = 1;
    public static final int SCU_ISLA_DRIVER_CONFIRM_OK = 2;
    public static final int SCU_ISLA_SPD_RANGE_HI = 2;
    public static final int SCU_ISLA_SPD_RANGE_LO = 1;
    public static final int SCU_ISLA_ST_ACTIVE = 3;
    public static final int SCU_ISLA_ST_OFF = 0;
    public static final int SCU_ISLA_ST_PERM_FAIL = 2;
    public static final int SCU_ISLA_ST_TEMP_FAIL = 1;
    public static final int SCU_ISLA_SW_ISLA = 2;
    public static final int SCU_ISLA_SW_OFF = 0;
    public static final int SCU_ISLA_SW_SLA = 1;
    public static final int SCU_ISLC_SW_ACTIVE = 3;
    public static final int SCU_ISLC_SW_NO_FAULT = 8;
    public static final int SCU_ISLC_SW_OFF = 0;
    public static final int SCU_ISLC_SW_OVERRIDE = 5;
    public static final int SCU_ISLC_SW_PASSIVE = 1;
    public static final int SCU_ISLC_SW_PERMANENT_FAIL = 7;
    public static final int SCU_ISLC_SW_STANDBY = 2;
    public static final int SCU_ISLC_SW_STANDWAIT = 4;
    public static final int SCU_ISLC_SW_TEMPORARY_FAIL = 6;
    public static final int SCU_LCC_SW_ACTIVE = 3;
    public static final int SCU_LCC_SW_OFF = 0;
    public static final int SCU_LCC_SW_PASSIVE = 1;
    public static final int SCU_LCC_SW_PERMANENT_FAIL = 5;
    public static final int SCU_LCC_SW_STANDBY = 2;
    public static final int SCU_LCC_SW_TEMPORARY_FAIL = 4;
    public static final int SCU_LDW_BUTTON_ON = 1;
    public static final int SCU_LKA_BUTTON_ON = 2;
    public static final int SCU_LKA_STATE_OFF_BY_TRAILER_MODE = 10;
    public static final int SCU_LKA_ST_ACTIVE = 7;
    public static final int SCU_LKA_ST_OFF = 4;
    public static final int SCU_LKA_ST_PASSIVE = 5;
    public static final int SCU_LKA_ST_PERMANENT_FAIL = 9;
    public static final int SCU_LKA_ST_STANDBY = 6;
    public static final int SCU_LKA_ST_TEMPORARY_FAIL = 8;
    public static final int SCU_LSS_ALL = 3;
    public static final int SCU_LSS_LDW = 1;
    public static final int SCU_LSS_LKA = 2;
    public static final int SCU_LSS_OFF = 0;
    public static final int SCU_OPERATION_TIP24 = 24;
    public static final int SCU_OPERATION_TIP3 = 3;
    public static final int SCU_OPERATION_TIP8 = 8;
    public static final int SCU_OTA_TAG_0 = 0;
    public static final int SCU_OTA_TAG_1 = 1;
    public static final int SCU_OTA_TAG_2 = 2;
    public static final int SCU_OTA_TAG_3 = 3;
    public static final int SCU_PARK_CTRL_TYPE_DOUBLE = 1;
    public static final int SCU_PARK_CTRL_TYPE_LONG_PRESS = 2;
    public static final int SCU_PARK_SOUND_TYPE_EFFECT = 1;
    public static final int SCU_PARK_SOUND_TYPE_VOICE = 2;
    public static final int SCU_RESPONSE_BLINDNESS = 11;
    public static final int SCU_RESPONSE_FAIL = 2;
    public static final int SCU_RESPONSE_INIT = 8;
    public static final int SCU_RESPONSE_NO_FAULT = 5;
    public static final int SCU_RESPONSE_OFF = 0;
    public static final int SCU_RESPONSE_OFF_MRR = 10;
    public static final int SCU_RESPONSE_OFF_TTM = 9;
    public static final int SCU_RESPONSE_ON = 1;
    public static final int SCU_RESPONSE_PERMANENT_ERROR = 4;
    public static final int SCU_RESPONSE_TEMP_ERROR = 3;
    public static final int SCU_RESPONSE_UNAVAILABLE = 6;
    public static final int SCU_SLIF_ST_ACTIVE = 3;
    public static final int SCU_SLIF_ST_OFF = 0;
    public static final int SCU_SLIF_ST_OVERRIDE = 5;
    public static final int SCU_SLIF_ST_PASSIVE = 1;
    public static final int SCU_SLIF_ST_PERMANENT_FAIL = 7;
    public static final int SCU_SLIF_ST_STANDBY = 2;
    public static final int SCU_SLIF_ST_STANDWAIT = 4;
    public static final int SCU_SLIF_ST_TEMPORARY_FAIL = 6;
    public static final int SCU_SW_FAIL = 2;
    public static final int SCU_SW_OFF = 0;
    public static final int SCU_SW_ON = 1;
    public static final int SCU_UNABLE_TO_ACTIVATE_ALC = 8;
    public static final int SCU_UNABLE_TO_ACTIVATE_LCC = 6;
    public static final int SDC_DOOR_OBSTACLE_DETECTION_DANGER = 1;
    public static final int SDC_DOOR_OBSTACLE_DETECTION_SAFETY = 2;
    public static final int SDC_DOOR_OBSTACLE_DETECTION_UNKNOWN = 3;
    public static final int SDC_RADAR_DISPLAY_LEVEL_1 = 1;
    public static final int SDC_RADAR_DISPLAY_LEVEL_2 = 2;
    public static final int SDC_TIPS_DISABLE = 0;
    public static final int SDC_TIPS_ENABLE = 1;
    public static final int SDC_TTS_ENABLE = 1;
    public static final int SPECIAL_SAS_CONTROL = 3;
    public static final int SPECIAL_SAS_CONTROL_FAIL = 5;
    public static final int SPECIAL_SAS_DISPLAY = 1;
    public static final int SPECIAL_SAS_FAIL = 4;
    public static final int SPECIAL_SAS_OFF = 0;
    public static final int SPECIAL_SAS_WARNING = 2;
    public static final String SYS_AUTO_PARK_WARNING = "persist.sys.auto_park_warning";
    public static final int VPA_ST_DISABLE = 2;
    public static final int VPA_ST_ENABLE = 1;
    public static final int VPA_ST_INIT = 0;
    public static final int VPA_ST_NOT_ACTIVATED = 3;
    public static final int VPA_ST_OFF_MRR = 5;
    public static final int VPA_ST_OFF_TTM = 4;
    public static final int XPILOT_ST_INIT = 0;
    public static final int XPILOT_ST_NOT_ACTIVATED = 3;
    public static final int XPILOT_ST_OFF = 2;
    public static final int XPILOT_ST_ON = 1;
    public static final int XPU_SCU_LSS_ALARM_ENABLED = 1;
    public static final int XPU_SCU_LSS_ALL_ENABLED = 3;
    public static final int XPU_SCU_LSS_BREAK_DOWN = 4;
    public static final int XPU_SCU_LSS_CLOSED = 0;
    public static final int XPU_SCU_LSS_DEVIATION_ENABLED = 2;
    public static final String XP_TTS_BROADCAST_TYPE = "tts_broadcast_type";
    public static final int XP_TTS_BROADCAST_TYPE_CONCISE = 1;
    public static final int XP_TTS_BROADCAST_TYPE_DETAIL = 0;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onAccStateChanged(int state) {
        }

        default void onAccStateForMirrorChanged(int state) {
        }

        default void onAutoParkSwChanged(int state) {
        }

        default void onBsdStateChanged(int state) {
        }

        default void onDowSwChanged(int state) {
        }

        default void onDsmSwStateChanged(int state) {
        }

        default void onElkSwChanged(int state) {
        }

        default void onFcwSenChanged(int state) {
        }

        default void onFcwSwChanged(int state) {
        }

        default void onIhbStateChanged(int state) {
        }

        default void onIslaConfirmModeChanged(int confirmMode) {
        }

        default void onIslaSpdRangeChanged(int spdRange) {
        }

        default void onIslaStateChanged(int state) {
        }

        default void onIslcSwChanged(int state) {
        }

        default void onKeyParkSwChanged(int state) {
        }

        default void onLccStateChanged(int state) {
        }

        default void onLcsStateChanged(int state) {
        }

        default void onLdwSwChanged(int state) {
        }

        default void onLkaSwChanged(int state) {
        }

        default void onLssSwChanged(int state) {
        }

        default void onMemParkSwChanged(int state) {
        }

        default void onMirrorCtrl(int ctrl) {
        }

        default void onNgpChangeLaneModeChanged(int mode) {
        }

        default void onNgpQuickLaneChanged(int state) {
        }

        default void onNgpRemindModeChanged(int mode) {
        }

        default void onNgpSafeExamResultChanged(boolean result) {
        }

        default void onNgpStateChanged(int state) {
        }

        default void onNgpTipWindowChanged(int state) {
        }

        default void onNgpTruckOffsetChanged(int state) {
        }

        default void onPhoneParkSwChanged(int state) {
        }

        default void onRctaSwChanged(int state) {
        }

        default void onRcwSwChanged(int state) {
        }

        default void onScuOperationTips(int state) {
        }

        default void onSdcLeftRadarDisLevelChanged(int level) {
        }

        default void onSdcRightRadarDisLevelChanged(int level) {
        }

        default void onShowSdcNarrowSpaceTips(int state) {
        }

        default void onShowSdcObstacleTips(int state) {
        }

        default void onSlaStateChanged(int state) {
        }

        default void onXpuXPilotStateChanged(int state) {
        }
    }

    int getAccState();

    int getAccStateForMirror();

    int getAlcState();

    boolean getApaSafeExamResult(String uid);

    int getAutoParkSw();

    int getBsdState();

    int getDowState();

    int getDsmSwStatus();

    int getElkState();

    int getFcwSensitivity();

    int getFcwState();

    int getIhbState();

    int getIslaConfirmMode();

    int getIslaSpdRange();

    int getIslaState();

    int getIslcState();

    int getKeyParkSw();

    boolean getLccSafeExamResult(String uid);

    int getLccState();

    int getLccWorkSt();

    int getLdwState();

    int getLkaState();

    int getLssMode();

    boolean getMemParkExamResult(String uid);

    int getMemoryParkSw();

    int getNgpChangeLaneMode();

    int getNgpFastLaneSw();

    int getNgpModeStatus();

    int getNgpRemindMode();

    boolean getNgpSafeExamResult(String uid);

    int getNgpState();

    int getNgpTipsWinSw();

    int getNgpTruckOffsetSw();

    int getNgpVoiceChangeLane();

    int getOldIslaSw();

    int getPhoneParkSw();

    int getRctaState();

    int getRcwState();

    boolean getRemoteCameraSw();

    int getScuRearMirrorControlState();

    int getSdcLeftRadarDisLevel();

    int getSdcRightRadarDisLevel();

    int getSlaState();

    boolean getSuperLccExamResult(String uid);

    boolean getSuperVpaExamResult(String uid);

    int getTtsBroadcastType();

    int getXpuXpilotState();

    boolean isAutoPilotNeedTts();

    boolean isFirstOpenXngpSw();

    boolean isLccVideoWatched();

    boolean isMemParkVideoWatched();

    boolean isXpuXpilotActivated();

    void setAlcState(boolean enable);

    void setAlcState(boolean enable, boolean needSave);

    void setApaSafeExamResult(String uid, boolean result);

    void setAutoParkSw(boolean enable);

    void setAutoPilotNeedTts(boolean enable);

    void setBsdState(boolean enable);

    void setDistractionSwitch(boolean enable);

    void setDowSw(boolean enable, boolean needSp);

    void setDsmSw(boolean on);

    void setElkState(boolean enable);

    void setFcwSensitivity(int value);

    void setFcwState(boolean enable);

    void setFirstOpenXngpSw(boolean enable);

    void setIhbState(boolean enable);

    void setIslaConfirmMode(boolean enable);

    void setIslaSpdRange(int range);

    void setIslaSw(int mode);

    void setIslcState(boolean enable);

    void setKeyParkSw(boolean enable);

    void setLccSafeExamResult(String uid, boolean result);

    void setLccState(boolean enable);

    void setLccState(boolean enable, boolean needSave);

    void setLccVideoWatched(boolean watched);

    void setLdwState(boolean enable);

    void setLssMode(int mode);

    void setMemParkSafeExamResult(String uid, boolean result);

    void setMemParkVideoWatched(boolean watched);

    void setMemoryParkSw(boolean enable);

    void setNgpChangeLaneMode(int mode);

    void setNgpEnable(boolean enable);

    void setNgpFastLaneSw(boolean enable);

    void setNgpRemindMode(int mode);

    void setNgpSafeExamResult(String uid, boolean result);

    void setNgpTipsWin(boolean enable);

    void setNgpTruckOffsetSw(boolean enable);

    void setNgpVoiceChangeLane(boolean enable);

    void setPhoneParkSw(boolean enable);

    void setRctaState(boolean enable);

    void setRcwState(boolean enable);

    void setRemoteCameraSw(boolean on);

    void setScuOtaTagStatus(int tag);

    void setSpecialSasMode(int mode);

    void setSuperLccSafeExamResult(String uid, boolean result);

    void setSuperVpaSafeExamResult(String uid, boolean result);

    void setTtsBroadcastType(int type);

    void syncXPilotMemParkSw();
}
