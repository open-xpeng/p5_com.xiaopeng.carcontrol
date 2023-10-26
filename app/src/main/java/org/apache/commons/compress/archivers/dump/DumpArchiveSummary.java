package org.apache.commons.compress.archivers.dump;

import java.io.IOException;
import java.util.Date;
import org.apache.commons.compress.archivers.zip.ZipEncoding;

/* loaded from: classes3.dex */
public class DumpArchiveSummary {
    private String devname;
    private long dumpDate;
    private String filesys;
    private int firstrec;
    private int flags;
    private String hostname;
    private String label;
    private int level;
    private int ntrec;
    private long previousDumpDate;
    private int volume;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DumpArchiveSummary(byte[] bArr, ZipEncoding zipEncoding) throws IOException {
        this.dumpDate = DumpArchiveUtil.convert32(bArr, 4) * 1000;
        this.previousDumpDate = DumpArchiveUtil.convert32(bArr, 8) * 1000;
        this.volume = DumpArchiveUtil.convert32(bArr, 12);
        this.label = DumpArchiveUtil.decode(zipEncoding, bArr, 676, 16).trim();
        this.level = DumpArchiveUtil.convert32(bArr, 692);
        this.filesys = DumpArchiveUtil.decode(zipEncoding, bArr, 696, 64).trim();
        this.devname = DumpArchiveUtil.decode(zipEncoding, bArr, 760, 64).trim();
        this.hostname = DumpArchiveUtil.decode(zipEncoding, bArr, 824, 64).trim();
        this.flags = DumpArchiveUtil.convert32(bArr, 888);
        this.firstrec = DumpArchiveUtil.convert32(bArr, 892);
        this.ntrec = DumpArchiveUtil.convert32(bArr, 896);
    }

    public Date getDumpDate() {
        return new Date(this.dumpDate);
    }

    public void setDumpDate(Date date) {
        this.dumpDate = date.getTime();
    }

    public Date getPreviousDumpDate() {
        return new Date(this.previousDumpDate);
    }

    public void setPreviousDumpDate(Date date) {
        this.previousDumpDate = date.getTime();
    }

    public int getVolume() {
        return this.volume;
    }

    public void setVolume(int i) {
        this.volume = i;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public String getFilesystem() {
        return this.filesys;
    }

    public void setFilesystem(String str) {
        this.filesys = str;
    }

    public String getDevname() {
        return this.devname;
    }

    public void setDevname(String str) {
        this.devname = str;
    }

    public String getHostname() {
        return this.hostname;
    }

    public void setHostname(String str) {
        this.hostname = str;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int i) {
        this.flags = i;
    }

    public int getFirstRecord() {
        return this.firstrec;
    }

    public void setFirstRecord(int i) {
        this.firstrec = i;
    }

    public int getNTRec() {
        return this.ntrec;
    }

    public void setNTRec(int i) {
        this.ntrec = i;
    }

    public boolean isNewHeader() {
        return (this.flags & 1) == 1;
    }

    public boolean isNewInode() {
        return (this.flags & 2) == 2;
    }

    public boolean isCompressed() {
        return (this.flags & 128) == 128;
    }

    public boolean isMetaDataOnly() {
        return (this.flags & 256) == 256;
    }

    public boolean isExtendedAttributes() {
        return (this.flags & 32768) == 32768;
    }

    public int hashCode() {
        String str = this.label;
        int hashCode = (int) ((str != null ? str.hashCode() : 17) + (this.dumpDate * 31));
        String str2 = this.hostname;
        if (str2 != null) {
            hashCode = (str2.hashCode() * 31) + 17;
        }
        String str3 = this.devname;
        return str3 != null ? (str3.hashCode() * 31) + 17 : hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass().equals(getClass())) {
            DumpArchiveSummary dumpArchiveSummary = (DumpArchiveSummary) obj;
            if (this.dumpDate == dumpArchiveSummary.dumpDate && getHostname() != null && getHostname().equals(dumpArchiveSummary.getHostname()) && getDevname() != null && getDevname().equals(dumpArchiveSummary.getDevname())) {
                return true;
            }
        }
        return false;
    }
}
