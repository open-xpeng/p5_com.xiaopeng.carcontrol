package zipkin2.v1;

import java.util.Objects;
import zipkin2.Endpoint;
import zipkin2.internal.Nullable;

@Deprecated
/* loaded from: classes3.dex */
public final class V1BinaryAnnotation implements Comparable<V1BinaryAnnotation> {
    public static final int TYPE_BOOLEAN = 0;
    public static final int TYPE_STRING = 6;
    final Endpoint endpoint;
    final String key;
    final String stringValue;
    final int type;

    public static V1BinaryAnnotation createAddress(String str, Endpoint endpoint) {
        Objects.requireNonNull(endpoint, "endpoint == null");
        return new V1BinaryAnnotation(str, null, endpoint);
    }

    public static V1BinaryAnnotation createString(String str, String str2, Endpoint endpoint) {
        Objects.requireNonNull(str2, "value == null");
        return new V1BinaryAnnotation(str, str2, endpoint);
    }

    public String key() {
        return this.key;
    }

    public int type() {
        return this.type;
    }

    @Nullable
    public String stringValue() {
        return this.stringValue;
    }

    public Endpoint endpoint() {
        return this.endpoint;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public V1BinaryAnnotation(String str, String str2, Endpoint endpoint) {
        Objects.requireNonNull(str, "key == null");
        this.key = str;
        this.stringValue = str2;
        this.type = str2 != null ? 6 : 0;
        this.endpoint = endpoint;
    }

    public boolean equals(Object obj) {
        String str;
        if (obj == this) {
            return true;
        }
        if (obj instanceof V1BinaryAnnotation) {
            V1BinaryAnnotation v1BinaryAnnotation = (V1BinaryAnnotation) obj;
            if (this.key.equals(v1BinaryAnnotation.key) && ((str = this.stringValue) != null ? str.equals(v1BinaryAnnotation.stringValue) : v1BinaryAnnotation.stringValue == null)) {
                Endpoint endpoint = this.endpoint;
                Endpoint endpoint2 = v1BinaryAnnotation.endpoint;
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
        int hashCode = (this.key.hashCode() ^ 1000003) * 1000003;
        String str = this.stringValue;
        int hashCode2 = (hashCode ^ (str == null ? 0 : str.hashCode())) * 1000003;
        Endpoint endpoint = this.endpoint;
        return hashCode2 ^ (endpoint != null ? endpoint.hashCode() : 0);
    }

    @Override // java.lang.Comparable
    public int compareTo(V1BinaryAnnotation v1BinaryAnnotation) {
        if (this == v1BinaryAnnotation) {
            return 0;
        }
        return this.key.compareTo(v1BinaryAnnotation.key);
    }
}
