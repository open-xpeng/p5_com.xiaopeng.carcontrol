package brave.internal.handler;

import brave.ErrorParser;
import brave.handler.FinishedSpanHandler;
import brave.handler.MutableSpan;
import brave.propagation.TraceContext;
import zipkin2.Span;
import zipkin2.reporter.Reporter;

/* loaded from: classes.dex */
public final class ZipkinFinishedSpanHandler extends FinishedSpanHandler {
    final MutableSpanConverter converter;
    final Reporter<Span> spanReporter;

    public ZipkinFinishedSpanHandler(Reporter<Span> reporter, ErrorParser errorParser, String str, String str2, int i) {
        this.spanReporter = reporter;
        this.converter = new MutableSpanConverter(errorParser, str, str2, i);
    }

    @Override // brave.handler.FinishedSpanHandler
    public boolean handle(TraceContext traceContext, MutableSpan mutableSpan) {
        if (Boolean.TRUE.equals(traceContext.sampled())) {
            Span.Builder id = Span.newBuilder().traceId(traceContext.traceIdString()).parentId(traceContext.parentIdString()).id(traceContext.spanIdString());
            if (traceContext.debug()) {
                id.debug(true);
            }
            this.converter.convert(mutableSpan, id);
            this.spanReporter.report(id.build());
            return true;
        }
        return true;
    }

    public String toString() {
        return this.spanReporter.toString();
    }
}
