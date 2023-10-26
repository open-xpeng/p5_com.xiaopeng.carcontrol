package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface ISfsController extends IBaseCarController<Callback> {
    public static final int SFS_CHANNEL_1 = 0;
    public static final int SFS_CHANNEL_2 = 1;
    public static final int SFS_CHANNEL_3 = 2;
    public static final int SFS_MAX_CHANNEL = 2;
    public static final int SFS_TYPE_1 = 1;
    public static final int SFS_TYPE_2 = 2;
    public static final int SFS_TYPE_3 = 3;
    public static final int SFS_TYPE_4 = 4;
    public static final int SFS_TYPE_NULL = 0;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onSfsChannelChanged(int channel) {
        }

        default void onSfsSwChanged(boolean enabled) {
        }

        default void onSfsTypeChanged(int[] typesInChannel) {
        }
    }

    int getActivatedChannel();

    int[] getTypesInChannel();

    boolean isSwEnabled();

    void setActiveChannel(int sfsChannel);

    void setSwEnable(boolean enable);
}
