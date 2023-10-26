package com.xiaopeng.carcontrol.viewmodel.meter;

import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.impl.McuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrolmodule.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public enum XKeyForCustomer {
    UnlockTrunk,
    SwitchMedia,
    ShowOff,
    DvrCapture,
    AutoPark,
    XPower,
    NraView,
    Disable;
    
    private static final String TAG = "XKeyForCustomer";
    private static List<XKeyForCustomer> sXkeyList;

    public static XKeyForCustomer fromSwsValue(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value != 2) {
                    if (value != 3) {
                        if (value != 4) {
                            switch (value) {
                                case 8:
                                    return UnlockTrunk;
                                case 9:
                                    return XPower;
                                case 10:
                                    return NraView;
                                default:
                                    LogUtils.d(TAG, "Unknown XKeyForCustomer value: " + value, false);
                                    return null;
                            }
                        }
                        return AutoPark;
                    }
                    return DvrCapture;
                }
                return ShowOff;
            }
            return SwitchMedia;
        }
        return Disable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.viewmodel.meter.XKeyForCustomer$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer;

        static {
            int[] iArr = new int[XKeyForCustomer.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer = iArr;
            try {
                iArr[XKeyForCustomer.Disable.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.UnlockTrunk.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.SwitchMedia.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.DvrCapture.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.AutoPark.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.ShowOff.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.XPower.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[XKeyForCustomer.NraView.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public int toSwsCmd() {
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 8;
            case 3:
                return 1;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 2;
            case 7:
                return 9;
            case 8:
                return 10;
            default:
                LogUtils.d(TAG, "Unknown XKeyForCustomer: " + this, false);
                return 0;
        }
    }

    public String getTitle() {
        int i;
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[ordinal()]) {
            case 1:
                i = R.string.custom_x_key_disable;
                break;
            case 2:
                i = R.string.custom_x_key_unlock_trunk;
                break;
            case 3:
                i = R.string.custom_x_key_switch_media;
                break;
            case 4:
                i = R.string.custom_x_key_dvr_capture;
                break;
            case 5:
                i = R.string.custom_x_key_auto_park;
                break;
            case 6:
                i = R.string.custom_x_key_show_off;
                break;
            case 7:
                i = R.string.custom_x_key_xpower;
                break;
            case 8:
                i = R.string.custom_x_key_narrow;
                break;
            default:
                throw new IllegalArgumentException("Unknown XKeyForCustomer: " + this);
        }
        return App.getInstance().getResources().getString(i);
    }

    public String getDesc() {
        int i;
        int i2 = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[ordinal()];
        if (i2 == 1) {
            i = R.string.custom_door_key_disable;
        } else if (i2 == 2) {
            i = R.string.custom_x_key_unlock_trunk_desc;
        } else if (i2 == 3) {
            i = R.string.custom_x_key_switch_media_desc;
        } else if (i2 == 5) {
            i = R.string.custom_x_key_auto_park_desc;
        } else if (i2 == 6) {
            i = R.string.custom_x_key_show_off_desc;
        } else if (i2 == 7) {
            i = R.string.custom_x_key_xpower_desc;
        } else {
            throw new IllegalArgumentException("Unknown XKeyForCustomer: " + this);
        }
        return App.getInstance().getResources().getString(i);
    }

    public static List<XKeyForCustomer> getXkeyList() {
        XKeyForCustomer[] values;
        if (sXkeyList == null) {
            sXkeyList = new ArrayList();
            for (XKeyForCustomer xKeyForCustomer : values()) {
                switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$XKeyForCustomer[xKeyForCustomer.ordinal()]) {
                    case 1:
                        if (CarBaseConfig.getInstance().isSupportXkeyDisable()) {
                            sXkeyList.add(xKeyForCustomer);
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (CarBaseConfig.getInstance().isSupportXUnlockTrunk()) {
                            sXkeyList.add(xKeyForCustomer);
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (CarBaseConfig.getInstance().isSupportSwitchMedia()) {
                            sXkeyList.add(xKeyForCustomer);
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        if (CarBaseConfig.getInstance().isSupportCiuConfig() && ((McuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_MCU_SERVICE)).getCiuState()) {
                            sXkeyList.add(xKeyForCustomer);
                            break;
                        }
                        break;
                    case 5:
                        if (CarBaseConfig.getInstance().isSupportAutoParkForXKey()) {
                            sXkeyList.add(xKeyForCustomer);
                            break;
                        } else {
                            break;
                        }
                    case 6:
                        if (CarBaseConfig.getInstance().isSupportXSayHi()) {
                            sXkeyList.add(xKeyForCustomer);
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        if (BaseFeatureOption.getInstance().isShowXkeyNraView()) {
                            sXkeyList.add(xKeyForCustomer);
                            break;
                        } else {
                            break;
                        }
                }
            }
        }
        return sXkeyList;
    }

    public static void addXKey(XKeyForCustomer key, int position) {
        List<XKeyForCustomer> list;
        if (key == null || (list = sXkeyList) == null || position < 0 || position > list.size()) {
            return;
        }
        sXkeyList.add(position, key);
    }

    public static void removeXKey(int position) {
        if (position < 0 || position >= sXkeyList.size()) {
            return;
        }
        sXkeyList.remove(position);
    }

    public static int getPosition(XKeyForCustomer value) {
        List<XKeyForCustomer> xkeyList = getXkeyList();
        for (int i = 0; i < xkeyList.size(); i++) {
            if (xkeyList.get(i) == value) {
                return i;
            }
        }
        return -1;
    }
}
