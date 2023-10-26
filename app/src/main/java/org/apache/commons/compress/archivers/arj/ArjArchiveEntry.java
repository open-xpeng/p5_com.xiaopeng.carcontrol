package org.apache.commons.compress.archivers.arj;

import java.io.File;
import java.util.Date;
import java.util.regex.Matcher;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipUtil;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes3.dex */
public class ArjArchiveEntry implements ArchiveEntry {
    private final LocalFileHeader localFileHeader;

    /* loaded from: classes3.dex */
    public static class HostOs {
        public static final int AMIGA = 3;
        public static final int APPLE_GS = 6;
        public static final int ATARI_ST = 7;
        public static final int DOS = 0;
        public static final int MAC_OS = 4;
        public static final int NEXT = 8;
        public static final int OS_2 = 5;
        public static final int PRIMOS = 1;
        public static final int UNIX = 2;
        public static final int VAX_VMS = 9;
        public static final int WIN32 = 11;
        public static final int WIN95 = 10;
    }

    public ArjArchiveEntry() {
        this.localFileHeader = new LocalFileHeader();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ArjArchiveEntry(LocalFileHeader localFileHeader) {
        this.localFileHeader = localFileHeader;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public String getName() {
        if ((this.localFileHeader.arjFlags & 16) != 0) {
            return this.localFileHeader.name.replaceAll(MqttTopic.TOPIC_LEVEL_SEPARATOR, Matcher.quoteReplacement(File.separator));
        }
        return this.localFileHeader.name;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public long getSize() {
        return this.localFileHeader.originalSize;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public boolean isDirectory() {
        return this.localFileHeader.fileType == 3;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public Date getLastModifiedDate() {
        return new Date(isHostOsUnix() ? this.localFileHeader.dateTimeModified * 1000 : ZipUtil.dosToJavaTime(4294967295L & this.localFileHeader.dateTimeModified));
    }

    public int getMode() {
        return this.localFileHeader.fileAccessMode;
    }

    public int getUnixMode() {
        if (isHostOsUnix()) {
            return getMode();
        }
        return 0;
    }

    public int getHostOs() {
        return this.localFileHeader.hostOS;
    }

    public boolean isHostOsUnix() {
        return getHostOs() == 2 || getHostOs() == 8;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getMethod() {
        return this.localFileHeader.method;
    }
}
