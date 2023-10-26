package zipkin2.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.IOException;
import org.eclipse.paho.android.service.MqttServiceConstants;
import zipkin2.Endpoint;
import zipkin2.Span;
import zipkin2.internal.JsonCodec;

/* loaded from: classes3.dex */
public final class V2SpanReader implements JsonCodec.JsonReaderAdapter<Span> {
    static final JsonCodec.JsonReaderAdapter<Endpoint> ENDPOINT_READER = new JsonCodec.JsonReaderAdapter<Endpoint>() { // from class: zipkin2.internal.V2SpanReader.1
        public String toString() {
            return "Endpoint";
        }

        @Override // zipkin2.internal.JsonCodec.JsonReaderAdapter
        public Endpoint fromJson(JsonCodec.JsonReader jsonReader) throws IOException {
            Endpoint.Builder newBuilder = Endpoint.newBuilder();
            jsonReader.beginObject();
            boolean z = false;
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                if (jsonReader.peekNull()) {
                    jsonReader.skipValue();
                } else {
                    if (nextName.equals("serviceName")) {
                        newBuilder.serviceName(jsonReader.nextString());
                    } else if (nextName.equals("ipv4") || nextName.equals("ipv6")) {
                        newBuilder.parseIp(jsonReader.nextString());
                    } else if (nextName.equals("port")) {
                        newBuilder.port(jsonReader.nextInt());
                    } else {
                        jsonReader.skipValue();
                    }
                    z = true;
                }
            }
            jsonReader.endObject();
            if (z) {
                return newBuilder.build();
            }
            return null;
        }
    };
    Span.Builder builder;

    public String toString() {
        return "Span";
    }

    @Override // zipkin2.internal.JsonCodec.JsonReaderAdapter
    public Span fromJson(JsonCodec.JsonReader jsonReader) throws IOException {
        Span.Builder builder = this.builder;
        if (builder == null) {
            this.builder = Span.newBuilder();
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
            } else if (nextName.equals("parentId")) {
                this.builder.parentId(jsonReader.nextString());
            } else if (nextName.equals("kind")) {
                this.builder.kind(Span.Kind.valueOf(jsonReader.nextString()));
            } else if (nextName.equals("name")) {
                this.builder.name(jsonReader.nextString());
            } else if (nextName.equals("timestamp")) {
                this.builder.timestamp(jsonReader.nextLong());
            } else if (nextName.equals(TypedValues.Transition.S_DURATION)) {
                this.builder.duration(jsonReader.nextLong());
            } else if (nextName.equals("localEndpoint")) {
                this.builder.localEndpoint(ENDPOINT_READER.fromJson(jsonReader));
            } else if (nextName.equals("remoteEndpoint")) {
                this.builder.remoteEndpoint(ENDPOINT_READER.fromJson(jsonReader));
            } else if (nextName.equals("annotations")) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    jsonReader.beginObject();
                    Long l = null;
                    String str = null;
                    while (jsonReader.hasNext()) {
                        String nextName2 = jsonReader.nextName();
                        if (nextName2.equals("timestamp")) {
                            l = Long.valueOf(jsonReader.nextLong());
                        } else if (nextName2.equals("value")) {
                            str = jsonReader.nextString();
                        } else {
                            jsonReader.skipValue();
                        }
                    }
                    if (l == null || str == null) {
                        throw new IllegalArgumentException("Incomplete annotation at " + jsonReader.getPath());
                    }
                    jsonReader.endObject();
                    this.builder.addAnnotation(l.longValue(), str);
                }
                jsonReader.endArray();
            } else if (nextName.equals("tags")) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String nextName3 = jsonReader.nextName();
                    if (jsonReader.peekNull()) {
                        throw new IllegalArgumentException("No value at " + jsonReader.getPath());
                    }
                    this.builder.putTag(nextName3, jsonReader.nextString());
                }
                jsonReader.endObject();
            } else if (nextName.equals(MqttServiceConstants.TRACE_DEBUG)) {
                if (jsonReader.nextBoolean()) {
                    this.builder.debug(true);
                }
            } else if (nextName.equals("shared")) {
                if (jsonReader.nextBoolean()) {
                    this.builder.shared(true);
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return this.builder.build();
    }
}
