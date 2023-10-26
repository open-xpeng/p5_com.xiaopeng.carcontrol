package com.xiaopeng.smartcontrol.sdk.client.powercenter;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaopeng.smartcontrol.sdk.client.BaseManager;
import com.xiaopeng.smartcontrol.utils.Constants;
import com.xiaopeng.smartcontrol.utils.LogUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class PowerCenterManager extends BaseManager<ISignalCallback> {

    /* loaded from: classes2.dex */
    public interface ISignalCallback extends BaseManager.IBaseSignalReceiver {
        default void onChargeGunStatusChanged(int i) {
        }

        default void onChargeLimitChanged(int i) {
        }

        default void onChargeStatusChanged(int i) {
        }

        default void onDisChargeLimitChanged(int i) {
        }

        default void onKeepTempModeChanged(boolean z) {
        }

        default void onPowerCenterPageStatusChanged(int i) {
        }

        default void onPowerDataChanged(Map<String, Float> map) {
        }

        default void onRemainingDistanceChanged(float f) {
        }

        default void onSocChanged(int i) {
        }

        default void onSuperChargeFlagChanged(boolean z) {
        }
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getAppId() {
        return Constants.APPID.POWERCENTER;
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final PowerCenterManager INSTANCE = new PowerCenterManager();

        private SingletonHolder() {
        }
    }

    public static PowerCenterManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getModuleName() {
        return Constants.CAR_CONTROL_MODULE.PowerCenter_IMPL;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    public void onIpcReceived(String str, String str2) {
        LogUtils.i(this.TAG, str + " " + str2);
        Iterator<BaseManager.IRawSignalReceiver> it = this.mRawCallback.iterator();
        while (it.hasNext()) {
            it.next().onIpcReceived(str, str2);
        }
        Iterator it2 = this.mCallback.iterator();
        while (it2.hasNext()) {
            ISignalCallback iSignalCallback = (ISignalCallback) it2.next();
            str.hashCode();
            char c = 65535;
            switch (str.hashCode()) {
                case -1994414484:
                    if (str.equals("onSocChanged")) {
                        c = 0;
                        break;
                    }
                    break;
                case -953543944:
                    if (str.equals("onPowerCenterPageStatusChanged")) {
                        c = 1;
                        break;
                    }
                    break;
                case -580051025:
                    if (str.equals("onChargeStatusChanged")) {
                        c = 2;
                        break;
                    }
                    break;
                case -39553927:
                    if (str.equals("onKeepTempModeChanged")) {
                        c = 3;
                        break;
                    }
                    break;
                case 336094564:
                    if (str.equals("onPowerDataChanged")) {
                        c = 4;
                        break;
                    }
                    break;
                case 852069340:
                    if (str.equals("onDisChargeLimitChanged")) {
                        c = 5;
                        break;
                    }
                    break;
                case 939091160:
                    if (str.equals("onSuperChargeFlagChanged")) {
                        c = 6;
                        break;
                    }
                    break;
                case 1252511628:
                    if (str.equals("onChargeLimitChanged")) {
                        c = 7;
                        break;
                    }
                    break;
                case 1420579989:
                    if (str.equals("onChargeGunStatusChanged")) {
                        c = '\b';
                        break;
                    }
                    break;
                case 2003821864:
                    if (str.equals("onRemainingDistanceChanged")) {
                        c = '\t';
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    iSignalCallback.onSocChanged(Integer.parseInt(str2));
                    break;
                case 1:
                    iSignalCallback.onPowerCenterPageStatusChanged(Integer.parseInt(str2));
                    break;
                case 2:
                    iSignalCallback.onChargeStatusChanged(Integer.parseInt(str2));
                    break;
                case 3:
                    iSignalCallback.onKeepTempModeChanged(Boolean.parseBoolean(str2));
                    break;
                case 4:
                    str2 = str2.replace('=', ':');
                    HashMap hashMap = new HashMap();
                    JSONObject parseObject = JSON.parseObject(str2);
                    for (String str3 : parseObject.keySet()) {
                        hashMap.put(str3.toString(), Float.valueOf(Float.parseFloat(parseObject.get(str3).toString())));
                    }
                    iSignalCallback.onPowerDataChanged(hashMap);
                    break;
                case 5:
                    iSignalCallback.onDisChargeLimitChanged(Integer.parseInt(str2));
                    break;
                case 6:
                    iSignalCallback.onSuperChargeFlagChanged(Boolean.parseBoolean(str2));
                    break;
                case 7:
                    iSignalCallback.onChargeLimitChanged(Integer.parseInt(str2));
                    break;
                case '\b':
                    iSignalCallback.onChargeGunStatusChanged(Integer.parseInt(str2));
                    break;
                case '\t':
                    iSignalCallback.onRemainingDistanceChanged(Float.parseFloat(str2));
                    break;
            }
        }
    }

    public void startPowerCenter() {
        callIpc("startPowerCenter", "");
    }

    public int getChargeStatus() {
        String callIpc = callIpc("getChargeStatus", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public int getChargeGunStatus() {
        String callIpc = callIpc("getChargeGunStatus", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean isSuperCharge() {
        String callIpc = callIpc("isSuperCharge", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public Map<String, Float> getPowerData() {
        String replace = callIpc("getPowerData", "").replace('=', ':');
        Log.d(this.TAG, "getPowerData: " + replace);
        HashMap hashMap = new HashMap();
        JSONObject parseObject = JSON.parseObject(replace);
        for (String str : parseObject.keySet()) {
            hashMap.put(str.toString(), Float.valueOf(Float.parseFloat(parseObject.get(str).toString())));
        }
        return hashMap;
    }

    public float getRemainingDistance() {
        String callIpc = callIpc("getRemainingDistance", "");
        if (callIpc != null) {
            return Float.parseFloat(callIpc);
        }
        return 0.0f;
    }

    public void startCharge() {
        callIpc("startCharge", "");
    }

    public void startDischarge() {
        callIpc("startDischarge", "");
    }

    public void stopCharge() {
        callIpc("stopCharge", "");
    }

    public void stopDischarge() {
        callIpc("stopDischarge", "");
    }

    public void startPreheat() {
        callIpc("startPreheat", "");
    }

    public void stopPreheat() {
        callIpc("stopPreheat", "");
    }

    public int getSoc() {
        String callIpc = callIpc("getSoc", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public int getChargeLimit() {
        String callIpc = callIpc("getChargeLimit", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public int getDischargeLimit() {
        String callIpc = callIpc("getDischargeLimit", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }
}
