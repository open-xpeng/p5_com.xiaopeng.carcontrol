package com.xiaopeng.carcontrol.util;

import android.car.Car;
import android.os.SystemProperties;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.xvs.xid.BuildConfig;

/* loaded from: classes2.dex */
public class CarStatusUtils {
    private static String CAR_TYPE_D21V = "D21V";
    private static String CAR_TYPE_D22V = "D22V";
    private static String CAR_TYPE_D55V = "D55V";
    private static String CAR_TYPE_E28V = "E28V";
    private static final String TAG = "CarVersionUtil";
    private static Boolean mIsD20;
    private static Boolean mIsD21V;
    private static Boolean mIsD22;
    private static Boolean mIsD22V;
    private static Boolean mIsD55;
    private static Boolean mIsD55V;
    private static Boolean mIsDSeries;
    private static Boolean mIsDV;
    private static Boolean mIsE28;
    private static Boolean mIsE28A;
    private static Boolean mIsE28V;
    private static Boolean mIsEU;
    private static Boolean mIsStageP;
    private static Boolean mIsStageX;

    public static String getHardwareCarType() {
        Exception e;
        try {
            String hardwareCarType = Car.getHardwareCarType();
            try {
                return ("".equals(hardwareCarType) || hardwareCarType == null || hardwareCarType.length() <= 3) ? hardwareCarType : hardwareCarType.substring(0, 3);
            } catch (Exception e2) {
                e = e2;
                LogUtils.e(TAG, "can not get HardwareCarType error = " + e);
                return "";
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public static String getProductModel() {
        return SystemProperties.get("ro.product.model", "");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String getNewHardwareCarType() {
        char c;
        String xpCduType = Car.getXpCduType();
        switch (xpCduType.hashCode()) {
            case 2560:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q1)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 2561:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q2)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2562:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q3)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 2563:
            default:
                c = 65535;
                break;
            case 2564:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q5)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 2565:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q6)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 2566:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q7)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 2567:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q8)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 2568:
                if (xpCduType.equals("Q9")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
        }
        return c != 0 ? c != 1 ? c != 2 ? c != 3 ? c != 4 ? c != 5 ? BuildConfig.LIB_PRODUCT : "F30" : "E38" : "D22" : "D20" : "D55" : "D21";
    }

    public static String getCarStageVersion() {
        try {
            return Car.getHardwareCarStage();
        } catch (Exception e) {
            LogUtils.e(TAG, "can not get CarStageVersion error = " + e);
            return "";
        }
    }

    public static boolean isE28CarType() {
        if (mIsE28 == null) {
            mIsE28 = Boolean.valueOf(BuildConfig.LIB_PRODUCT.equals(getHardwareCarType()));
        }
        return mIsE28.booleanValue();
    }

    public static boolean isD55CarType() {
        if (mIsD55 == null) {
            mIsD55 = Boolean.valueOf("D55".equals(getHardwareCarType()));
        }
        return mIsD55.booleanValue();
    }

    public static boolean isD20CarType() {
        if (mIsD20 == null) {
            mIsD20 = Boolean.valueOf("D20".equals(getHardwareCarType()));
        }
        return mIsD20.booleanValue();
    }

    public static boolean isD22CarType() {
        if (mIsD22 == null) {
            mIsD22 = Boolean.valueOf("D22".equals(getHardwareCarType()));
        }
        return mIsD22.booleanValue();
    }

    public static boolean isCarStageP() {
        if (mIsStageP == null) {
            mIsStageP = Boolean.valueOf("P".equals(getCarStageVersion()));
        }
        return mIsStageP.booleanValue();
    }

    public static boolean isCarStageX() {
        if (mIsStageX == null) {
            mIsStageX = Boolean.valueOf("X".equals(getCarStageVersion()));
        }
        return mIsStageX.booleanValue();
    }

    public static String getXpCduType() {
        try {
            return Car.getXpCduType();
        } catch (Exception e) {
            LogUtils.e(TAG, "can not get XpCduType error = " + e);
            return "";
        }
    }

    public static String getRegion() {
        try {
            String str = SystemProperties.get("ro.xiaopeng.software", "");
            return (str == null || str.isEmpty()) ? str : str.substring(7, 9);
        } catch (Exception e) {
            LogUtils.e(TAG, "can not get getRegion error = " + e);
            return "";
        }
    }

    public static boolean isEURegion() {
        if (mIsEU == null) {
            mIsEU = Boolean.valueOf("EU".equals(getRegion()));
        }
        return mIsEU.booleanValue();
    }

    public static boolean isD21VCarType() {
        if (mIsD21V == null) {
            mIsD21V = Boolean.valueOf("D21".equals(getHardwareCarType()) && isEURegion());
        }
        return mIsD21V.booleanValue();
    }

    public static boolean isD22VCarType() {
        if (mIsD22V == null) {
            mIsD22V = Boolean.valueOf("D22".equals(getHardwareCarType()) && isEURegion());
        }
        return mIsD22V.booleanValue();
    }

    public static boolean isD55VCarType() {
        if (mIsD55V == null) {
            mIsD55V = Boolean.valueOf("D55".equals(getHardwareCarType()) && isEURegion());
        }
        return mIsD55V.booleanValue();
    }

    public static boolean isDCarType() {
        if (mIsDSeries == null) {
            mIsDSeries = Boolean.valueOf("D21".equals(getHardwareCarType()) || isD22CarType() || isD55CarType());
        }
        return mIsDSeries.booleanValue();
    }

    public static boolean isDVCarType() {
        if (mIsDV == null) {
            mIsDV = Boolean.valueOf(isD55VCarType() || isD21VCarType() || isD22VCarType());
        }
        return mIsDV.booleanValue();
    }

    public static boolean isE28VCarType() {
        if (mIsE28V == null) {
            mIsE28V = Boolean.valueOf(BuildConfig.LIB_PRODUCT.equals(getHardwareCarType()) && isEURegion());
        }
        return mIsE28V.booleanValue();
    }

    public static boolean isE28ACarType() {
        if (mIsE28A == null) {
            mIsE28A = Boolean.valueOf(VuiUtils.CAR_PLATFORM_Q8.equals(Car.getXpCduType()));
        }
        return mIsE28A.booleanValue();
    }
}
