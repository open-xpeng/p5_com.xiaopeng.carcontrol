package android.support.rastermill;

import android.support.rastermill.loader.ILoader;
import android.support.rastermill.util.ThreadUtils;
import android.util.Pair;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class LoaderAsyncTask {
    private Runnable mBackground;
    private final AtomicBoolean mCancelled = new AtomicBoolean();
    private FrameSequenceController mFrameSequenceController;
    private boolean mRelease;

    public LoaderAsyncTask(FrameSequenceController frameSequenceController) {
        this.mFrameSequenceController = frameSequenceController;
    }

    public final void execute() {
        final ILoader loader = this.mFrameSequenceController.getLoader();
        Runnable runnable = new Runnable() { // from class: android.support.rastermill.LoaderAsyncTask.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                FrameSequenceDrawable frameSequenceDrawable;
                FrameSequenceDrawable frameSequenceDrawable2;
                try {
                    frameSequenceDrawable2 = LoaderAsyncTask.this.mFrameSequenceController.getFrameSequenceDrawable(loader);
                } catch (Throwable unused) {
                }
                try {
                    frameSequenceDrawable = frameSequenceDrawable2;
                    if (LogUtil.isLogEnable()) {
                        ILoader iLoader = loader;
                        LogUtil.e("doInBackground : " + (iLoader != null ? iLoader.getKey() : null));
                        frameSequenceDrawable = frameSequenceDrawable2;
                    }
                } catch (Throwable unused2) {
                    r0 = frameSequenceDrawable2;
                    LoaderAsyncTask.this.mCancelled.set(true);
                    frameSequenceDrawable = r0;
                    LoaderAsyncTask.this.post(loader, frameSequenceDrawable);
                }
                LoaderAsyncTask.this.post(loader, frameSequenceDrawable);
            }
        };
        this.mBackground = runnable;
        ThreadUtils.postWorker(runnable);
    }

    public final void cancel() {
        this.mCancelled.set(true);
        Runnable runnable = this.mBackground;
        if (runnable != null) {
            ThreadUtils.removeWorker(runnable);
        }
        ILoader loader = this.mFrameSequenceController.getLoader();
        release(loader);
        if (LogUtil.isLogEnable()) {
            LogUtil.e("cancel : " + loader.getKey());
        }
    }

    public final boolean isCancelled() {
        return this.mCancelled.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void post(final ILoader iLoader, final FrameSequenceDrawable frameSequenceDrawable) {
        ThreadUtils.postMain(new Runnable() { // from class: android.support.rastermill.LoaderAsyncTask.2
            @Override // java.lang.Runnable
            public void run() {
                boolean isCancelled = LoaderAsyncTask.this.isCancelled();
                Pair pair = new Pair(iLoader, frameSequenceDrawable);
                if (isCancelled) {
                    LoaderAsyncTask.this.onCancelled(pair);
                } else {
                    LoaderAsyncTask.this.onPostExecute(pair);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCancelled(Pair<ILoader, FrameSequenceDrawable> pair) {
        ILoader iLoader = (ILoader) pair.first;
        if (LogUtil.isLogEnable()) {
            LogUtil.e("onCancelled : " + iLoader.getKey());
        }
        FrameSequenceController.removeTask(this.mFrameSequenceController.getImageView());
        FrameSequenceDrawable frameSequenceDrawable = (FrameSequenceDrawable) pair.second;
        if (frameSequenceDrawable != null) {
            FrameSequenceUtil.destroy(frameSequenceDrawable, iLoader.getKey());
        }
        release(iLoader);
    }

    private void release(ILoader iLoader) {
        if (this.mRelease) {
            return;
        }
        iLoader.release();
        this.mRelease = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPostExecute(Pair<ILoader, FrameSequenceDrawable> pair) {
        ILoader iLoader = (ILoader) pair.first;
        if (LogUtil.isLogEnable()) {
            LogUtil.e("onPostExecute : " + (iLoader != null ? iLoader.getKey() : null));
        }
        FrameSequenceController.removeTask(this.mFrameSequenceController.getImageView());
        this.mFrameSequenceController.applyInternal(iLoader, (FrameSequenceDrawable) pair.second, true);
        release(iLoader);
    }
}
