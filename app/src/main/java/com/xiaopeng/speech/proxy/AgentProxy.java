package com.xiaopeng.speech.proxy;

import android.os.IBinder;
import android.os.RemoteException;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.ISpeechEngine;
import com.xiaopeng.speech.common.bean.SliceData;
import com.xiaopeng.speech.common.util.IPCRunner;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.common.util.WorkerHandler;
import com.xiaopeng.speech.coreapi.IAgent;
import com.xiaopeng.speech.coreapi.ISpeechConfigCallback;

/* loaded from: classes2.dex */
public class AgentProxy extends IAgent.Stub implements ConnectManager.OnConnectCallback {
    private volatile IAgent agent;
    private IPCRunner<IAgent> mIpcRunner = new IPCRunner<>("AgentProxy");
    private final String TAG = "AgentProxy";

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
        try {
            IAgent agent = iSpeechEngine.getAgent();
            this.agent = agent;
            this.mIpcRunner.setProxy(agent);
            this.mIpcRunner.fetchAll();
        } catch (RemoteException e) {
            LogUtils.e(this, "onConnect exception ", e);
        }
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
        this.agent = null;
        this.mIpcRunner.setProxy(null);
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void sendText(final String str) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.1
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.sendText(str);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void sendEvent(final String str, final String str2) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.2
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.sendEvent(str, str2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void feedbackResult(final String str, final String str2) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.3
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.feedbackResult(str, str2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void updateDeviceInfo(final String str) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.4
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.updateDeviceInfo(str);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void triggerIntent(final String str, final String str2, final String str3, final String str4) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.5
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.triggerIntent(str, str2, str3, str4);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void updateVocab(final String str, final String[] strArr, final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.6
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.updateVocab(str, strArr, z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void sendUIEvent(final String str, final String str2) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.7
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.sendUIEvent(str, str2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    @Deprecated
    public void setASRInterruptEnabled(final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.8
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.setASRInterruptEnabled(z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    @Deprecated
    public boolean isEnableASRInterrupt() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Boolean>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.9
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IAgent iAgent) throws RemoteException {
                return Boolean.valueOf(iAgent.isEnableASRInterrupt());
            }
        }, false)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void sendScript(final String str) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.10
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.sendScript(str);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    @Deprecated
    public boolean isDefaultEnableASRInterrupt() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Boolean>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.11
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IAgent iAgent) throws RemoteException {
                return Boolean.valueOf(iAgent.isDefaultEnableASRInterrupt());
            }
        }, false)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    @Deprecated
    public void setDefaultASRInterruptEnabled(final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.12
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.setDefaultASRInterruptEnabled(z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void setDefaultWelcomeEnabled(final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.13
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.setDefaultWelcomeEnabled(z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public boolean isDefaultEnableWelcome() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Boolean>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.14
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IAgent iAgent) throws RemoteException {
                return Boolean.valueOf(iAgent.isDefaultEnableWelcome());
            }
        }, false)).booleanValue();
    }

    public void setHandler(WorkerHandler workerHandler) {
        this.mIpcRunner.setWorkerHandler(workerHandler);
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void sendThirdCMD(final String str) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.15
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.sendThirdCMD(str);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void triggerEvent(final String str, final String str2) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.16
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.triggerEvent(str, str2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void setUseWheelVoiceButton(final IBinder iBinder, final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, String>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.17
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IAgent iAgent) throws RemoteException {
                iAgent.setUseWheelVoiceButton(iBinder, z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public boolean isWheelVoiceButtonEnable() {
        IAgent iAgent = this.agent;
        if (iAgent != null) {
            try {
                return iAgent.isWheelVoiceButtonEnable();
            } catch (Throwable th) {
                LogUtils.e("AgentProxy", "remote error: ", th);
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public String getRecommendData(final String str) {
        return (String) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, String>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.18
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IAgent iAgent) throws RemoteException {
                return iAgent.getRecommendData(str);
            }
        }, "");
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public String getSkillData(final String str) {
        return (String) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, String>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.19
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IAgent iAgent) throws RemoteException {
                return iAgent.getSkillData(str);
            }
        }, "");
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void setSkillData(final String str) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, String>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.20
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IAgent iAgent) throws RemoteException {
                iAgent.setSkillData(str);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public String getConfigData(final String str) {
        return (String) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, String>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.21
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IAgent iAgent) throws RemoteException {
                return iAgent.getConfigData(str);
            }
        }, "");
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void setConfigData(final String str, final String str2) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, String>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.22
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IAgent iAgent) throws RemoteException {
                iAgent.setConfigData(str, str2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void sendInfoFlowStatData(final int i, final String str) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.23
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.sendInfoFlowStatData(i, str);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void sendApiRoute(final String str, final String str2) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, String>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.24
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IAgent iAgent) throws RemoteException {
                iAgent.sendApiRoute(str, str2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public String getMessageBoxData(final String str, final String str2) {
        return (String) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, String>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.25
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IAgent iAgent) throws RemoteException {
                return iAgent.getMessageBoxData(str, str2);
            }
        }, "");
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void sendSceneData(final String str, final String str2, final String str3, final String str4, final String str5) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, String>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.26
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IAgent iAgent) throws RemoteException {
                iAgent.sendSceneData(str, str2, str3, str4, str5);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void sendInfoFlowCardState(final String str, final int i) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, String>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.27
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IAgent iAgent) throws RemoteException {
                iAgent.sendInfoFlowCardState(str, i);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void uploadContacts(final String str, final String str2, final int i) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, String>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.28
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IAgent iAgent) throws RemoteException {
                iAgent.uploadContacts(str, str2, i);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void uploadContact(final String str, final SliceData sliceData, final int i) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, String>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.29
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IAgent iAgent) throws RemoteException {
                iAgent.uploadContact(str, sliceData, i);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void triggerIntentWithBinder(final IBinder iBinder, final String str, final String str2, final String str3, final String str4) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.30
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.triggerIntentWithBinder(iBinder, str, str2, str3, str4);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IAgent
    public void setConfigDataWithCallback(final String str, final String str2, final ISpeechConfigCallback iSpeechConfigCallback) throws RemoteException {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IAgent, Object>() { // from class: com.xiaopeng.speech.proxy.AgentProxy.31
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IAgent iAgent) throws RemoteException {
                iAgent.setConfigDataWithCallback(str, str2, iSpeechConfigCallback);
                return null;
            }
        });
    }
}
