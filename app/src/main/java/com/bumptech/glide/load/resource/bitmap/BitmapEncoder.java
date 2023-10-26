package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;

/* loaded from: classes.dex */
public class BitmapEncoder implements ResourceEncoder<Bitmap> {
    private static final String TAG = "BitmapEncoder";
    public static final Option<Integer> COMPRESSION_QUALITY = Option.memory("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionQuality", 90);
    public static final Option<Bitmap.CompressFormat> COMPRESSION_FORMAT = Option.memory("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionFormat");

    /* JADX WARN: Removed duplicated region for block: B:26:0x0082 A[Catch: all -> 0x00dc, TRY_LEAVE, TryCatch #4 {all -> 0x00dc, blocks: (B:3:0x003f, B:8:0x005d, B:24:0x007c, B:26:0x0082, B:30:0x00d8, B:31:0x00db, B:22:0x0078), top: B:40:0x003f }] */
    @Override // com.bumptech.glide.load.Encoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean encode(com.bumptech.glide.load.engine.Resource<android.graphics.Bitmap> r9, java.io.File r10, com.bumptech.glide.load.Options r11) {
        /*
            r8 = this;
            java.lang.String r0 = "BitmapEncoder"
            java.lang.Object r9 = r9.get()
            android.graphics.Bitmap r9 = (android.graphics.Bitmap) r9
            android.graphics.Bitmap$CompressFormat r1 = r8.getFormat(r9, r11)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "encode: ["
            java.lang.StringBuilder r2 = r2.append(r3)
            int r3 = r9.getWidth()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = "x"
            java.lang.StringBuilder r2 = r2.append(r3)
            int r3 = r9.getHeight()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = "] "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r1)
            java.lang.String r2 = r2.toString()
            androidx.core.os.TraceCompat.beginSection(r2)
            long r2 = com.bumptech.glide.util.LogTime.getLogTime()     // Catch: java.lang.Throwable -> Ldc
            com.bumptech.glide.load.Option<java.lang.Integer> r4 = com.bumptech.glide.load.resource.bitmap.BitmapEncoder.COMPRESSION_QUALITY     // Catch: java.lang.Throwable -> Ldc
            java.lang.Object r4 = r11.get(r4)     // Catch: java.lang.Throwable -> Ldc
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch: java.lang.Throwable -> Ldc
            int r4 = r4.intValue()     // Catch: java.lang.Throwable -> Ldc
            r5 = 0
            r6 = 0
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L67 java.io.IOException -> L69
            r7.<init>(r10)     // Catch: java.lang.Throwable -> L67 java.io.IOException -> L69
            r9.compress(r1, r4, r7)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L64
            r7.close()     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L64
            r5 = 1
            r7.close()     // Catch: java.io.IOException -> L7b java.lang.Throwable -> Ldc
            goto L7b
        L61:
            r9 = move-exception
            r6 = r7
            goto Ld6
        L64:
            r10 = move-exception
            r6 = r7
            goto L6a
        L67:
            r9 = move-exception
            goto Ld6
        L69:
            r10 = move-exception
        L6a:
            r4 = 3
            boolean r4 = android.util.Log.isLoggable(r0, r4)     // Catch: java.lang.Throwable -> L67
            if (r4 == 0) goto L76
            java.lang.String r4 = "Failed to encode Bitmap"
            android.util.Log.d(r0, r4, r10)     // Catch: java.lang.Throwable -> L67
        L76:
            if (r6 == 0) goto L7b
            r6.close()     // Catch: java.io.IOException -> L7b java.lang.Throwable -> Ldc
        L7b:
            r10 = 2
            boolean r10 = android.util.Log.isLoggable(r0, r10)     // Catch: java.lang.Throwable -> Ldc
            if (r10 == 0) goto Ld2
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ldc
            r10.<init>()     // Catch: java.lang.Throwable -> Ldc
            java.lang.String r4 = "Compressed with type: "
            java.lang.StringBuilder r10 = r10.append(r4)     // Catch: java.lang.Throwable -> Ldc
            java.lang.StringBuilder r10 = r10.append(r1)     // Catch: java.lang.Throwable -> Ldc
            java.lang.String r1 = " of size "
            java.lang.StringBuilder r10 = r10.append(r1)     // Catch: java.lang.Throwable -> Ldc
            int r1 = com.bumptech.glide.util.Util.getBitmapByteSize(r9)     // Catch: java.lang.Throwable -> Ldc
            java.lang.StringBuilder r10 = r10.append(r1)     // Catch: java.lang.Throwable -> Ldc
            java.lang.String r1 = " in "
            java.lang.StringBuilder r10 = r10.append(r1)     // Catch: java.lang.Throwable -> Ldc
            double r1 = com.bumptech.glide.util.LogTime.getElapsedMillis(r2)     // Catch: java.lang.Throwable -> Ldc
            java.lang.StringBuilder r10 = r10.append(r1)     // Catch: java.lang.Throwable -> Ldc
            java.lang.String r1 = ", options format: "
            java.lang.StringBuilder r10 = r10.append(r1)     // Catch: java.lang.Throwable -> Ldc
            com.bumptech.glide.load.Option<android.graphics.Bitmap$CompressFormat> r1 = com.bumptech.glide.load.resource.bitmap.BitmapEncoder.COMPRESSION_FORMAT     // Catch: java.lang.Throwable -> Ldc
            java.lang.Object r11 = r11.get(r1)     // Catch: java.lang.Throwable -> Ldc
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch: java.lang.Throwable -> Ldc
            java.lang.String r11 = ", hasAlpha: "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch: java.lang.Throwable -> Ldc
            boolean r9 = r9.hasAlpha()     // Catch: java.lang.Throwable -> Ldc
            java.lang.StringBuilder r9 = r10.append(r9)     // Catch: java.lang.Throwable -> Ldc
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> Ldc
            android.util.Log.v(r0, r9)     // Catch: java.lang.Throwable -> Ldc
        Ld2:
            androidx.core.os.TraceCompat.endSection()
            return r5
        Ld6:
            if (r6 == 0) goto Ldb
            r6.close()     // Catch: java.io.IOException -> Ldb java.lang.Throwable -> Ldc
        Ldb:
            throw r9     // Catch: java.lang.Throwable -> Ldc
        Ldc:
            r9 = move-exception
            androidx.core.os.TraceCompat.endSection()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.bitmap.BitmapEncoder.encode(com.bumptech.glide.load.engine.Resource, java.io.File, com.bumptech.glide.load.Options):boolean");
    }

    private Bitmap.CompressFormat getFormat(Bitmap bitmap, Options options) {
        Bitmap.CompressFormat compressFormat = (Bitmap.CompressFormat) options.get(COMPRESSION_FORMAT);
        if (compressFormat != null) {
            return compressFormat;
        }
        if (bitmap.hasAlpha()) {
            return Bitmap.CompressFormat.PNG;
        }
        return Bitmap.CompressFormat.JPEG;
    }

    @Override // com.bumptech.glide.load.ResourceEncoder
    public EncodeStrategy getEncodeStrategy(Options options) {
        return EncodeStrategy.TRANSFORMED;
    }
}
