package com.xiaopeng.speech.proxy;

import android.os.RemoteException;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.IQueryInjector;
import com.xiaopeng.speech.IRemoteDataSensor;
import com.xiaopeng.speech.ISpeechEngine;
import com.xiaopeng.speech.common.bean.Value;
import com.xiaopeng.speech.common.util.IPCRunner;
import com.xiaopeng.speech.common.util.WorkerHandler;

/* loaded from: classes2.dex */
public class QueryInjectorProxy extends IQueryInjector.Stub implements ConnectManager.OnConnectCallback {
    private IPCRunner<IQueryInjector> mIpcRunner = new IPCRunner<>("QueryInjectorProxy");
    private WorkerHandler mWorkerHandler;

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
        try {
            this.mIpcRunner.setProxy(iSpeechEngine.getQueryInjector());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
        this.mIpcRunner.setProxy(null);
    }

    public void setHandler(WorkerHandler workerHandler) {
        this.mWorkerHandler = workerHandler;
        this.mIpcRunner.setWorkerHandler(workerHandler);
    }

    @Override // com.xiaopeng.speech.IQueryInjector
    public void registerDataSensor(final String[] strArr, final IRemoteDataSensor iRemoteDataSensor) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IQueryInjector, Object>() { // from class: com.xiaopeng.speech.proxy.QueryInjectorProxy.1
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IQueryInjector iQueryInjector) {
                try {
                    iQueryInjector.registerDataSensor(strArr, iRemoteDataSensor);
                    return null;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }

    @Override // com.xiaopeng.speech.IQueryInjector
    public void unRegisterDataSensor(final String[] strArr) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IQueryInjector, Object>() { // from class: com.xiaopeng.speech.proxy.QueryInjectorProxy.2
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IQueryInjector iQueryInjector) {
                try {
                    iQueryInjector.unRegisterDataSensor(strArr);
                    return null;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }

    @Override // com.xiaopeng.speech.IQueryInjector
    public Value queryData(final String str, final String str2) {
        return (Value) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IQueryInjector, Value>() { // from class: com.xiaopeng.speech.proxy.QueryInjectorProxy.3
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Value run(IQueryInjector iQueryInjector) {
                try {
                    return iQueryInjector.queryData(str, str2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return Value.VOID;
                }
            }
        }, Value.VOID);
    }

    @Override // com.xiaopeng.speech.IQueryInjector
    public boolean isQueryInject(final String str) {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IQueryInjector, Boolean>() { // from class: com.xiaopeng.speech.proxy.QueryInjectorProxy.4
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IQueryInjector iQueryInjector) {
                try {
                    return Boolean.valueOf(iQueryInjector.isQueryInject(str));
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }, false)).booleanValue();
    }

    @Override // com.xiaopeng.speech.IQueryInjector
    public Value queryApiRouterData(final String str, final String str2) {
        return (Value) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IQueryInjector, Value>() { // from class: com.xiaopeng.speech.proxy.QueryInjectorProxy.5
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Value run(IQueryInjector iQueryInjector) {
                try {
                    return iQueryInjector.queryApiRouterData(str, str2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return Value.VOID;
                }
            }
        }, Value.VOID);
    }
}
