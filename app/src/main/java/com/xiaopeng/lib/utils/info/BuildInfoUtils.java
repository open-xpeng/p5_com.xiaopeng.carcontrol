package com.xiaopeng.lib.utils.info;

import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.xiaopeng.lib.utils.SystemPropertyUtil;

/* loaded from: classes2.dex */
public class BuildInfoUtils {
    public static final String BID_LAN = "4";
    public static final String BID_PT_SPECIAL_1 = "5";
    public static final String BID_PT_SPECIAL_2 = "6";
    public static final String BID_WAN = "1";
    public static final String UNKNOWN = "unknown";
    private static String mFullSoftwareVersion;

    public static String getSystemVersion() {
        String fullSystemVersion;
        int indexOf;
        int i;
        int indexOf2;
        String string = getString("ro.product.firmware");
        return (!"unknown".equals(string) || (indexOf = (fullSystemVersion = getFullSystemVersion()).indexOf("_")) <= 1 || (indexOf2 = fullSystemVersion.indexOf("_", (i = indexOf + 1))) <= indexOf) ? string : fullSystemVersion.substring(i, indexOf2);
    }

    public static String getIccid() {
        return SystemPropertyUtil.getIccid();
    }

    public static String getFullSystemVersion() {
        String str = mFullSoftwareVersion;
        if (str != null) {
            return str;
        }
        String string = getString("ro.xiaopeng.software");
        mFullSoftwareVersion = string;
        if ("unknown".equals(string)) {
            mFullSoftwareVersion = getString("ro.build.display.id");
        }
        return mFullSoftwareVersion;
    }

    public static String getHardwareVersion() {
        return getString("ro.xiaopeng.hardware");
    }

    public static int getHardwareVersionCode() {
        String hardwareVersion = getHardwareVersion();
        if ("unknown".equals(hardwareVersion) || hardwareVersion.length() < 5) {
            return 0;
        }
        try {
            return Integer.parseInt(hardwareVersion.substring(3, hardwareVersion.length()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getHardwareId() {
        return getString("persist.sys.mcu.hardwareId");
    }

    public static String getDeviceId() {
        return "xp/" + getHardwareId();
    }

    public static String getBid() {
        int indexOf;
        String fullSystemVersion = getFullSystemVersion();
        return (!TextUtils.isEmpty(fullSystemVersion) && (indexOf = fullSystemVersion.indexOf("_")) > 1) ? fullSystemVersion.substring(indexOf - 1, indexOf) : BID_LAN;
    }

    public static boolean isLanVersion() {
        return BID_LAN.equals(getBid());
    }

    public static boolean isPTSpecialVersion() {
        return BID_PT_SPECIAL_1.equals(getBid()) || BID_PT_SPECIAL_2.equals(getBid());
    }

    public static boolean isEngVersion() {
        return "eng".equals(Build.TYPE);
    }

    public static boolean isUserDebugVersion() {
        return "userdebug".equals(Build.TYPE);
    }

    public static boolean isDebuggableVersion() {
        return isEngVersion() || isUserDebugVersion();
    }

    public static boolean isUserVersion() {
        return "user".equals(Build.TYPE);
    }

    private static String getString(String str) {
        return SystemProperties.get(str, "unknown");
    }

    static boolean isDeviceApproved() {
        String string = getString("ro.product.manufacturer");
        return "XiaoPeng".equals(string) || "XPENG".equals(string);
    }

    public static void checkoutDeviceLegality() {
        if (isDeviceApproved()) {
            return;
        }
        System.exit(0);
    }
}
