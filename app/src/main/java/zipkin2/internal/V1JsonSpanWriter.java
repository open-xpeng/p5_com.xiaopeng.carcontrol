package zipkin2.internal;

import zipkin2.Span;
import zipkin2.internal.Buffer;
import zipkin2.v1.V2SpanConverter;

/* loaded from: classes3.dex */
public final class V1JsonSpanWriter implements Buffer.Writer<Span> {
    final V2SpanConverter converter = V2SpanConverter.create();
    final V1SpanWriter v1SpanWriter = new V1SpanWriter();

    @Override // zipkin2.internal.Buffer.Writer
    public int sizeInBytes(Span span) {
        return this.v1SpanWriter.sizeInBytes(this.converter.convert(span));
    }

    @Override // zipkin2.internal.Buffer.Writer
    public void write(Span span, Buffer buffer) {
        this.v1SpanWriter.write(this.converter.convert(span), buffer);
    }
}
