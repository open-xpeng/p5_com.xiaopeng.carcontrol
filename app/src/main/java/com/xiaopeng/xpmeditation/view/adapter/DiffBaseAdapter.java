package com.xiaopeng.xpmeditation.view.adapter;

import android.os.Parcelable;
import android.util.Log;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.xpmeditation.util.CopyUtils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class DiffBaseAdapter<K, T extends RecyclerView.ViewHolder> extends BaseAdapter<K, T> {
    private static final String TAG = "DiffBaseAdapter";
    private final ArrayDeque<List<K>> mUpdateQueue = new ArrayDeque<>();
    private Runnable mRefreshRun = new Runnable() { // from class: com.xiaopeng.xpmeditation.view.adapter.DiffBaseAdapter.2
        @Override // java.lang.Runnable
        public void run() {
            if (!DiffBaseAdapter.this.mRecyclerView.isComputingLayout()) {
                DiffBaseAdapter.this.updateNext();
            } else {
                DiffBaseAdapter.this.mRecyclerView.post(DiffBaseAdapter.this.mRefreshRun);
            }
        }
    };

    protected abstract boolean areContentsTheSame(K oldBean, K newBean, int oldPos, int newPos);

    protected abstract boolean areItemsTheSame(K oldBean, K newBean, int oldPos, int newPos);

    protected Object getChangePayload(K oldBean, K newBean, int oldPos, int newPos) {
        return null;
    }

    @Override // com.xiaopeng.xpmeditation.view.adapter.BaseAdapter
    public void setData(final List<K> dataList) {
        if (dataList == null) {
            return;
        }
        this.mUpdateQueue.add(dataList);
        if (this.mUpdateQueue.size() == 1) {
            update(dataList);
        }
    }

    private void update(final List<K> dataList) {
        if (dataList.size() == 0) {
            this.mDataList.clear();
            notifyDataSetChanged();
            updateNext();
        } else if (this.mDataList.size() == 0) {
            this.mDataList.addAll(copyList(dataList));
            notifyDataSetChanged();
            Log.i(TAG, "update1: size = " + this.mDataList.size());
            updateNext();
        } else {
            final ArrayList arrayList = new ArrayList(dataList);
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.xpmeditation.view.adapter.DiffBaseAdapter.1
                @Override // java.lang.Runnable
                public void run() {
                    if (DiffBaseAdapter.this.mDataList.size() == 0) {
                        DiffBaseAdapter.this.mDataList.addAll(DiffBaseAdapter.this.copyList(arrayList));
                        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.xpmeditation.view.adapter.DiffBaseAdapter.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                DiffBaseAdapter.this.notifyDataSetChanged();
                                DiffBaseAdapter.this.updateNext();
                            }
                        });
                        return;
                    }
                    final DiffUtil.DiffResult calculateDiff = DiffUtil.calculateDiff(new DiffCallback(new ArrayList(DiffBaseAdapter.this.mDataList), arrayList), false);
                    final List<K> copyList = DiffBaseAdapter.this.copyList(arrayList);
                    ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.xpmeditation.view.adapter.DiffBaseAdapter.1.2
                        @Override // java.lang.Runnable
                        public void run() {
                            DiffBaseAdapter.this.mDataList.clear();
                            DiffBaseAdapter.this.mDataList.addAll(copyList);
                            RecyclerView.LayoutManager layoutManager = DiffBaseAdapter.this.mRecyclerView.getLayoutManager();
                            Parcelable onSaveInstanceState = layoutManager != null ? layoutManager.onSaveInstanceState() : null;
                            calculateDiff.dispatchUpdatesTo(DiffBaseAdapter.this);
                            if (layoutManager != null && onSaveInstanceState != null) {
                                layoutManager.onRestoreInstanceState(onSaveInstanceState);
                            }
                            DiffBaseAdapter.this.updateNext();
                        }
                    });
                }
            });
        }
    }

    protected List<K> copyList(List<K> list) {
        return CopyUtils.deepCopy(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNext() {
        this.mUpdateQueue.remove();
        if (this.mUpdateQueue.isEmpty()) {
            return;
        }
        if (this.mUpdateQueue.size() > 1) {
            this.mUpdateQueue.clear();
            this.mUpdateQueue.add(this.mUpdateQueue.peekLast());
        }
        update(this.mUpdateQueue.peek());
    }

    /* loaded from: classes2.dex */
    private class DiffCallback extends DiffUtil.Callback {
        private List<K> mNewList;
        private List<K> mOldList;

        public DiffCallback(List<K> oldList, List<K> newList) {
            this.mOldList = oldList;
            this.mNewList = newList;
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public int getOldListSize() {
            List<K> list = this.mOldList;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public int getNewListSize() {
            List<K> list = this.mNewList;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            K k = this.mOldList.get(oldItemPosition);
            K k2 = this.mNewList.get(newItemPosition);
            if (k == null || k2 == null) {
                return false;
            }
            return DiffBaseAdapter.this.areItemsTheSame(k, k2, oldItemPosition, newItemPosition);
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            K k = this.mOldList.get(oldItemPosition);
            K k2 = this.mNewList.get(newItemPosition);
            if (k == null || k2 == null) {
                return false;
            }
            return DiffBaseAdapter.this.areContentsTheSame(k, k2, oldItemPosition, newItemPosition);
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            K k = this.mOldList.get(oldItemPosition);
            K k2 = this.mNewList.get(newItemPosition);
            if (k == null || k2 == null) {
                return null;
            }
            return DiffBaseAdapter.this.getChangePayload(k, k2, oldItemPosition, newItemPosition);
        }
    }
}
