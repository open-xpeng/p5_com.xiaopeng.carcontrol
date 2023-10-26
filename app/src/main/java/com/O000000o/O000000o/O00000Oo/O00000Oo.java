package com.O000000o.O000000o.O00000Oo;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

/* compiled from: $Gson$Types.java */
/* loaded from: classes.dex */
public final class O00000Oo {
    static final Type[] O000000o = new Type[0];

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: $Gson$Types.java */
    /* loaded from: classes.dex */
    public static final class O000000o implements Serializable, GenericArrayType {
        private static final long O00000Oo = 0;
        private final Type O000000o;

        public O000000o(Type type) {
            this.O000000o = O00000Oo.O00000o(type);
        }

        public boolean equals(Object obj) {
            return (obj instanceof GenericArrayType) && O00000Oo.O000000o((Type) this, (Type) ((GenericArrayType) obj));
        }

        @Override // java.lang.reflect.GenericArrayType
        public Type getGenericComponentType() {
            return this.O000000o;
        }

        public int hashCode() {
            return this.O000000o.hashCode();
        }

        public String toString() {
            return O00000Oo.O00000oo(this.O000000o) + "[]";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: $Gson$Types.java */
    /* renamed from: com.O000000o.O000000o.O00000Oo.O00000Oo$O00000Oo  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public static final class C0005O00000Oo implements Serializable, ParameterizedType {
        private static final long O00000o = 0;
        private final Type O000000o;
        private final Type O00000Oo;
        private final Type[] O00000o0;

        public C0005O00000Oo(Type type, Type type2, Type... typeArr) {
            int i = 0;
            if (type2 instanceof Class) {
                Class cls = (Class) type2;
                boolean z = true;
                com.O000000o.O000000o.O00000Oo.O000000o.O000000o(type != null || cls.getEnclosingClass() == null);
                if (type != null && cls.getEnclosingClass() == null) {
                    z = false;
                }
                com.O000000o.O000000o.O00000Oo.O000000o.O000000o(z);
            }
            this.O000000o = type == null ? null : O00000Oo.O00000o(type);
            this.O00000Oo = O00000Oo.O00000o(type2);
            this.O00000o0 = (Type[]) typeArr.clone();
            while (true) {
                Type[] typeArr2 = this.O00000o0;
                if (i >= typeArr2.length) {
                    return;
                }
                com.O000000o.O000000o.O00000Oo.O000000o.O000000o(typeArr2[i]);
                O00000Oo.O0000Oo0(this.O00000o0[i]);
                Type[] typeArr3 = this.O00000o0;
                typeArr3[i] = O00000Oo.O00000o(typeArr3[i]);
                i++;
            }
        }

        public boolean equals(Object obj) {
            return (obj instanceof ParameterizedType) && O00000Oo.O000000o((Type) this, (Type) ((ParameterizedType) obj));
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type[] getActualTypeArguments() {
            return (Type[]) this.O00000o0.clone();
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type getOwnerType() {
            return this.O000000o;
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type getRawType() {
            return this.O00000Oo;
        }

        public int hashCode() {
            return (Arrays.hashCode(this.O00000o0) ^ this.O00000Oo.hashCode()) ^ O00000Oo.O00000Oo((Object) this.O000000o);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder((this.O00000o0.length + 1) * 30);
            sb.append(O00000Oo.O00000oo(this.O00000Oo));
            if (this.O00000o0.length == 0) {
                return sb.toString();
            }
            sb.append("<").append(O00000Oo.O00000oo(this.O00000o0[0]));
            for (int i = 1; i < this.O00000o0.length; i++) {
                sb.append(", ").append(O00000Oo.O00000oo(this.O00000o0[i]));
            }
            return sb.append(">").toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: $Gson$Types.java */
    /* loaded from: classes.dex */
    public static final class O00000o0 implements Serializable, WildcardType {
        private static final long O00000o0 = 0;
        private final Type O000000o;
        private final Type O00000Oo;

        public O00000o0(Type[] typeArr, Type[] typeArr2) {
            Type O00000o;
            com.O000000o.O000000o.O00000Oo.O000000o.O000000o(typeArr2.length <= 1);
            com.O000000o.O000000o.O00000Oo.O000000o.O000000o(typeArr.length == 1);
            if (typeArr2.length == 1) {
                com.O000000o.O000000o.O00000Oo.O000000o.O000000o(typeArr2[0]);
                O00000Oo.O0000Oo0(typeArr2[0]);
                com.O000000o.O000000o.O00000Oo.O000000o.O000000o(typeArr[0] == Object.class);
                this.O00000Oo = O00000Oo.O00000o(typeArr2[0]);
                O00000o = Object.class;
            } else {
                com.O000000o.O000000o.O00000Oo.O000000o.O000000o(typeArr[0]);
                O00000Oo.O0000Oo0(typeArr[0]);
                this.O00000Oo = null;
                O00000o = O00000Oo.O00000o(typeArr[0]);
            }
            this.O000000o = O00000o;
        }

        public boolean equals(Object obj) {
            return (obj instanceof WildcardType) && O00000Oo.O000000o((Type) this, (Type) ((WildcardType) obj));
        }

        @Override // java.lang.reflect.WildcardType
        public Type[] getLowerBounds() {
            Type type = this.O00000Oo;
            return type != null ? new Type[]{type} : O00000Oo.O000000o;
        }

        @Override // java.lang.reflect.WildcardType
        public Type[] getUpperBounds() {
            return new Type[]{this.O000000o};
        }

        public int hashCode() {
            Type type = this.O00000Oo;
            return (type != null ? type.hashCode() + 31 : 1) ^ (this.O000000o.hashCode() + 31);
        }

        public String toString() {
            StringBuilder append;
            Type type;
            if (this.O00000Oo != null) {
                append = new StringBuilder().append("? super ");
                type = this.O00000Oo;
            } else if (this.O000000o == Object.class) {
                return "?";
            } else {
                append = new StringBuilder().append("? extends ");
                type = this.O000000o;
            }
            return append.append(O00000Oo.O00000oo(type)).toString();
        }
    }

    private O00000Oo() {
    }

    private static int O000000o(Object[] objArr, Object obj) {
        for (int i = 0; i < objArr.length; i++) {
            if (obj.equals(objArr[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    private static Class<?> O000000o(TypeVariable<?> typeVariable) {
        Object genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            return (Class) genericDeclaration;
        }
        return null;
    }

    public static GenericArrayType O000000o(Type type) {
        return new O000000o(type);
    }

    public static ParameterizedType O000000o(Type type, Type type2, Type... typeArr) {
        return new C0005O00000Oo(type, type2, typeArr);
    }

    public static Type O000000o(Type type, Class<?> cls) {
        Type O00000Oo = O00000Oo(type, cls, Collection.class);
        if (O00000Oo instanceof WildcardType) {
            O00000Oo = ((WildcardType) O00000Oo).getUpperBounds()[0];
        }
        return O00000Oo instanceof ParameterizedType ? ((ParameterizedType) O00000Oo).getActualTypeArguments()[0] : Object.class;
    }

    static Type O000000o(Type type, Class<?> cls, Class<?> cls2) {
        if (cls2 == cls) {
            return type;
        }
        if (cls2.isInterface()) {
            Class<?>[] interfaces = cls.getInterfaces();
            int length = interfaces.length;
            for (int i = 0; i < length; i++) {
                if (interfaces[i] == cls2) {
                    return cls.getGenericInterfaces()[i];
                }
                if (cls2.isAssignableFrom(interfaces[i])) {
                    return O000000o(cls.getGenericInterfaces()[i], interfaces[i], cls2);
                }
            }
        }
        if (!cls.isInterface()) {
            while (cls != Object.class) {
                Class<? super Object> superclass = cls.getSuperclass();
                if (superclass == cls2) {
                    return cls.getGenericSuperclass();
                }
                if (cls2.isAssignableFrom(superclass)) {
                    return O000000o(cls.getGenericSuperclass(), (Class<?>) superclass, cls2);
                }
                cls = superclass;
            }
        }
        return cls2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:0:?, code lost:
        r10 = r10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.reflect.Type O000000o(java.lang.reflect.Type r8, java.lang.Class<?> r9, java.lang.reflect.Type r10) {
        /*
        L0:
            boolean r0 = r10 instanceof java.lang.reflect.TypeVariable
            if (r0 == 0) goto Lf
            java.lang.reflect.TypeVariable r10 = (java.lang.reflect.TypeVariable) r10
            java.lang.reflect.Type r0 = O000000o(r8, r9, r10)
            if (r0 != r10) goto Ld
            return r0
        Ld:
            r10 = r0
            goto L0
        Lf:
            boolean r0 = r10 instanceof java.lang.Class
            if (r0 == 0) goto L2c
            r0 = r10
            java.lang.Class r0 = (java.lang.Class) r0
            boolean r1 = r0.isArray()
            if (r1 == 0) goto L2c
            java.lang.Class r10 = r0.getComponentType()
            java.lang.reflect.Type r8 = O000000o(r8, r9, r10)
            if (r10 != r8) goto L27
            goto L2b
        L27:
            java.lang.reflect.GenericArrayType r0 = O000000o(r8)
        L2b:
            return r0
        L2c:
            boolean r0 = r10 instanceof java.lang.reflect.GenericArrayType
            if (r0 == 0) goto L42
            java.lang.reflect.GenericArrayType r10 = (java.lang.reflect.GenericArrayType) r10
            java.lang.reflect.Type r0 = r10.getGenericComponentType()
            java.lang.reflect.Type r8 = O000000o(r8, r9, r0)
            if (r0 != r8) goto L3d
            goto L41
        L3d:
            java.lang.reflect.GenericArrayType r10 = O000000o(r8)
        L41:
            return r10
        L42:
            boolean r0 = r10 instanceof java.lang.reflect.ParameterizedType
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L82
            java.lang.reflect.ParameterizedType r10 = (java.lang.reflect.ParameterizedType) r10
            java.lang.reflect.Type r0 = r10.getOwnerType()
            java.lang.reflect.Type r3 = O000000o(r8, r9, r0)
            if (r3 == r0) goto L56
            r0 = r1
            goto L57
        L56:
            r0 = r2
        L57:
            java.lang.reflect.Type[] r4 = r10.getActualTypeArguments()
            int r5 = r4.length
        L5c:
            if (r2 >= r5) goto L77
            r6 = r4[r2]
            java.lang.reflect.Type r6 = O000000o(r8, r9, r6)
            r7 = r4[r2]
            if (r6 == r7) goto L74
            if (r0 != 0) goto L72
            java.lang.Object r0 = r4.clone()
            r4 = r0
            java.lang.reflect.Type[] r4 = (java.lang.reflect.Type[]) r4
            r0 = r1
        L72:
            r4[r2] = r6
        L74:
            int r2 = r2 + 1
            goto L5c
        L77:
            if (r0 == 0) goto L81
            java.lang.reflect.Type r8 = r10.getRawType()
            java.lang.reflect.ParameterizedType r10 = O000000o(r3, r8, r4)
        L81:
            return r10
        L82:
            boolean r0 = r10 instanceof java.lang.reflect.WildcardType
            if (r0 == 0) goto Lb4
            java.lang.reflect.WildcardType r10 = (java.lang.reflect.WildcardType) r10
            java.lang.reflect.Type[] r0 = r10.getLowerBounds()
            java.lang.reflect.Type[] r3 = r10.getUpperBounds()
            int r4 = r0.length
            if (r4 != r1) goto La2
            r1 = r0[r2]
            java.lang.reflect.Type r8 = O000000o(r8, r9, r1)
            r9 = r0[r2]
            if (r8 == r9) goto Lb4
            java.lang.reflect.WildcardType r8 = O00000o0(r8)
            return r8
        La2:
            int r0 = r3.length
            if (r0 != r1) goto Lb4
            r0 = r3[r2]
            java.lang.reflect.Type r8 = O000000o(r8, r9, r0)
            r9 = r3[r2]
            if (r8 == r9) goto Lb4
            java.lang.reflect.WildcardType r8 = O00000Oo(r8)
            return r8
        Lb4:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.O000000o.O000000o.O00000Oo.O00000Oo.O000000o(java.lang.reflect.Type, java.lang.Class, java.lang.reflect.Type):java.lang.reflect.Type");
    }

    static Type O000000o(Type type, Class<?> cls, TypeVariable<?> typeVariable) {
        Class<?> O000000o2 = O000000o(typeVariable);
        if (O000000o2 == null) {
            return typeVariable;
        }
        Type O000000o3 = O000000o(type, cls, O000000o2);
        if (O000000o3 instanceof ParameterizedType) {
            return ((ParameterizedType) O000000o3).getActualTypeArguments()[O000000o((Object[]) O000000o2.getTypeParameters(), (Object) typeVariable)];
        }
        return typeVariable;
    }

    static boolean O000000o(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static boolean O000000o(Type type, Type type2) {
        if (type == type2) {
            return true;
        }
        if (type instanceof Class) {
            return type.equals(type2);
        }
        if (type instanceof ParameterizedType) {
            if (type2 instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                ParameterizedType parameterizedType2 = (ParameterizedType) type2;
                return O000000o((Object) parameterizedType.getOwnerType(), (Object) parameterizedType2.getOwnerType()) && parameterizedType.getRawType().equals(parameterizedType2.getRawType()) && Arrays.equals(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments());
            }
            return false;
        } else if (type instanceof GenericArrayType) {
            if (type2 instanceof GenericArrayType) {
                return O000000o(((GenericArrayType) type).getGenericComponentType(), ((GenericArrayType) type2).getGenericComponentType());
            }
            return false;
        } else if (type instanceof WildcardType) {
            if (type2 instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) type;
                WildcardType wildcardType2 = (WildcardType) type2;
                return Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds()) && Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds());
            }
            return false;
        } else if ((type instanceof TypeVariable) && (type2 instanceof TypeVariable)) {
            TypeVariable typeVariable = (TypeVariable) type;
            TypeVariable typeVariable2 = (TypeVariable) type2;
            return typeVariable.getGenericDeclaration() == typeVariable2.getGenericDeclaration() && typeVariable.getName().equals(typeVariable2.getName());
        } else {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int O00000Oo(Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    static Type O00000Oo(Type type, Class<?> cls, Class<?> cls2) {
        com.O000000o.O000000o.O00000Oo.O000000o.O000000o(cls2.isAssignableFrom(cls));
        return O000000o(type, cls, O000000o(type, cls, cls2));
    }

    public static WildcardType O00000Oo(Type type) {
        return new O00000o0(new Type[]{type}, O000000o);
    }

    public static Type[] O00000Oo(Type type, Class<?> cls) {
        if (type == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        Type O00000Oo = O00000Oo(type, cls, Map.class);
        return O00000Oo instanceof ParameterizedType ? ((ParameterizedType) O00000Oo).getActualTypeArguments() : new Type[]{Object.class, Object.class};
    }

    public static Type O00000o(Type type) {
        if (type instanceof Class) {
            Class cls = (Class) type;
            return cls.isArray() ? new O000000o(O00000o(cls.getComponentType())) : cls;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return new C0005O00000Oo(parameterizedType.getOwnerType(), parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            return new O000000o(((GenericArrayType) type).getGenericComponentType());
        } else {
            if (type instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) type;
                return new O00000o0(wildcardType.getUpperBounds(), wildcardType.getLowerBounds());
            }
            return type;
        }
    }

    public static WildcardType O00000o0(Type type) {
        return new O00000o0(new Type[]{Object.class}, new Type[]{type});
    }

    public static Class<?> O00000oO(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            com.O000000o.O000000o.O00000Oo.O000000o.O000000o(rawType instanceof Class);
            return (Class) rawType;
        } else if (type instanceof GenericArrayType) {
            return Array.newInstance(O00000oO(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        } else {
            if (type instanceof TypeVariable) {
                return Object.class;
            }
            if (type instanceof WildcardType) {
                return O00000oO(((WildcardType) type).getUpperBounds()[0]);
            }
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + (type == null ? "null" : type.getClass().getName()));
        }
    }

    public static String O00000oo(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    public static Type O0000O0o(Type type) {
        return type instanceof GenericArrayType ? ((GenericArrayType) type).getGenericComponentType() : ((Class) type).getComponentType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void O0000Oo0(Type type) {
        com.O000000o.O000000o.O00000Oo.O000000o.O000000o(((type instanceof Class) && ((Class) type).isPrimitive()) ? false : true);
    }
}
