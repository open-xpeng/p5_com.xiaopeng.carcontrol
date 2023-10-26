package com.xiaopeng.carcontrol.config.feature;

import android.os.SystemProperties;
import com.xiaopeng.appstore.storeprovider.AssembleRequest;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;

/* loaded from: classes2.dex */
public abstract class BaseFeatureOption {
    static final String PROPERTY_LLU_SYNC_SHOW = "persist.sys.xiaopeng.llu.SYNC";
    static final String PROPERTY_UNITY_DELAY_TIME = "persist.sys.xiaopeng.carcontrol.unity.DELAY_TIME";
    public static final String TAG = "BaseFeatureOption";

    public boolean IsSupportGeekMode() {
        return false;
    }

    public boolean IsSupportXpowerMode() {
        return false;
    }

    public int getSayHiPreviewInactiveTime() {
        return 2000;
    }

    public int getSeatTiltMovingSafePos() {
        return 0;
    }

    public long getWindowLoadingTime() {
        return 0L;
    }

    public long getWindowVentingTime() {
        return 0L;
    }

    public boolean isAcAutoNature() {
        return false;
    }

    public boolean isDelaySaveMirrorPos() {
        return true;
    }

    public boolean isDrvRightVentOnTop() {
        return false;
    }

    public boolean isEcuDoSeatLayFlat() {
        return false;
    }

    public boolean isForceCloseAlcWhenIgOn() {
        return false;
    }

    public boolean isForceTurnOnElkWhenIgOn() {
        return false;
    }

    public boolean isHvacDataMemoryFromRDCU() {
        return false;
    }

    public boolean isIhbDependOnXPilot() {
        return false;
    }

    public boolean isMeditationIndependent() {
        return false;
    }

    public boolean isNewFcwSignal() {
        return true;
    }

    public boolean isNewLssSignal() {
        return false;
    }

    public boolean isOldAiAssistant() {
        return false;
    }

    public boolean isSeatHeatVentGather() {
        return true;
    }

    public boolean isShowDoorKeySpeech() {
        return true;
    }

    public boolean isShowMainControlBtnStatus() {
        return true;
    }

    public boolean isShowReadyDisableCustomXDialog() {
        return false;
    }

    public boolean isShowSeatRealPosition() {
        return true;
    }

    public boolean isShowXkeyNraView() {
        return false;
    }

    public abstract boolean isSignalRegisterDynamically();

    public boolean isSupportAirBed() {
        return false;
    }

    public boolean isSupportAllWindowState() {
        return false;
    }

    public boolean isSupportAsHeightSaveMode() {
        return false;
    }

    public boolean isSupportAsWelcomeWithSeat() {
        return false;
    }

    public boolean isSupportAvasNewMemKey() {
        return false;
    }

    public boolean isSupportAvasOff() {
        return true;
    }

    public abstract boolean isSupportCallAvatar();

    public boolean isSupportCarControlSDK() {
        return false;
    }

    public boolean isSupportCarLife() {
        return false;
    }

    public abstract boolean isSupportCarStatusBIUpload();

    public boolean isSupportCenteringHvacSurfaceView() {
        return false;
    }

    public boolean isSupportChangeableDialogTitleSize() {
        return false;
    }

    public boolean isSupportChargePortOnOffConstraint() {
        return false;
    }

    public boolean isSupportChargePortPGearConstrain() {
        return true;
    }

    public boolean isSupportDomainControllerSeatWelcome() {
        return false;
    }

    public abstract boolean isSupportDriveAutoLock();

    public abstract boolean isSupportDriveInSettingsPage();

    public boolean isSupportDriveModeList() {
        return false;
    }

    public boolean isSupportDsmRegulation() {
        return false;
    }

    public boolean isSupportDualScreen() {
        return false;
    }

    public boolean isSupportECall() {
        return false;
    }

    public boolean isSupportEcoDriveMode() {
        return false;
    }

    public boolean isSupportEnhancedParkFunc() {
        return false;
    }

    public boolean isSupportEspFeedback() {
        return false;
    }

    public boolean isSupportFollowMeHomeWhenRemoteInOn() {
        return false;
    }

    public boolean isSupportForceOpenDsm() {
        return false;
    }

    public boolean isSupportFullscreenPanel() {
        return false;
    }

    public boolean isSupportGeoFence() {
        return false;
    }

    public boolean isSupportGuiPageOpen() {
        return true;
    }

    public boolean isSupportHvacColdHeatNature() {
        return false;
    }

    public boolean isSupportHvacPsnSeatRecovery() {
        return false;
    }

    public boolean isSupportHvacShowAuto() {
        return false;
    }

    public boolean isSupportHvacVentControl() {
        return false;
    }

    public boolean isSupportIhbInSettings() {
        return false;
    }

    public abstract boolean isSupportInflateTwice();

    public boolean isSupportIntellDriveSysSelfCheck() {
        return true;
    }

    public boolean isSupportInterTransportMode() {
        return false;
    }

    public boolean isSupportIpcModule() {
        return false;
    }

    public boolean isSupportIslaMemoryFunc() {
        return false;
    }

    public abstract boolean isSupportLeftCarImgChange();

    public boolean isSupportLidarSafeExam() {
        return false;
    }

    public abstract boolean isSupportLightMeHomeInSettings();

    public boolean isSupportLluAllOn() {
        return true;
    }

    public abstract boolean isSupportLluSyncShow();

    public boolean isSupportLluVideo() {
        return false;
    }

    public boolean isSupportLogoPlate() {
        return false;
    }

    public boolean isSupportLongItemXPilotTab() {
        return false;
    }

    public boolean isSupportLowBeamOffConfirm() {
        return false;
    }

    public boolean isSupportMeditationPlus() {
        return false;
    }

    public boolean isSupportMirrorAutoFoldOff() {
        return false;
    }

    public boolean isSupportMirrorFoldUnfoldFuzzySpeech() {
        return true;
    }

    public abstract boolean isSupportMirrorMemoryInSettings();

    public boolean isSupportMultipleUserDefineDriveMode() {
        return false;
    }

    public boolean isSupportNapa() {
        return false;
    }

    public abstract boolean isSupportNeedReceiveGreet();

    public boolean isSupportNewPsnSaveHabbitPos() {
        return false;
    }

    public abstract boolean isSupportNewSelfCheckArch();

    public boolean isSupportNotifyNormalCheckResult() {
        return false;
    }

    public boolean isSupportOpenAlcWhenNotInPGear() {
        return false;
    }

    public boolean isSupportPopPanel() {
        return false;
    }

    public boolean isSupportPressSignal() {
        return true;
    }

    public boolean isSupportPsnSeatManualSave() {
        return false;
    }

    public boolean isSupportPsnSeatPosCallback() {
        return false;
    }

    public boolean isSupportPsnSeatVerControl() {
        return false;
    }

    public boolean isSupportPsnThreeSavedPos() {
        return false;
    }

    public boolean isSupportRearSeatFlat() {
        return true;
    }

    public boolean isSupportRemainingMileageInSleepMode() {
        return true;
    }

    public boolean isSupportRsbWarningReset() {
        return false;
    }

    public boolean isSupportSdcNarrowSpaceTips() {
        return true;
    }

    public boolean isSupportSeatFlatFrontOccupied() {
        return true;
    }

    public boolean isSupportSeatSectionalRecovery() {
        return false;
    }

    public boolean isSupportSelfCheck() {
        return true;
    }

    public boolean isSupportShowAdaptiveDriveMode() {
        return false;
    }

    public boolean isSupportShowAvasSwitch() {
        return true;
    }

    public boolean isSupportShowCustomDialogStyle() {
        return false;
    }

    public boolean isSupportShowDriveModeInfo() {
        return false;
    }

    public boolean isSupportShowSelfCheckCard() {
        return true;
    }

    public boolean isSupportShowUserBook() {
        return true;
    }

    public boolean isSupportSingleMode() {
        return false;
    }

    public boolean isSupportSmartModeCountdown() {
        return true;
    }

    public boolean isSupportSmartOSFive() {
        return false;
    }

    public boolean isSupportStandardDriveMode() {
        return false;
    }

    public boolean isSupportTiltPosSavedReversed() {
        return false;
    }

    public boolean isSupportTpmsWarning() {
        return false;
    }

    public boolean isSupportUnlockResponseSettings() {
        return true;
    }

    public boolean isSupportVui() {
        return false;
    }

    public boolean isSupportVuiOpenCarControl() {
        return false;
    }

    public boolean isSupportWelcomeSwitchOff() {
        return false;
    }

    public abstract boolean isSupportWiperSenCfgShow();

    public boolean isSupportXSportApp() {
        return false;
    }

    public boolean isSupportXSportRacerMode() {
        return false;
    }

    public boolean needCheckValidMirrorPos() {
        return false;
    }

    public boolean shouldForceTurnOnAvhForXpedal() {
        return false;
    }

    public abstract boolean shouldIgnoreDrvOccupied();

    public boolean shouldRecoverMirror() {
        return false;
    }

    public boolean showXPilotStAsTitle() {
        return false;
    }

    static /* synthetic */ BaseFeatureOption access$000() {
        return createFeatureOption();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingletonHolder {
        private static final BaseFeatureOption sInstance = BaseFeatureOption.access$000();

        private SingletonHolder() {
        }
    }

    public static BaseFeatureOption getInstance() {
        return SingletonHolder.sInstance;
    }

    private static BaseFeatureOption createFeatureOption() {
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
                return CarStatusUtils.isEURegion() ? new E28VFeatureOption() : new E28FeatureOption();
            case 1:
                return CarStatusUtils.isEURegion() ? new D21VFeatureOption() : new D21FeatureOption();
            case 2:
                return CarStatusUtils.isEURegion() ? new D55VFeatureOption() : new D55FeatureOption();
            case 3:
                return CarStatusUtils.isEURegion() ? new D22VFeatureOption() : new D22FeatureOption();
            case 4:
                return CarStatusUtils.isEURegion() ? new E38VFeatureOption() : new E38FeatureOption();
            case 5:
                return CarStatusUtils.isEURegion() ? new E28AVFeatureOption() : new E28AFeatureOption();
            case 6:
                return new F30FeatureOption();
            case 7:
                return new H93FeatureOption();
            default:
                return new D21FeatureOption();
        }
    }

    public boolean isSupportLowPowerProtect() {
        return isSupportEcoDriveMode();
    }

    public long getUnityDelayTime() {
        return SystemProperties.getInt(PROPERTY_UNITY_DELAY_TIME, (int) AssembleRequest.ASSEMBLE_ACTION_CANCEL);
    }

    public boolean isBroadcastWhenCancelMuteOrUnmute() {
        return CarStatusUtils.isEURegion();
    }

    public int getAlcSpeed() {
        return CarStatusUtils.isEURegion() ? 65 : 45;
    }

    public boolean isSupportCduWelcomeOff() {
        return !isSupportDomainControllerSeatWelcome();
    }

    public boolean isSupportSdcBrakeDoorFunc() {
        return CarBaseConfig.getInstance().isSupportSdc();
    }

    public boolean isSupportThemeBootEffect() {
        return isSupportNapa();
    }

    public int[] getDefaultMirrorReversePos() {
        return new int[]{82, 27, 30, 15};
    }

    public boolean isSupportThousandSeparator() {
        return CarStatusUtils.isEURegion();
    }

    public boolean isMileageEuFormat() {
        return CarStatusUtils.isEURegion();
    }

    public boolean isSupportLluSayHi() {
        return CarBaseConfig.getInstance().isSupportLlu();
    }
}
