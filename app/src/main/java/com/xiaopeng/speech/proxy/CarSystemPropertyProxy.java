package com.xiaopeng.speech.proxy;

import android.os.RemoteException;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.ICarSystemProperty;
import com.xiaopeng.speech.ISpeechEngine;
import com.xiaopeng.speech.common.util.IPCRunner;
import com.xiaopeng.speech.common.util.LogUtils;

/* loaded from: classes2.dex */
public class CarSystemPropertyProxy extends ICarSystemProperty.Stub implements ConnectManager.OnConnectCallback {
    private static final String TAG = "CarSystemPropertyProxy";
    private IPCRunner<ICarSystemProperty> mIpcRunner = new IPCRunner<>(TAG);

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
        try {
            LogUtils.d(TAG, "onConnect.");
            this.mIpcRunner.setProxy(iSpeechEngine.getCarSystemProperty());
            this.mIpcRunner.fetchAll();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
        LogUtils.d(TAG, "onDisconnect.");
        this.mIpcRunner.setProxy(null);
    }

    @Override // com.xiaopeng.speech.ICarSystemProperty
    public String getCarType() {
        return (String) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<ICarSystemProperty, String>() { // from class: com.xiaopeng.speech.proxy.CarSystemPropertyProxy.1
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(ICarSystemProperty iCarSystemProperty) throws RemoteException {
                return iCarSystemProperty.getCarType();
            }
        }, "");
    }

    @Override // com.xiaopeng.speech.ICarSystemProperty
    public String getOverseasCarType() {
        return (String) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<ICarSystemProperty, String>() { // from class: com.xiaopeng.speech.proxy.CarSystemPropertyProxy.2
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(ICarSystemProperty iCarSystemProperty) throws RemoteException {
                return iCarSystemProperty.getOverseasCarType();
            }
        }, "");
    }
}
