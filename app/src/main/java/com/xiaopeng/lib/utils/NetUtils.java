package com.xiaopeng.lib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/* loaded from: classes2.dex */
public class NetUtils {
    private static final String GOBINET_PROPERTY_KEY = "persist.sys.ril.gobinet";
    private static final String GOBINET_STATE_FILE = "/sys/class/net/xpusb0/operstate";
    private static final String GOBINET_VALUE_ON = "on";
    public static final int NETWORK_TYPE_2G = 1;
    public static final int NETWORK_TYPE_3G = 2;
    public static final int NETWORK_TYPE_4G = 3;
    public static final int NETWORK_TYPE_NONE = 0;
    public static final int NETWORK_TYPE_WIFI = 10;
    public static final String TRAFFIC_STATUS_CHAGNE_ACTION = "com.xiaopeng.action.TRAFFIC_STATUS_CHANGE";
    public static final int TRAFFIC_STATUS_TYPE_APN_ERROR = 2;
    public static final int TRAFFIC_STATUS_TYPE_AVAILABLE = 3;
    public static final String TRAFFIC_STATUS_TYPE_KEY = "persist.sys.xp.4g.st";
    public static final int TRAFFIC_STATUS_TYPE_RUNOUT = 1;

    public static boolean checkNetState(Context context) {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || (allNetworkInfo = connectivityManager.getAllNetworkInfo()) == null) {
            return false;
        }
        for (NetworkInfo networkInfo : allNetworkInfo) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWifiEnabled(Context context) {
        NetworkInfo networkInfo;
        Objects.requireNonNull(context, "Global context is null");
        if (((WifiManager) context.getSystemService("wifi")).getWifiState() != 3 || (networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1)) == null) {
            return false;
        }
        return networkInfo.isConnected();
    }

    public static boolean isMobileNetwork(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 0;
    }

    public static int getNetworkType(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context != null && (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) != null) {
            if (activeNetworkInfo.getType() == 0) {
                switch (activeNetworkInfo.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                        return 1;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        return 2;
                    case 13:
                        return 3;
                }
            } else if (activeNetworkInfo.getType() == 1) {
                return 10;
            }
        }
        return 0;
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
    }

    public static String getMacAddress(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        return connectionInfo == null ? "" : connectionInfo.getMacAddress();
    }

    public static String getIMEI(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
    }

    public static String getNetworkOperatorName(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getNetworkOperatorName();
    }

    public static int getSimState(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getSimState();
    }

    public static boolean isTrafficRunOut() {
        return getTrafficStatus() == 1;
    }

    public static int getTrafficStatus() {
        return SystemProperties.getInt(TRAFFIC_STATUS_TYPE_KEY, 3);
    }

    public static boolean isSystemApnReady() {
        boolean z = true;
        if (!GOBINET_VALUE_ON.equals(SystemProperties.get(GOBINET_PROPERTY_KEY))) {
            return true;
        }
        File file = new File(GOBINET_STATE_FILE);
        if (!file.exists()) {
            return true;
        }
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
                try {
                    String readLine = bufferedReader2.readLine();
                    if (!"up".equals(readLine)) {
                        if (!"unknown".equals(readLine)) {
                            z = false;
                        }
                    }
                    FileUtils.closeQuietly(bufferedReader2);
                    return z;
                } catch (IOException e) {
                    e = e;
                    bufferedReader = bufferedReader2;
                    e.printStackTrace();
                    FileUtils.closeQuietly(bufferedReader);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    FileUtils.closeQuietly(bufferedReader);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
