package com.O000000o.O000000o.O00000o;

import com.xiaopeng.speech.protocol.event.OOBEEvent;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

/* compiled from: JsonReader.java */
/* loaded from: classes.dex */
public class O000000o implements Closeable {
    private static final char[] O000000o = ")]}'\n".toCharArray();
    private static final long O00000Oo = -922337203685477580L;
    private static final int O00000o = 1;
    private static final int O00000o0 = 0;
    private static final int O00000oO = 2;
    private static final int O00000oo = 3;
    private static final int O0000O0o = 4;
    private static final int O0000OOo = 5;
    private static final int O0000Oo = 7;
    private static final int O0000Oo0 = 6;
    private static final int O0000OoO = 8;
    private static final int O0000Ooo = 9;
    private static final int O0000o = 14;
    private static final int O0000o0 = 11;
    private static final int O0000o00 = 10;
    private static final int O0000o0O = 12;
    private static final int O0000o0o = 13;
    private static final int O0000oO = 16;
    private static final int O0000oO0 = 15;
    private static final int O0000oOO = 17;
    private static final int O0000oOo = 0;
    private static final int O0000oo = 2;
    private static final int O0000oo0 = 1;
    private static final int O0000ooO = 3;
    private static final int O0000ooo = 4;
    private static final int O000O00o = 7;
    private static final int O00oOooO = 5;
    private static final int O00oOooo = 6;
    private final Reader O000O0OO;
    private int O000OO;
    private long O000OO0o;
    private String O000OOOo;
    private int O000OOo;
    private int[] O000OOo0;
    private boolean O000O0Oo = false;
    private final char[] O00oOoOo = new char[1024];
    private int O000O0o0 = 0;
    private int O000O0o = 0;
    private int O000O0oO = 0;
    private int O000O0oo = 0;
    private int O000OO00 = 0;

    static {
        com.O000000o.O000000o.O00000Oo.O0000O0o.O000000o = new com.O000000o.O000000o.O00000Oo.O0000O0o() { // from class: com.O000000o.O000000o.O00000o.O000000o.1
            @Override // com.O000000o.O000000o.O00000Oo.O0000O0o
            public void O000000o(O000000o o000000o) throws IOException {
                int i;
                if (o000000o instanceof com.O000000o.O000000o.O00000Oo.O000000o.O00000o) {
                    ((com.O000000o.O000000o.O00000Oo.O000000o.O00000o) o000000o).O0000o0O();
                    return;
                }
                int i2 = o000000o.O000OO00;
                if (i2 == 0) {
                    i2 = o000000o.O0000o0O();
                }
                if (i2 == 13) {
                    i = 9;
                } else if (i2 == 12) {
                    i = 8;
                } else if (i2 != 14) {
                    throw new IllegalStateException("Expected a name but was " + o000000o.O00000oo() + "  at line " + o000000o.O0000oOo() + " column " + o000000o.O0000oo0());
                } else {
                    i = 10;
                }
                o000000o.O000OO00 = i;
            }
        };
    }

    public O000000o(Reader reader) {
        int[] iArr = new int[32];
        this.O000OOo0 = iArr;
        this.O000OOo = 0;
        this.O000OOo = 0 + 1;
        iArr[0] = 6;
        Objects.requireNonNull(reader, "in == null");
        this.O000O0OO = reader;
    }

    private void O000000o(int i) {
        int i2 = this.O000OOo;
        int[] iArr = this.O000OOo0;
        if (i2 == iArr.length) {
            int[] iArr2 = new int[i2 * 2];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            this.O000OOo0 = iArr2;
        }
        int[] iArr3 = this.O000OOo0;
        int i3 = this.O000OOo;
        this.O000OOo = i3 + 1;
        iArr3[i3] = i;
    }

    private boolean O000000o(char c) throws IOException {
        if (c == '\t' || c == '\n' || c == '\f' || c == '\r' || c == ' ') {
            return false;
        }
        if (c != '#') {
            if (c == ',') {
                return false;
            }
            if (c != '/' && c != '=') {
                if (c == '{' || c == '}' || c == ':') {
                    return false;
                }
                if (c != ';') {
                    switch (c) {
                        case '[':
                        case ']':
                            return false;
                        case '\\':
                            break;
                        default:
                            return true;
                    }
                }
            }
        }
        O0000oo();
        return false;
    }

    private boolean O000000o(String str) throws IOException {
        while (true) {
            if (this.O000O0o0 + str.length() > this.O000O0o && !O00000Oo(str.length())) {
                return false;
            }
            char[] cArr = this.O00oOoOo;
            int i = this.O000O0o0;
            if (cArr[i] != '\n') {
                for (int i2 = 0; i2 < str.length(); i2++) {
                    if (this.O00oOoOo[this.O000O0o0 + i2] != str.charAt(i2)) {
                        break;
                    }
                }
                return true;
            }
            this.O000O0oO++;
            this.O000O0oo = i + 1;
            this.O000O0o0++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0063, code lost:
        if (r1 != '/') goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0065, code lost:
        r7.O000O0o0 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0068, code lost:
        if (r4 != r2) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x006a, code lost:
        r7.O000O0o0 = r4 - 1;
        r2 = O00000Oo(2);
        r7.O000O0o0++;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0077, code lost:
        if (r2 != false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0079, code lost:
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x007a, code lost:
        O0000oo();
        r2 = r7.O000O0o0;
        r3 = r0[r2];
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0083, code lost:
        if (r3 == '*') goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0085, code lost:
        if (r3 == '/') goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0087, code lost:
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0088, code lost:
        r7.O000O0o0 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0091, code lost:
        r7.O000O0o0 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x009b, code lost:
        if (O000000o("*\/") == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00a8, code lost:
        throw O00000Oo("Unterminated comment");
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00a9, code lost:
        r7.O000O0o0 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ad, code lost:
        if (r1 != '#') goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00af, code lost:
        O0000oo();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b3, code lost:
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int O00000Oo(boolean r8) throws java.io.IOException {
        /*
            r7 = this;
            char[] r0 = r7.O00oOoOo
        L2:
            int r1 = r7.O000O0o0
        L4:
            int r2 = r7.O000O0o
        L6:
            r3 = 1
            if (r1 != r2) goto L44
            r7.O000O0o0 = r1
            boolean r1 = r7.O00000Oo(r3)
            if (r1 != 0) goto L40
            if (r8 != 0) goto L15
            r8 = -1
            return r8
        L15:
            java.io.EOFException r8 = new java.io.EOFException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "End of input at line "
            java.lang.StringBuilder r0 = r0.append(r1)
            int r1 = r7.O0000oOo()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = " column "
            java.lang.StringBuilder r0 = r0.append(r1)
            int r1 = r7.O0000oo0()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r8.<init>(r0)
            throw r8
        L40:
            int r1 = r7.O000O0o0
            int r2 = r7.O000O0o
        L44:
            int r4 = r1 + 1
            char r1 = r0[r1]
            r5 = 10
            if (r1 != r5) goto L54
            int r1 = r7.O000O0oO
            int r1 = r1 + r3
            r7.O000O0oO = r1
            r7.O000O0oo = r4
            goto Lb4
        L54:
            r5 = 32
            if (r1 == r5) goto Lb4
            r5 = 13
            if (r1 == r5) goto Lb4
            r5 = 9
            if (r1 != r5) goto L61
            goto Lb4
        L61:
            r5 = 47
            if (r1 != r5) goto La9
            r7.O000O0o0 = r4
            r6 = 2
            if (r4 != r2) goto L7a
            int r4 = r4 + (-1)
            r7.O000O0o0 = r4
            boolean r2 = r7.O00000Oo(r6)
            int r4 = r7.O000O0o0
            int r4 = r4 + r3
            r7.O000O0o0 = r4
            if (r2 != 0) goto L7a
            return r1
        L7a:
            r7.O0000oo()
            int r2 = r7.O000O0o0
            char r3 = r0[r2]
            r4 = 42
            if (r3 == r4) goto L91
            if (r3 == r5) goto L88
            return r1
        L88:
            int r2 = r2 + 1
            r7.O000O0o0 = r2
        L8c:
            r7.O0000ooO()
            goto L2
        L91:
            int r2 = r2 + 1
            r7.O000O0o0 = r2
        */
        //  java.lang.String r1 = "*/"
        /*
            boolean r1 = r7.O000000o(r1)
            if (r1 == 0) goto La2
            int r1 = r7.O000O0o0
            int r1 = r1 + r6
            goto L4
        La2:
            java.lang.String r8 = "Unterminated comment"
            java.io.IOException r8 = r7.O00000Oo(r8)
            throw r8
        La9:
            r2 = 35
            r7.O000O0o0 = r4
            if (r1 != r2) goto Lb3
            r7.O0000oo()
            goto L8c
        Lb3:
            return r1
        Lb4:
            r1 = r4
            goto L6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.O000000o.O000000o.O00000o.O000000o.O00000Oo(boolean):int");
    }

    private IOException O00000Oo(String str) throws IOException {
        throw new O0000O0o(str + " at line " + O0000oOo() + " column " + O0000oo0());
    }

    private String O00000Oo(char c) throws IOException {
        char[] cArr = this.O00oOoOo;
        StringBuilder sb = new StringBuilder();
        while (true) {
            int i = this.O000O0o0;
            int i2 = this.O000O0o;
            while (true) {
                if (i < i2) {
                    int i3 = i + 1;
                    char c2 = cArr[i];
                    if (c2 == c) {
                        this.O000O0o0 = i3;
                        sb.append(cArr, i, (i3 - i) - 1);
                        return sb.toString();
                    } else if (c2 == '\\') {
                        this.O000O0o0 = i3;
                        sb.append(cArr, i, (i3 - i) - 1);
                        sb.append(O0000ooo());
                        break;
                    } else {
                        if (c2 == '\n') {
                            this.O000O0oO++;
                            this.O000O0oo = i3;
                        }
                        i = i3;
                    }
                } else {
                    sb.append(cArr, i, i - i);
                    this.O000O0o0 = i;
                    if (!O00000Oo(1)) {
                        throw O00000Oo("Unterminated string");
                    }
                }
            }
        }
    }

    private boolean O00000Oo(int i) throws IOException {
        int i2;
        int i3;
        char[] cArr = this.O00oOoOo;
        int i4 = this.O000O0oo;
        int i5 = this.O000O0o0;
        this.O000O0oo = i4 - i5;
        int i6 = this.O000O0o;
        if (i6 != i5) {
            int i7 = i6 - i5;
            this.O000O0o = i7;
            System.arraycopy(cArr, i5, cArr, 0, i7);
        } else {
            this.O000O0o = 0;
        }
        this.O000O0o0 = 0;
        do {
            Reader reader = this.O000O0OO;
            int i8 = this.O000O0o;
            int read = reader.read(cArr, i8, cArr.length - i8);
            if (read == -1) {
                return false;
            }
            i2 = this.O000O0o + read;
            this.O000O0o = i2;
            if (this.O000O0oO == 0 && (i3 = this.O000O0oo) == 0 && i2 > 0 && cArr[0] == 65279) {
                this.O000O0o0++;
                this.O000O0oo = i3 + 1;
                i++;
                continue;
            }
        } while (i2 < i);
        return true;
    }

    private void O00000o0(char c) throws IOException {
        char[] cArr = this.O00oOoOo;
        while (true) {
            int i = this.O000O0o0;
            int i2 = this.O000O0o;
            while (true) {
                if (i < i2) {
                    int i3 = i + 1;
                    char c2 = cArr[i];
                    if (c2 == c) {
                        this.O000O0o0 = i3;
                        return;
                    } else if (c2 == '\\') {
                        this.O000O0o0 = i3;
                        O0000ooo();
                        break;
                    } else {
                        if (c2 == '\n') {
                            this.O000O0oO++;
                            this.O000O0oo = i3;
                        }
                        i = i3;
                    }
                } else {
                    this.O000O0o0 = i;
                    if (!O00000Oo(1)) {
                        throw O00000Oo("Unterminated string");
                    }
                }
            }
        }
    }

    private int O0000o() throws IOException {
        int i;
        String str;
        String str2;
        char c = this.O00oOoOo[this.O000O0o0];
        if (c == 't' || c == 'T') {
            i = 5;
            str = OOBEEvent.STRING_TRUE;
            str2 = "TRUE";
        } else if (c == 'f' || c == 'F') {
            i = 6;
            str = OOBEEvent.STRING_FALSE;
            str2 = "FALSE";
        } else if (c != 'n' && c != 'N') {
            return 0;
        } else {
            i = 7;
            str = "null";
            str2 = "NULL";
        }
        int length = str.length();
        for (int i2 = 1; i2 < length; i2++) {
            if (this.O000O0o0 + i2 >= this.O000O0o && !O00000Oo(i2 + 1)) {
                return 0;
            }
            char c2 = this.O00oOoOo[this.O000O0o0 + i2];
            if (c2 != str.charAt(i2) && c2 != str2.charAt(i2)) {
                return 0;
            }
        }
        if ((this.O000O0o0 + length < this.O000O0o || O00000Oo(length + 1)) && O000000o(this.O00oOoOo[this.O000O0o0 + length])) {
            return 0;
        }
        this.O000O0o0 += length;
        this.O000OO00 = i;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int O0000o0O() throws IOException {
        int i;
        int O00000Oo2;
        int[] iArr = this.O000OOo0;
        int i2 = this.O000OOo;
        int i3 = iArr[i2 - 1];
        if (i3 == 1) {
            iArr[i2 - 1] = 2;
        } else if (i3 != 2) {
            if (i3 == 3 || i3 == 5) {
                iArr[i2 - 1] = 4;
                if (i3 == 5 && (O00000Oo2 = O00000Oo(true)) != 44) {
                    if (O00000Oo2 != 59) {
                        if (O00000Oo2 == 125) {
                            this.O000OO00 = 2;
                            return 2;
                        }
                        throw O00000Oo("Unterminated object");
                    }
                    O0000oo();
                }
                int O00000Oo3 = O00000Oo(true);
                if (O00000Oo3 == 34) {
                    i = 13;
                } else if (O00000Oo3 == 39) {
                    O0000oo();
                    i = 12;
                } else if (O00000Oo3 == 125) {
                    if (i3 != 5) {
                        this.O000OO00 = 2;
                        return 2;
                    }
                    throw O00000Oo("Expected name");
                } else {
                    O0000oo();
                    this.O000O0o0--;
                    if (!O000000o((char) O00000Oo3)) {
                        throw O00000Oo("Expected name");
                    }
                    i = 14;
                }
            } else if (i3 == 4) {
                iArr[i2 - 1] = 5;
                int O00000Oo4 = O00000Oo(true);
                if (O00000Oo4 != 58) {
                    if (O00000Oo4 != 61) {
                        throw O00000Oo("Expected ':'");
                    }
                    O0000oo();
                    if (this.O000O0o0 < this.O000O0o || O00000Oo(1)) {
                        char[] cArr = this.O00oOoOo;
                        int i4 = this.O000O0o0;
                        if (cArr[i4] == '>') {
                            this.O000O0o0 = i4 + 1;
                        }
                    }
                }
            } else if (i3 == 6) {
                if (this.O000O0Oo) {
                    O00oOooO();
                }
                this.O000OOo0[this.O000OOo - 1] = 7;
            } else if (i3 == 7) {
                if (O00000Oo(false) == -1) {
                    i = 17;
                } else {
                    O0000oo();
                    this.O000O0o0--;
                }
            } else if (i3 == 8) {
                throw new IllegalStateException("JsonReader is closed");
            }
            this.O000OO00 = i;
            return i;
        } else {
            int O00000Oo5 = O00000Oo(true);
            if (O00000Oo5 != 44) {
                if (O00000Oo5 != 59) {
                    if (O00000Oo5 == 93) {
                        this.O000OO00 = 4;
                        return 4;
                    }
                    throw O00000Oo("Unterminated array");
                }
                O0000oo();
            }
        }
        int O00000Oo6 = O00000Oo(true);
        if (O00000Oo6 != 34) {
            if (O00000Oo6 == 39) {
                O0000oo();
                this.O000OO00 = 8;
                return 8;
            }
            if (O00000Oo6 != 44 && O00000Oo6 != 59) {
                if (O00000Oo6 == 91) {
                    this.O000OO00 = 3;
                    return 3;
                } else if (O00000Oo6 != 93) {
                    if (O00000Oo6 == 123) {
                        this.O000OO00 = 1;
                        return 1;
                    }
                    this.O000O0o0--;
                    if (this.O000OOo == 1) {
                        O0000oo();
                    }
                    int O0000o2 = O0000o();
                    if (O0000o2 != 0) {
                        return O0000o2;
                    }
                    int O0000oO02 = O0000oO0();
                    if (O0000oO02 != 0) {
                        return O0000oO02;
                    }
                    if (!O000000o(this.O00oOoOo[this.O000O0o0])) {
                        throw O00000Oo("Expected value");
                    }
                    O0000oo();
                    i = 10;
                } else if (i3 == 1) {
                    this.O000OO00 = 4;
                    return 4;
                }
            }
            if (i3 == 1 || i3 == 2) {
                O0000oo();
                this.O000O0o0--;
                this.O000OO00 = 7;
                return 7;
            }
            throw O00000Oo("Unexpected value");
        }
        if (this.O000OOo == 1) {
            O0000oo();
        }
        i = 9;
        this.O000OO00 = i;
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x004a, code lost:
        O0000oo();
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String O0000oO() throws java.io.IOException {
        /*
            r6 = this;
            r0 = 0
            r1 = 0
        L2:
            r2 = r0
        L3:
            int r3 = r6.O000O0o0
            int r4 = r3 + r2
            int r5 = r6.O000O0o
            if (r4 >= r5) goto L4e
            char[] r4 = r6.O00oOoOo
            int r3 = r3 + r2
            char r3 = r4[r3]
            r4 = 9
            if (r3 == r4) goto L5c
            r4 = 10
            if (r3 == r4) goto L5c
            r4 = 12
            if (r3 == r4) goto L5c
            r4 = 13
            if (r3 == r4) goto L5c
            r4 = 32
            if (r3 == r4) goto L5c
            r4 = 35
            if (r3 == r4) goto L4a
            r4 = 44
            if (r3 == r4) goto L5c
            r4 = 47
            if (r3 == r4) goto L4a
            r4 = 61
            if (r3 == r4) goto L4a
            r4 = 123(0x7b, float:1.72E-43)
            if (r3 == r4) goto L5c
            r4 = 125(0x7d, float:1.75E-43)
            if (r3 == r4) goto L5c
            r4 = 58
            if (r3 == r4) goto L5c
            r4 = 59
            if (r3 == r4) goto L4a
            switch(r3) {
                case 91: goto L5c;
                case 92: goto L4a;
                case 93: goto L5c;
                default: goto L47;
            }
        L47:
            int r2 = r2 + 1
            goto L3
        L4a:
            r6.O0000oo()
            goto L5c
        L4e:
            char[] r3 = r6.O00oOoOo
            int r3 = r3.length
            if (r2 >= r3) goto L5e
            int r3 = r2 + 1
            boolean r3 = r6.O00000Oo(r3)
            if (r3 == 0) goto L5c
            goto L3
        L5c:
            r0 = r2
            goto L78
        L5e:
            if (r1 != 0) goto L65
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
        L65:
            char[] r3 = r6.O00oOoOo
            int r4 = r6.O000O0o0
            r1.append(r3, r4, r2)
            int r3 = r6.O000O0o0
            int r3 = r3 + r2
            r6.O000O0o0 = r3
            r2 = 1
            boolean r2 = r6.O00000Oo(r2)
            if (r2 != 0) goto L2
        L78:
            if (r1 != 0) goto L84
            java.lang.String r1 = new java.lang.String
            char[] r2 = r6.O00oOoOo
            int r3 = r6.O000O0o0
            r1.<init>(r2, r3, r0)
            goto L8f
        L84:
            char[] r2 = r6.O00oOoOo
            int r3 = r6.O000O0o0
            r1.append(r2, r3, r0)
            java.lang.String r1 = r1.toString()
        L8f:
            int r2 = r6.O000O0o0
            int r2 = r2 + r0
            r6.O000O0o0 = r2
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.O000000o.O000000o.O00000o.O000000o.O0000oO():java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0093, code lost:
        if (O000000o(r14) != false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0095, code lost:
        if (r9 != 2) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0097, code lost:
        if (r10 == false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x009d, code lost:
        if (r11 != Long.MIN_VALUE) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x009f, code lost:
        if (r13 == false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00a1, code lost:
        if (r13 == false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00a4, code lost:
        r11 = -r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00a5, code lost:
        r18.O000OO0o = r11;
        r18.O000O0o0 += r8;
        r1 = 15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00ae, code lost:
        r18.O000OO00 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00b0, code lost:
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00b1, code lost:
        if (r9 == 2) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00b4, code lost:
        if (r9 == 4) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00b7, code lost:
        if (r9 != 7) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00ba, code lost:
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00bc, code lost:
        r18.O000OO = r8;
        r1 = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00c1, code lost:
        return 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int O0000oO0() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.O000000o.O000000o.O00000o.O000000o.O0000oO0():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0048, code lost:
        O0000oo();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void O0000oOO() throws java.io.IOException {
        /*
            r4 = this;
        L0:
            r0 = 0
        L1:
            int r1 = r4.O000O0o0
            int r2 = r1 + r0
            int r3 = r4.O000O0o
            if (r2 >= r3) goto L51
            char[] r2 = r4.O00oOoOo
            int r1 = r1 + r0
            char r1 = r2[r1]
            r2 = 9
            if (r1 == r2) goto L4b
            r2 = 10
            if (r1 == r2) goto L4b
            r2 = 12
            if (r1 == r2) goto L4b
            r2 = 13
            if (r1 == r2) goto L4b
            r2 = 32
            if (r1 == r2) goto L4b
            r2 = 35
            if (r1 == r2) goto L48
            r2 = 44
            if (r1 == r2) goto L4b
            r2 = 47
            if (r1 == r2) goto L48
            r2 = 61
            if (r1 == r2) goto L48
            r2 = 123(0x7b, float:1.72E-43)
            if (r1 == r2) goto L4b
            r2 = 125(0x7d, float:1.75E-43)
            if (r1 == r2) goto L4b
            r2 = 58
            if (r1 == r2) goto L4b
            r2 = 59
            if (r1 == r2) goto L48
            switch(r1) {
                case 91: goto L4b;
                case 92: goto L48;
                case 93: goto L4b;
                default: goto L45;
            }
        L45:
            int r0 = r0 + 1
            goto L1
        L48:
            r4.O0000oo()
        L4b:
            int r1 = r4.O000O0o0
            int r1 = r1 + r0
            r4.O000O0o0 = r1
            return
        L51:
            int r1 = r1 + r0
            r4.O000O0o0 = r1
            r0 = 1
            boolean r0 = r4.O00000Oo(r0)
            if (r0 != 0) goto L0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.O000000o.O000000o.O00000o.O000000o.O0000oOO():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int O0000oOo() {
        return this.O000O0oO + 1;
    }

    private void O0000oo() throws IOException {
        if (!this.O000O0Oo) {
            throw O00000Oo("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int O0000oo0() {
        return (this.O000O0o0 - this.O000O0oo) + 1;
    }

    private void O0000ooO() throws IOException {
        char c;
        do {
            if (this.O000O0o0 >= this.O000O0o && !O00000Oo(1)) {
                return;
            }
            char[] cArr = this.O00oOoOo;
            int i = this.O000O0o0;
            int i2 = i + 1;
            this.O000O0o0 = i2;
            c = cArr[i];
            if (c == '\n') {
                this.O000O0oO++;
                this.O000O0oo = i2;
                return;
            }
        } while (c != '\r');
    }

    private char O0000ooo() throws IOException {
        int i;
        int i2;
        if (this.O000O0o0 != this.O000O0o || O00000Oo(1)) {
            char[] cArr = this.O00oOoOo;
            int i3 = this.O000O0o0;
            int i4 = i3 + 1;
            this.O000O0o0 = i4;
            char c = cArr[i3];
            if (c == '\n') {
                this.O000O0oO++;
                this.O000O0oo = i4;
            } else if (c == 'b') {
                return '\b';
            } else {
                if (c == 'f') {
                    return '\f';
                }
                if (c == 'n') {
                    return '\n';
                }
                if (c == 'r') {
                    return '\r';
                }
                if (c == 't') {
                    return '\t';
                }
                if (c == 'u') {
                    if (i4 + 4 <= this.O000O0o || O00000Oo(4)) {
                        char c2 = 0;
                        int i5 = this.O000O0o0;
                        int i6 = i5 + 4;
                        while (i5 < i6) {
                            char c3 = this.O00oOoOo[i5];
                            char c4 = (char) (c2 << 4);
                            if (c3 < '0' || c3 > '9') {
                                if (c3 >= 'a' && c3 <= 'f') {
                                    i = c3 - 'a';
                                } else if (c3 < 'A' || c3 > 'F') {
                                    throw new NumberFormatException("\\u" + new String(this.O00oOoOo, this.O000O0o0, 4));
                                } else {
                                    i = c3 - 'A';
                                }
                                i2 = i + 10;
                            } else {
                                i2 = c3 - '0';
                            }
                            c2 = (char) (c4 + i2);
                            i5++;
                        }
                        this.O000O0o0 += 4;
                        return c2;
                    }
                    throw O00000Oo("Unterminated escape sequence");
                }
            }
            return c;
        }
        throw O00000Oo("Unterminated escape sequence");
    }

    private void O00oOooO() throws IOException {
        O00000Oo(true);
        int i = this.O000O0o0 - 1;
        this.O000O0o0 = i;
        char[] cArr = O000000o;
        if (i + cArr.length > this.O000O0o && !O00000Oo(cArr.length)) {
            return;
        }
        int i2 = 0;
        while (true) {
            char[] cArr2 = O000000o;
            if (i2 >= cArr2.length) {
                this.O000O0o0 += cArr2.length;
                return;
            } else if (this.O00oOoOo[this.O000O0o0 + i2] != cArr2[i2]) {
                return;
            } else {
                i2++;
            }
        }
    }

    public void O000000o() throws IOException {
        int i = this.O000OO00;
        if (i == 0) {
            i = O0000o0O();
        }
        if (i != 3) {
            throw new IllegalStateException("Expected BEGIN_ARRAY but was " + O00000oo() + " at line " + O0000oOo() + " column " + O0000oo0());
        }
        O000000o(1);
        this.O000OO00 = 0;
    }

    public final void O000000o(boolean z) {
        this.O000O0Oo = z;
    }

    public void O00000Oo() throws IOException {
        int i = this.O000OO00;
        if (i == 0) {
            i = O0000o0O();
        }
        if (i != 4) {
            throw new IllegalStateException("Expected END_ARRAY but was " + O00000oo() + " at line " + O0000oOo() + " column " + O0000oo0());
        }
        this.O000OOo--;
        this.O000OO00 = 0;
    }

    public void O00000o() throws IOException {
        int i = this.O000OO00;
        if (i == 0) {
            i = O0000o0O();
        }
        if (i != 2) {
            throw new IllegalStateException("Expected END_OBJECT but was " + O00000oo() + " at line " + O0000oOo() + " column " + O0000oo0());
        }
        this.O000OOo--;
        this.O000OO00 = 0;
    }

    public void O00000o0() throws IOException {
        int i = this.O000OO00;
        if (i == 0) {
            i = O0000o0O();
        }
        if (i != 1) {
            throw new IllegalStateException("Expected BEGIN_OBJECT but was " + O00000oo() + " at line " + O0000oOo() + " column " + O0000oo0());
        }
        O000000o(3);
        this.O000OO00 = 0;
    }

    public boolean O00000oO() throws IOException {
        int i = this.O000OO00;
        if (i == 0) {
            i = O0000o0O();
        }
        return (i == 2 || i == 4) ? false : true;
    }

    public O00000o0 O00000oo() throws IOException {
        int i = this.O000OO00;
        if (i == 0) {
            i = O0000o0O();
        }
        switch (i) {
            case 1:
                return O00000o0.BEGIN_OBJECT;
            case 2:
                return O00000o0.END_OBJECT;
            case 3:
                return O00000o0.BEGIN_ARRAY;
            case 4:
                return O00000o0.END_ARRAY;
            case 5:
            case 6:
                return O00000o0.BOOLEAN;
            case 7:
                return O00000o0.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return O00000o0.STRING;
            case 12:
            case 13:
            case 14:
                return O00000o0.NAME;
            case 15:
            case 16:
                return O00000o0.NUMBER;
            case 17:
                return O00000o0.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    public String O0000O0o() throws IOException {
        char c;
        String O00000Oo2;
        int i = this.O000OO00;
        if (i == 0) {
            i = O0000o0O();
        }
        if (i == 14) {
            O00000Oo2 = O0000oO();
        } else {
            if (i == 12) {
                c = '\'';
            } else if (i != 13) {
                throw new IllegalStateException("Expected a name but was " + O00000oo() + " at line " + O0000oOo() + " column " + O0000oo0());
            } else {
                c = '\"';
            }
            O00000Oo2 = O00000Oo(c);
        }
        this.O000OO00 = 0;
        return O00000Oo2;
    }

    public String O0000OOo() throws IOException {
        String str;
        char c;
        int i = this.O000OO00;
        if (i == 0) {
            i = O0000o0O();
        }
        if (i == 10) {
            str = O0000oO();
        } else {
            if (i == 8) {
                c = '\'';
            } else if (i == 9) {
                c = '\"';
            } else if (i == 11) {
                str = this.O000OOOo;
                this.O000OOOo = null;
            } else if (i == 15) {
                str = Long.toString(this.O000OO0o);
            } else if (i != 16) {
                throw new IllegalStateException("Expected a string but was " + O00000oo() + " at line " + O0000oOo() + " column " + O0000oo0());
            } else {
                str = new String(this.O00oOoOo, this.O000O0o0, this.O000OO);
                this.O000O0o0 += this.O000OO;
            }
            str = O00000Oo(c);
        }
        this.O000OO00 = 0;
        return str;
    }

    public void O0000Oo() throws IOException {
        int i = this.O000OO00;
        if (i == 0) {
            i = O0000o0O();
        }
        if (i != 7) {
            throw new IllegalStateException("Expected null but was " + O00000oo() + " at line " + O0000oOo() + " column " + O0000oo0());
        }
        this.O000OO00 = 0;
    }

    public boolean O0000Oo0() throws IOException {
        int i = this.O000OO00;
        if (i == 0) {
            i = O0000o0O();
        }
        if (i == 5) {
            this.O000OO00 = 0;
            return true;
        } else if (i == 6) {
            this.O000OO00 = 0;
            return false;
        } else {
            throw new IllegalStateException("Expected a boolean but was " + O00000oo() + " at line " + O0000oOo() + " column " + O0000oo0());
        }
    }

    public double O0000OoO() throws IOException {
        String O00000Oo2;
        int i = this.O000OO00;
        if (i == 0) {
            i = O0000o0O();
        }
        if (i == 15) {
            this.O000OO00 = 0;
            return this.O000OO0o;
        }
        if (i == 16) {
            this.O000OOOo = new String(this.O00oOoOo, this.O000O0o0, this.O000OO);
            this.O000O0o0 += this.O000OO;
        } else {
            if (i == 8 || i == 9) {
                O00000Oo2 = O00000Oo(i == 8 ? '\'' : '\"');
            } else if (i == 10) {
                O00000Oo2 = O0000oO();
            } else if (i != 11) {
                throw new IllegalStateException("Expected a double but was " + O00000oo() + " at line " + O0000oOo() + " column " + O0000oo0());
            }
            this.O000OOOo = O00000Oo2;
        }
        this.O000OO00 = 11;
        double parseDouble = Double.parseDouble(this.O000OOOo);
        if (this.O000O0Oo || !(Double.isNaN(parseDouble) || Double.isInfinite(parseDouble))) {
            this.O000OOOo = null;
            this.O000OO00 = 0;
            return parseDouble;
        }
        throw new O0000O0o("JSON forbids NaN and infinities: " + parseDouble + " at line " + O0000oOo() + " column " + O0000oo0());
    }

    public long O0000Ooo() throws IOException {
        int i = this.O000OO00;
        if (i == 0) {
            i = O0000o0O();
        }
        if (i == 15) {
            this.O000OO00 = 0;
            return this.O000OO0o;
        }
        if (i == 16) {
            this.O000OOOo = new String(this.O00oOoOo, this.O000O0o0, this.O000OO);
            this.O000O0o0 += this.O000OO;
        } else if (i != 8 && i != 9) {
            throw new IllegalStateException("Expected a long but was " + O00000oo() + " at line " + O0000oOo() + " column " + O0000oo0());
        } else {
            String O00000Oo2 = O00000Oo(i == 8 ? '\'' : '\"');
            this.O000OOOo = O00000Oo2;
            try {
                long parseLong = Long.parseLong(O00000Oo2);
                this.O000OO00 = 0;
                return parseLong;
            } catch (NumberFormatException unused) {
            }
        }
        this.O000OO00 = 11;
        double parseDouble = Double.parseDouble(this.O000OOOo);
        long j = (long) parseDouble;
        if (j == parseDouble) {
            this.O000OOOo = null;
            this.O000OO00 = 0;
            return j;
        }
        throw new NumberFormatException("Expected a long but was " + this.O000OOOo + " at line " + O0000oOo() + " column " + O0000oo0());
    }

    public void O0000o0() throws IOException {
        char c;
        int i = 0;
        do {
            int i2 = this.O000OO00;
            if (i2 == 0) {
                i2 = O0000o0O();
            }
            if (i2 == 3) {
                O000000o(1);
            } else if (i2 == 1) {
                O000000o(3);
            } else {
                if (i2 == 4 || i2 == 2) {
                    this.O000OOo--;
                    i--;
                } else if (i2 == 14 || i2 == 10) {
                    O0000oOO();
                } else {
                    if (i2 == 8 || i2 == 12) {
                        c = '\'';
                    } else if (i2 == 9 || i2 == 13) {
                        c = '\"';
                    } else if (i2 == 16) {
                        this.O000O0o0 += this.O000OO;
                    }
                    O00000o0(c);
                }
                this.O000OO00 = 0;
            }
            i++;
            this.O000OO00 = 0;
        } while (i != 0);
    }

    public int O0000o00() throws IOException {
        int i = this.O000OO00;
        if (i == 0) {
            i = O0000o0O();
        }
        if (i == 15) {
            long j = this.O000OO0o;
            int i2 = (int) j;
            if (j == i2) {
                this.O000OO00 = 0;
                return i2;
            }
            throw new NumberFormatException("Expected an int but was " + this.O000OO0o + " at line " + O0000oOo() + " column " + O0000oo0());
        }
        if (i == 16) {
            this.O000OOOo = new String(this.O00oOoOo, this.O000O0o0, this.O000OO);
            this.O000O0o0 += this.O000OO;
        } else if (i != 8 && i != 9) {
            throw new IllegalStateException("Expected an int but was " + O00000oo() + " at line " + O0000oOo() + " column " + O0000oo0());
        } else {
            String O00000Oo2 = O00000Oo(i == 8 ? '\'' : '\"');
            this.O000OOOo = O00000Oo2;
            try {
                int parseInt = Integer.parseInt(O00000Oo2);
                this.O000OO00 = 0;
                return parseInt;
            } catch (NumberFormatException unused) {
            }
        }
        this.O000OO00 = 11;
        double parseDouble = Double.parseDouble(this.O000OOOo);
        int i3 = (int) parseDouble;
        if (i3 == parseDouble) {
            this.O000OOOo = null;
            this.O000OO00 = 0;
            return i3;
        }
        throw new NumberFormatException("Expected an int but was " + this.O000OOOo + " at line " + O0000oOo() + " column " + O0000oo0());
    }

    public final boolean O0000o0o() {
        return this.O000O0Oo;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.O000OO00 = 0;
        this.O000OOo0[0] = 8;
        this.O000OOo = 1;
        this.O000O0OO.close();
    }

    public String toString() {
        return getClass().getSimpleName() + " at line " + O0000oOo() + " column " + O0000oo0();
    }
}
