package com.ut.mini.plugin;

import android.content.Context;

/* loaded from: classes.dex */
public class UTPluginContext {
    public static final int DEBUG_LOG_SWITCH = 1;
    private Context mContext = null;
    private boolean T = false;
    private boolean U = false;

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return this.mContext;
    }

    public void setDebugLogFlag(boolean z) {
        this.T = z;
    }

    public boolean isDebugLogEnable() {
        return this.T;
    }

    public void enableRealtimeDebug() {
        this.U = true;
    }

    public boolean isRealtimeDebugEnable() {
        return this.U;
    }
}
