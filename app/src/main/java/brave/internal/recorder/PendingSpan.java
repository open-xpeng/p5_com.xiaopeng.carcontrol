package brave.internal.recorder;

import brave.Clock;
import brave.handler.MutableSpan;

/* loaded from: classes.dex */
public final class PendingSpan {
    final TickClock clock;
    final MutableSpan state;

    /* JADX INFO: Access modifiers changed from: package-private */
    public PendingSpan(MutableSpan mutableSpan, TickClock tickClock) {
        this.state = mutableSpan;
        this.clock = tickClock;
    }

    public MutableSpan state() {
        return this.state;
    }

    public Clock clock() {
        return this.clock;
    }
}
