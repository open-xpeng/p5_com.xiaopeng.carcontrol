package com.alibaba.mtl.appmonitor;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/* loaded from: classes.dex */
public class AppMonitorService extends Service {
    IMonitor a = null;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (this.a == null) {
            this.a = new Monitor(getApplication());
        }
        return (IBinder) this.a;
    }

    @Override // android.app.Service
    public void onDestroy() {
        IMonitor iMonitor = this.a;
        if (iMonitor != null) {
            try {
                iMonitor.triggerUpload();
            } catch (RemoteException unused) {
            }
        }
        super.onDestroy();
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onLowMemory() {
        IMonitor iMonitor = this.a;
        if (iMonitor != null) {
            try {
                iMonitor.triggerUpload();
            } catch (RemoteException unused) {
            }
        }
        super.onLowMemory();
    }
}
