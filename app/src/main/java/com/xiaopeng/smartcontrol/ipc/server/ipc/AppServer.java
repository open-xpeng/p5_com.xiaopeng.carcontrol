package com.xiaopeng.smartcontrol.ipc.server.ipc;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.xiaopeng.ipc.aidl.IPipeBusListener;
import com.xiaopeng.smartcontrol.ipc.server.ipc.IpcServer;
import com.xiaopeng.smartcontrol.utils.Apps;
import com.xiaopeng.smartcontrol.utils.Constants;
import com.xiaopeng.smartcontrol.utils.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class AppServer implements IpcServer {
    private static final String TAG = "AppServer";
    private static final ArrayList<PipeListenerRecord> mPipeListeners = new ArrayList<>();
    private IpcServer.IpcCallback mListener;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingletonHolder {
        private static final AppServer INSTANCE = new AppServer();

        private SingletonHolder() {
        }
    }

    public static AppServer get() {
        return SingletonHolder.INSTANCE;
    }

    private AppServer() {
    }

    public void init(Context context) {
        LogUtils.i(TAG, "init");
        try {
            ServiceManager.addService(Constants.SDK_BINDER_PREFFIX + Apps.getAppId(context.getApplicationContext()), new AidlServiceBinder());
            LogUtils.i(TAG, "addService success");
        } catch (Throwable th) {
            th.printStackTrace();
            LogUtils.i(TAG, "addService failed, start service self");
            Intent intent = new Intent(Constants.BIND_SERVICE_ACTION);
            intent.setPackage(context.getPackageName());
            context.startService(intent);
        }
    }

    public void addListener(PipeListenerRecord pipeListenerRecord) {
        LogUtils.i(TAG, "addListener " + pipeListenerRecord);
        ArrayList<PipeListenerRecord> arrayList = mPipeListeners;
        synchronized (arrayList) {
            arrayList.add(pipeListenerRecord);
        }
    }

    public PipeListenerRecord removeListener(int i) {
        PipeListenerRecord remove;
        LogUtils.i(TAG, "removeListener " + i);
        ArrayList<PipeListenerRecord> arrayList = mPipeListeners;
        synchronized (arrayList) {
            remove = arrayList.remove(i);
        }
        return remove;
    }

    public int findIndexOfPipeListenerLocked(IPipeBusListener iPipeBusListener) {
        ArrayList<PipeListenerRecord> arrayList = mPipeListeners;
        synchronized (arrayList) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (mPipeListeners.get(size).listener.asBinder() == iPipeBusListener.asBinder()) {
                    return size;
                }
            }
            return -1;
        }
    }

    @Override // com.xiaopeng.smartcontrol.ipc.server.ipc.IpcServer
    public void send(String str, String str2, String str3) {
        LogUtils.i(TAG, String.format("send %s %s %s", str, str2, str3));
        ArrayList<PipeListenerRecord> arrayList = mPipeListeners;
        synchronized (arrayList) {
            LogUtils.i(TAG, "listeners size: " + arrayList.size());
            Iterator<PipeListenerRecord> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().send(str, str2, str3);
            }
        }
    }

    @Override // com.xiaopeng.smartcontrol.ipc.server.ipc.IpcServer
    public String onCall(String str, String str2, String str3) {
        LogUtils.i(TAG, String.format("onCall %s %s %s", str, str2, str3));
        IpcServer.IpcCallback ipcCallback = this.mListener;
        if (ipcCallback == null) {
            LogUtils.i(TAG, "listeners null");
            return null;
        }
        return ipcCallback.onCall(str, str2, str3);
    }

    @Override // com.xiaopeng.smartcontrol.ipc.server.ipc.IpcServer
    public void setIpcCallback(IpcServer.IpcCallback ipcCallback) {
        this.mListener = ipcCallback;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class PipeListenerRecord implements IBinder.DeathRecipient {
        IPipeBusListener listener;
        final int pid;
        final int uid;

        /* JADX INFO: Access modifiers changed from: package-private */
        public PipeListenerRecord(IPipeBusListener iPipeBusListener, int i, int i2) {
            this.listener = iPipeBusListener;
            this.pid = i;
            this.uid = i2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            LogUtils.i(AppServer.TAG, "binderDied");
            synchronized (AppServer.mPipeListeners) {
                AppServer.mPipeListeners.remove(this);
            }
            IPipeBusListener iPipeBusListener = this.listener;
            if (iPipeBusListener != null) {
                try {
                    iPipeBusListener.asBinder().unlinkToDeath(this, 0);
                    this.listener = null;
                } catch (Exception unused) {
                }
            }
        }

        public void send(String str, String str2, String str3) {
            LogUtils.i(AppServer.TAG, String.format("send %s %s %s", str, str2, str3));
            IPipeBusListener iPipeBusListener = this.listener;
            if (iPipeBusListener != null) {
                try {
                    iPipeBusListener.onPipeBusEvent(str, str2, str3);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
