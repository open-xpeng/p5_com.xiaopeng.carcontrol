package com.xiaopeng.carcontrol.bean.xpilot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.util.UIUtils;
import com.xiaopeng.carcontrol.view.XPilotOperateInterface;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.xui.sound.XSoundEffectManager;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XGroupHeader;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* loaded from: classes.dex */
public class XPilotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<XPilotItem> mItemList;
    private final LayoutInflater mLayoutInflater;
    private XPilotOperateInterface mOperator;

    public XPilotAdapter(Context context, List<XPilotItem> itemList) {
        ArrayList arrayList = new ArrayList();
        this.mItemList = arrayList;
        this.mLayoutInflater = LayoutInflater.from(context);
        arrayList.clear();
        arrayList.addAll(itemList);
    }

    public void setXPilotOperatorListener(XPilotOperateInterface listener) {
        this.mOperator = listener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mItemList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return this.mItemList.get(position).getType();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaopeng.carcontrol.bean.xpilot.XPilotAdapter.1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int position) {
                    if (XPilotAdapter.this.getItemViewType(position) == 2) {
                        return 1;
                    }
                    return gridLayoutManager.getSpanCount();
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != 0) {
            if (viewType != 5) {
                if (viewType != 2) {
                    if (viewType == 3) {
                        return new TabVH(this.mLayoutInflater.inflate(R.layout.item_x_pilot_tab, parent, false), this.mOperator);
                    }
                    return new CategoryVH(this.mLayoutInflater.inflate(R.layout.item_x_pilot_cate, parent, false));
                }
                return new CardVH(this.mLayoutInflater.inflate(R.layout.item_x_pilot_card, parent, false), this.mOperator);
            }
            return new FooterVH(this.mLayoutInflater.inflate(R.layout.item_x_pilot_footer, parent, false));
        }
        return new HeaderVH(this.mLayoutInflater.inflate(R.layout.item_x_pilot_header, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        XPilotItem xPilotItem = this.mItemList.get(position);
        int type = xPilotItem.getType();
        if (type == 1) {
            bindCategoryItem(holder, xPilotItem);
        } else if (type == 2) {
            bindCardItem(holder, xPilotItem);
        } else if (type != 3) {
        } else {
            bindTabItem(holder, xPilotItem);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (!payloads.isEmpty()) {
            XPilotItem xPilotItem = this.mItemList.get(position);
            int type = xPilotItem.getType();
            if (type == 2) {
                bindCardItem(holder, xPilotItem);
                return;
            } else if (type != 3) {
                return;
            } else {
                bindTabItem(holder, xPilotItem);
                return;
            }
        }
        super.onBindViewHolder(holder, position, payloads);
    }

    private void bindCategoryItem(RecyclerView.ViewHolder holder, XPilotItem item) {
        if (holder instanceof CategoryVH) {
            CategoryVH categoryVH = (CategoryVH) holder;
            categoryVH.titleView.setText(categoryVH.titleView.getResources().getString(item.getTitleResId()));
        }
    }

    private void bindCardItem(RecyclerView.ViewHolder holder, XPilotItem item) {
        if ((holder instanceof CardVH) && (item instanceof XPilotCardItem)) {
            CardVH cardVH = (CardVH) holder;
            XPilotCardItem xPilotCardItem = (XPilotCardItem) item;
            cardVH.titleView.setText(item.getTitleResId());
            if (item.getDrawableResId() != 0) {
                cardVH.drawableView.setImageResource(item.getDrawableResId());
            } else {
                cardVH.drawableView.setImageDrawable(null);
            }
            Object tag = cardVH.swView.getTag();
            boolean z = true;
            boolean z2 = xPilotCardItem.getValue() != null && xPilotCardItem.getValue().booleanValue();
            cardVH.swView.setChecked(z2);
            cardVH.itemView.setSelected(z2);
            if (xPilotCardItem.getExtBtnRes() > 0) {
                cardVH.btnView.setEnabled(xPilotCardItem.isEnabled());
                cardVH.btnView.setText(xPilotCardItem.getExtBtnRes());
                cardVH.btnView.setVisibility(0);
            } else {
                cardVH.btnView.setVisibility(8);
                cardVH.btnView.setText((CharSequence) null);
                cardVH.itemView.setBackgroundResource(R.drawable.bg_x_pilot_item_selector);
            }
            cardVH.infoBtn.setVisibility(item.getManualIdx() >= 0 ? 0 : 8);
            if (tag == null || ((Integer) tag).intValue() != 1) {
                z = false;
            }
            if (z) {
                XSoundEffectManager.get().play(z2 ? 3 : 4);
            }
            cardVH.swView.setTag(0);
        }
    }

    private void bindTabItem(RecyclerView.ViewHolder holder, XPilotItem item) {
        int intValue;
        if ((holder instanceof TabVH) && (item instanceof XPilotTabItem)) {
            TabVH tabVH = (TabVH) holder;
            XPilotTabItem xPilotTabItem = (XPilotTabItem) item;
            tabVH.titleView.setText(xPilotTabItem.getTitleResId());
            if (item.getDescResId() != 0) {
                tabVH.descView.setVisibility(0);
                tabVH.descView.setText(xPilotTabItem.getDescResId());
            }
            tabVH.infoBtn.setVisibility(item.getManualIdx() > 0 ? 0 : 8);
            String[] tabItems = xPilotTabItem.getTabItems();
            if (tabVH.tabLayout.getTabCount() != tabItems.length) {
                for (String str : tabItems) {
                    tabVH.tabLayout.addTab(str);
                }
            }
            if (xPilotTabItem.getValue() != null && (intValue = xPilotTabItem.getValue().intValue()) >= 0 && intValue < tabItems.length) {
                tabVH.tabLayout.selectTab(xPilotTabItem.getValue().intValue());
            }
            tabVH.itemView.setEnabled(xPilotTabItem.isEnabled());
            tabVH.tabLayout.setEnabled(xPilotTabItem.isEnabled());
        }
    }

    /* loaded from: classes.dex */
    static class HeaderVH extends RecyclerView.ViewHolder {
        HeaderVH(View itemView) {
            super(itemView);
        }
    }

    /* loaded from: classes.dex */
    static class FooterVH extends RecyclerView.ViewHolder {
        FooterVH(View itemView) {
            super(itemView);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class CategoryVH extends RecyclerView.ViewHolder {
        XGroupHeader titleView;

        CategoryVH(View itemView) {
            super(itemView);
            this.titleView = (XGroupHeader) itemView.findViewById(R.id.tv_x_pilot_cate);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class CardVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        XButton btnView;
        ImageView drawableView;
        View infoBtn;
        XPilotOperateInterface operator;
        XSwitch swView;
        TextView titleView;

        CardVH(View itemView, XPilotOperateInterface operator) {
            super(itemView);
            this.titleView = (TextView) itemView.findViewById(R.id.tv_x_pilot_card_title);
            this.btnView = (XButton) itemView.findViewById(R.id.btn_x_pilot_card);
            this.swView = (XSwitch) itemView.findViewById(R.id.sw_x_pilot_card);
            this.drawableView = (ImageView) itemView.findViewById(R.id.iv_x_pilot);
            this.infoBtn = itemView.findViewById(R.id.iv_x_pilot_info);
            this.operator = operator;
            this.swView.setClickable(false);
            itemView.setOnClickListener(this);
            this.infoBtn.setOnClickListener(this);
            this.btnView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (v.getId() == R.id.iv_x_pilot_info) {
                XPilotOperateInterface xPilotOperateInterface = this.operator;
                if (xPilotOperateInterface != null) {
                    xPilotOperateInterface.onInfoClicked(getAdapterPosition());
                }
            } else if (v.getId() == R.id.btn_x_pilot_card) {
                XPilotOperateInterface xPilotOperateInterface2 = this.operator;
                if (xPilotOperateInterface2 != null) {
                    xPilotOperateInterface2.onExtBtnClicked(getAdapterPosition(), v);
                    ((IVuiElement) v).setPerformVuiAction(false);
                }
                UIUtils.delayClickable(v, 500L);
            } else {
                if (this.operator != null) {
                    this.swView.setTag(1);
                    this.operator.onCardItemChanged(getAdapterPosition(), true ^ this.swView.isChecked(), this.itemView);
                }
                UIUtils.delayClickable(v, 500L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class TabVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView descView;
        View infoBtn;
        XPilotOperateInterface operator;
        XTabLayout tabLayout;
        TextView titleView;

        TabVH(View itemView, final XPilotOperateInterface operator) {
            super(itemView);
            this.titleView = (TextView) itemView.findViewById(R.id.tv_x_pilot_tab_title);
            this.descView = (TextView) itemView.findViewById(R.id.tv_x_pilot_tab_desc);
            this.tabLayout = (XTabLayout) itemView.findViewById(R.id.tab_x_pilot_tab);
            this.infoBtn = itemView.findViewById(R.id.iv_x_pilot_info);
            this.operator = operator;
            this.tabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.bean.xpilot.XPilotAdapter.TabVH.1
                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public void onTabChangeEnd(XTabLayout xTabLayout, int index, boolean tabChange, boolean fromUser) {
                }

                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                    XPilotOperateInterface xPilotOperateInterface;
                    if (!fromUser || (xPilotOperateInterface = operator) == null) {
                        return false;
                    }
                    xPilotOperateInterface.onTabItemChanged(TabVH.this.getAdapterPosition(), index, tabLayout);
                    return true;
                }
            });
            this.infoBtn.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            XPilotOperateInterface xPilotOperateInterface;
            if (v.getId() != R.id.iv_x_pilot_info || (xPilotOperateInterface = this.operator) == null) {
                return;
            }
            xPilotOperateInterface.onInfoClicked(getAdapterPosition());
        }
    }
}
