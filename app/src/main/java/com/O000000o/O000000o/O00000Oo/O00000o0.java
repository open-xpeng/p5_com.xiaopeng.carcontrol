package com.O000000o.O000000o.O00000Oo;

import com.O000000o.O000000o.O000O00o;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/* compiled from: ConstructorConstructor.java */
/* loaded from: classes.dex */
public final class O00000o0 {
    private final Map<Type, com.O000000o.O000000o.O0000Oo<?>> O000000o;

    public O00000o0(Map<Type, com.O000000o.O000000o.O0000Oo<?>> map) {
        this.O000000o = map;
    }

    private <T> O0000Oo<T> O000000o(Class<? super T> cls) {
        try {
            final Constructor<? super T> declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                declaredConstructor.setAccessible(true);
            }
            return new O0000Oo<T>() { // from class: com.O000000o.O000000o.O00000Oo.O00000o0.6
                @Override // com.O000000o.O000000o.O00000Oo.O0000Oo
                public T O000000o() {
                    try {
                        return (T) declaredConstructor.newInstance(null);
                    } catch (IllegalAccessException e) {
                        throw new AssertionError(e);
                    } catch (InstantiationException e2) {
                        throw new RuntimeException("Failed to invoke " + declaredConstructor + " with no args", e2);
                    } catch (InvocationTargetException e3) {
                        throw new RuntimeException("Failed to invoke " + declaredConstructor + " with no args", e3.getTargetException());
                    }
                }
            };
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    private <T> O0000Oo<T> O000000o(final Type type, Class<? super T> cls) {
        if (Collection.class.isAssignableFrom(cls)) {
            return SortedSet.class.isAssignableFrom(cls) ? new O0000Oo<T>() { // from class: com.O000000o.O000000o.O00000Oo.O00000o0.7
                @Override // com.O000000o.O000000o.O00000Oo.O0000Oo
                public T O000000o() {
                    return (T) new TreeSet();
                }
            } : EnumSet.class.isAssignableFrom(cls) ? new O0000Oo<T>() { // from class: com.O000000o.O000000o.O00000Oo.O00000o0.8
                @Override // com.O000000o.O000000o.O00000Oo.O0000Oo
                public T O000000o() {
                    Type type2 = type;
                    if (type2 instanceof ParameterizedType) {
                        Type type3 = ((ParameterizedType) type2).getActualTypeArguments()[0];
                        if (type3 instanceof Class) {
                            return (T) EnumSet.noneOf((Class) type3);
                        }
                        throw new O000O00o("Invalid EnumSet type: " + type.toString());
                    }
                    throw new O000O00o("Invalid EnumSet type: " + type.toString());
                }
            } : Set.class.isAssignableFrom(cls) ? new O0000Oo<T>() { // from class: com.O000000o.O000000o.O00000Oo.O00000o0.9
                @Override // com.O000000o.O000000o.O00000Oo.O0000Oo
                public T O000000o() {
                    return (T) new LinkedHashSet();
                }
            } : Queue.class.isAssignableFrom(cls) ? new O0000Oo<T>() { // from class: com.O000000o.O000000o.O00000Oo.O00000o0.10
                @Override // com.O000000o.O000000o.O00000Oo.O0000Oo
                public T O000000o() {
                    return (T) new LinkedList();
                }
            } : new O0000Oo<T>() { // from class: com.O000000o.O000000o.O00000Oo.O00000o0.11
                @Override // com.O000000o.O000000o.O00000Oo.O0000Oo
                public T O000000o() {
                    return (T) new ArrayList();
                }
            };
        } else if (Map.class.isAssignableFrom(cls)) {
            return SortedMap.class.isAssignableFrom(cls) ? new O0000Oo<T>() { // from class: com.O000000o.O000000o.O00000Oo.O00000o0.12
                @Override // com.O000000o.O000000o.O00000Oo.O0000Oo
                public T O000000o() {
                    return (T) new TreeMap();
                }
            } : (!(type instanceof ParameterizedType) || String.class.isAssignableFrom(com.O000000o.O000000o.O00000o0.O000000o.O00000Oo(((ParameterizedType) type).getActualTypeArguments()[0]).O000000o())) ? new O0000Oo<T>() { // from class: com.O000000o.O000000o.O00000Oo.O00000o0.3
                @Override // com.O000000o.O000000o.O00000Oo.O0000Oo
                public T O000000o() {
                    return (T) new O0000Oo0();
                }
            } : new O0000Oo<T>() { // from class: com.O000000o.O000000o.O00000Oo.O00000o0.2
                @Override // com.O000000o.O000000o.O00000Oo.O0000Oo
                public T O000000o() {
                    return (T) new LinkedHashMap();
                }
            };
        } else {
            return null;
        }
    }

    private <T> O0000Oo<T> O00000Oo(final Type type, final Class<? super T> cls) {
        return new O0000Oo<T>() { // from class: com.O000000o.O000000o.O00000Oo.O00000o0.4
            private final O0000o O00000o = O0000o.O000000o();

            @Override // com.O000000o.O000000o.O00000Oo.O0000Oo
            public T O000000o() {
                try {
                    return (T) this.O00000o.O000000o(cls);
                } catch (Exception e) {
                    throw new RuntimeException("Unable to invoke no-args constructor for " + type + ". Register an InstanceCreator with Gson for this type may fix this problem.", e);
                }
            }
        };
    }

    public <T> O0000Oo<T> O000000o(com.O000000o.O000000o.O00000o0.O000000o<T> o000000o) {
        final Type O00000Oo = o000000o.O00000Oo();
        Class<? super T> O000000o = o000000o.O000000o();
        final com.O000000o.O000000o.O0000Oo<?> o0000Oo = this.O000000o.get(O00000Oo);
        if (o0000Oo != null) {
            return new O0000Oo<T>() { // from class: com.O000000o.O000000o.O00000Oo.O00000o0.1
                @Override // com.O000000o.O000000o.O00000Oo.O0000Oo
                public T O000000o() {
                    return (T) o0000Oo.O000000o(O00000Oo);
                }
            };
        }
        final com.O000000o.O000000o.O0000Oo<?> o0000Oo2 = this.O000000o.get(O000000o);
        if (o0000Oo2 != null) {
            return new O0000Oo<T>() { // from class: com.O000000o.O000000o.O00000Oo.O00000o0.5
                @Override // com.O000000o.O000000o.O00000Oo.O0000Oo
                public T O000000o() {
                    return (T) o0000Oo2.O000000o(O00000Oo);
                }
            };
        }
        O0000Oo<T> O000000o2 = O000000o(O000000o);
        if (O000000o2 != null) {
            return O000000o2;
        }
        O0000Oo<T> O000000o3 = O000000o(O00000Oo, O000000o);
        return O000000o3 != null ? O000000o3 : O00000Oo(O00000Oo, O000000o);
    }

    public String toString() {
        return this.O000000o.toString();
    }
}
