package zipkin2.storage;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import zipkin2.Annotation;
import zipkin2.Span;
import zipkin2.internal.Nullable;

/* loaded from: classes3.dex */
public final class QueryRequest {
    final Map<String, String> annotationQuery;
    final long endTs;
    final int limit;
    final long lookback;
    final Long maxDuration;
    final Long minDuration;
    final String serviceName;
    final String spanName;

    @Nullable
    public String serviceName() {
        return this.serviceName;
    }

    @Nullable
    public String spanName() {
        return this.spanName;
    }

    public Map<String, String> annotationQuery() {
        return this.annotationQuery;
    }

    @Nullable
    public Long minDuration() {
        return this.minDuration;
    }

    @Nullable
    public Long maxDuration() {
        return this.maxDuration;
    }

    public long endTs() {
        return this.endTs;
    }

    public long lookback() {
        return this.lookback;
    }

    public int limit() {
        return this.limit;
    }

    @Nullable
    public String annotationQueryString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> it = annotationQuery().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            sb.append(next.getKey());
            if (!next.getValue().isEmpty()) {
                sb.append('=').append(next.getValue());
            }
            if (it.hasNext()) {
                sb.append(" and ");
            }
        }
        if (sb.length() > 0) {
            return sb.toString();
        }
        return null;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        Map<String, String> annotationQuery;
        long endTs;
        int limit;
        long lookback;
        Long maxDuration;
        Long minDuration;
        String serviceName;
        String spanName;

        Builder(QueryRequest queryRequest) {
            this.annotationQuery = Collections.emptyMap();
            this.serviceName = queryRequest.serviceName;
            this.spanName = queryRequest.spanName;
            this.annotationQuery = queryRequest.annotationQuery;
            this.minDuration = queryRequest.minDuration;
            this.maxDuration = queryRequest.maxDuration;
            this.endTs = queryRequest.endTs;
            this.lookback = queryRequest.lookback;
            this.limit = queryRequest.limit;
        }

        public Builder serviceName(@Nullable String str) {
            this.serviceName = str;
            return this;
        }

        public Builder spanName(@Nullable String str) {
            this.spanName = str;
            return this;
        }

        public Builder parseAnnotationQuery(@Nullable String str) {
            String[] split;
            if (str == null || str.isEmpty()) {
                return this;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (String str2 : str.split(" and ", 100)) {
                int indexOf = str2.indexOf(61);
                if (indexOf == -1) {
                    linkedHashMap.put(str2, "");
                } else {
                    linkedHashMap.put(str2.substring(0, indexOf), str2.split("=", 2).length >= 2 ? str2.substring(indexOf + 1) : "");
                }
            }
            return annotationQuery(linkedHashMap);
        }

        public Builder annotationQuery(Map<String, String> map) {
            Objects.requireNonNull(map, "annotationQuery == null");
            this.annotationQuery = map;
            return this;
        }

        public Builder minDuration(@Nullable Long l) {
            this.minDuration = l;
            return this;
        }

        public Builder maxDuration(@Nullable Long l) {
            this.maxDuration = l;
            return this;
        }

        public Builder endTs(long j) {
            this.endTs = j;
            return this;
        }

        public Builder lookback(long j) {
            this.lookback = j;
            return this;
        }

        public Builder limit(int i) {
            this.limit = i;
            return this;
        }

        public final QueryRequest build() {
            String str = this.serviceName;
            if (str != null) {
                this.serviceName = str.toLowerCase(Locale.ROOT);
            }
            String str2 = this.spanName;
            if (str2 != null) {
                this.spanName = str2.toLowerCase(Locale.ROOT);
            }
            this.annotationQuery.remove("");
            if ("".equals(this.serviceName)) {
                this.serviceName = null;
            }
            if ("".equals(this.spanName) || "all".equals(this.spanName)) {
                this.spanName = null;
            }
            if (this.endTs <= 0) {
                throw new IllegalArgumentException("endTs <= 0");
            }
            if (this.limit <= 0) {
                throw new IllegalArgumentException("limit <= 0");
            }
            if (this.lookback <= 0) {
                throw new IllegalArgumentException("lookback <= 0");
            }
            Long l = this.minDuration;
            if (l != null) {
                if (l.longValue() <= 0) {
                    throw new IllegalArgumentException("minDuration <= 0");
                }
                Long l2 = this.maxDuration;
                if (l2 != null && l2.longValue() < this.minDuration.longValue()) {
                    throw new IllegalArgumentException("maxDuration < minDuration");
                }
            } else if (this.maxDuration != null) {
                throw new IllegalArgumentException("maxDuration is only valid with minDuration");
            }
            return new QueryRequest(this.serviceName, this.spanName, this.annotationQuery, this.minDuration, this.maxDuration, this.endTs, this.lookback, this.limit);
        }

        Builder() {
            this.annotationQuery = Collections.emptyMap();
        }
    }

    public boolean test(List<Span> list) {
        Iterator<Span> it = list.iterator();
        long j = 0;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Span next = it.next();
            if (next.timestampAsLong() != 0) {
                if (next.parentId() == null) {
                    j = next.timestampAsLong();
                    break;
                } else if (j == 0 || j > next.timestampAsLong()) {
                    j = next.timestampAsLong();
                }
            }
        }
        if (j == 0 || j < (endTs() - lookback()) * 1000 || j > endTs() * 1000) {
            return false;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        boolean z = minDuration() == null && maxDuration() == null;
        String spanName = spanName();
        LinkedHashMap linkedHashMap = new LinkedHashMap(annotationQuery());
        for (Span span : list) {
            String localServiceName = span.localServiceName();
            if (localServiceName != null) {
                linkedHashSet.add(localServiceName);
            }
            if (serviceName() == null || serviceName().equals(localServiceName)) {
                for (Annotation annotation : span.annotations()) {
                    if ("".equals(linkedHashMap.get(annotation.value()))) {
                        linkedHashMap.remove(annotation.value());
                    }
                }
                for (Map.Entry<String, String> entry : span.tags().entrySet()) {
                    String str = (String) linkedHashMap.get(entry.getKey());
                    if (str != null && (str.isEmpty() || str.equals(entry.getValue()))) {
                        linkedHashMap.remove(entry.getKey());
                    }
                }
                if (spanName == null || spanName.equals(span.name())) {
                    spanName = null;
                }
            }
            if (serviceName() == null || serviceName().equals(localServiceName)) {
                if (!z) {
                    if (minDuration() != null && maxDuration() != null) {
                        if (span.durationAsLong() >= minDuration().longValue() && span.durationAsLong() <= maxDuration().longValue()) {
                        }
                    } else if (minDuration() != null) {
                        z = span.durationAsLong() >= minDuration().longValue();
                    }
                }
            }
        }
        return (serviceName() == null || linkedHashSet.contains(serviceName())) && spanName == null && linkedHashMap.isEmpty() && z;
    }

    QueryRequest(@Nullable String str, @Nullable String str2, Map<String, String> map, @Nullable Long l, @Nullable Long l2, long j, long j2, int i) {
        this.serviceName = str;
        this.spanName = str2;
        this.annotationQuery = map;
        this.minDuration = l;
        this.maxDuration = l2;
        this.endTs = j;
        this.lookback = j2;
        this.limit = i;
    }

    public String toString() {
        return "QueryRequest{serviceName=" + this.serviceName + ", spanName=" + this.spanName + ", annotationQuery=" + this.annotationQuery + ", minDuration=" + this.minDuration + ", maxDuration=" + this.maxDuration + ", endTs=" + this.endTs + ", lookback=" + this.lookback + ", limit=" + this.limit + "}";
    }
}
