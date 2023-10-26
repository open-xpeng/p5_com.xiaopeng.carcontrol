package com.xiaopeng.carcontrol.viewmodel.cabin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.model.DataSyncModel;

/* loaded from: classes2.dex */
public class MirrorViewModel extends MirrorBaseViewModel {
    private static final String TAG = "MirrorViewModel";
    private final MutableLiveData<MirrorFoldState> mMirrorFoldData = new MutableLiveData<>();
    private final MutableLiveData<MirrorReverseMode> mMirrorReverseMode = new MutableLiveData<>();
    final MutableLiveData<Boolean> mMirrorAutoDownData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mMirrorAutoFoldEnable = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCmsObjectRecognizeSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCmsReverseSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCmsTurnSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCmsHighSpdSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCmsLowSpdSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCmsAutoBrightSwData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mCmsBrightData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mCmsViewAngleData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mMirrorPanelShowData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mImsAutoVisionSwData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mImsBrightLevelData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mImsModeData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mImsSystemStData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mImsVisionAngleLevelData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mImsVisionVerticalLevelData = new MutableLiveData<>();
    private int prevRearScreenState = this.mBcmController.getRearScreenState();

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleMirrorAutoDownChanged(boolean enabled) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MirrorViewModel() {
        this.mBcmController.registerCallback(this.mBcmCallback);
        registerContentObserver();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    public void handleFoldStateChanged(MirrorFoldState state) {
        super.handleFoldStateChanged(state);
        this.mMirrorFoldData.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleReverseModeChanged(MirrorReverseMode mode) {
        this.mMirrorReverseMode.postValue(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleAutoFoldStateChanged(boolean state) {
        this.mMirrorAutoFoldEnable.postValue(Boolean.valueOf(state));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleCmsObjectRecognizeSwChanged(boolean enabled) {
        this.mCmsObjectRecognizeSwData.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleCmsReverseSwChanged(boolean enabled) {
        this.mCmsReverseSwData.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleCmsTurnSwChanged(boolean enabled) {
        this.mCmsTurnSwData.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleCmsHighSpdSwChanged(boolean enabled) {
        this.mCmsHighSpdSwData.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleCmsLowSpdSwChanged(boolean enabled) {
        this.mCmsLowSpdSwData.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleCmsAutoBrightSwChanged(boolean enabled) {
        this.mCmsAutoBrightSwData.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleCmsBrightChanged(int brightness) {
        this.mCmsBrightData.postValue(Integer.valueOf(brightness));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleCmsViewAngleChanged(int viewAngle) {
        this.mCmsViewAngleData.postValue(Integer.valueOf(viewAngle));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleRearScreenStateChanged(int state) {
        if (CarBaseConfig.getInstance().isSupportIms() && CarBaseConfig.getInstance().isSupportRearScreen()) {
            int i = this.prevRearScreenState;
            if (i == 0 && state != 0) {
                this.mBcmController.setImsMode(1, false);
            } else if (i != 0 && state == 0) {
                this.mBcmController.setImsMode(DataSyncModel.getInstance().getImsMode(), false);
            }
            this.prevRearScreenState = state;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleImsAutoVisionSwChanged(boolean enabled) {
        this.mImsAutoVisionSwData.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleImsBrightLevelChanged(int level) {
        this.mImsBrightLevelData.postValue(Integer.valueOf(level));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleImsModeChanged(int mode) {
        this.mImsModeData.postValue(Integer.valueOf(mode));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleImsSystemStChanged(int status) {
        this.mImsSystemStData.postValue(Integer.valueOf(status));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleImsVisionAngleLevelChanged(int level) {
        this.mImsVisionAngleLevelData.postValue(Integer.valueOf(level));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel
    protected void handleImsVisionVerticalLevelChanged(int level) {
        this.mImsVisionVerticalLevelData.postValue(Integer.valueOf(level));
    }

    public LiveData<MirrorFoldState> getMirrorFoldData() {
        return this.mMirrorFoldData;
    }

    public LiveData<MirrorReverseMode> getMirrorReverseData() {
        return this.mMirrorReverseMode;
    }

    public LiveData<Boolean> getMirrorAutoDownData() {
        return this.mMirrorAutoDownData;
    }

    public LiveData<Boolean> getMirrorAutoFoldEnable() {
        return this.mMirrorAutoFoldEnable;
    }

    public LiveData<Boolean> getCmsObjectRecognizeSwData() {
        return this.mCmsObjectRecognizeSwData;
    }

    public LiveData<Boolean> getCmsReverseSwData() {
        return this.mCmsReverseSwData;
    }

    public LiveData<Boolean> getCmsTurnSwData() {
        return this.mCmsTurnSwData;
    }

    public LiveData<Boolean> getCmsHighSpdSwData() {
        return this.mCmsHighSpdSwData;
    }

    public LiveData<Boolean> getCmsLowSpdSwData() {
        return this.mCmsLowSpdSwData;
    }

    public LiveData<Boolean> getCmsAutoBrightSwData() {
        return this.mCmsAutoBrightSwData;
    }

    public LiveData<Integer> getCmsBrightData() {
        return this.mCmsBrightData;
    }

    public LiveData<Integer> getCmsViewAngleData() {
        return this.mCmsViewAngleData;
    }

    public LiveData<Boolean> getMirrorPanelShowData() {
        return this.mMirrorPanelShowData;
    }

    public LiveData<Boolean> getImsAutoVisionSwData() {
        return this.mImsAutoVisionSwData;
    }

    public LiveData<Integer> getImsBrightLevelData() {
        return this.mImsBrightLevelData;
    }

    public LiveData<Integer> getImsModeData() {
        return this.mImsModeData;
    }

    public LiveData<Integer> getImsSystemStData() {
        return this.mImsSystemStData;
    }

    public LiveData<Integer> getImsVisionAngleLevelData() {
        return this.mImsVisionAngleLevelData;
    }

    public LiveData<Integer> getImsVisionVerticalLevelData() {
        return this.mImsVisionVerticalLevelData;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void showMirrorCtrlPanel(boolean show) {
        this.mMirrorPanelShowData.postValue(Boolean.valueOf(show));
    }
}
