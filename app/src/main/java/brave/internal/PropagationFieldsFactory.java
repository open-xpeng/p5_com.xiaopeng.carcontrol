package brave.internal;

import brave.internal.PropagationFields;
import brave.propagation.TraceContext;
import java.util.List;

/* loaded from: classes.dex */
public abstract class PropagationFieldsFactory<P extends PropagationFields> extends ExtraFactory<P> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // brave.internal.ExtraFactory
    public abstract P create();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // brave.internal.ExtraFactory
    protected /* bridge */ /* synthetic */ Object createExtraAndClaim(Object obj, long j, long j2) {
        return createExtraAndClaim((PropagationFieldsFactory<P>) ((PropagationFields) obj), j, j2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // brave.internal.ExtraFactory
    protected /* bridge */ /* synthetic */ boolean tryToClaim(Object obj, long j, long j2) {
        return tryToClaim((PropagationFieldsFactory<P>) ((PropagationFields) obj), j, j2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // brave.internal.ExtraFactory
    public P createExtraAndClaim(long j, long j2) {
        P create = create();
        create.tryToClaim(j, j2);
        return create;
    }

    protected P createExtraAndClaim(P p, long j, long j2) {
        P p2 = (P) create(p);
        p2.tryToClaim(j, j2);
        return p2;
    }

    protected boolean tryToClaim(P p, long j, long j2) {
        return p.tryToClaim(j, j2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // brave.internal.ExtraFactory
    public void consolidate(P p, P p2) {
        p2.putAllIfAbsent(p);
    }

    @Override // brave.internal.ExtraFactory
    protected TraceContext contextWithExtra(TraceContext traceContext, List<Object> list) {
        return InternalPropagation.instance.withExtra(traceContext, list);
    }
}
