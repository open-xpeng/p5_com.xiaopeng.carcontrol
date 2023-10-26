package zipkin2.internal;

import java.util.List;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import zipkin2.Endpoint;
import zipkin2.Span;
import zipkin2.internal.Buffer;
import zipkin2.v1.V1Annotation;
import zipkin2.v1.V1BinaryAnnotation;
import zipkin2.v1.V1Span;
import zipkin2.v1.V2SpanConverter;

/* loaded from: classes3.dex */
public final class V1ThriftSpanWriter implements Buffer.Writer<Span> {
    final V2SpanConverter converter = V2SpanConverter.create();
    static final ThriftField TRACE_ID = new ThriftField((byte) 10, 1);
    static final ThriftField TRACE_ID_HIGH = new ThriftField((byte) 10, 12);
    static final ThriftField NAME = new ThriftField(MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 3);
    static final ThriftField ID = new ThriftField((byte) 10, 4);
    static final ThriftField PARENT_ID = new ThriftField((byte) 10, 5);
    static final ThriftField ANNOTATIONS = new ThriftField((byte) 15, 6);
    static final ThriftField BINARY_ANNOTATIONS = new ThriftField((byte) 15, 8);
    static final ThriftField DEBUG = new ThriftField((byte) 2, 9);
    static final ThriftField TIMESTAMP = new ThriftField((byte) 10, 10);
    static final ThriftField DURATION = new ThriftField((byte) 10, 11);
    static final byte[] EMPTY_ARRAY = new byte[0];

    public String toString() {
        return "Span";
    }

    @Override // zipkin2.internal.Buffer.Writer
    public int sizeInBytes(Span span) {
        int sizeInBytes;
        V1Span convert = this.converter.convert(span);
        int sizeInBytes2 = span.localEndpoint() != null ? ThriftEndpointCodec.sizeInBytes(span.localEndpoint()) : 0;
        int i = convert.traceIdHigh() != 0 ? 22 : 11;
        if (convert.parentId() != 0) {
            i += 11;
        }
        int i2 = i + 11 + 7;
        if (span.name() != null) {
            i2 += Buffer.utf8SizeInBytes(span.name());
        }
        int i3 = i2 + 8;
        int size = convert.annotations().size();
        for (int i4 = 0; i4 < size; i4++) {
            i3 += ThriftAnnotationWriter.sizeInBytes(Buffer.utf8SizeInBytes(convert.annotations().get(i4).value()), sizeInBytes2);
        }
        int i5 = i3 + 8;
        int size2 = convert.binaryAnnotations().size();
        for (int i6 = 0; i6 < size2; i6++) {
            V1BinaryAnnotation v1BinaryAnnotation = convert.binaryAnnotations().get(i6);
            int utf8SizeInBytes = Buffer.utf8SizeInBytes(v1BinaryAnnotation.key());
            if (v1BinaryAnnotation.stringValue() != null) {
                sizeInBytes = ThriftBinaryAnnotationWriter.sizeInBytes(utf8SizeInBytes, Buffer.utf8SizeInBytes(v1BinaryAnnotation.stringValue()), sizeInBytes2);
            } else {
                sizeInBytes = ThriftBinaryAnnotationWriter.sizeInBytes(utf8SizeInBytes, 1, ThriftEndpointCodec.sizeInBytes(v1BinaryAnnotation.endpoint()));
            }
            i5 += sizeInBytes;
        }
        if (convert.debug() != null) {
            i5 += 4;
        }
        if (convert.timestamp() != 0) {
            i5 += 11;
        }
        if (convert.duration() != 0) {
            i5 += 11;
        }
        return i5 + 1;
    }

    @Override // zipkin2.internal.Buffer.Writer
    public void write(Span span, Buffer buffer) {
        V1Span convert = this.converter.convert(span);
        byte[] legacyEndpointBytes = legacyEndpointBytes(span.localEndpoint());
        TRACE_ID.write(buffer);
        ThriftCodec.writeLong(buffer, convert.traceId());
        NAME.write(buffer);
        ThriftCodec.writeLengthPrefixed(buffer, span.name() != null ? span.name() : "");
        ID.write(buffer);
        ThriftCodec.writeLong(buffer, convert.id());
        if (convert.parentId() != 0) {
            PARENT_ID.write(buffer);
            ThriftCodec.writeLong(buffer, convert.parentId());
        }
        ANNOTATIONS.write(buffer);
        writeAnnotations(buffer, convert, legacyEndpointBytes);
        BINARY_ANNOTATIONS.write(buffer);
        writeBinaryAnnotations(buffer, convert, legacyEndpointBytes);
        if (convert.debug() != null) {
            DEBUG.write(buffer);
            buffer.writeByte(convert.debug().booleanValue() ? 1 : 0);
        }
        if (convert.timestamp() != 0) {
            TIMESTAMP.write(buffer);
            ThriftCodec.writeLong(buffer, convert.timestamp());
        }
        if (convert.duration() != 0) {
            DURATION.write(buffer);
            ThriftCodec.writeLong(buffer, convert.duration());
        }
        if (convert.traceIdHigh() != 0) {
            TRACE_ID_HIGH.write(buffer);
            ThriftCodec.writeLong(buffer, convert.traceIdHigh());
        }
        buffer.writeByte(0);
    }

    static void writeAnnotations(Buffer buffer, V1Span v1Span, byte[] bArr) {
        int size = v1Span.annotations().size();
        ThriftCodec.writeListBegin(buffer, size);
        for (int i = 0; i < size; i++) {
            V1Annotation v1Annotation = v1Span.annotations().get(i);
            ThriftAnnotationWriter.write(v1Annotation.timestamp(), v1Annotation.value(), bArr, buffer);
        }
    }

    static void writeBinaryAnnotations(Buffer buffer, V1Span v1Span, byte[] bArr) {
        int size = v1Span.binaryAnnotations().size();
        ThriftCodec.writeListBegin(buffer, size);
        for (int i = 0; i < size; i++) {
            V1BinaryAnnotation v1BinaryAnnotation = v1Span.binaryAnnotations().get(i);
            ThriftBinaryAnnotationWriter.write(v1BinaryAnnotation.key(), v1BinaryAnnotation.stringValue(), v1BinaryAnnotation.stringValue() != null ? bArr : legacyEndpointBytes(v1BinaryAnnotation.endpoint()), buffer);
        }
    }

    public byte[] writeList(List<Span> list) {
        if (list.size() == 0) {
            return EMPTY_ARRAY;
        }
        Buffer buffer = new Buffer(ThriftCodec.listSizeInBytes(this, list));
        ThriftCodec.writeList(this, list, buffer);
        return buffer.toByteArray();
    }

    public byte[] write(Span span) {
        Buffer buffer = new Buffer(sizeInBytes(span));
        write(span, buffer);
        return buffer.toByteArray();
    }

    public int writeList(List<Span> list, byte[] bArr, int i) {
        if (list.size() == 0) {
            return 0;
        }
        Buffer buffer = new Buffer(bArr, i);
        ThriftCodec.writeList(this, list, buffer);
        return buffer.pos() - i;
    }

    static byte[] legacyEndpointBytes(@Nullable Endpoint endpoint) {
        if (endpoint == null) {
            return null;
        }
        Buffer buffer = new Buffer(ThriftEndpointCodec.sizeInBytes(endpoint));
        ThriftEndpointCodec.write(endpoint, buffer);
        return buffer.toByteArray();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class ThriftAnnotationWriter {
        static final ThriftField TIMESTAMP = new ThriftField((byte) 10, 1);
        static final ThriftField VALUE = new ThriftField(MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 2);
        static final ThriftField ENDPOINT = new ThriftField(MqttWireMessage.MESSAGE_TYPE_PINGREQ, 3);

        static int sizeInBytes(int i, int i2) {
            int i3 = 11 + i + 7;
            if (i2 > 0) {
                i3 += i2 + 3;
            }
            return i3 + 1;
        }

        ThriftAnnotationWriter() {
        }

        static void write(long j, String str, byte[] bArr, Buffer buffer) {
            TIMESTAMP.write(buffer);
            ThriftCodec.writeLong(buffer, j);
            VALUE.write(buffer);
            ThriftCodec.writeLengthPrefixed(buffer, str);
            if (bArr != null) {
                ENDPOINT.write(buffer);
                buffer.write(bArr);
            }
            buffer.writeByte(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class ThriftBinaryAnnotationWriter {
        static final ThriftField KEY = new ThriftField(MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 1);
        static final ThriftField VALUE = new ThriftField(MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 2);
        static final ThriftField TYPE = new ThriftField((byte) 8, 3);
        static final ThriftField ENDPOINT = new ThriftField(MqttWireMessage.MESSAGE_TYPE_PINGREQ, 4);

        static int sizeInBytes(int i, int i2, int i3) {
            int i4 = i + 7 + 0 + i2 + 7 + 7;
            if (i3 > 0) {
                i4 += i3 + 3;
            }
            return i4 + 1;
        }

        ThriftBinaryAnnotationWriter() {
        }

        static void write(String str, String str2, byte[] bArr, Buffer buffer) {
            int i;
            KEY.write(buffer);
            ThriftCodec.writeLengthPrefixed(buffer, str);
            VALUE.write(buffer);
            if (str2 != null) {
                i = 6;
                ThriftCodec.writeInt(buffer, Buffer.utf8SizeInBytes(str2));
                buffer.writeUtf8(str2);
            } else {
                ThriftCodec.writeInt(buffer, 1);
                buffer.writeByte(1);
                i = 0;
            }
            TYPE.write(buffer);
            ThriftCodec.writeInt(buffer, i);
            if (bArr != null) {
                ENDPOINT.write(buffer);
                buffer.write(bArr);
            }
            buffer.writeByte(0);
        }
    }
}
