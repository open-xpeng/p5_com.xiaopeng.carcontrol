package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.dcdc.CarDcdcManager;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IDcdcController;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class DcdcController extends BaseCarController<CarDcdcManager, IDcdcController.Callback> implements IDcdcController {
    protected static final String TAG = "IDcdcController";

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    public DcdcController(Car carClient) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarDcdcManager) carClient.getCarManager(CarClientWrapper.XP_DCDC_SERVICE);
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.d(TAG, "Init end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        return new ArrayList();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IDcdcController
    public int getDcdcInputVoltage() {
        try {
            return this.mCarManager.getDcdcInputVoltage();
        } catch (Exception e) {
            LogUtils.w(TAG, "getDcdcInputVoltage failed: " + e.getMessage(), false);
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IDcdcController
    public float getDcdcInputCurrent() {
        try {
            return this.mCarManager.getDcdcInputCurrent();
        } catch (Exception e) {
            LogUtils.w(TAG, "getDcdcInputVoltage failed: " + e.getMessage(), false);
            return 0.0f;
        }
    }
}
