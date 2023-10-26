package okhttp3;

import java.io.IOException;

/* loaded from: classes3.dex */
public interface Call extends Cloneable {

    /* loaded from: classes3.dex */
    public interface Factory {
        Call newCall(Request request);
    }

    void cancel();

    Call clone();

    void enqueue(Callback callback);

    Response execute() throws IOException;

    boolean isCanceled();

    boolean isExecuted();

    Request request();
}
