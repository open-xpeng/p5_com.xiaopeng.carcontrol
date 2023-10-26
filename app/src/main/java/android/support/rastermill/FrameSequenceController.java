package android.support.rastermill;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.rastermill.CacheEngine;
import android.support.rastermill.FrameSequenceDrawable;
import android.support.rastermill.cache.DiskCache;
import android.support.rastermill.data.Encoder;
import android.support.rastermill.data.StreamEncoder;
import android.support.rastermill.loader.AssetLoader;
import android.support.rastermill.loader.FileLoader;
import android.support.rastermill.loader.HttpLoader;
import android.support.rastermill.loader.ILoader;
import android.support.rastermill.loader.InputStreamLoader;
import android.support.rastermill.loader.NoneLoader;
import android.support.rastermill.loader.ResourceLoader;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public class FrameSequenceController implements CacheEngine.CacheCallback {
    private static final FileOpener DEFAULT_FILE_OPENER = new FileOpener();
    private static final SparseArray<FrameSequenceController> sRunningTaskMap = new SparseArray<>();
    private Integer mAlpha;
    private String mAsset;
    private LoaderAsyncTask mAsyncTask;
    private FrameSequenceDrawable.BitmapProvider mBitmapProvider;
    private Boolean mCircleMaskEnabled;
    private ColorFilter mColorFilter;
    private Context mContext;
    private Drawable mErrorDrawable;
    private int mErrorId;
    private File mFile;
    private Boolean mFilterBitmap;
    private Drawable mFinishDrawable;
    private int mFinishId;
    private boolean mForcePlaceholder;
    private Future mFuture;
    private ImageView mImageView;
    private InputStream mInputStream;
    private String mInputStreamKey;
    private LoadListener mLoadListener;
    private ILoader mLoader;
    private FrameSequenceDrawable.OnFinishedListener mOnFinishedListener;
    private Drawable mPlaceholderDrawable;
    private int mPlaceholderId;
    private Integer mResourceId;
    private String mUrl;
    private int mDecodingThreadId = 0;
    private int mLoopCount = 3;
    private int mLoopBehavior = 3;
    private FileOpener mFileOpener = DEFAULT_FILE_OPENER;

    /* loaded from: classes.dex */
    interface DiskCacheProvider {
        DiskCache getDiskCache();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FrameSequenceController(ImageView imageView) {
        this.mImageView = imageView;
        this.mContext = imageView.getContext();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ImageView getImageView() {
        return this.mImageView;
    }

    public FrameSequenceController decodingThreadId(int i) {
        this.mDecodingThreadId = i;
        return this;
    }

    public FrameSequenceController loopCount(int i) {
        this.mLoopCount = i;
        return this;
    }

    public FrameSequenceController loopBehavior(int i) {
        this.mLoopBehavior = i;
        return this;
    }

    public FrameSequenceController onFinishedListener(FrameSequenceDrawable.OnFinishedListener onFinishedListener) {
        this.mOnFinishedListener = onFinishedListener;
        return this;
    }

    public FrameSequenceController asset(String str) {
        this.mAsset = str;
        return this;
    }

    public FrameSequenceController file(File file) {
        this.mFile = file;
        return this;
    }

    public FrameSequenceController resourceId(int i) {
        this.mResourceId = Integer.valueOf(i);
        return this;
    }

    public FrameSequenceController inputStream(InputStream inputStream) {
        this.mInputStream = inputStream;
        return this;
    }

    public FrameSequenceController inputStream(String str, InputStream inputStream) {
        this.mInputStreamKey = str;
        this.mInputStream = inputStream;
        return this;
    }

    public FrameSequenceController url(String str) {
        this.mUrl = str;
        return this;
    }

    public FrameSequenceController alpha(Integer num) {
        this.mAlpha = num;
        return this;
    }

    public FrameSequenceController circleMaskEnabled(Boolean bool) {
        this.mCircleMaskEnabled = bool;
        return this;
    }

    public FrameSequenceController colorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        return this;
    }

    public FrameSequenceController filterBitmap(Boolean bool) {
        this.mFilterBitmap = bool;
        return this;
    }

    public FrameSequenceController bitmapProvider(FrameSequenceDrawable.BitmapProvider bitmapProvider) {
        this.mBitmapProvider = bitmapProvider;
        return this;
    }

    public FrameSequenceController loadListener(LoadListener loadListener) {
        this.mLoadListener = loadListener;
        return this;
    }

    public FrameSequenceController forcePlaceholder(boolean z) {
        this.mForcePlaceholder = z;
        return this;
    }

    public FrameSequenceController placeholder(int i) {
        this.mPlaceholderId = i;
        return this;
    }

    public FrameSequenceController placeholder(Drawable drawable) {
        this.mPlaceholderDrawable = drawable;
        return this;
    }

    public FrameSequenceController error(int i) {
        this.mErrorId = i;
        return this;
    }

    public FrameSequenceController error(Drawable drawable) {
        this.mErrorDrawable = drawable;
        return this;
    }

    public FrameSequenceController finish(int i) {
        this.mFinishId = i;
        return this;
    }

    public FrameSequenceController finish(Drawable drawable) {
        this.mFinishDrawable = drawable;
        return this;
    }

    public void applyAsync() {
        ILoader loader = getLoader();
        if (checkApplySame(loader)) {
            return;
        }
        if (this.mForcePlaceholder) {
            setPlaceholder();
        }
        LoaderAsyncTask loaderAsyncTask = this.mAsyncTask;
        if (loaderAsyncTask != null && !loaderAsyncTask.isCancelled()) {
            this.mAsyncTask.cancel();
        }
        Future future = this.mFuture;
        if (future != null && !future.isDone() && !this.mFuture.isCancelled()) {
            this.mFuture.cancel(true);
        }
        this.mAsyncTask = null;
        this.mFuture = null;
        putTask(this.mImageView, this);
        if (loader.isCacheRequired()) {
            if (loader.exists()) {
                this.mAsyncTask = new LoaderAsyncTask(this);
            } else {
                this.mFuture = CacheEngine.getInstance().cache(loader.getKey(), this);
            }
        } else {
            this.mAsyncTask = new LoaderAsyncTask(this);
        }
        LoaderAsyncTask loaderAsyncTask2 = this.mAsyncTask;
        if (loaderAsyncTask2 != null) {
            loaderAsyncTask2.execute();
        }
    }

    public FrameSequenceDrawable apply() {
        ILoader loader = getLoader();
        if (checkApplySame(loader)) {
            return (FrameSequenceDrawable) this.mImageView.getDrawable();
        }
        FrameSequenceDrawable frameSequenceDrawable = getFrameSequenceDrawable(loader);
        applyInternal(loader, frameSequenceDrawable, true);
        return frameSequenceDrawable;
    }

    private boolean checkApplySame(ILoader iLoader) {
        String key = getKey(this.mImageView);
        if (!TextUtils.isEmpty(key) && key.equals(iLoader.getKey()) && (this.mImageView.getDrawable() instanceof FrameSequenceDrawable)) {
            FrameSequenceDrawable frameSequenceDrawable = (FrameSequenceDrawable) this.mImageView.getDrawable();
            frameSequenceDrawable.stop();
            setImageView(this.mImageView, null, null);
            applyInternal(iLoader, frameSequenceDrawable, false);
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void applyInternal(ILoader iLoader, FrameSequenceDrawable frameSequenceDrawable, boolean z) {
        Drawable placeholderDrawable;
        if (z) {
            FrameSequenceUtil.destroy(this.mImageView);
        }
        String key = iLoader.getKey();
        if (frameSequenceDrawable != null) {
            frameSequenceDrawable.setLoopCount(this.mLoopCount);
            frameSequenceDrawable.setLoopBehavior(this.mLoopBehavior);
            if (hasFinish()) {
                frameSequenceDrawable.setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() { // from class: android.support.rastermill.FrameSequenceController.1
                    @Override // android.support.rastermill.FrameSequenceDrawable.OnFinishedListener
                    public void onFinished(FrameSequenceDrawable frameSequenceDrawable2) {
                        FrameSequenceUtil.destroy(FrameSequenceController.this.mImageView);
                        FrameSequenceController.setImageView(FrameSequenceController.this.mImageView, FrameSequenceController.this.getFinishDrawable(), null);
                        if (FrameSequenceController.this.mOnFinishedListener != null) {
                            FrameSequenceController.this.mOnFinishedListener.onFinished(frameSequenceDrawable2);
                        }
                    }
                });
            } else {
                frameSequenceDrawable.setOnFinishedListener(this.mOnFinishedListener);
            }
            frameSequenceDrawable.setColorFilter(this.mColorFilter);
            Integer num = this.mAlpha;
            if (num != null) {
                frameSequenceDrawable.setAlpha(num.intValue());
            }
            Boolean bool = this.mCircleMaskEnabled;
            if (bool != null) {
                frameSequenceDrawable.setCircleMaskEnabled(bool.booleanValue());
            }
            Boolean bool2 = this.mFilterBitmap;
            if (bool2 != null) {
                frameSequenceDrawable.setFilterBitmap(bool2.booleanValue());
            }
            placeholderDrawable = frameSequenceDrawable;
        } else {
            key = null;
            placeholderDrawable = getPlaceholderDrawable(true);
        }
        setImageView(this.mImageView, placeholderDrawable, key);
        LoadListener loadListener = this.mLoadListener;
        if (loadListener != null) {
            if (frameSequenceDrawable == null) {
                loadListener.onFail(iLoader.getType(), key);
            } else {
                loadListener.onReady(iLoader.getType(), key, frameSequenceDrawable);
            }
        }
        iLoader.release();
    }

    private void setPlaceholder() {
        FrameSequenceUtil.destroy(this.mImageView);
        setImageView(this.mImageView, getPlaceholderDrawable(false), null);
    }

    private Drawable getPlaceholderDrawable(boolean z) {
        Drawable drawable;
        if (!z || this.mErrorId == 0) {
            if (!z || (drawable = this.mErrorDrawable) == null) {
                if (this.mPlaceholderId != 0) {
                    return this.mContext.getResources().getDrawable(this.mPlaceholderId);
                }
                Drawable drawable2 = this.mPlaceholderDrawable;
                if (drawable2 != null) {
                    return drawable2;
                }
                return null;
            }
            return drawable;
        }
        return this.mContext.getResources().getDrawable(this.mErrorId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Drawable getFinishDrawable() {
        if (this.mFinishId != 0) {
            return this.mContext.getResources().getDrawable(this.mFinishId);
        }
        Drawable drawable = this.mFinishDrawable;
        if (drawable != null) {
            return drawable;
        }
        return null;
    }

    private boolean hasFinish() {
        return (this.mFinishId == 0 && this.mFinishDrawable == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ILoader getLoader() {
        ILoader noneLoader;
        ILoader iLoader = this.mLoader;
        if (iLoader != null) {
            return iLoader;
        }
        if (this.mInputStream != null) {
            noneLoader = new InputStreamLoader(this.mContext, this.mInputStreamKey, this.mInputStream);
        } else {
            Context context = this.mContext;
            if (context != null && this.mResourceId != null) {
                noneLoader = new ResourceLoader(this.mContext, this.mResourceId.intValue());
            } else if (context != null && !TextUtils.isEmpty(this.mAsset)) {
                noneLoader = new AssetLoader(this.mContext, this.mAsset);
            } else {
                File file = this.mFile;
                if (file != null && file.exists()) {
                    noneLoader = new FileLoader(this.mContext, this.mFile);
                } else if (!TextUtils.isEmpty(this.mUrl)) {
                    noneLoader = new HttpLoader(this.mContext, this.mUrl);
                } else {
                    noneLoader = new NoneLoader(this.mContext);
                }
            }
        }
        this.mLoader = noneLoader;
        return noneLoader;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FrameSequenceDrawable getFrameSequenceDrawable(ILoader iLoader) {
        FrameSequence frameSequence;
        if (iLoader == null || !iLoader.exists() || (frameSequence = iLoader.getFrameSequence()) == null) {
            return null;
        }
        return getFrameSequenceDrawable(frameSequence);
    }

    private FrameSequenceDrawable getFrameSequenceDrawable(FrameSequence frameSequence) {
        if (this.mBitmapProvider != null) {
            return new FrameSequenceDrawable(frameSequence, this.mBitmapProvider, this.mDecodingThreadId);
        }
        return new FrameSequenceDrawable(frameSequence, this.mDecodingThreadId);
    }

    private void cancelAsyncTask() {
        LoaderAsyncTask loaderAsyncTask = this.mAsyncTask;
        if (loaderAsyncTask != null && !loaderAsyncTask.isCancelled()) {
            this.mAsyncTask.cancel();
        }
        Future future = this.mFuture;
        if (future != null && !future.isDone() && !this.mFuture.isCancelled()) {
            this.mFuture.cancel(true);
        }
        this.mAsyncTask = null;
        this.mFuture = null;
    }

    @Override // android.support.rastermill.CacheEngine.CacheCallback
    public void onCacheFinished(String str) {
        if (LogUtil.isLogEnable()) {
            LogUtil.e("onCacheFinished:" + str);
        }
        this.mFuture = null;
        LoaderAsyncTask loaderAsyncTask = new LoaderAsyncTask(this);
        this.mAsyncTask = loaderAsyncTask;
        loaderAsyncTask.execute();
    }

    @Override // android.support.rastermill.CacheEngine.CacheCallback
    public void onCacheFailure(String str) {
        if (LogUtil.isLogEnable()) {
            LogUtil.e("onCacheFailure:" + str);
        }
        this.mFuture = null;
        removeTask(this.mImageView);
    }

    @Override // android.support.rastermill.CacheEngine.CacheCallback
    public void cacheData(String str, InputStream inputStream) {
        CacheEngine.getInstance().getDiskCache().put(str, new SourceWriter(new StreamEncoder(), inputStream));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setImageView(ImageView imageView, Drawable drawable, String str) {
        if (drawable == null) {
            str = null;
        }
        setKey(imageView, str);
        imageView.setImageDrawable(drawable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getKey(View view) {
        Object tag = view.getTag(R.id.tag_key);
        if (tag instanceof String) {
            return (String) tag;
        }
        return null;
    }

    static void setKey(View view, String str) {
        view.setTag(R.id.tag_key, str);
    }

    private static void putTask(ImageView imageView, FrameSequenceController frameSequenceController) {
        if (imageView == null) {
            return;
        }
        int hashCode = imageView.hashCode();
        SparseArray<FrameSequenceController> sparseArray = sRunningTaskMap;
        sparseArray.put(hashCode, frameSequenceController);
        if (LogUtil.isLogEnable()) {
            LogUtil.e("putTask : " + hashCode + ", new size : " + sparseArray.size());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void removeTask(ImageView imageView) {
        if (imageView == null) {
            return;
        }
        int hashCode = imageView.hashCode();
        SparseArray<FrameSequenceController> sparseArray = sRunningTaskMap;
        sparseArray.remove(hashCode);
        if (LogUtil.isLogEnable()) {
            LogUtil.e("removeTask : " + hashCode + ", new size : " + sparseArray.size());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void cancelTask(ImageView imageView) {
        FrameSequenceController frameSequenceController;
        if (imageView == null || (frameSequenceController = sRunningTaskMap.get(imageView.hashCode())) == null) {
            return;
        }
        frameSequenceController.cancelAsyncTask();
        removeTask(imageView);
    }

    /* loaded from: classes.dex */
    class SourceWriter<DataType> implements DiskCache.Writer {
        private final DataType data;
        private final Encoder<DataType> encoder;

        public SourceWriter(Encoder<DataType> encoder, DataType datatype) {
            this.encoder = encoder;
            this.data = datatype;
        }

        @Override // android.support.rastermill.cache.DiskCache.Writer
        public boolean write(File file) {
            boolean z;
            OutputStream outputStream = null;
            try {
                try {
                    outputStream = FrameSequenceController.this.mFileOpener.open(file);
                    z = this.encoder.encode(this.data, outputStream);
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException unused) {
                        }
                    }
                } catch (FileNotFoundException e) {
                    LogUtil.e("Failed to find file to write to disk cache", e);
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    z = false;
                }
                return z;
            } catch (Throwable th) {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException unused3) {
                    }
                }
                throw th;
            }
        }
    }

    /* loaded from: classes.dex */
    static class FileOpener {
        FileOpener() {
        }

        public OutputStream open(File file) throws FileNotFoundException {
            return new BufferedOutputStream(new FileOutputStream(file));
        }
    }
}
