package com.xiaopeng.carcontrol.util;

import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;

/* loaded from: classes2.dex */
public class ModuleUtils {
    public static IHttp getHttp() {
        try {
            return (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
