package com.xiaopeng.carcontrol.view.helper;

import android.animation.Animator;
import android.view.View;

/* loaded from: classes2.dex */
public class AnimHelper {
    public static void setVisibility(final View view, final int visibility) {
        view.animate().setListener(new Animator.AnimatorListener() { // from class: com.xiaopeng.carcontrol.view.helper.AnimHelper.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                view.setAlpha(visibility == 0 ? 0.0f : 1.0f);
                view.setVisibility(visibility);
            }
        }).alpha(visibility == 0 ? 1.0f : 0.0f).setDuration(300L).start();
    }
}
