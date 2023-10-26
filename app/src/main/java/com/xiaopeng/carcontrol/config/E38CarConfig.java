package com.xiaopeng.carcontrol.config;

import android.os.SystemProperties;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes.dex */
public class E38CarConfig extends CarBaseConfig {
    protected static final float E38_MAX_PRESSURE = 695.5f;
    public static final int PROPERTY_BODY_COLOR_BLACK = 19;
    public static final int PROPERTY_BODY_COLOR_BLUE = 16;
    public static final int PROPERTY_BODY_COLOR_BLUE2 = 17;
    public static final int PROPERTY_BODY_COLOR_GRAY = 15;
    public static final int PROPERTY_BODY_COLOR_GREEN = 18;
    private static final int PROPERTY_BODY_COLOR_INVALID = 0;
    public static final int PROPERTY_BODY_COLOR_SILVER = 14;
    public static final int PROPERTY_BODY_COLOR_WHITE = 12;
    private static final String TAG = "CarConfigHelper";

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public float getEpsSpdThreshold() {
        return 150.0f;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public float getEpsTorsionBarThreshold() {
        return 8.0f;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int getHvacMaxWindPos() {
        return 7;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int getHvacVersion() {
        return 3;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int getMeditationSeatPos() {
        return 50;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public float getTpmsMaxPressure() {
        return E38_MAX_PRESSURE;
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
    public boolean isSelfDevelopedHvac() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSingleChargePort() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSopStage() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAdjMirrorByCdu() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoCloseWin() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoTpmsCalibrate() {
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
    public boolean isSupportBrakeLight() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportChildLock() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCloseAutoDirect() {
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
    public boolean isSupportDomainCtrlUnit() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDomeLight() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDomeLightIndependentCtrl() {
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
    public boolean isSupportDriveModeNewArch() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrl() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvSeatStop() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportE38Model() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEbw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportElcTrunk() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportElk() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEnergyRecycleMediumLevel() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEpbSetting() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEpbWarning() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEsb() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEsbForLdw() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEspSportMode() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportHdc() {
        return true;
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
    public boolean isSupportLampHeight() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLightMeHomeNew() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLightMeHomeTime() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMainWiperInterval() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportManualTpmsCalibrate() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMcuKeyOpenFailed() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMcuSeatWelcome() {
        return true;
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
    public boolean isSupportMotorPower() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNeutralGearProtect() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNewAvasUnlockResponse() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNewChildLock() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNewDhc() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNewParkLampFmB() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNewUnlockResponseArch() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNewWiperSpdSt() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNormalDriveMode() {
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
    public boolean isSupportPsnSeatControl() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatStop() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRaeb() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRapidHeat() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearWiper() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSTWVR() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSdc() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSeatSmartControl() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSfsSw() {
        return true;
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
    public boolean isSupportShowEnergyRecycle() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportShowEps() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSmartHvac() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSnowMode() {
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
    public boolean isSupportTboxPowerSw() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTiltPosReversed() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTopCamera() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTrailerMode() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTrunkSetPosition() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTurnActive() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportUnity3D() {
        return false;
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
    public boolean isSupportWheelKeyProtect() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWheelSetting() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWindowStates() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWiperFault() {
        return true;
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
    public boolean isSupportXPedalNewArch() {
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
        switch (i) {
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                return i;
            case 13:
            default:
                return 12;
        }
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    int getCfcCode() {
        int parseInt;
        if (sCfcCode == null) {
            String str = SystemProperties.get("persist.sys.xiaopeng.cfcVehicleLevel", (String) null);
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
        return hasFeature(CarBaseConfig.PROPERTY_AWD);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportKeyPark() {
        return hasFeature(CarBaseConfig.PROPERTY_XPU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportKeyParkAdvanced() {
        return hasFeature(CarBaseConfig.PROPERTY_XPU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCarCallAdvanced() {
        return hasFeature(CarBaseConfig.PROPERTY_XPU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSsa() {
        return isSupportXpu();
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
        return getCfcLevel() >= 0;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportVipSeat() {
        return getCfcLevel() > 0;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSeatMassage() {
        return hasFeature(CarBaseConfig.PROPERTY_DRV_SEAT_MSG) || hasFeature(CarBaseConfig.PROPERTY_PSN_SEAT_MSG);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearSeatMassage() {
        return hasFeature(CarBaseConfig.PROPERTY_SECROW_LT_MSG) || hasFeature(CarBaseConfig.PROPERTY_SECROW_RT_MSG);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSeatRhythm() {
        return hasFeature(CarBaseConfig.PROPERTY_RHYTHM);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSeatRhythmEaseMode() {
        return isSupportSeatRhythm();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMsmD() {
        return hasFeature(CarBaseConfig.PROPERTY_MSMD);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvCushion() {
        return hasFeature(CarBaseConfig.PROPERTY_DRV_SEAT_CUSHEXT);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvLeg() {
        return hasFeature(CarBaseConfig.PROPERTY_DRV_SEAT_CUSHEXT_TILT);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvLumbar() {
        return hasFeature(CarBaseConfig.PROPERTY_DRV_SEAT_LSU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnLeg() {
        return hasFeature(CarBaseConfig.PROPERTY_PSN_SEAT_CUSHEXT);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnLumbar() {
        return hasFeature(CarBaseConfig.PROPERTY_PSN_SEAT_LSU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMsmP() {
        return hasFeature(CarBaseConfig.PROPERTY_MSMP);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearSeatCtrl() {
        return getCfcLevel() == 4 || getCfcLevel() == 19;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearSeatLeg() {
        return hasFeature(CarBaseConfig.PROPERTY_SECROW_LT_CUSHEXT) || hasFeature(CarBaseConfig.PROPERTY_SECROW_RT_CUSHEXT);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMsmPVerticalMove() {
        return isSupportSeatCtrl();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int[] getMSMSeatDefaultPos() {
        return MSM_SEAT_DEFAULT_POS_E38;
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
        return hasFeature(CarBaseConfig.PROPERTY_DRV_SEAT_VENT);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvSeatHeat() {
        return hasFeature(CarBaseConfig.PROPERTY_DRV_SEAT_HEAT);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatVent() {
        return hasFeature(CarBaseConfig.PROPERTY_PSN_SEAT_VENT);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatHeat() {
        return hasFeature(CarBaseConfig.PROPERTY_PSN_SEAT_HEAT);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDsmCamera() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMrrGeoFence() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPilot() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXpu() {
        return hasFeature(CarBaseConfig.PROPERTY_XPU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportActiveSafety() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportBsd() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDow() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRcw() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRcta() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLka() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIsla() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSimpleSas() {
        return isSupportIsla();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoPark() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMemPark() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoPilot() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLcc() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAlc() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNgp() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCNgp() {
        return isSupportLidar();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXpuNedc() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLidar() {
        return hasFeature(CarBaseConfig.PROPERTY_LIDAR_L) || hasFeature(CarBaseConfig.PROPERTY_LIDAR_R);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNra() {
        return hasFeature(CarBaseConfig.PROPERTY_XPU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTurnAssist() {
        return hasFeature(CarBaseConfig.PROPERTY_XPU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIhb() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearSeatHeat() {
        return hasFeature(CarBaseConfig.PROPERTY_SECROW_LT_HEAT) || hasFeature(CarBaseConfig.PROPERTY_SECROW_RT_HEAT);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSteerHeat() {
        return CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_STEER_HEAT);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAqs() {
        return hasFeature(CarBaseConfig.PROPERTY_AQS);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSfs() {
        return hasFeature(CarBaseConfig.PROPERTY_SFS);
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
    public boolean isSupportControlSteer() {
        return getCfcLevel() > 0;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportBossKey() {
        return getCfcLevel() > 0 && hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoLampHeight() {
        return getCfcLevel() >= 3;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSaberLightFeedBack() {
        return isSupportLlu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWelcomeMode() {
        return isSupportMsmD();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnWelcomeMode() {
        return isSupportMsmP();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearSeatWelcomeMode() {
        return hasFeature(CarBaseConfig.PROPERTY_REAT_SEAT_WELCOME);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAsWelcomeMode() {
        return hasFeature(CarBaseConfig.PROPERTY_AS_WELCOME);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXSport() {
        return hasSettingsFeature(CarBaseConfig.PROPERTY_SETTINGS_XSPORT, 1);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAirSuspension() {
        return hasFeature(CarBaseConfig.PROPERTY_AS);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEspMudMode() {
        return isSupportAwd();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTrailerHook() {
        return hasFeature(CarBaseConfig.PROPERTY_TTM);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTrailerRv() {
        return hasFeature(CarBaseConfig.PROPERTY_TTM);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSensorTrunk() {
        return hasFeature(CarBaseConfig.PROPERTY_FKS_TRUNK);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMeditationPlus() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCrossBarriers() {
        return isSupportLidar();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTrafficLight() {
        return isSupportLidar();
    }
}
