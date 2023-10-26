package brave.sampler;

import java.util.Random;

/* loaded from: classes.dex */
public final class BoundarySampler extends Sampler {
    static final long SALT = new Random().nextLong();
    private final long boundary;

    public static Sampler create(float f) {
        if (f == 0.0f) {
            return Sampler.NEVER_SAMPLE;
        }
        if (f == 1.0d) {
            return ALWAYS_SAMPLE;
        }
        if (f < 1.0E-4f || f > 1.0f) {
            throw new IllegalArgumentException("rate should be between 0.0001 and 1: was " + f);
        }
        return new BoundarySampler(f * 10000.0f);
    }

    BoundarySampler(long j) {
        this.boundary = j;
    }

    @Override // brave.sampler.Sampler
    public boolean isSampled(long j) {
        return Math.abs(j ^ SALT) % 10000 <= this.boundary;
    }

    public String toString() {
        return "BoundaryTraceIdSampler(" + this.boundary + ")";
    }
}
