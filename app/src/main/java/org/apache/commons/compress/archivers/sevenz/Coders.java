package org.apache.commons.compress.archivers.sevenz;

import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.tukaani.xz.ARMOptions;
import org.tukaani.xz.ARMThumbOptions;
import org.tukaani.xz.FilterOptions;
import org.tukaani.xz.FinishableWrapperOutputStream;
import org.tukaani.xz.IA64Options;
import org.tukaani.xz.LZMAInputStream;
import org.tukaani.xz.PowerPCOptions;
import org.tukaani.xz.SPARCOptions;
import org.tukaani.xz.X86Options;

/* loaded from: classes3.dex */
class Coders {
    private static final Map<SevenZMethod, CoderBase> CODER_MAP = new HashMap<SevenZMethod, CoderBase>() { // from class: org.apache.commons.compress.archivers.sevenz.Coders.1
        private static final long serialVersionUID = 1664829131806520867L;

        {
            put(SevenZMethod.COPY, new CopyDecoder());
            put(SevenZMethod.LZMA, new LZMADecoder());
            put(SevenZMethod.LZMA2, new LZMA2Decoder());
            put(SevenZMethod.DEFLATE, new DeflateDecoder());
            put(SevenZMethod.BZIP2, new BZIP2Decoder());
            put(SevenZMethod.AES256SHA256, new AES256SHA256Decoder());
            put(SevenZMethod.BCJ_X86_FILTER, new BCJDecoder(new X86Options()));
            put(SevenZMethod.BCJ_PPC_FILTER, new BCJDecoder(new PowerPCOptions()));
            put(SevenZMethod.BCJ_IA64_FILTER, new BCJDecoder(new IA64Options()));
            put(SevenZMethod.BCJ_ARM_FILTER, new BCJDecoder(new ARMOptions()));
            put(SevenZMethod.BCJ_ARM_THUMB_FILTER, new BCJDecoder(new ARMThumbOptions()));
            put(SevenZMethod.BCJ_SPARC_FILTER, new BCJDecoder(new SPARCOptions()));
            put(SevenZMethod.DELTA_FILTER, new DeltaDecoder());
        }
    };

    Coders() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static CoderBase findByMethod(SevenZMethod sevenZMethod) {
        return CODER_MAP.get(sevenZMethod);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static InputStream addDecoder(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
        CoderBase findByMethod = findByMethod(SevenZMethod.byId(coder.decompressionMethodId));
        if (findByMethod == null) {
            throw new IOException("Unsupported compression method " + Arrays.toString(coder.decompressionMethodId) + " used in " + str);
        }
        return findByMethod.decode(str, inputStream, j, coder, bArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static OutputStream addEncoder(OutputStream outputStream, SevenZMethod sevenZMethod, Object obj) throws IOException {
        CoderBase findByMethod = findByMethod(sevenZMethod);
        if (findByMethod == null) {
            throw new IOException("Unsupported compression method " + sevenZMethod);
        }
        return findByMethod.encode(outputStream, obj);
    }

    /* loaded from: classes3.dex */
    static class CopyDecoder extends CoderBase {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
            return inputStream;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public OutputStream encode(OutputStream outputStream, Object obj) {
            return outputStream;
        }

        CopyDecoder() {
            super(new Class[0]);
        }
    }

    /* loaded from: classes3.dex */
    static class LZMADecoder extends CoderBase {
        LZMADecoder() {
            super(new Class[0]);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
            int i;
            byte b = coder.properties[0];
            long j2 = coder.properties[1];
            for (int i2 = 1; i2 < 4; i2++) {
                j2 |= (coder.properties[i] & 255) << (i2 * 8);
            }
            if (j2 > 2147483632) {
                throw new IOException("Dictionary larger than 4GiB maximum size used in " + str);
            }
            return new LZMAInputStream(inputStream, j, b, (int) j2);
        }
    }

    /* loaded from: classes3.dex */
    static class BCJDecoder extends CoderBase {
        private final FilterOptions opts;

        BCJDecoder(FilterOptions filterOptions) {
            super(new Class[0]);
            this.opts = filterOptions;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
            try {
                return this.opts.getInputStream(inputStream);
            } catch (AssertionError e) {
                throw new IOException("BCJ filter used in " + str + " needs XZ for Java > 1.4 - see http://commons.apache.org/proper/commons-compress/limitations.html#7Z", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public OutputStream encode(OutputStream outputStream, Object obj) {
            return new FilterOutputStream(this.opts.getOutputStream(new FinishableWrapperOutputStream(outputStream))) { // from class: org.apache.commons.compress.archivers.sevenz.Coders.BCJDecoder.1
                @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
                public void flush() {
                }
            };
        }
    }

    /* loaded from: classes3.dex */
    static class DeflateDecoder extends CoderBase {
        DeflateDecoder() {
            super(Number.class);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
            final Inflater inflater = new Inflater(true);
            final InflaterInputStream inflaterInputStream = new InflaterInputStream(new DummyByteAddingInputStream(inputStream), inflater);
            return new InputStream() { // from class: org.apache.commons.compress.archivers.sevenz.Coders.DeflateDecoder.1
                @Override // java.io.InputStream
                public int read() throws IOException {
                    return inflaterInputStream.read();
                }

                @Override // java.io.InputStream
                public int read(byte[] bArr2, int i, int i2) throws IOException {
                    return inflaterInputStream.read(bArr2, i, i2);
                }

                @Override // java.io.InputStream
                public int read(byte[] bArr2) throws IOException {
                    return inflaterInputStream.read(bArr2);
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    try {
                        inflaterInputStream.close();
                    } finally {
                        inflater.end();
                    }
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public OutputStream encode(OutputStream outputStream, Object obj) {
            final Deflater deflater = new Deflater(numberOptionOrDefault(obj, 9), true);
            final DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(outputStream, deflater);
            return new OutputStream() { // from class: org.apache.commons.compress.archivers.sevenz.Coders.DeflateDecoder.2
                @Override // java.io.OutputStream
                public void write(int i) throws IOException {
                    deflaterOutputStream.write(i);
                }

                @Override // java.io.OutputStream
                public void write(byte[] bArr) throws IOException {
                    deflaterOutputStream.write(bArr);
                }

                @Override // java.io.OutputStream
                public void write(byte[] bArr, int i, int i2) throws IOException {
                    deflaterOutputStream.write(bArr, i, i2);
                }

                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    try {
                        deflaterOutputStream.close();
                    } finally {
                        deflater.end();
                    }
                }
            };
        }
    }

    /* loaded from: classes3.dex */
    static class BZIP2Decoder extends CoderBase {
        BZIP2Decoder() {
            super(Number.class);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
            return new BZip2CompressorInputStream(inputStream);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public OutputStream encode(OutputStream outputStream, Object obj) throws IOException {
            return new BZip2CompressorOutputStream(outputStream, numberOptionOrDefault(obj, 9));
        }
    }

    /* loaded from: classes3.dex */
    private static class DummyByteAddingInputStream extends FilterInputStream {
        private boolean addDummyByte;

        private DummyByteAddingInputStream(InputStream inputStream) {
            super(inputStream);
            this.addDummyByte = true;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read() throws IOException {
            int read = super.read();
            if (read == -1 && this.addDummyByte) {
                this.addDummyByte = false;
                return 0;
            }
            return read;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int read = super.read(bArr, i, i2);
            if (read == -1 && this.addDummyByte) {
                this.addDummyByte = false;
                bArr[i] = 0;
                return 1;
            }
            return read;
        }
    }
}
