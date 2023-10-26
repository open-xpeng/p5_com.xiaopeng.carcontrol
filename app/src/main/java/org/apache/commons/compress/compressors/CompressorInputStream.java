package org.apache.commons.compress.compressors;

import java.io.InputStream;

/* loaded from: classes3.dex */
public abstract class CompressorInputStream extends InputStream {
    private long bytesRead = 0;

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
