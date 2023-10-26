package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public class NotSupportV2Exception extends Exception {
    public NotSupportV2Exception() {
        this("this api is not support for carControllerModuleImplement V2");
    }

    public NotSupportV2Exception(String str) {
        super(str);
    }

    public NotSupportV2Exception(String str, Throwable th) {
        super(str, th);
    }

    public NotSupportV2Exception(Throwable th) {
        super(th);
    }
}
