package com.xiaopeng.xpmeditation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.lludancemanager.util.ResUtil;
import com.xiaopeng.xpmeditation.model.MeditationBean;
import com.xiaopeng.xpmeditation.model.MeditationItemBeanPlus;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public class MeditationPlusMusicAdapter extends DiffBaseAdapter<MeditationItemBeanPlus, Holder> {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_meditation_music_item_plus, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(Holder holder, final int position) {
        MeditationItemBeanPlus item = getItem(position);
        if (item == null || item.getData() == null) {
            return;
        }
        MeditationBean.DataBean.ListBeanPlus data = item.getData();
        if (item.isSelected()) {
            holder.mShadowImg.setVisibility(0);
        } else {
            holder.mShadowImg.setVisibility(8);
        }
        holder.mName.setText(ResUtil.getStringByIdentity(data.getTitle()));
        holder.mIconImg.setImageResource(ResUtil.getDrawableResByName(data.getThumbnailUrl()));
        if (position < 2) {
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.adapter.-$$Lambda$MeditationPlusMusicAdapter$o5yO4LUsioV-cT8bpEv6Ilf4-q0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MeditationPlusMusicAdapter.this.lambda$onBindViewHolder$0$MeditationPlusMusicAdapter(position, view);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onBindViewHolder$0$MeditationPlusMusicAdapter(final int position, View v) {
        if (this.mItemClickListener != null) {
            this.mItemClickListener.click(0, position);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.adapter.DiffBaseAdapter
    public boolean areItemsTheSame(MeditationItemBeanPlus oldBean, MeditationItemBeanPlus newBean, int oldPos, int newPos) {
        return (oldBean.getData() == null || newBean.getData() == null || oldBean.getData().getBizId() != newBean.getData().getBizId()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.adapter.DiffBaseAdapter
    public boolean areContentsTheSame(MeditationItemBeanPlus oldBean, MeditationItemBeanPlus newBean, int oldPos, int newPos) {
        return (oldBean.getData() == null || newBean.getData() == null || oldBean.isSelected() != newBean.isSelected() || oldBean.getData().getListenUrl() == null || !oldBean.getData().getListenUrl().equals(newBean.getData().getListenUrl())) ? false : true;
    }

    /* loaded from: classes2.dex */
    public static class Holder extends RecyclerView.ViewHolder {
        private XImageView mIconImg;
        private XTextView mName;
        private XImageView mShadowImg;

        public Holder(View itemView) {
            super(itemView);
            this.mIconImg = (XImageView) itemView.findViewById(R.id.img_icon);
            this.mShadowImg = (XImageView) itemView.findViewById(R.id.img_selected_shadow);
            this.mName = (XTextView) itemView.findViewById(R.id.img_name);
        }
    }
}
