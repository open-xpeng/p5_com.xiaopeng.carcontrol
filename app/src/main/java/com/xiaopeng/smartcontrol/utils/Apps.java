package com.xiaopeng.smartcontrol.utils;

import android.content.Context;
import com.xiaopeng.smartcontrol.utils.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class Apps {
    private static final HashMap<String, String> msMap;

    static {
        HashMap<String, String> hashMap = new HashMap<>();
        msMap = hashMap;
        hashMap.put("carcontrol", "com.xiaopeng.carcontrol");
        hashMap.put(Constants.APPID.SETTINGS, "com.xiaopeng.car.settings");
        hashMap.put(Constants.APPID.POWERCENTER, "com.xiaopeng.chargecontrol");
        hashMap.put("hvac", "com.xiaopeng.carhvac");
    }

    public static Set<String> getApps() {
        return msMap.keySet();
    }

    public static String getPackageNames(String str) {
        return msMap.get(str);
    }

    public static String getAppId(Context context) {
        return getAppId(context.getPackageName());
    }

    private static String getAppId(String str) {
        for (Map.Entry<String, String> entry : msMap.entrySet()) {
            if (entry.getValue().equals(str)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
