package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

/* loaded from: classes.dex */
public class CollectionCodec implements ObjectSerializer, ObjectDeserializer {
    public static final CollectionCodec instance = new CollectionCodec();

    private CollectionCodec() {
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            if ((serializeWriter.features & SerializerFeature.WriteNullListAsEmpty.mask) != 0) {
                serializeWriter.write("[]");
                return;
            } else {
                serializeWriter.writeNull();
                return;
            }
        }
        Type type2 = null;
        int i = 0;
        if ((serializeWriter.features & SerializerFeature.WriteClassName.mask) != 0 && (type instanceof ParameterizedType)) {
            type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        Collection collection = (Collection) obj;
        SerialContext serialContext = jSONSerializer.context;
        jSONSerializer.setContext(serialContext, obj, obj2, 0);
        if ((serializeWriter.features & SerializerFeature.WriteClassName.mask) != 0) {
            if (HashSet.class == collection.getClass()) {
                serializeWriter.append((CharSequence) "Set");
            } else if (TreeSet.class == collection.getClass()) {
                serializeWriter.append((CharSequence) "TreeSet");
            }
        }
        try {
            serializeWriter.write(91);
            for (Object obj3 : collection) {
                int i2 = i + 1;
                if (i != 0) {
                    serializeWriter.write(44);
                }
                if (obj3 == null) {
                    serializeWriter.writeNull();
                } else {
                    Class<?> cls = obj3.getClass();
                    if (cls == Integer.class) {
                        serializeWriter.writeInt(((Integer) obj3).intValue());
                    } else if (cls == Long.class) {
                        serializeWriter.writeLong(((Long) obj3).longValue());
                        if ((serializeWriter.features & SerializerFeature.WriteClassName.mask) != 0) {
                            serializeWriter.write(76);
                        }
                    } else {
                        jSONSerializer.config.get(cls).write(jSONSerializer, obj3, Integer.valueOf(i2 - 1), type2);
                    }
                }
                i = i2;
            }
            serializeWriter.write(93);
        } finally {
            jSONSerializer.context = serialContext;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v20, types: [java.util.Collection] */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.alibaba.fastjson.parser.DefaultJSONParser] */
    /* JADX WARN: Type inference failed for: r7v16, types: [com.alibaba.fastjson.JSONArray, T, java.util.Collection] */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        T t;
        Class cls;
        Class<Object> cls2;
        Type type2 = null;
        if (defaultJSONParser.lexer.token() == 8) {
            defaultJSONParser.lexer.nextToken(16);
            return null;
        } else if (type == JSONArray.class) {
            ?? r7 = (T) new JSONArray();
            defaultJSONParser.parseArray(r7);
            return r7;
        } else {
            Type type3 = type;
            while (!(type3 instanceof Class)) {
                if (type3 instanceof ParameterizedType) {
                    type3 = ((ParameterizedType) type3).getRawType();
                } else {
                    throw new JSONException("TODO");
                }
            }
            Class cls3 = (Class) type3;
            if (cls3 == AbstractCollection.class || cls3 == Collection.class) {
                t = (T) new ArrayList();
            } else if (cls3.isAssignableFrom(HashSet.class)) {
                t = (T) new HashSet();
            } else if (cls3.isAssignableFrom(LinkedHashSet.class)) {
                t = (T) new LinkedHashSet();
            } else if (cls3.isAssignableFrom(TreeSet.class)) {
                t = (T) new TreeSet();
            } else if (cls3.isAssignableFrom(ArrayList.class)) {
                t = (T) new ArrayList();
            } else if (cls3.isAssignableFrom(EnumSet.class)) {
                if (type instanceof ParameterizedType) {
                    cls2 = ((ParameterizedType) type).getActualTypeArguments()[0];
                } else {
                    cls2 = Object.class;
                }
                t = (T) EnumSet.noneOf(cls2);
            } else {
                try {
                    t = (Collection) cls3.newInstance();
                } catch (Exception unused) {
                    throw new JSONException("create instane error, class " + cls3.getName());
                }
            }
            if (type instanceof ParameterizedType) {
                cls = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                if (type instanceof Class) {
                    Class cls4 = (Class) type;
                    if (!cls4.getName().startsWith("java.")) {
                        Type genericSuperclass = cls4.getGenericSuperclass();
                        if (genericSuperclass instanceof ParameterizedType) {
                            type2 = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
                        }
                    }
                }
                cls = type2 == null ? Object.class : type2;
            }
            defaultJSONParser.parseArray(cls, (Collection) t, obj);
            return t;
        }
    }
}
