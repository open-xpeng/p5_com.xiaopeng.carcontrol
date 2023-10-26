package com.xiaopeng.speech.vui.exception;

/* loaded from: classes2.dex */
public class VuiException extends RuntimeException {
    protected final VuiErrorCode errorCode;

    public VuiException() {
        super(VuiErrorCode.UNSPECIFIED.getDescription());
        this.errorCode = VuiErrorCode.UNSPECIFIED;
    }

    public VuiException(VuiErrorCode vuiErrorCode) {
        super(vuiErrorCode.getDescription());
        this.errorCode = vuiErrorCode;
    }

    public VuiErrorCode getErrorCode() {
        return this.errorCode;
    }
}
