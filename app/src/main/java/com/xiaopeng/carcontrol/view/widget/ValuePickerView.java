package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ScrollerCompat;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xuimanager.utils.LogUtil;
import java.util.Objects;

/* loaded from: classes2.dex */
public class ValuePickerView extends View {
    private static final boolean DEFAULT_CURRENT_ITEM_INDEX_EFFECT = false;
    private static final int DEFAULT_DIVIDER_COLOR = -695533;
    private static final int DEFAULT_DIVIDER_HEIGHT = 2;
    private static final int DEFAULT_DIVIDER_MARGIN_HORIZONTAL = 0;
    private static final int DEFAULT_INTERVAL_REVISE_DURATION = 300;
    private static final int DEFAULT_ITEM_PADDING_DP_H = 5;
    private static final int DEFAULT_ITEM_PADDING_DP_V = 2;
    private static final int DEFAULT_MARGIN_END_OF_HINT_DP = 8;
    private static final int DEFAULT_MARGIN_START_OF_HINT_DP = 8;
    private static final int DEFAULT_MAX_SCROLL_BY_INDEX_DURATION = 600;
    private static final int DEFAULT_MIN_SCROLL_BY_INDEX_DURATION = 300;
    private static final boolean DEFAULT_RESPOND_CHANGE_IN_MAIN_THREAD = true;
    private static final boolean DEFAULT_RESPOND_CHANGE_ON_DETACH = false;
    private static final int DEFAULT_SHOW_COUNT = 3;
    private static final boolean DEFAULT_SHOW_DIVIDER = true;
    private static final int DEFAULT_TEXT_COLOR_NORMAL = -13421773;
    private static final int DEFAULT_TEXT_COLOR_SELECTED = -695533;
    private static final int DEFAULT_TEXT_SIZE_HINT_SP = 14;
    private static final int DEFAULT_TEXT_SIZE_NORMAL_SP = 14;
    private static final int DEFAULT_TEXT_SIZE_SELECTED_SP = 16;
    private static final boolean DEFAULT_WRAP_SELECTOR_WHEEL = true;
    private static final int HANDLER_INTERVAL_REFRESH = 32;
    private static final int HANDLER_WHAT_LISTENER_VALUE_CHANGED = 2;
    private static final int HANDLER_WHAT_REFRESH = 1;
    private static final int HANDLER_WHAT_REQUEST_LAYOUT = 3;
    private static final float SHADOW_OFFSET = 10.0f;
    private static final String TAG = "ValuePickerView";
    private static final String TEXT_ELLIPSIZE_END = "end";
    private static final String TEXT_ELLIPSIZE_MIDDLE = "middle";
    private static final String TEXT_ELLIPSIZE_START = "start";
    private float currY;
    private float dividerY0;
    private float dividerY1;
    private float downY;
    private float downYGlobal;
    private String mAlterHint;
    private CharSequence[] mAlterTextArrayWithMeasureHint;
    private CharSequence[] mAlterTextArrayWithoutMeasureHint;
    private int mCurrDrawFirstItemIndex;
    private int mCurrDrawFirstItemY;
    private int mCurrDrawGlobalY;
    private boolean mCurrentItemIndexEffect;
    private String[] mDisplayedValues;
    private int mDividerColor;
    private int mDividerHeight;
    private int mDividerIndex0;
    private int mDividerIndex1;
    private int mDividerMarginL;
    private int mDividerMarginR;
    private String mEmptyItemHint;
    private boolean mFlagMayPress;
    private float mFriction;
    private Handler mHandlerInMainThread;
    private Handler mHandlerInNewThread;
    private HandlerThread mHandlerThread;
    private boolean mHasInit;
    private String mHintText;
    private int mInScrollingPickedNewValue;
    private int mInScrollingPickedOldValue;
    private boolean mIsFromUser;
    private int mItemHeight;
    private int mItemPaddingHorizontal;
    private int mItemPaddingVertical;
    private int mMarginEndOfHint;
    private int mMarginStartOfHint;
    private int mMaxHeightOfDisplayedValues;
    private int mMaxShowIndex;
    private int mMaxValue;
    private int mMaxWidthOfAlterArrayWithMeasureHint;
    private int mMaxWidthOfAlterArrayWithoutMeasureHint;
    private int mMaxWidthOfDisplayedValues;
    private int mMinShowIndex;
    private int mMinValue;
    private int mMiniVelocityFling;
    private int mNotWrapLimitYBottom;
    private int mNotWrapLimitYTop;
    private OnScrollListener mOnScrollListener;
    private OnValueChangeListener mOnValueChangeListener;
    private OnValueChangeListenerInScrolling mOnValueChangeListenerInScrolling;
    private OnValueChangeListenerRelativeToRaw mOnValueChangeListenerRaw;
    private Paint mPaintDivider;
    private Paint mPaintHint;
    private TextPaint mPaintText;
    private boolean mPendingWrapToLinear;
    private int mPrevPickedIndex;
    private boolean mRespondChangeInMainThread;
    private boolean mRespondChangeOnDetach;
    private int mScaledTouchSlop;
    private int mScrollState;
    private ScrollerCompat mScroller;
    private int mShadowDirection;
    private int mShowCount;
    private boolean mShowDivider;
    private int mSpecModeH;
    private int mSpecModeW;
    private String mTextCelsius;
    private int mTextColorHint;
    private int mTextColorNormal;
    private int mTextColorSelected;
    private String mTextEllipsize;
    private int mTextSizeHint;
    private float mTextSizeHintCenterYOffset;
    private int mTextSizeNormal;
    private float mTextSizeNormalCenterYOffset;
    private int mTextSizeSelected;
    private float mTextSizeSelectedCenterYOffset;
    private int mTextSizeSufNormal;
    private int mTextSizeSufSelected;
    private VelocityTracker mVelocityTracker;
    private float mViewCenterX;
    private int mViewHeight;
    private int mViewWidth;
    private int mWidthOfAlterHint;
    private int mWidthOfHintText;
    private boolean mWrapSelectorWheel;
    private boolean mWrapSelectorWheelCheck;
    private long playSoundTime;

    /* loaded from: classes2.dex */
    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        void onScrollStateChange(ValuePickerView view, int scrollState);
    }

    /* loaded from: classes2.dex */
    public interface OnValueChangeListener {
        void onTouchDown();

        void onTouchUp();

        void onValueChange(ValuePickerView picker, int oldVal, int newVal);
    }

    /* loaded from: classes2.dex */
    public interface OnValueChangeListenerInScrolling {
        void onValueChangeInScrolling(ValuePickerView picker, int oldVal, int newVal);
    }

    /* loaded from: classes2.dex */
    public interface OnValueChangeListenerRelativeToRaw {
        void onValueChangeRelativeToRaw(ValuePickerView picker, int oldPickedIndex, int newPickedIndex, String[] displayedValues);
    }

    private int getEvaluateAlpha(float fraction, int startColor) {
        float f = ((-16777216) & startColor) >>> 24;
        return (((int) (f - (fraction * f))) << 24) | (((16711680 & startColor) >>> 16) << 16) | (((65280 & startColor) >>> 8) << 8) | ((startColor & 255) >>> 0);
    }

    private int getEvaluateColor(float fraction, int startColor, int endColor) {
        int i = (startColor & ViewCompat.MEASURED_STATE_MASK) >>> 24;
        int i2 = (startColor & 16711680) >>> 16;
        int i3 = (startColor & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>> 8;
        int i4 = (startColor & 255) >>> 0;
        return ((int) (i4 + ((((endColor & 255) >>> 0) - i4) * fraction))) | (((int) (i + (((((-16777216) & endColor) >>> 24) - i) * fraction))) << 24) | (((int) (i2 + ((((16711680 & endColor) >>> 16) - i2) * fraction))) << 16) | (((int) (i3 + ((((65280 & endColor) >>> 8) - i3) * fraction))) << 8);
    }

    private float getEvaluateSize(float fraction, float startSize, float endSize) {
        return startSize + ((endSize - startSize) * fraction);
    }

    public ValuePickerView(Context context) {
        super(context);
        this.mTextColorNormal = DEFAULT_TEXT_COLOR_NORMAL;
        this.mTextColorSelected = -695533;
        this.mTextColorHint = -695533;
        this.mTextSizeNormal = 0;
        this.mTextSizeSelected = 0;
        this.mTextSizeHint = 0;
        this.mWidthOfHintText = 0;
        this.mWidthOfAlterHint = 0;
        this.mMarginStartOfHint = 0;
        this.mMarginEndOfHint = 0;
        this.mItemPaddingVertical = 0;
        this.mItemPaddingHorizontal = 0;
        this.mDividerColor = -695533;
        this.mDividerHeight = 2;
        this.mDividerMarginL = 0;
        this.mDividerMarginR = 0;
        this.mShowCount = 3;
        this.mDividerIndex0 = 0;
        this.mDividerIndex1 = 0;
        this.mMinShowIndex = -1;
        this.mMaxShowIndex = -1;
        this.mMinValue = 0;
        this.mMaxValue = 0;
        this.mMaxWidthOfDisplayedValues = 0;
        this.mMaxHeightOfDisplayedValues = 0;
        this.mMaxWidthOfAlterArrayWithMeasureHint = 0;
        this.mMaxWidthOfAlterArrayWithoutMeasureHint = 0;
        this.mPrevPickedIndex = 0;
        this.mMiniVelocityFling = IHvacViewModel.HVAC_INNER_PM25_LEVEL_MIDDLE;
        this.mScaledTouchSlop = 8;
        this.mFriction = 1.0f;
        this.mTextSizeNormalCenterYOffset = 0.0f;
        this.mTextSizeSelectedCenterYOffset = 0.0f;
        this.mTextSizeHintCenterYOffset = 0.0f;
        this.mShowDivider = true;
        this.mWrapSelectorWheel = true;
        this.mCurrentItemIndexEffect = false;
        this.mHasInit = false;
        this.mWrapSelectorWheelCheck = true;
        this.mPendingWrapToLinear = false;
        this.mRespondChangeOnDetach = false;
        this.mRespondChangeInMainThread = true;
        this.mPaintDivider = new Paint();
        this.mPaintText = new TextPaint();
        this.mPaintHint = new Paint();
        this.mScrollState = 0;
        this.downYGlobal = 0.0f;
        this.downY = 0.0f;
        this.currY = 0.0f;
        this.mFlagMayPress = false;
        this.mCurrDrawFirstItemIndex = 0;
        this.mCurrDrawFirstItemY = 0;
        this.mCurrDrawGlobalY = 0;
        this.mSpecModeW = 0;
        this.mSpecModeH = 0;
        init(context);
    }

    public ValuePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTextColorNormal = DEFAULT_TEXT_COLOR_NORMAL;
        this.mTextColorSelected = -695533;
        this.mTextColorHint = -695533;
        this.mTextSizeNormal = 0;
        this.mTextSizeSelected = 0;
        this.mTextSizeHint = 0;
        this.mWidthOfHintText = 0;
        this.mWidthOfAlterHint = 0;
        this.mMarginStartOfHint = 0;
        this.mMarginEndOfHint = 0;
        this.mItemPaddingVertical = 0;
        this.mItemPaddingHorizontal = 0;
        this.mDividerColor = -695533;
        this.mDividerHeight = 2;
        this.mDividerMarginL = 0;
        this.mDividerMarginR = 0;
        this.mShowCount = 3;
        this.mDividerIndex0 = 0;
        this.mDividerIndex1 = 0;
        this.mMinShowIndex = -1;
        this.mMaxShowIndex = -1;
        this.mMinValue = 0;
        this.mMaxValue = 0;
        this.mMaxWidthOfDisplayedValues = 0;
        this.mMaxHeightOfDisplayedValues = 0;
        this.mMaxWidthOfAlterArrayWithMeasureHint = 0;
        this.mMaxWidthOfAlterArrayWithoutMeasureHint = 0;
        this.mPrevPickedIndex = 0;
        this.mMiniVelocityFling = IHvacViewModel.HVAC_INNER_PM25_LEVEL_MIDDLE;
        this.mScaledTouchSlop = 8;
        this.mFriction = 1.0f;
        this.mTextSizeNormalCenterYOffset = 0.0f;
        this.mTextSizeSelectedCenterYOffset = 0.0f;
        this.mTextSizeHintCenterYOffset = 0.0f;
        this.mShowDivider = true;
        this.mWrapSelectorWheel = true;
        this.mCurrentItemIndexEffect = false;
        this.mHasInit = false;
        this.mWrapSelectorWheelCheck = true;
        this.mPendingWrapToLinear = false;
        this.mRespondChangeOnDetach = false;
        this.mRespondChangeInMainThread = true;
        this.mPaintDivider = new Paint();
        this.mPaintText = new TextPaint();
        this.mPaintHint = new Paint();
        this.mScrollState = 0;
        this.downYGlobal = 0.0f;
        this.downY = 0.0f;
        this.currY = 0.0f;
        this.mFlagMayPress = false;
        this.mCurrDrawFirstItemIndex = 0;
        this.mCurrDrawFirstItemY = 0;
        this.mCurrDrawGlobalY = 0;
        this.mSpecModeW = 0;
        this.mSpecModeH = 0;
        initAttr(context, attrs);
        init(context);
    }

    public ValuePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mTextColorNormal = DEFAULT_TEXT_COLOR_NORMAL;
        this.mTextColorSelected = -695533;
        this.mTextColorHint = -695533;
        this.mTextSizeNormal = 0;
        this.mTextSizeSelected = 0;
        this.mTextSizeHint = 0;
        this.mWidthOfHintText = 0;
        this.mWidthOfAlterHint = 0;
        this.mMarginStartOfHint = 0;
        this.mMarginEndOfHint = 0;
        this.mItemPaddingVertical = 0;
        this.mItemPaddingHorizontal = 0;
        this.mDividerColor = -695533;
        this.mDividerHeight = 2;
        this.mDividerMarginL = 0;
        this.mDividerMarginR = 0;
        this.mShowCount = 3;
        this.mDividerIndex0 = 0;
        this.mDividerIndex1 = 0;
        this.mMinShowIndex = -1;
        this.mMaxShowIndex = -1;
        this.mMinValue = 0;
        this.mMaxValue = 0;
        this.mMaxWidthOfDisplayedValues = 0;
        this.mMaxHeightOfDisplayedValues = 0;
        this.mMaxWidthOfAlterArrayWithMeasureHint = 0;
        this.mMaxWidthOfAlterArrayWithoutMeasureHint = 0;
        this.mPrevPickedIndex = 0;
        this.mMiniVelocityFling = IHvacViewModel.HVAC_INNER_PM25_LEVEL_MIDDLE;
        this.mScaledTouchSlop = 8;
        this.mFriction = 1.0f;
        this.mTextSizeNormalCenterYOffset = 0.0f;
        this.mTextSizeSelectedCenterYOffset = 0.0f;
        this.mTextSizeHintCenterYOffset = 0.0f;
        this.mShowDivider = true;
        this.mWrapSelectorWheel = true;
        this.mCurrentItemIndexEffect = false;
        this.mHasInit = false;
        this.mWrapSelectorWheelCheck = true;
        this.mPendingWrapToLinear = false;
        this.mRespondChangeOnDetach = false;
        this.mRespondChangeInMainThread = true;
        this.mPaintDivider = new Paint();
        this.mPaintText = new TextPaint();
        this.mPaintHint = new Paint();
        this.mScrollState = 0;
        this.downYGlobal = 0.0f;
        this.downY = 0.0f;
        this.currY = 0.0f;
        this.mFlagMayPress = false;
        this.mCurrDrawFirstItemIndex = 0;
        this.mCurrDrawFirstItemY = 0;
        this.mCurrDrawGlobalY = 0;
        this.mSpecModeW = 0;
        this.mSpecModeH = 0;
        initAttr(context, attrs);
        init(context);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ValuePickerView);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == R.styleable.ValuePickerView_npv_ShowCount) {
                this.mShowCount = obtainStyledAttributes.getInt(index, 3);
            } else if (index == R.styleable.ValuePickerView_npv_DividerColor) {
                this.mDividerColor = obtainStyledAttributes.getColor(index, -695533);
            } else if (index == R.styleable.ValuePickerView_npv_DividerHeight) {
                this.mDividerHeight = obtainStyledAttributes.getDimensionPixelSize(index, 2);
            } else if (index == R.styleable.ValuePickerView_npv_DividerMarginLeft) {
                this.mDividerMarginL = obtainStyledAttributes.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.ValuePickerView_npv_DividerMarginRight) {
                this.mDividerMarginR = obtainStyledAttributes.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.ValuePickerView_npv_TextArray) {
                this.mDisplayedValues = convertCharSequenceArrayToStringArray(obtainStyledAttributes.getTextArray(index));
            } else if (index == R.styleable.ValuePickerView_npv_TextColorNormal) {
                this.mTextColorNormal = obtainStyledAttributes.getColor(index, DEFAULT_TEXT_COLOR_NORMAL);
            } else if (index == R.styleable.ValuePickerView_npv_TextColorSelected) {
                this.mTextColorSelected = obtainStyledAttributes.getColor(index, -695533);
            } else if (index == R.styleable.ValuePickerView_npv_TextColorHint) {
                this.mTextColorHint = obtainStyledAttributes.getColor(index, -695533);
            } else if (index == R.styleable.ValuePickerView_npv_TextSizeNormal) {
                this.mTextSizeNormal = obtainStyledAttributes.getDimensionPixelSize(index, sp2px(context, 14.0f));
            } else if (index == R.styleable.ValuePickerView_npv_TextSizeSelected) {
                this.mTextSizeSelected = obtainStyledAttributes.getDimensionPixelSize(index, sp2px(context, 16.0f));
            } else if (index == R.styleable.ValuePickerView_npv_TextSizeHint) {
                this.mTextSizeHint = obtainStyledAttributes.getDimensionPixelSize(index, sp2px(context, 14.0f));
            } else if (index == R.styleable.ValuePickerView_npv_TextSizeSufNormal) {
                this.mTextSizeSufNormal = obtainStyledAttributes.getDimensionPixelSize(index, sp2px(context, 14.0f));
            } else if (index == R.styleable.ValuePickerView_npv_TextSizeSufSelected) {
                this.mTextSizeSufSelected = obtainStyledAttributes.getDimensionPixelSize(index, sp2px(context, 16.0f));
            } else if (index == R.styleable.ValuePickerView_npv_MinValue) {
                this.mMinShowIndex = obtainStyledAttributes.getInteger(index, 0);
            } else if (index == R.styleable.ValuePickerView_npv_MaxValue) {
                this.mMaxShowIndex = obtainStyledAttributes.getInteger(index, 0);
            } else if (index == R.styleable.ValuePickerView_npv_WrapSelectorWheel) {
                this.mWrapSelectorWheel = obtainStyledAttributes.getBoolean(index, true);
            } else if (index == R.styleable.ValuePickerView_npv_ShowDivider) {
                this.mShowDivider = obtainStyledAttributes.getBoolean(index, true);
            } else if (index == R.styleable.ValuePickerView_npv_HintText) {
                this.mHintText = obtainStyledAttributes.getString(index);
            } else if (index == R.styleable.ValuePickerView_npv_AlternativeHint) {
                this.mAlterHint = obtainStyledAttributes.getString(index);
            } else if (index == R.styleable.ValuePickerView_npv_EmptyItemHint) {
                this.mEmptyItemHint = obtainStyledAttributes.getString(index);
            } else if (index == R.styleable.ValuePickerView_npv_MarginStartOfHint) {
                this.mMarginStartOfHint = obtainStyledAttributes.getDimensionPixelSize(index, dp2px(context, 8.0f));
            } else if (index == R.styleable.ValuePickerView_npv_MarginEndOfHint) {
                this.mMarginEndOfHint = obtainStyledAttributes.getDimensionPixelSize(index, dp2px(context, 8.0f));
            } else if (index == R.styleable.ValuePickerView_npv_ItemPaddingVertical) {
                this.mItemPaddingVertical = obtainStyledAttributes.getDimensionPixelSize(index, dp2px(context, 2.0f));
            } else if (index == R.styleable.ValuePickerView_npv_ItemPaddingHorizontal) {
                this.mItemPaddingHorizontal = obtainStyledAttributes.getDimensionPixelSize(index, dp2px(context, 5.0f));
            } else if (index == R.styleable.ValuePickerView_npv_AlternativeTextArrayWithMeasureHint) {
                this.mAlterTextArrayWithMeasureHint = obtainStyledAttributes.getTextArray(index);
            } else if (index == R.styleable.ValuePickerView_npv_AlternativeTextArrayWithoutMeasureHint) {
                this.mAlterTextArrayWithoutMeasureHint = obtainStyledAttributes.getTextArray(index);
            } else if (index == R.styleable.ValuePickerView_npv_RespondChangeOnDetached) {
                this.mRespondChangeOnDetach = obtainStyledAttributes.getBoolean(index, false);
            } else if (index == R.styleable.ValuePickerView_npv_RespondChangeInMainThread) {
                this.mRespondChangeInMainThread = obtainStyledAttributes.getBoolean(index, true);
            } else if (index == R.styleable.ValuePickerView_npv_TextEllipsize) {
                this.mTextEllipsize = obtainStyledAttributes.getString(index);
            }
        }
        obtainStyledAttributes.recycle();
    }

    private void init(Context context) {
        this.mScroller = ScrollerCompat.create(context);
        this.mMiniVelocityFling = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        this.mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        if (this.mTextSizeNormal == 0) {
            this.mTextSizeNormal = sp2px(context, 14.0f);
        }
        if (this.mTextSizeSelected == 0) {
            this.mTextSizeSelected = sp2px(context, 16.0f);
        }
        if (this.mTextSizeHint == 0) {
            this.mTextSizeHint = sp2px(context, 14.0f);
        }
        if (this.mMarginStartOfHint == 0) {
            this.mMarginStartOfHint = dp2px(context, 8.0f);
        }
        if (this.mMarginEndOfHint == 0) {
            this.mMarginEndOfHint = dp2px(context, 8.0f);
        }
        this.mTextCelsius = context.getString(R.string.hvac_celsius);
        this.mPaintDivider.setColor(this.mDividerColor);
        this.mPaintDivider.setAntiAlias(true);
        this.mPaintDivider.setStyle(Paint.Style.STROKE);
        this.mPaintDivider.setStrokeWidth(this.mDividerHeight);
        this.mPaintText.setColor(this.mTextColorNormal);
        this.mPaintText.setAntiAlias(true);
        this.mPaintText.setTextAlign(Paint.Align.CENTER);
        this.mPaintText.setTypeface(Typeface.create(getContext().getString(R.string.x_font_typeface_number), 0));
        this.mPaintHint.setColor(this.mTextColorHint);
        this.mPaintHint.setAntiAlias(true);
        this.mPaintHint.setTextAlign(Paint.Align.CENTER);
        this.mPaintHint.setTextSize(this.mTextSizeHint);
        int i = this.mShowCount;
        if (i % 2 == 0) {
            this.mShowCount = i + 1;
        }
        if (this.mMinShowIndex == -1 || this.mMaxShowIndex == -1) {
            updateValueForInit();
        }
        initHandler();
    }

    private void initHandler() {
        HandlerThread handlerThread = new HandlerThread("HandlerThread-For-Refreshing");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandlerInNewThread = new Handler(this.mHandlerThread.getLooper()) { // from class: com.xiaopeng.carcontrol.view.widget.ValuePickerView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                int willPickIndexByGlobalY;
                int i;
                int i2 = msg.what;
                if (i2 != 1) {
                    if (i2 != 2) {
                        return;
                    }
                    ValuePickerView.this.respondPickedValueChanged(msg.arg1, msg.arg2, msg.obj);
                    return;
                }
                int i3 = 0;
                if (!ValuePickerView.this.mScroller.isFinished()) {
                    if (ValuePickerView.this.mScrollState == 0) {
                        ValuePickerView.this.onScrollStateChange(1);
                    }
                    if (ValuePickerView.this.mHandlerInNewThread != null) {
                        ValuePickerView.this.mHandlerInNewThread.sendMessageDelayed(ValuePickerView.this.getMsg(1, 0, 0, msg.obj), 32L);
                        return;
                    }
                    return;
                }
                if (ValuePickerView.this.mCurrDrawFirstItemY != 0) {
                    if (ValuePickerView.this.mScrollState == 0) {
                        ValuePickerView.this.onScrollStateChange(1);
                    }
                    if (ValuePickerView.this.mCurrDrawFirstItemY < (-ValuePickerView.this.mItemHeight) / 2) {
                        i = (int) (((ValuePickerView.this.mItemHeight + ValuePickerView.this.mCurrDrawFirstItemY) * 300.0f) / ValuePickerView.this.mItemHeight);
                        ValuePickerView.this.mScroller.startScroll(0, ValuePickerView.this.mCurrDrawGlobalY, 0, ValuePickerView.this.mCurrDrawFirstItemY + ValuePickerView.this.mItemHeight, i * 3);
                        ValuePickerView valuePickerView = ValuePickerView.this;
                        willPickIndexByGlobalY = valuePickerView.getWillPickIndexByGlobalY(valuePickerView.mCurrDrawGlobalY + ValuePickerView.this.mItemHeight + ValuePickerView.this.mCurrDrawFirstItemY);
                    } else {
                        i = (int) (((-ValuePickerView.this.mCurrDrawFirstItemY) * 300.0f) / ValuePickerView.this.mItemHeight);
                        ValuePickerView.this.mScroller.startScroll(0, ValuePickerView.this.mCurrDrawGlobalY, 0, ValuePickerView.this.mCurrDrawFirstItemY, i * 3);
                        ValuePickerView valuePickerView2 = ValuePickerView.this;
                        willPickIndexByGlobalY = valuePickerView2.getWillPickIndexByGlobalY(valuePickerView2.mCurrDrawGlobalY + ValuePickerView.this.mCurrDrawFirstItemY);
                    }
                    i3 = i;
                    ValuePickerView.this.postInvalidate();
                } else {
                    ValuePickerView.this.onScrollStateChange(0);
                    ValuePickerView valuePickerView3 = ValuePickerView.this;
                    willPickIndexByGlobalY = valuePickerView3.getWillPickIndexByGlobalY(valuePickerView3.mCurrDrawGlobalY);
                }
                ValuePickerView valuePickerView4 = ValuePickerView.this;
                Message msg2 = valuePickerView4.getMsg(2, valuePickerView4.mPrevPickedIndex, willPickIndexByGlobalY, msg.obj);
                if (ValuePickerView.this.mRespondChangeInMainThread) {
                    if (ValuePickerView.this.mHandlerInMainThread != null) {
                        ValuePickerView.this.mHandlerInMainThread.sendMessageDelayed(msg2, i3 * 2);
                    }
                } else if (ValuePickerView.this.mHandlerInNewThread != null) {
                    ValuePickerView.this.mHandlerInNewThread.sendMessageDelayed(msg2, i3 * 2);
                }
            }
        };
        this.mHandlerInMainThread = new Handler(Looper.getMainLooper()) { // from class: com.xiaopeng.carcontrol.view.widget.ValuePickerView.2
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int i = msg.what;
                if (i == 2) {
                    ValuePickerView.this.respondPickedValueChanged(msg.arg1, msg.arg2, msg.obj);
                } else if (i != 3) {
                } else {
                    ValuePickerView.this.requestLayout();
                }
            }
        };
    }

    private void respondPickedValueChangedInScrolling(int oldVal, int newVal) {
        this.mOnValueChangeListenerInScrolling.onValueChangeInScrolling(this, oldVal, newVal);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        updateMaxWHOfDisplayedValues(false);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int i;
        super.onSizeChanged(w, h, oldw, oldh);
        this.mViewWidth = w;
        this.mViewHeight = h;
        this.mItemHeight = h / this.mShowCount;
        this.mViewCenterX = ((w + getPaddingLeft()) - getPaddingRight()) / 2.0f;
        boolean z = false;
        if (getOneRecycleSize() > 1) {
            if (this.mHasInit) {
                i = getValue() - this.mMinValue;
            } else if (this.mCurrentItemIndexEffect) {
                i = this.mCurrDrawFirstItemIndex + ((this.mShowCount - 1) / 2);
            }
            if (this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck) {
                z = true;
            }
            correctPositionByDefaultValue(i, z);
            updateFontAttr();
            updateNotWrapYLimit();
            updateDividerAttr();
            this.mHasInit = true;
        }
        i = 0;
        if (this.mWrapSelectorWheel) {
            z = true;
        }
        correctPositionByDefaultValue(i, z);
        updateFontAttr();
        updateNotWrapYLimit();
        updateDividerAttr();
        this.mHasInit = true;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread == null || !handlerThread.isAlive()) {
            initHandler();
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Handler handler = this.mHandlerInNewThread;
        if (handler != null) {
            handler.removeMessages(1);
            this.mHandlerInNewThread.removeMessages(2);
        }
        Handler handler2 = this.mHandlerInMainThread;
        if (handler2 != null) {
            handler2.removeMessages(2);
            this.mHandlerInMainThread.removeMessages(3);
        }
        this.mHandlerThread.quit();
        if (this.mItemHeight == 0) {
            return;
        }
        if (!this.mScroller.isFinished()) {
            this.mScroller.abortAnimation();
            this.mCurrDrawGlobalY = this.mScroller.getCurrY();
            calculateFirstItemParameterByGlobalY();
            int i = this.mCurrDrawFirstItemY;
            if (i != 0) {
                int i2 = this.mItemHeight;
                if (i < (-i2) / 2) {
                    this.mCurrDrawGlobalY = this.mCurrDrawGlobalY + i2 + i;
                } else {
                    this.mCurrDrawGlobalY += i;
                }
                calculateFirstItemParameterByGlobalY();
            }
            onScrollStateChange(0);
        }
        int willPickIndexByGlobalY = getWillPickIndexByGlobalY(this.mCurrDrawGlobalY);
        int i3 = this.mPrevPickedIndex;
        if (willPickIndexByGlobalY != i3 && this.mRespondChangeOnDetach) {
            try {
                OnValueChangeListener onValueChangeListener = this.mOnValueChangeListener;
                if (onValueChangeListener != null) {
                    int i4 = this.mMinValue;
                    onValueChangeListener.onValueChange(this, i3 + i4, i4 + willPickIndexByGlobalY);
                }
                OnValueChangeListenerRelativeToRaw onValueChangeListenerRelativeToRaw = this.mOnValueChangeListenerRaw;
                if (onValueChangeListenerRelativeToRaw != null) {
                    onValueChangeListenerRelativeToRaw.onValueChangeRelativeToRaw(this, this.mPrevPickedIndex, willPickIndexByGlobalY, this.mDisplayedValues);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.mPrevPickedIndex = willPickIndexByGlobalY;
    }

    public int getOneRecycleSize() {
        return (this.mMaxShowIndex - this.mMinShowIndex) + 1;
    }

    public int getRawContentSize() {
        String[] strArr = this.mDisplayedValues;
        if (strArr != null) {
            return strArr.length;
        }
        return 0;
    }

    public void setDisplayedValuesAndPickedIndex(String[] newDisplayedValues, int pickedIndex, boolean needRefresh) {
        stopScrolling();
        if (newDisplayedValues == null) {
            throw new IllegalArgumentException("newDisplayedValues should not be null.");
        }
        if (pickedIndex < 0) {
            throw new IllegalArgumentException("pickedIndex should not be negative, now pickedIndex is " + pickedIndex);
        }
        updateContent(newDisplayedValues);
        updateMaxWHOfDisplayedValues(true);
        updateNotWrapYLimit();
        updateValue();
        this.mPrevPickedIndex = this.mMinShowIndex + pickedIndex;
        correctPositionByDefaultValue(pickedIndex, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        if (needRefresh) {
            Handler handler = this.mHandlerInNewThread;
            if (handler != null) {
                handler.sendMessageDelayed(getMsg(1), 0L);
            }
            postInvalidate();
        }
    }

    public void setDisplayedValues(String[] newDisplayedValues, boolean needRefresh) {
        setDisplayedValuesAndPickedIndex(newDisplayedValues, 0, needRefresh);
    }

    public void setDisplayedValues(String[] newDisplayedValues) {
        stopRefreshing();
        stopScrolling();
        if (newDisplayedValues == null) {
            throw new IllegalArgumentException("newDisplayedValues should not be null.");
        }
        boolean z = true;
        if ((this.mMaxValue - this.mMinValue) + 1 > newDisplayedValues.length) {
            throw new IllegalArgumentException("mMaxValue - mMinValue + 1 should not be greater than mDisplayedValues.length, now ((mMaxValue - mMinValue + 1) is " + ((this.mMaxValue - this.mMinValue) + 1) + " newDisplayedValues.length is " + newDisplayedValues.length + ", you need to set MaxValue and MinValue before setDisplayedValues(String[])");
        }
        updateContent(newDisplayedValues);
        updateMaxWHOfDisplayedValues(true);
        this.mPrevPickedIndex = this.mMinShowIndex + 0;
        if (!this.mWrapSelectorWheel || !this.mWrapSelectorWheelCheck) {
            z = false;
        }
        correctPositionByDefaultValue(0, z);
        postInvalidate();
        Handler handler = this.mHandlerInMainThread;
        if (handler != null) {
            handler.sendEmptyMessage(3);
        }
    }

    public String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public void setWrapSelectorWheel(boolean wrapSelectorWheel) {
        if (this.mWrapSelectorWheel != wrapSelectorWheel) {
            if (!wrapSelectorWheel) {
                if (this.mScrollState == 0) {
                    internalSetWrapToLinear();
                    return;
                } else {
                    this.mPendingWrapToLinear = true;
                    return;
                }
            }
            this.mWrapSelectorWheel = wrapSelectorWheel;
            updateWrapStateByContent();
            postInvalidate();
        }
    }

    public void smoothScrollToValue(int toValue) {
        smoothScrollToValue(getValue(), toValue, true);
    }

    public void smoothScrollToValueFromUser(int toValue) {
        this.mIsFromUser = true;
        smoothScrollToValue(getValue(), toValue, true);
    }

    public void smoothScrollToValue(int toValue, boolean needRespond) {
        LogUtils.d(TAG, hashCode() + ":toValue:" + toValue + ",mItemHeight:" + this.mItemHeight);
        if (this.mItemHeight <= 0) {
            return;
        }
        smoothScrollToValue(getValue(), toValue, needRespond);
    }

    public void smoothScrollToValue(int fromValue, int toValue) {
        smoothScrollToValue(fromValue, toValue, true);
    }

    public void smoothScrollToValue(int fromValue, int toValue, boolean needRespond) {
        int i;
        stopScrolling();
        Handler handler = this.mHandlerInMainThread;
        if (handler != null) {
            handler.removeMessages(2);
        }
        Handler handler2 = this.mHandlerInNewThread;
        boolean z = true;
        if (handler2 != null) {
            handler2.removeMessages(1);
            this.mHandlerInNewThread.removeMessages(2);
        }
        int refineValueByLimit = refineValueByLimit(fromValue, this.mMinValue, this.mMaxValue, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        int i2 = this.mMinValue;
        int i3 = this.mMaxValue;
        if (!this.mWrapSelectorWheel || !this.mWrapSelectorWheelCheck) {
            z = false;
        }
        int refineValueByLimit2 = refineValueByLimit(toValue, i2, i3, z);
        if (this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck) {
            i = refineValueByLimit2 - refineValueByLimit;
            int oneRecycleSize = getOneRecycleSize() / 2;
            if (i < (-oneRecycleSize) || oneRecycleSize < i) {
                int oneRecycleSize2 = getOneRecycleSize();
                i = i > 0 ? i - oneRecycleSize2 : i + oneRecycleSize2;
            }
        } else {
            i = refineValueByLimit2 - refineValueByLimit;
        }
        setValue(refineValueByLimit);
        if (refineValueByLimit == refineValueByLimit2) {
            return;
        }
        scrollByIndexSmoothly(i, needRespond);
    }

    public void refreshByNewDisplayedValues(String[] display) {
        int minValue = getMinValue();
        int maxValue = (getMaxValue() - minValue) + 1;
        int length = display.length - 1;
        if ((length - minValue) + 1 > maxValue) {
            setDisplayedValues(display);
            setMaxValue(length);
            return;
        }
        setMaxValue(length);
        setDisplayedValues(display);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void respondPickedValueChanged(int oldVal, int newVal, Object respondChange) {
        onScrollStateChange(0);
        LogUtils.d(TAG, hashCode() + "：respondPickedValueChanged:" + respondChange + ",oldVal:" + oldVal + ",newVal:" + newVal, false);
        if (!(respondChange instanceof Boolean) || ((Boolean) respondChange).booleanValue()) {
            OnValueChangeListener onValueChangeListener = this.mOnValueChangeListener;
            if (onValueChangeListener != null) {
                int i = this.mMinValue;
                onValueChangeListener.onValueChange(this, oldVal + i, i + newVal);
            }
            OnValueChangeListenerRelativeToRaw onValueChangeListenerRelativeToRaw = this.mOnValueChangeListenerRaw;
            if (onValueChangeListenerRelativeToRaw != null) {
                onValueChangeListenerRelativeToRaw.onValueChangeRelativeToRaw(this, oldVal, newVal, this.mDisplayedValues);
            }
        }
        this.mPrevPickedIndex = newVal;
        if (this.mPendingWrapToLinear) {
            this.mPendingWrapToLinear = false;
            internalSetWrapToLinear();
        }
    }

    private void scrollByIndexSmoothly(int deltaIndex) {
        scrollByIndexSmoothly(deltaIndex, true);
    }

    private void scrollByIndexSmoothly(int deltaIndex, boolean needRespond) {
        int pickedIndexRelativeToRaw;
        int pickedIndexRelativeToRaw2;
        int i;
        int i2;
        if ((!this.mWrapSelectorWheel || !this.mWrapSelectorWheelCheck) && ((pickedIndexRelativeToRaw2 = (pickedIndexRelativeToRaw = getPickedIndexRelativeToRaw()) + deltaIndex) > (i = this.mMaxShowIndex) || pickedIndexRelativeToRaw2 < (i = this.mMinShowIndex))) {
            deltaIndex = i - pickedIndexRelativeToRaw;
        }
        int i3 = this.mCurrDrawFirstItemY;
        int i4 = this.mItemHeight;
        if (i3 < (-i4) / 2) {
            int i5 = i4 + i3;
            int i6 = (int) (((i3 + i4) * 300.0f) / i4);
            i2 = deltaIndex < 0 ? (-i6) - (deltaIndex * 300) : i6 + (deltaIndex * 300);
            i3 = i5;
        } else {
            int i7 = (int) (((-i3) * 300.0f) / i4);
            i2 = deltaIndex < 0 ? i7 - (deltaIndex * 300) : i7 + (deltaIndex * 300);
        }
        int i8 = i3 + (deltaIndex * i4);
        if (i2 < 300) {
            i2 = 300;
        }
        if (i2 > 600) {
            i2 = 600;
        }
        this.mScroller.startScroll(0, this.mCurrDrawGlobalY, 0, i8, i2);
        Handler handler = this.mHandlerInNewThread;
        if (handler != null) {
            if (needRespond) {
                handler.sendMessageDelayed(getMsg(1), i2 / 4);
            } else {
                handler.sendMessageDelayed(getMsg(1, 0, 0, Boolean.valueOf(needRespond)), i2 / 4);
            }
        }
        postInvalidate();
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public void setMinValue(int minValue) {
        this.mMinValue = minValue;
        this.mMinShowIndex = 0;
        updateNotWrapYLimit();
    }

    public void setMaxValue(int maxValue) {
        String[] strArr = this.mDisplayedValues;
        Objects.requireNonNull(strArr, "mDisplayedValues should not be null");
        int i = this.mMinValue;
        if ((maxValue - i) + 1 > strArr.length) {
            throw new IllegalArgumentException("(maxValue - mMinValue + 1) should not be greater than mDisplayedValues.length now  (maxValue - mMinValue + 1) is " + ((maxValue - this.mMinValue) + 1) + " and mDisplayedValues.length is " + this.mDisplayedValues.length);
        }
        this.mMaxValue = maxValue;
        int i2 = this.mMinShowIndex;
        int i3 = (maxValue - i) + i2;
        this.mMaxShowIndex = i3;
        setMinAndMaxShowIndex(i2, i3);
        updateNotWrapYLimit();
    }

    public void setValue(int value) {
        int i = this.mMinValue;
        if (value < i) {
            throw new IllegalArgumentException("should not set a value less than mMinValue, value is " + value);
        }
        if (value > this.mMaxValue) {
            throw new IllegalArgumentException("should not set a value greater than mMaxValue, value is " + value);
        }
        setPickedIndexRelativeToRaw(value - i);
    }

    public int getValue() {
        return getPickedIndexRelativeToRaw() + this.mMinValue;
    }

    public String getContentByCurrValue() {
        return this.mDisplayedValues[getValue() - this.mMinValue];
    }

    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    public boolean getWrapSelectorWheelAbsolutely() {
        return this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck;
    }

    public void setHintText(String hintText) {
        if (isStringEqual(this.mHintText, hintText)) {
            return;
        }
        this.mHintText = hintText;
        this.mTextSizeHintCenterYOffset = getTextCenterYOffset(this.mPaintHint.getFontMetrics());
        this.mWidthOfHintText = getTextWidth(this.mHintText, this.mPaintHint);
        Handler handler = this.mHandlerInMainThread;
        if (handler != null) {
            handler.sendEmptyMessage(3);
        }
    }

    public void setPickedIndexRelativeToMin(int pickedIndexToMin) {
        if (pickedIndexToMin < 0 || pickedIndexToMin >= getOneRecycleSize()) {
            return;
        }
        this.mPrevPickedIndex = this.mMinShowIndex + pickedIndexToMin;
        correctPositionByDefaultValue(pickedIndexToMin, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        postInvalidate();
    }

    public void setNormalTextColor(int normalTextColor) {
        int color = getResources().getColor(normalTextColor, getContext().getTheme());
        if (this.mTextColorNormal == color) {
            return;
        }
        this.mTextColorNormal = color;
        postInvalidate();
    }

    public void setSelectedTextColor(int selectedTextColor) {
        int color = getResources().getColor(selectedTextColor, getContext().getTheme());
        if (this.mTextColorSelected == color) {
            return;
        }
        this.mTextColorSelected = color;
        postInvalidate();
    }

    public void setHintTextColor(int hintTextColor) {
        int color = getResources().getColor(hintTextColor, getContext().getTheme());
        if (this.mTextColorHint == color) {
            return;
        }
        this.mTextColorHint = color;
        this.mPaintHint.setColor(color);
        postInvalidate();
    }

    public void setDividerColor(int dividerColor) {
        int color = getResources().getColor(dividerColor, getContext().getTheme());
        if (this.mDividerColor == color) {
            return;
        }
        this.mDividerColor = color;
        this.mPaintDivider.setColor(color);
        postInvalidate();
    }

    public void setPickedIndexRelativeToRaw(int pickedIndexToRaw) {
        LogUtils.d(TAG, hashCode() + ":setPickedIndexRelativeToRaw:" + pickedIndexToRaw);
        int i = this.mMinShowIndex;
        if (i <= -1 || i > pickedIndexToRaw || pickedIndexToRaw > this.mMaxShowIndex) {
            return;
        }
        this.mPrevPickedIndex = pickedIndexToRaw;
        correctPositionByDefaultValue(pickedIndexToRaw - i, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        postInvalidate();
    }

    public int getPickedIndexRelativeToRaw() {
        int i = this.mCurrDrawFirstItemY;
        if (i != 0) {
            int i2 = this.mItemHeight;
            if (i < (-i2) / 2) {
                return getWillPickIndexByGlobalY(this.mCurrDrawGlobalY + i2 + i);
            }
            return getWillPickIndexByGlobalY(this.mCurrDrawGlobalY + i);
        }
        return getWillPickIndexByGlobalY(this.mCurrDrawGlobalY);
    }

    public void setMinAndMaxShowIndex(int minShowIndex, int maxShowIndex) {
        setMinAndMaxShowIndex(minShowIndex, maxShowIndex, true);
    }

    public void setMinAndMaxShowIndex(int minShowIndex, int maxShowIndex, boolean needRefresh) {
        if (minShowIndex > maxShowIndex) {
            throw new IllegalArgumentException("minShowIndex should be less than maxShowIndex, minShowIndex is " + minShowIndex + ", maxShowIndex is " + maxShowIndex + ".");
        }
        String[] strArr = this.mDisplayedValues;
        if (strArr == null) {
            throw new IllegalArgumentException("mDisplayedValues should not be null, you need to set mDisplayedValues first.");
        }
        if (minShowIndex < 0) {
            throw new IllegalArgumentException("minShowIndex should not be less than 0, now minShowIndex is " + minShowIndex);
        }
        boolean z = true;
        if (minShowIndex > strArr.length - 1) {
            throw new IllegalArgumentException("minShowIndex should not be greater than (mDisplayedValues.length - 1), now (mDisplayedValues.length - 1) is " + (this.mDisplayedValues.length - 1) + " minShowIndex is " + minShowIndex);
        }
        if (maxShowIndex < 0) {
            throw new IllegalArgumentException("maxShowIndex should not be less than 0, now maxShowIndex is " + maxShowIndex);
        }
        if (maxShowIndex > strArr.length - 1) {
            throw new IllegalArgumentException("maxShowIndex should not be greater than (mDisplayedValues.length - 1), now (mDisplayedValues.length - 1) is " + (this.mDisplayedValues.length - 1) + " maxShowIndex is " + maxShowIndex);
        }
        this.mMinShowIndex = minShowIndex;
        this.mMaxShowIndex = maxShowIndex;
        if (needRefresh) {
            this.mPrevPickedIndex = minShowIndex + 0;
            if (!this.mWrapSelectorWheel || !this.mWrapSelectorWheelCheck) {
                z = false;
            }
            correctPositionByDefaultValue(0, z);
            postInvalidate();
        }
    }

    public void setFriction(float friction) {
        if (friction <= 0.0f) {
            throw new IllegalArgumentException("you should set a a positive float friction, now friction is " + friction);
        }
        ViewConfiguration.get(getContext());
        this.mFriction = ViewConfiguration.getScrollFriction() / friction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScrollStateChange(int scrollState) {
        if (this.mScrollState == scrollState) {
            return;
        }
        if (scrollState == 0) {
            this.mIsFromUser = false;
        }
        this.mScrollState = scrollState;
        OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChange(this, scrollState);
        }
    }

    public void setOnScrollListener(OnScrollListener listener) {
        this.mOnScrollListener = listener;
    }

    public void setOnValueChangedListener(OnValueChangeListener listener) {
        this.mOnValueChangeListener = listener;
    }

    public void setOnValueChangedListenerRelativeToRaw(OnValueChangeListenerRelativeToRaw listener) {
        this.mOnValueChangeListenerRaw = listener;
    }

    public void setOnValueChangeListenerInScrolling(OnValueChangeListenerInScrolling listener) {
        this.mOnValueChangeListenerInScrolling = listener;
    }

    public void setContentTextTypeface(Typeface typeface) {
        this.mPaintText.setTypeface(typeface);
    }

    public void setHintTextTypeface(Typeface typeface) {
        this.mPaintHint.setTypeface(typeface);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWillPickIndexByGlobalY(int globalY) {
        int i = this.mItemHeight;
        if (i == 0) {
            return 0;
        }
        int indexByRawIndex = getIndexByRawIndex((globalY / i) + (this.mShowCount / 2), getOneRecycleSize(), this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        int i2 = indexByRawIndex >= 0 ? indexByRawIndex : 0;
        return i2 >= getOneRecycleSize() ? getOneRecycleSize() - 1 : i2;
    }

    private int getIndexByRawIndex(int index, int size, boolean wrap) {
        if (size <= 0) {
            return 0;
        }
        if (wrap) {
            int i = index % size;
            return i < 0 ? i + size : i;
        }
        return index;
    }

    private void internalSetWrapToLinear() {
        correctPositionByDefaultValue(getPickedIndexRelativeToRaw() - this.mMinShowIndex, false);
        this.mWrapSelectorWheel = false;
        postInvalidate();
    }

    private void updateDividerAttr() {
        int i;
        int i2;
        int i3;
        int i4 = this.mShowCount / 2;
        this.mDividerIndex0 = i4;
        this.mDividerIndex1 = i4 + 1;
        int i5 = this.mViewHeight;
        this.dividerY0 = (i4 * i5) / i;
        this.dividerY1 = (i2 * i5) / i;
        if (this.mDividerMarginL < 0) {
            this.mDividerMarginL = 0;
        }
        if (this.mDividerMarginR < 0) {
            this.mDividerMarginR = 0;
        }
        if (this.mDividerMarginL + this.mDividerMarginR != 0 && getPaddingLeft() + this.mDividerMarginL >= (this.mViewWidth - getPaddingRight()) - this.mDividerMarginR) {
            int paddingLeft = getPaddingLeft() + this.mDividerMarginL + getPaddingRight();
            int i6 = this.mDividerMarginR;
            int i7 = (paddingLeft + i6) - this.mViewWidth;
            int i8 = this.mDividerMarginL;
            float f = i7;
            this.mDividerMarginL = (int) (i8 - ((i8 * f) / (i8 + i6)));
            this.mDividerMarginR = (int) (i6 - ((f * i6) / (i3 + i6)));
        }
    }

    private void updateFontAttr() {
        int i = this.mTextSizeNormal;
        int i2 = this.mItemHeight;
        if (i > i2) {
            this.mTextSizeNormal = i2;
        }
        if (this.mTextSizeSelected > i2) {
            this.mTextSizeSelected = i2;
        }
        Paint paint = this.mPaintHint;
        if (paint == null) {
            throw new IllegalArgumentException("mPaintHint should not be null.");
        }
        paint.setTextSize(this.mTextSizeHint);
        this.mTextSizeHintCenterYOffset = getTextCenterYOffset(this.mPaintHint.getFontMetrics());
        this.mWidthOfHintText = getTextWidth(this.mHintText, this.mPaintHint);
        TextPaint textPaint = this.mPaintText;
        if (textPaint == null) {
            throw new IllegalArgumentException("mPaintText should not be null.");
        }
        textPaint.setTextSize(this.mTextSizeSelected);
        this.mTextSizeSelectedCenterYOffset = getTextCenterYOffset(this.mPaintText.getFontMetrics());
        this.mPaintText.setTextSize(this.mTextSizeNormal);
        this.mTextSizeNormalCenterYOffset = getTextCenterYOffset(this.mPaintText.getFontMetrics());
    }

    private void updateNotWrapYLimit() {
        this.mNotWrapLimitYTop = 0;
        this.mNotWrapLimitYBottom = (-this.mShowCount) * this.mItemHeight;
        if (this.mDisplayedValues != null) {
            int oneRecycleSize = getOneRecycleSize();
            int i = this.mShowCount;
            int i2 = this.mItemHeight;
            this.mNotWrapLimitYTop = ((oneRecycleSize - (i / 2)) - 1) * i2;
            this.mNotWrapLimitYBottom = (-(i / 2)) * i2;
        }
    }

    private int limitY(int currDrawGlobalYPreferred) {
        if (this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck) {
            return currDrawGlobalYPreferred;
        }
        int i = this.mNotWrapLimitYBottom;
        return (currDrawGlobalYPreferred >= i && currDrawGlobalYPreferred <= (i = this.mNotWrapLimitYTop)) ? currDrawGlobalYPreferred : i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0069, code lost:
        if (r1 < r4) goto L31;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r18) {
        /*
            Method dump skipped, instructions count: 259
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.widget.ValuePickerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void click(MotionEvent event) {
        float y = event.getY();
        for (int i = 0; i < this.mShowCount; i++) {
            int i2 = this.mItemHeight;
            if (i2 * i <= y && y < i2 * (i + 1)) {
                clickItem(i);
                return;
            }
        }
    }

    private void clickItem(int showCountIndex) {
        int i;
        LogUtil.d(TAG, "clickItem showCountIndex:" + showCountIndex + ",value:" + getValue() + ",minValue:" + this.mMinValue + ",maxValue:" + this.mMaxValue);
        if (showCountIndex == 2 && getValue() == this.mMaxValue) {
            return;
        }
        if (!(showCountIndex == 0 && getValue() == this.mMinValue) && showCountIndex >= 0 && showCountIndex < (i = this.mShowCount)) {
            scrollByIndexSmoothly(showCountIndex - (i / 2));
        }
    }

    private float getTextCenterYOffset(Paint.FontMetrics fontMetrics) {
        if (fontMetrics == null) {
            return 0.0f;
        }
        return Math.abs(fontMetrics.top + fontMetrics.bottom) / 2.0f;
    }

    private void correctPositionByDefaultValue(int defaultPickedIndex, boolean wrap) {
        int i = defaultPickedIndex - ((this.mShowCount - 1) / 2);
        this.mCurrDrawFirstItemIndex = i;
        int indexByRawIndex = getIndexByRawIndex(i, getOneRecycleSize(), wrap);
        this.mCurrDrawFirstItemIndex = indexByRawIndex;
        int i2 = this.mItemHeight;
        if (i2 == 0) {
            this.mCurrentItemIndexEffect = true;
            return;
        }
        this.mCurrDrawGlobalY = i2 * indexByRawIndex;
        int i3 = indexByRawIndex + (this.mShowCount / 2);
        this.mInScrollingPickedOldValue = i3;
        int oneRecycleSize = i3 % getOneRecycleSize();
        this.mInScrollingPickedOldValue = oneRecycleSize;
        if (oneRecycleSize < 0) {
            this.mInScrollingPickedOldValue = oneRecycleSize + getOneRecycleSize();
        }
        this.mInScrollingPickedNewValue = this.mInScrollingPickedOldValue;
        calculateFirstItemParameterByGlobalY();
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mItemHeight != 0 && this.mScroller.computeScrollOffset()) {
            this.mCurrDrawGlobalY = this.mScroller.getCurrY();
            calculateFirstItemParameterByGlobalY();
            postInvalidate();
        }
    }

    private void calculateFirstItemParameterByGlobalY() {
        int floor = (int) Math.floor(this.mCurrDrawGlobalY / this.mItemHeight);
        this.mCurrDrawFirstItemIndex = floor;
        int i = this.mCurrDrawGlobalY;
        int i2 = this.mItemHeight;
        int i3 = -(i - (floor * i2));
        this.mCurrDrawFirstItemY = i3;
        if ((-i3) > i2 / 2) {
            this.mInScrollingPickedNewValue = floor + 1 + (this.mShowCount / 2);
        } else {
            this.mInScrollingPickedNewValue = floor + (this.mShowCount / 2);
        }
        int oneRecycleSize = this.mInScrollingPickedNewValue % getOneRecycleSize();
        this.mInScrollingPickedNewValue = oneRecycleSize;
        if (oneRecycleSize < 0) {
            this.mInScrollingPickedNewValue = oneRecycleSize + getOneRecycleSize();
        }
        int i4 = this.mInScrollingPickedOldValue;
        int i5 = this.mInScrollingPickedNewValue;
        if (i4 != i5) {
            if (this.mOnValueChangeListenerInScrolling != null) {
                respondPickedValueChangedInScrolling(i4, i5);
            }
            if (this.mIsFromUser && this.mHandlerInNewThread != null && System.currentTimeMillis() - this.playSoundTime > 50) {
                this.mHandlerInNewThread.post(new Runnable() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$ValuePickerView$Y5-21g-u8fD70UPJ8WbMPv0Jucg
                    @Override // java.lang.Runnable
                    public final void run() {
                        SoundHelper.playNotification(SoundHelper.PATH_WHEEL_SCROLL_7);
                    }
                });
                this.playSoundTime = System.currentTimeMillis();
            }
        }
        this.mInScrollingPickedOldValue = this.mInScrollingPickedNewValue;
    }

    private void releaseVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void updateMaxWHOfDisplayedValues(boolean needRequestLayout) {
        Handler handler;
        updateMaxWidthOfDisplayedValues();
        updateMaxHeightOfDisplayedValues();
        if (!needRequestLayout || (handler = this.mHandlerInMainThread) == null) {
            return;
        }
        if (this.mSpecModeW == Integer.MIN_VALUE || this.mSpecModeH == Integer.MIN_VALUE) {
            handler.sendEmptyMessage(3);
        }
    }

    private int measureWidth(int measureSpec) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        this.mSpecModeW = mode;
        int size = View.MeasureSpec.getSize(measureSpec);
        if (mode == 1073741824) {
            return size;
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight() + Math.max(this.mMaxWidthOfAlterArrayWithMeasureHint, Math.max(this.mMaxWidthOfDisplayedValues, this.mMaxWidthOfAlterArrayWithoutMeasureHint) + (((Math.max(this.mWidthOfHintText, this.mWidthOfAlterHint) != 0 ? this.mMarginStartOfHint : 0) + Math.max(this.mWidthOfHintText, this.mWidthOfAlterHint) + (Math.max(this.mWidthOfHintText, this.mWidthOfAlterHint) == 0 ? 0 : this.mMarginEndOfHint) + (this.mItemPaddingHorizontal * 2)) * 2));
        return mode == Integer.MIN_VALUE ? Math.min(paddingLeft, size) : paddingLeft;
    }

    private int measureHeight(int measureSpec) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        this.mSpecModeH = mode;
        int size = View.MeasureSpec.getSize(measureSpec);
        if (mode == 1073741824) {
            return size;
        }
        int paddingTop = getPaddingTop() + getPaddingBottom() + (this.mShowCount * (this.mMaxHeightOfDisplayedValues + (this.mItemPaddingVertical * 2)));
        return mode == Integer.MIN_VALUE ? Math.min(paddingTop, size) : paddingTop;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawContent(canvas);
        drawLine(canvas);
        drawHint(canvas);
    }

    private void drawContent(Canvas canvas) {
        int i;
        float f;
        float f2;
        float f3;
        float f4;
        int i2;
        String str;
        String str2;
        int i3;
        float f5 = 0.0f;
        int i4 = 0;
        while (i4 < this.mShowCount + 1) {
            float f6 = this.mCurrDrawFirstItemY + (this.mItemHeight * i4);
            int indexByRawIndex = getIndexByRawIndex(this.mCurrDrawFirstItemIndex + i4, getOneRecycleSize(), this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
            int i5 = this.mShowCount;
            if (i4 == i5 / 2) {
                f5 = (this.mCurrDrawFirstItemY + i3) / this.mItemHeight;
                i = getEvaluateColor(f5, this.mTextColorNormal, this.mTextColorSelected);
                f = getEvaluateSize(f5, this.mTextSizeNormal, this.mTextSizeSelected);
                f2 = getEvaluateSize(f5, this.mTextSizeSufNormal, this.mTextSizeSufSelected);
                f3 = getEvaluateSize(f5, this.mTextSizeNormalCenterYOffset, this.mTextSizeSelectedCenterYOffset);
            } else if (i4 == (i5 / 2) + 1) {
                float f7 = 1.0f - f5;
                int evaluateColor = getEvaluateColor(f7, this.mTextColorNormal, this.mTextColorSelected);
                float evaluateSize = getEvaluateSize(f7, this.mTextSizeNormal, this.mTextSizeSelected);
                float evaluateSize2 = getEvaluateSize(f7, this.mTextSizeSufNormal, this.mTextSizeSufSelected);
                f3 = getEvaluateSize(f7, this.mTextSizeNormalCenterYOffset, this.mTextSizeSelectedCenterYOffset);
                i = evaluateColor;
                f = evaluateSize;
                f2 = evaluateSize2;
            } else {
                i = this.mTextColorNormal;
                f = this.mTextSizeNormal;
                f2 = this.mTextSizeSufNormal;
                f3 = this.mTextSizeNormalCenterYOffset;
            }
            if (i4 == 0) {
                float f8 = -this.mCurrDrawFirstItemY;
                int i6 = this.mItemHeight;
                if (f8 > i6 / 2.0f) {
                    f8 = i6 / 2.0f;
                }
                f5 = (f8 * 2.0f) / i6;
                i = getEvaluateAlpha(f5, this.mTextColorNormal);
            }
            if (i4 == this.mShowCount) {
                int i7 = this.mItemHeight;
                float f9 = this.mCurrDrawFirstItemY + i7;
                if (f9 > i7 / 2.0f) {
                    f9 = i7 / 2.0f;
                }
                float f10 = (f9 * 2.0f) / i7;
                i = getEvaluateAlpha(f10, this.mTextColorNormal);
                f5 = f10;
            }
            this.mPaintText.setTextSize(f2);
            float measureText = this.mPaintText.measureText(this.mTextCelsius);
            this.mPaintText.setColor(i);
            this.mPaintText.setTextSize(f);
            if (indexByRawIndex >= 0 && indexByRawIndex < getOneRecycleSize()) {
                CharSequence charSequence = this.mDisplayedValues[this.mMinShowIndex + indexByRawIndex];
                if (this.mTextEllipsize != null) {
                    f4 = f5;
                    charSequence = TextUtils.ellipsize(charSequence, this.mPaintText, getWidth() - (this.mItemPaddingHorizontal * 2), getEllipsizeType());
                } else {
                    f4 = f5;
                }
                this.mPaintText.setColor(getShadowTextColor(i));
                float measureText2 = this.mPaintText.measureText(charSequence.toString()) * 0.75f;
                float f11 = 0.2f * f3;
                int i8 = this.mShadowDirection;
                i2 = i4;
                if (i8 == 1) {
                    str = "LOW";
                    str2 = "HIGH";
                    canvas.drawText(charSequence.toString(), (this.mViewCenterX - SHADOW_OFFSET) - measureText, (this.mItemHeight / 2) + f6 + f3, this.mPaintText);
                    this.mPaintText.setTextSize(f2);
                    canvas.drawText(this.mTextCelsius, ((this.mViewCenterX - SHADOW_OFFSET) - measureText) + measureText2, (this.mItemHeight / 2) + f6 + f11, this.mPaintText);
                } else {
                    str = "LOW";
                    str2 = "HIGH";
                    if (i8 == 2) {
                        canvas.drawText(charSequence.toString(), (this.mViewCenterX + SHADOW_OFFSET) - measureText, (this.mItemHeight / 2) + f6 + f3, this.mPaintText);
                        this.mPaintText.setTextSize(f2);
                        canvas.drawText(this.mTextCelsius, ((this.mViewCenterX + SHADOW_OFFSET) - measureText) + measureText2, (this.mItemHeight / 2) + f6 + f11, this.mPaintText);
                    }
                }
                this.mPaintText.setColor(i);
                this.mPaintText.setTextSize(f);
                canvas.drawText(charSequence.toString(), this.mViewCenterX - measureText, (this.mItemHeight / 2) + f6 + f3, this.mPaintText);
                if (indexByRawIndex == 0) {
                    this.mPaintText.setTextSize(f / 2.0f);
                    canvas.drawText(str2, (this.mViewCenterX - measureText) - 20.0f, (this.mItemHeight + f6) - SHADOW_OFFSET, this.mPaintText);
                } else if (indexByRawIndex == 28) {
                    this.mPaintText.setTextSize(f / 2.0f);
                    canvas.drawText(str, (this.mViewCenterX - measureText) - 20.0f, (this.mItemHeight + f6) - SHADOW_OFFSET, this.mPaintText);
                }
                this.mPaintText.setTextSize(f2);
                canvas.drawText(this.mTextCelsius, (this.mViewCenterX - measureText) + measureText2, f6 + (this.mItemHeight / 2) + f11, this.mPaintText);
            } else {
                f4 = f5;
                i2 = i4;
                if (!TextUtils.isEmpty(this.mEmptyItemHint)) {
                    this.mPaintText.setColor(getShadowTextColor(i));
                    int i9 = this.mShadowDirection;
                    if (i9 == 1) {
                        canvas.drawText(this.mEmptyItemHint, (this.mViewCenterX - SHADOW_OFFSET) - measureText, (this.mItemHeight / 2) + f6 + f3, this.mPaintText);
                    } else if (i9 == 2) {
                        canvas.drawText(this.mEmptyItemHint, (this.mViewCenterX + SHADOW_OFFSET) - measureText, (this.mItemHeight / 2) + f6 + f3, this.mPaintText);
                    }
                    this.mPaintText.setColor(i);
                    canvas.drawText(this.mEmptyItemHint, this.mViewCenterX - measureText, (this.mItemHeight / 2) + f6 + f3, this.mPaintText);
                    if (indexByRawIndex == 0) {
                        this.mPaintText.setTextSize(f / 2.0f);
                        canvas.drawText("HIGH", (this.mViewCenterX - measureText) - 20.0f, (f6 + this.mItemHeight) - SHADOW_OFFSET, this.mPaintText);
                    } else if (indexByRawIndex == 28) {
                        this.mPaintText.setTextSize(f / 2.0f);
                        canvas.drawText("LOW", (this.mViewCenterX - measureText) - 20.0f, (f6 + this.mItemHeight) - SHADOW_OFFSET, this.mPaintText);
                    }
                }
            }
            i4 = i2 + 1;
            f5 = f4;
        }
    }

    public void setShadowDirection(int direction) {
        this.mShadowDirection = direction;
        invalidate();
    }

    public int getShadowTextColor(int textColor) {
        return Color.argb((int) (Color.alpha(textColor) * 0.06f), Color.red(textColor), Color.green(textColor), Color.blue(textColor));
    }

    private TextUtils.TruncateAt getEllipsizeType() {
        String str = this.mTextEllipsize;
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1074341483:
                if (str.equals(TEXT_ELLIPSIZE_MIDDLE)) {
                    c = 0;
                    break;
                }
                break;
            case 100571:
                if (str.equals("end")) {
                    c = 1;
                    break;
                }
                break;
            case 109757538:
                if (str.equals("start")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return TextUtils.TruncateAt.MIDDLE;
            case 1:
                return TextUtils.TruncateAt.END;
            case 2:
                return TextUtils.TruncateAt.START;
            default:
                throw new IllegalArgumentException("Illegal text ellipsize type.");
        }
    }

    private void drawLine(Canvas canvas) {
        if (this.mShowDivider) {
            canvas.drawLine(getPaddingLeft() + this.mDividerMarginL, this.dividerY0, (this.mViewWidth - getPaddingRight()) - this.mDividerMarginR, this.dividerY0, this.mPaintDivider);
            canvas.drawLine(getPaddingLeft() + this.mDividerMarginL, this.dividerY1, (this.mViewWidth - getPaddingRight()) - this.mDividerMarginR, this.dividerY1, this.mPaintDivider);
        }
    }

    private void drawHint(Canvas canvas) {
        if (TextUtils.isEmpty(this.mHintText)) {
            return;
        }
        canvas.drawText(this.mHintText, this.mViewCenterX + ((this.mMaxWidthOfDisplayedValues + this.mWidthOfHintText) / 2) + this.mMarginStartOfHint, ((this.dividerY0 + this.dividerY1) / 2.0f) + this.mTextSizeHintCenterYOffset, this.mPaintHint);
    }

    private void updateMaxWidthOfDisplayedValues() {
        float textSize = this.mPaintText.getTextSize();
        this.mPaintText.setTextSize(this.mTextSizeSelected);
        this.mMaxWidthOfDisplayedValues = getMaxWidthOfTextArray(this.mDisplayedValues, this.mPaintText);
        this.mMaxWidthOfAlterArrayWithMeasureHint = getMaxWidthOfTextArray(this.mAlterTextArrayWithMeasureHint, this.mPaintText);
        this.mMaxWidthOfAlterArrayWithoutMeasureHint = getMaxWidthOfTextArray(this.mAlterTextArrayWithoutMeasureHint, this.mPaintText);
        this.mPaintText.setTextSize(this.mTextSizeHint);
        this.mWidthOfAlterHint = getTextWidth(this.mAlterHint, this.mPaintText);
        this.mPaintText.setTextSize(textSize);
    }

    private int getMaxWidthOfTextArray(CharSequence[] array, Paint paint) {
        if (array == null) {
            return 0;
        }
        int i = 0;
        for (CharSequence charSequence : array) {
            if (charSequence != null) {
                i = Math.max(getTextWidth(charSequence, paint), i);
            }
        }
        return i;
    }

    private int getTextWidth(CharSequence text, Paint paint) {
        if (TextUtils.isEmpty(text)) {
            return 0;
        }
        return (int) (paint.measureText(text.toString()) + 0.5f);
    }

    private void updateMaxHeightOfDisplayedValues() {
        float textSize = this.mPaintText.getTextSize();
        this.mPaintText.setTextSize(this.mTextSizeSelected);
        this.mMaxHeightOfDisplayedValues = (int) ((this.mPaintText.getFontMetrics().bottom - this.mPaintText.getFontMetrics().top) + 0.5d);
        this.mPaintText.setTextSize(textSize);
    }

    private void updateContentAndIndex(String[] newDisplayedValues) {
        this.mMinShowIndex = 0;
        this.mMaxShowIndex = newDisplayedValues.length - 1;
        this.mDisplayedValues = newDisplayedValues;
        updateWrapStateByContent();
    }

    private void updateContent(String[] newDisplayedValues) {
        this.mDisplayedValues = newDisplayedValues;
        updateWrapStateByContent();
    }

    private void updateValue() {
        inflateDisplayedValuesIfNull();
        updateWrapStateByContent();
        this.mMinShowIndex = 0;
        this.mMaxShowIndex = this.mDisplayedValues.length - 1;
    }

    private void updateValueForInit() {
        inflateDisplayedValuesIfNull();
        updateWrapStateByContent();
        if (this.mMinShowIndex == -1) {
            this.mMinShowIndex = 0;
        }
        if (this.mMaxShowIndex == -1) {
            this.mMaxShowIndex = this.mDisplayedValues.length - 1;
        }
        setMinAndMaxShowIndex(this.mMinShowIndex, this.mMaxShowIndex, false);
    }

    private void inflateDisplayedValuesIfNull() {
        if (this.mDisplayedValues == null) {
            this.mDisplayedValues = r0;
            String[] strArr = {"0"};
        }
    }

    private void updateWrapStateByContent() {
        this.mWrapSelectorWheelCheck = this.mDisplayedValues.length > this.mShowCount;
    }

    private int refineValueByLimit(int value, int minValue, int maxValue, boolean wrap) {
        if (!wrap) {
            return value > maxValue ? maxValue : value < minValue ? minValue : value;
        } else if (value > maxValue) {
            return (((value - maxValue) % getOneRecycleSize()) + minValue) - 1;
        } else {
            return value < minValue ? ((value - minValue) % getOneRecycleSize()) + maxValue + 1 : value;
        }
    }

    private void stopRefreshing() {
        Handler handler = this.mHandlerInNewThread;
        if (handler != null) {
            handler.removeMessages(1);
        }
    }

    public void stopScrolling() {
        ScrollerCompat scrollerCompat = this.mScroller;
        if (scrollerCompat == null || scrollerCompat.isFinished()) {
            return;
        }
        ScrollerCompat scrollerCompat2 = this.mScroller;
        scrollerCompat2.startScroll(0, scrollerCompat2.getCurrY(), 0, 0, 1);
        this.mScroller.abortAnimation();
        postInvalidate();
    }

    public void stopScrollingAndCorrectPosition() {
        stopScrolling();
        Handler handler = this.mHandlerInMainThread;
        if (handler != null) {
            handler.removeMessages(2);
        }
        Handler handler2 = this.mHandlerInNewThread;
        if (handler2 != null) {
            handler2.removeMessages(1);
            this.mHandlerInNewThread.removeMessages(2);
            this.mHandlerInNewThread.sendMessageDelayed(getMsg(1, 0, 0, false), 0L);
        }
    }

    private Message getMsg(int what) {
        return getMsg(what, 0, 0, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Message getMsg(int what, int arg1, int arg2, Object obj) {
        Message obtain = Message.obtain();
        obtain.what = what;
        obtain.arg1 = arg1;
        obtain.arg2 = arg2;
        obtain.obj = obj;
        return obtain;
    }

    private boolean isStringEqual(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    private int sp2px(Context context, float spValue) {
        return (int) ((spValue * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    private int dp2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private String[] convertCharSequenceArrayToStringArray(CharSequence[] charSequences) {
        if (charSequences == null) {
            return null;
        }
        String[] strArr = new String[charSequences.length];
        for (int i = 0; i < charSequences.length; i++) {
            strArr[i] = charSequences[i].toString();
        }
        return strArr;
    }
}
