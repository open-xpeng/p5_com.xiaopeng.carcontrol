package com.xiaopeng.smartcontrol.sdk.server.vehicle;

import android.text.TextUtils;
import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public class CarBodyManagerServer extends BaseManagerServer<Implementation> {
    private Callback mCallback = new Callback();

    /* loaded from: classes2.dex */
    public interface Implementation extends BaseManagerServer.Implementation {
        void controlLeftSlideDoor(int i);

        void controlRightSlideDoor(int i);

        int getChargePort(boolean z);

        boolean getCwcChargeErrorSt();

        int getCwcChargeSt();

        boolean getCwcFRChargeErrorSt();

        int getCwcFRChargeSt();

        boolean getCwcFRSwEnable();

        boolean getCwcRLChargeErrorSt();

        int getCwcRLChargeSt();

        boolean getCwcRLSwEnable();

        boolean getCwcRRChargeErrorSt();

        int getCwcRRChargeSt();

        boolean getCwcRRSwEnable();

        boolean getCwcSwEnable();

        int getLeftSlideDoorMode();

        int getLeftSlideDoorState();

        int getRightSlideDoorMode();

        int getRightSlideDoorState();

        int getTrunkState();

        int getWiperSensitivity();

        boolean isDoorClosed(int i);

        void setChargePort(boolean z, boolean z2);

        void setCwcSwEnable(boolean z);

        void setCwcSwFREnable(boolean z);

        void setCwcSwRLEnable(boolean z);

        void setCwcSwRREnable(boolean z);

        void setLeftSlideDoorMode(int i);

        void setRightSlideDoorMode(int i);

        void setTrunk(int i);

        void setWiperSensitivity(int i);
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final CarBodyManagerServer INSTANCE = new CarBodyManagerServer();

        private SingletonHolder() {
        }
    }

    public static CarBodyManagerServer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Callback getCallback() {
        return this.mCallback;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer
    public String onIpcCall(String str, String str2) {
        Object obj = null;
        if (this.mImpl == 0) {
            LogUtils.e(this.TAG, "mImpl is null");
            return null;
        }
        String[] split = TextUtils.isEmpty(str2) ? new String[0] : str2.split(",");
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2007017260:
                if (str.equals("setCwcSwRREnable")) {
                    c = 0;
                    break;
                }
                break;
            case -1774797005:
                if (str.equals("getTrunkState")) {
                    c = 1;
                    break;
                }
                break;
            case -1692707237:
                if (str.equals("controlLeftSlideDoor")) {
                    c = 2;
                    break;
                }
                break;
            case -1602014368:
                if (str.equals("getCwcRRSwEnable")) {
                    c = 3;
                    break;
                }
                break;
            case -1445904800:
                if (str.equals("setCwcSwFREnable")) {
                    c = 4;
                    break;
                }
                break;
            case -1438087468:
                if (str.equals("setCwcSwEnable")) {
                    c = 5;
                    break;
                }
                break;
            case -1403674994:
                if (str.equals("getCwcChargeSt")) {
                    c = 6;
                    break;
                }
                break;
            case -1084545912:
                if (str.equals("getCwcRLChargeSt")) {
                    c = 7;
                    break;
                }
                break;
            case -1059042680:
                if (str.equals("setRightSlideDoorMode")) {
                    c = '\b';
                    break;
                }
                break;
            case -981176038:
                if (str.equals("getCwcFRChargeSt")) {
                    c = '\t';
                    break;
                }
                break;
            case -561715946:
                if (str.equals("setWiperSensitivity")) {
                    c = '\n';
                    break;
                }
                break;
            case 221974744:
                if (str.equals("getRightSlideDoorState")) {
                    c = 11;
                    break;
                }
                break;
            case 333648736:
                if (str.equals("getCwcSwEnable")) {
                    c = '\f';
                    break;
                }
                break;
            case 378021058:
                if (str.equals("getCwcRLChargeErrorSt")) {
                    c = '\r';
                    break;
                }
                break;
            case 465976495:
                if (str.equals("getLeftSlideDoorState")) {
                    c = 14;
                    break;
                }
                break;
            case 494640384:
                if (str.equals("controlRightSlideDoor")) {
                    c = 15;
                    break;
                }
                break;
            case 652777818:
                if (str.equals("getCwcRLSwEnable")) {
                    c = 16;
                    break;
                }
                break;
            case 699713660:
                if (str.equals("getRightSlideDoorMode")) {
                    c = 17;
                    break;
                }
                break;
            case 728884080:
                if (str.equals("getCwcFRChargeErrorSt")) {
                    c = 18;
                    break;
                }
                break;
            case 756147692:
                if (str.equals("getCwcFRSwEnable")) {
                    c = 19;
                    break;
                }
                break;
            case 927945273:
                if (str.equals("setLeftSlideDoorMode")) {
                    c = 20;
                    break;
                }
                break;
            case 955629198:
                if (str.equals("getCwcRRChargeSt")) {
                    c = 21;
                    break;
                }
                break;
            case 982011914:
                if (str.equals("getWiperSensitivity")) {
                    c = 22;
                    break;
                }
                break;
            case 1120234827:
                if (str.equals("getChargePort")) {
                    c = 23;
                    break;
                }
                break;
            case 1231745924:
                if (str.equals("isDoorClosed")) {
                    c = 24;
                    break;
                }
                break;
            case 1257895246:
                if (str.equals("setCwcSwRLEnable")) {
                    c = 25;
                    break;
                }
                break;
            case 1405353586:
                if (str.equals("setTrunk")) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case 1538868677:
                if (str.equals("getLeftSlideDoorMode")) {
                    c = 27;
                    break;
                }
                break;
            case 1639636604:
                if (str.equals("getCwcRRChargeErrorSt")) {
                    c = 28;
                    break;
                }
                break;
            case 1866923644:
                if (str.equals("getCwcChargeErrorSt")) {
                    c = 29;
                    break;
                }
                break;
            case 1894366039:
                if (str.equals("setChargePort")) {
                    c = 30;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                ((Implementation) this.mImpl).setCwcSwRREnable(Boolean.parseBoolean(str2));
                break;
            case 1:
                obj = Integer.valueOf(((Implementation) this.mImpl).getTrunkState());
                break;
            case 2:
                ((Implementation) this.mImpl).controlLeftSlideDoor(Integer.parseInt(split[0]));
                break;
            case 3:
                obj = Boolean.valueOf(((Implementation) this.mImpl).getCwcRRSwEnable());
                break;
            case 4:
                ((Implementation) this.mImpl).setCwcSwFREnable(Boolean.parseBoolean(str2));
                break;
            case 5:
                ((Implementation) this.mImpl).setCwcSwEnable(Boolean.parseBoolean(str2));
                break;
            case 6:
                obj = Integer.valueOf(((Implementation) this.mImpl).getCwcChargeSt());
                break;
            case 7:
                obj = Integer.valueOf(((Implementation) this.mImpl).getCwcRLChargeSt());
                break;
            case '\b':
                ((Implementation) this.mImpl).setRightSlideDoorMode(Integer.parseInt(split[0]));
                break;
            case '\t':
                obj = Integer.valueOf(((Implementation) this.mImpl).getCwcFRChargeSt());
                break;
            case '\n':
                ((Implementation) this.mImpl).setWiperSensitivity(Integer.parseInt(split[0]));
                obj = Integer.valueOf(((Implementation) this.mImpl).getLeftSlideDoorMode());
                break;
            case 11:
                obj = Integer.valueOf(((Implementation) this.mImpl).getRightSlideDoorState());
                break;
            case '\f':
                obj = Boolean.valueOf(((Implementation) this.mImpl).getCwcSwEnable());
                break;
            case '\r':
                obj = Boolean.valueOf(((Implementation) this.mImpl).getCwcRLChargeErrorSt());
                break;
            case 14:
                obj = Integer.valueOf(((Implementation) this.mImpl).getLeftSlideDoorState());
                break;
            case 15:
                ((Implementation) this.mImpl).controlRightSlideDoor(Integer.parseInt(split[0]));
                break;
            case 16:
                obj = Boolean.valueOf(((Implementation) this.mImpl).getCwcRLSwEnable());
                break;
            case 17:
                obj = Integer.valueOf(((Implementation) this.mImpl).getRightSlideDoorMode());
                break;
            case 18:
                obj = Boolean.valueOf(((Implementation) this.mImpl).getCwcFRChargeErrorSt());
                break;
            case 19:
                obj = Boolean.valueOf(((Implementation) this.mImpl).getCwcFRSwEnable());
                break;
            case 20:
                ((Implementation) this.mImpl).setLeftSlideDoorMode(Integer.parseInt(split[0]));
                obj = Integer.valueOf(((Implementation) this.mImpl).getRightSlideDoorMode());
                break;
            case 21:
                obj = Integer.valueOf(((Implementation) this.mImpl).getCwcRRChargeSt());
                break;
            case 22:
                obj = Integer.valueOf(((Implementation) this.mImpl).getWiperSensitivity());
                break;
            case 23:
                obj = Integer.valueOf(((Implementation) this.mImpl).getChargePort(Boolean.parseBoolean(split[0])));
                break;
            case 24:
                obj = Boolean.valueOf(((Implementation) this.mImpl).isDoorClosed(Integer.parseInt(split[0])));
                break;
            case 25:
                ((Implementation) this.mImpl).setCwcSwRLEnable(Boolean.parseBoolean(str2));
                break;
            case 26:
                ((Implementation) this.mImpl).setTrunk(Integer.parseInt(split[0]));
                break;
            case 27:
                obj = Integer.valueOf(((Implementation) this.mImpl).getLeftSlideDoorMode());
                break;
            case 28:
                obj = Boolean.valueOf(((Implementation) this.mImpl).getCwcRRChargeErrorSt());
                break;
            case 29:
                obj = Boolean.valueOf(((Implementation) this.mImpl).getCwcChargeErrorSt());
                break;
            case 30:
                ((Implementation) this.mImpl).setChargePort(Boolean.parseBoolean(split[0]), Boolean.parseBoolean(split[1]));
                break;
        }
        return obj != null ? String.valueOf(obj) : "";
    }

    /* loaded from: classes2.dex */
    public class Callback {
        public Callback() {
        }

        public void onDoorChanged(int i, boolean z) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onDoorChanged, index: " + i + ", isClosed: " + z);
            CarBodyManagerServer.this.sendIpc("onDoorChanged", i + "," + z);
        }

        public void onTrunkStateChanged(int i) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onTrunkStateChanged: " + i);
            CarBodyManagerServer.this.sendIpc("onTrunkStateChanged", Integer.valueOf(i));
        }

        public void onChargePortChanged(boolean z, int i) {
            String str = String.valueOf(z) + "," + i;
            LogUtils.i(CarBodyManagerServer.this.TAG, "onChargePortChanged: " + str);
            CarBodyManagerServer.this.sendIpc("onChargePortChanged", str);
        }

        public void onWiperSensitivityChanged(int i) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onWiperSensitivityChanged: " + i);
            CarBodyManagerServer.this.sendIpc("onWiperSensitivityChanged", Integer.valueOf(i));
        }

        public void onLeftSlideDoorModeChanged(int i) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onLeftSlideDoorModeChanged: " + i);
            CarBodyManagerServer.this.sendIpc("onLeftSlideDoorModeChanged", Integer.valueOf(i));
        }

        public void onRightSlideDoorModeChanged(int i) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onRightSlideDoorModeChanged: " + i);
            CarBodyManagerServer.this.sendIpc("onRightSlideDoorModeChanged", Integer.valueOf(i));
        }

        public void onLeftSlideDoorStateChanged(int i) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onLeftSlideDoorStateChanged: " + i);
            CarBodyManagerServer.this.sendIpc("onLeftSlideDoorStateChanged", Integer.valueOf(i));
        }

        public void onRightSlideDoorStateChanged(int i) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onRightSlideDoorStateChanged: " + i);
            CarBodyManagerServer.this.sendIpc("onRightSlideDoorStateChanged", Integer.valueOf(i));
        }

        public void onCwcSwChanged(boolean z) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onCwcSwChanged: " + z);
            CarBodyManagerServer.this.sendIpc("onCwcSwChanged", Boolean.valueOf(z));
        }

        public void onCwcFRSwChanged(boolean z) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onCwcFRSwChanged: " + z);
            CarBodyManagerServer.this.sendIpc("onCwcFRSwChanged", Boolean.valueOf(z));
        }

        public void onCwcRLSwChanged(boolean z) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onCwcRLSwChanged: " + z);
            CarBodyManagerServer.this.sendIpc("onCwcRLSwChanged", Boolean.valueOf(z));
        }

        public void onCwcRRSwChanged(boolean z) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onCwcRRSwChanged: " + z);
            CarBodyManagerServer.this.sendIpc("onCwcRRSwChanged", Boolean.valueOf(z));
        }

        public void onCwcChargeStateChanged(int i) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onCwcChargeStateChanged: " + i);
            CarBodyManagerServer.this.sendIpc("onCwcChargeStateChanged", Integer.valueOf(i));
        }

        public void onCwcChargeErrorStateChanged(boolean z) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onCwcChargeErrorStateChanged: " + z);
            CarBodyManagerServer.this.sendIpc("onCwcChargeErrorStateChanged", Boolean.valueOf(z));
        }

        public void onCwcFRChargeStateChanged(int i) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onCwcFRChargeStateChanged: " + i);
            CarBodyManagerServer.this.sendIpc("onCwcFRChargeStateChanged", Integer.valueOf(i));
        }

        public void onCwcFRChargeErrorStateChanged(boolean z) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onCwcFRChargeErrorStateChanged: " + z);
            CarBodyManagerServer.this.sendIpc("onCwcFRChargeErrorStateChanged", Boolean.valueOf(z));
        }

        public void onCwcRLChargeStateChanged(int i) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onCwcRLChargeStateChanged: " + i);
            CarBodyManagerServer.this.sendIpc("onCwcRLChargeStateChanged", Integer.valueOf(i));
        }

        public void onCwcRLChargeErrorStateChanged(boolean z) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onCwcRLChargeErrorStateChanged: " + z);
            CarBodyManagerServer.this.sendIpc("onCwcRLChargeErrorStateChanged", Boolean.valueOf(z));
        }

        public void onCwcRRChargeStateChanged(int i) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onCwcRRChargeStateChanged: " + i);
            CarBodyManagerServer.this.sendIpc("onCwcRRChargeStateChanged", Integer.valueOf(i));
        }

        public void onCwcRRChargeErrorStateChanged(boolean z) {
            LogUtils.i(CarBodyManagerServer.this.TAG, "onCwcRRChargeErrorStateChanged: " + z);
            CarBodyManagerServer.this.sendIpc("onCwcRRChargeErrorStateChanged", Boolean.valueOf(z));
        }
    }
}
