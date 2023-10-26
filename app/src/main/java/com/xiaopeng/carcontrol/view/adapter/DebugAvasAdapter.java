package com.xiaopeng.carcontrol.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.xui.widget.XTextView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DebugAvasAdapter extends RecyclerView.Adapter {
    private static final int END = 20;
    public static final int OFFSET = 5;
    private OnItemClickListener mItemClickListener;
    private List<String> mList = new ArrayList();

    /* loaded from: classes2.dex */
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public DebugAvasAdapter(Context context, OnItemClickListener listener) {
        this.mItemClickListener = listener;
        for (int i = 4; i <= 20; i++) {
            this.mList.add(context.getString(R.string.avas_effect_title_head) + i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AvasEffectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.debug_avas_effect_btn, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AvasEffectViewHolder) holder).mBtn.setText(this.mList.get(position));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mList.size();
    }

    /* loaded from: classes2.dex */
    private class AvasEffectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private XTextView mBtn;

        AvasEffectViewHolder(View itemView) {
            super(itemView);
            XTextView xTextView = (XTextView) itemView;
            this.mBtn = xTextView;
            xTextView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (DebugAvasAdapter.this.mItemClickListener != null) {
                DebugAvasAdapter.this.mItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
