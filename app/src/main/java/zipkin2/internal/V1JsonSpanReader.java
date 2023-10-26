package zipkin2.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.IOException;
import java.util.Collection;
import org.eclipse.paho.android.service.MqttServiceConstants;
import zipkin2.Endpoint;
import zipkin2.Span;
import zipkin2.internal.JsonCodec;
import zipkin2.v1.V1Span;
import zipkin2.v1.V1SpanConverter;

/* loaded from: classes3.dex */
public final class V1JsonSpanReader implements JsonCodec.JsonReaderAdapter<V1Span> {
    V1Span.Builder builder;

    public String toString() {
        return "Span";
    }

    public boolean readList(byte[] bArr, Collection<Span> collection) {
        if (bArr.length == 0) {
            return false;
        }
        V1SpanConverter create = V1SpanConverter.create();
        JsonCodec.JsonReader jsonReader = new JsonCodec.JsonReader(bArr);
        try {
            jsonReader.beginArray();
            if (jsonReader.hasNext()) {
                while (jsonReader.hasNext()) {
                    create.convert(fromJson(jsonReader), collection);
                }
                jsonReader.endArray();
                return true;
            }
            return false;
        } catch (Exception e) {
            throw JsonCodec.exceptionReading("List<Span>", e);
        }
    }

    @Override // zipkin2.internal.JsonCodec.JsonReaderAdapter
    public V1Span fromJson(JsonCodec.JsonReader jsonReader) throws IOException {
        V1Span.Builder builder = this.builder;
        if (builder == null) {
            this.builder = V1Span.newBuilder();
        } else {
            builder.clear();
        }
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (nextName.equals("traceId")) {
                this.builder.traceId(jsonReader.nextString());
            } else if (nextName.equals("id")) {
                this.builder.id(jsonReader.nextString());
            } else if (jsonReader.peekNull()) {
                jsonReader.skipValue();
            } else if (nextName.equals("name")) {
                this.builder.name(jsonReader.nextString());
            } else if (nextName.equals("parentId")) {
                this.builder.parentId(jsonReader.nextString());
            } else if (nextName.equals("timestamp")) {
                this.builder.timestamp(jsonReader.nextLong());
            } else if (nextName.equals(TypedValues.Transition.S_DURATION)) {
                this.builder.duration(jsonReader.nextLong());
            } else if (nextName.equals("annotations")) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    readAnnotation(jsonReader);
                }
                jsonReader.endArray();
            } else if (nextName.equals("binaryAnnotations")) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    readBinaryAnnotation(jsonReader);
                }
                jsonReader.endArray();
            } else if (nextName.equals(MqttServiceConstants.TRACE_DEBUG)) {
                if (jsonReader.nextBoolean()) {
                    this.builder.debug(true);
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return this.builder.build();
    }

    void readAnnotation(JsonCodec.JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        Long l = null;
        String str = null;
        Endpoint endpoint = null;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (nextName.equals("timestamp")) {
                l = Long.valueOf(jsonReader.nextLong());
            } else if (nextName.equals("value")) {
                str = jsonReader.nextString();
            } else if (nextName.equals("endpoint") && !jsonReader.peekNull()) {
                endpoint = V2SpanReader.ENDPOINT_READER.fromJson(jsonReader);
            } else {
                jsonReader.skipValue();
            }
        }
        if (l == null || str == null) {
            throw new IllegalArgumentException("Incomplete annotation at " + jsonReader.getPath());
        }
        jsonReader.endObject();
        this.builder.addAnnotation(l.longValue(), str, endpoint);
    }

    void readBinaryAnnotation(JsonCodec.JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        String str = null;
        String str2 = null;
        Boolean bool = null;
        Endpoint endpoint = null;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (jsonReader.peekNull()) {
                jsonReader.skipValue();
            } else if (nextName.equals("key")) {
                str = jsonReader.nextString();
            } else if (nextName.equals("value")) {
                if (jsonReader.peekString()) {
                    str2 = jsonReader.nextString();
                } else if (jsonReader.peekBoolean()) {
                    bool = Boolean.valueOf(jsonReader.nextBoolean());
                } else {
                    jsonReader.skipValue();
                }
            } else if (nextName.equals("endpoint")) {
                endpoint = V2SpanReader.ENDPOINT_READER.fromJson(jsonReader);
            } else {
                jsonReader.skipValue();
            }
        }
        if (str == null) {
            throw new IllegalArgumentException("No key at " + jsonReader.getPath());
        }
        jsonReader.endObject();
        if (str2 != null) {
            this.builder.addBinaryAnnotation(str, str2, endpoint);
        } else if (bool == null || !bool.booleanValue() || endpoint == null) {
        } else {
            if (str.equals("sa") || str.equals("ca") || str.equals("ma")) {
                this.builder.addBinaryAnnotation(str, endpoint);
            }
        }
    }
}
