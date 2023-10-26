package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import com.google.android.material.timepicker.TimeModel;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.libtheme.ThemeViewModel;
import com.xiaopeng.xui.sound.XSoundEffectManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

/* loaded from: classes2.dex */
public class NumberPickerView extends LinearLayout {
    private static final long DEFAULT_LONG_PRESS_UPDATE_INTERVAL = 300;
    private static final int DEFAULT_TEXT_SIZE = 20;
    private static final int LAYOUT_ROUNDING_OFFSET = 5;
    private static final int SELECTED_TEXT_LAYOUT_OFFSET = 0;
    private static final int SELECTOR_ADJUSTMENT_DURATION_MILLIS = 800;
    private static final int SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT = 8;
    private static final int SELECTOR_MIDDLE_ITEM_INDEX = 2;
    private static final int SELECTOR_WHEEL_ITEM_COUNT = 5;
    private static final int SIZE_UNSPECIFIED = -1;
    private static final int SNAP_SCROLL_DURATION = 300;
    private static final int SYMBOL_LAYOUT_OFFSET = 35;
    private static final int SYMBOL_WIDTH = 6;
    private static final int TEXT_LAYOUT_DEFAULT = 1;
    private static final int TEXT_LAYOUT_LEFT = 0;
    private static final int TEXT_LAYOUT_MIDDLE = 1;
    private static final int TEXT_LAYOUT_RIGHT = 2;
    private static final float TOP_AND_BOTTOM_FADING_EDGE_STRENGTH = 0.9f;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDERS_DISTANCE = 48;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT = 2;
    private final Scroller mAdjustScroller;
    private BeginSoftInputOnLongPressCommand mBeginSoftInputOnLongPressCommand;
    private int mBottomSelectionDividerBottom;
    private ChangeCurrentByOneFromLongPressCommand mChangeCurrentByOneFromLongPressCommand;
    private final boolean mComputeMaxWidth;
    private int mCurrentScrollOffset;
    private String[] mDisplayedValues;
    private final Scroller mFlingScroller;
    private Formatter mFormatter;
    private boolean mHideWheelUntilFocused;
    private boolean mIgnoreMoveEvents;
    private int mInitialScrollOffset;
    private long mLastDownEventTime;
    private float mLastDownEventY;
    private float mLastDownOrMoveEventY;
    private int mLastHandledDownDpadKeyCode;
    private int mLastHoveredChildVirtualViewId;
    private long mLongPressUpdateInterval;
    private final int mMaxHeight;
    private int mMaxValue;
    private int mMaxWidth;
    private int mMaximumFlingVelocity;
    private final int mMinHeight;
    private int mMinValue;
    private final int mMinWidth;
    private int mMinimumFlingVelocity;
    private OnScrollListener mOnScrollListener;
    private OnValueChangeListener mOnValueChangeListener;
    private boolean mPerformClickOnTap;
    private String mPrefixSelectionStr;
    private float mPrefixWidth;
    private final PressedStateHelper mPressedStateHelper;
    private int mPreviousScrollerY;
    private int mScrollState;
    private final int mSelectedTextSize;
    private final Drawable mSelectionDivider;
    private final int mSelectionDividerHeight;
    private final int mSelectionDividersDistance;
    private int mSelectorElementHeight;
    private final SparseArray<String> mSelectorIndexToStringCache;
    private final int[] mSelectorIndices;
    private int mSelectorTextGapHeight;
    private final Paint mSelectorWheelPaint;
    private final int mSolidColor;
    private String mSuffixSelectionStr;
    private float mSuffixWidth;
    private final Drawable mSymbol;
    private ColorStateList mTextColors;
    private int mTextLayout;
    private int mTextLayoutMargin;
    private final int mTextSize;
    private ThemeViewModel mThemeViewModel;
    private int mTopSelectionDividerTop;
    private int mTouchSlop;
    private int mValue;
    private VelocityTracker mVelocityTracker;
    private int mWidth;
    private boolean mWrapSelectorWheel;
    private boolean mWrapSelectorWheelPreferred;
    private long soundTime;
    private static final TwoDigitFormatter sTwoDigitFormatter = new TwoDigitFormatter();
    private static final char[] DIGIT_CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 1632, 1633, 1634, 1635, 1636, 1637, 1638, 1639, 1640, 1641, 1776, 1777, 1778, 1779, 1780, 1781, 1782, 1783, 1784, 1785, 2406, 2407, 2408, 2409, 2410, 2411, 2412, 2413, 2414, 2415, 2534, 2535, 2536, 2537, 2538, 2539, 2540, 2541, 2542, 2543, 3302, 3303, 3304, 3305, 3306, 3307, 3308, 3309, 3310, 3311};

    /* loaded from: classes2.dex */
    public interface Formatter {
        String format(int value);
    }

    /* loaded from: classes2.dex */
    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes2.dex */
        public @interface ScrollState {
        }

        void onScrollStateChange(NumberPickerView view, int scrollState);
    }

    /* loaded from: classes2.dex */
    public interface OnValueChangeListener {
        void onValueChange(NumberPickerView picker, int oldVal, int newVal);
    }

    @Override // android.view.View
    protected float getBottomFadingEdgeStrength() {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH;
    }

    @Override // android.view.View
    protected float getTopFadingEdgeStrength() {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH;
    }

    /* loaded from: classes2.dex */
    private static class TwoDigitFormatter implements Formatter {
        java.util.Formatter mFmt;
        char mZeroDigit;
        final StringBuilder mBuilder = new StringBuilder();
        final Object[] mArgs = new Object[1];

        private static char getZeroDigit(Locale locale) {
            return '0';
        }

        TwoDigitFormatter() {
            init(Locale.getDefault());
        }

        private void init(Locale locale) {
            this.mFmt = createFormatter(locale);
            this.mZeroDigit = getZeroDigit(locale);
        }

        @Override // com.xiaopeng.carcontrol.view.widget.NumberPickerView.Formatter
        public String format(int value) {
            Locale locale = Locale.getDefault();
            if (this.mZeroDigit != getZeroDigit(locale)) {
                init(locale);
            }
            this.mArgs[0] = Integer.valueOf(value);
            StringBuilder sb = this.mBuilder;
            sb.delete(0, sb.length());
            this.mFmt.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, this.mArgs);
            return this.mFmt.toString();
        }

        private java.util.Formatter createFormatter(Locale locale) {
            return new java.util.Formatter(this.mBuilder, locale);
        }
    }

    public static Formatter getTwoDigitFormatter() {
        return sTwoDigitFormatter;
    }

    public NumberPickerView(Context context) {
        this(context, null);
    }

    public NumberPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.XNumberPicker);
    }

    public NumberPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.XNumberPicker);
    }

    public NumberPickerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mPrefixWidth = 0.0f;
        this.mSuffixWidth = 0.0f;
        this.mWrapSelectorWheelPreferred = true;
        this.mLongPressUpdateInterval = DEFAULT_LONG_PRESS_UPDATE_INTERVAL;
        this.mSelectorIndexToStringCache = new SparseArray<>();
        this.mSelectorIndices = new int[5];
        this.mInitialScrollOffset = Integer.MIN_VALUE;
        this.mScrollState = 0;
        this.mLastHandledDownDpadKeyCode = -1;
        this.soundTime = 0L;
        this.mThemeViewModel = ThemeViewModel.create(context, attrs, defStyleAttr, defStyleRes, null);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, com.xiaopeng.xpui.R.styleable.XNumberPicker, defStyleAttr, defStyleRes);
        this.mHideWheelUntilFocused = obtainStyledAttributes.getBoolean(2, false);
        this.mTextLayout = obtainStyledAttributes.getInt(14, 1);
        this.mSolidColor = obtainStyledAttributes.getColor(12, 0);
        Drawable drawable = obtainStyledAttributes.getDrawable(9);
        if (drawable != null) {
            drawable.setCallback(this);
            drawable.setLayoutDirection(getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
        }
        this.mSelectionDivider = drawable;
        this.mSymbol = obtainStyledAttributes.getDrawable(13);
        this.mSelectionDividerHeight = obtainStyledAttributes.getDimensionPixelSize(10, (int) TypedValue.applyDimension(1, 2.0f, getResources().getDisplayMetrics()));
        this.mSelectionDividersDistance = obtainStyledAttributes.getDimensionPixelSize(11, (int) TypedValue.applyDimension(1, 48.0f, getResources().getDisplayMetrics()));
        this.mTextLayoutMargin = obtainStyledAttributes.getDimensionPixelSize(15, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(6, -1);
        this.mMinHeight = dimensionPixelSize;
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(4, -1);
        this.mMaxHeight = dimensionPixelSize2;
        if (dimensionPixelSize != -1 && dimensionPixelSize2 != -1 && dimensionPixelSize > dimensionPixelSize2) {
            throw new IllegalArgumentException("minHeight > maxHeight");
        }
        int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(7, -1);
        this.mMinWidth = dimensionPixelSize3;
        int dimensionPixelSize4 = obtainStyledAttributes.getDimensionPixelSize(5, -1);
        this.mMaxWidth = dimensionPixelSize4;
        if (dimensionPixelSize3 != -1 && dimensionPixelSize4 != -1 && dimensionPixelSize3 > dimensionPixelSize4) {
            throw new IllegalArgumentException("minWidth > maxWidth");
        }
        this.mComputeMaxWidth = dimensionPixelSize4 == -1;
        int dimensionPixelSize5 = obtainStyledAttributes.getDimensionPixelSize(0, 20);
        this.mTextSize = dimensionPixelSize5;
        int dimensionPixelSize6 = obtainStyledAttributes.getDimensionPixelSize(8, 20);
        this.mSelectedTextSize = dimensionPixelSize6;
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(1);
        this.mTextColors = colorStateList;
        if (colorStateList == null) {
            this.mTextColors = ColorStateList.valueOf(context.getColor(17170444));
        }
        obtainStyledAttributes.recycle();
        this.mPressedStateHelper = new PressedStateHelper();
        setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity() / 8;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int i = this.mTextLayout;
        if (i == 0) {
            paint.setTextAlign(Paint.Align.LEFT);
        } else if (i == 1) {
            paint.setTextAlign(Paint.Align.CENTER);
        } else if (i == 2) {
            paint.setTextAlign(Paint.Align.RIGHT);
        }
        paint.setTextSize(Math.max(dimensionPixelSize5, dimensionPixelSize6));
        paint.setColor(this.mTextColors.getDefaultColor());
        this.mSelectorWheelPaint = paint;
        this.mFlingScroller = new Scroller(getContext(), null, true);
        this.mAdjustScroller = new Scroller(getContext(), new DecelerateInterpolator(2.5f));
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        if (getFocusable() == 16) {
            setFocusable(1);
            setFocusableInTouchMode(true);
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            initializeSelectorWheel();
            initializeFadingEdges();
            int height = getHeight();
            int i = this.mSelectionDividersDistance;
            int i2 = this.mSelectionDividerHeight;
            int i3 = (((height - i) / 2) - i2) + 5;
            this.mTopSelectionDividerTop = i3;
            this.mBottomSelectionDividerBottom = i3 + (i2 * 2) + i;
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(makeMeasureSpec(widthMeasureSpec, this.mMaxWidth), makeMeasureSpec(heightMeasureSpec, this.mMaxHeight));
        int resolveSizeAndStateRespectingMinSize = resolveSizeAndStateRespectingMinSize(this.mMinWidth, getMeasuredWidth(), widthMeasureSpec);
        this.mWidth = resolveSizeAndStateRespectingMinSize;
        setMeasuredDimension(resolveSizeAndStateRespectingMinSize, resolveSizeAndStateRespectingMinSize(this.mMinHeight, getMeasuredHeight(), heightMeasureSpec));
    }

    private boolean moveToFinalScrollerPosition(Scroller scroller) {
        scroller.forceFinished(true);
        int finalY = scroller.getFinalY() - scroller.getCurrY();
        int i = this.mInitialScrollOffset - ((this.mCurrentScrollOffset + finalY) % this.mSelectorElementHeight);
        if (i != 0) {
            int abs = Math.abs(i);
            int i2 = this.mSelectorElementHeight;
            if (abs > i2 / 2) {
                i = i > 0 ? i - i2 : i + i2;
            }
            scrollBy(0, finalY + i);
            return true;
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isEnabled() && event.getActionMasked() == 0) {
            removeAllCallbacks();
            float y = event.getY();
            this.mLastDownEventY = y;
            this.mLastDownOrMoveEventY = y;
            this.mLastDownEventTime = event.getEventTime();
            this.mIgnoreMoveEvents = false;
            this.mPerformClickOnTap = false;
            float f = this.mLastDownEventY;
            if (f < this.mTopSelectionDividerTop) {
                if (this.mScrollState == 0) {
                    this.mPressedStateHelper.buttonPressDelayed(2);
                }
            } else if (f > this.mBottomSelectionDividerBottom && this.mScrollState == 0) {
                this.mPressedStateHelper.buttonPressDelayed(1);
            }
            getParent().requestDisallowInterceptTouchEvent(true);
            if (!this.mFlingScroller.isFinished()) {
                this.mFlingScroller.forceFinished(true);
                this.mAdjustScroller.forceFinished(true);
                onScrollStateChange(0);
            } else if (!this.mAdjustScroller.isFinished()) {
                this.mFlingScroller.forceFinished(true);
                this.mAdjustScroller.forceFinished(true);
            } else {
                float f2 = this.mLastDownEventY;
                if (f2 < this.mTopSelectionDividerTop) {
                    postChangeCurrentByOneFromLongPress(false, ViewConfiguration.getLongPressTimeout());
                } else if (f2 > this.mBottomSelectionDividerBottom) {
                    postChangeCurrentByOneFromLongPress(true, ViewConfiguration.getLongPressTimeout());
                } else {
                    this.mPerformClickOnTap = true;
                    postBeginSoftInputOnLongPressCommand();
                }
            }
            return true;
        }
        return false;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(event);
            int actionMasked = event.getActionMasked();
            if (actionMasked != 1) {
                if (actionMasked == 2 && !this.mIgnoreMoveEvents) {
                    float y = event.getY();
                    if (this.mScrollState != 1) {
                        if (((int) Math.abs(y - this.mLastDownEventY)) > this.mTouchSlop) {
                            removeAllCallbacks();
                            onScrollStateChange(1);
                        }
                    } else {
                        scrollBy(0, (int) (y - this.mLastDownOrMoveEventY));
                        invalidate();
                    }
                    this.mLastDownOrMoveEventY = y;
                }
            } else {
                removeBeginSoftInputCommand();
                removeChangeCurrentByOneFromLongPress();
                this.mPressedStateHelper.cancel();
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                int yVelocity = (int) velocityTracker.getYVelocity();
                if (Math.abs(yVelocity) > this.mMinimumFlingVelocity) {
                    fling(yVelocity);
                    onScrollStateChange(2);
                } else {
                    int y2 = (int) event.getY();
                    int abs = (int) Math.abs(y2 - this.mLastDownEventY);
                    long eventTime = event.getEventTime() - this.mLastDownEventTime;
                    if (abs <= this.mTouchSlop && eventTime < ViewConfiguration.getTapTimeout()) {
                        if (this.mPerformClickOnTap) {
                            this.mPerformClickOnTap = false;
                            performClick();
                        } else {
                            int i = (y2 / this.mSelectorElementHeight) - 2;
                            if (i > 0) {
                                changeValueByOne(true);
                                this.mPressedStateHelper.buttonTapped(1);
                            } else if (i < 0) {
                                changeValueByOne(false);
                                this.mPressedStateHelper.buttonTapped(2);
                            }
                        }
                    } else {
                        ensureScrollWheelAdjusted();
                    }
                    onScrollStateChange(0);
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            return true;
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            removeAllCallbacks();
        }
        return super.dispatchTouchEvent(event);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onConfigurationChanged(this, newConfig);
        }
        if (ThemeManager.isThemeChanged(newConfig)) {
            this.mTextColors = getContext().getResources().getColorStateList(R.color.x_number_picker_text_color, null);
            invalidate();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onAttachedToWindow(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == 19 || keyCode == 20) {
            int action = event.getAction();
            if (action == 0) {
                if (!this.mWrapSelectorWheel) {
                    if (keyCode == 20) {
                    }
                }
                requestFocus();
                this.mLastHandledDownDpadKeyCode = keyCode;
                removeAllCallbacks();
                if (this.mFlingScroller.isFinished()) {
                    changeValueByOne(keyCode == 20);
                }
                return true;
            } else if (action == 1 && this.mLastHandledDownDpadKeyCode == keyCode) {
                this.mLastHandledDownDpadKeyCode = -1;
                return true;
            }
        } else if (keyCode == 23 || keyCode == 66) {
            removeAllCallbacks();
        }
        return super.dispatchKeyEvent(event);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTrackballEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            removeAllCallbacks();
        }
        return super.dispatchTrackballEvent(event);
    }

    @Override // android.view.View
    public void computeScroll() {
        Scroller scroller = this.mFlingScroller;
        if (scroller.isFinished()) {
            scroller = this.mAdjustScroller;
            if (scroller.isFinished()) {
                return;
            }
        }
        scroller.computeScrollOffset();
        int currY = scroller.getCurrY();
        if (this.mPreviousScrollerY == 0) {
            this.mPreviousScrollerY = scroller.getStartY();
        }
        scrollBy(0, currY - this.mPreviousScrollerY);
        this.mPreviousScrollerY = currY;
        if (scroller.isFinished()) {
            onScrollerFinished(scroller);
        } else {
            invalidate();
        }
    }

    @Override // android.view.View
    public void scrollBy(int x, int y) {
        int i;
        int[] iArr = this.mSelectorIndices;
        int i2 = this.mCurrentScrollOffset;
        boolean z = this.mWrapSelectorWheel;
        if (!z && y > 0 && iArr[2] <= this.mMinValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        } else if (!z && y < 0 && iArr[2] >= this.mMaxValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        } else {
            this.mCurrentScrollOffset = y + i2;
            while (true) {
                int i3 = this.mCurrentScrollOffset;
                if (i3 - this.mInitialScrollOffset <= this.mSelectorTextGapHeight) {
                    break;
                }
                this.mCurrentScrollOffset = i3 - this.mSelectorElementHeight;
                decrementSelectorIndices(iArr);
                setValueInternal(iArr[2], true);
                if (!this.mWrapSelectorWheel && iArr[2] <= this.mMinValue) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
            while (true) {
                i = this.mCurrentScrollOffset;
                if (i - this.mInitialScrollOffset >= (-this.mSelectorTextGapHeight)) {
                    break;
                }
                this.mCurrentScrollOffset = i + this.mSelectorElementHeight;
                incrementSelectorIndices(iArr);
                setValueInternal(iArr[2], true);
                if (!this.mWrapSelectorWheel && iArr[2] >= this.mMaxValue) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
            if (i2 != i) {
                onScrollChanged(0, i, 0, i2);
            }
        }
    }

    @Override // android.view.View
    protected int computeVerticalScrollOffset() {
        return this.mCurrentScrollOffset;
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        return ((this.mMaxValue - this.mMinValue) + 1) * this.mSelectorElementHeight;
    }

    @Override // android.view.View
    protected int computeVerticalScrollExtent() {
        return getHeight();
    }

    @Override // android.view.View
    public int getSolidColor() {
        return this.mSolidColor;
    }

    public void setOnValueChangedListener(OnValueChangeListener onValueChangedListener) {
        this.mOnValueChangeListener = onValueChangedListener;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setFormatter(Formatter formatter) {
        if (formatter == this.mFormatter) {
            return;
        }
        this.mFormatter = formatter;
        initializeSelectorWheelIndices();
    }

    public void setValue(int value) {
        setValueInternal(value, false);
    }

    @Override // android.view.View
    public boolean performClick() {
        return super.performClick();
    }

    @Override // android.view.View
    public boolean performLongClick() {
        return super.performLongClick();
    }

    private void tryComputeMaxWidth() {
        int i;
        if (this.mComputeMaxWidth) {
            String[] strArr = this.mDisplayedValues;
            int i2 = 0;
            if (strArr == null) {
                float f = 0.0f;
                for (int i3 = 0; i3 <= 9; i3++) {
                    float measureText = this.mSelectorWheelPaint.measureText(formatNumberWithLocale(i3));
                    if (measureText > f) {
                        f = measureText;
                    }
                }
                for (int i4 = this.mMaxValue; i4 > 0; i4 /= 10) {
                    i2++;
                }
                i = (int) (i2 * f);
            } else {
                int length = strArr.length;
                int length2 = strArr.length;
                int i5 = 0;
                while (i2 < length2) {
                    String str = strArr[i2];
                    if (!TextUtils.isEmpty(this.mPrefixSelectionStr)) {
                        str = this.mPrefixSelectionStr + str;
                    }
                    if (!TextUtils.isEmpty(this.mSuffixSelectionStr)) {
                        str = str + this.mSuffixSelectionStr;
                    }
                    float measureText2 = this.mSelectorWheelPaint.measureText(str);
                    if (measureText2 > i5) {
                        i5 = (int) measureText2;
                    }
                    i2++;
                }
                this.mSelectorWheelPaint.setColor(this.mTextColors.getDefaultColor());
                this.mSelectorWheelPaint.setTextSize(this.mSelectedTextSize);
                i = i5;
            }
            if (this.mMaxWidth != i) {
                int i6 = this.mMinWidth;
                if (i > i6) {
                    this.mMaxWidth = i;
                } else {
                    this.mMaxWidth = i6;
                }
                invalidate();
            }
        }
    }

    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    public void setWrapSelectorWheel(boolean wrapSelectorWheel) {
        this.mWrapSelectorWheelPreferred = wrapSelectorWheel;
        updateWrapSelectorWheel();
    }

    private void updateWrapSelectorWheel() {
        boolean z = true;
        if (!((this.mMaxValue - this.mMinValue) + 1 >= this.mSelectorIndices.length) || !this.mWrapSelectorWheelPreferred) {
            z = false;
        }
        this.mWrapSelectorWheel = z;
    }

    public void setOnLongPressUpdateInterval(long intervalMillis) {
        this.mLongPressUpdateInterval = intervalMillis;
    }

    public int getValue() {
        return this.mValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public void setMinValue(int minValue) {
        if (this.mMinValue == minValue) {
            return;
        }
        if (minValue < 0) {
            throw new IllegalArgumentException("minValue must be >= 0");
        }
        this.mMinValue = minValue;
        if (minValue > this.mValue) {
            this.mValue = minValue;
        }
        updateWrapSelectorWheel();
        invalidate();
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public void setMaxValue(int maxValue) {
        if (this.mMaxValue == maxValue) {
            return;
        }
        if (maxValue < 0) {
            throw new IllegalArgumentException("maxValue must be >= 0");
        }
        this.mMaxValue = maxValue;
        if (maxValue < this.mValue) {
            this.mValue = maxValue;
        }
        updateWrapSelectorWheel();
        invalidate();
    }

    public String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public void setDisplayedValues(String[] displayedValues) {
        if (this.mDisplayedValues == displayedValues) {
            return;
        }
        this.mDisplayedValues = displayedValues;
        initializeSelectorWheelIndices();
        tryComputeMaxWidth();
    }

    public void setDisplayedValues(String[] displayedValues, String prefix, String suffix) {
        setPrefixStringSelection(prefix);
        setSuffixStringSelection(suffix);
        setDisplayedValues(displayedValues);
    }

    public CharSequence getDisplayedValueForCurrentSelection() {
        return this.mSelectorIndexToStringCache.get(getValue());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ThemeViewModel themeViewModel = this.mThemeViewModel;
        if (themeViewModel != null) {
            themeViewModel.onDetachedFromWindow(this);
        }
        removeAllCallbacks();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mSelectionDivider;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mSelectionDivider;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0086  */
    @Override // android.widget.LinearLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onDraw(android.graphics.Canvas r13) {
        /*
            Method dump skipped, instructions count: 367
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.widget.NumberPickerView.onDraw(android.graphics.Canvas):void");
    }

    private int makeMeasureSpec(int measureSpec, int maxSize) {
        if (maxSize == -1) {
            return measureSpec;
        }
        int size = View.MeasureSpec.getSize(measureSpec);
        int mode = View.MeasureSpec.getMode(measureSpec);
        if (mode != Integer.MIN_VALUE) {
            if (mode != 0) {
                if (mode == 1073741824) {
                    return measureSpec;
                }
                throw new IllegalArgumentException("Unknown measure mode: " + mode);
            }
            return View.MeasureSpec.makeMeasureSpec(maxSize, BasicMeasure.EXACTLY);
        }
        return View.MeasureSpec.makeMeasureSpec(Math.min(size, maxSize), BasicMeasure.EXACTLY);
    }

    private int resolveSizeAndStateRespectingMinSize(int minSize, int measuredSize, int measureSpec) {
        return minSize != -1 ? resolveSizeAndState(Math.max(minSize, measuredSize), measureSpec, 0) : measuredSize;
    }

    private void initializeSelectorWheelIndices() {
        this.mSelectorIndexToStringCache.clear();
        int[] iArr = this.mSelectorIndices;
        int value = getValue();
        for (int i = 0; i < this.mSelectorIndices.length; i++) {
            int i2 = (i - 2) + value;
            if (this.mWrapSelectorWheel) {
                i2 = getWrappedSelectorIndex(i2);
            }
            iArr[i] = i2;
            ensureCachedScrollSelectorValue(iArr[i]);
        }
    }

    private void setValueInternal(int current, boolean notifyChange) {
        int min;
        if (this.mValue == current) {
            return;
        }
        if (this.mWrapSelectorWheel) {
            min = getWrappedSelectorIndex(current);
        } else {
            min = Math.min(Math.max(current, this.mMinValue), this.mMaxValue);
        }
        int i = this.mValue;
        this.mValue = min;
        if (notifyChange) {
            if (System.currentTimeMillis() - this.soundTime > 100) {
                this.soundTime = System.currentTimeMillis();
                XSoundEffectManager.get().play(2);
            }
            notifyChange(i, min);
        }
        initializeSelectorWheelIndices();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeValueByOne(boolean increment) {
        if (!moveToFinalScrollerPosition(this.mFlingScroller)) {
            moveToFinalScrollerPosition(this.mAdjustScroller);
        }
        this.mPreviousScrollerY = 0;
        if (increment) {
            this.mFlingScroller.startScroll(0, 0, 0, -this.mSelectorElementHeight, 300);
        } else {
            this.mFlingScroller.startScroll(0, 0, 0, this.mSelectorElementHeight, 300);
        }
        invalidate();
    }

    private void initializeSelectorWheel() {
        initializeSelectorWheelIndices();
        int[] iArr = this.mSelectorIndices;
        int bottom = (int) ((((getBottom() - getTop()) - (((iArr.length - 1) * this.mTextSize) + this.mSelectedTextSize)) / iArr.length) + 0.5f);
        this.mSelectorTextGapHeight = bottom;
        this.mSelectorElementHeight = this.mTextSize + bottom;
        int measuredHeight = ((((getMeasuredHeight() - (this.mSelectorElementHeight * 3)) - this.mSelectedTextSize) - this.mSelectorTextGapHeight) / 2) + (this.mTextSize / 2);
        this.mInitialScrollOffset = measuredHeight;
        this.mCurrentScrollOffset = measuredHeight;
    }

    private void initializeFadingEdges() {
        setVerticalFadingEdgeEnabled(true);
        setFadingEdgeLength(((getBottom() - getTop()) - this.mTextSize) / 2);
    }

    private void onScrollerFinished(Scroller scroller) {
        if (scroller == this.mFlingScroller) {
            ensureScrollWheelAdjusted();
            onScrollStateChange(0);
        }
    }

    private void onScrollStateChange(int scrollState) {
        if (this.mScrollState == scrollState) {
            return;
        }
        this.mScrollState = scrollState;
        OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChange(this, scrollState);
        }
    }

    private void fling(int velocityY) {
        this.mPreviousScrollerY = 0;
        if (velocityY > 0) {
            this.mFlingScroller.fling(0, 0, 0, velocityY, 0, 0, 0, Integer.MAX_VALUE);
        } else {
            this.mFlingScroller.fling(0, Integer.MAX_VALUE, 0, velocityY, 0, 0, 0, Integer.MAX_VALUE);
        }
        invalidate();
    }

    private int getWrappedSelectorIndex(int selectorIndex) {
        int i = this.mMaxValue;
        if (selectorIndex > i) {
            int i2 = this.mMinValue;
            return (i2 + ((selectorIndex - i) % (i - i2))) - 1;
        }
        int i3 = this.mMinValue;
        return selectorIndex < i3 ? (i - ((i3 - selectorIndex) % (i - i3))) + 1 : selectorIndex;
    }

    private void incrementSelectorIndices(int[] selectorIndices) {
        if (selectorIndices.length - 1 >= 0) {
            System.arraycopy(selectorIndices, 1, selectorIndices, 0, selectorIndices.length - 1);
        }
        int i = selectorIndices[selectorIndices.length - 2] + 1;
        if (this.mWrapSelectorWheel && i > this.mMaxValue) {
            i = this.mMinValue;
        }
        selectorIndices[selectorIndices.length - 1] = i;
        ensureCachedScrollSelectorValue(i);
    }

    private void decrementSelectorIndices(int[] selectorIndices) {
        if (selectorIndices.length - 1 >= 0) {
            System.arraycopy(selectorIndices, 0, selectorIndices, 1, selectorIndices.length - 1);
        }
        int i = selectorIndices[1] - 1;
        if (this.mWrapSelectorWheel && i < this.mMinValue) {
            i = this.mMaxValue;
        }
        selectorIndices[0] = i;
        ensureCachedScrollSelectorValue(i);
    }

    private void ensureCachedScrollSelectorValue(int selectorIndex) {
        String str;
        SparseArray<String> sparseArray = this.mSelectorIndexToStringCache;
        if (sparseArray.get(selectorIndex) != null) {
            return;
        }
        int i = this.mMinValue;
        if (selectorIndex < i || selectorIndex > this.mMaxValue) {
            str = "";
        } else {
            String[] strArr = this.mDisplayedValues;
            if (strArr != null) {
                str = strArr[selectorIndex - i];
            } else {
                str = formatNumber(selectorIndex);
            }
        }
        sparseArray.put(selectorIndex, str);
    }

    private String formatNumber(int value) {
        Formatter formatter = this.mFormatter;
        return formatter != null ? formatter.format(value) : formatNumberWithLocale(value);
    }

    private void notifyChange(int previous, int current) {
        OnValueChangeListener onValueChangeListener = this.mOnValueChangeListener;
        if (onValueChangeListener != null) {
            onValueChangeListener.onValueChange(this, previous, this.mValue);
        }
    }

    private void postChangeCurrentByOneFromLongPress(boolean increment, long delayMillis) {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand == null) {
            this.mChangeCurrentByOneFromLongPressCommand = new ChangeCurrentByOneFromLongPressCommand();
        } else {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
        this.mChangeCurrentByOneFromLongPressCommand.setStep(increment);
        postDelayed(this.mChangeCurrentByOneFromLongPressCommand, delayMillis);
    }

    private void removeChangeCurrentByOneFromLongPress() {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
    }

    private void postBeginSoftInputOnLongPressCommand() {
        BeginSoftInputOnLongPressCommand beginSoftInputOnLongPressCommand = this.mBeginSoftInputOnLongPressCommand;
        if (beginSoftInputOnLongPressCommand == null) {
            this.mBeginSoftInputOnLongPressCommand = new BeginSoftInputOnLongPressCommand();
        } else {
            removeCallbacks(beginSoftInputOnLongPressCommand);
        }
        postDelayed(this.mBeginSoftInputOnLongPressCommand, ViewConfiguration.getLongPressTimeout());
    }

    private void removeBeginSoftInputCommand() {
        BeginSoftInputOnLongPressCommand beginSoftInputOnLongPressCommand = this.mBeginSoftInputOnLongPressCommand;
        if (beginSoftInputOnLongPressCommand != null) {
            removeCallbacks(beginSoftInputOnLongPressCommand);
        }
    }

    private void removeAllCallbacks() {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
        BeginSoftInputOnLongPressCommand beginSoftInputOnLongPressCommand = this.mBeginSoftInputOnLongPressCommand;
        if (beginSoftInputOnLongPressCommand != null) {
            removeCallbacks(beginSoftInputOnLongPressCommand);
        }
        this.mPressedStateHelper.cancel();
    }

    private int getSelectedPos(String value) {
        try {
            if (this.mDisplayedValues == null) {
                return Integer.parseInt(value);
            }
            for (int i = 0; i < this.mDisplayedValues.length; i++) {
                value = value.toLowerCase();
                if (this.mDisplayedValues[i].toLowerCase().startsWith(value)) {
                    return this.mMinValue + i;
                }
            }
            return Integer.parseInt(value);
        } catch (NumberFormatException unused) {
            return this.mMinValue;
        }
    }

    private boolean ensureScrollWheelAdjusted() {
        int i = this.mInitialScrollOffset - this.mCurrentScrollOffset;
        if (i != 0) {
            this.mPreviousScrollerY = 0;
            int abs = Math.abs(i);
            int i2 = this.mSelectorElementHeight;
            if (abs > i2 / 2) {
                if (i > 0) {
                    i2 = -i2;
                }
                i += i2;
            }
            this.mAdjustScroller.startScroll(0, 0, 0, i, SELECTOR_ADJUSTMENT_DURATION_MILLIS);
            invalidate();
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class PressedStateHelper implements Runnable {
        public static final int BUTTON_DECREMENT = 2;
        public static final int BUTTON_INCREMENT = 1;
        private final int MODE_PRESS = 1;
        private final int MODE_TAPPED = 2;
        private int mManagedButton;
        private int mMode;

        PressedStateHelper() {
        }

        public void cancel() {
            this.mMode = 0;
            this.mManagedButton = 0;
            NumberPickerView.this.removeCallbacks(this);
        }

        public void buttonPressDelayed(int button) {
            cancel();
            this.mMode = 1;
            this.mManagedButton = button;
            NumberPickerView.this.postDelayed(this, ViewConfiguration.getTapTimeout());
        }

        public void buttonTapped(int button) {
            cancel();
            this.mMode = 2;
            this.mManagedButton = button;
            NumberPickerView.this.post(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mMode != 1) {
                return;
            }
            int i = this.mManagedButton;
            if (i == 1) {
                NumberPickerView numberPickerView = NumberPickerView.this;
                numberPickerView.invalidate(0, numberPickerView.mBottomSelectionDividerBottom, NumberPickerView.this.getRight(), NumberPickerView.this.getBottom());
            } else if (i != 2) {
            } else {
                NumberPickerView numberPickerView2 = NumberPickerView.this;
                numberPickerView2.invalidate(0, 0, numberPickerView2.getRight(), NumberPickerView.this.mTopSelectionDividerTop);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class ChangeCurrentByOneFromLongPressCommand implements Runnable {
        private boolean mIncrement;

        ChangeCurrentByOneFromLongPressCommand() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStep(boolean increment) {
            this.mIncrement = increment;
        }

        @Override // java.lang.Runnable
        public void run() {
            NumberPickerView.this.changeValueByOne(this.mIncrement);
            NumberPickerView numberPickerView = NumberPickerView.this;
            numberPickerView.postDelayed(this, numberPickerView.mLongPressUpdateInterval);
        }
    }

    /* loaded from: classes2.dex */
    public static class CustomEditText extends AppCompatEditText {
        public CustomEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override // android.widget.TextView
        public void onEditorAction(int actionCode) {
            super.onEditorAction(actionCode);
            if (actionCode == 6) {
                clearFocus();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class BeginSoftInputOnLongPressCommand implements Runnable {
        BeginSoftInputOnLongPressCommand() {
        }

        @Override // java.lang.Runnable
        public void run() {
            NumberPickerView.this.performLongClick();
        }
    }

    private static String formatNumberWithLocale(int value) {
        return String.format(Locale.getDefault(), TimeModel.NUMBER_FORMAT, Integer.valueOf(value));
    }

    private void setPrefixStringSelection(String prefix) {
        this.mPrefixSelectionStr = prefix;
    }

    private void setSuffixStringSelection(String suffix) {
        this.mSuffixSelectionStr = suffix;
    }
}
