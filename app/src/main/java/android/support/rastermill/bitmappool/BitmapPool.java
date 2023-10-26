package android.support.rastermill.bitmappool;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public interface BitmapPool {
    void clearMemory();

    Bitmap get(int i, int i2, Bitmap.Config config);

    Bitmap getDirty(int i, int i2, Bitmap.Config config);

    int getMaxSize();

    boolean put(Bitmap bitmap);

    void setSizeMultiplier(float f);

    void trimMemory(int i);
}
