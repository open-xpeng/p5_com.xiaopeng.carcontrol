package com.O000000o.O000000o;

import java.io.IOException;

/* compiled from: TreeTypeAdapter.java */
/* loaded from: classes.dex */
final class O00O0Oo<T> extends O000o000<T> {
    private final O000OOOo<T> O000000o;
    private final O0000o<T> O00000Oo;
    private final com.O000000o.O000000o.O00000o0.O000000o<T> O00000o;
    private final O0000OOo O00000o0;
    private final O000o00 O00000oO;
    private O000o000<T> O00000oo;

    /* compiled from: TreeTypeAdapter.java */
    /* loaded from: classes.dex */
    private static class O000000o implements O000o00 {
        private final com.O000000o.O000000o.O00000o0.O000000o<?> O000000o;
        private final boolean O00000Oo;
        private final O000OOOo<?> O00000o;
        private final Class<?> O00000o0;
        private final O0000o<?> O00000oO;

        private O000000o(Object obj, com.O000000o.O000000o.O00000o0.O000000o<?> o000000o, boolean z, Class<?> cls) {
            O000OOOo<?> o000OOOo = obj instanceof O000OOOo ? (O000OOOo) obj : null;
            this.O00000o = o000OOOo;
            O0000o<?> o0000o = obj instanceof O0000o ? (O0000o) obj : null;
            this.O00000oO = o0000o;
            com.O000000o.O000000o.O00000Oo.O000000o.O000000o((o000OOOo == null && o0000o == null) ? false : true);
            this.O000000o = o000000o;
            this.O00000Oo = z;
            this.O00000o0 = cls;
        }

        @Override // com.O000000o.O000000o.O000o00
        public <T> O000o000<T> O000000o(O0000OOo o0000OOo, com.O000000o.O000000o.O00000o0.O000000o<T> o000000o) {
            com.O000000o.O000000o.O00000o0.O000000o<?> o000000o2 = this.O000000o;
            if (o000000o2 != null ? o000000o2.equals(o000000o) || (this.O00000Oo && this.O000000o.O00000Oo() == o000000o.O000000o()) : this.O00000o0.isAssignableFrom(o000000o.O000000o())) {
                return new O00O0Oo(this.O00000o, this.O00000oO, o0000OOo, o000000o, this);
            }
            return null;
        }
    }

    private O00O0Oo(O000OOOo<T> o000OOOo, O0000o<T> o0000o, O0000OOo o0000OOo, com.O000000o.O000000o.O00000o0.O000000o<T> o000000o, O000o00 o000o00) {
        this.O000000o = o000OOOo;
        this.O00000Oo = o0000o;
        this.O00000o0 = o0000OOo;
        this.O00000o = o000000o;
        this.O00000oO = o000o00;
    }

    public static O000o00 O000000o(com.O000000o.O000000o.O00000o0.O000000o<?> o000000o, Object obj) {
        return new O000000o(obj, o000000o, false, null);
    }

    public static O000o00 O000000o(Class<?> cls, Object obj) {
        return new O000000o(obj, null, false, cls);
    }

    private O000o000<T> O00000Oo() {
        O000o000<T> o000o000 = this.O00000oo;
        if (o000o000 != null) {
            return o000o000;
        }
        O000o000<T> O000000o2 = this.O00000o0.O000000o(this.O00000oO, this.O00000o);
        this.O00000oo = O000000o2;
        return O000000o2;
    }

    public static O000o00 O00000Oo(com.O000000o.O000000o.O00000o0.O000000o<?> o000000o, Object obj) {
        return new O000000o(obj, o000000o, o000000o.O00000Oo() == o000000o.O000000o(), null);
    }

    @Override // com.O000000o.O000000o.O000o000
    public void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, T t) throws IOException {
        O000OOOo<T> o000OOOo = this.O000000o;
        if (o000OOOo == null) {
            O00000Oo().O000000o(o00000o, (com.O000000o.O000000o.O00000o.O00000o) t);
        } else if (t == null) {
            o00000o.O00000oo();
        } else {
            com.O000000o.O000000o.O00000Oo.O0000o0.O000000o(o000OOOo.O000000o(t, this.O00000o.O00000Oo(), this.O00000o0.O00000o0), o00000o);
        }
    }

    @Override // com.O000000o.O000000o.O000o000
    public T O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
        if (this.O00000Oo == null) {
            return O00000Oo().O00000Oo(o000000o);
        }
        O00oOooO O000000o2 = com.O000000o.O000000o.O00000Oo.O0000o0.O000000o(o000000o);
        if (O000000o2.O0000oO()) {
            return null;
        }
        return this.O00000Oo.O00000Oo(O000000o2, this.O00000o.O00000Oo(), this.O00000o0.O00000Oo);
    }
}
