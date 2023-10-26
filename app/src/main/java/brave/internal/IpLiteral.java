package brave.internal;

/* loaded from: classes.dex */
public final class IpLiteral {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
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
    public static String ipOrNull(@Nullable String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        if ("::1".equals(str) || "127.0.0.1".equals(str)) {
            return str;
        }
        IpFamily detectFamily = detectFamily(str);
        if (detectFamily == IpFamily.IPv4Embedded) {
            return str.substring(str.lastIndexOf(58) + 1);
        }
        if (detectFamily == IpFamily.Unknown) {
            return null;
        }
        return str;
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
        int i3 = i2 - i;
        if (i3 < 1 || i3 > 3) {
            return false;
        }
        char charAt2 = charSequence.charAt(i);
        if (i3 != 3) {
            if (charAt2 <= '9') {
                return i3 == 1 || isValidNumericChar(charSequence.charAt(i + 1));
            }
            return false;
        }
        char charAt3 = charSequence.charAt(i + 1);
        if (charAt3 < '0' || (charAt = charSequence.charAt(i + 2)) < '0') {
            return false;
        }
        if (charAt2 > '1' || charAt3 > '9' || charAt > '9') {
            if (charAt2 != '2' || charAt3 > '5') {
                return false;
            }
            if (charAt > '5' && (charAt3 >= '5' || charAt > '9')) {
                return false;
            }
        }
        return true;
    }
}
