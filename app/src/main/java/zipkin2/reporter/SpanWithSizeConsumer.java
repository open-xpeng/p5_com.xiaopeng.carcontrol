package zipkin2.reporter;

/* compiled from: ByteBoundedQueue.java */
/* loaded from: classes3.dex */
interface SpanWithSizeConsumer<S> {
    boolean offer(S s, int i);
}
