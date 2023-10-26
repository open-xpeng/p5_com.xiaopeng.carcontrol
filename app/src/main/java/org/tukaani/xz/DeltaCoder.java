package org.tukaani.xz;

/* loaded from: classes3.dex */
abstract class DeltaCoder implements FilterCoder {
    public static final long FILTER_ID = 3;

    @Override // org.tukaani.xz.FilterCoder
    public boolean changesSize() {
        return false;
    }

    @Override // org.tukaani.xz.FilterCoder
    public boolean lastOK() {
        return false;
    }

    @Override // org.tukaani.xz.FilterCoder
    public boolean nonLastOK() {
        return true;
    }
}
