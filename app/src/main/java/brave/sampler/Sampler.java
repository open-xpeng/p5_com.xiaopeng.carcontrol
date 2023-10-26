package brave.sampler;

/* loaded from: classes.dex */
public abstract class Sampler {
    public static final Sampler ALWAYS_SAMPLE = new Sampler() { // from class: brave.sampler.Sampler.1
        @Override // brave.sampler.Sampler
        public boolean isSampled(long j) {
            return true;
        }

        public String toString() {
            return "AlwaysSample";
        }
    };
    public static final Sampler NEVER_SAMPLE = new Sampler() { // from class: brave.sampler.Sampler.2
        @Override // brave.sampler.Sampler
        public boolean isSampled(long j) {
            return false;
        }

        public String toString() {
            return "NeverSample";
        }
    };

    public abstract boolean isSampled(long j);

    public static Sampler create(float f) {
        return CountingSampler.create(f);
    }
}
