package com.O000000o.O000000o.O00000Oo.O000000o;

import com.O000000o.O000000o.O000O0OO;
import com.O000000o.O000000o.O000O0o0;
import com.O000000o.O000000o.O000OO0o;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/* compiled from: JsonTreeWriter.java */
/* loaded from: classes.dex */
public final class O0000O0o extends com.O000000o.O000000o.O00000o.O00000o {
    private static final Writer O000000o = new Writer() { // from class: com.O000000o.O000000o.O00000Oo.O000000o.O0000O0o.1
        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            throw new AssertionError();
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() throws IOException {
            throw new AssertionError();
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) {
            throw new AssertionError();
        }
    };
    private static final O000OO0o O00000Oo = new O000OO0o("closed");
    private String O00000o;
    private final List<com.O000000o.O000000o.O00oOooO> O00000o0;
    private com.O000000o.O000000o.O00oOooO O00000oO;

    public O0000O0o() {
        super(O000000o);
        this.O00000o0 = new ArrayList();
        this.O00000oO = O000O0OO.O000000o;
    }

    private void O000000o(com.O000000o.O000000o.O00oOooO o00oOooO) {
        if (this.O00000o != null) {
            if (!o00oOooO.O0000oO() || O0000Oo0()) {
                ((O000O0o0) O0000Oo()).O000000o(this.O00000o, o00oOooO);
            }
            this.O00000o = null;
        } else if (this.O00000o0.isEmpty()) {
            this.O00000oO = o00oOooO;
        } else {
            com.O000000o.O000000o.O00oOooO O0000Oo = O0000Oo();
            if (!(O0000Oo instanceof com.O000000o.O000000o.O0000o00)) {
                throw new IllegalStateException();
            }
            ((com.O000000o.O000000o.O0000o00) O0000Oo).O000000o(o00oOooO);
        }
    }

    private com.O000000o.O000000o.O00oOooO O0000Oo() {
        List<com.O000000o.O000000o.O00oOooO> list = this.O00000o0;
        return list.get(list.size() - 1);
    }

    @Override // com.O000000o.O000000o.O00000o.O00000o
    public com.O000000o.O000000o.O00000o.O00000o O000000o(double d) throws IOException {
        if (O0000O0o() || !(Double.isNaN(d) || Double.isInfinite(d))) {
            O000000o(new O000OO0o((Number) Double.valueOf(d)));
            return this;
        }
        throw new IllegalArgumentException("JSON forbids NaN and infinities: " + d);
    }

    @Override // com.O000000o.O000000o.O00000o.O00000o
    public com.O000000o.O000000o.O00000o.O00000o O000000o(long j) throws IOException {
        O000000o(new O000OO0o((Number) Long.valueOf(j)));
        return this;
    }

    @Override // com.O000000o.O000000o.O00000o.O00000o
    public com.O000000o.O000000o.O00000o.O00000o O000000o(Number number) throws IOException {
        if (number == null) {
            return O00000oo();
        }
        if (!O0000O0o()) {
            double doubleValue = number.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                throw new IllegalArgumentException("JSON forbids NaN and infinities: " + number);
            }
        }
        O000000o(new O000OO0o(number));
        return this;
    }

    @Override // com.O000000o.O000000o.O00000o.O00000o
    public com.O000000o.O000000o.O00000o.O00000o O000000o(String str) throws IOException {
        if (this.O00000o0.isEmpty() || this.O00000o != null) {
            throw new IllegalStateException();
        }
        if (O0000Oo() instanceof O000O0o0) {
            this.O00000o = str;
            return this;
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00000o.O00000o
    public com.O000000o.O000000o.O00000o.O00000o O000000o(boolean z) throws IOException {
        O000000o(new O000OO0o(Boolean.valueOf(z)));
        return this;
    }

    public com.O000000o.O000000o.O00oOooO O000000o() {
        if (this.O00000o0.isEmpty()) {
            return this.O00000oO;
        }
        throw new IllegalStateException("Expected one JSON element but was " + this.O00000o0);
    }

    @Override // com.O000000o.O000000o.O00000o.O00000o
    public com.O000000o.O000000o.O00000o.O00000o O00000Oo() throws IOException {
        com.O000000o.O000000o.O0000o00 o0000o00 = new com.O000000o.O000000o.O0000o00();
        O000000o(o0000o00);
        this.O00000o0.add(o0000o00);
        return this;
    }

    @Override // com.O000000o.O000000o.O00000o.O00000o
    public com.O000000o.O000000o.O00000o.O00000o O00000Oo(String str) throws IOException {
        if (str == null) {
            return O00000oo();
        }
        O000000o(new O000OO0o(str));
        return this;
    }

    @Override // com.O000000o.O000000o.O00000o.O00000o
    public com.O000000o.O000000o.O00000o.O00000o O00000o() throws IOException {
        O000O0o0 o000O0o0 = new O000O0o0();
        O000000o(o000O0o0);
        this.O00000o0.add(o000O0o0);
        return this;
    }

    @Override // com.O000000o.O000000o.O00000o.O00000o
    public com.O000000o.O000000o.O00000o.O00000o O00000o0() throws IOException {
        if (this.O00000o0.isEmpty() || this.O00000o != null) {
            throw new IllegalStateException();
        }
        if (O0000Oo() instanceof com.O000000o.O000000o.O0000o00) {
            List<com.O000000o.O000000o.O00oOooO> list = this.O00000o0;
            list.remove(list.size() - 1);
            return this;
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00000o.O00000o
    public com.O000000o.O000000o.O00000o.O00000o O00000oO() throws IOException {
        if (this.O00000o0.isEmpty() || this.O00000o != null) {
            throw new IllegalStateException();
        }
        if (O0000Oo() instanceof O000O0o0) {
            List<com.O000000o.O000000o.O00oOooO> list = this.O00000o0;
            list.remove(list.size() - 1);
            return this;
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00000o.O00000o
    public com.O000000o.O000000o.O00000o.O00000o O00000oo() throws IOException {
        O000000o(O000O0OO.O000000o);
        return this;
    }

    @Override // com.O000000o.O000000o.O00000o.O00000o, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.O00000o0.isEmpty()) {
            throw new IOException("Incomplete document");
        }
        this.O00000o0.add(O00000Oo);
    }

    @Override // com.O000000o.O000000o.O00000o.O00000o, java.io.Flushable
    public void flush() throws IOException {
    }
}
