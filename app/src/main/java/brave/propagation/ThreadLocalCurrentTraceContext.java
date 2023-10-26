package brave.propagation;

import brave.internal.Nullable;
import brave.propagation.CurrentTraceContext;
import java.util.Objects;

/* loaded from: classes.dex */
public class ThreadLocalCurrentTraceContext extends CurrentTraceContext {
    static final ThreadLocal<TraceContext> DEFAULT = new ThreadLocal<>();
    final ThreadLocal<TraceContext> local;

    public static CurrentTraceContext create() {
        return new Builder().build();
    }

    public static CurrentTraceContext.Builder newBuilder() {
        return new Builder();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class Builder extends CurrentTraceContext.Builder {
        @Override // brave.propagation.CurrentTraceContext.Builder
        public CurrentTraceContext build() {
            return new ThreadLocalCurrentTraceContext(this, ThreadLocalCurrentTraceContext.DEFAULT);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ThreadLocalCurrentTraceContext(CurrentTraceContext.Builder builder, ThreadLocal<TraceContext> threadLocal) {
        super(builder);
        Objects.requireNonNull(threadLocal, "local == null");
        this.local = threadLocal;
    }

    @Override // brave.propagation.CurrentTraceContext
    public TraceContext get() {
        return this.local.get();
    }

    @Override // brave.propagation.CurrentTraceContext
    public CurrentTraceContext.Scope newScope(@Nullable TraceContext traceContext) {
        final TraceContext traceContext2 = this.local.get();
        this.local.set(traceContext);
        return decorateScope(traceContext, new CurrentTraceContext.Scope() { // from class: brave.propagation.ThreadLocalCurrentTraceContext.1ThreadLocalScope
            @Override // brave.propagation.CurrentTraceContext.Scope, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                ThreadLocalCurrentTraceContext.this.local.set(traceContext2);
            }
        });
    }
}
