package org.tukaani.xz.lzma;

/* loaded from: classes3.dex */
final class State {
    private static final int LIT_LIT = 0;
    private static final int LIT_LONGREP = 8;
    private static final int LIT_MATCH = 7;
    private static final int LIT_SHORTREP = 9;
    private static final int LIT_STATES = 7;
    private static final int MATCH_LIT = 4;
    private static final int MATCH_LIT_LIT = 1;
    private static final int NONLIT_MATCH = 10;
    private static final int NONLIT_REP = 11;
    private static final int REP_LIT = 5;
    private static final int REP_LIT_LIT = 2;
    private static final int SHORTREP_LIT = 6;
    private static final int SHORTREP_LIT_LIT = 3;
    static final int STATES = 12;
    private int state;

    /* JADX INFO: Access modifiers changed from: package-private */
    public State() {
    }

    State(State state) {
        this.state = state.state;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int get() {
        return this.state;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isLiteral() {
        return this.state < 7;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void reset() {
        this.state = 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void set(State state) {
        this.state = state.state;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateLiteral() {
        int i = this.state;
        this.state = i <= 3 ? 0 : i <= 9 ? i - 3 : i - 6;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateLongRep() {
        this.state = this.state < 7 ? 8 : 11;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateMatch() {
        this.state = this.state >= 7 ? 10 : 7;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateShortRep() {
        this.state = this.state < 7 ? 9 : 11;
    }
}
