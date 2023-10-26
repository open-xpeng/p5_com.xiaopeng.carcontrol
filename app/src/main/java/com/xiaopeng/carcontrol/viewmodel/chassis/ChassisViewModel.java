package com.xiaopeng.carcontrol.viewmodel.chassis;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.carsettings.AsEngineerMode;
import com.xiaopeng.carcontrol.viewmodel.carsettings.AsHeight;
import com.xiaopeng.carcontrol.viewmodel.carsettings.AsSoft;
import com.xiaopeng.carcontrolmodule.R;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class ChassisViewModel extends ChassisBaseViewModel {
    protected static final float MAX_PRESSURE = 348.742f;
    public static final String TPMS_DEFAULT_PRESSURE = "-.-";
    private static final String UNIT = " bar";
    private final MutableLiveData<Boolean> mEsp = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAvhSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAvhFault = new MutableLiveData<>();
    private final MutableLiveData<SteeringEpsMode> mWheelEps = new MutableLiveData<>();
    private final MutableLiveData<Integer> mCdcMode = new MutableLiveData<>();
    private final MutableLiveData<TpmsCalibrationStatus> mTpmsCalibrationStatus = new MutableLiveData<>();
    private final MutableLiveData<String> mTpmsFlValue = new MutableLiveData<>();
    private final MutableLiveData<String> mTpmsFrValue = new MutableLiveData<>();
    private final MutableLiveData<String> mTpmsRlValue = new MutableLiveData<>();
    private final MutableLiveData<String> mTpmsRrValue = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mTpmsSystemFaultData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mTpmsAbnormalData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mTpmsFlTempWarningData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mTpmsFrTempWarningData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mTpmsRlTempWarningData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mTpmsRrTempWarningData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTpmsFlTempData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTpmsFrTempData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTpmsRlTempData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTpmsRrTempData = new MutableLiveData<>();
    private final MutableLiveData<TpmsWarningState> mTpmsFlWarningState = new MutableLiveData<>();
    private final MutableLiveData<TpmsWarningState> mTpmsFrWarningState = new MutableLiveData<>();
    private final MutableLiveData<TpmsWarningState> mTpmsRlWarningState = new MutableLiveData<>();
    private final MutableLiveData<TpmsWarningState> mTpmsRrWarningState = new MutableLiveData<>();
    private TpmsCalibrationStatus mLastCalibrateStatus = TpmsCalibrationStatus.NotFix;
    final MutableLiveData<Boolean> mEspFault = new MutableLiveData<>();
    final MutableLiveData<Boolean> mHdc = new MutableLiveData<>();
    final MutableLiveData<Boolean> mHdcFault = new MutableLiveData<>();
    final MutableLiveData<Boolean> mEbwData = new MutableLiveData<>();
    private final MutableLiveData<AsHeight> mAsHeightMode = new MutableLiveData<>();
    private final MutableLiveData<AsSoft> mAsSoftMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAsWelcomeMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAsEasyLoadingMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAsRepairMode = new MutableLiveData<>();
    private final MutableLiveData<AsEngineerMode> mAsEngineerData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mApbStatusData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mEspOffRoadStatusData = new MutableLiveData<>();
    private final MutableLiveData<AsCampingStatus> mAsCampingMode = new MutableLiveData<>();
    private final MutableLiveData<Integer> mAsLevlingMode = new MutableLiveData<>();
    private final MutableLiveData<TrailerHitchStatus> mTtmSwitchData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mTrailerModeData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTtmMotorMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mTtmSysErrData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAsSaveResult = new MutableLiveData<>();
    private final float mMaxPressure = CarBaseConfig.getInstance().getTpmsMaxPressure();

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

    private int toAsHeightLevel(int vcuDriveMode) {
        if (vcuDriveMode != 0 && vcuDriveMode != 1) {
            if (vcuDriveMode == 2) {
                return 2;
            }
            if (vcuDriveMode != 10 && vcuDriveMode == 16) {
                return 1;
            }
        }
        return 3;
    }

    public ChassisViewModel() {
        this.mEspController.registerCallback(this);
        this.mEpsController.registerCallback(this);
        this.mTpmsController.registerCallback(this);
        this.mBcmController.registerCallback(this.mBcmCallBack);
        if (CarBaseConfig.getInstance().isSupportTrailerHook()) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.-$$Lambda$4vf9JqcypYXKJS6W9ieBTmEdNs4
                @Override // java.lang.Runnable
                public final void run() {
                    ChassisViewModel.this.initTrailerMode();
                }
            });
        }
        if (CarBaseConfig.getInstance().isSupportCdcControl()) {
            this.mCdcController.registerCallback(this.mCdcCallback);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public String getTyrePressure(int position) {
        if (this.mCarConfig.isSupportManualTpmsCalibrate() && getTpmsCalibrationStatus() == TpmsCalibrationStatus.Fixing) {
            LogUtils.d("ChassisViewModel", "Tp is under calibration, return default pressure: --", false);
            return TPMS_DEFAULT_PRESSURE;
        }
        return formatPressureFormat(this.mTpmsController.getTyrePressure(position));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel, com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void calibrateTyrePressure() {
        if (TpmsCalibrationStatus.Fixing != this.mTpmsCalibrationStatus.getValue()) {
            super.calibrateTyrePressure();
        } else {
            LogUtils.w("ChassisViewModel", "Tyre is under calibrating, can not recalibrate", false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    void handleEspSwChanged(boolean enabled) {
        this.mEsp.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    void handleEspFaultChanged(boolean isFault) {
        this.mEspFault.postValue(Boolean.valueOf(isFault));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    void handleAvhSwChanged(boolean enabled) {
        this.mAvhSw.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    void handleAvhFaultChanged(boolean isFault) {
        this.mAvhFault.postValue(Boolean.valueOf(isFault));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    void handleHdcChanged(boolean enabled) {
        if (this.mCarConfig.isSupportHdc()) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HDC_BOOL, Boolean.valueOf(enabled));
            this.mHdc.postValue(Boolean.valueOf(enabled));
            if (enabled || !App.isMainProcess()) {
                return;
            }
            LogUtils.i("ChassisViewModel", "HDC changed to false, re-send false to HDC");
            this.mEspController.setHdc(false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
    public void onHdcFaultChanged(boolean isFault) {
        this.mHdcFault.postValue(Boolean.valueOf(isFault));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
    public void onApbSystemStatusChanged(int status) {
        this.mApbStatusData.postValue(Integer.valueOf(status));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
    public void onEspOffRoadStatusChanged(boolean on) {
        this.mEspOffRoadStatusData.postValue(Boolean.valueOf(on));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    public void handleSteeringEpsChanged(int eps) {
        int xSportDrivingMode;
        SteeringEpsMode fromEpsState = SteeringEpsMode.fromEpsState(eps);
        if (fromEpsState != null) {
            if (CarBaseConfig.getInstance().isSupportXSport() && ((xSportDrivingMode = this.mVcuController.getXSportDrivingMode()) == 3 || xSportDrivingMode == 1 || xSportDrivingMode == 4)) {
                LogUtils.d("ChassisViewModel", "In XSport Mode " + xSportDrivingMode + ", and do not need to post EPS Value", false);
                return;
            }
            this.mWheelEps.postValue(fromEpsState);
            changeEspForEpsChanged(fromEpsState);
            return;
        }
        LogUtils.e("ChassisViewModel", "handle error eps state", false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    void handleCdcModeChanged(int cdc) {
        this.mCdcMode.postValue(Integer.valueOf(cdc));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    void handleEbwChanged(boolean enabled) {
        this.mEbwData.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    protected void handleAsHeightModeChanged(int mode) {
        AsHeight fromBcmState = AsHeight.fromBcmState(mode);
        if (fromBcmState != null) {
            this.mAsHeightMode.postValue(fromBcmState);
        } else {
            LogUtils.e("ChassisViewModel", "Error As HeightMode: " + fromBcmState, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    protected void handleAsSoftModeChanged(int mode) {
        AsSoft fromBcmState = AsSoft.fromBcmState(mode);
        if (fromBcmState != null) {
            this.mAsSoftMode.postValue(fromBcmState);
        } else {
            LogUtils.e("ChassisViewModel", "Error As Soft: " + mode, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    protected void handleAsWelcomeModeChanged(boolean mode) {
        this.mAsWelcomeMode.postValue(Boolean.valueOf(mode));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    protected void handleAsEasyLoadingModeChanged(boolean mode) {
        this.mAsEasyLoadingMode.postValue(Boolean.valueOf(mode));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    protected void handleAsRepairModeChanged(boolean mode) {
        this.mAsRepairMode.postValue(Boolean.valueOf(mode));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    protected void handleAsEngineerModeChanged(int status) {
        AsEngineerMode fromBcmState = AsEngineerMode.fromBcmState(status);
        if (fromBcmState != null) {
            this.mAsEngineerData.postValue(fromBcmState);
        } else {
            LogUtils.e("ChassisViewModel", "Error As EngineerMode: " + status, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    protected void handleApbSystemStatusChanged(int status) {
        this.mApbStatusData.postValue(Integer.valueOf(status));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    protected void handleAsCampingModeChanged(boolean mode) {
        postAsCampingMode(mode, this.mBcmController.getAsAutoLevelingResult());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    protected void handleAsLevlingModeChanged(int mode) {
        postAsCampingMode(this.mBcmController.getAsCampingModeSwitch(), mode);
        this.mAsLevlingMode.postValue(Integer.valueOf(mode));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    public void handleTtmSwitchStatusChanged(int status) {
        super.handleTtmSwitchStatusChanged(status);
        TrailerHitchStatus fromBcCmd = TrailerHitchStatus.fromBcCmd(status);
        if (fromBcCmd != null) {
            this.mTtmSwitchData.postValue(fromBcCmd);
            LogUtils.d("ChassisViewModel", "handleTtmSwitchStatusChanged status= " + status, false);
            if (TrailerHitchStatus.Close == fromBcCmd) {
                this.mTrailerModeData.postValue(false);
                return;
            }
            return;
        }
        LogUtils.e("ChassisViewModel", "Error TtmSwitch Status: " + status, false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    protected void handleTrailerModeChanged(boolean mode) {
        this.mTrailerModeData.postValue(Boolean.valueOf(mode));
        if (App.isMainProcess()) {
            CarControl.Quick.putInt(App.getInstance().getContentResolver(), CarControl.Quick.TRAILER_MODE_SW, mode ? 1 : 0);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    protected void handleTtmHookMotorStatusChanged(int status) {
        this.mTtmMotorMode.postValue(Integer.valueOf(status));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel
    protected void handleTtmSysErrStatusChanged(boolean isErr) {
        this.mTtmSysErrData.postValue(Boolean.valueOf(isErr));
    }

    protected void changeEspForEpsChanged(SteeringEpsMode epsMode) {
        if (this.mCarConfig.isSupportEspSportMode() && App.isMainProcess()) {
            boolean isEvSysReady = this.mVcuController.isEvSysReady();
            int esp = this.mEspController.getEsp();
            LogUtils.d("ChassisViewModel", "Current ESP state=" + esp + ", isEvReady=" + isEvSysReady, false);
            if (epsMode == SteeringEpsMode.Sport) {
                if (isEvSysReady) {
                    if (esp == 1 || esp == 3) {
                        LogUtils.d("ChassisViewModel", "EPS switched to Sport mode, and ESP need to be switched to Sport mode too", false);
                        this.mEspController.setEspSw(true);
                        this.mEspController.setEspModeSport();
                    }
                }
            } else if ((epsMode == SteeringEpsMode.Standard || epsMode == SteeringEpsMode.Soft) && isEvSysReady && esp == 2) {
                this.mEspController.setEspSw(true);
                LogUtils.d("ChassisViewModel", "EPS switched to non Sport mode, and ESP need to be switched to Normal mode too", false);
                this.mEspController.setEsp(true);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController.Callback
    public void onTpmsPressureChanged(int position, float pressure) {
        MutableLiveData<String> mutableLiveData;
        String str;
        if (position == 1) {
            mutableLiveData = this.mTpmsFlValue;
            str = QuickSettingConstants.KEY_TIRE_VALUE_FL;
        } else if (position == 2) {
            mutableLiveData = this.mTpmsFrValue;
            str = QuickSettingConstants.KEY_TIRE_VALUE_FR;
        } else if (position == 3) {
            mutableLiveData = this.mTpmsRlValue;
            str = QuickSettingConstants.KEY_TIRE_VALUE_RL;
        } else if (position != 4) {
            mutableLiveData = null;
            str = "";
        } else {
            mutableLiveData = this.mTpmsRrValue;
            str = QuickSettingConstants.KEY_TIRE_VALUE_RR;
        }
        if (mutableLiveData != null) {
            if (this.mCarConfig.isSupportManualTpmsCalibrate() && getTpmsCalibrationStatus() == TpmsCalibrationStatus.Fixing) {
                LogUtils.d("ChassisViewModel", "Tp is under calibration, return default pressure:--");
                mutableLiveData.postValue(TPMS_DEFAULT_PRESSURE);
                if (BaseFeatureOption.getInstance().isSupportInterTransportMode()) {
                    QuickSettingManager.getInstance().onSignalCallback(str, TPMS_DEFAULT_PRESSURE);
                    return;
                }
                return;
            }
            mutableLiveData.postValue(formatPressureFormat(pressure));
            if (BaseFeatureOption.getInstance().isSupportInterTransportMode()) {
                QuickSettingManager.getInstance().onSignalCallback(str, formatPressureFormat(pressure));
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController.Callback
    public void onTpmsCalibrationStateChanged(int state) {
        TpmsCalibrationStatus fromTpmsState = TpmsCalibrationStatus.fromTpmsState(state);
        if (fromTpmsState != null) {
            this.mTpmsCalibrationStatus.postValue(fromTpmsState);
            updateTyrePressure(fromTpmsState);
            return;
        }
        LogUtils.e("ChassisViewModel", "Error Tpms CalibrationState Changed: " + state, false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController.Callback
    public void onTpmsSystemFaultStateChanged(boolean fault) {
        this.mTpmsSystemFaultData.postValue(Boolean.valueOf(fault));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController.Callback
    public void onTpmsAbnormalWarningStateChanged(boolean abnormal) {
        this.mTpmsAbnormalData.postValue(Boolean.valueOf(abnormal));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController.Callback
    public void onTpmsTempWarningStateChanged(boolean[] states) {
        if (states == null || states.length < 4) {
            return;
        }
        this.mTpmsFlTempWarningData.postValue(Boolean.valueOf(states[0]));
        this.mTpmsFrTempWarningData.postValue(Boolean.valueOf(states[1]));
        this.mTpmsRlTempWarningData.postValue(Boolean.valueOf(states[2]));
        this.mTpmsRrTempWarningData.postValue(Boolean.valueOf(states[3]));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController.Callback
    public void onTpmsTempChanged(int[] temp) {
        if (temp == null || temp.length < 4) {
            return;
        }
        this.mTpmsFlTempData.postValue(Integer.valueOf(temp[0]));
        this.mTpmsFrTempData.postValue(Integer.valueOf(temp[1]));
        this.mTpmsRlTempData.postValue(Integer.valueOf(temp[2]));
        this.mTpmsRrTempData.postValue(Integer.valueOf(temp[3]));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController.Callback
    public void onTpmsWarningStateChanged(int[] states) {
        if (states == null || states.length < 4) {
            return;
        }
        this.mTpmsFlWarningState.postValue(TpmsWarningState.fromTpms(states[0]));
        this.mTpmsFrWarningState.postValue(TpmsWarningState.fromTpms(states[1]));
        this.mTpmsRlWarningState.postValue(TpmsWarningState.fromTpms(states[2]));
        this.mTpmsRrWarningState.postValue(TpmsWarningState.fromTpms(states[3]));
        if (BaseFeatureOption.getInstance().isSupportInterTransportMode()) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_TIRE_WARNING, (TpmsWarningState.fromTpms(states[0]) != TpmsWarningState.NORMAL) + MqttTopic.MULTI_LEVEL_WILDCARD + (TpmsWarningState.fromTpms(states[1]) != TpmsWarningState.NORMAL) + MqttTopic.MULTI_LEVEL_WILDCARD + (TpmsWarningState.fromTpms(states[2]) != TpmsWarningState.NORMAL) + MqttTopic.MULTI_LEVEL_WILDCARD + (TpmsWarningState.fromTpms(states[3]) != TpmsWarningState.NORMAL));
        }
    }

    public TpmsCalibrationStatus getTpmsCalibrationStatus() {
        try {
            return TpmsCalibrationStatus.fromTpmsState(getTpmsCalibrateState());
        } catch (Exception e) {
            LogUtils.d("ChassisViewModel", e.getMessage(), false);
            return null;
        }
    }

    public TrailerHitchStatus getTrailerHitchStatus() {
        try {
            return TrailerHitchStatus.fromBcCmd(getTtmSwitchStatusForUI());
        } catch (Exception e) {
            LogUtils.d("ChassisViewModel", e.getMessage(), false);
            return null;
        }
    }

    public LiveData<Boolean> getEspModeData() {
        return this.mEsp;
    }

    public LiveData<Boolean> getAvhSwData() {
        return this.mAvhSw;
    }

    public LiveData<Boolean> getAvhFaultData() {
        return this.mAvhFault;
    }

    public LiveData<Boolean> getEspFaultData() {
        return this.mEspFault;
    }

    public LiveData<Boolean> getHdcData() {
        return this.mHdc;
    }

    public LiveData<Boolean> getHdcFaultData() {
        return this.mHdcFault;
    }

    public LiveData<Boolean> getEbwData() {
        return this.mEbwData;
    }

    public LiveData<SteeringEpsMode> getSteeringEpsData() {
        return this.mWheelEps;
    }

    public LiveData<Integer> getCdcModeData() {
        return this.mCdcMode;
    }

    public LiveData<AsHeight> getAsHeightModeData() {
        return this.mAsHeightMode;
    }

    public LiveData<AsSoft> getAsSoftModeData() {
        return this.mAsSoftMode;
    }

    public MutableLiveData<Boolean> getAsWelcomeMode() {
        return this.mAsWelcomeMode;
    }

    public MutableLiveData<Boolean> getAsEasyLoadingMode() {
        return this.mAsEasyLoadingMode;
    }

    public LiveData<Boolean> getAsRepairModeData() {
        return this.mAsRepairMode;
    }

    public MutableLiveData<AsEngineerMode> getAsEngineerData() {
        return this.mAsEngineerData;
    }

    public MutableLiveData<Integer> getApbStatusData() {
        return this.mApbStatusData;
    }

    public MutableLiveData<Boolean> getEspOffRoadStatusData() {
        return this.mEspOffRoadStatusData;
    }

    public MutableLiveData<AsCampingStatus> getAsCampingModeData() {
        return this.mAsCampingMode;
    }

    public MutableLiveData<Integer> getAsLevlingModeData() {
        return this.mAsLevlingMode;
    }

    public MutableLiveData<TrailerHitchStatus> getTtmSwitchData() {
        return this.mTtmSwitchData;
    }

    public MutableLiveData<Boolean> getTrailerModeData() {
        return this.mTrailerModeData;
    }

    public MutableLiveData<Integer> geTtmMotorModeData() {
        return this.mTtmMotorMode;
    }

    public MutableLiveData<Boolean> getTtmSysErrData() {
        return this.mTtmSysErrData;
    }

    public void setSteeringEps(SteeringEpsMode mode) {
        setSteeringEps(mode.toEpsCmd(), true);
    }

    public SteeringEpsMode getSteeringEps(SteeringEpsMode defaultMode) {
        try {
            return SteeringEpsMode.fromEpsState(getSteeringEps());
        } catch (Exception e) {
            LogUtils.d("ChassisViewModel", e.getMessage(), false);
            return defaultMode;
        }
    }

    public void setAsHeightMode(AsHeight height) {
        setAsHeightMode(height, true);
    }

    public void setAsHeightMode(AsHeight height, boolean storeEnable) {
        if (height != null) {
            setAirSuspensionHeightMode(height.toBcmCmd(), storeEnable);
            handleAsHeightModeChanged(height.toBcmCmd());
        }
    }

    public AsHeight getAsHeightMode() {
        try {
            return AsHeight.fromBcmState(getAirSuspensionHeightMode());
        } catch (Exception e) {
            LogUtils.d("ChassisViewModel", e.getMessage(), false);
            return null;
        }
    }

    public void setAsEngineerMode(AsEngineerMode mode) {
        if (mode != null) {
            setEngineeringModeStatus(mode.toBcmCmd());
        }
    }

    public AsEngineerMode getAsEngineerMode() {
        try {
            return AsEngineerMode.fromBcmState(getEngineeringModeStatus());
        } catch (Exception e) {
            LogUtils.d("ChassisViewModel", e.getMessage(), false);
            return null;
        }
    }

    public void resumeAsHeight() {
        int driveMode = this.mVcuController.getDriveMode();
        boolean customerModeFlag = this.mBcmController.getCustomerModeFlag();
        int airSuspensionHeightSp = customerModeFlag ? this.mBcmController.getAirSuspensionHeightSp() : toAsHeightLevel(driveMode);
        int i = 1;
        if (CarBaseConfig.getInstance().isSupportXpu()) {
            int asTargetMinHeightRequest = this.mXpuController.getAsTargetMinHeightRequest();
            r4 = asTargetMinHeightRequest != 0 ? asTargetMinHeightRequest : 6;
            int asTargetMaxHeightRequest = this.mXpuController.getAsTargetMaxHeightRequest();
            if (asTargetMaxHeightRequest == 0) {
                i = r4;
                r4 = 1;
            } else {
                i = r4;
                r4 = asTargetMaxHeightRequest;
            }
        }
        LogUtils.i("ChassisViewModel", "resumeAsHeight targetHeight: " + airSuspensionHeightSp + ", Current DriveMode: " + driveMode, false);
        if (airSuspensionHeightSp >= r4 && airSuspensionHeightSp <= i) {
            if (customerModeFlag) {
                this.mBcmController.setAirSuspensionHeight(this.mBcmController.getAirSuspensionHeightSp());
            } else {
                this.mBcmController.setAsDrivingMode(toAsDriveMode(driveMode));
            }
            this.mBcmController.setCustomerModeFlag(customerModeFlag);
            return;
        }
        LogUtils.i("ChassisViewModel", "Invalid AS Target Height Lvl Set", false);
    }

    public void setAsSoftMode(AsSoft softMode) {
        if (softMode != null) {
            setAirSuspensionSoftMode(softMode.toBcmCmd());
        }
    }

    public AsSoft getAsSoftMode() {
        try {
            return AsSoft.fromBcmState(getAirSuspensionSoftMode());
        } catch (Exception e) {
            LogUtils.d("ChassisViewModel", e.getMessage(), false);
            return null;
        }
    }

    public AsCampingStatus getAsCampingStatus() {
        if (this.mBcmController.getAsCampingModeSwitch()) {
            return AsCampingStatus.Opened;
        }
        int asAutoLevelingResult = this.mBcmController.getAsAutoLevelingResult();
        if (1 == asAutoLevelingResult || 2 == asAutoLevelingResult) {
            return AsCampingStatus.Opening;
        }
        return AsCampingStatus.Closed;
    }

    public LiveData<TpmsCalibrationStatus> getTpmsCalibrationStatusData() {
        return this.mTpmsCalibrationStatus;
    }

    public LiveData<String> getTyrePressureData(int position) {
        if (position != 1) {
            if (position != 2) {
                if (position != 3) {
                    if (position != 4) {
                        return null;
                    }
                    return this.mTpmsRrValue;
                }
                return this.mTpmsRlValue;
            }
            return this.mTpmsFrValue;
        }
        return this.mTpmsFlValue;
    }

    public LiveData<Boolean> getTpmsSystemFaultData() {
        return this.mTpmsSystemFaultData;
    }

    public LiveData<Boolean> getTpmsAbnormalData() {
        return this.mTpmsAbnormalData;
    }

    public LiveData<Boolean> getTpmsTempWarningData(int position) {
        if (position != 1) {
            if (position != 2) {
                if (position != 3) {
                    if (position != 4) {
                        return null;
                    }
                    return this.mTpmsRrTempWarningData;
                }
                return this.mTpmsRlTempWarningData;
            }
            return this.mTpmsFrTempWarningData;
        }
        return this.mTpmsFlTempWarningData;
    }

    public LiveData<Integer> getTpmsTempData(int position) {
        if (position != 1) {
            if (position != 2) {
                if (position != 3) {
                    if (position != 4) {
                        return null;
                    }
                    return this.mTpmsRrTempData;
                }
                return this.mTpmsRlTempData;
            }
            return this.mTpmsFrTempData;
        }
        return this.mTpmsFlTempData;
    }

    public LiveData<TpmsWarningState> getTpmsWarningData(int position) {
        if (position != 1) {
            if (position != 2) {
                if (position != 3) {
                    if (position != 4) {
                        return null;
                    }
                    return this.mTpmsRrWarningState;
                }
                return this.mTpmsRlWarningState;
            }
            return this.mTpmsFrWarningState;
        }
        return this.mTpmsFlWarningState;
    }

    public TpmsWarningState[] getTpmsWarningStates() {
        int[] tpmsWarningState = this.mTpmsController.getTpmsWarningState();
        if (tpmsWarningState == null || tpmsWarningState.length < 4) {
            return null;
        }
        TpmsWarningState[] tpmsWarningStateArr = new TpmsWarningState[tpmsWarningState.length];
        for (int i = 0; i < tpmsWarningState.length; i++) {
            tpmsWarningStateArr[i] = TpmsWarningState.fromTpms(tpmsWarningState[i]);
        }
        return tpmsWarningStateArr;
    }

    private void updateTyrePressure(TpmsCalibrationStatus status) {
        if (this.mLastCalibrateStatus != null) {
            if (this.mCarConfig.isSupportManualTpmsCalibrate() && this.mLastCalibrateStatus != TpmsCalibrationStatus.Fixing && status == TpmsCalibrationStatus.Fixing) {
                this.mTpmsFlValue.postValue(TPMS_DEFAULT_PRESSURE);
                this.mTpmsFrValue.postValue(TPMS_DEFAULT_PRESSURE);
                this.mTpmsRlValue.postValue(TPMS_DEFAULT_PRESSURE);
                this.mTpmsRrValue.postValue(TPMS_DEFAULT_PRESSURE);
                if (BaseFeatureOption.getInstance().isSupportInterTransportMode()) {
                    QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_TIRE_VALUE_FL, TPMS_DEFAULT_PRESSURE);
                    QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_TIRE_VALUE_FR, TPMS_DEFAULT_PRESSURE);
                    QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_TIRE_VALUE_RL, TPMS_DEFAULT_PRESSURE);
                    QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_TIRE_VALUE_RR, TPMS_DEFAULT_PRESSURE);
                }
            } else if (this.mLastCalibrateStatus == TpmsCalibrationStatus.Fixing && status != TpmsCalibrationStatus.Fixing) {
                this.mTpmsFlValue.postValue(getTyrePressure(1));
                this.mTpmsFrValue.postValue(getTyrePressure(2));
                this.mTpmsRlValue.postValue(getTyrePressure(3));
                this.mTpmsRrValue.postValue(getTyrePressure(4));
                if (BaseFeatureOption.getInstance().isSupportInterTransportMode()) {
                    QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_TIRE_VALUE_FL, getTyrePressure(1));
                    QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_TIRE_VALUE_FR, getTyrePressure(2));
                    QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_TIRE_VALUE_RL, getTyrePressure(3));
                    QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_TIRE_VALUE_RR, getTyrePressure(4));
                }
            }
        }
        this.mLastCalibrateStatus = status;
    }

    protected String formatPressureFormat(float tyrePressure) {
        int i = (tyrePressure > this.mMaxPressure ? 1 : (tyrePressure == this.mMaxPressure ? 0 : -1));
        String str = TPMS_DEFAULT_PRESSURE;
        if (i > 0) {
            return TPMS_DEFAULT_PRESSURE;
        }
        float f = tyrePressure / 100.0f;
        if (f >= 0.1f) {
            str = String.valueOf(Math.round(f * 10.0f) / 10.0f);
        }
        return !this.mCarConfig.isNewEspArch() ? str + UNIT : str;
    }

    public void controlEpb() {
        if (this.mVcuController.getGearLevel() == 4 || this.mVcuController.getCarSpeed() > 3.0f || !this.mVcuController.getBreakPedalStatus()) {
            NotificationHelper.getInstance().showToast(R.string.epb_op_failed_tips);
            return;
        }
        int apbSystemStatus = this.mEspController.getApbSystemStatus();
        LogUtils.d("ChassisViewModel", "Current apb status is: " + apbSystemStatus, false);
        if (apbSystemStatus == 3 || apbSystemStatus == 4 || apbSystemStatus == 0) {
            return;
        }
        setEpbSystemStatus(apbSystemStatus == 1);
    }

    private void postAsCampingMode(boolean campingMode, int levelResult) {
        if (campingMode) {
            this.mAsCampingMode.postValue(AsCampingStatus.Opened);
        } else if (1 == levelResult) {
            this.mAsCampingMode.postValue(AsCampingStatus.Opening);
        } else if (2 == levelResult) {
            this.mAsCampingMode.postValue(AsCampingStatus.Opening);
        } else {
            this.mAsCampingMode.postValue(AsCampingStatus.Closed);
        }
    }

    public void updateAsSaveResult(boolean result) {
        this.mAsSaveResult.postValue(Boolean.valueOf(result));
    }

    public LiveData<Boolean> getAsSaveResultData() {
        return this.mAsSaveResult;
    }
}
