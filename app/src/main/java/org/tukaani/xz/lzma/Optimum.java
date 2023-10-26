package org.tukaani.xz.lzma;

/* loaded from: classes3.dex */
final class Optimum {
    private static final int INFINITY_PRICE = 1073741824;
    int backPrev;
    int backPrev2;
    boolean hasPrev2;
    int optPrev;
    int optPrev2;
    boolean prev1IsLiteral;
    int price;
    final State state = new State();
    final int[] reps = new int[4];

    /* JADX INFO: Access modifiers changed from: package-private */
    public void reset() {
        this.price = 1073741824;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void set1(int i, int i2, int i3) {
        this.price = i;
        this.optPrev = i2;
        this.backPrev = i3;
        this.prev1IsLiteral = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void set2(int i, int i2, int i3) {
        this.price = i;
        this.optPrev = i2 + 1;
        this.backPrev = i3;
        this.prev1IsLiteral = true;
        this.hasPrev2 = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void set3(int i, int i2, int i3, int i4, int i5) {
        this.price = i;
        this.optPrev = i4 + i2 + 1;
        this.backPrev = i5;
        this.prev1IsLiteral = true;
        this.hasPrev2 = true;
        this.optPrev2 = i2;
        this.backPrev2 = i3;
    }
}
