package com.xiaopeng.xui.widget.prompt;

import android.content.Context;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.xiaopeng.xpui.R;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public abstract class Prompt {
    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;
    private static final String TAG = "XPrompt";
    private Animation mChangeAnim;
    private Context mContext;
    private Animation mEnterAnim;
    private Animation mExitAnim;
    private boolean mIsQueueShow;
    private boolean mIsUseExitAnim;
    private int mLongTime;
    private OnPromptActionListener mOnPromptActionListener;
    private int mShortTime;
    private XPromptMessage mXPromptMessage;
    XPromptView mXPromptView;
    private Handler mHandler = new Handler();
    private ArrayList<XPromptMessage> mMessages = new ArrayList<>();
    private Runnable mRemoveRunnable = new Runnable() { // from class: com.xiaopeng.xui.widget.prompt.Prompt.2
        @Override // java.lang.Runnable
        public void run() {
            Prompt.this.removeView();
        }
    };
    private Runnable mShowRunnable = new Runnable() { // from class: com.xiaopeng.xui.widget.prompt.Prompt.3
        @Override // java.lang.Runnable
        public void run() {
            if (Prompt.this.showNext()) {
                return;
            }
            Prompt.this.cancel();
        }
    };

    protected abstract boolean addView();

    protected abstract void removeView();

    public Prompt(Context context) {
        this.mContext = context;
        XPromptView xPromptView = new XPromptView(context);
        this.mXPromptView = xPromptView;
        xPromptView.setPrompt(this);
        this.mShortTime = context.getResources().getInteger(R.integer.x_prompt_shortTime);
        this.mLongTime = context.getResources().getInteger(R.integer.x_prompt_longTime);
        initAnim();
    }

    private void initAnim() {
        this.mIsUseExitAnim = true;
        this.mEnterAnim = AnimationUtils.loadAnimation(this.mContext, R.anim.x_prompt_enter_anim);
        Animation loadAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.x_prompt_exit_anim);
        this.mExitAnim = loadAnimation;
        loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.xiaopeng.xui.widget.prompt.Prompt.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                Prompt.this.mHandler.post(Prompt.this.mRemoveRunnable);
            }
        });
        this.mChangeAnim = AnimationUtils.loadAnimation(this.mContext, R.anim.x_prompt_change_anim);
    }

    private void setXPromptMessage(XPromptMessage xPromptMessage) {
        this.mXPromptMessage = xPromptMessage;
    }

    public Prompt setQueueShow(boolean z) {
        this.mIsQueueShow = z;
        return this;
    }

    public boolean isQueueShow() {
        return this.mIsQueueShow;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void addMessage(XPromptMessage xPromptMessage) {
        if (!this.mIsQueueShow) {
            this.mMessages.clear();
        }
        this.mMessages.add(xPromptMessage);
    }

    public void show(CharSequence charSequence) {
        show(charSequence, 0);
    }

    public void show(CharSequence charSequence, int i) {
        show(new XPromptMessage(i, charSequence));
    }

    public void show(CharSequence charSequence, int i, String str) {
        show(charSequence, i, str, 1);
    }

    public void show(CharSequence charSequence, int i, String str, int i2) {
        show(new XPromptMessage(i2, charSequence, i, str));
    }

    public void show(CharSequence charSequence, CharSequence charSequence2, String str) {
        show(charSequence, charSequence2, str, 1);
    }

    public void show(CharSequence charSequence, CharSequence charSequence2, String str, int i) {
        show(new XPromptMessage(i, charSequence, charSequence2, str));
    }

    public void show(XPromptMessage xPromptMessage) {
        addMessage(xPromptMessage);
        enqueuePrompt();
    }

    public void show() {
        enqueuePrompt();
    }

    private void enqueuePrompt() {
        if (this.mXPromptView.getParent() == null || !this.mIsQueueShow) {
            showNext();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean showNext() {
        if (this.mMessages.size() > 0) {
            setXPromptMessage(this.mMessages.remove(0));
            return showCurrent();
        }
        return false;
    }

    private boolean showCurrent() {
        if (this.mXPromptMessage == null) {
            return false;
        }
        this.mHandler.removeCallbacks(this.mRemoveRunnable);
        if (this.mXPromptView.getParent() == null) {
            if (!addView()) {
                return false;
            }
            this.mXPromptView.getContentView().startAnimation(this.mEnterAnim);
        } else {
            this.mXPromptView.getContentView().startAnimation(this.mChangeAnim);
        }
        this.mHandler.removeCallbacks(this.mShowRunnable);
        this.mHandler.postDelayed(this.mShowRunnable, this.mXPromptMessage.getDuration() == 0 ? this.mShortTime : this.mLongTime);
        this.mXPromptView.show(this.mXPromptMessage.getText(), this.mXPromptMessage.getIcon(), this.mXPromptMessage.getButton());
        return true;
    }

    public void cancel() {
        this.mMessages.clear();
        this.mHandler.removeCallbacks(this.mShowRunnable);
        if (this.mIsUseExitAnim) {
            this.mXPromptView.getContentView().startAnimation(this.mExitAnim);
        } else {
            this.mHandler.post(this.mRemoveRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Prompt setUseExitAnim(boolean z) {
        this.mIsUseExitAnim = z;
        return this;
    }

    public Prompt setOnPromptActionClickListener(OnPromptActionListener onPromptActionListener) {
        this.mOnPromptActionListener = onPromptActionListener;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onAction(int i) {
        OnPromptActionListener onPromptActionListener = this.mOnPromptActionListener;
        if (onPromptActionListener != null) {
            onPromptActionListener.onPromptAction(this.mXPromptMessage.getId(), i);
        }
        this.mShowRunnable.run();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void clearQueue() {
        this.mHandler.removeCallbacks(this.mShowRunnable);
    }
}
