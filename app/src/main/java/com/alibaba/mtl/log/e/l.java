package com.alibaba.mtl.log.e;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

/* compiled from: NetworkUtil.java */
/* loaded from: classes.dex */
public class l {

    /* renamed from: a  reason: collision with other field name */
    private static String[] f64a = {"Unknown", "Unknown"};

    /* renamed from: a  reason: collision with other field name */
    private static b f63a = new b();
    private static a a = new a();

    private static String a(int i) {
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return "2G";
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return "3G";
            case 13:
                return "4G";
            default:
                return "Unknown";
        }
    }

    public static String w() {
        NetworkInfo activeNetworkInfo;
        Context context = com.alibaba.mtl.log.a.getContext();
        if (context == null) {
            return "Unknown";
        }
        try {
            if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) == 0 && (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) != null && activeNetworkInfo.isConnected()) {
                if (activeNetworkInfo.getType() == 1) {
                    return "wifi";
                }
                if (activeNetworkInfo.getType() == 0) {
                    return a(activeNetworkInfo.getSubtype());
                }
            }
        } catch (Throwable unused) {
        }
        return "Unknown";
    }

    public static boolean isConnected() {
        Context context = com.alibaba.mtl.log.a.getContext();
        if (context != null) {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null) {
                        return activeNetworkInfo.isConnected();
                    }
                    return false;
                }
                return true;
            } catch (Exception unused) {
                return true;
            }
        }
        return true;
    }

    public static String[] getNetworkState(Context context) {
        return f64a;
    }

    public static String getWifiAddress(Context context) {
        WifiInfo connectionInfo;
        if (context == null || (connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo()) == null) {
            return "00:00:00:00:00:00";
        }
        String macAddress = connectionInfo.getMacAddress();
        return TextUtils.isEmpty(macAddress) ? "00:00:00:00:00:00" : macAddress;
    }

    public static void b(Context context) {
        if (context == null) {
            return;
        }
        context.registerReceiver(f63a, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public static void c(Context context) {
        b bVar;
        if (context == null || (bVar = f63a) == null) {
            return;
        }
        context.unregisterReceiver(bVar);
    }

    /* compiled from: NetworkUtil.java */
    /* loaded from: classes.dex */
    private static class b extends BroadcastReceiver {
        private b() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            r.a().b(l.a.a(context));
        }
    }

    /* compiled from: NetworkUtil.java */
    /* loaded from: classes.dex */
    private static class a implements Runnable {
        private Context a;

        private a() {
        }

        public a a(Context context) {
            this.a = context;
            return this;
        }

        @Override // java.lang.Runnable
        public void run() {
            Context context = this.a;
            if (context == null) {
                return;
            }
            try {
                if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", this.a.getPackageName()) != 0) {
                    l.f64a[0] = "Unknown";
                    return;
                }
                ConnectivityManager connectivityManager = (ConnectivityManager) this.a.getSystemService("connectivity");
                if (connectivityManager == null) {
                    l.f64a[0] = "Unknown";
                    return;
                }
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                    return;
                }
                if (1 == activeNetworkInfo.getType()) {
                    l.f64a[0] = "Wi-Fi";
                } else if (activeNetworkInfo.getType() == 0) {
                    l.f64a[0] = "2G/3G";
                    l.f64a[1] = activeNetworkInfo.getSubtypeName();
                }
            } catch (Exception unused) {
            }
        }
    }
}
