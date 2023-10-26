package zipkin2.codec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import zipkin2.Span;
import zipkin2.internal.JsonCodec;
import zipkin2.internal.Nullable;
import zipkin2.internal.Proto3Codec;
import zipkin2.internal.ThriftCodec;
import zipkin2.internal.V1JsonSpanReader;
import zipkin2.internal.V2SpanReader;
import zipkin2.v1.V1Span;
import zipkin2.v1.V1SpanConverter;

/* loaded from: classes3.dex */
public enum SpanBytesDecoder implements BytesDecoder<Span> {
    JSON_V1 { // from class: zipkin2.codec.SpanBytesDecoder.1
        @Override // zipkin2.codec.BytesDecoder
        public Encoding encoding() {
            return Encoding.JSON;
        }

        @Override // zipkin2.codec.BytesDecoder
        public boolean decode(byte[] bArr, Collection<Span> collection) {
            Span decodeOne = decodeOne(bArr);
            if (decodeOne == null) {
                return false;
            }
            collection.add(decodeOne);
            return true;
        }

        @Override // zipkin2.codec.BytesDecoder
        public boolean decodeList(byte[] bArr, Collection<Span> collection) {
            return new V1JsonSpanReader().readList(bArr, collection);
        }

        @Override // zipkin2.codec.BytesDecoder
        public Span decodeOne(byte[] bArr) {
            ArrayList arrayList = new ArrayList(1);
            V1SpanConverter.create().convert((V1Span) JsonCodec.readOne(new V1JsonSpanReader(), bArr), arrayList);
            return (Span) arrayList.get(0);
        }

        @Override // zipkin2.codec.BytesDecoder
        public List<Span> decodeList(byte[] bArr) {
            return decodeList(this, bArr);
        }
    },
    THRIFT { // from class: zipkin2.codec.SpanBytesDecoder.2
        @Override // zipkin2.codec.BytesDecoder
        public Encoding encoding() {
            return Encoding.THRIFT;
        }

        @Override // zipkin2.codec.BytesDecoder
        public boolean decode(byte[] bArr, Collection<Span> collection) {
            return ThriftCodec.read(bArr, collection);
        }

        @Override // zipkin2.codec.BytesDecoder
        public boolean decodeList(byte[] bArr, Collection<Span> collection) {
            return ThriftCodec.readList(bArr, collection);
        }

        @Override // zipkin2.codec.BytesDecoder
        public Span decodeOne(byte[] bArr) {
            return ThriftCodec.readOne(bArr);
        }

        @Override // zipkin2.codec.BytesDecoder
        public List<Span> decodeList(byte[] bArr) {
            return decodeList(this, bArr);
        }
    },
    JSON_V2 { // from class: zipkin2.codec.SpanBytesDecoder.3
        @Override // zipkin2.codec.BytesDecoder
        public Encoding encoding() {
            return Encoding.JSON;
        }

        @Override // zipkin2.codec.BytesDecoder
        public boolean decode(byte[] bArr, Collection<Span> collection) {
            return JsonCodec.read(new V2SpanReader(), bArr, collection);
        }

        @Override // zipkin2.codec.BytesDecoder
        public boolean decodeList(byte[] bArr, Collection<Span> collection) {
            return JsonCodec.readList(new V2SpanReader(), bArr, collection);
        }

        @Override // zipkin2.codec.BytesDecoder
        @Nullable
        public Span decodeOne(byte[] bArr) {
            return (Span) JsonCodec.readOne(new V2SpanReader(), bArr);
        }

        @Override // zipkin2.codec.BytesDecoder
        public List<Span> decodeList(byte[] bArr) {
            return decodeList(this, bArr);
        }
    },
    PROTO3 { // from class: zipkin2.codec.SpanBytesDecoder.4
        @Override // zipkin2.codec.BytesDecoder
        public Encoding encoding() {
            return Encoding.PROTO3;
        }

        @Override // zipkin2.codec.BytesDecoder
        public boolean decode(byte[] bArr, Collection<Span> collection) {
            return Proto3Codec.read(bArr, collection);
        }

        @Override // zipkin2.codec.BytesDecoder
        public boolean decodeList(byte[] bArr, Collection<Span> collection) {
            return Proto3Codec.readList(bArr, collection);
        }

        @Override // zipkin2.codec.BytesDecoder
        @Nullable
        public Span decodeOne(byte[] bArr) {
            return Proto3Codec.readOne(bArr);
        }

        @Override // zipkin2.codec.BytesDecoder
        public List<Span> decodeList(byte[] bArr) {
            return decodeList(this, bArr);
        }
    };

    static List<Span> decodeList(SpanBytesDecoder spanBytesDecoder, byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        return !spanBytesDecoder.decodeList(bArr, arrayList) ? Collections.emptyList() : arrayList;
    }
}
