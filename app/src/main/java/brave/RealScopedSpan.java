package brave;

import brave.handler.FinishedSpanHandler;
import brave.handler.MutableSpan;
import brave.internal.recorder.PendingSpans;
import brave.propagation.CurrentTraceContext;
import brave.propagation.TraceContext;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class RealScopedSpan extends ScopedSpan {
    final Clock clock;
    final TraceContext context;
    final FinishedSpanHandler finishedSpanHandler;
    final PendingSpans pendingSpans;
    final CurrentTraceContext.Scope scope;
    final MutableSpan state;

    @Override // brave.ScopedSpan
    public boolean isNoop() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RealScopedSpan(TraceContext traceContext, CurrentTraceContext.Scope scope, MutableSpan mutableSpan, Clock clock, PendingSpans pendingSpans, FinishedSpanHandler finishedSpanHandler) {
        this.context = traceContext;
        this.scope = scope;
        this.pendingSpans = pendingSpans;
        this.state = mutableSpan;
        this.clock = clock;
        this.finishedSpanHandler = finishedSpanHandler;
    }

    @Override // brave.ScopedSpan
    public TraceContext context() {
        return this.context;
    }

    @Override // brave.ScopedSpan
    public ScopedSpan annotate(String str) {
        this.state.annotate(this.clock.currentTimeMicroseconds(), str);
        return this;
    }

    @Override // brave.ScopedSpan
    public ScopedSpan tag(String str, String str2) {
        this.state.tag(str, str2);
        return this;
    }

    @Override // brave.ScopedSpan
    public ScopedSpan error(Throwable th) {
        this.state.error(th);
        return this;
    }

    @Override // brave.ScopedSpan
    public void finish() {
        this.scope.close();
        if (this.pendingSpans.remove(this.context)) {
            this.state.finishTimestamp(this.clock.currentTimeMicroseconds());
            this.finishedSpanHandler.handle(this.context, this.state);
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RealScopedSpan) {
            RealScopedSpan realScopedSpan = (RealScopedSpan) obj;
            return this.context.equals(realScopedSpan.context) && this.scope.equals(realScopedSpan.scope);
        }
        return false;
    }

    public int hashCode() {
        return ((this.context.hashCode() ^ 1000003) * 1000003) ^ this.scope.hashCode();
    }
}
