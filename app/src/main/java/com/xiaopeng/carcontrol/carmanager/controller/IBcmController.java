package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface IBcmController extends IBaseCarController<Callback> {
    public static final int ARS_AUTO = 1;
    public static final int ARS_FAULT = 1;
    public static final int ARS_FOLD = 1;
    public static final int ARS_INITED = 1;
    public static final int ARS_INITING = 2;
    public static final int ARS_INIT_INVALID = 3;
    public static final int ARS_MANUAL = 2;
    public static final int ARS_NOT_INITED = 0;
    public static final int ARS_NO_FAULT = 0;
    public static final int ARS_ST_FOLDING = 1;
    public static final int ARS_ST_FOLD_DURG = 2;
    public static final int ARS_ST_INITING = 6;
    public static final int ARS_ST_STOP = 0;
    public static final int ARS_ST_UNFOLDING = 3;
    public static final int ARS_ST_UNFOLD_DURG = 4;
    public static final int ARS_ST_ZERO_POS_LRN = 5;
    public static final int ARS_UNFOLD = 2;
    public static final int AS_DRIVING_MODE_STATUS_COMFORT = 1;
    public static final int AS_DRIVING_MODE_STATUS_CUSTOMER = 6;
    public static final int AS_DRIVING_MODE_STATUS_ECO = 2;
    public static final int AS_DRIVING_MODE_STATUS_NORMAL = 4;
    public static final int AS_DRIVING_MODE_STATUS_NO_COMMAND = 0;
    public static final int AS_DRIVING_MODE_STATUS_OFFROAD = 5;
    public static final int AS_DRIVING_MODE_STATUS_SPORT = 3;
    public static final int AS_LOCATION_ST_OFF = 0;
    public static final int AS_LOCATION_ST_ON = 1;
    public static final String AS_LOCATION_SW = "as_location_save";
    public static final int BCM_ALL_DOME_LIGHT = 4;
    public static final int BCM_ALL_FRONT_DOME_LIGHT = 5;
    public static final int BCM_ALL_REAR_DOME_LIGHT = 6;
    public static final int BCM_ALL_THIRD_ROW_DOME_LIGHT = 9;
    public static final int BCM_AS_MODE_INVALID = -1;
    public static final int BCM_AS_RESULT_FAULT = 3;
    public static final int BCM_AS_RESULT_INVALID_VALUE = 2;
    public static final int BCM_AS_RESULT_LEVELED = 1;
    public static final int BCM_AS_RESULT_NO_TARGET = 0;
    public static final int BCM_BRK_PEDAL_NOT_PRESSED = 0;
    public static final int BCM_BRK_PEDAL_PRESSED = 1;
    public static final int BCM_CAMPING_MODE_STATUS = 6;
    public static final int BCM_CHARGE_PORT_CLOSED = 2;
    public static final int BCM_CHARGE_PORT_FAULT = 3;
    public static final int BCM_CHARGE_PORT_LOCK = 0;
    public static final int BCM_CHARGE_PORT_MIDDLE = 1;
    public static final int BCM_CHARGE_PORT_OPEN = 0;
    public static final int BCM_CHARGE_PORT_UNKNOWN = -1;
    public static final int BCM_CHARGE_PORT_UNLOCK = 1;
    public static final int BCM_CHILD_LOCK_ALL_LOCK = 6;
    public static final int BCM_CHILD_LOCK_ALL_UNLOCK = 1;
    public static final int BCM_CHILD_LOCK_LEFT_LOCK = 4;
    public static final int BCM_CHILD_LOCK_LEFT_UNLOCK = 2;
    public static final int BCM_CHILD_LOCK_RIGHT_LOCK = 5;
    public static final int BCM_CHILD_LOCK_RIGHT_UNLOCK = 3;
    public static final int BCM_CMS_VIEW_ANGLE_FLAT = 1;
    public static final int BCM_CMS_VIEW_ANGLE_NO_COMMAND = 0;
    public static final int BCM_CMS_VIEW_ANGLE_WIDE = 2;
    public static final int BCM_COMMON_ACTIVE = 1;
    public static final int BCM_COMMON_INACTIVE = 0;
    public static final int BCM_COMMON_INVALID = 2;
    public static final int BCM_CWC_CHARGE_DONE = 2;
    public static final int BCM_CWC_CHARGING = 1;
    public static final int BCM_CWC_ERROR_FOD = 3;
    public static final int BCM_CWC_ERROR_LOWVOL_PROTECT = 2;
    public static final int BCM_CWC_ERROR_NOCOMMAND = 0;
    public static final int BCM_CWC_ERROR_OVERVOL_PROTECT = 1;
    public static final int BCM_CWC_ERROR_PEPS_INTERRUPT = 4;
    public static final int BCM_CWC_ERROR_RX_ERROR = 5;
    public static final int BCM_CWC_ERROR_TEMP_HIGH = 6;
    public static final int BCM_DOMELIGHT_BRIGHT_LEVEL_1 = 1;
    public static final int BCM_DOMELIGHT_BRIGHT_LEVEL_2 = 2;
    public static final int BCM_DOMELIGHT_BRIGHT_LEVEL_3 = 3;
    public static final int BCM_DOMELIGHT_BRIGHT_LEVEL_4 = 4;
    public static final int BCM_DOMELIGHT_BRIGHT_LEVEL_5 = 5;
    public static final int BCM_DOMELIGHT_BRIGHT_LEVEL_6 = 6;
    public static final int BCM_DOMELIGHT_MODE_DOME = 0;
    public static final int BCM_DOMELIGHT_MODE_OFF = 0;
    public static final int BCM_DOMELIGHT_MODE_ON = 1;
    public static final int BCM_DOMELIGHT_SW_OFF = 0;
    public static final int BCM_DOME_AUTO = 1;
    public static final int BCM_DOME_OFF = 3;
    public static final int BCM_DOME_ON = 2;
    public static final int BCM_DOOR_CLOSE = 0;
    public static final int BCM_DOOR_FL = 0;
    public static final int BCM_DOOR_FR = 1;
    public static final int BCM_DOOR_OPEN = 1;
    public static final int BCM_DOOR_RL = 2;
    public static final int BCM_DOOR_RR = 3;
    public static final int BCM_DOOR_UNKNOWN = -1;
    public static final int BCM_FL_DOME_LIGHT = 0;
    public static final int BCM_FR_DOME_LIGHT = 1;
    public static final int BCM_LAMP_AUTO = 3;
    public static final int BCM_LAMP_AUTO_FAR = 4;
    public static final int BCM_LAMP_LOW_BEAM = 2;
    public static final int BCM_LAMP_OFF = 0;
    public static final int BCM_LAMP_POSITION = 1;
    public static final int BCM_LEFT_CHARGE_PORT = 1;
    public static final int BCM_LIGHT_ME_HOME_15S = 1;
    public static final int BCM_LIGHT_ME_HOME_30S = 2;
    public static final int BCM_LIGHT_ME_HOME_60S = 3;
    public static final int BCM_LIGHT_ME_HOME_OFF = 0;
    public static final int BCM_LOW_BEAM_OFF_DIAG_ACTIVE = 1;
    public static final int BCM_LOW_BEAM_OFF_DIAG_NO = 2;
    public static final int BCM_LOW_BEAM_OFF_DIAG_NOT_ACTIVE = 0;
    public static final int BCM_LOW_BEAM_OFF_DIAG_YES = 1;
    public static final int BCM_MAINTENANCE_MODE_STATUS = 1;
    public static final int BCM_MIRROR_REVERSE_BOTH = 3;
    public static final int BCM_MIRROR_REVERSE_LEFT = 1;
    public static final int BCM_MIRROR_REVERSE_OFF = 0;
    public static final int BCM_MIRROR_REVERSE_RIGHT = 2;
    public static final int BCM_MIRROR_TYPE_FOLD = 0;
    public static final int BCM_MIRROR_TYPE_UNFOLD = 1;
    public static final int BCM_NO_REQUEST_MODE_STATUS = 0;
    public static final int BCM_PARK_LIGHT_FM_B_ACTIVE = 1;
    public static final int BCM_READY_ENABLE_STATE_ACTIVE = 1;
    public static final int BCM_READY_ENABLE_STATE_INACTIVE = 0;
    public static final int BCM_REAR_TRUNK_CLOSE = 3;
    public static final int BCM_REAR_TRUNK_OPEN = 1;
    public static final int BCM_REAR_TRUNK_PAUSE = 2;
    public static final int BCM_REAR_TRUNK_SWS_OPEN = 5;
    public static final int BCM_RIGHT_CHARGE_PORT = 2;
    public static final int BCM_RL_DOME_LIGHT = 2;
    public static final int BCM_RR_DOME_LIGHT = 3;
    public static final int BCM_RUNNING_LIGHT_ALL = 3;
    public static final int BCM_RUNNING_LIGHT_LEFT = 1;
    public static final int BCM_RUNNING_LIGHT_OFF = 0;
    public static final int BCM_RUNNING_LIGHT_RIGHT = 2;
    public static final int BCM_SDC_BRAKE_CLOSE_DOOR_DRIVER = 1;
    public static final int BCM_SDC_BRAKE_CLOSE_DOOR_FRONT = 2;
    public static final int BCM_SDC_BRAKE_CLOSE_DOOR_NOT_ACTIVE = 0;
    public static final int BCM_SEAT_BLOW_LEVEL_1 = 1;
    public static final int BCM_SEAT_BLOW_LEVEL_2 = 2;
    public static final int BCM_SEAT_BLOW_LEVEL_3 = 3;
    public static final int BCM_SEAT_BLOW_LEVEL_OFF = 0;
    public static final int BCM_SEAT_HEAT_LEVEL_1 = 1;
    public static final int BCM_SEAT_HEAT_LEVEL_2 = 2;
    public static final int BCM_SEAT_HEAT_LEVEL_3 = 3;
    public static final int BCM_SEAT_HEAT_LEVEL_OFF = 0;
    public static final int BCM_SLIDE_DOOR_CTRL_CLOSE = 2;
    public static final int BCM_SLIDE_DOOR_CTRL_OPEN = 1;
    public static final int BCM_SLIDE_DOOR_CTRL_STOP = 3;
    public static final int BCM_SLIDE_DOOR_LOCK_STATE_LOCKED = 1;
    public static final int BCM_SLIDE_DOOR_LOCK_STATE_UNLOCKED = 0;
    public static final int BCM_SLIDE_DOOR_MODE_MANUAL_MODE = 1;
    public static final int BCM_SLIDE_DOOR_MODE_POWER_MODE = 0;
    public static final int BCM_SLIDE_DOOR_STATE_BRAKEMODE = 7;
    public static final int BCM_SLIDE_DOOR_STATE_CLOSED = 2;
    public static final int BCM_SLIDE_DOOR_STATE_CLOSING = 4;
    public static final int BCM_SLIDE_DOOR_STATE_DOORFREE = 6;
    public static final int BCM_SLIDE_DOOR_STATE_FULLOPEN = 1;
    public static final int BCM_SLIDE_DOOR_STATE_OPENING = 3;
    public static final int BCM_SLIDE_DOOR_STATE_STOPPED = 5;
    public static final int BCM_SLIDE_DOOR_STATE_UNKNOWN = 0;
    public static final int BCM_STATUS_OFF = 0;
    public static final int BCM_STATUS_ON = 1;
    public static final int BCM_TL_DOME_LIGHT = 7;
    public static final int BCM_TRAILER_MODE_STATUS = 3;
    public static final int BCM_TRANSPORT_MODE_STATUS = 2;
    public static final int BCM_TRUNK_FULL_OPEN_POS_LEVEL_1 = 2;
    public static final int BCM_TRUNK_FULL_OPEN_POS_LEVEL_2 = 3;
    public static final int BCM_TRUNK_FULL_OPEN_POS_LEVEL_3 = 4;
    public static final int BCM_TRUNK_FULL_OPEN_POS_LEVEL_4 = 5;
    public static final int BCM_TRUNK_FULL_OPEN_POS_LEVEL_5 = 6;
    public static final int BCM_TRUNK_OPENNER_FULL_CLOSED_STATUS = 1;
    public static final int BCM_TRUNK_OPENNER_FULL_OPENED_STATUS = 5;
    public static final int BCM_TRUNK_OPENNER_HALF_CLOSED_STATUS = 9;
    public static final int BCM_TRUNK_OPENNER_MOVE_DOWN_BRKG_STATUS = 7;
    public static final int BCM_TRUNK_OPENNER_MOVE_DOWN_STATUS = 6;
    public static final int BCM_TRUNK_OPENNER_MOVE_UP_BRKG_STATUS = 3;
    public static final int BCM_TRUNK_OPENNER_MOVE_UP_STATUS = 2;
    public static final int BCM_TRUNK_OPENNER_STOP_DURG_CLOSE_STATUS = 8;
    public static final int BCM_TRUNK_OPENNER_STOP_DURG_OPEN_STATUS = 4;
    public static final int BCM_TRUNK_OPENNER_STOP_MIN_PNT_FOR_CLOSE_STATUS = 10;
    public static final int BCM_TRUNK_OPENNER_UNKOWN_STATUS = 0;
    public static final int BCM_TRUNK_POS_11_20_PERCENT_TOP = 3;
    public static final int BCM_TRUNK_POS_1_10_PERCENT_TOP = 2;
    public static final int BCM_TRUNK_POS_21_30_PERCENT_TOP = 4;
    public static final int BCM_TRUNK_POS_31_40_PERCENT_TOP = 5;
    public static final int BCM_TRUNK_POS_41_50_PERCENT_TOP = 6;
    public static final int BCM_TRUNK_POS_51_60_PERCENT_TOP = 7;
    public static final int BCM_TRUNK_POS_61_70_PERCENT_TOP = 8;
    public static final int BCM_TRUNK_POS_71_80_PERCENT_TOP = 9;
    public static final int BCM_TRUNK_POS_81_90_PERCENT_TOP = 10;
    public static final int BCM_TRUNK_POS_91_100_PERCENT_TOP = 11;
    public static final int BCM_TRUNK_POS_FULL_CLOSED = 1;
    public static final int BCM_TRUNK_POS_UNKNOWN = 0;
    public static final int BCM_TRUNK_WORK_MODE_ST_ANTI_PLAY_MODE = 2;
    public static final int BCM_TRUNK_WORK_MODE_ST_MANUAL_MODE = 3;
    public static final int BCM_TRUNK_WORK_MODE_ST_NORMAL = 0;
    public static final int BCM_TRUNK_WORK_MODE_ST_OTA_PROBITY_MODE = 4;
    public static final int BCM_TRUNK_WORK_MODE_ST_SYSTEM_ERROR = 1;
    public static final int BCM_TRUNK_WORK_MODE_ST_VEHICLE_SPEED_PROBITY_MODE = 5;
    public static final int BCM_TR_DOME_LIGHT = 8;
    public static final int BCM_TTM_SW_CLOSE = 0;
    public static final int BCM_TTM_SW_CLOSING = 4;
    public static final int BCM_TTM_SW_FAULT = 2;
    public static final int BCM_TTM_SW_MIDDLE = 2;
    public static final int BCM_TTM_SW_OPEN = 1;
    public static final int BCM_TTM_SW_OPENING = 5;
    public static final int BCM_TTM_SW_STOP = 3;
    public static final int BCM_UNLOCK_LIGHT_AND_AVAS = 2;
    public static final int BCM_UNLOCK_LIGHT_AND_HORN = 0;
    public static final int BCM_UNLOCK_RESPONSE_LIGHT = 1;
    public static final int BCM_WINDOW_ALL = 4;
    public static final int BCM_WINDOW_FRONT = 5;
    public static final int BCM_WINDOW_FRONT_LEFT = 0;
    public static final int BCM_WINDOW_FRONT_RIGHT = 1;
    public static final int BCM_WINDOW_KEY_CONTROL_AUTO = 1;
    public static final int BCM_WINDOW_KEY_CONTROL_MANUAL = 2;
    public static final int BCM_WINDOW_KEY_CONTROL_OFF = 3;
    public static final int BCM_WINDOW_LEFT = 7;
    public static final int BCM_WINDOW_ONE_KEY_DOWN = 2;
    public static final int BCM_WINDOW_ONE_KEY_UP = 1;
    public static final float BCM_WINDOW_POS_INVALID = 255.0f;
    public static final int BCM_WINDOW_REAR = 6;
    public static final int BCM_WINDOW_REAR_LEFT = 2;
    public static final int BCM_WINDOW_REAR_RIGHT = 3;
    public static final int BCM_WINDOW_RIGHT = 8;
    public static final int BCM_WINDOW_TYPE_DOWN_AUTO = 4;
    public static final int BCM_WINDOW_TYPE_DOWN_MANUALLY = 3;
    public static final int BCM_WINDOW_TYPE_INVALID = 0;
    public static final int BCM_WINDOW_TYPE_STOP = 3;
    public static final int BCM_WINDOW_TYPE_STOP_ACTION = 5;
    public static final int BCM_WINDOW_TYPE_UP_AUTO = 2;
    public static final int BCM_WINDOW_TYPE_UP_MANUALLY = 1;
    public static final int BCM_WIPER_INTERNAL_GEAR_1 = 1;
    public static final int BCM_WIPER_INTERNAL_GEAR_2 = 2;
    public static final int BCM_WIPER_INTERNAL_GEAR_3 = 3;
    public static final int BCM_WIPER_INTERNAL_GEAR_4 = 4;
    public static final int BCM_WIPER_RAIN_DETECT_SENSITIVITY_LEVEL_1 = 1;
    public static final int BCM_WIPER_RAIN_DETECT_SENSITIVITY_LEVEL_2 = 2;
    public static final int BCM_WIPER_RAIN_DETECT_SENSITIVITY_LEVEL_3 = 3;
    public static final int BCM_WIPER_RAIN_DETECT_SENSITIVITY_LEVEL_4 = 4;
    public static final int BCM_WIPER_SPEED_SW_STATE_AUTO = 4;
    public static final int BCM_WIPER_SPEED_SW_STATE_HIGH = 2;
    public static final int BCM_WIPER_SPEED_SW_STATE_INTERMITTENT = 3;
    public static final int BCM_WIPER_SPEED_SW_STATE_LOW = 1;
    public static final int BCM_WIPER_SPEED_SW_STATE_OFF = 0;
    public static final int CDU_BONNET_CLOSED = 0;
    public static final int CDU_BONNET_OPENED = 1;
    public static final int CDU_CHILD_LOCK_ALL_LOCK = 4;
    public static final int CDU_CHILD_LOCK_ALL_UNLOCK = 1;
    public static final int CDU_CHILD_LOCK_LEFT_LOCK = 3;
    public static final int CDU_CHILD_LOCK_RIGHT_LOCK = 2;
    public static final int CDU_DOME_STATUS_AUTO = 1;
    public static final int CDU_DOME_STATUS_OFF = 3;
    public static final int CDU_DOME_STATUS_ON = 2;
    public static final int CDU_LIGHT_ME_HOME_15S = 1;
    public static final int CDU_LIGHT_ME_HOME_30S = 2;
    public static final int CDU_LIGHT_ME_HOME_60S = 3;
    public static final int CDU_LIGHT_ME_HOME_OFF = 0;
    public static final int CDU_TRUNK_CLOSED = 0;
    public static final int CDU_TRUNK_OPENED = 2;
    public static final int CDU_TRUNK_OPENING = 1;
    public static final int CLOSE_SUN_SHADE = 1;
    public static final int CMS_BRIGHT_FLAG_SAVE = 1;
    public static final float CMS_POS_LL_DEFAULT = 50.0f;
    public static final float CMS_POS_LR_DEFAULT = 50.0f;
    public static final float CMS_POS_RL_DEFAULT = 50.0f;
    public static final float CMS_POS_RR_DEFAULT = 50.0f;
    public static final int DOOR_UNLOCK_REQUEST_SOURCE_BLE = 4;
    public static final int DOOR_UNLOCK_REQUEST_SOURCE_NFC = 3;
    public static final int DOOR_UNLOCK_REQUEST_SOURCE_POLLING = 2;
    public static final int DOOR_UNLOCK_REQUEST_SOURCE_RKE = 1;
    public static final int DRV_BELT_BUCKLED = 0;
    public static final int DRV_BELT_UNBUCKLED_WARNING = 1;
    public static final int FIND_CARD_LIGHT = 2;
    public static final int FIND_CARD_LIGHT_AND_HORN = 1;
    public static final int HEADLAMPS_STATUS_AUTO = 3;
    public static final int HEADLAMPS_STATUS_HIGH_BEAM = 4;
    public static final int HEADLAMPS_STATUS_LOW_BEAM = 2;
    public static final int HEADLAMPS_STATUS_OFF = 0;
    public static final int HEADLAMPS_STATUS_POSITION = 1;
    public static final int HEIGHT_LVL_CONFIG_0 = 3;
    public static final int HEIGHT_LVL_CONFIG_HL1 = 2;
    public static final int HEIGHT_LVL_CONFIG_HL2 = 1;
    public static final int HEIGHT_LVL_CONFIG_INVALID = -1;
    public static final int HEIGHT_LVL_CONFIG_LL1 = 4;
    public static final int HEIGHT_LVL_CONFIG_LL2 = 5;
    public static final int HEIGHT_LVL_CONFIG_LL3 = 6;
    public static final int HEIGHT_LVL_CONFIG_OFF = 0;
    public static final int ID_BCM_EBW = 557849631;
    public static final int IMS_ANGLE_NARROW = 1;
    public static final int IMS_ANGLE_NORMAL = 0;
    public static final int IMS_ANGLE_WIDEN = 2;
    public static final int IMS_AUTO_VISION_OFF = 0;
    public static final int IMS_AUTO_VISION_ON = 1;
    public static final int IMS_BRIGHTNESS_LEVEL1 = 1;
    public static final int IMS_BRIGHTNESS_LEVEL2 = 2;
    public static final int IMS_BRIGHTNESS_LEVEL3 = 3;
    public static final int IMS_BRIGHTNESS_LEVEL4 = 4;
    public static final int IMS_BRIGHTNESS_LEVEL5 = 5;
    public static final int IMS_MODE_CAMERA = 1;
    public static final int IMS_MODE_MIRROR = 0;
    public static final int IMS_MOVE_DOWN = 2;
    public static final int IMS_MOVE_NARROW = 4;
    public static final int IMS_MOVE_UP = 1;
    public static final int IMS_MOVE_WIDE = 3;
    public static final int IMS_SYSTEM_FAILURE = 2;
    public static final int IMS_SYSTEM_NORMAL = 1;
    public static final int IMS_SYSTEM_OFF = 0;
    public static final int IMS_VERTICAL_LEVEL1 = 1;
    public static final int IMS_VERTICAL_LEVEL2 = 2;
    public static final int IMS_VERTICAL_LEVEL3 = 3;
    public static final int IMS_VERTICAL_LEVEL4 = 4;
    public static final int IMS_VERTICAL_LEVEL5 = 5;
    public static final int IMS_VERTICAL_LEVEL6 = 6;
    public static final int IMS_VERTICAL_LEVEL7 = 7;
    public static final int KEY_AUTH_ST_FAIL = 3;
    public static final int KEY_AUTH_ST_INACTIVE = 0;
    public static final int KEY_AUTH_ST_START_IDENTIFICATION = 1;
    public static final int KEY_AUTH_ST_SUCCESS = 2;
    public static final int LAMP_HEIGHT_AUTO_MODE = 5;
    public static final int LAMP_HEIGHT_LEVEL_0 = 0;
    public static final int LAMP_HEIGHT_LEVEL_1 = 1;
    public static final int LAMP_HEIGHT_LEVEL_2 = 2;
    public static final int LAMP_HEIGHT_LEVEL_3 = 3;
    public static final int LAMP_HEIGHT_MANUAL_MODE = 6;
    public static final int L_MIRROR_LR_DEFAULT = 50;
    public static final int L_MIRROR_UD_DEFAULT = 50;
    public static final int MIRROR_ADJUST_STATE_NULL = 0;
    public static final int MIRROR_CONTROL_PENDING = 2;
    public static final int MIRROR_CONTROL_START = 1;
    public static final int MIRROR_CONTROL_STOP = 3;
    public static final int MIRROR_MOVE_DOWN = 2;
    public static final int MIRROR_MOVE_LEFT = 3;
    public static final int MIRROR_MOVE_RIGHT = 4;
    public static final int MIRROR_MOVE_UP = 1;
    public static final int OPEN_SUN_SHADE = 2;
    public static final String REAR_SCREEN_STATE = "screen_state_3";
    public static final int REAR_SCREEN_STATE_CLOSED = 0;
    public static final int REAR_SCREEN_STATE_MIDDLE = 2;
    public static final int REAR_SCREEN_STATE_OPENED = 1;
    public static final int R_MIRROR_LR_DEFAULT = 50;
    public static final int R_MIRROR_UD_DEFAULT = 50;
    public static final int SDC_CONTROL_CLOSE = 1;
    public static final int SDC_CONTROL_OPEN = 2;
    public static final int SDC_CONTROL_PAUSE = 3;
    public static final int SDC_KEY_CTRL_CFG_DRIVER_DOOR = 0;
    public static final int SDC_KEY_CTRL_CFG_FRONT_DOOR = 1;
    public static final int SDC_MAX_DOOR_ANGLE_DEFAULT = 100;
    public static final int SDC_MIN_DOOR_ANGLE_DEFAULT = 40;
    public static final int SDC_SYS_RUNNING_STATUS_CLOSED = 1;
    public static final int SDC_SYS_RUNNING_STATUS_CLOSING = 3;
    public static final int SDC_SYS_RUNNING_STATUS_INITIAL = 0;
    public static final int SDC_SYS_RUNNING_STATUS_LOCK_STATUS_ERROR = 7;
    public static final int SDC_SYS_RUNNING_STATUS_OPENING = 2;
    public static final int SDC_SYS_RUNNING_STATUS_OPEN_FINISHED = 5;
    public static final int SDC_SYS_RUNNING_STATUS_PAUSE = 4;
    public static final int SDC_SYS_RUNNING_STATUS_PREVENT_PLAYING_MODE = 6;
    public static final int SDC_WINDOW_AUTO_DOWN_SWITCH_STATUS_OFF = 1;
    public static final int SDC_WINDOW_AUTO_DOWN_SWITCH_STATUS_ON = 2;
    public static final int SOFT_LVL_CONFIG_HARD = 3;
    public static final int SOFT_LVL_CONFIG_MEDIUM = 2;
    public static final int SOFT_LVL_CONFIG_SOFT = 1;
    public static final int STEER_CONTROL_PENDING = 2;
    public static final int STEER_CONTROL_START = 1;
    public static final int STEER_CONTROL_STOP = 3;
    public static final int STEER_HEAT_LEVEL_1 = 1;
    public static final int STEER_HEAT_LEVEL_2 = 2;
    public static final int STEER_HEAT_LEVEL_3 = 3;
    public static final int STEER_HEAT_LEVEL_OFF = 0;
    public static final int STEER_MOVE_DOWN = 2;
    public static final int STEER_MOVE_IN = 2;
    public static final int STEER_MOVE_INVALID = -1;
    public static final int STEER_MOVE_OUT = 1;
    public static final int STEER_MOVE_UP = 1;
    public static final int STOP_SUN_SHADE = 3;
    public static final int TTM_CONNECT = 1;
    public static final int TTM_DENORMALIZE_STATUS_INITIALIZED = 1;
    public static final int TTM_DENORMALIZE_STATUS_NOT_INITIALIZED = 0;
    public static final int TTM_HOOK_MOTOR_STATUS_CLOSING = 2;
    public static final int TTM_HOOK_MOTOR_STATUS_NOT_MOVE = 0;
    public static final int TTM_HOOK_MOTOR_STATUS_OPENING = 1;
    public static final int TTM_NO_CONNECT = 0;
    public static final int TTM_SYS_ERROR_STATUS_ERROR = 1;
    public static final int TTM_SYS_ERROR_STATUS_NO_ERROR = 0;
    public static final int TURN_LAMP_ACTIVE = 1;
    public static final int TURN_LAMP_INACTIVE = 0;
    public static final int UNLOCK_STATUS_LIGHT = 1;
    public static final int UNLOCK_STATUS_LIGHT_AND_HORN = 0;
    public static final int WINDOW_INIT_FAILED = 1;
    public static final int WINDOW_LOCK_ACTIVE = 1;
    public static final int WINDOW_LOCK_INACTIVE = 0;
    public static final int XPILOT_ST_INIT = 3;
    public static final int XPILOT_ST_NOT_ACTIVATED = 4;
    public static final int XPILOT_ST_OFF = 2;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onArsFaultStateChange(int state) {
        }

        default void onArsInitStChanged(int state) {
        }

        default void onArsPosChanged(int position) {
        }

        default void onArsWorkModeChanged(int mode) {
        }

        default void onArsWorkStChanged(int state) {
        }

        default void onAsCampingModeChanged(boolean mode) {
        }

        default void onAsEasyLoadingModeChanged(boolean mode) {
        }

        default void onAsHeightModeChanged(int mode) {
        }

        default void onAsLevlingModeChanged(int mode) {
        }

        default void onAsMaintainModeChanged(int mode) {
        }

        default void onAsSoftModeChanged(int mode) {
        }

        default void onAsWelcomeModeChanged(boolean mode) {
        }

        default void onAutoDoorHandleChanged(boolean enabled) {
        }

        default void onAutoLampHeightChanged(boolean auto) {
        }

        default void onAutoWindowLockSwChanged(boolean enable) {
        }

        default void onBackBeltWaringChanged(boolean status) {
        }

        default void onBackDefrostChanged(boolean enabled) {
        }

        default void onBcmBrkPedalStateChanged(int status) {
        }

        default void onBcmReadyStateChanged(int status) {
        }

        default void onBonnetStateChanged(int state) {
        }

        default void onCarpetLightWelcomeChanged(boolean enable) {
        }

        default void onCentralLockStateChanged(boolean locked) {
        }

        default void onChargePortChanged(boolean isLeft, int state, boolean isMock) {
        }

        default void onChildLockChanged(int state) {
        }

        default void onChildModeChanged(boolean enable) {
        }

        default void onCmsAutoBrightSwChanged(boolean enabled) {
        }

        default void onCmsBrightChanged(int brightness) {
        }

        default void onCmsHighSpdSwChanged(boolean enabled) {
        }

        default void onCmsLowSpdSwChanged(boolean enabled) {
        }

        default void onCmsObjectRecognizeSwChanged(boolean enable) {
        }

        default void onCmsPosChanged(float[] pos) {
        }

        default void onCmsReverseSwChanged(boolean enabled) {
        }

        default void onCmsTurnSwChanged(boolean enabled) {
        }

        default void onCmsViewAngleChanged(int ViewAngle) {
        }

        default void onCwcChargeErrorStateChanged(int state) {
        }

        default void onCwcChargeStateChanged(int state) {
        }

        default void onCwcFRChargeErrorStateChanged(int state) {
        }

        default void onCwcFRChargeStateChanged(int state) {
        }

        default void onCwcFRSwChanged(boolean enable) {
        }

        default void onCwcRLChargeErrorStateChanged(int state) {
        }

        default void onCwcRLChargeStateChanged(int state) {
        }

        default void onCwcRLSwChanged(boolean enable) {
        }

        default void onCwcRRChargeErrorStateChanged(int state) {
        }

        default void onCwcRRChargeStateChanged(int state) {
        }

        default void onCwcRRSwChanged(boolean enable) {
        }

        default void onCwcSwChanged(boolean enable) {
        }

        default void onDaytimeRunningLightChanged(int convertDaytimeRunningLightState) {
        }

        default void onDomeLightBrightChanged(int brightness) {
        }

        default void onDomeLightStChanged(int lightType, int status) {
        }

        default void onDomeLightStateChanged(int status) {
        }

        default void onDoorLockStateChanged(boolean locked) {
        }

        default void onDoorStateChanged(int[] doorState) {
        }

        default void onDriveAutoLockChanged(boolean enabled) {
        }

        default void onDriverSeatBlowLevelChanged(int level) {
        }

        default void onDriverSeatHeatLevelChanged(int level) {
        }

        default void onDrvBeltWaringChanged(int mode) {
        }

        default void onDrvSeatOccupiedChanged(boolean occupied) {
        }

        default void onEbwChanged(boolean enabled) {
        }

        default void onElcTrunkPosChanged(int pos) {
        }

        default void onElcTrunkStateChanged(int state) {
        }

        default void onEsbChanged(boolean enabled) {
        }

        default void onFrontMirrorHeatModeChanged(boolean enabled) {
        }

        default void onHeadLampModeChanged(int state) {
        }

        default void onHighBeamLampStateChanged(boolean isOn) {
        }

        default void onHighSpdCloseWinChanged(boolean enabled) {
        }

        default void onImsAutoVisionSwChanged(boolean enable) {
        }

        default void onImsBrightLevelChanged(int level) {
        }

        default void onImsModeChanged(int mode) {
        }

        default void onImsSystemStChanged(int state) {
        }

        default void onImsVisionAngleLevelChanged(int level) {
        }

        default void onImsVisionVerticalLevelChanged(int level) {
        }

        default void onKeyAuthStateChanged(int status) {
        }

        default void onLampHeightLevelChanged(int height) {
        }

        default void onLeaveAutoLockChanged(boolean enabled) {
        }

        default void onLeftChildLockChanged(boolean locked) {
        }

        default void onLeftDoorHotKeyChanged(boolean locked) {
        }

        default void onLeftSdcDoorPosChanged(int pos) {
        }

        default void onLeftSdcStateChanged(int state) {
        }

        default void onLeftSdcSysRunningStateChanged(int state) {
        }

        default void onLeftSlideDoorLockStateChanged(int state) {
        }

        default void onLeftSlideDoorModeChanged(int mode) {
        }

        default void onLeftSlideDoorStateChanged(int state) {
        }

        default void onLightMeHomeChanged(boolean enabled) {
        }

        default void onLightMeHomeTimeChanged(int timeCfg) {
        }

        default void onLowBeamLampStateChanged(boolean isOn) {
        }

        default void onLowBeamOffConfirmStateChanged(boolean isActivated) {
        }

        default void onMirrorAdjust(int[] mirrorStates) {
        }

        default void onMirrorAutoDownChanged(boolean enabled) {
        }

        default void onMirrorAutoFoldStateChanged(boolean enabled) {
        }

        default void onMirrorHeatModeChanged(boolean enabled) {
        }

        default void onMirrorPosChanged(int[] position) {
        }

        default void onMirrorReverseModeChanged(int mode, boolean needMove) {
        }

        default void onNearAutoUnlockChanged(boolean enabled) {
        }

        default void onNfcStopSwChanged(boolean enable) {
        }

        default void onParkLightFmbChanged(int cfg) {
        }

        default void onParkingAutoUnlockChanged(boolean enabled) {
        }

        default void onParkingLampStatesChanged(int[] states) {
        }

        default void onPollingLightWelcomeChanged(boolean enable) {
        }

        default void onPollingOpenCfgChanged(boolean enabled) {
        }

        default void onPositionLampStateChanged(boolean isOn) {
        }

        default void onPsnSeatHeatLevelChanged(int level) {
        }

        default void onPsnSeatVentLevelChanged(int level) {
        }

        default void onRLSeatHeatLevelChanged(int level) {
        }

        default void onRLSeatHeatLevelERR(int code) {
        }

        default void onRRSeatHeatLevelChanged(int level) {
        }

        default void onRRSeatHeatLevelERR(int code) {
        }

        default void onRearFogLampChanged(boolean enabled) {
        }

        default void onRearLogoLightSwChanged(boolean enable) {
        }

        default void onRearScreenStateChanged(int state) {
        }

        default void onRearSeatWelcomeModeChanged(boolean enabled) {
        }

        default void onRearWiperRepairModeChanged(boolean mode) {
        }

        default void onRightChildLockChanged(boolean locked) {
        }

        default void onRightDoorHotKeyChanged(boolean locked) {
        }

        default void onRightSdcDoorPosChanged(int pos) {
        }

        default void onRightSdcStateChanged(int state) {
        }

        default void onRightSdcSysRunningStateChanged(int state) {
        }

        default void onRightSlideDoorLockStateChanged(int state) {
        }

        default void onRightSlideDoorModeChanged(int mode) {
        }

        default void onRightSlideDoorStateChanged(int state) {
        }

        default void onSdcBrakeCloseDoorCfgChanged(int cfg) {
        }

        default void onSdcKeyCfgChanged(int cfg) {
        }

        default void onSdcMaxDoorOpeningAngleChanged(int angle) {
        }

        default void onSdcWinAutoDown(boolean auto) {
        }

        default void onStealthModeChanged(boolean enable) {
        }

        default void onSteerHeatLevelChanged(int level) {
        }

        default void onSunShadeInitializationChanged(boolean initialized) {
        }

        default void onSunShadePosChanged(int pos) {
        }

        default void onTrailerHitchStatusChanged(int status) {
        }

        default void onTrailerModeStatusChanged(boolean enable) {
        }

        default void onTrunkFullOpenPosChanged(int pos) {
        }

        default void onTrunkSensorEnableChanged(boolean enable) {
        }

        default void onTrunkStateChanged(int state) {
        }

        default void onTrunkWorkStatusChange(int state) {
        }

        default void onTtmHookMotorStatusChanged(int status) {
        }

        default void onTtmLampConnectStatusChanged(boolean connect) {
        }

        default void onTtmSysErrStatusChanged(boolean isErr) {
        }

        default void onTurnLampStateChanged(int[] state) {
        }

        default void onUnlockReqSrcChanged(int status) {
        }

        default void onUnlockResponseChanged(int type) {
        }

        default void onWelcomeModeChanged(boolean enabled) {
        }

        default void onWindowKeyControlModeChanged(int mode) {
        }

        default void onWindowLockStateChanged(boolean on) {
        }

        default void onWindowPosChanged(int window, float position) {
        }

        default void onWindowsPosChanged(float[] winsPos) {
        }

        default void onWiperIntervalChanged(int wiper) {
        }

        default void onWiperRepairModeChanged(boolean mode) {
        }

        default void onWiperSensitivityChanged(int level) {
        }

        default void onWiperSensitivityKeyDown() {
        }

        default void onWiperSensitivityKeyUp() {
        }
    }

    void controlInOutSteer(int controlType, int direction);

    void controlLeftMirrorMove(int control, int direction);

    void controlLeftSdc(int cmd);

    void controlLeftSlideDoor(int cmd);

    void controlMirror(boolean isFold);

    void controlRearTrunk(int controlType);

    void controlRightMirrorMove(int control, int direction);

    void controlRightSdc(int cmd);

    void controlRightSlideDoor(int cmd);

    void controlSunShade(int control);

    void controlVerSteer(int controlType, int direction);

    void controlWinVent();

    void controlWindowAuto(boolean open);

    int getAirSuspensionHeight();

    int getAirSuspensionHeightSp();

    boolean getAirSuspensionRepairMode();

    int getAirSuspensionSoft();

    float[] getAllWindowPosition();

    int getArsFaultState();

    int getArsInitSt();

    int getArsPosition();

    int getArsWorkMode();

    int getArsWorkSt();

    int getAsAutoLevelingResult();

    boolean getAsCampingModeSwitch();

    int getAsDrivingMode();

    boolean getAsHeightChangingStatus();

    boolean getAsHoistModeSwitchStatus();

    boolean getAsLocationControlSw();

    boolean getAsLocationSw();

    int getAsLockModeStatus();

    boolean getAsRedLampRequest();

    int getAsRequestEspState();

    boolean getAsWelcomeMode();

    boolean getAsYellowLampRequest();

    boolean getAutoWindowLockSw();

    boolean getBackDefrost();

    int getBcmReadyEnableState();

    int getBonnetState();

    boolean getCarpetLightWelcomeMode();

    int getChargePortUnlock(int port);

    boolean getChildLeftLock();

    int getChildLock();

    boolean getChildRightLock();

    boolean getCmsAutoBrightSw();

    int getCmsBright();

    boolean getCmsHighSpdAssistSw();

    float[] getCmsLocation();

    float[] getCmsLocation(boolean ignoreCache);

    boolean getCmsLowSpdAssistSw();

    boolean getCmsObjectRecognizeSw();

    boolean getCmsReverseAssistSw();

    boolean getCmsTurnAssistSw();

    int getCmsViewAngle();

    boolean getCustomerModeFlag();

    int getCwcChargeErrorSt();

    int getCwcChargeSt();

    int getDaytimeRunningLightsOutputStatus();

    int getDomeLightBright();

    int getDomeLightState();

    int getDomeLightStatus(int lightType);

    boolean getDoorLockState();

    int getDoorState(int index);

    int[] getDoorsState();

    boolean getDriveAutoLock();

    int getDrvBeltStatus();

    boolean getEasyLoadingSwitch();

    int getElcTrunkPos();

    int getElcTrunkState();

    int getEngineeringModeStatus();

    boolean getEsbModeSp();

    int getFWiperActiveSt();

    boolean getFrontMirrorHeat();

    int getHeadLampState();

    int getHeadLampStateSp();

    boolean getHighBeamLamp();

    int getImsAutoVisionSw();

    int getImsBrightLevel();

    int getImsMode();

    int getImsSystemSt();

    int getImsVisionAngleLevel();

    int getImsVisionVerticalLevel();

    int getKeyAuthState();

    int getLampHeightLevel();

    boolean getLeaveAutoLock();

    int getLeftMirrorLRPos();

    int getLeftMirrorLRPos(boolean ignoreCache);

    int getLeftMirrorUDPos();

    int getLeftMirrorUDPos(boolean ignoreCache);

    int getLeftSdcDoorPos();

    int getLeftSdcSysRunningState();

    int getLeftSlideDoorLockState();

    int getLeftSlideDoorMode();

    int getLeftSlideDoorState();

    boolean getLightMeHome();

    int getLightMeHomeTime();

    boolean getLockAvasSw();

    boolean getLowBeamLamp();

    int getLowBeamOffConfirmSt();

    int[] getMirrorPosition();

    int[] getMirrorPosition(boolean ignoreCache);

    String getMirrorSavedPos();

    boolean getNearAutoUnlock();

    boolean getParkLamp();

    boolean getParkingAutoUnlock();

    int[] getParkingLampStates();

    boolean getPollingLightWelcomeMode();

    boolean getPollingOpenCfg();

    int getPsnSeatHeatLevel();

    int getPsnSeatVentLevel();

    int getRLSeatHeatLevel();

    int getRRSeatHeatLevel();

    boolean getRearFogLamp();

    boolean getRearLogoLightSw();

    int getRearScreenState();

    boolean getRearSeatWelcomeMode();

    int getRearTrunk();

    int[] getRearViewMirrorsAdjustStates();

    boolean getRearWiperRepairMode();

    int getReverseMirrorMode();

    int getRightMirrorLRPos();

    int getRightMirrorLRPos(boolean ignoreCache);

    int getRightMirrorUDPos();

    int getRightMirrorUDPos(boolean ignoreCache);

    int getRightSdcDoorPos();

    int getRightSdcSysRunningState();

    int getRightSlideDoorLockState();

    int getRightSlideDoorMode();

    int getRightSlideDoorState();

    float[] getSavedCmsLocation();

    int getSdcBrakeCloseDoorCfg();

    int getSdcKeyOpenCtrlCfg();

    int getSdcMaxAutoDoorOpeningAngle();

    boolean getSdcWindowsAutoDownSwitch();

    int getSeatHeatLevel();

    int getSeatVentLevel();

    boolean getSeatWelcomeMode();

    boolean getStealthMode();

    int getSteerHeatLevel();

    int[] getSteerPos();

    int[] getSteerSavedPos();

    boolean getStopNfcCardSw();

    int getSunShadePos();

    int getTrailerHitchSwitchStatus();

    boolean getTrailerModeStatus();

    int getTrunkFullOpenPos();

    int getTrunkWorkModeStatus();

    int getTtmDenormalizeStatus();

    int getTtmHookMotorStatus();

    int getTtmLampConnectStatus();

    boolean getTtmSystemError();

    int[] getTurnLampState();

    int getUnlockReqSrc();

    int getUnlockResponse();

    int getUnlockResponseSp();

    float getWinPos(int windowType);

    boolean getWindowLockState();

    int getWiperInterval();

    boolean getWiperRepairMode();

    int getWiperSensitivity();

    boolean isAutoDoorHandleEnabled();

    boolean isAutoLampHeight();

    boolean isChildModeEnable();

    boolean isCwcFRSwEnable();

    boolean isCwcRLSwEnable();

    boolean isCwcRRSwEnable();

    boolean isCwcSwEnable();

    boolean isDrvSeatOccupied();

    boolean isEbwEnabled();

    boolean isEsbEnabled();

    boolean isHighSpdCloseWinEnabled();

    boolean isLedDrlEnabled();

    boolean isLeftDoorHotKeyEnable();

    boolean isMirrorAutoDown();

    boolean isMirrorAutoFoldEnable();

    boolean isMirrorHeatEnabled();

    boolean isParkLampIncludeFmB();

    boolean isRearWiperFault();

    boolean isRearWiperSpeedSwitchOff();

    boolean isRightDoorHotKeyEnable();

    boolean isRsbWarningEnabled();

    boolean isSunShadeAntiPinch();

    boolean isSunShadeHotProtect();

    boolean isSunShadeIceBreak();

    boolean isSunShadeInitialized();

    boolean isTrunkSensorEnable();

    boolean isWindowInitFailed(int index);

    boolean isWiperFault();

    boolean isWiperSpeedSwitchOff();

    void openCarBonnet();

    void resetSunShade();

    void restorePsnSeatHeatLevel();

    void restoreRLSeatHeatLevel();

    void restoreRRSeatHeatLevel();

    void restoreSeatHeatLevel();

    void restoreSeatVentLevel();

    void restoreSteerHeatLevel();

    void saveCmsLocation(float[] pos);

    void saveMirrorPos(String toString);

    void saveSteerPos(int[] pos);

    void setARSFoldOrUnfold(boolean fold);

    void setAirSuspensionHeight(int height);

    void setAirSuspensionHeight(int height, boolean needSave);

    void setAirSuspensionRepairMode(boolean enable);

    void setAirSuspensionSoft(int soft);

    void setAllChildLock(boolean lock, boolean needSave);

    void setArsWorkMode(int mode);

    void setAsCampingModeSwitch(boolean enable);

    void setAsDrivingMode(int mode);

    void setAsLocationControlSw(boolean on);

    void setAsLocationSw(boolean on);

    void setAsLocationSw(boolean on, boolean storeEnable);

    void setAsLocationValue(int value);

    void setAsTrailerModeSwitchStatus(boolean enable);

    void setAsWelcomeMode(boolean enable);

    void setAutoDoorHandleEnable(boolean enable, boolean needSave);

    void setAutoLampHeight(boolean auto);

    void setAutoLampHeight(boolean auto, boolean needSave);

    void setAutoWindowLockSw(boolean enable);

    void setBackDefrost(boolean enable);

    void setCarpetLightWelcomeMode(boolean enable);

    void setCentralLock(boolean lock);

    void setChargePortUnlock(int port, int state);

    void setChildLock(int lockCmd);

    void setChildLock(boolean leftSide, boolean lock);

    void setChildLock(boolean leftSide, boolean lock, boolean needSave);

    void setChildModeEnable(boolean enable, boolean fromUser);

    void setCmsAutoBrightSw(boolean enable);

    void setCmsBright(int brightness);

    void setCmsHighSpdAssistSw(boolean enable);

    void setCmsLocation(float leftHPos, float leftVPos, float rightHPos, float rightVPos);

    void setCmsLocation(float[] pos);

    void setCmsLowSpdAssistSw(boolean enable);

    void setCmsObjectRecognizeSw(boolean enable);

    void setCmsReverseAssistSw(boolean enable);

    void setCmsTurnAssistSw(boolean enable);

    void setCmsViewAngle(int viewAngle);

    void setCmsViewRecovery(boolean enable);

    void setCustomerModeFlag(boolean enable);

    void setCustomerModeFlag(boolean enable, boolean storeEnable);

    void setCwcFRSwEnable(boolean enable);

    void setCwcRLSwEnable(boolean enable);

    void setCwcRRSwEnable(boolean enable);

    void setCwcSwEnable(boolean isChecked);

    void setCwcSwEnable(boolean isChecked, boolean storeEnable);

    void setDomeLight(int type);

    void setDomeLightBright(int value);

    void setDomeLightBright(int value, boolean needSave);

    void setDomeLightSw(int lightType, boolean enable);

    void setDriveAutoLock(boolean enable);

    void setEasyLoadingSwitch(boolean enable);

    void setEbwEnable(boolean enable);

    void setEngineeringModeStatus(int status);

    void setEsbEnable(boolean enable);

    void setEsbEnable(boolean enable, boolean saveEnable);

    void setFrontMirrorHeat(boolean enable);

    void setHeadLampState(int groupId, boolean needSp);

    void setHighSpdCloseWin(boolean value);

    void setImsAutoVisionSw(int sw);

    void setImsBrightLevel(int level);

    void setImsMode(int mode);

    void setImsMode(int mode, boolean needSave);

    void setImsVisionCtrl(int control, int direction);

    void setLampHeightLevel(int height);

    void setLampHeightLevel(int height, boolean needSave);

    void setLeaveAutoLock(boolean enable);

    void setLedDrlEnable(boolean enable);

    void setLeftDoorHotKey(boolean enable);

    void setLeftSlideDoorMode(int mode);

    void setLightMeHome(boolean enable, boolean needSave);

    void setLightMeHomeTime(int time, boolean needSave);

    void setLockAvasSw(boolean enable);

    void setLowBeamOffConfirmSt(int state);

    void setMirrorAutoDown(boolean enable);

    void setMirrorAutoFoldEnable(boolean enable);

    void setMirrorHeat(boolean enable);

    void setMirrorLocation(int leftHPos, int leftVPos, int rightHPos, int rightVPos);

    void setNearAutoUnlock(boolean enable);

    void setParkLampIncludeFmB(boolean active);

    void setParkLampIncludeFmB(boolean active, boolean needSp);

    void setParkingAutoUnlock(boolean enable);

    void setPollingLightWelcomeMode(boolean enable);

    void setPollingOpenCfg(boolean enable);

    void setPsnSeatHeatLevel(int level);

    void setPsnSeatVentLevel(int level);

    void setRLSeatHeatLevel(int level);

    void setRRSeatHeatLevel(int level);

    void setRearFogLamp(boolean enable);

    void setRearLogoLightSw(boolean enable);

    void setRearSeatWelcomeMode(boolean enable, boolean needSave);

    void setRearWiperRepairMode(boolean on);

    void setReverseMirrorMode(int mode);

    void setReverseMirrorMode(int mode, boolean needMove);

    void setRightDoorHotKey(boolean enable);

    void setRightSlideDoorMode(int mode);

    void setRsbWarning(boolean enable);

    void setSdcBrakeCloseDoorCfg(int cfg);

    void setSdcKeyCloseCtrlCfg(int cfg);

    void setSdcKeyOpenCtrlCfg(int cfg);

    void setSdcMaxAutoDoorOpeningAngle(int angle);

    void setSdcWindowsAutoDownSwitch(boolean open);

    void setSeatHeatLevel(int level, boolean storeEnable);

    void setSeatVentLevel(int level, boolean storeEnable);

    void setSeatWelcomeMode(boolean enable);

    void setStealthMode(boolean enable);

    void setSteerHeatLevel(int level, boolean storeEnable);

    void setSteerHorPos(int pos);

    void setSteerPos(int[] pos);

    void setSteerVerPos(int pos);

    void setStopNfcCardSw(boolean enable);

    void setSunShadePos(int pos);

    void setTrailerHitchSwitchStatus(boolean enable);

    void setTrailerModeStatus(boolean enable);

    void setTransportMode(boolean enable);

    void setTrunkFullOpenPos(int pos);

    void setTrunkSensorEnable(boolean enable);

    void setUnlockResponse(int type);

    void setWinPos(int windowType, float position);

    void setWindowLockState(boolean enable);

    void setWindowMoveCmd(int window, int cmd);

    default void setWindowsMovePositions(float flPos, float frPos, float rlPos, float rrPos) {
    }

    void setWiperInterval(int interval);

    void setWiperRepairMode(boolean on);

    void setWiperSensitivity(int level, boolean needSave);

    void setWiperSensitivity(int level, boolean needSave, boolean inactive);

    void startArsInit();
}
