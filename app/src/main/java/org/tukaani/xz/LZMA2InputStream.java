package org.tukaani.xz;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.tukaani.xz.lz.LZDecoder;
import org.tukaani.xz.lzma.LZMADecoder;
import org.tukaani.xz.rangecoder.RangeDecoderFromBuffer;

/* loaded from: classes3.dex */
public class LZMA2InputStream extends InputStream {
    private static final int COMPRESSED_SIZE_MAX = 65536;
    public static final int DICT_SIZE_MAX = 2147483632;
    public static final int DICT_SIZE_MIN = 4096;
    private boolean endReached;
    private IOException exception;
    private DataInputStream in;
    private boolean isLZMAChunk;
    private final LZDecoder lz;
    private LZMADecoder lzma;
    private boolean needDictReset;
    private boolean needProps;
    private final RangeDecoderFromBuffer rc;
    private final byte[] tempBuf;
    private int uncompressedSize;

    public LZMA2InputStream(InputStream inputStream, int i) {
        this(inputStream, i, null);
    }

    public LZMA2InputStream(InputStream inputStream, int i, byte[] bArr) {
        this.rc = new RangeDecoderFromBuffer(65536);
        this.uncompressedSize = 0;
        this.needDictReset = true;
        this.needProps = true;
        this.endReached = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        Objects.requireNonNull(inputStream);
        this.in = new DataInputStream(inputStream);
        this.lz = new LZDecoder(getDictSize(i), bArr);
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        this.needDictReset = false;
    }

    private void decodeChunkHeader() throws IOException {
        int readUnsignedByte = this.in.readUnsignedByte();
        if (readUnsignedByte == 0) {
            this.endReached = true;
            return;
        }
        if (readUnsignedByte >= 224 || readUnsignedByte == 1) {
            this.needProps = true;
            this.needDictReset = false;
            this.lz.reset();
        } else if (this.needDictReset) {
            throw new CorruptedInputException();
        }
        if (readUnsignedByte < 128) {
            if (readUnsignedByte > 2) {
                throw new CorruptedInputException();
            }
            this.isLZMAChunk = false;
            this.uncompressedSize = this.in.readUnsignedShort() + 1;
            return;
        }
        this.isLZMAChunk = true;
        int i = (readUnsignedByte & 31) << 16;
        this.uncompressedSize = i;
        this.uncompressedSize = i + this.in.readUnsignedShort() + 1;
        int readUnsignedShort = this.in.readUnsignedShort() + 1;
        if (readUnsignedByte >= 192) {
            this.needProps = false;
            decodeProps();
        } else if (this.needProps) {
            throw new CorruptedInputException();
        } else {
            if (readUnsignedByte >= 160) {
                this.lzma.reset();
            }
        }
        this.rc.prepareInputBuffer(this.in, readUnsignedShort);
    }

    private void decodeProps() throws IOException {
        int readUnsignedByte = this.in.readUnsignedByte();
        if (readUnsignedByte > 224) {
            throw new CorruptedInputException();
        }
        int i = readUnsignedByte / 45;
        int i2 = readUnsignedByte - ((i * 9) * 5);
        int i3 = i2 / 9;
        int i4 = i2 - (i3 * 9);
        if (i4 + i3 > 4) {
            throw new CorruptedInputException();
        }
        this.lzma = new LZMADecoder(this.lz, this.rc, i4, i3, i);
    }

    private static int getDictSize(int i) {
        if (i < 4096 || i > 2147483632) {
            throw new IllegalArgumentException(new StringBuffer().append("Unsupported dictionary size ").append(i).toString());
        }
        return (i + 15) & (-16);
    }

    public static int getMemoryUsage(int i) {
        return (getDictSize(i) / 1024) + 104;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (this.in != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                return this.uncompressedSize;
            }
            throw iOException;
        }
        throw new XZIOException("Stream closed");
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        DataInputStream dataInputStream = this.in;
        if (dataInputStream != null) {
            try {
                dataInputStream.close();
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
                        if (this.uncompressedSize == 0) {
                            decodeChunkHeader();
                            if (this.endReached) {
                                if (i4 == 0) {
                                    return -1;
                                }
                                return i4;
                            }
                        }
                        int min = Math.min(this.uncompressedSize, i2);
                        if (this.isLZMAChunk) {
                            this.lz.setLimit(min);
                            this.lzma.decode();
                            if (!this.rc.isInBufferOK()) {
                                throw new CorruptedInputException();
                            }
                        } else {
                            this.lz.copyUncompressed(this.in, min);
                        }
                        int flush = this.lz.flush(bArr, i);
                        i += flush;
                        i2 -= flush;
                        i4 += flush;
                        int i5 = this.uncompressedSize - flush;
                        this.uncompressedSize = i5;
                        if (i5 == 0 && (!this.rc.isFinished() || this.lz.hasPending())) {
                            throw new CorruptedInputException();
                        }
                    } catch (IOException e) {
                        this.exception = e;
                        throw e;
                    }
                }
                return i4;
            }
            throw iOException;
        }
        throw new XZIOException("Stream closed");
    }
}
