package com.xiaopeng.system.delegate.module;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.UserHandle;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.system.delegate.ISystemDelegateInterface;

/* loaded from: classes2.dex */
class SystemDelegateBinder {
    private static final String TAG = "SystemDelegateBinder";
    private static volatile SystemDelegateBinder sInstance;
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.xiaopeng.system.delegate.module.SystemDelegateBinder.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtils.d(SystemDelegateBinder.TAG, "onServiceConnected");
            SystemDelegateBinder.this.mRemoteService = ISystemDelegateInterface.Stub.asInterface(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils.d(SystemDelegateBinder.TAG, "onServiceDisconnected");
            SystemDelegateBinder.this.mRemoteService = null;
        }
    };
    private ISystemDelegateInterface mRemoteService;

    private SystemDelegateBinder() {
    }

    static SystemDelegateBinder getInstance() {
        if (sInstance == null) {
            synchronized (SystemDelegateBinder.class) {
                if (sInstance == null) {
                    sInstance = new SystemDelegateBinder();
                }
            }
        }
        return sInstance;
    }

    void init(Context context) {
        LogUtils.d(TAG, "init threadId:" + Thread.currentThread().getId());
        Intent intent = new Intent("com.xiaopeng.service.SYSTEM_DELEGATE");
        intent.setPackage("com.xiaopeng.system.delegate");
        context.startServiceAsUser(intent, UserHandle.CURRENT);
        context.bindServiceAsUser(intent, this.mConnection, 1, UserHandle.CURRENT);
    }

    boolean isConnected() {
        return this.mRemoteService != null;
    }
}
