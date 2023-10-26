package com.xiaopeng.lludancemanager.helper;

import android.animation.Animator;
import android.view.View;

/* loaded from: classes2.dex */
public class AnimationHelper {
    public static final float ALPHA_0 = 0.0f;
    public static final float ALPHA_1 = 1.0f;
    public static final long DURATION = 1000;

    /* loaded from: classes2.dex */
    public interface ViewAnimationListener {
        void onAnimationEnd();
    }

    public static void startViewAlphaAnimation(View view, float alpha, long duration, final ViewAnimationListener viewAnimationListener) {
        view.animate().alpha(alpha).setDuration(duration).setListener(new Animator.AnimatorListener() { // from class: com.xiaopeng.lludancemanager.helper.AnimationHelper.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ViewAnimationListener viewAnimationListener2 = ViewAnimationListener.this;
                if (viewAnimationListener2 != null) {
                    viewAnimationListener2.onAnimationEnd();
                }
            }
        }).start();
    }
}
