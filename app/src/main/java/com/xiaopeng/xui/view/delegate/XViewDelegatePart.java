package com.xiaopeng.xui.view.delegate;

import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.TextView;

/* loaded from: classes2.dex */
public abstract class XViewDelegatePart {
    public abstract void onAttachedToWindow();

    public abstract void onConfigurationChanged(Configuration configuration);

    public abstract void onDetachedFromWindow();

    public static XViewDelegatePart createFont(TextView textView, AttributeSet attributeSet, int i, int i2) {
        return XViewDelegateFontScale.create(textView, attributeSet, i, i2);
    }
}
