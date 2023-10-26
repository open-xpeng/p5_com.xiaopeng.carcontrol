package com.xiaopeng.carcontrol.view.widget;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import com.xiaopeng.carcontrolmodule.R;
import java.io.IOException;
import java.util.Random;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class PlayingDrawable extends Drawable {
    private static final long DURATION = 4000;
    private static final int LINE_NUM = 4;
    private ValueAnimator mAnimator;
    private final Paint mPaint = new Paint();
    private boolean isAnimStart = false;
    private float MAX_LINE_HEIGHT = 24.0f;
    private float MIN_LINE_HEIGHT = 6.0f;
    private float mWidth = 24.0f;
    private float mHeight = 24.0f;
    private float mLineWidth = 3.0f;
    private float[] mLineHeights = new float[4];
    private float mLineSpacing = 4.0f;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        super.inflate(r, parser, attrs);
        inflateAttrs(r, attrs, null);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(r, parser, attrs, theme);
        inflateAttrs(r, attrs, theme);
    }

    private void inflateAttrs(Resources r, AttributeSet attrs, Resources.Theme theme) {
        TypedArray obtainAttributes;
        if (r == null || attrs == null) {
            return;
        }
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attrs, R.styleable.PlayDrawable, 0, 0);
        } else {
            obtainAttributes = r.obtainAttributes(attrs, R.styleable.PlayDrawable);
        }
        int color = obtainAttributes.getColor(R.styleable.PlayDrawable_play_color, -1);
        float f = obtainAttributes.getFloat(R.styleable.PlayDrawable_drawable_scale, 1.0f);
        obtainAttributes.recycle();
        if (color == -1) {
            color = Color.parseColor("#3B99FF");
        }
        this.mHeight *= f;
        this.mLineWidth *= f;
        this.mLineSpacing *= f;
        this.MAX_LINE_HEIGHT *= f;
        this.MIN_LINE_HEIGHT *= f;
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setStrokeWidth(this.mLineWidth);
        this.mPaint.setColor(color);
        final Random random = new Random();
        for (int i = 0; i < 4; i++) {
            float[] fArr = this.mLineHeights;
            float nextFloat = random.nextFloat();
            float f2 = this.MAX_LINE_HEIGHT;
            float f3 = this.MIN_LINE_HEIGHT;
            fArr[i] = (nextFloat * ((f2 - f3) + 1.0f)) + f3;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mAnimator = ofFloat;
        ofFloat.setRepeatCount(-1);
        this.mAnimator.setDuration(DURATION);
        this.mAnimator.setInterpolator(new LinearInterpolator());
        this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$PlayingDrawable$Eia5UpfRSxu291HP3NzQjfEldvE
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                PlayingDrawable.this.lambda$inflateAttrs$0$PlayingDrawable(random, valueAnimator);
            }
        });
    }

    public /* synthetic */ void lambda$inflateAttrs$0$PlayingDrawable(final Random random, ValueAnimator animation) {
        for (int i = 0; i < 4; i++) {
            boolean z = random.nextInt(2) == 1;
            float nextFloat = random.nextFloat();
            float[] fArr = this.mLineHeights;
            fArr[i] = fArr[i] + ((z ? 1 : -1) * nextFloat);
            if (fArr[i] > this.MAX_LINE_HEIGHT) {
                fArr[i] = fArr[i] - nextFloat;
            } else if (fArr[i] < this.MIN_LINE_HEIGHT) {
                fArr[i] = fArr[i] + nextFloat;
            }
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        for (int i = 0; i < 4; i++) {
            float f = this.mLineWidth;
            float f2 = (i * (this.mLineSpacing + f)) + (f * 0.5f);
            float f3 = this.mHeight;
            canvas.drawLine(f2, f3 - this.mLineHeights[i], f2, f3, this.mPaint);
        }
        startAnim();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean visible, boolean restart) {
        if (!visible) {
            cancelAnim();
        }
        return super.setVisible(visible, restart);
    }

    private void startAnim() {
        if (this.isAnimStart) {
            return;
        }
        this.mAnimator.start();
        this.isAnimStart = true;
    }

    public void cancelAnim() {
        this.mAnimator.cancel();
        this.isAnimStart = false;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return getBounds().right - getBounds().left;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return getBounds().bottom - getBounds().top;
    }
}
