package org.tukaani.xz;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class XZInputStream extends InputStream {
    private boolean endReached;
    private IOException exception;
    private InputStream in;
    private final int memoryLimit;
    private final byte[] tempBuf;
    private SingleXZInputStream xzIn;

    public XZInputStream(InputStream inputStream) throws IOException {
        this(inputStream, -1);
    }

    public XZInputStream(InputStream inputStream, int i) throws IOException {
        this.endReached = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        this.in = inputStream;
        this.memoryLimit = i;
        this.xzIn = new SingleXZInputStream(inputStream, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void prepareNextStream() throws java.io.IOException {
        /*
            r6 = this;
            java.io.DataInputStream r0 = new java.io.DataInputStream
            java.io.InputStream r1 = r6.in
            r0.<init>(r1)
            r1 = 12
            byte[] r1 = new byte[r1]
        Lb:
            r2 = 0
            r3 = 1
            int r4 = r0.read(r1, r2, r3)
            r5 = -1
            if (r4 != r5) goto L17
            r6.endReached = r3
            return
        L17:
            r4 = 3
            r0.readFully(r1, r3, r4)
            r2 = r1[r2]
            if (r2 != 0) goto L2c
            r2 = r1[r3]
            if (r2 != 0) goto L2c
            r2 = 2
            r2 = r1[r2]
            if (r2 != 0) goto L2c
            r2 = r1[r4]
            if (r2 == 0) goto Lb
        L2c:
            r2 = 4
            r3 = 8
            r0.readFully(r1, r2, r3)
            org.tukaani.xz.SingleXZInputStream r0 = new org.tukaani.xz.SingleXZInputStream     // Catch: org.tukaani.xz.XZFormatException -> L3e
            java.io.InputStream r2 = r6.in     // Catch: org.tukaani.xz.XZFormatException -> L3e
            int r3 = r6.memoryLimit     // Catch: org.tukaani.xz.XZFormatException -> L3e
            r0.<init>(r2, r3, r1)     // Catch: org.tukaani.xz.XZFormatException -> L3e
            r6.xzIn = r0     // Catch: org.tukaani.xz.XZFormatException -> L3e
            return
        L3e:
            org.tukaani.xz.CorruptedInputException r0 = new org.tukaani.xz.CorruptedInputException
            java.lang.String r1 = "Garbage after a valid XZ Stream"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.xz.XZInputStream.prepareNextStream():void");
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (this.in != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                SingleXZInputStream singleXZInputStream = this.xzIn;
                if (singleXZInputStream == null) {
                    return 0;
                }
                return singleXZInputStream.available();
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
        int i3;
        if (i < 0 || i2 < 0 || (i3 = i + i2) < 0 || i3 > bArr.length) {
            throw new IndexOutOfBoundsException();
        }
        int i4 = 0;
        if (i2 == 0) {
            return 0;
        }
        if (this.in != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                if (this.endReached) {
                    return -1;
                }
                while (i2 > 0) {
                    try {
                        if (this.xzIn == null) {
                            prepareNextStream();
                            if (this.endReached) {
                                if (i4 == 0) {
                                    return -1;
                                }
                                return i4;
                            }
                        }
                        int read = this.xzIn.read(bArr, i, i2);
                        if (read > 0) {
                            i4 += read;
                            i += read;
                            i2 -= read;
                        } else if (read == -1) {
                            this.xzIn = null;
                        }
                    } catch (IOException e) {
                        this.exception = e;
                        if (i4 == 0) {
                            throw e;
                        }
                    }
                }
                return i4;
            }
            throw iOException;
        }
        throw new XZIOException("Stream closed");
    }
}
