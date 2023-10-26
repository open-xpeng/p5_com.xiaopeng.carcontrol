package com.xiaopeng.xui.view.animation;

import android.view.animation.Interpolator;

/* loaded from: classes2.dex */
public class XSpringInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return (float) ((Math.pow(2.0d, (-11.0f) * f) * Math.sin(((f - 0.175f) * 6.283185307179586d) / 0.7f)) + 1.0d);
    }
}
