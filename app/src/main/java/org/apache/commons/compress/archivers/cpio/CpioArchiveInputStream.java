package org.apache.commons.compress.archivers.cpio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.ArchiveUtils;
import org.apache.commons.compress.utils.CharsetNames;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes3.dex */
public class CpioArchiveInputStream extends ArchiveInputStream implements CpioConstants {
    private final byte[] FOUR_BYTES_BUF;
    private final byte[] SIX_BYTES_BUF;
    private final byte[] TWO_BYTES_BUF;
    private final int blockSize;
    private boolean closed;
    private long crc;
    final String encoding;
    private CpioArchiveEntry entry;
    private long entryBytesRead;
    private boolean entryEOF;
    private final InputStream in;
    private final byte[] tmpbuf;
    private final ZipEncoding zipEncoding;

    public CpioArchiveInputStream(InputStream inputStream) {
        this(inputStream, 512, CharsetNames.US_ASCII);
    }

    public CpioArchiveInputStream(InputStream inputStream, String str) {
        this(inputStream, 512, str);
    }

    public CpioArchiveInputStream(InputStream inputStream, int i) {
        this(inputStream, i, CharsetNames.US_ASCII);
    }

    public CpioArchiveInputStream(InputStream inputStream, int i, String str) {
        this.closed = false;
        this.entryBytesRead = 0L;
        this.entryEOF = false;
        this.tmpbuf = new byte[4096];
        this.crc = 0L;
        this.TWO_BYTES_BUF = new byte[2];
        this.FOUR_BYTES_BUF = new byte[4];
        this.SIX_BYTES_BUF = new byte[6];
        this.in = inputStream;
        this.blockSize = i;
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        ensureOpen();
        return this.entryEOF ? 0 : 1;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.in.close();
        this.closed = true;
    }

    private void closeEntry() throws IOException {
        do {
        } while (skip(2147483647L) == 2147483647L);
    }

    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }

    public CpioArchiveEntry getNextCPIOEntry() throws IOException {
        ensureOpen();
        if (this.entry != null) {
            closeEntry();
        }
        byte[] bArr = this.TWO_BYTES_BUF;
        readFully(bArr, 0, bArr.length);
        if (CpioUtil.byteArray2long(this.TWO_BYTES_BUF, false) == 29127) {
            this.entry = readOldBinaryEntry(false);
        } else if (CpioUtil.byteArray2long(this.TWO_BYTES_BUF, true) == 29127) {
            this.entry = readOldBinaryEntry(true);
        } else {
            byte[] bArr2 = this.TWO_BYTES_BUF;
            System.arraycopy(bArr2, 0, this.SIX_BYTES_BUF, 0, bArr2.length);
            readFully(this.SIX_BYTES_BUF, this.TWO_BYTES_BUF.length, this.FOUR_BYTES_BUF.length);
            String asciiString = ArchiveUtils.toAsciiString(this.SIX_BYTES_BUF);
            if (asciiString.equals(CpioConstants.MAGIC_NEW)) {
                this.entry = readNewEntry(false);
            } else if (asciiString.equals(CpioConstants.MAGIC_NEW_CRC)) {
                this.entry = readNewEntry(true);
            } else if (asciiString.equals(CpioConstants.MAGIC_OLD_ASCII)) {
                this.entry = readOldAsciiEntry();
            } else {
                throw new IOException("Unknown magic [" + asciiString + "]. Occured at byte: " + getBytesRead());
            }
        }
        this.entryBytesRead = 0L;
        this.entryEOF = false;
        this.crc = 0L;
        if (this.entry.getName().equals(CpioConstants.CPIO_TRAILER)) {
            this.entryEOF = true;
            skipRemainderOfLastBlock();
            return null;
        }
        return this.entry;
    }

    private void skip(int i) throws IOException {
        if (i > 0) {
            readFully(this.FOUR_BYTES_BUF, 0, i);
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        ensureOpen();
        if (i < 0 || i2 < 0 || i > bArr.length - i2) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == 0) {
            return 0;
        }
        CpioArchiveEntry cpioArchiveEntry = this.entry;
        if (cpioArchiveEntry == null || this.entryEOF) {
            return -1;
        }
        if (this.entryBytesRead == cpioArchiveEntry.getSize()) {
            skip(this.entry.getDataPadCount());
            this.entryEOF = true;
            if (this.entry.getFormat() != 2 || this.crc == this.entry.getChksum()) {
                return -1;
            }
            throw new IOException("CRC Error. Occured at byte: " + getBytesRead());
        }
        int min = (int) Math.min(i2, this.entry.getSize() - this.entryBytesRead);
        if (min < 0) {
            return -1;
        }
        int readFully = readFully(bArr, i, min);
        if (this.entry.getFormat() == 2) {
            for (int i3 = 0; i3 < readFully; i3++) {
                this.crc += bArr[i3] & 255;
            }
        }
        this.entryBytesRead += readFully;
        return readFully;
    }

    private final int readFully(byte[] bArr, int i, int i2) throws IOException {
        int readFully = IOUtils.readFully(this.in, bArr, i, i2);
        count(readFully);
        if (readFully >= i2) {
            return readFully;
        }
        throw new EOFException();
    }

    private long readBinaryLong(int i, boolean z) throws IOException {
        byte[] bArr = new byte[i];
        readFully(bArr, 0, i);
        return CpioUtil.byteArray2long(bArr, z);
    }

    private long readAsciiLong(int i, int i2) throws IOException {
        byte[] bArr = new byte[i];
        readFully(bArr, 0, i);
        return Long.parseLong(ArchiveUtils.toAsciiString(bArr), i2);
    }

    private CpioArchiveEntry readNewEntry(boolean z) throws IOException {
        CpioArchiveEntry cpioArchiveEntry;
        if (z) {
            cpioArchiveEntry = new CpioArchiveEntry((short) 2);
        } else {
            cpioArchiveEntry = new CpioArchiveEntry((short) 1);
        }
        cpioArchiveEntry.setInode(readAsciiLong(8, 16));
        long readAsciiLong = readAsciiLong(8, 16);
        if (CpioUtil.fileType(readAsciiLong) != 0) {
            cpioArchiveEntry.setMode(readAsciiLong);
        }
        cpioArchiveEntry.setUID(readAsciiLong(8, 16));
        cpioArchiveEntry.setGID(readAsciiLong(8, 16));
        cpioArchiveEntry.setNumberOfLinks(readAsciiLong(8, 16));
        cpioArchiveEntry.setTime(readAsciiLong(8, 16));
        cpioArchiveEntry.setSize(readAsciiLong(8, 16));
        cpioArchiveEntry.setDeviceMaj(readAsciiLong(8, 16));
        cpioArchiveEntry.setDeviceMin(readAsciiLong(8, 16));
        cpioArchiveEntry.setRemoteDeviceMaj(readAsciiLong(8, 16));
        cpioArchiveEntry.setRemoteDeviceMin(readAsciiLong(8, 16));
        long readAsciiLong2 = readAsciiLong(8, 16);
        cpioArchiveEntry.setChksum(readAsciiLong(8, 16));
        String readCString = readCString((int) readAsciiLong2);
        cpioArchiveEntry.setName(readCString);
        if (CpioUtil.fileType(readAsciiLong) == 0 && !readCString.equals(CpioConstants.CPIO_TRAILER)) {
            throw new IOException("Mode 0 only allowed in the trailer. Found entry name: " + ArchiveUtils.sanitize(readCString) + " Occured at byte: " + getBytesRead());
        }
        skip(cpioArchiveEntry.getHeaderPadCount());
        return cpioArchiveEntry;
    }

    private CpioArchiveEntry readOldAsciiEntry() throws IOException {
        CpioArchiveEntry cpioArchiveEntry = new CpioArchiveEntry((short) 4);
        cpioArchiveEntry.setDevice(readAsciiLong(6, 8));
        cpioArchiveEntry.setInode(readAsciiLong(6, 8));
        long readAsciiLong = readAsciiLong(6, 8);
        if (CpioUtil.fileType(readAsciiLong) != 0) {
            cpioArchiveEntry.setMode(readAsciiLong);
        }
        cpioArchiveEntry.setUID(readAsciiLong(6, 8));
        cpioArchiveEntry.setGID(readAsciiLong(6, 8));
        cpioArchiveEntry.setNumberOfLinks(readAsciiLong(6, 8));
        cpioArchiveEntry.setRemoteDevice(readAsciiLong(6, 8));
        cpioArchiveEntry.setTime(readAsciiLong(11, 8));
        long readAsciiLong2 = readAsciiLong(6, 8);
        cpioArchiveEntry.setSize(readAsciiLong(11, 8));
        String readCString = readCString((int) readAsciiLong2);
        cpioArchiveEntry.setName(readCString);
        if (CpioUtil.fileType(readAsciiLong) != 0 || readCString.equals(CpioConstants.CPIO_TRAILER)) {
            return cpioArchiveEntry;
        }
        throw new IOException("Mode 0 only allowed in the trailer. Found entry: " + ArchiveUtils.sanitize(readCString) + " Occured at byte: " + getBytesRead());
    }

    private CpioArchiveEntry readOldBinaryEntry(boolean z) throws IOException {
        CpioArchiveEntry cpioArchiveEntry = new CpioArchiveEntry((short) 8);
        cpioArchiveEntry.setDevice(readBinaryLong(2, z));
        cpioArchiveEntry.setInode(readBinaryLong(2, z));
        long readBinaryLong = readBinaryLong(2, z);
        if (CpioUtil.fileType(readBinaryLong) != 0) {
            cpioArchiveEntry.setMode(readBinaryLong);
        }
        cpioArchiveEntry.setUID(readBinaryLong(2, z));
        cpioArchiveEntry.setGID(readBinaryLong(2, z));
        cpioArchiveEntry.setNumberOfLinks(readBinaryLong(2, z));
        cpioArchiveEntry.setRemoteDevice(readBinaryLong(2, z));
        cpioArchiveEntry.setTime(readBinaryLong(4, z));
        long readBinaryLong2 = readBinaryLong(2, z);
        cpioArchiveEntry.setSize(readBinaryLong(4, z));
        String readCString = readCString((int) readBinaryLong2);
        cpioArchiveEntry.setName(readCString);
        if (CpioUtil.fileType(readBinaryLong) == 0 && !readCString.equals(CpioConstants.CPIO_TRAILER)) {
            throw new IOException("Mode 0 only allowed in the trailer. Found entry: " + ArchiveUtils.sanitize(readCString) + "Occured at byte: " + getBytesRead());
        }
        skip(cpioArchiveEntry.getHeaderPadCount());
        return cpioArchiveEntry;
    }

    private String readCString(int i) throws IOException {
        int i2 = i - 1;
        byte[] bArr = new byte[i2];
        readFully(bArr, 0, i2);
        this.in.read();
        return this.zipEncoding.decode(bArr);
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("negative skip length");
        }
        ensureOpen();
        int min = (int) Math.min(j, 2147483647L);
        int i = 0;
        while (true) {
            if (i >= min) {
                break;
            }
            int i2 = min - i;
            byte[] bArr = this.tmpbuf;
            if (i2 > bArr.length) {
                i2 = bArr.length;
            }
            int read = read(bArr, 0, i2);
            if (read == -1) {
                this.entryEOF = true;
                break;
            }
            i += read;
        }
        return i;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public ArchiveEntry getNextEntry() throws IOException {
        return getNextCPIOEntry();
    }

    private void skipRemainderOfLastBlock() throws IOException {
        long bytesRead = getBytesRead();
        int i = this.blockSize;
        long j = bytesRead % i;
        long j2 = j == 0 ? 0L : i - j;
        while (j2 > 0) {
            long skip = skip(this.blockSize - j);
            if (skip <= 0) {
                return;
            }
            j2 -= skip;
        }
    }

    public static boolean matches(byte[] bArr, int i) {
        if (i < 6) {
            return false;
        }
        if (bArr[0] == 113 && (bArr[1] & 255) == 199) {
            return true;
        }
        if (bArr[1] == 113 && (bArr[0] & 255) == 199) {
            return true;
        }
        if (bArr[0] == 48 && bArr[1] == 55 && bArr[2] == 48 && bArr[3] == 55 && bArr[4] == 48) {
            return bArr[5] == 49 || bArr[5] == 50 || bArr[5] == 55;
        }
        return false;
    }
}
