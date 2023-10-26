package org.apache.commons.compress.archivers.zip;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes3.dex */
public class ZipFile implements Closeable {
    static final int BYTE_SHIFT = 8;
    private static final int CFD_LOCATOR_OFFSET = 16;
    private static final int CFH_LEN = 42;
    private static final long CFH_SIG = ZipLong.getValue(ZipArchiveOutputStream.CFH_SIG);
    private static final int HASH_SIZE = 509;
    private static final long LFH_OFFSET_FOR_FILENAME_LENGTH = 26;
    private static final int MAX_EOCD_SIZE = 65557;
    static final int MIN_EOCD_SIZE = 22;
    static final int NIBLET_MASK = 15;
    private static final int POS_0 = 0;
    private static final int POS_1 = 1;
    private static final int POS_2 = 2;
    private static final int POS_3 = 3;
    private static final int ZIP64_EOCDL_LENGTH = 20;
    private static final int ZIP64_EOCDL_LOCATOR_OFFSET = 8;
    private static final int ZIP64_EOCD_CFD_LOCATOR_OFFSET = 48;
    private final byte[] CFH_BUF;
    private final byte[] DWORD_BUF;
    private final Comparator<ZipArchiveEntry> OFFSET_COMPARATOR;
    private final byte[] SHORT_BUF;
    private final byte[] WORD_BUF;
    private final RandomAccessFile archive;
    private final String archiveName;
    private volatile boolean closed;
    private final String encoding;
    private final List<ZipArchiveEntry> entries;
    private final Map<String, LinkedList<ZipArchiveEntry>> nameMap;
    private final boolean useUnicodeExtraFields;
    private final ZipEncoding zipEncoding;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class OffsetEntry {
        private long dataOffset;
        private long headerOffset;

        private OffsetEntry() {
            this.headerOffset = -1L;
            this.dataOffset = -1L;
        }
    }

    public ZipFile(File file) throws IOException {
        this(file, "UTF8");
    }

    public ZipFile(String str) throws IOException {
        this(new File(str), "UTF8");
    }

    public ZipFile(String str, String str2) throws IOException {
        this(new File(str), str2, true);
    }

    public ZipFile(File file, String str) throws IOException {
        this(file, str, true);
    }

    public ZipFile(File file, String str, boolean z) throws IOException {
        this.entries = new LinkedList();
        this.nameMap = new HashMap(509);
        this.closed = true;
        this.DWORD_BUF = new byte[8];
        this.WORD_BUF = new byte[4];
        this.CFH_BUF = new byte[42];
        this.SHORT_BUF = new byte[2];
        this.OFFSET_COMPARATOR = new Comparator<ZipArchiveEntry>() { // from class: org.apache.commons.compress.archivers.zip.ZipFile.2
            @Override // java.util.Comparator
            public int compare(ZipArchiveEntry zipArchiveEntry, ZipArchiveEntry zipArchiveEntry2) {
                if (zipArchiveEntry == zipArchiveEntry2) {
                    return 0;
                }
                Entry entry = zipArchiveEntry instanceof Entry ? (Entry) zipArchiveEntry : null;
                Entry entry2 = zipArchiveEntry2 instanceof Entry ? (Entry) zipArchiveEntry2 : null;
                if (entry == null) {
                    return 1;
                }
                if (entry2 == null) {
                    return -1;
                }
                int i = ((entry.getOffsetEntry().headerOffset - entry2.getOffsetEntry().headerOffset) > 0L ? 1 : ((entry.getOffsetEntry().headerOffset - entry2.getOffsetEntry().headerOffset) == 0L ? 0 : -1));
                if (i == 0) {
                    return 0;
                }
                return i < 0 ? -1 : 1;
            }
        };
        this.archiveName = file.getAbsolutePath();
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
        this.useUnicodeExtraFields = z;
        this.archive = new RandomAccessFile(file, "r");
        try {
            resolveLocalFileHeaderData(populateFromCentralDirectory());
            this.closed = false;
        } catch (Throwable th) {
            this.closed = true;
            IOUtils.closeQuietly(this.archive);
            throw th;
        }
    }

    public String getEncoding() {
        return this.encoding;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.closed = true;
        this.archive.close();
    }

    public static void closeQuietly(ZipFile zipFile) {
        IOUtils.closeQuietly(zipFile);
    }

    public Enumeration<ZipArchiveEntry> getEntries() {
        return Collections.enumeration(this.entries);
    }

    public Enumeration<ZipArchiveEntry> getEntriesInPhysicalOrder() {
        List<ZipArchiveEntry> list = this.entries;
        ZipArchiveEntry[] zipArchiveEntryArr = (ZipArchiveEntry[]) list.toArray(new ZipArchiveEntry[list.size()]);
        Arrays.sort(zipArchiveEntryArr, this.OFFSET_COMPARATOR);
        return Collections.enumeration(Arrays.asList(zipArchiveEntryArr));
    }

    public ZipArchiveEntry getEntry(String str) {
        LinkedList<ZipArchiveEntry> linkedList = this.nameMap.get(str);
        if (linkedList != null) {
            return linkedList.getFirst();
        }
        return null;
    }

    public Iterable<ZipArchiveEntry> getEntries(String str) {
        LinkedList<ZipArchiveEntry> linkedList = this.nameMap.get(str);
        return linkedList != null ? linkedList : Collections.emptyList();
    }

    public Iterable<ZipArchiveEntry> getEntriesInPhysicalOrder(String str) {
        ZipArchiveEntry[] zipArchiveEntryArr = new ZipArchiveEntry[0];
        if (this.nameMap.containsKey(str)) {
            zipArchiveEntryArr = (ZipArchiveEntry[]) this.nameMap.get(str).toArray(zipArchiveEntryArr);
            Arrays.sort(zipArchiveEntryArr, this.OFFSET_COMPARATOR);
        }
        return Arrays.asList(zipArchiveEntryArr);
    }

    public boolean canReadEntryData(ZipArchiveEntry zipArchiveEntry) {
        return ZipUtil.canHandleEntryData(zipArchiveEntry);
    }

    public InputStream getRawInputStream(ZipArchiveEntry zipArchiveEntry) {
        if (zipArchiveEntry instanceof Entry) {
            return new BoundedInputStream(((Entry) zipArchiveEntry).getOffsetEntry().dataOffset, zipArchiveEntry.getCompressedSize());
        }
        return null;
    }

    public void copyRawEntries(ZipArchiveOutputStream zipArchiveOutputStream, ZipArchiveEntryPredicate zipArchiveEntryPredicate) throws IOException {
        Enumeration<ZipArchiveEntry> entriesInPhysicalOrder = getEntriesInPhysicalOrder();
        while (entriesInPhysicalOrder.hasMoreElements()) {
            ZipArchiveEntry nextElement = entriesInPhysicalOrder.nextElement();
            if (zipArchiveEntryPredicate.test(nextElement)) {
                zipArchiveOutputStream.addRawArchiveEntry(nextElement, getRawInputStream(nextElement));
            }
        }
    }

    public InputStream getInputStream(ZipArchiveEntry zipArchiveEntry) throws IOException, ZipException {
        if (zipArchiveEntry instanceof Entry) {
            OffsetEntry offsetEntry = ((Entry) zipArchiveEntry).getOffsetEntry();
            ZipUtil.checkRequestedFeatures(zipArchiveEntry);
            BoundedInputStream boundedInputStream = new BoundedInputStream(offsetEntry.dataOffset, zipArchiveEntry.getCompressedSize());
            int i = AnonymousClass3.$SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.getMethodByCode(zipArchiveEntry.getMethod()).ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            if (i == 5) {
                                return new BZip2CompressorInputStream(boundedInputStream);
                            }
                            throw new ZipException("Found unsupported compression method " + zipArchiveEntry.getMethod());
                        }
                        boundedInputStream.addDummy();
                        final Inflater inflater = new Inflater(true);
                        return new InflaterInputStream(boundedInputStream, inflater) { // from class: org.apache.commons.compress.archivers.zip.ZipFile.1
                            @Override // java.util.zip.InflaterInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                            public void close() throws IOException {
                                try {
                                    super.close();
                                } finally {
                                    inflater.end();
                                }
                            }
                        };
                    }
                    return new ExplodingInputStream(zipArchiveEntry.getGeneralPurposeBit().getSlidingDictionarySize(), zipArchiveEntry.getGeneralPurposeBit().getNumberOfShannonFanoTrees(), new BufferedInputStream(boundedInputStream));
                }
                return new UnshrinkingInputStream(boundedInputStream);
            }
            return boundedInputStream;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: org.apache.commons.compress.archivers.zip.ZipFile$3  reason: invalid class name */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod;

        static {
            int[] iArr = new int[ZipMethod.values().length];
            $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod = iArr;
            try {
                iArr[ZipMethod.STORED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.UNSHRINKING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.IMPLODING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.DEFLATED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.BZIP2.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.AES_ENCRYPTED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.ENHANCED_DEFLATED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.EXPANDING_LEVEL_1.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.EXPANDING_LEVEL_2.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.EXPANDING_LEVEL_3.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.EXPANDING_LEVEL_4.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.JPEG.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.LZMA.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.PKWARE_IMPLODING.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.PPMD.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.TOKENIZATION.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.UNKNOWN.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.WAVPACK.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    public String getUnixSymlink(ZipArchiveEntry zipArchiveEntry) throws IOException {
        InputStream inputStream = null;
        if (zipArchiveEntry == null || !zipArchiveEntry.isUnixSymlink()) {
            return null;
        }
        try {
            inputStream = getInputStream(zipArchiveEntry);
            return this.zipEncoding.decode(IOUtils.toByteArray(inputStream));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (!this.closed) {
                System.err.println("Cleaning up unclosed ZipFile for archive " + this.archiveName);
                close();
            }
        } finally {
            super.finalize();
        }
    }

    private Map<ZipArchiveEntry, NameAndComment> populateFromCentralDirectory() throws IOException {
        HashMap hashMap = new HashMap();
        positionAtCentralDirectory();
        this.archive.readFully(this.WORD_BUF);
        long value = ZipLong.getValue(this.WORD_BUF);
        if (value != CFH_SIG && startsWithLocalFileHeader()) {
            throw new IOException("central directory is empty, can't expand corrupt archive.");
        }
        while (value == CFH_SIG) {
            readCentralDirectoryEntry(hashMap);
            this.archive.readFully(this.WORD_BUF);
            value = ZipLong.getValue(this.WORD_BUF);
        }
        return hashMap;
    }

    private void readCentralDirectoryEntry(Map<ZipArchiveEntry, NameAndComment> map) throws IOException {
        this.archive.readFully(this.CFH_BUF);
        OffsetEntry offsetEntry = new OffsetEntry();
        Entry entry = new Entry(offsetEntry);
        int value = ZipShort.getValue(this.CFH_BUF, 0);
        entry.setVersionMadeBy(value);
        entry.setPlatform((value >> 8) & 15);
        entry.setVersionRequired(ZipShort.getValue(this.CFH_BUF, 2));
        GeneralPurposeBit parse = GeneralPurposeBit.parse(this.CFH_BUF, 4);
        boolean usesUTF8ForNames = parse.usesUTF8ForNames();
        ZipEncoding zipEncoding = usesUTF8ForNames ? ZipEncodingHelper.UTF8_ZIP_ENCODING : this.zipEncoding;
        entry.setGeneralPurposeBit(parse);
        entry.setRawFlag(ZipShort.getValue(this.CFH_BUF, 4));
        entry.setMethod(ZipShort.getValue(this.CFH_BUF, 6));
        entry.setTime(ZipUtil.dosToJavaTime(ZipLong.getValue(this.CFH_BUF, 8)));
        entry.setCrc(ZipLong.getValue(this.CFH_BUF, 12));
        entry.setCompressedSize(ZipLong.getValue(this.CFH_BUF, 16));
        entry.setSize(ZipLong.getValue(this.CFH_BUF, 20));
        int value2 = ZipShort.getValue(this.CFH_BUF, 24);
        int value3 = ZipShort.getValue(this.CFH_BUF, 26);
        int value4 = ZipShort.getValue(this.CFH_BUF, 28);
        int value5 = ZipShort.getValue(this.CFH_BUF, 30);
        entry.setInternalAttributes(ZipShort.getValue(this.CFH_BUF, 32));
        entry.setExternalAttributes(ZipLong.getValue(this.CFH_BUF, 34));
        byte[] bArr = new byte[value2];
        this.archive.readFully(bArr);
        entry.setName(zipEncoding.decode(bArr), bArr);
        offsetEntry.headerOffset = ZipLong.getValue(this.CFH_BUF, 38);
        this.entries.add(entry);
        byte[] bArr2 = new byte[value3];
        this.archive.readFully(bArr2);
        entry.setCentralDirectoryExtra(bArr2);
        setSizesAndOffsetFromZip64Extra(entry, offsetEntry, value5);
        byte[] bArr3 = new byte[value4];
        this.archive.readFully(bArr3);
        entry.setComment(zipEncoding.decode(bArr3));
        if (usesUTF8ForNames || !this.useUnicodeExtraFields) {
            return;
        }
        map.put(entry, new NameAndComment(bArr, bArr3));
    }

    private void setSizesAndOffsetFromZip64Extra(ZipArchiveEntry zipArchiveEntry, OffsetEntry offsetEntry, int i) throws IOException {
        Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = (Zip64ExtendedInformationExtraField) zipArchiveEntry.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        if (zip64ExtendedInformationExtraField != null) {
            boolean z = zipArchiveEntry.getSize() == 4294967295L;
            boolean z2 = zipArchiveEntry.getCompressedSize() == 4294967295L;
            boolean z3 = offsetEntry.headerOffset == 4294967295L;
            zip64ExtendedInformationExtraField.reparseCentralDirectoryData(z, z2, z3, i == 65535);
            if (z) {
                zipArchiveEntry.setSize(zip64ExtendedInformationExtraField.getSize().getLongValue());
            } else if (z2) {
                zip64ExtendedInformationExtraField.setSize(new ZipEightByteInteger(zipArchiveEntry.getSize()));
            }
            if (z2) {
                zipArchiveEntry.setCompressedSize(zip64ExtendedInformationExtraField.getCompressedSize().getLongValue());
            } else if (z) {
                zip64ExtendedInformationExtraField.setCompressedSize(new ZipEightByteInteger(zipArchiveEntry.getCompressedSize()));
            }
            if (z3) {
                offsetEntry.headerOffset = zip64ExtendedInformationExtraField.getRelativeHeaderOffset().getLongValue();
            }
        }
    }

    private void positionAtCentralDirectory() throws IOException {
        positionAtEndOfCentralDirectoryRecord();
        boolean z = false;
        boolean z2 = this.archive.getFilePointer() > 20;
        if (z2) {
            RandomAccessFile randomAccessFile = this.archive;
            randomAccessFile.seek(randomAccessFile.getFilePointer() - 20);
            this.archive.readFully(this.WORD_BUF);
            z = Arrays.equals(ZipArchiveOutputStream.ZIP64_EOCD_LOC_SIG, this.WORD_BUF);
        }
        if (!z) {
            if (z2) {
                skipBytes(16);
            }
            positionAtCentralDirectory32();
            return;
        }
        positionAtCentralDirectory64();
    }

    private void positionAtCentralDirectory64() throws IOException {
        skipBytes(4);
        this.archive.readFully(this.DWORD_BUF);
        this.archive.seek(ZipEightByteInteger.getLongValue(this.DWORD_BUF));
        this.archive.readFully(this.WORD_BUF);
        if (!Arrays.equals(this.WORD_BUF, ZipArchiveOutputStream.ZIP64_EOCD_SIG)) {
            throw new ZipException("archive's ZIP64 end of central directory locator is corrupt.");
        }
        skipBytes(44);
        this.archive.readFully(this.DWORD_BUF);
        this.archive.seek(ZipEightByteInteger.getLongValue(this.DWORD_BUF));
    }

    private void positionAtCentralDirectory32() throws IOException {
        skipBytes(16);
        this.archive.readFully(this.WORD_BUF);
        this.archive.seek(ZipLong.getValue(this.WORD_BUF));
    }

    private void positionAtEndOfCentralDirectoryRecord() throws IOException {
        if (!tryToLocateSignature(22L, 65557L, ZipArchiveOutputStream.EOCD_SIG)) {
            throw new ZipException("archive is not a ZIP archive");
        }
    }

    private boolean tryToLocateSignature(long j, long j2, byte[] bArr) throws IOException {
        long length = this.archive.length() - j;
        long max = Math.max(0L, this.archive.length() - j2);
        boolean z = true;
        if (length >= 0) {
            while (length >= max) {
                this.archive.seek(length);
                int read = this.archive.read();
                if (read != -1) {
                    if (read == bArr[0] && this.archive.read() == bArr[1] && this.archive.read() == bArr[2] && this.archive.read() == bArr[3]) {
                        break;
                    }
                    length--;
                } else {
                    break;
                }
            }
        }
        z = false;
        if (z) {
            this.archive.seek(length);
        }
        return z;
    }

    private void skipBytes(int i) throws IOException {
        int i2 = 0;
        while (i2 < i) {
            int skipBytes = this.archive.skipBytes(i - i2);
            if (skipBytes <= 0) {
                throw new EOFException();
            }
            i2 += skipBytes;
        }
    }

    private void resolveLocalFileHeaderData(Map<ZipArchiveEntry, NameAndComment> map) throws IOException {
        Iterator<ZipArchiveEntry> it = this.entries.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            OffsetEntry offsetEntry = entry.getOffsetEntry();
            long j = offsetEntry.headerOffset;
            RandomAccessFile randomAccessFile = this.archive;
            long j2 = j + LFH_OFFSET_FOR_FILENAME_LENGTH;
            randomAccessFile.seek(j2);
            this.archive.readFully(this.SHORT_BUF);
            int value = ZipShort.getValue(this.SHORT_BUF);
            this.archive.readFully(this.SHORT_BUF);
            int value2 = ZipShort.getValue(this.SHORT_BUF);
            int i = value;
            while (i > 0) {
                int skipBytes = this.archive.skipBytes(i);
                if (skipBytes <= 0) {
                    throw new IOException("failed to skip file name in local file header");
                }
                i -= skipBytes;
            }
            byte[] bArr = new byte[value2];
            this.archive.readFully(bArr);
            entry.setExtra(bArr);
            offsetEntry.dataOffset = j2 + 2 + 2 + value + value2;
            if (map.containsKey(entry)) {
                NameAndComment nameAndComment = map.get(entry);
                ZipUtil.setNameAndCommentFromExtraFields(entry, nameAndComment.name, nameAndComment.comment);
            }
            String name = entry.getName();
            LinkedList<ZipArchiveEntry> linkedList = this.nameMap.get(name);
            if (linkedList == null) {
                linkedList = new LinkedList<>();
                this.nameMap.put(name, linkedList);
            }
            linkedList.addLast(entry);
        }
    }

    private boolean startsWithLocalFileHeader() throws IOException {
        this.archive.seek(0L);
        this.archive.readFully(this.WORD_BUF);
        return Arrays.equals(this.WORD_BUF, ZipArchiveOutputStream.LFH_SIG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class BoundedInputStream extends InputStream {
        private boolean addDummyByte = false;
        private long loc;
        private long remaining;

        BoundedInputStream(long j, long j2) {
            this.remaining = j2;
            this.loc = j;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            int read;
            long j = this.remaining;
            this.remaining = j - 1;
            if (j > 0) {
                synchronized (ZipFile.this.archive) {
                    RandomAccessFile randomAccessFile = ZipFile.this.archive;
                    long j2 = this.loc;
                    this.loc = 1 + j2;
                    randomAccessFile.seek(j2);
                    read = ZipFile.this.archive.read();
                }
                return read;
            } else if (this.addDummyByte) {
                this.addDummyByte = false;
                return 0;
            } else {
                return -1;
            }
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int read;
            long j = this.remaining;
            if (j <= 0) {
                if (this.addDummyByte) {
                    this.addDummyByte = false;
                    bArr[i] = 0;
                    return 1;
                }
                return -1;
            } else if (i2 <= 0) {
                return 0;
            } else {
                if (i2 > j) {
                    i2 = (int) j;
                }
                synchronized (ZipFile.this.archive) {
                    ZipFile.this.archive.seek(this.loc);
                    read = ZipFile.this.archive.read(bArr, i, i2);
                }
                if (read > 0) {
                    long j2 = read;
                    this.loc += j2;
                    this.remaining -= j2;
                }
                return read;
            }
        }

        void addDummy() {
            this.addDummyByte = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class NameAndComment {
        private final byte[] comment;
        private final byte[] name;

        private NameAndComment(byte[] bArr, byte[] bArr2) {
            this.name = bArr;
            this.comment = bArr2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class Entry extends ZipArchiveEntry {
        private final OffsetEntry offsetEntry;

        Entry(OffsetEntry offsetEntry) {
            this.offsetEntry = offsetEntry;
        }

        OffsetEntry getOffsetEntry() {
            return this.offsetEntry;
        }

        @Override // org.apache.commons.compress.archivers.zip.ZipArchiveEntry, java.util.zip.ZipEntry
        public int hashCode() {
            return (super.hashCode() * 3) + ((int) (this.offsetEntry.headerOffset % 2147483647L));
        }

        @Override // org.apache.commons.compress.archivers.zip.ZipArchiveEntry
        public boolean equals(Object obj) {
            if (super.equals(obj)) {
                Entry entry = (Entry) obj;
                return this.offsetEntry.headerOffset == entry.offsetEntry.headerOffset && this.offsetEntry.dataOffset == entry.offsetEntry.dataOffset;
            }
            return false;
        }
    }
}
