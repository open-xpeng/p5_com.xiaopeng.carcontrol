package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class FieldDeserializer {
    public final Class<?> clazz;
    public final FieldInfo fieldInfo;

    public abstract void parseField(DefaultJSONParser defaultJSONParser, Object obj, Type type, Map<String, Object> map);

    public FieldDeserializer(Class<?> cls, FieldInfo fieldInfo, int i) {
        this.clazz = cls;
        this.fieldInfo = fieldInfo;
    }

    public void setValue(Object obj, int i) throws IllegalAccessException {
        this.fieldInfo.field.setInt(obj, i);
    }

    public void setValue(Object obj, long j) throws IllegalAccessException {
        this.fieldInfo.field.setLong(obj, j);
    }

    public void setValue(Object obj, float f) throws IllegalAccessException {
        this.fieldInfo.field.setFloat(obj, f);
    }

    public void setValue(Object obj, double d) throws IllegalAccessException {
        this.fieldInfo.field.setDouble(obj, d);
    }

    public void setValue(Object obj, Object obj2) {
        if (obj2 == null && this.fieldInfo.fieldClass.isPrimitive()) {
            return;
        }
        Field field = this.fieldInfo.field;
        Method method = this.fieldInfo.method;
        try {
            if (this.fieldInfo.fieldAccess) {
                if (this.fieldInfo.getOnly) {
                    if (Map.class.isAssignableFrom(this.fieldInfo.fieldClass)) {
                        Map map = (Map) field.get(obj);
                        if (map != null) {
                            map.putAll((Map) obj2);
                            return;
                        }
                        return;
                    }
                    Collection collection = (Collection) field.get(obj);
                    if (collection != null) {
                        collection.addAll((Collection) obj2);
                        return;
                    }
                    return;
                }
                field.set(obj, obj2);
            } else if (this.fieldInfo.getOnly) {
                if (Map.class.isAssignableFrom(this.fieldInfo.fieldClass)) {
                    Map map2 = (Map) method.invoke(obj, new Object[0]);
                    if (map2 != null) {
                        map2.putAll((Map) obj2);
                        return;
                    }
                    return;
                }
                Collection collection2 = (Collection) method.invoke(obj, new Object[0]);
                if (collection2 != null) {
                    collection2.addAll((Collection) obj2);
                }
            } else {
                method.invoke(obj, obj2);
            }
        } catch (Exception e) {
            throw new JSONException("set property error, " + this.fieldInfo.name, e);
        }
    }
}
