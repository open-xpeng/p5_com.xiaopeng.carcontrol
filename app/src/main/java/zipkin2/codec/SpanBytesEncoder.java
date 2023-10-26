package zipkin2.codec;

import java.util.List;
import zipkin2.Span;
import zipkin2.internal.JsonCodec;
import zipkin2.internal.Proto3Codec;
import zipkin2.internal.V1JsonSpanWriter;
import zipkin2.internal.V1ThriftSpanWriter;
import zipkin2.internal.V2SpanWriter;

/* loaded from: classes3.dex */
public enum SpanBytesEncoder implements BytesEncoder<Span> {
    JSON_V1 { // from class: zipkin2.codec.SpanBytesEncoder.1
        @Override // zipkin2.codec.BytesEncoder
        public Encoding encoding() {
            return Encoding.JSON;
        }

        @Override // zipkin2.codec.BytesEncoder
        public int sizeInBytes(Span span) {
            return new V1JsonSpanWriter().sizeInBytes(span);
        }

        @Override // zipkin2.codec.BytesEncoder
        public byte[] encode(Span span) {
            return JsonCodec.write(new V1JsonSpanWriter(), span);
        }

        @Override // zipkin2.codec.BytesEncoder
        public byte[] encodeList(List<Span> list) {
            return JsonCodec.writeList(new V1JsonSpanWriter(), list);
        }

        @Override // zipkin2.codec.SpanBytesEncoder
        public int encodeList(List<Span> list, byte[] bArr, int i) {
            return JsonCodec.writeList(new V1JsonSpanWriter(), list, bArr, i);
        }
    },
    THRIFT { // from class: zipkin2.codec.SpanBytesEncoder.2
        @Override // zipkin2.codec.BytesEncoder
        public Encoding encoding() {
            return Encoding.THRIFT;
        }

        @Override // zipkin2.codec.BytesEncoder
        public int sizeInBytes(Span span) {
            return new V1ThriftSpanWriter().sizeInBytes(span);
        }

        @Override // zipkin2.codec.BytesEncoder
        public byte[] encode(Span span) {
            return new V1ThriftSpanWriter().write(span);
        }

        @Override // zipkin2.codec.BytesEncoder
        public byte[] encodeList(List<Span> list) {
            return new V1ThriftSpanWriter().writeList(list);
        }

        @Override // zipkin2.codec.SpanBytesEncoder
        public int encodeList(List<Span> list, byte[] bArr, int i) {
            return new V1ThriftSpanWriter().writeList(list, bArr, i);
        }
    },
    JSON_V2 { // from class: zipkin2.codec.SpanBytesEncoder.3
        final V2SpanWriter writer = new V2SpanWriter();

        @Override // zipkin2.codec.BytesEncoder
        public Encoding encoding() {
            return Encoding.JSON;
        }

        @Override // zipkin2.codec.BytesEncoder
        public int sizeInBytes(Span span) {
            return this.writer.sizeInBytes(span);
        }

        @Override // zipkin2.codec.BytesEncoder
        public byte[] encode(Span span) {
            return JsonCodec.write(this.writer, span);
        }

        @Override // zipkin2.codec.BytesEncoder
        public byte[] encodeList(List<Span> list) {
            return JsonCodec.writeList(this.writer, list);
        }

        @Override // zipkin2.codec.SpanBytesEncoder
        public int encodeList(List<Span> list, byte[] bArr, int i) {
            return JsonCodec.writeList(this.writer, list, bArr, i);
        }
    },
    PROTO3 { // from class: zipkin2.codec.SpanBytesEncoder.4
        final Proto3Codec codec = new Proto3Codec();

        @Override // zipkin2.codec.BytesEncoder
        public Encoding encoding() {
            return Encoding.PROTO3;
        }

        @Override // zipkin2.codec.BytesEncoder
        public int sizeInBytes(Span span) {
            return this.codec.sizeInBytes(span);
        }

        @Override // zipkin2.codec.BytesEncoder
        public byte[] encode(Span span) {
            return this.codec.write(span);
        }

        @Override // zipkin2.codec.BytesEncoder
        public byte[] encodeList(List<Span> list) {
            return this.codec.writeList(list);
        }

        @Override // zipkin2.codec.SpanBytesEncoder
        public int encodeList(List<Span> list, byte[] bArr, int i) {
            return this.codec.writeList(list, bArr, i);
        }
    };

    public abstract int encodeList(List<Span> list, byte[] bArr, int i);
}
