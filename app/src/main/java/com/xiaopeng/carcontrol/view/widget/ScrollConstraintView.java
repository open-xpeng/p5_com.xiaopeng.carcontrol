package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.UIUtils;

/* loaded from: classes2.dex */
public class ScrollConstraintView extends ConstraintLayout {
    private static final String TAG = "ScrollConstraintView";
    private boolean isPressed;
    private Context mContext;
    private boolean mIsScroll;
    private int mMaxScrollY;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedIntercept;
    private OnScrollStateListener mOnScrollStateListener;
    private int mPageVisibleHeight;
    private int mScaleTouchSlop;
    private int mScrollYSlop;
    private OverScroller mScroller;
    private float mTouchDownY;
    private float mTouchY;
    private VelocityTracker mVelocityTracker;

    /* loaded from: classes2.dex */
    public interface OnScrollStateListener {
        void onScrollFinish();

        void onScrollStart();
    }

    public ScrollConstraintView(Context context) {
        super(context);
        init(context);
    }

    public ScrollConstraintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollConstraintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mScaleTouchSlop = UIUtils.dp2px(context, 10);
        this.mScrollYSlop = UIUtils.dp2px(this.mContext, 100);
        this.mPageVisibleHeight = UIUtils.dp2px(this.mContext, 800);
        this.mScroller = new OverScroller(context);
        this.mVelocityTracker = VelocityTracker.obtain();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mContext);
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.mMaxScrollY = getMeasuredHeight() - this.mPageVisibleHeight;
    }

    public void setNeedIntercept(boolean needIntercept) {
        this.mNeedIntercept = needIntercept;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        OnScrollStateListener onScrollStateListener;
        LogUtils.d(TAG, "onInterceptTouchEvent " + event.getAction());
        int action = event.getAction();
        if (action == 0) {
            this.mVelocityTracker.addMovement(event);
            float y = event.getY();
            this.mTouchDownY = y;
            this.mTouchY = y;
            this.mIsScroll = false;
            this.mScroller.forceFinished(true);
            this.mNeedIntercept = false;
        } else if (action == 2) {
            if (!this.mNeedIntercept && Math.abs(event.getY() - this.mTouchDownY) > this.mScaleTouchSlop) {
                this.mIsScroll = true;
                this.isPressed = true;
            }
            if (this.mIsScroll && (onScrollStateListener = this.mOnScrollStateListener) != null) {
                onScrollStateListener.onScrollStart();
            }
            return this.mIsScroll;
        }
        return super.onInterceptTouchEvent(event);
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001b, code lost:
        if (r1 != 3) goto L8;
     */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00bd  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r11) {
        /*
            Method dump skipped, instructions count: 211
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.widget.ScrollConstraintView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void scroll2Top() {
        OnScrollStateListener onScrollStateListener = this.mOnScrollStateListener;
        if (onScrollStateListener != null) {
            onScrollStateListener.onScrollStart();
        }
        postInvalidate();
        this.mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), 500);
    }

    public void scroll2Setting() {
        OnScrollStateListener onScrollStateListener = this.mOnScrollStateListener;
        if (onScrollStateListener != null) {
            onScrollStateListener.onScrollStart();
        }
        this.mScroller.startScroll(0, getScrollY(), 0, this.mPageVisibleHeight - getScrollY(), 500);
        postInvalidate();
    }

    @Override // android.view.View
    public void computeScroll() {
        OnScrollStateListener onScrollStateListener;
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            invalidate();
        } else if (this.isPressed || (onScrollStateListener = this.mOnScrollStateListener) == null) {
        } else {
            onScrollStateListener.onScrollFinish();
        }
    }

    public void setOnScrollStateListener(OnScrollStateListener onScrollStateListener) {
        this.mOnScrollStateListener = onScrollStateListener;
    }
}
