package com.xiaopeng.speech.common.util;

import android.os.RemoteException;

/* loaded from: classes2.dex */
public class IPCRunner<Proxy> extends AsyncRunner<IIPCFunc> {
    private volatile Proxy mProxy;

    /* loaded from: classes2.dex */
    public interface IIPCFunc<Proxy, R> {
        R run(Proxy proxy) throws RemoteException;
    }

    public IPCRunner(String str) {
        super(str);
    }

    public synchronized void setProxy(Proxy proxy) {
        LogUtils.i(this.TAG, "proxy = " + proxy);
        this.mProxy = proxy;
    }

    @Override // com.xiaopeng.speech.common.util.AsyncRunner
    protected synchronized boolean canRun() {
        return this.mProxy != null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.speech.common.util.AsyncRunner
    public synchronized Object realRun(IIPCFunc iIPCFunc) {
        try {
        } catch (Throwable th) {
            LogUtils.e(this.TAG, "ipc run catch exception ", th);
            return null;
        }
        return iIPCFunc.run(this.mProxy);
    }
}
