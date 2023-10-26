package com.alibaba.sdk.android.httpdns.e;

import android.util.Log;
import java.util.Random;

/* loaded from: classes.dex */
public class a {
    private String q;

    /* renamed from: com.alibaba.sdk.android.httpdns.e.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    private static final class C0012a {
        private static final a a = new a();
    }

    private a() {
        try {
            Random random = new Random();
            char[] cArr = new char[12];
            for (int i = 0; i < 12; i++) {
                cArr[i] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt(62));
            }
            this.q = new String(cArr);
        } catch (Exception e) {
            Log.d("SessionTrackMgr", e.getMessage(), e);
        }
    }

    public static a a() {
        return C0012a.a;
    }

    public String getSessionId() {
        return this.q;
    }

    public String h() {
        return com.alibaba.sdk.android.httpdns.c.a.a().h();
    }

    public String k() {
        int networkType = com.alibaba.sdk.android.httpdns.c.a.a().getNetworkType();
        return networkType != 1 ? networkType != 2 ? networkType != 3 ? networkType != 4 ? "unknown" : "4g" : "3g" : "2g" : "wifi";
    }
}
