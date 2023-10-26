package com.O000000o.O000000o;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: JsonStreamParser.java */
/* loaded from: classes.dex */
public final class O000OOo0 implements Iterator<O00oOooO> {
    private final com.O000000o.O000000o.O00000o.O000000o O000000o;
    private final Object O00000Oo;

    public O000OOo0(Reader reader) {
        com.O000000o.O000000o.O00000o.O000000o o000000o = new com.O000000o.O000000o.O00000o.O000000o(reader);
        this.O000000o = o000000o;
        o000000o.O000000o(true);
        this.O00000Oo = new Object();
    }

    public O000OOo0(String str) {
        this(new StringReader(str));
    }

    @Override // java.util.Iterator
    /* renamed from: O000000o */
    public O00oOooO next() throws O000O0o {
        if (hasNext()) {
            try {
                return com.O000000o.O000000o.O00000Oo.O0000o0.O000000o(this.O000000o);
            } catch (O000O0o e) {
                if (e.getCause() instanceof EOFException) {
                    throw new NoSuchElementException();
                }
                throw e;
            } catch (OutOfMemoryError e2) {
                throw new O000O0o("Failed parsing JSON source to Json", e2);
            } catch (StackOverflowError e3) {
                throw new O000O0o("Failed parsing JSON source to Json", e3);
            }
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        boolean z;
        synchronized (this.O00000Oo) {
            try {
                try {
                    try {
                        z = this.O000000o.O00000oo() != com.O000000o.O000000o.O00000o.O00000o0.END_DOCUMENT;
                    } catch (IOException e) {
                        throw new O000O00o(e);
                    }
                } catch (com.O000000o.O000000o.O00000o.O0000O0o e2) {
                    throw new O000OOo(e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
