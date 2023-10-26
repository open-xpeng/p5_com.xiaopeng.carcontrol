package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public final class JSONLexer {
    public static final char[] CA;
    public static final int END = 4;
    public static final char EOI = 26;
    static final int[] IA;
    public static final int NOT_MATCH = -1;
    public static final int NOT_MATCH_NAME = -2;
    public static final int UNKNOWN = 0;
    private static boolean V6 = false;
    public static final int VALUE = 3;
    protected static final int[] digits;
    public static final boolean[] firstIdentifierFlags;
    public static final boolean[] identifierFlags;
    private static final ThreadLocal<char[]> sbufLocal;
    protected int bp;
    protected Calendar calendar;
    protected char ch;
    public boolean disableCircularReferenceDetect;
    protected int eofPos;
    public int features;
    protected boolean hasSpecial;
    protected final int len;
    public Locale locale;
    public int matchStat;
    protected int np;
    protected int pos;
    protected char[] sbuf;
    protected int sp;
    protected String stringDefaultValue;
    protected final String text;
    public TimeZone timeZone;
    protected int token;

    static boolean checkDate(char c, char c2, char c3, char c4, char c5, char c6, int i, int i2) {
        if ((c == '1' || c == '2') && c2 >= '0' && c2 <= '9' && c3 >= '0' && c3 <= '9' && c4 >= '0' && c4 <= '9') {
            if (c5 == '0') {
                if (c6 < '1' || c6 > '9') {
                    return false;
                }
            } else if (c5 != '1' || (c6 != '0' && c6 != '1' && c6 != '2')) {
                return false;
            }
            if (i == 48) {
                return i2 >= 49 && i2 <= 57;
            } else if (i != 49 && i != 50) {
                return i == 51 && (i2 == 48 || i2 == 49);
            } else if (i2 >= 48 && i2 <= 57) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x001d, code lost:
        if (r5 <= '4') goto L5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static boolean checkTime(char r4, char r5, char r6, char r7, char r8, char r9) {
        /*
            r0 = 57
            r1 = 0
            r2 = 48
            if (r4 != r2) goto Lc
            if (r5 < r2) goto Lb
            if (r5 <= r0) goto L20
        Lb:
            return r1
        Lc:
            r3 = 49
            if (r4 != r3) goto L15
            if (r5 < r2) goto L14
            if (r5 <= r0) goto L20
        L14:
            return r1
        L15:
            r3 = 50
            if (r4 != r3) goto L42
            if (r5 < r2) goto L42
            r4 = 52
            if (r5 <= r4) goto L20
            goto L42
        L20:
            r4 = 53
            r5 = 54
            if (r6 < r2) goto L2d
            if (r6 > r4) goto L2d
            if (r7 < r2) goto L2c
            if (r7 <= r0) goto L32
        L2c:
            return r1
        L2d:
            if (r6 != r5) goto L42
            if (r7 == r2) goto L32
            return r1
        L32:
            if (r8 < r2) goto L3b
            if (r8 > r4) goto L3b
            if (r9 < r2) goto L3a
            if (r9 <= r0) goto L40
        L3a:
            return r1
        L3b:
            if (r8 != r5) goto L42
            if (r9 == r2) goto L40
            return r1
        L40:
            r4 = 1
            return r4
        L42:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.checkTime(char, char, char, char, char, char):boolean");
    }

    static {
        int i;
        try {
            i = Class.forName("android.os.Build$VERSION").getField("SDK_INT").getInt(null);
        } catch (Exception unused) {
            i = -1;
        }
        char c = 0;
        V6 = i >= 23;
        sbufLocal = new ThreadLocal<>();
        digits = new int[103];
        for (int i2 = 48; i2 <= 57; i2++) {
            digits[i2] = i2 - 48;
        }
        for (int i3 = 97; i3 <= 102; i3++) {
            digits[i3] = (i3 - 97) + 10;
        }
        for (int i4 = 65; i4 <= 70; i4++) {
            digits[i4] = (i4 - 65) + 10;
        }
        char[] charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        CA = charArray;
        int[] iArr = new int[256];
        IA = iArr;
        Arrays.fill(iArr, -1);
        int length = charArray.length;
        for (int i5 = 0; i5 < length; i5++) {
            IA[CA[i5]] = i5;
        }
        IA[61] = 0;
        firstIdentifierFlags = new boolean[256];
        char c2 = 0;
        while (true) {
            boolean[] zArr = firstIdentifierFlags;
            if (c2 >= zArr.length) {
                break;
            }
            if (c2 >= 'A' && c2 <= 'Z') {
                zArr[c2] = true;
            } else if (c2 >= 'a' && c2 <= 'z') {
                zArr[c2] = true;
            } else if (c2 == '_') {
                zArr[c2] = true;
            }
            c2 = (char) (c2 + 1);
        }
        identifierFlags = new boolean[256];
        while (true) {
            boolean[] zArr2 = identifierFlags;
            if (c >= zArr2.length) {
                return;
            }
            if (c >= 'A' && c <= 'Z') {
                zArr2[c] = true;
            } else if (c >= 'a' && c <= 'z') {
                zArr2[c] = true;
            } else if (c == '_') {
                zArr2[c] = true;
            } else if (c >= '0' && c <= '9') {
                zArr2[c] = true;
            }
            c = (char) (c + 1);
        }
    }

    public JSONLexer(String str) {
        this(str, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONLexer(char[] cArr, int i) {
        this(cArr, i, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONLexer(char[] cArr, int i, int i2) {
        this(new String(cArr, 0, i), i2);
    }

    public JSONLexer(String str, int i) {
        this.features = JSON.DEFAULT_PARSER_FEATURE;
        this.timeZone = JSON.defaultTimeZone;
        this.locale = JSON.defaultLocale;
        this.calendar = null;
        this.matchStat = 0;
        char[] cArr = sbufLocal.get();
        this.sbuf = cArr;
        if (cArr == null) {
            this.sbuf = new char[512];
        }
        this.features = i;
        this.text = str;
        int length = str.length();
        this.len = length;
        this.bp = -1;
        int i2 = (-1) + 1;
        this.bp = i2;
        char charAt = i2 >= length ? EOI : str.charAt(i2);
        this.ch = charAt;
        if (charAt == 65279) {
            next();
        }
        this.stringDefaultValue = (Feature.InitStringFieldAsEmpty.mask & i) != 0 ? "" : null;
        this.disableCircularReferenceDetect = (Feature.DisableCircularReferenceDetect.mask & i) != 0;
    }

    public final int token() {
        return this.token;
    }

    public void close() {
        char[] cArr = this.sbuf;
        if (cArr.length <= 8196) {
            sbufLocal.set(cArr);
        }
        this.sbuf = null;
    }

    public char next() {
        int i = this.bp + 1;
        this.bp = i;
        char charAt = i >= this.len ? EOI : this.text.charAt(i);
        this.ch = charAt;
        return charAt;
    }

    public final void config(Feature feature, boolean z) {
        if (z) {
            this.features |= feature.mask;
        } else {
            this.features &= ~feature.mask;
        }
        if (feature == Feature.InitStringFieldAsEmpty) {
            this.stringDefaultValue = z ? "" : null;
        }
        this.disableCircularReferenceDetect = (this.features & Feature.DisableCircularReferenceDetect.mask) != 0;
    }

    public final boolean isEnabled(Feature feature) {
        return (feature.mask & this.features) != 0;
    }

    public final void nextTokenWithChar(char c) {
        this.sp = 0;
        while (true) {
            char c2 = this.ch;
            if (c2 == c) {
                int i = this.bp + 1;
                this.bp = i;
                this.ch = i >= this.len ? EOI : this.text.charAt(i);
                nextToken();
                return;
            } else if (c2 == ' ' || c2 == '\n' || c2 == '\r' || c2 == '\t' || c2 == '\f' || c2 == '\b') {
                next();
            } else {
                throw new JSONException("not match " + c + " - " + this.ch);
            }
        }
    }

    public final boolean isRef() {
        return this.sp == 4 && this.text.startsWith("$ref", this.np + 1);
    }

    public final String numberString() {
        char charAt = this.text.charAt((this.np + this.sp) - 1);
        int i = this.sp;
        if (charAt == 'L' || charAt == 'S' || charAt == 'B' || charAt == 'F' || charAt == 'D') {
            i--;
        }
        return subString(this.np, i);
    }

    protected char charAt(int i) {
        return i >= this.len ? EOI : this.text.charAt(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0025, code lost:
        scanNumber();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0028, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x00da, code lost:
        scanIdent();
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x00dd, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void nextToken() {
        /*
            Method dump skipped, instructions count: 330
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.nextToken():void");
    }

    public final void nextToken(int i) {
        this.sp = 0;
        while (true) {
            if (i != 2) {
                char c = EOI;
                if (i == 4) {
                    char c2 = this.ch;
                    if (c2 == '\"') {
                        this.pos = this.bp;
                        scanString();
                        return;
                    } else if (c2 >= '0' && c2 <= '9') {
                        this.pos = this.bp;
                        scanNumber();
                        return;
                    } else if (c2 == '{') {
                        this.token = 12;
                        int i2 = this.bp + 1;
                        this.bp = i2;
                        if (i2 < this.len) {
                            c = this.text.charAt(i2);
                        }
                        this.ch = c;
                        return;
                    }
                } else if (i == 12) {
                    char c3 = this.ch;
                    if (c3 == '{') {
                        this.token = 12;
                        int i3 = this.bp + 1;
                        this.bp = i3;
                        if (i3 < this.len) {
                            c = this.text.charAt(i3);
                        }
                        this.ch = c;
                        return;
                    } else if (c3 == '[') {
                        this.token = 14;
                        int i4 = this.bp + 1;
                        this.bp = i4;
                        if (i4 < this.len) {
                            c = this.text.charAt(i4);
                        }
                        this.ch = c;
                        return;
                    }
                } else if (i != 18) {
                    if (i != 20) {
                        switch (i) {
                            case 14:
                                char c4 = this.ch;
                                if (c4 == '[') {
                                    this.token = 14;
                                    next();
                                    return;
                                } else if (c4 == '{') {
                                    this.token = 12;
                                    next();
                                    return;
                                }
                                break;
                            case 15:
                                if (this.ch == ']') {
                                    this.token = 15;
                                    next();
                                    return;
                                }
                                break;
                            case 16:
                                char c5 = this.ch;
                                if (c5 == ',') {
                                    this.token = 16;
                                    int i5 = this.bp + 1;
                                    this.bp = i5;
                                    if (i5 < this.len) {
                                        c = this.text.charAt(i5);
                                    }
                                    this.ch = c;
                                    return;
                                } else if (c5 == '}') {
                                    this.token = 13;
                                    int i6 = this.bp + 1;
                                    this.bp = i6;
                                    if (i6 < this.len) {
                                        c = this.text.charAt(i6);
                                    }
                                    this.ch = c;
                                    return;
                                } else if (c5 == ']') {
                                    this.token = 15;
                                    int i7 = this.bp + 1;
                                    this.bp = i7;
                                    if (i7 < this.len) {
                                        c = this.text.charAt(i7);
                                    }
                                    this.ch = c;
                                    return;
                                } else if (c5 == 26) {
                                    this.token = 20;
                                    return;
                                }
                                break;
                        }
                    }
                    if (this.ch == 26) {
                        this.token = 20;
                        return;
                    }
                } else {
                    nextIdent();
                    return;
                }
            } else {
                char c6 = this.ch;
                if (c6 >= '0' && c6 <= '9') {
                    this.pos = this.bp;
                    scanNumber();
                    return;
                } else if (c6 == '\"') {
                    this.pos = this.bp;
                    scanString();
                    return;
                } else if (c6 == '[') {
                    this.token = 14;
                    next();
                    return;
                } else if (c6 == '{') {
                    this.token = 12;
                    next();
                    return;
                }
            }
            char c7 = this.ch;
            if (c7 == ' ' || c7 == '\n' || c7 == '\r' || c7 == '\t' || c7 == '\f' || c7 == '\b') {
                next();
            } else {
                nextToken();
                return;
            }
        }
    }

    public final void nextIdent() {
        char c;
        while (true) {
            c = this.ch;
            if (!(c <= ' ' && (c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == '\f' || c == '\b'))) {
                break;
            }
            next();
        }
        if (c == '_' || Character.isLetter(c)) {
            scanIdent();
        } else {
            nextToken();
        }
    }

    public final String tokenName() {
        return JSONToken.name(this.token);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0081  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x007f -> B:16:0x004a). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Number integerValue() throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 223
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.integerValue():java.lang.Number");
    }

    public final String scanSymbol(SymbolTable symbolTable) {
        char c;
        while (true) {
            c = this.ch;
            if (c != ' ' && c != '\n' && c != '\r' && c != '\t' && c != '\f' && c != '\b') {
                break;
            }
            next();
        }
        if (c == '\"') {
            return scanSymbol(symbolTable, '\"');
        }
        if (c == '\'') {
            if ((this.features & Feature.AllowSingleQuotes.mask) == 0) {
                throw new JSONException("syntax error");
            }
            return scanSymbol(symbolTable, '\'');
        } else if (c == '}') {
            next();
            this.token = 13;
            return null;
        } else if (c == ',') {
            next();
            this.token = 16;
            return null;
        } else if (c == 26) {
            this.token = 20;
            return null;
        } else if ((this.features & Feature.AllowUnQuotedFieldNames.mask) == 0) {
            throw new JSONException("syntax error");
        } else {
            return scanSymbolUnQuoted(symbolTable);
        }
    }

    public String scanSymbol(SymbolTable symbolTable, char c) {
        String readString;
        int i = this.bp + 1;
        int indexOf = this.text.indexOf(c, i);
        if (indexOf == -1) {
            throw new JSONException("unclosed str, " + info());
        }
        int i2 = indexOf - i;
        char[] sub_chars = sub_chars(this.bp + 1, i2);
        boolean z = false;
        while (i2 > 0 && sub_chars[i2 - 1] == '\\') {
            int i3 = 1;
            for (int i4 = i2 - 2; i4 >= 0 && sub_chars[i4] == '\\'; i4--) {
                i3++;
            }
            if (i3 % 2 == 0) {
                break;
            }
            int indexOf2 = this.text.indexOf(c, indexOf + 1);
            int i5 = (indexOf2 - indexOf) + i2;
            if (i5 >= sub_chars.length) {
                int length = (sub_chars.length * 3) / 2;
                if (length < i5) {
                    length = i5;
                }
                char[] cArr = new char[length];
                System.arraycopy(sub_chars, 0, cArr, 0, sub_chars.length);
                sub_chars = cArr;
            }
            this.text.getChars(indexOf, indexOf2, sub_chars, i2);
            indexOf = indexOf2;
            i2 = i5;
            z = true;
        }
        if (z) {
            readString = readString(sub_chars, i2);
        } else {
            int i6 = 0;
            for (int i7 = 0; i7 < i2; i7++) {
                char c2 = sub_chars[i7];
                i6 = (i6 * 31) + c2;
                if (c2 == '\\') {
                    z = true;
                }
            }
            if (z) {
                readString = readString(sub_chars, i2);
            } else {
                readString = i2 < 20 ? symbolTable.addSymbol(sub_chars, 0, i2, i6) : new String(sub_chars, 0, i2);
            }
        }
        int i8 = indexOf + 1;
        this.bp = i8;
        this.ch = i8 >= this.len ? EOI : this.text.charAt(i8);
        return readString;
    }

    private static String readString(char[] cArr, int i) {
        int i2;
        char[] cArr2 = new char[i];
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            char c = cArr[i3];
            if (c != '\\') {
                cArr2[i4] = c;
                i4++;
            } else {
                i3++;
                char c2 = cArr[i3];
                if (c2 == '\"') {
                    i2 = i4 + 1;
                    cArr2[i4] = '\"';
                } else if (c2 != '\'') {
                    if (c2 != 'F') {
                        if (c2 == '\\') {
                            i2 = i4 + 1;
                            cArr2[i4] = '\\';
                        } else if (c2 == 'b') {
                            i2 = i4 + 1;
                            cArr2[i4] = '\b';
                        } else if (c2 != 'f') {
                            if (c2 == 'n') {
                                i2 = i4 + 1;
                                cArr2[i4] = '\n';
                            } else if (c2 == 'r') {
                                i2 = i4 + 1;
                                cArr2[i4] = '\r';
                            } else if (c2 != 'x') {
                                switch (c2) {
                                    case '/':
                                        i2 = i4 + 1;
                                        cArr2[i4] = '/';
                                        break;
                                    case '0':
                                        i2 = i4 + 1;
                                        cArr2[i4] = 0;
                                        break;
                                    case '1':
                                        i2 = i4 + 1;
                                        cArr2[i4] = 1;
                                        break;
                                    case '2':
                                        i2 = i4 + 1;
                                        cArr2[i4] = 2;
                                        break;
                                    case '3':
                                        i2 = i4 + 1;
                                        cArr2[i4] = 3;
                                        break;
                                    case '4':
                                        i2 = i4 + 1;
                                        cArr2[i4] = 4;
                                        break;
                                    case '5':
                                        i2 = i4 + 1;
                                        cArr2[i4] = 5;
                                        break;
                                    case '6':
                                        i2 = i4 + 1;
                                        cArr2[i4] = 6;
                                        break;
                                    case '7':
                                        i2 = i4 + 1;
                                        cArr2[i4] = 7;
                                        break;
                                    default:
                                        switch (c2) {
                                            case 't':
                                                i2 = i4 + 1;
                                                cArr2[i4] = '\t';
                                                break;
                                            case 'u':
                                                i2 = i4 + 1;
                                                int i5 = i3 + 1;
                                                int i6 = i5 + 1;
                                                int i7 = i6 + 1;
                                                i3 = i7 + 1;
                                                cArr2[i4] = (char) Integer.parseInt(new String(new char[]{cArr[i5], cArr[i6], cArr[i7], cArr[i3]}), 16);
                                                break;
                                            case 'v':
                                                i2 = i4 + 1;
                                                cArr2[i4] = 11;
                                                break;
                                            default:
                                                throw new JSONException("unclosed.str.lit");
                                        }
                                }
                            } else {
                                i2 = i4 + 1;
                                int[] iArr = digits;
                                int i8 = i3 + 1;
                                i3 = i8 + 1;
                                cArr2[i4] = (char) ((iArr[cArr[i8]] * 16) + iArr[cArr[i3]]);
                            }
                        }
                    }
                    i2 = i4 + 1;
                    cArr2[i4] = '\f';
                } else {
                    i2 = i4 + 1;
                    cArr2[i4] = '\'';
                }
                i4 = i2;
            }
            i3++;
        }
        return new String(cArr2, 0, i4);
    }

    public String info() {
        return "pos " + this.bp + ", json : " + (this.text.length() < 65536 ? this.text : this.text.substring(0, 65536));
    }

    protected void skipComment() {
        next();
        char c = this.ch;
        if (c == '/') {
            do {
                next();
            } while (this.ch != '\n');
            next();
        } else if (c == '*') {
            next();
            while (true) {
                char c2 = this.ch;
                if (c2 == 26) {
                    return;
                }
                if (c2 == '*') {
                    next();
                    if (this.ch == '/') {
                        next();
                        return;
                    }
                } else {
                    next();
                }
            }
        } else {
            throw new JSONException("invalid comment");
        }
    }

    public final String scanSymbolUnQuoted(SymbolTable symbolTable) {
        int i = this.ch;
        boolean[] zArr = firstIdentifierFlags;
        if (!(i >= zArr.length || zArr[i])) {
            throw new JSONException("illegal identifier : " + this.ch + ", " + info());
        }
        this.np = this.bp;
        this.sp = 1;
        while (true) {
            char next = next();
            boolean[] zArr2 = identifierFlags;
            if (next < zArr2.length && !zArr2[next]) {
                break;
            }
            i = (i * 31) + next;
            this.sp++;
        }
        this.ch = charAt(this.bp);
        this.token = 18;
        if (this.sp == 4 && this.text.startsWith("null", this.np)) {
            return null;
        }
        return symbolTable.addSymbol(this.text, this.np, this.sp, i);
    }

    public final void scanString() {
        char c = this.ch;
        int i = this.bp + 1;
        int indexOf = this.text.indexOf(c, i);
        if (indexOf == -1) {
            throw new JSONException("unclosed str, " + info());
        }
        int i2 = indexOf - i;
        char[] sub_chars = sub_chars(this.bp + 1, i2);
        boolean z = false;
        while (i2 > 0 && sub_chars[i2 - 1] == '\\') {
            int i3 = 1;
            for (int i4 = i2 - 2; i4 >= 0 && sub_chars[i4] == '\\'; i4--) {
                i3++;
            }
            if (i3 % 2 == 0) {
                break;
            }
            int indexOf2 = this.text.indexOf(c, indexOf + 1);
            int i5 = (indexOf2 - indexOf) + i2;
            if (i5 >= sub_chars.length) {
                int length = (sub_chars.length * 3) / 2;
                if (length < i5) {
                    length = i5;
                }
                char[] cArr = new char[length];
                System.arraycopy(sub_chars, 0, cArr, 0, sub_chars.length);
                sub_chars = cArr;
            }
            this.text.getChars(indexOf, indexOf2, sub_chars, i2);
            indexOf = indexOf2;
            i2 = i5;
            z = true;
        }
        if (!z) {
            for (int i6 = 0; i6 < i2; i6++) {
                if (sub_chars[i6] == '\\') {
                    z = true;
                }
            }
        }
        this.sbuf = sub_chars;
        this.sp = i2;
        this.np = this.bp;
        this.hasSpecial = z;
        int i7 = indexOf + 1;
        this.bp = i7;
        this.ch = i7 >= this.len ? EOI : this.text.charAt(i7);
        this.token = 4;
    }

    public String scanStringValue(char c) {
        String str;
        int i = this.bp + 1;
        int indexOf = this.text.indexOf(c, i);
        if (indexOf == -1) {
            throw new JSONException("unclosed str, " + info());
        }
        if (V6) {
            str = this.text.substring(i, indexOf);
        } else {
            int i2 = indexOf - i;
            str = new String(sub_chars(this.bp + 1, i2), 0, i2);
        }
        if (str.indexOf(92) != -1) {
            while (true) {
                int i3 = 0;
                for (int i4 = indexOf - 1; i4 >= 0 && this.text.charAt(i4) == '\\'; i4--) {
                    i3++;
                }
                if (i3 % 2 == 0) {
                    break;
                }
                indexOf = this.text.indexOf(c, indexOf + 1);
            }
            int i5 = indexOf - i;
            str = readString(sub_chars(this.bp + 1, i5), i5);
        }
        int i6 = indexOf + 1;
        this.bp = i6;
        this.ch = i6 >= this.len ? EOI : this.text.charAt(i6);
        return str;
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    public final int intValue() {
        int i;
        boolean z;
        int i2 = this.np;
        int i3 = this.sp + i2;
        int i4 = 0;
        if (charAt(i2) == '-') {
            i = Integer.MIN_VALUE;
            i2++;
            z = true;
        } else {
            i = -2147483647;
            z = false;
        }
        if (i2 < i3) {
            i4 = -(charAt(i2) - '0');
            i2++;
        }
        while (i2 < i3) {
            int i5 = i2 + 1;
            char charAt = charAt(i2);
            if (charAt == 'L' || charAt == 'S' || charAt == 'B') {
                i2 = i5;
                break;
            }
            int i6 = charAt - '0';
            if (i4 < -214748364) {
                throw new NumberFormatException(numberString());
            }
            int i7 = i4 * 10;
            if (i7 < i + i6) {
                throw new NumberFormatException(numberString());
            }
            i4 = i7 - i6;
            i2 = i5;
        }
        if (z) {
            if (i2 > this.np + 1) {
                return i4;
            }
            throw new NumberFormatException(numberString());
        }
        return -i4;
    }

    public byte[] bytesValue() {
        return decodeFast(this.text, this.np + 1, this.sp);
    }

    private void scanTrue() {
        if (this.text.startsWith(OOBEEvent.STRING_TRUE, this.bp)) {
            int i = this.bp + 4;
            this.bp = i;
            char charAt = charAt(i);
            this.ch = charAt;
            if (charAt == ' ' || charAt == ',' || charAt == '}' || charAt == ']' || charAt == '\n' || charAt == '\r' || charAt == '\t' || charAt == 26 || charAt == '\f' || charAt == '\b' || charAt == ':') {
                this.token = 6;
                return;
            }
        }
        throw new JSONException("scan true error");
    }

    private void scanNullOrNew() {
        int i;
        if (this.text.startsWith("null", this.bp)) {
            this.bp += 4;
            i = 8;
        } else if (this.text.startsWith("new", this.bp)) {
            this.bp += 3;
            i = 9;
        } else {
            i = 0;
        }
        if (i != 0) {
            char charAt = charAt(this.bp);
            this.ch = charAt;
            if (charAt == ' ' || charAt == ',' || charAt == '}' || charAt == ']' || charAt == '\n' || charAt == '\r' || charAt == '\t' || charAt == 26 || charAt == '\f' || charAt == '\b') {
                this.token = i;
                return;
            }
        }
        throw new JSONException("scan null/new error");
    }

    private void scanFalse() {
        if (this.text.startsWith(OOBEEvent.STRING_FALSE, this.bp)) {
            int i = this.bp + 5;
            this.bp = i;
            char charAt = charAt(i);
            this.ch = charAt;
            if (charAt == ' ' || charAt == ',' || charAt == '}' || charAt == ']' || charAt == '\n' || charAt == '\r' || charAt == '\t' || charAt == 26 || charAt == '\f' || charAt == '\b' || charAt == ':') {
                this.token = 7;
                return;
            }
        }
        throw new JSONException("scan false error");
    }

    private void scanIdent() {
        this.np = this.bp - 1;
        this.hasSpecial = false;
        do {
            this.sp++;
            next();
        } while (Character.isLetterOrDigit(this.ch));
        String stringVal = stringVal();
        if (stringVal.equals("null")) {
            this.token = 8;
        } else if (stringVal.equals(OOBEEvent.STRING_TRUE)) {
            this.token = 6;
        } else if (stringVal.equals(OOBEEvent.STRING_FALSE)) {
            this.token = 7;
        } else if (stringVal.equals("new")) {
            this.token = 9;
        } else if (stringVal.equals("undefined")) {
            this.token = 23;
        } else if (stringVal.equals("Set")) {
            this.token = 21;
        } else if (stringVal.equals("TreeSet")) {
            this.token = 22;
        } else {
            this.token = 18;
        }
    }

    public final String stringVal() {
        if (this.hasSpecial) {
            return readString(this.sbuf, this.sp);
        }
        return subString(this.np + 1, this.sp);
    }

    private final String subString(int i, int i2) {
        char[] cArr = this.sbuf;
        if (i2 < cArr.length) {
            this.text.getChars(i, i + i2, cArr, 0);
            return new String(this.sbuf, 0, i2);
        }
        char[] cArr2 = new char[i2];
        this.text.getChars(i, i2 + i, cArr2, 0);
        return new String(cArr2);
    }

    final char[] sub_chars(int i, int i2) {
        char[] cArr = this.sbuf;
        if (i2 < cArr.length) {
            this.text.getChars(i, i2 + i, cArr, 0);
            return this.sbuf;
        }
        char[] cArr2 = new char[i2];
        this.sbuf = cArr2;
        this.text.getChars(i, i2 + i, cArr2, 0);
        return cArr2;
    }

    public final boolean isBlankInput() {
        int i = 0;
        while (true) {
            char charAt = charAt(i);
            boolean z = true;
            if (charAt == 26) {
                return true;
            }
            if (charAt > ' ' || (charAt != ' ' && charAt != '\n' && charAt != '\r' && charAt != '\t' && charAt != '\f' && charAt != '\b')) {
                z = false;
            }
            if (!z) {
                return false;
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void skipWhitespace() {
        while (true) {
            char c = this.ch;
            if (c > '/') {
                return;
            }
            if (c == ' ' || c == '\r' || c == '\n' || c == '\t' || c == '\f' || c == '\b') {
                next();
            } else if (c != '/') {
                return;
            } else {
                skipComment();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x013a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void scanNumber() {
        /*
            Method dump skipped, instructions count: 318
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.scanNumber():void");
    }

    public boolean scanBoolean() {
        boolean z = false;
        int i = 1;
        if (this.text.startsWith(OOBEEvent.STRING_FALSE, this.bp)) {
            i = 5;
        } else if (this.text.startsWith(OOBEEvent.STRING_TRUE, this.bp)) {
            z = true;
            i = 4;
        } else {
            char c = this.ch;
            if (c == '1') {
                z = true;
            } else if (c != '0') {
                this.matchStat = -1;
                return false;
            }
        }
        int i2 = this.bp + i;
        this.bp = i2;
        this.ch = charAt(i2);
        return z;
    }

    public final Number scanNumberValue() {
        long j;
        boolean z;
        char c;
        Number number;
        boolean z2;
        char c2;
        Number valueOf;
        int i = this.bp;
        this.np = 0;
        boolean z3 = true;
        if (this.ch == '-') {
            j = Long.MIN_VALUE;
            this.np = 0 + 1;
            int i2 = i + 1;
            this.bp = i2;
            this.ch = i2 >= this.len ? EOI : this.text.charAt(i2);
            z = true;
        } else {
            j = -9223372036854775807L;
            z = false;
        }
        long j2 = 0;
        boolean z4 = false;
        while (true) {
            c = this.ch;
            if (c < '0' || c > '9') {
                break;
            }
            int i3 = c - '0';
            if (j2 < -922337203685477580L) {
                z4 = true;
            }
            long j3 = j2 * 10;
            long j4 = i3;
            if (j3 < j + j4) {
                z4 = true;
            }
            j2 = j3 - j4;
            this.np++;
            int i4 = this.bp + 1;
            this.bp = i4;
            this.ch = i4 >= this.len ? EOI : this.text.charAt(i4);
        }
        if (!z) {
            j2 = -j2;
        }
        if (c == 'L') {
            this.np++;
            next();
            number = Long.valueOf(j2);
        } else if (c == 'S') {
            this.np++;
            next();
            number = Short.valueOf((short) j2);
        } else if (c == 'B') {
            this.np++;
            next();
            number = Byte.valueOf((byte) j2);
        } else if (c == 'F') {
            this.np++;
            next();
            number = Float.valueOf((float) j2);
        } else if (c == 'D') {
            this.np++;
            next();
            number = Double.valueOf(j2);
        } else {
            number = null;
        }
        if (this.ch == '.') {
            this.np++;
            int i5 = this.bp + 1;
            this.bp = i5;
            this.ch = i5 >= this.len ? EOI : this.text.charAt(i5);
            while (true) {
                char c3 = this.ch;
                if (c3 < '0' || c3 > '9') {
                    break;
                }
                this.np++;
                int i6 = this.bp + 1;
                this.bp = i6;
                this.ch = i6 >= this.len ? EOI : this.text.charAt(i6);
            }
            z2 = true;
        } else {
            z2 = false;
        }
        char c4 = this.ch;
        if (c4 == 'e' || c4 == 'E') {
            this.np++;
            int i7 = this.bp + 1;
            this.bp = i7;
            char charAt = i7 >= this.len ? EOI : this.text.charAt(i7);
            this.ch = charAt;
            if (charAt == '+' || charAt == '-') {
                this.np++;
                int i8 = this.bp + 1;
                this.bp = i8;
                this.ch = i8 >= this.len ? EOI : this.text.charAt(i8);
            }
            while (true) {
                c2 = this.ch;
                if (c2 < '0' || c2 > '9') {
                    break;
                }
                this.np++;
                int i9 = this.bp + 1;
                this.bp = i9;
                this.ch = i9 >= this.len ? EOI : this.text.charAt(i9);
            }
            if (c2 == 'D' || c2 == 'F') {
                this.np++;
                next();
            } else {
                c2 = 0;
            }
        } else {
            c2 = 0;
            z3 = false;
        }
        if (!z2 && !z3) {
            if (z4) {
                int i10 = this.bp;
                char[] cArr = new char[i10 - i];
                this.text.getChars(i, i10, cArr, 0);
                number = new BigInteger(new String(cArr));
            }
            if (number == null) {
                if (j2 > -2147483648L && j2 < 2147483647L) {
                    return Integer.valueOf((int) j2);
                }
                return Long.valueOf(j2);
            }
            return number;
        }
        int i11 = this.bp - i;
        if (c2 != 0) {
            i11--;
        }
        char[] cArr2 = new char[i11];
        this.text.getChars(i, i11 + i, cArr2, 0);
        if (!z3 && (this.features & Feature.UseBigDecimal.mask) != 0) {
            return new BigDecimal(cArr2);
        }
        String str = new String(cArr2);
        try {
            if (c2 == 'F') {
                valueOf = Float.valueOf(str);
            } else {
                valueOf = Double.valueOf(Double.parseDouble(str));
            }
            return valueOf;
        } catch (NumberFormatException e) {
            throw new JSONException(e.getMessage() + ", " + info(), e);
        }
    }

    public final long scanLongValue() {
        long j;
        boolean z = false;
        this.np = 0;
        if (this.ch == '-') {
            this.np = 0 + 1;
            int i = this.bp + 1;
            this.bp = i;
            if (i >= this.len) {
                throw new JSONException("syntax error, " + info());
            }
            this.ch = this.text.charAt(i);
            j = Long.MIN_VALUE;
            z = true;
        } else {
            j = -9223372036854775807L;
        }
        long j2 = 0;
        while (true) {
            char c = this.ch;
            if (c < '0' || c > '9') {
                break;
            }
            int i2 = c - '0';
            if (j2 < -922337203685477580L) {
                throw new JSONException("error long value, " + j2 + ", " + info());
            }
            long j3 = j2 * 10;
            long j4 = i2;
            if (j3 < j + j4) {
                throw new JSONException("error long value, " + j3 + ", " + info());
            }
            j2 = j3 - j4;
            this.np++;
            int i3 = this.bp + 1;
            this.bp = i3;
            this.ch = i3 >= this.len ? EOI : this.text.charAt(i3);
        }
        return !z ? -j2 : j2;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0086  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x005d -> B:8:0x0026). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long longValue() throws java.lang.NumberFormatException {
        /*
            r13 = this;
            int r0 = r13.np
            int r1 = r13.sp
            int r1 = r1 + r0
            char r2 = r13.charAt(r0)
            r3 = 1
            r4 = 45
            if (r2 != r4) goto L14
            r4 = -9223372036854775808
            int r0 = r0 + 1
            r2 = r3
            goto L1a
        L14:
            r4 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r2 = 0
        L1a:
            if (r0 >= r1) goto L28
            int r6 = r0 + 1
            char r0 = r13.charAt(r0)
            int r0 = r0 + (-48)
            int r0 = -r0
            long r7 = (long) r0
        L26:
            r0 = r6
            goto L2a
        L28:
            r7 = 0
        L2a:
            if (r0 >= r1) goto L74
            int r6 = r0 + 1
            int r9 = r13.len
            if (r0 < r9) goto L35
            r0 = 26
            goto L3b
        L35:
            java.lang.String r9 = r13.text
            char r0 = r9.charAt(r0)
        L3b:
            r9 = 76
            if (r0 == r9) goto L73
            r9 = 83
            if (r0 == r9) goto L73
            r9 = 66
            if (r0 != r9) goto L48
            goto L73
        L48:
            int r0 = r0 + (-48)
            r9 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r9 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r9 < 0) goto L69
            r9 = 10
            long r7 = r7 * r9
            long r9 = (long) r0
            long r11 = r4 + r9
            int r0 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r0 < 0) goto L5f
            long r7 = r7 - r9
            goto L26
        L5f:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = r13.numberString()
            r0.<init>(r1)
            throw r0
        L69:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = r13.numberString()
            r0.<init>(r1)
            throw r0
        L73:
            r0 = r6
        L74:
            if (r2 == 0) goto L86
            int r1 = r13.np
            int r1 = r1 + r3
            if (r0 <= r1) goto L7c
            return r7
        L7c:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = r13.numberString()
            r0.<init>(r1)
            throw r0
        L86:
            long r0 = -r7
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.longValue():long");
    }

    public final Number decimalValue(boolean z) {
        char charAt = charAt((this.np + this.sp) - 1);
        try {
            if (charAt == 'F') {
                return Float.valueOf(Float.parseFloat(numberString()));
            }
            if (charAt == 'D') {
                return Double.valueOf(Double.parseDouble(numberString()));
            }
            if (z) {
                return decimalValue();
            }
            return Double.valueOf(Double.parseDouble(numberString()));
        } catch (NumberFormatException e) {
            throw new JSONException(e.getMessage() + ", " + info());
        }
    }

    public final BigDecimal decimalValue() {
        return new BigDecimal(numberString());
    }

    public boolean matchField(char[] cArr) {
        if (charArrayCompare(cArr)) {
            int length = this.bp + cArr.length;
            this.bp = length;
            if (length >= this.len) {
                throw new JSONException("unclosed str, " + info());
            }
            char charAt = this.text.charAt(length);
            this.ch = charAt;
            char c = EOI;
            if (charAt == '{') {
                int i = this.bp + 1;
                this.bp = i;
                if (i < this.len) {
                    c = this.text.charAt(i);
                }
                this.ch = c;
                this.token = 12;
            } else if (charAt == '[') {
                int i2 = this.bp + 1;
                this.bp = i2;
                if (i2 < this.len) {
                    c = this.text.charAt(i2);
                }
                this.ch = c;
                this.token = 14;
            } else {
                nextToken();
            }
            return true;
        }
        return false;
    }

    private boolean charArrayCompare(char[] cArr) {
        int length = cArr.length;
        if (this.bp + length > this.len) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (cArr[i] != this.text.charAt(this.bp + i)) {
                return false;
            }
        }
        return true;
    }

    public int scanFieldInt(char[] cArr) {
        boolean z;
        int i;
        char charAt;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0;
        }
        int length = cArr.length;
        int i2 = length + 1;
        char charAt2 = charAt(this.bp + length);
        char c = EOI;
        if (charAt2 == '\"') {
            int i3 = i2 + 1;
            int i4 = this.bp + i2;
            charAt2 = i4 >= this.len ? (char) 26 : this.text.charAt(i4);
            i2 = i3;
            z = true;
        } else {
            z = false;
        }
        if (charAt2 < '0' || charAt2 > '9') {
            this.matchStat = -1;
            return 0;
        }
        int i5 = charAt2 - '0';
        while (true) {
            i = i2 + 1;
            charAt = charAt(this.bp + i2);
            if (charAt < '0' || charAt > '9') {
                break;
            }
            i5 = (i5 * 10) + (charAt - '0');
            i2 = i;
        }
        if (charAt == '.') {
            this.matchStat = -1;
            return 0;
        }
        if (charAt == '\"') {
            if (!z) {
                this.matchStat = -1;
                return 0;
            }
            int i6 = i + 1;
            int i7 = this.bp + i;
            i = i6;
            charAt = i7 >= this.len ? (char) 26 : this.text.charAt(i7);
        }
        if (i5 < 0) {
            this.matchStat = -1;
            return 0;
        } else if (charAt == ',') {
            int i8 = this.bp + (i - 1);
            this.bp = i8;
            int i9 = i8 + 1;
            this.bp = i9;
            if (i9 < this.len) {
                c = this.text.charAt(i9);
            }
            this.ch = c;
            this.matchStat = 3;
            this.token = 16;
            return i5;
        } else if (charAt == '}') {
            int i10 = i + 1;
            char charAt3 = charAt(this.bp + i);
            if (charAt3 == ',') {
                this.token = 16;
                int i11 = this.bp + (i10 - 1);
                this.bp = i11;
                int i12 = i11 + 1;
                this.bp = i12;
                if (i12 < this.len) {
                    c = this.text.charAt(i12);
                }
                this.ch = c;
            } else if (charAt3 == ']') {
                this.token = 15;
                int i13 = this.bp + (i10 - 1);
                this.bp = i13;
                int i14 = i13 + 1;
                this.bp = i14;
                if (i14 < this.len) {
                    c = this.text.charAt(i14);
                }
                this.ch = c;
            } else if (charAt3 == '}') {
                this.token = 13;
                int i15 = this.bp + (i10 - 1);
                this.bp = i15;
                int i16 = i15 + 1;
                this.bp = i16;
                if (i16 < this.len) {
                    c = this.text.charAt(i16);
                }
                this.ch = c;
            } else if (charAt3 == 26) {
                this.token = 20;
                this.bp += i10 - 1;
                this.ch = EOI;
            } else {
                this.matchStat = -1;
                return 0;
            }
            this.matchStat = 4;
            return i5;
        } else {
            this.matchStat = -1;
            return 0;
        }
    }

    public long scanFieldLong(char[] cArr) {
        int i;
        char charAt;
        boolean z = false;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = cArr.length;
        int i2 = length + 1;
        int i3 = this.bp + length;
        char charAt2 = i3 >= this.len ? EOI : this.text.charAt(i3);
        if (charAt2 == '\"') {
            int i4 = i2 + 1;
            int i5 = this.bp + i2;
            i2 = i4;
            charAt2 = i5 >= this.len ? EOI : this.text.charAt(i5);
            z = true;
        }
        if (charAt2 < '0' || charAt2 > '9') {
            this.matchStat = -1;
            return 0L;
        }
        long j = charAt2 - '0';
        while (true) {
            i = i2 + 1;
            int i6 = this.bp + i2;
            charAt = i6 >= this.len ? EOI : this.text.charAt(i6);
            if (charAt < '0' || charAt > '9') {
                break;
            }
            j = (j * 10) + (charAt - '0');
            i2 = i;
        }
        if (charAt == '.') {
            this.matchStat = -1;
            return 0L;
        }
        if (charAt == '\"') {
            if (!z) {
                this.matchStat = -1;
                return 0L;
            }
            int i7 = i + 1;
            int i8 = this.bp + i;
            i = i7;
            charAt = i8 >= this.len ? EOI : this.text.charAt(i8);
        }
        if (j < 0) {
            this.matchStat = -1;
            return 0L;
        } else if (charAt == ',') {
            int i9 = this.bp + (i - 1);
            this.bp = i9;
            int i10 = i9 + 1;
            this.bp = i10;
            this.ch = i10 >= this.len ? EOI : this.text.charAt(i10);
            this.matchStat = 3;
            this.token = 16;
            return j;
        } else if (charAt == '}') {
            int i11 = i + 1;
            char charAt3 = charAt(this.bp + i);
            if (charAt3 == ',') {
                this.token = 16;
                int i12 = this.bp + (i11 - 1);
                this.bp = i12;
                int i13 = i12 + 1;
                this.bp = i13;
                this.ch = i13 >= this.len ? EOI : this.text.charAt(i13);
            } else if (charAt3 == ']') {
                this.token = 15;
                int i14 = this.bp + (i11 - 1);
                this.bp = i14;
                int i15 = i14 + 1;
                this.bp = i15;
                this.ch = i15 >= this.len ? EOI : this.text.charAt(i15);
            } else if (charAt3 == '}') {
                this.token = 13;
                int i16 = this.bp + (i11 - 1);
                this.bp = i16;
                int i17 = i16 + 1;
                this.bp = i17;
                this.ch = i17 >= this.len ? EOI : this.text.charAt(i17);
            } else if (charAt3 == 26) {
                this.token = 20;
                this.bp += i11 - 1;
                this.ch = EOI;
            } else {
                this.matchStat = -1;
                return 0L;
            }
            this.matchStat = 4;
            return j;
        } else {
            this.matchStat = -1;
            return 0L;
        }
    }

    public String scanFieldString(char[] cArr) {
        String str;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return this.stringDefaultValue;
        }
        int length = cArr.length;
        int i = length + 1;
        int i2 = this.bp + length;
        if (i2 >= this.len) {
            throw new JSONException("unclosed str, " + info());
        }
        if (this.text.charAt(i2) != '\"') {
            this.matchStat = -1;
            return this.stringDefaultValue;
        }
        int i3 = this.bp + i;
        int indexOf = this.text.indexOf(34, i3);
        if (indexOf == -1) {
            throw new JSONException("unclosed str, " + info());
        }
        if (V6) {
            str = this.text.substring(i3, indexOf);
        } else {
            int i4 = indexOf - i3;
            str = new String(sub_chars(this.bp + i, i4), 0, i4);
        }
        if (str.indexOf(92) != -1) {
            boolean z = false;
            while (true) {
                int i5 = indexOf - 1;
                int i6 = 0;
                while (i5 >= 0 && this.text.charAt(i5) == '\\') {
                    i6++;
                    i5--;
                    z = true;
                }
                if (i6 % 2 == 0) {
                    break;
                }
                indexOf = this.text.indexOf(34, indexOf + 1);
            }
            int i7 = indexOf - i3;
            char[] sub_chars = sub_chars(this.bp + i, i7);
            if (z) {
                str = readString(sub_chars, i7);
            } else {
                str = new String(sub_chars, 0, i7);
                if (str.indexOf(92) != -1) {
                    str = readString(sub_chars, i7);
                }
            }
        }
        int i8 = indexOf + 1;
        int i9 = this.len;
        char c = EOI;
        char charAt = i8 >= i9 ? (char) 26 : this.text.charAt(i8);
        if (charAt == ',') {
            this.bp = i8;
            int i10 = i8 + 1;
            this.bp = i10;
            if (i10 < this.len) {
                c = this.text.charAt(i10);
            }
            this.ch = c;
            this.matchStat = 3;
            this.token = 16;
            return str;
        } else if (charAt == '}') {
            int i11 = i8 + 1;
            char charAt2 = charAt(i11);
            if (charAt2 == ',') {
                this.token = 16;
                this.bp = i11;
                next();
            } else if (charAt2 == ']') {
                this.token = 15;
                this.bp = i11;
                next();
            } else if (charAt2 == '}') {
                this.token = 13;
                this.bp = i11;
                next();
            } else if (charAt2 == 26) {
                this.token = 20;
                this.bp = i11;
                this.ch = EOI;
            } else {
                this.matchStat = -1;
                return this.stringDefaultValue;
            }
            this.matchStat = 4;
            return str;
        } else {
            this.matchStat = -1;
            return this.stringDefaultValue;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean scanFieldBoolean(char[] r12) {
        /*
            Method dump skipped, instructions count: 254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.scanFieldBoolean(char[]):boolean");
    }

    public final float scanFieldFloat(char[] cArr) {
        int i;
        char charAt;
        int i2;
        int length;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0.0f;
        }
        int length2 = cArr.length;
        int i3 = length2 + 1;
        char charAt2 = charAt(this.bp + length2);
        if (charAt2 >= '0' && charAt2 <= '9') {
            while (true) {
                i = i3 + 1;
                charAt = charAt(this.bp + i3);
                if (charAt < '0' || charAt > '9') {
                    break;
                }
                i3 = i;
            }
            if (charAt == '.') {
                int i4 = i + 1;
                char charAt3 = charAt(this.bp + i);
                if (charAt3 >= '0' && charAt3 <= '9') {
                    while (true) {
                        i = i4 + 1;
                        charAt = charAt(this.bp + i4);
                        if (charAt < '0' || charAt > '9') {
                            break;
                        }
                        i4 = i;
                    }
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
            float parseFloat = Float.parseFloat(subString(cArr.length + this.bp, ((i2 + i) - length) - 1));
            if (charAt == ',') {
                this.bp += i - 1;
                next();
                this.matchStat = 3;
                this.token = 16;
                return parseFloat;
            } else if (charAt == '}') {
                int i5 = i + 1;
                char charAt4 = charAt(this.bp + i);
                if (charAt4 == ',') {
                    this.token = 16;
                    this.bp += i5 - 1;
                    next();
                } else if (charAt4 == ']') {
                    this.token = 15;
                    this.bp += i5 - 1;
                    next();
                } else if (charAt4 == '}') {
                    this.token = 13;
                    this.bp += i5 - 1;
                    next();
                } else if (charAt4 == 26) {
                    this.bp += i5 - 1;
                    this.token = 20;
                    this.ch = EOI;
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
                this.matchStat = 4;
                return parseFloat;
            } else {
                this.matchStat = -1;
                return 0.0f;
            }
        }
        this.matchStat = -1;
        return 0.0f;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x007b A[ADDED_TO_REGION] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x007d -> B:34:0x006e). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final double scanFieldDouble(char[] r11) {
        /*
            Method dump skipped, instructions count: 267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexer.scanFieldDouble(char[]):double");
    }

    public String scanFieldSymbol(char[] cArr, SymbolTable symbolTable) {
        int i;
        int length;
        int i2 = 0;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length2 = cArr.length;
        int i3 = length2 + 1;
        if (charAt(this.bp + length2) != '\"') {
            this.matchStat = -1;
            return null;
        }
        while (true) {
            int i4 = i3 + 1;
            char charAt = charAt(this.bp + i3);
            if (charAt == '\"') {
                String addSymbol = symbolTable.addSymbol(this.text, cArr.length + this.bp + 1, ((i + i4) - length) - 1, i2);
                int i5 = i4 + 1;
                char charAt2 = charAt(this.bp + i4);
                char c = EOI;
                if (charAt2 == ',') {
                    int i6 = this.bp + (i5 - 1);
                    this.bp = i6;
                    int i7 = i6 + 1;
                    this.bp = i7;
                    if (i7 < this.len) {
                        c = this.text.charAt(i7);
                    }
                    this.ch = c;
                    this.matchStat = 3;
                    return addSymbol;
                } else if (charAt2 == '}') {
                    int i8 = i5 + 1;
                    char charAt3 = charAt(this.bp + i5);
                    if (charAt3 == ',') {
                        this.token = 16;
                        this.bp += i8 - 1;
                        next();
                    } else if (charAt3 == ']') {
                        this.token = 15;
                        this.bp += i8 - 1;
                        next();
                    } else if (charAt3 == '}') {
                        this.token = 13;
                        this.bp += i8 - 1;
                        next();
                    } else if (charAt3 == 26) {
                        this.token = 20;
                        this.bp += i8 - 1;
                        this.ch = EOI;
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                    this.matchStat = 4;
                    return addSymbol;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            i2 = (i2 * 31) + charAt;
            if (charAt == '\\') {
                this.matchStat = -1;
                return null;
            }
            i3 = i4;
        }
    }

    public boolean scanISO8601DateIfMatch(boolean z) {
        int i;
        int i2;
        int i3;
        char c;
        char charAt;
        int i4;
        char charAt2;
        int i5;
        int i6;
        char charAt3;
        int length = this.text.length();
        int i7 = this.bp;
        int i8 = length - i7;
        if (!z && i8 > 13 && this.text.startsWith("/Date(", i7) && charAt((this.bp + i8) - 1) == '/' && charAt((this.bp + i8) - 2) == ')') {
            int i9 = -1;
            for (int i10 = 6; i10 < i8; i10++) {
                char charAt4 = charAt(this.bp + i10);
                if (charAt4 != '+') {
                    if (charAt4 < '0' || charAt4 > '9') {
                        break;
                    }
                } else {
                    i9 = i10;
                }
            }
            if (i9 == -1) {
                return false;
            }
            int i11 = this.bp + 6;
            long parseLong = Long.parseLong(subString(i11, i9 - i11));
            Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
            this.calendar = calendar;
            calendar.setTimeInMillis(parseLong);
            this.token = 5;
            return true;
        } else if (i8 == 8 || i8 == 14 || i8 == 17) {
            int i12 = 0;
            if (z) {
                return false;
            }
            char charAt5 = charAt(this.bp);
            char charAt6 = charAt(this.bp + 1);
            char charAt7 = charAt(this.bp + 2);
            char charAt8 = charAt(this.bp + 3);
            char charAt9 = charAt(this.bp + 4);
            char charAt10 = charAt(this.bp + 5);
            char charAt11 = charAt(this.bp + 6);
            char charAt12 = charAt(this.bp + 7);
            if (checkDate(charAt5, charAt6, charAt7, charAt8, charAt9, charAt10, charAt11, charAt12)) {
                setCalendar(charAt5, charAt6, charAt7, charAt8, charAt9, charAt10, charAt11, charAt12);
                if (i8 != 8) {
                    char charAt13 = charAt(this.bp + 8);
                    char charAt14 = charAt(this.bp + 9);
                    char charAt15 = charAt(this.bp + 10);
                    char charAt16 = charAt(this.bp + 11);
                    char charAt17 = charAt(this.bp + 12);
                    char charAt18 = charAt(this.bp + 13);
                    if (!checkTime(charAt13, charAt14, charAt15, charAt16, charAt17, charAt18)) {
                        return false;
                    }
                    if (i8 == 17) {
                        char charAt19 = charAt(this.bp + 14);
                        char charAt20 = charAt(this.bp + 15);
                        char charAt21 = charAt(this.bp + 16);
                        c = '0';
                        if (charAt19 < '0' || charAt19 > '9' || charAt20 < '0' || charAt20 > '9' || charAt21 < '0' || charAt21 > '9') {
                            return false;
                        }
                        i12 = ((charAt19 - '0') * 100) + ((charAt20 - '0') * 10) + (charAt21 - '0');
                    } else {
                        c = '0';
                    }
                    i3 = (charAt14 - c) + ((charAt13 - c) * 10);
                    i2 = ((charAt17 - '0') * 10) + (charAt18 - '0');
                    int i13 = i12;
                    i12 = ((charAt15 - c) * 10) + (charAt16 - c);
                    i = i13;
                } else {
                    i = 0;
                    i2 = 0;
                    i3 = 0;
                }
                this.calendar.set(11, i3);
                this.calendar.set(12, i12);
                this.calendar.set(13, i2);
                this.calendar.set(14, i);
                this.token = 5;
                return true;
            }
            return false;
        } else if (i8 >= 10 && charAt(this.bp + 4) == '-' && charAt(this.bp + 7) == '-') {
            char charAt22 = charAt(this.bp);
            char charAt23 = charAt(this.bp + 1);
            char charAt24 = charAt(this.bp + 2);
            char charAt25 = charAt(this.bp + 3);
            char charAt26 = charAt(this.bp + 5);
            char charAt27 = charAt(this.bp + 6);
            char charAt28 = charAt(this.bp + 8);
            char charAt29 = charAt(this.bp + 9);
            if (checkDate(charAt22, charAt23, charAt24, charAt25, charAt26, charAt27, charAt28, charAt29)) {
                setCalendar(charAt22, charAt23, charAt24, charAt25, charAt26, charAt27, charAt28, charAt29);
                char charAt30 = charAt(this.bp + 10);
                if (charAt30 != 'T' && (charAt30 != ' ' || z)) {
                    if (charAt30 == '\"' || charAt30 == 26) {
                        this.calendar.set(11, 0);
                        this.calendar.set(12, 0);
                        this.calendar.set(13, 0);
                        this.calendar.set(14, 0);
                        int i14 = this.bp + 10;
                        this.bp = i14;
                        this.ch = charAt(i14);
                        this.token = 5;
                        return true;
                    }
                    return false;
                } else if (i8 >= 19 && charAt(this.bp + 13) == ':' && charAt(this.bp + 16) == ':') {
                    char charAt31 = charAt(this.bp + 11);
                    char charAt32 = charAt(this.bp + 12);
                    char charAt33 = charAt(this.bp + 14);
                    char charAt34 = charAt(this.bp + 15);
                    char charAt35 = charAt(this.bp + 17);
                    char charAt36 = charAt(this.bp + 18);
                    if (checkTime(charAt31, charAt32, charAt33, charAt34, charAt35, charAt36)) {
                        this.calendar.set(11, ((charAt31 - '0') * 10) + (charAt32 - '0'));
                        this.calendar.set(12, ((charAt33 - '0') * 10) + (charAt34 - '0'));
                        this.calendar.set(13, ((charAt35 - '0') * 10) + (charAt36 - '0'));
                        char charAt37 = charAt(this.bp + 19);
                        if (charAt37 != '.') {
                            this.calendar.set(14, 0);
                            int i15 = this.bp + 19;
                            this.bp = i15;
                            this.ch = charAt(i15);
                            this.token = 5;
                            if (charAt37 == 'Z' && this.calendar.getTimeZone().getRawOffset() != 0) {
                                String[] availableIDs = TimeZone.getAvailableIDs(0);
                                if (availableIDs.length > 0) {
                                    this.calendar.setTimeZone(TimeZone.getTimeZone(availableIDs[0]));
                                }
                            }
                            return true;
                        }
                        if (i8 >= 23 && (charAt = charAt(this.bp + 20)) >= '0' && charAt <= '9') {
                            int[] iArr = digits;
                            int i16 = iArr[charAt];
                            char charAt38 = charAt(this.bp + 21);
                            if (charAt38 < '0' || charAt38 > '9') {
                                i4 = 1;
                            } else {
                                i16 = (i16 * 10) + iArr[charAt38];
                                i4 = 2;
                            }
                            if (i4 == 2 && (charAt3 = charAt(this.bp + 22)) >= '0' && charAt3 <= '9') {
                                i16 = (i16 * 10) + iArr[charAt3];
                                i4 = 3;
                            }
                            this.calendar.set(14, i16);
                            char charAt39 = charAt(this.bp + 20 + i4);
                            if (charAt39 == '+' || charAt39 == '-') {
                                char charAt40 = charAt(this.bp + 20 + i4 + 1);
                                if (charAt40 >= '0' && charAt40 <= '1' && (charAt2 = charAt(this.bp + 20 + i4 + 2)) >= '0' && charAt2 <= '9') {
                                    char charAt41 = charAt(this.bp + 20 + i4 + 3);
                                    if (charAt41 == ':') {
                                        if (charAt(this.bp + 20 + i4 + 4) != '0' || charAt(this.bp + 20 + i4 + 5) != '0') {
                                            return false;
                                        }
                                        i5 = 6;
                                    } else if (charAt41 != '0') {
                                        i5 = 3;
                                    } else if (charAt(this.bp + 20 + i4 + 4) != '0') {
                                        return false;
                                    } else {
                                        i5 = 5;
                                    }
                                    int i17 = ((iArr[charAt40] * 10) + iArr[charAt2]) * 3600 * 1000;
                                    if (charAt39 == '-') {
                                        i17 = -i17;
                                    }
                                    if (this.calendar.getTimeZone().getRawOffset() != i17) {
                                        String[] availableIDs2 = TimeZone.getAvailableIDs(i17);
                                        if (availableIDs2.length > 0) {
                                            this.calendar.setTimeZone(TimeZone.getTimeZone(availableIDs2[0]));
                                        }
                                    }
                                    i6 = i5;
                                }
                            } else if (charAt39 == 'Z') {
                                if (this.calendar.getTimeZone().getRawOffset() != 0) {
                                    String[] availableIDs3 = TimeZone.getAvailableIDs(0);
                                    if (availableIDs3.length > 0) {
                                        this.calendar.setTimeZone(TimeZone.getTimeZone(availableIDs3[0]));
                                    }
                                }
                                i6 = 1;
                            } else {
                                i6 = 0;
                            }
                            int i18 = i4 + 20 + i6;
                            char charAt42 = charAt(this.bp + i18);
                            if (charAt42 == 26 || charAt42 == '\"') {
                                int i19 = this.bp + i18;
                                this.bp = i19;
                                this.ch = charAt(i19);
                                this.token = 5;
                                return true;
                            }
                            return false;
                        }
                        return false;
                    }
                    return false;
                } else {
                    return false;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    private void setCalendar(char c, char c2, char c3, char c4, char c5, char c6, char c7, char c8) {
        Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
        this.calendar = calendar;
        calendar.set(1, ((c - '0') * 1000) + ((c2 - '0') * 100) + ((c3 - '0') * 10) + (c4 - '0'));
        this.calendar.set(2, (((c5 - '0') * 10) + (c6 - '0')) - 1);
        this.calendar.set(5, ((c7 - '0') * 10) + (c8 - '0'));
    }

    public static final byte[] decodeFast(char[] cArr, int i, int i2) {
        int i3;
        int i4 = 0;
        if (i2 == 0) {
            return new byte[0];
        }
        int i5 = (i + i2) - 1;
        while (i < i5 && IA[cArr[i]] < 0) {
            i++;
        }
        while (i5 > 0 && IA[cArr[i5]] < 0) {
            i5--;
        }
        int i6 = cArr[i5] == '=' ? cArr[i5 + (-1)] == '=' ? 2 : 1 : 0;
        int i7 = (i5 - i) + 1;
        if (i2 > 76) {
            i3 = (cArr[76] == '\r' ? i7 / 78 : 0) << 1;
        } else {
            i3 = 0;
        }
        int i8 = (((i7 - i3) * 6) >> 3) - i6;
        byte[] bArr = new byte[i8];
        int i9 = (i8 / 3) * 3;
        int i10 = 0;
        int i11 = 0;
        while (i10 < i9) {
            int[] iArr = IA;
            int i12 = i + 1;
            int i13 = i12 + 1;
            int i14 = (iArr[cArr[i]] << 18) | (iArr[cArr[i12]] << 12);
            int i15 = i13 + 1;
            int i16 = i14 | (iArr[cArr[i13]] << 6);
            int i17 = i15 + 1;
            int i18 = i16 | iArr[cArr[i15]];
            int i19 = i10 + 1;
            bArr[i10] = (byte) (i18 >> 16);
            int i20 = i19 + 1;
            bArr[i19] = (byte) (i18 >> 8);
            int i21 = i20 + 1;
            bArr[i20] = (byte) i18;
            if (i3 > 0 && (i11 = i11 + 1) == 19) {
                i17 += 2;
                i11 = 0;
            }
            i = i17;
            i10 = i21;
        }
        if (i10 < i8) {
            int i22 = 0;
            while (i <= i5 - i6) {
                i4 |= IA[cArr[i]] << (18 - (i22 * 6));
                i22++;
                i++;
            }
            int i23 = 16;
            while (i10 < i8) {
                bArr[i10] = (byte) (i4 >> i23);
                i23 -= 8;
                i10++;
            }
        }
        return bArr;
    }

    public static final byte[] decodeFast(String str, int i, int i2) {
        int i3;
        int i4 = 0;
        if (i2 == 0) {
            return new byte[0];
        }
        int i5 = (i + i2) - 1;
        while (i < i5 && IA[str.charAt(i)] < 0) {
            i++;
        }
        while (i5 > 0 && IA[str.charAt(i5)] < 0) {
            i5--;
        }
        int i6 = str.charAt(i5) == '=' ? str.charAt(i5 + (-1)) == '=' ? 2 : 1 : 0;
        int i7 = (i5 - i) + 1;
        if (i2 > 76) {
            i3 = (str.charAt(76) == '\r' ? i7 / 78 : 0) << 1;
        } else {
            i3 = 0;
        }
        int i8 = (((i7 - i3) * 6) >> 3) - i6;
        byte[] bArr = new byte[i8];
        int i9 = (i8 / 3) * 3;
        int i10 = 0;
        int i11 = 0;
        while (i10 < i9) {
            int[] iArr = IA;
            int i12 = i + 1;
            int i13 = i12 + 1;
            int i14 = (iArr[str.charAt(i)] << 18) | (iArr[str.charAt(i12)] << 12);
            int i15 = i13 + 1;
            int i16 = i14 | (iArr[str.charAt(i13)] << 6);
            int i17 = i15 + 1;
            int i18 = i16 | iArr[str.charAt(i15)];
            int i19 = i10 + 1;
            bArr[i10] = (byte) (i18 >> 16);
            int i20 = i19 + 1;
            bArr[i19] = (byte) (i18 >> 8);
            int i21 = i20 + 1;
            bArr[i20] = (byte) i18;
            if (i3 > 0 && (i11 = i11 + 1) == 19) {
                i17 += 2;
                i11 = 0;
            }
            i = i17;
            i10 = i21;
        }
        if (i10 < i8) {
            int i22 = 0;
            while (i <= i5 - i6) {
                i4 |= IA[str.charAt(i)] << (18 - (i22 * 6));
                i22++;
                i++;
            }
            int i23 = 16;
            while (i10 < i8) {
                bArr[i10] = (byte) (i4 >> i23);
                i23 -= 8;
                i10++;
            }
        }
        return bArr;
    }

    public static final byte[] decodeFast(String str) {
        int i;
        int length = str.length();
        int i2 = 0;
        if (length == 0) {
            return new byte[0];
        }
        int i3 = length - 1;
        int i4 = 0;
        while (i4 < i3 && IA[str.charAt(i4) & 255] < 0) {
            i4++;
        }
        while (i3 > 0 && IA[str.charAt(i3) & 255] < 0) {
            i3--;
        }
        int i5 = str.charAt(i3) == '=' ? str.charAt(i3 + (-1)) == '=' ? 2 : 1 : 0;
        int i6 = (i3 - i4) + 1;
        if (length > 76) {
            i = (str.charAt(76) == '\r' ? i6 / 78 : 0) << 1;
        } else {
            i = 0;
        }
        int i7 = (((i6 - i) * 6) >> 3) - i5;
        byte[] bArr = new byte[i7];
        int i8 = (i7 / 3) * 3;
        int i9 = 0;
        int i10 = 0;
        while (i9 < i8) {
            int[] iArr = IA;
            int i11 = i4 + 1;
            int i12 = i11 + 1;
            int i13 = (iArr[str.charAt(i4)] << 18) | (iArr[str.charAt(i11)] << 12);
            int i14 = i12 + 1;
            int i15 = i13 | (iArr[str.charAt(i12)] << 6);
            int i16 = i14 + 1;
            int i17 = i15 | iArr[str.charAt(i14)];
            int i18 = i9 + 1;
            bArr[i9] = (byte) (i17 >> 16);
            int i19 = i18 + 1;
            bArr[i18] = (byte) (i17 >> 8);
            int i20 = i19 + 1;
            bArr[i19] = (byte) i17;
            if (i > 0 && (i10 = i10 + 1) == 19) {
                i16 += 2;
                i10 = 0;
            }
            i4 = i16;
            i9 = i20;
        }
        if (i9 < i7) {
            int i21 = 0;
            while (i4 <= i3 - i5) {
                i2 |= IA[str.charAt(i4)] << (18 - (i21 * 6));
                i21++;
                i4++;
            }
            int i22 = 16;
            while (i9 < i7) {
                bArr[i9] = (byte) (i2 >> i22);
                i22 -= 8;
                i9++;
            }
        }
        return bArr;
    }
}
