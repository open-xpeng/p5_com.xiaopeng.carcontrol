package org.apache.commons.compress.archivers.sevenz;

import java.util.BitSet;

/* loaded from: classes3.dex */
class Archive {
    SevenZArchiveEntry[] files;
    Folder[] folders;
    long[] packCrcs;
    BitSet packCrcsDefined;
    long packPos;
    long[] packSizes;
    StreamMap streamMap;
    SubStreamsInfo subStreamsInfo;

    public String toString() {
        return "Archive with packed streams starting at offset " + this.packPos + ", " + lengthOf(this.packSizes) + " pack sizes, " + lengthOf(this.packCrcs) + " CRCs, " + lengthOf(this.folders) + " folders, " + lengthOf(this.files) + " files and " + this.streamMap;
    }

    private static String lengthOf(long[] jArr) {
        return jArr == null ? "(null)" : String.valueOf(jArr.length);
    }

    private static String lengthOf(Object[] objArr) {
        return objArr == null ? "(null)" : String.valueOf(objArr.length);
    }
}
