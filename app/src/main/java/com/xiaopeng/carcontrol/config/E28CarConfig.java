package com.xiaopeng.carcontrol.config;

import android.os.SystemProperties;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes.dex */
public class E28CarConfig extends CarBaseConfig {
    public static final int PROPERTY_BODY_COLOR_BLACK = 3;
    public static final int PROPERTY_BODY_COLOR_FULL_BLACK = 14;
    public static final int PROPERTY_BODY_COLOR_GRAY = 4;
    public static final int PROPERTY_BODY_COLOR_GREEN = 10;
    private static final int PROPERTY_BODY_COLOR_INVALID = 0;
    public static final int PROPERTY_BODY_COLOR_LIGHT_RED = 12;
    public static final int PROPERTY_BODY_COLOR_RED = 1;
    public static final int PROPERTY_BODY_COLOR_SILVER = 5;
    public static final int PROPERTY_BODY_COLOR_WHITE = 2;
    public static final int PROPERTY_BODY_COLOR_WHITE_2 = 13;
    private static final String PROPERTY_CFC = "persist.sys.xiaopeng.cfcIndex";
    private static final String TAG = "CarConfigHelper";

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int getEpbAccPedalThreshold() {
        return 30;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int getHvacVersion() {
        return 2;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int getMeditationSeatPos() {
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isFollowVehicleLostConfigUseNew() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isNewIcmArch() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isNewMsmArch() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isNewScuArch() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAbsFault() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAdjMirrorByCdu() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAirSuspension() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoCloseWin() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoWindowLock() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAuxiliaryFunc() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAvasLoudSpeaker() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportBcmCtrlSeatBelt() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportBootSoundEffect() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCNgp() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCarCallAdvanced() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportChildLock() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCtrlBonnet() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCtrlChargePort() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDaytimeRunningLight() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDhc() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDigitalKeyTip() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDomeLight() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDoorKeySetting() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDriveEnergyReset() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrl() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvCushion() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvLumbar() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvSeatStop() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEbw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEnergyRecycleMediumLevel() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEpbSetting() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEpbWarning() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEsbForLdw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEspMudMode() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEspSportMode() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportHdc() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportInductionLock() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIslc() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIslcInActive() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportKeyPark() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportKeyParkAdvanced() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLightMeHomeNew() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLightMeHomeTime() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLka() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMainWiperInterval() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMeterSetting() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMirrorDown() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNeutralGearProtect() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNewNgpArch() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNewWiperSpdSt() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNormalDriveMode() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportOldIsla() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPM25Out() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportParkingLampOutput() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPollingLock() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPollingOpenCfg() {
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
    public boolean isSupportRearSeatHeat() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearWiper() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSeatMassage() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSeatSmartControl() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSensorTrunk() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportShowAutoPark() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportShowDriveAutoLock() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportShowEbw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSrs() {
        return true;
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
    public boolean isSupportTopCamera() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTrailerHook() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTrailerMode() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTrailerRv() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTrunkOverHeatingProtected() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTrunkSetPosition() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTurnActive() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportUnity3D() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportUserPortfolioPage() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportVcuExhibitionMode() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportVcuExhibitionNewVcuArch() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportVipSeat() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWheelKeyProtect() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWheelSetting() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWindowAutoCtrl() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWindowStates() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWiperInterval() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWiperRepair() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWiperSenCfg() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPedal() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPilotTtsCfg() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXSayHi() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXUnlockTrunk() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int matchSeatSetPos(int pos) {
        return pos;
    }

    public static int getCarBodyColor() {
        int i = SystemProperties.getInt("persist.sys.xiaopeng.bodyColor", 0);
        LogUtils.i(TAG, "Car body color: " + i, false);
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 10) {
            return i;
        }
        switch (i) {
            case 12:
            case 13:
            case 14:
                return i;
            default:
                return 5;
        }
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    int getCfcCode() {
        int parseInt;
        if (sCfcCode == null) {
            String str = SystemProperties.get(PROPERTY_CFC, (String) null);
            if (!TextUtils.isEmpty(str)) {
                try {
                    parseInt = Integer.parseInt(str);
                } catch (Exception unused) {
                }
                sCfcCode = Integer.valueOf(parseInt);
                LogUtils.i(TAG, "getCfcCode: " + sCfcCode, false);
            }
            parseInt = 0;
            sCfcCode = Integer.valueOf(parseInt);
            LogUtils.i(TAG, "getCfcCode: " + sCfcCode, false);
        }
        return sCfcCode.intValue();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAwd() {
        return hasFeature(CarBaseConfig.PROPERTY_IPUF);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSnowMode() {
        return isSupportAwd();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEsb() {
        return hasFeature(CarBaseConfig.PROPERTY_MSB);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoPark() {
        return (hasFeature(CarBaseConfig.PROPERTY_SCU) && hasFeature(CarBaseConfig.PROPERTY_AVM)) || hasFeature(CarBaseConfig.PROPERTY_XPU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSopStage() {
        Boolean bool = sFeatureSupport.get(CarBaseConfig.PROPERTY_PRODUCTION_STAGE);
        if (bool == null) {
            String str = SystemProperties.get(CarBaseConfig.PROPERTY_PRODUCTION_STAGE, "");
            LogUtils.i(TAG, "Read prop: persist.sys.xiaopeng.production_stage value: " + str, false);
            bool = Boolean.valueOf("S".equals(str));
            sFeatureSupport.put(CarBaseConfig.PROPERTY_PRODUCTION_STAGE, bool);
        }
        return bool.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int getCduMirrorReverseMode() {
        return hasFeature(CarBaseConfig.PROPERTY_MIRROR) ? 1 : 0;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMirrorFold() {
        return hasFeature(CarBaseConfig.PROPERTY_MIRROR);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMirrorHeat() {
        return hasFeature(CarBaseConfig.PROPERTY_MIRROR);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCwc() {
        return hasFeature(CarBaseConfig.PROPERTY_CWC);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSeatCtrl() {
        return hasFeature(CarBaseConfig.PROPERTY_MSMD);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatControl() {
        return hasFeature(CarBaseConfig.PROPERTY_MSMP);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMsmD() {
        return hasFeature(CarBaseConfig.PROPERTY_MSMD);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMsmP() {
        return hasFeature(CarBaseConfig.PROPERTY_MSMP);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMsmPVerticalMove() {
        return isSupportMsmP();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int[] getMSMSeatDefaultPos() {
        return MSM_SEAT_DEFAULT_POS;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAtl() {
        return hasFeature(CarBaseConfig.PROPERTY_ATLS);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportFullAtl() {
        return hasFeature(CarBaseConfig.PROPERTY_ATLS);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLlu() {
        return hasFeature(CarBaseConfig.PROPERTY_LLU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLluDance() {
        return isSupportLlu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvSeatVent() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvSeatHeat() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatHeat() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSmartHvac() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDsmCamera() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isVpmHwMiss() {
        return hasFeature(CarBaseConfig.PROPERTY_VPM_HW);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isVpmSwReady() {
        return hasFeature(CarBaseConfig.PROPERTY_VPM_SW);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isVpmNotReady() {
        return isVpmHwMiss() && !isVpmSwReady();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPilot() {
        return (CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1) && !isVpmHwMiss()) || CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_SCU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXpu() {
        return hasFeature(CarBaseConfig.PROPERTY_XPU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportActiveSafety() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportBsd() {
        return hasFeature(CarBaseConfig.PROPERTY_SCU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDow() {
        return hasFeature(CarBaseConfig.PROPERTY_SCU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRcw() {
        return hasFeature(CarBaseConfig.PROPERTY_SCU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRcta() {
        return hasFeature(CarBaseConfig.PROPERTY_SCU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEnhancedBsd() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIsla() {
        return hasFeature(CarBaseConfig.PROPERTY_SCU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSimpleSas() {
        return isSupportIsla();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMemPark() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoPilot() {
        return hasFeature(CarBaseConfig.PROPERTY_SCU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLcc() {
        return hasFeature(CarBaseConfig.PROPERTY_SCU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAlc() {
        return hasFeature(CarBaseConfig.PROPERTY_SCU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNgp() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXpuNedc() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNra() {
        return hasFeature(CarBaseConfig.PROPERTY_AVM);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIhb() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSdc() {
        return hasFeature(CarBaseConfig.PROPERTY_SDC);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAqs() {
        return hasFeature(CarBaseConfig.PROPERTY_AQS);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isWiperSensitiveNegative() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_3);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNfc() {
        return hasFeature(CarBaseConfig.PROPERTY_NFC);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int[] getWindowVentPos() {
        return new int[]{89, 89, 80, 80};
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportBossKey() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWelcomeMode() {
        return hasFeature(CarBaseConfig.PROPERTY_MSMD);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvLeg() {
        return isSupportMsmD();
    }
}
