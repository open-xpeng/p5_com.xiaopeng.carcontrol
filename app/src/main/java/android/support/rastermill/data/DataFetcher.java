package android.support.rastermill.data;

/* loaded from: classes.dex */
public interface DataFetcher<T> {
    void cancel();

    void cleanup();

    String getId();

    T loadData() throws Exception;
}
