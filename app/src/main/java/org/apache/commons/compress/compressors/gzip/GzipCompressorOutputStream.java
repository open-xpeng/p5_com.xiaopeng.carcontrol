package org.apache.commons.compress.compressors.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.utils.CharsetNames;

/* loaded from: classes3.dex */
public class GzipCompressorOutputStream extends CompressorOutputStream {
    private static final int FCOMMENT = 16;
    private static final int FNAME = 8;
    private boolean closed;
    private final CRC32 crc;
    private final byte[] deflateBuffer;
    private final Deflater deflater;
    private final OutputStream out;

    public GzipCompressorOutputStream(OutputStream outputStream) throws IOException {
        this(outputStream, new GzipParameters());
    }

    public GzipCompressorOutputStream(OutputStream outputStream, GzipParameters gzipParameters) throws IOException {
        this.deflateBuffer = new byte[512];
        this.crc = new CRC32();
        this.out = outputStream;
        this.deflater = new Deflater(gzipParameters.getCompressionLevel(), true);
        writeHeader(gzipParameters);
    }

    private void writeHeader(GzipParameters gzipParameters) throws IOException {
        String filename = gzipParameters.getFilename();
        String comment = gzipParameters.getComment();
        ByteBuffer allocate = ByteBuffer.allocate(10);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.putShort((short) -29921);
        allocate.put((byte) 8);
        allocate.put((byte) ((filename == null ? 0 : 8) | (comment != null ? 16 : 0)));
        allocate.putInt((int) (gzipParameters.getModificationTime() / 1000));
        int compressionLevel = gzipParameters.getCompressionLevel();
        if (compressionLevel == 9) {
            allocate.put((byte) 2);
        } else if (compressionLevel == 1) {
            allocate.put((byte) 4);
        } else {
            allocate.put((byte) 0);
        }
        allocate.put((byte) gzipParameters.getOperatingSystem());
        this.out.write(allocate.array());
        if (filename != null) {
            this.out.write(filename.getBytes(CharsetNames.ISO_8859_1));
            this.out.write(0);
        }
        if (comment != null) {
            this.out.write(comment.getBytes(CharsetNames.ISO_8859_1));
            this.out.write(0);
        }
    }

    private void writeTrailer() throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.putInt((int) this.crc.getValue());
        allocate.putInt(this.deflater.getTotalIn());
        this.out.write(allocate.array());
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        write(new byte[]{(byte) (i & 255)}, 0, 1);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (this.deflater.finished()) {
            throw new IOException("Cannot write more data, the end of the compressed data stream has been reached");
        }
        if (i2 > 0) {
            this.deflater.setInput(bArr, i, i2);
            while (!this.deflater.needsInput()) {
                deflate();
            }
            this.crc.update(bArr, i, i2);
        }
    }

    private void deflate() throws IOException {
        Deflater deflater = this.deflater;
        byte[] bArr = this.deflateBuffer;
        int deflate = deflater.deflate(bArr, 0, bArr.length);
        if (deflate > 0) {
            this.out.write(this.deflateBuffer, 0, deflate);
        }
    }

    public void finish() throws IOException {
        if (this.deflater.finished()) {
            return;
        }
        this.deflater.finish();
        while (!this.deflater.finished()) {
            deflate();
        }
        writeTrailer();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        finish();
        this.deflater.end();
        this.out.close();
        this.closed = true;
    }
}
