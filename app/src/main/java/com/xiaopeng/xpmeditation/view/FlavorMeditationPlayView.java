package com.xiaopeng.xpmeditation.view;

import android.animation.Animator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xpmeditation.view.adapter.HorizatalLinearItemDecoration;

/* loaded from: classes2.dex */
public class FlavorMeditationPlayView extends MeditationPlayView {
    private boolean mIsInAmin;

    @Override // com.xiaopeng.xpmeditation.view.MeditationPlayView
    protected void refreshSeekBarStatus() {
    }

    public FlavorMeditationPlayView(Context context) {
        super(context);
        this.mIsInAmin = true;
    }

    public FlavorMeditationPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsInAmin = true;
    }

    public FlavorMeditationPlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mIsInAmin = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.MeditationPlayView
    public void initView() {
        super.initView();
        this.mCenterIconImg.setImageAlpha(194);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.MeditationPlayView
    public void initAnim() {
        super.initAnim();
        this.mAnimView.setRepeatCount(0);
        this.mAnimView.setAnimation("anim_meditation_in.json");
        this.mAnimView.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.xiaopeng.xpmeditation.view.FlavorMeditationPlayView.1
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
                FlavorMeditationPlayView.this.mIsInAmin = false;
                FlavorMeditationPlayView.this.mAnimView.removeAllAnimatorListeners();
                FlavorMeditationPlayView.this.mAnimView.setAnimation("anim_meditation.json");
                FlavorMeditationPlayView.this.mAnimView.setRepeatCount(-1);
                FlavorMeditationPlayView.this.mAnimView.playAnimation();
            }
        });
    }

    @Override // com.xiaopeng.xpmeditation.view.MeditationPlayView
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizatalLinearItemDecoration(43);
    }

    @Override // com.xiaopeng.xpmeditation.view.MeditationPlayView
    protected void refreshControllerBtnStatus(int status) {
        if (status == 3 || status == 4) {
            this.mControllerText.setText(R.string.meditation_play);
            this.mControllerText.setTextColor(getResources().getColor(R.color.color_forward_normal_night, getContext().getTheme()));
            this.mControllerText.setCompoundDrawablesWithIntrinsicBounds(getColorFilter(getContext().getDrawable(R.drawable.ic_play_blue), getResources().getColor(R.color.color_forward_normal_night, getContext().getTheme())), (Drawable) null, (Drawable) null, (Drawable) null);
            this.mControllerText.setCompoundDrawablePadding(10);
            return;
        }
        this.mControllerText.setText(R.string.meditation_pause);
        this.mControllerText.setCompoundDrawablePadding(0);
        this.mControllerText.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        this.mControllerText.setTextColor(getResources().getColor(R.color.txt_color_night, getContext().getTheme()));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.MeditationPlayView
    public void refreshAnimStatus(int status) {
        if (this.mIsInAmin) {
            return;
        }
        super.refreshAnimStatus(status);
    }

    private Drawable getColorFilter(Drawable src, int color) {
        if (src != null) {
            src.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            return src;
        }
        return null;
    }
}
