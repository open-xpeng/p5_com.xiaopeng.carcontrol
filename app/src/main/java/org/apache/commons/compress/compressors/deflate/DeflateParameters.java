package org.apache.commons.compress.compressors.deflate;

/* loaded from: classes3.dex */
public class DeflateParameters {
    private boolean zlibHeader = true;
    private int compressionLevel = -1;

    public boolean withZlibHeader() {
        return this.zlibHeader;
    }

    public void setWithZlibHeader(boolean z) {
        this.zlibHeader = z;
    }

    public int getCompressionLevel() {
        return this.compressionLevel;
    }

    public void setCompressionLevel(int i) {
        if (i < -1 || i > 9) {
            throw new IllegalArgumentException("Invalid Deflate compression level: " + i);
        }
        this.compressionLevel = i;
    }
}
