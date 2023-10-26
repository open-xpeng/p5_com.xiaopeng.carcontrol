package zipkin2;

import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Locale;
import zipkin2.internal.Nullable;

/* loaded from: classes3.dex */
public final class Endpoint implements Serializable {
    private static final int IPV6_PART_COUNT = 8;
    private static final long serialVersionUID = 0;
    final String ipv4;
    final byte[] ipv4Bytes;
    final String ipv6;
    final byte[] ipv6Bytes;
    final int port;
    final String serviceName;
    private static final ThreadLocal<char[]> IPV6_TO_STRING = new ThreadLocal<char[]>() { // from class: zipkin2.Endpoint.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public char[] initialValue() {
            return new char[39];
        }
    };
    static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public enum IpFamily {
        Unknown,
        IPv4,
        IPv4Embedded,
        IPv6
    }

    private static boolean isValidNumericChar(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean notHex(char c) {
        return (c < '0' || c > '9') && (c < 'a' || c > 'f') && (c < 'A' || c > 'F');
    }

    @Nullable
    public String serviceName() {
        return this.serviceName;
    }

    @Nullable
    public String ipv4() {
        return this.ipv4;
    }

    @Nullable
    public byte[] ipv4Bytes() {
        return this.ipv4Bytes;
    }

    @Nullable
    public String ipv6() {
        return this.ipv6;
    }

    @Nullable
    public byte[] ipv6Bytes() {
        return this.ipv6Bytes;
    }

    @Nullable
    public Integer port() {
        int i = this.port;
        if (i != 0) {
            return Integer.valueOf(i);
        }
        return null;
    }

    public int portAsInt() {
        return this.port;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        String ipv4;
        byte[] ipv4Bytes;
        String ipv6;
        byte[] ipv6Bytes;
        int port;
        String serviceName;

        Builder(Endpoint endpoint) {
            this.serviceName = endpoint.serviceName;
            this.ipv4 = endpoint.ipv4;
            this.ipv6 = endpoint.ipv6;
            this.ipv4Bytes = endpoint.ipv4Bytes;
            this.ipv6Bytes = endpoint.ipv6Bytes;
            this.port = endpoint.port;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Builder merge(Endpoint endpoint) {
            if (this.serviceName == null) {
                this.serviceName = endpoint.serviceName;
            }
            if (this.ipv4 == null) {
                this.ipv4 = endpoint.ipv4;
            }
            if (this.ipv6 == null) {
                this.ipv6 = endpoint.ipv6;
            }
            if (this.ipv4Bytes == null) {
                this.ipv4Bytes = endpoint.ipv4Bytes;
            }
            if (this.ipv6Bytes == null) {
                this.ipv6Bytes = endpoint.ipv6Bytes;
            }
            if (this.port == 0) {
                this.port = endpoint.port;
            }
            return this;
        }

        public Builder serviceName(@Nullable String str) {
            this.serviceName = (str == null || str.isEmpty()) ? null : str.toLowerCase(Locale.ROOT);
            return this;
        }

        public Builder ip(@Nullable InetAddress inetAddress) {
            parseIp(inetAddress);
            return this;
        }

        public final boolean parseIp(@Nullable InetAddress inetAddress) {
            if (inetAddress == null) {
                return false;
            }
            if (inetAddress instanceof Inet4Address) {
                this.ipv4 = inetAddress.getHostAddress();
                this.ipv4Bytes = inetAddress.getAddress();
                return true;
            } else if (inetAddress instanceof Inet6Address) {
                byte[] address = inetAddress.getAddress();
                if (parseEmbeddedIPv4(address)) {
                    return true;
                }
                this.ipv6 = Endpoint.writeIpV6(address);
                this.ipv6Bytes = address;
                return true;
            } else {
                return false;
            }
        }

        public final boolean parseIp(byte[] bArr) {
            if (bArr == null) {
                return false;
            }
            if (bArr.length == 4) {
                this.ipv4Bytes = bArr;
                this.ipv4 = String.valueOf(bArr[0] & 255) + '.' + (bArr[1] & 255) + '.' + (bArr[2] & 255) + '.' + (bArr[3] & 255);
            } else if (bArr.length != 16) {
                return false;
            } else {
                if (!parseEmbeddedIPv4(bArr)) {
                    this.ipv6 = Endpoint.writeIpV6(bArr);
                    this.ipv6Bytes = bArr;
                }
            }
            return true;
        }

        public Builder ip(@Nullable String str) {
            parseIp(str);
            return this;
        }

        public final boolean parseIp(@Nullable String str) {
            byte[] textToNumericFormatV6;
            if (str != null && !str.isEmpty()) {
                IpFamily detectFamily = Endpoint.detectFamily(str);
                if (detectFamily == IpFamily.IPv4) {
                    this.ipv4 = str;
                    this.ipv4Bytes = Endpoint.getIpv4Bytes(str);
                } else if (detectFamily == IpFamily.IPv4Embedded) {
                    String substring = str.substring(str.lastIndexOf(58) + 1);
                    this.ipv4 = substring;
                    this.ipv4Bytes = Endpoint.getIpv4Bytes(substring);
                } else if (detectFamily != IpFamily.IPv6 || (textToNumericFormatV6 = Endpoint.textToNumericFormatV6(str)) == null) {
                    return false;
                } else {
                    this.ipv6 = Endpoint.writeIpV6(textToNumericFormatV6);
                    this.ipv6Bytes = textToNumericFormatV6;
                }
                return true;
            }
            return false;
        }

        public Builder port(@Nullable Integer num) {
            if (num != null) {
                if (num.intValue() > 65535) {
                    throw new IllegalArgumentException("invalid port " + num);
                }
                if (num.intValue() <= 0) {
                    num = 0;
                }
            }
            this.port = num != null ? num.intValue() : 0;
            return this;
        }

        public Builder port(int i) {
            if (i > 65535) {
                throw new IllegalArgumentException("invalid port " + i);
            }
            if (i < 0) {
                i = 0;
            }
            this.port = i;
            return this;
        }

        public Endpoint build() {
            return new Endpoint(this);
        }

        Builder() {
        }

        boolean parseEmbeddedIPv4(byte[] bArr) {
            for (int i = 0; i < 10; i++) {
                if (bArr[i] != 0) {
                    return false;
                }
            }
            int i2 = ((bArr[10] & 255) << 8) | (bArr[11] & 255);
            if (i2 == 0 || i2 == -1) {
                byte b = bArr[12];
                byte b2 = bArr[13];
                byte b3 = bArr[14];
                byte b4 = bArr[15];
                if (i2 == 0 && b == 0 && b2 == 0 && b3 == 0 && b4 == 1) {
                    return false;
                }
                this.ipv4 = String.valueOf(b & 255) + '.' + (b2 & 255) + '.' + (b3 & 255) + '.' + (b4 & 255);
                this.ipv4Bytes = new byte[]{b, b2, b3, b4};
                return true;
            }
            return false;
        }
    }

    static IpFamily detectFamily(String str) {
        int length = str.length();
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == '.') {
                z2 = true;
            } else if (charAt == ':') {
                if (z2) {
                    return IpFamily.Unknown;
                }
                z = true;
            } else if (notHex(charAt)) {
                return IpFamily.Unknown;
            }
        }
        if (!z) {
            if (z2 && isValidIpV4Address(str, 0, str.length())) {
                return IpFamily.IPv4;
            }
            return IpFamily.Unknown;
        } else if (z2) {
            int lastIndexOf = str.lastIndexOf(58);
            if (!isValidIpV4Address(str, lastIndexOf + 1, str.length())) {
                return IpFamily.Unknown;
            }
            if (lastIndexOf == 1 && str.charAt(0) == ':') {
                return IpFamily.IPv4Embedded;
            }
            if (lastIndexOf != 6 || str.charAt(0) != ':' || str.charAt(1) != ':') {
                return IpFamily.Unknown;
            }
            for (int i2 = 2; i2 < 6; i2++) {
                char charAt2 = str.charAt(i2);
                if (charAt2 != 'f' && charAt2 != 'F' && charAt2 != '0') {
                    return IpFamily.Unknown;
                }
            }
            return IpFamily.IPv4Embedded;
        } else {
            return IpFamily.IPv6;
        }
    }

    static String writeIpV6(byte[] bArr) {
        char[] cArr = IPV6_TO_STRING.get();
        int i = -1;
        int i2 = -1;
        int i3 = -1;
        boolean z = true;
        for (int i4 = 0; i4 < bArr.length; i4 += 2) {
            if (bArr[i4] != 0 || bArr[i4 + 1] != 0) {
                if (i2 >= 0) {
                    int i5 = i4 - i2;
                    if (i5 > i3) {
                        i3 = i5;
                        i = i2;
                    }
                    i2 = -1;
                }
                z = false;
            } else if (i2 < 0) {
                i2 = i4;
            }
        }
        if (z) {
            return "::";
        }
        if (i == -1 && i2 != -1) {
            i3 = 16 - i2;
            i = i2;
        }
        int i6 = 0;
        int i7 = 0;
        while (i6 < bArr.length) {
            if (i6 == i) {
                int i8 = i7 + 1;
                cArr[i7] = ':';
                i6 += i3;
                if (i6 == bArr.length) {
                    i7 = i8 + 1;
                    cArr[i8] = ':';
                } else {
                    i7 = i8;
                }
            } else {
                if (i6 != 0) {
                    cArr[i7] = ':';
                    i7++;
                }
                int i9 = i6 + 1;
                byte b = bArr[i6];
                int i10 = i9 + 1;
                byte b2 = bArr[i9];
                char[] cArr2 = HEX_DIGITS;
                char c = cArr2[(b >> 4) & 15];
                boolean z2 = c == '0';
                if (!z2) {
                    cArr[i7] = c;
                    i7++;
                }
                char c2 = cArr2[b & 15];
                boolean z3 = z2 && c2 == '0';
                if (!z3) {
                    cArr[i7] = c2;
                    i7++;
                }
                char c3 = cArr2[(b2 >> 4) & 15];
                if (!z3 || c3 != '0') {
                    cArr[i7] = c3;
                    i7++;
                }
                cArr[i7] = cArr2[b2 & 15];
                i7++;
                i6 = i10;
            }
        }
        return new String(cArr, 0, i7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public static byte[] textToNumericFormatV6(String str) {
        int length;
        int i;
        String[] split = str.split(QuickSettingConstants.JOINER, 10);
        if (split.length < 3 || split.length > 9) {
            return null;
        }
        int i2 = -1;
        for (int i3 = 1; i3 < split.length - 1; i3++) {
            if (split[i3].length() == 0) {
                if (i2 >= 0) {
                    return null;
                }
                i2 = i3;
            }
        }
        if (i2 >= 0) {
            i = (split.length - i2) - 1;
            if (split[0].length() == 0) {
                length = i2 - 1;
                if (length != 0) {
                    return null;
                }
            } else {
                length = i2;
            }
            if (split[split.length - 1].length() == 0 && i - 1 != 0) {
                return null;
            }
        } else {
            length = split.length;
            i = 0;
        }
        int i4 = 8 - (length + i);
        if (i2 < 0 ? i4 == 0 : i4 >= 1) {
            ByteBuffer allocate = ByteBuffer.allocate(16);
            for (int i5 = 0; i5 < length; i5++) {
                try {
                    allocate.putShort(parseHextet(split[i5]));
                } catch (NumberFormatException unused) {
                    return null;
                }
            }
            for (int i6 = 0; i6 < i4; i6++) {
                allocate.putShort((short) 0);
            }
            while (i > 0) {
                allocate.putShort(parseHextet(split[split.length - i]));
                i--;
            }
            return allocate.array();
        }
        return null;
    }

    private static short parseHextet(String str) {
        int parseInt = Integer.parseInt(str, 16);
        if (parseInt <= 65535) {
            return (short) parseInt;
        }
        throw new NumberFormatException();
    }

    private static boolean isValidIpV4Address(String str, int i, int i2) {
        int indexOf;
        int i3;
        int indexOf2;
        int i4;
        int indexOf3;
        int i5 = i2 - i;
        return i5 <= 15 && i5 >= 7 && (indexOf = str.indexOf(46, i + 1)) > 0 && isValidIpV4Word(str, i, indexOf) && (indexOf2 = str.indexOf(46, (i3 = indexOf + 2))) > 0 && isValidIpV4Word(str, i3 - 1, indexOf2) && (indexOf3 = str.indexOf(46, (i4 = indexOf2 + 2))) > 0 && isValidIpV4Word(str, i4 - 1, indexOf3) && isValidIpV4Word(str, indexOf3 + 1, i2);
    }

    private static boolean isValidIpV4Word(CharSequence charSequence, int i, int i2) {
        char charAt;
        char charAt2;
        int i3 = i2 - i;
        if (i3 < 1 || i3 > 3 || (charAt = charSequence.charAt(i)) < '0') {
            return false;
        }
        if (i3 != 3) {
            if (charAt <= '9') {
                return i3 == 1 || isValidNumericChar(charSequence.charAt(i + 1));
            }
            return false;
        }
        char charAt3 = charSequence.charAt(i + 1);
        if (charAt3 < '0' || (charAt2 = charSequence.charAt(i + 2)) < '0') {
            return false;
        }
        if (charAt > '1' || charAt3 > '9' || charAt2 > '9') {
            if (charAt != '2' || charAt3 > '5') {
                return false;
            }
            if (charAt2 > '5' && (charAt3 >= '5' || charAt2 > '9')) {
                return false;
            }
        }
        return true;
    }

    Endpoint(Builder builder) {
        this.serviceName = builder.serviceName;
        this.ipv4 = builder.ipv4;
        this.ipv4Bytes = builder.ipv4Bytes;
        this.ipv6 = builder.ipv6;
        this.ipv6Bytes = builder.ipv6Bytes;
        this.port = builder.port;
    }

    Endpoint(SerializedForm serializedForm) {
        this.serviceName = serializedForm.serviceName;
        this.ipv4 = serializedForm.ipv4;
        this.ipv4Bytes = serializedForm.ipv4Bytes;
        this.ipv6 = serializedForm.ipv6;
        this.ipv6Bytes = serializedForm.ipv6Bytes;
        this.port = serializedForm.port;
    }

    public String toString() {
        return "Endpoint{serviceName=" + this.serviceName + ", ipv4=" + this.ipv4 + ", ipv6=" + this.ipv6 + ", port=" + this.port + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Endpoint) {
            Endpoint endpoint = (Endpoint) obj;
            String str = this.serviceName;
            if (str != null ? str.equals(endpoint.serviceName) : endpoint.serviceName == null) {
                String str2 = this.ipv4;
                if (str2 != null ? str2.equals(endpoint.ipv4) : endpoint.ipv4 == null) {
                    String str3 = this.ipv6;
                    if (str3 != null ? str3.equals(endpoint.ipv6) : endpoint.ipv6 == null) {
                        if (this.port == endpoint.port) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        String str = this.serviceName;
        int hashCode = ((str == null ? 0 : str.hashCode()) ^ 1000003) * 1000003;
        String str2 = this.ipv4;
        int hashCode2 = (hashCode ^ (str2 == null ? 0 : str2.hashCode())) * 1000003;
        String str3 = this.ipv6;
        return ((hashCode2 ^ (str3 != null ? str3.hashCode() : 0)) * 1000003) ^ this.port;
    }

    final Object writeReplace() throws ObjectStreamException {
        return new SerializedForm(this);
    }

    /* loaded from: classes3.dex */
    private static final class SerializedForm implements Serializable {
        static final long serialVersionUID = 0;
        final String ipv4;
        final byte[] ipv4Bytes;
        final String ipv6;
        final byte[] ipv6Bytes;
        final int port;
        final String serviceName;

        SerializedForm(Endpoint endpoint) {
            this.serviceName = endpoint.serviceName;
            this.ipv4 = endpoint.ipv4;
            this.ipv4Bytes = endpoint.ipv4Bytes;
            this.ipv6 = endpoint.ipv6;
            this.ipv6Bytes = endpoint.ipv6Bytes;
            this.port = endpoint.port;
        }

        Object readResolve() throws ObjectStreamException {
            try {
                return new Endpoint(this);
            } catch (IllegalArgumentException e) {
                throw new StreamCorruptedException(e.getMessage());
            }
        }
    }

    static byte[] getIpv4Bytes(String str) {
        int i;
        byte[] bArr = new byte[4];
        int length = str.length();
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int i4 = i2 + 1;
            int charAt = str.charAt(i2) - '0';
            if (i4 != length) {
                int i5 = i4 + 1;
                char charAt2 = str.charAt(i4);
                if (charAt2 == '.') {
                    i4 = i5;
                } else {
                    int i6 = (charAt * 10) + (charAt2 - '0');
                    if (i5 != length) {
                        i4 = i5 + 1;
                        char charAt3 = str.charAt(i5);
                        if (charAt3 != '.') {
                            int i7 = (i6 * 10) + (charAt3 - '0');
                            i = i3 + 1;
                            bArr[i3] = (byte) i7;
                            i2 = i4 + 1;
                            i3 = i;
                        }
                    } else {
                        i4 = i5;
                    }
                    i = i3 + 1;
                    bArr[i3] = (byte) i6;
                    i2 = i4;
                    i3 = i;
                }
            }
            i = i3 + 1;
            bArr[i3] = (byte) charAt;
            i2 = i4;
            i3 = i;
        }
        return bArr;
    }
}
