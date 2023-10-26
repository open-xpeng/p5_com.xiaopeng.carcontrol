package zipkin2.codec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import zipkin2.DependencyLink;
import zipkin2.internal.JsonCodec;
import zipkin2.internal.Nullable;

/* loaded from: classes3.dex */
public enum DependencyLinkBytesDecoder implements BytesDecoder<DependencyLink> {
    JSON_V1 { // from class: zipkin2.codec.DependencyLinkBytesDecoder.1
        @Override // zipkin2.codec.BytesDecoder
        public Encoding encoding() {
            return Encoding.JSON;
        }

        @Override // zipkin2.codec.BytesDecoder
        public boolean decode(byte[] bArr, Collection<DependencyLink> collection) {
            return JsonCodec.read(READER, bArr, collection);
        }

        @Override // zipkin2.codec.BytesDecoder
        @Nullable
        public DependencyLink decodeOne(byte[] bArr) {
            return (DependencyLink) JsonCodec.readOne(READER, bArr);
        }

        @Override // zipkin2.codec.BytesDecoder
        public boolean decodeList(byte[] bArr, Collection<DependencyLink> collection) {
            return JsonCodec.readList(READER, bArr, collection);
        }

        @Override // zipkin2.codec.BytesDecoder
        public List<DependencyLink> decodeList(byte[] bArr) {
            ArrayList arrayList = new ArrayList();
            return !decodeList(bArr, arrayList) ? Collections.emptyList() : arrayList;
        }
    };
    
    static final JsonCodec.JsonReaderAdapter<DependencyLink> READER = new JsonCodec.JsonReaderAdapter<DependencyLink>() { // from class: zipkin2.codec.DependencyLinkBytesDecoder.2
        public String toString() {
            return "DependencyLink";
        }

        @Override // zipkin2.internal.JsonCodec.JsonReaderAdapter
        public DependencyLink fromJson(JsonCodec.JsonReader jsonReader) throws IOException {
            DependencyLink.Builder newBuilder = DependencyLink.newBuilder();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                if (nextName.equals("parent")) {
                    newBuilder.parent(jsonReader.nextString());
                } else if (nextName.equals("child")) {
                    newBuilder.child(jsonReader.nextString());
                } else if (nextName.equals("callCount")) {
                    newBuilder.callCount(jsonReader.nextLong());
                } else if (nextName.equals("errorCount")) {
                    newBuilder.errorCount(jsonReader.nextLong());
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            return newBuilder.build();
        }
    };
}
