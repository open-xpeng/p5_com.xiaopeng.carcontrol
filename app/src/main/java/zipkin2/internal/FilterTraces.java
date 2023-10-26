package zipkin2.internal;

import java.util.Iterator;
import java.util.List;
import zipkin2.Call;
import zipkin2.Span;
import zipkin2.storage.QueryRequest;

/* loaded from: classes3.dex */
public final class FilterTraces implements Call.Mapper<List<List<Span>>, List<List<Span>>> {
    final QueryRequest request;

    public static Call.Mapper<List<List<Span>>, List<List<Span>>> create(QueryRequest queryRequest) {
        return new FilterTraces(queryRequest);
    }

    FilterTraces(QueryRequest queryRequest) {
        this.request = queryRequest;
    }

    @Override // zipkin2.Call.Mapper
    public List<List<Span>> map(List<List<Span>> list) {
        Iterator<List<Span>> it = list.iterator();
        while (it.hasNext()) {
            if (!this.request.test(it.next())) {
                it.remove();
            }
        }
        return list;
    }

    public String toString() {
        return "FilterTraces{request=" + this.request + "}";
    }
}
