package android.support.rastermill.bitmappool;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public class BitmapPoolAdapter implements BitmapPool {
    @Override // android.support.rastermill.bitmappool.BitmapPool
    public void clearMemory() {
    }

    @Override // android.support.rastermill.bitmappool.BitmapPool
    public Bitmap get(int i, int i2, Bitmap.Config config) {
        return null;
    }

    @Override // android.support.rastermill.bitmappool.BitmapPool
    public Bitmap getDirty(int i, int i2, Bitmap.Config config) {
        return null;
    }

    @Override // android.support.rastermill.bitmappool.BitmapPool
    public int getMaxSize() {
        return 0;
    }

    @Override // android.support.rastermill.bitmappool.BitmapPool
    public boolean put(Bitmap bitmap) {
        return false;
    }

    @Override // android.support.rastermill.bitmappool.BitmapPool
    public void setSizeMultiplier(float f) {
    }

    @Override // android.support.rastermill.bitmappool.BitmapPool
    public void trimMemory(int i) {
    }
}
