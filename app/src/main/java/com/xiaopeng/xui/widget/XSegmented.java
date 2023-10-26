package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaopeng.vui.commons.IVuiElementBuilder;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.drawable.XIndicatorDrawable;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.utils.XLogUtils;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;

/* loaded from: classes2.dex */
public class XSegmented extends XLinearLayout implements IVuiElementListener, View.OnClickListener {
    private static final String TAG = "XSegmented";
    private int[] mIconRes;
    private final XIndicatorDrawable mIndicatorDrawable;
    private final boolean mIsInit;
    private boolean mIsPlayingAnim;
    private SegmentListener mSegmentListener;
    private int mSelection;
    private int mTextColorRes;
    private float mTextSize;

    /* loaded from: classes2.dex */
    public interface SegmentListener {
        boolean onInterceptChange(XSegmented xSegmented, int i);

        void onSelectionChange(XSegmented xSegmented, int i, boolean z);
    }

    public XSegmented(Context context) {
        this(context, null);
    }

    public XSegmented(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XSegmented(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public XSegmented(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        XIndicatorDrawable xIndicatorDrawable = new XIndicatorDrawable();
        this.mIndicatorDrawable = xIndicatorDrawable;
        xIndicatorDrawable.inflateAttrs(getResources(), attributeSet, context.getTheme());
        xIndicatorDrawable.setCallback(this);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.XSegmented, i, i2);
        CharSequence[] textArray = obtainStyledAttributes.getTextArray(R.styleable.XSegmented_segment_vui_labels);
        int i3 = obtainStyledAttributes.getInt(R.styleable.XSegmented_segment_selected_pos, -1);
        if (obtainStyledAttributes.getBoolean(R.styleable.XSegmented_segment_anim_disable_click, false)) {
            xIndicatorDrawable.setOnAnimationListener(new XIndicatorDrawable.OnAnimationListener() { // from class: com.xiaopeng.xui.widget.XSegmented.1
                @Override // com.xiaopeng.xui.drawable.XIndicatorDrawable.OnAnimationListener
                public void onStart() {
                    XSegmented.this.mIsPlayingAnim = true;
                }

                @Override // com.xiaopeng.xui.drawable.XIndicatorDrawable.OnAnimationListener
                public void onEnd() {
                    XSegmented.this.mIsPlayingAnim = false;
                }
            });
        }
        if (obtainStyledAttributes.hasValue(R.styleable.XSegmented_segment_texts)) {
            this.mTextSize = obtainStyledAttributes.getDimension(R.styleable.XSegmented_segment_text_size, R.dimen.x_font_title_03_size);
            this.mTextColorRes = obtainStyledAttributes.getResourceId(R.styleable.XSegmented_segment_text_color, R.color.x_tabs_layout_title_color);
            addSegmentView(obtainStyledAttributes.getTextArray(R.styleable.XSegmented_segment_texts), textArray);
        } else {
            TypedArray obtainTypedArray = getResources().obtainTypedArray(obtainStyledAttributes.getResourceId(R.styleable.XSegmented_segment_icons, 0));
            this.mIconRes = new int[obtainTypedArray.length()];
            for (int i4 = 0; i4 < obtainTypedArray.length(); i4++) {
                this.mIconRes[i4] = obtainTypedArray.getResourceId(i4, 0);
            }
            obtainTypedArray.recycle();
            addSegmentView(this.mIconRes, textArray);
        }
        obtainStyledAttributes.recycle();
        setSelection(i3, false, false, false);
        setGravity(17);
        setWillNotDraw(false);
        this.mIsInit = true;
    }

    public void addSegmentView(CharSequence[] charSequenceArr, CharSequence[] charSequenceArr2) {
        if (charSequenceArr != null) {
            int i = 0;
            while (i < charSequenceArr.length) {
                addSegmentView(charSequenceArr[i], (charSequenceArr2 == null || charSequenceArr2.length <= i) ? null : charSequenceArr2[i]);
                i++;
            }
        }
    }

    public void addSegmentView(CharSequence charSequence, CharSequence charSequence2) {
        addSegmentView(charSequence, charSequence2, getChildCount());
    }

    public void addSegmentView(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (charSequence != null) {
            XTextView xTextView = new XTextView(getContext());
            xTextView.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1.0f));
            xTextView.setGravity(17);
            xTextView.setText(charSequence);
            xTextView.setTextSize(this.mTextSize);
            xTextView.setTextColor(getContext().getColorStateList(this.mTextColorRes));
            if (charSequence2 != null) {
                xTextView.setVuiLabel(charSequence2.toString());
            }
            xTextView.setVuiElementType(VuiElementType.TEXTVIEW);
            xTextView.setSoundEffectsEnabled(isSoundEffectsEnabled());
            xTextView.setOnClickListener(this);
            addView(xTextView, i);
            int i2 = this.mSelection;
            if (i <= i2) {
                i2++;
            }
            setSelection(i2, true, this.mIsInit);
        }
    }

    public void setTitleTextSize(float f) {
        this.mTextSize = f;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof TextView) {
                ((TextView) childAt).setTextSize(f);
            }
        }
    }

    public void setTitleTextColor(int i) {
        this.mTextColorRes = i;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof TextView) {
                ((TextView) childAt).setTextColor(getContext().getColorStateList(i));
            }
        }
    }

    public void setTitleText(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (getChildCount() > i) {
            View childAt = getChildAt(i);
            if (childAt instanceof XTextView) {
                XTextView xTextView = (XTextView) childAt;
                xTextView.setText(charSequence);
                xTextView.setVuiLabel(charSequence2.toString());
                return;
            }
            XLogUtils.w(TAG, "setTitleText failed, view is not XTextView");
            return;
        }
        XLogUtils.w(TAG, "setTitleText failed, index > child count");
    }

    public void addSegmentView(int[] iArr, CharSequence[] charSequenceArr) {
        this.mIconRes = iArr;
        int i = 0;
        while (i < iArr.length) {
            addSegmentView(iArr[i], (charSequenceArr == null || charSequenceArr.length <= i) ? null : charSequenceArr[i]);
            i++;
        }
    }

    public void addSegmentView(int i, CharSequence charSequence) {
        addSegmentView(i, charSequence, getChildCount());
    }

    public void addSegmentView(int i, CharSequence charSequence, int i2) {
        if (this.mIconRes == null) {
            this.mIconRes = new int[0];
        }
        if (i2 < 0) {
            i2 = 0;
        }
        int[] iArr = this.mIconRes;
        if (i2 >= iArr.length) {
            int[] iArr2 = new int[iArr.length + 1];
            this.mIconRes = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            this.mIconRes[iArr.length] = i;
        } else if (i2 < getChildCount()) {
            int[] iArr3 = this.mIconRes;
            int[] iArr4 = new int[iArr3.length + 1];
            this.mIconRes = iArr4;
            System.arraycopy(iArr3, 0, iArr4, 0, i2);
            int[] iArr5 = this.mIconRes;
            iArr5[i2] = i;
            System.arraycopy(iArr3, i2, iArr5, i2 + 1, iArr3.length - i2);
        }
        XImageView xImageView = new XImageView(getContext());
        xImageView.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1.0f));
        if (i != 0) {
            xImageView.setImageDrawable(getContext().getDrawable(i));
        }
        xImageView.setScaleType(ImageView.ScaleType.CENTER);
        if (charSequence != null) {
            xImageView.setVuiLabel(charSequence.toString());
        }
        xImageView.setVuiElementType(VuiElementType.TEXTVIEW);
        xImageView.setSoundEffectsEnabled(isSoundEffectsEnabled());
        xImageView.setOnClickListener(this);
        addView(xImageView, i2);
        int i3 = this.mSelection;
        if (i2 <= i3) {
            i3++;
        }
        setSelection(i3, true, this.mIsInit);
    }

    public void setSegmentIcon(int i, CharSequence charSequence, int i2) {
        if (getChildCount() > i2) {
            View childAt = getChildAt(i2);
            if (childAt instanceof XImageView) {
                XImageView xImageView = (XImageView) childAt;
                xImageView.setImageDrawable(getContext().getDrawable(i));
                if (charSequence != null) {
                    xImageView.setVuiLabel(charSequence.toString());
                }
                int[] iArr = this.mIconRes;
                if (iArr == null || iArr.length <= i2) {
                    return;
                }
                iArr[i2] = i;
                return;
            }
            XLogUtils.w(TAG, "setSegmentIcon failed, view is not XImageView");
            return;
        }
        XLogUtils.w(TAG, "setSegmentIcon failed, index > child count");
    }

    public void removeSegment(int i) {
        int[] iArr;
        if (getChildCount() > i) {
            if ((getChildAt(i) instanceof ImageView) && (iArr = this.mIconRes) != null) {
                int[] iArr2 = new int[iArr.length - 1];
                this.mIconRes = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, i);
                int[] iArr3 = this.mIconRes;
                System.arraycopy(iArr, i + 1, iArr3, i, iArr3.length - i);
            }
            removeViewAt(i);
            int i2 = this.mSelection;
            if (i == i2) {
                i2 = -1;
            } else if (i < i2 || i2 >= getChildCount()) {
                i2--;
            }
            setSelection(i2, true, true);
        }
    }

    public void clearSegment() {
        removeAllViews();
        this.mIconRes = new int[0];
        setSelection(-1);
    }

    public void setSelection(int i) {
        setSelection(i, true, false, false);
    }

    private void setSelection(int i, boolean z, boolean z2) {
        setSelection(i, z, false, z2);
    }

    public void setSelection(int i, boolean z) {
        setSelection(i, z, false, false);
    }

    private void setSelection(int i, boolean z, boolean z2, boolean z3) {
        int i2;
        int i3 = this.mSelection;
        this.mSelection = i;
        if (i < 0 || i >= getChildCount()) {
            this.mSelection = -1;
        }
        int i4 = 0;
        while (i4 < getChildCount()) {
            getChildAt(i4).setSelected(this.mSelection == i4);
            i4++;
        }
        XIndicatorDrawable xIndicatorDrawable = this.mIndicatorDrawable;
        if (xIndicatorDrawable != null) {
            xIndicatorDrawable.setSelection(getChildCount(), this.mSelection, z);
        }
        changeIndicatorEnable();
        SegmentListener segmentListener = this.mSegmentListener;
        if (segmentListener != null && i3 != (i2 = this.mSelection)) {
            segmentListener.onSelectionChange(this, i2, z2);
        }
        if (i3 != this.mSelection || z3) {
            updateVui(this);
        }
    }

    public int getSelection() {
        return this.mSelection;
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        for (int i = 0; i < getChildCount(); i++) {
            setEnabled(z, i);
        }
    }

    public void setEnabled(boolean z, int i) {
        if (i < 0 || i >= getChildCount()) {
            return;
        }
        getChildAt(i).setEnabled(z);
        changeIndicatorEnable();
    }

    public boolean isEnable(int i) {
        if (i < 0 || i >= getChildCount()) {
            return false;
        }
        return getChildAt(i).isEnabled();
    }

    private void changeIndicatorEnable() {
        int i;
        if (this.mIndicatorDrawable == null || (i = this.mSelection) < 0 || i >= getChildCount()) {
            return;
        }
        this.mIndicatorDrawable.setEnable(getChildAt(this.mSelection).isEnabled());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (XThemeManager.isThemeChanged(configuration)) {
            setTitleTextColor(this.mTextColorRes);
            int[] iArr = this.mIconRes;
            if (iArr != null && iArr.length == getChildCount()) {
                int i = 0;
                while (true) {
                    int[] iArr2 = this.mIconRes;
                    if (i >= iArr2.length) {
                        break;
                    }
                    setSegmentIcon(iArr2[i], null, i);
                    i++;
                }
            }
            XIndicatorDrawable xIndicatorDrawable = this.mIndicatorDrawable;
            if (xIndicatorDrawable != null) {
                xIndicatorDrawable.onConfigurationChanged(getResources(), getContext().getTheme());
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        XIndicatorDrawable xIndicatorDrawable = this.mIndicatorDrawable;
        if (xIndicatorDrawable != null) {
            xIndicatorDrawable.setBounds(new Rect(i, i2, i3, i4));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        XIndicatorDrawable xIndicatorDrawable = this.mIndicatorDrawable;
        if (xIndicatorDrawable != null) {
            xIndicatorDrawable.setState(getDrawableState());
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        XIndicatorDrawable xIndicatorDrawable = this.mIndicatorDrawable;
        if (xIndicatorDrawable != null) {
            xIndicatorDrawable.draw(canvas);
        }
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        super.invalidateDrawable(drawable);
        if (drawable instanceof XIndicatorDrawable) {
            invalidate();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.mIsPlayingAnim) {
            return;
        }
        int indexOfChild = indexOfChild(view);
        SegmentListener segmentListener = this.mSegmentListener;
        if (segmentListener == null || !segmentListener.onInterceptChange(this, indexOfChild)) {
            setSelection(indexOfChild(view), true, true, false);
        }
    }

    public void setSegmentListener(SegmentListener segmentListener) {
        this.mSegmentListener = segmentListener;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public VuiElement onBuildVuiElement(String str, IVuiElementBuilder iVuiElementBuilder) {
        setVuiValue(Integer.valueOf(this.mSelection));
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof VuiView) {
                VuiView vuiView = (VuiView) childAt;
                vuiView.setVuiPosition(i);
                vuiView.setVuiElementId(str + "_" + i);
                XLogUtils.d(TAG, "onBuildVuiElement:" + str);
            }
        }
        return null;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(final View view, VuiEvent vuiEvent) {
        final Double d;
        XLogUtils.d("Segmented onVuiElementEvent");
        if (view == null || (d = (Double) vuiEvent.getEventValue(vuiEvent)) == null) {
            return false;
        }
        post(new Runnable() { // from class: com.xiaopeng.xui.widget.-$$Lambda$XSegmented$2IvK-NCSFAmIGekRO2KhWUfFhfg
            @Override // java.lang.Runnable
            public final void run() {
                XSegmented.this.lambda$onVuiElementEvent$0$XSegmented(d, view);
            }
        });
        return true;
    }

    public /* synthetic */ void lambda$onVuiElementEvent$0$XSegmented(Double d, View view) {
        if (d.intValue() < getChildCount()) {
            view = getChildAt(d.intValue());
        }
        VuiFloatingLayerManager.show(view);
        setPerformVuiAction(true);
        setSelection(d.intValue(), true, true, false);
        setPerformVuiAction(false);
    }
}
