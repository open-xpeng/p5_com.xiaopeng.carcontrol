package com.xiaopeng.lib.framework.moduleinterface.syncmodule;

import android.app.Application;
import java.util.List;

/* loaded from: classes2.dex */
public interface ISync {
    void init(Application application, String str, String str2, String str3, String str4, String str5);

    void onNetworkAvailable();

    void onUserChanged(Long l);

    void restore();

    void save(List<SyncData> list);

    void saveAll(List<SyncData> list);
}
