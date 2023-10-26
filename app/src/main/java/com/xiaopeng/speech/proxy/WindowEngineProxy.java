package com.xiaopeng.speech.proxy;

import android.os.RemoteException;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.ISpeechEngine;
import com.xiaopeng.speech.IWindowEngine;
import com.xiaopeng.speech.common.util.IPCRunner;
import com.xiaopeng.speech.common.util.LogUtils;

/* loaded from: classes2.dex */
public class WindowEngineProxy extends IWindowEngine.Stub implements ConnectManager.OnConnectCallback {
    private static final String TAG = "WindowEngineProxy";
    private IPCRunner<IWindowEngine> mIPCRunner = new IPCRunner<>(TAG);
    private IWindowEngine mWindowEngine;

    @Override // com.xiaopeng.speech.IWindowEngine
    public int getShowingWindow() {
        IWindowEngine iWindowEngine = this.mWindowEngine;
        if (iWindowEngine != null) {
            try {
                return iWindowEngine.getShowingWindow();
            } catch (RemoteException e) {
                LogUtils.e(TAG, e != null ? e.getMessage() : "null");
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.speech.IWindowEngine
    public boolean isShowingWindow(int i) {
        IWindowEngine iWindowEngine = this.mWindowEngine;
        if (iWindowEngine != null) {
            try {
                return iWindowEngine.isShowingWindow(i);
            } catch (RemoteException e) {
                LogUtils.e(TAG, e != null ? e.getMessage() : "null");
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.speech.IWindowEngine
    public void closeWindow(int i) {
        IWindowEngine iWindowEngine = this.mWindowEngine;
        if (iWindowEngine != null) {
            try {
                iWindowEngine.closeWindow(i);
            } catch (RemoteException e) {
                LogUtils.e(TAG, e != null ? e.getMessage() : "null");
            }
        }
    }

    @Override // com.xiaopeng.speech.IWindowEngine
    public void onWindowAttached(final int i) {
        this.mIPCRunner.runFunc(new IPCRunner.IIPCFunc<IWindowEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WindowEngineProxy.1
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWindowEngine iWindowEngine) throws RemoteException {
                iWindowEngine.onWindowAttached(i);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.IWindowEngine
    public void onWindowDetached(final int i) {
        this.mIPCRunner.runFunc(new IPCRunner.IIPCFunc<IWindowEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WindowEngineProxy.2
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWindowEngine iWindowEngine) throws RemoteException {
                iWindowEngine.onWindowDetached(i);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.IWindowEngine
    public void setShowingWindow(int i, boolean z) {
        IWindowEngine iWindowEngine = this.mWindowEngine;
        if (iWindowEngine != null) {
            try {
                iWindowEngine.setShowingWindow(i, z);
            } catch (RemoteException e) {
                LogUtils.e(TAG, e != null ? e.getMessage() : "null");
            }
        }
    }

    @Override // com.xiaopeng.speech.IWindowEngine
    public void setAnimState(final int i, final int i2) {
        this.mIPCRunner.runFunc(new IPCRunner.IIPCFunc<IWindowEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WindowEngineProxy.3
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWindowEngine iWindowEngine) throws RemoteException {
                iWindowEngine.setAnimState(i, i2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
        if (iSpeechEngine != null) {
            try {
                IWindowEngine windowEngine = iSpeechEngine.getWindowEngine();
                this.mWindowEngine = windowEngine;
                this.mIPCRunner.setProxy(windowEngine);
                this.mIPCRunner.fetchAll();
            } catch (RemoteException e) {
                LogUtils.e(TAG, e != null ? e.getMessage() : "null");
            }
        }
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
        this.mIPCRunner.setProxy(null);
        this.mWindowEngine = null;
    }
}
