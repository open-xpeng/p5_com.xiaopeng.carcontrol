package org.tukaani.xz;

import java.io.InputStream;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class LZMA2Decoder extends LZMA2Coder implements FilterDecoder {
    private int dictSize;

    /* JADX INFO: Access modifiers changed from: package-private */
    public LZMA2Decoder(byte[] bArr) throws UnsupportedOptionsException {
        if (bArr.length != 1 || (bArr[0] & 255) > 37) {
            throw new UnsupportedOptionsException("Unsupported LZMA2 properties");
        }
        int i = (bArr[0] & 1) | 2;
        this.dictSize = i;
        this.dictSize = i << ((bArr[0] >>> 1) + 11);
    }

    @Override // org.tukaani.xz.FilterDecoder
    public InputStream getInputStream(InputStream inputStream) {
        return new LZMA2InputStream(inputStream, this.dictSize);
    }

    @Override // org.tukaani.xz.FilterDecoder
    public int getMemoryUsage() {
        return LZMA2InputStream.getMemoryUsage(this.dictSize);
    }
}
