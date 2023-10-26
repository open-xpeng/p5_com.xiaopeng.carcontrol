package com.xiaopeng.xui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes2.dex */
public class XActivityUtils {
    private static final String TAG = "ActivityUtils";

    public static int launchIntentFlag() {
        return 270548992;
    }

    @Deprecated
    public static int makeIntentFlag() {
        return 270548992;
    }

    public static void finish(Activity activity) {
        if (activity != null) {
            try {
                boolean isChild = activity.isChild();
                activity.finish();
                if (activity.isFinishing() || isChild) {
                    return;
                }
                startHome(activity);
            } catch (Exception e) {
                XLogUtils.w(TAG, "finish e=" + e);
            }
        }
    }

    public static boolean moveTaskToBack(Activity activity, boolean z) {
        boolean z2 = false;
        if (activity != null) {
            try {
                boolean isChild = activity.isChild();
                z2 = activity.moveTaskToBack(z);
                if (!z2 && !isChild) {
                    startHome(activity);
                }
            } catch (Exception e) {
                XLogUtils.w(TAG, "moveTaskToBack e=" + e);
            }
        }
        return z2;
    }

    public static void startHome(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            intent.addFlags(270532608);
            context.startActivity(intent);
        } catch (Exception e) {
            XLogUtils.w(TAG, "startHome e=" + e);
        }
    }

    public static void startActivity(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            XLogUtils.w(TAG, "startActivity e=" + e);
        }
    }

    public static void startActivityForResult(Activity activity, Intent intent, int i) {
        try {
            activity.startActivityForResult(intent, i);
        } catch (Exception e) {
            XLogUtils.w(TAG, "startActivityForResult e=" + e);
        }
    }

    public static void enterFullscreen(Activity activity) {
        activity.requestWindowFeature(14);
        activity.getWindow().getDecorView().setSystemUiVisibility(5894);
    }
}
