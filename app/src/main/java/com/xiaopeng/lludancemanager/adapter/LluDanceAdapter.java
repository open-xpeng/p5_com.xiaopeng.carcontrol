package com.xiaopeng.lludancemanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.lludancemanager.Constant;
import com.xiaopeng.lludancemanager.R;
import com.xiaopeng.lludancemanager.bean.LluDanceViewData;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import java.util.List;

/* loaded from: classes2.dex */
public class LluDanceAdapter extends RecyclerView.Adapter<LluDanceViewHolder> {
    private static final String TAG = "LluDanceAdapter";
    List<LluDanceViewData> mList;
    private final OnItemListener mListener;
    private volatile int mPreClickIndex = -1;

    /* loaded from: classes2.dex */
    public interface OnItemListener {
        void onItemClick(int position, LluDanceViewData data);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(LluDanceViewHolder holder, int position, List payloads) {
        onBindViewHolder2(holder, position, (List<Object>) payloads);
    }

    public LluDanceAdapter(OnItemListener mListener) {
        this.mListener = mListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public LluDanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LluDanceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_llu_dance, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final LluDanceViewHolder holder, final int position) {
        final LluDanceViewData lluDanceViewData = this.mList.get(position);
        if (lluDanceViewData.getId().equals(Constant.END_ITEM_MORE_ID)) {
            holder.bind(lluDanceViewData);
            holder.itemView.setOnClickListener(null);
            return;
        }
        holder.bind(lluDanceViewData, position, position == this.mPreClickIndex);
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.lludancemanager.adapter.-$$Lambda$LluDanceAdapter$FtUYA6_KNqhyIpoEnXa67BWVnSc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LluDanceAdapter.this.lambda$onBindViewHolder$0$LluDanceAdapter(holder, position, lluDanceViewData, view);
            }
        });
    }

    public /* synthetic */ void lambda$onBindViewHolder$0$LluDanceAdapter(final LluDanceViewHolder holder, final int position, final LluDanceViewData data, View v) {
        if (ClickHelper.isFastClick(holder.itemView.getId(), 200L, false)) {
            LogUtils.i(TAG, "Click to quick!");
            return;
        }
        if (VuiManager.instance().isVuiAction(holder.itemView)) {
            vuiFeedback(R.string.llu_select_feedback, holder.itemView);
        }
        selected(position);
        OnItemListener onItemListener = this.mListener;
        if (onItemListener != null) {
            onItemListener.onItemClick(position, data);
        }
    }

    /* renamed from: onBindViewHolder  reason: avoid collision after fix types in other method */
    public void onBindViewHolder2(LluDanceViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder((LluDanceAdapter) holder, position, payloads);
        if (payloads.isEmpty()) {
            return;
        }
        holder.itemView.setActivated(((Boolean) payloads.get(0)).booleanValue());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        return this.mList.get(position).getId().equals(Constant.END_ITEM_MORE_ID) ? Integer.parseInt(Constant.END_ITEM_MORE_ID) : position;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<LluDanceViewData> list = this.mList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setList(List<LluDanceViewData> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void selected(int position) {
        if (position == this.mPreClickIndex) {
            notifyItemChanged(position, true);
            return;
        }
        if (this.mPreClickIndex != -1) {
            notifyItemChanged(this.mPreClickIndex, false);
        }
        notifyItemChanged(position, true);
        this.mPreClickIndex = position;
    }

    private void vuiFeedback(int content, View view) {
        if (view != null) {
            VuiEngine.getInstance(view.getContext().getApplicationContext()).vuiFeedback(view, new VuiFeedback.Builder().state(1).content(view.getContext().getString(content)).build());
        }
    }
}
