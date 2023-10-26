package com.xiaopeng.xui.widget;

import android.content.Context;
import android.util.AttributeSet;

/* loaded from: classes2.dex */
public class XRadioButton extends XAppCompatRadioButton {
    public XRadioButton(Context context) {
        this(context, null);
    }

    public XRadioButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842878);
    }

    public XRadioButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setClickable(true);
    }
}
