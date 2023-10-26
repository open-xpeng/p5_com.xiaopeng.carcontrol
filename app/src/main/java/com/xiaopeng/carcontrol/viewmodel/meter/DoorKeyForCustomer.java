package com.xiaopeng.carcontrol.viewmodel.meter;

import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrolmodule.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public enum DoorKeyForCustomer {
    Speech,
    Mute,
    SwitchMedia,
    ShowOff,
    Disable;
    
    private static final String TAG = "DoorKeyForCustomer";
    private static List<DoorKeyForCustomer> sDoorKeyList;

    public static DoorKeyForCustomer fromSwsValue(int value) {
        if (value != 0) {
            if (value != 1) {
                if (value != 2) {
                    if (value != 3) {
                        if (value == 4) {
                            return ShowOff;
                        }
                        LogUtils.w(TAG, "Unknown DoorKeyForCustomer value: " + value, false);
                        return Disable;
                    }
                    return SwitchMedia;
                }
                return Mute;
            }
            return Speech;
        }
        return Disable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.viewmodel.meter.DoorKeyForCustomer$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer;

        static {
            int[] iArr = new int[DoorKeyForCustomer.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer = iArr;
            try {
                iArr[DoorKeyForCustomer.Disable.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[DoorKeyForCustomer.Speech.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[DoorKeyForCustomer.Mute.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[DoorKeyForCustomer.SwitchMedia.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[DoorKeyForCustomer.ShowOff.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public int toIcmCmd() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i != 5) {
                            LogUtils.w(TAG, "Unknown DoorKeyForCustomer: " + this, false);
                            return 0;
                        }
                        return 4;
                    }
                    return 3;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public String getTitle() {
        int i;
        int i2 = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[ordinal()];
        if (i2 == 1) {
            i = R.string.custom_door_key_disable;
        } else if (i2 == 2) {
            i = R.string.custom_door_key_speech;
        } else if (i2 == 3) {
            i = R.string.custom_door_key_mute;
        } else if (i2 == 4) {
            i = R.string.custom_door_key_switch_media;
        } else if (i2 == 5) {
            i = R.string.custom_door_key_show_off;
        } else {
            throw new IllegalArgumentException("Unknown DoorKeyForCustomer: " + this);
        }
        return App.getInstance().getResources().getString(i);
    }

    public String getDesc() {
        int i;
        int i2 = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[ordinal()];
        if (i2 == 1) {
            i = R.string.custom_door_key_disable_desc;
        } else if (i2 == 2) {
            i = R.string.custom_door_key_speech_desc;
        } else if (i2 == 3) {
            i = R.string.custom_door_key_mute_desc;
        } else if (i2 == 4) {
            i = R.string.custom_door_key_switch_media_desc;
        } else if (i2 == 5) {
            i = R.string.custom_door_key_show_off_desc;
        } else {
            throw new IllegalArgumentException("Unknown DoorKeyForCustomer: " + this);
        }
        return App.getInstance().getResources().getString(i);
    }

    public static List<DoorKeyForCustomer> getDoorKeyList() {
        DoorKeyForCustomer[] values;
        if (sDoorKeyList == null) {
            sDoorKeyList = new ArrayList();
            for (DoorKeyForCustomer doorKeyForCustomer : values()) {
                int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$meter$DoorKeyForCustomer[doorKeyForCustomer.ordinal()];
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            if (i == 4 && CarBaseConfig.getInstance().isSupportSwitchMedia()) {
                                sDoorKeyList.add(doorKeyForCustomer);
                            }
                        }
                    } else if (BaseFeatureOption.getInstance().isShowDoorKeySpeech()) {
                        sDoorKeyList.add(doorKeyForCustomer);
                    }
                }
                sDoorKeyList.add(doorKeyForCustomer);
            }
        }
        return sDoorKeyList;
    }

    public static int getPosition(DoorKeyForCustomer value) {
        List<DoorKeyForCustomer> doorKeyList = getDoorKeyList();
        for (int i = 0; i < doorKeyList.size(); i++) {
            if (doorKeyList.get(i) == value) {
                return i;
            }
        }
        return -1;
    }
}
