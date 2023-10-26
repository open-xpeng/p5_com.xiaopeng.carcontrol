package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging;

/* loaded from: classes2.dex */
public abstract class MessagingException extends Exception {
    public static final int REASON_ALREADY_INITIALIZED = 19;
    public static final int REASON_MQTT_EXCEPTION = 1;
    public static final int REASON_NOT_ALLOW_PUBLISH = 32;
    public static final int REASON_NOT_ALLOW_SUBSCRIBE = 33;
    public static final int REASON_NOT_INITIALIZED = 17;
    public static final int REASON_NOT_SUPPORTED_CHANNEL = 16;
    public static final int REASON_NO_EXCEPTION = 0;
    public static final int REASON_SERVICE_BINDING_ERROR = 18;
    private static final long serialVersionUID = 100;

    public abstract int getProtocolReasonCode();

    public abstract int getReasonCode();

    public MessagingException(String str) {
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
