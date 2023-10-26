package com.xiaopeng.xui.drawable.ripple;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.StateSet;
import com.xiaopeng.xpui.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class XRippleDrawable extends Drawable {
    private static final String TAG = "XRippleDrawable";
    private boolean mIsTouchDown;
    private float mDownX = -1.0f;
    private float mDownY = -1.0f;
    private int[] stateSpecPress = new int[2];
    private XRipple mRipple = new XRipple();

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public XRippleDrawable() {
        int[] iArr = this.stateSpecPress;
        iArr[0] = 16842919;
        iArr[1] = 16842910;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        if (iArr != null && this.mRipple != null) {
            if (StateSet.stateSetMatches(this.stateSpecPress, iArr) && !this.mIsTouchDown) {
                float f = this.mDownX;
                if (f > 0.0f) {
                    float f2 = this.mDownY;
                    if (f2 > 0.0f) {
                        this.mIsTouchDown = true;
                        this.mRipple.pressDown(f, f2);
                    }
                }
            }
            if (this.mIsTouchDown) {
                this.mIsTouchDown = false;
                this.mRipple.pressUp();
            }
        }
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        initRipple(rect);
    }

    private void initRipple(Rect rect) {
        this.mRipple.setCallback(getCallback());
        this.mRipple.setRippleRect(new RectF(rect.left, rect.top, rect.right, rect.bottom));
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        XRipple xRipple = this.mRipple;
        if (xRipple != null) {
            xRipple.drawRipple(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        super.setHotspot(f, f2);
        this.mDownX = f;
        this.mDownY = f2;
    }

    public XRipple getXRipple() {
        return this.mRipple;
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

    private void inflateAttrs(Resources resources, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray obtainAttributes;
        if (resources == null || attributeSet == null) {
            return;
        }
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.XRippleDrawable, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.XRippleDrawable);
        }
        int color = obtainAttributes.getColor(R.styleable.XRippleDrawable_rippleBackgroundColor, 0);
        int color2 = obtainAttributes.getColor(R.styleable.XRippleDrawable_rippleColor, resources.getColor(R.color.x_ripple_default_color, theme));
        boolean z = obtainAttributes.getBoolean(R.styleable.XRippleDrawable_rippleSupportScale, false);
        int i = obtainAttributes.getInt(R.styleable.XRippleDrawable_rippleDuration, resources.getInteger(R.integer.x_ripple_default_anim));
        this.mRipple.setRippleRadius(obtainAttributes.getDimensionPixelSize(R.styleable.XRippleDrawable_rippleRadius, 0));
        this.mRipple.setRippleColor(color2);
        this.mRipple.setRippleBackgroundColor(color);
        this.mRipple.setSupportScale(z);
        long j = i;
        this.mRipple.setPressDuration(j);
        this.mRipple.setUpDuration(j);
        obtainAttributes.recycle();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        XRipple xRipple = this.mRipple;
        if (xRipple != null && visible) {
            xRipple.setVisible(z);
        }
        return visible;
    }
}
