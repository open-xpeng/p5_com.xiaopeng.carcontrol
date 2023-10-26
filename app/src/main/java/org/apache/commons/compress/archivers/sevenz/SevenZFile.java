package org.apache.commons.compress.archivers.sevenz;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.zip.CRC32;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.commons.compress.utils.BoundedInputStream;
import org.apache.commons.compress.utils.CRC32VerifyingInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.tukaani.xz.common.Util;

/* loaded from: classes3.dex */
public class SevenZFile implements Closeable {
    static final int SIGNATURE_HEADER_SIZE = 32;
    static final byte[] sevenZSignature = {TarConstants.LF_CONTIG, 122, -68, -81, 39, 28};
    private final Archive archive;
    private int currentEntryIndex;
    private int currentFolderIndex;
    private InputStream currentFolderInputStream;
    private final ArrayList<InputStream> deferredBlockStreams;
    private RandomAccessFile file;
    private final String fileName;
    private byte[] password;

    public SevenZFile(File file, byte[] bArr) throws IOException {
        this.currentEntryIndex = -1;
        this.currentFolderIndex = -1;
        this.currentFolderInputStream = null;
        this.deferredBlockStreams = new ArrayList<>();
        this.file = new RandomAccessFile(file, "r");
        this.fileName = file.getAbsolutePath();
        try {
            this.archive = readHeaders(bArr);
            if (bArr != null) {
                byte[] bArr2 = new byte[bArr.length];
                this.password = bArr2;
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                return;
            }
            this.password = null;
        } catch (Throwable th) {
            this.file.close();
            throw th;
        }
    }

    public SevenZFile(File file) throws IOException {
        this(file, null);
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [java.io.RandomAccessFile, byte[]] */
    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        RandomAccessFile randomAccessFile = this.file;
        if (randomAccessFile != null) {
            try {
                randomAccessFile.close();
            } finally {
                this.file = null;
                byte[] bArr = this.password;
                if (bArr != null) {
                    Arrays.fill(bArr, (byte) 0);
                }
                this.password = null;
            }
        }
    }

    public SevenZArchiveEntry getNextEntry() throws IOException {
        if (this.currentEntryIndex >= this.archive.files.length - 1) {
            return null;
        }
        this.currentEntryIndex++;
        SevenZArchiveEntry sevenZArchiveEntry = this.archive.files[this.currentEntryIndex];
        buildDecodingStream();
        return sevenZArchiveEntry;
    }

    public Iterable<SevenZArchiveEntry> getEntries() {
        return Arrays.asList(this.archive.files);
    }

    private Archive readHeaders(byte[] bArr) throws IOException {
        byte[] bArr2 = new byte[6];
        this.file.readFully(bArr2);
        if (!Arrays.equals(bArr2, sevenZSignature)) {
            throw new IOException("Bad 7z signature");
        }
        byte readByte = this.file.readByte();
        byte readByte2 = this.file.readByte();
        if (readByte != 0) {
            throw new IOException(String.format("Unsupported 7z version (%d,%d)", Byte.valueOf(readByte), Byte.valueOf(readByte2)));
        }
        StartHeader readStartHeader = readStartHeader(4294967295L & Integer.reverseBytes(this.file.readInt()));
        int i = (int) readStartHeader.nextHeaderSize;
        if (i != readStartHeader.nextHeaderSize) {
            throw new IOException("cannot handle nextHeaderSize " + readStartHeader.nextHeaderSize);
        }
        this.file.seek(readStartHeader.nextHeaderOffset + 32);
        byte[] bArr3 = new byte[i];
        this.file.readFully(bArr3);
        CRC32 crc32 = new CRC32();
        crc32.update(bArr3);
        if (readStartHeader.nextHeaderCrc != crc32.getValue()) {
            throw new IOException("NextHeader CRC mismatch");
        }
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr3));
        Archive archive = new Archive();
        int readUnsignedByte = dataInputStream.readUnsignedByte();
        if (readUnsignedByte == 23) {
            dataInputStream = readEncodedHeader(dataInputStream, archive, bArr);
            archive = new Archive();
            readUnsignedByte = dataInputStream.readUnsignedByte();
        }
        if (readUnsignedByte == 1) {
            readHeader(dataInputStream, archive);
            dataInputStream.close();
            return archive;
        }
        throw new IOException("Broken or unsupported archive: no Header");
    }

    private StartHeader readStartHeader(long j) throws IOException {
        DataInputStream dataInputStream;
        StartHeader startHeader = new StartHeader();
        DataInputStream dataInputStream2 = null;
        try {
            dataInputStream = new DataInputStream(new CRC32VerifyingInputStream(new BoundedRandomAccessFileInputStream(this.file, 20L), 20L, j));
        } catch (Throwable th) {
            th = th;
        }
        try {
            startHeader.nextHeaderOffset = Long.reverseBytes(dataInputStream.readLong());
            startHeader.nextHeaderSize = Long.reverseBytes(dataInputStream.readLong());
            startHeader.nextHeaderCrc = 4294967295L & Integer.reverseBytes(dataInputStream.readInt());
            dataInputStream.close();
            return startHeader;
        } catch (Throwable th2) {
            th = th2;
            dataInputStream2 = dataInputStream;
            if (dataInputStream2 != null) {
                dataInputStream2.close();
            }
            throw th;
        }
    }

    private void readHeader(DataInput dataInput, Archive archive) throws IOException {
        int readUnsignedByte = dataInput.readUnsignedByte();
        if (readUnsignedByte == 2) {
            readArchiveProperties(dataInput);
            readUnsignedByte = dataInput.readUnsignedByte();
        }
        if (readUnsignedByte == 3) {
            throw new IOException("Additional streams unsupported");
        }
        if (readUnsignedByte == 4) {
            readStreamsInfo(dataInput, archive);
            readUnsignedByte = dataInput.readUnsignedByte();
        }
        if (readUnsignedByte == 5) {
            readFilesInfo(dataInput, archive);
            readUnsignedByte = dataInput.readUnsignedByte();
        }
        if (readUnsignedByte != 0) {
            throw new IOException("Badly terminated header, found " + readUnsignedByte);
        }
    }

    private void readArchiveProperties(DataInput dataInput) throws IOException {
        int readUnsignedByte = dataInput.readUnsignedByte();
        while (readUnsignedByte != 0) {
            dataInput.readFully(new byte[(int) readUint64(dataInput)]);
            readUnsignedByte = dataInput.readUnsignedByte();
        }
    }

    private DataInputStream readEncodedHeader(DataInputStream dataInputStream, Archive archive, byte[] bArr) throws IOException {
        readStreamsInfo(dataInputStream, archive);
        Folder folder = archive.folders[0];
        this.file.seek(archive.packPos + 32 + 0);
        BoundedRandomAccessFileInputStream boundedRandomAccessFileInputStream = new BoundedRandomAccessFileInputStream(this.file, archive.packSizes[0]);
        CRC32VerifyingInputStream cRC32VerifyingInputStream = boundedRandomAccessFileInputStream;
        for (Coder coder : folder.getOrderedCoders()) {
            if (coder.numInStreams != 1 || coder.numOutStreams != 1) {
                throw new IOException("Multi input/output stream coders are not yet supported");
            }
            cRC32VerifyingInputStream = Coders.addDecoder(this.fileName, cRC32VerifyingInputStream, folder.getUnpackSizeForCoder(coder), coder, bArr);
        }
        if (folder.hasCrc) {
            cRC32VerifyingInputStream = new CRC32VerifyingInputStream(cRC32VerifyingInputStream, folder.getUnpackSize(), folder.crc);
        }
        byte[] bArr2 = new byte[(int) folder.getUnpackSize()];
        DataInputStream dataInputStream2 = new DataInputStream(cRC32VerifyingInputStream);
        try {
            dataInputStream2.readFully(bArr2);
            dataInputStream2.close();
            return new DataInputStream(new ByteArrayInputStream(bArr2));
        } catch (Throwable th) {
            dataInputStream2.close();
            throw th;
        }
    }

    private void readStreamsInfo(DataInput dataInput, Archive archive) throws IOException {
        int readUnsignedByte = dataInput.readUnsignedByte();
        if (readUnsignedByte == 6) {
            readPackInfo(dataInput, archive);
            readUnsignedByte = dataInput.readUnsignedByte();
        }
        if (readUnsignedByte == 7) {
            readUnpackInfo(dataInput, archive);
            readUnsignedByte = dataInput.readUnsignedByte();
        } else {
            archive.folders = new Folder[0];
        }
        if (readUnsignedByte == 8) {
            readSubStreamsInfo(dataInput, archive);
            readUnsignedByte = dataInput.readUnsignedByte();
        }
        if (readUnsignedByte != 0) {
            throw new IOException("Badly terminated StreamsInfo");
        }
    }

    private void readPackInfo(DataInput dataInput, Archive archive) throws IOException {
        archive.packPos = readUint64(dataInput);
        long readUint64 = readUint64(dataInput);
        int readUnsignedByte = dataInput.readUnsignedByte();
        if (readUnsignedByte == 9) {
            archive.packSizes = new long[(int) readUint64];
            for (int i = 0; i < archive.packSizes.length; i++) {
                archive.packSizes[i] = readUint64(dataInput);
            }
            readUnsignedByte = dataInput.readUnsignedByte();
        }
        if (readUnsignedByte == 10) {
            int i2 = (int) readUint64;
            archive.packCrcsDefined = readAllOrBits(dataInput, i2);
            archive.packCrcs = new long[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                if (archive.packCrcsDefined.get(i3)) {
                    archive.packCrcs[i3] = 4294967295L & Integer.reverseBytes(dataInput.readInt());
                }
            }
            readUnsignedByte = dataInput.readUnsignedByte();
        }
        if (readUnsignedByte != 0) {
            throw new IOException("Badly terminated PackInfo (" + readUnsignedByte + ")");
        }
    }

    private void readUnpackInfo(DataInput dataInput, Archive archive) throws IOException {
        int readUnsignedByte = dataInput.readUnsignedByte();
        if (readUnsignedByte != 11) {
            throw new IOException("Expected kFolder, got " + readUnsignedByte);
        }
        int readUint64 = (int) readUint64(dataInput);
        Folder[] folderArr = new Folder[readUint64];
        archive.folders = folderArr;
        if (dataInput.readUnsignedByte() != 0) {
            throw new IOException("External unsupported");
        }
        for (int i = 0; i < readUint64; i++) {
            folderArr[i] = readFolder(dataInput);
        }
        int readUnsignedByte2 = dataInput.readUnsignedByte();
        if (readUnsignedByte2 != 12) {
            throw new IOException("Expected kCodersUnpackSize, got " + readUnsignedByte2);
        }
        for (int i2 = 0; i2 < readUint64; i2++) {
            Folder folder = folderArr[i2];
            folder.unpackSizes = new long[(int) folder.totalOutputStreams];
            for (int i3 = 0; i3 < folder.totalOutputStreams; i3++) {
                folder.unpackSizes[i3] = readUint64(dataInput);
            }
        }
        int readUnsignedByte3 = dataInput.readUnsignedByte();
        if (readUnsignedByte3 == 10) {
            BitSet readAllOrBits = readAllOrBits(dataInput, readUint64);
            for (int i4 = 0; i4 < readUint64; i4++) {
                if (readAllOrBits.get(i4)) {
                    folderArr[i4].hasCrc = true;
                    folderArr[i4].crc = 4294967295L & Integer.reverseBytes(dataInput.readInt());
                } else {
                    folderArr[i4].hasCrc = false;
                }
            }
            readUnsignedByte3 = dataInput.readUnsignedByte();
        }
        if (readUnsignedByte3 != 0) {
            throw new IOException("Badly terminated UnpackInfo");
        }
    }

    private void readSubStreamsInfo(DataInput dataInput, Archive archive) throws IOException {
        boolean z;
        Folder[] folderArr;
        Folder[] folderArr2;
        Folder[] folderArr3;
        Folder[] folderArr4 = archive.folders;
        int length = folderArr4.length;
        int i = 0;
        while (true) {
            z = true;
            if (i >= length) {
                break;
            }
            folderArr4[i].numUnpackSubStreams = 1;
            i++;
        }
        int length2 = archive.folders.length;
        int readUnsignedByte = dataInput.readUnsignedByte();
        if (readUnsignedByte == 13) {
            int i2 = 0;
            for (Folder folder : archive.folders) {
                long readUint64 = readUint64(dataInput);
                folder.numUnpackSubStreams = (int) readUint64;
                i2 = (int) (i2 + readUint64);
            }
            readUnsignedByte = dataInput.readUnsignedByte();
            length2 = i2;
        }
        SubStreamsInfo subStreamsInfo = new SubStreamsInfo();
        subStreamsInfo.unpackSizes = new long[length2];
        subStreamsInfo.hasCrc = new BitSet(length2);
        subStreamsInfo.crcs = new long[length2];
        int i3 = 0;
        for (Folder folder2 : archive.folders) {
            if (folder2.numUnpackSubStreams != 0) {
                long j = 0;
                if (readUnsignedByte == 9) {
                    int i4 = 0;
                    while (i4 < folder2.numUnpackSubStreams - 1) {
                        long readUint642 = readUint64(dataInput);
                        subStreamsInfo.unpackSizes[i3] = readUint642;
                        j += readUint642;
                        i4++;
                        i3++;
                    }
                }
                subStreamsInfo.unpackSizes[i3] = folder2.getUnpackSize() - j;
                i3++;
            }
        }
        if (readUnsignedByte == 9) {
            readUnsignedByte = dataInput.readUnsignedByte();
        }
        int i5 = 0;
        for (Folder folder3 : archive.folders) {
            if (folder3.numUnpackSubStreams != 1 || !folder3.hasCrc) {
                i5 += folder3.numUnpackSubStreams;
            }
        }
        if (readUnsignedByte == 10) {
            BitSet readAllOrBits = readAllOrBits(dataInput, i5);
            long[] jArr = new long[i5];
            for (int i6 = 0; i6 < i5; i6++) {
                if (readAllOrBits.get(i6)) {
                    jArr[i6] = 4294967295L & Integer.reverseBytes(dataInput.readInt());
                }
            }
            Folder[] folderArr5 = archive.folders;
            int length3 = folderArr5.length;
            int i7 = 0;
            int i8 = 0;
            int i9 = 0;
            while (i7 < length3) {
                Folder folder4 = folderArr5[i7];
                if (folder4.numUnpackSubStreams == z && folder4.hasCrc) {
                    subStreamsInfo.hasCrc.set(i8, z);
                    subStreamsInfo.crcs[i8] = folder4.crc;
                    i8++;
                } else {
                    for (int i10 = 0; i10 < folder4.numUnpackSubStreams; i10++) {
                        subStreamsInfo.hasCrc.set(i8, readAllOrBits.get(i9));
                        subStreamsInfo.crcs[i8] = jArr[i9];
                        i8++;
                        i9++;
                    }
                }
                i7++;
                z = true;
            }
            readUnsignedByte = dataInput.readUnsignedByte();
        }
        if (readUnsignedByte != 0) {
            throw new IOException("Badly terminated SubStreamsInfo");
        }
        archive.subStreamsInfo = subStreamsInfo;
    }

    private Folder readFolder(DataInput dataInput) throws IOException {
        int i;
        Folder folder = new Folder();
        int readUint64 = (int) readUint64(dataInput);
        Coder[] coderArr = new Coder[readUint64];
        long j = 0;
        long j2 = 0;
        for (int i2 = 0; i2 < readUint64; i2++) {
            coderArr[i2] = new Coder();
            int readUnsignedByte = dataInput.readUnsignedByte();
            int i3 = readUnsignedByte & 15;
            boolean z = (readUnsignedByte & 16) == 0;
            boolean z2 = (readUnsignedByte & 32) != 0;
            boolean z3 = (readUnsignedByte & 128) != 0;
            coderArr[i2].decompressionMethodId = new byte[i3];
            dataInput.readFully(coderArr[i2].decompressionMethodId);
            if (z) {
                coderArr[i2].numInStreams = 1L;
                coderArr[i2].numOutStreams = 1L;
            } else {
                coderArr[i2].numInStreams = readUint64(dataInput);
                coderArr[i2].numOutStreams = readUint64(dataInput);
            }
            j += coderArr[i2].numInStreams;
            j2 += coderArr[i2].numOutStreams;
            if (z2) {
                coderArr[i2].properties = new byte[(int) readUint64(dataInput)];
                dataInput.readFully(coderArr[i2].properties);
            }
            if (z3) {
                throw new IOException("Alternative methods are unsupported, please report. The reference implementation doesn't support them either.");
            }
        }
        folder.coders = coderArr;
        folder.totalInputStreams = j;
        folder.totalOutputStreams = j2;
        if (j2 == 0) {
            throw new IOException("Total output streams can't be 0");
        }
        long j3 = j2 - 1;
        int i4 = (int) j3;
        BindPair[] bindPairArr = new BindPair[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            bindPairArr[i5] = new BindPair();
            bindPairArr[i5].inIndex = readUint64(dataInput);
            bindPairArr[i5].outIndex = readUint64(dataInput);
        }
        folder.bindPairs = bindPairArr;
        if (j < j3) {
            throw new IOException("Total input streams can't be less than the number of bind pairs");
        }
        long j4 = j - j3;
        int i6 = (int) j4;
        long[] jArr = new long[i6];
        if (j4 == 1) {
            int i7 = 0;
            while (true) {
                i = (int) j;
                if (i7 >= i || folder.findBindPairForInStream(i7) < 0) {
                    break;
                }
                i7++;
            }
            if (i7 == i) {
                throw new IOException("Couldn't find stream's bind pair index");
            }
            jArr[0] = i7;
        } else {
            for (int i8 = 0; i8 < i6; i8++) {
                jArr[i8] = readUint64(dataInput);
            }
        }
        folder.packedStreams = jArr;
        return folder;
    }

    private BitSet readAllOrBits(DataInput dataInput, int i) throws IOException {
        if (dataInput.readUnsignedByte() != 0) {
            BitSet bitSet = new BitSet(i);
            for (int i2 = 0; i2 < i; i2++) {
                bitSet.set(i2, true);
            }
            return bitSet;
        }
        return readBits(dataInput, i);
    }

    private BitSet readBits(DataInput dataInput, int i) throws IOException {
        BitSet bitSet = new BitSet(i);
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < i; i4++) {
            if (i2 == 0) {
                i2 = 128;
                i3 = dataInput.readUnsignedByte();
            }
            bitSet.set(i4, (i3 & i2) != 0);
            i2 >>>= 1;
        }
        return bitSet;
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x01ec, code lost:
        throw new java.io.IOException("Error parsing file names");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void readFilesInfo(java.io.DataInput r17, org.apache.commons.compress.archivers.sevenz.Archive r18) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 612
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.archivers.sevenz.SevenZFile.readFilesInfo(java.io.DataInput, org.apache.commons.compress.archivers.sevenz.Archive):void");
    }

    private void calculateStreamMap(Archive archive) throws IOException {
        StreamMap streamMap = new StreamMap();
        int length = archive.folders != null ? archive.folders.length : 0;
        streamMap.folderFirstPackStreamIndex = new int[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            streamMap.folderFirstPackStreamIndex[i2] = i;
            i += archive.folders[i2].packedStreams.length;
        }
        long j = 0;
        int length2 = archive.packSizes != null ? archive.packSizes.length : 0;
        streamMap.packStreamOffsets = new long[length2];
        for (int i3 = 0; i3 < length2; i3++) {
            streamMap.packStreamOffsets[i3] = j;
            j += archive.packSizes[i3];
        }
        streamMap.folderFirstFileIndex = new int[length];
        streamMap.fileFolderIndex = new int[archive.files.length];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < archive.files.length; i6++) {
            if (!archive.files[i6].hasStream() && i4 == 0) {
                streamMap.fileFolderIndex[i6] = -1;
            } else {
                if (i4 == 0) {
                    while (i5 < archive.folders.length) {
                        streamMap.folderFirstFileIndex[i5] = i6;
                        if (archive.folders[i5].numUnpackSubStreams > 0) {
                            break;
                        }
                        i5++;
                    }
                    if (i5 >= archive.folders.length) {
                        throw new IOException("Too few folders in archive");
                    }
                }
                streamMap.fileFolderIndex[i6] = i5;
                if (archive.files[i6].hasStream() && (i4 = i4 + 1) >= archive.folders[i5].numUnpackSubStreams) {
                    i5++;
                    i4 = 0;
                }
            }
        }
        archive.streamMap = streamMap;
    }

    private void buildDecodingStream() throws IOException {
        int i = this.archive.streamMap.fileFolderIndex[this.currentEntryIndex];
        if (i < 0) {
            this.deferredBlockStreams.clear();
            return;
        }
        SevenZArchiveEntry sevenZArchiveEntry = this.archive.files[this.currentEntryIndex];
        if (this.currentFolderIndex == i) {
            sevenZArchiveEntry.setContentMethods(this.archive.files[this.currentEntryIndex - 1].getContentMethods());
        } else {
            this.currentFolderIndex = i;
            this.deferredBlockStreams.clear();
            InputStream inputStream = this.currentFolderInputStream;
            if (inputStream != null) {
                inputStream.close();
                this.currentFolderInputStream = null;
            }
            Folder folder = this.archive.folders[i];
            int i2 = this.archive.streamMap.folderFirstPackStreamIndex[i];
            this.currentFolderInputStream = buildDecoderStack(folder, this.archive.packPos + 32 + this.archive.streamMap.packStreamOffsets[i2], i2, sevenZArchiveEntry);
        }
        InputStream boundedInputStream = new BoundedInputStream(this.currentFolderInputStream, sevenZArchiveEntry.getSize());
        if (sevenZArchiveEntry.getHasCrc()) {
            boundedInputStream = new CRC32VerifyingInputStream(boundedInputStream, sevenZArchiveEntry.getSize(), sevenZArchiveEntry.getCrcValue());
        }
        this.deferredBlockStreams.add(boundedInputStream);
    }

    private InputStream buildDecoderStack(Folder folder, long j, int i, SevenZArchiveEntry sevenZArchiveEntry) throws IOException {
        this.file.seek(j);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new BoundedRandomAccessFileInputStream(this.file, this.archive.packSizes[i]));
        LinkedList linkedList = new LinkedList();
        InputStream inputStream = bufferedInputStream;
        for (Coder coder : folder.getOrderedCoders()) {
            if (coder.numInStreams != 1 || coder.numOutStreams != 1) {
                throw new IOException("Multi input/output stream coders are not yet supported");
            }
            SevenZMethod byId = SevenZMethod.byId(coder.decompressionMethodId);
            inputStream = Coders.addDecoder(this.fileName, inputStream, folder.getUnpackSizeForCoder(coder), coder, this.password);
            linkedList.addFirst(new SevenZMethodConfiguration(byId, Coders.findByMethod(byId).getOptionsFromCoder(coder, inputStream)));
        }
        sevenZArchiveEntry.setContentMethods(linkedList);
        return folder.hasCrc ? new CRC32VerifyingInputStream(inputStream, folder.getUnpackSize(), folder.crc) : inputStream;
    }

    public int read() throws IOException {
        return getCurrentStream().read();
    }

    private InputStream getCurrentStream() throws IOException {
        if (this.archive.files[this.currentEntryIndex].getSize() == 0) {
            return new ByteArrayInputStream(new byte[0]);
        }
        if (this.deferredBlockStreams.isEmpty()) {
            throw new IllegalStateException("No current 7z entry (call getNextEntry() first).");
        }
        while (this.deferredBlockStreams.size() > 1) {
            InputStream remove = this.deferredBlockStreams.remove(0);
            IOUtils.skip(remove, Util.VLI_MAX);
            remove.close();
        }
        return this.deferredBlockStreams.get(0);
    }

    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        return getCurrentStream().read(bArr, i, i2);
    }

    private static long readUint64(DataInput dataInput) throws IOException {
        long readUnsignedByte = dataInput.readUnsignedByte();
        int i = 128;
        long j = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            if ((i & readUnsignedByte) == 0) {
                return ((readUnsignedByte & (i - 1)) << (i2 * 8)) | j;
            }
            j |= dataInput.readUnsignedByte() << (i2 * 8);
            i >>>= 1;
        }
        return j;
    }

    public static boolean matches(byte[] bArr, int i) {
        if (i < sevenZSignature.length) {
            return false;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr2 = sevenZSignature;
            if (i2 >= bArr2.length) {
                return true;
            }
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
            i2++;
        }
    }

    private static long skipBytesFully(DataInput dataInput, long j) throws IOException {
        int skipBytes;
        if (j < 1) {
            return 0L;
        }
        long j2 = 0;
        while (j > 2147483647L) {
            long skipBytesFully = skipBytesFully(dataInput, 2147483647L);
            if (skipBytesFully == 0) {
                return j2;
            }
            j2 += skipBytesFully;
            j -= skipBytesFully;
        }
        while (j > 0 && (skipBytes = dataInput.skipBytes((int) j)) != 0) {
            long j3 = skipBytes;
            j2 += j3;
            j -= j3;
        }
        return j2;
    }

    public String toString() {
        return this.archive.toString();
    }
}
