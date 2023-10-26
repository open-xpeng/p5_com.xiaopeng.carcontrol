package com.xiaopeng.carcontrol.view.fragment.xpilot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrol.bean.xpilot.XPilotCardItem;
import com.xiaopeng.carcontrol.bean.xpilot.XPilotItem;
import com.xiaopeng.carcontrol.bean.xpilot.XPilotTabItem;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.UIUtils;
import com.xiaopeng.carcontrol.view.XPilotOperateInterface;
import com.xiaopeng.carcontrol.view.speech.VuiActions;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.VuiMode;
import com.xiaopeng.xui.sound.XSoundEffectManager;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XConstraintLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;
import com.xiaopeng.xui.widget.XTextView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class XPilotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "XPilotAdapter";
    private final Context mContext;
    private volatile boolean mIsSoundEffectEnabled;
    private final List<XPilotItem> mItemList;
    private final LayoutInflater mLayoutInflater;
    private XPilotOperateInterface mOperator;

    public XPilotAdapter(Context context, List<XPilotItem> itemList) {
        ArrayList arrayList = new ArrayList();
        this.mItemList = arrayList;
        this.mIsSoundEffectEnabled = false;
        this.mContext = context;
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
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.XPilotAdapter.1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int position) {
                    int itemViewType = XPilotAdapter.this.getItemViewType(position);
                    if (itemViewType != 2) {
                        if (itemViewType != 3) {
                            if (itemViewType != 6) {
                                return gridLayoutManager.getSpanCount();
                            }
                            return 1;
                        }
                        return ResUtils.getInt(R.integer.item_x_pilot_tab_span_size);
                    }
                    return 1;
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != 0) {
            if (viewType != 2) {
                if (viewType == 3) {
                    View inflate = this.mLayoutInflater.inflate(R.layout.item_x_pilot_tab, parent, false);
                    inflate.setEnabled(true);
                    inflate.setSelected(true);
                    return new TabVH(inflate, this.mOperator);
                } else if (viewType != 5) {
                    if (viewType == 6) {
                        return new EmptyCardVH(this.mLayoutInflater.inflate(R.layout.item_x_pilot_empty_card, parent, false));
                    }
                    return new CategoryVH(this.mLayoutInflater.inflate(R.layout.item_x_pilot_cate, parent, false));
                } else {
                    return new FooterVH(this.mLayoutInflater.inflate(R.layout.item_x_pilot_footer, parent, false));
                }
            }
            return new CardVH(this.mLayoutInflater.inflate(R.layout.item_x_pilot_card, parent, false), this.mOperator);
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
            bindCardItem(holder, xPilotItem, position);
        } else if (type != 3) {
        } else {
            bindTabItem(holder, xPilotItem, position);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (!payloads.isEmpty()) {
            XPilotItem xPilotItem = this.mItemList.get(position);
            int type = xPilotItem.getType();
            if (type == 2) {
                bindCardItem(holder, xPilotItem, position);
                return;
            } else if (type != 3) {
                return;
            } else {
                bindTabItem(holder, xPilotItem, position);
                return;
            }
        }
        super.onBindViewHolder(holder, position, payloads);
    }

    private void bindCategoryItem(RecyclerView.ViewHolder holder, XPilotItem item) {
        if (holder instanceof CategoryVH) {
            ((CategoryVH) holder).titleView.setText(item.getTitleResId());
        }
    }

    private void bindCardItem(RecyclerView.ViewHolder holder, XPilotItem item, int position) {
        if ((holder instanceof CardVH) && (item instanceof XPilotCardItem)) {
            CardVH cardVH = (CardVH) holder;
            XPilotCardItem xPilotCardItem = (XPilotCardItem) item;
            cardVH.titleView.setText(item.getTitleResId());
            if (item.getDescResId() != 0) {
                cardVH.descView.setText(item.getDescResId());
            } else {
                cardVH.descView.setText((CharSequence) null);
            }
            if (item.getDrawableResId() != 0) {
                cardVH.drawableView.setImageResource(item.getDrawableResId());
            } else {
                cardVH.drawableView.setImageDrawable(null);
            }
            if (item.getTestDrawableResId() != 0) {
                cardVH.testFuncIv.setImageResource(item.getTestDrawableResId());
            }
            cardVH.testFuncIv.setVisibility(item.isIsTestFunc() ? 0 : 8);
            if (item.isFunAvailable()) {
                cardVH.funcAvailable.setVisibility(8);
                if (xPilotCardItem.isNeedPurchase()) {
                    cardVH.itemView.setEnabled(xPilotCardItem.isEnabled());
                    cardVH.btnView.setVisibility(8);
                    cardVH.purchaseBtnView.setVisibility(0);
                    cardVH.drawableView.setAlpha(0.4f);
                    cardVH.swView.setVisibility(8);
                } else {
                    if (xPilotCardItem.getExtBtnRes() > 0) {
                        cardVH.btnView.setEnabled(xPilotCardItem.isEnabled());
                        cardVH.purchaseBtnView.setVisibility(8);
                        cardVH.btnView.setText(xPilotCardItem.getExtBtnRes());
                        cardVH.btnView.setVisibility(0);
                        cardVH.drawableView.setAlpha(xPilotCardItem.isEnabled() ? 1.0f : 0.4f);
                    } else {
                        cardVH.purchaseBtnView.setVisibility(8);
                        cardVH.btnView.setVisibility(8);
                        cardVH.btnView.setText((CharSequence) null);
                    }
                    cardVH.swView.setVisibility(xPilotCardItem.isNeedShowSw() ? 0 : 8);
                    cardVH.itemView.setEnabled(xPilotCardItem.isEnabled());
                    cardVH.swView.setEnabled(xPilotCardItem.isEnabled());
                    boolean z = xPilotCardItem.getValue() != null && xPilotCardItem.getValue().booleanValue();
                    boolean isChecked = cardVH.swView.isChecked();
                    cardVH.swView.setChecked(z);
                    cardVH.itemView.setSelected(z);
                    if (z) {
                        cardVH.drawableView.setAlpha(1.0f);
                    } else {
                        cardVH.drawableView.setAlpha(0.4f);
                    }
                    if (xPilotCardItem.getSoundFlag() && z != isChecked) {
                        LogUtils.d(TAG, "XSoundEffectManager.play()-->" + item.getKey());
                        XSoundEffectManager.get().play(z ? 3 : 4);
                        xPilotCardItem.clearSoundFlag();
                    }
                }
                cardVH.infoBtn.setVisibility(item.getManualIdx() >= 0 ? 0 : 8);
                if (item.isVuiEnable()) {
                    cardVH.mCardLayout.setVuiMode(VuiMode.NORMAL);
                    cardVH.mCardLayout.setVuiLabel(ResUtils.getString(item.getVuiLabelResId()));
                    cardVH.mCardLayout.setVuiElementId(cardVH.mCardLayout.getId() + "_" + position);
                } else {
                    cardVH.mCardLayout.setVuiMode(VuiMode.DISABLED);
                }
                if (cardVH.infoBtn.getVisibility() == 0 && item.isVuiEnable()) {
                    cardVH.infoBtn.setVuiLabel(ResUtils.getString(R.string.xpilot_vui_into_label, cardVH.mCardLayout.getVuiLabel()));
                    cardVH.infoBtn.setVuiElementId(cardVH.infoBtn.getId() + "_" + position);
                } else {
                    cardVH.infoBtn.setVuiLabel(null);
                    cardVH.infoBtn.setVuiElementId(null);
                }
                if (cardVH.purchaseBtnView.getVisibility() == 0 && item.isVuiEnable()) {
                    cardVH.purchaseBtnView.setVuiMode(VuiMode.NORMAL);
                    cardVH.purchaseBtnView.setVuiAction(VuiActions.CLICK);
                    cardVH.purchaseBtnView.setVuiLabel(ResUtils.getString(R.string.xpilot_3_purchase_btn_vui_label, cardVH.mCardLayout.getVuiLabel()));
                    cardVH.purchaseBtnView.setVuiElementId(cardVH.purchaseBtnView.getId() + "_" + position);
                } else {
                    cardVH.purchaseBtnView.setVuiMode(VuiMode.DISABLED);
                    cardVH.purchaseBtnView.setVuiLabel(null);
                    cardVH.purchaseBtnView.setVuiElementId(null);
                }
                if (cardVH.btnView.getVisibility() == 0 && cardVH.btnView.isEnabled() && item.isVuiEnable()) {
                    cardVH.btnView.setVuiMode(VuiMode.NORMAL);
                    cardVH.btnView.setVuiAction(VuiActions.CLICK);
                    cardVH.btnView.setVuiLabel(getExtBtnVuiLabel(xPilotCardItem, cardVH));
                    cardVH.btnView.setVuiElementId(cardVH.btnView.getId() + "_" + position);
                    return;
                }
                cardVH.btnView.setVuiMode(VuiMode.DISABLED);
                cardVH.btnView.setVuiLabel(null);
                cardVH.btnView.setVuiElementId(null);
                return;
            }
            cardVH.funcAvailable.setVisibility(0);
            cardVH.swView.setVisibility(8);
            cardVH.itemView.setSelected(false);
            cardVH.drawableView.setAlpha(0.4f);
            if (item.isVuiEnable()) {
                cardVH.mCardLayout.setVuiMode(VuiMode.NORMAL);
                cardVH.mCardLayout.setVuiLabel(ResUtils.getString(item.getVuiLabelResId()));
                cardVH.mCardLayout.setVuiElementId(cardVH.mCardLayout.getId() + "_" + position);
            } else {
                cardVH.mCardLayout.setVuiMode(VuiMode.DISABLED);
            }
            if (cardVH.itemView instanceof IVuiElement) {
                VuiUtils.addHasFeedbackProp((IVuiElement) cardVH.itemView);
            }
            cardVH.infoBtn.setVisibility(item.getManualIdx() >= 0 ? 0 : 8);
            if (cardVH.infoBtn.getVisibility() == 0 && item.isVuiEnable()) {
                cardVH.infoBtn.setVuiLabel(ResUtils.getString(R.string.xpilot_vui_into_label, cardVH.mCardLayout.getVuiLabel()));
                cardVH.infoBtn.setVuiElementId(cardVH.infoBtn.getId() + "_" + position);
                return;
            }
            cardVH.infoBtn.setVuiLabel(null);
            cardVH.infoBtn.setVuiElementId(null);
        }
    }

    private String getExtBtnVuiLabel(XPilotCardItem item, CardVH cardVh) {
        if (item.getExtBtnRes() <= 0) {
            return null;
        }
        if (R.string.ngp_setting_button == item.getExtBtnRes()) {
            return ResUtils.getString(R.string.ngp_setting_button_vui_label);
        }
        if (R.string.isla_setting_button == item.getExtBtnRes()) {
            return ResUtils.getString(R.string.isla_setting_button_vui_label);
        }
        if (R.string.ngp_study_button == item.getExtBtnRes() || R.string.mem_park_study_button == item.getExtBtnRes() || R.string.laa_study_button == item.getExtBtnRes()) {
            return ResUtils.getString(R.string.study_button_vui_label, cardVh.mCardLayout.getVuiLabel());
        }
        LogUtils.w(TAG, "getExtBtnVuiLabel un-catchable case! " + ResUtils.getString(item.getExtBtnRes()));
        return null;
    }

    private void bindTabItem(RecyclerView.ViewHolder holder, XPilotItem item, int position) {
        int intValue;
        if ((holder instanceof TabVH) && (item instanceof XPilotTabItem)) {
            TabVH tabVH = (TabVH) holder;
            XPilotTabItem xPilotTabItem = (XPilotTabItem) item;
            tabVH.titleView.setText(xPilotTabItem.getTitleResId());
            if (item.getDescResId() != 0) {
                tabVH.descView.setVisibility(0);
                tabVH.descView.setText(xPilotTabItem.getDescResId());
            } else {
                tabVH.descView.setVisibility(8);
            }
            tabVH.infoBtn.setVisibility(item.getManualIdx() > 0 ? 0 : 8);
            String[] tabItems = xPilotTabItem.getTabItems();
            tabVH.tabLayout.removeAllViews();
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
            tabVH.tabLayout.setVuiLabel(ResUtils.getString(xPilotTabItem.getTitleResId()));
            tabVH.tabLayout.setVuiElementId(tabVH.tabLayout.getId() + "_" + position);
            if (tabVH.infoBtn.getVisibility() == 0 && item.isVuiEnable()) {
                tabVH.infoBtn.setVuiLabel(ResUtils.getString(R.string.xpilot_vui_into_label, ResUtils.getString(item.getVuiLabelResId())));
                tabVH.infoBtn.setVuiElementId(tabVH.infoBtn.getId() + "_" + position);
                return;
            }
            tabVH.infoBtn.setVuiLabel(null);
            tabVH.infoBtn.setVuiElementId(null);
        }
    }

    public void enableSoundEffect() {
        this.mIsSoundEffectEnabled = true;
    }

    public void disableSoundEffect() {
        this.mIsSoundEffectEnabled = false;
    }

    /* loaded from: classes2.dex */
    static class HeaderVH extends RecyclerView.ViewHolder {
        HeaderVH(View itemView) {
            super(itemView);
        }
    }

    /* loaded from: classes2.dex */
    static class FooterVH extends RecyclerView.ViewHolder {
        FooterVH(View itemView) {
            super(itemView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class CategoryVH extends RecyclerView.ViewHolder {
        TextView titleView;

        CategoryVH(View itemView) {
            super(itemView);
            this.titleView = (TextView) itemView.findViewById(R.id.tv_x_pilot_cate);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class CardVH extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        XButton btnView;
        XTextView descView;
        ImageView drawableView;
        XImageView funcAvailable;
        XImageView infoBtn;
        XConstraintLayout mCardLayout;
        XPilotOperateInterface operator;
        XButton purchaseBtnView;
        XSwitch swView;
        XImageView testFuncIv;
        TextView titleView;

        CardVH(View itemView, XPilotOperateInterface operator) {
            super(itemView);
            this.titleView = (TextView) itemView.findViewById(R.id.tv_x_pilot_card_title);
            this.btnView = (XButton) itemView.findViewById(R.id.btn_x_pilot_card);
            this.swView = (XSwitch) itemView.findViewById(R.id.sw_x_pilot_card);
            this.drawableView = (ImageView) itemView.findViewById(R.id.iv_x_pilot);
            this.infoBtn = (XImageView) itemView.findViewById(R.id.iv_x_pilot_info);
            this.mCardLayout = (XConstraintLayout) itemView.findViewById(R.id.card_layout);
            this.funcAvailable = (XImageView) itemView.findViewById(R.id.img_func_not_available);
            this.purchaseBtnView = (XButton) itemView.findViewById(R.id.btn_x_pilot_purchase);
            this.descView = (XTextView) itemView.findViewById(R.id.tv_x_pilot_card_desc);
            this.testFuncIv = (XImageView) itemView.findViewById(R.id.iv_x_pilot_test);
            this.operator = operator;
            this.swView.setClickable(false);
            this.swView.setOnCheckedChangeListener(this);
            this.swView.setCheckSoundEnable(false);
            itemView.setOnClickListener(this);
            if (operator != null) {
                operator.onVuiFeedbackViewCreate(this.swView);
                operator.onVuiFeedbackViewCreate(this.btnView);
                operator.onVuiFeedbackViewCreate(itemView);
            }
            this.infoBtn.setOnClickListener(this);
            this.btnView.setOnClickListener(this);
            this.purchaseBtnView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (ClickHelper.isFastClick()) {
                LogUtils.i(XPilotAdapter.TAG, "Click to fast for xpilot card item", false);
            } else if (v.getId() == R.id.iv_x_pilot_info) {
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
                XPilotOperateInterface xPilotOperateInterface3 = this.operator;
                if (xPilotOperateInterface3 != null) {
                    xPilotOperateInterface3.onCardItemChanged(getAdapterPosition(), !this.swView.isChecked(), this.itemView);
                    ((IVuiElement) this.itemView).setPerformVuiAction(false);
                }
                UIUtils.delayClickable(v, 500L);
            }
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            XPilotOperateInterface xPilotOperateInterface = this.operator;
            if (xPilotOperateInterface != null) {
                xPilotOperateInterface.onVuiItemUpdate(this.itemView);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class TabVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView descView;
        XImageView infoBtn;
        XPilotOperateInterface operator;
        XTabLayout tabLayout;
        TextView titleView;

        TabVH(View itemView, final XPilotOperateInterface operator) {
            super(itemView);
            this.titleView = (TextView) itemView.findViewById(R.id.tv_x_pilot_tab_title);
            this.descView = (TextView) itemView.findViewById(R.id.tv_x_pilot_tab_desc);
            this.tabLayout = (XTabLayout) itemView.findViewById(R.id.tab_x_pilot_tab);
            this.infoBtn = (XImageView) itemView.findViewById(R.id.iv_x_pilot_info);
            this.operator = operator;
            this.tabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.xpilot.XPilotAdapter.TabVH.1
                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                    XPilotOperateInterface xPilotOperateInterface;
                    if (!fromUser || (xPilotOperateInterface = operator) == null) {
                        return false;
                    }
                    xPilotOperateInterface.onTabItemChanged(TabVH.this.getAdapterPosition(), index, tabLayout);
                    return true;
                }

                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public void onTabChangeEnd(XTabLayout xTabLayout, int index, boolean tabChange, boolean fromUser) {
                    XPilotOperateInterface xPilotOperateInterface = operator;
                    if (xPilotOperateInterface != null) {
                        xPilotOperateInterface.onVuiItemUpdate(TabVH.this.tabLayout);
                    }
                }
            });
            if (operator != null) {
                operator.onVuiFeedbackViewCreate(this.tabLayout);
            }
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

    /* loaded from: classes2.dex */
    private static class EmptyCardVH extends RecyclerView.ViewHolder {
        EmptyCardVH(View itemView) {
            super(itemView);
        }
    }
}
