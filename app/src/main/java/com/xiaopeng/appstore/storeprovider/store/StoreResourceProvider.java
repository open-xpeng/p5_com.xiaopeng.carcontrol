package com.xiaopeng.appstore.storeprovider.store;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.xiaopeng.appstore.storeprovider.store.IResourceService;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceBean;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceContainerBean;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceDownloadInfo;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class StoreResourceProvider {
    public static final long INVALID_DOWNLOAD_BYTES = -1;
    public static final long INVALID_DOWNLOAD_ID = -1;
    public static final int INVALID_DOWNLOAD_STATUS = -1;
    private static final int STATE_CONNECTED = 2;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_DISCONNECTED = 0;
    private static final String STORE_RESOURCE_MANAGER_SERVICE = "com.xiaopeng.resourceservice";
    public static final String STORE_RESOURCE_MANAGER_SERVICE_INTERFACE_NAME = "com.xiaopeng.appstore.resourceservice.ResourceService";
    private static final String TAG = "StoreResourceProvider";
    private static volatile StoreResourceProvider sResourceManager;
    private int mConnectionState;
    private final Context mContext;
    private IResourceService mService;
    private ServiceConnection mServiceConnectionListenerClient;
    private final Object mResourceServiceReady = new Object();
    private final ServiceConnection mServiceConnectionListener = new ServiceConnection() { // from class: com.xiaopeng.appstore.storeprovider.store.StoreResourceProvider.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(StoreResourceProvider.TAG, "service connected, Service.  1");
            Log.d(StoreResourceProvider.TAG, "service connected, Service.  2");
            StoreResourceProvider.this.mService = IResourceService.Stub.asInterface(iBinder);
            synchronized (StoreResourceProvider.this.mResourceServiceReady) {
                Log.d(StoreResourceProvider.TAG, "service connected, Service.  3");
                StoreResourceProvider.this.mConnectionState = 2;
                StoreResourceProvider.this.mResourceServiceReady.notifyAll();
                try {
                    StoreResourceProvider.this.mService.registerDownloadListener(StoreResourceProvider.this.mIRMDownloadCallback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Log.d(StoreResourceProvider.TAG, "service connected, Service = " + StoreResourceProvider.this.mService + " state = " + StoreResourceProvider.this.mConnectionState);
                if (StoreResourceProvider.this.mServiceConnectionListenerClient != null) {
                    StoreResourceProvider.this.mServiceConnectionListenerClient.onServiceConnected(componentName, iBinder);
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(StoreResourceProvider.TAG, "onServiceDisconnected, " + componentName);
            synchronized (StoreResourceProvider.this) {
                StoreResourceProvider.this.mService = null;
                if (StoreResourceProvider.this.mConnectionState == 0) {
                    Log.w(StoreResourceProvider.TAG, "onServiceDisconnected, already disconnected.");
                    return;
                }
                StoreResourceProvider.this.mConnectionState = 0;
                if (StoreResourceProvider.this.mServiceConnectionListenerClient != null) {
                    StoreResourceProvider.this.mServiceConnectionListenerClient.onServiceDisconnected(componentName);
                }
            }
        }
    };
    private IRMDownloadCallback mIRMDownloadCallback = new RMDownloadCallbackListenerToService(this);
    private Set<RMDownloadListener> mClientRMDownloadListeners = new HashSet();

    public StoreResourceProvider(Context context) {
        this.mContext = context;
    }

    boolean isResourceServiceConnected() {
        synchronized (this.mResourceServiceReady) {
            while (this.mConnectionState != 2) {
                try {
                    Log.d(TAG, "Waiting Resource service connected, mConnectionState = " + this.mConnectionState);
                    startResourceManagerService();
                    this.mResourceServiceReady.wait();
                } catch (InterruptedException e) {
                    Log.d(TAG, "Waiting Resource service connected, InterruptedException " + e);
                    Thread.currentThread().interrupt();
                }
            }
        }
        return true;
    }

    public void setServiceConnectionListenerClient(ServiceConnection serviceConnection) {
        this.mServiceConnectionListenerClient = serviceConnection;
    }

    private void startResourceManagerService() {
        Intent intent = new Intent();
        intent.setClassName("com.xiaopeng.resourceservice", STORE_RESOURCE_MANAGER_SERVICE_INTERFACE_NAME);
        this.mContext.bindService(intent, this.mServiceConnectionListener, 1);
    }

    public ResourceContainerBean queryResourceData(String str) {
        try {
            isResourceServiceConnected();
            return this.mService.queryResourceData(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ResourceDownloadInfo> queryDownloadInfo(String[] strArr) {
        try {
            isResourceServiceConnected();
            return this.mService.queryDownloadInfo(strArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void registerDownloadListener(RMDownloadListener rMDownloadListener) {
        if (rMDownloadListener != null && !this.mClientRMDownloadListeners.contains(rMDownloadListener)) {
            this.mClientRMDownloadListeners.add(rMDownloadListener);
        } else {
            Log.i(TAG, "registerDownloadListener, ignore this listener:" + rMDownloadListener);
        }
    }

    public void unregisterDownloadListener(RMDownloadListener rMDownloadListener) {
        if (rMDownloadListener != null) {
            this.mClientRMDownloadListeners.remove(rMDownloadListener);
        }
    }

    public boolean start(ResourceBean resourceBean) {
        Log.d(TAG, "start resourceBean:" + resourceBean);
        try {
            isResourceServiceConnected();
            return this.mService.start(resourceBean);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Deprecated
    public boolean resume(String str) {
        Log.d(TAG, "resume resourceId：" + str);
        try {
            isResourceServiceConnected();
            return this.mService.resume(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Deprecated
    public boolean remove(String str) {
        Log.d(TAG, "remove resourceId：" + str);
        try {
            isResourceServiceConnected();
            return this.mService.remove(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Deprecated
    public boolean pause(String str) {
        Log.d(TAG, "pause resourceId：" + str);
        try {
            isResourceServiceConnected();
            return this.mService.pause(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean resumeResDownload(String str, String str2) {
        Log.d(TAG, "resumeResDownload resType:" + str + ", resourceId：" + str2);
        try {
            isResourceServiceConnected();
            return this.mService.resumeResDownload(str, str2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelResDownload(String str, String str2) {
        Log.d(TAG, "cancelResDownload resType:" + str + ", resourceId：" + str2);
        try {
            isResourceServiceConnected();
            return this.mService.cancelResDownload(str, str2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean pauseResDownload(String str, String str2) {
        Log.d(TAG, "pauseResDownload resType:" + str + ", resourceId：" + str2);
        try {
            isResourceServiceConnected();
            return this.mService.pauseResDownload(str, str2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean resumeDownload(String str) {
        Log.d(TAG, "resumeDownload url:" + str);
        try {
            isResourceServiceConnected();
            return this.mService.resumeDownload(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelDownload(String str) {
        Log.d(TAG, "cancelDownload url:" + str);
        try {
            isResourceServiceConnected();
            return this.mService.cancelDownload(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean pauseDownload(String str) {
        Log.d(TAG, "pauseDownload url:" + str);
        try {
            isResourceServiceConnected();
            return this.mService.pauseDownload(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public long enqueue(String str, String str2) {
        Log.d(TAG, "enqueue url : " + str + "  title = " + str2);
        try {
            isResourceServiceConnected();
            return this.mService.enqueue(str, str2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public List<ResourceDownloadInfo> queryAllInfo() {
        List<ResourceDownloadInfo> list;
        Log.d(TAG, "queryAllInfo");
        try {
            isResourceServiceConnected();
            list = this.mService.queryAllInfo();
        } catch (RemoteException e) {
            e.printStackTrace();
            list = null;
        }
        Log.d(TAG, "queryAllInfo " + list);
        return list;
    }

    public int fetchDownloadStatus(long j) {
        Log.d(TAG, "fetchDownloadStatusById id : " + j);
        try {
            isResourceServiceConnected();
            return this.mService.fetchDownloadStatusById(j);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int fetchDownloadStatus(String str) {
        Log.d(TAG, "fetchDownloadStatusByUrl url : " + str);
        try {
            isResourceServiceConnected();
            return this.mService.fetchDownloadStatusByUrl(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getDownloadStatus(String str) {
        Log.d(TAG, "getDownloadStatusByUrl url : " + str);
        try {
            isResourceServiceConnected();
            return this.mService.getDownloadStatusByUrl(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getDownloadStatus(long j) {
        Log.d(TAG, "getDownloadStatusById id : " + j);
        try {
            isResourceServiceConnected();
            return this.mService.getDownloadStatusById(j);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long getTotalBytes(long j) {
        Log.d(TAG, "getTotalBytesById id : " + j);
        try {
            isResourceServiceConnected();
            return this.mService.getTotalBytesById(j);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public long getTotalBytes(String str) {
        Log.d(TAG, "getDownloadStatusById url : " + str);
        try {
            isResourceServiceConnected();
            return this.mService.getTotalBytesByUrl(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public long getDownloadedBytes(long j) {
        Log.d(TAG, "getDownloadedBytesById id : " + j);
        try {
            isResourceServiceConnected();
            return this.mService.getDownloadedBytesById(j);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public long getDownloadedBytes(String str) {
        Log.d(TAG, "getDownloadedBytesByUrl url : " + str);
        try {
            isResourceServiceConnected();
            return this.mService.getDownloadedBytesByUrl(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public void removeLocalData(long j) {
        Log.d(TAG, "removeLocalData id : " + j);
        try {
            isResourceServiceConnected();
            this.mService.removeLocalDataById(j);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void removeLocalData(String str) {
        Log.d(TAG, "removeLocalData url : " + str);
        try {
            isResourceServiceConnected();
            this.mService.removeLocalDataByUrl(str);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public File getLocalFile(String str) {
        Log.d(TAG, "getLocalFileByUrl url : " + str);
        try {
            isResourceServiceConnected();
            String localFilePath = this.mService.getLocalFilePath(str);
            if (localFilePath != null) {
                return new File(localFilePath);
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void dispatchDownloadCallback(int i, ResourceDownloadInfo resourceDownloadInfo) {
        Set<RMDownloadListener> set = this.mClientRMDownloadListeners;
        if (set != null) {
            for (RMDownloadListener rMDownloadListener : set) {
                rMDownloadListener.onDownloadCallback(i, resourceDownloadInfo);
            }
        }
    }

    public void dispatchMenuOpenCallback(String str) {
        Set<RMDownloadListener> set = this.mClientRMDownloadListeners;
        if (set != null) {
            for (RMDownloadListener rMDownloadListener : set) {
                rMDownloadListener.onMenuOpenCallback(str);
            }
        }
    }

    public void unbindService() {
        Set<RMDownloadListener> set = this.mClientRMDownloadListeners;
        if (set != null) {
            for (RMDownloadListener rMDownloadListener : set) {
                rMDownloadListener.unbindService();
            }
        }
        Log.d(TAG, "unbindService, disconnect  automatically");
        disconnect();
    }

    public void connect() {
        synchronized (this) {
            if (this.mConnectionState != 0) {
                throw new IllegalStateException("already connected or connecting");
            }
            this.mConnectionState = 1;
            startResourceManagerService();
        }
    }

    public void disconnect() {
        synchronized (this) {
            if (this.mConnectionState == 0) {
                return;
            }
            try {
                this.mService.unregisterDownloadListener(this.mIRMDownloadCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.mService = null;
            this.mConnectionState = 0;
            this.mContext.unbindService(this.mServiceConnectionListener);
        }
    }

    public void releaseService() {
        if (this.mService != null) {
            isResourceServiceConnected();
            try {
                this.mService.unregisterDownloadListener(this.mIRMDownloadCallback);
                return;
            } catch (RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        Log.w(TAG, "releaseService, service is null.");
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this) {
            z = this.mService != null;
        }
        return z;
    }

    public boolean isConnecting() {
        boolean z;
        synchronized (this) {
            z = true;
            if (this.mConnectionState != 1) {
                z = false;
            }
        }
        return z;
    }
}
