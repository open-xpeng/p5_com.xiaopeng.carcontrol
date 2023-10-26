package zipkin2.reporter;

import java.util.Collections;
import java.util.List;
import zipkin2.Call;
import zipkin2.Component;
import zipkin2.codec.Encoding;

/* loaded from: classes3.dex */
public abstract class Sender extends Component {
    public abstract Encoding encoding();

    public abstract int messageMaxBytes();

    public abstract int messageSizeInBytes(List<byte[]> list);

    public abstract Call<Void> sendSpans(List<byte[]> list);

    public int messageSizeInBytes(int i) {
        return messageSizeInBytes(Collections.singletonList(new byte[i]));
    }
}
