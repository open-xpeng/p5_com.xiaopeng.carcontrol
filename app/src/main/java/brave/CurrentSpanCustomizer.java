package brave;

/* loaded from: classes.dex */
public final class CurrentSpanCustomizer implements SpanCustomizer {
    private final Tracer tracer;

    public static CurrentSpanCustomizer create(Tracing tracing) {
        return new CurrentSpanCustomizer(tracing);
    }

    CurrentSpanCustomizer(Tracing tracing) {
        this.tracer = tracing.tracer();
    }

    @Override // brave.SpanCustomizer
    public SpanCustomizer name(String str) {
        return this.tracer.currentSpanCustomizer().name(str);
    }

    @Override // brave.SpanCustomizer
    public SpanCustomizer tag(String str, String str2) {
        return this.tracer.currentSpanCustomizer().tag(str, str2);
    }

    @Override // brave.SpanCustomizer
    public SpanCustomizer annotate(String str) {
        return this.tracer.currentSpanCustomizer().annotate(str);
    }
}
