package brave;

import brave.internal.Nullable;
import brave.propagation.TraceContext;
import zipkin2.Endpoint;

/* loaded from: classes.dex */
public abstract class Span implements SpanCustomizer {

    /* loaded from: classes.dex */
    public enum Kind {
        CLIENT,
        SERVER,
        PRODUCER,
        CONSUMER
    }

    public abstract void abandon();

    public abstract Span annotate(long j, String str);

    @Override // brave.SpanCustomizer
    public abstract Span annotate(String str);

    public abstract TraceContext context();

    public abstract SpanCustomizer customizer();

    public abstract Span error(Throwable th);

    public abstract void finish();

    public abstract void finish(long j);

    public abstract void flush();

    public abstract boolean isNoop();

    public abstract Span kind(Kind kind);

    @Override // brave.SpanCustomizer
    public abstract Span name(String str);

    public abstract boolean remoteIpAndPort(@Nullable String str, int i);

    public abstract Span remoteServiceName(String str);

    public abstract Span start();

    public abstract Span start(long j);

    @Override // brave.SpanCustomizer
    public abstract Span tag(String str, String str2);

    @Deprecated
    public Span remoteEndpoint(Endpoint endpoint) {
        if (endpoint == null) {
            return this;
        }
        if (endpoint.serviceName() != null) {
            remoteServiceName(endpoint.serviceName());
        }
        remoteIpAndPort(endpoint.ipv6() != null ? endpoint.ipv6() : endpoint.ipv4(), endpoint.portAsInt());
        return this;
    }
}
