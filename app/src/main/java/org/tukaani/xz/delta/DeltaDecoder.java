package org.tukaani.xz.delta;

/* loaded from: classes3.dex */
public class DeltaDecoder extends DeltaCoder {
    public DeltaDecoder(int i) {
        super(i);
    }

    public void decode(byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            bArr[i] = (byte) (bArr[i] + this.history[(this.distance + this.pos) & 255]);
            byte[] bArr2 = this.history;
            int i4 = this.pos;
            this.pos = i4 - 1;
            bArr2[i4 & 255] = bArr[i];
            i++;
        }
    }
}
