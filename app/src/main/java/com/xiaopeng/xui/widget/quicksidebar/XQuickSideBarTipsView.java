package com.xiaopeng.xui.widget.quicksidebar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.widget.XRelativeLayout;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class XQuickSideBarTipsView extends XRelativeLayout {
    private static final int SIDEBAR_TIPS_DELAY_CODE = 1;
    private boolean isAnimationHideTips;
    private DelayHandler mDelayHandler;
    private long mDelayedTime;
    private XQuickSideBarTipsItemView mTipsView;

    public XQuickSideBarTipsView(Context context) {
        this(context, null);
    }

    public XQuickSideBarTipsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XQuickSideBarTipsView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isAnimationHideTips = true;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mDelayHandler = new DelayHandler(this);
        if (attributeSet != null) {
            this.mTipsView = new XQuickSideBarTipsItemView(context, attributeSet);
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.XQuickSideBarView);
            this.mDelayedTime = obtainStyledAttributes.getInteger(R.styleable.XQuickSideBarView_sidebarTipsDelayTime, 500);
            obtainStyledAttributes.recycle();
            addView(this.mTipsView, new RelativeLayout.LayoutParams(-2, -2));
        }
    }

    public void setAnimationHideTips(boolean z) {
        this.isAnimationHideTips = z;
    }

    public void setText(String str) {
        this.mTipsView.setText(str);
    }

    public void setDelayedTime(long j) {
        this.mDelayedTime = j;
    }

    public void display(boolean z) {
        if (z) {
            setVisibility(0);
            this.mDelayHandler.removeMessages(1);
            return;
        }
        this.mDelayHandler.sendEmptyMessageDelayed(1, this.mDelayedTime);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class DelayHandler extends Handler {
        private final WeakReference<XQuickSideBarTipsView> reference;

        public DelayHandler(XQuickSideBarTipsView xQuickSideBarTipsView) {
            this.reference = new WeakReference<>(xQuickSideBarTipsView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            XQuickSideBarTipsView xQuickSideBarTipsView = this.reference.get();
            if (xQuickSideBarTipsView != null && message.what == 1) {
                xQuickSideBarTipsView.hideView(xQuickSideBarTipsView.isAnimationHideTips);
                xQuickSideBarTipsView.mDelayHandler.removeMessages(1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideView(boolean z) {
        if (z) {
            animate().alpha(0.0f).setDuration(200L).setListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.xui.widget.quicksidebar.XQuickSideBarTipsView.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    XQuickSideBarTipsView.this.setVisibility(4);
                    XQuickSideBarTipsView.this.setAlpha(1.0f);
                }
            });
        } else {
            setVisibility(4);
        }
    }
}
