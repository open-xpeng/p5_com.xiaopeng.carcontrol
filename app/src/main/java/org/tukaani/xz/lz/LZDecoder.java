package org.tukaani.xz.lz;

import java.io.DataInputStream;
import java.io.IOException;
import org.tukaani.xz.CorruptedInputException;

/* loaded from: classes3.dex */
public final class LZDecoder {
    private final byte[] buf;
    private int full;
    private int pos;
    private int start;
    private int limit = 0;
    private int pendingLen = 0;
    private int pendingDist = 0;

    public LZDecoder(int i, byte[] bArr) {
        this.start = 0;
        this.pos = 0;
        this.full = 0;
        byte[] bArr2 = new byte[i];
        this.buf = bArr2;
        if (bArr != null) {
            int min = Math.min(bArr.length, i);
            this.pos = min;
            this.full = min;
            this.start = min;
            System.arraycopy(bArr, bArr.length - min, bArr2, 0, min);
        }
    }

    public void copyUncompressed(DataInputStream dataInputStream, int i) throws IOException {
        int min = Math.min(this.buf.length - this.pos, i);
        dataInputStream.readFully(this.buf, this.pos, min);
        int i2 = this.pos + min;
        this.pos = i2;
        if (this.full < i2) {
            this.full = i2;
        }
    }

    public int flush(byte[] bArr, int i) {
        int i2 = this.pos;
        int i3 = this.start;
        int i4 = i2 - i3;
        byte[] bArr2 = this.buf;
        if (i2 == bArr2.length) {
            this.pos = 0;
        }
        System.arraycopy(bArr2, i3, bArr, i, i4);
        this.start = this.pos;
        return i4;
    }

    public int getByte(int i) {
        int i2 = this.pos;
        int i3 = (i2 - i) - 1;
        if (i >= i2) {
            i3 += this.buf.length;
        }
        return this.buf[i3] & 255;
    }

    public int getPos() {
        return this.pos;
    }

    public boolean hasPending() {
        return this.pendingLen > 0;
    }

    public boolean hasSpace() {
        return this.pos < this.limit;
    }

    public void putByte(byte b) {
        byte[] bArr = this.buf;
        int i = this.pos;
        int i2 = i + 1;
        this.pos = i2;
        bArr[i] = b;
        if (this.full < i2) {
            this.full = i2;
        }
    }

    public void repeat(int i, int i2) throws IOException {
        int i3;
        if (i < 0 || i >= this.full) {
            throw new CorruptedInputException();
        }
        int min = Math.min(this.limit - this.pos, i2);
        this.pendingLen = i2 - min;
        this.pendingDist = i;
        int i4 = this.pos;
        int i5 = (i4 - i) - 1;
        if (i >= i4) {
            i5 += this.buf.length;
        }
        do {
            byte[] bArr = this.buf;
            int i6 = this.pos;
            i3 = i6 + 1;
            this.pos = i3;
            int i7 = i5 + 1;
            bArr[i6] = bArr[i5];
            i5 = i7 == bArr.length ? 0 : i7;
            min--;
        } while (min > 0);
        if (this.full < i3) {
            this.full = i3;
        }
    }

    public void repeatPending() throws IOException {
        int i = this.pendingLen;
        if (i > 0) {
            repeat(this.pendingDist, i);
        }
    }

    public void reset() {
        this.start = 0;
        this.pos = 0;
        this.full = 0;
        this.limit = 0;
        byte[] bArr = this.buf;
        bArr[bArr.length - 1] = 0;
    }

    public void setLimit(int i) {
        byte[] bArr = this.buf;
        int length = bArr.length;
        int i2 = this.pos;
        if (length - i2 <= i) {
            this.limit = bArr.length;
        } else {
            this.limit = i2 + i;
        }
    }
}
