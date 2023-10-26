package android.support.rastermill.cache;

import android.content.Context;
import android.support.rastermill.cache.DiskLruCacheFactory;
import java.io.File;

/* loaded from: classes.dex */
public final class InternalCacheDiskCacheFactory extends DiskLruCacheFactory {
    public InternalCacheDiskCacheFactory(Context context) {
        this(context, "image_manager_disk_cache", 262144000);
    }

    public InternalCacheDiskCacheFactory(Context context, int i) {
        this(context, "image_manager_disk_cache", i);
    }

    public InternalCacheDiskCacheFactory(final Context context, final String str, int i) {
        super(new DiskLruCacheFactory.CacheDirectoryGetter() { // from class: android.support.rastermill.cache.InternalCacheDiskCacheFactory.1
            @Override // android.support.rastermill.cache.DiskLruCacheFactory.CacheDirectoryGetter
            public File getCacheDirectory() {
                File cacheDir = context.getCacheDir();
                if (cacheDir == null) {
                    return null;
                }
                return str != null ? new File(cacheDir, str) : cacheDir;
            }
        }, i);
    }
}
