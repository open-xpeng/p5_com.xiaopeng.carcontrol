package com.xiaopeng.xpmeditation.view.adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/* loaded from: classes2.dex */
public abstract class BaseRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    public static final int TYPE_CLICK_DELETE = 2;
    public static final int TYPE_CLICK_FOOT = 11;
    public static final int TYPE_CLICK_HEAD = 10;
    public static final int TYPE_CLICK_ITEM = 0;
    public static final int TYPE_CLICK_LOVE = 1;
    protected boolean mCanUpdateData = true;
    protected int mCurrTheme;
    protected ItemClickListener mItemClickListener;
    protected ItemPlayClickListener mItemPlayClickListener;
    protected RecyclerView mRecyclerView;

    /* loaded from: classes2.dex */
    public interface ItemClickListener {
        void click(int type, int position);
    }

    /* loaded from: classes2.dex */
    public interface ItemPlayClickListener {
        void click(int type, int position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    public void setCanUpdateData(boolean canUpdateData) {
        this.mCanUpdateData = canUpdateData;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setItemPlayClickListener(ItemPlayClickListener itemPlayClickListener) {
        this.mItemPlayClickListener = itemPlayClickListener;
    }

    public boolean isEmpty() {
        return getItemCount() <= 0;
    }
}
