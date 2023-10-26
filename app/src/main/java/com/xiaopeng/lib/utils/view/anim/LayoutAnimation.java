package com.xiaopeng.lib.utils.view.anim;

import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.lib.utils.view.anim.BaseAnimation;

/* loaded from: classes2.dex */
public class LayoutAnimation extends BaseAnimation {
    private int mOrderIndex = 0;

    public void startAnimation(View view, BaseAnimation.AnimationType animationType) {
        bindAnimation(view, 0, animationType);
    }

    private void bindAnimation(View view, int i, BaseAnimation.AnimationType animationType) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int i2 = 0;
            if (animationType == BaseAnimation.AnimationType.HORIZON_CROSS) {
                while (i2 < viewGroup.getChildCount()) {
                    bindAnimation(viewGroup.getChildAt(i2), i + 1, i2 % 2 == 0 ? BaseAnimation.AnimationType.HORIZON_LEFT : BaseAnimation.AnimationType.HORIZON_RIGHT);
                    i2++;
                }
                return;
            }
            while (i2 < viewGroup.getChildCount()) {
                bindAnimation(viewGroup.getChildAt(i2), i + 1, animationType);
                i2++;
            }
            return;
        }
        runAnimation(view, this.mDelay * this.mOrderIndex, animationType);
        this.mOrderIndex++;
    }
}
