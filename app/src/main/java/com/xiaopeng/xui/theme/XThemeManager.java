package com.xiaopeng.xui.theme;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.libtheme.ThemeView;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class XThemeManager {
    public static void setWindowBackgroundResource(Configuration configuration, Window window, int i) {
        ThemeManager.setWindowBackgroundResource(configuration, window, i);
    }

    public static void setWindowBackgroundDrawable(Configuration configuration, Window window, Drawable drawable) {
        ThemeManager.setWindowBackgroundDrawable(configuration, window, drawable);
    }

    public static boolean isThemeChanged(Configuration configuration) {
        return ThemeManager.isThemeChanged(configuration);
    }

    public static void onConfigurationChanged(Configuration configuration, Context context, View view, String str, List<ThemeView> list) {
        ThemeManager.create(context, view, str, list).onConfigurationChanged(configuration);
    }

    public static int getDatNightMode(Context context) {
        return ThemeManager.getDayNightMode(context);
    }

    public static boolean isNight(Context context) {
        return isNightMode(context);
    }

    public static boolean isNight(Configuration configuration) {
        return configuration != null && (configuration.uiMode & 48) == 32;
    }

    public static HashMap<String, Integer> resolveAttribute(Context context, AttributeSet attributeSet) {
        return ThemeManager.resolveAttribute(context, attributeSet);
    }

    public static void updateAttribute(View view, HashMap<String, Integer> hashMap) {
        ThemeManager.updateAttribute(view, hashMap);
    }

    public static int getThemeMode(Context context) {
        return ThemeManager.getThemeMode(context);
    }

    public static boolean isNightMode(Context context) {
        return ThemeManager.isNightMode(context);
    }

    public static String getThemeStyle() {
        String systemProperties = getSystemProperties("persist.sys.theme.style");
        return systemProperties == null ? "" : systemProperties;
    }

    private static String getSystemProperties(String str) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getDeclaredMethod(ISync.SYNC_CALL_METHOD_GET, String.class).invoke(null, str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
