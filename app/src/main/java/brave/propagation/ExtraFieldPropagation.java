package brave.propagation;

import brave.Tracing;
import brave.internal.Nullable;
import brave.internal.PredefinedPropagationFields;
import brave.internal.PropagationFields;
import brave.internal.PropagationFieldsFactory;
import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class ExtraFieldPropagation<K> implements Propagation<K> {
    final Propagation<K> delegate;
    final Factory factory;
    final List<K> keys;

    public static Factory newFactory(Propagation.Factory factory, String... strArr) {
        Objects.requireNonNull(factory, "delegate == null");
        Objects.requireNonNull(strArr, "fieldNames == null");
        String[] ensureLowerCase = ensureLowerCase(new LinkedHashSet(Arrays.asList(strArr)));
        return new Factory(factory, ensureLowerCase, ensureLowerCase);
    }

    public static Factory newFactory(Propagation.Factory factory, Collection<String> collection) {
        Objects.requireNonNull(factory, "delegate == null");
        Objects.requireNonNull(collection, "fieldNames == null");
        String[] ensureLowerCase = ensureLowerCase(new LinkedHashSet(collection));
        return new Factory(factory, ensureLowerCase, ensureLowerCase);
    }

    public static FactoryBuilder newFactoryBuilder(Propagation.Factory factory) {
        return new FactoryBuilder(factory);
    }

    /* loaded from: classes.dex */
    public static final class FactoryBuilder {
        final Propagation.Factory delegate;
        final Set<String> fieldNames = new LinkedHashSet();
        final Map<String, String[]> prefixedNames = new LinkedHashMap();

        FactoryBuilder(Propagation.Factory factory) {
            Objects.requireNonNull(factory, "delegate == null");
            this.delegate = factory;
        }

        public FactoryBuilder addField(String str) {
            Objects.requireNonNull(str, "fieldName == null");
            String trim = str.trim();
            if (trim.isEmpty()) {
                throw new IllegalArgumentException("fieldName is empty");
            }
            this.fieldNames.add(trim.toLowerCase(Locale.ROOT));
            return this;
        }

        public FactoryBuilder addPrefixedFields(String str, Collection<String> collection) {
            Objects.requireNonNull(str, "prefix == null");
            if (str.isEmpty()) {
                throw new IllegalArgumentException("prefix is empty");
            }
            Objects.requireNonNull(collection, "fieldNames == null");
            this.prefixedNames.put(str, ExtraFieldPropagation.ensureLowerCase(new LinkedHashSet(collection)));
            return this;
        }

        public Factory build() {
            String[] value;
            if (this.prefixedNames.isEmpty()) {
                String[] ensureLowerCase = ExtraFieldPropagation.ensureLowerCase(this.fieldNames);
                return new Factory(this.delegate, ensureLowerCase, ensureLowerCase);
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            if (!this.fieldNames.isEmpty()) {
                List asList = Arrays.asList(ExtraFieldPropagation.ensureLowerCase(this.fieldNames));
                int size = asList.size();
                for (int i = 0; i < size; i++) {
                    String str = (String) asList.get(i);
                    arrayList.add(str);
                    arrayList2.add(str);
                    arrayList3.add(Integer.valueOf(i));
                }
            }
            for (Map.Entry<String, String[]> entry : this.prefixedNames.entrySet()) {
                String key = entry.getKey();
                for (String str2 : entry.getValue()) {
                    int indexOf = arrayList.indexOf(str2);
                    if (indexOf == -1) {
                        indexOf = arrayList.size();
                        arrayList.add(str2);
                    }
                    arrayList2.add(key + str2);
                    arrayList3.add(Integer.valueOf(indexOf));
                }
            }
            int size2 = arrayList2.size();
            int[] iArr = new int[size2];
            for (int i2 = 0; i2 < size2; i2++) {
                iArr[i2] = ((Integer) arrayList3.get(i2)).intValue();
            }
            return new Factory(this.delegate, (String[]) arrayList.toArray(new String[0]), (String[]) arrayList2.toArray(new String[0]), iArr);
        }
    }

    @Nullable
    public static String current(String str) {
        return get(str);
    }

    @Nullable
    public static String get(String str) {
        TraceContext currentTraceContext = currentTraceContext();
        if (currentTraceContext != null) {
            return get(currentTraceContext, str);
        }
        return null;
    }

    public static void set(String str, String str2) {
        TraceContext currentTraceContext = currentTraceContext();
        if (currentTraceContext != null) {
            set(currentTraceContext, str, str2);
        }
    }

    public static Map<String, String> getAll() {
        TraceContext currentTraceContext = currentTraceContext();
        if (currentTraceContext == null) {
            return Collections.emptyMap();
        }
        return getAll(currentTraceContext);
    }

    public static Map<String, String> getAll(TraceContextOrSamplingFlags traceContextOrSamplingFlags) {
        Objects.requireNonNull(traceContextOrSamplingFlags, "extracted == null");
        TraceContext context = traceContextOrSamplingFlags.context();
        if (context != null) {
            return getAll(context);
        }
        PropagationFields propagationFields = (PropagationFields) TraceContext.findExtra(Extra.class, traceContextOrSamplingFlags.extra());
        return propagationFields != null ? propagationFields.toMap() : Collections.emptyMap();
    }

    public static Map<String, String> getAll(TraceContext traceContext) {
        Objects.requireNonNull(traceContext, "context == null");
        PropagationFields propagationFields = (PropagationFields) traceContext.findExtra(Extra.class);
        return propagationFields != null ? propagationFields.toMap() : Collections.emptyMap();
    }

    @Nullable
    static TraceContext currentTraceContext() {
        Tracing current = Tracing.current();
        if (current != null) {
            return current.currentTraceContext().get();
        }
        return null;
    }

    @Nullable
    public static String get(TraceContext traceContext, String str) {
        return PropagationFields.get(traceContext, lowercase(str), Extra.class);
    }

    public static void set(TraceContext traceContext, String str, String str2) {
        PropagationFields.put(traceContext, lowercase(str), str2, Extra.class);
    }

    /* loaded from: classes.dex */
    public static final class Factory extends Propagation.Factory {
        final Propagation.Factory delegate;
        final ExtraFactory extraFactory;
        final String[] fieldNames;
        final String[] keyNames;
        final int[] keyToField;

        Factory(Propagation.Factory factory, String[] strArr, String[] strArr2) {
            this(factory, strArr, strArr2, keyToField(strArr2));
        }

        static int[] keyToField(String[] strArr) {
            int length = strArr.length;
            int[] iArr = new int[length];
            for (int i = 0; i < length; i++) {
                iArr[i] = i;
            }
            return iArr;
        }

        Factory(Propagation.Factory factory, String[] strArr, String[] strArr2, int[] iArr) {
            this.delegate = factory;
            this.keyToField = iArr;
            this.fieldNames = strArr;
            this.keyNames = strArr2;
            this.extraFactory = new ExtraFactory(strArr);
        }

        @Override // brave.propagation.Propagation.Factory
        public boolean supportsJoin() {
            return this.delegate.supportsJoin();
        }

        @Override // brave.propagation.Propagation.Factory
        public boolean requires128BitTraceId() {
            return this.delegate.requires128BitTraceId();
        }

        @Override // brave.propagation.Propagation.Factory
        public final <K> ExtraFieldPropagation<K> create(Propagation.KeyFactory<K> keyFactory) {
            int length = this.keyNames.length;
            ArrayList arrayList = new ArrayList(length);
            for (int i = 0; i < length; i++) {
                arrayList.add(keyFactory.create(this.keyNames[i]));
            }
            return new ExtraFieldPropagation<>(this, keyFactory, arrayList);
        }

        @Override // brave.propagation.Propagation.Factory
        public TraceContext decorate(TraceContext traceContext) {
            return this.extraFactory.decorate(this.delegate.decorate(traceContext));
        }
    }

    ExtraFieldPropagation(Factory factory, Propagation.KeyFactory<K> keyFactory, List<K> list) {
        this.factory = factory;
        this.delegate = factory.delegate.create(keyFactory);
        this.keys = list;
    }

    public List<K> extraKeys() {
        return this.keys;
    }

    @Override // brave.propagation.Propagation
    public List<K> keys() {
        return this.delegate.keys();
    }

    @Override // brave.propagation.Propagation
    public <C> TraceContext.Injector<C> injector(Propagation.Setter<C, K> setter) {
        return new ExtraFieldInjector(this, setter);
    }

    @Override // brave.propagation.Propagation
    public <C> TraceContext.Extractor<C> extractor(Propagation.Getter<C, K> getter) {
        return new ExtraFieldExtractor(this, getter);
    }

    /* loaded from: classes.dex */
    static final class ExtraFieldInjector<C, K> implements TraceContext.Injector<C> {
        final TraceContext.Injector<C> delegate;
        final ExtraFieldPropagation<K> propagation;
        final Propagation.Setter<C, K> setter;

        ExtraFieldInjector(ExtraFieldPropagation<K> extraFieldPropagation, Propagation.Setter<C, K> setter) {
            this.propagation = extraFieldPropagation;
            this.delegate = extraFieldPropagation.delegate.injector(setter);
            this.setter = setter;
        }

        @Override // brave.propagation.TraceContext.Injector
        public void inject(TraceContext traceContext, C c) {
            this.delegate.inject(traceContext, c);
            Extra extra = (Extra) traceContext.findExtra(Extra.class);
            if (extra == null) {
                return;
            }
            inject(extra, (Extra) c);
        }

        void inject(Extra extra, C c) {
            int size = this.propagation.keys.size();
            for (int i = 0; i < size; i++) {
                String str = extra.get(this.propagation.factory.keyToField[i]);
                if (str != null) {
                    this.setter.put(c, this.propagation.keys.get(i), str);
                }
            }
        }
    }

    /* loaded from: classes.dex */
    static final class ExtraFieldExtractor<C, K> implements TraceContext.Extractor<C> {
        final TraceContext.Extractor<C> delegate;
        final Propagation.Getter<C, K> getter;
        final ExtraFieldPropagation<K> propagation;

        ExtraFieldExtractor(ExtraFieldPropagation<K> extraFieldPropagation, Propagation.Getter<C, K> getter) {
            this.propagation = extraFieldPropagation;
            this.delegate = extraFieldPropagation.delegate.extractor(getter);
            this.getter = getter;
        }

        @Override // brave.propagation.TraceContext.Extractor
        public TraceContextOrSamplingFlags extract(C c) {
            TraceContextOrSamplingFlags extract = this.delegate.extract(c);
            Extra create = this.propagation.factory.extraFactory.create();
            int size = this.propagation.keys.size();
            for (int i = 0; i < size; i++) {
                String str = this.getter.get(c, this.propagation.keys.get(i));
                if (str != null) {
                    create.put(this.propagation.factory.keyToField[i], str);
                }
            }
            return extract.toBuilder().addExtra(create).build();
        }
    }

    static String[] ensureLowerCase(Collection<String> collection) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("names is empty");
        }
        String[] strArr = new String[collection.size()];
        int i = 0;
        for (String str : collection) {
            if (str == null) {
                throw new NullPointerException("names[" + i + "] == null");
            }
            String trim = str.trim();
            if (trim.isEmpty()) {
                throw new IllegalArgumentException("names[" + i + "] is empty");
            }
            strArr[i] = trim.toLowerCase(Locale.ROOT);
            i++;
        }
        return strArr;
    }

    /* loaded from: classes.dex */
    static final class ExtraFactory extends PropagationFieldsFactory<Extra> {
        final String[] fieldNames;

        ExtraFactory(String[] strArr) {
            this.fieldNames = strArr;
        }

        @Override // brave.internal.ExtraFactory
        public Class<Extra> type() {
            return Extra.class;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // brave.internal.PropagationFieldsFactory, brave.internal.ExtraFactory
        public Extra create() {
            return new Extra(this.fieldNames);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // brave.internal.ExtraFactory
        public Extra create(Extra extra) {
            return new Extra(extra, this.fieldNames);
        }

        @Override // brave.internal.PropagationFieldsFactory, brave.internal.ExtraFactory
        protected TraceContext contextWithExtra(TraceContext traceContext, List<Object> list) {
            return traceContext.withExtra(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class Extra extends PredefinedPropagationFields {
        Extra(String... strArr) {
            super(strArr);
        }

        Extra(Extra extra, String... strArr) {
            super(extra, strArr);
        }
    }

    static String lowercase(String str) {
        Objects.requireNonNull(str, "name == null");
        return str.toLowerCase(Locale.ROOT);
    }
}
