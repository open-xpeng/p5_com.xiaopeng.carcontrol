package xiaopeng.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;

/* loaded from: classes3.dex */
public class SimpleSlider extends View {
    private static final int MAX_LEVEL = 10000;
    private static final int MAX_VALUE = 100;
    private static final int MIN_VALUE = 0;
    private static final int NO_ALPHA = 255;
    public static final int TOUCH_MODE_SEEK = 1;
    public static final int TOUCH_MODE_SLIDE = 0;
    private float mDisabledAlpha;
    private boolean mEnabled;
    private int mMax;
    private boolean mMaxInitialized;
    private int mMin;
    private boolean mMinInitialized;
    private OnSlideChangeListener mOnSlideChangeListener;
    private int mProgress;
    private Drawable mProgressDrawable;
    private int mProgressDrawableRes;
    private int mScaledTouchSlop;
    private float mSlideScale;
    private Drawable mTickMark;
    private boolean mTickMarkAll;
    private float mTickMarkPadding;
    private boolean mTickMarkProgressEnd;
    private int mTickMarkRes;
    private TouchEventHandler mTouchEventHandler;
    protected int mTouchMode;

    /* loaded from: classes3.dex */
    public interface OnSlideChangeListener {
        void onProgressChanged(SimpleSlider simpleSlider, int i, boolean z);

        void onStartTrackingTouch(SimpleSlider simpleSlider);

        void onStopTrackingTouch(SimpleSlider simpleSlider);
    }

    /* loaded from: classes3.dex */
    public interface TouchEventHandler {
        boolean onTouchEvent(SimpleSlider simpleSlider, MotionEvent motionEvent);
    }

    public SimpleSlider(Context context) {
        this(context, null);
    }

    public SimpleSlider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.style.SimpleSlider);
    }

    public SimpleSlider(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.SimpleSlider);
    }

    public SimpleSlider(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mProgress = 0;
        this.mMin = 0;
        this.mMax = 100;
        this.mMaxInitialized = false;
        this.mMinInitialized = false;
        this.mSlideScale = 1.0f;
        this.mProgressDrawableRes = -1;
        this.mTickMarkRes = -1;
        this.mTickMarkAll = false;
        this.mTickMarkPadding = 0.0f;
        this.mTickMarkProgressEnd = false;
        this.mTouchMode = 0;
        this.mEnabled = true;
        init(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        setEnabled(this.mEnabled);
    }

    private void init(Context context, AttributeSet attributeSet, int i, int i2) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SimpleSlider, i, i2);
        this.mProgressDrawableRes = obtainStyledAttributes.getResourceId(R.styleable.SimpleSlider_android_progressDrawable, this.mProgressDrawableRes);
        setProgressDrawable(obtainStyledAttributes.getDrawable(R.styleable.SimpleSlider_android_progressDrawable));
        this.mTickMarkRes = obtainStyledAttributes.getResourceId(R.styleable.SimpleSlider_android_tickMark, this.mTickMarkRes);
        setTickMark(obtainStyledAttributes.getDrawable(R.styleable.SimpleSlider_android_tickMark));
        this.mTickMarkAll = obtainStyledAttributes.getBoolean(R.styleable.SimpleSlider_ss_tickMarkAll, this.mTickMarkAll);
        this.mTickMarkPadding = obtainStyledAttributes.getDimension(R.styleable.SimpleSlider_ss_tickMark_padding, this.mTickMarkPadding);
        this.mTickMarkProgressEnd = obtainStyledAttributes.getBoolean(R.styleable.SimpleSlider_ss_tickMark_ProgressEnd, this.mTickMarkProgressEnd);
        this.mSlideScale = obtainStyledAttributes.getFloat(R.styleable.SimpleSlider_ss_slideScale, this.mSlideScale);
        this.mEnabled = obtainStyledAttributes.getBoolean(R.styleable.SimpleSlider_android_enabled, this.mEnabled);
        this.mDisabledAlpha = obtainStyledAttributes.getFloat(R.styleable.SimpleSlider_android_disabledAlpha, 0.36f);
        setMin(obtainStyledAttributes.getInt(R.styleable.SimpleSlider_android_min, this.mMin));
        setMax(obtainStyledAttributes.getInt(R.styleable.SimpleSlider_android_max, this.mMax));
        setProgress(obtainStyledAttributes.getInt(R.styleable.SimpleSlider_android_progress, this.mProgress));
        int i3 = obtainStyledAttributes.getInt(R.styleable.SimpleSlider_ss_touchMode, this.mTouchMode);
        this.mTouchMode = i3;
        this.mTouchEventHandler = generateTouchEventHandler(i3);
        obtainStyledAttributes.recycle();
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    protected TouchEventHandler generateTouchEventHandler(int i) {
        if (i == 1) {
            return new SeekHandler();
        }
        return new SlideHandler();
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    int getScaledTouchSlop() {
        return this.mScaledTouchSlop;
    }

    public void setTouchEventHandler(TouchEventHandler touchEventHandler) {
        this.mTouchEventHandler = touchEventHandler;
    }

    public void refreshVisual() {
        if (this.mProgressDrawableRes != -1) {
            setProgressDrawable(getContext().getDrawable(this.mProgressDrawableRes));
        }
        if (this.mTickMarkRes != -1) {
            setTickMark(getContext().getDrawable(this.mTickMarkRes));
        }
        int range = getRange();
        setVisualProgress(range > 0 ? (getProgress() - getMin()) / range : 0.0f);
    }

    public int getMin() {
        return this.mMin;
    }

    public void setMin(int i) {
        int i2;
        boolean z = this.mMaxInitialized;
        if (z && i > (i2 = this.mMax)) {
            i = i2;
        }
        this.mMinInitialized = true;
        if (z && i != this.mMin) {
            this.mMin = i;
            postInvalidate();
            if (this.mProgress < i) {
                this.mProgress = i;
            }
            setProgress(this.mProgress);
            return;
        }
        this.mMin = i;
    }

    public int getMax() {
        return this.mMax;
    }

    public void setMax(int i) {
        int i2;
        boolean z = this.mMinInitialized;
        if (z && i < (i2 = this.mMin)) {
            i = i2;
        }
        this.mMaxInitialized = true;
        if (z && i != this.mMax) {
            this.mMax = i;
            postInvalidate();
            if (this.mProgress > i) {
                this.mProgress = i;
            }
            setProgress(this.mProgress);
            return;
        }
        this.mMax = i;
    }

    public int getRange() {
        return this.mMax - this.mMin;
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void setOnSlideChangeListener(OnSlideChangeListener onSlideChangeListener) {
        this.mOnSlideChangeListener = onSlideChangeListener;
    }

    public void setProgress(int i) {
        setProgressInternal(i, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setProgressInternal(int i, boolean z) {
        int limitProgress = limitProgress(i);
        this.mProgress = limitProgress;
        int i2 = this.mMax;
        int i3 = this.mMin;
        int i4 = i2 - i3;
        setVisualProgress(i4 > 0 ? (limitProgress - i3) / i4 : 0.0f);
        OnSlideChangeListener onSlideChangeListener = this.mOnSlideChangeListener;
        if (onSlideChangeListener != null) {
            onSlideChangeListener.onProgressChanged(this, limitProgress, z);
        }
    }

    public float getSlideScale() {
        return this.mSlideScale;
    }

    public void setSlideScale(float f) {
        this.mSlideScale = f;
    }

    public void setProgressDrawable(Drawable drawable) {
        Drawable drawable2 = this.mProgressDrawable;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
                unscheduleDrawable(this.mProgressDrawable);
            }
            this.mProgressDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
                drawable.setLayoutDirection(getLayoutDirection());
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
                updateDrawableBounds(getWidth(), getHeight());
                updateDrawableState();
            }
        }
    }

    public void setTickMark(Drawable drawable) {
        Drawable drawable2 = this.mTickMark;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.mTickMark = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            drawable.setLayoutDirection(getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
        }
        invalidate();
    }

    public Drawable getTickMark() {
        return this.mTickMark;
    }

    private void updateDrawableBounds(int i, int i2) {
        int paddingRight = i - (getPaddingRight() + getPaddingLeft());
        int paddingTop = i2 - (getPaddingTop() + getPaddingBottom());
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, paddingRight, paddingTop);
        }
    }

    private void updateDrawableState() {
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mProgressDrawable;
        if ((drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState)) {
            invalidate();
        }
    }

    protected void setVisualProgress(float f) {
        Drawable drawable = this.mProgressDrawable;
        if ((drawable instanceof LayerDrawable) && (drawable = ((LayerDrawable) drawable).findDrawableByLayerId(16908301)) == null) {
            drawable = this.mProgressDrawable;
        }
        if (drawable != null) {
            drawable.setLevel((int) (f * 10000.0f));
        } else {
            invalidate();
        }
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            drawable.setAlpha(isEnabled() ? 255 : (int) (this.mDisabledAlpha * 255.0f));
        }
        Drawable drawable2 = this.mTickMark;
        if (drawable2 != null) {
            drawable2.setAlpha(isEnabled() ? 255 : (int) (this.mDisabledAlpha * 255.0f));
        }
        super.drawableStateChanged();
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mProgressDrawable || super.verifyDrawable(drawable);
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (isEnabled()) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                setPressed(true);
            } else if (actionMasked == 1 || actionMasked == 3) {
                setPressed(false);
            }
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isEnabled()) {
            TouchEventHandler touchEventHandler = this.mTouchEventHandler;
            if (touchEventHandler != null) {
                return touchEventHandler.onTouchEvent(this, motionEvent);
            }
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attemptClaimDrag() {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        updateDrawableBounds(i, i2);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        drawTickMarks(canvas);
    }

    protected void drawTickMarks(Canvas canvas) {
        boolean z;
        Drawable tickMark = getTickMark();
        if (tickMark != null) {
            int max = getMax() - getMin();
            if (max > 1) {
                int intrinsicWidth = tickMark.getIntrinsicWidth();
                int intrinsicHeight = tickMark.getIntrinsicHeight();
                int i = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                int i2 = intrinsicHeight >= 0 ? intrinsicHeight / 2 : 1;
                tickMark.setBounds(-i, -i2, i, i2);
                float width = (((getWidth() - getPaddingLeft()) - getPaddingRight()) - (this.mTickMarkPadding * 2.0f)) / max;
                int save = canvas.save();
                canvas.translate(getPaddingLeft(), getHeight() >> 1);
                canvas.translate(this.mTickMarkPadding + width, 0.0f);
                for (int i3 = 1; i3 < max && (this.mTickMarkAll || ((!(z = this.mTickMarkProgressEnd) || i3 <= this.mProgress) && (z || i3 < this.mProgress))); i3++) {
                    tickMark.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(save);
            }
        }
    }

    protected void onStartTrackingTouch() {
        OnSlideChangeListener onSlideChangeListener = this.mOnSlideChangeListener;
        if (onSlideChangeListener != null) {
            onSlideChangeListener.onStartTrackingTouch(this);
        }
    }

    protected void onStopTrackingTouch() {
        OnSlideChangeListener onSlideChangeListener = this.mOnSlideChangeListener;
        if (onSlideChangeListener != null) {
            onSlideChangeListener.onStopTrackingTouch(this);
        }
    }

    private int limitProgress(int i) {
        return Math.min(this.mMax, Math.max(i, this.mMin));
    }

    public boolean isInScrollingContainer() {
        for (ViewParent parent = getParent(); parent != null && (parent instanceof ViewGroup); parent = parent.getParent()) {
            if (((ViewGroup) parent).shouldDelayChildPressedState()) {
                return true;
            }
        }
        return false;
    }

    /* loaded from: classes3.dex */
    public static class SlideHandler implements TouchEventHandler {
        private boolean mIsDragging;
        private SimpleSlider mSimpleSlider;
        private int mTouchDownProgress;
        private float mTouchDownX;

        @Override // xiaopeng.widget.SimpleSlider.TouchEventHandler
        public boolean onTouchEvent(SimpleSlider simpleSlider, MotionEvent motionEvent) {
            this.mSimpleSlider = simpleSlider;
            return handleSlide(motionEvent);
        }

        /* JADX WARN: Code restructure failed: missing block: B:8:0x000d, code lost:
            if (r0 != 3) goto L8;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private boolean handleSlide(android.view.MotionEvent r4) {
            /*
                r3 = this;
                int r0 = r4.getActionMasked()
                r1 = 1
                if (r0 == 0) goto L53
                if (r0 == r1) goto L46
                r2 = 2
                if (r0 == r2) goto L10
                r4 = 3
                if (r0 == r4) goto L46
                goto L59
            L10:
                boolean r0 = r3.mIsDragging
                if (r0 == 0) goto L18
                r3.trackTouchEvent(r4)
                goto L59
            L18:
                float r0 = r4.getX()
                float r2 = r3.mTouchDownX
                float r0 = r0 - r2
                float r0 = java.lang.Math.abs(r0)
                xiaopeng.widget.SimpleSlider r2 = r3.mSimpleSlider
                int r2 = r2.getScaledTouchSlop()
                float r2 = (float) r2
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 <= 0) goto L59
                xiaopeng.widget.SimpleSlider r0 = r3.mSimpleSlider
                int r0 = r0.getProgress()
                r3.mTouchDownProgress = r0
                r3.mIsDragging = r1
                xiaopeng.widget.SimpleSlider r0 = r3.mSimpleSlider
                r0.onStartTrackingTouch()
                r3.trackTouchEvent(r4)
                xiaopeng.widget.SimpleSlider r4 = r3.mSimpleSlider
                xiaopeng.widget.SimpleSlider.access$000(r4)
                goto L59
            L46:
                boolean r4 = r3.mIsDragging
                if (r4 == 0) goto L59
                xiaopeng.widget.SimpleSlider r4 = r3.mSimpleSlider
                r4.onStopTrackingTouch()
                r4 = 0
                r3.mIsDragging = r4
                goto L59
            L53:
                float r4 = r4.getX()
                r3.mTouchDownX = r4
            L59:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: xiaopeng.widget.SimpleSlider.SlideHandler.handleSlide(android.view.MotionEvent):boolean");
        }

        private void trackTouchEvent(MotionEvent motionEvent) {
            this.mSimpleSlider.setProgressInternal(Math.round(this.mTouchDownProgress + ((Math.round(motionEvent.getX() - this.mTouchDownX) / ((this.mSimpleSlider.getWidth() - this.mSimpleSlider.getPaddingLeft()) - this.mSimpleSlider.getPaddingRight())) * this.mSimpleSlider.getSlideScale() * (this.mSimpleSlider.getMax() - this.mSimpleSlider.getMin())) + this.mSimpleSlider.getMin()), true);
        }
    }

    /* loaded from: classes3.dex */
    public static class SeekHandler implements TouchEventHandler {
        private boolean mIsDragging;
        private SimpleSlider mSimpleSlider;
        private float mTouchDownX;

        @Override // xiaopeng.widget.SimpleSlider.TouchEventHandler
        public boolean onTouchEvent(SimpleSlider simpleSlider, MotionEvent motionEvent) {
            this.mSimpleSlider = simpleSlider;
            return handleSeekTouch(motionEvent);
        }

        private boolean handleSeekTouch(MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action != 0) {
                if (action != 1) {
                    if (action == 2) {
                        if (this.mIsDragging) {
                            trackTouchEvent(motionEvent);
                        } else if (Math.abs(motionEvent.getX() - this.mTouchDownX) > this.mSimpleSlider.getScaledTouchSlop()) {
                            onStartTrackingTouch();
                            trackTouchEvent(motionEvent);
                            this.mSimpleSlider.attemptClaimDrag();
                        }
                    } else if (action == 3 && this.mIsDragging) {
                        onStopTrackingTouch();
                    }
                } else if (this.mIsDragging) {
                    trackTouchEvent(motionEvent);
                    onStopTrackingTouch();
                } else {
                    onStartTrackingTouch();
                    trackTouchEvent(motionEvent);
                    onStopTrackingTouch();
                }
            } else if (this.mSimpleSlider.isInScrollingContainer()) {
                this.mTouchDownX = motionEvent.getX();
            } else {
                onStartTrackingTouch();
                trackTouchEvent(motionEvent);
                this.mSimpleSlider.attemptClaimDrag();
            }
            return true;
        }

        private void onStartTrackingTouch() {
            this.mIsDragging = true;
            this.mSimpleSlider.onStartTrackingTouch();
        }

        private void onStopTrackingTouch() {
            this.mIsDragging = false;
            this.mSimpleSlider.onStopTrackingTouch();
        }

        private void trackTouchEvent(MotionEvent motionEvent) {
            this.mSimpleSlider.setProgressInternal(Math.round(((Math.round(motionEvent.getX()) / ((this.mSimpleSlider.getWidth() - this.mSimpleSlider.getPaddingLeft()) - this.mSimpleSlider.getPaddingRight())) * (this.mSimpleSlider.getMax() - this.mSimpleSlider.getMin())) + this.mSimpleSlider.getMin() + 0.0f), true);
        }
    }
}
