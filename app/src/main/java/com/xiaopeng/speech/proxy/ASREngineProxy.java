package com.xiaopeng.speech.proxy;

import android.os.RemoteException;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.ISpeechEngine;
import com.xiaopeng.speech.common.util.IPCRunner;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.common.util.WorkerHandler;
import com.xiaopeng.speech.coreapi.IASREngine;

/* loaded from: classes2.dex */
public class ASREngineProxy extends IASREngine.Stub implements ConnectManager.OnConnectCallback {
    private IPCRunner<IASREngine> mIpcRunner = new IPCRunner<>("ASREngineProxy");

    public void setHandler(WorkerHandler workerHandler) {
        this.mIpcRunner.setWorkerHandler(workerHandler);
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
        try {
            this.mIpcRunner.setProxy(iSpeechEngine.getASREngine());
            this.mIpcRunner.fetchAll();
        } catch (RemoteException e) {
            LogUtils.e(this, "onConnect exception ", e);
        }
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
        this.mIpcRunner.setProxy(null);
    }

    @Override // com.xiaopeng.speech.coreapi.IASREngine
    public void setASRInterruptEnabled(final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IASREngine, Object>() { // from class: com.xiaopeng.speech.proxy.ASREngineProxy.1
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IASREngine iASREngine) throws RemoteException {
                iASREngine.setASRInterruptEnabled(z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IASREngine
    public boolean isEnableASRInterrupt() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IASREngine, Boolean>() { // from class: com.xiaopeng.speech.proxy.ASREngineProxy.2
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IASREngine iASREngine) throws RemoteException {
                return Boolean.valueOf(iASREngine.isEnableASRInterrupt());
            }
        }, false)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IASREngine
    public boolean isDefaultEnableASRInterrupt() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IASREngine, Boolean>() { // from class: com.xiaopeng.speech.proxy.ASREngineProxy.3
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IASREngine iASREngine) throws RemoteException {
                return Boolean.valueOf(iASREngine.isDefaultEnableASRInterrupt());
            }
        }, false)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IASREngine
    public void setDefaultASRInterruptEnabled(final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IASREngine, Object>() { // from class: com.xiaopeng.speech.proxy.ASREngineProxy.4
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IASREngine iASREngine) throws RemoteException {
                iASREngine.setDefaultASRInterruptEnabled(z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IASREngine
    public void startListen() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IASREngine, Object>() { // from class: com.xiaopeng.speech.proxy.ASREngineProxy.5
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IASREngine iASREngine) throws RemoteException {
                iASREngine.startListen();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IASREngine
    public void stopListen() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IASREngine, Object>() { // from class: com.xiaopeng.speech.proxy.ASREngineProxy.6
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IASREngine iASREngine) throws RemoteException {
                iASREngine.stopListen();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IASREngine
    public void cancelListen() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IASREngine, Object>() { // from class: com.xiaopeng.speech.proxy.ASREngineProxy.7
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IASREngine iASREngine) throws RemoteException {
                iASREngine.cancelListen();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IASREngine
    public void setVadTimeout(final long j) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IASREngine, Object>() { // from class: com.xiaopeng.speech.proxy.ASREngineProxy.8
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IASREngine iASREngine) throws RemoteException {
                iASREngine.setVadTimeout(j);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IASREngine
    public void setVadPauseTime(final long j) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IASREngine, Object>() { // from class: com.xiaopeng.speech.proxy.ASREngineProxy.9
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IASREngine iASREngine) throws RemoteException {
                iASREngine.setVadPauseTime(j);
                return null;
            }
        });
    }
}
