package zipkin2.reporter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes3.dex */
public final class InMemoryReporterMetrics implements ReporterMetrics {
    private final ConcurrentHashMap<MetricKey, AtomicLong> metrics = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Class<? extends Throwable>, AtomicLong> messagesDropped = new ConcurrentHashMap<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public enum MetricKey {
        messages,
        messageBytes,
        spans,
        spanBytes,
        spansDropped,
        spansPending,
        spanBytesPending
    }

    @Override // zipkin2.reporter.ReporterMetrics
    public void incrementMessages() {
        increment(MetricKey.messages, 1);
    }

    public long messages() {
        return get(MetricKey.messages);
    }

    @Override // zipkin2.reporter.ReporterMetrics
    public void incrementMessagesDropped(Throwable th) {
        increment(this.messagesDropped, th.getClass(), 1);
    }

    public Map<Class<? extends Throwable>, Long> messagesDroppedByCause() {
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.messagesDropped.size());
        for (Map.Entry<Class<? extends Throwable>, AtomicLong> entry : this.messagesDropped.entrySet()) {
            linkedHashMap.put(entry.getKey(), Long.valueOf(entry.getValue().longValue()));
        }
        return linkedHashMap;
    }

    public long messagesDropped() {
        long j = 0;
        for (AtomicLong atomicLong : this.messagesDropped.values()) {
            j += atomicLong.longValue();
        }
        return j;
    }

    @Override // zipkin2.reporter.ReporterMetrics
    public void incrementMessageBytes(int i) {
        increment(MetricKey.messageBytes, i);
    }

    public long messageBytes() {
        return get(MetricKey.messageBytes);
    }

    @Override // zipkin2.reporter.ReporterMetrics
    public void incrementSpans(int i) {
        increment(MetricKey.spans, i);
    }

    public long spans() {
        return get(MetricKey.spans);
    }

    @Override // zipkin2.reporter.ReporterMetrics
    public void incrementSpanBytes(int i) {
        increment(MetricKey.spanBytes, i);
    }

    public long spanBytes() {
        return get(MetricKey.spanBytes);
    }

    @Override // zipkin2.reporter.ReporterMetrics
    public void incrementSpansDropped(int i) {
        increment(MetricKey.spansDropped, i);
    }

    public long spansDropped() {
        return get(MetricKey.spansDropped);
    }

    @Override // zipkin2.reporter.ReporterMetrics
    public void updateQueuedSpans(int i) {
        update(MetricKey.spansPending, i);
    }

    public long queuedSpans() {
        return get(MetricKey.spansPending);
    }

    @Override // zipkin2.reporter.ReporterMetrics
    public void updateQueuedBytes(int i) {
        update(MetricKey.spanBytesPending, i);
    }

    public long queuedBytes() {
        return get(MetricKey.spanBytesPending);
    }

    public void clear() {
        this.metrics.clear();
    }

    private long get(MetricKey metricKey) {
        AtomicLong atomicLong = this.metrics.get(metricKey);
        if (atomicLong == null) {
            return 0L;
        }
        return atomicLong.get();
    }

    private void increment(MetricKey metricKey, int i) {
        increment(this.metrics, metricKey, i);
    }

    static <K> void increment(ConcurrentHashMap<K, AtomicLong> concurrentHashMap, K k, int i) {
        long j;
        if (i == 0) {
            return;
        }
        AtomicLong atomicLong = concurrentHashMap.get(k);
        if (atomicLong == null && (atomicLong = concurrentHashMap.putIfAbsent(k, new AtomicLong(i))) == null) {
            return;
        }
        do {
            j = atomicLong.get();
        } while (!atomicLong.compareAndSet(j, i + j));
    }

    private void update(MetricKey metricKey, int i) {
        AtomicLong atomicLong = this.metrics.get(metricKey);
        if (atomicLong == null && (atomicLong = this.metrics.putIfAbsent(metricKey, new AtomicLong(i))) == null) {
            return;
        }
        atomicLong.set(i);
    }
}
