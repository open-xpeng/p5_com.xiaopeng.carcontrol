package android.support.rastermill.data;

import java.io.OutputStream;

/* loaded from: classes.dex */
public interface Encoder<T> {
    boolean encode(T t, OutputStream outputStream);

    String getId();
}
