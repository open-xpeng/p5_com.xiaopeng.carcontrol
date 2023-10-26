package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes3.dex */
abstract class CoderBase {
    private static final byte[] NONE = new byte[0];
    private final Class<?>[] acceptableOptions;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Object getOptionsFromCoder(Coder coder, InputStream inputStream) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CoderBase(Class<?>... clsArr) {
        this.acceptableOptions = clsArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean canAcceptOptions(Object obj) {
        for (Class<?> cls : this.acceptableOptions) {
            if (cls.isInstance(obj)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public byte[] getOptionsAsProperties(Object obj) {
        return NONE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OutputStream encode(OutputStream outputStream, Object obj) throws IOException {
        throw new UnsupportedOperationException("method doesn't support writing");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static int numberOptionOrDefault(Object obj, int i) {
        return obj instanceof Number ? ((Number) obj).intValue() : i;
    }
}
