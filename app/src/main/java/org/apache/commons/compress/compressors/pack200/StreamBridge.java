package org.apache.commons.compress.compressors.pack200;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes3.dex */
abstract class StreamBridge extends FilterOutputStream {
    private final Object INPUT_LOCK;
    private InputStream input;

    abstract InputStream getInputView() throws IOException;

    /* JADX INFO: Access modifiers changed from: protected */
    public StreamBridge(OutputStream outputStream) {
        super(outputStream);
        this.INPUT_LOCK = new Object();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public StreamBridge() {
        this(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public InputStream getInput() throws IOException {
        synchronized (this.INPUT_LOCK) {
            if (this.input == null) {
                this.input = getInputView();
            }
        }
        return this.input;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void stop() throws IOException {
        close();
        synchronized (this.INPUT_LOCK) {
            InputStream inputStream = this.input;
            if (inputStream != null) {
                inputStream.close();
                this.input = null;
            }
        }
    }
}
