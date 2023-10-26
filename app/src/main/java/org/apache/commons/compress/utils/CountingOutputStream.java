package org.apache.commons.compress.utils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes3.dex */
public class CountingOutputStream extends FilterOutputStream {
    private long bytesWritten;

    public CountingOutputStream(OutputStream outputStream) {
        super(outputStream);
        this.bytesWritten = 0L;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i) throws IOException {
        this.out.write(i);
        count(1L);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
        count(i2);
    }

    protected void count(long j) {
        if (j != -1) {
            this.bytesWritten += j;
        }
    }

    public long getBytesWritten() {
        return this.bytesWritten;
    }
}
