package com.O000000o.O000000o;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

/* compiled from: JsonElement.java */
/* loaded from: classes.dex */
public abstract class O00oOooO {
    public String O00000o() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public Number O00000o0() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public double O00000oO() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public BigDecimal O00000oo() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public BigInteger O0000O0o() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public float O0000OOo() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public int O0000Oo() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public long O0000Oo0() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public byte O0000OoO() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public char O0000Ooo() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public boolean O0000o() {
        return this instanceof O000O0o0;
    }

    public boolean O0000o0() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public short O0000o00() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract O00oOooO O0000o0O();

    public boolean O0000o0o() {
        return this instanceof O0000o00;
    }

    public boolean O0000oO() {
        return this instanceof O000O0OO;
    }

    public boolean O0000oO0() {
        return this instanceof O000OO0o;
    }

    public O000O0o0 O0000oOO() {
        if (O0000o()) {
            return (O000O0o0) this;
        }
        throw new IllegalStateException("Not a JSON Object: " + this);
    }

    public O0000o00 O0000oOo() {
        if (O0000o0o()) {
            return (O0000o00) this;
        }
        throw new IllegalStateException("This is not a JSON Array.");
    }

    public O000O0OO O0000oo() {
        if (O0000oO()) {
            return (O000O0OO) this;
        }
        throw new IllegalStateException("This is not a JSON Null.");
    }

    public O000OO0o O0000oo0() {
        if (O0000oO0()) {
            return (O000OO0o) this;
        }
        throw new IllegalStateException("This is not a JSON Primitive.");
    }

    Boolean O0000ooO() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String toString() {
        try {
            StringWriter stringWriter = new StringWriter();
            com.O000000o.O000000o.O00000o.O00000o o00000o = new com.O000000o.O000000o.O00000o.O00000o(stringWriter);
            o00000o.O00000Oo(true);
            com.O000000o.O000000o.O00000Oo.O0000o0.O000000o(this, o00000o);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
