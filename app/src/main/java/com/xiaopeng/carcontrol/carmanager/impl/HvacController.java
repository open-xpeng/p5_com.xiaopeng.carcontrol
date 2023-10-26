package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.hvac.CarHvacManager;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IHvacController;
import com.xiaopeng.carcontrol.carmanager.impl.oldarch.HvacOldController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.model.DebugFuncModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;
import com.xiaopeng.xuimanager.contextinfo.ContextInfoManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class HvacController extends BaseCarController<CarHvacManager, IHvacController.Callback> implements IHvacController {
    protected static final String TAG = "HvacController";
    private ContextInfoManager mCtiManager;
    private final CarHvacManager.CarHvacEventCallback mCarHvacEventCallback = new CarHvacManager.CarHvacEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.HvacController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            if (carPropertyValue.getPropertyId() != 557849092) {
                LogUtils.i(HvacController.TAG, "onChangeEvent: " + carPropertyValue, false);
            }
            HvacController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };
    private final Runnable closeHvacRun = new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$HvacController$gRvClC7NO_MogR64igtn0EOo4hY
        @Override // java.lang.Runnable
        public final void run() {
            HvacController.this.lambda$new$1$HvacController();
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    public static int parseCdnSwitchCmd(boolean enable) {
        return enable ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static boolean parseHvacStatus(int status) {
        return status == 1;
    }

    protected void addFurtherPropertyIds(List<Integer> propertyIds) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public boolean dependOnXuiManager() {
        return true;
    }

    public int getHvacRapidHeatCountDownTimer() {
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getWindMaxLevel() {
        return 10;
    }

    public boolean isHvacRapidHeatEnable() {
        return false;
    }

    protected void registerContentObserver() {
    }

    public void setHvacRapidHeatCountDownTimer(int time) {
    }

    public void setHvacRapidHeatEnable(boolean enable) {
    }

    public void setHvacWindBlowModeGroup(int mode) {
    }

    public HvacController(Car carClient) {
    }

    /* loaded from: classes.dex */
    public static class HvacControllerFactory {
        public static HvacController createCarController(Car carClient) {
            int hvacVersion = CarBaseConfig.getInstance().getHvacVersion();
            if (hvacVersion != 1) {
                if (hvacVersion == 2) {
                    return new HvacController(carClient);
                }
                return new Hvac3rdGenController(carClient);
            }
            return new HvacOldController(carClient);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.i(TAG, "Init start");
        try {
            this.mCarManager = (CarHvacManager) carClient.getCarManager("hvac");
            if (this.mCarManager != 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarHvacEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.i(TAG, "Init end");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
        LogUtils.i(TAG, "Init xui manager start");
        ContextInfoManager ctiManager = XuiClientWrapper.getInstance().getCtiManager();
        this.mCtiManager = ctiManager;
        if (ctiManager != null) {
            try {
                this.mCtiManager.registerListener(new ContextInfoManager.ContextWeatherInfoEventListener() { // from class: com.xiaopeng.carcontrol.carmanager.impl.HvacController.2
                    public void onWeatherInfo(String weatherInfo) {
                        LogUtils.i(HvacController.TAG, "weather:" + weatherInfo);
                        int i = -1;
                        try {
                            if (!TextUtils.isEmpty(weatherInfo)) {
                                JSONObject jSONObject = new JSONObject(weatherInfo);
                                if (jSONObject.has(IHvacController.HVAC_OUTSIDE_PM25_KEY)) {
                                    i = jSONObject.getInt(IHvacController.HVAC_OUTSIDE_PM25_KEY);
                                }
                            }
                        } catch (Exception e) {
                            LogUtils.e(HvacController.TAG, e.toString());
                        }
                        HvacController.this.handleHvacOutsidePm25Changed(i);
                    }

                    public void onErrorEvent(int errorCode, int operation) {
                        LogUtils.i(HvacController.TAG, "errorCode:" + errorCode + ",operation:" + operation);
                        HvacController.this.handleHvacOutsidePm25Changed(-1);
                    }
                });
            } catch (XUIServiceNotConnectedException e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
        LogUtils.i(TAG, "Init xui manager end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        if (BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        if (this.mIsMainProcess) {
            arrayList.add(557849130);
            arrayList.add(557849129);
            arrayList.add(557849127);
            arrayList.add(358614275);
            arrayList.add(559946242);
            arrayList.add(557849101);
            arrayList.add(356517121);
            arrayList.add(557849161);
            arrayList.add(356517120);
            arrayList.add(356517128);
            arrayList.add(557849126);
            arrayList.add(557849092);
            arrayList.add(557849093);
            arrayList.add(557849115);
            arrayList.add(291505923);
            arrayList.add(557849132);
            arrayList.add(557849162);
            arrayList.add(557849149);
            if (BaseFeatureOption.getInstance().isSupportHvacVentControl()) {
                arrayList.add(557849120);
                arrayList.add(557849116);
                arrayList.add(557849104);
                arrayList.add(557849105);
                arrayList.add(557849106);
                arrayList.add(557849107);
                arrayList.add(557849117);
                arrayList.add(557849108);
                arrayList.add(557849109);
                arrayList.add(557849110);
                arrayList.add(557849111);
                arrayList.add(557849114);
            }
            if (this.mCarConfig.isSupportAqs()) {
                arrayList.add(557849112);
                arrayList.add(557849118);
            }
        }
        addFurtherPropertyIds(arrayList);
        return arrayList;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarHvacEventCallback);
            } catch (Exception e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void handleCarEventsUpdate(final CarPropertyValue<?> value) {
        SINGLE_THREAD_POOL.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$HvacController$vh9tsgPLd1nM9bA7NivGxifgxj4
            @Override // java.lang.Runnable
            public final void run() {
                HvacController.this.lambda$handleCarEventsUpdate$0$HvacController(value);
            }
        });
    }

    public /* synthetic */ void lambda$handleCarEventsUpdate$0$HvacController(final CarPropertyValue value) {
        switch (value.getPropertyId()) {
            case 557849146:
            case 557849147:
            case 557849148:
                if (((Integer) getValue(value)).intValue() == 0) {
                    int i = 0;
                    try {
                        i = getIntProperty(value.getPropertyId());
                    } catch (Exception e) {
                        LogUtils.e(TAG, e.getMessage());
                    }
                    if (i == 0) {
                        return;
                    }
                }
                break;
        }
        this.mCarPropertyMap.put(Integer.valueOf(value.getPropertyId()), value);
        handleEventsUpdate(value);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void handleEventsUpdate(CarPropertyValue<?> value) {
        int propertyId = value.getPropertyId();
        switch (propertyId) {
            case 291505923:
                handleExternalTempUpdate(((Float) getValue(value)).floatValue());
                return;
            case 356517128:
                handleRecirculationModeUpdate(((Integer) getValue(value)).intValue());
                return;
            case 358614275:
                handleTempDriverUpdate(((Float) getValue(value)).floatValue());
                if (CarBaseConfig.getInstance().isSupportHvacDualTemp()) {
                    return;
                }
                this.mCarPropertyMap.put(559946242, value);
                handleTempPsnUpdate(((Float) getValue(value)).floatValue());
                return;
            case 557849101:
                handleSyncModeUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557849132:
                handleHvacSelfDry(((Integer) getValue(value)).intValue());
                return;
            case 557849149:
                handleHvacAutoDefogSwitchChanged(((Integer) getValue(value)).intValue());
                return;
            case 559946242:
                handleTempPsnUpdate(((Float) getValue(value)).floatValue());
                return;
            default:
                switch (propertyId) {
                    case 356517120:
                        handleWindSpeedLevelUpdate(((Integer) getValue(value)).intValue());
                        return;
                    case 356517121:
                        handleWindBlowModeUpdate(((Integer) getValue(value)).intValue());
                        return;
                    default:
                        switch (propertyId) {
                            case 557849092:
                                handleQualityInnerPM25Update(((Integer) getValue(value)).intValue());
                                return;
                            case 557849093:
                                handleHvacAirQualityLevel(((Integer) getValue(value)).intValue());
                                return;
                            default:
                                switch (propertyId) {
                                    case 557849104:
                                        handleEAVDriverLeftHPos(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849105:
                                        handleEAVDriverLeftVPos(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849106:
                                        handleEAVDriverRightHPos(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849107:
                                        handleEAVDriverRightVPos(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849108:
                                        handleEAVPsnLeftHPos(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849109:
                                        handleEAVPsnLeftVPos(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849110:
                                        handleEAVPsnRightHPos(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849111:
                                        handleEAVPsnRightVPos(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849112:
                                        handleAqsModeUpdate(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849113:
                                        handleAqsLevelUpdate(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849114:
                                        handleEAVSweepMode(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849115:
                                        handleEconModeUpdate(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849116:
                                        handleEAVDriverWindMode(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849117:
                                        handleEAVPsnWindMode(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849118:
                                        handleIonizerMode(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849119:
                                        handleAirCirculationInterval(((Integer) getValue(value)).intValue());
                                        return;
                                    case 557849120:
                                        handleWindModEconLour(((Integer) getValue(value)).intValue());
                                        return;
                                    default:
                                        switch (propertyId) {
                                            case 557849126:
                                                handleFrontDefrostUpdate(((Integer) getValue(value)).intValue());
                                                return;
                                            case 557849127:
                                                handleAcModeUpdate(((Integer) getValue(value)).intValue());
                                                return;
                                            default:
                                                switch (propertyId) {
                                                    case 557849129:
                                                        handleAutoModeUpdate(((Integer) getValue(value)).intValue());
                                                        return;
                                                    case 557849130:
                                                        handlePowerModeUpdate(((Integer) getValue(value)).intValue());
                                                        return;
                                                    default:
                                                        switch (propertyId) {
                                                            case 557849161:
                                                                handleRearWindBlowModeUpdate(((Integer) getValue(value)).intValue());
                                                                return;
                                                            case 557849162:
                                                                handleHvacAutoDefogWorkStChanged(((Integer) getValue(value)).intValue());
                                                                return;
                                                            default:
                                                                LogUtils.w(TAG, "handle unknown event: " + value);
                                                                return;
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
    }

    public void setHvacPowerMode(boolean enable) {
        if (!enable && isHvacRapidCoolingEnable()) {
            LogUtils.i(TAG, "setHvacPowerMode close rapid cooling", false);
            setHvacRapidCoolingEnable(false);
            delayCloseHvac();
        } else if (!enable && isHvacDeodorantEnable()) {
            LogUtils.i(TAG, "setHvacPowerMode close deodorant", false);
            setHvacDeodorantEnable(false);
            delayCloseHvac();
        } else if (!enable && isHvacQualityPurgeEnable()) {
            setHvacQualityPurgeMode(false);
            delayCloseHvac();
        } else {
            setPowerMode(enable);
        }
    }

    private void delayCloseHvac() {
        ThreadUtils.removeRunnable(this.closeHvacRun);
        ThreadUtils.postDelayed(2, this.closeHvacRun, 500L);
    }

    public /* synthetic */ void lambda$new$1$HvacController() {
        setPowerMode(false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setPowerMode(boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        if (!enable) {
            this.mFunctionModel.setHvacDrvTempSyncMode(getHvacDriverSyncMode());
        }
        try {
            this.mCarManager.setHvacPowerMode(parseCdnSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setPowerMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacPowerModeOn() {
        int hvacPowerMode;
        int i = 0;
        try {
            try {
                hvacPowerMode = getIntProperty(557849130);
                LogUtils.debug(TAG, "getIntProperty:" + hvacPowerMode, false);
            } catch (Exception e) {
                LogUtils.e(TAG, "isHvacPowerModeOn: " + e.getMessage(), false);
            }
        } catch (Exception unused) {
            hvacPowerMode = this.mCarManager.getHvacPowerMode();
            LogUtils.i(TAG, "getHvacPowerMode:" + hvacPowerMode, false);
        }
        i = hvacPowerMode;
        return parseHvacStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacAutoMode(boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setHvacAutoMode(parseCdnSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacAutoMode: " + e.getMessage(), false);
        }
    }

    public boolean isHvacAcModeOn() {
        return getAcHeatNatureMode() == 1;
    }

    public void setHvacAcMode(boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        int acHeatNatureMode = getAcHeatNatureMode();
        if (enable) {
            if (1 != acHeatNatureMode) {
                setAcHeatNatureMode(1);
            }
        } else if (1 == acHeatNatureMode) {
            setAcHeatNatureMode(3);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacAutoModeOn() {
        int i = 0;
        try {
            try {
                i = getIntProperty(557849129);
            } catch (Exception e) {
                LogUtils.e(TAG, "isHvacAutoModeOn: " + e.getMessage(), false);
            }
        } catch (Exception unused) {
            i = this.mCarManager.getHvacAutoMode();
        }
        return parseHvacStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setAcHeatNatureMode(int mode) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setAcHeatNatureButtonSt(mode);
        } catch (Exception e) {
            LogUtils.e(TAG, "setAcHeatNatureMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getAcHeatNatureMode() {
        try {
            try {
                return getIntProperty(557849127);
            } catch (Exception e) {
                LogUtils.e(TAG, "getAcHeatNatureMode: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getAcHeatNatureSt();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacTempDriver(float temperature) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setHvacTempDriverValue(temperature);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacTempDriver: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacTempDriverStep(boolean isUp) {
        if (EXHIBITION_MODE) {
            return;
        }
        if (isUp) {
            try {
                this.mCarManager.setHvacDrvseatTempInc();
                return;
            } catch (Exception e) {
                LogUtils.e(TAG, "setHvacTempDriverStep: " + e.getMessage(), false);
                return;
            }
        }
        try {
            this.mCarManager.setHvacDrvseatTempDec();
        } catch (Exception e2) {
            LogUtils.e(TAG, "setHvacTempDriverStep: " + e2.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public float getHvacTempDriver() {
        try {
            try {
                return getFloatProperty(358614275);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacTempDriver: " + e.getMessage(), false);
                return 18.0f;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHvacTempDriverValue();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacTempPsn(float temperature) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            if (CarBaseConfig.getInstance().isSupportHvacDualTemp() && ((!isHvacPowerModeOn() || !getHvacDriverSyncMode()) && (isHvacPowerModeOn() || !this.mFunctionModel.isHvacDrvTempSyncMode()))) {
                this.mCarManager.setHvacTempPsnValue(temperature);
                return;
            }
            setHvacTempDriver(temperature);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacTempPsn: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacTempPsnStep(boolean isUp) {
        if (EXHIBITION_MODE) {
            return;
        }
        if (!CarBaseConfig.getInstance().isSupportHvacDualTemp() || ((isHvacPowerModeOn() && getHvacDriverSyncMode()) || (!isHvacPowerModeOn() && this.mFunctionModel.isHvacDrvTempSyncMode()))) {
            setHvacTempDriverStep(isUp);
        } else if (isUp) {
            try {
                this.mCarManager.setHvacPsnseatTempInc();
            } catch (Exception e) {
                LogUtils.e(TAG, "setHvacTempPsnStep: " + e.getMessage(), false);
            }
        } else {
            try {
                this.mCarManager.setHvacPsnseatTempDec();
            } catch (Exception e2) {
                LogUtils.e(TAG, "setHvacTempPsnStep: " + e2.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public float getHvacTempPsn() {
        try {
            try {
                return getFloatProperty(559946242);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacTempPsn: " + e.getMessage(), false);
                return 18.0f;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHvacTempPsnValue();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacDriverSyncMode(boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setHvacTempLeftSyncMode(parseCdnSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacDriverSyncMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean getHvacDriverSyncMode() {
        int i;
        try {
            try {
                i = getIntProperty(557849101);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacDriverSyncMode: " + e.getMessage(), false);
                i = 3;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getHvacTempSyncMode();
        }
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacPsnSyncMode(boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        setHvacDriverSyncMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean getHvacPsnSyncMode() {
        return getHvacDriverSyncMode();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public float getHvacInnerTemp() {
        try {
            try {
                return getFloatProperty(559946285);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacInnerTemp: " + e.getMessage(), false);
                return 18.0f;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHvacInnerTemp();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacWindBlowMode(int mode) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setAirDistributionMode(mode);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacWindBlowMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRearWindBlowMode(int mode) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setRearAirDistributionMode(mode);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacRearWindBlowMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRearVoiceWindBlowMode(int mode) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setRearHvacWindBlowMode(mode);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacRearWindBlowMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacWindBlowMode() {
        try {
            try {
                return getIntProperty(356517121);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacWindBlowMode: " + e.getMessage(), false);
                return 1;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHvacWindBlowMode();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacRearWindBlowMode() {
        try {
            try {
                return getIntProperty(557849161);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacRearWindBlowMode: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getRearHvacWindBlowMode();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacWindSpeedLevel(int level) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setHvacWindSpeedLevel(level);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacWindSpeedLevel: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacWindSpeedStep(boolean isUp) {
        if (EXHIBITION_MODE) {
            return;
        }
        if (isUp) {
            try {
                this.mCarManager.setHvacFanSpeedInc();
                return;
            } catch (Exception e) {
                LogUtils.e(TAG, "setHvacWindSpeedStep: " + e.getMessage(), false);
                return;
            }
        }
        try {
            this.mCarManager.setHvacFanSpeedDec();
        } catch (Exception e2) {
            LogUtils.e(TAG, "setHvacWindSpeedStep: " + e2.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacWindSpeedLevel() {
        try {
            try {
                return getIntProperty(356517120);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacWindSpeedLevel: " + e.getMessage(), false);
                return 1;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHvacWindSpeedLevel();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacCirculationMode(int mode) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            if (BaseFeatureOption.getInstance().isSupportPressSignal()) {
                this.mCarManager.setHvacCirculationMode(mode);
            } else {
                this.mCarManager.setHvacCirculationMode(getHvacCirculationMode() == 2 ? 1 : 2);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacCirculationMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacCirculationMode() {
        try {
            try {
                return getIntProperty(356517128);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacCirculationMode: " + e.getMessage(), false);
                return 2;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHvacCirculationMode();
        }
    }

    public void setHvacFrontDefrost(boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        this.mFunctionModel.setHvacSmartModeClickTime(System.currentTimeMillis());
        final int parseCdnSwitchCmd = parseCdnSwitchCmd(enable);
        try {
            if (enable) {
                boolean z = true;
                if (isHvacQualityPurgeEnable()) {
                    setHvacQualityPurgeMode(false);
                } else if (isHvacRapidCoolingEnable()) {
                    setHvacRapidCoolingEnable(false);
                } else if (isHvacDeodorantEnable()) {
                    setHvacDeodorantEnable(false);
                } else {
                    z = false;
                }
                if (z) {
                    ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$HvacController$G-_rH1wxWUEBrob9cvmRQSBtzg0
                        @Override // java.lang.Runnable
                        public final void run() {
                            HvacController.this.lambda$setHvacFrontDefrost$2$HvacController(parseCdnSwitchCmd);
                        }
                    }, 500L);
                    return;
                } else {
                    this.mCarManager.setHvacFrontDefrostMode(parseCdnSwitchCmd);
                    return;
                }
            }
            this.mCarManager.setHvacFrontDefrostMode(parseCdnSwitchCmd);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacFrontDefrost: " + e.getMessage(), false);
        }
    }

    public /* synthetic */ void lambda$setHvacFrontDefrost$2$HvacController(final int cmd) {
        try {
            this.mCarManager.setHvacFrontDefrostMode(cmd);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacFrontDefrost: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacFrontDefrostOn() {
        int i = 0;
        try {
            try {
                i = getIntProperty(557849126);
            } catch (Exception e) {
                LogUtils.e(TAG, "isHvacFrontDefrostOn: " + e.getMessage(), false);
            }
        } catch (Exception unused) {
            i = this.mCarManager.getHVACFrontDefrostMode();
        }
        return parseHvacStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacInnerPm25() {
        try {
            try {
                return getIntProperty(557849092);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacInnerPm25: " + e.getMessage(), false);
                return -1;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHvacQualityInnerPm25Value();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacWindModEconLour() {
        try {
            try {
                return getIntProperty(557849120);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacWindModEconLour: " + e.getMessage(), false);
                return 1;
            }
        } catch (Exception unused) {
            return this.mCarManager.getWindModColor();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public float getHvacExternalTemp() {
        try {
            try {
                return getFloatProperty(291505923);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacExternalTemp: " + e.getMessage(), false);
                return 0.0f;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHvacOutterTemp();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEconMode(int status) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setEconMode(status);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacEconMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEconMode() {
        try {
            try {
                return getIntProperty(557849115);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacEconMode: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getEconModeSt();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacAqsMode(int status) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setAqsMode(status);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacAqsMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacAqsMode() {
        try {
            try {
                return getIntProperty(557849112);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacAqsMode: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getAqsModeSt();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacAqsLevel(int level) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setAqsSensitivity(level);
            if (this.mIsMainProcess) {
                this.mDataSync.setHvacAqsLevel(level);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacAqsLevel: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacAqsLevel() {
        try {
            try {
                return getIntProperty(557849113);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacAqsLevel: " + e.getMessage(), false);
                if (this.mIsMainProcess) {
                    return this.mDataSync.getHvacAqsLevel();
                }
                return 2;
            }
        } catch (Exception unused) {
            return this.mCarManager.getAqsSensitivity();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavDrvWindMode(int mode) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setEavDriverWindMode(mode);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacEavDrvWindMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavDrvWindMode() {
        try {
            try {
                return getIntProperty(557849116);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacEavDrvWindMode: " + e.getMessage(), false);
                return 3;
            }
        } catch (Exception unused) {
            return this.mCarManager.getEavDriverWindMode();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavPsnWindMode(int mode) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setEavPsnWindMode(mode);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacEavPsnWindMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavPsnWindMode() {
        try {
            try {
                return getIntProperty(557849117);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacEavPsnWindMode: " + e.getMessage(), false);
                return 3;
            }
        } catch (Exception unused) {
            return this.mCarManager.getEavPsnWindMode();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavDrvLeftHPos(int pos) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setDriverLeftWindHorPos(pos);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacEavDrvLeftHPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavDrvLeftHPos() {
        try {
            try {
                return getIntProperty(557849104);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacEavDrvLeftHPos: " + e.getMessage(), false);
                return 3;
            }
        } catch (Exception unused) {
            return this.mCarManager.getDriverLeftWindHorPos();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavDrvLeftVPos(int pos) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setDriverLeftWindVerPos(pos);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacEavDrvLeftVPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavDrvLeftVPos() {
        try {
            try {
                return getIntProperty(557849105);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacEavDrvLeftVPos: " + e.getMessage(), false);
                return 2;
            }
        } catch (Exception unused) {
            return this.mCarManager.getDriverLeftWindVerPos();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavDrvRightHPos(int pos) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setDriverRightWindHorPos(pos);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacEavDrvRightHPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavDrvRightHPos() {
        try {
            try {
                return getIntProperty(557849106);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacEavDrvRightHPos: " + e.getMessage(), false);
                return 3;
            }
        } catch (Exception unused) {
            return this.mCarManager.getDriverRightWindHorPos();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavDrvRightVPos(int pos) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setDriverRightWindVerPos(pos);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacEavDrvRightVPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavDrvRightVPos() {
        try {
            try {
                return getIntProperty(557849107);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacEavDrvRightVPos: " + e.getMessage(), false);
                return 2;
            }
        } catch (Exception unused) {
            return this.mCarManager.getDriverRightWindVerPos();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavPsnLeftHPos(int pos) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setPsnLeftWindHorPos(pos);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacEavPsnLeftHPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavPsnLeftHPos() {
        try {
            try {
                return getIntProperty(557849108);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacEavPsnLeftHPos: " + e.getMessage(), false);
                return 3;
            }
        } catch (Exception unused) {
            return this.mCarManager.getPsnLeftWindHorPos();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavPsnLeftVPos(int pos) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setPsnLeftWindVerPos(pos);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacEavPsnLeftVPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavPsnLeftVPos() {
        try {
            try {
                return getIntProperty(557849109);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacEavPsnLeftVPos: " + e.getMessage(), false);
                return 2;
            }
        } catch (Exception unused) {
            return this.mCarManager.getPsnLeftWindVerPos();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavPsnRightHPos(int pos) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setPsnRightWindHorPos(pos);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacEavPsnRightHPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavPsnRightHPos() {
        try {
            try {
                return getIntProperty(557849110);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacEavPsnRightHPos: " + e.getMessage(), false);
                return 3;
            }
        } catch (Exception unused) {
            return this.mCarManager.getPsnRightWindHorPos();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavPsnRightVPos(int pos) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setPsnRightWindVerPos(pos);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacEavPsnRightVPos: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavPsnRightVPos() {
        try {
            try {
                return getIntProperty(557849111);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacEavPsnRightVPos: " + e.getMessage(), false);
                return 2;
            }
        } catch (Exception unused) {
            return this.mCarManager.getPsnRightWindVerPos();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacCirculationTime(int time) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setCirculationPeriod(time);
            if (this.mIsMainProcess) {
                this.mDataSync.setHvacCircle(time);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacCirculationTime: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacCirculationTime() {
        try {
            try {
                return getIntProperty(557849119);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacCirculationTime: " + e.getMessage(), false);
                if (this.mIsMainProcess) {
                    return this.mDataSync.getHvacCircle();
                }
                return 2;
            }
        } catch (Exception unused) {
            return this.mCarManager.getCirculationPeriod();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacEavSweepMode(int status) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setSweepWindStatus(status);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacEavSweepMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacEavSweepMode() {
        try {
            try {
                return getIntProperty(557849114);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacEavSweepMode: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getSweepWindStatusSt();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacIonizerMode() {
        try {
            try {
                return getIntProperty(557849118);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacIonizerMode: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getLonizerModeSt();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacSelfDryEnable(boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setSelfDryStatus(enable ? 2 : 1);
            if (this.mIsMainProcess) {
                this.mDataSync.setHvacSelfDry(enable);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacSelfDryEnable: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacSelfDryOn() {
        int selfDryStatus;
        try {
            try {
                selfDryStatus = getIntProperty(557849132);
            } catch (Exception e) {
                LogUtils.e(TAG, "isHvacSelfDryOn: " + e.getMessage(), false);
                if (this.mIsMainProcess) {
                    return this.mDataSync.getHvacSelfDry();
                }
                return true;
            }
        } catch (Exception unused) {
            selfDryStatus = this.mCarManager.getSelfDryStatus();
        }
        return selfDryStatus == 2;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getAirAutoProtectMode() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getHvacAirProtectMode();
        }
        return 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setAirAutoProtectedMode(final int mode) {
        if (this.mIsMainProcess) {
            this.mDataSync.setHvacAirProtectMode(mode);
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$HvacController$ZjJiCtN66LK1ews4jCow8JaZUlU
                @Override // java.lang.Runnable
                public final void run() {
                    HvacController.this.lambda$setAirAutoProtectedMode$3$HvacController(mode);
                }
            });
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public String getAirAutoProtectSound() {
        return this.mIsMainProcess ? DebugFuncModel.getInstance().getAirAutoProtectSound() : "/system/media/audio/xiaopeng/cdu/wav/CDU_air_protect_3.wav";
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setAirAutoProtectSound(String soundType) {
        if (this.mIsMainProcess) {
            DebugFuncModel.getInstance().setAirAutoProtectSound(soundType);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isSmartHvacEnabled() {
        if (this.mIsMainProcess) {
            LogUtils.i(TAG, "isSmartHvacEnabled enabled:" + this.mDataSync.getHvacSmart(), false);
            return this.mDataSync.getHvacSmart();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setSmartHvacEnable(final boolean enable) {
        if (this.mIsMainProcess) {
            this.mDataSync.setHvacSmart(enable);
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$HvacController$ereB3f7sCSTk8vCUT9QD7oDR3P0
                @Override // java.lang.Runnable
                public final void run() {
                    HvacController.this.lambda$setSmartHvacEnable$4$HvacController(enable);
                }
            });
        }
    }

    public boolean isHvacQualityPurgeEnable() {
        if (this.mIsMainProcess) {
            return this.mFunctionModel.isHvacAirPurgeEnable();
        }
        return false;
    }

    public void setHvacQualityPurgeMode(final boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        LogUtils.i(TAG, "setHvacQualityPurgeMode:" + enable, false);
        this.mFunctionModel.setHvacPurgeClickTime(System.currentTimeMillis());
        if (!this.mIsMainProcess || this.mFunctionModel.isHvacAirPurgeEnable() == enable) {
            return;
        }
        this.mFunctionModel.setHvacAirPurgeMode(enable);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$HvacController$o-9GGbnguftFFaLr45_FGKEhGG8
            @Override // java.lang.Runnable
            public final void run() {
                HvacController.this.lambda$setHvacQualityPurgeMode$5$HvacController(enable);
            }
        });
    }

    public /* synthetic */ void lambda$setHvacQualityPurgeMode$5$HvacController(final boolean enable) {
        handleHvacQualityPurgeMode(enable ? 1 : 0);
    }

    public boolean isHvacRapidCoolingEnable() {
        if (this.mIsMainProcess) {
            return this.mFunctionModel.isHvacRapidCoolingEnable();
        }
        return false;
    }

    public void setHvacRapidCoolingEnable(final boolean enable) {
        LogUtils.i(TAG, "setHvacRapidCoolingEnable:" + enable + ",current:" + this.mFunctionModel.isHvacRapidCoolingEnable(), false);
        if (!EXHIBITION_MODE && this.mIsMainProcess) {
            if (enable) {
                this.mFunctionModel.setHvacRapidCoolingTime(System.currentTimeMillis());
            }
            this.mFunctionModel.setHvacSmartModeClickTime(System.currentTimeMillis());
            if (this.mFunctionModel.isHvacRapidCoolingEnable() != enable) {
                if (enable && isHvacQualityPurgeEnable()) {
                    setHvacQualityPurgeMode(false);
                    ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$HvacController$Zh4oitBaAkB3SrEvPYQtBQB0Ju0
                        @Override // java.lang.Runnable
                        public final void run() {
                            HvacController.this.lambda$setHvacRapidCoolingEnable$6$HvacController();
                        }
                    }, 500L);
                    return;
                }
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$HvacController$4ahCaVew86RcuiCu-Hk2qZ5FETA
                    @Override // java.lang.Runnable
                    public final void run() {
                        HvacController.this.lambda$setHvacRapidCoolingEnable$7$HvacController(enable);
                    }
                });
            }
        }
    }

    public /* synthetic */ void lambda$setHvacRapidCoolingEnable$6$HvacController() {
        lambda$setHvacRapidCoolingEnable$7$HvacController(true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacRapidCoolingCountDownTimer() {
        if (this.mIsMainProcess) {
            return this.mFunctionModel.getHvacRapidCoolingCountdown();
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRapidCoolingCountDownTimer(final int time) {
        if (this.mIsMainProcess) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$HvacController$CQ4qVjNcmp8JipF7fzQ8LO0mYto
                @Override // java.lang.Runnable
                public final void run() {
                    HvacController.this.lambda$setHvacRapidCoolingCountDownTimer$8$HvacController(time);
                }
            });
        }
    }

    public boolean isHvacDeodorantEnable() {
        if (this.mIsMainProcess) {
            return this.mFunctionModel.isHvacDeodorantEnable();
        }
        return false;
    }

    public void setHvacDeodorantEnable(final boolean enable) {
        LogUtils.i(TAG, "setHvacDeodorantEnable:" + enable + ",current:" + this.mFunctionModel.isHvacDeodorantEnable(), false);
        if (!EXHIBITION_MODE && this.mIsMainProcess) {
            if (enable) {
                this.mFunctionModel.setHvacDeodorantTime(System.currentTimeMillis());
            }
            this.mFunctionModel.setHvacSmartModeClickTime(System.currentTimeMillis());
            if (this.mFunctionModel.isHvacDeodorantEnable() != enable) {
                if (enable && isHvacQualityPurgeEnable()) {
                    setHvacQualityPurgeMode(false);
                    ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$HvacController$Q_mMGSkPHPlSbJg5TYtWz6DtL6s
                        @Override // java.lang.Runnable
                        public final void run() {
                            HvacController.this.lambda$setHvacDeodorantEnable$9$HvacController();
                        }
                    }, 500L);
                    return;
                }
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$HvacController$JZMW8Ahzzixiz9ExX_MrWhSSfUA
                    @Override // java.lang.Runnable
                    public final void run() {
                        HvacController.this.lambda$setHvacDeodorantEnable$10$HvacController(enable);
                    }
                });
            }
        }
    }

    public /* synthetic */ void lambda$setHvacDeodorantEnable$9$HvacController() {
        lambda$setHvacDeodorantEnable$10$HvacController(true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacDeodorantCountDownTimer() {
        if (this.mIsMainProcess) {
            return this.mFunctionModel.getHvacDeodorantCountdown();
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacDeodorantCountDownTimer(final int time) {
        if (this.mIsMainProcess) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$HvacController$a79J4UwJX7L2t3KIAn-YvHEjpqs
                @Override // java.lang.Runnable
                public final void run() {
                    HvacController.this.lambda$setHvacDeodorantCountDownTimer$11$HvacController(time);
                }
            });
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getOutsidePm25() {
        ContextInfoManager contextInfoManager = this.mCtiManager;
        if (contextInfoManager != null) {
            try {
                String weather = contextInfoManager.getWeather();
                LogUtils.i(TAG, "weather:" + weather, false);
                if (TextUtils.isEmpty(weather)) {
                    return -1;
                }
                JSONObject jSONObject = new JSONObject(weather);
                if (jSONObject.has(IHvacController.HVAC_OUTSIDE_PM25_KEY)) {
                    return jSONObject.getInt(IHvacController.HVAC_OUTSIDE_PM25_KEY);
                }
                return -1;
            } catch (Exception e) {
                LogUtils.e(TAG, "getOutsidePm25: " + e.getMessage(), false);
                return -1;
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void updateWeatherFromServer() {
        try {
            ContextInfoManager contextInfoManager = this.mCtiManager;
            if (contextInfoManager != null) {
                contextInfoManager.updateWeatherFromServer();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "updateWeatherFromServer: " + e.getMessage(), false);
        }
    }

    public boolean isHvacAirIntakeAutoEnable() {
        return getHvacCirculationMode() == 6;
    }

    public boolean isHvacFanSpeedAutoEnable() {
        return getHvacWindSpeedLevel() == 14;
    }

    public boolean isHvacAirDistributionAutoEnable() {
        return getHvacWindBlowMode() == 14;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacSingleMode(boolean enable) {
        this.mFunctionModel.setHvacSingleMode(enable);
        handleHvacSingleMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacSingleModeEnable() {
        return this.mFunctionModel.isHvacSingleMode();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacSingleModeActive(boolean enable) {
        this.mFunctionModel.setHvacSingleModeActive(enable);
        handleSingleModeActive(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacSingleModeActive() {
        return this.mFunctionModel.isHvacSingleModeActive();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacAcpConsumption() {
        try {
            return this.mCarManager.getCompressorConsumePower();
        } catch (Exception e) {
            LogUtils.w(TAG, "getHvacAcpConsumption failed: " + e.getMessage(), false);
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacPtcConsumption() {
        try {
            return this.mCarManager.getHvhConsumePower();
        } catch (Exception e) {
            LogUtils.w(TAG, "getHvacPtcConsumption failed: " + e.getMessage(), false);
            return 0;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isAutoDefogWorkSt() {
        int i = 0;
        try {
            try {
                i = getIntProperty(557849162);
            } catch (Exception e) {
                LogUtils.e(TAG, "isAutoDefogWorkSt: " + e.getMessage(), false);
            }
        } catch (Exception unused) {
            i = this.mCarManager.getAutoDefogWorkSt();
        }
        return parseHvacStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setAutoDefogSwitch(boolean enable) {
        LogUtils.i(TAG, "setAutoDefogSwitch:" + enable, false);
        if (EXHIBITION_MODE) {
            return;
        }
        this.mFunctionModel.setAutoDefogSwitch(enable);
        try {
            this.mCarManager.setAutoDefogSwitch(parseCdnSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacAqsMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isAutoDefogSwitch() {
        int i;
        try {
            try {
                i = getIntProperty(557849149);
            } catch (Exception e) {
                LogUtils.e(TAG, "isAutoDefogSwitch: " + e.getMessage(), false);
                i = 0;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getAutoDefogSwitchStatus();
        }
        LogUtils.e(TAG, "isAutoDefogSwitch XXX status:" + i, false);
        return parseHvacStatus(i);
    }

    private void mockHvacValue() {
        this.mCarPropertyMap.put(557849130, new CarPropertyValue<>(557849130, 0));
        this.mCarPropertyMap.put(557849129, new CarPropertyValue<>(557849129, 0));
        this.mCarPropertyMap.put(557849127, new CarPropertyValue<>(557849127, 0));
        ConcurrentHashMap<Integer, CarPropertyValue<?>> concurrentHashMap = this.mCarPropertyMap;
        Float valueOf = Float.valueOf(18.0f);
        concurrentHashMap.put(358614275, new CarPropertyValue<>(358614275, valueOf));
        this.mCarPropertyMap.put(559946242, new CarPropertyValue<>(559946242, valueOf));
        this.mCarPropertyMap.put(557849101, new CarPropertyValue<>(557849101, 3));
        this.mCarPropertyMap.put(356517121, new CarPropertyValue<>(356517121, 1));
        this.mCarPropertyMap.put(557849161, new CarPropertyValue<>(557849161, 1));
        this.mCarPropertyMap.put(356517120, new CarPropertyValue<>(356517120, 1));
        this.mCarPropertyMap.put(356517128, new CarPropertyValue<>(356517128, 2));
        this.mCarPropertyMap.put(557849126, new CarPropertyValue<>(557849126, 0));
        this.mCarPropertyMap.put(557849092, new CarPropertyValue<>(557849092, -1));
        this.mCarPropertyMap.put(291505923, new CarPropertyValue<>(291505923, Float.valueOf(0.0f)));
        this.mCarPropertyMap.put(557849162, new CarPropertyValue<>(557849162, 0));
        this.mCarPropertyMap.put(557849149, new CarPropertyValue<>(557849149, 0));
        mockExtHvacValue();
    }

    protected void mockExtHvacValue() {
        this.mCarPropertyMap.put(557849120, new CarPropertyValue<>(557849120, 1));
        this.mCarPropertyMap.put(557849115, new CarPropertyValue<>(557849115, 0));
        this.mCarPropertyMap.put(557849112, new CarPropertyValue<>(557849112, 0));
        int i = 2;
        this.mCarPropertyMap.put(557849113, new CarPropertyValue<>(557849113, Integer.valueOf(this.mIsMainProcess ? this.mDataSync.getHvacAqsLevel() : 2)));
        this.mCarPropertyMap.put(557849116, new CarPropertyValue<>(557849116, 3));
        this.mCarPropertyMap.put(557849104, new CarPropertyValue<>(557849104, 3));
        this.mCarPropertyMap.put(557849105, new CarPropertyValue<>(557849105, 2));
        this.mCarPropertyMap.put(557849106, new CarPropertyValue<>(557849106, 3));
        this.mCarPropertyMap.put(557849107, new CarPropertyValue<>(557849107, 2));
        this.mCarPropertyMap.put(557849117, new CarPropertyValue<>(557849117, 3));
        this.mCarPropertyMap.put(557849108, new CarPropertyValue<>(557849108, 3));
        this.mCarPropertyMap.put(557849109, new CarPropertyValue<>(557849109, 2));
        this.mCarPropertyMap.put(557849110, new CarPropertyValue<>(557849110, 3));
        this.mCarPropertyMap.put(557849111, new CarPropertyValue<>(557849111, 2));
        this.mCarPropertyMap.put(557849119, new CarPropertyValue<>(557849119, Integer.valueOf(this.mIsMainProcess ? this.mDataSync.getHvacCircle() : 2)));
        this.mCarPropertyMap.put(557849114, new CarPropertyValue<>(557849114, 0));
        this.mCarPropertyMap.put(557849118, new CarPropertyValue<>(557849118, 0));
        ConcurrentHashMap<Integer, CarPropertyValue<?>> concurrentHashMap = this.mCarPropertyMap;
        if (this.mIsMainProcess) {
            i = Boolean.valueOf(this.mDataSync.getHvacSelfDry());
        }
        concurrentHashMap.put(557849132, new CarPropertyValue<>(557849132, i));
    }

    private void handlePowerModeUpdate(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacPowerModeChanged(parseHvacStatus(status));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleAutoModeUpdate(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacAutoModeChanged(parseHvacStatus(status));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleAcModeUpdate(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacAcHeatNatureChanged(status);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleTempDriverUpdate(float temp) {
        if (temp > 32.0f) {
            temp = 32.0f;
        } else if (temp < 18.0f) {
            temp = 18.0f;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacTempDrvChanged(temp);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleTempPsnUpdate(float temp) {
        if (temp > 32.0f) {
            temp = 32.0f;
        } else if (temp < 18.0f) {
            temp = 18.0f;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacTempPsnChanged(temp);
            }
        }
    }

    private void handleSyncModeUpdate(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IHvacController.Callback callback = (IHvacController.Callback) it.next();
                boolean z = true;
                if (mode == 1 || mode == 2 || mode == 3) {
                    callback.onHvacDrvSyncModeChanged(mode == 1);
                    if (mode != 1) {
                        z = false;
                    }
                    callback.onHvacPsnSyncModeChanged(z);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleWindBlowModeUpdate(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacWindBlowModeChanged(mode);
            }
        }
    }

    protected void handleRearWindBlowModeUpdate(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacRearWindBlowModeChanged(mode);
            }
        }
    }

    protected void handleWindSpeedLevelUpdate(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacWindSpeedLevelChanged(level);
            }
        }
    }

    protected void handleRecirculationModeUpdate(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacCirculationModeChanged(mode);
            }
        }
    }

    private void handleFrontDefrostUpdate(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacFrontDefrostChanged(parseHvacStatus(status));
            }
        }
    }

    private void handleQualityInnerPM25Update(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacInnerPM25Changed(level);
            }
        }
    }

    private void handleWindModEconLour(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacWindModEconLourChanged(mode);
            }
        }
    }

    private void handleExternalTempUpdate(float temp) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacExternalTempChanged(temp);
            }
        }
    }

    private void handleEconModeUpdate(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacEconModeChange(status);
            }
        }
    }

    private void handleAqsModeUpdate(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacAqsModeChange(status);
            }
        }
    }

    private void handleAqsLevelUpdate(int level) {
        if (level < 1 || level > 3) {
            LogUtils.w(TAG, "Invalid aqs level: " + level, false);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacAqsLevelChange(level);
            }
        }
    }

    private void handleEAVDriverWindMode(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacEavDrvWindModeChange(mode);
            }
        }
    }

    private void handleEAVPsnWindMode(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacEavPsnWindModeChange(mode);
            }
        }
    }

    private void handleEAVDriverLeftHPos(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacEavDrvLeftHPosChanged(pos);
            }
        }
    }

    private void handleEAVDriverLeftVPos(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacEavDrvLeftVPosChanged(pos);
            }
        }
    }

    private void handleEAVDriverRightHPos(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacEavDrvRightHPosChanged(pos);
            }
        }
    }

    private void handleEAVDriverRightVPos(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacEavDrvRightVPosChanged(pos);
            }
        }
    }

    private void handleEAVPsnLeftHPos(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacEavPsnLeftHPosChanged(pos);
            }
        }
    }

    private void handleEAVPsnLeftVPos(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacEavPsnLeftVPosChanged(pos);
            }
        }
    }

    private void handleEAVPsnRightHPos(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacEavPsnRightHPosChanged(pos);
            }
        }
    }

    private void handleEAVPsnRightVPos(int pos) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacEavPsnRightVPosChanged(pos);
            }
        }
    }

    private void handleAirCirculationInterval(int interval) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacCirculationTimeChanged(interval);
            }
        }
    }

    private void handleEAVSweepMode(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacEavSweepModeChanged(status);
            }
        }
    }

    private void handleIonizerMode(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacIonizerModeChanged(status);
            }
        }
    }

    private void handleHvacSelfDry(int status) {
        if (status == 2 || status == 1) {
            boolean z = status == 2;
            synchronized (this.mCallbackLock) {
                Iterator it = this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((IHvacController.Callback) it.next()).onHvacSelfDryChanged(z);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleAirProtectModeChanged */
    public void lambda$setAirAutoProtectedMode$3$HvacController(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacAirProtectModeChanged(mode);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleHvacSmartSwChanged */
    public void lambda$setSmartHvacEnable$4$HvacController(boolean enabled) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacSmartSwChanged(enabled);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: handleHvacRapidCoolingChanged */
    public void lambda$setHvacRapidCoolingEnable$7$HvacController(boolean enable) {
        if (this.mIsMainProcess) {
            this.mFunctionModel.setHvacRapidCoolingEnable(enable);
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacRapidCoolingChanged(enable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleHvacRapidCoolingCountdownTimer */
    public void lambda$setHvacRapidCoolingCountDownTimer$8$HvacController(int time) {
        if (this.mIsMainProcess) {
            this.mFunctionModel.setHvacRapidCoolingCountdown(time);
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacRapidCoolingTimerChanged(time);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleHvacRapidHeatChanged(boolean enable) {
        if (this.mIsMainProcess) {
            this.mFunctionModel.setHvacRapidHeatEnable(enable);
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacRapidHeatChanged(enable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleHvacRapidHeatCountdownTimer(int time) {
        if (this.mIsMainProcess) {
            this.mFunctionModel.setHvacRapidHeatCountdown(time);
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacRapidHeatTimerChanged(time);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: handleHvacDeodorantChanged */
    public void lambda$setHvacDeodorantEnable$10$HvacController(boolean enable) {
        if (this.mIsMainProcess) {
            this.mFunctionModel.setHvacDeodorantEnable(enable);
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacDeodorantChanged(enable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleHvacDeodorantCountdownTimer */
    public void lambda$setHvacDeodorantCountDownTimer$11$HvacController(int time) {
        if (this.mIsMainProcess) {
            this.mFunctionModel.setHvacDeodorantCountdown(time);
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacDeodorantTimerChanged(time);
            }
        }
    }

    private void handleHvacAirQualityLevel(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacAirQualityLevel(level);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHvacOutsidePm25Changed(int value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacOutsidePm25Changed(value);
            }
        }
    }

    private void handleHvacSingleMode(boolean enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacSingleModeChanged(enable);
            }
        }
    }

    private void handleSingleModeActive(boolean enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacSingleModeActivated(enable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleHvacQualityPurgeMode(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IHvacController.Callback callback = (IHvacController.Callback) it.next();
                boolean z = true;
                if (mode != 1) {
                    z = false;
                }
                callback.onHvacQualityPurgeChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleFanAutoUpdate(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacFanSpeedAutoChanged(parseHvacStatus(status));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleAirIntakeAutoUpdate(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacAirIntakeAutoChanged(parseHvacStatus(status));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleHvacSfsSwitchChanged(boolean enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacSfsSwitchChanged(enable);
            }
        }
    }

    protected void handleHvacAutoDefogWorkStChanged(int status) {
        LogUtils.d(TAG, "handleHvacAutoDefogWorkStChanged:" + status);
        if (isHvacQualityPurgeEnable()) {
            setHvacQualityPurgeMode(false);
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacAutoDefogWorkStChanged(parseHvacStatus(status));
            }
        }
    }

    protected void handleHvacAutoDefogSwitchChanged(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IHvacController.Callback callback = (IHvacController.Callback) it.next();
                boolean z = true;
                if (status != 1) {
                    z = false;
                }
                callback.onHvacAutoDefogSwitchChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static float mockNewTemp(boolean isUp, float currentTemp) {
        return Math.max(Math.min(isUp ? currentTemp + 0.5f : currentTemp - 0.5f, 32.0f), 18.0f);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int mockNewSpeed(boolean isUp, int currentSpeed) {
        return Math.max(Math.min(isUp ? currentSpeed + 1 : currentSpeed - 1, getWindMaxLevel()), 1);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRearPowerMode(boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setRearHvacPowerMode(parseCdnSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setRearHvacPowerMode:" + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacRearPowerModeOn() {
        int rearHvacPowerMode;
        int i = 0;
        try {
            try {
                rearHvacPowerMode = getIntProperty(557849163);
                LogUtils.debug(TAG, "getIntProperty:" + rearHvacPowerMode, false);
            } catch (Exception e) {
                LogUtils.e(TAG, "isHvacRearPowerModeOn: " + e.getMessage(), false);
            }
        } catch (Exception unused) {
            rearHvacPowerMode = this.mCarManager.getRearHvacPowerMode();
            LogUtils.i(TAG, "isHvacRearPowerModeOn:" + rearHvacPowerMode, false);
        }
        i = rearHvacPowerMode;
        return parseHvacStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRearTempDriver(float temperature) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setHvacTempSecRowLeftValue(temperature);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacTempSecRowLeftValue:" + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRearTempDriverStep(boolean isUp) {
        if (EXHIBITION_MODE) {
            return;
        }
        if (isUp) {
            try {
                this.mCarManager.setHvacSecRowLeftTempInc();
                return;
            } catch (Exception e) {
                LogUtils.e(TAG, "setHvacSecRowLeftTempInc:" + e.getMessage(), false);
                return;
            }
        }
        try {
            this.mCarManager.setHvacSecRowLeftTempDec();
        } catch (Exception e2) {
            LogUtils.e(TAG, "setHvacSecRowLeftTempDec:" + e2.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public float getHvacRearTempDriver() {
        try {
            try {
                return getFloatProperty(559946320);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacRearTempDriver:" + e.getMessage(), false);
                return 18.0f;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHvacTempSecRowLeftValue();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRearTempPsn(float temperature) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            if ((isHvacRearPowerModeOn() && getHvacDriverSyncMode()) || (!isHvacRearPowerModeOn() && this.mFunctionModel.isHvacDrvTempSyncMode())) {
                setHvacRearTempDriver(temperature);
            } else {
                this.mCarManager.setHvacTempSecRowRightValue(temperature);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacRearTempPsn:" + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRearTempPsnStep(boolean isUp) {
        if (EXHIBITION_MODE) {
            return;
        }
        if ((isHvacRearPowerModeOn() && getHvacDriverSyncMode()) || (!isHvacRearPowerModeOn() && this.mFunctionModel.isHvacDrvTempSyncMode())) {
            setHvacRearTempDriverStep(isUp);
        } else if (isUp) {
            try {
                this.mCarManager.setHvacSecRowRightTempInc();
            } catch (Exception e) {
                LogUtils.e(TAG, "setHvacRearTempPsnStep:" + e.getMessage(), false);
            }
        } else {
            try {
                this.mCarManager.setHvacSecRowRightTempDec();
            } catch (Exception e2) {
                LogUtils.e(TAG, "setHvacRearTempPsnStep:" + e2.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public float getHvacRearTempPsn() {
        try {
            try {
                return getFloatProperty(559946321);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacRearTempPsn:" + e.getMessage(), false);
                return 18.0f;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHvacTempSecRowRightValue();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacThirdRowTempDriver(float temperature) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setHvacTempThirdRowtValue(temperature);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacTempThirdRowValue:" + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacThirdRowTempDriverStep(boolean isUp) {
        if (EXHIBITION_MODE) {
            return;
        }
        if (isUp) {
            try {
                this.mCarManager.setHvacThirdRowTempInc();
                return;
            } catch (Exception e) {
                LogUtils.e(TAG, "setHvacThirdRowTempDriverStep:" + e.getMessage(), false);
                return;
            }
        }
        try {
            this.mCarManager.setHvacThirdRowTempDec();
        } catch (Exception e2) {
            LogUtils.e(TAG, "setHvacThirdRowTempDriverStep:" + e2.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public float getHvacThirdRowTempDriver() {
        try {
            try {
                return getFloatProperty(559946330);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacThirdRowTempDriver:" + e.getMessage(), false);
                return 18.0f;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHvacTempThirdRowValue();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacThirdRowTempStep(boolean isUp) {
        if (EXHIBITION_MODE) {
            return;
        }
        if (isUp) {
            try {
                this.mCarManager.setHvacThirdRowTempInc();
                return;
            } catch (Exception e) {
                LogUtils.e(TAG, "setHvacThirdRowTempInc:" + e.getMessage(), false);
                return;
            }
        }
        try {
            this.mCarManager.setHvacThirdRowTempDec();
        } catch (Exception e2) {
            LogUtils.e(TAG, "setHvacThirdRowTempDec:" + e2.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRearAutoMode(boolean enable) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setHvacRearAutoMode(parseCdnSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacRearAutoMode:" + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacRearAutoModeOn() {
        int i = 0;
        try {
            try {
                i = getIntProperty(557849170);
            } catch (Exception e) {
                LogUtils.e(TAG, "isHvacRearAutoModeOn:" + e.getMessage(), false);
            }
        } catch (Exception unused) {
            i = this.mCarManager.getHvacRearAutoMode();
        }
        return parseHvacStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRearWindSpeedLevel(int level) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setHvacRearWindSpeedLevel(level);
        } catch (Exception e) {
            LogUtils.e(TAG, " setHvacRearWindSpeedLevel:" + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacRearWindSpeedStep(boolean isUp) {
        if (EXHIBITION_MODE) {
            return;
        }
        if (isUp) {
            try {
                this.mCarManager.setHvacRearFanSpeedInc();
                return;
            } catch (Exception e) {
                LogUtils.e(TAG, "setHvacRearWindSpeedStep:" + e.getMessage(), false);
                return;
            }
        }
        try {
            this.mCarManager.setHvacRearFanSpeedDec();
        } catch (Exception e2) {
            LogUtils.e(TAG, "setHvacRearWindSpeedStep:" + e2.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacRearWindSpeedLevel() {
        try {
            try {
                return getIntProperty(557849175);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacWindSpeedLevel:" + e.getMessage(), false);
                return 1;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHvacRearWindSpeedLevel();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacThirdRowWindBlowMode(int mode) {
        if (EXHIBITION_MODE) {
            return;
        }
        try {
            this.mCarManager.setHvacThirdRowWindBlowMode(mode);
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacThirdRowWindBlowMode:" + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public int getHvacThirdRowWindBlowMode() {
        try {
            try {
                return getIntProperty(557849179);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacThirdRowWindBlowMode:" + e.getMessage(), false);
                return 1;
            }
        } catch (Exception unused) {
            return this.mCarManager.getHvacThirdRowWindBlowMode();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacNewFreshSwitchStatus(boolean enable) {
        LogUtils.e(TAG, "setHvacNewFreshSwitchStatus: " + enable, false);
        try {
            this.mCarManager.setNewFreshSwitchStatus(parseCdnSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacNewFreshSwitchStatus: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacNewFreshSwitchOn() {
        int newFreshSwitchStatus;
        int i = 0;
        try {
            try {
                newFreshSwitchStatus = getIntProperty(557849180);
                LogUtils.debug(TAG, "getIntProperty:" + newFreshSwitchStatus, false);
            } catch (Exception e) {
                LogUtils.e(TAG, "getHvacNewFreshSwitchStatus: " + e.getMessage(), false);
            }
        } catch (Exception unused) {
            newFreshSwitchStatus = this.mCarManager.getNewFreshSwitchStatus();
            LogUtils.i(TAG, "isHvacNewFreshSwitchOn:" + newFreshSwitchStatus, false);
        }
        i = newFreshSwitchStatus;
        return parseHvacStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public void setHvacNIVent(boolean enable) {
        LogUtils.e(TAG, "setHvacNIVent: " + enable, false);
        try {
            this.mCarManager.setHvacRearWindLessSwitch(parseCdnSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setHvacNIVent: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController
    public boolean isHvacNIVentOn() {
        int hvacRearWindLessSwitch;
        int i = 0;
        try {
            try {
                hvacRearWindLessSwitch = getIntProperty(557849181);
                LogUtils.debug(TAG, "getIntProperty:" + hvacRearWindLessSwitch, false);
            } catch (Exception e) {
                LogUtils.e(TAG, "isHvacNIVentOn: " + e.getMessage(), false);
            }
        } catch (Exception unused) {
            hvacRearWindLessSwitch = this.mCarManager.getHvacRearWindLessSwitch();
            LogUtils.i(TAG, "isHvacNIVentOn:" + hvacRearWindLessSwitch, false);
        }
        i = hvacRearWindLessSwitch;
        return parseHvacStatus(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleHvacRearPowerModeChanged(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacRearPowerModeChanged(parseHvacStatus(state));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleRearTempDriverUpdate(float temp) {
        if (temp > 32.0f) {
            temp = 32.0f;
        } else if (temp < 18.0f) {
            temp = 18.0f;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacRearTempDrvChanged(temp);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleRearTempPsnUpdate(float temp) {
        if (temp > 32.0f) {
            temp = 32.0f;
        } else if (temp < 18.0f) {
            temp = 18.0f;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacRearTempPsnChanged(temp);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleThirdRowTempDriverUpdate(float temp) {
        if (temp > 32.0f) {
            temp = 32.0f;
        } else if (temp < 18.0f) {
            temp = 18.0f;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacThirdRowTempChanged(temp);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleRearAutoModeUpdate(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacRearAutoModeChanged(parseHvacStatus(status));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleRearWindSpeedLevelUpdate(int level) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacRearWindSpeedLevelChanged(level);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleThirdRowWindBlowModeUpdate(int mode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacThirdRowWindBlowModeChanged(mode);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleNewFreshSwitchUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacNewFreshSwitchChanged(parseHvacStatus(state));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleNIVentUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IHvacController.Callback) it.next()).onHvacNIVentChanged(parseHvacStatus(state));
            }
        }
    }
}
