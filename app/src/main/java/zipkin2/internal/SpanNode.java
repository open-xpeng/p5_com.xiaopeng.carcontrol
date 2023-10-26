package zipkin2.internal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import zipkin2.Endpoint;
import zipkin2.Span;

/* loaded from: classes3.dex */
public final class SpanNode {
    List<SpanNode> children = Collections.emptyList();
    @Nullable
    SpanNode parent;
    @Nullable
    Span span;

    SpanNode(@Nullable Span span) {
        this.span = span;
    }

    @Nullable
    public SpanNode parent() {
        return this.parent;
    }

    @Nullable
    public Span span() {
        return this.span;
    }

    public List<SpanNode> children() {
        return this.children;
    }

    public Iterator<SpanNode> traverse() {
        return new BreadthFirstIterator(this);
    }

    /* loaded from: classes3.dex */
    static final class BreadthFirstIterator implements Iterator<SpanNode> {
        final ArrayDeque<SpanNode> queue;

        BreadthFirstIterator(SpanNode spanNode) {
            ArrayDeque<SpanNode> arrayDeque = new ArrayDeque<>();
            this.queue = arrayDeque;
            if (spanNode.span == null) {
                int size = spanNode.children.size();
                for (int i = 0; i < size; i++) {
                    this.queue.add(spanNode.children.get(i));
                }
                return;
            }
            arrayDeque.add(spanNode);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        @Override // java.util.Iterator
        public SpanNode next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            SpanNode remove = this.queue.remove();
            int size = remove.children.size();
            for (int i = 0; i < size; i++) {
                this.queue.add(remove.children.get(i));
            }
            return remove;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    SpanNode addChild(SpanNode spanNode) {
        Objects.requireNonNull(spanNode, "child == null");
        if (spanNode == this) {
            throw new IllegalArgumentException("circular dependency on " + this);
        }
        if (this.children.equals(Collections.emptyList())) {
            this.children = new ArrayList();
        }
        this.children.add(spanNode);
        spanNode.parent = this;
        return this;
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        final Logger logger;
        SpanNode rootSpan = null;
        Map<Object, SpanNode> keyToNode = new LinkedHashMap();
        Map<Object, Object> spanToParent = new LinkedHashMap();

        /* JADX INFO: Access modifiers changed from: package-private */
        public Builder(Logger logger) {
            this.logger = logger;
        }

        void clear() {
            this.rootSpan = null;
            this.keyToNode.clear();
            this.spanToParent.clear();
        }

        public SpanNode build(List<Span> list) {
            if (list.isEmpty()) {
                throw new IllegalArgumentException("spans were empty");
            }
            clear();
            List<Span> merge = Trace.merge(list);
            int size = merge.size();
            String traceId = merge.get(0).traceId();
            if (this.logger.isLoggable(Level.FINE)) {
                this.logger.fine("building trace tree: traceId=" + traceId);
            }
            for (int i = 0; i < size; i++) {
                index(merge.get(i));
            }
            for (int i2 = 0; i2 < size; i2++) {
                process(merge.get(i2));
            }
            if (this.rootSpan == null) {
                if (this.logger.isLoggable(Level.FINE)) {
                    this.logger.fine("substituting dummy node for missing root span: traceId=" + traceId);
                }
                this.rootSpan = new SpanNode(null);
            }
            for (Map.Entry<Object, Object> entry : this.spanToParent.entrySet()) {
                SpanNode spanNode = this.keyToNode.get(entry.getKey());
                SpanNode spanNode2 = this.keyToNode.get(entry.getValue());
                if (spanNode2 == null) {
                    this.rootSpan.addChild(spanNode);
                } else {
                    spanNode2.addChild(spanNode);
                }
            }
            return this.rootSpan;
        }

        void index(Span span) {
            Object id;
            String parentId;
            if (Boolean.TRUE.equals(span.shared())) {
                id = SpanNode.createKey(span.id(), true, span.localEndpoint());
                parentId = span.id();
            } else {
                id = span.id();
                parentId = span.parentId();
            }
            this.spanToParent.put(id, parentId);
        }

        void process(Span span) {
            Endpoint localEndpoint = span.localEndpoint();
            boolean equals = Boolean.TRUE.equals(span.shared());
            Object createKey = SpanNode.createKey(span.id(), equals, span.localEndpoint());
            Object obj = null;
            Object createKey2 = localEndpoint != null ? SpanNode.createKey(span.id(), equals, null) : createKey;
            if (equals) {
                obj = span.id();
            } else if (span.parentId() != null) {
                obj = SpanNode.createKey(span.parentId(), true, localEndpoint);
                if (this.spanToParent.containsKey(obj)) {
                    this.spanToParent.put(createKey2, obj);
                } else {
                    obj = span.parentId();
                }
            } else if (this.rootSpan != null && this.logger.isLoggable(Level.FINE)) {
                this.logger.fine(String.format("attributing span missing parent to root: traceId=%s, rootSpanId=%s, spanId=%s", span.traceId(), this.rootSpan.span().id(), span.id()));
            }
            SpanNode spanNode = new SpanNode(span);
            if (obj == null && this.rootSpan == null) {
                this.rootSpan = spanNode;
                this.spanToParent.remove(createKey2);
            } else if (equals) {
                this.keyToNode.put(createKey, spanNode);
                this.keyToNode.put(createKey2, spanNode);
            } else {
                this.keyToNode.put(createKey2, spanNode);
            }
        }
    }

    static Object createKey(String str, boolean z, @Nullable Endpoint endpoint) {
        return !z ? str : new SharedKey(str, endpoint);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class SharedKey {
        @Nullable
        final Endpoint endpoint;
        final String id;

        SharedKey(String str, @Nullable Endpoint endpoint) {
            Objects.requireNonNull(str, "id == null");
            this.id = str;
            this.endpoint = endpoint;
        }

        public String toString() {
            return "SharedKey{id=" + this.id + ", endpoint=" + this.endpoint + "}";
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof SharedKey) {
                SharedKey sharedKey = (SharedKey) obj;
                return this.id.equals(sharedKey.id) && equal(this.endpoint, sharedKey.endpoint);
            }
            return false;
        }

        static boolean equal(Object obj, Object obj2) {
            return obj == obj2 || (obj != null && obj.equals(obj2));
        }

        public int hashCode() {
            int hashCode = (this.id.hashCode() ^ 1000003) * 1000003;
            Endpoint endpoint = this.endpoint;
            return hashCode ^ (endpoint == null ? 0 : endpoint.hashCode());
        }
    }

    public String toString() {
        ArrayList arrayList = new ArrayList();
        int size = this.children.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(this.children.get(i).span);
        }
        StringBuilder append = new StringBuilder().append("SpanNode{parent=");
        SpanNode spanNode = this.parent;
        return append.append(spanNode != null ? spanNode.span : null).append(", span=").append(this.span).append(", children=").append(arrayList).append("}").toString();
    }
}
