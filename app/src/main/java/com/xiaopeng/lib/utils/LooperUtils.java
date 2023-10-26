package com.xiaopeng.lib.utils;

import android.os.Looper;

/* loaded from: classes2.dex */
public class LooperUtils {
    public static void assertMainThread() {
        if (!isOnMainThread()) {
            throw new IllegalArgumentException("You must call this method on the main thread");
        }
    }

    public static void assertAsyncThread() {
        if (isOnMainThread()) {
            throw new IllegalArgumentException("You must call this method on the async thread");
        }
    }

    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
