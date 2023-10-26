package com.xiaopeng.xui;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.xiaopeng.xpui.BuildConfig;
import com.xiaopeng.xui.drawable.shimmer.XShimmer;
import com.xiaopeng.xui.utils.XLogUtils;
import com.xiaopeng.xvs.xid.sync.api.ISync;

/* loaded from: classes2.dex */
public class Xui {
    private static Application mApp;
    private static String mPackageName;
    private static boolean sDialogFullScreen;
    private static boolean sFontScaleDynamicChangeEnable;
    private static boolean sSharedDisplay;
    private static boolean sVuiEnable;

    public static void clear() {
    }

    public static void release() {
    }

    public static void init(Application application) {
        mApp = application;
        XShimmer.msGlobalEnable = false;
        if ("1".equals(getSystemProperties("persist.sys.xp.shared_display.enable"))) {
            sSharedDisplay = true;
        }
        mPackageName = application.getPackageName();
        Log.i("xpui", sSharedDisplay + "," + BuildConfig.BUILD_VERSION);
    }

    public static Context getContext() {
        Application application = mApp;
        if (application != null) {
            return application;
        }
        throw new RuntimeException("Xui must be call Xui#init()!");
    }

    private static String getSystemProperties(String str) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getDeclaredMethod(ISync.SYNC_CALL_METHOD_GET, String.class).invoke(null, str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isVuiEnable() {
        return sVuiEnable;
    }

    public static void setVuiEnable(boolean z) {
        sVuiEnable = z;
    }

    public static boolean isFontScaleDynamicChangeEnable() {
        return sFontScaleDynamicChangeEnable;
    }

    public static void setFontScaleDynamicChangeEnable(boolean z) {
        sFontScaleDynamicChangeEnable = z;
    }

    public static boolean isDialogFullScreen() {
        return sDialogFullScreen;
    }

    public static boolean isSharedDisplay() {
        return sSharedDisplay;
    }

    public static void setDialogFullScreen(boolean z) {
        sDialogFullScreen = z;
    }

    public static String getPackageName() {
        return mPackageName;
    }

    public static void setLogLevel(int i) {
        XLogUtils.setLogLevel(i);
    }
}
