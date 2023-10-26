package com.alibaba.sdk.android.oss.internal;

import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

/* loaded from: classes.dex */
public class CheckCRC64DownloadInputStream extends CheckedInputStream {
    private long mClientCRC64;
    private String mRequestId;
    private long mServerCRC64;
    private long mTotalBytesRead;
    private long mTotalLength;

    public CheckCRC64DownloadInputStream(InputStream inputStream, Checksum checksum, long j, long j2, String str) {
        super(inputStream, checksum);
        this.mTotalLength = j;
        this.mServerCRC64 = j2;
        this.mRequestId = str;
    }

    @Override // java.util.zip.CheckedInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int read = super.read();
        checkCRC64(read);
        return read;
    }

    @Override // java.util.zip.CheckedInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = super.read(bArr, i, i2);
        checkCRC64(read);
        return read;
    }

    private void checkCRC64(int i) throws IOException {
        long j = this.mTotalBytesRead + i;
        this.mTotalBytesRead = j;
        if (j >= this.mTotalLength) {
            long value = getChecksum().getValue();
            this.mClientCRC64 = value;
            OSSUtils.checkChecksum(Long.valueOf(value), Long.valueOf(this.mServerCRC64), this.mRequestId);
        }
    }

    public long getClientCRC64() {
        return this.mClientCRC64;
    }
}
