package com.O000000o.O000000o.O00000o0;

import com.O000000o.O000000o.O00000Oo.O00000Oo;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

/* compiled from: TypeToken.java */
/* loaded from: classes.dex */
public class O000000o<T> {
    final Class<? super T> O000000o;
    final Type O00000Oo;
    final int O00000o0;

    protected O000000o() {
        Type O000000o = O000000o(getClass());
        this.O00000Oo = O000000o;
        this.O000000o = (Class<? super T>) O00000Oo.O00000oO(O000000o);
        this.O00000o0 = O000000o.hashCode();
    }

    O000000o(Type type) {
        Type O00000o = O00000Oo.O00000o((Type) com.O000000o.O000000o.O00000Oo.O000000o.O000000o(type));
        this.O00000Oo = O00000o;
        this.O000000o = (Class<? super T>) O00000Oo.O00000oO(O00000o);
        this.O00000o0 = O00000o.hashCode();
    }

    private static AssertionError O000000o(Type type, Class<?>... clsArr) {
        StringBuilder sb = new StringBuilder("Unexpected type. Expected one of: ");
        for (Class<?> cls : clsArr) {
            sb.append(cls.getName()).append(", ");
        }
        sb.append("but got: ").append(type.getClass().getName()).append(", for type token: ").append(type.toString()).append('.');
        return new AssertionError(sb.toString());
    }

    static Type O000000o(Class<?> cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        return O00000Oo.O00000o(((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
    }

    private static boolean O000000o(ParameterizedType parameterizedType, ParameterizedType parameterizedType2, Map<String, Type> map) {
        if (parameterizedType.getRawType().equals(parameterizedType2.getRawType())) {
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Type[] actualTypeArguments2 = parameterizedType2.getActualTypeArguments();
            for (int i = 0; i < actualTypeArguments.length; i++) {
                if (!O000000o(actualTypeArguments[i], actualTypeArguments2[i], map)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.Class] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r1v9 */
    private static boolean O000000o(Type type, GenericArrayType genericArrayType) {
        Type genericComponentType = genericArrayType.getGenericComponentType();
        if (genericComponentType instanceof ParameterizedType) {
            if (type instanceof GenericArrayType) {
                type = ((GenericArrayType) type).getGenericComponentType();
            } else if (type instanceof Class) {
                type = (Class) type;
                while (type.isArray()) {
                    type = type.getComponentType();
                }
            }
            return O000000o((Type) type, (ParameterizedType) genericComponentType, (Map<String, Type>) new HashMap());
        }
        return true;
    }

    private static boolean O000000o(Type type, ParameterizedType parameterizedType, Map<String, Type> map) {
        if (type == null) {
            return false;
        }
        if (parameterizedType.equals(type)) {
            return true;
        }
        Class<?> O00000oO = O00000Oo.O00000oO(type);
        ParameterizedType parameterizedType2 = type instanceof ParameterizedType ? (ParameterizedType) type : null;
        if (parameterizedType2 != null) {
            Type[] actualTypeArguments = parameterizedType2.getActualTypeArguments();
            TypeVariable<Class<?>>[] typeParameters = O00000oO.getTypeParameters();
            for (int i = 0; i < actualTypeArguments.length; i++) {
                Type type2 = actualTypeArguments[i];
                TypeVariable<Class<?>> typeVariable = typeParameters[i];
                while (type2 instanceof TypeVariable) {
                    type2 = map.get(((TypeVariable) type2).getName());
                }
                map.put(typeVariable.getName(), type2);
            }
            if (O000000o(parameterizedType2, parameterizedType, map)) {
                return true;
            }
        }
        for (Type type3 : O00000oO.getGenericInterfaces()) {
            if (O000000o(type3, parameterizedType, (Map<String, Type>) new HashMap(map))) {
                return true;
            }
        }
        return O000000o(O00000oO.getGenericSuperclass(), parameterizedType, (Map<String, Type>) new HashMap(map));
    }

    private static boolean O000000o(Type type, Type type2, Map<String, Type> map) {
        return type2.equals(type) || ((type instanceof TypeVariable) && type2.equals(map.get(((TypeVariable) type).getName())));
    }

    public static O000000o<?> O00000Oo(Type type) {
        return new O000000o<>(type);
    }

    public static <T> O000000o<T> O00000o0(Class<T> cls) {
        return new O000000o<>(cls);
    }

    public final Class<? super T> O000000o() {
        return this.O000000o;
    }

    @Deprecated
    public boolean O000000o(O000000o<?> o000000o) {
        return O000000o(o000000o.O00000Oo());
    }

    @Deprecated
    public boolean O000000o(Type type) {
        if (type == null) {
            return false;
        }
        if (this.O00000Oo.equals(type)) {
            return true;
        }
        Type type2 = this.O00000Oo;
        if (type2 instanceof Class) {
            return this.O000000o.isAssignableFrom(O00000Oo.O00000oO(type));
        }
        if (type2 instanceof ParameterizedType) {
            return O000000o(type, (ParameterizedType) type2, (Map<String, Type>) new HashMap());
        }
        if (type2 instanceof GenericArrayType) {
            return this.O000000o.isAssignableFrom(O00000Oo.O00000oO(type)) && O000000o(type, (GenericArrayType) this.O00000Oo);
        }
        throw O000000o(type2, Class.class, ParameterizedType.class, GenericArrayType.class);
    }

    public final Type O00000Oo() {
        return this.O00000Oo;
    }

    @Deprecated
    public boolean O00000Oo(Class<?> cls) {
        return O000000o((Type) cls);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof O000000o) && O00000Oo.O000000o(this.O00000Oo, ((O000000o) obj).O00000Oo);
    }

    public final int hashCode() {
        return this.O00000o0;
    }

    public final String toString() {
        return O00000Oo.O00000oo(this.O00000Oo);
    }
}
