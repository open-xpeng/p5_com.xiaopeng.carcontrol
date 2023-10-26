package com.xiaopeng.carcontrol.config;

import android.car.Car;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.ContextUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import java.util.HashMap;

/* loaded from: classes.dex */
public abstract class CarBaseConfig {
    static final int CFC_CODE_HIGH = 3;
    static final int CFC_CODE_INVALID = 0;
    static final int CFC_CODE_LOW = 1;
    static final int CFC_CODE_MIDDLE = 2;
    static final int CFC_CODE_TRIP = 4;
    static final int CFC_LVL_0 = 0;
    static final int CFC_LVL_1 = 1;
    static final int CFC_LVL_2 = 2;
    static final int CFC_LVL_2X = 18;
    static final int CFC_LVL_3 = 3;
    static final int CFC_LVL_3A = 5;
    static final int CFC_LVL_3B = 6;
    static final int CFC_LVL_3X = 19;
    static final int CFC_LVL_4 = 4;
    protected static final float DEFAULT_MAX_PRESSURE = 348.742f;
    public static final String FO_AI_LLU = "fo.xui.aillu.enabled";
    public static final int HVAC_VER_1 = 1;
    public static final int HVAC_VER_2 = 2;
    public static final int HVAC_VER_3 = 3;
    static final boolean IS_DEVELOPING = false;
    public static final String PROPERTY_AMBIENT_API = "persist.sys.xpeng.ambient.api";
    public static final String PROPERTY_AMBIENT_SERVICE = "persist.sys.xpeng.ambient.service";
    public static final String PROPERTY_AMP = "persist.sys.xiaopeng.AMP";
    public static final String PROPERTY_AQS = "persist.sys.xiaopeng.AQS";
    public static final String PROPERTY_ARS = "persist.sys.xiaopeng.Spoiler";
    public static final String PROPERTY_AS = "persist.sys.xiaopeng.AS";
    public static final String PROPERTY_AS_WELCOME = "persist.sys.xiaopeng.AS_WELCOME";
    public static final String PROPERTY_ATLS = "persist.sys.xiaopeng.ATLS";
    public static final String PROPERTY_AVM = "persist.sys.xiaopeng.AVM";
    public static final String PROPERTY_AWD = "persist.sys.xiaopeng.driveway";
    protected static final String PROPERTY_BODY_COLOR = "persist.sys.xiaopeng.bodyColor";
    public static final String PROPERTY_CARPET_LIGHT = " persist.sys.xiaopeng.CARPET_LIGHT";
    public static final String PROPERTY_CDC = "persist.sys.xiaopeng.CDC";
    protected static final String PROPERTY_CFC = "persist.sys.xiaopeng.cfcVehicleLevel";
    public static final String PROPERTY_CIU = "persist.sys.xiaopeng.CIU";
    public static final String PROPERTY_CMS = "persist.sys.xiaopeng.CMS";
    public static final String PROPERTY_CWC = "persist.sys.xiaopeng.CWC";
    public static final String PROPERTY_CWC_FR = "persist.sys.xiaopeng.CWC_FR";
    public static final String PROPERTY_CWC_RL = "persist.sys.xiaopeng.CWC_RL";
    public static final String PROPERTY_CWC_RR = "persist.sys.xiaopeng.CWC_RR";
    public static final String PROPERTY_DOLBY = "persist.sys.xiaopeng.DOLBY";
    public static final String PROPERTY_DRV_SEAT_CUSHEXT = "persist.sys.xiaopeng.MSMD_CUSHEXT";
    public static final String PROPERTY_DRV_SEAT_CUSHEXT_TILT = "persist.sys.xiaopeng.MSMD_CUSHEXT_TILT";
    @Deprecated
    public static final String PROPERTY_DRV_SEAT_HEAT = "persist.sys.xiaopeng.MSMD_HEAT";
    public static final String PROPERTY_DRV_SEAT_LSU = "persist.sys.xiaopeng.MSMD_LSU";
    public static final String PROPERTY_DRV_SEAT_MSG = "persist.sys.xiaopeng.MSMD_MASSG";
    public static final String PROPERTY_DRV_SEAT_VENT = "persist.sys.xiaopeng.MSMD_VENT";
    public static final String PROPERTY_DSM_CAMERA = "persist.sys.xiaopeng.dsm";
    public static final String PROPERTY_ELC_TRUNK = "persist.sys.xiaopeng.empennage";
    public static final String PROPERTY_FKS_TRUNK = "persist.sys.xiaopeng.FKS";
    public static final String PROPERTY_IMS = "persist.sys.xiaopeng.IMS";
    public static final String PROPERTY_IMU = "persist.sys.xiaopeng.IMU";
    public static final String PROPERTY_IPUF = "persist.sys.xiaopeng.IPUF";
    public static final String PROPERTY_IPUR = "persist.sys.xiaopeng.IPUR";
    @Deprecated
    public static final String PROPERTY_LEG_SUP = "persist.sys.xiaopeng.LEG_SUP";
    public static final String PROPERTY_LIDAR_L = "persist.sys.xiaopeng.Lidar_F";
    public static final String PROPERTY_LIDAR_R = "persist.sys.xiaopeng.Lidar_R";
    public static final String PROPERTY_LLU = "persist.sys.xiaopeng.LLU";
    @Deprecated
    public static final String PROPERTY_LUMBAR_SUP = "persist.sys.xiaopeng.LUMBAR_SUP";
    public static final String PROPERTY_MIRROR = "persist.sys.xiaopeng.MIRROR";
    public static final String PROPERTY_MRR = "persist.sys.xiaopeng.MRR";
    public static final String PROPERTY_MRR_SRR = "persist.sys.xiaopeng.MRR_SRR";
    public static final String PROPERTY_MSB = "persist.sys.xiaopeng.MSB";
    public static final String PROPERTY_MSMD = "persist.sys.xiaopeng.MSMD";
    public static final String PROPERTY_MSMD_ADJ = "persist.sys.xiaopeng.MSMD_ADJ";
    public static final String PROPERTY_MSMD_HRS = "persist.sys.xiaopeng.MSMD_HRS";
    public static final String PROPERTY_MSMP = "persist.sys.xiaopeng.MSMP";
    public static final String PROPERTY_MSMP_ADJ = "persist.sys.xiaopeng.MSMP_ADJ";
    public static final String PROPERTY_MSMP_HRS = "persist.sys.xiaopeng.MSMP_HRS";
    public static final String PROPERTY_MULTITEMPAREA = "persist.sys.xiaopeng.multiTempArea";
    public static final String PROPERTY_NFC = "persist.sys.xiaopeng.NFC";
    static final String PROPERTY_NOT_SUPPORT = "0";
    public static final String PROPERTY_PAB_SWITCH = "persist.sys.xiaopeng.PAB.Switch";
    public static final String PROPERTY_PACKAGE_1 = "persist.sys.xiaopeng.Package1";
    public static final String PROPERTY_PACKAGE_2 = "persist.sys.xiaopeng.Package2";
    public static final String PROPERTY_PACKAGE_3 = "persist.sys.xiaopeng.Package3";
    public static final String PROPERTY_PACKAGE_4 = "persist.sys.xiaopeng.Package4";
    public static final String PROPERTY_PACKAGE_5 = "persist.sys.xiaopeng.Package5";
    public static final String PROPERTY_PACKAGE_6 = "persist.sys.xiaopeng.Package6";
    public static final String PROPERTY_PACKAGE_7 = "persist.sys.xiaopeng.Package7";
    public static final String PROPERTY_PACKAGE_8 = "persist.sys.xiaopeng.Package8";
    public static final String PROPERTY_PAS = "persist.sys.xiaopeng.PAS";
    public static final String PROPERTY_PM03 = "persist.sys.xiaopeng.PM03";
    public static final String PROPERTY_PM25 = "persist.sys.xiaopeng.pm25";
    public static final String PROPERTY_PRODUCTION_STAGE = "persist.sys.xiaopeng.production_stage";
    public static final String PROPERTY_PSN_SEAT_CUSHEXT = "persist.sys.xiaopeng.MSMP_CUSHEXT";
    public static final String PROPERTY_PSN_SEAT_HEAT = "persist.sys.xiaopeng.MSMP_HEAT";
    public static final String PROPERTY_PSN_SEAT_LSU = "persist.sys.xiaopeng.MSMP_LSU";
    public static final String PROPERTY_PSN_SEAT_MSG = "persist.sys.xiaopeng.MSMP_MASSG";
    public static final String PROPERTY_PSN_SEAT_VENT = "persist.sys.xiaopeng.MSMP_VENT";
    public static final String PROPERTY_REAR_SCREEN = "persist.sys.xiaopeng.SFM";
    @Deprecated
    public static final String PROPERTY_REAR_SEAT_HEAT = "persist.sys.xiaopeng.RSEAT_HEAT";
    public static final String PROPERTY_REAT_SEAT_WELCOME = "persist.sys.xiaopeng.SECROW_WELCOME";
    public static final String PROPERTY_RHYTHM = "persist.sys.xiaopeng.FSEAT_RHYTHM";
    public static final String PROPERTY_RLS = "persist.sys.xiaopeng.RLS";
    public static final String PROPERTY_RWS = "persist.sys.xiaopeng.RWS";
    public static final String PROPERTY_SCU = "persist.sys.xiaopeng.SCU";
    public static final String PROPERTY_SDC = "persist.sys.xiaopeng.scissorsGate";
    public static final String PROPERTY_SEAT_DEBUG = "persist.sys.xiaopeng.msm.debug";
    @Deprecated
    public static final String PROPERTY_SEAT_MSG = "persist.sys.xiaopeng.SEAT_MASS";
    @Deprecated
    public static final String PROPERTY_SEAT_VENT = "persist.sys.xiaopeng.SEAT_VENT";
    public static final String PROPERTY_SECROW_HEADREST = "persist.sys.xiaopeng.SECROW_HEAD_ADJ";
    public static final String PROPERTY_SECROW_LT_CUSHEXT = "persist.sys.xiaopeng.SECROW_LT_CUSHEXT";
    public static final String PROPERTY_SECROW_LT_HEAT = "persist.sys.xiaopeng.SECROW_LT_HEAT";
    public static final String PROPERTY_SECROW_LT_LEGVER = "persist.sys.xiaopeng.SECROW_LT_CUSHEXT_TILT";
    public static final String PROPERTY_SECROW_LT_LSU = "persist.sys.xiaopeng.SECROW_LT_LSU";
    public static final String PROPERTY_SECROW_LT_MSG = "persist.sys.xiaopeng.SECROW_LT_MASSG";
    public static final String PROPERTY_SECROW_LT_VENT = "persist.sys.xiaopeng.SECROW_LT_VENT";
    public static final String PROPERTY_SECROW_MEMORY = "persist.sys.xiaopeng.SECROW_MEMORY";
    public static final String PROPERTY_SECROW_RT_CUSHEXT = "persist.sys.xiaopeng.SECROW_RT_CUSHEXT";
    public static final String PROPERTY_SECROW_RT_HEAT = "persist.sys.xiaopeng.SECROW_RT_HEAT";
    public static final String PROPERTY_SECROW_RT_LEGVER = "persist.sys.xiaopeng.SECROW_RT_CUSHEXT_TILT";
    public static final String PROPERTY_SECROW_RT_LSU = "persist.sys.xiaopeng.SECROW_RT_LSU";
    public static final String PROPERTY_SECROW_RT_MSG = "persist.sys.xiaopeng.SECROW_RT_MASSG";
    public static final String PROPERTY_SECROW_RT_VENT = "persist.sys.xiaopeng.SECROW_RT_VENT";
    public static final String PROPERTY_SECROW_SEAT_HORZ = "persist.sys.xiaopeng.SECROW_SEAT_HORZ";
    public static final String PROPERTY_SECROW_ZEROGRAV = "persist.sys.xiaopeng.MSM_ZEROGRAV";
    public static final int PROPERTY_SETTINGS_NOT_SUPPORT = 0;
    public static final int PROPERTY_SETTINGS_SUPPORT = 1;
    public static final String PROPERTY_SETTINGS_XSPORT = "key_napa_xsport";
    public static final String PROPERTY_SFS = "persist.sys.xiaopeng.SFS";
    public static final String PROPERTY_SHC = "persist.sys.xiaopeng.SHC";
    public static final String PROPERTY_SPC = "persist.sys.xiaopeng.SPC";
    public static final String PROPERTY_SRR_FL = "persist.sys.xiaopeng.SRR_FL";
    public static final String PROPERTY_SRR_FR = "persist.sys.xiaopeng.SRR_FR";
    public static final String PROPERTY_SRR_RL = "persist.sys.xiaopeng.SRR_RL";
    public static final String PROPERTY_SRR_RR = "persist.sys.xiaopeng.SRR_RR";
    public static final String PROPERTY_STEER_COLUMN_ADJUST = "persist.sys.xiaopeng.STEER_COL_ADJ";
    public static final String PROPERTY_STEER_HEAT = "persist.sys.xiaopeng.SWS_HEAT";
    static final String PROPERTY_SUPPORT = "1";
    public static final String PROPERTY_SXP = "persist.sys.xiaopeng.SXP";
    public static final String PROPERTY_TRDROW_HEADREST = "persist.sys.xiaopeng.SECROW_RL_HEAD_HEGIHT";
    public static final String PROPERTY_TRDROW_MID_HEADREST = "persist.sys.xiaopeng.SECROW_MID_HEAD_HEGIHT";
    public static final String PROPERTY_TRDROW_MID_SEAT = "persist.sys.xiaopeng.MSM_MID_SEAT";
    public static final String PROPERTY_TRDROW_STOW = "persist.sys.xiaopeng.MSM_STOW";
    public static final String PROPERTY_TRDROW_TILT = "persist.sys.xiaopeng.MSM_TILTING";
    public static final String PROPERTY_TTM = "persist.sys.xiaopeng.TTM";
    protected static final String PROPERTY_VEHICLE_TYPE = "persist.sys.xiaopeng.vehicleType";
    public static final String PROPERTY_VPM = "persist.sys.xiaopeng.VPM";
    public static final String PROPERTY_VPM_HW = "persist.sys.xiaopeng.VPM_MRR";
    public static final String PROPERTY_VPM_SW = "persist.sys.xiaopeng.VPM_MRR_SW";
    public static final String PROPERTY_XPU = "persist.sys.xiaopeng.XPU";
    public static final String PROPERTY_XSPORT = "persist.sys.xiaopeng.XSPORT";
    public static final String TAG = "CarBaseConfig";
    static final int TEMP_AREA_DUAL = 2;
    private static final String VEHICLE_TYPE_D22A = "5";
    protected static Integer sCfcLvl;
    protected static final int[] MSM_SEAT_DEFAULT_POS = {20, 55, 25, 30, 0};
    protected static final int[] MSM_SEAT_DEFAULT_POS_E28A = {20, 55, 75, 30, 0};
    protected static final int[] MSM_SEAT_DEFAULT_POS_D21 = {23, 20, 31, 30, 0};
    protected static final int[] MSM_SEAT_DEFAULT_POS_D55 = {23, 20, 69, 0, 0};
    protected static final int[] MSM_SEAT_DEFAULT_POS_E38 = {20, 55, 75, 30, 50};
    protected static final int[] MSM_SEAT_DEFAULT_POS_H93 = {50, 0, 50, 50, 50, 50, 50};
    protected static int[] sleepRestSeatDrv = {68, 32, 81};
    protected static int[] sleepRestSeatPsn = {69, 0, 80};
    protected static String sVehicleType = null;
    protected static String sCduType = null;
    protected static Integer sCfcCode = null;
    protected static HashMap<String, Boolean> sFeatureSupport = new HashMap<>();

    public static boolean isDeveloping() {
        return false;
    }

    public abstract int getCduMirrorReverseMode();

    abstract int getCfcCode();

    public int getEpbAccPedalThreshold() {
        return 2;
    }

    public float getEpsSpdThreshold() {
        return 100.0f;
    }

    public float getEpsTorsionBarThreshold() {
        return 0.5f;
    }

    public float getHighSpdFuncThreshold() {
        return 70.0f;
    }

    public int getHvacMaxWindPos() {
        return 5;
    }

    public abstract int getHvacVersion();

    public abstract int[] getMSMSeatDefaultPos();

    public int getMeditationSeatPos() {
        return 85;
    }

    public float getSeatTiltAngleFactor() {
        return 0.5f;
    }

    public int[] getSecRowMSMSeatDefaultPos() {
        return null;
    }

    public float getTpmsMaxPressure() {
        return DEFAULT_MAX_PRESSURE;
    }

    public abstract int[] getWindowVentPos();

    public boolean isChargePortSignalErr() {
        return false;
    }

    public abstract boolean isFollowVehicleLostConfigUseNew();

    public boolean isLssCertification() {
        return false;
    }

    public boolean isNewAvasArch() {
        return true;
    }

    public boolean isNewBcmArch() {
        return true;
    }

    public boolean isNewEspArch() {
        return true;
    }

    public abstract boolean isNewIcmArch();

    public abstract boolean isNewMsmArch();

    public abstract boolean isNewScuArch();

    public boolean isNewVcuArch() {
        return true;
    }

    public boolean isSelfDevelopedHvac() {
        return false;
    }

    public boolean isSingleChargePort() {
        return false;
    }

    public boolean isSopStage() {
        return false;
    }

    public boolean isSupportAbsFault() {
        return false;
    }

    public abstract boolean isSupportActiveSafety();

    public boolean isSupportAdjMirrorByCdu() {
        return false;
    }

    public abstract boolean isSupportAirSuspension();

    public abstract boolean isSupportAlc();

    public boolean isSupportAqs() {
        return false;
    }

    public boolean isSupportAsWelcomeMode() {
        return false;
    }

    public abstract boolean isSupportAutoCloseWin();

    public boolean isSupportAutoLampHeight() {
        return false;
    }

    public abstract boolean isSupportAutoPark();

    public abstract boolean isSupportAutoPilot();

    public boolean isSupportAutoPowerOff() {
        return false;
    }

    public boolean isSupportAutoTpmsCalibrate() {
        return false;
    }

    public abstract boolean isSupportAutoWindowLock();

    public abstract boolean isSupportAuxiliaryFunc();

    public abstract boolean isSupportAvasLoudSpeaker();

    public abstract boolean isSupportAwd();

    public boolean isSupportAwdSetting() {
        return false;
    }

    public abstract boolean isSupportBcmCtrlSeatBelt();

    public abstract boolean isSupportBootSoundEffect();

    public abstract boolean isSupportBossKey();

    public boolean isSupportBrakeLight() {
        return false;
    }

    public abstract boolean isSupportBsd();

    public abstract boolean isSupportCNgp();

    public abstract boolean isSupportCarCallAdvanced();

    public boolean isSupportCarCondition() {
        return true;
    }

    public boolean isSupportCdcControl() {
        return false;
    }

    public abstract boolean isSupportChildLock();

    public boolean isSupportChildMode() {
        return false;
    }

    public boolean isSupportCiuConfig() {
        return false;
    }

    public boolean isSupportCloseAutoDirect() {
        return false;
    }

    public boolean isSupportCms() {
        return false;
    }

    public boolean isSupportControlSteer() {
        return false;
    }

    public boolean isSupportCrossBarriers() {
        return false;
    }

    public abstract boolean isSupportCtrlBonnet();

    public abstract boolean isSupportCtrlChargePort();

    public abstract boolean isSupportCwc();

    public boolean isSupportDaytimeRunningLight() {
        return false;
    }

    public abstract boolean isSupportDhc();

    public abstract boolean isSupportDigitalKeyTip();

    public boolean isSupportDomainCtrlUnit() {
        return false;
    }

    public abstract boolean isSupportDomeLight();

    public boolean isSupportDomeLightIndependentCtrl() {
        return false;
    }

    public boolean isSupportDomeLightThirdRow() {
        return false;
    }

    public abstract boolean isSupportDoorKeySetting();

    public abstract boolean isSupportDow();

    public abstract boolean isSupportDriveEnergyReset();

    public boolean isSupportDriveModeNewArch() {
        return false;
    }

    public abstract boolean isSupportDrl();

    public boolean isSupportDrvCushion() {
        return false;
    }

    public boolean isSupportDrvLumbar() {
        return false;
    }

    public abstract boolean isSupportDrvSeatHeat();

    public abstract boolean isSupportDrvSeatStop();

    public abstract boolean isSupportDrvSeatVent();

    public abstract boolean isSupportDsmCamera();

    public boolean isSupportE38Model() {
        return false;
    }

    public abstract boolean isSupportEbw();

    public boolean isSupportElcTrunk() {
        return false;
    }

    public boolean isSupportElk() {
        return false;
    }

    public abstract boolean isSupportEnergyRecycleMediumLevel();

    public boolean isSupportEnhancedBsd() {
        return false;
    }

    public boolean isSupportEnhancedLidarFunc() {
        return false;
    }

    public abstract boolean isSupportEpbSetting();

    public abstract boolean isSupportEpbWarning();

    public boolean isSupportEpsTorque() {
        return true;
    }

    public abstract boolean isSupportEsb();

    public abstract boolean isSupportEsbForLdw();

    public boolean isSupportEspBpf() {
        return false;
    }

    public boolean isSupportEspCst() {
        return false;
    }

    public abstract boolean isSupportEspMudMode();

    public boolean isSupportEspSportMode() {
        return false;
    }

    public boolean isSupportFcwSen() {
        return false;
    }

    public boolean isSupportFrontMirrorHeat() {
        return false;
    }

    public abstract boolean isSupportHdc();

    public boolean isSupportIBoosterSignal() {
        return true;
    }

    public abstract boolean isSupportIhb();

    public boolean isSupportIms() {
        return false;
    }

    public abstract boolean isSupportInductionLock();

    public boolean isSupportIntelligentDesiccation() {
        return true;
    }

    public abstract boolean isSupportIsla();

    public abstract boolean isSupportIslc();

    public abstract boolean isSupportIslcInActive();

    public abstract boolean isSupportKeyPark();

    public abstract boolean isSupportKeyParkAdvanced();

    public boolean isSupportLampHeight() {
        return false;
    }

    public abstract boolean isSupportLcc();

    public boolean isSupportLidar() {
        return false;
    }

    public abstract boolean isSupportLightMeHomeNew();

    public abstract boolean isSupportLightMeHomeTime();

    public abstract boolean isSupportLka();

    public abstract boolean isSupportLlu();

    public boolean isSupportLluDance() {
        return false;
    }

    public boolean isSupportLssSen() {
        return false;
    }

    public abstract boolean isSupportMainWiperInterval();

    public boolean isSupportMaintenanceInfo() {
        return false;
    }

    public boolean isSupportManualTpmsCalibrate() {
        return true;
    }

    public boolean isSupportMcuKeyOpenFailed() {
        return false;
    }

    public boolean isSupportMcuPowerSw() {
        return false;
    }

    public boolean isSupportMcuSeatWelcome() {
        return true;
    }

    public boolean isSupportMeditationPlus() {
        return false;
    }

    public abstract boolean isSupportMemPark();

    public abstract boolean isSupportMeterSetting();

    public abstract boolean isSupportMirrorDown();

    public boolean isSupportMirrorFold() {
        return true;
    }

    public boolean isSupportMirrorHeat() {
        return false;
    }

    public boolean isSupportMotorPower() {
        return false;
    }

    public boolean isSupportMrrGeoFence() {
        return false;
    }

    public abstract boolean isSupportMsmD();

    public abstract boolean isSupportMsmP();

    public abstract boolean isSupportMsmPVerticalMove();

    public abstract boolean isSupportNeutralGearProtect();

    public boolean isSupportNewAvasUnlockResponse() {
        return false;
    }

    public boolean isSupportNewChildLock() {
        return false;
    }

    public boolean isSupportNewDhc() {
        return false;
    }

    public boolean isSupportNewParkLampFmB() {
        return false;
    }

    public boolean isSupportNewUnlockResponseArch() {
        return false;
    }

    public abstract boolean isSupportNewWiperSpdSt();

    public abstract boolean isSupportNfc();

    public abstract boolean isSupportNgp();

    public abstract boolean isSupportNormalDriveMode();

    public boolean isSupportNra() {
        return false;
    }

    public boolean isSupportOfflineUserPortfolioPage() {
        return false;
    }

    public boolean isSupportOldIsla() {
        return false;
    }

    public boolean isSupportOldLka() {
        return false;
    }

    public abstract boolean isSupportPM25Out();

    public boolean isSupportParkingLampOutput() {
        return false;
    }

    public boolean isSupportPollingLightWelcomeMode() {
        return false;
    }

    public abstract boolean isSupportPollingLock();

    public abstract boolean isSupportPollingOpenCfg();

    public boolean isSupportPsnLeg() {
        return false;
    }

    public boolean isSupportPsnLumbar() {
        return false;
    }

    public boolean isSupportPsnSeatControl() {
        return false;
    }

    public abstract boolean isSupportPsnSeatHeat();

    public abstract boolean isSupportPsnSeatStop();

    public abstract boolean isSupportPsnSeatVent();

    public boolean isSupportPsnWelcomeMode() {
        return false;
    }

    public boolean isSupportRaeb() {
        return false;
    }

    public boolean isSupportRapidHeat() {
        return false;
    }

    public abstract boolean isSupportRcta();

    public abstract boolean isSupportRcw();

    public boolean isSupportRearBeltWarningSwitch() {
        return true;
    }

    public boolean isSupportRearLogoLight() {
        return false;
    }

    public boolean isSupportRearScreen() {
        return false;
    }

    public boolean isSupportRearSeatCtrl() {
        return false;
    }

    public abstract boolean isSupportRearSeatHeat();

    public boolean isSupportRearSeatLeg() {
        return false;
    }

    public boolean isSupportRearSeatMassage() {
        return false;
    }

    public boolean isSupportRearSeatWelcomeMode() {
        return false;
    }

    public abstract boolean isSupportRearWiper();

    public boolean isSupportRemoteCamera() {
        return false;
    }

    public boolean isSupportSTWVR() {
        return false;
    }

    public boolean isSupportSaberLightFeedBack() {
        return false;
    }

    public abstract boolean isSupportSdc();

    public abstract boolean isSupportSeatCtrl();

    public boolean isSupportSeatMassage() {
        return false;
    }

    public boolean isSupportSeatRhythmEaseMode() {
        return false;
    }

    public abstract boolean isSupportSeatSmartControl();

    public boolean isSupportSfs() {
        return false;
    }

    public boolean isSupportSfsSw() {
        return false;
    }

    public boolean isSupportShadeInitCloseConstraint() {
        return false;
    }

    public abstract boolean isSupportShowAutoPark();

    public abstract boolean isSupportShowDriveAutoLock();

    public abstract boolean isSupportShowEbw();

    public boolean isSupportShowEnergyRecycle() {
        return true;
    }

    public boolean isSupportShowEps() {
        return true;
    }

    public boolean isSupportSlideDoor() {
        return false;
    }

    public abstract boolean isSupportSmartHvac();

    public abstract boolean isSupportSnowMode();

    public boolean isSupportSpecialSas() {
        return false;
    }

    public boolean isSupportSrrMiss() {
        return false;
    }

    public abstract boolean isSupportSrs();

    public boolean isSupportSsa() {
        return false;
    }

    public boolean isSupportSteerHeat() {
        return false;
    }

    public abstract boolean isSupportSunShade();

    public abstract boolean isSupportSwitchMedia();

    public boolean isSupportTboxPowerSw() {
        return false;
    }

    public boolean isSupportTiltPosReversed() {
        return false;
    }

    public abstract boolean isSupportTopCamera();

    public boolean isSupportTpmsCalibrate() {
        return true;
    }

    public boolean isSupportTrafficLight() {
        return false;
    }

    public abstract boolean isSupportTrailerHook();

    public abstract boolean isSupportTrailerMode();

    public abstract boolean isSupportTrailerRv();

    public abstract boolean isSupportTrunkSetPosition();

    public abstract boolean isSupportTurnActive();

    public boolean isSupportTurnAssist() {
        return false;
    }

    public abstract boolean isSupportUnity3D();

    public abstract boolean isSupportUserPortfolioPage();

    public abstract boolean isSupportVcuExhibitionMode();

    public boolean isSupportVcuExhibitionNewVcuArch() {
        return true;
    }

    public abstract boolean isSupportVipSeat();

    public abstract boolean isSupportWelcomeMode();

    public abstract boolean isSupportWheelKeyProtect();

    public abstract boolean isSupportWheelSetting();

    public boolean isSupportWindowAutoCtrl() {
        return true;
    }

    public boolean isSupportWindowInitFailed() {
        return false;
    }

    public boolean isSupportWindowLock() {
        return false;
    }

    public boolean isSupportWindowPos() {
        return true;
    }

    public abstract boolean isSupportWindowStates();

    public boolean isSupportWiperFault() {
        return false;
    }

    public abstract boolean isSupportWiperInterval();

    public abstract boolean isSupportWiperRepair();

    public abstract boolean isSupportWiperSenCfg();

    public abstract boolean isSupportXPedal();

    public boolean isSupportXPilot() {
        return true;
    }

    public boolean isSupportXPilotSafeExam() {
        return true;
    }

    public abstract boolean isSupportXPilotTtsCfg();

    public abstract boolean isSupportXSayHi();

    public boolean isSupportXSport() {
        return false;
    }

    public abstract boolean isSupportXUnlockTrunk();

    public boolean isSupportXkeyDisable() {
        return false;
    }

    public boolean isSupportXpilotLccBindLdw() {
        return false;
    }

    public boolean isSupportXpilotLccBindLss() {
        return false;
    }

    public abstract boolean isSupportXpu();

    public abstract boolean isSupportXpuNedc();

    public boolean isVpmHwMiss() {
        return false;
    }

    public boolean isVpmNotReady() {
        return false;
    }

    public boolean isVpmSwReady() {
        return true;
    }

    public boolean isWiperAdjustByHardKey() {
        return false;
    }

    public boolean isWiperSensitiveNegative() {
        return false;
    }

    public abstract int matchSeatSetPos(int pos);

    static /* synthetic */ CarBaseConfig access$000() {
        return createCarConfig();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class SingletonHolder {
        private static final CarBaseConfig sInstance = CarBaseConfig.access$000();

        private SingletonHolder() {
        }
    }

    public static CarBaseConfig getInstance() {
        return SingletonHolder.sInstance;
    }

    private static CarBaseConfig createCarConfig() {
        String xpCduType = CarStatusUtils.getXpCduType();
        xpCduType.hashCode();
        char c = 65535;
        switch (xpCduType.hashCode()) {
            case 2560:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q1)) {
                    c = 0;
                    break;
                }
                break;
            case 2561:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q2)) {
                    c = 1;
                    break;
                }
                break;
            case 2562:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q3)) {
                    c = 2;
                    break;
                }
                break;
            case 2565:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q6)) {
                    c = 3;
                    break;
                }
                break;
            case 2566:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q7)) {
                    c = 4;
                    break;
                }
                break;
            case 2567:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q8)) {
                    c = 5;
                    break;
                }
                break;
            case 2568:
                if (xpCduType.equals("Q9")) {
                    c = 6;
                    break;
                }
                break;
            case 2577:
                if (xpCduType.equals("QB")) {
                    c = 7;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return CarStatusUtils.isEURegion() ? new E28vCarConfig() : new E28CarConfig();
            case 1:
                return CarStatusUtils.isEURegion() ? new D21vCarConfig() : new D21CarConfig();
            case 2:
                return CarStatusUtils.isEURegion() ? new D55vCarConfig() : new D55CarConfig();
            case 3:
                if ("5".equalsIgnoreCase(getVehicleType())) {
                    return new D22aCarConfig();
                }
                return CarStatusUtils.isEURegion() ? new D22vCarConfig() : new D22CarConfig();
            case 4:
                return CarStatusUtils.isEURegion() ? new E38vCarConfig() : new E38CarConfig();
            case 5:
                return CarStatusUtils.isEURegion() ? new E28avCarConfig() : new E28aCarConfig();
            case 6:
                return new F30CarConfig();
            case 7:
                return new H93CarConfig();
            default:
                return new D21CarConfig();
        }
    }

    protected static String getVehicleType() {
        if (sVehicleType == null) {
            sVehicleType = SystemProperties.get(PROPERTY_VEHICLE_TYPE);
            LogUtils.i(TAG, "Vehicle type: " + sVehicleType, false);
        }
        return sVehicleType;
    }

    public static String getCarCduType() {
        if (sCduType == null) {
            try {
                sCduType = Car.getXpCduType();
            } catch (Exception e) {
                LogUtils.w(TAG, "can not getXpCduType error = " + e, false);
                sCduType = VuiUtils.CAR_PLATFORM_Q2;
            }
        }
        return sCduType;
    }

    public static boolean isSupportShowDebug() {
        String xpCduType = CarStatusUtils.getXpCduType();
        xpCduType.hashCode();
        char c = 65535;
        switch (xpCduType.hashCode()) {
            case 2560:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q1)) {
                    c = 0;
                    break;
                }
                break;
            case 2562:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q3)) {
                    c = 1;
                    break;
                }
                break;
            case 2565:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q6)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    public static boolean hasFeature(String propertyName) {
        Boolean bool = sFeatureSupport.get(propertyName);
        if (bool == null) {
            String str = SystemProperties.get(propertyName, "0");
            LogUtils.i(TAG, "Read prop: " + propertyName + " value: " + str, false);
            bool = Boolean.valueOf("1".equals(str));
            sFeatureSupport.put(propertyName, bool);
        }
        return bool.booleanValue();
    }

    protected static boolean hasSettingsFeature(String propertyName) {
        return hasSettingsFeature(propertyName, 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static boolean hasSettingsFeature(String propertyName, int defVaule) {
        Boolean bool = sFeatureSupport.get(propertyName);
        if (bool == null) {
            int i = Settings.Global.getInt(ContextUtils.getContext().getContentResolver(), propertyName, defVaule);
            LogUtils.i(TAG, "Read Settings prop: " + propertyName + " value: " + i, false);
            bool = Boolean.valueOf(i == 1);
            sFeatureSupport.put(propertyName, bool);
        }
        return bool.booleanValue();
    }

    public static void updateFeature(String propertyName, boolean value) {
        if (sFeatureSupport.get(propertyName) != null) {
            sFeatureSupport.put(propertyName, Boolean.valueOf(value));
            LogUtils.i(TAG, "UpdateFeature Settings prop: " + propertyName + " value: " + value, false);
        }
    }

    public int getCfcLevel() {
        int parseInt;
        if (sCfcLvl == null) {
            String str = SystemProperties.get(PROPERTY_CFC, (String) null);
            if (!TextUtils.isEmpty(str)) {
                try {
                    parseInt = Integer.parseInt(str);
                } catch (Exception unused) {
                }
                sCfcLvl = Integer.valueOf(parseInt);
                LogUtils.i(TAG, "getCfcLevel: " + sCfcLvl, false);
            }
            parseInt = 0;
            sCfcLvl = Integer.valueOf(parseInt);
            LogUtils.i(TAG, "getCfcLevel: " + sCfcLvl, false);
        }
        return sCfcLvl.intValue();
    }

    public boolean isSupportXpuDomainCtrl() {
        return isSupportDomainCtrlUnit();
    }

    public boolean isSupportHeadrestStatus() {
        return isSupportVipSeat();
    }

    public boolean isSupportSeatRhythm() {
        return hasFeature(PROPERTY_RHYTHM);
    }

    public int[] getSleepRestSeatDrv() {
        return sleepRestSeatDrv;
    }

    public int[] getSleepRestSeatPsn() {
        return sleepRestSeatPsn;
    }

    public boolean isSupportDrvLeg() {
        return hasFeature(PROPERTY_DRV_SEAT_CUSHEXT_TILT);
    }

    public boolean isSeatDebug() {
        return hasFeature(PROPERTY_SEAT_DEBUG);
    }

    public boolean isSupportAtl() {
        return hasFeature(PROPERTY_ATLS) || hasFeature(PROPERTY_AMBIENT_SERVICE);
    }

    public boolean isSupportFullAtl() {
        return hasFeature(PROPERTY_ATLS) || hasFeature(PROPERTY_AMBIENT_SERVICE);
    }

    public boolean isSupportAiLlu() {
        return hasFeature(FO_AI_LLU);
    }

    public boolean isSupportLluPreview() {
        return isSupportLlu();
    }

    public boolean isSupportHvacDualTemp() {
        return getMultiTempArea() >= 2;
    }

    public boolean isSupportInnerPm25() {
        return hasFeature(PROPERTY_PM25);
    }

    public boolean isSupportSimpleSas() {
        return isSupportXpu();
    }

    public boolean isSupportAutoParkForXKey() {
        return isSupportAutoPark();
    }

    public boolean isSupportXNgp() {
        return isSupportCNgp();
    }

    public boolean isSupportNewNgpArch() {
        return isSupportNgp();
    }

    public boolean isSupportCwcFR() {
        return hasFeature(PROPERTY_CWC_FR);
    }

    public boolean isSupportCwcRL() {
        return hasFeature(PROPERTY_CWC_RL);
    }

    public boolean isSupportCwcRR() {
        return hasFeature(PROPERTY_CWC_RR);
    }

    public boolean isSupportMirrorPositionSignalSet() {
        return isSupportMirrorMemory() || isSupportMirrorDown();
    }

    public final boolean isSupportMirrorMemory() {
        return getCduMirrorReverseMode() == 1;
    }

    public boolean isSupportElecTail() {
        return hasFeature(PROPERTY_ARS);
    }

    public boolean isSupportDomeLightBrightnessCtrl() {
        return isSupportDomeLightIndependentCtrl();
    }

    public boolean isSupportArs() {
        return hasFeature(PROPERTY_ARS);
    }

    public boolean isSupportCarpetLightWelcomeMode() {
        return hasFeature(PROPERTY_CARPET_LIGHT);
    }

    public boolean isSupportVMCControl() {
        return hasFeature(PROPERTY_RWS);
    }

    public boolean isSupportXPedalNewArch() {
        return isSupportDriveModeNewArch();
    }

    public boolean isSupportSensorTrunk() {
        return hasFeature(PROPERTY_FKS_TRUNK);
    }

    public boolean isSupportPsnSrsSwitch() {
        return hasFeature(PROPERTY_PAB_SWITCH);
    }

    public boolean isSupportTrunkOverHeatingProtected() {
        return isSupportElcTrunk();
    }

    public int getMultiTempArea() {
        int parseInt;
        String str = SystemProperties.get(PROPERTY_MULTITEMPAREA, (String) null);
        if (!TextUtils.isEmpty(str)) {
            try {
                parseInt = Integer.parseInt(str);
            } catch (Exception e) {
                LogUtils.w(TAG, "can not getMultiTempArea error = " + e, false);
            }
            LogUtils.i(TAG, "getMultiTempArea: " + parseInt, false);
            return parseInt;
        }
        parseInt = 2;
        LogUtils.i(TAG, "getMultiTempArea: " + parseInt, false);
        return parseInt;
    }

    public boolean isSupportAmbientService() {
        return hasFeature(PROPERTY_AMBIENT_SERVICE);
    }

    public boolean isSupportSecRowMemory() {
        return hasFeature(PROPERTY_SECROW_MEMORY);
    }

    public boolean isSupportSecRowLegHorz() {
        return isSupportRearSeatLeg();
    }

    public boolean isSupportSecRowLegVer() {
        return hasFeature(PROPERTY_SECROW_LT_LEGVER) || hasFeature(PROPERTY_SECROW_RT_LEGVER);
    }

    public boolean isSupportSecRowHor() {
        return hasFeature(PROPERTY_SECROW_SEAT_HORZ);
    }

    public boolean isSupportSecRowHeadRest() {
        return hasFeature(PROPERTY_SECROW_HEADREST);
    }

    public boolean isSupportSecRowZeroGravity() {
        return hasFeature(PROPERTY_SECROW_ZEROGRAV);
    }

    public boolean isSupportSecRowLTSeatLumb() {
        return hasFeature(PROPERTY_SECROW_LT_LSU);
    }

    public boolean isSupportSecRowRTSeatLumb() {
        return hasFeature(PROPERTY_SECROW_RT_LSU);
    }

    public boolean isSupportTrdRowHeadRest() {
        return hasFeature(PROPERTY_TRDROW_HEADREST);
    }

    public boolean isSupportTrdRowMidHeadRest() {
        return hasFeature(PROPERTY_TRDROW_MID_HEADREST);
    }

    public boolean isSupportTrdRowSeatControl() {
        return hasFeature(PROPERTY_TRDROW_TILT);
    }

    public boolean isSupportTrdRowFold() {
        return hasFeature(PROPERTY_TRDROW_STOW);
    }

    public boolean isSupportTrdRowStow() {
        return hasFeature(PROPERTY_TRDROW_STOW);
    }

    public boolean isSupportAmp() {
        return hasFeature(PROPERTY_AMP);
    }

    public boolean isSupportAtlSpeakerControl() {
        return isSupportAmp();
    }
}
