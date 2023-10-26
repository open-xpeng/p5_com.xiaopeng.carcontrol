package com.alibaba.fastjson.parser;

/* loaded from: classes.dex */
public class ThrowableDeserializer extends JavaBeanDeserializer {
    public ThrowableDeserializer(ParserConfig parserConfig, Class<?> cls) {
        super(parserConfig, cls, cls);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0031, code lost:
        if (java.lang.Throwable.class.isAssignableFrom(r1) != false) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00e3, code lost:
        if (r1 != null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00e5, code lost:
        r0 = (T) new java.lang.Exception(r10, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00ec, code lost:
        r0 = r1.getConstructors();
        r1 = r0.length;
        r3 = null;
        r4 = null;
        r6 = null;
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00f6, code lost:
        if (r2 >= r1) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00f8, code lost:
        r12 = r0[r2];
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00ff, code lost:
        if (r12.getParameterTypes().length != 0) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0101, code lost:
        r6 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0108, code lost:
        if (r12.getParameterTypes().length != 1) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0112, code lost:
        if (r12.getParameterTypes()[0] != java.lang.String.class) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0114, code lost:
        r4 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x011c, code lost:
        if (r12.getParameterTypes().length != 2) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0126, code lost:
        if (r12.getParameterTypes()[0] != java.lang.String.class) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0130, code lost:
        if (r12.getParameterTypes()[1] != java.lang.Throwable.class) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0132, code lost:
        r3 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0133, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0136, code lost:
        if (r3 == null) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0138, code lost:
        r5 = (java.lang.Throwable) r3.newInstance(r10, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0147, code lost:
        if (r4 == null) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0149, code lost:
        r5 = (java.lang.Throwable) r4.newInstance(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0155, code lost:
        if (r6 == null) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0157, code lost:
        r5 = (java.lang.Throwable) r6.newInstance(new java.lang.Object[0]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0160, code lost:
        if (r5 != null) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0162, code lost:
        r0 = (T) new java.lang.Exception(r10, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0168, code lost:
        r0 = (T) r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0169, code lost:
        if (r11 == null) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x016b, code lost:
        ((java.lang.Throwable) r0).setStackTrace(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x016e, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x016f, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0177, code lost:
        throw new com.alibaba.fastjson.JSONException("create instance error", r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x00e0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0061 A[SYNTHETIC] */
    @Override // com.alibaba.fastjson.parser.JavaBeanDeserializer, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r17, java.lang.reflect.Type r18, java.lang.Object r19) {
        /*
            Method dump skipped, instructions count: 384
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.ThrowableDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }
}
