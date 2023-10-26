package org.apache.commons.compress.archivers.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.UnsupportedZipFeatureException;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.ArchiveUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.tukaani.xz.common.Util;

/* loaded from: classes3.dex */
public class ZipArchiveInputStream extends ArchiveInputStream {
    private static final int CFH_LEN = 46;
    private static final int LFH_LEN = 30;
    private static final long TWO_EXP_32 = 4294967296L;
    private final byte[] LFH_BUF;
    private final byte[] SHORT_BUF;
    private final byte[] SKIP_BUF;
    private final byte[] TWO_DWORD_BUF;
    private final byte[] WORD_BUF;
    private boolean allowStoredEntriesWithDataDescriptor;
    private final ByteBuffer buf;
    private boolean closed;
    private CurrentEntry current;
    final String encoding;
    private int entriesRead;
    private boolean hitCentralDirectory;
    private final InputStream in;
    private final Inflater inf;
    private ByteArrayInputStream lastStoredEntry;
    private final boolean useUnicodeExtraFields;
    private final ZipEncoding zipEncoding;
    private static final byte[] LFH = ZipLong.LFH_SIG.getBytes();
    private static final byte[] CFH = ZipLong.CFH_SIG.getBytes();
    private static final byte[] DD = ZipLong.DD_SIG.getBytes();

    public ZipArchiveInputStream(InputStream inputStream) {
        this(inputStream, "UTF8");
    }

    public ZipArchiveInputStream(InputStream inputStream, String str) {
        this(inputStream, str, true);
    }

    public ZipArchiveInputStream(InputStream inputStream, String str, boolean z) {
        this(inputStream, str, z, false);
    }

    public ZipArchiveInputStream(InputStream inputStream, String str, boolean z, boolean z2) {
        this.inf = new Inflater(true);
        ByteBuffer allocate = ByteBuffer.allocate(512);
        this.buf = allocate;
        this.current = null;
        this.closed = false;
        this.hitCentralDirectory = false;
        this.lastStoredEntry = null;
        this.allowStoredEntriesWithDataDescriptor = false;
        this.LFH_BUF = new byte[30];
        this.SKIP_BUF = new byte[1024];
        this.SHORT_BUF = new byte[2];
        this.WORD_BUF = new byte[4];
        this.TWO_DWORD_BUF = new byte[16];
        this.entriesRead = 0;
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
        this.useUnicodeExtraFields = z;
        this.in = new PushbackInputStream(inputStream, allocate.capacity());
        this.allowStoredEntriesWithDataDescriptor = z2;
        allocate.limit(0);
    }

    public ZipArchiveEntry getNextZipEntry() throws IOException {
        boolean z;
        ZipLong zipLong;
        ZipLong zipLong2;
        if (!this.closed && !this.hitCentralDirectory) {
            if (this.current != null) {
                closeEntry();
                z = false;
            } else {
                z = true;
            }
            try {
                if (z) {
                    readFirstLocalFileHeader(this.LFH_BUF);
                } else {
                    readFully(this.LFH_BUF);
                }
                ZipLong zipLong3 = new ZipLong(this.LFH_BUF);
                if (zipLong3.equals(ZipLong.CFH_SIG) || zipLong3.equals(ZipLong.AED_SIG)) {
                    this.hitCentralDirectory = true;
                    skipRemainderOfArchive();
                }
                if (zipLong3.equals(ZipLong.LFH_SIG)) {
                    this.current = new CurrentEntry();
                    this.current.entry.setPlatform((ZipShort.getValue(this.LFH_BUF, 4) >> 8) & 15);
                    GeneralPurposeBit parse = GeneralPurposeBit.parse(this.LFH_BUF, 6);
                    boolean usesUTF8ForNames = parse.usesUTF8ForNames();
                    ZipEncoding zipEncoding = usesUTF8ForNames ? ZipEncodingHelper.UTF8_ZIP_ENCODING : this.zipEncoding;
                    this.current.hasDataDescriptor = parse.usesDataDescriptor();
                    this.current.entry.setGeneralPurposeBit(parse);
                    this.current.entry.setMethod(ZipShort.getValue(this.LFH_BUF, 8));
                    this.current.entry.setTime(ZipUtil.dosToJavaTime(ZipLong.getValue(this.LFH_BUF, 10)));
                    if (this.current.hasDataDescriptor) {
                        zipLong = null;
                        zipLong2 = null;
                    } else {
                        this.current.entry.setCrc(ZipLong.getValue(this.LFH_BUF, 14));
                        zipLong = new ZipLong(this.LFH_BUF, 18);
                        zipLong2 = new ZipLong(this.LFH_BUF, 22);
                    }
                    int value = ZipShort.getValue(this.LFH_BUF, 26);
                    int value2 = ZipShort.getValue(this.LFH_BUF, 28);
                    byte[] bArr = new byte[value];
                    readFully(bArr);
                    this.current.entry.setName(zipEncoding.decode(bArr), bArr);
                    byte[] bArr2 = new byte[value2];
                    readFully(bArr2);
                    this.current.entry.setExtra(bArr2);
                    if (!usesUTF8ForNames && this.useUnicodeExtraFields) {
                        ZipUtil.setNameAndCommentFromExtraFields(this.current.entry, bArr, null);
                    }
                    processZip64Extra(zipLong2, zipLong);
                    if (this.current.entry.getCompressedSize() != -1) {
                        if (this.current.entry.getMethod() == ZipMethod.UNSHRINKING.getCode()) {
                            this.current.in = new UnshrinkingInputStream(new BoundedInputStream(this.in, this.current.entry.getCompressedSize()));
                        } else if (this.current.entry.getMethod() == ZipMethod.IMPLODING.getCode()) {
                            this.current.in = new ExplodingInputStream(this.current.entry.getGeneralPurposeBit().getSlidingDictionarySize(), this.current.entry.getGeneralPurposeBit().getNumberOfShannonFanoTrees(), new BoundedInputStream(this.in, this.current.entry.getCompressedSize()));
                        } else if (this.current.entry.getMethod() == ZipMethod.BZIP2.getCode()) {
                            this.current.in = new BZip2CompressorInputStream(new BoundedInputStream(this.in, this.current.entry.getCompressedSize()));
                        }
                    }
                    this.entriesRead++;
                    return this.current.entry;
                }
                return null;
            } catch (EOFException unused) {
            }
        }
        return null;
    }

    private void readFirstLocalFileHeader(byte[] bArr) throws IOException {
        readFully(bArr);
        ZipLong zipLong = new ZipLong(bArr);
        if (zipLong.equals(ZipLong.DD_SIG)) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.SPLITTING);
        }
        if (zipLong.equals(ZipLong.SINGLE_SEGMENT_SPLIT_MARKER)) {
            byte[] bArr2 = new byte[4];
            readFully(bArr2);
            System.arraycopy(bArr, 4, bArr, 0, 26);
            System.arraycopy(bArr2, 0, bArr, 26, 4);
        }
    }

    private void processZip64Extra(ZipLong zipLong, ZipLong zipLong2) {
        Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = (Zip64ExtendedInformationExtraField) this.current.entry.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        this.current.usesZip64 = zip64ExtendedInformationExtraField != null;
        if (this.current.hasDataDescriptor) {
            return;
        }
        if (zip64ExtendedInformationExtraField == null || (!zipLong2.equals(ZipLong.ZIP64_MAGIC) && !zipLong.equals(ZipLong.ZIP64_MAGIC))) {
            this.current.entry.setCompressedSize(zipLong2.getValue());
            this.current.entry.setSize(zipLong.getValue());
            return;
        }
        this.current.entry.setCompressedSize(zip64ExtendedInformationExtraField.getCompressedSize().getLongValue());
        this.current.entry.setSize(zip64ExtendedInformationExtraField.getSize().getLongValue());
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public ArchiveEntry getNextEntry() throws IOException {
        return getNextZipEntry();
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public boolean canReadEntryData(ArchiveEntry archiveEntry) {
        if (archiveEntry instanceof ZipArchiveEntry) {
            ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) archiveEntry;
            return ZipUtil.canHandleEntryData(zipArchiveEntry) && supportsDataDescriptorFor(zipArchiveEntry);
        }
        return false;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read;
        if (this.closed) {
            throw new IOException("The stream is closed");
        }
        CurrentEntry currentEntry = this.current;
        if (currentEntry == null) {
            return -1;
        }
        if (i > bArr.length || i2 < 0 || i < 0 || bArr.length - i < i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        ZipUtil.checkRequestedFeatures(currentEntry.entry);
        if (!supportsDataDescriptorFor(this.current.entry)) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.DATA_DESCRIPTOR, this.current.entry);
        }
        if (this.current.entry.getMethod() == 0) {
            read = readStored(bArr, i, i2);
        } else if (this.current.entry.getMethod() == 8) {
            read = readDeflated(bArr, i, i2);
        } else if (this.current.entry.getMethod() != ZipMethod.UNSHRINKING.getCode() && this.current.entry.getMethod() != ZipMethod.IMPLODING.getCode() && this.current.entry.getMethod() != ZipMethod.BZIP2.getCode()) {
            throw new UnsupportedZipFeatureException(ZipMethod.getMethodByCode(this.current.entry.getMethod()), this.current.entry);
        } else {
            read = this.current.in.read(bArr, i, i2);
        }
        if (read >= 0) {
            this.current.crc.update(bArr, i, read);
        }
        return read;
    }

    private int readStored(byte[] bArr, int i, int i2) throws IOException {
        if (this.current.hasDataDescriptor) {
            if (this.lastStoredEntry == null) {
                readStoredEntry();
            }
            return this.lastStoredEntry.read(bArr, i, i2);
        }
        long size = this.current.entry.getSize();
        if (this.current.bytesRead >= size) {
            return -1;
        }
        if (this.buf.position() >= this.buf.limit()) {
            this.buf.position(0);
            int read = this.in.read(this.buf.array());
            if (read == -1) {
                return -1;
            }
            this.buf.limit(read);
            count(read);
            CurrentEntry.access$714(this.current, read);
        }
        int min = Math.min(this.buf.remaining(), i2);
        if (size - this.current.bytesRead < min) {
            min = (int) (size - this.current.bytesRead);
        }
        this.buf.get(bArr, i, min);
        CurrentEntry.access$614(this.current, min);
        return min;
    }

    private int readDeflated(byte[] bArr, int i, int i2) throws IOException {
        int readFromInflater = readFromInflater(bArr, i, i2);
        if (readFromInflater <= 0) {
            if (this.inf.finished()) {
                return -1;
            }
            if (this.inf.needsDictionary()) {
                throw new ZipException("This archive needs a preset dictionary which is not supported by Commons Compress.");
            }
            if (readFromInflater == -1) {
                throw new IOException("Truncated ZIP file");
            }
        }
        return readFromInflater;
    }

    private int readFromInflater(byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        while (true) {
            if (this.inf.needsInput()) {
                int fill = fill();
                if (fill > 0) {
                    CurrentEntry.access$714(this.current, this.buf.limit());
                } else if (fill == -1) {
                    return -1;
                }
            }
            try {
                i3 = this.inf.inflate(bArr, i, i2);
                if (i3 == 0) {
                    if (!this.inf.needsInput()) {
                        break;
                    }
                } else {
                    break;
                }
            } catch (DataFormatException e) {
                throw ((IOException) new ZipException(e.getMessage()).initCause(e));
            }
        }
        return i3;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        try {
            this.in.close();
        } finally {
            this.inf.end();
        }
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        long j2 = 0;
        if (j >= 0) {
            while (j2 < j) {
                long j3 = j - j2;
                byte[] bArr = this.SKIP_BUF;
                if (bArr.length <= j3) {
                    j3 = bArr.length;
                }
                int read = read(bArr, 0, (int) j3);
                if (read == -1) {
                    return j2;
                }
                j2 += read;
            }
            return j2;
        }
        throw new IllegalArgumentException();
    }

    public static boolean matches(byte[] bArr, int i) {
        if (i < ZipArchiveOutputStream.LFH_SIG.length) {
            return false;
        }
        return checksig(bArr, ZipArchiveOutputStream.LFH_SIG) || checksig(bArr, ZipArchiveOutputStream.EOCD_SIG) || checksig(bArr, ZipArchiveOutputStream.DD_SIG) || checksig(bArr, ZipLong.SINGLE_SEGMENT_SPLIT_MARKER.getBytes());
    }

    private static boolean checksig(byte[] bArr, byte[] bArr2) {
        for (int i = 0; i < bArr2.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    private void closeEntry() throws IOException {
        if (this.closed) {
            throw new IOException("The stream is closed");
        }
        CurrentEntry currentEntry = this.current;
        if (currentEntry == null) {
            return;
        }
        if (currentEntry.bytesReadFromStream <= this.current.entry.getCompressedSize() && !this.current.hasDataDescriptor) {
            drainCurrentEntryData();
        } else {
            skip(Util.VLI_MAX);
            int bytesInflated = (int) (this.current.bytesReadFromStream - (this.current.entry.getMethod() == 8 ? getBytesInflated() : this.current.bytesRead));
            if (bytesInflated > 0) {
                pushback(this.buf.array(), this.buf.limit() - bytesInflated, bytesInflated);
            }
        }
        if (this.lastStoredEntry == null && this.current.hasDataDescriptor) {
            readDataDescriptor();
        }
        this.inf.reset();
        this.buf.clear().flip();
        this.current = null;
        this.lastStoredEntry = null;
    }

    private void drainCurrentEntryData() throws IOException {
        long compressedSize = this.current.entry.getCompressedSize() - this.current.bytesReadFromStream;
        while (compressedSize > 0) {
            long read = this.in.read(this.buf.array(), 0, (int) Math.min(this.buf.capacity(), compressedSize));
            if (read < 0) {
                throw new EOFException("Truncated ZIP entry: " + ArchiveUtils.sanitize(this.current.entry.getName()));
            }
            count(read);
            compressedSize -= read;
        }
    }

    private long getBytesInflated() {
        long bytesRead = this.inf.getBytesRead();
        if (this.current.bytesReadFromStream >= TWO_EXP_32) {
            while (true) {
                long j = bytesRead + TWO_EXP_32;
                if (j > this.current.bytesReadFromStream) {
                    break;
                }
                bytesRead = j;
            }
        }
        return bytesRead;
    }

    private int fill() throws IOException {
        if (this.closed) {
            throw new IOException("The stream is closed");
        }
        int read = this.in.read(this.buf.array());
        if (read > 0) {
            this.buf.limit(read);
            count(this.buf.limit());
            this.inf.setInput(this.buf.array(), 0, this.buf.limit());
        }
        return read;
    }

    private void readFully(byte[] bArr) throws IOException {
        int readFully = IOUtils.readFully(this.in, bArr);
        count(readFully);
        if (readFully < bArr.length) {
            throw new EOFException();
        }
    }

    private void readDataDescriptor() throws IOException {
        readFully(this.WORD_BUF);
        ZipLong zipLong = new ZipLong(this.WORD_BUF);
        if (ZipLong.DD_SIG.equals(zipLong)) {
            readFully(this.WORD_BUF);
            zipLong = new ZipLong(this.WORD_BUF);
        }
        this.current.entry.setCrc(zipLong.getValue());
        readFully(this.TWO_DWORD_BUF);
        ZipLong zipLong2 = new ZipLong(this.TWO_DWORD_BUF, 8);
        if (zipLong2.equals(ZipLong.CFH_SIG) || zipLong2.equals(ZipLong.LFH_SIG)) {
            pushback(this.TWO_DWORD_BUF, 8, 8);
            this.current.entry.setCompressedSize(ZipLong.getValue(this.TWO_DWORD_BUF));
            this.current.entry.setSize(ZipLong.getValue(this.TWO_DWORD_BUF, 4));
            return;
        }
        this.current.entry.setCompressedSize(ZipEightByteInteger.getLongValue(this.TWO_DWORD_BUF));
        this.current.entry.setSize(ZipEightByteInteger.getLongValue(this.TWO_DWORD_BUF, 8));
    }

    private boolean supportsDataDescriptorFor(ZipArchiveEntry zipArchiveEntry) {
        return !zipArchiveEntry.getGeneralPurposeBit().usesDataDescriptor() || (this.allowStoredEntriesWithDataDescriptor && zipArchiveEntry.getMethod() == 0) || zipArchiveEntry.getMethod() == 8;
    }

    private void readStoredEntry() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = this.current.usesZip64 ? 20 : 12;
        boolean z = false;
        int i2 = 0;
        while (!z) {
            int read = this.in.read(this.buf.array(), i2, 512 - i2);
            if (read <= 0) {
                throw new IOException("Truncated ZIP file");
            }
            int i3 = read + i2;
            if (i3 < 4) {
                i2 = i3;
            } else {
                z = bufferContainsSignature(byteArrayOutputStream, i2, read, i);
                if (!z) {
                    i2 = cacheBytesRead(byteArrayOutputStream, i2, read, i);
                }
            }
        }
        this.lastStoredEntry = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a2 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean bufferContainsSignature(java.io.ByteArrayOutputStream r12, int r13, int r14, int r15) throws java.io.IOException {
        /*
            r11 = this;
            r0 = 0
            r1 = r0
            r2 = r1
            r3 = r2
        L4:
            if (r1 != 0) goto La6
            int r4 = r14 + (-4)
            if (r2 >= r4) goto La6
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            r4 = r4[r2]
            byte[] r5 = org.apache.commons.compress.archivers.zip.ZipArchiveInputStream.LFH
            r6 = r5[r0]
            if (r4 != r6) goto La2
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            int r6 = r2 + 1
            r4 = r4[r6]
            r6 = 1
            r7 = r5[r6]
            if (r4 != r7) goto La2
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            int r7 = r2 + 2
            r4 = r4[r7]
            r8 = 2
            r9 = r5[r8]
            r10 = 3
            if (r4 != r9) goto L45
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            int r9 = r2 + 3
            r4 = r4[r9]
            r5 = r5[r10]
            if (r4 == r5) goto L61
        L45:
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            r4 = r4[r2]
            byte[] r5 = org.apache.commons.compress.archivers.zip.ZipArchiveInputStream.CFH
            r9 = r5[r8]
            if (r4 != r9) goto L68
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            int r9 = r2 + 3
            r4 = r4[r9]
            r5 = r5[r10]
            if (r4 != r5) goto L68
        L61:
            int r1 = r13 + r14
            int r1 = r1 - r2
            int r1 = r1 - r15
        L65:
            r3 = r1
            r1 = r6
            goto L88
        L68:
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            r4 = r4[r7]
            byte[] r5 = org.apache.commons.compress.archivers.zip.ZipArchiveInputStream.DD
            r7 = r5[r8]
            if (r4 != r7) goto L88
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            int r7 = r2 + 3
            r4 = r4[r7]
            r5 = r5[r10]
            if (r4 != r5) goto L88
            int r1 = r13 + r14
            int r1 = r1 - r2
            goto L65
        L88:
            if (r1 == 0) goto La2
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            int r5 = r13 + r14
            int r5 = r5 - r3
            r11.pushback(r4, r5, r3)
            java.nio.ByteBuffer r4 = r11.buf
            byte[] r4 = r4.array()
            r12.write(r4, r0, r2)
            r11.readDataDescriptor()
        La2:
            int r2 = r2 + 1
            goto L4
        La6:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.archivers.zip.ZipArchiveInputStream.bufferContainsSignature(java.io.ByteArrayOutputStream, int, int, int):boolean");
    }

    private int cacheBytesRead(ByteArrayOutputStream byteArrayOutputStream, int i, int i2, int i3) {
        int i4 = i + i2;
        int i5 = (i4 - i3) - 3;
        if (i5 > 0) {
            byteArrayOutputStream.write(this.buf.array(), 0, i5);
            int i6 = i3 + 3;
            System.arraycopy(this.buf.array(), i5, this.buf.array(), 0, i6);
            return i6;
        }
        return i4;
    }

    private void pushback(byte[] bArr, int i, int i2) throws IOException {
        ((PushbackInputStream) this.in).unread(bArr, i, i2);
        pushedBackBytes(i2);
    }

    private void skipRemainderOfArchive() throws IOException {
        realSkip((this.entriesRead * 46) - 30);
        findEocdRecord();
        realSkip(16L);
        readFully(this.SHORT_BUF);
        realSkip(ZipShort.getValue(this.SHORT_BUF));
    }

    private void findEocdRecord() throws IOException {
        boolean z = false;
        int i = -1;
        while (true) {
            if (!z) {
                i = readOneByte();
                if (i <= -1) {
                    return;
                }
            }
            if (isFirstByteOfEocdSig(i)) {
                i = readOneByte();
                if (i == ZipArchiveOutputStream.EOCD_SIG[1]) {
                    i = readOneByte();
                    if (i == ZipArchiveOutputStream.EOCD_SIG[2]) {
                        i = readOneByte();
                        if (i == -1 || i == ZipArchiveOutputStream.EOCD_SIG[3]) {
                            return;
                        }
                        z = isFirstByteOfEocdSig(i);
                    } else if (i == -1) {
                        return;
                    } else {
                        z = isFirstByteOfEocdSig(i);
                    }
                } else if (i == -1) {
                    return;
                } else {
                    z = isFirstByteOfEocdSig(i);
                }
            } else {
                z = false;
            }
        }
    }

    private void realSkip(long j) throws IOException {
        long j2 = 0;
        if (j < 0) {
            throw new IllegalArgumentException();
        }
        while (j2 < j) {
            long j3 = j - j2;
            InputStream inputStream = this.in;
            byte[] bArr = this.SKIP_BUF;
            if (bArr.length <= j3) {
                j3 = bArr.length;
            }
            int read = inputStream.read(bArr, 0, (int) j3);
            if (read == -1) {
                return;
            }
            count(read);
            j2 += read;
        }
    }

    private int readOneByte() throws IOException {
        int read = this.in.read();
        if (read != -1) {
            count(1);
        }
        return read;
    }

    private boolean isFirstByteOfEocdSig(int i) {
        return i == ZipArchiveOutputStream.EOCD_SIG[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class CurrentEntry {
        private long bytesRead;
        private long bytesReadFromStream;
        private final CRC32 crc;
        private final ZipArchiveEntry entry;
        private boolean hasDataDescriptor;
        private InputStream in;
        private boolean usesZip64;

        private CurrentEntry() {
            this.entry = new ZipArchiveEntry();
            this.crc = new CRC32();
        }

        static /* synthetic */ long access$614(CurrentEntry currentEntry, long j) {
            long j2 = currentEntry.bytesRead + j;
            currentEntry.bytesRead = j2;
            return j2;
        }

        static /* synthetic */ long access$708(CurrentEntry currentEntry) {
            long j = currentEntry.bytesReadFromStream;
            currentEntry.bytesReadFromStream = 1 + j;
            return j;
        }

        static /* synthetic */ long access$714(CurrentEntry currentEntry, long j) {
            long j2 = currentEntry.bytesReadFromStream + j;
            currentEntry.bytesReadFromStream = j2;
            return j2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class BoundedInputStream extends InputStream {
        private final InputStream in;
        private final long max;
        private long pos = 0;

        public BoundedInputStream(InputStream inputStream, long j) {
            this.max = j;
            this.in = inputStream;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            long j = this.max;
            if (j < 0 || this.pos < j) {
                int read = this.in.read();
                this.pos++;
                ZipArchiveInputStream.this.count(1);
                CurrentEntry.access$708(ZipArchiveInputStream.this.current);
                return read;
            }
            return -1;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr) throws IOException {
            return read(bArr, 0, bArr.length);
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            long j = this.max;
            if (j < 0 || this.pos < j) {
                int read = this.in.read(bArr, i, (int) (j >= 0 ? Math.min(i2, j - this.pos) : i2));
                if (read == -1) {
                    return -1;
                }
                long j2 = read;
                this.pos += j2;
                ZipArchiveInputStream.this.count(read);
                CurrentEntry.access$714(ZipArchiveInputStream.this.current, j2);
                return read;
            }
            return -1;
        }

        @Override // java.io.InputStream
        public long skip(long j) throws IOException {
            long j2 = this.max;
            if (j2 >= 0) {
                j = Math.min(j, j2 - this.pos);
            }
            long skip = this.in.skip(j);
            this.pos += skip;
            return skip;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            long j = this.max;
            if (j < 0 || this.pos < j) {
                return this.in.available();
            }
            return 0;
        }
    }
}
