package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.diagnostic.XpDiagnosticManager;
import android.car.hardware.CarPropertyValue;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IDiagnosticController;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class DiagnosticController extends BaseCarController<XpDiagnosticManager, IDiagnosticController.Callback> implements IDiagnosticController {
    private static final String TAG = "DiagnosticController";
    private XpDiagnosticManager.XpDiagnosticEventCallback mDiagnosticEventCallback = new XpDiagnosticManager.XpDiagnosticEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.DiagnosticController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.i(DiagnosticController.TAG, "onChangeEvent: " + carPropertyValue, false);
            DiagnosticController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    public DiagnosticController(Car carClient) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (XpDiagnosticManager) carClient.getCarManager(CarClientWrapper.XP_DIAGNOSTIC_SERVICE);
            if (this.mCarManager != 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mDiagnosticEventCallback);
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
        if (this.mCarConfig.isSupportEpbWarning() && this.mIsMainProcess) {
            propertyIds.add(557851665);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        try {
            if (this.mCarManager != 0) {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mDiagnosticEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        int propertyId = value.getPropertyId();
        if (propertyId == 557851663) {
            handleAbsFault(((Integer) getValue(value)).intValue());
        } else if (propertyId == 557851665) {
            handleIbtFault(((Integer) getValue(value)).intValue());
        } else {
            LogUtils.w(TAG, "handle unknown event: " + value);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IDiagnosticController
    public boolean isAbsFault() {
        int i;
        try {
            i = this.mCarManager.getAbsFailureState();
        } catch (Exception e) {
            LogUtils.e(TAG, "isAbsFault: " + e.getMessage(), false);
            i = -1;
        }
        return i != 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IDiagnosticController
    public boolean isEpbFault() {
        try {
            return this.mCarManager.getApbFailureState() != 0;
        } catch (Exception e) {
            LogUtils.e(TAG, "isEpbFault: " + e.getMessage(), false);
            return true;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IDiagnosticController
    public boolean isIbtFault() {
        int i;
        try {
            try {
                i = getIntProperty(557851665);
            } catch (Exception e) {
                LogUtils.e(TAG, "isIbtFault: " + e.getMessage(), false);
                i = 1;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getIbtFailureState();
        }
        return i != 0;
    }

    private void handleAbsFault(int value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IDiagnosticController.Callback) it.next()).onAbsFaultChanged(value != 0);
            }
        }
    }

    private void handleIbtFault(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IDiagnosticController.Callback) it.next()).onIbtFaultChanged(state != 0);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IDiagnosticController
    public XpDiagnosticManager getManager() {
        return this.mCarManager;
    }
}
