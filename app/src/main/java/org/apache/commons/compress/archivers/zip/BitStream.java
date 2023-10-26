package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import org.apache.commons.compress.utils.BitInputStream;

/* loaded from: classes3.dex */
class BitStream extends BitInputStream {
    /* JADX INFO: Access modifiers changed from: package-private */
    public BitStream(InputStream inputStream) {
        super(inputStream, ByteOrder.LITTLE_ENDIAN);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int nextBit() throws IOException {
        return (int) readBits(1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long nextBits(int i) throws IOException {
        return readBits(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int nextByte() throws IOException {
        return (int) readBits(8);
    }
}
