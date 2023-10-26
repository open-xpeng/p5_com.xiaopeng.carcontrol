package com.xiaopeng.xui.drawable;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.theme.XThemeManager;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public final class XIndicatorDrawable extends Drawable {
    private static final int INDICATOR_TYPE_LINE = 1;
    private static final int INDICATOR_TYPE_RECT = 0;
    private static final String TAG = "XIndicatorDrawable";
    private float mAnimIndicatorEnd;
    private float mAnimIndicatorStart;
    private BlurMaskFilter mBlurMaskFilter;
    private ColorStateList mColorStateList;
    private int mDefaultColor;
    private int mIndicatorColorRes;
    private int mIndicatorCount;
    private float mIndicatorEnd;
    private float mIndicatorEndAnimSpeed;
    private float mIndicatorEndDistance;
    private float mIndicatorHeight;
    private float mIndicatorPaddingBottom;
    private float mIndicatorPercent;
    private float mIndicatorRadius;
    private final RectF mIndicatorRect;
    private float mIndicatorStart;
    private float mIndicatorStartAnimSpeed;
    private float mIndicatorStartDistance;
    private OnAnimationListener mOnAnimationListener;
    private final Paint mPaint;
    private final ValueAnimator mValueAnimator;
    private int mIndicatorType = 0;
    private int mCurrentSelection = -1;
    private boolean mEnable = true;

    /* loaded from: classes2.dex */
    public interface OnAnimationListener {
        void onEnd();

        void onStart();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public XIndicatorDrawable() {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mIndicatorRect = new RectF();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mValueAnimator = ofFloat;
        ofFloat.setDuration(500L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.xui.drawable.-$$Lambda$XIndicatorDrawable$ZR1VuKg9dXX-xNZ2p1ibIatjAjo
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                XIndicatorDrawable.this.lambda$new$0$XIndicatorDrawable(valueAnimator);
            }
        });
        ofFloat.setInterpolator(new AccelerateDecelerateInterpolator());
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.xiaopeng.xui.drawable.XIndicatorDrawable.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                if (XIndicatorDrawable.this.mOnAnimationListener != null) {
                    XIndicatorDrawable.this.mOnAnimationListener.onStart();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (XIndicatorDrawable.this.mOnAnimationListener != null) {
                    XIndicatorDrawable.this.mOnAnimationListener.onEnd();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                XIndicatorDrawable xIndicatorDrawable = XIndicatorDrawable.this;
                xIndicatorDrawable.mAnimIndicatorStart = xIndicatorDrawable.mIndicatorStart;
                XIndicatorDrawable xIndicatorDrawable2 = XIndicatorDrawable.this;
                xIndicatorDrawable2.mAnimIndicatorEnd = xIndicatorDrawable2.mIndicatorEnd;
            }
        });
    }

    public /* synthetic */ void lambda$new$0$XIndicatorDrawable(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        float f = this.mIndicatorStartAnimSpeed * floatValue;
        if (f > 1.0f) {
            f = 1.0f;
        }
        this.mIndicatorStart = this.mAnimIndicatorStart - (this.mIndicatorStartDistance * (1.0f - f));
        float f2 = floatValue * this.mIndicatorEndAnimSpeed;
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        this.mIndicatorEnd = this.mAnimIndicatorEnd - (this.mIndicatorEndDistance * (1.0f - f2));
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        if (this.mIndicatorType == 1) {
            this.mIndicatorRect.bottom = rect.height() - this.mIndicatorPaddingBottom;
            RectF rectF = this.mIndicatorRect;
            rectF.top = rectF.bottom - this.mIndicatorHeight;
        } else {
            this.mIndicatorRect.top = 0.0f;
            this.mIndicatorRect.bottom = rect.height();
        }
        this.mIndicatorRadius = this.mIndicatorRect.height() / 2.0f;
        setSelection(this.mIndicatorCount, this.mCurrentSelection, false);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (!isVisible() || this.mCurrentSelection == -1) {
            return;
        }
        this.mIndicatorRect.left = this.mIndicatorStart;
        this.mIndicatorRect.right = this.mIndicatorEnd;
        if (this.mIndicatorType == 1) {
            this.mPaint.setMaskFilter(this.mBlurMaskFilter);
        } else {
            this.mPaint.setMaskFilter(null);
        }
        RectF rectF = this.mIndicatorRect;
        float f = this.mIndicatorRadius;
        canvas.drawRoundRect(rectF, f, f, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        super.onStateChange(iArr);
        setPaintColor();
        return true;
    }

    private void setPaintColor() {
        ColorStateList colorStateList;
        Paint paint = this.mPaint;
        if (paint == null || (colorStateList = this.mColorStateList) == null) {
            return;
        }
        if (this.mEnable) {
            paint.setColor(colorStateList.getColorForState(getState(), this.mDefaultColor));
        } else {
            paint.setColor(colorStateList.getColorForState(StateSet.WILD_CARD, this.mDefaultColor));
        }
    }

    public void setEnable(boolean z) {
        if (z != this.mEnable) {
            this.mEnable = z;
            setPaintColor();
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws IOException, XmlPullParserException {
        inflateAttrs(resources, attributeSet, null);
        super.inflate(resources, xmlPullParser, attributeSet);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws IOException, XmlPullParserException {
        inflateAttrs(resources, attributeSet, theme);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
    }

    public void setSelection(int i, int i2, boolean z) {
        this.mIndicatorCount = i;
        this.mCurrentSelection = i2;
        Rect bounds = getBounds();
        if (this.mIndicatorCount <= this.mCurrentSelection || bounds.width() <= 0) {
            return;
        }
        if (this.mValueAnimator.isRunning()) {
            this.mValueAnimator.cancel();
        }
        float width = bounds.width();
        if (this.mIndicatorCount != 0) {
            width = bounds.width() / this.mIndicatorCount;
        }
        float f = this.mCurrentSelection * width;
        this.mAnimIndicatorStart = f;
        float f2 = f + width;
        this.mAnimIndicatorEnd = f2;
        if (this.mIndicatorType == 1) {
            float f3 = (width * (1.0f - this.mIndicatorPercent)) / 2.0f;
            this.mAnimIndicatorStart = f + f3;
            this.mAnimIndicatorEnd = f2 - f3;
        }
        float f4 = this.mIndicatorStart;
        float f5 = this.mAnimIndicatorStart;
        if (f4 == f5 && this.mIndicatorEnd == this.mAnimIndicatorEnd) {
            return;
        }
        if (z) {
            startAnimation();
            return;
        }
        this.mIndicatorStart = f5;
        this.mIndicatorEnd = this.mAnimIndicatorEnd;
        invalidateSelf();
    }

    private void startAnimation() {
        float f = this.mAnimIndicatorStart;
        float f2 = this.mIndicatorStart;
        if (f <= f2 && this.mAnimIndicatorEnd <= this.mIndicatorEnd) {
            this.mIndicatorStartAnimSpeed = 2.0f;
            this.mIndicatorEndAnimSpeed = 1.0f;
        } else if (f >= f2 && this.mAnimIndicatorEnd >= this.mIndicatorEnd) {
            this.mIndicatorStartAnimSpeed = 1.0f;
            this.mIndicatorEndAnimSpeed = 2.0f;
        } else {
            this.mIndicatorStartAnimSpeed = 1.0f;
            this.mIndicatorEndAnimSpeed = 1.0f;
        }
        this.mIndicatorStartDistance = f - f2;
        this.mIndicatorEndDistance = this.mAnimIndicatorEnd - this.mIndicatorEnd;
        this.mValueAnimator.start();
    }

    public void inflateAttrs(Resources resources, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray obtainAttributes;
        if (resources == null || attributeSet == null) {
            return;
        }
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.XSegmented, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.XSegmented);
        }
        this.mIndicatorColorRes = obtainAttributes.getResourceId(R.styleable.XSegmented_segment_indicator_color, R.color.x_segment_indicator_color);
        this.mIndicatorType = obtainAttributes.getInt(R.styleable.XSegmented_segment_indicator_type, 0);
        onConfigurationChanged(resources, theme);
        if (this.mIndicatorType == 1) {
            this.mIndicatorPercent = obtainAttributes.getFloat(R.styleable.XSegmented_segment_line_width_percent, 1.0f);
            this.mIndicatorHeight = dp(resources, 4);
            this.mIndicatorPaddingBottom = obtainAttributes.getDimension(R.styleable.XSegmented_segment_line_padding_bottom, dp(resources, 6));
            if (this.mIndicatorPercent > 1.0f) {
                this.mIndicatorPercent = 1.0f;
            }
        }
        obtainAttributes.recycle();
    }

    public void onConfigurationChanged(Resources resources, Resources.Theme theme) {
        this.mColorStateList = resources.getColorStateList(this.mIndicatorColorRes, theme);
        this.mDefaultColor = resources.getColor(R.color.x_segment_indicator_color, theme);
        setPaintColor();
        if (XThemeManager.isNight(resources.getConfiguration()) && this.mIndicatorType == 1) {
            this.mBlurMaskFilter = new BlurMaskFilter(dp(resources, 4), BlurMaskFilter.Blur.SOLID);
        } else {
            this.mBlurMaskFilter = null;
        }
        invalidateSelf();
    }

    private float dp(Resources resources, int i) {
        return TypedValue.applyDimension(1, i, resources.getDisplayMetrics());
    }

    public void setOnAnimationListener(OnAnimationListener onAnimationListener) {
        this.mOnAnimationListener = onAnimationListener;
    }
}
