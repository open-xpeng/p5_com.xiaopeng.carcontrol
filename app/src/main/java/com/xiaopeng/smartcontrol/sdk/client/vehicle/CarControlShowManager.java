package com.xiaopeng.smartcontrol.sdk.client.vehicle;

import com.xiaopeng.smartcontrol.sdk.client.BaseManager;
import com.xiaopeng.smartcontrol.utils.Constants;
import com.xiaopeng.smartcontrol.utils.LogUtils;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class CarControlShowManager extends BaseManager<ISignalCallback> {

    /* loaded from: classes2.dex */
    public interface ISignalCallback extends BaseManager.IBaseSignalReceiver {
        default void onEnterVehicleFaultPage(String str) {
        }

        default void onExitVehicleFaultPage() {
        }
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getAppId() {
        return "carcontrol";
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final CarControlShowManager INSTANCE = new CarControlShowManager();

        private SingletonHolder() {
        }
    }

    private CarControlShowManager() {
    }

    public static CarControlShowManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getModuleName() {
        return Constants.CAR_CONTROL_MODULE.SHOW_IMPL;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    public void onIpcReceived(String str, String str2) {
        LogUtils.i(this.TAG, str + " " + str2);
        Iterator<BaseManager.IRawSignalReceiver> it = this.mRawCallback.iterator();
        while (it.hasNext()) {
            it.next().onIpcReceived(str, str2);
        }
        str.hashCode();
        if (str.equals("onExitVehicleFaultPage")) {
            Iterator it2 = this.mCallback.iterator();
            while (it2.hasNext()) {
                ((ISignalCallback) it2.next()).onExitVehicleFaultPage();
            }
        } else if (str.equals("onEnterVehicleFaultPage")) {
            Iterator it3 = this.mCallback.iterator();
            while (it3.hasNext()) {
                ((ISignalCallback) it3.next()).onEnterVehicleFaultPage(str2);
            }
        }
    }

    public void showCarControlPage(int i) {
        LogUtils.i(this.TAG, "showCarControlPage " + i);
        callIpc("showCarControlPage", Integer.valueOf(i));
    }

    public void showVehicleFaultListPage() {
        LogUtils.i(this.TAG, "showVehicleFaultListPage");
        callIpc("showVehicleFaultListPage", null);
    }

    public void showVehicleFaultDetailPage(String str) {
        LogUtils.i(this.TAG, "showVehicleFaultDetailPage " + str);
        callIpc("showVehicleFaultDetailPage", str);
    }
}
