package org.tukaani.xz.index;

/* loaded from: classes3.dex */
class IndexRecord {
    final long uncompressed;
    final long unpadded;

    /* JADX INFO: Access modifiers changed from: package-private */
    public IndexRecord(long j, long j2) {
        this.unpadded = j;
        this.uncompressed = j2;
    }
}
