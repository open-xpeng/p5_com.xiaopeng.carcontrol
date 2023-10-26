package android.support.rastermill.cache;

import android.support.rastermill.cache.DiskCache;
import android.support.rastermill.disklrucache.DiskLruCache;
import android.util.Log;
import java.io.File;
import java.io.IOException;

/* loaded from: classes.dex */
public class DiskLruCacheWrapper implements DiskCache {
    private static final int APP_VERSION = 1;
    private static final String TAG = "DiskLruCacheWrapper";
    private static final int VALUE_COUNT = 1;
    private static DiskLruCacheWrapper wrapper;
    private final File directory;
    private DiskLruCache diskLruCache;
    private final int maxSize;
    private final DiskCacheWriteLocker writeLocker = new DiskCacheWriteLocker();
    private final SafeKeyGenerator safeKeyGenerator = new SafeKeyGenerator();

    public static synchronized DiskCache get(File file, int i) {
        DiskLruCacheWrapper diskLruCacheWrapper;
        synchronized (DiskLruCacheWrapper.class) {
            if (wrapper == null) {
                wrapper = new DiskLruCacheWrapper(file, i);
            }
            diskLruCacheWrapper = wrapper;
        }
        return diskLruCacheWrapper;
    }

    protected DiskLruCacheWrapper(File file, int i) {
        this.directory = file;
        this.maxSize = i;
    }

    private synchronized DiskLruCache getDiskCache() throws IOException {
        if (this.diskLruCache == null) {
            this.diskLruCache = DiskLruCache.open(this.directory, 1, 1, this.maxSize);
        }
        return this.diskLruCache;
    }

    private synchronized void resetDiskCache() {
        this.diskLruCache = null;
    }

    @Override // android.support.rastermill.cache.DiskCache
    public File get(String str) {
        try {
            DiskLruCache.Value value = getDiskCache().get(this.safeKeyGenerator.getSafeKey(str));
            if (value != null) {
                return value.getFile(0);
            }
            return null;
        } catch (IOException e) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Unable to get from disk cache", e);
                return null;
            }
            return null;
        }
    }

    @Override // android.support.rastermill.cache.DiskCache
    public void put(String str, DiskCache.Writer writer) {
        String safeKey = this.safeKeyGenerator.getSafeKey(str);
        this.writeLocker.acquire(str);
        try {
            try {
                DiskLruCache.Editor edit = getDiskCache().edit(safeKey);
                if (edit != null) {
                    try {
                        if (writer.write(edit.getFile(0))) {
                            edit.commit();
                        }
                        edit.abortUnlessCommitted();
                    } catch (Throwable th) {
                        edit.abortUnlessCommitted();
                        throw th;
                    }
                }
            } catch (IOException e) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Unable to put to disk cache", e);
                }
            }
        } finally {
            this.writeLocker.release(str);
        }
    }

    @Override // android.support.rastermill.cache.DiskCache
    public void delete(String str) {
        try {
            getDiskCache().remove(this.safeKeyGenerator.getSafeKey(str));
        } catch (IOException e) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Unable to delete from disk cache", e);
            }
        }
    }

    @Override // android.support.rastermill.cache.DiskCache
    public synchronized void clear() {
        try {
            getDiskCache().delete();
            resetDiskCache();
        } catch (IOException e) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Unable to clear disk cache", e);
            }
        }
    }
}
