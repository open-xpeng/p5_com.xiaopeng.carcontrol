package com.xiaopeng.lib.utils.config;

import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.lib.utils.info.DeviceInfoUtils;

/* loaded from: classes2.dex */
public class CommonConfig {
    public static final String ACTION_BROADCAST_ADB_OTA = "com.xiaopeng.broadcast.ACTION_ADB_OTA";
    public static final String ACTION_BROADCAST_CAMERA_CLOSE = "com.xiaopeng.action.CAMERA_CLOSE";
    public static final String ACTION_BROADCAST_CAMERA_OPEN = "com.xiaopeng.action.CAMERA_OPEN";
    public static final String ACTION_BROADCAST_CLIENT_SSL_UPDATE = "com.xiaopeng.action.CLIENT_SSL_UPDATE";
    public static final String ACTION_BROADCAST_GET_TOKEN = "com.xiaopeng.broadcast.ACTION_GET_TOKEN";
    public static final String ACTION_BROADCAST_HIGH_LAMP_CLOSE = "com.xiaopeng.action.HIGH_LAMP_CLOSE";
    public static final String ACTION_BROADCAST_HIGH_LAMP_OPEN = "com.xiaopeng.action.HIGH_LAMP_OPEN";
    public static final String ACTION_BROADCAST_NEAR_LAMP_CLOSE = "com.xiaopeng.action.NEAR_LAMP_CLOSE";
    public static final String ACTION_BROADCAST_NEAR_LAMP_OPEN = "com.xiaopeng.action.NEAR_LAMP_OPEN";
    public static final String ACTION_BROADCAST_PM_STATUS_CHANGE = "com.xiaopeng.broadcast.ACTION_PM_STATUS_CHANGE";
    public static final String ACTION_BROADCAST_SCREEN_THEMES_CHANGE = "com.xiaopeng.broadcast.ACTION_SCREEN_THEMES_CHANGE";
    public static final String ACTION_BROADCAST_TOKEN_GOTTEN = "com.xiaopeng.broadcast.ACTION_TOKEN_GOTTEN";
    public static final String ACTION_BROADCAST_TO_SPEAK = "aios.intent.action.TO_SPEAK";
    public static final String ACTION_CONBIND_KEY_UPLOAD_LOG = "com.xiaopeng.scu.ACTION_UP_LOAD_CAN_LOG";
    public static final String ACTION_CONBIND_KEY_UPLOAD_LOG_CS = "com.xiaopeng.scu.ACTION_UP_LOAD_CAN_LOG_CS";
    public static final String AIOS_PRIORITY = "aios.intent.extra.PRIORITY";
    public static final int AIOS_PRIORITY_DEFAULT = 3;
    public static final String AIOS_TEXT = "aios.intent.extra.TEXT";
    public static final String BUCKET_AND_ENDPOINT = "http://xmart-cdu-service-log.oss-cn-hangzhou.aliyuncs.com/";
    public static final String BUCKET_NAME = "xmart-cdu-service-log";
    public static final String CAR_APP_ID = "xmart:appid:002";
    public static final String END_POINT = "http://oss-cn-hangzhou.aliyuncs.com/";
    public static final String EXTRA_THEME_TYPE = "extra_theme_type";
    public static final String HTTP_BUSINESS_HOST;
    public static final String KEY_AUTHORIZATION = "Authorization";
    public static final String KEY_CLIENT = "Client";
    public static final String KEY_VALUE_STATUS = "status";
    public static final long MAX_LOG_LENGTH = 10485760;
    public static final int PM_FAKE_OFF = 1;
    public static final int PM_NORMAL = 0;
    public static final int PM_SLEEP = 2;
    public static final int RESPONSE_OK = 200;
    public static final int THEME_AUTO = 3;
    public static final int THEME_DAY = 1;
    public static final int THEME_NIGHT = 2;
    public static final String CAR_CLIENT_DEFAULT = "car.xmart.com/" + BuildInfoUtils.getSystemVersion();
    public static String HTTP_HOST = EnvConfig.getHostInString("https://10.0.13.28:8553", "https://xmart.xiaopeng.com");
    public static String HTTP_HOST_EU = EnvConfig.getHostInString("https://10.0.13.28:8556", "https://xmart-eu.xiaopeng.com");

    static {
        if (DeviceInfoUtils.isInternationalVer()) {
            HTTP_BUSINESS_HOST = HTTP_HOST_EU + "/biz";
        } else {
            HTTP_BUSINESS_HOST = HTTP_HOST + "/biz";
        }
    }
}
