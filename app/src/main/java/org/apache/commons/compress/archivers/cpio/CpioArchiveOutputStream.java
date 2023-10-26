package org.apache.commons.compress.archivers.cpio;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.ArchiveUtils;
import org.apache.commons.compress.utils.CharsetNames;

/* loaded from: classes3.dex */
public class CpioArchiveOutputStream extends ArchiveOutputStream implements CpioConstants {
    private final int blockSize;
    private boolean closed;
    private long crc;
    final String encoding;
    private CpioArchiveEntry entry;
    private final short entryFormat;
    private boolean finished;
    private final HashMap<String, CpioArchiveEntry> names;
    private long nextArtificalDeviceAndInode;
    private final OutputStream out;
    private long written;
    private final ZipEncoding zipEncoding;

    public CpioArchiveOutputStream(OutputStream outputStream, short s) {
        this(outputStream, s, 512, CharsetNames.US_ASCII);
    }

    public CpioArchiveOutputStream(OutputStream outputStream, short s, int i) {
        this(outputStream, s, i, CharsetNames.US_ASCII);
    }

    public CpioArchiveOutputStream(OutputStream outputStream, short s, int i, String str) {
        this.closed = false;
        this.names = new HashMap<>();
        this.crc = 0L;
        this.nextArtificalDeviceAndInode = 1L;
        this.out = outputStream;
        if (s != 1 && s != 2 && s != 4 && s != 8) {
            throw new IllegalArgumentException("Unknown format: " + ((int) s));
        }
        this.entryFormat = s;
        this.blockSize = i;
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
    }

    public CpioArchiveOutputStream(OutputStream outputStream) {
        this(outputStream, (short) 1);
    }

    public CpioArchiveOutputStream(OutputStream outputStream, String str) {
        this(outputStream, (short) 1, 512, str);
    }

    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        CpioArchiveEntry cpioArchiveEntry = (CpioArchiveEntry) archiveEntry;
        ensureOpen();
        if (this.entry != null) {
            closeArchiveEntry();
        }
        if (cpioArchiveEntry.getTime() == -1) {
            cpioArchiveEntry.setTime(System.currentTimeMillis() / 1000);
        }
        short format = cpioArchiveEntry.getFormat();
        if (format != this.entryFormat) {
            throw new IOException("Header format: " + ((int) format) + " does not match existing format: " + ((int) this.entryFormat));
        }
        if (this.names.put(cpioArchiveEntry.getName(), cpioArchiveEntry) != null) {
            throw new IOException("duplicate entry: " + cpioArchiveEntry.getName());
        }
        writeHeader(cpioArchiveEntry);
        this.entry = cpioArchiveEntry;
        this.written = 0L;
    }

    private void writeHeader(CpioArchiveEntry cpioArchiveEntry) throws IOException {
        short format = cpioArchiveEntry.getFormat();
        if (format == 1) {
            this.out.write(ArchiveUtils.toAsciiBytes(CpioConstants.MAGIC_NEW));
            count(6);
            writeNewEntry(cpioArchiveEntry);
        } else if (format == 2) {
            this.out.write(ArchiveUtils.toAsciiBytes(CpioConstants.MAGIC_NEW_CRC));
            count(6);
            writeNewEntry(cpioArchiveEntry);
        } else if (format == 4) {
            this.out.write(ArchiveUtils.toAsciiBytes(CpioConstants.MAGIC_OLD_ASCII));
            count(6);
            writeOldAsciiEntry(cpioArchiveEntry);
        } else if (format == 8) {
            writeBinaryLong(29127L, 2, true);
            writeOldBinaryEntry(cpioArchiveEntry, true);
        } else {
            throw new IOException("unknown format " + ((int) cpioArchiveEntry.getFormat()));
        }
    }

    private void writeNewEntry(CpioArchiveEntry cpioArchiveEntry) throws IOException {
        long inode = cpioArchiveEntry.getInode();
        long deviceMin = cpioArchiveEntry.getDeviceMin();
        if (CpioConstants.CPIO_TRAILER.equals(cpioArchiveEntry.getName())) {
            inode = 0;
            deviceMin = 0;
        } else if (inode == 0 && deviceMin == 0) {
            long j = this.nextArtificalDeviceAndInode;
            this.nextArtificalDeviceAndInode = 1 + j;
            deviceMin = (-1) & (j >> 32);
            inode = j & (-1);
        } else {
            this.nextArtificalDeviceAndInode = Math.max(this.nextArtificalDeviceAndInode, (4294967296L * deviceMin) + inode) + 1;
        }
        writeAsciiLong(inode, 8, 16);
        writeAsciiLong(cpioArchiveEntry.getMode(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getUID(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getGID(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getNumberOfLinks(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getTime(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getSize(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getDeviceMaj(), 8, 16);
        writeAsciiLong(deviceMin, 8, 16);
        writeAsciiLong(cpioArchiveEntry.getRemoteDeviceMaj(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getRemoteDeviceMin(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getName().length() + 1, 8, 16);
        writeAsciiLong(cpioArchiveEntry.getChksum(), 8, 16);
        writeCString(cpioArchiveEntry.getName());
        pad(cpioArchiveEntry.getHeaderPadCount());
    }

    private void writeOldAsciiEntry(CpioArchiveEntry cpioArchiveEntry) throws IOException {
        long inode = cpioArchiveEntry.getInode();
        long device = cpioArchiveEntry.getDevice();
        if (CpioConstants.CPIO_TRAILER.equals(cpioArchiveEntry.getName())) {
            inode = 0;
            device = 0;
        } else if (inode == 0 && device == 0) {
            long j = this.nextArtificalDeviceAndInode;
            this.nextArtificalDeviceAndInode = 1 + j;
            device = 262143 & (j >> 18);
            inode = j & 262143;
        } else {
            this.nextArtificalDeviceAndInode = Math.max(this.nextArtificalDeviceAndInode, (PlaybackStateCompat.ACTION_SET_REPEAT_MODE * device) + inode) + 1;
        }
        writeAsciiLong(device, 6, 8);
        writeAsciiLong(inode, 6, 8);
        writeAsciiLong(cpioArchiveEntry.getMode(), 6, 8);
        writeAsciiLong(cpioArchiveEntry.getUID(), 6, 8);
        writeAsciiLong(cpioArchiveEntry.getGID(), 6, 8);
        writeAsciiLong(cpioArchiveEntry.getNumberOfLinks(), 6, 8);
        writeAsciiLong(cpioArchiveEntry.getRemoteDevice(), 6, 8);
        writeAsciiLong(cpioArchiveEntry.getTime(), 11, 8);
        writeAsciiLong(cpioArchiveEntry.getName().length() + 1, 6, 8);
        writeAsciiLong(cpioArchiveEntry.getSize(), 11, 8);
        writeCString(cpioArchiveEntry.getName());
    }

    private void writeOldBinaryEntry(CpioArchiveEntry cpioArchiveEntry, boolean z) throws IOException {
        long inode = cpioArchiveEntry.getInode();
        long device = cpioArchiveEntry.getDevice();
        if (CpioConstants.CPIO_TRAILER.equals(cpioArchiveEntry.getName())) {
            inode = 0;
            device = 0;
        } else if (inode == 0 && device == 0) {
            long j = this.nextArtificalDeviceAndInode;
            this.nextArtificalDeviceAndInode = 1 + j;
            device = 65535 & (j >> 16);
            inode = j & 65535;
        } else {
            this.nextArtificalDeviceAndInode = Math.max(this.nextArtificalDeviceAndInode, (PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH * device) + inode) + 1;
        }
        writeBinaryLong(device, 2, z);
        writeBinaryLong(inode, 2, z);
        writeBinaryLong(cpioArchiveEntry.getMode(), 2, z);
        writeBinaryLong(cpioArchiveEntry.getUID(), 2, z);
        writeBinaryLong(cpioArchiveEntry.getGID(), 2, z);
        writeBinaryLong(cpioArchiveEntry.getNumberOfLinks(), 2, z);
        writeBinaryLong(cpioArchiveEntry.getRemoteDevice(), 2, z);
        writeBinaryLong(cpioArchiveEntry.getTime(), 4, z);
        writeBinaryLong(cpioArchiveEntry.getName().length() + 1, 2, z);
        writeBinaryLong(cpioArchiveEntry.getSize(), 4, z);
        writeCString(cpioArchiveEntry.getName());
        pad(cpioArchiveEntry.getHeaderPadCount());
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        ensureOpen();
        CpioArchiveEntry cpioArchiveEntry = this.entry;
        if (cpioArchiveEntry == null) {
            throw new IOException("Trying to close non-existent entry");
        }
        if (cpioArchiveEntry.getSize() != this.written) {
            throw new IOException("invalid entry size (expected " + this.entry.getSize() + " but got " + this.written + " bytes)");
        }
        pad(this.entry.getDataPadCount());
        if (this.entry.getFormat() == 2 && this.crc != this.entry.getChksum()) {
            throw new IOException("CRC Error");
        }
        this.entry = null;
        this.crc = 0L;
        this.written = 0L;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        ensureOpen();
        if (i < 0 || i2 < 0 || i > bArr.length - i2) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == 0) {
            return;
        }
        CpioArchiveEntry cpioArchiveEntry = this.entry;
        if (cpioArchiveEntry == null) {
            throw new IOException("no current CPIO entry");
        }
        long j = i2;
        if (this.written + j > cpioArchiveEntry.getSize()) {
            throw new IOException("attempt to write past end of STORED entry");
        }
        this.out.write(bArr, i, i2);
        this.written += j;
        if (this.entry.getFormat() == 2) {
            for (int i3 = 0; i3 < i2; i3++) {
                this.crc += bArr[i3] & 255;
            }
        }
        count(i2);
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void finish() throws IOException {
        ensureOpen();
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        if (this.entry != null) {
            throw new IOException("This archive contains unclosed entries.");
        }
        CpioArchiveEntry cpioArchiveEntry = new CpioArchiveEntry(this.entryFormat);
        this.entry = cpioArchiveEntry;
        cpioArchiveEntry.setName(CpioConstants.CPIO_TRAILER);
        this.entry.setNumberOfLinks(1L);
        writeHeader(this.entry);
        closeArchiveEntry();
        long bytesWritten = getBytesWritten();
        int i = this.blockSize;
        int i2 = (int) (bytesWritten % i);
        if (i2 != 0) {
            pad(i - i2);
        }
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

    private void pad(int i) throws IOException {
        if (i > 0) {
            this.out.write(new byte[i]);
            count(i);
        }
    }

    private void writeBinaryLong(long j, int i, boolean z) throws IOException {
        byte[] long2byteArray = CpioUtil.long2byteArray(j, i, z);
        this.out.write(long2byteArray);
        count(long2byteArray.length);
    }

    private void writeAsciiLong(long j, int i, int i2) throws IOException {
        String substring;
        StringBuilder sb = new StringBuilder();
        if (i2 == 16) {
            sb.append(Long.toHexString(j));
        } else if (i2 == 8) {
            sb.append(Long.toOctalString(j));
        } else {
            sb.append(Long.toString(j));
        }
        if (sb.length() <= i) {
            long length = i - sb.length();
            for (int i3 = 0; i3 < length; i3++) {
                sb.insert(0, "0");
            }
            substring = sb.toString();
        } else {
            substring = sb.substring(sb.length() - i);
        }
        byte[] asciiBytes = ArchiveUtils.toAsciiBytes(substring);
        this.out.write(asciiBytes);
        count(asciiBytes.length);
    }

    private void writeCString(String str) throws IOException {
        ByteBuffer encode = this.zipEncoding.encode(str);
        int limit = encode.limit() - encode.position();
        this.out.write(encode.array(), encode.arrayOffset(), limit);
        this.out.write(0);
        count(limit + 1);
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public ArchiveEntry createArchiveEntry(File file, String str) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new CpioArchiveEntry(file, str);
    }
}
