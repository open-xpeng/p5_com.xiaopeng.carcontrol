package brave.internal;

/* loaded from: classes.dex */
public final class HexCodec {
    static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static long lowerHexToUnsignedLong(CharSequence charSequence) {
        int length = charSequence.length();
        if (length < 1 || length > 32) {
            throw isntLowerHexLong(charSequence);
        }
        return lowerHexToUnsignedLong(charSequence, length > 16 ? length - 16 : 0);
    }

    public static long lowerHexToUnsignedLong(CharSequence charSequence, int i) {
        long lenientLowerHexToUnsignedLong = lenientLowerHexToUnsignedLong(charSequence, i, Math.min(i + 16, charSequence.length()));
        if (lenientLowerHexToUnsignedLong != 0) {
            return lenientLowerHexToUnsignedLong;
        }
        throw isntLowerHexLong(charSequence);
    }

    public static long lenientLowerHexToUnsignedLong(CharSequence charSequence, int i, int i2) {
        int i3;
        long j = 0;
        while (i < i2) {
            int i4 = i + 1;
            char charAt = charSequence.charAt(i);
            long j2 = j << 4;
            if (charAt >= '0' && charAt <= '9') {
                i3 = charAt - '0';
            } else if (charAt < 'a' || charAt > 'f') {
                return 0L;
            } else {
                i3 = (charAt - 'a') + 10;
            }
            j = j2 | i3;
            i = i4;
        }
        return j;
    }

    static NumberFormatException isntLowerHexLong(CharSequence charSequence) {
        throw new NumberFormatException(((Object) charSequence) + " should be a 1 to 32 character lower-hex string with no prefix");
    }

    public static String toLowerHex(long j) {
        char[] cArr = new char[16];
        writeHexLong(cArr, 0, j);
        return new String(cArr);
    }

    public static void writeHexLong(char[] cArr, int i, long j) {
        writeHexByte(cArr, i + 0, (byte) ((j >>> 56) & 255));
        writeHexByte(cArr, i + 2, (byte) ((j >>> 48) & 255));
        writeHexByte(cArr, i + 4, (byte) ((j >>> 40) & 255));
        writeHexByte(cArr, i + 6, (byte) ((j >>> 32) & 255));
        writeHexByte(cArr, i + 8, (byte) ((j >>> 24) & 255));
        writeHexByte(cArr, i + 10, (byte) ((j >>> 16) & 255));
        writeHexByte(cArr, i + 12, (byte) ((j >>> 8) & 255));
        writeHexByte(cArr, i + 14, (byte) (j & 255));
    }

    static void writeHexByte(char[] cArr, int i, byte b) {
        char[] cArr2 = HEX_DIGITS;
        cArr[i + 0] = cArr2[(b >> 4) & 15];
        cArr[i + 1] = cArr2[b & 15];
    }

    HexCodec() {
    }
}
