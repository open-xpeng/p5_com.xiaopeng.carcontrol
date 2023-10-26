package com.alibaba.sdk.android.httpdns;

import android.text.TextUtils;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes.dex */
class a {
    private static long a;
    private static String sSecretKey;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(String str, String str2) {
        if (k.b(str)) {
            try {
                return k.a(str + "-" + sSecretKey + "-" + str2);
            } catch (NoSuchAlgorithmException unused) {
                return "";
            }
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a() {
        return !TextUtils.isEmpty(sSecretKey);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getTimestamp() {
        return String.valueOf((System.currentTimeMillis() / 1000) + a + 600);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setAuthCurrentTime(long j) {
        a = j - (System.currentTimeMillis() / 1000);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setSecretKey(String str) {
        sSecretKey = str;
    }
}
