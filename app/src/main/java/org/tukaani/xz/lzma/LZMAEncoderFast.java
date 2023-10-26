package org.tukaani.xz.lzma;

import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.lz.LZEncoder;
import org.tukaani.xz.lz.Matches;
import org.tukaani.xz.rangecoder.RangeEncoder;

/* loaded from: classes3.dex */
final class LZMAEncoderFast extends LZMAEncoder {
    private static int EXTRA_SIZE_AFTER = 272;
    private static int EXTRA_SIZE_BEFORE = 1;
    private Matches matches;

    /* JADX INFO: Access modifiers changed from: package-private */
    public LZMAEncoderFast(RangeEncoder rangeEncoder, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        super(rangeEncoder, LZEncoder.getInstance(i4, Math.max(i5, EXTRA_SIZE_BEFORE), EXTRA_SIZE_AFTER, i6, LZMA2Options.NICE_LEN_MAX, i7, i8), i, i2, i3, i4, i6);
        this.matches = null;
    }

    private boolean changePair(int i, int i2) {
        return i < (i2 >>> 7);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getMemoryUsage(int i, int i2, int i3) {
        return LZEncoder.getMemoryUsage(i, Math.max(i2, EXTRA_SIZE_BEFORE), EXTRA_SIZE_AFTER, LZMA2Options.NICE_LEN_MAX, i3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00b6, code lost:
        if (r8 < 128) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00b8, code lost:
        r4 = 1;
     */
    @Override // org.tukaani.xz.lzma.LZMAEncoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    int getNextSymbol() {
        /*
            Method dump skipped, instructions count: 317
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.xz.lzma.LZMAEncoderFast.getNextSymbol():int");
    }
}
