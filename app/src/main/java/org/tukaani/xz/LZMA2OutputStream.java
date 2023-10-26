package org.tukaani.xz;

import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.speech.common.SpeechConstant;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import org.tukaani.xz.lz.LZEncoder;
import org.tukaani.xz.lzma.LZMAEncoder;
import org.tukaani.xz.rangecoder.RangeEncoder;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class LZMA2OutputStream extends FinishableOutputStream {
    static final /* synthetic */ boolean $assertionsDisabled;
    static final int COMPRESSED_SIZE_MAX = 65536;
    static /* synthetic */ Class class$org$tukaani$xz$LZMA2OutputStream;
    private boolean dictResetNeeded;
    private final LZEncoder lz;
    private final LZMAEncoder lzma;
    private FinishableOutputStream out;
    private final DataOutputStream outData;
    private final int props;
    private final RangeEncoder rc;
    private boolean stateResetNeeded = true;
    private boolean propsNeeded = true;
    private int pendingSize = 0;
    private boolean finished = false;
    private IOException exception = null;
    private final byte[] tempBuf = new byte[1];

    static {
        if (class$org$tukaani$xz$LZMA2OutputStream == null) {
            class$org$tukaani$xz$LZMA2OutputStream = class$("org.tukaani.xz.LZMA2OutputStream");
        }
        $assertionsDisabled = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LZMA2OutputStream(FinishableOutputStream finishableOutputStream, LZMA2Options lZMA2Options) {
        this.dictResetNeeded = true;
        Objects.requireNonNull(finishableOutputStream);
        this.out = finishableOutputStream;
        this.outData = new DataOutputStream(finishableOutputStream);
        RangeEncoder rangeEncoder = new RangeEncoder(65536);
        this.rc = rangeEncoder;
        int dictSize = lZMA2Options.getDictSize();
        LZMAEncoder lZMAEncoder = LZMAEncoder.getInstance(rangeEncoder, lZMA2Options.getLc(), lZMA2Options.getLp(), lZMA2Options.getPb(), lZMA2Options.getMode(), dictSize, getExtraSizeBefore(dictSize), lZMA2Options.getNiceLen(), lZMA2Options.getMatchFinder(), lZMA2Options.getDepthLimit());
        this.lzma = lZMAEncoder;
        LZEncoder lZEncoder = lZMAEncoder.getLZEncoder();
        this.lz = lZEncoder;
        byte[] presetDict = lZMA2Options.getPresetDict();
        if (presetDict != null && presetDict.length > 0) {
            lZEncoder.setPresetDict(dictSize, presetDict);
            this.dictResetNeeded = false;
        }
        this.props = (((lZMA2Options.getPb() * 5) + lZMA2Options.getLp()) * 9) + lZMA2Options.getLc();
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private static int getExtraSizeBefore(int i) {
        if (65536 > i) {
            return 65536 - i;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getMemoryUsage(LZMA2Options lZMA2Options) {
        int dictSize = lZMA2Options.getDictSize();
        return LZMAEncoder.getMemoryUsage(lZMA2Options.getMode(), dictSize, getExtraSizeBefore(dictSize), lZMA2Options.getMatchFinder()) + 70;
    }

    private void writeChunk() throws IOException {
        int finish = this.rc.finish();
        int uncompressedSize = this.lzma.getUncompressedSize();
        boolean z = $assertionsDisabled;
        if (!z && finish <= 0) {
            throw new AssertionError(finish);
        }
        if (!z && uncompressedSize <= 0) {
            throw new AssertionError(uncompressedSize);
        }
        if (finish + 2 < uncompressedSize) {
            writeLZMA(uncompressedSize, finish);
        } else {
            this.lzma.reset();
            uncompressedSize = this.lzma.getUncompressedSize();
            if (!z && uncompressedSize <= 0) {
                throw new AssertionError(uncompressedSize);
            }
            writeUncompressed(uncompressedSize);
        }
        this.pendingSize -= uncompressedSize;
        this.lzma.resetUncompressedSize();
        this.rc.reset();
    }

    private void writeEndMarker() throws IOException {
        if (!$assertionsDisabled && this.finished) {
            throw new AssertionError();
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
        this.lz.setFinishing();
        while (this.pendingSize > 0) {
            try {
                this.lzma.encodeForLZMA2();
                writeChunk();
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
        this.out.write(0);
        this.finished = true;
    }

    private void writeLZMA(int i, int i2) throws IOException {
        int i3 = i - 1;
        this.outData.writeByte((this.propsNeeded ? this.dictResetNeeded ? 224 : ThemeManager.UI_MODE_THEME_MASK : this.stateResetNeeded ? SpeechConstant.SoundLocation.MAX_ANGLE : 128) | (i3 >>> 16));
        this.outData.writeShort(i3);
        this.outData.writeShort(i2 - 1);
        if (this.propsNeeded) {
            this.outData.writeByte(this.props);
        }
        this.rc.write(this.out);
        this.propsNeeded = false;
        this.stateResetNeeded = false;
        this.dictResetNeeded = false;
    }

    private void writeUncompressed(int i) throws IOException {
        while (true) {
            int i2 = 1;
            if (i <= 0) {
                this.stateResetNeeded = true;
                return;
            }
            int min = Math.min(i, 65536);
            DataOutputStream dataOutputStream = this.outData;
            if (!this.dictResetNeeded) {
                i2 = 2;
            }
            dataOutputStream.writeByte(i2);
            this.outData.writeShort(min - 1);
            this.lz.copyUncompressed(this.out, i, min);
            i -= min;
            this.dictResetNeeded = false;
        }
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.out != null) {
            if (!this.finished) {
                try {
                    writeEndMarker();
                } catch (IOException unused) {
                }
            }
            try {
                this.out.close();
            } catch (IOException e) {
                if (this.exception == null) {
                    this.exception = e;
                }
            }
            this.out = null;
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
    }

    @Override // org.tukaani.xz.FinishableOutputStream
    public void finish() throws IOException {
        if (this.finished) {
            return;
        }
        writeEndMarker();
        try {
            this.out.finish();
            this.finished = true;
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
        if (this.finished) {
            throw new XZIOException("Stream finished or closed");
        }
        try {
            this.lz.setFlushing();
            while (this.pendingSize > 0) {
                this.lzma.encodeForLZMA2();
                writeChunk();
            }
            this.out.flush();
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        byte[] bArr = this.tempBuf;
        bArr[0] = (byte) i;
        write(bArr, 0, 1);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        if (i < 0 || i2 < 0 || (i3 = i + i2) < 0 || i3 > bArr.length) {
            throw new IndexOutOfBoundsException();
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
        if (this.finished) {
            throw new XZIOException("Stream finished or closed");
        }
        while (i2 > 0) {
            try {
                int fillWindow = this.lz.fillWindow(bArr, i, i2);
                i += fillWindow;
                i2 -= fillWindow;
                this.pendingSize += fillWindow;
                if (this.lzma.encodeForLZMA2()) {
                    writeChunk();
                }
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }
}
