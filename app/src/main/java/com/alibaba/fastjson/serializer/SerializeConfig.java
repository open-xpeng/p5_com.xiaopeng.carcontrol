package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.util.IdentityHashMap;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class SerializeConfig {
    public static final SerializeConfig globalInstance = new SerializeConfig();
    public PropertyNamingStrategy propertyNamingStrategy;
    private final IdentityHashMap<ObjectSerializer> serializers;
    protected String typeKey = JSON.DEFAULT_TYPE_KEY;

    public static final SerializeConfig getGlobalInstance() {
        return globalInstance;
    }

    public ObjectSerializer registerIfNotExists(Class<?> cls) {
        return registerIfNotExists(cls, cls.getModifiers(), false, true, true, true);
    }

    public ObjectSerializer registerIfNotExists(Class<?> cls, int i, boolean z, boolean z2, boolean z3, boolean z4) {
        ObjectSerializer objectSerializer = this.serializers.get(cls);
        if (objectSerializer == null) {
            JavaBeanSerializer javaBeanSerializer = new JavaBeanSerializer(cls, i, null, z, z2, z3, z4, this.propertyNamingStrategy);
            this.serializers.put(cls, javaBeanSerializer);
            return javaBeanSerializer;
        }
        return objectSerializer;
    }

    public SerializeConfig() {
        IdentityHashMap<ObjectSerializer> identityHashMap = new IdentityHashMap<>(1024);
        this.serializers = identityHashMap;
        identityHashMap.put(Boolean.class, BooleanCodec.instance);
        identityHashMap.put(Character.class, MiscCodec.instance);
        identityHashMap.put(Byte.class, IntegerCodec.instance);
        identityHashMap.put(Short.class, IntegerCodec.instance);
        identityHashMap.put(Integer.class, IntegerCodec.instance);
        identityHashMap.put(Long.class, IntegerCodec.instance);
        identityHashMap.put(Float.class, NumberCodec.instance);
        identityHashMap.put(Double.class, NumberCodec.instance);
        identityHashMap.put(Number.class, NumberCodec.instance);
        identityHashMap.put(BigDecimal.class, BigDecimalCodec.instance);
        identityHashMap.put(BigInteger.class, BigDecimalCodec.instance);
        identityHashMap.put(String.class, StringCodec.instance);
        identityHashMap.put(Object[].class, ArrayCodec.instance);
        identityHashMap.put(Class.class, MiscCodec.instance);
        identityHashMap.put(SimpleDateFormat.class, MiscCodec.instance);
        identityHashMap.put(Locale.class, MiscCodec.instance);
        identityHashMap.put(Currency.class, MiscCodec.instance);
        identityHashMap.put(TimeZone.class, MiscCodec.instance);
        identityHashMap.put(UUID.class, MiscCodec.instance);
        identityHashMap.put(URI.class, MiscCodec.instance);
        identityHashMap.put(URL.class, MiscCodec.instance);
        identityHashMap.put(Pattern.class, MiscCodec.instance);
        identityHashMap.put(Charset.class, MiscCodec.instance);
    }

    public ObjectSerializer get(Class<?> cls) {
        Class<? super Object> superclass;
        boolean z;
        ObjectSerializer objectSerializer = this.serializers.get(cls);
        if (objectSerializer == null) {
            if (Map.class.isAssignableFrom(cls)) {
                this.serializers.put(cls, new MapSerializer());
            } else if (List.class.isAssignableFrom(cls)) {
                this.serializers.put(cls, new ListSerializer());
            } else if (Collection.class.isAssignableFrom(cls)) {
                this.serializers.put(cls, CollectionCodec.instance);
            } else if (Date.class.isAssignableFrom(cls)) {
                this.serializers.put(cls, DateCodec.instance);
            } else if (JSONAware.class.isAssignableFrom(cls)) {
                this.serializers.put(cls, MiscCodec.instance);
            } else if (JSONSerializable.class.isAssignableFrom(cls)) {
                this.serializers.put(cls, MiscCodec.instance);
            } else if (JSONStreamAware.class.isAssignableFrom(cls)) {
                this.serializers.put(cls, MiscCodec.instance);
            } else if (cls.isEnum() || ((superclass = cls.getSuperclass()) != null && superclass != Object.class && superclass.isEnum())) {
                this.serializers.put(cls, new EnumSerializer());
            } else if (cls.isArray()) {
                Class<?> componentType = cls.getComponentType();
                this.serializers.put(cls, new ArraySerializer(componentType, get(componentType)));
            } else if (Throwable.class.isAssignableFrom(cls)) {
                JavaBeanSerializer javaBeanSerializer = new JavaBeanSerializer(cls, this.propertyNamingStrategy);
                javaBeanSerializer.features |= SerializerFeature.WriteClassName.mask;
                this.serializers.put(cls, javaBeanSerializer);
            } else if (TimeZone.class.isAssignableFrom(cls)) {
                this.serializers.put(cls, MiscCodec.instance);
            } else if (Charset.class.isAssignableFrom(cls)) {
                this.serializers.put(cls, MiscCodec.instance);
            } else if (Enumeration.class.isAssignableFrom(cls)) {
                this.serializers.put(cls, MiscCodec.instance);
            } else if (Calendar.class.isAssignableFrom(cls)) {
                this.serializers.put(cls, DateCodec.instance);
            } else {
                Class<?>[] interfaces = cls.getInterfaces();
                int length = interfaces.length;
                boolean z2 = false;
                int i = 0;
                while (true) {
                    z = true;
                    if (i >= length) {
                        z = false;
                        break;
                    }
                    Class<?> cls2 = interfaces[i];
                    if (cls2.getName().equals("net.sf.cglib.proxy.Factory") || cls2.getName().equals("org.springframework.cglib.proxy.Factory")) {
                        break;
                    } else if (cls2.getName().equals("javassist.util.proxy.ProxyObject")) {
                        break;
                    } else {
                        i++;
                    }
                }
                z = false;
                z2 = true;
                if (z2 || z) {
                    ObjectSerializer objectSerializer2 = get(cls.getSuperclass());
                    this.serializers.put(cls, objectSerializer2);
                    return objectSerializer2;
                }
                this.serializers.put(cls, new JavaBeanSerializer(cls, this.propertyNamingStrategy));
            }
            return this.serializers.get(cls);
        }
        return objectSerializer;
    }

    public boolean put(Type type, ObjectSerializer objectSerializer) {
        return this.serializers.put(type, objectSerializer);
    }

    public String getTypeKey() {
        return this.typeKey;
    }

    public void setTypeKey(String str) {
        this.typeKey = str;
    }
}
