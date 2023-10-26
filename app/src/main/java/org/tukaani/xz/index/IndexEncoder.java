package org.tukaani.xz.index;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import org.tukaani.xz.XZIOException;
import org.tukaani.xz.common.EncoderUtil;

/* loaded from: classes3.dex */
public class IndexEncoder extends IndexBase {
    private final ArrayList records;

    public IndexEncoder() {
        super(new XZIOException("XZ Stream or its Index has grown too big"));
        this.records = new ArrayList();
    }

    @Override // org.tukaani.xz.index.IndexBase
    public void add(long j, long j2) throws XZIOException {
        super.add(j, j2);
        this.records.add(new IndexRecord(j, j2));
    }

    public void encode(OutputStream outputStream) throws IOException {
        CRC32 crc32 = new CRC32();
        CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, crc32);
        checkedOutputStream.write(0);
        EncoderUtil.encodeVLI(checkedOutputStream, this.recordCount);
        Iterator it = this.records.iterator();
        while (it.hasNext()) {
            IndexRecord indexRecord = (IndexRecord) it.next();
            EncoderUtil.encodeVLI(checkedOutputStream, indexRecord.unpadded);
            EncoderUtil.encodeVLI(checkedOutputStream, indexRecord.uncompressed);
        }
        for (int indexPaddingSize = getIndexPaddingSize(); indexPaddingSize > 0; indexPaddingSize--) {
            checkedOutputStream.write(0);
        }
        long value = crc32.getValue();
        for (int i = 0; i < 4; i++) {
            outputStream.write((byte) (value >>> (i * 8)));
        }
    }
}
