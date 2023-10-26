package com.xiaopeng.carcontrol.download;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class DownloadService extends Service {
    private static final String TAG = "DownloadService";
    private final DownloadServiceImpl downloadServiceImpl = new DownloadServiceImpl(this);

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i(TAG, "onStartCommand " + intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.downloadServiceImpl;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.downloadServiceImpl.onCreate();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        this.downloadServiceImpl.onDestroy();
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
