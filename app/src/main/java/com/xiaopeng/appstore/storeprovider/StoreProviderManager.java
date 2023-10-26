package com.xiaopeng.appstore.storeprovider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Log;
import com.xiaopeng.appstore.storeprovider.IAssembleListener;
import com.xiaopeng.appstore.storeprovider.IResourceServiceV2;
import com.xiaopeng.appstore.storeprovider.StoreProviderManager;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public class StoreProviderManager {
    public static final String PACKAGE_NAME = "com.xiaopeng.resourceservice";
    private static final long RELEASE_DELAY = 10000;
    public static final String SERVICE = "com.xiaopeng.appstore.resourceservice.ResourceServiceV2";
    private static final String TAG = "StoreProviderManager";
    private static final long WAIT_CONNECTED_TIMEOUT = 45000;
    private static String sCallingPackageName;
    private static volatile StoreProviderManager sInstance;
    private Context mContext;
    private HandlerThread mHandlerThread;
    private IResourceServiceV2 mResourceService;
    private Handler mWorkHandler;
    private final ServiceConnection mServiceConnection = new AnonymousClass1();
    private volatile boolean mIsConnected = false;
    private final Object mConnectLock = new Object();
    private volatile boolean mIsConnecting = false;
    private final Set<RemoteCallbackCookie> mListenerCookies = new ArraySet();
    private final Object mListenerLock = new Object();
    private final Runnable mTryToBindAction = new Runnable() { // from class: com.xiaopeng.appstore.storeprovider.-$$Lambda$StoreProviderManager$a5dFp5lu2s2wOTpBQq81MnDtzG0
        @Override // java.lang.Runnable
        public final void run() {
            StoreProviderManager.this.tryToBind();
        }
    };
    private final Runnable mTryToReleaseAction = new Runnable() { // from class: com.xiaopeng.appstore.storeprovider.-$$Lambda$StoreProviderManager$g_FdIBk6qD0tfKwcjwwbFZTfrKM
        @Override // java.lang.Runnable
        public final void run() {
            StoreProviderManager.this.tryToRelease();
        }
    };

    public static StoreProviderManager get() {
        if (sInstance == null) {
            synchronized (StoreProviderManager.class) {
                if (sInstance == null) {
                    sInstance = new StoreProviderManager();
                }
            }
        }
        return sInstance;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.appstore.storeprovider.StoreProviderManager$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements ServiceConnection {
        AnonymousClass1() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, final IBinder iBinder) {
            Log.i(StoreProviderManager.TAG, "onServiceConnected: " + componentName);
            StoreProviderManager.this.mWorkHandler.post(new Runnable() { // from class: com.xiaopeng.appstore.storeprovider.-$$Lambda$StoreProviderManager$1$j0TZ192gR7W2oyKU6mFR-aRp7lg
                @Override // java.lang.Runnable
                public final void run() {
                    StoreProviderManager.AnonymousClass1.this.lambda$onServiceConnected$0$StoreProviderManager$1(iBinder);
                }
            });
        }

        public /* synthetic */ void lambda$onServiceConnected$0$StoreProviderManager$1(IBinder iBinder) {
            StoreProviderManager.this.initInternal(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(StoreProviderManager.TAG, "onServiceDisconnected: " + componentName);
            StoreProviderManager.this.mWorkHandler.post(StoreProviderManager.this.mTryToReleaseAction);
        }
    }

    public void initialize(Context context) {
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null && handlerThread.isAlive()) {
            Log.w(TAG, "Already initialized! Old:" + this.mContext + ", new:" + context);
            return;
        }
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        sCallingPackageName = applicationContext.getPackageName();
        HandlerThread handlerThread2 = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread2;
        handlerThread2.start();
        this.mWorkHandler = new Handler(this.mHandlerThread.getLooper());
    }

    public void release() {
        this.mListenerCookies.clear();
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        this.mContext = null;
    }

    public static void setFakeCalling(String str) {
        sCallingPackageName = str;
    }

    public void startObserve() {
        assertContextValid();
        scheduleToBind();
    }

    public void stopObserve() {
        assertContextValid();
        scheduleToRelease();
    }

    Context getContext() {
        return this.mContext;
    }

    private void scheduleToBind() {
        Log.i(TAG, "scheduleToBind");
        cancelSchedule();
        this.mWorkHandler.post(this.mTryToBindAction);
    }

    private void scheduleToRelease() {
        Log.i(TAG, "scheduleToRelease");
        this.mWorkHandler.postDelayed(this.mTryToReleaseAction, RELEASE_DELAY);
    }

    private void cancelSchedule() {
        this.mWorkHandler.removeCallbacks(this.mTryToReleaseAction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryToBind() {
        synchronized (this.mConnectLock) {
            if (!this.mIsConnected && !this.mIsConnecting) {
                bindService();
            } else {
                Log.i(TAG, "tryToBind: bind already.");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryToRelease() {
        synchronized (this.mConnectLock) {
            if (this.mIsConnected) {
                releaseInternal();
            } else {
                Log.i(TAG, "tryToRelease: not connected.");
            }
        }
    }

    private void bindService() {
        Context context = this.mContext;
        Log.d(TAG, "bindService, " + context + ", pn:com.xiaopeng.resourceservice");
        assertContextValid();
        this.mIsConnecting = true;
        Intent intent = new Intent();
        intent.setClassName("com.xiaopeng.resourceservice", "com.xiaopeng.appstore.resourceservice.ResourceServiceV2");
        context.bindService(intent, this.mServiceConnection, 1);
    }

    private void unbindService(Context context) {
        Log.d(TAG, "unbindService: " + context);
        context.getApplicationContext().unbindService(this.mServiceConnection);
    }

    private boolean waitConnected() {
        synchronized (this.mConnectLock) {
            while (!this.mIsConnected) {
                try {
                    this.mConnectLock.wait(WAIT_CONNECTED_TIMEOUT);
                } catch (InterruptedException e) {
                    Log.w(TAG, "waitConnected ex:" + e);
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isConnectedWait() {
        boolean z;
        synchronized (this.mConnectLock) {
            if (!this.mIsConnected) {
                try {
                    this.mConnectLock.wait(WAIT_CONNECTED_TIMEOUT);
                } catch (InterruptedException e) {
                    Log.w(TAG, "waitConnected ex:" + e);
                    Thread.currentThread().interrupt();
                }
            }
            z = this.mIsConnected;
        }
        return z;
    }

    private void notifyConnected() {
        synchronized (this.mConnectLock) {
            this.mIsConnecting = false;
            this.mIsConnected = true;
            this.mConnectLock.notifyAll();
        }
    }

    private void notifyDisconnected() {
        synchronized (this.mConnectLock) {
            this.mIsConnected = false;
            this.mIsConnecting = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initInternal(IBinder iBinder) {
        Log.d(TAG, "init: service:" + this.mContext);
        assertContextValid();
        this.mResourceService = IResourceServiceV2.Stub.asInterface(iBinder);
        synchronized (this.mListenerLock) {
            if (!this.mListenerCookies.isEmpty()) {
                for (RemoteCallbackCookie remoteCallbackCookie : this.mListenerCookies) {
                    try {
                        Log.i(TAG, "Register remote callback in init:" + remoteCallbackCookie);
                        this.mResourceService.registerAssembleListener(remoteCallbackCookie.resType, remoteCallbackCookie.callingPackage, remoteCallbackCookie.listenerWrapper);
                    } catch (RemoteException e) {
                        Log.w(TAG, "init, registerCallback ex:" + e);
                    }
                }
            }
        }
        notifyConnected();
        Log.d(TAG, "init: service END:" + this.mContext);
    }

    private void releaseInternal() {
        Log.d(TAG, "release");
        if (this.mResourceService != null) {
            for (RemoteCallbackCookie remoteCallbackCookie : this.mListenerCookies) {
                try {
                    Log.i(TAG, "Unregister remote callback in release:" + remoteCallbackCookie);
                    this.mResourceService.unregisterAssembleListener(remoteCallbackCookie.listenerWrapper);
                } catch (RemoteException e) {
                    Log.w(TAG, "release, unregisterAssembleListener ex:" + e);
                }
            }
        }
        unbindService(this.mContext);
        notifyDisconnected();
        this.mResourceService = null;
        Log.d(TAG, "release end");
    }

    public ResourceContainer query(final ResourceRequest resourceRequest) {
        Log.d(TAG, "query start:" + resourceRequest);
        return (ResourceContainer) executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.storeprovider.-$$Lambda$StoreProviderManager$LLnm7yBDNIla3x49c4fRoxmBKSY
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return StoreProviderManager.this.lambda$query$0$StoreProviderManager(resourceRequest);
            }
        });
    }

    public /* synthetic */ ResourceContainer lambda$query$0$StoreProviderManager(ResourceRequest resourceRequest) throws Exception {
        IResourceServiceV2 iResourceServiceV2 = this.mResourceService;
        if (iResourceServiceV2 != null) {
            try {
                return iResourceServiceV2.query(sCallingPackageName, resourceRequest);
            } catch (RemoteException e) {
                Log.w(TAG, "query ex:" + e);
                return null;
            }
        }
        return null;
    }

    public AssembleResult assemble(AssembleRequest assembleRequest) {
        return assemble(assembleRequest, sCallingPackageName);
    }

    public AssembleResult assemble(final AssembleRequest assembleRequest, final String str) {
        Log.d(TAG, "assemble start:" + assembleRequest + ", calling:" + str);
        return (AssembleResult) executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.storeprovider.-$$Lambda$StoreProviderManager$vqg-JYFTGCLOt6UPi3kKcuMW4M8
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return StoreProviderManager.this.lambda$assemble$1$StoreProviderManager(assembleRequest, str);
            }
        });
    }

    public /* synthetic */ AssembleResult lambda$assemble$1$StoreProviderManager(AssembleRequest assembleRequest, String str) throws Exception {
        IResourceServiceV2 iResourceServiceV2 = this.mResourceService;
        if (iResourceServiceV2 != null) {
            try {
                if (assembleRequest instanceof EnqueueRequest) {
                    return iResourceServiceV2.assembleEnqueue(str, (EnqueueRequest) assembleRequest);
                }
                if (assembleRequest instanceof SimpleRequest) {
                    return iResourceServiceV2.assembleAction(str, (SimpleRequest) assembleRequest);
                }
                Log.w(TAG, "assemble: not support request:" + assembleRequest);
                return null;
            } catch (RemoteException e) {
                Log.w(TAG, "assemble ex:" + e);
                return null;
            }
        }
        return null;
    }

    public List<AssembleInfo> getAssembleInfoList() {
        return getAssembleInfoList(-1, sCallingPackageName);
    }

    public List<AssembleInfo> getAssembleInfoList(int i) {
        return getAssembleInfoList(i, null);
    }

    public List<AssembleInfo> getAssembleInfoList(final int i, final String str) {
        List<AssembleInfo> list = (List) executeWaitConnected(new Callable() { // from class: com.xiaopeng.appstore.storeprovider.-$$Lambda$StoreProviderManager$sPNH1ZT9ZQNO0rSW7B7n_Xx77Zk
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return StoreProviderManager.this.lambda$getAssembleInfoList$2$StoreProviderManager(i, str);
            }
        });
        Log.d(TAG, "getAssembleInfoList:" + list);
        return list;
    }

    public /* synthetic */ List lambda$getAssembleInfoList$2$StoreProviderManager(int i, String str) throws Exception {
        if (this.mResourceService != null) {
            Log.d(TAG, "getAssembleInfoList start, resType:" + i + ", calling:" + str);
            try {
                return this.mResourceService.getAssembleInfoList(i, str);
            } catch (RemoteException e) {
                Log.w(TAG, "getAssembleInfoList ex:" + e);
                return null;
            }
        }
        return null;
    }

    public void registerListener(int i, IAssembleClientListener iAssembleClientListener) {
        registerListener(i, sCallingPackageName, iAssembleClientListener);
    }

    public void registerListener(int i, String str, IAssembleClientListener iAssembleClientListener) {
        if (iAssembleClientListener == null) {
            return;
        }
        if (this.mResourceService != null) {
            Log.i(TAG, "registerListener, register remoteCallback for resType:" + i + ", calling:" + str + ", lis:" + iAssembleClientListener);
            try {
                this.mResourceService.registerAssembleListener(i, str, new RemoteCallbackWrapper(iAssembleClientListener));
            } catch (RemoteException e) {
                Log.w(TAG, "registerListener, registerAssembleListener ex:" + e);
            }
        } else {
            Log.i(TAG, "registerListener, not connected yet, add to pending, resType:" + i + ", calling:" + str + ", lis:" + iAssembleClientListener);
        }
        synchronized (this.mListenerLock) {
            Log.i(TAG, "registerListener, add to list, lis:" + iAssembleClientListener + ", list:" + this.mListenerCookies);
            RemoteCallbackCookie remoteCallbackCookie = new RemoteCallbackCookie(null);
            remoteCallbackCookie.callingPackage = str;
            remoteCallbackCookie.resType = i;
            remoteCallbackCookie.listenerWrapper = new RemoteCallbackWrapper(iAssembleClientListener);
            this.mListenerCookies.add(remoteCallbackCookie);
        }
    }

    public void unregisterListener(IAssembleClientListener iAssembleClientListener) {
        if (iAssembleClientListener == null) {
            return;
        }
        synchronized (this.mListenerLock) {
            Iterator<RemoteCallbackCookie> it = this.mListenerCookies.iterator();
            while (it.hasNext()) {
                RemoteCallbackCookie next = it.next();
                if (next.listenerWrapper.clientListener == iAssembleClientListener) {
                    Log.i(TAG, "unregisterListener, unregisterListener remoteCallback now:" + next);
                    IResourceServiceV2 iResourceServiceV2 = this.mResourceService;
                    if (iResourceServiceV2 != null) {
                        try {
                            iResourceServiceV2.unregisterAssembleListener(next.listenerWrapper);
                        } catch (RemoteException e) {
                            Log.w(TAG, "unregisterListener, registerAssembleListener ex:" + e);
                        }
                    }
                    it.remove();
                }
            }
        }
    }

    private void assertContextValid() {
        if (this.mContext == null) {
            throw new IllegalStateException("initialize(Context context) should be called first.");
        }
    }

    private boolean tryConnected() {
        boolean z;
        synchronized (this.mConnectLock) {
            boolean z2 = this.mIsConnected;
            if (!z2 || this.mResourceService == null) {
                if (!this.mIsConnecting) {
                    bindService();
                }
                z2 = isConnectedWait();
            }
            z = z2 && this.mResourceService != null;
        }
        return z;
    }

    private <T> T executeWaitConnected(Callable<T> callable) {
        T t;
        Log.d(TAG, "executeWaitConnected");
        synchronized (this.mConnectLock) {
            boolean z = this.mIsConnected;
            if (z && this.mResourceService != null) {
                Log.d(TAG, "executeWaitConnected: already connected");
                t = null;
                if (!z && this.mResourceService != null) {
                    try {
                        t = callable.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.w(TAG, "executeWaitConnected error, callback ex:" + e);
                    }
                } else {
                    Log.w(TAG, "executeWaitConnected error: bindService fail");
                    notifyDisconnected();
                }
            }
            if (!this.mIsConnecting) {
                bindService();
            }
            z = isConnectedWait();
            t = null;
            if (!z) {
            }
            Log.w(TAG, "executeWaitConnected error: bindService fail");
            notifyDisconnected();
        }
        return t;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class RemoteCallbackCookie {
        public String callingPackage;
        public RemoteCallbackWrapper listenerWrapper;
        public int resType;

        private RemoteCallbackCookie() {
        }

        /* synthetic */ RemoteCallbackCookie(AnonymousClass1 anonymousClass1) {
            this();
        }

        public String toString() {
            return "RemoteCallbackCookie{lis=" + this.listenerWrapper + ", calling='" + this.callingPackage + "', resType=" + this.resType + '}';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof RemoteCallbackCookie) {
                RemoteCallbackCookie remoteCallbackCookie = (RemoteCallbackCookie) obj;
                return this.resType == remoteCallbackCookie.resType && Objects.equals(this.listenerWrapper, remoteCallbackCookie.listenerWrapper) && Objects.equals(this.callingPackage, remoteCallbackCookie.callingPackage);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.listenerWrapper, this.callingPackage, Integer.valueOf(this.resType));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class RemoteCallbackWrapper extends IAssembleListener.Stub {
        public final IAssembleClientListener clientListener;

        public RemoteCallbackWrapper(IAssembleClientListener iAssembleClientListener) {
            this.clientListener = iAssembleClientListener;
        }

        @Override // com.xiaopeng.appstore.storeprovider.IAssembleListener
        public void onAssembleEvent(int i, AssembleInfo assembleInfo) throws RemoteException {
            Log.d(StoreProviderManager.TAG, "onAssembleEvent, type:" + i + ", info:" + assembleInfo + ", client:" + this.clientListener);
            this.clientListener.onAssembleEvent(i, assembleInfo);
        }

        public String toString() {
            return "RemoteCallbackWrapper{client=" + this.clientListener + "} " + super.toString();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof RemoteCallbackWrapper) {
                return Objects.equals(this.clientListener, ((RemoteCallbackWrapper) obj).clientListener);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.clientListener);
        }
    }
}
