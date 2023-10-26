package com.xiaopeng.carcontrol.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrolmodule.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public abstract class BaseSeatControlView extends View implements ISeatView {
    protected static final int BUTTON_BACK_DOWN = 6;
    protected static final int BUTTON_BACK_UP = 5;
    protected static final int BUTTON_LEG_BACK = 14;
    protected static final int BUTTON_LEG_DOWN = 8;
    protected static final int BUTTON_LEG_FRONT = 13;
    protected static final int BUTTON_LEG_UP = 7;
    protected static final int BUTTON_LUMBAR_BACK = 10;
    protected static final int BUTTON_LUMBAR_DOWN = 12;
    protected static final int BUTTON_LUMBAR_FRONT = 9;
    protected static final int BUTTON_LUMBAR_UP = 11;
    protected static final int BUTTON_SEAT_BACK = 2;
    protected static final int BUTTON_SEAT_DOWN = 4;
    protected static final int BUTTON_SEAT_FRONT = 1;
    protected static final int BUTTON_SEAT_UP = 3;
    protected static int CONTROL_MODE_CHAIR = 1;
    protected static int CONTROL_MODE_CUSHION = 4;
    protected static int CONTROL_MODE_LEG = 3;
    protected static int CONTROL_MODE_LUMBAR = 2;
    protected static int CONTROL_MODE_SECOND_ROW = 5;
    public static final int DIRECTION_BACK_DOWN = 2;
    public static final int DIRECTION_FRONT_UP = 1;
    protected final String TAG;
    private boolean mBtnPressed;
    private int mControlBtn;
    protected int mControlMode;
    private PaintFlagsDrawFilter mDrawFilter;
    private int mEventDownButton;
    protected int mHorPosition;
    protected int mLegHorPosition;
    protected int mLegVerPosition;
    protected Drawable mMidDownDisable;
    protected Drawable mMidDownEnable;
    protected Drawable mMidDownPressed;
    private OnButtonClickListener mOnButtonClickListener;
    private OnButtonTouchListener mOnButtonTouchListener;
    protected int mTiltPosition;
    protected int mVerPosition;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface ControlBtn {
    }

    /* loaded from: classes2.dex */
    public interface OnButtonClickListener {
        default void onLegHorTouched(boolean isTouch, int direction) {
        }

        default void onLegTouched(boolean isTouch, int direction) {
        }

        default void onLumbarHorTouched(boolean isTouch, int direction) {
        }

        default void onLumbarVerTouched(boolean isTouch, int direction) {
        }

        default void onSeatHorTouched(boolean isTouch, int direction) {
        }

        default void onSeatVerTouched(boolean isTouch, int direction) {
        }

        default void onTiltTouched(boolean isTouch, int direction) {
        }
    }

    /* loaded from: classes2.dex */
    public interface OnButtonTouchListener {
        void onButtonTouchDown();

        void onButtonTouchUp();
    }

    protected abstract void initView(Context context);

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isButtonEnable(int position, boolean isUpFront) {
        if ((!isUpFront || position < 98) && !isUpFront && position <= 2) {
        }
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.ISeatView
    public boolean needPressedStatus() {
        return false;
    }

    protected abstract void onThemeChanged();

    protected abstract int resolveBtnWithMode(float x, float y);

    public BaseSeatControlView(Context context) {
        super(context);
        this.TAG = getClass().getSimpleName();
        this.mControlMode = CONTROL_MODE_CHAIR;
        this.mTiltPosition = 0;
        this.mBtnPressed = false;
        this.mMidDownPressed = null;
        initView(context);
    }

    public BaseSeatControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.TAG = getClass().getSimpleName();
        this.mControlMode = CONTROL_MODE_CHAIR;
        this.mTiltPosition = 0;
        this.mBtnPressed = false;
        this.mMidDownPressed = null;
        initView(context);
    }

    public BaseSeatControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.TAG = getClass().getSimpleName();
        this.mControlMode = CONTROL_MODE_CHAIR;
        this.mTiltPosition = 0;
        this.mBtnPressed = false;
        this.mMidDownPressed = null;
        initView(context);
    }

    @Override // com.xiaopeng.carcontrol.view.ISeatView
    public void setTiltPosition(int position) {
        LogUtils.d(this.TAG, "setTiltPosition " + position);
        if (position > 100 || position < 0) {
            return;
        }
        int i = this.mTiltPosition;
        this.mTiltPosition = position;
        invalidate();
        if (CarStatusUtils.isD55CarType()) {
            if (i < 98 && position >= 98 && this.mControlBtn == 5 && this.mBtnPressed) {
                NotificationHelper.getInstance().showToast(R.string.seat_tilt_min_toast);
            } else if (i <= 2 || position > 2 || this.mControlBtn != 6 || !this.mBtnPressed) {
            } else {
                NotificationHelper.getInstance().showToast(R.string.seat_tilt_max_toast);
            }
        } else if (i < 98 && position >= 98 && this.mControlBtn == 6 && this.mBtnPressed) {
            NotificationHelper.getInstance().showToast(R.string.seat_tilt_max_toast);
        } else if (i <= 2 || position > 2 || this.mControlBtn != 5 || !this.mBtnPressed) {
        } else {
            NotificationHelper.getInstance().showToast(R.string.seat_tilt_min_toast);
        }
    }

    public int getTiltPosition() {
        return this.mTiltPosition;
    }

    @Override // com.xiaopeng.carcontrol.view.ISeatView
    public void setHorPosition(int position) {
        LogUtils.d(this.TAG, "setHorPosition " + position);
        if (position > 100 || position < 0) {
            return;
        }
        int i = this.mHorPosition;
        this.mHorPosition = position;
        invalidate();
        if (i < 98 && position >= 98 && this.mControlBtn == 1 && this.mBtnPressed) {
            NotificationHelper.getInstance().showToast(R.string.seat_horz_max_toast);
        } else if (i <= 2 || position > 2 || this.mControlBtn != 2 || !this.mBtnPressed) {
        } else {
            NotificationHelper.getInstance().showToast(R.string.seat_horz_min_toast);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.ISeatView
    public void setVerPosition(int position) {
        LogUtils.d(this.TAG, "setVerPosition " + position);
        if (position > 100 || position < 0) {
            return;
        }
        int i = this.mVerPosition;
        this.mVerPosition = position;
        invalidate();
        if (i < 98 && position >= 98 && this.mControlBtn == 3 && this.mBtnPressed) {
            NotificationHelper.getInstance().showToast(R.string.seat_ver_max_toast);
        } else if (i <= 2 || position > 2 || this.mControlBtn != 4 || !this.mBtnPressed) {
        } else {
            NotificationHelper.getInstance().showToast(R.string.seat_ver_min_toast);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.ISeatView
    public void setLegVerPosition(int position) {
        LogUtils.d(this.TAG, "setLegVerPosition " + position);
        if (position > 100 || position < 0) {
            return;
        }
        int i = this.mLegVerPosition;
        this.mLegVerPosition = position;
        invalidate();
        if (i < 98 && position >= 98 && this.mControlBtn == 7 && this.mBtnPressed) {
            NotificationHelper.getInstance().showToast(R.string.seat_cushion_max_toast);
        } else if (i <= 2 || position > 2 || this.mControlBtn != 8 || !this.mBtnPressed) {
        } else {
            NotificationHelper.getInstance().showToast(R.string.seat_cushion_min_toast);
        }
    }

    public void setLegHorPosition(int position) {
        LogUtils.d(this.TAG, "setLegHorPosition:" + position);
        if (position > 100 || position < 0) {
            return;
        }
        int i = this.mLegHorPosition;
        this.mLegHorPosition = position;
        invalidate();
        if (i < 98 && position >= 98 && this.mControlBtn == 13 && this.mBtnPressed) {
            NotificationHelper.getInstance().showToast(R.string.seat_leg_max_toast);
        } else if (i <= 2 || position > 2 || this.mControlBtn != 14 || !this.mBtnPressed) {
        } else {
            NotificationHelper.getInstance().showToast(R.string.seat_leg_min_toast);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.ISeatView
    public void setControlModeChair() {
        this.mControlMode = CONTROL_MODE_CHAIR;
        invalidate();
    }

    @Override // com.xiaopeng.carcontrol.view.ISeatView
    public void setControlModeLumbar() {
        this.mControlMode = CONTROL_MODE_LUMBAR;
        invalidate();
    }

    @Override // com.xiaopeng.carcontrol.view.ISeatView
    public void setControlModeCushion() {
        this.mControlMode = CONTROL_MODE_CUSHION;
        invalidate();
    }

    @Override // com.xiaopeng.carcontrol.view.ISeatView
    public void setControlModeLeg() {
        this.mControlMode = CONTROL_MODE_LEG;
        invalidate();
    }

    public void setControlModeSecondRow() {
        this.mControlMode = CONTROL_MODE_SECOND_ROW;
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDrawFilter == null) {
            this.mDrawFilter = new PaintFlagsDrawFilter(0, 3);
        }
        canvas.setDrawFilter(this.mDrawFilter);
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (action == 1 || action == 3) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (getAlpha() >= 1.0f && this.mOnButtonClickListener != null) {
            int action = event.getAction();
            if (action == 0) {
                int resolveBtnWithMode = resolveBtnWithMode(event.getX(), event.getY());
                this.mEventDownButton = resolveBtnWithMode;
                if (resolveBtnWithMode != 0) {
                    checkPositionOnTouchDown();
                    onTouchDown();
                    playSoundEffect(0);
                    touchCallback(this.mEventDownButton, true);
                    handlePressed(this.mEventDownButton, true);
                    return true;
                }
            } else if (action != 1) {
                if (action == 2) {
                    int resolveBtnWithMode2 = resolveBtnWithMode(event.getX(), event.getY());
                    int i = this.mEventDownButton;
                    if (resolveBtnWithMode2 != i && i != 0) {
                        onTouchUp();
                        touchCallback(this.mEventDownButton, false);
                        handlePressed(this.mEventDownButton, false);
                        this.mEventDownButton = 0;
                    }
                } else if (action == 3 && this.mEventDownButton != 0) {
                    onTouchUp();
                    touchCallback(this.mEventDownButton, false);
                    handlePressed(this.mEventDownButton, false);
                    this.mEventDownButton = 0;
                }
            } else if (this.mEventDownButton != 0) {
                onTouchUp();
                touchCallback(this.mEventDownButton, false);
                handlePressed(this.mEventDownButton, false);
                this.mEventDownButton = 0;
                return true;
            }
        }
        return false;
    }

    private void checkPositionOnTouchDown() {
        int i = this.mEventDownButton;
        if (i == 13) {
            if (this.mLegHorPosition <= 2) {
                NotificationHelper.getInstance().showToast(R.string.seat_leg_max_toast);
            }
        } else if (i != 14) {
            switch (i) {
                case 1:
                    if (this.mHorPosition >= 98) {
                        NotificationHelper.getInstance().showToast(R.string.seat_horz_max_toast);
                        return;
                    }
                    return;
                case 2:
                    if (this.mHorPosition <= 2) {
                        NotificationHelper.getInstance().showToast(R.string.seat_horz_min_toast);
                        return;
                    }
                    return;
                case 3:
                    if (this.mVerPosition >= 98) {
                        NotificationHelper.getInstance().showToast(R.string.seat_ver_max_toast);
                        return;
                    }
                    return;
                case 4:
                    if (this.mVerPosition <= 2) {
                        NotificationHelper.getInstance().showToast(R.string.seat_ver_min_toast);
                        return;
                    }
                    return;
                case 5:
                    if ((!CarStatusUtils.isD55CarType() || this.mTiltPosition < 98) && (CarStatusUtils.isD55CarType() || this.mTiltPosition > 2)) {
                        return;
                    }
                    NotificationHelper.getInstance().showToast(R.string.seat_tilt_min_toast);
                    return;
                case 6:
                    if ((!CarStatusUtils.isD55CarType() || this.mTiltPosition > 2) && (CarStatusUtils.isD55CarType() || this.mTiltPosition < 98)) {
                        return;
                    }
                    NotificationHelper.getInstance().showToast(R.string.seat_tilt_max_toast);
                    return;
                case 7:
                    if (this.mLegVerPosition >= 98) {
                        NotificationHelper.getInstance().showToast(R.string.seat_cushion_max_toast);
                        return;
                    }
                    return;
                case 8:
                    if (this.mLegVerPosition <= 2) {
                        NotificationHelper.getInstance().showToast(R.string.seat_cushion_min_toast);
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else if (this.mLegHorPosition >= 98) {
            NotificationHelper.getInstance().showToast(R.string.seat_leg_min_toast);
        }
    }

    private void onTouchDown() {
        OnButtonTouchListener onButtonTouchListener = this.mOnButtonTouchListener;
        if (onButtonTouchListener != null) {
            onButtonTouchListener.onButtonTouchDown();
        }
    }

    private void onTouchUp() {
        OnButtonTouchListener onButtonTouchListener = this.mOnButtonTouchListener;
        if (onButtonTouchListener != null) {
            onButtonTouchListener.onButtonTouchUp();
        }
    }

    private void handlePressed(int button, boolean isPressed) {
        if (needPressedStatus()) {
            this.mBtnPressed = isPressed;
            this.mControlBtn = button;
            invalidate();
        }
    }

    private void touchCallback(int button, boolean isTouch) {
        LogUtils.d(this.TAG, "touchCallback button " + button);
        switch (button) {
            case 1:
                if (isButtonEnable(this.mHorPosition, true)) {
                    this.mOnButtonClickListener.onSeatHorTouched(isTouch, 1);
                    return;
                }
                return;
            case 2:
                if (isButtonEnable(this.mHorPosition, false)) {
                    this.mOnButtonClickListener.onSeatHorTouched(isTouch, 2);
                    return;
                }
                return;
            case 3:
                if (isButtonEnable(this.mVerPosition, true)) {
                    this.mOnButtonClickListener.onSeatVerTouched(isTouch, 1);
                    return;
                }
                return;
            case 4:
                if (isButtonEnable(this.mVerPosition, false)) {
                    this.mOnButtonClickListener.onSeatVerTouched(isTouch, 2);
                    return;
                }
                return;
            case 5:
                if (isButtonEnable(this.mTiltPosition, false)) {
                    this.mOnButtonClickListener.onTiltTouched(isTouch, 1);
                    return;
                }
                return;
            case 6:
                if (isButtonEnable(this.mTiltPosition, true)) {
                    this.mOnButtonClickListener.onTiltTouched(isTouch, 2);
                    return;
                }
                return;
            case 7:
                if (isButtonEnable(this.mLegVerPosition, true)) {
                    this.mOnButtonClickListener.onLegTouched(isTouch, 1);
                    return;
                }
                return;
            case 8:
                if (isButtonEnable(this.mLegVerPosition, false)) {
                    this.mOnButtonClickListener.onLegTouched(isTouch, 2);
                    return;
                }
                return;
            case 9:
                this.mOnButtonClickListener.onLumbarHorTouched(isTouch, 1);
                return;
            case 10:
                this.mOnButtonClickListener.onLumbarHorTouched(isTouch, 2);
                return;
            case 11:
                this.mOnButtonClickListener.onLumbarVerTouched(isTouch, 1);
                return;
            case 12:
                this.mOnButtonClickListener.onLumbarVerTouched(isTouch, 2);
                return;
            case 13:
                if (isButtonEnable(this.mLegHorPosition, true)) {
                    this.mOnButtonClickListener.onLegHorTouched(isTouch, 1);
                    return;
                }
                return;
            case 14:
                if (isButtonEnable(this.mLegHorPosition, false)) {
                    this.mOnButtonClickListener.onLegHorTouched(isTouch, 2);
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected Drawable getArrowDrawable(int btn, int position, boolean isUpFront) {
        Drawable drawable;
        if (isButtonEnable(position, isUpFront)) {
            return (needPressedStatus() && btn == this.mControlBtn && this.mBtnPressed && (drawable = this.mMidDownPressed) != null) ? drawable : this.mMidDownEnable;
        }
        return this.mMidDownDisable;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onThemeChanged();
    }

    @Override // com.xiaopeng.carcontrol.view.ISeatView
    public void setOnButtonTouchListener(OnButtonTouchListener onButtonTouchListener) {
        this.mOnButtonTouchListener = onButtonTouchListener;
    }

    @Override // com.xiaopeng.carcontrol.view.ISeatView
    public void stopControl() {
        int i = this.mEventDownButton;
        if (i == 0 || this.mOnButtonClickListener == null) {
            return;
        }
        touchCallback(i, false);
        this.mEventDownButton = 0;
    }

    @Override // com.xiaopeng.carcontrol.view.ISeatView
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.mOnButtonClickListener = onButtonClickListener;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onThemeChanged();
        invalidate();
    }
}
