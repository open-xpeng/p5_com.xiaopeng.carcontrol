package com.xiaopeng.xui.vui.listenner;

import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.xui.vui.VuiRecyclerView;

/* loaded from: classes2.dex */
public class VuiRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private VuiRecyclerView mVuiRecycleView;

    public VuiRecyclerViewScrollListener(VuiRecyclerView vuiRecyclerView) {
        this.mVuiRecycleView = null;
        this.mVuiRecycleView = vuiRecyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrollStateChanged(RecyclerView recyclerView, int i) {
        VuiRecyclerView vuiRecyclerView;
        super.onScrollStateChanged(recyclerView, i);
        if (i != 0 || (vuiRecyclerView = this.mVuiRecycleView) == null) {
            return;
        }
        vuiRecyclerView.updateVuiScene();
    }
}
