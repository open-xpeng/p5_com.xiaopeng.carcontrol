package com.xiaopeng.carcontrol.statistic;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.CarControlService;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.audio.AudioViewModel;
import com.xiaopeng.carcontrol.viewmodel.audio.IAudioViewModel;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasEffect;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasViewModel;
import com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.MirrorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.UnlockResponse;
import com.xiaopeng.carcontrol.viewmodel.chassis.ChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampMode;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LightMeHomeTime;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluSayHiEffect;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel;
import com.xiaopeng.carcontrol.viewmodel.light.AtlColor;
import com.xiaopeng.carcontrol.viewmodel.light.AtlEffect;
import com.xiaopeng.carcontrol.viewmodel.light.AtlViewModel;
import com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.DoorKeyForCustomer;
import com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.MeterViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.XKeyForCustomer;
import com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.DriveMode;
import com.xiaopeng.carcontrol.viewmodel.vcu.EnergyRecycleGrade;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.XpuViewModel;
import java.text.SimpleDateFormat;

/* loaded from: classes2.dex */
public class CarStatusUploadHelper {
    private static final int ATL_MANUAL_BRIGHTNESS_MAX = 100;
    private static final int ATL_MANUAL_BRIGHTNESS_MIN = 10;
    private static final String TAG = "CarStatusUploadHelper";
    private AtlViewModel mAtlVm;
    private AudioViewModel mAudioVm;
    private AvasViewModel mAvasVm;
    private CarBodyViewModel mCarBodyVm;
    private ChassisViewModel mChassisVm;
    private LampViewModel mLampVm;
    private LluViewModel mLluVm;
    private MeterViewModel mMeterVm;
    private MirrorViewModel mMirrorVm;
    private ScuViewModel mScuVm;
    private SeatViewModel mSeatVm;
    private VcuViewModel mVcuVm;
    private WindowDoorViewModel mWindowDoorVm;
    private XpuViewModel mXpuVm;

    /* synthetic */ CarStatusUploadHelper(AnonymousClass1 anonymousClass1) {
        this();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingletonHolder {
        private static final CarStatusUploadHelper sInstance = new CarStatusUploadHelper(null);

        private SingletonHolder() {
        }
    }

    public static CarStatusUploadHelper getInstance() {
        return SingletonHolder.sInstance;
    }

    private CarStatusUploadHelper() {
    }

    public void startToUploadResumeStatus(Context context) {
        Intent intent = new Intent(context, CarControlService.class);
        intent.setAction(GlobalConstant.ACTION.ACTION_UPLOAD_CAR_STATUS_BI);
        context.startService(intent);
    }

    public void uploadResumeStatus() {
        if (hasUpload()) {
            return;
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.statistic.-$$Lambda$CarStatusUploadHelper$vP_rx9bsWnyFjC41yZgdHkao2vI
            @Override // java.lang.Runnable
            public final void run() {
                CarStatusUploadHelper.this.lambda$uploadResumeStatus$0$CarStatusUploadHelper();
            }
        });
    }

    public /* synthetic */ void lambda$uploadResumeStatus$0$CarStatusUploadHelper() {
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            init();
            if (BaseFeatureOption.getInstance().isSupportNapa()) {
                uploadCarStatusBIForNapa();
            } else {
                uploadCarStatusBI();
            }
            FunctionModel.getInstance().setUploadResumeBITime(System.currentTimeMillis());
        }
    }

    private boolean hasUpload() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(Long.valueOf(FunctionModel.getInstance().getUploadResumeBITime()));
        String format2 = simpleDateFormat.format(Long.valueOf(System.currentTimeMillis()));
        LogUtils.i(TAG, "uploadResumeStatus: lastUploadDayUploadDay=" + format + ", currentDay=" + format2, false);
        return TextUtils.equals(format, format2);
    }

    private void init() {
        ViewModelManager viewModelManager = ViewModelManager.getInstance();
        this.mVcuVm = (VcuViewModel) viewModelManager.getViewModelImpl(IVcuViewModel.class);
        this.mCarBodyVm = (CarBodyViewModel) viewModelManager.getViewModelImpl(ICarBodyViewModel.class);
        this.mMirrorVm = (MirrorViewModel) viewModelManager.getViewModelImpl(IMirrorViewModel.class);
        this.mLampVm = (LampViewModel) viewModelManager.getViewModelImpl(ILampViewModel.class);
        if (CarBaseConfig.getInstance().isSupportLlu()) {
            this.mLluVm = (LluViewModel) viewModelManager.getViewModelImpl(ILluViewModel.class);
        }
        this.mAtlVm = (AtlViewModel) viewModelManager.getViewModelImpl(IAtlViewModel.class);
        this.mWindowDoorVm = (WindowDoorViewModel) viewModelManager.getViewModelImpl(IWindowDoorViewModel.class);
        this.mSeatVm = (SeatViewModel) viewModelManager.getViewModelImpl(ISeatViewModel.class);
        this.mAvasVm = (AvasViewModel) viewModelManager.getViewModelImpl(IAvasViewModel.class);
        this.mChassisVm = (ChassisViewModel) viewModelManager.getViewModelImpl(IChassisViewModel.class);
        this.mMeterVm = (MeterViewModel) viewModelManager.getViewModelImpl(IMeterViewModel.class);
        if (BaseFeatureOption.getInstance().isSupportNapa()) {
            this.mScuVm = (ScuViewModel) viewModelManager.getViewModelImpl(IScuViewModel.class);
            this.mXpuVm = (XpuViewModel) viewModelManager.getViewModelImpl(IXpuViewModel.class);
            this.mAudioVm = (AudioViewModel) viewModelManager.getViewModelImpl(IAudioViewModel.class);
        }
    }

    private void uploadCarStatusBI() {
        uploadMainControlStatus();
        uploadLampControlStatus();
        uploadAtlControlStatus();
        uploadSettingsControlStatus();
        uploadSituationControlStatus();
    }

    private void uploadCarStatusBIForNapa() {
        uploadNapaXPilotStatus();
        uploadNapaChildStatus();
        uploadNapaSettingStatus();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void uploadMainControlStatus() {
        /*
            Method dump skipped, instructions count: 275
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.statistic.CarStatusUploadHelper.uploadMainControlStatus():void");
    }

    private void uploadLampControlStatus() {
        int i;
        LampMode headLampMode = this.mLampVm.getHeadLampMode(LampMode.Auto);
        int i2 = 4;
        if (headLampMode != null) {
            int i3 = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[headLampMode.ordinal()];
            i = i3 != 1 ? i3 != 2 ? i3 != 3 ? 4 : 3 : 2 : 1;
        } else {
            i = 0;
        }
        StatisticUtils.sendCarStatusStatistic(PageEnum.LAMP_PAGE, BtnEnum.LAMP_PAGE_HEAD_LAMP_BTN, Integer.valueOf(i));
        StatisticUtils.sendCarStatusStatistic(PageEnum.LAMP_PAGE, BtnEnum.LAMP_PAGE_FOG_LAMP_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mLampVm.getRearFogLampState())));
        if (this.mLampVm.isLightMeHomeEnable()) {
            int i4 = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime[this.mLampVm.getLightMeTime().ordinal()];
            if (i4 == 1) {
                i2 = 3;
            } else if (i4 != 2) {
                i2 = 2;
            }
        } else {
            i2 = 1;
        }
        StatisticUtils.sendCarStatusStatistic(PageEnum.LAMP_PAGE, BtnEnum.LAMP_PAGE_HOME_LAMP_BTN, Integer.valueOf(i2));
        if (CarBaseConfig.getInstance().isSupportLlu()) {
            PageEnum pageEnum = PageEnum.LAMP_PAGE;
            BtnEnum btnEnum = BtnEnum.LAMP_PAGE_SIDE_LIGHT_MODE_BTN;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(this.mLampVm.isParkLampIncludeFmB() ? 2 : 1);
            StatisticUtils.sendCarStatusStatistic(pageEnum, btnEnum, objArr);
            uploadLluControlStatus();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0070, code lost:
        if (r0 != 3) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void uploadLluControlStatus() {
        /*
            r7 = this;
            com.xiaopeng.carcontrol.statistic.PageEnum r0 = com.xiaopeng.carcontrol.statistic.PageEnum.LAMP_PAGE
            com.xiaopeng.carcontrol.statistic.BtnEnum r1 = com.xiaopeng.carcontrol.statistic.BtnEnum.LLU_SW
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel r4 = r7.mLluVm
            boolean r4 = r4.isLLuSwEnabled()
            int r4 = com.xiaopeng.carcontrol.statistic.StatisticUtils.getSwitchOnOff(r4)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r5 = 0
            r3[r5] = r4
            com.xiaopeng.carcontrol.statistic.StatisticUtils.sendCarStatusStatistic(r0, r1, r3)
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r0 = r0.isSupportLluPreview()
            if (r0 != 0) goto L26
            return
        L26:
            com.xiaopeng.carcontrol.statistic.PageEnum r0 = com.xiaopeng.carcontrol.statistic.PageEnum.LAMP_PAGE
            com.xiaopeng.carcontrol.statistic.BtnEnum r1 = com.xiaopeng.carcontrol.statistic.BtnEnum.LAMP_PAGE_DANCE_AUTO_VOLUME_BTN
            java.lang.Object[] r3 = new java.lang.Object[r2]
            boolean r4 = com.xiaopeng.carcontrol.model.SharedPreferenceUtil.getLluAutoVolume()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r5] = r4
            com.xiaopeng.carcontrol.statistic.StatisticUtils.sendCarStatusStatistic(r0, r1, r3)
            com.xiaopeng.carcontrol.statistic.PageEnum r0 = com.xiaopeng.carcontrol.statistic.PageEnum.LAMP_PAGE
            com.xiaopeng.carcontrol.statistic.BtnEnum r1 = com.xiaopeng.carcontrol.statistic.BtnEnum.LAMP_PAGE_DANCE_AUTO_WINDOW_BTN
            java.lang.Object[] r3 = new java.lang.Object[r2]
            boolean r4 = com.xiaopeng.carcontrol.model.SharedPreferenceUtil.getLluAutoWindow()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r5] = r4
            com.xiaopeng.carcontrol.statistic.StatisticUtils.sendCarStatusStatistic(r0, r1, r3)
            com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel r0 = r7.mLluVm
            com.xiaopeng.carcontrol.viewmodel.lamp.LluEffect r0 = r0.getRunningLluEffect()
            com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel r1 = r7.mLluVm
            com.xiaopeng.carcontrol.viewmodel.lamp.LluEffect r3 = com.xiaopeng.carcontrol.viewmodel.lamp.LluEffect.SayHi
            int r1 = r1.getLluEffect(r3)
            com.xiaopeng.carcontrol.viewmodel.lamp.LluSayHiEffect r1 = com.xiaopeng.carcontrol.viewmodel.lamp.LluSayHiEffect.fromLluValue(r1)
            com.xiaopeng.carcontrol.viewmodel.lamp.LluEffect r3 = com.xiaopeng.carcontrol.viewmodel.lamp.LluEffect.SayHi
            r4 = 3
            r6 = 2
            if (r0 != r3) goto L77
            int[] r0 = com.xiaopeng.carcontrol.statistic.CarStatusUploadHelper.AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect
            int r1 = r1.ordinal()
            r0 = r0[r1]
            if (r0 == r2) goto L75
            if (r0 == r6) goto L73
            if (r0 == r4) goto L78
            goto L77
        L73:
            r4 = r6
            goto L78
        L75:
            r4 = r2
            goto L78
        L77:
            r4 = r5
        L78:
            com.xiaopeng.carcontrol.statistic.PageEnum r0 = com.xiaopeng.carcontrol.statistic.PageEnum.LAMP_PAGE
            com.xiaopeng.carcontrol.statistic.BtnEnum r1 = com.xiaopeng.carcontrol.statistic.BtnEnum.LAMP_PAGE_SAYHI_SETTING_BTN
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r5] = r4
            com.xiaopeng.carcontrol.statistic.StatisticUtils.sendCarStatusStatistic(r0, r1, r3)
            com.xiaopeng.carcontrol.statistic.PageEnum r0 = com.xiaopeng.carcontrol.statistic.PageEnum.LAMP_PAGE
            com.xiaopeng.carcontrol.statistic.BtnEnum r1 = com.xiaopeng.carcontrol.statistic.BtnEnum.LAMP_PAGE_SAYHI_AUTO_PALY_BTN
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel r3 = r7.mLluVm
            boolean r3 = r3.isSayHiEnabled()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r2[r5] = r3
            com.xiaopeng.carcontrol.statistic.StatisticUtils.sendCarStatusStatistic(r0, r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.statistic.CarStatusUploadHelper.uploadLluControlStatus():void");
    }

    private void uploadAtlControlStatus() {
        int atlSingleColor;
        StatisticUtils.sendCarStatusStatistic(PageEnum.ATL_PAGE, BtnEnum.ATL_POWER_BTN, Integer.valueOf(!this.mAtlVm.isAtlEnabled()));
        int atlBrightnessValue = this.mAtlVm.getAtlBrightnessValue();
        if (atlBrightnessValue > 100) {
            atlBrightnessValue = 100;
        } else if (atlBrightnessValue < 10) {
            atlBrightnessValue = 10;
        }
        StatisticUtils.sendCarStatusStatistic(PageEnum.ATL_PAGE, BtnEnum.ATL_LIGHT_SLIDE, Integer.valueOf(atlBrightnessValue));
        boolean atlThemeColorMode = this.mAtlVm.getAtlThemeColorMode();
        StatisticUtils.sendCarStatusStatistic(PageEnum.ATL_PAGE, BtnEnum.ATL_NONE_COLOR_MODE, Integer.valueOf(atlThemeColorMode ? 1 : 0));
        StatisticUtils.sendCarStatusStatistic(PageEnum.ATL_PAGE, BtnEnum.ATL_BREATH_COLOR_MODE, Integer.valueOf(atlThemeColorMode ? 1 : 0));
        AtlEffect atlEffect = this.mAtlVm.getAtlEffect(AtlEffect.None);
        if (atlThemeColorMode) {
            int atlCmd = AtlColor.fromAtlStatus(this.mAtlVm.getAtlDualFirstColor()).toAtlCmd();
            int atlCmd2 = AtlColor.fromAtlStatus(this.mAtlVm.getAtlDualSecondColor()).toAtlCmd();
            atlSingleColor = 5;
            if (atlCmd >= 1 && atlCmd <= 20 && atlCmd2 >= 1 && atlCmd2 <= 20) {
                if (atlCmd == 1 && atlCmd2 == 6) {
                    atlSingleColor = 1;
                } else if (atlCmd == 2 && atlCmd2 == 5) {
                    atlSingleColor = 2;
                } else if (atlCmd == 5 && atlCmd2 == 11) {
                    atlSingleColor = 3;
                } else if (atlCmd == 14 && atlCmd2 == 6) {
                    atlSingleColor = 4;
                } else if (atlCmd != 15 || atlCmd2 != 11) {
                    if (atlCmd == 15 && atlCmd2 == 18) {
                        atlSingleColor = 6;
                    } else if (atlCmd == 17 && atlCmd2 == 20) {
                        atlSingleColor = 7;
                    }
                }
            }
            atlSingleColor = 0;
        } else {
            atlSingleColor = this.mAtlVm.getAtlSingleColor();
        }
        PageEnum pageEnum = PageEnum.ATL_PAGE;
        BtnEnum btnEnum = BtnEnum.ATL_EFFECT_MODE_SUMMARY;
        Object[] objArr = new Object[3];
        objArr[0] = Integer.valueOf(atlThemeColorMode ? 2 : 1);
        objArr[1] = Integer.valueOf(AtlEffect.toStatistic(atlEffect));
        objArr[2] = Integer.valueOf(atlSingleColor);
        StatisticUtils.sendCarStatusStatistic(pageEnum, btnEnum, objArr);
    }

    private void uploadSettingsControlStatus() {
        int i;
        int lowSpdEffect;
        int unlockResponse = this.mCarBodyVm.getUnlockResponse();
        int i2 = 2;
        if (unlockResponse == UnlockResponse.LightAndHorn.toBcmCmd()) {
            i = 2;
        } else {
            i = unlockResponse == UnlockResponse.LightAndAvas.toBcmCmd() ? 3 : 1;
        }
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_WIN_LOCK_PAGE, BtnEnum.SETTING_WIN_LOCK_PAGE_LOCK_RESPONSE_BTN, Integer.valueOf(i));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_WIN_LOCK_PAGE, BtnEnum.SETTING_WIN_LOCK_PAGE_DOOR_HANDLE_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mWindowDoorVm.isAutoDoorHandleEnabled())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_WIN_LOCK_PAGE, BtnEnum.SETTING_WIN_LOCK_PAGE_PAKING_UNLOCK_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mCarBodyVm.getParkingAutoUnlock())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_WIN_LOCK_PAGE, BtnEnum.SETTING_WIN_LOCK_PAGE_LEAVE_LOCK_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mWindowDoorVm.getAutoWindowLockSw())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_WIN_LOCK_PAGE, BtnEnum.SETTING_WIN_LOCK_PAGE_HIGH_SPEED_CLOSE_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mWindowDoorVm.isHighSpdCloseWinEnabled())));
        if (this.mCarBodyVm.isRightChildLocked()) {
            if (!this.mCarBodyVm.isLeftChildLocked()) {
                i2 = 4;
            }
        } else {
            i2 = this.mCarBodyVm.isLeftChildLocked() ? 3 : 1;
        }
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_WIN_LOCK_PAGE, BtnEnum.SETTING_WIN_LOCK_PAGE_CHILD_LOCK_BTN, Integer.valueOf(i2));
        if (CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_MSMD)) {
            StatisticUtils.sendCarStatusStatistic(PageEnum.SEAT_ADJUST_DIALOG_PAGE, BtnEnum.SEAT_ADJUST_DIALOG_PAGE_WELCOME_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mSeatVm.isWelcomeModeEnabled())));
            StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_OTHER_PAGE, BtnEnum.SETTING_OTHER_PAGE_DRVESB_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mSeatVm.getEsbMode())));
        }
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_OTHER_PAGE, BtnEnum.SETTING_OTHER_PAGE_BOOT_SOUND_BTN, Integer.valueOf(this.mAvasVm.getBootSoundEffect() + 1));
        StatisticUtils.sendCarStatusStatistic(PageEnum.DRIVE_SETTING_PAGE, BtnEnum.DRIVE_SETTING_PAGE_EPS_BTN, Integer.valueOf(this.mChassisVm.getSteeringEps() + 1));
        StatisticUtils.sendCarStatusStatistic(PageEnum.DRIVE_SETTING_PAGE, BtnEnum.DRIVE_SETTING_PAGE_AVH_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mChassisVm.getAvhForUI())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.DRIVE_SETTING_PAGE, BtnEnum.DRIVE_SETTING_PAGE_ESP_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mChassisVm.getEspForUI())));
        if (CarBaseConfig.getInstance().isSupportSnowMode()) {
            StatisticUtils.sendCarStatusStatistic(PageEnum.DRIVE_SETTING_PAGE, BtnEnum.DRIVE_SETTING_PAGE_SNOW_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mVcuVm.getSnowMode())));
        }
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_OTHER_PAGE, BtnEnum.SETTING_OTHERR_PAGE_AVAS_BTN, Integer.valueOf((!this.mAvasVm.isLowSpdEnabled() || (lowSpdEffect = this.mAvasVm.getLowSpdEffect()) == AvasEffect.SoundEffect4.toAvasType()) ? 1 : lowSpdEffect + 1));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_OTHER_PAGE, BtnEnum.SETTING_OTHER_PAGE_NO_BELT_WARN_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mSeatVm.getBackBeltSw())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_CUSTOM_KEY_PAGE, BtnEnum.SETTING_CUSTOM_KEY_PAGE_WHELL_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mMeterVm.isWheelKeyProtectEnabled())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_CUSTOM_KEY_PAGE, BtnEnum.SETTING_CUSTOM_KEY_PAGE_N_STALL_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mVcuVm.getNGearWarningSwitchStatus())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_OTHER_PAGE, BtnEnum.SETTING_OTHER_PAGE_AUTO_WIPER_BTN, Integer.valueOf(this.mCarBodyVm.getWiperSensitivity()));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_X_KEY_PAGE, BtnEnum.SETTING_X_KEY_PAGE_X_KEY_SEL_BTN, Integer.valueOf(getStatisticForXKey(this.mMeterVm.getXKeyForCustomerValue())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_PSN_DOOR_PAGE, BtnEnum.SETTING_PSN_DOOR_PAGE_DOOR_KEY_SEL_BTN, Integer.valueOf(getStatisticForDoorKey(this.mMeterVm.getDoorKeyForCustomerValue())));
        if (CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1)) {
            StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_PSN_DOOR_PAGE, BtnEnum.SETTING_PSN_DOOR_PAGE_DOOR_BOSS_KEY_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mMeterVm.getDoorBossKeySw())));
        }
    }

    private void uploadSituationControlStatus() {
        StatisticUtils.sendCarStatusStatistic(PageEnum.STATUS_E28_PAGE, BtnEnum.STATUS_E28_PAGE_WIPER_REPAIR_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(this.mCarBodyVm.getWiperRepairMode())));
    }

    private void uploadNapaXPilotStatus() {
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_FCW, Integer.valueOf(getScuStatus(this.mScuVm.getFcwState())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_BSD, Integer.valueOf(getScuStatus(this.mScuVm.getBsdState())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_DOW, Integer.valueOf(getScuStatus(this.mScuVm.getDowState())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_RAED, Integer.valueOf(getScuStatus(this.mXpuVm.getRaebState())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_RCW, Integer.valueOf(getScuStatus(this.mScuVm.getRcwState())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_RCTA, Integer.valueOf(getScuStatus(this.mScuVm.getRctaState())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_LSS, Integer.valueOf(this.mScuVm.getLssMode().ordinal()));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_LSS_SEN, Integer.valueOf(this.mXpuVm.getLssSensitivity() + 1));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_ISLA, Integer.valueOf(this.mScuVm.getIslaMode().toDisplayIndex()));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_APA, Integer.valueOf(getScuStatus(this.mScuVm.getAutoParkSw())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_VPA, Integer.valueOf(this.mScuVm.getVpaState()));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_KPA, Integer.valueOf(getScuStatus(this.mScuVm.getKeyParkSw())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_EPA, Integer.valueOf(getScuStatus(this.mScuVm.getPhoneParkSw())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_LCC, Integer.valueOf(getScuStatus(this.mScuVm.getLccState())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_ALC, Integer.valueOf(getScuStatus(this.mScuVm.getAlcState())));
        StatisticUtils.sendCarStatusStatistic(PageEnum.XPILOT_E38_SETTING_PAGE, BtnEnum.XPILOT_E38_SETTING_NGP, Integer.valueOf(getScuStatus(this.mScuVm.getNgpState())));
    }

    private int getScuStatus(ScuResponse scuResponse) {
        return scuResponse == ScuResponse.ON ? 1 : 0;
    }

    private void uploadNapaChildStatus() {
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_E38_CHILD_PAGE, BtnEnum.SETTING_E38_CHILD_MODE, Integer.valueOf(this.mCarBodyVm.isChildModeEnable() ? 1 : 0));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_E38_CHILD_PAGE, BtnEnum.SETTING_E38_LEFT_CHILD_MODE, Integer.valueOf(this.mCarBodyVm.isLeftChildLocked() ? 1 : 0));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_E38_CHILD_PAGE, BtnEnum.SETTING_E38_RIGHT_CHILD_MODE, Integer.valueOf(this.mCarBodyVm.isRightChildLocked() ? 1 : 0));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_E38_CHILD_PAGE, BtnEnum.SETTING_E38_LEFT_DOOR_HOT, Integer.valueOf(this.mCarBodyVm.isLeftDoorHotKeyEnable() ? 1 : 0));
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_E38_CHILD_PAGE, BtnEnum.SETTING_E38_RIGHT_DOOR_HOT, Integer.valueOf(this.mCarBodyVm.isRightDoorHotKeyEnable() ? 1 : 0));
    }

    private void uploadNapaSettingStatus() {
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_E38_PAGE, BtnEnum.SETTING_E38_REAR_SEAT_WELCOME, Integer.valueOf(this.mSeatVm.isRearSeatWelcomeEnabled() ? 1 : 0));
        PageEnum pageEnum = PageEnum.SETTING_E38_PAGE;
        BtnEnum btnEnum = BtnEnum.SETTING_E38_EPB;
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(this.mChassisVm.getApbSystemStatus() == 2 ? 1 : 0);
        StatisticUtils.sendCarStatusStatistic(pageEnum, btnEnum, objArr);
        StatisticUtils.sendCarStatusStatistic(PageEnum.SETTING_E38_PAGE, BtnEnum.SETTING_E38_INDOOR_MIC, Integer.valueOf(this.mAudioVm.isMicrophoneMute() ? 1 : 0));
    }

    private int getStatisticForXKey(XKeyForCustomer key) {
        if (key == null) {
            return 0;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[key.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return i != 4 ? -1 : 5;
                }
                return 1;
            }
            return 3;
        }
        return 4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.statistic.CarStatusUploadHelper$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade;

        static {
            int[] iArr = new int[DoorKeyForCustomer.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer = iArr;
            try {
                iArr[DoorKeyForCustomer.Speech.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[DoorKeyForCustomer.Mute.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[DoorKeyForCustomer.SwitchMedia.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[DoorKeyForCustomer.Disable.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[XKeyForCustomer.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer = iArr2;
            try {
                iArr2[XKeyForCustomer.UnlockTrunk.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.SwitchMedia.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.AutoPark.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.ShowOff.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr3 = new int[LluSayHiEffect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect = iArr3;
            try {
                iArr3[LluSayHiEffect.EffectA.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[LluSayHiEffect.EffectB.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[LluSayHiEffect.EffectC.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            int[] iArr4 = new int[LightMeHomeTime.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime = iArr4;
            try {
                iArr4[LightMeHomeTime.Time30s.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime[LightMeHomeTime.Time60s.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LightMeHomeTime[LightMeHomeTime.Time15s.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
            int[] iArr5 = new int[LampMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode = iArr5;
            try {
                iArr5[LampMode.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[LampMode.Position.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[LampMode.LowBeam.ordinal()] = 3;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[LampMode.Auto.ordinal()] = 4;
            } catch (NoSuchFieldError unused18) {
            }
            int[] iArr6 = new int[EnergyRecycleGrade.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade = iArr6;
            try {
                iArr6[EnergyRecycleGrade.Low.ordinal()] = 1;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$EnergyRecycleGrade[EnergyRecycleGrade.High.ordinal()] = 2;
            } catch (NoSuchFieldError unused20) {
            }
            int[] iArr7 = new int[DriveMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode = iArr7;
            try {
                iArr7[DriveMode.Normal.ordinal()] = 1;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Eco.ordinal()] = 2;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Sport.ordinal()] = 3;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.EcoPlus.ordinal()] = 4;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Comfort.ordinal()] = 5;
            } catch (NoSuchFieldError unused25) {
            }
        }
    }

    private int getStatisticForDoorKey(DoorKeyForCustomer key) {
        if (key == null) {
            return 0;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[key.ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    i2 = 4;
                    if (i != 4) {
                        return -1;
                    }
                }
            }
        }
        return i2;
    }
}
