package com.xiaopeng.xui.vui.listenner;

import android.view.ViewTreeObserver;
import com.xiaopeng.xui.vui.VuiRecyclerView;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class VuiRecyclerViewGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private VuiRecyclerView mVuiRecycleView;
    private AtomicInteger updateNum = new AtomicInteger(0);

    public VuiRecyclerViewGlobalLayoutListener(VuiRecyclerView vuiRecyclerView) {
        this.mVuiRecycleView = null;
        this.mVuiRecycleView = vuiRecyclerView;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        if (this.updateNum.get() <= 0 || this.mVuiRecycleView == null) {
            return;
        }
        this.updateNum.decrementAndGet();
        this.mVuiRecycleView.updateVuiScene();
    }

    public void setUpdateNum() {
        this.updateNum.incrementAndGet();
    }
}
