package com.xiaopeng.carcontrol.viewmodel.scu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.bean.NgpSafeExamResult;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class ScuViewModel extends ScuBaseViewModel {
    private static final String TAG = "ScuViewModel";
    private final MutableLiveData<ScuResponse> mFcw = new MutableLiveData<>();
    private final MutableLiveData<FcwSensitivity> mFcwSen = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mRcw = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mRcta = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mLdw = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mElk = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mBsd = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mDow = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mIhb = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mAutoPark = new MutableLiveData<>();
    private final MutableLiveData<ApResponse> mMemoryPark = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mPhoneParkSw = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mKeyParkSw = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mLcc = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mAcc = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mAccForMirror = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mIslc = new MutableLiveData<>();
    private final MutableLiveData<ScuIslaMode> mIsla = new MutableLiveData<>();
    private final MutableLiveData<ScuIslaSpdRange> mIslaSpdRange = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIslaConfirmMode = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mAlc = new MutableLiveData<>();
    private final MutableLiveData<Integer> mXpuXPilotActive = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mNgp = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mNgpQuickLane = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mNgpTruckOffset = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mNgpTipWindow = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mNgpVoiceChangeLane = new MutableLiveData<>();
    private final MutableLiveData<NgpChangeLaneMode> mNgpChangeLaneMode = new MutableLiveData<>();
    private final MutableLiveData<Integer> mNgpRemind = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mScuOptionTipsData = new MutableLiveData<>();
    private final MutableLiveData<SdcRadarDisLevel> mLeftSdcDisLevel = new MutableLiveData<>();
    private final MutableLiveData<SdcRadarDisLevel> mRightSdcDisLevel = new MutableLiveData<>();
    final MutableLiveData<ScuLssMode> mScuLssModeData = new MutableLiveData<>();
    private final MutableLiveData<DsmState> mDsmStateData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSpecialSasData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCloseMrrDialogData = new MutableLiveData<>();

    private NgpSafeExamResult requestNgpSafeExamResultImpl() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ScuViewModel() {
        this.mScuController.registerCallback(this);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void showCloseMrrDialog() {
        if (BaseFeatureOption.getInstance().isSupportNapa()) {
            this.mCloseMrrDialogData.postValue(true);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void dismissCloseMrrDialog() {
        if (BaseFeatureOption.getInstance().isSupportNapa()) {
            this.mCloseMrrDialogData.postValue(false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleFcwStateChanged(ScuResponse state) {
        this.mFcw.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    public void handleFcwSenChanged(FcwSensitivity sensitivity) {
        this.mFcwSen.postValue(sensitivity);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleRcwStateChanged(ScuResponse state) {
        this.mRcw.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleRctaStateChanged(ScuResponse state) {
        this.mRcta.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    public void handleLssStateChanged(int state) {
        this.mScuLssModeData.postValue(convertLssState(state));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    public void handleLdwStateChanged(ScuResponse state) {
        if (CarBaseConfig.getInstance().isSupportLka()) {
            this.mScuLssModeData.postValue(convertLssState(state, getLkaState()));
        } else {
            this.mLdw.postValue(state);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    public void handleLkaStateChanged(ScuResponse state) {
        if (CarBaseConfig.getInstance().isSupportLka()) {
            this.mScuLssModeData.postValue(convertLssState(getLdwState(), state));
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    public void handleElkStateChanged(ScuResponse state) {
        if (CarBaseConfig.getInstance().isLssCertification()) {
            this.mScuLssModeData.postValue(convertLssState(getLdwState(), getLkaState()));
        } else {
            this.mElk.postValue(state);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    public void handleBsdStateChanged(ScuResponse state) {
        this.mBsd.postValue(state);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    public void handleDowStateChanged(ScuResponse state) {
        this.mDow.postValue(state);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    public void handleIhbStateChanged(ScuResponse state) {
        super.handleIhbStateChanged(state);
        this.mIhb.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleDsmStateChanged(DsmState state) {
        this.mDsmStateData.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleAutoParkStateChanged(ScuResponse state) {
        this.mAutoPark.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleMemParkStateChanged(ApResponse state) {
        this.mMemoryPark.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    public void handlePhoneParkSwChanged(ScuResponse state) {
        this.mPhoneParkSw.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleKeyParkSwChanged(ScuResponse state) {
        this.mKeyParkSw.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleLccStateChanged(ScuResponse state) {
        this.mLcc.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleAccStateChanged(ScuResponse state) {
        this.mAcc.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleAccStateForMirrorChanged(ScuResponse state) {
        this.mAccForMirror.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleIslcStateChanged(ScuResponse state) {
        this.mIslc.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleIslaStateChanged(ScuIslaMode state) {
        this.mIsla.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleIslaSpdRangeChanged(ScuIslaSpdRange spdRange) {
        this.mIslaSpdRange.postValue(spdRange);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleIslaConfirmModeChanged(int confirmMode) {
        this.mIslaConfirmMode.postValue(Boolean.valueOf(confirmMode == 2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    public void handleAlcStateChanged(ScuResponse state) {
        this.mAlc.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleXpuXPilotStateChanged(int state) {
        this.mXpuXPilotActive.postValue(Integer.valueOf(state));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleNgpStateChanged(ScuResponse state) {
        this.mNgp.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleNgpQuickLaneChanged(boolean enabled) {
        this.mNgpQuickLane.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleNgpTruckOffsetChanged(boolean enabled) {
        this.mNgpTruckOffset.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleNgpTipWindowChanged(boolean enabled) {
        this.mNgpTipWindow.postValue(Boolean.valueOf(enabled));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    public void handleNgpVoiceChangeLane(boolean enable) {
        this.mNgpVoiceChangeLane.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleNgpChangeLaneModeChanged(NgpChangeLaneMode mode) {
        this.mNgpChangeLaneMode.postValue(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleSpecialSasStateChanged(int state) {
        this.mSpecialSasData.postValue(Integer.valueOf(state));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleNgpRemindModeChanged(int mode) {
        this.mNgpRemind.postValue(Integer.valueOf(mode));
    }

    public LiveData<ScuResponse> getFcwData() {
        return this.mFcw;
    }

    public LiveData<FcwSensitivity> getFcwSenData() {
        return this.mFcwSen;
    }

    public LiveData<ScuResponse> getRcwData() {
        return this.mRcw;
    }

    public LiveData<ScuResponse> getRctaData() {
        return this.mRcta;
    }

    public LiveData<ScuResponse> getLdwData() {
        return this.mLdw;
    }

    public LiveData<ScuResponse> getElkData() {
        return this.mElk;
    }

    public LiveData<ScuResponse> getBsdData() {
        return this.mBsd;
    }

    public LiveData<ScuResponse> getDowData() {
        return this.mDow;
    }

    public LiveData<ScuResponse> getIhbData() {
        return this.mIhb;
    }

    public LiveData<ScuResponse> getAutoParkData() {
        return this.mAutoPark;
    }

    public LiveData<ApResponse> getMemoryParkData() {
        return this.mMemoryPark;
    }

    public LiveData<ScuResponse> getPhoneParkSwData() {
        return this.mPhoneParkSw;
    }

    public LiveData<ScuResponse> getKeyParkSwData() {
        return this.mKeyParkSw;
    }

    public LiveData<ScuResponse> getLccData() {
        return this.mLcc;
    }

    public LiveData<ScuResponse> getAccForMirrorData() {
        return this.mAccForMirror;
    }

    public LiveData<ScuResponse> getIslcData() {
        return this.mIslc;
    }

    public LiveData<ScuIslaMode> getIslaData() {
        return this.mIsla;
    }

    public LiveData<Integer> getSpecialSasData() {
        return this.mSpecialSasData;
    }

    public LiveData<ScuIslaSpdRange> getIslaSpdRangeData() {
        return this.mIslaSpdRange;
    }

    public LiveData<Boolean> getIslaConfirmModeData() {
        return this.mIslaConfirmMode;
    }

    public LiveData<ScuResponse> getAlcData() {
        return this.mAlc;
    }

    public LiveData<Integer> getXpuXPilotActiveData() {
        return this.mXpuXPilotActive;
    }

    public LiveData<ScuResponse> getNgpData() {
        return this.mNgp;
    }

    public LiveData<Boolean> getNgpTruckOffsetData() {
        return this.mNgpTruckOffset;
    }

    public LiveData<Boolean> getNgpTipWindowData() {
        return this.mNgpTipWindow;
    }

    public LiveData<Boolean> getNgpVoiceChangeLaneData() {
        return this.mNgpVoiceChangeLane;
    }

    public LiveData<NgpChangeLaneMode> getNgpChangeLaneModeData() {
        return this.mNgpChangeLaneMode;
    }

    public LiveData<Integer> getNgpRemindData() {
        return this.mNgpRemind;
    }

    public LiveData<Boolean> getScuOperationTipsData() {
        return this.mScuOptionTipsData;
    }

    public LiveData<SdcRadarDisLevel> getLeftSdcDisLevelData() {
        return this.mLeftSdcDisLevel;
    }

    public LiveData<SdcRadarDisLevel> getRightSdcDisLevelData() {
        return this.mRightSdcDisLevel;
    }

    public LiveData<ScuLssMode> getLssModeData() {
        return this.mScuLssModeData;
    }

    public LiveData<DsmState> getDsmStateData() {
        return this.mDsmStateData;
    }

    public LiveData<Boolean> getCloseMrrDialogData() {
        return this.mCloseMrrDialogData;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onScuOperationTips(int state) {
        this.mScuOptionTipsData.postValue(Boolean.valueOf(state == 3 || state == 8 || state == 24));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onSdcLeftRadarDisLevelChanged(int level) {
        SdcRadarDisLevel fromScuState = SdcRadarDisLevel.fromScuState(level);
        if (fromScuState != null && fromScuState != this.mLeftSdcDisLevel.getValue()) {
            this.mLeftSdcDisLevel.postValue(fromScuState);
        } else {
            LogUtils.w(TAG, "onSdcLeftRadarDisLevelChanged with invalid levels", false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onSdcRightRadarDisLevelChanged(int level) {
        SdcRadarDisLevel fromScuState = SdcRadarDisLevel.fromScuState(level);
        if (fromScuState != null && fromScuState != this.mRightSdcDisLevel.getValue()) {
            this.mRightSdcDisLevel.postValue(fromScuState);
        } else {
            LogUtils.w(TAG, "onSdcRightRadarDisLevelChanged with invalid levels", false);
        }
    }

    public SdcRadarDisLevel getLeftSdcRadarDisplayLevel() {
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            if (this.mLeftSdcDisLevel.getValue() == null) {
                SdcRadarDisLevel fromScuState = SdcRadarDisLevel.fromScuState(getSdcLeftRadarDisLevel());
                if (fromScuState != null && fromScuState != this.mLeftSdcDisLevel.getValue()) {
                    this.mLeftSdcDisLevel.postValue(fromScuState);
                } else {
                    LogUtils.w(TAG, "onSdcRightRadarDisLevelChanged with invalid levels", false);
                }
            }
            return this.mLeftSdcDisLevel.getValue();
        }
        return null;
    }

    public SdcRadarDisLevel getRightSdcRadarDisplayLevel() {
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            if (this.mRightSdcDisLevel.getValue() == null) {
                SdcRadarDisLevel fromScuState = SdcRadarDisLevel.fromScuState(getSdcRightRadarDisLevel());
                if (fromScuState != null && fromScuState != this.mRightSdcDisLevel.getValue()) {
                    this.mRightSdcDisLevel.postValue(fromScuState);
                } else {
                    LogUtils.w(TAG, "onSdcRightRadarDisLevelChanged with invalid levels", false);
                }
            }
            return this.mRightSdcDisLevel.getValue();
        }
        return null;
    }
}
