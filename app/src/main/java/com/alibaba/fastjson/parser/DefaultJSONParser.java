package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/* loaded from: classes.dex */
public class DefaultJSONParser implements Closeable {
    public static final int NONE = 0;
    public static final int NeedToResolve = 1;
    public static final int TypeNameRedirect = 2;
    public ParserConfig config;
    protected ParseContext contex;
    private ParseContext[] contextArray;
    private int contextArrayIndex;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    protected List<ExtraProcessor> extraProcessors;
    protected List<ExtraTypeProvider> extraTypeProviders;
    public FieldTypeResolver fieldTypeResolver;
    public final JSONLexer lexer;
    public int resolveStatus;
    private List<ResolveTask> resolveTaskList;
    public final SymbolTable symbolTable;

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.dateFormatPattern, this.lexer.locale);
            this.dateFormat = simpleDateFormat;
            simpleDateFormat.setTimeZone(this.lexer.timeZone);
        }
        return this.dateFormat;
    }

    public void setDateFormat(String str) {
        this.dateFormatPattern = str;
        this.dateFormat = null;
    }

    public void setDateFomrat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DefaultJSONParser(String str) {
        this(str, ParserConfig.global, JSON.DEFAULT_PARSER_FEATURE);
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig) {
        this(new JSONLexer(str, JSON.DEFAULT_PARSER_FEATURE), parserConfig);
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig, int i) {
        this(new JSONLexer(str, i), parserConfig);
    }

    public DefaultJSONParser(char[] cArr, int i, ParserConfig parserConfig, int i2) {
        this(new JSONLexer(cArr, i, i2), parserConfig);
    }

    public DefaultJSONParser(JSONLexer jSONLexer) {
        this(jSONLexer, ParserConfig.global);
    }

    public DefaultJSONParser(JSONLexer jSONLexer, ParserConfig parserConfig) {
        this.dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
        this.contextArrayIndex = 0;
        this.resolveStatus = 0;
        this.extraTypeProviders = null;
        this.extraProcessors = null;
        this.fieldTypeResolver = null;
        this.lexer = jSONLexer;
        this.config = parserConfig;
        this.symbolTable = parserConfig.symbolTable;
        char c = jSONLexer.ch;
        char c2 = JSONLexer.EOI;
        if (c == '{') {
            int i = jSONLexer.bp + 1;
            jSONLexer.bp = i;
            jSONLexer.ch = i < jSONLexer.len ? jSONLexer.text.charAt(i) : c2;
            jSONLexer.token = 12;
        } else if (jSONLexer.ch == '[') {
            int i2 = jSONLexer.bp + 1;
            jSONLexer.bp = i2;
            jSONLexer.ch = i2 < jSONLexer.len ? jSONLexer.text.charAt(i2) : c2;
            jSONLexer.token = 14;
        } else {
            jSONLexer.nextToken();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:142:0x029a, code lost:
        r3.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x02a1, code lost:
        if (r3.token != 13) goto L326;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x02a3, code lost:
        r3.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x02a6, code lost:
        r0 = r19.config.getDeserializer(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x02ae, code lost:
        if ((r0 instanceof com.alibaba.fastjson.parser.JavaBeanDeserializer) == false) goto L322;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x02b0, code lost:
        r16 = ((com.alibaba.fastjson.parser.JavaBeanDeserializer) r0).createInstance(r19, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x02b9, code lost:
        r16 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x02bb, code lost:
        if (r16 != null) goto L319;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x02bf, code lost:
        if (r7 != java.lang.Cloneable.class) goto L315;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x02c1, code lost:
        r16 = new java.util.HashMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x02cd, code lost:
        if ("java.util.Collections$EmptyMap".equals(r6) == false) goto L318;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x02cf, code lost:
        r16 = java.util.Collections.emptyMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x02d4, code lost:
        r16 = r7.newInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x02d8, code lost:
        if (r13 != false) goto L321;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x02da, code lost:
        r19.contex = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x02dc, code lost:
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x02dd, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x02e5, code lost:
        throw new com.alibaba.fastjson.JSONException("create instance error", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x02e6, code lost:
        r19.resolveStatus = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x02eb, code lost:
        if (r19.contex == null) goto L331;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x02ef, code lost:
        if ((r21 instanceof java.lang.Integer) != false) goto L331;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x02f1, code lost:
        popContext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x02f8, code lost:
        if (r20.size() <= 0) goto L337;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x02fa, code lost:
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r20, (java.lang.Class<java.lang.Object>) r7, r19.config);
        parseObject(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0303, code lost:
        if (r13 != false) goto L336;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0305, code lost:
        r19.contex = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0307, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0308, code lost:
        r0 = r19.config.getDeserializer(r7).deserialze(r19, r7, r21);
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x0312, code lost:
        if (r13 != false) goto L340;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x0314, code lost:
        r19.contex = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x0316, code lost:
        return r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0230 A[Catch: all -> 0x06f7, TryCatch #1 {all -> 0x06f7, blocks: (B:21:0x0068, B:24:0x0072, B:27:0x007b, B:29:0x0084, B:33:0x0096, B:35:0x00a0, B:38:0x00a8, B:39:0x00ca, B:111:0x0230, B:115:0x0242, B:131:0x0261, B:134:0x026e, B:137:0x0275, B:139:0x027d, B:141:0x028f, B:142:0x029a, B:144:0x02a3, B:145:0x02a6, B:147:0x02b0, B:152:0x02c1, B:153:0x02c7, B:155:0x02cf, B:156:0x02d4, B:161:0x02de, B:162:0x02e5, B:163:0x02e6, B:165:0x02ed, B:167:0x02f1, B:168:0x02f4, B:170:0x02fa, B:174:0x0308, B:180:0x031c, B:182:0x0324, B:184:0x032b, B:186:0x033a, B:188:0x033e, B:190:0x0344, B:193:0x0349, B:195:0x034d, B:215:0x039f, B:217:0x03a3, B:221:0x03ad, B:222:0x03c7, B:197:0x0354, B:199:0x035c, B:201:0x0360, B:202:0x0363, B:203:0x036f, B:206:0x0378, B:208:0x037c, B:209:0x037f, B:211:0x0383, B:212:0x0387, B:213:0x0393, B:223:0x03c8, B:224:0x03e6, B:227:0x03eb, B:233:0x03fc, B:235:0x0402, B:237:0x040e, B:238:0x0414, B:240:0x0419, B:332:0x05a1, B:336:0x05ab, B:339:0x05b4, B:343:0x05c7, B:342:0x05c1, B:347:0x05d3, B:351:0x05e6, B:353:0x05ef, B:357:0x0602, B:374:0x064a, B:356:0x05fc, B:360:0x060d, B:364:0x0620, B:363:0x061a, B:367:0x062b, B:371:0x063e, B:370:0x0638, B:372:0x0645, B:350:0x05e0, B:378:0x0654, B:379:0x066e, B:241:0x041d, B:248:0x042d, B:252:0x043c, B:256:0x0453, B:258:0x0464, B:260:0x046b, B:262:0x046f, B:270:0x0482, B:271:0x049c, B:259:0x0468, B:255:0x044d, B:274:0x04a1, B:278:0x04b4, B:280:0x04c5, B:284:0x04d9, B:286:0x04df, B:289:0x04e5, B:291:0x04ef, B:293:0x04f7, B:297:0x0509, B:300:0x0511, B:301:0x0513, B:303:0x0518, B:305:0x0521, B:308:0x052a, B:309:0x052d, B:311:0x0533, B:313:0x053a, B:320:0x0547, B:321:0x0561, B:306:0x0525, B:281:0x04d0, B:277:0x04ae, B:324:0x0568, B:326:0x0575, B:329:0x0588, B:331:0x0594, B:380:0x066f, B:382:0x0680, B:383:0x0684, B:385:0x068d, B:390:0x0699, B:393:0x06a6, B:394:0x06c0, B:114:0x023c, B:133:0x026a, B:44:0x00d4, B:48:0x00e7, B:47:0x00e1, B:54:0x00fa, B:56:0x0103, B:58:0x010d, B:59:0x0110, B:62:0x0115, B:63:0x012d, B:64:0x012e, B:65:0x0148, B:76:0x015d, B:77:0x0163, B:79:0x0168, B:82:0x0175, B:83:0x0179, B:86:0x017f, B:87:0x019b, B:80:0x016d, B:88:0x019c, B:89:0x01b8, B:95:0x01c2, B:97:0x01cb, B:100:0x01da, B:101:0x01e0, B:102:0x0202, B:103:0x0203, B:104:0x021d, B:105:0x021e, B:107:0x0227, B:395:0x06c1, B:396:0x06db, B:397:0x06dc, B:398:0x06f6), top: B:404:0x0068, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x026a A[Catch: all -> 0x06f7, TryCatch #1 {all -> 0x06f7, blocks: (B:21:0x0068, B:24:0x0072, B:27:0x007b, B:29:0x0084, B:33:0x0096, B:35:0x00a0, B:38:0x00a8, B:39:0x00ca, B:111:0x0230, B:115:0x0242, B:131:0x0261, B:134:0x026e, B:137:0x0275, B:139:0x027d, B:141:0x028f, B:142:0x029a, B:144:0x02a3, B:145:0x02a6, B:147:0x02b0, B:152:0x02c1, B:153:0x02c7, B:155:0x02cf, B:156:0x02d4, B:161:0x02de, B:162:0x02e5, B:163:0x02e6, B:165:0x02ed, B:167:0x02f1, B:168:0x02f4, B:170:0x02fa, B:174:0x0308, B:180:0x031c, B:182:0x0324, B:184:0x032b, B:186:0x033a, B:188:0x033e, B:190:0x0344, B:193:0x0349, B:195:0x034d, B:215:0x039f, B:217:0x03a3, B:221:0x03ad, B:222:0x03c7, B:197:0x0354, B:199:0x035c, B:201:0x0360, B:202:0x0363, B:203:0x036f, B:206:0x0378, B:208:0x037c, B:209:0x037f, B:211:0x0383, B:212:0x0387, B:213:0x0393, B:223:0x03c8, B:224:0x03e6, B:227:0x03eb, B:233:0x03fc, B:235:0x0402, B:237:0x040e, B:238:0x0414, B:240:0x0419, B:332:0x05a1, B:336:0x05ab, B:339:0x05b4, B:343:0x05c7, B:342:0x05c1, B:347:0x05d3, B:351:0x05e6, B:353:0x05ef, B:357:0x0602, B:374:0x064a, B:356:0x05fc, B:360:0x060d, B:364:0x0620, B:363:0x061a, B:367:0x062b, B:371:0x063e, B:370:0x0638, B:372:0x0645, B:350:0x05e0, B:378:0x0654, B:379:0x066e, B:241:0x041d, B:248:0x042d, B:252:0x043c, B:256:0x0453, B:258:0x0464, B:260:0x046b, B:262:0x046f, B:270:0x0482, B:271:0x049c, B:259:0x0468, B:255:0x044d, B:274:0x04a1, B:278:0x04b4, B:280:0x04c5, B:284:0x04d9, B:286:0x04df, B:289:0x04e5, B:291:0x04ef, B:293:0x04f7, B:297:0x0509, B:300:0x0511, B:301:0x0513, B:303:0x0518, B:305:0x0521, B:308:0x052a, B:309:0x052d, B:311:0x0533, B:313:0x053a, B:320:0x0547, B:321:0x0561, B:306:0x0525, B:281:0x04d0, B:277:0x04ae, B:324:0x0568, B:326:0x0575, B:329:0x0588, B:331:0x0594, B:380:0x066f, B:382:0x0680, B:383:0x0684, B:385:0x068d, B:390:0x0699, B:393:0x06a6, B:394:0x06c0, B:114:0x023c, B:133:0x026a, B:44:0x00d4, B:48:0x00e7, B:47:0x00e1, B:54:0x00fa, B:56:0x0103, B:58:0x010d, B:59:0x0110, B:62:0x0115, B:63:0x012d, B:64:0x012e, B:65:0x0148, B:76:0x015d, B:77:0x0163, B:79:0x0168, B:82:0x0175, B:83:0x0179, B:86:0x017f, B:87:0x019b, B:80:0x016d, B:88:0x019c, B:89:0x01b8, B:95:0x01c2, B:97:0x01cb, B:100:0x01da, B:101:0x01e0, B:102:0x0202, B:103:0x0203, B:104:0x021d, B:105:0x021e, B:107:0x0227, B:395:0x06c1, B:396:0x06db, B:397:0x06dc, B:398:0x06f6), top: B:404:0x0068, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0275 A[Catch: all -> 0x06f7, TRY_ENTER, TryCatch #1 {all -> 0x06f7, blocks: (B:21:0x0068, B:24:0x0072, B:27:0x007b, B:29:0x0084, B:33:0x0096, B:35:0x00a0, B:38:0x00a8, B:39:0x00ca, B:111:0x0230, B:115:0x0242, B:131:0x0261, B:134:0x026e, B:137:0x0275, B:139:0x027d, B:141:0x028f, B:142:0x029a, B:144:0x02a3, B:145:0x02a6, B:147:0x02b0, B:152:0x02c1, B:153:0x02c7, B:155:0x02cf, B:156:0x02d4, B:161:0x02de, B:162:0x02e5, B:163:0x02e6, B:165:0x02ed, B:167:0x02f1, B:168:0x02f4, B:170:0x02fa, B:174:0x0308, B:180:0x031c, B:182:0x0324, B:184:0x032b, B:186:0x033a, B:188:0x033e, B:190:0x0344, B:193:0x0349, B:195:0x034d, B:215:0x039f, B:217:0x03a3, B:221:0x03ad, B:222:0x03c7, B:197:0x0354, B:199:0x035c, B:201:0x0360, B:202:0x0363, B:203:0x036f, B:206:0x0378, B:208:0x037c, B:209:0x037f, B:211:0x0383, B:212:0x0387, B:213:0x0393, B:223:0x03c8, B:224:0x03e6, B:227:0x03eb, B:233:0x03fc, B:235:0x0402, B:237:0x040e, B:238:0x0414, B:240:0x0419, B:332:0x05a1, B:336:0x05ab, B:339:0x05b4, B:343:0x05c7, B:342:0x05c1, B:347:0x05d3, B:351:0x05e6, B:353:0x05ef, B:357:0x0602, B:374:0x064a, B:356:0x05fc, B:360:0x060d, B:364:0x0620, B:363:0x061a, B:367:0x062b, B:371:0x063e, B:370:0x0638, B:372:0x0645, B:350:0x05e0, B:378:0x0654, B:379:0x066e, B:241:0x041d, B:248:0x042d, B:252:0x043c, B:256:0x0453, B:258:0x0464, B:260:0x046b, B:262:0x046f, B:270:0x0482, B:271:0x049c, B:259:0x0468, B:255:0x044d, B:274:0x04a1, B:278:0x04b4, B:280:0x04c5, B:284:0x04d9, B:286:0x04df, B:289:0x04e5, B:291:0x04ef, B:293:0x04f7, B:297:0x0509, B:300:0x0511, B:301:0x0513, B:303:0x0518, B:305:0x0521, B:308:0x052a, B:309:0x052d, B:311:0x0533, B:313:0x053a, B:320:0x0547, B:321:0x0561, B:306:0x0525, B:281:0x04d0, B:277:0x04ae, B:324:0x0568, B:326:0x0575, B:329:0x0588, B:331:0x0594, B:380:0x066f, B:382:0x0680, B:383:0x0684, B:385:0x068d, B:390:0x0699, B:393:0x06a6, B:394:0x06c0, B:114:0x023c, B:133:0x026a, B:44:0x00d4, B:48:0x00e7, B:47:0x00e1, B:54:0x00fa, B:56:0x0103, B:58:0x010d, B:59:0x0110, B:62:0x0115, B:63:0x012d, B:64:0x012e, B:65:0x0148, B:76:0x015d, B:77:0x0163, B:79:0x0168, B:82:0x0175, B:83:0x0179, B:86:0x017f, B:87:0x019b, B:80:0x016d, B:88:0x019c, B:89:0x01b8, B:95:0x01c2, B:97:0x01cb, B:100:0x01da, B:101:0x01e0, B:102:0x0202, B:103:0x0203, B:104:0x021d, B:105:0x021e, B:107:0x0227, B:395:0x06c1, B:396:0x06db, B:397:0x06dc, B:398:0x06f6), top: B:404:0x0068, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x031c A[Catch: all -> 0x06f7, TryCatch #1 {all -> 0x06f7, blocks: (B:21:0x0068, B:24:0x0072, B:27:0x007b, B:29:0x0084, B:33:0x0096, B:35:0x00a0, B:38:0x00a8, B:39:0x00ca, B:111:0x0230, B:115:0x0242, B:131:0x0261, B:134:0x026e, B:137:0x0275, B:139:0x027d, B:141:0x028f, B:142:0x029a, B:144:0x02a3, B:145:0x02a6, B:147:0x02b0, B:152:0x02c1, B:153:0x02c7, B:155:0x02cf, B:156:0x02d4, B:161:0x02de, B:162:0x02e5, B:163:0x02e6, B:165:0x02ed, B:167:0x02f1, B:168:0x02f4, B:170:0x02fa, B:174:0x0308, B:180:0x031c, B:182:0x0324, B:184:0x032b, B:186:0x033a, B:188:0x033e, B:190:0x0344, B:193:0x0349, B:195:0x034d, B:215:0x039f, B:217:0x03a3, B:221:0x03ad, B:222:0x03c7, B:197:0x0354, B:199:0x035c, B:201:0x0360, B:202:0x0363, B:203:0x036f, B:206:0x0378, B:208:0x037c, B:209:0x037f, B:211:0x0383, B:212:0x0387, B:213:0x0393, B:223:0x03c8, B:224:0x03e6, B:227:0x03eb, B:233:0x03fc, B:235:0x0402, B:237:0x040e, B:238:0x0414, B:240:0x0419, B:332:0x05a1, B:336:0x05ab, B:339:0x05b4, B:343:0x05c7, B:342:0x05c1, B:347:0x05d3, B:351:0x05e6, B:353:0x05ef, B:357:0x0602, B:374:0x064a, B:356:0x05fc, B:360:0x060d, B:364:0x0620, B:363:0x061a, B:367:0x062b, B:371:0x063e, B:370:0x0638, B:372:0x0645, B:350:0x05e0, B:378:0x0654, B:379:0x066e, B:241:0x041d, B:248:0x042d, B:252:0x043c, B:256:0x0453, B:258:0x0464, B:260:0x046b, B:262:0x046f, B:270:0x0482, B:271:0x049c, B:259:0x0468, B:255:0x044d, B:274:0x04a1, B:278:0x04b4, B:280:0x04c5, B:284:0x04d9, B:286:0x04df, B:289:0x04e5, B:291:0x04ef, B:293:0x04f7, B:297:0x0509, B:300:0x0511, B:301:0x0513, B:303:0x0518, B:305:0x0521, B:308:0x052a, B:309:0x052d, B:311:0x0533, B:313:0x053a, B:320:0x0547, B:321:0x0561, B:306:0x0525, B:281:0x04d0, B:277:0x04ae, B:324:0x0568, B:326:0x0575, B:329:0x0588, B:331:0x0594, B:380:0x066f, B:382:0x0680, B:383:0x0684, B:385:0x068d, B:390:0x0699, B:393:0x06a6, B:394:0x06c0, B:114:0x023c, B:133:0x026a, B:44:0x00d4, B:48:0x00e7, B:47:0x00e1, B:54:0x00fa, B:56:0x0103, B:58:0x010d, B:59:0x0110, B:62:0x0115, B:63:0x012d, B:64:0x012e, B:65:0x0148, B:76:0x015d, B:77:0x0163, B:79:0x0168, B:82:0x0175, B:83:0x0179, B:86:0x017f, B:87:0x019b, B:80:0x016d, B:88:0x019c, B:89:0x01b8, B:95:0x01c2, B:97:0x01cb, B:100:0x01da, B:101:0x01e0, B:102:0x0202, B:103:0x0203, B:104:0x021d, B:105:0x021e, B:107:0x0227, B:395:0x06c1, B:396:0x06db, B:397:0x06dc, B:398:0x06f6), top: B:404:0x0068, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:226:0x03e9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:233:0x03fc A[Catch: all -> 0x06f7, TryCatch #1 {all -> 0x06f7, blocks: (B:21:0x0068, B:24:0x0072, B:27:0x007b, B:29:0x0084, B:33:0x0096, B:35:0x00a0, B:38:0x00a8, B:39:0x00ca, B:111:0x0230, B:115:0x0242, B:131:0x0261, B:134:0x026e, B:137:0x0275, B:139:0x027d, B:141:0x028f, B:142:0x029a, B:144:0x02a3, B:145:0x02a6, B:147:0x02b0, B:152:0x02c1, B:153:0x02c7, B:155:0x02cf, B:156:0x02d4, B:161:0x02de, B:162:0x02e5, B:163:0x02e6, B:165:0x02ed, B:167:0x02f1, B:168:0x02f4, B:170:0x02fa, B:174:0x0308, B:180:0x031c, B:182:0x0324, B:184:0x032b, B:186:0x033a, B:188:0x033e, B:190:0x0344, B:193:0x0349, B:195:0x034d, B:215:0x039f, B:217:0x03a3, B:221:0x03ad, B:222:0x03c7, B:197:0x0354, B:199:0x035c, B:201:0x0360, B:202:0x0363, B:203:0x036f, B:206:0x0378, B:208:0x037c, B:209:0x037f, B:211:0x0383, B:212:0x0387, B:213:0x0393, B:223:0x03c8, B:224:0x03e6, B:227:0x03eb, B:233:0x03fc, B:235:0x0402, B:237:0x040e, B:238:0x0414, B:240:0x0419, B:332:0x05a1, B:336:0x05ab, B:339:0x05b4, B:343:0x05c7, B:342:0x05c1, B:347:0x05d3, B:351:0x05e6, B:353:0x05ef, B:357:0x0602, B:374:0x064a, B:356:0x05fc, B:360:0x060d, B:364:0x0620, B:363:0x061a, B:367:0x062b, B:371:0x063e, B:370:0x0638, B:372:0x0645, B:350:0x05e0, B:378:0x0654, B:379:0x066e, B:241:0x041d, B:248:0x042d, B:252:0x043c, B:256:0x0453, B:258:0x0464, B:260:0x046b, B:262:0x046f, B:270:0x0482, B:271:0x049c, B:259:0x0468, B:255:0x044d, B:274:0x04a1, B:278:0x04b4, B:280:0x04c5, B:284:0x04d9, B:286:0x04df, B:289:0x04e5, B:291:0x04ef, B:293:0x04f7, B:297:0x0509, B:300:0x0511, B:301:0x0513, B:303:0x0518, B:305:0x0521, B:308:0x052a, B:309:0x052d, B:311:0x0533, B:313:0x053a, B:320:0x0547, B:321:0x0561, B:306:0x0525, B:281:0x04d0, B:277:0x04ae, B:324:0x0568, B:326:0x0575, B:329:0x0588, B:331:0x0594, B:380:0x066f, B:382:0x0680, B:383:0x0684, B:385:0x068d, B:390:0x0699, B:393:0x06a6, B:394:0x06c0, B:114:0x023c, B:133:0x026a, B:44:0x00d4, B:48:0x00e7, B:47:0x00e1, B:54:0x00fa, B:56:0x0103, B:58:0x010d, B:59:0x0110, B:62:0x0115, B:63:0x012d, B:64:0x012e, B:65:0x0148, B:76:0x015d, B:77:0x0163, B:79:0x0168, B:82:0x0175, B:83:0x0179, B:86:0x017f, B:87:0x019b, B:80:0x016d, B:88:0x019c, B:89:0x01b8, B:95:0x01c2, B:97:0x01cb, B:100:0x01da, B:101:0x01e0, B:102:0x0202, B:103:0x0203, B:104:0x021d, B:105:0x021e, B:107:0x0227, B:395:0x06c1, B:396:0x06db, B:397:0x06dc, B:398:0x06f6), top: B:404:0x0068, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:242:0x0421  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0509 A[Catch: all -> 0x06f7, TryCatch #1 {all -> 0x06f7, blocks: (B:21:0x0068, B:24:0x0072, B:27:0x007b, B:29:0x0084, B:33:0x0096, B:35:0x00a0, B:38:0x00a8, B:39:0x00ca, B:111:0x0230, B:115:0x0242, B:131:0x0261, B:134:0x026e, B:137:0x0275, B:139:0x027d, B:141:0x028f, B:142:0x029a, B:144:0x02a3, B:145:0x02a6, B:147:0x02b0, B:152:0x02c1, B:153:0x02c7, B:155:0x02cf, B:156:0x02d4, B:161:0x02de, B:162:0x02e5, B:163:0x02e6, B:165:0x02ed, B:167:0x02f1, B:168:0x02f4, B:170:0x02fa, B:174:0x0308, B:180:0x031c, B:182:0x0324, B:184:0x032b, B:186:0x033a, B:188:0x033e, B:190:0x0344, B:193:0x0349, B:195:0x034d, B:215:0x039f, B:217:0x03a3, B:221:0x03ad, B:222:0x03c7, B:197:0x0354, B:199:0x035c, B:201:0x0360, B:202:0x0363, B:203:0x036f, B:206:0x0378, B:208:0x037c, B:209:0x037f, B:211:0x0383, B:212:0x0387, B:213:0x0393, B:223:0x03c8, B:224:0x03e6, B:227:0x03eb, B:233:0x03fc, B:235:0x0402, B:237:0x040e, B:238:0x0414, B:240:0x0419, B:332:0x05a1, B:336:0x05ab, B:339:0x05b4, B:343:0x05c7, B:342:0x05c1, B:347:0x05d3, B:351:0x05e6, B:353:0x05ef, B:357:0x0602, B:374:0x064a, B:356:0x05fc, B:360:0x060d, B:364:0x0620, B:363:0x061a, B:367:0x062b, B:371:0x063e, B:370:0x0638, B:372:0x0645, B:350:0x05e0, B:378:0x0654, B:379:0x066e, B:241:0x041d, B:248:0x042d, B:252:0x043c, B:256:0x0453, B:258:0x0464, B:260:0x046b, B:262:0x046f, B:270:0x0482, B:271:0x049c, B:259:0x0468, B:255:0x044d, B:274:0x04a1, B:278:0x04b4, B:280:0x04c5, B:284:0x04d9, B:286:0x04df, B:289:0x04e5, B:291:0x04ef, B:293:0x04f7, B:297:0x0509, B:300:0x0511, B:301:0x0513, B:303:0x0518, B:305:0x0521, B:308:0x052a, B:309:0x052d, B:311:0x0533, B:313:0x053a, B:320:0x0547, B:321:0x0561, B:306:0x0525, B:281:0x04d0, B:277:0x04ae, B:324:0x0568, B:326:0x0575, B:329:0x0588, B:331:0x0594, B:380:0x066f, B:382:0x0680, B:383:0x0684, B:385:0x068d, B:390:0x0699, B:393:0x06a6, B:394:0x06c0, B:114:0x023c, B:133:0x026a, B:44:0x00d4, B:48:0x00e7, B:47:0x00e1, B:54:0x00fa, B:56:0x0103, B:58:0x010d, B:59:0x0110, B:62:0x0115, B:63:0x012d, B:64:0x012e, B:65:0x0148, B:76:0x015d, B:77:0x0163, B:79:0x0168, B:82:0x0175, B:83:0x0179, B:86:0x017f, B:87:0x019b, B:80:0x016d, B:88:0x019c, B:89:0x01b8, B:95:0x01c2, B:97:0x01cb, B:100:0x01da, B:101:0x01e0, B:102:0x0202, B:103:0x0203, B:104:0x021d, B:105:0x021e, B:107:0x0227, B:395:0x06c1, B:396:0x06db, B:397:0x06dc, B:398:0x06f6), top: B:404:0x0068, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:303:0x0518 A[Catch: all -> 0x06f7, TryCatch #1 {all -> 0x06f7, blocks: (B:21:0x0068, B:24:0x0072, B:27:0x007b, B:29:0x0084, B:33:0x0096, B:35:0x00a0, B:38:0x00a8, B:39:0x00ca, B:111:0x0230, B:115:0x0242, B:131:0x0261, B:134:0x026e, B:137:0x0275, B:139:0x027d, B:141:0x028f, B:142:0x029a, B:144:0x02a3, B:145:0x02a6, B:147:0x02b0, B:152:0x02c1, B:153:0x02c7, B:155:0x02cf, B:156:0x02d4, B:161:0x02de, B:162:0x02e5, B:163:0x02e6, B:165:0x02ed, B:167:0x02f1, B:168:0x02f4, B:170:0x02fa, B:174:0x0308, B:180:0x031c, B:182:0x0324, B:184:0x032b, B:186:0x033a, B:188:0x033e, B:190:0x0344, B:193:0x0349, B:195:0x034d, B:215:0x039f, B:217:0x03a3, B:221:0x03ad, B:222:0x03c7, B:197:0x0354, B:199:0x035c, B:201:0x0360, B:202:0x0363, B:203:0x036f, B:206:0x0378, B:208:0x037c, B:209:0x037f, B:211:0x0383, B:212:0x0387, B:213:0x0393, B:223:0x03c8, B:224:0x03e6, B:227:0x03eb, B:233:0x03fc, B:235:0x0402, B:237:0x040e, B:238:0x0414, B:240:0x0419, B:332:0x05a1, B:336:0x05ab, B:339:0x05b4, B:343:0x05c7, B:342:0x05c1, B:347:0x05d3, B:351:0x05e6, B:353:0x05ef, B:357:0x0602, B:374:0x064a, B:356:0x05fc, B:360:0x060d, B:364:0x0620, B:363:0x061a, B:367:0x062b, B:371:0x063e, B:370:0x0638, B:372:0x0645, B:350:0x05e0, B:378:0x0654, B:379:0x066e, B:241:0x041d, B:248:0x042d, B:252:0x043c, B:256:0x0453, B:258:0x0464, B:260:0x046b, B:262:0x046f, B:270:0x0482, B:271:0x049c, B:259:0x0468, B:255:0x044d, B:274:0x04a1, B:278:0x04b4, B:280:0x04c5, B:284:0x04d9, B:286:0x04df, B:289:0x04e5, B:291:0x04ef, B:293:0x04f7, B:297:0x0509, B:300:0x0511, B:301:0x0513, B:303:0x0518, B:305:0x0521, B:308:0x052a, B:309:0x052d, B:311:0x0533, B:313:0x053a, B:320:0x0547, B:321:0x0561, B:306:0x0525, B:281:0x04d0, B:277:0x04ae, B:324:0x0568, B:326:0x0575, B:329:0x0588, B:331:0x0594, B:380:0x066f, B:382:0x0680, B:383:0x0684, B:385:0x068d, B:390:0x0699, B:393:0x06a6, B:394:0x06c0, B:114:0x023c, B:133:0x026a, B:44:0x00d4, B:48:0x00e7, B:47:0x00e1, B:54:0x00fa, B:56:0x0103, B:58:0x010d, B:59:0x0110, B:62:0x0115, B:63:0x012d, B:64:0x012e, B:65:0x0148, B:76:0x015d, B:77:0x0163, B:79:0x0168, B:82:0x0175, B:83:0x0179, B:86:0x017f, B:87:0x019b, B:80:0x016d, B:88:0x019c, B:89:0x01b8, B:95:0x01c2, B:97:0x01cb, B:100:0x01da, B:101:0x01e0, B:102:0x0202, B:103:0x0203, B:104:0x021d, B:105:0x021e, B:107:0x0227, B:395:0x06c1, B:396:0x06db, B:397:0x06dc, B:398:0x06f6), top: B:404:0x0068, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0521 A[Catch: all -> 0x06f7, TryCatch #1 {all -> 0x06f7, blocks: (B:21:0x0068, B:24:0x0072, B:27:0x007b, B:29:0x0084, B:33:0x0096, B:35:0x00a0, B:38:0x00a8, B:39:0x00ca, B:111:0x0230, B:115:0x0242, B:131:0x0261, B:134:0x026e, B:137:0x0275, B:139:0x027d, B:141:0x028f, B:142:0x029a, B:144:0x02a3, B:145:0x02a6, B:147:0x02b0, B:152:0x02c1, B:153:0x02c7, B:155:0x02cf, B:156:0x02d4, B:161:0x02de, B:162:0x02e5, B:163:0x02e6, B:165:0x02ed, B:167:0x02f1, B:168:0x02f4, B:170:0x02fa, B:174:0x0308, B:180:0x031c, B:182:0x0324, B:184:0x032b, B:186:0x033a, B:188:0x033e, B:190:0x0344, B:193:0x0349, B:195:0x034d, B:215:0x039f, B:217:0x03a3, B:221:0x03ad, B:222:0x03c7, B:197:0x0354, B:199:0x035c, B:201:0x0360, B:202:0x0363, B:203:0x036f, B:206:0x0378, B:208:0x037c, B:209:0x037f, B:211:0x0383, B:212:0x0387, B:213:0x0393, B:223:0x03c8, B:224:0x03e6, B:227:0x03eb, B:233:0x03fc, B:235:0x0402, B:237:0x040e, B:238:0x0414, B:240:0x0419, B:332:0x05a1, B:336:0x05ab, B:339:0x05b4, B:343:0x05c7, B:342:0x05c1, B:347:0x05d3, B:351:0x05e6, B:353:0x05ef, B:357:0x0602, B:374:0x064a, B:356:0x05fc, B:360:0x060d, B:364:0x0620, B:363:0x061a, B:367:0x062b, B:371:0x063e, B:370:0x0638, B:372:0x0645, B:350:0x05e0, B:378:0x0654, B:379:0x066e, B:241:0x041d, B:248:0x042d, B:252:0x043c, B:256:0x0453, B:258:0x0464, B:260:0x046b, B:262:0x046f, B:270:0x0482, B:271:0x049c, B:259:0x0468, B:255:0x044d, B:274:0x04a1, B:278:0x04b4, B:280:0x04c5, B:284:0x04d9, B:286:0x04df, B:289:0x04e5, B:291:0x04ef, B:293:0x04f7, B:297:0x0509, B:300:0x0511, B:301:0x0513, B:303:0x0518, B:305:0x0521, B:308:0x052a, B:309:0x052d, B:311:0x0533, B:313:0x053a, B:320:0x0547, B:321:0x0561, B:306:0x0525, B:281:0x04d0, B:277:0x04ae, B:324:0x0568, B:326:0x0575, B:329:0x0588, B:331:0x0594, B:380:0x066f, B:382:0x0680, B:383:0x0684, B:385:0x068d, B:390:0x0699, B:393:0x06a6, B:394:0x06c0, B:114:0x023c, B:133:0x026a, B:44:0x00d4, B:48:0x00e7, B:47:0x00e1, B:54:0x00fa, B:56:0x0103, B:58:0x010d, B:59:0x0110, B:62:0x0115, B:63:0x012d, B:64:0x012e, B:65:0x0148, B:76:0x015d, B:77:0x0163, B:79:0x0168, B:82:0x0175, B:83:0x0179, B:86:0x017f, B:87:0x019b, B:80:0x016d, B:88:0x019c, B:89:0x01b8, B:95:0x01c2, B:97:0x01cb, B:100:0x01da, B:101:0x01e0, B:102:0x0202, B:103:0x0203, B:104:0x021d, B:105:0x021e, B:107:0x0227, B:395:0x06c1, B:396:0x06db, B:397:0x06dc, B:398:0x06f6), top: B:404:0x0068, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0525 A[Catch: all -> 0x06f7, TryCatch #1 {all -> 0x06f7, blocks: (B:21:0x0068, B:24:0x0072, B:27:0x007b, B:29:0x0084, B:33:0x0096, B:35:0x00a0, B:38:0x00a8, B:39:0x00ca, B:111:0x0230, B:115:0x0242, B:131:0x0261, B:134:0x026e, B:137:0x0275, B:139:0x027d, B:141:0x028f, B:142:0x029a, B:144:0x02a3, B:145:0x02a6, B:147:0x02b0, B:152:0x02c1, B:153:0x02c7, B:155:0x02cf, B:156:0x02d4, B:161:0x02de, B:162:0x02e5, B:163:0x02e6, B:165:0x02ed, B:167:0x02f1, B:168:0x02f4, B:170:0x02fa, B:174:0x0308, B:180:0x031c, B:182:0x0324, B:184:0x032b, B:186:0x033a, B:188:0x033e, B:190:0x0344, B:193:0x0349, B:195:0x034d, B:215:0x039f, B:217:0x03a3, B:221:0x03ad, B:222:0x03c7, B:197:0x0354, B:199:0x035c, B:201:0x0360, B:202:0x0363, B:203:0x036f, B:206:0x0378, B:208:0x037c, B:209:0x037f, B:211:0x0383, B:212:0x0387, B:213:0x0393, B:223:0x03c8, B:224:0x03e6, B:227:0x03eb, B:233:0x03fc, B:235:0x0402, B:237:0x040e, B:238:0x0414, B:240:0x0419, B:332:0x05a1, B:336:0x05ab, B:339:0x05b4, B:343:0x05c7, B:342:0x05c1, B:347:0x05d3, B:351:0x05e6, B:353:0x05ef, B:357:0x0602, B:374:0x064a, B:356:0x05fc, B:360:0x060d, B:364:0x0620, B:363:0x061a, B:367:0x062b, B:371:0x063e, B:370:0x0638, B:372:0x0645, B:350:0x05e0, B:378:0x0654, B:379:0x066e, B:241:0x041d, B:248:0x042d, B:252:0x043c, B:256:0x0453, B:258:0x0464, B:260:0x046b, B:262:0x046f, B:270:0x0482, B:271:0x049c, B:259:0x0468, B:255:0x044d, B:274:0x04a1, B:278:0x04b4, B:280:0x04c5, B:284:0x04d9, B:286:0x04df, B:289:0x04e5, B:291:0x04ef, B:293:0x04f7, B:297:0x0509, B:300:0x0511, B:301:0x0513, B:303:0x0518, B:305:0x0521, B:308:0x052a, B:309:0x052d, B:311:0x0533, B:313:0x053a, B:320:0x0547, B:321:0x0561, B:306:0x0525, B:281:0x04d0, B:277:0x04ae, B:324:0x0568, B:326:0x0575, B:329:0x0588, B:331:0x0594, B:380:0x066f, B:382:0x0680, B:383:0x0684, B:385:0x068d, B:390:0x0699, B:393:0x06a6, B:394:0x06c0, B:114:0x023c, B:133:0x026a, B:44:0x00d4, B:48:0x00e7, B:47:0x00e1, B:54:0x00fa, B:56:0x0103, B:58:0x010d, B:59:0x0110, B:62:0x0115, B:63:0x012d, B:64:0x012e, B:65:0x0148, B:76:0x015d, B:77:0x0163, B:79:0x0168, B:82:0x0175, B:83:0x0179, B:86:0x017f, B:87:0x019b, B:80:0x016d, B:88:0x019c, B:89:0x01b8, B:95:0x01c2, B:97:0x01cb, B:100:0x01da, B:101:0x01e0, B:102:0x0202, B:103:0x0203, B:104:0x021d, B:105:0x021e, B:107:0x0227, B:395:0x06c1, B:396:0x06db, B:397:0x06dc, B:398:0x06f6), top: B:404:0x0068, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:308:0x052a A[Catch: all -> 0x06f7, TryCatch #1 {all -> 0x06f7, blocks: (B:21:0x0068, B:24:0x0072, B:27:0x007b, B:29:0x0084, B:33:0x0096, B:35:0x00a0, B:38:0x00a8, B:39:0x00ca, B:111:0x0230, B:115:0x0242, B:131:0x0261, B:134:0x026e, B:137:0x0275, B:139:0x027d, B:141:0x028f, B:142:0x029a, B:144:0x02a3, B:145:0x02a6, B:147:0x02b0, B:152:0x02c1, B:153:0x02c7, B:155:0x02cf, B:156:0x02d4, B:161:0x02de, B:162:0x02e5, B:163:0x02e6, B:165:0x02ed, B:167:0x02f1, B:168:0x02f4, B:170:0x02fa, B:174:0x0308, B:180:0x031c, B:182:0x0324, B:184:0x032b, B:186:0x033a, B:188:0x033e, B:190:0x0344, B:193:0x0349, B:195:0x034d, B:215:0x039f, B:217:0x03a3, B:221:0x03ad, B:222:0x03c7, B:197:0x0354, B:199:0x035c, B:201:0x0360, B:202:0x0363, B:203:0x036f, B:206:0x0378, B:208:0x037c, B:209:0x037f, B:211:0x0383, B:212:0x0387, B:213:0x0393, B:223:0x03c8, B:224:0x03e6, B:227:0x03eb, B:233:0x03fc, B:235:0x0402, B:237:0x040e, B:238:0x0414, B:240:0x0419, B:332:0x05a1, B:336:0x05ab, B:339:0x05b4, B:343:0x05c7, B:342:0x05c1, B:347:0x05d3, B:351:0x05e6, B:353:0x05ef, B:357:0x0602, B:374:0x064a, B:356:0x05fc, B:360:0x060d, B:364:0x0620, B:363:0x061a, B:367:0x062b, B:371:0x063e, B:370:0x0638, B:372:0x0645, B:350:0x05e0, B:378:0x0654, B:379:0x066e, B:241:0x041d, B:248:0x042d, B:252:0x043c, B:256:0x0453, B:258:0x0464, B:260:0x046b, B:262:0x046f, B:270:0x0482, B:271:0x049c, B:259:0x0468, B:255:0x044d, B:274:0x04a1, B:278:0x04b4, B:280:0x04c5, B:284:0x04d9, B:286:0x04df, B:289:0x04e5, B:291:0x04ef, B:293:0x04f7, B:297:0x0509, B:300:0x0511, B:301:0x0513, B:303:0x0518, B:305:0x0521, B:308:0x052a, B:309:0x052d, B:311:0x0533, B:313:0x053a, B:320:0x0547, B:321:0x0561, B:306:0x0525, B:281:0x04d0, B:277:0x04ae, B:324:0x0568, B:326:0x0575, B:329:0x0588, B:331:0x0594, B:380:0x066f, B:382:0x0680, B:383:0x0684, B:385:0x068d, B:390:0x0699, B:393:0x06a6, B:394:0x06c0, B:114:0x023c, B:133:0x026a, B:44:0x00d4, B:48:0x00e7, B:47:0x00e1, B:54:0x00fa, B:56:0x0103, B:58:0x010d, B:59:0x0110, B:62:0x0115, B:63:0x012d, B:64:0x012e, B:65:0x0148, B:76:0x015d, B:77:0x0163, B:79:0x0168, B:82:0x0175, B:83:0x0179, B:86:0x017f, B:87:0x019b, B:80:0x016d, B:88:0x019c, B:89:0x01b8, B:95:0x01c2, B:97:0x01cb, B:100:0x01da, B:101:0x01e0, B:102:0x0202, B:103:0x0203, B:104:0x021d, B:105:0x021e, B:107:0x0227, B:395:0x06c1, B:396:0x06db, B:397:0x06dc, B:398:0x06f6), top: B:404:0x0068, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:317:0x0541  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x05a7  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x05b4 A[Catch: all -> 0x06f7, TryCatch #1 {all -> 0x06f7, blocks: (B:21:0x0068, B:24:0x0072, B:27:0x007b, B:29:0x0084, B:33:0x0096, B:35:0x00a0, B:38:0x00a8, B:39:0x00ca, B:111:0x0230, B:115:0x0242, B:131:0x0261, B:134:0x026e, B:137:0x0275, B:139:0x027d, B:141:0x028f, B:142:0x029a, B:144:0x02a3, B:145:0x02a6, B:147:0x02b0, B:152:0x02c1, B:153:0x02c7, B:155:0x02cf, B:156:0x02d4, B:161:0x02de, B:162:0x02e5, B:163:0x02e6, B:165:0x02ed, B:167:0x02f1, B:168:0x02f4, B:170:0x02fa, B:174:0x0308, B:180:0x031c, B:182:0x0324, B:184:0x032b, B:186:0x033a, B:188:0x033e, B:190:0x0344, B:193:0x0349, B:195:0x034d, B:215:0x039f, B:217:0x03a3, B:221:0x03ad, B:222:0x03c7, B:197:0x0354, B:199:0x035c, B:201:0x0360, B:202:0x0363, B:203:0x036f, B:206:0x0378, B:208:0x037c, B:209:0x037f, B:211:0x0383, B:212:0x0387, B:213:0x0393, B:223:0x03c8, B:224:0x03e6, B:227:0x03eb, B:233:0x03fc, B:235:0x0402, B:237:0x040e, B:238:0x0414, B:240:0x0419, B:332:0x05a1, B:336:0x05ab, B:339:0x05b4, B:343:0x05c7, B:342:0x05c1, B:347:0x05d3, B:351:0x05e6, B:353:0x05ef, B:357:0x0602, B:374:0x064a, B:356:0x05fc, B:360:0x060d, B:364:0x0620, B:363:0x061a, B:367:0x062b, B:371:0x063e, B:370:0x0638, B:372:0x0645, B:350:0x05e0, B:378:0x0654, B:379:0x066e, B:241:0x041d, B:248:0x042d, B:252:0x043c, B:256:0x0453, B:258:0x0464, B:260:0x046b, B:262:0x046f, B:270:0x0482, B:271:0x049c, B:259:0x0468, B:255:0x044d, B:274:0x04a1, B:278:0x04b4, B:280:0x04c5, B:284:0x04d9, B:286:0x04df, B:289:0x04e5, B:291:0x04ef, B:293:0x04f7, B:297:0x0509, B:300:0x0511, B:301:0x0513, B:303:0x0518, B:305:0x0521, B:308:0x052a, B:309:0x052d, B:311:0x0533, B:313:0x053a, B:320:0x0547, B:321:0x0561, B:306:0x0525, B:281:0x04d0, B:277:0x04ae, B:324:0x0568, B:326:0x0575, B:329:0x0588, B:331:0x0594, B:380:0x066f, B:382:0x0680, B:383:0x0684, B:385:0x068d, B:390:0x0699, B:393:0x06a6, B:394:0x06c0, B:114:0x023c, B:133:0x026a, B:44:0x00d4, B:48:0x00e7, B:47:0x00e1, B:54:0x00fa, B:56:0x0103, B:58:0x010d, B:59:0x0110, B:62:0x0115, B:63:0x012d, B:64:0x012e, B:65:0x0148, B:76:0x015d, B:77:0x0163, B:79:0x0168, B:82:0x0175, B:83:0x0179, B:86:0x017f, B:87:0x019b, B:80:0x016d, B:88:0x019c, B:89:0x01b8, B:95:0x01c2, B:97:0x01cb, B:100:0x01da, B:101:0x01e0, B:102:0x0202, B:103:0x0203, B:104:0x021d, B:105:0x021e, B:107:0x0227, B:395:0x06c1, B:396:0x06db, B:397:0x06dc, B:398:0x06f6), top: B:404:0x0068, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:411:0x0317 A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:425:0x0533 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:427:0x05cf A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object parseObject(java.util.Map r20, java.lang.Object r21) {
        /*
            Method dump skipped, instructions count: 1789
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(java.util.Map, java.lang.Object):java.lang.Object");
    }

    public <T> T parseObject(Class<T> cls) {
        return (T) parseObject(cls, (Object) null);
    }

    public <T> T parseObject(Type type) {
        return (T) parseObject(type, (Object) null);
    }

    public <T> T parseObject(Type type, Object obj) {
        if (this.lexer.token == 8) {
            this.lexer.nextToken();
            return null;
        }
        if (this.lexer.token == 4) {
            if (type == byte[].class) {
                T t = (T) this.lexer.bytesValue();
                this.lexer.nextToken();
                return t;
            } else if (type == char[].class) {
                String stringVal = this.lexer.stringVal();
                this.lexer.nextToken();
                return (T) stringVal.toCharArray();
            }
        }
        try {
            return (T) this.config.getDeserializer(type).deserialze(this, type, obj);
        } catch (JSONException e) {
            throw e;
        } catch (Exception e2) {
            throw new JSONException(e2.getMessage(), e2);
        }
    }

    public <T> List<T> parseArray(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        parseArray((Class<?>) cls, (Collection) arrayList);
        return arrayList;
    }

    public void parseArray(Class<?> cls, Collection collection) {
        parseArray((Type) cls, collection);
    }

    public void parseArray(Type type, Collection collection) {
        parseArray(type, collection, null);
    }

    public void parseArray(Type type, Collection collection, Object obj) {
        ObjectDeserializer deserializer;
        String str;
        if (this.lexer.token == 21 || this.lexer.token == 22) {
            this.lexer.nextToken();
        }
        if (this.lexer.token != 14) {
            throw new JSONException("exepct '[', but " + JSONToken.name(this.lexer.token) + ", " + this.lexer.info());
        }
        if (Integer.TYPE == type) {
            deserializer = IntegerCodec.instance;
            this.lexer.nextToken(2);
        } else if (String.class == type) {
            deserializer = StringCodec.instance;
            this.lexer.nextToken(4);
        } else {
            deserializer = this.config.getDeserializer(type);
            this.lexer.nextToken(12);
        }
        ParseContext parseContext = this.contex;
        if (!this.lexer.disableCircularReferenceDetect) {
            setContext(this.contex, collection, obj);
        }
        int i = 0;
        while (true) {
            try {
                if ((this.lexer.features & Feature.AllowArbitraryCommas.mask) != 0) {
                    while (this.lexer.token == 16) {
                        this.lexer.nextToken();
                    }
                }
                if (this.lexer.token != 15) {
                    Object obj2 = null;
                    String obj3 = null;
                    if (Integer.TYPE == type) {
                        collection.add(IntegerCodec.instance.deserialze(this, null, null));
                    } else if (String.class == type) {
                        if (this.lexer.token == 4) {
                            str = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            Object parse = parse();
                            if (parse != null) {
                                obj3 = parse.toString();
                            }
                            str = obj3;
                        }
                        collection.add(str);
                    } else {
                        if (this.lexer.token == 8) {
                            this.lexer.nextToken();
                        } else {
                            obj2 = deserializer.deserialze(this, type, Integer.valueOf(i));
                        }
                        collection.add(obj2);
                        if (this.resolveStatus == 1) {
                            checkListResolve(collection);
                        }
                    }
                    if (this.lexer.token == 16) {
                        this.lexer.nextToken();
                    }
                    i++;
                } else {
                    this.contex = parseContext;
                    this.lexer.nextToken(16);
                    return;
                }
            } catch (Throwable th) {
                this.contex = parseContext;
                throw th;
            }
        }
    }

    public Object[] parseArray(Type[] typeArr) {
        Object cast;
        Class<?> cls;
        boolean z;
        int i = 8;
        if (this.lexer.token == 8) {
            this.lexer.nextToken(16);
            return null;
        } else if (this.lexer.token != 14) {
            throw new JSONException("syntax error, " + this.lexer.info());
        } else {
            Object[] objArr = new Object[typeArr.length];
            if (typeArr.length == 0) {
                this.lexer.nextToken(15);
                if (this.lexer.token != 15) {
                    throw new JSONException("syntax error, " + this.lexer.info());
                }
                this.lexer.nextToken(16);
                return new Object[0];
            }
            this.lexer.nextToken(2);
            int i2 = 0;
            while (i2 < typeArr.length) {
                if (this.lexer.token == i) {
                    this.lexer.nextToken(16);
                    cast = null;
                } else {
                    Type type = typeArr[i2];
                    if (type == Integer.TYPE || type == Integer.class) {
                        if (this.lexer.token == 2) {
                            cast = Integer.valueOf(this.lexer.intValue());
                            this.lexer.nextToken(16);
                        } else {
                            cast = TypeUtils.cast(parse(), type, this.config);
                        }
                    } else if (type == String.class) {
                        if (this.lexer.token == 4) {
                            cast = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            cast = TypeUtils.cast(parse(), type, this.config);
                        }
                    } else {
                        if (i2 == typeArr.length - 1 && (type instanceof Class)) {
                            Class cls2 = (Class) type;
                            z = cls2.isArray();
                            cls = cls2.getComponentType();
                        } else {
                            cls = null;
                            z = false;
                        }
                        if (z && this.lexer.token != 14) {
                            ArrayList arrayList = new ArrayList();
                            ObjectDeserializer deserializer = this.config.getDeserializer(cls);
                            if (this.lexer.token != 15) {
                                while (true) {
                                    arrayList.add(deserializer.deserialze(this, type, null));
                                    if (this.lexer.token != 16) {
                                        break;
                                    }
                                    this.lexer.nextToken(12);
                                }
                                if (this.lexer.token != 15) {
                                    throw new JSONException("syntax error, " + this.lexer.info());
                                }
                            }
                            cast = TypeUtils.cast(arrayList, type, this.config);
                        } else {
                            cast = this.config.getDeserializer(type).deserialze(this, type, null);
                        }
                    }
                }
                objArr[i2] = cast;
                if (this.lexer.token == 15) {
                    break;
                } else if (this.lexer.token != 16) {
                    throw new JSONException("syntax error, " + this.lexer.info());
                } else {
                    if (i2 == typeArr.length - 1) {
                        this.lexer.nextToken(15);
                    } else {
                        this.lexer.nextToken(2);
                    }
                    i2++;
                    i = 8;
                }
            }
            if (this.lexer.token != 15) {
                throw new JSONException("syntax error, " + this.lexer.info());
            }
            this.lexer.nextToken(16);
            return objArr;
        }
    }

    public void parseObject(Object obj) {
        Object deserialze;
        Class<?> cls = obj.getClass();
        ObjectDeserializer deserializer = this.config.getDeserializer(cls);
        JavaBeanDeserializer javaBeanDeserializer = deserializer instanceof JavaBeanDeserializer ? (JavaBeanDeserializer) deserializer : null;
        if (this.lexer.token != 12 && this.lexer.token != 16) {
            throw new JSONException("syntax error, expect {, actual " + this.lexer.tokenName());
        }
        while (true) {
            String scanSymbol = this.lexer.scanSymbol(this.symbolTable);
            if (scanSymbol == null) {
                if (this.lexer.token == 13) {
                    this.lexer.nextToken(16);
                    return;
                } else if (this.lexer.token == 16 && (this.lexer.features & Feature.AllowArbitraryCommas.mask) != 0) {
                }
            }
            FieldDeserializer fieldDeserializer = javaBeanDeserializer != null ? javaBeanDeserializer.getFieldDeserializer(scanSymbol) : null;
            if (fieldDeserializer == null) {
                if ((this.lexer.features & Feature.IgnoreNotMatch.mask) == 0) {
                    throw new JSONException("setter not found, class " + cls.getName() + ", property " + scanSymbol);
                }
                this.lexer.nextTokenWithChar(':');
                parse();
                if (this.lexer.token == 13) {
                    this.lexer.nextToken();
                    return;
                }
            } else {
                Class<?> cls2 = fieldDeserializer.fieldInfo.fieldClass;
                Type type = fieldDeserializer.fieldInfo.fieldType;
                if (cls2 == Integer.TYPE) {
                    this.lexer.nextTokenWithChar(':');
                    deserialze = IntegerCodec.instance.deserialze(this, type, null);
                } else if (cls2 == String.class) {
                    this.lexer.nextTokenWithChar(':');
                    deserialze = parseString();
                } else if (cls2 == Long.TYPE) {
                    this.lexer.nextTokenWithChar(':');
                    deserialze = IntegerCodec.instance.deserialze(this, type, null);
                } else {
                    ObjectDeserializer deserializer2 = this.config.getDeserializer(cls2, type);
                    this.lexer.nextTokenWithChar(':');
                    deserialze = deserializer2.deserialze(this, type, null);
                }
                fieldDeserializer.setValue(obj, deserialze);
                if (this.lexer.token != 16 && this.lexer.token == 13) {
                    this.lexer.nextToken(16);
                    return;
                }
            }
        }
    }

    public Object parseArrayWithType(Type type) {
        if (this.lexer.token == 8) {
            this.lexer.nextToken();
            return null;
        }
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (actualTypeArguments.length != 1) {
            throw new JSONException("not support type " + type);
        }
        Type type2 = actualTypeArguments[0];
        if (type2 instanceof Class) {
            ArrayList arrayList = new ArrayList();
            parseArray((Class) type2, (Collection) arrayList);
            return arrayList;
        } else if (type2 instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type2;
            Type type3 = wildcardType.getUpperBounds()[0];
            if (Object.class.equals(type3)) {
                if (wildcardType.getLowerBounds().length == 0) {
                    return parse();
                }
                throw new JSONException("not support type : " + type);
            }
            ArrayList arrayList2 = new ArrayList();
            parseArray((Class) type3, (Collection) arrayList2);
            return arrayList2;
        } else {
            if (type2 instanceof TypeVariable) {
                TypeVariable typeVariable = (TypeVariable) type2;
                Type[] bounds = typeVariable.getBounds();
                if (bounds.length != 1) {
                    throw new JSONException("not support : " + typeVariable);
                }
                Type type4 = bounds[0];
                if (type4 instanceof Class) {
                    ArrayList arrayList3 = new ArrayList();
                    parseArray((Class) type4, (Collection) arrayList3);
                    return arrayList3;
                }
            }
            if (type2 instanceof ParameterizedType) {
                ArrayList arrayList4 = new ArrayList();
                parseArray((ParameterizedType) type2, arrayList4);
                return arrayList4;
            }
            throw new JSONException("TODO : " + type);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void checkListResolve(Collection collection) {
        if (collection instanceof List) {
            ResolveTask lastResolveTask = getLastResolveTask();
            lastResolveTask.fieldDeserializer = new ResolveFieldDeserializer(this, (List) collection, collection.size() - 1);
            lastResolveTask.ownerContext = this.contex;
            this.resolveStatus = 0;
            return;
        }
        ResolveTask lastResolveTask2 = getLastResolveTask();
        lastResolveTask2.fieldDeserializer = new ResolveFieldDeserializer(collection);
        lastResolveTask2.ownerContext = this.contex;
        this.resolveStatus = 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void checkMapResolve(Map map, Object obj) {
        ResolveFieldDeserializer resolveFieldDeserializer = new ResolveFieldDeserializer(map, obj);
        ResolveTask lastResolveTask = getLastResolveTask();
        lastResolveTask.fieldDeserializer = resolveFieldDeserializer;
        lastResolveTask.ownerContext = this.contex;
        this.resolveStatus = 0;
    }

    public Object parseObject(Map map) {
        return parseObject(map, (Object) null);
    }

    public JSONObject parseObject() {
        return (JSONObject) parseObject((this.lexer.features & Feature.OrderedField.mask) != 0 ? new JSONObject(new LinkedHashMap()) : new JSONObject(), (Object) null);
    }

    public final void parseArray(Collection collection) {
        parseArray(collection, (Object) null);
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x0208 A[Catch: all -> 0x027f, TryCatch #0 {all -> 0x027f, blocks: (B:12:0x002a, B:15:0x003e, B:20:0x004f, B:24:0x0067, B:33:0x008b, B:35:0x0091, B:37:0x009f, B:41:0x00b7, B:43:0x00c0, B:47:0x00ca, B:40:0x00af, B:49:0x00d3, B:53:0x00eb, B:55:0x00f4, B:56:0x00f7, B:52:0x00e3, B:60:0x0101, B:61:0x0106, B:63:0x010c, B:65:0x0117, B:86:0x0146, B:116:0x0216, B:118:0x021d, B:119:0x0220, B:121:0x0226, B:123:0x022c, B:128:0x0241, B:131:0x0252, B:135:0x026e, B:134:0x0266, B:136:0x0271, B:88:0x014e, B:92:0x0158, B:93:0x0165, B:94:0x016d, B:95:0x0175, B:96:0x0176, B:98:0x0183, B:100:0x0193, B:99:0x018e, B:101:0x019c, B:102:0x01a4, B:103:0x01ae, B:104:0x01b8, B:106:0x01d0, B:108:0x01db, B:109:0x01e1, B:110:0x01e6, B:112:0x01f3, B:114:0x0202, B:113:0x01fb, B:115:0x0208, B:23:0x005f, B:25:0x006e, B:27:0x0075, B:30:0x0082), top: B:144:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x021d A[Catch: all -> 0x027f, TryCatch #0 {all -> 0x027f, blocks: (B:12:0x002a, B:15:0x003e, B:20:0x004f, B:24:0x0067, B:33:0x008b, B:35:0x0091, B:37:0x009f, B:41:0x00b7, B:43:0x00c0, B:47:0x00ca, B:40:0x00af, B:49:0x00d3, B:53:0x00eb, B:55:0x00f4, B:56:0x00f7, B:52:0x00e3, B:60:0x0101, B:61:0x0106, B:63:0x010c, B:65:0x0117, B:86:0x0146, B:116:0x0216, B:118:0x021d, B:119:0x0220, B:121:0x0226, B:123:0x022c, B:128:0x0241, B:131:0x0252, B:135:0x026e, B:134:0x0266, B:136:0x0271, B:88:0x014e, B:92:0x0158, B:93:0x0165, B:94:0x016d, B:95:0x0175, B:96:0x0176, B:98:0x0183, B:100:0x0193, B:99:0x018e, B:101:0x019c, B:102:0x01a4, B:103:0x01ae, B:104:0x01b8, B:106:0x01d0, B:108:0x01db, B:109:0x01e1, B:110:0x01e6, B:112:0x01f3, B:114:0x0202, B:113:0x01fb, B:115:0x0208, B:23:0x005f, B:25:0x006e, B:27:0x0075, B:30:0x0082), top: B:144:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0226 A[Catch: all -> 0x027f, TryCatch #0 {all -> 0x027f, blocks: (B:12:0x002a, B:15:0x003e, B:20:0x004f, B:24:0x0067, B:33:0x008b, B:35:0x0091, B:37:0x009f, B:41:0x00b7, B:43:0x00c0, B:47:0x00ca, B:40:0x00af, B:49:0x00d3, B:53:0x00eb, B:55:0x00f4, B:56:0x00f7, B:52:0x00e3, B:60:0x0101, B:61:0x0106, B:63:0x010c, B:65:0x0117, B:86:0x0146, B:116:0x0216, B:118:0x021d, B:119:0x0220, B:121:0x0226, B:123:0x022c, B:128:0x0241, B:131:0x0252, B:135:0x026e, B:134:0x0266, B:136:0x0271, B:88:0x014e, B:92:0x0158, B:93:0x0165, B:94:0x016d, B:95:0x0175, B:96:0x0176, B:98:0x0183, B:100:0x0193, B:99:0x018e, B:101:0x019c, B:102:0x01a4, B:103:0x01ae, B:104:0x01b8, B:106:0x01d0, B:108:0x01db, B:109:0x01e1, B:110:0x01e6, B:112:0x01f3, B:114:0x0202, B:113:0x01fb, B:115:0x0208, B:23:0x005f, B:25:0x006e, B:27:0x0075, B:30:0x0082), top: B:144:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x009f A[Catch: all -> 0x027f, TryCatch #0 {all -> 0x027f, blocks: (B:12:0x002a, B:15:0x003e, B:20:0x004f, B:24:0x0067, B:33:0x008b, B:35:0x0091, B:37:0x009f, B:41:0x00b7, B:43:0x00c0, B:47:0x00ca, B:40:0x00af, B:49:0x00d3, B:53:0x00eb, B:55:0x00f4, B:56:0x00f7, B:52:0x00e3, B:60:0x0101, B:61:0x0106, B:63:0x010c, B:65:0x0117, B:86:0x0146, B:116:0x0216, B:118:0x021d, B:119:0x0220, B:121:0x0226, B:123:0x022c, B:128:0x0241, B:131:0x0252, B:135:0x026e, B:134:0x0266, B:136:0x0271, B:88:0x014e, B:92:0x0158, B:93:0x0165, B:94:0x016d, B:95:0x0175, B:96:0x0176, B:98:0x0183, B:100:0x0193, B:99:0x018e, B:101:0x019c, B:102:0x01a4, B:103:0x01ae, B:104:0x01b8, B:106:0x01d0, B:108:0x01db, B:109:0x01e1, B:110:0x01e6, B:112:0x01f3, B:114:0x0202, B:113:0x01fb, B:115:0x0208, B:23:0x005f, B:25:0x006e, B:27:0x0075, B:30:0x0082), top: B:144:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void parseArray(java.util.Collection r17, java.lang.Object r18) {
        /*
            Method dump skipped, instructions count: 689
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseArray(java.util.Collection, java.lang.Object):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void addResolveTask(ResolveTask resolveTask) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        this.resolveTaskList.add(resolveTask);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ResolveTask getLastResolveTask() {
        List<ResolveTask> list = this.resolveTaskList;
        return list.get(list.size() - 1);
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList(2);
        }
        return this.extraTypeProviders;
    }

    public void setContext(ParseContext parseContext) {
        if (this.lexer.disableCircularReferenceDetect) {
            return;
        }
        this.contex = parseContext;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void popContext() {
        this.contex = this.contex.parent;
        ParseContext[] parseContextArr = this.contextArray;
        int i = this.contextArrayIndex;
        parseContextArr[i - 1] = null;
        this.contextArrayIndex = i - 1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ParseContext setContext(ParseContext parseContext, Object obj, Object obj2) {
        if (this.lexer.disableCircularReferenceDetect) {
            return null;
        }
        this.contex = new ParseContext(parseContext, obj, obj2);
        int i = this.contextArrayIndex;
        this.contextArrayIndex = i + 1;
        ParseContext[] parseContextArr = this.contextArray;
        if (parseContextArr == null) {
            this.contextArray = new ParseContext[8];
        } else if (i >= parseContextArr.length) {
            ParseContext[] parseContextArr2 = new ParseContext[(parseContextArr.length * 3) / 2];
            System.arraycopy(parseContextArr, 0, parseContextArr2, 0, parseContextArr.length);
            this.contextArray = parseContextArr2;
        }
        ParseContext[] parseContextArr3 = this.contextArray;
        ParseContext parseContext2 = this.contex;
        parseContextArr3[i] = parseContext2;
        return parseContext2;
    }

    public Object parse() {
        return parse(null);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public Object parse(Object obj) {
        int i = this.lexer.token;
        if (i == 2) {
            Number integerValue = this.lexer.integerValue();
            this.lexer.nextToken();
            return integerValue;
        }
        if (i == 3) {
            Number decimalValue = this.lexer.decimalValue((this.lexer.features & Feature.UseBigDecimal.mask) != 0);
            this.lexer.nextToken();
            return decimalValue;
        } else if (i == 4) {
            String stringVal = this.lexer.stringVal();
            this.lexer.nextToken(16);
            if ((this.lexer.features & Feature.AllowISO8601DateFormat.mask) != 0) {
                JSONLexer jSONLexer = new JSONLexer(stringVal);
                try {
                    if (jSONLexer.scanISO8601DateIfMatch(true)) {
                        return jSONLexer.calendar.getTime();
                    }
                } finally {
                    jSONLexer.close();
                }
            }
            return stringVal;
        } else if (i == 12) {
            return parseObject((this.lexer.features & Feature.OrderedField.mask) != 0 ? new JSONObject(new LinkedHashMap()) : new JSONObject(), obj);
        } else if (i == 14) {
            JSONArray jSONArray = new JSONArray();
            parseArray(jSONArray, obj);
            return jSONArray;
        } else {
            switch (i) {
                case 6:
                    this.lexer.nextToken(16);
                    return Boolean.TRUE;
                case 7:
                    this.lexer.nextToken(16);
                    return Boolean.FALSE;
                case 8:
                    break;
                case 9:
                    this.lexer.nextToken(18);
                    if (this.lexer.token != 18) {
                        throw new JSONException("syntax error, " + this.lexer.info());
                    }
                    this.lexer.nextToken(10);
                    accept(10);
                    long longValue = this.lexer.integerValue().longValue();
                    accept(2);
                    accept(11);
                    return new Date(longValue);
                default:
                    switch (i) {
                        case 20:
                            if (this.lexer.isBlankInput()) {
                                return null;
                            }
                            throw new JSONException("syntax error, " + this.lexer.info());
                        case 21:
                            this.lexer.nextToken();
                            HashSet hashSet = new HashSet();
                            parseArray(hashSet, obj);
                            return hashSet;
                        case 22:
                            this.lexer.nextToken();
                            TreeSet treeSet = new TreeSet();
                            parseArray(treeSet, obj);
                            return treeSet;
                        case 23:
                            break;
                        default:
                            throw new JSONException("syntax error, " + this.lexer.info());
                    }
            }
            this.lexer.nextToken();
            return null;
        }
    }

    public void config(Feature feature, boolean z) {
        this.lexer.config(feature, z);
    }

    public final void accept(int i) {
        if (this.lexer.token == i) {
            this.lexer.nextToken();
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(i) + ", actual " + JSONToken.name(this.lexer.token));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        try {
            if ((this.lexer.features & Feature.AutoCloseSource.mask) != 0 && this.lexer.token != 20) {
                throw new JSONException("not close json text, token : " + JSONToken.name(this.lexer.token));
            }
        } finally {
            this.lexer.close();
        }
    }

    public void handleResovleTask(Object obj) {
        List<ResolveTask> list = this.resolveTaskList;
        if (list == null) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ResolveTask resolveTask = this.resolveTaskList.get(i);
            FieldDeserializer fieldDeserializer = resolveTask.fieldDeserializer;
            if (fieldDeserializer != null) {
                Object obj2 = null;
                Object obj3 = resolveTask.ownerContext != null ? resolveTask.ownerContext.object : null;
                String str = resolveTask.referenceValue;
                if (str.startsWith("$")) {
                    for (int i2 = 0; i2 < this.contextArrayIndex; i2++) {
                        if (str.equals(this.contextArray[i2].toString())) {
                            obj2 = this.contextArray[i2].object;
                        }
                    }
                } else {
                    obj2 = resolveTask.context.object;
                }
                fieldDeserializer.setValue(obj3, obj2);
            }
        }
    }

    public String parseString() {
        int i = this.lexer.token;
        if (i != 4) {
            if (i == 2) {
                String numberString = this.lexer.numberString();
                this.lexer.nextToken(16);
                return numberString;
            }
            Object parse = parse();
            if (parse == null) {
                return null;
            }
            return parse.toString();
        }
        String stringVal = this.lexer.stringVal();
        char c = this.lexer.ch;
        char c2 = JSONLexer.EOI;
        if (c == ',') {
            JSONLexer jSONLexer = this.lexer;
            int i2 = jSONLexer.bp + 1;
            jSONLexer.bp = i2;
            JSONLexer jSONLexer2 = this.lexer;
            if (i2 < jSONLexer2.len) {
                c2 = this.lexer.text.charAt(i2);
            }
            jSONLexer2.ch = c2;
            this.lexer.token = 16;
        } else if (this.lexer.ch == ']') {
            JSONLexer jSONLexer3 = this.lexer;
            int i3 = jSONLexer3.bp + 1;
            jSONLexer3.bp = i3;
            JSONLexer jSONLexer4 = this.lexer;
            if (i3 < jSONLexer4.len) {
                c2 = this.lexer.text.charAt(i3);
            }
            jSONLexer4.ch = c2;
            this.lexer.token = 15;
        } else if (this.lexer.ch == '}') {
            JSONLexer jSONLexer5 = this.lexer;
            int i4 = jSONLexer5.bp + 1;
            jSONLexer5.bp = i4;
            JSONLexer jSONLexer6 = this.lexer;
            if (i4 < jSONLexer6.len) {
                c2 = this.lexer.text.charAt(i4);
            }
            jSONLexer6.ch = c2;
            this.lexer.token = 13;
        } else {
            this.lexer.nextToken();
        }
        return stringVal;
    }

    /* loaded from: classes.dex */
    public static class ResolveTask {
        private final ParseContext context;
        public FieldDeserializer fieldDeserializer;
        public ParseContext ownerContext;
        private final String referenceValue;

        public ResolveTask(ParseContext parseContext, String str) {
            this.context = parseContext;
            this.referenceValue = str;
        }
    }
}
