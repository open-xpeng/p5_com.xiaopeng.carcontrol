package org.tukaani.xz;

import java.io.IOException;
import java.util.Objects;
import org.tukaani.xz.simple.SimpleFilter;

/* loaded from: classes3.dex */
class SimpleOutputStream extends FinishableOutputStream {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final int FILTER_BUF_SIZE = 4096;
    static /* synthetic */ Class class$org$tukaani$xz$SimpleOutputStream;
    private FinishableOutputStream out;
    private final SimpleFilter simpleFilter;
    private final byte[] filterBuf = new byte[4096];
    private int pos = 0;
    private int unfiltered = 0;
    private IOException exception = null;
    private boolean finished = false;
    private final byte[] tempBuf = new byte[1];

    static {
        if (class$org$tukaani$xz$SimpleOutputStream == null) {
            class$org$tukaani$xz$SimpleOutputStream = class$("org.tukaani.xz.SimpleOutputStream");
        }
        $assertionsDisabled = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SimpleOutputStream(FinishableOutputStream finishableOutputStream, SimpleFilter simpleFilter) {
        Objects.requireNonNull(finishableOutputStream);
        this.out = finishableOutputStream;
        this.simpleFilter = simpleFilter;
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getMemoryUsage() {
        return 5;
    }

    private void writePending() throws IOException {
        if (!$assertionsDisabled && this.finished) {
            throw new AssertionError();
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
        try {
            this.out.write(this.filterBuf, this.pos, this.unfiltered);
            this.finished = true;
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
                    writePending();
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
        writePending();
        try {
            this.out.finish();
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        throw new UnsupportedOptionsException("Flushing is not supported");
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
            int min = Math.min(i2, 4096 - (this.pos + this.unfiltered));
            System.arraycopy(bArr, i, this.filterBuf, this.pos + this.unfiltered, min);
            i += min;
            i2 -= min;
            int i4 = this.unfiltered + min;
            this.unfiltered = i4;
            int code = this.simpleFilter.code(this.filterBuf, this.pos, i4);
            if (!$assertionsDisabled && code > this.unfiltered) {
                throw new AssertionError();
            }
            this.unfiltered -= code;
            try {
                this.out.write(this.filterBuf, this.pos, code);
                int i5 = this.pos + code;
                this.pos = i5;
                int i6 = this.unfiltered;
                if (i5 + i6 == 4096) {
                    byte[] bArr2 = this.filterBuf;
                    System.arraycopy(bArr2, i5, bArr2, 0, i6);
                    this.pos = 0;
                }
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }
}
