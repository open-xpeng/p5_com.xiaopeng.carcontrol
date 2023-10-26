package com.xiaopeng.lib.bughunter.anr;

import android.util.Log;

/* loaded from: classes2.dex */
public class Config {
    public static boolean LOG_ENABLED = true;
    public static long THRESHOLD_TIME;

    public static void log(String str, String str2) {
        if (LOG_ENABLED) {
            Log.e(str, str2);
        }
    }
}
