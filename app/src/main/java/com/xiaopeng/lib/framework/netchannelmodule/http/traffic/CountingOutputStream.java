package com.xiaopeng.lib.framework.netchannelmodule.http.traffic;

import com.xiaopeng.lib.framework.netchannelmodule.http.statistic.BaseHttpCounter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class CountingOutputStream extends FilterOutputStream {
    protected ICollector mCollector;
    protected BaseHttpCounter mCounter;

    public CountingOutputStream(ICollector iCollector, OutputStream outputStream) throws IOException {
        super(outputStream);
        this.mCollector = iCollector;
    }

    public CountingOutputStream setStatisticCounter(BaseHttpCounter baseHttpCounter) {
        this.mCounter = baseHttpCounter;
        return this;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i) throws IOException {
        this.out.write(i);
        BaseHttpCounter baseHttpCounter = this.mCounter;
        if (baseHttpCounter != null) {
            baseHttpCounter.addSentSize(this.mCollector.getDomain(), 1L);
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        BaseHttpCounter baseHttpCounter;
        this.out.write(bArr, i, i2);
        if (i2 <= 0 || (baseHttpCounter = this.mCounter) == null) {
            return;
        }
        baseHttpCounter.addSentSize(this.mCollector.getDomain(), i2);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        super.flush();
    }
}
