package com.xiaopeng.carcontrol.view.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrolmodule.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class XRangeSlider extends View {
    private static final DecelerateInterpolator PROGRESS_ANIM_INTERPOLATOR = new DecelerateInterpolator();
    private static final String TAG = "XRangeSlider";
    private final FloatProperty<XRangeSlider> VISUAL_PROGRESS;
    private RectF mBarContainerPaddingRectF;
    private RectF mBarRect;
    int mCurrentItem;
    private Drawable mIndicatorDrawable;
    int mIndicatorHeight;
    Rect mIndicatorRect;
    int mIndicatorWidth;
    private boolean mIsDragging;
    private OnSlierTickListener mListener;
    List<CharSequence> mMarkTextsList;
    Paint mPaint;
    private float mProgress;
    private int mRangeBarColor;
    int mRangeBarHeight;
    int mRangeBarWidth;
    private int mScaledTouchSlop;
    int mStepsLineColor;
    float mStepsLineHeight;
    float mStepsLineWidth;
    int mTextColor;
    float mTextMargin;
    Paint mTextPaint;
    int mTextSize;
    private float mTouchDownX;
    private float mVisualProgress;

    /* loaded from: classes2.dex */
    public interface OnSlierTickListener {
        void onChanged(int index);
    }

    public XRangeSlider(Context context) {
        this(context, null);
    }

    public XRangeSlider(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRangeSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public XRangeSlider(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mCurrentItem = 0;
        this.mMarkTextsList = new ArrayList();
        this.VISUAL_PROGRESS = new FloatProperty<XRangeSlider>("visual_progress") { // from class: com.xiaopeng.carcontrol.view.widget.XRangeSlider.1
            @Override // android.util.FloatProperty
            public void setValue(XRangeSlider object, float value) {
                object.setVisualProgress(value);
            }

            @Override // android.util.Property
            public Float get(XRangeSlider object) {
                return Float.valueOf(object.mVisualProgress);
            }
        };
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        this.mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        TypedArray obtainAttributes = getResources().obtainAttributes(attrs, R.styleable.XRangeSlider);
        CharSequence[] textArray = obtainAttributes.getTextArray(R.styleable.XRangeSlider_range_mark_text_array);
        if (textArray != null && textArray.length > 0) {
            this.mMarkTextsList.addAll(Arrays.asList(textArray));
        }
        this.mIndicatorWidth = obtainAttributes.getDimensionPixelSize(R.styleable.XRangeSlider_range_indicator_width, 30);
        this.mIndicatorHeight = obtainAttributes.getDimensionPixelSize(R.styleable.XRangeSlider_range_indicator_height, 96);
        this.mRangeBarWidth = obtainAttributes.getDimensionPixelSize(R.styleable.XRangeSlider_range_bar_width, 6);
        this.mRangeBarHeight = obtainAttributes.getDimensionPixelSize(R.styleable.XRangeSlider_range_bar_height, 6);
        RectF rectF = new RectF(obtainAttributes.getDimensionPixelSize(R.styleable.XRangeSlider_range_bar_padding_left, 0), obtainAttributes.getDimensionPixelSize(R.styleable.XRangeSlider_range_bar_padding_top, 0), obtainAttributes.getDimensionPixelSize(R.styleable.XRangeSlider_range_bar_padding_right, 0), obtainAttributes.getDimensionPixelSize(R.styleable.XRangeSlider_range_bar_padding_bottom, 0));
        this.mBarContainerPaddingRectF = rectF;
        float f = (rectF.left + (this.mIndicatorWidth / 2.0f)) - (this.mRangeBarWidth / 2.0f);
        this.mBarRect = new RectF(f, this.mBarContainerPaddingRectF.top, this.mRangeBarWidth + f, this.mBarContainerPaddingRectF.top + this.mRangeBarHeight);
        this.mIndicatorRect = new Rect((int) this.mBarContainerPaddingRectF.left, 0, (int) (this.mIndicatorWidth + this.mBarContainerPaddingRectF.left), 0);
        this.mTextSize = obtainAttributes.getDimensionPixelSize(R.styleable.XRangeSlider_range_bar_mark_text_size, 30);
        this.mTextColor = obtainAttributes.getColor(R.styleable.XRangeSlider_range_bar_mark_text_color, ResUtils.getColor(R.color.x_theme_text_03));
        this.mTextMargin = obtainAttributes.getDimensionPixelSize(R.styleable.XRangeSlider_range_bar_mark_text_margin_left, 16);
        this.mPaint = new Paint(1);
        Paint paint = new Paint(1);
        this.mTextPaint = paint;
        paint.setTextSize(this.mTextSize);
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setStyle(Paint.Style.FILL);
        this.mTextPaint.setTextAlign(Paint.Align.LEFT);
        this.mIndicatorDrawable = ResUtils.getDrawable(R.drawable.x_range_bar_indicator);
        this.mRangeBarColor = ResUtils.getColor(R.color.x_range_slider_bar_color);
        this.mStepsLineWidth = ResUtils.getDimensionPixelSize(R.dimen.x_range_bar_line_width);
        this.mStepsLineHeight = ResUtils.getDimensionPixelSize(R.dimen.x_range_bar_line_height);
        this.mStepsLineColor = ResUtils.getColor(R.color.x_range_slider_bar_line_color);
    }

    private float getGap() {
        return this.mRangeBarHeight / this.mMarkTextsList.size();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec((int) (this.mBarRect.right + this.mBarContainerPaddingRectF.right + this.mStepsLineWidth + this.mTextMargin + 100.0f), View.MeasureSpec.getMode(widthMeasureSpec)), View.MeasureSpec.makeMeasureSpec(((int) this.mBarRect.bottom) + ((int) this.mBarContainerPaddingRectF.bottom) + getPaddingTop() + getPaddingBottom(), View.MeasureSpec.getMode(heightMeasureSpec)));
    }

    public void setIndicatorHeight(int height) {
        this.mRangeBarHeight = height;
        this.mBarRect.bottom = this.mBarContainerPaddingRectF.top + this.mRangeBarHeight;
        invalidate();
    }

    public void setIndex(int i) {
        LogUtils.i(TAG, "setIndex: " + i);
        if (i < 0) {
            this.mCurrentItem = 0;
        }
        if (i >= this.mMarkTextsList.size()) {
            i = this.mMarkTextsList.size() - 1;
        }
        if (this.mCurrentItem != i) {
            this.mCurrentItem = i;
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawBar(canvas);
        onDrawRangeSteps(canvas);
        onDrawTickMark(canvas);
    }

    private void onDrawBar(Canvas canvas) {
        this.mPaint.setColor(this.mRangeBarColor);
        canvas.drawRoundRect(this.mBarRect, 0.0f, 0.0f, this.mPaint);
    }

    private void onDrawRangeSteps(Canvas canvas) {
        int size = this.mMarkTextsList.size();
        float f = this.mBarRect.right + this.mBarContainerPaddingRectF.right;
        float gap = getGap();
        float f2 = this.mBarContainerPaddingRectF.top + (gap / 2.0f);
        this.mPaint.setColor(this.mStepsLineColor);
        float f3 = f2;
        for (int i = 0; i < size; i++) {
            canvas.drawLine(f, f3, f + this.mStepsLineWidth, f3, this.mPaint);
            f3 += gap;
        }
        float f4 = this.mBarRect.right + this.mBarContainerPaddingRectF.right + this.mStepsLineWidth + this.mTextMargin;
        Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        float f5 = (f2 - (fontMetrics.top / 2.0f)) - (fontMetrics.bottom / 2.0f);
        for (int i2 = 0; i2 < size; i2++) {
            canvas.drawText(this.mMarkTextsList.get(i2).toString(), f4, f5, this.mTextPaint);
            f5 += gap;
        }
    }

    private void onDrawTickMark(Canvas canvas) {
        if (this.mIsDragging) {
            this.mIndicatorRect.top = (int) (this.mBarContainerPaddingRectF.top + this.mProgress);
        } else {
            this.mIndicatorRect.top = (int) ((this.mBarContainerPaddingRectF.top + (getGap() * (this.mCurrentItem + 0.5f))) - (this.mIndicatorHeight / 2));
        }
        Rect rect = this.mIndicatorRect;
        rect.bottom = rect.top + this.mIndicatorHeight;
        this.mIndicatorDrawable.setBounds(this.mIndicatorRect);
        this.mIndicatorDrawable.draw(canvas);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == 0) {
            startDrag(event);
        } else if (action == 1) {
            if (this.mIsDragging) {
                trackTouchEvent(event);
                onStopTrackingTouch();
                setPressed(false);
            } else {
                onStartTrackingTouch();
                trackTouchEvent(event);
                onStopTrackingTouch();
            }
            invalidate();
        } else if (action != 2) {
            if (action == 3) {
                if (this.mIsDragging) {
                    onStopTrackingTouch();
                    setPressed(false);
                }
                invalidate();
            }
        } else if (Math.abs(event.getX() - this.mTouchDownX) > this.mScaledTouchSlop) {
            startDrag(event);
        }
        return true;
    }

    private void trackTouchEvent(MotionEvent event) {
        float f;
        Math.round(event.getX());
        int round = Math.round(event.getY());
        int i = this.mRangeBarHeight;
        float f2 = round;
        if (f2 < this.mBarRect.top) {
            f = 0.0f;
        } else {
            f = f2 > this.mBarRect.bottom ? 1.0f : (f2 - this.mBarRect.top) / i;
        }
        setProgressInternal(Math.round(f * i), true, false);
    }

    synchronized boolean setProgressInternal(float progress, boolean fromUser, boolean animate) {
        if (progress == this.mProgress) {
            return false;
        }
        this.mProgress = progress;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, this.VISUAL_PROGRESS, progress);
        ofFloat.setAutoCancel(true);
        ofFloat.setDuration(80L);
        ofFloat.setInterpolator(PROGRESS_ANIM_INTERPOLATOR);
        ofFloat.start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVisualProgress(float progress) {
        this.mVisualProgress = progress;
        invalidate();
    }

    private void startDrag(MotionEvent event) {
        setPressed(true);
        onStartTrackingTouch();
        trackTouchEvent(event);
    }

    void onStartTrackingTouch() {
        this.mIsDragging = true;
    }

    void onStopTrackingTouch() {
        this.mIsDragging = false;
        int round = Math.round(this.mProgress / getGap());
        if (round > this.mMarkTextsList.size() - 1) {
            round = this.mMarkTextsList.size() - 1;
        }
        if (this.mCurrentItem != round) {
            this.mCurrentItem = round;
            invalidate();
        }
        this.mListener.onChanged(this.mCurrentItem);
    }

    public void setEntries(List<String> entries) {
        this.mMarkTextsList.clear();
        this.mMarkTextsList.addAll(entries);
    }

    public int getSize() {
        return this.mMarkTextsList.size();
    }

    public void setListener(OnSlierTickListener listener) {
        this.mListener = listener;
    }
}
