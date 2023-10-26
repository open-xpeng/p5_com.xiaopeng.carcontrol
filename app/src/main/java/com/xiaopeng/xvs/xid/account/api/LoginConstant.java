package com.xiaopeng.xvs.xid.account.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* loaded from: classes2.dex */
public interface LoginConstant {
    public static final int TYPE_LOGIN_BT_KEY = 5;
    public static final int TYPE_LOGIN_FACE_ID = 4;
    public static final int TYPE_LOGIN_MESSAGE_PUSH = 2;
    public static final int TYPE_LOGIN_MQTT_PUSH = 3;
    public static final int TYPE_LOGIN_QR_SCAN = 1;
    public static final int TYPE_LOGOUT_ACTIVE = 1;
    public static final int TYPE_LOGOUT_EXCEPTION = 6;
    public static final int TYPE_LOGOUT_INTERRUPT = 5;
    public static final int TYPE_LOGOUT_MESSAGE_PUSH = 2;
    public static final int TYPE_LOGOUT_MQTT_PUSH = 3;
    public static final int TYPE_LOGOUT_OVER_TIME = 4;

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface LoginType {
    }

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface LogoutType {
    }
}
