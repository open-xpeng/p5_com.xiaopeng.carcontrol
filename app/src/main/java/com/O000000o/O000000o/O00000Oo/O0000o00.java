package com.O000000o.O000000o.O00000Oo;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Primitives.java */
/* loaded from: classes.dex */
public final class O0000o00 {
    private static final Map<Class<?>, Class<?>> O000000o;
    private static final Map<Class<?>, Class<?>> O00000Oo;

    static {
        HashMap hashMap = new HashMap(16);
        HashMap hashMap2 = new HashMap(16);
        O000000o(hashMap, hashMap2, Boolean.TYPE, Boolean.class);
        O000000o(hashMap, hashMap2, Byte.TYPE, Byte.class);
        O000000o(hashMap, hashMap2, Character.TYPE, Character.class);
        O000000o(hashMap, hashMap2, Double.TYPE, Double.class);
        O000000o(hashMap, hashMap2, Float.TYPE, Float.class);
        O000000o(hashMap, hashMap2, Integer.TYPE, Integer.class);
        O000000o(hashMap, hashMap2, Long.TYPE, Long.class);
        O000000o(hashMap, hashMap2, Short.TYPE, Short.class);
        O000000o(hashMap, hashMap2, Void.TYPE, Void.class);
        O000000o = Collections.unmodifiableMap(hashMap);
        O00000Oo = Collections.unmodifiableMap(hashMap2);
    }

    private O0000o00() {
    }

    public static <T> Class<T> O000000o(Class<T> cls) {
        Class<T> cls2 = (Class<T>) O000000o.get(O000000o.O000000o(cls));
        return cls2 == null ? cls : cls2;
    }

    private static void O000000o(Map<Class<?>, Class<?>> map, Map<Class<?>, Class<?>> map2, Class<?> cls, Class<?> cls2) {
        map.put(cls, cls2);
        map2.put(cls2, cls);
    }

    public static boolean O000000o(Type type) {
        return O000000o.containsKey(type);
    }

    public static <T> Class<T> O00000Oo(Class<T> cls) {
        Class<T> cls2 = (Class<T>) O00000Oo.get(O000000o.O000000o(cls));
        return cls2 == null ? cls : cls2;
    }

    public static boolean O00000Oo(Type type) {
        return O00000Oo.containsKey(O000000o.O000000o(type));
    }
}
