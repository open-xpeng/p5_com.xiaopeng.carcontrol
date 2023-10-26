package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.hvac.CarHvacManager;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.controller.ISfsController;
import com.xiaopeng.carcontrol.util.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class SfsController extends BaseCarController<CarHvacManager, ISfsController.Callback> implements ISfsController {
    protected static final String TAG = "SfsController";
    private final CarHvacManager.CarHvacEventCallback mCarHvacEventCallback = new CarHvacManager.CarHvacEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.SfsController.1
        public void onErrorEvent(int propertyId, int zone) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.i(SfsController.TAG, "onChangeEvent: " + carPropertyValue, false);
            SfsController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    public SfsController(Car carClient) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarHvacManager) carClient.getCarManager("hvac");
            if (this.mCarManager != 0 && this.mPropertyIds.size() > 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarHvacEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.d(TAG, "Init end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        addExtPropertyIds(arrayList);
        return arrayList;
    }

    protected void addExtPropertyIds(List<Integer> propertyIds) {
        if (this.mCarConfig.isSupportSfs()) {
            propertyIds.add(557849143);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        try {
            if (this.mCarManager == 0 || this.mPropertyIds.size() <= 0) {
                return;
            }
            this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarHvacEventCallback);
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        int propertyId = value.getPropertyId();
        if (propertyId == 557849143) {
            handleSfsSwUpdate(((Integer) getValue(value)).intValue());
        } else if (propertyId == 557849145) {
            handleSfsActiveChannelUpdate(((Integer) getValue(value)).intValue());
        } else if (propertyId == 557914680) {
            handleSfsTypeUpdate(getIntArrayProperty(value));
        } else {
            LogUtils.w(TAG, "handle unknown event: " + value);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ISfsController
    public void setSwEnable(boolean enable) {
        if (this.mCarConfig.isSupportSfs()) {
            if (!this.mCarConfig.isSupportSfsSw() && enable == isSwEnabled()) {
                LogUtils.i(TAG, "Current Sfs is Equal: " + enable, false);
                return;
            }
            try {
                this.mCarManager.setSfsSwitch(enable ? 1 : 0);
            } catch (Exception e) {
                LogUtils.e(TAG, "setSwEnable: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ISfsController
    public boolean isSwEnabled() {
        int i;
        if (this.mCarConfig.isSupportSfs()) {
            try {
                try {
                    i = getIntProperty(557849143);
                } catch (Exception unused) {
                    i = this.mCarManager.getSfsSwitchStatus();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "isSwEnabled: " + e.getMessage(), false);
                i = 0;
            }
            return i == 1;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ISfsController
    public int[] getTypesInChannel() {
        if (this.mCarConfig.isSupportSfs()) {
            try {
                try {
                    return getIntArrayProperty(557914680);
                } catch (Exception unused) {
                    return this.mCarManager.getSfsTypeInChannels();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getTypesInChannel: " + e.getMessage(), false);
                return null;
            }
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ISfsController
    public void setActiveChannel(int sfsChannel) {
        if (this.mCarConfig.isSupportSfs()) {
            try {
                this.mCarManager.setSfsChannel(sfsChannel);
            } catch (Exception e) {
                LogUtils.e(TAG, "setActiveChannel: " + e.getMessage(), false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.ISfsController
    public int getActivatedChannel() {
        if (this.mCarConfig.isSupportSfs()) {
            try {
                try {
                    return getIntProperty(557849145);
                } catch (Exception unused) {
                    return this.mCarManager.getSfsChannel();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getActivatedChannel: " + e.getMessage(), false);
                return 0;
            }
        }
        return 0;
    }

    private void handleSfsSwUpdate(int value) {
        boolean z = value == 1;
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ISfsController.Callback) it.next()).onSfsSwChanged(z);
            }
        }
    }

    private void handleSfsTypeUpdate(int[] typeInChannel) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ISfsController.Callback) it.next()).onSfsTypeChanged(typeInChannel);
            }
        }
    }

    private void handleSfsActiveChannelUpdate(int channel) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ISfsController.Callback) it.next()).onSfsChannelChanged(channel);
            }
        }
    }
}
