package brave.internal.recorder;

import brave.Clock;

/* loaded from: classes.dex */
final class TickClock implements Clock {
    final long baseEpochMicros;
    final long baseTickNanos;

    /* JADX INFO: Access modifiers changed from: package-private */
    public TickClock(long j, long j2) {
        this.baseEpochMicros = j;
        this.baseTickNanos = j2;
    }

    @Override // brave.Clock
    public long currentTimeMicroseconds() {
        return ((System.nanoTime() - this.baseTickNanos) / 1000) + this.baseEpochMicros;
    }

    public String toString() {
        return "TickClock{baseEpochMicros=" + this.baseEpochMicros + ", baseTickNanos=" + this.baseTickNanos + "}";
    }
}
