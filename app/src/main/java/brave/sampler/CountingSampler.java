package brave.sampler;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class CountingSampler extends Sampler {
    private final AtomicInteger counter;
    private final BitSet sampleDecisions;

    public String toString() {
        return "CountingSampler()";
    }

    public static Sampler create(float f) {
        if (f == 0.0f) {
            return NEVER_SAMPLE;
        }
        if (f == 1.0d) {
            return ALWAYS_SAMPLE;
        }
        if (f < 0.01f || f > 1.0f) {
            throw new IllegalArgumentException("rate should be between 0.01 and 1: was " + f);
        }
        return new CountingSampler(f);
    }

    CountingSampler(float f) {
        this(f, new Random());
    }

    CountingSampler(float f, Random random) {
        this.counter = new AtomicInteger();
        this.sampleDecisions = randomBitSet(100, (int) (f * 100.0f), random);
    }

    @Override // brave.sampler.Sampler
    public boolean isSampled(long j) {
        return this.sampleDecisions.get(mod(this.counter.getAndIncrement(), 100));
    }

    static int mod(int i, int i2) {
        int i3 = i % i2;
        return i3 >= 0 ? i3 : i3 + i2;
    }

    static BitSet randomBitSet(int i, int i2, Random random) {
        BitSet bitSet = new BitSet(i);
        int[] iArr = new int[i2];
        int i3 = 0;
        while (i3 < i2) {
            iArr[i3] = i3;
            bitSet.set(i3);
            i3++;
        }
        while (i3 < i) {
            int i4 = i3 + 1;
            int nextInt = random.nextInt(i4);
            if (nextInt < i2) {
                bitSet.clear(iArr[nextInt]);
                bitSet.set(i3);
                iArr[nextInt] = i3;
            }
            i3 = i4;
        }
        return bitSet;
    }
}
