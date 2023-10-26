package com.alibaba.sdk.android.oss.common;

import android.util.Log;

/* loaded from: classes.dex */
public class OSSLog {
    private static final String TAG = "OSS-Android-SDK";
    private static boolean enableLog = false;

    public static void enableLog() {
        enableLog = true;
    }

    public static void disableLog() {
        enableLog = false;
    }

    public static boolean isEnableLog() {
        return enableLog;
    }

    public static void logInfo(String str) {
        logInfo(str, true);
    }

    public static void logInfo(String str, boolean z) {
        if (enableLog) {
            Log.i(TAG, "[INFO]: ".concat(str));
            log2Local(str, z);
        }
    }

    public static void logVerbose(String str) {
        logVerbose(str, true);
    }

    public static void logVerbose(String str, boolean z) {
        if (enableLog) {
            Log.v(TAG, "[Verbose]: ".concat(str));
            log2Local(str, z);
        }
    }

    public static void logWarn(String str) {
        logWarn(str, true);
    }

    public static void logWarn(String str, boolean z) {
        if (enableLog) {
            Log.w(TAG, "[Warn]: ".concat(str));
            log2Local(str, z);
        }
    }

    public static void logDebug(String str) {
        logDebug(TAG, str);
    }

    public static void logDebug(String str, String str2) {
        logDebug(str, str2, true);
    }

    public static void logDebug(String str, boolean z) {
        logDebug(TAG, str, z);
    }

    public static void logDebug(String str, String str2, boolean z) {
        if (enableLog) {
            Log.d(str, "[Debug]: ".concat(str2));
            log2Local(str2, z);
        }
    }

    public static void logError(String str) {
        logError(TAG, str);
    }

    public static void logError(String str, String str2) {
        logDebug(str, str2, true);
    }

    public static void logError(String str, boolean z) {
        logError(TAG, str, z);
    }

    public static void logError(String str, String str2, boolean z) {
        if (enableLog) {
            Log.d(str, "[Error]: ".concat(str2));
            log2Local(str2, z);
        }
    }

    public static void logThrowable2Local(Throwable th) {
        if (enableLog) {
            OSSLogToFileUtils.getInstance().write(th);
        }
    }

    private static void log2Local(String str, boolean z) {
        if (z) {
            OSSLogToFileUtils.getInstance().write(str);
        }
    }
}
