package com.xiaopeng.libconfig.ipc;

/* loaded from: classes2.dex */
public class IpcConfig {

    /* loaded from: classes2.dex */
    public static class AIAssistantConfig {
        public static final int IPC_ACCOUNT_GET_QR_CODE = 11001;
        public static final int IPC_ACCOUNT_LOGIN_RESULT = 11004;
        public static final int IPC_ACCOUNT_QR_CODE_OVERDUE = 11003;
        public static final int IPC_ACCOUNT_RETURN_QR_CODE = 11002;
        public static final int IPC_APP_CLOSE_AI_HOME = 11015;
        public static final int IPC_APP_INTERACTIVE_PROTO = 11012;
        public static final int IPC_APP_PLAY_AI_WEBP = 11013;
        public static final int IPC_CONTEXT_SENSOR = 11020;
        public static final int IPC_HOME_OPEN_STATE = 11018;
        public static final int IPC_IDENTIFIED_DISTRACTION_ = 11023;
        public static final int IPC_IDENTIFIED_FACE = 11022;
        public static final int IPC_IDENTIFIED_FATIGUE_ = 11024;
        public static final int IPC_MESSAGE_CAN_NOT_SHOW_NOTIFY = 11021;
        public static final int IPC_MESSAGE_CLOSE = 11014;
        public static final int IPC_MESSAGE_SPEECH_CLOSE = 11019;
        public static final int IPC_MUSIC_GET_TAG = 11005;
        public static final int IPC_MUSIC_GET_TAG_RETURN = 11006;
        public static final int IPC_MUSIC_SET_TAG = 11007;
        public static final int IPC_NOTICE_OTA_SUCCESS_MESSAGE_SHOWED = 11025;
        public static final int IPC_OOBE_FACE_ACCOUNT = 11016;
        public static final int IPC_OOBE_FACE_AI = 11017;
        public static final int IPC_OOBE_FINISH = 11009;
        public static final int IPC_OOBE_OPEN_AI_HOME = 11010;
        public static final int IPC_OOBE_START = 11008;
        public static final int IPC_OOBE_START_FINISH = 11011;
    }

    /* loaded from: classes2.dex */
    public static class AccountConfig {
        public static final int IPC_MSG_ID_ACCOUNT_CHANGE_ACCOUNT = 130006;
        public static final int IPC_MSG_ID_ACCOUNT_COLLECT_CODE = 130008;
        public static final int IPC_MSG_ID_ACCOUNT_FACE_BIND = 130005;
        public static final int IPC_MSG_ID_ACCOUNT_ONLINE_ACCOUNT = 130007;
        public static final int IPC_MSG_ID_ACCOUNT_QR_LOGIN = 130001;
        public static final int IPC_MSG_ID_ACCOUNT_UPDATE_INFO = 130003;
        public static final int IPC_MSG_ID_ACCOUNT_UPDATE_PERMISSION = 130004;
        public static final int IPC_MSG_ID_ACCOUNT_USER_SWITCH = 130002;
        public static final int IPC_MSG_ID_CONTROL_CANCEL_MOVE_SEAT = 140001;
        public static final int IPC_MSG_ID_SYNC_ANIM_DONE = 130009;
    }

    /* loaded from: classes2.dex */
    public static class AfterSalesConfig {
        public static final int IPC_DIAG_MAP_GAODE_LOG = 6006;
        public static final int IPC_DIAG_REQUEST = 6004;
        public static final int IPC_DIAG_RESPONSE = 6005;
        public static final int IPC_EVENT_REPAIRMODE_STATUS_CHANGE = 6001;
        public static final int IPC_REQUEST_CHANGE_REPAIRMODE = 6002;
        public static final int IPC_REQUEST_REPAIRMODE_STATUS = 6003;
        public static final String MSGKEY_REPAIRMODE_STATUS_CHANGE = "repairmode";
        public static final String MSGKEY_REQUEST_CHANGE_REPAIRMODE = "change_repairmode";
        public static final String MSGKEY_REQUEST_REPAIRMODE_STATUS = "read_repairmode";
    }

    /* loaded from: classes2.dex */
    public static class App {
        public static final String AI = "com.xiaopeng.aiassistant";
        public static final String APP_AFTER_SALES = "com.xiaopeng.aftersales";
        public static final String APP_AIOT_SERVICE = "com.xiaopeng.aiot.coreservice";
        public static final String APP_APPSTORE = "com.xiaopeng.appstore";
        public static final String APP_BUGHUNTER = "com.xiaopeng.bughunter";
        public static final String APP_CAR_DIAGNOSIS = "com.xiaopeng.cardiagnosis";
        public static final String APP_DIAGNOSTIC = "com.xiaopeng.diagnostic";
        public static final String APP_ENGINE = "com.xiaopeng.appengine";
        public static final String APP_PRIVACY_SERVICE = "com.xiaopeng.privacyservice";
        public static final String APP_ROUTE_SRHDPKMAP = "com.xiaopeng.hiddenCam";
        public static final String APP_XUI_SERVICE = "com.xiaopeng.xuiservice";
        public static final String AUTO_CAR_WASH = "com.xiaopeng.autocarwash";
        public static final String AUTO_SHOW = "com.xiaopeng.autoshow";
        public static final String CAR_ACCOUNT = "com.xiaopeng.caraccount";
        public static final String CAR_AIOS_ADAPTER = "com.aispeech.aios.adapter";
        public static final String CAR_AIOS_BRIDGE = "com.aispeech.aios.bridge";
        public static final String CAR_AUTOPILOT = "com.xiaopeng.autopilot";
        public static final String CAR_BT_PHONE = "com.xiaopeng.btphone";
        public static final String CAR_CAMERA = "com.xiaopeng.xmart.camera";
        public static final String CAR_CHARGE_CONTROL = "com.xiaopeng.chargecontrol";
        public static final String CAR_CONTROL = "com.xiaopeng.carcontrol";
        public static final String CAR_DEVTOOLS = "com.xiaopeng.devtools";
        public static final String CAR_FACTORY_TEST = "com.xiaopeng.factorytest";
        public static final String CAR_GALLERY = "com.xiaopeng.xmart.cargallery";
        public static final String CAR_MUSIC = "com.xiaopeng.musicradio";
        public static final String CAR_NAVIGATION = "com.xiaopeng.xmapnavi";
        public static final String CAR_OTA = "com.xiaopeng.ota";
        public static final String CAR_REMOTE_CONTROL = "com.xiaopeng.remote.control";
        public static final String CAR_SETTINGS = "com.xiaopeng.car.settings";
        public static final String CONFIGURATION_CENTER = "com.xiaopeng.configurationcenter";
        public static final String DATA_COLLECTOR = "com.xiaopeng.data.collector";
        public static final String DEVICE_COMMUNICATION = "com.xiaopeng.device.communication";
        public static final String MAP_NAVI = "com.xiaopeng.montecarlo";
        public static final String MESSAGE_CENTER = "com.xiaopeng.message.center";
        public static final String PILOT_PARK = "com.xiaopeng.pilotpark";
        public static final String SPEECH_PACKAGE = "com.xiaopeng.carspeechservice";
        public static final String SYSTEM_SETTINGS = "com.android.settings";
        public static final String SYSTEM_SYSTEMUI = "com.android.systemui";
        public static final String UPLOAD_CAR_INFO = "com.xiaopeng.uploadcarinfo";
    }

    /* loaded from: classes2.dex */
    public static class AutoShowConfig {
        public static final int ALLOW_FACE_ID_LOGIN = 1001;
        public static final int FORBID_EXPERIENCE_MODE = 1002;
        public static final int FORBID_HIGH_VOLT = 1000;
        public static final int P_TALK_SWITCH = 1003;
        public static final int TRANSPORT_MODE = 1004;
    }

    /* loaded from: classes2.dex */
    public static class AutopilotConfig {
        public static final int REMOTE_COMMAND_AUTOPILOT_ENTER_AUTO_PARK = 90006;
        public static final int REMOTE_COMMAND_AUTOPILOT_EXIT_AUTO_PARK = 90007;
        public static final int REMOTE_COMMAND_AUTOPILOT_SYSTEMUI_ACTIVE_SUPER_PARK = 90011;
        public static final int REMOTE_COMMAND_AUTOPILOT_TO_EXIT_BEFORE_WINDOW_CLOSE = 90010;
        public static final int REMOTE_COMMAND_AUTOPILOT_WINDOW_CLOSED = 90012;
        public static final int REMOTE_COMMAND_AUTOPILOT_WINDOW_OPENED = 90013;
    }

    /* loaded from: classes2.dex */
    public static class BTPhoneConfig {
        public static final int IPC_MSG_AI_CMD_ANSWER_CALL = 1002;
        public static final int IPC_MSG_AI_CMD_HAND_UP = 1003;
        public static final int IPC_MSG_ANWSER_CALL = 1006;
        public static final int IPC_MSG_CALL_OVER = 1005;
        public static final int IPC_MSG_DIAL_NUMBER = 1007;
        public static final int IPC_MSG_ON_CALL_IN = 1004;
        public static final int PHONE_CODE = 1001;
    }

    /* loaded from: classes2.dex */
    public static class BughunterConfig {
        public static final int IPC_COMMAND_ID_ICM_CRASH = 4000;
    }

    /* loaded from: classes2.dex */
    public static class CameraConfig {
        public static final int REMOTE_COMMAND_CAMERA_RELEASED_EVENT = 110006;
        public static final int REMOTE_COMMAND_LIVE_CLIENT_IS_LIVING = 110005;
    }

    /* loaded from: classes2.dex */
    public static class CarControlConfig {
        public static final int IPC_FACE_RECOGNITION = 16005;
        public static final int IPC_HEAD_LAMP_STATE = 16006;
        public static final int IPC_LEARNING_MORE = 16004;
        public static final int REMOTE_COMMAND_AUTOPILOT = 16002;
        public static final int REMOTE_COMMAND_DVR = 16000;
        public static final int REMOTE_COMMAND_LAUNCH = 16003;
        public static final int REMOTE_COMMAND_MEDIA_SOURCE = 16001;
    }

    /* loaded from: classes2.dex */
    public static class CarInfoUploadConfig {
        public static final int TYPE_CANCEL_GET_CARINFO_DATA_REQUEST = 100002;
        public static final int TYPE_GET_CARINFO_DATA_REQUEST = 100001;
        public static final int TYPE_HEAR_BEAT_GET_CARINFO_DATA = 100003;
        public static final int TYPE_IMMEDIATELY_GET_CARINFO_DATA_REQUEST = 100004;
    }

    /* loaded from: classes2.dex */
    public static class CarRemoteControlConfig {
        public static final int TYPE_CONTROL_COMMAND = 150002;
        public static final int TYPE_CONTROL_COMMAND_IPC = 150003;
        public static final int TYPE_KEEP_IG_ON_WHEN_LIVE = 150001;
    }

    /* loaded from: classes2.dex */
    public static class ChargeConfig {
        public static final int CHARGE_CONNECTOR_LOCK_STATUS = 30001;
        public static final int IPC_ID_HIDE_CHARGE_UI = 30003;
        public static final int IPC_ID_HIGH_SPEED_CHARGE = 30005;
        public static final int IPC_ID_SHOW_CHARGE_SPOTS_LIST = 30002;
        public static final int IPC_ID_SWITCH_DRIVE_MODE = 30004;
    }

    /* loaded from: classes2.dex */
    public static class DeviceCommunicationConfig {
        public static final String KEY_APP_MESSAGE_EVENT_NAME = "eventName";
        public static final String KEY_APP_MESSAGE_PARAMS = "params";
        public static final int SEND_APP_MESSAGE = 1403;
        public static final int SEND_CAR_CONTROL_TOPIC = 1400;
        public static final int SEND_ICM_CRASH_MESSAGE = 1404;
        public static final int TRAFFIC_STATUS_REQUEST = 1401;
        public static final int TRAFFIC_STATUS_RESPONSE = 1402;
    }

    /* loaded from: classes2.dex */
    public static class DevtoolsConfig {
        public static final int IPC_ID_MSG_NOTIFY = 60001;
    }

    /* loaded from: classes2.dex */
    public static class GalleryConfig {
        public static final int REMOTE_COMMAND_ADD_FILE = 110008;
        public static final int REMOTE_COMMAND_DELETE_FILE = 110007;
    }

    /* loaded from: classes2.dex */
    public static class HVACControlConfig {
        public static final int IPC_COMMAND_ID_HVAC_CONTROL = 190000;
        public static final int IPC_COMMAND_ID_HVAC_ESRSYSTEM_ENTER_REQUEST = 190004;
        public static final int IPC_COMMAND_ID_HVAC_ESRSYSTEM_ENTER_RESPONSE = 190006;
        public static final int IPC_COMMAND_ID_HVAC_ESRSYSTEM_EXIT_REQUEST = 190005;
        public static final int IPC_COMMAND_ID_HVAC_ESRSYSTEM_EXIT_RESPONSE = 190007;
        public static final int IPC_COMMAND_ID_HVAC_ESRSYSTEM_NEED_REQUEST = 190001;
        public static final int IPC_COMMAND_ID_HVAC_ESRSYSTEM_NEED_RESPONSE = 190002;
        public static final int IPC_COMMAND_ID_HVAC_ESRSYSTEM_NOT_NEED_RESPONSE = 190003;
        public static final int IPC_COMMAND_ID_HVAC_VENTILATION_ENTER_RESPONSE = 190008;
        public static final int IPC_COMMAND_ID_HVAC_VENTILATION_EXIT_RESPONSE = 190009;
    }

    /* loaded from: classes2.dex */
    public static class IPCKey {
        public static final String BOOLEAN_VALUE = "boolean_value";
        public static final String INT_VALUE = "int_value";
        public static final String SOUND_MODE = "sound_mode";
        public static final String STRING_MSG = "string_msg";
    }

    /* loaded from: classes2.dex */
    public static class ManyToMqttIdConfig {
        public static final int IPC_MSG_AUTO_DRIVING_CONFIG_RES = 2001;
        public static final int IPC_MSG_CAR_LOC_PIC_FEEDBACK = 20003;
        public static final int IPC_MSG_V2X_CAR_STATE = 1002;
        public static final int IPC_MSG_V2X_CAR_STATE_FEEDBACK = 20002;
        public static final int IPC_MSG_V2X_COMMAND_CMD = 20001;
        public static final int IPC_MSG_V2X_GROUP_TOPIC = 1001;
    }

    /* loaded from: classes2.dex */
    public static class MapContainerConfig {
        public static final int IPC_MAP_CONTAINER = 70001;
    }

    /* loaded from: classes2.dex */
    public static class MessageCenterConfig {
        public static final int IPC_ID_APP_PUSH_MESSAGE = 10009;
        public static final int IPC_ID_GET_MESSAGE_BOX_HISTORY_LIST = 10002;
        public static final int IPC_ID_HAS_UNREAD_MESSAGE = 10005;
        public static final int IPC_ID_MESSAGE_CHANNEL_PUSH_MESSAGE = 10010;
        public static final int IPC_ID_MESSAGE_CLOSED = 10016;
        public static final int IPC_ID_MESSAGE_DELETE = 10013;
        public static final int IPC_ID_MESSAGE_GET_CAR_STATE = 10012;
        public static final int IPC_ID_MESSAGE_PUSH_TO_APP = 10011;
        public static final int IPC_ID_MESSAGE_SHOW = 10014;
        public static final int IPC_ID_MESSAGE_SHOW_CHECKED_OK = 10015;
        public static final int IPC_ID_PUSH_MESSAGE = 10006;
        public static final int IPC_ID_READ_ALL_MESSAGE = 10004;
        public static final int IPC_ID_READ_MESSAGE = 10003;
        public static final int IPC_ID_RETURN_HAS_UNREAD_MESSAGE = 10008;
        public static final int IPC_ID_RETURN_MESSAGE_BOX_HISTORY_LIST = 10007;
        public static final int IPC_ID_SUBSCRIBE = 10000;
        public static final int IPC_ID_UNSUBSCRIBE = 10001;
    }

    /* loaded from: classes2.dex */
    public static class MqttToManyIdConfig {
        public static final int IPC_MSG_AUTO_DRIVING_CONFIG_RES = 2001;
        public static final int IPC_MSG_CAR_LOC_PIC_FEEDBACK = 20003;
        public static final int IPC_MSG_V2X_CAR_STATE = 1002;
        public static final int IPC_MSG_V2X_CAR_STATE_FEEDBACK = 20002;
        public static final int IPC_MSG_V2X_COMMAND_CMD = 20001;
        public static final int IPC_MSG_V2X_GROUP_TOPIC = 1001;
    }

    /* loaded from: classes2.dex */
    public static class MusicConfig {
        public static final int MSG_ID_OPEN_BT_PLAYER = 800001;
    }

    /* loaded from: classes2.dex */
    public static class MusicServiceConfig {
        public static final int IPC_COMMAND_ID_MUSIC_CONTROL = 70000;
        public static final int IPC_OPEN_MANUAL_SEARCH = 70001;
    }

    /* loaded from: classes2.dex */
    public static class OTAConfig {
        public static final int NOTIFY_BROADCAS = 1302;
        public static final int OTA_STATUS_REQUEST = 1305;
        public static final int OTA_STATUS_RESPONSE = 1306;
        public static final int SHOW_OTA_DETAIL = 1304;
        public static final int SHOW_OTA_MAIN = 1303;
        public static final int STATE_CHANGE = 1301;
    }

    /* loaded from: classes2.dex */
    public static class SettingsConfig {
        public static final int DIALOG_DISMISS = 1103;
        public static final int IPC_LOW_SPEED_NOISE = 1105;
        public static final int IPC_PHONE_COMING = 1104;
        public static final int IPC_SMART_RAIN_WIPER = 1107;
        public static final int IPC_WAITING_MODE = 1106;
        public static final int SHOW_BT_DIALOG = 1102;
        public static final int SOUND_MODE = 1101;
    }

    /* loaded from: classes2.dex */
    public static class SpeechConfig {
        public static final int GPS_INFORMATION = 1000;
    }

    /* loaded from: classes2.dex */
    public static class SpeechServiceConfig {
        public static final int IPC_CMD_SDK_INIT = 1001;
    }

    /* loaded from: classes2.dex */
    public static class XUIConfig {
        public static final int MSG_ID = 100011;
        public static final String MSG_SHOW_FLAG = "MSG_SHOW_FLAG";
    }
}
