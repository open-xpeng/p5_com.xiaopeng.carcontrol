package org.apache.commons.compress.utils;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class BoundedInputStream extends InputStream {
    private long bytesRemaining;
    private final InputStream in;

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    public BoundedInputStream(InputStream inputStream, long j) {
        this.in = inputStream;
        this.bytesRemaining = j;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        long j = this.bytesRemaining;
        if (j > 0) {
            this.bytesRemaining = j - 1;
            return this.in.read();
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        long j = this.bytesRemaining;
        if (j == 0) {
            return -1;
        }
        if (i2 > j) {
            i2 = (int) j;
        }
        int read = this.in.read(bArr, i, i2);
        if (read >= 0) {
            this.bytesRemaining -= read;
        }
        return read;
    }
}
