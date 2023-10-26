package zipkin2.internal;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import zipkin2.Annotation;
import zipkin2.Endpoint;
import zipkin2.Span;
import zipkin2.internal.Proto3Fields;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class Proto3ZipkinFields {
    static final Logger LOG = Logger.getLogger(Proto3ZipkinFields.class.getName());
    static final SpanField SPAN = new SpanField();

    Proto3ZipkinFields() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class EndpointField extends Proto3Fields.LengthDelimitedField<Endpoint> {
        static final int IPV4_KEY = 18;
        static final int IPV6_KEY = 26;
        static final int PORT_KEY = 32;
        static final int SERVICE_NAME_KEY = 10;
        static final Proto3Fields.Utf8Field SERVICE_NAME = new Proto3Fields.Utf8Field(10);
        static final Proto3Fields.BytesField IPV4 = new Proto3Fields.BytesField(18);
        static final Proto3Fields.BytesField IPV6 = new Proto3Fields.BytesField(26);
        static final Proto3Fields.VarintField PORT = new Proto3Fields.VarintField(32);

        EndpointField(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public int sizeOfValue(Endpoint endpoint) {
            return SERVICE_NAME.sizeInBytes(endpoint.serviceName()) + 0 + IPV4.sizeInBytes(endpoint.ipv4Bytes()) + IPV6.sizeInBytes(endpoint.ipv6Bytes()) + PORT.sizeInBytes(endpoint.portAsInt());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public void writeValue(Buffer buffer, Endpoint endpoint) {
            SERVICE_NAME.write(buffer, endpoint.serviceName());
            IPV4.write(buffer, endpoint.ipv4Bytes());
            IPV6.write(buffer, endpoint.ipv6Bytes());
            PORT.write(buffer, endpoint.portAsInt());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public Endpoint readValue(Buffer buffer, int i) {
            int i2 = buffer.pos + i;
            Endpoint.Builder newBuilder = Endpoint.newBuilder();
            while (buffer.pos < i2) {
                int readVarint32 = buffer.readVarint32();
                if (readVarint32 == 10) {
                    newBuilder.serviceName(SERVICE_NAME.readLengthPrefixAndValue(buffer));
                } else if (readVarint32 == 18) {
                    newBuilder.parseIp(IPV4.readLengthPrefixAndValue(buffer));
                } else if (readVarint32 == 26) {
                    newBuilder.parseIp(IPV6.readLengthPrefixAndValue(buffer));
                } else if (readVarint32 == 32) {
                    newBuilder.port(buffer.readVarint32());
                } else {
                    Proto3ZipkinFields.logAndSkip(buffer, readVarint32);
                }
            }
            return newBuilder.build();
        }
    }

    /* loaded from: classes3.dex */
    static abstract class SpanBuilderField<T> extends Proto3Fields.LengthDelimitedField<T> {
        abstract boolean readLengthPrefixAndValue(Buffer buffer, Span.Builder builder);

        SpanBuilderField(int i) {
            super(i);
        }

        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        final T readValue(Buffer buffer, int i) {
            throw new UnsupportedOperationException();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class AnnotationField extends SpanBuilderField<Annotation> {
        static final int TIMESTAMP_KEY = 9;
        static final int VALUE_KEY = 18;
        static final Proto3Fields.Fixed64Field TIMESTAMP = new Proto3Fields.Fixed64Field(9);
        static final Proto3Fields.Utf8Field VALUE = new Proto3Fields.Utf8Field(18);

        AnnotationField(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public int sizeOfValue(Annotation annotation) {
            return TIMESTAMP.sizeInBytes(annotation.timestamp()) + VALUE.sizeInBytes(annotation.value());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public void writeValue(Buffer buffer, Annotation annotation) {
            TIMESTAMP.write(buffer, annotation.timestamp());
            VALUE.write(buffer, annotation.value());
        }

        @Override // zipkin2.internal.Proto3ZipkinFields.SpanBuilderField
        boolean readLengthPrefixAndValue(Buffer buffer, Span.Builder builder) {
            int readLengthPrefix = readLengthPrefix(buffer);
            if (readLengthPrefix == 0) {
                return false;
            }
            int i = buffer.pos + readLengthPrefix;
            String str = null;
            long j = 0;
            while (buffer.pos < i) {
                int readVarint32 = buffer.readVarint32();
                if (readVarint32 == 9) {
                    j = TIMESTAMP.readValue(buffer);
                } else if (readVarint32 == 18) {
                    str = VALUE.readLengthPrefixAndValue(buffer);
                } else {
                    Proto3ZipkinFields.logAndSkip(buffer, readVarint32);
                }
            }
            if (j == 0 || str == null) {
                return false;
            }
            builder.addAnnotation(j, str);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class TagField extends SpanBuilderField<Map.Entry<String, String>> {
        static final int KEY_KEY = 10;
        static final int VALUE_KEY = 18;
        static final Proto3Fields.Utf8Field KEY = new Proto3Fields.Utf8Field(10);
        static final Proto3Fields.Utf8Field VALUE = new Proto3Fields.Utf8Field(18);

        TagField(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public int sizeOfValue(Map.Entry<String, String> entry) {
            return KEY.sizeInBytes(entry.getKey()) + VALUE.sizeInBytes(entry.getValue());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public void writeValue(Buffer buffer, Map.Entry<String, String> entry) {
            KEY.write(buffer, entry.getKey());
            VALUE.write(buffer, entry.getValue());
        }

        @Override // zipkin2.internal.Proto3ZipkinFields.SpanBuilderField
        boolean readLengthPrefixAndValue(Buffer buffer, Span.Builder builder) {
            int readLengthPrefix = readLengthPrefix(buffer);
            if (readLengthPrefix == 0) {
                return false;
            }
            int i = buffer.pos + readLengthPrefix;
            String str = null;
            String str2 = "";
            while (buffer.pos < i) {
                int readVarint32 = buffer.readVarint32();
                if (readVarint32 == 10) {
                    str = KEY.readLengthPrefixAndValue(buffer);
                } else if (readVarint32 == 18) {
                    String readLengthPrefixAndValue = VALUE.readLengthPrefixAndValue(buffer);
                    if (readLengthPrefixAndValue != null) {
                        str2 = readLengthPrefixAndValue;
                    }
                } else {
                    Proto3ZipkinFields.logAndSkip(buffer, readVarint32);
                }
            }
            if (str == null) {
                return false;
            }
            builder.putTag(str, str2);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class SpanField extends Proto3Fields.LengthDelimitedField<Span> {
        static final int ANNOTATION_KEY = 82;
        static final int DEBUG_KEY = 96;
        static final int DURATION_KEY = 56;
        static final int ID_KEY = 26;
        static final int KIND_KEY = 32;
        static final int LOCAL_ENDPOINT_KEY = 66;
        static final int NAME_KEY = 42;
        static final int PARENT_ID_KEY = 18;
        static final int REMOTE_ENDPOINT_KEY = 74;
        static final int SHARED_KEY = 104;
        static final int TAG_KEY = 90;
        static final int TIMESTAMP_KEY = 49;
        static final int TRACE_ID_KEY = 10;
        static final Proto3Fields.HexField TRACE_ID = new Proto3Fields.HexField(10);
        static final Proto3Fields.HexField PARENT_ID = new Proto3Fields.HexField(18);
        static final Proto3Fields.HexField ID = new Proto3Fields.HexField(26);
        static final Proto3Fields.VarintField KIND = new Proto3Fields.VarintField(32);
        static final Proto3Fields.Utf8Field NAME = new Proto3Fields.Utf8Field(42);
        static final Proto3Fields.Fixed64Field TIMESTAMP = new Proto3Fields.Fixed64Field(49);
        static final Proto3Fields.VarintField DURATION = new Proto3Fields.VarintField(56);
        static final EndpointField LOCAL_ENDPOINT = new EndpointField(66);
        static final EndpointField REMOTE_ENDPOINT = new EndpointField(74);
        static final AnnotationField ANNOTATION = new AnnotationField(82);
        static final TagField TAG = new TagField(90);
        static final Proto3Fields.BooleanField DEBUG = new Proto3Fields.BooleanField(96);
        static final Proto3Fields.BooleanField SHARED = new Proto3Fields.BooleanField(104);

        SpanField() {
            super(10);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public int sizeOfValue(Span span) {
            int sizeInBytes = TRACE_ID.sizeInBytes(span.traceId()) + PARENT_ID.sizeInBytes(span.parentId()) + ID.sizeInBytes(span.id()) + KIND.sizeInBytes(span.kind() != null ? 1 : 0) + NAME.sizeInBytes(span.name()) + TIMESTAMP.sizeInBytes(span.timestampAsLong()) + DURATION.sizeInBytes(span.durationAsLong()) + LOCAL_ENDPOINT.sizeInBytes(span.localEndpoint()) + REMOTE_ENDPOINT.sizeInBytes(span.remoteEndpoint());
            List<Annotation> annotations = span.annotations();
            int size = annotations.size();
            for (int i = 0; i < size; i++) {
                sizeInBytes += ANNOTATION.sizeInBytes(annotations.get(i));
            }
            Map<String, String> tags = span.tags();
            if (tags.size() > 0) {
                for (Map.Entry<String, String> entry : tags.entrySet()) {
                    sizeInBytes += TAG.sizeInBytes(entry);
                }
            }
            return sizeInBytes + DEBUG.sizeInBytes(Boolean.TRUE.equals(span.debug())) + SHARED.sizeInBytes(Boolean.TRUE.equals(span.shared()));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public void writeValue(Buffer buffer, Span span) {
            TRACE_ID.write(buffer, span.traceId());
            PARENT_ID.write(buffer, span.parentId());
            ID.write(buffer, span.id());
            KIND.write(buffer, toByte(span.kind()));
            NAME.write(buffer, span.name());
            TIMESTAMP.write(buffer, span.timestampAsLong());
            DURATION.write(buffer, span.durationAsLong());
            LOCAL_ENDPOINT.write(buffer, span.localEndpoint());
            REMOTE_ENDPOINT.write(buffer, span.remoteEndpoint());
            List<Annotation> annotations = span.annotations();
            int size = annotations.size();
            for (int i = 0; i < size; i++) {
                ANNOTATION.write(buffer, annotations.get(i));
            }
            Map<String, String> tags = span.tags();
            if (!tags.isEmpty()) {
                for (Map.Entry<String, String> entry : tags.entrySet()) {
                    TAG.write(buffer, entry);
                }
            }
            DEBUG.write(buffer, Boolean.TRUE.equals(span.debug()));
            SHARED.write(buffer, Boolean.TRUE.equals(span.shared()));
        }

        int toByte(Span.Kind kind) {
            if (kind != null) {
                return kind.ordinal() + 1;
            }
            return 0;
        }

        public Span read(Buffer buffer) {
            buffer.readVarint32();
            return readLengthPrefixAndValue(buffer);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public Span readValue(Buffer buffer, int i) {
            int i2 = buffer.pos + i;
            Span.Builder newBuilder = Span.newBuilder();
            while (buffer.pos < i2) {
                int readVarint32 = buffer.readVarint32();
                switch (readVarint32) {
                    case 10:
                        newBuilder.traceId(TRACE_ID.readLengthPrefixAndValue(buffer));
                        break;
                    case 18:
                        newBuilder.parentId(PARENT_ID.readLengthPrefixAndValue(buffer));
                        break;
                    case 26:
                        newBuilder.id(ID.readLengthPrefixAndValue(buffer));
                        break;
                    case 32:
                        int readVarint322 = buffer.readVarint32();
                        if (readVarint322 != 0 && readVarint322 <= Span.Kind.values().length) {
                            newBuilder.kind(Span.Kind.values()[readVarint322 - 1]);
                            break;
                        }
                        break;
                    case 42:
                        newBuilder.name(NAME.readLengthPrefixAndValue(buffer));
                        break;
                    case 49:
                        newBuilder.timestamp(TIMESTAMP.readValue(buffer));
                        break;
                    case 56:
                        newBuilder.duration(buffer.readVarint64());
                        break;
                    case 66:
                        newBuilder.localEndpoint(LOCAL_ENDPOINT.readLengthPrefixAndValue(buffer));
                        break;
                    case 74:
                        newBuilder.remoteEndpoint(REMOTE_ENDPOINT.readLengthPrefixAndValue(buffer));
                        break;
                    case 82:
                        ANNOTATION.readLengthPrefixAndValue(buffer, newBuilder);
                        break;
                    case 90:
                        TAG.readLengthPrefixAndValue(buffer, newBuilder);
                        break;
                    case 96:
                        if (!DEBUG.read(buffer)) {
                            break;
                        } else {
                            newBuilder.debug(true);
                            break;
                        }
                    case 104:
                        if (!SHARED.read(buffer)) {
                            break;
                        } else {
                            newBuilder.shared(true);
                            break;
                        }
                    default:
                        Proto3ZipkinFields.logAndSkip(buffer, readVarint32);
                        break;
                }
            }
            return newBuilder.build();
        }
    }

    static void logAndSkip(Buffer buffer, int i) {
        int wireType = Proto3Fields.Field.wireType(i, buffer.pos);
        Logger logger = LOG;
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("Skipping field: byte=%s, fieldNumber=%s, wireType=%s", Integer.valueOf(buffer.pos), Integer.valueOf(Proto3Fields.Field.fieldNumber(i, buffer.pos)), Integer.valueOf(wireType)));
        }
        Proto3Fields.Field.skipValue(buffer, wireType);
    }
}
