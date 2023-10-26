package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ListTypeFieldDeserializer extends FieldDeserializer {
    private final boolean array;
    private ObjectDeserializer deserializer;
    private final Type itemType;

    public ListTypeFieldDeserializer(ParserConfig parserConfig, Class<?> cls, FieldInfo fieldInfo) {
        super(cls, fieldInfo, 14);
        Type type = fieldInfo.fieldType;
        Class<?> cls2 = fieldInfo.fieldClass;
        if (type instanceof ParameterizedType) {
            this.itemType = ((ParameterizedType) type).getActualTypeArguments()[0];
            this.array = false;
        } else if (cls2.isArray()) {
            this.itemType = cls2.getComponentType();
            this.array = true;
        } else {
            this.itemType = Object.class;
            this.array = false;
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.FieldDeserializer
    public void parseField(DefaultJSONParser defaultJSONParser, Object obj, Type type, Map<String, Object> map) {
        JSONArray arrayList;
        JSONArray jSONArray;
        if (defaultJSONParser.lexer.token == 8) {
            setValue(obj, (Object) null);
            defaultJSONParser.lexer.nextToken();
            return;
        }
        if (this.array) {
            JSONArray jSONArray2 = new JSONArray();
            jSONArray2.setComponentType(this.itemType);
            jSONArray = jSONArray2;
            arrayList = jSONArray2;
        } else {
            arrayList = new ArrayList();
            jSONArray = null;
        }
        ParseContext parseContext = defaultJSONParser.contex;
        defaultJSONParser.setContext(parseContext, obj, this.fieldInfo.name);
        parseArray(defaultJSONParser, type, arrayList);
        defaultJSONParser.setContext(parseContext);
        Object obj2 = arrayList;
        if (this.array) {
            Object array = arrayList.toArray((Object[]) Array.newInstance((Class) this.itemType, arrayList.size()));
            jSONArray.setRelatedArray(array);
            obj2 = array;
        }
        if (obj == null) {
            map.put(this.fieldInfo.name, obj2);
        } else {
            setValue(obj, obj2);
        }
    }

    final void parseArray(DefaultJSONParser defaultJSONParser, Type type, Collection collection) {
        Class cls;
        int i;
        int i2;
        Type type2 = this.itemType;
        ObjectDeserializer objectDeserializer = this.deserializer;
        int i3 = 0;
        if (type instanceof ParameterizedType) {
            if (type2 instanceof TypeVariable) {
                TypeVariable typeVariable = (TypeVariable) type2;
                ParameterizedType parameterizedType = (ParameterizedType) type;
                cls = parameterizedType.getRawType() instanceof Class ? (Class) parameterizedType.getRawType() : null;
                if (cls != null) {
                    int length = cls.getTypeParameters().length;
                    i2 = 0;
                    while (i2 < length) {
                        if (cls.getTypeParameters()[i2].getName().equals(typeVariable.getName())) {
                            break;
                        }
                        i2++;
                    }
                }
                i2 = -1;
                if (i2 != -1) {
                    type2 = parameterizedType.getActualTypeArguments()[i2];
                    if (!type2.equals(this.itemType)) {
                        objectDeserializer = defaultJSONParser.config.getDeserializer(type2);
                    }
                }
            } else if (type2 instanceof ParameterizedType) {
                ParameterizedType parameterizedType2 = (ParameterizedType) type2;
                Type[] actualTypeArguments = parameterizedType2.getActualTypeArguments();
                if (actualTypeArguments.length == 1 && (actualTypeArguments[0] instanceof TypeVariable)) {
                    TypeVariable typeVariable2 = (TypeVariable) actualTypeArguments[0];
                    ParameterizedType parameterizedType3 = (ParameterizedType) type;
                    cls = parameterizedType3.getRawType() instanceof Class ? (Class) parameterizedType3.getRawType() : null;
                    if (cls != null) {
                        int length2 = cls.getTypeParameters().length;
                        i = 0;
                        while (i < length2) {
                            if (cls.getTypeParameters()[i].getName().equals(typeVariable2.getName())) {
                                break;
                            }
                            i++;
                        }
                    }
                    i = -1;
                    if (i != -1) {
                        i3 = 0;
                        actualTypeArguments[0] = parameterizedType3.getActualTypeArguments()[i];
                        type2 = new ParameterizedTypeImpl(actualTypeArguments, parameterizedType2.getOwnerType(), parameterizedType2.getRawType());
                    } else {
                        i3 = 0;
                    }
                }
            }
        }
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token != 14) {
            String str = "exepct '[', but " + JSONToken.name(jSONLexer.token);
            if (type != null) {
                str = str + ", type : " + type;
            }
            throw new JSONException(str);
        }
        if (objectDeserializer == null) {
            objectDeserializer = defaultJSONParser.config.getDeserializer(type2);
            this.deserializer = objectDeserializer;
        }
        char c = jSONLexer.ch;
        if (c == '[') {
            int i4 = jSONLexer.bp + 1;
            jSONLexer.bp = i4;
            jSONLexer.ch = i4 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i4);
            jSONLexer.token = 14;
        } else if (c == '{') {
            int i5 = jSONLexer.bp + 1;
            jSONLexer.bp = i5;
            jSONLexer.ch = i5 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i5);
            jSONLexer.token = 12;
        } else if (c == '\"') {
            jSONLexer.scanString();
        } else if (c == ']') {
            int i6 = jSONLexer.bp + 1;
            jSONLexer.bp = i6;
            jSONLexer.ch = i6 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i6);
            jSONLexer.token = 15;
        } else {
            jSONLexer.nextToken();
        }
        while (true) {
            if (jSONLexer.token == 16 && (jSONLexer.features & Feature.AllowArbitraryCommas.mask) != 0) {
                jSONLexer.nextToken();
            } else if (jSONLexer.token == 15) {
                break;
            } else {
                collection.add(objectDeserializer.deserialze(defaultJSONParser, type2, Integer.valueOf(i3)));
                if (defaultJSONParser.resolveStatus == 1) {
                    defaultJSONParser.checkListResolve(collection);
                }
                if (jSONLexer.token == 16) {
                    char c2 = jSONLexer.ch;
                    if (c2 == '[') {
                        int i7 = jSONLexer.bp + 1;
                        jSONLexer.bp = i7;
                        jSONLexer.ch = i7 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i7);
                        jSONLexer.token = 14;
                    } else if (c2 == '{') {
                        int i8 = jSONLexer.bp + 1;
                        jSONLexer.bp = i8;
                        jSONLexer.ch = i8 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i8);
                        jSONLexer.token = 12;
                    } else if (c2 == '\"') {
                        jSONLexer.scanString();
                    } else {
                        jSONLexer.nextToken();
                    }
                }
                i3++;
            }
        }
        if (jSONLexer.ch == ',') {
            int i9 = jSONLexer.bp + 1;
            jSONLexer.bp = i9;
            jSONLexer.ch = i9 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i9);
            jSONLexer.token = 16;
            return;
        }
        jSONLexer.nextToken();
    }
}
