package com.xiaopeng.carcontrol.viewmodel.carsettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;

/* loaded from: classes2.dex */
public class CarBodyViewModel extends CarBodyBaseViewModel {
    private static final String TAG = "CarBodyViewModel";
    private final MutableLiveData<Boolean> mDriveAutoLock = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mParkingAutoUnlock = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mNearAutoUnlock = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLeaveAutoLock = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mInductionLockEnable = new MutableLiveData<>();
    private final MutableLiveData<UnlockResponse> mUnlockResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mChildMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLeftChildLock = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mRightChildLock = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLeftDoorHotKey = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mRightDoorHotKey = new MutableLiveData<>();
    protected final MutableLiveData<WiperInterval> mWiperInterval = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mWiperRepairMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mRearWiperRepairMode = new MutableLiveData<>();
    private final MutableLiveData<ChargePortState> mLeftChargePortState = new MutableLiveData<>();
    private final MutableLiveData<ChargePortState> mRightChargePortState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mStealthData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mStopNfcData = new MutableLiveData<>();
    private final MutableLiveData<WiperSensitivity> mWiperSensitivity = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCwcSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCwcFRSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCwcRLSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCwcRRSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAutoPowerOffData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mArsWorkModeData = new MutableLiveData<>();

    public CarBodyViewModel() {
        this.mBcmController.registerCallback(this.mBcmCallback);
        this.mTboxController.registerCallback(this.mTboxCallBack);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.carsettings.-$$Lambda$6xWlSYWUO-NWLxxNxJOpPgpyk1E
            @Override // java.lang.Runnable
            public final void run() {
                CarBodyViewModel.this.initChargePort();
            }
        });
        ThreadUtils.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.carsettings.-$$Lambda$6xWlSYWUO-NWLxxNxJOpPgpyk1E
            @Override // java.lang.Runnable
            public final void run() {
                CarBodyViewModel.this.initChargePort();
            }
        }, 10000L);
    }

    public LiveData<Boolean> getDriveAutoLockData() {
        return this.mDriveAutoLock;
    }

    public LiveData<Boolean> getParkingAutoUnlockData() {
        return this.mParkingAutoUnlock;
    }

    public LiveData<Boolean> getNearAutoUnlockData() {
        return this.mNearAutoUnlock;
    }

    public LiveData<Boolean> getLeaveAutoLockData() {
        return this.mLeaveAutoLock;
    }

    public MutableLiveData<Boolean> getInductionLockData() {
        return this.mInductionLockEnable;
    }

    public void setUnlockResponseMode(UnlockResponse type) {
        if (type != null) {
            setUnlockResponse(type.toBcmCmd());
        }
    }

    public UnlockResponse getUnlockResponseMode() {
        return UnlockResponse.fromBcmState(getUnlockResponse());
    }

    public LiveData<UnlockResponse> getUnlockResponseData() {
        return this.mUnlockResponse;
    }

    public LiveData<Boolean> getChildMode() {
        return this.mChildMode;
    }

    public LiveData<Boolean> getLeftChildLockData() {
        return this.mLeftChildLock;
    }

    public LiveData<Boolean> getRightChildLockData() {
        return this.mRightChildLock;
    }

    public LiveData<Boolean> getLeftDoorHotKey() {
        return this.mLeftDoorHotKey;
    }

    public LiveData<Boolean> getRightDoorHotKey() {
        return this.mRightDoorHotKey;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setWiperInterval(WiperInterval interval) {
        if (interval != null) {
            setWiperSensitivity(interval.toSensitivityBcmCmd());
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public WiperInterval getWiperIntervalValue() {
        WiperInterval value = this.mWiperInterval.getValue();
        if (value != null) {
            return value;
        }
        try {
            return WiperInterval.fromSensitivityBcmState(getWiperSensitivity());
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
    }

    public void setWiperSensitivity(WiperSensitivity level) {
        if (level != null) {
            setWiperSensitivity(level.toBcmCmd());
        } else {
            LogUtils.e(TAG, "setWiperSensitivity with null");
        }
    }

    public WiperSensitivity getWiperSensitivityValue() {
        return WiperSensitivity.fromBcmState(getWiperSensitivity());
    }

    public LiveData<WiperInterval> getWiperIntervalData() {
        return this.mWiperInterval;
    }

    public LiveData<Boolean> getWiperRepairModeData() {
        return this.mWiperRepairMode;
    }

    public MutableLiveData<Boolean> getRearWiperRepairModeData() {
        return this.mRearWiperRepairMode;
    }

    public LiveData<WiperSensitivity> getWiperSensitivityData() {
        return this.mWiperSensitivity;
    }

    public void controlChargePort(ChargePort chargePort, boolean unlock) {
        setChargePortUnlock(chargePort.toBcmPortValue(), unlock);
    }

    public LiveData<ChargePortState> getLeftChargePortStateData() {
        return this.mLeftChargePortState;
    }

    public LiveData<ChargePortState> getRightChargePortStateData() {
        return this.mRightChargePortState;
    }

    public ChargePortState getChargePortState(ChargePort port) {
        try {
            return ChargePortState.fromBcmValue(getChargePortUnlock(port.toBcmPortValue()));
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
            return ChargePortState.UNKNOWN;
        }
    }

    public LiveData<Boolean> getStopNfcSwData() {
        return this.mStopNfcData;
    }

    public LiveData<Boolean> getAutoPowerOffData() {
        return this.mAutoPowerOffData;
    }

    public LiveData<Integer> getArsWorkModeData() {
        return this.mArsWorkModeData;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public void setAutoPowerOffConfig(boolean status) {
        this.mTboxController.setAutoPowerOffConfig(status);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel
    public boolean getAutoPowerOffConfig() {
        return this.mTboxController.getAutoPowerOffConfig();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleDriveLockChanged(boolean enabled) {
        this.mDriveAutoLock.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleParkUnlockChanged(boolean enabled) {
        this.mParkingAutoUnlock.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleNearAutoUnlock(boolean enabled) {
        this.mNearAutoUnlock.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleLeaveAutoLock(boolean enabled) {
        this.mLeaveAutoLock.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleInductionLock(boolean enabled) {
        this.mInductionLockEnable.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleUnlockTypeChanged(int type) {
        this.mUnlockResponse.postValue(UnlockResponse.fromBcmState(type));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleChildLockChanged(boolean leftLocked, boolean rightLocked) {
        this.mLeftChildLock.postValue(Boolean.valueOf(leftLocked));
        this.mRightChildLock.postValue(Boolean.valueOf(rightLocked));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleChildModeChanged(boolean locked) {
        this.mChildMode.postValue(Boolean.valueOf(locked));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleLeftChildLockChanged(boolean locked) {
        this.mLeftChildLock.postValue(Boolean.valueOf(locked));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleRightChildLockChanged(boolean locked) {
        this.mRightChildLock.postValue(Boolean.valueOf(locked));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleLeftHotKeyChanged(boolean locked) {
        this.mLeftDoorHotKey.postValue(Boolean.valueOf(locked));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleRightHotKeyChanged(boolean locked) {
        this.mRightDoorHotKey.postValue(Boolean.valueOf(locked));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleWiperIntervalModeChanged(int wiper) {
        WiperInterval fromBcmState = WiperInterval.fromBcmState(wiper);
        if (fromBcmState != null) {
            this.mWiperInterval.postValue(fromBcmState);
        } else {
            LogUtils.d(TAG, "Error WiperIntervalMode: " + wiper, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleCiuRainWiperIntervalChanged(int wiper) {
        WiperInterval fromCiuState = WiperInterval.fromCiuState(wiper);
        if (fromCiuState != null) {
            this.mWiperInterval.postValue(fromCiuState);
        } else {
            LogUtils.d(TAG, "Error Ciu Rain WiperMode: " + wiper, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleWiperSensitivityChanged(int level) {
        WiperInterval fromSensitivityBcmState = WiperInterval.fromSensitivityBcmState(level);
        if (fromSensitivityBcmState != null) {
            this.mWiperInterval.postValue(fromSensitivityBcmState);
        } else {
            LogUtils.d(TAG, "Error Wiper Sensitivity: " + level, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleWiperRepairModeChanged(boolean mode) {
        this.mWiperRepairMode.postValue(Boolean.valueOf(mode));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleRearWiperRepairModeChanged(boolean mode) {
        this.mRearWiperRepairMode.postValue(Boolean.valueOf(mode));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    public void handleChargePortChanged(boolean isLeft, int state) {
        super.handleChargePortChanged(isLeft, state);
        ChargePortState fromBcmValue = ChargePortState.fromBcmValue(state);
        if (fromBcmValue == null) {
            LogUtils.e(TAG, "Error Charge Port State: " + state, false);
        } else if (isLeft) {
            this.mLeftChargePortState.postValue(fromBcmValue);
        } else {
            this.mRightChargePortState.postValue(fromBcmValue);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleStealModeChanged(boolean enable) {
        this.mStealthData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleNfcStopSwChanged(boolean enable) {
        this.mStopNfcData.postValue(Boolean.valueOf(enable));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    public void handleAutoPowerOffConfigChanged(boolean enable) {
        this.mAutoPowerOffData.postValue(Boolean.valueOf(enable));
    }

    public LiveData<Boolean> getCwcSwData() {
        return this.mCwcSwData;
    }

    public MutableLiveData<Boolean> getCwcFRSwData() {
        return this.mCwcFRSwData;
    }

    public MutableLiveData<Boolean> getCwcRLSwData() {
        return this.mCwcRLSwData;
    }

    public MutableLiveData<Boolean> getCwcRRSwData() {
        return this.mCwcRRSwData;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleCwcSwChanged(boolean enable) {
        this.mCwcSwData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleCwcFRSwChanged(boolean enable) {
        this.mCwcFRSwData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleCwcRLSwChanged(boolean enable) {
        this.mCwcRLSwData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleCwcRRSwChanged(boolean enable) {
        this.mCwcRRSwData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyBaseViewModel
    protected void handleArsWorkModeChanged(int mode) {
        this.mArsWorkModeData.postValue(Integer.valueOf(mode));
    }
}
