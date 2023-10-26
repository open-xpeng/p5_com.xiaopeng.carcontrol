package android.support.rastermill.cache;

import android.support.rastermill.cache.DiskCache;
import java.io.File;

/* loaded from: classes.dex */
public class DiskLruCacheFactory implements DiskCache.Factory {
    private final CacheDirectoryGetter cacheDirectoryGetter;
    private final int diskCacheSize;

    /* loaded from: classes.dex */
    public interface CacheDirectoryGetter {
        File getCacheDirectory();
    }

    public DiskLruCacheFactory(final String str, int i) {
        this(new CacheDirectoryGetter() { // from class: android.support.rastermill.cache.DiskLruCacheFactory.1
            @Override // android.support.rastermill.cache.DiskLruCacheFactory.CacheDirectoryGetter
            public File getCacheDirectory() {
                return new File(str);
            }
        }, i);
    }

    public DiskLruCacheFactory(final String str, final String str2, int i) {
        this(new CacheDirectoryGetter() { // from class: android.support.rastermill.cache.DiskLruCacheFactory.2
            @Override // android.support.rastermill.cache.DiskLruCacheFactory.CacheDirectoryGetter
            public File getCacheDirectory() {
                return new File(str, str2);
            }
        }, i);
    }

    public DiskLruCacheFactory(CacheDirectoryGetter cacheDirectoryGetter, int i) {
        this.diskCacheSize = i;
        this.cacheDirectoryGetter = cacheDirectoryGetter;
    }

    @Override // android.support.rastermill.cache.DiskCache.Factory
    public DiskCache build() {
        File cacheDirectory = this.cacheDirectoryGetter.getCacheDirectory();
        if (cacheDirectory == null) {
            return null;
        }
        if (cacheDirectory.mkdirs() || (cacheDirectory.exists() && cacheDirectory.isDirectory())) {
            return DiskLruCacheWrapper.get(cacheDirectory, this.diskCacheSize);
        }
        return null;
    }
}
