package com.xiaopeng.lib.utils.view.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import androidx.constraintlayout.motion.widget.Key;
import com.xiaopeng.lib.utils.view.UIUtils;

/* loaded from: classes2.dex */
public class BaseAnimation {
    private static final String ANIM_ALPHA = "alpha";
    protected int mDelay = 100;
    protected int mDuration = 200;

    /* loaded from: classes2.dex */
    public enum AnimationType {
        ALPHA,
        ROTATE,
        ROTATE_CENTER,
        HORIZON_LEFT,
        HORIZON_RIGHT,
        HORIZON_CROSS,
        SCALE,
        FLIP_HORIZON,
        FLIP_VERTICAL
    }

    /* renamed from: com.xiaopeng.lib.utils.view.anim.BaseAnimation$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType;

        static {
            int[] iArr = new int[AnimationType.values().length];
            $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType = iArr;
            try {
                iArr[AnimationType.ROTATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.ALPHA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.HORIZON_LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.HORIZON_RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.HORIZON_CROSS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.SCALE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.FLIP_HORIZON.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.FLIP_VERTICAL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public void runAnimation(View view, long j, AnimationType animationType) {
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[animationType.ordinal()]) {
            case 1:
                runRotateAnimation(view, j);
                return;
            case 2:
                runAlphaAnimation(view, j);
                return;
            case 3:
                runHorizonLeftAnimation(view, j);
                return;
            case 4:
                runHorizonRightAnimation(view, j);
                return;
            case 5:
            default:
                return;
            case 6:
                runScaleAnimation(view, j);
                return;
            case 7:
                runFlipHorizonAnimation(view, j);
                return;
            case 8:
                runFlipVerticalAnimation(view, j);
                return;
        }
    }

    private void runHorizonLeftAnimation(View view, long j) {
        view.setAlpha(0.0f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationX", -UIUtils.getScreenWidth(), 0.0f);
        ofFloat.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(this.mDuration);
        animatorSet.setStartDelay(j);
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.addListener(new AnimatorListener(view));
        animatorSet.start();
    }

    private void runHorizonRightAnimation(View view, long j) {
        view.setAlpha(0.0f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationX", UIUtils.getScreenWidth(), 0.0f);
        ofFloat.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setStartDelay(j);
        animatorSet.setDuration(this.mDuration);
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.addListener(new AnimatorListener(view));
        animatorSet.start();
    }

    private void runAlphaAnimation(View view, long j) {
        view.setAlpha(0.0f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        ofFloat.setStartDelay(j);
        ofFloat.setDuration(this.mDuration);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.addListener(new AnimatorListener(view));
        ofFloat.start();
    }

    private void runRotateAnimation(View view, long j) {
        view.setAlpha(0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, Key.ROTATION, 0.0f, 360.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        ofFloat2.setInterpolator(new AccelerateInterpolator(1.0f));
        ofFloat3.setInterpolator(new AccelerateInterpolator(1.0f));
        animatorSet.setDuration(this.mDuration);
        animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3, ofFloat4);
        animatorSet.setStartDelay(j);
        animatorSet.addListener(new AnimatorListener(view));
        animatorSet.start();
    }

    private void runScaleAnimation(View view, long j) {
        view.setAlpha(0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        animatorSet.setDuration(this.mDuration);
        animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        animatorSet.setStartDelay(j);
        animatorSet.addListener(new AnimatorListener(view));
        animatorSet.start();
    }

    private void runFlipVerticalAnimation(View view, long j) {
        view.setAlpha(0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "rotationX", -180.0f, 0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        animatorSet.setDuration(this.mDuration);
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.setStartDelay(j);
        animatorSet.addListener(new AnimatorListener(view));
        animatorSet.start();
    }

    private void runFlipHorizonAnimation(View view, long j) {
        view.setAlpha(0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "rotationY", -180.0f, 0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        animatorSet.setDuration(this.mDuration);
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.setStartDelay(j);
        animatorSet.addListener(new AnimatorListener(view));
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class AnimatorListener implements Animator.AnimatorListener {
        View mmView;

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        public AnimatorListener(View view) {
            this.mmView = view;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.mmView.setLayerType(0, null);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            this.mmView.setLayerType(0, null);
        }
    }
}
