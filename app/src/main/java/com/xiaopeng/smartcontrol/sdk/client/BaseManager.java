package com.xiaopeng.smartcontrol.sdk.client;

import com.xiaopeng.smartcontrol.ipc.client.ClientManager;
import com.xiaopeng.smartcontrol.sdk.base.AbsBaseManager;
import com.xiaopeng.smartcontrol.sdk.client.BaseManager.IBaseSignalReceiver;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes2.dex */
public abstract class BaseManager<T extends IBaseSignalReceiver> extends AbsBaseManager {
    protected CopyOnWriteArraySet<T> mCallback = new CopyOnWriteArraySet<>();
    protected CopyOnWriteArraySet<IRawSignalReceiver> mRawCallback = new CopyOnWriteArraySet<>();
    protected final String TAG = getClass().getSimpleName();

    /* loaded from: classes2.dex */
    public interface IBaseSignalReceiver {
    }

    /* loaded from: classes2.dex */
    public interface IRawSignalReceiver {
        void onIpcReceived(String str, String str2);
    }

    protected abstract String getAppId();

    protected abstract String getModuleName();

    public abstract void onIpcReceived(String str, String str2);

    public BaseManager() {
        ClientManager.get().bindService(getAppId());
    }

    public void addSignalCallback(T t) {
        this.mCallback.add(t);
    }

    public void addRawSignalCallback(IRawSignalReceiver iRawSignalReceiver) {
        this.mRawCallback.add(iRawSignalReceiver);
    }

    public void removeSignalCallback(T t) {
        this.mCallback.remove(t);
    }

    public void clearSignalCallback() {
        this.mCallback.clear();
    }

    public void removeRawSignalCallback(T t) {
        this.mRawCallback.remove(t);
    }

    public void clearRawSignalCallback() {
        this.mRawCallback.clear();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String callIpc(String str, Object obj) {
        return ClientManager.get().call(getAppId(), getModuleName(), str, String.valueOf(obj));
    }

    protected final void callIpcAsync(String str, String str2, Object obj) {
        ClientManager.get().callAsync(str, getAppId(), getModuleName(), str2, String.valueOf(obj));
    }
}
