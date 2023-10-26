package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class MiscCodec implements ObjectSerializer, ObjectDeserializer {
    public static final MiscCodec instance = new MiscCodec();

    private MiscCodec() {
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            if (type == Character.TYPE || type == Character.class) {
                jSONSerializer.write("");
            } else if ((serializeWriter.features & SerializerFeature.WriteNullListAsEmpty.mask) != 0 && Enumeration.class.isAssignableFrom(TypeUtils.getClass(type))) {
                serializeWriter.write("[]");
            } else {
                serializeWriter.writeNull();
            }
        } else if (obj instanceof Pattern) {
            jSONSerializer.write(((Pattern) obj).pattern());
        } else if (obj instanceof TimeZone) {
            jSONSerializer.write(((TimeZone) obj).getID());
        } else if (obj instanceof Currency) {
            jSONSerializer.write(((Currency) obj).getCurrencyCode());
        } else if (obj instanceof Class) {
            jSONSerializer.write(((Class) obj).getName());
        } else if (obj instanceof Character) {
            Character ch = (Character) obj;
            if (ch.charValue() == 0) {
                jSONSerializer.write("\u0000");
            } else {
                jSONSerializer.write(ch.toString());
            }
        } else {
            int i = 0;
            if (obj instanceof SimpleDateFormat) {
                String pattern = ((SimpleDateFormat) obj).toPattern();
                if ((serializeWriter.features & SerializerFeature.WriteClassName.mask) != 0 && obj.getClass() != type) {
                    serializeWriter.write(123);
                    serializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY, false);
                    jSONSerializer.write(obj.getClass().getName());
                    serializeWriter.write(44);
                    serializeWriter.writeFieldName("val", false);
                    serializeWriter.writeString(pattern);
                    serializeWriter.write(125);
                    return;
                }
                serializeWriter.writeString(pattern);
            } else if (obj instanceof JSONStreamAware) {
                ((JSONStreamAware) obj).writeJSONString(jSONSerializer.out);
            } else if (obj instanceof JSONAware) {
                serializeWriter.write(((JSONAware) obj).toJSONString());
            } else if (obj instanceof JSONSerializable) {
                ((JSONSerializable) obj).write(jSONSerializer, obj2, type);
            } else if (obj instanceof Enumeration) {
                Type type2 = null;
                if ((serializeWriter.features & SerializerFeature.WriteClassName.mask) != 0 && (type instanceof ParameterizedType)) {
                    type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
                }
                Enumeration enumeration = (Enumeration) obj;
                SerialContext serialContext = jSONSerializer.context;
                jSONSerializer.setContext(serialContext, obj, obj2, 0);
                try {
                    serializeWriter.write(91);
                    while (enumeration.hasMoreElements()) {
                        Object nextElement = enumeration.nextElement();
                        int i2 = i + 1;
                        if (i != 0) {
                            serializeWriter.write(44);
                        }
                        if (nextElement == null) {
                            serializeWriter.writeNull();
                        } else {
                            jSONSerializer.config.get(nextElement.getClass()).write(jSONSerializer, nextElement, Integer.valueOf(i2 - 1), type2);
                        }
                        i = i2;
                    }
                    serializeWriter.write(93);
                } finally {
                    jSONSerializer.context = serialContext;
                }
            } else {
                jSONSerializer.write(obj.toString());
            }
        }
    }

    /* JADX WARN: Type inference failed for: r7v3, types: [T, java.text.SimpleDateFormat] */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        Object parse;
        if (type == StackTraceElement.class) {
            return (T) parseStackTraceElement(defaultJSONParser);
        }
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (defaultJSONParser.resolveStatus == 2) {
            defaultJSONParser.resolveStatus = 0;
            defaultJSONParser.accept(16);
            if (jSONLexer.token() == 4) {
                if (!"val".equals(jSONLexer.stringVal())) {
                    throw new JSONException("syntax error");
                }
                jSONLexer.nextToken();
                defaultJSONParser.accept(17);
                parse = defaultJSONParser.parse();
                defaultJSONParser.accept(13);
            } else {
                throw new JSONException("syntax error");
            }
        } else {
            parse = defaultJSONParser.parse();
        }
        if (parse == null) {
            return null;
        }
        if (parse instanceof String) {
            String str = (String) parse;
            if (str.length() == 0) {
                return null;
            }
            if (type == UUID.class) {
                return (T) UUID.fromString(str);
            }
            if (type == Class.class) {
                return (T) TypeUtils.loadClass(str, defaultJSONParser.config.defaultClassLoader);
            }
            if (type == Locale.class) {
                String[] split = str.split("_");
                if (split.length == 1) {
                    return (T) new Locale(split[0]);
                }
                if (split.length == 2) {
                    return (T) new Locale(split[0], split[1]);
                }
                return (T) new Locale(split[0], split[1], split[2]);
            } else if (type == URI.class) {
                return (T) URI.create(str);
            } else {
                if (type == URL.class) {
                    try {
                        return (T) new URL(str);
                    } catch (MalformedURLException e) {
                        throw new JSONException("create url error", e);
                    }
                } else if (type == Pattern.class) {
                    return (T) Pattern.compile(str);
                } else {
                    if (type == Charset.class) {
                        return (T) Charset.forName(str);
                    }
                    if (type == Currency.class) {
                        return (T) Currency.getInstance(str);
                    }
                    if (type == SimpleDateFormat.class) {
                        ?? r7 = (T) new SimpleDateFormat(str, defaultJSONParser.lexer.locale);
                        r7.setTimeZone(defaultJSONParser.lexer.timeZone);
                        return r7;
                    } else if (type == Character.TYPE || type == Character.class) {
                        return (T) TypeUtils.castToChar(str);
                    } else {
                        return (T) TimeZone.getTimeZone(str);
                    }
                }
            }
        }
        throw new JSONException("except string value");
    }

    /* JADX WARN: Code restructure failed: missing block: B:92:0x0176, code lost:
        return (T) new java.lang.StackTraceElement(r5, r7, r8, r9);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected <T> T parseStackTraceElement(com.alibaba.fastjson.parser.DefaultJSONParser r17) {
        /*
            Method dump skipped, instructions count: 404
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.MiscCodec.parseStackTraceElement(com.alibaba.fastjson.parser.DefaultJSONParser):java.lang.Object");
    }
}
