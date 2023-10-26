package com.xiaopeng.lib.framework.netchannelmodule.http.traffic;

import com.xiaopeng.lib.framework.netchannelmodule.http.statistic.BaseHttpCounter;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class CountingInputStream extends FilterInputStream {
    protected ICollector mCollector;
    protected BaseHttpCounter mCounter;

    public CountingInputStream(ICollector iCollector, InputStream inputStream) throws IOException {
        super(inputStream);
        this.mCollector = iCollector;
    }

    public CountingInputStream setStatisticCounter(BaseHttpCounter baseHttpCounter) {
        this.mCounter = baseHttpCounter;
        return this;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        BaseHttpCounter baseHttpCounter;
        int read = this.in.read();
        if (read != -1 && (baseHttpCounter = this.mCounter) != null) {
            baseHttpCounter.addReceivedSize(this.mCollector.getDomain(), 1L);
        }
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        BaseHttpCounter baseHttpCounter;
        int read = this.in.read(bArr, i, i2);
        if (read > 0 && (baseHttpCounter = this.mCounter) != null) {
            baseHttpCounter.addReceivedSize(this.mCollector.getDomain(), read);
        }
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
    }
}
