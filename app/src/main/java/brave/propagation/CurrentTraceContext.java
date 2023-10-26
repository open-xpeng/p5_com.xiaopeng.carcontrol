package brave.propagation;

import brave.internal.Nullable;
import brave.internal.WrappingExecutorService;
import brave.propagation.ThreadLocalCurrentTraceContext;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/* loaded from: classes.dex */
public abstract class CurrentTraceContext {
    final List<ScopeDecorator> scopeDecorators;

    /* loaded from: classes.dex */
    public interface Scope extends Closeable {
        public static final Scope NOOP = new Scope() { // from class: brave.propagation.CurrentTraceContext.Scope.1
            @Override // brave.propagation.CurrentTraceContext.Scope, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            public String toString() {
                return "NoopScope";
            }
        };

        @Override // java.io.Closeable, java.lang.AutoCloseable
        void close();
    }

    /* loaded from: classes.dex */
    public interface ScopeDecorator {
        Scope decorateScope(@Nullable TraceContext traceContext, Scope scope);
    }

    @Nullable
    public abstract TraceContext get();

    public abstract Scope newScope(@Nullable TraceContext traceContext);

    static {
        SamplingFlags.DEBUG.toString();
    }

    /* loaded from: classes.dex */
    public static abstract class Builder {
        ArrayList<ScopeDecorator> scopeDecorators = new ArrayList<>();

        public abstract CurrentTraceContext build();

        public Builder addScopeDecorator(ScopeDecorator scopeDecorator) {
            Objects.requireNonNull(scopeDecorator, "scopeDecorator == null");
            this.scopeDecorators.add(scopeDecorator);
            return this;
        }
    }

    protected CurrentTraceContext() {
        this.scopeDecorators = Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CurrentTraceContext(Builder builder) {
        this.scopeDecorators = new ArrayList(builder.scopeDecorators);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Scope decorateScope(@Nullable TraceContext traceContext, Scope scope) {
        int size = this.scopeDecorators.size();
        for (int i = 0; i < size; i++) {
            scope = this.scopeDecorators.get(i).decorateScope(traceContext, scope);
        }
        return scope;
    }

    public Scope maybeScope(@Nullable TraceContext traceContext) {
        TraceContext traceContext2 = get();
        if (traceContext != null) {
            return traceContext.equals(traceContext2) ? Scope.NOOP : newScope(traceContext);
        } else if (traceContext2 == null) {
            return Scope.NOOP;
        } else {
            return newScope(null);
        }
    }

    /* loaded from: classes.dex */
    public static final class Default extends ThreadLocalCurrentTraceContext {
        static final InheritableThreadLocal<TraceContext> INHERITABLE = new InheritableThreadLocal<>();

        public static CurrentTraceContext create() {
            return new ThreadLocalCurrentTraceContext(new ThreadLocalCurrentTraceContext.Builder(), DEFAULT);
        }

        public static CurrentTraceContext inheritable() {
            return new Default();
        }

        Default() {
            super(new ThreadLocalCurrentTraceContext.Builder(), INHERITABLE);
        }
    }

    public <C> Callable<C> wrap(final Callable<C> callable) {
        final TraceContext traceContext = get();
        return new Callable<C>() { // from class: brave.propagation.CurrentTraceContext.1CurrentTraceContextCallable
            @Override // java.util.concurrent.Callable
            public C call() throws Exception {
                Scope maybeScope = CurrentTraceContext.this.maybeScope(traceContext);
                try {
                    C c = (C) callable.call();
                    if (maybeScope != null) {
                        maybeScope.close();
                    }
                    return c;
                } catch (Throwable th) {
                    if (maybeScope != null) {
                        try {
                            maybeScope.close();
                        } catch (Throwable unused) {
                        }
                    }
                    throw th;
                }
            }
        };
    }

    public Runnable wrap(final Runnable runnable) {
        final TraceContext traceContext = get();
        return new Runnable() { // from class: brave.propagation.CurrentTraceContext.1CurrentTraceContextRunnable
            @Override // java.lang.Runnable
            public void run() {
                Scope maybeScope = CurrentTraceContext.this.maybeScope(traceContext);
                try {
                    runnable.run();
                    if (maybeScope != null) {
                        maybeScope.close();
                    }
                } catch (Throwable th) {
                    if (maybeScope != null) {
                        try {
                            maybeScope.close();
                        } catch (Throwable unused) {
                        }
                    }
                    throw th;
                }
            }
        };
    }

    public Executor executor(final Executor executor) {
        return new Executor() { // from class: brave.propagation.CurrentTraceContext.1CurrentTraceContextExecutor
            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) {
                executor.execute(CurrentTraceContext.this.wrap(runnable));
            }
        };
    }

    public ExecutorService executorService(final ExecutorService executorService) {
        return new WrappingExecutorService() { // from class: brave.propagation.CurrentTraceContext.1CurrentTraceContextExecutorService
            @Override // brave.internal.WrappingExecutorService
            protected ExecutorService delegate() {
                return executorService;
            }

            @Override // brave.internal.WrappingExecutorService
            protected <C> Callable<C> wrap(Callable<C> callable) {
                return CurrentTraceContext.this.wrap(callable);
            }

            @Override // brave.internal.WrappingExecutorService
            protected Runnable wrap(Runnable runnable) {
                return CurrentTraceContext.this.wrap(runnable);
            }
        };
    }
}
