package android.support.rastermill;

/* loaded from: classes.dex */
public interface LoadListener {
    void onFail(int i, String str);

    void onReady(int i, String str, FrameSequenceDrawable frameSequenceDrawable);
}
