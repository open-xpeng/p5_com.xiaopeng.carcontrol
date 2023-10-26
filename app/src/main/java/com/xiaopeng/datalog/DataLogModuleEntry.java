package com.xiaopeng.datalog;

import android.content.Context;
import com.xiaopeng.lib.framework.module.IModuleEntry;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;

/* loaded from: classes2.dex */
public class DataLogModuleEntry implements IModuleEntry {
    private Context mContext;
    private volatile IDataLog mDataLog;

    public DataLogModuleEntry(Context context) {
        this.mContext = context;
    }

    @Override // com.xiaopeng.lib.framework.module.IModuleEntry
    public Object get(Class cls) {
        if (cls == IDataLog.class) {
            if (this.mDataLog == null) {
                synchronized (this) {
                    if (this.mDataLog == null) {
                        this.mDataLog = new DataLogService(this.mContext);
                    }
                }
            }
            return this.mDataLog;
        }
        return null;
    }
}
