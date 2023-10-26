package com.xiaopeng.smartcontrol.sdk.client.vehicle;

import com.xiaopeng.smartcontrol.sdk.client.BaseManager;
import com.xiaopeng.smartcontrol.utils.Constants;
import com.xiaopeng.smartcontrol.utils.LogUtils;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class WindowControlManager extends BaseManager<ISignalCallback> {

    /* loaded from: classes2.dex */
    public interface ISignalCallback extends BaseManager.IBaseSignalReceiver {
        default void onWindowLockStateChanged(boolean z) {
        }

        default void onWindowPosChanged(int i, float f) {
        }

        default void onWindowStateChanged(int i) {
        }
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getAppId() {
        return "carcontrol";
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final WindowControlManager INSTANCE = new WindowControlManager();

        private SingletonHolder() {
        }
    }

    private WindowControlManager() {
    }

    public static WindowControlManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getModuleName() {
        return Constants.CAR_CONTROL_MODULE.WINDOW_IMPL;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    public void onIpcReceived(String str, String str2) {
        LogUtils.i(this.TAG, str + " " + str2);
        Iterator<BaseManager.IRawSignalReceiver> it = this.mRawCallback.iterator();
        while (it.hasNext()) {
            it.next().onIpcReceived(str, str2);
        }
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -968779470:
                if (str.equals("onWindowStateChanged")) {
                    c = 0;
                    break;
                }
                break;
            case -677410179:
                if (str.equals("onWindowLockStateChanged")) {
                    c = 1;
                    break;
                }
                break;
            case 111870415:
                if (str.equals("onWindowPosChanged")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                int parseInt = Integer.parseInt(str2);
                Iterator it2 = this.mCallback.iterator();
                while (it2.hasNext()) {
                    ((ISignalCallback) it2.next()).onWindowStateChanged(parseInt);
                }
                return;
            case 1:
                boolean parseBoolean = Boolean.parseBoolean(str2);
                Iterator it3 = this.mCallback.iterator();
                while (it3.hasNext()) {
                    ((ISignalCallback) it3.next()).onWindowLockStateChanged(parseBoolean);
                }
                return;
            case 2:
                String[] split = str2.split(",");
                int parseInt2 = Integer.parseInt(split[0]);
                float parseFloat = Float.parseFloat(split[1]);
                Iterator it4 = this.mCallback.iterator();
                while (it4.hasNext()) {
                    ((ISignalCallback) it4.next()).onWindowPosChanged(parseInt2, parseFloat);
                }
                return;
            default:
                return;
        }
    }

    public void controlWindow(int i) {
        LogUtils.i(this.TAG, "controlWindow " + i);
        callIpc("controlWindow", Integer.valueOf(i));
    }

    public float getWindowPos(int i) {
        String callIpc = callIpc("getWindowPos", Integer.valueOf(i));
        LogUtils.i(this.TAG, "getWindowPos: " + callIpc);
        if (callIpc != null) {
            return Float.parseFloat(callIpc);
        }
        return 0.0f;
    }

    public void setWindowPos(int i, float f) {
        String str = i + "," + f;
        LogUtils.i(this.TAG, "setWindowPos " + str);
        callIpc("setWindowPos", str);
    }

    public boolean isWindowLockActive() {
        String callIpc = callIpc("isWindowLockActive", null);
        LogUtils.i(this.TAG, "isWindowLockActive: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public void setWindowLockActive(boolean z) {
        LogUtils.i(this.TAG, "setWindowLockActive " + z);
        callIpc("setWindowLockActive", Boolean.valueOf(z));
    }
}
