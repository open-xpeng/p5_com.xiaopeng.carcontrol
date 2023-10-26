package org.tukaani.xz;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

/* loaded from: classes3.dex */
class UncompressedLZMA2OutputStream extends FinishableOutputStream {
    private FinishableOutputStream out;
    private final DataOutputStream outData;
    private final byte[] uncompBuf = new byte[65536];
    private int uncompPos = 0;
    private boolean dictResetNeeded = true;
    private boolean finished = false;
    private IOException exception = null;
    private final byte[] tempBuf = new byte[1];

    /* JADX INFO: Access modifiers changed from: package-private */
    public UncompressedLZMA2OutputStream(FinishableOutputStream finishableOutputStream) {
        Objects.requireNonNull(finishableOutputStream);
        this.out = finishableOutputStream;
        this.outData = new DataOutputStream(finishableOutputStream);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getMemoryUsage() {
        return 70;
    }

    private void writeChunk() throws IOException {
        this.outData.writeByte(this.dictResetNeeded ? 1 : 2);
        this.outData.writeShort(this.uncompPos - 1);
        this.outData.write(this.uncompBuf, 0, this.uncompPos);
        this.uncompPos = 0;
        this.dictResetNeeded = false;
    }

    private void writeEndMarker() throws IOException {
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
        if (this.finished) {
            throw new XZIOException("Stream finished or closed");
        }
        try {
            if (this.uncompPos > 0) {
                writeChunk();
            }
            this.out.write(0);
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.out != null) {
            if (!this.finished) {
                try {
                    writeEndMarker();
                } catch (IOException unused) {
                }
            }
            try {
                this.out.close();
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
        writeEndMarker();
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
            if (this.uncompPos > 0) {
                writeChunk();
            }
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
            throw new XZIOException("Stream finished or closed");
        }
        while (i2 > 0) {
            try {
                int min = Math.min(this.uncompBuf.length - this.uncompPos, i2);
                System.arraycopy(bArr, i, this.uncompBuf, this.uncompPos, min);
                i2 -= min;
                int i4 = this.uncompPos + min;
                this.uncompPos = i4;
                if (i4 == this.uncompBuf.length) {
                    writeChunk();
                }
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }
}
