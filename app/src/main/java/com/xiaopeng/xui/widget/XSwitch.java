package com.xiaopeng.xui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.sound.XSoundEffectManager;
import com.xiaopeng.xui.theme.XThemeManager;

/* loaded from: classes2.dex */
public class XSwitch extends XCompoundButton implements ValueAnimator.AnimatorUpdateListener {
    private static final int ANIMATION_ALPHA_IN = 200;
    private static final int ANIMATION_ALPHA_OUT = 200;
    private static final int ANIMATION_TRANSLATE = 300;
    private static final int ANIMATION_VERTICAL_OFFSET = 120;
    private static final int ANIMATOR_DURATION = 700;
    private static final float BLUR_ALPHA_DISABLE = 0.36f;
    private static final float BLUR_ALPHA_ENABLE = 1.0f;
    private static final String TAG = "XSwitch";
    private float BG_BOTTOM;
    private final float BG_RADIUS;
    private float BG_RIGHT;
    private float CENTER_Y;
    private final float CIRCLE_OUT_DAY_SHADOW_RADIUS;
    private final float CIRCLE_OUT_NIGHT_ON_SHADOW_RADIUS;
    private final float DIMEN_2;
    private float INNER_BOTTOM;
    private final float INNER_CIRCLE_RADIUS;
    private float INNER_CIRCLE_RIGHT;
    private float INNER_LEFT;
    private float INNER_TOP;
    private final float MOVE_X;
    private float OUT_CIRCLE_BOTTOM;
    private float OUT_CIRCLE_TOP;
    private float OUT_STROKE_BOTTOM;
    private float OUT_STROKE_LEFT;
    private final float OUT_STROKE_RADIUS;
    private float OUT_STROKE_RIGHT;
    private float OUT_STROKE_TOP;
    private float PADDING_LEFT;
    private float PADDING_TOP;
    private final float SHADOW_RADIUS;
    private final float VER_HEIGHT_RIGHT;
    private float VER_LEFT;
    private final float VER_RADIUS;
    private float VER_RIGHT;
    private float VER_TOP_RIGHT;
    private final float VER_WIDTH_LEFT;
    private final float VER_WIDTH_RIGHT;
    private boolean isDetachedFromWindow;
    private boolean isDetachedNightTheme;
    private ArgbEvaluator mArgbEvaluator;
    private Shader mBackgroundShader;
    private int mBgDisableOffColorEnd;
    private int mBgDisableOffColorStart;
    private int mBgDisableOnColorEnd;
    private int mBgDisableOnColorStart;
    private int mBgEnableOffNormalColorEnd;
    private int mBgEnableOffNormalColorStart;
    private int mBgEnableOffPressedColorEnd;
    private int mBgEnableOffPressedColorStart;
    private int mBgEnableOnNormalColorEnd;
    private int mBgEnableOnNormalColorStart;
    private int mBgEnableOnPressedColorEnd;
    private int mBgEnableOnPressedColorStart;
    private int mBgOffColorEnd;
    private int mBgOffColorStart;
    private int mBgOnColorEnd;
    private int mBgOnColorStart;
    private float mBlurAlphaDst;
    private BlurMaskFilter mBlurMaskFilter;
    private boolean mCheckSoundEnable;
    private int mCircleInnerDisableColorEnd;
    private int mCircleInnerDisableColorStart;
    private int mCircleInnerEnableColorEnd;
    private int mCircleInnerEnableColorStart;
    private Shader mCircleInnerShader;
    private int mCircleOutDisableOffColorEnd;
    private int mCircleOutDisableOffColorStart;
    private int mCircleOutDisableOnColorEnd;
    private int mCircleOutDisableOnColorStart;
    private int mCircleOutEnableOffColorEnd;
    private int mCircleOutEnableOffColorStart;
    private int mCircleOutEnableOnColorEnd;
    private int mCircleOutEnableOnColorStart;
    private Shader mCircleOutLightShader;
    private int mCircleOutOffColorEnd;
    private int mCircleOutOffColorStart;
    private float mCircleOutOffShadowOffsetY;
    private float mCircleOutOffShadowRadius;
    private int mCircleOutOnColorEnd;
    private int mCircleOutOnColorStart;
    private float mCircleOutOnShadowOffsetY;
    private float mCircleOutOnShadowRadius;
    private Shader mCircleOutShader;
    private int mCircleOutShadowColor;
    private float mCircleOutShadowOffsetY;
    private float mCircleOutShadowRadius;
    private Paint mColorPaint;
    private int mCurrentAnimValue;
    private OnInterceptListener mOnInterceptListener;
    private Paint mShaderPaint;
    private float mTranslateX;
    private ValueAnimator mValueAnimator;
    private int mVerticalColor;
    private int mVerticalDisableOffColor;
    private int mVerticalDisableOnColor;
    private int mVerticalEnableOffColor;
    private int mVerticalEnableOnColor;
    private float mVerticalHeight;
    private float mVerticalLeft;
    private int mVerticalLightColor;
    private int mVerticalOffColor;
    private int mVerticalOnColor;
    private float mVerticalShadowRadius;
    private float mVerticalTop;
    private float mVerticalWidth;
    private Xfermode mXFerModeAdd;
    private float transPercent;

    /* loaded from: classes2.dex */
    public interface OnInterceptListener {
        boolean onInterceptCheck(View view, boolean z);

        default boolean onInterceptClickEvent(View view) {
            return false;
        }
    }

    public XSwitch(Context context) {
        this(context, null);
    }

    public XSwitch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        float px = px(2);
        this.DIMEN_2 = px;
        float px2 = px(6);
        this.SHADOW_RADIUS = px2;
        this.PADDING_TOP = getResources().getDimension(R.dimen.x_switch_padding_top);
        this.PADDING_LEFT = getResources().getDimension(R.dimen.x_switch_padding_start);
        this.BG_BOTTOM = this.PADDING_TOP + px(50);
        this.BG_RADIUS = px(25);
        this.BG_RIGHT = this.PADDING_LEFT + px(90);
        this.OUT_CIRCLE_TOP = this.PADDING_TOP + px;
        float px3 = px(21);
        this.OUT_STROKE_RADIUS = px3;
        this.OUT_STROKE_TOP = this.OUT_CIRCLE_TOP + px;
        float px4 = this.PADDING_LEFT + px(4);
        this.OUT_STROKE_LEFT = px4;
        float f = this.OUT_STROKE_TOP + (px3 * 2.0f);
        this.OUT_STROKE_BOTTOM = f;
        this.OUT_CIRCLE_BOTTOM = f + px;
        this.OUT_STROKE_RIGHT = px4 + (px3 * 2.0f);
        float px5 = px(19);
        this.INNER_CIRCLE_RADIUS = px5;
        float f2 = this.OUT_STROKE_LEFT + px;
        this.INNER_LEFT = f2;
        float f3 = this.OUT_STROKE_TOP + px;
        this.INNER_TOP = f3;
        this.INNER_BOTTOM = f3 + (px5 * 2.0f);
        this.INNER_CIRCLE_RIGHT = f2 + (px5 * 2.0f);
        float f4 = this.BG_BOTTOM;
        float f5 = this.PADDING_TOP;
        this.CENTER_Y = ((f4 - f5) / 2.0f) + f5;
        this.MOVE_X = px(40);
        float px6 = this.INNER_LEFT + px(10);
        this.VER_LEFT = px6;
        this.VER_RIGHT = px6 + px(46);
        this.VER_TOP_RIGHT = this.INNER_TOP + px(8);
        this.VER_WIDTH_LEFT = px(18);
        this.VER_WIDTH_RIGHT = px(6);
        this.VER_HEIGHT_RIGHT = px(22);
        this.VER_RADIUS = px(3);
        this.CIRCLE_OUT_DAY_SHADOW_RADIUS = px(5);
        this.CIRCLE_OUT_NIGHT_ON_SHADOW_RADIUS = px2;
        this.mCheckSoundEnable = true;
        this.isDetachedFromWindow = true;
        init();
    }

    public XSwitch(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        float px = px(2);
        this.DIMEN_2 = px;
        float px2 = px(6);
        this.SHADOW_RADIUS = px2;
        this.PADDING_TOP = getResources().getDimension(R.dimen.x_switch_padding_top);
        this.PADDING_LEFT = getResources().getDimension(R.dimen.x_switch_padding_start);
        this.BG_BOTTOM = this.PADDING_TOP + px(50);
        this.BG_RADIUS = px(25);
        this.BG_RIGHT = this.PADDING_LEFT + px(90);
        this.OUT_CIRCLE_TOP = this.PADDING_TOP + px;
        float px3 = px(21);
        this.OUT_STROKE_RADIUS = px3;
        this.OUT_STROKE_TOP = this.OUT_CIRCLE_TOP + px;
        float px4 = this.PADDING_LEFT + px(4);
        this.OUT_STROKE_LEFT = px4;
        float f = this.OUT_STROKE_TOP + (px3 * 2.0f);
        this.OUT_STROKE_BOTTOM = f;
        this.OUT_CIRCLE_BOTTOM = f + px;
        this.OUT_STROKE_RIGHT = px4 + (px3 * 2.0f);
        float px5 = px(19);
        this.INNER_CIRCLE_RADIUS = px5;
        float f2 = this.OUT_STROKE_LEFT + px;
        this.INNER_LEFT = f2;
        float f3 = this.OUT_STROKE_TOP + px;
        this.INNER_TOP = f3;
        this.INNER_BOTTOM = f3 + (px5 * 2.0f);
        this.INNER_CIRCLE_RIGHT = f2 + (px5 * 2.0f);
        float f4 = this.BG_BOTTOM;
        float f5 = this.PADDING_TOP;
        this.CENTER_Y = ((f4 - f5) / 2.0f) + f5;
        this.MOVE_X = px(40);
        float px6 = this.INNER_LEFT + px(10);
        this.VER_LEFT = px6;
        this.VER_RIGHT = px6 + px(46);
        this.VER_TOP_RIGHT = this.INNER_TOP + px(8);
        this.VER_WIDTH_LEFT = px(18);
        this.VER_WIDTH_RIGHT = px(6);
        this.VER_HEIGHT_RIGHT = px(22);
        this.VER_RADIUS = px(3);
        this.CIRCLE_OUT_DAY_SHADOW_RADIUS = px(5);
        this.CIRCLE_OUT_NIGHT_ON_SHADOW_RADIUS = px2;
        this.mCheckSoundEnable = true;
        this.isDetachedFromWindow = true;
        init();
    }

    public XSwitch(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        float px = px(2);
        this.DIMEN_2 = px;
        float px2 = px(6);
        this.SHADOW_RADIUS = px2;
        this.PADDING_TOP = getResources().getDimension(R.dimen.x_switch_padding_top);
        this.PADDING_LEFT = getResources().getDimension(R.dimen.x_switch_padding_start);
        this.BG_BOTTOM = this.PADDING_TOP + px(50);
        this.BG_RADIUS = px(25);
        this.BG_RIGHT = this.PADDING_LEFT + px(90);
        this.OUT_CIRCLE_TOP = this.PADDING_TOP + px;
        float px3 = px(21);
        this.OUT_STROKE_RADIUS = px3;
        this.OUT_STROKE_TOP = this.OUT_CIRCLE_TOP + px;
        float px4 = this.PADDING_LEFT + px(4);
        this.OUT_STROKE_LEFT = px4;
        float f = this.OUT_STROKE_TOP + (px3 * 2.0f);
        this.OUT_STROKE_BOTTOM = f;
        this.OUT_CIRCLE_BOTTOM = f + px;
        this.OUT_STROKE_RIGHT = px4 + (px3 * 2.0f);
        float px5 = px(19);
        this.INNER_CIRCLE_RADIUS = px5;
        float f2 = this.OUT_STROKE_LEFT + px;
        this.INNER_LEFT = f2;
        float f3 = this.OUT_STROKE_TOP + px;
        this.INNER_TOP = f3;
        this.INNER_BOTTOM = f3 + (px5 * 2.0f);
        this.INNER_CIRCLE_RIGHT = f2 + (px5 * 2.0f);
        float f4 = this.BG_BOTTOM;
        float f5 = this.PADDING_TOP;
        this.CENTER_Y = ((f4 - f5) / 2.0f) + f5;
        this.MOVE_X = px(40);
        float px6 = this.INNER_LEFT + px(10);
        this.VER_LEFT = px6;
        this.VER_RIGHT = px6 + px(46);
        this.VER_TOP_RIGHT = this.INNER_TOP + px(8);
        this.VER_WIDTH_LEFT = px(18);
        this.VER_WIDTH_RIGHT = px(6);
        this.VER_HEIGHT_RIGHT = px(22);
        this.VER_RADIUS = px(3);
        this.CIRCLE_OUT_DAY_SHADOW_RADIUS = px(5);
        this.CIRCLE_OUT_NIGHT_ON_SHADOW_RADIUS = px2;
        this.mCheckSoundEnable = true;
        this.isDetachedFromWindow = true;
        init();
    }

    private void init() {
        setClickable(true);
        Paint paint = new Paint(1);
        this.mShaderPaint = paint;
        paint.setStrokeWidth(px(4));
        this.mColorPaint = new Paint(1);
        this.mXFerModeAdd = new PorterDuffXfermode(PorterDuff.Mode.ADD);
        this.mBlurMaskFilter = new BlurMaskFilter(px(8), BlurMaskFilter.Blur.NORMAL);
        setButtonDrawable(R.drawable.x_switch_drawable);
        setBackgroundColor(getColor(17170445));
        setGravity(16);
        initColor();
        setSoundEffectsEnabled(false);
        this.mArgbEvaluator = new ArgbEvaluator();
    }

    private boolean isNight() {
        if (isInEditMode()) {
            return true;
        }
        return XThemeManager.isNight(getContext());
    }

    private void initColor() {
        this.isDetachedNightTheme = isNight();
        this.mBgEnableOffNormalColorStart = getColor(R.color.x_switch_bg_off_enable_normal_start_color);
        this.mBgEnableOffNormalColorEnd = getColor(R.color.x_switch_bg_off_enable_normal_end_color);
        this.mBgEnableOffPressedColorStart = getColor(R.color.x_switch_bg_off_enable_pressed_start_color);
        this.mBgEnableOffPressedColorEnd = getColor(R.color.x_switch_bg_off_enable_pressed_end_color);
        this.mBgEnableOnNormalColorStart = getColor(R.color.x_switch_bg_on_enable_normal_start_color);
        this.mBgEnableOnNormalColorEnd = getColor(R.color.x_switch_bg_on_enable_normal_end_color);
        this.mBgEnableOnPressedColorStart = getColor(R.color.x_switch_bg_on_enable_pressed_start_color);
        this.mBgEnableOnPressedColorEnd = getColor(R.color.x_switch_bg_on_enable_pressed_end_color);
        this.mBgDisableOffColorStart = getColor(R.color.x_switch_bg_off_disable_start_color);
        this.mBgDisableOffColorEnd = getColor(R.color.x_switch_bg_off_disable_end_color);
        this.mBgDisableOnColorStart = getColor(R.color.x_switch_bg_on_disable_start_color);
        this.mBgDisableOnColorEnd = getColor(R.color.x_switch_bg_on_disable_end_color);
        this.mCircleOutEnableOffColorStart = getColor(R.color.x_switch_circle_out_off_enable_start_color);
        this.mCircleOutEnableOffColorEnd = getColor(R.color.x_switch_circle_out_off_enable_end_color);
        this.mCircleOutEnableOnColorStart = getColor(R.color.x_switch_circle_out_on_enable_start_color);
        this.mCircleOutEnableOnColorEnd = getColor(R.color.x_switch_circle_out_on_enable_end_color);
        this.mCircleOutDisableOffColorStart = getColor(R.color.x_switch_circle_out_off_disable_start_color);
        this.mCircleOutDisableOffColorEnd = getColor(R.color.x_switch_circle_out_off_disable_end_color);
        this.mCircleOutDisableOnColorStart = getColor(R.color.x_switch_circle_out_on_disable_start_color);
        this.mCircleOutDisableOnColorEnd = getColor(R.color.x_switch_circle_out_on_disable_end_color);
        this.mCircleOutShadowColor = getColor(R.color.x_switch_circle_out_shadow_color);
        this.mCircleInnerEnableColorStart = getColor(R.color.x_switch_circle_inner_enable_start_color);
        this.mCircleInnerEnableColorEnd = getColor(R.color.x_switch_circle_inner_enable_end_color);
        this.mCircleInnerDisableColorStart = getColor(R.color.x_switch_circle_inner_disable_start_color);
        this.mCircleInnerDisableColorEnd = getColor(R.color.x_switch_circle_inner_disable_end_color);
        this.mVerticalEnableOffColor = getColor(R.color.x_switch_vertical_off_enable_color);
        this.mVerticalEnableOnColor = getColor(R.color.x_switch_vertical_on_enable_color);
        this.mVerticalDisableOffColor = getColor(R.color.x_switch_vertical_off_disable_color);
        this.mVerticalDisableOnColor = getColor(R.color.x_switch_vertical_on_disable_color);
        changeParams();
    }

    private void changeParams() {
        int i;
        int i2;
        if (isEnabled()) {
            if (isPressed()) {
                this.mBgOnColorStart = this.mBgEnableOnPressedColorStart;
                this.mBgOnColorEnd = this.mBgEnableOnPressedColorEnd;
                this.mBgOffColorStart = this.mBgEnableOffPressedColorStart;
                this.mBgOffColorEnd = this.mBgEnableOffPressedColorEnd;
            } else {
                this.mBgOnColorStart = this.mBgEnableOnNormalColorStart;
                this.mBgOnColorEnd = this.mBgEnableOnNormalColorEnd;
                this.mBgOffColorStart = this.mBgEnableOffNormalColorStart;
                this.mBgOffColorEnd = this.mBgEnableOffNormalColorEnd;
            }
            this.mCircleOutOffColorStart = this.mCircleOutEnableOffColorStart;
            this.mCircleOutOffColorEnd = this.mCircleOutEnableOffColorEnd;
            this.mCircleOutOnColorStart = this.mCircleOutEnableOnColorStart;
            this.mCircleOutOnColorEnd = this.mCircleOutEnableOnColorEnd;
            this.mBlurAlphaDst = 1.0f;
            i = this.mCircleInnerEnableColorStart;
            i2 = this.mCircleInnerEnableColorEnd;
            this.mVerticalOffColor = this.mVerticalEnableOffColor;
            this.mVerticalOnColor = this.mVerticalEnableOnColor;
        } else {
            this.mBgOnColorStart = this.mBgDisableOnColorStart;
            this.mBgOnColorEnd = this.mBgDisableOnColorEnd;
            this.mBgOffColorStart = this.mBgDisableOffColorStart;
            this.mBgOffColorEnd = this.mBgDisableOffColorEnd;
            this.mCircleOutOffColorStart = this.mCircleOutDisableOffColorStart;
            this.mCircleOutOffColorEnd = this.mCircleOutDisableOffColorEnd;
            this.mCircleOutOnColorStart = this.mCircleOutDisableOnColorStart;
            this.mCircleOutOnColorEnd = this.mCircleOutDisableOnColorEnd;
            this.mBlurAlphaDst = BLUR_ALPHA_DISABLE;
            i = this.mCircleInnerDisableColorStart;
            i2 = this.mCircleInnerDisableColorEnd;
            this.mVerticalOffColor = this.mVerticalDisableOffColor;
            this.mVerticalOnColor = this.mVerticalDisableOnColor;
        }
        this.mCircleInnerShader = new LinearGradient(0.0f, this.INNER_TOP, 0.0f, this.INNER_BOTTOM, i, i2, Shader.TileMode.CLAMP);
        if (isNight()) {
            this.mCircleOutOffShadowRadius = 0.0f;
            this.mCircleOutOffShadowOffsetY = 0.0f;
            this.mCircleOutOnShadowRadius = this.CIRCLE_OUT_NIGHT_ON_SHADOW_RADIUS;
            this.mCircleOutOnShadowOffsetY = 0.0f;
            this.mVerticalShadowRadius = this.SHADOW_RADIUS;
        } else {
            float f = this.CIRCLE_OUT_DAY_SHADOW_RADIUS;
            this.mCircleOutOffShadowRadius = f;
            this.mCircleOutOffShadowOffsetY = f;
            this.mCircleOutOnShadowRadius = f;
            this.mCircleOutOnShadowOffsetY = f;
            this.mVerticalShadowRadius = 0.0f;
        }
        updateViewState();
    }

    private void updateViewState() {
        updateBgFraction();
        updateCircleOutFraction();
        updateCircleMoveFraction();
        updateVerticalFraction();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XCompoundButton, android.widget.TextView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (XThemeManager.isThemeChanged(configuration)) {
            initColor();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XCompoundButton, android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.isDetachedFromWindow && this.isDetachedNightTheme != isNight()) {
            this.isDetachedFromWindow = false;
            initColor();
        }
        resetAnimValue();
        changeParams();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XCompoundButton, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.isDetachedFromWindow = true;
        this.isDetachedNightTheme = isNight();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.widget.CompoundButton
    public void setButtonDrawable(Drawable drawable) {
        super.setButtonDrawable(drawable);
        stopAnimation();
        resetAnimValue();
    }

    @Override // android.view.View
    public void setPressed(boolean z) {
        super.setPressed(z);
        changeParams();
    }

    public void setCheckSoundEnable(boolean z) {
        this.mCheckSoundEnable = z;
    }

    @Override // com.xiaopeng.xui.widget.XCompoundButton, android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z) {
        OnInterceptListener onInterceptListener = this.mOnInterceptListener;
        if (onInterceptListener == null || !onInterceptListener.onInterceptCheck(this, z)) {
            boolean isChecked = isChecked();
            if (isChecked != z && isPressed() && this.mCheckSoundEnable) {
                XSoundEffectManager.get().play(z ? 3 : 4);
            }
            super.setChecked(z);
            if (!isAttachedToWindow() || getWidth() == 0) {
                resetAnimValue();
                changeParams();
            } else if (isChecked != z) {
                stopAnimation();
                if (z) {
                    runAnimator(700);
                } else {
                    runAnimator(0);
                }
            }
        }
    }

    public void setChecked(boolean z, boolean z2) {
        if (z2) {
            setChecked(z);
            return;
        }
        super.setChecked(z);
        resetAnimValue();
        changeParams();
    }

    private void resetAnimValue() {
        if (isChecked()) {
            this.mCurrentAnimValue = 700;
        } else {
            this.mCurrentAnimValue = 0;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        changeParams();
    }

    private void runAnimator(int i) {
        ValueAnimator ofInt = ValueAnimator.ofInt(this.mCurrentAnimValue, i);
        this.mValueAnimator = ofInt;
        ofInt.setDuration(Math.abs(i - this.mCurrentAnimValue));
        this.mValueAnimator.addUpdateListener(this);
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.xui.widget.XSwitch.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                XSwitch.this.mValueAnimator.removeAllUpdateListeners();
                XSwitch.this.mValueAnimator.removeAllListeners();
                XSwitch.this.mValueAnimator = null;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                XSwitch.this.mValueAnimator.removeAllUpdateListeners();
                XSwitch.this.mValueAnimator.removeAllListeners();
                XSwitch.this.mValueAnimator = null;
            }
        });
        this.mValueAnimator.start();
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.mCurrentAnimValue = intValue;
        this.transPercent = (intValue - 200) / 300.0f;
        updateViewState();
    }

    private void stopAnimation() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            return;
        }
        this.mValueAnimator.cancel();
    }

    private void updateBgFraction() {
        int i = this.mCurrentAnimValue;
        if (i <= 200) {
            updateBgShader(this.mBgOffColorStart, this.mBgOffColorEnd);
        } else if (i <= 500) {
            updateBgShader(gradientColor(this.mBgOffColorStart, this.mBgOnColorStart, this.transPercent), gradientColor(this.mBgOffColorEnd, this.mBgOnColorEnd, this.transPercent));
        } else {
            updateBgShader(this.mBgOnColorStart, this.mBgOnColorEnd);
        }
    }

    private void updateBgShader(int i, int i2) {
        this.mBackgroundShader = new LinearGradient(0.0f, this.PADDING_TOP, 0.0f, this.BG_BOTTOM, i, i2, Shader.TileMode.CLAMP);
    }

    private void updateCircleOutFraction() {
        int i = this.mCurrentAnimValue;
        if (i <= 200) {
            updateCircleOutShader(this.mCircleOutOffColorStart, this.mCircleOutOffColorEnd);
            this.mCircleOutShadowRadius = this.mCircleOutOffShadowRadius;
            this.mCircleOutShadowOffsetY = this.mCircleOutOffShadowOffsetY;
            float f = (this.mBlurAlphaDst * this.mCurrentAnimValue) / 200.0f;
            updateCircleOutLightShader(changeColorAlpha(f, this.mCircleOutOffColorStart), changeColorAlpha(f, this.mCircleOutOffColorEnd));
        } else if (i <= 500) {
            int gradientColor = gradientColor(this.mCircleOutOffColorStart, this.mCircleOutOnColorStart, this.transPercent);
            int gradientColor2 = gradientColor(this.mCircleOutOffColorEnd, this.mCircleOutOnColorEnd, this.transPercent);
            updateCircleOutShader(gradientColor, gradientColor2);
            float f2 = this.mCircleOutOnShadowRadius - this.mCircleOutOffShadowRadius;
            float f3 = this.transPercent;
            this.mCircleOutShadowRadius = f2 * f3;
            this.mCircleOutShadowOffsetY = (this.mCircleOutOnShadowOffsetY - this.mCircleOutOffShadowOffsetY) * f3;
            updateCircleOutLightShader(changeColorAlpha(this.mBlurAlphaDst, gradientColor), changeColorAlpha(this.mBlurAlphaDst, gradientColor2));
        } else {
            updateCircleOutShader(this.mCircleOutOnColorStart, this.mCircleOutOnColorEnd);
            this.mCircleOutShadowRadius = this.mCircleOutOnShadowRadius;
            this.mCircleOutShadowOffsetY = this.mCircleOutOnShadowOffsetY;
            float f4 = this.mBlurAlphaDst;
            float f5 = f4 - ((((this.mCurrentAnimValue - 300) - 200) * f4) / 200.0f);
            updateCircleOutLightShader(changeColorAlpha(f5, this.mCircleOutOnColorStart), changeColorAlpha(f5, this.mCircleOutOnColorEnd));
        }
    }

    private void updateCircleOutShader(int i, int i2) {
        this.mCircleOutShader = new LinearGradient(0.0f, this.OUT_CIRCLE_TOP, 0.0f, this.OUT_CIRCLE_BOTTOM, i, i2, Shader.TileMode.CLAMP);
    }

    private void updateCircleOutLightShader(int i, int i2) {
        this.mCircleOutLightShader = new LinearGradient(0.0f, this.OUT_CIRCLE_TOP, 0.0f, this.OUT_CIRCLE_BOTTOM, i, i2, Shader.TileMode.CLAMP);
    }

    private void updateCircleMoveFraction() {
        int i = this.mCurrentAnimValue;
        if (i <= 200) {
            this.mTranslateX = 0.0f;
        } else if (i <= 500) {
            this.mTranslateX = this.MOVE_X * this.transPercent;
        } else {
            this.mTranslateX = this.MOVE_X;
        }
    }

    private void updateVerticalFraction() {
        int i = this.mCurrentAnimValue;
        if (i <= 200) {
            int i2 = this.mVerticalOffColor;
            this.mVerticalColor = i2;
            this.mVerticalLeft = this.VER_LEFT;
            this.mVerticalTop = this.CENTER_Y;
            this.mVerticalWidth = this.VER_WIDTH_LEFT;
            this.mVerticalHeight = 0.0f;
            this.mVerticalLightColor = changeColorAlpha((this.mBlurAlphaDst * i) / 200.0f, i2);
        } else if (i <= 500) {
            int gradientColor = gradientColor(this.mVerticalOffColor, this.mVerticalOnColor, this.transPercent);
            this.mVerticalColor = gradientColor;
            float f = ((this.mCurrentAnimValue - 200) - 120) / 180.0f;
            if (f >= 0.0f) {
                float f2 = this.VER_LEFT;
                this.mVerticalLeft = f2 + ((this.VER_RIGHT - f2) * f);
                float f3 = this.CENTER_Y;
                this.mVerticalTop = f3 - ((f3 - this.VER_TOP_RIGHT) * f);
                float f4 = this.VER_WIDTH_LEFT;
                this.mVerticalWidth = f4 - ((f4 - this.VER_WIDTH_RIGHT) * f);
                this.mVerticalHeight = this.VER_HEIGHT_RIGHT * f;
            }
            this.mVerticalLightColor = changeColorAlpha(this.mBlurAlphaDst, gradientColor);
        } else {
            int i3 = this.mVerticalOnColor;
            this.mVerticalColor = i3;
            this.mVerticalLeft = this.VER_RIGHT;
            this.mVerticalTop = this.VER_TOP_RIGHT;
            this.mVerticalWidth = this.VER_WIDTH_RIGHT;
            this.mVerticalHeight = this.VER_HEIGHT_RIGHT;
            float f5 = this.mBlurAlphaDst;
            this.mVerticalLightColor = changeColorAlpha(f5 - ((((i - 300) - 200) * f5) / 200.0f), i3);
        }
    }

    private int getColor(int i) {
        return getResources().getColor(i, getContext().getTheme());
    }

    private int changeColorAlpha(float f, int i) {
        return Color.argb((int) (f * 255.0f), Color.red(i), Color.green(i), Color.blue(i));
    }

    private int gradientColor(int i, int i2, float f) {
        return ((Integer) this.mArgbEvaluator.evaluate(f, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        float height = (((getHeight() - getPaddingTop()) - px(50)) / 2.0f) + getPaddingTop();
        this.PADDING_TOP = height;
        float px = height + px(50);
        this.BG_BOTTOM = px;
        float f = this.PADDING_TOP;
        float f2 = this.DIMEN_2;
        float f3 = f + f2;
        this.OUT_CIRCLE_TOP = f3;
        float f4 = f3 + f2;
        this.OUT_STROKE_TOP = f4;
        float f5 = (this.OUT_STROKE_RADIUS * 2.0f) + f4;
        this.OUT_STROKE_BOTTOM = f5;
        this.OUT_CIRCLE_BOTTOM = f5 + f2;
        float f6 = f4 + f2;
        this.INNER_TOP = f6;
        this.INNER_BOTTOM = (this.INNER_CIRCLE_RADIUS * 2.0f) + f6;
        this.CENTER_Y = ((px - f) / 2.0f) + f;
        this.VER_TOP_RIGHT = f6 + px(8);
        float paddingLeft = getPaddingLeft() + px(7);
        this.PADDING_LEFT = paddingLeft;
        this.BG_RIGHT = paddingLeft + px(90);
        float px2 = this.PADDING_LEFT + px(4);
        this.OUT_STROKE_LEFT = px2;
        this.OUT_STROKE_RIGHT = (this.OUT_STROKE_RADIUS * 2.0f) + px2;
        float f7 = px2 + this.DIMEN_2;
        this.INNER_LEFT = f7;
        this.INNER_CIRCLE_RIGHT = (this.INNER_CIRCLE_RADIUS * 2.0f) + f7;
        float px3 = f7 + px(10);
        this.VER_LEFT = px3;
        this.VER_RIGHT = px3 + px(46);
        changeParams();
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getVisibility() == 8) {
            return;
        }
        this.mShaderPaint.setStyle(Paint.Style.FILL);
        this.mShaderPaint.setShader(this.mBackgroundShader);
        float f = this.PADDING_LEFT;
        float f2 = this.PADDING_TOP;
        float f3 = this.BG_RIGHT;
        float f4 = this.BG_BOTTOM;
        float f5 = this.BG_RADIUS;
        canvas.drawRoundRect(f, f2, f3, f4, f5, f5, this.mShaderPaint);
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), this.mShaderPaint);
        this.mShaderPaint.setStyle(Paint.Style.STROKE);
        this.mShaderPaint.setShader(this.mCircleOutShader);
        float f6 = this.mCircleOutShadowRadius;
        if (f6 > 0.0f) {
            this.mShaderPaint.setShadowLayer(f6, 0.0f, this.mCircleOutShadowOffsetY, this.mCircleOutShadowColor);
        }
        float f7 = this.OUT_STROKE_LEFT;
        float f8 = this.mTranslateX;
        float f9 = this.OUT_STROKE_BOTTOM;
        float f10 = this.OUT_STROKE_RADIUS;
        canvas.drawRoundRect(f7 + f8, this.OUT_STROKE_TOP, this.OUT_STROKE_RIGHT + f8, f9, f10, f10, this.mShaderPaint);
        this.mShaderPaint.clearShadowLayer();
        this.mShaderPaint.setXfermode(this.mXFerModeAdd);
        this.mShaderPaint.setMaskFilter(this.mBlurMaskFilter);
        this.mShaderPaint.setShader(this.mCircleOutLightShader);
        float f11 = this.OUT_STROKE_LEFT;
        float f12 = this.mTranslateX;
        float f13 = this.OUT_STROKE_BOTTOM;
        float f14 = this.OUT_STROKE_RADIUS;
        canvas.drawRoundRect(f11 + f12, this.OUT_STROKE_TOP, this.OUT_STROKE_RIGHT + f12, f13, f14, f14, this.mShaderPaint);
        canvas.restoreToCount(saveLayer);
        this.mShaderPaint.setMaskFilter(null);
        this.mShaderPaint.setXfermode(null);
        this.mShaderPaint.clearShadowLayer();
        this.mShaderPaint.setStyle(Paint.Style.FILL);
        this.mShaderPaint.setShader(this.mCircleInnerShader);
        float f15 = this.INNER_LEFT;
        float f16 = this.mTranslateX;
        float f17 = this.INNER_BOTTOM;
        float f18 = this.INNER_CIRCLE_RADIUS;
        canvas.drawRoundRect(f15 + f16, this.INNER_TOP, this.INNER_CIRCLE_RIGHT + f16, f17, f18, f18, this.mShaderPaint);
        this.mColorPaint.setColor(this.mVerticalColor);
        float f19 = this.mVerticalShadowRadius;
        if (f19 > 0.0f) {
            this.mColorPaint.setShadowLayer(f19, 0.0f, 0.0f, this.mVerticalColor);
        }
        int saveLayer2 = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), this.mShaderPaint);
        float f20 = this.mVerticalLeft;
        float f21 = this.mVerticalTop;
        float f22 = this.VER_RADIUS;
        canvas.drawRoundRect(f20, f21, f20 + this.mVerticalWidth, f21 + this.mVerticalHeight, f22, f22, this.mColorPaint);
        this.mColorPaint.clearShadowLayer();
        if (isEnabled()) {
            this.mColorPaint.setColor(this.mVerticalLightColor);
            this.mColorPaint.setXfermode(this.mXFerModeAdd);
            this.mColorPaint.setMaskFilter(this.mBlurMaskFilter);
            float f23 = this.mVerticalLeft;
            float f24 = this.mVerticalTop;
            float f25 = this.VER_RADIUS;
            canvas.drawRoundRect(f23, f24, f23 + this.mVerticalWidth, f24 + this.mVerticalHeight, f25, f25, this.mColorPaint);
        }
        canvas.restoreToCount(saveLayer2);
        this.mColorPaint.setMaskFilter(null);
        this.mColorPaint.setXfermode(null);
    }

    private float px(int i) {
        return TypedValue.applyDimension(1, i, getResources().getDisplayMetrics());
    }

    @Override // com.xiaopeng.xui.widget.XCompoundButton, android.widget.CompoundButton, android.view.View
    public boolean performClick() {
        OnInterceptListener onInterceptListener = this.mOnInterceptListener;
        if (onInterceptListener == null || !onInterceptListener.onInterceptClickEvent(this)) {
            return super.performClick();
        }
        return false;
    }

    public void setOnInterceptListener(OnInterceptListener onInterceptListener) {
        this.mOnInterceptListener = onInterceptListener;
    }
}
