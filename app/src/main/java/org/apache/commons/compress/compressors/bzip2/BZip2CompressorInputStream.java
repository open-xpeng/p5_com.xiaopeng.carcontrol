package org.apache.commons.compress.compressors.bzip2;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import org.apache.commons.compress.compressors.CompressorInputStream;

/* loaded from: classes3.dex */
public class BZip2CompressorInputStream extends CompressorInputStream implements BZip2Constants {
    private static final int EOF = 0;
    private static final int NO_RAND_PART_A_STATE = 5;
    private static final int NO_RAND_PART_B_STATE = 6;
    private static final int NO_RAND_PART_C_STATE = 7;
    private static final int RAND_PART_A_STATE = 2;
    private static final int RAND_PART_B_STATE = 3;
    private static final int RAND_PART_C_STATE = 4;
    private static final int START_BLOCK_STATE = 1;
    private boolean blockRandomised;
    private int blockSize100k;
    private int bsBuff;
    private int bsLive;
    private int computedBlockCRC;
    private int computedCombinedCRC;
    private final CRC crc;
    private int currentState;
    private Data data;
    private final boolean decompressConcatenated;
    private InputStream in;
    private int last;
    private int nInUse;
    private int origPtr;
    private int storedBlockCRC;
    private int storedCombinedCRC;
    private int su_ch2;
    private int su_chPrev;
    private int su_count;
    private int su_i2;
    private int su_j2;
    private int su_rNToGo;
    private int su_rTPos;
    private int su_tPos;
    private char su_z;

    public BZip2CompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, false);
    }

    public BZip2CompressorInputStream(InputStream inputStream, boolean z) throws IOException {
        this.crc = new CRC();
        this.currentState = 1;
        this.in = inputStream;
        this.decompressConcatenated = z;
        init(true);
        initBlock();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.in != null) {
            int read0 = read0();
            count(read0 < 0 ? -1 : 1);
            return read0;
        }
        throw new IOException("stream closed");
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i >= 0) {
            if (i2 < 0) {
                throw new IndexOutOfBoundsException("len(" + i2 + ") < 0.");
            }
            int i3 = i + i2;
            if (i3 > bArr.length) {
                throw new IndexOutOfBoundsException("offs(" + i + ") + len(" + i2 + ") > dest.length(" + bArr.length + ").");
            }
            if (this.in != null) {
                if (i2 == 0) {
                    return 0;
                }
                int i4 = i;
                while (i4 < i3) {
                    int read0 = read0();
                    if (read0 < 0) {
                        break;
                    }
                    bArr[i4] = (byte) read0;
                    count(1);
                    i4++;
                }
                if (i4 == i) {
                    return -1;
                }
                return i4 - i;
            }
            throw new IOException("stream closed");
        }
        throw new IndexOutOfBoundsException("offs(" + i + ") < 0.");
    }

    private void makeMaps() {
        boolean[] zArr = this.data.inUse;
        byte[] bArr = this.data.seqToUnseq;
        int i = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            if (zArr[i2]) {
                bArr[i] = (byte) i2;
                i++;
            }
        }
        this.nInUse = i;
    }

    private int read0() throws IOException {
        switch (this.currentState) {
            case 0:
                return -1;
            case 1:
                return setupBlock();
            case 2:
                throw new IllegalStateException();
            case 3:
                return setupRandPartB();
            case 4:
                return setupRandPartC();
            case 5:
                throw new IllegalStateException();
            case 6:
                return setupNoRandPartB();
            case 7:
                return setupNoRandPartC();
            default:
                throw new IllegalStateException();
        }
    }

    private boolean init(boolean z) throws IOException {
        InputStream inputStream = this.in;
        if (inputStream == null) {
            throw new IOException("No InputStream");
        }
        int read = inputStream.read();
        if (read != -1 || z) {
            int read2 = this.in.read();
            int read3 = this.in.read();
            if (read != 66 || read2 != 90 || read3 != 104) {
                throw new IOException(z ? "Stream is not in the BZip2 format" : "Garbage after a valid BZip2 stream");
            }
            int read4 = this.in.read();
            if (read4 < 49 || read4 > 57) {
                throw new IOException("BZip2 block size is invalid");
            }
            this.blockSize100k = read4 - 48;
            this.bsLive = 0;
            this.computedCombinedCRC = 0;
            return true;
        }
        return false;
    }

    private void initBlock() throws IOException {
        do {
            char bsGetUByte = bsGetUByte();
            char bsGetUByte2 = bsGetUByte();
            char bsGetUByte3 = bsGetUByte();
            char bsGetUByte4 = bsGetUByte();
            char bsGetUByte5 = bsGetUByte();
            char bsGetUByte6 = bsGetUByte();
            if (bsGetUByte != 23 || bsGetUByte2 != 'r' || bsGetUByte3 != 'E' || bsGetUByte4 != '8' || bsGetUByte5 != 'P' || bsGetUByte6 != 144) {
                if (bsGetUByte != '1' || bsGetUByte2 != 'A' || bsGetUByte3 != 'Y' || bsGetUByte4 != '&' || bsGetUByte5 != 'S' || bsGetUByte6 != 'Y') {
                    this.currentState = 0;
                    throw new IOException("bad block header");
                }
                this.storedBlockCRC = bsGetInt();
                this.blockRandomised = bsR(1) == 1;
                if (this.data == null) {
                    this.data = new Data(this.blockSize100k);
                }
                getAndMoveToFrontDecode();
                this.crc.initialiseCRC();
                this.currentState = 1;
                return;
            }
        } while (!complete());
    }

    private void endBlock() throws IOException {
        int finalCRC = this.crc.getFinalCRC();
        this.computedBlockCRC = finalCRC;
        int i = this.storedBlockCRC;
        if (i != finalCRC) {
            int i2 = this.storedCombinedCRC;
            int i3 = (i2 >>> 31) | (i2 << 1);
            this.computedCombinedCRC = i3;
            this.computedCombinedCRC = i3 ^ i;
            throw new IOException("BZip2 CRC error");
        }
        int i4 = this.computedCombinedCRC;
        int i5 = (i4 >>> 31) | (i4 << 1);
        this.computedCombinedCRC = i5;
        this.computedCombinedCRC = finalCRC ^ i5;
    }

    private boolean complete() throws IOException {
        int bsGetInt = bsGetInt();
        this.storedCombinedCRC = bsGetInt;
        this.currentState = 0;
        this.data = null;
        if (bsGetInt == this.computedCombinedCRC) {
            return (this.decompressConcatenated && init(false)) ? false : true;
        }
        throw new IOException("BZip2 CRC error");
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream$Data, java.io.InputStream] */
    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        InputStream inputStream = this.in;
        if (inputStream != null) {
            try {
                if (inputStream != System.in) {
                    inputStream.close();
                }
            } finally {
                this.data = null;
                this.in = null;
            }
        }
    }

    private int bsR(int i) throws IOException {
        int i2 = this.bsLive;
        int i3 = this.bsBuff;
        if (i2 < i) {
            InputStream inputStream = this.in;
            do {
                int read = inputStream.read();
                if (read < 0) {
                    throw new IOException("unexpected end of stream");
                }
                i3 = (i3 << 8) | read;
                i2 += 8;
            } while (i2 < i);
            this.bsBuff = i3;
        }
        int i4 = i2 - i;
        this.bsLive = i4;
        return ((1 << i) - 1) & (i3 >> i4);
    }

    private boolean bsGetBit() throws IOException {
        return bsR(1) != 0;
    }

    private char bsGetUByte() throws IOException {
        return (char) bsR(8);
    }

    private int bsGetInt() throws IOException {
        return bsR(8) | (((((bsR(8) << 8) | bsR(8)) << 8) | bsR(8)) << 8);
    }

    private static void hbCreateDecodeTables(int[] iArr, int[] iArr2, int[] iArr3, char[] cArr, int i, int i2, int i3) {
        int i4 = 0;
        int i5 = 0;
        for (int i6 = i; i6 <= i2; i6++) {
            for (int i7 = 0; i7 < i3; i7++) {
                if (cArr[i7] == i6) {
                    iArr3[i5] = i7;
                    i5++;
                }
            }
        }
        int i8 = 23;
        while (true) {
            i8--;
            if (i8 <= 0) {
                break;
            }
            iArr2[i8] = 0;
            iArr[i8] = 0;
        }
        for (int i9 = 0; i9 < i3; i9++) {
            int i10 = cArr[i9] + 1;
            iArr2[i10] = iArr2[i10] + 1;
        }
        int i11 = iArr2[0];
        for (int i12 = 1; i12 < 23; i12++) {
            i11 += iArr2[i12];
            iArr2[i12] = i11;
        }
        int i13 = iArr2[i];
        int i14 = i;
        while (i14 <= i2) {
            int i15 = i14 + 1;
            int i16 = iArr2[i15];
            int i17 = i4 + (i16 - i13);
            iArr[i14] = i17 - 1;
            i4 = i17 << 1;
            i14 = i15;
            i13 = i16;
        }
        for (int i18 = i + 1; i18 <= i2; i18++) {
            iArr2[i18] = ((iArr[i18 - 1] + 1) << 1) - iArr2[i18];
        }
    }

    private void recvDecodingTables() throws IOException {
        Data data = this.data;
        boolean[] zArr = data.inUse;
        byte[] bArr = data.recvDecodingTables_pos;
        byte[] bArr2 = data.selector;
        byte[] bArr3 = data.selectorMtf;
        int i = 0;
        for (int i2 = 0; i2 < 16; i2++) {
            if (bsGetBit()) {
                i |= 1 << i2;
            }
        }
        int i3 = 256;
        while (true) {
            i3--;
            if (i3 < 0) {
                break;
            }
            zArr[i3] = false;
        }
        for (int i4 = 0; i4 < 16; i4++) {
            if (((1 << i4) & i) != 0) {
                int i5 = i4 << 4;
                for (int i6 = 0; i6 < 16; i6++) {
                    if (bsGetBit()) {
                        zArr[i5 + i6] = true;
                    }
                }
            }
        }
        makeMaps();
        int i7 = this.nInUse + 2;
        int bsR = bsR(3);
        int bsR2 = bsR(15);
        for (int i8 = 0; i8 < bsR2; i8++) {
            int i9 = 0;
            while (bsGetBit()) {
                i9++;
            }
            bArr3[i8] = (byte) i9;
        }
        int i10 = bsR;
        while (true) {
            i10--;
            if (i10 < 0) {
                break;
            }
            bArr[i10] = (byte) i10;
        }
        for (int i11 = 0; i11 < bsR2; i11++) {
            int i12 = bArr3[i11] & 255;
            byte b = bArr[i12];
            while (i12 > 0) {
                bArr[i12] = bArr[i12 - 1];
                i12--;
            }
            bArr[0] = b;
            bArr2[i11] = b;
        }
        char[][] cArr = data.temp_charArray2d;
        for (int i13 = 0; i13 < bsR; i13++) {
            int bsR3 = bsR(5);
            char[] cArr2 = cArr[i13];
            for (int i14 = 0; i14 < i7; i14++) {
                while (bsGetBit()) {
                    bsR3 += bsGetBit() ? -1 : 1;
                }
                cArr2[i14] = (char) bsR3;
            }
        }
        createHuffmanDecodingTables(i7, bsR);
    }

    private void createHuffmanDecodingTables(int i, int i2) {
        Data data = this.data;
        char[][] cArr = data.temp_charArray2d;
        int[] iArr = data.minLens;
        int[][] iArr2 = data.limit;
        int[][] iArr3 = data.base;
        int[][] iArr4 = data.perm;
        for (int i3 = 0; i3 < i2; i3++) {
            char c = ' ';
            char[] cArr2 = cArr[i3];
            int i4 = i;
            char c2 = 0;
            while (true) {
                i4--;
                if (i4 >= 0) {
                    char c3 = cArr2[i4];
                    if (c3 > c2) {
                        c2 = c3;
                    }
                    if (c3 < c) {
                        c = c3;
                    }
                }
            }
            hbCreateDecodeTables(iArr2[i3], iArr3[i3], iArr4[i3], cArr[i3], c, c2, i);
            iArr[i3] = c;
        }
    }

    private void getAndMoveToFrontDecode() throws IOException {
        int i;
        int i2;
        char c;
        int i3;
        BZip2CompressorInputStream bZip2CompressorInputStream = this;
        bZip2CompressorInputStream.origPtr = bZip2CompressorInputStream.bsR(24);
        recvDecodingTables();
        InputStream inputStream = bZip2CompressorInputStream.in;
        Data data = bZip2CompressorInputStream.data;
        byte[] bArr = data.ll8;
        int[] iArr = data.unzftab;
        byte[] bArr2 = data.selector;
        byte[] bArr3 = data.seqToUnseq;
        char[] cArr = data.getAndMoveToFrontDecode_yy;
        int[] iArr2 = data.minLens;
        int[][] iArr3 = data.limit;
        int[][] iArr4 = data.base;
        int[][] iArr5 = data.perm;
        int i4 = bZip2CompressorInputStream.blockSize100k * 100000;
        int i5 = 256;
        while (true) {
            i5--;
            if (i5 < 0) {
                break;
            }
            cArr[i5] = (char) i5;
            iArr[i5] = 0;
        }
        int i6 = bZip2CompressorInputStream.nInUse + 1;
        int andMoveToFrontDecode0 = bZip2CompressorInputStream.getAndMoveToFrontDecode0(0);
        int i7 = bZip2CompressorInputStream.bsBuff;
        int i8 = bZip2CompressorInputStream.bsLive;
        int i9 = bArr2[0] & 255;
        int[] iArr6 = iArr4[i9];
        int[] iArr7 = iArr3[i9];
        int[] iArr8 = iArr5[i9];
        int i10 = 0;
        int i11 = i8;
        int i12 = andMoveToFrontDecode0;
        int i13 = 49;
        int i14 = -1;
        int i15 = iArr2[i9];
        int i16 = i7;
        while (i12 != i6) {
            int i17 = i6;
            int i18 = i16;
            if (i12 == 0 || i12 == 1) {
                int i19 = 1;
                int i20 = -1;
                while (true) {
                    if (i12 == 0) {
                        i20 += i19;
                        i = i14;
                    } else {
                        i = i14;
                        if (i12 == 1) {
                            i20 += i19 << 1;
                        } else {
                            int[][] iArr9 = iArr5;
                            byte[] bArr4 = bArr2;
                            byte b = bArr3[cArr[0]];
                            int i21 = b & 255;
                            iArr[i21] = iArr[i21] + i20 + 1;
                            i14 = i;
                            while (true) {
                                int i22 = i20 - 1;
                                if (i20 < 0) {
                                    break;
                                }
                                i14++;
                                bArr[i14] = b;
                                i20 = i22;
                            }
                            if (i14 >= i4) {
                                throw new IOException("block overrun");
                            }
                            bZip2CompressorInputStream = this;
                            i6 = i17;
                            i16 = i18;
                            iArr5 = iArr9;
                            bArr2 = bArr4;
                        }
                    }
                    if (i13 == 0) {
                        i10++;
                        int i23 = bArr2[i10] & 255;
                        iArr6 = iArr4[i23];
                        iArr7 = iArr3[i23];
                        iArr8 = iArr5[i23];
                        i2 = iArr2[i23];
                        i13 = 49;
                    } else {
                        i13--;
                        i2 = i15;
                    }
                    int i24 = i11;
                    while (i24 < i2) {
                        int read = inputStream.read();
                        if (read < 0) {
                            throw new IOException("unexpected end of stream");
                        }
                        i18 = (i18 << 8) | read;
                        i24 += 8;
                    }
                    int i25 = i24 - i2;
                    int[][] iArr10 = iArr5;
                    i11 = i25;
                    int i26 = (i18 >> i25) & ((1 << i2) - 1);
                    int i27 = i2;
                    while (i26 > iArr7[i27]) {
                        int i28 = i27 + 1;
                        byte[] bArr5 = bArr2;
                        int i29 = i11;
                        while (i29 < 1) {
                            int read2 = inputStream.read();
                            if (read2 < 0) {
                                throw new IOException("unexpected end of stream");
                            }
                            i18 = (i18 << 8) | read2;
                            i29 += 8;
                        }
                        i11 = i29 - 1;
                        i26 = (i26 << 1) | ((i18 >> i11) & 1);
                        i27 = i28;
                        bArr2 = bArr5;
                    }
                    int i30 = iArr8[i26 - iArr6[i27]];
                    i19 <<= 1;
                    i15 = i2;
                    i14 = i;
                    i12 = i30;
                    iArr5 = iArr10;
                }
            } else {
                i14++;
                if (i14 >= i4) {
                    throw new IOException("block overrun");
                }
                int i31 = i12 - 1;
                char c2 = cArr[i31];
                int i32 = bArr3[c2] & 255;
                iArr[i32] = iArr[i32] + 1;
                bArr[i14] = bArr3[c2];
                if (i12 <= 16) {
                    while (i31 > 0) {
                        int i33 = i31 - 1;
                        cArr[i31] = cArr[i33];
                        i31 = i33;
                    }
                    c = 0;
                } else {
                    c = 0;
                    System.arraycopy(cArr, 0, cArr, 1, i31);
                }
                cArr[c] = c2;
                if (i13 == 0) {
                    i10++;
                    int i34 = bArr2[i10] & 255;
                    int[] iArr11 = iArr4[i34];
                    int[] iArr12 = iArr3[i34];
                    int[] iArr13 = iArr5[i34];
                    i3 = iArr2[i34];
                    iArr6 = iArr11;
                    iArr7 = iArr12;
                    iArr8 = iArr13;
                    i13 = 49;
                } else {
                    i13--;
                    i3 = i15;
                }
                int i35 = i11;
                while (i35 < i3) {
                    int read3 = inputStream.read();
                    if (read3 < 0) {
                        throw new IOException("unexpected end of stream");
                    }
                    i18 = (i18 << 8) | read3;
                    i35 += 8;
                }
                int i36 = i35 - i3;
                int i37 = (i18 >> i36) & ((1 << i3) - 1);
                i11 = i36;
                int i38 = i3;
                while (i37 > iArr7[i38]) {
                    i38++;
                    int i39 = i3;
                    int i40 = i11;
                    while (i40 < 1) {
                        int read4 = inputStream.read();
                        if (read4 < 0) {
                            throw new IOException("unexpected end of stream");
                        }
                        i18 = (i18 << 8) | read4;
                        i40 += 8;
                    }
                    i11 = i40 - 1;
                    i37 = (i37 << 1) | ((i18 >> i11) & 1);
                    i3 = i39;
                }
                int i41 = i3;
                i12 = iArr8[i37 - iArr6[i38]];
                bZip2CompressorInputStream = this;
                i6 = i17;
                i16 = i18;
                i15 = i41;
            }
        }
        bZip2CompressorInputStream.last = i14;
        bZip2CompressorInputStream.bsLive = i11;
        bZip2CompressorInputStream.bsBuff = i16;
    }

    private int getAndMoveToFrontDecode0(int i) throws IOException {
        InputStream inputStream = this.in;
        Data data = this.data;
        int i2 = data.selector[i] & 255;
        int[] iArr = data.limit[i2];
        int i3 = data.minLens[i2];
        int bsR = bsR(i3);
        int i4 = this.bsLive;
        int i5 = this.bsBuff;
        while (bsR > iArr[i3]) {
            i3++;
            while (i4 < 1) {
                int read = inputStream.read();
                if (read < 0) {
                    throw new IOException("unexpected end of stream");
                }
                i5 = (i5 << 8) | read;
                i4 += 8;
            }
            i4--;
            bsR = (bsR << 1) | (1 & (i5 >> i4));
        }
        this.bsLive = i4;
        this.bsBuff = i5;
        return data.perm[i2][bsR - data.base[i2][i3]];
    }

    private int setupBlock() throws IOException {
        Data data;
        if (this.currentState == 0 || (data = this.data) == null) {
            return -1;
        }
        int[] iArr = data.cftab;
        int[] initTT = this.data.initTT(this.last + 1);
        byte[] bArr = this.data.ll8;
        iArr[0] = 0;
        System.arraycopy(this.data.unzftab, 0, iArr, 1, 256);
        int i = iArr[0];
        for (int i2 = 1; i2 <= 256; i2++) {
            i += iArr[i2];
            iArr[i2] = i;
        }
        int i3 = this.last;
        for (int i4 = 0; i4 <= i3; i4++) {
            int i5 = bArr[i4] & 255;
            int i6 = iArr[i5];
            iArr[i5] = i6 + 1;
            initTT[i6] = i4;
        }
        int i7 = this.origPtr;
        if (i7 < 0 || i7 >= initTT.length) {
            throw new IOException("stream corrupted");
        }
        this.su_tPos = initTT[i7];
        this.su_count = 0;
        this.su_i2 = 0;
        this.su_ch2 = 256;
        if (this.blockRandomised) {
            this.su_rNToGo = 0;
            this.su_rTPos = 0;
            return setupRandPartA();
        }
        return setupNoRandPartA();
    }

    private int setupRandPartA() throws IOException {
        if (this.su_i2 <= this.last) {
            this.su_chPrev = this.su_ch2;
            int i = this.data.ll8[this.su_tPos] & 255;
            this.su_tPos = this.data.tt[this.su_tPos];
            int i2 = this.su_rNToGo;
            if (i2 == 0) {
                this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
                int i3 = this.su_rTPos + 1;
                this.su_rTPos = i3;
                if (i3 == 512) {
                    this.su_rTPos = 0;
                }
            } else {
                this.su_rNToGo = i2 - 1;
            }
            int i4 = i ^ (this.su_rNToGo == 1 ? 1 : 0);
            this.su_ch2 = i4;
            this.su_i2++;
            this.currentState = 3;
            this.crc.updateCRC(i4);
            return i4;
        }
        endBlock();
        initBlock();
        return setupBlock();
    }

    private int setupNoRandPartA() throws IOException {
        if (this.su_i2 <= this.last) {
            this.su_chPrev = this.su_ch2;
            int i = this.data.ll8[this.su_tPos] & 255;
            this.su_ch2 = i;
            this.su_tPos = this.data.tt[this.su_tPos];
            this.su_i2++;
            this.currentState = 6;
            this.crc.updateCRC(i);
            return i;
        }
        this.currentState = 5;
        endBlock();
        initBlock();
        return setupBlock();
    }

    private int setupRandPartB() throws IOException {
        if (this.su_ch2 != this.su_chPrev) {
            this.currentState = 2;
            this.su_count = 1;
            return setupRandPartA();
        }
        int i = this.su_count + 1;
        this.su_count = i;
        if (i >= 4) {
            this.su_z = (char) (this.data.ll8[this.su_tPos] & 255);
            this.su_tPos = this.data.tt[this.su_tPos];
            int i2 = this.su_rNToGo;
            if (i2 == 0) {
                this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
                int i3 = this.su_rTPos + 1;
                this.su_rTPos = i3;
                if (i3 == 512) {
                    this.su_rTPos = 0;
                }
            } else {
                this.su_rNToGo = i2 - 1;
            }
            this.su_j2 = 0;
            this.currentState = 4;
            if (this.su_rNToGo == 1) {
                this.su_z = (char) (this.su_z ^ 1);
            }
            return setupRandPartC();
        }
        this.currentState = 2;
        return setupRandPartA();
    }

    private int setupRandPartC() throws IOException {
        if (this.su_j2 < this.su_z) {
            this.crc.updateCRC(this.su_ch2);
            this.su_j2++;
            return this.su_ch2;
        }
        this.currentState = 2;
        this.su_i2++;
        this.su_count = 0;
        return setupRandPartA();
    }

    private int setupNoRandPartB() throws IOException {
        if (this.su_ch2 != this.su_chPrev) {
            this.su_count = 1;
            return setupNoRandPartA();
        }
        int i = this.su_count + 1;
        this.su_count = i;
        if (i >= 4) {
            this.su_z = (char) (this.data.ll8[this.su_tPos] & 255);
            this.su_tPos = this.data.tt[this.su_tPos];
            this.su_j2 = 0;
            return setupNoRandPartC();
        }
        return setupNoRandPartA();
    }

    private int setupNoRandPartC() throws IOException {
        if (this.su_j2 < this.su_z) {
            int i = this.su_ch2;
            this.crc.updateCRC(i);
            this.su_j2++;
            this.currentState = 7;
            return i;
        }
        this.su_i2++;
        this.su_count = 0;
        return setupNoRandPartA();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class Data {
        byte[] ll8;
        int[] tt;
        final boolean[] inUse = new boolean[256];
        final byte[] seqToUnseq = new byte[256];
        final byte[] selector = new byte[BZip2Constants.MAX_SELECTORS];
        final byte[] selectorMtf = new byte[BZip2Constants.MAX_SELECTORS];
        final int[] unzftab = new int[256];
        final int[][] limit = (int[][]) Array.newInstance(int.class, 6, BZip2Constants.MAX_ALPHA_SIZE);
        final int[][] base = (int[][]) Array.newInstance(int.class, 6, BZip2Constants.MAX_ALPHA_SIZE);
        final int[][] perm = (int[][]) Array.newInstance(int.class, 6, BZip2Constants.MAX_ALPHA_SIZE);
        final int[] minLens = new int[6];
        final int[] cftab = new int[257];
        final char[] getAndMoveToFrontDecode_yy = new char[256];
        final char[][] temp_charArray2d = (char[][]) Array.newInstance(char.class, 6, BZip2Constants.MAX_ALPHA_SIZE);
        final byte[] recvDecodingTables_pos = new byte[6];

        Data(int i) {
            this.ll8 = new byte[i * 100000];
        }

        int[] initTT(int i) {
            int[] iArr = this.tt;
            if (iArr == null || iArr.length < i) {
                int[] iArr2 = new int[i];
                this.tt = iArr2;
                return iArr2;
            }
            return iArr;
        }
    }

    public static boolean matches(byte[] bArr, int i) {
        return i >= 3 && bArr[0] == 66 && bArr[1] == 90 && bArr[2] == 104;
    }
}
