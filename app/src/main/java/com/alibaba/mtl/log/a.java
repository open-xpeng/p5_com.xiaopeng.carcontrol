package com.alibaba.mtl.log;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.l;
import com.alibaba.mtl.log.sign.IRequestAuth;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: UTDC.java */
/* loaded from: classes.dex */
public class a {

    /* renamed from: a  reason: collision with other field name */
    private static boolean f51a = false;
    public static long b = -1;
    private static Context mContext = null;
    public static boolean o = false;
    public static boolean p = false;
    public static int s = 10000;
    public static int t;
    private static boolean q = true;
    public static String B = String.valueOf(System.currentTimeMillis());
    public static final AtomicInteger d = new AtomicInteger(0);
    public static boolean r = true;
    public static IRequestAuth a = null;

    public static String d() {
        return "";
    }

    public static String e() {
        return "";
    }

    public static synchronized void a(Context context) {
        synchronized (a.class) {
            if (context == null) {
                i.a("UTDC", "UTDC init failed ,context:" + context);
                return;
            }
            if (!f51a) {
                f51a = true;
                mContext = context.getApplicationContext();
                com.alibaba.mtl.log.d.a.a().start();
            }
        }
    }

    public static void a(IRequestAuth iRequestAuth) {
        a = iRequestAuth;
        if (iRequestAuth != null) {
            com.alibaba.mtl.log.e.b.o(iRequestAuth.getAppkey());
        }
    }

    public static void setChannel(String str) {
        com.alibaba.mtl.log.e.b.n(str);
    }

    public static void k() {
        i.a("UTDC", "[onBackground]");
        o = true;
        com.alibaba.mtl.log.b.a.D();
    }

    public static void l() {
        i.a("UTDC", "[onForeground]");
        o = false;
        com.alibaba.mtl.log.d.a.a().start();
    }

    public static void a(String str, String str2, String str3, String str4, String str5, Map<String, String> map) {
        if (mContext == null) {
            i.a("UTDC", "please call UTDC.init(context) before commit log,and this log will be discarded");
        } else if (a == null) {
            i.a("UTDC", "please call UTDC.setRequestAuthentication(auth) before commit log,and this log will be discarded");
        } else {
            i.a("UTDC", "[commit] page:", str, "eventId:", str2, "arg1:", str3, "arg2:", str4, "arg3:", str5, "args:", map);
            com.alibaba.mtl.log.b.a.l(str2);
            com.alibaba.mtl.log.c.c.a().a(new com.alibaba.mtl.log.model.a(str, str2, str3, str4, str5, map));
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public static IRequestAuth a() {
        IRequestAuth iRequestAuth = a;
        if (iRequestAuth == null || TextUtils.isEmpty(iRequestAuth.getAppkey())) {
            if (i.l()) {
                throw new RuntimeException("please Set <meta-data android:value=\"YOU KEY\" android:name=\"com.alibaba.apmplus.app_key\"></meta-data> in app AndroidManifest.xml ");
            }
            Log.w("UTDC", "please Set <meta-data android:value=\"YOU KEY\" android:name=\"com.alibaba.apmplus.app_key\"></meta-data> in app AndroidManifest.xml ");
        }
        return a;
    }

    public static String b() {
        try {
            return l.getNetworkState(getContext())[0];
        } catch (Exception unused) {
            return "Unknown";
        }
    }

    public static String c() {
        try {
            String[] networkState = l.getNetworkState(getContext());
            return networkState[0].equals("2G/3G") ? networkState[1] : "Unknown";
        } catch (Exception unused) {
            return "Unknown";
        }
    }

    public static void m() {
        com.alibaba.mtl.log.d.a.a().start();
    }
}
