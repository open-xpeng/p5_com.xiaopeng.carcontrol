package org.apache.commons.compress.archivers.zip;

/* loaded from: classes3.dex */
public class ScatterStatistics {
    private final long compressionElapsed;
    private final long mergingElapsed;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ScatterStatistics(long j, long j2) {
        this.compressionElapsed = j;
        this.mergingElapsed = j2;
    }

    public long getCompressionElapsed() {
        return this.compressionElapsed;
    }

    public long getMergingElapsed() {
        return this.mergingElapsed;
    }

    public String toString() {
        return "compressionElapsed=" + this.compressionElapsed + "ms, mergingElapsed=" + this.mergingElapsed + "ms";
    }
}
