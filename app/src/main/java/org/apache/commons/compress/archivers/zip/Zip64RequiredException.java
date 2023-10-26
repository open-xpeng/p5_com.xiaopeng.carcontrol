package org.apache.commons.compress.archivers.zip;

import java.util.zip.ZipException;

/* loaded from: classes3.dex */
public class Zip64RequiredException extends ZipException {
    static final String ARCHIVE_TOO_BIG_MESSAGE = "archive's size exceeds the limit of 4GByte.";
    static final String TOO_MANY_ENTRIES_MESSAGE = "archive contains more than 65535 entries.";
    private static final long serialVersionUID = 20110809;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getEntryTooBigMessage(ZipArchiveEntry zipArchiveEntry) {
        return zipArchiveEntry.getName() + "'s size exceeds the limit of 4GByte.";
    }

    public Zip64RequiredException(String str) {
        super(str);
    }
}
