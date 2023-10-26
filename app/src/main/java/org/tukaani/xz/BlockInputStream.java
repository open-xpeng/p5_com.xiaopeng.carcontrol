package org.tukaani.xz;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.tukaani.xz.check.Check;
import org.tukaani.xz.common.DecoderUtil;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class BlockInputStream extends InputStream {
    private final Check check;
    private long compressedSizeInHeader;
    private long compressedSizeLimit;
    private InputStream filterChain;
    private final int headerSize;
    private final CountingInputStream inCounted;
    private final DataInputStream inData;
    private long uncompressedSizeInHeader;
    private long uncompressedSize = 0;
    private boolean endReached = false;
    private final byte[] tempBuf = new byte[1];

    public BlockInputStream(InputStream inputStream, Check check, int i, long j, long j2) throws IOException, IndexIndicatorException {
        String str;
        this.uncompressedSizeInHeader = -1L;
        this.compressedSizeInHeader = -1L;
        this.check = check;
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        this.inData = dataInputStream;
        byte[] bArr = new byte[1024];
        dataInputStream.readFully(bArr, 0, 1);
        if (bArr[0] == 0) {
            throw new IndexIndicatorException();
        }
        int i2 = ((bArr[0] & 255) + 1) * 4;
        this.headerSize = i2;
        dataInputStream.readFully(bArr, 1, i2 - 1);
        if (!DecoderUtil.isCRC32Valid(bArr, 0, i2 - 4, i2 - 4)) {
            throw new CorruptedInputException("XZ Block Header is corrupt");
        }
        if ((bArr[1] & 60) != 0) {
            throw new UnsupportedOptionsException("Unsupported options in XZ Block Header");
        }
        int i3 = (bArr[1] & 3) + 1;
        long[] jArr = new long[i3];
        byte[][] bArr2 = new byte[i3];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr, 2, i2 - 6);
        try {
            this.compressedSizeLimit = (9223372036854775804L - i2) - check.getSize();
            if ((bArr[1] & 64) != 0) {
                long decodeVLI = DecoderUtil.decodeVLI(byteArrayInputStream);
                this.compressedSizeInHeader = decodeVLI;
                if (decodeVLI != 0) {
                    str = "XZ Block Header is corrupt";
                    try {
                        if (decodeVLI <= this.compressedSizeLimit) {
                            this.compressedSizeLimit = decodeVLI;
                        }
                    } catch (IOException unused) {
                        throw new CorruptedInputException(str);
                    }
                }
                throw new CorruptedInputException();
            }
            if ((bArr[1] & 128) != 0) {
                this.uncompressedSizeInHeader = DecoderUtil.decodeVLI(byteArrayInputStream);
            }
            for (int i4 = 0; i4 < i3; i4++) {
                jArr[i4] = DecoderUtil.decodeVLI(byteArrayInputStream);
                long decodeVLI2 = DecoderUtil.decodeVLI(byteArrayInputStream);
                if (decodeVLI2 > byteArrayInputStream.available()) {
                    throw new CorruptedInputException();
                }
                bArr2[i4] = new byte[(int) decodeVLI2];
                byteArrayInputStream.read(bArr2[i4]);
            }
            for (int available = byteArrayInputStream.available(); available > 0; available--) {
                if (byteArrayInputStream.read() != 0) {
                    throw new UnsupportedOptionsException("Unsupported options in XZ Block Header");
                }
            }
            if (j != -1) {
                long size = this.headerSize + check.getSize();
                if (size >= j) {
                    throw new CorruptedInputException("XZ Index does not match a Block Header");
                }
                long j3 = j - size;
                if (j3 <= this.compressedSizeLimit) {
                    long j4 = this.compressedSizeInHeader;
                    if (j4 == -1 || j4 == j3) {
                        long j5 = this.uncompressedSizeInHeader;
                        if (j5 != -1 && j5 != j2) {
                            throw new CorruptedInputException("XZ Index does not match a Block Header");
                        }
                        this.compressedSizeLimit = j3;
                        this.compressedSizeInHeader = j3;
                        this.uncompressedSizeInHeader = j2;
                    }
                }
                throw new CorruptedInputException("XZ Index does not match a Block Header");
            }
            FilterDecoder[] filterDecoderArr = new FilterDecoder[i3];
            for (int i5 = 0; i5 < i3; i5++) {
                if (jArr[i5] == 33) {
                    filterDecoderArr[i5] = new LZMA2Decoder(bArr2[i5]);
                } else if (jArr[i5] == 3) {
                    filterDecoderArr[i5] = new DeltaDecoder(bArr2[i5]);
                } else if (!BCJDecoder.isBCJFilterID(jArr[i5])) {
                    throw new UnsupportedOptionsException(new StringBuffer().append("Unknown Filter ID ").append(jArr[i5]).toString());
                } else {
                    filterDecoderArr[i5] = new BCJDecoder(jArr[i5], bArr2[i5]);
                }
            }
            RawCoder.validate(filterDecoderArr);
            if (i >= 0) {
                int i6 = 0;
                for (int i7 = 0; i7 < i3; i7++) {
                    i6 += filterDecoderArr[i7].getMemoryUsage();
                }
                if (i6 > i) {
                    throw new MemoryLimitException(i6, i);
                }
            }
            CountingInputStream countingInputStream = new CountingInputStream(inputStream);
            this.inCounted = countingInputStream;
            this.filterChain = countingInputStream;
            for (int i8 = i3 - 1; i8 >= 0; i8--) {
                this.filterChain = filterDecoderArr[i8].getInputStream(this.filterChain);
            }
        } catch (IOException unused2) {
            str = "XZ Block Header is corrupt";
        }
    }

    private void validate() throws IOException {
        long size = this.inCounted.getSize();
        long j = this.compressedSizeInHeader;
        if (j == -1 || j == size) {
            long j2 = this.uncompressedSizeInHeader;
            if (j2 == -1 || j2 == this.uncompressedSize) {
                while (true) {
                    long j3 = 1 + size;
                    if ((size & 3) == 0) {
                        byte[] bArr = new byte[this.check.getSize()];
                        this.inData.readFully(bArr);
                        if (!Arrays.equals(this.check.finish(), bArr)) {
                            throw new CorruptedInputException(new StringBuffer().append("Integrity check (").append(this.check.getName()).append(") does not match").toString());
                        }
                        return;
                    } else if (this.inData.readUnsignedByte() != 0) {
                        throw new CorruptedInputException();
                    } else {
                        size = j3;
                    }
                }
            }
        }
        throw new CorruptedInputException();
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.filterChain.available();
    }

    public long getUncompressedSize() {
        return this.uncompressedSize;
    }

    public long getUnpaddedSize() {
        return this.headerSize + this.inCounted.getSize() + this.check.getSize();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.tempBuf, 0, 1) == -1) {
            return -1;
        }
        return this.tempBuf[0] & 255;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0059, code lost:
        if (r0 == (-1)) goto L25;
     */
    @Override // java.io.InputStream
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int read(byte[] r8, int r9, int r10) throws java.io.IOException {
        /*
            r7 = this;
            boolean r0 = r7.endReached
            r1 = -1
            if (r0 == 0) goto L6
            return r1
        L6:
            java.io.InputStream r0 = r7.filterChain
            int r0 = r0.read(r8, r9, r10)
            r2 = 1
            if (r0 <= 0) goto L59
            org.tukaani.xz.check.Check r3 = r7.check
            r3.update(r8, r9, r0)
            long r8 = r7.uncompressedSize
            long r3 = (long) r0
            long r8 = r8 + r3
            r7.uncompressedSize = r8
            org.tukaani.xz.CountingInputStream r8 = r7.inCounted
            long r8 = r8.getSize()
            r3 = 0
            int r5 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r5 < 0) goto L53
            long r5 = r7.compressedSizeLimit
            int r8 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r8 > 0) goto L53
            long r8 = r7.uncompressedSize
            int r3 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r3 < 0) goto L53
            long r3 = r7.uncompressedSizeInHeader
            r5 = -1
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 == 0) goto L3e
            int r5 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r5 > 0) goto L53
        L3e:
            if (r0 < r10) goto L44
            int r8 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r8 != 0) goto L60
        L44:
            java.io.InputStream r8 = r7.filterChain
            int r8 = r8.read()
            if (r8 != r1) goto L4d
            goto L5b
        L4d:
            org.tukaani.xz.CorruptedInputException r8 = new org.tukaani.xz.CorruptedInputException
            r8.<init>()
            throw r8
        L53:
            org.tukaani.xz.CorruptedInputException r8 = new org.tukaani.xz.CorruptedInputException
            r8.<init>()
            throw r8
        L59:
            if (r0 != r1) goto L60
        L5b:
            r7.validate()
            r7.endReached = r2
        L60:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.xz.BlockInputStream.read(byte[], int, int):int");
    }
}
