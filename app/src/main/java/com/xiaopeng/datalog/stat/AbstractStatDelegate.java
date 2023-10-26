package com.xiaopeng.datalog.stat;

import android.content.Context;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEvent;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IStatEvent;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class AbstractStatDelegate {
    protected Context context;

    public abstract void uploadCan(String str);

    public abstract void uploadCdu(IMoleEvent iMoleEvent);

    public abstract void uploadCdu(IStatEvent iStatEvent);

    public abstract void uploadCduWithFiles(IStatEvent iStatEvent, List<String> list);

    public abstract void uploadFiles(List<String> list);

    public abstract void uploadLogImmediately(String str, String str2);

    public abstract void uploadLogOrigin(String str, String str2);

    public abstract String uploadRecentSystemLog();

    public AbstractStatDelegate(Context context) {
        this.context = context;
    }
}
