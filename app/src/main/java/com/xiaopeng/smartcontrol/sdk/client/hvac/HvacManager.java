package com.xiaopeng.smartcontrol.sdk.client.hvac;

import android.text.TextUtils;
import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.smartcontrol.sdk.client.BaseManager;
import com.xiaopeng.smartcontrol.utils.Constants;
import com.xiaopeng.smartcontrol.utils.LogUtils;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class HvacManager extends BaseManager<ISignalCallback> {
    public static final int BCM_HEAT_STATUS_LEVEL_1 = 1;
    public static final int BCM_HEAT_STATUS_LEVEL_2 = 2;
    public static final int BCM_HEAT_STATUS_LEVEL_3 = 3;
    public static final int BCM_HEAT_STATUS_OFF = 0;
    public static final int HVAC_BLOW_WIND_MODE_FACE = 2;
    public static final int HVAC_BLOW_WIND_MODE_FOOT = 3;
    public static final int HVAC_BLOW_WIND_MODE_WINDSHIELD = 1;
    public static final int HVAC_CIRCULATION_STATUS_AUTO = 6;
    public static final int HVAC_CIRCULATION_STATUS_ERROR = 7;
    public static final int HVAC_CIRCULATION_STATUS_INNER = 1;
    public static final int HVAC_CIRCULATION_STATUS_OUTSIDE = 2;
    public static final float HVAC_CODE_INVALID_FLOAT = 0.0f;
    public static final int HVAC_CODE_INVALID_INT = 0;
    public static final int HVAC_EAV_POSITION_1 = 1;
    public static final int HVAC_EAV_POSITION_2 = 2;
    public static final int HVAC_EAV_POSITION_3 = 3;
    public static final int HVAC_EAV_POSITION_4 = 4;
    public static final int HVAC_EAV_POSITION_5 = 5;
    public static final int HVAC_EAV_POSITION_6 = 6;
    public static final int HVAC_EAV_POSITION_7 = 7;
    public static final int HVAC_EAV_POSITION_ERROR = 15;
    public static final int HVAC_EAV_POSITION_OFF = 14;
    public static final int HVAC_EAV_POSITION_OPEN = 13;
    public static final int HVAC_GET_BLOW_WIND_MODE_AUTOMODE = 14;
    public static final int HVAC_GET_BLOW_WIND_MODE_AUTO_FDEFROST = 7;
    public static final int HVAC_GET_BLOW_WIND_MODE_ERROR = 15;
    public static final int HVAC_GET_BLOW_WIND_MODE_FACE = 1;
    public static final int HVAC_GET_BLOW_WIND_MODE_FACE_FOOT = 2;
    public static final int HVAC_GET_BLOW_WIND_MODE_FACE_FOOT_WINDSHIELD = 9;
    public static final int HVAC_GET_BLOW_WIND_MODE_FACE_WINDSHIELD = 8;
    public static final int HVAC_GET_BLOW_WIND_MODE_FDEFROST = 5;
    public static final int HVAC_GET_BLOW_WIND_MODE_FOOT = 3;
    public static final int HVAC_GET_BLOW_WIND_MODE_FOOT_WINDSHIELD = 4;
    public static final int HVAC_GET_BLOW_WIND_MODE_WINDSHIELD = 6;
    public static final int HVAC_WINDCOLOR_COLD = 2;
    public static final int HVAC_WINDCOLOR_ERROR = 7;
    public static final int HVAC_WINDCOLOR_HOT = 3;
    public static final int HVAC_WINDCOLOR_NATURE = 1;
    public static final int HVAC_WIND_SPEED_1 = 1;
    public static final int HVAC_WIND_SPEED_10 = 10;
    public static final int HVAC_WIND_SPEED_2 = 2;
    public static final int HVAC_WIND_SPEED_3 = 3;
    public static final int HVAC_WIND_SPEED_4 = 4;
    public static final int HVAC_WIND_SPEED_5 = 5;
    public static final int HVAC_WIND_SPEED_6 = 6;
    public static final int HVAC_WIND_SPEED_7 = 7;
    public static final int HVAC_WIND_SPEED_8 = 8;
    public static final int HVAC_WIND_SPEED_9 = 9;
    public static final int HVAC_WIND_SPEED_AUTO = 14;
    public static final int HVAC_WIND_SPEED_ERROR = 15;
    public static final int HVAC_WIND_SPEED_OFF = 0;
    float HVAC_TEMP_MAX = 32.0f;
    float HVAC_TEMP_MIN = 18.0f;

    /* loaded from: classes2.dex */
    public interface ISignalCallback extends BaseManager.IBaseSignalReceiver {
        default void onHvacAcChanged(boolean z) {
        }

        default void onHvacAirPurgeModeChanged(boolean z) {
        }

        default void onHvacAutoChanged(boolean z) {
        }

        default void onHvacAutoDefogWorkStChanged(boolean z) {
        }

        default void onHvacBackMirrorHeatChanged(boolean z) {
        }

        default void onHvacCirculationModeChanged(int i) {
        }

        default void onHvacDeodorantChanged(boolean z) {
        }

        default void onHvacDrvSeatBlowLevelChanged(int i) {
        }

        default void onHvacDrvSeatHeatLevelChanged(int i) {
        }

        default void onHvacDrvSyncChanged(boolean z) {
        }

        default void onHvacDrvTempChanged(float f) {
        }

        default void onHvacEAVDriverLeftHPos(int i) {
        }

        default void onHvacEAVDriverLeftVPos(int i) {
        }

        default void onHvacEAVDriverRightHPos(int i) {
        }

        default void onHvacEAVDriverRightVPos(int i) {
        }

        default void onHvacEAVPsnLeftHPos(int i) {
        }

        default void onHvacEAVPsnLeftVPos(int i) {
        }

        default void onHvacEAVPsnRightHPos(int i) {
        }

        default void onHvacEAVPsnRightVPos(int i) {
        }

        default void onHvacFrontDefrostChanged(boolean z) {
        }

        default void onHvacFrontMirrorHeatChanged(boolean z) {
        }

        default void onHvacInnerPM25Changed(float f) {
        }

        default void onHvacInnerTempChanged(float f) {
        }

        default void onHvacNIVentChanged(boolean z) {
        }

        default void onHvacOutTempChanged(float f) {
        }

        default void onHvacPowerChanged(boolean z) {
        }

        default void onHvacPsnSeatBlowLevelChanged(int i) {
        }

        default void onHvacPsnSeatHeatLevelChanged(int i) {
        }

        default void onHvacPsnTempChanged(float f) {
        }

        default void onHvacRLSeatBlowLevelChanged(int i) {
        }

        default void onHvacRLSeatHeatLevelChanged(int i) {
        }

        default void onHvacRLTempChanged(float f) {
        }

        default void onHvacRRSeatBlowLevelChanged(int i) {
        }

        default void onHvacRRSeatHeatLevelChanged(int i) {
        }

        default void onHvacRRTempChanged(float f) {
        }

        default void onHvacRapidCoolingChanged(boolean z) {
        }

        default void onHvacRapidHeatChanged(boolean z) {
        }

        default void onHvacRearAutoChanged(boolean z) {
        }

        default void onHvacRearPowerChanged(boolean z) {
        }

        default void onHvacRearWindBlowModeChanged(int i) {
        }

        default void onHvacRearWindLevelChanged(int i) {
        }

        default void onHvacSteerHeatLevelChanged(int i) {
        }

        default void onHvacThirdTempChanged(float f) {
        }

        default void onHvacThirdWindBlowModeChanged(int i) {
        }

        default void onHvacTopVentChanged(boolean z) {
        }

        default void onHvacWindBlowModeChanged(int i) {
        }

        default void onHvacWindLevelChanged(int i) {
        }

        default void onHvacWindModeColorChanged(int i) {
        }
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getAppId() {
        return "hvac";
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final HvacManager INSTANCE = new HvacManager();

        private SingletonHolder() {
        }
    }

    public static HvacManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    protected String getModuleName() {
        return Constants.CAR_CONTROL_MODULE.HVAC_IMPL;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.client.BaseManager
    public void onIpcReceived(String str, String str2) {
        LogUtils.i(this.TAG, str + " " + str2);
        Iterator<BaseManager.IRawSignalReceiver> it = this.mRawCallback.iterator();
        while (it.hasNext()) {
            it.next().onIpcReceived(str, str2);
        }
        String[] split = TextUtils.isEmpty(str2) ? new String[0] : str2.split(",");
        Iterator it2 = this.mCallback.iterator();
        while (it2.hasNext()) {
            ISignalCallback iSignalCallback = (ISignalCallback) it2.next();
            str.hashCode();
            char c = 65535;
            switch (str.hashCode()) {
                case -2143879349:
                    if (str.equals("onHvacRearWindLevelChanged")) {
                        c = 0;
                        break;
                    }
                    break;
                case -1850413596:
                    if (str.equals("onHvacRearWindBlowModeChanged")) {
                        c = 1;
                        break;
                    }
                    break;
                case -1796918845:
                    if (str.equals("onHvacPsnSeatBlowLevelChanged")) {
                        c = 2;
                        break;
                    }
                    break;
                case -1696929529:
                    if (str.equals("onHvacWindLevelChanged")) {
                        c = 3;
                        break;
                    }
                    break;
                case -1645682809:
                    if (str.equals("onHvacDrvTempChanged")) {
                        c = 4;
                        break;
                    }
                    break;
                case -1483397794:
                    if (str.equals("onHvacDrvSeatHeatLevelChanged")) {
                        c = 5;
                        break;
                    }
                    break;
                case -1312745470:
                    if (str.equals("onHvacRearPowerChanged")) {
                        c = 6;
                        break;
                    }
                    break;
                case -1166943576:
                    if (str.equals("onHvacWindBlowModeChanged")) {
                        c = 7;
                        break;
                    }
                    break;
                case -1088499241:
                    if (str.equals("onHvacRLTempChanged")) {
                        c = '\b';
                        break;
                    }
                    break;
                case -935032427:
                    if (str.equals("onHvacEAVDriverRightHPos")) {
                        c = '\t';
                        break;
                    }
                    break;
                case -934615353:
                    if (str.equals("onHvacEAVDriverRightVPos")) {
                        c = '\n';
                        break;
                    }
                    break;
                case -718678997:
                    if (str.equals("onHvacFrontDefrostChanged")) {
                        c = 11;
                        break;
                    }
                    break;
                case -708559966:
                    if (str.equals("onHvacSteerHeatLevelChanged")) {
                        c = '\f';
                        break;
                    }
                    break;
                case -706556658:
                    if (str.equals("onHvacRLSeatHeatLevelChanged")) {
                        c = '\r';
                        break;
                    }
                    break;
                case -567761322:
                    if (str.equals("onHvacAutoChanged")) {
                        c = 14;
                        break;
                    }
                    break;
                case -463296863:
                    if (str.equals("onHvacAirPurgeModeChanged")) {
                        c = 15;
                        break;
                    }
                    break;
                case -448516476:
                    if (str.equals("onHvacPsnTempChanged")) {
                        c = 16;
                        break;
                    }
                    break;
                case -410852403:
                    if (str.equals("onHvacInnerPM25Changed")) {
                        c = 17;
                        break;
                    }
                    break;
                case -408054861:
                    if (str.equals("onHvacEAVPsnLeftHPos")) {
                        c = 18;
                        break;
                    }
                    break;
                case -407637787:
                    if (str.equals("onHvacEAVPsnLeftVPos")) {
                        c = 19;
                        break;
                    }
                    break;
                case -403521341:
                    if (str.equals("onHvacAcChanged")) {
                        c = 20;
                        break;
                    }
                    break;
                case -314002927:
                    if (str.equals("onHvacRRTempChanged")) {
                        c = 21;
                        break;
                    }
                    break;
                case -120327136:
                    if (str.equals("onHvacDrvSeatBlowLevelChanged")) {
                        c = 22;
                        break;
                    }
                    break;
                case -108519805:
                    if (str.equals("onHvacAutoDefogWorkStChanged")) {
                        c = 23;
                        break;
                    }
                    break;
                case 65761727:
                    if (str.equals("onHvacThirdWindBlowModeChanged")) {
                        c = 24;
                        break;
                    }
                    break;
                case 137709544:
                    if (str.equals("onHvacRapidCoolingChanged")) {
                        c = 25;
                        break;
                    }
                    break;
                case 244732449:
                    if (str.equals("onHvacOutTempChanged")) {
                        c = JSONLexer.EOI;
                        break;
                    }
                    break;
                case 475962024:
                    if (str.equals("onHvacThirdTempChanged")) {
                        c = 27;
                        break;
                    }
                    break;
                case 483208783:
                    if (str.equals("onHvacBackMirrorHeatChanged")) {
                        c = 28;
                        break;
                    }
                    break;
                case 530910649:
                    if (str.equals("onHvacTopVentChanged")) {
                        c = 29;
                        break;
                    }
                    break;
                case 561548820:
                    if (str.equals("onHvacRRSeatHeatLevelChanged")) {
                        c = 30;
                        break;
                    }
                    break;
                case 656514000:
                    if (str.equals("onHvacRLSeatBlowLevelChanged")) {
                        c = 31;
                        break;
                    }
                    break;
                case 719369618:
                    if (str.equals("onHvacRearAutoChanged")) {
                        c = ' ';
                        break;
                    }
                    break;
                case 983633227:
                    if (str.equals("onHvacFrontMirrorHeatChanged")) {
                        c = '!';
                        break;
                    }
                    break;
                case 1045771078:
                    if (str.equals("onHvacEAVDriverLeftHPos")) {
                        c = '\"';
                        break;
                    }
                    break;
                case 1046188152:
                    if (str.equals("onHvacEAVDriverLeftVPos")) {
                        c = '#';
                        break;
                    }
                    break;
                case 1050883673:
                    if (str.equals("onHvacInnerTempChanged")) {
                        c = '$';
                        break;
                    }
                    break;
                case 1134977793:
                    if (str.equals("onHvacPsnSeatHeatLevelChanged")) {
                        c = '%';
                        break;
                    }
                    break;
                case 1241003720:
                    if (str.equals("onHvacEAVPsnRightHPos")) {
                        c = '&';
                        break;
                    }
                    break;
                case 1241420794:
                    if (str.equals("onHvacEAVPsnRightVPos")) {
                        c = '\'';
                        break;
                    }
                    break;
                case 1267748789:
                    if (str.equals("onHvacNIVentChanged")) {
                        c = '(';
                        break;
                    }
                    break;
                case 1357717081:
                    if (str.equals("onHvacCirculationModeChanged")) {
                        c = ')';
                        break;
                    }
                    break;
                case 1607026773:
                    if (str.equals("onHvacDeodorantChanged")) {
                        c = '*';
                        break;
                    }
                    break;
                case 1615289943:
                    if (str.equals("onHvacRapidHeatChanged")) {
                        c = '+';
                        break;
                    }
                    break;
                case 1735868350:
                    if (str.equals("onHvacPowerChanged")) {
                        c = ',';
                        break;
                    }
                    break;
                case 1821780704:
                    if (str.equals("onHvacDrvSyncChanged")) {
                        c = '-';
                        break;
                    }
                    break;
                case 1899506187:
                    if (str.equals("onHvacWindModeColorChanged")) {
                        c = '.';
                        break;
                    }
                    break;
                case 1924619478:
                    if (str.equals("onHvacRRSeatBlowLevelChanged")) {
                        c = '/';
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    iSignalCallback.onHvacRearWindLevelChanged(Integer.parseInt(split[0]));
                    break;
                case 1:
                    iSignalCallback.onHvacRearWindBlowModeChanged(Integer.parseInt(split[0]));
                    break;
                case 2:
                    iSignalCallback.onHvacPsnSeatBlowLevelChanged(Integer.parseInt(split[0]));
                    break;
                case 3:
                    iSignalCallback.onHvacWindLevelChanged(Integer.parseInt(split[0]));
                    break;
                case 4:
                    iSignalCallback.onHvacDrvTempChanged(Float.parseFloat(split[0]));
                    break;
                case 5:
                    iSignalCallback.onHvacDrvSeatHeatLevelChanged(Integer.parseInt(split[0]));
                    break;
                case 6:
                    iSignalCallback.onHvacRearPowerChanged(Boolean.parseBoolean(split[0]));
                    break;
                case 7:
                    iSignalCallback.onHvacWindBlowModeChanged(Integer.parseInt(split[0]));
                    break;
                case '\b':
                    iSignalCallback.onHvacRLTempChanged(Float.parseFloat(split[0]));
                    break;
                case '\t':
                    iSignalCallback.onHvacEAVDriverRightHPos(Integer.parseInt(split[0]));
                    break;
                case '\n':
                    iSignalCallback.onHvacEAVDriverRightVPos(Integer.parseInt(split[0]));
                    break;
                case 11:
                    iSignalCallback.onHvacFrontDefrostChanged(Boolean.parseBoolean(split[0]));
                    break;
                case '\f':
                    iSignalCallback.onHvacSteerHeatLevelChanged(Integer.parseInt(split[0]));
                    break;
                case '\r':
                    iSignalCallback.onHvacRLSeatHeatLevelChanged(Integer.parseInt(split[0]));
                    break;
                case 14:
                    iSignalCallback.onHvacAutoChanged(Boolean.parseBoolean(split[0]));
                    break;
                case 15:
                    iSignalCallback.onHvacAirPurgeModeChanged(Boolean.parseBoolean(split[0]));
                    break;
                case 16:
                    iSignalCallback.onHvacPsnTempChanged(Float.parseFloat(split[0]));
                    break;
                case 17:
                    iSignalCallback.onHvacInnerPM25Changed(Float.parseFloat(split[0]));
                    break;
                case 18:
                    iSignalCallback.onHvacEAVPsnLeftHPos(Integer.parseInt(split[0]));
                    break;
                case 19:
                    iSignalCallback.onHvacEAVPsnLeftVPos(Integer.parseInt(split[0]));
                    break;
                case 20:
                    iSignalCallback.onHvacAcChanged(Boolean.parseBoolean(split[0]));
                    break;
                case 21:
                    iSignalCallback.onHvacRRTempChanged(Float.parseFloat(split[0]));
                    break;
                case 22:
                    iSignalCallback.onHvacDrvSeatBlowLevelChanged(Integer.parseInt(split[0]));
                    break;
                case 23:
                    iSignalCallback.onHvacAutoDefogWorkStChanged(Boolean.parseBoolean(split[0]));
                    break;
                case 24:
                    iSignalCallback.onHvacThirdWindBlowModeChanged(Integer.parseInt(split[0]));
                    break;
                case 25:
                    iSignalCallback.onHvacRapidCoolingChanged(Boolean.parseBoolean(split[0]));
                    break;
                case 26:
                    iSignalCallback.onHvacOutTempChanged(Float.parseFloat(split[0]));
                    break;
                case 27:
                    iSignalCallback.onHvacThirdTempChanged(Float.parseFloat(split[0]));
                    break;
                case 28:
                    iSignalCallback.onHvacBackMirrorHeatChanged(Boolean.parseBoolean(split[0]));
                    break;
                case 29:
                    iSignalCallback.onHvacTopVentChanged(Boolean.parseBoolean(split[0]));
                    break;
                case 30:
                    iSignalCallback.onHvacRRSeatHeatLevelChanged(Integer.parseInt(split[0]));
                    break;
                case 31:
                    iSignalCallback.onHvacRLSeatBlowLevelChanged(Integer.parseInt(split[0]));
                    break;
                case ' ':
                    iSignalCallback.onHvacRearAutoChanged(Boolean.parseBoolean(split[0]));
                    break;
                case '!':
                    iSignalCallback.onHvacFrontMirrorHeatChanged(Boolean.parseBoolean(split[0]));
                    break;
                case '\"':
                    iSignalCallback.onHvacEAVDriverLeftHPos(Integer.parseInt(split[0]));
                    break;
                case '#':
                    iSignalCallback.onHvacEAVDriverLeftVPos(Integer.parseInt(split[0]));
                    break;
                case '$':
                    iSignalCallback.onHvacInnerTempChanged(Float.parseFloat(split[0]));
                    break;
                case '%':
                    iSignalCallback.onHvacPsnSeatHeatLevelChanged(Integer.parseInt(split[0]));
                    break;
                case '&':
                    iSignalCallback.onHvacEAVPsnRightHPos(Integer.parseInt(split[0]));
                    break;
                case '\'':
                    iSignalCallback.onHvacEAVPsnRightVPos(Integer.parseInt(split[0]));
                    break;
                case '(':
                    iSignalCallback.onHvacNIVentChanged(Boolean.parseBoolean(split[0]));
                    break;
                case ')':
                    iSignalCallback.onHvacCirculationModeChanged(Integer.parseInt(split[0]));
                    break;
                case '*':
                    iSignalCallback.onHvacDeodorantChanged(Boolean.parseBoolean(split[0]));
                    break;
                case '+':
                    iSignalCallback.onHvacRapidHeatChanged(Boolean.parseBoolean(split[0]));
                    break;
                case ',':
                    iSignalCallback.onHvacPowerChanged(Boolean.parseBoolean(split[0]));
                    break;
                case '-':
                    iSignalCallback.onHvacDrvSyncChanged(Boolean.parseBoolean(split[0]));
                    break;
                case '.':
                    iSignalCallback.onHvacWindModeColorChanged(Integer.parseInt(split[0]));
                    break;
                case '/':
                    iSignalCallback.onHvacRRSeatBlowLevelChanged(Integer.parseInt(split[0]));
                    break;
            }
        }
    }

    public void setHvacPower(boolean z) {
        callIpc("setHvacPower", String.valueOf(z));
    }

    public boolean isHvacPowerOn() {
        String callIpc = callIpc("isHvacPowerOn", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacRearPowerMode(boolean z) {
        callIpc("setHvacRearPowerMode", String.valueOf(z));
    }

    public boolean isHvacRearPowerModeOn() {
        String callIpc = callIpc("isHvacRearPowerModeOn", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacAutoMode(boolean z) {
        callIpc("setHvacAutoMode", String.valueOf(z));
    }

    public boolean isHvacAutoMode() {
        String callIpc = callIpc("isHvacAutoMode", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacRearAutoMode(boolean z) {
        callIpc("setHvacRearAutoMode", String.valueOf(z));
    }

    public boolean isHvacRearAutoModeOn() {
        String callIpc = callIpc("isHvacRearAutoModeOn", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacAcMode(boolean z) {
        callIpc("setHvacAcMode", String.valueOf(z));
    }

    public boolean isHvacAcModeOn() {
        String callIpc = callIpc("isHvacAcModeOn", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacCirculationMode(int i) {
        callIpc("setHvacCirculationMode", String.valueOf(i));
    }

    public int getHvacCirculationMode() {
        String callIpc = callIpc("getHvacCirculationMode", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacWindSpeedLevel(int i) {
        callIpc("setHvacWindSpeedLevel", String.valueOf(i));
    }

    public int getHvacWindSpeedLevel() {
        String callIpc = callIpc("getHvacWindSpeedLevel", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacWindSpeedMax() {
        callIpc("setHvacWindSpeedMax", "");
    }

    public void setHvacWindSpeedMin() {
        callIpc("setHvacWindSpeedMin", "");
    }

    public void setHvacWindSpeedUp() {
        callIpc("setHvacWindSpeedUp", "");
    }

    public void setHvacWindSpeedDown() {
        callIpc("setHvacWindSpeedDown", "");
    }

    public void setHvacRearWindSpeedLevel(int i) {
        callIpc("setHvacRearWindSpeedLevel", String.valueOf(i));
    }

    public void setHvacRearWindSpeedStep(boolean z) {
        callIpc("setHvacRearWindSpeedStep", String.valueOf(z));
    }

    public int getHvacRearWindSpeedLevel() {
        String callIpc = callIpc("getHvacRearWindSpeedLevel", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacWindBlowMode(int i) {
        callIpc("setHvacWindBlowMode", String.valueOf(i));
    }

    public int getHvacWindBlowMode() {
        String callIpc = callIpc("getHvacWindBlowMode", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacRearWindBlowMode(int i) {
        callIpc("setHvacRearWindBlowMode", String.valueOf(i));
    }

    public int getHvacRearWindBlowMode() {
        String callIpc = callIpc("getHvacRearWindBlowMode", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacThirdRowWindBlowMode(int i) {
        callIpc("setHvacThirdRowWindBlowMode", String.valueOf(i));
    }

    public int getHvacThirdRowWindBlowMode() {
        String callIpc = callIpc("getHvacThirdRowWindBlowMode", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacNIVent(boolean z) {
        callIpc("setHvacNIVent", String.valueOf(z));
    }

    public boolean isHvacNIVentOn() {
        String callIpc = callIpc("isHvacNIVentOn", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacTopVentOn() {
        callIpc("setHvacTopVentOn", "");
    }

    public boolean isHvacTopVentOn() {
        String callIpc = callIpc("isHvacTopVentOn", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacFrontDefrostEnable(boolean z) {
        callIpc("setHvacFrontDefrostEnable", String.valueOf(z));
    }

    public boolean isHvacFrontDefrostEnable() {
        String callIpc = callIpc("isHvacFrontDefrostEnable", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacDeodorantEnable(boolean z) {
        callIpc("setHvacDeodorantEnable", String.valueOf(z));
    }

    public boolean isHvacDeodorantEnable() {
        String callIpc = callIpc("isHvacDeodorantEnable", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacRapidCoolingEnable(boolean z) {
        callIpc("setHvacRapidCoolingEnable", String.valueOf(z));
    }

    public boolean isHvacRapidCoolingEnable() {
        String callIpc = callIpc("isHvacRapidCoolingEnable", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacRapidHeatEnable(boolean z) {
        callIpc("setHvacRapidHeatEnable", String.valueOf(z));
    }

    public boolean isHvacRapidHeatEnable() {
        String callIpc = callIpc("isHvacRapidHeatEnable", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public int getHvacWindModeColor() {
        String callIpc = callIpc("getHvacWindModeColor", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean getHvacAirPurgeMode() {
        String callIpc = callIpc("getHvacAirPurgeMode", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacTempDriver(float f) {
        callIpc("setHvacTempDriver", String.valueOf(f));
    }

    public float getHvacDriverTemp() {
        String callIpc = callIpc("getHvacDriverTemp", "");
        if (callIpc != null) {
            return Float.parseFloat(callIpc);
        }
        return 0.0f;
    }

    public void setHvacDrvTempStep(boolean z) {
        callIpc("setHvacDrvTempStep", String.valueOf(z));
    }

    public void setHvacDriverSyncMode(boolean z) {
        callIpc("setHvacDriverSyncMode", String.valueOf(z));
    }

    public boolean isHvacDriverSyncMode() {
        String callIpc = callIpc("isHvacDriverSyncMode", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacPsnTemp(float f) {
        callIpc("setHvacPsnTemp", String.valueOf(f));
    }

    public float getHvacPsnTemp() {
        String callIpc = callIpc("getHvacPsnTemp", "");
        if (callIpc != null) {
            return Float.parseFloat(callIpc);
        }
        return 0.0f;
    }

    public void setHvacPsnTempStep(boolean z) {
        callIpc("setHvacPsnTempStep", String.valueOf(z));
    }

    public void setHvacRLTemp(float f) {
        callIpc("setHvacRLTemp", String.valueOf(f));
    }

    public void setHvacRLTempStep(boolean z) {
        callIpc("setHvacRLTempStep", String.valueOf(z));
    }

    public float getHvacRLTemp() {
        String callIpc = callIpc("getHvacRLTemp", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0.0f;
    }

    public void setHvacRRTemp(float f) {
        callIpc("setHvacRRTemp", String.valueOf(f));
    }

    public void setHvacRRTempStep(boolean z) {
        callIpc("setHvacRRTempStep", String.valueOf(z));
    }

    public float getHvacRRTemp() {
        String callIpc = callIpc("getHvacRRTemp", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0.0f;
    }

    public void setHvacThirdTemp(float f) {
        callIpc("setHvacThirdTemp", String.valueOf(f));
    }

    public void setHvacThirdTempStep(boolean z) {
        callIpc("setHvacThirdTempStep", String.valueOf(z));
    }

    public float getHvacThirdTemp() {
        String callIpc = callIpc("getHvacThirdTemp", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0.0f;
    }

    public float getHvacExternalTemp() {
        String callIpc = callIpc("getHvacExternalTemp", "");
        if (callIpc != null) {
            return Float.parseFloat(callIpc);
        }
        return 0.0f;
    }

    public float getHvacInnerTemp() {
        String callIpc = callIpc("getHvacInnerTemp", "");
        if (callIpc != null) {
            return Float.parseFloat(callIpc);
        }
        return 0.0f;
    }

    public int getHvacInnerPM25() {
        String callIpc = callIpc("getHvacInnerPM25", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setBackMirrorHeatEnable(boolean z) {
        callIpc("setBackMirrorHeatEnable", String.valueOf(z));
    }

    public boolean isBackMirrorHeatEnabled() {
        String callIpc = callIpc("isBackMirrorHeatEnabled", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setFrontMirrorHeatEnable(boolean z) {
        callIpc("setFrontMirrorHeatEnable", String.valueOf(z));
    }

    public boolean isFrontMirrorHeatEnabled() {
        String callIpc = callIpc("isFrontMirrorHeatEnabled", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setSteerHeatLevel(int i) {
        callIpc("setSteerHeatLevel", String.valueOf(i));
    }

    public int getSteerHeatLevel() {
        String callIpc = callIpc("getSteerHeatLevel", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean isSupportHvacSteerHeat() {
        String callIpc = callIpc("isSupportHvacSteerHeat", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacDrvSeatHeatLevel(int i) {
        callIpc("setHvacDrvSeatHeatLevel", String.valueOf(i));
    }

    public int getHvacDrvSeatHeatLevel() {
        String callIpc = callIpc("getHvacDrvSeatHeatLevel", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean isSupportHvacDrvSeatHeat() {
        String callIpc = callIpc("isSupportHvacDrvSeatHeat", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacDrvSeatVentLevel(int i) {
        callIpc("setHvacDrvSeatVentLevel", String.valueOf(i));
    }

    public int getHvacDrvSeatVentLevel() {
        String callIpc = callIpc("getHvacDrvSeatVentLevel", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean isSupportHvacDrvSeatVent() {
        String callIpc = callIpc("isSupportHvacDrvSeatVent", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacPsnSeatHeatLevel(int i) {
        callIpc("setHvacPsnSeatHeatLevel", String.valueOf(i));
    }

    public int getHvacPsnSeatHeatLevel() {
        String callIpc = callIpc("getHvacPsnSeatHeatLevel", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean isSupportHvacPsnSeatHeat() {
        String callIpc = callIpc("isSupportHvacPsnSeatHeat", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacPsnSeatVentLevel(int i) {
        callIpc("setHvacPsnSeatVentLevel", String.valueOf(i));
    }

    public int getHvacPsnSeatVentLevel() {
        String callIpc = callIpc("getHvacPsnSeatVentLevel", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean isSupportHvacPsnSeatVent() {
        String callIpc = callIpc("isSupportHvacPsnSeatVent", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public boolean isSupportHvacRearSeatHeat() {
        String callIpc = callIpc("isSupportHvacRearSeatHeat", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacRLSeatHeatLevel(int i) {
        callIpc("setHvacRLSeatHeatLevel", String.valueOf(i));
    }

    public int getHvacRLSeatHeatLevel() {
        String callIpc = callIpc("getHvacRLSeatHeatLevel", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacRRSeatHeatLevel(int i) {
        callIpc("setHvacRRSeatHeatLevel", String.valueOf(i));
    }

    public int getHvacRRSeatHeatLevel() {
        String callIpc = callIpc("getHvacRRSeatHeatLevel", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public boolean isSupportHvacRearSeatVent() {
        String callIpc = callIpc("isSupportHvacRearSeatVent", "");
        return callIpc != null && Boolean.parseBoolean(callIpc);
    }

    public void setHvacRLSeatVentLevel(int i) {
        callIpc("setHvacRLSeatVentLevel", String.valueOf(i));
    }

    public int getHvacRLSeatVentLevel() {
        String callIpc = callIpc("getHvacRLSeatVentLevel", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacRRSeatVentLevel(int i) {
        callIpc("setHvacRRSeatVentLevel", String.valueOf(i));
    }

    public int getHvacRRSeatVentLevel() {
        String callIpc = callIpc("getHvacRRSeatVentLevel", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacEAVDriverLeftHPosDirect(int i) {
        callIpc("setHvacEAVDriverLeftHPosDirect", String.valueOf(i));
    }

    public int getHvacEAVDriverLeftHPos() {
        String callIpc = callIpc("getHvacEAVDriverLeftHPos", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacEAVDriverLeftVPosDirect(int i) {
        callIpc("setHvacEAVDriverLeftVPosDirect", String.valueOf(i));
    }

    public int getHvacEAVDriverLeftVPos() {
        String callIpc = callIpc("getHvacEAVDriverLeftVPos", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacEAVDriverRightHPosDirect(int i) {
        callIpc("setHvacEAVDriverRightHPosDirect", String.valueOf(i));
    }

    public int getHvacEAVDriverRightHPos() {
        String callIpc = callIpc("getHvacEAVDriverRightHPos", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacEAVDriverRightVPosDirect(int i) {
        callIpc("setHvacEAVDriverRightVPosDirect", String.valueOf(i));
    }

    public int getHvacEAVDriverRightVPos() {
        String callIpc = callIpc("getHvacEAVDriverRightVPos", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacEAVPsnLeftHPosDirect(int i) {
        callIpc("setHvacEAVPsnLeftHPosDirect", String.valueOf(i));
    }

    public int getHvacEAVPsnLeftHPos() {
        String callIpc = callIpc("getHvacEAVPsnLeftHPos", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacEAVPsnLeftVPosDirect(int i) {
        callIpc("setHvacEAVPsnLeftVPosDirect", String.valueOf(i));
    }

    public int getHvacEAVPsnLeftVPos() {
        String callIpc = callIpc("getHvacEAVPsnLeftVPos", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacEAVPsnRightHPosDirect(int i) {
        callIpc("setHvacEAVPsnRightHPosDirect", String.valueOf(i));
    }

    public int getHvacEAVPsnRightHPos() {
        String callIpc = callIpc("getHvacEAVPsnRightHPos", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public void setHvacEAVPsnRightVPosDirect(int i) {
        callIpc("setHvacEAVPsnRightVPosDirect", String.valueOf(i));
    }

    public int getHvacEAVPsnRightVPos() {
        String callIpc = callIpc("getHvacEAVPsnRightVPos", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public int getHvacAcpConsumption() {
        String callIpc = callIpc("getHvacAcpConsumption", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }

    public int getHvacPtcConsumption() {
        String callIpc = callIpc("getHvacPtcConsumption", "");
        if (callIpc != null) {
            return Integer.parseInt(callIpc);
        }
        return 0;
    }
}
