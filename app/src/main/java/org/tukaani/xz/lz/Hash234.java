package org.tukaani.xz.lz;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class Hash234 extends CRC32Hash {
    private static final int HASH_2_MASK = 1023;
    private static final int HASH_2_SIZE = 1024;
    private static final int HASH_3_MASK = 65535;
    private static final int HASH_3_SIZE = 65536;
    private final int hash4Mask;
    private final int[] hash4Table;
    private final int[] hash2Table = new int[1024];
    private final int[] hash3Table = new int[65536];
    private int hash2Value = 0;
    private int hash3Value = 0;
    private int hash4Value = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Hash234(int i) {
        int[] iArr = new int[getHash4Size(i)];
        this.hash4Table = iArr;
        this.hash4Mask = iArr.length - 1;
    }

    static int getHash4Size(int i) {
        int i2 = i - 1;
        int i3 = i2 | (i2 >>> 1);
        int i4 = i3 | (i3 >>> 2);
        int i5 = i4 | (i4 >>> 4);
        int i6 = ((i5 | (i5 >>> 8)) >>> 1) | 65535;
        if (i6 > 16777216) {
            i6 >>>= 1;
        }
        return i6 + 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getMemoryUsage(int i) {
        return ((getHash4Size(i) + 66560) / 256) + 4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void calcHashes(byte[] bArr, int i) {
        int i2 = crcTable[bArr[i] & 255] ^ (bArr[i + 1] & 255);
        this.hash2Value = i2 & 1023;
        int i3 = i2 ^ ((bArr[i + 2] & 255) << 8);
        this.hash3Value = 65535 & i3;
        this.hash4Value = ((crcTable[bArr[i + 3] & 255] << 5) ^ i3) & this.hash4Mask;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getHash2Pos() {
        return this.hash2Table[this.hash2Value];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getHash3Pos() {
        return this.hash3Table[this.hash3Value];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getHash4Pos() {
        return this.hash4Table[this.hash4Value];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void normalize(int i) {
        LZEncoder.normalize(this.hash2Table, i);
        LZEncoder.normalize(this.hash3Table, i);
        LZEncoder.normalize(this.hash4Table, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateTables(int i) {
        this.hash2Table[this.hash2Value] = i;
        this.hash3Table[this.hash3Value] = i;
        this.hash4Table[this.hash4Value] = i;
    }
}
