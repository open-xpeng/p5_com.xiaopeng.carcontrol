package com.xiaopeng.lib.bughunter.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemProperties;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import java.text.SimpleDateFormat;
import java.util.List;

/* loaded from: classes2.dex */
public class BugHunterUtils {
    private static final String APPROVED_DEVICE_INFO = "XiaoPeng";

    public static String getSystemVersion() {
        return SystemProperties.get("ro.product.firmware", "unknown");
    }

    public static String getDevice() {
        return "xp/" + SystemProperties.get("persist.sys.hardware_id");
    }

    public static String getSoftwareId() {
        return SystemProperties.get("ro.xiaopeng.software");
    }

    public static String getMCUVer() {
        return SystemProperties.get("sys.mcu.version");
    }

    public static long getUid() {
        return SystemProperties.getLong("persist.sys.account_uid", -1L);
    }

    public static String getVersionName(Context context) {
        return getPackageInfo(context, null).versionName;
    }

    public static String getVersionName(Context context, String str) {
        return getPackageInfo(context, str).versionName;
    }

    public static int getVersionCode(Context context) {
        return getPackageInfo(context, null).versionCode;
    }

    public static int getVersionCode(Context context, String str) {
        return getPackageInfo(context, str).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (str == null) {
                str = context.getPackageName();
            }
            return packageManager.getPackageInfo(str, 16384);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatTime(long j) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(j));
    }

    public static String getNetworkType(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.getType() != 0) {
                return activeNetworkInfo.getType() == 1 ? "WIFI" : "";
            }
            switch (activeNetworkInfo.getSubtype()) {
                case 1:
                case 2:
                case 4:
                case 7:
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
                case 11:
                default:
                    return "";
                case 13:
                    return "4G";
            }
        }
        return "";
    }

    public boolean isApplicationInBackground(Context context) {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY)).getRunningTasks(1);
        return (runningTasks == null || runningTasks.isEmpty() || runningTasks.get(0).topActivity.getPackageName().equals(context.getPackageName())) ? false : true;
    }

    public static String getVin() {
        return SystemProperties.get("sys.xiaopeng.vin", "");
    }

    public static String getVid() {
        return SystemProperties.get("persist.sys.vehicle_id", "");
    }
}
