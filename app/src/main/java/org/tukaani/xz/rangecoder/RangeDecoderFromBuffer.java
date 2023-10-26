package org.tukaani.xz.rangecoder;

import androidx.core.view.ViewCompat;
import java.io.DataInputStream;
import java.io.IOException;
import org.tukaani.xz.CorruptedInputException;

/* loaded from: classes3.dex */
public final class RangeDecoderFromBuffer extends RangeDecoder {
    private static final int INIT_SIZE = 5;
    private final byte[] buf;
    private int pos = 0;
    private int end = 0;

    public RangeDecoderFromBuffer(int i) {
        this.buf = new byte[i - 5];
    }

    public boolean isFinished() {
        return this.pos == this.end && this.code == 0;
    }

    public boolean isInBufferOK() {
        return this.pos <= this.end;
    }

    @Override // org.tukaani.xz.rangecoder.RangeDecoder
    public void normalize() throws IOException {
        if ((this.range & ViewCompat.MEASURED_STATE_MASK) == 0) {
            try {
                byte[] bArr = this.buf;
                int i = this.pos;
                this.pos = i + 1;
                this.code = (this.code << 8) | (bArr[i] & 255);
                this.range <<= 8;
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new CorruptedInputException();
            }
        }
    }

    public void prepareInputBuffer(DataInputStream dataInputStream, int i) throws IOException {
        if (i < 5) {
            throw new CorruptedInputException();
        }
        if (dataInputStream.readUnsignedByte() != 0) {
            throw new CorruptedInputException();
        }
        this.code = dataInputStream.readInt();
        this.range = -1;
        this.pos = 0;
        int i2 = i - 5;
        this.end = i2;
        dataInputStream.readFully(this.buf, 0, i2);
    }
}
