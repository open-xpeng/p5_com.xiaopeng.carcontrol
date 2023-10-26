package com.xiaopeng.carcontrol.viewmodel.cabin;

import android.os.Handler;
import android.os.Message;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class WindowDoorViewModel extends WindowDoorBaseViewModel {
    private static final long SUN_SHADE_LOADING_TIME = 1000;
    private static final String TAG = "WindowDoorViewModel";
    private final MutableLiveData<Boolean> mFlDoorState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mFrDoorState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mRlDoorState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mRrDoorState = new MutableLiveData<>();
    private final MutableLiveData<String> mMixedDoorState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCentralLockState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAutoWindowLockSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mWindowLockStateData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHighSpdCloseWinData = new MutableLiveData<>();
    protected final MutableLiveData<WindowState> mWindowState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mBonnetState = new MutableLiveData<>();
    protected final MutableLiveData<TrunkState> mRearTrunkState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mDoorHandle = new MutableLiveData<>();
    private final MutableLiveData<Float> mDrvWinPos = new MutableLiveData<>();
    private final MutableLiveData<Float> mFrWinPos = new MutableLiveData<>();
    private final MutableLiveData<Float> mRlWinPos = new MutableLiveData<>();
    private final MutableLiveData<Float> mRrWinPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mWinMoveState = new MutableLiveData<>();
    private final Handler mWindowHandler = new Handler(ThreadUtils.getHandler(0).getLooper(), new Handler.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$WindowDoorViewModel$dsJ9w2RAdJPNJbGjwAIwDw5w8JQ
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            return WindowDoorViewModel.this.lambda$new$0$WindowDoorViewModel(message);
        }
    });
    private final MutableLiveData<WindowKeyCtrlMode> mKeyCtrlMode = new MutableLiveData<>();
    private final MutableLiveData<Integer> mLeftSdcState = new MutableLiveData<>();
    private final MutableLiveData<Integer> mRightSdcState = new MutableLiveData<>();
    private final MutableLiveData<Integer> mLeftSdcDoorPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mRightSdcDoorPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mLeftSdcSycRunningState = new MutableLiveData<>();
    private final MutableLiveData<Integer> mRightSdcSycRunningState = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSdcKeyCfg = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mSdcWinAutoDown = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSdcMaxDoorOpeningAngleData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSdcBrakeCloseDoorCfgData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mLeftSlideDoorMode = new MutableLiveData<>();
    private final MutableLiveData<Integer> mRightSlideDoorMode = new MutableLiveData<>();
    private final MutableLiveData<Integer> mLeftSlideDoorState = new MutableLiveData<>();
    private final MutableLiveData<Integer> mRightSlideDoorState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLeftSlideDoorLockState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mRightSlideDoorLockState = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSunShadePosData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mSunShadeLoadingData = new MutableLiveData<>();
    private final Runnable mDelayUpdateSunShadeLoading = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$WindowDoorViewModel$udNiO6V6x8Op6kWgJfmLwpudxB0
        @Override // java.lang.Runnable
        public final void run() {
            WindowDoorViewModel.this.lambda$new$1$WindowDoorViewModel();
        }
    };
    private final MutableLiveData<Boolean> mTrunkSensorData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTrunkFullOpenPosData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mElcTrunkPosData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mRearTrunkWorkStatus = new MutableLiveData<>();
    private final Runnable mWinLoadingTimeoutTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$P_juaaQTWV7B8E2yA7lfu--VbIc
        @Override // java.lang.Runnable
        public final void run() {
            WindowDoorViewModel.this.handleWindowStateChange();
        }
    };

    public /* synthetic */ boolean lambda$new$0$WindowDoorViewModel(Message msg) {
        if (msg != null && msg.what == 1) {
            this.mWinMoveState.postValue(0);
        }
        return true;
    }

    public /* synthetic */ void lambda$new$1$WindowDoorViewModel() {
        this.mSunShadeLoadingData.postValue(false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public WindowDoorViewModel() {
        initTrunkState();
        initCentralLockState();
        this.mBcmController.registerCallback(this);
        ThreadUtils.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$FCP40y9p3OYqPWrs-j6dyMAGw20
            @Override // java.lang.Runnable
            public final void run() {
                WindowDoorViewModel.this.initTrunkState();
            }
        }, 10000L);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public WindowState getWindowStateValue() {
        return this.mWindowState.getValue();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleDoorStateChanged(boolean[] doorState) {
        this.mFlDoorState.postValue(Boolean.valueOf(doorState[0]));
        this.mFrDoorState.postValue(Boolean.valueOf(doorState[1]));
        this.mRlDoorState.postValue(Boolean.valueOf(doorState[2]));
        this.mRrDoorState.postValue(Boolean.valueOf(doorState[3]));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleDoorStateChanged(String mixedDoorState) {
        this.mMixedDoorState.postValue(mixedDoorState);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleCentralLockStateChanged(boolean isLocked) {
        this.mCentralLockState.postValue(Boolean.valueOf(isLocked));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleAutoDoorHandleChanged(boolean enabled) {
        this.mDoorHandle.postValue(Boolean.valueOf(enabled));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    public void handleWinPosChanged(int window) {
        float[] windowsPos = getWindowsPos();
        if (windowsPos == null || windowsPos.length < 4) {
            LogUtils.d(TAG, "handleWinPosChanged: windowPos array is error");
            return;
        }
        this.mDrvWinPos.postValue(Float.valueOf(windowsPos[0]));
        this.mFrWinPos.postValue(Float.valueOf(windowsPos[1]));
        this.mRlWinPos.postValue(Float.valueOf(windowsPos[2]));
        this.mRrWinPos.postValue(Float.valueOf(windowsPos[3]));
        if (this.mWinMoveState.getValue() == null || 1 != this.mWinMoveState.getValue().intValue()) {
            this.mWinMoveState.postValue(1);
        }
        this.mWindowHandler.removeMessages(1);
        this.mWindowHandler.sendEmptyMessageDelayed(1, 1000L);
    }

    public LiveData<Integer> getWinMoveStateData() {
        return this.mWinMoveState;
    }

    public LiveData<Integer> getElcTrunkPosData() {
        return this.mElcTrunkPosData;
    }

    public LiveData<Integer> getRearTrunkWorkStatusData() {
        return this.mRearTrunkWorkStatus;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleElcTrunkPosChanged(int pos) {
        this.mElcTrunkPosData.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleTrunkStateChanged(int state) {
        this.mRearTrunkState.postValue(state == 0 ? TrunkState.Closed : TrunkState.Opened);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleTrunkWorkStatusChanged(int state) {
        this.mRearTrunkWorkStatus.postValue(Integer.valueOf(state));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleBonnetStateChanged(boolean isClosed) {
        this.mBonnetState.postValue(Boolean.valueOf(isClosed));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleKeyCtrlModeChanged(int mode) {
        WindowKeyCtrlMode fromBcmState = WindowKeyCtrlMode.fromBcmState(mode);
        if (fromBcmState != null) {
            this.mKeyCtrlMode.postValue(fromBcmState);
        } else {
            LogUtils.d(TAG, "Error KeyCtrl Mode: " + mode, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleAutoWindowLockSwChanged(boolean enable) {
        this.mAutoWindowLockSwData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleWindowLockStateChanged(boolean on) {
        this.mWindowLockStateData.postValue(Boolean.valueOf(on));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleHighSpdCloseWinChanged(boolean enable) {
        this.mHighSpdCloseWinData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleLeftSdcStateChanged(int state) {
        this.mLeftSdcState.postValue(Integer.valueOf(state));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleRightSdcStateChanged(int state) {
        this.mRightSdcState.postValue(Integer.valueOf(state));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleLeftSdcDoorPosChanged(int pos) {
        this.mLeftSdcDoorPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleRightSdcDoorPosChanged(int pos) {
        this.mRightSdcDoorPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleLeftSdcSysRunningStateChanged(int state) {
        this.mLeftSdcSycRunningState.postValue(Integer.valueOf(state));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleRightSdcSysRunningStateChanged(int state) {
        this.mRightSdcSycRunningState.postValue(Integer.valueOf(state));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleSdcKeyCfgChanged(int mode) {
        this.mSdcKeyCfg.postValue(Integer.valueOf(mode));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleSdcAutoWinDownChanged(boolean auto) {
        this.mSdcWinAutoDown.postValue(Boolean.valueOf(auto));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlRearTrunk(TrunkType type) {
        if (type != null) {
            controlRearTrunk(type.toBcmTrunkType());
        }
    }

    public LiveData<Boolean> getAutoWindowLockSwData() {
        return this.mAutoWindowLockSwData;
    }

    public LiveData<Boolean> getWindowLockStateData() {
        return this.mWindowLockStateData;
    }

    public LiveData<Boolean> getHighSpdCloseWinSwData() {
        return this.mHighSpdCloseWinData;
    }

    public LiveData<WindowState> getWindowStateData() {
        return this.mWindowState;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void initTrunkState() {
        if (CarBaseConfig.getInstance().isSupportElcTrunk()) {
            int elcTrunkState = getElcTrunkState();
            TrunkState convertTrunkState = convertTrunkState(elcTrunkState);
            MutableLiveData<TrunkState> mutableLiveData = this.mRearTrunkState;
            if (convertTrunkState == null) {
                convertTrunkState = TrunkState.Closed;
            }
            mutableLiveData.postValue(convertTrunkState);
            final TrunkState convertTrunkStateToQuickSetting = convertTrunkStateToQuickSetting(elcTrunkState);
            QuickSettingManager.getInstance().onSignalCallbackSingleThread(QuickSettingConstants.KEY_CONTROL_TRUNK_INT, new Supplier() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$WindowDoorViewModel$l-JutLEzSuT8D9isfaOJz8e9zQs
                @Override // java.util.function.Supplier
                public final Object get() {
                    return WindowDoorViewModel.lambda$initTrunkState$2(TrunkState.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ TrunkState lambda$initTrunkState$2(final TrunkState quickSettingTrunkState) {
        return quickSettingTrunkState == null ? TrunkState.Closed : quickSettingTrunkState;
    }

    private void initCentralLockState() {
        this.mCentralLockState.postValue(Boolean.valueOf(isCentralLocked()));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onElcTrunkStateChanged(int state) {
        TrunkState convertTrunkState = convertTrunkState(state);
        if (convertTrunkState != null) {
            this.mRearTrunkState.postValue(convertTrunkState);
        }
    }

    public TrunkState getRearTrunkStateValue() {
        return this.mRearTrunkState.getValue();
    }

    public LiveData<Boolean> getDoorStateData(int doorIdx) {
        if (doorIdx != 0) {
            if (doorIdx != 1) {
                if (doorIdx != 2) {
                    if (doorIdx != 3) {
                        return null;
                    }
                    return this.mRrDoorState;
                }
                return this.mRlDoorState;
            }
            return this.mFrDoorState;
        }
        return this.mFlDoorState;
    }

    public LiveData<String> getMixedDoorData() {
        return this.mMixedDoorState;
    }

    public LiveData<Boolean> getCentralLockStateData() {
        return this.mCentralLockState;
    }

    public LiveData<Boolean> getBonnetData() {
        return this.mBonnetState;
    }

    public LiveData<TrunkState> getRearTrunkData() {
        return this.mRearTrunkState;
    }

    public LiveData<Integer> getLeftSdcStateData() {
        return this.mLeftSdcState;
    }

    public LiveData<Integer> getRightSdcStateData() {
        return this.mRightSdcState;
    }

    public LiveData<Integer> getLeftSdcDoorPosData() {
        return this.mLeftSdcDoorPos;
    }

    public LiveData<Integer> getRightSdcDoorPosData() {
        return this.mRightSdcDoorPos;
    }

    public LiveData<Integer> getLeftSdcSysRunningStateData() {
        return this.mLeftSdcSycRunningState;
    }

    public LiveData<Integer> getRightSdcSysRunningStateData() {
        return this.mRightSdcSycRunningState;
    }

    public LiveData<Integer> getSdcKeyCfgData() {
        return this.mSdcKeyCfg;
    }

    public LiveData<Boolean> getSdcWinAutoDownData() {
        return this.mSdcWinAutoDown;
    }

    public LiveData<Integer> getSdcMaxAutoDoorOpeningAngleData() {
        return this.mSdcMaxDoorOpeningAngleData;
    }

    public LiveData<Integer> getSdcBrakeCloseDoorCfgData() {
        return this.mSdcBrakeCloseDoorCfgData;
    }

    public LiveData<Integer> getSunShadePosData() {
        return this.mSunShadePosData;
    }

    public LiveData<WindowKeyCtrlMode> getKeyControlData() {
        return this.mKeyCtrlMode;
    }

    public MutableLiveData<Integer> getLeftSlideDoorModeData() {
        return this.mLeftSlideDoorMode;
    }

    public MutableLiveData<Integer> getRightSlideDoorModeData() {
        return this.mRightSlideDoorMode;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Deprecated
    public void handleWindowStateChange() {
        float[] windowsPos = getWindowsPos();
        if (windowsPos == null || windowsPos.length < 4) {
            LogUtils.d(TAG, "handleWindowStateChange: windowPos array is error");
            this.mWindowState.postValue(WindowState.Closed);
            return;
        }
        LogUtils.d(TAG, "handleWindowStateChange: lastWinState=" + this.mWindowState.getValue());
        if (isWindowsAlmostClose(windowsPos)) {
            this.mWindowState.postValue(WindowState.Closed);
        } else if (isWindowsAlmostOpen(windowsPos)) {
            this.mWindowState.postValue(WindowState.Opened);
        } else {
            this.mWindowState.postValue(WindowState.HalfOpened);
        }
    }

    protected boolean isWindowsAlmostClose(float[] windowsState) {
        return windowsState[0] >= 95.0f && windowsState[1] >= 95.0f && windowsState[2] >= 95.0f && windowsState[3] >= 95.0f;
    }

    protected boolean isWindowsAlmostOpen(float[] windowsState) {
        return windowsState[0] <= 3.0f && windowsState[1] <= 3.0f && windowsState[2] <= 3.0f && windowsState[3] <= 3.0f;
    }

    public void controlWindowClose() {
        if (isWindowInitFailed(4)) {
            return;
        }
        if (isWindowLockActive()) {
            NotificationHelper.getInstance().showToast(R.string.win_lock_is_active, true);
            return;
        }
        controlWindowAuto(false);
        this.mWindowHandler.sendEmptyMessageDelayed(1, 1000L);
        this.mWindowState.postValue(WindowState.Closing);
        ThreadUtils.postDelayed(2, this.mWinLoadingTimeoutTask, BaseFeatureOption.getInstance().getWindowVentingTime());
    }

    public void controlWindowOpen() {
        if (isWindowInitFailed(4)) {
            return;
        }
        if (isWindowLockActive()) {
            NotificationHelper.getInstance().showToast(R.string.win_lock_is_active, true);
            return;
        }
        controlWindowAuto(true);
        this.mWindowHandler.sendEmptyMessageDelayed(1, 1000L);
        this.mWindowState.postValue(WindowState.Opening);
        ThreadUtils.postDelayed(2, this.mWinLoadingTimeoutTask, BaseFeatureOption.getInstance().getWindowLoadingTime());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void controlWindowVent() {
        if (isWindowInitFailed(4)) {
            return;
        }
        if (isWindowLockActive()) {
            NotificationHelper.getInstance().showToast(R.string.win_lock_is_active, true);
            return;
        }
        super.controlWindowVent();
        this.mWindowHandler.sendEmptyMessageDelayed(1, 1000L);
        this.mWindowState.postValue(WindowState.Ventting);
        ThreadUtils.postDelayed(2, this.mWinLoadingTimeoutTask, BaseFeatureOption.getInstance().getWindowVentingTime());
    }

    public LiveData<Float> getDrvWinPosData() {
        return this.mDrvWinPos;
    }

    public LiveData<Float> getFrWinPosData() {
        return this.mFrWinPos;
    }

    public LiveData<Float> getRlWinPosData() {
        return this.mRlWinPos;
    }

    public LiveData<Float> getRrWinPosData() {
        return this.mRrWinPos;
    }

    public LiveData<Boolean> getDoorHandle() {
        return this.mDoorHandle;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleSdcMaxDoorOpeningAngleChange(int angle) {
        this.mSdcMaxDoorOpeningAngleData.postValue(Integer.valueOf(angle));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleSdcBrakeCloseDoorCfgChange(int cfg) {
        this.mSdcBrakeCloseDoorCfgData.postValue(Integer.valueOf(cfg));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleLeftSlideDoorModeChanged(int mode) {
        this.mLeftSlideDoorMode.postValue(Integer.valueOf(mode));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleRightSlideDoorModeChanged(int mode) {
        this.mRightSlideDoorMode.postValue(Integer.valueOf(mode));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleLeftSlideDoorStateChanged(int state) {
        this.mLeftSlideDoorState.postValue(Integer.valueOf(state));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleRightSlideDoorStateChanged(int state) {
        this.mRightSlideDoorState.postValue(Integer.valueOf(state));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleLeftSlideDoorLockStateChanged(int state) {
        this.mLeftSlideDoorLockState.postValue(Boolean.valueOf(state == 1));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleRightSlideDoorLockStateChanged(int state) {
        this.mRightSlideDoorLockState.postValue(Boolean.valueOf(state == 1));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel
    protected void handleSunShadePosChange(int pos) {
        this.mSunShadePosData.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void openSunShade() {
        if (!isSunShadeInitialized()) {
            NotificationHelper.getInstance().showToast(R.string.sun_shade_initializing);
            return;
        }
        ThreadUtils.removeRunnable(this.mDelayUpdateSunShadeLoading);
        this.mSunShadeLoadingData.postValue(true);
        ThreadUtils.postDelayed(2, this.mDelayUpdateSunShadeLoading, 1000L);
        super.openSunShade();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel
    public void closeSunShade() {
        if (!isSunShadeInitialized()) {
            NotificationHelper.getInstance().showToast(R.string.sun_shade_initializing);
            return;
        }
        LogUtils.d(TAG, "closeSunShade", false);
        ThreadUtils.removeRunnable(this.mDelayUpdateSunShadeLoading);
        this.mSunShadeLoadingData.postValue(true);
        ThreadUtils.postDelayed(2, this.mDelayUpdateSunShadeLoading, 1000L);
        super.closeSunShade();
    }

    public LiveData<Boolean> getSunShadeLoadingData() {
        return this.mSunShadeLoadingData;
    }

    public boolean isSdcRunning() {
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            int leftSdcSysRunningState = getLeftSdcSysRunningState();
            int rightSdcSysRunningState = getRightSdcSysRunningState();
            return leftSdcSysRunningState == 2 || leftSdcSysRunningState == 3 || rightSdcSysRunningState == 2 || rightSdcSysRunningState == 3;
        }
        return false;
    }

    public LiveData<Integer> getLeftSlideDoorStateData() {
        return this.mLeftSlideDoorState;
    }

    public LiveData<Integer> getRightSlideDoorStateData() {
        return this.mRightSlideDoorState;
    }

    public LiveData<Boolean> getTrunkSensorData() {
        return this.mTrunkSensorData;
    }

    public LiveData<Integer> getTrunkFullOpenPosData() {
        return this.mTrunkFullOpenPosData;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onTrunkSensorEnableChanged(boolean enable) {
        this.mTrunkSensorData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
    public void onTrunkFullOpenPosChanged(int level) {
        this.mTrunkFullOpenPosData.postValue(Integer.valueOf(IWindowDoorViewModel.convertTrunkFullOpenPosLevel2Pos(level)));
    }
}
