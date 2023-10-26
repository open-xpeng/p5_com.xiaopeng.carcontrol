package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.eps.CarEpsManager;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IEpsController;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class EpsController extends BaseCarController<CarEpsManager, IEpsController.Callback> implements IEpsController {
    protected static final String TAG = "EpsController";
    private final CarEpsManager.CarEpsEventCallback mCarEpsEventCallback = new CarEpsManager.CarEpsEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.EpsController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            EpsController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    public EpsController(Car carClient) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarEpsManager) carClient.getCarManager(CarClientWrapper.XP_EPS_SERVICE);
            if (this.mCarManager != 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarEpsEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.d(TAG, "Init end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(557851653);
        return arrayList;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarEpsEventCallback);
            } catch (CarNotConnectedException e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        int propertyId = value.getPropertyId();
        if (propertyId == 557851653) {
            LogUtils.i(TAG, "onChangeEvent: " + value, false);
            checkSteeringWheelEpsUpdate(((Integer) getValue(value)).intValue());
        } else if (propertyId == 559948806) {
            handleSteerAngleUpdate(((Float) getValue(value)).floatValue());
        } else {
            LogUtils.w(TAG, "handle unknown event: " + value);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEpsController
    public void setSteeringEps(int eps, boolean storeEnable) {
        if (storeEnable) {
            try {
                if (this.mIsMainProcess) {
                    this.mDataSync.setSteerMode(eps);
                } else {
                    CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.STEERING_EPS, eps);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setSteeringEps: " + e.getMessage(), false);
                return;
            }
        }
        this.mCarManager.setSteeringWheelEPS(eps);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEpsController
    public void setSteeringEps(int eps) {
        setSteeringEps(eps, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEpsController
    public int getSteeringEps() {
        try {
            try {
                return getIntProperty(557851653);
            } catch (Exception e) {
                LogUtils.e(TAG, "getSteeringEps: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getSteeringWheelEPS();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEpsController
    public int getSteeringEpsSp() {
        return this.mDataSync.getSteerMode();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEpsController
    public float getSteeringAngle() {
        try {
            try {
                return getFloatProperty(559948806);
            } catch (Exception e) {
                LogUtils.e(TAG, "getSteeringAngle: " + e.getMessage(), false);
                return 0.0f;
            }
        } catch (Exception unused) {
            return this.mCarManager.getSteeringAngle();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEpsController
    public float getTorsionBarTorque() {
        float torsionBarTorque;
        if (this.mCarConfig.isSupportEpsTorque()) {
            try {
                torsionBarTorque = this.mCarManager.getTorsionBarTorque();
            } catch (Exception e) {
                LogUtils.e(TAG, "getTorsionBarTorque: " + e.getMessage(), false);
            }
            LogUtils.i(TAG, "getTorsionBarTorque, torque=" + torsionBarTorque, false);
            return torsionBarTorque;
        }
        torsionBarTorque = 0.0f;
        LogUtils.i(TAG, "getTorsionBarTorque, torque=" + torsionBarTorque, false);
        return torsionBarTorque;
    }

    private void mockEpsProperty() {
        this.mCarPropertyMap.put(557851653, new CarPropertyValue<>(557851653, 0));
        this.mCarPropertyMap.put(559948806, new CarPropertyValue<>(559948806, Float.valueOf(0.0f)));
    }

    private void checkSteeringWheelEpsUpdate(int eps) {
        if (eps == 0 || eps == 1 || eps == 2) {
            handleSteeringWheelEpsUpdate(eps);
        } else {
            LogUtils.w(TAG, "Invalid steering eps value: " + eps, false);
        }
    }

    protected void handleSteeringWheelEpsUpdate(int eps) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IEpsController.Callback) it.next()).onSteeringEpsChanged(eps);
            }
        }
    }

    private void handleSteerAngleUpdate(float angle) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IEpsController.Callback) it.next()).onSteeringAngleChanged(angle);
            }
        }
    }
}
