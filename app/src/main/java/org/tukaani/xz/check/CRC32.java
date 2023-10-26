package org.tukaani.xz.check;

/* loaded from: classes3.dex */
public class CRC32 extends Check {
    private final java.util.zip.CRC32 state = new java.util.zip.CRC32();

    public CRC32() {
        this.size = 4;
        this.name = "CRC32";
    }

    @Override // org.tukaani.xz.check.Check
    public byte[] finish() {
        long value = this.state.getValue();
        byte[] bArr = {(byte) value, (byte) (value >>> 8), (byte) (value >>> 16), (byte) (value >>> 24)};
        this.state.reset();
        return bArr;
    }

    @Override // org.tukaani.xz.check.Check
    public void update(byte[] bArr, int i, int i2) {
        this.state.update(bArr, i, i2);
    }
}
