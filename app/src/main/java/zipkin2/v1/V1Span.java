package zipkin2.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import zipkin2.Endpoint;
import zipkin2.internal.HexCodec;
import zipkin2.internal.Nullable;

@Deprecated
/* loaded from: classes3.dex */
public final class V1Span {
    static final Endpoint EMPTY_ENDPOINT = Endpoint.newBuilder().build();
    final List<V1Annotation> annotations;
    final List<V1BinaryAnnotation> binaryAnnotations;
    final Boolean debug;
    final long duration;
    final long id;
    final String name;
    final long parentId;
    final long timestamp;
    final long traceId;
    final long traceIdHigh;

    public long traceIdHigh() {
        return this.traceIdHigh;
    }

    public long traceId() {
        return this.traceId;
    }

    public long id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public long parentId() {
        return this.parentId;
    }

    public long timestamp() {
        return this.timestamp;
    }

    public long duration() {
        return this.duration;
    }

    public List<V1Annotation> annotations() {
        return this.annotations;
    }

    public List<V1BinaryAnnotation> binaryAnnotations() {
        return this.binaryAnnotations;
    }

    public Boolean debug() {
        return this.debug;
    }

    public Set<String> serviceNames() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (V1Annotation v1Annotation : this.annotations) {
            if (v1Annotation.endpoint != null && v1Annotation.endpoint.serviceName() != null) {
                linkedHashSet.add(v1Annotation.endpoint.serviceName());
            }
        }
        for (V1BinaryAnnotation v1BinaryAnnotation : this.binaryAnnotations) {
            if (v1BinaryAnnotation.endpoint != null && v1BinaryAnnotation.endpoint.serviceName() != null) {
                linkedHashSet.add(v1BinaryAnnotation.endpoint.serviceName());
            }
        }
        return linkedHashSet;
    }

    V1Span(Builder builder) {
        if (builder.traceId == 0) {
            throw new IllegalArgumentException("traceId == 0");
        }
        if (builder.id == 0) {
            throw new IllegalArgumentException("id == 0");
        }
        this.traceId = builder.traceId;
        this.traceIdHigh = builder.traceIdHigh;
        this.name = builder.name;
        this.id = builder.id;
        this.parentId = builder.parentId;
        this.timestamp = builder.timestamp;
        this.duration = builder.duration;
        this.annotations = sortedList(builder.annotations);
        this.binaryAnnotations = sortedList(builder.binaryAnnotations);
        this.debug = builder.debug;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        ArrayList<V1Annotation> annotations;
        ArrayList<V1BinaryAnnotation> binaryAnnotations;
        Boolean debug;
        long duration;
        long id;
        String name;
        long parentId;
        long timestamp;
        long traceId;
        long traceIdHigh;

        public long traceIdHigh() {
            return this.traceIdHigh;
        }

        public long traceId() {
            return this.traceId;
        }

        public long id() {
            return this.id;
        }

        Builder() {
        }

        public Builder clear() {
            this.id = 0L;
            this.traceIdHigh = 0L;
            this.traceId = 0L;
            this.name = null;
            this.duration = 0L;
            this.timestamp = 0L;
            this.parentId = 0L;
            ArrayList<V1Annotation> arrayList = this.annotations;
            if (arrayList != null) {
                arrayList.clear();
            }
            ArrayList<V1BinaryAnnotation> arrayList2 = this.binaryAnnotations;
            if (arrayList2 != null) {
                arrayList2.clear();
            }
            this.debug = null;
            return this;
        }

        public Builder traceId(String str) {
            Objects.requireNonNull(str, "traceId == null");
            if (str.length() == 32) {
                this.traceIdHigh = HexCodec.lowerHexToUnsignedLong(str, 0);
            }
            this.traceId = HexCodec.lowerHexToUnsignedLong(str);
            return this;
        }

        public Builder traceId(long j) {
            this.traceId = j;
            return this;
        }

        public Builder traceIdHigh(long j) {
            this.traceIdHigh = j;
            return this;
        }

        public Builder id(long j) {
            this.id = j;
            return this;
        }

        public Builder id(String str) {
            Objects.requireNonNull(str, "id == null");
            this.id = HexCodec.lowerHexToUnsignedLong(str);
            return this;
        }

        public Builder parentId(String str) {
            this.parentId = str != null ? HexCodec.lowerHexToUnsignedLong(str) : 0L;
            return this;
        }

        public Builder parentId(long j) {
            this.parentId = j;
            return this;
        }

        public Builder name(String str) {
            this.name = (str == null || str.isEmpty()) ? null : str.toLowerCase(Locale.ROOT);
            return this;
        }

        public Builder timestamp(long j) {
            this.timestamp = j;
            return this;
        }

        public Builder duration(long j) {
            this.duration = j;
            return this;
        }

        public Builder addAnnotation(long j, String str, @Nullable Endpoint endpoint) {
            if (this.annotations == null) {
                this.annotations = new ArrayList<>(4);
            }
            if (V1Span.EMPTY_ENDPOINT.equals(endpoint)) {
                endpoint = null;
            }
            this.annotations.add(new V1Annotation(j, str, endpoint));
            return this;
        }

        public Builder addBinaryAnnotation(String str, Endpoint endpoint) {
            if (endpoint != null && !V1Span.EMPTY_ENDPOINT.equals(endpoint)) {
                if (this.binaryAnnotations == null) {
                    this.binaryAnnotations = new ArrayList<>(4);
                }
                this.binaryAnnotations.add(new V1BinaryAnnotation(str, null, endpoint));
            }
            return this;
        }

        public Builder addBinaryAnnotation(String str, String str2, Endpoint endpoint) {
            Objects.requireNonNull(str2, "value == null");
            if (V1Span.EMPTY_ENDPOINT.equals(endpoint)) {
                endpoint = null;
            }
            if (this.binaryAnnotations == null) {
                this.binaryAnnotations = new ArrayList<>(4);
            }
            this.binaryAnnotations.add(new V1BinaryAnnotation(str, str2, endpoint));
            return this;
        }

        public Builder debug(@Nullable Boolean bool) {
            this.debug = bool;
            return this;
        }

        public V1Span build() {
            return new V1Span(this);
        }
    }

    public boolean equals(Object obj) {
        String str;
        if (obj == this) {
            return true;
        }
        if (obj instanceof V1Span) {
            V1Span v1Span = (V1Span) obj;
            if (this.traceIdHigh == v1Span.traceIdHigh && this.traceId == v1Span.traceId && ((str = this.name) != null ? str.equals(v1Span.name) : v1Span.name == null) && this.id == v1Span.id && this.parentId == v1Span.parentId && this.timestamp == v1Span.timestamp && this.duration == v1Span.duration && this.annotations.equals(v1Span.annotations) && this.binaryAnnotations.equals(v1Span.binaryAnnotations)) {
                Boolean bool = this.debug;
                Boolean bool2 = v1Span.debug;
                if (bool == null) {
                    if (bool2 == null) {
                        return true;
                    }
                } else if (bool.equals(bool2)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        long j = this.traceIdHigh;
        int i = (((int) (1000003 ^ (j ^ (j >>> 32)))) ^ 1000003) * 1000003;
        long j2 = this.traceId;
        int i2 = (i ^ ((int) (i ^ (j2 ^ (j2 >>> 32))))) * 1000003;
        String str = this.name;
        int hashCode = (i2 ^ (str == null ? 0 : str.hashCode())) * 1000003;
        long j3 = this.id;
        int i3 = (hashCode ^ ((int) (hashCode ^ (j3 ^ (j3 >>> 32))))) * 1000003;
        long j4 = this.parentId;
        int i4 = (i3 ^ ((int) (i3 ^ (j4 ^ (j4 >>> 32))))) * 1000003;
        long j5 = this.timestamp;
        int i5 = (i4 ^ ((int) (i4 ^ (j5 ^ (j5 >>> 32))))) * 1000003;
        long j6 = this.duration;
        int hashCode2 = (((((i5 ^ ((int) (((j6 >>> 32) ^ j6) ^ i5))) * 1000003) ^ this.annotations.hashCode()) * 1000003) ^ this.binaryAnnotations.hashCode()) * 1000003;
        Boolean bool = this.debug;
        return hashCode2 ^ (bool != null ? bool.hashCode() : 0);
    }

    static <T extends Comparable<T>> List<T> sortedList(List<T> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        Collections.sort(list);
        return Collections.unmodifiableList(new ArrayList(list));
    }
}
