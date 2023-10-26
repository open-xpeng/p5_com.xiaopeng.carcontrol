package brave;

import brave.Span;
import brave.propagation.TraceContext;

/* loaded from: classes.dex */
final class NoopSpan extends Span {
    final TraceContext context;

    @Override // brave.Span
    public void abandon() {
    }

    @Override // brave.Span
    public Span annotate(long j, String str) {
        return this;
    }

    @Override // brave.Span, brave.SpanCustomizer
    public Span annotate(String str) {
        return this;
    }

    @Override // brave.Span
    public Span error(Throwable th) {
        return this;
    }

    @Override // brave.Span
    public void finish() {
    }

    @Override // brave.Span
    public void finish(long j) {
    }

    @Override // brave.Span
    public void flush() {
    }

    @Override // brave.Span
    public boolean isNoop() {
        return true;
    }

    @Override // brave.Span
    public Span kind(Span.Kind kind) {
        return this;
    }

    @Override // brave.Span, brave.SpanCustomizer
    public Span name(String str) {
        return this;
    }

    @Override // brave.Span
    public boolean remoteIpAndPort(String str, int i) {
        return true;
    }

    @Override // brave.Span
    public Span remoteServiceName(String str) {
        return this;
    }

    @Override // brave.Span
    public Span start() {
        return this;
    }

    @Override // brave.Span
    public Span start(long j) {
        return this;
    }

    @Override // brave.Span, brave.SpanCustomizer
    public Span tag(String str, String str2) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public NoopSpan(TraceContext traceContext) {
        this.context = traceContext;
    }

    @Override // brave.Span
    public SpanCustomizer customizer() {
        return NoopSpanCustomizer.INSTANCE;
    }

    @Override // brave.Span
    public TraceContext context() {
        return this.context;
    }

    public String toString() {
        return "NoopSpan(" + this.context + ")";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof NoopSpan) {
            return this.context.equals(((NoopSpan) obj).context);
        }
        return false;
    }

    public int hashCode() {
        return this.context.hashCode();
    }
}
