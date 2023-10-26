package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.core.view.ViewCompat;
import com.google.android.material.timepicker.TimeModel;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.sound.XSoundEffectManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

/* loaded from: classes2.dex */
public class XNumberPicker extends XLinearLayout {
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
    private final PressedStateHelper mPressedStateHelper;
    private int mPreviousScrollerY;
    private int mScrollState;
    private int mSelectedSelectorElementHeight;
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
    private final Drawable mSymbol;
    private ColorStateList mTextColors;
    private int mTextLayout;
    private int mTextLayoutMargin;
    private final int mTextSize;
    private int mTopSelectionDividerTop;
    private int mTouchSlop;
    private int mValue;
    private VelocityTracker mVelocityTracker;
    private int mWidth;
    private boolean mWrapSelectorWheel;
    private boolean mWrapSelectorWheelPreferred;
    private static final TwoDigitFormatter sTwoDigitFormatter = new TwoDigitFormatter();
    private static final char[] DIGIT_CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 1632, 1633, 1634, 1635, 1636, 1637, 1638, 1639, 1640, 1641, 1776, 1777, 1778, 1779, 1780, 1781, 1782, 1783, 1784, 1785, 2406, 2407, 2408, 2409, 2410, 2411, 2412, 2413, 2414, 2415, 2534, 2535, 2536, 2537, 2538, 2539, 2540, 2541, 2542, 2543, 3302, 3303, 3304, 3305, 3306, 3307, 3308, 3309, 3310, 3311};

    /* loaded from: classes2.dex */
    public interface Formatter {
        String format(int i);
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

        void onScrollStateChange(XNumberPicker xNumberPicker, int i);
    }

    /* loaded from: classes2.dex */
    public interface OnValueChangeListener {
        void onValueChange(XNumberPicker xNumberPicker, int i, int i2);
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

        @Override // com.xiaopeng.xui.widget.XNumberPicker.Formatter
        public String format(int i) {
            Locale locale = Locale.getDefault();
            if (this.mZeroDigit != getZeroDigit(locale)) {
                init(locale);
            }
            this.mArgs[0] = Integer.valueOf(i);
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

    public XNumberPicker(Context context) {
        this(context, null);
    }

    public XNumberPicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.style.XNumberPicker);
    }

    public XNumberPicker(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.XNumberPicker);
    }

    public XNumberPicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mWrapSelectorWheelPreferred = true;
        this.mLongPressUpdateInterval = DEFAULT_LONG_PRESS_UPDATE_INTERVAL;
        this.mSelectorIndexToStringCache = new SparseArray<>();
        this.mSelectorIndices = new int[5];
        this.mInitialScrollOffset = Integer.MIN_VALUE;
        this.mScrollState = 0;
        this.mLastHandledDownDpadKeyCode = -1;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XNumberPicker, i, i2);
        this.mHideWheelUntilFocused = obtainStyledAttributes.getBoolean(R.styleable.XNumberPicker_np_hideWheelUntilFocused, false);
        this.mTextLayout = obtainStyledAttributes.getInt(R.styleable.XNumberPicker_np_textLayout, 1);
        this.mSolidColor = obtainStyledAttributes.getColor(R.styleable.XNumberPicker_np_solidColor, 0);
        Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.XNumberPicker_np_selectionDivider);
        if (drawable != null) {
            drawable.setCallback(this);
            drawable.setLayoutDirection(getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
        }
        this.mSelectionDivider = drawable;
        this.mSymbol = obtainStyledAttributes.getDrawable(R.styleable.XNumberPicker_np_symbol);
        this.mSelectionDividerHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XNumberPicker_np_selectionDividerHeight, (int) TypedValue.applyDimension(1, 2.0f, getResources().getDisplayMetrics()));
        this.mSelectionDividersDistance = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XNumberPicker_np_selectionDividersDistance, (int) TypedValue.applyDimension(1, 48.0f, getResources().getDisplayMetrics()));
        this.mTextLayoutMargin = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XNumberPicker_np_text_layout_margin, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XNumberPicker_np_internalMinHeight, -1);
        this.mMinHeight = dimensionPixelSize;
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XNumberPicker_np_internalMaxHeight, -1);
        this.mMaxHeight = dimensionPixelSize2;
        if (dimensionPixelSize != -1 && dimensionPixelSize2 != -1 && dimensionPixelSize > dimensionPixelSize2) {
            throw new IllegalArgumentException("minHeight > maxHeight");
        }
        int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XNumberPicker_np_internalMinWidth, -1);
        this.mMinWidth = dimensionPixelSize3;
        int dimensionPixelSize4 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XNumberPicker_np_internalMaxWidth, -1);
        this.mMaxWidth = dimensionPixelSize4;
        if (dimensionPixelSize3 != -1 && dimensionPixelSize4 != -1 && dimensionPixelSize3 > dimensionPixelSize4) {
            throw new IllegalArgumentException("minWidth > maxWidth");
        }
        this.mComputeMaxWidth = dimensionPixelSize4 == -1;
        int dimensionPixelSize5 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XNumberPicker_android_textSize, 20);
        this.mTextSize = dimensionPixelSize5;
        int dimensionPixelSize6 = obtainStyledAttributes.getDimensionPixelSize(R.styleable.XNumberPicker_np_selectedTextSize, 20);
        this.mSelectedTextSize = dimensionPixelSize6;
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.XNumberPicker_android_textColor);
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
        int i3 = this.mTextLayout;
        if (i3 == 0) {
            paint.setTextAlign(Paint.Align.LEFT);
        } else if (i3 == 1) {
            paint.setTextAlign(Paint.Align.CENTER);
        } else if (i3 == 2) {
            paint.setTextAlign(Paint.Align.RIGHT);
        }
        paint.setTextSize(Math.max(dimensionPixelSize5, dimensionPixelSize6));
        paint.setColor(this.mTextColors.getDefaultColor());
        paint.setTypeface(Typeface.create(getResources().getString(R.string.x_font_typeface_medium), 0));
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
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            initializeSelectorWheel();
            initializeFadingEdges();
            int height = getHeight();
            int i5 = this.mSelectionDividersDistance;
            int i6 = this.mSelectionDividerHeight;
            int i7 = (((height - i5) / 2) - i6) + 5;
            this.mTopSelectionDividerTop = i7;
            this.mBottomSelectionDividerBottom = i7 + (i6 * 2) + i5;
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(makeMeasureSpec(i, this.mMaxWidth), makeMeasureSpec(i2, this.mMaxHeight));
        int resolveSizeAndStateRespectingMinSize = resolveSizeAndStateRespectingMinSize(this.mMinWidth, getMeasuredWidth(), i);
        this.mWidth = resolveSizeAndStateRespectingMinSize;
        setMeasuredDimension(resolveSizeAndStateRespectingMinSize, resolveSizeAndStateRespectingMinSize(this.mMinHeight, getMeasuredHeight(), i2));
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
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (isEnabled() && motionEvent.getActionMasked() == 0) {
            removeAllCallbacks();
            float y = motionEvent.getY();
            this.mLastDownEventY = y;
            this.mLastDownOrMoveEventY = y;
            this.mLastDownEventTime = motionEvent.getEventTime();
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
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isEnabled()) {
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 1) {
                if (actionMasked == 2 && !this.mIgnoreMoveEvents) {
                    float y = motionEvent.getY();
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
                    int y2 = (int) motionEvent.getY();
                    int abs = (int) Math.abs(y2 - this.mLastDownEventY);
                    long eventTime = motionEvent.getEventTime() - this.mLastDownEventTime;
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
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            removeAllCallbacks();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (ThemeManager.isThemeChanged(configuration)) {
            this.mTextColors = getContext().getResources().getColorStateList(R.color.x_number_picker_text_color, null);
            postInvalidate();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 19 || keyCode == 20) {
            int action = keyEvent.getAction();
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
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            removeAllCallbacks();
        }
        return super.dispatchTrackballEvent(motionEvent);
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
    public void scrollBy(int i, int i2) {
        int i3;
        int[] iArr = this.mSelectorIndices;
        int i4 = this.mCurrentScrollOffset;
        boolean z = this.mWrapSelectorWheel;
        if (!z && i2 > 0 && iArr[2] <= this.mMinValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        } else if (!z && i2 < 0 && iArr[2] >= this.mMaxValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        } else {
            this.mCurrentScrollOffset = i2 + i4;
            while (true) {
                int i5 = this.mCurrentScrollOffset;
                int i6 = i5 - this.mInitialScrollOffset;
                int i7 = this.mSelectorTextGapHeight;
                if (i6 <= i7) {
                    break;
                }
                this.mCurrentScrollOffset = i5 - ((i7 * 2) + 1);
                decrementSelectorIndices(iArr);
                setValueInternal(iArr[2], true);
                if (!this.mWrapSelectorWheel && iArr[2] <= this.mMinValue) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
            while (true) {
                i3 = this.mCurrentScrollOffset;
                int i8 = i3 - this.mInitialScrollOffset;
                int i9 = this.mSelectorTextGapHeight;
                if (i8 >= (-i9)) {
                    break;
                }
                this.mCurrentScrollOffset = i3 + (i9 * 2) + 1;
                incrementSelectorIndices(iArr);
                setValueInternal(iArr[2], true);
                if (!this.mWrapSelectorWheel && iArr[2] >= this.mMaxValue) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
            if (i4 != i3) {
                onScrollChanged(0, i3, 0, i4);
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

    public void setOnValueChangedListener(OnValueChangeListener onValueChangeListener) {
        this.mOnValueChangeListener = onValueChangeListener;
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

    public void setValue(int i) {
        setValueInternal(i, false);
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
                    float measureText2 = this.mSelectorWheelPaint.measureText(strArr[i2]);
                    if (measureText2 > i5) {
                        i5 = (int) measureText2;
                    }
                    i2++;
                }
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

    public void setWrapSelectorWheel(boolean z) {
        this.mWrapSelectorWheelPreferred = z;
        updateWrapSelectorWheel();
    }

    private void updateWrapSelectorWheel() {
        boolean z = true;
        if (!((this.mMaxValue - this.mMinValue) + 1 >= this.mSelectorIndices.length) || !this.mWrapSelectorWheelPreferred) {
            z = false;
        }
        this.mWrapSelectorWheel = z;
    }

    public void setOnLongPressUpdateInterval(long j) {
        this.mLongPressUpdateInterval = j;
    }

    public int getValue() {
        return this.mValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public void setMinValue(int i) {
        if (this.mMinValue == i) {
            return;
        }
        if (i < 0) {
            throw new IllegalArgumentException("minValue must be >= 0");
        }
        this.mMinValue = i;
        if (i > this.mValue) {
            this.mValue = i;
        }
        updateWrapSelectorWheel();
        invalidate();
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public void setMaxValue(int i) {
        if (this.mMaxValue == i) {
            return;
        }
        if (i < 0) {
            throw new IllegalArgumentException("maxValue must be >= 0");
        }
        this.mMaxValue = i;
        if (i < this.mValue) {
            this.mValue = i;
        }
        updateWrapSelectorWheel();
        invalidate();
    }

    public String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public void setDisplayedValues(String[] strArr) {
        if (this.mDisplayedValues == strArr) {
            return;
        }
        this.mDisplayedValues = strArr;
        initializeSelectorWheelIndices();
        tryComputeMaxWidth();
    }

    public CharSequence getDisplayedValueForCurrentSelection() {
        return this.mSelectorIndexToStringCache.get(getValue());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
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

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(Canvas canvas) {
        Drawable drawable;
        int i;
        int i2;
        float f;
        int i3;
        float f2;
        boolean z = !this.mHideWheelUntilFocused || hasFocus();
        float f3 = this.mCurrentScrollOffset;
        int[] iArr = this.mSelectorIndices;
        for (int i4 = 0; i4 < iArr.length; i4++) {
            String str = this.mSelectorIndexToStringCache.get(iArr[i4]);
            if (z) {
                if (i4 == 2) {
                    this.mSelectorWheelPaint.setColor(this.mTextColors.getColorForState(SELECTED_STATE_SET, ViewCompat.MEASURED_STATE_MASK));
                    this.mSelectorWheelPaint.setTextSize(this.mSelectedTextSize);
                    int i5 = this.mTextLayout;
                    if (i5 == 0) {
                        i3 = this.mTextLayoutMargin;
                    } else if (i5 == 2) {
                        i3 = (getRight() - getLeft()) - this.mTextLayoutMargin;
                    } else {
                        f2 = (getRight() - getLeft()) / 2.0f;
                        canvas.drawText(str, f2, f3, this.mSelectorWheelPaint);
                    }
                    f2 = i3;
                    canvas.drawText(str, f2, f3, this.mSelectorWheelPaint);
                } else {
                    this.mSelectorWheelPaint.setColor(this.mTextColors.getDefaultColor());
                    this.mSelectorWheelPaint.setTextSize(this.mTextSize);
                    int i6 = this.mTextLayout;
                    if (i6 == 0) {
                        i2 = this.mTextLayoutMargin;
                    } else if (i6 == 2) {
                        i2 = (getRight() - getLeft()) - this.mTextLayoutMargin;
                    } else {
                        f = (getRight() - getLeft()) / 2.0f;
                        canvas.drawText(str, f, f3, this.mSelectorWheelPaint);
                    }
                    f = i2;
                    canvas.drawText(str, f, f3, this.mSelectorWheelPaint);
                }
            }
            if (i4 == 1) {
                i = this.mSelectedSelectorElementHeight;
            } else {
                i = this.mSelectorElementHeight;
            }
            f3 += i;
        }
        if (!z || (drawable = this.mSelectionDivider) == null) {
            return;
        }
        int i7 = this.mTopSelectionDividerTop;
        int i8 = this.mSelectionDividerHeight + i7;
        drawable.setBounds(0, i7, this.mWidth, i8);
        this.mSelectionDivider.draw(canvas);
        int i9 = this.mBottomSelectionDividerBottom;
        int i10 = i9 - this.mSelectionDividerHeight;
        this.mSelectionDivider.setBounds(0, i10, this.mWidth, i9);
        this.mSelectionDivider.draw(canvas);
        Drawable drawable2 = this.mSymbol;
        if (drawable2 != null) {
            int i11 = ((i8 * 2) + i10) / 3;
            int i12 = (i8 + (i10 * 2)) / 3;
            int i13 = this.mTextLayout;
            if (i13 != 0) {
                if (i13 != 2) {
                    return;
                }
                drawable2.setBounds(35, i11, 41, i11 + 6);
                this.mSymbol.draw(canvas);
                this.mSymbol.setBounds(35, i12 - 6, 41, i12);
                this.mSymbol.draw(canvas);
                return;
            }
            int i14 = this.mWidth;
            drawable2.setBounds(i14 - 6, i11, i14, i11 + 6);
            this.mSymbol.draw(canvas);
            Drawable drawable3 = this.mSymbol;
            int i15 = this.mWidth;
            drawable3.setBounds(i15 - 6, i12 - 6, i15, i12);
            this.mSymbol.draw(canvas);
        }
    }

    private int makeMeasureSpec(int i, int i2) {
        if (i2 == -1) {
            return i;
        }
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        if (mode != Integer.MIN_VALUE) {
            if (mode != 0) {
                if (mode == 1073741824) {
                    return i;
                }
                throw new IllegalArgumentException("Unknown measure mode: " + mode);
            }
            return View.MeasureSpec.makeMeasureSpec(i2, BasicMeasure.EXACTLY);
        }
        return View.MeasureSpec.makeMeasureSpec(Math.min(size, i2), BasicMeasure.EXACTLY);
    }

    private int resolveSizeAndStateRespectingMinSize(int i, int i2, int i3) {
        return i != -1 ? resolveSizeAndState(Math.max(i, i2), i3, 0) : i2;
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

    private void setValueInternal(int i, boolean z) {
        int min;
        if (this.mValue == i) {
            return;
        }
        if (this.mWrapSelectorWheel) {
            min = getWrappedSelectorIndex(i);
        } else {
            min = Math.min(Math.max(i, this.mMinValue), this.mMaxValue);
        }
        int i2 = this.mValue;
        this.mValue = min;
        if (z) {
            XSoundEffectManager.get().play(2);
            notifyChange(i2, min);
        }
        initializeSelectorWheelIndices();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeValueByOne(boolean z) {
        if (!moveToFinalScrollerPosition(this.mFlingScroller)) {
            moveToFinalScrollerPosition(this.mAdjustScroller);
        }
        this.mPreviousScrollerY = 0;
        if (z) {
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
        int i = this.mTextSize;
        this.mSelectorElementHeight = i + bottom;
        this.mSelectedSelectorElementHeight = this.mSelectedTextSize + bottom;
        int i2 = bottom + (i / 2);
        this.mInitialScrollOffset = i2;
        this.mCurrentScrollOffset = i2;
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

    private void onScrollStateChange(int i) {
        if (this.mScrollState == i) {
            return;
        }
        this.mScrollState = i;
        OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChange(this, i);
        }
    }

    private void fling(int i) {
        this.mPreviousScrollerY = 0;
        if (i > 0) {
            this.mFlingScroller.fling(0, 0, 0, i, 0, 0, 0, Integer.MAX_VALUE);
        } else {
            this.mFlingScroller.fling(0, Integer.MAX_VALUE, 0, i, 0, 0, 0, Integer.MAX_VALUE);
        }
        invalidate();
    }

    private int getWrappedSelectorIndex(int i) {
        int i2 = this.mMaxValue;
        if (i > i2) {
            int i3 = this.mMinValue;
            return (i3 + ((i - i2) % (i2 - i3))) - 1;
        }
        int i4 = this.mMinValue;
        return i < i4 ? (i2 - ((i4 - i) % (i2 - i4))) + 1 : i;
    }

    private void incrementSelectorIndices(int[] iArr) {
        if (iArr.length - 1 >= 0) {
            System.arraycopy(iArr, 1, iArr, 0, iArr.length - 1);
        }
        int i = iArr[iArr.length - 2] + 1;
        if (this.mWrapSelectorWheel && i > this.mMaxValue) {
            i = this.mMinValue;
        }
        iArr[iArr.length - 1] = i;
        ensureCachedScrollSelectorValue(i);
    }

    private void decrementSelectorIndices(int[] iArr) {
        if (iArr.length - 1 >= 0) {
            System.arraycopy(iArr, 0, iArr, 1, iArr.length - 1);
        }
        int i = iArr[1] - 1;
        if (this.mWrapSelectorWheel && i < this.mMinValue) {
            i = this.mMaxValue;
        }
        iArr[0] = i;
        ensureCachedScrollSelectorValue(i);
    }

    private void ensureCachedScrollSelectorValue(int i) {
        String str;
        SparseArray<String> sparseArray = this.mSelectorIndexToStringCache;
        if (sparseArray.get(i) != null) {
            return;
        }
        int i2 = this.mMinValue;
        if (i < i2 || i > this.mMaxValue) {
            str = "";
        } else {
            String[] strArr = this.mDisplayedValues;
            if (strArr != null) {
                str = strArr[i - i2];
            } else {
                str = formatNumber(i);
            }
        }
        sparseArray.put(i, str);
    }

    private String formatNumber(int i) {
        Formatter formatter = this.mFormatter;
        return formatter != null ? formatter.format(i) : formatNumberWithLocale(i);
    }

    private void notifyChange(int i, int i2) {
        OnValueChangeListener onValueChangeListener = this.mOnValueChangeListener;
        if (onValueChangeListener != null) {
            onValueChangeListener.onValueChange(this, i, this.mValue);
        }
    }

    private void postChangeCurrentByOneFromLongPress(boolean z, long j) {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand == null) {
            this.mChangeCurrentByOneFromLongPressCommand = new ChangeCurrentByOneFromLongPressCommand();
        } else {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
        this.mChangeCurrentByOneFromLongPressCommand.setStep(z);
        postDelayed(this.mChangeCurrentByOneFromLongPressCommand, j);
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

    private int getSelectedPos(String str) {
        try {
            if (this.mDisplayedValues == null) {
                return Integer.parseInt(str);
            }
            for (int i = 0; i < this.mDisplayedValues.length; i++) {
                str = str.toLowerCase();
                if (this.mDisplayedValues[i].toLowerCase().startsWith(str)) {
                    return this.mMinValue + i;
                }
            }
            return Integer.parseInt(str);
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
            XNumberPicker.this.removeCallbacks(this);
        }

        public void buttonPressDelayed(int i) {
            cancel();
            this.mMode = 1;
            this.mManagedButton = i;
            XNumberPicker.this.postDelayed(this, ViewConfiguration.getTapTimeout());
        }

        public void buttonTapped(int i) {
            cancel();
            this.mMode = 2;
            this.mManagedButton = i;
            XNumberPicker.this.post(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mMode != 1) {
                return;
            }
            int i = this.mManagedButton;
            if (i == 1) {
                XNumberPicker xNumberPicker = XNumberPicker.this;
                xNumberPicker.invalidate(0, xNumberPicker.mBottomSelectionDividerBottom, XNumberPicker.this.getRight(), XNumberPicker.this.getBottom());
            } else if (i != 2) {
            } else {
                XNumberPicker xNumberPicker2 = XNumberPicker.this;
                xNumberPicker2.invalidate(0, 0, xNumberPicker2.getRight(), XNumberPicker.this.mTopSelectionDividerTop);
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
        public void setStep(boolean z) {
            this.mIncrement = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            XNumberPicker.this.changeValueByOne(this.mIncrement);
            XNumberPicker xNumberPicker = XNumberPicker.this;
            xNumberPicker.postDelayed(this, xNumberPicker.mLongPressUpdateInterval);
        }
    }

    /* loaded from: classes2.dex */
    public static class CustomEditText extends AppCompatEditText {
        public CustomEditText(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override // android.widget.TextView
        public void onEditorAction(int i) {
            super.onEditorAction(i);
            if (i == 6) {
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
            XNumberPicker.this.performLongClick();
        }
    }

    private static String formatNumberWithLocale(int i) {
        return String.format(Locale.getDefault(), TimeModel.NUMBER_FORMAT, Integer.valueOf(i));
    }
}
