package org.apache.commons.compress.archivers.tar;

import com.xiaopeng.lib.apirouter.ClientConstants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.ArchiveUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.tukaani.xz.common.Util;

/* loaded from: classes3.dex */
public class TarArchiveInputStream extends ArchiveInputStream {
    private static final int SMALL_BUFFER_SIZE = 256;
    private final byte[] SMALL_BUF;
    private final int blockSize;
    private TarArchiveEntry currEntry;
    final String encoding;
    private long entryOffset;
    private long entrySize;
    private Map<String, String> globalPaxHeaders;
    private boolean hasHitEOF;
    private final InputStream is;
    private final int recordSize;
    private final ZipEncoding zipEncoding;

    @Override // java.io.InputStream
    public void mark(int i) {
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    public TarArchiveInputStream(InputStream inputStream) {
        this(inputStream, (int) TarConstants.DEFAULT_BLKSIZE, 512);
    }

    public TarArchiveInputStream(InputStream inputStream, String str) {
        this(inputStream, TarConstants.DEFAULT_BLKSIZE, 512, str);
    }

    public TarArchiveInputStream(InputStream inputStream, int i) {
        this(inputStream, i, 512);
    }

    public TarArchiveInputStream(InputStream inputStream, int i, String str) {
        this(inputStream, i, 512, str);
    }

    public TarArchiveInputStream(InputStream inputStream, int i, int i2) {
        this(inputStream, i, i2, null);
    }

    public TarArchiveInputStream(InputStream inputStream, int i, int i2, String str) {
        this.SMALL_BUF = new byte[256];
        this.globalPaxHeaders = new HashMap();
        this.is = inputStream;
        this.hasHitEOF = false;
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
        this.recordSize = i2;
        this.blockSize = i;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.is.close();
    }

    public int getRecordSize() {
        return this.recordSize;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (isDirectory()) {
            return 0;
        }
        long j = this.entrySize;
        long j2 = this.entryOffset;
        if (j - j2 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) (j - j2);
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        if (j <= 0 || isDirectory()) {
            return 0L;
        }
        long skip = this.is.skip(Math.min(j, this.entrySize - this.entryOffset));
        count(skip);
        this.entryOffset += skip;
        return skip;
    }

    @Override // java.io.InputStream
    public synchronized void reset() {
    }

    public TarArchiveEntry getNextTarEntry() throws IOException {
        if (this.hasHitEOF) {
            return null;
        }
        if (this.currEntry != null) {
            IOUtils.skip(this, Util.VLI_MAX);
            skipRecordPadding();
        }
        byte[] record = getRecord();
        if (record == null) {
            this.currEntry = null;
            return null;
        }
        try {
            TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(record, this.zipEncoding);
            this.currEntry = tarArchiveEntry;
            this.entryOffset = 0L;
            this.entrySize = tarArchiveEntry.getSize();
            if (this.currEntry.isGNULongLinkEntry()) {
                byte[] longNameData = getLongNameData();
                if (longNameData == null) {
                    return null;
                }
                this.currEntry.setLinkName(this.zipEncoding.decode(longNameData));
            }
            if (this.currEntry.isGNULongNameEntry()) {
                byte[] longNameData2 = getLongNameData();
                if (longNameData2 == null) {
                    return null;
                }
                this.currEntry.setName(this.zipEncoding.decode(longNameData2));
            }
            if (this.currEntry.isGlobalPaxHeader()) {
                readGlobalPaxHeaders();
            }
            if (this.currEntry.isPaxHeader()) {
                paxHeaders();
            } else if (!this.globalPaxHeaders.isEmpty()) {
                applyPaxHeadersToCurrentEntry(this.globalPaxHeaders);
            }
            if (this.currEntry.isOldGNUSparse()) {
                readOldGNUSparse();
            }
            this.entrySize = this.currEntry.getSize();
            return this.currEntry;
        } catch (IllegalArgumentException e) {
            throw new IOException("Error detected parsing the header", e);
        }
    }

    private void skipRecordPadding() throws IOException {
        if (isDirectory()) {
            return;
        }
        long j = this.entrySize;
        if (j > 0) {
            int i = this.recordSize;
            if (j % i != 0) {
                count(IOUtils.skip(this.is, (((j / i) + 1) * i) - j));
            }
        }
    }

    protected byte[] getLongNameData() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int read = read(this.SMALL_BUF);
            if (read < 0) {
                break;
            }
            byteArrayOutputStream.write(this.SMALL_BUF, 0, read);
        }
        getNextEntry();
        if (this.currEntry == null) {
            return null;
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        int length = byteArray.length;
        while (length > 0 && byteArray[length - 1] == 0) {
            length--;
        }
        if (length != byteArray.length) {
            byte[] bArr = new byte[length];
            System.arraycopy(byteArray, 0, bArr, 0, length);
            return bArr;
        }
        return byteArray;
    }

    private byte[] getRecord() throws IOException {
        byte[] readRecord = readRecord();
        boolean isEOFRecord = isEOFRecord(readRecord);
        this.hasHitEOF = isEOFRecord;
        if (!isEOFRecord || readRecord == null) {
            return readRecord;
        }
        tryToConsumeSecondEOFRecord();
        consumeRemainderOfLastBlock();
        return null;
    }

    protected boolean isEOFRecord(byte[] bArr) {
        return bArr == null || ArchiveUtils.isArrayZero(bArr, this.recordSize);
    }

    protected byte[] readRecord() throws IOException {
        byte[] bArr = new byte[this.recordSize];
        int readFully = IOUtils.readFully(this.is, bArr);
        count(readFully);
        if (readFully != this.recordSize) {
            return null;
        }
        return bArr;
    }

    private void readGlobalPaxHeaders() throws IOException {
        this.globalPaxHeaders = parsePaxHeaders(this);
        getNextEntry();
    }

    private void paxHeaders() throws IOException {
        Map<String, String> parsePaxHeaders = parsePaxHeaders(this);
        getNextEntry();
        applyPaxHeadersToCurrentEntry(parsePaxHeaders);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0074, code lost:
        r4 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x007c, code lost:
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007c, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    java.util.Map<java.lang.String, java.lang.String> parsePaxHeaders(java.io.InputStream r10) throws java.io.IOException {
        /*
            r9 = this;
            java.util.HashMap r0 = new java.util.HashMap
            java.util.Map<java.lang.String, java.lang.String> r1 = r9.globalPaxHeaders
            r0.<init>(r1)
        L7:
            r1 = 0
            r2 = r1
            r3 = r2
        La:
            int r4 = r10.read()
            r5 = -1
            if (r4 == r5) goto L7c
            r6 = 1
            int r2 = r2 + r6
            r7 = 10
            if (r4 != r7) goto L18
            goto L7c
        L18:
            r7 = 32
            if (r4 != r7) goto L76
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream
            r4.<init>()
        L21:
            int r7 = r10.read()
            if (r7 == r5) goto L74
            int r2 = r2 + r6
            r8 = 61
            if (r7 != r8) goto L6f
            java.lang.String r8 = "UTF-8"
            java.lang.String r4 = r4.toString(r8)
            int r3 = r3 - r2
            if (r3 != r6) goto L39
            r0.remove(r4)
            goto L74
        L39:
            byte[] r2 = new byte[r3]
            int r6 = org.apache.commons.compress.utils.IOUtils.readFully(r10, r2)
            if (r6 != r3) goto L4c
            java.lang.String r6 = new java.lang.String
            int r3 = r3 + (-1)
            r6.<init>(r2, r1, r3, r8)
            r0.put(r4, r6)
            goto L74
        L4c:
            java.io.IOException r10 = new java.io.IOException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Failed to read Paxheader. Expected "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r1 = " bytes, read "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r6)
            java.lang.String r0 = r0.toString()
            r10.<init>(r0)
            throw r10
        L6f:
            byte r7 = (byte) r7
            r4.write(r7)
            goto L21
        L74:
            r4 = r7
            goto L7c
        L76:
            int r3 = r3 * 10
            int r4 = r4 + (-48)
            int r3 = r3 + r4
            goto La
        L7c:
            if (r4 != r5) goto L7
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.archivers.tar.TarArchiveInputStream.parsePaxHeaders(java.io.InputStream):java.util.Map");
    }

    private void applyPaxHeadersToCurrentEntry(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (ClientConstants.ALIAS.PATH.equals(key)) {
                this.currEntry.setName(value);
            } else if ("linkpath".equals(key)) {
                this.currEntry.setLinkName(value);
            } else if ("gid".equals(key)) {
                this.currEntry.setGroupId(Long.parseLong(value));
            } else if ("gname".equals(key)) {
                this.currEntry.setGroupName(value);
            } else if ("uid".equals(key)) {
                this.currEntry.setUserId(Long.parseLong(value));
            } else if ("uname".equals(key)) {
                this.currEntry.setUserName(value);
            } else if ("size".equals(key)) {
                this.currEntry.setSize(Long.parseLong(value));
            } else if ("mtime".equals(key)) {
                this.currEntry.setModTime((long) (Double.parseDouble(value) * 1000.0d));
            } else if ("SCHILY.devminor".equals(key)) {
                this.currEntry.setDevMinor(Integer.parseInt(value));
            } else if ("SCHILY.devmajor".equals(key)) {
                this.currEntry.setDevMajor(Integer.parseInt(value));
            } else if ("GNU.sparse.size".equals(key)) {
                this.currEntry.fillGNUSparse0xData(map);
            } else if ("GNU.sparse.realsize".equals(key)) {
                this.currEntry.fillGNUSparse1xData(map);
            } else if ("SCHILY.filetype".equals(key) && "sparse".equals(value)) {
                this.currEntry.fillStarSparseData(map);
            }
        }
    }

    private void readOldGNUSparse() throws IOException {
        byte[] record;
        if (this.currEntry.isExtended()) {
            do {
                record = getRecord();
                if (record == null) {
                    this.currEntry = null;
                    return;
                }
            } while (new TarArchiveSparseEntry(record).isExtended());
        }
    }

    private boolean isDirectory() {
        TarArchiveEntry tarArchiveEntry = this.currEntry;
        return tarArchiveEntry != null && tarArchiveEntry.isDirectory();
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public ArchiveEntry getNextEntry() throws IOException {
        return getNextTarEntry();
    }

    private void tryToConsumeSecondEOFRecord() throws IOException {
        boolean markSupported = this.is.markSupported();
        if (markSupported) {
            this.is.mark(this.recordSize);
        }
        try {
            if ((!isEOFRecord(readRecord())) && markSupported) {
            }
        } finally {
            if (markSupported) {
                pushedBackBytes(this.recordSize);
                this.is.reset();
            }
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.hasHitEOF || isDirectory() || this.entryOffset >= this.entrySize) {
            return -1;
        }
        if (this.currEntry == null) {
            throw new IllegalStateException("No current tar entry");
        }
        int min = Math.min(i2, available());
        int read = this.is.read(bArr, i, min);
        if (read != -1) {
            count(read);
            this.entryOffset += read;
        } else if (min > 0) {
            throw new IOException("Truncated TAR archive");
        } else {
            this.hasHitEOF = true;
        }
        return read;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public boolean canReadEntryData(ArchiveEntry archiveEntry) {
        if (archiveEntry instanceof TarArchiveEntry) {
            return !((TarArchiveEntry) archiveEntry).isSparse();
        }
        return false;
    }

    public TarArchiveEntry getCurrentEntry() {
        return this.currEntry;
    }

    protected final void setCurrentEntry(TarArchiveEntry tarArchiveEntry) {
        this.currEntry = tarArchiveEntry;
    }

    protected final boolean isAtEOF() {
        return this.hasHitEOF;
    }

    protected final void setAtEOF(boolean z) {
        this.hasHitEOF = z;
    }

    private void consumeRemainderOfLastBlock() throws IOException {
        long bytesRead = getBytesRead();
        int i = this.blockSize;
        long j = bytesRead % i;
        if (j > 0) {
            count(IOUtils.skip(this.is, i - j));
        }
    }

    public static boolean matches(byte[] bArr, int i) {
        if (i < 265) {
            return false;
        }
        if (ArchiveUtils.matchAsciiBuffer("ustar\u0000", bArr, 257, 6) && ArchiveUtils.matchAsciiBuffer(TarConstants.VERSION_POSIX, bArr, TarConstants.VERSION_OFFSET, 2)) {
            return true;
        }
        if (ArchiveUtils.matchAsciiBuffer(TarConstants.MAGIC_GNU, bArr, 257, 6) && (ArchiveUtils.matchAsciiBuffer(TarConstants.VERSION_GNU_SPACE, bArr, TarConstants.VERSION_OFFSET, 2) || ArchiveUtils.matchAsciiBuffer(TarConstants.VERSION_GNU_ZERO, bArr, TarConstants.VERSION_OFFSET, 2))) {
            return true;
        }
        return ArchiveUtils.matchAsciiBuffer("ustar\u0000", bArr, 257, 6) && ArchiveUtils.matchAsciiBuffer(TarConstants.VERSION_ANT, bArr, TarConstants.VERSION_OFFSET, 2);
    }
}
