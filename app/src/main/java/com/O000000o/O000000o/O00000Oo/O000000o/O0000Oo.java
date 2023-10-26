package com.O000000o.O000000o.O00000Oo.O000000o;

import com.O000000o.O000000o.O000OOo;
import com.O000000o.O000000o.O000o00;
import com.O000000o.O000000o.O000o000;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: ReflectiveTypeAdapterFactory.java */
/* loaded from: classes.dex */
public final class O0000Oo implements O000o00 {
    private final com.O000000o.O000000o.O00000Oo.O00000o0 O000000o;
    private final com.O000000o.O000000o.O0000O0o O00000Oo;
    private final com.O000000o.O000000o.O00000Oo.O00000o O00000o0;

    /* compiled from: ReflectiveTypeAdapterFactory.java */
    /* loaded from: classes.dex */
    public static final class O000000o<T> extends O000o000<T> {
        private final com.O000000o.O000000o.O00000Oo.O0000Oo<T> O000000o;
        private final Map<String, O00000Oo> O00000Oo;

        private O000000o(com.O000000o.O000000o.O00000Oo.O0000Oo<T> o0000Oo, Map<String, O00000Oo> map) {
            this.O000000o = o0000Oo;
            this.O00000Oo = map;
        }

        @Override // com.O000000o.O000000o.O000o000
        public void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, T t) throws IOException {
            if (t == null) {
                o00000o.O00000oo();
                return;
            }
            o00000o.O00000o();
            try {
                for (O00000Oo o00000Oo : this.O00000Oo.values()) {
                    if (o00000Oo.O0000OOo) {
                        o00000o.O000000o(o00000Oo.O0000O0o);
                        o00000Oo.O000000o(o00000o, t);
                    }
                }
                o00000o.O00000oO();
            } catch (IllegalAccessException unused) {
                throw new AssertionError();
            }
        }

        @Override // com.O000000o.O000000o.O000o000
        public T O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
            if (o000000o.O00000oo() == com.O000000o.O000000o.O00000o.O00000o0.NULL) {
                o000000o.O0000Oo();
                return null;
            }
            T O000000o = this.O000000o.O000000o();
            try {
                o000000o.O00000o0();
                while (o000000o.O00000oO()) {
                    O00000Oo o00000Oo = this.O00000Oo.get(o000000o.O0000O0o());
                    if (o00000Oo != null && o00000Oo.O0000Oo0) {
                        o00000Oo.O000000o(o000000o, O000000o);
                    }
                    o000000o.O0000o0();
                }
                o000000o.O00000o();
                return O000000o;
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (IllegalStateException e2) {
                throw new O000OOo(e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ReflectiveTypeAdapterFactory.java */
    /* loaded from: classes.dex */
    public static abstract class O00000Oo {
        final String O0000O0o;
        final boolean O0000OOo;
        final boolean O0000Oo0;

        protected O00000Oo(String str, boolean z, boolean z2) {
            this.O0000O0o = str;
            this.O0000OOo = z;
            this.O0000Oo0 = z2;
        }

        abstract void O000000o(com.O000000o.O000000o.O00000o.O000000o o000000o, Object obj) throws IOException, IllegalAccessException;

        abstract void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, Object obj) throws IOException, IllegalAccessException;
    }

    public O0000Oo(com.O000000o.O000000o.O00000Oo.O00000o0 o00000o0, com.O000000o.O000000o.O0000O0o o0000O0o, com.O000000o.O000000o.O00000Oo.O00000o o00000o) {
        this.O000000o = o00000o0;
        this.O00000Oo = o0000O0o;
        this.O00000o0 = o00000o;
    }

    private O00000Oo O000000o(com.O000000o.O000000o.O0000OOo o0000OOo, Field field, String str, com.O000000o.O000000o.O00000o0.O000000o<?> o000000o, boolean z, boolean z2) {
        return new O00000Oo(str, z, z2, o0000OOo, o000000o, field, com.O000000o.O000000o.O00000Oo.O0000o00.O000000o((Type) o000000o.O000000o())) { // from class: com.O000000o.O000000o.O00000Oo.O000000o.O0000Oo.1
            final O000o000<?> O000000o;
            final /* synthetic */ com.O000000o.O000000o.O0000OOo O00000Oo;
            final /* synthetic */ Field O00000o;
            final /* synthetic */ com.O000000o.O000000o.O00000o0.O000000o O00000o0;
            final /* synthetic */ boolean O00000oO;

            {
                this.O00000Oo = o0000OOo;
                this.O00000o0 = o000000o;
                this.O00000o = field;
                this.O00000oO = r8;
                this.O000000o = o0000OOo.O000000o(o000000o);
            }

            @Override // com.O000000o.O000000o.O00000Oo.O000000o.O0000Oo.O00000Oo
            void O000000o(com.O000000o.O000000o.O00000o.O000000o o000000o2, Object obj) throws IOException, IllegalAccessException {
                Object O00000Oo2 = this.O000000o.O00000Oo(o000000o2);
                if (O00000Oo2 == null && this.O00000oO) {
                    return;
                }
                this.O00000o.set(obj, O00000Oo2);
            }

            @Override // com.O000000o.O000000o.O00000Oo.O000000o.O0000Oo.O00000Oo
            void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, Object obj) throws IOException, IllegalAccessException {
                new O0000o(this.O00000Oo, this.O000000o, this.O00000o0.O00000Oo()).O000000o(o00000o, (com.O000000o.O000000o.O00000o.O00000o) this.O00000o.get(obj));
            }
        };
    }

    private String O000000o(Field field) {
        com.O000000o.O000000o.O000000o.O00000Oo o00000Oo = (com.O000000o.O000000o.O000000o.O00000Oo) field.getAnnotation(com.O000000o.O000000o.O000000o.O00000Oo.class);
        return o00000Oo == null ? this.O00000Oo.O000000o(field) : o00000Oo.O000000o();
    }

    private Map<String, O00000Oo> O000000o(com.O000000o.O000000o.O0000OOo o0000OOo, com.O000000o.O000000o.O00000o0.O000000o<?> o000000o, Class<?> cls) {
        Field[] declaredFields;
        O00000Oo o00000Oo;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (cls.isInterface()) {
            return linkedHashMap;
        }
        Type O00000Oo2 = o000000o.O00000Oo();
        com.O000000o.O000000o.O00000o0.O000000o<?> o000000o2 = o000000o;
        Class<?> cls2 = cls;
        while (cls2 != Object.class) {
            for (Field field : cls2.getDeclaredFields()) {
                boolean O000000o2 = O000000o(field, true);
                boolean O000000o3 = O000000o(field, false);
                if (O000000o2 || O000000o3) {
                    field.setAccessible(true);
                    O00000Oo O000000o4 = O000000o(o0000OOo, field, O000000o(field), com.O000000o.O000000o.O00000o0.O000000o.O00000Oo(com.O000000o.O000000o.O00000Oo.O00000Oo.O000000o(o000000o2.O00000Oo(), cls2, field.getGenericType())), O000000o2, O000000o3);
                    if (((O00000Oo) linkedHashMap.put(O000000o4.O0000O0o, O000000o4)) != null) {
                        throw new IllegalArgumentException(O00000Oo2 + " declares multiple JSON fields named " + o00000Oo.O0000O0o);
                    }
                }
            }
            o000000o2 = com.O000000o.O000000o.O00000o0.O000000o.O00000Oo(com.O000000o.O000000o.O00000Oo.O00000Oo.O000000o(o000000o2.O00000Oo(), cls2, cls2.getGenericSuperclass()));
            cls2 = o000000o2.O000000o();
        }
        return linkedHashMap;
    }

    @Override // com.O000000o.O000000o.O000o00
    public <T> O000o000<T> O000000o(com.O000000o.O000000o.O0000OOo o0000OOo, com.O000000o.O000000o.O00000o0.O000000o<T> o000000o) {
        Class<? super T> O000000o2 = o000000o.O000000o();
        if (Object.class.isAssignableFrom(O000000o2)) {
            return new O000000o(this.O000000o.O000000o(o000000o), O000000o(o0000OOo, o000000o, O000000o2));
        }
        return null;
    }

    public boolean O000000o(Field field, boolean z) {
        return (this.O00000o0.O000000o(field.getType(), z) || this.O00000o0.O000000o(field, z)) ? false : true;
    }
}
