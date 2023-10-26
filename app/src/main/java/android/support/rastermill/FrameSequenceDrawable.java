package android.support.rastermill;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.rastermill.FrameSequence;
import android.util.Log;
import org.tukaani.xz.common.Util;

/* loaded from: classes.dex */
public class FrameSequenceDrawable extends Drawable implements Animatable, Runnable {
    public static final int DEFAULT_DECODING_THREAD_ID = 0;
    private static final long DEFAULT_DELAY_MS = 100;
    public static final int LOOP_DEFAULT = 3;
    public static final int LOOP_FINITE = 1;
    public static final int LOOP_INF = 2;
    @Deprecated
    public static final int LOOP_ONCE = 1;
    private static final long MIN_DELAY_MS = 20;
    private static final int STATE_DECODING = 2;
    private static final int STATE_READY_TO_SWAP = 4;
    private static final int STATE_SCHEDULED = 1;
    private static final int STATE_WAITING_TO_SWAP = 3;
    private static final String TAG = "FrameSequence";
    private static BitmapProvider sAllocatingBitmapProvider = new BitmapProvider() { // from class: android.support.rastermill.FrameSequenceDrawable.1
        @Override // android.support.rastermill.FrameSequenceDrawable.BitmapProvider
        public void releaseBitmap(Bitmap bitmap) {
        }

        @Override // android.support.rastermill.FrameSequenceDrawable.BitmapProvider
        public Bitmap acquireBitmap(int i, int i2) {
            return Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        }
    };
    private static Handler sMainThreadHandler = new Handler(Looper.getMainLooper());
    private Bitmap mBackBitmap;
    private BitmapShader mBackBitmapShader;
    private final BitmapProvider mBitmapProvider;
    private boolean mCircleMaskEnabled;
    private int mCurrentLoop;
    private Runnable mDecodeRunnable;
    private final Handler mDecodingThreadHandler;
    private final int mDecodingThreadId;
    private boolean mDestroyed;
    private Runnable mFinishedCallbackRunnable;
    private final FrameSequence mFrameSequence;
    private final FrameSequence.State mFrameSequenceState;
    private Bitmap mFrontBitmap;
    private BitmapShader mFrontBitmapShader;
    private int mLastNextFrameToDecode;
    private long mLastSwap;
    private final Object mLock;
    private int mLoopBehavior;
    private int mLoopCount;
    private int mNextFrameToDecode;
    private long mNextSwap;
    private OnFinishedListener mOnFinishedListener;
    private final Paint mPaint;
    private Runnable mScheduleSelfRunnable;
    private final Rect mSrcRect;
    private int mState;
    private RectF mTempRectF;

    /* loaded from: classes.dex */
    public interface BitmapProvider {
        Bitmap acquireBitmap(int i, int i2);

        void releaseBitmap(Bitmap bitmap);
    }

    /* loaded from: classes.dex */
    public interface OnFinishedListener {
        void onFinished(FrameSequenceDrawable frameSequenceDrawable);
    }

    public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
        this.mOnFinishedListener = onFinishedListener;
    }

    public void setLoopBehavior(int i) {
        this.mLoopBehavior = i;
    }

    public void setLoopCount(int i) {
        this.mLoopCount = i;
    }

    private static Bitmap acquireAndValidateBitmap(BitmapProvider bitmapProvider, int i, int i2) {
        Bitmap acquireBitmap = bitmapProvider.acquireBitmap(i, i2);
        if (acquireBitmap.getWidth() < i || acquireBitmap.getHeight() < i2 || acquireBitmap.getConfig() != Bitmap.Config.ARGB_8888) {
            throw new IllegalArgumentException("Invalid bitmap provided");
        }
        return acquireBitmap;
    }

    public FrameSequenceDrawable(FrameSequence frameSequence) {
        this(frameSequence, sAllocatingBitmapProvider);
    }

    public FrameSequenceDrawable(FrameSequence frameSequence, int i) {
        this(frameSequence, sAllocatingBitmapProvider, i);
    }

    public FrameSequenceDrawable(FrameSequence frameSequence, BitmapProvider bitmapProvider) {
        this(frameSequence, bitmapProvider, 0);
    }

    public FrameSequenceDrawable(FrameSequence frameSequence, BitmapProvider bitmapProvider, int i) {
        this.mLock = new Object();
        this.mDestroyed = false;
        this.mLoopBehavior = 3;
        this.mLoopCount = 1;
        this.mTempRectF = new RectF();
        this.mDecodeRunnable = new Runnable() { // from class: android.support.rastermill.FrameSequenceDrawable.2
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                Bitmap bitmap;
                synchronized (FrameSequenceDrawable.this.mLock) {
                    if (FrameSequenceDrawable.this.mDestroyed) {
                        return;
                    }
                    int i2 = FrameSequenceDrawable.this.mNextFrameToDecode;
                    if (i2 < 0) {
                        return;
                    }
                    Bitmap bitmap2 = FrameSequenceDrawable.this.mBackBitmap;
                    FrameSequenceDrawable.this.mState = 2;
                    long j = 0;
                    boolean z2 = true;
                    try {
                        j = FrameSequenceDrawable.this.mFrameSequenceState.getFrame(i2, bitmap2, i2 - 2);
                        z = false;
                    } catch (Exception e) {
                        Log.e("FrameSequence", "exception during decode: " + e);
                        z = true;
                    }
                    if (j < FrameSequenceDrawable.MIN_DELAY_MS) {
                        j = FrameSequenceDrawable.DEFAULT_DELAY_MS;
                    }
                    synchronized (FrameSequenceDrawable.this.mLock) {
                        bitmap = null;
                        if (FrameSequenceDrawable.this.mDestroyed) {
                            Bitmap bitmap3 = FrameSequenceDrawable.this.mBackBitmap;
                            FrameSequenceDrawable.this.mBackBitmap = null;
                            bitmap = bitmap3;
                        } else if (FrameSequenceDrawable.this.mNextFrameToDecode >= 0 && FrameSequenceDrawable.this.mState == 2) {
                            FrameSequenceDrawable frameSequenceDrawable = FrameSequenceDrawable.this;
                            frameSequenceDrawable.mNextSwap = z ? Util.VLI_MAX : j + frameSequenceDrawable.mLastSwap;
                            FrameSequenceDrawable.this.mState = 3;
                        }
                        z2 = false;
                    }
                    if (z2) {
                        FrameSequenceDrawable.sMainThreadHandler.post(FrameSequenceDrawable.this.mScheduleSelfRunnable);
                    }
                    if (bitmap != null) {
                        FrameSequenceDrawable.this.mBitmapProvider.releaseBitmap(bitmap);
                    }
                }
            }
        };
        this.mFinishedCallbackRunnable = new Runnable() { // from class: android.support.rastermill.FrameSequenceDrawable.3
            @Override // java.lang.Runnable
            public void run() {
                synchronized (FrameSequenceDrawable.this.mLock) {
                    FrameSequenceDrawable.this.mNextFrameToDecode = -1;
                    FrameSequenceDrawable.this.mLastNextFrameToDecode = -1;
                    FrameSequenceDrawable.this.mState = 0;
                }
                if (FrameSequenceDrawable.this.mOnFinishedListener != null) {
                    FrameSequenceDrawable.this.mOnFinishedListener.onFinished(FrameSequenceDrawable.this);
                }
                if (LogUtil.isLogEnable()) {
                    LogUtil.e("FrameSequenceDrawable onFinished");
                }
            }
        };
        this.mScheduleSelfRunnable = new Runnable() { // from class: android.support.rastermill.FrameSequenceDrawable.4
            @Override // java.lang.Runnable
            public void run() {
                FrameSequenceDrawable frameSequenceDrawable = FrameSequenceDrawable.this;
                frameSequenceDrawable.scheduleSelf(frameSequenceDrawable, frameSequenceDrawable.mNextSwap);
            }
        };
        if (frameSequence == null || bitmapProvider == null) {
            throw new IllegalArgumentException();
        }
        this.mFrameSequence = frameSequence;
        FrameSequence.State createState = frameSequence.createState();
        this.mFrameSequenceState = createState;
        int width = frameSequence.getWidth();
        int height = frameSequence.getHeight();
        this.mBitmapProvider = bitmapProvider;
        this.mFrontBitmap = acquireAndValidateBitmap(bitmapProvider, width, height);
        this.mBackBitmap = acquireAndValidateBitmap(bitmapProvider, width, height);
        this.mSrcRect = new Rect(0, 0, width, height);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setFilterBitmap(true);
        this.mLastSwap = 0L;
        this.mNextFrameToDecode = -1;
        this.mLastNextFrameToDecode = -1;
        createState.getFrame(0, this.mFrontBitmap, -1);
        this.mDecodingThreadId = i;
        this.mDecodingThreadHandler = DecodingThreadFactory.getDecodingThreadHandler(i);
    }

    public final void setCircleMaskEnabled(boolean z) {
        if (this.mCircleMaskEnabled != z) {
            this.mCircleMaskEnabled = z;
            if (z) {
                this.mFrontBitmapShader = new BitmapShader(this.mFrontBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                this.mBackBitmapShader = new BitmapShader(this.mBackBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            } else {
                this.mFrontBitmapShader = null;
                this.mBackBitmapShader = null;
            }
            this.mPaint.setAntiAlias(z);
            invalidateSelf();
        }
    }

    public final boolean getCircleMaskEnabled() {
        return this.mCircleMaskEnabled;
    }

    private void checkDestroyedLocked() {
        if (this.mDestroyed) {
            throw new IllegalStateException("Cannot perform operation on recycled drawable");
        }
    }

    public boolean isDestroyed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    public FrameSequence getFrameSequence() {
        return this.mFrameSequence;
    }

    public void destroy() {
        Bitmap bitmap;
        Bitmap bitmap2;
        if (this.mBitmapProvider == null) {
            throw new IllegalStateException("BitmapProvider must be non-null");
        }
        synchronized (this.mLock) {
            checkDestroyedLocked();
            bitmap = this.mFrontBitmap;
            bitmap2 = null;
            this.mFrontBitmap = null;
            if (this.mState != 2) {
                Bitmap bitmap3 = this.mBackBitmap;
                this.mBackBitmap = null;
                bitmap2 = bitmap3;
            }
            this.mDestroyed = true;
        }
        this.mBitmapProvider.releaseBitmap(bitmap);
        if (bitmap2 != null) {
            this.mBitmapProvider.releaseBitmap(bitmap2);
        }
        this.mFrameSequenceState.destroy();
        if (LogUtil.isLogEnable()) {
            LogUtil.e("FrameSequenceDrawable.destroy : mFrameSequence = " + this.mFrameSequence.hashCode());
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.mFrameSequenceState.destroy();
            DecodingThreadFactory.releaseDecodingThreadHandler(this.mDecodingThreadId);
        } finally {
            super.finalize();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        synchronized (this.mLock) {
            checkDestroyedLocked();
            if (this.mState == 3 && this.mNextSwap - SystemClock.uptimeMillis() <= 0) {
                this.mState = 4;
            }
            if (isRunning() && this.mState == 4) {
                Bitmap bitmap = this.mBackBitmap;
                this.mBackBitmap = this.mFrontBitmap;
                this.mFrontBitmap = bitmap;
                BitmapShader bitmapShader = this.mBackBitmapShader;
                this.mBackBitmapShader = this.mFrontBitmapShader;
                this.mFrontBitmapShader = bitmapShader;
                this.mLastSwap = SystemClock.uptimeMillis();
                boolean z = true;
                if (this.mNextFrameToDecode == this.mFrameSequence.getFrameCount() - 1) {
                    int i = this.mCurrentLoop + 1;
                    this.mCurrentLoop = i;
                    int i2 = this.mLoopBehavior;
                    if ((i2 == 1 && i == this.mLoopCount) || (i2 == 3 && i == this.mFrameSequence.getDefaultLoopCount())) {
                        z = false;
                    }
                    if (LogUtil.isLogEnable()) {
                        LogUtil.e("FrameSequenceDrawable show last frame");
                    }
                }
                if (z) {
                    scheduleDecodeLocked(false);
                } else {
                    scheduleSelf(this.mFinishedCallbackRunnable, 0L);
                }
            }
        }
        if (this.mCircleMaskEnabled) {
            Rect bounds = getBounds();
            int intrinsicWidth = getIntrinsicWidth();
            float f = intrinsicWidth;
            float width = (bounds.width() * 1.0f) / f;
            float intrinsicHeight = getIntrinsicHeight();
            float height = (bounds.height() * 1.0f) / intrinsicHeight;
            canvas.save();
            canvas.translate(bounds.left, bounds.top);
            canvas.scale(width, height);
            float min = Math.min(bounds.width(), bounds.height());
            float f2 = min / width;
            float f3 = min / height;
            this.mTempRectF.set((f - f2) / 2.0f, (intrinsicHeight - f3) / 2.0f, (f + f2) / 2.0f, (intrinsicHeight + f3) / 2.0f);
            this.mPaint.setShader(this.mFrontBitmapShader);
            canvas.drawOval(this.mTempRectF, this.mPaint);
            canvas.restore();
            return;
        }
        this.mPaint.setShader(null);
        canvas.drawBitmap(this.mFrontBitmap, this.mSrcRect, getBounds(), this.mPaint);
    }

    private void scheduleDecodeLocked(boolean z) {
        this.mState = 1;
        if (z) {
            this.mNextFrameToDecode = (this.mLastNextFrameToDecode + 1) % this.mFrameSequence.getFrameCount();
        } else {
            this.mNextFrameToDecode = (this.mNextFrameToDecode + 1) % this.mFrameSequence.getFrameCount();
        }
        this.mDecodingThreadHandler.post(this.mDecodeRunnable);
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z;
        synchronized (this.mLock) {
            if (this.mNextFrameToDecode < 0 || this.mState != 3) {
                z = false;
            } else {
                this.mState = 4;
                z = true;
            }
        }
        if (z) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        if (isRunning()) {
            return;
        }
        synchronized (this.mLock) {
            checkDestroyedLocked();
            if (this.mState == 1) {
                return;
            }
            this.mCurrentLoop = 0;
            scheduleDecodeLocked(false);
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        if (isRunning()) {
            sMainThreadHandler.removeCallbacks(this.mScheduleSelfRunnable);
            unscheduleSelf(this);
        }
    }

    public void pause() {
        stop();
    }

    public void resume() {
        if (isRunning()) {
            return;
        }
        synchronized (this.mLock) {
            checkDestroyedLocked();
            if (this.mState == 1) {
                return;
            }
            scheduleDecodeLocked(true);
        }
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mNextFrameToDecode > -1 && !this.mDestroyed;
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    public void unscheduleSelf(Runnable runnable) {
        synchronized (this.mLock) {
            this.mLastNextFrameToDecode = this.mNextFrameToDecode;
            this.mNextFrameToDecode = -1;
            this.mState = 0;
        }
        super.unscheduleSelf(runnable);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (!z) {
            stop();
        } else if (z2 || visible) {
            stop();
            start();
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mFrameSequence.getWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mFrameSequence.getHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.mFrameSequence.isOpaque() ? -1 : -2;
    }

    public Bitmap getFrontBitmap() {
        Bitmap createBitmap;
        synchronized (this.mLock) {
            Bitmap bitmap = this.mFrontBitmap;
            createBitmap = (bitmap == null || bitmap.isRecycled()) ? null : Bitmap.createBitmap(this.mFrontBitmap);
        }
        return createBitmap;
    }
}
