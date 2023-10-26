package com.xiaopeng.xui.drawable.ripple;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.utils.XLogUtils;

/* loaded from: classes2.dex */
public class XRipple {
    private static final long ANIMATION_TIME = 400;
    private static final String TAG = "XRipple";
    private AnimTransformListener mAnimTransformListener;
    private ValueAnimator mAnimationPress;
    private ValueAnimator mAnimationUp;
    private int mBackgroundColor;
    private Drawable.Callback mCallback;
    private float mClearDistance;
    private float mCurrentDistance;
    private float mDownX;
    private float mDownY;
    private boolean mIsAnimating;
    private boolean mIsPressAnimating;
    private boolean mIsTouched;
    private boolean mIsUpAnimating;
    private float mMaxPressRadius;
    private boolean mNeedUpAnim;
    private Paint mPaint;
    private float mPressRadius;
    private int mRippleAlpha;
    private int mRippleColor;
    private Path mRipplePath;
    private float mRippleRadius;
    private RectF mRippleRectF;
    private boolean mSupportScale;
    private View mView;

    /* loaded from: classes2.dex */
    public interface AnimTransformListener {
        void onDownTransformation(float f, Transformation transformation);

        void onUpTransformation(float f, Transformation transformation);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public XRipple() {
        init();
    }

    public XRipple(Drawable.Callback callback) {
        this.mCallback = callback;
        getView(callback);
        init();
    }

    private void init() {
        this.mRipplePath = new Path();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        initAnimation();
    }

    @Deprecated
    public void setView(Drawable.Callback callback) {
        this.mCallback = callback;
        getView(callback);
    }

    public void setCallback(Drawable.Callback callback) {
        this.mCallback = callback;
        getView(callback);
    }

    private void getView(Drawable.Callback callback) {
        if (callback != null) {
            if (callback instanceof Drawable) {
                getView(((Drawable) callback).getCallback());
                return;
            } else if (callback instanceof View) {
                this.mView = (View) callback;
                return;
            } else {
                XLogUtils.d(TAG, hashCode() + ",callback is " + callback);
                return;
            }
        }
        XLogUtils.d(TAG, hashCode() + ",callback is null");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setRippleColor(int i) {
        this.mRippleColor = i;
        this.mPaint.setColor(i);
        this.mRippleAlpha = this.mPaint.getAlpha();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setRippleBackgroundColor(int i) {
        this.mBackgroundColor = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setRippleRect(RectF rectF) {
        this.mRippleRectF = rectF;
        if (rectF.width() > this.mRippleRectF.height()) {
            this.mClearDistance = this.mRippleRectF.height() / 2.0f;
        } else {
            this.mClearDistance = this.mRippleRectF.width() / 2.0f;
        }
        resetPath();
    }

    public void setSupportScale(boolean z) {
        this.mSupportScale = false;
    }

    public void setRippleRadius(float f) {
        this.mRippleRadius = f;
        resetPath();
    }

    private void resetPath() {
        if (this.mRippleRectF != null) {
            this.mRipplePath.reset();
            Path path = this.mRipplePath;
            RectF rectF = this.mRippleRectF;
            float f = this.mRippleRadius;
            path.addRoundRect(rectF, f, f, Path.Direction.CW);
        }
    }

    private void initAnimation() {
        AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
        ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(ANIMATION_TIME);
        this.mAnimationPress = duration;
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.xui.drawable.ripple.XRipple.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                XRipple xRipple = XRipple.this;
                xRipple.mPressRadius = xRipple.mMaxPressRadius * floatValue;
                if (XRipple.this.mAnimTransformListener != null) {
                    XRipple.this.mAnimTransformListener.onDownTransformation(floatValue, null);
                }
                if (XRipple.this.mView != null && XRipple.this.mSupportScale) {
                    float f = 1.0f - (floatValue * 0.1f);
                    XRipple.this.mView.setScaleX(f);
                    XRipple.this.mView.setScaleY(f);
                }
                XRipple.this.invalidateSelf();
            }
        });
        this.mAnimationPress.addListener(new Animator.AnimatorListener() { // from class: com.xiaopeng.xui.drawable.ripple.XRipple.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                XRipple.this.mPaint.setAlpha(XRipple.this.mRippleAlpha);
                XRipple.this.mIsPressAnimating = true;
                XRipple.this.mIsAnimating = true;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (XRipple.this.mIsPressAnimating) {
                    XRipple.this.mIsPressAnimating = false;
                    if (XRipple.this.mNeedUpAnim) {
                        XRipple.this.mNeedUpAnim = false;
                        XRipple.this.mAnimationUp.start();
                        XRipple.this.mIsUpAnimating = true;
                    }
                }
            }
        });
        this.mAnimationPress.setInterpolator(accelerateDecelerateInterpolator);
        this.mAnimationPress.setDuration(ANIMATION_TIME);
        ValueAnimator duration2 = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(ANIMATION_TIME);
        this.mAnimationUp = duration2;
        duration2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.xui.drawable.ripple.XRipple.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                XRipple xRipple = XRipple.this;
                float f = 1.0f - floatValue;
                xRipple.mCurrentDistance = xRipple.mClearDistance * f;
                if (XRipple.this.mAnimTransformListener != null) {
                    XRipple.this.mAnimTransformListener.onUpTransformation(floatValue, null);
                }
                XRipple.this.mPaint.setAlpha((int) (XRipple.this.mRippleAlpha * f));
                if (XRipple.this.mView != null && XRipple.this.mSupportScale) {
                    float f2 = (floatValue * 0.1f) + 0.9f;
                    XRipple.this.mView.setScaleX(f2);
                    XRipple.this.mView.setScaleY(f2);
                }
                XRipple.this.invalidateSelf();
            }
        });
        this.mAnimationUp.addListener(new Animator.AnimatorListener() { // from class: com.xiaopeng.xui.drawable.ripple.XRipple.4
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                XRipple.this.mIsUpAnimating = true;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                XRipple.this.mPaint.setAlpha(XRipple.this.mRippleAlpha);
                XRipple.this.mIsUpAnimating = false;
                XRipple.this.mIsAnimating = false;
            }
        });
        this.mAnimationUp.setInterpolator(accelerateDecelerateInterpolator);
        this.mAnimationUp.setDuration(ANIMATION_TIME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateSelf() {
        Drawable.Callback callback = this.mCallback;
        if (callback != null) {
            if (callback instanceof View) {
                ((View) callback).invalidate();
                return;
            } else if (callback instanceof Drawable) {
                ((Drawable) callback).invalidateSelf();
                return;
            } else {
                return;
            }
        }
        XLogUtils.d(TAG, "Callback is null");
    }

    public void setPressInterpolator(Interpolator interpolator) {
        this.mAnimationPress.setInterpolator(interpolator);
    }

    public void setUpInterpolator(Interpolator interpolator) {
        this.mAnimationUp.setInterpolator(interpolator);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setPressDuration(long j) {
        this.mAnimationPress.setDuration(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setUpDuration(long j) {
        this.mAnimationUp.setDuration(j);
    }

    public void drawRipple(Canvas canvas) {
        if (isVisible()) {
            if (this.mRippleRectF == null) {
                setRippleRect(new RectF(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight()));
            }
            if (this.mIsPressAnimating && this.mIsAnimating) {
                drawRippleBackground(canvas);
                int saveLayer = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null);
                canvas.clipPath(this.mRipplePath);
                canvas.drawCircle(this.mDownX, this.mDownY, this.mPressRadius, this.mPaint);
                canvas.restoreToCount(saveLayer);
            } else if (this.mIsUpAnimating && this.mIsAnimating) {
                drawRippleBackground(canvas);
                int saveLayer2 = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null);
                RectF rectF = this.mRippleRectF;
                float f = this.mRippleRadius;
                canvas.drawRoundRect(rectF, f, f, this.mPaint);
                this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                float f2 = this.mCurrentDistance;
                float f3 = (1.0f - (f2 / this.mClearDistance)) * this.mRippleRadius;
                canvas.drawRoundRect(f2 + this.mRippleRectF.left, this.mCurrentDistance + this.mRippleRectF.top, (this.mRippleRectF.width() - this.mCurrentDistance) + this.mRippleRectF.left, (this.mRippleRectF.height() - this.mCurrentDistance) + this.mRippleRectF.top, f3, f3, this.mPaint);
                this.mPaint.setXfermode(null);
                canvas.restoreToCount(saveLayer2);
            } else if (this.mIsTouched && this.mIsAnimating) {
                drawRippleBackground(canvas);
                RectF rectF2 = this.mRippleRectF;
                float f4 = this.mRippleRadius;
                canvas.drawRoundRect(rectF2, f4, f4, this.mPaint);
            }
        }
    }

    private void drawRippleBackground(Canvas canvas) {
        View view;
        if (this.mRippleColor == 0 && (view = this.mView) != null) {
            this.mRippleColor = view.getContext().getColor(R.color.x_ripple_default_color);
        }
        int i = this.mBackgroundColor;
        if (i != 0) {
            this.mPaint.setColor(i);
            RectF rectF = this.mRippleRectF;
            float f = this.mRippleRadius;
            canvas.drawRoundRect(rectF, f, f, this.mPaint);
        }
        int i2 = this.mRippleColor;
        if (i2 != 0) {
            this.mPaint.setColor(i2);
        }
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            pressDown(motionEvent.getX(), motionEvent.getY());
        } else if (action == 1 || action == 3) {
            pressUp();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void pressDown(float f, float f2) {
        RectF rectF;
        if (!isVisible() || (rectF = this.mRippleRectF) == null) {
            return;
        }
        this.mDownX = f;
        this.mDownY = f2;
        this.mNeedUpAnim = false;
        this.mIsAnimating = false;
        float f3 = f - rectF.left;
        float f4 = this.mDownY - this.mRippleRectF.top;
        if (f3 < this.mRippleRectF.width() / 2.0f) {
            f3 = this.mRippleRectF.width() - f3;
        }
        if (f4 < this.mRippleRectF.height() / 2.0f) {
            f4 = this.mRippleRectF.height() - f4;
        }
        this.mMaxPressRadius = (float) Math.sqrt((f3 * f3) + (f4 * f4));
        if (this.mIsUpAnimating) {
            this.mIsUpAnimating = false;
            this.mAnimationUp.cancel();
        }
        this.mIsPressAnimating = true;
        if (this.mAnimationPress.isRunning()) {
            this.mAnimationPress.cancel();
        }
        this.mAnimationPress.start();
        this.mIsTouched = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void pressUp() {
        if (isVisible()) {
            this.mIsTouched = false;
            if (this.mIsPressAnimating) {
                this.mNeedUpAnim = true;
                return;
            }
            this.mAnimationUp.start();
            this.mIsUpAnimating = true;
        }
    }

    private boolean isVisible() {
        Drawable.Callback callback = this.mCallback;
        if (callback != null) {
            if (callback instanceof View) {
                return ((View) callback).getVisibility() == 0;
            } else if (callback instanceof Drawable) {
                return ((Drawable) callback).isVisible();
            }
        }
        return true;
    }

    public void setVisible(boolean z) {
        if (!z) {
            this.mAnimationPress.cancel();
            this.mAnimationUp.cancel();
            this.mIsPressAnimating = false;
            this.mIsUpAnimating = false;
            this.mIsAnimating = false;
            this.mPressRadius = 0.0f;
            this.mCurrentDistance = 0.0f;
            return;
        }
        View view = this.mView;
        if (view == null || !this.mSupportScale) {
            return;
        }
        view.setScaleX(1.0f);
        this.mView.setScaleY(1.0f);
    }

    public void setAnimTransformListener(AnimTransformListener animTransformListener) {
        this.mAnimTransformListener = animTransformListener;
    }
}
