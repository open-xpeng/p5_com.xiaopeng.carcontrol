package com.alibaba.mtl.log.e;

import android.os.Process;
import android.util.Log;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;

/* compiled from: Logger.java */
/* loaded from: classes.dex */
public class i {
    private static boolean J = false;
    private static boolean K = false;
    private static String af = "UTAnalytics:";

    public static boolean k() {
        return J;
    }

    public static boolean l() {
        return K;
    }

    public static void d(boolean z) {
        K = z;
    }

    public static void a(String str, Object... objArr) {
        if (K) {
            String str2 = af + str;
            StringBuilder sb = new StringBuilder();
            sb.append("pid:").append(Process.myPid()).append(" ");
            if (objArr != null) {
                for (int i = 0; i < objArr.length; i++) {
                    if (objArr[i] != null) {
                        String obj = objArr[i].toString();
                        if (obj.endsWith(QuickSettingConstants.JOINER) || obj.endsWith(": ")) {
                            sb.append(obj);
                        } else {
                            sb.append(obj).append(",");
                        }
                    }
                }
            }
            Log.d(str2, sb.toString());
        }
    }

    public static void a(String str, Object obj, Throwable th) {
        if (l() || k()) {
            Log.w(str + af, obj + "", th);
        }
    }

    public static void a(String str, Object obj) {
        if (l() || k()) {
            Log.w(str + af, obj + "");
        }
    }

    public static void a(String str, String... strArr) {
        if (K) {
            String str2 = af + str;
            StringBuilder sb = new StringBuilder();
            sb.append("pid:").append(Process.myPid()).append(" ");
            if (strArr != null) {
                for (int i = 0; i < strArr.length; i++) {
                    if (strArr[i] != null) {
                        String str3 = strArr[i];
                        if (str3.endsWith(QuickSettingConstants.JOINER) || str3.endsWith(": ")) {
                            sb.append(str3);
                        } else {
                            sb.append(str3).append(",");
                        }
                    }
                }
            }
            Log.i(str2, sb.toString());
        }
    }
}
