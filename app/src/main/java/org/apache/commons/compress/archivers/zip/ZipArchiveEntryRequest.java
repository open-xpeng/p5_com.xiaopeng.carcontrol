package org.apache.commons.compress.archivers.zip;

import java.io.InputStream;
import org.apache.commons.compress.parallel.InputStreamSupplier;

/* loaded from: classes3.dex */
public class ZipArchiveEntryRequest {
    private final int method;
    private final InputStreamSupplier payloadSupplier;
    private final ZipArchiveEntry zipArchiveEntry;

    private ZipArchiveEntryRequest(ZipArchiveEntry zipArchiveEntry, InputStreamSupplier inputStreamSupplier) {
        this.zipArchiveEntry = zipArchiveEntry;
        this.payloadSupplier = inputStreamSupplier;
        this.method = zipArchiveEntry.getMethod();
    }

    public static ZipArchiveEntryRequest createZipArchiveEntryRequest(ZipArchiveEntry zipArchiveEntry, InputStreamSupplier inputStreamSupplier) {
        return new ZipArchiveEntryRequest(zipArchiveEntry, inputStreamSupplier);
    }

    public InputStream getPayloadStream() {
        return this.payloadSupplier.get();
    }

    public int getMethod() {
        return this.method;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ZipArchiveEntry getZipArchiveEntry() {
        return this.zipArchiveEntry;
    }
}
