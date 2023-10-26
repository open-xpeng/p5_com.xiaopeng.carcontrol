package org.apache.commons.compress.compressors.snappy;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.utils.BoundedInputStream;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes3.dex */
public class FramedSnappyCompressorInputStream extends CompressorInputStream {
    private static final int COMPRESSED_CHUNK_TYPE = 0;
    static final long MASK_OFFSET = 2726488792L;
    private static final int MAX_SKIPPABLE_TYPE = 253;
    private static final int MAX_UNSKIPPABLE_TYPE = 127;
    private static final int MIN_UNSKIPPABLE_TYPE = 2;
    private static final int PADDING_CHUNK_TYPE = 254;
    private static final int STREAM_IDENTIFIER_TYPE = 255;
    private static final byte[] SZ_SIGNATURE = {-1, 6, 0, 0, 115, 78, 97, 80, 112, 89};
    private static final int UNCOMPRESSED_CHUNK_TYPE = 1;
    private final PureJavaCrc32C checksum;
    private SnappyCompressorInputStream currentCompressedChunk;
    private final FramedSnappyDialect dialect;
    private boolean endReached;
    private long expectedChecksum;
    private final PushbackInputStream in;
    private boolean inUncompressedChunk;
    private final byte[] oneByte;
    private int uncompressedBytesRemaining;

    static long unmask(long j) {
        long j2 = (j - MASK_OFFSET) & 4294967295L;
        return ((j2 << 15) | (j2 >> 17)) & 4294967295L;
    }

    public FramedSnappyCompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, FramedSnappyDialect.STANDARD);
    }

    public FramedSnappyCompressorInputStream(InputStream inputStream, FramedSnappyDialect framedSnappyDialect) throws IOException {
        this.oneByte = new byte[1];
        this.expectedChecksum = -1L;
        this.checksum = new PureJavaCrc32C();
        this.in = new PushbackInputStream(inputStream, 1);
        this.dialect = framedSnappyDialect;
        if (framedSnappyDialect.hasStreamIdentifier()) {
            readStreamIdentifier();
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.oneByte, 0, 1) == -1) {
            return -1;
        }
        return this.oneByte[0] & 255;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        SnappyCompressorInputStream snappyCompressorInputStream = this.currentCompressedChunk;
        if (snappyCompressorInputStream != null) {
            snappyCompressorInputStream.close();
            this.currentCompressedChunk = null;
        }
        this.in.close();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int readOnce = readOnce(bArr, i, i2);
        if (readOnce == -1) {
            readNextBlock();
            if (this.endReached) {
                return -1;
            }
            return readOnce(bArr, i, i2);
        }
        return readOnce;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (this.inUncompressedChunk) {
            return Math.min(this.uncompressedBytesRemaining, this.in.available());
        }
        SnappyCompressorInputStream snappyCompressorInputStream = this.currentCompressedChunk;
        if (snappyCompressorInputStream != null) {
            return snappyCompressorInputStream.available();
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int readOnce(byte[] r5, int r6, int r7) throws java.io.IOException {
        /*
            r4 = this;
            boolean r0 = r4.inUncompressedChunk
            r1 = -1
            if (r0 == 0) goto L1f
            int r0 = r4.uncompressedBytesRemaining
            int r7 = java.lang.Math.min(r0, r7)
            if (r7 != 0) goto Le
            return r1
        Le:
            java.io.PushbackInputStream r0 = r4.in
            int r7 = r0.read(r5, r6, r7)
            if (r7 == r1) goto L42
            int r0 = r4.uncompressedBytesRemaining
            int r0 = r0 - r7
            r4.uncompressedBytesRemaining = r0
            r4.count(r7)
            goto L42
        L1f:
            org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream r0 = r4.currentCompressedChunk
            if (r0 == 0) goto L43
            long r2 = r0.getBytesRead()
            org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream r0 = r4.currentCompressedChunk
            int r7 = r0.read(r5, r6, r7)
            if (r7 != r1) goto L38
            org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream r0 = r4.currentCompressedChunk
            r0.close()
            r0 = 0
            r4.currentCompressedChunk = r0
            goto L42
        L38:
            org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream r0 = r4.currentCompressedChunk
            long r0 = r0.getBytesRead()
            long r0 = r0 - r2
            r4.count(r0)
        L42:
            r1 = r7
        L43:
            if (r1 <= 0) goto L4a
            org.apache.commons.compress.compressors.snappy.PureJavaCrc32C r7 = r4.checksum
            r7.update(r5, r6, r1)
        L4a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorInputStream.readOnce(byte[], int, int):int");
    }

    private void readNextBlock() throws IOException {
        verifyLastChecksumAndReset();
        this.inUncompressedChunk = false;
        int readOneByte = readOneByte();
        if (readOneByte == -1) {
            this.endReached = true;
        } else if (readOneByte == 255) {
            this.in.unread(readOneByte);
            pushedBackBytes(1L);
            readStreamIdentifier();
            readNextBlock();
        } else if (readOneByte == PADDING_CHUNK_TYPE || (readOneByte > MAX_UNSKIPPABLE_TYPE && readOneByte <= MAX_SKIPPABLE_TYPE)) {
            skipBlock();
            readNextBlock();
        } else if (readOneByte >= 2 && readOneByte <= MAX_UNSKIPPABLE_TYPE) {
            throw new IOException("unskippable chunk with type " + readOneByte + " (hex " + Integer.toHexString(readOneByte) + ") detected.");
        } else {
            if (readOneByte == 1) {
                this.inUncompressedChunk = true;
                this.uncompressedBytesRemaining = readSize() - 4;
                this.expectedChecksum = unmask(readCrc());
            } else if (readOneByte == 0) {
                boolean usesChecksumWithCompressedChunks = this.dialect.usesChecksumWithCompressedChunks();
                long readSize = readSize() - (usesChecksumWithCompressedChunks ? 4 : 0);
                if (usesChecksumWithCompressedChunks) {
                    this.expectedChecksum = unmask(readCrc());
                } else {
                    this.expectedChecksum = -1L;
                }
                SnappyCompressorInputStream snappyCompressorInputStream = new SnappyCompressorInputStream(new BoundedInputStream(this.in, readSize));
                this.currentCompressedChunk = snappyCompressorInputStream;
                count(snappyCompressorInputStream.getBytesRead());
            } else {
                throw new IOException("unknown chunk type " + readOneByte + " detected.");
            }
        }
    }

    private long readCrc() throws IOException {
        byte[] bArr = new byte[4];
        int readFully = IOUtils.readFully(this.in, bArr);
        count(readFully);
        if (readFully == 4) {
            long j = 0;
            for (int i = 0; i < 4; i++) {
                j |= (bArr[i] & 255) << (i * 8);
            }
            return j;
        }
        throw new IOException("premature end of stream");
    }

    private int readSize() throws IOException {
        int i = 0;
        for (int i2 = 0; i2 < 3; i2++) {
            int readOneByte = readOneByte();
            if (readOneByte == -1) {
                throw new IOException("premature end of stream");
            }
            i |= readOneByte << (i2 * 8);
        }
        return i;
    }

    private void skipBlock() throws IOException {
        long readSize = readSize();
        long skip = IOUtils.skip(this.in, readSize);
        count(skip);
        if (skip != readSize) {
            throw new IOException("premature end of stream");
        }
    }

    private void readStreamIdentifier() throws IOException {
        byte[] bArr = new byte[10];
        int readFully = IOUtils.readFully(this.in, bArr);
        count(readFully);
        if (10 != readFully || !matches(bArr, 10)) {
            throw new IOException("Not a framed Snappy stream");
        }
    }

    private int readOneByte() throws IOException {
        int read = this.in.read();
        if (read != -1) {
            count(1);
            return read & 255;
        }
        return -1;
    }

    private void verifyLastChecksumAndReset() throws IOException {
        long j = this.expectedChecksum;
        if (j >= 0 && j != this.checksum.getValue()) {
            throw new IOException("Checksum verification failed");
        }
        this.expectedChecksum = -1L;
        this.checksum.reset();
    }

    public static boolean matches(byte[] bArr, int i) {
        byte[] bArr2 = SZ_SIGNATURE;
        if (i < bArr2.length) {
            return false;
        }
        if (bArr.length > bArr2.length) {
            byte[] bArr3 = new byte[bArr2.length];
            System.arraycopy(bArr, 0, bArr3, 0, bArr2.length);
            bArr = bArr3;
        }
        return Arrays.equals(bArr, bArr2);
    }
}
