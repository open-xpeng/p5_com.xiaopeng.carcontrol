package com.O000000o.O000000o;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: Gson.java */
/* loaded from: classes.dex */
public final class O0000OOo {
    static final boolean O000000o = false;
    private static final String O00000o = ")]}'\n";
    final O0000o0 O00000Oo;
    final O000OO O00000o0;
    private final ThreadLocal<Map<com.O000000o.O000000o.O00000o0.O000000o<?>, O000000o<?>>> O00000oO;
    private final Map<com.O000000o.O000000o.O00000o0.O000000o<?>, O000o000<?>> O00000oo;
    private final List<O000o00> O0000O0o;
    private final com.O000000o.O000000o.O00000Oo.O00000o0 O0000OOo;
    private final boolean O0000Oo;
    private final boolean O0000Oo0;
    private final boolean O0000OoO;
    private final boolean O0000Ooo;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Gson.java */
    /* loaded from: classes.dex */
    public static class O000000o<T> extends O000o000<T> {
        private O000o000<T> O000000o;

        O000000o() {
        }

        @Override // com.O000000o.O000000o.O000o000
        public void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, T t) throws IOException {
            O000o000<T> o000o000 = this.O000000o;
            if (o000o000 == null) {
                throw new IllegalStateException();
            }
            o000o000.O000000o(o00000o, (com.O000000o.O000000o.O00000o.O00000o) t);
        }

        public void O000000o(O000o000<T> o000o000) {
            if (this.O000000o != null) {
                throw new AssertionError();
            }
            this.O000000o = o000o000;
        }

        @Override // com.O000000o.O000000o.O000o000
        public T O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
            O000o000<T> o000o000 = this.O000000o;
            if (o000o000 != null) {
                return o000o000.O00000Oo(o000000o);
            }
            throw new IllegalStateException();
        }
    }

    public O0000OOo() {
        this(com.O000000o.O000000o.O00000Oo.O00000o.O000000o, O00000o.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, O000Oo0.DEFAULT, Collections.emptyList());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public O0000OOo(com.O000000o.O000000o.O00000Oo.O00000o o00000o, O0000O0o o0000O0o, Map<Type, O0000Oo<?>> map, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, O000Oo0 o000Oo0, List<O000o00> list) {
        this.O00000oO = new ThreadLocal<>();
        this.O00000oo = Collections.synchronizedMap(new HashMap());
        this.O00000Oo = new O0000o0() { // from class: com.O000000o.O000000o.O0000OOo.1
            @Override // com.O000000o.O000000o.O0000o0
            public <T> T O000000o(O00oOooO o00oOooO, Type type) throws O000O0o {
                return (T) O0000OOo.this.O000000o(o00oOooO, type);
            }
        };
        this.O00000o0 = new O000OO() { // from class: com.O000000o.O000000o.O0000OOo.2
            @Override // com.O000000o.O000000o.O000OO
            public O00oOooO O000000o(Object obj) {
                return O0000OOo.this.O000000o(obj);
            }

            @Override // com.O000000o.O000000o.O000OO
            public O00oOooO O000000o(Object obj, Type type) {
                return O0000OOo.this.O000000o(obj, type);
            }
        };
        com.O000000o.O000000o.O00000Oo.O00000o0 o00000o0 = new com.O000000o.O000000o.O00000Oo.O00000o0(map);
        this.O0000OOo = o00000o0;
        this.O0000Oo0 = z;
        this.O0000OoO = z3;
        this.O0000Oo = z4;
        this.O0000Ooo = z5;
        ArrayList arrayList = new ArrayList();
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000OOoo);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O0000Oo0.O000000o);
        arrayList.add(o00000o);
        arrayList.addAll(list);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O0000ooO);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O0000o00);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O0000O0o);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O0000Oo0);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O0000OoO);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000000o(Long.TYPE, Long.class, O000000o(o000Oo0)));
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000000o(Double.TYPE, Double.class, O000000o(z6)));
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000000o(Float.TYPE, Float.class, O00000Oo(z6)));
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O0000oO0);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O0000oOO);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O00oOooO);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000O00o);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000000o(BigDecimal.class, com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O0000oo0));
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000000o(BigInteger.class, com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O0000oo));
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000O0Oo);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000O0o0);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000OO00);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000OOo);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000O0oO);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O00000o);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00000o0.O000000o);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000OOOo);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O0000o0.O000000o);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O0000o00.O000000o);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000OO0o);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O000000o.O000000o);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000Oo00);
        arrayList.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O00000Oo);
        arrayList.add(new com.O000000o.O000000o.O00000Oo.O000000o.O00000Oo(o00000o0));
        arrayList.add(new com.O000000o.O000000o.O00000Oo.O000000o.O0000OOo(o00000o0, z2));
        arrayList.add(new com.O000000o.O000000o.O00000Oo.O000000o.O0000Oo(o00000o0, o0000O0o, o00000o));
        this.O0000O0o = Collections.unmodifiableList(arrayList);
    }

    private com.O000000o.O000000o.O00000o.O00000o O000000o(Writer writer) throws IOException {
        if (this.O0000OoO) {
            writer.write(O00000o);
        }
        com.O000000o.O000000o.O00000o.O00000o o00000o = new com.O000000o.O000000o.O00000o.O00000o(writer);
        if (this.O0000Ooo) {
            o00000o.O00000o0("  ");
        }
        o00000o.O00000o(this.O0000Oo0);
        return o00000o;
    }

    private O000o000<Number> O000000o(O000Oo0 o000Oo0) {
        return o000Oo0 == O000Oo0.DEFAULT ? com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O0000o0 : new O000o000<Number>() { // from class: com.O000000o.O000000o.O0000OOo.5
            @Override // com.O000000o.O000000o.O000o000
            /* renamed from: O000000o */
            public Number O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
                if (o000000o.O00000oo() == com.O000000o.O000000o.O00000o.O00000o0.NULL) {
                    o000000o.O0000Oo();
                    return null;
                }
                return Long.valueOf(o000000o.O0000Ooo());
            }

            @Override // com.O000000o.O000000o.O000o000
            public void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, Number number) throws IOException {
                if (number == null) {
                    o00000o.O00000oo();
                } else {
                    o00000o.O00000Oo(number.toString());
                }
            }
        };
    }

    private O000o000<Number> O000000o(boolean z) {
        return z ? com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O0000o0o : new O000o000<Number>() { // from class: com.O000000o.O000000o.O0000OOo.3
            @Override // com.O000000o.O000000o.O000o000
            /* renamed from: O000000o */
            public Double O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
                if (o000000o.O00000oo() == com.O000000o.O000000o.O00000o.O00000o0.NULL) {
                    o000000o.O0000Oo();
                    return null;
                }
                return Double.valueOf(o000000o.O0000OoO());
            }

            @Override // com.O000000o.O000000o.O000o000
            public void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, Number number) throws IOException {
                if (number == null) {
                    o00000o.O00000oo();
                    return;
                }
                O0000OOo.this.O000000o(number.doubleValue());
                o00000o.O000000o(number);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void O000000o(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException(d + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private static void O000000o(Object obj, com.O000000o.O000000o.O00000o.O000000o o000000o) {
        if (obj != null) {
            try {
                if (o000000o.O00000oo() == com.O000000o.O000000o.O00000o.O00000o0.END_DOCUMENT) {
                    return;
                }
                throw new O000O00o("JSON document was not fully consumed.");
            } catch (com.O000000o.O000000o.O00000o.O0000O0o e) {
                throw new O000OOo(e);
            } catch (IOException e2) {
                throw new O000O00o(e2);
            }
        }
    }

    private O000o000<Number> O00000Oo(boolean z) {
        return z ? com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O0000o0O : new O000o000<Number>() { // from class: com.O000000o.O000000o.O0000OOo.4
            @Override // com.O000000o.O000000o.O000o000
            /* renamed from: O000000o */
            public Float O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
                if (o000000o.O00000oo() == com.O000000o.O000000o.O00000o.O00000o0.NULL) {
                    o000000o.O0000Oo();
                    return null;
                }
                return Float.valueOf((float) o000000o.O0000OoO());
            }

            @Override // com.O000000o.O000000o.O000o000
            public void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, Number number) throws IOException {
                if (number == null) {
                    o00000o.O00000oo();
                    return;
                }
                O0000OOo.this.O000000o(number.floatValue());
                o00000o.O000000o(number);
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> O000o000<T> O000000o(com.O000000o.O000000o.O00000o0.O000000o<T> o000000o) {
        O000o000<T> o000o000 = (O000o000<T>) this.O00000oo.get(o000000o);
        if (o000o000 != null) {
            return o000o000;
        }
        Map<com.O000000o.O000000o.O00000o0.O000000o<?>, O000000o<?>> map = this.O00000oO.get();
        boolean z = false;
        if (map == null) {
            map = new HashMap<>();
            this.O00000oO.set(map);
            z = true;
        }
        O000000o<?> o000000o2 = map.get(o000000o);
        if (o000000o2 != null) {
            return o000000o2;
        }
        try {
            O000000o<?> o000000o3 = new O000000o<>();
            map.put(o000000o, o000000o3);
            for (O000o00 o000o00 : this.O0000O0o) {
                O000o000 o000o0002 = (O000o000<T>) o000o00.O000000o(this, o000000o);
                if (o000o0002 != null) {
                    o000000o3.O000000o((O000o000<?>) o000o0002);
                    this.O00000oo.put(o000000o, o000o0002);
                    return o000o0002;
                }
            }
            throw new IllegalArgumentException("GSON cannot handle " + o000000o);
        } finally {
            map.remove(o000000o);
            if (z) {
                this.O00000oO.remove();
            }
        }
    }

    public <T> O000o000<T> O000000o(O000o00 o000o00, com.O000000o.O000000o.O00000o0.O000000o<T> o000000o) {
        boolean z = false;
        for (O000o00 o000o002 : this.O0000O0o) {
            if (z) {
                O000o000<T> O000000o2 = o000o002.O000000o(this, o000000o);
                if (O000000o2 != null) {
                    return O000000o2;
                }
            } else if (o000o002 == o000o00) {
                z = true;
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + o000000o);
    }

    public <T> O000o000<T> O000000o(Class<T> cls) {
        return O000000o((com.O000000o.O000000o.O00000o0.O000000o) com.O000000o.O000000o.O00000o0.O000000o.O00000o0(cls));
    }

    public O00oOooO O000000o(Object obj) {
        return obj == null ? O000O0OO.O000000o : O000000o(obj, obj.getClass());
    }

    public O00oOooO O000000o(Object obj, Type type) {
        com.O000000o.O000000o.O00000Oo.O000000o.O0000O0o o0000O0o = new com.O000000o.O000000o.O00000Oo.O000000o.O0000O0o();
        O000000o(obj, type, o0000O0o);
        return o0000O0o.O000000o();
    }

    public <T> T O000000o(com.O000000o.O000000o.O00000o.O000000o o000000o, Type type) throws O000O00o, O000OOo {
        boolean O0000o0o = o000000o.O0000o0o();
        boolean z = true;
        o000000o.O000000o(true);
        try {
            try {
                try {
                    o000000o.O00000oo();
                    z = false;
                    T O00000Oo = O000000o((com.O000000o.O000000o.O00000o0.O000000o) com.O000000o.O000000o.O00000o0.O000000o.O00000Oo(type)).O00000Oo(o000000o);
                    o000000o.O000000o(O0000o0o);
                    return O00000Oo;
                } catch (EOFException e) {
                    if (z) {
                        o000000o.O000000o(O0000o0o);
                        return null;
                    }
                    throw new O000OOo(e);
                } catch (IllegalStateException e2) {
                    throw new O000OOo(e2);
                }
            } catch (IOException e3) {
                throw new O000OOo(e3);
            }
        } catch (Throwable th) {
            o000000o.O000000o(O0000o0o);
            throw th;
        }
    }

    public <T> T O000000o(O00oOooO o00oOooO, Class<T> cls) throws O000OOo {
        return (T) com.O000000o.O000000o.O00000Oo.O0000o00.O000000o((Class) cls).cast(O000000o(o00oOooO, (Type) cls));
    }

    public <T> T O000000o(O00oOooO o00oOooO, Type type) throws O000OOo {
        if (o00oOooO == null) {
            return null;
        }
        return (T) O000000o((com.O000000o.O000000o.O00000o.O000000o) new com.O000000o.O000000o.O00000Oo.O000000o.O00000o(o00oOooO), type);
    }

    public <T> T O000000o(Reader reader, Class<T> cls) throws O000OOo, O000O00o {
        com.O000000o.O000000o.O00000o.O000000o o000000o = new com.O000000o.O000000o.O00000o.O000000o(reader);
        Object O000000o2 = O000000o(o000000o, (Type) cls);
        O000000o(O000000o2, o000000o);
        return (T) com.O000000o.O000000o.O00000Oo.O0000o00.O000000o((Class) cls).cast(O000000o2);
    }

    public <T> T O000000o(Reader reader, Type type) throws O000O00o, O000OOo {
        com.O000000o.O000000o.O00000o.O000000o o000000o = new com.O000000o.O000000o.O00000o.O000000o(reader);
        T t = (T) O000000o(o000000o, type);
        O000000o(t, o000000o);
        return t;
    }

    public <T> T O000000o(String str, Class<T> cls) throws O000OOo {
        return (T) com.O000000o.O000000o.O00000Oo.O0000o00.O000000o((Class) cls).cast(O000000o(str, (Type) cls));
    }

    public <T> T O000000o(String str, Type type) throws O000OOo {
        if (str == null) {
            return null;
        }
        return (T) O000000o((Reader) new StringReader(str), type);
    }

    public String O000000o(O00oOooO o00oOooO) {
        StringWriter stringWriter = new StringWriter();
        O000000o(o00oOooO, (Appendable) stringWriter);
        return stringWriter.toString();
    }

    public void O000000o(O00oOooO o00oOooO, com.O000000o.O000000o.O00000o.O00000o o00000o) throws O000O00o {
        boolean O0000O0o = o00000o.O0000O0o();
        o00000o.O00000Oo(true);
        boolean O0000OOo = o00000o.O0000OOo();
        o00000o.O00000o0(this.O0000Oo);
        boolean O0000Oo0 = o00000o.O0000Oo0();
        o00000o.O00000o(this.O0000Oo0);
        try {
            try {
                com.O000000o.O000000o.O00000Oo.O0000o0.O000000o(o00oOooO, o00000o);
            } catch (IOException e) {
                throw new O000O00o(e);
            }
        } finally {
            o00000o.O00000Oo(O0000O0o);
            o00000o.O00000o0(O0000OOo);
            o00000o.O00000o(O0000Oo0);
        }
    }

    public void O000000o(O00oOooO o00oOooO, Appendable appendable) throws O000O00o {
        try {
            O000000o(o00oOooO, O000000o(com.O000000o.O000000o.O00000Oo.O0000o0.O000000o(appendable)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void O000000o(Object obj, Appendable appendable) throws O000O00o {
        if (obj != null) {
            O000000o(obj, obj.getClass(), appendable);
        } else {
            O000000o((O00oOooO) O000O0OO.O000000o, appendable);
        }
    }

    public void O000000o(Object obj, Type type, com.O000000o.O000000o.O00000o.O00000o o00000o) throws O000O00o {
        O000o000 O000000o2 = O000000o((com.O000000o.O000000o.O00000o0.O000000o) com.O000000o.O000000o.O00000o0.O000000o.O00000Oo(type));
        boolean O0000O0o = o00000o.O0000O0o();
        o00000o.O00000Oo(true);
        boolean O0000OOo = o00000o.O0000OOo();
        o00000o.O00000o0(this.O0000Oo);
        boolean O0000Oo0 = o00000o.O0000Oo0();
        o00000o.O00000o(this.O0000Oo0);
        try {
            try {
                O000000o2.O000000o(o00000o, (com.O000000o.O000000o.O00000o.O00000o) obj);
            } catch (IOException e) {
                throw new O000O00o(e);
            }
        } finally {
            o00000o.O00000Oo(O0000O0o);
            o00000o.O00000o0(O0000OOo);
            o00000o.O00000o(O0000Oo0);
        }
    }

    public void O000000o(Object obj, Type type, Appendable appendable) throws O000O00o {
        try {
            O000000o(obj, type, O000000o(com.O000000o.O000000o.O00000Oo.O0000o0.O000000o(appendable)));
        } catch (IOException e) {
            throw new O000O00o(e);
        }
    }

    public String O00000Oo(Object obj) {
        return obj == null ? O000000o((O00oOooO) O000O0OO.O000000o) : O00000Oo(obj, obj.getClass());
    }

    public String O00000Oo(Object obj, Type type) {
        StringWriter stringWriter = new StringWriter();
        O000000o(obj, type, stringWriter);
        return stringWriter.toString();
    }

    public String toString() {
        return "{serializeNulls:" + this.O0000Oo0 + "factories:" + this.O0000O0o + ",instanceCreators:" + this.O0000OOo + "}";
    }
}
