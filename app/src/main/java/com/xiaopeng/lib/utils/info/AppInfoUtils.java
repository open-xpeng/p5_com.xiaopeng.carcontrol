package com.xiaopeng.lib.utils.info;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Process;
import com.xiaopeng.lib.utils.FileUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/* loaded from: classes2.dex */
public class AppInfoUtils {
    private static String sProcessName;

    public static PackageInfo getPackageInfo(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 128);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ApplicationInfo getApplicationInfo(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        try {
            if (context.getPackageName().equals(str)) {
                return context.getApplicationInfo();
            }
            return packageManager.getApplicationInfo(str, 128);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getVersionName(Context context, String str) {
        PackageInfo packageInfo = getPackageInfo(context, str);
        return packageInfo == null ? "" : packageInfo.versionName;
    }

    public static int getVersionCode(Context context, String str) {
        PackageInfo packageInfo = getPackageInfo(context, str);
        if (packageInfo == null) {
            return -1;
        }
        return packageInfo.versionCode;
    }

    public static Drawable getApplicationIcon(Context context, String str) {
        try {
            return context.getPackageManager().getApplicationIcon(str);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CharSequence getApplicationLabel(Context context, String str) {
        ApplicationInfo applicationInfo = getApplicationInfo(context, str);
        return applicationInfo == null ? "" : applicationInfo.loadLabel(context.getPackageManager());
    }

    public static String getProcessName() {
        BufferedReader bufferedReader;
        Throwable th;
        Exception e;
        String str = sProcessName;
        if (str != null) {
            return str;
        }
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader(new File("/proc/" + Process.myPid() + "/cmdline")));
            } catch (Exception e2) {
                bufferedReader = null;
                e = e2;
            } catch (Throwable th2) {
                bufferedReader = null;
                th = th2;
                FileUtils.closeQuietly(bufferedReader);
                throw th;
            }
            try {
                sProcessName = bufferedReader.readLine().trim();
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                sProcessName = "";
                FileUtils.closeQuietly(bufferedReader);
                return sProcessName;
            }
            FileUtils.closeQuietly(bufferedReader);
            return sProcessName;
        } catch (Throwable th3) {
            th = th3;
            FileUtils.closeQuietly(bufferedReader);
            throw th;
        }
    }
}
