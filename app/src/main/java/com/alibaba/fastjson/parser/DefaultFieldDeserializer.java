package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.FieldInfo;

/* loaded from: classes.dex */
public class DefaultFieldDeserializer extends FieldDeserializer {
    protected ObjectDeserializer fieldValueDeserilizer;

    public DefaultFieldDeserializer(ParserConfig parserConfig, Class<?> cls, FieldInfo fieldInfo) {
        super(cls, fieldInfo, 2);
    }

    public ObjectDeserializer getFieldValueDeserilizer(ParserConfig parserConfig) {
        if (this.fieldValueDeserilizer == null) {
            this.fieldValueDeserilizer = parserConfig.getDeserializer(this.fieldInfo.fieldClass, this.fieldInfo.fieldType);
        }
        return this.fieldValueDeserilizer;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0058  */
    @Override // com.alibaba.fastjson.parser.deserializer.FieldDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parseField(com.alibaba.fastjson.parser.DefaultJSONParser r4, java.lang.Object r5, java.lang.reflect.Type r6, java.util.Map<java.lang.String, java.lang.Object> r7) {
        /*
            r3 = this;
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r0 = r3.fieldValueDeserilizer
            if (r0 != 0) goto L14
            com.alibaba.fastjson.parser.ParserConfig r0 = r4.config
            com.alibaba.fastjson.util.FieldInfo r1 = r3.fieldInfo
            java.lang.Class<?> r1 = r1.fieldClass
            com.alibaba.fastjson.util.FieldInfo r2 = r3.fieldInfo
            java.lang.reflect.Type r2 = r2.fieldType
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r0 = r0.getDeserializer(r1, r2)
            r3.fieldValueDeserilizer = r0
        L14:
            boolean r0 = r6 instanceof java.lang.reflect.ParameterizedType
            if (r0 == 0) goto L1c
            com.alibaba.fastjson.parser.ParseContext r0 = r4.contex
            r0.type = r6
        L1c:
            com.alibaba.fastjson.util.FieldInfo r6 = r3.fieldInfo
            java.lang.String r6 = r6.format
            if (r6 == 0) goto L37
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r0 = r3.fieldValueDeserilizer
            boolean r1 = r0 instanceof com.alibaba.fastjson.serializer.DateCodec
            if (r1 == 0) goto L37
            com.alibaba.fastjson.serializer.DateCodec r0 = (com.alibaba.fastjson.serializer.DateCodec) r0
            com.alibaba.fastjson.util.FieldInfo r1 = r3.fieldInfo
            java.lang.reflect.Type r1 = r1.fieldType
            com.alibaba.fastjson.util.FieldInfo r2 = r3.fieldInfo
            java.lang.String r2 = r2.name
            java.lang.Object r6 = r0.deserialze(r4, r1, r2, r6)
            goto L45
        L37:
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r6 = r3.fieldValueDeserilizer
            com.alibaba.fastjson.util.FieldInfo r0 = r3.fieldInfo
            java.lang.reflect.Type r0 = r0.fieldType
            com.alibaba.fastjson.util.FieldInfo r1 = r3.fieldInfo
            java.lang.String r1 = r1.name
            java.lang.Object r6 = r6.deserialze(r4, r0, r1)
        L45:
            int r0 = r4.resolveStatus
            r1 = 1
            if (r0 != r1) goto L58
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r5 = r4.getLastResolveTask()
            r5.fieldDeserializer = r3
            com.alibaba.fastjson.parser.ParseContext r6 = r4.contex
            r5.ownerContext = r6
            r5 = 0
            r4.resolveStatus = r5
            goto L7c
        L58:
            if (r5 != 0) goto L62
            com.alibaba.fastjson.util.FieldInfo r4 = r3.fieldInfo
            java.lang.String r4 = r4.name
            r7.put(r4, r6)
            goto L7c
        L62:
            if (r6 != 0) goto L79
            com.alibaba.fastjson.util.FieldInfo r4 = r3.fieldInfo
            java.lang.Class<?> r4 = r4.fieldClass
            java.lang.Class r7 = java.lang.Byte.TYPE
            if (r4 == r7) goto L78
            java.lang.Class r7 = java.lang.Short.TYPE
            if (r4 == r7) goto L78
            java.lang.Class r7 = java.lang.Float.TYPE
            if (r4 == r7) goto L78
            java.lang.Class r7 = java.lang.Double.TYPE
            if (r4 != r7) goto L79
        L78:
            return
        L79:
            r3.setValue(r5, r6)
        L7c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultFieldDeserializer.parseField(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.Object, java.lang.reflect.Type, java.util.Map):void");
    }
}
