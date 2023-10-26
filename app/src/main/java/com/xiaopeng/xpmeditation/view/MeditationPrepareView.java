package com.xiaopeng.xpmeditation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xpmeditation.util.GlideUtil;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public class MeditationPrepareView extends XFrameLayout {
    private static final String TAG = "MeditationPrepareView";
    private LottieAnimationView mAnimView;
    private XTextView mFinishBtn;
    private View.OnClickListener mFinishListener;
    private XImageView mFlowerImg;
    private XTextView mNumTxt;
    private XTextView mPrepareTxt;
    private XTextView mTipTxt;

    public MeditationPrepareView(Context context) {
        super(context);
        init();
    }

    public MeditationPrepareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MeditationPrepareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LogUtils.d(TAG, "init: ");
        LayoutInflater.from(getContext()).inflate(R.layout.layout_meditation_prepare, this);
        this.mTipTxt = (XTextView) findViewById(R.id.txt_tip);
        this.mNumTxt = (XTextView) findViewById(R.id.txt_num);
        this.mPrepareTxt = (XTextView) findViewById(R.id.txt_prepare);
        if (CarBaseConfig.getInstance().isSupportMsmD()) {
            this.mPrepareTxt.setText(R.string.meditation_prepareing_tip);
        } else {
            this.mPrepareTxt.setText(R.string.meditation_prepareing_tip_low);
        }
        XTextView xTextView = (XTextView) findViewById(R.id.btn_close);
        this.mFinishBtn = xTextView;
        xTextView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.MeditationPrepareView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (MeditationPrepareView.this.mFinishListener != null) {
                    MeditationPrepareView.this.mFinishListener.onClick(v);
                }
            }
        });
        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottie_meditation);
        this.mAnimView = lottieAnimationView;
        lottieAnimationView.setAnimation("anim_meditation_start.json");
        this.mAnimView.setRepeatCount(-1);
        this.mAnimView.playAnimation();
        this.mFlowerImg = (XImageView) findViewById(R.id.img_flower);
        GlideUtil.asyncLoadSrc(R.drawable.meditation_flower, this.mFlowerImg);
        if (ResUtils.isScreenOrientationLand()) {
            this.mFlowerImg.setAlpha(0.9f);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LottieAnimationView lottieAnimationView = this.mAnimView;
        if (lottieAnimationView == null || !lottieAnimationView.isAnimating()) {
            return;
        }
        this.mAnimView.cancelAnimation();
    }

    public void setOnFinishListener(View.OnClickListener finishListener) {
        this.mFinishListener = finishListener;
    }

    public void switchCountDown() {
        this.mPrepareTxt.setVisibility(8);
        this.mFlowerImg.setVisibility(8);
        this.mNumTxt.setVisibility(0);
    }

    public void refreshCountDownTime(long second) {
        this.mNumTxt.setText(String.valueOf(second));
    }
}
