package com.xiaopeng.lib.framework.moduleinterface.datalogmodule;

import java.util.List;

/* loaded from: classes2.dex */
public interface IDataLog {
    IMoleEventBuilder buildMoleEvent();

    @Deprecated
    IStatEventBuilder buildStat();

    ICounterFactory counterFactory();

    void sendCanData(String str);

    void sendFiles(List<String> list);

    String sendRecentSystemLog();

    void sendStatData(IMoleEvent iMoleEvent);

    @Deprecated
    void sendStatData(IStatEvent iStatEvent);

    void sendStatData(IStatEvent iStatEvent, List<String> list);

    void sendStatData(String str, String str2);

    void sendStatOriginData(String str, String str2);
}
