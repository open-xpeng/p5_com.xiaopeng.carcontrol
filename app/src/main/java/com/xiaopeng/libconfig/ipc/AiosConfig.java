package com.xiaopeng.libconfig.ipc;

/* loaded from: classes2.dex */
public class AiosConfig {

    /* loaded from: classes2.dex */
    public static class AiosAdapterId {
        public static final int CANCEL_NAVIGATION_ID = 0;
    }

    /* loaded from: classes2.dex */
    public static class DataCollectorId {
        public static final int DELETE_CANCEL_BCAN = 3;
        public static final int START_COLLECT_BCAN = 0;
        public static final int STOP_COLLECT_BCAN = 1;
        public static final int UPLOAD_BCAN_LOG = 2;
    }

    /* loaded from: classes2.dex */
    public static class IPC_CAR_CONTROL_MSG_ID {
        public static final int IPC_MSG_ID_CLOSE_ALL_WINDOWS = 200007;
        public static final int IPC_MSG_ID_CLOSE_FRONT_LEFT_WINDOW = 200009;
        public static final int IPC_MSG_ID_CLOSE_FRONT_RIGHT_WINDOW = 200011;
        public static final int IPC_MSG_ID_LIGHT_ME_HOME_DISABLE = 200002;
        public static final int IPC_MSG_ID_LIGHT_ME_HOME_ENABLE = 200001;
        public static final int IPC_MSG_ID_LOCK_CAR = 200005;
        public static final int IPC_MSG_ID_OPEN_ALL_WINDOWS = 200006;
        public static final int IPC_MSG_ID_OPEN_FRONT_LEFT_WINDOW = 200008;
        public static final int IPC_MSG_ID_OPEN_FRONT_RIGHT_WINDOW = 200010;
        public static final int IPC_MSG_ID_REAR_FOG_LAMP_CLOSE = 200004;
        public static final int IPC_MSG_ID_REAR_FOG_LAMP_OPEN = 200003;
    }

    /* loaded from: classes2.dex */
    public static class IPC_CHARGE_CONTROL_MSG_ID {
        public static final int IPC_MSG_ID_OPEN_CHARGE_INTERFACE = 200008;
        public static final int IPC_MSG_ID_OPEN_CHARGE_PROT = 200009;
        public static final int IPC_MSG_ID_START_TO_CHARGE = 200006;
        public static final int IPC_MSG_ID_STOP_TO_CHARGE = 200007;
    }

    /* loaded from: classes2.dex */
    public static class MSG_AIR_VALUE {
        public static final int AIOS_AIR_AUTO_MODE = 10501;
        public static final int AIOS_AIR_AUTO_MODE_CLOSE = 0;
        public static final int AIOS_AIR_AUTO_MODE_OPEN = 1;
        public static final int AIOS_AIR_BASE_SWITCH = 10101;
        public static final int AIOS_AIR_BASE_SWITCH_CLOSE = 0;
        public static final int AIOS_AIR_BASE_SWITCH_OPEN = 1;
        public static final int AIOS_AIR_BLOWING_MODE = 10401;
        public static final int AIOS_AIR_BLOWING_MODE_FACE = 1;
        public static final int AIOS_AIR_BLOWING_MODE_FACE_FOOT = 2;
        public static final int AIOS_AIR_BLOWING_MODE_FOOT = 3;
        public static final int AIOS_AIR_BLOWING_MODE_FOOT_DEFROST = 4;
        public static final int AIOS_AIR_BLOWING_MODE_FRONT_DEFROST = 5;
        public static final int AIOS_AIR_LOOP_MODE = 20101;
        public static final int AIOS_AIR_LOOP_MODE_INNER = 0;
        public static final int AIOS_AIR_LOOP_MODE_OUTSIDE = 1;
        public static final int AIOS_AIR_TEMP_AC = 10201;
        public static final int AIOS_AIR_TEMP_AC_CLOSE = 0;
        public static final int AIOS_AIR_TEMP_AC_OPEN = 1;
        public static final int AIOS_AIR_TEMP_AFT_DEFROST = 30101;
        public static final int AIOS_AIR_TEMP_AFT_DEFROST_CLOSE = 0;
        public static final int AIOS_AIR_TEMP_AFT_DEFROST_OPEN = 1;
        public static final int AIOS_AIR_TEMP_PRE_DEFROST = 10207;
        public static final int AIOS_AIR_TEMP_PRE_DEFROST_CLOSE = 0;
        public static final int AIOS_AIR_TEMP_PRE_DEFROST_OPEN = 1;
        public static final int AIOS_AIR_WIND_DECREASE = 10302;
        public static final int AIOS_AIR_WIND_INCREASE = 10301;
        public static final int AIOS_AIR_WIND_VALUE_TO = 10303;
    }

    /* loaded from: classes2.dex */
    public static class MSG_MUSIC_ID {
        public static final int IPC_MSG_ID_BT_DISMISS = 100003;
        public static final int IPC_MSG_ID_COLLECT_MUSIC = 100002;
        public static final int IPC_MSG_ID_CONTROL_MODEL = 100001;
    }

    /* loaded from: classes2.dex */
    public static class MSG_MUSIC_VALUE {
        public static final int IPC_MSG_VALUE_MODEL_LOOP = 2;
        public static final int IPC_MSG_VALUE_MODEL_RANDOM = 4;
        public static final int IPC_MSG_VALUE_MODEL_SINGLE = 3;
    }

    /* loaded from: classes2.dex */
    public static class MSG_NAVIGATION_ID {
        public static final int ID_CHARGE_STUB = 1004;
        public static final int ID_OPEN_FAVORITES = 1002;
        public static final int ID_OPEN_SETTINGS = 1003;
        public static final int ID_OVERVIEW = 1001;
        public static final int ID_ROUTE_PREFERENCES = 1006;
        public static final int ID_START_NAVIGATION = 1005;
    }

    /* loaded from: classes2.dex */
    public static class aios {
        public static final int status_exit_wait = 1;
        public static final int status_wait = 0;
    }

    /* loaded from: classes2.dex */
    public static class settings {
        public static final int auto = 2;
        public static final int brightness = 7;
        public static final int clean = 3;
        public static final int closewifi = 6;
        public static final int day = 0;
        public static final int incall_mute = 9;
        public static final int night = 1;
        public static final int openwifi = 5;
        public static final int rename = 4;
        public static final int show_bt_dialog = 8;
        public static final int sound_mode = 10;
    }

    /* loaded from: classes2.dex */
    public static class settings_value {
        public static final int down = 1;
        public static final int max = 3;
        public static final int min = 2;
        public static final int up = 0;
    }

    /* loaded from: classes2.dex */
    public static class systemui {
        public static final int change_bg = 0;
    }
}
