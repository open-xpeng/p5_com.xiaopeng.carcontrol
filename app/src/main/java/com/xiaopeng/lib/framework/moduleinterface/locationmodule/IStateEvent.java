package com.xiaopeng.lib.framework.moduleinterface.locationmodule;

/* loaded from: classes2.dex */
public interface IStateEvent {

    /* loaded from: classes2.dex */
    public enum TYPE {
        BOUND,
        UNBOUND
    }

    TYPE type();
}
