package org.apache.commons.compress.compressors.pack200;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
class InMemoryCachingStreamBridge extends StreamBridge {
    /* JADX INFO: Access modifiers changed from: package-private */
    public InMemoryCachingStreamBridge() {
        super(new ByteArrayOutputStream());
    }

    @Override // org.apache.commons.compress.compressors.pack200.StreamBridge
    InputStream getInputView() throws IOException {
        return new ByteArrayInputStream(((ByteArrayOutputStream) this.out).toByteArray());
    }
}
