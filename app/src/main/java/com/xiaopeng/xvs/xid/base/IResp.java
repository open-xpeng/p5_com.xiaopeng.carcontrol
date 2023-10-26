package com.xiaopeng.xvs.xid.base;

/* loaded from: classes2.dex */
public interface IResp<T> {
    public static final int ERROR_CODE_NO_ERROR = 0;
    public static final int ERROR_CODE_UNKNOWN = -1;

    int getCode();

    T getData();

    String getMessage();

    String toString();
}
