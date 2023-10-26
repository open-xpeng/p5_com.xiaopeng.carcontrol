package brave.propagation;

import brave.internal.HexCodec;
import brave.internal.Nullable;
import brave.internal.Platform;
import java.util.Collections;

/* loaded from: classes.dex */
public final class B3SingleFormat {
    static final ThreadLocal<char[]> CHAR_BUFFER = new ThreadLocal<>();
    static final int FORMAT_MAX_LENGTH = 68;

    static boolean isLowerHex(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f');
    }

    public static String writeB3SingleFormatWithoutParentId(TraceContext traceContext) {
        char[] charBuffer = getCharBuffer();
        return new String(charBuffer, 0, writeB3SingleFormat(traceContext, 0L, charBuffer));
    }

    public static byte[] writeB3SingleFormatWithoutParentIdAsBytes(TraceContext traceContext) {
        char[] charBuffer = getCharBuffer();
        return asciiToNewByteArray(charBuffer, writeB3SingleFormat(traceContext, 0L, charBuffer));
    }

    public static String writeB3SingleFormat(TraceContext traceContext) {
        char[] charBuffer = getCharBuffer();
        return new String(charBuffer, 0, writeB3SingleFormat(traceContext, traceContext.parentIdAsLong(), charBuffer));
    }

    public static byte[] writeB3SingleFormatAsBytes(TraceContext traceContext) {
        char[] charBuffer = getCharBuffer();
        return asciiToNewByteArray(charBuffer, writeB3SingleFormat(traceContext, traceContext.parentIdAsLong(), charBuffer));
    }

    static int writeB3SingleFormat(TraceContext traceContext, long j, char[] cArr) {
        long traceIdHigh = traceContext.traceIdHigh();
        int i = 0;
        if (traceIdHigh != 0) {
            HexCodec.writeHexLong(cArr, 0, traceIdHigh);
            i = 16;
        }
        HexCodec.writeHexLong(cArr, i, traceContext.traceId());
        int i2 = i + 16;
        int i3 = i2 + 1;
        cArr[i2] = '-';
        HexCodec.writeHexLong(cArr, i3, traceContext.spanId());
        int i4 = i3 + 16;
        Boolean sampled = traceContext.sampled();
        if (sampled != null) {
            int i5 = i4 + 1;
            cArr[i4] = '-';
            i4 = i5 + 1;
            cArr[i5] = traceContext.debug() ? 'd' : sampled.booleanValue() ? '1' : '0';
        }
        if (j != 0) {
            int i6 = i4 + 1;
            cArr[i4] = '-';
            HexCodec.writeHexLong(cArr, i6, j);
            return i6 + 16;
        }
        return i4;
    }

    @Nullable
    public static TraceContextOrSamplingFlags parseB3SingleFormat(CharSequence charSequence) {
        return parseB3SingleFormat(charSequence, 0, charSequence.length());
    }

    @Nullable
    public static TraceContextOrSamplingFlags parseB3SingleFormat(CharSequence charSequence, int i, int i2) {
        long tryParse16HexCharacters;
        long j;
        int i3;
        long j2;
        int i4 = i;
        if (i4 == i2) {
            Platform.get().log("Invalid input: empty", null);
            return null;
        } else if (i4 + 1 == i2) {
            return tryParseSamplingFlags(charSequence, i);
        } else {
            if (i2 < 33) {
                Platform.get().log("Invalid input: truncated", null);
                return null;
            } else if (i2 > 68) {
                Platform.get().log("Invalid input: too long", null);
                return null;
            } else {
                if (charSequence.charAt(i4 + 32) == '-') {
                    long tryParse16HexCharacters2 = tryParse16HexCharacters(charSequence, i, i2);
                    i4 += 16;
                    tryParse16HexCharacters = tryParse16HexCharacters(charSequence, i4, i2);
                    j = tryParse16HexCharacters2;
                } else {
                    tryParse16HexCharacters = tryParse16HexCharacters(charSequence, i, i2);
                    j = 0;
                }
                int i5 = i4 + 16;
                if (j == 0 && tryParse16HexCharacters == 0) {
                    Platform.get().log("Invalid input: expected a 16 or 32 lower hex trace ID at offset 0", null);
                    return null;
                } else if (isLowerHex(charSequence.charAt(i5))) {
                    Platform.get().log("Invalid input: trace ID is too long", null);
                    return null;
                } else {
                    int i6 = i5 + 1;
                    if (checkHyphen(charSequence, i5)) {
                        long tryParse16HexCharacters3 = tryParse16HexCharacters(charSequence, i6, i2);
                        if (tryParse16HexCharacters3 == 0) {
                            Platform.get().log("Invalid input: expected a 16 lower hex span ID at offset {0}", Integer.valueOf(i6), null);
                            return null;
                        }
                        int i7 = i6 + 16;
                        int i8 = 0;
                        if (i2 > i7) {
                            if (isLowerHex(charSequence.charAt(i7))) {
                                Platform.get().log("Invalid input: span ID is too long", null);
                                return null;
                            }
                            int i9 = i7 + 1;
                            if (i2 == i9) {
                                Platform.get().log("Invalid input: truncated", null);
                                return null;
                            } else if (!checkHyphen(charSequence, i7)) {
                                return null;
                            } else {
                                boolean notHexFollowsPos = notHexFollowsPos(charSequence, i9, i2);
                                int i10 = i9 + 1;
                                if (i2 == i10 || notHexFollowsPos) {
                                    i8 = parseFlags(charSequence, i9);
                                    if (i8 == 0) {
                                        return null;
                                    }
                                    if (notHexFollowsPos) {
                                        i9 = i10 + 1;
                                        if (!checkHyphen(charSequence, i10)) {
                                            return null;
                                        }
                                    } else {
                                        i9 = i10;
                                    }
                                }
                                if (i2 > i9 || notHexFollowsPos) {
                                    long tryParseParentId = tryParseParentId(charSequence, i9, i2);
                                    if (tryParseParentId == 0) {
                                        return null;
                                    }
                                    i3 = i8;
                                    j2 = tryParseParentId;
                                    return TraceContextOrSamplingFlags.create(new TraceContext(i3, j, tryParse16HexCharacters, 0L, j2, tryParse16HexCharacters3, Collections.emptyList()));
                                }
                            }
                        }
                        i3 = i8;
                        j2 = 0;
                        return TraceContextOrSamplingFlags.create(new TraceContext(i3, j, tryParse16HexCharacters, 0L, j2, tryParse16HexCharacters3, Collections.emptyList()));
                    }
                    return null;
                }
            }
        }
    }

    static long tryParseParentId(CharSequence charSequence, int i, int i2) {
        int i3 = i + 16;
        if (i2 < i3) {
            Platform.get().log("Invalid input: truncated", null);
            return 0L;
        }
        long tryParse16HexCharacters = tryParse16HexCharacters(charSequence, i, i2);
        if (tryParse16HexCharacters == 0) {
            Platform.get().log("Invalid input: expected a 16 lower hex parent ID at offset {0}", Integer.valueOf(i), null);
            return 0L;
        } else if (i2 != i3) {
            Platform.get().log("Invalid input: parent ID is too long", null);
            return 0L;
        } else {
            return tryParse16HexCharacters;
        }
    }

    static TraceContextOrSamplingFlags tryParseSamplingFlags(CharSequence charSequence, int i) {
        int parseFlags = parseFlags(charSequence, i);
        if (parseFlags == 0) {
            return null;
        }
        return TraceContextOrSamplingFlags.create(SamplingFlags.toSamplingFlags(parseFlags));
    }

    static boolean checkHyphen(CharSequence charSequence, int i) {
        if (charSequence.charAt(i) == '-') {
            return true;
        }
        Platform.get().log("Invalid input: expected a hyphen(-) delimiter at offset {0}", Integer.valueOf(i), null);
        return false;
    }

    static boolean notHexFollowsPos(CharSequence charSequence, int i, int i2) {
        return i2 >= i + 2 && !isLowerHex(charSequence.charAt(i + 1));
    }

    static long tryParse16HexCharacters(CharSequence charSequence, int i, int i2) {
        int i3 = i + 16;
        if (i3 > i2) {
            return 0L;
        }
        return HexCodec.lenientLowerHexToUnsignedLong(charSequence, i, i3);
    }

    static int parseFlags(CharSequence charSequence, int i) {
        char charAt = charSequence.charAt(i);
        if (charAt == 'd') {
            return 14;
        }
        if (charAt == '1') {
            return 6;
        }
        if (charAt == '0') {
            return 4;
        }
        logInvalidSampled(i);
        return 0;
    }

    static void logInvalidSampled(int i) {
        Platform.get().log("Invalid input: expected 0, 1 or d for sampled at offset {0}", Integer.valueOf(i), null);
    }

    static byte[] asciiToNewByteArray(char[] cArr, int i) {
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) cArr[i2];
        }
        return bArr;
    }

    static char[] getCharBuffer() {
        ThreadLocal<char[]> threadLocal = CHAR_BUFFER;
        char[] cArr = threadLocal.get();
        if (cArr == null) {
            char[] cArr2 = new char[68];
            threadLocal.set(cArr2);
            return cArr2;
        }
        return cArr;
    }

    B3SingleFormat() {
    }
}
