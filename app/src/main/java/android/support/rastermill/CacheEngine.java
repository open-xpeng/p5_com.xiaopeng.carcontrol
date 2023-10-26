package android.support.rastermill;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.rastermill.FrameSequenceController;
import android.support.rastermill.cache.DiskCache;
import android.support.rastermill.cache.DiskCacheAdapter;
import android.support.rastermill.util.Util;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public class CacheEngine {
    private static final String DIR_NAME_WEBP_CACHE = "webp_cache";
    private static CacheEngine sInstance;
    private static Handler sMainThreadHandler = new Handler(Looper.getMainLooper());
    private final LazyDiskCacheProvider diskCacheProvider;
    private Context mContext;
    private HashMap<String, LinkedList<CacheCallback>> mFutureMap = new HashMap<>();
    private HashSet<String> mFetchingSet = new HashSet<>();
    private Object mLock = new Object();
    private final ExecutorService mSourceService = Executors.newFixedThreadPool(Math.max(1, Runtime.getRuntime().availableProcessors()));

    /* loaded from: classes.dex */
    public interface CacheCallback {
        void cacheData(String str, InputStream inputStream);

        void onCacheFailure(String str);

        void onCacheFinished(String str);
    }

    public static CacheEngine getInstance() {
        CacheEngine cacheEngine = sInstance;
        if (cacheEngine != null) {
            return cacheEngine;
        }
        throw new RuntimeException("CacheEngine should be initialized.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void init(Context context, DiskCache.Factory factory) {
        sInstance = new CacheEngine(context.getApplicationContext(), factory);
    }

    private CacheEngine(Context context, DiskCache.Factory factory) {
        this.mContext = context;
        this.diskCacheProvider = new LazyDiskCacheProvider(factory);
    }

    public Future cache(final String str, final CacheCallback cacheCallback) {
        if (!Util.isNetworkAvailable(this.mContext)) {
            cacheCallback.onCacheFailure(null);
            return null;
        }
        putFuture(str, cacheCallback);
        if (isFetching(str)) {
            return null;
        }
        putFetching(str);
        return this.mSourceService.submit(new Runnable() { // from class: android.support.rastermill.CacheEngine.1
            /* JADX WARN: Removed duplicated region for block: B:13:0x002f  */
            /* JADX WARN: Removed duplicated region for block: B:14:0x0037  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    r5 = this;
                    java.lang.String r0 = "cache"
                    android.support.rastermill.data.HttpUrlFetcher r1 = new android.support.rastermill.data.HttpUrlFetcher
                    android.support.rastermill.data.RequestUrl r2 = new android.support.rastermill.data.RequestUrl
                    java.lang.String r3 = r2
                    r2.<init>(r3)
                    r1.<init>(r2)
                    java.io.InputStream r2 = r1.loadData()     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L20 java.lang.SecurityException -> L25
                    android.support.rastermill.CacheEngine$CacheCallback r3 = r3     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L20 java.lang.SecurityException -> L25
                    java.lang.String r4 = r2     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L20 java.lang.SecurityException -> L25
                    r3.cacheData(r4, r2)     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L20 java.lang.SecurityException -> L25
                    r0 = 0
                    r1.cleanup()
                    goto L2d
                L1e:
                    r0 = move-exception
                    goto L3f
                L20:
                    r2 = move-exception
                    android.support.rastermill.LogUtil.e(r0, r2)     // Catch: java.lang.Throwable -> L1e
                    goto L29
                L25:
                    r2 = move-exception
                    android.support.rastermill.LogUtil.e(r0, r2)     // Catch: java.lang.Throwable -> L1e
                L29:
                    r1.cleanup()
                    r0 = 1
                L2d:
                    if (r0 == 0) goto L37
                    android.support.rastermill.CacheEngine r0 = android.support.rastermill.CacheEngine.this
                    java.lang.String r1 = r2
                    android.support.rastermill.CacheEngine.access$000(r0, r1)
                    goto L3e
                L37:
                    android.support.rastermill.CacheEngine r0 = android.support.rastermill.CacheEngine.this
                    java.lang.String r1 = r2
                    android.support.rastermill.CacheEngine.access$100(r0, r1)
                L3e:
                    return
                L3f:
                    r1.cleanup()
                    throw r0
                */
                throw new UnsupportedOperationException("Method not decompiled: android.support.rastermill.CacheEngine.AnonymousClass1.run():void");
            }
        });
    }

    private void putFetching(String str) {
        synchronized (this.mLock) {
            this.mFetchingSet.add(str);
        }
    }

    private boolean isFetching(String str) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mFetchingSet.contains(str);
        }
        return contains;
    }

    private void putFuture(String str, CacheCallback cacheCallback) {
        synchronized (this.mLock) {
            LinkedList<CacheCallback> linkedList = this.mFutureMap.get(cacheCallback);
            if (linkedList == null) {
                linkedList = new LinkedList<>();
                this.mFutureMap.put(str, linkedList);
            }
            linkedList.add(cacheCallback);
        }
    }

    private LinkedList<CacheCallback> getCallbackList(String str) {
        LinkedList<CacheCallback> linkedList;
        synchronized (this.mLock) {
            LinkedList<CacheCallback> linkedList2 = this.mFutureMap.get(str);
            if (linkedList2 != null) {
                linkedList = new LinkedList<>(linkedList2);
                linkedList2.clear();
            } else {
                linkedList = null;
            }
            this.mFetchingSet.remove(str);
        }
        return linkedList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCacheFinished(final String str) {
        final LinkedList<CacheCallback> callbackList = getCallbackList(str);
        sMainThreadHandler.post(new Runnable() { // from class: android.support.rastermill.CacheEngine.2
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = callbackList.iterator();
                while (it.hasNext()) {
                    ((CacheCallback) it.next()).onCacheFinished(str);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCacheFailure(final String str) {
        final LinkedList<CacheCallback> callbackList = getCallbackList(str);
        sMainThreadHandler.post(new Runnable() { // from class: android.support.rastermill.CacheEngine.3
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = callbackList.iterator();
                while (it.hasNext()) {
                    ((CacheCallback) it.next()).onCacheFailure(str);
                }
            }
        });
    }

    public static boolean copyToFile(InputStream inputStream, File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = inputStream.read(bArr);
                if (read < 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            }
            fileOutputStream.flush();
            try {
                fileOutputStream.getFD().sync();
            } catch (IOException unused) {
            }
            fileOutputStream.close();
            return true;
        } catch (Throwable th) {
            Log.e("CacheEngine", "copyToFile", th);
            return false;
        }
    }

    public DiskCache getDiskCache() {
        return this.diskCacheProvider.getDiskCache();
    }

    /* loaded from: classes.dex */
    private static class LazyDiskCacheProvider implements FrameSequenceController.DiskCacheProvider {
        private volatile DiskCache diskCache;
        private final DiskCache.Factory factory;

        public LazyDiskCacheProvider(DiskCache.Factory factory) {
            this.factory = factory;
        }

        @Override // android.support.rastermill.FrameSequenceController.DiskCacheProvider
        public DiskCache getDiskCache() {
            if (this.diskCache == null) {
                synchronized (this) {
                    if (this.diskCache == null) {
                        this.diskCache = this.factory.build();
                    }
                    if (this.diskCache == null) {
                        this.diskCache = new DiskCacheAdapter();
                    }
                }
            }
            return this.diskCache;
        }
    }
}
