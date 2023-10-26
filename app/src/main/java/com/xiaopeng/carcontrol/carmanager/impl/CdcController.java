package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.cdc.CarCdcManager;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.ICdcController;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class CdcController extends BaseCarController<CarCdcManager, ICdcController.Callback> implements ICdcController {
    protected static final String TAG = "CdcController";
    private final CarCdcManager.CarCdcEventCallback mCarCdcEventCallback = new CarCdcManager.CarCdcEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.CdcController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.i(CdcController.TAG, "onChangeEvent: " + carPropertyValue, false);
            CdcController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    public CdcController(Car carClient) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarCdcManager) carClient.getCarManager(CarClientWrapper.XP_CDC_SERVICE);
            if (this.mCarManager != 0 && this.mPropertyIds.size() > 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarCdcEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.d(TAG, "Init end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        if (this.mCarConfig.isSupportCdcControl()) {
            arrayList.add(557854724);
        }
        return arrayList;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarCdcEventCallback);
            } catch (CarNotConnectedException e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        if (value.getPropertyId() == 557854724) {
            checkCdcUpdate(((Integer) getValue(value)).intValue());
        } else {
            LogUtils.w(TAG, "handle unknown event: " + value);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICdcController
    public void setCdcMode(int cdc, boolean needSave) {
        try {
            this.mCarManager.setCdcFunctionStyle(cdc);
            if (this.mIsMainProcess && needSave) {
                this.mDataSync.setCdcMode(cdc);
            }
        } catch (Exception e) {
            LogUtils.w(TAG, "setCdcFunctionStyle failed: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICdcController
    public int getCdcMode() {
        try {
            return getIntProperty(557854724);
        } catch (Exception e) {
            try {
                return this.mCarManager.getCdcFunctionStyle();
            } catch (Exception unused) {
                LogUtils.w(TAG, "getCdcFunctionStyle failed: " + e.getMessage(), false);
                return 1;
            }
        }
    }

    private void checkCdcUpdate(int cdc) {
        if (cdc == 1 || cdc == 2 || cdc == 3) {
            handleCdcUpdate(cdc);
        } else {
            LogUtils.w(TAG, "Invalid CDC value: " + cdc, false);
        }
    }

    private void handleCdcUpdate(int cdc) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ICdcController.Callback) it.next()).onCdcModeChanged(cdc);
            }
        }
    }
}
