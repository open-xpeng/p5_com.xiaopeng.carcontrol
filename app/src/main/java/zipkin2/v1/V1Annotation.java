package zipkin2.v1;

import java.util.Objects;
import zipkin2.Endpoint;
import zipkin2.internal.Nullable;

@Deprecated
/* loaded from: classes3.dex */
public final class V1Annotation implements Comparable<V1Annotation> {
    final Endpoint endpoint;
    final long timestamp;
    final String value;

    public static V1Annotation create(long j, String str, @Nullable Endpoint endpoint) {
        return new V1Annotation(j, str, endpoint);
    }

    public long timestamp() {
        return this.timestamp;
    }

    public String value() {
        return this.value;
    }

    @Nullable
    public Endpoint endpoint() {
        return this.endpoint;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public V1Annotation(long j, String str, @Nullable Endpoint endpoint) {
        this.timestamp = j;
        Objects.requireNonNull(str, "value == null");
        this.value = str;
        this.endpoint = endpoint;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof V1Annotation) {
            V1Annotation v1Annotation = (V1Annotation) obj;
            if (this.timestamp == v1Annotation.timestamp && this.value.equals(v1Annotation.value)) {
                Endpoint endpoint = this.endpoint;
                Endpoint endpoint2 = v1Annotation.endpoint;
                if (endpoint == null) {
                    if (endpoint2 == null) {
                        return true;
                    }
                } else if (endpoint.equals(endpoint2)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        long j = this.timestamp;
        int hashCode = (((((int) (1000003 ^ (j ^ (j >>> 32)))) ^ 1000003) * 1000003) ^ this.value.hashCode()) * 1000003;
        Endpoint endpoint = this.endpoint;
        return (endpoint == null ? 0 : endpoint.hashCode()) ^ hashCode;
    }

    @Override // java.lang.Comparable
    public int compareTo(V1Annotation v1Annotation) {
        int i = 0;
        if (this == v1Annotation) {
            return 0;
        }
        long j = this.timestamp;
        long j2 = v1Annotation.timestamp;
        if (j < j2) {
            i = -1;
        } else if (j != j2) {
            i = 1;
        }
        return i != 0 ? i : this.value.compareTo(v1Annotation.value);
    }
}
