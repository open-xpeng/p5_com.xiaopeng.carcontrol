package org.apache.commons.compress.compressors.z;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import org.apache.commons.compress.compressors.lzw.LZWInputStream;

/* loaded from: classes3.dex */
public class ZCompressorInputStream extends LZWInputStream {
    private static final int BLOCK_MODE_MASK = 128;
    private static final int MAGIC_1 = 31;
    private static final int MAGIC_2 = 157;
    private static final int MAX_CODE_SIZE_MASK = 31;
    private final boolean blockMode;
    private final int maxCodeSize;
    private long totalCodesRead;

    public ZCompressorInputStream(InputStream inputStream) throws IOException {
        super(inputStream, ByteOrder.LITTLE_ENDIAN);
        this.totalCodesRead = 0L;
        int readBits = (int) this.in.readBits(8);
        int readBits2 = (int) this.in.readBits(8);
        int readBits3 = (int) this.in.readBits(8);
        if (readBits != 31 || readBits2 != MAGIC_2 || readBits3 < 0) {
            throw new IOException("Input is not in .Z format");
        }
        boolean z = (readBits3 & 128) != 0;
        this.blockMode = z;
        int i = readBits3 & 31;
        this.maxCodeSize = i;
        if (z) {
            setClearCode(9);
        }
        initializeTables(i);
        clearEntries();
    }

    private void clearEntries() {
        setTableSize((this.blockMode ? 1 : 0) + 256);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.apache.commons.compress.compressors.lzw.LZWInputStream
    public int readNextCode() throws IOException {
        int readNextCode = super.readNextCode();
        if (readNextCode >= 0) {
            this.totalCodesRead++;
        }
        return readNextCode;
    }

    private void reAlignReading() throws IOException {
        long j = 8 - (this.totalCodesRead % 8);
        if (j == 8) {
            j = 0;
        }
        for (long j2 = 0; j2 < j; j2++) {
            readNextCode();
        }
        this.in.clearBitCache();
    }

    @Override // org.apache.commons.compress.compressors.lzw.LZWInputStream
    protected int addEntry(int i, byte b) throws IOException {
        int codeSize = 1 << getCodeSize();
        int addEntry = addEntry(i, b, codeSize);
        if (getTableSize() == codeSize && getCodeSize() < this.maxCodeSize) {
            reAlignReading();
            incrementCodeSize();
        }
        return addEntry;
    }

    @Override // org.apache.commons.compress.compressors.lzw.LZWInputStream
    protected int decompressNextSymbol() throws IOException {
        int readNextCode = readNextCode();
        if (readNextCode < 0) {
            return -1;
        }
        boolean z = false;
        if (this.blockMode && readNextCode == getClearCode()) {
            clearEntries();
            reAlignReading();
            resetCodeSize();
            resetPreviousCode();
            return 0;
        }
        if (readNextCode == getTableSize()) {
            addRepeatOfPreviousCode();
            z = true;
        } else if (readNextCode > getTableSize()) {
            throw new IOException(String.format("Invalid %d bit code 0x%x", Integer.valueOf(getCodeSize()), Integer.valueOf(readNextCode)));
        }
        return expandCodeToOutputStack(readNextCode, z);
    }

    public static boolean matches(byte[] bArr, int i) {
        return i > 3 && bArr[0] == 31 && bArr[1] == -99;
    }
}
