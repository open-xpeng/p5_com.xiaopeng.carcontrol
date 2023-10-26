package com.xiaopeng.carcontrol.view.dialog.panel;

import android.view.View;
import android.widget.CompoundButton;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.view.dialog.panel.customkey.AbstractCustomKeyControlPanel;
import com.xiaopeng.carcontrol.viewmodel.meter.DoorKeyForCustomer;
import com.xiaopeng.xui.widget.XSwitch;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DoorKeyControlPanel extends AbstractCustomKeyControlPanel {
    private XSwitch mBossKeySwitch;
    private DoorKeyForCustomer mCurrentKey;
    private DoorKeyForCustomer mPreviousKey;

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.customkey.AbstractCustomKeyControlPanel
    protected int getBackgroundId() {
        return R.drawable.img_dialog_btn_door;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.panel.customkey.AbstractCustomKeyControlPanel, com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public int getLayoutId() {
        return R.layout.layout_door_key_panel;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel, com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return VuiManager.SCENE_DOORKEY_SETTING;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.customkey.AbstractCustomKeyControlPanel
    protected int getTitleId() {
        return R.string.custom_door_key_title;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.panel.customkey.AbstractCustomKeyControlPanel, com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onInitView() {
        super.onInitView();
        View findViewById = findViewById(R.id.boss_key_sw);
        if (findViewById != null) {
            if (CarBaseConfig.getInstance().isSupportBossKey()) {
                XSwitch xSwitch = (XSwitch) findViewById.findViewById(R.id.x_list_sw);
                this.mBossKeySwitch = xSwitch;
                if (xSwitch != null) {
                    xSwitch.setVuiLabel(ResUtils.getString(R.string.car_setting_boss_key_title));
                    this.mBossKeySwitch.setOnInterceptListener(new XSwitch.OnInterceptListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$DoorKeyControlPanel$iQMXQUvb58WqgJzbs210rPnbvU0
                        @Override // com.xiaopeng.xui.widget.XSwitch.OnInterceptListener
                        public final boolean onInterceptCheck(View view, boolean z) {
                            boolean isFastClick;
                            isFastClick = ClickHelper.isFastClick();
                            return isFastClick;
                        }
                    });
                    this.mBossKeySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.-$$Lambda$DoorKeyControlPanel$JCGy_mCqHCmQCYJYsbHn1Pdz6NM
                        @Override // android.widget.CompoundButton.OnCheckedChangeListener
                        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                            DoorKeyControlPanel.this.lambda$onInitView$1$DoorKeyControlPanel(compoundButton, z);
                        }
                    });
                    this.mBossKeySwitch.setChecked(this.mMeterVm.getDoorBossKeySw());
                    return;
                }
                return;
            }
            findViewById.setVisibility(8);
            this.mBossKeySwitch = null;
        }
    }

    public /* synthetic */ void lambda$onInitView$1$DoorKeyControlPanel(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed() || isVuiAction(buttonView)) {
            this.mMeterVm.setDoorBossKeySw(isChecked);
            StatisticUtils.sendStatistic(PageEnum.SETTING_PSN_DOOR_PAGE, BtnEnum.SETTING_PSN_DOOR_PAGE_DOOR_BOSS_KEY_BTN, Integer.valueOf(StatisticUtils.getSwitchOnOff(isChecked)));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onRefresh() {
        DoorKeyForCustomer doorKeyForCustomerValue = this.mMeterVm.getDoorKeyForCustomerValue();
        this.mPreviousKey = doorKeyForCustomerValue;
        if (doorKeyForCustomerValue != null) {
            this.mAdapter.setPosition(DoorKeyForCustomer.getPosition(this.mPreviousKey), false);
        }
        XSwitch xSwitch = this.mBossKeySwitch;
        if (xSwitch != null) {
            xSwitch.setChecked(this.mMeterVm.getDoorBossKeySw());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.panel.customkey.AbstractCustomKeyControlPanel
    public void setCustomKey(int position) {
        this.mCurrentKey = DoorKeyForCustomer.getDoorKeyList().get(position);
        this.mMeterVm.setDoorKeyForCustomerValue(this.mCurrentKey);
        StatisticUtils.sendStatistic(PageEnum.SETTING_PSN_DOOR_PAGE, BtnEnum.SETTING_PSN_DOOR_PAGE_DOOR_KEY_SEL_BTN, Integer.valueOf(getStatisticForDoorKey(this.mCurrentKey)));
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.customkey.AbstractCustomKeyControlPanel
    protected List<String> getTitleList() {
        ArrayList arrayList = new ArrayList();
        for (DoorKeyForCustomer doorKeyForCustomer : DoorKeyForCustomer.getDoorKeyList()) {
            arrayList.add(doorKeyForCustomer.getTitle());
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onDismiss() {
        super.onDismiss();
        if (this.mPreviousKey != this.mCurrentKey) {
            LogUtils.d(this.TAG, "onDismiss: " + this.mCurrentKey, false);
            NotificationHelper.getInstance().showToast(String.format(ResUtils.getString(R.string.custom_door_key_format), this.mCurrentKey.getTitle()));
        }
        this.mCurrentKey = null;
        XSwitch xSwitch = this.mBossKeySwitch;
        if (xSwitch == null || !xSwitch.isChecked()) {
            return;
        }
        SpeechHelper.getInstance().speak(ResUtils.getString(R.string.car_setting_boss_key_summary));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.dialog.panel.DoorKeyControlPanel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer;

        static {
            int[] iArr = new int[DoorKeyForCustomer.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer = iArr;
            try {
                iArr[DoorKeyForCustomer.Speech.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[DoorKeyForCustomer.Mute.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[DoorKeyForCustomer.SwitchMedia.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[DoorKeyForCustomer.Disable.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private int getStatisticForDoorKey(DoorKeyForCustomer key) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[key.ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    i2 = 4;
                    if (i != 4) {
                        return -1;
                    }
                }
            }
        }
        return i2;
    }
}
