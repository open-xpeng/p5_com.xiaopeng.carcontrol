package com.alibaba.fastjson.util;

import com.alibaba.fastjson.annotation.JSONField;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* loaded from: classes.dex */
public class FieldInfo implements Comparable<FieldInfo> {
    public final Class<?> declaringClass;
    public final Field field;
    public final boolean fieldAccess;
    private final JSONField fieldAnnotation;
    public final Class<?> fieldClass;
    public final boolean fieldTransient;
    public final Type fieldType;
    public final String format;
    public final boolean getOnly;
    public final boolean isEnum;
    public final Method method;
    private final JSONField methodAnnotation;
    public final String name;
    public final char[] name_chars;
    private int ordinal;

    public FieldInfo(String str, Class<?> cls, Class<?> cls2, Type type, Field field, int i, int i2) {
        this.ordinal = 0;
        this.name = str;
        this.declaringClass = cls;
        this.fieldClass = cls2;
        this.fieldType = type;
        this.method = null;
        this.field = field;
        this.ordinal = i;
        this.isEnum = cls2.isEnum();
        this.fieldAnnotation = null;
        this.methodAnnotation = null;
        if (field != null) {
            int modifiers = field.getModifiers();
            int i3 = modifiers & 1;
            this.fieldAccess = true;
            this.fieldTransient = Modifier.isTransient(modifiers);
        } else {
            this.fieldAccess = false;
            this.fieldTransient = false;
        }
        this.getOnly = false;
        int length = str.length();
        char[] cArr = new char[length + 3];
        this.name_chars = cArr;
        str.getChars(0, str.length(), cArr, 1);
        cArr[0] = '\"';
        cArr[length + 1] = '\"';
        cArr[length + 2] = ':';
        this.format = null;
    }

    public FieldInfo(String str, Method method, Field field, Class<?> cls, Type type, int i, int i2, JSONField jSONField, JSONField jSONField2, boolean z) {
        Class<?> type2;
        Type type3;
        Type inheritGenericType;
        boolean z2 = false;
        this.ordinal = 0;
        this.name = str;
        this.method = method;
        this.field = field;
        this.ordinal = i;
        this.methodAnnotation = jSONField;
        this.fieldAnnotation = jSONField2;
        JSONField annotation = getAnnotation();
        String str2 = null;
        if (annotation != null) {
            String format = annotation.format();
            if (format.trim().length() != 0) {
                str2 = format;
            }
        }
        this.format = str2;
        if (field != null) {
            int modifiers = field.getModifiers();
            this.fieldAccess = method == null || ((modifiers & 1) != 0 && method.getReturnType() == field.getType());
            this.fieldTransient = (modifiers & 128) != 0;
        } else {
            this.fieldAccess = false;
            this.fieldTransient = false;
        }
        int length = str.length();
        char[] cArr = new char[length + 3];
        this.name_chars = cArr;
        str.getChars(0, str.length(), cArr, 1);
        cArr[0] = '\"';
        cArr[length + 1] = '\"';
        cArr[length + 2] = ':';
        if (method != null) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 1) {
                type2 = parameterTypes[0];
                type3 = (type2 == Class.class || type2 == String.class || type2.isPrimitive() || !z) ? type2 : method.getGenericParameterTypes()[0];
                this.getOnly = false;
            } else {
                type2 = method.getReturnType();
                type3 = (type2 != Class.class && z) ? method.getGenericReturnType() : type2;
                this.getOnly = true;
            }
            this.declaringClass = method.getDeclaringClass();
        } else {
            type2 = field.getType();
            Type genericType = (type2.isPrimitive() || type2 == String.class || type2.isEnum() || !z) ? type2 : field.getGenericType();
            this.declaringClass = field.getDeclaringClass();
            this.getOnly = Modifier.isFinal(field.getModifiers());
            type3 = genericType;
        }
        if (cls != null && type2 == Object.class && (type3 instanceof TypeVariable) && (inheritGenericType = getInheritGenericType(cls, (TypeVariable) type3)) != null) {
            this.fieldClass = TypeUtils.getClass(inheritGenericType);
            this.fieldType = inheritGenericType;
            this.isEnum = type2.isEnum();
            return;
        }
        if (!(type3 instanceof Class)) {
            Type fieldType = getFieldType(cls, type == null ? cls : type, type3);
            if (fieldType != type3) {
                if (fieldType instanceof ParameterizedType) {
                    type2 = TypeUtils.getClass(fieldType);
                } else if (fieldType instanceof Class) {
                    type2 = TypeUtils.getClass(fieldType);
                }
            }
            type3 = fieldType;
        }
        this.fieldType = type3;
        this.fieldClass = type2;
        if (!type2.isArray() && type2.isEnum()) {
            z2 = true;
        }
        this.isEnum = z2;
    }

    public static Type getFieldType(Class<?> cls, Type type, Type type2) {
        TypeVariable<Class<? super Object>>[] typeVariableArr;
        ParameterizedType parameterizedType;
        if (cls != null && type != null) {
            if (type2 instanceof GenericArrayType) {
                Type genericComponentType = ((GenericArrayType) type2).getGenericComponentType();
                Type fieldType = getFieldType(cls, type, genericComponentType);
                return genericComponentType != fieldType ? Array.newInstance(TypeUtils.getClass(fieldType), 0).getClass() : type2;
            } else if (!TypeUtils.isGenericParamType(type)) {
                return type2;
            } else {
                if (type2 instanceof TypeVariable) {
                    ParameterizedType parameterizedType2 = (ParameterizedType) TypeUtils.getGenericParamType(type);
                    Class<?> cls2 = TypeUtils.getClass(parameterizedType2);
                    TypeVariable typeVariable = (TypeVariable) type2;
                    for (int i = 0; i < cls2.getTypeParameters().length; i++) {
                        if (cls2.getTypeParameters()[i].getName().equals(typeVariable.getName())) {
                            return parameterizedType2.getActualTypeArguments()[i];
                        }
                    }
                }
                if (type2 instanceof ParameterizedType) {
                    ParameterizedType parameterizedType3 = (ParameterizedType) type2;
                    Type[] actualTypeArguments = parameterizedType3.getActualTypeArguments();
                    Type[] typeArr = null;
                    if (type instanceof ParameterizedType) {
                        parameterizedType = (ParameterizedType) type;
                        typeVariableArr = cls.getTypeParameters();
                    } else if (cls.getGenericSuperclass() instanceof ParameterizedType) {
                        parameterizedType = (ParameterizedType) cls.getGenericSuperclass();
                        typeVariableArr = cls.getSuperclass().getTypeParameters();
                    } else {
                        typeVariableArr = null;
                        parameterizedType = null;
                    }
                    boolean z = false;
                    for (int i2 = 0; i2 < actualTypeArguments.length && parameterizedType != null; i2++) {
                        Type type3 = actualTypeArguments[i2];
                        if (type3 instanceof TypeVariable) {
                            TypeVariable typeVariable2 = (TypeVariable) type3;
                            for (int i3 = 0; i3 < typeVariableArr.length; i3++) {
                                if (typeVariableArr[i3].getName().equals(typeVariable2.getName())) {
                                    if (typeArr == null) {
                                        typeArr = parameterizedType.getActualTypeArguments();
                                    }
                                    actualTypeArguments[i2] = typeArr[i3];
                                    z = true;
                                }
                            }
                        }
                    }
                    if (z) {
                        return new ParameterizedTypeImpl(actualTypeArguments, parameterizedType3.getOwnerType(), parameterizedType3.getRawType());
                    }
                }
            }
        }
        return type2;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.reflect.GenericDeclaration] */
    public static Type getInheritGenericType(Class<?> cls, TypeVariable<?> typeVariable) {
        ?? genericDeclaration = typeVariable.getGenericDeclaration();
        while (true) {
            Type genericSuperclass = cls.getGenericSuperclass();
            if (genericSuperclass == null) {
                return null;
            }
            if (genericSuperclass instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                if (parameterizedType.getRawType() == genericDeclaration) {
                    TypeVariable<?>[] typeParameters = genericDeclaration.getTypeParameters();
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    for (int i = 0; i < typeParameters.length; i++) {
                        if (typeParameters[i] == typeVariable) {
                            return actualTypeArguments[i];
                        }
                    }
                    return null;
                }
            }
            Class<?> cls2 = TypeUtils.getClass(genericSuperclass);
            if (genericSuperclass == null) {
                return null;
            }
            cls = cls2;
        }
    }

    public String toString() {
        return this.name;
    }

    @Override // java.lang.Comparable
    public int compareTo(FieldInfo fieldInfo) {
        int i = this.ordinal;
        int i2 = fieldInfo.ordinal;
        if (i < i2) {
            return -1;
        }
        if (i > i2) {
            return 1;
        }
        return this.name.compareTo(fieldInfo.name);
    }

    public boolean equals(FieldInfo fieldInfo) {
        return fieldInfo == this || compareTo(fieldInfo) == 0;
    }

    public JSONField getAnnotation() {
        JSONField jSONField = this.fieldAnnotation;
        return jSONField != null ? jSONField : this.methodAnnotation;
    }

    public Object get(Object obj) throws IllegalAccessException, InvocationTargetException {
        if (this.fieldAccess) {
            return this.field.get(obj);
        }
        return this.method.invoke(obj, new Object[0]);
    }

    public void set(Object obj, Object obj2) throws IllegalAccessException, InvocationTargetException {
        Method method = this.method;
        if (method != null) {
            method.invoke(obj, obj2);
        } else {
            this.field.set(obj, obj2);
        }
    }
}
