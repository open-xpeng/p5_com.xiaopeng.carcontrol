package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.llu.CarLluManager;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.ILluController;
import com.xiaopeng.carcontrol.model.DebugFuncModel;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.xuimanager.lightlanuage.LightLanuageManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class LluController extends BaseCarController<CarLluManager, ILluController.Callback> implements ILluController {
    private static final String KEY_SAYHI_SW = "isSayHiEnable";
    private static final String TAG = "LluController";
    private final CarLluManager.CarLluEventCallback mCarLluEventCallback;
    private LightLanuageManager mLluManager;

    private static int parseCduCmd(boolean enable) {
        return enable ? 1 : 0;
    }

    private static boolean parseLluEffectSw(int status) {
        return status != 0;
    }

    private static boolean parseLluStatus(int status) {
        return status == 1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public boolean dependOnXuiManager() {
        return true;
    }

    private LluController(Car carClient) {
        this.mCarLluEventCallback = new CarLluManager.CarLluEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.LluController.1
            public void onErrorEvent(int propertyId, int zone) {
            }

            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                LogUtils.i(LluController.TAG, "onChangeEvent: " + carPropertyValue, false);
                LluController.this.handleCarEventsUpdate(carPropertyValue);
            }
        };
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$LluController$9Q6H4K8Zx-fSg_On8qA7NHrVQbA
            @Override // java.lang.Runnable
            public final void run() {
                LluController.this.lambda$new$0$LluController();
            }
        });
    }

    public /* synthetic */ void lambda$new$0$LluController() {
        final ContentResolver contentResolver = App.getInstance().getContentResolver();
        contentResolver.registerContentObserver(Settings.System.getUriFor(KEY_SAYHI_SW), true, new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.carmanager.impl.LluController.2
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                LluController.this.handleLluSayHiSw(Settings.System.getInt(contentResolver, LluController.KEY_SAYHI_SW, 0) == 1);
            }
        });
    }

    /* loaded from: classes.dex */
    public static class LluControllerFactory {
        public static LluController createCarController(Car carClient) {
            return new LluController(carClient);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarLluManager) carClient.getCarManager(CarClientWrapper.XP_LLU_SERVICE);
            if (this.mCarManager != 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarLluEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.d(TAG, "Init end");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
        String str = TAG;
        LogUtils.d(str, "Init xui manager start");
        try {
            LightLanuageManager lightLanguageManager = XuiClientWrapper.getInstance().getLightLanguageManager();
            this.mLluManager = lightLanguageManager;
            if (lightLanguageManager != null) {
                this.mLluManager.registerListener(new LightLanuageManager.EventListener() { // from class: com.xiaopeng.carcontrol.carmanager.impl.LluController.3
                    public void onUpgrade(int effectName, int effectMode) {
                        LogUtils.i(LluController.TAG, "onLightEffectFinishEvent effectName:" + effectName + " effectMode:" + effectMode, false);
                        LluController.this.handleXuiLluEffectSetFinish(effectName, effectMode);
                    }

                    public void onStart(String effectName, String effectType) {
                        LogUtils.i(LluController.TAG, "onLightEffectShowStartEvent effectName:" + effectName + " effectType:" + effectType, false);
                        LluController.this.handleLluPreviewShowStart(effectName, effectType);
                    }

                    public void onStop(String effectName, String effectType) {
                        LogUtils.i(LluController.TAG, "onLightEffectShowStopEvent effectName:" + effectName + " effectType:" + effectType, false);
                        LluController.this.handleLluPreviewShowStop(effectName, effectType);
                    }

                    public void onFinish(String effectName, String effectType) {
                        LogUtils.i(LluController.TAG, "onLightEffectShowFinishEvent effectName:" + effectName + " effectType:" + effectType, false);
                        LluController.this.handleLluPreviewShowFinish(effectName, effectType);
                    }

                    public void onError(String effectName, int errorCode) {
                        LogUtils.i(LluController.TAG, "onLightEffectShowError effectName:" + effectName + ", error code " + errorCode, false);
                        LluController.this.handleLluPreviewShowError(effectName, errorCode);
                    }
                });
            } else {
                LogUtils.e(str, "XuiSmartManager is null", false);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.d(TAG, "Init xui manager end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        if (this.mCarConfig.isSupportLlu()) {
            arrayList.add(557847625);
            arrayList.add(557854218);
            arrayList.add(557854220);
            if (this.mIsMainProcess) {
                arrayList.add(557847626);
                arrayList.add(557847630);
                arrayList.add(557847631);
            } else {
                arrayList.add(557847619);
                arrayList.add(557847627);
                arrayList.add(557847632);
            }
        }
        return arrayList;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarLluEventCallback);
            } catch (CarNotConnectedException e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 557847625:
                handleLluSwUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847626:
                handleLluWakeWaitUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847627:
                handleLluShowOffUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847630:
                handleLluSleepUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847631:
                handleLluChargingUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557847632:
                handleLluPhotoUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557854218:
                handleLluAwakeMode(((Integer) getValue(value)).intValue());
                return;
            case 557854220:
                handleLluSleepMode(((Integer) getValue(value)).intValue());
                return;
            default:
                LogUtils.w(TAG, "handle unknown event: " + value);
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setLluEnable(boolean enable) {
        try {
            this.mCarManager.setMcuLluEnableStatus(parseCduCmd(enable));
            LightLanuageManager lightLanuageManager = this.mLluManager;
            if (lightLanuageManager != null) {
                lightLanuageManager.setLightEffectEnable(enable);
            }
            if (this.mIsMainProcess) {
                this.mDataSync.setLluSw(enable);
            } else {
                CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.LLU_SW, enable);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setLluEnable: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public boolean isLluEnabled() {
        int i;
        try {
            try {
                i = getIntProperty(557847625);
            } catch (Exception unused) {
                i = 0;
            }
        } catch (Exception unused2) {
            i = this.mCarManager.getMcuLluEnableStatus();
        }
        LogUtils.i(TAG, "isLluEnabled: " + i, false);
        return parseLluStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setLluAwakeMode(int mode) {
        try {
            this.mCarManager.setLluWakeWaitMode(mode);
        } catch (Exception e) {
            LogUtils.e(TAG, "setLluAwakeMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public int getLluAwakeMode() {
        try {
            try {
                return getIntProperty(557854218);
            } catch (Exception e) {
                LogUtils.e(TAG, "getLluAwakeMode: " + e.getMessage(), false);
                if (this.mIsMainProcess) {
                    return DebugFuncModel.getInstance().getLluAwakeMode();
                }
                return 1;
            }
        } catch (Exception unused) {
            return this.mCarManager.getLluWakeWaitMode();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setLluSleepMode(int mode) {
        try {
            this.mCarManager.setLluSleepMode(mode);
        } catch (Exception e) {
            LogUtils.e(TAG, "setLluSleepMode: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public int getLluSleepMode() {
        try {
            try {
                return getIntProperty(557854220);
            } catch (Exception e) {
                LogUtils.e(TAG, "getLluSleepMode: " + e.getMessage(), false);
                if (this.mIsMainProcess) {
                    return DebugFuncModel.getInstance().getLluSleepMode();
                }
                return 1;
            }
        } catch (Exception unused) {
            return this.mCarManager.getLluSleepMode();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void stopLluEffectPreview() {
        if (this.mLluManager != null) {
            LogUtils.d(TAG, "stopLluEffectPreview", false);
            try {
                this.mLluManager.stopEffect();
            } catch (Exception e) {
                LogUtils.e(TAG, "stopLluEffectPreview: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public int setLluEffectPreview(String effect, String filename) {
        return setLluEffectPreview(effect, filename, -1);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public int setLluEffectPreview(String effect, String filename, int times) {
        if (this.mLluManager == null) {
            LogUtils.e(TAG, "setLluEffectPreview: mXuiSmartManager==null");
            return -1;
        }
        try {
            if (ILluController.LLU_EFFECT_DANCE.equals(effect)) {
                if (TextUtils.isEmpty(filename)) {
                    return this.mLluManager.playEffect(ILluController.LLU_EFFECT_DANCE, times);
                }
                return this.mLluManager.playEffect(filename, times);
            }
            this.mLluManager.playEffect(effect, times);
            return 0;
        } catch (Exception e) {
            LogUtils.e(TAG, "setLluEffectPreview: " + e.getMessage(), false);
            return -1;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public String getRunningLluEffectName() {
        LightLanuageManager lightLanuageManager = this.mLluManager;
        if (lightLanuageManager != null) {
            try {
                return lightLanuageManager.getRunningEffect();
            } catch (Exception e) {
                LogUtils.e(TAG, "getRunningLluEffectName: " + e.getMessage(), false);
                return null;
            }
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setLluLockUnlockEleSw(boolean enable) {
        try {
            this.mCarManager.setLluLockUnlockSocDspSwitch(parseCduCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setLluLockUnlockEleSw: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public boolean isLluLockUnlockEleEnabled() {
        try {
            try {
                if (getIntProperty(557847619) != 1) {
                    return false;
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "isLluLockUnlockEleEnabled: " + e.getMessage(), false);
                return false;
            }
        } catch (Exception unused) {
            if (this.mCarManager.getLluLockUnlockSocDspSwitchState() != 1) {
                return false;
            }
        }
        return true;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setLluWakeWaitSwitch(boolean enable, boolean sendCmd, boolean needSave) {
        if (sendCmd) {
            try {
                this.mCarManager.setMcuLluWakeWaitSwitch(parseCduCmd(enable));
            } catch (Exception e) {
                LogUtils.e(TAG, "setLluWakeWaitSwitch: " + e.getMessage(), false);
            }
        }
        if (this.mIsMainProcess && needSave) {
            this.mDataSync.setLluUnlockSw(enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public boolean isLluWakeWaitEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557847626);
            } catch (Exception unused) {
                i = 0;
            }
        } catch (Exception unused2) {
            i = this.mCarManager.getMcuLluWakeWaitSwitch();
        }
        LogUtils.i(TAG, "isLluWakeWaitEnable: " + i, false);
        return parseLluEffectSw(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public boolean getLluWakeWaitSw() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getLluUnlockSw();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setLluShowOffSwitch(boolean enable) {
        try {
            this.mCarManager.setLluShowOffSwitch(parseCduCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setLluShowOffSwitch: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public boolean isLluShowOffEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557847627);
            } catch (Exception e) {
                LogUtils.e(TAG, "isLluShowOffEnable: " + e.getMessage(), false);
                i = 1;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getLluShowOffSwitch();
        }
        return parseLluStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setLluChargingSwitch(boolean enable, boolean sendCmd, boolean needSave) {
        if (sendCmd) {
            try {
                this.mCarManager.setMcuLluChargingSwitch(parseCduCmd(enable));
            } catch (Exception e) {
                LogUtils.e(TAG, "setLluChargingSwitch: " + e.getMessage(), false);
            }
        }
        if (this.mIsMainProcess && needSave) {
            this.mDataSync.setLluChargeSw(enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public boolean isLluChargingEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557847631);
            } catch (Exception unused) {
                i = 0;
            }
        } catch (Exception unused2) {
            i = this.mCarManager.getMcuLluChargingSwitch();
        }
        LogUtils.i(TAG, "isLluChargingEnable: " + i, false);
        return parseLluEffectSw(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public boolean getLluChargingSw() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getLluChargeSw();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setLluSleepSwitch(boolean enable, boolean sendCmd, boolean needSave) {
        if (sendCmd) {
            try {
                this.mCarManager.setMcuLluSleepSwitch(parseCduCmd(enable));
            } catch (Exception e) {
                LogUtils.e(TAG, "setLluSleepSwitch: " + e.getMessage(), false);
            }
        }
        if (this.mIsMainProcess && needSave) {
            this.mDataSync.setLluLockSw(enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public boolean isLluSleepEnable() {
        int i;
        try {
            try {
                i = getIntProperty(557847630);
            } catch (Exception unused) {
                i = 0;
            }
        } catch (Exception unused2) {
            i = this.mCarManager.getMcuLluSleepSwitch();
        }
        LogUtils.i(TAG, "isLluSleepEnable: " + i, false);
        return parseLluEffectSw(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public boolean getLluSleepSw() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getLluLockSw();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setLluPhotoSwitch(boolean enable) {
        try {
            this.mCarManager.setLluPhotoSwitch(parseCduCmd(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "setLluPhotoSwitch: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public boolean isLluPhotoEnable() {
        int i = 0;
        try {
            try {
                i = getIntProperty(557847632);
            } catch (Exception e) {
                LogUtils.e(TAG, "isLluPhotoEnable: " + e.getMessage(), false);
            }
        } catch (Exception unused) {
            i = this.mCarManager.getLluPhotoSwitch();
        }
        return parseLluStatus(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setLangLightMusicEffect(String effectName) {
        try {
            LightLanuageManager lightLanuageManager = this.mLluManager;
            if (lightLanuageManager != null) {
                lightLanuageManager.setRhythmEffect(effectName);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setLangLightMusicEffect: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setPause(boolean pause) {
        try {
            LightLanuageManager lightLanuageManager = this.mLluManager;
            if (lightLanuageManager != null) {
                lightLanuageManager.stopEffect();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setPause: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setLluEffect(int effectName, int effectMode) {
        try {
            LightLanuageManager lightLanuageManager = this.mLluManager;
            if (lightLanuageManager != null) {
                lightLanuageManager.setMcuEffect(effectName, effectMode);
                handleXuiLluEffectSetFinish(effectName, effectMode);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setLluEffect: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public int getLluEffect(int effectName) {
        try {
            LightLanuageManager lightLanuageManager = this.mLluManager;
            if (lightLanuageManager != null) {
                return lightLanuageManager.getMcuEffect(effectName);
            }
            return 1;
        } catch (Exception e) {
            LogUtils.e(TAG, "getLluEffect: " + e.getMessage(), false);
            return 1;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setLluSpeedLimitCfg(int speed) {
        try {
            this.mCarManager.setLluSpeedLimitCfg(speed);
        } catch (Exception e) {
            LogUtils.e(TAG, "setLluSpeedLimitCfg: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public int getLluSpeedLimitCfg() {
        try {
            try {
                return getIntProperty(557847622);
            } catch (Exception e) {
                LogUtils.e(TAG, "getLluSpeedLimitCfg: " + e.getMessage(), false);
                return 0;
            }
        } catch (Exception unused) {
            return this.mCarManager.getLluSpeedLimitCfg();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public boolean isSayHiEnabled() {
        boolean z;
        try {
            z = this.mLluManager.getSayhiEffectEnable();
        } catch (Throwable unused) {
            z = false;
        }
        LogUtils.i(TAG, "isSayHiEnabled: " + z);
        return z;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public void setSayHiEnable(boolean enable) {
        LightLanuageManager lightLanuageManager = this.mLluManager;
        if (lightLanuageManager != null) {
            try {
                lightLanuageManager.setSayhiEffectEnable(enable);
            } catch (Exception e) {
                LogUtils.e(TAG, "setSayHiEnable: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController
    public int isLightDanceAvailable() {
        int danceEffectRunnable;
        LightLanuageManager lightLanuageManager = this.mLluManager;
        if (lightLanuageManager != null) {
            try {
                danceEffectRunnable = lightLanuageManager.getDanceEffectRunnable();
            } catch (Exception unused) {
            }
            LogUtils.i(TAG, "isLightDanceAvailable result=" + danceEffectRunnable);
            return danceEffectRunnable;
        }
        danceEffectRunnable = 0;
        LogUtils.i(TAG, "isLightDanceAvailable result=" + danceEffectRunnable);
        return danceEffectRunnable;
    }

    private void mockLluProperty() {
        this.mCarPropertyMap.put(557847625, new CarPropertyValue<>(557847625, Boolean.valueOf(this.mDataSync != null ? this.mDataSync.getLluSw() : true)));
        this.mCarPropertyMap.put(557854218, new CarPropertyValue<>(557854218, 1));
        this.mCarPropertyMap.put(557854220, new CarPropertyValue<>(557854220, 1));
        this.mCarPropertyMap.put(557847619, new CarPropertyValue<>(557847619, false));
        this.mCarPropertyMap.put(557847626, new CarPropertyValue<>(557847626, 1));
        this.mCarPropertyMap.put(557847627, new CarPropertyValue<>(557847627, 1));
        this.mCarPropertyMap.put(557847630, new CarPropertyValue<>(557847630, 1));
        this.mCarPropertyMap.put(557847631, new CarPropertyValue<>(557847631, 1));
        this.mCarPropertyMap.put(557847632, new CarPropertyValue<>(557847632, 0));
    }

    private void handleLluSwUpdate(int status) {
        boolean parseLluStatus = parseLluStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluEnableChanged(parseLluStatus);
            }
        }
    }

    private void handleLluAwakeMode(int mode) {
        if (this.mIsMainProcess) {
            DebugFuncModel.getInstance().setLluAwakeMode(mode);
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluAwakeModeChanged(mode);
            }
        }
    }

    private void handleLluSleepMode(int mode) {
        if (this.mIsMainProcess) {
            DebugFuncModel.getInstance().setLluSleepMode(mode);
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluSleepModeChanged(mode);
            }
        }
    }

    private void handleLluLinkToShowEleUpdate(int status) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluLinkToShowEleChanged(parseLluStatus(status));
            }
        }
    }

    private void handleLluPhotoUpdate(int enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluPhotoChanged(parseLluStatus(enable));
            }
        }
    }

    private void handleLluChargingUpdate(int enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluChargingChanged(parseLluEffectSw(enable));
            }
        }
    }

    private void handleLluSleepUpdate(int enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluSleepChanged(parseLluEffectSw(enable));
            }
        }
    }

    private void handleLluShowOffUpdate(int enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluShowOffChanged(parseLluStatus(enable));
            }
        }
    }

    private void handleLluWakeWaitUpdate(int enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluWakeWaitChanged(parseLluEffectSw(enable));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleXuiLluEffectSetFinish(int effectName, int effectMode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onXuiLluEffectSetFinish(effectName, effectMode);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLluPreviewShowError(String effectName, int errCode) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluPreviewShowError(effectName, errCode);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLluPreviewShowFinish(String effectName, String effectType) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluPreviewShowFinish(effectName, effectType);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLluPreviewShowStop(String effectName, String effectType) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluPreviewShowStop(effectName, effectType);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLluPreviewShowStart(String effectName, String effectType) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluPreviewShowStart(effectName, effectType);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLluSayHiSw(boolean enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ILluController.Callback) it.next()).onLluSayHiSwChanged(enable);
            }
        }
    }
}
