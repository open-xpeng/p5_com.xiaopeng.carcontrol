package zipkin2;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import zipkin2.codec.SpanBytesDecoder;
import zipkin2.codec.SpanBytesEncoder;
import zipkin2.internal.Nullable;

/* loaded from: classes3.dex */
public final class Span implements Serializable {
    static final int FLAG_DEBUG = 2;
    static final int FLAG_DEBUG_SET = 4;
    static final int FLAG_SHARED = 8;
    static final int FLAG_SHARED_SET = 16;
    private static final long serialVersionUID = 0;
    final List<Annotation> annotations;
    final long duration;
    final int flags;
    final String id;
    final Kind kind;
    final Endpoint localEndpoint;
    final String name;
    final String parentId;
    final Endpoint remoteEndpoint;
    final Map<String, String> tags;
    final long timestamp;
    final String traceId;
    static final Charset UTF_8 = Charset.forName("UTF-8");
    static final Endpoint EMPTY_ENDPOINT = Endpoint.newBuilder().build();

    /* loaded from: classes3.dex */
    public enum Kind {
        CLIENT,
        SERVER,
        PRODUCER,
        CONSUMER
    }

    public String traceId() {
        return this.traceId;
    }

    @Nullable
    public String parentId() {
        return this.parentId;
    }

    public String id() {
        return this.id;
    }

    @Nullable
    public Kind kind() {
        return this.kind;
    }

    @Nullable
    public String name() {
        return this.name;
    }

    @Nullable
    public Long timestamp() {
        long j = this.timestamp;
        if (j > 0) {
            return Long.valueOf(j);
        }
        return null;
    }

    public long timestampAsLong() {
        return this.timestamp;
    }

    @Nullable
    public Long duration() {
        long j = this.duration;
        if (j > 0) {
            return Long.valueOf(j);
        }
        return null;
    }

    public long durationAsLong() {
        return this.duration;
    }

    @Nullable
    public Endpoint localEndpoint() {
        return this.localEndpoint;
    }

    @Nullable
    public Endpoint remoteEndpoint() {
        return this.remoteEndpoint;
    }

    public List<Annotation> annotations() {
        return this.annotations;
    }

    public Map<String, String> tags() {
        return this.tags;
    }

    @Nullable
    public Boolean debug() {
        int i = this.flags;
        if ((i & 4) == 4) {
            return Boolean.valueOf((i & 2) == 2);
        }
        return null;
    }

    @Nullable
    public Boolean shared() {
        int i = this.flags;
        if ((i & 16) == 16) {
            return Boolean.valueOf((i & 8) == 8);
        }
        return null;
    }

    @Nullable
    public String localServiceName() {
        Endpoint localEndpoint = localEndpoint();
        if (localEndpoint != null) {
            return localEndpoint.serviceName();
        }
        return null;
    }

    @Nullable
    public String remoteServiceName() {
        Endpoint remoteEndpoint = remoteEndpoint();
        if (remoteEndpoint != null) {
            return remoteEndpoint.serviceName();
        }
        return null;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        ArrayList<Annotation> annotations;
        long duration;
        int flags;
        String id;
        Kind kind;
        Endpoint localEndpoint;
        String name;
        String parentId;
        Endpoint remoteEndpoint;
        TreeMap<String, String> tags;
        long timestamp;
        String traceId;

        public Builder clear() {
            this.traceId = null;
            this.parentId = null;
            this.id = null;
            this.kind = null;
            this.name = null;
            this.timestamp = 0L;
            this.duration = 0L;
            this.localEndpoint = null;
            this.remoteEndpoint = null;
            ArrayList<Annotation> arrayList = this.annotations;
            if (arrayList != null) {
                arrayList.clear();
            }
            TreeMap<String, String> treeMap = this.tags;
            if (treeMap != null) {
                treeMap.clear();
            }
            this.flags = 0;
            return this;
        }

        public Builder clone() {
            Builder builder = new Builder();
            builder.traceId = this.traceId;
            builder.parentId = this.parentId;
            builder.id = this.id;
            builder.kind = this.kind;
            builder.name = this.name;
            builder.timestamp = this.timestamp;
            builder.duration = this.duration;
            builder.localEndpoint = this.localEndpoint;
            builder.remoteEndpoint = this.remoteEndpoint;
            ArrayList<Annotation> arrayList = this.annotations;
            if (arrayList != null) {
                builder.annotations = (ArrayList) arrayList.clone();
            }
            TreeMap<String, String> treeMap = this.tags;
            if (treeMap != null) {
                builder.tags = (TreeMap) treeMap.clone();
            }
            builder.flags = this.flags;
            return builder;
        }

        Builder(Span span) {
            this.flags = 0;
            this.traceId = span.traceId;
            this.parentId = span.parentId;
            this.id = span.id;
            this.kind = span.kind;
            this.name = span.name;
            this.timestamp = span.timestamp;
            this.duration = span.duration;
            this.localEndpoint = span.localEndpoint;
            this.remoteEndpoint = span.remoteEndpoint;
            if (!span.annotations.isEmpty()) {
                ArrayList<Annotation> arrayList = new ArrayList<>(span.annotations.size());
                this.annotations = arrayList;
                arrayList.addAll(span.annotations);
            }
            if (!span.tags.isEmpty()) {
                TreeMap<String, String> treeMap = new TreeMap<>();
                this.tags = treeMap;
                treeMap.putAll(span.tags);
            }
            this.flags = span.flags;
        }

        public Builder merge(Span span) {
            if (this.traceId == null) {
                this.traceId = span.traceId;
            }
            if (this.id == null) {
                this.id = span.id;
            }
            if (this.parentId == null) {
                this.parentId = span.parentId;
            }
            if (this.kind == null) {
                this.kind = span.kind;
            }
            if (this.name == null) {
                this.name = span.name;
            }
            if (this.timestamp == 0) {
                this.timestamp = span.timestamp;
            }
            if (this.duration == 0) {
                this.duration = span.duration;
            }
            if (this.localEndpoint == null) {
                this.localEndpoint = span.localEndpoint;
            } else if (span.localEndpoint != null) {
                this.localEndpoint = this.localEndpoint.toBuilder().merge(span.localEndpoint).build();
            }
            if (this.remoteEndpoint == null) {
                this.remoteEndpoint = span.remoteEndpoint;
            } else if (span.remoteEndpoint != null) {
                this.remoteEndpoint = this.remoteEndpoint.toBuilder().merge(span.remoteEndpoint).build();
            }
            if (!span.annotations.isEmpty()) {
                if (this.annotations == null) {
                    this.annotations = new ArrayList<>(span.annotations.size());
                }
                this.annotations.addAll(span.annotations);
            }
            if (!span.tags.isEmpty()) {
                if (this.tags == null) {
                    this.tags = new TreeMap<>();
                }
                this.tags.putAll(span.tags);
            }
            this.flags = span.flags | this.flags;
            return this;
        }

        @Nullable
        public Kind kind() {
            return this.kind;
        }

        @Nullable
        public Endpoint localEndpoint() {
            return this.localEndpoint;
        }

        public Builder traceId(String str) {
            this.traceId = Span.normalizeTraceId(str);
            return this;
        }

        public Builder traceId(long j, long j2) {
            int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
            if (i == 0 && j2 == 0) {
                throw new IllegalArgumentException("empty trace ID");
            }
            int i2 = 16;
            char[] cArr = new char[i != 0 ? 32 : 16];
            if (i != 0) {
                Span.writeHexLong(cArr, 0, j);
            } else {
                i2 = 0;
            }
            Span.writeHexLong(cArr, i2, j2);
            this.traceId = new String(cArr);
            return this;
        }

        public Builder parentId(long j) {
            this.parentId = j != 0 ? Span.toLowerHex(j) : null;
            return this;
        }

        public Builder parentId(@Nullable String str) {
            if (str == null) {
                this.parentId = null;
                return this;
            }
            int length = str.length();
            if (length != 0) {
                if (length > 16) {
                    throw new IllegalArgumentException("parentId.length > 16");
                }
                if (Span.validateHexAndReturnZeroPrefix(str) == length) {
                    this.parentId = null;
                } else {
                    if (length < 16) {
                        str = Span.padLeft(str, 16);
                    }
                    this.parentId = str;
                }
                return this;
            }
            throw new IllegalArgumentException("parentId is empty");
        }

        public Builder id(long j) {
            if (j == 0) {
                throw new IllegalArgumentException("empty id");
            }
            this.id = Span.toLowerHex(j);
            return this;
        }

        public Builder id(String str) {
            Objects.requireNonNull(str, "id == null");
            int length = str.length();
            if (length != 0) {
                if (length > 16) {
                    throw new IllegalArgumentException("id.length > 16");
                }
                if (Span.validateHexAndReturnZeroPrefix(str) != 16) {
                    if (length < 16) {
                        str = Span.padLeft(str, 16);
                    }
                    this.id = str;
                    return this;
                }
                throw new IllegalArgumentException("id is all zeros");
            }
            throw new IllegalArgumentException("id is empty");
        }

        public Builder kind(@Nullable Kind kind) {
            this.kind = kind;
            return this;
        }

        public Builder name(@Nullable String str) {
            this.name = (str == null || str.isEmpty()) ? null : str.toLowerCase(Locale.ROOT);
            return this;
        }

        public Builder timestamp(long j) {
            if (j < 0) {
                j = 0;
            }
            this.timestamp = j;
            return this;
        }

        public Builder timestamp(@Nullable Long l) {
            this.timestamp = ((l == null || l.longValue() < 0) ? 0L : 0L).longValue();
            return this;
        }

        public Builder duration(long j) {
            if (j < 0) {
                j = 0;
            }
            this.duration = j;
            return this;
        }

        public Builder duration(@Nullable Long l) {
            this.duration = ((l == null || l.longValue() < 0) ? 0L : 0L).longValue();
            return this;
        }

        public Builder localEndpoint(@Nullable Endpoint endpoint) {
            if (Span.EMPTY_ENDPOINT.equals(endpoint)) {
                endpoint = null;
            }
            this.localEndpoint = endpoint;
            return this;
        }

        public Builder remoteEndpoint(@Nullable Endpoint endpoint) {
            if (Span.EMPTY_ENDPOINT.equals(endpoint)) {
                endpoint = null;
            }
            this.remoteEndpoint = endpoint;
            return this;
        }

        public Builder addAnnotation(long j, String str) {
            if (this.annotations == null) {
                this.annotations = new ArrayList<>(2);
            }
            this.annotations.add(Annotation.create(j, str));
            return this;
        }

        public Builder clearAnnotations() {
            ArrayList<Annotation> arrayList = this.annotations;
            if (arrayList == null) {
                return this;
            }
            arrayList.clear();
            return this;
        }

        public Builder putTag(String str, String str2) {
            if (this.tags == null) {
                this.tags = new TreeMap<>();
            }
            Objects.requireNonNull(str, "key == null");
            if (str2 == null) {
                throw new NullPointerException("value of " + str + " == null");
            }
            this.tags.put(str, str2);
            return this;
        }

        public Builder clearTags() {
            TreeMap<String, String> treeMap = this.tags;
            if (treeMap == null) {
                return this;
            }
            treeMap.clear();
            return this;
        }

        public Builder debug(boolean z) {
            int i = this.flags | 4;
            this.flags = i;
            if (z) {
                this.flags = i | 2;
            } else {
                this.flags = i & (-3);
            }
            return this;
        }

        public Builder debug(@Nullable Boolean bool) {
            if (bool != null) {
                return debug(bool.booleanValue());
            }
            this.flags &= -7;
            return this;
        }

        public Builder shared(boolean z) {
            int i = this.flags | 16;
            this.flags = i;
            if (z) {
                this.flags = i | 8;
            } else {
                this.flags = i & (-9);
            }
            return this;
        }

        public Builder shared(@Nullable Boolean bool) {
            if (bool != null) {
                return shared(bool.booleanValue());
            }
            this.flags &= -25;
            return this;
        }

        public Span build() {
            String str = this.traceId == null ? " traceId" : "";
            if (this.id == null) {
                str = str + " id";
            }
            if (!"".equals(str)) {
                throw new IllegalStateException("Missing :" + str);
            }
            if (this.id.equals(this.parentId)) {
                Logger logger = Logger.getLogger(Span.class.getName());
                if (logger.isLoggable(Level.FINEST)) {
                    logger.fine(String.format("undoing circular dependency: traceId=%s, spanId=%s", this.traceId, this.id));
                }
                this.parentId = null;
            }
            if ((this.flags & 8) == 8 && this.kind == Kind.CLIENT) {
                Logger logger2 = Logger.getLogger(Span.class.getName());
                if (logger2.isLoggable(Level.FINEST)) {
                    logger2.fine(String.format("removing shared flag on client: traceId=%s, spanId=%s", this.traceId, this.id));
                }
                shared((Boolean) null);
            }
            return new Span(this);
        }

        Builder() {
            this.flags = 0;
        }
    }

    public String toString() {
        return new String(SpanBytesEncoder.JSON_V2.encode(this), UTF_8);
    }

    public static String normalizeTraceId(String str) {
        Objects.requireNonNull(str, "traceId == null");
        int length = str.length();
        if (length != 0) {
            if (length > 32) {
                throw new IllegalArgumentException("traceId.length > 32");
            }
            int validateHexAndReturnZeroPrefix = validateHexAndReturnZeroPrefix(str);
            if (validateHexAndReturnZeroPrefix != length) {
                if (length == 32 || length == 16) {
                    return (length != 32 || validateHexAndReturnZeroPrefix < 16) ? str : str.substring(16);
                } else if (length < 16) {
                    return padLeft(str, 16);
                } else {
                    return padLeft(str, 32);
                }
            }
            throw new IllegalArgumentException("traceId is all zeros");
        }
        throw new IllegalArgumentException("traceId is empty");
    }

    static String padLeft(String str, int i) {
        StringBuilder sb = new StringBuilder(i);
        int length = i - str.length();
        for (int i2 = 0; i2 < length; i2++) {
            sb.append('0');
        }
        sb.append(str);
        return sb.toString();
    }

    static String toLowerHex(long j) {
        char[] cArr = new char[16];
        writeHexLong(cArr, 0, j);
        return new String(cArr);
    }

    static void writeHexLong(char[] cArr, int i, long j) {
        writeHexByte(cArr, i + 0, (byte) ((j >>> 56) & 255));
        writeHexByte(cArr, i + 2, (byte) ((j >>> 48) & 255));
        writeHexByte(cArr, i + 4, (byte) ((j >>> 40) & 255));
        writeHexByte(cArr, i + 6, (byte) ((j >>> 32) & 255));
        writeHexByte(cArr, i + 8, (byte) ((j >>> 24) & 255));
        writeHexByte(cArr, i + 10, (byte) ((j >>> 16) & 255));
        writeHexByte(cArr, i + 12, (byte) ((j >>> 8) & 255));
        writeHexByte(cArr, i + 14, (byte) (j & 255));
    }

    static void writeHexByte(char[] cArr, int i, byte b) {
        cArr[i + 0] = Endpoint.HEX_DIGITS[(b >> 4) & 15];
        cArr[i + 1] = Endpoint.HEX_DIGITS[b & 15];
    }

    static int validateHexAndReturnZeroPrefix(String str) {
        boolean z = str.charAt(0) == '0';
        int length = str.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if ((charAt < '0' || charAt > '9') && (charAt < 'a' || charAt > 'f')) {
                throw new IllegalArgumentException(str + " should be lower-hex encoded with no prefix");
            }
            if (charAt != '0') {
                z = false;
            } else if (z) {
                i++;
            }
        }
        return i;
    }

    static <T extends Comparable<? super T>> List<T> sortedList(@Nullable List<T> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        int i = 0;
        if (list.size() == 1) {
            return Collections.singletonList(list.get(0));
        }
        Object[] array = list.toArray();
        Arrays.sort(array);
        int i2 = 1;
        while (i2 < array.length) {
            if (!array[i2].equals(array[i])) {
                i++;
                array[i] = array[i2];
            }
            i2++;
        }
        int i3 = i + 1;
        if (i2 != i3) {
            array = Arrays.copyOf(array, i3);
        }
        return Collections.unmodifiableList(Arrays.asList(array));
    }

    Span(Builder builder) {
        this.traceId = builder.traceId;
        this.parentId = builder.id.equals(builder.parentId) ? null : builder.parentId;
        this.id = builder.id;
        this.kind = builder.kind;
        this.name = builder.name;
        this.timestamp = builder.timestamp;
        this.duration = builder.duration;
        this.localEndpoint = builder.localEndpoint;
        this.remoteEndpoint = builder.remoteEndpoint;
        this.annotations = sortedList(builder.annotations);
        this.tags = builder.tags == null ? Collections.emptyMap() : new LinkedHashMap<>(builder.tags);
        this.flags = builder.flags;
    }

    public boolean equals(Object obj) {
        String str;
        Kind kind;
        String str2;
        Endpoint endpoint;
        Endpoint endpoint2;
        if (obj == this) {
            return true;
        }
        if (obj instanceof Span) {
            Span span = (Span) obj;
            return this.traceId.equals(span.traceId) && ((str = this.parentId) != null ? str.equals(span.parentId) : span.parentId == null) && this.id.equals(span.id) && ((kind = this.kind) != null ? kind.equals(span.kind) : span.kind == null) && ((str2 = this.name) != null ? str2.equals(span.name) : span.name == null) && this.timestamp == span.timestamp && this.duration == span.duration && ((endpoint = this.localEndpoint) != null ? endpoint.equals(span.localEndpoint) : span.localEndpoint == null) && ((endpoint2 = this.remoteEndpoint) != null ? endpoint2.equals(span.remoteEndpoint) : span.remoteEndpoint == null) && this.annotations.equals(span.annotations) && this.tags.equals(span.tags) && this.flags == span.flags;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = (this.traceId.hashCode() ^ 1000003) * 1000003;
        String str = this.parentId;
        int hashCode2 = (((hashCode ^ (str == null ? 0 : str.hashCode())) * 1000003) ^ this.id.hashCode()) * 1000003;
        Kind kind = this.kind;
        int hashCode3 = (hashCode2 ^ (kind == null ? 0 : kind.hashCode())) * 1000003;
        String str2 = this.name;
        int hashCode4 = (hashCode3 ^ (str2 == null ? 0 : str2.hashCode())) * 1000003;
        long j = this.timestamp;
        int i = (hashCode4 ^ ((int) (hashCode4 ^ (j ^ (j >>> 32))))) * 1000003;
        long j2 = this.duration;
        int i2 = (i ^ ((int) (i ^ (j2 ^ (j2 >>> 32))))) * 1000003;
        Endpoint endpoint = this.localEndpoint;
        int hashCode5 = (i2 ^ (endpoint == null ? 0 : endpoint.hashCode())) * 1000003;
        Endpoint endpoint2 = this.remoteEndpoint;
        return ((((((hashCode5 ^ (endpoint2 != null ? endpoint2.hashCode() : 0)) * 1000003) ^ this.annotations.hashCode()) * 1000003) ^ this.tags.hashCode()) * 1000003) ^ this.flags;
    }

    final Object writeReplace() throws ObjectStreamException {
        return new SerializedForm(SpanBytesEncoder.PROTO3.encode(this));
    }

    /* loaded from: classes3.dex */
    private static final class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final byte[] bytes;

        SerializedForm(byte[] bArr) {
            this.bytes = bArr;
        }

        Object readResolve() throws ObjectStreamException {
            try {
                return SpanBytesDecoder.PROTO3.decodeOne(this.bytes);
            } catch (IllegalArgumentException e) {
                throw new StreamCorruptedException(e.getMessage());
            }
        }
    }
}
