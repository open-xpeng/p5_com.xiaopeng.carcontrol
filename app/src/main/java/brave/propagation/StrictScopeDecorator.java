package brave.propagation;

import brave.internal.Nullable;
import brave.propagation.CurrentTraceContext;

/* loaded from: classes.dex */
public final class StrictScopeDecorator implements CurrentTraceContext.ScopeDecorator {
    public static CurrentTraceContext.ScopeDecorator create() {
        return new StrictScopeDecorator();
    }

    @Override // brave.propagation.CurrentTraceContext.ScopeDecorator
    public CurrentTraceContext.Scope decorateScope(@Nullable TraceContext traceContext, CurrentTraceContext.Scope scope) {
        return new StrictScope(scope, new Error(String.format("Thread %s opened scope for %s here:", Thread.currentThread().getName(), traceContext)));
    }

    /* loaded from: classes.dex */
    static final class StrictScope implements CurrentTraceContext.Scope {
        final Throwable caller;
        final CurrentTraceContext.Scope delegate;
        final long threadId = Thread.currentThread().getId();

        StrictScope(CurrentTraceContext.Scope scope, Throwable th) {
            this.delegate = scope;
            this.caller = th;
        }

        @Override // brave.propagation.CurrentTraceContext.Scope, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (Thread.currentThread().getId() != this.threadId) {
                throw new IllegalStateException("scope closed in a different thread: " + Thread.currentThread().getName(), this.caller);
            }
            this.delegate.close();
        }

        public String toString() {
            return this.caller.toString();
        }
    }
}
