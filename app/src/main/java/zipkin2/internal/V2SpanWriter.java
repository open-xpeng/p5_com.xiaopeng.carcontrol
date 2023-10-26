package zipkin2.internal;

import java.util.Iterator;
import java.util.Map;
import zipkin2.Annotation;
import zipkin2.Endpoint;
import zipkin2.Span;
import zipkin2.internal.Buffer;

/* loaded from: classes3.dex */
public final class V2SpanWriter implements Buffer.Writer<Span> {
    public String toString() {
        return "Span";
    }

    @Override // zipkin2.internal.Buffer.Writer
    public int sizeInBytes(Span span) {
        int length = span.traceId().length() + 13;
        if (span.parentId() != null) {
            length += 30;
        }
        int i = length + 24;
        if (span.kind() != null) {
            i = i + 10 + span.kind().name().length();
        }
        if (span.name() != null) {
            i = i + 10 + JsonEscaper.jsonEscapedSizeInBytes(span.name());
        }
        if (span.timestampAsLong() != 0) {
            i = i + 13 + Buffer.asciiSizeInBytes(span.timestampAsLong());
        }
        if (span.durationAsLong() != 0) {
            i = i + 12 + Buffer.asciiSizeInBytes(span.durationAsLong());
        }
        if (span.localEndpoint() != null) {
            i = i + 17 + endpointSizeInBytes(span.localEndpoint(), false);
        }
        if (span.remoteEndpoint() != null) {
            i = i + 18 + endpointSizeInBytes(span.remoteEndpoint(), false);
        }
        if (!span.annotations().isEmpty()) {
            i += 17;
            int size = span.annotations().size();
            if (size > 1) {
                i += size - 1;
            }
            for (int i2 = 0; i2 < size; i2++) {
                Annotation annotation = span.annotations().get(i2);
                i += annotationSizeInBytes(annotation.timestamp(), annotation.value(), 0);
            }
        }
        if (!span.tags().isEmpty()) {
            i += 10;
            int size2 = span.tags().size();
            if (size2 > 1) {
                i += size2 - 1;
            }
            for (Map.Entry<String, String> entry : span.tags().entrySet()) {
                i = i + 5 + JsonEscaper.jsonEscapedSizeInBytes(entry.getKey()) + JsonEscaper.jsonEscapedSizeInBytes(entry.getValue());
            }
        }
        if (Boolean.TRUE.equals(span.debug())) {
            i += 13;
        }
        if (Boolean.TRUE.equals(span.shared())) {
            i += 14;
        }
        return i + 1;
    }

    @Override // zipkin2.internal.Buffer.Writer
    public void write(Span span, Buffer buffer) {
        buffer.writeAscii("{\"traceId\":\"").writeAscii(span.traceId()).writeByte(34);
        if (span.parentId() != null) {
            buffer.writeAscii(",\"parentId\":\"").writeAscii(span.parentId()).writeByte(34);
        }
        buffer.writeAscii(",\"id\":\"").writeAscii(span.id()).writeByte(34);
        if (span.kind() != null) {
            buffer.writeAscii(",\"kind\":\"").writeAscii(span.kind().toString()).writeByte(34);
        }
        if (span.name() != null) {
            buffer.writeAscii(",\"name\":\"").writeUtf8(JsonEscaper.jsonEscape(span.name())).writeByte(34);
        }
        if (span.timestampAsLong() != 0) {
            buffer.writeAscii(",\"timestamp\":").writeAscii(span.timestampAsLong());
        }
        if (span.durationAsLong() != 0) {
            buffer.writeAscii(",\"duration\":").writeAscii(span.durationAsLong());
        }
        int i = 0;
        if (span.localEndpoint() != null) {
            buffer.writeAscii(",\"localEndpoint\":");
            writeEndpoint(span.localEndpoint(), buffer, false);
        }
        if (span.remoteEndpoint() != null) {
            buffer.writeAscii(",\"remoteEndpoint\":");
            writeEndpoint(span.remoteEndpoint(), buffer, false);
        }
        if (!span.annotations().isEmpty()) {
            buffer.writeAscii(",\"annotations\":");
            buffer.writeByte(91);
            int size = span.annotations().size();
            while (i < size) {
                int i2 = i + 1;
                Annotation annotation = span.annotations().get(i);
                writeAnnotation(annotation.timestamp(), annotation.value(), null, buffer);
                if (i2 < size) {
                    buffer.writeByte(44);
                }
                i = i2;
            }
            buffer.writeByte(93);
        }
        if (!span.tags().isEmpty()) {
            buffer.writeAscii(",\"tags\":{");
            Iterator<Map.Entry<String, String>> it = span.tags().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> next = it.next();
                buffer.writeByte(34).writeUtf8(JsonEscaper.jsonEscape(next.getKey())).writeAscii("\":\"");
                buffer.writeUtf8(JsonEscaper.jsonEscape(next.getValue())).writeByte(34);
                if (it.hasNext()) {
                    buffer.writeByte(44);
                }
            }
            buffer.writeByte(125);
        }
        if (Boolean.TRUE.equals(span.debug())) {
            buffer.writeAscii(",\"debug\":true");
        }
        if (Boolean.TRUE.equals(span.shared())) {
            buffer.writeAscii(",\"shared\":true");
        }
        buffer.writeByte(125);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int endpointSizeInBytes(Endpoint endpoint, boolean z) {
        String serviceName = endpoint.serviceName();
        if (serviceName == null && z) {
            serviceName = "";
        }
        int jsonEscapedSizeInBytes = serviceName != null ? 17 + JsonEscaper.jsonEscapedSizeInBytes(serviceName) : 1;
        if (endpoint.ipv4() != null) {
            if (jsonEscapedSizeInBytes != 1) {
                jsonEscapedSizeInBytes++;
            }
            jsonEscapedSizeInBytes = jsonEscapedSizeInBytes + 9 + endpoint.ipv4().length();
        }
        if (endpoint.ipv6() != null) {
            if (jsonEscapedSizeInBytes != 1) {
                jsonEscapedSizeInBytes++;
            }
            jsonEscapedSizeInBytes = jsonEscapedSizeInBytes + 9 + endpoint.ipv6().length();
        }
        int portAsInt = endpoint.portAsInt();
        if (portAsInt != 0) {
            if (jsonEscapedSizeInBytes != 1) {
                jsonEscapedSizeInBytes++;
            }
            jsonEscapedSizeInBytes = jsonEscapedSizeInBytes + 7 + Buffer.asciiSizeInBytes(portAsInt);
        }
        return jsonEscapedSizeInBytes + 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void writeEndpoint(Endpoint endpoint, Buffer buffer, boolean z) {
        boolean z2;
        buffer.writeByte(123);
        String serviceName = endpoint.serviceName();
        if (serviceName == null && z) {
            serviceName = "";
        }
        boolean z3 = true;
        if (serviceName != null) {
            buffer.writeAscii("\"serviceName\":\"");
            buffer.writeUtf8(JsonEscaper.jsonEscape(serviceName)).writeByte(34);
            z2 = true;
        } else {
            z2 = false;
        }
        if (endpoint.ipv4() != null) {
            if (z2) {
                buffer.writeByte(44);
            }
            buffer.writeAscii("\"ipv4\":\"");
            buffer.writeAscii(endpoint.ipv4()).writeByte(34);
            z2 = true;
        }
        if (endpoint.ipv6() != null) {
            if (z2) {
                buffer.writeByte(44);
            }
            buffer.writeAscii("\"ipv6\":\"");
            buffer.writeAscii(endpoint.ipv6()).writeByte(34);
        } else {
            z3 = z2;
        }
        int portAsInt = endpoint.portAsInt();
        if (portAsInt != 0) {
            if (z3) {
                buffer.writeByte(44);
            }
            buffer.writeAscii("\"port\":").writeAscii(portAsInt);
        }
        buffer.writeByte(125);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int annotationSizeInBytes(long j, String str, int i) {
        int asciiSizeInBytes = Buffer.asciiSizeInBytes(j) + 25 + JsonEscaper.jsonEscapedSizeInBytes(str);
        return i != 0 ? asciiSizeInBytes + 12 + i : asciiSizeInBytes;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void writeAnnotation(long j, String str, @Nullable byte[] bArr, Buffer buffer) {
        buffer.writeAscii("{\"timestamp\":").writeAscii(j);
        buffer.writeAscii(",\"value\":\"").writeUtf8(JsonEscaper.jsonEscape(str)).writeByte(34);
        if (bArr != null) {
            buffer.writeAscii(",\"endpoint\":").write(bArr);
        }
        buffer.writeByte(125);
    }
}
