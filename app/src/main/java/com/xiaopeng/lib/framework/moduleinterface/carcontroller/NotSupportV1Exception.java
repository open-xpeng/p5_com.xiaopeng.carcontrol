package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public class NotSupportV1Exception extends Exception {
    public NotSupportV1Exception() {
        this("this api is not support for carControllerModuleImplement V1");
    }

    public NotSupportV1Exception(String str) {
        super(str);
    }

    public NotSupportV1Exception(String str, Throwable th) {
        super(str, th);
    }

    public NotSupportV1Exception(Throwable th) {
        super(th);
    }
}
