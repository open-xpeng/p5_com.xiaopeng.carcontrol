package android.support.rastermill;

import android.support.rastermill.cache.DiskCache;

/* loaded from: classes.dex */
public class FrameSequenceParams {
    private DiskCache.Factory mDiskCacheFactory;

    public DiskCache.Factory getDiskCacheFactory() {
        return this.mDiskCacheFactory;
    }

    /* loaded from: classes.dex */
    public static class Builder {
        DiskCache.Factory diskCacheFactory;

        public Builder diskCacheFactory(DiskCache.Factory factory) {
            this.diskCacheFactory = factory;
            return this;
        }

        public FrameSequenceParams build() {
            FrameSequenceParams frameSequenceParams = new FrameSequenceParams();
            frameSequenceParams.mDiskCacheFactory = this.diskCacheFactory;
            return frameSequenceParams;
        }
    }
}
