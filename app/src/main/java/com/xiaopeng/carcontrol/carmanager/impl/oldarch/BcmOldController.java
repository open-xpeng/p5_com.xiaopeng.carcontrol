package com.xiaopeng.carcontrol.carmanager.impl.oldarch;

import android.car.Car;
import android.car.hardware.CarPropertyValue;
import android.provider.Settings;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.impl.BcmController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.Iterator;

/* loaded from: classes.dex */
public class BcmOldController extends BcmController {
    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController
    protected void registerContentObserver() {
    }

    public BcmOldController(Car carClient) {
        super(carClient);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController
    protected void mockExtBcmValue() {
        this.mCarPropertyMap.put(557849676, new CarPropertyValue<>(557849676, 2));
        this.mCarPropertyMap.put(557849601, new CarPropertyValue<>(557849601, 1));
        this.mCarPropertyMap.put(557849648, new CarPropertyValue<>(557849648, 0));
        this.mCarPropertyMap.put(557849635, new CarPropertyValue<>(557849635, 1));
        this.mCarPropertyMap.put(557849636, new CarPropertyValue<>(557849636, 1));
        this.mCarPropertyMap.put(560012320, new CarPropertyValue<>(560012320, Float.valueOf(100.0f)));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController, com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public float[] getAllWindowPosition() {
        try {
            try {
                return getFloatArrayProperty(560012320);
            } catch (Exception e) {
                LogUtils.e("BcmController", "getAllWindowPosition: " + e.getMessage(), false);
                return null;
            }
        } catch (Exception unused) {
            return this.mCarManager.getWindowsState();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController, com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlWindowAuto(boolean open) {
        try {
            this.mCarManager.setAutoWindowCmd(open ? 2 : 1);
        } catch (Exception e) {
            LogUtils.e("BcmController", "controlWindowAuto: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController, com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setHeadLampState(final int groupId, boolean needSp) {
        try {
            this.mCarManager.setHeadLampGroup(groupId);
            if (needSp) {
                this.mDataSync.setHeadLampState(groupId);
            }
            Settings.System.putString(App.getInstance().getContentResolver(), "c_head_lamp_mode", String.valueOf(groupId));
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$BcmOldController$S1ZGSKCyyMpg-VmXOqqhZBfEHHw
                @Override // java.lang.Runnable
                public final void run() {
                    BcmOldController.this.lambda$setHeadLampState$0$BcmOldController(groupId);
                }
            });
        } catch (Exception e) {
            LogUtils.e("BcmController", "setHeadLampState: " + e.getMessage(), false);
        }
    }

    public /* synthetic */ void lambda$setHeadLampState$0$BcmOldController(final int groupId) {
        handleHeadLampMode(groupId);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController, com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getHeadLampState() {
        return getHeadLampStateSp();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController, com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setLightMeHome(boolean enable, boolean needSave) {
        try {
            this.mCarManager.setLightMeHome(enable ? 2 : 1);
            if (this.mIsMainProcess && needSave) {
                this.mDataSync.setLightMeHome(enable);
            }
        } catch (Exception e) {
            LogUtils.e("BcmController", "setLightMeHome: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController, com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void controlWinVent() {
        try {
            this.mCarManager.setVentilate();
        } catch (Exception e) {
            LogUtils.e("BcmController", "controlWinVent: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController, com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public int getUnlockResponse() {
        try {
            try {
                return getIntProperty(557849630);
            } catch (Exception e) {
                LogUtils.e("BcmController", "getUnlockResponse: " + e.getMessage(), false);
                return this.mDataSync == null ? GlobalConstant.DEFAULT.UNLOCK_RESPONSE : this.mDataSync.getUnlockResponse();
            }
        } catch (Exception unused) {
            return this.mCarManager.getUnlockResponse();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController, com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setMirrorHeat(boolean enable) {
        try {
            this.mCarManager.setBcmBackMirrorHeatMode(parseCduSwitchCmd(enable));
            this.mCarManager.setBcmBackDefrostMode(parseCduSwitchCmd(enable));
        } catch (Exception e) {
            LogUtils.e("BcmController", "setMirrorHeat: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController, com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void setEbwEnable(boolean enable) {
        this.mDataSync.setEbwEnable(enable);
        super.setEbwEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController
    protected void handleEbwState(int status) {
        boolean parseBcmStatus = parseBcmStatus(status);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onEbwChanged(parseBcmStatus);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController, com.xiaopeng.carcontrol.carmanager.controller.IBcmController
    public void restorePsnSeatHeatLevel() {
        if (this.mIsMainProcess) {
            try {
                this.mCarManager.setBcmPsnSeatHeatLevel(measureSeatHeatLevel(this.mDataSync.getPsnSeatHeatLevel()));
            } catch (Exception e) {
                LogUtils.e("BcmController", (String) null, (Throwable) e, false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController
    protected void handleLightMeHome(int state) {
        if (state != 2 && state != 1 && state != 3) {
            LogUtils.w("BcmController", "handleLightMeHome unknown state: " + state, false);
            return;
        }
        boolean z = state != 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onLightMeHomeChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.impl.BcmController
    public void handleUnlockResponse(int type) {
        if (CarBaseConfig.getInstance().isNewAvasArch()) {
            super.lambda$setUnlockResponse$5$BcmController(type);
            return;
        }
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IBcmController.Callback) it.next()).onUnlockResponseChanged(type);
            }
        }
    }
}
