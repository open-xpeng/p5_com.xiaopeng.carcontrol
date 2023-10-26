package com.alibaba.sdk.android.httpdns.c;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/* loaded from: classes.dex */
public class a {
    private int h;
    private String m;
    private String n;

    /* renamed from: com.alibaba.sdk.android.httpdns.c.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    private static final class C0011a {
        private static final a b = new a();
    }

    private a() {
        this.h = 0;
        this.m = "UNKNOWN";
        this.n = "UNKNOWN";
    }

    private int a(Context context) {
        ConnectivityManager connectivityManager;
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        } catch (Exception unused) {
        }
        if (connectivityManager == null) {
            return 0;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == 1) {
                return 1;
            }
            if (activeNetworkInfo.getType() == 0) {
                switch (activeNetworkInfo.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return 2;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 15:
                        return 3;
                    case 12:
                    case 14:
                    default:
                        return 0;
                    case 13:
                        return 4;
                }
            }
            return 0;
        }
        return 255;
    }

    public static a a() {
        return C0011a.b;
    }

    /* renamed from: a  reason: collision with other method in class */
    private String m37a(Context context) {
        try {
            String simOperator = ((TelephonyManager) context.getSystemService("phone")).getSimOperator();
            if (!TextUtils.isEmpty(simOperator)) {
                return simOperator;
            }
        } catch (Throwable unused) {
        }
        return String.valueOf(0);
    }

    private String b(Context context) {
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
            if (connectionInfo != null) {
                return connectionInfo.getSSID();
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    private String c(Context context) {
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
            if (connectionInfo != null) {
                return connectionInfo.getBSSID();
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* renamed from: c  reason: collision with other method in class */
    private void m38c(Context context) {
        int a = a(context);
        this.h = a;
        if (a == 1) {
            this.m = b(context);
            this.n = c(context);
        } else if (a == 2 || a == 3 || a == 4) {
            this.m = m37a(context);
        }
        if (TextUtils.isEmpty(this.m)) {
            this.m = "UNKNOWN";
        }
    }

    /* renamed from: b  reason: collision with other method in class */
    public void m39b(Context context) {
        m38c(context);
    }

    public String g() {
        return this.m;
    }

    /* renamed from: g  reason: collision with other method in class */
    public boolean m40g() {
        return this.h == 1;
    }

    public int getNetworkType() {
        return this.h;
    }

    public String h() {
        return this.n;
    }
}
