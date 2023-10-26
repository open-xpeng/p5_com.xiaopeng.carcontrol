package com.xiaopeng.xui.drawable.checkbox;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.StateSet;
import com.xiaopeng.xpui.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class XCheckBoxDrawable extends Drawable {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private ColorStateList mColorBg;
    private ColorStateList mColorFront;
    private ColorStateList mColorInner;
    private float mDrawablePadding;
    private float mFrameWidth;
    private Path mNikePath;
    private Paint mPaint;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(this.mColorBg.getColorForState(getState(), R.color.x_check_box_inner_color));
        float f = this.mDrawablePadding;
        float f2 = this.mFrameWidth;
        canvas.drawRect(f, f, f + f2, f + f2, this.mPaint);
        if (StateSet.stateSetMatches(CHECKED_STATE_SET, getState())) {
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(this.mColorFront.getColorForState(getState(), R.color.x_check_box_frame_color));
            float f3 = this.mDrawablePadding;
            float f4 = this.mFrameWidth;
            canvas.drawRoundRect(f3 - 1.5f, f3 - 1.5f, f3 + f4 + 1.5f, f3 + f4 + 1.5f, 3.0f, 3.0f, this.mPaint);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setColor(this.mColorInner.getColorForState(getState(), R.color.x_radio_inner_color_selector));
            this.mPaint.setStrokeWidth(4.0f);
            canvas.drawPath(this.mNikePath, this.mPaint);
            return;
        }
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(3.0f);
        this.mPaint.setColor(this.mColorFront.getColorForState(getState(), R.color.x_check_box_frame_color));
        float f5 = this.mDrawablePadding;
        float f6 = this.mFrameWidth;
        canvas.drawRoundRect(f5, f5, f5 + f6, f5 + f6, 3.0f, 3.0f, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws IOException, XmlPullParserException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.mPaint = new Paint(1);
        this.mColorBg = resources.getColorStateList(R.color.x_check_box_inner_color, theme);
        this.mColorFront = resources.getColorStateList(R.color.x_check_box_frame_color, theme);
        this.mColorInner = resources.getColorStateList(R.color.x_radio_inner_color_selector, theme);
        this.mDrawablePadding = 28.5f;
        this.mFrameWidth = 33.0f;
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);
        Path path = new Path();
        this.mNikePath = path;
        path.moveTo(36.0f, 46.0f);
        this.mNikePath.lineTo(41.0f, 52.0f);
        this.mNikePath.lineTo(54.0f, 40.0f);
    }
}
