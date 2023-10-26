package com.xiaopeng.speech.common;

/* loaded from: classes2.dex */
public class SpeechConstant {
    public static final int ASR_NULL_RETRY_TIMES = 1;
    public static long CLOUD_TIME = 663;
    public static final boolean DEFAULT_ENABLE_ANGLE_TIP = false;
    public static final boolean DEFAULT_ENABLE_ASR_INTERRUPT = false;
    public static final boolean DEFAULT_ENABLE_FASTWAKE = true;
    public static final boolean DEFAULT_ENABLE_INTERRUPT_WAKEUP = false;
    public static final boolean DEFAULT_ENABLE_ONESHOT = true;
    public static final boolean DEFAULT_ENABLE_SOUND_LOCATION = true;
    public static final boolean DEFAULT_ENABLE_SOUND_LOCK = false;
    public static final boolean DEFAULT_ENABLE_WAKEUP = true;
    public static final boolean DEFAULT_ENABLE_WELCOME = true;
    public static final int DM_ERROR_SOUND_TIME = 300;
    public static final String KEY_BF_ENABLED = "SpeechEnv.enable_bf_check";
    public static final String KEY_CUSTOM_WAKEUP_CONFIG = "key.custom.wakeup.config";
    public static final String KEY_CUSTOM_WAKEUP_INDEX = "key.custom.wakeup_index";
    public static final String KEY_DATA_AUTHORIZED = "key.speech.data.authorized";
    public static final String KEY_DATA_AUTHORIZED_ONCE = "key.speech.data.authorized.once";
    public static final String KEY_DATA_AUTHORIZED_OPERATION = "key.speech.data.authorized.operation";
    public static final String KEY_DIALOGUE_ENABLE = "key.dialogue.enable";
    public static final String KEY_ENABLE_ANGLE_TIP = "SpeechConfig.angle_tip_enable";
    public static final String KEY_ENABLE_SPEECH_SCENE_TIP = "speech.scene.tip.enable";
    public static final String KEY_INTERRUPTION_ENABLE = "key.interruption.enable";
    public static final String KEY_MIC_WAKEUP_BY_TTS = "key.mic.wakeup.by.tts";
    public static final String KEY_SPEECH_SCENE_ENABLE = "key.speech.scene.enabke";
    public static final String KEY_SPEECH_SCENE_ENABLE_WITH_CALLBACK = "key.speech.scene.enable.callback";
    public static final String KEY_SPEECH_URL = "key.speech.url";
    public static final String KEY_SUPER_DIALOGUE_SWITCH = "key.super.dialogue.switch";
    public static final String KEY_TTS_ENGINE = "SpeechConfig.tts_engine";
    public static final String KEY_UPGRADE_IMMEDIATELY = "key_upgrade_immediately";
    public static final String KEY_USER_EXPRESSION_ENABLE = "key.user.expression.enable";
    public static final String KEY_VISIBLE_ENABLE = "key.visible.enable";
    public static final String KEY_WAKEUP_WORD_RECOMMEND = "key.wakeup.word.recommend";
    public static final String SPEECH_SERVER_ACTION = "carspeechservice.SpeechServer";
    public static final String SPEECH_SERVER_ACTION_DATA_AUTHOR = "carspeechservice.action.data.author";
    public static final String SPEECH_SERVER_ACTION_DEBUG = "carspeechservice.action.debug";
    public static final String SPEECH_SERVER_ACTION_START = "carspeechservice.SpeechServer.Start";
    public static final String SPEECH_SERVICE_PACKAGE_NAME = "com.xiaopeng.carspeechservice";
    public static final int TTS_CHAR_TIME = 200;
    public static int TTS_DEFAULT_MODEL = 0;
    public static String TTS_DEFAULT_SPEAKER = "zhilingfa";
    public static final String TTS_IFLYTEK = "iflytek";
    public static final int TTS_MODE_CLOUD = 1;
    public static final int TTS_MODE_LOCAL = 0;
    public static final String TTS_SPEEX = "speex";
    public static final int TTS_STREAM_NAVI = 9;
    public static final int TTS_STREAM_SPEECH = -1;
    public static int TTS_TIMEOUT = 1000;
    public static int TTS_TIMEOUT_TEST = 3000;
    public static final int VAD_PAUSE_TIME = 500;
    public static final int VAD_TIMEOUT = 7000;

    /* loaded from: classes2.dex */
    public static class AirWindDirection {
        public static final int AIR_WIND_DIRECTION_BACK = 2;
        public static final int AIR_WIND_DIRECTION_BUTTOM = 6;
        public static final int AIR_WIND_DIRECTION_CENTER = 7;
        public static final int AIR_WIND_DIRECTION_FRONT = 1;
        public static final int AIR_WIND_DIRECTION_LEFT = 3;
        public static final int AIR_WIND_DIRECTION_RIGHT = 4;
        public static final int AIR_WIND_DIRECTION_TOP = 5;
    }

    /* loaded from: classes2.dex */
    public static class AppOpen {
        public static final String KEY_OPEN_APP = "key_open_app";
        public static final int SPEECH_OPEN_APP = 1001;
    }

    /* loaded from: classes2.dex */
    public static class ChargePort {
        public static final int CLOSE = 1;
        public static final int LEFT = 0;
        public static final int OPEN = 0;
        public static final int RIGHT = 1;
    }

    /* loaded from: classes2.dex */
    public static class ELECTRIC_CURTAIN {
        public static final int CLOSE = 1;
        public static final int OPEN = 0;
    }

    /* loaded from: classes2.dex */
    public static class FuncState {
        public static final int CUSTOM_WAKEUP_WORD_STATE = 2;
        public static final int IS_FIRST_QUERY = 3;
        public static final int ONLY_SUPPRORT_SHORTCUT = 0;
        public static final int RESET_AVATAR_ENABLE = 1;
    }

    /* loaded from: classes2.dex */
    public static class NAVI {
        public static final int MODE_COMBO_INVISIBLE = 1;
        public static final int MODE_DEFAULT = 0;
    }

    /* loaded from: classes2.dex */
    public static class OOBE {
        public static final int OOBE_ADDRESS_COMPANY = 3;
        public static final int OOBE_ADDRES_HOME = 2;
        public static final int OOBE_EXIT = -1;
        public static final int OOBE_NORMAL = 0;
        public static final int OOBE_OTHER = 4;
        public static final int OOBE_SPEECH = 1;
    }

    /* loaded from: classes2.dex */
    public static class PhoneStatus {
        public static final int CALL_AUDIO_IN = 4;
        public static final int CALL_AUDIO_OUT = 5;
        public static final int CALL_END = 0;
        public static final int CALL_INCOMING = 1;
        public static final int CALL_IN_CALL = 3;
        public static final int CALL_OUTGOING = 2;
    }

    /* loaded from: classes2.dex */
    public static class REAR_SEAT_HEAT_MODE {
        public static final int CLOSE = 1;
        public static final int OPEN = 0;
    }

    /* loaded from: classes2.dex */
    public static class REAR_SEAT_HEAT_POSITION {
        public static final int LEFT_REAR = 0;
        public static final int RIGHT_REAR = 1;
    }

    /* loaded from: classes2.dex */
    public static class SoundLocation {
        public static final int ALL_LOCATION = 0;
        public static final String DEF_DOA_DB_LOCK = "20";
        public static final String DEF_DOA_DB_UNLOCK = "0";
        public static final int DEF_DRIVER_ANGLE = 55;
        public static final int DEF_PASSENGER_ANGLE = 125;
        public static final int DRIVE_END_ANGLE = 75;
        public static final int DRIVE_LOCATION = 1;
        public static final int DRIVE_START_ANGLE = 35;
        public static final int MAX_ANGLE = 160;
        public static final int MAX_ANGLE_OFFSET = 15;
        public static final int MIN_ANGLE = 20;
        public static final int PASSENGER_END_ANGLE = 145;
        public static final int PASSENGER_LOCATION = 2;
        public static final int PASSENGER_START_ANGLE = 105;
        public static final int REAR_LEFT = 3;
        public static final int REAR_MIDDLE = 5;
        public static final int REAR_RIGHT = 4;
        public static final int SOUND_MODE_INCALL = 1;
        public static final int SOUND_MODE_OTHER = 0;
        public static final int VAD_LOCK_MAX = 20;
        public static final int VAD_LOCK_MIN = 15;
        public static final int VAD_UNLOCK_MAX = 40;
        public static final int VAD_UNLOCK_MIN = 30;
    }

    /* loaded from: classes2.dex */
    public static class Speaker {
        public static final String GQLANF = "gqlanf";
        public static final String ZHILINGF = "zhilingf";
        public static final String ZHILINGFA = "zhilingfa";
    }

    /* loaded from: classes2.dex */
    public static class TTS {
        public static final int END_STATE_ERROR = 1;
        public static final int END_STATE_INTERRUPTED = 2;
        public static final int END_STATE_SUCCESS = 0;
        public static final int PRIORITY_HIGH = 2;
        public static final int PRIORITY_INTERNAL = 0;
        public static final int PRIORITY_NORMAL = 1;
        public static final int PRIORITY_SUPUER = 3;
        public static final int PRIORITY_URGENT = 4;
    }

    /* loaded from: classes2.dex */
    public static class ThirdCMD {
        public static final String CMD_FINISH_BUG_REPORT = "cmd_finish_bug_report";
    }

    /* loaded from: classes2.dex */
    public static class Vocab {
        public static final String APP = "sys.应用名称";
    }

    /* loaded from: classes2.dex */
    public static class Windows {
        public static final int ALL = 6;
        public static final int CLOSE = 1;
        public static final int DRIVER = 0;
        public static final int FRONT = 7;
        public static final int LEFT = 4;
        public static final int LEFT_REAR = 2;
        public static final int OPEN = 0;
        public static final int PASSENGER = 1;
        public static final int REAR = 8;
        public static final int RIGHT = 5;
        public static final int RIGHT_REAR = 3;
    }
}
