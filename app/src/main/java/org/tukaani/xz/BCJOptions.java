package org.tukaani.xz;

/* loaded from: classes3.dex */
abstract class BCJOptions extends FilterOptions {
    static final /* synthetic */ boolean $assertionsDisabled;
    static /* synthetic */ Class class$org$tukaani$xz$BCJOptions;
    private final int alignment;
    int startOffset = 0;

    static {
        if (class$org$tukaani$xz$BCJOptions == null) {
            class$org$tukaani$xz$BCJOptions = class$("org.tukaani.xz.BCJOptions");
        }
        $assertionsDisabled = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BCJOptions(int i) {
        this.alignment = i;
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            if ($assertionsDisabled) {
                throw new RuntimeException();
            }
            throw new AssertionError();
        }
    }

    @Override // org.tukaani.xz.FilterOptions
    public int getDecoderMemoryUsage() {
        return SimpleInputStream.getMemoryUsage();
    }

    @Override // org.tukaani.xz.FilterOptions
    public int getEncoderMemoryUsage() {
        return SimpleOutputStream.getMemoryUsage();
    }

    public int getStartOffset() {
        return this.startOffset;
    }

    public void setStartOffset(int i) throws UnsupportedOptionsException {
        if (((this.alignment - 1) & i) != 0) {
            throw new UnsupportedOptionsException(new StringBuffer().append("Start offset must be a multiple of ").append(this.alignment).toString());
        }
        this.startOffset = i;
    }
}
