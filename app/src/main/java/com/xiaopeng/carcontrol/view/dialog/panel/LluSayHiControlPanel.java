package com.xiaopeng.carcontrol.view.dialog.panel;

import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.CarControlService;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasEffect;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasViewModel;
import com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluEffect;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluPreviewState;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluSayHiEffect;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.MeterViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XSwitch;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class LluSayHiControlPanel extends AbstractControlPanel {
    private AvasViewModel mAvasVm;
    private XDialog mConfirmSayHiSwDialog;
    private LampViewModel mLampVm;
    private TextView mLluSayHiDescTv;
    private final SparseArray<View> mLluSayHiList = new SparseArray<>();
    private LluViewModel mLluVm;
    private MeterViewModel mMeterVm;
    private XSwitch mSayHiAvasSw;
    private XSwitch mSayHiSw;
    private VcuViewModel mVcuVm;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public int getLayoutId() {
        return R.layout.layout_llu_sayhi_setting;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.AbstractControlPanel
    protected int getPanelHeight() {
        return R.dimen.llu_sayhi_setting_height;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.AbstractControlPanel
    protected int getPanelWidth() {
        return R.dimen.x_dialog_xlarge_width;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel, com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return VuiManager.SCENE_LLU_SAY_CONTROL;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean needInitVm() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitViewModel() {
        ViewModelManager viewModelManager = ViewModelManager.getInstance();
        this.mLluVm = (LluViewModel) viewModelManager.getViewModelImpl(ILluViewModel.class);
        this.mVcuVm = (VcuViewModel) viewModelManager.getViewModelImpl(IVcuViewModel.class);
        this.mLampVm = (LampViewModel) viewModelManager.getViewModelImpl(ILampViewModel.class);
        this.mAvasVm = (AvasViewModel) viewModelManager.getViewModelImpl(IAvasViewModel.class);
        this.mMeterVm = (MeterViewModel) viewModelManager.getViewModelImpl(IMeterViewModel.class);
    }

    public /* synthetic */ void lambda$onInitView$0$LluSayHiControlPanel(View v) {
        dismiss();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitView() {
        findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$s9-Q1DD8k9R2XQv1mVDbYS8oyu4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LluSayHiControlPanel.this.lambda$onInitView$0$LluSayHiControlPanel(view);
            }
        });
        this.mLluSayHiDescTv = (TextView) findViewById(R.id.llu_sayhi_effect_summary_tv);
        XButton xButton = (XButton) findViewById(R.id.llu_sayhi_effect_mode_1);
        VuiUtils.setStatefulButtonAttr(xButton, 1, new String[]{ResUtils.getString(R.string.llu_effect_sayhi_mode_1_idle_label), ResUtils.getString(R.string.llu_effect_sayhi_mode_1_playing_label)}, "SetValue|Click");
        XButton xButton2 = (XButton) findViewById(R.id.llu_sayhi_effect_mode_2);
        VuiUtils.setStatefulButtonAttr(xButton2, 1, new String[]{ResUtils.getString(R.string.llu_effect_sayhi_mode_2_idle_label), ResUtils.getString(R.string.llu_effect_sayhi_mode_2_playing_label)}, "SetValue|Click");
        XButton xButton3 = (XButton) findViewById(R.id.llu_sayhi_effect_mode_3);
        VuiUtils.setStatefulButtonAttr(xButton3, 1, new String[]{ResUtils.getString(R.string.llu_effect_sayhi_mode_3_idle_label), ResUtils.getString(R.string.llu_effect_sayhi_mode_3_playing_label)}, "SetValue|Click");
        this.mLluSayHiList.put(LluSayHiEffect.EffectA.ordinal(), xButton);
        this.mLluSayHiList.put(LluSayHiEffect.EffectB.ordinal(), xButton2);
        this.mLluSayHiList.put(LluSayHiEffect.EffectC.ordinal(), xButton3);
        HashMap hashMap = new HashMap();
        hashMap.put("hasFeedback", true);
        hashMap.put("skipMultipleAlready", true);
        for (int i = 0; i < this.mLluSayHiList.size(); i++) {
            XButton xButton4 = (XButton) this.mLluSayHiList.valueAt(i);
            VuiUtils.addProps(xButton4, hashMap);
            VuiManager.instance().setVuiLabelUnSupportText(this.mContext, xButton4);
            xButton4.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$eaTE6lPKkZan8Mna8Af68WowHr4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LluSayHiControlPanel.this.lambda$onInitView$1$LluSayHiControlPanel(view);
                }
            });
        }
        XSwitch xSwitch = (XSwitch) findViewById(R.id.llu_sayhi_sw).findViewById(R.id.x_list_sw);
        this.mSayHiSw = xSwitch;
        xSwitch.setVuiLabel(ResUtils.getString(R.string.llu_sayhi_sw_title));
        this.mSayHiSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$bDFJliY6ZckaY13F0DtjDRYdbfc
            @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
            public final boolean onInterceptCheck(View view, boolean z) {
                return LluSayHiControlPanel.this.lambda$onInitView$2$LluSayHiControlPanel(view, z);
            }
        });
        this.mSayHiSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$SHVtdDWPaXtulmCbSumSaCa5xVs
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                LluSayHiControlPanel.this.lambda$onInitView$3$LluSayHiControlPanel(compoundButton, z);
            }
        });
        XSwitch xSwitch2 = (XSwitch) findViewById(R.id.llu_sayhi_avas_sw).findViewById(R.id.x_list_sw);
        this.mSayHiAvasSw = xSwitch2;
        VuiUtils.addHasFeedbackProp(xSwitch2);
        this.mSayHiAvasSw.setVuiLabel(ResUtils.getString(R.string.llu_sayhi_avas_sw_title));
        this.mSayHiAvasSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$arnuCM2tngZk1HwlQFo6OoEnOF0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                LluSayHiControlPanel.this.lambda$onInitView$4$LluSayHiControlPanel(compoundButton, z);
            }
        });
        findViewById(R.id.llu_sayhi_steer_key_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$-g6Kp-ln18rAw1qwye9sZatC_IQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LluSayHiControlPanel.this.lambda$onInitView$6$LluSayHiControlPanel(view);
            }
        });
    }

    public /* synthetic */ boolean lambda$onInitView$2$LluSayHiControlPanel(View view, boolean isChecked) {
        if (view.isPressed() || isVuiAction(view)) {
            if (isChecked) {
                confirmSayHiSw();
                return true;
            }
            this.mLluVm.setSayHiEnable(false);
            this.mAvasVm.setAvasSayHiSw(false);
            StatisticUtils.sendStatistic(PageEnum.LAMP_PAGE, BtnEnum.LAMP_PAGE_SAYHI_AUTO_PALY_BTN, 0);
        }
        return false;
    }

    public /* synthetic */ void lambda$onInitView$3$LluSayHiControlPanel(CompoundButton buttonView, boolean isChecked) {
        this.mSayHiAvasSw.setEnabled(isChecked);
        this.mSayHiAvasSw.setChecked(isChecked);
    }

    public /* synthetic */ void lambda$onInitView$4$LluSayHiControlPanel(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mAvasVm.setAvasSayHiSw(isChecked);
            StatisticUtils.sendStatistic(PageEnum.LAMP_PAGE, BtnEnum.LAMP_PAGE_SAYHI_AVAS_BTN, Integer.valueOf(isChecked ? 1 : 0));
        }
    }

    public /* synthetic */ void lambda$onInitView$6$LluSayHiControlPanel(View v) {
        LogUtils.i(this.TAG, "Show custom x key panel");
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$jPtNZIVXrqqq5IeLwB-9xaNU_4U
            @Override // java.lang.Runnable
            public final void run() {
                LluSayHiControlPanel.this.lambda$null$5$LluSayHiControlPanel();
            }
        });
    }

    public /* synthetic */ void lambda$null$5$LluSayHiControlPanel() {
        Intent intent = new Intent(this.mContext, CarControlService.class);
        intent.setAction(GlobalConstant.ACTION.ACTION_SHOW_CUSTOM_X_KEY_PANEL);
        App.getInstance().startService(intent);
    }

    private void confirmSayHiSw() {
        if (this.mConfirmSayHiSwDialog == null) {
            this.mConfirmSayHiSwDialog = new XDialog(this.mContext, 2131886921);
            this.mConfirmSayHiSwDialog.setCloseVisibility(true).setTitle(R.string.llu_sayhi_safe_prompt_tv).setCustomView(LayoutInflater.from(this.mContext).inflate(R.layout.layout_llu_sayhi_sw_confirm, (ViewGroup) null), false).setPositiveButton(R.string.btn_ok, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$POHGvJp1J66OehVLQTIlBaKxen4
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    LluSayHiControlPanel.this.lambda$confirmSayHiSw$7$LluSayHiControlPanel(xDialog, i);
                }
            }).setNegativeButton(R.string.btn_cancel).setSystemDialog(2048);
        }
        VuiManager.instance().initVuiDialog(this.mConfirmSayHiSwDialog, this.mContext, VuiManager.SCENE_LLU_SAYHI_CONFIRM);
        this.mConfirmSayHiSwDialog.show();
    }

    public /* synthetic */ void lambda$confirmSayHiSw$7$LluSayHiControlPanel(XDialog xDialog, int i) {
        this.mLluVm.setSayHiEnable(true);
        this.mAvasVm.setAvasSayHiSw(true);
        this.mSayHiSw.setChecked(true);
        StatisticUtils.sendStatistic(PageEnum.LAMP_PAGE, BtnEnum.LAMP_PAGE_SAYHI_AUTO_PALY_BTN, 1);
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel, com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        super.onVuiEvent(view, vuiEvent);
        if (this.mLluSayHiList.indexOfValue(view) >= 0) {
            ((VuiView) view).setPerformVuiAction(true);
            view.performClick();
        }
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onRegisterLiveData() {
        setLiveDataObserver(this.mLluVm.getLluPreviewStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$pBRrrCmwFAj5PHZlGc6LrfAqbD4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluSayHiControlPanel.this.refreshLluView((LluPreviewState) obj);
            }
        });
        setLiveDataObserver(this.mLluVm.getLluSyaHiSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$Elb4liH8Pab_hsu1BFawQvtkunE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluSayHiControlPanel.this.lambda$onRegisterLiveData$8$LluSayHiControlPanel((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mAvasVm.getAvasSayHiSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$s1REdsAKCBMsc0N7uNx1kz9OHbE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluSayHiControlPanel.this.lambda$onRegisterLiveData$9$LluSayHiControlPanel((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mAvasVm.getFriendEffectData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$fyXv3XAo_VOX4liC-5gd4OvsuWg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluSayHiControlPanel.this.lambda$onRegisterLiveData$10$LluSayHiControlPanel((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$onRegisterLiveData$8$LluSayHiControlPanel(Boolean enable) {
        if (enable != null) {
            this.mSayHiSw.setChecked(enable.booleanValue());
            this.mSayHiAvasSw.setEnabled(enable.booleanValue());
            this.mSayHiAvasSw.setChecked(enable.booleanValue() && this.mAvasVm.getAvasSayHiSw());
        }
    }

    public /* synthetic */ void lambda$onRegisterLiveData$9$LluSayHiControlPanel(Boolean enable) {
        if (enable != null) {
            this.mSayHiAvasSw.setChecked(this.mLluVm.isSayHiEnabled() && enable.booleanValue());
        }
    }

    public /* synthetic */ void lambda$onRegisterLiveData$10$LluSayHiControlPanel(Integer effect) {
        if (effect == null || this.mLluSayHiDescTv == null) {
            return;
        }
        this.mLluSayHiDescTv.setText(ResUtils.getString(R.string.llu_sayhi_effect_setting_summary, ResUtils.getString(covertAvasEffectToLluSayHiEffect(AvasEffect.fromAvasType(effect.intValue())).getDescId())));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onRefresh() {
        refreshLluView(this.mLluVm.getLluPreviewStateData().getValue());
        boolean isSayHiEnabled = this.mLluVm.isSayHiEnabled();
        this.mSayHiSw.setChecked(isSayHiEnabled);
        this.mSayHiAvasSw.setEnabled(isSayHiEnabled);
        this.mSayHiAvasSw.setChecked(isSayHiEnabled && this.mAvasVm.getAvasSayHiSw());
    }

    private boolean onInterceptItemClick(View view) {
        int i;
        boolean isLLuSwEnabled = this.mLluVm.isLLuSwEnabled();
        GearLevel gearLevelValue = this.mVcuVm.getGearLevelValue();
        boolean isTurnLampOn = this.mLampVm.isTurnLampOn();
        boolean rearFogLampState = this.mLampVm.getRearFogLampState();
        LogUtils.d(this.TAG, "onInterceptItemClick lluSwitch=" + isLLuSwEnabled + ", currentGear=" + gearLevelValue + ", turnLamp=" + isTurnLampOn + ", fogLamp=" + rearFogLampState);
        if (isLLuSwEnabled) {
            i = (gearLevelValue != GearLevel.P || isTurnLampOn || rearFogLampState) ? R.string.llu_effect_condition_disable : 0;
        } else {
            i = R.string.llu_effect_sw_disable;
        }
        if (i != 0) {
            NotificationHelper.getInstance().showToast(i);
            vuiFeedbackClick(i, view);
            return true;
        }
        return false;
    }

    private String getSoundPath(LluSayHiEffect effect) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[effect.ordinal()];
        String str = i != 1 ? i != 2 ? i != 3 ? null : SoundHelper.PATH_AVAS_SAYHI_3 : SoundHelper.PATH_AVAS_SAYHI_2 : SoundHelper.PATH_AVAS_SAYHI_1;
        LogUtils.i(this.TAG, "getSoundPath:" + str);
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onItemClick */
    public void lambda$onInitView$1$LluSayHiControlPanel(XButton button) {
        LogUtils.d(this.TAG, "onItemClick: " + ((Object) button.getText()));
        if (this.mLluVm.getLluPreviewStateData().getValue() == null) {
            LogUtils.d(this.TAG, "onItemClick: LluPreviewState is null");
            return;
        }
        LluEffect effect = this.mLluVm.getLluPreviewStateData().getValue().getEffect();
        LluSayHiEffect covertAvasEffectToLluSayHiEffect = covertAvasEffectToLluSayHiEffect(AvasEffect.fromAvasType(this.mAvasVm.getFriendEffect()));
        LluSayHiEffect[] values = LluSayHiEffect.values();
        SparseArray<View> sparseArray = this.mLluSayHiList;
        final LluSayHiEffect lluSayHiEffect = values[sparseArray.keyAt(sparseArray.indexOfValue(button))];
        if ((isRunning(effect) && lluSayHiEffect.equals(covertAvasEffectToLluSayHiEffect)) ? false : true) {
            if (lluSayHiEffect != null) {
                this.mLluVm.setLluEffect(LluEffect.SayHi, lluSayHiEffect.toLluCmd());
                TextView textView = this.mLluSayHiDescTv;
                if (textView != null) {
                    textView.setText(ResUtils.getString(R.string.llu_sayhi_effect_setting_summary, ResUtils.getString(lluSayHiEffect.getDescId())));
                }
                this.mAvasVm.setAvasFriendEffect(getAvasEffect(lluSayHiEffect));
                ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$EPu33dT6joQK76r8DpXE13kfRaQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        LluSayHiControlPanel.this.lambda$onItemClick$11$LluSayHiControlPanel(lluSayHiEffect);
                    }
                }, 450L);
                if (!onInterceptItemClick(button)) {
                    ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSayHiControlPanel$UyrxgEzD6do0EH4Nt-6KacxJpkI
                        @Override // java.lang.Runnable
                        public final void run() {
                            LluSayHiControlPanel.this.lambda$onItemClick$12$LluSayHiControlPanel(lluSayHiEffect);
                        }
                    }, 50L);
                }
            } else {
                LogUtils.w(this.TAG, "Can not find SayHi effect for " + ((Object) button.getText()));
            }
            StatisticUtils.sendStatistic(PageEnum.LAMP_PAGE, BtnEnum.LAMP_PAGE_SAYHI_SETTING_BTN, Integer.valueOf(this.mLluSayHiList.indexOfValue(button) + 1));
            return;
        }
        this.mLluVm.stopLluEffectPreview();
        SoundHelper.play("", true, false);
    }

    public /* synthetic */ void lambda$onItemClick$11$LluSayHiControlPanel(final LluSayHiEffect clickSayHiEffect) {
        String soundPath = getSoundPath(clickSayHiEffect);
        if (TextUtils.isEmpty(soundPath)) {
            return;
        }
        SoundHelper.play(soundPath, true, false);
    }

    public /* synthetic */ void lambda$onItemClick$12$LluSayHiControlPanel(final LluSayHiEffect clickSayHiEffect) {
        this.mLluVm.setLluEffectPreview(getLluEffect(clickSayHiEffect));
    }

    private AvasEffect getAvasEffect(LluSayHiEffect sayHiEffect) {
        AvasEffect avasEffect = AvasEffect.SoundEffect1;
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[sayHiEffect.ordinal()];
        if (i != 2) {
            return i != 3 ? avasEffect : AvasEffect.SoundEffect3;
        }
        return AvasEffect.SoundEffect2;
    }

    private LluSayHiEffect covertAvasEffectToLluSayHiEffect(AvasEffect avasEffect) {
        if (avasEffect == null) {
            return LluSayHiEffect.EffectA;
        }
        LluSayHiEffect lluSayHiEffect = LluSayHiEffect.EffectA;
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect[avasEffect.ordinal()];
        if (i != 1) {
            return i != 2 ? lluSayHiEffect : LluSayHiEffect.EffectC;
        }
        return LluSayHiEffect.EffectB;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.dialog.panel.LluSayHiControlPanel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect;

        static {
            int[] iArr = new int[AvasEffect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect = iArr;
            try {
                iArr[AvasEffect.SoundEffect2.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect[AvasEffect.SoundEffect3.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[LluSayHiEffect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect = iArr2;
            try {
                iArr2[LluSayHiEffect.EffectA.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[LluSayHiEffect.EffectB.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[LluSayHiEffect.EffectC.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private LluEffect getLluEffect(LluSayHiEffect sayHiEffect) {
        LluEffect lluEffect = LluEffect.SayHi;
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[sayHiEffect.ordinal()];
        if (i != 2) {
            return i != 3 ? lluEffect : LluEffect.SayHi3;
        }
        return LluEffect.SayHi2;
    }

    private boolean isRunning(LluEffect runningEffect) {
        return LluEffect.SayHi.equals(runningEffect) || LluEffect.SayHi2.equals(runningEffect) || LluEffect.SayHi3.equals(runningEffect);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshLluView(LluPreviewState state) {
        if (state == null) {
            LogUtils.d(this.TAG, "refreshLluView failed: LluPreviewState is null");
            return;
        }
        LluEffect effect = state.getEffect();
        LluSayHiEffect covertAvasEffectToLluSayHiEffect = covertAvasEffectToLluSayHiEffect(AvasEffect.fromAvasType(this.mAvasVm.getFriendEffect()));
        LogUtils.d(this.TAG, "refreshLluView: state=" + state + ", runningEffect=" + effect + ", currentSayHiEffect=" + covertAvasEffectToLluSayHiEffect);
        boolean z = LluPreviewState.Idle.equals(state) || LluPreviewState.Previewing.equals(state);
        boolean isRunning = isRunning(effect);
        TextView textView = this.mLluSayHiDescTv;
        if (textView != null) {
            textView.setText(ResUtils.getString(R.string.llu_sayhi_effect_setting_summary, ResUtils.getString(covertAvasEffectToLluSayHiEffect.getDescId())));
        }
        for (int i = 0; i < this.mLluSayHiList.size(); i++) {
            XButton xButton = (XButton) this.mLluSayHiList.valueAt(i);
            xButton.setEnabled(z);
            LluSayHiEffect lluSayHiEffect = LluSayHiEffect.values()[this.mLluSayHiList.keyAt(i)];
            if (isRunning && lluSayHiEffect.equals(covertAvasEffectToLluSayHiEffect)) {
                xButton.setSelected(true);
                xButton.setText(R.string.llu_effect_stop);
                VuiUtils.setStatefulButtonValue(xButton, 0);
            } else {
                xButton.setSelected(false);
                xButton.setText(lluSayHiEffect.getDescId());
                VuiUtils.setStatefulButtonValue(xButton, 1);
            }
        }
    }
}
