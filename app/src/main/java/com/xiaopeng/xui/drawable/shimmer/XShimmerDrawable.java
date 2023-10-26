package com.xiaopeng.xui.drawable.shimmer;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.drawable.shimmer.XShimmer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@Deprecated
/* loaded from: classes2.dex */
public class XShimmerDrawable extends Drawable {
    private XShimmer.Builder mBuilder;
    private final Rect mDrawRect;
    private final Matrix mShaderMatrix;
    private XShimmer mShimmer;
    private final Paint mShimmerPaint;
    private final ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.xui.drawable.shimmer.XShimmerDrawable.1
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            XShimmerDrawable.this.invalidateSelf();
        }
    };
    private ValueAnimator mValueAnimator;

    private float offset(float f, float f2, float f3) {
        return f + ((f2 - f) * f3);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public XShimmerDrawable() {
        Paint paint = new Paint();
        this.mShimmerPaint = paint;
        this.mDrawRect = new Rect();
        this.mShaderMatrix = new Matrix();
        paint.setAntiAlias(true);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        inflate(resources, xmlPullParser, attributeSet, null);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        setShimmer(parseShimmer(resources, attributeSet, theme));
        super.inflate(resources, xmlPullParser, attributeSet, theme);
    }

    public XShimmer parseShimmer(Resources resources, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray obtainAttributes;
        if (attributeSet == null) {
            return new XShimmer.AlphaHighlightBuilder().build();
        }
        if (resources == null || attributeSet == null) {
            return null;
        }
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.XShimmerDrawable, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.XShimmerDrawable);
        }
        try {
            XShimmer.Builder colorHighlightBuilder = (obtainAttributes.hasValue(R.styleable.XShimmerDrawable_shimmer_colored) && obtainAttributes.getBoolean(R.styleable.XShimmerDrawable_shimmer_colored, false)) ? new XShimmer.ColorHighlightBuilder() : new XShimmer.AlphaHighlightBuilder();
            saveShimmerBuilder(colorHighlightBuilder);
            return colorHighlightBuilder.consumeAttributes(obtainAttributes).build();
        } finally {
            obtainAttributes.recycle();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        float offset;
        float offset2;
        if (this.mShimmer == null || this.mShimmerPaint.getShader() == null) {
            return;
        }
        float tan = (float) Math.tan(Math.toRadians(this.mShimmer.tilt));
        float height = this.mDrawRect.height() + (this.mDrawRect.width() * tan);
        float width = this.mDrawRect.width() + (tan * this.mDrawRect.height());
        ValueAnimator valueAnimator = this.mValueAnimator;
        float f = 0.0f;
        float animatedFraction = valueAnimator != null ? valueAnimator.getAnimatedFraction() : 0.0f;
        int i = this.mShimmer.direction;
        if (i != 1) {
            if (i == 2) {
                offset2 = offset(width, -width, animatedFraction);
            } else if (i != 3) {
                offset2 = offset(-width, width, animatedFraction);
            } else {
                offset = offset(height, -height, animatedFraction);
            }
            f = offset2;
            offset = 0.0f;
        } else {
            offset = offset(-height, height, animatedFraction);
        }
        this.mShaderMatrix.reset();
        this.mShaderMatrix.setRotate(this.mShimmer.tilt, this.mDrawRect.width() / 2.0f, this.mDrawRect.height() / 2.0f);
        this.mShaderMatrix.postTranslate(f, offset);
        this.mShimmerPaint.getShader().setLocalMatrix(this.mShaderMatrix);
        canvas.drawRect(this.mDrawRect, this.mShimmerPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mBuilder.setHighlightAlpha(i / 255.0f).build();
        setShimmer(this.mBuilder.build());
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        XShimmer xShimmer = this.mShimmer;
        return (xShimmer == null || !(xShimmer.clipToChildren || this.mShimmer.alphaShimmer)) ? -1 : -3;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mDrawRect.set(rect);
        updateShader();
        maybeStartShimmer();
    }

    public void setColorHighlight(int i, int i2) {
        this.mBuilder.setHighlightColor(i).setBaseColor(i2).build();
        setShimmer(this.mBuilder.build());
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        if (!z) {
            stopShimmer();
        } else {
            maybeStartShimmer();
        }
        return super.setVisible(z, z2);
    }

    public void setShimmer(XShimmer xShimmer) {
        this.mShimmer = xShimmer;
        if (xShimmer != null) {
            updateShader();
            updateValueAnimator();
            invalidateSelf();
        }
    }

    private void saveShimmerBuilder(XShimmer.Builder builder) {
        this.mBuilder = builder;
    }

    public void maybeStartShimmer() {
        XShimmer xShimmer;
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator == null || valueAnimator.isStarted() || (xShimmer = this.mShimmer) == null || !xShimmer.autoStart || getCallback() == null) {
            return;
        }
        this.mValueAnimator.start();
    }

    public void startShimmer() {
        if (this.mValueAnimator == null || isShimmerStarted() || getCallback() == null) {
            return;
        }
        this.mValueAnimator.start();
    }

    public void stopShimmer() {
        if (this.mValueAnimator == null || !isShimmerStarted()) {
            return;
        }
        this.mValueAnimator.cancel();
    }

    public boolean isShimmerStarted() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        return valueAnimator != null && valueAnimator.isStarted();
    }

    private void updateValueAnimator() {
        boolean z;
        if (this.mShimmer == null) {
            return;
        }
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            z = valueAnimator.isStarted();
            this.mValueAnimator.cancel();
            this.mValueAnimator.removeAllUpdateListeners();
        } else {
            z = false;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, ((float) (this.mShimmer.repeatDelay / this.mShimmer.animationDuration)) + 1.0f);
        this.mValueAnimator = ofFloat;
        ofFloat.setRepeatMode(this.mShimmer.repeatMode);
        this.mValueAnimator.setRepeatCount(this.mShimmer.repeatCount);
        this.mValueAnimator.setDuration(this.mShimmer.animationDuration + this.mShimmer.repeatDelay);
        this.mValueAnimator.addUpdateListener(this.mUpdateListener);
        if (z) {
            this.mValueAnimator.start();
        }
    }

    private void updateShader() {
        XShimmer xShimmer;
        Shader radialGradient;
        Rect bounds = getBounds();
        int width = bounds.width();
        int height = bounds.height();
        if (width == 0 || height == 0 || (xShimmer = this.mShimmer) == null) {
            return;
        }
        int width2 = xShimmer.width(width);
        int height2 = this.mShimmer.height(height);
        boolean z = true;
        if (this.mShimmer.shape != 1) {
            if (this.mShimmer.direction != 1 && this.mShimmer.direction != 3) {
                z = false;
            }
            if (z) {
                width2 = 0;
            }
            if (!z) {
                height2 = 0;
            }
            radialGradient = new LinearGradient(0.0f, 0.0f, width2, height2, this.mShimmer.colors, this.mShimmer.positions, Shader.TileMode.CLAMP);
        } else {
            radialGradient = new RadialGradient(width2 / 2.0f, height2 / 2.0f, (float) (Math.max(width2, height2) / Math.sqrt(2.0d)), this.mShimmer.colors, this.mShimmer.positions, Shader.TileMode.CLAMP);
        }
        this.mShimmerPaint.setShader(radialGradient);
    }
}
