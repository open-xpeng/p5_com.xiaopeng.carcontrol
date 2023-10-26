package org.apache.commons.compress.compressors.snappy;

/* loaded from: classes3.dex */
public enum FramedSnappyDialect {
    STANDARD(true, true),
    IWORK_ARCHIVE(false, false);
    
    private final boolean checksumWithCompressedChunks;
    private final boolean streamIdentifier;

    FramedSnappyDialect(boolean z, boolean z2) {
        this.streamIdentifier = z;
        this.checksumWithCompressedChunks = z2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean hasStreamIdentifier() {
        return this.streamIdentifier;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean usesChecksumWithCompressedChunks() {
        return this.checksumWithCompressedChunks;
    }
}
