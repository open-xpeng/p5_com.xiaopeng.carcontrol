package com.xiaopeng.appstore.storeprovider.store;

import android.os.RemoteException;
import android.util.Log;
import com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceDownloadInfo;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class RMDownloadCallbackListenerToService extends IRMDownloadCallback.Stub {
    private static final String TAG = "RMDownloadCallbackListenerToService";
    private WeakReference<StoreResourceProvider> mStoreResourceProvider;

    @Override // com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback
    public void basicTypes(int i, long j, boolean z, float f, double d, String str) throws RemoteException {
    }

    public RMDownloadCallbackListenerToService(StoreResourceProvider storeResourceProvider) {
        this.mStoreResourceProvider = new WeakReference<>(storeResourceProvider);
    }

    @Override // com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback
    public void onDownloadCallback(int i, ResourceDownloadInfo resourceDownloadInfo) {
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider.get();
        if (storeResourceProvider != null) {
            storeResourceProvider.dispatchDownloadCallback(i, resourceDownloadInfo);
        } else {
            Log.d(TAG, "storeResourceProvider is null");
        }
    }

    @Override // com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback
    public void onMenuOpenCallback(String str) {
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider.get();
        if (storeResourceProvider != null) {
            storeResourceProvider.dispatchMenuOpenCallback(str);
        } else {
            Log.d(TAG, "storeResourceProvider is null");
        }
    }

    @Override // com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback
    public void unbindService() {
        StoreResourceProvider storeResourceProvider = this.mStoreResourceProvider.get();
        if (storeResourceProvider != null) {
            storeResourceProvider.unbindService();
        } else {
            Log.d(TAG, "storeResourceProvider is null");
        }
    }
}
