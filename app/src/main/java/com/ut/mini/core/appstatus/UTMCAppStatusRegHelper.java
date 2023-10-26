package com.ut.mini.core.appstatus;

import android.app.Application;

/* loaded from: classes.dex */
public class UTMCAppStatusRegHelper {
    public static void registerAppStatusCallbacks(UTMCAppStatusCallbacks uTMCAppStatusCallbacks) {
        if (uTMCAppStatusCallbacks != null) {
            UTMCAppStatusMonitor.getInstance().registerAppStatusCallbacks(uTMCAppStatusCallbacks);
        }
    }

    public static void unRegisterAppStatusCallbacks(UTMCAppStatusCallbacks uTMCAppStatusCallbacks) {
        if (uTMCAppStatusCallbacks != null) {
            UTMCAppStatusMonitor.getInstance().unregisterAppStatusCallbacks(uTMCAppStatusCallbacks);
        }
    }

    public static void registeActivityLifecycleCallbacks(Application application) {
        if (application != null) {
            application.registerActivityLifecycleCallbacks(UTMCAppStatusMonitor.getInstance());
        }
    }

    public static void unregisterActivityLifecycleCallbacks(Application application) {
        if (application != null) {
            application.unregisterActivityLifecycleCallbacks(UTMCAppStatusMonitor.getInstance());
        }
    }
}
