package com.xiaopeng.xui.view.animation;

import android.graphics.PointF;
import android.view.animation.Interpolator;

/* loaded from: classes2.dex */
public class XBesselCurve3Interpolator implements Interpolator {
    private static final float STEP_SIZE = 2.4414062E-4f;
    private int mLastI;
    private final PointF point1;
    private final PointF point2;

    public static double cubicEquation(double d, double d2, double d3) {
        double d4 = 1.0d - d;
        double d5 = d * d;
        return (d4 * d4 * 3.0d * d * d2) + (d4 * 3.0d * d5 * d3) + (d5 * d);
    }

    public XBesselCurve3Interpolator() {
        this(0.2f, 0.0f, 0.2f, 1.0f);
    }

    public XBesselCurve3Interpolator(float f, float f2, float f3, float f4) {
        this.mLastI = 0;
        PointF pointF = new PointF();
        this.point1 = pointF;
        PointF pointF2 = new PointF();
        this.point2 = pointF2;
        pointF.x = f;
        pointF.y = f2;
        pointF2.x = f3;
        pointF2.y = f4;
    }

    public void set(float f, float f2, float f3, float f4) {
        this.point1.x = f;
        this.point1.y = f2;
        this.point2.x = f3;
        this.point2.y = f4;
        this.mLastI = 0;
    }

    public void reset() {
        this.mLastI = 0;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f == 0.0f) {
            this.mLastI = 0;
        }
        int i = this.mLastI;
        float f2 = f;
        while (true) {
            if (i >= 4096) {
                break;
            }
            f2 = i * STEP_SIZE;
            if (cubicEquation(f2, this.point1.x, this.point2.x) >= f) {
                this.mLastI = i;
                break;
            }
            i++;
        }
        double cubicEquation = cubicEquation(f2, this.point1.y, this.point2.y);
        if (f == 1.0f) {
            this.mLastI = 0;
        }
        return (float) cubicEquation;
    }
}
