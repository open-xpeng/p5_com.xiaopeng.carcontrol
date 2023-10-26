package com.xiaopeng.lib.framework.moduleinterface.datalogmodule;

/* loaded from: classes2.dex */
public interface IMoleEventBuilder {
    IMoleEvent build();

    IMoleEventBuilder setButtonId(String str);

    IMoleEventBuilder setEvent(String str);

    IMoleEventBuilder setModule(String str);

    IMoleEventBuilder setPageId(String str);

    IMoleEventBuilder setProperty(String str, char c);

    IMoleEventBuilder setProperty(String str, Number number);

    IMoleEventBuilder setProperty(String str, String str2);

    IMoleEventBuilder setProperty(String str, boolean z);
}
