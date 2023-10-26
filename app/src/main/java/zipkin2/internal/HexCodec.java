package zipkin2.internal;

/* loaded from: classes3.dex */
public final class HexCodec {
    public static long lowerHexToUnsignedLong(String str) {
        int length = str.length();
        if (length < 1 || length > 32) {
            throw isntLowerHexLong(str);
        }
        return lowerHexToUnsignedLong(str, length > 16 ? length - 16 : 0);
    }

    public static long lowerHexToUnsignedLong(String str, int i) {
        int i2;
        int min = Math.min(i + 16, str.length());
        long j = 0;
        while (i < min) {
            char charAt = str.charAt(i);
            long j2 = j << 4;
            if (charAt >= '0' && charAt <= '9') {
                i2 = charAt - '0';
            } else if (charAt < 'a' || charAt > 'f') {
                throw isntLowerHexLong(str);
            } else {
                i2 = (charAt - 'a') + 10;
            }
            j = j2 | i2;
            i++;
        }
        return j;
    }

    static NumberFormatException isntLowerHexLong(String str) {
        throw new NumberFormatException(str + " should be a 1 to 32 character lower-hex string with no prefix");
    }

    HexCodec() {
    }
}
