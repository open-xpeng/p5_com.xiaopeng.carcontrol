package com.xiaopeng.smartcontrol.sdk.server.vehicle;

import android.text.TextUtils;
import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public class SeatManagerServer extends BaseManagerServer<Implementation> {
    private Callback mCallback = new Callback();

    /* loaded from: classes2.dex */
    public interface Implementation extends BaseManagerServer.Implementation {
        int getDSeatCushionExtPos();

        int getDSeatHorzPos();

        int getDSeatLegPos();

        int getDSeatTiltPos();

        int getDSeatVerPos();

        int getMassagerIntensity(int i);

        int getPSeatHorzPos();

        int getPSeatLegPos();

        int getPSeatTiltPos();

        int getPSeatVerPos();

        int getRLSeatHorPos();

        int getRLSeatLegHorPos();

        String getRLSeatName(int i);

        int getRLSeatSelectId();

        int getRLSeatTiltPos();

        int getRRSeatHorPos();

        int getRRSeatLegHorPos();

        String getRRSeatName(int i);

        int getRRSeatSelectId();

        int getRRSeatTiltPos();

        int getSecRowLtSeatZeroGrav();

        int getSecRowRtSeatZeroGrav();

        int getTrdRowLtSeatTiltPos();

        int getTrdRowRtSeatTiltPos();

        void setDSeatCushionExtPos(int i);

        void setDSeatHorzPos(int i);

        void setDSeatLegPos(int i);

        void setDSeatTiltPos(int i);

        void setDSeatVerPos(int i);

        void setMassagerIntensity(int i, int i2);

        void setPSeatHorzPos(int i);

        void setPSeatLegPos(int i);

        void setPSeatTiltPos(int i);

        void setPSeatVerPos(int i);

        void setRLSeatHorPos(int i);

        void setRLSeatLegHorPos(int i);

        void setRLSeatTiltPos(int i);

        void setRRSeatHorPos(int i);

        void setRRSeatLegHorPos(int i);

        void setRRSeatTiltPos(int i);

        void setSecRowLtSeatZeroGrav(boolean z);

        void setSecRowRtSeatZeroGrav(boolean z);

        void setTrdRowLtSeatTiltPos(int i);

        void setTrdRowRtSeatTiltPos(int i);

        void startMassager(int i, String str);

        void stopMassager(int i);

        void switchRLSeatId(int i);

        void switchRRSeatId(int i);
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final SeatManagerServer INSTANCE = new SeatManagerServer();

        private SingletonHolder() {
        }
    }

    public static SeatManagerServer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Callback getCallback() {
        return this.mCallback;
    }

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
            case -2135711886:
                if (str.equals("setPSeatHorzPos")) {
                    c = 0;
                    break;
                }
                break;
            case -2070300484:
                if (str.equals("getRRSeatLegHorPos")) {
                    c = 1;
                    break;
                }
                break;
            case -1982255071:
                if (str.equals("getDSeatCushionExtPos")) {
                    c = 2;
                    break;
                }
                break;
            case -1969896290:
                if (str.equals("getRLSeatHorPos")) {
                    c = 3;
                    break;
                }
                break;
            case -1941796106:
                if (str.equals("getMassagerIntensity")) {
                    c = 4;
                    break;
                }
                break;
            case -1933021403:
                if (str.equals("getPSeatLegPos")) {
                    c = 5;
                    break;
                }
                break;
            case -1844441575:
                if (str.equals("getDSeatLegPos")) {
                    c = 6;
                    break;
                }
                break;
            case -1802089712:
                if (str.equals("setDSeatTiltPos")) {
                    c = 7;
                    break;
                }
                break;
            case -1764927760:
                if (str.equals("switchRRSeatSelectId")) {
                    c = '\b';
                    break;
                }
                break;
            case -1754355564:
                if (str.equals("getSecRowLtSeatZeroGrav")) {
                    c = '\t';
                    break;
                }
                break;
            case -1731517300:
                if (str.equals("getRLSeatSelectId")) {
                    c = '\n';
                    break;
                }
                break;
            case -1646402192:
                if (str.equals("getPSeatVerPos")) {
                    c = 11;
                    break;
                }
                break;
            case -1557822364:
                if (str.equals("getDSeatVerPos")) {
                    c = '\f';
                    break;
                }
                break;
            case -1474848826:
                if (str.equals("getTrdRowRtSeatTiltPos")) {
                    c = '\r';
                    break;
                }
                break;
            case -1163849608:
                if (str.equals("getPSeatTiltPos")) {
                    c = 14;
                    break;
                }
                break;
            case -1150266832:
                if (str.equals("setRRSeatLegHorPos")) {
                    c = 15;
                    break;
                }
                break;
            case -1059143766:
                if (str.equals("setRLSeatHorPos")) {
                    c = 16;
                    break;
                }
                break;
            case -890413463:
                if (str.equals("stopMassager")) {
                    c = 17;
                    break;
                }
                break;
            case -884849450:
                if (str.equals("setRLSeatTiltPos")) {
                    c = 18;
                    break;
                }
                break;
            case -492740018:
                if (str.equals("getSecRowRtSeatZeroGrav")) {
                    c = 19;
                    break;
                }
                break;
            case -300489742:
                if (str.equals("getDSeatHorzPos")) {
                    c = 20;
                    break;
                }
                break;
            case -253097084:
                if (str.equals("setPSeatTiltPos")) {
                    c = 21;
                    break;
                }
                break;
            case -198532512:
                if (str.equals("getRLSeatName")) {
                    c = 22;
                    break;
                }
                break;
            case -161720518:
                if (str.equals("setTrdRowRtSeatTiltPos")) {
                    c = 23;
                    break;
                }
                break;
            case -110353136:
                if (str.equals("setRRSeatTiltPos")) {
                    c = 24;
                    break;
                }
                break;
            case -4509718:
                if (str.equals("switchRLSeatSelectId")) {
                    c = 25;
                    break;
                }
                break;
            case 133297444:
                if (str.equals("getRRSeatHorPos")) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case 285569216:
                if (str.equals("getTrdRowLtSeatTiltPos")) {
                    c = 27;
                    break;
                }
                break;
            case 297916320:
                if (str.equals("setSecRowLtSeatZeroGrav")) {
                    c = 28;
                    break;
                }
                break;
            case 409975497:
                if (str.equals("startMassager")) {
                    c = 29;
                    break;
                }
                break;
            case 553955885:
                if (str.equals("setDSeatCushionExtPos")) {
                    c = 30;
                    break;
                }
                break;
            case 590209689:
                if (str.equals("setPSeatLegPos")) {
                    c = 31;
                    break;
                }
                break;
            case 610262782:
                if (str.equals("setDSeatHorzPos")) {
                    c = ' ';
                    break;
                }
                break;
            case 678789517:
                if (str.equals("setDSeatLegPos")) {
                    c = '!';
                    break;
                }
                break;
            case 803031954:
                if (str.equals("getRRSeatSelectId")) {
                    c = '\"';
                    break;
                }
                break;
            case 876828900:
                if (str.equals("setPSeatVerPos")) {
                    c = '#';
                    break;
                }
                break;
            case 946593378:
                if (str.equals("getRLSeatTiltPos")) {
                    c = '$';
                    break;
                }
                break;
            case 963051266:
                if (str.equals("getRLSeatLegHorPos")) {
                    c = '%';
                    break;
                }
                break;
            case 965408728:
                if (str.equals("setDSeatVerPos")) {
                    c = '&';
                    break;
                }
                break;
            case 1044049968:
                if (str.equals("setRRSeatHorPos")) {
                    c = '\'';
                    break;
                }
                break;
            case 1248502886:
                if (str.equals("getPSeatHorzPos")) {
                    c = '(';
                    break;
                }
                break;
            case 1559531866:
                if (str.equals("setSecRowRtSeatZeroGrav")) {
                    c = ')';
                    break;
                }
                break;
            case 1582125060:
                if (str.equals("getDSeatTiltPos")) {
                    c = '*';
                    break;
                }
                break;
            case 1598697524:
                if (str.equals("setTrdRowLtSeatTiltPos")) {
                    c = '+';
                    break;
                }
                break;
            case 1721089692:
                if (str.equals("getRRSeatTiltPos")) {
                    c = ',';
                    break;
                }
                break;
            case 1742247786:
                if (str.equals("setMassagerIntensity")) {
                    c = '-';
                    break;
                }
                break;
            case 1841642598:
                if (str.equals("getRRSeatName")) {
                    c = '.';
                    break;
                }
                break;
            case 1883084918:
                if (str.equals("setRLSeatLegHorPos")) {
                    c = '/';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                ((Implementation) this.mImpl).setPSeatHorzPos(Integer.parseInt(str2));
                break;
            case 1:
                obj = Integer.valueOf(((Implementation) this.mImpl).getRRSeatLegHorPos());
                break;
            case 2:
                obj = Integer.valueOf(((Implementation) this.mImpl).getDSeatCushionExtPos());
                break;
            case 3:
                obj = Integer.valueOf(((Implementation) this.mImpl).getRLSeatHorPos());
                break;
            case 4:
                obj = Integer.valueOf(((Implementation) this.mImpl).getMassagerIntensity(Integer.parseInt(split[0])));
                break;
            case 5:
                obj = Integer.valueOf(((Implementation) this.mImpl).getPSeatLegPos());
                break;
            case 6:
                obj = Integer.valueOf(((Implementation) this.mImpl).getDSeatLegPos());
                break;
            case 7:
                ((Implementation) this.mImpl).setDSeatTiltPos(Integer.parseInt(str2));
                break;
            case '\b':
                ((Implementation) this.mImpl).switchRRSeatId(Integer.parseInt(str2));
                break;
            case '\t':
                obj = Integer.valueOf(((Implementation) this.mImpl).getSecRowLtSeatZeroGrav());
                break;
            case '\n':
                obj = Integer.valueOf(((Implementation) this.mImpl).getRLSeatSelectId());
                break;
            case 11:
                obj = Integer.valueOf(((Implementation) this.mImpl).getPSeatVerPos());
                break;
            case '\f':
                obj = Integer.valueOf(((Implementation) this.mImpl).getDSeatVerPos());
                break;
            case '\r':
                obj = Integer.valueOf(((Implementation) this.mImpl).getTrdRowRtSeatTiltPos());
                break;
            case 14:
                obj = Integer.valueOf(((Implementation) this.mImpl).getPSeatTiltPos());
                break;
            case 15:
                ((Implementation) this.mImpl).setRRSeatLegHorPos(Integer.parseInt(str2));
                break;
            case 16:
                ((Implementation) this.mImpl).setRLSeatHorPos(Integer.parseInt(str2));
                break;
            case 17:
                ((Implementation) this.mImpl).stopMassager(Integer.parseInt(split[0]));
                break;
            case 18:
                ((Implementation) this.mImpl).setRLSeatTiltPos(Integer.parseInt(str2));
                break;
            case 19:
                obj = Integer.valueOf(((Implementation) this.mImpl).getSecRowRtSeatZeroGrav());
                break;
            case 20:
                obj = Integer.valueOf(((Implementation) this.mImpl).getDSeatHorzPos());
                break;
            case 21:
                ((Implementation) this.mImpl).setPSeatTiltPos(Integer.parseInt(str2));
                break;
            case 22:
                obj = ((Implementation) this.mImpl).getRLSeatName(Integer.parseInt(str2));
                break;
            case 23:
                ((Implementation) this.mImpl).setTrdRowRtSeatTiltPos(Integer.parseInt(str2));
                break;
            case 24:
                ((Implementation) this.mImpl).setRRSeatTiltPos(Integer.parseInt(str2));
                break;
            case 25:
                ((Implementation) this.mImpl).switchRLSeatId(Integer.parseInt(str2));
                break;
            case 26:
                obj = Integer.valueOf(((Implementation) this.mImpl).getRRSeatHorPos());
                break;
            case 27:
                obj = Integer.valueOf(((Implementation) this.mImpl).getTrdRowLtSeatTiltPos());
                break;
            case 28:
                ((Implementation) this.mImpl).setSecRowLtSeatZeroGrav(Boolean.parseBoolean(str2));
                break;
            case 29:
                ((Implementation) this.mImpl).startMassager(Integer.parseInt(split[0]), split[1]);
                break;
            case 30:
                ((Implementation) this.mImpl).setDSeatCushionExtPos(Integer.parseInt(str2));
                break;
            case 31:
                ((Implementation) this.mImpl).setPSeatLegPos(Integer.parseInt(str2));
                break;
            case ' ':
                ((Implementation) this.mImpl).setDSeatHorzPos(Integer.parseInt(str2));
                break;
            case '!':
                ((Implementation) this.mImpl).setDSeatLegPos(Integer.parseInt(str2));
                break;
            case '\"':
                obj = Integer.valueOf(((Implementation) this.mImpl).getRRSeatSelectId());
                break;
            case '#':
                ((Implementation) this.mImpl).setPSeatVerPos(Integer.parseInt(str2));
                break;
            case '$':
                obj = Integer.valueOf(((Implementation) this.mImpl).getRLSeatTiltPos());
                break;
            case '%':
                obj = Integer.valueOf(((Implementation) this.mImpl).getRLSeatLegHorPos());
                break;
            case '&':
                ((Implementation) this.mImpl).setDSeatVerPos(Integer.parseInt(str2));
                break;
            case '\'':
                ((Implementation) this.mImpl).setRRSeatHorPos(Integer.parseInt(str2));
                break;
            case '(':
                obj = Integer.valueOf(((Implementation) this.mImpl).getPSeatHorzPos());
                break;
            case ')':
                ((Implementation) this.mImpl).setSecRowRtSeatZeroGrav(Boolean.parseBoolean(str2));
                break;
            case '*':
                obj = Integer.valueOf(((Implementation) this.mImpl).getDSeatTiltPos());
                break;
            case '+':
                ((Implementation) this.mImpl).setTrdRowLtSeatTiltPos(Integer.parseInt(str2));
                break;
            case ',':
                obj = Integer.valueOf(((Implementation) this.mImpl).getRRSeatTiltPos());
                break;
            case '-':
                ((Implementation) this.mImpl).setMassagerIntensity(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                break;
            case '.':
                obj = ((Implementation) this.mImpl).getRRSeatName(Integer.parseInt(str2));
                break;
            case '/':
                ((Implementation) this.mImpl).setRLSeatLegHorPos(Integer.parseInt(str2));
                break;
        }
        return obj != null ? String.valueOf(obj) : "";
    }

    /* loaded from: classes2.dex */
    public class Callback {
        public Callback() {
        }

        public void onDrvHorPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onDrvHorPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onDrvHorPosChanged", Integer.valueOf(i));
        }

        public void onDrvVerPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onDrvVerPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onDrvVerPosChanged", Integer.valueOf(i));
        }

        public void onDrvTiltPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onDrvTiltPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onDrvTiltPosChanged", Integer.valueOf(i));
        }

        public void onDrvLegPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onDrvLegPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onDrvLegPosChanged", Integer.valueOf(i));
        }

        public void onDrvCushionExtPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onDrvCushionExtPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onDrvCushionExtPosChanged", Integer.valueOf(i));
        }

        public void onPsnVerPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onPsnVerPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onPsnVerPosChanged", Integer.valueOf(i));
        }

        public void onPsnHorPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onPsnHorPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onPsnHorPosChanged", Integer.valueOf(i));
        }

        public void onPsnTiltPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onPsnTiltPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onPsnTiltPosChanged", Integer.valueOf(i));
        }

        public void onPsnLegPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onPsnLegPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onPsnLegPosChanged", Integer.valueOf(i));
        }

        public void onRlSeatTiltPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onRlSeatTiltPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onRlSeatTiltPosChanged", Integer.valueOf(i));
        }

        public void onRlSeatLegHorPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onRlSeatLegHorPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onRlSeatLegHorPosChanged", Integer.valueOf(i));
        }

        public void onRlSeatHorPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onRlSeatHorPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onRlSeatHorPosChanged", Integer.valueOf(i));
        }

        public void onRrSeatTiltPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onRrSeatTiltPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onRrSeatTiltPosChanged", Integer.valueOf(i));
        }

        public void onRrSeatLegHorPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onRrSeatLegHorPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onRrSeatLegHorPosChanged", Integer.valueOf(i));
        }

        public void onRrSeatHorPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onRrSeatHorPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onRrSeatHorPosChanged", Integer.valueOf(i));
        }

        public void onTrdRowLtSeatTiltPosChangedpos(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onTrdRowLtSeatTiltPosChangedpos: " + i);
            SeatManagerServer.this.sendIpc("onTrdRowLtSeatTiltPosChangedpos", Integer.valueOf(i));
        }

        public void onTrdRowRtSeatTiltPosChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onTrdRowRtSeatTiltPosChanged: " + i);
            SeatManagerServer.this.sendIpc("onTrdRowRtSeatTiltPosChanged", Integer.valueOf(i));
        }

        public void onRLSeatNameChanged(int i, String str) {
            String str2 = i + "," + str;
            LogUtils.i(SeatManagerServer.this.TAG, "onRLSeatNameChanged: " + str2);
            SeatManagerServer.this.sendIpc("onRLSeatNameChanged", str2);
        }

        public void onRRSeatNameChanged(int i, String str) {
            String str2 = i + "," + str;
            LogUtils.i(SeatManagerServer.this.TAG, "onRRSeatNameChanged: " + str2);
            SeatManagerServer.this.sendIpc("onRRSeatNameChanged", str2);
        }

        public void onRLSeatSelectIdChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onRLSeatSelectIdChanged: " + i);
            SeatManagerServer.this.sendIpc("onRLSeatSelectIdChanged", Integer.valueOf(i));
        }

        public void onRRSeatSelectIdChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onRRSeatSelectIdChanged: " + i);
            SeatManagerServer.this.sendIpc("onRRSeatSelectIdChanged", Integer.valueOf(i));
        }

        public void onSecRowLtSeatZeroGravChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onSecRowLtSeatZeroGravChanged: " + i);
            SeatManagerServer.this.sendIpc("onSecRowLtSeatZeroGravChanged", Integer.valueOf(i));
        }

        public void onSecRowRtSeatZeroGravChanged(int i) {
            LogUtils.i(SeatManagerServer.this.TAG, "onSecRowRtSeatZeroGravChanged: " + i);
            SeatManagerServer.this.sendIpc("onSecRowRtSeatZeroGravChanged", Integer.valueOf(i));
        }

        public void onMassagerStatusChange(int i, String str, int i2, boolean z) {
            String str2 = i + "," + str + "," + i2 + "," + z;
            LogUtils.i(SeatManagerServer.this.TAG, "onMassagerStatusChange: " + str2);
            SeatManagerServer.this.sendIpc("onMassagerStatusChange", str2);
        }
    }
}
