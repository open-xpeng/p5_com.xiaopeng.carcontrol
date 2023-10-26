package org.apache.commons.compress.compressors.bzip2;

import androidx.core.view.InputDeviceCompat;
import com.xiaopeng.lib.framework.moduleinterface.carcontroller.IInputController;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import org.apache.commons.compress.compressors.CompressorOutputStream;

/* loaded from: classes3.dex */
public class BZip2CompressorOutputStream extends CompressorOutputStream implements BZip2Constants {
    private static final int GREATER_ICOST = 15;
    private static final int LESSER_ICOST = 0;
    public static final int MAX_BLOCKSIZE = 9;
    public static final int MIN_BLOCKSIZE = 1;
    private final int allowableBlockSize;
    private int blockCRC;
    private final int blockSize100k;
    private BlockSort blockSorter;
    private int bsBuff;
    private int bsLive;
    private volatile boolean closed;
    private int combinedCRC;
    private final CRC crc;
    private int currentChar;
    private Data data;
    private int last;
    private int nInUse;
    private int nMTF;
    private OutputStream out;
    private int runLength;

    private static void hbMakeCodeLengths(byte[] bArr, int[] iArr, Data data, int i, int i2) {
        int[] iArr2 = data.heap;
        int[] iArr3 = data.weight;
        int[] iArr4 = data.parent;
        int i3 = i;
        while (true) {
            i3--;
            int i4 = 1;
            if (i3 < 0) {
                break;
            }
            int i5 = i3 + 1;
            if (iArr[i3] != 0) {
                i4 = iArr[i3];
            }
            iArr3[i5] = i4 << 8;
        }
        boolean z = true;
        while (z) {
            iArr2[0] = 0;
            iArr3[0] = 0;
            iArr4[0] = -2;
            int i6 = 0;
            for (int i7 = 1; i7 <= i; i7++) {
                iArr4[i7] = -1;
                i6++;
                iArr2[i6] = i7;
                int i8 = iArr2[i6];
                int i9 = i6;
                while (true) {
                    int i10 = i9 >> 1;
                    if (iArr3[i8] < iArr3[iArr2[i10]]) {
                        iArr2[i9] = iArr2[i10];
                        i9 = i10;
                    }
                }
                iArr2[i9] = i8;
            }
            int i11 = i;
            while (i6 > 1) {
                int i12 = iArr2[1];
                iArr2[1] = iArr2[i6];
                int i13 = i6 - 1;
                int i14 = iArr2[1];
                int i15 = 1;
                while (true) {
                    int i16 = i15 << 1;
                    if (i16 > i13) {
                        break;
                    }
                    if (i16 < i13) {
                        int i17 = i16 + 1;
                        if (iArr3[iArr2[i17]] < iArr3[iArr2[i16]]) {
                            i16 = i17;
                        }
                    }
                    if (iArr3[i14] < iArr3[iArr2[i16]]) {
                        break;
                    }
                    iArr2[i15] = iArr2[i16];
                    i15 = i16;
                }
                iArr2[i15] = i14;
                int i18 = iArr2[1];
                iArr2[1] = iArr2[i13];
                int i19 = i13 - 1;
                int i20 = iArr2[1];
                int i21 = 1;
                while (true) {
                    int i22 = i21 << 1;
                    if (i22 > i19) {
                        break;
                    }
                    if (i22 < i19) {
                        int i23 = i22 + 1;
                        if (iArr3[iArr2[i23]] < iArr3[iArr2[i22]]) {
                            i22 = i23;
                        }
                    }
                    if (iArr3[i20] < iArr3[iArr2[i22]]) {
                        break;
                    }
                    iArr2[i21] = iArr2[i22];
                    i21 = i22;
                }
                iArr2[i21] = i20;
                i11++;
                iArr4[i18] = i11;
                iArr4[i12] = i11;
                int i24 = iArr3[i12];
                int i25 = iArr3[i18];
                int i26 = (i24 & InputDeviceCompat.SOURCE_ANY) + (i25 & InputDeviceCompat.SOURCE_ANY);
                int i27 = i24 & 255;
                int i28 = i25 & 255;
                if (i27 <= i28) {
                    i27 = i28;
                }
                iArr3[i11] = (i27 + 1) | i26;
                iArr4[i11] = -1;
                i6 = i19 + 1;
                iArr2[i6] = i11;
                int i29 = iArr2[i6];
                int i30 = iArr3[i29];
                int i31 = i6;
                while (true) {
                    int i32 = i31 >> 1;
                    if (i30 < iArr3[iArr2[i32]]) {
                        iArr2[i31] = iArr2[i32];
                        i31 = i32;
                    }
                }
                iArr2[i31] = i29;
            }
            z = false;
            for (int i33 = 1; i33 <= i; i33++) {
                int i34 = i33;
                int i35 = 0;
                while (true) {
                    i34 = iArr4[i34];
                    if (i34 < 0) {
                        break;
                    }
                    i35++;
                }
                bArr[i33 - 1] = (byte) i35;
                if (i35 > i2) {
                    z = true;
                }
            }
            if (z) {
                for (int i36 = 1; i36 < i; i36++) {
                    iArr3[i36] = (((iArr3[i36] >> 8) >> 1) + 1) << 8;
                }
            }
        }
    }

    public static int chooseBlockSize(long j) {
        if (j > 0) {
            return (int) Math.min((j / 132000) + 1, 9L);
        }
        return 9;
    }

    public BZip2CompressorOutputStream(OutputStream outputStream) throws IOException {
        this(outputStream, 9);
    }

    public BZip2CompressorOutputStream(OutputStream outputStream, int i) throws IOException {
        this.crc = new CRC();
        this.currentChar = -1;
        this.runLength = 0;
        if (i < 1) {
            throw new IllegalArgumentException("blockSize(" + i + ") < 1");
        }
        if (i > 9) {
            throw new IllegalArgumentException("blockSize(" + i + ") > 9");
        }
        this.blockSize100k = i;
        this.out = outputStream;
        this.allowableBlockSize = (i * 100000) - 20;
        init();
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        if (!this.closed) {
            write0(i);
            return;
        }
        throw new IOException("closed");
    }

    private void writeRun() throws IOException {
        int i = this.last;
        if (i < this.allowableBlockSize) {
            int i2 = this.currentChar;
            Data data = this.data;
            data.inUse[i2] = true;
            byte b = (byte) i2;
            int i3 = this.runLength;
            this.crc.updateCRC(i2, i3);
            if (i3 == 1) {
                data.block[i + 2] = b;
                this.last = i + 1;
                return;
            } else if (i3 == 2) {
                int i4 = i + 2;
                data.block[i4] = b;
                data.block[i + 3] = b;
                this.last = i4;
                return;
            } else if (i3 == 3) {
                byte[] bArr = data.block;
                bArr[i + 2] = b;
                int i5 = i + 3;
                bArr[i5] = b;
                bArr[i + 4] = b;
                this.last = i5;
                return;
            } else {
                int i6 = i3 - 4;
                data.inUse[i6] = true;
                byte[] bArr2 = data.block;
                bArr2[i + 2] = b;
                bArr2[i + 3] = b;
                bArr2[i + 4] = b;
                int i7 = i + 5;
                bArr2[i7] = b;
                bArr2[i + 6] = (byte) i6;
                this.last = i7;
                return;
            }
        }
        endBlock();
        initBlock();
        writeRun();
    }

    protected void finalize() throws Throwable {
        if (!this.closed) {
            System.err.println("Unclosed BZip2CompressorOutputStream detected, will *not* close it");
        }
        super.finalize();
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.OutputStream, org.apache.commons.compress.compressors.bzip2.BlockSort, org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream$Data] */
    public void finish() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        try {
            if (this.runLength > 0) {
                writeRun();
            }
            this.currentChar = -1;
            endBlock();
            endCompression();
        } finally {
            this.out = null;
            this.blockSorter = null;
            this.data = null;
        }
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        OutputStream outputStream = this.out;
        finish();
        outputStream.close();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        OutputStream outputStream = this.out;
        if (outputStream != null) {
            outputStream.flush();
        }
    }

    private void init() throws IOException {
        bsPutUByte(66);
        bsPutUByte(90);
        this.data = new Data(this.blockSize100k);
        this.blockSorter = new BlockSort(this.data);
        bsPutUByte(104);
        bsPutUByte(this.blockSize100k + 48);
        this.combinedCRC = 0;
        initBlock();
    }

    private void initBlock() {
        this.crc.initialiseCRC();
        this.last = -1;
        boolean[] zArr = this.data.inUse;
        int i = 256;
        while (true) {
            i--;
            if (i < 0) {
                return;
            }
            zArr[i] = false;
        }
    }

    private void endBlock() throws IOException {
        int finalCRC = this.crc.getFinalCRC();
        this.blockCRC = finalCRC;
        int i = this.combinedCRC;
        int i2 = (i >>> 31) | (i << 1);
        this.combinedCRC = i2;
        this.combinedCRC = finalCRC ^ i2;
        if (this.last == -1) {
            return;
        }
        blockSort();
        bsPutUByte(49);
        bsPutUByte(65);
        bsPutUByte(89);
        bsPutUByte(38);
        bsPutUByte(83);
        bsPutUByte(89);
        bsPutInt(this.blockCRC);
        bsW(1, 0);
        moveToFrontCodeAndSend();
    }

    private void endCompression() throws IOException {
        bsPutUByte(23);
        bsPutUByte(114);
        bsPutUByte(69);
        bsPutUByte(56);
        bsPutUByte(80);
        bsPutUByte(144);
        bsPutInt(this.combinedCRC);
        bsFinishedWithStream();
    }

    public final int getBlockSize() {
        return this.blockSize100k;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (i < 0) {
            throw new IndexOutOfBoundsException("offs(" + i + ") < 0.");
        }
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("len(" + i2 + ") < 0.");
        }
        int i3 = i + i2;
        if (i3 > bArr.length) {
            throw new IndexOutOfBoundsException("offs(" + i + ") + len(" + i2 + ") > buf.length(" + bArr.length + ").");
        }
        if (this.closed) {
            throw new IOException("stream closed");
        }
        while (i < i3) {
            write0(bArr[i]);
            i++;
        }
    }

    private void write0(int i) throws IOException {
        int i2 = this.currentChar;
        if (i2 == -1) {
            this.currentChar = i & 255;
            this.runLength++;
            return;
        }
        int i3 = i & 255;
        if (i2 == i3) {
            int i4 = this.runLength + 1;
            this.runLength = i4;
            if (i4 > 254) {
                writeRun();
                this.currentChar = -1;
                this.runLength = 0;
                return;
            }
            return;
        }
        writeRun();
        this.runLength = 1;
        this.currentChar = i3;
    }

    private static void hbAssignCodes(int[] iArr, byte[] bArr, int i, int i2, int i3) {
        int i4 = 0;
        while (i <= i2) {
            for (int i5 = 0; i5 < i3; i5++) {
                if ((bArr[i5] & 255) == i) {
                    iArr[i5] = i4;
                    i4++;
                }
            }
            i4 <<= 1;
            i++;
        }
    }

    private void bsFinishedWithStream() throws IOException {
        while (this.bsLive > 0) {
            this.out.write(this.bsBuff >> 24);
            this.bsBuff <<= 8;
            this.bsLive -= 8;
        }
    }

    private void bsW(int i, int i2) throws IOException {
        OutputStream outputStream = this.out;
        int i3 = this.bsLive;
        int i4 = this.bsBuff;
        while (i3 >= 8) {
            outputStream.write(i4 >> 24);
            i4 <<= 8;
            i3 -= 8;
        }
        this.bsBuff = (i2 << ((32 - i3) - i)) | i4;
        this.bsLive = i3 + i;
    }

    private void bsPutUByte(int i) throws IOException {
        bsW(8, i);
    }

    private void bsPutInt(int i) throws IOException {
        bsW(8, (i >> 24) & 255);
        bsW(8, (i >> 16) & 255);
        bsW(8, (i >> 8) & 255);
        bsW(8, i & 255);
    }

    private void sendMTFValues() throws IOException {
        byte[][] bArr = this.data.sendMTFValues_len;
        int i = this.nInUse + 2;
        int i2 = 6;
        while (true) {
            i2--;
            if (i2 < 0) {
                break;
            }
            byte[] bArr2 = bArr[i2];
            int i3 = i;
            while (true) {
                i3--;
                if (i3 >= 0) {
                    bArr2[i3] = 15;
                }
            }
        }
        int i4 = this.nMTF;
        int i5 = i4 >= 200 ? i4 < 600 ? 3 : i4 < 1200 ? 4 : i4 < 2400 ? 5 : 6 : 2;
        sendMTFValues0(i5, i);
        int sendMTFValues1 = sendMTFValues1(i5, i);
        sendMTFValues2(i5, sendMTFValues1);
        sendMTFValues3(i5, i);
        sendMTFValues4();
        sendMTFValues5(i5, sendMTFValues1);
        sendMTFValues6(i5, i);
        sendMTFValues7();
    }

    private void sendMTFValues0(int i, int i2) {
        byte[][] bArr = this.data.sendMTFValues_len;
        int[] iArr = this.data.mtfFreq;
        int i3 = this.nMTF;
        int i4 = 0;
        for (int i5 = i; i5 > 0; i5--) {
            int i6 = i3 / i5;
            int i7 = i4 - 1;
            int i8 = i2 - 1;
            int i9 = 0;
            while (i9 < i6 && i7 < i8) {
                i7++;
                i9 += iArr[i7];
            }
            if (i7 > i4 && i5 != i && i5 != 1 && (1 & (i - i5)) != 0) {
                i9 -= iArr[i7];
                i7--;
            }
            byte[] bArr2 = bArr[i5 - 1];
            int i10 = i2;
            while (true) {
                i10--;
                if (i10 >= 0) {
                    if (i10 >= i4 && i10 <= i7) {
                        bArr2[i10] = 0;
                    } else {
                        bArr2[i10] = 15;
                    }
                }
            }
            i4 = i7 + 1;
            i3 -= i9;
        }
    }

    private int sendMTFValues1(int i, int i2) {
        byte[] bArr;
        int i3;
        BZip2CompressorOutputStream bZip2CompressorOutputStream = this;
        Data data = bZip2CompressorOutputStream.data;
        int[][] iArr = data.sendMTFValues_rfreq;
        int[] iArr2 = data.sendMTFValues_fave;
        short[] sArr = data.sendMTFValues_cost;
        char[] cArr = data.sfmap;
        byte[] bArr2 = data.selector;
        byte[][] bArr3 = data.sendMTFValues_len;
        int i4 = 0;
        byte[] bArr4 = bArr3[0];
        byte[] bArr5 = bArr3[1];
        byte[] bArr6 = bArr3[2];
        byte[] bArr7 = bArr3[3];
        int i5 = 4;
        byte[] bArr8 = bArr3[4];
        byte[] bArr9 = bArr3[5];
        int i6 = bZip2CompressorOutputStream.nMTF;
        int i7 = 0;
        int i8 = 0;
        while (i7 < i5) {
            int i9 = i;
            while (true) {
                i9--;
                if (i9 < 0) {
                    break;
                }
                iArr2[i9] = i4;
                int[] iArr3 = iArr[i9];
                int i10 = i2;
                while (true) {
                    i10--;
                    if (i10 >= 0) {
                        iArr3[i10] = i4;
                    }
                }
            }
            int i11 = i4;
            i8 = i11;
            while (i11 < bZip2CompressorOutputStream.nMTF) {
                int i12 = i11;
                int min = Math.min((i11 + 50) - 1, i6 - 1);
                if (i == 6) {
                    int i13 = i12;
                    short s = 0;
                    short s2 = 0;
                    short s3 = 0;
                    short s4 = 0;
                    short s5 = 0;
                    short s6 = 0;
                    while (i13 <= min) {
                        char c = cArr[i13];
                        byte[] bArr10 = bArr4;
                        short s7 = (short) (s2 + (bArr5[c] & 255));
                        short s8 = (short) (s3 + (bArr6[c] & 255));
                        short s9 = (short) (s4 + (bArr7[c] & 255));
                        short s10 = (short) (s5 + (bArr8[c] & 255));
                        i13++;
                        s6 = (short) (s6 + (bArr9[c] & 255));
                        s5 = s10;
                        bArr4 = bArr10;
                        s4 = s9;
                        s3 = s8;
                        s2 = s7;
                        s = (short) (s + (bArr4[c] & 255));
                        i6 = i6;
                    }
                    bArr = bArr4;
                    i3 = i6;
                    sArr[0] = s;
                    sArr[1] = s2;
                    sArr[2] = s3;
                    sArr[3] = s4;
                    sArr[4] = s5;
                    sArr[5] = s6;
                } else {
                    bArr = bArr4;
                    i3 = i6;
                    int i14 = i;
                    while (true) {
                        i14--;
                        if (i14 < 0) {
                            break;
                        }
                        sArr[i14] = 0;
                    }
                    for (int i15 = i12; i15 <= min; i15++) {
                        char c2 = cArr[i15];
                        int i16 = i;
                        while (true) {
                            i16--;
                            if (i16 >= 0) {
                                sArr[i16] = (short) (sArr[i16] + (bArr3[i16][c2] & 255));
                            }
                        }
                    }
                }
                short s11 = 999999999;
                int i17 = i;
                int i18 = -1;
                while (true) {
                    i17--;
                    if (i17 < 0) {
                        break;
                    }
                    byte[] bArr11 = bArr5;
                    short s12 = sArr[i17];
                    if (s12 < s11) {
                        s11 = s12;
                        i18 = i17;
                    }
                    bArr5 = bArr11;
                }
                byte[] bArr12 = bArr5;
                iArr2[i18] = iArr2[i18] + 1;
                bArr2[i8] = (byte) i18;
                i8++;
                int[] iArr4 = iArr[i18];
                for (int i19 = i12; i19 <= min; i19++) {
                    char c3 = cArr[i19];
                    iArr4[c3] = iArr4[c3] + 1;
                }
                i11 = min + 1;
                bArr5 = bArr12;
                i6 = i3;
                bArr4 = bArr;
            }
            byte[] bArr13 = bArr4;
            byte[] bArr14 = bArr5;
            int i20 = i6;
            int i21 = 0;
            while (i21 < i) {
                hbMakeCodeLengths(bArr3[i21], iArr[i21], bZip2CompressorOutputStream.data, i2, 20);
                i21++;
                bZip2CompressorOutputStream = this;
            }
            i7++;
            i4 = 0;
            bZip2CompressorOutputStream = this;
            i5 = 4;
            bArr5 = bArr14;
            i6 = i20;
            bArr4 = bArr13;
        }
        return i8;
    }

    private void sendMTFValues2(int i, int i2) {
        Data data = this.data;
        byte[] bArr = data.sendMTFValues2_pos;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            bArr[i] = (byte) i;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            byte b = data.selector[i3];
            byte b2 = bArr[0];
            int i4 = 0;
            while (b != b2) {
                i4++;
                byte b3 = bArr[i4];
                bArr[i4] = b2;
                b2 = b3;
            }
            bArr[0] = b2;
            data.selectorMtf[i3] = (byte) i4;
        }
    }

    private void sendMTFValues3(int i, int i2) {
        int[][] iArr = this.data.sendMTFValues_code;
        byte[][] bArr = this.data.sendMTFValues_len;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = 32;
            byte[] bArr2 = bArr[i3];
            int i5 = i2;
            int i6 = 0;
            while (true) {
                i5--;
                if (i5 >= 0) {
                    int i7 = bArr2[i5] & 255;
                    if (i7 > i6) {
                        i6 = i7;
                    }
                    if (i7 < i4) {
                        i4 = i7;
                    }
                }
            }
            hbAssignCodes(iArr[i3], bArr[i3], i4, i6, i2);
        }
    }

    private void sendMTFValues4() throws IOException {
        boolean[] zArr = this.data.inUse;
        boolean[] zArr2 = this.data.sentMTFValues4_inUse16;
        int i = 16;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            zArr2[i] = false;
            int i2 = i * 16;
            int i3 = 16;
            while (true) {
                i3--;
                if (i3 >= 0) {
                    if (zArr[i2 + i3]) {
                        zArr2[i] = true;
                    }
                }
            }
        }
        for (int i4 = 0; i4 < 16; i4++) {
            bsW(1, zArr2[i4] ? 1 : 0);
        }
        OutputStream outputStream = this.out;
        int i5 = this.bsLive;
        int i6 = this.bsBuff;
        for (int i7 = 0; i7 < 16; i7++) {
            if (zArr2[i7]) {
                int i8 = i7 * 16;
                for (int i9 = 0; i9 < 16; i9++) {
                    while (i5 >= 8) {
                        outputStream.write(i6 >> 24);
                        i6 <<= 8;
                        i5 -= 8;
                    }
                    if (zArr[i8 + i9]) {
                        i6 |= 1 << ((32 - i5) - 1);
                    }
                    i5++;
                }
            }
        }
        this.bsBuff = i6;
        this.bsLive = i5;
    }

    private void sendMTFValues5(int i, int i2) throws IOException {
        bsW(3, i);
        bsW(15, i2);
        OutputStream outputStream = this.out;
        byte[] bArr = this.data.selectorMtf;
        int i3 = this.bsLive;
        int i4 = this.bsBuff;
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = bArr[i5] & 255;
            for (int i7 = 0; i7 < i6; i7++) {
                while (i3 >= 8) {
                    outputStream.write(i4 >> 24);
                    i4 <<= 8;
                    i3 -= 8;
                }
                i4 |= 1 << ((32 - i3) - 1);
                i3++;
            }
            while (i3 >= 8) {
                outputStream.write(i4 >> 24);
                i4 <<= 8;
                i3 -= 8;
            }
            i3++;
        }
        this.bsBuff = i4;
        this.bsLive = i3;
    }

    private void sendMTFValues6(int i, int i2) throws IOException {
        byte[][] bArr = this.data.sendMTFValues_len;
        OutputStream outputStream = this.out;
        int i3 = this.bsLive;
        int i4 = this.bsBuff;
        for (int i5 = 0; i5 < i; i5++) {
            byte[] bArr2 = bArr[i5];
            int i6 = bArr2[0] & 255;
            while (i3 >= 8) {
                outputStream.write(i4 >> 24);
                i4 <<= 8;
                i3 -= 8;
            }
            i4 |= i6 << ((32 - i3) - 5);
            i3 += 5;
            for (int i7 = 0; i7 < i2; i7++) {
                int i8 = bArr2[i7] & 255;
                while (i6 < i8) {
                    while (i3 >= 8) {
                        outputStream.write(i4 >> 24);
                        i4 <<= 8;
                        i3 -= 8;
                    }
                    i4 |= 2 << ((32 - i3) - 2);
                    i3 += 2;
                    i6++;
                }
                while (i6 > i8) {
                    while (i3 >= 8) {
                        outputStream.write(i4 >> 24);
                        i4 <<= 8;
                        i3 -= 8;
                    }
                    i4 |= 3 << ((32 - i3) - 2);
                    i3 += 2;
                    i6--;
                }
                while (i3 >= 8) {
                    outputStream.write(i4 >> 24);
                    i4 <<= 8;
                    i3 -= 8;
                }
                i3++;
            }
        }
        this.bsBuff = i4;
        this.bsLive = i3;
    }

    private void sendMTFValues7() throws IOException {
        Data data = this.data;
        byte[][] bArr = data.sendMTFValues_len;
        int[][] iArr = data.sendMTFValues_code;
        OutputStream outputStream = this.out;
        byte[] bArr2 = data.selector;
        char[] cArr = data.sfmap;
        int i = this.nMTF;
        int i2 = this.bsLive;
        int i3 = this.bsBuff;
        int i4 = 0;
        int i5 = 0;
        while (i4 < i) {
            int min = Math.min((i4 + 50) - 1, i - 1);
            int i6 = bArr2[i5] & 255;
            int[] iArr2 = iArr[i6];
            byte[] bArr3 = bArr[i6];
            while (i4 <= min) {
                char c = cArr[i4];
                while (i2 >= 8) {
                    outputStream.write(i3 >> 24);
                    i3 <<= 8;
                    i2 -= 8;
                }
                int i7 = bArr3[c] & 255;
                i3 |= iArr2[c] << ((32 - i2) - i7);
                i2 += i7;
                i4++;
            }
            i4 = min + 1;
            i5++;
        }
        this.bsBuff = i3;
        this.bsLive = i2;
    }

    private void moveToFrontCodeAndSend() throws IOException {
        bsW(24, this.data.origPtr);
        generateMTFValues();
        sendMTFValues();
    }

    private void blockSort() {
        this.blockSorter.blockSort(this.data, this.last);
    }

    private void generateMTFValues() {
        int i = this.last;
        Data data = this.data;
        boolean[] zArr = data.inUse;
        byte[] bArr = data.block;
        int[] iArr = data.fmap;
        char[] cArr = data.sfmap;
        int[] iArr2 = data.mtfFreq;
        byte[] bArr2 = data.unseqToSeq;
        byte[] bArr3 = data.generateMTFValues_yy;
        int i2 = 0;
        for (int i3 = 0; i3 < 256; i3++) {
            if (zArr[i3]) {
                bArr2[i3] = (byte) i2;
                i2++;
            }
        }
        this.nInUse = i2;
        int i4 = i2 + 1;
        for (int i5 = i4; i5 >= 0; i5--) {
            iArr2[i5] = 0;
        }
        while (true) {
            i2--;
            if (i2 < 0) {
                break;
            }
            bArr3[i2] = (byte) i2;
        }
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 <= i; i8++) {
            byte b = bArr2[bArr[iArr[i8]] & 255];
            byte b2 = bArr3[0];
            int i9 = 0;
            while (b != b2) {
                i9++;
                byte b3 = bArr3[i9];
                bArr3[i9] = b2;
                b2 = b3;
            }
            bArr3[0] = b2;
            if (i9 == 0) {
                i6++;
            } else {
                if (i6 > 0) {
                    int i10 = i6 - 1;
                    while (true) {
                        if ((i10 & 1) == 0) {
                            cArr[i7] = 0;
                            i7++;
                            iArr2[0] = iArr2[0] + 1;
                        } else {
                            cArr[i7] = 1;
                            i7++;
                            iArr2[1] = iArr2[1] + 1;
                        }
                        if (i10 < 2) {
                            break;
                        }
                        i10 = (i10 - 2) >> 1;
                    }
                    i6 = 0;
                }
                int i11 = i9 + 1;
                cArr[i7] = (char) i11;
                i7++;
                iArr2[i11] = iArr2[i11] + 1;
            }
        }
        if (i6 > 0) {
            int i12 = i6 - 1;
            while (true) {
                if ((i12 & 1) == 0) {
                    cArr[i7] = 0;
                    i7++;
                    iArr2[0] = iArr2[0] + 1;
                } else {
                    cArr[i7] = 1;
                    i7++;
                    iArr2[1] = iArr2[1] + 1;
                }
                if (i12 < 2) {
                    break;
                }
                i12 = (i12 - 2) >> 1;
            }
        }
        cArr[i7] = (char) i4;
        iArr2[i4] = iArr2[i4] + 1;
        this.nMTF = i7 + 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class Data {
        final byte[] block;
        final int[] fmap;
        int origPtr;
        final char[] sfmap;
        final boolean[] inUse = new boolean[256];
        final byte[] unseqToSeq = new byte[256];
        final int[] mtfFreq = new int[BZip2Constants.MAX_ALPHA_SIZE];
        final byte[] selector = new byte[BZip2Constants.MAX_SELECTORS];
        final byte[] selectorMtf = new byte[BZip2Constants.MAX_SELECTORS];
        final byte[] generateMTFValues_yy = new byte[256];
        final byte[][] sendMTFValues_len = (byte[][]) Array.newInstance(byte.class, 6, BZip2Constants.MAX_ALPHA_SIZE);
        final int[][] sendMTFValues_rfreq = (int[][]) Array.newInstance(int.class, 6, BZip2Constants.MAX_ALPHA_SIZE);
        final int[] sendMTFValues_fave = new int[6];
        final short[] sendMTFValues_cost = new short[6];
        final int[][] sendMTFValues_code = (int[][]) Array.newInstance(int.class, 6, BZip2Constants.MAX_ALPHA_SIZE);
        final byte[] sendMTFValues2_pos = new byte[6];
        final boolean[] sentMTFValues4_inUse16 = new boolean[16];
        final int[] heap = new int[260];
        final int[] weight = new int[IInputController.KEYCODE_LEFT_OK_BUTTON];
        final int[] parent = new int[IInputController.KEYCODE_LEFT_OK_BUTTON];

        Data(int i) {
            int i2 = i * 100000;
            this.block = new byte[i2 + 1 + 20];
            this.fmap = new int[i2];
            this.sfmap = new char[i2 * 2];
        }
    }
}
