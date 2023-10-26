package org.tukaani.xz;

import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class DeltaOutputStream extends FinishableOutputStream {
    private static final int FILTER_BUF_SIZE = 4096;
    private final org.tukaani.xz.delta.DeltaEncoder delta;
    private FinishableOutputStream out;
    private final byte[] filterBuf = new byte[4096];
    private boolean finished = false;
    private IOException exception = null;
    private final byte[] tempBuf = new byte[1];

    /* JADX INFO: Access modifiers changed from: package-private */
    public DeltaOutputStream(FinishableOutputStream finishableOutputStream, DeltaOptions deltaOptions) {
        this.out = finishableOutputStream;
        this.delta = new org.tukaani.xz.delta.DeltaEncoder(deltaOptions.getDistance());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getMemoryUsage() {
        return 5;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        FinishableOutputStream finishableOutputStream = this.out;
        if (finishableOutputStream != null) {
            try {
                finishableOutputStream.close();
            } catch (IOException e) {
                if (this.exception == null) {
                    this.exception = e;
                }
            }
            this.out = null;
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
    }

    @Override // org.tukaani.xz.FinishableOutputStream
    public void finish() throws IOException {
        if (this.finished) {
            return;
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
        try {
            this.out.finish();
            this.finished = true;
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
        if (this.finished) {
            throw new XZIOException("Stream finished or closed");
        }
        try {
            this.out.flush();
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        byte[] bArr = this.tempBuf;
        bArr[0] = (byte) i;
        write(bArr, 0, 1);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        if (i < 0 || i2 < 0 || (i3 = i + i2) < 0 || i3 > bArr.length) {
            throw new IndexOutOfBoundsException();
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
        if (this.finished) {
            throw new XZIOException("Stream finished");
        }
        while (i2 > 4096) {
            try {
                this.delta.encode(bArr, i, 4096, this.filterBuf);
                this.out.write(this.filterBuf);
                i += 4096;
                i2 -= 4096;
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
        this.delta.encode(bArr, i, i2, this.filterBuf);
        this.out.write(this.filterBuf, 0, i2);
    }
}
