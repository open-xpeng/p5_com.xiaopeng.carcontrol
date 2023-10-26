package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.util.FieldInfo;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Collection;

/* loaded from: classes.dex */
public final class FieldSerializer implements Comparable<FieldSerializer> {
    protected final int features;
    public final FieldInfo fieldInfo;
    protected final String format;
    private RuntimeSerializerInfo runtimeInfo;
    protected final boolean writeNull;

    public FieldSerializer(FieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
        JSONField annotation = fieldInfo.getAnnotation();
        boolean z = false;
        if (annotation != null) {
            boolean z2 = false;
            for (SerializerFeature serializerFeature : annotation.serialzeFeatures()) {
                if (serializerFeature == SerializerFeature.WriteMapNullValue) {
                    z2 = true;
                }
            }
            String trim = annotation.format().trim();
            r0 = trim.length() != 0 ? trim : null;
            this.features = SerializerFeature.of(annotation.serialzeFeatures());
            z = z2;
        } else {
            this.features = 0;
        }
        this.writeNull = z;
        this.format = r0;
    }

    public void writePrefix(JSONSerializer jSONSerializer) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        int i = serializeWriter.features;
        if ((SerializerFeature.QuoteFieldNames.mask & i) != 0) {
            if ((i & SerializerFeature.UseSingleQuotes.mask) != 0) {
                serializeWriter.writeFieldName(this.fieldInfo.name, true);
                return;
            } else {
                serializeWriter.write(this.fieldInfo.name_chars, 0, this.fieldInfo.name_chars.length);
                return;
            }
        }
        serializeWriter.writeFieldName(this.fieldInfo.name, true);
    }

    public Object getPropertyValue(Object obj) throws Exception {
        try {
            return this.fieldInfo.get(obj);
        } catch (Exception e) {
            Member member = this.fieldInfo.method != null ? this.fieldInfo.method : this.fieldInfo.field;
            throw new JSONException("get property error。 " + (member.getDeclaringClass().getName() + "." + member.getName()), e);
        }
    }

    public void writeValue(JSONSerializer jSONSerializer, Object obj) throws Exception {
        Class<?> cls;
        String str = this.format;
        if (str != null) {
            jSONSerializer.writeWithFormat(obj, str);
            return;
        }
        if (this.runtimeInfo == null) {
            if (obj == null) {
                cls = this.fieldInfo.fieldClass;
            } else {
                cls = obj.getClass();
            }
            this.runtimeInfo = new RuntimeSerializerInfo(jSONSerializer.config.get(cls), cls);
        }
        RuntimeSerializerInfo runtimeSerializerInfo = this.runtimeInfo;
        if (obj == null) {
            if ((this.features & SerializerFeature.WriteNullNumberAsZero.mask) != 0 && Number.class.isAssignableFrom(runtimeSerializerInfo.runtimeFieldClass)) {
                jSONSerializer.out.write(48);
                return;
            } else if ((this.features & SerializerFeature.WriteNullBooleanAsFalse.mask) != 0 && Boolean.class == runtimeSerializerInfo.runtimeFieldClass) {
                jSONSerializer.out.write(OOBEEvent.STRING_FALSE);
                return;
            } else if ((this.features & SerializerFeature.WriteNullListAsEmpty.mask) != 0 && Collection.class.isAssignableFrom(runtimeSerializerInfo.runtimeFieldClass)) {
                jSONSerializer.out.write("[]");
                return;
            } else {
                runtimeSerializerInfo.fieldSerializer.write(jSONSerializer, null, this.fieldInfo.name, runtimeSerializerInfo.runtimeFieldClass);
                return;
            }
        }
        Class<?> cls2 = obj.getClass();
        if (cls2 == runtimeSerializerInfo.runtimeFieldClass) {
            runtimeSerializerInfo.fieldSerializer.write(jSONSerializer, obj, this.fieldInfo.name, this.fieldInfo.fieldType);
        } else {
            jSONSerializer.config.get(cls2).write(jSONSerializer, obj, this.fieldInfo.name, this.fieldInfo.fieldType);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class RuntimeSerializerInfo {
        ObjectSerializer fieldSerializer;
        Class<?> runtimeFieldClass;

        public RuntimeSerializerInfo(ObjectSerializer objectSerializer, Class<?> cls) {
            this.fieldSerializer = objectSerializer;
            this.runtimeFieldClass = cls;
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(FieldSerializer fieldSerializer) {
        return this.fieldInfo.compareTo(fieldSerializer.fieldInfo);
    }
}
