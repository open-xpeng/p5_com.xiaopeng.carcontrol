package com.xiaopeng.carcontrol.helper;

/* loaded from: classes2.dex */
public class ClickHelper {
    private static final int DEFAULT_VIEW_ID = 1;
    private static final long NORMAL_CLICK_INTERVAL = 500;
    private static long mLastClickTime = 0;
    private static int mLastClickedViewId = -1;

    public static boolean isFastClick() {
        return isFastClick(1, (long) NORMAL_CLICK_INTERVAL);
    }

    public static boolean isFastClick(long duration) {
        return isFastClick(1, duration);
    }

    public static boolean isFastClick(int viewId, long duration) {
        return isFastClick(viewId, duration, true);
    }

    public static boolean isFastClick(long duration, boolean resetTime) {
        return isFastClick(1, duration, resetTime);
    }

    public static boolean isFastClick(int viewId, long duration, boolean resetTime) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean z = false;
        if (viewId == mLastClickedViewId && currentTimeMillis - mLastClickTime <= duration) {
            z = true;
        }
        mLastClickedViewId = viewId;
        if (resetTime || mLastClickTime <= 0 || !z) {
            mLastClickTime = currentTimeMillis;
        }
        return z;
    }

    public static boolean isFastClick(int clickedViewId, int interceptViewId, long duration, boolean resetTime) {
        long currentTimeMillis = System.currentTimeMillis();
        int i = mLastClickedViewId;
        boolean z = false;
        if ((clickedViewId == i || interceptViewId == i) && currentTimeMillis - mLastClickTime <= duration) {
            z = true;
        }
        mLastClickedViewId = clickedViewId;
        if (resetTime || mLastClickTime <= 0 || !z) {
            mLastClickTime = currentTimeMillis;
        }
        return z;
    }

    public static void setDefaultViewIdCurrentTime(long currentTime) {
        mLastClickTime = currentTime;
    }
}
