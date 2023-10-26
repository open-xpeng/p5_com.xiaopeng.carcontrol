package org.apache.commons.compress.archivers.dump;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.apache.commons.compress.archivers.dump.DumpArchiveConstants;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes3.dex */
class TapeInputStream extends FilterInputStream {
    private static final int recordSize = 1024;
    private byte[] blockBuffer;
    private int blockSize;
    private long bytesRead;
    private int currBlkIdx;
    private boolean isCompressed;
    private int readOffset;

    public TapeInputStream(InputStream inputStream) {
        super(inputStream);
        this.blockBuffer = new byte[1024];
        this.currBlkIdx = -1;
        this.blockSize = 1024;
        this.readOffset = 1024;
        this.isCompressed = false;
        this.bytesRead = 0L;
    }

    public void resetBlockSize(int i, boolean z) throws IOException {
        this.isCompressed = z;
        int i2 = i * 1024;
        this.blockSize = i2;
        byte[] bArr = this.blockBuffer;
        byte[] bArr2 = new byte[i2];
        this.blockBuffer = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, 1024);
        readFully(this.blockBuffer, 1024, this.blockSize - 1024);
        this.currBlkIdx = 0;
        this.readOffset = 1024;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        int i = this.readOffset;
        int i2 = this.blockSize;
        return i < i2 ? i2 - i : this.in.available();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 % 1024 == 0) {
            int i3 = 0;
            while (i3 < i2) {
                if (this.readOffset == this.blockSize && !readBlock(true)) {
                    return -1;
                }
                int i4 = this.readOffset;
                int i5 = i2 - i3;
                int i6 = i4 + i5;
                int i7 = this.blockSize;
                if (i6 > i7) {
                    i5 = i7 - i4;
                }
                System.arraycopy(this.blockBuffer, i4, bArr, i, i5);
                this.readOffset += i5;
                i3 += i5;
                i += i5;
            }
            return i3;
        }
        throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        long j2 = 0;
        if (j % 1024 == 0) {
            while (j2 < j) {
                int i = this.readOffset;
                int i2 = this.blockSize;
                if (i == i2) {
                    if (!readBlock(j - j2 < ((long) i2))) {
                        return -1L;
                    }
                }
                int i3 = this.readOffset;
                long j3 = j - j2;
                int i4 = this.blockSize;
                if (i3 + j3 > i4) {
                    j3 = i4 - i3;
                }
                this.readOffset = (int) (i3 + j3);
                j2 += j3;
            }
            return j2;
        }
        throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.in == null || this.in == System.in) {
            return;
        }
        this.in.close();
    }

    public byte[] peek() throws IOException {
        if (this.readOffset != this.blockSize || readBlock(true)) {
            byte[] bArr = new byte[1024];
            System.arraycopy(this.blockBuffer, this.readOffset, bArr, 0, 1024);
            return bArr;
        }
        return null;
    }

    public byte[] readRecord() throws IOException {
        byte[] bArr = new byte[1024];
        if (-1 != read(bArr, 0, 1024)) {
            return bArr;
        }
        throw new ShortFileException();
    }

    private boolean readBlock(boolean z) throws IOException {
        boolean readFully;
        if (this.in == null) {
            throw new IOException("input buffer is closed");
        }
        if (!this.isCompressed || this.currBlkIdx == -1) {
            readFully = readFully(this.blockBuffer, 0, this.blockSize);
            this.bytesRead += this.blockSize;
        } else if (!readFully(this.blockBuffer, 0, 4)) {
            return false;
        } else {
            this.bytesRead += 4;
            int convert32 = DumpArchiveUtil.convert32(this.blockBuffer, 0);
            if (!((convert32 & 1) == 1)) {
                readFully = readFully(this.blockBuffer, 0, this.blockSize);
                this.bytesRead += this.blockSize;
            } else {
                int i = (convert32 >> 1) & 7;
                int i2 = (convert32 >> 4) & 268435455;
                byte[] bArr = new byte[i2];
                boolean readFully2 = readFully(bArr, 0, i2);
                this.bytesRead += i2;
                if (!z) {
                    Arrays.fill(this.blockBuffer, (byte) 0);
                } else {
                    int i3 = AnonymousClass1.$SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE[DumpArchiveConstants.COMPRESSION_TYPE.find(i & 3).ordinal()];
                    if (i3 != 1) {
                        if (i3 != 2) {
                            if (i3 == 3) {
                                throw new UnsupportedCompressionAlgorithmException("LZO");
                            }
                            throw new UnsupportedCompressionAlgorithmException();
                        }
                        throw new UnsupportedCompressionAlgorithmException("BZLIB2");
                    }
                    Inflater inflater = new Inflater();
                    try {
                        try {
                            inflater.setInput(bArr, 0, i2);
                            if (inflater.inflate(this.blockBuffer) != this.blockSize) {
                                throw new ShortFileException();
                            }
                        } catch (DataFormatException e) {
                            throw new DumpArchiveException("bad data", e);
                        }
                    } finally {
                        inflater.end();
                    }
                }
                readFully = readFully2;
            }
        }
        this.currBlkIdx++;
        this.readOffset = 0;
        return readFully;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: org.apache.commons.compress.archivers.dump.TapeInputStream$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE;

        static {
            int[] iArr = new int[DumpArchiveConstants.COMPRESSION_TYPE.values().length];
            $SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE = iArr;
            try {
                iArr[DumpArchiveConstants.COMPRESSION_TYPE.ZLIB.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE[DumpArchiveConstants.COMPRESSION_TYPE.BZLIB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE[DumpArchiveConstants.COMPRESSION_TYPE.LZO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private boolean readFully(byte[] bArr, int i, int i2) throws IOException {
        if (IOUtils.readFully(this.in, bArr, i, i2) >= i2) {
            return true;
        }
        throw new ShortFileException();
    }

    public long getBytesRead() {
        return this.bytesRead;
    }
}
