package com.xiaopeng.lib.framework.moduleinterface.locationmodule;

/* loaded from: classes2.dex */
public abstract class ILocationServiceException extends Exception {
    public static final int REASON_SERVICE_BOUND = 3;
    public static final int REASON_SERVICE_NOT_INITIALIZED = 5;
    public static final int REASON_SERVICE_STARTED = 2;
    public static final int REASON_SERVICE_STOPPED = 1;
    public static final int REASON_SERVICE_SUBSCRIBE_EXCEPTION = 6;
    public static final int REASON_SERVICE_UNBOUND = 4;

    public abstract int getReasonCode();

    public ILocationServiceException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public String toString() {
        StringBuilder sb;
        String str;
        if (getMessage() != null) {
            sb = new StringBuilder().append(getMessage());
            str = " (";
        } else {
            sb = new StringBuilder();
            str = "Reason: (";
        }
        return sb.append(str).append(getReasonCode()).append(")").toString();
    }
}
