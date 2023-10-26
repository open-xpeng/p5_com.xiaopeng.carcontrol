package brave;

/* loaded from: classes.dex */
public interface SpanCustomizer {
    SpanCustomizer annotate(String str);

    SpanCustomizer name(String str);

    SpanCustomizer tag(String str, String str2);
}
