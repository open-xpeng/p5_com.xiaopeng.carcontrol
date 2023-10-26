package org.tukaani.xz.lz;

/* loaded from: classes3.dex */
final class HC4 extends LZEncoder {
    static final /* synthetic */ boolean $assertionsDisabled;
    static /* synthetic */ Class class$org$tukaani$xz$lz$HC4;
    private final int[] chain;
    private int cyclicPos;
    private final int cyclicSize;
    private final int depthLimit;
    private final Hash234 hash;
    private int lzPos;
    private final Matches matches;

    static {
        if (class$org$tukaani$xz$lz$HC4 == null) {
            class$org$tukaani$xz$lz$HC4 = class$("org.tukaani.xz.lz.HC4");
        }
        $assertionsDisabled = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public HC4(int i, int i2, int i3, int i4, int i5, int i6) {
        super(i, i2, i3, i4, i5);
        this.cyclicPos = -1;
        this.hash = new Hash234(i);
        int i7 = i + 1;
        this.cyclicSize = i7;
        this.chain = new int[i7];
        this.lzPos = i7;
        this.matches = new Matches(i4 - 1);
        this.depthLimit = i6 <= 0 ? (i4 / 4) + 4 : i6;
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getMemoryUsage(int i) {
        return Hash234.getMemoryUsage(i) + (i / 256) + 10;
    }

    private int movePos() {
        int movePos = movePos(4, 4);
        if (movePos != 0) {
            int i = this.lzPos + 1;
            this.lzPos = i;
            if (i == Integer.MAX_VALUE) {
                int i2 = Integer.MAX_VALUE - this.cyclicSize;
                this.hash.normalize(i2);
                normalize(this.chain, i2);
                this.lzPos -= i2;
            }
            int i3 = this.cyclicPos + 1;
            this.cyclicPos = i3;
            if (i3 == this.cyclicSize) {
                this.cyclicPos = 0;
            }
        }
        return movePos;
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x0143, code lost:
        return r13.matches;
     */
    @Override // org.tukaani.xz.lz.LZEncoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.tukaani.xz.lz.Matches getMatches() {
        /*
            Method dump skipped, instructions count: 324
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.xz.lz.HC4.getMatches():org.tukaani.xz.lz.Matches");
    }

    @Override // org.tukaani.xz.lz.LZEncoder
    public void skip(int i) {
        if (!$assertionsDisabled && i < 0) {
            throw new AssertionError();
        }
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            if (movePos() != 0) {
                this.hash.calcHashes(this.buf, this.readPos);
                this.chain[this.cyclicPos] = this.hash.getHash4Pos();
                this.hash.updateTables(this.lzPos);
            }
            i = i2;
        }
    }
}
