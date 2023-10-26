package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarEcuManager;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.icm.CarIcmManager;
import android.car.hardware.input.CarInputManager;
import android.provider.Settings;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.BusinessConstant;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IIcmController;
import com.xiaopeng.carcontrol.carmanager.impl.oldarch.IcmOldController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.xuimanager.smart.SmartManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class IcmController extends BaseCarController<CarIcmManager, IIcmController.Callback> implements IIcmController {
    protected static final String TAG = "IcmController";
    private final CarIcmManager.CarIcmEventCallback mCarIcmEventCallback = new CarIcmManager.CarIcmEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.IcmController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            switch (carPropertyValue.getPropertyId()) {
                case 559945262:
                case 559945263:
                case 559945264:
                case 559945265:
                case 559945266:
                    break;
                default:
                    LogUtils.i(IcmController.TAG, "onChangeEvent: " + carPropertyValue, false);
                    break;
            }
            IcmController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };
    private final CarInputManager.CarInputEventCallback mCarInputEventCallback = new CarInputManager.CarInputEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.IcmController.2
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.d(IcmController.TAG, "onChangeEvent: " + carPropertyValue);
            IcmController.this.handleCarEventsUpdate(carPropertyValue);
        }

        public void onErrorEvent(int propertyId, int zone) {
            LogUtils.e(IcmController.TAG, "onErrorEvent: " + propertyId, false);
        }
    };
    private CarInputManager mInputManager;
    private List<Integer> mInputPropertyIds;
    private SmartManager mXuiSmartManager;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public boolean dependOnXuiManager() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getBrightSw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getMediaSw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public int getSpeedLimitValueFromDataSync() {
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getSpeedWarningSw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public int getSpeedWarningValue() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getTempSw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getWindModeSw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getWindPowerSw() {
        return false;
    }

    protected void mockExtIcmProperty() {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setBrightSw(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setIcmDrvTemp(float temp) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setIcmWindBlowMode(int mode) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setIcmWindLevel(int level) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setMediaSw(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setMeterMultiProperties(HashMap<Integer, Object> propertyMap) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setSpeedWarningSw(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setSpeedWarningValue(int speed) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setTempSw(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setWindModeSw(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setWindPowerSw(boolean enable) {
    }

    public IcmController(Car carClient) {
    }

    public static boolean getDefaultMenuMediaSourceValue() {
        return CarBaseConfig.getInstance().isSupportSwitchMedia();
    }

    /* loaded from: classes.dex */
    public static class IcmControllerFactory {
        public static IcmController createCarController(Car carClient) {
            if (CarBaseConfig.getInstance().isNewIcmArch()) {
                return new IcmController(carClient);
            }
            return new IcmOldController(carClient);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarIcmManager) carClient.getCarManager(CarClientWrapper.XP_ICM_SERVICE);
            if (this.mCarManager != 0) {
                if (this.mPropertyIds.size() > 0) {
                    this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarIcmEventCallback);
                } else {
                    LogUtils.w(TAG, "No prop ids to register");
                }
            }
            if (this.mIsMainProcess && !FunctionModel.getInstance().isNewSteerKey()) {
                this.mInputManager = (CarInputManager) carClient.getCarManager("xp_input");
                ArrayList arrayList = new ArrayList();
                this.mInputPropertyIds = arrayList;
                arrayList.add(557916716);
                this.mInputManager.registerPropCallback(this.mInputPropertyIds, this.mCarInputEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.d(TAG, "Init end");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
        LogUtils.d(TAG, "Init xui manager start");
        this.mXuiSmartManager = XuiClientWrapper.getInstance().getSmartManager();
        LogUtils.d(TAG, "Init xui manager end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        addExtPropertyIds(arrayList);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void addExtPropertyIds(List<Integer> propertyIds) {
        if ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess && !BaseFeatureOption.getInstance().isSignalRegisterDynamically()) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess)) {
            propertyIds.add(559945262);
            propertyIds.add(559945264);
            propertyIds.add(559945263);
            propertyIds.add(559945265);
            propertyIds.add(559945266);
        }
        if (this.mCarConfig.isNewIcmArch() && this.mIsMainProcess) {
            propertyIds.add(557848078);
            propertyIds.add(554702353);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarIcmEventCallback);
            } catch (CarNotConnectedException e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
        CarInputManager carInputManager = this.mInputManager;
        if (carInputManager != null) {
            try {
                carInputManager.unregisterPropCallback(this.mInputPropertyIds, this.mCarInputEventCallback);
            } catch (CarNotConnectedException e2) {
                LogUtils.e(TAG, (String) null, (Throwable) e2, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void handleEventsUpdate(CarPropertyValue<?> value) {
        int propertyId = value.getPropertyId();
        if (propertyId == 554702353) {
            parseSyncSignal((String) value.getValue());
        } else if (propertyId == 557848078) {
            if (((Integer) getValue(value)).intValue() == 1) {
                sendTouchRotationSpeedToIcm();
            }
        } else if (propertyId != 557916716) {
            switch (propertyId) {
                case 559945262:
                    handleMileageTotalUpdate(((Float) getValue(value)).floatValue());
                    return;
                case 559945263:
                    handleMileageStartUpUpdate(((Float) getValue(value)).floatValue());
                    return;
                case 559945264:
                    handleMileageLastChargeUpdate(((Float) getValue(value)).floatValue());
                    return;
                case 559945265:
                    handleMileageAUpdate(((Float) getValue(value)).floatValue());
                    return;
                case 559945266:
                    handleMileageBUpdate(((Float) getValue(value)).floatValue());
                    return;
                default:
                    LogUtils.w(TAG, "handle unknown event: " + value);
                    return;
            }
        } else {
            handleSteerKeyUpdate((Object[]) getValue(value));
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void resetMeterMileageA() {
        try {
            this.mCarManager.resetMeterMileageA();
        } catch (Exception e) {
            LogUtils.e(TAG, "resetMeterMileageA: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void resetMeterMileageB() {
        try {
            this.mCarManager.resetMeterMileageB();
        } catch (Exception e) {
            LogUtils.e(TAG, "resetMeterMileageB: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public float getMileageTotal() {
        float f;
        try {
            f = this.mCarManager.getDriveTotalMileage();
        } catch (Exception unused) {
            f = 0.0f;
        }
        LogUtils.i(TAG, "getMileageTotal " + f, false);
        return f;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public float getMileageSinceLastCharge() {
        float f;
        try {
            f = this.mCarManager.getLastChargeMileage();
        } catch (Exception unused) {
            f = 0.0f;
        }
        LogUtils.i(TAG, "getMileageSinceLastCharge " + f, false);
        return f;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public float getMileageSinceStartUp() {
        float f;
        try {
            f = this.mCarManager.getLastStartUpMileage();
        } catch (Exception unused) {
            f = 0.0f;
        }
        LogUtils.i(TAG, "getMileageSinceStartUp " + f, false);
        return f;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public float getMileageA() {
        float f;
        try {
            f = this.mCarManager.getMeterMileageA();
        } catch (Exception unused) {
            f = 0.0f;
        }
        LogUtils.i(TAG, "getMileageA " + f, false);
        return f;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public float getMileageB() {
        float f;
        try {
            f = this.mCarManager.getMeterMileageB();
        } catch (Exception unused) {
            f = 0.0f;
        }
        LogUtils.i(TAG, "getMileageB " + f, false);
        return f;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setXKeyForCustomer(int keyFunc) {
        if (keyFunc == 0 && !CarBaseConfig.getInstance().isSupportXkeyDisable()) {
            LogUtils.i(TAG, "setXKeyForCustomer failed with 0", false);
            return;
        }
        if (this.mIsMainProcess) {
            this.mDataSync.setWheelXKey(keyFunc);
        }
        SmartManager smartManager = this.mXuiSmartManager;
        if (smartManager != null) {
            try {
                smartManager.setXKeyForCustomer(keyFunc);
            } catch (Exception e) {
                LogUtils.e(TAG, "setXKeyForCustomer: " + e.getMessage(), false);
            }
        }
        handleXKeyChanged(keyFunc);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public int getXKeyForCustomer() {
        int wheelXKey = this.mIsMainProcess ? this.mDataSync.getWheelXKey() : 0;
        SmartManager smartManager = this.mXuiSmartManager;
        if (smartManager != null) {
            try {
                return smartManager.getXKeyForCustomer();
            } catch (Exception e) {
                LogUtils.e(TAG, "getXKeyForCustomer: " + e.getMessage(), false);
                return wheelXKey;
            }
        }
        return wheelXKey;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setDoorKeyForCustomer(int keyFunc) {
        if (this.mIsMainProcess) {
            this.mDataSync.setDoorBossKey(keyFunc);
        }
        SmartManager smartManager = this.mXuiSmartManager;
        if (smartManager != null) {
            try {
                smartManager.setBossKeyForCustomer(keyFunc);
            } catch (Exception e) {
                LogUtils.e(TAG, "setDoorKeyForCustomer: " + e.getMessage(), false);
            }
        }
        handleDoorKeyChanged(keyFunc);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public int getDoorKeyForCustomer() {
        int doorBossKey = this.mIsMainProcess ? this.mDataSync.getDoorBossKey() : 0;
        SmartManager smartManager = this.mXuiSmartManager;
        if (smartManager != null) {
            try {
                return smartManager.getBossKeyForCustomer();
            } catch (Exception e) {
                LogUtils.e(TAG, "getDoorKeyForCustomer: " + e.getMessage(), false);
                return doorBossKey;
            }
        }
        return doorBossKey;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setDoorBossKeySw(boolean enable) {
        if (this.mIsMainProcess) {
            this.mDataSync.setDoorBossKeySw(enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getDoorBossKeySw() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getDoorBossKeySw();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setTouchRotationDirection(int direction) {
        SmartManager smartManager = this.mXuiSmartManager;
        if (smartManager != null) {
            try {
                smartManager.setTouchRotationDirection(direction);
            } catch (Exception e) {
                LogUtils.e(TAG, "setTouchRotationDirection: " + e.getMessage(), false);
            }
        }
        if (this.mIsMainProcess) {
            this.mDataSync.setWheelTouchDirection(direction);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public int getTouchRotationDirection() {
        try {
            SmartManager smartManager = this.mXuiSmartManager;
            if (smartManager != null) {
                return smartManager.getTouchRotationDirection();
            }
            return 1;
        } catch (Exception e) {
            LogUtils.e(TAG, "getTouchRotationDirection: " + e.getMessage(), false);
            return 1;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setTouchRotationSpeed(int speed, boolean needSave) {
        try {
            if (this.mCarManager != 0) {
                CarIcmManager carIcmManager = this.mCarManager;
                carIcmManager.getClass();
                CarIcmManager.SyncSinal syncSinal = new CarIcmManager.SyncSinal(carIcmManager);
                syncSinal.SyncMode = "WheelKeyMode";
                syncSinal.SyncProgress = speed;
                try {
                    this.mCarManager.setIcmSyncSignal(new Gson().toJson(syncSinal));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Settings.System.putInt(App.getInstance().getContentResolver(), IIcmController.KEY_TOUCH_ROTATION_SPEED, speed);
                if (needSave && this.mIsMainProcess) {
                    this.mDataSync.setWheelTouchSpeed(speed);
                }
            }
        } catch (Exception e2) {
            LogUtils.e(TAG, "setTouchRotationSpeed: " + e2.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public int getTouchRotationSpeed() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), IIcmController.KEY_TOUCH_ROTATION_SPEED, 1);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public int getBrakeFluidLevelWarningMessage() {
        try {
            return this.mCarManager.getBrakeFluidLevelWarningMessage();
        } catch (Exception e) {
            LogUtils.e(TAG, "getBrakeFluidLevelWarningMessage: " + e.getMessage(), false);
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setIcmDayNightMode(int mode) {
        CarIcmManager carIcmManager = this.mCarManager;
        carIcmManager.getClass();
        CarIcmManager.SyncSinal syncSinal = new CarIcmManager.SyncSinal(carIcmManager);
        syncSinal.SyncMode = "DayNight";
        syncSinal.SyncProgress = mode;
        syncSinal.msgId = "";
        String json = new Gson().toJson(syncSinal);
        try {
            this.mCarManager.setIcmSyncSignal(json);
            LogUtils.i(TAG, "setIcmDayNightMode: " + json, false);
        } catch (Exception e) {
            LogUtils.e(TAG, "setIcmDayNightMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public int getIcmBrightness() {
        int i;
        try {
            i = this.mCarManager.getIcmBrightness();
        } catch (Exception unused) {
            i = 0;
        }
        LogUtils.i(TAG, "getIcmBrightness: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setIcmBrightness(int value) {
        try {
            this.mCarManager.setIcmBrightness(value);
            Settings.System.putInt(App.getInstance().getContentResolver(), IIcmController.KEY_ICM_BRIGHTNESS, value);
            LogUtils.i(TAG, "setIcmBrightness: " + value, false);
        } catch (Exception e) {
            LogUtils.e(TAG, "setIcmBrightness: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setIcmMeditationMode(boolean enter) {
        LogUtils.i(TAG, "setIcmMeditationMode: " + enter, false);
        Settings.Secure.putInt(App.getInstance().getContentResolver(), IIcmController.KEY_SYSTEM_MEDITATION_MODE, enter ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setWheelKeyProtectSw(boolean enable) {
        LogUtils.d(TAG, "setWheelKeyProtectSw: " + (enable ? 1 : 0), false);
        Settings.System.putInt(App.getInstance().getContentResolver(), IIcmController.KEY_WHEEL_KEY_PROTECT, enable ? 1 : 0);
        if (this.mIsMainProcess) {
            this.mDataSync.setWheelKeyProtect(enable);
        } else {
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.WHEEL_KEY_PROTECT, enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean isWheelKeyProtectEnabled() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), IIcmController.KEY_WHEEL_KEY_PROTECT, 1) == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setWiperSensitivity(int value) {
        if (this.mCarConfig.isWiperAdjustByHardKey()) {
            try {
                this.mCarManager.setWiperRainDetectSensitivity(value);
            } catch (Exception e) {
                LogUtils.e(TAG, "setWiperRainDetectSensitivity: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setUserScenarioInfo(int[] info) {
        try {
            this.mCarManager.setUserScenarioInfo(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockIcmProperty() {
        ConcurrentHashMap<Integer, CarPropertyValue<?>> concurrentHashMap = this.mCarPropertyMap;
        Float valueOf = Float.valueOf(0.0f);
        concurrentHashMap.put(559945262, new CarPropertyValue<>(559945262, valueOf));
        this.mCarPropertyMap.put(559945264, new CarPropertyValue<>(559945264, valueOf));
        this.mCarPropertyMap.put(559945263, new CarPropertyValue<>(559945263, valueOf));
        this.mCarPropertyMap.put(559945265, new CarPropertyValue<>(559945265, valueOf));
        this.mCarPropertyMap.put(559945266, new CarPropertyValue<>(559945266, valueOf));
        mockExtIcmProperty();
    }

    private void handleMileageTotalUpdate(float value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onMileageTotalChanged(value);
            }
        }
    }

    private void handleMileageLastChargeUpdate(float value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onMileageLastChargeChanged(value);
            }
        }
    }

    private void handleMileageStartUpUpdate(float value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onMileageStartUpChanged(value);
            }
        }
    }

    private void handleMileageAUpdate(float value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onMileageAChanged(value);
            }
        }
    }

    private void handleMileageBUpdate(float value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onMileageBChanged(value);
            }
        }
    }

    private void handleSteerKeyUpdate(Object[] values) {
        LogUtils.d(TAG, "handleSteerKeyUpdate:" + Arrays.toString(values));
        if (values != null) {
            FunctionModel.getInstance().setNewSteerKey();
        }
    }

    private void handleXKeyChanged(int keyValue) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onXKeyChanged(keyValue);
            }
        }
    }

    private void handleDoorKeyChanged(int keyValue) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onDoorKeyChanged(keyValue);
            }
        }
    }

    private void sendTouchRotationSpeedToIcm() {
        int touchRotationSpeed = getTouchRotationSpeed();
        LogUtils.i(TAG, "sendTouchRotationSpeedToIcm, speed = " + touchRotationSpeed, false);
        setTouchRotationSpeed(touchRotationSpeed, false);
    }

    private void parseSyncSignal(String json) {
        LogUtils.i(TAG, "parseSyncSignal, json = " + json, false);
        if (TextUtils.isEmpty(json) || !json.contains("SysReady")) {
            return;
        }
        sendTouchRotationSpeedToIcm();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected CarEcuManager.CarEcuEventCallback getCarEventCallback() {
        return this.mCarIcmEventCallback;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void buildPropIdList(List<Integer> container, String key) {
        key.hashCode();
        if (key.equals(BusinessConstant.KEY_MILEAGE_DATA)) {
            container.addAll(buildMileageIdList());
        }
    }

    private Collection<Integer> buildMileageIdList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(559945262);
        arrayList.add(559945263);
        arrayList.add(559945264);
        arrayList.add(559945265);
        arrayList.add(559945266);
        return arrayList;
    }
}
