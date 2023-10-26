package com.O000000o.O000000o;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: JsonArray.java */
/* loaded from: classes.dex */
public final class O0000o00 extends O00oOooO implements Iterable<O00oOooO> {
    private final List<O00oOooO> O000000o = new ArrayList();

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.O000000o.O000000o.O00oOooO
    /* renamed from: O000000o */
    public O0000o00 O0000o0O() {
        O0000o00 o0000o00 = new O0000o00();
        for (O00oOooO o00oOooO : this.O000000o) {
            o0000o00.O000000o(o00oOooO.O0000o0O());
        }
        return o0000o00;
    }

    public O00oOooO O000000o(int i) {
        return this.O000000o.get(i);
    }

    public void O000000o(O0000o00 o0000o00) {
        this.O000000o.addAll(o0000o00.O000000o);
    }

    public void O000000o(O00oOooO o00oOooO) {
        if (o00oOooO == null) {
            o00oOooO = O000O0OO.O000000o;
        }
        this.O000000o.add(o00oOooO);
    }

    public int O00000Oo() {
        return this.O000000o.size();
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public String O00000o() {
        if (this.O000000o.size() == 1) {
            return this.O000000o.get(0).O00000o();
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public Number O00000o0() {
        if (this.O000000o.size() == 1) {
            return this.O000000o.get(0).O00000o0();
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public double O00000oO() {
        if (this.O000000o.size() == 1) {
            return this.O000000o.get(0).O00000oO();
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public BigDecimal O00000oo() {
        if (this.O000000o.size() == 1) {
            return this.O000000o.get(0).O00000oo();
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public BigInteger O0000O0o() {
        if (this.O000000o.size() == 1) {
            return this.O000000o.get(0).O0000O0o();
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public float O0000OOo() {
        if (this.O000000o.size() == 1) {
            return this.O000000o.get(0).O0000OOo();
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public int O0000Oo() {
        if (this.O000000o.size() == 1) {
            return this.O000000o.get(0).O0000Oo();
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public long O0000Oo0() {
        if (this.O000000o.size() == 1) {
            return this.O000000o.get(0).O0000Oo0();
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public byte O0000OoO() {
        if (this.O000000o.size() == 1) {
            return this.O000000o.get(0).O0000OoO();
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public char O0000Ooo() {
        if (this.O000000o.size() == 1) {
            return this.O000000o.get(0).O0000Ooo();
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public boolean O0000o0() {
        if (this.O000000o.size() == 1) {
            return this.O000000o.get(0).O0000o0();
        }
        throw new IllegalStateException();
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public short O0000o00() {
        if (this.O000000o.size() == 1) {
            return this.O000000o.get(0).O0000o00();
        }
        throw new IllegalStateException();
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof O0000o00) && ((O0000o00) obj).O000000o.equals(this.O000000o));
    }

    public int hashCode() {
        return this.O000000o.hashCode();
    }

    @Override // java.lang.Iterable
    public Iterator<O00oOooO> iterator() {
        return this.O000000o.iterator();
    }
}
