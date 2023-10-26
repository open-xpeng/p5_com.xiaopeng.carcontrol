package brave;

/* loaded from: classes.dex */
public enum NoopSpanCustomizer implements SpanCustomizer {
    INSTANCE;

    @Override // brave.SpanCustomizer
    public SpanCustomizer annotate(String str) {
        return this;
    }

    @Override // brave.SpanCustomizer
    public SpanCustomizer name(String str) {
        return this;
    }

    @Override // brave.SpanCustomizer
    public SpanCustomizer tag(String str, String str2) {
        return this;
    }
}
