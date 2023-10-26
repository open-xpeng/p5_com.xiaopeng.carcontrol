package com.xiaopeng.carcontrol.speech;

import android.content.Intent;
import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacEavWindMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacSwitchStatus;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.SeatHeatLevel;
import com.xiaopeng.carcontrol.viewmodel.hvac.SeatVentLevel;
import com.xiaopeng.speech.protocol.SpeechUtils;
import com.xiaopeng.speech.protocol.event.CaracEvent;
import com.xiaopeng.speech.protocol.event.query.QueryCarAirEvent;
import com.xiaopeng.speech.protocol.node.carac.bean.ChangeValue;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class HvacControlSpeechModel implements ISpeechModel {
    public static boolean IS_HVAC_PANEL_SHOWING = false;
    private static final int STATUS_AIR_TEMP_LEVEL_UNIT = 5;
    private static final String TAG = "HvacControlSpeechModel";
    public static final String VUI_SPEECH_KEY = "from";
    public static final String VUI_SPEECH_VALUE = "speech";
    IHvacViewModel mHvacViewModel;
    ISeatViewModel mSeatViewModel;

    private void brokenSweepWind(int pilot) {
    }

    private int seatHeatVentRule(int value) {
        if (value < 1) {
            return 1;
        }
        if (value > 3) {
            return 3;
        }
        return value;
    }

    static /* synthetic */ HvacControlSpeechModel access$000() {
        return create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        private static final HvacControlSpeechModel sInstance = HvacControlSpeechModel.access$000();

        private Holder() {
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static HvacControlSpeechModel create() {
        char c;
        String xpCduType = CarStatusUtils.getXpCduType();
        int hashCode = xpCduType.hashCode();
        if (hashCode != 2577) {
            switch (hashCode) {
                case 2560:
                    if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q1)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2561:
                    if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q2)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2562:
                    if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q3)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    switch (hashCode) {
                        case 2564:
                            if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q5)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2565:
                            if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q6)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2566:
                            if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q7)) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2567:
                            if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q8)) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2568:
                            if (xpCduType.equals("Q9")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
            }
        } else {
            if (xpCduType.equals("QB")) {
                c = '\b';
            }
            c = 65535;
        }
        if (c != 0) {
            if (c == 1 || c == 2 || c == 3) {
                return new D2HvacControlSpeechModel();
            }
            return new XpHvacControlSpeechModel();
        }
        return new HvacControlSpeechModel();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static HvacControlSpeechModel getInstance() {
        return Holder.sInstance;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public HvacControlSpeechModel() {
        ViewModelManager viewModelManager = ViewModelManager.getInstance();
        this.mHvacViewModel = (IHvacViewModel) viewModelManager.getViewModelImpl(IHvacViewModel.class);
        this.mSeatViewModel = (ISeatViewModel) viewModelManager.getViewModelImpl(ISeatViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.speech.ISpeechModel
    public void onEvent(String event, String data, int source) {
        boolean z = source != 1;
        event.hashCode();
        char c = 65535;
        switch (event.hashCode()) {
            case -2114800121:
                if (event.equals("command://ac.psn.seat.ventilate.max")) {
                    c = 0;
                    break;
                }
                break;
            case -2114799883:
                if (event.equals("command://ac.psn.seat.ventilate.min")) {
                    c = 1;
                    break;
                }
                break;
            case -2114798062:
                if (event.equals("command://ac.psn.seat.ventilate.off")) {
                    c = 2;
                    break;
                }
                break;
            case -2114794235:
                if (event.equals("command://ac.psn.seat.ventilate.set")) {
                    c = 3;
                    break;
                }
                break;
            case -2049213332:
                if (event.equals(CaracEvent.TEMP_PASSENGER_UP)) {
                    c = 4;
                    break;
                }
                break;
            case -2031004713:
                if (event.equals(CaracEvent.MODES_ECO_ON)) {
                    c = 5;
                    break;
                }
                break;
            case -1997714566:
                if (event.equals(CaracEvent.TEMP_DUAL_OFF)) {
                    c = 6;
                    break;
                }
                break;
            case -1997710125:
                if (event.equals(CaracEvent.TEMP_DUAL_SYN)) {
                    c = 7;
                    break;
                }
                break;
            case -1982458101:
                if (event.equals(CaracEvent.TEMP_DRIVER_DOWN)) {
                    c = '\b';
                    break;
                }
                break;
            case -1877455440:
                if (event.equals(CaracEvent.OPEN_FAST_FRIDGE)) {
                    c = '\t';
                    break;
                }
                break;
            case -1846450597:
                if (event.equals("command://ac.steering.heating.on")) {
                    c = '\n';
                    break;
                }
                break;
            case -1846450409:
                if (event.equals("command://ac.steering.heating.up")) {
                    c = 11;
                    break;
                }
                break;
            case -1754019960:
                if (event.equals(CaracEvent.AC_RIGHT_REAR_SEAT_HEAT_CLOSE)) {
                    c = '\f';
                    break;
                }
                break;
            case -1659193971:
                if (event.equals(CaracEvent.AC_LEFT_REAR_SEAT_HEAT_SET)) {
                    c = '\r';
                    break;
                }
                break;
            case -1616994677:
                if (event.equals("command://ac.intelligent.desiccation.off")) {
                    c = 14;
                    break;
                }
                break;
            case -1611128182:
                if (event.equals(CaracEvent.SEAT_HEATING_DOWN)) {
                    c = 15;
                    break;
                }
                break;
            case -1610800078:
                if (event.equals(CaracEvent.SEAT_HEATING_OPEN)) {
                    c = 16;
                    break;
                }
                break;
            case -1520877577:
                if (event.equals(CaracEvent.WIND_AUTO_SWEEP_OFF)) {
                    c = 17;
                    break;
                }
                break;
            case -1495695801:
                if (event.equals(CaracEvent.OPEN_AC_PANEL)) {
                    c = 18;
                    break;
                }
                break;
            case -1474634240:
                if (event.equals(CaracEvent.HEAD_WINDOW_OFF)) {
                    c = 19;
                    break;
                }
                break;
            case -1405395864:
                if (event.equals("command://ac.steering.heating.max")) {
                    c = 20;
                    break;
                }
                break;
            case -1405395626:
                if (event.equals("command://ac.steering.heating.min")) {
                    c = 21;
                    break;
                }
                break;
            case -1405393805:
                if (event.equals("command://ac.steering.heating.off")) {
                    c = 22;
                    break;
                }
                break;
            case -1405389978:
                if (event.equals("command://ac.steering.heating.set")) {
                    c = 23;
                    break;
                }
                break;
            case -1402704680:
                if (event.equals(CaracEvent.WIND_UNIDIRECTION)) {
                    c = 24;
                    break;
                }
                break;
            case -1379253628:
                if (event.equals(CaracEvent.EXIT_FAST_FRIDGE)) {
                    c = 25;
                    break;
                }
                break;
            case -1263486935:
                if (event.equals("command://back.ac.hvac.on")) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case -1179765898:
                if (event.equals(CaracEvent.OPEN_AC_INTELLIGENT_PSN_ON)) {
                    c = 27;
                    break;
                }
                break;
            case -1143304767:
                if (event.equals(CaracEvent.SEAT_HEAT_PASSENGER_UP)) {
                    c = 28;
                    break;
                }
                break;
            case -1134548897:
                if (event.equals("command://ac.psn.seat.ventilate.down")) {
                    c = 29;
                    break;
                }
                break;
            case -1125717353:
                if (event.equals(CaracEvent.OUTLET_OFF)) {
                    c = 30;
                    break;
                }
                break;
            case -1082711556:
                if (event.equals(CaracEvent.SEAT_HEAT_PASSENGER_SET)) {
                    c = 31;
                    break;
                }
                break;
            case -1067108253:
                if (event.equals(CaracEvent.AC_LEFT_REAR_SEAT_HEAT_CLOSE)) {
                    c = ' ';
                    break;
                }
                break;
            case -1064721728:
                if (event.equals(CaracEvent.HVAC_ON)) {
                    c = '!';
                    break;
                }
                break;
            case -1054854969:
                if (event.equals(CaracEvent.TEMP_MAX)) {
                    c = '\"';
                    break;
                }
                break;
            case -1054854731:
                if (event.equals(CaracEvent.TEMP_MIN)) {
                    c = '#';
                    break;
                }
                break;
            case -1054849083:
                if (event.equals(CaracEvent.TEMP_SET)) {
                    c = '$';
                    break;
                }
                break;
            case -1032797908:
                if (event.equals(CaracEvent.DEMIST_FOOT_ON)) {
                    c = '%';
                    break;
                }
                break;
            case -997211612:
                if (event.equals(CaracEvent.WIND_UP)) {
                    c = '&';
                    break;
                }
                break;
            case -970874129:
                if (event.equals(CaracEvent.PURIFIER_OPEN)) {
                    c = '\'';
                    break;
                }
                break;
            case -933998679:
                if (event.equals(CaracEvent.NATURE_ON)) {
                    c = '(';
                    break;
                }
                break;
            case -902370839:
                if (event.equals(CaracEvent.SEAT_PSN_HEAT_CLOSE)) {
                    c = ')';
                    break;
                }
                break;
            case -848796933:
                if (event.equals(CaracEvent.WIND_MAX)) {
                    c = '*';
                    break;
                }
                break;
            case -848796695:
                if (event.equals(CaracEvent.WIND_MIN)) {
                    c = '+';
                    break;
                }
                break;
            case -848791047:
                if (event.equals(CaracEvent.WIND_SET)) {
                    c = ',';
                    break;
                }
                break;
            case -794660614:
                if (event.equals(CaracEvent.INNER_OFF)) {
                    c = '-';
                    break;
                }
                break;
            case -686815348:
                if (event.equals(CaracEvent.DEMIST_FRONT_ON)) {
                    c = '.';
                    break;
                }
                break;
            case -684339037:
                if (event.equals(CaracEvent.OPEN_FRESH_AIR)) {
                    c = '/';
                    break;
                }
                break;
            case -617853410:
                if (event.equals("command://ac.steering.heating.down")) {
                    c = '0';
                    break;
                }
                break;
            case -543155733:
                if (event.equals(CaracEvent.WIND_DOWN)) {
                    c = '1';
                    break;
                }
                break;
            case -543093835:
                if (event.equals(CaracEvent.WIND_FREE)) {
                    c = '2';
                    break;
                }
                break;
            case -533834088:
                if (event.equals(CaracEvent.HEATING_ON)) {
                    c = '3';
                    break;
                }
                break;
            case -530897568:
                if (event.equals(CaracEvent.AC_OFF)) {
                    c = '4';
                    break;
                }
                break;
            case -515974873:
                if (event.equals(CaracEvent.AQS_ON)) {
                    c = '5';
                    break;
                }
                break;
            case -513389467:
                if (event.equals("command://back.ac.hvac.off")) {
                    c = '6';
                    break;
                }
                break;
            case -483861284:
                if (event.equals("command://ac.psn.seat.ventilate.on")) {
                    c = '7';
                    break;
                }
                break;
            case -483861096:
                if (event.equals("command://ac.psn.seat.ventilate.up")) {
                    c = '8';
                    break;
                }
                break;
            case -363842875:
                if (event.equals(CaracEvent.MIRROR_ON)) {
                    c = '9';
                    break;
                }
                break;
            case -352081897:
                if (event.equals("command://ac.wind.coordinate.open")) {
                    c = ':';
                    break;
                }
                break;
            case -324663506:
                if (event.equals(CaracEvent.HEAD_WINDOW_ON)) {
                    c = ';';
                    break;
                }
                break;
            case -299710995:
                if (event.equals(CaracEvent.DEMIST_REAR_OFF)) {
                    c = '<';
                    break;
                }
                break;
            case -202489381:
                if (event.equals(CaracEvent.TEMP_DRIVE_MAX)) {
                    c = '=';
                    break;
                }
                break;
            case -202489143:
                if (event.equals(CaracEvent.TEMP_DRIVE_MIN)) {
                    c = '>';
                    break;
                }
                break;
            case -202483495:
                if (event.equals(CaracEvent.TEMP_DRIVER_SET)) {
                    c = '?';
                    break;
                }
                break;
            case -43518477:
                if (event.equals(CaracEvent.PURIFIER_CLOSE)) {
                    c = '@';
                    break;
                }
                break;
            case -28747719:
                if (event.equals(CaracEvent.SEAT_PSN_HEAT_OPEN)) {
                    c = 'A';
                    break;
                }
                break;
            case 53979742:
                if (event.equals("command://ac.all.seat.heat.on")) {
                    c = 'B';
                    break;
                }
                break;
            case 54152895:
                if (event.equals(CaracEvent.SEAT_VENTILATE_ON)) {
                    c = 'C';
                    break;
                }
                break;
            case 54153083:
                if (event.equals(CaracEvent.SEAT_VENTILATE_UP)) {
                    c = 'D';
                    break;
                }
                break;
            case 67141408:
                if (event.equals(CaracEvent.BLOW_FOOT_ON)) {
                    c = 'E';
                    break;
                }
                break;
            case 85158953:
                if (event.equals("command://ac.fast.warming.off")) {
                    c = 'F';
                    break;
                }
                break;
            case 104157399:
                if (event.equals(CaracEvent.AC_LEFT_REAR_SEAT_HEAT_DOWN)) {
                    c = 'G';
                    break;
                }
                break;
            case 104485503:
                if (event.equals(CaracEvent.AC_LEFT_REAR_SEAT_HEAT_OPEN)) {
                    c = 'H';
                    break;
                }
                break;
            case 183560546:
                if (event.equals(CaracEvent.DEMIST_FRONT_OFF)) {
                    c = 'I';
                    break;
                }
                break;
            case 339726725:
                if (event.equals(CaracEvent.REAR_HEAT_ON)) {
                    c = 'J';
                    break;
                }
                break;
            case 343152168:
                if (event.equals(CaracEvent.WIND_EVAD)) {
                    c = 'K';
                    break;
                }
                break;
            case 343970304:
                if (event.equals(CaracEvent.WIND_FRONT)) {
                    c = 'L';
                    break;
                }
                break;
            case 348580471:
                if (event.equals(CaracEvent.EXIT_FRESH_AIR)) {
                    c = 'M';
                    break;
                }
                break;
            case 363678332:
                if (event.equals(CaracEvent.SEAT_HEATING_MAX)) {
                    c = 'N';
                    break;
                }
                break;
            case 363678570:
                if (event.equals(CaracEvent.SEAT_HEATING_MIN)) {
                    c = 'O';
                    break;
                }
                break;
            case 409110340:
                if (event.equals(CaracEvent.TEMP_DRIVER_UP)) {
                    c = 'P';
                    break;
                }
                break;
            case 422273879:
                if (event.equals("command://back.ac.blow.foot.on")) {
                    c = 'Q';
                    break;
                }
                break;
            case 428424718:
                if (event.equals(CaracEvent.TEMP_PSN_SYN_OFF)) {
                    c = 'R';
                    break;
                }
                break;
            case 428429159:
                if (event.equals(CaracEvent.TEMP_PSN_SYN_ON)) {
                    c = 'S';
                    break;
                }
                break;
            case 501001602:
                if (event.equals(CaracEvent.SEAT_VENTILATE_DOWN)) {
                    c = 'T';
                    break;
                }
                break;
            case 520162008:
                if (event.equals(CaracEvent.TEMP_UP)) {
                    c = 'U';
                    break;
                }
                break;
            case 579107870:
                if (event.equals(CaracEvent.WINDOW_ON)) {
                    c = 'V';
                    break;
                }
                break;
            case 607537788:
                if (event.equals(CaracEvent.SEAT_VENTILATE_DRIVER_SET)) {
                    c = 'W';
                    break;
                }
                break;
            case 631012310:
                if (event.equals(CaracEvent.HEATING_OFF)) {
                    c = 'X';
                    break;
                }
                break;
            case 667102452:
                if (event.equals(CaracEvent.INNER_ON)) {
                    c = 'Y';
                    break;
                }
                break;
            case 795243080:
                if (event.equals(CaracEvent.SEAT_HEAT_PASSENGER_DOWN)) {
                    c = 'Z';
                    break;
                }
                break;
            case 816190956:
                if (event.equals(CaracEvent.SEAT_HEAT_DRIVER_SET)) {
                    c = '[';
                    break;
                }
                break;
            case 843015811:
                if (event.equals(CaracEvent.SEAT_HEATING_UP)) {
                    c = '\\';
                    break;
                }
                break;
            case 875138608:
                if (event.equals(CaracEvent.HEAD_FOOT_ON)) {
                    c = ']';
                    break;
                }
                break;
            case 896588338:
                if (event.equals("command://ac.all.seat.ventilate.on")) {
                    c = '^';
                    break;
                }
                break;
            case 898888115:
                if (event.equals(CaracEvent.TEMP_PASSENGER_MAX)) {
                    c = '_';
                    break;
                }
                break;
            case 898888353:
                if (event.equals(CaracEvent.TEMP_PASSENGER_MIN)) {
                    c = '`';
                    break;
                }
                break;
            case 898894001:
                if (event.equals(CaracEvent.TEMP_PASSENGER_SET)) {
                    c = 'a';
                    break;
                }
                break;
            case 916309008:
                if (event.equals(CaracEvent.AC_LEFT_REAR_SEAT_HEAT_UP)) {
                    c = 'b';
                    break;
                }
                break;
            case 917670211:
                if (event.equals("command://ac.intelligent.desiccation.on")) {
                    c = 'c';
                    break;
                }
                break;
            case 940029017:
                if (event.equals("command://back.ac.blow.head.foot.on")) {
                    c = 'd';
                    break;
                }
                break;
            case 960163233:
                if (event.equals(CaracEvent.DEMIST_REAR_ON)) {
                    c = 'e';
                    break;
                }
                break;
            case 1012713970:
                if (event.equals(CaracEvent.AC_RIGHT_REAR_SEAT_HEAT_SET)) {
                    c = 'f';
                    break;
                }
                break;
            case 1110811877:
                if (event.equals(CaracEvent.NATURE_OFF)) {
                    c = 'g';
                    break;
                }
                break;
            case 1184647975:
                if (event.equals(CaracEvent.AQS_OFF)) {
                    c = 'h';
                    break;
                }
                break;
            case 1279594251:
                if (event.equals(CaracEvent.AC_RIGHT_REAR_SEAT_HEAT_UP)) {
                    c = 'i';
                    break;
                }
                break;
            case 1301962337:
                if (event.equals(CaracEvent.AUTO_ON)) {
                    c = 'j';
                    break;
                }
                break;
            case 1328924946:
                if (event.equals(CaracEvent.AC_RIGHT_REAR_SEAT_HEAT_DOWN)) {
                    c = 'k';
                    break;
                }
                break;
            case 1329253050:
                if (event.equals(CaracEvent.AC_RIGHT_REAR_SEAT_HEAT_OPEN)) {
                    c = 'l';
                    break;
                }
                break;
            case 1336412759:
                if (event.equals(CaracEvent.WIND_AUTO_SWEEP_ON)) {
                    c = 'm';
                    break;
                }
                break;
            case 1349159863:
                if (event.equals(CaracEvent.OUTLET_ON)) {
                    c = 'n';
                    break;
                }
                break;
            case 1353364654:
                if (event.equals(CaracEvent.HVAC_OFF)) {
                    c = 'o';
                    break;
                }
                break;
            case 1360839242:
                if (event.equals(CaracEvent.WIND_UNIDIRECTION_SET)) {
                    c = 'p';
                    break;
                }
                break;
            case 1368347598:
                if (event.equals(CaracEvent.AC_ON)) {
                    c = 'q';
                    break;
                }
                break;
            case 1388220389:
                if (event.equals("command://ac.fast.warming.on")) {
                    c = 'r';
                    break;
                }
                break;
            case 1463363191:
                if (event.equals(CaracEvent.MODES_ECO_OFF)) {
                    c = 's';
                    break;
                }
                break;
            case 1542451310:
                if (event.equals(CaracEvent.BLOW_HEAD_ON)) {
                    c = 't';
                    break;
                }
                break;
            case 1593613584:
                if (event.equals(CaracEvent.SEAT_HEATING_CLOSE)) {
                    c = 'u';
                    break;
                }
                break;
            case 1605772617:
                if (event.equals(CaracEvent.MIRROR_OFF)) {
                    c = 'v';
                    break;
                }
                break;
            case 1658979743:
                if (event.equals(CaracEvent.TEMP_DOWN)) {
                    c = 'w';
                    break;
                }
                break;
            case 1673371856:
                if (event.equals("command://ac.all.seat.heat.off")) {
                    c = 'x';
                    break;
                }
                break;
            case 1678737540:
                if (event.equals(CaracEvent.SEAT_VENTILATE_MAX)) {
                    c = 'y';
                    break;
                }
                break;
            case 1678737778:
                if (event.equals(CaracEvent.SEAT_VENTILATE_MIN)) {
                    c = 'z';
                    break;
                }
                break;
            case 1678739599:
                if (event.equals(CaracEvent.SEAT_VENTILATE_OFF)) {
                    c = '{';
                    break;
                }
                break;
            case 1706126637:
                if (event.equals(CaracEvent.AUTO_OFF)) {
                    c = '|';
                    break;
                }
                break;
            case 1897583781:
                if (event.equals("command://back.ac.blow.head.on")) {
                    c = '}';
                    break;
                }
                break;
            case 1941593737:
                if (event.equals(CaracEvent.REAR_HEAT_OFF)) {
                    c = '~';
                    break;
                }
                break;
            case 1959171531:
                if (event.equals("command://ac.wind.coordinate.close")) {
                    c = 127;
                    break;
                }
                break;
            case 2004070256:
                if (event.equals(CaracEvent.HEAD_WINDOW_FOOT_ON)) {
                    c = 128;
                    break;
                }
                break;
            case 2024434556:
                if (event.equals("command://ac.all.seat.ventilate.off")) {
                    c = 129;
                    break;
                }
                break;
            case 2081962680:
                if (event.equals(CaracEvent.CLOSE_AC_INTELLIGENT_PSN_OFF)) {
                    c = 130;
                    break;
                }
                break;
            case 2095473203:
                if (event.equals(CaracEvent.TEMP_PASSENGER_DOWN)) {
                    c = 131;
                    break;
                }
                break;
            case 2120908599:
                if (event.equals("command://ac.wind.fresh.air.open")) {
                    c = 132;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                onPsnSeatVentilateMax();
                return;
            case 1:
                onPsnSeatVentilateMin();
                return;
            case 2:
                onPsnSeatVentilateOff();
                return;
            case 3:
                onSeatVentilatePsnSet(ChangeValue.fromJson(data));
                return;
            case 4:
                onTempPassengerUp(ChangeValue.fromJson(data));
                return;
            case 5:
                onModesEcoOn();
                return;
            case 6:
                onTempDualOff();
                return;
            case 7:
                onTempDualSyn();
                return;
            case '\b':
                onTempDriverDown(ChangeValue.fromJson(data));
                return;
            case '\t':
                onOpenFastFridge();
                return;
            case '\n':
                onSteeringHeatingOn(z);
                return;
            case 11:
                onSteeringHeatingUp(ChangeValue.fromJson(data), z);
                return;
            case '\f':
                onRearSeatHeatingOpenClose(1, 1);
                return;
            case '\r':
                onRearSeatHeatSet(0, ChangeValue.fromJson(data));
                return;
            case 14:
                onIntelligentDesiccationOff();
                return;
            case 15:
                onSeatHeatingDown(z);
                return;
            case 16:
                onSeatHeatingOpen(z);
                return;
            case 17:
                onWindAutoSweepOff();
                return;
            case 18:
                onAcPanelOn();
                return;
            case 19:
                onHeadWindowOff();
                return;
            case 20:
                onSteeringHeatingMax(z);
                return;
            case 21:
                onSteeringHeatingMin(z);
                return;
            case 22:
                onSteeringHeatingOff(z);
                return;
            case 23:
                onSteeringHeatingSet(ChangeValue.fromJson(data), z);
                return;
            case 24:
                onWindUnidirection(getPilot(data), getDirection(data));
                return;
            case 25:
                onExitFastFridge();
                return;
            case 26:
                onRearHvacOn();
                return;
            case 27:
                openIntelligentPsn();
                return;
            case 28:
                onSeatHeatPassengerUp(ChangeValue.fromJson(data));
                return;
            case 29:
                onPsnSeatVentilateDown(ChangeValue.fromJson(data));
                return;
            case 30:
                onWindOutletOff(getPilot(data));
                return;
            case 31:
                onSeatHeatPassengerSet(ChangeValue.fromJson(data));
                return;
            case ' ':
                onRearSeatHeatingOpenClose(1, 0);
                return;
            case '!':
                onHvacOn();
                return;
            case '\"':
                onTempMax();
                return;
            case '#':
                onTempMin();
                return;
            case '$':
                onTempSet(ChangeValue.fromJson(data));
                return;
            case '%':
                onDemistFootOn();
                return;
            case '&':
                onWindUp(ChangeValue.fromJson(data));
                return;
            case '\'':
                onPurifierOpen();
                return;
            case '(':
                onNatureOn();
                return;
            case ')':
                onSeatPsnHeatingClose();
                return;
            case '*':
                onWindMax();
                return;
            case '+':
                onWindMin();
                return;
            case ',':
                onWindSet(ChangeValue.fromJson(data));
                return;
            case '-':
                onInnerOff();
                return;
            case '.':
                onDemistFrontOn();
                return;
            case '/':
                onOpenFreshAir();
                return;
            case '0':
                onSteeringHeatingDown(ChangeValue.fromJson(data), z);
                return;
            case '1':
                onWindDown(ChangeValue.fromJson(data));
                return;
            case '2':
                onWindFree(getPilot(data));
                return;
            case '3':
                onHeatingOn();
                return;
            case '4':
                onAcOff();
                return;
            case '5':
                onAqsOn();
                return;
            case '6':
                onRearHvacOff();
                return;
            case '7':
                onPsnSeatVentilateOn();
                return;
            case '8':
                onPsnSeatVentilateUp(ChangeValue.fromJson(data));
                return;
            case '9':
                onMirrorOn(getPilot(data));
                return;
            case ':':
                onWindUniCoordinateOpen(getPilot(data), getX(data), getY(data));
                return;
            case ';':
                onHeadWindowOn();
                return;
            case '<':
                onDemistRearOff();
                return;
            case '=':
                onTempDriveMax();
                return;
            case '>':
                onTempDriveMin();
                return;
            case '?':
                onTempDriverSet(ChangeValue.fromJson(data));
                return;
            case '@':
                onPurifierClose();
                return;
            case 'A':
                onSeatPsnHeatingOpen();
                return;
            case 'B':
                onAllSeatHeatOn(z);
                return;
            case 'C':
                onSeatVentilateOn(z);
                return;
            case 'D':
                onSeatVentilateUp(ChangeValue.fromJson(data), z);
                return;
            case 'E':
                onBlowFootOn();
                return;
            case 'F':
                onFastWarmingOff();
                return;
            case 'G':
                onRearSeatHeatDown(0, ChangeValue.fromJson(data));
                return;
            case 'H':
                onRearSeatHeatingOpenClose(0, 0);
                return;
            case 'I':
                onDemistFrontOff();
                return;
            case 'J':
                onRearHeatOn();
                return;
            case 'K':
                onWindEvad(getPilot(data));
                return;
            case 'L':
                onWindFront(getPilot(data));
                return;
            case 'M':
                onExitFreshAir();
                return;
            case 'N':
                onSeatHeatingMax(z);
                return;
            case 'O':
                onSeatHeatingMin(z);
                return;
            case 'P':
                onTempDriverUp(ChangeValue.fromJson(data));
                return;
            case 'Q':
                onRearBlowFootOn();
                return;
            case 'R':
                onPsnTempSynOff();
                return;
            case 'S':
                onPsnTempSynOn();
                return;
            case 'T':
                onSeatVentilateDown(z);
                return;
            case 'U':
                onTempUp(ChangeValue.fromJson(data));
                return;
            case 'V':
                onWindowOn();
                return;
            case 'W':
                onSeatVentilateDriverSet(ChangeValue.fromJson(data), z);
                return;
            case 'X':
                onHeatingOff();
                return;
            case 'Y':
                onInnerOn();
                return;
            case 'Z':
                onSeatHeatPassengerDown(ChangeValue.fromJson(data));
                return;
            case '[':
                onSeatHeatDriverSet(ChangeValue.fromJson(data), z);
                return;
            case '\\':
                onSeatHeatingUp(ChangeValue.fromJson(data), z);
                return;
            case ']':
                onHeadFootOn();
                return;
            case '^':
                onAllSeatVentilateOn(z);
                return;
            case '_':
                onTempPassengerMax();
                return;
            case '`':
                onTempPassengerMin();
                return;
            case 'a':
                onTempPassengerSet(ChangeValue.fromJson(data));
                return;
            case 'b':
                onRearSeatHeatUp(0, ChangeValue.fromJson(data));
                return;
            case 'c':
                onIntelligentDesiccationOn();
                return;
            case 'd':
                onRearBlowHeadFootOn();
                return;
            case 'e':
                onDemistRearOn();
                return;
            case 'f':
                onRearSeatHeatSet(1, ChangeValue.fromJson(data));
                return;
            case 'g':
                onNatureOff();
                return;
            case 'h':
                onAqsOff();
                return;
            case 'i':
                onRearSeatHeatUp(1, ChangeValue.fromJson(data));
                return;
            case 'j':
                onAutoOn();
                return;
            case 'k':
                onRearSeatHeatDown(1, ChangeValue.fromJson(data));
                return;
            case 'l':
                onRearSeatHeatingOpenClose(0, 1);
                return;
            case 'm':
                onWindAutoSweepOn();
                return;
            case 'n':
                this.mHvacViewModel.openHvacWindModeFace();
                onWindOutletOn(getPilot(data));
                return;
            case 'o':
                onHvacOff();
                return;
            case 'p':
                onWindUnidirectionSet(getPilot(data), getDirection(data));
                return;
            case 'q':
                onAcOn();
                return;
            case 'r':
                onFastWarmingOn();
                return;
            case 's':
                onModesEcoOff();
                return;
            case 't':
                onBlowHeadOn();
                return;
            case 'u':
                onSeatHeatingClose(z);
                return;
            case 'v':
                onMirrorOff(getPilot(data));
                return;
            case 'w':
                onTempDown(ChangeValue.fromJson(data));
                return;
            case 'x':
                onAllSeatHeatOff(z);
                return;
            case 'y':
                onSeatVentilateMax(z);
                return;
            case 'z':
                onSeatVentilateMin(z);
                return;
            case '{':
                onSeatVentilateOff(z);
                return;
            case '|':
                onAutoOff();
                return;
            case '}':
                onRearBlowHeadOn();
                return;
            case '~':
                onRearHeatOff();
                return;
            case 127:
                onWindUniCoordinateClose(getPilot(data), getX(data), getY(data));
                return;
            case 128:
                onHeadWindowFootOn();
                return;
            case 129:
                onAllSeatVentilateOff(z);
                return;
            case 130:
                closeIntelligentPsn();
                return;
            case TarConstants.PREFIXLEN_XSTAR /* 131 */:
                onTempPassengerDown(ChangeValue.fromJson(data));
                return;
            case 132:
                onWindFreshAirOpen();
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.speech.ISpeechModel
    public void onQuery(String event, String data, String callback) {
        event.hashCode();
        char c = 65535;
        switch (event.hashCode()) {
            case -2107526581:
                if (event.equals(QueryCarAirEvent.IS_SUPPORT_MP_QUERY)) {
                    c = 0;
                    break;
                }
                break;
            case -1859599012:
                if (event.equals(QueryCarAirEvent.OUTSIDE_MP_LEVEL_QUERY)) {
                    c = 1;
                    break;
                }
                break;
            case -1766598080:
                if (event.equals("ac.all.seat.heat.status")) {
                    c = 2;
                    break;
                }
                break;
            case -1730121586:
                if (event.equals(QueryCarAirEvent.GET_AC_LEFT_REAR_SEAT_HEAT_LV)) {
                    c = 3;
                    break;
                }
                break;
            case -1412747795:
                if (event.equals("ac.support.psn.seat.blow")) {
                    c = 4;
                    break;
                }
                break;
            case -1412576213:
                if (event.equals(QueryCarAirEvent.IS_SUPPORT_PSN_SEAT_HEAT)) {
                    c = 5;
                    break;
                }
                break;
            case -1303288855:
                if (event.equals(QueryCarAirEvent.IS_SUPPORT_DECIMAL_VALUE)) {
                    c = 6;
                    break;
                }
                break;
            case -1296863527:
                if (event.equals(QueryCarAirEvent.GET_WINDS_STATUS)) {
                    c = 7;
                    break;
                }
                break;
            case -1288077686:
                if (event.equals(QueryCarAirEvent.AC_PANEL_STATUS)) {
                    c = '\b';
                    break;
                }
                break;
            case -1281041456:
                if (event.equals(QueryCarAirEvent.IS_SUPPORT_SEAT_BLOW)) {
                    c = '\t';
                    break;
                }
                break;
            case -1280869874:
                if (event.equals(QueryCarAirEvent.IS_SUPPORT_SEAT_HEAT)) {
                    c = '\n';
                    break;
                }
                break;
            case -1259508010:
                if (event.equals("ac.all.seat.ventilate.status")) {
                    c = 11;
                    break;
                }
                break;
            case -1142835157:
                if (event.equals(QueryCarAirEvent.IS_SUPPORT_TEMP_DUAL)) {
                    c = '\f';
                    break;
                }
                break;
            case -913109034:
                if (event.equals("ac.is.in.intelligent.desiccation")) {
                    c = '\r';
                    break;
                }
                break;
            case -884720036:
                if (event.equals("ac.is.in.fast.warming")) {
                    c = 14;
                    break;
                }
                break;
            case -882663067:
                if (event.equals(QueryCarAirEvent.GET_AIR_LEVEL)) {
                    c = 15;
                    break;
                }
                break;
            case -538746711:
                if (event.equals(QueryCarAirEvent.GET_AC_INTELLIGENT_PASSENGER)) {
                    c = 16;
                    break;
                }
                break;
            case -510636961:
                if (event.equals(QueryCarAirEvent.IS_FAST_FRIDGE)) {
                    c = 17;
                    break;
                }
                break;
            case -508546549:
                if (event.equals(QueryCarAirEvent.IS_SUPPORT_PM25)) {
                    c = 18;
                    break;
                }
                break;
            case -479761207:
                if (event.equals("ac.outside.temp")) {
                    c = 19;
                    break;
                }
                break;
            case -366701654:
                if (event.equals("ac.get.psn.seat.blow.max")) {
                    c = 20;
                    break;
                }
                break;
            case -366701416:
                if (event.equals("ac.get.psn.seat.blow.min")) {
                    c = 21;
                    break;
                }
                break;
            case -153961649:
                if (event.equals("ac.get.steering.heating.max")) {
                    c = 22;
                    break;
                }
                break;
            case -153961411:
                if (event.equals("ac.get.steering.heating.min")) {
                    c = 23;
                    break;
                }
                break;
            case -746436:
                if (event.equals("ac.support.intelligent.desiccation")) {
                    c = 24;
                    break;
                }
                break;
            case 30284012:
                if (event.equals("ac.psn.seat.blow.lv")) {
                    c = 25;
                    break;
                }
                break;
            case 112869676:
                if (event.equals("ac.support.temp.dual.off")) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case 173810349:
                if (event.equals(QueryCarAirEvent.AC_IS_SUPPORT_PERFUME)) {
                    c = 27;
                    break;
                }
                break;
            case 389659321:
                if (event.equals(QueryCarAirEvent.DRI_WIND_DIRECTION_MODE)) {
                    c = 28;
                    break;
                }
                break;
            case 396260790:
                if (event.equals("ac.support.fast.warming")) {
                    c = 29;
                    break;
                }
                break;
            case 433798074:
                if (event.equals(QueryCarAirEvent.AC_SUPPORT_REAR_SEAT_HEAT)) {
                    c = 30;
                    break;
                }
                break;
            case 487552707:
                if (event.equals("back.ac.wind.outlet.mode")) {
                    c = 31;
                    break;
                }
                break;
            case 500789806:
                if (event.equals(QueryCarAirEvent.GET_TEMP_MAX)) {
                    c = ' ';
                    break;
                }
                break;
            case 500790044:
                if (event.equals(QueryCarAirEvent.GET_TEMP_MIN)) {
                    c = '!';
                    break;
                }
                break;
            case 563037458:
                if (event.equals("back.ac.wind.outlet.status")) {
                    c = '\"';
                    break;
                }
                break;
            case 656839565:
                if (event.equals(QueryCarAirEvent.GET_SEAT_BLOW_MAX)) {
                    c = '#';
                    break;
                }
                break;
            case 656839803:
                if (event.equals(QueryCarAirEvent.GET_SEAT_BLOW_MIN)) {
                    c = '$';
                    break;
                }
                break;
            case 706847842:
                if (event.equals(QueryCarAirEvent.GET_WIND_MAX)) {
                    c = '%';
                    break;
                }
                break;
            case 706848080:
                if (event.equals(QueryCarAirEvent.GET_WIND_MIN)) {
                    c = '&';
                    break;
                }
                break;
            case 722046538:
                if (event.equals(QueryCarAirEvent.GET_HEAT_MAX)) {
                    c = '\'';
                    break;
                }
                break;
            case 722046776:
                if (event.equals(QueryCarAirEvent.GET_HEAT_MIN)) {
                    c = '(';
                    break;
                }
                break;
            case 783003410:
                if (event.equals(QueryCarAirEvent.IS_FRESH_AIR)) {
                    c = ')';
                    break;
                }
                break;
            case 844632651:
                if (event.equals(QueryCarAirEvent.GET_AC_RIGHT_REAR_SEAT_HEAT_LV)) {
                    c = '*';
                    break;
                }
                break;
            case 1338650683:
                if (event.equals(QueryCarAirEvent.IS_SUPPORT_AUTO_OFF)) {
                    c = '+';
                    break;
                }
                break;
            case 1386894569:
                if (event.equals(QueryCarAirEvent.IS_SUPPORT_MIRROR_HEAT)) {
                    c = ',';
                    break;
                }
                break;
            case 1399563517:
                if (event.equals("ac.wind.mode")) {
                    c = '-';
                    break;
                }
                break;
            case 1460629481:
                if (event.equals(QueryCarAirEvent.PSN_WIND_DIRECTION_MODE)) {
                    c = '.';
                    break;
                }
                break;
            case 1639524806:
                if (event.equals(QueryCarAirEvent.OUTSIDE_MP_QUERY)) {
                    c = '/';
                    break;
                }
                break;
            case 1649865015:
                if (event.equals(QueryCarAirEvent.IS_SUPPORT_PURIFIER)) {
                    c = '0';
                    break;
                }
                break;
            case 1796148799:
                if (event.equals("ac.get.steering.heating.lv")) {
                    c = '1';
                    break;
                }
                break;
            case 2045864960:
                if (event.equals("ac.support.steering.heating")) {
                    c = '2';
                    break;
                }
                break;
            case 2119585779:
                if (event.equals(QueryCarAirEvent.IS_SUPPORT_DEMIST_FOOT)) {
                    c = '3';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                reply(event, callback, Integer.valueOf(isSupportOutSidePm()));
                return;
            case 1:
                reply(event, callback, Integer.valueOf(getOutSidePmLevelQuality()));
                return;
            case 2:
                reply(event, callback, Integer.valueOf(getAllSeatHeatStatus()));
                return;
            case 3:
                reply(event, callback, Integer.valueOf(getLeftRearSeatHeatLevel()));
                return;
            case 4:
                reply(event, callback, Boolean.valueOf(isSupportPsnSeatBlow()));
                return;
            case 5:
                reply(event, callback, Boolean.valueOf(isSupportPsnSeatHeat()));
                return;
            case 6:
                reply(event, callback, Boolean.valueOf(isSupportDecimalValue()));
                return;
            case 7:
                reply(event, callback, getWindsStatus());
                return;
            case '\b':
                reply(event, callback, Integer.valueOf(getAcPanelStatus()));
                return;
            case '\t':
                reply(event, callback, Boolean.valueOf(isSupportSeatBlow()));
                return;
            case '\n':
                reply(event, callback, Boolean.valueOf(isSupportSeatHeat()));
                return;
            case 11:
                reply(event, callback, Integer.valueOf(getAllSeatVentilateStatus()));
                return;
            case '\f':
                reply(event, callback, Boolean.valueOf(isSupportTempDual()));
                return;
            case '\r':
                reply(event, callback, Boolean.valueOf(isInIntelligentDesiccation()));
                return;
            case 14:
                reply(event, callback, Boolean.valueOf(isFastHeatEnable()));
                return;
            case 15:
                reply(event, callback, Integer.valueOf(getAirLevel()));
                return;
            case 16:
                reply(event, callback, Integer.valueOf(getIntelligentPassengerStatus()));
                return;
            case 17:
                reply(event, callback, Boolean.valueOf(isFastFridge()));
                return;
            case 18:
                reply(event, callback, Boolean.valueOf(isSupportPm25()));
                return;
            case 19:
                reply(event, callback, Float.valueOf(getOutsideTemp()));
                return;
            case 20:
                reply(event, callback, Integer.valueOf(getPsnSeatBlowMax()));
                return;
            case 21:
                reply(event, callback, Integer.valueOf(getPsnSeatBlowMin()));
                return;
            case 22:
                reply(event, callback, Integer.valueOf(getSteeringHeatingMax()));
                return;
            case 23:
                reply(event, callback, Integer.valueOf(getSteeringHeatingMin()));
                return;
            case 24:
                reply(event, callback, Boolean.valueOf(isSupportIntelligentDesiccation()));
                return;
            case 25:
                reply(event, callback, Integer.valueOf(getPsnSeatBlowLevel()));
                return;
            case 26:
                reply(event, callback, Integer.valueOf(getTempDualMode()));
                return;
            case 27:
                reply(event, callback, Boolean.valueOf(isAcSupportPerfume()));
                return;
            case 28:
                reply(event, callback, Integer.valueOf(getDriWindDirectionMode()));
                return;
            case 29:
                reply(event, callback, Boolean.valueOf(isSupportFastHeat()));
                return;
            case 30:
                reply(event, callback, Boolean.valueOf(isSupportRearSeatHeat()));
                return;
            case 31:
                reply(event, callback, Integer.valueOf(getRearWindBlowMode()));
                return;
            case ' ':
                reply(event, callback, Double.valueOf(getTempMax()));
                return;
            case '!':
                reply(event, callback, Double.valueOf(getTempMin()));
                return;
            case '\"':
                reply(event, callback, Integer.valueOf(getRearPowerStatus()));
                return;
            case '#':
                reply(event, callback, Integer.valueOf(getSeatBlowMax()));
                return;
            case '$':
                reply(event, callback, Integer.valueOf(getSeatBlowMin()));
                return;
            case '%':
                reply(event, callback, Integer.valueOf(getWindMax()));
                return;
            case '&':
                reply(event, callback, Integer.valueOf(getWindMin()));
                return;
            case '\'':
                reply(event, callback, Integer.valueOf(getHeatMax()));
                return;
            case '(':
                reply(event, callback, Integer.valueOf(getHeatMin()));
                return;
            case ')':
                reply(event, callback, Boolean.valueOf(isFreshAir()));
                return;
            case '*':
                reply(event, callback, Integer.valueOf(getRightRearSeatHeatLevel()));
                return;
            case '+':
                reply(event, callback, Boolean.valueOf(isSupportAutoOff()));
                return;
            case ',':
                reply(event, callback, Integer.valueOf(isSupportMirrorHeat()));
                return;
            case '-':
                reply(event, callback, Integer.valueOf(getCurrWindMode()));
                return;
            case '.':
                reply(event, callback, Integer.valueOf(getPsnWindDirectionMode()));
                return;
            case '/':
                reply(event, callback, Integer.valueOf(getOutSidePmQuality()));
                return;
            case '0':
                reply(event, callback, Boolean.valueOf(isSupportPurifier()));
                return;
            case '1':
                reply(event, callback, Integer.valueOf(getSteeringHeatingLevel()));
                return;
            case '2':
                reply(event, callback, Boolean.valueOf(isSupportSteeringHeating()));
                return;
            case '3':
                reply(event, callback, Boolean.valueOf(isSupportDemistFoot()));
                return;
            default:
                return;
        }
    }

    protected void onHvacOff() {
        LogUtils.d(TAG, "onHvacOff", false);
        if (this.mHvacViewModel.isHvacPowerModeOn()) {
            this.mHvacViewModel.setHvacPowerMode(false);
        }
    }

    protected void onHvacOn() {
        LogUtils.d(TAG, "onHvacOn", false);
        if (this.mHvacViewModel.isHvacPowerModeOn()) {
            return;
        }
        this.mHvacViewModel.setHvacPowerMode(true);
    }

    protected void onAcOn() {
        LogUtils.d(TAG, "onAcOn", false);
        if (this.mHvacViewModel.isHvacAcModeOn()) {
            return;
        }
        this.mHvacViewModel.setHvacAcMode(true);
    }

    protected void onAcOff() {
        LogUtils.d(TAG, "onAcOff", false);
        if (this.mHvacViewModel.isHvacAcModeOn()) {
            this.mHvacViewModel.setHvacAcMode(false);
        }
    }

    protected void onAutoOn() {
        LogUtils.d(TAG, "onAutoOn", false);
        if (this.mHvacViewModel.isHvacAutoModeOn()) {
            return;
        }
        this.mHvacViewModel.setHvacAutoMode(true);
    }

    protected void onAutoOffSupport() {
        LogUtils.d(TAG, "onAutoOffSupport", false);
        SpeechUtils.replySupport(CaracEvent.AUTO_OFF, true);
    }

    protected void onAutoOff() {
        LogUtils.d(TAG, "onAutoOff", false);
        if (this.mHvacViewModel.isHvacAutoModeOn()) {
            if (CarBaseConfig.getInstance().isSupportCloseAutoDirect()) {
                this.mHvacViewModel.setHvacAutoMode(false);
            } else {
                this.mHvacViewModel.setAcHeatNatureMode(3);
            }
        }
    }

    protected void onBlowFootOn() {
        LogUtils.d(TAG, "onBlowFootOn", false);
        this.mHvacViewModel.setHvacWindBlowFoot();
    }

    protected void onHeadFootOn() {
        LogUtils.d(TAG, "onHeadFootOn", false);
        this.mHvacViewModel.setHvacWindBlowFaceFoot();
    }

    protected void onBlowHeadOn() {
        LogUtils.d(TAG, "onBlowHeadOn", false);
        this.mHvacViewModel.setHvacWindBlowFace();
    }

    protected void onWindowOn() {
        LogUtils.d(TAG, "onWindowOn", false);
        this.mHvacViewModel.setHvacWindBlowWindow();
    }

    protected int getCurrWindMode() {
        LogUtils.d(TAG, "getCurrWindMode", false);
        return this.mHvacViewModel.getHvacWindBlowMode();
    }

    protected void onDemistFrontOff() {
        LogUtils.d(TAG, "onDemistFrontOff", false);
        if (this.mHvacViewModel.isHvacPowerModeOn() && this.mHvacViewModel.isHvacFrontDefrostOn()) {
            this.mHvacViewModel.setHvacFrontDefrost(false);
        }
    }

    protected void onDemistFrontOn() {
        LogUtils.d(TAG, "onDemistFrontOn", false);
        if (this.mHvacViewModel.isHvacPowerModeOn() && this.mHvacViewModel.isHvacFrontDefrostOn()) {
            return;
        }
        this.mHvacViewModel.setHvacFrontDefrost(true);
    }

    protected void onDemistRearOff() {
        LogUtils.d(TAG, "onDemistRearOff", false);
        if (this.mHvacViewModel.isMirrorHeatEnabled()) {
            this.mHvacViewModel.setMirrorHeatEnable(false);
        }
    }

    protected void onDemistRearOn() {
        LogUtils.d(TAG, "onDemistRearOn", false);
        if (this.mHvacViewModel.isMirrorHeatEnabled()) {
            return;
        }
        this.mHvacViewModel.setMirrorHeatEnable(true);
    }

    protected void onDemistFootOnSupport() {
        LogUtils.d(TAG, "onDemistFootOnSupport", false);
    }

    protected void onDemistFootOn() {
        LogUtils.d(TAG, "onDemistFootOn", false);
        this.mHvacViewModel.setHvacWindBlowWinFoot();
    }

    protected void onInnerOff() {
        LogUtils.d(TAG, "onInnerOff", false);
        if (CarBaseConfig.getInstance().isSupportAqs() && this.mHvacViewModel.getHvacCirculationMode() == 2 && this.mHvacViewModel.getHvacAqsMode() == 1) {
            this.mHvacViewModel.setHvacAqsMode(0);
        } else {
            this.mHvacViewModel.setHvacCirculationOut();
        }
    }

    protected void onInnerOn() {
        LogUtils.d(TAG, "onInnerOn", false);
        if (CarBaseConfig.getInstance().isSupportAqs() && this.mHvacViewModel.getHvacCirculationMode() == 1 && this.mHvacViewModel.getHvacAqsMode() == 1) {
            this.mHvacViewModel.setHvacAqsMode(0);
        } else {
            this.mHvacViewModel.setHvacCirculationInner();
        }
    }

    protected void onWindDown(ChangeValue changeValue) {
        LogUtils.d(TAG, "onWindDown:" + changeValue.getValue(), false);
        int hvacWindSpeedLevel = this.mHvacViewModel.getHvacWindSpeedLevel();
        if (hvacWindSpeedLevel == 14) {
            this.mHvacViewModel.setHvacWindSpeedDown();
        } else {
            this.mHvacViewModel.setHvacWindSpeedLevel(hvacWindSpeedLevel - changeValue.getValue());
        }
    }

    protected void onWindUp(ChangeValue changeValue) {
        LogUtils.d(TAG, "onWindUp:" + changeValue.getValue(), false);
        int hvacWindSpeedLevel = this.mHvacViewModel.getHvacWindSpeedLevel();
        if (hvacWindSpeedLevel == 14) {
            this.mHvacViewModel.setHvacWindSpeedUp();
        } else {
            this.mHvacViewModel.setHvacWindSpeedLevel(hvacWindSpeedLevel + changeValue.getValue());
        }
    }

    protected void onWindSet(ChangeValue changeValue) {
        LogUtils.d(TAG, "onWindSet:" + changeValue.getValue(), false);
        this.mHvacViewModel.setHvacWindSpeedLevel(changeValue.getValue());
    }

    protected void onRearHeatOff() {
        LogUtils.d(TAG, "onRearHeatOff", false);
        if (this.mHvacViewModel.isMirrorHeatEnabled()) {
            this.mHvacViewModel.setMirrorHeatEnable(false);
        }
    }

    protected void onRearHeatOffSupport() {
        LogUtils.d(TAG, "onRearHeatOffSupport", false);
        SpeechUtils.replySupport(CaracEvent.REAR_HEAT_OFF, true);
    }

    protected void onRearHeatOn() {
        LogUtils.d(TAG, "onRearHeatOn", false);
        if (this.mHvacViewModel.isMirrorHeatEnabled()) {
            return;
        }
        this.mHvacViewModel.setMirrorHeatEnable(true);
    }

    protected void onRearHeatOnSupport() {
        LogUtils.d(TAG, "onRearHeatOnSupport", false);
    }

    protected void onTempDownHalfSupport() {
        LogUtils.d(TAG, "onTempDownHalfSupport", false);
    }

    protected void onTempDown(ChangeValue changeValue) {
        LogUtils.d(TAG, "onTempDown:" + changeValue.getValue(), false);
        onTempDriverDown(changeValue);
    }

    protected void onTempUp(ChangeValue changeValue) {
        LogUtils.d(TAG, "onTempUp:" + changeValue.getValue(), false);
        onTempDriverUp(changeValue);
    }

    protected void onTempSet(ChangeValue changeValue) {
        LogUtils.d(TAG, "onTempSet:" + changeValue.getValue(), false);
        onTempDriverSet(changeValue);
        onTempPassengerSet(changeValue);
    }

    protected void onTempUpHalfSupport() {
        LogUtils.d(TAG, "onTempUpHalfSupport", false);
    }

    protected void onPurifierOpenSupport() {
        LogUtils.d(TAG, "onPurifierOpenSupport", false);
    }

    protected void onPurifierOpen() {
        LogUtils.d(TAG, "onPurifierOpen", false);
        if (this.mHvacViewModel.isHvacQualityPurgeEnable()) {
            return;
        }
        this.mHvacViewModel.setHvacQualityPurgeMode(true);
    }

    protected void onPurifierCloseSupport() {
        LogUtils.d(TAG, "onPurifierCloseSupport", false);
    }

    protected void onPurifierClose() {
        LogUtils.d(TAG, "onPurifierClose", false);
        if (this.mHvacViewModel.isHvacQualityPurgeEnable()) {
            this.mHvacViewModel.setHvacQualityPurgeMode(false);
        }
    }

    protected void onPurifierPm25() {
        LogUtils.d(TAG, "onPurifierPm25", false);
    }

    protected void onTempDriverUpSupport() {
        LogUtils.d(TAG, "onTempDriverUpSupport", false);
    }

    protected void onTempDriverUp(ChangeValue changeValue) {
        LogUtils.d(TAG, "onTempDriverUp " + changeValue.getValue(), false);
        tempWithAutoOrAc();
        float hvacDriverTemp = this.mHvacViewModel.getHvacDriverTemp() + changeValue.getValue();
        if (changeValue.getDecimal() == 5) {
            hvacDriverTemp += 0.5f;
        }
        this.mHvacViewModel.setHvacTempDriver(hvacDriverTemp);
    }

    protected void onTempDriverDownSupport() {
        LogUtils.d(TAG, "onTempDriverDownSupport", false);
    }

    protected void onTempDriverDown(ChangeValue changeValue) {
        LogUtils.d(TAG, "onTempDriverDown " + changeValue.getValue(), false);
        tempWithAutoOrAc();
        float hvacDriverTemp = this.mHvacViewModel.getHvacDriverTemp() - changeValue.getValue();
        if (changeValue.getDecimal() == 5) {
            hvacDriverTemp -= 0.5f;
        }
        this.mHvacViewModel.setHvacTempDriver(hvacDriverTemp);
    }

    protected void onTempDriverSet(ChangeValue changeValue) {
        LogUtils.d(TAG, "onTempDriverSet " + changeValue.getValue(), false);
        tempWithAutoOrAc();
        float value = changeValue.getValue();
        if (changeValue.getDecimal() == 5) {
            value += 0.5f;
        }
        this.mHvacViewModel.setHvacTempDriver(value);
    }

    protected void onTempPassengerUpSupport() {
        LogUtils.d(TAG, "onTempPassengerUpSupport", false);
    }

    protected void onTempPassengerUp(ChangeValue changeValue) {
        LogUtils.d(TAG, "onTempPassengerUp " + changeValue.getValue(), false);
        tempWithAutoOrAc();
        float hvacPsnTemp = this.mHvacViewModel.getHvacPsnTemp() + changeValue.getValue();
        if (changeValue.getDecimal() == 5) {
            hvacPsnTemp += 0.5f;
        }
        this.mHvacViewModel.setHvacTempPsn(hvacPsnTemp);
    }

    protected void onTempPassengerDownSupport() {
        LogUtils.d(TAG, "onTempPassengerDownSupport", false);
    }

    protected void onTempPassengerDown(ChangeValue changeValue) {
        LogUtils.d(TAG, "onTempPassengerDown " + changeValue.getValue(), false);
        tempWithAutoOrAc();
        float hvacPsnTemp = this.mHvacViewModel.getHvacPsnTemp() - changeValue.getValue();
        if (changeValue.getDecimal() == 5) {
            hvacPsnTemp -= 0.5f;
        }
        this.mHvacViewModel.setHvacTempPsn(hvacPsnTemp);
    }

    protected void onTempPassengerSet(ChangeValue changeValue) {
        LogUtils.d(TAG, "onTempPassengerSet " + changeValue.getValue(), false);
        tempWithAutoOrAc();
        float value = changeValue.getValue();
        if (changeValue.getDecimal() == 5) {
            value += 0.5f;
        }
        this.mHvacViewModel.setHvacTempPsn(value);
    }

    protected void onTempDualSyn() {
        LogUtils.d(TAG, "onTempDualSyn", false);
        if (this.mHvacViewModel.isHvacDriverSyncMode()) {
            return;
        }
        this.mHvacViewModel.setHvacDriverSyncMode(true);
    }

    protected void onTempDualOff() {
        LogUtils.d(TAG, "onTempDualOff", false);
        if (this.mHvacViewModel.isHvacDriverSyncMode()) {
            this.mHvacViewModel.setHvacDriverSyncMode(false);
        }
    }

    protected void onPsnTempSynOn() {
        LogUtils.d(TAG, "onPsnTempSynOn", false);
    }

    protected void onPsnTempSynOff() {
        LogUtils.d(TAG, "onPsnTempSynOff", false);
    }

    protected void onDataTempTTS() {
        LogUtils.d(TAG, "onDataTempTTS", false);
    }

    protected void onDataWindTTS() {
        LogUtils.d(TAG, "onDataWindTTS", false);
    }

    protected void onWindMax() {
        LogUtils.d(TAG, "onWindMax", false);
        this.mHvacViewModel.setHvacWindSpeedMax();
    }

    protected void onWindMin() {
        LogUtils.d(TAG, "onWindMin", false);
        this.mHvacViewModel.setHvacWindSpeedMin();
    }

    protected void onTempMin() {
        LogUtils.d(TAG, "onTempMin", false);
        onTempDriveMin();
    }

    protected void onTempMax() {
        LogUtils.d(TAG, "onTempMax", false);
        onTempDriveMax();
    }

    protected void onTempDriveMin() {
        LogUtils.d(TAG, "onTempDriveMin", false);
        tempWithAutoOrAc();
        this.mHvacViewModel.setHvacTempDriver(18.0f);
    }

    protected void onTempDriveMax() {
        LogUtils.d(TAG, "onTempDriveMax", false);
        tempWithAutoOrAc();
        this.mHvacViewModel.setHvacTempDriver(32.0f);
    }

    protected void onTempPassengerMin() {
        LogUtils.d(TAG, "onTempPassengerMin", false);
        tempWithAutoOrAc();
        this.mHvacViewModel.setHvacTempPsn(18.0f);
    }

    protected void onTempPassengerMax() {
        LogUtils.d(TAG, "onTempPassengerMax", false);
        tempWithAutoOrAc();
        this.mHvacViewModel.setHvacTempPsn(32.0f);
    }

    protected void tempWithAutoOrAc() {
        LogUtils.d(TAG, "tempWithAutoOrAc", false);
        if (CarBaseConfig.getInstance().isSelfDevelopedHvac()) {
            ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.speech.-$$Lambda$HvacControlSpeechModel$jx2w2u6EoyabApvsv8d0I9fjqa0
                @Override // java.lang.Runnable
                public final void run() {
                    HvacControlSpeechModel.this.lambda$tempWithAutoOrAc$0$HvacControlSpeechModel();
                }
            }, 200L);
        } else if (this.mHvacViewModel.getAcHeatNatureMode() == 5 || this.mHvacViewModel.getAcHeatNatureMode() == 4) {
            this.mHvacViewModel.setHvacAutoMode(true);
        }
    }

    public /* synthetic */ void lambda$tempWithAutoOrAc$0$HvacControlSpeechModel() {
        if (this.mHvacViewModel.isHvacAcModeOn()) {
            return;
        }
        this.mHvacViewModel.setHvacAcMode(true);
    }

    protected void onSeatHeatingOpen(boolean storeEnable) {
        LogUtils.d(TAG, "onSeatHeatingOpen", false);
        if (SeatHeatLevel.Off == SeatHeatLevel.fromBcmState(this.mHvacViewModel.getSeatHeatLevel())) {
            this.mHvacViewModel.setSeatHeatLevel(SeatHeatLevel.Level3.ordinal(), storeEnable);
        }
    }

    protected void onSeatHeatingClose(boolean storeEnable) {
        LogUtils.d(TAG, "onSeatHeatingClose", false);
        if (SeatHeatLevel.Off != SeatHeatLevel.fromBcmState(this.mHvacViewModel.getSeatHeatLevel())) {
            this.mHvacViewModel.setSeatHeatLevel(SeatHeatLevel.Off.ordinal(), storeEnable);
        }
    }

    protected void onAllSeatHeatOn(boolean storeEnable) {
        LogUtils.d(TAG, "onAllSeatHeatOn", false);
        if (isSupportSeatHeat()) {
            onSeatHeatingOpen(storeEnable);
        }
        if (isSupportPsnSeatHeat()) {
            onSeatPsnHeatingOpen();
        }
        if (isSupportRearSeatHeat()) {
            onRearSeatHeatingOpenClose(0, 0);
            onRearSeatHeatingOpenClose(0, 1);
        }
    }

    protected void onAllSeatHeatOff(boolean storeEnable) {
        LogUtils.d(TAG, "onAllSeatHeatOff", false);
        if (isSupportSeatHeat()) {
            onSeatHeatingClose(storeEnable);
        }
        if (isSupportPsnSeatHeat()) {
            onSeatPsnHeatingClose();
        }
        if (isSupportRearSeatHeat()) {
            onRearSeatHeatingOpenClose(1, 0);
            onRearSeatHeatingOpenClose(1, 1);
        }
    }

    protected void onSeatPsnHeatingOpen() {
        LogUtils.d(TAG, "onSeatPsnHeatingOpen", false);
        if (SeatHeatLevel.Off == SeatHeatLevel.fromBcmState(this.mHvacViewModel.getPsnSeatHeatLevel())) {
            this.mHvacViewModel.setPsnSeatHeatLevel(SeatHeatLevel.Level3.ordinal());
        }
    }

    protected void onSeatPsnHeatingClose() {
        LogUtils.d(TAG, "onSeatPsnHeatingClose", false);
        if (SeatHeatLevel.Off != SeatHeatLevel.fromBcmState(this.mHvacViewModel.getPsnSeatHeatLevel())) {
            this.mHvacViewModel.setPsnSeatHeatLevel(SeatHeatLevel.Off.ordinal());
        }
    }

    protected void onSeatHeatingMax(boolean storeEnable) {
        LogUtils.d(TAG, "onSeatHeatingMax", false);
        this.mHvacViewModel.setSeatHeatLevel(SeatHeatLevel.Level3.ordinal(), storeEnable);
    }

    protected void onSeatHeatingMin(boolean storeEnable) {
        LogUtils.d(TAG, "onSeatHeatingMin", false);
        this.mHvacViewModel.setSeatHeatLevel(SeatHeatLevel.Level1.ordinal(), storeEnable);
    }

    protected void onSeatHeatingUp(ChangeValue changeValue, boolean storeEnable) {
        LogUtils.d(TAG, "onSeatHeatingUp value=" + changeValue.getValue(), false);
        onSeatHeatDriverSet(new ChangeValue().setValue(this.mHvacViewModel.getSeatHeatLevel() + changeValue.getValue()), storeEnable);
    }

    protected void onSeatHeatingDown(boolean storeEnable) {
        LogUtils.d(TAG, "onSeatHeatingDown", false);
        int seatHeatLevel = this.mHvacViewModel.getSeatHeatLevel() - 1;
        if (seatHeatLevel < SeatHeatLevel.Level1.ordinal()) {
            seatHeatLevel = SeatHeatLevel.Level1.ordinal();
        }
        onSeatHeatDriverSet(new ChangeValue().setValue(seatHeatLevel), storeEnable);
    }

    protected void onSeatVentilateOn(boolean storeEnable) {
        LogUtils.d(TAG, "onSeatVentilateOn", false);
        if (SeatVentLevel.Off == SeatVentLevel.fromBcmState(this.mHvacViewModel.getSeatVentLevel())) {
            this.mHvacViewModel.setSeatVentLevel(SeatVentLevel.Level3.ordinal(), storeEnable);
        }
    }

    protected void onSeatVentilateOff(boolean storeEnable) {
        LogUtils.d(TAG, "onSeatVentilateOff", false);
        if (SeatVentLevel.Off != SeatVentLevel.fromBcmState(this.mHvacViewModel.getSeatVentLevel())) {
            this.mHvacViewModel.setSeatVentLevel(SeatVentLevel.Off.ordinal(), storeEnable);
        }
    }

    protected void onAllSeatVentilateOn(boolean storeEnable) {
        LogUtils.d(TAG, "onAllSeatVentilateOn", false);
        if (isSupportSeatBlow()) {
            onSeatVentilateOn(storeEnable);
        }
        if (isSupportPsnSeatBlow()) {
            onPsnSeatVentilateOn();
        }
    }

    protected void onAllSeatVentilateOff(boolean storeEnable) {
        LogUtils.d(TAG, "onAllSeatVentilateOff", false);
        if (isSupportSeatBlow()) {
            onSeatVentilateOff(storeEnable);
        }
        if (isSupportPsnSeatBlow()) {
            onPsnSeatVentilateOff();
        }
    }

    protected void onSeatVentilateMax(boolean storeEnable) {
        LogUtils.d(TAG, "onSeatVentilateMax", false);
        this.mHvacViewModel.setSeatVentLevel(SeatVentLevel.Level3.ordinal(), storeEnable);
    }

    protected void onSeatVentilateMin(boolean storeEnable) {
        LogUtils.d(TAG, "onSeatVentilateMin", false);
        this.mHvacViewModel.setSeatVentLevel(SeatVentLevel.Level1.ordinal(), storeEnable);
    }

    protected void onSeatVentilateDown(boolean storeEnable) {
        LogUtils.d(TAG, "onSeatVentilateDown", false);
        int seatVentLevel = this.mHvacViewModel.getSeatVentLevel() - 1;
        if (seatVentLevel < SeatVentLevel.Level1.ordinal()) {
            seatVentLevel = SeatVentLevel.Level1.ordinal();
        }
        onSeatVentilateDriverSet(new ChangeValue().setValue(seatVentLevel), storeEnable);
    }

    protected void onSeatVentilateUp(ChangeValue changeValue, boolean storeEnable) {
        LogUtils.d(TAG, "onSeatVentilateUp value=" + changeValue.getValue(), false);
        onSeatVentilateDriverSet(new ChangeValue().setValue(this.mHvacViewModel.getSeatVentLevel() + changeValue.getValue()), storeEnable);
    }

    protected void onSeatVentilateDriverSet(ChangeValue changeValue, boolean storeEnable) {
        LogUtils.d(TAG, "onSeatVentilateDriverSet value=" + changeValue.getValue(), false);
        this.mHvacViewModel.setSeatVentLevel(changeValue.getValue(), storeEnable);
    }

    protected void onSeatHeatDriverSet(ChangeValue changeValue, boolean storeEnable) {
        LogUtils.d(TAG, "onSeatHeatDriverSet value=" + changeValue.getValue(), false);
        this.mHvacViewModel.setSeatHeatLevel(changeValue.getValue(), storeEnable);
    }

    protected void onSeatHeatPassengerSet(ChangeValue changeValue) {
        LogUtils.d(TAG, "onSeatHeatPassengerSet value=" + changeValue.getValue(), false);
        this.mHvacViewModel.setPsnSeatHeatLevel(changeValue.getValue());
    }

    protected void onSeatHeatPassengerUp(ChangeValue changeValue) {
        LogUtils.d(TAG, "onSeatHeatPassengerUp value=" + changeValue.getValue(), false);
        onSeatHeatPassengerSet(new ChangeValue().setValue(this.mHvacViewModel.getPsnSeatHeatLevel() + changeValue.getValue()));
    }

    protected void onSeatHeatPassengerDown(ChangeValue changeValue) {
        LogUtils.d(TAG, "onSeatHeatPassengerDown value=" + changeValue.getValue(), false);
        int psnSeatHeatLevel = this.mHvacViewModel.getPsnSeatHeatLevel() - changeValue.getValue();
        if (psnSeatHeatLevel < SeatHeatLevel.Level1.ordinal()) {
            psnSeatHeatLevel = SeatHeatLevel.Level1.ordinal();
        }
        onSeatHeatPassengerSet(new ChangeValue().setValue(psnSeatHeatLevel));
    }

    protected void onPsnSeatVentilateOn() {
        LogUtils.d(TAG, "onPsnSeatVentilateOn", false);
        if (SeatVentLevel.Off == SeatVentLevel.fromBcmState(this.mHvacViewModel.getPsnSeatVentLevel())) {
            this.mHvacViewModel.setPsnSeatVentLevel(SeatVentLevel.Level3.ordinal());
        }
    }

    protected void onPsnSeatVentilateOff() {
        LogUtils.d(TAG, "onPsnSeatVentilateOff", false);
        if (SeatVentLevel.Off != SeatVentLevel.fromBcmState(this.mHvacViewModel.getPsnSeatVentLevel())) {
            this.mHvacViewModel.setPsnSeatVentLevel(SeatVentLevel.Off.ordinal());
        }
    }

    protected void onPsnSeatVentilateMax() {
        LogUtils.d(TAG, "onPsnSeatVentilateMax", false);
        this.mHvacViewModel.setPsnSeatVentLevel(SeatVentLevel.Level3.ordinal());
    }

    protected void onPsnSeatVentilateMin() {
        LogUtils.d(TAG, "onPsnSeatVentilateMin", false);
        this.mHvacViewModel.setPsnSeatVentLevel(SeatVentLevel.Level1.ordinal());
    }

    protected void onPsnSeatVentilateDown(ChangeValue changeValue) {
        LogUtils.d(TAG, "onPsnSeatVentilateDown value=" + changeValue.getValue(), false);
        int psnSeatVentLevel = this.mHvacViewModel.getPsnSeatVentLevel() - changeValue.getValue();
        if (psnSeatVentLevel < SeatVentLevel.Level1.ordinal()) {
            psnSeatVentLevel = SeatVentLevel.Level1.ordinal();
        }
        this.mHvacViewModel.setPsnSeatVentLevel(psnSeatVentLevel);
    }

    protected void onPsnSeatVentilateUp(ChangeValue changeValue) {
        LogUtils.d(TAG, "onPsnSeatVentilateUp value=" + changeValue.getValue(), false);
        int psnSeatVentLevel = this.mHvacViewModel.getPsnSeatVentLevel() + changeValue.getValue();
        if (psnSeatVentLevel > SeatVentLevel.Level3.ordinal()) {
            psnSeatVentLevel = SeatVentLevel.Level3.ordinal();
        }
        this.mHvacViewModel.setPsnSeatVentLevel(psnSeatVentLevel);
    }

    protected void onSeatVentilatePsnSet(ChangeValue changeValue) {
        LogUtils.d(TAG, "onSeatVentilatePsnSet value=" + changeValue.getValue(), false);
        this.mHvacViewModel.setPsnSeatVentLevel(changeValue.getValue());
    }

    protected void onOpenFastFridge() {
        LogUtils.d(TAG, "onOpenFastFridge", false);
        if (this.mHvacViewModel.isHvacRapidCoolingEnable()) {
            return;
        }
        this.mHvacViewModel.setHvacRapidCoolingEnable(true);
    }

    protected void onOpenFreshAir() {
        LogUtils.d(TAG, "onOpenFreshAir", false);
        if (this.mHvacViewModel.isHvacDeodorantEnable()) {
            return;
        }
        this.mHvacViewModel.setHvacDeodorantEnable(true);
    }

    protected void onExitFastFridge() {
        LogUtils.d(TAG, "onExitFastFridge", false);
        if (this.mHvacViewModel.isHvacRapidCoolingEnable()) {
            this.mHvacViewModel.setHvacRapidCoolingEnable(false);
        }
    }

    protected void onExitFreshAir() {
        LogUtils.d(TAG, "onExitFreshAir", false);
        if (this.mHvacViewModel.isHvacDeodorantEnable()) {
            this.mHvacViewModel.setHvacDeodorantEnable(false);
        }
    }

    protected boolean isSupportAutoOff() {
        LogUtils.d(TAG, "isSupportAutoOff", false);
        return true;
    }

    protected boolean isSupportDemistFoot() {
        LogUtils.d(TAG, "isSupportDemistFoot", false);
        return true;
    }

    protected int getWindMax() {
        LogUtils.d(TAG, "getWindMax", false);
        return this.mHvacViewModel.getFanMaxLevel();
    }

    protected int getWindMin() {
        LogUtils.d(TAG, "getWindMin", false);
        return 1;
    }

    protected double getTempMax() {
        LogUtils.d(TAG, "getTempMax", false);
        return 32.0d;
    }

    protected double getTempMin() {
        LogUtils.d(TAG, "getTempMin", false);
        return 18.0d;
    }

    protected boolean isSupportDecimalValue() {
        LogUtils.d(TAG, "isSupportDecimalValue", false);
        return true;
    }

    protected boolean isSupportPurifier() {
        LogUtils.d(TAG, "isSupportPurifier", false);
        return CarBaseConfig.getInstance().isSupportInnerPm25();
    }

    protected boolean isSupportPm25() {
        LogUtils.d(TAG, "isSupportPm25", false);
        return CarBaseConfig.getInstance().isSupportInnerPm25();
    }

    protected int getAirLevel() {
        LogUtils.d(TAG, "getAirLevel", false);
        int hvacInnerPM25 = this.mHvacViewModel.getHvacInnerPM25();
        if (hvacInnerPM25 > 150) {
            return 4;
        }
        if (hvacInnerPM25 > 100) {
            return 3;
        }
        return hvacInnerPM25 > 50 ? 2 : 1;
    }

    protected int getOutSidePmQuality() {
        LogUtils.d(TAG, "getOutSidePmQuality", false);
        return this.mHvacViewModel.getOutsidePm25();
    }

    protected int getOutSidePmLevelQuality() {
        int outsidePm25 = this.mHvacViewModel.getOutsidePm25();
        int i = outsidePm25 > 150 ? 4 : outsidePm25 > 100 ? 3 : outsidePm25 > 50 ? 2 : 1;
        LogUtils.d(TAG, "getOutSidePmLevelQuality" + i, false);
        return i;
    }

    protected int isSupportOutSidePm() {
        LogUtils.d(TAG, "isSupportOutSidePm", false);
        return CarBaseConfig.getInstance().isSupportPM25Out() ? 1 : 0;
    }

    protected boolean isSupportSeatHeat() {
        LogUtils.d(TAG, "isSupportSeatHeat", false);
        return CarBaseConfig.getInstance().isSupportDrvSeatHeat();
    }

    protected boolean isSupportPsnSeatHeat() {
        LogUtils.d(TAG, "isSupportPsnSeatHeat", false);
        return CarBaseConfig.getInstance().isSupportPsnSeatHeat();
    }

    protected int getHeatMax() {
        LogUtils.d(TAG, "getHeatMax", false);
        return SeatHeatLevel.Level3.ordinal();
    }

    protected int getHeatMin() {
        LogUtils.d(TAG, "getHeatMin", false);
        return SeatHeatLevel.Level1.ordinal();
    }

    protected boolean isSupportSeatBlow() {
        LogUtils.d(TAG, "isSupportSeatBlow", false);
        return CarBaseConfig.getInstance().isSupportDrvSeatVent();
    }

    protected int getSeatBlowMax() {
        LogUtils.d(TAG, "getSeatBlowMax", false);
        return SeatVentLevel.Level3.ordinal();
    }

    protected int getSeatBlowMin() {
        LogUtils.d(TAG, "getSeatBlowMin", false);
        return SeatVentLevel.Level1.ordinal();
    }

    protected boolean isFastFridge() {
        LogUtils.d(TAG, "isFastFridge", false);
        return this.mHvacViewModel.isHvacRapidCoolingEnable();
    }

    protected boolean isFreshAir() {
        LogUtils.d(TAG, "isFreshAir", false);
        return this.mHvacViewModel.isHvacDeodorantEnable();
    }

    protected boolean isSupportTempDual() {
        LogUtils.d(TAG, "isSupportTempDual", false);
        return CarBaseConfig.getInstance().isSupportHvacDualTemp();
    }

    protected void onAqsOn() {
        LogUtils.d(TAG, "onAqsOn", false);
        if (this.mHvacViewModel.getHvacAqsMode() != 1) {
            this.mHvacViewModel.setHvacAqsMode(1);
        }
    }

    protected void onAqsOff() {
        LogUtils.d(TAG, "onAqsOff", false);
        if (this.mHvacViewModel.getHvacAqsMode() != 0) {
            this.mHvacViewModel.setHvacAqsMode(0);
        }
    }

    protected void onModesEcoOn() {
        LogUtils.d(TAG, "onModesEcoOn", false);
        if (this.mHvacViewModel.getHvacEconMode() != 1) {
            this.mHvacViewModel.setHvacEconMode(1);
        }
    }

    protected void onWindUnidirection(final int pilot, final int direction) {
        LogUtils.d(TAG, "onWindUnidirection:" + pilot + "," + direction, false);
        onHvacOn();
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.speech.-$$Lambda$HvacControlSpeechModel$zAJrAXyH40rITaCMyvYm4j2JyGs
            @Override // java.lang.Runnable
            public final void run() {
                HvacControlSpeechModel.this.lambda$onWindUnidirection$1$HvacControlSpeechModel(pilot, direction);
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$onWindUnidirection$1$HvacControlSpeechModel(final int pilot, final int direction) {
        this.mHvacViewModel.openHvacWindModeFace();
        if (pilot != 2) {
            onWindOutletOn(1);
            this.mHvacViewModel.setHvacEavDriverWindMode(HvacEavWindMode.toHvacCmd(HvacEavWindMode.Single));
            if (direction == 3) {
                this.mHvacViewModel.setHvacEAVDriverLeftHPosDirect(1);
                this.mHvacViewModel.setHvacEAVDriverLeftVPosDirect(4);
                this.mHvacViewModel.setHvacEAVDriverRightHPosDirect(1);
                this.mHvacViewModel.setHvacEAVDriverRightVPosDirect(4);
            } else if (direction == 4) {
                this.mHvacViewModel.setHvacEAVDriverLeftHPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                this.mHvacViewModel.setHvacEAVDriverLeftVPosDirect(4);
                this.mHvacViewModel.setHvacEAVDriverRightHPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                this.mHvacViewModel.setHvacEAVDriverRightVPosDirect(4);
            } else if (direction == 5) {
                this.mHvacViewModel.setHvacEAVDriverLeftVPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                this.mHvacViewModel.setHvacEAVDriverRightVPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
            } else if (direction == 6) {
                this.mHvacViewModel.setHvacEAVDriverLeftVPosDirect(1);
                this.mHvacViewModel.setHvacEAVDriverRightVPosDirect(1);
            }
        }
        if (pilot != 1) {
            onWindOutletOn(2);
            this.mHvacViewModel.setHvacEavPsnWindMode(HvacEavWindMode.toHvacCmd(HvacEavWindMode.Single));
            if (direction == 3) {
                this.mHvacViewModel.setHvacEAVPsnLeftHPosDirect(1);
                this.mHvacViewModel.setHvacEAVPsnLeftVPosDirect(4);
                this.mHvacViewModel.setHvacEAVPsnRightHPosDirect(1);
                this.mHvacViewModel.setHvacEAVPsnRightVPosDirect(4);
            } else if (direction == 4) {
                this.mHvacViewModel.setHvacEAVPsnLeftHPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                this.mHvacViewModel.setHvacEAVPsnLeftVPosDirect(4);
                this.mHvacViewModel.setHvacEAVPsnRightHPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                this.mHvacViewModel.setHvacEAVPsnRightVPosDirect(4);
            } else if (direction == 5) {
                this.mHvacViewModel.setHvacEAVPsnLeftVPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                this.mHvacViewModel.setHvacEAVPsnRightVPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
            } else if (direction == 6) {
                this.mHvacViewModel.setHvacEAVPsnLeftVPosDirect(1);
                this.mHvacViewModel.setHvacEAVPsnRightVPosDirect(1);
            }
        }
        brokenSweepWind(pilot);
    }

    protected void onWindEvad(final int pilot) {
        LogUtils.d(TAG, "onWindEvad:" + pilot, false);
        onHvacOn();
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.speech.-$$Lambda$HvacControlSpeechModel$09CdhQBKeKRBd2uoR6X5UpSbZ68
            @Override // java.lang.Runnable
            public final void run() {
                HvacControlSpeechModel.this.lambda$onWindEvad$2$HvacControlSpeechModel(pilot);
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$onWindEvad$2$HvacControlSpeechModel(final int pilot) {
        this.mHvacViewModel.openHvacWindModeFace();
        if (pilot != 2) {
            onWindOutletOn(1);
            this.mHvacViewModel.setHvacEavDriverWindMode(HvacEavWindMode.toHvacCmd(HvacEavWindMode.Mirror));
            this.mHvacViewModel.setHvacEAVDriverLeftHPosDirect(1);
            this.mHvacViewModel.setHvacEAVDriverLeftVPosDirect(4);
            this.mHvacViewModel.setHvacEAVDriverRightHPosDirect(5);
            this.mHvacViewModel.setHvacEAVDriverRightVPosDirect(4);
        }
        if (pilot != 1) {
            onWindOutletOn(2);
            this.mHvacViewModel.setHvacEavPsnWindMode(HvacEavWindMode.toHvacCmd(HvacEavWindMode.Mirror));
            this.mHvacViewModel.setHvacEAVPsnLeftHPosDirect(1);
            this.mHvacViewModel.setHvacEAVPsnLeftVPosDirect(4);
            this.mHvacViewModel.setHvacEAVPsnRightHPosDirect(5);
            this.mHvacViewModel.setHvacEAVPsnRightVPosDirect(4);
        }
        brokenSweepWind(pilot);
    }

    protected void onWindFront(final int pilot) {
        LogUtils.d(TAG, "onWindFront:" + pilot, false);
        onHvacOn();
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.speech.-$$Lambda$HvacControlSpeechModel$Q4z4vh6Az1XFSoX7myzp6DysVcI
            @Override // java.lang.Runnable
            public final void run() {
                HvacControlSpeechModel.this.lambda$onWindFront$3$HvacControlSpeechModel(pilot);
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$onWindFront$3$HvacControlSpeechModel(final int pilot) {
        this.mHvacViewModel.openHvacWindModeFace();
        if (pilot != 2) {
            onWindOutletOn(1);
            this.mHvacViewModel.setHvacEavDriverWindMode(HvacEavWindMode.toHvacCmd(HvacEavWindMode.Mirror));
            this.mHvacViewModel.setHvacEAVDriverLeftHPosDirect(4);
            this.mHvacViewModel.setHvacEAVDriverLeftVPosDirect(4);
            this.mHvacViewModel.setHvacEAVDriverRightHPosDirect(2);
            this.mHvacViewModel.setHvacEAVDriverRightVPosDirect(4);
        }
        if (pilot != 1) {
            onWindOutletOn(2);
            this.mHvacViewModel.setHvacEavPsnWindMode(HvacEavWindMode.toHvacCmd(HvacEavWindMode.Mirror));
            this.mHvacViewModel.setHvacEAVPsnLeftHPosDirect(4);
            this.mHvacViewModel.setHvacEAVPsnLeftVPosDirect(4);
            this.mHvacViewModel.setHvacEAVPsnRightHPosDirect(2);
            this.mHvacViewModel.setHvacEAVPsnRightVPosDirect(4);
        }
        brokenSweepWind(pilot);
    }

    protected void onWindFree(final int pilot) {
        LogUtils.d(TAG, "onWindFree:" + pilot, false);
        onHvacOn();
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.speech.-$$Lambda$HvacControlSpeechModel$ROP2CvNV6bAGaXWZQfHbGPGOom0
            @Override // java.lang.Runnable
            public final void run() {
                HvacControlSpeechModel.this.lambda$onWindFree$4$HvacControlSpeechModel(pilot);
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$onWindFree$4$HvacControlSpeechModel(final int pilot) {
        this.mHvacViewModel.openHvacWindModeFace();
        if (pilot != 2) {
            onWindOutletOn(1);
            this.mHvacViewModel.setHvacEavDriverWindMode(HvacEavWindMode.toHvacCmd(HvacEavWindMode.Free));
        }
        if (pilot != 1) {
            onWindOutletOn(2);
            this.mHvacViewModel.setHvacEavPsnWindMode(HvacEavWindMode.toHvacCmd(HvacEavWindMode.Free));
        }
        brokenSweepWind(pilot);
    }

    protected void onWindFreshAirOpen() {
        LogUtils.i(TAG, "onWindFreshAirOpen");
        if (this.mHvacViewModel.isHvacNewFreshSwitchOn()) {
            return;
        }
        this.mHvacViewModel.setHvacNewFreshSwitchStatus(true);
    }

    protected void onWindUniCoordinateClose(int pilot, int x, int y) {
        LogUtils.d(TAG, "onWindUniCoordinateClose:" + pilot + ",x:" + x + ",y:" + y, false);
        onWindOutletOff(pilot);
    }

    protected void onWindUniCoordinateOpen(final int pilot, final int x, final int y) {
        LogUtils.d(TAG, "onWindUniCoordinateOpen:" + pilot + ",x:" + x + ",y:" + y, false);
        onHvacOn();
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.speech.-$$Lambda$HvacControlSpeechModel$mK9RvFO5dFhhhtUHRo0STH7kyJ4
            @Override // java.lang.Runnable
            public final void run() {
                HvacControlSpeechModel.this.lambda$onWindUniCoordinateOpen$5$HvacControlSpeechModel(pilot, x, y);
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$onWindUniCoordinateOpen$5$HvacControlSpeechModel(final int pilot, final int x, final int y) {
        this.mHvacViewModel.openHvacWindModeFace();
        onWindOutletOn(pilot);
        if (pilot == 3) {
            this.mHvacViewModel.setHvacEAVDriverLeftHPosDirect(x);
            this.mHvacViewModel.setHvacEAVDriverLeftVPosDirect(y);
        } else if (pilot == 4) {
            this.mHvacViewModel.setHvacEAVDriverRightHPosDirect(x);
            this.mHvacViewModel.setHvacEAVDriverRightVPosDirect(y);
        } else if (pilot == 5) {
            this.mHvacViewModel.setHvacEAVPsnLeftHPosDirect(x);
            this.mHvacViewModel.setHvacEAVPsnLeftVPosDirect(y);
        } else if (pilot != 6) {
        } else {
            this.mHvacViewModel.setHvacEAVPsnRightHPosDirect(x);
            this.mHvacViewModel.setHvacEAVPsnRightVPosDirect(y);
        }
    }

    protected void onWindUnidirectionSet(final int pilot, final int direction) {
        LogUtils.d(TAG, "onWindUnidirectionSet:" + pilot + ",direction:" + direction, false);
        onHvacOn();
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.speech.-$$Lambda$HvacControlSpeechModel$6pK1otvjBC_GiA6f1M0FIEW6_sI
            @Override // java.lang.Runnable
            public final void run() {
                HvacControlSpeechModel.this.lambda$onWindUnidirectionSet$6$HvacControlSpeechModel(pilot, direction);
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$onWindUnidirectionSet$6$HvacControlSpeechModel(final int pilot, final int direction) {
        this.mHvacViewModel.openHvacWindModeFace();
        if (pilot != 2) {
            onWindOutletOn(1);
            switch (direction) {
                case 1:
                case 2:
                    break;
                case 3:
                    this.mHvacViewModel.setHvacEAVDriverLeftHPosDirect(1);
                    this.mHvacViewModel.setHvacEAVDriverRightHPosDirect(1);
                    break;
                case 4:
                    this.mHvacViewModel.setHvacEAVDriverLeftHPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                    this.mHvacViewModel.setHvacEAVDriverRightHPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                    break;
                case 5:
                    this.mHvacViewModel.setHvacEAVDriverLeftVPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                    this.mHvacViewModel.setHvacEAVDriverRightVPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                    break;
                case 6:
                    this.mHvacViewModel.setHvacEAVDriverLeftVPosDirect(1);
                    this.mHvacViewModel.setHvacEAVDriverRightVPosDirect(1);
                    break;
                default:
                    this.mHvacViewModel.setHvacEAVDriverLeftHPosDirect(3);
                    this.mHvacViewModel.setHvacEAVDriverLeftVPosDirect(4);
                    this.mHvacViewModel.setHvacEAVDriverRightHPosDirect(3);
                    this.mHvacViewModel.setHvacEAVDriverRightVPosDirect(4);
                    break;
            }
        }
        if (pilot != 1) {
            onWindOutletOn(2);
            switch (direction) {
                case 1:
                case 2:
                    return;
                case 3:
                    this.mHvacViewModel.setHvacEAVPsnLeftHPosDirect(1);
                    this.mHvacViewModel.setHvacEAVPsnRightHPosDirect(1);
                    return;
                case 4:
                    this.mHvacViewModel.setHvacEAVPsnLeftHPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                    this.mHvacViewModel.setHvacEAVPsnRightHPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                    return;
                case 5:
                    this.mHvacViewModel.setHvacEAVPsnLeftVPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                    this.mHvacViewModel.setHvacEAVPsnRightVPosDirect(CarBaseConfig.getInstance().getHvacMaxWindPos());
                    return;
                case 6:
                    this.mHvacViewModel.setHvacEAVPsnLeftVPosDirect(1);
                    this.mHvacViewModel.setHvacEAVPsnRightVPosDirect(1);
                    return;
                default:
                    this.mHvacViewModel.setHvacEAVPsnLeftHPosDirect(3);
                    this.mHvacViewModel.setHvacEAVPsnLeftVPosDirect(4);
                    this.mHvacViewModel.setHvacEAVPsnRightHPosDirect(3);
                    this.mHvacViewModel.setHvacEAVPsnRightVPosDirect(4);
                    return;
            }
        }
    }

    protected void onModesEcoOff() {
        LogUtils.d(TAG, "onModesEcoOff", false);
        if (this.mHvacViewModel.getHvacEconMode() != 0) {
            this.mHvacViewModel.setHvacEconMode(0);
        }
    }

    protected void onHeatingOn() {
        LogUtils.d(TAG, "onHeatingOn", false);
        this.mHvacViewModel.setHvacHeatMode(true);
    }

    protected void onHeatingOff() {
        LogUtils.d(TAG, "onHeatingOff", false);
        this.mHvacViewModel.setHvacHeatMode(false);
    }

    protected void onNatureOn() {
        LogUtils.d(TAG, "onNatureOn", false);
        this.mHvacViewModel.setHvacNatureMode(true);
    }

    protected void onNatureOff() {
        LogUtils.d(TAG, "onNatureOff", false);
        this.mHvacViewModel.setHvacNatureMode(false);
    }

    protected void onWindAutoSweepOn() {
        LogUtils.d(TAG, "onWindAutoSweepOn", false);
        onHvacOn();
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.speech.-$$Lambda$HvacControlSpeechModel$DUDIEIjg5EYJ6lWXquXQYbzFPN0
            @Override // java.lang.Runnable
            public final void run() {
                HvacControlSpeechModel.this.lambda$onWindAutoSweepOn$7$HvacControlSpeechModel();
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$onWindAutoSweepOn$7$HvacControlSpeechModel() {
        this.mHvacViewModel.openHvacWindModeFace();
        this.mHvacViewModel.setHvacEavSweepMode(HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.ON));
    }

    protected void onWindAutoSweepOff() {
        LogUtils.d(TAG, "onWindAutoSweepOff", false);
        this.mHvacViewModel.setHvacEavDriverWindMode(HvacEavWindMode.toHvacCmd(HvacEavWindMode.Free));
        this.mHvacViewModel.setHvacEavPsnWindMode(HvacEavWindMode.toHvacCmd(HvacEavWindMode.Free));
    }

    private int getY(String data) {
        try {
            return Integer.parseInt(new JSONObject(data).optString("y"));
        } catch (Throwable th) {
            LogUtils.e(TAG, "y: " + th.getMessage());
            return 0;
        }
    }

    private int getX(String data) {
        try {
            return Integer.parseInt(new JSONObject(data).optString("x"));
        } catch (Throwable th) {
            LogUtils.e(TAG, "x: " + th.getMessage());
            return 0;
        }
    }

    private int getPilot(String data) {
        try {
            return Integer.parseInt(new JSONObject(data).optString("pilot"));
        } catch (Throwable th) {
            LogUtils.e(TAG, "getPilot: " + th.getMessage());
            return 0;
        }
    }

    private int getDirection(String data) {
        try {
            return Integer.parseInt(new JSONObject(data).optString(VuiConstants.EVENT_VALUE_DIRECTION));
        } catch (Throwable th) {
            LogUtils.e(TAG, "getDirection: " + th.getMessage());
            return 7;
        }
    }

    protected void onMirrorOn(final int i) {
        LogUtils.d(TAG, "onMirrorOn" + i, false);
        onHvacOn();
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.speech.-$$Lambda$HvacControlSpeechModel$rI21-d8I4ecIcy70yXTetmFKIj8
            @Override // java.lang.Runnable
            public final void run() {
                HvacControlSpeechModel.this.lambda$onMirrorOn$8$HvacControlSpeechModel(i);
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$onMirrorOn$8$HvacControlSpeechModel(final int i) {
        this.mHvacViewModel.openHvacWindModeFace();
        if (i != 2) {
            this.mHvacViewModel.setHvacEavDriverWindMode(2);
        }
        if (i != 1) {
            this.mHvacViewModel.setHvacEavPsnWindMode(2);
        }
        brokenSweepWind(i);
    }

    protected void onMirrorOff(int i) {
        LogUtils.d(TAG, "onMirrorOff" + i, false);
        onWindFree(i);
    }

    protected int isSupportMirrorHeat() {
        LogUtils.d(TAG, "isSupportMirrorHeat", false);
        return CarBaseConfig.getInstance().isSupportMirrorHeat() ? 1 : 0;
    }

    protected void onWindOutletOn(int i) {
        LogUtils.d(TAG, "onWindOutletOn" + i, false);
        switch (i) {
            case 1:
                this.mHvacViewModel.setHvacVentStatus(0, true, true);
                this.mHvacViewModel.setHvacVentStatus(1, true, true);
                return;
            case 2:
                this.mHvacViewModel.setHvacVentStatus(2, true, true);
                this.mHvacViewModel.setHvacVentStatus(3, true, true);
                return;
            case 3:
                this.mHvacViewModel.setHvacVentStatus(0, true, true);
                return;
            case 4:
                this.mHvacViewModel.setHvacVentStatus(1, true, true);
                return;
            case 5:
                this.mHvacViewModel.setHvacVentStatus(2, true, true);
                return;
            case 6:
                this.mHvacViewModel.setHvacVentStatus(3, true, true);
                return;
            default:
                return;
        }
    }

    protected void onWindOutletOff(int i) {
        LogUtils.d(TAG, "onWindOutletOff" + i, false);
        switch (i) {
            case 1:
                if (this.mHvacViewModel.isHvacVentOpen(0)) {
                    this.mHvacViewModel.setHvacVentStatus(0, false, true);
                }
                if (this.mHvacViewModel.isHvacVentOpen(1)) {
                    this.mHvacViewModel.setHvacVentStatus(1, false, true);
                    return;
                }
                return;
            case 2:
                if (this.mHvacViewModel.isHvacVentOpen(2)) {
                    this.mHvacViewModel.setHvacVentStatus(2, false, true);
                }
                if (this.mHvacViewModel.isHvacVentOpen(3)) {
                    this.mHvacViewModel.setHvacVentStatus(3, false, true);
                    return;
                }
                return;
            case 3:
                if (this.mHvacViewModel.isHvacVentOpen(0)) {
                    this.mHvacViewModel.setHvacVentStatus(0, false, true);
                    return;
                }
                return;
            case 4:
                if (this.mHvacViewModel.isHvacVentOpen(1)) {
                    this.mHvacViewModel.setHvacVentStatus(1, false, true);
                    return;
                }
                return;
            case 5:
                if (this.mHvacViewModel.isHvacVentOpen(2)) {
                    this.mHvacViewModel.setHvacVentStatus(2, false, true);
                    return;
                }
                return;
            case 6:
                if (this.mHvacViewModel.isHvacVentOpen(3)) {
                    this.mHvacViewModel.setHvacVentStatus(3, false, true);
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected int[] getWindsStatus() {
        LogUtils.d(TAG, "getWindsStatus", false);
        return new int[]{this.mHvacViewModel.isHvacVentOpen(0) ? 1 : 0, this.mHvacViewModel.isHvacVentOpen(1) ? 1 : 0, this.mHvacViewModel.isHvacVentOpen(2) ? 1 : 0, this.mHvacViewModel.isHvacVentOpen(3) ? 1 : 0};
    }

    private void openHvacActivity() {
        LogUtils.d(TAG, "openHvacActivity:" + IS_HVAC_PANEL_SHOWING, false);
        if (IS_HVAC_PANEL_SHOWING) {
            return;
        }
        Intent intent = new Intent(GlobalConstant.ACTION.ACTION_SHOW_HVAC_PANEL);
        intent.putExtra("from", "speech");
        intent.setFlags(270532608);
        App.getInstance().startActivity(intent);
    }

    protected void onAcPanelOn() {
        LogUtils.d(TAG, "onAcPanelOn", false);
        openHvacActivity();
    }

    protected int getDriWindDirectionMode() {
        LogUtils.d(TAG, "getDriWindDirectionMode", false);
        return this.mHvacViewModel.getHvacEavDriverWindMode();
    }

    protected int getPsnWindDirectionMode() {
        LogUtils.d(TAG, "getPsnWindDirectionMode", false);
        return this.mHvacViewModel.getHvacEavPsnWindMode();
    }

    protected int getAcPanelStatus() {
        LogUtils.d(TAG, "getAcPanelStatus:" + IS_HVAC_PANEL_SHOWING, false);
        return IS_HVAC_PANEL_SHOWING ? 1 : 0;
    }

    protected void openIntelligentPsn() {
        LogUtils.d(TAG, "openIntelligentPsn:", false);
        this.mHvacViewModel.setHvacSingleMode(true);
    }

    protected void closeIntelligentPsn() {
        LogUtils.d(TAG, "closeIntelligentPsn:", false);
        this.mHvacViewModel.setHvacSingleMode(false);
    }

    protected int getIntelligentPassengerStatus() {
        LogUtils.d(TAG, "getIntelligentPassengerStatus", false);
        return this.mHvacViewModel.isHvacSingleMode() ? 1 : 0;
    }

    protected boolean isSupportRearSeatHeat() {
        LogUtils.i(TAG, "isSupportRearSeatHeat");
        return CarBaseConfig.getInstance().isSupportRearSeatHeat();
    }

    protected int getLeftRearSeatHeatLevel() {
        LogUtils.i(TAG, "getLeftRearSeatHeatLevel");
        return this.mHvacViewModel.getRLSeatHeatLevel();
    }

    protected int getRightRearSeatHeatLevel() {
        LogUtils.i(TAG, "getRightRearSeatHeatLevel");
        return this.mHvacViewModel.getRRSeatHeatLevel();
    }

    protected void onRearSeatHeatingOpenClose(int mode, int position) {
        LogUtils.i(TAG, "onRearSeatHeatingOpenClose:" + mode + "," + position);
        boolean z = mode == 0;
        if (position == 0) {
            if (z && this.mHvacViewModel.getRLSeatHeatLevel() == 0) {
                this.mHvacViewModel.setRLSeatHeatLevel(3);
            }
            if (!z && this.mHvacViewModel.getRLSeatHeatLevel() != 0) {
                this.mHvacViewModel.setRLSeatHeatLevel(0);
            }
        }
        if (position == 1) {
            if (z && this.mHvacViewModel.getRRSeatHeatLevel() == 0) {
                this.mHvacViewModel.setRRSeatHeatLevel(3);
            }
            if (z || this.mHvacViewModel.getRRSeatHeatLevel() == 0) {
                return;
            }
            this.mHvacViewModel.setRRSeatHeatLevel(0);
        }
    }

    protected void onRearSeatHeatSet(int position, ChangeValue changeValue) {
        LogUtils.i(TAG, "onRearSeatHeatSet:" + position + "," + changeValue);
        if (changeValue != null) {
            if (position == 0) {
                this.mHvacViewModel.setRLSeatHeatLevel(changeValue.getValue());
            }
            if (position == 1) {
                this.mHvacViewModel.setRRSeatHeatLevel(changeValue.getValue());
            }
        }
    }

    protected void onRearSeatHeatUp(int position, ChangeValue changeValue) {
        LogUtils.i(TAG, "onRearSeatHeatUp:" + position + "," + changeValue);
        int value = changeValue != null ? changeValue.getValue() : 1;
        if (position == 0) {
            value = seatHeatVentRule(value + this.mHvacViewModel.getRLSeatHeatLevel());
            this.mHvacViewModel.setRLSeatHeatLevel(value);
        }
        if (position == 1) {
            this.mHvacViewModel.setRRSeatHeatLevel(seatHeatVentRule(value + this.mHvacViewModel.getRRSeatHeatLevel()));
        }
    }

    protected void onRearSeatHeatDown(int position, ChangeValue changeValue) {
        LogUtils.i(TAG, "onRearSeatHeatDown:" + position + "," + changeValue);
        int value = changeValue != null ? changeValue.getValue() : 1;
        if (position == 0) {
            value = seatHeatVentRule(this.mHvacViewModel.getRLSeatHeatLevel() - value);
            this.mHvacViewModel.setRLSeatHeatLevel(value);
        }
        if (position == 1) {
            this.mHvacViewModel.setRRSeatHeatLevel(seatHeatVentRule(this.mHvacViewModel.getRRSeatHeatLevel() - value));
        }
    }

    protected boolean isAcSupportPerfume() {
        LogUtils.i(TAG, "isAcSupportPerfume");
        return CarBaseConfig.getInstance().isSupportSfs();
    }

    protected void onHeadWindowOn() {
        LogUtils.d(TAG, "onHeadWindowOn");
    }

    protected void onHeadWindowFootOn() {
        LogUtils.d(TAG, "onHeadWindowFootOn");
    }

    protected void onHeadWindowOff() {
        LogUtils.d(TAG, "onHeadWindowOff");
    }

    protected boolean isSupportPsnSeatBlow() {
        LogUtils.i(TAG, "isSupportPsnSeatBlow");
        return CarBaseConfig.getInstance().isSupportPsnSeatVent();
    }

    protected int getPsnSeatBlowMax() {
        LogUtils.i(TAG, "getPsnSeatBlowMax");
        return SeatVentLevel.Level3.ordinal();
    }

    protected int getPsnSeatBlowMin() {
        LogUtils.i(TAG, "getPsnSeatBlowMin");
        return SeatVentLevel.Level1.ordinal();
    }

    protected int getPsnSeatBlowLevel() {
        LogUtils.i(TAG, "getPsnSeatBlowLevel");
        return this.mHvacViewModel.getPsnSeatVentLevel();
    }

    protected void onSteeringHeatingOn(boolean storeEnable) {
        LogUtils.i(TAG, "onSteeringHeatingOn");
        if (this.mHvacViewModel.getSteerHeatLevel() == 0) {
            this.mHvacViewModel.setSteerHeatLevel(3, storeEnable);
        }
    }

    protected void onSteeringHeatingOff(boolean storeEnable) {
        LogUtils.i(TAG, "onSteeringHeatingOff");
        if (this.mHvacViewModel.getSteerHeatLevel() != 0) {
            this.mHvacViewModel.setSteerHeatLevel(0, storeEnable);
        }
    }

    protected void onSteeringHeatingMax(boolean storeEnable) {
        LogUtils.i(TAG, "onSteeringHeatingMax");
        this.mHvacViewModel.setSteerHeatLevel(3, storeEnable);
    }

    protected void onSteeringHeatingMin(boolean storeEnable) {
        LogUtils.i(TAG, "onSteeringHeatingMin");
        this.mHvacViewModel.setSteerHeatLevel(1, storeEnable);
    }

    protected void onSteeringHeatingDown(ChangeValue changeValue, boolean storeEnable) {
        LogUtils.i(TAG, "onSteeringHeatingDown value=" + changeValue.getValue());
        int steerHeatLevel = this.mHvacViewModel.getSteerHeatLevel() - changeValue.getValue();
        if (steerHeatLevel < 1) {
            steerHeatLevel = 1;
        }
        this.mHvacViewModel.setSteerHeatLevel(steerHeatLevel, storeEnable);
    }

    protected void onSteeringHeatingUp(ChangeValue changeValue, boolean storeEnable) {
        LogUtils.i(TAG, "onSteeringHeatingUp value=" + changeValue.getValue());
        int steerHeatLevel = this.mHvacViewModel.getSteerHeatLevel() + changeValue.getValue();
        if (steerHeatLevel > 3) {
            steerHeatLevel = 3;
        }
        this.mHvacViewModel.setSteerHeatLevel(steerHeatLevel, storeEnable);
    }

    protected void onSteeringHeatingSet(ChangeValue changeValue, boolean storeEnable) {
        LogUtils.i(TAG, "onSteeringHeatingSet value=" + changeValue.getValue());
        this.mHvacViewModel.setSteerHeatLevel(changeValue.getValue(), storeEnable);
    }

    protected boolean isSupportSteeringHeating() {
        LogUtils.i(TAG, "isSupportSteeringHeating");
        return CarBaseConfig.getInstance().isSupportSteerHeat();
    }

    protected int getSteeringHeatingMax() {
        LogUtils.i(TAG, "getSteeringHeatingMax");
        return 3;
    }

    protected int getSteeringHeatingMin() {
        LogUtils.i(TAG, "getSteeringHeatingMin");
        return 1;
    }

    protected int getSteeringHeatingLevel() {
        LogUtils.i(TAG, "getSteeringHeatingLevel");
        return this.mHvacViewModel.getSteerHeatLevel();
    }

    protected void onFastWarmingOn() {
        LogUtils.i(TAG, "onFastWarmingOn");
        if (this.mHvacViewModel.isHvacRapidHeatEnable()) {
            return;
        }
        this.mHvacViewModel.setHvacRapidHeatEnable(true);
    }

    protected void onFastWarmingOff() {
        LogUtils.i(TAG, "onFastWarmingOff");
        if (this.mHvacViewModel.isHvacRapidHeatEnable()) {
            this.mHvacViewModel.setHvacRapidHeatEnable(false);
        }
    }

    protected boolean isSupportFastHeat() {
        LogUtils.i(TAG, "isSupportFastHeat");
        return CarBaseConfig.getInstance().isSupportRapidHeat();
    }

    protected boolean isFastHeatEnable() {
        LogUtils.i(TAG, "isFastHeatEnable");
        return this.mHvacViewModel.isHvacRapidHeatEnable();
    }

    protected void onIntelligentDesiccationOn() {
        LogUtils.i(TAG, "onIntelligentDesiccationOn");
        this.mHvacViewModel.setHvacSelfDryEnable(true);
    }

    protected void onIntelligentDesiccationOff() {
        LogUtils.i(TAG, "onIntelligentDesiccationOff");
        this.mHvacViewModel.setHvacSelfDryEnable(false);
    }

    protected boolean isSupportIntelligentDesiccation() {
        LogUtils.i(TAG, "isSupportIntelligentDesiccation");
        return CarBaseConfig.getInstance().isSupportIntelligentDesiccation();
    }

    protected boolean isInIntelligentDesiccation() {
        LogUtils.i(TAG, "isInIntelligentDesiccation");
        return this.mHvacViewModel.isHvacSelfDryOn();
    }

    protected int getTempDualMode() {
        LogUtils.i(TAG, "getTempDualMode");
        return 1;
    }

    protected int getAllSeatHeatStatus() {
        LogUtils.i(TAG, "getAllSeatHeatStatus");
        if (isSupportSeatHeat() || isSupportPsnSeatHeat() || isSupportRearSeatHeat()) {
            int i = 1;
            if (((isSupportSeatHeat() && SeatHeatLevel.Off != SeatHeatLevel.fromBcmState(this.mHvacViewModel.getSeatHeatLevel())) || !isSupportSeatHeat()) && (((isSupportPsnSeatHeat() && SeatHeatLevel.Off != SeatHeatLevel.fromBcmState(this.mHvacViewModel.getPsnSeatHeatLevel())) || !isSupportPsnSeatHeat()) && ((isSupportRearSeatHeat() && SeatHeatLevel.Off != SeatHeatLevel.fromBcmState(this.mHvacViewModel.getRLSeatHeatLevel()) && SeatHeatLevel.Off != SeatHeatLevel.fromBcmState(this.mHvacViewModel.getRRSeatHeatLevel())) || !isSupportRearSeatHeat()))) {
                i = 3;
            }
            if (!(isSupportSeatHeat() && SeatHeatLevel.Off == SeatHeatLevel.fromBcmState(this.mHvacViewModel.getSeatHeatLevel())) && isSupportSeatHeat()) {
                return i;
            }
            if (!(isSupportPsnSeatHeat() && SeatHeatLevel.Off == SeatHeatLevel.fromBcmState(this.mHvacViewModel.getPsnSeatHeatLevel())) && isSupportPsnSeatHeat()) {
                return i;
            }
            if (!(isSupportRearSeatHeat() && SeatHeatLevel.Off == SeatHeatLevel.fromBcmState(this.mHvacViewModel.getRLSeatHeatLevel()) && SeatHeatLevel.Off == SeatHeatLevel.fromBcmState(this.mHvacViewModel.getRRSeatHeatLevel())) && isSupportRearSeatHeat()) {
                return i;
            }
            return 4;
        }
        return 2;
    }

    protected int getAllSeatVentilateStatus() {
        LogUtils.i(TAG, "getAllSeatVentilateStatus");
        if (isSupportSeatBlow() || isSupportPsnSeatBlow()) {
            int i = 1;
            if (((isSupportSeatBlow() && SeatVentLevel.Off != SeatVentLevel.fromBcmState(this.mHvacViewModel.getSeatVentLevel())) || !isSupportSeatBlow()) && ((isSupportPsnSeatBlow() && SeatVentLevel.Off != SeatVentLevel.fromBcmState(this.mHvacViewModel.getPsnSeatVentLevel())) || !isSupportPsnSeatBlow())) {
                i = 3;
            }
            if (!(isSupportSeatBlow() && SeatVentLevel.Off == SeatVentLevel.fromBcmState(this.mHvacViewModel.getSeatVentLevel())) && isSupportSeatBlow()) {
                return i;
            }
            if (!(isSupportPsnSeatBlow() && SeatVentLevel.Off == SeatVentLevel.fromBcmState(this.mHvacViewModel.getPsnSeatVentLevel())) && isSupportPsnSeatBlow()) {
                return i;
            }
            return 4;
        }
        return 2;
    }

    protected float getOutsideTemp() {
        LogUtils.i(TAG, "getOutsideTemp");
        IHvacViewModel iHvacViewModel = this.mHvacViewModel;
        if (iHvacViewModel != null) {
            return iHvacViewModel.getHvacExternalTemp();
        }
        return 0.0f;
    }

    protected void onRearHvacOn() {
        LogUtils.d(TAG, "onRearHvacOn", false);
        this.mHvacViewModel.setHvacRearVoiceWindBlowMode(225);
    }

    protected void onRearHvacOff() {
        LogUtils.d(TAG, "onRearHvacOff", false);
        this.mHvacViewModel.setHvacRearVoiceWindBlowMode(224);
    }

    protected void onRearBlowHeadOn() {
        LogUtils.d(TAG, "onRearBlowHeadOn", false);
        this.mHvacViewModel.setHvacRearVoiceWindBlowMode(1);
    }

    protected void onRearBlowFootOn() {
        LogUtils.d(TAG, "onRearBlowFootOn", false);
        this.mHvacViewModel.setHvacRearVoiceWindBlowMode(3);
    }

    protected void onRearBlowHeadFootOn() {
        LogUtils.d(TAG, "onRearBlowHeadFootOn", false);
        this.mHvacViewModel.setHvacRearVoiceWindBlowMode(2);
    }

    protected int getRearPowerStatus() {
        int rearWindBlowMode = getRearWindBlowMode();
        return (rearWindBlowMode == 225 || rearWindBlowMode == 1 || rearWindBlowMode == 2 || rearWindBlowMode == 3) ? 1 : 0;
    }

    protected int getRearWindBlowMode() {
        IHvacViewModel iHvacViewModel = this.mHvacViewModel;
        int hvacRearWindBlowMode = iHvacViewModel != null ? iHvacViewModel.getHvacRearWindBlowMode() : 224;
        LogUtils.d(TAG, "getRearWindBlowMode: " + hvacRearWindBlowMode, false);
        return hvacRearWindBlowMode;
    }
}
