package com.xiaopeng.carcontrol.view.scene;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.UIUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasEffect;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasViewModel;
import com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XListMultiple;
import com.xiaopeng.xui.widget.XListSingle;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;

/* loaded from: classes2.dex */
public class AvasControlScene extends AbstractScene {
    private XDialog mAvasDialog;
    private XTabLayout mAvasEffectTab;
    private XListMultiple mAvasEffectTabItem;
    private XSwitch mAvasSw;
    private AvasViewModel mAvasVm;
    private boolean mIsAvasSwConfirmed;
    private final boolean mIsSupportAvasOff;
    private final boolean mIsSupportNewAvas;

    public AvasControlScene(String sceneId, View parent, LifecycleOwner owner) {
        super(sceneId, parent, owner);
        this.mIsAvasSwConfirmed = false;
        this.mIsSupportNewAvas = CarBaseConfig.getInstance().isNewAvasArch();
        this.mIsSupportAvasOff = BaseFeatureOption.getInstance().isSupportAvasOff();
    }

    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    protected void initViewModels() {
        this.mAvasVm = (AvasViewModel) ViewModelManager.getInstance().getViewModelImpl(IAvasViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    protected void initViews() {
        XListSingle xListSingle = (XListSingle) findViewById(R.id.low_avas_sw_item);
        this.mAvasSw = (XSwitch) xListSingle.findViewById(R.id.x_list_sw);
        XListMultiple xListMultiple = (XListMultiple) findViewById(R.id.low_avas_tab_item);
        this.mAvasEffectTabItem = xListMultiple;
        this.mAvasEffectTab = (XTabLayout) xListMultiple.findViewById(R.id.avas_tab);
        this.mAvasEffectTabItem.setBackground(ResUtils.getDrawable(this.mIsSupportNewAvas ? R.drawable.sub_item_bg : R.drawable.sub_item_bottom_bg));
        if (this.mIsSupportNewAvas) {
            xListSingle.setVisibility(8);
            this.mAvasEffectTabItem.setText(ResUtils.getString(R.string.avas_settings_title));
            if (this.mIsSupportAvasOff) {
                this.mAvasEffectTab.addTab(ResUtils.getString(R.string.switch_off), 0);
                this.mAvasEffectTab.addTab(ResUtils.getString(R.string.avas_effect_1), 1);
                this.mAvasEffectTab.addTab(ResUtils.getString(R.string.avas_effect_2), 2);
                this.mAvasEffectTab.addTab(ResUtils.getString(R.string.avas_effect_3), 3);
            } else {
                this.mAvasEffectTab.addTab(ResUtils.getString(R.string.avas_effect_1), 0);
                this.mAvasEffectTab.addTab(ResUtils.getString(R.string.avas_effect_2), 1);
                this.mAvasEffectTab.addTab(ResUtils.getString(R.string.avas_effect_3), 2);
            }
        } else if (BaseFeatureOption.getInstance().isSupportShowAvasSwitch()) {
            xListSingle.setVisibility(0);
            this.mAvasEffectTabItem.setText(null);
            ViewGroup.LayoutParams layoutParams = this.mAvasEffectTabItem.getLayoutParams();
            layoutParams.height = UIUtils.dp2px(getContext(), (int) ResUtils.getDimension(R.dimen.avas_tab_item_without_title_height));
            this.mAvasEffectTabItem.setLayoutParams(layoutParams);
            this.mAvasEffectTab.addTab(ResUtils.getString(R.string.avas_effect_1), 0);
            this.mAvasEffectTab.addTab(ResUtils.getString(R.string.avas_effect_2), 1);
            this.mAvasEffectTab.addTab(ResUtils.getString(R.string.avas_effect_3), 2);
            this.mAvasEffectTab.addTab(ResUtils.getString(R.string.avas_effect_4), 3);
        }
        if (!this.mIsSupportNewAvas) {
            this.mAvasSw.setVuiLabel(ResUtils.getString(R.string.avas_settings_title));
            this.mAvasSw.setChecked(this.mAvasVm.isLowSpdEnabled());
            this.mAvasSw.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.scene.-$$Lambda$AvasControlScene$UK_JFynE7jXwoTH3G3MYLOLb1iY
                @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                public final boolean onInterceptCheck(View view, boolean z) {
                    return AvasControlScene.this.lambda$initViews$0$AvasControlScene(view, z);
                }
            });
        }
        this.mAvasEffectTab.setSoundEffectsEnabled(false);
        this.mAvasEffectTab.setOnTabChangeListener(new XTabLayout.OnTabChangeListenerAdapter() { // from class: com.xiaopeng.carcontrol.view.scene.AvasControlScene.1
            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListenerAdapter, com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public boolean onInterceptTabChange(XTabLayout tabLayout, int index, boolean tabChange, boolean fromUser) {
                if (AvasControlScene.this.mIsSupportAvasOff && fromUser && index == 0 && AvasControlScene.this.mIsSupportNewAvas) {
                    AvasControlScene.this.showAvasDialog();
                    StatisticUtils.sendStatistic(PageEnum.SETTING_OTHER_PAGE, BtnEnum.SETTING_OTHERR_PAGE_AVAS_BTN, 1);
                    return true;
                }
                return super.onInterceptTabChange(tabLayout, index, tabChange, fromUser);
            }

            @Override // com.xiaopeng.xui.widget.XTabLayout.OnTabChangeListener
            public void onTabChangeEnd(XTabLayout xTabLayout, int index, boolean tabChange, boolean fromUser) {
                if (fromUser) {
                    if (!AvasControlScene.this.mIsSupportAvasOff) {
                        index++;
                    }
                    if (index != 0) {
                        if (index != 1) {
                            if (index != 2) {
                                if (index == 3) {
                                    if (AvasControlScene.this.mIsSupportNewAvas) {
                                        AvasControlScene.this.mAvasVm.setAvasLowSpdEffect(AvasEffect.SoundEffect3);
                                        SoundHelper.play(SoundHelper.PATH_AVAS_LOW_SPEED_3, true, false);
                                    } else {
                                        AvasControlScene.this.mAvasVm.setAvasLowSpdEffect(AvasEffect.SoundEffect4);
                                        SoundHelper.play(SoundHelper.PATH_AVAS_LOW_SPEED_4, true, false);
                                    }
                                }
                            } else if (AvasControlScene.this.mIsSupportNewAvas) {
                                AvasControlScene.this.mAvasVm.setAvasLowSpdEffect(AvasEffect.SoundEffect2);
                                SoundHelper.play(SoundHelper.PATH_AVAS_LOW_SPEED_2, true, false);
                            } else {
                                AvasControlScene.this.mAvasVm.setAvasLowSpdEffect(AvasEffect.SoundEffect3);
                                SoundHelper.play(SoundHelper.PATH_AVAS_LOW_SPEED_3, true, false);
                            }
                        } else if (AvasControlScene.this.mIsSupportNewAvas) {
                            AvasControlScene.this.mAvasVm.setAvasLowSpdEffect(AvasEffect.SoundEffect1);
                            SoundHelper.play(SoundHelper.PATH_AVAS_LOW_SPEED_1, true, false);
                        } else {
                            AvasControlScene.this.mAvasVm.setAvasLowSpdEffect(AvasEffect.SoundEffect2);
                            SoundHelper.play(SoundHelper.PATH_AVAS_LOW_SPEED_2, true, false);
                        }
                    } else if (!AvasControlScene.this.mIsSupportNewAvas) {
                        AvasControlScene.this.mAvasVm.setAvasLowSpdEffect(AvasEffect.SoundEffect1);
                        SoundHelper.play(SoundHelper.PATH_AVAS_LOW_SPEED_1, true, false);
                    } else {
                        SoundHelper.play("", false, false);
                    }
                    StatisticUtils.sendStatistic(PageEnum.SETTING_OTHER_PAGE, BtnEnum.SETTING_OTHERR_PAGE_AVAS_BTN, Integer.valueOf(index + 1));
                }
            }
        });
        setLdo(this.mAvasVm.getLowSpdSwData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.scene.-$$Lambda$AvasControlScene$mAvnwpyxkxi3HDRvxwV64gYC0Mw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AvasControlScene.this.lambda$initViews$1$AvasControlScene((Boolean) obj);
            }
        });
        setLdo(this.mAvasVm.getAvasLowSpdEffectData(), new Observer() { // from class: com.xiaopeng.carcontrol.view.scene.-$$Lambda$AvasControlScene$QTm4e7wccvfTm-P-q85AqGnBudY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AvasControlScene.this.updateAvasTabByEffect((AvasEffect) obj);
            }
        });
        updateAvasBySw(this.mAvasVm.isLowSpdEnabled());
    }

    public /* synthetic */ boolean lambda$initViews$0$AvasControlScene(View buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            if (!isChecked) {
                showAvasDialog();
                StatisticUtils.sendStatistic(PageEnum.SETTING_OTHER_PAGE, BtnEnum.SETTING_OTHERR_PAGE_AVAS_BTN, 1);
            } else {
                this.mAvasVm.setLowSpdEnable(true);
                StatisticUtils.sendStatistic(PageEnum.SETTING_PAGE, BtnEnum.LOW_SPEED_SOUND_SWITCH, Integer.valueOf(StatisticUtils.getSwitchOnOff(true)));
            }
            return true;
        }
        return false;
    }

    public /* synthetic */ void lambda$initViews$1$AvasControlScene(Boolean enabled) {
        if (enabled != null) {
            if (this.mIsSupportNewAvas) {
                updateAvasBySw(enabled.booleanValue());
                return;
            }
            this.mAvasSw.setChecked(enabled.booleanValue());
            this.mAvasEffectTab.setEnabled(enabled.booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    public void onPause() {
        super.onPause();
        XDialog xDialog = this.mAvasDialog;
        if (xDialog == null || !xDialog.isShowing()) {
            return;
        }
        this.mAvasDialog.dismiss();
    }

    private void updateAvasBySw(boolean enabled) {
        if (!this.mIsSupportNewAvas) {
            this.mAvasEffectTab.setEnabled(enabled);
        } else if (enabled) {
            int lowSpdEffect = this.mAvasVm.getLowSpdEffect();
            if (lowSpdEffect != AvasEffect.SoundEffect4.toAvasType()) {
                updateXTabLayout(this.mAvasEffectTab, this.mAvasVm.getLowSpdEffect());
            } else {
                LogUtils.w(this.TAG, "Invalid AVAS effect type: " + lowSpdEffect, false);
            }
        } else {
            updateXTabLayout(this.mAvasEffectTab, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAvasTabByEffect(AvasEffect effect) {
        if (effect == null) {
            return;
        }
        if (this.mIsSupportNewAvas) {
            if (this.mAvasVm.isLowSpdEnabled()) {
                if (effect != AvasEffect.SoundEffect4) {
                    XTabLayout xTabLayout = this.mAvasEffectTab;
                    boolean z = this.mIsSupportAvasOff;
                    int avasType = effect.toAvasType();
                    if (!z) {
                        avasType--;
                    }
                    updateXTabLayout(xTabLayout, avasType);
                    return;
                }
                return;
            }
            LogUtils.w(this.TAG, "Invalid AVAS effect type: " + effect, false);
            return;
        }
        this.mAvasEffectTab.selectTab(effect.ordinal());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAvasDialog() {
        if (this.mAvasDialog == null) {
            XDialog positiveButton = new XDialog(getContext()).setTitle(R.string.avas_settings_title).setMessage(R.string.avas_low_spd_desc).setNegativeButton(ResUtils.getString(R.string.btn_cancel), (XDialogInterface.OnClickListener) null).setPositiveButton(ResUtils.getString(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.scene.-$$Lambda$AvasControlScene$jVQoke20dv3o4f8BspkfM7JZXxw
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i) {
                    AvasControlScene.this.lambda$showAvasDialog$2$AvasControlScene(xDialog, i);
                }
            });
            this.mAvasDialog = positiveButton;
            positiveButton.setSystemDialog(2048);
            this.mAvasDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.scene.-$$Lambda$AvasControlScene$qNhnOKMMole-6LfmlJWXe9kC9Uk
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    AvasControlScene.this.lambda$showAvasDialog$3$AvasControlScene(dialogInterface);
                }
            });
        }
        if (this.mAvasDialog.isShowing()) {
            return;
        }
        this.mAvasDialog.show();
        VuiManager.instance().initVuiDialog(this.mAvasDialog, getContext(), VuiManager.SCENE_AVAS_CLOSE_SETTING);
    }

    public /* synthetic */ void lambda$showAvasDialog$2$AvasControlScene(XDialog xDialog, int i) {
        LogUtils.d(this.TAG, "mAvasDialog Close");
        this.mIsAvasSwConfirmed = true;
        this.mAvasVm.setLowSpdEnable(false);
        SoundHelper.play("", false, false);
        if (this.mIsSupportNewAvas) {
            this.mAvasEffectTab.selectTab(0);
        } else {
            this.mAvasEffectTab.setEnabled(false);
        }
    }

    public /* synthetic */ void lambda$showAvasDialog$3$AvasControlScene(DialogInterface dialog) {
        if (this.mIsSupportNewAvas) {
            return;
        }
        if (!this.mIsAvasSwConfirmed) {
            this.mAvasSw.setChecked(true);
        } else {
            this.mIsAvasSwConfirmed = false;
        }
    }

    @Override // com.xiaopeng.carcontrol.view.scene.AbstractScene
    public void onConfigurationChanged(Configuration newConfig) {
        this.mAvasEffectTabItem.setBackground(ResUtils.getDrawable(this.mIsSupportNewAvas ? R.drawable.sub_item_bg : R.drawable.sub_item_bottom_bg));
    }
}
