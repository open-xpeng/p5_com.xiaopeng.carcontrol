package org.apache.commons.compress.archivers.sevenz;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;
import org.apache.commons.compress.archivers.ArchiveEntry;

/* loaded from: classes3.dex */
public class SevenZArchiveEntry implements ArchiveEntry {
    private long accessDate;
    private long compressedCrc;
    private long compressedSize;
    private Iterable<? extends SevenZMethodConfiguration> contentMethods;
    private long crc;
    private long creationDate;
    private boolean hasAccessDate;
    private boolean hasCrc;
    private boolean hasCreationDate;
    private boolean hasLastModifiedDate;
    private boolean hasStream;
    private boolean hasWindowsAttributes;
    private boolean isAntiItem;
    private boolean isDirectory;
    private long lastModifiedDate;
    private String name;
    private long size;
    private int windowsAttributes;

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public boolean hasStream() {
        return this.hasStream;
    }

    public void setHasStream(boolean z) {
        this.hasStream = z;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public boolean isDirectory() {
        return this.isDirectory;
    }

    public void setDirectory(boolean z) {
        this.isDirectory = z;
    }

    public boolean isAntiItem() {
        return this.isAntiItem;
    }

    public void setAntiItem(boolean z) {
        this.isAntiItem = z;
    }

    public boolean getHasCreationDate() {
        return this.hasCreationDate;
    }

    public void setHasCreationDate(boolean z) {
        this.hasCreationDate = z;
    }

    public Date getCreationDate() {
        if (this.hasCreationDate) {
            return ntfsTimeToJavaTime(this.creationDate);
        }
        throw new UnsupportedOperationException("The entry doesn't have this timestamp");
    }

    public void setCreationDate(long j) {
        this.creationDate = j;
    }

    public void setCreationDate(Date date) {
        boolean z = date != null;
        this.hasCreationDate = z;
        if (z) {
            this.creationDate = javaTimeToNtfsTime(date);
        }
    }

    public boolean getHasLastModifiedDate() {
        return this.hasLastModifiedDate;
    }

    public void setHasLastModifiedDate(boolean z) {
        this.hasLastModifiedDate = z;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public Date getLastModifiedDate() {
        if (this.hasLastModifiedDate) {
            return ntfsTimeToJavaTime(this.lastModifiedDate);
        }
        throw new UnsupportedOperationException("The entry doesn't have this timestamp");
    }

    public void setLastModifiedDate(long j) {
        this.lastModifiedDate = j;
    }

    public void setLastModifiedDate(Date date) {
        boolean z = date != null;
        this.hasLastModifiedDate = z;
        if (z) {
            this.lastModifiedDate = javaTimeToNtfsTime(date);
        }
    }

    public boolean getHasAccessDate() {
        return this.hasAccessDate;
    }

    public void setHasAccessDate(boolean z) {
        this.hasAccessDate = z;
    }

    public Date getAccessDate() {
        if (this.hasAccessDate) {
            return ntfsTimeToJavaTime(this.accessDate);
        }
        throw new UnsupportedOperationException("The entry doesn't have this timestamp");
    }

    public void setAccessDate(long j) {
        this.accessDate = j;
    }

    public void setAccessDate(Date date) {
        boolean z = date != null;
        this.hasAccessDate = z;
        if (z) {
            this.accessDate = javaTimeToNtfsTime(date);
        }
    }

    public boolean getHasWindowsAttributes() {
        return this.hasWindowsAttributes;
    }

    public void setHasWindowsAttributes(boolean z) {
        this.hasWindowsAttributes = z;
    }

    public int getWindowsAttributes() {
        return this.windowsAttributes;
    }

    public void setWindowsAttributes(int i) {
        this.windowsAttributes = i;
    }

    public boolean getHasCrc() {
        return this.hasCrc;
    }

    public void setHasCrc(boolean z) {
        this.hasCrc = z;
    }

    @Deprecated
    public int getCrc() {
        return (int) this.crc;
    }

    @Deprecated
    public void setCrc(int i) {
        this.crc = i;
    }

    public long getCrcValue() {
        return this.crc;
    }

    public void setCrcValue(long j) {
        this.crc = j;
    }

    @Deprecated
    int getCompressedCrc() {
        return (int) this.compressedCrc;
    }

    @Deprecated
    void setCompressedCrc(int i) {
        this.compressedCrc = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long getCompressedCrcValue() {
        return this.compressedCrc;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCompressedCrcValue(long j) {
        this.compressedCrc = j;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public long getSize() {
        return this.size;
    }

    public void setSize(long j) {
        this.size = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long getCompressedSize() {
        return this.compressedSize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCompressedSize(long j) {
        this.compressedSize = j;
    }

    public void setContentMethods(Iterable<? extends SevenZMethodConfiguration> iterable) {
        if (iterable != null) {
            LinkedList linkedList = new LinkedList();
            for (SevenZMethodConfiguration sevenZMethodConfiguration : iterable) {
                linkedList.addLast(sevenZMethodConfiguration);
            }
            this.contentMethods = Collections.unmodifiableList(linkedList);
            return;
        }
        this.contentMethods = null;
    }

    public Iterable<? extends SevenZMethodConfiguration> getContentMethods() {
        return this.contentMethods;
    }

    public static Date ntfsTimeToJavaTime(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        calendar.set(1601, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        return new Date(calendar.getTimeInMillis() + (j / 10000));
    }

    public static long javaTimeToNtfsTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        calendar.set(1601, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        return (date.getTime() - calendar.getTimeInMillis()) * 1000 * 10;
    }
}
