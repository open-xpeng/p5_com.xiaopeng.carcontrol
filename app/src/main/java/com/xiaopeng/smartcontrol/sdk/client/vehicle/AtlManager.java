package com.xiaopeng.smartcontrol.sdk.client.vehicle;

import com.xiaopeng.smartcontrol.sdk.client.BaseManager;
import com.xiaopeng.smartcontrol.utils.Constants;
import com.xiaopeng.smartcontrol.utils.LogUtils;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class AtlManager extends BaseManager<ISignalCallback> {

    /* loaded from: classes2.dex */
    public interface ISignalCallback extends BaseManager.IBaseSignalReceiver {
        void onAtlBrightnessChanged(int i);

        void onAtlColorModeChanged(int i);

        void onAtlEffectChanged(int i);

        void onAtlSwChanged(boolean z);

        void onDoubleColorChanged(int i);

        void onSingleColorChanged(int i);
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getAppId() {
        return "carcontrol";
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final AtlManager INSTANCE = new AtlManager();

        private SingletonHolder() {
        }
    }

    private AtlManager() {
    }

    public static AtlManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getModuleName() {
        return Constants.CAR_CONTROL_MODULE.ATL_IMPL;
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
            case -1691876216:
                if (str.equals("onAtlColorModeChanged")) {
                    c = 0;
                    break;
                }
                break;
            case -1668377471:
                if (str.equals("onDoubleColorChanged")) {
                    c = 1;
                    break;
                }
                break;
            case -783368042:
                if (str.equals("onAtlSwChanged")) {
                    c = 2;
                    break;
                }
                break;
            case -747920776:
                if (str.equals("onSingleColorChanged")) {
                    c = 3;
                    break;
                }
                break;
            case 825865673:
                if (str.equals("onAtlEffectChanged")) {
                    c = 4;
                    break;
                }
                break;
            case 933775369:
                if (str.equals("onAtlBrightnessChanged")) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                int parseInt = Integer.parseInt(str2);
                Iterator it2 = this.mCallback.iterator();
                while (it2.hasNext()) {
                    ((ISignalCallback) it2.next()).onAtlColorModeChanged(parseInt);
                }
                return;
            case 1:
                int parseInt2 = Integer.parseInt(str2);
                Iterator it3 = this.mCallback.iterator();
                while (it3.hasNext()) {
                    ((ISignalCallback) it3.next()).onDoubleColorChanged(parseInt2);
                }
                return;
            case 2:
                boolean parseBoolean = Boolean.parseBoolean(str2);
                Iterator it4 = this.mCallback.iterator();
                while (it4.hasNext()) {
                    ((ISignalCallback) it4.next()).onAtlSwChanged(parseBoolean);
                }
                return;
            case 3:
                int parseInt3 = Integer.parseInt(str2);
                Iterator it5 = this.mCallback.iterator();
                while (it5.hasNext()) {
                    ((ISignalCallback) it5.next()).onSingleColorChanged(parseInt3);
                }
                return;
            case 4:
                int parseInt4 = Integer.parseInt(str2);
                Iterator it6 = this.mCallback.iterator();
                while (it6.hasNext()) {
                    ((ISignalCallback) it6.next()).onAtlEffectChanged(parseInt4);
                }
                return;
            case 5:
                int parseInt5 = Integer.parseInt(str2);
                Iterator it7 = this.mCallback.iterator();
                while (it7.hasNext()) {
                    ((ISignalCallback) it7.next()).onAtlBrightnessChanged(parseInt5);
                }
                return;
            default:
                return;
        }
    }

    public boolean isAtlSwEnable() {
        String callIpc = callIpc("isAtlSwEnable", null);
        LogUtils.i(this.TAG, "isAtlSwEnable: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public void setAtlSw(boolean z) {
        LogUtils.i(this.TAG, "setAtlSw " + z);
        callIpc("setAtlSw", Boolean.valueOf(z));
    }

    public int getAtlEffect() {
        String callIpc = callIpc("getAtlEffect", null);
        LogUtils.i(this.TAG, "getAtlEffect: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setAtlEffect(int i) {
        LogUtils.i(this.TAG, "setAtlEffect " + i);
        callIpc("setAtlEffect", Integer.valueOf(i));
    }

    public int getAtlColorMode() {
        String callIpc = callIpc("getAtlColorMode", null);
        LogUtils.i(this.TAG, "getAtlColorMode: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setAtlColorMode(int i) {
        LogUtils.i(this.TAG, "setAtlColorMode " + i);
        callIpc("setAtlColorMode", Integer.valueOf(i));
    }

    public int getSingleColor() {
        String callIpc = callIpc("getSingleColor", null);
        LogUtils.i(this.TAG, "getSingleColor: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setSingleColor(int i) {
        LogUtils.i(this.TAG, "setSingleColor " + i);
        callIpc("setSingleColor", Integer.valueOf(i));
    }

    public int getDoubleColor() {
        String callIpc = callIpc("getDoubleColor", null);
        LogUtils.i(this.TAG, "getDoubleColor: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setDoubleColor(int i) {
        LogUtils.i(this.TAG, "setDoubleColor " + i);
        callIpc("setDoubleColor", Integer.valueOf(i));
    }

    public int getAtlBrightness() {
        String callIpc = callIpc("getAtlBrightness", null);
        LogUtils.i(this.TAG, "getAtlBrightness: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setAtlBrightness(int i) {
        LogUtils.i(this.TAG, "setAtlBrightness " + i);
        callIpc("setAtlBrightness", Integer.valueOf(i));
    }
}
