package org.apache.commons.compress.archivers.tar;

import com.xiaopeng.lib.apirouter.ClientConstants;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.CountingOutputStream;

/* loaded from: classes3.dex */
public class TarArchiveOutputStream extends ArchiveOutputStream {
    private static final ZipEncoding ASCII = ZipEncodingHelper.getZipEncoding("ASCII");
    public static final int BIGNUMBER_ERROR = 0;
    public static final int BIGNUMBER_POSIX = 2;
    public static final int BIGNUMBER_STAR = 1;
    public static final int LONGFILE_ERROR = 0;
    public static final int LONGFILE_GNU = 2;
    public static final int LONGFILE_POSIX = 3;
    public static final int LONGFILE_TRUNCATE = 1;
    private boolean addPaxHeadersForNonAsciiNames;
    private final byte[] assemBuf;
    private int assemLen;
    private int bigNumberMode;
    private boolean closed;
    private long currBytes;
    private String currName;
    private long currSize;
    final String encoding;
    private boolean finished;
    private boolean haveUnclosedEntry;
    private int longFileMode;
    private final OutputStream out;
    private final byte[] recordBuf;
    private final int recordSize;
    private final int recordsPerBlock;
    private int recordsWritten;
    private final ZipEncoding zipEncoding;

    private boolean shouldBeReplaced(char c) {
        return c == 0 || c == '/' || c == '\\';
    }

    public TarArchiveOutputStream(OutputStream outputStream) {
        this(outputStream, (int) TarConstants.DEFAULT_BLKSIZE, 512);
    }

    public TarArchiveOutputStream(OutputStream outputStream, String str) {
        this(outputStream, TarConstants.DEFAULT_BLKSIZE, 512, str);
    }

    public TarArchiveOutputStream(OutputStream outputStream, int i) {
        this(outputStream, i, 512);
    }

    public TarArchiveOutputStream(OutputStream outputStream, int i, String str) {
        this(outputStream, i, 512, str);
    }

    public TarArchiveOutputStream(OutputStream outputStream, int i, int i2) {
        this(outputStream, i, i2, null);
    }

    public TarArchiveOutputStream(OutputStream outputStream, int i, int i2, String str) {
        this.longFileMode = 0;
        this.bigNumberMode = 0;
        this.closed = false;
        this.haveUnclosedEntry = false;
        this.finished = false;
        this.addPaxHeadersForNonAsciiNames = false;
        this.out = new CountingOutputStream(outputStream);
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
        this.assemLen = 0;
        this.assemBuf = new byte[i2];
        this.recordBuf = new byte[i2];
        this.recordSize = i2;
        this.recordsPerBlock = i / i2;
    }

    public void setLongFileMode(int i) {
        this.longFileMode = i;
    }

    public void setBigNumberMode(int i) {
        this.bigNumberMode = i;
    }

    public void setAddPaxHeadersForNonAsciiNames(boolean z) {
        this.addPaxHeadersForNonAsciiNames = z;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    @Deprecated
    public int getCount() {
        return (int) getBytesWritten();
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public long getBytesWritten() {
        return ((CountingOutputStream) this.out).getBytesWritten();
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void finish() throws IOException {
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        if (this.haveUnclosedEntry) {
            throw new IOException("This archives contains unclosed entries.");
        }
        writeEOFRecord();
        writeEOFRecord();
        padAsNeeded();
        this.out.flush();
        this.finished = true;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.finished) {
            finish();
        }
        if (this.closed) {
            return;
        }
        this.out.close();
        this.closed = true;
    }

    public int getRecordSize() {
        return this.recordSize;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        TarArchiveEntry tarArchiveEntry = (TarArchiveEntry) archiveEntry;
        HashMap hashMap = new HashMap();
        String name = tarArchiveEntry.getName();
        boolean handleLongName = handleLongName(tarArchiveEntry, name, hashMap, ClientConstants.ALIAS.PATH, TarConstants.LF_GNUTYPE_LONGNAME, "file name");
        String linkName = tarArchiveEntry.getLinkName();
        boolean z = linkName != null && linkName.length() > 0 && handleLongName(tarArchiveEntry, linkName, hashMap, "linkpath", TarConstants.LF_GNUTYPE_LONGLINK, "link name");
        int i = this.bigNumberMode;
        if (i == 2) {
            addPaxHeadersForBigNumbers(hashMap, tarArchiveEntry);
        } else if (i != 1) {
            failForBigNumbers(tarArchiveEntry);
        }
        if (this.addPaxHeadersForNonAsciiNames && !handleLongName && !ASCII.canEncode(name)) {
            hashMap.put(ClientConstants.ALIAS.PATH, name);
        }
        if (this.addPaxHeadersForNonAsciiNames && !z && ((tarArchiveEntry.isLink() || tarArchiveEntry.isSymbolicLink()) && !ASCII.canEncode(linkName))) {
            hashMap.put("linkpath", linkName);
        }
        if (hashMap.size() > 0) {
            writePaxHeaders(tarArchiveEntry, name, hashMap);
        }
        tarArchiveEntry.writeEntryHeader(this.recordBuf, this.zipEncoding, this.bigNumberMode == 1);
        writeRecord(this.recordBuf);
        this.currBytes = 0L;
        if (tarArchiveEntry.isDirectory()) {
            this.currSize = 0L;
        } else {
            this.currSize = tarArchiveEntry.getSize();
        }
        this.currName = name;
        this.haveUnclosedEntry = true;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void closeArchiveEntry() throws IOException {
        byte[] bArr;
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (!this.haveUnclosedEntry) {
            throw new IOException("No current entry to close");
        }
        int i = this.assemLen;
        if (i > 0) {
            while (true) {
                bArr = this.assemBuf;
                if (i >= bArr.length) {
                    break;
                }
                bArr[i] = 0;
                i++;
            }
            writeRecord(bArr);
            this.currBytes += this.assemLen;
            this.assemLen = 0;
        }
        if (this.currBytes < this.currSize) {
            throw new IOException("entry '" + this.currName + "' closed at '" + this.currBytes + "' before the '" + this.currSize + "' bytes specified in the header were written");
        }
        this.haveUnclosedEntry = false;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (!this.haveUnclosedEntry) {
            throw new IllegalStateException("No current tar entry");
        }
        if (this.currBytes + i2 > this.currSize) {
            throw new IOException("request to write '" + i2 + "' bytes exceeds size in header of '" + this.currSize + "' bytes for entry '" + this.currName + "'");
        }
        int i3 = this.assemLen;
        if (i3 > 0) {
            int i4 = i3 + i2;
            byte[] bArr2 = this.recordBuf;
            if (i4 >= bArr2.length) {
                int length = bArr2.length - i3;
                System.arraycopy(this.assemBuf, 0, bArr2, 0, i3);
                System.arraycopy(bArr, i, this.recordBuf, this.assemLen, length);
                writeRecord(this.recordBuf);
                this.currBytes += this.recordBuf.length;
                i += length;
                i2 -= length;
                this.assemLen = 0;
            } else {
                System.arraycopy(bArr, i, this.assemBuf, i3, i2);
                i += i2;
                this.assemLen += i2;
                i2 = 0;
            }
        }
        while (i2 > 0) {
            if (i2 < this.recordBuf.length) {
                System.arraycopy(bArr, i, this.assemBuf, this.assemLen, i2);
                this.assemLen += i2;
                return;
            }
            writeRecord(bArr, i);
            int length2 = this.recordBuf.length;
            this.currBytes += length2;
            i2 -= length2;
            i += length2;
        }
    }

    void writePaxHeaders(TarArchiveEntry tarArchiveEntry, String str, Map<String, String> map) throws IOException {
        String str2 = "./PaxHeaders.X/" + stripTo7Bits(str);
        if (str2.length() >= 100) {
            str2 = str2.substring(0, 99);
        }
        TarArchiveEntry tarArchiveEntry2 = new TarArchiveEntry(str2, (byte) TarConstants.LF_PAX_EXTENDED_HEADER_LC);
        transferModTime(tarArchiveEntry, tarArchiveEntry2);
        StringWriter stringWriter = new StringWriter();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            int length = key.length() + value.length() + 3 + 2;
            String str3 = length + " " + key + "=" + value + "\n";
            int length2 = str3.getBytes("UTF-8").length;
            while (length != length2) {
                str3 = length2 + " " + key + "=" + value + "\n";
                int i = length2;
                length2 = str3.getBytes("UTF-8").length;
                length = i;
            }
            stringWriter.write(str3);
        }
        byte[] bytes = stringWriter.toString().getBytes("UTF-8");
        tarArchiveEntry2.setSize(bytes.length);
        putArchiveEntry(tarArchiveEntry2);
        write(bytes);
        closeArchiveEntry();
    }

    private String stripTo7Bits(String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char charAt = (char) (str.charAt(i) & 127);
            if (shouldBeReplaced(charAt)) {
                sb.append("_");
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    private void writeEOFRecord() throws IOException {
        Arrays.fill(this.recordBuf, (byte) 0);
        writeRecord(this.recordBuf);
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public ArchiveEntry createArchiveEntry(File file, String str) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new TarArchiveEntry(file, str);
    }

    private void writeRecord(byte[] bArr) throws IOException {
        if (bArr.length != this.recordSize) {
            throw new IOException("record to write has length '" + bArr.length + "' which is not the record size of '" + this.recordSize + "'");
        }
        this.out.write(bArr);
        this.recordsWritten++;
    }

    private void writeRecord(byte[] bArr, int i) throws IOException {
        int i2 = this.recordSize;
        if (i + i2 > bArr.length) {
            throw new IOException("record has length '" + bArr.length + "' with offset '" + i + "' which is less than the record size of '" + this.recordSize + "'");
        }
        this.out.write(bArr, i, i2);
        this.recordsWritten++;
    }

    private void padAsNeeded() throws IOException {
        int i = this.recordsWritten % this.recordsPerBlock;
        if (i != 0) {
            while (i < this.recordsPerBlock) {
                writeEOFRecord();
                i++;
            }
        }
    }

    private void addPaxHeadersForBigNumbers(Map<String, String> map, TarArchiveEntry tarArchiveEntry) {
        addPaxHeaderForBigNumber(map, "size", tarArchiveEntry.getSize(), TarConstants.MAXSIZE);
        addPaxHeaderForBigNumber(map, "gid", tarArchiveEntry.getLongGroupId(), TarConstants.MAXID);
        addPaxHeaderForBigNumber(map, "mtime", tarArchiveEntry.getModTime().getTime() / 1000, TarConstants.MAXSIZE);
        addPaxHeaderForBigNumber(map, "uid", tarArchiveEntry.getLongUserId(), TarConstants.MAXID);
        addPaxHeaderForBigNumber(map, "SCHILY.devmajor", tarArchiveEntry.getDevMajor(), TarConstants.MAXID);
        addPaxHeaderForBigNumber(map, "SCHILY.devminor", tarArchiveEntry.getDevMinor(), TarConstants.MAXID);
        failForBigNumber("mode", tarArchiveEntry.getMode(), TarConstants.MAXID);
    }

    private void addPaxHeaderForBigNumber(Map<String, String> map, String str, long j, long j2) {
        if (j < 0 || j > j2) {
            map.put(str, String.valueOf(j));
        }
    }

    private void failForBigNumbers(TarArchiveEntry tarArchiveEntry) {
        failForBigNumber("entry size", tarArchiveEntry.getSize(), TarConstants.MAXSIZE);
        failForBigNumberWithPosixMessage("group id", tarArchiveEntry.getLongGroupId(), TarConstants.MAXID);
        failForBigNumber("last modification time", tarArchiveEntry.getModTime().getTime() / 1000, TarConstants.MAXSIZE);
        failForBigNumber("user id", tarArchiveEntry.getLongUserId(), TarConstants.MAXID);
        failForBigNumber("mode", tarArchiveEntry.getMode(), TarConstants.MAXID);
        failForBigNumber("major device number", tarArchiveEntry.getDevMajor(), TarConstants.MAXID);
        failForBigNumber("minor device number", tarArchiveEntry.getDevMinor(), TarConstants.MAXID);
    }

    private void failForBigNumber(String str, long j, long j2) {
        failForBigNumber(str, j, j2, "");
    }

    private void failForBigNumberWithPosixMessage(String str, long j, long j2) {
        failForBigNumber(str, j, j2, " Use STAR or POSIX extensions to overcome this limit");
    }

    private void failForBigNumber(String str, long j, long j2, String str2) {
        if (j < 0 || j > j2) {
            throw new RuntimeException(str + " '" + j + "' is too big ( > " + j2 + " )." + str2);
        }
    }

    private boolean handleLongName(TarArchiveEntry tarArchiveEntry, String str, Map<String, String> map, String str2, byte b, String str3) throws IOException {
        ByteBuffer encode = this.zipEncoding.encode(str);
        int limit = encode.limit() - encode.position();
        if (limit >= 100) {
            int i = this.longFileMode;
            if (i == 3) {
                map.put(str2, str);
                return true;
            } else if (i == 2) {
                TarArchiveEntry tarArchiveEntry2 = new TarArchiveEntry(TarConstants.GNU_LONGLINK, b);
                tarArchiveEntry2.setSize(limit + 1);
                transferModTime(tarArchiveEntry, tarArchiveEntry2);
                putArchiveEntry(tarArchiveEntry2);
                write(encode.array(), encode.arrayOffset(), limit);
                write(0);
                closeArchiveEntry();
            } else if (i != 1) {
                throw new RuntimeException(str3 + " '" + str + "' is too long ( > 100 bytes)");
            }
        }
        return false;
    }

    private void transferModTime(TarArchiveEntry tarArchiveEntry, TarArchiveEntry tarArchiveEntry2) {
        Date modTime = tarArchiveEntry.getModTime();
        long time = modTime.getTime() / 1000;
        if (time < 0 || time > TarConstants.MAXSIZE) {
            modTime = new Date(0L);
        }
        tarArchiveEntry2.setModTime(modTime);
    }
}
