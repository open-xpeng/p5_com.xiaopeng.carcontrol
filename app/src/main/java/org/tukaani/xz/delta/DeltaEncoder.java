package org.tukaani.xz.delta;

/* loaded from: classes3.dex */
public class DeltaEncoder extends DeltaCoder {
    public DeltaEncoder(int i) {
        super(i);
    }

    public void encode(byte[] bArr, int i, int i2, byte[] bArr2) {
        for (int i3 = 0; i3 < i2; i3++) {
            byte b = this.history[(this.distance + this.pos) & 255];
            byte[] bArr3 = this.history;
            int i4 = this.pos;
            this.pos = i4 - 1;
            int i5 = i + i3;
            bArr3[i4 & 255] = bArr[i5];
            bArr2[i3] = (byte) (bArr[i5] - b);
        }
    }
}
