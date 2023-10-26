package com.xiaopeng.xpmeditation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xpmeditation.model.MeditationItemBean;
import com.xiaopeng.xpmeditation.util.GlideUtil;
import com.xiaopeng.xui.widget.XImageView;

/* loaded from: classes2.dex */
public class MeditationMusicAdapter extends DiffBaseAdapter<MeditationItemBean, Holder> {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_meditation_music_item, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(Holder holder, final int position) {
        MeditationItemBean item = getItem(position);
        if (item == null || item.getData() == null) {
            return;
        }
        if (item.isSelected()) {
            if (ResUtils.isScreenOrientationLand()) {
                holder.mSelectedImg.setVisibility(0);
            }
            holder.mShadowImg.setVisibility(0);
            holder.mIconImg.setScaleX(1.0f);
            holder.mIconImg.setScaleY(1.0f);
            holder.mIconImg.setImageAlpha(255);
        } else {
            holder.mShadowImg.setVisibility(8);
            holder.mIconImg.setScaleX(0.8f);
            holder.mIconImg.setScaleY(0.8f);
            if (ResUtils.isScreenOrientationLand()) {
                holder.mIconImg.setImageAlpha(76);
                holder.mSelectedImg.setVisibility(8);
            } else {
                holder.mIconImg.setImageAlpha(120);
            }
        }
        GlideUtil.loadWithOriginSize(holder.itemView.getContext(), item.getData().getThumbnailUrl(), R.drawable.meditation_music_default, holder.mIconImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.adapter.MeditationMusicAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (MeditationMusicAdapter.this.mItemClickListener != null) {
                    MeditationMusicAdapter.this.mItemClickListener.click(0, position);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.adapter.DiffBaseAdapter
    public boolean areItemsTheSame(MeditationItemBean oldBean, MeditationItemBean newBean, int oldPos, int newPos) {
        return (oldBean.getData() == null || newBean.getData() == null || oldBean.getData().getBizId() != newBean.getData().getBizId()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xpmeditation.view.adapter.DiffBaseAdapter
    public boolean areContentsTheSame(MeditationItemBean oldBean, MeditationItemBean newBean, int oldPos, int newPos) {
        return (oldBean.getData() == null || newBean.getData() == null || oldBean.isSelected() != newBean.isSelected() || oldBean.getData().getListenUrl() == null || !oldBean.getData().getListenUrl().equals(newBean.getData().getListenUrl())) ? false : true;
    }

    /* loaded from: classes2.dex */
    public static class Holder extends RecyclerView.ViewHolder {
        private XImageView mIconImg;
        private XImageView mSelectedImg;
        private XImageView mShadowImg;

        public Holder(View itemView) {
            super(itemView);
            this.mIconImg = (XImageView) itemView.findViewById(R.id.img_icon);
            this.mSelectedImg = (XImageView) itemView.findViewById(R.id.img_selected);
            this.mShadowImg = (XImageView) itemView.findViewById(R.id.img_selected_shadow);
        }
    }
}
