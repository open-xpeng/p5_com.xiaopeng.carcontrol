package com.xiaopeng.xpmeditation.view.adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class BaseAdapter<K, T extends RecyclerView.ViewHolder> extends BaseRecyclerViewAdapter<T> {
    protected List<K> mDataList = new ArrayList();

    public void setData(List<K> dataList) {
        if (dataList == null) {
            return;
        }
        this.mDataList.clear();
        this.mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void addData(List<K> dataList) {
        if (dataList == null) {
            return;
        }
        this.mDataList.addAll(dataList);
        notifyItemRangeInserted(this.mDataList.size() - dataList.size(), dataList.size());
    }

    public void addData(K data) {
        if (data == null) {
            return;
        }
        this.mDataList.add(data);
        notifyItemRangeInserted(this.mDataList.size(), this.mDataList.size());
    }

    public void addData(int position, K data) {
        if (data == null) {
            return;
        }
        this.mDataList.add(position, data);
        notifyItemRangeInserted(position, 1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mDataList.size();
    }

    public K getItem(int pos) {
        if (pos >= this.mDataList.size()) {
            return null;
        }
        return this.mDataList.get(pos);
    }

    public List<K> getData() {
        return this.mDataList;
    }
}
