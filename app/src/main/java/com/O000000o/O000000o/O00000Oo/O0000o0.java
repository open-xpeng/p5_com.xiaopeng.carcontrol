package com.O000000o.O000000o.O00000Oo;

import com.O000000o.O000000o.O000O00o;
import com.O000000o.O000000o.O000O0OO;
import com.O000000o.O000000o.O000O0o;
import com.O000000o.O000000o.O000OOo;
import com.O000000o.O000000o.O00oOooO;
import java.io.EOFException;
import java.io.IOException;
import java.io.Writer;

/* compiled from: Streams.java */
/* loaded from: classes.dex */
public final class O0000o0 {

    /* compiled from: Streams.java */
    /* loaded from: classes.dex */
    private static final class O000000o extends Writer {
        private final Appendable O000000o;
        private final C0006O000000o O00000Oo;

        /* compiled from: Streams.java */
        /* renamed from: com.O000000o.O000000o.O00000Oo.O0000o0$O000000o$O000000o  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        static class C0006O000000o implements CharSequence {
            char[] O000000o;

            C0006O000000o() {
            }

            @Override // java.lang.CharSequence
            public char charAt(int i) {
                return this.O000000o[i];
            }

            @Override // java.lang.CharSequence
            public int length() {
                return this.O000000o.length;
            }

            @Override // java.lang.CharSequence
            public CharSequence subSequence(int i, int i2) {
                return new String(this.O000000o, i, i2 - i);
            }
        }

        private O000000o(Appendable appendable) {
            this.O00000Oo = new C0006O000000o();
            this.O000000o = appendable;
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
        }

        @Override // java.io.Writer
        public void write(int i) throws IOException {
            this.O000000o.append((char) i);
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) throws IOException {
            this.O00000Oo.O000000o = cArr;
            this.O000000o.append(this.O00000Oo, i, i2 + i);
        }
    }

    public static O00oOooO O000000o(com.O000000o.O000000o.O00000o.O000000o o000000o) throws O000O0o {
        boolean z;
        try {
            try {
                o000000o.O00000oo();
                z = false;
                try {
                    return com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000OOoO.O00000Oo(o000000o);
                } catch (EOFException e) {
                    e = e;
                    if (z) {
                        return O000O0OO.O000000o;
                    }
                    throw new O000OOo(e);
                }
            } catch (com.O000000o.O000000o.O00000o.O0000O0o e2) {
                throw new O000OOo(e2);
            } catch (IOException e3) {
                throw new O000O00o(e3);
            } catch (NumberFormatException e4) {
                throw new O000OOo(e4);
            }
        } catch (EOFException e5) {
            e = e5;
            z = true;
        }
    }

    public static Writer O000000o(Appendable appendable) {
        return appendable instanceof Writer ? (Writer) appendable : new O000000o(appendable);
    }

    public static void O000000o(O00oOooO o00oOooO, com.O000000o.O000000o.O00000o.O00000o o00000o) throws IOException {
        com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000OOoO.O000000o(o00000o, (com.O000000o.O000000o.O00000o.O00000o) o00oOooO);
    }
}
