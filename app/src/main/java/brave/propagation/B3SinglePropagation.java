package brave.propagation;

import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class B3SinglePropagation<K> implements Propagation<K> {
    public static final Propagation.Factory FACTORY = new Propagation.Factory() { // from class: brave.propagation.B3SinglePropagation.1
        @Override // brave.propagation.Propagation.Factory
        public boolean supportsJoin() {
            return true;
        }

        public String toString() {
            return "B3SinglePropagationFactory";
        }

        @Override // brave.propagation.Propagation.Factory
        public <K> Propagation<K> create(Propagation.KeyFactory<K> keyFactory) {
            return new B3SinglePropagation(keyFactory);
        }
    };
    final K b3Key;
    final List<K> fields;

    B3SinglePropagation(Propagation.KeyFactory<K> keyFactory) {
        K create = keyFactory.create("b3");
        this.b3Key = create;
        this.fields = Collections.unmodifiableList(Arrays.asList(create));
    }

    @Override // brave.propagation.Propagation
    public List<K> keys() {
        return this.fields;
    }

    @Override // brave.propagation.Propagation
    public <C> TraceContext.Injector<C> injector(Propagation.Setter<C, K> setter) {
        Objects.requireNonNull(setter, "setter == null");
        return new B3SingleInjector(this, setter);
    }

    /* loaded from: classes.dex */
    static final class B3SingleInjector<C, K> implements TraceContext.Injector<C> {
        final B3SinglePropagation<K> propagation;
        final Propagation.Setter<C, K> setter;

        B3SingleInjector(B3SinglePropagation<K> b3SinglePropagation, Propagation.Setter<C, K> setter) {
            this.propagation = b3SinglePropagation;
            this.setter = setter;
        }

        @Override // brave.propagation.TraceContext.Injector
        public void inject(TraceContext traceContext, C c) {
            this.setter.put(c, this.propagation.b3Key, B3SingleFormat.writeB3SingleFormat(traceContext));
        }
    }

    @Override // brave.propagation.Propagation
    public <C> TraceContext.Extractor<C> extractor(Propagation.Getter<C, K> getter) {
        Objects.requireNonNull(getter, "getter == null");
        return new B3SingleExtractor(this.b3Key, getter);
    }

    /* loaded from: classes.dex */
    static final class B3SingleExtractor<C, K> implements TraceContext.Extractor<C> {
        final K b3Key;
        final Propagation.Getter<C, K> getter;

        /* JADX INFO: Access modifiers changed from: package-private */
        public B3SingleExtractor(K k, Propagation.Getter<C, K> getter) {
            this.b3Key = k;
            this.getter = getter;
        }

        @Override // brave.propagation.TraceContext.Extractor
        public TraceContextOrSamplingFlags extract(C c) {
            Objects.requireNonNull(c, "carrier == null");
            String str = this.getter.get(c, this.b3Key);
            if (str == null) {
                return TraceContextOrSamplingFlags.EMPTY;
            }
            TraceContextOrSamplingFlags parseB3SingleFormat = B3SingleFormat.parseB3SingleFormat(str);
            return parseB3SingleFormat == null ? TraceContextOrSamplingFlags.EMPTY : parseB3SingleFormat;
        }
    }
}
