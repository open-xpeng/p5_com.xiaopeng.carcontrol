package org.apache.commons.compress.compressors.snappy;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes3.dex */
public class SnappyCompressorInputStream extends CompressorInputStream {
    public static final int DEFAULT_BLOCK_SIZE = 32768;
    private static final int TAG_MASK = 3;
    private final int blockSize;
    private final byte[] decompressBuf;
    private boolean endReached;
    private final InputStream in;
    private final byte[] oneByte;
    private int readIndex;
    private final int size;
    private int uncompressedBytesRemaining;
    private int writeIndex;

    public SnappyCompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, 32768);
    }

    public SnappyCompressorInputStream(InputStream inputStream, int i) throws IOException {
        this.oneByte = new byte[1];
        this.endReached = false;
        this.in = inputStream;
        this.blockSize = i;
        this.decompressBuf = new byte[i * 3];
        this.readIndex = 0;
        this.writeIndex = 0;
        int readSize = (int) readSize();
        this.size = readSize;
        this.uncompressedBytesRemaining = readSize;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.oneByte, 0, 1) == -1) {
            return -1;
        }
        return this.oneByte[0] & 255;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    @Override // java.io.InputStream
    public int available() {
        return this.writeIndex - this.readIndex;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.endReached) {
            return -1;
        }
        int available = available();
        if (i2 > available) {
            fill(i2 - available);
        }
        int min = Math.min(i2, available());
        if (min != 0 || i2 <= 0) {
            System.arraycopy(this.decompressBuf, this.readIndex, bArr, i, min);
            int i3 = this.readIndex + min;
            this.readIndex = i3;
            if (i3 > this.blockSize) {
                slideBuffer();
            }
            return min;
        }
        return -1;
    }

    private void fill(int i) throws IOException {
        int i2 = this.uncompressedBytesRemaining;
        if (i2 == 0) {
            this.endReached = true;
        }
        int min = Math.min(i, i2);
        while (min > 0) {
            int readOneByte = readOneByte();
            int i3 = 0;
            int i4 = readOneByte & 3;
            if (i4 == 0) {
                i3 = readLiteralLength(readOneByte);
                if (expandLiteral(i3)) {
                    return;
                }
            } else if (i4 == 1) {
                i3 = ((readOneByte >> 2) & 7) + 4;
                if (expandCopy(((readOneByte & 224) << 3) | readOneByte(), i3)) {
                    return;
                }
            } else if (i4 == 2) {
                i3 = (readOneByte >> 2) + 1;
                if (expandCopy(readOneByte() | (readOneByte() << 8), i3)) {
                    return;
                }
            } else if (i4 != 3) {
                continue;
            } else {
                i3 = (readOneByte >> 2) + 1;
                if (expandCopy(readOneByte() | (readOneByte() << 8) | (readOneByte() << 16) | (readOneByte() << 24), i3)) {
                    return;
                }
            }
            min -= i3;
            this.uncompressedBytesRemaining -= i3;
        }
    }

    private void slideBuffer() {
        byte[] bArr = this.decompressBuf;
        int i = this.blockSize;
        System.arraycopy(bArr, i, bArr, 0, i * 2);
        int i2 = this.writeIndex;
        int i3 = this.blockSize;
        this.writeIndex = i2 - i3;
        this.readIndex -= i3;
    }

    private int readLiteralLength(int i) throws IOException {
        int readOneByte;
        int readOneByte2;
        int i2 = i >> 2;
        switch (i2) {
            case 60:
                i2 = readOneByte();
                break;
            case 61:
                readOneByte = readOneByte();
                readOneByte2 = readOneByte() << 8;
                i2 = readOneByte | readOneByte2;
                break;
            case 62:
                readOneByte = readOneByte() | (readOneByte() << 8);
                readOneByte2 = readOneByte() << 16;
                i2 = readOneByte | readOneByte2;
                break;
            case 63:
                i2 = (int) (readOneByte() | (readOneByte() << 8) | (readOneByte() << 16) | (readOneByte() << 24));
                break;
        }
        return i2 + 1;
    }

    private boolean expandLiteral(int i) throws IOException {
        int readFully = IOUtils.readFully(this.in, this.decompressBuf, this.writeIndex, i);
        count(readFully);
        if (i != readFully) {
            throw new IOException("Premature end of stream");
        }
        int i2 = this.writeIndex + i;
        this.writeIndex = i2;
        return i2 >= this.blockSize * 2;
    }

    private boolean expandCopy(long j, int i) throws IOException {
        if (j <= this.blockSize) {
            int i2 = (int) j;
            if (i2 == 1) {
                byte b = this.decompressBuf[this.writeIndex - 1];
                for (int i3 = 0; i3 < i; i3++) {
                    byte[] bArr = this.decompressBuf;
                    int i4 = this.writeIndex;
                    this.writeIndex = i4 + 1;
                    bArr[i4] = b;
                }
            } else if (i < i2) {
                byte[] bArr2 = this.decompressBuf;
                int i5 = this.writeIndex;
                System.arraycopy(bArr2, i5 - i2, bArr2, i5, i);
                this.writeIndex += i;
            } else {
                int i6 = i / i2;
                int i7 = i - (i2 * i6);
                while (true) {
                    int i8 = i6 - 1;
                    if (i6 == 0) {
                        break;
                    }
                    byte[] bArr3 = this.decompressBuf;
                    int i9 = this.writeIndex;
                    System.arraycopy(bArr3, i9 - i2, bArr3, i9, i2);
                    this.writeIndex += i2;
                    i6 = i8;
                }
                if (i7 > 0) {
                    byte[] bArr4 = this.decompressBuf;
                    int i10 = this.writeIndex;
                    System.arraycopy(bArr4, i10 - i2, bArr4, i10, i7);
                    this.writeIndex += i7;
                }
            }
            return this.writeIndex >= this.blockSize * 2;
        }
        throw new IOException("Offset is larger than block size");
    }

    private int readOneByte() throws IOException {
        int read = this.in.read();
        if (read == -1) {
            throw new IOException("Premature end of stream");
        }
        count(1);
        return read & 255;
    }

    private long readSize() throws IOException {
        int i = 0;
        long j = 0;
        while (true) {
            int readOneByte = readOneByte();
            int i2 = i + 1;
            j |= (readOneByte & 127) << (i * 7);
            if ((readOneByte & 128) == 0) {
                return j;
            }
            i = i2;
        }
    }

    public int getSize() {
        return this.size;
    }
}
