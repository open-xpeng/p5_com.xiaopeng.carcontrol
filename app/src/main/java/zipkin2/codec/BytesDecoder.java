package zipkin2.codec;

import java.util.Collection;
import java.util.List;
import zipkin2.internal.Nullable;

/* loaded from: classes3.dex */
public interface BytesDecoder<T> {
    boolean decode(byte[] bArr, Collection<T> collection);

    List<T> decodeList(byte[] bArr);

    boolean decodeList(byte[] bArr, Collection<T> collection);

    @Nullable
    T decodeOne(byte[] bArr);

    Encoding encoding();
}
