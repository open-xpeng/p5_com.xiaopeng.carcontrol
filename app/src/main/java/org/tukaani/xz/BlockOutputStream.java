package org.tukaani.xz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.tukaani.xz.check.Check;
import org.tukaani.xz.common.EncoderUtil;

/* loaded from: classes3.dex */
class BlockOutputStream extends FinishableOutputStream {
    private final Check check;
    private final long compressedSizeLimit;
    private FinishableOutputStream filterChain;
    private final int headerSize;
    private final OutputStream out;
    private final CountingOutputStream outCounted;
    private long uncompressedSize = 0;
    private final byte[] tempBuf = new byte[1];

    public BlockOutputStream(OutputStream outputStream, FilterEncoder[] filterEncoderArr, Check check) throws IOException {
        this.out = outputStream;
        this.check = check;
        CountingOutputStream countingOutputStream = new CountingOutputStream(outputStream);
        this.outCounted = countingOutputStream;
        this.filterChain = countingOutputStream;
        for (int length = filterEncoderArr.length - 1; length >= 0; length--) {
            this.filterChain = filterEncoderArr[length].getOutputStream(this.filterChain);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(filterEncoderArr.length - 1);
        for (int i = 0; i < filterEncoderArr.length; i++) {
            EncoderUtil.encodeVLI(byteArrayOutputStream, filterEncoderArr[i].getFilterID());
            byte[] filterProps = filterEncoderArr[i].getFilterProps();
            EncoderUtil.encodeVLI(byteArrayOutputStream, filterProps.length);
            byteArrayOutputStream.write(filterProps);
        }
        while ((byteArrayOutputStream.size() & 3) != 0) {
            byteArrayOutputStream.write(0);
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        int length2 = byteArray.length + 4;
        this.headerSize = length2;
        if (length2 > 1024) {
            throw new UnsupportedOptionsException();
        }
        byteArray[0] = (byte) (byteArray.length / 4);
        outputStream.write(byteArray);
        EncoderUtil.writeCRC32(outputStream, byteArray);
        this.compressedSizeLimit = (9223372036854775804L - length2) - check.getSize();
    }

    private void validate() throws IOException {
        long size = this.outCounted.getSize();
        if (size < 0 || size > this.compressedSizeLimit || this.uncompressedSize < 0) {
            throw new XZIOException("XZ Stream has grown too big");
        }
    }

    @Override // org.tukaani.xz.FinishableOutputStream
    public void finish() throws IOException {
        this.filterChain.finish();
        validate();
        for (long size = this.outCounted.getSize(); (3 & size) != 0; size++) {
            this.out.write(0);
        }
        this.out.write(this.check.finish());
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.filterChain.flush();
        validate();
    }

    public long getUncompressedSize() {
        return this.uncompressedSize;
    }

    public long getUnpaddedSize() {
        return this.headerSize + this.outCounted.getSize() + this.check.getSize();
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        byte[] bArr = this.tempBuf;
        bArr[0] = (byte) i;
        write(bArr, 0, 1);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.filterChain.write(bArr, i, i2);
        this.check.update(bArr, i, i2);
        this.uncompressedSize += i2;
        validate();
    }
}
