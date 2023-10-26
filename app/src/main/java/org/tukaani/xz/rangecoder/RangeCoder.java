package org.tukaani.xz.rangecoder;

import java.util.Arrays;

/* loaded from: classes3.dex */
public abstract class RangeCoder {
    static final int BIT_MODEL_TOTAL = 2048;
    static final int BIT_MODEL_TOTAL_BITS = 11;
    static final int MOVE_BITS = 5;
    static final short PROB_INIT = 1024;
    static final int SHIFT_BITS = 8;
    static final int TOP_MASK = -16777216;

    public static final void initProbs(short[] sArr) {
        Arrays.fill(sArr, (short) PROB_INIT);
    }
}
