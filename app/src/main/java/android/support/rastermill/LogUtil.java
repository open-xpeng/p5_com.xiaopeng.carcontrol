package android.support.rastermill;

import android.util.Log;

/* loaded from: classes.dex */
public class LogUtil {
    public static final String TAG = "FrameSequence";
    private static boolean sLogEnable;

    public static boolean isLogEnable() {
        return sLogEnable;
    }

    public static void enableLog(boolean z) {
        sLogEnable = z;
    }

    public static void e(String str) {
        if (sLogEnable) {
            Log.e(TAG, str);
        }
    }

    public static void e(String str, Throwable th) {
        if (sLogEnable) {
            Log.e(TAG, str, th);
        }
    }
}
