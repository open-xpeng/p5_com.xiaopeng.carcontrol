package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

import java.util.List;

/* loaded from: classes2.dex */
public interface ILifeCycle {
    void registerCanEventMsg(List<Class<? extends IEventMsg>> list);

    void unregisterCanEventMsg(List<Class<? extends IEventMsg>> list);
}
