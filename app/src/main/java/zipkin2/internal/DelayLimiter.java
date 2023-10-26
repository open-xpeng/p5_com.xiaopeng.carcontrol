package zipkin2.internal;

import com.xiaopeng.xvs.xid.base.AbsException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class DelayLimiter<C> {
    final long cardinality;
    final Ticker ticker;
    final long ttlNanos;
    final ConcurrentHashMap<C, Suppression<C>> cache = new ConcurrentHashMap<>();
    final DelayQueue<Suppression<C>> suppressions = new DelayQueue<>();

    public static <C> DelayLimiter<C> create() {
        return new Builder().build();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        Ticker ticker = new Ticker();
        long ttlNanos = TimeUnit.HOURS.toNanos(1);
        int cardinality = AbsException.ERROR_CODE_BASE_PAY;

        public Builder ttl(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("ttl <= 0");
            }
            this.ttlNanos = TimeUnit.MILLISECONDS.toNanos(i);
            return this;
        }

        public Builder cardinality(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("cardinality <= 0");
            }
            this.cardinality = i;
            return this;
        }

        Builder ticker(Ticker ticker) {
            this.ticker = ticker;
            return this;
        }

        public <C> DelayLimiter<C> build() {
            return new DelayLimiter<>(this);
        }

        Builder() {
        }
    }

    /* loaded from: classes3.dex */
    static class Ticker {
        Ticker() {
        }

        long read() {
            return System.nanoTime();
        }
    }

    DelayLimiter(Builder builder) {
        this.ticker = builder.ticker;
        this.ttlNanos = builder.ttlNanos;
        this.cardinality = builder.cardinality;
    }

    public boolean shouldInvoke(C c) {
        cleanupExpiredSuppressions();
        if (this.cache.containsKey(c)) {
            return false;
        }
        Ticker ticker = this.ticker;
        Suppression<C> suppression = new Suppression<>(ticker, c, ticker.read() + this.ttlNanos);
        if (this.cache.putIfAbsent(c, suppression) != null) {
            return false;
        }
        this.suppressions.offer((DelayQueue<Suppression<C>>) suppression);
        if (this.suppressions.size() > this.cardinality) {
            removeOneSuppression();
            return true;
        }
        return true;
    }

    void removeOneSuppression() {
        Suppression<C> peek;
        do {
            peek = this.suppressions.peek();
            if (peek == null) {
                return;
            }
        } while (!this.suppressions.remove(peek));
        this.cache.remove(peek.context, peek);
    }

    public void invalidate(C c) {
        Suppression<C> remove = this.cache.remove(c);
        if (remove != null) {
            this.suppressions.remove(remove);
        }
    }

    public void clear() {
        this.cache.clear();
        this.suppressions.clear();
    }

    void cleanupExpiredSuppressions() {
        while (true) {
            Suppression<C> poll = this.suppressions.poll();
            if (poll == null) {
                return;
            }
            this.cache.remove(poll.context, poll);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class Suppression<C> implements Delayed {
        final C context;
        final long expiration;
        final Ticker ticker;

        Suppression(Ticker ticker, C c, long j) {
            this.ticker = ticker;
            this.context = c;
            this.expiration = j;
        }

        @Override // java.util.concurrent.Delayed
        public long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(this.expiration - this.ticker.read(), TimeUnit.NANOSECONDS);
        }

        @Override // java.lang.Comparable
        public int compareTo(Delayed delayed) {
            return Long.signum(this.expiration - ((Suppression) delayed).expiration);
        }
    }
}
