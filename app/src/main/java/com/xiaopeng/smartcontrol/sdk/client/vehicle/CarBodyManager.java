package com.xiaopeng.smartcontrol.sdk.client.vehicle;

import com.xiaopeng.smartcontrol.sdk.client.BaseManager;
import com.xiaopeng.smartcontrol.utils.Constants;
import com.xiaopeng.smartcontrol.utils.LogUtils;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class CarBodyManager extends BaseManager<ISignalCallback> {

    /* loaded from: classes2.dex */
    public interface ISignalCallback extends BaseManager.IBaseSignalReceiver {
        default void onChargePortChanged(boolean z, int i) {
        }

        default void onCwcChargeErrorStateChanged(boolean z) {
        }

        default void onCwcChargeStateChanged(int i) {
        }

        default void onCwcFRChargeErrorStateChanged(boolean z) {
        }

        default void onCwcFRChargeStateChanged(int i) {
        }

        default void onCwcFRSwChanged(boolean z) {
        }

        default void onCwcRLChargeErrorStateChanged(boolean z) {
        }

        default void onCwcRLChargeStateChanged(int i) {
        }

        default void onCwcRLSwChanged(boolean z) {
        }

        default void onCwcRRChargeErrorStateChanged(boolean z) {
        }

        default void onCwcRRChargeStateChanged(int i) {
        }

        default void onCwcRRSwChanged(boolean z) {
        }

        default void onCwcSwChanged(boolean z) {
        }

        default void onDoorChanged(int i, boolean z) {
        }

        default void onLeftSlideDoorModeChanged(int i) {
        }

        default void onLeftSlideDoorStateChanged(int i) {
        }

        default void onRightSlideDoorModeChanged(int i) {
        }

        default void onRightSlideDoorStateChanged(int i) {
        }

        default void onTrunkStateChanged(int i) {
        }

        default void onWiperSensitivityChanged(int i) {
        }
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getAppId() {
        return "carcontrol";
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final CarBodyManager INSTANCE = new CarBodyManager();

        private SingletonHolder() {
        }
    }

    private CarBodyManager() {
    }

    public static CarBodyManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getModuleName() {
        return Constants.CAR_CONTROL_MODULE.CAR_BODY_IMPL;
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
            case -2107882362:
                if (str.equals("onCwcRLSwChanged")) {
                    c = 0;
                    break;
                }
                break;
            case -1879087181:
                if (str.equals("onCwcFRChargeStateChanged")) {
                    c = 1;
                    break;
                }
                break;
            case -1718146655:
                if (str.equals("onCwcRLChargeStateChanged")) {
                    c = 2;
                    break;
                }
                break;
            case -1648127848:
                if (str.equals("onTrunkStateChanged")) {
                    c = 3;
                    break;
                }
                break;
            case -863481672:
                if (str.equals("onLeftSlideDoorModeChanged")) {
                    c = 4;
                    break;
                }
                break;
            case -604593503:
                if (str.equals("onWiperSensitivityChanged")) {
                    c = 5;
                    break;
                }
                break;
            case -534206308:
                if (str.equals("onLeftSlideDoorStateChanged")) {
                    c = 6;
                    break;
                }
                break;
            case 147532160:
                if (str.equals("onCwcSwChanged")) {
                    c = 7;
                    break;
                }
                break;
            case 521230208:
                if (str.equals("onChargePortChanged")) {
                    c = '\b';
                    break;
                }
                break;
            case 803731003:
                if (str.equals("onCwcFRChargeErrorStateChanged")) {
                    c = '\t';
                    break;
                }
                break;
            case 1008003904:
                if (str.equals("onCwcRRSwChanged")) {
                    c = '\n';
                    break;
                }
                break;
            case 1096583732:
                if (str.equals("onCwcFRSwChanged")) {
                    c = 11;
                    break;
                }
                break;
            case 1290960839:
                if (str.equals("onDoorChanged")) {
                    c = '\f';
                    break;
                }
                break;
            case 1391976583:
                if (str.equals("onCwcChargeErrorStateChanged")) {
                    c = '\r';
                    break;
                }
                break;
            case 1562946607:
                if (str.equals("onRightSlideDoorModeChanged")) {
                    c = 14;
                    break;
                }
                break;
            case 1647022663:
                if (str.equals("onCwcRRChargeErrorStateChanged")) {
                    c = 15;
                    break;
                }
                break;
            case 1670626309:
                if (str.equals("onRightSlideDoorStateChanged")) {
                    c = 16;
                    break;
                }
                break;
            case 1734426087:
                if (str.equals("onCwcChargeStateChanged")) {
                    c = 17;
                    break;
                }
                break;
            case 1804956711:
                if (str.equals("onCwcRRChargeStateChanged")) {
                    c = 18;
                    break;
                }
                break;
            case 2049063181:
                if (str.equals("onCwcRLChargeErrorStateChanged")) {
                    c = 19;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                boolean parseBoolean = Boolean.parseBoolean(str2);
                Iterator it2 = this.mCallback.iterator();
                while (it2.hasNext()) {
                    ((ISignalCallback) it2.next()).onCwcRLSwChanged(parseBoolean);
                }
                return;
            case 1:
                int parseInt = Integer.parseInt(str2);
                Iterator it3 = this.mCallback.iterator();
                while (it3.hasNext()) {
                    ((ISignalCallback) it3.next()).onCwcFRChargeStateChanged(parseInt);
                }
                return;
            case 2:
                int parseInt2 = Integer.parseInt(str2);
                Iterator it4 = this.mCallback.iterator();
                while (it4.hasNext()) {
                    ((ISignalCallback) it4.next()).onCwcRLChargeStateChanged(parseInt2);
                }
                return;
            case 3:
                int parseInt3 = Integer.parseInt(str2);
                Iterator it5 = this.mCallback.iterator();
                while (it5.hasNext()) {
                    ((ISignalCallback) it5.next()).onTrunkStateChanged(parseInt3);
                }
                return;
            case 4:
                int parseInt4 = Integer.parseInt(str2);
                Iterator it6 = this.mCallback.iterator();
                while (it6.hasNext()) {
                    ((ISignalCallback) it6.next()).onLeftSlideDoorModeChanged(parseInt4);
                }
                return;
            case 5:
                int parseInt5 = Integer.parseInt(str2);
                Iterator it7 = this.mCallback.iterator();
                while (it7.hasNext()) {
                    ((ISignalCallback) it7.next()).onWiperSensitivityChanged(parseInt5);
                }
                return;
            case 6:
                int parseInt6 = Integer.parseInt(str2);
                Iterator it8 = this.mCallback.iterator();
                while (it8.hasNext()) {
                    ((ISignalCallback) it8.next()).onLeftSlideDoorStateChanged(parseInt6);
                }
                return;
            case 7:
                boolean parseBoolean2 = Boolean.parseBoolean(str2);
                Iterator it9 = this.mCallback.iterator();
                while (it9.hasNext()) {
                    ((ISignalCallback) it9.next()).onCwcSwChanged(parseBoolean2);
                }
                return;
            case '\b':
                String[] split = str2.split(",");
                boolean parseBoolean3 = Boolean.parseBoolean(split[0]);
                int parseInt7 = Integer.parseInt(split[1]);
                Iterator it10 = this.mCallback.iterator();
                while (it10.hasNext()) {
                    ((ISignalCallback) it10.next()).onChargePortChanged(parseBoolean3, parseInt7);
                }
                return;
            case '\t':
                boolean parseBoolean4 = Boolean.parseBoolean(str2);
                Iterator it11 = this.mCallback.iterator();
                while (it11.hasNext()) {
                    ((ISignalCallback) it11.next()).onCwcFRChargeErrorStateChanged(parseBoolean4);
                }
                return;
            case '\n':
                boolean parseBoolean5 = Boolean.parseBoolean(str2);
                Iterator it12 = this.mCallback.iterator();
                while (it12.hasNext()) {
                    ((ISignalCallback) it12.next()).onCwcRRSwChanged(parseBoolean5);
                }
                return;
            case 11:
                boolean parseBoolean6 = Boolean.parseBoolean(str2);
                Iterator it13 = this.mCallback.iterator();
                while (it13.hasNext()) {
                    ((ISignalCallback) it13.next()).onCwcFRSwChanged(parseBoolean6);
                }
                return;
            case '\f':
                String[] split2 = str2.split(",");
                int parseInt8 = Integer.parseInt(split2[0]);
                boolean parseBoolean7 = Boolean.parseBoolean(split2[1]);
                Iterator it14 = this.mCallback.iterator();
                while (it14.hasNext()) {
                    ((ISignalCallback) it14.next()).onDoorChanged(parseInt8, parseBoolean7);
                }
                return;
            case '\r':
                boolean parseBoolean8 = Boolean.parseBoolean(str2);
                Iterator it15 = this.mCallback.iterator();
                while (it15.hasNext()) {
                    ((ISignalCallback) it15.next()).onCwcChargeErrorStateChanged(parseBoolean8);
                }
                return;
            case 14:
                int parseInt9 = Integer.parseInt(str2);
                Iterator it16 = this.mCallback.iterator();
                while (it16.hasNext()) {
                    ((ISignalCallback) it16.next()).onRightSlideDoorModeChanged(parseInt9);
                }
                return;
            case 15:
                boolean parseBoolean9 = Boolean.parseBoolean(str2);
                Iterator it17 = this.mCallback.iterator();
                while (it17.hasNext()) {
                    ((ISignalCallback) it17.next()).onCwcRRChargeErrorStateChanged(parseBoolean9);
                }
                return;
            case 16:
                int parseInt10 = Integer.parseInt(str2);
                Iterator it18 = this.mCallback.iterator();
                while (it18.hasNext()) {
                    ((ISignalCallback) it18.next()).onRightSlideDoorStateChanged(parseInt10);
                }
                return;
            case 17:
                int parseInt11 = Integer.parseInt(str2);
                Iterator it19 = this.mCallback.iterator();
                while (it19.hasNext()) {
                    ((ISignalCallback) it19.next()).onCwcChargeStateChanged(parseInt11);
                }
                return;
            case 18:
                int parseInt12 = Integer.parseInt(str2);
                Iterator it20 = this.mCallback.iterator();
                while (it20.hasNext()) {
                    ((ISignalCallback) it20.next()).onCwcRRChargeStateChanged(parseInt12);
                }
                return;
            case 19:
                boolean parseBoolean10 = Boolean.parseBoolean(str2);
                Iterator it21 = this.mCallback.iterator();
                while (it21.hasNext()) {
                    ((ISignalCallback) it21.next()).onCwcRLChargeErrorStateChanged(parseBoolean10);
                }
                return;
            default:
                return;
        }
    }

    public boolean isDoorClosed(int i) {
        String callIpc = callIpc("isDoorClosed", Integer.valueOf(i));
        LogUtils.i(this.TAG, "isDoorClosed, index: " + i + ", result: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return true;
    }

    public int getTrunkState() {
        String callIpc = callIpc("getTrunkState", null);
        LogUtils.i(this.TAG, "getTrunkState: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setTrunk(int i) {
        LogUtils.d(this.TAG, "setTrunk: " + i);
        callIpc("setTrunk", Integer.valueOf(i));
    }

    public int getChargePort(boolean z) {
        String callIpc = callIpc("getChargePort", Boolean.valueOf(z));
        LogUtils.i(this.TAG, "getChargePort: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setChargePort(boolean z, boolean z2) {
        String str = z + "," + z2;
        LogUtils.i(this.TAG, "setChargePort " + str);
        callIpc("setChargePort", str);
    }

    public int getWiperSensitivity() {
        String callIpc = callIpc("getWiperSensitivity", null);
        LogUtils.i(this.TAG, "getWiperSensitivity: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setWiperSensitivity(int i) {
        LogUtils.d(this.TAG, "setWiperSensitivity: " + i);
        callIpc("setWiperSensitivity", Integer.valueOf(i));
    }

    public int getLeftSlideDoorMode() {
        String callIpc = callIpc("getLeftSlideDoorMode", null);
        LogUtils.i(this.TAG, "getLeftSlideDoorMode: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setLeftSlideDoorMode(int i) {
        LogUtils.d(this.TAG, "setLeftSlideDoorMode: " + i);
        callIpc("setLeftSlideDoorMode", Integer.valueOf(i));
    }

    public int getRightSlideDoorMode() {
        String callIpc = callIpc("getRightSlideDoorMode", null);
        LogUtils.i(this.TAG, "getRightSlideDoorMode: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setRightSlideDoorMode(int i) {
        LogUtils.d(this.TAG, "setRightSlideDoorMode: " + i);
        callIpc("setRightSlideDoorMode", Integer.valueOf(i));
    }

    public int getLeftSlideDoorState() {
        String callIpc = callIpc("getLeftSlideDoorState", null);
        LogUtils.i(this.TAG, "getLeftSlideDoorState: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public int getRightSlideDoorState() {
        String callIpc = callIpc("getRightSlideDoorState", null);
        LogUtils.i(this.TAG, "getRightSlideDoorState: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void controlLeftSlideDoor(int i) {
        LogUtils.d(this.TAG, "controlLeftSlideDoor: " + i);
        callIpc("controlLeftSlideDoor", Integer.valueOf(i));
    }

    public void controlRightSlideDoor(int i) {
        LogUtils.d(this.TAG, "controlRightSlideDoor: " + i);
        callIpc("controlRightSlideDoor", Integer.valueOf(i));
    }

    public boolean getCwcSwEnable() {
        String callIpc = callIpc("getCwcSwEnable", null);
        LogUtils.i(this.TAG, "getCwcSwEnable: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public void setCwcSwEnable(boolean z) {
        LogUtils.d(this.TAG, "setCwcSwEnable: " + z);
        callIpc("setCwcSwEnable", Boolean.valueOf(z));
    }

    public boolean getCwcFRSwEnable() {
        String callIpc = callIpc("getCwcFRSwEnable", null);
        LogUtils.i(this.TAG, "getCwcFRSwEnable: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public void setCwcSwFREnable(boolean z) {
        LogUtils.d(this.TAG, "setCwcSwFREnable: " + z);
        callIpc("setCwcSwFREnable", Boolean.valueOf(z));
    }

    public boolean getCwcRLSwEnable() {
        String callIpc = callIpc("getCwcRLSwEnable", null);
        LogUtils.i(this.TAG, "getCwcRLSwEnable: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public void setCwcSwRLEnable(boolean z) {
        LogUtils.d(this.TAG, "setCwcSwRLEnable: " + z);
        callIpc("setCwcSwRLEnable", Boolean.valueOf(z));
    }

    public boolean getCwcRRSwEnable() {
        String callIpc = callIpc("getCwcRRSwEnable", null);
        LogUtils.i(this.TAG, "getCwcRRSwEnable: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public void setCwcSwRREnable(boolean z) {
        LogUtils.d(this.TAG, "setCwcSwRREnable: " + z);
        callIpc("setCwcSwRREnable", Boolean.valueOf(z));
    }

    public int getCwcChargeSt() {
        String callIpc = callIpc("getCwcChargeSt", null);
        LogUtils.i(this.TAG, "getCwcChargeSt: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean getCwcChargeErrorSt() {
        String callIpc = callIpc("getCwcChargeErrorSt", null);
        LogUtils.i(this.TAG, "getCwcChargeErrorSt: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public int getCwcFRChargeSt() {
        String callIpc = callIpc("getCwcFRChargeSt", null);
        LogUtils.i(this.TAG, "getCwcFRChargeSt: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean getCwcFRChargeErrorSt() {
        String callIpc = callIpc("getCwcFRChargeErrorSt", null);
        LogUtils.i(this.TAG, "getCwcFRChargeErrorSt: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public int getCwcRLChargeSt() {
        String callIpc = callIpc("getCwcRLChargeSt", null);
        LogUtils.i(this.TAG, "getCwcRLChargeSt: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean getCwcRLChargeErrorSt() {
        String callIpc = callIpc("getCwcRLChargeErrorSt", null);
        LogUtils.i(this.TAG, "getCwcRLChargeErrorSt: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }

    public int getCwcRRChargeSt() {
        String callIpc = callIpc("getCwcRRChargeSt", null);
        LogUtils.i(this.TAG, "getCwcRRChargeSt: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean getCwcRRChargeErrorSt() {
        String callIpc = callIpc("getCwcRRChargeErrorSt", null);
        LogUtils.i(this.TAG, "getCwcRRChargeErrorSt: " + callIpc);
        if (callIpc != null) {
            return Boolean.parseBoolean(callIpc);
        }
        return false;
    }
}
