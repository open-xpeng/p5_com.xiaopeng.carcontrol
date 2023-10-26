package org.tukaani.xz.index;

import org.tukaani.xz.XZIOException;
import org.tukaani.xz.common.Util;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public abstract class IndexBase {
    private final XZIOException invalidIndexException;
    long blocksSum = 0;
    long uncompressedSum = 0;
    long indexListSize = 0;
    long recordCount = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public IndexBase(XZIOException xZIOException) {
        this.invalidIndexException = xZIOException;
    }

    private long getUnpaddedIndexSize() {
        return Util.getVLISize(this.recordCount) + 1 + this.indexListSize + 4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void add(long j, long j2) throws XZIOException {
        this.blocksSum += (3 + j) & (-4);
        this.uncompressedSum += j2;
        this.indexListSize += Util.getVLISize(j) + Util.getVLISize(j2);
        this.recordCount++;
        if (this.blocksSum < 0 || this.uncompressedSum < 0 || getIndexSize() > Util.BACKWARD_SIZE_MAX || getStreamSize() < 0) {
            throw this.invalidIndexException;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getIndexPaddingSize() {
        return (int) (3 & (4 - getUnpaddedIndexSize()));
    }

    public long getIndexSize() {
        return (getUnpaddedIndexSize() + 3) & (-4);
    }

    public long getStreamSize() {
        return this.blocksSum + 12 + getIndexSize() + 12;
    }
}
