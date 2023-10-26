package brave;

import brave.handler.MutableSpan;
import org.eclipse.paho.android.service.MqttServiceConstants;

/* loaded from: classes.dex */
public class ErrorParser {
    public static final ErrorParser NOOP = new ErrorParser() { // from class: brave.ErrorParser.1
        @Override // brave.ErrorParser
        protected void error(Throwable th, Object obj) {
        }
    };

    public final void error(Throwable th, ScopedSpan scopedSpan) {
        error(th, (Object) scopedSpan);
    }

    public final void error(Throwable th, SpanCustomizer spanCustomizer) {
        error(th, (Object) spanCustomizer);
    }

    public final void error(Throwable th, MutableSpan mutableSpan) {
        error(th, (Object) mutableSpan);
    }

    protected void error(Throwable th, Object obj) {
        String message = th.getMessage();
        if (message == null) {
            message = th.getClass().getSimpleName();
        }
        tag(obj, MqttServiceConstants.TRACE_ERROR, message);
    }

    protected final void annotate(Object obj, String str) {
        if (obj instanceof SpanCustomizer) {
            ((SpanCustomizer) obj).annotate(str);
        } else if (obj instanceof ScopedSpan) {
            ((ScopedSpan) obj).annotate(str);
        }
    }

    protected final void tag(Object obj, String str, String str2) {
        if (obj instanceof SpanCustomizer) {
            ((SpanCustomizer) obj).tag(str, str2);
        } else if (obj instanceof ScopedSpan) {
            ((ScopedSpan) obj).tag(str, str2);
        } else if (obj instanceof MutableSpan) {
            ((MutableSpan) obj).tag(str, str2);
        }
    }
}
