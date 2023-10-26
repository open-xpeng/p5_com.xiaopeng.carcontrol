package com.xiaopeng.carcontrol.view.control.hvac.vent;

import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class RotateHelper {
    private static final String TAG = "RotateHelper";
    private float mHorizontalAngle;
    private int mHorizontalPosition;
    private float mVerticalAngle;
    private int mVerticalPosition;
    private float maxAngleHorizontal;
    private float maxAngleVertical;
    private float minAngleHorizontal;
    private float minAngleVertical;
    private float stepAngleHorizontal;
    private float stepAngleVertical;
    private float totalAngleHorizontal;
    private float totalAngleVertical;

    public RotateHelper(float minAngleVertical, float maxAngleVertical, float minAngleHorizontal, float maxAngleHorizontal) {
        this.minAngleVertical = minAngleVertical;
        this.maxAngleVertical = maxAngleVertical;
        this.minAngleHorizontal = minAngleHorizontal;
        this.maxAngleHorizontal = maxAngleHorizontal;
        float f = maxAngleVertical - minAngleVertical;
        this.totalAngleVertical = f;
        this.stepAngleVertical = f / 5.0f;
        float f2 = maxAngleHorizontal - minAngleHorizontal;
        this.totalAngleHorizontal = f2;
        this.stepAngleHorizontal = f2 / 5.0f;
    }

    public float changeVertical(float angle) {
        LogUtils.d(TAG, "mVerticalAngle " + this.mVerticalAngle + ",angle " + angle);
        float f = this.mVerticalAngle;
        float f2 = angle + f;
        this.mVerticalAngle = f2;
        float f3 = this.maxAngleVertical;
        if (f2 > f3) {
            this.mVerticalAngle = f3;
        } else {
            float f4 = this.minAngleVertical;
            if (f2 < f4) {
                this.mVerticalAngle = f4;
            }
        }
        calculateVerticalPosition();
        return this.mVerticalAngle - f;
    }

    public float changeHorizontal(float angle) {
        float f = this.mHorizontalAngle;
        float f2 = angle + f;
        this.mHorizontalAngle = f2;
        float f3 = this.maxAngleHorizontal;
        if (f2 > f3) {
            this.mHorizontalAngle = f3;
        } else {
            float f4 = this.minAngleHorizontal;
            if (f2 < f4) {
                this.mHorizontalAngle = f4;
            }
        }
        calculateHorizontalPosition();
        return this.mHorizontalAngle - f;
    }

    private void calculateVerticalPosition() {
        this.mVerticalPosition = ((int) (((this.maxAngleVertical - this.mVerticalAngle) - 0.01f) / this.stepAngleVertical)) + 1;
        LogUtils.d(TAG, "mVerticalPosition " + this.mVerticalPosition);
    }

    private void calculateHorizontalPosition() {
        this.mHorizontalPosition = ((int) (((this.mHorizontalAngle - this.minAngleHorizontal) - 0.01f) / this.stepAngleHorizontal)) + 1;
        LogUtils.d(TAG, "mHorizontalPosition " + this.mHorizontalPosition);
    }

    public float getAngleVertical() {
        return this.mVerticalAngle;
    }

    public float getAngleHorizontal() {
        return this.mHorizontalAngle;
    }

    public int[] getPosition() {
        return new int[]{this.mVerticalPosition, this.mHorizontalPosition};
    }

    public int getVerticalPosition() {
        return this.mVerticalPosition;
    }

    public int getHorizontalPosition() {
        return this.mHorizontalPosition;
    }

    public float setVerticalPosition(int position) {
        float f = this.totalAngleVertical;
        float f2 = this.stepAngleVertical;
        float f3 = (((f - (position * f2)) + (f2 / 2.0f)) + this.minAngleVertical) - this.mVerticalAngle;
        LogUtils.d(TAG, "offset " + f3);
        return f3;
    }

    public float setHorizontalPosition(int position) {
        return ((((position - 1) * this.stepAngleHorizontal) + (this.stepAngleVertical / 2.0f)) + this.minAngleHorizontal) - this.mHorizontalAngle;
    }

    public float[] getLocationPercent() {
        return new float[]{(this.mVerticalAngle - this.minAngleVertical) / this.totalAngleVertical, (this.mHorizontalAngle - this.minAngleHorizontal) / this.totalAngleHorizontal};
    }
}
