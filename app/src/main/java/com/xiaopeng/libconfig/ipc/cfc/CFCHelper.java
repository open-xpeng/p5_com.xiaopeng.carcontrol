package com.xiaopeng.libconfig.ipc.cfc;

import android.os.SystemProperties;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public class CFCHelper {
    private static final String SIGNAL_CFC_SPLIT = ",";
    private static final String SYS_PROPERTY_CFC = "persist.sys.xiaopeng.cfc_info";

    private static int getCFC(int i) {
        String[] split;
        String str = SystemProperties.get(SYS_PROPERTY_CFC);
        if (TextUtils.isEmpty(str) || (split = str.split(",")) == null) {
            return 0;
        }
        if (i < split.length) {
            try {
            } catch (NumberFormatException unused) {
                return 0;
            }
        }
        return Integer.parseInt(split[i]);
    }

    public static int getVehicleType() {
        return getCFC(0);
    }

    public static int getProductAddr() {
        return getCFC(1);
    }

    public static int getInterior() {
        return getCFC(2);
    }

    public static int getBodyColor() {
        int cfc = getCFC(3);
        if (cfc == 2 || cfc == 4 || cfc == 5 || cfc == 7 || cfc == 12 || cfc == 13 || cfc == 10 || cfc == 9 || cfc == 8) {
            return cfc;
        }
        return 1;
    }

    public static int getProductStage() {
        return getCFC(4);
    }

    public static int getConfigCode() {
        return getCFC(5);
    }

    public static int getSSBState() {
        return getCFC(13);
    }
}
