package org.apache.commons.compress.archivers.zip;

import java.io.Serializable;

/* loaded from: classes3.dex */
public final class ZipLong implements Cloneable, Serializable {
    private static final int BYTE_1 = 1;
    private static final int BYTE_1_MASK = 65280;
    private static final int BYTE_1_SHIFT = 8;
    private static final int BYTE_2 = 2;
    private static final int BYTE_2_MASK = 16711680;
    private static final int BYTE_2_SHIFT = 16;
    private static final int BYTE_3 = 3;
    private static final long BYTE_3_MASK = 4278190080L;
    private static final int BYTE_3_SHIFT = 24;
    private static final long serialVersionUID = 1;
    private final long value;
    public static final ZipLong CFH_SIG = new ZipLong(33639248);
    public static final ZipLong LFH_SIG = new ZipLong(67324752);
    public static final ZipLong DD_SIG = new ZipLong(134695760);
    static final ZipLong ZIP64_MAGIC = new ZipLong(4294967295L);
    public static final ZipLong SINGLE_SEGMENT_SPLIT_MARKER = new ZipLong(808471376);
    public static final ZipLong AED_SIG = new ZipLong(134630224);

    public ZipLong(long j) {
        this.value = j;
    }

    public ZipLong(byte[] bArr) {
        this(bArr, 0);
    }

    public ZipLong(byte[] bArr, int i) {
        this.value = getValue(bArr, i);
    }

    public byte[] getBytes() {
        return getBytes(this.value);
    }

    public long getValue() {
        return this.value;
    }

    public static byte[] getBytes(long j) {
        byte[] bArr = new byte[4];
        putLong(j, bArr, 0);
        return bArr;
    }

    public static void putLong(long j, byte[] bArr, int i) {
        int i2 = i + 1;
        bArr[i] = (byte) (255 & j);
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((65280 & j) >> 8);
        bArr[i3] = (byte) ((16711680 & j) >> 16);
        bArr[i3 + 1] = (byte) ((j & BYTE_3_MASK) >> 24);
    }

    public void putLong(byte[] bArr, int i) {
        putLong(this.value, bArr, i);
    }

    public static long getValue(byte[] bArr, int i) {
        return ((bArr[i + 3] << 24) & BYTE_3_MASK) + ((bArr[i + 2] << 16) & BYTE_2_MASK) + ((bArr[i + 1] << 8) & 65280) + (bArr[i] & 255);
    }

    public static long getValue(byte[] bArr) {
        return getValue(bArr, 0);
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof ZipLong) && this.value == ((ZipLong) obj).getValue();
    }

    public int hashCode() {
        return (int) this.value;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return "ZipLong value: " + this.value;
    }
}
