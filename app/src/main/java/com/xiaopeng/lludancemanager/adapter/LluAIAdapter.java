package com.xiaopeng.lludancemanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.lludancemanager.R;
import com.xiaopeng.lludancemanager.bean.LluAiUserManualBean;
import com.xiaopeng.xui.widget.XTextView;
import java.util.List;

/* loaded from: classes2.dex */
public class LluAIAdapter extends RecyclerView.Adapter<LluAiHolder> {
    private List<LluAiUserManualBean> mList;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public LluAiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LluAiHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_llu_ai, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(LluAiHolder holder, int position) {
        LluAiUserManualBean lluAiUserManualBean = this.mList.get(position);
        holder.mIndex.setText(lluAiUserManualBean.getIndex() + "");
        holder.mContent.setText(lluAiUserManualBean.getText());
    }

    public void setList(List<LluAiUserManualBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<LluAiUserManualBean> list = this.mList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class LluAiHolder extends RecyclerView.ViewHolder {
        public XTextView mContent;
        public XTextView mIndex;

        public LluAiHolder(View itemView) {
            super(itemView);
            this.mIndex = (XTextView) itemView.findViewById(R.id.index);
            this.mContent = (XTextView) itemView.findViewById(R.id.content);
        }
    }
}
