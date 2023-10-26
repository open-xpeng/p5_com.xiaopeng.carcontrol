package com.O000000o.O000000o;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

/* compiled from: FieldAttributes.java */
/* loaded from: classes.dex */
public final class O00000o0 {
    private final Field O000000o;

    public O00000o0(Field field) {
        com.O000000o.O000000o.O00000Oo.O000000o.O000000o(field);
        this.O000000o = field;
    }

    public Class<?> O000000o() {
        return this.O000000o.getDeclaringClass();
    }

    Object O000000o(Object obj) throws IllegalAccessException {
        return this.O000000o.get(obj);
    }

    public <T extends Annotation> T O000000o(Class<T> cls) {
        return (T) this.O000000o.getAnnotation(cls);
    }

    public boolean O000000o(int i) {
        return (i & this.O000000o.getModifiers()) != 0;
    }

    public String O00000Oo() {
        return this.O000000o.getName();
    }

    public Class<?> O00000o() {
        return this.O000000o.getType();
    }

    public Type O00000o0() {
        return this.O000000o.getGenericType();
    }

    public Collection<Annotation> O00000oO() {
        return Arrays.asList(this.O000000o.getAnnotations());
    }

    boolean O00000oo() {
        return this.O000000o.isSynthetic();
    }
}
