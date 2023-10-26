package com.xiaopeng.carcontrol.viewmodel.meter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.bean.MaintainInfo;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class MeterViewModel extends MeterBaseViewModel {
    private static final String TAG = "MeterViewModel";
    private final MutableLiveData<String> mMileageTotal = new MutableLiveData<>();
    private final MutableLiveData<String> mMileageSinceLastCharge = new MutableLiveData<>();
    private final MutableLiveData<String> mMileageSinceStartUp = new MutableLiveData<>();
    private final MutableLiveData<String> mMileageA = new MutableLiveData<>();
    private final MutableLiveData<String> mMileageB = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mSpdWarningSw = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSpdWarningValue = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mMenuTempSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mMenuWindPowerSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mMenuWindModeSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mMenuBrightSw = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mMenuMediaSw = new MutableLiveData<>();
    private final MutableLiveData<XKeyForCustomer> mXKeyData = new MutableLiveData<>();
    private final MutableLiveData<DoorKeyForCustomer> mDoorKeyData = new MutableLiveData<>();
    private final MutableLiveData<MaintainInfo> mLastMaintainData = new MutableLiveData<>();
    private final MutableLiveData<MaintainInfo> mNextMaintainData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mEcallAvailableData = new MutableLiveData<>();

    public MeterViewModel() {
        if (!App.isMainProcess()) {
            registerContentObserver();
        }
        this.mIcmController.registerCallback(this);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onMileageTotalChanged(float value) {
        this.mMileageTotal.postValue(formatMileage(value, 0));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onMileageLastChargeChanged(float value) {
        this.mMileageSinceLastCharge.postValue(formatMileage(value, 1));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onMileageStartUpChanged(float value) {
        this.mMileageSinceStartUp.postValue(formatMileage(value, 1));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onMileageAChanged(float value) {
        this.mMileageA.postValue(formatMileage(value, 1));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onMileageBChanged(float value) {
        this.mMileageB.postValue(formatMileage(value, 1));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onSpeedWarningSwChanged(boolean enabled) {
        this.mSpdWarningSw.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onSpeedWarningValueChanged(int value) {
        this.mSpdWarningValue.postValue(Integer.valueOf(value));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onTempSwChanged(boolean enabled) {
        this.mMenuTempSw.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onWindPowerSwChanged(boolean enabled) {
        this.mMenuWindPowerSw.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onWindModeSwChanged(boolean enabled) {
        this.mMenuWindModeSw.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onBrightSwChanged(boolean enabled) {
        this.mMenuMediaSw.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController.Callback
    public void onMediaSwChanged(boolean enabled) {
        this.mMenuMediaSw.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.MeterBaseViewModel
    protected void handleXKeyChanged(int keyValue) {
        LogUtils.d(TAG, "handleXKeyChanged: " + keyValue);
        this.mXKeyData.postValue(XKeyForCustomer.fromSwsValue(keyValue));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.MeterBaseViewModel
    protected void handleDoorKeyChanged(int keyValue) {
        LogUtils.d(TAG, "handleDoorKeyChanged: " + keyValue);
        this.mDoorKeyData.postValue(DoorKeyForCustomer.fromSwsValue(keyValue));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.MeterBaseViewModel
    protected void handleLastMaintainChanged(MaintainInfo last) {
        this.mLastMaintainData.postValue(last);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.MeterBaseViewModel
    protected void handleNextMaintainChanged(MaintainInfo next) {
        this.mNextMaintainData.postValue(next);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.meter.MeterBaseViewModel
    protected void handleEcallAvailableChanged(boolean available) {
        this.mEcallAvailableData.postValue(Boolean.valueOf(available));
    }

    public LiveData<String> getTotalMileageData() {
        return this.mMileageTotal;
    }

    public LiveData<String> getMileageDataSinceCharge() {
        return this.mMileageSinceLastCharge;
    }

    public LiveData<String> getMileageDataSinceStart() {
        return this.mMileageSinceStartUp;
    }

    public LiveData<String> getMileageAData() {
        return this.mMileageA;
    }

    public LiveData<String> getMileageBData() {
        return this.mMileageB;
    }

    public LiveData<Boolean> getSpdWarningSwData() {
        return this.mSpdWarningSw;
    }

    public LiveData<Integer> getSpdWarningValueData() {
        return this.mSpdWarningValue;
    }

    public LiveData<Boolean> getMenuTempSwData() {
        return this.mMenuTempSw;
    }

    public LiveData<Boolean> getMenuWindPowerSwData() {
        return this.mMenuWindPowerSw;
    }

    public LiveData<Boolean> getMenuWindModeSwData() {
        return this.mMenuWindModeSw;
    }

    public LiveData<Boolean> getMenuBrightSwData() {
        return this.mMenuBrightSw;
    }

    public LiveData<Boolean> getMenuMediaSwData() {
        return this.mMenuMediaSw;
    }

    public MutableLiveData<XKeyForCustomer> getXKeyData() {
        return this.mXKeyData;
    }

    public MutableLiveData<DoorKeyForCustomer> getDoorKeyData() {
        return this.mDoorKeyData;
    }

    public LiveData<MaintainInfo> getLastMaintenanceData() {
        return this.mLastMaintainData;
    }

    public LiveData<MaintainInfo> getNextMaintenanceData() {
        return this.mNextMaintainData;
    }

    public LiveData<Boolean> getEcallAvailableData() {
        return this.mEcallAvailableData;
    }

    public void setKeyTouchDirection(KeyTouchDirection direction) {
        if (direction != null) {
            setTouchRotationDirection(direction.toIcmCmd());
        }
    }

    public KeyTouchDirection getKeyTouchDirection() {
        try {
            return KeyTouchDirection.fromIcmStatus(getTouchRotationDirection());
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
            return null;
        }
    }

    public void setXKeyForCustomerValue(XKeyForCustomer keyForCustomer) {
        if (keyForCustomer != null) {
            setXKeyForCustomer(keyForCustomer.toSwsCmd());
        }
    }

    public XKeyForCustomer getXKeyForCustomerValue() {
        return XKeyForCustomer.fromSwsValue(getXKeyForCustomer());
    }

    public void setDoorKeyForCustomerValue(DoorKeyForCustomer doorKeyForCustomer) {
        if (doorKeyForCustomer != null) {
            setDoorKeyForCustomer(doorKeyForCustomer.toIcmCmd());
        }
    }

    public DoorKeyForCustomer getDoorKeyForCustomerValue() {
        return DoorKeyForCustomer.fromSwsValue(getDoorKeyForCustomer());
    }
}
