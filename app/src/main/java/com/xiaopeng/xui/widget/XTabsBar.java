package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.vui.commons.IVuiElementBuilder;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.VuiMode;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.utils.XTouchAreaUtils;
import com.xiaopeng.xui.widget.XTabLayout;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class XTabsBar extends XRelativeLayout implements IVuiElementListener {
    private XImageButton mIconBtn;
    private ViewGroup mIconBtnLay;
    private OnTabsBarClickListener mOnTabChangeListener;
    private XTabLayout mTabLayout;

    /* loaded from: classes2.dex */
    public interface OnTabsBarClickListener extends XTabLayout.OnTabChangeListener {
        void onTabsBarCloseClick(View view);
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(View view, VuiEvent vuiEvent) {
        return false;
    }

    public XTabsBar(Context context) {
        this(context, null);
    }

    public XTabsBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XTabsBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public XTabsBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initView(context, attributeSet, i, i2);
    }

    private void initView(Context context, AttributeSet attributeSet, int i, int i2) {
        Resources.Theme theme = getContext().getTheme();
        if (i == 0) {
            i = R.style.XTabsBarAppearance_Left;
        }
        TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, R.styleable.XTabsBar, i, i);
        float dimension = obtainStyledAttributes.getDimension(R.styleable.XTabsBar_tabsBarMarginStart, dp(24));
        float dimension2 = obtainStyledAttributes.getDimension(R.styleable.XTabsBar_tabsBarTabGap, 0.0f);
        float dimension3 = obtainStyledAttributes.getDimension(R.styleable.XTabsBar_tabsBarTabWidth, dp(ThemeManager.UI_MODE_THEME_MASK));
        float dimension4 = obtainStyledAttributes.getDimension(R.styleable.XTabsBar_tabsBarTabHeight, dp(130));
        int integer = obtainStyledAttributes.getInteger(R.styleable.XTabsBar_tabsBarBtnVisibility, 0);
        int integer2 = obtainStyledAttributes.getInteger(R.styleable.XTabsBar_tabsBarTitleVisibility, 8);
        obtainStyledAttributes.recycle();
        LayoutInflater.from(context).inflate(R.layout.x_tabsbar, this);
        XImageButton xImageButton = (XImageButton) findViewById(R.id.x_tabsbar_btn_close);
        this.mIconBtn = xImageButton;
        xImageButton.setVisibility(integer);
        this.mIconBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xui.widget.-$$Lambda$XTabsBar$wa3GlpUsqn3S9cujDWCPGPDbLYk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                XTabsBar.this.lambda$initView$0$XTabsBar(view);
            }
        });
        this.mIconBtnLay = (ViewGroup) findViewById(R.id.x_x_tabsbar_btn_close_lay);
        ((XTextView) findViewById(R.id.x_tabsbar_tv_title)).setVisibility(integer2);
        XTabLayout xTabLayout = new XTabLayout(context, attributeSet, i, i2);
        this.mTabLayout = xTabLayout;
        int tabCount = xTabLayout.getTabCount();
        int i3 = (int) ((dimension3 * tabCount) + (dimension2 * (tabCount - 1)));
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (tabCount == 0) {
            i3 = (int) (displayMetrics.widthPixels - dimension);
        }
        int i4 = getResources().getConfiguration().orientation;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i3, (int) dimension4);
        if (i4 == 1) {
            layoutParams.addRule(14);
        } else {
            layoutParams.addRule(9);
            layoutParams.leftMargin = (int) dimension;
        }
        addView(this.mTabLayout, layoutParams);
        this.mTabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListener() { // from class: com.xiaopeng.xui.widget.XTabsBar.1
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout xTabLayout2, int i5, boolean z, boolean z2) {
                if (XTabsBar.this.mOnTabChangeListener != null) {
                    return XTabsBar.this.mOnTabChangeListener.onInterceptTabChange(xTabLayout2, i5, z, z2);
                }
                return false;
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeStart(XTabLayout xTabLayout2, int i5, boolean z, boolean z2) {
                if (XTabsBar.this.mOnTabChangeListener != null) {
                    XTabsBar.this.mOnTabChangeListener.onTabChangeStart(xTabLayout2, i5, z, z2);
                }
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout2, int i5, boolean z, boolean z2) {
                if (XTabsBar.this.mOnTabChangeListener != null) {
                    XTabsBar.this.mOnTabChangeListener.onTabChangeEnd(xTabLayout2, i5, z, z2);
                }
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$XTabsBar(View view) {
        OnTabsBarClickListener onTabsBarClickListener = this.mOnTabChangeListener;
        if (onTabsBarClickListener != null) {
            onTabsBarClickListener.onTabsBarCloseClick(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        XTouchAreaUtils.extendTouchAreaAsParentSameSize(this.mIconBtn, this.mIconBtnLay);
    }

    protected int dp(int i) {
        return (int) TypedValue.applyDimension(1, i, getResources().getDisplayMetrics());
    }

    public void setOnTabsClickListener(OnTabsBarClickListener onTabsBarClickListener) {
        this.mOnTabChangeListener = onTabsBarClickListener;
    }

    public int getTabCount() {
        XTabLayout xTabLayout = this.mTabLayout;
        if (xTabLayout != null) {
            return xTabLayout.getTabCount();
        }
        return 0;
    }

    public void addTab(String str) {
        XTabLayout xTabLayout = this.mTabLayout;
        if (xTabLayout != null) {
            xTabLayout.addTab(str);
        }
    }

    public void addTab(String str, int i) {
        XTabLayout xTabLayout = this.mTabLayout;
        if (xTabLayout != null) {
            xTabLayout.addTab(str, i);
        }
    }

    public void updateTabTitle(int i, String str) {
        XTabLayout xTabLayout = this.mTabLayout;
        if (xTabLayout != null) {
            xTabLayout.updateTabTitle(i, str);
        }
    }

    public void removeTab(int i) {
        XTabLayout xTabLayout = this.mTabLayout;
        if (xTabLayout != null) {
            xTabLayout.removeTab(i);
        }
    }

    public void removeTab(int i, int i2) {
        XTabLayout xTabLayout = this.mTabLayout;
        if (xTabLayout != null) {
            xTabLayout.removeTab(i, i2);
        }
    }

    public CharSequence getTabTitle(int i) {
        XTabLayout xTabLayout = this.mTabLayout;
        return xTabLayout != null ? xTabLayout.getTabTitle(i) : "";
    }

    public void setTabLayoutVisible(boolean z) {
        XTabLayout xTabLayout = this.mTabLayout;
        if (xTabLayout != null) {
            xTabLayout.setVisibility(z ? 0 : 8);
        }
    }

    public void selectTab(int i, boolean z) {
        XTabLayout xTabLayout = this.mTabLayout;
        if (xTabLayout != null) {
            xTabLayout.selectTab(i, z);
        }
    }

    public void selectTab(int i) {
        XTabLayout xTabLayout = this.mTabLayout;
        if (xTabLayout != null) {
            xTabLayout.selectTab(i, true);
        }
    }

    public int getSelectedTabIndex() {
        XTabLayout xTabLayout = this.mTabLayout;
        if (xTabLayout != null) {
            return xTabLayout.getSelectedTabIndex();
        }
        return -1;
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        XTabLayout xTabLayout = this.mTabLayout;
        if (xTabLayout != null) {
            xTabLayout.setEnabled(z);
        }
    }

    @Override // android.view.View
    public boolean isEnabled() {
        XTabLayout xTabLayout = this.mTabLayout;
        return xTabLayout != null && xTabLayout.isEnabled();
    }

    public void setEnabled(boolean z, int i) {
        XTabLayout xTabLayout = this.mTabLayout;
        if (xTabLayout != null) {
            xTabLayout.setEnabled(z, i);
        }
    }

    public boolean isEnabled(int i) {
        XTabLayout xTabLayout = this.mTabLayout;
        return xTabLayout != null && xTabLayout.isEnabled(i);
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public VuiElement onBuildVuiElement(String str, IVuiElementBuilder iVuiElementBuilder) {
        try {
            this.mTabLayout.setVuiElementType(VuiElementType.XTABLAYOUT);
            this.mTabLayout.setVuiMode(VuiMode.NORMAL);
            if (this.mIconBtn.getVisibility() != 0) {
                this.mIconBtn.setVuiMode(VuiMode.DISABLED);
            } else {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(VuiConstants.PROPS_GENERALACT, VuiConstants.GENERALACT_CLOSE);
                this.mIconBtn.setVuiProps(jSONObject);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }
}
