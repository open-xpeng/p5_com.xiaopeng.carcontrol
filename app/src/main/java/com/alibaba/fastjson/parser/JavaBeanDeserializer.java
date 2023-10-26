package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.eclipse.paho.android.service.MqttServiceConstants;

/* loaded from: classes.dex */
public class JavaBeanDeserializer implements ObjectDeserializer {
    public final JavaBeanInfo beanInfo;
    private final Class<?> clazz;
    private final FieldDeserializer[] fieldDeserializers;
    private final FieldDeserializer[] sortedFieldDeserializers;

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls, Type type) {
        this(parserConfig, cls, type, JavaBeanInfo.build(cls, cls.getModifiers(), type, false, true, true, true, parserConfig.propertyNamingStrategy));
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls, Type type, JavaBeanInfo javaBeanInfo) {
        this.clazz = cls;
        this.beanInfo = javaBeanInfo;
        this.sortedFieldDeserializers = new FieldDeserializer[javaBeanInfo.sortedFields.length];
        int length = javaBeanInfo.sortedFields.length;
        for (int i = 0; i < length; i++) {
            this.sortedFieldDeserializers[i] = parserConfig.createFieldDeserializer(parserConfig, cls, javaBeanInfo.sortedFields[i]);
        }
        this.fieldDeserializers = new FieldDeserializer[javaBeanInfo.fields.length];
        int length2 = javaBeanInfo.fields.length;
        for (int i2 = 0; i2 < length2; i2++) {
            this.fieldDeserializers[i2] = getFieldDeserializer(javaBeanInfo.fields[i2].name);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Object createInstance(DefaultJSONParser defaultJSONParser, Type type) {
        FieldInfo[] fieldInfoArr;
        if ((type instanceof Class) && this.clazz.isInterface()) {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{(Class) type}, new JSONObject((defaultJSONParser.lexer.features & Feature.OrderedField.mask) != 0));
        } else if (this.beanInfo.defaultConstructor == null) {
            return null;
        } else {
            try {
                Constructor<?> constructor = this.beanInfo.defaultConstructor;
                Object newInstance = this.beanInfo.defaultConstructorParameterSize == 0 ? constructor.newInstance(new Object[0]) : constructor.newInstance(defaultJSONParser.contex.object);
                if (defaultJSONParser != null && (defaultJSONParser.lexer.features & Feature.InitStringFieldAsEmpty.mask) != 0) {
                    for (FieldInfo fieldInfo : this.beanInfo.fields) {
                        if (fieldInfo.fieldClass == String.class) {
                            fieldInfo.set(newInstance, "");
                        }
                    }
                }
                return newInstance;
            } catch (Exception e) {
                throw new JSONException("create instance error, class " + this.clazz.getName(), e);
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, null);
    }

    private <T> T deserialzeArrayMapping(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        Enum r8;
        String str;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        T t = (T) createInstance(defaultJSONParser, type);
        int length = this.sortedFieldDeserializers.length;
        int i = 0;
        while (i < length) {
            boolean z = i == length + (-1) ? true : true;
            FieldDeserializer fieldDeserializer = this.sortedFieldDeserializers[i];
            FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
            Class<?> cls = fieldInfo.fieldClass;
            try {
                if (cls == Integer.TYPE) {
                    int scanLongValue = (int) jSONLexer.scanLongValue();
                    if (fieldInfo.fieldAccess) {
                        fieldInfo.field.setInt(t, scanLongValue);
                    } else {
                        fieldDeserializer.setValue(t, new Integer(scanLongValue));
                    }
                    if (jSONLexer.ch == ',') {
                        int i2 = jSONLexer.bp + 1;
                        jSONLexer.bp = i2;
                        jSONLexer.ch = i2 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i2);
                        jSONLexer.token = 16;
                    } else if (jSONLexer.ch == ']') {
                        int i3 = jSONLexer.bp + 1;
                        jSONLexer.bp = i3;
                        jSONLexer.ch = i3 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i3);
                        jSONLexer.token = 15;
                    } else {
                        jSONLexer.nextToken();
                    }
                } else if (cls == String.class) {
                    if (jSONLexer.ch == '\"') {
                        str = jSONLexer.scanStringValue('\"');
                    } else if (jSONLexer.ch == 'n' && jSONLexer.text.startsWith("null", jSONLexer.bp)) {
                        jSONLexer.bp += 4;
                        jSONLexer.ch = jSONLexer.bp >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(jSONLexer.bp);
                        str = null;
                    } else {
                        throw new JSONException("not match string. feild : " + obj);
                    }
                    if (fieldInfo.fieldAccess) {
                        fieldInfo.field.set(t, str);
                    } else {
                        fieldDeserializer.setValue(t, str);
                    }
                    if (jSONLexer.ch == ',') {
                        int i4 = jSONLexer.bp + 1;
                        jSONLexer.bp = i4;
                        jSONLexer.ch = i4 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i4);
                        jSONLexer.token = 16;
                    } else if (jSONLexer.ch == ']') {
                        int i5 = jSONLexer.bp + 1;
                        jSONLexer.bp = i5;
                        jSONLexer.ch = i5 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i5);
                        jSONLexer.token = 15;
                    } else {
                        jSONLexer.nextToken();
                    }
                } else {
                    if (cls == Long.TYPE) {
                        long scanLongValue2 = jSONLexer.scanLongValue();
                        if (fieldInfo.fieldAccess) {
                            fieldInfo.field.setLong(t, scanLongValue2);
                        } else {
                            fieldDeserializer.setValue(t, new Long(scanLongValue2));
                        }
                        if (jSONLexer.ch == ',') {
                            int i6 = jSONLexer.bp + 1;
                            jSONLexer.bp = i6;
                            jSONLexer.ch = i6 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i6);
                            jSONLexer.token = 16;
                        } else if (jSONLexer.ch == ']') {
                            int i7 = jSONLexer.bp + 1;
                            jSONLexer.bp = i7;
                            jSONLexer.ch = i7 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i7);
                            jSONLexer.token = 15;
                        } else {
                            jSONLexer.nextToken();
                        }
                    } else if (cls == Boolean.TYPE) {
                        boolean scanBoolean = jSONLexer.scanBoolean();
                        if (fieldInfo.fieldAccess) {
                            fieldInfo.field.setBoolean(t, scanBoolean);
                        } else {
                            fieldDeserializer.setValue(t, Boolean.valueOf(scanBoolean));
                        }
                        if (jSONLexer.ch == ',') {
                            int i8 = jSONLexer.bp + 1;
                            jSONLexer.bp = i8;
                            jSONLexer.ch = i8 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i8);
                            jSONLexer.token = 16;
                        } else if (jSONLexer.ch == ']') {
                            int i9 = jSONLexer.bp + 1;
                            jSONLexer.bp = i9;
                            jSONLexer.ch = i9 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i9);
                            jSONLexer.token = 15;
                        } else {
                            jSONLexer.nextToken();
                        }
                    } else if (cls.isEnum()) {
                        char c = jSONLexer.ch;
                        if (c == '\"') {
                            String scanSymbol = jSONLexer.scanSymbol(defaultJSONParser.symbolTable);
                            r8 = scanSymbol == null ? null : Enum.valueOf(cls, scanSymbol);
                        } else if (c >= '0' && c <= '9') {
                            r8 = ((EnumDeserializer) ((DefaultFieldDeserializer) fieldDeserializer).getFieldValueDeserilizer(defaultJSONParser.config)).values[(int) jSONLexer.scanLongValue()];
                        } else {
                            throw new JSONException("illegal enum." + jSONLexer.info());
                        }
                        fieldDeserializer.setValue(t, r8);
                        if (jSONLexer.ch == ',') {
                            int i10 = jSONLexer.bp + 1;
                            jSONLexer.bp = i10;
                            jSONLexer.ch = i10 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i10);
                            jSONLexer.token = 16;
                        } else if (jSONLexer.ch == ']') {
                            int i11 = jSONLexer.bp + 1;
                            jSONLexer.bp = i11;
                            jSONLexer.ch = i11 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i11);
                            jSONLexer.token = 15;
                        } else {
                            jSONLexer.nextToken();
                        }
                    } else if (cls == Date.class && jSONLexer.ch == '1') {
                        fieldDeserializer.setValue(t, new Date(jSONLexer.scanLongValue()));
                        if (jSONLexer.ch == ',') {
                            int i12 = jSONLexer.bp + 1;
                            jSONLexer.bp = i12;
                            jSONLexer.ch = i12 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i12);
                            jSONLexer.token = 16;
                        } else if (jSONLexer.ch == ']') {
                            int i13 = jSONLexer.bp + 1;
                            jSONLexer.bp = i13;
                            jSONLexer.ch = i13 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i13);
                            jSONLexer.token = 15;
                        } else {
                            jSONLexer.nextToken();
                        }
                    } else {
                        if (jSONLexer.ch == '[') {
                            int i14 = jSONLexer.bp + 1;
                            jSONLexer.bp = i14;
                            jSONLexer.ch = i14 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i14);
                            jSONLexer.token = 14;
                        } else if (jSONLexer.ch == '{') {
                            int i15 = jSONLexer.bp + 1;
                            jSONLexer.bp = i15;
                            jSONLexer.ch = i15 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i15);
                            jSONLexer.token = 12;
                        } else {
                            jSONLexer.nextToken();
                        }
                        fieldDeserializer.parseField(defaultJSONParser, t, fieldInfo.fieldType, null);
                        if (z) {
                            if (jSONLexer.token != 15) {
                                throw new JSONException("syntax error");
                            }
                        } else if (z && jSONLexer.token != 16) {
                            throw new JSONException("syntax error");
                        }
                    }
                    i++;
                }
                i++;
            } catch (IllegalAccessException e) {
                throw new JSONException("set " + fieldInfo.name + MqttServiceConstants.TRACE_ERROR, e);
            }
        }
        if (jSONLexer.ch == ',') {
            int i16 = jSONLexer.bp + 1;
            jSONLexer.bp = i16;
            jSONLexer.ch = i16 >= jSONLexer.len ? JSONLexer.EOI : jSONLexer.text.charAt(i16);
            jSONLexer.token = 16;
        } else {
            jSONLexer.nextToken();
        }
        return t;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:(2:409|410)(1:62)|63|(4:65|(13:70|(7:75|(11:77|(11:339|(5:341|342|92|93|94)|343|344|345|(4:83|(2:85|(2:335|336)(2:87|(4:91|92|93|94)))(1:337)|101|(3:300|301|(4:303|(1:305)(2:315|(2:317|(1:319)(1:320))(2:321|(5:323|(2:326|324)|327|328|(1:330)(1:331))(1:332)))|306|(4:308|(1:310)|311|312)(2:313|314))(2:333|334))(2:103|(2:105|(2:107|(1:1)(5:111|(2:113|114)|92|93|94))(3:171|172|173))))(1:338)|(3:176|(1:178)|(1:180))|181|(5:(1:184)(3:(4:205|(4:210|(4:215|(4:220|(2:225|226)|231|226)|232|226)|233|226)|234|226)(2:235|(5:238|239|(4:244|(4:249|(4:254|(1:259)|260|(1:265)(1:264))|266|(1:271)(1:270))|272|(1:277)(1:276))|278|(1:283)(1:282))(1:237))|227|(2:229|230))|185|186|(1:188)(2:190|(2:201|202)(3:192|193|(1:1)))|189)(2:288|(2:290|(2:293|294)(2:292|189))(5:295|(3:297|298|299)|186|(0)(0)|189))|93|94)|79|80|81|(0)(0)|(3:176|(0)|(0))|181|(0)(0)|93|94)(4:346|(4:351|(4:356|(6:361|(2:373|(10:375|376|344|345|(0)(0)|(0)|181|(0)(0)|93|94))(9:365|(1:367)(2:369|(1:371)(1:372))|368|(0)(0)|(0)|181|(0)(0)|93|94)|342|92|93|94)|377|(10:379|380|80|81|(0)(0)|(0)|181|(0)(0)|93|94)(2:381|(5:383|342|92|93|94)))|385|(10:387|380|80|81|(0)(0)|(0)|181|(0)(0)|93|94)(2:388|(5:390|342|92|93|94)))|391|(10:393|79|80|81|(0)(0)|(0)|181|(0)(0)|93|94)(11:394|(5:396|342|92|93|94)|343|344|345|(0)(0)|(0)|181|(0)(0)|93|94))|95|96|(1:98)|99|100)|397|(2:399|(5:401|342|92|93|94))|380|80|81|(0)(0)|(0)|181|(0)(0)|93|94)|402|(8:404|81|(0)(0)|(0)|181|(0)(0)|93|94)(6:405|(8:407|345|(0)(0)|(0)|181|(0)(0)|93|94)|342|92|93|94))(1:408)|384|376|344|345|(0)(0)|(0)|181|(0)(0)|93|94) */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x0303, code lost:
        r3 = getSeeAlso(r31.config, r30.beanInfo, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x030b, code lost:
        if (r3 != null) goto L169;
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x030d, code lost:
        r12 = com.alibaba.fastjson.util.TypeUtils.loadClass(r2, r31.config.defaultClassLoader);
        r0 = com.alibaba.fastjson.util.TypeUtils.getClass(r32);
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x0319, code lost:
        if (r0 == null) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x031b, code lost:
        if (r12 == null) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x0321, code lost:
        if (r0.isAssignableFrom(r12) == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x032c, code lost:
        throw new com.alibaba.fastjson.JSONException("type not match");
     */
    /* JADX WARN: Code restructure failed: missing block: B:227:0x032d, code lost:
        r3 = r31.config.getDeserializer(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x0334, code lost:
        r12 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x0335, code lost:
        r0 = (T) r3.deserialze(r31, r12, r33);
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x0339, code lost:
        if (r6 == null) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x033b, code lost:
        r6.object = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x033d, code lost:
        r31.setContext(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:233:0x0340, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:346:0x04c9, code lost:
        r6 = r17;
        r1 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:387:0x05a6, code lost:
        throw new com.alibaba.fastjson.JSONException("syntax error, unexpect token " + com.alibaba.fastjson.parser.JSONToken.name(r10.token));
     */
    /* JADX WARN: Removed duplicated region for block: B:158:0x01e9 A[Catch: all -> 0x00d1, TryCatch #3 {all -> 0x00d1, blocks: (B:64:0x00c8, B:70:0x00e0, B:75:0x00ef, B:82:0x00fd, B:158:0x01e9, B:160:0x01f3, B:162:0x01fd, B:351:0x04d7, B:165:0x0208, B:171:0x021d, B:173:0x0225, B:175:0x0231, B:193:0x0280, B:195:0x0289, B:200:0x0299, B:201:0x02a0, B:176:0x0235, B:178:0x023d, B:180:0x0243, B:181:0x0246, B:182:0x0252, B:185:0x025b, B:187:0x025f, B:188:0x0262, B:190:0x0266, B:191:0x0269, B:192:0x0275, B:202:0x02a1, B:203:0x02bd, B:206:0x02c2, B:208:0x02ca, B:210:0x02d7, B:212:0x02e4, B:214:0x02ea, B:217:0x0303, B:219:0x030d, B:222:0x031d, B:225:0x0324, B:226:0x032c, B:227:0x032d, B:229:0x0335, B:234:0x0341, B:235:0x0349, B:239:0x0355, B:241:0x035b, B:243:0x0367, B:85:0x010a, B:89:0x0115, B:94:0x011f, B:99:0x0128, B:104:0x0131, B:106:0x0135, B:108:0x013f, B:110:0x0149, B:111:0x0151, B:116:0x0167, B:119:0x0172, B:122:0x017b, B:125:0x0180, B:128:0x0189, B:131:0x018e, B:134:0x019c, B:137:0x01a1, B:141:0x01af, B:144:0x01b4, B:148:0x01ca), top: B:403:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:236:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0353 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:241:0x035b A[Catch: all -> 0x00d1, TryCatch #3 {all -> 0x00d1, blocks: (B:64:0x00c8, B:70:0x00e0, B:75:0x00ef, B:82:0x00fd, B:158:0x01e9, B:160:0x01f3, B:162:0x01fd, B:351:0x04d7, B:165:0x0208, B:171:0x021d, B:173:0x0225, B:175:0x0231, B:193:0x0280, B:195:0x0289, B:200:0x0299, B:201:0x02a0, B:176:0x0235, B:178:0x023d, B:180:0x0243, B:181:0x0246, B:182:0x0252, B:185:0x025b, B:187:0x025f, B:188:0x0262, B:190:0x0266, B:191:0x0269, B:192:0x0275, B:202:0x02a1, B:203:0x02bd, B:206:0x02c2, B:208:0x02ca, B:210:0x02d7, B:212:0x02e4, B:214:0x02ea, B:217:0x0303, B:219:0x030d, B:222:0x031d, B:225:0x0324, B:226:0x032c, B:227:0x032d, B:229:0x0335, B:234:0x0341, B:235:0x0349, B:239:0x0355, B:241:0x035b, B:243:0x0367, B:85:0x010a, B:89:0x0115, B:94:0x011f, B:99:0x0128, B:104:0x0131, B:106:0x0135, B:108:0x013f, B:110:0x0149, B:111:0x0151, B:116:0x0167, B:119:0x0172, B:122:0x017b, B:125:0x0180, B:128:0x0189, B:131:0x018e, B:134:0x019c, B:137:0x01a1, B:141:0x01af, B:144:0x01b4, B:148:0x01ca), top: B:403:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0367 A[Catch: all -> 0x00d1, TRY_LEAVE, TryCatch #3 {all -> 0x00d1, blocks: (B:64:0x00c8, B:70:0x00e0, B:75:0x00ef, B:82:0x00fd, B:158:0x01e9, B:160:0x01f3, B:162:0x01fd, B:351:0x04d7, B:165:0x0208, B:171:0x021d, B:173:0x0225, B:175:0x0231, B:193:0x0280, B:195:0x0289, B:200:0x0299, B:201:0x02a0, B:176:0x0235, B:178:0x023d, B:180:0x0243, B:181:0x0246, B:182:0x0252, B:185:0x025b, B:187:0x025f, B:188:0x0262, B:190:0x0266, B:191:0x0269, B:192:0x0275, B:202:0x02a1, B:203:0x02bd, B:206:0x02c2, B:208:0x02ca, B:210:0x02d7, B:212:0x02e4, B:214:0x02ea, B:217:0x0303, B:219:0x030d, B:222:0x031d, B:225:0x0324, B:226:0x032c, B:227:0x032d, B:229:0x0335, B:234:0x0341, B:235:0x0349, B:239:0x0355, B:241:0x035b, B:243:0x0367, B:85:0x010a, B:89:0x0115, B:94:0x011f, B:99:0x0128, B:104:0x0131, B:106:0x0135, B:108:0x013f, B:110:0x0149, B:111:0x0151, B:116:0x0167, B:119:0x0172, B:122:0x017b, B:125:0x0180, B:128:0x0189, B:131:0x018e, B:134:0x019c, B:137:0x01a1, B:141:0x01af, B:144:0x01b4, B:148:0x01ca), top: B:403:0x00c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0374  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x0486 A[Catch: all -> 0x05b0, TryCatch #0 {all -> 0x05b0, blocks: (B:385:0x057b, B:247:0x0376, B:340:0x04b5, B:343:0x04c0, B:345:0x04c6, B:380:0x056d, B:382:0x0573, B:386:0x0587, B:387:0x05a6, B:250:0x0386, B:255:0x038f, B:260:0x0398, B:265:0x03a1, B:275:0x03c4, B:329:0x047c, B:271:0x03ad, B:272:0x03b5, B:273:0x03bb, B:274:0x03c0, B:279:0x03d3, B:284:0x03dd, B:289:0x03e6, B:294:0x03ef, B:299:0x03f8, B:300:0x03ff, B:302:0x0403, B:304:0x0407, B:305:0x040c, B:306:0x0416, B:308:0x041a, B:310:0x041e, B:311:0x0422, B:312:0x042b, B:314:0x042f, B:316:0x0433, B:317:0x0439, B:318:0x0443, B:320:0x0447, B:322:0x044b, B:323:0x0451, B:328:0x0477, B:326:0x045c, B:327:0x0476, B:332:0x0486, B:334:0x049f, B:336:0x04a5, B:338:0x04af, B:388:0x05a7, B:389:0x05af), top: B:400:0x057b, inners: #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:342:0x04bb  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x04c0 A[Catch: all -> 0x05b0, TryCatch #0 {all -> 0x05b0, blocks: (B:385:0x057b, B:247:0x0376, B:340:0x04b5, B:343:0x04c0, B:345:0x04c6, B:380:0x056d, B:382:0x0573, B:386:0x0587, B:387:0x05a6, B:250:0x0386, B:255:0x038f, B:260:0x0398, B:265:0x03a1, B:275:0x03c4, B:329:0x047c, B:271:0x03ad, B:272:0x03b5, B:273:0x03bb, B:274:0x03c0, B:279:0x03d3, B:284:0x03dd, B:289:0x03e6, B:294:0x03ef, B:299:0x03f8, B:300:0x03ff, B:302:0x0403, B:304:0x0407, B:305:0x040c, B:306:0x0416, B:308:0x041a, B:310:0x041e, B:311:0x0422, B:312:0x042b, B:314:0x042f, B:316:0x0433, B:317:0x0439, B:318:0x0443, B:320:0x0447, B:322:0x044b, B:323:0x0451, B:328:0x0477, B:326:0x045c, B:327:0x0476, B:332:0x0486, B:334:0x049f, B:336:0x04a5, B:338:0x04af, B:388:0x05a7, B:389:0x05af), top: B:400:0x057b, inners: #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x005f A[Catch: all -> 0x0040, TRY_LEAVE, TryCatch #2 {all -> 0x0040, blocks: (B:17:0x0030, B:19:0x0035, B:28:0x004a, B:30:0x0050, B:36:0x005f, B:42:0x006e, B:47:0x007a, B:49:0x0084, B:52:0x008b, B:54:0x00a3, B:55:0x00ac, B:56:0x00b5, B:60:0x00bb), top: B:402:0x002e }] */
    /* JADX WARN: Removed duplicated region for block: B:395:0x05bb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r31, java.lang.reflect.Type r32, java.lang.Object r33, java.lang.Object r34) {
        /*
            Method dump skipped, instructions count: 1478
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JavaBeanDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public FieldDeserializer getFieldDeserializer(String str) {
        if (str == null) {
            return null;
        }
        int i = 0;
        if (!this.beanInfo.ordered) {
            int length = this.sortedFieldDeserializers.length - 1;
            while (i <= length) {
                int i2 = (i + length) >>> 1;
                int compareTo = this.sortedFieldDeserializers[i2].fieldInfo.name.compareTo(str);
                if (compareTo < 0) {
                    i = i2 + 1;
                } else if (compareTo <= 0) {
                    return this.sortedFieldDeserializers[i2];
                } else {
                    length = i2 - 1;
                }
            }
            return null;
        }
        while (true) {
            FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
            if (i >= fieldDeserializerArr.length) {
                return null;
            }
            if (fieldDeserializerArr[i].fieldInfo.name.equalsIgnoreCase(str)) {
                return this.sortedFieldDeserializers[i];
            }
            i++;
        }
    }

    private boolean parseField(DefaultJSONParser defaultJSONParser, String str, Object obj, Type type, Map<String, Object> map) {
        FieldDeserializer[] fieldDeserializerArr;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        FieldDeserializer fieldDeserializer = getFieldDeserializer(str);
        if (fieldDeserializer == null) {
            boolean startsWith = str.startsWith("is");
            for (FieldDeserializer fieldDeserializer2 : this.sortedFieldDeserializers) {
                FieldInfo fieldInfo = fieldDeserializer2.fieldInfo;
                Class<?> cls = fieldInfo.fieldClass;
                String str2 = fieldInfo.name;
                if (str2.equalsIgnoreCase(str) || (startsWith && ((cls == Boolean.TYPE || cls == Boolean.class) && str2.equalsIgnoreCase(str.substring(2))))) {
                    fieldDeserializer = fieldDeserializer2;
                    break;
                }
            }
        }
        if (fieldDeserializer == null) {
            parseExtra(defaultJSONParser, obj, str);
            return false;
        }
        jSONLexer.nextTokenWithChar(':');
        fieldDeserializer.parseField(defaultJSONParser, obj, type, map);
        return true;
    }

    void parseExtra(DefaultJSONParser defaultJSONParser, Object obj, String str) {
        Object parseObject;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if ((defaultJSONParser.lexer.features & Feature.IgnoreNotMatch.mask) == 0) {
            throw new JSONException("setter not found, class " + this.clazz.getName() + ", property " + str);
        }
        jSONLexer.nextTokenWithChar(':');
        Type type = null;
        List<ExtraTypeProvider> list = defaultJSONParser.extraTypeProviders;
        if (list != null) {
            for (ExtraTypeProvider extraTypeProvider : list) {
                type = extraTypeProvider.getExtraType(obj, str);
            }
        }
        if (type == null) {
            parseObject = defaultJSONParser.parse();
        } else {
            parseObject = defaultJSONParser.parseObject(type);
        }
        if (obj instanceof ExtraProcessable) {
            ((ExtraProcessable) obj).processExtra(str, parseObject);
            return;
        }
        List<ExtraProcessor> list2 = defaultJSONParser.extraProcessors;
        if (list2 != null) {
            for (ExtraProcessor extraProcessor : list2) {
                extraProcessor.processExtra(obj, str, parseObject);
            }
        }
    }

    public Object createInstance(Map<String, Object> map, ParserConfig parserConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.beanInfo.creatorConstructor == null) {
            Object createInstance = createInstance((DefaultJSONParser) null, this.clazz);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                FieldDeserializer fieldDeserializer = getFieldDeserializer(entry.getKey());
                if (fieldDeserializer != null) {
                    Object value = entry.getValue();
                    Method method = fieldDeserializer.fieldInfo.method;
                    if (method != null) {
                        method.invoke(createInstance, TypeUtils.cast(value, method.getGenericParameterTypes()[0], parserConfig));
                    } else {
                        fieldDeserializer.fieldInfo.field.set(createInstance, TypeUtils.cast(value, fieldDeserializer.fieldInfo.fieldType, parserConfig));
                    }
                }
            }
            return createInstance;
        }
        FieldInfo[] fieldInfoArr = this.beanInfo.fields;
        int length = fieldInfoArr.length;
        Object[] objArr = new Object[length];
        for (int i = 0; i < length; i++) {
            objArr[i] = map.get(fieldInfoArr[i].name);
        }
        if (this.beanInfo.creatorConstructor != null) {
            try {
                return this.beanInfo.creatorConstructor.newInstance(objArr);
            } catch (Exception e) {
                throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e);
            }
        }
        return null;
    }

    protected JavaBeanDeserializer getSeeAlso(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo, String str) {
        if (javaBeanInfo.jsonType == null) {
            return null;
        }
        for (Class<?> cls : javaBeanInfo.jsonType.seeAlso()) {
            ObjectDeserializer deserializer = parserConfig.getDeserializer(cls);
            if (deserializer instanceof JavaBeanDeserializer) {
                JavaBeanDeserializer javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
                JavaBeanInfo javaBeanInfo2 = javaBeanDeserializer.beanInfo;
                if (javaBeanInfo2.typeName.equals(str)) {
                    return javaBeanDeserializer;
                }
                JavaBeanDeserializer seeAlso = getSeeAlso(parserConfig, javaBeanInfo2, str);
                if (seeAlso != null) {
                    return seeAlso;
                }
            }
        }
        return null;
    }
}
