package org.apache.commons.compress.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

/* loaded from: classes3.dex */
public class BitInputStream implements Closeable {
    private static final long[] MASKS = new long[64];
    private static final int MAXIMUM_CACHE_SIZE = 63;
    private long bitsCached = 0;
    private int bitsCachedSize = 0;
    private final ByteOrder byteOrder;
    private final InputStream in;

    static {
        for (int i = 1; i <= 63; i++) {
            long[] jArr = MASKS;
            jArr[i] = (jArr[i - 1] << 1) + 1;
        }
    }

    public BitInputStream(InputStream inputStream, ByteOrder byteOrder) {
        this.in = inputStream;
        this.byteOrder = byteOrder;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    public void clearBitCache() {
        this.bitsCached = 0L;
        this.bitsCachedSize = 0;
    }

    public long readBits(int i) throws IOException {
        long j;
        if (i < 0 || i > 63) {
            throw new IllegalArgumentException("count must not be negative or greater than 63");
        }
        while (this.bitsCachedSize < i) {
            long read = this.in.read();
            if (read < 0) {
                return read;
            }
            if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.bitsCached = (read << this.bitsCachedSize) | this.bitsCached;
            } else {
                long j2 = this.bitsCached << 8;
                this.bitsCached = j2;
                this.bitsCached = read | j2;
            }
            this.bitsCachedSize += 8;
        }
        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            long j3 = this.bitsCached;
            j = MASKS[i] & j3;
            this.bitsCached = j3 >>> i;
        } else {
            j = MASKS[i] & (this.bitsCached >> (this.bitsCachedSize - i));
        }
        this.bitsCachedSize -= i;
        return j;
    }
}
