package com.xiaopeng.carcontrol.viewmodel.account;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IAccountViewModel extends IBaseViewModel {
    public static final int SYNC_GROUP_CREATE_STATE_CREATED = 2;
    public static final int SYNC_GROUP_CREATE_STATE_CREATE_FAILED = -1;
    public static final int SYNC_GROUP_CREATE_STATE_CREATING = 1;
    public static final int SYNC_GROUP_CREATE_STATE_INIT = 0;

    boolean checkLogin();

    void closeSdsEnterPGear();

    String getCurrentSyncGroupId();

    String getCurrentSyncGroupName();

    void getTaskQrCode(long taskId);

    void getTaskResult(long taskId, boolean loop);

    void requestCreateNewSyncGroup();

    void updateApaSafeExamResult();

    void updateLccSafeExamResult();

    void updateSuperLccSafeExamResult();

    void updateSuperVpaSafeExamResult();

    void updateXngpSafeExamResult();
}
