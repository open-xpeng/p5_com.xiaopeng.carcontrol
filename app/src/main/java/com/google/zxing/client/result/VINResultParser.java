package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.xiaopeng.carcontrol.GlobalConstant;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class VINResultParser extends ResultParser {
    private static final Pattern IOQ = Pattern.compile("[IOQ]");
    private static final Pattern AZ09 = Pattern.compile("[A-Z0-9]{17}");

    @Override // com.google.zxing.client.result.ResultParser
    public VINParsedResult parse(Result result) {
        if (result.getBarcodeFormat() != BarcodeFormat.CODE_39) {
            return null;
        }
        String trim = IOQ.matcher(result.getText()).replaceAll("").trim();
        if (AZ09.matcher(trim).matches()) {
            try {
                if (checkChecksum(trim)) {
                    String substring = trim.substring(0, 3);
                    return new VINParsedResult(trim, substring, trim.substring(3, 9), trim.substring(9, 17), countryCode(substring), trim.substring(3, 8), modelYear(trim.charAt(9)), trim.charAt(10), trim.substring(11));
                }
                return null;
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }
        return null;
    }

    private static boolean checkChecksum(CharSequence charSequence) {
        int i = 0;
        int i2 = 0;
        while (i < charSequence.length()) {
            int i3 = i + 1;
            i2 += vinPositionWeight(i3) * vinCharValue(charSequence.charAt(i));
            i = i3;
        }
        return charSequence.charAt(8) == checkChar(i2 % 11);
    }

    private static int vinCharValue(char c) {
        if (c < 'A' || c > 'I') {
            if (c < 'J' || c > 'R') {
                if (c < 'S' || c > 'Z') {
                    if (c < '0' || c > '9') {
                        throw new IllegalArgumentException();
                    }
                    return c - '0';
                }
                return (c - 'S') + 2;
            }
            return (c - 'J') + 1;
        }
        return (c - 'A') + 1;
    }

    private static int vinPositionWeight(int i) {
        if (i < 1 || i > 7) {
            if (i == 8) {
                return 10;
            }
            if (i == 9) {
                return 0;
            }
            if (i < 10 || i > 17) {
                throw new IllegalArgumentException();
            }
            return 19 - i;
        }
        return 9 - i;
    }

    private static char checkChar(int i) {
        if (i < 10) {
            return (char) (i + 48);
        }
        if (i == 10) {
            return 'X';
        }
        throw new IllegalArgumentException();
    }

    private static int modelYear(char c) {
        if (c < 'E' || c > 'H') {
            if (c < 'J' || c > 'N') {
                if (c == 'P') {
                    return 1993;
                }
                if (c < 'R' || c > 'T') {
                    if (c < 'V' || c > 'Y') {
                        if (c < '1' || c > '9') {
                            if (c < 'A' || c > 'D') {
                                throw new IllegalArgumentException();
                            }
                            return (c - 'A') + 2010;
                        }
                        return (c - '1') + 2001;
                    }
                    return (c - 'V') + 1997;
                }
                return (c - 'R') + 1994;
            }
            return (c - 'J') + 1988;
        }
        return (c - 'E') + 1984;
    }

    private static String countryCode(CharSequence charSequence) {
        char charAt = charSequence.charAt(0);
        char charAt2 = charSequence.charAt(1);
        if (charAt == '9') {
            if (charAt2 < 'A' || charAt2 > 'E') {
                if (charAt2 < '3' || charAt2 > '9') {
                    return null;
                }
                return "BR";
            }
            return "BR";
        } else if (charAt == 'S') {
            if (charAt2 < 'A' || charAt2 > 'M') {
                if (charAt2 < 'N' || charAt2 > 'T') {
                    return null;
                }
                return "DE";
            }
            return "UK";
        } else if (charAt == 'Z') {
            if (charAt2 < 'A' || charAt2 > 'R') {
                return null;
            }
            return "IT";
        } else {
            switch (charAt) {
                case '1':
                case '4':
                case '5':
                    return "US";
                case '2':
                    return "CA";
                case '3':
                    if (charAt2 < 'A' || charAt2 > 'W') {
                        return null;
                    }
                    return "MX";
                default:
                    switch (charAt) {
                        case 'J':
                            if (charAt2 < 'A' || charAt2 > 'T') {
                                return null;
                            }
                            return "JP";
                        case 'K':
                            if (charAt2 < 'L' || charAt2 > 'R') {
                                return null;
                            }
                            return "KO";
                        case 'L':
                            return GlobalConstant.COUNTRY.COUNTRY_CN;
                        case 'M':
                            if (charAt2 < 'A' || charAt2 > 'E') {
                                return null;
                            }
                            return "IN";
                        default:
                            switch (charAt) {
                                case 'V':
                                    if (charAt2 < 'F' || charAt2 > 'R') {
                                        if (charAt2 < 'S' || charAt2 > 'W') {
                                            return null;
                                        }
                                        return "ES";
                                    }
                                    return "FR";
                                case 'W':
                                    return "DE";
                                case 'X':
                                    if (charAt2 != '0') {
                                        if (charAt2 < '3' || charAt2 > '9') {
                                            return null;
                                        }
                                        return "RU";
                                    }
                                    return "RU";
                                default:
                                    return null;
                            }
                    }
            }
        }
    }
}
