package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

/* loaded from: classes2.dex */
public class PreloadBitmap {
    private SparseArray<Bitmap> mBitmaps;
    private SparseArray<String> mStrings;

    /* loaded from: classes2.dex */
    private static class PreLoadHolder {
        private static final PreloadBitmap mPreloadBitmap = new PreloadBitmap();

        private PreLoadHolder() {
        }
    }

    public static PreloadBitmap getInstance() {
        return PreLoadHolder.mPreloadBitmap;
    }

    private PreloadBitmap() {
        this.mBitmaps = new SparseArray<>();
        this.mStrings = new SparseArray<>();
    }

    public void createBitmap(int[] resource, Context context) {
        if (resource == null || resource.length <= 0) {
            return;
        }
        for (int i : resource) {
            this.mBitmaps.put(i, BitmapFactory.decodeResource(context.getResources(), i));
        }
    }

    Bitmap getBitmap(int resourceId) {
        return this.mBitmaps.get(resourceId);
    }

    void putBitmap(int resourceId, Bitmap bitmap) {
        this.mBitmaps.put(resourceId, bitmap);
    }

    public void readText(int[] resource, Context context) {
        if (resource == null || resource.length <= 0) {
            return;
        }
        for (int i : resource) {
            this.mStrings.put(i, TextResourceReader.readTextFromFile(context, i));
        }
    }

    String getShaderString(int resourceId) {
        return this.mStrings.get(resourceId);
    }
}
