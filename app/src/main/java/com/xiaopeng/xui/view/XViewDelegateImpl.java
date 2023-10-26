package com.xiaopeng.xui.view;

import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xui.Xui;
import com.xiaopeng.xui.view.XViewDelegate;
import com.xiaopeng.xui.view.delegate.XViewDelegatePart;
import com.xiaopeng.xui.view.font.XFontChangeMonitor;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
class XViewDelegateImpl extends XViewDelegate {
    private ThemeViewModel mThemeViewModel;
    private View mView;
    private XFontChangeMonitor mXFontChangeMonitor;
    private ArrayList<XViewDelegatePart> mXViewDelegateParts = new ArrayList<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    public XViewDelegateImpl(View view, AttributeSet attributeSet, int i, int i2, Object obj) {
        XViewDelegatePart createFont;
        this.mView = view;
        if (Xui.isFontScaleDynamicChangeEnable() && (view instanceof TextView) && (createFont = XViewDelegatePart.createFont((TextView) view, attributeSet, i, i2)) != null) {
            this.mXViewDelegateParts.add(createFont);
        }
        if (view.isInEditMode()) {
            return;
        }
        this.mThemeViewModel = ThemeViewModel.create(view.getContext(), attributeSet, i, i2, obj);
    }

    @Override // com.xiaopeng.xui.view.XViewDelegate
    public void onConfigurationChanged(Configuration configuration) {
        Iterator<XViewDelegatePart> it = this.mXViewDelegateParts.iterator();
        while (it.hasNext()) {
            it.next().onConfigurationChanged(configuration);
        }
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onConfigurationChanged(this.mView, configuration);
        }
        XFontChangeMonitor xFontChangeMonitor = this.mXFontChangeMonitor;
        if (xFontChangeMonitor != null) {
            xFontChangeMonitor.onConfigurationChanged(configuration);
        }
    }

    @Override // com.xiaopeng.xui.view.XViewDelegate
    public void onAttachedToWindow() {
        Iterator<XViewDelegatePart> it = this.mXViewDelegateParts.iterator();
        while (it.hasNext()) {
            it.next().onAttachedToWindow();
        }
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onAttachedToWindow(this.mView);
        }
        XFontChangeMonitor xFontChangeMonitor = this.mXFontChangeMonitor;
        if (xFontChangeMonitor != null) {
            xFontChangeMonitor.onAttachedToWindow();
        }
    }

    @Override // com.xiaopeng.xui.view.XViewDelegate
    public void onDetachedFromWindow() {
        Iterator<XViewDelegatePart> it = this.mXViewDelegateParts.iterator();
        while (it.hasNext()) {
            it.next().onDetachedFromWindow();
        }
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onDetachedFromWindow(this.mView);
        }
    }

    @Override // com.xiaopeng.xui.view.XViewDelegate
    public void onWindowFocusChanged(boolean z) {
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onWindowFocusChanged(this.mView, z);
        }
    }

    @Override // com.xiaopeng.xui.view.XViewDelegate
    public void setFontScaleChangeCallback(XViewDelegate.onFontScaleChangeCallback onfontscalechangecallback) {
        if (onfontscalechangecallback != null && this.mXFontChangeMonitor == null) {
            this.mXFontChangeMonitor = new XFontChangeMonitor(this.mView.getContext());
        }
        this.mXFontChangeMonitor.setOnFontScaleChangeCallback(onfontscalechangecallback);
    }

    @Override // com.xiaopeng.xui.view.XViewDelegate
    public ThemeViewModel getThemeViewModel() {
        return this.mThemeViewModel;
    }
}
