package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.UIUtils;
import com.xiaopeng.speech.common.SpeechConstant;
import com.xiaopeng.xui.theme.XThemeManager;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes2.dex */
public class WindowControlView extends ConstraintLayout {
    private static final int BTN_FONT_PRESS_MSG_WHAT = 5;
    private static final int BTN_REAR_PRESS_MSG_WHAT = 6;
    private static final int CONTROL_LEFT_FRONT_MSG_WHAT = 1;
    private static final int CONTROL_LEFT_REAR_MSG_WHAT = 2;
    private static final int CONTROL_POS_MSG_TIME = 1000;
    private static final int CONTROL_PROTECT_TIME = 1000;
    private static final int CONTROL_RIGHT_FRONT_MSG_WHAT = 3;
    private static final int CONTROL_RIGHT_REAR_MSG_WHAT = 4;
    public static final int CONTROL_SIDE_LEFT = 1;
    public static final int CONTROL_SIDE_RIGHT = 2;
    private static final String TAG = "WindowControlView";
    public static final int WIN_LEFT_FRONT_INDEX = 0;
    public static final int WIN_LEFT_REAR_INDEX = 1;
    public static final int WIN_RIGHT_FRONT_INDEX = 2;
    public static final int WIN_RIGHT_REAR_INDEX = 3;
    private int mBackgroundTop;
    private Drawable mBgDrawable;
    private Handler mControlHandle;
    private int mControlSide;
    private int mControlWinIndex;
    private float mEventY;
    private Bitmap mFrontLeftLineBitmap;
    private Bitmap mFrontLeftMaskBitmap;
    private float mFrontLeftStart;
    private float mFrontLeftTop;
    private Bitmap mFrontLeftWinBitmap;
    private Bitmap mFrontRightLineBitmap;
    private Bitmap mFrontRightMaskBitmap;
    private float mFrontRightStart;
    private float mFrontRightTop;
    private Bitmap mFrontRightWinBitmap;
    private Bitmap mGesture;
    private int mLastControlIndex;
    private long mLastControlTime;
    private ControlWindow mLeftFrontWin;
    private ControlWindow mLeftRearWin;
    private OnWinControlListener mOnWinControlListener;
    private Paint mPaint;
    private Bitmap mRearLeftLineBitmap;
    private Bitmap mRearLeftMaskBitmap;
    private float mRearLeftStart;
    private float mRearLeftTop;
    private Bitmap mRearLeftWinBitmap;
    private Bitmap mRearRightLineBitmap;
    private Bitmap mRearRightMaskBitmap;
    private float mRearRightStart;
    private float mRearRightTop;
    private Bitmap mRearRightWinBitmap;
    private ControlWindow mRightFrontWin;
    private ControlWindow mRightRearWin;
    private Drawable mSeatDrawable;
    private boolean mShowGesture;
    private Xfermode mXfermode;

    /* loaded from: classes2.dex */
    public interface OnWinControlListener {
        void onFrontLeftChanged(int pos);

        void onFrontRightChanged(int pos);

        boolean onInterceptControl();

        void onRearLeftChanged(int pos);

        void onRearRightChanged(int pos);
    }

    public WindowControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mControlSide = 1;
        this.mControlWinIndex = -1;
        this.mShowGesture = false;
        init();
    }

    public WindowControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mControlSide = 1;
        this.mControlWinIndex = -1;
        this.mShowGesture = false;
        init();
    }

    private void init() {
        this.mPaint = new Paint(1);
        this.mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        this.mLeftFrontWin = new ControlWindow(UIUtils.dp2px(getContext(), 133), -UIUtils.dp2px(getContext(), 37));
        this.mLeftRearWin = new ControlWindow(UIUtils.dp2px(getContext(), 127), -UIUtils.dp2px(getContext(), 15));
        this.mRightFrontWin = new ControlWindow(UIUtils.dp2px(getContext(), TarConstants.CHKSUM_OFFSET), -UIUtils.dp2px(getContext(), 38));
        this.mRightRearWin = new ControlWindow(UIUtils.dp2px(getContext(), SpeechConstant.SoundLocation.PASSENGER_END_ANGLE), -UIUtils.dp2px(getContext(), 23));
        initBitmap();
    }

    private void initDrawable() {
        if (this.mControlSide == 2) {
            Drawable drawable = getContext().getDrawable(R.drawable.img_window_car_inside);
            this.mBgDrawable = drawable;
            this.mBackgroundTop = 0;
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth() + 0, this.mBackgroundTop + this.mBgDrawable.getIntrinsicHeight());
            }
            Drawable drawable2 = getContext().getDrawable(R.drawable.img_window_car_seat);
            this.mSeatDrawable = drawable2;
            if (drawable2 != null) {
                int dp2px = UIUtils.dp2px(getContext(), 107) + 0;
                int dp2px2 = this.mBackgroundTop + UIUtils.dp2px(getContext(), 47);
                Drawable drawable3 = this.mSeatDrawable;
                drawable3.setBounds(dp2px, dp2px2, drawable3.getIntrinsicWidth() + dp2px, this.mSeatDrawable.getIntrinsicHeight() + dp2px2);
            }
            this.mFrontRightStart = UIUtils.dp2px(getContext(), 116) + 0;
            this.mFrontRightTop = this.mBackgroundTop + UIUtils.dp2px(getContext(), 54);
            this.mRearRightStart = 0 + UIUtils.dp2px(getContext(), 507);
            this.mRearRightTop = this.mBackgroundTop + UIUtils.dp2px(getContext(), 53);
            return;
        }
        Drawable drawable4 = getContext().getDrawable(R.drawable.img_window_car_outside);
        this.mBgDrawable = drawable4;
        this.mBackgroundTop = 0;
        if (drawable4 != null) {
            drawable4.setBounds(0, 0, drawable4.getIntrinsicWidth() + 0, this.mBackgroundTop + this.mBgDrawable.getIntrinsicHeight());
        }
        this.mSeatDrawable = null;
        this.mFrontLeftStart = UIUtils.dp2px(getContext(), 195) + 0;
        this.mFrontLeftTop = this.mBackgroundTop + UIUtils.dp2px(getContext(), 60);
        this.mRearLeftStart = 0 + UIUtils.dp2px(getContext(), 538);
        this.mRearLeftTop = this.mBackgroundTop + UIUtils.dp2px(getContext(), 60);
    }

    private void initBitmap() {
        if (this.mControlSide == 1) {
            this.mFrontLeftMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_mask_front_left);
            this.mRearLeftMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_mask_rear_left);
            this.mFrontLeftWinBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_preview_front_left);
            this.mRearLeftWinBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_preview_rear_left);
        } else {
            this.mFrontRightMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_mask_front_right);
            this.mRearRightMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_mask_rear_right);
            this.mFrontRightWinBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_preview_front_right);
            this.mRearRightWinBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_preview_rear_right);
        }
        this.mGesture = BitmapFactory.decodeResource(getResources(), R.drawable.window_gesture);
    }

    public void showGesture() {
        this.mShowGesture = true;
    }

    private void dismissGesture() {
        if (this.mShowGesture) {
            this.mShowGesture = false;
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap;
        Bitmap bitmap2;
        Bitmap bitmap3;
        Bitmap bitmap4;
        super.onDraw(canvas);
        Drawable drawable = this.mBgDrawable;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), this.mPaint);
        if (this.mControlSide == 1 && (bitmap3 = this.mFrontLeftMaskBitmap) != null && !bitmap3.isRecycled() && (bitmap4 = this.mRearLeftMaskBitmap) != null && !bitmap4.isRecycled()) {
            canvas.drawBitmap(this.mFrontLeftMaskBitmap, this.mFrontLeftStart, this.mFrontLeftTop, this.mPaint);
            canvas.drawBitmap(this.mRearLeftMaskBitmap, this.mRearLeftStart, this.mRearLeftTop, this.mPaint);
            this.mPaint.setXfermode(this.mXfermode);
            canvas.drawBitmap(this.mFrontLeftWinBitmap, this.mFrontLeftStart + this.mLeftFrontWin.getRealMoveX(), this.mFrontLeftTop + this.mLeftFrontWin.getRealMoveY(), this.mPaint);
            canvas.drawBitmap(this.mRearLeftWinBitmap, this.mRearLeftStart + this.mLeftRearWin.getRealMoveX(), this.mRearLeftTop + this.mLeftRearWin.getRealMoveY(), this.mPaint);
            if (this.mLeftFrontWin.isControl()) {
                canvas.drawBitmap(this.mFrontLeftLineBitmap, this.mFrontLeftStart + this.mLeftFrontWin.getControlX(), this.mFrontLeftTop + this.mLeftFrontWin.getControlY(), this.mPaint);
            }
            if (this.mLeftRearWin.isControl()) {
                canvas.drawBitmap(this.mRearLeftLineBitmap, this.mRearLeftStart + this.mLeftRearWin.getControlX(), this.mRearLeftTop + this.mLeftRearWin.getControlY(), this.mPaint);
            }
        } else {
            Bitmap bitmap5 = this.mFrontRightMaskBitmap;
            if (bitmap5 != null && !bitmap5.isRecycled() && (bitmap = this.mRearRightMaskBitmap) != null && !bitmap.isRecycled()) {
                canvas.drawBitmap(this.mFrontRightMaskBitmap, this.mFrontRightStart, this.mFrontRightTop, this.mPaint);
                canvas.drawBitmap(this.mRearRightMaskBitmap, this.mRearRightStart, this.mRearRightTop, this.mPaint);
                this.mPaint.setXfermode(this.mXfermode);
                canvas.drawBitmap(this.mFrontRightWinBitmap, this.mFrontRightStart + this.mRightFrontWin.getRealMoveX(), this.mFrontRightTop + this.mRightFrontWin.getRealMoveY(), this.mPaint);
                canvas.drawBitmap(this.mRearRightWinBitmap, this.mRearRightStart + this.mRightRearWin.getRealMoveX(), this.mRearRightTop + this.mRightRearWin.getRealMoveY(), this.mPaint);
                if (this.mRightFrontWin.isControl()) {
                    canvas.drawBitmap(this.mFrontRightLineBitmap, this.mFrontRightStart + this.mRightFrontWin.getControlX(), this.mFrontRightTop + this.mRightFrontWin.getControlY(), this.mPaint);
                }
                if (this.mRightRearWin.isControl()) {
                    canvas.drawBitmap(this.mRearRightLineBitmap, this.mRearRightStart + this.mRightRearWin.getControlX(), this.mRearRightTop + this.mRightRearWin.getControlY(), this.mPaint);
                }
                Drawable drawable2 = this.mSeatDrawable;
                if (drawable2 != null) {
                    drawable2.draw(canvas);
                }
            }
        }
        this.mPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
        if (this.mShowGesture && (bitmap2 = this.mGesture) != null && !bitmap2.isRecycled()) {
            Bitmap bitmap6 = this.mGesture;
            int i = this.mControlSide;
            canvas.drawBitmap(bitmap6, i == 1 ? 355.0f : 300.0f, i == 1 ? 100.0f : 105.0f, this.mPaint);
        }
        recycleBitmap();
    }

    private void recycleBitmap() {
        if (this.mControlSide == 2) {
            recycleBitmap(this.mFrontLeftMaskBitmap);
            recycleBitmap(this.mRearLeftMaskBitmap);
            recycleBitmap(this.mFrontLeftWinBitmap);
            recycleBitmap(this.mRearLeftWinBitmap);
            return;
        }
        recycleBitmap(this.mFrontRightMaskBitmap);
        recycleBitmap(this.mRearRightMaskBitmap);
        recycleBitmap(this.mFrontRightWinBitmap);
        recycleBitmap(this.mRearRightWinBitmap);
    }

    private void recycleBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000e, code lost:
        if (r0 != 3) goto L8;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r10) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.widget.WindowControlView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void onWinControlChanged() {
        int controlPos;
        int i = this.mControlWinIndex;
        if (i == 0) {
            controlPos = this.mLeftFrontWin.getControlPos();
            OnWinControlListener onWinControlListener = this.mOnWinControlListener;
            if (onWinControlListener != null && !onWinControlListener.onInterceptControl()) {
                this.mOnWinControlListener.onFrontLeftChanged(controlPos);
            }
            sendControlMessage(1);
        } else if (i == 1) {
            controlPos = this.mLeftRearWin.getControlPos();
            OnWinControlListener onWinControlListener2 = this.mOnWinControlListener;
            if (onWinControlListener2 != null && !onWinControlListener2.onInterceptControl()) {
                this.mOnWinControlListener.onRearLeftChanged(controlPos);
            }
            sendControlMessage(2);
        } else if (i == 2) {
            controlPos = this.mRightFrontWin.getControlPos();
            OnWinControlListener onWinControlListener3 = this.mOnWinControlListener;
            if (onWinControlListener3 != null && !onWinControlListener3.onInterceptControl()) {
                this.mOnWinControlListener.onFrontRightChanged(controlPos);
            }
            sendControlMessage(3);
        } else if (i != 3) {
            controlPos = -1;
        } else {
            controlPos = this.mRightRearWin.getControlPos();
            OnWinControlListener onWinControlListener4 = this.mOnWinControlListener;
            if (onWinControlListener4 != null && !onWinControlListener4.onInterceptControl()) {
                this.mOnWinControlListener.onRearRightChanged(controlPos);
            }
            sendControlMessage(4);
        }
        LogUtils.d(TAG, "onWinControlChanged mControlWinIndex:" + this.mControlWinIndex + ",pos:" + controlPos);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endControl(int controlIndex) {
        if (controlIndex != 0) {
            if (controlIndex != 1) {
                if (controlIndex == 2) {
                    if (this.mControlWinIndex != 2) {
                        this.mRightFrontWin.controlFinish();
                        Bitmap bitmap = this.mFrontRightLineBitmap;
                        if (bitmap != null && !bitmap.isRecycled()) {
                            this.mFrontRightLineBitmap.recycle();
                        }
                    }
                } else if (controlIndex == 3 && this.mControlWinIndex != 3) {
                    this.mRightRearWin.controlFinish();
                    Bitmap bitmap2 = this.mRearRightLineBitmap;
                    if (bitmap2 != null && !bitmap2.isRecycled()) {
                        this.mRearRightLineBitmap.recycle();
                    }
                }
            } else if (this.mControlWinIndex != 1) {
                this.mLeftRearWin.controlFinish();
                Bitmap bitmap3 = this.mRearLeftLineBitmap;
                if (bitmap3 != null && !bitmap3.isRecycled()) {
                    this.mRearLeftLineBitmap.recycle();
                }
            }
        } else if (this.mControlWinIndex != 0) {
            this.mLeftFrontWin.controlFinish();
            Bitmap bitmap4 = this.mFrontLeftLineBitmap;
            if (bitmap4 != null && !bitmap4.isRecycled()) {
                this.mFrontLeftLineBitmap.recycle();
            }
        }
        postInvalidate();
    }

    private void drawTouchLine() {
        Bitmap bitmap;
        int i = this.mControlWinIndex;
        if (i == 0) {
            Bitmap bitmap2 = this.mFrontLeftLineBitmap;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                this.mFrontLeftLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_control_front_left);
            }
        } else if (i == 1) {
            Bitmap bitmap3 = this.mRearLeftLineBitmap;
            if (bitmap3 == null || bitmap3.isRecycled()) {
                this.mRearLeftLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_control_rear_left);
            }
        } else if (i == 2) {
            Bitmap bitmap4 = this.mFrontRightLineBitmap;
            if (bitmap4 == null || bitmap4.isRecycled()) {
                this.mFrontRightLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_control_front_right);
            }
        } else if (i == 3 && ((bitmap = this.mRearRightLineBitmap) == null || bitmap.isRecycled())) {
            this.mRearRightLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_control_rear_right);
        }
        invalidate();
    }

    private void calculateMovePx(float eventY) {
        float f = eventY - this.mEventY;
        this.mEventY = eventY;
        int i = this.mControlWinIndex;
        if (i == 0) {
            this.mLeftFrontWin.moveWindow(f);
        } else if (i == 1) {
            this.mLeftRearWin.moveWindow(f);
        } else if (i == 2) {
            this.mRightFrontWin.moveWindow(f);
        } else if (i == 3) {
            this.mRightRearWin.moveWindow(f);
        }
        invalidate();
    }

    private void analysisControlIndex(float touchX, float touchY) {
        if (this.mControlSide == 1) {
            Bitmap bitmap = this.mFrontLeftWinBitmap;
            if (bitmap != null && !bitmap.isRecycled() && touchX < this.mFrontLeftStart + this.mFrontLeftWinBitmap.getWidth()) {
                this.mControlWinIndex = 0;
            } else if (touchX > this.mRearLeftStart) {
                this.mControlWinIndex = 1;
            }
        } else {
            Bitmap bitmap2 = this.mFrontRightWinBitmap;
            if (bitmap2 != null && !bitmap2.isRecycled() && touchX < this.mFrontRightStart + this.mFrontRightWinBitmap.getWidth()) {
                this.mControlWinIndex = 2;
            } else if (touchX > this.mRearRightStart) {
                this.mControlWinIndex = 3;
            }
        }
        LogUtils.d(TAG, "analysisControlIndex:" + this.mControlWinIndex);
    }

    public void setLeftFrontWinPos(int pos) {
        this.mLeftFrontWin.setWindowPos(pos);
        postInvalidate();
        if (this.mLeftFrontWin.isControl()) {
            sendControlMessage(1);
        }
    }

    public void setLeftRearWinPos(int pos) {
        this.mLeftRearWin.setWindowPos(pos);
        postInvalidate();
        if (this.mLeftRearWin.isControl()) {
            sendControlMessage(2);
        }
    }

    public void setRightFrontWinPos(int pos) {
        this.mRightFrontWin.setWindowPos(pos);
        postInvalidate();
        if (this.mRightFrontWin.isControl()) {
            sendControlMessage(3);
        }
    }

    public void setRightRearWinPos(int pos) {
        this.mRightRearWin.setWindowPos(pos);
        postInvalidate();
        if (this.mRightRearWin.isControl()) {
            sendControlMessage(4);
        }
    }

    public void setControlSide(int side) {
        if (this.mControlSide != side) {
            this.mControlSide = side;
            if (getMeasuredWidth() <= 0 || getMeasuredHeight() <= 0) {
                return;
            }
            initDrawable();
            initBitmap();
            invalidate();
        }
    }

    private void initHandler() {
        if (this.mControlHandle == null) {
            this.mControlHandle = new Handler() { // from class: com.xiaopeng.carcontrol.view.widget.WindowControlView.1
                @Override // android.os.Handler
                public void handleMessage(Message msg) {
                    int i = msg.what;
                    if (i == 1) {
                        WindowControlView.this.endControl(0);
                    } else if (i == 2) {
                        WindowControlView.this.endControl(1);
                    } else if (i == 3) {
                        WindowControlView.this.endControl(2);
                    } else if (i != 4) {
                    } else {
                        WindowControlView.this.endControl(3);
                    }
                }
            };
        }
    }

    private void sendControlMessage(int what) {
        Handler handler = this.mControlHandle;
        if (handler != null) {
            handler.removeMessages(what);
            Message obtain = Message.obtain();
            obtain.what = what;
            this.mControlHandle.sendMessageDelayed(obtain, 1000L);
        }
    }

    public int getControlSide() {
        return this.mControlSide;
    }

    public void setWinControlListener(OnWinControlListener onWinControlListener) {
        this.mOnWinControlListener = onWinControlListener;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (XThemeManager.isThemeChanged(newConfig)) {
            themeChange();
            Bitmap bitmap = this.mFrontLeftLineBitmap;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.mFrontLeftLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_control_front_left);
            }
            Bitmap bitmap2 = this.mFrontRightLineBitmap;
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                this.mFrontRightLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_control_front_right);
            }
            Bitmap bitmap3 = this.mRearLeftLineBitmap;
            if (bitmap3 != null && !bitmap3.isRecycled()) {
                this.mRearLeftLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_control_rear_left);
            }
            Bitmap bitmap4 = this.mRearRightLineBitmap;
            if (bitmap4 != null && !bitmap4.isRecycled()) {
                this.mRearRightLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_window_control_rear_right);
            }
            invalidate();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        themeChange();
        initHandler();
    }

    private void themeChange() {
        initDrawable();
        initBitmap();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Handler handler = this.mControlHandle;
        if (handler != null) {
            handler.removeMessages(1);
            this.mControlHandle.removeMessages(2);
            this.mControlHandle.removeMessages(3);
            this.mControlHandle.removeMessages(4);
            this.mControlHandle.removeMessages(5);
            this.mControlHandle.removeMessages(6);
            this.mControlHandle = null;
        }
        ControlWindow controlWindow = this.mLeftFrontWin;
        if (controlWindow != null) {
            controlWindow.controlFinish();
        }
        ControlWindow controlWindow2 = this.mLeftRearWin;
        if (controlWindow2 != null) {
            controlWindow2.controlFinish();
        }
        ControlWindow controlWindow3 = this.mRightFrontWin;
        if (controlWindow3 != null) {
            controlWindow3.controlFinish();
        }
        ControlWindow controlWindow4 = this.mRightRearWin;
        if (controlWindow4 != null) {
            controlWindow4.controlFinish();
        }
    }
}
