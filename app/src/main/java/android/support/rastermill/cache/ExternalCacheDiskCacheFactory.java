package android.support.rastermill.cache;

import android.content.Context;
import android.support.rastermill.cache.DiskLruCacheFactory;
import java.io.File;

/* loaded from: classes.dex */
public final class ExternalCacheDiskCacheFactory extends DiskLruCacheFactory {
    public ExternalCacheDiskCacheFactory(Context context) {
        this(context, "image_manager_disk_cache", 262144000);
    }

    public ExternalCacheDiskCacheFactory(Context context, int i) {
        this(context, "image_manager_disk_cache", i);
    }

    public ExternalCacheDiskCacheFactory(final Context context, final String str, int i) {
        super(new DiskLruCacheFactory.CacheDirectoryGetter() { // from class: android.support.rastermill.cache.ExternalCacheDiskCacheFactory.1
            @Override // android.support.rastermill.cache.DiskLruCacheFactory.CacheDirectoryGetter
            public File getCacheDirectory() {
                File externalCacheDir = context.getExternalCacheDir();
                if (externalCacheDir == null) {
                    return null;
                }
                return str != null ? new File(externalCacheDir, str) : externalCacheDir;
            }
        }, i);
    }
}
