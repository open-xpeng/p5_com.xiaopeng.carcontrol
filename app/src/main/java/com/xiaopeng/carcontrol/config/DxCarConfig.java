package com.xiaopeng.carcontrol.config;

import android.os.SystemProperties;
import com.xiaopeng.speech.vui.utils.VuiUtils;

/* loaded from: classes.dex */
public abstract class DxCarConfig extends CarBaseConfig {
    static final String PROPERTY_CFC = "persist.sys.xiaopeng.configCode";
    private static final String PROPERTY_NOT_SUPPORT = "0";
    private static final String PROPERTY_SUPPORT = "1";
    protected static Boolean sCiuConfig;
    protected static Boolean sIsD2xSeries;

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isFollowVehicleLostConfigUseNew() {
        return false;
    }

    public abstract boolean isHighConfig();

    public abstract boolean isLowConfig();

    public abstract boolean isMiddleConfig();

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isNewEspArch() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isNewVcuArch() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAirSuspension() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoWindowLock() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAuxiliaryFunc() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAvasLoudSpeaker() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAwd() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportBcmCtrlSeatBelt() {
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
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCtrlBonnet() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCtrlChargePort() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDigitalKeyTip() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDomeLight() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrl() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEbw() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEnergyRecycleMediumLevel() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEpbSetting() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEpbWarning() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEpsTorque() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEspMudMode() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportHdc() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIntelligentDesiccation() {
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
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMcuSeatWelcome() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNeutralGearProtect() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNewWiperSpdSt() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNfc() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNormalDriveMode() {
        return false;
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
    public boolean isSupportRearSeatHeat() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportRearWiper() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSdc() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSensorTrunk() {
        return false;
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
        return false;
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
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportVcuExhibitionNewVcuArch() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWelcomeMode() {
        return true;
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
    public boolean isSupportWindowAutoCtrl() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWindowStates() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWiperInterval() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWiperSenCfg() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPilotTtsCfg() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXSayHi() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXUnlockTrunk() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXpu() {
        return false;
    }

    public static DxCarConfig getInstance() {
        return (DxCarConfig) CarBaseConfig.getInstance();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001a  */
    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getCfcCode() {
        /*
            r4 = this;
            java.lang.Integer r0 = com.xiaopeng.carcontrol.config.DxCarConfig.sCfcCode
            if (r0 != 0) goto L3b
            r0 = 0
            java.lang.String r1 = "persist.sys.xiaopeng.configCode"
            java.lang.String r0 = android.os.SystemProperties.get(r1, r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r2 = 0
            if (r1 == 0) goto L14
        L12:
            r0 = r2
            goto L18
        L14:
            int r0 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.Exception -> L12
        L18:
            if (r0 != 0) goto L1b
            r0 = 1
        L1b:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            com.xiaopeng.carcontrol.config.DxCarConfig.sCfcCode = r0
            java.lang.String r0 = com.xiaopeng.carcontrol.config.DxCarConfig.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "getCfcCode: "
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.Integer r3 = com.xiaopeng.carcontrol.config.DxCarConfig.sCfcCode
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            com.xiaopeng.carcontrol.util.LogUtils.i(r0, r1, r2)
        L3b:
            java.lang.Integer r0 = com.xiaopeng.carcontrol.config.DxCarConfig.sCfcCode
            int r0 = r0.intValue()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.config.DxCarConfig.getCfcCode():int");
    }

    public static boolean isD2xSeries() {
        return sIsD2xSeries.booleanValue();
    }

    public static int getBodyColor() {
        return SystemProperties.getInt("persist.sys.xiaopeng.bodyColor", 0);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCiuConfig() {
        if (sCiuConfig == null) {
            int cfcCode = getCfcCode();
            sCiuConfig = Boolean.valueOf(VuiUtils.CAR_PLATFORM_Q5.equals(getCarCduType()) && (cfcCode == 3 || cfcCode == 2));
        }
        return sCiuConfig.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDsmCamera() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int[] getWindowVentPos() {
        return new int[]{-1, -1, -1, -1};
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNgp() {
        return isSupportXpu();
    }
}
