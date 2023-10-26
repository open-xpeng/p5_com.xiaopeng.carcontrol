package com.xiaopeng.carcontrol.util;

import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

/* loaded from: classes2.dex */
public final class ResUtils {
    private static final String TAG = "ResUtils";

    public static String getString(int stringId) {
        if (stringId <= 0) {
            LogUtils.w(TAG, "Invalid String resource id");
            return null;
        }
        try {
            return ContextUtils.getContext().getString(stringId);
        } catch (Exception unused) {
            LogUtils.e(TAG, "getString id=" + stringId + " failed, return null");
            return "";
        }
    }

    public static String getString(int resId, Object... formatArgs) {
        try {
            return ContextUtils.getContext().getString(resId, formatArgs);
        } catch (Exception unused) {
            LogUtils.e(TAG, "getString id=" + resId + " failed, return null");
            return "";
        }
    }

    public static Drawable getDrawable(int drawableId) {
        try {
            return ContextCompat.getDrawable(ContextUtils.getContext(), drawableId);
        } catch (Exception unused) {
            LogUtils.e(TAG, "getDrawable id=" + drawableId + " failed, return null");
            return null;
        }
    }

    public static String[] getStringArray(int arrayResId) {
        return ContextUtils.getContext().getResources().getStringArray(arrayResId);
    }

    public static int getInt(int intResId) {
        try {
            return ContextUtils.getContext().getResources().getInteger(intResId);
        } catch (Exception unused) {
            LogUtils.e(TAG, "getInt id=" + intResId + " failed, return null");
            return 0;
        }
    }

    public static int getDimensionPixelSize(int dimenId) {
        return ContextUtils.getContext().getResources().getDimensionPixelSize(dimenId);
    }

    public static float getDimension(int dimenId) {
        return ContextUtils.getContext().getResources().getDimension(dimenId);
    }

    public static int getColor(int colorResId) {
        try {
            return ContextUtils.getContext().getResources().getColor(colorResId, ContextUtils.getContext().getTheme());
        } catch (Exception unused) {
            LogUtils.e(TAG, "getColor id=" + colorResId + " failed, return null");
            return 0;
        }
    }

    public static int dp2px(int dpValue) {
        return (int) ((dpValue * ContextUtils.getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static boolean isScreenOrientationLand() {
        return ContextUtils.getContext().getResources().getConfiguration().orientation == 2;
    }
}
