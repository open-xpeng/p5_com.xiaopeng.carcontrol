package com.xiaopeng.smartcontrol.sdk.server.hvac;

import android.text.TextUtils;
import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public class HvacManagerServer extends BaseManagerServer<Implementation> {
    private static final String TAG = "HvacManagerServer";
    private Callback mCallback = new Callback();

    /* loaded from: classes2.dex */
    public interface Implementation extends BaseManagerServer.Implementation {
        int getHvacAcpConsumption();

        boolean getHvacAirPurgeMode();

        int getHvacCirculationMode();

        float getHvacDriverTemp();

        int getHvacDrvSeatHeatLevel();

        int getHvacDrvSeatVentLevel();

        int getHvacEAVDriverLeftHPos();

        int getHvacEAVDriverLeftVPos();

        int getHvacEAVDriverRightHPos();

        int getHvacEAVDriverRightVPos();

        int getHvacEAVPsnLeftHPos();

        int getHvacEAVPsnLeftVPos();

        int getHvacEAVPsnRightHPos();

        int getHvacEAVPsnRightVPos();

        float getHvacExternalTemp();

        float getHvacInnerPM25();

        float getHvacInnerTemp();

        int getHvacPsnSeatHeatLevel();

        int getHvacPsnSeatVentLevel();

        float getHvacPsnTemp();

        int getHvacPtcConsumption();

        int getHvacRLSeatHeatLevel();

        int getHvacRLSeatVentLevel();

        float getHvacRLTemp();

        int getHvacRRSeatHeatLevel();

        int getHvacRRSeatVentLevel();

        float getHvacRRTemp();

        int getHvacRearWindBlowMode();

        int getHvacRearWindSpeedLevel();

        int getHvacThirdRowWindBlowMode();

        float getHvacThirdTemp();

        int getHvacWindBlowMode();

        int getHvacWindModeColor();

        int getHvacWindSpeedLevel();

        int getSteerHeatLevel();

        boolean isBackMirrorHeatEnabled();

        boolean isFrontMirrorHeatEnabled();

        boolean isHvacAcModeOn();

        boolean isHvacAutoMode();

        boolean isHvacDeodorantEnable();

        boolean isHvacDriverSyncMode();

        boolean isHvacFrontDefrostEnable();

        boolean isHvacNIVentOn();

        boolean isHvacPowerOn();

        boolean isHvacRapidCoolingEnable();

        boolean isHvacRapidHeatEnable();

        boolean isHvacRearAutoModeOn();

        boolean isHvacRearPowerModeOn();

        boolean isHvacTopVentOn();

        boolean isSupportHvacDrvSeatHeat();

        boolean isSupportHvacDrvSeatVent();

        boolean isSupportHvacPsnSeatHeat();

        boolean isSupportHvacPsnSeatVent();

        boolean isSupportHvacRearSeatHeat();

        boolean isSupportHvacRearSeatVent();

        boolean isSupportHvacSteerHeat();

        void setBackMirrorHeatEnable(boolean z);

        void setFrontMirrorHeatEnable(boolean z);

        void setHvacAcMode(boolean z);

        void setHvacAutoMode(boolean z);

        void setHvacCirculationMode(int i);

        void setHvacDeodorantEnable(boolean z);

        void setHvacDriverSyncMode(boolean z);

        void setHvacDrvSeatHeatLevel(int i);

        void setHvacDrvSeatVentLevel(int i);

        void setHvacDrvTempStep(boolean z);

        void setHvacEAVDriverLeftHPosDirect(int i);

        void setHvacEAVDriverLeftVPosDirect(int i);

        void setHvacEAVDriverRightHPosDirect(int i);

        void setHvacEAVDriverRightVPosDirect(int i);

        void setHvacEAVPsnLeftHPosDirect(int i);

        void setHvacEAVPsnLeftVPosDirect(int i);

        void setHvacEAVPsnRightHPosDirect(int i);

        void setHvacEAVPsnRightVPosDirect(int i);

        void setHvacFrontDefrostEnable(boolean z);

        void setHvacNIVent(boolean z);

        void setHvacPower(boolean z);

        void setHvacPsnSeatHeatLevel(int i);

        void setHvacPsnSeatVentLevel(int i);

        void setHvacPsnTemp(float f);

        void setHvacPsnTempStep(boolean z);

        void setHvacRLSeatHeatLevel(int i);

        void setHvacRLSeatVentLevel(int i);

        void setHvacRLTemp(float f);

        void setHvacRLTempStep(boolean z);

        void setHvacRRSeatHeatLevel(int i);

        void setHvacRRSeatVentLevel(int i);

        void setHvacRRTemp(float f);

        void setHvacRRTempStep(boolean z);

        void setHvacRapidCoolingEnable(boolean z);

        void setHvacRapidHeatEnable(boolean z);

        void setHvacRearAutoMode(boolean z);

        void setHvacRearPowerMode(boolean z);

        void setHvacRearWindBlowMode(int i);

        void setHvacRearWindSpeedLevel(int i);

        void setHvacRearWindSpeedStep(boolean z);

        void setHvacTempDriver(float f);

        void setHvacThirdRowWindBlowMode(int i);

        void setHvacThirdTemp(float f);

        void setHvacThirdTempStep(boolean z);

        void setHvacTopVentOn();

        void setHvacWindBlowMode(int i);

        void setHvacWindSpeedDown();

        void setHvacWindSpeedLevel(int i);

        void setHvacWindSpeedMax();

        void setHvacWindSpeedMin();

        void setHvacWindSpeedUp();

        void setSteerHeatLevel(int i);
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final HvacManagerServer INSTANCE = new HvacManagerServer();

        private SingletonHolder() {
        }
    }

    public static HvacManagerServer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Callback getCallback() {
        return this.mCallback;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer
    public String onIpcCall(String str, String str2) {
        Object obj = null;
        if (this.mImpl == 0) {
            LogUtils.e(TAG, "mImpl is null");
            return null;
        }
        String[] split = TextUtils.isEmpty(str2) ? new String[0] : str2.split(",");
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2088953771:
                if (str.equals("isHvacRapidHeatEnable")) {
                    c = 0;
                    break;
                }
                break;
            case -2053786248:
                if (str.equals("setHvacRRSeatVentLevel")) {
                    c = 1;
                    break;
                }
                break;
            case -2035388710:
                if (str.equals("isHvacRapidCoolingEnable")) {
                    c = 2;
                    break;
                }
                break;
            case -2029186174:
                if (str.equals("setHvacThirdRowWindBlowMode")) {
                    c = 3;
                    break;
                }
                break;
            case -1925142119:
                if (str.equals("getSteerHeatLevel")) {
                    c = 4;
                    break;
                }
                break;
            case -1900673428:
                if (str.equals("setHvacEAVDriverLeftHPosDirect")) {
                    c = 5;
                    break;
                }
                break;
            case -1885313068:
                if (str.equals("setHvacDrvSeatVentLevel")) {
                    c = 6;
                    break;
                }
                break;
            case -1787892873:
                if (str.equals("setHvacWindSpeedLevel")) {
                    c = 7;
                    break;
                }
                break;
            case -1733006524:
                if (str.equals("setHvacAutoMode")) {
                    c = '\b';
                    break;
                }
                break;
            case -1699768593:
                if (str.equals("setHvacWindBlowMode")) {
                    c = '\t';
                    break;
                }
                break;
            case -1676263767:
                if (str.equals("isHvacRearPowerModeOn")) {
                    c = '\n';
                    break;
                }
                break;
            case -1676059572:
                if (str.equals("isSupportHvacSteerHeat")) {
                    c = 11;
                    break;
                }
                break;
            case -1660872647:
                if (str.equals("getHvacPsnTemp")) {
                    c = '\f';
                    break;
                }
                break;
            case -1641349390:
                if (str.equals("setHvacRearPowerMode")) {
                    c = '\r';
                    break;
                }
                break;
            case -1635267489:
                if (str.equals("setHvacDeodorantEnable")) {
                    c = 14;
                    break;
                }
                break;
            case -1577789642:
                if (str.equals("setHvacPsnSeatHeatLevel")) {
                    c = 15;
                    break;
                }
                break;
            case -1540614341:
                if (str.equals("setBackMirrorHeatEnable")) {
                    c = 16;
                    break;
                }
                break;
            case -1506520224:
                if (str.equals("setHvacEAVPsnRightVPosDirect")) {
                    c = 17;
                    break;
                }
                break;
            case -1496453603:
                if (str.equals("setHvacRapidHeatEnable")) {
                    c = 18;
                    break;
                }
                break;
            case -1446283853:
                if (str.equals("setHvacRearWindBlowMode")) {
                    c = 19;
                    break;
                }
                break;
            case -1399936085:
                if (str.equals("isSupportHvacPsnSeatHeat")) {
                    c = 20;
                    break;
                }
                break;
            case -1399518608:
                if (str.equals("isSupportHvacPsnSeatVent")) {
                    c = 21;
                    break;
                }
                break;
            case -1330144877:
                if (str.equals("setHvacEAVDriverRightVPosDirect")) {
                    c = 22;
                    break;
                }
                break;
            case -1292829549:
                if (str.equals("isFrontMirrorHeatEnabled")) {
                    c = 23;
                    break;
                }
                break;
            case -1266433169:
                if (str.equals("setHvacFrontDefrostEnable")) {
                    c = 24;
                    break;
                }
                break;
            case -1173006622:
                if (str.equals("getHvacDriverTemp")) {
                    c = 25;
                    break;
                }
                break;
            case -1171873507:
                if (str.equals("setHvacRRSeatHeatLevel")) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case -1092336278:
                if (str.equals("getHvacEAVPsnLeftHPos")) {
                    c = 27;
                    break;
                }
                break;
            case -1091919204:
                if (str.equals("getHvacEAVPsnLeftVPos")) {
                    c = 28;
                    break;
                }
                break;
            case -1059690471:
                if (str.equals("setHvacPsnTempStep")) {
                    c = 29;
                    break;
                }
                break;
            case -1034657544:
                if (str.equals("setHvacDriverSyncMode")) {
                    c = 30;
                    break;
                }
                break;
            case -1003400327:
                if (str.equals("setHvacDrvSeatHeatLevel")) {
                    c = 31;
                    break;
                }
                break;
            case -999449970:
                if (str.equals("isSupportHvacRearSeatHeat")) {
                    c = ' ';
                    break;
                }
                break;
            case -999032493:
                if (str.equals("isSupportHvacRearSeatVent")) {
                    c = '!';
                    break;
                }
                break;
            case -995908488:
                if (str.equals("setHvacCirculationMode")) {
                    c = '\"';
                    break;
                }
                break;
            case -817517815:
                if (str.equals("isHvacNIVentOn")) {
                    c = '#';
                    break;
                }
                break;
            case -736636916:
                if (str.equals("isHvacAutoMode")) {
                    c = '$';
                    break;
                }
                break;
            case -683652490:
                if (str.equals("getHvacThirdRowWindBlowMode")) {
                    c = '%';
                    break;
                }
                break;
            case -650811851:
                if (str.equals("setHvacRearWindSpeedStep")) {
                    c = '&';
                    break;
                }
                break;
            case -590510132:
                if (str.equals("getHvacEAVDriverRightHPos")) {
                    c = '\'';
                    break;
                }
                break;
            case -590093058:
                if (str.equals("getHvacEAVDriverRightVPos")) {
                    c = '(';
                    break;
                }
                break;
            case -525389876:
                if (str.equals("setHvacRLTempStep")) {
                    c = ')';
                    break;
                }
                break;
            case -467135953:
                if (str.equals("getHvacEAVDriverLeftHPos")) {
                    c = '*';
                    break;
                }
                break;
            case -466718879:
                if (str.equals("getHvacEAVDriverLeftVPos")) {
                    c = '+';
                    break;
                }
                break;
            case -448735532:
                if (str.equals("getHvacRLTemp")) {
                    c = ',';
                    break;
                }
                break;
            case -443194406:
                if (str.equals("getHvacRRTemp")) {
                    c = '-';
                    break;
                }
                break;
            case -361033792:
                if (str.equals("isHvacDriverSyncMode")) {
                    c = '.';
                    break;
                }
                break;
            case -356895202:
                if (str.equals("setHvacEAVDriverLeftVPosDirect")) {
                    c = '/';
                    break;
                }
                break;
            case -333562806:
                if (str.equals("getHvacRLSeatVentLevel")) {
                    c = '0';
                    break;
                }
                break;
            case -217006971:
                if (str.equals("getHvacPsnSeatVentLevel")) {
                    c = '1';
                    break;
                }
                break;
            case -156040733:
                if (str.equals("getHvacWindBlowMode")) {
                    c = '2';
                    break;
                }
                break;
            case -123351497:
                if (str.equals("isHvacFrontDefrostEnable")) {
                    c = '3';
                    break;
                }
                break;
            case -118502199:
                if (str.equals("setHvacThirdTemp")) {
                    c = '4';
                    break;
                }
                break;
            case -29136533:
                if (str.equals("getHvacWindSpeedLevel")) {
                    c = '5';
                    break;
                }
                break;
            case 80644687:
                if (str.equals("setHvacWindSpeedDown")) {
                    c = '6';
                    break;
                }
                break;
            case 208168130:
                if (str.equals("setHvacNIVent")) {
                    c = '7';
                    break;
                }
                break;
            case 286847954:
                if (str.equals("getHvacWindModeColor")) {
                    c = '8';
                    break;
                }
                break;
            case 312933256:
                if (str.equals("setHvacWindSpeedUp")) {
                    c = '9';
                    break;
                }
                break;
            case 313922071:
                if (str.equals("setHvacTopVentOn")) {
                    c = ':';
                    break;
                }
                break;
            case 325395680:
                if (str.equals("setHvacRLTemp")) {
                    c = ';';
                    break;
                }
                break;
            case 330936806:
                if (str.equals("setHvacRRTemp")) {
                    c = '<';
                    break;
                }
                break;
            case 347742927:
                if (str.equals("isHvacRearAutoModeOn")) {
                    c = '=';
                    break;
                }
                break;
            case 357382344:
                if (str.equals("getHvacDrvSeatVentLevel")) {
                    c = '>';
                    break;
                }
                break;
            case 449482031:
                if (str.equals("getHvacRearWindSpeedLevel")) {
                    c = '?';
                    break;
                }
                break;
            case 548349935:
                if (str.equals("getHvacRLSeatHeatLevel")) {
                    c = '@';
                    break;
                }
                break;
            case 587489023:
                if (str.equals("setHvacEAVPsnLeftHPosDirect")) {
                    c = 'A';
                    break;
                }
                break;
            case 664905770:
                if (str.equals("getHvacPsnSeatHeatLevel")) {
                    c = 'B';
                    break;
                }
                break;
            case 675823362:
                if (str.equals("getHvacPtcConsumption")) {
                    c = 'C';
                    break;
                }
                break;
            case 794934069:
                if (str.equals("setHvacThirdTempStep")) {
                    c = 'D';
                    break;
                }
                break;
            case 796411559:
                if (str.equals("getHvacRearWindBlowMode")) {
                    c = 'E';
                    break;
                }
                break;
            case 841009907:
                if (str.equals("setHvacPower")) {
                    c = 'F';
                    break;
                }
                break;
            case 862358445:
                if (str.equals("setHvacPsnTemp")) {
                    c = 'G';
                    break;
                }
                break;
            case 928052740:
                if (str.equals("getHvacRRSeatVentLevel")) {
                    c = 'H';
                    break;
                }
                break;
            case 979565502:
                if (str.equals("setHvacRLSeatVentLevel")) {
                    c = 'I';
                    break;
                }
                break;
            case 1059192709:
                if (str.equals("getHvacExternalTemp")) {
                    c = 'J';
                    break;
                }
                break;
            case 1110988311:
                if (str.equals("setHvacWindSpeedMax")) {
                    c = 'K';
                    break;
                }
                break;
            case 1110988549:
                if (str.equals("setHvacWindSpeedMin")) {
                    c = 'L';
                    break;
                }
                break;
            case 1116496914:
                if (str.equals("setHvacRapidCoolingEnable")) {
                    c = 'M';
                    break;
                }
                break;
            case 1136608847:
                if (str.equals("isHvacTopVentOn")) {
                    c = 'N';
                    break;
                }
                break;
            case 1239295085:
                if (str.equals("getHvacDrvSeatHeatLevel")) {
                    c = 'O';
                    break;
                }
                break;
            case 1244668846:
                if (str.equals("setHvacEAVPsnRightHPosDirect")) {
                    c = 'P';
                    break;
                }
                break;
            case 1292773691:
                if (str.equals("setHvacRearWindSpeedLevel")) {
                    c = 'Q';
                    break;
                }
                break;
            case 1323584705:
                if (str.equals("isBackMirrorHeatEnabled")) {
                    c = 'R';
                    break;
                }
                break;
            case 1358318600:
                if (str.equals("setHvacRearAutoMode")) {
                    c = 'S';
                    break;
                }
                break;
            case 1421044193:
                if (str.equals("setHvacEAVDriverRightHPosDirect")) {
                    c = 'T';
                    break;
                }
                break;
            case 1429672357:
                if (str.equals("setSteerHeatLevel")) {
                    c = 'U';
                    break;
                }
                break;
            case 1503116273:
                if (str.equals("getHvacEAVPsnRightHPos")) {
                    c = 'V';
                    break;
                }
                break;
            case 1503533347:
                if (str.equals("getHvacEAVPsnRightVPos")) {
                    c = 'W';
                    break;
                }
                break;
            case 1514785234:
                if (str.equals("setHvacRRTempStep")) {
                    c = 'X';
                    break;
                }
                break;
            case 1583990201:
                if (str.equals("setFrontMirrorHeatEnable")) {
                    c = 'Y';
                    break;
                }
                break;
            case 1605390728:
                if (str.equals("isSupportHvacDrvSeatHeat")) {
                    c = 'Z';
                    break;
                }
                break;
            case 1605808205:
                if (str.equals("isSupportHvacDrvSeatVent")) {
                    c = '[';
                    break;
                }
                break;
            case 1712940629:
                if (str.equals("getHvacThirdTemp")) {
                    c = '\\';
                    break;
                }
                break;
            case 1758643818:
                if (str.equals("isHvacPowerOn")) {
                    c = ']';
                    break;
                }
                break;
            case 1761762286:
                if (str.equals("setHvacTempDriver")) {
                    c = '^';
                    break;
                }
                break;
            case 1770187984:
                if (str.equals("getHvacInnerPM25")) {
                    c = '_';
                    break;
                }
                break;
            case 1770332100:
                if (str.equals("getHvacInnerTemp")) {
                    c = '`';
                    break;
                }
                break;
            case 1775815690:
                if (str.equals("getHvacAirPurgeMode")) {
                    c = 'a';
                    break;
                }
                break;
            case 1809965481:
                if (str.equals("getHvacRRSeatHeatLevel")) {
                    c = 'b';
                    break;
                }
                break;
            case 1835264913:
                if (str.equals("setHvacPsnSeatVentLevel")) {
                    c = 'c';
                    break;
                }
                break;
            case 1861478243:
                if (str.equals("setHvacRLSeatHeatLevel")) {
                    c = 'd';
                    break;
                }
                break;
            case 1945636342:
                if (str.equals("setHvacDrvTempStep")) {
                    c = 'e';
                    break;
                }
                break;
            case 1985930500:
                if (str.equals("getHvacCirculationMode")) {
                    c = 'f';
                    break;
                }
                break;
            case 2040227315:
                if (str.equals("getHvacAcpConsumption")) {
                    c = 'g';
                    break;
                }
                break;
            case 2067199639:
                if (str.equals("isHvacDeodorantEnable")) {
                    c = 'h';
                    break;
                }
                break;
            case 2131267249:
                if (str.equals("setHvacEAVPsnLeftVPosDirect")) {
                    c = 'i';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                obj = Boolean.valueOf(((Implementation) this.mImpl).isHvacRapidHeatEnable());
                break;
            case 1:
                ((Implementation) this.mImpl).setHvacRRSeatVentLevel(Integer.parseInt(split[0]));
                break;
            case 2:
                obj = Boolean.valueOf(((Implementation) this.mImpl).isHvacRapidCoolingEnable());
                break;
            case 3:
                ((Implementation) this.mImpl).setHvacThirdRowWindBlowMode(Integer.parseInt(split[0]));
                break;
            case 4:
                obj = Integer.valueOf(((Implementation) this.mImpl).getSteerHeatLevel());
                break;
            case 5:
                ((Implementation) this.mImpl).setHvacEAVDriverLeftHPosDirect(Integer.parseInt(split[0]));
                break;
            case 6:
                ((Implementation) this.mImpl).setHvacDrvSeatVentLevel(Integer.parseInt(split[0]));
                break;
            case 7:
                ((Implementation) this.mImpl).setHvacWindSpeedLevel(Integer.parseInt(split[0]));
                break;
            case '\b':
                ((Implementation) this.mImpl).setHvacAutoMode(Boolean.parseBoolean(split[0]));
                break;
            case '\t':
                ((Implementation) this.mImpl).setHvacWindBlowMode(Integer.parseInt(split[0]));
                break;
            case '\n':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isHvacRearPowerModeOn());
                break;
            case 11:
                obj = Boolean.valueOf(((Implementation) this.mImpl).isSupportHvacSteerHeat());
                break;
            case '\f':
                obj = Float.valueOf(((Implementation) this.mImpl).getHvacPsnTemp());
                break;
            case '\r':
                ((Implementation) this.mImpl).setHvacRearPowerMode(Boolean.parseBoolean(split[0]));
                break;
            case 14:
                ((Implementation) this.mImpl).setHvacDeodorantEnable(Boolean.parseBoolean(split[0]));
                break;
            case 15:
                ((Implementation) this.mImpl).setHvacPsnSeatHeatLevel(Integer.parseInt(split[0]));
                break;
            case 16:
                ((Implementation) this.mImpl).setBackMirrorHeatEnable(Boolean.parseBoolean(split[0]));
                break;
            case 17:
                ((Implementation) this.mImpl).setHvacEAVPsnRightVPosDirect(Integer.parseInt(split[0]));
                break;
            case 18:
                ((Implementation) this.mImpl).setHvacRapidHeatEnable(Boolean.parseBoolean(split[0]));
                break;
            case 19:
                ((Implementation) this.mImpl).setHvacRearWindBlowMode(Integer.parseInt(split[0]));
                break;
            case 20:
                obj = Boolean.valueOf(((Implementation) this.mImpl).isSupportHvacPsnSeatHeat());
                break;
            case 21:
                obj = Boolean.valueOf(((Implementation) this.mImpl).isSupportHvacPsnSeatVent());
                break;
            case 22:
                ((Implementation) this.mImpl).setHvacEAVDriverRightVPosDirect(Integer.parseInt(split[0]));
                break;
            case 23:
                obj = Boolean.valueOf(((Implementation) this.mImpl).isFrontMirrorHeatEnabled());
                break;
            case 24:
                ((Implementation) this.mImpl).setHvacFrontDefrostEnable(Boolean.parseBoolean(split[0]));
                break;
            case 25:
                obj = Float.valueOf(((Implementation) this.mImpl).getHvacDriverTemp());
                break;
            case 26:
                ((Implementation) this.mImpl).setHvacRRSeatHeatLevel(Integer.parseInt(split[0]));
                break;
            case 27:
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacEAVPsnLeftHPos());
                break;
            case 28:
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacEAVPsnLeftVPos());
                break;
            case 29:
                ((Implementation) this.mImpl).setHvacPsnTempStep(Boolean.parseBoolean(split[0]));
                break;
            case 30:
                ((Implementation) this.mImpl).setHvacDriverSyncMode(Boolean.parseBoolean(split[0]));
                break;
            case 31:
                ((Implementation) this.mImpl).setHvacDrvSeatHeatLevel(Integer.parseInt(split[0]));
                break;
            case ' ':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isSupportHvacRearSeatHeat());
                break;
            case '!':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isSupportHvacRearSeatVent());
                break;
            case '\"':
                ((Implementation) this.mImpl).setHvacCirculationMode(Integer.parseInt(split[0]));
                break;
            case '#':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isHvacNIVentOn());
                break;
            case '$':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isHvacAutoMode());
                break;
            case '%':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacThirdRowWindBlowMode());
                break;
            case '&':
                ((Implementation) this.mImpl).setHvacRearWindSpeedStep(Boolean.parseBoolean(split[0]));
                break;
            case '\'':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacEAVDriverRightHPos());
                break;
            case '(':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacEAVDriverRightVPos());
                break;
            case ')':
                ((Implementation) this.mImpl).setHvacRLTempStep(Boolean.parseBoolean(split[0]));
                break;
            case '*':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacEAVDriverLeftHPos());
                break;
            case '+':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacEAVDriverLeftVPos());
                break;
            case ',':
                obj = Float.valueOf(((Implementation) this.mImpl).getHvacRLTemp());
                break;
            case '-':
                obj = Float.valueOf(((Implementation) this.mImpl).getHvacRRTemp());
                break;
            case '.':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isHvacDriverSyncMode());
                break;
            case '/':
                ((Implementation) this.mImpl).setHvacEAVDriverLeftVPosDirect(Integer.parseInt(split[0]));
                break;
            case '0':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacRLSeatVentLevel());
                break;
            case '1':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacPsnSeatVentLevel());
                break;
            case '2':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacWindBlowMode());
                break;
            case '3':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isHvacFrontDefrostEnable());
                break;
            case '4':
                ((Implementation) this.mImpl).setHvacThirdTemp(Float.parseFloat(split[0]));
                break;
            case '5':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacWindSpeedLevel());
                break;
            case '6':
                ((Implementation) this.mImpl).setHvacWindSpeedDown();
                break;
            case '7':
                ((Implementation) this.mImpl).setHvacNIVent(Boolean.parseBoolean(split[0]));
                break;
            case '8':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacWindModeColor());
                break;
            case '9':
                ((Implementation) this.mImpl).setHvacWindSpeedUp();
                break;
            case ':':
                ((Implementation) this.mImpl).setHvacTopVentOn();
                break;
            case ';':
                ((Implementation) this.mImpl).setHvacRLTemp(Float.parseFloat(split[0]));
                break;
            case '<':
                ((Implementation) this.mImpl).setHvacRRTemp(Float.parseFloat(split[0]));
                break;
            case '=':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isHvacRearAutoModeOn());
                break;
            case '>':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacDrvSeatVentLevel());
                break;
            case '?':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacRearWindSpeedLevel());
                break;
            case '@':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacRLSeatHeatLevel());
                break;
            case 'A':
                ((Implementation) this.mImpl).setHvacEAVPsnLeftHPosDirect(Integer.parseInt(split[0]));
                break;
            case 'B':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacPsnSeatHeatLevel());
                break;
            case 'C':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacPtcConsumption());
                break;
            case 'D':
                ((Implementation) this.mImpl).setHvacThirdTempStep(Boolean.parseBoolean(split[0]));
                break;
            case 'E':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacRearWindBlowMode());
                break;
            case 'F':
                ((Implementation) this.mImpl).setHvacPower(Boolean.parseBoolean(split[0]));
                break;
            case 'G':
                ((Implementation) this.mImpl).setHvacPsnTemp(Float.parseFloat(split[0]));
                break;
            case 'H':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacRRSeatVentLevel());
                break;
            case 'I':
                ((Implementation) this.mImpl).setHvacRLSeatVentLevel(Integer.parseInt(split[0]));
                break;
            case 'J':
                obj = Float.valueOf(((Implementation) this.mImpl).getHvacExternalTemp());
                break;
            case 'K':
                ((Implementation) this.mImpl).setHvacWindSpeedMax();
                break;
            case 'L':
                ((Implementation) this.mImpl).setHvacWindSpeedMin();
                break;
            case 'M':
                ((Implementation) this.mImpl).setHvacRapidCoolingEnable(Boolean.parseBoolean(split[0]));
                break;
            case 'N':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isHvacTopVentOn());
                break;
            case 'O':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacDrvSeatHeatLevel());
                break;
            case 'P':
                ((Implementation) this.mImpl).setHvacEAVPsnRightHPosDirect(Integer.parseInt(split[0]));
                break;
            case 'Q':
                ((Implementation) this.mImpl).setHvacRearWindSpeedLevel(Integer.parseInt(split[0]));
                break;
            case 'R':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isBackMirrorHeatEnabled());
                break;
            case 'S':
                ((Implementation) this.mImpl).setHvacRearAutoMode(Boolean.parseBoolean(split[0]));
                break;
            case 'T':
                ((Implementation) this.mImpl).setHvacEAVDriverRightHPosDirect(Integer.parseInt(split[0]));
                break;
            case 'U':
                ((Implementation) this.mImpl).setSteerHeatLevel(Integer.parseInt(split[0]));
                break;
            case 'V':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacEAVPsnRightHPos());
                break;
            case 'W':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacEAVPsnRightVPos());
                break;
            case 'X':
                ((Implementation) this.mImpl).setHvacRRTempStep(Boolean.parseBoolean(split[0]));
                break;
            case 'Y':
                ((Implementation) this.mImpl).setFrontMirrorHeatEnable(Boolean.parseBoolean(split[0]));
                break;
            case 'Z':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isSupportHvacDrvSeatHeat());
                break;
            case '[':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isSupportHvacDrvSeatVent());
                break;
            case '\\':
                obj = Float.valueOf(((Implementation) this.mImpl).getHvacThirdTemp());
                break;
            case ']':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isHvacPowerOn());
                break;
            case '^':
                ((Implementation) this.mImpl).setHvacTempDriver(Float.parseFloat(split[0]));
                break;
            case '_':
                obj = Float.valueOf(((Implementation) this.mImpl).getHvacInnerPM25());
                break;
            case '`':
                obj = Float.valueOf(((Implementation) this.mImpl).getHvacInnerTemp());
                break;
            case 'a':
                obj = Boolean.valueOf(((Implementation) this.mImpl).getHvacAirPurgeMode());
                break;
            case 'b':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacRRSeatHeatLevel());
                break;
            case 'c':
                ((Implementation) this.mImpl).setHvacPsnSeatVentLevel(Integer.parseInt(split[0]));
                break;
            case 'd':
                ((Implementation) this.mImpl).setHvacRLSeatHeatLevel(Integer.parseInt(split[0]));
                break;
            case 'e':
                ((Implementation) this.mImpl).setHvacDrvTempStep(Boolean.parseBoolean(split[0]));
                break;
            case 'f':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacCirculationMode());
                break;
            case 'g':
                obj = Integer.valueOf(((Implementation) this.mImpl).getHvacAcpConsumption());
                break;
            case 'h':
                obj = Boolean.valueOf(((Implementation) this.mImpl).isHvacDeodorantEnable());
                break;
            case 'i':
                ((Implementation) this.mImpl).setHvacEAVPsnLeftVPosDirect(Integer.parseInt(split[0]));
                break;
        }
        return obj != null ? String.valueOf(obj) : "";
    }

    /* loaded from: classes2.dex */
    public class Callback {
        public Callback() {
        }

        public void onHvacPowerChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacPowerChanged", String.valueOf(z));
        }

        public void onHvacRearPowerChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacRearPowerChanged", String.valueOf(z));
        }

        public void onHvacAutoChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacAutoChanged", String.valueOf(z));
        }

        public void onHvacRearAutoChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacAutoChanged", String.valueOf(z));
        }

        public void onHvacAcChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacAcChanged", String.valueOf(z));
        }

        public void onHvacCirculationModeChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacCirculationModeChanged", String.valueOf(i));
        }

        public void onHvacWindLevelChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacWindLevelChanged", String.valueOf(i));
        }

        public void onHvacRearWindLevelChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacRearWindLevelChanged", String.valueOf(i));
        }

        public void onHvacWindBlowModeChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacWindBlowModeChanged", String.valueOf(i));
        }

        public void onHvacRearWindBlowModeChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacRearWindBlowModeChanged", String.valueOf(i));
        }

        public void onHvacThirdWindBlowModeChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacThirdWindBlowModeChanged", String.valueOf(i));
        }

        public void onHvacNIVentChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacNIVentChanged", String.valueOf(z));
        }

        public void onHvacTopVentChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacTopVentChanged", String.valueOf(z));
        }

        public void onHvacFrontDefrostChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacFrontDefrostChanged", String.valueOf(z));
        }

        public void onHvacAutoDefogWorkStChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacAutoDefogWorkStChanged", String.valueOf(z));
        }

        public void onHvacRapidCoolingChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacRapidCoolingChanged", String.valueOf(z));
        }

        public void onHvacRapidHeatChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacRapidHeatChanged", String.valueOf(z));
        }

        public void onHvacWindModeColorChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacWindModeColorChanged", String.valueOf(i));
        }

        public void onHvacAirPurgeModeChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacAirPurgeModeChanged", String.valueOf(z));
        }

        public void onHvacDeodorantChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacDeodorantChanged", String.valueOf(z));
        }

        public void onHvacDrvTempChanged(float f) {
            HvacManagerServer.this.sendIpc("onHvacDrvTempChanged", String.valueOf(f));
        }

        public void onHvacDrvSyncChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacDrvSyncChanged", String.valueOf(z));
        }

        public void onHvacPsnTempChanged(float f) {
            HvacManagerServer.this.sendIpc("onHvacPsnTempChanged", String.valueOf(f));
        }

        public void onHvacRLTempChanged(float f) {
            HvacManagerServer.this.sendIpc("onHvacRLTempChanged", String.valueOf(f));
        }

        public void onHvacRRTempChanged(float f) {
            HvacManagerServer.this.sendIpc("onHvacRRTempChanged", String.valueOf(f));
        }

        public void onHvacThirdTempChanged(float f) {
            HvacManagerServer.this.sendIpc("onHvacThirdTempChanged", String.valueOf(f));
        }

        public void onHvacOutTempChanged(float f) {
            HvacManagerServer.this.sendIpc("onHvacOutTempChanged", String.valueOf(f));
        }

        public void onHvacInnerTempChanged(float f) {
            HvacManagerServer.this.sendIpc("onHvacInnerTempChanged", String.valueOf(f));
        }

        public void onHvacInnerPM25Changed(float f) {
            HvacManagerServer.this.sendIpc("onHvacInnerPM25Changed", String.valueOf(f));
        }

        public void onHvacBackMirrorHeatChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacBackMirrorHeatChanged", String.valueOf(z));
        }

        public void onHvacFrontMirrorHeatChanged(boolean z) {
            HvacManagerServer.this.sendIpc("onHvacFrontMirrorHeatChanged", String.valueOf(z));
        }

        public void onHvacSteerHeatLevelChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacSteerHeatLevelChanged", String.valueOf(i));
        }

        public void onHvacDrvSeatHeatLevelChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacDrvSeatHeatLevelChanged", String.valueOf(i));
        }

        public void onHvacDrvSeatBlowLevelChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacDrvSeatBlowLevelChanged", String.valueOf(i));
        }

        public void onHvacPsnSeatHeatLevelChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacPsnSeatHeatLevelChanged", String.valueOf(i));
        }

        public void onHvacPsnSeatBlowLevelChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacPsnSeatBlowLevelChanged", String.valueOf(i));
        }

        public void onHvacRLSeatHeatLevelChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacRLSeatHeatLevelChanged", String.valueOf(i));
        }

        public void onHvacRLSeatBlowLevelChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacRLSeatBlowLevelChanged", String.valueOf(i));
        }

        public void onHvacRRSeatHeatLevelChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacRRSeatHeatLevelChanged", String.valueOf(i));
        }

        public void onHvacRRSeatBlowLevelChanged(int i) {
            HvacManagerServer.this.sendIpc("onHvacRRSeatBlowLevelChanged", String.valueOf(i));
        }

        public void onHvacEAVDriverLeftHPos(int i) {
            HvacManagerServer.this.sendIpc("onHvacEAVDriverLeftHPos", String.valueOf(i));
        }

        public void onHvacEAVDriverLeftVPos(int i) {
            HvacManagerServer.this.sendIpc("onHvacEAVDriverLeftVPos", String.valueOf(i));
        }

        public void onHvacEAVDriverRightHPos(int i) {
            HvacManagerServer.this.sendIpc("onHvacEAVDriverRightHPos", String.valueOf(i));
        }

        public void onHvacEAVDriverRightVPos(int i) {
            HvacManagerServer.this.sendIpc("onHvacEAVDriverRightVPos", String.valueOf(i));
        }

        public void onHvacEAVPsnLeftHPos(int i) {
            HvacManagerServer.this.sendIpc("onHvacEAVPsnLeftHPos", String.valueOf(i));
        }

        public void onHvacEAVPsnLeftVPos(int i) {
            HvacManagerServer.this.sendIpc("onHvacEAVPsnLeftVPos", String.valueOf(i));
        }

        public void onHvacEAVPsnRightHPos(int i) {
            HvacManagerServer.this.sendIpc("onHvacEAVPsnRightHPos", String.valueOf(i));
        }

        public void onHvacEAVPsnRightVPos(int i) {
            HvacManagerServer.this.sendIpc("onHvacEAVPsnRightVPos", String.valueOf(i));
        }
    }
}
