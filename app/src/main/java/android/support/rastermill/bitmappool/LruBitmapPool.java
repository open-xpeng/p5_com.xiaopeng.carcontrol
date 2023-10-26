package android.support.rastermill.bitmappool;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class LruBitmapPool implements BitmapPool {
    private static final Bitmap.Config DEFAULT_CONFIG = Bitmap.Config.ARGB_8888;
    private static final String TAG = "LruBitmapPool";
    private final Set<Bitmap.Config> allowedConfigs;
    private int currentSize;
    private int evictions;
    private int hits;
    private final int initialMaxSize;
    private int maxSize;
    private int misses;
    private int puts;
    private final LruPoolStrategy strategy;
    private final BitmapTracker tracker;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface BitmapTracker {
        void add(Bitmap bitmap);

        void remove(Bitmap bitmap);
    }

    LruBitmapPool(int i, LruPoolStrategy lruPoolStrategy, Set<Bitmap.Config> set) {
        this.initialMaxSize = i;
        this.maxSize = i;
        this.strategy = lruPoolStrategy;
        this.allowedConfigs = set;
        this.tracker = new NullBitmapTracker();
    }

    public LruBitmapPool(int i) {
        this(i, getDefaultStrategy(), getDefaultAllowedConfigs());
    }

    public LruBitmapPool(int i, Set<Bitmap.Config> set) {
        this(i, getDefaultStrategy(), set);
    }

    @Override // android.support.rastermill.bitmappool.BitmapPool
    public int getMaxSize() {
        return this.maxSize;
    }

    @Override // android.support.rastermill.bitmappool.BitmapPool
    public synchronized void setSizeMultiplier(float f) {
        this.maxSize = Math.round(this.initialMaxSize * f);
        evict();
    }

    @Override // android.support.rastermill.bitmappool.BitmapPool
    public synchronized boolean put(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("Bitmap must not be null");
        }
        if (bitmap.isMutable() && this.strategy.getSize(bitmap) <= this.maxSize && this.allowedConfigs.contains(bitmap.getConfig())) {
            int size = this.strategy.getSize(bitmap);
            this.strategy.put(bitmap);
            this.tracker.add(bitmap);
            this.puts++;
            this.currentSize += size;
            Log.e(TAG, "put currentSize = " + this.currentSize);
            Log.e(TAG, "put bitmap in pool=" + this.strategy.logBitmap(bitmap));
            dump();
            evict();
            return true;
        }
        Log.e(TAG, "Reject bitmap from pool, bitmap: " + this.strategy.logBitmap(bitmap) + ", is mutable: " + bitmap.isMutable() + ", is allowed config: " + this.allowedConfigs.contains(bitmap.getConfig()));
        return false;
    }

    private void evict() {
        trimToSize(this.maxSize);
    }

    @Override // android.support.rastermill.bitmappool.BitmapPool
    public synchronized Bitmap get(int i, int i2, Bitmap.Config config) {
        Bitmap dirty;
        dirty = getDirty(i, i2, config);
        if (dirty != null) {
            dirty.eraseColor(0);
        }
        return dirty;
    }

    @Override // android.support.rastermill.bitmappool.BitmapPool
    public synchronized Bitmap getDirty(int i, int i2, Bitmap.Config config) {
        Bitmap bitmap;
        bitmap = this.strategy.get(i, i2, config != null ? config : DEFAULT_CONFIG);
        if (bitmap == null) {
            Log.e(TAG, "Missing bitmap=" + this.strategy.logBitmap(i, i2, config));
            this.misses++;
        } else {
            this.hits++;
            this.currentSize -= this.strategy.getSize(bitmap);
            this.tracker.remove(bitmap);
            if (Build.VERSION.SDK_INT >= 12) {
                bitmap.setHasAlpha(true);
            }
        }
        Log.e(TAG, "Get bitmap=" + this.strategy.logBitmap(i, i2, config));
        dump();
        return bitmap;
    }

    @Override // android.support.rastermill.bitmappool.BitmapPool
    public void clearMemory() {
        Log.e(TAG, "clearMemory");
        trimToSize(0);
    }

    @Override // android.support.rastermill.bitmappool.BitmapPool
    public void trimMemory(int i) {
        Log.e(TAG, "trimMemory, level=" + i);
        if (i >= 60) {
            clearMemory();
        } else if (i >= 40) {
            trimToSize(this.maxSize / 2);
        }
    }

    private synchronized void trimToSize(int i) {
        while (this.currentSize > i) {
            Bitmap removeLast = this.strategy.removeLast();
            if (removeLast == null) {
                Log.e(TAG, "Size mismatch, resetting");
                dumpUnchecked();
                this.currentSize = 0;
                return;
            }
            this.tracker.remove(removeLast);
            this.currentSize -= this.strategy.getSize(removeLast);
            removeLast.recycle();
            this.evictions++;
            Log.e(TAG, "Evicting bitmap=" + this.strategy.logBitmap(removeLast));
            dump();
        }
    }

    private void dump() {
        dumpUnchecked();
    }

    private void dumpUnchecked() {
        Log.v(TAG, "Hits=" + this.hits + ", misses=" + this.misses + ", puts=" + this.puts + ", evictions=" + this.evictions + ", currentSize=" + this.currentSize + ", maxSize=" + this.maxSize + "\nStrategy=" + this.strategy);
    }

    private static LruPoolStrategy getDefaultStrategy() {
        if (Build.VERSION.SDK_INT >= 19) {
            return new SizeConfigStrategy();
        }
        return new AttributeStrategy();
    }

    private static Set<Bitmap.Config> getDefaultAllowedConfigs() {
        HashSet hashSet = new HashSet();
        hashSet.addAll(Arrays.asList(Bitmap.Config.values()));
        if (Build.VERSION.SDK_INT >= 19) {
            hashSet.add(null);
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public static LruBitmapPool get(Context context) {
        return new LruBitmapPool(new MemorySizeCalculator(context).getBitmapPoolSize());
    }

    /* loaded from: classes.dex */
    private static class ThrowingBitmapTracker implements BitmapTracker {
        private final Set<Bitmap> bitmaps = Collections.synchronizedSet(new HashSet());

        private ThrowingBitmapTracker() {
        }

        @Override // android.support.rastermill.bitmappool.LruBitmapPool.BitmapTracker
        public void add(Bitmap bitmap) {
            if (this.bitmaps.contains(bitmap)) {
                throw new IllegalStateException("Can't add already added bitmap: " + bitmap + " [" + bitmap.getWidth() + "x" + bitmap.getHeight() + "]");
            }
            this.bitmaps.add(bitmap);
        }

        @Override // android.support.rastermill.bitmappool.LruBitmapPool.BitmapTracker
        public void remove(Bitmap bitmap) {
            if (!this.bitmaps.contains(bitmap)) {
                throw new IllegalStateException("Cannot remove bitmap not in tracker");
            }
            this.bitmaps.remove(bitmap);
        }
    }

    /* loaded from: classes.dex */
    private static class NullBitmapTracker implements BitmapTracker {
        @Override // android.support.rastermill.bitmappool.LruBitmapPool.BitmapTracker
        public void add(Bitmap bitmap) {
        }

        @Override // android.support.rastermill.bitmappool.LruBitmapPool.BitmapTracker
        public void remove(Bitmap bitmap) {
        }

        private NullBitmapTracker() {
        }
    }
}
