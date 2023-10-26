package zipkin2;

import org.apache.commons.compress.archivers.tar.TarConstants;
import zipkin2.codec.BytesDecoder;
import zipkin2.codec.SpanBytesDecoder;

/* loaded from: classes3.dex */
public final class SpanBytesDecoderDetector {
    static final byte[] ENDPOINT_FIELD_SUFFIX = {69, 110, 100, 112, 111, 105, 110, 116, 34};
    static final byte[] TAGS_FIELD = {34, 116, 97, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 115, 34};

    public static BytesDecoder<Span> decoderForMessage(byte[] bArr) {
        BytesDecoder<Span> detectDecoder = detectDecoder(bArr);
        if (bArr[0] == 12 || bArr[0] == 91) {
            throw new IllegalArgumentException("Expected json or thrift object, not list encoding");
        }
        if (detectDecoder == SpanBytesDecoder.JSON_V2 || detectDecoder == SpanBytesDecoder.PROTO3) {
            throw new UnsupportedOperationException("v2 formats should only be used with list messages");
        }
        return detectDecoder;
    }

    public static BytesDecoder<Span> decoderForListMessage(byte[] bArr) {
        BytesDecoder<Span> detectDecoder = detectDecoder(bArr);
        if (bArr[0] == 12 || protobuf3(bArr) || bArr[0] == 91) {
            return detectDecoder;
        }
        throw new IllegalArgumentException("Expected json, proto3 or thrift list encoding");
    }

    static BytesDecoder<Span> detectDecoder(byte[] bArr) {
        if (bArr[0] <= 16) {
            return protobuf3(bArr) ? SpanBytesDecoder.PROTO3 : SpanBytesDecoder.THRIFT;
        } else if (bArr[0] != 91 && bArr[0] != 123) {
            throw new IllegalArgumentException("Could not detect the span format");
        } else {
            if (!contains(bArr, ENDPOINT_FIELD_SUFFIX) && !contains(bArr, TAGS_FIELD)) {
                return SpanBytesDecoder.JSON_V1;
            }
            return SpanBytesDecoder.JSON_V2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0015, code lost:
        r1 = r1 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static boolean contains(byte[] r6, byte[] r7) {
        /*
            r0 = 0
            r1 = r0
        L2:
            int r2 = r6.length
            int r3 = r7.length
            int r2 = r2 - r3
            r3 = 1
            int r2 = r2 + r3
            if (r1 >= r2) goto L1c
            r2 = r0
        La:
            int r4 = r7.length
            if (r2 >= r4) goto L1b
            int r4 = r1 + r2
            r4 = r6[r4]
            r5 = r7[r2]
            if (r4 == r5) goto L18
            int r1 = r1 + 1
            goto L2
        L18:
            int r2 = r2 + 1
            goto La
        L1b:
            return r3
        L1c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: zipkin2.SpanBytesDecoderDetector.contains(byte[], byte[]):boolean");
    }

    static boolean protobuf3(byte[] bArr) {
        return bArr[0] == 10 && bArr[1] != 0;
    }

    SpanBytesDecoderDetector() {
    }
}
