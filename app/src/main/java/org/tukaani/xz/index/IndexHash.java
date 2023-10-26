package org.tukaani.xz.index;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.CheckedInputStream;
import org.tukaani.xz.CorruptedInputException;
import org.tukaani.xz.XZIOException;
import org.tukaani.xz.check.CRC32;
import org.tukaani.xz.check.Check;
import org.tukaani.xz.check.SHA256;
import org.tukaani.xz.common.DecoderUtil;

/* loaded from: classes3.dex */
public class IndexHash extends IndexBase {
    private Check hash;

    public IndexHash() {
        super(new CorruptedInputException());
        try {
            this.hash = new SHA256();
        } catch (NoSuchAlgorithmException unused) {
            this.hash = new CRC32();
        }
    }

    @Override // org.tukaani.xz.index.IndexBase
    public void add(long j, long j2) throws XZIOException {
        super.add(j, j2);
        ByteBuffer allocate = ByteBuffer.allocate(16);
        allocate.putLong(j);
        allocate.putLong(j2);
        this.hash.update(allocate.array());
    }

    public void validate(InputStream inputStream) throws IOException {
        java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
        crc32.update(0);
        CheckedInputStream checkedInputStream = new CheckedInputStream(inputStream, crc32);
        if (DecoderUtil.decodeVLI(checkedInputStream) != this.recordCount) {
            throw new CorruptedInputException("XZ Index is corrupt");
        }
        IndexHash indexHash = new IndexHash();
        for (long j = 0; j < this.recordCount; j++) {
            try {
                indexHash.add(DecoderUtil.decodeVLI(checkedInputStream), DecoderUtil.decodeVLI(checkedInputStream));
                if (indexHash.blocksSum > this.blocksSum || indexHash.uncompressedSum > this.uncompressedSum || indexHash.indexListSize > this.indexListSize) {
                    throw new CorruptedInputException("XZ Index is corrupt");
                }
            } catch (XZIOException unused) {
                throw new CorruptedInputException("XZ Index is corrupt");
            }
        }
        if (indexHash.blocksSum != this.blocksSum || indexHash.uncompressedSum != this.uncompressedSum || indexHash.indexListSize != this.indexListSize || !Arrays.equals(indexHash.hash.finish(), this.hash.finish())) {
            throw new CorruptedInputException("XZ Index is corrupt");
        }
        DataInputStream dataInputStream = new DataInputStream(checkedInputStream);
        for (int indexPaddingSize = getIndexPaddingSize(); indexPaddingSize > 0; indexPaddingSize--) {
            if (dataInputStream.readUnsignedByte() != 0) {
                throw new CorruptedInputException("XZ Index is corrupt");
            }
        }
        long value = crc32.getValue();
        for (int i = 0; i < 4; i++) {
            if (((value >>> (i * 8)) & 255) != dataInputStream.readUnsignedByte()) {
                throw new CorruptedInputException("XZ Index is corrupt");
            }
        }
    }
}
