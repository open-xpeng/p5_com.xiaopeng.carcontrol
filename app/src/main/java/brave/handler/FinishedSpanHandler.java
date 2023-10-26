package brave.handler;

import brave.propagation.TraceContext;

/* loaded from: classes.dex */
public abstract class FinishedSpanHandler {
    public static final FinishedSpanHandler NOOP = new FinishedSpanHandler() { // from class: brave.handler.FinishedSpanHandler.1
        @Override // brave.handler.FinishedSpanHandler
        public boolean handle(TraceContext traceContext, MutableSpan mutableSpan) {
            return true;
        }

        public String toString() {
            return "NoopFinishedSpanHandler{}";
        }
    };

    public boolean alwaysSampleLocal() {
        return false;
    }

    public abstract boolean handle(TraceContext traceContext, MutableSpan mutableSpan);
}
