package com.xiaopeng.xvs.xid.utils;

import android.util.Log;

/* loaded from: classes2.dex */
public class L {
    private static boolean enable = true;

    public static boolean isLoggable() {
        return enable;
    }

    public static void setLoggable(boolean z) {
        enable = z;
    }

    public static void v(String str, String str2) {
        if (enable) {
            Log.i(str, str2);
        }
    }

    public static void v(String str, String str2, Throwable th) {
        if (enable) {
            Log.i(str, str2, th);
        }
    }

    public static void d(String str, String str2) {
        if (enable) {
            Log.i(str, str2);
        }
    }

    public static void d(String str, String str2, Throwable th) {
        if (enable) {
            Log.i(str, str2, th);
        }
    }

    public static void i(String str, String str2) {
        if (enable) {
            Log.i(str, str2);
        }
    }

    public static void i(String str, String str2, Throwable th) {
        if (enable) {
            Log.i(str, str2, th);
        }
    }

    public static void w(String str, String str2) {
        if (enable) {
            Log.w(str, str2);
        }
    }

    public static void w(String str, String str2, Throwable th) {
        if (enable) {
            Log.w(str, str2, th);
        }
    }

    public static void w(String str, Throwable th) {
        if (enable) {
            Log.w(str, th);
        }
    }

    public static void e(String str, String str2) {
        if (enable) {
            Log.e(str, str2);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (enable) {
            Log.e(str, str2, th);
        }
    }
}
