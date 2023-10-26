package brave.internal.recorder;

import brave.Clock;
import brave.handler.FinishedSpanHandler;
import brave.handler.MutableSpan;
import brave.internal.InternalPropagation;
import brave.internal.Nullable;
import brave.internal.Platform;
import brave.propagation.TraceContext;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class PendingSpans extends ReferenceQueue<TraceContext> {
    final Clock clock;
    final ConcurrentMap<Object, PendingSpan> delegate = new ConcurrentHashMap(64);
    final AtomicBoolean noop;
    final FinishedSpanHandler zipkinHandler;

    public PendingSpans(Clock clock, FinishedSpanHandler finishedSpanHandler, AtomicBoolean atomicBoolean) {
        this.clock = clock;
        this.zipkinHandler = finishedSpanHandler;
        this.noop = atomicBoolean;
    }

    public PendingSpan getOrCreate(TraceContext traceContext, boolean z) {
        Objects.requireNonNull(traceContext, "context == null");
        reportOrphanedSpans();
        PendingSpan pendingSpan = this.delegate.get(traceContext);
        if (pendingSpan != null) {
            return pendingSpan;
        }
        MutableSpan mutableSpan = new MutableSpan();
        if (traceContext.shared()) {
            mutableSpan.setShared();
        }
        TickClock clockFromParent = getClockFromParent(traceContext);
        if (clockFromParent == null) {
            clockFromParent = new TickClock(this.clock.currentTimeMicroseconds(), System.nanoTime());
            if (z) {
                mutableSpan.startTimestamp(clockFromParent.baseEpochMicros);
            }
        } else if (z) {
            mutableSpan.startTimestamp(clockFromParent.currentTimeMicroseconds());
        }
        PendingSpan pendingSpan2 = new PendingSpan(mutableSpan, clockFromParent);
        PendingSpan putIfAbsent = this.delegate.putIfAbsent(new RealKey(traceContext, this), pendingSpan2);
        return putIfAbsent != null ? putIfAbsent : pendingSpan2;
    }

    @Nullable
    TickClock getClockFromParent(TraceContext traceContext) {
        PendingSpan pendingSpan;
        long parentIdAsLong = traceContext.parentIdAsLong();
        if (traceContext.shared() || parentIdAsLong != 0) {
            if (parentIdAsLong == 0) {
                parentIdAsLong = traceContext.spanId();
            }
            pendingSpan = this.delegate.get(InternalPropagation.instance.newTraceContext(0, traceContext.traceIdHigh(), traceContext.traceId(), 0L, 0L, parentIdAsLong, Collections.emptyList()));
        } else {
            pendingSpan = null;
        }
        if (pendingSpan != null) {
            return pendingSpan.clock;
        }
        return null;
    }

    public boolean remove(TraceContext traceContext) {
        Objects.requireNonNull(traceContext, "context == null");
        PendingSpan remove = this.delegate.remove(traceContext);
        reportOrphanedSpans();
        return remove != null;
    }

    void reportOrphanedSpans() {
        boolean z = this.zipkinHandler == FinishedSpanHandler.NOOP || this.noop.get();
        long j = 0;
        long j2 = 0;
        while (true) {
            RealKey realKey = (RealKey) poll();
            if (realKey == null) {
                return;
            }
            PendingSpan remove = this.delegate.remove(realKey);
            if (!z && remove != null && realKey.sampled) {
                if (j2 == j) {
                    j2 = this.clock.currentTimeMicroseconds();
                }
                TraceContext newTraceContext = InternalPropagation.instance.newTraceContext(6, realKey.traceIdHigh, realKey.traceId, realKey.localRootId, 0L, realKey.spanId, Collections.emptyList());
                remove.state.annotate(j2, "brave.flush");
                try {
                    this.zipkinHandler.handle(newTraceContext, remove.state);
                } catch (RuntimeException e) {
                    Platform.get().log("error reporting {0}", newTraceContext, e);
                }
            }
            j = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class RealKey extends WeakReference<TraceContext> {
        final int hashCode;
        final long localRootId;
        final boolean sampled;
        final long spanId;
        final long traceId;
        final long traceIdHigh;

        RealKey(TraceContext traceContext, ReferenceQueue<TraceContext> referenceQueue) {
            super(traceContext, referenceQueue);
            this.hashCode = traceContext.hashCode();
            this.traceIdHigh = traceContext.traceIdHigh();
            this.traceId = traceContext.traceId();
            this.localRootId = traceContext.localRootId();
            this.spanId = traceContext.spanId();
            this.sampled = Boolean.TRUE.equals(traceContext.sampled());
        }

        public String toString() {
            TraceContext traceContext = (TraceContext) get();
            return traceContext != null ? "WeakReference(" + traceContext + ")" : "ClearedReference()";
        }

        public int hashCode() {
            return this.hashCode;
        }

        public boolean equals(Object obj) {
            TraceContext traceContext = (TraceContext) get();
            TraceContext traceContext2 = (TraceContext) ((RealKey) obj).get();
            if (traceContext == null) {
                return traceContext2 == null;
            }
            return traceContext.equals(traceContext2);
        }
    }

    /* loaded from: classes.dex */
    static final class LookupKey {
        int hashCode;
        boolean shared;
        long spanId;
        long traceId;
        long traceIdHigh;

        static int generateHashCode(long j, long j2, long j3, boolean z) {
            return ((((((((int) (j ^ (j >>> 32))) ^ 1000003) * 1000003) ^ ((int) (j2 ^ (j2 >>> 32)))) * 1000003) ^ ((int) ((j3 >>> 32) ^ j3))) * 1000003) ^ (z ? 16 : 0);
        }

        LookupKey() {
        }

        void set(TraceContext traceContext) {
            set(traceContext.traceIdHigh(), traceContext.traceId(), traceContext.spanId(), traceContext.shared());
        }

        void set(long j, long j2, long j3, boolean z) {
            this.traceIdHigh = j;
            this.traceId = j2;
            this.spanId = j3;
            this.shared = z;
            this.hashCode = generateHashCode(j, j2, j3, z);
        }

        public int hashCode() {
            return this.hashCode;
        }

        public boolean equals(Object obj) {
            TraceContext traceContext = (TraceContext) ((RealKey) obj).get();
            return traceContext != null && this.traceIdHigh == traceContext.traceIdHigh() && this.traceId == traceContext.traceId() && this.spanId == traceContext.spanId() && this.shared == traceContext.shared();
        }
    }

    public String toString() {
        return "PendingSpans" + this.delegate.keySet();
    }
}
