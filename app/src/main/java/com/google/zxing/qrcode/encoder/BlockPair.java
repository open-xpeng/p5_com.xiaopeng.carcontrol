package com.google.zxing.qrcode.encoder;

/* loaded from: classes.dex */
final class BlockPair {
    private final byte[] dataBytes;
    private final byte[] errorCorrectionBytes;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BlockPair(byte[] bArr, byte[] bArr2) {
        this.dataBytes = bArr;
        this.errorCorrectionBytes = bArr2;
    }

    public byte[] getDataBytes() {
        return this.dataBytes;
    }

    public byte[] getErrorCorrectionBytes() {
        return this.errorCorrectionBytes;
    }
}
