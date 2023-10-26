package brave.propagation;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.internal.Nullable;
import java.util.ArrayDeque;
import java.util.Objects;

/* loaded from: classes.dex */
public class ThreadLocalSpan {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final ThreadLocalSpan CURRENT_TRACER = new ThreadLocalSpan(null);
    final ThreadLocal<ArrayDeque<Object[]>> currentSpanInScopeStack = new ThreadLocal<>();
    @Nullable
    final Tracer tracer;

    public static ThreadLocalSpan create(Tracer tracer) {
        Objects.requireNonNull(tracer, "tracer == null");
        return new ThreadLocalSpan(tracer);
    }

    ThreadLocalSpan(Tracer tracer) {
        this.tracer = tracer;
    }

    Tracer tracer() {
        Tracer tracer = this.tracer;
        return tracer != null ? tracer : Tracing.currentTracer();
    }

    @Nullable
    public Span next(TraceContextOrSamplingFlags traceContextOrSamplingFlags) {
        Tracer tracer = tracer();
        if (tracer == null) {
            return null;
        }
        Span nextSpan = tracer.nextSpan(traceContextOrSamplingFlags);
        getCurrentSpanInScopeStack().addFirst(new Object[]{nextSpan, tracer.withSpanInScope(nextSpan)});
        return nextSpan;
    }

    @Nullable
    public Span next() {
        Tracer tracer = tracer();
        if (tracer == null) {
            return null;
        }
        Span nextSpan = tracer.nextSpan();
        getCurrentSpanInScopeStack().addFirst(new Object[]{nextSpan, tracer.withSpanInScope(nextSpan)});
        return nextSpan;
    }

    @Nullable
    public Span remove() {
        Tracer tracer = tracer();
        Span currentSpan = tracer != null ? tracer.currentSpan() : null;
        Object[] pollFirst = getCurrentSpanInScopeStack().pollFirst();
        if (pollFirst == null) {
            return currentSpan;
        }
        Span span = (Span) pollFirst[0];
        ((Tracer.SpanInScope) pollFirst[1]).close();
        return currentSpan;
    }

    ArrayDeque<Object[]> getCurrentSpanInScopeStack() {
        ArrayDeque<Object[]> arrayDeque = this.currentSpanInScopeStack.get();
        if (arrayDeque == null) {
            ArrayDeque<Object[]> arrayDeque2 = new ArrayDeque<>();
            this.currentSpanInScopeStack.set(arrayDeque2);
            return arrayDeque2;
        }
        return arrayDeque;
    }
}
