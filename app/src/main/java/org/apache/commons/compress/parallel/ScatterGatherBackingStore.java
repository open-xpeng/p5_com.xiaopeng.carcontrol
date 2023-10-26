package org.apache.commons.compress.parallel;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public interface ScatterGatherBackingStore extends Closeable {
    void closeForWriting() throws IOException;

    InputStream getInputStream() throws IOException;

    void writeOut(byte[] bArr, int i, int i2) throws IOException;
}
