package org.apache.commons.compress.archivers;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public abstract class ArchiveInputStream extends InputStream {
    private static final int BYTE_MASK = 255;
    private final byte[] SINGLE = new byte[1];
    private long bytesRead = 0;

    public boolean canReadEntryData(ArchiveEntry archiveEntry) {
        return true;
    }

    public abstract ArchiveEntry getNextEntry() throws IOException;

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.SINGLE, 0, 1) == -1) {
            return -1;
        }
        return this.SINGLE[0] & 255;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void count(int i) {
        count(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void count(long j) {
        if (j != -1) {
            this.bytesRead += j;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void pushedBackBytes(long j) {
        this.bytesRead -= j;
    }

    @Deprecated
    public int getCount() {
        return (int) this.bytesRead;
    }

    public long getBytesRead() {
        return this.bytesRead;
    }
}
