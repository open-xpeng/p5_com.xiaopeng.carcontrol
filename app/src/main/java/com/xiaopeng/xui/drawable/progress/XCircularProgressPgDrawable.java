package com.xiaopeng.xui.drawable.progress;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.graphics.XLightPaint;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class XCircularProgressPgDrawable extends Drawable {
    private static final float PROGRESS_STROKE_WIDTH_DEFAULT = 0.0f;
    private boolean mEnableLight;
    private float mInset;
    private float mLightRadius;
    private int mProgressColor;
    private float mStrokeWidth;
    protected Paint mPaint = new Paint();
    private Rect mOutBounds = getBounds();
    private RectF mInnerBounds = new RectF(getBounds());

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public XCircularProgressPgDrawable() {
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
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.XCircularProgressPgDrawable, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.XCircularProgressPgDrawable);
        }
        this.mProgressColor = obtainAttributes.getColor(R.styleable.XCircularProgressPgDrawable_progress_color, resources.getColor(R.color.x_circular_progress_primary_color, theme));
        this.mStrokeWidth = obtainAttributes.getDimension(R.styleable.XCircularProgressPgDrawable_progress_pg_strokeWidth, 0.0f);
        this.mEnableLight = obtainAttributes.getBoolean(R.styleable.XCircularProgressPgDrawable_progress_enable_light, false);
        this.mInset = obtainAttributes.getDimensionPixelSize(R.styleable.XCircularProgressPgDrawable_progress_pg_inset, 0);
        this.mLightRadius = obtainAttributes.getDimensionPixelOffset(R.styleable.XCircularProgressPgDrawable_progress_pg_light_radius, 0);
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
        this.mInnerBounds.set(rect);
        if (this.mStrokeWidth == 0.0f) {
            this.mStrokeWidth = (this.mOutBounds.width() * 1.0f) / 10.0f;
        }
        float f = this.mStrokeWidth / 2.0f;
        RectF rectF = this.mInnerBounds;
        float f2 = this.mInset;
        rectF.inset(f2 + f, f2 + f);
        this.mPaint.setColor(this.mProgressColor);
        this.mPaint.setStrokeWidth(this.mStrokeWidth);
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
        canvas.drawArc(this.mInnerBounds, -90.0f, ((getLevel() * 1.0f) / 10000.0f) * 360.0f, false, this.mPaint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        invalidateSelf();
        return super.onLevelChange(i);
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
