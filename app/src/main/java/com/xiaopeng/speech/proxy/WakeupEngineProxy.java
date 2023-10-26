package com.xiaopeng.speech.proxy;

import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.ISpeechEngine;
import com.xiaopeng.speech.common.bean.DisableInfoBean;
import com.xiaopeng.speech.common.util.IPCRunner;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.common.util.WorkerHandler;
import com.xiaopeng.speech.coreapi.IWakeupEngine;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class WakeupEngineProxy extends IWakeupEngine.Stub implements ConnectManager.OnConnectCallback {
    private static final String TAG = "WakeupEngineProxy";
    private IPCRunner<IWakeupEngine> mIpcRunner = new IPCRunner<>(TAG);
    private Map<String, DisableInfoBean> disableInfoCache = new ConcurrentHashMap();

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
        try {
            this.mIpcRunner.setProxy(iSpeechEngine.getWakeupEngine());
            this.mIpcRunner.fetchAll();
            LogUtils.i(TAG, "reset:   onConnect");
            resumeCarSpeechStatus();
        } catch (RemoteException e) {
            LogUtils.e(this, "onConnect exception ", e);
        }
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
        this.mIpcRunner.setProxy(null);
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void startDialog() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.1
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.startDialog();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void stopDialog() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.2
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.stopDialog();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void avatarClick(final String str) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.3
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.avatarClick(str);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void avatarPress() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.4
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.avatarPress();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void avatarRelease() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.5
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.avatarRelease();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableWakeup() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.6
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.enableWakeup();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableWakeup() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.7
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.disableWakeup();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public String[] getWakeupWords() {
        return (String[]) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, String[]>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.8
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String[] run(IWakeupEngine iWakeupEngine) throws RemoteException {
                return iWakeupEngine.getWakeupWords();
            }
        }, new String[0]);
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void updateMinorWakeupWord(final String str, final String str2, final String str3, final String[] strArr) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.9
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.updateMinorWakeupWord(str, str2, str3, strArr);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public String getMinorWakeupWord() {
        return (String) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, String>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.10
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public String run(IWakeupEngine iWakeupEngine) throws RemoteException {
                return iWakeupEngine.getMinorWakeupWord();
            }
        }, null);
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void updateCommandWakeupWord(final String[] strArr, final String[] strArr2, final String[] strArr3, final String[] strArr4, final String[] strArr5) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.11
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.updateCommandWakeupWord(strArr, strArr2, strArr3, strArr4, strArr5);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void clearCommandWakeupWord() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.12
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.clearCommandWakeupWord();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void addCommandWakeupWord(final String[] strArr, final String[] strArr2, final String[] strArr3, final String[] strArr4, final String[] strArr5) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.13
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.addCommandWakeupWord(strArr, strArr2, strArr3, strArr4, strArr5);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void removeCommandWakeupWord(final String[] strArr) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.14
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.removeCommandWakeupWord(strArr);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void updateShortcutWakeupWord(final String[] strArr, final String[] strArr2, final String[] strArr3) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.15
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.updateShortcutWakeupWord(strArr, strArr2, strArr3);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void clearShortCutWakeupWord() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.16
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.clearShortCutWakeupWord();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void addShortcutWakeupWord(final String[] strArr, final String[] strArr2, final String[] strArr3) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.17
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.addShortcutWakeupWord(strArr, strArr2, strArr3);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void removeShortcutWakeupWord(final String[] strArr) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.18
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.removeShortcutWakeupWord(strArr);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void pauseDialog() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.19
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.pauseDialog();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void resumeDialog() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.20
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.resumeDialog();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public boolean isEnableWakeup() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Boolean>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.21
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IWakeupEngine iWakeupEngine) throws RemoteException {
                return Boolean.valueOf(iWakeupEngine.isEnableWakeup());
            }
        }, false)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public boolean isDefaultEnableWakeup() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Boolean>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.22
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IWakeupEngine iWakeupEngine) throws RemoteException {
                return Boolean.valueOf(iWakeupEngine.isDefaultEnableWakeup());
            }
        }, true)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void setDefaultWakeupEnabled(final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.23
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.setDefaultWakeupEnabled(z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableWakeupEnhance(final IBinder iBinder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.24
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.enableWakeupEnhance(iWakeupEngine.asBinder());
                    return null;
                }
                iWakeupEngine.enableWakeupEnhance(iBinder2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableWakeupEnhance(final IBinder iBinder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.25
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.disableWakeupEnhance(iWakeupEngine.asBinder());
                    return null;
                }
                iWakeupEngine.disableWakeupEnhance(iBinder2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public boolean isWheelWakeupEnabled() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Boolean>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.26
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IWakeupEngine iWakeupEngine) throws RemoteException {
                return Boolean.valueOf(iWakeupEngine.isWheelWakeupEnabled());
            }
        }, true)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void setWheelWakeupEnabled(final IBinder iBinder, final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.27
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.setWheelWakeupEnabled(iWakeupEngine.asBinder(), z);
                    return null;
                }
                iWakeupEngine.setWheelWakeupEnabled(iBinder2, z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public boolean isDefaultEnableOneshot() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Boolean>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.28
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IWakeupEngine iWakeupEngine) throws RemoteException {
                return Boolean.valueOf(iWakeupEngine.isDefaultEnableOneshot());
            }
        }, false)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void setDefaultOneshotEnabled(final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.29
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.setDefaultOneshotEnabled(z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableOneshot() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.30
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.enableOneshot();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableOneshot() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.31
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.disableOneshot();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public boolean isDefaultEnableFastWake() {
        return ((Boolean) this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Boolean>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.32
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Boolean run(IWakeupEngine iWakeupEngine) throws RemoteException {
                return Boolean.valueOf(iWakeupEngine.isDefaultEnableFastWake());
            }
        }, false)).booleanValue();
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void setDefaultFastWakeEnabled(final boolean z) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.33
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.setDefaultFastWakeEnabled(z);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableFastWake() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.34
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.enableFastWake();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableFastWake() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.35
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.disableFastWake();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void stopDialogMessage() {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.36
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.stopDialogMessage();
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void stopDialogReason(final String str) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.37
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.stopDialogReason(str);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableMainWakeupWord(final IBinder iBinder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.38
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.enableMainWakeupWord(iWakeupEngine.asBinder());
                    return null;
                }
                iWakeupEngine.enableMainWakeupWord(iBinder2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableMainWakeupWord(final IBinder iBinder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.39
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.disableMainWakeupWord(iWakeupEngine.asBinder());
                    return null;
                }
                iWakeupEngine.disableMainWakeupWord(iBinder2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableFastWakeEnhance(final IBinder iBinder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.40
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.enableFastWakeEnhance(iWakeupEngine.asBinder());
                    return null;
                }
                iWakeupEngine.enableFastWakeEnhance(iBinder2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableFastWakeEnhance(final IBinder iBinder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.41
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.disableFastWakeEnhance(iWakeupEngine.asBinder());
                    return null;
                }
                iWakeupEngine.disableFastWakeEnhance(iBinder2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableInterruptWake(final IBinder iBinder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.42
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.enableInterruptWake(iWakeupEngine.asBinder());
                    return null;
                }
                iWakeupEngine.enableInterruptWake(iBinder2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableInterruptWake(final IBinder iBinder) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.43
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.disableInterruptWake(iWakeupEngine.asBinder());
                    return null;
                }
                iWakeupEngine.disableInterruptWake(iBinder2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void startDialogFrom(final String str) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.44
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                iWakeupEngine.startDialogFrom(str);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableWakeupWithInfo(final IBinder iBinder, final int i, final String str, final String str2, final int i2) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.45
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.disableWakeupWithInfo(iWakeupEngine.asBinder(), i, str, str2, i2);
                } else {
                    iWakeupEngine.disableWakeupWithInfo(iBinder2, i, str, str2, i2);
                }
                WakeupEngineProxy wakeupEngineProxy = WakeupEngineProxy.this;
                IBinder iBinder3 = iBinder;
                wakeupEngineProxy.setDisableInfoCache(iBinder3 == null ? iWakeupEngine.asBinder() : iBinder3, i, str, str2, i2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableWakeupWithInfo(final IBinder iBinder, final int i, final String str, final int i2) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.46
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.enableWakeupWithInfo(iWakeupEngine.asBinder(), i, str, i2);
                } else {
                    iWakeupEngine.enableWakeupWithInfo(iBinder2, i, str, i2);
                }
                WakeupEngineProxy wakeupEngineProxy = WakeupEngineProxy.this;
                IBinder iBinder3 = iBinder;
                if (iBinder3 == null) {
                    iBinder3 = iWakeupEngine.asBinder();
                }
                wakeupEngineProxy.removeDisableInfoCache(iBinder3, i, str);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void disableWheelWakeupWithInfo(final IBinder iBinder, final String str, final String str2, final int i) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.47
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.disableWheelWakeupWithInfo(iWakeupEngine.asBinder(), str, str2, i);
                } else {
                    iWakeupEngine.disableWheelWakeupWithInfo(iBinder2, str, str2, i);
                }
                WakeupEngineProxy wakeupEngineProxy = WakeupEngineProxy.this;
                IBinder iBinder3 = iBinder;
                wakeupEngineProxy.setDisableInfoCache(iBinder3 == null ? iWakeupEngine.asBinder() : iBinder3, -1, str, str2, i);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void enableWheelWakeupWithInfo(final IBinder iBinder, final String str, final int i) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.48
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.enableWheelWakeupWithInfo(iWakeupEngine.asBinder(), str, i);
                } else {
                    iWakeupEngine.enableWheelWakeupWithInfo(iBinder2, str, i);
                }
                WakeupEngineProxy wakeupEngineProxy = WakeupEngineProxy.this;
                IBinder iBinder3 = iBinder;
                if (iBinder3 == null) {
                    iBinder3 = iWakeupEngine.asBinder();
                }
                wakeupEngineProxy.removeDisableInfoCache(iBinder3, -1, str);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void suspendDialogWithReason(final IBinder iBinder, final String str, final String str2) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.49
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.suspendDialogWithReason(iWakeupEngine.asBinder(), str, str2);
                    return null;
                }
                iWakeupEngine.suspendDialogWithReason(iBinder2, str, str2);
                return null;
            }
        });
    }

    @Override // com.xiaopeng.speech.coreapi.IWakeupEngine
    public void resumeDialogWithReason(final IBinder iBinder, final String str, final String str2) {
        this.mIpcRunner.runFunc(new IPCRunner.IIPCFunc<IWakeupEngine, Object>() { // from class: com.xiaopeng.speech.proxy.WakeupEngineProxy.50
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IWakeupEngine iWakeupEngine) throws RemoteException {
                IBinder iBinder2 = iBinder;
                if (iBinder2 == null) {
                    iWakeupEngine.resumeDialogWithReason(iWakeupEngine.asBinder(), str, str2);
                    return null;
                }
                iWakeupEngine.resumeDialogWithReason(iBinder2, str, str2);
                return null;
            }
        });
    }

    public void setHandler(WorkerHandler workerHandler) {
        this.mIpcRunner.setWorkerHandler(workerHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisableInfoCache(IBinder iBinder, int i, String str, String str2, int i2) {
        String generateKey = generateKey(iBinder, i, str);
        LogUtils.i(TAG, "setDisableInfoCache :  " + generateKey);
        if (TextUtils.isEmpty(generateKey)) {
            return;
        }
        DisableInfoBean disableInfoBean = new DisableInfoBean(iBinder, i, str, str2, i2);
        if (this.disableInfoCache.containsKey(generateKey)) {
            return;
        }
        LogUtils.i(TAG, "put data  :  " + generateKey + ": " + disableInfoBean.toString());
        this.disableInfoCache.put(generateKey, disableInfoBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeDisableInfoCache(IBinder iBinder, int i, String str) {
        String generateKey = generateKey(iBinder, i, str);
        LogUtils.i(TAG, "removeDisableInfoCache :  " + generateKey);
        if (TextUtils.isEmpty(generateKey) || !this.disableInfoCache.containsKey(generateKey)) {
            return;
        }
        LogUtils.i(TAG, "remove  :  " + generateKey + ": " + this.disableInfoCache.size());
        this.disableInfoCache.remove(generateKey);
        LogUtils.i(TAG, "remove after :  " + generateKey + ": " + this.disableInfoCache.size());
    }

    private String generateKey(IBinder iBinder, int i, String str) {
        if (iBinder != null) {
            String obj = iBinder.toString();
            if (i == -1) {
                return obj + "_" + str;
            }
            return obj + "_" + i + "_" + str;
        }
        return null;
    }

    private void resumeCarSpeechStatus() {
        LogUtils.i(TAG, "resumeCarSpeechStatus  disableInfoCache size " + this.disableInfoCache.size());
        if (this.disableInfoCache.size() > 0) {
            for (Map.Entry<String, DisableInfoBean> entry : this.disableInfoCache.entrySet()) {
                DisableInfoBean value = entry.getValue();
                if (value != null) {
                    LogUtils.i(TAG, "disable from cache:    = ====  " + value.toString());
                    if (value.getType() == -1) {
                        disableWheelWakeupWithInfo(value.getBinder(), value.getByWho(), value.getInfo(), value.getNotifyType());
                    } else {
                        disableWakeupWithInfo(value.getBinder(), value.getType(), value.getByWho(), value.getInfo(), value.getNotifyType());
                    }
                }
            }
        }
    }
}
