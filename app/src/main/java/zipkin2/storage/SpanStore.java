package zipkin2.storage;

import java.util.List;
import zipkin2.Call;
import zipkin2.DependencyLink;
import zipkin2.Span;

/* loaded from: classes3.dex */
public interface SpanStore {
    Call<List<DependencyLink>> getDependencies(long j, long j2);

    Call<List<String>> getServiceNames();

    Call<List<String>> getSpanNames(String str);

    Call<List<Span>> getTrace(String str);

    Call<List<List<Span>>> getTraces(QueryRequest queryRequest);
}
