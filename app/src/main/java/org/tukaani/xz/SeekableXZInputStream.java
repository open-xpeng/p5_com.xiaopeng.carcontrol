package org.tukaani.xz;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import org.tukaani.xz.check.Check;
import org.tukaani.xz.index.BlockInfo;
import org.tukaani.xz.index.IndexDecoder;

/* loaded from: classes3.dex */
public class SeekableXZInputStream extends SeekableInputStream {
    static final /* synthetic */ boolean $assertionsDisabled;
    static /* synthetic */ Class class$org$tukaani$xz$SeekableXZInputStream;
    private int blockCount;
    private BlockInputStream blockDecoder;
    private Check check;
    private int checkTypes;
    private final BlockInfo curBlockInfo;
    private long curPos;
    private boolean endReached;
    private IOException exception;
    private SeekableInputStream in;
    private int indexMemoryUsage;
    private long largestBlockSize;
    private final int memoryLimit;
    private final BlockInfo queriedBlockInfo;
    private boolean seekNeeded;
    private long seekPos;
    private final ArrayList streams;
    private final byte[] tempBuf;
    private long uncompressedSize;

    static {
        if (class$org$tukaani$xz$SeekableXZInputStream == null) {
            class$org$tukaani$xz$SeekableXZInputStream = class$("org.tukaani.xz.SeekableXZInputStream");
        }
        $assertionsDisabled = true;
    }

    public SeekableXZInputStream(SeekableInputStream seekableInputStream) throws IOException {
        this(seekableInputStream, -1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0090, code lost:
        if (r7.backwardSize >= r8) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0092, code lost:
        r16.check = org.tukaani.xz.check.Check.getInstance(r7.checkType);
        r16.checkTypes |= r10 << r7.checkType;
        r17.seek(r8 - r7.backwardSize);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00aa, code lost:
        r4 = new org.tukaani.xz.index.IndexDecoder(r17, r7, r5, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00b7, code lost:
        r16.indexMemoryUsage += r4.getMemoryUsage();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00c0, code lost:
        if (r13 < 0) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00c2, code lost:
        r13 = r13 - r4.getMemoryUsage();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00c9, code lost:
        if (org.tukaani.xz.SeekableXZInputStream.$assertionsDisabled != false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00cb, code lost:
        if (r13 < 0) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00d3, code lost:
        throw new java.lang.AssertionError();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00dc, code lost:
        if (r16.largestBlockSize >= r4.getLargestBlockSize()) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00de, code lost:
        r16.largestBlockSize = r4.getLargestBlockSize();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00e4, code lost:
        r2 = r4.getStreamSize() - 12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00eb, code lost:
        if (r8 < r2) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00ed, code lost:
        r2 = r8 - r2;
        r17.seek(r2);
        r11.readFully(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00fd, code lost:
        if (org.tukaani.xz.common.DecoderUtil.areStreamFlagsEqual(org.tukaani.xz.common.DecoderUtil.decodeStreamHeader(r12), r7) == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00ff, code lost:
        r4 = r16.uncompressedSize + r4.getUncompressedSize();
        r16.uncompressedSize = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x010c, code lost:
        if (r4 < 0) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x010e, code lost:
        r4 = r16.blockCount + r4.getRecordCount();
        r16.blockCount = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0117, code lost:
        if (r4 < 0) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x012b, code lost:
        throw new org.tukaani.xz.UnsupportedOptionsException("XZ file has over 2147483647 Blocks");
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0133, code lost:
        throw new org.tukaani.xz.UnsupportedOptionsException("XZ file is too big");
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x013b, code lost:
        throw new org.tukaani.xz.CorruptedInputException("XZ Stream Footer does not match Stream Header");
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0143, code lost:
        throw new org.tukaani.xz.CorruptedInputException("XZ Index indicates too big compressed size for the XZ Stream");
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0144, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0147, code lost:
        if (org.tukaani.xz.SeekableXZInputStream.$assertionsDisabled != false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0150, code lost:
        throw new java.lang.AssertionError();
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0151, code lost:
        r0 = r0.getMemoryNeeded();
        r3 = r16.indexMemoryUsage;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x015e, code lost:
        throw new org.tukaani.xz.MemoryLimitException(r0 + r3, r13 + r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0166, code lost:
        throw new org.tukaani.xz.CorruptedInputException("Backward Size in XZ Stream Footer is too big");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SeekableXZInputStream(org.tukaani.xz.SeekableInputStream r17, int r18) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 462
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.xz.SeekableXZInputStream.<init>(org.tukaani.xz.SeekableInputStream, int):void");
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private void initBlockDecoder() throws IOException {
        try {
            this.blockDecoder = null;
            this.blockDecoder = new BlockInputStream(this.in, this.check, this.memoryLimit, this.curBlockInfo.unpaddedSize, this.curBlockInfo.uncompressedSize);
        } catch (IndexIndicatorException unused) {
            throw new CorruptedInputException();
        } catch (MemoryLimitException e) {
            if (!$assertionsDisabled && this.memoryLimit < 0) {
                throw new AssertionError();
            }
            int memoryNeeded = e.getMemoryNeeded();
            int i = this.indexMemoryUsage;
            throw new MemoryLimitException(memoryNeeded + i, this.memoryLimit + i);
        }
    }

    private void locateBlockByNumber(BlockInfo blockInfo, int i) {
        if (i < 0 || i >= this.blockCount) {
            throw new IndexOutOfBoundsException(new StringBuffer().append("Invalid XZ Block number: ").append(i).toString());
        }
        if (blockInfo.blockNumber == i) {
            return;
        }
        int i2 = 0;
        while (true) {
            IndexDecoder indexDecoder = (IndexDecoder) this.streams.get(i2);
            if (indexDecoder.hasRecord(i)) {
                indexDecoder.setBlockInfo(blockInfo, i);
                return;
            }
            i2++;
        }
    }

    private void locateBlockByPos(BlockInfo blockInfo, long j) {
        IndexDecoder indexDecoder;
        if (j < 0 || j >= this.uncompressedSize) {
            throw new IndexOutOfBoundsException(new StringBuffer().append("Invalid uncompressed position: ").append(j).toString());
        }
        int i = 0;
        while (true) {
            indexDecoder = (IndexDecoder) this.streams.get(i);
            if (indexDecoder.hasUncompressedOffset(j)) {
                break;
            }
            i++;
        }
        indexDecoder.locateBlock(blockInfo, j);
        boolean z = $assertionsDisabled;
        if (!z && (blockInfo.compressedOffset & 3) != 0) {
            throw new AssertionError();
        }
        if (!z && blockInfo.uncompressedSize <= 0) {
            throw new AssertionError();
        }
        if (!z && j < blockInfo.uncompressedOffset) {
            throw new AssertionError();
        }
        if (!z && j >= blockInfo.uncompressedOffset + blockInfo.uncompressedSize) {
            throw new AssertionError();
        }
    }

    private void seek() throws IOException {
        if (!this.seekNeeded) {
            if (this.curBlockInfo.hasNext()) {
                this.curBlockInfo.setNext();
                initBlockDecoder();
                return;
            }
            this.seekPos = this.curPos;
        }
        this.seekNeeded = false;
        long j = this.seekPos;
        if (j >= this.uncompressedSize) {
            this.curPos = j;
            this.blockDecoder = null;
            this.endReached = true;
            return;
        }
        this.endReached = false;
        locateBlockByPos(this.curBlockInfo, j);
        if (this.curPos <= this.curBlockInfo.uncompressedOffset || this.curPos > this.seekPos) {
            this.in.seek(this.curBlockInfo.compressedOffset);
            this.check = Check.getInstance(this.curBlockInfo.getCheckType());
            initBlockDecoder();
            this.curPos = this.curBlockInfo.uncompressedOffset;
        }
        long j2 = this.seekPos;
        long j3 = this.curPos;
        if (j2 > j3) {
            long j4 = j2 - j3;
            if (this.blockDecoder.skip(j4) != j4) {
                throw new CorruptedInputException();
            }
            this.curPos = this.seekPos;
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        BlockInputStream blockInputStream;
        if (this.in != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                if (this.endReached || this.seekNeeded || (blockInputStream = this.blockDecoder) == null) {
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
        SeekableInputStream seekableInputStream = this.in;
        if (seekableInputStream != null) {
            try {
                seekableInputStream.close();
            } finally {
                this.in = null;
            }
        }
    }

    public int getBlockCheckType(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return this.queriedBlockInfo.getCheckType();
    }

    public long getBlockCompPos(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return this.queriedBlockInfo.compressedOffset;
    }

    public long getBlockCompSize(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return (this.queriedBlockInfo.unpaddedSize + 3) & (-4);
    }

    public int getBlockCount() {
        return this.blockCount;
    }

    public int getBlockNumber(long j) {
        locateBlockByPos(this.queriedBlockInfo, j);
        return this.queriedBlockInfo.blockNumber;
    }

    public long getBlockPos(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return this.queriedBlockInfo.uncompressedOffset;
    }

    public long getBlockSize(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return this.queriedBlockInfo.uncompressedSize;
    }

    public int getCheckTypes() {
        return this.checkTypes;
    }

    public int getIndexMemoryUsage() {
        return this.indexMemoryUsage;
    }

    public long getLargestBlockSize() {
        return this.largestBlockSize;
    }

    public int getStreamCount() {
        return this.streams.size();
    }

    @Override // org.tukaani.xz.SeekableInputStream
    public long length() {
        return this.uncompressedSize;
    }

    @Override // org.tukaani.xz.SeekableInputStream
    public long position() throws IOException {
        if (this.in != null) {
            return this.seekNeeded ? this.seekPos : this.curPos;
        }
        throw new XZIOException("Stream closed");
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
                try {
                    if (this.seekNeeded) {
                        seek();
                    }
                } catch (IOException e) {
                    e = e;
                    if (e instanceof EOFException) {
                        e = new CorruptedInputException();
                    }
                    this.exception = e;
                    if (i4 == 0) {
                        throw e;
                    }
                }
                if (this.endReached) {
                    return -1;
                }
                while (i2 > 0) {
                    if (this.blockDecoder == null) {
                        seek();
                        if (this.endReached) {
                            break;
                        }
                    }
                    int read = this.blockDecoder.read(bArr, i, i2);
                    if (read > 0) {
                        this.curPos += read;
                        i4 += read;
                        i += read;
                        i2 -= read;
                    } else if (read == -1) {
                        this.blockDecoder = null;
                    }
                }
                return i4;
            }
            throw iOException;
        }
        throw new XZIOException("Stream closed");
    }

    @Override // org.tukaani.xz.SeekableInputStream
    public void seek(long j) throws IOException {
        if (this.in == null) {
            throw new XZIOException("Stream closed");
        }
        if (j < 0) {
            throw new XZIOException(new StringBuffer().append("Negative seek position: ").append(j).toString());
        }
        this.seekPos = j;
        this.seekNeeded = true;
    }

    public void seekToBlock(int i) throws IOException {
        if (this.in == null) {
            throw new XZIOException("Stream closed");
        }
        if (i < 0 || i >= this.blockCount) {
            throw new XZIOException(new StringBuffer().append("Invalid XZ Block number: ").append(i).toString());
        }
        this.seekPos = getBlockPos(i);
        this.seekNeeded = true;
    }
}
