package com.xiaopeng.xui.widget.pageindicator;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.widget.XImageView;

/* loaded from: classes2.dex */
public class XDotPagerIndicator extends XViewPagerIndicatorAbs {
    private int mDotSize;

    public XDotPagerIndicator(Context context) {
        super(context);
    }

    public XDotPagerIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public XDotPagerIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public XDotPagerIndicator(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.pageindicator.XViewPagerIndicatorAbs
    public void init() {
        super.init();
        this.mDotSize = getContext().getResources().getDimensionPixelSize(R.dimen.x_pager_indicator_view_size);
        int dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(R.dimen.x_pager_indicator_dot_padding);
        setPadding(dimensionPixelOffset, 0, dimensionPixelOffset, 0);
    }

    @Override // com.xiaopeng.xui.widget.pageindicator.XViewPagerIndicatorAbs
    public void populateData(int i) {
        if (i > 0) {
            removeAllViews();
            for (int i2 = 0; i2 < i; i2++) {
                View generateIndicator = generateIndicator();
                int i3 = this.mDotSize;
                addView(generateIndicator, new LinearLayout.LayoutParams(i3, i3));
            }
        }
    }

    protected View generateIndicator() {
        XImageView xImageView = new XImageView(getContext());
        xImageView.setImageResource(R.drawable.x_dot_indicator_bg);
        xImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return xImageView;
    }

    @Override // com.xiaopeng.xui.widget.pageindicator.XViewPagerIndicatorAbs
    public void select(int i) {
        int childCount = getChildCount();
        if (childCount <= 0 || i >= childCount) {
            return;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (i2 == i) {
                childAt.setActivated(true);
            } else {
                childAt.setActivated(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (ThemeManager.isThemeChanged(configuration)) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (childAt instanceof ImageView) {
                    ((ImageView) childAt).setImageResource(R.drawable.x_dot_indicator_bg);
                }
            }
        }
    }
}
