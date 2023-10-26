package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public abstract class AbstractEventMsg<T> implements IEventMsg<T> {
    private T mData;

    @Override // com.xiaopeng.lib.framework.moduleinterface.carcontroller.IEventMsg
    public void setData(T t) {
        this.mData = t;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.carcontroller.IEventMsg
    public T getData() {
        return this.mData;
    }

    public String toString() {
        if (this.mData != null) {
            return getClass().getSimpleName() + " = " + this.mData.toString();
        }
        return getClass().getSimpleName() + " = empty content";
    }
}
