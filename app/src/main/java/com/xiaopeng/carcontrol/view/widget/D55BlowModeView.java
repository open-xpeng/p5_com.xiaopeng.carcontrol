package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacWindBlowMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.xui.widget.XLinearLayout;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class D55BlowModeView extends XLinearLayout implements BlowModeInter {
    private ImageView mImgBlowFace;
    private ImageView mImgBlowFoot;
    private ImageView mImgBlowWin;
    private HvacViewModel mViewModel;

    public D55BlowModeView(Context context) {
        super(context);
    }

    public D55BlowModeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public D55BlowModeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override // com.xiaopeng.carcontrol.view.widget.BlowModeInter
    public void initView() {
        this.mViewModel = (HvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class);
        this.mImgBlowWin = (ImageView) findViewById(R.id.img_blow_win);
        this.mImgBlowFace = (ImageView) findViewById(R.id.img_blow_face);
        this.mImgBlowFoot = (ImageView) findViewById(R.id.img_blow_foot);
        this.mImgBlowWin.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$D55BlowModeView$TfjuEUNClZ0YeTUUwLZ_XjRrQBM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                D55BlowModeView.this.lambda$initView$0$D55BlowModeView(view);
            }
        });
        this.mImgBlowFace.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$D55BlowModeView$gCjAAXRryKtsQo2rEgOr3CRwY4k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                D55BlowModeView.this.lambda$initView$1$D55BlowModeView(view);
            }
        });
        this.mImgBlowFoot.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$D55BlowModeView$MexuXyRXbdadPP_NALXz7ap6m50
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                D55BlowModeView.this.lambda$initView$2$D55BlowModeView(view);
            }
        });
        setWindBlowMode();
        this.mViewModel.getHvacWindModeData().observe((AppCompatActivity) getContext(), new Observer() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$D55BlowModeView$3aM3-q3BfU_lyIF3iHFXNNUq1SE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                D55BlowModeView.this.lambda$initView$3$D55BlowModeView((HvacWindBlowMode) obj);
            }
        });
        this.mViewModel.getHvacPowerData().observe((AppCompatActivity) getContext(), new Observer() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$D55BlowModeView$gr_l4csMYLYu2Yu1gti3jceK4T8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                D55BlowModeView.this.lambda$initView$4$D55BlowModeView((Boolean) obj);
            }
        });
        VuiEngine.getInstance(getContext().getApplicationContext()).setVuiElementUnStandardSwitch(this.mImgBlowWin);
        VuiEngine.getInstance(getContext().getApplicationContext()).setVuiElementUnStandardSwitch(this.mImgBlowFace);
        VuiEngine.getInstance(getContext().getApplicationContext()).setVuiElementUnStandardSwitch(this.mImgBlowFoot);
    }

    public /* synthetic */ void lambda$initView$0$D55BlowModeView(View v) {
        int hvacCmd = HvacWindBlowMode.toHvacCmd(HvacWindBlowMode.Windshield);
        this.mViewModel.setHvacWindBlowMode(hvacCmd);
        StatisticUtils.sendHvacStatistic(PageEnum.HVAC_PAGE, BtnEnum.WIND_MODE_BTN, Integer.valueOf(hvacCmd));
    }

    public /* synthetic */ void lambda$initView$1$D55BlowModeView(View v) {
        int hvacCmd = HvacWindBlowMode.toHvacCmd(HvacWindBlowMode.Face);
        this.mViewModel.setHvacWindBlowMode(hvacCmd);
        StatisticUtils.sendHvacStatistic(PageEnum.HVAC_PAGE, BtnEnum.WIND_MODE_BTN, Integer.valueOf(hvacCmd));
    }

    public /* synthetic */ void lambda$initView$2$D55BlowModeView(View v) {
        int hvacCmd = HvacWindBlowMode.toHvacCmd(HvacWindBlowMode.Foot);
        this.mViewModel.setHvacWindBlowMode(hvacCmd);
        StatisticUtils.sendHvacStatistic(PageEnum.HVAC_PAGE, BtnEnum.WIND_MODE_BTN, Integer.valueOf(hvacCmd));
    }

    public /* synthetic */ void lambda$initView$3$D55BlowModeView(HvacWindBlowMode hvacWindBlowMode) {
        setWindBlowMode();
    }

    public /* synthetic */ void lambda$initView$4$D55BlowModeView(Boolean aBoolean) {
        setWindBlowMode();
    }

    private void setWindBlowMode() {
        boolean isHvacPowerModeOn = this.mViewModel.isHvacPowerModeOn();
        HvacWindBlowMode windBlowMode = this.mViewModel.getWindBlowMode();
        boolean z = true;
        this.mImgBlowWin.setSelected(isHvacPowerModeOn && (HvacWindBlowMode.Windshield == windBlowMode || HvacWindBlowMode.FootWindshield == windBlowMode || HvacWindBlowMode.FaceWindshield == windBlowMode || HvacWindBlowMode.FaceFootWindshield == windBlowMode));
        this.mImgBlowFace.setSelected(isHvacPowerModeOn && (HvacWindBlowMode.Face == windBlowMode || HvacWindBlowMode.FaceAndFoot == windBlowMode || HvacWindBlowMode.FaceWindshield == windBlowMode || HvacWindBlowMode.FaceFootWindshield == windBlowMode));
        ImageView imageView = this.mImgBlowFoot;
        if (!isHvacPowerModeOn || (HvacWindBlowMode.Foot != windBlowMode && HvacWindBlowMode.FootWindshield != windBlowMode && HvacWindBlowMode.FaceAndFoot != windBlowMode && HvacWindBlowMode.FaceFootWindshield != windBlowMode)) {
            z = false;
        }
        imageView.setSelected(z);
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mImgBlowWin);
        arrayList.add(this.mImgBlowFace);
        arrayList.add(this.mImgBlowFoot);
        VuiEngine.getInstance(getContext().getApplicationContext()).updateScene("hvac", arrayList);
    }
}
