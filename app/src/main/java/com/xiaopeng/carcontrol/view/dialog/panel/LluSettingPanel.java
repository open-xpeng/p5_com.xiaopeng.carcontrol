package com.xiaopeng.carcontrol.view.dialog.panel;

import android.view.View;
import android.widget.CompoundButton;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel;
import com.xiaopeng.xui.widget.XSwitch;

/* loaded from: classes2.dex */
public class LluSettingPanel extends AbstractControlPanel {
    private XSwitch mLluAwakeSw;
    private XSwitch mLluChargeSw;
    private XSwitch mLluSleepSw;
    private LluViewModel mLluVm;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public int getLayoutId() {
        return R.layout.layout_llu_setting_panel;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel, com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return VuiManager.SCENE_LLU_SETTING_CONTROL;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean needInitVm() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitViewModel() {
        this.mLluVm = (LluViewModel) ViewModelManager.getInstance().getViewModelImpl(ILluViewModel.class);
    }

    public /* synthetic */ void lambda$onInitView$0$LluSettingPanel(View v) {
        dismiss();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitView() {
        findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSettingPanel$5SMA8Y6K4CcrBoS4c3XHIAN9O58
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LluSettingPanel.this.lambda$onInitView$0$LluSettingPanel(view);
            }
        });
        XSwitch xSwitch = (XSwitch) findViewById(R.id.llu_awake_sw).findViewById(R.id.x_list_sw);
        this.mLluAwakeSw = xSwitch;
        xSwitch.setVuiLabel(ResUtils.getString(R.string.llu_awake_sw_title));
        this.mLluAwakeSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSettingPanel$bmlESlOszlCMfIFKrf4Z7YApuiw
            @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
            public final boolean onInterceptCheck(View view, boolean z) {
                return LluSettingPanel.lambda$onInitView$1(view, z);
            }
        });
        this.mLluAwakeSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSettingPanel$kXjvykqsnU1z6-ID8aq-4IV2EJ8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                LluSettingPanel.this.lambda$onInitView$2$LluSettingPanel(compoundButton, z);
            }
        });
        XSwitch xSwitch2 = (XSwitch) findViewById(R.id.llu_sleep_sw).findViewById(R.id.x_list_sw);
        this.mLluSleepSw = xSwitch2;
        xSwitch2.setVuiLabel(ResUtils.getString(R.string.llu_sleep_sw_title));
        this.mLluSleepSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSettingPanel$Cv6rKwEl_QDeBsZwQb5NZjMu3uA
            @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
            public final boolean onInterceptCheck(View view, boolean z) {
                return LluSettingPanel.lambda$onInitView$3(view, z);
            }
        });
        this.mLluSleepSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSettingPanel$TiPueySIWCUp7oQlLruFh4B7F4c
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                LluSettingPanel.this.lambda$onInitView$4$LluSettingPanel(compoundButton, z);
            }
        });
        XSwitch xSwitch3 = (XSwitch) findViewById(R.id.llu_charge_sw).findViewById(R.id.x_list_sw);
        this.mLluChargeSw = xSwitch3;
        xSwitch3.setVuiLabel(ResUtils.getString(R.string.llu_charge_sw_title));
        this.mLluChargeSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSettingPanel$YcZQy1WgFmiJnQnQjHSFXDF259c
            @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
            public final boolean onInterceptCheck(View view, boolean z) {
                return LluSettingPanel.lambda$onInitView$5(view, z);
            }
        });
        this.mLluChargeSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSettingPanel$O8tKCqxqg1BZG51pG3iijmeB7n4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                LluSettingPanel.this.lambda$onInitView$6$LluSettingPanel(compoundButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$onInitView$1(View view, boolean b) {
        return view.isPressed() && ClickHelper.isFastClick(500L, false);
    }

    public /* synthetic */ void lambda$onInitView$2$LluSettingPanel(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mLluVm.setLluWakeWaitSwitch(isChecked);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$onInitView$3(View view, boolean b) {
        return view.isPressed() && ClickHelper.isFastClick(500L, false);
    }

    public /* synthetic */ void lambda$onInitView$4$LluSettingPanel(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mLluVm.setLluSleepSwitch(isChecked);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$onInitView$5(View view, boolean b) {
        return view.isPressed() && ClickHelper.isFastClick(500L, false);
    }

    public /* synthetic */ void lambda$onInitView$6$LluSettingPanel(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mLluVm.setLluChargingSwitch(isChecked);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onRefresh() {
        this.mLluAwakeSw.setChecked(this.mLluVm.isLluWakeWaitEnable());
        this.mLluSleepSw.setChecked(this.mLluVm.isLluSleepEnable());
        this.mLluChargeSw.setChecked(this.mLluVm.isLluChargingEnable());
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onRegisterLiveData() {
        setLiveDataObserver(this.mLluVm.getLluAwakeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSettingPanel$hBzsgxL7jCFSqaYO47qZ7VxIMkQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluSettingPanel.this.lambda$onRegisterLiveData$7$LluSettingPanel((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mLluVm.getLluSleepData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSettingPanel$BOlQVynngtPniHu9SDco2EFbSAk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluSettingPanel.this.lambda$onRegisterLiveData$8$LluSettingPanel((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mLluVm.getLluChargeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$LluSettingPanel$Jcvr9smmMUaTGuZUASujHiRoo9Y
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluSettingPanel.this.lambda$onRegisterLiveData$9$LluSettingPanel((Boolean) obj);
            }
        });
    }

    public /* synthetic */ void lambda$onRegisterLiveData$7$LluSettingPanel(Boolean enabled) {
        if (enabled != null) {
            this.mLluAwakeSw.setChecked(enabled.booleanValue());
        }
    }

    public /* synthetic */ void lambda$onRegisterLiveData$8$LluSettingPanel(Boolean enabled) {
        if (enabled != null) {
            this.mLluSleepSw.setChecked(enabled.booleanValue());
        }
    }

    public /* synthetic */ void lambda$onRegisterLiveData$9$LluSettingPanel(Boolean enabled) {
        if (enabled != null) {
            this.mLluChargeSw.setChecked(enabled.booleanValue());
        }
    }
}
