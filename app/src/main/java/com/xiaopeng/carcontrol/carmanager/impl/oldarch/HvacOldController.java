package com.xiaopeng.carcontrol.carmanager.impl.oldarch;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.input.CarInputManager;
import android.car.hardware.mcu.CarMcuManager;
import android.os.SystemClock;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IHvacController;
import com.xiaopeng.carcontrol.carmanager.impl.HvacController;
import com.xiaopeng.carcontrol.config.DxCarConfig;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class HvacOldController extends HvacController {
    public static final long PART_CONTROL_DELAY_TIME = 2500;
    private final CarInputManager.CarInputEventCallback mCarInputEventCallback;
    private CarMcuManager mMcuManager;
    private long mPrePartControlTime;

    private static int parseAcStatus(int status) {
        return status == 1 ? 1 : 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getAirAutoProtectMode() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacAqsLevel() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacAqsMode() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacCirculationTime() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavDrvLeftHPos() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavDrvLeftVPos() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavDrvRightHPos() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavDrvRightVPos() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavDrvWindMode() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavPsnLeftHPos() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavPsnLeftVPos() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavPsnRightHPos() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavPsnRightVPos() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavPsnWindMode() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavSweepMode() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacIonizerMode() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacWindModEconLour() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getWindMaxLevel() {
        return 7;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacSelfDryOn() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacSingleModeActive() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacSingleModeEnable() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isSmartHvacEnabled() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController
    protected void registerContentObserver() {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setAcHeatNatureMode(int mode) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setAirAutoProtectSound(String soundType) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setAirAutoProtectedMode(int mode) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacAqsLevel(int level) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacAqsMode(int status) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacCirculationTime(int time) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavDrvLeftHPos(int pos) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavDrvLeftVPos(int pos) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavDrvRightHPos(int pos) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavDrvRightVPos(int pos) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavDrvWindMode(int mode) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavPsnLeftHPos(int pos) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavPsnLeftVPos(int pos) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavPsnRightHPos(int pos) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavPsnRightVPos(int pos) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavPsnWindMode(int mode) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavSweepMode(int status) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacPsnSyncMode(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacSelfDryEnable(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacSingleMode(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacSingleModeActive(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacWindBlowMode(int mode) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setSmartHvacEnable(boolean enable) {
    }

    public HvacOldController(Car carClient) {
        super(carClient);
        this.mCarInputEventCallback = new CarInputManager.CarInputEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.HvacOldController.1
            public void onErrorEvent(int propertyId, int zone) {
            }

            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                LogUtils.i("HvacController", "onChangeEvent: " + carPropertyValue, false);
                HvacOldController.this.handleCarEventsUpdate(carPropertyValue);
            }
        };
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void initCarManager(Car carClient) {
        super.initCarManager(carClient);
        try {
            if (this.mIsMainProcess) {
                this.mMcuManager = (CarMcuManager) carClient.getCarManager(CarClientWrapper.XP_MCU_SERVICE);
                ArrayList arrayList = new ArrayList();
                arrayList.add(557916703);
                arrayList.add(557916705);
                arrayList.add(557851163);
                ((CarInputManager) carClient.getCarManager("xp_input")).registerPropCallback(arrayList, this.mCarInputEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e("HvacController", (String) null, (Throwable) e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController
    protected void addFurtherPropertyIds(List<Integer> propertyIds) {
        if (this.mIsMainProcess) {
            propertyIds.add(557849089);
            propertyIds.add(557849094);
            propertyIds.add(557849141);
            propertyIds.add(557849142);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController
    protected void mockExtHvacValue() {
        this.mCarPropertyMap.put(557849089, new CarPropertyValue<>(557849089, 0));
        this.mCarPropertyMap.put(557849094, new CarPropertyValue<>(557849094, 0));
        this.mCarPropertyMap.put(557849093, new CarPropertyValue<>(557849093, 14));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 358614275:
                handleTempDriverUpdate(((Float) getValue(value)).floatValue());
                if (DxCarConfig.getInstance().isSupportHvacDualTemp()) {
                    return;
                }
                this.mCarPropertyMap.put(559946242, value);
                handleTempPsnUpdate(((Float) getValue(value)).floatValue());
                return;
            case 557849089:
                handleHvacQualityPurgeMode(((Integer) getValue(value)).intValue());
                return;
            case 557849093:
                return;
            case 557849094:
                handleHvacAirQualityOutside(((Integer) getValue(value)).intValue());
                return;
            case 557849127:
                handleAcModeUpdate(parseAcStatus(((Integer) getValue(value)).intValue()));
                return;
            case 557849141:
                handleFanAutoUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557849142:
                handleAirIntakeAutoUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557851163:
                handleWindModeRequest(((Integer) getValue(value)).intValue());
                return;
            case 557916703:
                handleWindRequest(getIntArrayProperty(value));
                return;
            case 557916705:
                handleTempRequest(getIntArrayProperty(value));
                return;
            default:
                super.handleEventsUpdate(value);
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacAcModeOn() {
        int i;
        try {
            try {
                i = getIntProperty(557849127);
            } catch (Exception unused) {
                i = 0;
            }
        } catch (Exception unused2) {
            i = this.mCarManager.getHvacTempAcMode();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacAcMode(boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setHvacTempAcMode(enable ? 1 : 0);
        } catch (Exception e) {
            LogUtils.e("HvacController", (String) null, e);
        }
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
            LogUtils.e("HvacController", (String) null, e);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacWindSpeedLevel(int level) {
        if (EXHIBITION_MODE) {
            return;
        }
        super.setHvacWindSpeedLevel(level);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacFanSpeedAutoEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557849138);
            } catch (Exception unused) {
                i = 0;
            }
        } catch (Exception unused2) {
            i = this.mCarManager.getHvacBlowerCtrlType();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacCirculationMode(int mode) {
        if (EXHIBITION_MODE) {
            return;
        }
        super.setHvacCirculationMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacFrontDefrostOn() {
        int i;
        try {
            try {
                i = getIntProperty(557849126);
            } catch (Exception unused) {
                i = 0;
            }
        } catch (Exception unused2) {
            i = this.mCarManager.getHVACFrontDefrostMode();
        }
        return parseHvacStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRapidCoolingEnable(final boolean enable) {
        LogUtils.i("HvacController", "setHvacRapidCoolingEnable:" + enable + ",current:" + this.mFunctionModel.isHvacRapidCoolingEnable(), false);
        if (!EXHIBITION_MODE && this.mIsMainProcess) {
            if (enable) {
                this.mFunctionModel.setHvacRapidCoolingTime(System.currentTimeMillis());
            }
            this.mFunctionModel.setHvacSmartModeClickTime(System.currentTimeMillis());
            if (enable && !isHvacDeodorantEnable()) {
                FunctionModel.getInstance().setHvacSmartQualityPurge(isHvacQualityPurgeEnable());
            }
            if (enable && isHvacQualityPurgeEnable()) {
                setHvacQualityPurgeMode(false);
                ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$HvacOldController$bCE4UwWApM5QDX5ydXAJB7i-16E
                    @Override // java.lang.Runnable
                    public final void run() {
                        HvacOldController.this.lambda$setHvacRapidCoolingEnable$0$HvacOldController();
                    }
                }, 300L);
            } else if (this.mFunctionModel.isHvacRapidCoolingEnable() != enable) {
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$HvacOldController$ujTYxjCOcaiCaE8wHZ8YE6PCHHE
                    @Override // java.lang.Runnable
                    public final void run() {
                        HvacOldController.this.lambda$setHvacRapidCoolingEnable$1$HvacOldController(enable);
                    }
                });
            }
        }
    }

    public /* synthetic */ void lambda$setHvacRapidCoolingEnable$0$HvacOldController() {
        lambda$setHvacRapidCoolingEnable$7$HvacController(true);
    }

    public /* synthetic */ void lambda$setHvacRapidCoolingEnable$1$HvacOldController(final boolean enable) {
        lambda$setHvacRapidCoolingEnable$7$HvacController(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacDeodorantEnable(final boolean enable) {
        LogUtils.i("HvacController", "setHvacDeodorantEnable:" + enable + ",current:" + this.mFunctionModel.isHvacDeodorantEnable(), false);
        if (!EXHIBITION_MODE && this.mIsMainProcess) {
            if (enable) {
                this.mFunctionModel.setHvacDeodorantTime(System.currentTimeMillis());
            }
            this.mFunctionModel.setHvacSmartModeClickTime(System.currentTimeMillis());
            if (enable && !isHvacRapidCoolingEnable()) {
                FunctionModel.getInstance().setHvacSmartQualityPurge(isHvacQualityPurgeEnable());
            }
            if (enable && isHvacQualityPurgeEnable()) {
                setHvacQualityPurgeMode(false);
                ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$HvacOldController$o0hTpC_T_VTdqTQz78sj6new-Lg
                    @Override // java.lang.Runnable
                    public final void run() {
                        HvacOldController.this.lambda$setHvacDeodorantEnable$2$HvacOldController();
                    }
                }, 300L);
            } else if (this.mFunctionModel.isHvacDeodorantEnable() != enable) {
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$HvacOldController$SxJFpoojCWXISrZTFgz30-x-aJo
                    @Override // java.lang.Runnable
                    public final void run() {
                        HvacOldController.this.lambda$setHvacDeodorantEnable$3$HvacOldController(enable);
                    }
                });
            }
        }
    }

    public /* synthetic */ void lambda$setHvacDeodorantEnable$2$HvacOldController() {
        lambda$setHvacDeodorantEnable$10$HvacController(true);
    }

    public /* synthetic */ void lambda$setHvacDeodorantEnable$3$HvacOldController(final boolean enable) {
        lambda$setHvacDeodorantEnable$10$HvacController(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacAirIntakeAutoEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557849142);
            } catch (Exception unused) {
                i = 0;
            }
        } catch (Exception unused2) {
            i = this.mCarManager.getHvacAirCirculationType();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEconMode(int status) {
        if (EXHIBITION_MODE) {
            return;
        }
        if (isHvacPowerModeOn()) {
            super.setHvacEconMode(status);
            return;
        }
        setHvacPowerMode(true);
        if (getHvacEconMode() == 0) {
            super.setHvacEconMode(1);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacTempDriverStep(boolean isUp) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            setHvacTempDriver(mockNewTemp(isUp, getHvacTempDriver()));
        } catch (Exception e) {
            LogUtils.e("HvacController", (String) null, (Throwable) e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacTempPsnStep(boolean isUp) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            float mockNewTemp = mockNewTemp(isUp, getHvacTempPsn());
            if (!DxCarConfig.getInstance().isSupportHvacDualTemp()) {
                setHvacTempDriver(mockNewTemp);
            } else {
                setHvacTempPsn(mockNewTemp);
            }
        } catch (Exception e) {
            LogUtils.e("HvacController", (String) null, (Throwable) e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacWindSpeedStep(boolean isUp) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            setHvacWindSpeedLevel(mockNewSpeed(isUp, getHvacWindSpeedLevel()));
        } catch (Exception e) {
            LogUtils.e("HvacController", (String) null, (Throwable) e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacQualityPurgeEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557849089);
            } catch (Exception unused) {
                i = 0;
            }
        } catch (Exception unused2) {
            i = this.mCarManager.getHvacQualityPurgeMode();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController, com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacQualityPurgeMode(final boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mFunctionModel.setHvacPurgeClickTime(System.currentTimeMillis());
            if (isHvacRapidCoolingEnable()) {
                this.mFunctionModel.setHvacSmartQualityPurge(false);
                setHvacRapidCoolingEnable(false);
                ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$HvacOldController$9oFJKR_jF-_-VfNIDwoB9SW0L4M
                    @Override // java.lang.Runnable
                    public final void run() {
                        HvacOldController.this.lambda$setHvacQualityPurgeMode$4$HvacOldController(enable);
                    }
                }, 200L);
            } else if (isHvacDeodorantEnable()) {
                this.mFunctionModel.setHvacSmartQualityPurge(false);
                setHvacDeodorantEnable(false);
                ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$HvacOldController$vqcykhCalZSf0sdQ08GsHLVnY8Q
                    @Override // java.lang.Runnable
                    public final void run() {
                        HvacOldController.this.lambda$setHvacQualityPurgeMode$5$HvacOldController(enable);
                    }
                }, 200L);
            } else {
                this.mCarManager.setHvacQualityPurgeMode(parseCdnSwitchCmd(enable));
            }
        } catch (Exception e) {
            LogUtils.e("HvacController", (String) null, e);
        }
    }

    public /* synthetic */ void lambda$setHvacQualityPurgeMode$4$HvacOldController(final boolean enable) {
        try {
            this.mCarManager.setHvacQualityPurgeMode(parseCdnSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e("HvacController", (String) null, e);
        }
    }

    public /* synthetic */ void lambda$setHvacQualityPurgeMode$5$HvacOldController(final boolean enable) {
        try {
            this.mCarManager.setHvacQualityPurgeMode(parseCdnSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e("HvacController", (String) null, e);
        }
    }

    private void handleHvacAirQualityOutside(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IHvacController.Callback callback = (IHvacController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onHvacAirQualityOutside(z);
            }
        }
    }

    private void handleWindRequest(int[] array) {
        int i;
        LogUtils.i("HvacController", "ID_WIND_SPD_ADJ_REQUEST:" + Arrays.toString(array), false);
        if (!isIgOn() || array == null || array.length <= 1) {
            return;
        }
        int i2 = array[0];
        int i3 = array[1];
        try {
            int hvacWindSpeedLevel = getHvacWindSpeedLevel();
            if (i2 == 0) {
                i = hvacWindSpeedLevel + i3;
                if (i > getWindMaxLevel()) {
                    i = getWindMaxLevel();
                }
            } else {
                i = hvacWindSpeedLevel > i3 ? hvacWindSpeedLevel - i3 : 0;
            }
            LogUtils.i("HvacController", "try to setHvacWindSpeedLevel to :" + i, false);
            if (i < 1) {
                partControlWindToClosePower();
                return;
            }
            if (i > getWindMaxLevel()) {
                i = getWindMaxLevel();
            }
            setHvacWindSpeedLevel(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void partControlWindToClosePower() {
        if (!isHvacPowerModeOn()) {
            LogUtils.i("HvacController", "partControlWindToClosePower isHvacPowerModeOn is close.", false);
            return;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        LogUtils.i("HvacController", "partControlWindToClosePower curTime:" + elapsedRealtime + ",preTime:" + this.mPrePartControlTime + ",delay:" + (elapsedRealtime - this.mPrePartControlTime), false);
        if (elapsedRealtime - this.mPrePartControlTime < PART_CONTROL_DELAY_TIME) {
            this.mPrePartControlTime = 0L;
            setPowerMode(false);
            return;
        }
        this.mPrePartControlTime = elapsedRealtime;
    }

    private void handleTempRequest(int[] array) {
        float f;
        LogUtils.i("HvacController", "ID_TEMP_ADJ_REQUEST:" + Arrays.toString(array), false);
        if (!isIgOn() || array == null || array.length <= 1) {
            return;
        }
        int i = array[0];
        int i2 = array[1];
        try {
            float hvacTempDriver = getHvacTempDriver();
            if (i == 0) {
                f = hvacTempDriver + i2;
                if (f > 32.0f) {
                    f = 32.0f;
                }
            } else {
                f = hvacTempDriver - i2;
                if (f <= 18.0f) {
                    f = 18.0f;
                }
            }
            LogUtils.i("HvacController", "try to setHvacWindSpeedLevel to :" + f, false);
            if (f < 18.0f || f > 32.0f) {
                return;
            }
            setHvacTempDriver(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleWindModeRequest(int value) {
        LogUtils.d("HvacController", "ID_WIND_EXIT_MODE_REQUEST:" + value);
        try {
            if (!isIgOn() || value < 1 || value > 4) {
                return;
            }
            setHvacWindBlowModeGroup(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isIgOn() {
        int i;
        try {
            try {
                i = getIntProperty(557847561);
            } catch (Exception unused) {
                i = this.mMcuManager.getIgStatusFromMcu();
            }
        } catch (Exception unused2) {
            i = 0;
        }
        LogUtils.i("HvacController", "ig status " + i, false);
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.HvacController
    protected void handleAutoModeUpdate(int status) {
        if (parseHvacStatus(status)) {
            SharedPreferenceUtil.setHvacBlowModeAuto(true);
        }
        super.handleAutoModeUpdate(status);
    }
}
