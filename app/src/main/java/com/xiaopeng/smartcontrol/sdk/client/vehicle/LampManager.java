package com.xiaopeng.smartcontrol.sdk.client.vehicle;

import com.xiaopeng.smartcontrol.sdk.client.BaseManager;
import com.xiaopeng.smartcontrol.utils.Constants;
import com.xiaopeng.smartcontrol.utils.LogUtils;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class LampManager extends BaseManager<ISignalCallback> {

    /* loaded from: classes2.dex */
    public interface ISignalCallback extends BaseManager.IBaseSignalReceiver {
        default void onBrakeLightChanged(boolean z) {
        }

        default void onDaytimeRunningLightChanged(int i) {
        }

        default void onDoubleFlashBeamChanged(int i) {
        }

        default void onHighBeamChanged(boolean z) {
        }

        default void onIhbStateChanged(int i) {
        }

        default void onLowBeamChanged(boolean z) {
        }

        default void onPositionBeamChanged(boolean z) {
        }

        default void onRearFogLampChanged(boolean z) {
        }

        default void onReverseLampChanged(boolean z) {
        }

        default void onSaberModeChanged(int i) {
        }
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getAppId() {
        return "carcontrol";
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final LampManager INSTANCE = new LampManager();

        private SingletonHolder() {
        }
    }

    private LampManager() {
    }

    public static LampManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getModuleName() {
        return Constants.CAR_CONTROL_MODULE.LAMP_IMPL;
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
            case -1561930460:
                if (str.equals("onHighBeamChanged")) {
                    c = 0;
                    break;
                }
                break;
            case -1371018823:
                if (str.equals("onReverseLampChanged")) {
                    c = 1;
                    break;
                }
                break;
            case -699220025:
                if (str.equals("onIhbStateChanged")) {
                    c = 2;
                    break;
                }
                break;
            case -585429851:
                if (str.equals("onDoubleFlashBeamChanged")) {
                    c = 3;
                    break;
                }
                break;
            case -251133078:
                if (str.equals("onBrakeLightChanged")) {
                    c = 4;
                    break;
                }
                break;
            case -190459341:
                if (str.equals("onDaytimeRunningLightChanged")) {
                    c = 5;
                    break;
                }
                break;
            case 269055677:
                if (str.equals("onPositionBeamChanged")) {
                    c = 6;
                    break;
                }
                break;
            case 851723216:
                if (str.equals("onLowBeamChanged")) {
                    c = 7;
                    break;
                }
                break;
            case 987291983:
                if (str.equals("onSaberModeChanged")) {
                    c = '\b';
                    break;
                }
                break;
            case 1085121825:
                if (str.equals("onRearFogLampChanged")) {
                    c = '\t';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                boolean parseBoolean = Boolean.parseBoolean(str2);
                Iterator it2 = this.mCallback.iterator();
                while (it2.hasNext()) {
                    ((ISignalCallback) it2.next()).onHighBeamChanged(parseBoolean);
                }
                return;
            case 1:
                boolean parseBoolean2 = Boolean.parseBoolean(str2);
                Iterator it3 = this.mCallback.iterator();
                while (it3.hasNext()) {
                    ((ISignalCallback) it3.next()).onReverseLampChanged(parseBoolean2);
                }
                return;
            case 2:
                int parseInt = Integer.parseInt(str2);
                Iterator it4 = this.mCallback.iterator();
                while (it4.hasNext()) {
                    ((ISignalCallback) it4.next()).onIhbStateChanged(parseInt);
                }
                return;
            case 3:
                int parseInt2 = Integer.parseInt(str2);
                Iterator it5 = this.mCallback.iterator();
                while (it5.hasNext()) {
                    ((ISignalCallback) it5.next()).onDoubleFlashBeamChanged(parseInt2);
                }
                return;
            case 4:
                boolean parseBoolean3 = Boolean.parseBoolean(str2);
                Iterator it6 = this.mCallback.iterator();
                while (it6.hasNext()) {
                    ((ISignalCallback) it6.next()).onBrakeLightChanged(parseBoolean3);
                }
                return;
            case 5:
                int parseInt3 = Integer.parseInt(str2);
                Iterator it7 = this.mCallback.iterator();
                while (it7.hasNext()) {
                    ((ISignalCallback) it7.next()).onDaytimeRunningLightChanged(parseInt3);
                }
                return;
            case 6:
                boolean parseBoolean4 = Boolean.parseBoolean(str2);
                Iterator it8 = this.mCallback.iterator();
                while (it8.hasNext()) {
                    ((ISignalCallback) it8.next()).onPositionBeamChanged(parseBoolean4);
                }
                return;
            case 7:
                boolean parseBoolean5 = Boolean.parseBoolean(str2);
                Iterator it9 = this.mCallback.iterator();
                while (it9.hasNext()) {
                    ((ISignalCallback) it9.next()).onLowBeamChanged(parseBoolean5);
                }
                return;
            case '\b':
                int parseInt4 = Integer.parseInt(str2);
                Iterator it10 = this.mCallback.iterator();
                while (it10.hasNext()) {
                    ((ISignalCallback) it10.next()).onSaberModeChanged(parseInt4);
                }
                return;
            case '\t':
                boolean parseBoolean6 = Boolean.parseBoolean(str2);
                Iterator it11 = this.mCallback.iterator();
                while (it11.hasNext()) {
                    ((ISignalCallback) it11.next()).onRearFogLampChanged(parseBoolean6);
                }
                return;
            default:
                return;
        }
    }

    public int getDoubleFlashBeamState() {
        String callIpc = callIpc("getDoubleFlashBeamState", null);
        LogUtils.i(this.TAG, "getDoubleFlashBeamState: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean isPositionBeamOn() {
        String callIpc = callIpc("isPositionBeamOn", null);
        LogUtils.i(this.TAG, "isPositionBeamOn: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public boolean isLowBeamOn() {
        String callIpc = callIpc("isLowBeamOn", null);
        LogUtils.i(this.TAG, "isLowBeamOn: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public boolean isHighBeamOn() {
        String callIpc = callIpc("isHighBeamOn", null);
        LogUtils.i(this.TAG, "isHighBeamOn: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public int getSaberMode() {
        String callIpc = callIpc("getSaberMode", null);
        LogUtils.i(this.TAG, "getSaberMode: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean isRearFogLampOn() {
        String callIpc = callIpc("isRearFogLampOn", null);
        LogUtils.i(this.TAG, "isRearFogLampOn: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public boolean isReverseLampOn() {
        String callIpc = callIpc("isReverseLampOn", null);
        LogUtils.i(this.TAG, "isReverseLampOn: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public boolean getBrakeLightOnOff() {
        String callIpc = callIpc("getBrakeLightOnOff", null);
        LogUtils.i(this.TAG, "getBrakeLightOnOff: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public int getDaytimeRunningLightsOutputStatus() {
        String callIpc = callIpc("getBrakeLightOnOff", null);
        LogUtils.i(this.TAG, "getBrakeLightOnOff: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public int getIhbState() {
        String callIpc = callIpc("getIhbState", null);
        LogUtils.i(this.TAG, "getIhbState: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setIhbSw(boolean z) {
        LogUtils.d(this.TAG, "setIhbSw: " + z);
        callIpc("setIhbSw", Boolean.valueOf(z));
    }
}
