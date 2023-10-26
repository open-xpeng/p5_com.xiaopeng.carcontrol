package com.xiaopeng.xui.graphics;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class XLightPaint {
    public static final int LIGHT_TYPE_BLUR_MASK_FILTER = 0;
    public static final int LIGHT_TYPE_SHADOW_LAYER = 1;
    private static float MAX_LIGHT_RADIUS = 200.0f;
    private BlurMaskFilter mBlurMaskFilter;
    private LightingColorFilter mLightingColorFilter;
    private Paint mPaint;
    private int mLightColor = -1;
    private int mLightAlpha = 255;
    private int mBrightness = 0;
    private float mLightStrength = 1.0f;
    private int mLightType = 0;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface LightType {
    }

    public XLightPaint(Paint paint) {
        this.mPaint = paint;
    }

    public void setLightType(int i) {
        this.mLightType = i;
    }

    public void setLightRadius(float f) {
        MAX_LIGHT_RADIUS = f;
    }

    public void setBrightness(int i) {
        this.mBrightness = i;
    }

    public void setLightColor(int i) {
        this.mLightColor = i;
    }

    public void setLightStrength(float f) {
        this.mLightStrength = f;
    }

    public void apply() {
        float f = MAX_LIGHT_RADIUS;
        float f2 = this.mLightStrength;
        float f3 = f * f2;
        int i = this.mBrightness;
        if (i > 0) {
            int i2 = (int) (f2 * i);
            LightingColorFilter lightingColorFilter = new LightingColorFilter(-1, Color.argb(this.mLightAlpha, i2, i2, i2));
            this.mLightingColorFilter = lightingColorFilter;
            this.mPaint.setColorFilter(lightingColorFilter);
        }
        int i3 = this.mLightType;
        if (i3 == 0) {
            if (f3 > 0.0f) {
                BlurMaskFilter blurMaskFilter = new BlurMaskFilter(f3, BlurMaskFilter.Blur.SOLID);
                this.mBlurMaskFilter = blurMaskFilter;
                this.mPaint.setMaskFilter(blurMaskFilter);
                return;
            }
            this.mPaint.setMaskFilter(null);
        } else if (i3 == 1) {
            int i4 = this.mLightColor;
            if (i4 == -1) {
                throw new IllegalArgumentException("Please set light color.");
            }
            this.mPaint.setShadowLayer(f3, 0.0f, 0.0f, i4);
        }
    }

    public Paint getPaint() {
        return this.mPaint;
    }
}
