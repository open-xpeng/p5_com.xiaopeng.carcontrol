package com.xiaopeng.carcontrol.view.scene;

import android.content.res.Configuration;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.UserBookHelper;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.WiperInterval;
import com.xiaopeng.carcontrol.viewmodel.ciu.CiuViewModel;
import com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XListMultiple;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;
import com.xiaopeng.xui.widget.XTextView;
import java.util.Locale;

/* loaded from: classes2.dex */
public class WiperControlScene extends AbstractScene {
    private XDialog mAutoWiperDialog;
    private CiuViewModel mCiuVm;
    private CarBodyViewModel mSceneViewModel;
    private XDialog mWiperInfoDialog;
    private XTabLayout mWiperLevelTab;

    public WiperControlScene(String sceneId, View parent, LifecycleOwner owner) {
        super(sceneId, parent, owner);
    }

    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    protected void initViewModels() {
        this.mSceneViewModel = (CarBodyViewModel) ViewModelManager.getInstance().getViewModelImpl(ICarBodyViewModel.class);
        this.mCiuVm = (CiuViewModel) ViewModelManager.getInstance().getViewModelImpl(ICiuViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    protected void initViews() {
        XListMultiple xListMultiple = (XListMultiple) findViewById(R.id.wipe_interval_tab_item);
        if (BaseFeatureOption.getInstance().isSupportWiperSenCfgShow()) {
            xListMultiple.setText(ResUtils.getString(R.string.wiper_auto_gear_title));
            xListMultiple.setTextSub(null);
        } else {
            xListMultiple.setText(ResUtils.getString(R.string.wiper_gear_title));
            xListMultiple.setTextSub(ResUtils.getString(R.string.wiper_gear_summary));
        }
        XTabLayout xTabLayout = (XTabLayout) xListMultiple.findViewById(R.id.auto_wiper_sensitivity_mode_tab);
        this.mWiperLevelTab = xTabLayout;
        xTabLayout.setVuiLabel(ResUtils.getString(R.string.wiper_auto_gear_title));
        boolean isCiuExist = this.mCiuVm.isCiuExist();
        if (isCiuExist && this.mSceneViewModel.isCiuRainSwEnable()) {
            this.mWiperLevelTab.addTab(R.drawable.ic_wiper_gear_auto, ResUtils.getString(R.string.wiper_gear_auto));
        }
        this.mWiperLevelTab.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.scene.WiperControlScene.1
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                if (fromUser) {
                    if (ClickHelper.isFastClick(tabLayout.getId(), 500L, false)) {
                        return true;
                    }
                    if (CarBaseConfig.getInstance().isSupportWiperRepair() && WiperControlScene.this.mSceneViewModel.getWiperRepairMode()) {
                        NotificationHelper.getInstance().showToast(R.string.switch_off_wiper_repair_mode_title);
                        return true;
                    } else if (index == 4) {
                        WiperControlScene.this.showWiperAutoGearConfirmDialog();
                        return true;
                    }
                }
                return false;
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout2, int index, boolean tabChange, boolean fromUser) {
                if (fromUser) {
                    WiperControlScene.this.mSceneViewModel.setWiperInterval(WiperInterval.getIndexValue(index));
                    StatisticUtils.sendStatistic(PageEnum.SETTING_OTHER_PAGE, BtnEnum.SETTING_OTHER_PAGE_AUTO_WIPER_BTN, Integer.valueOf(index + 1));
                }
            }
        });
        WiperInterval wiperIntervalValue = this.mSceneViewModel.getWiperIntervalValue();
        if (wiperIntervalValue != null) {
            this.mWiperLevelTab.selectTab(wiperIntervalValue.ordinal());
        }
        setLdo(this.mSceneViewModel.getWiperIntervalData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.scene.-$$Lambda$WiperControlScene$mKtoyMhPxaEpW-uzkSXrbE0IxaE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                WiperControlScene.this.lambda$initViews$0$WiperControlScene((WiperInterval) obj);
            }
        });
        XImageView xImageView = (XImageView) xListMultiple.findViewById(R.id.wipe_interval_tab_item_info);
        if (isCiuExist) {
            xImageView.setVisibility(0);
            xImageView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.scene.-$$Lambda$WiperControlScene$uh6qsdDvbzLqoLxGpaJT907FJ80
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    WiperControlScene.this.lambda$initViews$1$WiperControlScene(view);
                }
            });
            return;
        }
        xImageView.setVisibility(8);
    }

    public /* synthetic */ void lambda$initViews$0$WiperControlScene(WiperInterval interval) {
        if (interval != null) {
            this.mWiperLevelTab.selectTab(interval.ordinal());
        }
    }

    public /* synthetic */ void lambda$initViews$1$WiperControlScene(View v) {
        showWiperInfoPanel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    public void onResume() {
        WiperInterval wiperIntervalValue;
        super.onResume();
        if (this.mWiperLevelTab == null || (wiperIntervalValue = this.mSceneViewModel.getWiperIntervalValue()) == null) {
            return;
        }
        this.mWiperLevelTab.selectTab(wiperIntervalValue.ordinal());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    public void onPause() {
        super.onPause();
        XDialog xDialog = this.mAutoWiperDialog;
        if (xDialog != null && xDialog.isShowing()) {
            this.mAutoWiperDialog.dismiss();
        }
        XDialog xDialog2 = this.mWiperInfoDialog;
        if (xDialog2 == null || !xDialog2.isShowing()) {
            return;
        }
        this.mWiperInfoDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showWiperAutoGearConfirmDialog() {
        if (this.mAutoWiperDialog == null) {
            this.mAutoWiperDialog = new XDialog(getContext(), R.style.XDialogView_Large).setTitle(R.string.wiper_gear_auto_setting_dialog_title).setNegativeButton(R.string.btn_cancel, (XDialogInterface.OnClickListener) null).setPositiveButton(getPositiveBtnText(ResUtils.getString(R.string.btn_confirm), 10), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.scene.-$$Lambda$WiperControlScene$kJd8awUshlHpy4t_eCxp5L7onE0
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    WiperControlScene.this.lambda$showWiperAutoGearConfirmDialog$2$WiperControlScene(xDialog, i);
                }
            }).setPositiveButtonEnable(false);
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_xpilot_info_dialog_view, (ViewGroup) null);
            inflate.findViewById(R.id.help_pic_1).setVisibility(8);
            XTextView xTextView = (XTextView) inflate.findViewById(R.id.help_text_1);
            xTextView.setVisibility(0);
            xTextView.setText(R.string.wiper_gear_auto_setting_dialog_content);
            this.mAutoWiperDialog.setCustomView(inflate, false);
            this.mAutoWiperDialog.setSystemDialog(2008);
        }
        if (this.mAutoWiperDialog.isShowing()) {
            return;
        }
        this.mAutoWiperDialog.show();
        Window window = this.mAutoWiperDialog.getDialog().getWindow();
        if (window != null) {
            window.setElevation(0.0f);
        }
        new CountDownTimer(10300L, 1000L) { // from class: com.xiaopeng.carcontrol.view.scene.WiperControlScene.2
            @Override // android.os.CountDownTimer
            public void onTick(long millisUntilFinished) {
                WiperControlScene.this.mAutoWiperDialog.setPositiveButton(WiperControlScene.this.getPositiveBtnText(ResUtils.getString(R.string.btn_confirm), ((int) millisUntilFinished) / 1000));
                WiperControlScene.this.mAutoWiperDialog.setPositiveButtonEnable(false);
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                WiperControlScene.this.mAutoWiperDialog.setPositiveButton(R.string.btn_confirm);
                WiperControlScene.this.mAutoWiperDialog.setPositiveButtonEnable(true);
            }
        }.start();
    }

    public /* synthetic */ void lambda$showWiperAutoGearConfirmDialog$2$WiperControlScene(XDialog xDialog1, int i) {
        this.mSceneViewModel.setWiperInterval(WiperInterval.Auto);
        this.mWiperLevelTab.selectTab(4);
    }

    private void showWiperInfoPanel() {
        XDialog xDialog = this.mWiperInfoDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mWiperInfoDialog = null;
        }
        XDialog xDialog2 = new XDialog(getContext(), R.style.XDialogView_Large);
        this.mWiperInfoDialog = xDialog2;
        xDialog2.setSystemDialog(2008);
        this.mWiperInfoDialog.setCustomView(LayoutInflater.from(getContext()).inflate(R.layout.layout_wiper_interval_tips_dialog_view, (ViewGroup) null), false);
        this.mWiperInfoDialog.getDialog().setOnKeyListener(null);
        this.mWiperInfoDialog.setPositiveButton(R.string.btn_close, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.scene.-$$Lambda$WiperControlScene$VSbyhfXghwxcQWCM4T1Nad0PpvU
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog3, int i) {
                WiperControlScene.this.lambda$showWiperInfoPanel$3$WiperControlScene(xDialog3, i);
            }
        });
        if (UserBookHelper.isSupport()) {
            this.mWiperInfoDialog.setNegativeButton(R.string.btn_user_manual, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.scene.-$$Lambda$WiperControlScene$8VVAi1UZ8GM2ZYSaeOqhDNbpFw4
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog3, int i) {
                    UserBookHelper.openUserBook(ResUtils.getString(R.string.wiper_search_um_keyword), true);
                }
            });
        }
        initWiperTipsDialog();
        this.mWiperInfoDialog.show();
        Window window = this.mWiperInfoDialog.getDialog().getWindow();
        if (window != null) {
            window.setElevation(0.0f);
        }
    }

    public /* synthetic */ void lambda$showWiperInfoPanel$3$WiperControlScene(XDialog xDialog, int i) {
        this.mWiperInfoDialog.dismiss();
    }

    private void initWiperTipsDialog() {
        this.mWiperInfoDialog.getContentView().findViewById(R.id.scrollView).scrollTo(0, 0);
        this.mWiperInfoDialog.setTitle(R.string.wiper_gear_title);
        this.mWiperInfoDialog.setCloseVisibility(true);
        XSwitch xSwitch = (XSwitch) this.mWiperInfoDialog.getContentView().findViewById(R.id.wiper_interval_tips_sw_item).findViewById(R.id.x_list_sw);
        xSwitch.setChecked(this.mSceneViewModel.isCiuRainSwEnable());
        xSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.scene.-$$Lambda$WiperControlScene$rc91xS1LqZJ8jK-r_KSemRG1hnM
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                WiperControlScene.this.lambda$initWiperTipsDialog$5$WiperControlScene(compoundButton, z);
            }
        });
    }

    public /* synthetic */ void lambda$initWiperTipsDialog$5$WiperControlScene(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            this.mSceneViewModel.setCiuRainSwEnable(isChecked);
            if (isChecked && this.mWiperLevelTab.getTabCount() == 4) {
                this.mWiperLevelTab.addTab(R.drawable.ic_wiper_gear_auto, ResUtils.getString(R.string.wiper_gear_auto));
            } else if (isChecked || this.mWiperLevelTab.getTabCount() != 5) {
            } else {
                if (this.mWiperLevelTab.getSelectedTabIndex() == 4) {
                    this.mWiperLevelTab.selectTab(2);
                    this.mSceneViewModel.setWiperInterval(WiperInterval.Fast);
                }
                this.mWiperLevelTab.removeTab(4);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence getPositiveBtnText(CharSequence positiveBtnText, int delayTimeInSecond) {
        return String.format(Locale.getDefault(), "%1s(%2ds)", positiveBtnText, Integer.valueOf(delayTimeInSecond));
    }

    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!XThemeManager.isThemeChanged(newConfig) || this.mWiperLevelTab.getTabCount() <= 4) {
            return;
        }
        View childAt = this.mWiperLevelTab.getChildAt(4);
        if (childAt instanceof XImageView) {
            ((XImageView) childAt).setImageResource(R.drawable.ic_wiper_gear_auto);
        }
    }
}
