package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage;

/* loaded from: classes2.dex */
public abstract class StorageException extends Exception {
    public static final int REASON_ALREADY_INITIALIZED = 2;
    public static final int REASON_CLOSE_STEAM_FAILED = 514;
    public static final int REASON_DOWNLOAD_ERROR = 1281;
    public static final int REASON_DOWNLOAD_INCOMPLETE = 1282;
    public static final int REASON_EMPTY_CONTENT = 4;
    public static final int REASON_EXCEED_TASK_LIMITATION = 1026;
    public static final int REASON_EXCEED_TRAFFIC_QUOTA = 1537;
    public static final int REASON_FAIL_TO_CREATE_FILE = 515;
    public static final int REASON_FILE_NOT_EXISTS = 513;
    public static final int REASON_GET_TOKEN_ERROR = 769;
    public static final int REASON_ILLEGAL_PATH_ARGUMENT = 3;
    public static final int REASON_NOT_INITIALIZED = 1;
    public static final int REASON_UPLOAD_ERROR = 1025;
    private static final long serialVersionUID = 100;

    public abstract int getReasonCode();

    public StorageException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public String toString() {
        if (getMessage() != null) {
            return getMessage() + " (" + getReasonCode() + ")";
        }
        return "Reason: (" + getReasonCode() + ")";
    }
}
