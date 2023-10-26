package com.xiaopeng.carcontrol.util;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Hashtable;

/* loaded from: classes2.dex */
public class QrCodeUtils {
    public static Bitmap createQRImage(String url, final int width, final int height) {
        return createQRImage(url, width, height, 0);
    }

    public static Bitmap createQRImage(String url, final int width, final int height, int margin) {
        if (url != null) {
            try {
                if (!"".equals(url) && url.length() >= 1) {
                    Hashtable hashtable = new Hashtable();
                    hashtable.put(EncodeHintType.CHARACTER_SET, "utf-8");
                    hashtable.put(EncodeHintType.MARGIN, Integer.valueOf(margin));
                    BitMatrix encode = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hashtable);
                    int[] iArr = new int[width * height];
                    for (int i = 0; i < height; i++) {
                        for (int i2 = 0; i2 < width; i2++) {
                            if (encode.get(i2, i)) {
                                iArr[(i * width) + i2] = -16777216;
                            } else {
                                iArr[(i * width) + i2] = -1;
                            }
                        }
                    }
                    Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
                    return createBitmap;
                }
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void releaseQrBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }
}
