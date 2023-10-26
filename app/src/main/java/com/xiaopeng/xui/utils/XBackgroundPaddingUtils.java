package com.xiaopeng.xui.utils;

import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import android.view.View;
import com.xiaopeng.xpui.R;

/* loaded from: classes2.dex */
public class XBackgroundPaddingUtils {
    public static Rect backgroundPadding(View view, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = view.getContext().obtainStyledAttributes(attributeSet, R.styleable.XBackgroundPadding);
        if (obtainStyledAttributes.getBoolean(R.styleable.XBackgroundPadding_x_bg_padding_enable, false)) {
            int dimensionPixelSize = obtainStyledAttributes.hasValue(R.styleable.XBackgroundPadding_x_bg_padding) ? obtainStyledAttributes.getDimensionPixelSize(R.styleable.XBackgroundPadding_x_bg_padding, -1) : -1;
            int i = dimensionPixelSize;
            int i2 = i;
            int i3 = i2;
            if (obtainStyledAttributes.hasValue(R.styleable.XBackgroundPadding_x_bg_padding_left)) {
                dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XBackgroundPadding_x_bg_padding_left, -1);
            }
            if (obtainStyledAttributes.hasValue(R.styleable.XBackgroundPadding_x_bg_padding_right)) {
                i = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XBackgroundPadding_x_bg_padding_right, -1);
            }
            if (obtainStyledAttributes.hasValue(R.styleable.XBackgroundPadding_x_bg_padding_top)) {
                i2 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XBackgroundPadding_x_bg_padding_top, -1);
            }
            if (obtainStyledAttributes.hasValue(R.styleable.XBackgroundPadding_x_bg_padding_bottom)) {
                i3 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XBackgroundPadding_x_bg_padding_bottom, -1);
            }
            obtainStyledAttributes.recycle();
            return backgroundPadding(view, dimensionPixelSize, i2, i, i3);
        }
        obtainStyledAttributes.recycle();
        return null;
    }

    public static Rect backgroundPadding(View view, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        Drawable drawable;
        int i8;
        String str;
        int i9;
        int i10;
        int i11;
        int i12;
        log(String.format("insetLeft %s, insetTop %s, insetRight %s, insetBottom %s, ", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)));
        Drawable background = view.getBackground();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        log(String.format("paddingLeft %s, paddingRight %s, paddingTop %s, paddingBottom %s, ", Integer.valueOf(paddingLeft), Integer.valueOf(paddingRight), Integer.valueOf(paddingTop), Integer.valueOf(paddingBottom)));
        if (background != null) {
            if (background instanceof InsetDrawable) {
                InsetDrawable insetDrawable = (InsetDrawable) background;
                drawable = insetDrawable.getDrawable();
                Rect rect = new Rect();
                insetDrawable.getPadding(rect);
                i5 = i;
                if (i5 == -1) {
                    i5 = rect.left;
                }
                i6 = i2;
                if (i6 == -1) {
                    i6 = rect.top;
                }
                i7 = i3;
                if (i7 == -1) {
                    i7 = rect.right;
                }
                i8 = i4 == -1 ? rect.bottom : i4;
                i11 = i5 - rect.left;
                i10 = i6 - rect.top;
                i9 = i7 - rect.right;
                i12 = i8 - rect.bottom;
                str = "paddingLeft %s, paddingRight %s, paddingTop %s, paddingBottom %s, ";
            } else {
                i5 = i;
                i6 = i2;
                i7 = i3;
                drawable = background;
                i8 = i4;
                str = "paddingLeft %s, paddingRight %s, paddingTop %s, paddingBottom %s, ";
                i9 = 0;
                i10 = 0;
                i11 = 0;
                i12 = 0;
            }
            log(String.format("paddingLeftOffset %s, paddingTopOffset %s, paddingRightOffset %s, paddingBottomOffset %s, ", Integer.valueOf(i11), Integer.valueOf(i10), Integer.valueOf(i9), Integer.valueOf(i12)));
            InsetDrawable insetDrawable2 = new InsetDrawable(drawable, i5, i6, i7, i8);
            view.setBackground(insetDrawable2);
            insetDrawable2.setDrawable(drawable);
            view.setPadding(paddingLeft + i11, paddingTop + i10, paddingRight + i9, paddingBottom + i12);
            log(String.format(str, Integer.valueOf(view.getPaddingLeft()), Integer.valueOf(view.getPaddingRight()), Integer.valueOf(view.getPaddingTop()), Integer.valueOf(view.getPaddingBottom())));
            return new Rect(i5, i6, i7, i8);
        }
        return null;
    }

    private static void log(String str) {
        XLogUtils.d("xpui-bgPadding", str);
    }
}
