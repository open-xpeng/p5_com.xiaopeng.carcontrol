package com.xiaopeng.libtheme;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;

/* loaded from: classes2.dex */
public class TextView extends android.widget.TextView {
    private static final String TAG = "XTextView";
    private ThemeViewModel mThemeViewModel;

    public TextView(Context context) {
        this(context, null);
    }

    public TextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mThemeViewModel = ThemeViewModel.create(context, attributeSet, i, i2);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mThemeViewModel.onConfigurationChanged(this, configuration);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mThemeViewModel.onAttachedToWindow(this);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mThemeViewModel.onDetachedFromWindow(this);
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
    }

    @Override // android.widget.TextView, android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        this.mThemeViewModel.setBackgroundResource(i);
    }

    @Override // android.widget.TextView
    public void setTextColor(int i) {
        super.setTextColor(i);
    }
}
