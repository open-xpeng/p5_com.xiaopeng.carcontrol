package com.xiaopeng.carcontrol.view.dialog.panel;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.direct.IPanelDirectDispatch;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.XpuViewModel;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;

/* loaded from: classes2.dex */
public class NgpSettingPanel extends AbstractControlPanel implements IPanelDirectDispatch {
    private XDialog mConfirmDialog = null;
    private XSwitch mCrossBarriersSw;
    private XTabLayout mRemindModeTab;
    private ScuViewModel mScuVm;
    private XSwitch mTipWindowSw;
    private XSwitch mTruckOffsetSw;
    private XSwitch mVoiceChangeLaneSw;
    private XpuViewModel mXpuVm;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public int getLayoutId() {
        return R.layout.layout_ngp_setting;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.AbstractControlPanel
    protected int getPanelHeight() {
        return R.dimen.ngp_setting_height;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.AbstractControlPanel
    protected int getPanelWidth() {
        return R.dimen.x_dialog_xlarge_width;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel, com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return VuiManager.SCENE_NGP_SETTING;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean isPanelSupportScroll() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean needInitVm() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean shouldDisableVui() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitViewModel() {
        ViewModelManager viewModelManager = ViewModelManager.getInstance();
        this.mScuVm = (ScuViewModel) viewModelManager.getViewModelImpl(IScuViewModel.class);
        if (CarBaseConfig.getInstance().isSupportCrossBarriers()) {
            this.mXpuVm = (XpuViewModel) viewModelManager.getViewModelImpl(IXpuViewModel.class);
        }
    }

    public /* synthetic */ void lambda$onInitView$0$NgpSettingPanel(View v) {
        dismiss();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitView() {
        findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$PEEycMI71v1Lu-x8fIhYt4hQbEQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NgpSettingPanel.this.lambda$onInitView$0$NgpSettingPanel(view);
            }
        });
        XListTwo xListTwo = (XListTwo) findViewById(R.id.lcc_cross_barriers_sw);
        if (xListTwo != null && CarBaseConfig.getInstance().isSupportCrossBarriers()) {
            ((TextView) findViewById(R.id.title)).setText(R.string.ngp_lcc_setting_title);
            xListTwo.setVisibility(0);
            XSwitch xSwitch = (XSwitch) xListTwo.findViewById(R.id.x_list_sw);
            this.mCrossBarriersSw = xSwitch;
            xSwitch.setVuiLabel(ResUtils.getString(R.string.lcc_setting_cross_barriers_vui_label));
            this.mCrossBarriersSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.NgpSettingPanel.1
                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public boolean onInterceptCheck(View view, boolean isChecked) {
                    if ((view.isPressed() || NgpSettingPanel.this.isVuiAction(view, false)) && isChecked) {
                        NgpSettingPanel.this.confirmCrossBarriersFunc();
                        return true;
                    }
                    return false;
                }

                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public boolean onInterceptClickEvent(View v) {
                    return ClickHelper.isFastClick(v.getId(), 500L, false);
                }
            });
            this.mCrossBarriersSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$87_Kv8171HPplY7wVvl72cl9t_M
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    NgpSettingPanel.this.lambda$onInitView$1$NgpSettingPanel(compoundButton, z);
                }
            });
            this.mCrossBarriersSw.setChecked(this.mXpuVm.getLccCrossBarriersSw() == ScuResponse.ON);
        }
        XListTwo xListTwo2 = (XListTwo) findViewById(R.id.ngp_truck_offset_sw);
        if (xListTwo2 != null) {
            XSwitch xSwitch2 = (XSwitch) xListTwo2.findViewById(R.id.x_list_sw);
            this.mTruckOffsetSw = xSwitch2;
            xSwitch2.setVuiLabel(ResUtils.getString(R.string.ngp_setting_truck_offset_vui_label));
            this.mTruckOffsetSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.NgpSettingPanel.2
                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public boolean onInterceptCheck(View view, boolean isChecked) {
                    if ((view.isPressed() || NgpSettingPanel.this.isVuiAction(view, false)) && isChecked) {
                        NgpSettingPanel.this.confirmTruckOffsetFunc();
                        return true;
                    }
                    return false;
                }

                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public boolean onInterceptClickEvent(View v) {
                    return ClickHelper.isFastClick(v.getId(), 500L, false);
                }
            });
            this.mTruckOffsetSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$ClbUl5IThYMkcqXY63j846OwBCs
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    NgpSettingPanel.this.lambda$onInitView$2$NgpSettingPanel(compoundButton, z);
                }
            });
            this.mTruckOffsetSw.setChecked(this.mScuVm.getNgpTruckOffsetSw());
        }
        XListTwo xListTwo3 = (XListTwo) findViewById(R.id.ngp_tip_window_sw);
        if (xListTwo3 != null) {
            XSwitch xSwitch3 = (XSwitch) xListTwo3.findViewById(R.id.x_list_sw);
            this.mTipWindowSw = xSwitch3;
            xSwitch3.setVuiLabel(ResUtils.getString(R.string.ngp_setting_tip_window_vui_label));
            this.mTipWindowSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.NgpSettingPanel.3
                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public boolean onInterceptCheck(View view, boolean isChecked) {
                    if ((view.isPressed() || NgpSettingPanel.this.isVuiAction(view, false)) && isChecked) {
                        NgpSettingPanel.this.confirmTipWindowFunc();
                        return true;
                    }
                    return false;
                }

                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public boolean onInterceptClickEvent(View v) {
                    return ClickHelper.isFastClick(v.getId(), 500L, false);
                }
            });
            this.mTipWindowSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$v4KSd6jsrCr3IspwlbgGJhVqpPY
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    NgpSettingPanel.this.lambda$onInitView$3$NgpSettingPanel(compoundButton, z);
                }
            });
            this.mTipWindowSw.setChecked(this.mScuVm.getNgpTipsWindowSw());
        }
        XListTwo xListTwo4 = (XListTwo) findViewById(R.id.ngp_voice_change_lane_sw);
        if (xListTwo4 != null) {
            XSwitch xSwitch4 = (XSwitch) xListTwo4.findViewById(R.id.x_list_sw);
            this.mVoiceChangeLaneSw = xSwitch4;
            xSwitch4.setVuiLabel(ResUtils.getString(R.string.ngp_setting_voice_change_lane_vui_label));
            this.mVoiceChangeLaneSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.NgpSettingPanel.4
                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public boolean onInterceptCheck(View view, boolean isChecked) {
                    if ((view.isPressed() || NgpSettingPanel.this.isVuiAction(view, false)) && isChecked) {
                        NgpSettingPanel.this.confirmVoiceChangeLaneFunc();
                        return true;
                    }
                    return false;
                }

                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public boolean onInterceptClickEvent(View v) {
                    return ClickHelper.isFastClick(v.getId(), 500L, false);
                }
            });
            this.mVoiceChangeLaneSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$fe9kQryuFkSQhLt0aei7Z7k0VkM
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    NgpSettingPanel.this.lambda$onInitView$4$NgpSettingPanel(compoundButton, z);
                }
            });
            this.mVoiceChangeLaneSw.setChecked(this.mScuVm.getNgpVoiceChangeLane());
        }
        View findViewById = findViewById(R.id.ngp_remind_mode_layout);
        if (findViewById != null) {
            XTabLayout xTabLayout = (XTabLayout) findViewById.findViewById(R.id.ngp_remind_mode_tab);
            this.mRemindModeTab = xTabLayout;
            xTabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.NgpSettingPanel.5
                @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
                public void onTabChangeEnd(XTabLayout xTabLayout2, int index, boolean tabChange, boolean fromUser) {
                    if (fromUser) {
                        if (index == 0 || index == 1) {
                            NgpSettingPanel.this.mScuVm.setNgpRemindMode(index);
                        }
                    }
                }
            });
            updateNgpRemindMode(Integer.valueOf(this.mScuVm.getNgpRemindMode()));
        }
    }

    public /* synthetic */ void lambda$onInitView$1$NgpSettingPanel(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mXpuVm.setLccCrossBarriersSw(isChecked);
        }
    }

    public /* synthetic */ void lambda$onInitView$2$NgpSettingPanel(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mScuVm.setNgpTruckOffsetSw(isChecked);
        }
    }

    public /* synthetic */ void lambda$onInitView$3$NgpSettingPanel(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mScuVm.setNgpTipsWindow(isChecked);
        }
    }

    public /* synthetic */ void lambda$onInitView$4$NgpSettingPanel(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mScuVm.setNgpVoiceChangeLane(isChecked);
        }
    }

    @Override // com.xiaopeng.carcontrol.direct.IPanelDirectDispatch
    public void dispatchDirectData(Uri url) {
        this.mDirectIntent = null;
        if (url != null) {
            this.mDirectIntent = new Intent();
            this.mDirectIntent.setData(url);
        }
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onRegisterLiveData() {
        if (CarBaseConfig.getInstance().isSupportCrossBarriers()) {
            setLiveDataObserver(this.mXpuVm.getLccCrossBarriersData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$-u-px6w-p7bZtZZHgQTgGNyhj7Q
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    NgpSettingPanel.this.lambda$onRegisterLiveData$5$NgpSettingPanel((ScuResponse) obj);
                }
            });
        }
        setLiveDataObserver(this.mScuVm.getNgpTruckOffsetData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$UdcnGFKn3xqxmPNKu4wDMduVGqM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NgpSettingPanel.this.lambda$onRegisterLiveData$6$NgpSettingPanel((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mScuVm.getNgpTipWindowData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$vKZ6d7pSr7Rj5NYUjpLsPyRfe00
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NgpSettingPanel.this.lambda$onRegisterLiveData$7$NgpSettingPanel((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mScuVm.getNgpVoiceChangeLaneData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$w6XJyHD956VYDRBl7LKIhB_i-dU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NgpSettingPanel.this.lambda$onRegisterLiveData$8$NgpSettingPanel((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mScuVm.getNgpRemindData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$EYx1RKH7_koR-shSgFFfzVyUuOY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NgpSettingPanel.this.updateNgpRemindMode((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$onRegisterLiveData$5$NgpSettingPanel(ScuResponse scuResponse) {
        if (scuResponse != null) {
            this.mCrossBarriersSw.setChecked(scuResponse == ScuResponse.ON);
        }
    }

    public /* synthetic */ void lambda$onRegisterLiveData$6$NgpSettingPanel(Boolean enabled) {
        if (enabled != null) {
            this.mTruckOffsetSw.setChecked(enabled.booleanValue());
        }
    }

    public /* synthetic */ void lambda$onRegisterLiveData$7$NgpSettingPanel(Boolean enabled) {
        if (enabled != null) {
            this.mTipWindowSw.setChecked(enabled.booleanValue());
        }
    }

    public /* synthetic */ void lambda$onRegisterLiveData$8$NgpSettingPanel(Boolean enabled) {
        if (enabled != null) {
            this.mVoiceChangeLaneSw.setChecked(enabled.booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onRefresh() {
        if (this.mRemindModeTab != null) {
            updateNgpRemindMode(Integer.valueOf(this.mScuVm.getNgpRemindMode()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNgpRemindMode(Integer mode) {
        if (mode == null || mode.intValue() < 0 || mode.intValue() > 1) {
            LogUtils.w(this.TAG, "updateNgpRemindMode with invalid remind mode: " + mode, false);
        } else {
            this.mRemindModeTab.selectTab(mode.intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void confirmCrossBarriersFunc() {
        showDialog(R.string.lcc_setting_cross_barriers_title, R.string.lcc_setting_cross_barriers_confirm_text, ResUtils.getString(R.string.btn_cancel), null, ResUtils.getString(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$j1w-C2fQrUWlXM4A6ZsMZJwHCEg
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                NgpSettingPanel.this.lambda$confirmCrossBarriersFunc$9$NgpSettingPanel(xDialog, i);
            }
        }, null, VuiManager.SCENE_XPILOT_DIALOG_LCC_CROSS_BARRIERS);
    }

    public /* synthetic */ void lambda$confirmCrossBarriersFunc$9$NgpSettingPanel(XDialog xDialog, int i) {
        this.mXpuVm.setLccCrossBarriersSw(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void confirmTruckOffsetFunc() {
        showDialog(R.string.ngp_setting_truck_offset_title, R.string.ngp_setting_truck_offset_confirm_text, ResUtils.getString(R.string.btn_cancel), null, ResUtils.getString(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$DlukTzBqLCYWhyYa1Jd6CLvTr28
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                NgpSettingPanel.this.lambda$confirmTruckOffsetFunc$10$NgpSettingPanel(xDialog, i);
            }
        }, null, VuiManager.SCENE_XPILOT_DIALOG_NGP_TRUCK_OFFSET);
    }

    public /* synthetic */ void lambda$confirmTruckOffsetFunc$10$NgpSettingPanel(XDialog xDialog, int i) {
        this.mScuVm.setNgpTruckOffsetSw(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void confirmTipWindowFunc() {
        showDialog(R.string.ngp_setting_tip_window_title, R.string.ngp_setting_tip_window_confirm_text, ResUtils.getString(R.string.btn_cancel), null, ResUtils.getString(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$SVEjooIrcavfAJWdshLSrjYBB-8
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                NgpSettingPanel.this.lambda$confirmTipWindowFunc$11$NgpSettingPanel(xDialog, i);
            }
        }, null, VuiManager.SCENE_XPILOT_DIALOG_NGP_TIP_WINDOW);
    }

    public /* synthetic */ void lambda$confirmTipWindowFunc$11$NgpSettingPanel(XDialog xDialog, int i) {
        this.mScuVm.setNgpTipsWindow(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void confirmVoiceChangeLaneFunc() {
        showDialog(R.string.ngp_setting_voice_change_lane_title, R.string.ngp_setting_voice_change_lane_confirm_text, ResUtils.getString(R.string.btn_cancel), null, ResUtils.getString(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$2M9EGu0gcJImD_8Nz83kAiDgasA
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                NgpSettingPanel.this.lambda$confirmVoiceChangeLaneFunc$12$NgpSettingPanel(xDialog, i);
            }
        }, null, VuiManager.SCENE_XPILOT_DIALOG_NGP_VOICE_CHANGE_LANE);
    }

    public /* synthetic */ void lambda$confirmVoiceChangeLaneFunc$12$NgpSettingPanel(XDialog xDialog, int i) {
        this.mScuVm.setNgpVoiceChangeLane(true);
    }

    protected void showDialog(int titleId, int messageId, String negative, XDialogInterface.OnClickListener negativeListener, String positive, XDialogInterface.OnClickListener positiveListener, final DialogInterface.OnDismissListener onDismissListener, String sceneId) {
        dismissDialog();
        XDialog positiveButton = new XDialog(this.mContext).setTitle(titleId).setMessage(messageId).setNegativeButton(negative, negativeListener).setPositiveButton(positive, positiveListener);
        positiveButton.setSystemDialog(2048);
        if (!TextUtils.isEmpty(sceneId)) {
            positiveButton.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$NgpSettingPanel$YyZYkcRUaOP3um1R_D5wLhryWsM
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    NgpSettingPanel.lambda$showDialog$13(onDismissListener, dialogInterface);
                }
            });
        } else if (onDismissListener != null) {
            positiveButton.getDialog().setOnDismissListener(onDismissListener);
        }
        if (!TextUtils.isEmpty(sceneId)) {
            VuiManager.instance().initVuiDialog(positiveButton, this.mContext, sceneId);
        }
        positiveButton.show();
        this.mConfirmDialog = positiveButton;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showDialog$13(final DialogInterface.OnDismissListener onDismissListener, DialogInterface dialog) {
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    protected void dismissDialog() {
        XDialog xDialog = this.mConfirmDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mConfirmDialog = null;
        }
    }
}
