package org.tukaani.xz;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.tukaani.xz.check.Check;
import org.tukaani.xz.common.DecoderUtil;
import org.tukaani.xz.common.StreamFlags;
import org.tukaani.xz.index.IndexHash;

/* loaded from: classes3.dex */
public class SingleXZInputStream extends InputStream {
    private BlockInputStream blockDecoder;
    private Check check;
    private boolean endReached;
    private IOException exception;
    private InputStream in;
    private final IndexHash indexHash;
    private int memoryLimit;
    private StreamFlags streamHeaderFlags;
    private final byte[] tempBuf;

    public SingleXZInputStream(InputStream inputStream) throws IOException {
        this.blockDecoder = null;
        this.indexHash = new IndexHash();
        this.endReached = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        initialize(inputStream, -1);
    }

    public SingleXZInputStream(InputStream inputStream, int i) throws IOException {
        this.blockDecoder = null;
        this.indexHash = new IndexHash();
        this.endReached = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        initialize(inputStream, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SingleXZInputStream(InputStream inputStream, int i, byte[] bArr) throws IOException {
        this.blockDecoder = null;
        this.indexHash = new IndexHash();
        this.endReached = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        initialize(inputStream, i, bArr);
    }

    private void initialize(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[12];
        new DataInputStream(inputStream).readFully(bArr);
        initialize(inputStream, i, bArr);
    }

    private void initialize(InputStream inputStream, int i, byte[] bArr) throws IOException {
        this.in = inputStream;
        this.memoryLimit = i;
        StreamFlags decodeStreamHeader = DecoderUtil.decodeStreamHeader(bArr);
        this.streamHeaderFlags = decodeStreamHeader;
        this.check = Check.getInstance(decodeStreamHeader.checkType);
    }

    private void validateStreamFooter() throws IOException {
        byte[] bArr = new byte[12];
        new DataInputStream(this.in).readFully(bArr);
        StreamFlags decodeStreamFooter = DecoderUtil.decodeStreamFooter(bArr);
        if (!DecoderUtil.areStreamFlagsEqual(this.streamHeaderFlags, decodeStreamFooter) || this.indexHash.getIndexSize() != decodeStreamFooter.backwardSize) {
            throw new CorruptedInputException("XZ Stream Footer does not match Stream Header");
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (this.in != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                BlockInputStream blockInputStream = this.blockDecoder;
                if (blockInputStream == null) {
                    return 0;
                }
                return blockInputStream.available();
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

    public String getCheckName() {
        return this.check.getName();
    }

    public int getCheckType() {
        return this.streamHeaderFlags.checkType;
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
                        if (this.blockDecoder == null) {
                            try {
                                this.blockDecoder = new BlockInputStream(this.in, this.check, this.memoryLimit, -1L, -1L);
                            } catch (IndexIndicatorException unused) {
                                this.indexHash.validate(this.in);
                                validateStreamFooter();
                                this.endReached = true;
                                if (i4 > 0) {
                                    return i4;
                                }
                                return -1;
                            }
                        }
                        int read = this.blockDecoder.read(bArr, i, i2);
                        if (read > 0) {
                            i4 += read;
                            i += read;
                            i2 -= read;
                        } else if (read == -1) {
                            this.indexHash.add(this.blockDecoder.getUnpaddedSize(), this.blockDecoder.getUncompressedSize());
                            this.blockDecoder = null;
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
