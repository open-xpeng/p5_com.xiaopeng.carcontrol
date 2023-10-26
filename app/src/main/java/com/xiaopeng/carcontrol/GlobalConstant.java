package com.xiaopeng.carcontrol;

import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.lib.http.CommonUtils;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class GlobalConstant {
    public static final int ERROR_CODE_EXCEPTION = 1;
    public static final int ERROR_CODE_INVALID = 2;
    public static final int RESULT_OK = 0;

    /* loaded from: classes.dex */
    public static final class ACTION {
        public static final String ACTION_BLUETOOTH_POPUP = "com.xiaopeng.intent.action.POPUP_BLUETOOTH";
        public static final String ACTION_BOSSKEY = "com.xiaopeng.intent.action.bosskey";
        public static final String ACTION_BOSSKEY_SPEECH = "xiaopeng.intent.action.UI_MIC_CLICK";
        public static final String ACTION_BOSSKEY_USER = "com.xiaopeng.intent.action.bosskey.user";
        public static final String ACTION_CANCEL_MICROPHONE_MUTE = "com.xiaopeng.carcontrol.intent.action.ACTION_CANCEL_MICROPHONE_MUTE";
        public static final String ACTION_CANCEL_MICROPHONE_UNMUTE = "com.xiaopeng.carcontrol.intent.action.ACTION_CANCEL_MICROPHONE_UNMUTE";
        public static final String ACTION_CAPSULE_CINEMA_GUIDE_SHOW = "com.xiaopeng.intent.action.CINEMA_USE_GUIDE";
        public static final String ACTION_CAPSULE_DC_CHARGE_SECURITY_CONFIRM_SHOW = "com.xiaopeng.carcontrol.action.CAPSULE_DC_CHARGE_SECURITY_CONFIRM_SHOW";
        public static final String ACTION_CAPSULE_LOW_BATTERY_TIPS_SHOW = "com.xiaopeng.carcontrol.action.CAPSULE_LOW_BATTERY_TIPS_SHOW";
        public static final String ACTION_CAPSULE_PASSIVE_EXIT_SHOW = "com.xiaopeng.carcontrol.action.CAPSULE_PASSIVE_EXIT_SHOW";
        public static final String ACTION_CAPSULE_SECURITY_CONFIRM_SHOW = "com.xiaopeng.carcontrol.action.CAPSULE_SECURITY_CONFIRM_SHOW";
        public static final String ACTION_CAPSULE_SLEEP_ALARM_STOP_DISMISS = "com.xiaopeng.carcontrol.action.CAPSULE_SLEEP_ALARM_STOP_DISMISS";
        public static final String ACTION_CAPSULE_SLEEP_ALARM_STOP_DISMISS_DIALOG = "com.xiaopeng.carcontrol.action.CAPSULE_SLEEP_ALARM_STOP_DISMISS_DIALOG";
        public static final String ACTION_CAPSULE_SLEEP_ALARM_STOP_SHOW = "com.xiaopeng.carcontrol.action.CAPSULE_SLEEP_ALARM_STOP_SHOW";
        public static final String ACTION_CONTROL_WINDOW_VENT = "com.xiaopeng.carcontrol.intent.action.ACTION_CONTROL_WINDOW_VENT";
        public static final String ACTION_DEMO_ELEMENT_DIRECT = "com.xiaopeng.carcontrol.action.DEMO_ELEMENT_DIRECT";
        public static final String ACTION_DIRECT_DISPATCH = "com.xiaopeng.carcontrol.action.DIRECT_DISPATCH";
        public static final String ACTION_DO_CUSTOM_X_KEY = "com.xiaopeng.intent.action.xkey";
        public static final String ACTION_EMERGENCY_IGOFF = "com.xiaopeng.intent.action.emergency.igoff";
        public static final String ACTION_EXIT_TRAILER_MODE = "com.xiaopeng.carcontrol.intent.action.CLOSE_TRAILER_PAGE";
        public static final String ACTION_GREET_QUIT = "com.xiaopeng.broadcast.GREET_QUIT";
        public static final String ACTION_GREET_TTS_COMPLETE = "com.xiaopeng.broadcast.GREET_TTS_COMPLETE";
        public static final String ACTION_MEDITATION_MODE = "com.xiaopeng.carcontrol.intent.action.SHOW_MEDITATION";
        public static final String ACTION_MEDITATION_MODE_EXIT = "com.xiaopeng.intent.action.CLOSE_MEDITATION";
        public static final String ACTION_MEDITATION_MODE_PLUS = "com.xiaopeng.carcontrol.intent.action.SHOW_MEDITATION_PLUS";
        public static final String ACTION_NEGATIVE = "com.xiaopeng.carcontrol.intent.action.ACTION_NEGATIVE";
        public static final String ACTION_OPERATOR_MICROPHONE = "com.xiaopeng.carcontrol.intent.action.ACTION_OPERATOR_MICROPHONE";
        public static final String ACTION_PM_STATUS_CHANGE = "com.xiaopeng.broadcast.ACTION_PM_STATUS_CHANGE";
        public static final String ACTION_POSITIVE = "com.xiaopeng.carcontrol.intent.action.ACTION_POSITIVE";
        public static final String ACTION_PRELOAD_CAR_CONTROL = "com.xiaopeng.carcontrol.intent.action.PRELOAD_CAR_CONTROL";
        public static final String ACTION_PRE_BOOT_COMPLETED = "android.intent.action.PRE_BOOT_COMPLETED";
        public static final String ACTION_SCREEN_STATUS_CHANGE = "com.xiaopeng.broadcast.ACTION_SCREEN_STATUS_CHANGE";
        public static final String ACTION_SCREEN_STATUS_CHANGE_DEVICE_EXTRA = "device";
        public static final String ACTION_SCREEN_STATUS_CHANGE_STATUS_EXTRA = "status";
        public static final String ACTION_SEAT_COMFORT = "com.xiaopeng.carcontrol.intent.action.SHOW_SEAT_COMFORT";
        public static final String ACTION_SHOW_AS_HEIGHT_SETTINGS_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_AS_HEIGHT_SETTINGS_CONTROL_PANEL";
        public static final String ACTION_SHOW_ATL_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_ATL_CONTROL_PANEL";
        public static final String ACTION_SHOW_CAR_CONTROL = "com.xiaopeng.carcontrol.intent.action.SHOW_CAR_CONTROL";
        public static final String ACTION_SHOW_CHILD_MODE_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_CHILD_MODE_SETTING_DIALOG";
        public static final String ACTION_SHOW_CMS_MIRROR_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_CMS_MIRROR_CONTROL_PANEL";
        public static final String ACTION_SHOW_CNGP_MAP_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_CNGP_MAP_PANEL";
        public static final String ACTION_SHOW_CUSTOM_DOOR_KEY_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_CUSTOM_DOOR_KEY_PANEL";
        public static final String ACTION_SHOW_CUSTOM_X_KEY_PANEL = "com.xiaopeng.intent.action.xkey.user";
        public static final String ACTION_SHOW_DRIVE_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_DRIVE_CONTROL_PANEL";
        public static final String ACTION_SHOW_DRIVE_SETTINGS_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_DRIVE_SETTINGS_CONTROL_PANEL";
        public static final String ACTION_SHOW_HVAC_PANEL = "com.xiaopeng.carcontrol.intent.action.SHOW_HVAC_PANEL";
        public static final String ACTION_SHOW_ISLA_SETTING_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_ISLA_SETTING_PANEL";
        public static final String ACTION_SHOW_LAMP_SETTING_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_LAMP_SETTING_PANEL";
        public static final String ACTION_SHOW_LLU_SETTING_PANEL = "com.xiaopeng.carcontrol.action.ACTION_SHOW_LLU_SETTING_PANEL";
        public static final String ACTION_SHOW_MATTRESS = "com.xiaopeng.carcontrol.intent.action.SHOW_MATTRESS";
        public static final String ACTION_SHOW_MICROPHONE_MUTE_DIALOG = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_MICROPHONE_MUTE_DIALOG";
        public static final String ACTION_SHOW_MICROPHONE_UNMUTE_DIALOG = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_MICROPHONE_UNMUTE_DIALOG";
        public static final String ACTION_SHOW_MIRROR_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_MIRROR_CONTROL_PANEL";
        public static final String ACTION_SHOW_NGP_SETTING_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_NGP_SETTING_PANEL";
        public static final String ACTION_SHOW_PSN_SEAT_BUTTON_SETTING_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_PSN_SEAT_BUTTON_SETTING_PANEL";
        public static final String ACTION_SHOW_PSN_SRS_CLOSE_DIALOG = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_PSN_SRS_CLOSE_DIALOG";
        public static final String ACTION_SHOW_REAR_LOGO_LIGHT_CONFIRM_DIALOG = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_REAR_LOGO_LIGHT_CONFIRM_DIALOG";
        public static final String ACTION_SHOW_SAYHI_SETTING_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_SAYHI_SETTING_PANEL";
        public static final String ACTION_SHOW_SCD_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_SCD_CONTROL_PANEL";
        public static final String ACTION_SHOW_SEAT_BUTTON_SETTING_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_SEAT_BUTTON_SETTING_PANEL";
        public static final String ACTION_SHOW_SEAT_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_SEAT_CONTROL_PANEL";
        public static final String ACTION_SHOW_SEAT_HEAT_DIALOG = "com.xiaopeng.carcontrol.intent.action.SHOW_SEAT_HEAT_DIALOG";
        public static final String ACTION_SHOW_SPECIAL_SAS_SETTING_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_SPECIAL_SAS_SETTING_PANEL";
        public static final String ACTION_SHOW_STEER_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_STEER_CONTROL_PANEL";
        public static final String ACTION_SHOW_SUNSHADE_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_SUNSHADE_CONTROL_PANEL";
        public static final String ACTION_SHOW_TRAILER_MODE_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_TRAILER_MODE_SETTING_DIALOG";
        public static final String ACTION_SHOW_VIP_SEAT_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_VIP_SEAT_CONTROL_PANEL";
        public static final String ACTION_SHOW_WINDOW_CONTROL_PANEL = "com.xiaopeng.carcontrol.intent.action.ACTION_SHOW_WINDOW_CONTROL_PANEL";
        public static final String ACTION_SMART_VEHICLE = "com.xiaopeng.action.SMART_VEHICLE";
        public static final String ACTION_SPACE_CAPSULE = "com.xiaopeng.carcontrol.intent.action.SHOW_SPACE_CAPSULE";
        public static final String ACTION_SPACE_CAPSULE_CHOOSE_SLEEP = "com.xiaopeng.carcontrol.intent.action.SHOW_SLEEP_CAPSULE_CHOOSE";
        public static final String ACTION_SPACE_CAPSULE_CHOOSE_SLEEP_FINISH = "com.xiaopeng.carcontrol.intent.action.SHOW_SLEEP_CAPSULE_CHOOSE_FINISH";
        public static final String ACTION_SPACE_CAPSULE_GUIDE = "com.xiaopeng.carcontrol.intent.action.SHOW_SPACE_CAPSULE_USE_GUIDE";
        public static final String ACTION_START_CINEMA_USE_GUIDE = "com.xiaopeng.carcontrol.intent.action.SHOW_CAPSULE_CINEMA_USE_GUIDE";
        public static final String ACTION_START_SERVICE = "com.xiaopeng.action.START_BOOT_SERVICE";
        public static final String ACTION_TRAILER_MODE = "com.xiaopeng.carcontrol.intent.action.SHOW_TRAILER_PAGE";
        public static final String ACTION_UPLOAD_CAR_STATUS_BI = "com.xiaopeng.carcontrol.action.UPLOAD_CAR_STATUS_BI";
        public static final String ACTION_VIP_SEAT = "com.xiaopeng.carcontrol.intent.action.SHOW_VIP_SEAT";
    }

    /* loaded from: classes.dex */
    public static class CALLS {
        public static final String[] RESCUE_CALLS_CN = {"400-819-3388", "020-37520800"};
        public static final String[] RESCUE_CALLS_NO = {"+47 80017060"};
        public static final String[] RESCUE_CALLS_SE = {"+46 812160608"};
        public static final String[] RESCUE_CALLS_DK = {"+45 78724343"};
        public static final String[] RESCUE_CALLS_NL = {"+31 202626822"};
    }

    /* loaded from: classes.dex */
    public static class COUNTRY {
        public static final String COUNTRY_CN = "CN";
        public static final String COUNTRY_DK = "DK";
        public static final String COUNTRY_NL = "NL";
        public static final String COUNTRY_NO = "NO";
        public static final String COUNTRY_SE = "SE";
    }

    /* loaded from: classes.dex */
    public static final class CarControlConfig {
        public static final int IPC_GET_PSN_SEAT_EQUAL_MEMORY = 16302;
        public static final int IPC_NAVI_ENTER_TELESCOPE_RANGE = 16104;
        public static final int IPC_NAVI_EXIT_TELESCOPE_RANGE = 16105;
        public static final int IPC_NAVI_SMART_AS_MOVE_DOWN_CMD = 16103;
        public static final int IPC_NAVI_SMART_AS_MOVE_UP_CMD = 16102;
        public static final int IPC_NAVI_SMART_AS_SAVE_RESULT = 16101;
        public static final int IPC_RESTORE_PSN_SEAT = 16301;
        public static final int IPC_XSPORT_RESUME_DRIVE_MODE = 16201;
        public static final int IPC_XSPORT_SAVE_DRIVE_MODE = 16202;
    }

    /* loaded from: classes.dex */
    public static final class EXTRA {
        public static final String KEY_BACK = "back";
        public static final String KEY_DISMISS_MICROPHONE_UNMUTE = "dismissUnmute";
        public static final String KEY_IN_SUB_MODE = "is_in_sub_mode";
        public static final String KEY_IS_MUTE_MICROPHONE = "is_mute_microphone";
        public static final String KEY_KEY_FUNC = "keyfunc";
        public static final String KEY_KEY_TYPE = "keytype";
        public static final String KEY_LOW_BATTERY_TIPS_STATUS = "is_low_battery_tips";
        public static final String KEY_MEDITATION_MODE_STATUS = "meditation_mode_start";
        public static final String KEY_MICROPHONE_DISPLAY_ID = "microphoneDisplayId";
        public static final String KEY_MIRROR_FROM = "from";
        public static final String KEY_MODE_TYPE = "mode_type";
        public static final String KEY_PRELOAD_UI = "preloadUI";
        public static final String KEY_SEAT_COMFORT_FROM = "seat_comfort_from";
        public static final String KEY_SEAT_COMFORT_SEAT = "seat_comfort_seat";
        public static final String KEY_SEAT_COMFORT_TYPE = "seat_comfort_type";
        public static final String KEY_SHOW_MIRROR_PANEL_INNER = "key_show_mirror_panel_inner";
        public static final String KEY_SHOW_SEAT_HEAT_DIALOG = "show_seat_heat_dialog";
        public static final String KEY_SPACE_CHOOSE_BED = "space_choose_sleep_bed";
        public static final String KEY_SPACE_CINEMA_BT_CONNECTED_STATUS = "space_cinema_bt_status";
        public static final String KEY_SPACE_CINEMA_GUIDE_FLAT = "space_cinema_guide_flat";
        public static final String KEY_SPACE_EXIT_HEAD_REST = "is_space_head_rest";
        public static final String KEY_SPACE_EXIT_REASON = "space_capsule_exit_reason";
        public static final String KEY_SPACE_MODE_STATUS = "space_mode_start";
        public static final String KEY_SPACE_SLEEP_GUIDE_AUTO = "space_sleep_guide_auto";
        public static final String KEY_SPACE_SLEEP_GUIDE_FLAT = "space_sleep_guide_flat";
        public static final String KEY_SPACE_SOURCE = "space_source";
        public static final String KEY_SPACE_SUB_MODE_INDEX = "space_sub_mode_index";
        public static final String KEY_USER_GUIDE_OPEN_FROM = "user_guide_open_from";
        public static final String KEY_VIP_SEAT = "is_vip_seat";
        public static final String MAIN_CATEGORY_INDEX = "category_index";
        public static final int PM_FAKE_OFF = 1;
        public static final int PM_NORMAL = 0;
        public static final int PM_SLEEP = 2;
        public static final String PM_STATUS = "pm_status";
        public static final int SEAT_COMFORT_SEAT_MAIN = 0;
        public static final int SEAT_COMFORT_SEAT_PSN = 1;
        public static final int SEAT_COMFORT_TYPE_MASSAGE = 0;
        public static final int SEAT_COMFORT_TYPE_RHYTHM = 1;
        public static final String SHOW_CAR_CONTROL = "showCarControl";
        public static final String SHOW_HVAC_PANEL = "showPanel";
        public static final boolean VALUE_BACK = false;
        public static final String VALUE_BOSS_LONG = "bosslong";
        public static final String VALUE_BOSS_SHORT = "bossshort";
        public static final String VALUE_FROM_DROP_DOWN_MENU = "drop_down_menu";
        public static final String VALUE_X_LONG = "long";
        public static final String VALUE_X_SHORT = "short";
        public static final String VALUE_X_SHORT_TOUCH = "shorttouch";
    }

    /* loaded from: classes.dex */
    public static class GLOBAL {
        public static final String FOLLOWED_VEHICLE_LOST_CONFIG = "followed_vehicle_lost_config";
        public static final String FOLLOWED_VEHICLE_LOST_CONFIG_DEF = "2";
        public static final String FOLLOWED_VEHICLE_LOST_CONFIG_NEW = "2";
        public static final String FOLLOWED_VEHICLE_LOST_CONFIG_OLD = "1";
    }

    /* loaded from: classes.dex */
    public static class LluDance {
        public static final String CURRENT_SELECT_RSC_ID = "llu_dance_current_select_rsc_id";
        public static final String LAST_PLAY_RSC_ID = "llu_dance_last_play_rsc_id";
    }

    /* loaded from: classes.dex */
    public static final class NOTIFICATION {
        public static final int INFO_FLOW_AIR_PROTECT = 14;
        public static final int INFO_FLOW_BATTERY_PROTECT = 13;
        public static final int INFO_FLOW_SELF_BIG = 18;
        public static final int INFO_FLOW_SELF_SMALL = 19;
        public static final String KEY_CARD_EXTRA_DATA = "extraData";
    }

    /* loaded from: classes.dex */
    public static final class PREFS {
        public static final String ATL_AUTO_BRIGHTNESS_BOOL = "pref_atl_auto_bright";
        public static final String ATL_DUAL_COLOR_MODE_BOOL = "pref_atl_dual_color_sw";
        public static final String ATL_DUAL_COLOR_STR = "pref_atl_dual_color";
        public static final String ATL_EFFECT_MODE_STR = "pref_atl_effect_mode";
        public static final String ATL_MANUAL_BRIGHTNESS_INT = "pref_atl_manual_bright";
        public static final String ATL_SINGLE_COLOR_INT = "pref_atl_single_color";
        public static final String AUTO_EASY_LOAD_BOOL = "auto_easy_load_bool";
        public static final String AWD_SETTING_MODE_INT = "pref_awd_setting_mode_int";
        public static final String EASY_LOADING_BOOL = "easy_loading_bool";
        public static final String FIRST_ENTER_XPOWER_FLAG = "pref_first_enter_xpower_flag_boolean";
        public static final String KEY_CHAIR_NAME_PSN_SELECT_ONE = "CHAIR_NAME_PSN_SELECT_ONE";
        public static final String KEY_CHAIR_NAME_PSN_SELECT_THREE = "CHAIR_NAME_PSN_SELECT_THREE";
        public static final String KEY_CHAIR_NAME_PSN_SELECT_TWO = "CHAIR_NAME_PSN_SELECT_TWO";
        public static final String KEY_CHAIR_NAME_RL_SELECT_ONE = "CHAIR_NAME_RL_SELECT_ONE";
        public static final String KEY_CHAIR_NAME_RL_SELECT_THREE = "CHAIR_NAME_RL_SELECT_THREE";
        public static final String KEY_CHAIR_NAME_RL_SELECT_TWO = "CHAIR_NAME_RL_SELECT_TWO";
        public static final String KEY_CHAIR_NAME_RR_SELECT_ONE = "CHAIR_NAME_RR_SELECT_ONE";
        public static final String KEY_CHAIR_NAME_RR_SELECT_THREE = "CHAIR_NAME_RR_SELECT_THREE";
        public static final String KEY_CHAIR_NAME_RR_SELECT_TWO = "CHAIR_NAME_RR_SELECT_TWO";
        public static final String KEY_CHAIR_POS_PSN_SELECT_ONE = "CHAIR_POSITION_ONE";
        public static final String KEY_CHAIR_POS_PSN_SELECT_THREE = "CHAIR_POSITION_THREE";
        public static final String KEY_CHAIR_POS_PSN_SELECT_TWO = "CHAIR_POSITION_TWO";
        public static final String KEY_CHAIR_POS_RL_SELECT_ONE = "CHAIR_RL_POSITION_ONE";
        public static final String KEY_CHAIR_POS_RL_SELECT_THREE = "CHAIR_RL_POSITION_THREE";
        public static final String KEY_CHAIR_POS_RL_SELECT_TWO = "CHAIR_RL_POSITION_TWO";
        public static final String KEY_CHAIR_POS_RR_SELECT_ONE = "CHAIR_RR_POSITION_ONE";
        public static final String KEY_CHAIR_POS_RR_SELECT_THREE = "CHAIR_RR_POSITION_THREE";
        public static final String KEY_CHAIR_POS_RR_SELECT_TWO = "CHAIR_RR_POSITION_TWO";
        public static final String KEY_CURRENT_SELECTED_PSN_HABBIT_ID = "current_selected_psn_habbit_id";
        public static final String KEY_CURRENT_SELECTED_RL_HABBIT_ID = "current_selected_rl_habbit_id";
        public static final String KEY_CURRENT_SELECTED_RR_HABBIT_ID = "current_selected_rr_habbit_id";
        public static final String N_GEAR_WARNING_SW_BOOL = "pref_n_gear_warning_sw";
        public static final String PREF_AIR_AUTO_PROTECT_INT = "pref_air_auto_protect_int";
        public static final String PREF_AIR_PROTECT_SOUND_STRING = "pref_air_protect_sound";
        public static final String PREF_ALC_BOOL = "pref_lca";
        public static final String PREF_ANTI_SICKNESS_BOOL = "pref_anti_sickness_bool";
        public static final String PREF_AS_CAMP_MODE_BOOL = "pref_as_camping_bool";
        public static final String PREF_AS_CUSTOM_ENABLE_BOOL = "pref_as_custom_enable_bool";
        public static final String PREF_AS_HEIGHT_LVL_INT = "pref_as_height_lvl";
        public static final String PREF_AS_LOCATION_CONTROL_SW_BOOL = "pref_as_location_control_sw_bool";
        public static final String PREF_AS_LOCATION_SW_BOOL = "pref_as_location_sw_bool";
        public static final String PREF_AS_WELCOME_MODE_BOOL = "pref_as_welcome_mode_bool";
        public static final String PREF_ATL_SWITCH_BOOL = "pref_atl_sw";
        public static final String PREF_AUTO_CLOSE_WINDOW_BOOL = "pref_auto_close_window_bool";
        public static final String PREF_AUTO_DOOR_HANDLE = "pref_auto_door_handle";
        public static final String PREF_AUTO_DRIVE_MODE_BOOL = "pref_auto_drive_mode_bool";
        public static final String PREF_AUTO_FAR_LAMP_INT = "pref_auto_farlamp";
        public static final String PREF_AUTO_LAMP_HEIGHT_BOOL = "pref_auto_lamp_height_bool";
        public static final String PREF_AUTO_PARK_BOOL = "pref_auto_park";
        public static final String PREF_AUTO_PARK_SOUND_INT = "pref_auto_park_sound";
        public static final String PREF_AVAS_BOOL = "pref_low_spd_sw";
        public static final String PREF_AVAS_EXTERNAL_SW_BOOL = "pref_avas_external_sw";
        public static final String PREF_AVAS_EXTERNAL_VOLUME_INT = "pref_external_volume_volume";
        public static final String PREF_AVAS_INT = "pref_low_spd_effect";
        public static final String PREF_AVAS_VOLUME_INT = "pref_low_spd_volume";
        public static final String PREF_AVH_AUTO_PARKING_BOOL = "pref_avh_auto_parking_bool";
        public static final String PREF_BOOT_EFFECT_INT = "pref_boot_effect";
        public static final String PREF_BOOT_EFFECT_OLD_INT = "pref_boot_effect_old";
        public static final String PREF_BSD_BOOL = "pref_bsd";
        public static final String PREF_CARCONTROL_MOVE_BACK = "pref_carcontrol_move_back";
        public static final String PREF_CARPET_LIGHT_WELCOME_MODE_BOOL = "pref_carpet_light_welcome_mode_bool";
        public static final String PREF_CAR_LICENSE_PLATE = "pref_car_license_plate";
        public static final String PREF_CDC_INT = "pref_cdc_int";
        public static final String PREF_CITY_NGP_BOOL = "pref_cngp";
        public static final String PREF_CIU_CAMERA_BOOL = "pref_ciu_camera";
        public static final String PREF_CIU_DISTRACT_BOOL = "pref_ciu_distract";
        public static final String PREF_CIU_FACE_BOOL = "pref_ciu_face";
        public static final String PREF_CIU_FATIGUE_BOOL = "pref_ciu_fatigue";
        public static final String PREF_CIU_RAIN_BOOL = "pref_ciu_rain_bool";
        public static final String PREF_CIU_WIPER_INTERVAL_GEAR_INT = "pref_ciu_wiper_interval_gear_int";
        public static final String PREF_CMS_AUTO_BRIGHT_SW = "pref_cms_auto_bright_sw";
        public static final String PREF_CMS_BRIGHTNESS = "pref_cms_brightness";
        public static final String PREF_CMS_HIGH_SPD_SW = "pref_cms_high_spd_sw";
        public static final String PREF_CMS_LOW_SPD_SW = "pref_cms_low_spd_sw";
        public static final String PREF_CMS_OBJECT_RECOGNIZE_SW = "pref_cms_object_recognize";
        public static final String PREF_CMS_POS = "pref_cms_pos";
        public static final String PREF_CMS_REVERSE_SW = "pref_cms_reverse_sw";
        public static final String PREF_CMS_TURN_SW = "pref_cms_turn_sw";
        public static final String PREF_CMS_VIEW_ANGLE = "pref_cms_view_angle";
        public static final String PREF_CMS_VIEW_RECOVERY_SW = "pref_cms_view_recovery";
        public static final String PREF_CWC_BOOL = "pref_cwc_bool";
        public static final String PREF_CWC_FR_BOOL = "pref_cwc_fr_bool";
        public static final String PREF_CWC_RL_BOOL = "pref_cwc_rl_bool";
        public static final String PREF_CWC_RR_BOOL = "pref_cwc_rr_bool";
        public static final String PREF_DOME_BRIGHT_INT = "pref_dome_bright";
        public static final String PREF_DOME_INT = "pref_dome";
        public static final String PREF_DOOR_BOSS_SW_BOOL = "pref_door_boss_sw";
        public static final String PREF_DOOR_KEY_INT = "pref_door_key";
        public static final String PREF_DOW_BOOL = "pref_dow";
        public static final String PREF_DRIVER_HEIGHT_INT = "pref_driver_height_int";
        public static final String PREF_DRIVER_MODE_INT = "pref_drive_mode_int";
        public static final String PREF_DRIVER_WEIGHT_INT = "pref_driver_weight_int";
        public static final String PREF_DRIVE_AUTO_LOCK_BOOL = "pref_drive_auto_lock_bool";
        public static final String PREF_DRV_LUMB_SW = "pref_drv_lumb_sw";
        public static final String PREF_DRV_SEAT_HEAT_LEVEL_INT = "pref_drv_seat_heat_level_int";
        public static final String PREF_DRV_SEAT_MEMORY_INDEX = "drv_sear_memory_index";
        public static final String PREF_DRV_SEAT_VENT_LEVEL_INT = "pref_drv_seat_vent_level_int";
        public static final String PREF_DSM_INT = "pref_dsm";
        public static final String PREF_EBW_BOOL = "pref_ebw_bool";
        public static final String PREF_ELECTRIC_BELT_BOOL = "pref_electric_belt_bool";
        public static final String PREF_ELK_BOOL = "pref_elk";
        public static final String PREF_ENERGY_RECYCLE_GRADE_INT = "pref_energy_recycle_int";
        public static final String PREF_ESP_BPF = "pref_esp_bpf";
        public static final String PREF_ESP_CST_SW = "pref_esp_cst_sw";
        public static final String PREF_ESP_MODE_BOOL = "pref_esp_mode_bool";
        public static final String PREF_FACE_RECOGNITION = "persist.sys.face_recognition";
        public static final String PREF_FACTORY_RESET_FLAG = "factory_reset_for_carcontrol";
        public static final String PREF_FCW_BOOL = "pref_fcw";
        public static final String PREF_FIND_CAR_RESPONSE_INT = "pref_find_car_response_int";
        public static final String PREF_FIRST_OPEN_CNGP_SW_BOOL = "pref_first_open_cngp_sw_bool";
        public static final String PREF_FIRST_OPEN_XNGP_SW_BOOL = "pref_first_open_xngp_sw_bool";
        public static final String PREF_FIRST_START_SPACE_CAPSULE_BOOL = "pref_first_start_capsule_bool";
        public static final String PREF_FOREST_ID_MED = "pref_forest_id_med";
        public static final String PREF_FRIEND_INT = "pref_friend_effect";
        public static final String PREF_HEAD_LAMP_INT = "pref_head_lamp_int";
        public static final String PREF_HVAC_AQS_LEVEL = "pref_hvac_aqs_level";
        public static final String PREF_HVAC_BLOW_MODE_AUTO = "pref_hvac_blow_mode_auto";
        public static final String PREF_HVAC_CIRCULATION_INTERVAL = "pref_hvac_circulation_interval";
        public static final String PREF_HVAC_SELF_DRY = "pref_hvac_self_dry_bool";
        public static final String PREF_IBS_INT = "pref_ibs_int";
        public static final String PREF_ICM_BRIGHTNESS_INT = "pref_icm_brightness_int";
        public static final String PREF_ICM_MEDITATION_BOOL = "pref_icm_meditation_bool";
        public static final String PREF_IMS_AUTO_VISION_SW = "pref_ims_auto_vision_sw";
        public static final String PREF_IMS_MODE = "pref_ims_mode";
        public static final String PREF_ISLA_CONFIRM_MODE_BOOL = "pref_isla_confirm_mode";
        public static final String PREF_ISLA_INT = "pref_isla";
        public static final String PREF_ISLA_SPD_RANGE_INT = "pref_isla_spd_range";
        public static final String PREF_ISLC_BOOL = "pref_islc";
        public static final String PREF_KEY_CTRL_PARK_BOOL = "pref_key_ctrl_park";
        public static final String PREF_KEY_CTRL_PARK_TYPE_INT = "pref_key_ctrl_park_type";
        public static final String PREF_LAMP_HEIGHT_LEVEL_INT = "pref_lamp_height_level_int";
        public static final String PREF_LAST_DRIVER_MODE_INT = "pref_last_drive_mode_int";
        public static final String PREF_LAST_ESP_MODE_BOOL = "pref_last_esp_mode";
        public static final String PREF_LCC_BOOL = "pref_laa";
        public static final String PREF_LCC_VIDEO_WATCHED_BOOL = "pref_lcc_video_watched_bool";
        public static final String PREF_LDW_BOOL = "pref_ldw";
        public static final String PREF_LEAVE_AUTO_LOCK_BOOL = "pref_leave_auto_lock_bool";
        public static final String PREF_LEFT_CHILD_LOCK_BOOL = "pref_left_child_lock";
        public static final String PREF_LEFT_DOOR_HOT_KEY_BOOL = "pref_left_door_hot_key";
        public static final String PREF_LEFT_SLIDER_DOOR_MODE = "pref_left_slider_door_mode";
        public static final String PREF_LIGHT_ME_HOME_BOOL = "pref_light_me_home_bool";
        public static final String PREF_LIGHT_ME_HOME_TIME_INT = "pref_light_me_home_time_int";
        public static final String PREF_LLU_AWAKE_MODE_INT = "pref_llu_awake_mode";
        public static final String PREF_LLU_CHARGE_SW_BOOL = "pref_llu_charge_sw";
        public static final String PREF_LLU_CONFIRM_DIALOG_AUTO_VOLUME = "pref_llu_confirm_dialog_auto_volume";
        public static final String PREF_LLU_CONFIRM_DIALOG_AUTO_WINDOW = "pref_llu_confirm_dialog_auto_window";
        public static final String PREF_LLU_CONFIRM_DIALOG_ORDER_PLAY = "pref_llu_confirm_dialog_order_play";
        public static final String PREF_LLU_LOCK_SW_BOOL = "pref_llu_lock_sw";
        public static final String PREF_LLU_LOCK_UNLOCK_ELE_BOOL = "pref_llu_lock_unlock_ele_sw";
        public static final String PREF_LLU_SHOW_DOUBLE_CONFIRM = "pref_llu_show_double_confirm";
        public static final String PREF_LLU_SLEEP_MODE_INT = "pref_llu_sleep_mode";
        public static final String PREF_LLU_SWITCH_BOOL = "pref_llu_sw";
        public static final String PREF_LLU_UNLOCK_SW_BOOL = "pref_llu_unlock_sw";
        public static final String PREF_LOCK_CLOSE_WIN_BOOL = "pref_lock_close_win_bool";
        public static final String PREF_LSS_INT = "pref_lss";
        public static final String PREF_MEDITATION_ATL_SW = "pref_meditation_atl_sw";
        public static final String PREF_MEDITATION_DAY_NIHGT_INT = "pref_meditation_day_night_int";
        public static final String PREF_MEDITATION_ID_MED = "pref_meditation_id_med";
        public static final String PREF_MEDITATION_ID_MOMENT = "pref_meditation_id_moment";
        public static final String PREF_MEDITATION_ID_VENUS = "pref_meditation_id_venus";
        public static final String PREF_MEDITATION_LAST_PLAY_INDEX_INT = "pref_meditation_last_play_index";
        public static final String PREF_MEM_PARK_BOOL = "pref_mem_park";
        public static final String PREF_MEM_PARK_VIDEO_WATCHED_BOOL = "pref_mem_park_video_watched_bool";
        public static final String PREF_METER_ALARM_VOLUME_INT = "pref_meter_alarm_volume_int";
        public static final String PREF_MICROPHONE_MUTE_BOOL = "pref_microphone_mute_bool";
        public static final String PREF_MIRROR_AUTO_FOLD_BOOL = "pref_mirror_auto_fold";
        public static final String PREF_MIRROR_DOWN_BOOL = "pref_mirror_down_bool";
        public static final String PREF_MIRROR_MEMORY_STRING = "pref_mirror_pos_string";
        public static final String PREF_MIRROR_REVERSE_INT = "pref_mirror_reverse";
        public static final String PREF_MM_MEDIA_SRC_BOOL = "pref_mm_media_src_bool";
        public static final String PREF_MM_SCREEN_BRIGHT_BOOL = "pref_mm_screen_brightness_bool";
        public static final String PREF_MM_TEMP_BOOL = "pref_mm_temp_bool";
        public static final String PREF_MM_WIND_MODE_BOOL = "pref_mm_wind_mode_bool";
        public static final String PREF_MM_WIND_POWER_BOOL = "pref_mm_wind_power_bool";
        public static final String PREF_NEAR_AUTO_UNLOCK_BOOL = "pref_near_auto_unlock_bool";
        public static final String PREF_NEW_DRIVE_XPEDAL_BOOL = "pref_new_drive_xpedal_bool";
        public static final String PREF_NFC_KEY_ENABLE_BOOL = "pref_nfc_key_enable_bool";
        public static final String PREF_NGP_AUTO_LCS_BOOL = "pref_ngp_auto_lcs";
        public static final String PREF_NGP_BOOL = "pref_ngp";
        public static final String PREF_NGP_FAST_LANE_BOOL = "pref_ngp_fast_lane";
        public static final String PREF_NGP_LC_MODE_INT = "pref_ngp_lc_mode";
        public static final String PREF_NGP_REMIND_MODE_INT = "pref_ngp_remind_mode";
        public static final String PREF_NGP_TIP_WIN_BOOL = "pref_ngp_tip_win";
        public static final String PREF_NGP_TRUCK_OFFSET_BOOL = "pref_ngp_truck_offset";
        public static final String PREF_NORMAL_SPEC_DRIVE_MODEL_BOOL = "pref_normal_special_drive_bool";
        public static final String PREF_NRA_INT = "pref_nra";
        public static final String PREF_PARK_AUTO_UNLOCK_BOOL = "pref_parking_auto_unlock_bool";
        public static final String PREF_PARK_LAMP_B_BOOL = "pref_park_lamp_b_bool";
        public static final String PREF_PHONE_CTRL_PARK_BOOL = "pref_phone_ctrl_park";
        public static final String PREF_PHONE_CTRL_PARK_TYPE_INT = "pref_phone_ctrl_park_type";
        public static final String PREF_POLLING_LIGHT_WELCOME_MODE_BOOL = "pref_polling_light_welcome_mode_bool";
        public static final String PREF_PSN_LUMB_SW = "pref_psn_lumb_sw";
        public static final String PREF_PSN_SEAT_HEAT_LEVEL_INT = "pref_psn_seat_heat_level_int";
        public static final String PREF_PSN_SEAT_MEMORY_INDEX0 = "psn_seat_memory_index0";
        public static final String PREF_PSN_SEAT_MEMORY_INDEX1 = "psn_seat_memory_index1";
        public static final String PREF_PSN_SEAT_MEMORY_INDEX2 = "psn_seat_memory_index2";
        public static final String PREF_PSN_SRS_ENABLE_BOOL = "pref_psn_srs_enable_bool";
        public static final String PREF_PSN_WELCOME_MODE_BOOL = "pref_psn_welcome_mode_bool";
        public static final String PREF_RCTA_BOOL = "pref_rcta";
        public static final String PREF_RCW_BOOL = "pref_rcw";
        public static final String PREF_REAR_BELT_WARNING_BOOL = "pref_rear_belt_warning_bool";
        public static final String PREF_REAR_LOGO_LIGHT_SW = "pref_rear_car_light_sw";
        public static final String PREF_REAR_SEAT_WELCOME_MODE_BOOL = "pref_rear_seat_welcome_mode_bool";
        public static final String PREF_REMOTE_CALL_CAR_BOOL = "pref_remote_call_car";
        public static final String PREF_RIGHT_CHILD_LOCK_BOOL = "pref_right_child_lock";
        public static final String PREF_RIGHT_DOOR_HOT_KEY_BOOL = "pref_right_door_hot_key";
        public static final String PREF_RIGHT_SLIDER_DOOR_MODE = "pref_right_slider_door_mode";
        public static final String PREF_SDC_AUTO_WIN_DOWN_BOOL = "pref_sdc_auto_win_down";
        public static final String PREF_SDC_BRAKE_CLOSE_CFG = "pref_sdc_brake_close";
        public static final String PREF_SDC_KEY_CFG_INT = "pref_sdc_key_cfg";
        public static final String PREF_SDC_MAX_AUTO_DOOR_OPENING_ANGLE = "pref_sdc_max_auto_door_opening_angle";
        public static final String PREF_SEA_ID_MED = "pref_sea_id_med";
        public static final String PREF_SIDE_REVERSE_WARNING_BOOL = "pref_side_reverse";
        public static final String PREF_SMART_HVAC_BOOL = "pref_smart_hvac_bool";
        public static final String PREF_SNOW_MODE_ENERGY_CACHE = "pref_snow_mode_energy_cache";
        public static final String PREF_SOLDIER_STATUS_INT = "pref_soldier_status";
        public static final String PREF_SPACE_CAPSULE_SLEEP_MUSIC = "pref_space_capsule_sleep_music";
        public static final String PREF_SPEC_DRIVE_MODE_INT = "pref_special_drive_mode_int";
        public static final String PREF_SPEED_LIMIT_BOOL = "pref_speed_limit_bool";
        public static final String PREF_SPEED_LIMIT_VALUE_INT = "pref_speed_limit_int";
        public static final String PREF_STEER_POS_STRING = "pref_steer_pos_string";
        public static final String PREF_TRUNK_OPEN_POS_INT = "pref_trunk_open_pos_int";
        public static final String PREF_TRUNK_SENSOR_ENABLE_BOOL = "pref_trunk_sensor_enable_bool";
        public static final String PREF_UNLOCK_RESPONSE_INT = "pref_unlock_response_int";
        public static final String PREF_USER_DEFINE_MODE_INFO_STR = "pref_user_define_drive_mode_info_str";
        public static final String PREF_USER_DEFINE_MODE_INT = "pref_user_define_drive_mode_int";
        public static final String PREF_WELCOME_MODE_BOOL = "pref_welcome_mode_bool";
        public static final String PREF_WHEEL_DEFINED_BUTTON_INT = "pref_wheel_defined_button_int";
        public static final String PREF_WHEEL_EPS_INT = "pref_wheel_eps_int";
        public static final String PREF_WHEEL_X_KEY_INT = "pref_wheel_x_key";
        public static final String PREF_WINDOW_SYNC_BOOL = "pref_window_sync_bool";
        public static final String PREF_WIN_LOCK_STATE = "pref_win_lock_state";
        public static final String PREF_WIN_REMOTE_CTRL_TYPE_INT = "pref_win_remote_ctrl";
        public static final String PREF_WIPER_INTERVAL_GEAR_INT = "pref_wiper_interval_gear_int";
        public static final String PREF_XPEDAL_BOOL = "pref_xpedal_bool";
        public static final String TTM_STATUS_BOOL = "pref_ttm_status_bool";
        public static final String WHEEL_KEY_PROTECT_BOOL = "pref_wheel_key_protect";
    }

    /* loaded from: classes.dex */
    public static final class SCREEN {
        public static final int SCREEN_ID_DRV = 0;
        public static final int SCREEN_ID_PSN = 1;
        public static final int SCREEN_ID_REAR = 2;
    }

    /* loaded from: classes.dex */
    public static final class SELF_CHECK {
        public static final int CHECKED = 1;
        public static final int CHECKED_NO_ERROR = 2;
        public static final int CHECKING = 0;
    }

    /* loaded from: classes.dex */
    public static class SPACE {
        public static final String SPACE_CAPSULE_AUTO = "0";
        public static final String SPACE_CAPSULE_CINEMA_MODE = "space_capsule_cinema";
        public static final int SPACE_CAPSULE_CINEMA_MODE_VALUE = 2;
        public static final int SPACE_CAPSULE_CINEMA_PREPARE_MODE_VALUE = 5;
        public static final int SPACE_CAPSULE_CINEMA_RESTORE_MODE_VALUE = 6;
        public static final String SPACE_CAPSULE_DAY = "1";
        public static final String SPACE_CAPSULE_DAY_NIGHT_MODE = "space.capsule.day.night";
        public static final int SPACE_CAPSULE_INVALIDATE_MODE_VALUE = -1;
        public static final int SPACE_CAPSULE_LOW_BATTERY_ENTER_VALUE = 60;
        public static final int SPACE_CAPSULE_LOW_BATTERY_TIPS_VALUE = 120;
        public static final int SPACE_CAPSULE_MATTRESS_MODE_VALUE = 8;
        public static final String SPACE_CAPSULE_MODE = "space_capsule";
        public static final String SPACE_CAPSULE_NIGHT = "2";
        public static final int SPACE_CAPSULE_SEAT_FLAT_MODE_VALUE = 7;
        public static final int SPACE_CAPSULE_SEAT_START_FLAT = 1;
        public static final int SPACE_CAPSULE_SEAT_START_RESTORE = 2;
        public static final int SPACE_CAPSULE_SLEEP_BED = 2;
        public static final String SPACE_CAPSULE_SLEEP_BGM_OPEN = "space.capsule.sleepBgmOpen";
        public static final String SPACE_CAPSULE_SLEEP_MODE = "space_capsule_sleep";
        public static final int SPACE_CAPSULE_SLEEP_MODE_VALUE = 1;
        public static final int SPACE_CAPSULE_SLEEP_PREPARE_MODE_VALUE = 3;
        public static final int SPACE_CAPSULE_SLEEP_RESTORE_MODE_VALUE = 4;
        public static final String SPACE_CAPSULE_SLEEP_UG_SHOW_NEXT_TIME_ENTER = "space.capsule.showUGNextTimeEnter";
        public static final String SPACE_CAPSULE_SLEEP_UG_SHOW_NEXT_TIME_EXIT = "space.capsule.showUGNextTimeExit";
        public static final int STATE_BT_MEDIA_CONNECTED = 5;
    }

    /* loaded from: classes.dex */
    public static class UnityDialogScene {
        public static final String DIALOG_EPB = "epb_dialog";
        public static final String DIALOG_MIRROR_CTRL = "mirror_ctrl_panel";
        public static final String DIALOG_NARROW_SPACE = "narrow_space_warn_dialog";
        public static final String DIALOG_READY_TIMEOUT_BLE_KEY = "ready_timeout_ble_key_dialog";
        public static final String DIALOG_READY_TIMEOUT_NFC_KEY = "ready_timeout_nfc_key_dialog";
        public static final String DIALOG_READY_WITHOUT_KEY = "ready_without_key_dialog";
        public static final String DIALOG_READY_WITHOUT_NFC_KEY = "ready_without_nfc_key_dialog";
        public static final String DIALOG_READY_WITHOUT_WATCH_KEY = "ready_without_watch_key_dialog";
        public static final String DIALOG_RESET_AC_CHARGEPORT = "reset_ac_chargeport_dialog";
        public static final String DIALOG_RESET_DC_CHARGEPORT = "reset_dc_chargeport_dialog";
        public static final String DIALOG_SHOW_CUSTOM_X_KEY = "custom_x_key_dialog";
        public static final String DIALOG_SPEECH_MUTE = "speech_mute_dialog";
        public static final String DIALOG_SPEECH_UNMUTE = "speech_unmute_dialog";
        public static final String DIALOG_TELESCOPE_MRR = "close_mrr_dialog";
    }

    /* loaded from: classes.dex */
    public static class UnityDropDownScene {
        public static final String DROP_DOWN_MENU_PSN_SEAT = "psn_seat_drop_down_menu";
        public static final String DROP_DOWN_MENU_P_GEAR = "p_gear_drop_down_menu";
        public static final String DROP_DOWN_MENU_SEAT = "seat_drop_down_menu";
    }

    /* loaded from: classes.dex */
    public static final class DEFAULT {
        public static final int AIR_AUTO_PROTECT = 1;
        public static final String AIR_PROTECT_SOUND = "/system/media/audio/xiaopeng/cdu/wav/CDU_air_protect_3.wav";
        public static final boolean ALC = false;
        public static final boolean ANTI_SICK_SW = false;
        public static final boolean AS_AUTO_EASY_LOAD_MODE = false;
        public static final boolean AS_CUSTOM_MODE = false;
        public static final boolean AS_EASY_LOADING_MODE = false;
        public static final int AS_HEIGHT = 3;
        public static final int AS_HEIGHT_LVL = -1;
        public static final boolean AS_REPAIR_MODE = false;
        public static final int AS_SOFT = 2;
        public static final boolean AS_WELCOME_MODE = false;
        public static final boolean ATL_AUTO_BRIGHTNESS = true;
        public static final boolean ATL_DUAL_COLOR = false;
        public static final int ATL_DUAL_FIRST_COLOR = 1;
        public static final int ATL_DUAL_SECOND_COLOR = 6;
        public static final String ATL_EFFECT_MODE = "stable_effect";
        public static final int ATL_MANUAL_BRIGHTNESS = 100;
        public static final int ATL_SINGLE_COLOR = 14;
        public static final boolean ATL_SWITCH = true;
        public static final boolean AUTO_DOOR_HANDLE;
        public static final boolean AUTO_DRIVE_MODE = false;
        public static final int AUTO_FAR_LAMP = 0;
        public static final boolean AUTO_LAMP_HEIGHT = true;
        public static final boolean AUTO_PARK = false;
        public static final int AUTO_PARK_SOUND_EFFECT = 1;
        public static final int AUTO_PILOT_TTS = 1;
        public static final int AVAS_EFFECT = 1;
        public static final boolean AVAS_ENABLED = true;
        public static final boolean AVAS_EXTERNAL_SW = true;
        public static final int AVAS_EXTERNAL_VOLUME = 100;
        public static final int AVAS_VOLUME = 100;
        public static final boolean AVH = true;
        public static final int AWD_SETTING = 2;
        public static final int BOOT_SOUND_EFFECT = 1;
        public static final boolean BSD = true;
        public static final boolean CARCONTROL_MOVE_BACK_DEDAULT = true;
        public static final boolean CARPET_LIGHT_WELCOME_MODE = true;
        public static final int CDC = 1;
        public static final boolean CHILD_LEFT_LOCK = false;
        public static final int CHILD_LOCK = 1;
        public static final boolean CHILD_RIGHT_LOCK = false;
        public static final boolean CITY_NGP_SW = false;
        public static final boolean CIU_CAMERA = true;
        public static final boolean CIU_DISTRACT = false;
        public static final boolean CIU_FACE = true;
        public static final boolean CIU_FATIGUE = false;
        public static final boolean CIU_RAIN_SW = false;
        public static final int CIU_WIPER_INTERNAL = 3;
        public static final boolean CMS_AUTO_BRIGHT_SW = true;
        public static final int CMS_BRIGHT = 50;
        public static final boolean CMS_HIGH_SPD_SW = true;
        public static final boolean CMS_LOW_SPD_SW = true;
        public static final boolean CMS_OBJECT_RECOGNIZE = true;
        public static final boolean CMS_REVERSE_SW = true;
        public static final boolean CMS_TURN_SW = true;
        public static final int CMS_VIEW_ANGLE = 1;
        public static final boolean CMS_VIEW_RECOVERY_SW = true;
        public static final boolean CWC_SW = true;
        public static final boolean D2_ISLC = true;
        public static final boolean D2_LDW = true;
        public static final int DOME = 1;
        public static final int DOME_BRIGHT = 3;
        public static final boolean DOOR_KEY_SW = false;
        public static final int DOOR_KEY_VALUE = 0;
        public static final boolean DOW = true;
        public static final int DRIVER_MODE;
        public static final boolean DRIVE_AUTO_LOCK = true;
        public static final int DRV_SEAT_HEAT_LEVEL = 0;
        public static final int DRV_SEAT_MEMORY_INDEX = 0;
        public static final int DRV_SEAT_VENT_LEVEL = 0;
        public static final int DSM = 0;
        public static final boolean ELECTRIC_SEAT_BELT = true;
        public static final boolean ELK = false;
        public static final boolean EMERGENCY_BREAK_WARNING;
        public static final int ENERGY_RECYCLE_GRADE;
        public static final boolean ESP = true;
        public static final int ESP_BPF_MODE = 1;
        public static final boolean ESP_CST_SW = false;
        public static final String FACTORY_RESET_VALUE = "factory_reset";
        public static final boolean FCW = true;
        public static final int FIND_CAR_RESPONSE = 1;
        public static final boolean FIRST_OPEN_CNGP_SW = true;
        public static final boolean FIRST_OPEN_XNGP_SW = true;
        public static final int FRIEND_EFFECT = 1;
        public static final int HEAD_LAMP_GROUP = 3;
        public static final boolean HIGH_SPD_CLOSE_WIN = false;
        public static final int HVAC_AQS_LEVEL = 2;
        public static final int HVAC_CIRCULATION_INTERVAL = 2;
        public static final boolean HVAC_SELF_DRY = true;
        public static final boolean HVAC_SINGLE_MODE = true;
        public static final int IBS = 0;
        public static final int IMS_AUTO_VISION_SW = 1;
        public static final int IMS_MODE = 1;
        public static final int ISLA = 1;
        public static final boolean ISLA_CONFIRM_MODE = true;
        public static final int ISLA_SPD_RANGE = 2;
        public static final int KEYBOARD_TOUCH_DIRECTION = 1;
        public static final boolean KEYBOARD_TOUCH_PROMPT = true;
        public static final int KEYBOARD_TOUCH_SPEED = 1;
        public static final int KEY_PARK_CFG = 2;
        public static final boolean KEY_PARK_SW = false;
        public static final int LAMP_HEIGHT_LEVEL = 0;
        public static final int LAST_DRIVER_MODE;
        public static final boolean LCC = false;
        public static final boolean LEAVE_AUTO_LOCK = true;
        public static final boolean LED_DRL = true;
        public static final boolean LEFT_DOOR_HOT_KEY = false;
        public static final boolean LIGHT_ME_HOME = true;
        public static final int LIGHT_ME_HOME_TIME = 1;
        public static final int LLU_AWAKE_MODE = 1;
        public static final boolean LLU_CHARGE_SW = true;
        public static final boolean LLU_CONFIRM_DIALOG_AUTO_VOLUME = true;
        public static final boolean LLU_CONFIRM_DIALOG_AUTO_WINDOW = true;
        public static final boolean LLU_CONFIRM_DIALOG_ORDER_PLAY = false;
        public static final boolean LLU_LOCK_SW = true;
        public static final boolean LLU_LOCK_UNLOCK_ELE = false;
        public static final boolean LLU_SHOW_DOUBLE_CONFIRM_DIALOG = true;
        public static final int LLU_SLEEP_MODE = 1;
        public static final boolean LLU_SWITCH = true;
        public static final boolean LLU_UNLOCK_SW = true;
        public static final boolean LOCK_CLOSE_WIN = true;
        public static final boolean LOCK_STATE_WIN = false;
        public static final int LSS = 0;
        public static final boolean MEM_PARK = false;
        public static final int METER_ALARM_VOLUME = 2;
        public static final boolean METER_MENU_MEDIA_SOURCE = true;
        public static final boolean METER_MENU_SCREEN_BRIGHTNESS = true;
        public static final boolean METER_MENU_TEMPERATURE = true;
        public static final boolean METER_MENU_WIND_MODE = true;
        public static final boolean METER_MENU_WIND_POWER = true;
        public static final boolean MICROPHONE_MUTE = false;
        public static final boolean MIRROR_AUTO_DOWN = true;
        public static final boolean MIRROR_AUTO_FOLD_SW = true;
        public static final int MIRROR_REVERSE_MODE = 3;
        public static final int MOTOR_POWER = 1;
        public static final boolean NEAR_AUTO_UNLOCK = true;
        public static final boolean NEW_DRIVE_ARCH_X_PEDAL_MODE = false;
        public static final boolean NFC_KEY_ENABLE = true;
        public static final boolean NGP_DRIVER_CONFIRM_LCS = false;
        public static final boolean NGP_FAST_LANE = false;
        public static final int NGP_LC_MODE = 1;
        public static final int NGP_REMIND_MODE = 0;
        public static final boolean NGP_SW = false;
        public static final boolean NGP_TIPS_WINDOW = false;
        public static final boolean NGP_TRUCK_OFFSET = false;
        public static final String NORMAL_DRIVE_MODE_ADAPTIVE_CFG = "3_2_6_2_0_1_0_1";
        public static final String NORMAL_DRIVE_MODE_COMFORT_CFG = "3_1_4_1_1_1_0_1";
        public static final String NORMAL_DRIVE_MODE_ECO_CFG = "3_2_2_-1_0_1_0_1";
        public static final String NORMAL_DRIVE_MODE_MUD_CFG = "1_3_4_1_2_1_1_1";
        public static final String NORMAL_DRIVE_MODE_NORMAL_CFG = "3_2_1_-1_0_1_0_1";
        public static final String NORMAL_DRIVE_MODE_SPORT_CFG = "4_3_3_-1_2_2_0_1";
        public static final boolean NORMAL_DRIVE_SW = true;
        public static final int NRA = 2;
        public static final boolean N_GEAR_WARNING = true;
        public static final boolean PARK_AUTO_UNLOCK = false;
        public static final boolean PARK_LAMP_B = true;
        public static final int PHONE_PARK_CFG = 2;
        public static final boolean PHONE_PARK_SW = false;
        public static final boolean POLLING_LIGHT_WELCOME_MODE = true;
        public static final int POWER_RESPONSE = 1;
        public static final int POWER_RESPONSE_FOR_XPOWER = 5;
        public static final int PSN_SEAT_HEAT_LEVEL = 0;
        public static final boolean PSN_SRS_ENABLE = true;
        public static final boolean PSN_WELCOME_MODE = true;
        public static final boolean RCTA = false;
        public static final boolean RCW = true;
        public static final boolean REAR_LOGO_LIGHT = false;
        public static final boolean REAR_SEAT_BELT_WARNING = true;
        public static final boolean REAR_SEAT_WELCOME_MODE = true;
        public static final boolean REMOTE_CALL_CAR = false;
        public static final boolean RIGHT_DOOR_HOT_KEY = false;
        public static final int SDC_BRAKE_CLOSE_CFG = 0;
        public static final int SDC_KEY_CFG = 1;
        public static final int SDC_MAX_AUTO_DOOR_OPENING_ANGLE = 100;
        public static final boolean SDC_WIN_AUTO_DOWN = true;
        public static final boolean SEAT_LUMB_SW = false;
        public static final boolean SIDE_REVERS_WARNING = true;
        public static final int SLIDE_DOOR_MODE = 0;
        public static final boolean SMART_HVAC = false;
        public static final boolean SNOW_MODE = false;
        public static final int SNOW_MODE_ENERGY_CACHE = -1;
        public static final int SOLDIER_LEVEL = 2;
        public static final int SPECIAL_DRIVER_MODE = 0;
        public static final String SPECIAL_DRIVE_MODE_ECO_PLUS_CFG = "4_3_2_3_-1_1_0_1";
        public static final String SPECIAL_DRIVE_MODE_X_POWER_CFG = "4_3_5_1_2_2_0_2";
        public static final boolean SPEED_LIMIT = false;
        public static final int SPEED_LIMIT_VALUE = 80;
        public static final boolean STEALTH_MODE = false;
        public static final int STEERING_X_VALUE = 0;
        public static final int STEER_EPS = 0;
        public static final int[] STEER_EXHIBITION_POS;
        public static final int STEER_HOR_POS = 50;
        public static final String STEER_POS;
        public static final int STEER_VER_POS = 50;
        public static final int TRUNK_FULL_OPEN_POS = 6;
        public static final boolean TRUNK_SENSOR_ENABLE = true;
        public static final int TTM_STATUS = 0;
        public static final int UNLOCK_RESPONSE;
        public static final int USER_DEFINE_DRIVE_MODE = 11;
        public static final String USER_DEFINE_DRIVE_MODE_CFG;
        public static final boolean VMC_SYSTEM_STATE = false;
        public static final boolean WELCOME_MODE = true;
        public static final boolean WHEEL_KEY_PROTECT = true;
        public static final int WINDOW_REMOTE_CTRL = 3;
        public static final boolean WINDOW_SYNC = false;
        public static final int WIPER_INTERNAL = 3;
        public static final boolean WIPER_REPAIR_MODE = false;
        public static final int WIPER_Sensitivity;
        public static final boolean X_PEDAL_SW = false;

        static {
            DRIVER_MODE = CarBaseConfig.getInstance().isSupportNormalDriveMode() ? 10 : 0;
            LAST_DRIVER_MODE = CarBaseConfig.getInstance().isSupportNormalDriveMode() ? 10 : 0;
            int i = CarBaseConfig.getInstance().isSupportEnergyRecycleMediumLevel() ? 2 : 1;
            ENERGY_RECYCLE_GRADE = i;
            EMERGENCY_BREAK_WARNING = !CarBaseConfig.getInstance().isSupportEbw();
            AUTO_DOOR_HANDLE = !CarBaseConfig.getInstance().isSupportSdc();
            UNLOCK_RESPONSE = CarBaseConfig.getInstance().isNewAvasArch() ? 2 : 1;
            STEER_POS = Arrays.toString(new int[]{50, 50});
            STEER_EXHIBITION_POS = new int[]{100, 50};
            USER_DEFINE_DRIVE_MODE_CFG = "3_2_1_" + i + "_0_1_0_1";
            WIPER_Sensitivity = CarBaseConfig.getInstance().isWiperSensitiveNegative() ? 2 : 3;
        }
    }

    /* loaded from: classes.dex */
    public static class URL {
        public static final String GET_PLATE;
        public static final String HOST;
        public static final String MAINTENANCE_ADVICE;
        public static final String MAINTENANCE_LAST;

        static {
            String str = CommonUtils.HTTP_HOST;
            HOST = str;
            MAINTENANCE_LAST = str + "/voss/public/v1/module/maintenance/items";
            MAINTENANCE_ADVICE = str + "/voss/public/v1/module/maintenance/advice";
            GET_PLATE = str + "/biz/v5/cdu/getPlateNo";
        }
    }
}
