package org.tukaani.xz.check;

/* loaded from: classes3.dex */
public class CRC64 extends Check {
    private static final long[] crcTable = new long[256];
    private static final long poly = -3932672073523589310L;
    private long crc = -1;

    static {
        for (int i = 0; i < crcTable.length; i++) {
            long j = i;
            for (int i2 = 0; i2 < 8; i2++) {
                int i3 = ((j & 1) > 1L ? 1 : ((j & 1) == 1L ? 0 : -1));
                j >>>= 1;
                if (i3 == 0) {
                    j ^= poly;
                }
            }
            crcTable[i] = j;
        }
    }

    public CRC64() {
        this.size = 8;
        this.name = "CRC64";
    }

    @Override // org.tukaani.xz.check.Check
    public byte[] finish() {
        long j = ~this.crc;
        this.crc = -1L;
        byte[] bArr = new byte[8];
        for (int i = 0; i < 8; i++) {
            bArr[i] = (byte) (j >> (i * 8));
        }
        return bArr;
    }

    @Override // org.tukaani.xz.check.Check
    public void update(byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            long[] jArr = crcTable;
            int i4 = i + 1;
            byte b = bArr[i];
            long j = this.crc;
            this.crc = (j >>> 8) ^ jArr[(b ^ ((int) j)) & 255];
            i = i4;
        }
    }
}
