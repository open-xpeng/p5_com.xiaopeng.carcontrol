package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
import java.io.IOException;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public final class BooleanCodec implements ObjectSerializer, ObjectDeserializer {
    public static final BooleanCodec instance = new BooleanCodec();

    private BooleanCodec() {
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        Boolean bool = (Boolean) obj;
        if (bool == null) {
            if ((serializeWriter.features & SerializerFeature.WriteNullBooleanAsFalse.mask) != 0) {
                serializeWriter.write(OOBEEvent.STRING_FALSE);
            } else {
                serializeWriter.writeNull();
            }
        } else if (bool.booleanValue()) {
            serializeWriter.write(OOBEEvent.STRING_TRUE);
        } else {
            serializeWriter.write(OOBEEvent.STRING_FALSE);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i = jSONLexer.token();
        if (i == 6) {
            jSONLexer.nextToken(16);
            return (T) Boolean.TRUE;
        } else if (i == 7) {
            jSONLexer.nextToken(16);
            return (T) Boolean.FALSE;
        } else if (i == 2) {
            int intValue = jSONLexer.intValue();
            jSONLexer.nextToken(16);
            if (intValue == 1) {
                return (T) Boolean.TRUE;
            }
            return (T) Boolean.FALSE;
        } else {
            Object parse = defaultJSONParser.parse();
            if (parse == null) {
                return null;
            }
            return (T) TypeUtils.castToBoolean(parse);
        }
    }
}
