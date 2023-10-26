package com.xiaopeng.speech.common.util;

import android.app.ActivityThread;
import android.util.Log;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;

/* loaded from: classes2.dex */
public class LogUtils {
    public static int LOG_LEVEL = 2;
    public static String CTAG = getAppName();
    public static Logger sLogger = new DefaultLogger();
    public static boolean sLogEnable = true;

    /* loaded from: classes2.dex */
    public interface Logger {
        void logByLevel(int i, String str, String str2, String str3);
    }

    public static boolean isLogEnable() {
        return sLogEnable;
    }

    public static void setLogEnable(boolean z) {
        sLogEnable = z;
    }

    public static void setLogLevel(int i) {
        LOG_LEVEL = i;
    }

    public static int getLogLevel() {
        return LOG_LEVEL;
    }

    public static void setLogger(Logger logger) {
        sLogger = logger;
    }

    public static boolean isLogLevelEnabled(int i) {
        return LOG_LEVEL <= i && isLogEnable();
    }

    public static void v(String str) {
        if (isLogLevelEnabled(2)) {
            doLog(2, null, str, null, true);
        }
    }

    public static void v(Object obj, String str) {
        if (isLogLevelEnabled(2)) {
            doLog(2, obj, str, null, true);
        }
    }

    public static void v(Object obj, String str, Object... objArr) {
        if (isLogLevelEnabled(2)) {
            doLog(2, obj, String.format(str, objArr), null, true);
        }
    }

    public static void v(Object obj, String str, Throwable th) {
        if (isLogLevelEnabled(2)) {
            doLog(2, obj, str, th, true);
        }
    }

    public static void v(Object obj, Throwable th) {
        if (isLogLevelEnabled(2)) {
            doLog(2, obj, "Exception occurs at", th, true);
        }
    }

    public static void d(String str) {
        if (isLogLevelEnabled(3)) {
            doLog(3, null, str, null, true);
        }
    }

    public static void d(Object obj, String str) {
        if (isLogLevelEnabled(3)) {
            doLog(3, obj, str, null, true);
        }
    }

    public static void d(Object obj, String str, Object... objArr) {
        if (isLogLevelEnabled(3)) {
            doLog(3, obj, String.format(str, objArr), null, true);
        }
    }

    public static void d(Object obj, String str, Throwable th) {
        if (isLogLevelEnabled(3)) {
            doLog(3, obj, str, th, true);
        }
    }

    public static void d(Object obj, Throwable th) {
        if (isLogLevelEnabled(3)) {
            doLog(3, obj, "Exception occurs at", th, true);
        }
    }

    public static void i(String str) {
        if (isLogLevelEnabled(4)) {
            doLog(4, null, str, null, true);
        }
    }

    public static void i(Object obj, String str) {
        if (isLogLevelEnabled(4)) {
            doLog(4, obj, str, null, true);
        }
    }

    public static void i(Object obj, String str, Object... objArr) {
        if (isLogLevelEnabled(4)) {
            doLog(4, obj, String.format(str, objArr), null, true);
        }
    }

    public static void i(Object obj, String str, Throwable th) {
        if (isLogLevelEnabled(4)) {
            doLog(4, obj, str, th, true);
        }
    }

    public static void i(Object obj, Throwable th) {
        if (isLogLevelEnabled(4)) {
            doLog(4, obj, "Exception occurs at", th, true);
        }
    }

    public static void w(String str) {
        if (isLogLevelEnabled(5)) {
            doLog(5, null, str, null, true);
        }
    }

    public static void w(Object obj, String str) {
        if (isLogLevelEnabled(5)) {
            doLog(5, obj, str, null, true);
        }
    }

    public static void w(Object obj, String str, Object... objArr) {
        if (isLogLevelEnabled(5)) {
            doLog(5, obj, String.format(str, objArr), null, true);
        }
    }

    public static void w(Object obj, String str, Throwable th) {
        if (isLogLevelEnabled(5)) {
            doLog(5, obj, str, th, true);
        }
    }

    public static void w(Object obj, Throwable th) {
        if (isLogLevelEnabled(5)) {
            doLog(5, obj, "Exception occurs at", th, true);
        }
    }

    public static void e(String str) {
        if (isLogLevelEnabled(6)) {
            doLog(6, null, str, null, true);
        }
    }

    public static void e(Object obj, String str) {
        if (isLogLevelEnabled(6)) {
            doLog(6, obj, str, null, true);
        }
    }

    public static void e(Object obj, String str, Object... objArr) {
        if (isLogLevelEnabled(6)) {
            doLog(6, obj, String.format(str, objArr), null, true);
        }
    }

    public static void e(Object obj, String str, Throwable th) {
        if (isLogLevelEnabled(6)) {
            doLog(6, obj, str, th, true);
        }
    }

    public static void e(Object obj, Throwable th) {
        if (isLogLevelEnabled(6)) {
            doLog(6, obj, "Exception occurs at", th, true);
        }
    }

    public static void log(int i, Object obj, String str, Throwable th, boolean z) {
        if (isLogLevelEnabled(i)) {
            doLog(i, obj, str, th, z);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void doLog(int r4, java.lang.Object r5, java.lang.String r6, java.lang.Throwable r7, boolean r8) {
        /*
            if (r8 == 0) goto L1e
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            java.lang.StackTraceElement[] r0 = r0.getStackTrace()
            r1 = 0
            if (r0 == 0) goto L13
            int r2 = r0.length
            r3 = 4
            if (r2 <= r3) goto L13
            r1 = r0[r3]
        L13:
            if (r1 == 0) goto L1e
            java.lang.String r0 = r1.getFileName()
            int r1 = r1.getLineNumber()
            goto L21
        L1e:
            java.lang.String r0 = ""
            r1 = 0
        L21:
            java.lang.String r6 = msgForTextLog(r0, r1, r6, r7, r8)
            java.lang.String r5 = objClassName(r5)
            boolean r7 = android.text.TextUtils.isEmpty(r5)
            if (r7 == 0) goto L30
            goto L31
        L30:
            r0 = r5
        L31:
            logByLevel(r4, r0, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.common.util.LogUtils.doLog(int, java.lang.Object, java.lang.String, java.lang.Throwable, boolean):void");
    }

    private static void logByLevel(int i, String str, String str2) {
        sLogger.logByLevel(i, str2, str, null);
    }

    private static String msgForTextLog(String str, int i, String str2, Throwable th, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        if (z) {
            sb.append("(T:").append(Thread.currentThread().getId()).append(")");
            if (CTAG != null) {
                sb.append("(C:").append(CTAG).append(")");
            }
            sb.append("at (").append(str).append(QuickSettingConstants.JOINER).append(i).append(")");
        }
        if (th != null) {
            sb.append('\n').append(Log.getStackTraceString(th));
        }
        return sb.toString();
    }

    private static String objClassName(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Class) {
            return ((Class) obj).getSimpleName();
        }
        return obj.getClass().getSimpleName();
    }

    /* loaded from: classes2.dex */
    private static class DefaultLogger implements Logger {
        private DefaultLogger() {
        }

        @Override // com.xiaopeng.speech.common.util.LogUtils.Logger
        public void logByLevel(int i, String str, String str2, String str3) {
            if (i == 2) {
                Log.v(str2, str);
            } else if (i == 3) {
                Log.d(str2, str);
                String str4 = "DEBUG: " + str;
            } else if (i == 4) {
                Log.i(str2, str);
                String str5 = "INFO: " + str;
            } else if (i == 5) {
                Log.w(str2, str);
                String str6 = "WARN: " + str;
            } else if (i != 6) {
            } else {
                Log.e(str2, str);
                String str7 = "ERROR: " + str;
            }
        }
    }

    private static String getAppName() {
        String[] split = ActivityThread.currentApplication().getPackageName().split("\\.");
        return split[split.length - 1];
    }
}
