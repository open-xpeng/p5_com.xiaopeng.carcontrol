package com.irdeto.securesdk;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/* loaded from: classes.dex */
public class appInfo extends Activity {
    private static final String TAG = "securesdk";

    public static String getAppVer(Object obj) {
        PackageInfo packageInfo;
        Context context = (Context) obj;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null || (packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0)) == null) {
                return "";
            }
            String str = packageInfo.versionName;
            return str != null ? str.length() <= 0 ? "plaintext error" : str : "plaintext error";
        } catch (Exception e) {
            e.printStackTrace();
            return "plaintext error catch";
        }
    }

    public static String getPackageName(Object obj) {
        return ((Context) obj).getPackageName();
    }
}
