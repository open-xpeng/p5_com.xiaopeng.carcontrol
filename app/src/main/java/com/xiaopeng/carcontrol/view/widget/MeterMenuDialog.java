package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.widget.XToggleButton;

/* loaded from: classes2.dex */
public class MeterMenuDialog extends XDialog implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    public static final int STATE_MEDIA_SOURCE = 3;
    public static final int STATE_SCREEN_LIGHT = 2;
    public static final int STATE_TEMPERATURE = 0;
    public static final int STATE_WIND_MODE = 4;
    public static final int STATE_WIND_POWER = 1;
    private SparseBooleanArray mItemValues;
    private IMeterDialogClickListener mListener;
    private XToggleButton mMediaSource;
    private XToggleButton mScreenLight;
    private XToggleButton mTemperature;
    private XToggleButton mWindMode;
    private XToggleButton mWindPower;

    /* loaded from: classes2.dex */
    public interface IMeterDialogClickListener {
        void onMeterItemClick(int index, boolean value);
    }

    public MeterMenuDialog(Context context) {
        this(context, R.style.XDialogView_Large_Custom);
    }

    public MeterMenuDialog(Context context, int style) {
        super(context, style);
        setSystemDialog(2008);
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_meter_menu_panel, getContentView(), false);
        initViews(inflate);
        Drawable background = inflate.getBackground();
        getDialog().getWindow().getAttributes().width = background.getIntrinsicWidth() - 18;
        setTitleVisibility(false);
        setCustomView(inflate, false);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v.getId() == R.id.close_btn) {
            dismiss();
        }
    }

    private void initViews(View view) {
        view.findViewById(R.id.close_btn).setOnClickListener(this);
        this.mTemperature = (XToggleButton) view.findViewById(R.id.btn_meter_temperature);
        this.mMediaSource = (XToggleButton) view.findViewById(R.id.btn_meter_media_src);
        this.mWindPower = (XToggleButton) view.findViewById(R.id.btn_meter_wind_level);
        this.mScreenLight = (XToggleButton) view.findViewById(R.id.btn_meter_screen_light);
        this.mWindMode = (XToggleButton) view.findViewById(R.id.btn_meter_wind_mode);
        this.mTemperature.setOnCheckedChangeListener(this);
        this.mMediaSource.setOnCheckedChangeListener(this);
        this.mWindPower.setOnCheckedChangeListener(this);
        this.mScreenLight.setOnCheckedChangeListener(this);
        this.mWindMode.setOnCheckedChangeListener(this);
    }

    private void updateSetting(View v, int state, boolean ischecked) {
        boolean z = true;
        this.mItemValues.get(state, true);
        if (ischecked) {
            this.mItemValues.put(state, true);
        } else if (getSelectedItemCount() <= 1) {
            NotificationHelper.getInstance().showToast(R.string.meter_item_warning);
            ((Checkable) v).setChecked(true);
            this.mItemValues.put(state, true);
            return;
        } else {
            this.mItemValues.put(state, false);
            z = false;
        }
        IMeterDialogClickListener iMeterDialogClickListener = this.mListener;
        if (iMeterDialogClickListener != null) {
            iMeterDialogClickListener.onMeterItemClick(state, z);
        }
    }

    private int getSelectedItemCount() {
        int i = 0;
        for (int i2 = 0; i2 < this.mItemValues.size(); i2++) {
            if (this.mItemValues.valueAt(i2)) {
                i++;
            }
        }
        return i;
    }

    public void setClickListener(IMeterDialogClickListener listener) {
        this.mListener = listener;
    }

    public void initData(SparseBooleanArray initData) {
        SparseBooleanArray sparseBooleanArray = this.mItemValues;
        if (sparseBooleanArray != null) {
            sparseBooleanArray.clear();
            this.mItemValues = null;
        }
        this.mItemValues = new SparseBooleanArray();
        boolean z = initData.get(0, true);
        this.mItemValues.put(0, z);
        this.mTemperature.setChecked(z);
        if (CarBaseConfig.getInstance().isSupportSwitchMedia()) {
            boolean z2 = initData.get(3, true);
            this.mItemValues.put(3, z2);
            this.mMediaSource.setChecked(z2);
        }
        boolean z3 = initData.get(1, true);
        this.mItemValues.put(1, z3);
        this.mWindPower.setChecked(z3);
        boolean z4 = initData.get(2, true);
        this.mItemValues.put(2, z4);
        this.mScreenLight.setChecked(z4);
        boolean z5 = initData.get(4, true);
        this.mItemValues.put(4, z5);
        this.mWindMode.setChecked(z5);
        if (getSelectedItemCount() == 0) {
            this.mItemValues.put(0, true);
            this.mTemperature.setChecked(true);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
        int id = v.getId();
        if (id == R.id.btn_meter_temperature) {
            updateSetting(v, 0, isChecked);
        } else if (id == R.id.btn_meter_media_src) {
            updateSetting(v, 3, isChecked);
        } else if (id == R.id.btn_meter_wind_level) {
            updateSetting(v, 1, isChecked);
        } else if (id == R.id.btn_meter_screen_light) {
            updateSetting(v, 2, isChecked);
        } else if (id == R.id.btn_meter_wind_mode) {
            updateSetting(v, 4, isChecked);
        }
    }
}
