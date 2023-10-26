package com.xiaopeng.carcontrol.util;

import android.content.Context;
import android.view.View;
import com.xiaopeng.carcontrol.App;

/* loaded from: classes2.dex */
public class UIUtils {
    private static final String TAG = "UIUtils";

    public static int dp2px(Context context, int value) {
        return (int) ((value * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void delayEnable(final View view) {
        if (view == null) {
            LogUtils.e(TAG, "view is null");
            return;
        }
        view.setEnabled(false);
        ThreadUtils.postDelayed(1, new Runnable() { // from class: com.xiaopeng.carcontrol.util.-$$Lambda$UIUtils$pF3DMVYqFzlhJLpewoNo_spqKnM
            @Override // java.lang.Runnable
            public final void run() {
                view.setEnabled(true);
            }
        }, 1000L);
    }

    public static void delayClickable(final View view, long duration) {
        if (view == null) {
            LogUtils.e(TAG, "view is null");
            return;
        }
        view.setClickable(false);
        ThreadUtils.postDelayed(1, new Runnable() { // from class: com.xiaopeng.carcontrol.util.-$$Lambda$UIUtils$gmMRTmti76KsueUx134EpNe2DGc
            @Override // java.lang.Runnable
            public final void run() {
                view.setClickable(true);
            }
        }, duration);
    }

    public static void delayClickable(View view) {
        delayClickable(view, 1000L);
    }

    public static String getString(int resid) {
        return App.getInstance().getString(resid);
    }
}
