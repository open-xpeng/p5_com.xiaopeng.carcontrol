package com.xiaopeng.lib.framework.moduleinterface.datalogmodule;

/* loaded from: classes2.dex */
public interface IStatEvent {
    public static final String CUSTOM_DEVICE_MCUVER = "_mcuver";
    public static final String CUSTOM_EVENT = "_event";
    public static final String CUSTOM_MODULE = "_module";
    public static final String CUSTOM_MODULE_VERSION = "_module_version";
    public static final String CUSTOM_NETWORK = "_network";
    public static final String CUSTOM_STARTUP = "_st_time";
    public static final String CUSTOM_TIMESTAMP = "_time";
    public static final String CUSTOM_UID = "_uid";

    String getEventName();

    void put(String str, Boolean bool);

    void put(String str, Character ch);

    void put(String str, Number number);

    void put(String str, String str2);

    void setEventName(String str);

    String toJson();
}
