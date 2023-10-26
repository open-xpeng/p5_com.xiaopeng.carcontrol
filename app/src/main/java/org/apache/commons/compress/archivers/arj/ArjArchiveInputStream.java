package org.apache.commons.compress.archivers.arj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.CRC32;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.utils.BoundedInputStream;
import org.apache.commons.compress.utils.CRC32VerifyingInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.tukaani.xz.common.Util;

/* loaded from: classes3.dex */
public class ArjArchiveInputStream extends ArchiveInputStream {
    private static final int ARJ_MAGIC_1 = 96;
    private static final int ARJ_MAGIC_2 = 234;
    private final String charsetName;
    private InputStream currentInputStream;
    private LocalFileHeader currentLocalFileHeader;
    private final DataInputStream in;
    private final MainHeader mainHeader;

    public ArjArchiveInputStream(InputStream inputStream, String str) throws ArchiveException {
        this.currentLocalFileHeader = null;
        this.currentInputStream = null;
        this.in = new DataInputStream(inputStream);
        this.charsetName = str;
        try {
            MainHeader readMainHeader = readMainHeader();
            this.mainHeader = readMainHeader;
            if ((readMainHeader.arjFlags & 1) != 0) {
                throw new ArchiveException("Encrypted ARJ files are unsupported");
            }
            if ((readMainHeader.arjFlags & 4) != 0) {
                throw new ArchiveException("Multi-volume ARJ files are unsupported");
            }
        } catch (IOException e) {
            throw new ArchiveException(e.getMessage(), e);
        }
    }

    public ArjArchiveInputStream(InputStream inputStream) throws ArchiveException {
        this(inputStream, "CP437");
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    private int read8(DataInputStream dataInputStream) throws IOException {
        int readUnsignedByte = dataInputStream.readUnsignedByte();
        count(1);
        return readUnsignedByte;
    }

    private int read16(DataInputStream dataInputStream) throws IOException {
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        count(2);
        return Integer.reverseBytes(readUnsignedShort) >>> 16;
    }

    private int read32(DataInputStream dataInputStream) throws IOException {
        int readInt = dataInputStream.readInt();
        count(4);
        return Integer.reverseBytes(readInt);
    }

    private String readString(DataInputStream dataInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int readUnsignedByte = dataInputStream.readUnsignedByte();
            if (readUnsignedByte == 0) {
                break;
            }
            byteArrayOutputStream.write(readUnsignedByte);
        }
        if (this.charsetName != null) {
            return new String(byteArrayOutputStream.toByteArray(), this.charsetName);
        }
        return new String(byteArrayOutputStream.toByteArray());
    }

    private void readFully(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        dataInputStream.readFully(bArr);
        count(bArr.length);
    }

    private byte[] readHeader() throws IOException {
        boolean z = false;
        byte[] bArr = null;
        do {
            int read8 = read8(this.in);
            while (true) {
                int read82 = read8(this.in);
                if (read8 == 96 || read82 == ARJ_MAGIC_2) {
                    break;
                }
                read8 = read82;
            }
            int read16 = read16(this.in);
            if (read16 == 0) {
                return null;
            }
            if (read16 <= 2600) {
                bArr = new byte[read16];
                readFully(this.in, bArr);
                CRC32 crc32 = new CRC32();
                crc32.update(bArr);
                if ((read32(this.in) & 4294967295L) == crc32.getValue()) {
                    z = true;
                    continue;
                } else {
                    continue;
                }
            }
        } while (!z);
        return bArr;
    }

    private MainHeader readMainHeader() throws IOException {
        byte[] readHeader = readHeader();
        if (readHeader == null) {
            throw new IOException("Archive ends without any headers");
        }
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(readHeader));
        int readUnsignedByte = dataInputStream.readUnsignedByte();
        byte[] bArr = new byte[readUnsignedByte - 1];
        dataInputStream.readFully(bArr);
        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
        MainHeader mainHeader = new MainHeader();
        mainHeader.archiverVersionNumber = dataInputStream2.readUnsignedByte();
        mainHeader.minVersionToExtract = dataInputStream2.readUnsignedByte();
        mainHeader.hostOS = dataInputStream2.readUnsignedByte();
        mainHeader.arjFlags = dataInputStream2.readUnsignedByte();
        mainHeader.securityVersion = dataInputStream2.readUnsignedByte();
        mainHeader.fileType = dataInputStream2.readUnsignedByte();
        mainHeader.reserved = dataInputStream2.readUnsignedByte();
        mainHeader.dateTimeCreated = read32(dataInputStream2);
        mainHeader.dateTimeModified = read32(dataInputStream2);
        mainHeader.archiveSize = read32(dataInputStream2) & 4294967295L;
        mainHeader.securityEnvelopeFilePosition = read32(dataInputStream2);
        mainHeader.fileSpecPosition = read16(dataInputStream2);
        mainHeader.securityEnvelopeLength = read16(dataInputStream2);
        pushedBackBytes(20L);
        mainHeader.encryptionVersion = dataInputStream2.readUnsignedByte();
        mainHeader.lastChapter = dataInputStream2.readUnsignedByte();
        if (readUnsignedByte >= 33) {
            mainHeader.arjProtectionFactor = dataInputStream2.readUnsignedByte();
            mainHeader.arjFlags2 = dataInputStream2.readUnsignedByte();
            dataInputStream2.readUnsignedByte();
            dataInputStream2.readUnsignedByte();
        }
        mainHeader.name = readString(dataInputStream);
        mainHeader.comment = readString(dataInputStream);
        int read16 = read16(this.in);
        if (read16 > 0) {
            mainHeader.extendedHeaderBytes = new byte[read16];
            readFully(this.in, mainHeader.extendedHeaderBytes);
            CRC32 crc32 = new CRC32();
            crc32.update(mainHeader.extendedHeaderBytes);
            if ((read32(this.in) & 4294967295L) != crc32.getValue()) {
                throw new IOException("Extended header CRC32 verification failure");
            }
        }
        return mainHeader;
    }

    private LocalFileHeader readLocalFileHeader() throws IOException {
        byte[] readHeader = readHeader();
        if (readHeader == null) {
            return null;
        }
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(readHeader));
        int readUnsignedByte = dataInputStream.readUnsignedByte();
        byte[] bArr = new byte[readUnsignedByte - 1];
        dataInputStream.readFully(bArr);
        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(bArr));
        LocalFileHeader localFileHeader = new LocalFileHeader();
        localFileHeader.archiverVersionNumber = dataInputStream2.readUnsignedByte();
        localFileHeader.minVersionToExtract = dataInputStream2.readUnsignedByte();
        localFileHeader.hostOS = dataInputStream2.readUnsignedByte();
        localFileHeader.arjFlags = dataInputStream2.readUnsignedByte();
        localFileHeader.method = dataInputStream2.readUnsignedByte();
        localFileHeader.fileType = dataInputStream2.readUnsignedByte();
        localFileHeader.reserved = dataInputStream2.readUnsignedByte();
        localFileHeader.dateTimeModified = read32(dataInputStream2);
        localFileHeader.compressedSize = read32(dataInputStream2) & 4294967295L;
        localFileHeader.originalSize = read32(dataInputStream2) & 4294967295L;
        localFileHeader.originalCrc32 = read32(dataInputStream2) & 4294967295L;
        localFileHeader.fileSpecPosition = read16(dataInputStream2);
        localFileHeader.fileAccessMode = read16(dataInputStream2);
        pushedBackBytes(20L);
        localFileHeader.firstChapter = dataInputStream2.readUnsignedByte();
        localFileHeader.lastChapter = dataInputStream2.readUnsignedByte();
        readExtraData(readUnsignedByte, dataInputStream2, localFileHeader);
        localFileHeader.name = readString(dataInputStream);
        localFileHeader.comment = readString(dataInputStream);
        ArrayList arrayList = new ArrayList();
        while (true) {
            int read16 = read16(this.in);
            if (read16 > 0) {
                byte[] bArr2 = new byte[read16];
                readFully(this.in, bArr2);
                CRC32 crc32 = new CRC32();
                crc32.update(bArr2);
                if ((read32(this.in) & 4294967295L) != crc32.getValue()) {
                    throw new IOException("Extended header CRC32 verification failure");
                }
                arrayList.add(bArr2);
            } else {
                localFileHeader.extendedHeaders = (byte[][]) arrayList.toArray(new byte[arrayList.size()]);
                return localFileHeader;
            }
        }
    }

    private void readExtraData(int i, DataInputStream dataInputStream, LocalFileHeader localFileHeader) throws IOException {
        if (i >= 33) {
            localFileHeader.extendedFilePosition = read32(dataInputStream);
            if (i >= 45) {
                localFileHeader.dateTimeAccessed = read32(dataInputStream);
                localFileHeader.dateTimeCreated = read32(dataInputStream);
                localFileHeader.originalSizeEvenForVolumes = read32(dataInputStream);
                pushedBackBytes(12L);
            }
            pushedBackBytes(4L);
        }
    }

    public static boolean matches(byte[] bArr, int i) {
        return i >= 2 && (bArr[0] & 255) == 96 && (bArr[1] & 255) == ARJ_MAGIC_2;
    }

    public String getArchiveName() {
        return this.mainHeader.name;
    }

    public String getArchiveComment() {
        return this.mainHeader.comment;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public ArjArchiveEntry getNextEntry() throws IOException {
        InputStream inputStream = this.currentInputStream;
        if (inputStream != null) {
            IOUtils.skip(inputStream, Util.VLI_MAX);
            this.currentInputStream.close();
            this.currentLocalFileHeader = null;
            this.currentInputStream = null;
        }
        LocalFileHeader readLocalFileHeader = readLocalFileHeader();
        this.currentLocalFileHeader = readLocalFileHeader;
        if (readLocalFileHeader != null) {
            this.currentInputStream = new BoundedInputStream(this.in, this.currentLocalFileHeader.compressedSize);
            if (this.currentLocalFileHeader.method == 0) {
                this.currentInputStream = new CRC32VerifyingInputStream(this.currentInputStream, this.currentLocalFileHeader.originalSize, this.currentLocalFileHeader.originalCrc32);
            }
            return new ArjArchiveEntry(this.currentLocalFileHeader);
        }
        this.currentInputStream = null;
        return null;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public boolean canReadEntryData(ArchiveEntry archiveEntry) {
        return (archiveEntry instanceof ArjArchiveEntry) && ((ArjArchiveEntry) archiveEntry).getMethod() == 0;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        LocalFileHeader localFileHeader = this.currentLocalFileHeader;
        if (localFileHeader == null) {
            throw new IllegalStateException("No current arj entry");
        }
        if (localFileHeader.method != 0) {
            throw new IOException("Unsupported compression method " + this.currentLocalFileHeader.method);
        }
        return this.currentInputStream.read(bArr, i, i2);
    }
}
