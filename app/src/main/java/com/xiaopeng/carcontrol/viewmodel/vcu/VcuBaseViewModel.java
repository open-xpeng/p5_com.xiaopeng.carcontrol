package com.xiaopeng.carcontrol.viewmodel.vcu;

import android.database.ContentObserver;
import android.net.Uri;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.controller.IDiagnosticController;
import com.xiaopeng.carcontrol.carmanager.controller.IEpsController;
import com.xiaopeng.carcontrol.carmanager.controller.IEspController;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.carmanager.controller.IMsmController;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.carmanager.controller.IXpuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.vcu.bean.NewDriveSubItem;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.lib.apirouter.ApiRouter;
import com.xiaopeng.xuimanager.soundresource.SoundResourceManager;
import com.xiaopeng.xuimanager.soundresource.data.SoundEffectResource;
import java.io.File;

/* loaded from: classes2.dex */
public abstract class VcuBaseViewModel implements IVcuViewModel, IVcuController.Callback {
    private static final int SOUND_DRIVE_MODE_BOOST = 6;
    private static final String SOUND_DRIVE_MODE_BOOST_SUFFIX = "boost.wav";
    private static final int SOUND_DRIVE_MODE_COMFORT = 3;
    private static final String SOUND_DRIVE_MODE_COMFORT_SUFFIX = "comfort.wav";
    private static final int SOUND_DRIVE_MODE_ECO = 2;
    private static final String SOUND_DRIVE_MODE_ECO_SUFFIX = "eco.wav";
    private static final int SOUND_DRIVE_MODE_MUD = 4;
    private static final String SOUND_DRIVE_MODE_MUD_SUFFIX = "mud.wav";
    private static final int SOUND_DRIVE_MODE_NORMAL = 0;
    private static final String SOUND_DRIVE_MODE_NORMAL_SUFFIX = "normal.wav";
    private static final int SOUND_DRIVE_MODE_RACER = 5;
    private static final String SOUND_DRIVE_MODE_RACER_SUFFIX = "racer.wav";
    private static final int SOUND_DRIVE_MODE_SPORT = 1;
    private static final String SOUND_DRIVE_MODE_SPORT_SUFFIX = "sport.wav";
    private static final String TAG = "VcuBaseViewModel";
    private boolean isQuickControlVisible;
    private IBcmController mBcmController;
    private final IDiagnosticController mDiagnosticController;
    private IEpsController mEpsController;
    IEspController mEspController;
    IMcuController.Callback mMcuCallback = new IMcuController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel.1
        @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController.Callback
        public void onIgStatusChanged(int state) {
            VcuBaseViewModel.this.handleIgStatusChange(state);
        }
    };
    IMcuController mMcuController;
    IMsmController mMmsController;
    IVcuController mVcuController;
    private IXpuController mXpuController;

    private int toAsDriveMode(int vcuDriveMode) {
        if (vcuDriveMode != 0) {
            if (vcuDriveMode == 1) {
                return 2;
            }
            if (vcuDriveMode == 2) {
                return 3;
            }
            if (vcuDriveMode == 10) {
                return 4;
            }
            if (vcuDriveMode == 16) {
                return 5;
            }
        }
        return 1;
    }

    protected abstract void handleBrakeLightChanged(boolean on);

    protected abstract void handleCarSpeedChanged(float speed);

    protected abstract void handleCruiseChanged(boolean isActive);

    protected abstract void handleEvSysReady(boolean ready);

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: handleExhibitionModeChanged */
    public abstract void lambda$controlExhibitionMode$1$VcuBaseViewModel(boolean enabled);

    protected abstract void handleGearChange(int gear);

    protected abstract void handleNGearWarningSwChanged(boolean enable);

    protected abstract void handlePowerResponseModeChange(int mode);

    protected abstract void handleQuickControlVisible(boolean isQuickControlVisible);

    protected abstract void handleShowModeChanged(boolean on);

    protected abstract void handleShowXpowerDialog(boolean on);

    protected abstract void handleSnowModeChange(boolean enable);

    protected abstract void handleSsaSwChange(boolean enabled);

    protected abstract void handleTrailerModeSwitchChange(boolean on);

    protected abstract void handleXPedalModeSwitchChange(boolean on);

    protected abstract void handleXSportDriveModeChange(int mode);

    /* JADX INFO: Access modifiers changed from: package-private */
    public VcuBaseViewModel() {
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
        this.mDiagnosticController = (IDiagnosticController) carClientWrapper.getController(CarClientWrapper.XP_DIAGNOSTIC_SERVICE);
        this.mMcuController = (IMcuController) carClientWrapper.getController(CarClientWrapper.XP_MCU_SERVICE);
        this.mBcmController = (IBcmController) carClientWrapper.getController(CarClientWrapper.XP_BCM_SERVICE);
        this.mEpsController = (IEpsController) carClientWrapper.getController(CarClientWrapper.XP_EPS_SERVICE);
        this.mEspController = (IEspController) carClientWrapper.getController(CarClientWrapper.XP_ESP_SERVICE);
        if (CarBaseConfig.getInstance().isSupportXpu()) {
            this.mXpuController = (IXpuController) carClientWrapper.getController(CarClientWrapper.XP_XPU_SERVICE);
        }
        this.mMmsController = (IMsmController) carClientWrapper.getController(CarClientWrapper.XP_MSM_SERVICE);
        if (App.isMainProcess()) {
            App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.SET_SNOW_MODE), false, new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel.2
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    LogUtils.d(VcuBaseViewModel.TAG, "onChange: " + uri);
                    String lastPathSegment = uri.getLastPathSegment();
                    if (lastPathSegment == null) {
                        return;
                    }
                    lastPathSegment.hashCode();
                    if (!lastPathSegment.equals(CarControl.PrivateControl.QUICK_CONTROL_UI_RESUME)) {
                        if (lastPathSegment.equals(CarControl.PrivateControl.SET_SNOW_MODE)) {
                            VcuBaseViewModel.this.setSnowMode(CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), lastPathSegment, false));
                            return;
                        }
                        return;
                    }
                    VcuBaseViewModel.this.isQuickControlVisible = CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), lastPathSegment, false);
                    VcuBaseViewModel vcuBaseViewModel = VcuBaseViewModel.this;
                    vcuBaseViewModel.handleQuickControlVisible(vcuBaseViewModel.isQuickControlVisible);
                }
            });
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setDriveMode(int driveMode) {
        setDriveMode(driveMode, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setDriveMode(int driveMode, boolean audioEffect) {
        setDriveMode(driveMode, audioEffect, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setDriveMode(int driveMode, boolean audioEffect, boolean storeEnable) {
        int xSportDrivingMode;
        if (audioEffect) {
            if (driveMode == 0) {
                SoundHelper.play(getDriveModeSoundPath(0, "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_normal.wav"), true, false);
            } else if (driveMode == 1) {
                SoundHelper.play(getDriveModeSoundPath(2, SoundHelper.PATH_DRIVE_MODE_ECO), true, false);
            } else if (driveMode == 2) {
                SoundHelper.play(getDriveModeSoundPath(1, "/system/media/audio/xiaopeng/cdu/wav/CDU_drive_sport.wav"), true, false);
            } else if (driveMode == 5) {
                SoundHelper.play(SoundHelper.PATH_DRIVE_MODE_XPEDAL, true, false);
            } else if (driveMode == 7) {
                SoundHelper.play(getDriveModeSoundPath(3, SoundHelper.PATH_DRIVE_MODE_ANTISICK), true, false);
            } else if (driveMode == 10) {
                SoundHelper.play(getDriveModeSoundPath(0, SoundHelper.PATH_DRIVE_MODE_NORMAL_NEW), true, false);
            } else if (driveMode == 16) {
                SoundHelper.play(getDriveModeSoundPath(4, SoundHelper.PATH_DRIVE_MODE_MUD), true, false);
            }
        }
        if (!CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            int driveMode2 = getDriveMode();
            if (driveMode != 5 && driveMode2 == 5) {
                NotificationHelper.getInstance().showToast(R.string.drive_mode_eco_plus_off);
            } else if (driveMode != 7 && driveMode2 == 7) {
                NotificationHelper.getInstance().showToast(R.string.drive_mode_anti_sickness_off);
            }
        }
        this.mVcuController.setDriveMode(driveMode, storeEnable);
        if (BaseFeatureOption.getInstance().isSupportXSportApp() || !CarBaseConfig.getInstance().isSupportXSport() || (xSportDrivingMode = this.mVcuController.getXSportDrivingMode()) == 0) {
            return;
        }
        this.mVcuController.exitXSportDriveMode();
        if (4 == xSportDrivingMode) {
            NotificationHelper.getInstance().showToast(R.string.drive_mode_anti_racer_off);
        }
    }

    protected String getDriveModeSoundPath(int mode, String defaultPath) {
        SoundResourceManager soundResManager = XuiClientWrapper.getInstance().getSoundResManager();
        if (soundResManager == null) {
            return defaultPath;
        }
        SoundEffectResource activeSoundEffectResource = soundResManager.getActiveSoundEffectResource(6);
        String str = null;
        String[] resPath = activeSoundEffectResource == null ? null : activeSoundEffectResource.getResPath();
        if (resPath == null) {
            return defaultPath;
        }
        switch (mode) {
            case 0:
                str = SOUND_DRIVE_MODE_NORMAL_SUFFIX;
                break;
            case 1:
                str = SOUND_DRIVE_MODE_SPORT_SUFFIX;
                break;
            case 2:
                str = SOUND_DRIVE_MODE_ECO_SUFFIX;
                break;
            case 3:
                str = SOUND_DRIVE_MODE_COMFORT_SUFFIX;
                break;
            case 4:
                str = SOUND_DRIVE_MODE_MUD_SUFFIX;
                break;
            case 5:
                str = SOUND_DRIVE_MODE_RACER_SUFFIX;
                break;
            case 6:
                str = SOUND_DRIVE_MODE_BOOST_SUFFIX;
                break;
        }
        if (str == null) {
            return defaultPath;
        }
        for (String str2 : resPath) {
            if (str.equals(str2.substring(str2.lastIndexOf(File.separatorChar) + 1))) {
                LogUtils.i(TAG, "Matched drive mode sound path: " + str2);
                return str2;
            }
        }
        return defaultPath;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setDriveModeSp(int driveMode) {
        this.mVcuController.setDriveModeSp(driveMode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setDriveModeByUser(DriveMode driveMode) {
        setDriveModeByUser(driveMode, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setDriveModeByUser(final DriveMode driveMode, boolean storeEnable) {
        if (driveMode == null) {
            return;
        }
        if (App.isMainProcess()) {
            setDriveMode(driveMode.toVcuCmd(), true, storeEnable);
            if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
                setDriveModeSubItemByUser(driveMode.toVcuCmd());
            }
            FunctionModel.getInstance().setDriveModeChangedByUser(true);
            return;
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.vcu.-$$Lambda$VcuBaseViewModel$Axgh9aiUFsySwMoccXiHlLYMapg
            @Override // java.lang.Runnable
            public final void run() {
                CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.DRIVE_MODE_UI, DriveMode.this.toVcuCmd());
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setDriveModeSubItemByUser(int vcuCode) {
        setDriveModeSubItemByUser(vcuCode, false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setDriveModeSubItemByUser(int vcuCode, boolean customerFlag) {
        setDriveModeSubItemByUser(vcuCode, customerFlag, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0089  */
    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setDriveModeSubItemByUser(int r10, boolean r11, boolean r12) {
        /*
            Method dump skipped, instructions count: 266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel.setDriveModeSubItemByUser(int, boolean, boolean):void");
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setDriveModeSubItemForAsMode(int vcuCode, boolean customerFlag) {
        if (customerFlag) {
            IBcmController iBcmController = this.mBcmController;
            iBcmController.setAirSuspensionHeight(iBcmController.getAirSuspensionHeightSp());
            return;
        }
        NewDriveSubItem newDriveSubItem = new NewDriveSubItem(this.mVcuController.getDriveModeSubItemInfo(vcuCode));
        LogUtils.i(TAG, "setDriveModeSubItemForAsMode, NewDriveSubItem is: " + newDriveSubItem, false);
        this.mBcmController.setAirSuspensionHeight(newDriveSubItem.getAsHeight());
        this.mBcmController.setAirSuspensionSoft(newDriveSubItem.getAsSoft());
        this.mVcuController.setPowerResponseMode(newDriveSubItem.getPowerResponse());
        this.mVcuController.setEnergyRecycleGrade(newDriveSubItem.getEnergyRecovery());
        this.mVcuController.setMotorPowerMode(newDriveSubItem.getMotorPower());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getDriveMode() {
        int driveMode = this.mVcuController.getDriveMode();
        LogUtils.debug(TAG, "getDriveMode: " + driveMode, false);
        return driveMode;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getDriveModeByUser() {
        int driveModeByUser = this.mVcuController.getDriveModeByUser();
        LogUtils.i(TAG, "getDriveModeByUser: " + driveModeByUser, false);
        return driveModeByUser;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public String getDriveModeSubItemInfo(int driveMode) {
        return this.mVcuController.getDriveModeSubItemInfo(driveMode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void controlExhibitionMode(final boolean enter) {
        this.mVcuController.controlExhibitionMode(enter);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.vcu.-$$Lambda$VcuBaseViewModel$5FDsJa0QcVY_GnEjpD721bQJa10
            @Override // java.lang.Runnable
            public final void run() {
                VcuBaseViewModel.this.lambda$controlExhibitionMode$1$VcuBaseViewModel(enter);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean isExhibitionMode() {
        return this.mVcuController.isExhibitionMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean isAutoDriveModeEnabled() {
        return this.mVcuController.isAutoDriveModeEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setAutoDriveMode(boolean enable) {
        this.mVcuController.setAutoDriveMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean isEnergyEnable(boolean showToast) {
        int energyDisableString = getEnergyDisableString();
        if (energyDisableString != 0) {
            if (showToast) {
                NotificationHelper.getInstance().showToast(energyDisableString);
                return false;
            }
            return false;
        }
        return true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getEnergyDisableString() {
        if (CarBaseConfig.getInstance().isSupportXSport() && this.mVcuController.getXSportDrivingMode() == 3) {
            return R.string.power_recycle_unable_with_xsport_geek;
        }
        if (CarBaseConfig.getInstance().isSupportXSport() && this.mVcuController.getXSportDrivingMode() == 1) {
            return R.string.power_recycle_unable_with_xsport_xpower;
        }
        if (CarBaseConfig.getInstance().isSupportSnowMode() && getSnowMode()) {
            return R.string.power_recycle_unable_with_snow_mode;
        }
        if (CarBaseConfig.getInstance().isSupportDriveModeNewArch() || !this.mDiagnosticController.isAbsFault()) {
            return 0;
        }
        return R.string.power_recycle_unable_with_ads_fault;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean isAbsFault() {
        return this.mDiagnosticController.isAbsFault();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setEnergyRecycleGrade(int grade) {
        setEnergyRecycleGrade(grade, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setEnergyRecycleGrade(final int grade, boolean storeEnable) {
        if (App.isMainProcess()) {
            this.mVcuController.setEnergyRecycleGrade(grade, storeEnable);
            if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
                return;
            }
            int driveMode = getDriveMode();
            if (driveMode == 5) {
                NotificationHelper.getInstance().showToast(R.string.drive_mode_eco_plus_off);
                return;
            } else if (driveMode != 7) {
                return;
            } else {
                NotificationHelper.getInstance().showToast(R.string.drive_mode_anti_sickness_off);
                return;
            }
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.vcu.-$$Lambda$VcuBaseViewModel$4idDVMwhpYwVZjHDNzgwUJB6sdw
            @Override // java.lang.Runnable
            public final void run() {
                CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.ENERGY_RECYCLE, grade);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getEnergyRecycleGrade() {
        return this.mVcuController.getEnergyRecycleGrade();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getEnergyRecycleGradeByUser() {
        return this.mVcuController.getEnergyRecycleGradeByUser();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getGearLevel() {
        return this.mVcuController.getGearLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getAvailableMileage() {
        return getAvailableMileage(true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getAvailableMileage(boolean byMileageMode) {
        return this.mVcuController.getAvailableMileage(byMileageMode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getMileageMode() {
        return this.mVcuController.getMileageMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getChargeStatus() {
        return this.mVcuController.getChargeStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public float getCarSpeed() {
        return this.mVcuController.getCarSpeed();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onGearChanged(int gear) {
        if (gear >= 1 && gear <= 4) {
            handleGearChange(gear);
        } else {
            LogUtils.e(TAG, "invalid gear:" + gear, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onRawCarSpeedChanged(float speed) {
        handleCarSpeedChanged(speed);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onNGearWarningSwChanged(boolean enable) {
        handleNGearWarningSwChanged(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onCruiseStateChanged(boolean isActive) {
        handleCruiseChanged(isActive);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean isCruiseActive() {
        return this.mVcuController.isCruiseActive();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setNGearWarningSwitch(boolean enable) {
        this.mVcuController.setNGearWarningSwitch(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean getNGearWarningSwitchStatus() {
        return this.mVcuController.getNGearWarningSwitchStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setSnowMode(boolean enable) {
        if (App.isMainProcess()) {
            this.mVcuController.setSnowMode(enable);
            if (enable) {
                FunctionModel.getInstance().setSnowModeEnergyCache(getEnergyRecycleGrade());
                return;
            }
            return;
        }
        if (enable && getDriveMode() == 5) {
            int driveMode = getDriveMode();
            if (driveMode == 5) {
                NotificationHelper.getInstance().showToast(R.string.drive_mode_eco_plus_with_open_snow_mode);
                return;
            } else if (driveMode == 7) {
                NotificationHelper.getInstance().showToast(R.string.drive_mode_anti_sickness_with_open_snow_mode);
                return;
            }
        }
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SET_SNOW_MODE, enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean getSnowMode() {
        return this.mVcuController.getSnowMode();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onSnowModeChanged(boolean enable) {
        handleSnowModeChange(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onXSportDriveModeChanged(int mode) {
        handleXSportDriveModeChange(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onPowerResponseModeChanged(int mode) {
        handlePowerResponseModeChange(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onXPedalModeSwitchChanged(boolean on) {
        handleXPedalModeSwitchChange(on);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onTrailerModeSwitchChanged(boolean on) {
        handleTrailerModeSwitchChange(on);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onSsaSwChanged(boolean enabled) {
        handleSsaSwChange(enabled);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onBrakeLightChanged(boolean on) {
        handleBrakeLightChanged(on);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onShowModeChanged(boolean on) {
        handleShowModeChanged(on);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean isEVSysReady() {
        return this.mVcuController.isEvSysReady();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean isEvHighVolOn() {
        return this.mVcuController.isEvHighVolReady();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onEvSysReady(int state) {
        handleEvSysReady(state == 2);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getElectricityPercent() {
        return this.mVcuController.getElecPercent();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setQuickControlVisible(boolean visible) {
        if (App.isMainProcess()) {
            return;
        }
        this.isQuickControlVisible = visible;
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.QUICK_CONTROL_UI_RESUME, visible);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean isQuickControlVisible() {
        return this.isQuickControlVisible;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getXSportDriveMode() {
        return this.mVcuController.getXSportDrivingMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setXSportDriveMode(int mode) {
        if (CarBaseConfig.getInstance().isSupportXSport() && !BaseFeatureOption.getInstance().isSupportXSportApp()) {
            if (4 == mode) {
                SoundHelper.play(getDriveModeSoundPath(5, SoundHelper.PATH_DRIVE_MODE_RACER), true, false);
            } else if (3 == mode) {
                SoundHelper.play(getDriveModeSoundPath(6, SoundHelper.PATH_DRIVE_MODE_BOOST), true, false);
            }
        }
        this.mVcuController.setXSportDrivingMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void enterXSportDriveModeByType(int driveMode, boolean showTips) {
        this.mVcuController.enterXSportDriveModeByType(driveMode, !showTips ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getPowerResponseMode() {
        return this.mVcuController.getPowerResponseMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setPowerResponseMode(int mode) {
        this.mVcuController.setPowerResponseMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean getNewDriveArchXPedalMode() {
        return this.mVcuController.getNewDriveArchXPedalMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setNewDriveArchXPedalMode(boolean enable, boolean storeEnable) {
        this.mVcuController.setNewDriveArchXPedalMode(enable, storeEnable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setNewDriveArchXPedalMode(boolean enable) {
        setNewDriveArchXPedalMode(enable, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean getVMCRwsSwitchState() {
        return this.mVcuController.getVMCRwsSwitchState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setVMCRwsSwitch(boolean on) {
        this.mVcuController.setVMCRwsSwitch(on);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean getVMCZWalkModeState() {
        return this.mVcuController.getVMCZWalkModeState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setVMCZWalkModeSwitch(boolean on) {
        this.mVcuController.setVMCZWalkModeSwitch(on);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean getVMCSystemState() {
        return this.mVcuController.getVMCSystemState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setTrailerMode(boolean enable) {
        this.mVcuController.setTrailerMode(enable);
        this.mBcmController.setTransportMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean getTrailerModeStatus() {
        return this.mVcuController.getTrailerModeStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean isBreakPedalPressed() {
        return this.mVcuController.getBreakPedalStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getAsDriveModeStatus() {
        return this.mVcuController.getAsDriveModeStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean getSsaSw() {
        return this.mVcuController.getSsaSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setSsaSwEnable(boolean enable) {
        this.mVcuController.setSsaSwEnable(enable);
        NotificationHelper.getInstance().showToast(enable ? R.string.ssa_feature_on_toast : R.string.ssa_feature_off_toast, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void enterXSportGeekSettings() {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.vcu.-$$Lambda$VcuBaseViewModel$a5QSDotQpI9HLfR9Nz3rBKThnDY
            @Override // java.lang.Runnable
            public final void run() {
                VcuBaseViewModel.lambda$enterXSportGeekSettings$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$enterXSportGeekSettings$3() {
        Uri build = new Uri.Builder().authority("com.xiaopeng.xsport.GeekModeService").path("showCustomizeDialog").build();
        LogUtils.i(TAG, "enterXSportGeekSettings, result: ", false);
        try {
            ApiRouter.route(build);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean getBrakeLightOnOff() {
        return this.mVcuController.getBrakeLightOnOff();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getCreepVehSpd() {
        return this.mVcuController.getCreepVehSpd();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public int getAwdSetting() {
        return this.mVcuController.getAwdSetting();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setAwdSetting(int mode) {
        this.mVcuController.setAwdSetting(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean isExhibitionModeOn() {
        return this.mVcuController.isExhibitionModeOn();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleIgStatusChange(int state) {
        if (App.isMainProcess() || state != 0) {
            return;
        }
        SoundHelper.play("", false, false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void registerBusiness(String... keys) {
        this.mVcuController.registerBusiness(keys);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void unregisterBusiness(String... keys) {
        this.mVcuController.unregisterBusiness(keys);
    }
}
