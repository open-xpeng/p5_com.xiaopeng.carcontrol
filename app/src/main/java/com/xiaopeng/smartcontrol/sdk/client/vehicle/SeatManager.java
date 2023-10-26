package com.xiaopeng.smartcontrol.sdk.client.vehicle;

import com.xiaopeng.smartcontrol.sdk.client.BaseManager;
import com.xiaopeng.smartcontrol.utils.Constants;
import com.xiaopeng.smartcontrol.utils.LogUtils;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class SeatManager extends BaseManager<ISignalCallback> {

    /* loaded from: classes2.dex */
    public interface ISignalCallback extends BaseManager.IBaseSignalReceiver {
        default void onDrvCushionExtPosChanged(int i) {
        }

        default void onDrvHorPosChanged(int i) {
        }

        default void onDrvLegPosChanged(int i) {
        }

        default void onDrvTiltPosChanged(int i) {
        }

        default void onDrvVerPosChanged(int i) {
        }

        default void onMassagerStatusChange(int i, String str, int i2, boolean z) {
        }

        default void onPsnHorPosChanged(int i) {
        }

        default void onPsnLegPosChanged(int i) {
        }

        default void onPsnTiltPosChanged(int i) {
        }

        default void onPsnVerPosChanged(int i) {
        }

        default void onRLSeatNameChanged(int i, String str) {
        }

        default void onRLSeatSelectIdChanged(int i) {
        }

        default void onRRSeatNameChanged(int i, String str) {
        }

        default void onRRSeatSelectIdChanged(int i) {
        }

        default void onRhythmStatusChange(int i, int i2, int i3, boolean z) {
        }

        default void onRlSeatHorPosChanged(int i) {
        }

        default void onRlSeatLegHorPosChanged(int i) {
        }

        default void onRlSeatTiltPosChanged(int i) {
        }

        default void onRrSeatHorPosChanged(int i) {
        }

        default void onRrSeatLegHorPosChanged(int i) {
        }

        default void onRrSeatTiltPosChanged(int i) {
        }

        default void onSecRowLtSeatZeroGravChanged(int i) {
        }

        default void onSecRowRtSeatZeroGravChanged(int i) {
        }

        default void onTrdRowLtSeatTiltPosChangedpos(int i) {
        }

        default void onTrdRowRtSeatTiltPosChanged(int i) {
        }
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getAppId() {
        return "carcontrol";
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final SeatManager INSTANCE = new SeatManager();

        private SingletonHolder() {
        }
    }

    private SeatManager() {
    }

    public static SeatManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getModuleName() {
        return Constants.CAR_CONTROL_MODULE.SEAT_IMPL;
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
            case -2119147614:
                if (str.equals("onDrvHorPosChanged")) {
                    c = 0;
                    break;
                }
                break;
            case -2073332825:
                if (str.equals("onRrSeatHorPosChanged")) {
                    c = 1;
                    break;
                }
                break;
            case -1996991067:
                if (str.equals("onDrvLegPosChanged")) {
                    c = 2;
                    break;
                }
                break;
            case -1978913449:
                if (str.equals("onTrdRowRtSeatTiltPosChanged")) {
                    c = 3;
                    break;
                }
                break;
            case -1322768581:
                if (str.equals("onRlSeatLegHorPosChanged")) {
                    c = 4;
                    break;
                }
                break;
            case -997266271:
                if (str.equals("onRrSeatTiltPosChanged")) {
                    c = 5;
                    break;
                }
                break;
            case -846919719:
                if (str.equals("onRRSeatSelectIdChanged")) {
                    c = 6;
                    break;
                }
                break;
            case -428476585:
                if (str.equals("onSecRowLtSeatZeroGravChanged")) {
                    c = 7;
                    break;
                }
                break;
            case -385866003:
                if (str.equals("onRlSeatHorPosChanged")) {
                    c = '\b';
                    break;
                }
                break;
            case -225402341:
                if (str.equals("onRlSeatTiltPosChanged")) {
                    c = '\t';
                    break;
                }
                break;
            case -54663103:
                if (str.equals("onRrSeatLegHorPosChanged")) {
                    c = '\n';
                    break;
                }
                break;
            case 32004631:
                if (str.equals("onTrdRowLtSeatTiltPosChangedpos")) {
                    c = 11;
                    break;
                }
                break;
            case 497928375:
                if (str.equals("onPsnVerPosChanged")) {
                    c = '\f';
                    break;
                }
                break;
            case 634335435:
                if (str.equals("onRLSeatNameChanged")) {
                    c = '\r';
                    break;
                }
                break;
            case 839628893:
                if (str.equals("onSecRowRtSeatZeroGravChanged")) {
                    c = 14;
                    break;
                }
                break;
            case 1037132968:
                if (str.equals("onMassagerStatusChange")) {
                    c = 15;
                    break;
                }
                break;
            case 1072317690:
                if (str.equals("onDrvVerPosChanged")) {
                    c = 16;
                    break;
                }
                break;
            case 1251242985:
                if (str.equals("onPsnTiltPosChanged")) {
                    c = 17;
                    break;
                }
                break;
            case 1601430367:
                if (str.equals("onPsnHorPosChanged")) {
                    c = 18;
                    break;
                }
                break;
            case 1606025631:
                if (str.equals("onRLSeatSelectIdChanged")) {
                    c = 19;
                    break;
                }
                break;
            case 1723586914:
                if (str.equals("onPsnLegPosChanged")) {
                    c = 20;
                    break;
                }
                break;
            case 1866097669:
                if (str.equals("onRRSeatNameChanged")) {
                    c = 21;
                    break;
                }
                break;
            case 1877442566:
                if (str.equals("onDrvTiltPosChanged")) {
                    c = 22;
                    break;
                }
                break;
            case 1948378313:
                if (str.equals("onDrvCushionExtPosChanged")) {
                    c = 23;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                int parseInt = Integer.parseInt(str2);
                Iterator it2 = this.mCallback.iterator();
                while (it2.hasNext()) {
                    ((ISignalCallback) it2.next()).onDrvHorPosChanged(parseInt);
                }
                return;
            case 1:
                int parseInt2 = Integer.parseInt(str2);
                Iterator it3 = this.mCallback.iterator();
                while (it3.hasNext()) {
                    ((ISignalCallback) it3.next()).onRrSeatHorPosChanged(parseInt2);
                }
                return;
            case 2:
                int parseInt3 = Integer.parseInt(str2);
                Iterator it4 = this.mCallback.iterator();
                while (it4.hasNext()) {
                    ((ISignalCallback) it4.next()).onDrvLegPosChanged(parseInt3);
                }
                return;
            case 3:
                int parseInt4 = Integer.parseInt(str2);
                Iterator it5 = this.mCallback.iterator();
                while (it5.hasNext()) {
                    ((ISignalCallback) it5.next()).onTrdRowRtSeatTiltPosChanged(parseInt4);
                }
                return;
            case 4:
                int parseInt5 = Integer.parseInt(str2);
                Iterator it6 = this.mCallback.iterator();
                while (it6.hasNext()) {
                    ((ISignalCallback) it6.next()).onRlSeatLegHorPosChanged(parseInt5);
                }
                return;
            case 5:
                int parseInt6 = Integer.parseInt(str2);
                Iterator it7 = this.mCallback.iterator();
                while (it7.hasNext()) {
                    ((ISignalCallback) it7.next()).onRrSeatTiltPosChanged(parseInt6);
                }
                return;
            case 6:
                int parseInt7 = Integer.parseInt(str2);
                Iterator it8 = this.mCallback.iterator();
                while (it8.hasNext()) {
                    ((ISignalCallback) it8.next()).onRRSeatSelectIdChanged(parseInt7);
                }
                return;
            case 7:
                int parseInt8 = Integer.parseInt(str2);
                Iterator it9 = this.mCallback.iterator();
                while (it9.hasNext()) {
                    ((ISignalCallback) it9.next()).onSecRowLtSeatZeroGravChanged(parseInt8);
                }
                return;
            case '\b':
                int parseInt9 = Integer.parseInt(str2);
                Iterator it10 = this.mCallback.iterator();
                while (it10.hasNext()) {
                    ((ISignalCallback) it10.next()).onRlSeatHorPosChanged(parseInt9);
                }
                return;
            case '\t':
                int parseInt10 = Integer.parseInt(str2);
                Iterator it11 = this.mCallback.iterator();
                while (it11.hasNext()) {
                    ((ISignalCallback) it11.next()).onRlSeatTiltPosChanged(parseInt10);
                }
                return;
            case '\n':
                int parseInt11 = Integer.parseInt(str2);
                Iterator it12 = this.mCallback.iterator();
                while (it12.hasNext()) {
                    ((ISignalCallback) it12.next()).onRrSeatLegHorPosChanged(parseInt11);
                }
                return;
            case 11:
                int parseInt12 = Integer.parseInt(str2);
                Iterator it13 = this.mCallback.iterator();
                while (it13.hasNext()) {
                    ((ISignalCallback) it13.next()).onTrdRowLtSeatTiltPosChangedpos(parseInt12);
                }
                return;
            case '\f':
                int parseInt13 = Integer.parseInt(str2);
                Iterator it14 = this.mCallback.iterator();
                while (it14.hasNext()) {
                    ((ISignalCallback) it14.next()).onPsnVerPosChanged(parseInt13);
                }
                return;
            case '\r':
                String[] split = str2.split(",");
                int parseInt14 = Integer.parseInt(split[0]);
                String str3 = split[1];
                Iterator it15 = this.mCallback.iterator();
                while (it15.hasNext()) {
                    ((ISignalCallback) it15.next()).onRLSeatNameChanged(parseInt14, str3);
                }
                return;
            case 14:
                int parseInt15 = Integer.parseInt(str2);
                Iterator it16 = this.mCallback.iterator();
                while (it16.hasNext()) {
                    ((ISignalCallback) it16.next()).onSecRowRtSeatZeroGravChanged(parseInt15);
                }
                return;
            case 15:
                String[] split2 = str2.split(",");
                int parseInt16 = Integer.parseInt(split2[0]);
                String str4 = split2[1];
                int parseInt17 = Integer.parseInt(split2[2]);
                boolean parseBoolean = Boolean.parseBoolean(split2[3]);
                Iterator it17 = this.mCallback.iterator();
                while (it17.hasNext()) {
                    ((ISignalCallback) it17.next()).onMassagerStatusChange(parseInt16, str4, parseInt17, parseBoolean);
                }
                return;
            case 16:
                int parseInt18 = Integer.parseInt(str2);
                Iterator it18 = this.mCallback.iterator();
                while (it18.hasNext()) {
                    ((ISignalCallback) it18.next()).onDrvVerPosChanged(parseInt18);
                }
                return;
            case 17:
                int parseInt19 = Integer.parseInt(str2);
                Iterator it19 = this.mCallback.iterator();
                while (it19.hasNext()) {
                    ((ISignalCallback) it19.next()).onPsnTiltPosChanged(parseInt19);
                }
                return;
            case 18:
                int parseInt20 = Integer.parseInt(str2);
                Iterator it20 = this.mCallback.iterator();
                while (it20.hasNext()) {
                    ((ISignalCallback) it20.next()).onPsnHorPosChanged(parseInt20);
                }
                return;
            case 19:
                int parseInt21 = Integer.parseInt(str2);
                Iterator it21 = this.mCallback.iterator();
                while (it21.hasNext()) {
                    ((ISignalCallback) it21.next()).onRLSeatSelectIdChanged(parseInt21);
                }
                return;
            case 20:
                int parseInt22 = Integer.parseInt(str2);
                Iterator it22 = this.mCallback.iterator();
                while (it22.hasNext()) {
                    ((ISignalCallback) it22.next()).onPsnLegPosChanged(parseInt22);
                }
                return;
            case 21:
                String[] split3 = str2.split(",");
                int parseInt23 = Integer.parseInt(split3[0]);
                String str5 = split3[1];
                Iterator it23 = this.mCallback.iterator();
                while (it23.hasNext()) {
                    ((ISignalCallback) it23.next()).onRRSeatNameChanged(parseInt23, str5);
                }
                return;
            case 22:
                int parseInt24 = Integer.parseInt(str2);
                Iterator it24 = this.mCallback.iterator();
                while (it24.hasNext()) {
                    ((ISignalCallback) it24.next()).onDrvTiltPosChanged(parseInt24);
                }
                return;
            case 23:
                int parseInt25 = Integer.parseInt(str2);
                Iterator it25 = this.mCallback.iterator();
                while (it25.hasNext()) {
                    ((ISignalCallback) it25.next()).onDrvCushionExtPosChanged(parseInt25);
                }
                return;
            default:
                return;
        }
    }

    public int getDSeatHorzPos() {
        String callIpc = callIpc("getDSeatHorzPos", null);
        LogUtils.i(this.TAG, "getDSeatHorzPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setDSeatHorzPos(int i) {
        LogUtils.i(this.TAG, "setDSeatHorzPos " + i);
        callIpc("setDSeatHorzPos", Integer.valueOf(i));
    }

    public int getDSeatVerPos() {
        String callIpc = callIpc("getDSeatVerPos", null);
        LogUtils.i(this.TAG, "getDSeatVerPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setDSeatVerPos(int i) {
        LogUtils.i(this.TAG, "setDSeatVerPos " + i);
        callIpc("setDSeatVerPos", Integer.valueOf(i));
    }

    public int getDSeatTiltPos() {
        String callIpc = callIpc("getDSeatTiltPos", null);
        LogUtils.i(this.TAG, "getDSeatTiltPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setDSeatTiltPos(int i) {
        LogUtils.i(this.TAG, "setDSeatTiltPos " + i);
        callIpc("setDSeatTiltPos", Integer.valueOf(i));
    }

    public int getDSeatLegPos() {
        String callIpc = callIpc("getDSeatLegPos", null);
        LogUtils.i(this.TAG, "getDSeatLegPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setDSeatLegPos(int i) {
        LogUtils.i(this.TAG, "setDSeatLegPos " + i);
        callIpc("setDSeatLegPos", Integer.valueOf(i));
    }

    public int getDSeatCushionExtPos() {
        String callIpc = callIpc("getDSeatCushionExtPos", null);
        LogUtils.i(this.TAG, "getDSeatCushionExtPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setDSeatCushionExtPos(int i) {
        LogUtils.i(this.TAG, "setDSeatCushionExtPos " + i);
        callIpc("setDSeatCushionExtPos", Integer.valueOf(i));
    }

    public int getPSeatHorzPos() {
        String callIpc = callIpc("getPSeatHorzPos", null);
        LogUtils.i(this.TAG, "getPSeatHorzPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setPSeatHorzPos(int i) {
        LogUtils.i(this.TAG, "setPSeatHorzPos " + i);
        callIpc("setPSeatHorzPos", Integer.valueOf(i));
    }

    public int getPSeatVerPos() {
        String callIpc = callIpc("getPSeatVerPos", null);
        LogUtils.i(this.TAG, "getPSeatVerPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setPSeatVerPos(int i) {
        LogUtils.i(this.TAG, "setPSeatVerPos " + i);
        callIpc("setPSeatVerPos", Integer.valueOf(i));
    }

    public int getPSeatTiltPos() {
        String callIpc = callIpc("getPSeatTiltPos", null);
        LogUtils.i(this.TAG, "getPSeatTiltPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setPSeatTiltPos(int i) {
        LogUtils.i(this.TAG, "setPSeatTiltPos " + i);
        callIpc("setPSeatTiltPos", Integer.valueOf(i));
    }

    public int getPSeatLegPos() {
        String callIpc = callIpc("getPSeatLegPos", null);
        LogUtils.i(this.TAG, "getPSeatLegPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setPSeatLegPos(int i) {
        LogUtils.i(this.TAG, "setPSeatLegPos " + i);
        callIpc("setPSeatLegPos", Integer.valueOf(i));
    }

    public int getRLSeatTiltPos() {
        String callIpc = callIpc("getRLSeatTiltPos", null);
        LogUtils.i(this.TAG, "getRLSeatTiltPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setRLSeatTiltPos(int i) {
        LogUtils.i(this.TAG, "setRLSeatTiltPos " + i);
        callIpc("setRLSeatTiltPos", Integer.valueOf(i));
    }

    public int getRLSeatHorPos() {
        String callIpc = callIpc("getRLSeatHorPos", null);
        LogUtils.i(this.TAG, "getRLSeatHorPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setRLSeatHorPos(int i) {
        LogUtils.i(this.TAG, "setRLSeatHorPos " + i);
        callIpc("setRLSeatHorPos", Integer.valueOf(i));
    }

    public int getRLSeatLegHorPos() {
        String callIpc = callIpc("getRLSeatLegHorPos", null);
        LogUtils.i(this.TAG, "getRLSeatLegHorPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setRLSeatLegHorPos(int i) {
        LogUtils.i(this.TAG, "setRLSeatLegHorPos " + i);
        callIpc("setRLSeatLegHorPos", Integer.valueOf(i));
    }

    public int getRRSeatTiltPos() {
        String callIpc = callIpc("getRRSeatTiltPos", null);
        LogUtils.i(this.TAG, "getRRSeatTiltPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setRRSeatTiltPos(int i) {
        LogUtils.i(this.TAG, "setRRSeatTiltPos " + i);
        callIpc("setRRSeatTiltPos", Integer.valueOf(i));
    }

    public int getRRSeatHorPos() {
        String callIpc = callIpc("getRRSeatHorPos", null);
        LogUtils.i(this.TAG, "getRRSeatHorPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setRRSeatHorPos(int i) {
        LogUtils.i(this.TAG, "setRRSeatHorPos " + i);
        callIpc("setRRSeatHorPos", Integer.valueOf(i));
    }

    public int getRRSeatLegHorPos() {
        String callIpc = callIpc("getRRSeatLegHorPos", null);
        LogUtils.i(this.TAG, "getRRSeatLegHorPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setRRSeatLegHorPos(int i) {
        LogUtils.i(this.TAG, "setRRSeatLegHorPos " + i);
        callIpc("setRRSeatLegHorPos", Integer.valueOf(i));
    }

    public int getTrdRowLtSeatTiltPos() {
        String callIpc = callIpc("getTrdRowLtSeatTiltPos", null);
        LogUtils.i(this.TAG, "getTrdRowLtSeatTiltPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setTrdRowLtSeatTiltPos(int i) {
        LogUtils.i(this.TAG, "setTrdRowLtSeatTiltPos " + i);
        callIpc("setTrdRowLtSeatTiltPos", Integer.valueOf(i));
    }

    public int getTrdRowRtSeatTiltPos() {
        String callIpc = callIpc("getTrdRowRtSeatTiltPos", null);
        LogUtils.i(this.TAG, "getTrdRowRtSeatTiltPos: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setTrdRowRtSeatTiltPos(int i) {
        LogUtils.i(this.TAG, "setTrdRowRtSeatTiltPos " + i);
        callIpc("setTrdRowRtSeatTiltPos", Integer.valueOf(i));
    }

    public String getRLSeatName(int i) {
        String callIpc = callIpc("getRLSeatName", Integer.valueOf(i));
        LogUtils.i(this.TAG, "getRLSeatName: " + i);
        return callIpc != null ? callIpc : "";
    }

    public String getRRSeatName(int i) {
        String callIpc = callIpc("getRRSeatName", Integer.valueOf(i));
        LogUtils.i(this.TAG, "getRRSeatName: " + i);
        return callIpc != null ? callIpc : "";
    }

    public int getRLSeatSelectId() {
        String callIpc = callIpc("getRLSeatSelectId", null);
        LogUtils.i(this.TAG, "getRLSeatSelectId: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public int getRRSeatSelectId() {
        String callIpc = callIpc("getRRSeatSelectId", null);
        LogUtils.i(this.TAG, "getRRSeatSelectId: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void switchRLSeatId(int i) {
        LogUtils.i(this.TAG, "switchRLSeatId " + i);
        callIpc("switchRLSeatId", Integer.valueOf(i));
    }

    public void switchRRSeatId(int i) {
        LogUtils.i(this.TAG, "switchRRSeatId " + i);
        callIpc("switchRRSeatId", Integer.valueOf(i));
    }

    public int getSecRowLtSeatZeroGrav() {
        String callIpc = callIpc("getSecRowLtSeatZeroGrav", null);
        LogUtils.i(this.TAG, "getSecRowLtSeatZeroGrav: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setSecRowLtSeatZeroGrav(boolean z) {
        callIpc("setSecRowLtSeatZeroGrav", Boolean.valueOf(z));
    }

    public int getSecRowRtSeatZeroGrav() {
        String callIpc = callIpc("getSecRowRtSeatZeroGrav", null);
        LogUtils.i(this.TAG, "getSecRowRtSeatZeroGrav: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setSecRowRtSeatZeroGrav(boolean z) {
        callIpc("setSecRowRtSeatZeroGrav", Boolean.valueOf(z));
    }

    public void startMassager(int i, String str) {
        String str2 = i + "," + str;
        LogUtils.i(this.TAG, "startMassager " + str2);
        callIpc("startMassager", str2);
    }

    public void stopMassager(int i) {
        LogUtils.i(this.TAG, "stopMassager " + i);
        callIpc("stopMassager", Integer.valueOf(i));
    }

    public void setMassagerIntensity(int i, int i2) {
        String str = i + "," + i2;
        LogUtils.i(this.TAG, "setMassagerIntensity " + str);
        callIpc("setMassagerIntensity", str);
    }

    public int getMassagerIntensity(int i) {
        String callIpc = callIpc("getMassagerIntensity", Integer.valueOf(i));
        LogUtils.i(this.TAG, "getMassagerIntensity: " + callIpc);
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }
}
