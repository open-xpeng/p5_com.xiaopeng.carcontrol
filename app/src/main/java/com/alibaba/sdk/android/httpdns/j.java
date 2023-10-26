package com.alibaba.sdk.android.httpdns;

import android.util.Log;
import java.lang.Thread;

/* loaded from: classes.dex */
public class j implements Thread.UncaughtExceptionHandler {
    private void b(Throwable th) {
        com.alibaba.sdk.android.httpdns.d.a a = com.alibaba.sdk.android.httpdns.d.a.a();
        if (a != null) {
            a.k(th.getMessage());
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        Log.e("HttpDnsSDK", "Catch an uncaught exception, " + thread.getName() + ", error message: " + th.getMessage());
        b(th);
        th.printStackTrace();
    }
}
