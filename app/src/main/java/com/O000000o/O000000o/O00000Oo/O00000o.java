package com.O000000o.O000000o.O00000Oo;

import com.O000000o.O000000o.O000o00;
import com.O000000o.O000000o.O000o000;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: Excluder.java */
/* loaded from: classes.dex */
public final class O00000o implements O000o00, Cloneable {
    public static final O00000o O000000o = new O00000o();
    private static final double O00000Oo = -1.0d;
    private boolean O00000oo;
    private double O00000o0 = O00000Oo;
    private int O00000o = 136;
    private boolean O00000oO = true;
    private List<com.O000000o.O000000o.O00000Oo> O0000O0o = Collections.emptyList();
    private List<com.O000000o.O000000o.O00000Oo> O0000OOo = Collections.emptyList();

    private boolean O000000o(com.O000000o.O000000o.O000000o.O00000o0 o00000o0) {
        return o00000o0 == null || o00000o0.O000000o() <= this.O00000o0;
    }

    private boolean O000000o(com.O000000o.O000000o.O000000o.O00000o0 o00000o0, com.O000000o.O000000o.O000000o.O00000o o00000o) {
        return O000000o(o00000o0) && O000000o(o00000o);
    }

    private boolean O000000o(com.O000000o.O000000o.O000000o.O00000o o00000o) {
        return o00000o == null || o00000o.O000000o() > this.O00000o0;
    }

    private boolean O000000o(Class<?> cls) {
        return !Enum.class.isAssignableFrom(cls) && (cls.isAnonymousClass() || cls.isLocalClass());
    }

    private boolean O00000Oo(Class<?> cls) {
        return cls.isMemberClass() && !O00000o0(cls);
    }

    private boolean O00000o0(Class<?> cls) {
        return (cls.getModifiers() & 8) != 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: O000000o */
    public O00000o clone() {
        try {
            return (O00000o) super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new AssertionError();
        }
    }

    public O00000o O000000o(double d) {
        O00000o clone = clone();
        clone.O00000o0 = d;
        return clone;
    }

    public O00000o O000000o(com.O000000o.O000000o.O00000Oo o00000Oo, boolean z, boolean z2) {
        O00000o clone = clone();
        if (z) {
            ArrayList arrayList = new ArrayList(this.O0000O0o);
            clone.O0000O0o = arrayList;
            arrayList.add(o00000Oo);
        }
        if (z2) {
            ArrayList arrayList2 = new ArrayList(this.O0000OOo);
            clone.O0000OOo = arrayList2;
            arrayList2.add(o00000Oo);
        }
        return clone;
    }

    public O00000o O000000o(int... iArr) {
        O00000o clone = clone();
        clone.O00000o = 0;
        for (int i : iArr) {
            clone.O00000o = i | clone.O00000o;
        }
        return clone;
    }

    @Override // com.O000000o.O000000o.O000o00
    public <T> O000o000<T> O000000o(final com.O000000o.O000000o.O0000OOo o0000OOo, final com.O000000o.O000000o.O00000o0.O000000o<T> o000000o) {
        Class<? super T> O000000o2 = o000000o.O000000o();
        final boolean O000000o3 = O000000o((Class<?>) O000000o2, true);
        final boolean O000000o4 = O000000o((Class<?>) O000000o2, false);
        if (O000000o3 || O000000o4) {
            return new O000o000<T>() { // from class: com.O000000o.O000000o.O00000Oo.O00000o.1
                private O000o000<T> O00000oo;

                private O000o000<T> O00000Oo() {
                    O000o000<T> o000o000 = this.O00000oo;
                    if (o000o000 != null) {
                        return o000o000;
                    }
                    O000o000<T> O000000o5 = o0000OOo.O000000o(O00000o.this, o000000o);
                    this.O00000oo = O000000o5;
                    return O000000o5;
                }

                @Override // com.O000000o.O000000o.O000o000
                public void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, T t) throws IOException {
                    if (O000000o3) {
                        o00000o.O00000oo();
                    } else {
                        O00000Oo().O000000o(o00000o, (com.O000000o.O000000o.O00000o.O00000o) t);
                    }
                }

                @Override // com.O000000o.O000000o.O000o000
                public T O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o2) throws IOException {
                    if (O000000o4) {
                        o000000o2.O0000o0();
                        return null;
                    }
                    return O00000Oo().O00000Oo(o000000o2);
                }
            };
        }
        return null;
    }

    public boolean O000000o(Class<?> cls, boolean z) {
        if (this.O00000o0 == O00000Oo || O000000o((com.O000000o.O000000o.O000000o.O00000o0) cls.getAnnotation(com.O000000o.O000000o.O000000o.O00000o0.class), (com.O000000o.O000000o.O000000o.O00000o) cls.getAnnotation(com.O000000o.O000000o.O000000o.O00000o.class))) {
            if ((this.O00000oO || !O00000Oo(cls)) && !O000000o(cls)) {
                for (com.O000000o.O000000o.O00000Oo o00000Oo : z ? this.O0000O0o : this.O0000OOo) {
                    if (o00000Oo.O000000o(cls)) {
                        return true;
                    }
                }
                return false;
            }
            return true;
        }
        return true;
    }

    public boolean O000000o(Field field, boolean z) {
        com.O000000o.O000000o.O000000o.O000000o o000000o;
        if ((this.O00000o & field.getModifiers()) != 0) {
            return true;
        }
        if ((this.O00000o0 == O00000Oo || O000000o((com.O000000o.O000000o.O000000o.O00000o0) field.getAnnotation(com.O000000o.O000000o.O000000o.O00000o0.class), (com.O000000o.O000000o.O000000o.O00000o) field.getAnnotation(com.O000000o.O000000o.O000000o.O00000o.class))) && !field.isSynthetic()) {
            if (!this.O00000oo || ((o000000o = (com.O000000o.O000000o.O000000o.O000000o) field.getAnnotation(com.O000000o.O000000o.O000000o.O000000o.class)) != null && (!z ? !o000000o.O00000Oo() : !o000000o.O000000o()))) {
                if ((this.O00000oO || !O00000Oo(field.getType())) && !O000000o(field.getType())) {
                    List<com.O000000o.O000000o.O00000Oo> list = z ? this.O0000O0o : this.O0000OOo;
                    if (list.isEmpty()) {
                        return false;
                    }
                    com.O000000o.O000000o.O00000o0 o00000o0 = new com.O000000o.O000000o.O00000o0(field);
                    for (com.O000000o.O000000o.O00000Oo o00000Oo : list) {
                        if (o00000Oo.O000000o(o00000o0)) {
                            return true;
                        }
                    }
                    return false;
                }
                return true;
            }
            return true;
        }
        return true;
    }

    public O00000o O00000Oo() {
        O00000o clone = clone();
        clone.O00000oO = false;
        return clone;
    }

    public O00000o O00000o0() {
        O00000o clone = clone();
        clone.O00000oo = true;
        return clone;
    }
}
