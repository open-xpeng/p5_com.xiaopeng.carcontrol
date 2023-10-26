package com.google.zxing.common;

import com.google.zxing.DecodeHintType;
import java.nio.charset.Charset;
import java.util.Map;

/* loaded from: classes.dex */
public final class StringUtils {
    private static final boolean ASSUME_SHIFT_JIS;
    private static final String EUC_JP = "EUC_JP";
    public static final String GB2312 = "GB2312";
    private static final String ISO88591 = "ISO8859_1";
    private static final String PLATFORM_DEFAULT_ENCODING;
    public static final String SHIFT_JIS = "SJIS";
    private static final String UTF8 = "UTF8";

    static {
        String name = Charset.defaultCharset().name();
        PLATFORM_DEFAULT_ENCODING = name;
        ASSUME_SHIFT_JIS = SHIFT_JIS.equalsIgnoreCase(name) || EUC_JP.equalsIgnoreCase(name);
    }

    private StringUtils() {
    }

    public static String guessEncoding(byte[] bArr, Map<DecodeHintType, ?> map) {
        String str;
        byte[] bArr2 = bArr;
        if (map == null || (str = (String) map.get(DecodeHintType.CHARACTER_SET)) == null) {
            int length = bArr2.length;
            boolean z = true;
            int i = 0;
            boolean z2 = bArr2.length > 3 && bArr2[0] == -17 && bArr2[1] == -69 && bArr2[2] == -65;
            boolean z3 = true;
            boolean z4 = true;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            int i8 = 0;
            int i9 = 0;
            int i10 = 0;
            int i11 = 0;
            int i12 = 0;
            while (i3 < length && (z || z3 || z4)) {
                int i13 = bArr2[i3] & 255;
                if (z4) {
                    if (i4 > 0) {
                        if ((i13 & 128) != 0) {
                            i4--;
                        }
                        z4 = false;
                    } else if ((i13 & 128) != 0) {
                        if ((i13 & 64) != 0) {
                            i4++;
                            if ((i13 & 32) == 0) {
                                i6++;
                            } else {
                                i4++;
                                if ((i13 & 16) == 0) {
                                    i7++;
                                } else {
                                    i4++;
                                    if ((i13 & 8) == 0) {
                                        i8++;
                                    }
                                }
                            }
                        }
                        z4 = false;
                    }
                }
                if (z) {
                    if (i13 > 127 && i13 < 160) {
                        z = false;
                    } else if (i13 > 159 && (i13 < 192 || i13 == 215 || i13 == 247)) {
                        i10++;
                    }
                }
                if (z3) {
                    if (i5 > 0) {
                        if (i13 >= 64 && i13 != 127 && i13 <= 252) {
                            i5--;
                        }
                        z3 = false;
                    } else {
                        if (i13 != 128 && i13 != 160 && i13 <= 239) {
                            if (i13 <= 160 || i13 >= 224) {
                                if (i13 > 127) {
                                    i5++;
                                    int i14 = i11 + 1;
                                    if (i14 > i) {
                                        i = i14;
                                        i11 = i;
                                    } else {
                                        i11 = i14;
                                    }
                                } else {
                                    i11 = 0;
                                }
                                i12 = 0;
                            } else {
                                i2++;
                                int i15 = i12 + 1;
                                if (i15 > i9) {
                                    i9 = i15;
                                    i12 = i9;
                                } else {
                                    i12 = i15;
                                }
                                i11 = 0;
                            }
                        }
                        z3 = false;
                    }
                }
                i3++;
                bArr2 = bArr;
            }
            if (z4 && i4 > 0) {
                z4 = false;
            }
            if (z3 && i5 > 0) {
                z3 = false;
            }
            return (!z4 || (!z2 && (i6 + i7) + i8 <= 0)) ? (!z3 || (!ASSUME_SHIFT_JIS && i9 < 3 && i < 3)) ? (z && z3) ? (!(i9 == 2 && i2 == 2) && i10 * 10 < length) ? ISO88591 : SHIFT_JIS : z ? ISO88591 : z3 ? SHIFT_JIS : z4 ? UTF8 : PLATFORM_DEFAULT_ENCODING : SHIFT_JIS : UTF8;
        }
        return str;
    }
}
