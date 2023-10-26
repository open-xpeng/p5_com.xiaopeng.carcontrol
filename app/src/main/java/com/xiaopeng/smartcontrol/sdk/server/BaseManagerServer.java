package com.xiaopeng.smartcontrol.sdk.server;

import com.xiaopeng.smartcontrol.ipc.server.ServerManager;
import com.xiaopeng.smartcontrol.sdk.base.AbsBaseManager;
import com.xiaopeng.smartcontrol.sdk.server.BaseManagerServer.Implementation;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public abstract class BaseManagerServer<T extends Implementation> extends AbsBaseManager {
    protected final String TAG = getClass().getSimpleName();
    protected T mImpl;

    /* loaded from: classes2.dex */
    public interface Implementation {
    }

    public abstract String onIpcCall(String str, String str2);

    public void setSignalImpl(T t) {
        if (t != null) {
            this.mImpl = t;
        } else {
            LogUtils.e(this.TAG, "setSignalImpl null");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void sendIpc(String str, Object obj) {
        ServerManager.get().send(getClass().getName(), str, String.valueOf(obj));
    }
}
