package com.xiaopeng.speech.protocol.utils;

import android.os.SystemProperties;
import com.xiaopeng.speech.common.util.LogUtils;

/* loaded from: classes2.dex */
public class CarTypeUtils {
    private static final String CAR_TYPE_D21EU = "D21EU";
    private static final String CAR_TYPE_D22EU = "D22EU";
    private static final String CAR_TYPE_D55EU = "D55EU";
    private static final String CAR_TYPE_D55ZH = "D55ZH";
    private static final String CAR_TYPE_E28EU = "E28EU";
    private static final String CAR_TYPE_E38EU = "E38EU";
    private static final String TAG = "CarTypeUtils";

    public static boolean isOverseasCarType() {
        String str = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isOverseasCarType, carType: " + str);
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case 64338291:
                if (str.equals(CAR_TYPE_D21EU)) {
                    c = 0;
                    break;
                }
                break;
            case 64339252:
                if (str.equals(CAR_TYPE_D22EU)) {
                    c = 1;
                    break;
                }
                break;
            case 64431508:
                if (str.equals(CAR_TYPE_D55EU)) {
                    c = 2;
                    break;
                }
                break;
            case 65268539:
                if (str.equals(CAR_TYPE_E28EU)) {
                    c = 3;
                    break;
                }
                break;
            case 65298330:
                if (str.equals(CAR_TYPE_E38EU)) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }

    public static boolean isD21EU() {
        String str = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isD21EU, carType: " + str);
        return CAR_TYPE_D21EU.equalsIgnoreCase(str);
    }

    public static boolean isD22EU() {
        String str = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isD22EU, carType: " + str);
        return CAR_TYPE_D22EU.equalsIgnoreCase(str);
    }

    public static boolean isD55EU() {
        String str = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isD55EU, carType: " + str);
        return CAR_TYPE_D55EU.equalsIgnoreCase(str);
    }

    public static boolean isE28EU() {
        String str = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isE28V, carType: " + str);
        return CAR_TYPE_E28EU.equalsIgnoreCase(str);
    }

    public static boolean isE38EU() {
        String str = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isE38V, carType: " + str);
        return CAR_TYPE_E38EU.equalsIgnoreCase(str);
    }

    public static boolean isD55ZH() {
        String str = getHardwareCarType() + getVersionInCountryCode();
        LogUtils.d(TAG, "isD55ZH, carType: " + str);
        return CAR_TYPE_D55ZH.equalsIgnoreCase(str);
    }

    private static String getVersionInCountryCode() {
        String str = SystemProperties.get("ro.xiaopeng.software", "");
        return ("".equals(str) || str == null) ? str : str.substring(7, 9);
    }

    private static String getHardwareCarType() {
        String str = SystemProperties.get("ro.xiaopeng.software", "");
        return ("".equals(str) || str == null) ? str : str.substring(9, 12);
    }
}
