package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public abstract class AbsError implements IError {
    int code;

    @Override // com.xiaopeng.lib.framework.moduleinterface.carcontroller.IError
    public void setCode(int i) {
        this.code = i;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.carcontroller.IError
    public int getCode() {
        return this.code;
    }
}
