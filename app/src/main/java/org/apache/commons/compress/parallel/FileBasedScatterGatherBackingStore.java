package org.apache.commons.compress.parallel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class FileBasedScatterGatherBackingStore implements ScatterGatherBackingStore {
    private boolean closed;
    private final FileOutputStream os;
    private final File target;

    public FileBasedScatterGatherBackingStore(File file) throws FileNotFoundException {
        this.target = file;
        this.os = new FileOutputStream(file);
    }

    @Override // org.apache.commons.compress.parallel.ScatterGatherBackingStore
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.target);
    }

    @Override // org.apache.commons.compress.parallel.ScatterGatherBackingStore
    public void closeForWriting() throws IOException {
        if (this.closed) {
            return;
        }
        this.os.close();
        this.closed = true;
    }

    @Override // org.apache.commons.compress.parallel.ScatterGatherBackingStore
    public void writeOut(byte[] bArr, int i, int i2) throws IOException {
        this.os.write(bArr, i, i2);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        closeForWriting();
        this.target.delete();
    }
}
