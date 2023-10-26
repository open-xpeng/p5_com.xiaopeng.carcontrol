package com.xiaopeng.xui.drawable.progress;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.graphics.XLightPaint;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class XCircularProgressIndeterminateDrawable extends Drawable {
    private static final float ARC_START_ANGLE = 8.0f;
    private static final float ARC_SWEEP_ANGLE = 340.0f;
    private static final float STROKE_WIDTH_DEFAULT = 0.0f;
    private boolean mEnableLight;
    private int mEndColor;
    private float mInset;
    private float mLightRadius;
    private int mStartColor;
    private float mStrokeWidth;
    protected Paint mPaint = new Paint();
    private Rect mBounds = getBounds();
    private RectF mInnerBounds = new RectF(getBounds());

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public XCircularProgressIndeterminateDrawable() {
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setDither(true);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws IOException, XmlPullParserException {
        super.inflate(resources, xmlPullParser, attributeSet);
        updateAttr(resources, attributeSet, null);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws IOException, XmlPullParserException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateAttr(resources, attributeSet, theme);
    }

    private void updateAttr(Resources resources, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray obtainAttributes;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.XCircularProgressIndeterminateDrawable, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.XCircularProgressIndeterminateDrawable);
        }
        this.mStartColor = obtainAttributes.getColor(R.styleable.XCircularProgressIndeterminateDrawable_progress_indeterminate_startColor, resources.getColor(17170445, theme));
        this.mEndColor = obtainAttributes.getColor(R.styleable.XCircularProgressIndeterminateDrawable_progress_indeterminate_endColor, resources.getColor(R.color.x_circular_progress_primary_color, theme));
        this.mStrokeWidth = obtainAttributes.getDimension(R.styleable.XCircularProgressIndeterminateDrawable_progress_indeterminate_strokeWidth, 0.0f);
        this.mEnableLight = obtainAttributes.getBoolean(R.styleable.XCircularProgressIndeterminateDrawable_progress_indeterminate_enable_light, false);
        this.mInset = obtainAttributes.getDimensionPixelSize(R.styleable.XCircularProgressIndeterminateDrawable_progress_indeterminate_inset, 0);
        this.mLightRadius = obtainAttributes.getDimensionPixelOffset(R.styleable.XCircularProgressIndeterminateDrawable_progress_indeterminate_light_radius, 0);
        obtainAttributes.recycle();
    }

    public void setStrokeWidth(float f) {
        this.mStrokeWidth = f;
    }

    public void setInset(float f) {
        this.mInset = f;
    }

    public void setLightRadius(float f) {
        this.mLightRadius = f;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mPaint.setShader(new SweepGradient(this.mBounds.centerX(), this.mBounds.centerY(), new int[]{this.mStartColor, this.mEndColor}, (float[]) null));
        if (this.mStrokeWidth == 0.0f) {
            this.mStrokeWidth = this.mBounds.width() / 10.0f;
        }
        this.mPaint.setStrokeWidth(this.mStrokeWidth);
        float f = this.mStrokeWidth / 2.0f;
        this.mInnerBounds.set(rect);
        RectF rectF = this.mInnerBounds;
        float f2 = this.mInset;
        rectF.inset(f2 + f, f2 + f);
        if (this.mEnableLight) {
            if (this.mLightRadius == 0.0f) {
                this.mLightRadius = this.mStrokeWidth;
            }
            XLightPaint xLightPaint = new XLightPaint(this.mPaint);
            xLightPaint.setLightRadius(this.mLightRadius);
            xLightPaint.apply();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.drawArc(this.mInnerBounds, ARC_START_ANGLE, ARC_SWEEP_ANGLE, false, this.mPaint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }
}
