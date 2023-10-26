package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.tbox.CarTboxManager;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.ITboxController;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class TboxController extends BaseCarController<CarTboxManager, ITboxController.Callback> implements ITboxController {
    protected static final String TAG = "TboxController";
    private final CarTboxManager.CarTboxEventCallback mCarTboxEventCallback = new CarTboxManager.CarTboxEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.TboxController.1
        public void onErrorEvent(int propertyId, int errorCode) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.d(TboxController.TAG, "onChangeEvent: " + carPropertyValue);
            TboxController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    public TboxController(Car carClient) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarTboxManager) carClient.getCarManager(CarClientWrapper.XP_TBOX_SERVICE);
            if (this.mCarManager != 0 && this.mPropertyIds.size() > 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarTboxEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.d(TAG, "Init end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        addExtPropertyIds(arrayList);
        return arrayList;
    }

    protected void addExtPropertyIds(List<Integer> propertyIds) {
        if (this.mCarConfig.isSupportTboxPowerSw()) {
            propertyIds.add(557846575);
        }
        propertyIds.add(557846618);
        propertyIds.add(557846619);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        try {
            if (this.mCarManager == 0 || this.mPropertyIds.size() <= 0) {
                return;
            }
            this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarTboxEventCallback);
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 557846575:
                handleAutoPowerOffConfigStatus(((Integer) getValue(value)).intValue());
                return;
            case 557846578:
                handleSoldierStatus(((Integer) getValue(value)).intValue());
                return;
            case 557846618:
                handleTboxAcChargeSt(((Integer) getValue(value)).intValue());
                return;
            case 557846619:
                handleIotBusinessTypeSt(((Integer) getValue(value)).intValue());
                return;
            default:
                LogUtils.w(TAG, "handle unknown event: " + value);
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController
    public void setSoldierSw(int status) {
        try {
            if (this.mIsMainProcess) {
                this.mDataSync.setSoldierSwStatus(status);
            }
            this.mCarManager.setSoldierSw(status);
        } catch (Exception e) {
            LogUtils.e(TAG, "setSoldierSw: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController
    public void setAutoPowerOffConfig(boolean enable) {
        if (this.mCarConfig.isSupportTboxPowerSw() && this.mCarManager != 0) {
            int i = enable ? 1 : 0;
            try {
                this.mCarManager.setAutoPowerOffConfig(i);
                LogUtils.w(TAG, "setAutoPowerOffConfig over enable : " + enable + " , status : " + i);
            } catch (Throwable th) {
                LogUtils.e(TAG, "setAutoPowerOffConfig: " + th.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController
    public int getSoldierSwStatus() {
        try {
            return this.mCarManager.getSoldierSwState();
        } catch (Exception e) {
            LogUtils.e(TAG, "getSoldierSwStatus: " + e.getMessage(), false);
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController
    public void setNetWorkType(int type) {
        try {
            this.mCarManager.setNetWorkType(type);
            LogUtils.d(TAG, "setNetWorkType: " + type, false);
        } catch (Exception e) {
            LogUtils.e(TAG, "setNetWorkType: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController
    public boolean getAutoPowerOffConfig() {
        if (this.mCarConfig.isSupportTboxPowerSw()) {
            if (this.mCarManager != 0) {
                try {
                    return this.mCarManager.getAutoPowerOffSt() == 1;
                } catch (Throwable th) {
                    LogUtils.e(TAG, "getAutoPowerOffConfig: " + th.getMessage(), false);
                }
            }
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController
    public int getTboxAcChargerSt() {
        int i;
        try {
            i = this.mCarManager.getACChargeUnlockST();
        } catch (Error | Exception e) {
            LogUtils.w(TAG, "getACChargeUnlockST failed: " + e.getMessage(), false);
            i = 0;
        }
        LogUtils.i(TAG, "getAcChargerSt: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController
    public int getIotBusinessType() {
        int i;
        try {
            i = this.mCarManager.getIOTBusinessType();
        } catch (Error | Exception e) {
            LogUtils.w(TAG, "getIotBusinessType failed: " + e.getMessage(), false);
            i = 0;
        }
        LogUtils.i(TAG, "getIotBusinessType: " + i, false);
        return i;
    }

    private void handleSoldierStatus(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ITboxController.Callback) it.next()).onSoldierStateChanged(status);
            }
        }
    }

    private void handleAutoPowerOffConfigStatus(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ITboxController.Callback callback = (ITboxController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onAutoPowerOffConfigStatusChanged(z);
            }
        }
    }

    private void handleTboxAcChargeSt(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ITboxController.Callback) it.next()).onAcChargeUnlockStChanged(status);
            }
        }
    }

    private void handleIotBusinessTypeSt(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ITboxController.Callback) it.next()).onAcChargeUnlockStChanged(status);
            }
        }
    }
}
