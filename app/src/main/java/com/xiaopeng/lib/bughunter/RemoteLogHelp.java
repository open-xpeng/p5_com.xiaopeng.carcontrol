package com.xiaopeng.lib.bughunter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import com.xiaopeng.lib.bughunter.IRemoteLogInterface;
import com.xiaopeng.lib.utils.LogUtils;

/* loaded from: classes2.dex */
public class RemoteLogHelp {
    private static final String DATA_COLLECTOR_PACKAGE_NAME = "com.xiaopeng.data.collector";
    private static final String DATA_COLLECTOT_DATA_SERVICE = "com.xiaopeng.service.DATA_SERVICE";
    private static final String TAG = "RemoteLogHelp";
    private static volatile RemoteLogHelp mInstance;
    private Context mContext;
    private IRemoteLogInterface mRemoteLogService;
    private ServiceConnection mServiceConnection = new ServiceConnection() { // from class: com.xiaopeng.lib.bughunter.RemoteLogHelp.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            RemoteLogHelp.this.mRemoteLogService = IRemoteLogInterface.Stub.asInterface(iBinder);
            IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() { // from class: com.xiaopeng.lib.bughunter.RemoteLogHelp.1.1
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    if (RemoteLogHelp.this.mRemoteLogService == null) {
                        return;
                    }
                    RemoteLogHelp.this.mRemoteLogService.asBinder().unlinkToDeath(this, 0);
                    RemoteLogHelp.this.mRemoteLogService = null;
                    RemoteLogHelp.this.binderService(RemoteLogHelp.this.mContext);
                }
            };
            try {
                if (RemoteLogHelp.this.mRemoteLogService == null) {
                    return;
                }
                RemoteLogHelp.this.mRemoteLogService.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                LogUtils.w(RemoteLogHelp.TAG, "RemoteException occurs when reLink to Service, exception:", e);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            RemoteLogHelp.this.mRemoteLogService = null;
        }
    };

    private RemoteLogHelp() {
    }

    public static RemoteLogHelp getInstance() {
        if (mInstance == null) {
            synchronized (RemoteLogHelp.class) {
                if (mInstance == null) {
                    mInstance = new RemoteLogHelp();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        binderService(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void binderService(Context context) {
        Intent intent = new Intent();
        intent.setPackage("com.xiaopeng.data.collector");
        intent.setAction(DATA_COLLECTOT_DATA_SERVICE);
        if (19 <= Build.VERSION.SDK_INT) {
            context.startService(intent);
            context.bindService(intent, this.mServiceConnection, 1);
            return;
        }
        context.startServiceAsUser(intent, UserHandle.CURRENT);
        context.bindServiceAsUser(intent, this.mServiceConnection, 1, UserHandle.CURRENT);
    }

    public boolean isServiceConnected() {
        return this.mRemoteLogService != null;
    }

    public void uploadRemoteLog(String str) {
        IRemoteLogInterface iRemoteLogInterface = this.mRemoteLogService;
        if (iRemoteLogInterface != null) {
            try {
                iRemoteLogInterface.uploadRemoteLog(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
