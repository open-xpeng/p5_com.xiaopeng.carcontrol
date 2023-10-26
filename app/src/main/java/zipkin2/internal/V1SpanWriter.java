package zipkin2.internal;

import zipkin2.Endpoint;
import zipkin2.internal.Buffer;
import zipkin2.v1.V1Annotation;
import zipkin2.v1.V1BinaryAnnotation;
import zipkin2.v1.V1Span;

/* loaded from: classes3.dex */
public final class V1SpanWriter implements Buffer.Writer<V1Span> {
    public String toString() {
        return "Span";
    }

    @Override // zipkin2.internal.Buffer.Writer
    public int sizeInBytes(V1Span v1Span) {
        int i;
        int i2;
        int i3;
        int i4 = v1Span.traceIdHigh() != 0 ? 45 : 29;
        if (v1Span.parentId() != 0) {
            i4 += 30;
        }
        int i5 = i4 + 24 + 10;
        if (v1Span.name() != null) {
            i5 += JsonEscaper.jsonEscapedSizeInBytes(v1Span.name());
        }
        if (v1Span.timestamp() != 0) {
            i5 = i5 + 13 + Buffer.asciiSizeInBytes(v1Span.timestamp());
        }
        if (v1Span.duration() != 0) {
            i5 = i5 + 12 + Buffer.asciiSizeInBytes(v1Span.duration());
        }
        int size = v1Span.annotations().size();
        Endpoint endpoint = null;
        if (size > 0) {
            i5 += 17;
            if (size > 1) {
                i5 += size - 1;
            }
            int i6 = 0;
            i = 0;
            while (i6 < size) {
                V1Annotation v1Annotation = v1Span.annotations().get(i6);
                Endpoint endpoint2 = v1Annotation.endpoint();
                if (endpoint2 == null) {
                    i3 = i;
                    i = 0;
                } else {
                    if (!endpoint2.equals(endpoint)) {
                        i = V2SpanWriter.endpointSizeInBytes(endpoint2, true);
                        endpoint = endpoint2;
                    }
                    i3 = i;
                }
                i5 += V2SpanWriter.annotationSizeInBytes(v1Annotation.timestamp(), v1Annotation.value(), i);
                i6++;
                i = i3;
            }
        } else {
            i = 0;
        }
        int size2 = v1Span.binaryAnnotations().size();
        if (size2 > 0) {
            i5 += 23;
            if (size2 > 1) {
                i5 += size2 - 1;
            }
            int i7 = 0;
            while (i7 < size2) {
                int i8 = i7 + 1;
                V1BinaryAnnotation v1BinaryAnnotation = v1Span.binaryAnnotations().get(i7);
                Endpoint endpoint3 = v1BinaryAnnotation.endpoint();
                if (endpoint3 == null) {
                    i2 = i;
                    i = 0;
                } else {
                    if (!endpoint3.equals(endpoint)) {
                        i = V2SpanWriter.endpointSizeInBytes(endpoint3, true);
                        endpoint = endpoint3;
                    }
                    i2 = i;
                }
                i5 = v1BinaryAnnotation.stringValue() != null ? i5 + binaryAnnotationSizeInBytes(v1BinaryAnnotation.key(), v1BinaryAnnotation.stringValue(), i) : i5 + 37 + i;
                i = i2;
                i7 = i8;
            }
        }
        if (Boolean.TRUE.equals(v1Span.debug())) {
            i5 += 13;
        }
        return i5 + 1;
    }

    @Override // zipkin2.internal.Buffer.Writer
    public void write(V1Span v1Span, Buffer buffer) {
        Endpoint endpoint;
        byte[] bArr;
        byte[] bArr2;
        byte[] bArr3;
        buffer.writeAscii("{\"traceId\":\"");
        if (v1Span.traceIdHigh() != 0) {
            buffer.writeLongHex(v1Span.traceIdHigh());
        }
        buffer.writeLongHex(v1Span.traceId()).writeByte(34);
        if (v1Span.parentId() != 0) {
            buffer.writeAscii(",\"parentId\":\"").writeLongHex(v1Span.parentId()).writeByte(34);
        }
        buffer.writeAscii(",\"id\":\"").writeLongHex(v1Span.id()).writeByte(34);
        buffer.writeAscii(",\"name\":\"");
        if (v1Span.name() != null) {
            buffer.writeUtf8(JsonEscaper.jsonEscape(v1Span.name()));
        }
        buffer.writeByte(34);
        if (v1Span.timestamp() != 0) {
            buffer.writeAscii(",\"timestamp\":").writeAscii(v1Span.timestamp());
        }
        if (v1Span.duration() != 0) {
            buffer.writeAscii(",\"duration\":").writeAscii(v1Span.duration());
        }
        int size = v1Span.annotations().size();
        int i = 0;
        if (size > 0) {
            buffer.writeAscii(",\"annotations\":[");
            int i2 = 0;
            endpoint = null;
            bArr = null;
            while (i2 < size) {
                int i3 = i2 + 1;
                V1Annotation v1Annotation = v1Span.annotations().get(i2);
                Endpoint endpoint2 = v1Annotation.endpoint();
                if (endpoint2 == null) {
                    bArr3 = bArr;
                    bArr = null;
                } else {
                    if (!endpoint2.equals(endpoint)) {
                        bArr = legacyEndpointBytes(endpoint2);
                        endpoint = endpoint2;
                    }
                    bArr3 = bArr;
                }
                V2SpanWriter.writeAnnotation(v1Annotation.timestamp(), v1Annotation.value(), bArr, buffer);
                if (i3 < size) {
                    buffer.writeByte(44);
                }
                bArr = bArr3;
                i2 = i3;
            }
            buffer.writeByte(93);
        } else {
            endpoint = null;
            bArr = null;
        }
        int size2 = v1Span.binaryAnnotations().size();
        if (size2 > 0) {
            buffer.writeAscii(",\"binaryAnnotations\":[");
            while (i < size2) {
                int i4 = i + 1;
                V1BinaryAnnotation v1BinaryAnnotation = v1Span.binaryAnnotations().get(i);
                Endpoint endpoint3 = v1BinaryAnnotation.endpoint();
                if (endpoint3 == null) {
                    bArr2 = bArr;
                    bArr = null;
                } else {
                    if (!endpoint3.equals(endpoint)) {
                        bArr = legacyEndpointBytes(endpoint3);
                        endpoint = endpoint3;
                    }
                    bArr2 = bArr;
                }
                if (v1BinaryAnnotation.stringValue() != null) {
                    writeBinaryAnnotation(v1BinaryAnnotation.key(), v1BinaryAnnotation.stringValue(), bArr, buffer);
                } else {
                    buffer.writeAscii("{\"key\":\"").writeAscii(v1BinaryAnnotation.key());
                    buffer.writeAscii("\",\"value\":true,\"endpoint\":");
                    buffer.write(bArr);
                    buffer.writeByte(125);
                }
                if (i4 < size2) {
                    buffer.writeByte(44);
                }
                bArr = bArr2;
                i = i4;
            }
            buffer.writeByte(93);
        }
        if (Boolean.TRUE.equals(v1Span.debug())) {
            buffer.writeAscii(",\"debug\":true");
        }
        buffer.writeByte(125);
    }

    static byte[] legacyEndpointBytes(@Nullable Endpoint endpoint) {
        if (endpoint == null) {
            return null;
        }
        Buffer buffer = new Buffer(V2SpanWriter.endpointSizeInBytes(endpoint, true));
        V2SpanWriter.writeEndpoint(endpoint, buffer, true);
        return buffer.toByteArray();
    }

    static int binaryAnnotationSizeInBytes(String str, String str2, int i) {
        int jsonEscapedSizeInBytes = JsonEscaper.jsonEscapedSizeInBytes(str) + 21 + JsonEscaper.jsonEscapedSizeInBytes(str2);
        return i != 0 ? jsonEscapedSizeInBytes + 12 + i : jsonEscapedSizeInBytes;
    }

    static void writeBinaryAnnotation(String str, String str2, @Nullable byte[] bArr, Buffer buffer) {
        buffer.writeAscii("{\"key\":\"").writeUtf8(JsonEscaper.jsonEscape(str));
        buffer.writeAscii("\",\"value\":\"").writeUtf8(JsonEscaper.jsonEscape(str2)).writeByte(34);
        if (bArr != null) {
            buffer.writeAscii(",\"endpoint\":").write(bArr);
        }
        buffer.writeAscii("}");
    }
}
