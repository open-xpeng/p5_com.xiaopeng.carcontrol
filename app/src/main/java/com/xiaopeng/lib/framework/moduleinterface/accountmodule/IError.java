package com.xiaopeng.lib.framework.moduleinterface.accountmodule;

/* loaded from: classes2.dex */
public interface IError {
    public static final int ERROR_CODE_SERVER_UNKNOWN = 0;
    public static final int ERR_REQUEST_FAILED = 1002;
    public static final int ERR_SERVICE_DISCONNECTED = 1000;
    public static final int ERR_USER_LOGOUT = 1001;
    public static final String STR_REQUEST_FAILED = "网络请求失败";
    public static final String STR_SERVICE_DISCONNECTED = "账号服务已断开连接";
    public static final String STR_UNKNOWN_ERR = "账号服务未知错误";
    public static final String STR_USER_LOGOUT = "用户未登录";

    int getCode();

    String getMessage();
}
