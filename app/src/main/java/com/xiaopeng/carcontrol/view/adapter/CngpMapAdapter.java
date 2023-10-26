package com.xiaopeng.carcontrol.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.bean.xpilot.map.CngpMapItem;
import com.xiaopeng.carcontrol.view.adapter.CngpMapAdapter;
import com.xiaopeng.xui.widget.XCircularProgressBar;
import java.util.List;

/* loaded from: classes2.dex */
public class CngpMapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<CngpMapItem> mItemList;
    private final LayoutInflater mLayoutInflater;
    private ItemClickListener mListener;

    /* loaded from: classes2.dex */
    public interface ItemClickListener {
        void onDelete(int id);

        void onDownload(int id);

        void onRetry(int id);
    }

    public CngpMapAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }

    public void setData(List<CngpMapItem> list) {
        this.mItemList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != 1) {
            if (viewType != 2) {
                return null;
            }
            return new CityItemVH(this.mLayoutInflater.inflate(R.layout.layout_cngp_map_city_item, parent, false));
        }
        return new CategoryTitleVH(this.mLayoutInflater.inflate(R.layout.layout_cngp_map_title_item, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        List<CngpMapItem> list = this.mItemList;
        if (list == null) {
            return;
        }
        CngpMapItem cngpMapItem = list.get(position);
        int type = cngpMapItem.getType();
        if (type == 1) {
            ((CategoryTitleVH) viewHolder).bind(cngpMapItem);
        } else if (type != 2) {
        } else {
            ((CityItemVH) viewHolder).bind(cngpMapItem);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, List<Object> payloads) {
        List<CngpMapItem> list = this.mItemList;
        if (list == null) {
            return;
        }
        CngpMapItem cngpMapItem = list.get(position);
        int type = cngpMapItem.getType();
        if (type == 1) {
            ((CategoryTitleVH) viewHolder).bind(cngpMapItem);
        } else if (type != 2) {
        } else {
            ((CityItemVH) viewHolder).bind(cngpMapItem);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<CngpMapItem> list = this.mItemList;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        List<CngpMapItem> list = this.mItemList;
        if (list == null) {
            return -1;
        }
        return list.get(position).getType();
    }

    /* loaded from: classes2.dex */
    class CategoryTitleVH extends RecyclerView.ViewHolder {
        TextView titleView;

        CategoryTitleVH(View itemView) {
            super(itemView);
            this.titleView = (TextView) itemView.findViewById(R.id.tv_cngp_title);
        }

        public void bind(CngpMapItem item) {
            this.titleView.setText(item.getTitle());
            this.titleView.setTextColor(CngpMapAdapter.this.mContext.getColor(item.getTitleColor()));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class CityItemVH extends RecyclerView.ViewHolder {
        TextView actionBtn;
        XCircularProgressBar progressBar;
        TextView statusTv;
        TextView titleView;

        CityItemVH(View itemView) {
            super(itemView);
            this.titleView = (TextView) itemView.findViewById(R.id.tv_cngp_title);
            this.statusTv = (TextView) itemView.findViewById(R.id.tv_cngp_status);
            this.actionBtn = (TextView) itemView.findViewById(R.id.btn_cngp_action);
            this.progressBar = (XCircularProgressBar) itemView.findViewById(R.id.pb_cngp_download);
        }

        public void bind(final CngpMapItem item) {
            this.titleView.setText(item.getTitle());
            int state = item.getState();
            if (state == 0) {
                this.actionBtn.setText(R.string.cngp_map_panel_action_download);
                this.actionBtn.setEnabled(true);
                this.actionBtn.setActivated(item.isActivated());
                this.statusTv.setText(R.string.cngp_map_panel_status_not_download);
                this.statusTv.setTextColor(CngpMapAdapter.this.mContext.getColor(R.color.x_theme_text_03));
                this.progressBar.setVisibility(4);
                this.actionBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.adapter.-$$Lambda$CngpMapAdapter$CityItemVH$9gyTsVY5_s37HM4lmPWwqzoT5CI
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        CngpMapAdapter.CityItemVH.this.lambda$bind$0$CngpMapAdapter$CityItemVH(item, view);
                    }
                });
            } else if (state == 1) {
                this.actionBtn.setText(R.string.cngp_map_panel_action_downloading);
                this.actionBtn.setEnabled(false);
                this.actionBtn.setActivated(false);
                this.statusTv.setText(item.getDownloadedPercentage() + "%");
                this.statusTv.setTextColor(CngpMapAdapter.this.mContext.getColor(R.color.x_theme_text_03));
                this.progressBar.setVisibility(0);
                this.progressBar.setProgress(item.getDownloadedPercentage());
            } else if (state == 2) {
                this.actionBtn.setText(R.string.cngp_map_panel_action_delete);
                this.actionBtn.setEnabled(true);
                this.actionBtn.setActivated(true);
                this.statusTv.setText(R.string.cngp_map_panel_status_downloaded);
                this.statusTv.setTextColor(CngpMapAdapter.this.mContext.getColor(R.color.x_theme_text_03));
                this.progressBar.setVisibility(4);
                this.actionBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.adapter.-$$Lambda$CngpMapAdapter$CityItemVH$mc3-7AtlxQHA0utCKH35aIMqJow
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        CngpMapAdapter.CityItemVH.this.lambda$bind$1$CngpMapAdapter$CityItemVH(item, view);
                    }
                });
            } else if (state != 3) {
            } else {
                this.actionBtn.setText(R.string.cngp_map_panel_action_retry);
                this.actionBtn.setEnabled(true);
                this.actionBtn.setActivated(item.isActivated());
                this.statusTv.setText(R.string.cngp_map_panel_status_failed);
                this.statusTv.setTextColor(CngpMapAdapter.this.mContext.getColor(R.color.x_theme_primary_negative_normal));
                this.progressBar.setVisibility(4);
                this.actionBtn.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.adapter.-$$Lambda$CngpMapAdapter$CityItemVH$mboQITlWX4t4Ya2h7oGpb__Mn80
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        CngpMapAdapter.CityItemVH.this.lambda$bind$2$CngpMapAdapter$CityItemVH(item, view);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$bind$0$CngpMapAdapter$CityItemVH(final CngpMapItem item, View v) {
            CngpMapAdapter.this.mListener.onDownload(item.getId());
        }

        public /* synthetic */ void lambda$bind$1$CngpMapAdapter$CityItemVH(final CngpMapItem item, View v) {
            CngpMapAdapter.this.mListener.onDelete(item.getId());
        }

        public /* synthetic */ void lambda$bind$2$CngpMapAdapter$CityItemVH(final CngpMapItem item, View v) {
            CngpMapAdapter.this.mListener.onRetry(item.getId());
        }
    }
}
