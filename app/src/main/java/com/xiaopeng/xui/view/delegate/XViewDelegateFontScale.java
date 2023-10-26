package com.xiaopeng.xui.view.delegate;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;
import com.xiaopeng.xpui.R;

/* loaded from: classes2.dex */
public class XViewDelegateFontScale extends XViewDelegatePart {
    private float mComplexToFloat;
    private float mFontScale;
    private TextView mTextView;

    @Override // com.xiaopeng.xui.view.delegate.XViewDelegatePart
    public void onDetachedFromWindow() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static XViewDelegateFontScale create(TextView textView, AttributeSet attributeSet, int i, int i2) {
        int i3;
        boolean z;
        if (attributeSet == null) {
            return null;
        }
        Resources.Theme theme = textView.getContext().getTheme();
        TypedArray obtainStyledAttributes = textView.getContext().obtainStyledAttributes(attributeSet, R.styleable.XFontSize, i, i2);
        boolean z2 = obtainStyledAttributes.getBoolean(R.styleable.XFontSize_x_dynamic_font_scale_change_enable, true);
        obtainStyledAttributes.recycle();
        if (z2) {
            float f = 0.0f;
            TypedArray obtainStyledAttributes2 = theme.obtainStyledAttributes(attributeSet, new int[]{16842901}, i, i2);
            if (obtainStyledAttributes2.hasValue(0)) {
                TypedValue typedValue = new TypedValue();
                obtainStyledAttributes2.getValue(0, typedValue);
                i3 = typedValue.getComplexUnit();
                f = TypedValue.complexToFloat(typedValue.data);
                z = true;
            } else {
                i3 = 0;
                z = false;
            }
            obtainStyledAttributes2.recycle();
            if (!z) {
                TypedArray obtainStyledAttributes3 = theme.obtainStyledAttributes(attributeSet, new int[]{16842804}, i, i2);
                int resourceId = obtainStyledAttributes3.getResourceId(0, -1);
                obtainStyledAttributes3.recycle();
                TypedArray obtainStyledAttributes4 = resourceId != -1 ? theme.obtainStyledAttributes(resourceId, new int[]{16842901}) : null;
                if (obtainStyledAttributes4 != null) {
                    if (obtainStyledAttributes4.hasValue(0)) {
                        TypedValue typedValue2 = new TypedValue();
                        obtainStyledAttributes4.getValue(0, typedValue2);
                        int complexUnit = typedValue2.getComplexUnit();
                        f = TypedValue.complexToFloat(typedValue2.data);
                        i3 = complexUnit;
                    }
                    obtainStyledAttributes4.recycle();
                }
            }
            if (i3 == 2) {
                return new XViewDelegateFontScale(textView, f);
            }
            return null;
        }
        return null;
    }

    private XViewDelegateFontScale(TextView textView, float f) {
        this.mTextView = textView;
        this.mComplexToFloat = f;
        this.mFontScale = textView.getContext().getResources().getConfiguration().fontScale;
    }

    @Override // com.xiaopeng.xui.view.delegate.XViewDelegatePart
    public void onConfigurationChanged(Configuration configuration) {
        checkFont(configuration, "onConfigurationChanged");
    }

    private void checkFont(Configuration configuration, String str) {
        if (this.mFontScale != configuration.fontScale) {
            this.mFontScale = configuration.fontScale;
            this.mTextView.setTextSize(this.mComplexToFloat);
        }
    }

    @Override // com.xiaopeng.xui.view.delegate.XViewDelegatePart
    public void onAttachedToWindow() {
        checkFont(this.mTextView.getResources().getConfiguration(), "onAttachedToWindow");
    }
}
