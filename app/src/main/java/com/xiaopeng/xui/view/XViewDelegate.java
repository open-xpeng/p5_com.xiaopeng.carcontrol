package com.xiaopeng.xui.view;

import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import com.xiaopeng.libtheme.ThemeViewModel;

/* loaded from: classes2.dex */
public abstract class XViewDelegate {

    /* loaded from: classes2.dex */
    public interface onFontScaleChangeCallback {
        void onFontScaleChanged();
    }

    public abstract ThemeViewModel getThemeViewModel();

    public abstract void onAttachedToWindow();

    public abstract void onConfigurationChanged(Configuration configuration);

    public abstract void onDetachedFromWindow();

    public abstract void onWindowFocusChanged(boolean z);

    public abstract void setFontScaleChangeCallback(onFontScaleChangeCallback onfontscalechangecallback);

    public static XViewDelegate create(View view, AttributeSet attributeSet) {
        return create(view, attributeSet, 0, 0);
    }

    public static XViewDelegate create(View view, AttributeSet attributeSet, int i) {
        return create(view, attributeSet, i, 0);
    }

    public static XViewDelegate create(View view, AttributeSet attributeSet, int i, int i2) {
        return new XViewDelegateImpl(view, attributeSet, i, i2, null);
    }

    public static XViewDelegate create(View view, AttributeSet attributeSet, int i, int i2, Object obj) {
        return new XViewDelegateImpl(view, attributeSet, i, i2, obj);
    }
}
