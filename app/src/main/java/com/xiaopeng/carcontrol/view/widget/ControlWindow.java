package com.xiaopeng.carcontrol.view.widget;

/* loaded from: classes2.dex */
public class ControlWindow {
    private static final int WINDOW_MAX_POSITION = 100;
    private static final int WINDOW_MIN_POSITION = 0;
    private float mControlX;
    private float mControlY;
    private boolean mIsControl;
    private float mMaxMoveX;
    private float mMaxMoveY;
    private float mMoveK;
    private float mRealMoveX;
    private float mRealMoveY;
    private int mWindowPos;

    int verifyPosition(int pos) {
        if (pos > 100) {
            return 100;
        }
        if (pos < 0) {
            return 0;
        }
        return pos;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ControlWindow(float maxMoveY, float maxMoveX) {
        this.mMaxMoveY = maxMoveY;
        this.mMaxMoveX = maxMoveX;
        this.mMoveK = maxMoveX / maxMoveY;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setWindowPos(int pos) {
        this.mWindowPos = verifyPosition(pos);
        this.mRealMoveY = getMoveY();
        this.mRealMoveX = getMoveX();
        if (this.mIsControl) {
            return;
        }
        resetControlYX();
    }

    int getWindowPos() {
        return this.mWindowPos;
    }

    private float getMoveY() {
        return ((100 - this.mWindowPos) * this.mMaxMoveY) / 100.0f;
    }

    private float getMoveX() {
        return ((100 - this.mWindowPos) * this.mMaxMoveX) / 100.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getRealMoveY() {
        return this.mRealMoveY;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getRealMoveX() {
        return this.mRealMoveX;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getControlY() {
        return this.mControlY;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getControlX() {
        return this.mControlX;
    }

    private void resetControlYX() {
        this.mControlY = this.mRealMoveY;
        this.mControlX = this.mRealMoveX;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void moveWindow(float dy) {
        this.mIsControl = true;
        float f = this.mControlY + dy;
        this.mControlY = f;
        float f2 = this.mMaxMoveY;
        if (f > f2) {
            this.mControlY = f2;
        } else if (f < 0.0f) {
            this.mControlY = 0.0f;
        }
        float f3 = this.mControlX + (dy * this.mMoveK);
        this.mControlX = f3;
        float f4 = this.mMaxMoveX;
        if (f3 < f4) {
            this.mControlX = f4;
        } else if (f3 > 0.0f) {
            this.mControlX = 0.0f;
        }
    }

    void moveWindow(int pos) {
        moveWindow((pos / 100.0f) * this.mMaxMoveY);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getControlPos() {
        return 100 - ((int) ((this.mControlY * 100.0f) / this.mMaxMoveY));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void controlFinish() {
        this.mIsControl = false;
        resetControlYX();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isControl() {
        return this.mIsControl;
    }
}
