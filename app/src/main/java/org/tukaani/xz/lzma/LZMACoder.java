package org.tukaani.xz.lzma;

import java.lang.reflect.Array;
import org.tukaani.xz.rangecoder.RangeCoder;

/* loaded from: classes3.dex */
abstract class LZMACoder {
    static final int ALIGN_BITS = 4;
    static final int ALIGN_MASK = 15;
    static final int ALIGN_SIZE = 16;
    static final int DIST_MODEL_END = 14;
    static final int DIST_MODEL_START = 4;
    static final int DIST_SLOTS = 64;
    static final int DIST_STATES = 4;
    static final int FULL_DISTANCES = 128;
    static final int MATCH_LEN_MAX = 273;
    static final int MATCH_LEN_MIN = 2;
    static final int POS_STATES_MAX = 16;
    static final int REPS = 4;
    final int posMask;
    final int[] reps = new int[4];
    final State state = new State();
    final short[][] isMatch = (short[][]) Array.newInstance(short.class, 12, 16);
    final short[] isRep = new short[12];
    final short[] isRep0 = new short[12];
    final short[] isRep1 = new short[12];
    final short[] isRep2 = new short[12];
    final short[][] isRep0Long = (short[][]) Array.newInstance(short.class, 12, 16);
    final short[][] distSlots = (short[][]) Array.newInstance(short.class, 4, 64);
    final short[][] distSpecial = {new short[2], new short[2], new short[4], new short[4], new short[8], new short[8], new short[16], new short[16], new short[32], new short[32]};
    final short[] distAlign = new short[16];

    /* loaded from: classes3.dex */
    abstract class LengthCoder {
        static final int HIGH_SYMBOLS = 256;
        static final int LOW_SYMBOLS = 8;
        static final int MID_SYMBOLS = 8;
        final short[] choice = new short[2];
        final short[][] low = (short[][]) Array.newInstance(short.class, 16, 8);
        final short[][] mid = (short[][]) Array.newInstance(short.class, 16, 8);
        final short[] high = new short[256];

        /* JADX INFO: Access modifiers changed from: package-private */
        public LengthCoder() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void reset() {
            RangeCoder.initProbs(this.choice);
            int i = 0;
            while (true) {
                short[][] sArr = this.low;
                if (i >= sArr.length) {
                    break;
                }
                RangeCoder.initProbs(sArr[i]);
                i++;
            }
            for (int i2 = 0; i2 < this.low.length; i2++) {
                RangeCoder.initProbs(this.mid[i2]);
            }
            RangeCoder.initProbs(this.high);
        }
    }

    /* loaded from: classes3.dex */
    abstract class LiteralCoder {
        private final int lc;
        private final int literalPosMask;

        /* loaded from: classes3.dex */
        abstract class LiteralSubcoder {
            final short[] probs = new short[768];

            /* JADX INFO: Access modifiers changed from: package-private */
            public LiteralSubcoder() {
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public void reset() {
                RangeCoder.initProbs(this.probs);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public LiteralCoder(int i, int i2) {
            this.lc = i;
            this.literalPosMask = (1 << i2) - 1;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final int getSubcoderIndex(int i, int i2) {
            int i3 = this.lc;
            return (i >> (8 - i3)) + ((i2 & this.literalPosMask) << i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LZMACoder(int i) {
        this.posMask = (1 << i) - 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final int getDistState(int i) {
        if (i < 6) {
            return i - 2;
        }
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void reset() {
        int[] iArr = this.reps;
        int i = 0;
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 0;
        this.state.reset();
        int i2 = 0;
        while (true) {
            short[][] sArr = this.isMatch;
            if (i2 >= sArr.length) {
                break;
            }
            RangeCoder.initProbs(sArr[i2]);
            i2++;
        }
        RangeCoder.initProbs(this.isRep);
        RangeCoder.initProbs(this.isRep0);
        RangeCoder.initProbs(this.isRep1);
        RangeCoder.initProbs(this.isRep2);
        int i3 = 0;
        while (true) {
            short[][] sArr2 = this.isRep0Long;
            if (i3 >= sArr2.length) {
                break;
            }
            RangeCoder.initProbs(sArr2[i3]);
            i3++;
        }
        int i4 = 0;
        while (true) {
            short[][] sArr3 = this.distSlots;
            if (i4 >= sArr3.length) {
                break;
            }
            RangeCoder.initProbs(sArr3[i4]);
            i4++;
        }
        while (true) {
            short[][] sArr4 = this.distSpecial;
            if (i >= sArr4.length) {
                RangeCoder.initProbs(this.distAlign);
                return;
            } else {
                RangeCoder.initProbs(sArr4[i]);
                i++;
            }
        }
    }
}
