package com.xiaopeng.libconfig.ipc;

/* loaded from: classes2.dex */
public class AccountConfig {
    public static final int ACCOUNT_GRANT_LOGIN = 1;
    public static final int ACCOUNT_GRANT_UNLOGIN = 2;
    public static final String ACTION_ACCOUNT_OVER_TIME = "com.xiaopeng.action.OVER_TIME";
    public static final String ACTION_OPEN_PAGE = "com.xiaopeng.action.OPEN_ACCOUNT_RN_PAGE";
    public static final String ACTION_QR_LOGIN = "com.xiaopeng.action.QR_LOGIN";
    public static final String CMD_FACEID_CLICK_RETRY = "cmd_faceid_click_retry";
    public static final String CMD_FACEID_REGISTER_ON_ACTION = "cmd_faceid_register_on_action";
    public static final String CMD_FACEID_REGISTER_ON_FAULT = "cmd_faceid_register_on_fault";
    public static final String CMD_FACEID_REGISTER_ON_SHIELD = "cmd_faceid_register_on_shield";
    public static final String CMD_FACEID_REGISTER_ON_SUCCESS = "cmd_faceid_register_on_success";
    public static final String CMD_FACEID_REGISTER_ON_TERMINATED = "cmd_faceid_register_on_terminated";
    public static final String CMD_FACEID_SKIP_REGISTER = "cmd_faceid_skip_register";
    public static final String CMD_FACEID_START_REGISTER = "cmd_faceid_start_register";
    public static final String FACEID_STARTUP_FAULT = "faceid_startup_fault";
    public static final String FACEID_STARTUP_TIMED_OUT = "faceid_startup_timed_out";
    public static final int GRADE_DRIVER = 4;
    public static final int GRADE_OWNER = 1;
    public static final int GRADE_TEMP = -1;
    public static final int GRADE_TENANT = 3;
    public static final int GRADE_USER = 2;
    public static final String INVALID_TOKEN = "invalid_token";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_APP_ID = "app_id";
    public static final String KEY_AUTHORIZATION = "Authorization";
    public static final String KEY_CLIENT = "Client";
    public static final String KEY_CONTENT_TYPE = "Content-Type";
    public static final String KEY_REFRESH_TOKEN = "refresh_token";
    public static final String KEY_SIGN = "sign";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String OVER_TIME_KEY = "OVER_TIME_KEY";
    public static final String TEMP_NAME = "未登录";
    public static final String TEMP_PHONE = "无";
    public static final long TEMP_UID = -1;
    public static final String VALUE_CAR_CLIENT_DEFAULT = "car.xmart.com/v1.0.0";
    public static final String VALUE_DEFAULT_TYPE_JSON = "application/json";

    /* loaded from: classes2.dex */
    public static final class AccountCommandBean {
        public String cmd;
        public String param;

        public String toString() {
            return "AccountCommandBean { cmd:" + this.cmd + "; param:" + this.param + " };";
        }
    }

    /* loaded from: classes2.dex */
    public static final class FaceIDRegisterAction {
        public static final String ORIENTATION_DOWN = "down";
        public static final String ORIENTATION_FRONT = "front";
        public static final String ORIENTATION_LEFT = "left";
        public static final String ORIENTATION_NONE = "none";
        public static final String ORIENTATION_RIGHT = "right";
        public static final String ORIENTATION_UP = "up";
        public static final String STATUS_PROCESSING = "processing";
        public static final String STATUS_START = "start";
        public static final String STATUS_SUCCEED = "succeed";
        public static final String STATUS_TIMEDOUT = "timedOut";
        public String orientation;
        public String status;
        public int timedOutCounter;

        public String toString() {
            return "FaceIDRegisterAction { orientation:" + this.orientation + "; status:" + this.status + " };";
        }
    }
}
