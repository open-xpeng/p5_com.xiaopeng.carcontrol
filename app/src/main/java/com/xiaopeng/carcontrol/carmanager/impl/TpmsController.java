package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarEcuManager;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.tpms.CarTpmsManager;
import com.xiaopeng.carcontrol.BusinessConstant;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.ITpmsController;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class TpmsController extends BaseCarController<CarTpmsManager, ITpmsController.Callback> implements ITpmsController {
    private static final String TAG = "TpmsController";
    private final CarTpmsManager.CarTpmsEventCallback mCarTpmsEventCallback = new CarTpmsManager.CarTpmsEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.TpmsController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            switch (carPropertyValue.getPropertyId()) {
                case 559947266:
                case 559947267:
                case 559947268:
                case 559947269:
                    break;
                default:
                    LogUtils.i(TpmsController.TAG, "onChangeEvent: " + carPropertyValue, false);
                    break;
            }
            LogUtils.d(TpmsController.TAG, "onChangeEvent: " + carPropertyValue);
            TpmsController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    public TpmsController(Car carClient) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarTpmsManager) carClient.getCarManager(CarClientWrapper.XP_TPMS_SERVICE);
            if (this.mCarManager != 0 && this.mPropertyIds.size() > 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarTpmsEventCallback);
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
        if ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess && !BaseFeatureOption.getInstance().isSignalRegisterDynamically()) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess)) {
            propertyIds.add(559947266);
            propertyIds.add(559947267);
            propertyIds.add(559947268);
            propertyIds.add(559947269);
            if (BaseFeatureOption.getInstance().isSupportTpmsWarning()) {
                propertyIds.add(557915660);
            }
            propertyIds.add(557850113);
        }
        if (BaseFeatureOption.getInstance().isSupportTpmsWarning()) {
            if ((!this.mCarConfig.isSupportUnity3D() || this.mIsMainProcess) && (this.mCarConfig.isSupportUnity3D() || !this.mIsMainProcess)) {
                return;
            }
            propertyIds.add(557915662);
            propertyIds.add(557915661);
            propertyIds.add(557915663);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        try {
            if (this.mCarManager == 0 || this.mPropertyIds.size() <= 0) {
                return;
            }
            this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarTpmsEventCallback);
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 557850113:
                handleTpmsCalibrationStateUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557850118:
                handleTpmsSystemFault(((Integer) getValue(value)).intValue());
                return;
            case 557850119:
                handleTpmsAbnormalWarning(((Integer) getValue(value)).intValue());
                return;
            case 557915660:
                handleTpmsWarningStateChanged(getIntArrayProperty(value));
                return;
            case 557915662:
                handleTpmsTempWarning(getIntArrayProperty(value));
                return;
            case 557915663:
                handleTpmsTempChanged(getIntArrayProperty(value));
                return;
            case 559947266:
            case 559947267:
            case 559947268:
            case 559947269:
                handleTpmsPressureUpdate(value.getPropertyId(), ((Float) getValue(value)).floatValue());
                return;
            default:
                LogUtils.w(TAG, "handle unknown event: " + value);
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController
    public float getTyrePressure(int position) {
        int convertPosToPressureId = convertPosToPressureId(position);
        float f = 0.0f;
        if (convertPosToPressureId != 0) {
            try {
                try {
                    f = getFloatProperty(convertPosToPressureId);
                } catch (Exception e) {
                    LogUtils.e(TAG, "position: " + position + ", getTyrePressure: " + e.getMessage(), false);
                }
            } catch (Exception unused) {
                f = this.mCarManager.getTirePressureValue(position);
            }
        }
        LogUtils.d(TAG, "getTyrePressure position: " + position + ", pressure: " + f);
        return f;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController
    public void calibrateTyrePressure() {
        try {
            this.mCarManager.calibrateTirePressure();
        } catch (Exception e) {
            LogUtils.e(TAG, "calibrateTyrePressure: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController
    public int getTpmsCalibrateState() {
        try {
            try {
                return getIntProperty(557850113);
            } catch (Exception e) {
                LogUtils.e(TAG, "getTpmsCalibrateState: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getTirePressureStatus();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController
    public boolean[] isTpmsTempWarning() {
        int[] iArr;
        try {
            try {
                iArr = getIntArrayProperty(557915662);
            } catch (Exception e) {
                LogUtils.e(TAG, "isTpmsTempWarning: " + e.getMessage(), false);
                iArr = new int[]{1, 1, 1, 1};
            }
        } catch (Exception unused) {
            iArr = this.mCarManager.getAllTireTemperatureWarnings();
        }
        return convertTpmsTempWarningState(iArr);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController
    public boolean[] isTpmsSensorWarning() {
        int[] iArr;
        try {
            try {
                iArr = getIntArrayProperty(557915661);
            } catch (Exception e) {
                LogUtils.e(TAG, "isTpmsSensorWarning: " + e.getMessage(), false);
                iArr = new int[]{1, 1, 1, 1};
            }
        } catch (Exception unused) {
            iArr = this.mCarManager.getAllTirePerssureSensorStatus();
        }
        boolean[] zArr = new boolean[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            zArr[i] = iArr[i] != 0;
        }
        return zArr;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController
    public int[] getTpmsTemperature() {
        try {
            try {
                return getIntArrayProperty(557915663);
            } catch (Exception e) {
                LogUtils.e(TAG, "getTpmsTemperature: " + e.getMessage(), false);
                return new int[]{0, 0, 0, 0};
            }
        } catch (Exception unused) {
            return this.mCarManager.getAllTireTemperature();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ITpmsController
    public int[] getTpmsWarningState() {
        try {
            try {
                return getIntArrayProperty(557915660);
            } catch (Exception e) {
                LogUtils.e(TAG, "getTpmsWarningState: " + e.getMessage(), false);
                return new int[]{7, 7, 7, 7};
            }
        } catch (Exception unused) {
            return this.mCarManager.getAllTirePressureWarnings();
        }
    }

    private void mockTpmsProperty() {
        this.mCarPropertyMap.put(557850113, new CarPropertyValue<>(557850113, 0));
        this.mCarPropertyMap.put(557850118, new CarPropertyValue<>(557850118, 0));
        this.mCarPropertyMap.put(557850119, new CarPropertyValue<>(557850119, 0));
        this.mCarPropertyMap.put(557915662, new CarPropertyValue<>(557915662, new Integer[]{0, 0, 0, 0}));
        this.mCarPropertyMap.put(557915661, new CarPropertyValue<>(557915662, new Integer[]{0, 0, 0, 0}));
        this.mCarPropertyMap.put(557915663, new CarPropertyValue<>(557915663, new Integer[]{0, 0, 0, 0}));
        this.mCarPropertyMap.put(557915660, new CarPropertyValue<>(557915660, new Integer[]{0, 0, 0, 0}));
    }

    private int convertPressureIdToPos(int id) {
        switch (id) {
            case 559947266:
                return 1;
            case 559947267:
                return 2;
            case 559947268:
                return 3;
            case 559947269:
                return 4;
            default:
                LogUtils.e(TAG, "convertPressureIdToPos Unknown id: " + id);
                return 0;
        }
    }

    private int convertPosToPressureId(int position) {
        if (position != 1) {
            if (position != 2) {
                if (position != 3) {
                    if (position != 4) {
                        LogUtils.e(TAG, "convertPosToPressureId Unknown position: " + position);
                        return 0;
                    }
                    return 559947269;
                }
                return 559947268;
            }
            return 559947267;
        }
        return 559947266;
    }

    private void handleTpmsPressureUpdate(int id, float pressure) {
        synchronized (this.mCallbackLock) {
            int convertPressureIdToPos = convertPressureIdToPos(id);
            if (convertPressureIdToPos != 0) {
                Iterator it = this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((ITpmsController.Callback) it.next()).onTpmsPressureChanged(convertPressureIdToPos, pressure);
                }
            }
        }
    }

    private void handleTpmsCalibrationStateUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ITpmsController.Callback) it.next()).onTpmsCalibrationStateChanged(state);
            }
        }
    }

    private void handleTpmsSystemFault(int state) {
        boolean z = state != 0;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ITpmsController.Callback) it.next()).onTpmsSystemFaultStateChanged(z);
            }
        }
    }

    private void handleTpmsAbnormalWarning(int state) {
        boolean z = state != 0;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ITpmsController.Callback) it.next()).onTpmsAbnormalWarningStateChanged(z);
            }
        }
    }

    private void handleTpmsTempWarning(int[] states) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ITpmsController.Callback) it.next()).onTpmsTempWarningStateChanged(convertTpmsTempWarningState(states));
            }
        }
    }

    private void handleTpmsTempChanged(int[] temp) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ITpmsController.Callback) it.next()).onTpmsTempChanged(temp);
            }
        }
    }

    private void handleTpmsWarningStateChanged(int[] states) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ITpmsController.Callback) it.next()).onTpmsWarningStateChanged(states);
            }
        }
    }

    private static boolean[] convertTpmsTempWarningState(int[] states) {
        boolean[] zArr = new boolean[states.length];
        for (int i = 0; i < states.length; i++) {
            zArr[i] = states[i] != 0;
        }
        return zArr;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected CarEcuManager.CarEcuEventCallback getCarEventCallback() {
        return this.mCarTpmsEventCallback;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void buildPropIdList(List<Integer> container, String key) {
        key.hashCode();
        if (key.equals(BusinessConstant.KEY_TIRE_CONDITION)) {
            container.addAll(buildTireIdList());
        }
    }

    private List<Integer> buildTireIdList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(559947267);
        arrayList.add(559947266);
        arrayList.add(559947269);
        arrayList.add(559947268);
        if (BaseFeatureOption.getInstance().isSupportTpmsWarning()) {
            arrayList.add(557915660);
        }
        arrayList.add(557850113);
        return arrayList;
    }
}
