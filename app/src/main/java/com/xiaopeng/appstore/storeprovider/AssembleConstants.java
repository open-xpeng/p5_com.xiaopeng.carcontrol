package com.xiaopeng.appstore.storeprovider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class AssembleConstants {
    public static final String ACTION_CANCEL = "com.xiaopeng.appstore.resource.action.cancel";
    public static final String ACTION_CANCEL_ALL = "com.xiaopeng.appstore.resource.action_cancelAll";
    public static final String ACTION_PAUSE = "com.xiaopeng.appstore.resource.action.pause";
    public static final String ACTION_RESUME = "com.xiaopeng.appstore.resource.action.resume";
    public static final String ACTION_RETRY = "com.xiaopeng.appstore.resource.action.retry";
    public static final String ACTION_SUCCESS = "com.xiaopeng.appstore.resource.action.success";
    public static final int EVENT_TYPE_PROGRESS_CHANGED = 1001;
    public static final int EVENT_TYPE_STATE_CHANGED = 1000;
    public static final String EXTRA_KEY_PARAMS_1 = "extra_key_params_1";
    public static final String EXTRA_KEY_PARAMS_2 = "extra_key_params_2";
    public static final String EXTRA_KEY_PARAMS_3 = "extra_key_params_3";
    public static final String EXTRA_KEY_PARAMS_4 = "extra_key_params_4";
    public static final String EXTRA_KEY_PARAMS_FILE_PATH = "extra_key_params_file_path";
    public static final String EXTRA_KEY_PARAMS_MD5_1 = "extra_key_params_md5_1";
    public static final String EXTRA_KEY_PARAMS_MD5_2 = "extra_key_params_md5_2";
    public static final String EXTRA_KEY_PARAMS_REMAIN_TIME = "extra_key_params_remain_time";
    public static final String EXTRA_KEY_PARAMS_SHOW_DIALOG = "extra_key_show_dialog";
    public static final String EXTRA_KEY_PARAMS_SPEED = "extra_key_params_speed";
    public static final String EXTRA_KEY_PARAMS_START_DOWNLOAD_SHOW_TOAST = "extra_key_params_start_download_show_toast";
    public static final String EXTRA_KEY_PARAMS_URL = "extra_key_params_url";
    public static final String INTENT_KEY_ASSEMBLE_KEY = "intent_key_assemble_key";
    public static final String INTENT_KEY_CALLING_PACKAGE = "intent_key_calling_package";
    public static final String INTENT_KEY_RES_TYPE = "intent_key_res_type";
    public static final int NOTIFICATION_VISIBILITY_HIDE = 1;
    public static final int NOTIFICATION_VISIBILITY_VISIBLE = 100;
    public static final int RES_TYPE_APP = 1000;
    public static final int RES_TYPE_DOWNLOAD = 1;
    public static final int RES_TYPE_LLU = 2000;
    public static final int RES_TYPE_UNKNOWN = 0;
    public static final int RES_TYPE_USER_MANUAL = 3000;
    public static final int RES_TYPE_XUI_COMMON = 4000;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface ResourceType {
    }

    private AssembleConstants() {
    }
}
