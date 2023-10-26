package com.xiaopeng.speech.apirouter;

import android.content.pm.PackageInfo;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.xiaopeng.speech.vui.constants.Foo;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class Utils {
    private static Boolean isXpDevice;
    private static Map<String, Boolean> packageMap = new ArrayMap();

    public static boolean isCorrectObserver(String str, String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str) || !str2.contains(".")) {
            return false;
        }
        return str2.equals(new StringBuilder().append(str).append(str2.substring(str2.lastIndexOf("."))).toString());
    }

    public static boolean isXpDevice() {
        if (isXpDevice == null) {
            if (TextUtils.isEmpty(VuiUtils.getXpCduType())) {
                isXpDevice = false;
            } else {
                isXpDevice = true;
            }
        }
        return isXpDevice.booleanValue();
    }

    public static boolean checkApkExist(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (packageMap.containsKey(str)) {
            return packageMap.get(str).booleanValue();
        }
        List<PackageInfo> installedPackages = Foo.getContext().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < installedPackages.size(); i++) {
            if (installedPackages.get(i).packageName.equalsIgnoreCase(str)) {
                LogUtils.i("Utils", "system contain " + str);
                packageMap.put(str, true);
                return true;
            }
        }
        LogUtils.i("Utils", "system do not contain " + str);
        packageMap.put(str, false);
        return false;
    }
}
