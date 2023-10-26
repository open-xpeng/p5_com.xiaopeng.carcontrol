package com.xiaopeng.carcontrol.config;

import android.car.Car;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;

/* loaded from: classes.dex */
public class D21CarConfig extends DxCarConfig {
    public static final int PROPERTY_BODY_COLOR_BLACK = 3;
    public static final int PROPERTY_BODY_COLOR_BLUE = 9;
    public static final int PROPERTY_BODY_COLOR_BLUE_BLACK = 7;
    public static final int PROPERTY_BODY_COLOR_CHAMPAGNE = 11;
    public static final int PROPERTY_BODY_COLOR_COFFEE = 6;
    public static final int PROPERTY_BODY_COLOR_FUGUANG_GREEN = 17;
    public static final int PROPERTY_BODY_COLOR_GRAY = 4;
    public static final int PROPERTY_BODY_COLOR_INVALID = 0;
    public static final int PROPERTY_BODY_COLOR_JINGMI_BLACK = 14;
    public static final int PROPERTY_BODY_COLOR_QINGYUE_BLUE = 15;
    public static final int PROPERTY_BODY_COLOR_QINGYUE_BLUE_BLACK = 16;
    public static final int PROPERTY_BODY_COLOR_RED = 10;
    public static final int PROPERTY_BODY_COLOR_RED_BLACK = 1;
    public static final int PROPERTY_BODY_COLOR_SILVER = 8;
    public static final int PROPERTY_BODY_COLOR_SILVER_BLACK = 5;
    public static final int PROPERTY_BODY_COLOR_WHITE = 2;
    public static final int PROPERTY_BODY_COLOR_XINGJI_GRAY = 12;
    public static final int PROPERTY_BODY_COLOR_YINGHUO_BLUE = 13;

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public float getHighSpdFuncThreshold() {
        return 80.0f;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int getHvacVersion() {
        return 1;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isNewAvasArch() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isNewBcmArch() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isNewIcmArch() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isNewMsmArch() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isNewScuArch() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAtl() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoCloseWin() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportBootSoundEffect() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportBossKey() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCwc() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDhc() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDoorKeySetting() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDriveEnergyReset() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvSeatStop() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEbw() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportElcTrunk() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEsbForLdw() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportFullAtl() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIhb() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportInductionLock() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIsla() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIslcInActive() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLka() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLlu() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMemPark() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMeterSetting() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMirrorHeat() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMirrorPositionSignalSet() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMsmD() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMsmP() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMsmPVerticalMove() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPM25Out() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatControl() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatStop() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatVent() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRcw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSeatCtrl() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSeatMassage() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSeatSmartControl() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportShowDriveAutoLock() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportShowEbw() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSrs() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSunShade() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSwitchMedia() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportVipSeat() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWindowPos() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWiperRepair() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPedal() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXpuNedc() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int matchSeatSetPos(int pos) {
        return pos;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public D21CarConfig() {
        sIsD2xSeries = true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig
    public boolean isLowConfig() {
        return getCfcCode() == 1;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig
    public boolean isMiddleConfig() {
        return getCfcCode() == 2;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig
    public boolean isHighConfig() {
        return getCfcCode() == 3;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMaintenanceInfo() {
        String xpCduType = Car.getXpCduType();
        return VuiUtils.CAR_PLATFORM_Q2.equals(xpCduType) || VuiUtils.CAR_PLATFORM_Q5.equals(xpCduType) || VuiUtils.CAR_PLATFORM_Q6.equals(xpCduType);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEsb() {
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMirrorDown() {
        String carCduType = getCarCduType();
        return getCfcCode() == 3 && (VuiUtils.CAR_PLATFORM_Q2.equals(carCduType) || (VuiUtils.CAR_PLATFORM_Q5.equals(carCduType) && !"D20".equals(CarStatusUtils.getHardwareCarType())));
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoPark() {
        int cfcCode = getCfcCode();
        return cfcCode == 3 || cfcCode == 2;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int getCduMirrorReverseMode() {
        return isSupportMirrorDown() ? 3 : 0;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvSeatVent() {
        if (VuiUtils.CAR_PLATFORM_Q5.equals(getCarCduType()) && CarStatusUtils.isD20CarType() && !CarStatusUtils.isCarStageP()) {
            return !isLowConfig();
        }
        return isHighConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvSeatHeat() {
        if (VuiUtils.CAR_PLATFORM_Q5.equals(getCarCduType()) && CarStatusUtils.isD20CarType() && !CarStatusUtils.isCarStageP()) {
            return isHighConfig();
        }
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatHeat() {
        if (VuiUtils.CAR_PLATFORM_Q5.equals(getCarCduType()) && CarStatusUtils.isD20CarType() && !CarStatusUtils.isCarStageP()) {
            return false;
        }
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportActiveSafety() {
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportBsd() {
        return isSupportActiveSafety();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDow() {
        if ("D20".equals(CarStatusUtils.getHardwareCarType())) {
            return false;
        }
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRcta() {
        return isSupportActiveSafety();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIslc() {
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportShowAutoPark() {
        return isSupportAutoPark();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoPilot() {
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLcc() {
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAlc() {
        return !isLowConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMainWiperInterval() {
        return !isSupportCiuConfig();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int[] getMSMSeatDefaultPos() {
        return MSM_SEAT_DEFAULT_POS_D21;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTopCamera() {
        return isHighConfig();
    }
}
