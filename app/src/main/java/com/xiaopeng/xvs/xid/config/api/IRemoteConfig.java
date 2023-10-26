package com.xiaopeng.xvs.xid.config.api;

import java.util.Map;

/* loaded from: classes2.dex */
public interface IRemoteConfig {
    void addRemoteConfigListener(RemoteConfigListener remoteConfigListener);

    String getRemoteConfig(String str);

    Map<String, String> getRemoteConfig();

    void removeRemoteConfigListener(RemoteConfigListener remoteConfigListener);
}
