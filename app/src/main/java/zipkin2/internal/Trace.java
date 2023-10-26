package zipkin2.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import zipkin2.Endpoint;
import zipkin2.Span;

/* loaded from: classes3.dex */
public class Trace {
    static final Comparator<Span> CLEANUP_COMPARATOR = new Comparator<Span>() { // from class: zipkin2.internal.Trace.1
        @Override // java.util.Comparator
        public int compare(Span span, Span span2) {
            if (span.equals(span2)) {
                return 0;
            }
            int compareTo = span.id().compareTo(span2.id());
            if (compareTo != 0) {
                return compareTo;
            }
            int compareShared = Trace.compareShared(span, span2);
            return compareShared != 0 ? compareShared : Trace.compareEndpoint(span.localEndpoint(), span2.localEndpoint());
        }
    };

    public static List<Span> merge(List<Span> list) {
        int i;
        int size = list.size();
        if (size <= 1) {
            return list;
        }
        ArrayList arrayList = new ArrayList(list);
        Collections.sort(arrayList, CLEANUP_COMPARATOR);
        int i2 = 0;
        String traceId = ((Span) arrayList.get(0)).traceId();
        for (int i3 = 1; i3 < size; i3++) {
            String traceId2 = ((Span) arrayList.get(i3)).traceId();
            if (traceId.length() != 32) {
                traceId = traceId2;
            }
        }
        Span span = null;
        while (i2 < size) {
            Span span2 = (Span) arrayList.get(i2);
            boolean equals = Boolean.TRUE.equals(span2.shared());
            Span.Builder traceId3 = span2.traceId().length() != traceId.length() ? span2.toBuilder().traceId(traceId) : null;
            EndpointTracker endpointTracker = null;
            while (true) {
                i = i2 + 1;
                if (i >= size) {
                    break;
                }
                Span span3 = (Span) arrayList.get(i);
                if (!span3.id().equals(span2.id())) {
                    break;
                }
                if (endpointTracker == null) {
                    endpointTracker = new EndpointTracker();
                    endpointTracker.tryMerge(span2.localEndpoint());
                }
                if (equals != Boolean.TRUE.equals(span3.shared()) || !endpointTracker.tryMerge(span3.localEndpoint())) {
                    break;
                }
                if (traceId3 == null) {
                    traceId3 = span2.toBuilder();
                }
                traceId3.merge(span3);
                size--;
                arrayList.remove(i);
            }
            if (span != null && span.id().equals(span2.id())) {
                if (span.kind() == Span.Kind.CLIENT && span2.kind() == Span.Kind.SERVER && !equals) {
                    if (traceId3 == null) {
                        traceId3 = span2.toBuilder();
                    }
                    traceId3.shared(true);
                    equals = true;
                }
                if (equals && span2.parentId() == null && span.parentId() != null) {
                    if (traceId3 == null) {
                        traceId3 = span2.toBuilder();
                    }
                    traceId3.parentId(span.parentId());
                }
            }
            if (traceId3 != null) {
                span = traceId3.build();
                arrayList.set(i2, span);
            } else {
                span = span2;
            }
            i2 = i;
        }
        return arrayList;
    }

    static int compareShared(Span span, Span span2) {
        boolean equals = Boolean.TRUE.equals(span.shared());
        boolean equals2 = Boolean.TRUE.equals(span2.shared());
        if (equals && equals2) {
            return 0;
        }
        if (equals) {
            return 1;
        }
        if (equals2) {
            return -1;
        }
        boolean equals3 = Span.Kind.CLIENT.equals(span.kind());
        boolean equals4 = Span.Kind.CLIENT.equals(span2.kind());
        if (equals3 && equals4) {
            return 0;
        }
        if (equals3) {
            return -1;
        }
        return equals4 ? 1 : 0;
    }

    static int compareEndpoint(Endpoint endpoint, Endpoint endpoint2) {
        if (endpoint == null) {
            return endpoint2 == null ? 0 : -1;
        } else if (endpoint2 == null) {
            return 1;
        } else {
            int nullSafeCompareTo = nullSafeCompareTo(endpoint.serviceName(), endpoint2.serviceName(), false);
            if (nullSafeCompareTo != 0) {
                return nullSafeCompareTo;
            }
            int nullSafeCompareTo2 = nullSafeCompareTo(endpoint.ipv4(), endpoint2.ipv4(), false);
            return nullSafeCompareTo2 != 0 ? nullSafeCompareTo2 : nullSafeCompareTo(endpoint.ipv6(), endpoint2.ipv6(), false);
        }
    }

    static <T extends Comparable<T>> int nullSafeCompareTo(T t, T t2, boolean z) {
        if (t == null) {
            if (t2 == null) {
                return 0;
            }
            return z ? -1 : 1;
        } else if (t2 == null) {
            return z ? 1 : -1;
        } else {
            return t.compareTo(t2);
        }
    }

    /* loaded from: classes3.dex */
    static final class EndpointTracker {
        String ipv4;
        String ipv6;
        int port;
        String serviceName;

        EndpointTracker() {
        }

        boolean tryMerge(Endpoint endpoint) {
            if (endpoint == null) {
                return true;
            }
            if (this.serviceName == null || endpoint.serviceName() == null || this.serviceName.equals(endpoint.serviceName())) {
                if (this.ipv4 == null || endpoint.ipv4() == null || this.ipv4.equals(endpoint.ipv4())) {
                    if (this.ipv6 == null || endpoint.ipv6() == null || this.ipv6.equals(endpoint.ipv6())) {
                        if (this.port == 0 || endpoint.portAsInt() == 0 || this.port == endpoint.portAsInt()) {
                            if (this.serviceName == null) {
                                this.serviceName = endpoint.serviceName();
                            }
                            if (this.ipv4 == null) {
                                this.ipv4 = endpoint.ipv4();
                            }
                            if (this.ipv6 == null) {
                                this.ipv6 = endpoint.ipv6();
                            }
                            if (this.port == 0) {
                                this.port = endpoint.portAsInt();
                            }
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
    }

    Trace() {
    }
}
