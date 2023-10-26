package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.drawable.XLoadingDrawable;
import com.xiaopeng.xui.graphics.XFpsMonitor;
import com.xiaopeng.xui.view.XView;

/* loaded from: classes2.dex */
public class XLoading extends XView {
    private static final String TAG = "XLoading";
    private boolean isDebug;
    private XLoadingDrawable mDrawable;
    private XFpsMonitor mFpsMonitor;

    public XLoading(Context context) {
        this(context, null);
    }

    public XLoading(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.style.XLoading_XLarge);
    }

    public XLoading(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.XLoading_XLarge);
    }

    public XLoading(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.isDebug = false;
        init(context, attributeSet, i, i2);
    }

    private void init(Context context, AttributeSet attributeSet, int i, int i2) {
        if (Build.VERSION.SDK_INT <= 26) {
            setLayerType(1, null);
        }
        XLoadingDrawable xLoadingDrawable = new XLoadingDrawable();
        this.mDrawable = xLoadingDrawable;
        xLoadingDrawable.setCallback(this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XLoading, i, i2);
        this.mDrawable.setType(obtainStyledAttributes.getInt(R.styleable.XLoading_loading_type, 3));
        obtainStyledAttributes.recycle();
        if (this.isDebug) {
            this.mFpsMonitor = new XFpsMonitor(TAG);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mDrawable;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (getLayoutParams().width == -2 && getLayoutParams().height == -2) {
            setMeasuredDimension(216, 216);
        } else if (getLayoutParams().width == -2) {
            setMeasuredDimension(216, size2);
        } else if (getLayoutParams().height == -2) {
            setMeasuredDimension(size, 216);
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mDrawable.setBounds(i, i2, i3, i4);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.view.XView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDrawable.onConfigurationChanged(getContext(), configuration);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        XFpsMonitor xFpsMonitor;
        XFpsMonitor xFpsMonitor2;
        if (this.isDebug && (xFpsMonitor2 = this.mFpsMonitor) != null) {
            xFpsMonitor2.frameStart();
        }
        super.draw(canvas);
        if (!this.isDebug || (xFpsMonitor = this.mFpsMonitor) == null) {
            return;
        }
        xFpsMonitor.frameEnd();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mDrawable.draw(canvas);
    }

    public void setType(int i) {
        this.mDrawable.setType(i);
    }

    public void setDuration(long j) {
        this.mDrawable.setDuration(j);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.view.XView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mDrawable.onConfigurationChanged(getContext(), getResources().getConfiguration());
        this.mDrawable.cancelAnimations();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.view.XView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDrawable.cancelAnimations();
    }

    public void setDebug(boolean z) {
        this.isDebug = z;
        this.mDrawable.setDebug(z);
    }

    public float getDelayFactor() {
        return this.mDrawable.getDelayFactor();
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        this.mDrawable.setVisible(i == 0, true);
    }
}
