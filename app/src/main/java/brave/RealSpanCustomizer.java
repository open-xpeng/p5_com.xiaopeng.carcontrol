package brave;

import brave.handler.MutableSpan;
import brave.propagation.TraceContext;

/* loaded from: classes.dex */
final class RealSpanCustomizer implements SpanCustomizer {
    final Clock clock;
    final TraceContext context;
    final MutableSpan state;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RealSpanCustomizer(TraceContext traceContext, MutableSpan mutableSpan, Clock clock) {
        this.context = traceContext;
        this.state = mutableSpan;
        this.clock = clock;
    }

    @Override // brave.SpanCustomizer
    public SpanCustomizer name(String str) {
        synchronized (this.state) {
            this.state.name(str);
        }
        return this;
    }

    @Override // brave.SpanCustomizer
    public SpanCustomizer annotate(String str) {
        long currentTimeMicroseconds = this.clock.currentTimeMicroseconds();
        synchronized (this.state) {
            this.state.annotate(currentTimeMicroseconds, str);
        }
        return this;
    }

    @Override // brave.SpanCustomizer
    public SpanCustomizer tag(String str, String str2) {
        synchronized (this.state) {
            this.state.tag(str, str2);
        }
        return this;
    }

    public String toString() {
        return "RealSpanCustomizer(" + this.context + ")";
    }
}
