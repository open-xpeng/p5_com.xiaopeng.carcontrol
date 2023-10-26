package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.xui.theme.XThemeManager;

/* loaded from: classes2.dex */
public class SunShadeCtrlView extends View {
    private static final int DEFAULT_POS = 100;
    private static final int DEFAULT_SHADE_TOP_X = ResUtils.dp2px(130);
    private static final int DEFAULT_SHADE_TOP_Y = ResUtils.dp2px(252);
    private static final String TAG = "SunShadeCtrlView";
    private boolean isControl;
    private boolean isShowGesture;
    private Bitmap mBg;
    private final BitmapFactory.Options mBgOptions;
    private Bitmap mCover;
    private Bitmap mDashLine;
    private Bitmap mGesture;
    private long mLastDragTime;
    private int mLastY;
    private onTargetPosChangeListener mListener;
    private final Paint mPaint;
    private int mPos;
    private Bitmap mSunShade;
    private int mTPos;
    private final Xfermode mXfermode;

    /* loaded from: classes2.dex */
    public interface onTargetPosChangeListener {
        void onTargetPosChanged(int tPos);
    }

    public SunShadeCtrlView(Context context) {
        this(context, null);
    }

    public SunShadeCtrlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunShadeCtrlView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SunShadeCtrlView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        BitmapFactory.Options options = new BitmapFactory.Options();
        this.mBgOptions = options;
        this.mPos = 100;
        this.mTPos = 100;
        this.isShowGesture = true;
        this.isControl = false;
        this.mPaint = new Paint(1);
        this.mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.bg_sun_shade, options);
    }

    private void initBitmap() {
        this.mBg = BitmapFactory.decodeResource(getResources(), R.drawable.bg_sun_shade);
        this.mCover = BitmapFactory.decodeResource(getResources(), R.drawable.img_sun_shade_cover);
        this.mSunShade = BitmapFactory.decodeResource(getResources(), R.drawable.img_sun_shade);
        this.mDashLine = BitmapFactory.decodeResource(getResources(), R.drawable.img_sun_shade_dash_line);
        this.mGesture = BitmapFactory.decodeResource(getResources(), R.drawable.window_gesture);
    }

    private void resetAllBitmaps() {
        recycleBitmaps(this.mBg, this.mCover, this.mSunShade, this.mDashLine, this.mGesture);
        this.mBg = null;
        this.mCover = null;
        this.mSunShade = null;
        this.mDashLine = null;
        this.mGesture = null;
    }

    private void recycleBitmaps(Bitmap... bitmaps) {
        for (Bitmap bitmap : bitmaps) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    private boolean isBitmapsRecycled(Bitmap... bitmaps) {
        for (Bitmap bitmap : bitmaps) {
            if (bitmap == null || bitmap.isRecycled()) {
                LogUtils.d(TAG, "bitmap is recycled");
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initBitmap();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isShowGesture = true;
        resetAllBitmaps();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (XThemeManager.isThemeChanged(newConfig)) {
            resetAllBitmaps();
            initBitmap();
            invalidate();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        int action = event.getAction();
        if (action != 0) {
            if (action == 1) {
                this.isControl = false;
                if (System.currentTimeMillis() - this.mLastDragTime >= 1000) {
                    this.mLastDragTime = System.currentTimeMillis();
                }
                onTargetPosChangeListener ontargetposchangelistener = this.mListener;
                if (ontargetposchangelistener != null) {
                    ontargetposchangelistener.onTargetPosChanged(100 - this.mTPos);
                }
                this.mTPos = this.mPos;
                this.isShowGesture = false;
                invalidate();
            } else if (action == 2) {
                int height = ((y - this.mLastY) * 100) / this.mSunShade.getHeight();
                this.isShowGesture = false;
                setTPos(Math.round((this.mPos - height) / 5.0f) * 5);
            } else if (action == 3) {
                this.isControl = false;
                this.mTPos = this.mPos;
                this.isShowGesture = false;
                invalidate();
            }
        } else if (System.currentTimeMillis() - this.mLastDragTime < 1000) {
            Log.d(TAG, "drag too fast!!!");
            return super.onTouchEvent(event);
        } else {
            this.isControl = true;
            this.mLastY = y;
        }
        return true;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (isBitmapsRecycled(this.mBg, this.mCover, this.mSunShade, this.mDashLine, this.mGesture)) {
            initBitmap();
        }
        canvas.drawBitmap(this.mBg, 0.0f, 0.0f, this.mPaint);
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), this.mPaint);
        Bitmap bitmap = this.mCover;
        int i = DEFAULT_SHADE_TOP_X;
        int i2 = DEFAULT_SHADE_TOP_Y;
        canvas.drawBitmap(bitmap, i, i2, this.mPaint);
        this.mPaint.setXfermode(this.mXfermode);
        canvas.drawBitmap(this.mSunShade, i, ((this.mSunShade.getHeight() * (100 - this.mPos)) / 100) + i2, this.mPaint);
        if (this.isControl) {
            canvas.drawBitmap(this.mDashLine, i, i2 + ((this.mSunShade.getHeight() * (100 - this.mTPos)) / 100), this.mPaint);
        }
        this.mPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
        if (this.isShowGesture) {
            canvas.drawBitmap(this.mGesture, (getWidth() - this.mGesture.getWidth()) / 2.0f, (getHeight() - this.mGesture.getHeight()) / 2.0f, this.mPaint);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        int i = this.mBgOptions.outWidth;
        int i2 = this.mBgOptions.outHeight;
        if (getLayoutParams().width == -2 && getLayoutParams().height == -2) {
            setMeasuredDimension(i, i2);
        } else if (getLayoutParams().width == -2) {
            setMeasuredDimension(i, size2);
        } else if (getLayoutParams().height == -2) {
            setMeasuredDimension(size, i2);
        }
    }

    public void setTPosChangeListener(onTargetPosChangeListener listener) {
        this.mListener = listener;
    }

    public void setPosition(int pos) {
        int i = 100 - pos;
        if (i > 100 || i < 0 || i == this.mPos) {
            LogUtils.d(TAG, "illegal pos = " + i);
            return;
        }
        this.mPos = i;
        this.mTPos = i;
        invalidate();
    }

    private void setTPos(int tPos) {
        if (tPos > 100) {
            tPos = 100;
        } else if (tPos < 0) {
            tPos = 0;
        }
        if (tPos == this.mTPos) {
            return;
        }
        this.mTPos = tPos;
        invalidate();
    }
}
