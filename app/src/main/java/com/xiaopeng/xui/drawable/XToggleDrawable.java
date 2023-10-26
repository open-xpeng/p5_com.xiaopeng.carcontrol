package com.xiaopeng.xui.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.graphics.XLightPaint;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class XToggleDrawable extends Drawable {
    private static final int BRIGHTNESS_DEFAULT = 0;
    private static final float LIGHT_RADIUS_DEFAULT = 0.0f;
    private static final float LIGHT_STRENGTH_DEFAULT = 0.5f;
    private static final int TOGGLE_STYLE_DEFAULT = 0;
    private float mRoundRadius;
    private XLightPaint mXLightPaint;
    private Paint mPaint = new Paint(1);
    private boolean mChecked = false;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
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
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.XToggleDrawable, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.XToggleDrawable);
        }
        int color = resources.getColor(R.color.x_theme_primary_normal, theme);
        int color2 = obtainAttributes.getColor(R.styleable.XToggleDrawable_toggle_stroke_color, color);
        float dimension = obtainAttributes.getDimension(R.styleable.XToggleDrawable_toggle_stroke_width, resources.getDimension(R.dimen.x_toggle_stroke_size));
        boolean z = obtainAttributes.getBoolean(R.styleable.XToggleDrawable_toggle_enable_light, false);
        float dimension2 = obtainAttributes.getDimension(R.styleable.XToggleDrawable_toggle_light_radius, 0.0f);
        int i = obtainAttributes.getInt(R.styleable.XToggleDrawable_toggle_brightness, 0);
        float f = obtainAttributes.getFloat(R.styleable.XToggleDrawable_toggle_light_strength, 0.5f);
        int i2 = obtainAttributes.getInt(R.styleable.XToggleDrawable_toggle_light_type, 0);
        int color3 = obtainAttributes.getColor(R.styleable.XToggleDrawable_toggle_light_color, color);
        int i3 = obtainAttributes.getInt(R.styleable.XToggleDrawable_toggle_style, 0);
        this.mRoundRadius = obtainAttributes.getDimension(R.styleable.XToggleDrawable_toggle_round_radius, resources.getDimension(R.dimen.x_toggle_rect_round_radius));
        obtainAttributes.recycle();
        this.mPaint.setColor(color2);
        this.mPaint.setStrokeWidth(dimension);
        if (i3 == 0) {
            this.mPaint.setStyle(Paint.Style.STROKE);
        } else if (i3 == 1) {
            this.mPaint.setStyle(Paint.Style.FILL);
        }
        if (z) {
            XLightPaint xLightPaint = new XLightPaint(this.mPaint);
            this.mXLightPaint = xLightPaint;
            xLightPaint.setLightRadius(dimension2);
            this.mXLightPaint.setBrightness(i);
            this.mXLightPaint.setLightColor(color3);
            this.mXLightPaint.setLightStrength(f);
            this.mXLightPaint.setLightType(i2);
            this.mXLightPaint.apply();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.mChecked) {
            int save = canvas.save();
            Rect bounds = getBounds();
            float f = this.mRoundRadius;
            canvas.drawRoundRect(bounds.left, bounds.top, bounds.right, bounds.bottom, f, f, this.mPaint);
            canvas.restoreToCount(save);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean z;
        int length = iArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            } else if (iArr[i] == 16842912) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        boolean z2 = this.mChecked != z;
        this.mChecked = z;
        return z2;
    }
}
