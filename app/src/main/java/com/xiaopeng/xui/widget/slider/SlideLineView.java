package com.xiaopeng.xui.widget.slider;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.view.XView;

/* loaded from: classes2.dex */
class SlideLineView extends XView {
    private static final float BG_DOC_RADIUS = 2.0f;
    private static final long DURATION = 800;
    public static final int LINE_WIDTH = 22;
    private static final float halfLineHeight = 5.0f;
    private static final float halfLineWidth = 3.2258065f;
    private static final float slope = 1.55f;
    private ValueAnimator animator;
    int bgBallColorSelect;
    int bgBallColorUnSelect;
    int bgLineColorSelect;
    int bgLineColorUnSelect;
    private Paint bgLinePaint;
    private Paint blurPaint;
    private final int desireHeight;
    private final int desireWidth;
    private boolean isNight;
    private boolean isSelect;
    private final int lineStrokeWidth;
    private float mHalfHeight;
    private float mHalfWidth;
    private float progress;

    public SlideLineView(Context context) {
        this(context, null);
    }

    public SlideLineView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlideLineView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SlideLineView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.desireWidth = 22;
        this.desireHeight = 40;
        this.lineStrokeWidth = 4;
        this.bgBallColorUnSelect = 671088640;
        this.bgBallColorSelect = -1;
        this.bgLineColorUnSelect = 671088640;
        this.bgLineColorSelect = -15301639;
        this.isNight = XThemeManager.isNight(getContext());
        this.progress = 1.0f;
        initView(attributeSet, i2);
    }

    public SlideLineView(Context context, boolean z, int i) {
        this(context, null, 0, i);
        this.isSelect = z;
    }

    private void readStyleAttrs(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes;
        if (attributeSet != null) {
            obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SlideLineView, 0, i);
        } else {
            obtainStyledAttributes = context.obtainStyledAttributes(i, R.styleable.SlideLineView);
        }
        this.bgLineColorUnSelect = obtainStyledAttributes.getColor(R.styleable.SlideLineView_slider_line_un_select, this.bgLineColorUnSelect);
        this.bgLineColorSelect = obtainStyledAttributes.getColor(R.styleable.SlideLineView_slider_line_select, this.bgLineColorSelect);
        obtainStyledAttributes.recycle();
    }

    private void applyStyle() {
        if (this.bgLinePaint == null) {
            Paint paint = new Paint(1);
            this.bgLinePaint = paint;
            paint.setStyle(Paint.Style.FILL);
            this.bgLinePaint.setStrokeCap(Paint.Cap.ROUND);
            this.bgLinePaint.setStrokeWidth(4.0f);
        }
        this.bgLinePaint.setColor(this.bgLineColorSelect);
        if (this.blurPaint == null) {
            Paint paint2 = new Paint(1);
            this.blurPaint = paint2;
            paint2.setStyle(Paint.Style.FILL);
            this.blurPaint.setStrokeCap(Paint.Cap.ROUND);
            this.blurPaint.setStrokeWidth(4.0f);
            this.blurPaint.setColor(4);
        }
        if (this.animator == null) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, BG_DOC_RADIUS, 1.0f);
            this.animator = ofFloat;
            ofFloat.setDuration(DURATION);
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.xui.widget.slider.SlideLineView.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SlideLineView.this.progress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    SlideLineView.this.invalidate();
                }
            });
            this.animator.setInterpolator(new DecelerateInterpolator());
            this.animator.addListener(new Animator.AnimatorListener() { // from class: com.xiaopeng.xui.widget.slider.SlideLineView.2
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    SlideLineView.this.bgLinePaint.setStrokeWidth(4.0f);
                    SlideLineView.this.blurPaint.setMaskFilter(null);
                }
            });
        }
        setEnabled(true);
        invalidate();
    }

    public void setStyle(int i) {
        setStyle(null, i);
    }

    private void setStyle(AttributeSet attributeSet, int i) {
        readStyleAttrs(getContext(), attributeSet, i);
        applyStyle();
    }

    private void initView(AttributeSet attributeSet, int i) {
        setLayerType(1, null);
        setStyle(attributeSet, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.view.XView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.isNight = XThemeManager.isNight(getContext());
        postDelayed(new Runnable() { // from class: com.xiaopeng.xui.widget.slider.SlideLineView.3
            @Override // java.lang.Runnable
            public void run() {
                SlideLineView.this.invalidate();
            }
        }, 500L);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mHalfWidth = getWidth() / BG_DOC_RADIUS;
        this.mHalfHeight = getHeight() / BG_DOC_RADIUS;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isSelect) {
            if (this.isNight) {
                this.bgLinePaint.setColor(this.bgLineColorSelect);
                this.blurPaint.setColor(this.bgLineColorSelect);
                float f = this.mHalfWidth;
                float f2 = this.progress;
                float f3 = f - (f2 * halfLineWidth);
                float f4 = f + (halfLineWidth * f2);
                float f5 = this.mHalfHeight;
                float f6 = f5 + (f2 * halfLineHeight);
                float f7 = f5 - (f2 * halfLineHeight);
                canvas.drawLine(f3, f6, f4, f7, this.bgLinePaint);
                canvas.drawLine(f3, f6, f4, f7, this.blurPaint);
                return;
            }
            this.bgLinePaint.setColor(this.bgBallColorSelect);
            this.blurPaint.setColor(this.bgBallColorSelect);
            canvas.drawCircle(this.mHalfWidth, this.mHalfHeight, this.progress * BG_DOC_RADIUS, this.bgLinePaint);
            canvas.drawCircle(this.mHalfWidth, this.mHalfHeight, this.progress * BG_DOC_RADIUS, this.blurPaint);
        } else if (this.isNight) {
            this.bgLinePaint.setColor(this.bgLineColorUnSelect);
            float f8 = this.mHalfWidth;
            float f9 = f8 - halfLineWidth;
            float f10 = this.mHalfHeight;
            canvas.drawLine(f9, f10 + halfLineHeight, f8 + halfLineWidth, f10 - halfLineHeight, this.bgLinePaint);
        } else {
            this.bgLinePaint.setColor(this.bgBallColorUnSelect);
            canvas.drawCircle(this.mHalfWidth, this.mHalfHeight, BG_DOC_RADIUS, this.bgLinePaint);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(22, 40);
    }

    public void setSelect(boolean z) {
        this.isSelect = z;
        this.bgLinePaint.setStrokeWidth(z ? BG_DOC_RADIUS : 4.0f);
        if (z) {
            if (this.isNight) {
                this.blurPaint.setColor(this.bgLineColorSelect);
            } else {
                this.blurPaint.setColor(this.bgBallColorSelect);
            }
            this.blurPaint.setMaskFilter(new BlurMaskFilter(4.0f, BlurMaskFilter.Blur.NORMAL));
        }
        if (!z) {
            this.animator.cancel();
        } else {
            this.animator.start();
        }
        invalidate();
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        setAlphaByEnable(z);
        invalidate();
    }

    private void setAlphaByEnable(boolean z) {
        this.bgLineColorUnSelect = z ? 671088640 : 503316480;
        this.bgLineColorSelect = z ? this.bgLineColorSelect | (-1291845632) : this.bgLineColorSelect & 1291845631;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.view.XView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) {
            return;
        }
        this.isNight = XThemeManager.isNight(getContext());
    }
}
