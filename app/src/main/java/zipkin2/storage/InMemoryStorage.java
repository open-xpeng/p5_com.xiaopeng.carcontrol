package zipkin2.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import zipkin2.Call;
import zipkin2.DependencyLink;
import zipkin2.Span;
import zipkin2.internal.DependencyLinker;
import zipkin2.storage.StorageComponent;

/* loaded from: classes3.dex */
public final class InMemoryStorage extends StorageComponent implements SpanStore, SpanConsumer, AutocompleteTags {
    static final Comparator<String> STRING_COMPARATOR = new Comparator<String>() { // from class: zipkin2.storage.InMemoryStorage.5
        public String toString() {
            return "String::compareTo";
        }

        @Override // java.util.Comparator
        public int compare(String str, String str2) {
            if (str == null) {
                return -1;
            }
            return str.compareTo(str2);
        }
    };
    static final Comparator<TraceIdTimestamp> TIMESTAMP_DESCENDING = new Comparator<TraceIdTimestamp>() { // from class: zipkin2.storage.InMemoryStorage.6
        public String toString() {
            return "TimestampDescending{}";
        }

        @Override // java.util.Comparator
        public int compare(TraceIdTimestamp traceIdTimestamp, TraceIdTimestamp traceIdTimestamp2) {
            int i = (traceIdTimestamp.timestamp > traceIdTimestamp2.timestamp ? 1 : (traceIdTimestamp.timestamp == traceIdTimestamp2.timestamp ? 0 : -1));
            int i2 = i < 0 ? -1 : i == 0 ? 0 : 1;
            return i2 != 0 ? -i2 : traceIdTimestamp2.lowTraceId.compareTo(traceIdTimestamp.lowTraceId);
        }
    };
    volatile int acceptedSpanCount;
    final Set<String> autocompleteKeys;
    final Call<List<String>> autocompleteKeysCall;
    private final SortedMultimap<String, String> autocompleteTags;
    final int maxSpanCount;
    final boolean searchEnabled;
    private final SortedMultimap<String, String> serviceToSpanNames;
    private final ServiceNameToTraceIds serviceToTraceIds;
    private final SortedMultimap<TraceIdTimestamp, Span> spansByTraceIdTimeStamp = new SortedMultimap(TIMESTAMP_DESCENDING) { // from class: zipkin2.storage.InMemoryStorage.1
        @Override // zipkin2.storage.InMemoryStorage.SortedMultimap
        Collection<Span> valueContainer() {
            return new ArrayList();
        }
    };
    final boolean strictTraceId;
    private final SortedMultimap<String, TraceIdTimestamp> traceIdToTraceIdTimeStamps;

    @Override // zipkin2.storage.StorageComponent
    public InMemoryStorage autocompleteTags() {
        return this;
    }

    @Override // zipkin2.Component, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // zipkin2.storage.StorageComponent
    public SpanConsumer spanConsumer() {
        return this;
    }

    @Override // zipkin2.storage.StorageComponent
    public InMemoryStorage spanStore() {
        return this;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /* loaded from: classes3.dex */
    public static final class Builder extends StorageComponent.Builder {
        boolean strictTraceId = true;
        boolean searchEnabled = true;
        int maxSpanCount = 500000;
        List<String> autocompleteKeys = Collections.emptyList();

        @Override // zipkin2.storage.StorageComponent.Builder
        public /* bridge */ /* synthetic */ StorageComponent.Builder autocompleteKeys(List list) {
            return autocompleteKeys((List<String>) list);
        }

        @Override // zipkin2.storage.StorageComponent.Builder
        public Builder strictTraceId(boolean z) {
            this.strictTraceId = z;
            return this;
        }

        @Override // zipkin2.storage.StorageComponent.Builder
        public Builder searchEnabled(boolean z) {
            this.searchEnabled = z;
            return this;
        }

        @Override // zipkin2.storage.StorageComponent.Builder
        public Builder autocompleteKeys(List<String> list) {
            Objects.requireNonNull(list, "autocompleteKeys == null");
            this.autocompleteKeys = list;
            return this;
        }

        public Builder maxSpanCount(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("maxSpanCount <= 0");
            }
            this.maxSpanCount = i;
            return this;
        }

        @Override // zipkin2.storage.StorageComponent.Builder
        public InMemoryStorage build() {
            return new InMemoryStorage(this);
        }
    }

    InMemoryStorage(Builder builder) {
        Comparator<String> comparator = STRING_COMPARATOR;
        this.traceIdToTraceIdTimeStamps = new SortedMultimap<String, TraceIdTimestamp>(comparator) { // from class: zipkin2.storage.InMemoryStorage.2
            @Override // zipkin2.storage.InMemoryStorage.SortedMultimap
            Collection<TraceIdTimestamp> valueContainer() {
                return new LinkedHashSet();
            }
        };
        this.serviceToTraceIds = new ServiceNameToTraceIds();
        this.serviceToSpanNames = new SortedMultimap<String, String>(comparator) { // from class: zipkin2.storage.InMemoryStorage.3
            @Override // zipkin2.storage.InMemoryStorage.SortedMultimap
            Collection<String> valueContainer() {
                return new LinkedHashSet();
            }
        };
        this.autocompleteTags = new SortedMultimap<String, String>(comparator) { // from class: zipkin2.storage.InMemoryStorage.4
            @Override // zipkin2.storage.InMemoryStorage.SortedMultimap
            Collection<String> valueContainer() {
                return new LinkedHashSet();
            }
        };
        this.strictTraceId = builder.strictTraceId;
        this.searchEnabled = builder.searchEnabled;
        this.maxSpanCount = builder.maxSpanCount;
        this.autocompleteKeysCall = Call.create(builder.autocompleteKeys);
        this.autocompleteKeys = new LinkedHashSet(builder.autocompleteKeys);
    }

    public int acceptedSpanCount() {
        return this.acceptedSpanCount;
    }

    public synchronized void clear() {
        this.acceptedSpanCount = 0;
        this.traceIdToTraceIdTimeStamps.clear();
        this.spansByTraceIdTimeStamp.clear();
        this.serviceToTraceIds.clear();
        this.serviceToSpanNames.clear();
        this.autocompleteTags.clear();
    }

    @Override // zipkin2.storage.SpanConsumer
    public synchronized Call<Void> accept(List<Span> list) {
        evictToRecoverSpans((this.spansByTraceIdTimeStamp.size() + list.size()) - this.maxSpanCount);
        for (Span span : list) {
            long timestampAsLong = span.timestampAsLong();
            String lowTraceId = lowTraceId(span.traceId());
            TraceIdTimestamp traceIdTimestamp = new TraceIdTimestamp(lowTraceId, timestampAsLong);
            this.spansByTraceIdTimeStamp.put(traceIdTimestamp, span);
            this.traceIdToTraceIdTimeStamps.put(lowTraceId, traceIdTimestamp);
            this.acceptedSpanCount++;
            if (this.searchEnabled) {
                String name = span.name();
                if (span.localServiceName() != null) {
                    this.serviceToTraceIds.put(span.localServiceName(), lowTraceId);
                    if (name != null) {
                        this.serviceToSpanNames.put(span.localServiceName(), name);
                    }
                }
                if (span.remoteServiceName() != null) {
                    this.serviceToTraceIds.put(span.remoteServiceName(), lowTraceId);
                    if (name != null) {
                        this.serviceToSpanNames.put(span.remoteServiceName(), name);
                    }
                }
                for (Map.Entry<String, String> entry : span.tags().entrySet()) {
                    if (this.autocompleteKeys.contains(entry.getKey())) {
                        this.autocompleteTags.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        return Call.create(null);
    }

    int evictToRecoverSpans(int i) {
        int i2 = 0;
        while (i > 0) {
            int deleteOldestTrace = deleteOldestTrace();
            i -= deleteOldestTrace;
            i2 += deleteOldestTrace;
        }
        return i2;
    }

    private int deleteOldestTrace() {
        String str = this.spansByTraceIdTimeStamp.delegate.lastKey().lowTraceId;
        int i = 0;
        for (TraceIdTimestamp traceIdTimestamp : this.traceIdToTraceIdTimeStamps.remove(str)) {
            i += this.spansByTraceIdTimeStamp.remove(traceIdTimestamp).size();
        }
        if (this.searchEnabled) {
            for (String str2 : this.serviceToTraceIds.removeServiceIfTraceId(str)) {
                this.serviceToSpanNames.remove(str2);
            }
        }
        return i;
    }

    @Override // zipkin2.storage.SpanStore
    public synchronized Call<List<List<Span>>> getTraces(QueryRequest queryRequest) {
        return getTraces(queryRequest, this.strictTraceId);
    }

    synchronized Call<List<List<Span>>> getTraces(QueryRequest queryRequest, boolean z) {
        Set<String> traceIdsDescendingByTimestamp = traceIdsDescendingByTimestamp(queryRequest);
        if (traceIdsDescendingByTimestamp.isEmpty()) {
            return Call.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = traceIdsDescendingByTimestamp.iterator();
        while (it.hasNext() && arrayList.size() < queryRequest.limit()) {
            List<Span> spansByTraceId = spansByTraceId(it.next());
            if (queryRequest.test(spansByTraceId)) {
                if (!z) {
                    arrayList.add(spansByTraceId);
                } else {
                    for (List<Span> list : strictByTraceId(spansByTraceId)) {
                        if (queryRequest.test(list)) {
                            arrayList.add(list);
                        }
                    }
                }
            }
        }
        return Call.create(arrayList);
    }

    static Collection<List<Span>> strictByTraceId(List<Span> list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Span span : list) {
            String traceId = span.traceId();
            if (!linkedHashMap.containsKey(traceId)) {
                linkedHashMap.put(traceId, new ArrayList());
            }
            ((List) linkedHashMap.get(traceId)).add(span);
        }
        return linkedHashMap.values();
    }

    public synchronized List<List<Span>> getTraces() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (String str : this.traceIdToTraceIdTimeStamps.keySet()) {
            List<Span> spansByTraceId = spansByTraceId(str);
            if (this.strictTraceId) {
                arrayList.addAll(strictByTraceId(spansByTraceId));
            } else {
                arrayList.add(spansByTraceId);
            }
        }
        return arrayList;
    }

    public synchronized List<DependencyLink> getDependencies() {
        return LinkDependencies.INSTANCE.map(getTraces());
    }

    Set<String> traceIdsDescendingByTimestamp(QueryRequest queryRequest) {
        Collection<TraceIdTimestamp> keySet;
        if (this.searchEnabled) {
            if (queryRequest.serviceName() != null) {
                keySet = traceIdTimestampsByServiceName(queryRequest.serviceName());
            } else {
                keySet = this.spansByTraceIdTimeStamp.keySet();
            }
            long endTs = queryRequest.endTs() * 1000;
            long lookback = endTs - (queryRequest.lookback() * 1000);
            if (keySet == null || keySet.isEmpty()) {
                return Collections.emptySet();
            }
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (TraceIdTimestamp traceIdTimestamp : keySet) {
                if (traceIdTimestamp.timestamp >= lookback || traceIdTimestamp.timestamp <= endTs) {
                    linkedHashSet.add(traceIdTimestamp.lowTraceId);
                }
            }
            return linkedHashSet;
        }
        return Collections.emptySet();
    }

    @Override // zipkin2.storage.SpanStore
    public synchronized Call<List<Span>> getTrace(String str) {
        String normalizeTraceId = Span.normalizeTraceId(str);
        List<Span> spansByTraceId = spansByTraceId(lowTraceId(normalizeTraceId));
        if (spansByTraceId != null && !spansByTraceId.isEmpty()) {
            if (!this.strictTraceId) {
                return Call.create(spansByTraceId);
            }
            ArrayList arrayList = new ArrayList(spansByTraceId);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (!((Span) it.next()).traceId().equals(normalizeTraceId)) {
                    it.remove();
                }
            }
            return Call.create(arrayList);
        }
        return Call.emptyList();
    }

    @Override // zipkin2.storage.SpanStore
    public synchronized Call<List<String>> getServiceNames() {
        if (this.searchEnabled) {
            return Call.create(new ArrayList(this.serviceToTraceIds.keySet()));
        }
        return Call.emptyList();
    }

    @Override // zipkin2.storage.SpanStore
    public synchronized Call<List<String>> getSpanNames(String str) {
        if (!str.isEmpty() && this.searchEnabled) {
            return Call.create(new ArrayList(this.serviceToSpanNames.get(str.toLowerCase(Locale.ROOT))));
        }
        return Call.emptyList();
    }

    @Override // zipkin2.storage.SpanStore
    public synchronized Call<List<DependencyLink>> getDependencies(long j, long j2) {
        return getTraces(QueryRequest.newBuilder().endTs(j).lookback(j2).limit(Integer.MAX_VALUE).build(), false).map(LinkDependencies.INSTANCE);
    }

    @Override // zipkin2.storage.AutocompleteTags
    public Call<List<String>> getKeys() {
        return !this.searchEnabled ? Call.emptyList() : this.autocompleteKeysCall.clone();
    }

    @Override // zipkin2.storage.AutocompleteTags
    public Call<List<String>> getValues(String str) {
        Objects.requireNonNull(str, "key == null");
        if (str.isEmpty()) {
            throw new IllegalArgumentException("key was empty");
        }
        return !this.searchEnabled ? Call.emptyList() : Call.create(new ArrayList(this.autocompleteTags.get(str)));
    }

    /* loaded from: classes3.dex */
    enum LinkDependencies implements Call.Mapper<List<List<Span>>, List<DependencyLink>> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "LinkDependencies";
        }

        @Override // zipkin2.Call.Mapper
        public List<DependencyLink> map(List<List<Span>> list) {
            DependencyLinker dependencyLinker = new DependencyLinker();
            for (List<Span> list2 : list) {
                dependencyLinker.putTrace(list2);
            }
            return dependencyLinker.link();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class ServiceNameToTraceIds extends SortedMultimap<String, String> {
        ServiceNameToTraceIds() {
            super(InMemoryStorage.STRING_COMPARATOR);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.storage.InMemoryStorage.SortedMultimap
        public Collection<String> valueContainer() {
            return new LinkedHashSet();
        }

        Set<String> removeServiceIfTraceId(String str) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (Map.Entry entry : this.delegate.entrySet()) {
                Collection collection = (Collection) entry.getValue();
                if (collection.remove(str) && collection.isEmpty()) {
                    linkedHashSet.add((String) entry.getKey());
                }
            }
            this.delegate.keySet().removeAll(linkedHashSet);
            return linkedHashSet;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static abstract class SortedMultimap<K, V> {
        final SortedMap<K, Collection<V>> delegate;
        int size = 0;

        abstract Collection<V> valueContainer();

        SortedMultimap(Comparator<K> comparator) {
            this.delegate = new TreeMap(comparator);
        }

        Set<K> keySet() {
            return this.delegate.keySet();
        }

        int size() {
            return this.size;
        }

        void put(K k, V v) {
            Collection<V> collection = this.delegate.get(k);
            if (collection == null) {
                SortedMap<K, Collection<V>> sortedMap = this.delegate;
                Collection<V> valueContainer = valueContainer();
                sortedMap.put(k, valueContainer);
                collection = valueContainer;
            }
            if (collection.add(v)) {
                this.size++;
            }
        }

        Collection<V> remove(K k) {
            Collection<V> remove = this.delegate.remove(k);
            if (remove != null) {
                this.size -= remove.size();
            }
            return remove;
        }

        void clear() {
            this.delegate.clear();
            this.size = 0;
        }

        Collection<V> get(K k) {
            Collection<V> collection = this.delegate.get(k);
            return collection != null ? collection : Collections.emptySet();
        }
    }

    List<Span> spansByTraceId(String str) {
        ArrayList arrayList = new ArrayList();
        for (TraceIdTimestamp traceIdTimestamp : this.traceIdToTraceIdTimeStamps.get(str)) {
            arrayList.addAll(this.spansByTraceIdTimeStamp.get(traceIdTimestamp));
        }
        return arrayList;
    }

    Collection<TraceIdTimestamp> traceIdTimestampsByServiceName(String str) {
        ArrayList arrayList = new ArrayList();
        for (String str2 : this.serviceToTraceIds.get(str)) {
            arrayList.addAll(this.traceIdToTraceIdTimeStamps.get(str2));
        }
        Collections.sort(arrayList, TIMESTAMP_DESCENDING);
        return arrayList;
    }

    static String lowTraceId(String str) {
        return str.length() == 32 ? str.substring(16) : str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class TraceIdTimestamp {
        final String lowTraceId;
        final long timestamp;

        TraceIdTimestamp(String str, long j) {
            this.lowTraceId = str;
            this.timestamp = j;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof TraceIdTimestamp) {
                TraceIdTimestamp traceIdTimestamp = (TraceIdTimestamp) obj;
                return this.lowTraceId.equals(traceIdTimestamp.lowTraceId) && this.timestamp == traceIdTimestamp.timestamp;
            }
            return false;
        }

        public int hashCode() {
            long j = this.timestamp;
            return ((this.lowTraceId.hashCode() ^ 1000003) * 1000003) ^ ((int) (j ^ (j >>> 32)));
        }
    }
}
