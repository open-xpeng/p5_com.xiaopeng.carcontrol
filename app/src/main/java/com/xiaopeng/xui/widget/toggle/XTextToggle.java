package com.xiaopeng.xui.widget.toggle;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.view.XViewDelegate;
import com.xiaopeng.xui.view.font.XFontScaleHelper;

@Deprecated
/* loaded from: classes2.dex */
public class XTextToggle extends XToggleLayout {
    private CheckedTextView mContentTextView;
    private int mDrawableStartResId;
    private int mTextColorResId;
    private CharSequence mTextOff;
    private CharSequence mTextOn;

    public XTextToggle(Context context) {
        this(context, null);
    }

    public XTextToggle(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.style.XToggleLayout_Fill_TextToggle);
    }

    public XTextToggle(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.XToggleLayout_Fill_TextToggle);
    }

    public XTextToggle(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.XTextToggle, i, i2);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XTextToggle_android_textSize, 15);
        final XFontScaleHelper create = XFontScaleHelper.create(obtainStyledAttributes, R.styleable.XTextToggle_android_textSize);
        if (create != null && this.mXViewDelegate != null) {
            this.mXViewDelegate.setFontScaleChangeCallback(new XViewDelegate.onFontScaleChangeCallback() { // from class: com.xiaopeng.xui.widget.toggle.-$$Lambda$XTextToggle$zUrvniJ7bYZBRRS9K2uc9YUsOKU
                @Override // com.xiaopeng.xui.view.XViewDelegate.onFontScaleChangeCallback
                public final void onFontScaleChanged() {
                    XTextToggle.this.lambda$new$0$XTextToggle(create);
                }
            });
        }
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.XTextToggle_android_textColor);
        this.mTextColorResId = obtainStyledAttributes.getResourceId(R.styleable.XTextToggle_android_textColor, 0);
        CheckedTextView checkedTextView = new CheckedTextView(context);
        this.mContentTextView = checkedTextView;
        checkedTextView.setTextColor(colorStateList);
        this.mContentTextView.setTextSize(0, dimensionPixelSize);
        this.mContentTextView.setGravity(16);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        addView(this.mContentTextView, layoutParams);
        Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.XTextToggle_android_drawableStart);
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XTextToggle_android_drawablePadding, 0);
        if (drawable != null) {
            this.mContentTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
            this.mContentTextView.setCompoundDrawablePadding(dimensionPixelSize2);
        }
        this.mDrawableStartResId = obtainStyledAttributes.getResourceId(R.styleable.XTextToggle_android_drawableStart, 0);
        this.mTextOn = obtainStyledAttributes.getText(R.styleable.XTextToggle_android_textOn);
        this.mTextOff = obtainStyledAttributes.getText(R.styleable.XTextToggle_android_textOff);
        syncTextState();
        obtainStyledAttributes.recycle();
    }

    public /* synthetic */ void lambda$new$0$XTextToggle(XFontScaleHelper xFontScaleHelper) {
        xFontScaleHelper.refreshTextSize(this.mContentTextView);
    }

    public CharSequence getTextOn() {
        return this.mTextOn;
    }

    public void setTextOn(CharSequence charSequence) {
        this.mTextOn = charSequence;
    }

    public CharSequence getTextOff() {
        return this.mTextOff;
    }

    public void setTextOff(CharSequence charSequence) {
        this.mTextOff = charSequence;
    }

    @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout, android.widget.Checkable
    public void setChecked(boolean z) {
        super.setChecked(z);
        syncTextState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout
    public void updateThemeResource() {
        if (this.mTextColorResId != 0) {
            this.mContentTextView.setTextColor(getContext().getColorStateList(this.mTextColorResId));
        }
        if (this.mDrawableStartResId != 0) {
            this.mContentTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(getContext().getDrawable(this.mDrawableStartResId), (Drawable) null, (Drawable) null, (Drawable) null);
        }
        super.updateThemeResource();
    }

    private void syncTextState() {
        CharSequence charSequence;
        CharSequence charSequence2;
        boolean isChecked = isChecked();
        if (isChecked && (charSequence2 = this.mTextOn) != null) {
            setText(charSequence2);
        } else if (isChecked || (charSequence = this.mTextOff) == null) {
        } else {
            setText(charSequence);
        }
    }

    public void setText(CharSequence charSequence) {
        CheckedTextView checkedTextView = this.mContentTextView;
        if (checkedTextView != null) {
            checkedTextView.setText(charSequence);
        }
    }
}
