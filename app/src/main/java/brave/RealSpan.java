package brave;

import brave.Span;
import brave.handler.FinishedSpanHandler;
import brave.handler.MutableSpan;
import brave.internal.recorder.PendingSpans;
import brave.propagation.TraceContext;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class RealSpan extends Span {
    final Clock clock;
    final TraceContext context;
    final RealSpanCustomizer customizer;
    final FinishedSpanHandler finishedSpanHandler;
    final PendingSpans pendingSpans;
    final MutableSpan state;

    @Override // brave.Span
    public boolean isNoop() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RealSpan(TraceContext traceContext, PendingSpans pendingSpans, MutableSpan mutableSpan, Clock clock, FinishedSpanHandler finishedSpanHandler) {
        this.context = traceContext;
        this.pendingSpans = pendingSpans;
        this.state = mutableSpan;
        this.clock = clock;
        this.customizer = new RealSpanCustomizer(traceContext, mutableSpan, clock);
        this.finishedSpanHandler = finishedSpanHandler;
    }

    @Override // brave.Span
    public TraceContext context() {
        return this.context;
    }

    @Override // brave.Span
    public SpanCustomizer customizer() {
        return new RealSpanCustomizer(this.context, this.state, this.clock);
    }

    @Override // brave.Span
    public Span start() {
        return start(this.clock.currentTimeMicroseconds());
    }

    @Override // brave.Span
    public Span start(long j) {
        synchronized (this.state) {
            this.state.startTimestamp(j);
        }
        return this;
    }

    @Override // brave.Span, brave.SpanCustomizer
    public Span name(String str) {
        synchronized (this.state) {
            this.state.name(str);
        }
        return this;
    }

    @Override // brave.Span
    public Span kind(Span.Kind kind) {
        synchronized (this.state) {
            this.state.kind(kind);
        }
        return this;
    }

    @Override // brave.Span, brave.SpanCustomizer
    public Span annotate(String str) {
        return annotate(this.clock.currentTimeMicroseconds(), str);
    }

    @Override // brave.Span
    public Span annotate(long j, String str) {
        if ("cs".equals(str)) {
            synchronized (this.state) {
                this.state.kind(Span.Kind.CLIENT);
                this.state.startTimestamp(j);
            }
        } else if ("sr".equals(str)) {
            synchronized (this.state) {
                this.state.kind(Span.Kind.SERVER);
                this.state.startTimestamp(j);
            }
        } else if ("cr".equals(str)) {
            synchronized (this.state) {
                this.state.kind(Span.Kind.CLIENT);
            }
            finish(j);
        } else if ("ss".equals(str)) {
            synchronized (this.state) {
                this.state.kind(Span.Kind.SERVER);
            }
            finish(j);
        } else {
            synchronized (this.state) {
                this.state.annotate(j, str);
            }
        }
        return this;
    }

    @Override // brave.Span, brave.SpanCustomizer
    public Span tag(String str, String str2) {
        synchronized (this.state) {
            this.state.tag(str, str2);
        }
        return this;
    }

    @Override // brave.Span
    public Span error(Throwable th) {
        synchronized (this.state) {
            this.state.error(th);
        }
        return this;
    }

    @Override // brave.Span
    public Span remoteServiceName(String str) {
        synchronized (this.state) {
            this.state.remoteServiceName(str);
        }
        return this;
    }

    @Override // brave.Span
    public boolean remoteIpAndPort(String str, int i) {
        boolean remoteIpAndPort;
        synchronized (this.state) {
            remoteIpAndPort = this.state.remoteIpAndPort(str, i);
        }
        return remoteIpAndPort;
    }

    @Override // brave.Span
    public void finish() {
        finish(this.clock.currentTimeMicroseconds());
    }

    @Override // brave.Span
    public void finish(long j) {
        if (this.pendingSpans.remove(this.context)) {
            synchronized (this.state) {
                this.state.finishTimestamp(j);
            }
            this.finishedSpanHandler.handle(this.context, this.state);
        }
    }

    @Override // brave.Span
    public void abandon() {
        this.pendingSpans.remove(this.context);
    }

    @Override // brave.Span
    public void flush() {
        abandon();
        this.finishedSpanHandler.handle(this.context, this.state);
    }

    public String toString() {
        return "RealSpan(" + this.context + ")";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RealSpan) {
            return this.context.equals(((RealSpan) obj).context);
        }
        return false;
    }

    public int hashCode() {
        return this.context.hashCode();
    }
}
