package com.alibaba.sdk.android.httpdns;

import android.util.Log;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;

/* loaded from: classes.dex */
public class i {
    private static ILogger a = null;
    private static boolean b = false;
    private static int c = -1;

    public static void a(Throwable th) {
        if (!b || th == null) {
            return;
        }
        th.printStackTrace();
    }

    private static String b() {
        if (c == -1) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            int length = stackTrace.length;
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (stackTrace[i].getMethodName().equals("getTraceInfo")) {
                    c = i2 + 1;
                    break;
                } else {
                    i2++;
                    i++;
                }
            }
        }
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[c + 1];
        return stackTraceElement.getFileName() + QuickSettingConstants.JOINER + stackTraceElement.getLineNumber() + " - [" + stackTraceElement.getMethodName() + "]";
    }

    public static void d(String str) {
        if (b && str != null) {
            Log.d("HttpDnsSDK", Thread.currentThread().getId() + " - " + b() + " - " + str);
        }
        ILogger iLogger = a;
        if (iLogger != null) {
            iLogger.log(str);
        }
    }

    public static void e(String str) {
        if (b && str != null) {
            Log.i("HttpDnsSDK", Thread.currentThread().getId() + " - " + b() + " - " + str);
        }
        ILogger iLogger = a;
        if (iLogger != null) {
            iLogger.log(str);
        }
    }

    public static void f(String str) {
        if (b && str != null) {
            Log.e("HttpDnsSDK", Thread.currentThread().getId() + " - " + b() + " - " + str);
        }
        ILogger iLogger = a;
        if (iLogger != null) {
            iLogger.log(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized void setLogEnabled(boolean z) {
        synchronized (i.class) {
            b = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setLogger(ILogger iLogger) {
        a = iLogger;
    }
}
