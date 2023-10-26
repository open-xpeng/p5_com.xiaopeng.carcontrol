package org.tukaani.xz;

/* loaded from: classes3.dex */
class DeltaEncoder extends DeltaCoder implements FilterEncoder {
    private final DeltaOptions options;
    private final byte[] props;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DeltaEncoder(DeltaOptions deltaOptions) {
        this.props = r1;
        byte[] bArr = {(byte) (deltaOptions.getDistance() - 1)};
        this.options = (DeltaOptions) deltaOptions.clone();
    }

    @Override // org.tukaani.xz.FilterEncoder
    public long getFilterID() {
        return 3L;
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
        return true;
    }
}
