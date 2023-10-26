package android.support.rastermill.data;

import android.support.rastermill.data.LazyHeaders;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes.dex */
public interface Headers {
    @Deprecated
    public static final Headers NONE = new Headers() { // from class: android.support.rastermill.data.Headers.1
        @Override // android.support.rastermill.data.Headers
        public Map<String, String> getHeaders() {
            return Collections.emptyMap();
        }
    };
    public static final Headers DEFAULT = new LazyHeaders.Builder().build();

    Map<String, String> getHeaders();
}
