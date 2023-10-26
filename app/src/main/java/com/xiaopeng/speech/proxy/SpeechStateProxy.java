package com.xiaopeng.speech.proxy;

import android.os.IBinder;
import android.os.RemoteException;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.ISpeechEngine;
import com.xiaopeng.speech.ISpeechState;
import com.xiaopeng.speech.common.util.IPCRunner;
import com.xiaopeng.speech.common.util.LogUtils;

/* loaded from: classes2.dex */
public class SpeechStateProxy extends ISpeechState.Stub implements ConnectManager.OnConnectCallback {
    private IPCRunner<ISpeechState> mIpcRunner = new IPCRunner<>("SpeechStateProxy");
    private ISpeechState mSpeechState;

    @Override // com.xiaopeng.speech.ISpeechState
    public boolean isInitOK() {
        ISpeechState iSpeechState = this.mSpeechState;
        if (iSpeechState != null) {
            try {
                return iSpeechState.isInitOK();
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public void setPhoneCallStatus(final int i) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<ISpeechState, Object>() { // from class: com.xiaopeng.speech.proxy.SpeechStateProxy.1
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(ISpeechState iSpeechState) throws RemoteException {
                iSpeechState.setPhoneCallStatus(i);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public void setPhoneCallStatusWithBinder(final IBinder iBinder, final int i) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<ISpeechState, Object>() { // from class: com.xiaopeng.speech.proxy.SpeechStateProxy.2
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(ISpeechState iSpeechState) throws RemoteException {
                iSpeechState.setPhoneCallStatusWithBinder(iBinder, i);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public int getPhoneCallStatus() {
        ISpeechState iSpeechState = this.mSpeechState;
        if (iSpeechState != null) {
            try {
                return iSpeechState.getPhoneCallStatus();
            } catch (RemoteException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public void setShowSpeechDialog(final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<ISpeechState, Object>() { // from class: com.xiaopeng.speech.proxy.SpeechStateProxy.3
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(ISpeechState iSpeechState) throws RemoteException {
                iSpeechState.setShowSpeechDialog(z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public boolean isShowSpeechDialog() {
        ISpeechState iSpeechState = this.mSpeechState;
        if (iSpeechState != null) {
            try {
                return iSpeechState.isShowSpeechDialog();
            } catch (RemoteException e) {
                e.printStackTrace();
                return true;
            }
        }
        return true;
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
        try {
            if (iSpeechEngine.getSpeechState() != null) {
                ISpeechState speechState = iSpeechEngine.getSpeechState();
                this.mSpeechState = speechState;
                this.mIpcRunner.setProxy(speechState);
                this.mIpcRunner.fetchAll();
            } else {
                LogUtils.e("SpeechStateProxy", "isInitOK false, speechEngine.getSpeechState() = null");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
        this.mIpcRunner.setProxy(null);
        this.mSpeechState = null;
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public void setOOBEStatus(final int i) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<ISpeechState, Object>() { // from class: com.xiaopeng.speech.proxy.SpeechStateProxy.4
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(ISpeechState iSpeechState) throws RemoteException {
                iSpeechState.setOOBEStatus(i);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public int getOOBEStatus() {
        ISpeechState iSpeechState = this.mSpeechState;
        if (iSpeechState != null) {
            try {
                return iSpeechState.getOOBEStatus();
            } catch (RemoteException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return -1;
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public void setCanExitFlag(final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<ISpeechState, Object>() { // from class: com.xiaopeng.speech.proxy.SpeechStateProxy.5
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(ISpeechState iSpeechState) throws RemoteException {
                iSpeechState.setCanExitFlag(z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public boolean canExit() {
        ISpeechState iSpeechState = this.mSpeechState;
        if (iSpeechState != null) {
            try {
                return iSpeechState.canExit();
            } catch (RemoteException e) {
                e.printStackTrace();
                return true;
            }
        }
        return true;
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public boolean isDMStarted() {
        ISpeechState iSpeechState = this.mSpeechState;
        if (iSpeechState != null) {
            try {
                return iSpeechState.isDMStarted();
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public void changeCurrentTTS(final String str) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<ISpeechState, Object>() { // from class: com.xiaopeng.speech.proxy.SpeechStateProxy.6
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(ISpeechState iSpeechState) throws RemoteException {
                iSpeechState.changeCurrentTTS(str);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public String getCurrentTTS() {
        ISpeechState iSpeechState = this.mSpeechState;
        if (iSpeechState != null) {
            try {
                return iSpeechState.getCurrentTTS();
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public void setFuncState(final int i, final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<ISpeechState, Object>() { // from class: com.xiaopeng.speech.proxy.SpeechStateProxy.7
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(ISpeechState iSpeechState) throws RemoteException {
                iSpeechState.setFuncState(i, z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public boolean getFuncState(int i) {
        ISpeechState iSpeechState = this.mSpeechState;
        if (iSpeechState != null) {
            try {
                return iSpeechState.getFuncState(i);
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public boolean isStateWaiting() {
        ISpeechState iSpeechState = this.mSpeechState;
        if (iSpeechState != null) {
            try {
                return iSpeechState.isStateWaiting();
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public boolean onWaiting() {
        ISpeechState iSpeechState = this.mSpeechState;
        if (iSpeechState != null) {
            try {
                return iSpeechState.onWaiting();
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public boolean isDMEndByPOISelect() {
        ISpeechState iSpeechState = this.mSpeechState;
        if (iSpeechState != null) {
            try {
                return iSpeechState.isDMEndByPOISelect();
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public boolean isHotWordEngineReady() {
        ISpeechState iSpeechState = this.mSpeechState;
        if (iSpeechState != null) {
            try {
                return iSpeechState.isHotWordEngineReady();
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.speech.ISpeechState
    public boolean isMicrophoneMute() {
        ISpeechState iSpeechState = this.mSpeechState;
        if (iSpeechState != null) {
            try {
                return iSpeechState.isMicrophoneMute();
            } catch (RemoteException e) {
                e.printStackTrace();
                return true;
            }
        }
        return true;
    }
}
