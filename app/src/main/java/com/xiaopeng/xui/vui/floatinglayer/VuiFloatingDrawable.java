package com.xiaopeng.xui.vui.floatinglayer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.xiaopeng.xui.utils.XLogUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes2.dex */
class VuiFloatingDrawable extends Drawable implements Animatable2 {
    private static final int PADDING = 0;
    private ValueAnimator mAlphaAnimatorIn;
    private ValueAnimator mAlphaAnimatorOut;
    private ArrayList<Animatable2.AnimationCallback> mAnimationCallbacks;
    private AnimatorSet mAnimatorSet;
    private ArrayList<Animator> mAnimators;
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private Paint mPaint;
    private PaintFlagsDrawFilter mPaintFlagsDrawFilter;
    private boolean mRunning;
    private ValueAnimator[] mScaleAnimator;
    private boolean mStarting;
    private ValueAnimator.AnimatorUpdateListener mUpdateListenerAlpha = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.xui.vui.floatinglayer.VuiFloatingDrawable.1
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            VuiFloatingDrawable.this.mPaint.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
            VuiFloatingDrawable.this.invalidateSelf();
        }
    };
    private ValueAnimator.AnimatorUpdateListener mUpdateListenerScale = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.xui.vui.floatinglayer.VuiFloatingDrawable.2
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            VuiFloatingDrawable.this.mMatrix.setScale(floatValue, floatValue, VuiFloatingDrawable.this.mBitmap.getWidth() / 2, VuiFloatingDrawable.this.mBitmap.getHeight() / 2);
            VuiFloatingDrawable.this.invalidateSelf();
        }
    };
    private Animator.AnimatorListener mEndListener = new Animator.AnimatorListener() { // from class: com.xiaopeng.xui.vui.floatinglayer.VuiFloatingDrawable.3
        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            VuiFloatingDrawable.this.log("onAnimationEnd ");
            VuiFloatingDrawable.this.stop();
        }
    };

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    private VuiFloatingDrawable() {
    }

    public VuiFloatingDrawable(Bitmap bitmap) {
        this.mBitmap = bitmap;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mMatrix = new Matrix();
        int i = 0;
        this.mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
        ValueAnimator ofInt = ValueAnimator.ofInt(0, 255);
        this.mAlphaAnimatorIn = ofInt;
        ofInt.setDuration(500L);
        this.mAlphaAnimatorIn.setInterpolator(new DecelerateInterpolator());
        this.mScaleAnimator = new ValueAnimator[4];
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
        while (true) {
            ValueAnimator[] valueAnimatorArr = this.mScaleAnimator;
            if (i < valueAnimatorArr.length) {
                if (i % 2 == 0) {
                    valueAnimatorArr[i] = ValueAnimator.ofFloat(1.0f, 1.05f);
                } else {
                    valueAnimatorArr[i] = ValueAnimator.ofFloat(1.05f, 1.0f);
                }
                this.mScaleAnimator[i].setDuration(1000L);
                this.mScaleAnimator[i].setInterpolator(decelerateInterpolator);
                i++;
            } else {
                ValueAnimator ofInt2 = ValueAnimator.ofInt(255, 0);
                this.mAlphaAnimatorOut = ofInt2;
                ofInt2.setDuration(250L);
                this.mAlphaAnimatorOut.setInterpolator(new AccelerateInterpolator());
                this.mAnimatorSet = new AnimatorSet();
                ArrayList<Animator> arrayList = new ArrayList<>();
                this.mAnimators = arrayList;
                arrayList.add(this.mAlphaAnimatorIn);
                this.mAnimators.addAll(Arrays.asList(this.mScaleAnimator));
                this.mAnimators.add(this.mAlphaAnimatorOut);
                this.mAnimatorSet.playSequentially(this.mAnimators);
                return;
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        if (!z) {
            log("setVisible false ");
            stop();
        }
        return super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            return bitmap.getWidth() + 0;
        }
        return super.getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            return bitmap.getHeight() + 0;
        }
        return super.getIntrinsicHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (isVisible()) {
            if (this.mStarting) {
                this.mStarting = false;
                this.mRunning = true;
                postOnAnimationStart();
            }
            if (this.mRunning) {
                canvas.setDrawFilter(this.mPaintFlagsDrawFilter);
                canvas.translate(0.0f, 0.0f);
                canvas.drawBitmap(this.mBitmap, this.mMatrix, this.mPaint);
            }
        }
    }

    @Override // android.graphics.drawable.Animatable2
    public void registerAnimationCallback(Animatable2.AnimationCallback animationCallback) {
        if (this.mAnimationCallbacks == null) {
            this.mAnimationCallbacks = new ArrayList<>();
        }
        if (this.mAnimationCallbacks.contains(animationCallback)) {
            return;
        }
        this.mAnimationCallbacks.add(animationCallback);
    }

    @Override // android.graphics.drawable.Animatable2
    public boolean unregisterAnimationCallback(Animatable2.AnimationCallback animationCallback) {
        ArrayList<Animatable2.AnimationCallback> arrayList = this.mAnimationCallbacks;
        if (arrayList == null || !arrayList.remove(animationCallback)) {
            return false;
        }
        if (this.mAnimationCallbacks.isEmpty()) {
            clearAnimationCallbacks();
            return true;
        }
        return true;
    }

    @Override // android.graphics.drawable.Animatable2
    public void clearAnimationCallbacks() {
        if (this.mAnimationCallbacks != null) {
            this.mAnimationCallbacks = null;
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        if (this.mRunning) {
            stop();
        }
        this.mStarting = true;
        this.mPaint.setAlpha(0);
        for (ValueAnimator valueAnimator : this.mScaleAnimator) {
            valueAnimator.addUpdateListener(this.mUpdateListenerScale);
        }
        this.mAlphaAnimatorIn.addUpdateListener(this.mUpdateListenerAlpha);
        this.mAlphaAnimatorOut.addUpdateListener(this.mUpdateListenerAlpha);
        this.mAlphaAnimatorOut.addListener(this.mEndListener);
        this.mAnimatorSet.start();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.mAnimatorSet.cancel();
        ArrayList<Animator> arrayList = this.mAnimators;
        if (arrayList != null) {
            Iterator<Animator> it = arrayList.iterator();
            while (it.hasNext()) {
                Animator next = it.next();
                if (next instanceof ValueAnimator) {
                    ((ValueAnimator) next).removeAllUpdateListeners();
                }
                next.removeAllListeners();
            }
        }
        this.mMatrix.reset();
        if (this.mRunning) {
            this.mRunning = false;
            postOnAnimationEnd();
        }
    }

    private void postOnAnimationStart() {
        ArrayList<Animatable2.AnimationCallback> arrayList = this.mAnimationCallbacks;
        if (arrayList == null) {
            return;
        }
        Iterator<Animatable2.AnimationCallback> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().onAnimationStart(this);
        }
    }

    private void postOnAnimationEnd() {
        log("postOnAnimationEnd ");
        ArrayList<Animatable2.AnimationCallback> arrayList = this.mAnimationCallbacks;
        if (arrayList == null) {
            return;
        }
        Iterator<Animatable2.AnimationCallback> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().onAnimationEnd(this);
        }
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mRunning;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(String str) {
        XLogUtils.d("VuiFloatingDrawable", str);
    }
}
