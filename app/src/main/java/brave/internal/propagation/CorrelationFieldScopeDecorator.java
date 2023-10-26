package brave.internal.propagation;

import brave.internal.Nullable;
import brave.propagation.CurrentTraceContext;
import brave.propagation.TraceContext;

/* loaded from: classes.dex */
public abstract class CorrelationFieldScopeDecorator implements CurrentTraceContext.ScopeDecorator {
    @Nullable
    protected abstract String get(String str);

    protected abstract void put(String str, String str2);

    protected abstract void remove(String str);

    @Override // brave.propagation.CurrentTraceContext.ScopeDecorator
    public CurrentTraceContext.Scope decorateScope(@Nullable TraceContext traceContext, final CurrentTraceContext.Scope scope) {
        final String str = get("traceId");
        final String str2 = get("spanId");
        final String str3 = get("parentId");
        if (traceContext != null) {
            maybeReplaceTraceContext(traceContext, str, str3, str2);
        } else {
            remove("traceId");
            remove("parentId");
            remove("spanId");
        }
        return new CurrentTraceContext.Scope() { // from class: brave.internal.propagation.CorrelationFieldScopeDecorator.1CorrelationFieldCurrentTraceContextScope
            @Override // brave.propagation.CurrentTraceContext.Scope, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                scope.close();
                CorrelationFieldScopeDecorator.this.replace("traceId", str);
                CorrelationFieldScopeDecorator.this.replace("parentId", str3);
                CorrelationFieldScopeDecorator.this.replace("spanId", str2);
            }
        };
    }

    void maybeReplaceTraceContext(TraceContext traceContext, String str, @Nullable String str2, String str3) {
        if (!traceContext.traceIdString().equals(str)) {
            put("traceId", traceContext.traceIdString());
        }
        String parentIdString = traceContext.parentIdString();
        if (parentIdString == null) {
            remove("parentId");
        } else if (!parentIdString.equals(str2)) {
            put("parentId", parentIdString);
        }
        String spanIdString = traceContext.spanIdString();
        if (spanIdString.equals(str3)) {
            return;
        }
        put("spanId", spanIdString);
    }

    final void replace(String str, @Nullable String str2) {
        if (str2 != null) {
            put(str, str2);
        } else {
            remove(str);
        }
    }
}
