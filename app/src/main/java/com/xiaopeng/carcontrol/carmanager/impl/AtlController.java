package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.atl.CarAtlManager;
import android.database.ContentObserver;
import android.provider.Settings;
import android.util.Pair;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.carmanager.ambient.AmbientColor;
import com.xiaopeng.carcontrol.carmanager.ambient.AmbientEventListener;
import com.xiaopeng.carcontrol.carmanager.ambient.AmbientManagerCompat;
import com.xiaopeng.carcontrol.carmanager.controller.IAtlController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.light.AtlColorType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class AtlController extends BaseCarController<CarAtlManager, IAtlController.Callback> implements IAtlController {
    private static final String ATL_SPEAKER_SW = "atl_speaker";
    public static final String PARTITION_ALL = "all";
    private static final String TAG = "AtlController";
    protected AmbientManagerCompat mXuiAtlManager;
    private final CarAtlManager.CarAtlEventCallback mCarAtlEventCallback = new CarAtlManager.CarAtlEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.AtlController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.i(AtlController.TAG, "onChangeEvent: " + carPropertyValue, false);
            AtlController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };
    private AmbientEventListener mAtlManagerCallback = new AmbientEventListener() { // from class: com.xiaopeng.carcontrol.carmanager.impl.AtlController.2
        @Override // com.xiaopeng.carcontrol.carmanager.ambient.AmbientEventListener
        public void onChangeMode(String partition, String mode) {
            LogUtils.i(AtlController.TAG, "onChangeMode: partition:" + partition + ",mode:" + mode, false);
            AtlController.this.handleEffectTypeChanged(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.ambient.AmbientEventListener
        public void onChangeColorType(String partition, String type) {
            try {
                LogUtils.i(AtlController.TAG, "onChangeColorType:partition:" + partition + ",type:" + type, false);
                String ambientLightMode = AtlController.this.mXuiAtlManager.getAmbientLightMode(partition);
                if (AtlColorType.Mono.match(type)) {
                    AtlController.this.handleDoubleColorEnableChanged(ambientLightMode, false);
                } else if (AtlColorType.Double.match(type)) {
                    AtlController.this.handleDoubleColorEnableChanged(ambientLightMode, true);
                }
            } catch (Exception e) {
                LogUtils.e(AtlController.TAG, (String) null, (Throwable) e, false);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.ambient.AmbientEventListener
        public void onChangeMonoColor(String partition, AmbientColor color) {
            try {
                LogUtils.i(AtlController.TAG, "onChangeMonoColor:partition:" + partition + ",color:" + color, false);
                AtlController.this.handleMonoColorChanged(AtlController.this.mXuiAtlManager.getAmbientLightMode(partition), color.predef);
            } catch (Exception e) {
                LogUtils.e(AtlController.TAG, (String) null, (Throwable) e, false);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.ambient.AmbientEventListener
        public void onChangeDoubleColor(String partition, Pair<AmbientColor, AmbientColor> color) {
            try {
                LogUtils.i(AtlController.TAG, "onChangeDoubleColor:partition:" + partition + ",color:" + color, false);
                AtlController.this.handleDoubleColorChanged(AtlController.this.mXuiAtlManager.getAmbientLightMode(partition), ((AmbientColor) color.first).predef, ((AmbientColor) color.second).predef);
            } catch (Exception e) {
                LogUtils.e(AtlController.TAG, (String) null, (Throwable) e, false);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.ambient.AmbientEventListener
        public void onChangeBright(String partition, int bright) {
            LogUtils.i(AtlController.TAG, "handleManualBrightUpdate/onChangeBright:" + partition + "," + bright);
            AtlController.this.handleManualBrightUpdate(bright);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.ambient.AmbientEventListener
        public void onErrorPlay(String partition, String effect) {
            LogUtils.i(AtlController.TAG, "onErrorPlay:" + partition + "," + effect);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.ambient.AmbientEventListener
        public void onErrorStop(String partition, String effect) {
            LogUtils.i(AtlController.TAG, "onErrorStop:" + partition + "," + effect);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.ambient.AmbientEventListener
        public void onErrorSet(String partition, String mode) {
            LogUtils.i(AtlController.TAG, "onErrorSet:" + partition + "," + mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.ambient.AmbientEventListener
        public void onErrorSub(String partition, String mode) {
            LogUtils.i(AtlController.TAG, "onErrorSub:" + partition + "," + mode);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public boolean dependOnXuiManager() {
        return true;
    }

    public AtlController(Car carClient) {
        if (!CarBaseConfig.getInstance().isSupportAtl() || CarBaseConfig.getInstance().isSupportFullAtl()) {
            return;
        }
        App.getInstance().getContentResolver().registerContentObserver(Settings.System.getUriFor(IAtlController.ATL_SW_KEY), true, new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.carmanager.impl.AtlController.3
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                int i = Settings.System.getInt(App.getInstance().getContentResolver(), IAtlController.ATL_SW_KEY, 1);
                LogUtils.i(AtlController.TAG, "handle atl sw update by provider: isAmbientLightOpen=" + i);
                AtlController.this.handleAtlSwUpdate(i);
            }
        });
    }

    /* loaded from: classes.dex */
    public static class AtlControllerFactory {
        public static AtlController createCarController(Car carClient) {
            return new AtlController(carClient);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarAtlManager) carClient.getCarManager(CarClientWrapper.XP_ATL_SERVICE);
            if (this.mCarManager != 0 && this.mPropertyIds.size() > 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarAtlEventCallback);
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
        AmbientManagerCompat atlManager = XuiClientWrapper.getInstance().getAtlManager();
        this.mXuiAtlManager = atlManager;
        if (atlManager != null) {
            atlManager.registerListener(this.mAtlManagerCallback);
        } else {
            LogUtils.e(str, "mXuiAtlManager is null", false);
        }
        LogUtils.d(str, "Init xui manager end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        addExtPropertyIds(arrayList);
        return arrayList;
    }

    protected void addExtPropertyIds(List<Integer> propertyIds) {
        if (this.mCarConfig.isSupportAtl()) {
            if ((!this.mCarConfig.isSupportUnity3D() || this.mIsMainProcess) && (this.mCarConfig.isSupportUnity3D() || !this.mIsMainProcess)) {
                return;
            }
            if (this.mCarConfig.isSupportFullAtl()) {
                propertyIds.add(557848586);
            }
            propertyIds.add(557848581);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        try {
            if (this.mCarManager != 0 && this.mPropertyIds.size() > 0) {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarAtlEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        AmbientManagerCompat ambientManagerCompat = this.mXuiAtlManager;
        if (ambientManagerCompat != null) {
            ambientManagerCompat.unregisterListener(this.mAtlManagerCallback);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        int propertyId = value.getPropertyId();
        if (propertyId == 557848581) {
            handleAutoBrightUpdate(((Integer) getValue(value)).intValue());
        } else if (propertyId == 557848586) {
            handleAtlSwUpdate(((Integer) getValue(value)).intValue());
        } else {
            LogUtils.w(TAG, "handle unknown event: " + value);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public boolean isAtlSwEnabled() {
        boolean atlSw;
        int i;
        if (this.mCarConfig.isSupportFullAtl()) {
            try {
                try {
                    i = getIntProperty(557848586);
                } catch (Exception unused) {
                    i = this.mCarManager.getAtlOpen();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "isAtlSwEnabled: " + e.getMessage(), false);
                i = 1;
            }
            return i == 1;
        }
        try {
            AmbientManagerCompat ambientManagerCompat = this.mXuiAtlManager;
            if (ambientManagerCompat != null) {
                atlSw = ambientManagerCompat.getAmbientLightEnable();
            } else {
                atlSw = this.mDataSync.getAtlSw();
            }
            return atlSw;
        } catch (Exception e2) {
            LogUtils.e(TAG, "isAtlSwEnabled: " + e2.getMessage(), false);
            return this.mDataSync.getAtlSw();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public void setAtlSpeakerSwEnable(final boolean enable) {
        LogUtils.d(TAG, "setAtlSpeakerSwEnable, enable= " + enable, false);
        if (this.mCarConfig.isSupportAtl()) {
            Settings.System.putInt(App.getInstance().getContentResolver(), ATL_SPEAKER_SW, enable ? 1 : 0);
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$AtlController$4zvthctNk2vnxn9QkYjuFdrABsw
                @Override // java.lang.Runnable
                public final void run() {
                    AtlController.this.lambda$setAtlSpeakerSwEnable$0$AtlController(enable);
                }
            });
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public boolean isAtlSpeakerSwEnabled() {
        int i = this.mCarConfig.isSupportAtl() ? Settings.System.getInt(App.getInstance().getContentResolver(), ATL_SPEAKER_SW, 0) : 0;
        LogUtils.d(TAG, "isAtlSpeakerSwEnabled, value = " + i, false);
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public void setAtlSwEnable(boolean enable) {
        LogUtils.d(TAG, "setAtlSwEnable, enable = " + enable, false);
        if (this.mIsMainProcess) {
            this.mDataSync.setAtlSw(enable);
        } else {
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.ATL_SW, enable);
        }
        try {
            AmbientManagerCompat ambientManagerCompat = this.mXuiAtlManager;
            if (ambientManagerCompat != null) {
                ambientManagerCompat.setAmbientLightEnable(enable);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setAtlSwEnable: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public boolean isAtlAutoBrightEnabled() {
        int autoBrightness;
        try {
            try {
                autoBrightness = getIntProperty(557848581);
            } catch (Exception e) {
                LogUtils.e(TAG, "isAtlAutoBrightEnabled: " + e.getMessage(), false);
                if (this.mIsMainProcess) {
                    return this.mDataSync.getAtlAutoBrightSw();
                }
                return true;
            }
        } catch (Exception unused) {
            autoBrightness = this.mCarManager.getAutoBrightness();
        }
        return autoBrightness == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public void setAtlAutoBrightEnable(boolean enable) {
        try {
            LogUtils.i(TAG, "setAtlAutoBrightEnable:enable:" + enable, false);
            this.mCarManager.setAutoBrightness(enable ? 1 : 0);
        } catch (Exception e) {
            LogUtils.e(TAG, "setAtlAutoBrightEnable: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public int getAtlBrightness() {
        int i = 100;
        try {
            AmbientManagerCompat ambientManagerCompat = this.mXuiAtlManager;
            if (ambientManagerCompat != null) {
                i = ambientManagerCompat.getAmbientLightBright("all");
            } else if (this.mIsMainProcess) {
                i = this.mDataSync.getAtlBright();
            }
            return i;
        } catch (Exception e) {
            LogUtils.e(TAG, "getAtlBrightness: " + e.getMessage(), false);
            return this.mIsMainProcess ? this.mDataSync.getAtlBright() : i;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public void setAtlBrightness(int brightness, boolean force) {
        try {
            if (this.mIsMainProcess) {
                this.mDataSync.setAtlBright(brightness);
            } else {
                CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.ATL_BRIGHTNESS, brightness);
            }
            if (force) {
                if (!isAtlSwEnabled()) {
                    setAtlSwEnable(true);
                }
                AmbientManagerCompat ambientManagerCompat = this.mXuiAtlManager;
                if (ambientManagerCompat != null) {
                    ambientManagerCompat.setAmbientLightBright("all", brightness);
                    return;
                }
                return;
            }
            AmbientManagerCompat ambientManagerCompat2 = this.mXuiAtlManager;
            if (ambientManagerCompat2 != null) {
                ambientManagerCompat2.setAmbientLightBright("all", brightness);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setAtlBrightness: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public String getAtlEffect() {
        try {
            AmbientManagerCompat ambientManagerCompat = this.mXuiAtlManager;
            return ambientManagerCompat != null ? ambientManagerCompat.getAmbientLightMode("all") : "stable_effect";
        } catch (Exception e) {
            LogUtils.e(TAG, "getAtlEffect: " + e.getMessage(), false);
            return this.mIsMainProcess ? this.mDataSync.getAtlEffect() : "stable_effect";
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public void setAtlEffect(String effect, boolean force) {
        setAtlEffect(effect, force, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public void setAtlEffect(String effect, boolean force, boolean needSave) {
        if (needSave) {
            if (this.mIsMainProcess) {
                this.mDataSync.setAtlEffect(effect);
            } else {
                CarControl.PrivateControl.putString(App.getInstance().getContentResolver(), CarControl.PrivateControl.ATL_EFFECT, effect);
            }
        }
        if (this.mXuiAtlManager != null) {
            if (force) {
                try {
                    if (!isAtlSwEnabled()) {
                        setAtlSwEnable(true);
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "setAtlEffect: " + e.getMessage(), false);
                    return;
                }
            }
            this.mXuiAtlManager.setAmbientLightMode("all", effect);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public boolean isAtlDualColor(String effect) {
        try {
            AmbientManagerCompat ambientManagerCompat = this.mXuiAtlManager;
            if (ambientManagerCompat != null) {
                return AtlColorType.Double.match(ambientManagerCompat.getAmbientLightColorType("all"));
            }
            LogUtils.i(TAG, "getDoubleThemeColorEnable:false, effect:" + effect, false);
            return false;
        } catch (Exception e) {
            LogUtils.e(TAG, "isAtlDualColor: " + e.getMessage(), false);
            return this.mIsMainProcess && this.mDataSync.getAtlDualColorSw();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public void setAtlDualColor(String effect, boolean dualColor, boolean force) {
        if (this.mIsMainProcess) {
            this.mDataSync.setAtlDualColorSw(dualColor);
        } else {
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.ATL_DUAL_COLOR_SW, dualColor);
        }
        if (this.mXuiAtlManager != null) {
            if (force) {
                try {
                    if (!isAtlSwEnabled()) {
                        setAtlSwEnable(true);
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "setAtlDualColor: " + e.getMessage(), false);
                    return;
                }
            }
            if (dualColor) {
                this.mXuiAtlManager.setAmbientLightColorType("all", AtlColorType.Double.toAtlCommand());
            } else {
                this.mXuiAtlManager.setAmbientLightColorType("all", AtlColorType.Mono.toAtlCommand());
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public int getAtlSingleColor(String effect) {
        try {
            AmbientManagerCompat ambientManagerCompat = this.mXuiAtlManager;
            if (ambientManagerCompat != null) {
                AmbientColor ambientLightMonoColor = ambientManagerCompat.getAmbientLightMonoColor("all");
                LogUtils.i(TAG, "getAtlSingleColor:" + effect + ", color:" + (ambientLightMonoColor != null ? ambientLightMonoColor.toHexString() : "null"), false);
                if (ambientLightMonoColor != null) {
                    return ambientLightMonoColor.predef;
                }
                return 0;
            }
            return 0;
        } catch (Exception e) {
            LogUtils.e(TAG, "getAtlSingleColor: " + e.getMessage(), false);
            return this.mIsMainProcess ? this.mDataSync.getAtlSingleColor() : 14;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public void setAtlSingleColor(String effect, int color, boolean force) {
        setAtlSingleColor(effect, color, force, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public void setAtlSingleColor(String effect, int color, boolean force, boolean needSave) {
        LogUtils.i(TAG, "setAtlSingleColor:" + effect + ", color:" + color + ", force:" + force);
        if (color < 1 || color > 20) {
            return;
        }
        if (needSave) {
            if (this.mIsMainProcess) {
                this.mDataSync.setAtlSingleColor(color);
            } else {
                CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.ATL_SINGLE_COLOR, color);
            }
        }
        if (this.mXuiAtlManager != null) {
            if (force) {
                try {
                    if (!isAtlSwEnabled()) {
                        setAtlSwEnable(true);
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "setAtlSingleColor: " + e.getMessage(), false);
                    return;
                }
            }
            this.mXuiAtlManager.setAmbientLightMonoColor("all", new AmbientColor(false, Integer.toHexString(color)));
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public int getAtlDualFirstColor(String effect) {
        try {
            AmbientManagerCompat ambientManagerCompat = this.mXuiAtlManager;
            if (ambientManagerCompat != null) {
                int i = ((AmbientColor) ambientManagerCompat.getAmbientLightDoubleColor("all").first).predef;
                LogUtils.i(TAG, "getAtlDualFirstColor:" + effect + ", color:" + i, false);
                return i;
            }
            return 0;
        } catch (Exception e) {
            LogUtils.e(TAG, "getAtlDualFirstColor: " + e.getMessage(), false);
            return 1;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public int getAtlDualSecondColor(String effect) {
        try {
            AmbientManagerCompat ambientManagerCompat = this.mXuiAtlManager;
            if (ambientManagerCompat != null) {
                int i = ((AmbientColor) ambientManagerCompat.getAmbientLightDoubleColor("all").second).predef;
                LogUtils.i(TAG, "getAtlDualSecondColor:" + effect + ", color:" + i, false);
                return i;
            }
            return 0;
        } catch (Exception e) {
            LogUtils.e(TAG, "getAtlDualSecondColor: " + e.getMessage(), false);
            return 6;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public void setAtlDualColor(String effect, int color1, int color2, boolean force) {
        setAtlDualColor(effect, color1, color2, force, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public void setAtlDualColor(String effect, int color1, int color2, boolean force, boolean needSave) {
        String str = TAG;
        LogUtils.i(str, "setAtlDualColor:" + effect + ", color1:" + color1 + ", color2:" + color2 + ", force:" + force);
        if (color1 == 0 || color2 == 0) {
            LogUtils.w(str, "Invalid color, do not set to AmbientLightManager", false);
            return;
        }
        if (needSave) {
            if (this.mIsMainProcess) {
                this.mDataSync.setAtlDualColor(color1, color2);
            } else {
                CarControl.PrivateControl.putString(App.getInstance().getContentResolver(), CarControl.PrivateControl.ATL_DUAL_COLOR, color1 + "," + color2);
            }
        }
        if (this.mXuiAtlManager != null) {
            if (force) {
                try {
                    if (!isAtlSwEnabled()) {
                        setAtlSwEnable(true);
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "setAtlDualColor: " + e.getMessage(), false);
                    return;
                }
            }
            this.mXuiAtlManager.setAmbientLightDoubleColor("all", new Pair<>(new AmbientColor(false, Integer.toHexString(color1)), new AmbientColor(false, Integer.toHexString(color2))));
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAtlController
    public boolean isSupportDoubleThemeColor(String effectType) {
        try {
            AmbientManagerCompat ambientManagerCompat = this.mXuiAtlManager;
            if (ambientManagerCompat != null) {
                return ambientManagerCompat.isSupportDoubleThemeColor(effectType);
            }
            return false;
        } catch (Exception e) {
            LogUtils.e(TAG, "isSupportDoubleThemeColor: " + e.getMessage(), false);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAtlSwUpdate(int status) {
        boolean z = status == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAtlController.Callback) it.next()).onAtlSwChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleAtlSpeakerSwUpdate */
    public void lambda$setAtlSpeakerSwEnable$0$AtlController(boolean enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAtlController.Callback) it.next()).onAtlSpeakerSwChanged(enable);
            }
        }
    }

    private void handleAutoBrightUpdate(int status) {
        boolean z = status == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAtlController.Callback) it.next()).onAtlAutoBrightSwChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleManualBrightUpdate(int brightness) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAtlController.Callback) it.next()).onAtlManualBrightChanged(brightness);
            }
        }
    }

    protected void handleEffectTypeChanged(String type) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAtlController.Callback) it.next()).onAtlEffectTypeChanged(type);
            }
        }
    }

    protected void handleDoubleColorEnableChanged(String effectType, boolean enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAtlController.Callback) it.next()).onAtlDoubleColorEnableChanged(effectType, enable);
            }
        }
    }

    protected void handleMonoColorChanged(String effectType, int color) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAtlController.Callback) it.next()).onAtlMonoColorChanged(effectType, color);
            }
        }
    }

    protected void handleDoubleColorChanged(String effectType, int firstColor, int secondColor) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAtlController.Callback) it.next()).onAtlDoubleColorChanged(effectType, firstColor, secondColor);
            }
        }
    }
}
