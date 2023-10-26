package com.irdeto.securesdk;

/* loaded from: classes.dex */
public class CryptoException extends Exception {
    private int errorCode;
    private String message;

    public CryptoException(int i, String str) {
        this.errorCode = i;
        this.message = str;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.message;
    }
}
