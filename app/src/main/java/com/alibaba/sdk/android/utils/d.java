package com.alibaba.sdk.android.utils;

import android.util.Log;

/* compiled from: Logger.java */
/* loaded from: classes.dex */
public class d {
    private static boolean c = false;

    public static boolean c() {
        return c;
    }

    public static void setLogEnabled(boolean z) {
        c = z;
    }

    public static void a(String str, String str2) {
        if (c) {
            Log.d(str, str2);
        }
    }

    public static void b(String str, String str2) {
        if (c) {
            Log.i(str, str2);
        }
    }

    public static void c(String str, String str2) {
        if (c) {
            Log.e(str, str2);
        }
    }

    public static void a(String str, Throwable th) {
        if (!c || th == null) {
            return;
        }
        Log.e(str, th.toString(), th);
    }
}
