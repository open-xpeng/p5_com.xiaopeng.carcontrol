package com.xiaopeng.system.delegate.module;

import android.content.Context;
import com.xiaopeng.lib.framework.module.IModuleEntry;
import com.xiaopeng.lib.framework.moduleinterface.systemdelegate.ISystemDelegate;

/* loaded from: classes2.dex */
public class SystemDelegateModuleEntry implements IModuleEntry {
    private Context mContext;
    private volatile ISystemDelegate mSystemDelegate;

    public SystemDelegateModuleEntry(Context context) {
        this.mContext = context;
    }

    @Override // com.xiaopeng.lib.framework.module.IModuleEntry
    public Object get(Class cls) {
        if (cls == ISystemDelegate.class) {
            if (this.mSystemDelegate == null) {
                synchronized (this) {
                    if (this.mSystemDelegate == null) {
                        this.mSystemDelegate = new SystemDelegateService(this.mContext);
                    }
                }
            }
            return this.mSystemDelegate;
        }
        return null;
    }
}
