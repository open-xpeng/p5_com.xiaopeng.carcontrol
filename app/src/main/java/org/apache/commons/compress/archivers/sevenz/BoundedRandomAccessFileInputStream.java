package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/* loaded from: classes3.dex */
class BoundedRandomAccessFileInputStream extends InputStream {
    private long bytesRemaining;
    private final RandomAccessFile file;

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    public BoundedRandomAccessFileInputStream(RandomAccessFile randomAccessFile, long j) {
        this.file = randomAccessFile;
        this.bytesRemaining = j;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        long j = this.bytesRemaining;
        if (j > 0) {
            this.bytesRemaining = j - 1;
            return this.file.read();
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
        int read = this.file.read(bArr, i, i2);
        if (read >= 0) {
            this.bytesRemaining -= read;
        }
        return read;
    }
}
