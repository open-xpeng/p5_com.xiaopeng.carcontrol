package brave.internal.handler;

import brave.handler.FinishedSpanHandler;
import brave.handler.MutableSpan;
import brave.internal.Platform;
import brave.propagation.TraceContext;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class FinishedSpanHandlers {
    public static FinishedSpanHandler compose(List<FinishedSpanHandler> list) {
        Objects.requireNonNull(list, "finishedSpanHandlers == null");
        int size = list.size();
        if (size == 0) {
            return FinishedSpanHandler.NOOP;
        }
        FinishedSpanHandler finishedSpanHandler = FinishedSpanHandler.NOOP;
        for (int i = 0; i < size; i++) {
            FinishedSpanHandler finishedSpanHandler2 = list.get(i);
            if (finishedSpanHandler2 != FinishedSpanHandler.NOOP) {
                finishedSpanHandler = finishedSpanHandler == FinishedSpanHandler.NOOP ? finishedSpanHandler2 : new CompositeFinishedSpanHandler(finishedSpanHandler, finishedSpanHandler2);
            }
        }
        return finishedSpanHandler;
    }

    public static FinishedSpanHandler noopAware(FinishedSpanHandler finishedSpanHandler, AtomicBoolean atomicBoolean) {
        return finishedSpanHandler == FinishedSpanHandler.NOOP ? finishedSpanHandler : new NoopAwareFinishedSpan(finishedSpanHandler, atomicBoolean);
    }

    /* loaded from: classes.dex */
    static final class NoopAwareFinishedSpan extends FinishedSpanHandler {
        final FinishedSpanHandler delegate;
        final AtomicBoolean noop;

        NoopAwareFinishedSpan(FinishedSpanHandler finishedSpanHandler, AtomicBoolean atomicBoolean) {
            Objects.requireNonNull(finishedSpanHandler, "delegate == null");
            this.delegate = finishedSpanHandler;
            this.noop = atomicBoolean;
        }

        @Override // brave.handler.FinishedSpanHandler
        public boolean handle(TraceContext traceContext, MutableSpan mutableSpan) {
            if (this.noop.get()) {
                return false;
            }
            try {
                return this.delegate.handle(traceContext, mutableSpan);
            } catch (RuntimeException e) {
                Platform.get().log("error accepting {0}", traceContext, e);
                return false;
            }
        }

        @Override // brave.handler.FinishedSpanHandler
        public boolean alwaysSampleLocal() {
            return this.delegate.alwaysSampleLocal();
        }

        public String toString() {
            return this.delegate.toString();
        }
    }

    /* loaded from: classes.dex */
    static final class CompositeFinishedSpanHandler extends FinishedSpanHandler {
        final boolean alwaysSampleLocal;
        final FinishedSpanHandler first;
        final FinishedSpanHandler second;

        CompositeFinishedSpanHandler(FinishedSpanHandler finishedSpanHandler, FinishedSpanHandler finishedSpanHandler2) {
            this.first = finishedSpanHandler;
            this.second = finishedSpanHandler2;
            this.alwaysSampleLocal = finishedSpanHandler.alwaysSampleLocal() || finishedSpanHandler2.alwaysSampleLocal();
        }

        @Override // brave.handler.FinishedSpanHandler
        public boolean handle(TraceContext traceContext, MutableSpan mutableSpan) {
            return this.first.handle(traceContext, mutableSpan) && this.second.handle(traceContext, mutableSpan);
        }

        @Override // brave.handler.FinishedSpanHandler
        public boolean alwaysSampleLocal() {
            return this.alwaysSampleLocal;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(",").append(this.second);
            CompositeFinishedSpanHandler compositeFinishedSpanHandler = this;
            while (true) {
                FinishedSpanHandler finishedSpanHandler = compositeFinishedSpanHandler.first;
                if (finishedSpanHandler instanceof CompositeFinishedSpanHandler) {
                    compositeFinishedSpanHandler = (CompositeFinishedSpanHandler) finishedSpanHandler;
                    sb.insert(0, ",").insert(1, compositeFinishedSpanHandler.second);
                } else {
                    sb.insert(0, finishedSpanHandler);
                    return sb.insert(0, "CompositeFinishedSpanHandler(").append(")").toString();
                }
            }
        }
    }
}
