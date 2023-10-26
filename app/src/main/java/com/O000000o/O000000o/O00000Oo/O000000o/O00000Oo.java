package com.O000000o.O000000o.O00000Oo.O000000o;

import com.O000000o.O000000o.O000o00;
import com.O000000o.O000000o.O000o000;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

/* compiled from: CollectionTypeAdapterFactory.java */
/* loaded from: classes.dex */
public final class O00000Oo implements O000o00 {
    private final com.O000000o.O000000o.O00000Oo.O00000o0 O000000o;

    /* compiled from: CollectionTypeAdapterFactory.java */
    /* loaded from: classes.dex */
    private static final class O000000o<E> extends O000o000<Collection<E>> {
        private final O000o000<E> O000000o;
        private final com.O000000o.O000000o.O00000Oo.O0000Oo<? extends Collection<E>> O00000Oo;

        public O000000o(com.O000000o.O000000o.O0000OOo o0000OOo, Type type, O000o000<E> o000o000, com.O000000o.O000000o.O00000Oo.O0000Oo<? extends Collection<E>> o0000Oo) {
            this.O000000o = new O0000o(o0000OOo, o000o000, type);
            this.O00000Oo = o0000Oo;
        }

        @Override // com.O000000o.O000000o.O000o000
        /* renamed from: O000000o */
        public Collection<E> O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
            if (o000000o.O00000oo() == com.O000000o.O000000o.O00000o.O00000o0.NULL) {
                o000000o.O0000Oo();
                return null;
            }
            Collection<E> O000000o = this.O00000Oo.O000000o();
            o000000o.O000000o();
            while (o000000o.O00000oO()) {
                O000000o.add(this.O000000o.O00000Oo(o000000o));
            }
            o000000o.O00000Oo();
            return O000000o;
        }

        @Override // com.O000000o.O000000o.O000o000
        public /* bridge */ /* synthetic */ void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, Object obj) throws IOException {
            O000000o(o00000o, (Collection) ((Collection) obj));
        }

        public void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, Collection<E> collection) throws IOException {
            if (collection == null) {
                o00000o.O00000oo();
                return;
            }
            o00000o.O00000Oo();
            for (E e : collection) {
                this.O000000o.O000000o(o00000o, (com.O000000o.O000000o.O00000o.O00000o) e);
            }
            o00000o.O00000o0();
        }
    }

    public O00000Oo(com.O000000o.O000000o.O00000Oo.O00000o0 o00000o0) {
        this.O000000o = o00000o0;
    }

    @Override // com.O000000o.O000000o.O000o00
    public <T> O000o000<T> O000000o(com.O000000o.O000000o.O0000OOo o0000OOo, com.O000000o.O000000o.O00000o0.O000000o<T> o000000o) {
        Type O00000Oo = o000000o.O00000Oo();
        Class<? super T> O000000o2 = o000000o.O000000o();
        if (Collection.class.isAssignableFrom(O000000o2)) {
            Type O000000o3 = com.O000000o.O000000o.O00000Oo.O00000Oo.O000000o(O00000Oo, (Class<?>) O000000o2);
            return new O000000o(o0000OOo, O000000o3, o0000OOo.O000000o((com.O000000o.O000000o.O00000o0.O000000o) com.O000000o.O000000o.O00000o0.O000000o.O00000Oo(O000000o3)), this.O000000o.O000000o(o000000o));
        }
        return null;
    }
}
