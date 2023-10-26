package com.O000000o.O000000o.O00000Oo.O000000o;

import com.O000000o.O000000o.O000O0OO;
import com.O000000o.O000000o.O000O0o0;
import com.O000000o.O000000o.O000OO0o;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: JsonTreeReader.java */
/* loaded from: classes.dex */
public final class O00000o extends com.O000000o.O000000o.O00000o.O000000o {
    private static final Reader O000000o = new Reader() { // from class: com.O000000o.O000000o.O00000Oo.O000000o.O00000o.1
        @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            throw new AssertionError();
        }

        @Override // java.io.Reader
        public int read(char[] cArr, int i, int i2) throws IOException {
            throw new AssertionError();
        }
    };
    private static final Object O00000Oo = new Object();
    private final List<Object> O00000o0;

    public O00000o(com.O000000o.O000000o.O00oOooO o00oOooO) {
        super(O000000o);
        ArrayList arrayList = new ArrayList();
        this.O00000o0 = arrayList;
        arrayList.add(o00oOooO);
    }

    private void O000000o(com.O000000o.O000000o.O00000o.O00000o0 o00000o0) throws IOException {
        if (O00000oo() != o00000o0) {
            throw new IllegalStateException("Expected " + o00000o0 + " but was " + O00000oo());
        }
    }

    private Object O0000o() {
        List<Object> list = this.O00000o0;
        return list.get(list.size() - 1);
    }

    private Object O0000oO0() {
        List<Object> list = this.O00000o0;
        return list.remove(list.size() - 1);
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public void O000000o() throws IOException {
        O000000o(com.O000000o.O000000o.O00000o.O00000o0.BEGIN_ARRAY);
        this.O00000o0.add(((com.O000000o.O000000o.O0000o00) O0000o()).iterator());
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public void O00000Oo() throws IOException {
        O000000o(com.O000000o.O000000o.O00000o.O00000o0.END_ARRAY);
        O0000oO0();
        O0000oO0();
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public void O00000o() throws IOException {
        O000000o(com.O000000o.O000000o.O00000o.O00000o0.END_OBJECT);
        O0000oO0();
        O0000oO0();
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public void O00000o0() throws IOException {
        O000000o(com.O000000o.O000000o.O00000o.O00000o0.BEGIN_OBJECT);
        this.O00000o0.add(((O000O0o0) O0000o()).O00000Oo().iterator());
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public boolean O00000oO() throws IOException {
        com.O000000o.O000000o.O00000o.O00000o0 O00000oo = O00000oo();
        return (O00000oo == com.O000000o.O000000o.O00000o.O00000o0.END_OBJECT || O00000oo == com.O000000o.O000000o.O00000o.O00000o0.END_ARRAY) ? false : true;
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public com.O000000o.O000000o.O00000o.O00000o0 O00000oo() throws IOException {
        if (this.O00000o0.isEmpty()) {
            return com.O000000o.O000000o.O00000o.O00000o0.END_DOCUMENT;
        }
        Object O0000o = O0000o();
        if (O0000o instanceof Iterator) {
            List<Object> list = this.O00000o0;
            boolean z = list.get(list.size() - 2) instanceof O000O0o0;
            Iterator it = (Iterator) O0000o;
            if (!it.hasNext()) {
                return z ? com.O000000o.O000000o.O00000o.O00000o0.END_OBJECT : com.O000000o.O000000o.O00000o.O00000o0.END_ARRAY;
            } else if (z) {
                return com.O000000o.O000000o.O00000o.O00000o0.NAME;
            } else {
                this.O00000o0.add(it.next());
                return O00000oo();
            }
        } else if (O0000o instanceof O000O0o0) {
            return com.O000000o.O000000o.O00000o.O00000o0.BEGIN_OBJECT;
        } else {
            if (O0000o instanceof com.O000000o.O000000o.O0000o00) {
                return com.O000000o.O000000o.O00000o.O00000o0.BEGIN_ARRAY;
            }
            if (!(O0000o instanceof O000OO0o)) {
                if (O0000o instanceof O000O0OO) {
                    return com.O000000o.O000000o.O00000o.O00000o0.NULL;
                }
                if (O0000o == O00000Oo) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                throw new AssertionError();
            }
            O000OO0o o000OO0o = (O000OO0o) O0000o;
            if (o000OO0o.O00oOooO()) {
                return com.O000000o.O000000o.O00000o.O00000o0.STRING;
            }
            if (o000OO0o.O00000Oo()) {
                return com.O000000o.O000000o.O00000o.O00000o0.BOOLEAN;
            }
            if (o000OO0o.O0000ooo()) {
                return com.O000000o.O000000o.O00000o.O00000o0.NUMBER;
            }
            throw new AssertionError();
        }
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public String O0000O0o() throws IOException {
        O000000o(com.O000000o.O000000o.O00000o.O00000o0.NAME);
        Map.Entry entry = (Map.Entry) ((Iterator) O0000o()).next();
        this.O00000o0.add(entry.getValue());
        return (String) entry.getKey();
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public String O0000OOo() throws IOException {
        com.O000000o.O000000o.O00000o.O00000o0 O00000oo = O00000oo();
        if (O00000oo == com.O000000o.O000000o.O00000o.O00000o0.STRING || O00000oo == com.O000000o.O000000o.O00000o.O00000o0.NUMBER) {
            return ((O000OO0o) O0000oO0()).O00000o();
        }
        throw new IllegalStateException("Expected " + com.O000000o.O000000o.O00000o.O00000o0.STRING + " but was " + O00000oo);
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public void O0000Oo() throws IOException {
        O000000o(com.O000000o.O000000o.O00000o.O00000o0.NULL);
        O0000oO0();
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public boolean O0000Oo0() throws IOException {
        O000000o(com.O000000o.O000000o.O00000o.O00000o0.BOOLEAN);
        return ((O000OO0o) O0000oO0()).O0000o0();
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public double O0000OoO() throws IOException {
        com.O000000o.O000000o.O00000o.O00000o0 O00000oo = O00000oo();
        if (O00000oo == com.O000000o.O000000o.O00000o.O00000o0.NUMBER || O00000oo == com.O000000o.O000000o.O00000o.O00000o0.STRING) {
            double O00000oO = ((O000OO0o) O0000o()).O00000oO();
            if (O0000o0o() || !(Double.isNaN(O00000oO) || Double.isInfinite(O00000oO))) {
                O0000oO0();
                return O00000oO;
            }
            throw new NumberFormatException("JSON forbids NaN and infinities: " + O00000oO);
        }
        throw new IllegalStateException("Expected " + com.O000000o.O000000o.O00000o.O00000o0.NUMBER + " but was " + O00000oo);
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public long O0000Ooo() throws IOException {
        com.O000000o.O000000o.O00000o.O00000o0 O00000oo = O00000oo();
        if (O00000oo == com.O000000o.O000000o.O00000o.O00000o0.NUMBER || O00000oo == com.O000000o.O000000o.O00000o.O00000o0.STRING) {
            long O0000Oo0 = ((O000OO0o) O0000o()).O0000Oo0();
            O0000oO0();
            return O0000Oo0;
        }
        throw new IllegalStateException("Expected " + com.O000000o.O000000o.O00000o.O00000o0.NUMBER + " but was " + O00000oo);
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public void O0000o0() throws IOException {
        if (O00000oo() == com.O000000o.O000000o.O00000o.O00000o0.NAME) {
            O0000O0o();
        } else {
            O0000oO0();
        }
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public int O0000o00() throws IOException {
        com.O000000o.O000000o.O00000o.O00000o0 O00000oo = O00000oo();
        if (O00000oo == com.O000000o.O000000o.O00000o.O00000o0.NUMBER || O00000oo == com.O000000o.O000000o.O00000o.O00000o0.STRING) {
            int O0000Oo = ((O000OO0o) O0000o()).O0000Oo();
            O0000oO0();
            return O0000Oo;
        }
        throw new IllegalStateException("Expected " + com.O000000o.O000000o.O00000o.O00000o0.NUMBER + " but was " + O00000oo);
    }

    public void O0000o0O() throws IOException {
        O000000o(com.O000000o.O000000o.O00000o.O00000o0.NAME);
        Map.Entry entry = (Map.Entry) ((Iterator) O0000o()).next();
        this.O00000o0.add(entry.getValue());
        this.O00000o0.add(new O000OO0o((String) entry.getKey()));
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.O00000o0.clear();
        this.O00000o0.add(O00000Oo);
    }

    @Override // com.O000000o.O000000o.O00000o.O000000o
    public String toString() {
        return getClass().getSimpleName();
    }
}
