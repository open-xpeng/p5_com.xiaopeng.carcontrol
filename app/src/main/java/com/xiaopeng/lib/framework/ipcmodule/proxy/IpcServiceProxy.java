package com.xiaopeng.lib.framework.ipcmodule.proxy;

import android.content.Context;
import android.os.Bundle;
import com.xiaopeng.lib.framework.ipcmodule.IpcServiceImpl;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;

/* loaded from: classes2.dex */
public class IpcServiceProxy implements IIpcService {
    @Override // com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService
    public void init() {
    }

    public IpcServiceProxy(Context context) {
        IpcServiceImpl.getInstance().init(context);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService
    public void sendData(int i, Bundle bundle, String... strArr) {
        IpcServiceImpl.getInstance().sendData(i, bundle, strArr);
    }
}
