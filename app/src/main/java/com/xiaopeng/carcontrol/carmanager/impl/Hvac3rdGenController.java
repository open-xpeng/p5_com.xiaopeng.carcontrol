package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.hardware.CarPropertyValue;
import com.xiaopeng.carcontrol.carmanager.controller.IHvacController;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class Hvac3rdGenController extends HvacController {
    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController
    protected void registerContentObserver() {
    }

    public Hvac3rdGenController(Car carClient) {
        super(carClient);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController
    protected void addFurtherPropertyIds(List<Integer> propertyIds) {
        if (this.mIsMainProcess) {
            propertyIds.add(557849089);
            propertyIds.add(557849137);
            propertyIds.add(557849139);
            propertyIds.add(557849138);
            propertyIds.add(557849148);
            propertyIds.add(557849146);
            propertyIds.add(557849147);
            propertyIds.add(557849143);
            propertyIds.add(557849163);
            propertyIds.add(559946320);
            propertyIds.add(559946321);
            propertyIds.add(559946330);
            propertyIds.add(557849179);
            propertyIds.add(557849170);
            propertyIds.add(557849175);
            propertyIds.add(557849180);
            propertyIds.add(557849181);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 557849089:
                handleHvacQualityPurgeMode(((Integer) getValue(value)).intValue());
                return;
            case 557849137:
                handleAirIntakeAutoUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557849138:
                handleFanAutoUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557849139:
                handleAirDistributionAutoUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557849143:
                handleHvacSfsSwitchChanged(((Integer) getValue(value)).intValue() == 1);
                return;
            case 557849146:
                lambda$setHvacDeodorantEnable$10$HvacController(((Integer) getValue(value)).intValue() == 1);
                return;
            case 557849147:
                handleHvacRapidHeatChanged(((Integer) getValue(value)).intValue() == 1);
                return;
            case 557849148:
                lambda$setHvacRapidCoolingEnable$7$HvacController(((Integer) getValue(value)).intValue() == 1);
                return;
            case 557849163:
                handleHvacRearPowerModeChanged(((Integer) getValue(value)).intValue());
                return;
            case 557849170:
                handleRearAutoModeUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557849175:
                handleRearWindSpeedLevelUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557849179:
                handleThirdRowWindBlowModeUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557849180:
                handleNewFreshSwitchUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557849181:
                handleNIVentUpdate(((Integer) getValue(value)).intValue());
                return;
            case 559946320:
                handleRearTempDriverUpdate(((Float) getValue(value)).floatValue());
                return;
            case 559946321:
                handleRearTempPsnUpdate(((Float) getValue(value)).floatValue());
                return;
            case 559946330:
                handleThirdRowTempDriverUpdate(((Float) getValue(value)).floatValue());
                return;
            default:
                super.handleEventsUpdate(value);
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacAcModeOn() {
        return getAcHeatNatureMode() == 7;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacAcMode(boolean enable) {
        if (enable) {
            setAcHeatNatureMode(4);
        } else {
            setAcHeatNatureMode(3);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacQualityPurgeEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557849089);
            } catch (Exception e) {
                LogUtils.e("HvacController", "isHvacQualityPurgeEnable: " + e.getMessage(), false);
                i = 0;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getHvacQualityPurgeMode();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacQualityPurgeMode(boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        this.mFunctionModel.setHvacPurgeClickTime(System.currentTimeMillis());
        try {
            this.mCarManager.setHvacQualityPurgeMode(parseCdnSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e("HvacController", "setHvacQualityPurgeMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacAirDistributionAutoEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557849139);
            } catch (Exception e) {
                LogUtils.e("HvacController", "isHvacAirDistributionAutoEnable: " + e.getMessage(), false);
                i = 0;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getAirDistributionAutoControlStatus();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacFanSpeedAutoEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557849138);
            } catch (Exception e) {
                LogUtils.e("HvacController", "isHvacFanSpeedAutoEnable: " + e.getMessage(), false);
                i = 0;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getWindSpeedAutoControlStatus();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacAirIntakeAutoEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557849137);
            } catch (Exception e) {
                LogUtils.e("HvacController", "isHvacAirIntakeAutoEnable: " + e.getMessage(), false);
                i = 0;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getAirInTakeAutoControlStatus();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController
    protected void mockExtHvacValue() {
        this.mCarPropertyMap.put(557849089, new CarPropertyValue<>(557849089, 0));
        this.mCarPropertyMap.put(557849094, new CarPropertyValue<>(557849094, 0));
        this.mCarPropertyMap.put(557849093, new CarPropertyValue<>(557849093, 14));
        this.mCarPropertyMap.put(557849139, new CarPropertyValue<>(557849139, 0));
        this.mCarPropertyMap.put(557849138, new CarPropertyValue<>(557849138, 0));
        this.mCarPropertyMap.put(557849137, new CarPropertyValue<>(557849137, 0));
    }

    private void handleAirDistributionAutoUpdate(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacAirDistributionAutoChanged(parseHvacStatus(status));
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacPowerMode(boolean enable) {
        super.setPowerMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacFrontDefrost(boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setHvacFrontDefrostMode(parseCdnSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e("HvacController", "setHvacFrontDefrost: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacRapidCoolingEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557849148);
            } catch (Exception e) {
                LogUtils.e("HvacController", "isHvacRapidCoolingEnable: " + e.getMessage(), false);
                i = 0;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getWarpSpeedCoolingSwitchStatus();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRapidCoolingEnable(boolean enable) {
        if (!EXHIBITION_MODE && this.mIsMainProcess) {
            if (enable) {
                this.mFunctionModel.setHvacRapidCoolingTime(System.currentTimeMillis());
            }
            this.mFunctionModel.setHvacSmartModeClickTime(System.currentTimeMillis());
            try {
                this.mCarManager.setWarpSpeedCoolingSwitch(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e("HvacController", "setHvacRapidCoolingEnable: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacDeodorantEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557849146);
            } catch (Exception e) {
                LogUtils.e("HvacController", "isHvacDeodorantEnable: " + e.getMessage(), false);
                i = 0;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getDeodorizeSwitchStatus();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacDeodorantEnable(boolean enable) {
        if (!EXHIBITION_MODE && this.mIsMainProcess) {
            if (enable) {
                this.mFunctionModel.setHvacDeodorantTime(System.currentTimeMillis());
            }
            this.mFunctionModel.setHvacSmartModeClickTime(System.currentTimeMillis());
            try {
                this.mCarManager.setDeodorizeSwitch(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e("HvacController", "setHvacDeodorantEnable: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacRapidHeatEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557849147);
            } catch (Exception e) {
                LogUtils.e("HvacController", "isHvacRapidHeatEnable: " + e.getMessage(), false);
                i = 0;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getWarpSpeedWarmingSwitchStatus();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRapidHeatEnable(boolean enable) {
        if (!EXHIBITION_MODE && this.mIsMainProcess) {
            if (enable) {
                this.mFunctionModel.setHvacRapidHeatTime(System.currentTimeMillis());
            }
            this.mFunctionModel.setHvacSmartModeClickTime(System.currentTimeMillis());
            try {
                this.mCarManager.setWarpSpeedWarmingSwitch(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e("HvacController", "setHvacRapidHeatEnable: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacRapidHeatCountDownTimer() {
        if (this.mIsMainProcess) {
            return this.mFunctionModel.getHvacRapidHeatCountdown();
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRapidHeatCountDownTimer(final int time) {
        if (this.mIsMainProcess) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$Hvac3rdGenController$6B5IjcwRyJxyAKCWwpvD7mk9-sI
                @Override // java.lang.Runnable
                public final void run() {
                    Hvac3rdGenController.this.lambda$setHvacRapidHeatCountDownTimer$0$Hvac3rdGenController(time);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setHvacRapidHeatCountDownTimer$0$Hvac3rdGenController(final int time) {
        handleHvacRapidHeatCountdownTimer(time);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacWindBlowModeGroup(int mode) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            SharedPreferenceUtil.setHvacBlowModeAuto(false);
            if (mode == getHvacWindBlowMode()) {
                handleWindBlowModeUpdate(mode);
            }
            this.mCarManager.setHvacWindBlowMode(mode);
        } catch (Exception e) {
            LogUtils.e("HvacController", "setHvacWindBlowModeGroup: " + e.getMessage(), false);
        }
    }
}
