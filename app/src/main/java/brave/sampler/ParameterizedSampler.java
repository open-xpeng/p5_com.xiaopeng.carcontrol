package brave.sampler;

import brave.internal.Nullable;
import brave.propagation.SamplingFlags;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ParameterizedSampler<P> {
    final List<? extends Rule<P>> rules;

    public static <P> ParameterizedSampler<P> create(List<? extends Rule<P>> list) {
        Objects.requireNonNull(list, "rules == null");
        return new ParameterizedSampler<>(list);
    }

    /* loaded from: classes.dex */
    public static abstract class Rule<P> {
        final Sampler sampler;

        public abstract boolean matches(P p);

        protected Rule(float f) {
            this.sampler = CountingSampler.create(f);
        }

        SamplingFlags isSampled() {
            if (this.sampler.isSampled(0L)) {
                return SamplingFlags.SAMPLED;
            }
            return SamplingFlags.NOT_SAMPLED;
        }
    }

    ParameterizedSampler(List<? extends Rule<P>> list) {
        this.rules = list;
    }

    public SamplingFlags sample(@Nullable P p) {
        if (p == null) {
            return SamplingFlags.EMPTY;
        }
        for (Rule<P> rule : this.rules) {
            if (rule.matches(p)) {
                return rule.isSampled();
            }
        }
        return SamplingFlags.EMPTY;
    }
}
