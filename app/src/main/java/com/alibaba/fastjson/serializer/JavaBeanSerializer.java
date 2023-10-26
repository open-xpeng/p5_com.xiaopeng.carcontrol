package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class JavaBeanSerializer implements ObjectSerializer {
    protected int features;
    private final FieldSerializer[] getters;
    private final FieldSerializer[] sortedGetters;
    protected String typeName;
    private static final char[] true_chars = {'t', 'r', 'u', 'e'};
    private static final char[] false_chars = {'f', 'a', 'l', 's', 'e'};

    public JavaBeanSerializer(Class<?> cls) {
        this(cls, (PropertyNamingStrategy) null);
    }

    public JavaBeanSerializer(Class<?> cls, PropertyNamingStrategy propertyNamingStrategy) {
        this(cls, cls.getModifiers(), null, false, true, true, true, propertyNamingStrategy);
    }

    public JavaBeanSerializer(Class<?> cls, String... strArr) {
        this(cls, cls.getModifiers(), map(strArr), false, true, true, true, null);
    }

    private static Map<String, String> map(String... strArr) {
        HashMap hashMap = new HashMap();
        for (String str : strArr) {
            hashMap.put(str, str);
        }
        return hashMap;
    }

    public JavaBeanSerializer(Class<?> cls, int i, Map<String, String> map, boolean z, boolean z2, boolean z3, boolean z4, PropertyNamingStrategy propertyNamingStrategy) {
        this.features = 0;
        JSONType jSONType = z2 ? (JSONType) cls.getAnnotation(JSONType.class) : null;
        if (jSONType != null) {
            this.features = SerializerFeature.of(jSONType.serialzeFeatures());
            String typeName = jSONType.typeName();
            this.typeName = typeName;
            if (typeName.length() == 0) {
                this.typeName = null;
            }
        }
        List<FieldInfo> computeGetters = TypeUtils.computeGetters(cls, i, z, jSONType, map, false, z3, z4, propertyNamingStrategy);
        ArrayList arrayList = new ArrayList();
        for (FieldInfo fieldInfo : computeGetters) {
            arrayList.add(new FieldSerializer(fieldInfo));
        }
        FieldSerializer[] fieldSerializerArr = (FieldSerializer[]) arrayList.toArray(new FieldSerializer[arrayList.size()]);
        this.getters = fieldSerializerArr;
        String[] orders = jSONType != null ? jSONType.orders() : null;
        if (orders != null && orders.length != 0) {
            List<FieldInfo> computeGetters2 = TypeUtils.computeGetters(cls, i, z, jSONType, map, true, z3, z4, propertyNamingStrategy);
            ArrayList arrayList2 = new ArrayList();
            for (FieldInfo fieldInfo2 : computeGetters2) {
                arrayList2.add(new FieldSerializer(fieldInfo2));
            }
            this.sortedGetters = (FieldSerializer[]) arrayList2.toArray(new FieldSerializer[arrayList2.size()]);
            return;
        }
        FieldSerializer[] fieldSerializerArr2 = new FieldSerializer[fieldSerializerArr.length];
        System.arraycopy(fieldSerializerArr, 0, fieldSerializerArr2, 0, fieldSerializerArr.length);
        Arrays.sort(fieldSerializerArr2);
        if (Arrays.equals(fieldSerializerArr2, fieldSerializerArr)) {
            this.sortedGetters = fieldSerializerArr;
        } else {
            this.sortedGetters = fieldSerializerArr2;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(3:(8:86|87|(3:(3:100|(3:103|(2:105|106)|101)|326)|327|(7:108|(5:110|(1:112)(2:316|(2:318|319)(2:320|(1:322)(1:323)))|(4:(3:115|(1:117)(2:308|(1:310)(2:311|(1:313)))|118)(1:314)|119|(3:122|(2:125|126)(1:124)|120)|306)(1:315)|307|(5:128|95|96|97|98)(7:(4:(3:132|(1:134)(2:136|(1:138)(2:139|(1:141)))|135)|142|(2:145|143)|146)(1:305)|(5:(3:150|(1:152)(2:154|(1:156)(2:157|(1:159)))|153)|160|(2:163|161)|164|165)(1:304)|(1:303)(2:171|(4:173|96|97|98))|(6:(4:201|(2:203|(1:205)(1:206))|207|(1:209))(1:302)|(2:(1:212)|213)(1:(2:(1:219)|220)(2:(1:(4:223|(2:225|(1:227)(5:228|(1:229)|231|232|233))|234|233)(1:235))|(4:(2:272|(1:(3:275|(1:280)|281)(2:282|(1:284)(1:285)))(2:286|(1:(2:289|(3:291|(1:293)(1:297)|(1:295)(1:296))(1:298))(1:299))(1:300)))(1:301)|215|216|98)(2:238|(2:240|(1:242)(10:(1:244)(1:262)|245|(2:248|246)|249|250|(1:252)|253|(2:255|(1:257)(2:258|(1:260)))|261|(0)))(2:263|(1:265)(2:266|(1:(1:269)(1:270)))))))|214|215|216|98)|96|97|98))(1:325)|324|319|(0)(0)|307|(0)(0)))(1:93)|94|95|96|97|98)|83|84) */
    /* JADX WARN: Code restructure failed: missing block: B:374:0x0555, code lost:
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:375:0x0556, code lost:
        r1 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:376:0x0559, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:377:0x055a, code lost:
        r1 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:383:0x0563, code lost:
        r3 = r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x025b  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x041d A[Catch: all -> 0x04e9, Exception -> 0x04ef, TryCatch #8 {Exception -> 0x04ef, all -> 0x04e9, blocks: (B:111:0x0170, B:113:0x018b, B:115:0x018f, B:119:0x0196, B:120:0x019a, B:122:0x01a0, B:129:0x01bb, B:131:0x01c2, B:133:0x01c6, B:146:0x0212, B:148:0x0218, B:157:0x0233, B:158:0x0237, B:160:0x023d, B:171:0x0261, B:173:0x0265, B:175:0x026d, B:177:0x0271, B:178:0x0276, B:180:0x027a, B:181:0x027f, B:182:0x0286, B:184:0x028c, B:189:0x02a6, B:191:0x02aa, B:193:0x02b1, B:195:0x02b5, B:196:0x02ba, B:198:0x02be, B:199:0x02c3, B:200:0x02ca, B:202:0x02d0, B:208:0x02f0, B:210:0x02f4, B:217:0x0308, B:219:0x030c, B:221:0x0310, B:223:0x0314, B:225:0x0318, B:227:0x031c, B:234:0x032e, B:236:0x0332, B:238:0x0336, B:229:0x0320, B:231:0x0324, B:242:0x0348, B:244:0x0351, B:246:0x0355, B:247:0x0359, B:248:0x035d, B:250:0x0372, B:254:0x037e, B:255:0x0382, B:259:0x038c, B:260:0x038f, B:263:0x0397, B:265:0x03a2, B:267:0x03a6, B:269:0x03ab, B:273:0x03cb, B:274:0x03d5, B:277:0x03dc, B:281:0x03e6, B:286:0x03f2, B:288:0x03f8, B:290:0x03fc, B:291:0x03fe, B:293:0x0406, B:295:0x040a, B:296:0x040e, B:299:0x041d, B:300:0x0427, B:301:0x042a, B:303:0x042e, B:304:0x0437, B:307:0x043d, B:308:0x0448, B:313:0x045b, B:315:0x0464, B:318:0x046e, B:319:0x0473, B:320:0x047a, B:322:0x047e, B:323:0x0483, B:324:0x048a, B:327:0x0490, B:329:0x0499, B:334:0x04ad, B:335:0x04b2, B:336:0x04b7, B:337:0x04c2, B:338:0x04c7, B:339:0x04cc, B:150:0x021f, B:152:0x0223, B:153:0x0228, B:155:0x022c, B:134:0x01d6, B:136:0x01da, B:137:0x01e6, B:139:0x01ea, B:140:0x01fa, B:141:0x0201, B:352:0x0506, B:353:0x050c, B:355:0x0512, B:360:0x0522, B:362:0x052b, B:365:0x053a, B:367:0x053e, B:368:0x0542), top: B:397:0x0170 }] */
    /* JADX WARN: Removed duplicated region for block: B:349:0x0500  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x0522 A[Catch: all -> 0x04e9, Exception -> 0x04ef, TRY_ENTER, TryCatch #8 {Exception -> 0x04ef, all -> 0x04e9, blocks: (B:111:0x0170, B:113:0x018b, B:115:0x018f, B:119:0x0196, B:120:0x019a, B:122:0x01a0, B:129:0x01bb, B:131:0x01c2, B:133:0x01c6, B:146:0x0212, B:148:0x0218, B:157:0x0233, B:158:0x0237, B:160:0x023d, B:171:0x0261, B:173:0x0265, B:175:0x026d, B:177:0x0271, B:178:0x0276, B:180:0x027a, B:181:0x027f, B:182:0x0286, B:184:0x028c, B:189:0x02a6, B:191:0x02aa, B:193:0x02b1, B:195:0x02b5, B:196:0x02ba, B:198:0x02be, B:199:0x02c3, B:200:0x02ca, B:202:0x02d0, B:208:0x02f0, B:210:0x02f4, B:217:0x0308, B:219:0x030c, B:221:0x0310, B:223:0x0314, B:225:0x0318, B:227:0x031c, B:234:0x032e, B:236:0x0332, B:238:0x0336, B:229:0x0320, B:231:0x0324, B:242:0x0348, B:244:0x0351, B:246:0x0355, B:247:0x0359, B:248:0x035d, B:250:0x0372, B:254:0x037e, B:255:0x0382, B:259:0x038c, B:260:0x038f, B:263:0x0397, B:265:0x03a2, B:267:0x03a6, B:269:0x03ab, B:273:0x03cb, B:274:0x03d5, B:277:0x03dc, B:281:0x03e6, B:286:0x03f2, B:288:0x03f8, B:290:0x03fc, B:291:0x03fe, B:293:0x0406, B:295:0x040a, B:296:0x040e, B:299:0x041d, B:300:0x0427, B:301:0x042a, B:303:0x042e, B:304:0x0437, B:307:0x043d, B:308:0x0448, B:313:0x045b, B:315:0x0464, B:318:0x046e, B:319:0x0473, B:320:0x047a, B:322:0x047e, B:323:0x0483, B:324:0x048a, B:327:0x0490, B:329:0x0499, B:334:0x04ad, B:335:0x04b2, B:336:0x04b7, B:337:0x04c2, B:338:0x04c7, B:339:0x04cc, B:150:0x021f, B:152:0x0223, B:153:0x0228, B:155:0x022c, B:134:0x01d6, B:136:0x01da, B:137:0x01e6, B:139:0x01ea, B:140:0x01fa, B:141:0x0201, B:352:0x0506, B:353:0x050c, B:355:0x0512, B:360:0x0522, B:362:0x052b, B:365:0x053a, B:367:0x053e, B:368:0x0542), top: B:397:0x0170 }] */
    /* JADX WARN: Removed duplicated region for block: B:365:0x053a A[Catch: all -> 0x04e9, Exception -> 0x04ef, TRY_ENTER, TryCatch #8 {Exception -> 0x04ef, all -> 0x04e9, blocks: (B:111:0x0170, B:113:0x018b, B:115:0x018f, B:119:0x0196, B:120:0x019a, B:122:0x01a0, B:129:0x01bb, B:131:0x01c2, B:133:0x01c6, B:146:0x0212, B:148:0x0218, B:157:0x0233, B:158:0x0237, B:160:0x023d, B:171:0x0261, B:173:0x0265, B:175:0x026d, B:177:0x0271, B:178:0x0276, B:180:0x027a, B:181:0x027f, B:182:0x0286, B:184:0x028c, B:189:0x02a6, B:191:0x02aa, B:193:0x02b1, B:195:0x02b5, B:196:0x02ba, B:198:0x02be, B:199:0x02c3, B:200:0x02ca, B:202:0x02d0, B:208:0x02f0, B:210:0x02f4, B:217:0x0308, B:219:0x030c, B:221:0x0310, B:223:0x0314, B:225:0x0318, B:227:0x031c, B:234:0x032e, B:236:0x0332, B:238:0x0336, B:229:0x0320, B:231:0x0324, B:242:0x0348, B:244:0x0351, B:246:0x0355, B:247:0x0359, B:248:0x035d, B:250:0x0372, B:254:0x037e, B:255:0x0382, B:259:0x038c, B:260:0x038f, B:263:0x0397, B:265:0x03a2, B:267:0x03a6, B:269:0x03ab, B:273:0x03cb, B:274:0x03d5, B:277:0x03dc, B:281:0x03e6, B:286:0x03f2, B:288:0x03f8, B:290:0x03fc, B:291:0x03fe, B:293:0x0406, B:295:0x040a, B:296:0x040e, B:299:0x041d, B:300:0x0427, B:301:0x042a, B:303:0x042e, B:304:0x0437, B:307:0x043d, B:308:0x0448, B:313:0x045b, B:315:0x0464, B:318:0x046e, B:319:0x0473, B:320:0x047a, B:322:0x047e, B:323:0x0483, B:324:0x048a, B:327:0x0490, B:329:0x0499, B:334:0x04ad, B:335:0x04b2, B:336:0x04b7, B:337:0x04c2, B:338:0x04c7, B:339:0x04cc, B:150:0x021f, B:152:0x0223, B:153:0x0228, B:155:0x022c, B:134:0x01d6, B:136:0x01da, B:137:0x01e6, B:139:0x01ea, B:140:0x01fa, B:141:0x0201, B:352:0x0506, B:353:0x050c, B:355:0x0512, B:360:0x0522, B:362:0x052b, B:365:0x053a, B:367:0x053e, B:368:0x0542), top: B:397:0x0170 }] */
    /* JADX WARN: Removed duplicated region for block: B:393:0x056b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:397:0x0170 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0117 A[Catch: all -> 0x00a0, Exception -> 0x00a5, TRY_ENTER, TryCatch #9 {Exception -> 0x00a5, all -> 0x00a0, blocks: (B:41:0x0093, B:43:0x0097, B:44:0x009b, B:52:0x00b5, B:54:0x00be, B:58:0x00cd, B:61:0x00d8, B:63:0x00e1, B:69:0x00ed, B:71:0x00f3, B:73:0x00fe, B:74:0x0106, B:83:0x0117, B:84:0x011d, B:86:0x0123, B:94:0x013c), top: B:395:0x0093 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x013c A[Catch: all -> 0x00a0, Exception -> 0x00a5, TRY_ENTER, TRY_LEAVE, TryCatch #9 {Exception -> 0x00a5, all -> 0x00a0, blocks: (B:41:0x0093, B:43:0x0097, B:44:0x009b, B:52:0x00b5, B:54:0x00be, B:58:0x00cd, B:61:0x00d8, B:63:0x00e1, B:69:0x00ed, B:71:0x00f3, B:73:0x00fe, B:74:0x0106, B:83:0x0117, B:84:0x011d, B:86:0x0123, B:94:0x013c), top: B:395:0x0093 }] */
    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void write(com.alibaba.fastjson.serializer.JSONSerializer r36, java.lang.Object r37, java.lang.Object r38, java.lang.reflect.Type r39) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1421
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JavaBeanSerializer.write(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type):void");
    }

    public Map<String, Object> getFieldValuesMap(Object obj) throws Exception {
        FieldSerializer[] fieldSerializerArr;
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            linkedHashMap.put(fieldSerializer.fieldInfo.name, fieldSerializer.getPropertyValue(obj));
        }
        return linkedHashMap;
    }
}
