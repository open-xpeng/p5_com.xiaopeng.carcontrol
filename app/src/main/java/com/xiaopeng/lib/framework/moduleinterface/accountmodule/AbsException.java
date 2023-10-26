package com.xiaopeng.lib.framework.moduleinterface.accountmodule;

/* loaded from: classes2.dex */
public abstract class AbsException extends Exception {
    public abstract int getCode();

    public AbsException() {
    }

    public AbsException(int i) {
    }

    public AbsException(String str) {
        super(str);
    }

    public AbsException(int i, String str) {
        super(str);
    }
}
