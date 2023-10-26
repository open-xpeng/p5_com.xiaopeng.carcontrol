package com.xiaopeng.lib.framework.moduleinterface.appresourcemodule;

/* loaded from: classes2.dex */
public abstract class IAppResourceException extends Exception {
    public static final int REASON_BINDER_FAILED = 4001;
    public static final int REASON_BINDER_TIMEOUT = 4002;
    public static final int REASON_FILE_NOT_FOUND = 2001;
    public static final int REASON_HTTP_ERROR = 4007;
    public static final int REASON_HTTP_FILE_DOWNLOADING = 4005;
    public static final int REASON_HTTP_NOT_FOUND = 4006;
    public static final int REASON_HTTP_NO_ETAG = 4003;
    public static final int REASON_HTTP_NO_UPDATE = 4004;
    public static final int REASON_INVALID_URI = 1002;
    public static final int REASON_MGR_DB_ERROR = 2002;
    public static final int REASON_NOT_AVAILABLE_NOW = 3002;
    public static final int REASON_NOT_INITIALIZED = 1001;
    public static final int REASON_NOT_SUPPORT = 5001;
    public static final int REASON_NO_PERMISSION = 3001;
    public static final int REASON_PARAM_ERROR = 1003;

    public abstract long getReason();

    public IAppResourceException(String str) {
        super(str);
    }
}
