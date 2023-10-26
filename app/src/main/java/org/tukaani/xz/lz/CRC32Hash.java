package org.tukaani.xz.lz;

/* loaded from: classes3.dex */
class CRC32Hash {
    private static final int CRC32_POLY = -306674912;
    static final int[] crcTable = new int[256];

    static {
        for (int i = 0; i < 256; i++) {
            int i2 = i;
            for (int i3 = 0; i3 < 8; i3++) {
                int i4 = i2 & 1;
                i2 >>>= 1;
                if (i4 != 0) {
                    i2 ^= CRC32_POLY;
                }
            }
            crcTable[i] = i2;
        }
    }
}
