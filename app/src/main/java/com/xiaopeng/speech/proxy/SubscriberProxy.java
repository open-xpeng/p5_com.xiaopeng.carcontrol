package com.xiaopeng.speech.proxy;

import android.os.RemoteException;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.ISpeechEngine;
import com.xiaopeng.speech.common.util.IPCRunner;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.common.util.WorkerHandler;
import com.xiaopeng.speech.coreapi.IEventObserver;
import com.xiaopeng.speech.coreapi.ISubscriber;

/* loaded from: classes2.dex */
public class SubscriberProxy extends ISubscriber.Stub implements ConnectManager.OnConnectCallback {
    private IPCRunner<ISubscriber> mIpcRunner = new IPCRunner<>("SubscriberProxy");

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
        try {
            this.mIpcRunner.setProxy(iSpeechEngine.getSubscriber());
            this.mIpcRunner.fetchAll();
        } catch (RemoteException e) {
            LogUtils.e(this, "onConnect exception ", e);
        }
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
        this.mIpcRunner.setProxy(null);
    }

    @Override // com.xiaopeng.speech.coreapi.ISubscriber
    public void subscribe(final String[] strArr, final IEventObserver iEventObserver) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<ISubscriber, Object>() { // from class: com.xiaopeng.speech.proxy.SubscriberProxy.1
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(ISubscriber iSubscriber) throws RemoteException {
                iSubscriber.subscribe(strArr, iEventObserver);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.ISubscriber
    public void unSubscribe(final IEventObserver iEventObserver) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<ISubscriber, Object>() { // from class: com.xiaopeng.speech.proxy.SubscriberProxy.2
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(ISubscriber iSubscriber) throws RemoteException {
                iSubscriber.unSubscribe(iEventObserver);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.ISubscriber
    public boolean isSubscribed(final String str) {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<ISubscriber, Boolean>() { // from class: com.xiaopeng.speech.proxy.SubscriberProxy.3
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(ISubscriber iSubscriber) throws RemoteException {
                return Boolean.valueOf(iSubscriber.isSubscribed(str));
            }
        }, false)).booleanValue();
    }

    public void setHandler(WorkerHandler workerHandler) {
        this.mIpcRunner.setWorkerHandler(workerHandler);
    }
}
