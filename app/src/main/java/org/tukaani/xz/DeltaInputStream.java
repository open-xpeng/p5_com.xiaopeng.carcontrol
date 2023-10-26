package org.tukaani.xz;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/* loaded from: classes3.dex */
public class DeltaInputStream extends InputStream {
    public static final int DISTANCE_MAX = 256;
    public static final int DISTANCE_MIN = 1;
    private final org.tukaani.xz.delta.DeltaDecoder delta;
    private InputStream in;
    private IOException exception = null;
    private final byte[] tempBuf = new byte[1];

    public DeltaInputStream(InputStream inputStream, int i) {
        Objects.requireNonNull(inputStream);
        this.in = inputStream;
        this.delta = new org.tukaani.xz.delta.DeltaDecoder(i);
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        InputStream inputStream = this.in;
        if (inputStream != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                return inputStream.available();
            }
            throw iOException;
        }
        throw new XZIOException("Stream closed");
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        InputStream inputStream = this.in;
        if (inputStream != null) {
            try {
                inputStream.close();
            } finally {
                this.in = null;
            }
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.tempBuf, 0, 1) == -1) {
            return -1;
        }
        return this.tempBuf[0] & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        InputStream inputStream = this.in;
        if (inputStream != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                try {
                    int read = inputStream.read(bArr, i, i2);
                    if (read == -1) {
                        return -1;
                    }
                    this.delta.decode(bArr, i, read);
                    return read;
                } catch (IOException e) {
                    this.exception = e;
                    throw e;
                }
            }
            throw iOException;
        }
        throw new XZIOException("Stream closed");
    }
}
