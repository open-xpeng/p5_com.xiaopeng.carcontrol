package com.xiaopeng.xui.widget.toggle;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.drawable.XLoadingDrawable;
import com.xiaopeng.xui.widget.XRelativeLayout;

/* loaded from: classes2.dex */
public class XToggleLayout extends XRelativeLayout implements Checkable {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int NO_ALPHA = 255;
    private int mBgId;
    private boolean mBroadcasting;
    private boolean mCheckStarting;
    private boolean mChecked;
    private float mDisabledAlpha;
    private boolean mEnabled;
    private Drawable mIndicatorDrawable;
    private boolean mLoading;
    private XLoadingDrawable mLoadingDrawable;
    private OnCheckedChangeListener mOnCheckedChangeListener;

    /* loaded from: classes2.dex */
    public interface OnCheckedChangeListener {
        void onCheckedChanged(XToggleLayout xToggleLayout, boolean z);

        boolean onInterceptClickCheck(XToggleLayout xToggleLayout);
    }

    public XToggleLayout(Context context) {
        this(context, null);
    }

    public XToggleLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.style.XToggleLayout_Fill);
    }

    public XToggleLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.XToggleLayout_Fill);
    }

    public XToggleLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mEnabled = true;
        init(context, attributeSet, i, i2);
    }

    private void init(Context context, AttributeSet attributeSet, int i, int i2) {
        setClickable(true);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XToggleLayout, i, i2);
        this.mChecked = obtainStyledAttributes.getBoolean(R.styleable.XToggleLayout_android_checked, false);
        boolean z = obtainStyledAttributes.getBoolean(R.styleable.XToggleLayout_android_enabled, true);
        this.mEnabled = z;
        super.setEnabled(z);
        this.mDisabledAlpha = obtainStyledAttributes.getFloat(R.styleable.XToggleLayout_android_disabledAlpha, 0.5f);
        setLoading(obtainStyledAttributes.getBoolean(R.styleable.XToggleLayout_loading, false));
        this.mBgId = obtainStyledAttributes.getResourceId(R.styleable.XToggleLayout_android_background, 0);
        obtainStyledAttributes.recycle();
        if (this.mXViewDelegate != null && this.mXViewDelegate.getThemeViewModel() != null) {
            this.mXViewDelegate.getThemeViewModel().setCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.xui.widget.toggle.-$$Lambda$Lc6vTh4DreXLqfHBskUcsDiYg5w
                @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
                public final void onThemeChanged() {
                    XToggleLayout.this.updateThemeResource();
                }
            });
        }
        setVuiElementType(VuiElementType.SWITCH);
    }

    public void setLoading(boolean z) {
        if (this.mLoading != z) {
            this.mLoading = z;
            if (z) {
                setEnabled(false);
                if (this.mLoadingDrawable == null) {
                    XLoadingDrawable xLoadingDrawable = new XLoadingDrawable();
                    this.mLoadingDrawable = xLoadingDrawable;
                    xLoadingDrawable.onConfigurationChanged(getContext(), null);
                    this.mLoadingDrawable.setCallback(this);
                    this.mLoadingDrawable.setType(1);
                    int measuredWidth = getMeasuredWidth();
                    int measuredHeight = getMeasuredHeight();
                    if (measuredWidth != 0 && measuredHeight != 0) {
                        this.mLoadingDrawable.setBounds(0, 0, measuredWidth, measuredHeight);
                    }
                }
                invalidate();
                return;
            }
            XLoadingDrawable xLoadingDrawable2 = this.mLoadingDrawable;
            if (xLoadingDrawable2 != null) {
                xLoadingDrawable2.cancelAnimations();
            }
            setEnabled(true);
        }
    }

    public boolean isLoading() {
        return this.mLoading;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        XLoadingDrawable xLoadingDrawable = this.mLoadingDrawable;
        if (xLoadingDrawable != null) {
            xLoadingDrawable.setBounds(0, 0, i, i2);
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateReferenceToIndicatorDrawable(getBackground());
        refreshChildrenState();
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        super.setBackground(drawable);
        updateReferenceToIndicatorDrawable(drawable);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mIndicatorDrawable;
        if (drawable != null) {
            drawable.setAlpha(isEnabled() ? 255 : (int) (this.mDisabledAlpha * 255.0f));
        }
    }

    private void refreshChildrenState() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof Checkable) {
                ((Checkable) childAt).setChecked(this.mChecked);
                childAt.setEnabled(this.mEnabled);
            }
        }
    }

    private void checkChildren() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof Checkable) {
                ((Checkable) childAt).setChecked(this.mChecked);
            }
        }
    }

    private void enableChildren() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof Checkable) {
                childAt.setEnabled(this.mEnabled);
            }
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mLoadingDrawable || super.verifyDrawable(drawable);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.mLoading) {
            XLoadingDrawable xLoadingDrawable = this.mLoadingDrawable;
            if (xLoadingDrawable != null) {
                xLoadingDrawable.draw(canvas);
                return;
            }
            return;
        }
        super.dispatchDraw(canvas);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    private void updateReferenceToIndicatorDrawable(Drawable drawable) {
        if (drawable instanceof LayerDrawable) {
            this.mIndicatorDrawable = ((LayerDrawable) drawable).findDrawableByLayerId(16908311);
        } else {
            this.mIndicatorDrawable = null;
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (this.mEnabled != z) {
            this.mEnabled = z;
            enableChildren();
        }
    }

    public void setChecked(boolean z) {
        if (this.mCheckStarting) {
            throw new IllegalStateException("Cannot change check state in onInterceptClickCheck");
        }
        if (this.mChecked != z) {
            this.mChecked = z;
            checkChildren();
            refreshDrawableState();
            if (this.mBroadcasting) {
                return;
            }
            this.mBroadcasting = true;
            OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(this, this.mChecked);
            }
            this.mBroadcasting = false;
            updateVui(this);
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        this.mCheckStarting = true;
        OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
        boolean onInterceptClickCheck = onCheckedChangeListener != null ? onCheckedChangeListener.onInterceptClickCheck(this) : false;
        this.mCheckStarting = false;
        if (!onInterceptClickCheck) {
            toggle();
        }
        boolean performClick = super.performClick();
        if (!performClick) {
            playSoundEffect(0);
        }
        return performClick;
    }

    @Override // android.widget.Checkable
    public boolean isChecked() {
        return this.mChecked;
    }

    @Override // android.widget.Checkable
    public void toggle() {
        setChecked(!this.mChecked);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        XLoadingDrawable xLoadingDrawable = this.mLoadingDrawable;
        if (xLoadingDrawable != null) {
            xLoadingDrawable.onConfigurationChanged(getContext(), configuration);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        XLoadingDrawable xLoadingDrawable = this.mLoadingDrawable;
        if (xLoadingDrawable != null) {
            xLoadingDrawable.onConfigurationChanged(getContext(), getResources().getConfiguration());
            this.mLoadingDrawable.cancelAnimations();
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        XLoadingDrawable xLoadingDrawable = this.mLoadingDrawable;
        if (xLoadingDrawable != null) {
            xLoadingDrawable.cancelAnimations();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateThemeResource() {
        setBackground(getContext().getDrawable(this.mBgId));
        refreshDrawableState();
    }
}
