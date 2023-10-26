package org.tukaani.xz;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.tukaani.xz.simple.SimpleFilter;

/* loaded from: classes3.dex */
class SimpleInputStream extends InputStream {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final int FILTER_BUF_SIZE = 4096;
    static /* synthetic */ Class class$org$tukaani$xz$SimpleInputStream;
    private InputStream in;
    private final SimpleFilter simpleFilter;
    private final byte[] filterBuf = new byte[4096];
    private int pos = 0;
    private int filtered = 0;
    private int unfiltered = 0;
    private boolean endReached = false;
    private IOException exception = null;
    private final byte[] tempBuf = new byte[1];

    static {
        if (class$org$tukaani$xz$SimpleInputStream == null) {
            class$org$tukaani$xz$SimpleInputStream = class$("org.tukaani.xz.SimpleInputStream");
        }
        $assertionsDisabled = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SimpleInputStream(InputStream inputStream, SimpleFilter simpleFilter) {
        Objects.requireNonNull(inputStream);
        if (!$assertionsDisabled && simpleFilter == null) {
            throw new AssertionError();
        }
        this.in = inputStream;
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

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (this.in != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                return this.filtered;
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

    /* JADX WARN: Code restructure failed: missing block: B:43:0x009f, code lost:
        if (r1 <= 0) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00a3, code lost:
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:?, code lost:
        return r1;
     */
    @Override // java.io.InputStream
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int read(byte[] r11, int r12, int r13) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 183
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.xz.SimpleInputStream.read(byte[], int, int):int");
    }
}
