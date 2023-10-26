package com.xiaopeng.carcontrol.view.dialog.panel.customkey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.xui.widget.toggle.XTextToggle;
import com.xiaopeng.xui.widget.toggle.XToggleLayout;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class CustomKeyAdapter extends RecyclerView.Adapter<CustomKeyViewHolder> {
    private static final long FAST_CHANGE_POSITION_INTERVAL = 500;
    private static final String TAG = "CustomKeyAdapter";
    private OnItemCheckedListener mListener;
    private int titleId;
    private List<String> mList = new ArrayList();
    private int mPosition = -1;
    private long mLastSetPositionTime = 0;
    private XToggleLayout.OnCheckedChangeListener mCheckedChangeListener = new XToggleLayout.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.customkey.CustomKeyAdapter.1
        @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
        public boolean onInterceptClickCheck(XToggleLayout xToggleLayout) {
            return xToggleLayout.isChecked();
        }

        @Override // com.xiaopeng.xui.widget.toggle.XToggleLayout.OnCheckedChangeListener
        public void onCheckedChanged(XToggleLayout xToggleLayout, boolean isCheck) {
            if ((xToggleLayout.isPressed() || CustomKeyAdapter.this.isVuiAction(xToggleLayout)) && isCheck) {
                CustomKeyAdapter.this.setPosition(((Integer) xToggleLayout.getTag()).intValue(), true);
            }
        }
    };

    /* loaded from: classes2.dex */
    public interface OnItemCheckedListener {
        void onItemChecked(int position);
    }

    public CustomKeyAdapter() {
    }

    public CustomKeyAdapter(List<String> list, OnItemCheckedListener listener, int titleId) {
        this.titleId = titleId;
        this.mList.addAll(list);
        this.mListener = listener;
    }

    public void setSelect(int position, boolean isClick, boolean fromVui) {
        OnItemCheckedListener onItemCheckedListener;
        if (position < 0 || position >= this.mList.size()) {
            LogUtils.e(TAG, "setPosition : Error position : " + position);
            return;
        }
        LogUtils.d(TAG, "setPosition : " + position + ", lastPosition : " + this.mPosition + ", isClick : " + isClick);
        int i = this.mPosition;
        this.mPosition = position;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastSetPositionTime < FAST_CHANGE_POSITION_INTERVAL) {
            LogUtils.d(TAG, "setPosition : change position fast, notify all item");
            notifyDataSetChanged();
        } else {
            if (!isClick) {
                notifyItemChanged(position);
            }
            if (i >= 0 && i < this.mList.size()) {
                notifyItemChanged(i);
            }
        }
        this.mLastSetPositionTime = currentTimeMillis;
        if (fromVui || (onItemCheckedListener = this.mListener) == null) {
            return;
        }
        onItemCheckedListener.onItemChecked(position);
    }

    public void setPosition(int position, boolean isClick) {
        setSelect(position, isClick, false);
    }

    public void increase() {
        setPosition(this.mPosition + 1, false);
    }

    public void decrease() {
        setPosition(this.mPosition - 1, false);
    }

    public void addItem(String item, int index) {
        if (item == null || index < 0 || index > getItemCount()) {
            return;
        }
        this.mList.add(index, item);
        notifyItemInserted(index);
    }

    public void removeItem(int index) {
        if (index < 0 || index >= getItemCount()) {
            return;
        }
        this.mList.remove(index);
        notifyItemRemoved(index);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CustomKeyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomKeyViewHolder(LayoutInflater.from(App.getInstance().getApplicationContext()).inflate(R.layout.custom_key_btn, parent, false), this.mCheckedChangeListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(CustomKeyViewHolder holder, int position) {
        holder.mContentView.setVuiLabel(this.mList.get(position));
        holder.mContentView.setTextOn(this.mList.get(position));
        holder.mContentView.setTextOff(this.mList.get(position));
        holder.mContentView.setTag(Integer.valueOf(position));
        holder.mContentView.setChecked(position == this.mPosition);
        initVui(holder.mContentView, position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mList.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class CustomKeyViewHolder extends RecyclerView.ViewHolder {
        private XTextToggle mContentView;

        CustomKeyViewHolder(View itemView, XToggleLayout.OnCheckedChangeListener listener) {
            super(itemView);
            XTextToggle xTextToggle = (XTextToggle) itemView;
            this.mContentView = xTextToggle;
            xTextToggle.setOnCheckedChangeListener(listener);
        }
    }

    private void initVui(View view, int position) {
        if (view instanceof IVuiElement) {
            IVuiElement iVuiElement = (IVuiElement) view;
            iVuiElement.setVuiLabel(this.mList.get(position));
            iVuiElement.setVuiPosition(position);
            iVuiElement.setVuiElementId(view.getId() + "_" + position);
            iVuiElement.setVuiFatherElementId(String.valueOf(this.titleId));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isVuiAction(View view) {
        return VuiManager.instance().isVuiAction(view);
    }
}
