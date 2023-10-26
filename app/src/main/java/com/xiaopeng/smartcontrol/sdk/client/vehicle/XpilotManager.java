package com.xiaopeng.smartcontrol.sdk.client.vehicle;

import com.xiaopeng.smartcontrol.sdk.client.BaseManager;
import com.xiaopeng.smartcontrol.utils.Constants;
import com.xiaopeng.smartcontrol.utils.LogUtils;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class XpilotManager extends BaseManager<ISignalCallback> {

    /* loaded from: classes2.dex */
    public interface ISignalCallback extends BaseManager.IBaseSignalReceiver {
        default void onAutoParkChanged(int i) {
        }

        default void onMemParkChanged(int i) {
        }
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getAppId() {
        return "carcontrol";
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final XpilotManager INSTANCE = new XpilotManager();

        private SingletonHolder() {
        }
    }

    private XpilotManager() {
    }

    public static XpilotManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getModuleName() {
        return Constants.CAR_CONTROL_MODULE.XPILOT_IMPL;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    public void onIpcReceived(String str, String str2) {
        LogUtils.i(this.TAG, str + " " + str2);
        Iterator<BaseManager.IRawSignalReceiver> it = this.mRawCallback.iterator();
        while (it.hasNext()) {
            it.next().onIpcReceived(str, str2);
        }
        str.hashCode();
        if (str.equals("onMemParkChanged")) {
            Iterator it2 = this.mCallback.iterator();
            while (it2.hasNext()) {
                ((ISignalCallback) it2.next()).onMemParkChanged(Integer.parseInt(str2));
            }
        } else if (str.equals("onAutoParkChanged")) {
            Iterator it3 = this.mCallback.iterator();
            while (it3.hasNext()) {
                ((ISignalCallback) it3.next()).onAutoParkChanged(Integer.parseInt(str2));
            }
        }
    }

    public int getAutoParkState() {
        String callIpc = callIpc("getAutoParkState", null);
        LogUtils.i(this.TAG, "getAutoParkState: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setAutoParkEnable(boolean z) {
        LogUtils.i(this.TAG, "setAutoParkEnable " + z);
        callIpc("setAutoParkEnable", Boolean.valueOf(z));
    }

    public int getMemParkState() {
        String callIpc = callIpc("getMemParkState", null);
        LogUtils.i(this.TAG, "getMemParkState: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setMemParkEnable(boolean z) {
        LogUtils.i(this.TAG, "setMemParkEnable " + z);
        callIpc("setMemParkEnable", Boolean.valueOf(z));
    }
}
