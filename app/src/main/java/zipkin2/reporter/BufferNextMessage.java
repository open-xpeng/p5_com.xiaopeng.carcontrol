package zipkin2.reporter;

import java.util.ArrayList;
import java.util.Iterator;
import zipkin2.codec.Encoding;

/* loaded from: classes3.dex */
abstract class BufferNextMessage<S> implements SpanWithSizeConsumer<S> {
    boolean bufferFull;
    long deadlineNanoTime;
    final int maxBytes;
    int messageSizeInBytes;
    final long timeoutNanos;
    final ArrayList<S> spans = new ArrayList<>();
    final ArrayList<Integer> sizes = new ArrayList<>();

    abstract int messageSizeInBytes(int i);

    abstract void resetMessageSizeInBytes();

    /* renamed from: zipkin2.reporter.BufferNextMessage$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$zipkin2$codec$Encoding;

        static {
            int[] iArr = new int[Encoding.values().length];
            $SwitchMap$zipkin2$codec$Encoding = iArr;
            try {
                iArr[Encoding.JSON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$zipkin2$codec$Encoding[Encoding.THRIFT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$zipkin2$codec$Encoding[Encoding.PROTO3.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <S> BufferNextMessage<S> create(Encoding encoding, int i, long j) {
        int i2 = AnonymousClass1.$SwitchMap$zipkin2$codec$Encoding[encoding.ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 == 3) {
                    return new BufferNextProto3Message(i, j);
                }
                throw new UnsupportedOperationException("encoding: " + encoding);
            }
            return new BufferNextThriftMessage(i, j);
        }
        return new BufferNextJsonMessage(i, j);
    }

    BufferNextMessage(int i, long j) {
        this.maxBytes = i;
        this.timeoutNanos = j;
    }

    /* loaded from: classes3.dex */
    static final class BufferNextJsonMessage<S> extends BufferNextMessage<S> {
        boolean hasAtLeastOneSpan;

        BufferNextJsonMessage(int i, long j) {
            super(i, j);
            this.messageSizeInBytes = 2;
            this.hasAtLeastOneSpan = false;
        }

        @Override // zipkin2.reporter.BufferNextMessage
        int messageSizeInBytes(int i) {
            return this.messageSizeInBytes + i + (this.hasAtLeastOneSpan ? 1 : 0);
        }

        @Override // zipkin2.reporter.BufferNextMessage
        void resetMessageSizeInBytes() {
            int size = this.sizes.size();
            this.hasAtLeastOneSpan = size > 0;
            if (size < 2) {
                this.messageSizeInBytes = 2;
                if (this.hasAtLeastOneSpan) {
                    this.messageSizeInBytes += this.sizes.get(0).intValue();
                    return;
                }
                return;
            }
            this.messageSizeInBytes = (size + 2) - 1;
            for (int i = 0; i < size; i++) {
                this.messageSizeInBytes += this.sizes.get(i).intValue();
            }
        }

        @Override // zipkin2.reporter.BufferNextMessage
        void addSpanToBuffer(S s, int i) {
            super.addSpanToBuffer(s, i);
            this.hasAtLeastOneSpan = true;
        }
    }

    /* loaded from: classes3.dex */
    static final class BufferNextThriftMessage<S> extends BufferNextMessage<S> {
        BufferNextThriftMessage(int i, long j) {
            super(i, j);
            this.messageSizeInBytes = 5;
        }

        @Override // zipkin2.reporter.BufferNextMessage
        int messageSizeInBytes(int i) {
            return this.messageSizeInBytes + i;
        }

        @Override // zipkin2.reporter.BufferNextMessage
        void resetMessageSizeInBytes() {
            this.messageSizeInBytes = 5;
            int size = this.sizes.size();
            for (int i = 0; i < size; i++) {
                this.messageSizeInBytes += this.sizes.get(i).intValue();
            }
        }
    }

    /* loaded from: classes3.dex */
    static final class BufferNextProto3Message<S> extends BufferNextMessage<S> {
        BufferNextProto3Message(int i, long j) {
            super(i, j);
        }

        @Override // zipkin2.reporter.BufferNextMessage
        int messageSizeInBytes(int i) {
            int i2 = this.messageSizeInBytes + i;
            this.messageSizeInBytes = i2;
            return i2;
        }

        @Override // zipkin2.reporter.BufferNextMessage
        void resetMessageSizeInBytes() {
            this.messageSizeInBytes = 0;
            int size = this.sizes.size();
            for (int i = 0; i < size; i++) {
                this.messageSizeInBytes += this.sizes.get(i).intValue();
            }
        }
    }

    @Override // zipkin2.reporter.SpanWithSizeConsumer
    public boolean offer(S s, int i) {
        int messageSizeInBytes = messageSizeInBytes(i);
        int i2 = this.maxBytes;
        char c = messageSizeInBytes < i2 ? (char) 65535 : messageSizeInBytes == i2 ? (char) 0 : (char) 1;
        if (c > 0) {
            this.bufferFull = true;
            return false;
        }
        addSpanToBuffer(s, i);
        this.messageSizeInBytes = messageSizeInBytes;
        if (c == 0) {
            this.bufferFull = true;
        }
        return true;
    }

    void addSpanToBuffer(S s, int i) {
        this.spans.add(s);
        this.sizes.add(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long remainingNanos() {
        if (this.spans.isEmpty()) {
            this.deadlineNanoTime = System.nanoTime() + this.timeoutNanos;
        }
        return Math.max(this.deadlineNanoTime - System.nanoTime(), 0L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isReady() {
        return this.bufferFull || remainingNanos() <= 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void drain(SpanWithSizeConsumer<S> spanWithSizeConsumer) {
        Iterator<S> it = this.spans.iterator();
        Iterator<Integer> it2 = this.sizes.iterator();
        while (it.hasNext()) {
            if (spanWithSizeConsumer.offer(it.next(), it2.next().intValue())) {
                this.bufferFull = false;
                it.remove();
                it2.remove();
            }
        }
        resetMessageSizeInBytes();
        this.deadlineNanoTime = 0L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int count() {
        return this.spans.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int sizeInBytes() {
        return this.messageSizeInBytes;
    }
}
