package com.xiaopeng.carcontrol.config;

/* loaded from: classes.dex */
public class D55CarConfig extends DxCarConfig {
    private static final int CFC_CODE_HIGH = 4;
    private static final int CFC_CODE_INVALID = 0;
    private static final int CFC_CODE_LOW = 2;
    private static final int CFC_CODE_MIDDLE = 3;
    private static final int CFC_CODE_TRIP = 1;
    private static final int CFC_LEV_1 = 1;
    private static final int CFC_LEV_2 = 2;
    private static final int CFC_LEV_3 = 3;
    private static final int CFC_LEV_4 = 4;
    private static final int CFC_LEV_7 = 7;
    public static final int PROPERTY_BODY_COLOR_BLACK = 3;
    public static final int PROPERTY_BODY_COLOR_BLUE = 7;
    public static final int PROPERTY_BODY_COLOR_CHAMPAGNE = 11;
    public static final int PROPERTY_BODY_COLOR_COFFEE = 6;
    public static final int PROPERTY_BODY_COLOR_GRAY = 4;
    public static final int PROPERTY_BODY_COLOR_GREEN = 10;
    public static final int PROPERTY_BODY_COLOR_INVALID = 0;
    public static final int PROPERTY_BODY_COLOR_PURPLE = 8;
    public static final int PROPERTY_BODY_COLOR_RED_BLACK = 1;
    public static final int PROPERTY_BODY_COLOR_SILVER = 5;
    public static final int PROPERTY_BODY_COLOR_WHITE = 2;
    public static final int PROPERTY_BODY_COLOR_WHITE_BLACK = 12;
    private static final String PROPERTY_CFC = "persist.sys.xiaopeng.cfcVehicleLevel";
    public static final int onPROPERTY_BODY_COLOR_YELLOW = 9;

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public float getEpsSpdThreshold() {
        return 150.0f;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public float getEpsTorsionBarThreshold() {
        return 8.0f;
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
    public float getSeatTiltAngleFactor() {
        return 0.9f;
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

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isNewVcuArch() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSelfDevelopedHvac() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSopStage() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoCloseWin() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoWindowLock() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
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

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCiuConfig() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCloseAutoDirect() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCrossBarriers() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCtrlBonnet() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDhc() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
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

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrl() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEbw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEpsTorque() {
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
    public boolean isSupportInductionLock() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIsla() {
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

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLightMeHomeNew() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLightMeHomeTime() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMainWiperInterval() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMaintenanceInfo() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMcuKeyOpenFailed() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
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
    public boolean isSupportMsmPVerticalMove() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNewParkLampFmB() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNewWiperSpdSt() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPM25Out() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatVent() {
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
    public boolean isSupportSfsSw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportShadeInitCloseConstraint() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportShowDriveAutoLock() {
        return true;
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
        return true;
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

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportTurnActive() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportVcuExhibitionMode() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWindowInitFailed() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWindowStates() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWiperInterval() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWiperRepair() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWiperSenCfg() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXNgp() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPedal() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXPilotSafeExam() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXUnlockTrunk() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int matchSeatSetPos(int pos) {
        if (pos <= 0) {
            return 1;
        }
        return pos;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public D55CarConfig() {
        sIsD2xSeries = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001a  */
    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getCfcCode() {
        /*
            r4 = this;
            java.lang.Integer r0 = com.xiaopeng.carcontrol.config.D55CarConfig.sCfcCode
            if (r0 != 0) goto L3b
            r0 = 0
            java.lang.String r1 = "persist.sys.xiaopeng.cfcVehicleLevel"
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
            r0 = 2
        L1b:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            com.xiaopeng.carcontrol.config.D55CarConfig.sCfcCode = r0
            java.lang.String r0 = com.xiaopeng.carcontrol.config.D55CarConfig.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "getCfcCode: "
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.Integer r3 = com.xiaopeng.carcontrol.config.D55CarConfig.sCfcCode
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            com.xiaopeng.carcontrol.util.LogUtils.i(r0, r1, r2)
        L3b:
            java.lang.Integer r0 = com.xiaopeng.carcontrol.config.D55CarConfig.sCfcCode
            int r0 = r0.intValue()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.config.D55CarConfig.getCfcCode():int");
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig
    public boolean isLowConfig() {
        return getCfcCode() == 2;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig
    public boolean isMiddleConfig() {
        return getCfcCode() == 3;
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig
    public boolean isHighConfig() {
        return getCfcCode() == 4;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAtl() {
        return hasFeature(CarBaseConfig.PROPERTY_ATLS);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLlu() {
        return hasFeature(CarBaseConfig.PROPERTY_LLU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEsb() {
        return hasFeature(CarBaseConfig.PROPERTY_MSB);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportAutoPark() {
        return getCfcCode() >= 3 && getCfcCode() != 7;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCwc() {
        return hasFeature(CarBaseConfig.PROPERTY_CWC);
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
    public boolean isSupportSfs() {
        return hasFeature(CarBaseConfig.PROPERTY_SFS);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSrrMiss() {
        return hasFeature(CarBaseConfig.PROPERTY_MRR_SRR);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportActiveSafety() {
        return hasFeature(CarBaseConfig.PROPERTY_SCU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportBsd() {
        return isSupportActiveSafety();
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
        return isSupportActiveSafety();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLka() {
        return hasFeature(CarBaseConfig.PROPERTY_SCU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportElk() {
        return hasFeature(CarBaseConfig.PROPERTY_SCU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportShowAutoPark() {
        return hasFeature(CarBaseConfig.PROPERTY_SCU);
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

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXpu() {
        return hasFeature(CarBaseConfig.PROPERTY_XPU);
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNgp() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportCNgp() {
        return isSupportLidar();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXpuNedc() {
        return hasFeature(CarBaseConfig.PROPERTY_XPU);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportNra() {
        return hasFeature(CarBaseConfig.PROPERTY_AVM);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLidar() {
        return hasFeature(CarBaseConfig.PROPERTY_LIDAR_L) || hasFeature(CarBaseConfig.PROPERTY_LIDAR_R);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportEnhancedLidarFunc() {
        return isSupportLidar();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportIhb() {
        return isSupportXpu();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportSeatCtrl() {
        return hasFeature(CarBaseConfig.PROPERTY_MSMD_ADJ) || hasFeature(CarBaseConfig.PROPERTY_MSMP_ADJ);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportVipSeat() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1) && hasFeature(CarBaseConfig.PROPERTY_MSMD);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatControl() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMsmD() {
        return hasFeature(CarBaseConfig.PROPERTY_MSMD);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMsmP() {
        return hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1);
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportWelcomeMode() {
        return hasFeature(CarBaseConfig.PROPERTY_MSMD);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public int[] getMSMSeatDefaultPos() {
        return MSM_SEAT_DEFAULT_POS_D55;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportDrvSeatStop() {
        return isSupportMsmD();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportPsnSeatStop() {
        return isSupportMsmP();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportMirrorHeat() {
        return hasFeature(CarBaseConfig.PROPERTY_MIRROR);
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isWiperSensitiveNegative() {
        int cfcCode = getCfcCode();
        return cfcCode == 3 || cfcCode == 4 || !hasFeature(CarBaseConfig.PROPERTY_RLS);
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public int[] getWindowVentPos() {
        return new int[]{90, 90, 90, 90};
    }

    @Override // com.xiaopeng.carcontrol.config.DxCarConfig, com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportXSayHi() {
        return getCfcCode() >= 2;
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportBossKey() {
        return isSupportVipSeat();
    }

    @Override // com.xiaopeng.carcontrol.config.CarBaseConfig
    public boolean isSupportLluDance() {
        return isSupportLlu();
    }
}
