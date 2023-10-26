package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.avm.CarAvmManager;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IAvmController;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class AvmController extends BaseCarController<CarAvmManager, IAvmController.Callback> implements IAvmController {
    protected final String TAG = "AvmController";
    private final CarAvmManager.CarAvmEventCallback mCarAvmEventCallback = new CarAvmManager.CarAvmEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.AvmController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.i("AvmController", "onChangeEvent: " + carPropertyValue, false);
            AvmController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    public AvmController(Car carClient) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d("AvmController", "Init start");
        try {
            this.mCarManager = (CarAvmManager) carClient.getCarManager(CarClientWrapper.XP_AVM_SERVICE);
            if (this.mCarManager != 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarAvmEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e("AvmController", (String) null, (Throwable) e, false);
        }
        LogUtils.d("AvmController", "Init end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        addExtPropertyIds(arrayList);
        return arrayList;
    }

    protected void addExtPropertyIds(List<Integer> propertyIds) {
        if (this.mCarConfig.isSupportSdc() && this.mIsMainProcess) {
            propertyIds.add(557855760);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarAvmEventCallback);
            } catch (CarNotConnectedException e) {
                LogUtils.e("AvmController", (String) null, (Throwable) e, false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        if (value.getPropertyId() == 557855760) {
            handleAvmWorkState(((Integer) getValue(value)).intValue());
        } else {
            LogUtils.w("AvmController", "handle unknown event: " + value);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvmController
    public int getAVMWorkSt() {
        if (this.mCarConfig.isSupportSdc()) {
            try {
                return this.mCarManager.getAvmWorkState();
            } catch (Exception e) {
                LogUtils.e("AvmController", "getAVMWorkSt: " + e.getMessage(), false);
                return 0;
            }
        }
        return -1;
    }

    private void handleAvmWorkState(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAvmController.Callback) it.next()).onAvmWorkStateChanged(status);
            }
        }
    }
}
