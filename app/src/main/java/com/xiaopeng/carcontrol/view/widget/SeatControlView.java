package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.view.BaseSeatControlView;

/* loaded from: classes2.dex */
public class SeatControlView extends BaseSeatControlView {
    private static final int DIMEN_HORIZONTAL_LINE_LEFT = 70;
    private static final int DIMEN_LEG_DOWN_BTN_TOP = 389;
    private static final int DIMEN_LEG_LEFT = 120;
    private static final int DIMEN_LEG_TOP = 300;
    private static final int DIMEN_LEG_UP_BTN_TOP = 200;
    private static final int DIMEN_LUMBAR_BACK_BTN_LEFT = 350;
    private static final int DIMEN_LUMBAR_DOWN_BTN_TOP = 310;
    private static final int DIMEN_LUMBAR_FRONT_BTN_LEFT = 222;
    private static final int DIMEN_LUMBAR_FRONT_BTN_TOP = 209;
    private static final int DIMEN_LUMBAR_LEFT = 304;
    private static final int DIMEN_LUMBAR_TOP = 182;
    private static final int DIMEN_LUMBAR_UP_BTN_LEFT = 313;
    private static final int DIMEN_LUMBAR_UP_BTN_TOP = 108;
    private static final int DIMEN_PADDING_TOP = 40;
    private static final int DIMEN_SEAT_CONTROL_LEFT = 225;
    private static final int DIMEN_SEAT_CONTROL_TOP = 335;
    private static final int DIMEN_SEAT_DOWN_BTN_TOP = 406;
    private static final int DIMEN_SEAT_FRONT_BTN_LEFT = 10;
    private static final int DIMEN_SEAT_FRONT_BTN_TOP = 340;
    private static final int DIMEN_SEAT_UP_BTN_TOP = 198;
    private static final int DIMEN_TILT_DOWN_BTN_LEFT = 625;
    private static final int DIMEN_TILT_DOWN_BTN_TOP = 295;
    private static final int DIMEN_TILT_LEFT = 365;
    private static final int DIMEN_TILT_LINE_TOP = 116;
    private static final int DIMEN_TILT_TOP = 107;
    private static final int DIMEN_TILT_UP_BTN_LEFT = 290;
    private static final int DIMEN_TILT_UP_BTN_TOP = 85;
    private static final int DIMEN_VERTICAL_LINE_TOP = 261;
    private static final int DISABLE_ALPHA = 41;
    private static final int ENABLE_ALPHA = 255;
    private static final int OFFSET_TOUCH = 0;
    private boolean isDriver;
    private float mBtnHalf;
    private CarBaseConfig mCarBaseConfig;
    private Drawable mDChairBack;
    private Drawable mDChairControl;
    private Drawable mDLineBack;
    private Drawable mDLineHor;
    private Drawable mDLineVer;
    private float mHalfLine;
    private Matrix mMatrix;
    private Drawable mMidDownDisable;
    private Drawable mMidDownEnable;
    private boolean mSupportMove;

    @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView, com.xiaopeng.carcontrol.view.ISeatView
    public boolean needPressedStatus() {
        return true;
    }

    public SeatControlView(Context context) {
        super(context);
        this.isDriver = true;
    }

    public SeatControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isDriver = true;
    }

    public SeatControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.isDriver = true;
    }

    @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView
    protected void initView(Context context) {
        this.mCarBaseConfig = CarBaseConfig.getInstance();
        this.mMatrix = new Matrix();
        Drawable drawable = getResources().getDrawable(R.drawable.img_backrest, getContext().getTheme());
        this.mDChairBack = drawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mDChairBack.getIntrinsicHeight());
        }
        Drawable drawable2 = getResources().getDrawable(R.drawable.img_seats_left_control, getContext().getTheme());
        this.mDChairControl = drawable2;
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), this.mDChairControl.getIntrinsicHeight());
        }
        Drawable drawable3 = getResources().getDrawable(R.drawable.ic_seat_control_arrow_button, getContext().getTheme());
        this.mMidDownEnable = drawable3;
        if (drawable3 != null) {
            drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), this.mMidDownEnable.getIntrinsicHeight());
            this.mBtnHalf = this.mMidDownEnable.getIntrinsicWidth() / 2.0f;
            this.mMidDownEnable.mutate().setAlpha(255);
        }
        Drawable drawable4 = getResources().getDrawable(R.drawable.ic_seat_control_arrow_button, getContext().getTheme());
        this.mMidDownDisable = drawable4;
        if (drawable4 != null) {
            drawable4.setBounds(0, 0, drawable4.getIntrinsicWidth(), this.mMidDownDisable.getIntrinsicHeight());
            this.mBtnHalf = this.mMidDownDisable.getIntrinsicWidth() / 2.0f;
            this.mMidDownDisable.mutate().setAlpha(41);
        }
        setThemeDrawable();
    }

    private void setThemeDrawable() {
        Drawable drawable = getResources().getDrawable(R.drawable.img_seats_lines_arc_left, getContext().getTheme());
        this.mDLineBack = drawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mDLineBack.getIntrinsicHeight());
        }
        Drawable drawable2 = getResources().getDrawable(R.drawable.img_seats_lines_vertical, getContext().getTheme());
        this.mDLineVer = drawable2;
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), this.mDLineVer.getIntrinsicHeight());
        }
        Drawable drawable3 = getResources().getDrawable(R.drawable.img_seats_lines_horizontal, getContext().getTheme());
        this.mDLineHor = drawable3;
        if (drawable3 != null) {
            drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), this.mDLineHor.getIntrinsicHeight());
            this.mHalfLine = this.mDLineHor.getIntrinsicHeight() / 2.0f;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(680, View.MeasureSpec.getMode(widthMeasureSpec)), View.MeasureSpec.makeMeasureSpec(540, View.MeasureSpec.getMode(heightMeasureSpec)));
    }

    @Override // com.xiaopeng.carcontrol.view.ISeatView
    public void setSupportMove(boolean supportMove) {
        this.mSupportMove = supportMove;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mMidDownEnable == null || this.mDLineHor == null || this.mDChairControl == null || this.mDLineVer == null || this.mDLineBack == null) {
            return;
        }
        float horizontalMove = getHorizontalMove();
        float verticalMove = getVerticalMove();
        if (this.mControlMode == CONTROL_MODE_CHAIR) {
            drawSeatControl(canvas, horizontalMove, verticalMove);
        }
        drawChair(canvas, horizontalMove, verticalMove);
    }

    private void drawLegControl(Canvas canvas) {
        Matrix matrix = this.mMatrix;
        float f = this.mBtnHalf;
        matrix.setRotate(180.0f, f, f);
        this.mMatrix.postTranslate(120.0f, 200.0f);
        canvas.setMatrix(this.mMatrix);
        getArrowDrawable(this.mLegVerPosition, true).draw(canvas);
        this.mMatrix.setTranslate(120.0f, 300.0f);
        canvas.setMatrix(this.mMatrix);
        this.mMatrix.setTranslate(120.0f, 389.0f);
        canvas.setMatrix(this.mMatrix);
        getArrowDrawable(this.mLegVerPosition, false).draw(canvas);
    }

    private void drawLumbarControl(Canvas canvas) {
        Matrix matrix = this.mMatrix;
        float f = this.mBtnHalf;
        matrix.setRotate(180.0f, f, f);
        this.mMatrix.postTranslate(313.0f, 108.0f);
        canvas.setMatrix(this.mMatrix);
        getArrowDrawable(50, true).draw(canvas);
        this.mMatrix.setTranslate(313.0f, 310.0f);
        canvas.setMatrix(this.mMatrix);
        getArrowDrawable(50, false).draw(canvas);
        Matrix matrix2 = this.mMatrix;
        float f2 = this.mBtnHalf;
        matrix2.setRotate(90.0f, f2, f2);
        this.mMatrix.postTranslate(222.0f, 209.0f);
        canvas.setMatrix(this.mMatrix);
        getArrowDrawable(50, true).draw(canvas);
        Matrix matrix3 = this.mMatrix;
        float f3 = this.mBtnHalf;
        matrix3.setRotate(-90.0f, f3, f3);
        this.mMatrix.postTranslate(350.0f, 209.0f);
        canvas.setMatrix(this.mMatrix);
        getArrowDrawable(50, false).draw(canvas);
        this.mMatrix.setTranslate(304.0f, 182.0f);
        canvas.setMatrix(this.mMatrix);
    }

    private void drawSeatControl(Canvas canvas, float horizontalMove, float verticalMove) {
        Matrix matrix = this.mMatrix;
        float f = this.mBtnHalf;
        matrix.setRotate(90.0f, f, f);
        float f2 = verticalMove + 340.0f;
        this.mMatrix.postTranslate(10.0f, f2);
        canvas.setMatrix(this.mMatrix);
        getArrowDrawable(this.mHorPosition, true).draw(canvas);
        this.mMatrix.setTranslate(70.0f, ((this.mBtnHalf + 340.0f) - this.mHalfLine) + verticalMove);
        canvas.setMatrix(this.mMatrix);
        this.mDLineHor.draw(canvas);
        Matrix matrix2 = this.mMatrix;
        float f3 = this.mBtnHalf;
        matrix2.setRotate(-90.0f, f3, f3);
        this.mMatrix.postTranslate(this.mDLineHor.getIntrinsicWidth() + 70, f2);
        canvas.setMatrix(this.mMatrix);
        getArrowDrawable(this.mHorPosition, false).draw(canvas);
        if (this.isDriver) {
            Matrix matrix3 = this.mMatrix;
            float f4 = this.mBtnHalf;
            matrix3.setRotate(180.0f, f4, f4);
            float f5 = horizontalMove + 290.0f;
            this.mMatrix.postTranslate(f5, 198.0f);
            canvas.setMatrix(this.mMatrix);
            getArrowDrawable(this.mVerPosition, true).draw(canvas);
            this.mMatrix.setTranslate(((this.mBtnHalf + 290.0f) - this.mHalfLine) + horizontalMove, 261.0f);
            canvas.setMatrix(this.mMatrix);
            this.mDLineVer.draw(canvas);
            this.mMatrix.setTranslate(f5, 406.0f);
            canvas.setMatrix(this.mMatrix);
            getArrowDrawable(this.mVerPosition, false).draw(canvas);
        }
        Matrix matrix4 = this.mMatrix;
        float f6 = this.mBtnHalf;
        matrix4.setRotate(90.0f, f6, f6);
        this.mMatrix.postTranslate(290.0f + horizontalMove, 85.0f + verticalMove);
        canvas.setMatrix(this.mMatrix);
        getArrowDrawable(this.mTiltPosition, false).draw(canvas);
        this.mMatrix.setTranslate(this.mMidDownEnable.getIntrinsicWidth() + DIMEN_TILT_UP_BTN_LEFT + horizontalMove, 116.0f + verticalMove);
        canvas.setMatrix(this.mMatrix);
        this.mDLineBack.draw(canvas);
        Matrix matrix5 = this.mMatrix;
        float f7 = this.mBtnHalf;
        matrix5.setRotate(-30.0f, f7, f7);
        this.mMatrix.postTranslate(horizontalMove + 625.0f, verticalMove + 295.0f);
        canvas.setMatrix(this.mMatrix);
        getArrowDrawable(this.mTiltPosition, true).draw(canvas);
    }

    private void drawChair(Canvas canvas, float horizontalMove, float verticalMove) {
        float f;
        if (this.mControlMode == CONTROL_MODE_CHAIR && this.mSupportMove) {
            f = this.mCarBaseConfig.isSupportTiltPosReversed() ? 100 - this.mTiltPosition : this.mTiltPosition;
        } else {
            f = 0.0f;
        }
        this.mMatrix.setRotate((f * this.mCarBaseConfig.getSeatTiltAngleFactor()) - 9.0f, 36.0f, 260.0f);
        this.mMatrix.postTranslate(365.0f + horizontalMove, 107.0f + verticalMove);
        canvas.setMatrix(this.mMatrix);
        this.mDChairBack.draw(canvas);
        this.mMatrix.setTranslate(horizontalMove + 225.0f, verticalMove + 335.0f);
        canvas.setMatrix(this.mMatrix);
        this.mDChairControl.draw(canvas);
    }

    private Drawable getArrowDrawable(int position, boolean isUpFront) {
        if (isButtonEnable(position, isUpFront)) {
            return this.mMidDownEnable;
        }
        return this.mMidDownDisable;
    }

    @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView
    protected int resolveBtnWithMode(float x, float y) {
        int intrinsicWidth = this.mMidDownEnable.getIntrinsicWidth();
        int intrinsicHeight = this.mMidDownEnable.getIntrinsicHeight();
        float horizontalMove = getHorizontalMove();
        float verticalMove = getVerticalMove();
        int i = 0;
        if (this.mControlMode == CONTROL_MODE_CHAIR) {
            if (x <= 290.0f + horizontalMove || x >= intrinsicWidth + DIMEN_TILT_UP_BTN_LEFT + 0 + horizontalMove) {
                if (x > 10.0f && x < intrinsicWidth + 10 + 0 && y > verticalMove + 340.0f && y < intrinsicHeight + DIMEN_SEAT_FRONT_BTN_TOP + 0 + verticalMove) {
                    i = 1;
                } else if (x > (this.mDLineHor.getIntrinsicWidth() + 70) - 0 && x < this.mDLineHor.getIntrinsicWidth() + 70 + intrinsicWidth + 0 && y > 340.0f + verticalMove && y < intrinsicHeight + DIMEN_SEAT_FRONT_BTN_TOP + 0 + verticalMove) {
                    i = 2;
                } else if (x > 625.0f + horizontalMove && x < intrinsicWidth + DIMEN_TILT_DOWN_BTN_LEFT + 0 + horizontalMove && y > 295.0f + verticalMove && y < intrinsicHeight + DIMEN_TILT_DOWN_BTN_TOP + 0 + verticalMove) {
                    i = 6;
                }
            } else if (y <= 85.0f + verticalMove || y >= intrinsicHeight + 85 + 0 + verticalMove) {
                boolean z = this.isDriver;
                if (z && y > 198.0f && y < intrinsicHeight + DIMEN_SEAT_UP_BTN_TOP + 0) {
                    i = 3;
                } else if (z && y > 406.0f && y < intrinsicHeight + DIMEN_SEAT_DOWN_BTN_TOP + 0) {
                    i = 4;
                }
            } else {
                i = 5;
            }
        } else if (this.mControlMode == CONTROL_MODE_LEG) {
            if (x > 120.0f && x < intrinsicWidth + 120 + 0) {
                if (y > 200.0f && y < intrinsicHeight + 200 + 0) {
                    i = 7;
                } else if (y > 389.0f && y < intrinsicHeight + DIMEN_LEG_DOWN_BTN_TOP + 0) {
                    i = 8;
                }
            }
        } else if (this.mControlMode == CONTROL_MODE_LUMBAR) {
            LogUtils.d(this.TAG, "x " + x + ", y " + y);
            if (y <= 209.0f || y >= intrinsicHeight + 209 + 0) {
                if (x > 313.0f && x < intrinsicWidth + 313 + 0) {
                    if (y > 108.0f && y < intrinsicHeight + 108 + 0) {
                        i = 11;
                    } else if (y > 310.0f && y < intrinsicHeight + 310 + 0) {
                        i = 12;
                    }
                }
            } else if (x > 222.0f && x < intrinsicWidth + DIMEN_LUMBAR_FRONT_BTN_LEFT + 0) {
                i = 9;
            } else if (x > 350.0f && x < intrinsicWidth + DIMEN_LUMBAR_BACK_BTN_LEFT + 0) {
                i = 10;
            }
        }
        LogUtils.d(this.TAG, "id " + getId() + "x " + x + ", y " + y + ", mode " + this.mControlMode + ",button " + i);
        return i;
    }

    private float getHorizontalMove() {
        if (this.mControlMode == CONTROL_MODE_CHAIR && this.mSupportMove) {
            return (-this.mHorPosition) * 1.3f;
        }
        return -65.0f;
    }

    private float getVerticalMove() {
        if (this.mControlMode == CONTROL_MODE_CHAIR && this.mSupportMove) {
            return (-this.mVerPosition) * 0.8f;
        }
        return -40.0f;
    }

    @Override // com.xiaopeng.carcontrol.view.BaseSeatControlView
    protected void onThemeChanged() {
        initView(getContext());
        invalidate();
    }

    public void setDriver(boolean isDriver) {
        this.isDriver = isDriver;
    }
}
