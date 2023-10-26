package com.xiaopeng.carcontrol.view.dialog.panel.customkey;

import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.viewmodel.meter.XKeyForCustomer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class SteerKeyControlPanel extends AbstractCustomKeyControlPanel {
    private XKeyForCustomer mCurrentKey;
    private XKeyForCustomer mPreviousKey;

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.customkey.AbstractCustomKeyControlPanel
    protected int getBackgroundId() {
        return R.drawable.img_steelwheel_shotcut;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel, com.xiaopeng.speech.vui.Helper.IVuiSceneHelper
    public String getSceneId() {
        return VuiManager.SCENE_XKEY_SETTING;
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.customkey.AbstractCustomKeyControlPanel
    protected int getTitleId() {
        return R.string.custom_x_key_title_for_d21;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.panel.customkey.AbstractCustomKeyControlPanel, com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onInitViewModel() {
        super.onInitViewModel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onRefresh() {
        XKeyForCustomer xKeyForCustomerValue = this.mMeterVm.getXKeyForCustomerValue();
        this.mPreviousKey = xKeyForCustomerValue;
        if (xKeyForCustomerValue != null) {
            this.mAdapter.setPosition(XKeyForCustomer.getPosition(this.mPreviousKey), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.panel.customkey.AbstractCustomKeyControlPanel
    public void setCustomKey(int position) {
        this.mCurrentKey = XKeyForCustomer.getXkeyList().get(position);
        this.mMeterVm.setXKeyForCustomerValue(this.mCurrentKey);
        StatisticUtils.sendStatistic(PageEnum.SETTING_PAGE, BtnEnum.WHEER_CUSTOME_BTN_DATA, Integer.valueOf(getStatisticForCustomKey(this.mCurrentKey)));
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.panel.customkey.AbstractCustomKeyControlPanel
    protected List<String> getTitleList() {
        ArrayList arrayList = new ArrayList();
        for (XKeyForCustomer xKeyForCustomer : XKeyForCustomer.getXkeyList()) {
            arrayList.add(xKeyForCustomer.getTitle());
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onDismiss() {
        XKeyForCustomer xKeyForCustomer = this.mCurrentKey;
        if (xKeyForCustomer != null && this.mPreviousKey != xKeyForCustomer) {
            LogUtils.d(this.TAG, "onDismiss: " + this.mCurrentKey, false);
            NotificationHelper.getInstance().showToast(String.format(ResUtils.getString(R.string.custom_x_key_format_d21), this.mCurrentKey.getTitle()));
        }
        this.mCurrentKey = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.view.dialog.panel.customkey.SteerKeyControlPanel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer;

        static {
            int[] iArr = new int[XKeyForCustomer.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer = iArr;
            try {
                iArr[XKeyForCustomer.UnlockTrunk.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.SwitchMedia.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.AutoPark.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.ShowOff.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.Disable.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private int getStatisticForCustomKey(XKeyForCustomer key) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[key.ordinal()];
        int i2 = 1;
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    i2 = 5;
                    if (i != 4) {
                        return i != 5 ? -1 : 0;
                    }
                }
                return i2;
            }
            return 3;
        }
        return 4;
    }
}
