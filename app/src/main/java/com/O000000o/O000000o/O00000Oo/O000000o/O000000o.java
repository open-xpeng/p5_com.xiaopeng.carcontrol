package com.O000000o.O000000o.O00000Oo.O000000o;

import com.O000000o.O000000o.O000o00;
import com.O000000o.O000000o.O000o000;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/* compiled from: ArrayTypeAdapter.java */
/* loaded from: classes.dex */
public final class O000000o<E> extends O000o000<Object> {
    public static final O000o00 O000000o = new O000o00() { // from class: com.O000000o.O000000o.O00000Oo.O000000o.O000000o.1
        @Override // com.O000000o.O000000o.O000o00
        public <T> O000o000<T> O000000o(com.O000000o.O000000o.O0000OOo o0000OOo, com.O000000o.O000000o.O00000o0.O000000o<T> o000000o) {
            Type O00000Oo = o000000o.O00000Oo();
            if ((O00000Oo instanceof GenericArrayType) || ((O00000Oo instanceof Class) && ((Class) O00000Oo).isArray())) {
                Type O0000O0o = com.O000000o.O000000o.O00000Oo.O00000Oo.O0000O0o(O00000Oo);
                return new O000000o(o0000OOo, o0000OOo.O000000o((com.O000000o.O000000o.O00000o0.O000000o) com.O000000o.O000000o.O00000o0.O000000o.O00000Oo(O0000O0o)), com.O000000o.O000000o.O00000Oo.O00000Oo.O00000oO(O0000O0o));
            }
            return null;
        }
    };
    private final Class<E> O00000Oo;
    private final O000o000<E> O00000o0;

    public O000000o(com.O000000o.O000000o.O0000OOo o0000OOo, O000o000<E> o000o000, Class<E> cls) {
        this.O00000o0 = new O0000o(o0000OOo, o000o000, cls);
        this.O00000Oo = cls;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.O000000o.O000000o.O000o000
    public void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, Object obj) throws IOException {
        if (obj == null) {
            o00000o.O00000oo();
            return;
        }
        o00000o.O00000Oo();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            this.O00000o0.O000000o(o00000o, (com.O000000o.O000000o.O00000o.O00000o) Array.get(obj, i));
        }
        o00000o.O00000o0();
    }

    @Override // com.O000000o.O000000o.O000o000
    public Object O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
        if (o000000o.O00000oo() == com.O000000o.O000000o.O00000o.O00000o0.NULL) {
            o000000o.O0000Oo();
            return null;
        }
        ArrayList arrayList = new ArrayList();
        o000000o.O000000o();
        while (o000000o.O00000oO()) {
            arrayList.add(this.O00000o0.O00000Oo(o000000o));
        }
        o000000o.O00000Oo();
        Object newInstance = Array.newInstance((Class<?>) this.O00000Oo, arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            Array.set(newInstance, i, arrayList.get(i));
        }
        return newInstance;
    }
}
