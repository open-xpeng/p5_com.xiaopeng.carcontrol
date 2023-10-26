package com.O000000o.O000000o.O00000o;

import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

/* compiled from: JsonWriter.java */
/* loaded from: classes.dex */
public class O00000o implements Closeable, Flushable {
    private static final String[] O000000o = new String[128];
    private static final String[] O00000Oo;
    private final Writer O00000o0;
    private String O00000oo;
    private String O0000O0o;
    private boolean O0000OOo;
    private String O0000Oo;
    private boolean O0000Oo0;
    private boolean O0000OoO;
    private int[] O00000o = new int[32];
    private int O00000oO = 0;

    static {
        for (int i = 0; i <= 31; i++) {
            O000000o[i] = String.format("\\u%04x", Integer.valueOf(i));
        }
        String[] strArr = O000000o;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
        String[] strArr2 = (String[]) strArr.clone();
        O00000Oo = strArr2;
        strArr2[60] = "\\u003c";
        strArr2[62] = "\\u003e";
        strArr2[38] = "\\u0026";
        strArr2[61] = "\\u003d";
        strArr2[39] = "\\u0027";
    }

    public O00000o(Writer writer) {
        O000000o(6);
        this.O0000O0o = QuickSettingConstants.JOINER;
        this.O0000OoO = true;
        Objects.requireNonNull(writer, "out == null");
        this.O00000o0 = writer;
    }

    private int O000000o() {
        int i = this.O00000oO;
        if (i != 0) {
            return this.O00000o[i - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    private O00000o O000000o(int i, int i2, String str) throws IOException {
        int O000000o2 = O000000o();
        if (O000000o2 == i2 || O000000o2 == i) {
            if (this.O0000Oo == null) {
                this.O00000oO--;
                if (O000000o2 == i2) {
                    O0000OoO();
                }
                this.O00000o0.write(str);
                return this;
            }
            throw new IllegalStateException("Dangling name: " + this.O0000Oo);
        }
        throw new IllegalStateException("Nesting problem.");
    }

    private O00000o O000000o(int i, String str) throws IOException {
        O00000oO(true);
        O000000o(i);
        this.O00000o0.write(str);
        return this;
    }

    private void O000000o(int i) {
        int i2 = this.O00000oO;
        int[] iArr = this.O00000o;
        if (i2 == iArr.length) {
            int[] iArr2 = new int[i2 * 2];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            this.O00000o = iArr2;
        }
        int[] iArr3 = this.O00000o;
        int i3 = this.O00000oO;
        this.O00000oO = i3 + 1;
        iArr3[i3] = i;
    }

    private void O00000Oo(int i) {
        this.O00000o[this.O00000oO - 1] = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void O00000o(java.lang.String r9) throws java.io.IOException {
        /*
            r8 = this;
            boolean r0 = r8.O0000Oo0
            if (r0 == 0) goto L7
            java.lang.String[] r0 = com.O000000o.O000000o.O00000o.O00000o.O00000Oo
            goto L9
        L7:
            java.lang.String[] r0 = com.O000000o.O000000o.O00000o.O00000o.O000000o
        L9:
            java.io.Writer r1 = r8.O00000o0
            java.lang.String r2 = "\""
            r1.write(r2)
            int r1 = r9.length()
            r3 = 0
            r4 = r3
        L16:
            if (r3 >= r1) goto L45
            char r5 = r9.charAt(r3)
            r6 = 128(0x80, float:1.8E-43)
            if (r5 >= r6) goto L25
            r5 = r0[r5]
            if (r5 != 0) goto L32
            goto L42
        L25:
            r6 = 8232(0x2028, float:1.1535E-41)
            if (r5 != r6) goto L2c
            java.lang.String r5 = "\\u2028"
            goto L32
        L2c:
            r6 = 8233(0x2029, float:1.1537E-41)
            if (r5 != r6) goto L42
            java.lang.String r5 = "\\u2029"
        L32:
            if (r4 >= r3) goto L3b
            java.io.Writer r6 = r8.O00000o0
            int r7 = r3 - r4
            r6.write(r9, r4, r7)
        L3b:
            java.io.Writer r4 = r8.O00000o0
            r4.write(r5)
            int r4 = r3 + 1
        L42:
            int r3 = r3 + 1
            goto L16
        L45:
            if (r4 >= r1) goto L4d
            java.io.Writer r0 = r8.O00000o0
            int r1 = r1 - r4
            r0.write(r9, r4, r1)
        L4d:
            java.io.Writer r9 = r8.O00000o0
            r9.write(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.O000000o.O000000o.O00000o.O00000o.O00000o(java.lang.String):void");
    }

    private void O00000oO(boolean z) throws IOException {
        int O000000o2 = O000000o();
        if (O000000o2 == 1) {
            O00000Oo(2);
        } else if (O000000o2 != 2) {
            if (O000000o2 == 4) {
                this.O00000o0.append((CharSequence) this.O0000O0o);
                O00000Oo(5);
                return;
            }
            if (O000000o2 != 6) {
                if (O000000o2 != 7) {
                    throw new IllegalStateException("Nesting problem.");
                }
                if (!this.O0000OOo) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
            }
            if (!this.O0000OOo && !z) {
                throw new IllegalStateException("JSON must start with an array or an object.");
            }
            O00000Oo(7);
            return;
        } else {
            this.O00000o0.append(',');
        }
        O0000OoO();
    }

    private void O0000Oo() throws IOException {
        if (this.O0000Oo != null) {
            O0000Ooo();
            O00000o(this.O0000Oo);
            this.O0000Oo = null;
        }
    }

    private void O0000OoO() throws IOException {
        if (this.O00000oo == null) {
            return;
        }
        this.O00000o0.write("\n");
        int i = this.O00000oO;
        for (int i2 = 1; i2 < i; i2++) {
            this.O00000o0.write(this.O00000oo);
        }
    }

    private void O0000Ooo() throws IOException {
        int O000000o2 = O000000o();
        if (O000000o2 == 5) {
            this.O00000o0.write(44);
        } else if (O000000o2 != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        O0000OoO();
        O00000Oo(4);
    }

    public O00000o O000000o(double d) throws IOException {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + d);
        }
        O0000Oo();
        O00000oO(false);
        this.O00000o0.append((CharSequence) Double.toString(d));
        return this;
    }

    public O00000o O000000o(long j) throws IOException {
        O0000Oo();
        O00000oO(false);
        this.O00000o0.write(Long.toString(j));
        return this;
    }

    public O00000o O000000o(Number number) throws IOException {
        if (number == null) {
            return O00000oo();
        }
        O0000Oo();
        String obj = number.toString();
        if (this.O0000OOo || !(obj.equals("-Infinity") || obj.equals("Infinity") || obj.equals("NaN"))) {
            O00000oO(false);
            this.O00000o0.append((CharSequence) obj);
            return this;
        }
        throw new IllegalArgumentException("Numeric values must be finite, but was " + number);
    }

    public O00000o O000000o(String str) throws IOException {
        Objects.requireNonNull(str, "name == null");
        if (this.O0000Oo == null) {
            if (this.O00000oO != 0) {
                this.O0000Oo = str;
                return this;
            }
            throw new IllegalStateException("JsonWriter is closed.");
        }
        throw new IllegalStateException();
    }

    public O00000o O000000o(boolean z) throws IOException {
        O0000Oo();
        O00000oO(false);
        this.O00000o0.write(z ? OOBEEvent.STRING_TRUE : OOBEEvent.STRING_FALSE);
        return this;
    }

    public O00000o O00000Oo() throws IOException {
        O0000Oo();
        return O000000o(1, "[");
    }

    public O00000o O00000Oo(String str) throws IOException {
        if (str == null) {
            return O00000oo();
        }
        O0000Oo();
        O00000oO(false);
        O00000o(str);
        return this;
    }

    public final void O00000Oo(boolean z) {
        this.O0000OOo = z;
    }

    public O00000o O00000o() throws IOException {
        O0000Oo();
        return O000000o(3, "{");
    }

    public final void O00000o(boolean z) {
        this.O0000OoO = z;
    }

    public O00000o O00000o0() throws IOException {
        return O000000o(1, 2, "]");
    }

    public final void O00000o0(String str) {
        String str2;
        if (str.length() == 0) {
            this.O00000oo = null;
            str2 = QuickSettingConstants.JOINER;
        } else {
            this.O00000oo = str;
            str2 = ": ";
        }
        this.O0000O0o = str2;
    }

    public final void O00000o0(boolean z) {
        this.O0000Oo0 = z;
    }

    public O00000o O00000oO() throws IOException {
        return O000000o(3, 5, "}");
    }

    public O00000o O00000oo() throws IOException {
        if (this.O0000Oo != null) {
            if (!this.O0000OoO) {
                this.O0000Oo = null;
                return this;
            }
            O0000Oo();
        }
        O00000oO(false);
        this.O00000o0.write("null");
        return this;
    }

    public boolean O0000O0o() {
        return this.O0000OOo;
    }

    public final boolean O0000OOo() {
        return this.O0000Oo0;
    }

    public final boolean O0000Oo0() {
        return this.O0000OoO;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.O00000o0.close();
        int i = this.O00000oO;
        if (i > 1 || (i == 1 && this.O00000o[i - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.O00000oO = 0;
    }

    public void flush() throws IOException {
        if (this.O00000oO == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.O00000o0.flush();
    }
}
