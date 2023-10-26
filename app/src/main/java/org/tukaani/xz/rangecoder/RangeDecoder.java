package org.tukaani.xz.rangecoder;

import java.io.IOException;

/* loaded from: classes3.dex */
public abstract class RangeDecoder extends RangeCoder {
    int range = 0;
    int code = 0;

    public int decodeBit(short[] sArr, int i) throws IOException {
        normalize();
        short s = sArr[i];
        int i2 = this.range;
        int i3 = (i2 >>> 11) * s;
        int i4 = this.code;
        if ((i4 ^ Integer.MIN_VALUE) < (Integer.MIN_VALUE ^ i3)) {
            this.range = i3;
            sArr[i] = (short) (s + ((2048 - s) >>> 5));
            return 0;
        }
        this.range = i2 - i3;
        this.code = i4 - i3;
        sArr[i] = (short) (s - (s >>> 5));
        return 1;
    }

    public int decodeBitTree(short[] sArr) throws IOException {
        int i = 1;
        do {
            i = decodeBit(sArr, i) | (i << 1);
        } while (i < sArr.length);
        return i - sArr.length;
    }

    public int decodeDirectBits(int i) throws IOException {
        int i2 = 0;
        do {
            normalize();
            int i3 = this.range >>> 1;
            this.range = i3;
            int i4 = this.code;
            int i5 = (i4 - i3) >>> 31;
            this.code = i4 - (i3 & (i5 - 1));
            i2 = (i2 << 1) | (1 - i5);
            i--;
        } while (i != 0);
        return i2;
    }

    public int decodeReverseBitTree(short[] sArr) throws IOException {
        int i = 0;
        int i2 = 0;
        int i3 = 1;
        while (true) {
            int decodeBit = decodeBit(sArr, i3);
            i3 = (i3 << 1) | decodeBit;
            int i4 = i2 + 1;
            i |= decodeBit << i2;
            if (i3 >= sArr.length) {
                return i;
            }
            i2 = i4;
        }
    }

    public abstract void normalize() throws IOException;
}
