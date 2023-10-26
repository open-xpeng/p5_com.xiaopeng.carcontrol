package com.xiaopeng.carcontrol.viewmodel.vcu;

import android.net.Uri;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.lib.apirouter.ApiRouter;
import java.util.HashSet;

/* loaded from: classes2.dex */
public class VcuViewModel extends VcuBaseViewModel {
    private static final String TAG = "VcuViewModel";
    private final MutableLiveData<DriveMode> mDriveMode = new MutableLiveData<>();
    private final MutableLiveData<XSportDriveMode> mXSportDriveMode = new MutableLiveData<>();
    private final MutableLiveData<EnergyRecycleGrade> mEnergyRecycle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mDCChargeStatusData = new MutableLiveData<>();
    private final MutableLiveData<ChargeGunStatus> mChargeGunStatus = new MutableLiveData<>();
    private final MutableLiveData<GearLevel> mGearLevel = new MutableLiveData<>();
    private final MutableLiveData<Float> mCarSpd = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mSnowModeData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mEvSysReadyData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mNGearWarningData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mExhibitionMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCruiseData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mElectricityPercent = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mQuickControlVisibleData = new MutableLiveData<>();
    private final MutableLiveData<PowerResponse> mPowerResponseData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mXPedalSwitchData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mAvailableMileageData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mTrailerSwitchData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mSsaSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mParkDropdownMenuData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mAwdSetting = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAsAutoEasyLoadMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mRwsModeData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mZwalkModeData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mVMCSystemStateData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mShowXpowerDialogData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mBrakeLightData = new MutableLiveData<>();

    public VcuViewModel() {
        this.mVcuController.registerCallback(this);
        this.mMcuController.registerCallback(this.mMcuCallback);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onDriveModeChanged(int mode) {
        DriveMode fromVcuDriveMode = DriveMode.fromVcuDriveMode(mode);
        if (fromVcuDriveMode != null) {
            this.mDriveMode.postValue(fromVcuDriveMode);
            if (!CarBaseConfig.getInstance().isSupportXSport() || fromVcuDriveMode == DriveMode.NoCommand) {
                return;
            }
            this.mXSportDriveMode.postValue(XSportDriveMode.NoCommand);
            return;
        }
        LogUtils.e(TAG, "on Error Drive Mode Changed: " + mode, false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onAwdSettingChanged(int mode) {
        if (-1 != mode) {
            this.mAwdSetting.postValue(Integer.valueOf(mode));
        } else {
            LogUtils.e(TAG, "on Error AwdSetting Changed: " + mode, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onAutoEasyLoadModeChanged(boolean mode) {
        this.mAsAutoEasyLoadMode.postValue(Boolean.valueOf(mode));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onRwsModeChanged(boolean ison) {
        Log.d(TAG, "onRwsModeChanged: " + ison);
        this.mRwsModeData.postValue(Boolean.valueOf(ison));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onZwalkModeChanged(boolean ison) {
        this.mZwalkModeData.postValue(Boolean.valueOf(ison));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onVMCSystemStateChanged(boolean isfailed) {
        this.mVMCSystemStateData.postValue(Boolean.valueOf(isfailed));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onRecycleGradeChanged(int grade) {
        int xSportDrivingMode;
        if (CarBaseConfig.getInstance().isSupportXSport() && ((xSportDrivingMode = this.mVcuController.getXSportDrivingMode()) == 3 || xSportDrivingMode == 1 || xSportDrivingMode == 4)) {
            LogUtils.d(TAG, "In XSport Mode " + xSportDrivingMode + ", and do not need to post Energy Value", false);
            return;
        }
        try {
            this.mEnergyRecycle.postValue(EnergyRecycleGrade.fromVcuRecycleState(grade));
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onChargeStatusChanged(int status) {
        int chargeGunStatus = this.mVcuController.getChargeGunStatus();
        LogUtils.d(TAG, "onChargeStatusChanged: gunStatus=" + chargeGunStatus + ", chargeStatus=" + status);
        this.mDCChargeStatusData.postValue(Boolean.valueOf(chargeGunStatus == 2 && status == 2));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handleGearChange(int gear) {
        try {
            this.mGearLevel.postValue(GearLevel.fromVcuGearLevel(gear));
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handleSnowModeChange(boolean enable) {
        if (BaseFeatureOption.getInstance().isSupportXSportRacerMode() && 4 == this.mVcuController.getXSportDrivingMode()) {
            LogUtils.i(TAG, "handleSnowModeChange in Racer Mode, Return", false);
        } else {
            this.mSnowModeData.postValue(Boolean.valueOf(enable));
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handleEvSysReady(boolean ready) {
        this.mEvSysReadyData.postValue(Boolean.valueOf(ready));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    public void handleExhibitionModeChanged(boolean enabled) {
        this.mExhibitionMode.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handleXSportDriveModeChange(int mode) {
        XSportDriveMode fromVcuState = XSportDriveMode.fromVcuState(mode);
        if (fromVcuState != null) {
            this.mXSportDriveMode.postValue(fromVcuState);
            if (XSportDriveMode.NoCommand != fromVcuState) {
                this.mDriveMode.postValue(DriveMode.NoCommand);
                return;
            }
            return;
        }
        LogUtils.e(TAG, "handle Error XSport Drive Mode: " + mode, false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handlePowerResponseModeChange(int mode) {
        PowerResponse fromVcuState = PowerResponse.fromVcuState(mode);
        if (fromVcuState != null) {
            this.mPowerResponseData.postValue(fromVcuState);
        } else {
            LogUtils.e(TAG, "handle Error Power Response Mode: " + mode, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handleXPedalModeSwitchChange(boolean on) {
        this.mXPedalSwitchData.postValue(Boolean.valueOf(on));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handleTrailerModeSwitchChange(boolean on) {
        this.mTrailerSwitchData.postValue(Boolean.valueOf(on));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handleSsaSwChange(boolean enabled) {
        this.mSsaSwData.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onChargeGunStatusChanged(int status) {
        try {
            this.mChargeGunStatus.postValue(ChargeGunStatus.fromVcuStatus(status));
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
        }
    }

    public LiveData<DriveMode> getDriveModeData() {
        return this.mDriveMode;
    }

    public MutableLiveData<XSportDriveMode> getXSportDriveModeData() {
        return this.mXSportDriveMode;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void mockPostDriveMode(DriveMode driveMode) {
        if (CarBaseConfig.isDeveloping()) {
            this.mDriveMode.postValue(driveMode);
        }
    }

    public LiveData<Integer> getAwdSettingData() {
        return this.mAwdSetting;
    }

    public MutableLiveData<Boolean> getAsAutoEasyLoadMode() {
        return this.mAsAutoEasyLoadMode;
    }

    public MutableLiveData<Boolean> getRwsModeData() {
        return this.mRwsModeData;
    }

    public MutableLiveData<Boolean> getZwalkModeData() {
        return this.mZwalkModeData;
    }

    public MutableLiveData<Boolean> getVMCSystemData() {
        return this.mVMCSystemStateData;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void mockPostEnergyRecycleGrade(EnergyRecycleGrade energyRecycleGrade) {
        if (CarBaseConfig.isDeveloping()) {
            this.mEnergyRecycle.postValue(energyRecycleGrade);
        }
    }

    protected void postEnergyRecycleGrade(EnergyRecycleGrade energyRecycleGrade) {
        this.mEnergyRecycle.postValue(energyRecycleGrade);
    }

    public DriveMode getDriveModeValue() {
        try {
            return DriveMode.fromVcuDriveMode(getDriveMode());
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
            return null;
        }
    }

    public DriveMode getDriveModeValueByUser() {
        try {
            return DriveMode.fromVcuDriveMode(getDriveModeByUser());
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
            return null;
        }
    }

    public XSportDriveMode getXSportDriveModeValue() {
        try {
            return XSportDriveMode.fromVcuState(getXSportDriveMode());
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
            return null;
        }
    }

    public void setXSportDriveModeByUser(XSportDriveMode xSportDriveMode) {
        setXSportDriveMode(xSportDriveMode.toVcuCmd());
    }

    public int onModesDrivingXSport(int mode) {
        return this.mVcuController.onModesDrivingXSport(mode);
    }

    public int getFirsTimeXSport(int mode) {
        String str = TAG;
        int i = 0;
        LogUtils.i(str, "getFirsTimeXSport, mode: " + mode, false);
        Uri.Builder builder = new Uri.Builder();
        builder.authority("com.xiaopeng.xsport.XSportService").path("isFirstEnterXSportDriveMode").appendQueryParameter("driveMode", String.valueOf(mode));
        try {
            int i2 = ((Boolean) ApiRouter.route(builder.build())).booleanValue() ? 1 : 0;
            try {
                LogUtils.i(str, "getFirsTimeXSport, result: " + i2, false);
                return i2;
            } catch (Exception e) {
                e = e;
                i = i2;
                e.printStackTrace();
                LogUtils.e(TAG, "getFirsTimeXSport() error.");
                return i;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public LiveData<EnergyRecycleGrade> getEnergyRecycleData() {
        return this.mEnergyRecycle;
    }

    public EnergyRecycleGrade getEnergyRecycle() {
        try {
            return EnergyRecycleGrade.fromVcuRecycleState(getEnergyRecycleGrade());
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
            return null;
        }
    }

    public EnergyRecycleGrade getEnergyRecycleByUser() {
        try {
            return EnergyRecycleGrade.fromVcuRecycleState(getEnergyRecycleGradeByUser());
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
            return null;
        }
    }

    public void setEnergyRecycleGrade(EnergyRecycleGrade grade) {
        setEnergyRecycleGrade(grade.toVcuRecycleCmd());
    }

    public void setEnergyRecycleGrade(EnergyRecycleGrade grade, boolean storeEnable) {
        setEnergyRecycleGrade(grade.toVcuRecycleCmd(), storeEnable);
    }

    public PowerResponse getPowerResponse() {
        try {
            return PowerResponse.fromVcuState(getPowerResponseMode());
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
            return null;
        }
    }

    public void setPowerResponse(PowerResponse response) {
        setPowerResponseMode(response.toVcuCmd());
    }

    public boolean isInDcCharge() {
        return this.mVcuController.getChargeGunStatus() == 2 && this.mVcuController.getChargeStatus() == 2;
    }

    public boolean isChargeGunLinked() {
        return (this.mVcuController.getChargeGunStatus() == 0 || this.mVcuController.getChargeGunStatus() == 7) ? false : true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean getParkDropdownMenuEnable() {
        return this.mVcuController.getParkDropdownMenuEnable();
    }

    public boolean getFirstEnterXpowerFlag() {
        return this.mVcuController.getFirstEnterXpowerFlag();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setParkDropdownMenuEnable(final boolean enable, boolean fromNapa) {
        this.mVcuController.setParkDropdownMenuEnable(enable);
        if (BaseFeatureOption.getInstance().isSupportNapa()) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.vcu.-$$Lambda$VcuViewModel$jb6VZhsP7SuFV4dIFlZ63Js0Ces
                @Override // java.lang.Runnable
                public final void run() {
                    VcuViewModel.this.lambda$setParkDropdownMenuEnable$0$VcuViewModel(enable);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setParkDropdownMenuEnable$0$VcuViewModel(final boolean enable) {
        this.mParkDropdownMenuData.postValue(Boolean.valueOf(enable));
    }

    public LiveData<GearLevel> getGearLevelData() {
        return this.mGearLevel;
    }

    public LiveData<Boolean> getDCChargeStatusData() {
        return this.mDCChargeStatusData;
    }

    public LiveData<ChargeGunStatus> getChargeGunStatusData() {
        return this.mChargeGunStatus;
    }

    public MutableLiveData<Boolean> getCruiseData() {
        return this.mCruiseData;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handleCruiseChanged(boolean isActive) {
        this.mCruiseData.postValue(Boolean.valueOf(isActive));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handleNGearWarningSwChanged(boolean enable) {
        this.mNGearWarningData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handleCarSpeedChanged(float speed) {
        this.mCarSpd.postValue(Float.valueOf(speed));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handleBrakeLightChanged(boolean on) {
        this.mBrakeLightData.postValue(Boolean.valueOf(on));
    }

    public LiveData<Boolean> getBrakeLightData() {
        return this.mBrakeLightData;
    }

    public LiveData<Float> getCarSpeedData() {
        return this.mCarSpd;
    }

    public LiveData<Boolean> getSnowModeData() {
        return this.mSnowModeData;
    }

    public GearLevel getGearLevelValue() {
        return GearLevel.fromVcuGearLevel(getGearLevel());
    }

    public LiveData<Boolean> getNGearWarningSwData() {
        return this.mNGearWarningData;
    }

    public LiveData<Boolean> getExhibitionModeData() {
        return this.mExhibitionMode;
    }

    public LiveData<Integer> getElectricityPercentData() {
        return this.mElectricityPercent;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onElecPercentChanged(int percent) {
        this.mElectricityPercent.postValue(Integer.valueOf(percent));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handleQuickControlVisible(boolean isQuickControlVisible) {
        this.mQuickControlVisibleData.postValue(Boolean.valueOf(isQuickControlVisible));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    protected void handleShowModeChanged(boolean on) {
        if (on) {
            if (CarBaseConfig.getInstance().isSupportWelcomeMode()) {
                this.mMcuController.setSeatWelcomeMode(false);
            }
            if (CarBaseConfig.getInstance().isSupportPsnWelcomeMode()) {
                this.mMmsController.setPsnSeatWelcomeMode(false);
            }
            if (CarBaseConfig.getInstance().isSupportSeatRhythm()) {
                this.mMmsController.setRhythmMode(this.mMmsController.getRhythmMode());
                HashSet hashSet = new HashSet();
                hashSet.add(0);
                hashSet.add(1);
                this.mMmsController.setRhythmEnable(hashSet, true);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuBaseViewModel
    public void handleShowXpowerDialog(boolean enable) {
        this.mShowXpowerDialogData.postValue(Boolean.valueOf(enable));
    }

    public LiveData<Boolean> getQuickControlVisibleData() {
        return this.mQuickControlVisibleData;
    }

    public MutableLiveData<PowerResponse> getPowerResponseData() {
        return this.mPowerResponseData;
    }

    public MutableLiveData<Boolean> getXPedalSwitchData() {
        return this.mXPedalSwitchData;
    }

    public MutableLiveData<Boolean> getTrailerSwitchData() {
        return this.mTrailerSwitchData;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
    public void onAvailableMileageChanged(int mileage) {
        this.mAvailableMileageData.postValue(Integer.valueOf(mileage));
    }

    public LiveData<Integer> getAvailableMileageData() {
        return this.mAvailableMileageData;
    }

    public LiveData<Boolean> getSsaSwData() {
        return this.mSsaSwData;
    }

    public LiveData<Boolean> getParkDropdownMenuData() {
        return this.mParkDropdownMenuData;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public void setAutoEasyLoadMode(boolean enable) {
        this.mVcuController.setAutoEasyLoadMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel
    public boolean isAutoEasyLoadingMode() {
        return this.mVcuController.getAutoEasyLoadMode();
    }

    public MutableLiveData<Boolean> getShowXpowerDialogData() {
        return this.mShowXpowerDialogData;
    }
}
