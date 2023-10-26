package zipkin2.internal;

import java.nio.ByteBuffer;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import zipkin2.Endpoint;
import zipkin2.v1.V1Span;

/* loaded from: classes3.dex */
public final class V1ThriftSpanReader {
    V1Span.Builder builder = V1Span.newBuilder();

    public static V1ThriftSpanReader create() {
        return new V1ThriftSpanReader();
    }

    public V1Span read(ByteBuffer byteBuffer) {
        V1Span.Builder builder = this.builder;
        if (builder == null) {
            this.builder = V1Span.newBuilder();
        } else {
            builder.clear();
        }
        while (true) {
            ThriftField read = ThriftField.read(byteBuffer);
            if (read.type != 0) {
                if (read.isEqualTo(V1ThriftSpanWriter.TRACE_ID_HIGH)) {
                    this.builder.traceIdHigh(byteBuffer.getLong());
                } else if (read.isEqualTo(V1ThriftSpanWriter.TRACE_ID)) {
                    this.builder.traceId(byteBuffer.getLong());
                } else if (read.isEqualTo(V1ThriftSpanWriter.NAME)) {
                    this.builder.name(ThriftCodec.readUtf8(byteBuffer));
                } else if (read.isEqualTo(V1ThriftSpanWriter.ID)) {
                    this.builder.id(byteBuffer.getLong());
                } else if (read.isEqualTo(V1ThriftSpanWriter.PARENT_ID)) {
                    this.builder.parentId(byteBuffer.getLong());
                } else {
                    if (read.isEqualTo(V1ThriftSpanWriter.ANNOTATIONS)) {
                        int readListLength = ThriftCodec.readListLength(byteBuffer);
                        for (int i = 0; i < readListLength; i++) {
                            AnnotationReader.read(byteBuffer, this.builder);
                        }
                    } else if (read.isEqualTo(V1ThriftSpanWriter.BINARY_ANNOTATIONS)) {
                        int readListLength2 = ThriftCodec.readListLength(byteBuffer);
                        for (int i2 = 0; i2 < readListLength2; i2++) {
                            BinaryAnnotationReader.read(byteBuffer, this.builder);
                        }
                    } else if (read.isEqualTo(V1ThriftSpanWriter.DEBUG)) {
                        this.builder.debug(Boolean.valueOf(byteBuffer.get() == 1));
                    } else if (read.isEqualTo(V1ThriftSpanWriter.TIMESTAMP)) {
                        this.builder.timestamp(byteBuffer.getLong());
                    } else if (read.isEqualTo(V1ThriftSpanWriter.DURATION)) {
                        this.builder.duration(byteBuffer.getLong());
                    } else {
                        ThriftCodec.skip(byteBuffer, read.type);
                    }
                }
            } else {
                return this.builder.build();
            }
        }
    }

    /* loaded from: classes3.dex */
    static final class AnnotationReader {
        static final ThriftField TIMESTAMP = new ThriftField((byte) 10, 1);
        static final ThriftField VALUE = new ThriftField(MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 2);
        static final ThriftField ENDPOINT = new ThriftField(MqttWireMessage.MESSAGE_TYPE_PINGREQ, 3);

        AnnotationReader() {
        }

        static void read(ByteBuffer byteBuffer, V1Span.Builder builder) {
            String str = null;
            Endpoint endpoint = null;
            long j = 0;
            while (true) {
                ThriftField read = ThriftField.read(byteBuffer);
                if (read.type == 0) {
                    break;
                } else if (read.isEqualTo(TIMESTAMP)) {
                    j = byteBuffer.getLong();
                } else if (read.isEqualTo(VALUE)) {
                    str = ThriftCodec.readUtf8(byteBuffer);
                } else if (read.isEqualTo(ENDPOINT)) {
                    endpoint = ThriftEndpointCodec.read(byteBuffer);
                } else {
                    ThriftCodec.skip(byteBuffer, read.type);
                }
            }
            if (j == 0 || str == null) {
                return;
            }
            builder.addAnnotation(j, str, endpoint);
        }
    }

    /* loaded from: classes3.dex */
    static final class BinaryAnnotationReader {
        static final ThriftField KEY = new ThriftField(MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 1);
        static final ThriftField VALUE = new ThriftField(MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 2);
        static final ThriftField TYPE = new ThriftField((byte) 8, 3);
        static final ThriftField ENDPOINT = new ThriftField(MqttWireMessage.MESSAGE_TYPE_PINGREQ, 4);

        BinaryAnnotationReader() {
        }

        static void read(ByteBuffer byteBuffer, V1Span.Builder builder) {
            String str = null;
            boolean z = false;
            boolean z2 = false;
            byte[] bArr = null;
            Endpoint endpoint = null;
            while (true) {
                ThriftField read = ThriftField.read(byteBuffer);
                if (read.type == 0) {
                    break;
                } else if (read.isEqualTo(KEY)) {
                    str = ThriftCodec.readUtf8(byteBuffer);
                } else if (read.isEqualTo(VALUE)) {
                    bArr = ThriftCodec.readByteArray(byteBuffer);
                } else if (read.isEqualTo(TYPE)) {
                    int i = byteBuffer.getInt();
                    if (i == 0) {
                        z2 = true;
                    } else if (i == 6) {
                        z = true;
                    }
                } else if (read.isEqualTo(ENDPOINT)) {
                    endpoint = ThriftEndpointCodec.read(byteBuffer);
                } else {
                    ThriftCodec.skip(byteBuffer, read.type);
                }
            }
            if (str == null || bArr == null) {
                return;
            }
            if (z) {
                builder.addBinaryAnnotation(str, new String(bArr, ThriftCodec.UTF_8), endpoint);
            } else if (z2 && bArr.length == 1 && bArr[0] == 1 && endpoint != null) {
                if (str.equals("sa") || str.equals("ca") || str.equals("ma")) {
                    builder.addBinaryAnnotation(str, endpoint);
                }
            }
        }
    }
}
