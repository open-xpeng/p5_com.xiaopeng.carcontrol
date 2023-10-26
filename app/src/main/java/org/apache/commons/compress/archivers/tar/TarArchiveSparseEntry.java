package org.apache.commons.compress.archivers.tar;

import java.io.IOException;

/* loaded from: classes3.dex */
public class TarArchiveSparseEntry implements TarConstants {
    private final boolean isExtended;

    public TarArchiveSparseEntry(byte[] bArr) throws IOException {
        this.isExtended = TarUtils.parseBoolean(bArr, 504);
    }

    public boolean isExtended() {
        return this.isExtended;
    }
}
