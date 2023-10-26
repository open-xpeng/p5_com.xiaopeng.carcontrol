package com.xiaopeng.speech.protocol.node.navi.bean;

import android.text.TextUtils;
import com.xiaopeng.speech.common.util.DontProguardClass;
import com.xiaopeng.speech.common.util.LogUtils;
import org.json.JSONException;
import org.json.JSONObject;

@DontProguardClass
/* loaded from: classes2.dex */
public class NaviPreferenceBean {
    public static final String ATMS = "atms";
    public static final int ATMS_ID = 300;
    public static final String AVOID_CARPOOL = "avoid carpool";
    public static final String AVOID_COUNTRY_BORDER = "avoid country border";
    public static final String AVOID_FERRIES = "avoid ferries";
    public static final String AVOID_HIGHWAY = "avoid highway";
    public static final String AVOID_TOLL = "avoid toll";
    public static final String AVOID_TUNNEL = "avoid tunnel";
    public static final String AVOID_UNPAVED = "avoid unpaved";
    public static final String CAFE = "coffee";
    public static final int CAFE_ID = 301;
    public static final String CARPOOL = "carpool";
    public static final String CHARGING_STATION = "charging station";
    public static final int CHARGING_STATION_ID = 307;
    public static final String COUNTRY_BORDER = "country border";
    public static final String ECO_FRIENDLY = "eco friendly";
    public static final String FASTEST = "fastest";
    public static final String FAST_FOOD = "fast food";
    public static final int FAST_FOOD_ID = 304;
    public static final String FERRIES = "ferries";
    public static final String GROCERY = "grocery";
    public static final int GROCERY_ID = 303;
    public static final String HIGHWAY = "highway";
    public static final String HOTEL = "hotel";
    public static final int HOTEL_ID = 302;
    public static final String PARKING_LOT = "parking lot";
    public static final int PARKING_LOT_ID = 306;
    public static final int PATH_PREF_AVOID_CARPOOL = 207;
    public static final int PATH_PREF_AVOID_COUNTRY_BORDER = 213;
    public static final int PATH_PREF_AVOID_FERRIES = 205;
    public static final int PATH_PREF_AVOID_HIGHWAY = 209;
    public static final int PATH_PREF_AVOID_TOLL = 201;
    public static final int PATH_PREF_AVOID_TUNNEL = 203;
    public static final int PATH_PREF_AVOID_UNPAVED = 211;
    public static final int PATH_PREF_CARPOOL = 206;
    public static final int PATH_PREF_COUNTRY_BORDER = 212;
    public static final int PATH_PREF_ECO_FRIENDLY = 101;
    public static final int PATH_PREF_FASTEST = 100;
    public static final int PATH_PREF_FERRIES = 204;
    public static final int PATH_PREF_HIGHWAY = 208;
    public static final int PATH_PREF_TOLL = 200;
    public static final int PATH_PREF_TUNNEL = 202;
    public static final int PATH_PREF_UNPAVED = 210;
    public static final String RESTAURANT = "restaurant";
    public static final int RESTAURANT_ID = 305;
    public static final String SAFETY_ALERT = "safety alert";
    private static final String TAG = "NaviPreferenceBean";
    public static final String TOLL = "toll";
    public static final String TOLL_GATE_ALERT = "toll gate alert";
    public static final String TRAFFIC_CAMERA_ALERT = "traffic camera alert";
    public static final String TRAFFIC_EVENT_ALERT = "traffic event alert";
    public static final String TUNNEL = "tunnel";
    public static final String UNPAVED = "unpaved";
    private int mEnable;
    private String mPref;

    public static NaviPreferenceBean fromJson(String str) {
        NaviPreferenceBean naviPreferenceBean = new NaviPreferenceBean();
        try {
            JSONObject jSONObject = new JSONObject(str);
            naviPreferenceBean.mPref = jSONObject.optString("mode");
            naviPreferenceBean.mEnable = jSONObject.optInt("switch");
            LogUtils.d(TAG, "fromJson, pref:" + naviPreferenceBean.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return naviPreferenceBean;
    }

    public String getPref() {
        return this.mPref;
    }

    public int getPrefId() {
        return prefNameToPrefId(this.mPref);
    }

    public boolean isEnable() {
        return this.mEnable == 1;
    }

    public static int prefNameToPrefId(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2097684128:
                if (str.equals(AVOID_UNPAVED)) {
                    c = 0;
                    break;
                }
                break;
            case -1772467395:
                if (str.equals(RESTAURANT)) {
                    c = 1;
                    break;
                }
                break;
            case -1749203455:
                if (str.equals(AVOID_COUNTRY_BORDER)) {
                    c = 2;
                    break;
                }
                break;
            case -1722267927:
                if (str.equals(PARKING_LOT)) {
                    c = 3;
                    break;
                }
                break;
            case -1355030580:
                if (str.equals(CAFE)) {
                    c = 4;
                    break;
                }
                break;
            case -1262772667:
                if (str.equals(AVOID_CARPOOL)) {
                    c = 5;
                    break;
                }
                break;
            case -1122290598:
                if (str.equals(ECO_FRIENDLY)) {
                    c = 6;
                    break;
                }
                break;
            case -1077115990:
                if (str.equals(FASTEST)) {
                    c = 7;
                    break;
                }
                break;
            case -963579080:
                if (str.equals(FERRIES)) {
                    c = '\b';
                    break;
                }
                break;
            case -901578142:
                if (str.equals(AVOID_HIGHWAY)) {
                    c = '\t';
                    break;
                }
                break;
            case -862547864:
                if (str.equals(TUNNEL)) {
                    c = '\n';
                    break;
                }
                break;
            case -597819771:
                if (str.equals(CHARGING_STATION)) {
                    c = 11;
                    break;
                }
                break;
            case -280604405:
                if (str.equals(UNPAVED)) {
                    c = '\f';
                    break;
                }
                break;
            case -85459550:
                if (str.equals(FAST_FOOD)) {
                    c = '\r';
                    break;
                }
                break;
            case 3004697:
                if (str.equals(ATMS)) {
                    c = 14;
                    break;
                }
                break;
            case 3565883:
                if (str.equals(TOLL)) {
                    c = 15;
                    break;
                }
                break;
            case 99467700:
                if (str.equals(HOTEL)) {
                    c = 16;
                    break;
                }
                break;
            case 292882701:
                if (str.equals(GROCERY)) {
                    c = 17;
                    break;
                }
                break;
            case 554307056:
                if (str.equals(CARPOOL)) {
                    c = 18;
                    break;
                }
                break;
            case 915501581:
                if (str.equals(HIGHWAY)) {
                    c = 19;
                    break;
                }
                break;
            case 1157046643:
                if (str.equals(AVOID_TUNNEL)) {
                    c = 20;
                    break;
                }
                break;
            case 1359855878:
                if (str.equals(AVOID_TOLL)) {
                    c = 21;
                    break;
                }
                break;
            case 1514308493:
                if (str.equals(AVOID_FERRIES)) {
                    c = 22;
                    break;
                }
                break;
            case 1569517558:
                if (str.equals(COUNTRY_BORDER)) {
                    c = 23;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return PATH_PREF_AVOID_UNPAVED;
            case 1:
                return 305;
            case 2:
                return PATH_PREF_AVOID_COUNTRY_BORDER;
            case 3:
                return 306;
            case 4:
                return 301;
            case 5:
                return PATH_PREF_AVOID_CARPOOL;
            case 6:
                return 101;
            case 7:
                return 100;
            case '\b':
                return PATH_PREF_FERRIES;
            case '\t':
                return PATH_PREF_AVOID_HIGHWAY;
            case '\n':
                return PATH_PREF_TUNNEL;
            case 11:
                return 307;
            case '\f':
                return PATH_PREF_UNPAVED;
            case '\r':
                return 304;
            case 14:
                return 300;
            case 15:
                return 200;
            case 16:
                return 302;
            case 17:
                return 303;
            case 18:
                return PATH_PREF_CARPOOL;
            case 19:
                return PATH_PREF_HIGHWAY;
            case 20:
                return PATH_PREF_AVOID_TUNNEL;
            case 21:
                return 201;
            case 22:
                return PATH_PREF_AVOID_FERRIES;
            case 23:
                return PATH_PREF_COUNTRY_BORDER;
            default:
                return 0;
        }
    }

    public String toString() {
        return "NaviPreferenceBean{pref='" + this.mPref + "', enable='" + this.mEnable + "'}";
    }
}
