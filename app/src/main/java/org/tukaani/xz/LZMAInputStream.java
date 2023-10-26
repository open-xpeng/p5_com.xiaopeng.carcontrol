package org.tukaani.xz;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.tukaani.xz.lz.LZDecoder;
import org.tukaani.xz.lzma.LZMADecoder;
import org.tukaani.xz.rangecoder.RangeDecoderFromStream;

/* loaded from: classes3.dex */
public class LZMAInputStream extends InputStream {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final int DICT_SIZE_MAX = 2147483632;
    static /* synthetic */ Class class$org$tukaani$xz$LZMAInputStream;
    private boolean endReached;
    private IOException exception;
    private InputStream in;
    private LZDecoder lz;
    private LZMADecoder lzma;
    private RangeDecoderFromStream rc;
    private long remainingSize;
    private final byte[] tempBuf;

    static {
        if (class$org$tukaani$xz$LZMAInputStream == null) {
            class$org$tukaani$xz$LZMAInputStream = class$("org.tukaani.xz.LZMAInputStream");
        }
        $assertionsDisabled = true;
    }

    public LZMAInputStream(InputStream inputStream) throws IOException {
        this(inputStream, -1);
    }

    public LZMAInputStream(InputStream inputStream, int i) throws IOException {
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        byte readByte = dataInputStream.readByte();
        int i2 = 0;
        for (int i3 = 0; i3 < 4; i3++) {
            i2 |= dataInputStream.readUnsignedByte() << (i3 * 8);
        }
        long j = 0;
        for (int i4 = 0; i4 < 8; i4++) {
            j |= dataInputStream.readUnsignedByte() << (i4 * 8);
        }
        int memoryUsage = getMemoryUsage(i2, readByte);
        if (i != -1 && memoryUsage > i) {
            throw new MemoryLimitException(memoryUsage, i);
        }
        initialize(inputStream, j, readByte, i2, null);
    }

    public LZMAInputStream(InputStream inputStream, long j, byte b, int i) throws IOException {
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        initialize(inputStream, j, b, i, null);
    }

    public LZMAInputStream(InputStream inputStream, long j, byte b, int i, byte[] bArr) throws IOException {
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        initialize(inputStream, j, b, i, bArr);
    }

    public LZMAInputStream(InputStream inputStream, long j, int i, int i2, int i3, int i4, byte[] bArr) throws IOException {
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        initialize(inputStream, j, i, i2, i3, i4, bArr);
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private static int getDictSize(int i) {
        if (i < 0 || i > 2147483632) {
            throw new IllegalArgumentException("LZMA dictionary is too big for this implementation");
        }
        if (i < 4096) {
            i = 4096;
        }
        return (i + 15) & (-16);
    }

    public static int getMemoryUsage(int i, byte b) throws UnsupportedOptionsException, CorruptedInputException {
        if (i < 0 || i > 2147483632) {
            throw new UnsupportedOptionsException("LZMA dictionary is too big for this implementation");
        }
        int i2 = b & 255;
        if (i2 <= 224) {
            int i3 = i2 % 45;
            int i4 = i3 / 9;
            return getMemoryUsage(i, i3 - (i4 * 9), i4);
        }
        throw new CorruptedInputException("Invalid LZMA properties byte");
    }

    public static int getMemoryUsage(int i, int i2, int i3) {
        if (i2 < 0 || i2 > 8 || i3 < 0 || i3 > 4) {
            throw new IllegalArgumentException("Invalid lc or lp");
        }
        return (getDictSize(i) / 1024) + 10 + ((1536 << (i2 + i3)) / 1024);
    }

    private void initialize(InputStream inputStream, long j, byte b, int i, byte[] bArr) throws IOException {
        if (j < -1) {
            throw new UnsupportedOptionsException("Uncompressed size is too big");
        }
        int i2 = b & 255;
        if (i2 > 224) {
            throw new CorruptedInputException("Invalid LZMA properties byte");
        }
        int i3 = i2 / 45;
        int i4 = i2 - ((i3 * 9) * 5);
        int i5 = i4 / 9;
        int i6 = i4 - (i5 * 9);
        if (i < 0 || i > 2147483632) {
            throw new UnsupportedOptionsException("LZMA dictionary is too big for this implementation");
        }
        initialize(inputStream, j, i6, i5, i3, i, bArr);
    }

    private void initialize(InputStream inputStream, long j, int i, int i2, int i3, int i4, byte[] bArr) throws IOException {
        if (j < -1 || i < 0 || i > 8 || i2 < 0 || i2 > 4 || i3 < 0 || i3 > 4) {
            throw new IllegalArgumentException();
        }
        this.in = inputStream;
        int dictSize = getDictSize(i4);
        if (j >= 0 && dictSize > j) {
            dictSize = getDictSize((int) j);
        }
        this.lz = new LZDecoder(getDictSize(dictSize), bArr);
        this.rc = new RangeDecoderFromStream(inputStream);
        this.lzma = new LZMADecoder(this.lz, this.rc, i, i2, i3);
        this.remainingSize = j;
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
                        long j = this.remainingSize;
                        this.lz.setLimit((j < 0 || j >= ((long) i2)) ? i2 : (int) j);
                        try {
                            this.lzma.decode();
                        } catch (CorruptedInputException e) {
                            if (this.remainingSize != -1 || !this.lzma.endMarkerDetected()) {
                                throw e;
                            }
                            this.endReached = true;
                            this.rc.normalize();
                        }
                        int flush = this.lz.flush(bArr, i);
                        i += flush;
                        i2 -= flush;
                        i4 += flush;
                        long j2 = this.remainingSize;
                        if (j2 >= 0) {
                            long j3 = j2 - flush;
                            this.remainingSize = j3;
                            if (!$assertionsDisabled && j3 < 0) {
                                throw new AssertionError();
                            }
                            if (j3 == 0) {
                                this.endReached = true;
                            }
                        }
                        if (this.endReached) {
                            if (!this.rc.isFinished() || this.lz.hasPending()) {
                                throw new CorruptedInputException();
                            }
                            if (i4 == 0) {
                                return -1;
                            }
                            return i4;
                        }
                    } catch (IOException e2) {
                        this.exception = e2;
                        throw e2;
                    }
                }
                return i4;
            }
            throw iOException;
        }
        throw new XZIOException("Stream closed");
    }
}
