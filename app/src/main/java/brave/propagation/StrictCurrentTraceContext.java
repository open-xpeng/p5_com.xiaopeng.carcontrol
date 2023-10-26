package brave.propagation;

import brave.propagation.CurrentTraceContext;

@Deprecated
/* loaded from: classes.dex */
public final class StrictCurrentTraceContext extends ThreadLocalCurrentTraceContext {
    static final CurrentTraceContext.Builder SCOPE_DECORATING_BUILDER = ThreadLocalCurrentTraceContext.newBuilder().addScopeDecorator(new StrictScopeDecorator());

    public StrictCurrentTraceContext() {
        super(SCOPE_DECORATING_BUILDER, new ThreadLocal());
    }
}
