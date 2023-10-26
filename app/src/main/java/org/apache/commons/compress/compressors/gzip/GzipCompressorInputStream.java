package org.apache.commons.compress.compressors.gzip;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.utils.CharsetNames;

/* loaded from: classes3.dex */
public class GzipCompressorInputStream extends CompressorInputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int FCOMMENT = 16;
    private static final int FEXTRA = 4;
    private static final int FHCRC = 2;
    private static final int FNAME = 8;
    private static final int FRESERVED = 224;
    private final byte[] buf;
    private int bufUsed;
    private final CRC32 crc;
    private final boolean decompressConcatenated;
    private boolean endReached;
    private final InputStream in;
    private Inflater inf;
    private final byte[] oneByte;
    private final GzipParameters parameters;

    public GzipCompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, false);
    }

    public GzipCompressorInputStream(InputStream inputStream, boolean z) throws IOException {
        this.buf = new byte[8192];
        this.bufUsed = 0;
        this.inf = new Inflater(true);
        this.crc = new CRC32();
        this.endReached = false;
        this.oneByte = new byte[1];
        this.parameters = new GzipParameters();
        if (inputStream.markSupported()) {
            this.in = inputStream;
        } else {
            this.in = new BufferedInputStream(inputStream);
        }
        this.decompressConcatenated = z;
        init(true);
    }

    public GzipParameters getMetaData() {
        return this.parameters;
    }

    private boolean init(boolean z) throws IOException {
        int read = this.in.read();
        int read2 = this.in.read();
        if (read != -1 || z) {
            if (read != 31 || read2 != 139) {
                throw new IOException(z ? "Input is not in the .gz format" : "Garbage after a valid .gz stream");
            }
            DataInputStream dataInputStream = new DataInputStream(this.in);
            int readUnsignedByte = dataInputStream.readUnsignedByte();
            if (readUnsignedByte != 8) {
                throw new IOException("Unsupported compression method " + readUnsignedByte + " in the .gz header");
            }
            int readUnsignedByte2 = dataInputStream.readUnsignedByte();
            if ((readUnsignedByte2 & FRESERVED) != 0) {
                throw new IOException("Reserved flags are set in the .gz header");
            }
            this.parameters.setModificationTime(readLittleEndianInt(dataInputStream) * 1000);
            int readUnsignedByte3 = dataInputStream.readUnsignedByte();
            if (readUnsignedByte3 == 2) {
                this.parameters.setCompressionLevel(9);
            } else if (readUnsignedByte3 == 4) {
                this.parameters.setCompressionLevel(1);
            }
            this.parameters.setOperatingSystem(dataInputStream.readUnsignedByte());
            if ((readUnsignedByte2 & 4) != 0) {
                int readUnsignedByte4 = (dataInputStream.readUnsignedByte() << 8) | dataInputStream.readUnsignedByte();
                while (true) {
                    int i = readUnsignedByte4 - 1;
                    if (readUnsignedByte4 <= 0) {
                        break;
                    }
                    dataInputStream.readUnsignedByte();
                    readUnsignedByte4 = i;
                }
            }
            if ((readUnsignedByte2 & 8) != 0) {
                this.parameters.setFilename(new String(readToNull(dataInputStream), CharsetNames.ISO_8859_1));
            }
            if ((readUnsignedByte2 & 16) != 0) {
                this.parameters.setComment(new String(readToNull(dataInputStream), CharsetNames.ISO_8859_1));
            }
            if ((readUnsignedByte2 & 2) != 0) {
                dataInputStream.readShort();
            }
            this.inf.reset();
            this.crc.reset();
            return true;
        }
        return false;
    }

    private byte[] readToNull(DataInputStream dataInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int readUnsignedByte = dataInputStream.readUnsignedByte();
            if (readUnsignedByte != 0) {
                byteArrayOutputStream.write(readUnsignedByte);
            } else {
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    private long readLittleEndianInt(DataInputStream dataInputStream) throws IOException {
        return dataInputStream.readUnsignedByte() | (dataInputStream.readUnsignedByte() << 8) | (dataInputStream.readUnsignedByte() << 16) | (dataInputStream.readUnsignedByte() << 24);
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.oneByte, 0, 1) == -1) {
            return -1;
        }
        return this.oneByte[0] & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.endReached) {
            return -1;
        }
        int i3 = 0;
        while (i2 > 0) {
            if (this.inf.needsInput()) {
                this.in.mark(this.buf.length);
                int read = this.in.read(this.buf);
                this.bufUsed = read;
                if (read == -1) {
                    throw new EOFException();
                }
                this.inf.setInput(this.buf, 0, read);
            }
            try {
                int inflate = this.inf.inflate(bArr, i, i2);
                this.crc.update(bArr, i, inflate);
                i += inflate;
                i2 -= inflate;
                i3 += inflate;
                count(inflate);
                if (this.inf.finished()) {
                    this.in.reset();
                    long remaining = this.bufUsed - this.inf.getRemaining();
                    if (this.in.skip(remaining) != remaining) {
                        throw new IOException();
                    }
                    this.bufUsed = 0;
                    DataInputStream dataInputStream = new DataInputStream(this.in);
                    if (readLittleEndianInt(dataInputStream) != this.crc.getValue()) {
                        throw new IOException("Gzip-compressed data is corrupt (CRC32 error)");
                    }
                    if (readLittleEndianInt(dataInputStream) != (this.inf.getBytesWritten() & 4294967295L)) {
                        throw new IOException("Gzip-compressed data is corrupt(uncompressed size mismatch)");
                    }
                    if (!this.decompressConcatenated || !init(false)) {
                        this.inf.end();
                        this.inf = null;
                        this.endReached = true;
                        if (i3 == 0) {
                            return -1;
                        }
                        return i3;
                    }
                }
            } catch (DataFormatException unused) {
                throw new IOException("Gzip-compressed data is corrupt");
            }
        }
        return i3;
    }

    public static boolean matches(byte[] bArr, int i) {
        return i >= 2 && bArr[0] == 31 && bArr[1] == -117;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Inflater inflater = this.inf;
        if (inflater != null) {
            inflater.end();
            this.inf = null;
        }
        if (this.in != System.in) {
            this.in.close();
        }
    }
}
