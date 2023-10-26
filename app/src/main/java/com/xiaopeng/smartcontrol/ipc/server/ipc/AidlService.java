package com.xiaopeng.smartcontrol.ipc.server.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.xiaopeng.smartcontrol.utils.LogUtils;

/* loaded from: classes2.dex */
public class AidlService extends Service {
    private static final String TAG = "AidlService";

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }

    @Override // android.app.Service
    public void onCreate() {
        LogUtils.i(TAG, "onCreate");
        super.onCreate();
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogUtils.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        LogUtils.i(TAG, "onBind");
        return AidlServiceBinder.get();
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        LogUtils.i(TAG, "onUnbind");
        return super.onUnbind(intent);
    }
}
