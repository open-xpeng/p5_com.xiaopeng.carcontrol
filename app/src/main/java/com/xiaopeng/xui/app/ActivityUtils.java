package com.xiaopeng.xui.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.xui.utils.XLogUtils;

@Deprecated
/* loaded from: classes2.dex */
public class ActivityUtils {
    private static final String TAG = "ActivityUtils";

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
                XLogUtils.d(TAG, "finish e=" + e);
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
                XLogUtils.d(TAG, "moveTaskToBack e=" + e);
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
            XLogUtils.d(TAG, "startHome e=" + e);
        }
    }
}
