package com.xiaopeng.carcontrol.util;

import android.app.ActivityManager;
import android.app.UiModeManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;

/* loaded from: classes2.dex */
public class Utils {
    public static boolean isUserRelease() {
        return "user".equals(Build.TYPE);
    }

    public static boolean isMonkeyRunning() {
        return ActivityManager.isUserAMonkey();
    }

    public static Uri getResourceUri(Context context, int resourceId) {
        return new Uri.Builder().scheme("android.resource").authority(context.getPackageName()).path(String.valueOf(resourceId)).build();
    }

    public static int getDayNightMode(Context context) {
        return ((UiModeManager) context.getSystemService("uimode")).getDayNightMode();
    }

    public static int getDayNightAutoMode(Context context) {
        return ((UiModeManager) context.getSystemService("uimode")).getDayNightAutoMode();
    }

    public static void setDayNightMode(Context context, int daynightMode) {
        ((UiModeManager) context.getSystemService("uimode")).applyDayNightMode(daynightMode);
    }
}
