package brave.internal;

import brave.propagation.TraceContext;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class PropagationFields {
    long spanId;
    long traceId;

    public abstract String get(String str);

    public abstract void put(String str, String str2);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void putAllIfAbsent(PropagationFields propagationFields);

    public abstract Map<String, String> toMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean tryToClaim(long j, long j2) {
        synchronized (this) {
            long j3 = this.traceId;
            boolean z = true;
            if (j3 == 0) {
                this.traceId = j;
                this.spanId = j2;
                return true;
            }
            if (j3 != j || this.spanId != j2) {
                z = false;
            }
            return z;
        }
    }

    public String toString() {
        return getClass().getSimpleName() + toMap();
    }

    public static String get(TraceContext traceContext, String str, Class<? extends PropagationFields> cls) {
        Objects.requireNonNull(traceContext, "context == null");
        Objects.requireNonNull(str, "name == null");
        PropagationFields propagationFields = (PropagationFields) traceContext.findExtra(cls);
        if (propagationFields != null) {
            return propagationFields.get(str);
        }
        return null;
    }

    public static void put(TraceContext traceContext, String str, String str2, Class<? extends PropagationFields> cls) {
        Objects.requireNonNull(traceContext, "context == null");
        Objects.requireNonNull(str, "name == null");
        Objects.requireNonNull(str2, "value == null");
        PropagationFields propagationFields = (PropagationFields) traceContext.findExtra(cls);
        if (propagationFields == null) {
            return;
        }
        propagationFields.put(str, str2);
    }
}
