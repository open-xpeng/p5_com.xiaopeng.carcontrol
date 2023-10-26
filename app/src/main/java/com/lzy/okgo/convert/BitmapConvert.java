package com.lzy.okgo.convert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* loaded from: classes.dex */
public class BitmapConvert implements Converter<Bitmap> {
    private Bitmap.Config decodeConfig;
    private int maxHeight;
    private int maxWidth;
    private ImageView.ScaleType scaleType;

    public BitmapConvert() {
        this(1000, 1000, Bitmap.Config.ARGB_8888, ImageView.ScaleType.CENTER_INSIDE);
    }

    public BitmapConvert(int i, int i2) {
        this(i, i2, Bitmap.Config.ARGB_8888, ImageView.ScaleType.CENTER_INSIDE);
    }

    public BitmapConvert(int i, int i2, Bitmap.Config config, ImageView.ScaleType scaleType) {
        this.maxWidth = i;
        this.maxHeight = i2;
        this.decodeConfig = config;
        this.scaleType = scaleType;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.lzy.okgo.convert.Converter
    public Bitmap convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }
        return parse(body.bytes());
    }

    private Bitmap parse(byte[] bArr) throws OutOfMemoryError {
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (this.maxWidth == 0 && this.maxHeight == 0) {
            options.inPreferredConfig = this.decodeConfig;
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        }
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        int i = options.outWidth;
        int i2 = options.outHeight;
        int resizedDimension = getResizedDimension(this.maxWidth, this.maxHeight, i, i2, this.scaleType);
        int resizedDimension2 = getResizedDimension(this.maxHeight, this.maxWidth, i2, i, this.scaleType);
        options.inJustDecodeBounds = false;
        options.inSampleSize = findBestSampleSize(i, i2, resizedDimension, resizedDimension2);
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        if (decodeByteArray != null) {
            if (decodeByteArray.getWidth() > resizedDimension || decodeByteArray.getHeight() > resizedDimension2) {
                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(decodeByteArray, resizedDimension, resizedDimension2, true);
                decodeByteArray.recycle();
                return createScaledBitmap;
            }
            return decodeByteArray;
        }
        return decodeByteArray;
    }

    private static int getResizedDimension(int i, int i2, int i3, int i4, ImageView.ScaleType scaleType) {
        if (i == 0 && i2 == 0) {
            return i3;
        }
        if (scaleType == ImageView.ScaleType.FIT_XY) {
            return i == 0 ? i3 : i;
        } else if (i == 0) {
            return (int) (i3 * (i2 / i4));
        } else if (i2 == 0) {
            return i;
        } else {
            double d = i4 / i3;
            if (scaleType == ImageView.ScaleType.CENTER_CROP) {
                double d2 = i2;
                return ((double) i) * d < d2 ? (int) (d2 / d) : i;
            }
            double d3 = i2;
            return ((double) i) * d > d3 ? (int) (d3 / d) : i;
        }
    }

    private static int findBestSampleSize(int i, int i2, int i3, int i4) {
        double min = Math.min(i / i3, i2 / i4);
        float f = 1.0f;
        while (true) {
            float f2 = 2.0f * f;
            if (f2 > min) {
                return (int) f;
            }
            f = f2;
        }
    }
}
