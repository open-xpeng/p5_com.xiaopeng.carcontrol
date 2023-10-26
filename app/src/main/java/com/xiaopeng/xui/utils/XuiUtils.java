package com.xiaopeng.xui.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.xiaopeng.xui.Xui;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class XuiUtils {
    private static volatile DisplayMetrics sDisplayMetrics;

    public static String formatVisibility(int i) {
        if (i != 0) {
            if (i != 4) {
                if (i != 8) {
                    return null;
                }
                return "gone";
            }
            return "invisible";
        }
        return "visible";
    }

    public static int getScreenWidth() {
        return Xui.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Xui.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getDisplayWidth() {
        return getDisplayMetrics().widthPixels;
    }

    private static DisplayMetrics getDisplayMetrics() {
        if (sDisplayMetrics == null) {
            synchronized (XuiUtils.class) {
                if (sDisplayMetrics == null) {
                    sDisplayMetrics = new DisplayMetrics();
                    WindowManager windowManager = (WindowManager) Xui.getContext().getSystemService("window");
                    if (windowManager != null) {
                        windowManager.getDefaultDisplay().getRealMetrics(sDisplayMetrics);
                    }
                }
            }
        }
        return sDisplayMetrics;
    }

    public static int getDisplayHeight() {
        return getDisplayMetrics().heightPixels;
    }

    public static int dip2px(float f) {
        return (int) ((f * Xui.getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int dip2px(Context context, int i) {
        return (int) ((context.getResources().getDimension(i) * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(float f) {
        return (int) ((f / Xui.getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int sp2px(float f) {
        return (int) ((f * Xui.getContext().getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int px2sp(float f) {
        return (int) ((f / Xui.getContext().getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int getColor(int i, Resources.Theme theme) {
        return Xui.getContext().getResources().getColor(i, theme);
    }

    public static float getFontScale() {
        return Xui.getContext().getResources().getDisplayMetrics().scaledDensity;
    }

    public static List<View> findViewsByType(ViewGroup viewGroup, Class cls) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (cls.isInstance(childAt)) {
                arrayList.add(childAt);
            } else if (childAt instanceof ViewGroup) {
                arrayList.addAll(findViewsByType((ViewGroup) childAt, cls));
            }
        }
        return arrayList;
    }
}
