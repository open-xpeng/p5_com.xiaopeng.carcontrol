package com.xiaopeng.xvs.xid.base;

/* loaded from: classes2.dex */
public interface IException {
    public static final int ERROR_CODE_NO_ERROR = 0;
    public static final int ERROR_CODE_UNKNOWN = -1;

    int getCode();

    String getMessage();

    String toString();

    default boolean isSuccess() {
        return getCode() == 0;
    }
}
