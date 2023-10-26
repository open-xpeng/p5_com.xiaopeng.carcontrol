package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.ciu.CarCiuManager;
import android.os.SystemProperties;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.ICiuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class CiuController extends BaseCarController<CarCiuManager, ICiuController.Callback> implements ICiuController {
    protected static final String TAG = "CiuController";
    private final CarCiuManager.CarCiuEventCallback mCarCiuEventCallback = new CarCiuManager.CarCiuEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.CiuController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.i(CiuController.TAG, "onChangeEvent: " + carPropertyValue, false);
            CiuController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    public CiuController(Car carClient) {
    }

    /* loaded from: classes.dex */
    public static class CiuControllerFactory {
        public static CiuController createCarController(Car carClient) {
            return new CiuController(carClient);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarCiuManager) carClient.getCarManager(CarClientWrapper.XP_CIU_SERVICE);
            if (this.mCarManager != 0 && this.mPropertyIds.size() > 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarCiuEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.d(TAG, "Init end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        if (CarBaseConfig.getInstance().isSupportCiuConfig()) {
            arrayList.add(557852702);
            arrayList.add(557852687);
            arrayList.add(557852699);
            arrayList.add(557852707);
            arrayList.add(557852706);
            arrayList.add(557852704);
        }
        return arrayList;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        try {
            if (this.mCarManager == 0 || this.mPropertyIds.size() <= 0) {
                return;
            }
            this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarCiuEventCallback);
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 557852687:
                handleDmsUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557852699:
                handleFaceIdSwUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557852702:
                handleCiuValid(((Integer) getValue(value)).intValue());
                return;
            case 557852704:
                handleCiuWiperIntervalUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557852706:
                handleDistractionSwUpdate(((Integer) getValue(value)).intValue());
                return;
            case 557852707:
                handleFatigueSwUpdate(((Integer) getValue(value)).intValue());
                return;
            default:
                LogUtils.w(TAG, "handle unknown event: " + value);
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public boolean isCiuValid() {
        int i;
        try {
            i = this.mCarManager.getCiuValid();
        } catch (Exception e) {
            LogUtils.e(TAG, "isCiuValid: " + e.getMessage(), false);
            i = 0;
        }
        LogUtils.i(TAG, "CiuValid status = " + i, false);
        return i == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public boolean getDmsSw() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getCiuCameraSw();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public void setDmsSw(boolean enable) {
        if (this.mIsMainProcess) {
            this.mDataSync.setCiuCameraSw(enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public void controlDmsSwitch(boolean enable) {
        LogUtils.i(TAG, "controlDmsSwitch enable = " + enable, false);
        if (enable) {
            setMultiDms(true, this.mDataSync.getCiuFaceSw(), this.mDataSync.getCiuFatigueSw(), this.mDataSync.getCiuDistractSw());
        } else {
            setMultiDms(false, false, false, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public void setMultiDms(boolean dmsSw, boolean faceIdSw, boolean fatigueSw, boolean distractSw) {
        LogUtils.i(TAG, "setMultiDms() with: main = [" + dmsSw + "], faceId = [" + faceIdSw + "], fatigue = [" + fatigueSw + "], distract = [" + distractSw + "]", false);
        try {
            int i = 1;
            this.mCarManager.setDmsStatus(dmsSw ? 1 : 0);
            this.mCarManager.setFaceIdSw(faceIdSw ? 1 : 0);
            SystemProperties.set(GlobalConstant.PREFS.PREF_FACE_RECOGNITION, String.valueOf(faceIdSw));
            this.mCarManager.setFatigueStatus(fatigueSw ? 1 : 0);
            CarCiuManager carCiuManager = this.mCarManager;
            if (!distractSw) {
                i = 0;
            }
            carCiuManager.setDistractionStatus(i);
        } catch (Exception e) {
            LogUtils.e(TAG, "setMultiDms: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public boolean getFaceIdSw() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getCiuFaceSw();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public void setFaceIdSw(boolean enable) {
        if (this.mIsMainProcess) {
            this.mDataSync.setCiuFaceSw(enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public void controlFaceIdSwitch(boolean enable, boolean needSave) {
        LogUtils.i(TAG, "controlDmsSwitch enable = " + enable, false);
        if (needSave) {
            setFaceIdSw(enable);
        }
        try {
            this.mCarManager.setFaceIdSw(enable ? 1 : 0);
            SystemProperties.set(GlobalConstant.PREFS.PREF_FACE_RECOGNITION, String.valueOf(enable));
        } catch (Exception e) {
            LogUtils.e(TAG, "controlFaceIdSwitch: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public boolean getFatigueSw() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getCiuFatigueSw();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public void setFatigueSw(boolean enable) {
        if (this.mIsMainProcess) {
            this.mDataSync.setCiuFatigueSw(enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public void controlFatigueSwitch(boolean enable, boolean needSave) {
        LogUtils.i(TAG, "controlFatigueSwitch enable = " + enable, false);
        if (needSave) {
            setFatigueSw(enable);
        }
        try {
            this.mCarManager.setFatigueStatus(enable ? 1 : 0);
        } catch (Exception e) {
            LogUtils.e(TAG, "controlFatigueSwitch: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public boolean getDistractSw() {
        if (this.mIsMainProcess) {
            return this.mDataSync.getCiuDistractSw();
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public void setDistractSw(boolean enable) {
        if (this.mIsMainProcess) {
            this.mDataSync.setCiuDistractSw(enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public void controlDistractSwitch(boolean enable, boolean needSave) {
        LogUtils.i(TAG, "controlDistractSwitch enable = " + enable, false);
        if (needSave) {
            setDistractSw(enable);
        }
        try {
            this.mCarManager.setDistractionStatus(enable ? 1 : 0);
        } catch (Exception e) {
            LogUtils.e(TAG, "controlDistractSwitch: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public void setCiuWiperLevel(int level) {
        try {
            if (this.mIsMainProcess) {
                this.mDataSync.setCiuWiperInterval(level);
            }
            this.mCarManager.setCiuRainSw(level);
        } catch (Exception e) {
            LogUtils.e(TAG, "setCiuWiperLevel: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public int getCiuWiperLevel() {
        try {
            try {
                return getIntProperty(557852704);
            } catch (Exception e) {
                LogUtils.e(TAG, "getCiuWiperLevel: " + e.getMessage(), false);
                return 3;
            }
        } catch (Exception unused) {
            return this.mCarManager.getCiuRainSw();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public void setCiuRainEnable(boolean enable) {
        if (this.mIsMainProcess) {
            this.mDataSync.setCiuRainEnable(enable);
        }
        handleCiuWiperAutoSwitchUpdate(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController
    public boolean isCiuRainEnable() {
        if (this.mIsMainProcess) {
            return this.mDataSync.isCiuRainEnable();
        }
        return false;
    }

    private void handleCiuValid(int status) {
        if (this.mIsMainProcess && status == 1) {
            setDmsSw(this.mDataSync.getCiuCameraSw());
        }
    }

    private void handleDmsUpdate(int status) {
        if (isCiuValid()) {
            if (status == 1 || status == 2) {
                boolean z = status == 2;
                synchronized (this.mCallbackLock) {
                    Iterator it = this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((ICiuController.Callback) it.next()).onDmsSwChanged(z);
                    }
                }
            }
        }
    }

    private void handleFaceIdSwUpdate(int status) {
        if (isCiuValid()) {
            if (status == 0 || status == 1) {
                boolean z = status == 1;
                synchronized (this.mCallbackLock) {
                    Iterator it = this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((ICiuController.Callback) it.next()).onFaceIdSwChanged(z);
                    }
                }
            }
        }
    }

    private void handleFatigueSwUpdate(int status) {
        if (isCiuValid()) {
            if (status == 1 || status == 2) {
                boolean z = status == 1;
                synchronized (this.mCallbackLock) {
                    Iterator it = this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((ICiuController.Callback) it.next()).onFatigueSwChanged(z);
                    }
                }
            }
        }
    }

    private void handleDistractionSwUpdate(int status) {
        if (isCiuValid()) {
            if (status == 1 || status == 2) {
                boolean z = status == 1;
                synchronized (this.mCallbackLock) {
                    Iterator it = this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((ICiuController.Callback) it.next()).onDistractSwChanged(z);
                    }
                }
            }
        }
    }

    private void handleCiuWiperIntervalUpdate(int level) {
        if (isCiuValid()) {
            synchronized (this.mCallbackLock) {
                Iterator it = this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((ICiuController.Callback) it.next()).onCiuWiperIntervalChanged(level);
                }
            }
        }
    }

    private void handleCiuWiperAutoSwitchUpdate(boolean enable) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ICiuController.Callback) it.next()).onCiuWiperAutoSwitchChanged(enable);
            }
        }
    }
}
