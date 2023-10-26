package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.avas.CarAvasManager;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IAvasController;
import com.xiaopeng.carcontrol.carmanager.impl.oldarch.AvasOldController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.model.DebugFuncModel;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.xuimanager.smart.SmartManager;
import com.xiaopeng.xuimanager.soundresource.SoundResourceManager;
import com.xiaopeng.xuimanager.soundresource.data.BootSoundResource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class AvasController extends BaseCarController<CarAvasManager, IAvasController.Callback> implements IAvasController {
    private static final String AVAS_LOUD_SPEAKER_SW = "avas_speaker";
    private static final String SAY_HI_AVAS_SW = "avasGearAvhSayhiEnable";
    protected static final String TAG = "AvasController";
    private Integer mBootEffectBeforeSw = 1;
    private final CarAvasManager.CarAvasEventCallback mCarAvasEventCallback = new CarAvasManager.CarAvasEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.AvasController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            if (carPropertyValue.getPropertyId() != 557855240) {
                LogUtils.i(AvasController.TAG, "onAvasChangeEvent: " + carPropertyValue, false);
            }
            AvasController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };
    private ContentObserver mContentObserver;
    private SoundResourceManager mSoundResourceManager;
    private SmartManager mXuiSmartManager;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public boolean dependOnXuiManager() {
        return true;
    }

    public AvasController(Car carClient) {
        if (this.mIsMainProcess) {
            return;
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$AvasController$x4MQBIul6B1-hVTX2-caoSNWxoQ
            @Override // java.lang.Runnable
            public final void run() {
                AvasController.this.lambda$new$0$AvasController();
            }
        });
    }

    public /* synthetic */ void lambda$new$0$AvasController() {
        this.mBootEffectBeforeSw = Integer.valueOf(CarControl.PrivateControl.getInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.BOOT_EFFECT_BEFORE_SW, 1));
        registerContentObserver();
    }

    /* loaded from: classes.dex */
    public static class AvasControllerFactory {
        public static AvasController createCarController(Car carClient) {
            if (CarBaseConfig.getInstance().isNewAvasArch()) {
                return new AvasController(carClient);
            }
            return new AvasOldController(carClient);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarAvasManager) carClient.getCarManager(CarClientWrapper.XP_AVAS_SERVICE);
            if (this.mCarManager != 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarAvasEventCallback);
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
        this.mSoundResourceManager = XuiClientWrapper.getInstance().getSoundResManager();
        LogUtils.d(TAG, "Init xui manager end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        addExtPropertyIds(arrayList);
        return arrayList;
    }

    protected void addExtPropertyIds(List<Integer> propertyIds) {
        propertyIds.add(557855239);
        if (!this.mIsMainProcess) {
            propertyIds.add(557855240);
            propertyIds.add(557855243);
            propertyIds.add(557855233);
            propertyIds.add(557855234);
            propertyIds.add(557855235);
            propertyIds.add(557855236);
            propertyIds.add(557855237);
            propertyIds.add(557855238);
            propertyIds.add(557855246);
        } else if (!this.mIsMainProcess || this.mCarConfig.isSupportUnity3D()) {
        } else {
            propertyIds.add(557855240);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarAvasEventCallback);
            } catch (CarNotConnectedException e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 557855233:
                handleAvasWakeWaitUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557855234:
                handleAvasWakeWaitFullChargeUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557855235:
                handleAvasSleepUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557855236:
                handleAvasAcChargingUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557855237:
                handleAvasDcChargingUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557855238:
                handleAvasDisconnectChargingUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557855239:
                handleLowSpdEnableUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557855240:
                handleLowSpdEffectUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557855241:
            case 557855242:
            case 557855245:
            default:
                LogUtils.w(TAG, "handle unknown event: " + value);
                return;
            case 557855243:
                handleAvasExternalVolumeUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557855244:
                handleLowSpdVolumeUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557855246:
                handleAvasTakePhotoUpdate(((Integer) getValue(value)).intValue());
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setLowSpdSoundEnable(boolean enable) {
        try {
            this.mCarManager.setAvasLowSpeedSoundSwitch(enable ? 1 : 0);
            LogUtils.d(TAG, "setLowSpdSoundEnable, enable: " + enable, false);
        } catch (Exception e) {
            LogUtils.e(TAG, "setLowSpdSoundEnable: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public boolean isLowSpdSoundEnabled() {
        int avasLowSpeedSoundSwitch;
        try {
            try {
                avasLowSpeedSoundSwitch = getIntProperty(557855239);
            } catch (Exception unused) {
                avasLowSpeedSoundSwitch = this.mCarManager.getAvasLowSpeedSoundSwitch();
            }
            LogUtils.debug(TAG, "isLowSpdSoundEnabled, status: " + avasLowSpeedSoundSwitch, false);
            return avasLowSpeedSoundSwitch == 1;
        } catch (Exception e) {
            LogUtils.e(TAG, "isLowSpdSoundEnabled: " + e.getMessage(), false);
            return true;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setLowSpdSoundType(int type) {
        try {
            this.mCarManager.setAvasLowSpeedSound(type);
            if (this.mIsMainProcess) {
                this.mDataSync.setAvasEffect(type);
            } else {
                CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.AVAS_EFFECT, type);
            }
            LogUtils.d(TAG, "setLowSpdSoundType, type: " + type, false);
        } catch (Exception e) {
            LogUtils.e(TAG, "setLowSpdSoundType: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public int getLowSpdSoundType() {
        int avasEffect;
        try {
            try {
                avasEffect = getIntProperty(557855240);
            } catch (Exception e) {
                LogUtils.e(TAG, "getLowSpdSoundType: " + e.getMessage(), false);
                avasEffect = this.mIsMainProcess ? this.mDataSync.getAvasEffect() : 1;
            }
        } catch (Exception unused) {
            avasEffect = this.mCarManager.getAvasLowSpeedSound();
        }
        LogUtils.d(TAG, "getLowSpdSoundType, type: " + avasEffect, false);
        return avasEffect;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setLowSpdSoundVolume(int volume) {
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                this.mCarManager.setLowSpeedVolume(volume);
                if (this.mIsMainProcess) {
                    this.mDataSync.setAvasVolume(volume);
                } else {
                    CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.AVAS_VOLUME, volume);
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "setLowSpdSoundVolume: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public int getLowSpdSoundVolume() {
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                try {
                    return getIntProperty(557855244);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getLowSpdSoundVolume: " + e.getMessage(), false);
                    if (this.mIsMainProcess) {
                        return this.mDataSync.getAvasVolume();
                    }
                    return 100;
                }
            } catch (Exception unused) {
                return this.mCarManager.getLowSpeedVolume();
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public int getFriendSoundType() {
        if (this.mCarConfig.isNewAvasArch()) {
            int sayHiEffect = this.mIsMainProcess ? this.mDataSync.getSayHiEffect() : 1;
            SmartManager smartManager = this.mXuiSmartManager;
            if (smartManager != null) {
                try {
                    return smartManager.getSayHiEffect();
                } catch (Exception e) {
                    LogUtils.e(TAG, "getFriendSoundType: " + e.getMessage(), false);
                    return sayHiEffect;
                }
            }
            return sayHiEffect;
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setFriendSoundType(int type) {
        if (this.mCarConfig.isNewAvasArch()) {
            if (this.mIsMainProcess) {
                this.mDataSync.setSayHiEffect(type);
            } else {
                CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.SAY_HI_EFFECT, type);
            }
            SmartManager smartManager = this.mXuiSmartManager;
            if (smartManager != null) {
                try {
                    smartManager.setSayHiEffect(type);
                    handleFriendSoundTypeChanged(getFriendSoundType());
                } catch (Exception e) {
                    LogUtils.e(TAG, "setFriendSoundType: " + e.getMessage(), false);
                }
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setAvasWakeWaitSwitch(boolean enable) {
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                this.mCarManager.setAvasWakeWaitSwitch(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setAvasWakeWaitSwitch: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public boolean isAvasWakeWaitEnable() {
        int avasWakeWaitSwitch;
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                try {
                    avasWakeWaitSwitch = getIntProperty(557855233);
                } catch (Exception unused) {
                    avasWakeWaitSwitch = this.mCarManager.getAvasWakeWaitSwitch();
                }
                return avasWakeWaitSwitch == 1;
            } catch (Exception e) {
                LogUtils.e(TAG, "isAvasWakeWaitEnable: " + e.getMessage(), false);
                return true;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setAvasWakeWaitFullChargeSwitch(boolean enable) {
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                this.mCarManager.setAvasWakeWaitFullChargeSwitch(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setAvasWakeWaitFullChargeSwitch: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public boolean isAvasWakeWaitFullChargeEnable() {
        int avasWakeWaitFullChargeSwitch;
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                try {
                    avasWakeWaitFullChargeSwitch = getIntProperty(557855234);
                } catch (Exception unused) {
                    avasWakeWaitFullChargeSwitch = this.mCarManager.getAvasWakeWaitFullChargeSwitch();
                }
                return avasWakeWaitFullChargeSwitch == 1;
            } catch (Exception e) {
                LogUtils.e(TAG, "isAvasWakeWaitFullChargeEnable: " + e.getMessage(), false);
                return true;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setAvasSleepSwitch(boolean enable) {
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                this.mCarManager.setAvasSleepSwitch(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setAvasSleepSwitch: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public boolean isAvasSleepEnable() {
        int avasSleepSwitch;
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                try {
                    avasSleepSwitch = getIntProperty(557855235);
                } catch (Exception unused) {
                    avasSleepSwitch = this.mCarManager.getAvasSleepSwitch();
                }
                return avasSleepSwitch == 1;
            } catch (Exception e) {
                LogUtils.e(TAG, "isAvasSleepEnable: " + e.getMessage(), false);
                return true;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setAvasAcChargingSwitch(boolean enable) {
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                this.mCarManager.setAvasAcChargingSwitch(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setAvasAcChargingSwitch: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public boolean isAvasAcChargingEnable() {
        int avasAcChargingSwitch;
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                try {
                    avasAcChargingSwitch = getIntProperty(557855236);
                } catch (Exception unused) {
                    avasAcChargingSwitch = this.mCarManager.getAvasAcChargingSwitch();
                }
                return avasAcChargingSwitch == 1;
            } catch (Exception e) {
                LogUtils.e(TAG, "isAvasAcChargingEnable: " + e.getMessage(), false);
                return true;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setAvasDcChargingSwitch(boolean enable) {
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                this.mCarManager.setAvasDcChargingSwitch(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setAvasDcChargingSwitch: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public boolean isAvasDcChargingEnable() {
        int avasDcChargingSwitch;
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                try {
                    avasDcChargingSwitch = getIntProperty(557855237);
                } catch (Exception unused) {
                    avasDcChargingSwitch = this.mCarManager.getAvasDcChargingSwitch();
                }
                return avasDcChargingSwitch == 1;
            } catch (Exception e) {
                LogUtils.e(TAG, "isAvasDcChargingEnable: " + e.getMessage(), false);
                return true;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setAvasDisconnectChargingSwitch(boolean enable) {
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                this.mCarManager.setAvasDisconnectChargingSwitch(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setAvasDisconnectChargingSwitch: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public boolean isAvasDisconnectChargingEnable() {
        int avasDisconnectChargingSwitch;
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                try {
                    avasDisconnectChargingSwitch = getIntProperty(557855238);
                } catch (Exception unused) {
                    avasDisconnectChargingSwitch = this.mCarManager.getAvasDisconnectChargingSwitch();
                }
                return avasDisconnectChargingSwitch == 1;
            } catch (Exception e) {
                LogUtils.e(TAG, "isAvasDisconnectChargingEnable: " + e.getMessage(), false);
                return true;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setAvasExternalSw(final boolean enable) {
        if (this.mCarConfig.isNewAvasArch()) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$AvasController$qlceixTni5t8z5cmJpnE8FAcxqE
                @Override // java.lang.Runnable
                public final void run() {
                    AvasController.this.lambda$setAvasExternalSw$1$AvasController(enable);
                }
            });
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public boolean isAvasExternalEnabled() {
        if (this.mCarConfig.isNewAvasArch()) {
            return DebugFuncModel.getInstance().isAvasExternalSwEnabled();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setAvasExternalVolume(int volume) {
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                this.mCarManager.setAvasExternalVolume(volume);
            } catch (Exception e) {
                LogUtils.e(TAG, "setAvasExternalVolume: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public int getAvasExternalVolume() {
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                try {
                    return getIntProperty(557855243);
                } catch (Exception e) {
                    LogUtils.e(TAG, "getAvasExternalVolume: " + e.getMessage(), false);
                    if (this.mIsMainProcess) {
                        return this.mDataSync.getAvasOtherVolume();
                    }
                    return 100;
                }
            } catch (Exception unused) {
                return this.mCarManager.getAvasExternalVolume();
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setAvasTakePhotoSwitch(boolean enable) {
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                this.mCarManager.setAvasPhotoSoundSwitch(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setAvasTakePhotoSwitch: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public boolean isAvasTakePhotoEnable() {
        int avasPhotoSoundSwitch;
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                try {
                    avasPhotoSoundSwitch = getIntProperty(557855246);
                } catch (Exception unused) {
                    avasPhotoSoundSwitch = this.mCarManager.getAvasPhotoSoundSwitch();
                }
                return avasPhotoSoundSwitch == 1;
            } catch (Exception e) {
                LogUtils.e(TAG, "isAvasTakePhotoEnable: " + e.getMessage(), false);
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setAvasSayHiSw(final boolean enable) {
        if (this.mCarConfig.isNewAvasArch()) {
            Settings.System.putInt(App.getInstance().getContentResolver(), SAY_HI_AVAS_SW, enable ? 1 : 0);
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$AvasController$h3FIzWEwNfZKl6JKv3Ybzsr4ouo
                @Override // java.lang.Runnable
                public final void run() {
                    AvasController.this.lambda$setAvasSayHiSw$2$AvasController(enable);
                }
            });
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public boolean getAvasSayHiSw() {
        return this.mCarConfig.isNewAvasArch() && Settings.System.getInt(App.getInstance().getContentResolver(), SAY_HI_AVAS_SW, 1) == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setAvasExternalSound(int type) {
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                this.mCarManager.setAvasExternalSound(type);
            } catch (Exception e) {
                LogUtils.e(TAG, "setAvasExternalSound: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setExternalSoundModeCmd(int mode) {
        if (this.mCarConfig.isNewAvasArch()) {
            try {
                this.mCarManager.setExternalSoundModeCmd(mode);
            } catch (Exception e) {
                LogUtils.e(TAG, "setExternalSoundModeCmd: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setBootSoundEffect(final int type) {
        SoundResourceManager soundResourceManager;
        if (this.mCarConfig.isSupportBootSoundEffect()) {
            if (this.mIsMainProcess) {
                this.mDataSync.setBootEffect(type);
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$AvasController$VSrSdZ96DGDcoPhWTfqtFe20uGw
                    @Override // java.lang.Runnable
                    public final void run() {
                        AvasController.this.lambda$setBootSoundEffect$3$AvasController(type);
                    }
                });
            } else {
                CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.BOOT_EFFECT, type);
            }
            if (BaseFeatureOption.getInstance().isSupportThemeBootEffect() && (soundResourceManager = this.mSoundResourceManager) != null) {
                try {
                    if (type == 0) {
                        soundResourceManager.setBootSoundOnOff(false);
                    } else {
                        soundResourceManager.setBootSoundOnOff(true);
                        this.mSoundResourceManager.setBootSoundResource(type);
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG, "setBootSoundEffect: " + e.getMessage(), false);
                }
            }
            SmartManager smartManager = this.mXuiSmartManager;
            if (smartManager != null) {
                try {
                    smartManager.setBootSoundEffect(type);
                } catch (Exception e2) {
                    LogUtils.e(TAG, "setBootSoundEffect: " + e2.getMessage(), false);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getBootSoundEffect() {
        /*
            r7 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = r7.mCarConfig
            boolean r0 = r0.isSupportBootSoundEffect()
            if (r0 == 0) goto L7d
            com.xiaopeng.carcontrol.config.feature.BaseFeatureOption r0 = com.xiaopeng.carcontrol.config.feature.BaseFeatureOption.getInstance()
            boolean r0 = r0.isSupportThemeBootEffect()
            java.lang.String r1 = "AvasController"
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L4e
            com.xiaopeng.xuimanager.soundresource.SoundResourceManager r0 = r7.mSoundResourceManager
            if (r0 == 0) goto L72
            int r0 = r0.getBootSoundOnOff()     // Catch: java.lang.Exception -> L2d
            com.xiaopeng.xuimanager.soundresource.SoundResourceManager r4 = r7.mSoundResourceManager     // Catch: java.lang.Exception -> L2b
            com.xiaopeng.xuimanager.soundresource.data.BootSoundResource r4 = r4.getActiveBootSoundResource()     // Catch: java.lang.Exception -> L2b
            if (r4 == 0) goto L49
            int r1 = r4.getResId()     // Catch: java.lang.Exception -> L2b
            goto L4a
        L2b:
            r4 = move-exception
            goto L2f
        L2d:
            r4 = move-exception
            r0 = r3
        L2f:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "getBootSoundEffect:"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r4 = r4.getMessage()
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.String r4 = r4.toString()
            com.xiaopeng.carcontrol.util.LogUtils.e(r1, r4, r3)
        L49:
            r1 = r2
        L4a:
            if (r0 != r2) goto L4d
            r3 = r1
        L4d:
            return r3
        L4e:
            com.xiaopeng.xuimanager.smart.SmartManager r0 = r7.mXuiSmartManager
            if (r0 == 0) goto L72
            int r0 = r0.getBootSoundEffect()     // Catch: java.lang.Exception -> L57
            return r0
        L57:
            r0 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "getBootSoundEffect: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r4.append(r0)
            java.lang.String r0 = r0.toString()
            com.xiaopeng.carcontrol.util.LogUtils.e(r1, r0, r3)
        L72:
            boolean r0 = r7.mIsMainProcess
            if (r0 == 0) goto L7c
            com.xiaopeng.carcontrol.model.DataSyncModel r0 = r7.mDataSync
            int r2 = r0.getBootEffect()
        L7c:
            return r2
        L7d:
            r0 = -1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.AvasController.getBootSoundEffect():int");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setBootEffectBeforeSw(int type) {
        if (this.mCarConfig.isSupportBootSoundEffect()) {
            if (this.mIsMainProcess) {
                this.mDataSync.setBootEffectBeforeSw(type);
                return;
            }
            this.mBootEffectBeforeSw = Integer.valueOf(type);
            CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.BOOT_EFFECT_BEFORE_SW, type);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public int getBootEffectBeforeSw() {
        if (this.mCarConfig.isSupportBootSoundEffect()) {
            return this.mIsMainProcess ? this.mDataSync.getBootEffectBeforeSw() : this.mBootEffectBeforeSw.intValue();
        }
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public BootSoundResource[] getBootSoundResource() {
        try {
            return this.mSoundResourceManager.getBootSoundResource();
        } catch (Exception e) {
            LogUtils.e(TAG, "getBootSoundResourceArray: " + e.getMessage(), false);
            return null;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setAvasSpeakerSw(boolean enable) {
        LogUtils.d(TAG, "setMultimediaSw, enable = " + enable, false);
        if (this.mCarConfig.isSupportAvasLoudSpeaker()) {
            Settings.System.putInt(App.getInstance().getContentResolver(), AVAS_LOUD_SPEAKER_SW, enable ? 1 : 0);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public boolean getAvasSpeakerSw() {
        int i = this.mCarConfig.isSupportAvasLoudSpeaker() ? Settings.System.getInt(App.getInstance().getContentResolver(), AVAS_LOUD_SPEAKER_SW, 0) : 0;
        LogUtils.d(TAG, "getMultimediaSw, value = " + i, false);
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setLockAvasSw(boolean enable) {
        if (this.mCarConfig.isSupportNewAvasUnlockResponse()) {
            try {
                this.mCarManager.setLockUnlockSoundSwitchStatus(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setLockAvasSw: " + e.getMessage(), false);
            }
        }
    }

    private void mockAvasProperty() {
        this.mCarPropertyMap.put(557855239, new CarPropertyValue<>(557855239, 1));
        this.mCarPropertyMap.put(557855240, new CarPropertyValue<>(557855240, Integer.valueOf(this.mIsMainProcess ? this.mDataSync.getAvasEffect() : 1)));
        mockExtAvasValue();
    }

    protected void mockExtAvasValue() {
        this.mCarPropertyMap.put(557855244, new CarPropertyValue<>(557855244, Integer.valueOf(this.mIsMainProcess ? this.mDataSync.getAvasVolume() : 100)));
        this.mCarPropertyMap.put(557855233, new CarPropertyValue<>(557855233, true));
        this.mCarPropertyMap.put(557855234, new CarPropertyValue<>(557855234, true));
        this.mCarPropertyMap.put(557855235, new CarPropertyValue<>(557855235, true));
        this.mCarPropertyMap.put(557855236, new CarPropertyValue<>(557855236, true));
        this.mCarPropertyMap.put(557855237, new CarPropertyValue<>(557855237, true));
        this.mCarPropertyMap.put(557855238, new CarPropertyValue<>(557855238, true));
        this.mCarPropertyMap.put(557855246, new CarPropertyValue<>(557855246, false));
        this.mCarPropertyMap.put(557855243, new CarPropertyValue<>(557855243, Integer.valueOf(this.mIsMainProcess ? this.mDataSync.getAvasOtherVolume() : 100)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleLowSpdEnableUpdate(int state) {
        boolean z = state == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAvasController.Callback) it.next()).onAvasLowSpdChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleLowSpdEffectUpdate(int value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAvasController.Callback) it.next()).onAvasLowSpdEffectChanged(value);
            }
        }
    }

    private void handleLowSpdVolumeUpdate(int volume) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAvasController.Callback) it.next()).onAvasLowSpdVolumeChanged(volume);
            }
        }
    }

    private void handleAvasWakeWaitUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IAvasController.Callback callback = (IAvasController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onAvasWakeWaitChanged(z);
            }
        }
    }

    private void handleAvasWakeWaitFullChargeUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IAvasController.Callback callback = (IAvasController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onAvasWakeWaitFullChargeChanged(z);
            }
        }
    }

    private void handleAvasSleepUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IAvasController.Callback callback = (IAvasController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onAvasSleepChanged(z);
            }
        }
    }

    private void handleAvasAcChargingUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IAvasController.Callback callback = (IAvasController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onAvasAcChargingChanged(z);
            }
        }
    }

    private void handleAvasDcChargingUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IAvasController.Callback callback = (IAvasController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onAvasDcChargingChanged(z);
            }
        }
    }

    private void handleAvasDisconnectChargingUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IAvasController.Callback callback = (IAvasController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onAvasDisconnectChargingChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleAvasExternalSwUpdate */
    public void lambda$setAvasExternalSw$1$AvasController(boolean enabled) {
        if (this.mIsMainProcess) {
            DebugFuncModel.getInstance().setAvasExternalSw(enabled);
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAvasController.Callback) it.next()).onAvasExternalSwChanged(enabled);
            }
        }
    }

    private void handleAvasExternalVolumeUpdate(int value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAvasController.Callback) it.next()).onAvasExternalVolumeChanged(value);
            }
        }
    }

    private void handleAvasTakePhotoUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                IAvasController.Callback callback = (IAvasController.Callback) it.next();
                boolean z = true;
                if (state != 1) {
                    z = false;
                }
                callback.onAvasTakePhotoChanged(z);
            }
        }
    }

    protected void registerContentObserver() {
        final ContentResolver contentResolver = App.getInstance().getContentResolver();
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.carmanager.impl.AvasController.2
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    String lastPathSegment = uri.getLastPathSegment();
                    if (CarControl.PrivateControl.BOOT_EFFECT_BEFORE_SW.equals(lastPathSegment)) {
                        int i = CarControl.PrivateControl.getInt(contentResolver, CarControl.PrivateControl.BOOT_EFFECT_BEFORE_SW, 1);
                        LogUtils.d(AvasController.TAG, "onChange: " + lastPathSegment + ", Boot Effect before sw =" + i);
                        AvasController.this.mBootEffectBeforeSw = Integer.valueOf(i);
                    } else if (AvasController.SAY_HI_AVAS_SW.equals(lastPathSegment)) {
                        AvasController.this.lambda$setAvasSayHiSw$2$AvasController(Settings.System.getInt(contentResolver, AvasController.SAY_HI_AVAS_SW, 0) == 1);
                    }
                }
            };
        }
        contentResolver.registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.BOOT_EFFECT_BEFORE_SW), false, this.mContentObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleAvasSayHiSw */
    public void lambda$setAvasSayHiSw$2$AvasController(boolean enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAvasController.Callback) it.next()).onAvasSayHiSwChanged(enable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleBootEffectChanged */
    public void lambda$setBootSoundEffect$3$AvasController(int bootEffect) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAvasController.Callback) it.next()).onBootEffectChanged(bootEffect);
            }
        }
    }

    private void handleFriendSoundTypeChanged(int type) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IAvasController.Callback) it.next()).onFriendSoundTypeChanged(type);
            }
        }
    }
}
