package com.ut.mini.plugin;

/* loaded from: classes.dex */
public abstract class UTPluginMsgDispatchDelegate {
    private Object f;

    public boolean isMatchPlugin(UTPlugin uTPlugin) {
        return true;
    }

    public final Object getMsgObj() {
        return this.f;
    }

    public UTPluginMsgDispatchDelegate(Object obj) {
        this.f = null;
        this.f = obj;
    }

    public Object getDispatchObject(UTPlugin uTPlugin) {
        return this.f;
    }
}
