package org.tukaani.xz.delta;

/* loaded from: classes3.dex */
abstract class DeltaCoder {
    static final int DISTANCE_MASK = 255;
    static final int DISTANCE_MAX = 256;
    static final int DISTANCE_MIN = 1;
    final int distance;
    final byte[] history = new byte[256];
    int pos = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DeltaCoder(int i) {
        if (i < 1 || i > 256) {
            throw new IllegalArgumentException();
        }
        this.distance = i;
    }
}
