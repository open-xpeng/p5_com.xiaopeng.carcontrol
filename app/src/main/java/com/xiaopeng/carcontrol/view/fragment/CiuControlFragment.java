package com.xiaopeng.carcontrol.view.fragment;

import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.view.helper.CiuInfoHelper;
import com.xiaopeng.carcontrol.viewmodel.ciu.CiuViewModel;
import com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XSwitch;

/* loaded from: classes2.dex */
public class CiuControlFragment extends BaseFragment {
    private CiuViewModel mCiuVm;

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected int getPreLoadLayoutId() {
        return R.layout.layout_car_ciu_stub;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_car_ciu;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected boolean needPreLoadLayout() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViewModels() {
        this.mCiuVm = (CiuViewModel) getViewModel(ICiuViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViews() {
        initContentLayout();
    }

    private void initContentLayout() {
        XListTwo xListTwo = (XListTwo) this.mPreloadLayout.findViewById(R.id.inner_camera_sw_item);
        final XSwitch xSwitch = (XSwitch) xListTwo.findViewById(R.id.x_list_sw);
        xSwitch.setChecked(this.mCiuVm.isDmsSwEnabled());
        xSwitch.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$LOmdqX_bkZuuwqe_qekQfOUuUqk
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return CiuControlFragment.this.lambda$initContentLayout$1$CiuControlFragment(xSwitch, view, motionEvent);
            }
        });
        xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$5dgEBq8vSz4KC2DOyTMpayXpGMg
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CiuControlFragment.this.lambda$initContentLayout$2$CiuControlFragment(compoundButton, z);
            }
        });
        LiveData<Boolean> dmsSwData = this.mCiuVm.getDmsSwData();
        xSwitch.getClass();
        setLiveDataObserver(dmsSwData, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$aeIiqtRaGva9_dxY-D21QFFdegA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                XSwitch.this.setChecked(((Boolean) obj).booleanValue());
            }
        });
        ((XImageView) xListTwo.findViewById(R.id.x_list_img)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$NaXV57sofmJTN6ltbpRl3e3fcC8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CiuControlFragment.this.lambda$initContentLayout$3$CiuControlFragment(view);
            }
        });
        XListSingle xListSingle = (XListSingle) this.mPreloadLayout.findViewById(R.id.face_recognition_sw_item);
        final XSwitch xSwitch2 = (XSwitch) xListSingle.findViewById(R.id.x_list_sw);
        xSwitch2.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$2z0AO5-SC-lBexSKDxWEZQ1sEb0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return CiuControlFragment.this.lambda$initContentLayout$4$CiuControlFragment(xSwitch2, view, motionEvent);
            }
        });
        xSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$P_tFq4ntFoqY04GuJC94R0QwK3s
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CiuControlFragment.this.lambda$initContentLayout$5$CiuControlFragment(compoundButton, z);
            }
        });
        xSwitch2.setChecked(this.mCiuVm.isFaceIdSwEnabled());
        LiveData<Boolean> faceIdSwData = this.mCiuVm.getFaceIdSwData();
        xSwitch2.getClass();
        setLiveDataObserver(faceIdSwData, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$aeIiqtRaGva9_dxY-D21QFFdegA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                XSwitch.this.setChecked(((Boolean) obj).booleanValue());
            }
        });
        ((XImageView) xListSingle.findViewById(R.id.x_list_img)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$PU46oV92dTAh_XhNXMqfyV8GCqc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CiuControlFragment.this.lambda$initContentLayout$6$CiuControlFragment(view);
            }
        });
        XListSingle xListSingle2 = (XListSingle) this.mPreloadLayout.findViewById(R.id.fatigue_driving_sw_item);
        final XSwitch xSwitch3 = (XSwitch) xListSingle2.findViewById(R.id.x_list_sw);
        xSwitch3.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$NBTN8IGK3KkZUiUi_E203ieoOjY
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return CiuControlFragment.this.lambda$initContentLayout$7$CiuControlFragment(xSwitch3, view, motionEvent);
            }
        });
        xSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$aOL4c6qr0a6m1SjncehUVV5xGMY
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CiuControlFragment.this.lambda$initContentLayout$8$CiuControlFragment(compoundButton, z);
            }
        });
        xSwitch3.setChecked(this.mCiuVm.isFatigueSwEnabled());
        LiveData<Boolean> fatigueSwData = this.mCiuVm.getFatigueSwData();
        xSwitch3.getClass();
        setLiveDataObserver(fatigueSwData, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$aeIiqtRaGva9_dxY-D21QFFdegA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                XSwitch.this.setChecked(((Boolean) obj).booleanValue());
            }
        });
        ((XImageView) xListSingle2.findViewById(R.id.x_list_img)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$HPwm5WLwek7K5SN2VnRgc7ZABjM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CiuControlFragment.this.lambda$initContentLayout$9$CiuControlFragment(view);
            }
        });
        XListSingle xListSingle3 = (XListSingle) this.mPreloadLayout.findViewById(R.id.inattention_sw_item);
        final XSwitch xSwitch4 = (XSwitch) xListSingle3.findViewById(R.id.x_list_sw);
        xSwitch4.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$LVzwUybuVwksqa8rqLmTOcjJnJg
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return CiuControlFragment.this.lambda$initContentLayout$10$CiuControlFragment(xSwitch4, view, motionEvent);
            }
        });
        xSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$eBSQmy4qcl69MBiTacimZzKGbM4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CiuControlFragment.this.lambda$initContentLayout$11$CiuControlFragment(compoundButton, z);
            }
        });
        xSwitch4.setChecked(this.mCiuVm.isDistractSwEnabled());
        LiveData<Boolean> distractionSwData = this.mCiuVm.getDistractionSwData();
        xSwitch4.getClass();
        setLiveDataObserver(distractionSwData, new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$aeIiqtRaGva9_dxY-D21QFFdegA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                XSwitch.this.setChecked(((Boolean) obj).booleanValue());
            }
        });
        ((XImageView) xListSingle3.findViewById(R.id.x_list_img)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$iH-TdIvPazcgl6IM-OZ3jrth65E
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CiuControlFragment.this.lambda$initContentLayout$12$CiuControlFragment(view);
            }
        });
    }

    public /* synthetic */ boolean lambda$initContentLayout$1$CiuControlFragment(final XSwitch dmsCameraSw, View v, MotionEvent event) {
        if (event.getAction() == 0) {
            if (ClickHelper.isFastClick()) {
                return true;
            }
            if (dmsCameraSw.isChecked()) {
                showDialog(R.string.ciu_inner_camera_title_tv, R.string.ciu_inner_camera_feature_feedback, getStringById(R.string.btn_cancel), (XDialogInterface.OnClickListener) null, getStringById(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$CiuControlFragment$tzaOzLsjCEK_0YWmzwBeLdLxmcc
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        CiuControlFragment.this.lambda$null$0$CiuControlFragment(xDialog, i);
                    }
                });
                return true;
            }
            return false;
        }
        return false;
    }

    public /* synthetic */ void lambda$null$0$CiuControlFragment(XDialog xDialog, int i) {
        this.mCiuVm.setDmsSwEnable(false);
    }

    public /* synthetic */ void lambda$initContentLayout$2$CiuControlFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            this.mCiuVm.setDmsSwEnable(isChecked);
        }
    }

    public /* synthetic */ void lambda$initContentLayout$3$CiuControlFragment(View v) {
        CiuInfoHelper.getInstance().showCiuInfoPanel(this.mContext, 0);
    }

    public /* synthetic */ boolean lambda$initContentLayout$4$CiuControlFragment(final XSwitch faceIdSw, View v, MotionEvent event) {
        if (event.getAction() == 0) {
            return ClickHelper.isFastClick();
        }
        if (faceIdSw.isChecked() || this.mCiuVm.isDmsSwEnabled()) {
            return false;
        }
        NotificationHelper.getInstance().showToast(R.string.ciu_inner_camera_not_open_tip);
        return true;
    }

    public /* synthetic */ void lambda$initContentLayout$5$CiuControlFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            this.mCiuVm.setFaceIdSwEnable(isChecked);
        }
    }

    public /* synthetic */ void lambda$initContentLayout$6$CiuControlFragment(View v) {
        CiuInfoHelper.getInstance().showCiuInfoPanel(this.mContext, 1);
    }

    public /* synthetic */ boolean lambda$initContentLayout$7$CiuControlFragment(final XSwitch fatigueSw, View v, MotionEvent event) {
        if (event.getAction() == 0) {
            return ClickHelper.isFastClick();
        }
        if (fatigueSw.isChecked() || this.mCiuVm.isDmsSwEnabled()) {
            return false;
        }
        NotificationHelper.getInstance().showToast(R.string.ciu_inner_camera_not_open_tip);
        return true;
    }

    public /* synthetic */ void lambda$initContentLayout$8$CiuControlFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            this.mCiuVm.setFatigueSwEnable(isChecked);
        }
    }

    public /* synthetic */ void lambda$initContentLayout$9$CiuControlFragment(View v) {
        CiuInfoHelper.getInstance().showCiuInfoPanel(this.mContext, 2);
    }

    public /* synthetic */ boolean lambda$initContentLayout$10$CiuControlFragment(final XSwitch distractSw, View v, MotionEvent event) {
        if (event.getAction() == 0) {
            return ClickHelper.isFastClick();
        }
        if (distractSw.isChecked() || this.mCiuVm.isDmsSwEnabled()) {
            return false;
        }
        NotificationHelper.getInstance().showToast(R.string.ciu_inner_camera_not_open_tip);
        return true;
    }

    public /* synthetic */ void lambda$initContentLayout$11$CiuControlFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            this.mCiuVm.setDistractSwEnable(isChecked);
        }
    }

    public /* synthetic */ void lambda$initContentLayout$12$CiuControlFragment(View v) {
        CiuInfoHelper.getInstance().showCiuInfoPanel(this.mContext, 3);
    }
}
