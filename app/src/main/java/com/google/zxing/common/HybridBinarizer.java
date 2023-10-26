package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public final class HybridBinarizer extends GlobalHistogramBinarizer {
    private static final int BLOCK_SIZE = 8;
    private static final int BLOCK_SIZE_MASK = 7;
    private static final int BLOCK_SIZE_POWER = 3;
    private static final int MINIMUM_DIMENSION = 40;
    private static final int MIN_DYNAMIC_RANGE = 24;
    private BitMatrix matrix;

    private static int cap(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    @Override // com.google.zxing.common.GlobalHistogramBinarizer, com.google.zxing.Binarizer
    public BitMatrix getBlackMatrix() throws NotFoundException {
        BitMatrix bitMatrix = this.matrix;
        if (bitMatrix != null) {
            return bitMatrix;
        }
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        if (width >= 40 && height >= 40) {
            byte[] matrix = luminanceSource.getMatrix();
            int i = width >> 3;
            if ((width & 7) != 0) {
                i++;
            }
            int i2 = i;
            int i3 = height >> 3;
            if ((height & 7) != 0) {
                i3++;
            }
            int i4 = i3;
            int[][] calculateBlackPoints = calculateBlackPoints(matrix, i2, i4, width, height);
            BitMatrix bitMatrix2 = new BitMatrix(width, height);
            calculateThresholdForBlock(matrix, i2, i4, width, height, calculateBlackPoints, bitMatrix2);
            this.matrix = bitMatrix2;
        } else {
            this.matrix = super.getBlackMatrix();
        }
        return this.matrix;
    }

    @Override // com.google.zxing.common.GlobalHistogramBinarizer, com.google.zxing.Binarizer
    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    private static void calculateThresholdForBlock(byte[] bArr, int i, int i2, int i3, int i4, int[][] iArr, BitMatrix bitMatrix) {
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = i5 << 3;
            int i7 = i4 - 8;
            if (i6 > i7) {
                i6 = i7;
            }
            for (int i8 = 0; i8 < i; i8++) {
                int i9 = i8 << 3;
                int i10 = i3 - 8;
                if (i9 <= i10) {
                    i10 = i9;
                }
                int cap = cap(i8, 2, i - 3);
                int cap2 = cap(i5, 2, i2 - 3);
                int i11 = 0;
                for (int i12 = -2; i12 <= 2; i12++) {
                    int[] iArr2 = iArr[cap2 + i12];
                    i11 += iArr2[cap - 2] + iArr2[cap - 1] + iArr2[cap] + iArr2[cap + 1] + iArr2[cap + 2];
                }
                thresholdBlock(bArr, i10, i6, i11 / 25, i3, bitMatrix);
            }
        }
    }

    private static void thresholdBlock(byte[] bArr, int i, int i2, int i3, int i4, BitMatrix bitMatrix) {
        int i5 = (i2 * i4) + i;
        int i6 = 0;
        while (i6 < 8) {
            for (int i7 = 0; i7 < 8; i7++) {
                if ((bArr[i5 + i7] & 255) <= i3) {
                    bitMatrix.set(i + i7, i2 + i6);
                }
            }
            i6++;
            i5 += i4;
        }
    }

    private static int[][] calculateBlackPoints(byte[] bArr, int i, int i2, int i3, int i4) {
        char c;
        char c2 = 2;
        boolean z = true;
        int i5 = 0;
        int[][] iArr = (int[][]) Array.newInstance(int.class, i2, i);
        int i6 = 0;
        while (i6 < i2) {
            int i7 = i6 << 3;
            int i8 = i4 - 8;
            if (i7 > i8) {
                i7 = i8;
            }
            int i9 = i5;
            while (i9 < i) {
                int i10 = i9 << 3;
                int i11 = i3 - 8;
                if (i10 > i11) {
                    i10 = i11;
                }
                int i12 = (i7 * i3) + i10;
                int i13 = i5;
                int i14 = i13;
                int i15 = i14;
                int i16 = 255;
                while (i13 < 8) {
                    for (int i17 = 0; i17 < 8; i17++) {
                        int i18 = bArr[i12 + i17] & 255;
                        i14 += i18;
                        if (i18 < i16) {
                            i16 = i18;
                        }
                        if (i18 > i15) {
                            i15 = i18;
                        }
                    }
                    if (i15 - i16 <= 24) {
                        i13++;
                        i12 += i3;
                        z = true;
                    }
                    while (true) {
                        i13++;
                        i12 += i3;
                        if (i13 < 8) {
                            for (int i19 = 0; i19 < 8; i19++) {
                                i14 += bArr[i12 + i19] & 255;
                            }
                        }
                    }
                    i13++;
                    i12 += i3;
                    z = true;
                }
                boolean z2 = z;
                int i20 = i14 >> 6;
                if (i15 - i16 <= 24) {
                    i20 = i16 / 2;
                    if (i6 > 0 && i9 > 0) {
                        int i21 = i6 - 1;
                        int i22 = i9 - 1;
                        c = 2;
                        int i23 = ((iArr[i21][i9] + (iArr[i6][i22] * 2)) + iArr[i21][i22]) / 4;
                        if (i16 < i23) {
                            i20 = i23;
                        }
                        iArr[i6][i9] = i20;
                        i9++;
                        z = z2;
                        c2 = c;
                        i5 = 0;
                    }
                }
                c = 2;
                iArr[i6][i9] = i20;
                i9++;
                z = z2;
                c2 = c;
                i5 = 0;
            }
            i6++;
            c2 = c2;
            i5 = 0;
        }
        return iArr;
    }
}
