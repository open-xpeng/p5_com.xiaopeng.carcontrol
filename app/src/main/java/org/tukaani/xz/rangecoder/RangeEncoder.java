package org.tukaani.xz.rangecoder;

import com.xiaopeng.carcontrol.view.dialog.panel.ControlPanelManager;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes3.dex */
public final class RangeEncoder extends RangeCoder {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final int BIT_PRICE_SHIFT_BITS = 4;
    private static final int MOVE_REDUCING_BITS = 4;
    static /* synthetic */ Class class$org$tukaani$xz$rangecoder$RangeEncoder;
    private static final int[] prices;
    private final byte[] buf;
    private int bufPos;
    private byte cache;
    private int cacheSize;
    private long low;
    private int range;

    static {
        if (class$org$tukaani$xz$rangecoder$RangeEncoder == null) {
            class$org$tukaani$xz$rangecoder$RangeEncoder = class$("org.tukaani.xz.rangecoder.RangeEncoder");
        }
        $assertionsDisabled = true;
        prices = new int[128];
        for (int i = 8; i < 2048; i += 16) {
            int i2 = i;
            int i3 = 0;
            for (int i4 = 0; i4 < 4; i4++) {
                i2 *= i2;
                i3 <<= 1;
                while (((-65536) & i2) != 0) {
                    i2 >>>= 1;
                    i3++;
                }
            }
            prices[i >> 4] = 161 - i3;
        }
    }

    public RangeEncoder(int i) {
        this.buf = new byte[i];
        reset();
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public static int getBitPrice(int i, int i2) {
        if ($assertionsDisabled || i2 == 0 || i2 == 1) {
            return prices[(i ^ ((-i2) & ControlPanelManager.SUPER_SYSTEM_TYPE)) >>> 4];
        }
        throw new AssertionError();
    }

    public static int getBitTreePrice(short[] sArr, int i) {
        int length = i | sArr.length;
        int i2 = 0;
        do {
            int i3 = length & 1;
            length >>>= 1;
            i2 += getBitPrice(sArr[length], i3);
        } while (length != 1);
        return i2;
    }

    public static int getDirectBitsPrice(int i) {
        return i << 4;
    }

    public static int getReverseBitTreePrice(short[] sArr, int i) {
        int length = i | sArr.length;
        int i2 = 0;
        int i3 = 1;
        do {
            int i4 = length & 1;
            length >>>= 1;
            i2 += getBitPrice(sArr[i3], i4);
            i3 = (i3 << 1) | i4;
        } while (length != 1);
        return i2;
    }

    private void shiftLow() {
        int i;
        long j = this.low;
        int i2 = (int) (j >>> 32);
        if (i2 != 0 || j < 4278190080L) {
            byte b = this.cache;
            do {
                byte[] bArr = this.buf;
                int i3 = this.bufPos;
                this.bufPos = i3 + 1;
                bArr[i3] = (byte) (b + i2);
                b = 255;
                i = this.cacheSize - 1;
                this.cacheSize = i;
            } while (i != 0);
            this.cache = (byte) (this.low >>> 24);
        }
        this.cacheSize++;
        this.low = (this.low & 16777215) << 8;
    }

    public void encodeBit(short[] sArr, int i, int i2) {
        short s = sArr[i];
        int i3 = this.range;
        int i4 = (i3 >>> 11) * s;
        if (i2 == 0) {
            this.range = i4;
            sArr[i] = (short) (s + ((2048 - s) >>> 5));
        } else {
            this.low += i4 & 4294967295L;
            this.range = i3 - i4;
            sArr[i] = (short) (s - (s >>> 5));
        }
        int i5 = this.range;
        if (((-16777216) & i5) == 0) {
            this.range = i5 << 8;
            shiftLow();
        }
    }

    public void encodeBitTree(short[] sArr, int i) {
        int length = sArr.length;
        int i2 = 1;
        do {
            length >>>= 1;
            int i3 = i & length;
            encodeBit(sArr, i2, i3);
            i2 <<= 1;
            if (i3 != 0) {
                i2 |= 1;
                continue;
            }
        } while (length != 1);
    }

    public void encodeDirectBits(int i, int i2) {
        do {
            int i3 = this.range >>> 1;
            this.range = i3;
            i2--;
            this.low += (0 - ((i >>> i2) & 1)) & i3;
            if (((-16777216) & i3) == 0) {
                this.range = i3 << 8;
                shiftLow();
                continue;
            }
        } while (i2 != 0);
    }

    public void encodeReverseBitTree(short[] sArr, int i) {
        int length = i | sArr.length;
        int i2 = 1;
        do {
            int i3 = length & 1;
            length >>>= 1;
            encodeBit(sArr, i2, i3);
            i2 = (i2 << 1) | i3;
        } while (length != 1);
    }

    public int finish() {
        for (int i = 0; i < 5; i++) {
            shiftLow();
        }
        return this.bufPos;
    }

    public int getPendingSize() {
        return ((this.bufPos + this.cacheSize) + 5) - 1;
    }

    public void reset() {
        this.low = 0L;
        this.range = -1;
        this.cache = (byte) 0;
        this.cacheSize = 1;
        this.bufPos = 0;
    }

    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(this.buf, 0, this.bufPos);
    }
}
