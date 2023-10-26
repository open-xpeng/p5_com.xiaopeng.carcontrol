package com.O000000o.O000000o.O00000Oo.O000000o;

import com.O000000o.O000000o.O000OO0o;
import com.O000000o.O000000o.O000OOo;
import com.O000000o.O000000o.O000o00;
import com.O000000o.O000000o.O000o000;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/* compiled from: MapTypeAdapterFactory.java */
/* loaded from: classes.dex */
public final class O0000OOo implements O000o00 {
    private final com.O000000o.O000000o.O00000Oo.O00000o0 O000000o;
    private final boolean O00000Oo;

    /* compiled from: MapTypeAdapterFactory.java */
    /* loaded from: classes.dex */
    private final class O000000o<K, V> extends O000o000<Map<K, V>> {
        private final O000o000<K> O00000Oo;
        private final com.O000000o.O000000o.O00000Oo.O0000Oo<? extends Map<K, V>> O00000o;
        private final O000o000<V> O00000o0;

        public O000000o(com.O000000o.O000000o.O0000OOo o0000OOo, Type type, O000o000<K> o000o000, Type type2, O000o000<V> o000o0002, com.O000000o.O000000o.O00000Oo.O0000Oo<? extends Map<K, V>> o0000Oo) {
            this.O00000Oo = new O0000o(o0000OOo, o000o000, type);
            this.O00000o0 = new O0000o(o0000OOo, o000o0002, type2);
            this.O00000o = o0000Oo;
        }

        private String O00000Oo(com.O000000o.O000000o.O00oOooO o00oOooO) {
            if (!o00oOooO.O0000oO0()) {
                if (o00oOooO.O0000oO()) {
                    return "null";
                }
                throw new AssertionError();
            }
            O000OO0o O0000oo0 = o00oOooO.O0000oo0();
            if (O0000oo0.O0000ooo()) {
                return String.valueOf(O0000oo0.O00000o0());
            }
            if (O0000oo0.O00000Oo()) {
                return Boolean.toString(O0000oo0.O0000o0());
            }
            if (O0000oo0.O00oOooO()) {
                return O0000oo0.O00000o();
            }
            throw new AssertionError();
        }

        @Override // com.O000000o.O000000o.O000o000
        /* renamed from: O000000o */
        public Map<K, V> O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
            com.O000000o.O000000o.O00000o.O00000o0 O00000oo = o000000o.O00000oo();
            if (O00000oo == com.O000000o.O000000o.O00000o.O00000o0.NULL) {
                o000000o.O0000Oo();
                return null;
            }
            Map<K, V> O000000o = this.O00000o.O000000o();
            if (O00000oo == com.O000000o.O000000o.O00000o.O00000o0.BEGIN_ARRAY) {
                o000000o.O000000o();
                while (o000000o.O00000oO()) {
                    o000000o.O000000o();
                    K O00000Oo = this.O00000Oo.O00000Oo(o000000o);
                    if (O000000o.put(O00000Oo, this.O00000o0.O00000Oo(o000000o)) != null) {
                        throw new O000OOo("duplicate key: " + O00000Oo);
                    }
                    o000000o.O00000Oo();
                }
                o000000o.O00000Oo();
            } else {
                o000000o.O00000o0();
                while (o000000o.O00000oO()) {
                    com.O000000o.O000000o.O00000Oo.O0000O0o.O000000o.O000000o(o000000o);
                    K O00000Oo2 = this.O00000Oo.O00000Oo(o000000o);
                    if (O000000o.put(O00000Oo2, this.O00000o0.O00000Oo(o000000o)) != null) {
                        throw new O000OOo("duplicate key: " + O00000Oo2);
                    }
                }
                o000000o.O00000o();
            }
            return O000000o;
        }

        @Override // com.O000000o.O000000o.O000o000
        public /* bridge */ /* synthetic */ void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, Object obj) throws IOException {
            O000000o(o00000o, (Map) ((Map) obj));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, Map<K, V> map) throws IOException {
            if (map == null) {
                o00000o.O00000oo();
            } else if (!O0000OOo.this.O00000Oo) {
                o00000o.O00000o();
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    o00000o.O000000o(String.valueOf(entry.getKey()));
                    this.O00000o0.O000000o(o00000o, (com.O000000o.O000000o.O00000o.O00000o) entry.getValue());
                }
                o00000o.O00000oO();
            } else {
                ArrayList arrayList = new ArrayList(map.size());
                ArrayList arrayList2 = new ArrayList(map.size());
                int i = 0;
                boolean z = false;
                for (Map.Entry<K, V> entry2 : map.entrySet()) {
                    com.O000000o.O000000o.O00oOooO O00000Oo = this.O00000Oo.O00000Oo((O000o000<K>) entry2.getKey());
                    arrayList.add(O00000Oo);
                    arrayList2.add(entry2.getValue());
                    z |= O00000Oo.O0000o0o() || O00000Oo.O0000o();
                }
                if (!z) {
                    o00000o.O00000o();
                    while (i < arrayList.size()) {
                        o00000o.O000000o(O00000Oo((com.O000000o.O000000o.O00oOooO) arrayList.get(i)));
                        this.O00000o0.O000000o(o00000o, (com.O000000o.O000000o.O00000o.O00000o) arrayList2.get(i));
                        i++;
                    }
                    o00000o.O00000oO();
                    return;
                }
                o00000o.O00000Oo();
                while (i < arrayList.size()) {
                    o00000o.O00000Oo();
                    com.O000000o.O000000o.O00000Oo.O0000o0.O000000o((com.O000000o.O000000o.O00oOooO) arrayList.get(i), o00000o);
                    this.O00000o0.O000000o(o00000o, (com.O000000o.O000000o.O00000o.O00000o) arrayList2.get(i));
                    o00000o.O00000o0();
                    i++;
                }
                o00000o.O00000o0();
            }
        }
    }

    public O0000OOo(com.O000000o.O000000o.O00000Oo.O00000o0 o00000o0, boolean z) {
        this.O000000o = o00000o0;
        this.O00000Oo = z;
    }

    private O000o000<?> O000000o(com.O000000o.O000000o.O0000OOo o0000OOo, Type type) {
        return (type == Boolean.TYPE || type == Boolean.class) ? O00oOooO.O00000oo : o0000OOo.O000000o((com.O000000o.O000000o.O00000o0.O000000o) com.O000000o.O000000o.O00000o0.O000000o.O00000Oo(type));
    }

    @Override // com.O000000o.O000000o.O000o00
    public <T> O000o000<T> O000000o(com.O000000o.O000000o.O0000OOo o0000OOo, com.O000000o.O000000o.O00000o0.O000000o<T> o000000o) {
        Type O00000Oo = o000000o.O00000Oo();
        if (Map.class.isAssignableFrom(o000000o.O000000o())) {
            Type[] O00000Oo2 = com.O000000o.O000000o.O00000Oo.O00000Oo.O00000Oo(O00000Oo, com.O000000o.O000000o.O00000Oo.O00000Oo.O00000oO(O00000Oo));
            return new O000000o(o0000OOo, O00000Oo2[0], O000000o(o0000OOo, O00000Oo2[0]), O00000Oo2[1], o0000OOo.O000000o((com.O000000o.O000000o.O00000o0.O000000o) com.O000000o.O000000o.O00000o0.O000000o.O00000Oo(O00000Oo2[1])), this.O000000o.O000000o(o000000o));
        }
        return null;
    }
}
