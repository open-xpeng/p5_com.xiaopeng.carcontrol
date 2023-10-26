package zipkin2.reporter;

import java.util.List;
import java.util.Objects;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import zipkin2.codec.Encoding;

/* loaded from: classes3.dex */
public enum BytesMessageEncoder {
    JSON { // from class: zipkin2.reporter.BytesMessageEncoder.1
        @Override // zipkin2.reporter.BytesMessageEncoder
        public byte[] encode(List<byte[]> list) {
            int size = list.size();
            int i = 2;
            int i2 = 0;
            while (i2 < size) {
                int i3 = i2 + 1;
                i += list.get(i2).length;
                if (i3 < size) {
                    i++;
                }
                i2 = i3;
            }
            byte[] bArr = new byte[i];
            bArr[0] = 91;
            int i4 = 1;
            int i5 = 0;
            while (i5 < size) {
                int i6 = i5 + 1;
                byte[] bArr2 = list.get(i5);
                System.arraycopy(bArr2, 0, bArr, i4, bArr2.length);
                i4 += bArr2.length;
                if (i6 < size) {
                    bArr[i4] = 44;
                    i4++;
                }
                i5 = i6;
            }
            bArr[i4] = 93;
            return bArr;
        }
    },
    THRIFT { // from class: zipkin2.reporter.BytesMessageEncoder.2
        @Override // zipkin2.reporter.BytesMessageEncoder
        public byte[] encode(List<byte[]> list) {
            int size = list.size();
            int i = 5;
            int i2 = 5;
            for (int i3 = 0; i3 < size; i3++) {
                i2 += list.get(i3).length;
            }
            byte[] bArr = new byte[i2];
            bArr[0] = MqttWireMessage.MESSAGE_TYPE_PINGREQ;
            bArr[1] = (byte) ((size >>> 24) & 255);
            bArr[2] = (byte) ((size >>> 16) & 255);
            bArr[3] = (byte) ((size >>> 8) & 255);
            bArr[4] = (byte) (size & 255);
            int i4 = 0;
            while (i4 < size) {
                int i5 = i4 + 1;
                byte[] bArr2 = list.get(i4);
                System.arraycopy(bArr2, 0, bArr, i, bArr2.length);
                i += bArr2.length;
                i4 = i5;
            }
            return bArr;
        }
    },
    PROTO3 { // from class: zipkin2.reporter.BytesMessageEncoder.3
        @Override // zipkin2.reporter.BytesMessageEncoder
        public byte[] encode(List<byte[]> list) {
            int size = list.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                i += list.get(i2).length;
            }
            byte[] bArr = new byte[i];
            int i3 = 0;
            int i4 = 0;
            while (i3 < size) {
                int i5 = i3 + 1;
                byte[] bArr2 = list.get(i3);
                System.arraycopy(bArr2, 0, bArr, i4, bArr2.length);
                i4 += bArr2.length;
                i3 = i5;
            }
            return bArr;
        }
    };

    public abstract byte[] encode(List<byte[]> list);

    /* renamed from: zipkin2.reporter.BytesMessageEncoder$4  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$zipkin2$codec$Encoding;

        static {
            int[] iArr = new int[Encoding.values().length];
            $SwitchMap$zipkin2$codec$Encoding = iArr;
            try {
                iArr[Encoding.JSON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$zipkin2$codec$Encoding[Encoding.PROTO3.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$zipkin2$codec$Encoding[Encoding.THRIFT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static BytesMessageEncoder forEncoding(Encoding encoding) {
        Objects.requireNonNull(encoding, "encoding == null");
        int i = AnonymousClass4.$SwitchMap$zipkin2$codec$Encoding[encoding.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return THRIFT;
                }
                throw new UnsupportedOperationException(encoding.name());
            }
            return PROTO3;
        }
        return JSON;
    }
}
