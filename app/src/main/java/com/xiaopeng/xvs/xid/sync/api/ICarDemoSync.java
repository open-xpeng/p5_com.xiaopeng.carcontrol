package com.xiaopeng.xvs.xid.sync.api;

/* loaded from: classes2.dex */
public interface ICarDemoSync {
    public static final String KEY1 = "DEMO_KEY1";
    public static final String KEY2 = "DEMO_KEY2";
    public static final String KEY3 = "DEMO_KEY3";
    public static final String KEY4 = "DEMO_KEY4";
    public static final String KEY5 = "DEMO_KEY5";
    public static final String KEY_BOOL = "KEY_BOOL";
    public static final String KEY_FLOAT = "KEY_FLOAT";
    public static final String KEY_INT = "KEY_INT";
    public static final String KEY_JSON = "KEY_JSON";
    public static final String KEY_LONG = "KEY_LONG";
    public static final String KEY_MAP = "KEY_MAP";
    public static final String KEY_STRING = "KEY_STRING";

    String getKEY1(String str);

    String getKEY2(String str);

    String getKEY3(String str);

    String getKEY4(String str);

    String getKEY5(String str);

    String getKeyBool(String str);

    String getKeyFloat(String str);

    String getKeyInt(String str);

    String getKeyJson(String str);

    String getKeyLong(String str);

    String getKeyMap(String str);

    String getKeyString(String str);

    void putKEY1(String str);

    void putKEY2(String str);

    void putKEY3(String str);

    void putKEY4(String str);

    void putKEY5(String str);

    void putKeyBool(String str);

    void putKeyFloat(String str);

    void putKeyInt(String str);

    void putKeyJson(String str);

    void putKeyLong(String str);

    void putKeyMap(String str);

    void putKeyString(String str);
}
