package com.xiaopeng.lib.utils.info;

import android.os.SystemProperties;
import android.util.Log;

/* loaded from: classes2.dex */
public class DeviceInfoUtils {
    public static final int POWER_STATUS_FAKESLEEP = 1;
    public static final int POWER_STATUS_NORMAL = 0;
    public static final int POWER_STATUS_SLEEP = 2;
    public static final int POWER_STATUS_UNKNOWN = -1;
    private static final String PROPERTY_POWER_STATUS = "sys.power.xp_power_status";
    private static final String PROPERTY_PRODUCT_MODEL = "ro.product.model";

    public static int getPowerStatus() {
        return SystemProperties.getInt(PROPERTY_POWER_STATUS, -1);
    }

    public static String getProductModel() {
        return SystemProperties.get(PROPERTY_PRODUCT_MODEL, "");
    }

    public static boolean isInternationalVer() {
        boolean z = true;
        try {
            z = true ^ getVersionInCountryCode().contains("ZH");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("netChannel", "isInternationVersion :\t" + z);
        return z;
    }

    public static String getVersionInCountryCode() {
        String str = SystemProperties.get("ro.xiaopeng.software", "");
        return ("".equals(str) || str == null) ? str : str.substring(7, 9);
    }
}
