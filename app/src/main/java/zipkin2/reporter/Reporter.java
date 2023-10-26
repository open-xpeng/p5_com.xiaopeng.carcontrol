package zipkin2.reporter;

import zipkin2.Span;

/* loaded from: classes3.dex */
public interface Reporter<S> {
    public static final Reporter<Span> NOOP = new Reporter<Span>() { // from class: zipkin2.reporter.Reporter.1
        @Override // zipkin2.reporter.Reporter
        public void report(Span span) {
        }

        public String toString() {
            return "NoopReporter{}";
        }
    };
    public static final Reporter<Span> CONSOLE = new Reporter<Span>() { // from class: zipkin2.reporter.Reporter.2
        public String toString() {
            return "ConsoleReporter{}";
        }

        @Override // zipkin2.reporter.Reporter
        public void report(Span span) {
            System.out.println(span.toString());
        }
    };

    void report(S s);
}
