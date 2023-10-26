package com.O000000o.O000000o;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/* compiled from: TypeAdapter.java */
/* loaded from: classes.dex */
public abstract class O000o000<T> {
    public final O000o000<T> O000000o() {
        return new O000o000<T>() { // from class: com.O000000o.O000000o.O000o000.1
            @Override // com.O000000o.O000000o.O000o000
            public void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, T t) throws IOException {
                if (t == null) {
                    o00000o.O00000oo();
                } else {
                    O000o000.this.O000000o(o00000o, (com.O000000o.O000000o.O00000o.O00000o) t);
                }
            }

            @Override // com.O000000o.O000000o.O000o000
            public T O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
                if (o000000o.O00000oo() == com.O000000o.O000000o.O00000o.O00000o0.NULL) {
                    o000000o.O0000Oo();
                    return null;
                }
                return (T) O000o000.this.O00000Oo(o000000o);
            }
        };
    }

    public final T O000000o(O00oOooO o00oOooO) {
        try {
            return O00000Oo((com.O000000o.O000000o.O00000o.O000000o) new com.O000000o.O000000o.O00000Oo.O000000o.O00000o(o00oOooO));
        } catch (IOException e) {
            throw new O000O00o(e);
        }
    }

    public final T O000000o(Reader reader) throws IOException {
        return O00000Oo(new com.O000000o.O000000o.O00000o.O000000o(reader));
    }

    public final T O000000o(String str) throws IOException {
        return O000000o((Reader) new StringReader(str));
    }

    public final String O000000o(T t) throws IOException {
        StringWriter stringWriter = new StringWriter();
        O000000o((Writer) stringWriter, (StringWriter) t);
        return stringWriter.toString();
    }

    public abstract void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, T t) throws IOException;

    public final void O000000o(Writer writer, T t) throws IOException {
        O000000o(new com.O000000o.O000000o.O00000o.O00000o(writer), (com.O000000o.O000000o.O00000o.O00000o) t);
    }

    public final O00oOooO O00000Oo(T t) {
        try {
            com.O000000o.O000000o.O00000Oo.O000000o.O0000O0o o0000O0o = new com.O000000o.O000000o.O00000Oo.O000000o.O0000O0o();
            O000000o((com.O000000o.O000000o.O00000o.O00000o) o0000O0o, (com.O000000o.O000000o.O00000Oo.O000000o.O0000O0o) t);
            return o0000O0o.O000000o();
        } catch (IOException e) {
            throw new O000O00o(e);
        }
    }

    public abstract T O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException;
}
