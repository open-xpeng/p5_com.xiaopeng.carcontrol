package brave.propagation;

import brave.internal.Nullable;
import brave.propagation.TraceContext;
import java.util.List;

/* loaded from: classes.dex */
public interface Propagation<K> {
    public static final Propagation<String> B3_STRING = B3Propagation.FACTORY.create(KeyFactory.STRING);
    public static final Propagation<String> B3_SINGLE_STRING = B3SinglePropagation.FACTORY.create(KeyFactory.STRING);

    /* loaded from: classes.dex */
    public static abstract class Factory {
        public abstract <K> Propagation<K> create(KeyFactory<K> keyFactory);

        public TraceContext decorate(TraceContext traceContext) {
            return traceContext;
        }

        public boolean requires128BitTraceId() {
            return false;
        }

        public boolean supportsJoin() {
            return false;
        }
    }

    /* loaded from: classes.dex */
    public interface Getter<C, K> {
        @Nullable
        String get(C c, K k);
    }

    /* loaded from: classes.dex */
    public interface KeyFactory<K> {
        public static final KeyFactory<String> STRING = new KeyFactory<String>() { // from class: brave.propagation.Propagation.KeyFactory.1
            @Override // brave.propagation.Propagation.KeyFactory
            public String create(String str) {
                return str;
            }

            public String toString() {
                return "StringKeyFactory{}";
            }
        };

        K create(String str);
    }

    /* loaded from: classes.dex */
    public interface Setter<C, K> {
        void put(C c, K k, String str);
    }

    <C> TraceContext.Extractor<C> extractor(Getter<C, K> getter);

    <C> TraceContext.Injector<C> injector(Setter<C, K> setter);

    List<K> keys();
}
