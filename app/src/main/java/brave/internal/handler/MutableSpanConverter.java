package brave.internal.handler;

import brave.ErrorParser;
import brave.Span;
import brave.handler.MutableSpan;
import brave.internal.Nullable;
import java.util.Objects;
import org.eclipse.paho.android.service.MqttServiceConstants;
import zipkin2.Endpoint;
import zipkin2.Span;

/* loaded from: classes.dex */
public final class MutableSpanConverter {
    final ErrorParser errorParser;
    final Endpoint localEndpoint;
    @Nullable
    final String localIp;
    final int localPort;
    final String localServiceName;

    public MutableSpanConverter(ErrorParser errorParser, String str, String str2, int i) {
        Objects.requireNonNull(errorParser, "errorParser == null");
        this.errorParser = errorParser;
        Objects.requireNonNull(str, "localServiceName == null");
        this.localServiceName = str;
        this.localIp = str2;
        this.localPort = i;
        this.localEndpoint = Endpoint.newBuilder().serviceName(str).ip(str2).port(i).build();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void convert(MutableSpan mutableSpan, Span.Builder builder) {
        builder.name(mutableSpan.name());
        long startTimestamp = mutableSpan.startTimestamp();
        long finishTimestamp = mutableSpan.finishTimestamp();
        builder.timestamp(startTimestamp);
        if (startTimestamp != 0 && finishTimestamp != 0) {
            builder.duration(Math.max(finishTimestamp - startTimestamp, 1L));
        }
        Span.Kind kind = mutableSpan.kind();
        if (kind != null && kind.ordinal() < Span.Kind.values().length) {
            builder.kind(Span.Kind.values()[kind.ordinal()]);
        }
        addLocalEndpoint(mutableSpan.localServiceName(), mutableSpan.localIp(), mutableSpan.localPort(), builder);
        String remoteServiceName = mutableSpan.remoteServiceName();
        String remoteIp = mutableSpan.remoteIp();
        if (remoteServiceName != null || remoteIp != null) {
            builder.remoteEndpoint(Endpoint.newBuilder().serviceName(remoteServiceName).ip(remoteIp).port(mutableSpan.remotePort()).build());
        }
        if (mutableSpan.tag(MqttServiceConstants.TRACE_ERROR) == null && mutableSpan.error() != null) {
            this.errorParser.error(mutableSpan.error(), mutableSpan);
        }
        mutableSpan.forEachTag(Consumer.INSTANCE, builder);
        mutableSpan.forEachAnnotation(Consumer.INSTANCE, builder);
        if (mutableSpan.shared()) {
            builder.shared(true);
        }
    }

    void addLocalEndpoint(String str, @Nullable String str2, int i, Span.Builder builder) {
        String str3;
        if (str == null) {
            str = this.localServiceName;
        }
        if (str2 == null) {
            str2 = this.localIp;
        }
        if (i <= 0) {
            i = this.localPort;
        }
        if (this.localServiceName.equals(str) && ((str3 = this.localIp) != null ? str3.equals(str2) : str2 == null) && this.localPort == i) {
            builder.localEndpoint(this.localEndpoint);
        } else {
            builder.localEndpoint(Endpoint.newBuilder().serviceName(str).ip(str2).port(i).build());
        }
    }

    /* loaded from: classes.dex */
    enum Consumer implements MutableSpan.TagConsumer<Span.Builder>, MutableSpan.AnnotationConsumer<Span.Builder> {
        INSTANCE;

        @Override // brave.handler.MutableSpan.TagConsumer
        public void accept(Span.Builder builder, String str, String str2) {
            builder.putTag(str, str2);
        }

        @Override // brave.handler.MutableSpan.AnnotationConsumer
        public void accept(Span.Builder builder, long j, String str) {
            builder.addAnnotation(j, str);
        }
    }
}
