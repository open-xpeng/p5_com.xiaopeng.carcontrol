package com.xiaopeng.lib.utils.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.xiaopeng.appstore.storeprovider.AssembleRequest;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public class BitmapUtils {
    public static void saveAsPicture(int i, int i2, byte[] bArr, String str) {
        int[] iArr = new int[i * i2];
        decodeYUV420SP(iArr, bArr, i, i2);
        Bitmap createBitmap = Bitmap.createBitmap(iArr, i, i2, Bitmap.Config.ARGB_8888);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap byte2Bmp(int i, int i2, byte[] bArr, Bitmap.Config config) {
        int[] iArr = new int[i * i2];
        decodeYUV420SP(iArr, bArr, i, i2);
        return Bitmap.createBitmap(iArr, i, i2, config);
    }

    public static Bitmap byte2Bmp(int i, int i2, byte[] bArr, int i3) {
        YuvImage yuvImage = new YuvImage(bArr, 17, i, i2, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, i, i2), i3, byteArrayOutputStream);
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decodeByteArray;
    }

    private static void decodeYUV420SP(int[] iArr, byte[] bArr, int i, int i2) {
        int i3 = i * i2;
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = ((i5 >> 1) * i) + i3;
            int i7 = 0;
            int i8 = 0;
            int i9 = 0;
            while (i7 < i) {
                int i10 = (bArr[i4] & 255) - 16;
                if (i10 < 0) {
                    i10 = 0;
                }
                if ((i7 & 1) == 0) {
                    int i11 = i6 + 1;
                    i9 = (bArr[i6] & 255) - 128;
                    i6 = i11 + 1;
                    i8 = (bArr[i11] & 255) - 128;
                }
                int i12 = i10 * 1192;
                int i13 = (i9 * 1634) + i12;
                int i14 = (i12 - (i9 * 833)) - (i8 * AssembleRequest.ASSEMBLE_ACTION_CANCEL);
                int i15 = i12 + (i8 * 2066);
                if (i13 < 0) {
                    i13 = 0;
                } else if (i13 > 262143) {
                    i13 = 262143;
                }
                if (i14 < 0) {
                    i14 = 0;
                } else if (i14 > 262143) {
                    i14 = 262143;
                }
                if (i15 < 0) {
                    i15 = 0;
                } else if (i15 > 262143) {
                    i15 = 262143;
                }
                iArr[i4] = ((i15 >> 10) & 255) | ((i13 << 6) & 16711680) | ViewCompat.MEASURED_STATE_MASK | ((i14 >> 2) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
                i7++;
                i4++;
            }
        }
    }
}
