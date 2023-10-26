package com.xiaopeng.carcontrol.viewmodel.vcu;

import android.text.TextUtils;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.IpcRouterService;
import com.xiaopeng.carcontrol.carmanager.impl.oldarch.HvacOldController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.service.ShowCarControl;
import com.xiaopeng.carcontrolmodule.R;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class VcuSmartControl {
    private static final long AUTO_DRIVE_VALID_TIME = 60000;
    private static final int MILEAGE_THRESHOLD_HIGH = 100;
    private static final int MILEAGE_THRESHOLD_LOW = 60;
    private static final int MILEAGE_THRESHOLD_TOO_LOW = 30;
    private static final String TAG = "VcuSmartControl";
    private boolean mAutoDriveMode;
    private IVcuViewModel mVcuVm = (IVcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
    private IChassisViewModel mChassisVm = (IChassisViewModel) ViewModelManager.getInstance().getViewModelImpl(IChassisViewModel.class);
    private FunctionModel mFuncModel = FunctionModel.getInstance();

    int getMileageHighThreshold() {
        return 100;
    }

    /* loaded from: classes2.dex */
    private static class SingleHolder {
        private static VcuSmartControl sInstance;

        private SingleHolder() {
        }

        static {
            sInstance = "D21".equals(CarStatusUtils.getHardwareCarType()) ? new D2VcuSmartControl() : new VcuSmartControl();
        }
    }

    public static VcuSmartControl getInstance() {
        return SingleHolder.sInstance;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public VcuSmartControl() {
        if (App.isMainProcess()) {
            onAutoDriveModeChanged(this.mVcuVm.isAutoDriveModeEnabled(), true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void toEcoMode(boolean auto) {
        String string;
        int driveMode = this.mVcuVm.getDriveMode();
        if (driveMode == 1 || driveMode == 5) {
            LogUtils.d(TAG, "can not Switch to eco mode, currentMode=" + driveMode, false);
            return;
        }
        LogUtils.d(TAG, "Switch to eco mode: " + auto, false);
        this.mFuncModel.setLastDriveMode(this.mVcuVm.getDriveModeByUser());
        this.mFuncModel.setDriveModeChangedByUser(false);
        this.mVcuVm.setDriveMode(1);
        if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            this.mVcuVm.setDriveModeSubItemByUser(1);
        }
        if (auto) {
            string = ResUtils.getString(R.string.auto_change_eco_response);
        } else {
            string = ResUtils.getString(R.string.format_change_mode_response, getDriveModeStr(1));
        }
        NotificationHelper.getInstance().showToast(string);
        if (auto) {
            SpeechHelper.getInstance().speak(string);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void toPreviousMode(boolean auto) {
        int lastDriveMode = this.mFuncModel.getLastDriveMode();
        LogUtils.d(TAG, "toPreviousMode lastMode:" + lastDriveMode + ",auto:" + auto);
        if (lastDriveMode != -1) {
            if (lastDriveMode == 1001) {
                this.mVcuVm.setXSportDriveMode(1);
            } else {
                this.mVcuVm.setDriveMode(lastDriveMode);
                if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
                    this.mVcuVm.setDriveModeSubItemByUser(lastDriveMode);
                }
            }
            this.mFuncModel.setLastDriveMode(-1);
            this.mFuncModel.setDriveModeChangedByUser(false);
            String string = ResUtils.getString(auto ? R.string.format_auto_change_previous_mode_response : R.string.format_change_mode_response, getDriveModeStr(lastDriveMode));
            NotificationHelper.getInstance().showToast(string);
            if (auto) {
                SpeechHelper.getInstance().speak(string);
            }
        }
    }

    private String getDriveModeStr(int lastMode) {
        if (lastMode != 0) {
            if (lastMode != 1) {
                if (lastMode != 2) {
                    if (lastMode != 10) {
                        if (lastMode != 16) {
                            return lastMode != 1001 ? "" : ResUtils.getString(R.string.drive_mode_xsport_geek);
                        }
                        return ResUtils.getString(R.string.special_drive_mode_mud);
                    }
                    return ResUtils.getString(R.string.drive_mode_normal);
                }
                return ResUtils.getString(R.string.drive_mode_sport);
            }
            return ResUtils.getString(R.string.drive_mode_eco);
        }
        return ResUtils.getString(R.string.drive_mode_normal);
    }

    public void onRemainMileageChanged(int mileage) {
        if (BaseFeatureOption.getInstance().isSupportLowPowerProtect()) {
            if (mileage < 0 || ShowCarControl.getInstance().isShowCarDriveDisable()) {
                LogUtils.w(TAG, "onRemainMileageChanged with invalid value: " + mileage, false);
                return;
            }
            int driveMode = this.mVcuVm.getDriveMode();
            String str = TAG;
            LogUtils.debug(str, "leftMileage: " + mileage + ", currentMode: " + driveMode + ", mAutoDriveMode: " + this.mAutoDriveMode);
            if (mileage <= 30 && driveMode != 1 && driveMode != 5 && driveMode != 7 && this.mVcuVm.getChargeStatus() != 2 && FunctionModel.getInstance().isFuncLowPower30Allowed()) {
                FunctionModel.getInstance().setLowPower30Ts(System.currentTimeMillis());
                handleTooLow();
            } else if (mileage > 30 && mileage <= 60 && driveMode != 1 && driveMode != 5 && driveMode != 7 && this.mVcuVm.getChargeStatus() != 2 && FunctionModel.getInstance().isFuncLowPower60Allowed()) {
                FunctionModel.getInstance().setLowPower60Ts(System.currentTimeMillis());
                if (this.mAutoDriveMode) {
                    toEcoMode(true);
                } else {
                    NotificationHelper.getInstance().sendMessageToMessageCenter(BaseFeatureOption.getInstance().isOldAiAssistant() ? 5001 : NotificationHelper.SCENE_60_KM, ResUtils.getString(R.string.low_battery_title), ResUtils.getString(R.string.low_battery_content), ResUtils.getString(R.string.low_battery_tts), ResUtils.getString(R.string.low_battery_wake_words), ResUtils.getString(R.string.low_battery_response_tts), ResUtils.getString(R.string.low_battery_btn_title), false, 60000L, new IpcRouterService.ICallback() { // from class: com.xiaopeng.carcontrol.viewmodel.vcu.-$$Lambda$VcuSmartControl$eR4xRZokKq2vWPJTU4rgC7Uc_bY
                        @Override // com.xiaopeng.carcontrol.IpcRouterService.ICallback
                        public final void onCallback(String str2) {
                            VcuSmartControl.this.lambda$onRemainMileageChanged$0$VcuSmartControl(str2);
                        }
                    });
                }
            } else if (mileage <= getMileageHighThreshold() || driveMode != 1 || !FunctionModel.getInstance().isFuncLowPower100Allowed() || FunctionModel.getInstance().isDriveModeChangedByUser() || this.mFuncModel.getLastDriveMode() == -1) {
            } else {
                FunctionModel.getInstance().setLowPower100Ts(System.currentTimeMillis());
                if (this.mFuncModel.getLastDriveMode() != 1) {
                    handleExitLowMode();
                    return;
                }
                LogUtils.d(str, "reset last drive mode", false);
                this.mFuncModel.setLastDriveMode(-1);
            }
        }
    }

    public /* synthetic */ void lambda$onRemainMileageChanged$0$VcuSmartControl(String content) {
        toEcoMode(false);
    }

    void handleTooLow() {
        if (this.mAutoDriveMode) {
            toEcoMode(true);
        } else {
            NotificationHelper.getInstance().sendMessageToMessageCenter(BaseFeatureOption.getInstance().isOldAiAssistant() ? NotificationHelper.D21_SCENE_30_KM : NotificationHelper.SCENE_30_KM, ResUtils.getString(R.string.low_battery_title), ResUtils.getString(R.string.too_low_battery_content), ResUtils.getString(R.string.too_low_battery_prompt_tts), ResUtils.getString(R.string.low_battery_wake_words), ResUtils.getString(R.string.low_battery_response_tts), ResUtils.getString(R.string.low_battery_btn_title), false, 60000L, new IpcRouterService.ICallback() { // from class: com.xiaopeng.carcontrol.viewmodel.vcu.-$$Lambda$VcuSmartControl$V5UAqMRoQN-wBNpKvHoGOKvuzY0
                @Override // com.xiaopeng.carcontrol.IpcRouterService.ICallback
                public final void onCallback(String str) {
                    VcuSmartControl.this.lambda$handleTooLow$1$VcuSmartControl(str);
                }
            });
        }
    }

    public /* synthetic */ void lambda$handleTooLow$1$VcuSmartControl(String content) {
        toEcoMode(false);
    }

    void handleExitLowMode() {
        if (this.mAutoDriveMode) {
            toPreviousMode(true);
            return;
        }
        String driveModeStr = getDriveModeStr(this.mFuncModel.getLastDriveMode());
        NotificationHelper.getInstance().sendMessageToMessageCenter(BaseFeatureOption.getInstance().isOldAiAssistant() ? NotificationHelper.D21_SCENE_100_KM : NotificationHelper.SCENE_100_KM, ResUtils.getString(R.string.fully_battery_title, driveModeStr), ResUtils.getString(R.string.fully_battery_content), ResUtils.getString(R.string.fully_battery_tts, driveModeStr), ResUtils.getString(R.string.low_battery_wake_words), ResUtils.getString(R.string.format_change_mode_response, driveModeStr), ResUtils.getString(R.string.low_battery_btn_title), false, 60000L, new IpcRouterService.ICallback() { // from class: com.xiaopeng.carcontrol.viewmodel.vcu.-$$Lambda$VcuSmartControl$GQ8JEDfSO_6rtE1Dv6NaGpxwUhk
            @Override // com.xiaopeng.carcontrol.IpcRouterService.ICallback
            public final void onCallback(String str) {
                VcuSmartControl.this.lambda$handleExitLowMode$2$VcuSmartControl(str);
            }
        });
    }

    public /* synthetic */ void lambda$handleExitLowMode$2$VcuSmartControl(String content) {
        toPreviousMode(false);
    }

    public void onChargeStatusChanged(int chargeStatus) {
        if (chargeStatus == 2 || chargeStatus == 5 || chargeStatus == 4) {
            LogUtils.d(TAG, "onChargeStatusChanged: " + chargeStatus + ", and reset all low power timestamp", false);
            FunctionModel.getInstance().setLowPower30Ts(0L);
            FunctionModel.getInstance().setLowPower60Ts(0L);
            FunctionModel.getInstance().setLowPower100Ts(0L);
            onRemainMileageChanged(this.mVcuVm.getAvailableMileage());
        }
    }

    private void onAutoDriveModeChanged(boolean enable, boolean force) {
        if (this.mAutoDriveMode != enable || force) {
            this.mAutoDriveMode = enable;
            if (!force) {
                LogUtils.d(TAG, "onAutoDriveModeChanged: " + enable + ", and reset all low power timestamp", false);
                FunctionModel.getInstance().setLowPower30Ts(0L);
                FunctionModel.getInstance().setLowPower60Ts(0L);
                FunctionModel.getInstance().setLowPower100Ts(0L);
            }
        }
        onRemainMileageChanged(this.mVcuVm.getAvailableMileage());
    }

    public void onSnowModeChange(boolean enable) {
        if (enable) {
            boolean espSw = this.mChassisVm.getEspSw();
            LogUtils.d(TAG, "Snow mode on, current esp sw: " + espSw, false);
            if (espSw) {
                return;
            }
            this.mChassisVm.setEspSw();
            return;
        }
        int snowModeEnergyCache = this.mFuncModel.getSnowModeEnergyCache();
        if (snowModeEnergyCache != -1) {
            this.mVcuVm.setEnergyRecycleGrade(snowModeEnergyCache);
        }
        this.mFuncModel.setSnowModeEnergyCache(-1);
    }

    public boolean isParkStall() {
        return this.mVcuVm.getGearLevel() == 4;
    }

    public void onEcoPlusMode(boolean enable) {
        if (!enable || this.mChassisVm.getAvhSw()) {
            return;
        }
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.vcu.-$$Lambda$VcuSmartControl$OTgsAYC6S-MFmQBvBHghD2qFcOU
            @Override // java.lang.Runnable
            public final void run() {
                VcuSmartControl.this.lambda$onEcoPlusMode$3$VcuSmartControl();
            }
        }, this.mChassisVm.getAvhFault() ? HvacOldController.PART_CONTROL_DELAY_TIME : 100L);
    }

    public /* synthetic */ void lambda$onEcoPlusMode$3$VcuSmartControl() {
        this.mChassisVm.setAvhSw(true);
        if (this.mChassisVm.getAvhFault() || !App.isMainProcess()) {
            return;
        }
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.AVH_SW_NOTIFY, true);
    }

    public void setDriveModeByUser(int driveMode) {
        this.mVcuVm.setDriveModeByUser(DriveMode.fromVcuDriveMode(driveMode));
    }

    public void resumeDriveMode(int driveMode) {
        resumeDriveMode(driveMode, false);
    }

    public void resumeDriveMode(int driveMode, boolean igOn) {
        if (CarBaseConfig.getInstance().isSupportXSport() && driveMode == 1001) {
            this.mVcuVm.enterXSportDriveModeByType(1, false);
        } else if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            boolean asCustomerModeFlagSwitchStatus = this.mChassisVm.getAsCustomerModeFlagSwitchStatus();
            LogUtils.d(TAG, "resumeDriveMode customerFlag: " + asCustomerModeFlagSwitchStatus + ", driveMode: " + driveMode, false);
            if (asCustomerModeFlagSwitchStatus) {
                this.mVcuVm.setDriveModeSp(driveMode);
            } else {
                this.mVcuVm.setDriveMode(driveMode, false);
            }
            this.mVcuVm.setDriveModeSubItemByUser(driveMode, asCustomerModeFlagSwitchStatus, igOn);
        } else {
            this.mVcuVm.setDriveMode(driveMode, false);
        }
    }

    public void setEnergyRecycleGrade(int grade) {
        this.mVcuVm.setEnergyRecycleGrade(grade);
    }

    public void onXSportResumeDriveMode(String params) {
        int i;
        if (TextUtils.isEmpty(params)) {
            i = this.mVcuVm.getDriveModeByUser();
        } else {
            try {
                i = new JSONObject(params).optInt("driveMode");
            } catch (Exception e) {
                e.printStackTrace();
                i = -1;
            }
        }
        if (i != -1) {
            if (1001 == i) {
                this.mVcuVm.setXSportDriveMode(1);
            } else {
                this.mVcuVm.setDriveModeByUser(DriveMode.fromVcuDriveMode(i));
            }
        }
    }

    public void onXSportSaveDriveMode(String params) {
        if (TextUtils.isEmpty(params)) {
            return;
        }
        try {
            if (new JSONObject(params).optInt("driveMode") == 1001) {
                this.mVcuVm.setDriveModeSp(1001);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
