package com.xiaopeng.carcontrol.view.fragment;

import android.content.Intent;
import android.util.SparseArray;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.view.dialog.panel.ControlPanelManager;
import com.xiaopeng.carcontrol.view.widget.AtlColorView;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluEffect;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluPreviewState;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel;
import com.xiaopeng.carcontrol.viewmodel.light.AtlColor;
import com.xiaopeng.carcontrol.viewmodel.light.AtlViewModel;
import com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.lib.bughunter.anr.UILooperObserver;
import com.xiaopeng.lludancemanager.view.LluDanceActivityNew;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.vui.VuiView;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;
import java.util.HashMap;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* loaded from: classes2.dex */
public class LluFragment extends BaseFragment {
    private static final String TAG = "LluFragment";
    private static final String[] sElementDirectSupport = {"/lampcontrol/lampsetting", "/lampcontrol/sayhicontrol"};
    private AtlColorView mAtlColorView;
    private XSwitch mAtlSw;
    private AtlViewModel mAtlVm;
    private LampViewModel mLampVm;
    private TextView mLluDescText;
    private final SparseArray<View> mLluList = new SparseArray<>();
    private View mLluPlayingView;
    private XSwitch mLluSw;
    private LluViewModel mLluVm;
    private XTabLayout mPositionLampTab;
    private VcuViewModel mVcuVm;

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected int getPreLoadLayoutId() {
        return R.layout.layout_lamp_stub;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment
    protected int getRootLayoutId() {
        return R.layout.fragment_llu;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected boolean needPreLoadLayout() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViewModels() {
        this.mLluVm = (LluViewModel) getViewModel(ILluViewModel.class);
        this.mVcuVm = (VcuViewModel) getViewModel(IVcuViewModel.class);
        this.mLampVm = (LampViewModel) getViewModel(ILampViewModel.class);
        this.mAtlVm = (AtlViewModel) getViewModel(IAtlViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected void initViews() {
        initLampLayout();
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        dismissSettingPanel(GlobalConstant.ACTION.ACTION_SHOW_LLU_SETTING_PANEL, GlobalConstant.ACTION.ACTION_SHOW_SAYHI_SETTING_PANEL);
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment
    protected String[] supportSecondPageForElementDirect() {
        return sElementDirectSupport;
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.direct.OnPageDirectShowListener
    public void onPageDirectShow(String name) {
        String[] strArr = sElementDirectSupport;
        if (strArr[0].equals(name)) {
            if (isGuiInited()) {
                showSettingPanel(GlobalConstant.ACTION.ACTION_SHOW_LLU_SETTING_PANEL);
            }
        } else if (strArr[1].equals(name) && isGuiInited() && !onInterceptItemClickSimple(null)) {
            showSettingPanel(GlobalConstant.ACTION.ACTION_SHOW_SAYHI_SETTING_PANEL);
        }
        super.onPageDirectShow(name);
    }

    @Override // com.xiaopeng.carcontrol.direct.OnPageDirectShowListener
    public void onElementDirectShow(String name) {
        LogUtils.i(TAG, "onElementDirectShow: " + name);
        dismissSettingPanel(GlobalConstant.ACTION.ACTION_SHOW_LLU_SETTING_PANEL, GlobalConstant.ACTION.ACTION_SHOW_SAYHI_SETTING_PANEL);
    }

    private void initLampLayout() {
        ((XImageView) this.mPreloadLayout.findViewById(R.id.llu_top_img)).setImageResource(App.getMainControlPageCarBodyImg());
        initLluSw(this.mPreloadLayout);
        initLluBtns(this.mPreloadLayout);
        initOther(this.mPreloadLayout);
    }

    private void initOther(View parent) {
        boolean isSupportAtl = CarBaseConfig.getInstance().isSupportAtl();
        boolean isSupportLlu = CarBaseConfig.getInstance().isSupportLlu();
        if (isSupportAtl || isSupportLlu) {
            parent.findViewById(R.id.other_title_tv).setVisibility(0);
        }
        if (isSupportAtl) {
            parent.findViewById(R.id.atl_bg).setVisibility(0);
            View findViewById = parent.findViewById(R.id.atl_sw_item);
            findViewById.setVisibility(0);
            XSwitch xSwitch = (XSwitch) findViewById.findViewById(R.id.x_list_sw);
            this.mAtlSw = xSwitch;
            xSwitch.setVuiLabel(ResUtils.getString(R.string.title_atl));
            this.mAtlSw.setSelected(this.mAtlVm.isAtlEnabled());
            this.mAtlSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$LluFragment$3DP1X9yErGycReYBRaEsyqT4mO0
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    LluFragment.this.lambda$initOther$0$LluFragment(compoundButton, z);
                }
            });
            setLiveDataObserver(this.mAtlVm.getAtlSwitchData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$LluFragment$R7nmAiMIMnOHnskGSI_ut3HmKoQ
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LluFragment.this.lambda$initOther$1$LluFragment((Boolean) obj);
                }
            });
            AtlColorView atlColorView = (AtlColorView) parent.findViewById(R.id.atl_color_view);
            this.mAtlColorView = atlColorView;
            atlColorView.setVisibility(0);
            updateColorViewSingleColor();
            this.mAtlColorView.setOnStatusChangeListener(new AtlColorView.OnStatusChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.LluFragment.1
                @Override // com.xiaopeng.carcontrol.view.widget.AtlColorView.OnStatusChangeListener
                public void onDoubleColorChanged(int colorFirst, int colorSecond, int index) {
                }

                @Override // com.xiaopeng.carcontrol.view.widget.AtlColorView.OnStatusChangeListener
                public void onSlideColorChanged(int color) {
                }

                @Override // com.xiaopeng.carcontrol.view.widget.AtlColorView.OnStatusChangeListener
                public void onSingleColorChanged(int color) {
                    LluFragment.this.mAtlVm.setAtlSingleColor(AtlColor.fromAtlStatus(color));
                    if (LluFragment.this.mVcuVm.getGearLevelValue() == GearLevel.P || LluFragment.this.mVcuVm.getGearLevelValue() == GearLevel.N || ClickHelper.isFastClick((long) UILooperObserver.ANR_TRIGGER_TIME, false)) {
                        return;
                    }
                    NotificationHelper.getInstance().showToast(R.string.atl_color_adjust_prompt);
                }
            });
            setLiveDataObserver(this.mAtlVm.getAtlFirstColorData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$LluFragment$Fp15Vz37yZQDT3N9b0wDHGvD7GA
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LluFragment.this.lambda$initOther$2$LluFragment((AtlColor) obj);
                }
            });
        }
        if (isSupportLlu) {
            View findViewById2 = parent.findViewById(R.id.position_lamp_mode_item);
            findViewById2.setVisibility(0);
            XTabLayout xTabLayout = (XTabLayout) findViewById2.findViewById(R.id.position_lamp_mode_tab);
            this.mPositionLampTab = xTabLayout;
            updateXTabLayout(xTabLayout, !this.mLampVm.isParkLampIncludeFmB());
            this.mPositionLampTab.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.fragment.LluFragment.2
                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                    if (fromUser && ClickHelper.isFastClick(tabLayout.getId(), 1000L, false)) {
                        NotificationHelper.getInstance().showToast(R.string.big_lamp_mode_operation_fast);
                        return true;
                    }
                    return false;
                }

                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public void onTabChangeEnd(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                    if (fromUser) {
                        LluFragment.this.mLampVm.setParkLampIncludeFmB(index == 0);
                        StatisticUtils.sendStatistic(PageEnum.LAMP_PAGE, BtnEnum.LAMP_PAGE_SIDE_LIGHT_MODE_BTN, Integer.valueOf(index + 1));
                    }
                }
            });
            setLiveDataObserver(this.mLampVm.getParkLightRelatedFMBLightState(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$LluFragment$KTfiiC9UJQ8tgXA2GywudgP9f0I
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LluFragment.this.lambda$initOther$3$LluFragment((Boolean) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$initOther$0$LluFragment(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mAtlVm.setAtlSwitch(isChecked);
            if (this.mVcuVm.getGearLevelValue() == GearLevel.P || this.mVcuVm.getGearLevelValue() == GearLevel.N || ClickHelper.isFastClick((long) UILooperObserver.ANR_TRIGGER_TIME, false)) {
                return;
            }
            NotificationHelper.getInstance().showToast(R.string.atl_color_adjust_prompt);
        }
    }

    public /* synthetic */ void lambda$initOther$1$LluFragment(Boolean enable) {
        this.mAtlSw.setChecked(enable.booleanValue());
    }

    public /* synthetic */ void lambda$initOther$2$LluFragment(AtlColor atlColor) {
        updateColorViewSingleColor();
    }

    public /* synthetic */ void lambda$initOther$3$LluFragment(Boolean enabled) {
        if (enabled != null) {
            updateXTabLayout(this.mPositionLampTab, !enabled.booleanValue());
        }
    }

    private void updateColorViewSingleColor() {
        try {
            this.mAtlColorView.setSingleColor(AtlColor.fromAtlStatus(this.mAtlVm.getAtlSingleColor()).toAtlCmd());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLluSw(View parent) {
        if (CarBaseConfig.getInstance().isSupportLlu()) {
            parent.findViewById(R.id.llu_title_tv).setVisibility(0);
            boolean isLLuSwEnabled = this.mLluVm.isLLuSwEnabled();
            View findViewById = parent.findViewById(R.id.llu_sw_item);
            findViewById.setVisibility(0);
            XSwitch xSwitch = (XSwitch) findViewById.findViewById(R.id.x_list_sw);
            this.mLluSw = xSwitch;
            xSwitch.setVuiLabel(ResUtils.getString(R.string.llu_feature_title));
            this.mLluSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$LluFragment$NgKWQ1-H1ge6AHWV2OMylpnDTFQ
                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public final boolean onInterceptCheck(View view, boolean z) {
                    return LluFragment.lambda$initLluSw$4(view, z);
                }
            });
            this.mLluSw.setChecked(isLLuSwEnabled);
            setLiveDataObserver(this.mLluVm.getLluSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$LluFragment$0Jy5-cTusGPGdTSvtY8WOEep0lc
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LluFragment.this.lambda$initLluSw$5$LluFragment((Boolean) obj);
                }
            });
            View findViewById2 = parent.findViewById(R.id.llu_setting_item);
            findViewById2.setVisibility(0);
            final XButton xButton = (XButton) findViewById2.findViewById(R.id.x_list_button);
            xButton.setVuiLabel(ResUtils.getString(R.string.llu_setting_tv));
            VuiUtils.addHasFeedbackProp(xButton);
            xButton.setEnabled(isLLuSwEnabled);
            xButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$LluFragment$X0J69fhQkAHHbnEmgewlARKEv18
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LluFragment.this.lambda$initLluSw$6$LluFragment(view);
                }
            });
            this.mLluSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$LluFragment$i7d7rlETq7pqQ_7rZ54BNGJDaos
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    LluFragment.this.lambda$initLluSw$7$LluFragment(xButton, compoundButton, z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$initLluSw$4(View view, boolean b) {
        return view.isPressed() && ClickHelper.isFastClick(500L, false);
    }

    public /* synthetic */ void lambda$initLluSw$5$LluFragment(Boolean enabled) {
        if (enabled != null) {
            this.mLluSw.setChecked(enabled.booleanValue());
        }
    }

    public /* synthetic */ void lambda$initLluSw$6$LluFragment(View v) {
        showSettingPanel(GlobalConstant.ACTION.ACTION_SHOW_LLU_SETTING_PANEL);
    }

    public /* synthetic */ void lambda$initLluSw$7$LluFragment(final XButton lluSettingBtn, CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mLluVm.setLluMultiSwEnable(isChecked);
            StatisticUtils.sendStatistic(PageEnum.LAMP_PAGE, BtnEnum.LLU_SW, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
        lluSettingBtn.setEnabled(isChecked);
    }

    private void initLluBtns(View parent) {
        if (CarBaseConfig.getInstance().isSupportLlu() && CarBaseConfig.getInstance().isSupportLluPreview()) {
            parent.findViewById(R.id.llu_preview_bg).setVisibility(0);
            parent.findViewById(R.id.llu_preview_tv).setVisibility(0);
            View findViewById = parent.findViewById(R.id.xl_llu_dance);
            findViewById.setVisibility(0);
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$LluFragment$lUQI-KWx5akVbnyZQe4vJLoiBGA
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LluFragment.this.lambda$initLluBtns$8$LluFragment(view);
                }
            });
            TextView textView = (TextView) parent.findViewById(R.id.llu_preview_desc);
            this.mLluDescText = textView;
            textView.setVisibility(0);
            this.mLluDescText.setSelected(true);
            View findViewById2 = parent.findViewById(R.id.btn_llu_sayhi_container);
            findViewById2.setVisibility(0);
            this.mLluPlayingView = parent.findViewById(R.id.btn_llu_sayhi_ic);
            XButton xButton = (XButton) parent.findViewById(R.id.btn_llu_awake);
            VuiUtils.setStatefulButtonAttr(xButton, 1, new String[]{ResUtils.getString(R.string.llu_effect_awake_idle_label), ResUtils.getString(R.string.llu_effect_awake_playing_label)}, "SetValue|Click");
            xButton.setVisibility(0);
            XButton xButton2 = (XButton) parent.findViewById(R.id.btn_llu_sleep);
            VuiUtils.setStatefulButtonAttr(xButton2, 1, new String[]{ResUtils.getString(R.string.llu_effect_sleep_idle_label), ResUtils.getString(R.string.llu_effect_sleep_playing_label)}, "SetValue|Click");
            xButton2.setVisibility(0);
            XButton xButton3 = (XButton) parent.findViewById(R.id.btn_llu_dc_charge);
            VuiUtils.setStatefulButtonAttr(xButton3, 1, new String[]{ResUtils.getString(R.string.llu_effect_dc_charge_idle_label), ResUtils.getString(R.string.llu_effect_dc_charge_playing_label)}, "SetValue|Click");
            xButton3.setVisibility(0);
            XButton xButton4 = (XButton) parent.findViewById(R.id.btn_llu_ac_charge);
            VuiUtils.setStatefulButtonAttr(xButton4, 1, new String[]{ResUtils.getString(R.string.llu_effect_ac_charge_idle_label), ResUtils.getString(R.string.llu_effect_ac_charge_playing_label)}, "SetValue|Click");
            xButton4.setVisibility(0);
            XButton xButton5 = (XButton) parent.findViewById(R.id.btn_llu_find_car);
            VuiUtils.setStatefulButtonAttr(xButton5, 1, new String[]{ResUtils.getString(R.string.llu_effect_find_car_idle_label), ResUtils.getString(R.string.llu_effect_find_car_playing_label)}, "SetValue|Click");
            xButton5.setVisibility(0);
            this.mLluList.put(LluEffect.SayHi.ordinal(), findViewById2);
            this.mLluList.put(LluEffect.AwakeWait.ordinal(), xButton);
            this.mLluList.put(LluEffect.Sleep.ordinal(), xButton2);
            this.mLluList.put(LluEffect.DcCharged.ordinal(), xButton3);
            this.mLluList.put(LluEffect.AcCharged.ordinal(), xButton4);
            this.mLluList.put(LluEffect.FindCar.ordinal(), xButton5);
            HashMap hashMap = new HashMap();
            hashMap.put("hasFeedback", true);
            hashMap.put("skipMultipleAlready", true);
            for (int i = 0; i < this.mLluList.size(); i++) {
                View valueAt = this.mLluList.valueAt(i);
                LluEffect lluEffect = LluEffect.values()[this.mLluList.keyAt(i)];
                if (valueAt instanceof VuiView) {
                    VuiUtils.addProps((IVuiElement) valueAt, hashMap);
                    setVuiLabelUnSupportText(valueAt);
                }
                setItemClickListener(valueAt, lluEffect);
            }
            VuiUtils.addProps((IVuiElement) findViewById, hashMap);
            setVuiLabelUnSupportText(findViewById);
            refreshLluView(this.mLluVm.getLluPreviewStateData().getValue());
            setLiveDataObserver(this.mLluVm.getLluPreviewStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$LluFragment$kRfAOlyPO2lBuAUabvitDwEfR6Y
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LluFragment.this.refreshLluView((LluPreviewState) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$initLluBtns$8$LluFragment(View v) {
        if (onInterceptItemClickSimple(v)) {
            return;
        }
        Intent intent = new Intent(App.getInstance(), LluDanceActivityNew.class);
        intent.addFlags(ClientDefaults.MAX_MSG_SIZE);
        App.getInstance().startActivity(intent);
    }

    private void setItemClickListener(View view, LluEffect effect) {
        if (effect == LluEffect.SayHi) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$LluFragment$ZmS9u8wTqgqk7eGUDlgoy7-5kc4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    LluFragment.this.lambda$setItemClickListener$9$LluFragment(view2);
                }
            });
        } else {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.fragment.-$$Lambda$LluFragment$8xkusrrhfGoGLrrsyE0TyHEHTIo
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    LluFragment.this.lambda$setItemClickListener$10$LluFragment(view2);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setItemClickListener$9$LluFragment(View v) {
        if (onInterceptItemClickSimple(v)) {
            return;
        }
        showSettingPanel(GlobalConstant.ACTION.ACTION_SHOW_SAYHI_SETTING_PANEL);
        StatisticUtils.sendStatistic(PageEnum.LAMP_PAGE, BtnEnum.LAMP_PAGE_SAYHI_DISPLAY_BTN, new Object[0]);
    }

    public /* synthetic */ void lambda$setItemClickListener$10$LluFragment(View v) {
        if (onInterceptItemClick(v)) {
            return;
        }
        onItemClick(v);
    }

    private void setLluBtnEnable(View view, LluEffect effect, boolean enable) {
        view.setEnabled(enable);
        if (effect == LluEffect.SayHi) {
            view.findViewById(R.id.btn_llu_sayhi).setEnabled(enable);
        }
    }

    private void showSettingPanel(String action) {
        LogUtils.i(TAG, "showSettingPanel: " + action);
        ControlPanelManager.getInstance().show(action, 2048);
    }

    private void dismissSettingPanel(String... actions) {
        for (String str : actions) {
            if (str != null) {
                ControlPanelManager.getInstance().dismiss(str);
            }
        }
    }

    private boolean onInterceptItemClickSimple(View view) {
        boolean isLLuSwEnabled = this.mLluVm.isLLuSwEnabled();
        LogUtils.d(TAG, "onInterceptItemClick lluSwitch=" + isLLuSwEnabled);
        if (isLLuSwEnabled) {
            return false;
        }
        NotificationHelper.getInstance().showToast(R.string.llu_effect_sw_disable);
        vuiFeedbackClick(R.string.llu_effect_sw_disable, view);
        return true;
    }

    private boolean onInterceptItemClick(View view) {
        int i;
        boolean isLLuSwEnabled = this.mLluVm.isLLuSwEnabled();
        GearLevel gearLevelValue = this.mVcuVm.getGearLevelValue();
        boolean isTurnLampOn = this.mLampVm.isTurnLampOn();
        boolean rearFogLampState = this.mLampVm.getRearFogLampState();
        LogUtils.d(TAG, "onInterceptItemClick lluSwitch=" + isLLuSwEnabled + ", currentGear=" + gearLevelValue + ", turnLamp=" + isTurnLampOn + ", fogLamp=" + rearFogLampState);
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

    private void onItemClick(View view) {
        LluEffect effect = this.mLluVm.getLluPreviewStateData().getValue().getEffect();
        LluEffect[] values = LluEffect.values();
        SparseArray<View> sparseArray = this.mLluList;
        LluEffect lluEffect = values[sparseArray.keyAt(sparseArray.indexOfValue(view))];
        LogUtils.d(TAG, "onItemClick: runningEffect=" + effect + ", effect=" + lluEffect);
        if (!lluEffect.equals(effect)) {
            BtnEnum btnEnum = null;
            int i = AnonymousClass3.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[lluEffect.ordinal()];
            if (i == 1) {
                btnEnum = BtnEnum.LLU_EFFECT_SLEEP;
            } else if (i == 2) {
                btnEnum = BtnEnum.LLU_EFFECT_AWAKE;
            } else if (i == 3) {
                btnEnum = BtnEnum.LLU_EFFECT_DC_CHARGE;
            } else if (i == 4) {
                btnEnum = BtnEnum.LLU_EFFECT_AC_CHARGE;
            } else if (i == 5) {
                btnEnum = BtnEnum.LLU_EFFECT_FIND_CAR;
            }
            this.mLluVm.setLluEffectPreview(lluEffect);
            if (btnEnum != null) {
                StatisticUtils.sendStatistic(PageEnum.LAMP_PAGE, btnEnum, new Object[0]);
                return;
            }
            return;
        }
        this.mLluVm.stopLluEffectPreview();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.fragment.LluFragment$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect;

        static {
            int[] iArr = new int[LluEffect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect = iArr;
            try {
                iArr[LluEffect.Sleep.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.AwakeWait.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.DcCharged.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.AcCharged.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluEffect[LluEffect.FindCar.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshLluView(LluPreviewState state) {
        if (state == null) {
            LogUtils.d(TAG, "refreshLluView failed: LluPreviewState is null");
            return;
        }
        LluEffect effect = state.getEffect();
        LogUtils.i(TAG, "refreshLluView: state=" + state + ", runningEffect=" + effect);
        TextView textView = this.mLluDescText;
        if (textView != null) {
            textView.setText(effect == null ? R.string.llu_effect_default_desc : effect.getDescId());
        }
        this.mLluPlayingView.setVisibility((LluEffect.SayHi.equals(effect) || LluEffect.SayHi2.equals(effect) || LluEffect.SayHi3.equals(effect)) ? 0 : 8);
        for (int i = 0; i < this.mLluList.size(); i++) {
            LluEffect lluEffect = LluEffect.values()[this.mLluList.keyAt(i)];
            View valueAt = this.mLluList.valueAt(i);
            if (!LluEffect.SayHi.equals(lluEffect)) {
                boolean equals = lluEffect.equals(effect);
                valueAt.setSelected(equals);
                ((XButton) valueAt).setText(equals ? R.string.llu_effect_stop : lluEffect.getTitleId());
                VuiUtils.setStatefulButtonValue(valueAt, !equals);
            }
            setLluBtnEnable(valueAt, lluEffect, LluPreviewState.Idle.equals(state) || LluPreviewState.Previewing.equals(state));
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.VuiFragment, com.xiaopeng.vui.commons.IVuiSceneListener
    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        super.onVuiEvent(view, vuiEvent);
        if (this.mLluList.indexOfValue(view) >= 0) {
            ((VuiView) view).setPerformVuiAction(true);
            view.performClick();
        }
    }

    @Override // com.xiaopeng.carcontrol.view.fragment.BaseFragment, com.xiaopeng.carcontrol.view.fragment.VuiFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mLluList.clear();
    }
}
