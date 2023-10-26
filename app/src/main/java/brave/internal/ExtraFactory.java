package brave.internal;

import brave.propagation.TraceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public abstract class ExtraFactory<E> {
    protected abstract void consolidate(E e, E e2);

    protected abstract E create();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract E create(E e);

    protected abstract E createExtraAndClaim(long j, long j2);

    protected abstract E createExtraAndClaim(E e, long j, long j2);

    protected abstract boolean tryToClaim(E e, long j, long j2);

    public abstract Class<E> type();

    /* JADX WARN: Multi-variable type inference failed */
    public final TraceContext decorate(TraceContext traceContext) {
        boolean z;
        List<Object> ensureMutable;
        long traceId = traceContext.traceId();
        long spanId = traceContext.spanId();
        Class type = type();
        List<Object> extra = traceContext.extra();
        int size = extra.size();
        if (size == 0) {
            return contextWithExtra(traceContext, Collections.singletonList(createExtraAndClaim(traceId, spanId)));
        }
        Object obj = extra.get(0);
        Object obj2 = null;
        if (type.isInstance(obj)) {
            obj2 = tryToClaim(obj, traceId, spanId) ? obj : createExtraAndClaim(obj, traceId, spanId);
        }
        boolean z2 = true;
        if (size == 1) {
            if (obj2 != null) {
                return obj2 == obj ? traceContext : contextWithExtra(traceContext, Collections.singletonList(obj2));
            }
            ArrayList arrayList = new ArrayList(2);
            arrayList.add(obj);
            arrayList.add(createExtraAndClaim(traceId, spanId));
            return contextWithExtra(traceContext, Collections.unmodifiableList(arrayList));
        }
        int i = 1;
        while (i < size) {
            Object obj3 = extra.get(i);
            if (type.isInstance(obj3)) {
                if (obj2 == null) {
                    z = z2;
                    if (tryToClaim(obj3, traceId, spanId)) {
                        obj2 = obj3;
                    } else {
                        obj2 = createExtraAndClaim(obj3, traceId, spanId);
                        ensureMutable = Lists.ensureMutable(extra);
                        ensureMutable.set(i, obj2);
                    }
                } else {
                    z = z2;
                    consolidate(obj3, obj2);
                    ensureMutable = Lists.ensureMutable(extra);
                    ensureMutable.remove(i);
                    size--;
                    i--;
                }
                extra = ensureMutable;
            } else {
                z = z2;
            }
            i++;
            z2 = z;
        }
        if (obj2 == null) {
            Object createExtraAndClaim = createExtraAndClaim(traceId, spanId);
            extra = Lists.ensureMutable(extra);
            extra.add(createExtraAndClaim);
        }
        return extra == traceContext.extra() ? traceContext : contextWithExtra(traceContext, Collections.unmodifiableList(extra));
    }

    protected TraceContext contextWithExtra(TraceContext traceContext, List<Object> list) {
        return InternalPropagation.instance.withExtra(traceContext, list);
    }
}
