package com.xiaopeng.carcontrol.carmanager.impl.oldarch;

import android.car.Car;
import android.car.hardware.CarPropertyValue;
import com.xiaopeng.carcontrol.carmanager.controller.IIcmController;
import com.xiaopeng.carcontrol.carmanager.impl.IcmController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.model.D2DataSyncModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class IcmOldController extends IcmController {
    private static int convertToIcmCmd(boolean enable) {
        return enable ? 1 : 0;
    }

    private static boolean parseIcmStatus(int status) {
        return status == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getDoorBossKeySw() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public int getIcmBrightness() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setDoorBossKeySw(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setIcmBrightness(int value) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setIcmDayNightMode(int mode) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setIcmMeditationMode(boolean enter) {
    }

    public IcmOldController(Car carClient) {
        super(carClient);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController
    public void addExtPropertyIds(List<Integer> propertyIds) {
        super.addExtPropertyIds(propertyIds);
        propertyIds.add(Integer.valueOf((int) IIcmController.ID_ICM_SPEED_LIMIT_WARNING_SWITCH));
        propertyIds.add(Integer.valueOf((int) IIcmController.ID_ICM_SPEED_LIMIT_WARNING_VALUE));
        propertyIds.add(Integer.valueOf((int) IIcmController.ID_ICM_TEMPERATURE));
        propertyIds.add(Integer.valueOf((int) IIcmController.ID_ICM_WIND_POWER));
        propertyIds.add(Integer.valueOf((int) IIcmController.ID_ICM_WIND_MODE));
        propertyIds.add(Integer.valueOf((int) IIcmController.ID_ICM_SCREEN_LIGHT));
        if (CarBaseConfig.getInstance().isSupportSwitchMedia()) {
            propertyIds.add(Integer.valueOf((int) IIcmController.ID_ICM_MEDIA_SOURCE));
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController
    protected void mockExtIcmProperty() {
        this.mCarPropertyMap.put(Integer.valueOf((int) IIcmController.ID_ICM_SPEED_LIMIT_WARNING_SWITCH), new CarPropertyValue<>((int) IIcmController.ID_ICM_SPEED_LIMIT_WARNING_SWITCH, 0));
        this.mCarPropertyMap.put(Integer.valueOf((int) IIcmController.ID_ICM_SPEED_LIMIT_WARNING_VALUE), new CarPropertyValue<>((int) IIcmController.ID_ICM_SPEED_LIMIT_WARNING_VALUE, 80));
        this.mCarPropertyMap.put(Integer.valueOf((int) IIcmController.ID_ICM_TEMPERATURE), new CarPropertyValue<>((int) IIcmController.ID_ICM_TEMPERATURE, 1));
        this.mCarPropertyMap.put(Integer.valueOf((int) IIcmController.ID_ICM_WIND_POWER), new CarPropertyValue<>((int) IIcmController.ID_ICM_WIND_POWER, 1));
        this.mCarPropertyMap.put(Integer.valueOf((int) IIcmController.ID_ICM_WIND_MODE), new CarPropertyValue<>((int) IIcmController.ID_ICM_WIND_MODE, 1));
        this.mCarPropertyMap.put(Integer.valueOf((int) IIcmController.ID_ICM_SCREEN_LIGHT), new CarPropertyValue<>((int) IIcmController.ID_ICM_SCREEN_LIGHT, 1));
        this.mCarPropertyMap.put(Integer.valueOf((int) IIcmController.ID_ICM_MEDIA_SOURCE), new CarPropertyValue<>((int) IIcmController.ID_ICM_MEDIA_SOURCE, 1));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case IIcmController.ID_ICM_WIND_MODE /* 557848097 */:
                handleMenuWindModeSwUpdate(((Integer) getValue(value)).intValue());
                return;
            case IIcmController.ID_ICM_SPEED_LIMIT_WARNING_SWITCH /* 557848098 */:
                handleSpdWarningSwUpdate(((Integer) getValue(value)).intValue());
                return;
            case IIcmController.ID_ICM_TEMPERATURE /* 557848101 */:
                handleMenuTempSwUpdate(((Integer) getValue(value)).intValue());
                return;
            case IIcmController.ID_ICM_WIND_POWER /* 557848103 */:
                handleMenuWindPowerSwUpdate(((Integer) getValue(value)).intValue());
                return;
            case IIcmController.ID_ICM_SPEED_LIMIT_WARNING_VALUE /* 557848109 */:
                handleSpdWarningValueUpdate(((Integer) getValue(value)).intValue());
                return;
            case IIcmController.ID_ICM_MEDIA_SOURCE /* 557848118 */:
                handleMenuMediaSwUpdate(((Integer) getValue(value)).intValue());
                return;
            case IIcmController.ID_ICM_SCREEN_LIGHT /* 557848119 */:
                handleMenuBrightSwUpdate(((Integer) getValue(value)).intValue());
                return;
            default:
                super.handleEventsUpdate(value);
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setSpeedWarningSw(boolean enable) {
        try {
            if (this.mIsMainProcess) {
                ((D2DataSyncModel) this.mDataSync).setSpeedLimit(enable);
            }
            this.mCarManager.setSpeedLimitWarningSwitch(convertToIcmCmd(enable));
        } catch (Exception e) {
            LogUtils.e("IcmController", "setSpeedWarningSw: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getSpeedWarningSw() {
        int speedLimitWarningSwitch;
        try {
            try {
                speedLimitWarningSwitch = getIntProperty(IIcmController.ID_ICM_SPEED_LIMIT_WARNING_SWITCH);
            } catch (Exception e) {
                LogUtils.e("IcmController", "getSpeedWarningSw: " + e.getMessage(), false);
                return false;
            }
        } catch (Exception unused) {
            speedLimitWarningSwitch = this.mCarManager.getSpeedLimitWarningSwitch();
        }
        return parseIcmStatus(speedLimitWarningSwitch);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setSpeedWarningValue(int speed) {
        try {
            if (this.mIsMainProcess) {
                ((D2DataSyncModel) this.mDataSync).setSpeedLimitValue(speed);
            }
            this.mCarManager.setSpeedLimitWarningValue(speed);
        } catch (Exception e) {
            LogUtils.e("IcmController", "setSpeedWarningValue: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public int getSpeedWarningValue() {
        try {
            try {
                return getIntProperty(IIcmController.ID_ICM_SPEED_LIMIT_WARNING_VALUE);
            } catch (Exception e) {
                LogUtils.e("IcmController", "getSpeedWarningValue: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getSpeedLimitWarningValue();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public int getSpeedLimitValueFromDataSync() {
        return ((D2DataSyncModel) this.mDataSync).getSpeedLimitValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setTempSw(boolean enable) {
        try {
            if (this.mIsMainProcess) {
                ((D2DataSyncModel) this.mDataSync).setMeterDefineTemperature(enable);
            }
            this.mCarManager.setIcmTemperature(convertToIcmCmd(enable));
        } catch (Exception e) {
            LogUtils.e("IcmController", "setTempSw: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getTempSw() {
        int icmTemperature;
        try {
            try {
                icmTemperature = getIntProperty(IIcmController.ID_ICM_TEMPERATURE);
            } catch (Exception e) {
                LogUtils.e("IcmController", "getTempSw: " + e.getMessage(), false);
                return true;
            }
        } catch (Exception unused) {
            icmTemperature = this.mCarManager.getIcmTemperature();
        }
        return parseIcmStatus(icmTemperature);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setWindPowerSw(boolean enable) {
        try {
            if (this.mIsMainProcess) {
                ((D2DataSyncModel) this.mDataSync).setMeterDefineWindPower(enable);
            }
            this.mCarManager.setIcmWindPower(convertToIcmCmd(enable));
        } catch (Exception e) {
            LogUtils.e("IcmController", "setWindPowerSw: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getWindPowerSw() {
        int icmWindPower;
        try {
            try {
                icmWindPower = getIntProperty(IIcmController.ID_ICM_WIND_POWER);
            } catch (Exception e) {
                LogUtils.e("IcmController", "getWindPowerSw: " + e.getMessage(), false);
                return true;
            }
        } catch (Exception unused) {
            icmWindPower = this.mCarManager.getIcmWindPower();
        }
        return parseIcmStatus(icmWindPower);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setWindModeSw(boolean enable) {
        try {
            if (this.mIsMainProcess) {
                ((D2DataSyncModel) this.mDataSync).setMeterDefineWindMode(enable);
            }
            this.mCarManager.setIcmWindMode(convertToIcmCmd(enable));
        } catch (Exception e) {
            LogUtils.e("IcmController", "setWindModeSw: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getWindModeSw() {
        int icmWindMode;
        try {
            try {
                icmWindMode = getIntProperty(IIcmController.ID_ICM_WIND_MODE);
            } catch (Exception e) {
                LogUtils.e("IcmController", "getWindModeSw: " + e.getMessage(), false);
                return true;
            }
        } catch (Exception unused) {
            icmWindMode = this.mCarManager.getIcmWindMode();
        }
        return parseIcmStatus(icmWindMode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setBrightSw(boolean enable) {
        try {
            if (this.mIsMainProcess) {
                ((D2DataSyncModel) this.mDataSync).setMeterDefineScreenLight(enable);
            }
            this.mCarManager.setIcmScreenLight(convertToIcmCmd(enable));
        } catch (Exception e) {
            LogUtils.e("IcmController", "setBrightSw: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getBrightSw() {
        int icmScreenLight;
        try {
            try {
                icmScreenLight = getIntProperty(IIcmController.ID_ICM_SCREEN_LIGHT);
            } catch (Exception e) {
                LogUtils.e("IcmController", "getBrightSw: " + e.getMessage(), false);
                return true;
            }
        } catch (Exception unused) {
            icmScreenLight = this.mCarManager.getIcmScreenLight();
        }
        return parseIcmStatus(icmScreenLight);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setMediaSw(boolean enable) {
        try {
            if (this.mIsMainProcess) {
                ((D2DataSyncModel) this.mDataSync).setMeterDefineMediaSource(enable);
            }
            this.mCarManager.setIcmMediaSource(convertToIcmCmd(enable));
        } catch (Exception e) {
            LogUtils.e("IcmController", "setMediaSw: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public boolean getMediaSw() {
        int icmMediaSource;
        try {
            try {
                icmMediaSource = getIntProperty(IIcmController.ID_ICM_MEDIA_SOURCE);
            } catch (Exception e) {
                LogUtils.e("IcmController", "getMediaSw: " + e.getMessage(), false);
                return IcmController.getDefaultMenuMediaSourceValue();
            }
        } catch (Exception unused) {
            icmMediaSource = this.mCarManager.getIcmMediaSource();
        }
        return parseIcmStatus(icmMediaSource);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setMeterMultiProperties(HashMap<Integer, Object> propertyMap) {
        CarPropertyValue carPropertyValue;
        if (propertyMap == null || propertyMap.size() <= 0) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        for (Map.Entry<Integer, Object> entry : propertyMap.entrySet()) {
            int intValue = entry.getKey().intValue();
            Object value = entry.getValue();
            switch (intValue) {
                case IIcmController.ID_ICM_WIND_MODE /* 557848097 */:
                case IIcmController.ID_ICM_SPEED_LIMIT_WARNING_SWITCH /* 557848098 */:
                case IIcmController.ID_ICM_TEMPERATURE /* 557848101 */:
                case IIcmController.ID_ICM_WIND_POWER /* 557848103 */:
                case IIcmController.ID_ICM_MEDIA_SOURCE /* 557848118 */:
                case IIcmController.ID_ICM_SCREEN_LIGHT /* 557848119 */:
                    carPropertyValue = new CarPropertyValue(intValue, Integer.valueOf(convertToIcmCmd(((Boolean) value).booleanValue())));
                    break;
                default:
                    carPropertyValue = new CarPropertyValue(intValue, (Integer) value);
                    break;
            }
            linkedList.add(carPropertyValue);
        }
        try {
            this.mCarManager.setMultipleProperties(linkedList);
        } catch (Exception e) {
            LogUtils.e("IcmController", "setMeterMultiProperties: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setIcmWindLevel(int level) {
        try {
            this.mCarManager.setIcmWindLevel(level);
        } catch (Exception e) {
            LogUtils.e("IcmController", "setIcmWindLevel: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setIcmDrvTemp(float temp) {
        try {
            this.mCarManager.setIcmDriverTempValue(temp);
        } catch (Exception e) {
            LogUtils.e("IcmController", "setIcmDrvTemp: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.IcmController, com.xiaopeng.carcontrol.carmanager.controller.IIcmController
    public void setIcmWindBlowMode(int mode) {
        try {
            this.mCarManager.setIcmWindBlowMode(mode);
        } catch (Exception e) {
            LogUtils.e("IcmController", "setIcmWindBlowMode: " + e.getMessage(), false);
        }
    }

    private void handleSpdWarningSwUpdate(int status) {
        boolean parseIcmStatus = parseIcmStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onSpeedWarningSwChanged(parseIcmStatus);
            }
        }
    }

    private void handleSpdWarningValueUpdate(int speed) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onSpeedWarningValueChanged(speed);
            }
        }
    }

    private void handleMenuTempSwUpdate(int status) {
        boolean parseIcmStatus = parseIcmStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onTempSwChanged(parseIcmStatus);
            }
        }
    }

    private void handleMenuWindPowerSwUpdate(int status) {
        boolean parseIcmStatus = parseIcmStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onWindPowerSwChanged(parseIcmStatus);
            }
        }
    }

    private void handleMenuWindModeSwUpdate(int status) {
        boolean parseIcmStatus = parseIcmStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onWindModeSwChanged(parseIcmStatus);
            }
        }
    }

    private void handleMenuBrightSwUpdate(int status) {
        boolean parseIcmStatus = parseIcmStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onBrightSwChanged(parseIcmStatus);
            }
        }
    }

    private void handleMenuMediaSwUpdate(int status) {
        boolean parseIcmStatus = parseIcmStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IIcmController.Callback) it.next()).onMediaSwChanged(parseIcmStatus);
            }
        }
    }
}
