package zipkin2.storage;

import java.util.List;
import zipkin2.Call;
import zipkin2.Span;

/* loaded from: classes3.dex */
public interface SpanConsumer {
    Call<Void> accept(List<Span> list);
}
