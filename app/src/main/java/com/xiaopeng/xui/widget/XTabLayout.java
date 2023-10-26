package com.xiaopeng.xui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaopeng.vui.commons.IVuiElementBuilder;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.view.XViewDelegate;
import com.xiaopeng.xui.view.font.XFontScaleHelper;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;

@Deprecated
/* loaded from: classes2.dex */
public class XTabLayout extends XLinearLayout implements IVuiElementListener {
    private static final long DURATION = 300;
    private static final float INDICATOR_DAY_HEIGHT_PERCENT = 1.0f;
    private static final double K = 1.048d;
    private static final double K2 = 4.648d;
    public static final int STYLE_DAY = 1;
    public static final int STYLE_NIGHT = 2;
    private static final String TAG = "xpui-XTabLayout";
    private final int MARGIN_DAY;
    private Paint mBlurPaint;
    private Paint mBlurPaint2;
    private View.OnClickListener mChildClickListener;
    private int mCurrentEnd;
    private int mCurrentEnd2;
    private int mCurrentStart;
    private int mCurrentStart2;
    private float mDivideValue;
    private CharSequence[] mIconVuiLabels;
    private boolean mIndicatorAnimatorEnable;
    private int mIndicatorColor;
    private int mIndicatorColor2;
    private int mIndicatorColorFrom;
    private int mIndicatorColorTo;
    private boolean mIndicatorDayNightDiff;
    private float mIndicatorHeight;
    private float mIndicatorMarginBottom;
    private float mIndicatorMaxHeight;
    private float mIndicatorMinHeight;
    private int mIndicatorShadowColor;
    private int mIndicatorShadowColor2;
    private float mIndicatorShadowRadius;
    private float mIndicatorShadowRadius2;
    private float mIndicatorWidth;
    private float mIndicatorWidthPercent;
    private boolean mIsDetachedFromWindow;
    private boolean mIsDetachedNightTheme;
    private OnTabChangeListener mOnTabChangeListener;
    private int mPaddingNight;
    private Paint mPaint;
    private Paint mPaint2;
    private int mSelectTabIndex;
    private int mStyle;
    private boolean mTabCustomBackground;
    private boolean mTabsBarStyle;
    private int mTempEnd;
    private int mTempEnd2;
    private int mTempStart;
    private int mTempStart2;
    private int[] mTitleIcons;
    private CharSequence[] mTitleString;
    private int mTitleTextColorRes;
    private ColorStateList mTitleTextColorStateList;
    private float mTitleTextSize;
    private int mToEnd;
    private int mToEnd2;
    private int mToStart;
    private int mToStart2;
    private ValueAnimator mValueAnimator;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface OnMoveIndicatorListener {
        void onEnd();

        void onStart();
    }

    /* loaded from: classes2.dex */
    public interface OnTabChangeListener {
        boolean onInterceptTabChange(XTabLayout xTabLayout, int i, boolean z, boolean z2);

        void onTabChangeEnd(XTabLayout xTabLayout, int i, boolean z, boolean z2);

        void onTabChangeStart(XTabLayout xTabLayout, int i, boolean z, boolean z2);
    }

    /* loaded from: classes2.dex */
    public static abstract class OnTabChangeListenerAdapter implements OnTabChangeListener {
        @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
        public boolean onInterceptTabChange(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
            return false;
        }

        @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
        public void onTabChangeStart(XTabLayout xTabLayout, int i, boolean z, boolean z2) {
        }
    }

    public XTabLayout(Context context) {
        this(context, null);
    }

    public XTabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XTabLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public XTabLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2 == 0 ? R.style.XTabLayoutAppearance : i2);
        this.MARGIN_DAY = 0;
        this.mPaint = new Paint(1);
        this.mBlurPaint = new Paint(1);
        this.mPaint2 = new Paint(1);
        this.mBlurPaint2 = new Paint(1);
        this.mCurrentStart = 0;
        this.mCurrentEnd = 0;
        this.mCurrentStart2 = 0;
        this.mCurrentEnd2 = 0;
        this.mToStart = 0;
        this.mToStart2 = 0;
        this.mToEnd = 0;
        this.mToEnd2 = 0;
        this.mTempStart = 0;
        this.mTempStart2 = 0;
        this.mTempEnd = 0;
        this.mTempEnd2 = 0;
        this.mDivideValue = 0.6f;
        this.mSelectTabIndex = -1;
        this.mStyle = 2;
        this.mChildClickListener = new View.OnClickListener() { // from class: com.xiaopeng.xui.widget.XTabLayout.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (XTabLayout.this.mValueAnimator == null || !XTabLayout.this.mValueAnimator.isRunning()) {
                    XTabLayout xTabLayout = XTabLayout.this;
                    xTabLayout.selectTab(xTabLayout.indexOfChild(view), true, true);
                }
            }
        };
        this.mIsDetachedFromWindow = true;
        if (Build.VERSION.SDK_INT <= 26) {
            setLayerType(1, null);
        }
        getContext().getTheme();
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.XTabLayout, i == 0 ? R.style.XTabLayoutAppearance : i, R.style.XTabLayoutAppearance);
        this.mTitleString = obtainStyledAttributes.getTextArray(R.styleable.XTabLayout_titles);
        this.mTitleTextSize = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_titleTextSize, 35.0f);
        final XFontScaleHelper create = XFontScaleHelper.create(obtainStyledAttributes, R.styleable.XTabLayout_titleTextSize);
        if (create != null && this.mXViewDelegate != null) {
            this.mXViewDelegate.setFontScaleChangeCallback(new XViewDelegate.onFontScaleChangeCallback() { // from class: com.xiaopeng.xui.widget.-$$Lambda$XTabLayout$nB_0cTxsmLZBvThy4w6y7HxO3Xo
                @Override // com.xiaopeng.xui.view.XViewDelegate.onFontScaleChangeCallback
                public final void onFontScaleChanged() {
                    XTabLayout.this.lambda$new$0$XTabLayout(create);
                }
            });
        }
        this.mTitleTextColorStateList = obtainStyledAttributes.getColorStateList(R.styleable.XTabLayout_titleTextColorStateList);
        int integer = obtainStyledAttributes.getInteger(R.styleable.XTabLayout_selectTab, 0);
        this.mIndicatorWidth = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_indicatorWidth, 0.0f);
        this.mIndicatorWidthPercent = obtainStyledAttributes.getFraction(R.styleable.XTabLayout_indicatorWidthPercent, 1, 1, 0.7f);
        this.mIndicatorMaxHeight = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_indicatorMaxHeight, dp(4));
        this.mIndicatorMinHeight = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_indicatorMinHeight, dp(2));
        this.mIndicatorHeight = this.mIndicatorMaxHeight;
        this.mIndicatorMarginBottom = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_indicatorMarginBottom, dp(6));
        this.mIndicatorColor = obtainStyledAttributes.getResourceId(R.styleable.XTabLayout_indicatorColor, R.color.x_theme_primary_normal);
        this.mIndicatorColorFrom = obtainStyledAttributes.getColor(R.styleable.XTabLayout_indicatorColorFrom, -1);
        this.mIndicatorColorTo = obtainStyledAttributes.getColor(R.styleable.XTabLayout_indicatorColorTo, -1);
        this.mIndicatorShadowColor = obtainStyledAttributes.getColor(R.styleable.XTabLayout_indicatorShadowColor, -15880455);
        this.mIndicatorShadowRadius = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_indicatorShadowRadius, dp(4));
        this.mIndicatorColor2 = obtainStyledAttributes.getResourceId(R.styleable.XTabLayout_indicatorColor2, R.color.x_table_indicator_color);
        this.mIndicatorShadowColor2 = obtainStyledAttributes.getColor(R.styleable.XTabLayout_indicatorShadowColor2, -15880455);
        this.mIndicatorShadowRadius2 = obtainStyledAttributes.getDimension(R.styleable.XTabLayout_indicatorShadowRadius2, dp(4));
        this.mIndicatorAnimatorEnable = obtainStyledAttributes.getBoolean(R.styleable.XTabLayout_indicatorAnimatorEnable, true);
        this.mTitleTextColorRes = obtainStyledAttributes.getResourceId(R.styleable.XTabLayout_titleTextColorStateList, -1);
        this.mTabsBarStyle = obtainStyledAttributes.getBoolean(R.styleable.XTabLayout_tabsBarStyle, false);
        this.mTabCustomBackground = obtainStyledAttributes.getBoolean(R.styleable.XTabLayout_tabCustomBackground, false);
        this.mPaddingNight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XTabLayout_tabPaddingNight, dp(40));
        this.mIndicatorDayNightDiff = obtainStyledAttributes.getBoolean(R.styleable.XTabLayout_indicatorDayNightDiff, false);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.XTabLayout_tabLayoutIcons, 0);
        if (resourceId != 0) {
            TypedArray obtainTypedArray = getResources().obtainTypedArray(resourceId);
            this.mTitleIcons = new int[obtainTypedArray.length()];
            for (int i3 = 0; i3 < obtainTypedArray.length(); i3++) {
                this.mTitleIcons[i3] = obtainTypedArray.getResourceId(i3, 0);
            }
            obtainTypedArray.recycle();
        }
        this.mIconVuiLabels = obtainStyledAttributes.getTextArray(R.styleable.XTabLayout_tabLayoutVuiLabels);
        setGravity(17);
        obtainStyledAttributes.recycle();
        setWillNotDraw(false);
        init();
        selectTab(integer, false, false);
        setStyle(isNight() ? 2 : 1);
    }

    public /* synthetic */ void lambda$new$0$XTabLayout(XFontScaleHelper xFontScaleHelper) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof TextView) {
                xFontScaleHelper.refreshTextSize((TextView) childAt);
            }
        }
    }

    private View getSelectView() {
        return getChildAt(this.mSelectTabIndex);
    }

    public void setStyle(int i) {
        int[] iArr;
        this.mIsDetachedNightTheme = isNight();
        if (!this.mIndicatorDayNightDiff) {
            i = 1;
        }
        this.mStyle = i;
        if (this.mTabsBarStyle) {
            this.mStyle = 2;
        }
        if (this.mStyle == 2) {
            int i2 = this.mPaddingNight;
            setPadding(i2, 0, i2, 0);
        } else {
            setPadding(0, 0, 0, 0);
        }
        if (this.mTitleTextColorRes > 0) {
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                if (childAt instanceof TextView) {
                    ((TextView) childAt).setTextColor(getResources().getColorStateList(this.mTitleTextColorRes, getContext().getTheme()));
                } else if ((childAt instanceof ImageView) && (iArr = this.mTitleIcons) != null && iArr.length > i3) {
                    ((ImageView) childAt).setImageResource(iArr[i3]);
                }
            }
        }
        this.mPaint.setColor(getContext().getColor(this.mIndicatorColor));
        this.mPaint2.setColor(getContext().getColor(this.mIndicatorColor2));
        moveIndicatorTo(false, null);
    }

    private void init() {
        this.mPaint.setStrokeWidth(0.0f);
        this.mPaint.setColor(getContext().getColor(this.mIndicatorColor));
        this.mBlurPaint.setStrokeWidth(0.0f);
        this.mBlurPaint.setColor(this.mIndicatorShadowColor);
        if (Build.VERSION.SDK_INT <= 26) {
            setLayerType(1, this.mBlurPaint);
        }
        this.mPaint2.setStrokeWidth(0.0f);
        this.mPaint2.setColor(getContext().getColor(this.mIndicatorColor2));
        this.mBlurPaint2.setStrokeWidth(0.0f);
        this.mBlurPaint2.setColor(this.mIndicatorShadowColor2);
        if (Build.VERSION.SDK_INT <= 26) {
            setLayerType(1, this.mBlurPaint2);
        }
        CharSequence[] charSequenceArr = this.mTitleString;
        if (charSequenceArr != null && charSequenceArr.length > 0) {
            for (CharSequence charSequence : charSequenceArr) {
                addTab(charSequence);
            }
        } else {
            int[] iArr = this.mTitleIcons;
            if (iArr != null && iArr.length > 0) {
                int i = 0;
                while (i < this.mTitleIcons.length) {
                    CharSequence[] charSequenceArr2 = this.mIconVuiLabels;
                    addTab(this.mTitleIcons[i], (charSequenceArr2 == null || charSequenceArr2.length <= i) ? "" : charSequenceArr2[i].toString());
                    i++;
                }
            }
        }
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            getChildAt(i2).setOnClickListener(this.mChildClickListener);
        }
        setOnHierarchyChangeListener(new AnonymousClass2());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.xui.widget.XTabLayout$2  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass2 implements ViewGroup.OnHierarchyChangeListener {
        AnonymousClass2() {
        }

        private void refreshIndicator(final boolean z) {
            XTabLayout.this.post(new Runnable() { // from class: com.xiaopeng.xui.widget.XTabLayout.2.1
                @Override // java.lang.Runnable
                public void run() {
                    XTabLayout.this.moveIndicatorTo(true, new OnMoveIndicatorListener() { // from class: com.xiaopeng.xui.widget.XTabLayout.2.1.1
                        @Override // com.xiaopeng.xui.widget.XTabLayout.OnMoveIndicatorListener
                        public void onStart() {
                            if (XTabLayout.this.mOnTabChangeListener != null) {
                                XTabLayout.this.mOnTabChangeListener.onTabChangeStart(XTabLayout.this, XTabLayout.this.mSelectTabIndex, z, false);
                            }
                        }

                        @Override // com.xiaopeng.xui.widget.XTabLayout.OnMoveIndicatorListener
                        public void onEnd() {
                            if (XTabLayout.this.mOnTabChangeListener != null) {
                                XTabLayout.this.mOnTabChangeListener.onTabChangeEnd(XTabLayout.this, XTabLayout.this.mSelectTabIndex, z, false);
                            }
                        }
                    });
                }
            });
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(View view, View view2) {
            view2.setOnClickListener(XTabLayout.this.mChildClickListener);
            if (XTabLayout.this.mSelectTabIndex < 0) {
                XTabLayout xTabLayout = XTabLayout.this;
                xTabLayout.mSelectTabIndex = xTabLayout.indexOfChild(view2);
                view2.setSelected(true);
            }
            Object tag = view2.getTag();
            refreshIndicator(tag == null ? false : ((Boolean) tag).booleanValue());
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(View view, View view2) {
            view2.setOnClickListener(null);
            Object tag = view2.getTag();
            refreshIndicator(tag == null ? false : ((Boolean) tag).booleanValue());
        }
    }

    public boolean isIndicatorAnimatorEnable() {
        return this.mIndicatorAnimatorEnable;
    }

    public void setIndicatorAnimatorEnable(boolean z) {
        this.mIndicatorAnimatorEnable = z;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        int i = this.mStyle;
        if (i == 1) {
            drawDayIndicator(canvas);
        } else if (i == 2) {
            drawNightIndicator(canvas);
        }
        super.dispatchDraw(canvas);
    }

    public int addTab(CharSequence charSequence, int i) {
        TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.x_tab_layout_title_view, (ViewGroup) this, false);
        textView.setText(charSequence);
        if (this.mTitleTextColorRes > 0) {
            textView.setTextColor(getResources().getColorStateList(this.mTitleTextColorRes, getContext().getTheme()));
        }
        textView.setTextSize(0, this.mTitleTextSize);
        textView.setTag(Boolean.valueOf(i == this.mSelectTabIndex));
        int i2 = this.mSelectTabIndex;
        if (i <= i2) {
            this.mSelectTabIndex = i2 + 1;
        }
        textView.setSoundEffectsEnabled(isSoundEffectsEnabled());
        addView(textView, i);
        return i;
    }

    public int addTab(int i, int i2, String str) {
        XImageView xImageView = new XImageView(getContext());
        xImageView.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1.0f));
        xImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        xImageView.setImageResource(i);
        xImageView.setVuiElementType(VuiElementType.TEXTVIEW);
        xImageView.setVuiLabel(str);
        xImageView.setTag(Boolean.valueOf(i2 == this.mSelectTabIndex));
        int i3 = this.mSelectTabIndex;
        if (i2 <= i3) {
            this.mSelectTabIndex = i3 + 1;
        }
        xImageView.setSoundEffectsEnabled(isSoundEffectsEnabled());
        addView(xImageView, i2);
        return i2;
    }

    public boolean isTabClickable(int i) {
        return getChildAt(i).isClickable();
    }

    public boolean isAllTabClickable() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (!getChildAt(i).isClickable()) {
                return false;
            }
        }
        return true;
    }

    public void setAllTabClickable(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setClickable(z);
        }
    }

    public void setTabClickable(int i, boolean z) {
        getChildAt(i).setClickable(z);
    }

    public int addTab(CharSequence charSequence) {
        return addTab(charSequence, getChildCount());
    }

    public int addTab(int i, String str) {
        return addTab(i, getChildCount(), str);
    }

    public void removeTab(int i, int i2) {
        if (i >= getTabCount() || i2 >= getTabCount()) {
            return;
        }
        if (i == i2) {
            removeTab(i);
            return;
        }
        int i3 = this.mSelectTabIndex;
        boolean z = i2 == i3;
        if (z) {
            getChildAt(i3).setSelected(false);
            getChildAt(i2).setSelected(true);
        }
        this.mSelectTabIndex = i2;
        getChildAt(i).setTag(Boolean.valueOf(z));
        removeViewAt(i);
    }

    public void removeTab(int i) {
        if (getChildAt(i) == null) {
            throw new IllegalArgumentException("targetView is not exits. index = " + i + ", tabCount = " + getChildCount());
        }
        int i2 = this.mSelectTabIndex;
        boolean z = i2 == i;
        if (i2 < getTabCount() - 1) {
            getChildAt(this.mSelectTabIndex).setSelected(false);
            getChildAt(i).setTag(Boolean.valueOf(z));
            removeViewAt(i);
            getChildAt(this.mSelectTabIndex).setSelected(true);
            return;
        }
        getChildAt(this.mSelectTabIndex).setSelected(false);
        this.mSelectTabIndex--;
        getChildAt(i).setTag(Boolean.valueOf(z));
        removeViewAt(i);
        View childAt = getChildAt(this.mSelectTabIndex);
        if (childAt != null) {
            childAt.setSelected(true);
        }
    }

    public void selectedNoneTab(boolean z, final boolean z2) {
        View selectView = getSelectView();
        if (selectView != null) {
            selectView.setSelected(false);
        }
        this.mSelectTabIndex = -1;
        moveIndicatorTo(z, new OnMoveIndicatorListener() { // from class: com.xiaopeng.xui.widget.XTabLayout.3
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnMoveIndicatorListener
            public void onStart() {
                if (!z2 || XTabLayout.this.mOnTabChangeListener == null) {
                    return;
                }
                XTabLayout.this.mOnTabChangeListener.onTabChangeStart(XTabLayout.this, -1, true, false);
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnMoveIndicatorListener
            public void onEnd() {
                if (z2 && XTabLayout.this.mOnTabChangeListener != null) {
                    XTabLayout.this.mOnTabChangeListener.onTabChangeEnd(XTabLayout.this, -1, true, false);
                }
                XTabLayout xTabLayout = XTabLayout.this;
                xTabLayout.updateVui(xTabLayout);
            }
        });
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectTab(final int i, boolean z, final boolean z2) {
        final boolean z3;
        if (i >= getTabCount() || i < 0 || i == this.mSelectTabIndex) {
            return;
        }
        OnTabChangeListener onTabChangeListener = this.mOnTabChangeListener;
        if (onTabChangeListener == null || !onTabChangeListener.onInterceptTabChange(this, i, true, z2)) {
            final View childAt = getChildAt(i);
            View selectView = getSelectView();
            if (childAt != selectView) {
                if (childAt != null) {
                    childAt.setSelected(true);
                }
                if (selectView != null) {
                    selectView.setSelected(false);
                }
                this.mSelectTabIndex = i;
                z3 = true;
            } else {
                z3 = false;
            }
            moveIndicatorTo(z, new OnMoveIndicatorListener() { // from class: com.xiaopeng.xui.widget.XTabLayout.4
                @Override // com.xiaopeng.xui.widget.XTabLayout.OnMoveIndicatorListener
                public void onStart() {
                    if (!z3 || XTabLayout.this.mOnTabChangeListener == null) {
                        return;
                    }
                    if (childAt == null) {
                        XTabLayout.this.mOnTabChangeListener.onTabChangeStart(XTabLayout.this, -1, true, z2);
                    } else {
                        XTabLayout.this.mOnTabChangeListener.onTabChangeStart(XTabLayout.this, i, true, z2);
                    }
                }

                @Override // com.xiaopeng.xui.widget.XTabLayout.OnMoveIndicatorListener
                public void onEnd() {
                    if (z3 && XTabLayout.this.mOnTabChangeListener != null) {
                        if (childAt == null) {
                            XTabLayout.this.mOnTabChangeListener.onTabChangeEnd(XTabLayout.this, -1, true, z2);
                        } else {
                            XTabLayout.this.mOnTabChangeListener.onTabChangeEnd(XTabLayout.this, i, true, z2);
                        }
                    }
                    XTabLayout xTabLayout = XTabLayout.this;
                    xTabLayout.updateVui(xTabLayout);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (XThemeManager.isThemeChanged(configuration)) {
            setStyle(isNight() ? 2 : 1);
        }
    }

    private int getTabViewStart(int i) {
        if (i < 0 || getWidth() <= 0) {
            return 0;
        }
        if (this.mStyle == 2) {
            int width = (getWidth() - (this.mPaddingNight * 2)) / getTabCount();
            return this.mPaddingNight + (i * width) + getIndicatorOffset(width);
        }
        return 0 + (i * (getWidth() / getTabCount()));
    }

    private int getTabViewEnd(int i) {
        if (i < 0 || getWidth() <= 0) {
            return 0;
        }
        if (this.mStyle == 2) {
            int width = (getWidth() - (this.mPaddingNight * 2)) / getTabCount();
            return this.mPaddingNight + (((i + 1) * width) - getIndicatorOffset(width));
        }
        return ((i + 1) * (getWidth() / getTabCount())) + 0;
    }

    private int getIndicatorOffset(int i) {
        float f;
        float f2 = this.mIndicatorWidth;
        if (f2 != 0.0f) {
            f = (i - f2) / 2.0f;
        } else {
            f = i * ((1.0f - this.mIndicatorWidthPercent) / 2.0f);
        }
        return (int) f;
    }

    private void getIndicatorPosition() {
        int selectedTabIndex = getSelectedTabIndex();
        if (selectedTabIndex < 0) {
            this.mToStart = 0;
            this.mToEnd = 0;
            this.mToStart2 = 0;
            this.mToEnd2 = 0;
            return;
        }
        this.mToStart = getTabViewStart(selectedTabIndex);
        int tabViewEnd = getTabViewEnd(selectedTabIndex);
        this.mToEnd = tabViewEnd;
        this.mToStart2 = this.mToStart;
        this.mToEnd2 = tabViewEnd;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveIndicatorTo(boolean z, final OnMoveIndicatorListener onMoveIndicatorListener) {
        if (getTabCount() <= 0) {
            return;
        }
        boolean z2 = z && this.mIndicatorAnimatorEnable;
        getIndicatorPosition();
        if (z2) {
            if (this.mValueAnimator == null) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mValueAnimator = ofFloat;
                ofFloat.setDuration(DURATION);
                this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.xui.widget.XTabLayout.5
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        float min = Math.min(floatValue, XTabLayout.this.mDivideValue) / XTabLayout.this.mDivideValue;
                        if (floatValue < XTabLayout.this.mDivideValue) {
                            XTabLayout xTabLayout = XTabLayout.this;
                            xTabLayout.mIndicatorHeight = (int) (xTabLayout.mIndicatorMaxHeight - ((floatValue / XTabLayout.this.mDivideValue) * (XTabLayout.this.mIndicatorMaxHeight - XTabLayout.this.mIndicatorMinHeight)));
                        } else {
                            XTabLayout xTabLayout2 = XTabLayout.this;
                            xTabLayout2.mIndicatorHeight = (int) (xTabLayout2.mIndicatorMaxHeight + ((((floatValue - XTabLayout.this.mDivideValue) / (1.0f - XTabLayout.this.mDivideValue)) - 1.0f) * (XTabLayout.this.mIndicatorMaxHeight - XTabLayout.this.mIndicatorMinHeight)));
                        }
                        if (XTabLayout.this.mToStart > XTabLayout.this.mCurrentStart) {
                            XTabLayout xTabLayout3 = XTabLayout.this;
                            xTabLayout3.mTempStart = (int) (xTabLayout3.mCurrentStart + (Math.pow(floatValue, XTabLayout.K) * (XTabLayout.this.mToStart - XTabLayout.this.mCurrentStart)));
                            XTabLayout xTabLayout4 = XTabLayout.this;
                            xTabLayout4.mTempEnd = (int) (xTabLayout4.mCurrentEnd + ((XTabLayout.this.mToEnd - XTabLayout.this.mCurrentEnd) * min));
                        } else {
                            XTabLayout xTabLayout5 = XTabLayout.this;
                            xTabLayout5.mTempStart = (int) (xTabLayout5.mCurrentStart + ((XTabLayout.this.mToStart - XTabLayout.this.mCurrentStart) * min));
                            XTabLayout xTabLayout6 = XTabLayout.this;
                            xTabLayout6.mTempEnd = (int) (xTabLayout6.mCurrentEnd + (Math.pow(floatValue, XTabLayout.K) * (XTabLayout.this.mToEnd - XTabLayout.this.mCurrentEnd)));
                        }
                        if (XTabLayout.this.mToStart2 > XTabLayout.this.mCurrentStart2) {
                            XTabLayout xTabLayout7 = XTabLayout.this;
                            xTabLayout7.mTempStart2 = (int) (xTabLayout7.mCurrentStart2 + (Math.pow(floatValue, XTabLayout.K2) * (XTabLayout.this.mToStart2 - XTabLayout.this.mCurrentStart2)));
                            XTabLayout xTabLayout8 = XTabLayout.this;
                            xTabLayout8.mTempEnd2 = (int) (xTabLayout8.mCurrentEnd2 + (min * (XTabLayout.this.mToEnd2 - XTabLayout.this.mCurrentEnd2)));
                        } else {
                            XTabLayout xTabLayout9 = XTabLayout.this;
                            xTabLayout9.mTempStart2 = (int) (xTabLayout9.mCurrentStart2 + (min * (XTabLayout.this.mToStart2 - XTabLayout.this.mCurrentStart2)));
                            XTabLayout xTabLayout10 = XTabLayout.this;
                            xTabLayout10.mTempEnd2 = (int) (xTabLayout10.mCurrentEnd2 + (Math.pow(floatValue, XTabLayout.K2) * (XTabLayout.this.mToEnd2 - XTabLayout.this.mCurrentEnd2)));
                        }
                        XTabLayout.this.invalidate();
                    }
                });
                this.mValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.xui.widget.XTabLayout.6
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        super.onAnimationStart(animator);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        XTabLayout.this.assignPosition();
                        XTabLayout.this.invalidate();
                    }
                });
                this.mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            }
            this.mValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.xui.widget.XTabLayout.7
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    OnMoveIndicatorListener onMoveIndicatorListener2 = onMoveIndicatorListener;
                    if (onMoveIndicatorListener2 != null) {
                        onMoveIndicatorListener2.onStart();
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    XTabLayout.this.mValueAnimator.removeListener(this);
                    OnMoveIndicatorListener onMoveIndicatorListener2 = onMoveIndicatorListener;
                    if (onMoveIndicatorListener2 != null) {
                        onMoveIndicatorListener2.onEnd();
                    }
                }
            });
            this.mValueAnimator.start();
            return;
        }
        assignPosition();
        if (onMoveIndicatorListener != null) {
            onMoveIndicatorListener.onStart();
            onMoveIndicatorListener.onEnd();
        }
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void assignPosition() {
        int i = this.mToStart;
        this.mCurrentStart = i;
        int i2 = this.mToEnd;
        this.mCurrentEnd = i2;
        this.mCurrentStart2 = this.mToStart2;
        int i3 = this.mToEnd2;
        this.mCurrentEnd2 = i3;
        this.mTempStart = i;
        this.mTempEnd = i2;
        this.mTempStart2 = i;
        this.mTempEnd2 = i3;
    }

    public void updateTabTitle(int i, String str) {
        View childAt = getChildAt(i);
        if (childAt instanceof TextView) {
            ((TextView) childAt).setText(str);
        }
    }

    public CharSequence getTabTitle(int i) {
        return getChildAt(i) instanceof TextView ? ((TextView) getChildAt(i)).getText() : "";
    }

    public void updateTabTitle(int i, int i2) {
        View childAt = getChildAt(i);
        if (childAt instanceof TextView) {
            ((TextView) childAt).setText(i2);
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setEnabled(z);
        }
        if (Build.VERSION.SDK_INT > 26) {
            int i2 = z ? 255 : 92;
            this.mPaint.setAlpha(i2);
            this.mBlurPaint.setAlpha(i2);
            this.mPaint2.setAlpha(i2);
            this.mBlurPaint2.setAlpha(i2);
            invalidate();
        }
    }

    public void setEnabled(boolean z, int i) {
        int childCount = getChildCount();
        View childAt = getChildAt(i);
        if (i >= childCount || childAt == null) {
            return;
        }
        childAt.setEnabled(z);
        invalidate();
    }

    public boolean isEnabled(int i) {
        int childCount = getChildCount();
        View childAt = getChildAt(i);
        if (i >= childCount || childAt == null) {
            return false;
        }
        return childAt.isEnabled();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        post(new Runnable() { // from class: com.xiaopeng.xui.widget.XTabLayout.8
            @Override // java.lang.Runnable
            public void run() {
                XTabLayout.this.moveIndicatorTo(false, null);
            }
        });
    }

    public void selectTab(int i, boolean z) {
        selectTab(i, z, false);
    }

    public int getSelectedTabIndex() {
        return this.mSelectTabIndex;
    }

    public void selectTab(int i) {
        selectTab(i, true);
    }

    public int getTabCount() {
        return getChildCount();
    }

    private void drawDayIndicator(Canvas canvas) {
        if (!isNight()) {
            this.mPaint.setMaskFilter(null);
        }
        this.mPaint.setAlpha(isEnabled(this.mSelectTabIndex) ? 255 : 92);
        float height = (getHeight() * 1.0f) / 2.0f;
        float height2 = getHeight() >> 1;
        int i = this.mTempStart;
        int i2 = this.mTempEnd;
        if (i < i2) {
            canvas.drawRoundRect(i, height2 - height, i2, height2 + height, height, height, this.mPaint);
        } else {
            canvas.drawRoundRect(i2, height2 - height, i, height2 + height, height, height, this.mPaint);
        }
    }

    private void drawNightIndicator(Canvas canvas) {
        if (isNight()) {
            this.mPaint.setMaskFilter(new BlurMaskFilter(this.mIndicatorShadowRadius, BlurMaskFilter.Blur.SOLID));
            this.mPaint2.setMaskFilter(new BlurMaskFilter(this.mIndicatorShadowRadius2, BlurMaskFilter.Blur.SOLID));
        } else {
            this.mPaint.setMaskFilter(null);
            this.mPaint2.setMaskFilter(null);
        }
        this.mPaint.setAlpha(isEnabled(this.mSelectTabIndex) ? 255 : 92);
        this.mPaint2.setAlpha(isEnabled(this.mSelectTabIndex) ? 255 : 92);
        float f = this.mIndicatorHeight / 2.0f;
        float height = getHeight() - this.mIndicatorMarginBottom;
        if (this.mTempStart2 < this.mTempEnd2) {
            canvas.drawRoundRect(this.mTempStart2, height - Math.max(this.mIndicatorHeight, 1.0f), this.mTempEnd2, height, f, f, this.mPaint2);
        } else {
            canvas.drawRoundRect(this.mTempEnd2, height - Math.max(this.mIndicatorHeight, 1.0f), this.mTempStart2, height, f, f, this.mPaint2);
        }
    }

    protected int dp(int i) {
        return (int) TypedValue.applyDimension(1, i, getResources().getDisplayMetrics());
    }

    protected float dpF(float f) {
        return TypedValue.applyDimension(1, f, getResources().getDisplayMetrics());
    }

    public void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
        this.mOnTabChangeListener = onTabChangeListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mIsDetachedFromWindow || this.mIsDetachedNightTheme == isNight()) {
            return;
        }
        this.mIsDetachedFromWindow = false;
        setStyle(isNight() ? 2 : 1);
    }

    private boolean isNight() {
        if (isInEditMode()) {
            return false;
        }
        return XThemeManager.isNight(getContext());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIsDetachedFromWindow = true;
        this.mIsDetachedNightTheme = isNight();
    }

    @Override // android.view.View
    public void setSoundEffectsEnabled(boolean z) {
        super.setSoundEffectsEnabled(z);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != null) {
                childAt.setSoundEffectsEnabled(z);
            }
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public VuiElement onBuildVuiElement(String str, IVuiElementBuilder iVuiElementBuilder) {
        setVuiValue(Integer.valueOf(this.mSelectTabIndex));
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof VuiView) {
                VuiView vuiView = (VuiView) childAt;
                vuiView.setVuiPosition(i);
                vuiView.setVuiElementId(str + "_" + i);
                logD("onBuildVuiElement:" + str);
            }
        }
        return null;
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(final View view, VuiEvent vuiEvent) {
        final Double d;
        logD("tablayout onVuiElementEvent");
        if (view == null || (d = (Double) vuiEvent.getEventValue(vuiEvent)) == null) {
            return false;
        }
        post(new Runnable() { // from class: com.xiaopeng.xui.widget.-$$Lambda$XTabLayout$cLLB54eM7h-1AR7MzpzE8sxpg3w
            @Override // java.lang.Runnable
            public final void run() {
                XTabLayout.this.lambda$onVuiElementEvent$1$XTabLayout(d, view);
            }
        });
        return true;
    }

    public /* synthetic */ void lambda$onVuiElementEvent$1$XTabLayout(Double d, View view) {
        if (d.intValue() < getChildCount()) {
            VuiFloatingLayerManager.show(getChildAt(d.intValue()));
        } else {
            VuiFloatingLayerManager.show(view);
        }
        setPerformVuiAction(true);
        selectTab(d.intValue(), true, true);
        setPerformVuiAction(false);
    }
}
