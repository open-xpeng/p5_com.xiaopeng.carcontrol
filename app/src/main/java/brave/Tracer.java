package brave;

import brave.handler.FinishedSpanHandler;
import brave.handler.MutableSpan;
import brave.internal.InternalPropagation;
import brave.internal.Lists;
import brave.internal.Nullable;
import brave.internal.Platform;
import brave.internal.recorder.PendingSpan;
import brave.internal.recorder.PendingSpans;
import brave.propagation.CurrentTraceContext;
import brave.propagation.Propagation;
import brave.propagation.SamplingFlags;
import brave.propagation.TraceContext;
import brave.propagation.TraceContextOrSamplingFlags;
import brave.propagation.TraceIdContext;
import brave.sampler.Sampler;
import java.io.Closeable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class Tracer {
    final boolean alwaysSampleLocal;
    final Clock clock;
    final CurrentTraceContext currentTraceContext;
    final FinishedSpanHandler finishedSpanHandler;
    final AtomicBoolean noop;
    final PendingSpans pendingSpans;
    final Propagation.Factory propagationFactory;
    final Sampler sampler;
    final boolean supportsJoin;
    final boolean traceId128Bit;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Tracer(Clock clock, Propagation.Factory factory, FinishedSpanHandler finishedSpanHandler, PendingSpans pendingSpans, Sampler sampler, CurrentTraceContext currentTraceContext, boolean z, boolean z2, boolean z3, AtomicBoolean atomicBoolean) {
        this.clock = clock;
        this.propagationFactory = factory;
        this.finishedSpanHandler = finishedSpanHandler;
        this.pendingSpans = pendingSpans;
        this.sampler = sampler;
        this.currentTraceContext = currentTraceContext;
        this.traceId128Bit = z;
        this.supportsJoin = z2;
        this.alwaysSampleLocal = z3;
        this.noop = atomicBoolean;
    }

    public Tracer withSampler(Sampler sampler) {
        Objects.requireNonNull(sampler, "sampler == null");
        return new Tracer(this.clock, this.propagationFactory, this.finishedSpanHandler, this.pendingSpans, sampler, this.currentTraceContext, this.traceId128Bit, this.supportsJoin, this.alwaysSampleLocal, this.noop);
    }

    public Span newTrace() {
        return _toSpan(newRootContext());
    }

    public final Span joinSpan(TraceContext traceContext) {
        Objects.requireNonNull(traceContext, "context == null");
        if (this.supportsJoin) {
            int flags = InternalPropagation.instance.flags(traceContext);
            if (this.alwaysSampleLocal && (flags & 32) != 32) {
                flags |= 32;
            }
            if ((flags & 4) != 4) {
                flags = InternalPropagation.sampled(this.sampler.isSampled(traceContext.traceId()), flags);
            } else if ((flags & 2) == 2) {
                flags |= 16;
            }
            return _toSpan(this.propagationFactory.decorate(InternalPropagation.instance.newTraceContext(flags | 64, traceContext.traceIdHigh(), traceContext.traceId(), traceContext.spanId(), traceContext.parentIdAsLong(), traceContext.spanId(), traceContext.extra())));
        }
        return newChild(traceContext);
    }

    public Span newChild(TraceContext traceContext) {
        Objects.requireNonNull(traceContext, "parent == null");
        return _toSpan(nextContext(traceContext));
    }

    TraceContext newRootContext() {
        return nextContext(64, 0L, 0L, 0L, 0L, Collections.emptyList());
    }

    TraceContext nextContext(TraceContext traceContext) {
        return nextContext(InternalPropagation.instance.flags(traceContext), traceContext.traceIdHigh(), traceContext.traceId(), traceContext.localRootId(), traceContext.spanId(), traceContext.extra());
    }

    TraceContext nextContext(int i, long j, long j2, long j3, long j4, List<Object> list) {
        long j5;
        long j6;
        int i2 = (!this.alwaysSampleLocal || (i & 32) == 32) ? i : i | 32;
        long nextId = nextId();
        if (j2 == 0) {
            j5 = this.traceId128Bit ? Platform.get().nextTraceIdHigh() : 0L;
            j6 = nextId;
        } else {
            i2 &= -81;
            j5 = j;
            j6 = j2;
        }
        if ((i2 & 4) != 4) {
            i2 = InternalPropagation.sampled(this.sampler.isSampled(j6), i2);
        }
        return this.propagationFactory.decorate(InternalPropagation.instance.newTraceContext(i2, j5, j6, j3 == 0 ? nextId : j3, j4, nextId, list));
    }

    public Span nextSpan(TraceContextOrSamplingFlags traceContextOrSamplingFlags) {
        List<Object> list;
        long j;
        long j2;
        int flags;
        long j3;
        long j4;
        Objects.requireNonNull(traceContextOrSamplingFlags, "extracted == null");
        TraceContext context = traceContextOrSamplingFlags.context();
        if (context != null) {
            return newChild(context);
        }
        TraceIdContext traceIdContext = traceContextOrSamplingFlags.traceIdContext();
        if (traceIdContext != null) {
            return _toSpan(nextContext(InternalPropagation.instance.flags(traceContextOrSamplingFlags.traceIdContext()), traceIdContext.traceIdHigh(), traceIdContext.traceId(), 0L, 0L, traceContextOrSamplingFlags.extra()));
        }
        SamplingFlags samplingFlags = traceContextOrSamplingFlags.samplingFlags();
        List<Object> extra = traceContextOrSamplingFlags.extra();
        TraceContext traceContext = this.currentTraceContext.get();
        if (traceContext != null) {
            int flags2 = InternalPropagation.instance.flags(traceContext);
            long traceIdHigh = traceContext.traceIdHigh();
            long traceId = traceContext.traceId();
            long localRootId = traceContext.localRootId();
            long spanId = traceContext.spanId();
            List<Object> concatImmutableLists = Lists.concatImmutableLists(extra, traceContext.extra());
            j3 = traceIdHigh;
            j4 = traceId;
            j = localRootId;
            j2 = spanId;
            list = concatImmutableLists;
            flags = flags2;
        } else {
            list = extra;
            j = 0;
            j2 = 0;
            flags = InternalPropagation.instance.flags(samplingFlags);
            j3 = 0;
            j4 = 0;
        }
        return _toSpan(nextContext(flags, j3, j4, j, j2, list));
    }

    public Span toSpan(TraceContext traceContext) {
        Objects.requireNonNull(traceContext, "context == null");
        if (this.alwaysSampleLocal) {
            int flags = InternalPropagation.instance.flags(traceContext);
            if ((flags & 32) != 32) {
                traceContext = InternalPropagation.instance.withFlags(traceContext, flags | 32);
            }
        }
        return _toSpan(this.propagationFactory.decorate(traceContext));
    }

    Span _toSpan(TraceContext traceContext) {
        if (isNoop(traceContext)) {
            return new NoopSpan(traceContext);
        }
        PendingSpan orCreate = this.pendingSpans.getOrCreate(traceContext, false);
        return new RealSpan(traceContext, this.pendingSpans, orCreate.state(), orCreate.clock(), this.finishedSpanHandler);
    }

    public SpanInScope withSpanInScope(@Nullable Span span) {
        return new SpanInScope(this.currentTraceContext.newScope(span != null ? span.context() : null));
    }

    public SpanCustomizer currentSpanCustomizer() {
        TraceContext traceContext = this.currentTraceContext.get();
        if (traceContext == null || isNoop(traceContext)) {
            return NoopSpanCustomizer.INSTANCE;
        }
        PendingSpan orCreate = this.pendingSpans.getOrCreate(traceContext, false);
        return new RealSpanCustomizer(traceContext, orCreate.state(), orCreate.clock());
    }

    @Nullable
    public Span currentSpan() {
        TraceContext traceContext = this.currentTraceContext.get();
        if (traceContext != null) {
            return toSpan(traceContext);
        }
        return null;
    }

    public Span nextSpan() {
        TraceContext traceContext = this.currentTraceContext.get();
        return traceContext != null ? newChild(traceContext) : newTrace();
    }

    public ScopedSpan startScopedSpan(String str) {
        return startScopedSpanWithParent(str, null);
    }

    public ScopedSpan startScopedSpanWithParent(String str, @Nullable TraceContext traceContext) {
        Objects.requireNonNull(str, "name == null");
        if (traceContext == null) {
            traceContext = this.currentTraceContext.get();
        }
        TraceContext nextContext = traceContext != null ? nextContext(traceContext) : newRootContext();
        CurrentTraceContext.Scope newScope = this.currentTraceContext.newScope(nextContext);
        if (isNoop(nextContext)) {
            return new NoopScopedSpan(nextContext, newScope);
        }
        PendingSpan orCreate = this.pendingSpans.getOrCreate(nextContext, true);
        Clock clock = orCreate.clock();
        MutableSpan state = orCreate.state();
        state.name(str);
        return new RealScopedSpan(nextContext, newScope, state, clock, this.pendingSpans, this.finishedSpanHandler);
    }

    /* loaded from: classes.dex */
    public static final class SpanInScope implements Closeable {
        final CurrentTraceContext.Scope scope;

        SpanInScope(CurrentTraceContext.Scope scope) {
            Objects.requireNonNull(scope, "scope == null");
            this.scope = scope;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.scope.close();
        }

        public String toString() {
            return this.scope.toString();
        }
    }

    public String toString() {
        TraceContext traceContext = this.currentTraceContext.get();
        return "Tracer{" + (traceContext != null ? "currentSpan=" + traceContext + ", " : "") + (this.noop.get() ? "noop=true, " : "") + "finishedSpanHandler=" + this.finishedSpanHandler + "}";
    }

    boolean isNoop(TraceContext traceContext) {
        if (this.finishedSpanHandler == FinishedSpanHandler.NOOP || this.noop.get()) {
            return true;
        }
        int flags = InternalPropagation.instance.flags(traceContext);
        return ((flags & 32) == 32 || (flags & 2) == 2) ? false : true;
    }

    long nextId() {
        long randomLong = Platform.get().randomLong();
        while (randomLong == 0) {
            randomLong = Platform.get().randomLong();
        }
        return randomLong;
    }
}
