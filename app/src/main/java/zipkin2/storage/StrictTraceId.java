package zipkin2.storage;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import zipkin2.Call;
import zipkin2.Span;
import zipkin2.internal.FilterTraces;

/* loaded from: classes3.dex */
public final class StrictTraceId {
    public static Call.Mapper<List<Span>, List<Span>> filterSpans(String str) {
        return new FilterSpans(str);
    }

    public static Call.Mapper<List<List<Span>>, List<List<Span>>> filterTraces(QueryRequest queryRequest) {
        return new FilterTracesIfClashOnLowerTraceId(queryRequest);
    }

    /* loaded from: classes3.dex */
    static final class FilterTracesIfClashOnLowerTraceId implements Call.Mapper<List<List<Span>>, List<List<Span>>> {
        final QueryRequest request;

        FilterTracesIfClashOnLowerTraceId(QueryRequest queryRequest) {
            this.request = queryRequest;
        }

        @Override // zipkin2.Call.Mapper
        public List<List<Span>> map(List<List<Span>> list) {
            return StrictTraceId.hasClashOnLowerTraceId(list) ? FilterTraces.create(this.request).map(list) : list;
        }

        public String toString() {
            return "FilterTracesIfClashOnLowerTraceId{request=" + this.request + "}";
        }
    }

    static boolean hasClashOnLowerTraceId(List<List<Span>> list) {
        int size = list.size();
        if (size <= 1) {
            return false;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (int i = 0; i < size; i++) {
            if (!linkedHashSet.add(lowerTraceId(list.get(i).get(0).traceId()))) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String lowerTraceId(String str) {
        return str.length() == 16 ? str : str.substring(16);
    }

    /* loaded from: classes3.dex */
    static final class FilterSpans implements Call.Mapper<List<Span>, List<Span>> {
        final String traceId;

        FilterSpans(String str) {
            this.traceId = str;
        }

        @Override // zipkin2.Call.Mapper
        public List<Span> map(List<Span> list) {
            Iterator<Span> it = list.iterator();
            while (it.hasNext()) {
                if (!it.next().traceId().equals(this.traceId)) {
                    it.remove();
                }
            }
            return list;
        }

        public String toString() {
            return "FilterSpans{traceId=" + this.traceId + "}";
        }
    }

    StrictTraceId() {
    }
}
