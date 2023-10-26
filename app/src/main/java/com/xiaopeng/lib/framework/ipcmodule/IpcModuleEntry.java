package com.xiaopeng.lib.framework.ipcmodule;

import android.content.Context;
import com.xiaopeng.lib.framework.ipcmodule.proxy.IpcServiceProxy;
import com.xiaopeng.lib.framework.module.IModuleEntry;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;

/* loaded from: classes2.dex */
public class IpcModuleEntry implements IModuleEntry {
    private Context mContext;
    private volatile IpcServiceProxy mModuleServiceProxy;

    public IpcModuleEntry(Context context) {
        this.mContext = context;
    }

    @Override // com.xiaopeng.lib.framework.module.IModuleEntry
    public Object get(Class cls) {
        if (cls == IIpcService.class) {
            if (this.mModuleServiceProxy == null) {
                synchronized (this) {
                    if (this.mModuleServiceProxy == null) {
                        this.mModuleServiceProxy = new IpcServiceProxy(this.mContext);
                    }
                }
            }
            return this.mModuleServiceProxy;
        }
        return null;
    }
}
