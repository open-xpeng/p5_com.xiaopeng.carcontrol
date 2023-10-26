package brave;

import brave.propagation.CurrentTraceContext;
import brave.propagation.TraceContext;

/* loaded from: classes.dex */
final class NoopScopedSpan extends ScopedSpan {
    final TraceContext context;
    final CurrentTraceContext.Scope scope;

    @Override // brave.ScopedSpan
    public ScopedSpan annotate(String str) {
        return this;
    }

    @Override // brave.ScopedSpan
    public ScopedSpan error(Throwable th) {
        return this;
    }

    @Override // brave.ScopedSpan
    public boolean isNoop() {
        return true;
    }

    @Override // brave.ScopedSpan
    public ScopedSpan tag(String str, String str2) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public NoopScopedSpan(TraceContext traceContext, CurrentTraceContext.Scope scope) {
        this.context = traceContext;
        this.scope = scope;
    }

    @Override // brave.ScopedSpan
    public TraceContext context() {
        return this.context;
    }

    @Override // brave.ScopedSpan
    public void finish() {
        this.scope.close();
    }

    public String toString() {
        return "NoopScopedSpan(" + this.context + ")";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof NoopScopedSpan) {
            NoopScopedSpan noopScopedSpan = (NoopScopedSpan) obj;
            return this.context.equals(noopScopedSpan.context) && this.scope.equals(noopScopedSpan.scope);
        }
        return false;
    }

    public int hashCode() {
        return ((this.context.hashCode() ^ 1000003) * 1000003) ^ this.scope.hashCode();
    }
}
