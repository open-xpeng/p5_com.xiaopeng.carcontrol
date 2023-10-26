package com.O000000o.O000000o;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/* compiled from: JsonParser.java */
/* loaded from: classes.dex */
public final class O000OO00 {
    public O00oOooO O000000o(com.O000000o.O000000o.O00000o.O000000o o000000o) throws O000O00o, O000OOo {
        boolean O0000o0o = o000000o.O0000o0o();
        o000000o.O000000o(true);
        try {
            try {
                return com.O000000o.O000000o.O00000Oo.O0000o0.O000000o(o000000o);
            } catch (OutOfMemoryError e) {
                throw new O000O0o("Failed parsing JSON source: " + o000000o + " to Json", e);
            } catch (StackOverflowError e2) {
                throw new O000O0o("Failed parsing JSON source: " + o000000o + " to Json", e2);
            }
        } finally {
            o000000o.O000000o(O0000o0o);
        }
    }

    public O00oOooO O000000o(Reader reader) throws O000O00o, O000OOo {
        try {
            com.O000000o.O000000o.O00000o.O000000o o000000o = new com.O000000o.O000000o.O00000o.O000000o(reader);
            O00oOooO O000000o = O000000o(o000000o);
            if (!O000000o.O0000oO() && o000000o.O00000oo() != com.O000000o.O000000o.O00000o.O00000o0.END_DOCUMENT) {
                throw new O000OOo("Did not consume the entire document.");
            }
            return O000000o;
        } catch (com.O000000o.O000000o.O00000o.O0000O0o e) {
            throw new O000OOo(e);
        } catch (IOException e2) {
            throw new O000O00o(e2);
        } catch (NumberFormatException e3) {
            throw new O000OOo(e3);
        }
    }

    public O00oOooO O000000o(String str) throws O000OOo {
        return O000000o(new StringReader(str));
    }
}
