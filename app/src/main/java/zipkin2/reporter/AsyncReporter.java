package zipkin2.reporter;

import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import zipkin2.Call;
import zipkin2.CheckResult;
import zipkin2.Component;
import zipkin2.Span;
import zipkin2.codec.BytesEncoder;
import zipkin2.codec.Encoding;
import zipkin2.codec.SpanBytesEncoder;

/* loaded from: classes3.dex */
public abstract class AsyncReporter<S> extends Component implements Reporter<S>, Flushable {
    @Override // zipkin2.Component, java.io.Closeable, java.lang.AutoCloseable
    public abstract void close();

    @Override // java.io.Flushable
    public abstract void flush();

    public static AsyncReporter<Span> create(Sender sender) {
        return new Builder(sender).build();
    }

    public static Builder builder(Sender sender) {
        return new Builder(sender);
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        int messageMaxBytes;
        final Sender sender;
        ReporterMetrics metrics = ReporterMetrics.NOOP_METRICS;
        long messageTimeoutNanos = TimeUnit.SECONDS.toNanos(1);
        long closeTimeoutNanos = TimeUnit.SECONDS.toNanos(1);
        int queuedMaxSpans = 10000;
        int queuedMaxBytes = onePercentOfMemory();

        static int onePercentOfMemory() {
            return (int) Math.max(Math.min(2147483647L, (long) (Runtime.getRuntime().totalMemory() * 0.01d)), -2147483648L);
        }

        Builder(Sender sender) {
            Objects.requireNonNull(sender, "sender == null");
            this.sender = sender;
            this.messageMaxBytes = sender.messageMaxBytes();
        }

        public Builder metrics(ReporterMetrics reporterMetrics) {
            Objects.requireNonNull(reporterMetrics, "metrics == null");
            this.metrics = reporterMetrics;
            return this;
        }

        public Builder messageMaxBytes(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("messageMaxBytes < 0: " + i);
            }
            this.messageMaxBytes = Math.min(i, this.sender.messageMaxBytes());
            return this;
        }

        public Builder messageTimeout(long j, TimeUnit timeUnit) {
            if (j < 0) {
                throw new IllegalArgumentException("messageTimeout < 0: " + j);
            }
            Objects.requireNonNull(timeUnit, "unit == null");
            this.messageTimeoutNanos = timeUnit.toNanos(j);
            return this;
        }

        public Builder closeTimeout(long j, TimeUnit timeUnit) {
            if (j < 0) {
                throw new IllegalArgumentException("closeTimeout < 0: " + j);
            }
            Objects.requireNonNull(timeUnit, "unit == null");
            this.closeTimeoutNanos = timeUnit.toNanos(j);
            return this;
        }

        public Builder queuedMaxSpans(int i) {
            this.queuedMaxSpans = i;
            return this;
        }

        public Builder queuedMaxBytes(int i) {
            this.queuedMaxBytes = i;
            return this;
        }

        public AsyncReporter<Span> build() {
            int i = AnonymousClass1.$SwitchMap$zipkin2$codec$Encoding[this.sender.encoding().ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        return build(SpanBytesEncoder.THRIFT);
                    }
                    throw new UnsupportedOperationException(this.sender.encoding().name());
                }
                return build(SpanBytesEncoder.PROTO3);
            }
            return build(SpanBytesEncoder.JSON_V2);
        }

        public <S> AsyncReporter<S> build(BytesEncoder<S> bytesEncoder) {
            Objects.requireNonNull(bytesEncoder, "encoder == null");
            if (bytesEncoder.encoding() != this.sender.encoding()) {
                throw new IllegalArgumentException(String.format("Encoder doesn't match Sender: %s %s", bytesEncoder.encoding(), this.sender.encoding()));
            }
            final BoundedAsyncReporter boundedAsyncReporter = new BoundedAsyncReporter(this, bytesEncoder);
            if (this.messageTimeoutNanos > 0) {
                final BufferNextMessage create = BufferNextMessage.create(bytesEncoder.encoding(), this.messageMaxBytes, this.messageTimeoutNanos);
                Thread thread = new Thread("AsyncReporter{" + this.sender + "}") { // from class: zipkin2.reporter.AsyncReporter.Builder.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        while (!boundedAsyncReporter.closed.get()) {
                            try {
                                try {
                                    boundedAsyncReporter.flush(create);
                                } catch (Error | RuntimeException e) {
                                    BoundedAsyncReporter.logger.log(Level.WARNING, "Unexpected error flushing spans", e);
                                    throw e;
                                }
                            } finally {
                                int count = create.count();
                                if (count > 0) {
                                    Builder.this.metrics.incrementSpansDropped(count);
                                    BoundedAsyncReporter.logger.warning("Dropped " + count + " spans due to AsyncReporter.close()");
                                }
                                boundedAsyncReporter.close.countDown();
                            }
                        }
                    }
                };
                thread.setDaemon(true);
                thread.start();
            }
            return boundedAsyncReporter;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: zipkin2.reporter.AsyncReporter$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$zipkin2$codec$Encoding;

        static {
            int[] iArr = new int[Encoding.values().length];
            $SwitchMap$zipkin2$codec$Encoding = iArr;
            try {
                iArr[Encoding.JSON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$zipkin2$codec$Encoding[Encoding.PROTO3.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$zipkin2$codec$Encoding[Encoding.THRIFT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class BoundedAsyncReporter<S> extends AsyncReporter<S> {
        static final Logger logger = Logger.getLogger(BoundedAsyncReporter.class.getName());
        final CountDownLatch close;
        final long closeTimeoutNanos;
        final AtomicBoolean closed = new AtomicBoolean(false);
        final BytesEncoder<S> encoder;
        final int messageMaxBytes;
        final long messageTimeoutNanos;
        final ReporterMetrics metrics;
        final ByteBoundedQueue<S> pending;
        final Sender sender;

        BoundedAsyncReporter(Builder builder, BytesEncoder<S> bytesEncoder) {
            this.pending = new ByteBoundedQueue<>(builder.queuedMaxSpans, builder.queuedMaxBytes);
            this.sender = builder.sender;
            this.messageMaxBytes = builder.messageMaxBytes;
            this.messageTimeoutNanos = builder.messageTimeoutNanos;
            this.closeTimeoutNanos = builder.closeTimeoutNanos;
            this.close = new CountDownLatch(builder.messageTimeoutNanos > 0 ? 1 : 0);
            this.metrics = builder.metrics;
            this.encoder = bytesEncoder;
        }

        @Override // zipkin2.reporter.Reporter
        public void report(S s) {
            Objects.requireNonNull(s, "span == null");
            this.metrics.incrementSpans(1);
            int sizeInBytes = this.encoder.sizeInBytes(s);
            int messageSizeInBytes = this.sender.messageSizeInBytes(sizeInBytes);
            this.metrics.incrementSpanBytes(sizeInBytes);
            if (this.closed.get() || messageSizeInBytes > this.messageMaxBytes || !this.pending.offer(s, sizeInBytes)) {
                this.metrics.incrementSpansDropped(1);
            }
        }

        @Override // zipkin2.reporter.AsyncReporter, java.io.Flushable
        public final void flush() {
            flush(BufferNextMessage.create(this.encoder.encoding(), this.messageMaxBytes, 0L));
        }

        void flush(BufferNextMessage<S> bufferNextMessage) {
            if (this.closed.get()) {
                throw new IllegalStateException("closed");
            }
            this.pending.drainTo(bufferNextMessage, bufferNextMessage.remainingNanos());
            this.metrics.updateQueuedSpans(this.pending.count);
            this.metrics.updateQueuedBytes(this.pending.sizeInBytes);
            if (bufferNextMessage.isReady() || this.closed.get()) {
                this.metrics.incrementMessages();
                this.metrics.incrementMessageBytes(bufferNextMessage.sizeInBytes());
                final ArrayList arrayList = new ArrayList(bufferNextMessage.count());
                bufferNextMessage.drain(new SpanWithSizeConsumer<S>() { // from class: zipkin2.reporter.AsyncReporter.BoundedAsyncReporter.1
                    @Override // zipkin2.reporter.SpanWithSizeConsumer
                    public boolean offer(S s, int i) {
                        arrayList.add(BoundedAsyncReporter.this.encoder.encode(s));
                        if (BoundedAsyncReporter.this.sender.messageSizeInBytes(arrayList) > BoundedAsyncReporter.this.messageMaxBytes) {
                            ArrayList arrayList2 = arrayList;
                            arrayList2.remove(arrayList2.size() - 1);
                            return false;
                        }
                        return true;
                    }
                });
                try {
                    this.sender.sendSpans(arrayList).execute();
                } catch (IOException | Error | RuntimeException e) {
                    int size = arrayList.size();
                    Call.propagateIfFatal(e);
                    this.metrics.incrementMessagesDropped(e);
                    this.metrics.incrementSpansDropped(size);
                    Logger logger2 = logger;
                    if (logger2.isLoggable(Level.FINE)) {
                        Level level = Level.FINE;
                        Object[] objArr = new Object[3];
                        objArr[0] = Integer.valueOf(size);
                        objArr[1] = e.getClass().getSimpleName();
                        objArr[2] = e.getMessage() == null ? "" : e.getMessage();
                        logger2.log(level, String.format("Dropped %s spans due to %s(%s)", objArr), e);
                    }
                    if (e instanceof IllegalStateException) {
                        throw ((IllegalStateException) e);
                    }
                }
            }
        }

        @Override // zipkin2.Component
        public CheckResult check() {
            return this.sender.check();
        }

        @Override // zipkin2.reporter.AsyncReporter, zipkin2.Component, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (this.closed.compareAndSet(false, true)) {
                try {
                    if (!this.close.await(this.closeTimeoutNanos, TimeUnit.NANOSECONDS)) {
                        logger.warning("Timed out waiting for in-flight spans to send");
                    }
                } catch (InterruptedException unused) {
                    logger.warning("Interrupted waiting for in-flight spans to send");
                    Thread.currentThread().interrupt();
                }
                int clear = this.pending.clear();
                if (clear > 0) {
                    this.metrics.incrementSpansDropped(clear);
                    logger.warning("Dropped " + clear + " spans due to AsyncReporter.close()");
                }
            }
        }

        public String toString() {
            return "AsyncReporter{" + this.sender + "}";
        }
    }
}
