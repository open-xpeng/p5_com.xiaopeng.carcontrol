package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.mcu.CarMcuManager;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class McuController extends BaseCarController<CarMcuManager, IMcuController.Callback> implements IMcuController {
    protected static final String TAG = "McuController";
    private final CarMcuManager.CarMcuEventCallback mCarMcuEventCallback = new CarMcuManager.CarMcuEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.McuController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.i(McuController.TAG, "onChangeEvent: " + carPropertyValue, false);
            McuController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    public McuController(Car carClient) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarMcuManager) carClient.getCarManager(CarClientWrapper.XP_MCU_SERVICE);
            if (this.mCarManager != 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarMcuEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.d(TAG, "Init end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(557847561);
        addExtPropertyIds(arrayList);
        return arrayList;
    }

    protected void addExtPropertyIds(List<Integer> propertyIds) {
        if (this.mCarConfig.isSupportMcuSeatWelcome()) {
            propertyIds.add(557849724);
        }
        if (this.mCarConfig.isSupportCiuConfig()) {
            propertyIds.add(557847613);
        }
        if (this.mCarConfig.isSupportMcuPowerSw()) {
            propertyIds.add(557847634);
        }
        if (this.mCarConfig.isSupportMcuKeyOpenFailed()) {
            propertyIds.add(557847661);
        }
        propertyIds.add(557847680);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarMcuEventCallback);
            } catch (CarNotConnectedException e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 557847561:
                handleIgStatusChanged(((Integer) getValue(value)).intValue());
                return;
            case 557847613:
                handleCiuStateChange(((Integer) getValue(value)).intValue());
                return;
            case 557847634:
                handleAutoPowerOffConfigStatus(((Integer) getValue(value)).intValue());
                return;
            case 557847661:
                handleKeyOpenFailed(((Integer) getValue(value)).intValue());
                return;
            case 557847680:
                handleRemindWarningState(((Integer) getValue(value)).intValue());
                return;
            case 557849724:
                handleSeatWelcomeModeUpdate(((Integer) getValue(value)).intValue());
                return;
            default:
                LogUtils.w(TAG, "handle unknown event: " + value);
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController
    public int getIgStatusFromMcu() {
        try {
            try {
                return getIntProperty(557847561);
            } catch (Exception e) {
                LogUtils.e(TAG, "getIgStatusFromMcu: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getIgStatusFromMcu();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController
    public boolean getSeatWelcomeMode() {
        int chairWelcomeMode;
        try {
            try {
                chairWelcomeMode = getIntProperty(557849724);
            } catch (Exception e) {
                LogUtils.e(TAG, "getSeatWelcomeMode: " + e.getMessage(), false);
                if (this.mDataSync == null) {
                    return true;
                }
                return this.mDataSync.getWelcomeMode();
            }
        } catch (Exception unused) {
            chairWelcomeMode = this.mCarManager.getChairWelcomeMode();
        }
        return chairWelcomeMode == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController
    public void setSeatWelcomeMode(boolean enable) {
        try {
            this.mCarManager.setChairWelcomeMode(enable ? 1 : 0);
            if (this.mIsMainProcess) {
                this.mDataSync.setWelcomeMode(enable);
            } else {
                CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SEAT_WELCOME_SW, enable);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setSeatWelcomeMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController
    public boolean getCiuState() {
        int i;
        if (this.mCarConfig.isSupportCiuConfig()) {
            try {
                try {
                    i = getIntProperty(557847613);
                } catch (Exception unused) {
                    i = this.mCarManager.getCiuState();
                }
            } catch (Exception unused2) {
                i = 2;
            }
            LogUtils.i(TAG, "CIU state=" + i, false);
            return i == 1;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController
    public void setAutoPowerOffSwitch(boolean enable) {
        if (!this.mCarConfig.isSupportMcuPowerSw() || this.mCarManager == 0) {
            return;
        }
        try {
            this.mCarManager.setAutoPowerOffSwitch(enable ? 1 : 0);
        } catch (Throwable th) {
            LogUtils.e(TAG, "setAutoPowerOffSwitch: " + th.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController
    public boolean isAutoPowerOffEnable() {
        if (this.mCarConfig.isSupportMcuPowerSw()) {
            if (this.mCarManager != 0) {
                try {
                    return this.mCarManager.getAutoPowerOffSwitchState() == 1;
                } catch (Throwable th) {
                    LogUtils.e(TAG, "isAutoPowerOffEnable: " + th.getMessage(), false);
                }
            }
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController
    public boolean isFactoryModeActive() {
        if (this.mCarManager != 0) {
            try {
                return this.mCarManager.getFactoryModeSwitchStatus() == 1;
            } catch (Throwable th) {
                LogUtils.e(TAG, "isFactoryModeActive: " + th.getMessage(), false);
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController
    public void setGeoFenceStatus(boolean enter) {
        if (this.mCarConfig.isSupportMrrGeoFence()) {
            try {
                this.mCarManager.setGeofenceStatus(enter ? 1 : 0);
            } catch (Exception e) {
                LogUtils.w(TAG, "setGeofenceStatus failed: " + e.getMessage(), false);
            }
        }
    }

    protected void handleSeatWelcomeModeUpdate(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMcuController.Callback) it.next()).onWelcomeModeChanged(z);
            }
        }
    }

    private void handleCiuStateChange(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMcuController.Callback) it.next()).onCiuStateChanged(z);
            }
        }
    }

    private void handleIgStatusChanged(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMcuController.Callback) it.next()).onIgStatusChanged(status);
            }
        }
    }

    private void handleAutoPowerOffConfigStatus(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IMcuController.Callback callback = (IMcuController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onAutoPowerOffConfig(z);
            }
        }
    }

    private void handleKeyOpenFailed(int value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMcuController.Callback) it.next()).onKeyOpenFailed(value);
            }
        }
    }

    private void handleRemindWarningState(int state) {
        synchronized (this.mCallbacks) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IMcuController.Callback) it.next()).onRemindWarning(state);
            }
        }
    }
}
