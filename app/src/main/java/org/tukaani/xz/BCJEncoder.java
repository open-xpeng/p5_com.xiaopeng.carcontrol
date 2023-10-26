package org.tukaani.xz;

/* loaded from: classes3.dex */
class BCJEncoder extends BCJCoder implements FilterEncoder {
    static final /* synthetic */ boolean $assertionsDisabled;
    static /* synthetic */ Class class$org$tukaani$xz$BCJEncoder;
    private final long filterID;
    private final BCJOptions options;
    private final byte[] props;

    static {
        if (class$org$tukaani$xz$BCJEncoder == null) {
            class$org$tukaani$xz$BCJEncoder = class$("org.tukaani.xz.BCJEncoder");
        }
        $assertionsDisabled = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BCJEncoder(BCJOptions bCJOptions, long j) {
        if (!$assertionsDisabled && !isBCJFilterID(j)) {
            throw new AssertionError();
        }
        int startOffset = bCJOptions.getStartOffset();
        if (startOffset == 0) {
            this.props = new byte[0];
        } else {
            this.props = new byte[4];
            for (int i = 0; i < 4; i++) {
                this.props[i] = (byte) (startOffset >>> (i * 8));
            }
        }
        this.filterID = j;
        this.options = (BCJOptions) bCJOptions.clone();
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // org.tukaani.xz.FilterEncoder
    public long getFilterID() {
        return this.filterID;
    }

    @Override // org.tukaani.xz.FilterEncoder
    public byte[] getFilterProps() {
        return this.props;
    }

    @Override // org.tukaani.xz.FilterEncoder
    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream) {
        return this.options.getOutputStream(finishableOutputStream);
    }

    @Override // org.tukaani.xz.FilterEncoder
    public boolean supportsFlushing() {
        return false;
    }
}
