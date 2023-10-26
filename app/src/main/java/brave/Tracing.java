package brave;

import brave.handler.FinishedSpanHandler;
import brave.internal.IpLiteral;
import brave.internal.Nullable;
import brave.internal.Platform;
import brave.internal.handler.FinishedSpanHandlers;
import brave.internal.handler.ZipkinFinishedSpanHandler;
import brave.internal.recorder.PendingSpans;
import brave.propagation.B3Propagation;
import brave.propagation.CurrentTraceContext;
import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import brave.sampler.Sampler;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import zipkin2.Endpoint;
import zipkin2.reporter.Reporter;

/* loaded from: classes.dex */
public abstract class Tracing implements Closeable {
    static volatile Tracing current;

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public abstract void close();

    public abstract CurrentTraceContext currentTraceContext();

    public abstract ErrorParser errorParser();

    public abstract boolean isNoop();

    public abstract Propagation.Factory propagationFactory();

    public abstract Sampler sampler();

    public abstract void setNoop(boolean z);

    public abstract Tracer tracer();

    public static Builder newBuilder() {
        return new Builder();
    }

    public Propagation<String> propagation() {
        return propagationFactory().create(Propagation.KeyFactory.STRING);
    }

    public final Clock clock(TraceContext traceContext) {
        return tracer().pendingSpans.getOrCreate(traceContext, false).clock();
    }

    @Nullable
    public static Tracer currentTracer() {
        Tracing tracing = current;
        if (tracing != null) {
            return tracing.tracer();
        }
        return null;
    }

    @Nullable
    public static Tracing current() {
        return current;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        Clock clock;
        String localIp;
        int localPort;
        Reporter<zipkin2.Span> spanReporter;
        String localServiceName = "unknown";
        Sampler sampler = Sampler.ALWAYS_SAMPLE;
        CurrentTraceContext currentTraceContext = CurrentTraceContext.Default.inheritable();
        boolean traceId128Bit = false;
        boolean supportsJoin = true;
        Propagation.Factory propagationFactory = B3Propagation.FACTORY;
        ErrorParser errorParser = new ErrorParser();
        List<FinishedSpanHandler> finishedSpanHandlers = new ArrayList();

        public Builder localServiceName(String str) {
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException(str + " is not a valid serviceName");
            }
            this.localServiceName = str.toLowerCase(Locale.ROOT);
            return this;
        }

        public Builder localIp(String str) {
            String ipOrNull = IpLiteral.ipOrNull(str);
            if (ipOrNull == null) {
                throw new IllegalArgumentException(str + " is not a valid IP");
            }
            this.localIp = ipOrNull;
            return this;
        }

        public Builder localPort(int i) {
            if (i > 65535) {
                throw new IllegalArgumentException("invalid localPort " + i);
            }
            if (i < 0) {
                i = 0;
            }
            this.localPort = i;
            return this;
        }

        @Deprecated
        public Builder endpoint(Endpoint endpoint) {
            Objects.requireNonNull(endpoint, "endpoint == null");
            this.localServiceName = endpoint.serviceName();
            this.localIp = endpoint.ipv6() != null ? endpoint.ipv6() : endpoint.ipv4();
            this.localPort = endpoint.portAsInt();
            return this;
        }

        public Builder spanReporter(Reporter<zipkin2.Span> reporter) {
            Objects.requireNonNull(reporter, "spanReporter == null");
            this.spanReporter = reporter;
            return this;
        }

        public Builder clock(Clock clock) {
            Objects.requireNonNull(clock, "clock == null");
            this.clock = clock;
            return this;
        }

        public Builder sampler(Sampler sampler) {
            Objects.requireNonNull(sampler, "sampler == null");
            this.sampler = sampler;
            return this;
        }

        public Builder currentTraceContext(CurrentTraceContext currentTraceContext) {
            Objects.requireNonNull(currentTraceContext, "currentTraceContext == null");
            this.currentTraceContext = currentTraceContext;
            return this;
        }

        public Builder propagationFactory(Propagation.Factory factory) {
            Objects.requireNonNull(factory, "propagationFactory == null");
            this.propagationFactory = factory;
            return this;
        }

        public Builder traceId128Bit(boolean z) {
            this.traceId128Bit = z;
            return this;
        }

        public Builder supportsJoin(boolean z) {
            this.supportsJoin = z;
            return this;
        }

        public Builder errorParser(ErrorParser errorParser) {
            this.errorParser = errorParser;
            return this;
        }

        public Builder addFinishedSpanHandler(FinishedSpanHandler finishedSpanHandler) {
            Objects.requireNonNull(finishedSpanHandler, "finishedSpanHandler == null");
            this.finishedSpanHandlers.add(finishedSpanHandler);
            return this;
        }

        public Tracing build() {
            if (this.clock == null) {
                this.clock = Platform.get().clock();
            }
            if (this.localIp == null) {
                this.localIp = Platform.get().linkLocalIp();
            }
            if (this.spanReporter == null) {
                this.spanReporter = new LoggingReporter();
            }
            return new Default(this);
        }

        Builder() {
        }
    }

    /* loaded from: classes.dex */
    static final class LoggingReporter implements Reporter<zipkin2.Span> {
        final Logger logger = Logger.getLogger(Tracer.class.getName());

        LoggingReporter() {
        }

        @Override // zipkin2.reporter.Reporter
        public void report(zipkin2.Span span) {
            Objects.requireNonNull(span, "span == null");
            if (this.logger.isLoggable(Level.INFO)) {
                this.logger.info(span.toString());
            }
        }

        public String toString() {
            return "LoggingReporter{name=" + this.logger.getName() + "}";
        }
    }

    /* loaded from: classes.dex */
    static final class Default extends Tracing {
        final Clock clock;
        final CurrentTraceContext currentTraceContext;
        final ErrorParser errorParser;
        final AtomicBoolean noop;
        final Propagation.Factory propagationFactory;
        final Sampler sampler;
        final Propagation<String> stringPropagation;
        final Tracer tracer;

        Default(Builder builder) {
            Clock clock = builder.clock;
            this.clock = clock;
            ErrorParser errorParser = builder.errorParser;
            this.errorParser = errorParser;
            Propagation.Factory factory = builder.propagationFactory;
            this.propagationFactory = factory;
            this.stringPropagation = builder.propagationFactory.create(Propagation.KeyFactory.STRING);
            this.currentTraceContext = builder.currentTraceContext;
            this.sampler = builder.sampler;
            AtomicBoolean atomicBoolean = new AtomicBoolean();
            this.noop = atomicBoolean;
            List<FinishedSpanHandler> list = builder.finishedSpanHandlers;
            FinishedSpanHandler finishedSpanHandler = FinishedSpanHandler.NOOP;
            if (builder.spanReporter != Reporter.NOOP) {
                ZipkinFinishedSpanHandler zipkinFinishedSpanHandler = new ZipkinFinishedSpanHandler(builder.spanReporter, errorParser, builder.localServiceName, builder.localIp, builder.localPort);
                ArrayList arrayList = new ArrayList(list);
                arrayList.add(zipkinFinishedSpanHandler);
                list = arrayList;
                finishedSpanHandler = zipkinFinishedSpanHandler;
            }
            FinishedSpanHandler noopAware = FinishedSpanHandlers.noopAware(FinishedSpanHandlers.compose(list), atomicBoolean);
            this.tracer = new Tracer(builder.clock, builder.propagationFactory, noopAware, new PendingSpans(clock, finishedSpanHandler, atomicBoolean), builder.sampler, builder.currentTraceContext, builder.traceId128Bit || factory.requires128BitTraceId(), builder.supportsJoin && factory.supportsJoin(), noopAware.alwaysSampleLocal(), atomicBoolean);
            maybeSetCurrent();
        }

        @Override // brave.Tracing
        public Tracer tracer() {
            return this.tracer;
        }

        @Override // brave.Tracing
        public Propagation<String> propagation() {
            return this.stringPropagation;
        }

        @Override // brave.Tracing
        public Propagation.Factory propagationFactory() {
            return this.propagationFactory;
        }

        @Override // brave.Tracing
        public Sampler sampler() {
            return this.sampler;
        }

        @Override // brave.Tracing
        public CurrentTraceContext currentTraceContext() {
            return this.currentTraceContext;
        }

        @Override // brave.Tracing
        public ErrorParser errorParser() {
            return this.errorParser;
        }

        @Override // brave.Tracing
        public boolean isNoop() {
            return this.noop.get();
        }

        @Override // brave.Tracing
        public void setNoop(boolean z) {
            this.noop.set(z);
        }

        private void maybeSetCurrent() {
            if (current != null) {
                return;
            }
            synchronized (Tracing.class) {
                if (current == null) {
                    current = this;
                }
            }
        }

        @Override // brave.Tracing, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (current != this) {
                return;
            }
            synchronized (Tracing.class) {
                if (current == this) {
                    current = null;
                }
            }
        }
    }

    Tracing() {
    }
}
