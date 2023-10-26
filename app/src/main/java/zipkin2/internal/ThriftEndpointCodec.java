package zipkin2.internal;

import java.nio.ByteBuffer;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import zipkin2.Endpoint;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class ThriftEndpointCodec {
    static final byte[] INT_ZERO = {0, 0, 0, 0};
    static final ThriftField IPV4 = new ThriftField((byte) 8, 1);
    static final ThriftField PORT = new ThriftField((byte) 6, 2);
    static final ThriftField SERVICE_NAME = new ThriftField(MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 3);
    static final ThriftField IPV6 = new ThriftField(MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 4);

    ThriftEndpointCodec() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Endpoint read(ByteBuffer byteBuffer) {
        Endpoint.Builder newBuilder = Endpoint.newBuilder();
        while (true) {
            ThriftField read = ThriftField.read(byteBuffer);
            if (read.type != 0) {
                if (read.isEqualTo(IPV4)) {
                    int i = byteBuffer.getInt();
                    if (i != 0) {
                        newBuilder.parseIp(new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)});
                    }
                } else if (read.isEqualTo(PORT)) {
                    newBuilder.port(byteBuffer.getShort() & 65535);
                } else if (read.isEqualTo(SERVICE_NAME)) {
                    newBuilder.serviceName(ThriftCodec.readUtf8(byteBuffer));
                } else if (read.isEqualTo(IPV6)) {
                    newBuilder.parseIp(ThriftCodec.readByteArray(byteBuffer));
                } else {
                    ThriftCodec.skip(byteBuffer, read.type);
                }
            } else {
                return newBuilder.build();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int sizeInBytes(Endpoint endpoint) {
        String serviceName = endpoint.serviceName();
        int utf8SizeInBytes = 12 + (serviceName != null ? Buffer.utf8SizeInBytes(serviceName) : 0) + 7;
        if (endpoint.ipv6() != null) {
            utf8SizeInBytes += 23;
        }
        return utf8SizeInBytes + 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void write(Endpoint endpoint, Buffer buffer) {
        IPV4.write(buffer);
        buffer.write(endpoint.ipv4Bytes() != null ? endpoint.ipv4Bytes() : INT_ZERO);
        PORT.write(buffer);
        int portAsInt = endpoint.portAsInt();
        buffer.writeByte((portAsInt >>> 8) & 255);
        buffer.writeByte(portAsInt & 255);
        SERVICE_NAME.write(buffer);
        ThriftCodec.writeLengthPrefixed(buffer, endpoint.serviceName() != null ? endpoint.serviceName() : "");
        byte[] ipv6Bytes = endpoint.ipv6Bytes();
        if (ipv6Bytes != null) {
            IPV6.write(buffer);
            ThriftCodec.writeInt(buffer, 16);
            buffer.write(ipv6Bytes);
        }
        buffer.writeByte(0);
    }
}
