package zipkin2.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.android.service.MqttServiceConstants;
import zipkin2.DependencyLink;
import zipkin2.Span;
import zipkin2.internal.SpanNode;

/* loaded from: classes3.dex */
public final class DependencyLinker {
    final SpanNode.Builder builder;
    final Map<Pair, Long> callCounts;
    final Map<Pair, Long> errorCounts;
    final Logger logger;

    public DependencyLinker() {
        this(Logger.getLogger(DependencyLinker.class.getName()));
    }

    DependencyLinker(Logger logger) {
        this.callCounts = new LinkedHashMap();
        this.errorCounts = new LinkedHashMap();
        this.logger = logger;
        this.builder = new SpanNode.Builder(logger);
    }

    public DependencyLinker putTrace(List<Span> list) {
        String str;
        String localServiceName;
        if (list.isEmpty()) {
            return this;
        }
        SpanNode build = this.builder.build(list);
        if (this.logger.isLoggable(Level.FINE)) {
            this.logger.fine("traversing trace tree, breadth-first");
        }
        Iterator<SpanNode> traverse = build.traverse();
        while (traverse.hasNext()) {
            SpanNode next = traverse.next();
            Span span = next.span();
            if (this.logger.isLoggable(Level.FINE)) {
                this.logger.fine("processing " + span);
            }
            Span.Kind kind = span.kind();
            if (!Span.Kind.CLIENT.equals(kind) || next.children().isEmpty()) {
                String localServiceName2 = span.localServiceName();
                String remoteServiceName = span.remoteServiceName();
                if (kind == null) {
                    if (localServiceName2 != null && remoteServiceName != null) {
                        kind = Span.Kind.CLIENT;
                    } else {
                        this.logger.fine("non remote span; skipping");
                    }
                }
                int i = AnonymousClass1.$SwitchMap$zipkin2$Span$Kind[kind.ordinal()];
                if (i == 1 || i == 2) {
                    if (next == build && remoteServiceName == null) {
                        this.logger.fine("root's client is unknown; skipping");
                    } else {
                        str = remoteServiceName;
                        remoteServiceName = localServiceName2;
                        boolean containsKey = span.tags().containsKey(MqttServiceConstants.TRACE_ERROR);
                        if (kind == Span.Kind.PRODUCER && kind != Span.Kind.CONSUMER) {
                            Span firstRemoteAncestor = firstRemoteAncestor(next);
                            if (firstRemoteAncestor != null && (localServiceName = firstRemoteAncestor.localServiceName()) != null) {
                                if (kind == Span.Kind.CLIENT && localServiceName2 != null && !localServiceName.equals(localServiceName2)) {
                                    this.logger.fine("detected missing link to client span");
                                    addLink(localServiceName, localServiceName2, false);
                                }
                                if (kind == Span.Kind.SERVER || str == null) {
                                    str = localServiceName;
                                }
                                if (!containsKey && Span.Kind.CLIENT.equals(firstRemoteAncestor.kind()) && span.parentId() != null && span.parentId().equals(firstRemoteAncestor.id())) {
                                    containsKey = firstRemoteAncestor.tags().containsKey(MqttServiceConstants.TRACE_ERROR);
                                }
                            }
                            if (str == null || remoteServiceName == null) {
                                this.logger.fine("cannot find remote ancestor; skipping");
                            } else {
                                addLink(str, remoteServiceName, containsKey);
                            }
                        } else if (str != null || remoteServiceName == null) {
                            this.logger.fine("cannot link messaging span to its broker; skipping");
                        } else {
                            addLink(str, remoteServiceName, containsKey);
                        }
                    }
                } else if (i != 3 && i != 4) {
                    this.logger.fine("unknown kind; skipping");
                } else {
                    str = localServiceName2;
                    boolean containsKey2 = span.tags().containsKey(MqttServiceConstants.TRACE_ERROR);
                    if (kind == Span.Kind.PRODUCER) {
                    }
                    if (str != null) {
                    }
                    this.logger.fine("cannot link messaging span to its broker; skipping");
                }
            }
        }
        return this;
    }

    /* renamed from: zipkin2.internal.DependencyLinker$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$zipkin2$Span$Kind;

        static {
            int[] iArr = new int[Span.Kind.values().length];
            $SwitchMap$zipkin2$Span$Kind = iArr;
            try {
                iArr[Span.Kind.SERVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$zipkin2$Span$Kind[Span.Kind.CONSUMER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$zipkin2$Span$Kind[Span.Kind.CLIENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$zipkin2$Span$Kind[Span.Kind.PRODUCER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    Span firstRemoteAncestor(SpanNode spanNode) {
        for (SpanNode parent = spanNode.parent(); parent != null; parent = parent.parent()) {
            Span span = parent.span();
            if (span != null && span.kind() != null) {
                if (this.logger.isLoggable(Level.FINE)) {
                    this.logger.fine("found remote ancestor " + span);
                }
                return span;
            }
        }
        return null;
    }

    void addLink(String str, String str2, boolean z) {
        if (this.logger.isLoggable(Level.FINE)) {
            this.logger.fine("incrementing " + (z ? "error " : "") + "link " + str + " -> " + str2);
        }
        Pair pair = new Pair(str, str2);
        if (this.callCounts.containsKey(pair)) {
            Map<Pair, Long> map = this.callCounts;
            map.put(pair, Long.valueOf(map.get(pair).longValue() + 1));
        } else {
            this.callCounts.put(pair, 1L);
        }
        if (z) {
            if (this.errorCounts.containsKey(pair)) {
                Map<Pair, Long> map2 = this.errorCounts;
                map2.put(pair, Long.valueOf(map2.get(pair).longValue() + 1));
                return;
            }
            this.errorCounts.put(pair, 1L);
        }
    }

    public List<DependencyLink> link() {
        return link(this.callCounts, this.errorCounts);
    }

    public static List<DependencyLink> merge(Iterable<DependencyLink> iterable) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (DependencyLink dependencyLink : iterable) {
            Pair pair = new Pair(dependencyLink.parent(), dependencyLink.child());
            long j = 0;
            linkedHashMap.put(pair, Long.valueOf((linkedHashMap.containsKey(pair) ? ((Long) linkedHashMap.get(pair)).longValue() : 0L) + dependencyLink.callCount()));
            if (linkedHashMap2.containsKey(pair)) {
                j = ((Long) linkedHashMap2.get(pair)).longValue();
            }
            linkedHashMap2.put(pair, Long.valueOf(j + dependencyLink.errorCount()));
        }
        return link(linkedHashMap, linkedHashMap2);
    }

    static List<DependencyLink> link(Map<Pair, Long> map, Map<Pair, Long> map2) {
        ArrayList arrayList = new ArrayList(map.size());
        for (Map.Entry<Pair, Long> entry : map.entrySet()) {
            Pair key = entry.getKey();
            arrayList.add(DependencyLink.newBuilder().parent(key.left).child(key.right).callCount(entry.getValue().longValue()).errorCount(map2.containsKey(key) ? map2.get(key).longValue() : 0L).build());
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class Pair {
        final String left;
        final String right;

        Pair(String str, String str2) {
            this.left = str;
            this.right = str2;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Pair) {
                Pair pair = (Pair) obj;
                return this.left.equals(pair.left) && this.right.equals(pair.right);
            }
            return false;
        }

        public int hashCode() {
            return ((this.left.hashCode() ^ 1000003) * 1000003) ^ this.right.hashCode();
        }
    }
}
