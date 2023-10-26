package org.tukaani.xz;

import java.io.IOException;
import java.io.OutputStream;
import org.tukaani.xz.check.Check;
import org.tukaani.xz.common.EncoderUtil;
import org.tukaani.xz.common.StreamFlags;
import org.tukaani.xz.index.IndexEncoder;

/* loaded from: classes3.dex */
public class XZOutputStream extends FinishableOutputStream {
    private BlockOutputStream blockEncoder;
    private final Check check;
    private IOException exception;
    private FilterEncoder[] filters;
    private boolean filtersSupportFlushing;
    private boolean finished;
    private final IndexEncoder index;
    private OutputStream out;
    private final StreamFlags streamFlags;
    private final byte[] tempBuf;

    public XZOutputStream(OutputStream outputStream, FilterOptions filterOptions) throws IOException {
        this(outputStream, filterOptions, 4);
    }

    public XZOutputStream(OutputStream outputStream, FilterOptions filterOptions, int i) throws IOException {
        this(outputStream, new FilterOptions[]{filterOptions}, i);
    }

    public XZOutputStream(OutputStream outputStream, FilterOptions[] filterOptionsArr) throws IOException {
        this(outputStream, filterOptionsArr, 4);
    }

    public XZOutputStream(OutputStream outputStream, FilterOptions[] filterOptionsArr, int i) throws IOException {
        StreamFlags streamFlags = new StreamFlags();
        this.streamFlags = streamFlags;
        this.index = new IndexEncoder();
        this.blockEncoder = null;
        this.exception = null;
        this.finished = false;
        this.tempBuf = new byte[1];
        this.out = outputStream;
        updateFilters(filterOptionsArr);
        streamFlags.checkType = i;
        this.check = Check.getInstance(i);
        encodeStreamHeader();
    }

    private void encodeStreamFlags(byte[] bArr, int i) {
        bArr[i] = 0;
        bArr[i + 1] = (byte) this.streamFlags.checkType;
    }

    private void encodeStreamFooter() throws IOException {
        byte[] bArr = new byte[6];
        long indexSize = (this.index.getIndexSize() / 4) - 1;
        for (int i = 0; i < 4; i++) {
            bArr[i] = (byte) (indexSize >>> (i * 8));
        }
        encodeStreamFlags(bArr, 4);
        EncoderUtil.writeCRC32(this.out, bArr);
        this.out.write(bArr);
        this.out.write(XZ.FOOTER_MAGIC);
    }

    private void encodeStreamHeader() throws IOException {
        this.out.write(XZ.HEADER_MAGIC);
        byte[] bArr = new byte[2];
        encodeStreamFlags(bArr, 0);
        this.out.write(bArr);
        EncoderUtil.writeCRC32(this.out, bArr);
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.out != null) {
            try {
                finish();
            } catch (IOException unused) {
            }
            try {
                this.out.close();
            } catch (IOException e) {
                if (this.exception == null) {
                    this.exception = e;
                }
            }
            this.out = null;
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
    }

    public void endBlock() throws IOException {
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
        if (this.finished) {
            throw new XZIOException("Stream finished or closed");
        }
        BlockOutputStream blockOutputStream = this.blockEncoder;
        if (blockOutputStream != null) {
            try {
                blockOutputStream.finish();
                this.index.add(this.blockEncoder.getUnpaddedSize(), this.blockEncoder.getUncompressedSize());
                this.blockEncoder = null;
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    @Override // org.tukaani.xz.FinishableOutputStream
    public void finish() throws IOException {
        if (this.finished) {
            return;
        }
        endBlock();
        try {
            this.index.encode(this.out);
            encodeStreamFooter();
            this.finished = true;
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        OutputStream outputStream;
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
        if (this.finished) {
            throw new XZIOException("Stream finished or closed");
        }
        try {
            BlockOutputStream blockOutputStream = this.blockEncoder;
            if (blockOutputStream == null) {
                outputStream = this.out;
            } else if (this.filtersSupportFlushing) {
                blockOutputStream.flush();
                return;
            } else {
                endBlock();
                outputStream = this.out;
            }
            outputStream.flush();
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }

    public void updateFilters(FilterOptions filterOptions) throws XZIOException {
        updateFilters(new FilterOptions[]{filterOptions});
    }

    public void updateFilters(FilterOptions[] filterOptionsArr) throws XZIOException {
        if (this.blockEncoder != null) {
            throw new UnsupportedOptionsException("Changing filter options in the middle of a XZ Block not implemented");
        }
        if (filterOptionsArr.length < 1 || filterOptionsArr.length > 4) {
            throw new UnsupportedOptionsException("XZ filter chain must be 1-4 filters");
        }
        this.filtersSupportFlushing = true;
        FilterEncoder[] filterEncoderArr = new FilterEncoder[filterOptionsArr.length];
        for (int i = 0; i < filterOptionsArr.length; i++) {
            filterEncoderArr[i] = filterOptionsArr[i].getFilterEncoder();
            this.filtersSupportFlushing &= filterEncoderArr[i].supportsFlushing();
        }
        RawCoder.validate(filterEncoderArr);
        this.filters = filterEncoderArr;
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        byte[] bArr = this.tempBuf;
        bArr[0] = (byte) i;
        write(bArr, 0, 1);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        if (i < 0 || i2 < 0 || (i3 = i + i2) < 0 || i3 > bArr.length) {
            throw new IndexOutOfBoundsException();
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
        if (this.finished) {
            throw new XZIOException("Stream finished or closed");
        }
        try {
            if (this.blockEncoder == null) {
                this.blockEncoder = new BlockOutputStream(this.out, this.filters, this.check);
            }
            this.blockEncoder.write(bArr, i, i2);
        } catch (IOException e) {
            this.exception = e;
            throw e;
        }
    }
}
