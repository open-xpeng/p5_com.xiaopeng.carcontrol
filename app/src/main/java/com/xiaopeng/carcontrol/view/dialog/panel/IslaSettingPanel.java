package com.xiaopeng.carcontrol.view.dialog.panel;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.direct.IPanelDirectDispatch;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuIslaSpdRange;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuViewModel;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XListMultiple;
import com.xiaopeng.xui.widget.XListTwo;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;

/* loaded from: classes2.dex */
public class IslaSettingPanel extends AbstractControlPanel implements IPanelDirectDispatch {
    private XDialog mConfirmDialog = null;
    private XSwitch mConfirmModeSw;
    private ScuViewModel mScuVm;
    private XTabLayout mSpdRangeTab;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public int getLayoutId() {
        return R.layout.layout_isla_setting;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.AbstractControlPanel
    protected int getPanelHeight() {
        return R.dimen.isla_setting_panel_height;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.AbstractControlPanel
    protected int getPanelWidth() {
        return R.dimen.x_dialog_xlarge_width;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel, com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return VuiManager.SCENE_ISLA_SETTING;
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
        this.mScuVm = (ScuViewModel) ViewModelManager.getInstance().getViewModelImpl(IScuViewModel.class);
    }

    public /* synthetic */ void lambda$onInitView$0$IslaSettingPanel(View v) {
        dismiss();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onInitView() {
        findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$IslaSettingPanel$txjOzexxjk-cJ3VXlktOlunVdnA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IslaSettingPanel.this.lambda$onInitView$0$IslaSettingPanel(view);
            }
        });
        XTabLayout xTabLayout = (XTabLayout) ((XListMultiple) findViewById(R.id.isla_spd_range_tab_item)).findViewById(R.id.isla_spd_range_tab);
        this.mSpdRangeTab = xTabLayout;
        xTabLayout.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.IslaSettingPanel.1
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout2, int index, boolean tabChange, boolean fromUser) {
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                if (fromUser) {
                    if (index == 0) {
                        IslaSettingPanel.this.confirmSpdRangeToLow();
                        return true;
                    } else if (index != 1) {
                        return true;
                    } else {
                        IslaSettingPanel.this.mScuVm.setIslaSpdRange(ScuIslaSpdRange.HIGH);
                        return false;
                    }
                }
                return false;
            }
        });
        XSwitch xSwitch = (XSwitch) ((XListTwo) findViewById(R.id.isla_confirm_sw)).findViewById(R.id.x_list_sw);
        this.mConfirmModeSw = xSwitch;
        xSwitch.setVuiLabel(ResUtils.getString(R.string.isla_confirm_mode_title));
        this.mConfirmModeSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$IslaSettingPanel$Dg0aYM8Ojb1jOvbrQOOaNpBr-8s
            @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
            public final boolean onInterceptCheck(View view, boolean z) {
                return IslaSettingPanel.this.lambda$onInitView$1$IslaSettingPanel(view, z);
            }
        });
    }

    public /* synthetic */ boolean lambda$onInitView$1$IslaSettingPanel(View buttonView, boolean checked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            if (checked) {
                this.mScuVm.setIslaConfirmMode(true);
                return false;
            }
            confirmIslaConfirmModeOff();
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected void onRegisterLiveData() {
        if (this.mSpdRangeTab != null) {
            setLiveDataObserver(this.mScuVm.getIslaSpdRangeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$IslaSettingPanel$g1AjxHARDipZ7HF5izBgcEIWBm8
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    IslaSettingPanel.this.lambda$onRegisterLiveData$2$IslaSettingPanel((ScuIslaSpdRange) obj);
                }
            });
        }
        if (this.mConfirmModeSw != null) {
            setLiveDataObserver(this.mScuVm.getIslaConfirmModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$IslaSettingPanel$cBTtnzfUEV6AcSpOQHphZBsVKBw
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    IslaSettingPanel.this.lambda$onRegisterLiveData$3$IslaSettingPanel((Boolean) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onRegisterLiveData$2$IslaSettingPanel(ScuIslaSpdRange spdRange) {
        if (spdRange != null) {
            this.mSpdRangeTab.selectTab(spdRange.toDisplayIndex(), false);
        }
    }

    public /* synthetic */ void lambda$onRegisterLiveData$3$IslaSettingPanel(Boolean mode) {
        if (mode != null) {
            this.mConfirmModeSw.setChecked(mode.booleanValue(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onRefresh() {
        super.onRefresh();
        XTabLayout xTabLayout = this.mSpdRangeTab;
        if (xTabLayout != null) {
            xTabLayout.selectTab(this.mScuVm.getIslaSpdRange().toDisplayIndex(), false);
        }
        XSwitch xSwitch = this.mConfirmModeSw;
        if (xSwitch != null) {
            xSwitch.setChecked(this.mScuVm.getIslaConfirmMode(), false);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void confirmSpdRangeToLow() {
        showDialog(R.string.isla_speed_range_title, R.string.isla_low_speed_range_confirm, ResUtils.getString(R.string.btn_cancel), null, ResUtils.getString(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$IslaSettingPanel$Y_nqHQfRqbLGsRW_9Xfx9K7EUf0
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                IslaSettingPanel.this.lambda$confirmSpdRangeToLow$4$IslaSettingPanel(xDialog, i);
            }
        }, null, VuiManager.SCENE_XPILOT_DIALOG_ISLA_SPD_RANGE);
    }

    public /* synthetic */ void lambda$confirmSpdRangeToLow$4$IslaSettingPanel(XDialog xDialog, int i) {
        this.mScuVm.setIslaSpdRange(ScuIslaSpdRange.LOW);
        XTabLayout xTabLayout = this.mSpdRangeTab;
        if (xTabLayout != null) {
            xTabLayout.selectTab(ScuIslaSpdRange.LOW.toDisplayIndex());
        }
    }

    private void confirmIslaConfirmModeOff() {
        showDialog(R.string.isla_confirm_mode_title, R.string.isla_confirm_mode_confirm, ResUtils.getString(R.string.btn_cancel), null, ResUtils.getString(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$IslaSettingPanel$VPURCym54Nc3OBYdDnBPFKq6HcE
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                IslaSettingPanel.this.lambda$confirmIslaConfirmModeOff$5$IslaSettingPanel(xDialog, i);
            }
        }, null, VuiManager.SCENE_XPILOT_DIALOG_ISLA_CONFIRM_MODE);
    }

    public /* synthetic */ void lambda$confirmIslaConfirmModeOff$5$IslaSettingPanel(XDialog xDialog, int i) {
        this.mScuVm.setIslaConfirmMode(false);
        XSwitch xSwitch = this.mConfirmModeSw;
        if (xSwitch != null) {
            xSwitch.setChecked(false);
        }
    }

    protected void showDialog(int titleId, int messageId, String negative, XDialogInterface.OnClickListener negativeListener, String positive, XDialogInterface.OnClickListener positiveListener, final DialogInterface.OnDismissListener onDismissListener, String sceneId) {
        dismissDialog();
        XDialog positiveButton = new XDialog(this.mContext, (int) R.style.x_dialog_style).setTitle(titleId).setMessage(messageId).setNegativeButton(negative, negativeListener).setPositiveButton(positive, positiveListener);
        positiveButton.setSystemDialog(2048);
        if (!TextUtils.isEmpty(sceneId)) {
            positiveButton.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$IslaSettingPanel$RsQhaaL7PofClBrZjsaForQEXAc
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    IslaSettingPanel.lambda$showDialog$6(onDismissListener, dialogInterface);
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
        if (BaseFeatureOption.getInstance().isSupportChangeableDialogTitleSize()) {
            ((TextView) this.mConfirmDialog.getContentView().findViewById(R.id.x_dialog_title)).setTextSize(33.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showDialog$6(final DialogInterface.OnDismissListener onDismissListener, DialogInterface dialog) {
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
