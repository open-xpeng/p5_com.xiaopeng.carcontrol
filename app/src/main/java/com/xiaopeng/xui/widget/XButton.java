package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xui.utils.XBackgroundPaddingUtils;
import com.xiaopeng.xui.view.XViewDelegate;
import com.xiaopeng.xui.vui.VuiView;

/* loaded from: classes2.dex */
public class XButton extends AppCompatButton implements VuiView {
    private Rect mRectBgPadding;
    protected XViewDelegate mXViewDelegate;

    public XButton(Context context) {
        super(context);
    }

    public XButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mXViewDelegate = XViewDelegate.create(this, attributeSet);
        init(attributeSet);
    }

    public XButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mXViewDelegate = XViewDelegate.create(this, attributeSet, i);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        initVui(this, attributeSet);
        this.mRectBgPadding = XBackgroundPaddingUtils.backgroundPadding(this, attributeSet);
        XViewDelegate xViewDelegate = this.mXViewDelegate;
        if (xViewDelegate == null || xViewDelegate.getThemeViewModel() == null) {
            return;
        }
        this.mXViewDelegate.getThemeViewModel().setCallback(new ThemeViewModel.OnCallback() { // from class: com.xiaopeng.xui.widget.-$$Lambda$XButton$k8D9nKEJdkYk-jEyQ_ngt01WjPY
            @Override // com.xiaopeng.libtheme.ThemeViewModel.OnCallback
            public final void onThemeChanged() {
                XButton.this.lambda$init$1$XButton();
            }
        });
    }

    public /* synthetic */ void lambda$init$1$XButton() {
        post(new Runnable() { // from class: com.xiaopeng.xui.widget.-$$Lambda$XButton$lLwj4qsck8oY2mJva_KKpsvlBn4
            @Override // java.lang.Runnable
            public final void run() {
                XButton.this.lambda$null$0$XButton();
            }
        });
    }

    public /* synthetic */ void lambda$null$0$XButton() {
        if (this.mRectBgPadding != null) {
            logD("XButton change theme reset backgroundPadding");
            this.mRectBgPadding = XBackgroundPaddingUtils.backgroundPadding(this, this.mRectBgPadding.left, this.mRectBgPadding.top, this.mRectBgPadding.right, this.mRectBgPadding.bottom);
        }
    }

    public void backgroundPadding(int i, int i2, int i3, int i4) {
        this.mRectBgPadding = XBackgroundPaddingUtils.backgroundPadding(this, i, i2, i3, i4);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        XViewDelegate xViewDelegate = this.mXViewDelegate;
        if (xViewDelegate != null) {
            xViewDelegate.onConfigurationChanged(configuration);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        XViewDelegate xViewDelegate = this.mXViewDelegate;
        if (xViewDelegate != null) {
            xViewDelegate.onAttachedToWindow();
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        XViewDelegate xViewDelegate = this.mXViewDelegate;
        if (xViewDelegate != null) {
            xViewDelegate.onDetachedFromWindow();
        }
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        setVuiVisibility(this, i);
    }

    @Override // android.widget.TextView, android.view.View
    public void setSelected(boolean z) {
        super.setSelected(z);
        setVuiSelected(this, z);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        releaseVui();
    }
}
