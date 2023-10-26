package com.xiaopeng.lib.framework.moduleinterface.datalogmodule;

/* loaded from: classes2.dex */
public interface IStatEventBuilder {
    IStatEvent build();

    IStatEventBuilder setEventName(String str);

    IStatEventBuilder setProperty(String str, char c);

    IStatEventBuilder setProperty(String str, Number number);

    IStatEventBuilder setProperty(String str, String str2);

    IStatEventBuilder setProperty(String str, boolean z);
}
