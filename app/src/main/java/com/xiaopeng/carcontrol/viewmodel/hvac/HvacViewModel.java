package com.xiaopeng.carcontrol.viewmodel.hvac;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.carmanager.controller.IHvacController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.model.HvacDataModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;

/* loaded from: classes2.dex */
public class HvacViewModel extends HvacBaseViewModel {
    private static final long GET_WEATHER_INTERVAL_TIME = 600000;
    public static final int SEAT_HEAT_TYPE_MAIN = 0;
    public static final int SEAT_HEAT_TYPE_PSN = 1;
    public static final int SEAT_HEAT_TYPE_RL = 2;
    public static final int SEAT_HEAT_TYPE_RR = 3;
    public static final int SEAT_VENT_TYPE_FL = 0;
    public static final int SEAT_VENT_TYPE_FR = 1;
    private static final String WEATHER_URL = "http://xmart.deploy-test.xiaopeng.com/assistant/assistant_skills/batch";
    private long mLastWeatherTime;
    private final MutableLiveData<Boolean> mHvacPowerMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacAutoMode = new MutableLiveData<>();
    private final MutableLiveData<AcHeatNatureMode> mHvacTempAcMode = new MutableLiveData<>();
    private final MutableLiveData<Float> mHvacTempDriver = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacDriverSyncMode = new MutableLiveData<>();
    private final MutableLiveData<Float> mHvacTempPsn = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacPsnSyncMode = new MutableLiveData<>();
    private final MutableLiveData<Float> mHvacTempInner = new MutableLiveData<>();
    private final MutableLiveData<Float> mHvacTempExt = new MutableLiveData<>();
    private final MutableLiveData<HvacWindTempMode> mHvacWindTempMode = new MutableLiveData<>();
    private final MutableLiveData<Integer> mHvacWindLevel = new MutableLiveData<>();
    private final MutableLiveData<HvacWindBlowMode> mHvacWindMode = new MutableLiveData<>();
    private final MutableLiveData<HvacWindBlowMode> mHvacRearWindMode = new MutableLiveData<>();
    private final MutableLiveData<HvacCirculationMode> mHvacCirculation = new MutableLiveData<>();
    private final MutableLiveData<HvacCirculationTime> mHvacCirculationTime = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacFrontDefrost = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mMirrorHeat = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mFrontMirrorHeat = new MutableLiveData<>();
    private final MutableLiveData<SeatHeatLevel> mSeatHeatLevel = new MutableLiveData<>();
    private final MutableLiveData<SeatVentLevel> mSeatVentLevel = new MutableLiveData<>();
    private final MutableLiveData<SeatHeatLevel> mPsnSeatHeatLevel = new MutableLiveData<>();
    private final MutableLiveData<SeatVentLevel> mPsnSeatVentLevel = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSteerHeatLevel = new MutableLiveData<>();
    private final MutableLiveData<Integer> mHvacQualityInner = new MutableLiveData<>();
    private final MutableLiveData<Integer> mHvacOutsidePM25 = new MutableLiveData<>();
    private final MutableLiveData<HvacSwitchStatus> mHvacEconMode = new MutableLiveData<>();
    private final MutableLiveData<HvacSwitchStatus> mHvacAqsMode = new MutableLiveData<>();
    private final MutableLiveData<AqsSensitivityLevel> mHvacAqsLevel = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacAutoDefogSt = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacAutoDefogWorkSt = new MutableLiveData<>();
    private final MutableLiveData<HvacEavWindMode> mHvacEavDriver = new MutableLiveData<>();
    private final MutableLiveData<HvacEavWindMode> mHvacEavPsn = new MutableLiveData<>();
    private final MutableLiveData<HvacSwitchStatus> mHvacEavSweepMode = new MutableLiveData<>();
    private final MutableLiveData<HvacSwitchStatus> mHvacIonizerMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mSmartHvacData = new MutableLiveData<>();
    private final MutableLiveData<HvacAirAutoProtect> mAirProtectData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAirDistributionAutoMode = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDriverLeftHPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDriverLeftVPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDriverRightHPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDriverRightVPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mPsnLeftHPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mPsnLeftVPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mPsnRightHPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mPsnRightVPos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacSelfDry = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacRapidCooling = new MutableLiveData<>();
    private final MutableLiveData<Integer> mHvacRapidCoolingTime = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacDeodorant = new MutableLiveData<>();
    private final MutableLiveData<Integer> mHvacDeodorantTime = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacRapidHeat = new MutableLiveData<>();
    private final MutableLiveData<Integer> mHvacRapidHeatTime = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacSingleMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacQualityPurgeMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacSingleModeActive = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mFanSpeedAutoMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAirIntakeAutoMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacSfsSwitchData = new MutableLiveData<>();
    protected final MutableLiveData<SeatHeatLevel> mRRSeatHeatLevel = new MutableLiveData<>();
    private final MutableLiveData<SeatHeatLevel> mRLSeatHeatLevel = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacRearPowerMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacRearAutoMode = new MutableLiveData<>();
    private final MutableLiveData<Float> mHvacRearTempDriver = new MutableLiveData<>();
    private final MutableLiveData<Float> mHvacRearTempPsn = new MutableLiveData<>();
    private final MutableLiveData<Float> mHvacThirdTemp = new MutableLiveData<>();
    private final MutableLiveData<Integer> mHvacRearWindLevel = new MutableLiveData<>();
    private final MutableLiveData<HvacWindBlowMode> mHvacThirdWindMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacNewFreshSwitch = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHvacNIVent = new MutableLiveData<>();

    public HvacViewModel() {
        this.mHvacController.registerCallback(this);
        this.mBcmController.registerCallback(this.mBcmCallback);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacModeData(boolean store) {
        LogUtils.d("HvacViewModel", "setHvacModeData store:" + store, false);
        if (store) {
            storeHvacModeData();
        } else {
            reStoreHvacModeData();
        }
    }

    public void storeHvacModeData() {
        if (HvacDataModel.getInstance().getHvacModeDataSwitch()) {
            LogUtils.e("HvacViewModel", "storeHvacModeData HvacModeDataSwitch is true.", false);
            return;
        }
        HvacDataModel.getInstance().setHvacModeDataSwitch(true);
        storeSeatLevel();
        float hvacTempDriver = this.mHvacController.getHvacTempDriver();
        float hvacTempPsn = this.mHvacController.getHvacTempPsn();
        int hvacWindSpeedLevel = this.mHvacController.getHvacWindSpeedLevel();
        int hvacWindBlowMode = this.mHvacController.getHvacWindBlowMode();
        boolean isHvacAutoModeOn = this.mHvacController.isHvacAutoModeOn();
        boolean isHvacAcModeOn = this.mHvacController.isHvacAcModeOn();
        int hvacCirculationMode = this.mHvacController.getHvacCirculationMode();
        HvacDataModel.getInstance().setHvacModeStateDvrTemp(hvacTempDriver);
        HvacDataModel.getInstance().setHvacModeStatePsnTemp(hvacTempPsn);
        HvacDataModel.getInstance().setHvacModeStateWindLevel(hvacWindSpeedLevel);
        HvacDataModel.getInstance().setHvacModeStateWindBlowMode(hvacWindBlowMode);
        HvacDataModel.getInstance().setHvacModeStateAutoMode(isHvacAutoModeOn);
        HvacDataModel.getInstance().setHvacModeStateACMode(isHvacAcModeOn);
        HvacDataModel.getInstance().setHvacModeStateCirculationMode(hvacCirculationMode);
    }

    private void storeSeatLevel() {
        if (CarBaseConfig.getInstance().isSupportDrvSeatVent()) {
            HvacDataModel.getInstance().setHvacModeStateSeatVent(this.mBcmController.getSeatVentLevel());
        }
        if (CarBaseConfig.getInstance().isSupportDrvSeatHeat()) {
            HvacDataModel.getInstance().setHvacModeStateSeatHeat(this.mBcmController.getSeatHeatLevel());
        }
        if (CarBaseConfig.getInstance().isSupportPsnSeatVent()) {
            HvacDataModel.getInstance().setHvacModeStatePsnSeatVent(this.mBcmController.getPsnSeatVentLevel());
        }
        if (CarBaseConfig.getInstance().isSupportPsnSeatHeat()) {
            HvacDataModel.getInstance().setHvacModeStatePsnSeatHeat(this.mBcmController.getPsnSeatHeatLevel());
        }
        if (CarBaseConfig.getInstance().isSupportRearSeatHeat()) {
            int rLSeatHeatLevel = this.mBcmController.getRLSeatHeatLevel();
            int rRSeatHeatLevel = this.mBcmController.getRRSeatHeatLevel();
            HvacDataModel.getInstance().setHvacModeStateRlSeatHeat(rLSeatHeatLevel);
            HvacDataModel.getInstance().setHvacModeStateRrSeatHeat(rRSeatHeatLevel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: reStoreModeSeatLevel */
    public void lambda$reStoreHvacModeData$6$HvacViewModel() {
        if (CarBaseConfig.getInstance().isSupportDrvSeatVent()) {
            this.mBcmController.setSeatVentLevel(HvacDataModel.getInstance().getHvacModeStateSeatVent(), true);
        }
        if (CarBaseConfig.getInstance().isSupportDrvSeatHeat()) {
            this.mBcmController.setSeatHeatLevel(HvacDataModel.getInstance().getHvacModeStateSeatHeat(), true);
        }
        if (CarBaseConfig.getInstance().isSupportPsnSeatVent()) {
            this.mBcmController.setPsnSeatVentLevel(HvacDataModel.getInstance().getHvacModeStatePsnSeatVent());
        }
        if (CarBaseConfig.getInstance().isSupportPsnSeatHeat()) {
            this.mBcmController.setPsnSeatHeatLevel(HvacDataModel.getInstance().getHvacModeStatePsnSeatHeat());
        }
        if (CarBaseConfig.getInstance().isSupportRearSeatHeat()) {
            HvacDataModel.getInstance().getHvacModeStateRlSeatHeat();
            HvacDataModel.getInstance().getHvacModeStateRrSeatHeat();
        }
    }

    public void reStoreHvacModeData() {
        if (!HvacDataModel.getInstance().getHvacModeDataSwitch()) {
            LogUtils.e("HvacViewModel", "reStoreHvacModeData HvacModeDataSwitch is false.", false);
        } else if (!isLocalIgOn()) {
            LogUtils.e("HvacViewModel", "reStoreHvacModeData isLocalIgOn is false.", false);
        } else {
            HvacDataModel.getInstance().setHvacModeDataSwitch(false);
            float hvacModeStateDvrTemp = HvacDataModel.getInstance().getHvacModeStateDvrTemp();
            if (hvacModeStateDvrTemp >= 18.0f && hvacModeStateDvrTemp <= 32.0f) {
                this.mHvacController.setHvacTempDriver(hvacModeStateDvrTemp);
            }
            ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$bopjgh1jV7-VjeJn_cI4bRfmkgM
                @Override // java.lang.Runnable
                public final void run() {
                    HvacViewModel.this.lambda$reStoreHvacModeData$0$HvacViewModel();
                }
            }, 150L);
            ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$11XtRznGWUrHDuf1LEuAQm33N6I
                @Override // java.lang.Runnable
                public final void run() {
                    HvacViewModel.this.lambda$reStoreHvacModeData$1$HvacViewModel();
                }
            }, 300L);
            ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$NL45b4DxXmMo6gNDJ6Ji773tt4Y
                @Override // java.lang.Runnable
                public final void run() {
                    HvacViewModel.this.lambda$reStoreHvacModeData$2$HvacViewModel();
                }
            }, 450L);
            ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$9iwsT8dClO7LyJrGqacddkjd8Mk
                @Override // java.lang.Runnable
                public final void run() {
                    HvacViewModel.this.lambda$reStoreHvacModeData$3$HvacViewModel();
                }
            }, 600L);
            ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$dVuGpGrURpIoVdNBRVjkgweRWOw
                @Override // java.lang.Runnable
                public final void run() {
                    HvacViewModel.this.lambda$reStoreHvacModeData$4$HvacViewModel();
                }
            }, 750L);
            ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$AIQXJsaGUcfEJH-IHKdmsEW7-iY
                @Override // java.lang.Runnable
                public final void run() {
                    HvacViewModel.this.lambda$reStoreHvacModeData$5$HvacViewModel();
                }
            }, 900L);
            ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$gELNSQ37pIwGVI2PBQal-D059mk
                @Override // java.lang.Runnable
                public final void run() {
                    HvacViewModel.this.lambda$reStoreHvacModeData$6$HvacViewModel();
                }
            }, 1050L);
        }
    }

    public /* synthetic */ void lambda$reStoreHvacModeData$0$HvacViewModel() {
        float hvacModeStatePsnTemp = HvacDataModel.getInstance().getHvacModeStatePsnTemp();
        if (hvacModeStatePsnTemp < 18.0f || hvacModeStatePsnTemp > 32.0f) {
            return;
        }
        this.mHvacController.setHvacTempPsn(hvacModeStatePsnTemp);
    }

    public /* synthetic */ void lambda$reStoreHvacModeData$1$HvacViewModel() {
        this.mHvacController.setHvacWindSpeedLevel(HvacDataModel.getInstance().getHvacModeStateWindLevel());
    }

    public /* synthetic */ void lambda$reStoreHvacModeData$2$HvacViewModel() {
        int hvacModeStateWindBlowMode = HvacDataModel.getInstance().getHvacModeStateWindBlowMode();
        switch (hvacModeStateWindBlowMode) {
            case 5:
            case 6:
                this.mHvacController.setHvacWindBlowModeGroup(6);
                return;
            case 7:
                this.mHvacController.setHvacWindBlowModeGroup(4);
                return;
            case 8:
                this.mHvacController.setHvacWindBlowModeGroup(5);
                return;
            case 9:
                this.mHvacController.setHvacWindBlowModeGroup(7);
                return;
            default:
                this.mHvacController.setHvacWindBlowModeGroup(hvacModeStateWindBlowMode);
                return;
        }
    }

    public /* synthetic */ void lambda$reStoreHvacModeData$3$HvacViewModel() {
        this.mHvacController.setHvacAutoMode(HvacDataModel.getInstance().getHvacModeStateAutoMode());
    }

    public /* synthetic */ void lambda$reStoreHvacModeData$4$HvacViewModel() {
        this.mHvacController.setHvacAcMode(HvacDataModel.getInstance().getHvacModeStateAcMode());
    }

    public /* synthetic */ void lambda$reStoreHvacModeData$5$HvacViewModel() {
        int hvacCirculationMode = this.mHvacController.getHvacCirculationMode();
        if (hvacCirculationMode != HvacDataModel.getInstance().getHvacModeStateCirculationMode()) {
            IHvacController iHvacController = this.mHvacController;
            if (hvacCirculationMode != 1) {
                hvacCirculationMode = 1;
            }
            iHvacController.setHvacCirculationMode(hvacCirculationMode);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacPowerModeChanged(boolean enabled) {
        this.mHvacPowerMode.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacAutoModeChanged(boolean enabled) {
        this.mHvacAutoMode.postValue(Boolean.valueOf(enabled));
    }

    public void setAcHeatNatureMode(AcHeatNatureMode acHeatNatureMode) {
        try {
            setAcHeatNatureMode(AcHeatNatureMode.toggle(acHeatNatureMode));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AcHeatNatureMode getAcHeatNatureStatus() {
        return AcHeatNatureMode.fromHvacMode(getAcHeatNatureMode());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacAcHeatNatureChanged(int mode) {
        this.mHvacTempAcMode.postValue(AcHeatNatureMode.fromHvacMode(mode));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacTempDrvChanged(float temp) {
        if (temp > 32.0f || temp < 18.0f) {
            return;
        }
        this.mHvacTempDriver.postValue(Float.valueOf(temp));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacTempPsnChanged(float temp) {
        if (temp > 32.0f || temp < 18.0f) {
            return;
        }
        this.mHvacTempPsn.postValue(Float.valueOf(temp));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacDrvSyncModeChanged(boolean enabled) {
        this.mHvacDriverSyncMode.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacPsnSyncModeChanged(boolean enabled) {
        this.mHvacPsnSyncMode.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacInnerTempChanged(float temp) {
        this.mHvacTempInner.postValue(Float.valueOf(temp));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacWindBlowModeChanged(int mode) {
        this.mHvacWindMode.postValue(HvacWindBlowMode.fromHvacState(mode));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacRearWindBlowModeChanged(int mode) {
        this.mHvacRearWindMode.postValue(HvacWindBlowMode.fromHvacState(mode));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacWindSpeedLevelChanged(int level) {
        this.mHvacWindLevel.postValue(Integer.valueOf(level));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacCirculationModeChanged(int mode) {
        this.mHvacCirculation.postValue(HvacCirculationMode.fromHvacState(mode));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacFrontDefrostChanged(boolean enabled) {
        this.mHvacFrontDefrost.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacInnerPM25Changed(int value) {
        this.mHvacQualityInner.postValue(Integer.valueOf(value));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacOutsidePm25Changed(int value) {
        this.mHvacOutsidePM25.postValue(Integer.valueOf(value));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel
    protected void handleMirrorHeatChanged(boolean enabled) {
        this.mMirrorHeat.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel
    protected void handleFrontMirrorHeatChanged(boolean enabled) {
        this.mFrontMirrorHeat.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel
    protected void handleSeatHeatChanged(int level) {
        this.mSeatHeatLevel.postValue(SeatHeatLevel.fromBcmState(level));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel
    protected void handleSeatVentChanged(int level) {
        this.mSeatVentLevel.postValue(SeatVentLevel.fromBcmState(level));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel
    protected void handlePsnSeatHeatChanged(int level) {
        try {
            this.mPsnSeatHeatLevel.postValue(SeatHeatLevel.fromBcmState(level));
        } catch (Exception e) {
            LogUtils.d("HvacViewModel", e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel
    protected void handlePsnSeatVentChanged(int level) {
        try {
            this.mPsnSeatVentLevel.postValue(SeatVentLevel.fromBcmState(level));
        } catch (Exception e) {
            LogUtils.d("HvacViewModel", e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel
    protected void handleSteerHeatChanged(int level) {
        this.mSteerHeatLevel.postValue(Integer.valueOf(level));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel
    protected void handleRLSeatHeatChanged(int level) {
        try {
            this.mRLSeatHeatLevel.postValue(SeatHeatLevel.fromBcmState(level));
        } catch (Exception e) {
            LogUtils.d("HvacViewModel", e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel
    protected void handleRRSeatHeatChanged(int level) {
        try {
            this.mRRSeatHeatLevel.postValue(SeatHeatLevel.fromBcmState(level));
        } catch (Exception e) {
            LogUtils.d("HvacViewModel", e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel
    protected void handleSmartHvacModeChanged(boolean enabled) {
        this.mSmartHvacData.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel
    protected void handleAirProtectModeChanged(int mode) {
        this.mAirProtectData.postValue(HvacAirAutoProtect.fromValue(mode));
    }

    public LiveData<Boolean> getAirDistributionAutoModeData() {
        return this.mAirDistributionAutoMode;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel, com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacAirDistributionAutoChanged(boolean enable) {
        this.mAirDistributionAutoMode.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacWindModEconLourChanged(int value) {
        this.mHvacWindTempMode.postValue(HvacWindTempMode.fromHvacState(value));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacExternalTempChanged(float temp) {
        this.mHvacTempExt.postValue(Float.valueOf(temp));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacEconModeChange(int status) {
        this.mHvacEconMode.postValue(HvacSwitchStatus.fromHvacStatus(status));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacAqsModeChange(int status) {
        this.mHvacAqsMode.postValue(HvacSwitchStatus.fromHvacStatus(status));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacAqsLevelChange(int level) {
        AqsSensitivityLevel fromHvacState = AqsSensitivityLevel.fromHvacState(level);
        if (fromHvacState != null) {
            this.mHvacAqsLevel.postValue(fromHvacState);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacAutoDefogSwitchChanged(boolean value) {
        this.mHvacAutoDefogSt.postValue(Boolean.valueOf(value));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacAutoDefogWorkStChanged(boolean value) {
        this.mHvacAutoDefogWorkSt.postValue(Boolean.valueOf(value));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacRearPowerModeChanged(boolean enabled) {
        this.mHvacRearPowerMode.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacRearAutoModeChanged(boolean enabled) {
        this.mHvacRearAutoMode.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacRearTempDrvChanged(float temp) {
        this.mHvacRearTempDriver.postValue(Float.valueOf(temp));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacRearTempPsnChanged(float temp) {
        this.mHvacRearTempPsn.postValue(Float.valueOf(temp));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacThirdRowTempChanged(float temp) {
        this.mHvacThirdTemp.postValue(Float.valueOf(temp));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacThirdRowWindBlowModeChanged(int mode) {
        this.mHvacThirdWindMode.postValue(HvacWindBlowMode.fromHvacState(mode));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacRearWindSpeedLevelChanged(int level) {
        this.mHvacRearWindLevel.postValue(Integer.valueOf(level));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacNewFreshSwitchChanged(boolean enabled) {
        this.mHvacNewFreshSwitch.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacNIVentChanged(boolean enabled) {
        this.mHvacNIVent.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacEavDrvWindModeChange(int mode) {
        this.mHvacEavDriver.postValue(HvacEavWindMode.fromHvacState(mode));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacEavPsnWindModeChange(int mode) {
        this.mHvacEavPsn.postValue(HvacEavWindMode.fromHvacState(mode));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacEavDrvLeftHPosChanged(int pos) {
        if (pos <= 0 || pos > CarBaseConfig.getInstance().getHvacMaxWindPos()) {
            return;
        }
        this.mDriverLeftHPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacEavDrvLeftVPosChanged(int pos) {
        if ((pos <= 0 || pos > CarBaseConfig.getInstance().getHvacMaxWindPos()) && pos != 14) {
            return;
        }
        this.mDriverLeftVPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacEavDrvRightHPosChanged(int pos) {
        if (pos <= 0 || pos > CarBaseConfig.getInstance().getHvacMaxWindPos()) {
            return;
        }
        this.mDriverRightHPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacEavDrvRightVPosChanged(int pos) {
        if ((pos <= 0 || pos > CarBaseConfig.getInstance().getHvacMaxWindPos()) && pos != 14) {
            return;
        }
        this.mDriverRightVPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacEavPsnLeftHPosChanged(int pos) {
        if (pos <= 0 || pos > CarBaseConfig.getInstance().getHvacMaxWindPos()) {
            return;
        }
        this.mPsnLeftHPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacEavPsnLeftVPosChanged(int pos) {
        if ((pos <= 0 || pos > CarBaseConfig.getInstance().getHvacMaxWindPos()) && pos != 14) {
            return;
        }
        this.mPsnLeftVPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacEavPsnRightHPosChanged(int pos) {
        if (pos <= 0 || pos > CarBaseConfig.getInstance().getHvacMaxWindPos()) {
            return;
        }
        this.mPsnRightHPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacEavPsnRightVPosChanged(int pos) {
        if ((pos <= 0 || pos > CarBaseConfig.getInstance().getHvacMaxWindPos()) && pos != 14) {
            return;
        }
        this.mPsnRightVPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacCirculationTimeChanged(int time) {
        HvacCirculationTime fromHvacState = HvacCirculationTime.fromHvacState(time);
        if (fromHvacState != null) {
            this.mHvacCirculationTime.postValue(fromHvacState);
        }
    }

    public LiveData<Boolean> getAirIntakeAutoModeData() {
        return this.mAirIntakeAutoMode;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacAirIntakeAutoChanged(boolean enable) {
        this.mAirIntakeAutoMode.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacEavSweepModeChanged(int status) {
        this.mHvacEavSweepMode.postValue(HvacSwitchStatus.fromHvacStatus(status));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacIonizerModeChanged(int status) {
        this.mHvacIonizerMode.postValue(HvacSwitchStatus.fromHvacStatus(status));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacSelfDryChanged(boolean enabled) {
        this.mHvacSelfDry.postValue(Boolean.valueOf(enabled));
    }

    public LiveData<Boolean> getHvacPowerData() {
        return this.mHvacPowerMode;
    }

    public LiveData<Boolean> getHvacAutoData() {
        return this.mHvacAutoMode;
    }

    public LiveData<AcHeatNatureMode> getHvacTempAcData() {
        return this.mHvacTempAcMode;
    }

    public LiveData<Float> getHvacTempDriverData() {
        return this.mHvacTempDriver;
    }

    public LiveData<Boolean> getHvacDriverSyncData() {
        return this.mHvacDriverSyncMode;
    }

    public LiveData<Float> getHvacTempPsnData() {
        return this.mHvacTempPsn;
    }

    public LiveData<Boolean> getHvacPsnSyncData() {
        return this.mHvacPsnSyncMode;
    }

    public LiveData<Float> getHvacTempInnerData() {
        return this.mHvacTempInner;
    }

    public LiveData<Float> getHvacTempOutData() {
        return this.mHvacTempExt;
    }

    public LiveData<HvacWindTempMode> getHvacWindTempData() {
        return this.mHvacWindTempMode;
    }

    public LiveData<Integer> getHvacWindLevelData() {
        return this.mHvacWindLevel;
    }

    public LiveData<HvacWindBlowMode> getHvacWindModeData() {
        return this.mHvacWindMode;
    }

    public LiveData<HvacWindBlowMode> getHvacRearWindModeData() {
        return this.mHvacRearWindMode;
    }

    public LiveData<HvacSwitchStatus> getHvacIonizerModeData() {
        return this.mHvacIonizerMode;
    }

    public LiveData<Boolean> getSmartHvacData() {
        return this.mSmartHvacData;
    }

    public LiveData<HvacAirAutoProtect> getHvacAirProtectData() {
        return this.mAirProtectData;
    }

    public LiveData<Boolean> getHvacSelfDryData() {
        return this.mHvacSelfDry;
    }

    public LiveData<Boolean> getFrontDefrostData() {
        return this.mHvacFrontDefrost;
    }

    public LiveData<Boolean> getMirrorHeatData() {
        return this.mMirrorHeat;
    }

    public LiveData<Boolean> getFrontMirrorHeatData() {
        return this.mFrontMirrorHeat;
    }

    public HvacSwitchStatus getHvacIonizerStatus() {
        if (this.mHvacIonizerMode.getValue() != null) {
            return this.mHvacIonizerMode.getValue();
        }
        return HvacSwitchStatus.fromHvacStatus(getHvacIonizerMode());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindSpeedMax() {
        setHvacWindSpeedLevel(getFanMaxLevel());
    }

    public LiveData<Boolean> getFanSpeedAutoModeData() {
        return this.mFanSpeedAutoMode;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacFanSpeedAutoChanged(boolean enable) {
        this.mFanSpeedAutoMode.postValue(Boolean.valueOf(enable));
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode;

        static {
            int[] iArr = new int[HvacWindBlowMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode = iArr;
            try {
                iArr[HvacWindBlowMode.Face.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.Foot.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.Windshield.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void setHvacWindMode(HvacWindBlowMode mode) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[mode.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            setHvacWindBlowMode(HvacWindBlowMode.toHvacCmd(mode));
        }
    }

    public LiveData<HvacCirculationMode> getHvacCirculationData() {
        return this.mHvacCirculation;
    }

    public void setHvacCirculationMode() {
        setHvacCirculationMode(1);
    }

    public HvacCirculationMode getCirculationMode() {
        if (this.mHvacCirculation.getValue() != null) {
            return this.mHvacCirculation.getValue();
        }
        return HvacCirculationMode.fromHvacState(getHvacCirculationMode());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacCirculationOut() {
        if (2 != getHvacCirculationMode()) {
            setHvacCirculationMode(1);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacCirculationInner() {
        if (6 == getHvacCirculationMode()) {
            setHvacCirculationMode(1);
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$TtQBtXS-ekWTEpcCEFTjdi7aQGw
                @Override // java.lang.Runnable
                public final void run() {
                    HvacViewModel.this.lambda$setHvacCirculationInner$7$HvacViewModel();
                }
            }, 100L);
        } else if (2 == getHvacCirculationMode()) {
            setHvacCirculationMode(1);
        }
    }

    public /* synthetic */ void lambda$setHvacCirculationInner$7$HvacViewModel() {
        setHvacCirculationMode(1);
    }

    public LiveData<HvacCirculationTime> getHvacCirculationTimeData() {
        return this.mHvacCirculationTime;
    }

    public HvacCirculationTime getHvacCirculationTime(HvacCirculationTime defValue) {
        if (this.mHvacCirculationTime.getValue() != null) {
            return this.mHvacCirculationTime.getValue();
        }
        return HvacCirculationTime.fromHvacState(getHvacCirculationTime());
    }

    public void setHvacCirculationTime(HvacCirculationTime time) {
        if (time != null) {
            setHvacCirculationTime(HvacCirculationTime.toHvacCmd(time));
        }
    }

    public LiveData<SeatHeatLevel> getHvacSeatHeatData() {
        return this.mSeatHeatLevel;
    }

    public void setHvacSeatHeatLevel(SeatHeatLevel seatHeatLevel) {
        if (seatHeatLevel != null) {
            setSeatHeatLevel(seatHeatLevel.ordinal());
        }
    }

    public SeatHeatLevel getHvacSeatHeatLevel() {
        if (this.mSeatHeatLevel.getValue() != null) {
            return this.mSeatHeatLevel.getValue();
        }
        return SeatHeatLevel.fromBcmState(getSeatHeatLevel());
    }

    public LiveData<SeatVentLevel> getHvacSeatVentData() {
        return this.mSeatVentLevel;
    }

    public SeatVentLevel getHvacSeatVentLevel() {
        if (this.mSeatVentLevel.getValue() != null) {
            return this.mSeatVentLevel.getValue();
        }
        return SeatVentLevel.fromBcmState(getSeatVentLevel());
    }

    public void setHvacSeatVentLevel(SeatVentLevel seatVentLevel) {
        if (seatVentLevel != null) {
            setSeatVentLevel(seatVentLevel.ordinal());
        }
    }

    public LiveData<SeatHeatLevel> getHvacPsnSeatHeatData() {
        return this.mPsnSeatHeatLevel;
    }

    public SeatHeatLevel getHvacPsnSeatHeatLevel() {
        if (this.mPsnSeatHeatLevel.getValue() != null) {
            return this.mPsnSeatHeatLevel.getValue();
        }
        return SeatHeatLevel.fromBcmState(getPsnSeatHeatLevel());
    }

    public void setHvacPsnSeatHeatLevel(SeatHeatLevel seatHeatLevel) {
        if (seatHeatLevel != null) {
            setPsnSeatHeatLevel(seatHeatLevel.ordinal());
        }
    }

    public LiveData<SeatVentLevel> getHvacPsnSeatVentData() {
        return this.mPsnSeatVentLevel;
    }

    public SeatVentLevel getHvacPsnSeatVentLevel() {
        if (this.mPsnSeatVentLevel.getValue() != null) {
            return this.mPsnSeatVentLevel.getValue();
        }
        return SeatVentLevel.fromBcmState(getPsnSeatVentLevel());
    }

    public void setHvacPsnSeatVentLevel(SeatVentLevel seatVentLevel) {
        if (seatVentLevel != null) {
            setPsnSeatVentLevel(seatVentLevel.ordinal());
        }
    }

    public LiveData<Integer> getSteerHeatData() {
        return this.mSteerHeatLevel;
    }

    public LiveData<Integer> getHvacAqInnerData() {
        return this.mHvacQualityInner;
    }

    public MutableLiveData<Integer> getmHvacOutsidePM25() {
        return this.mHvacOutsidePM25;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void updateWeatherFromServer() {
        this.mHvacController.updateWeatherFromServer();
    }

    public LiveData<HvacSwitchStatus> getHvacEconData() {
        return this.mHvacEconMode;
    }

    public void setHvacEconSwitchStatus(HvacSwitchStatus hvacSwitchStatus) {
        setHvacEconMode(HvacSwitchStatus.toHvacCmd(hvacSwitchStatus));
    }

    public HvacSwitchStatus getHvacEconSwitchStatus() {
        if (this.mHvacEconMode.getValue() != null) {
            return this.mHvacEconMode.getValue();
        }
        return HvacSwitchStatus.fromHvacStatus(getHvacEconMode());
    }

    public LiveData<HvacSwitchStatus> getHvacAqsData() {
        return this.mHvacAqsMode;
    }

    public void setHvacAqsStatus(HvacSwitchStatus hvacSwitchStatus) {
        try {
            setHvacAqsMode(HvacSwitchStatus.toHvacCmd(hvacSwitchStatus));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HvacSwitchStatus getHvacAqsStatus() {
        if (this.mHvacAqsMode.getValue() != null) {
            return this.mHvacAqsMode.getValue();
        }
        return HvacSwitchStatus.fromHvacStatus(getHvacAqsMode());
    }

    public LiveData<AqsSensitivityLevel> getHvacAqsLevelData() {
        return this.mHvacAqsLevel;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isAutoDefogWorkSt() {
        MutableLiveData<Boolean> mutableLiveData = this.mHvacAutoDefogWorkSt;
        if (mutableLiveData == null || mutableLiveData.getValue() == null) {
            return false;
        }
        return this.mHvacAutoDefogWorkSt.getValue().booleanValue();
    }

    public LiveData<Boolean> getHvacAutoDefogSt() {
        return this.mHvacAutoDefogSt;
    }

    public LiveData<Boolean> getHvacAutoDefogWorkSt() {
        return this.mHvacAutoDefogWorkSt;
    }

    public void setHvacAqsLevel(AqsSensitivityLevel level) {
        if (level != null) {
            try {
                setHvacAqsLevel(AqsSensitivityLevel.toHvacCmd(level));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public AqsSensitivityLevel getHvacAqsSensitivityLevel() {
        if (this.mHvacAqsLevel.getValue() != null) {
            return this.mHvacAqsLevel.getValue();
        }
        return AqsSensitivityLevel.fromHvacState(getHvacAqsLevel());
    }

    public LiveData<HvacEavWindMode> getHvacEavDriverData() {
        return this.mHvacEavDriver;
    }

    public HvacEavWindMode getHvacEavDrv() {
        if (this.mHvacEavDriver.getValue() != null) {
            return this.mHvacEavDriver.getValue();
        }
        return HvacEavWindMode.fromHvacState(getHvacEavDriverWindMode());
    }

    public void setHvacEavModeDriver(HvacEavWindMode mode) {
        if (mode != null) {
            setHvacEavDriverWindMode(HvacEavWindMode.toHvacCmd(mode));
        }
    }

    public LiveData<HvacEavWindMode> getHvacEavPsnData() {
        return this.mHvacEavPsn;
    }

    public HvacEavWindMode getHvacEavPsn() {
        if (this.mHvacEavPsn.getValue() != null) {
            return this.mHvacEavPsn.getValue();
        }
        return HvacEavWindMode.fromHvacState(getHvacEavPsnWindMode());
    }

    public void setHvacEavModePsn(HvacEavWindMode mode) {
        if (mode != null) {
            setHvacEavPsnWindMode(HvacEavWindMode.toHvacCmd(mode));
        }
    }

    public LiveData<HvacSwitchStatus> getHvacEavSweepData() {
        return this.mHvacEavSweepMode;
    }

    public void setHvacEavSweepStatus(HvacSwitchStatus hvacEavSweepStatus) {
        try {
            setHvacEavSweepMode(HvacSwitchStatus.toHvacCmd(hvacEavSweepStatus));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HvacSwitchStatus getHvacEavSweepStatus() {
        if (this.mHvacEavSweepMode.getValue() != null) {
            return this.mHvacEavSweepMode.getValue();
        }
        return HvacSwitchStatus.fromHvacStatus(getHvacEavSweepMode());
    }

    public void setDriverLeftVentPos(int vertical, int horizontal) {
        LogUtils.d("HvacViewModel", "setDriverLeftVentPos vertical " + vertical + ",current vertical:" + getHvacEAVDriverLeftVPos() + ",horizontal " + horizontal + ",current horizontal:" + getHvacEAVDriverLeftHPos());
        if (vertical != getHvacEAVDriverLeftVPos()) {
            setHvacEAVDriverLeftVPos(vertical);
        }
        if (horizontal != getHvacEAVDriverLeftHPos()) {
            setHvacEAVDriverLeftHPos(horizontal);
        }
    }

    public void setDriverRightVentPos(int vertical, int horizontal) {
        if (vertical != getHvacEAVDriverRightVPos()) {
            setHvacEAVDriverRightVPos(vertical);
        }
        if (horizontal != getHvacEAVDriverRightHPos()) {
            setHvacEAVDriverRightHPos(horizontal);
        }
    }

    public void setPsnLeftVentPos(int vertical, int horizontal) {
        if (vertical != getHvacEAVPsnLeftVPos()) {
            setHvacEAVPsnLeftVPos(vertical);
        }
        if (horizontal != getHvacEAVPsnLeftHPos()) {
            setHvacEAVPsnLeftHPos(horizontal);
        }
    }

    public void setPsnRightVentPos(int vertical, int horizontal) {
        if (vertical != getHvacEAVPsnRightVPos()) {
            setHvacEAVPsnRightVPos(vertical);
        }
        if (horizontal != getHvacEAVPsnRightHPos()) {
            setHvacEAVPsnRightHPos(horizontal);
        }
    }

    public HvacWindBlowMode getWindBlowMode() {
        return HvacWindBlowMode.fromHvacState(getHvacWindBlowMode());
    }

    public LiveData<Integer> getDriverLeftVPos() {
        return this.mDriverLeftVPos;
    }

    public LiveData<Integer> getDriverLeftHPos() {
        return this.mDriverLeftHPos;
    }

    public LiveData<Integer> getDriverRightVPos() {
        return this.mDriverRightVPos;
    }

    public LiveData<Integer> getDriverRightHPos() {
        return this.mDriverRightHPos;
    }

    public LiveData<Integer> getPsnLeftVPos() {
        return this.mPsnLeftVPos;
    }

    public LiveData<Integer> getPsnLeftHPos() {
        return this.mPsnLeftHPos;
    }

    public LiveData<Integer> getPsnRightVPos() {
        return this.mPsnRightVPos;
    }

    public LiveData<Integer> getPsnRightHPos() {
        return this.mPsnRightHPos;
    }

    public LiveData<Boolean> getHvacRapidCoolingData() {
        return this.mHvacRapidCooling;
    }

    public LiveData<Integer> getHvacRapidCoolingTimeData() {
        return this.mHvacRapidCoolingTime;
    }

    public LiveData<Boolean> getHvacDeodorantData() {
        return this.mHvacDeodorant;
    }

    public LiveData<Integer> getHvacDeodorantTimeData() {
        return this.mHvacDeodorantTime;
    }

    public LiveData<Boolean> getHvacRapidHeatData() {
        return this.mHvacRapidHeat;
    }

    public LiveData<Integer> getHvacRapidHeatTimeData() {
        return this.mHvacRapidHeatTime;
    }

    public LiveData<Boolean> getHvacSfsSwitchData() {
        return this.mHvacSfsSwitchData;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacRapidCoolingChanged(boolean enable) {
        this.mHvacRapidCooling.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacRapidCoolingTimerChanged(int time) {
        this.mHvacRapidCoolingTime.postValue(Integer.valueOf(time));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacDeodorantChanged(boolean enable) {
        this.mHvacDeodorant.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacDeodorantTimerChanged(int time) {
        this.mHvacDeodorantTime.postValue(Integer.valueOf(time));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacRapidHeatChanged(boolean enable) {
        this.mHvacRapidHeat.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacRapidHeatTimerChanged(int time) {
        this.mHvacRapidHeatTime.postValue(Integer.valueOf(time));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacSfsSwitchChanged(boolean enable) {
        this.mHvacSfsSwitchData.postValue(Boolean.valueOf(enable));
    }

    public LiveData<Boolean> getHvacQualityPurgeData() {
        return this.mHvacQualityPurgeMode;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacQualityPurgeChanged(boolean enable) {
        this.mHvacQualityPurgeMode.postValue(Boolean.valueOf(enable));
    }

    public HvacWindTempMode getWindTempColor() {
        return HvacWindTempMode.fromHvacState(getWindModeColor());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindBlowFace() {
        int hvacWindBlowMode = getHvacWindBlowMode();
        if (hvacWindBlowMode == 1) {
            if (isHvacPowerModeOn()) {
                return;
            }
            setHvacPowerMode(true);
        } else if (hvacWindBlowMode == 2) {
            setHvacWindBlowMode(3);
        } else if (hvacWindBlowMode == 3) {
            setHvacWindBlowMode(2);
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$J_EnTzOLdI7ptqVaWeU48_peme8
                @Override // java.lang.Runnable
                public final void run() {
                    HvacViewModel.this.lambda$setHvacWindBlowFace$8$HvacViewModel();
                }
            }, 100L);
        } else {
            setHvacWindBlowMode(2);
        }
    }

    public /* synthetic */ void lambda$setHvacWindBlowFace$8$HvacViewModel() {
        setHvacWindBlowMode(3);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindBlowFoot() {
        switch (getHvacWindBlowMode()) {
            case 1:
                setHvacWindBlowMode(3);
                ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$w51GM4vErEX99n8AlEH8AHcmNGw
                    @Override // java.lang.Runnable
                    public final void run() {
                        HvacViewModel.this.lambda$setHvacWindBlowFoot$9$HvacViewModel();
                    }
                }, 100L);
                return;
            case 2:
                setHvacWindBlowMode(2);
                return;
            case 3:
                if (isHvacPowerModeOn()) {
                    return;
                }
                setHvacPowerMode(true);
                return;
            case 4:
                setHvacWindBlowMode(1);
                return;
            case 5:
            case 6:
                setHvacWindBlowMode(3);
                ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$VUW4c7sIcPVN1x6Y8sdwEU2D2CI
                    @Override // java.lang.Runnable
                    public final void run() {
                        HvacViewModel.this.lambda$setHvacWindBlowFoot$10$HvacViewModel();
                    }
                }, 100L);
                return;
            default:
                setHvacWindBlowMode(3);
                return;
        }
    }

    public /* synthetic */ void lambda$setHvacWindBlowFoot$9$HvacViewModel() {
        setHvacWindBlowMode(2);
    }

    public /* synthetic */ void lambda$setHvacWindBlowFoot$10$HvacViewModel() {
        setHvacWindBlowMode(1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindBlowWindow() {
        int hvacWindBlowMode = getHvacWindBlowMode();
        if (hvacWindBlowMode == 3) {
            setHvacWindBlowMode(1);
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$oU5AD7RCQ0mXVluBA8TNPQPMjEQ
                @Override // java.lang.Runnable
                public final void run() {
                    HvacViewModel.this.lambda$setHvacWindBlowWindow$11$HvacViewModel();
                }
            }, 100L);
        } else if (hvacWindBlowMode == 4) {
            setHvacWindBlowMode(3);
        } else if (hvacWindBlowMode == 5 || hvacWindBlowMode == 6) {
            if (isHvacPowerModeOn()) {
                return;
            }
            setHvacPowerMode(true);
        } else {
            setHvacWindBlowMode(1);
        }
    }

    public /* synthetic */ void lambda$setHvacWindBlowWindow$11$HvacViewModel() {
        setHvacWindBlowMode(3);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindBlowFaceFoot() {
        int hvacWindBlowMode = getHvacWindBlowMode();
        if (hvacWindBlowMode == 1) {
            setHvacWindBlowMode(3);
        } else if (hvacWindBlowMode == 2) {
            if (isHvacPowerModeOn()) {
                return;
            }
            setHvacPowerMode(true);
        } else if (hvacWindBlowMode == 3) {
            setHvacWindBlowMode(2);
        } else {
            setHvacWindBlowMode(2);
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$X-eckZIqFRzec3a1PGZHlR_lzH4
                @Override // java.lang.Runnable
                public final void run() {
                    HvacViewModel.this.lambda$setHvacWindBlowFaceFoot$12$HvacViewModel();
                }
            }, 100L);
        }
    }

    public /* synthetic */ void lambda$setHvacWindBlowFaceFoot$12$HvacViewModel() {
        setHvacWindBlowMode(3);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindBlowWinFoot() {
        int hvacWindBlowMode = getHvacWindBlowMode();
        if (hvacWindBlowMode == 3) {
            setHvacWindBlowMode(1);
        } else if (hvacWindBlowMode == 4) {
            if (isHvacPowerModeOn()) {
                return;
            }
            setHvacPowerMode(true);
        } else if (hvacWindBlowMode == 5 || hvacWindBlowMode == 6) {
            setHvacWindBlowMode(3);
        } else {
            setHvacWindBlowMode(1);
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacViewModel$ONTwYGz3noQ054-rUn_7yaEScTg
                @Override // java.lang.Runnable
                public final void run() {
                    HvacViewModel.this.lambda$setHvacWindBlowWinFoot$13$HvacViewModel();
                }
            }, 100L);
        }
    }

    public /* synthetic */ void lambda$setHvacWindBlowWinFoot$13$HvacViewModel() {
        setHvacWindBlowMode(3);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void openHvacWindModeFace() {
        int hvacWindBlowMode = getHvacWindBlowMode();
        if (1 == hvacWindBlowMode || 2 == hvacWindBlowMode) {
            return;
        }
        setHvacWindBlowMode(2);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void closeHvacWindBlowFace() {
        setHvacWindBlowFoot();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void closeHvacWindBlowFoot() {
        if (HvacWindBlowMode.FootWindshield == getWindBlowMode()) {
            setHvacWindBlowWindow();
        } else {
            setHvacWindBlowFace();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void closeHvacWindBlowWin() {
        setHvacWindBlowFoot();
    }

    public LiveData<Boolean> getHvacSingleModeData() {
        return this.mHvacSingleMode;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void enterHvacSingleMode() {
        if (!isHvacVentOpen(0) && !isHvacVentOpen(1)) {
            this.mHvacController.setHvacEavDrvLeftVPos(13);
            this.mHvacController.setHvacEavDrvRightVPos(13);
        }
        this.mHvacController.setHvacEavPsnLeftVPos(14);
        this.mHvacController.setHvacEavPsnRightVPos(14);
        setHvacSingleModeActive(true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacSingleModeChanged(boolean enable) {
        this.mHvacSingleMode.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacSingleModeActive(boolean enable) {
        this.mHvacController.setHvacSingleModeActive(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacSingleModeActive() {
        return this.mHvacController.isHvacSingleModeActive();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacSingleModeActivated(boolean enable) {
        this.mHvacSingleModeActive.postValue(Boolean.valueOf(enable));
    }

    public LiveData<Boolean> getHvacSingleModeActiveData() {
        return this.mHvacSingleModeActive;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isSupportDrvSeatVent() {
        return CarBaseConfig.getInstance().isSupportDrvSeatVent();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isSupportDrvSeatHeat() {
        return CarBaseConfig.getInstance().isSupportDrvSeatHeat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isSupportPsnSeatHeat() {
        return CarBaseConfig.getInstance().isSupportPsnSeatHeat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isSupportRearSeatHeat() {
        return CarBaseConfig.getInstance().isSupportRearSeatHeat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isSupportXFreeBreath() {
        return CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_PACKAGE_1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isSupportDulTemp() {
        return CarBaseConfig.getInstance().isSupportHvacDualTemp();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRearPowerMode(boolean enable) {
        this.mHvacController.setHvacRearPowerMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacRearPowerModeOn() {
        return this.mHvacController.isHvacRearPowerModeOn();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRearTempDriver(float temperature) {
        this.mHvacController.setHvacRearTempDriver(temperature);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRearTempDriverStep(boolean isUp) {
        this.mHvacController.setHvacRearTempDriverStep(isUp);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public float getHvacRearTempDriver() {
        return this.mHvacController.getHvacRearTempDriver();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRearTempPsn(float temperature) {
        this.mHvacController.setHvacRearTempPsn(temperature);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRearTempPsnStep(boolean isUp) {
        this.mHvacController.setHvacRearTempPsnStep(isUp);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public float getHvacRearTempPsn() {
        return this.mHvacController.getHvacRearTempPsn();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacThirdRowTempDriver(float temperature) {
        this.mHvacController.setHvacThirdRowTempDriver(temperature);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacThirdRowTempDriverStep(boolean isUp) {
        this.mHvacController.setHvacThirdRowTempDriverStep(isUp);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public float getHvacThirdRowTempDriver() {
        return this.mHvacController.getHvacThirdRowTempDriver();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacThirdRowTempStep(boolean isUp) {
        this.mHvacController.setHvacThirdRowTempStep(isUp);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRearAutoMode(boolean enable) {
        this.mHvacController.setHvacRearAutoMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacRearAutoModeOn() {
        return this.mHvacController.isHvacRearAutoModeOn();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRearWindSpeedLevel(int level) {
        this.mHvacController.setHvacRearWindSpeedLevel(level);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRearWindSpeedStep(boolean isUp) {
        this.mHvacController.setHvacRearWindSpeedStep(isUp);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacRearWindSpeedLevel() {
        return this.mHvacController.getHvacRearWindSpeedLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacThirdRowWindBlowMode(int mode) {
        this.mHvacController.setHvacThirdRowWindBlowMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacThirdRowWindBlowMode() {
        return this.mHvacController.getHvacThirdRowWindBlowMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacNewFreshSwitchStatus(boolean enable) {
        this.mHvacController.setHvacNewFreshSwitchStatus(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacNewFreshSwitchOn() {
        return this.mHvacController.isHvacNewFreshSwitchOn();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacNIVentOn() {
        return this.mHvacController.isHvacNIVentOn();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacNIVent(boolean enable) {
        this.mHvacController.setHvacNIVent(enable);
    }

    public void setHvacRLSeatHeatLevel(SeatHeatLevel seatHeatLevel) {
        if (seatHeatLevel != null) {
            setRLSeatHeatLevel(seatHeatLevel.ordinal());
        }
    }

    public LiveData<SeatHeatLevel> getHvacRLSeatHeatData() {
        return this.mRLSeatHeatLevel;
    }

    public SeatHeatLevel getHvacRLSeatHeatLevel() {
        if (this.mRLSeatHeatLevel.getValue() != null) {
            return this.mRLSeatHeatLevel.getValue();
        }
        try {
            return SeatHeatLevel.fromBcmState(getRLSeatHeatLevel());
        } catch (Exception unused) {
            return null;
        }
    }

    public void setHvacRRSeatHeatLevel(SeatHeatLevel seatHeatLevel) {
        if (seatHeatLevel != null) {
            setRRSeatHeatLevel(seatHeatLevel.ordinal());
        }
    }

    public LiveData<SeatHeatLevel> getHvacRRSeatHeatData() {
        return this.mRRSeatHeatLevel;
    }

    public SeatHeatLevel getHvacRRSeatHeatLevel() {
        if (this.mRRSeatHeatLevel.getValue() != null) {
            return this.mRRSeatHeatLevel.getValue();
        }
        try {
            return SeatHeatLevel.fromBcmState(getRRSeatHeatLevel());
        } catch (Exception unused) {
            return null;
        }
    }

    public boolean getCurrentSeatHeatVentStateData() {
        boolean currentSeatHeatSwitchStateData = getCurrentSeatHeatSwitchStateData();
        if (CarBaseConfig.getInstance().isSupportDrvSeatVent()) {
            return currentSeatHeatSwitchStateData || getHvacSeatVentLevel() != SeatVentLevel.Off;
        }
        return currentSeatHeatSwitchStateData;
    }

    public boolean getCurrentSeatHeatSwitchStateData() {
        SeatHeatLevel hvacSeatHeatLevel = getHvacSeatHeatLevel();
        SeatHeatLevel hvacPsnSeatHeatLevel = getHvacPsnSeatHeatLevel();
        SeatHeatLevel hvacRLSeatHeatLevel = getHvacRLSeatHeatLevel();
        SeatHeatLevel hvacRRSeatHeatLevel = getHvacRRSeatHeatLevel();
        return ((hvacSeatHeatLevel == null || hvacSeatHeatLevel == SeatHeatLevel.Off) && (hvacPsnSeatHeatLevel == null || hvacPsnSeatHeatLevel == SeatHeatLevel.Off) && ((hvacRLSeatHeatLevel == null || hvacRLSeatHeatLevel == SeatHeatLevel.Off) && (hvacRRSeatHeatLevel == null || hvacRRSeatHeatLevel == SeatHeatLevel.Off))) ? false : true;
    }

    public boolean getCurrentSeatVentSwitchStateData() {
        SeatVentLevel hvacSeatVentLevel = getHvacSeatVentLevel();
        return (hvacSeatVentLevel == null || hvacSeatVentLevel == SeatVentLevel.Off) ? false : true;
    }

    public boolean isShowSeatHeatVentEntry() {
        return isSupportDrvSeatHeat() || isSupportPsnSeatHeat() || isSupportRearSeatHeat() || isSupportDrvSeatVent();
    }

    public MutableLiveData<Boolean> getHvacRearPowerModeData() {
        return this.mHvacRearPowerMode;
    }

    public MutableLiveData<Boolean> getHvacRearAutoModeData() {
        return this.mHvacRearAutoMode;
    }

    public MutableLiveData<Float> getHvacRearTempDriverData() {
        return this.mHvacRearTempDriver;
    }

    public MutableLiveData<Float> getHvacRearTempPsnData() {
        return this.mHvacRearTempPsn;
    }

    public MutableLiveData<Float> getHvacThirdTempData() {
        return this.mHvacThirdTemp;
    }

    public MutableLiveData<Integer> getHvacRearWindLevelData() {
        return this.mHvacRearWindLevel;
    }

    public MutableLiveData<HvacWindBlowMode> getHvacThirdWindModeData() {
        return this.mHvacThirdWindMode;
    }

    public MutableLiveData<Boolean> getHvacNewFreshSwitchData() {
        return this.mHvacNewFreshSwitch;
    }

    public MutableLiveData<Boolean> getHvacNIVentData() {
        return this.mHvacNIVent;
    }
}
