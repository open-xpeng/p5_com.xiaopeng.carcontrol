package com.xiaopeng.smartcontrol.ipc.server.ipc;

import android.os.Binder;
import android.os.RemoteException;
import com.xiaopeng.ipc.aidl.IPipeBus;
import com.xiaopeng.ipc.aidl.IPipeBusListener;
import com.xiaopeng.smartcontrol.ipc.server.ipc.AppServer;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public class AidlServiceBinder extends IPipeBus.Stub {
    private static final String TAG = "AidlServiceBinder";
    private final Object mLock = new Object();

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final AidlServiceBinder INSTANCE = new AidlServiceBinder();

        private SingletonHolder() {
        }
    }

    public static AidlServiceBinder get() {
        return SingletonHolder.INSTANCE;
    }

    @Override // com.xiaopeng.ipc.aidl.IPipeBus
    public int call(String str, String str2, String str3, String[] strArr) throws RemoteException {
        LogUtils.i(TAG, String.format("onCall = %s %s %s", str, str2, str3));
        String onCall = AppServer.get().onCall(str, str2, str3);
        if (strArr != null && strArr.length > 0) {
            strArr[0] = onCall;
        }
        return onCall != null ? 0 : -1;
    }

    @Override // com.xiaopeng.ipc.aidl.IPipeBus
    public void registerListener(IPipeBusListener iPipeBusListener) throws RemoteException {
        String str = TAG;
        LogUtils.i(str, "registerListener=" + iPipeBusListener);
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        synchronized (this.mLock) {
            if (AppServer.get().findIndexOfPipeListenerLocked(iPipeBusListener) != -1) {
                LogUtils.w(str, "PipeListener is already added, ignoring");
                return;
            }
            AppServer.PipeListenerRecord pipeListenerRecord = new AppServer.PipeListenerRecord(iPipeBusListener, callingPid, callingUid);
            try {
                iPipeBusListener.asBinder().linkToDeath(pipeListenerRecord, 0);
                AppServer.get().addListener(pipeListenerRecord);
            } catch (RemoteException unused) {
                LogUtils.e(TAG, "PipeListener is dead, ignoring it");
            }
        }
    }

    @Override // com.xiaopeng.ipc.aidl.IPipeBus
    public void unRegisterListener(IPipeBusListener iPipeBusListener) throws RemoteException {
        AppServer.PipeListenerRecord removeListener;
        LogUtils.i(TAG, "unRegisterListener=" + iPipeBusListener);
        synchronized (this.mLock) {
            int findIndexOfPipeListenerLocked = AppServer.get().findIndexOfPipeListenerLocked(iPipeBusListener);
            if (findIndexOfPipeListenerLocked != -1 && (removeListener = AppServer.get().removeListener(findIndexOfPipeListenerLocked)) != null) {
                try {
                    removeListener.listener.asBinder().unlinkToDeath(removeListener, 0);
                    removeListener.listener = null;
                } catch (Exception unused) {
                }
            }
        }
    }
}
