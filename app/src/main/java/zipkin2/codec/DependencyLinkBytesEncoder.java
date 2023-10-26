package zipkin2.codec;

import java.util.List;
import zipkin2.DependencyLink;
import zipkin2.internal.Buffer;
import zipkin2.internal.JsonCodec;
import zipkin2.internal.JsonEscaper;

/* loaded from: classes3.dex */
public enum DependencyLinkBytesEncoder implements BytesEncoder<DependencyLink> {
    JSON_V1 { // from class: zipkin2.codec.DependencyLinkBytesEncoder.1
        @Override // zipkin2.codec.BytesEncoder
        public Encoding encoding() {
            return Encoding.JSON;
        }

        @Override // zipkin2.codec.BytesEncoder
        public int sizeInBytes(DependencyLink dependencyLink) {
            return WRITER.sizeInBytes(dependencyLink);
        }

        @Override // zipkin2.codec.BytesEncoder
        public byte[] encode(DependencyLink dependencyLink) {
            return JsonCodec.write(WRITER, dependencyLink);
        }

        @Override // zipkin2.codec.BytesEncoder
        public byte[] encodeList(List<DependencyLink> list) {
            return JsonCodec.writeList(WRITER, list);
        }
    };
    
    static final Buffer.Writer<DependencyLink> WRITER = new Buffer.Writer<DependencyLink>() { // from class: zipkin2.codec.DependencyLinkBytesEncoder.2
        public String toString() {
            return "DependencyLink";
        }

        @Override // zipkin2.internal.Buffer.Writer
        public int sizeInBytes(DependencyLink dependencyLink) {
            int jsonEscapedSizeInBytes = JsonEscaper.jsonEscapedSizeInBytes(dependencyLink.parent()) + 37 + JsonEscaper.jsonEscapedSizeInBytes(dependencyLink.child()) + Buffer.asciiSizeInBytes(dependencyLink.callCount());
            return dependencyLink.errorCount() > 0 ? jsonEscapedSizeInBytes + 14 + Buffer.asciiSizeInBytes(dependencyLink.errorCount()) : jsonEscapedSizeInBytes;
        }

        @Override // zipkin2.internal.Buffer.Writer
        public void write(DependencyLink dependencyLink, Buffer buffer) {
            buffer.writeAscii("{\"parent\":\"").writeUtf8(JsonEscaper.jsonEscape(dependencyLink.parent()));
            buffer.writeAscii("\",\"child\":\"").writeUtf8(JsonEscaper.jsonEscape(dependencyLink.child()));
            buffer.writeAscii("\",\"callCount\":").writeAscii(dependencyLink.callCount());
            if (dependencyLink.errorCount() > 0) {
                buffer.writeAscii(",\"errorCount\":").writeAscii(dependencyLink.errorCount());
            }
            buffer.writeByte(125);
        }
    };
}
