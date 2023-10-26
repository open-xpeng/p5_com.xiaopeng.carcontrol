package com.xiaopeng.carcontrol.viewmodel.account;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.internal.util.CollectionUtils;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.IpcRouterService;
import com.xiaopeng.carcontrol.bean.SyncGroupEventMsg;
import com.xiaopeng.carcontrol.bean.SyncGroupInfo;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.carmanager.controller.IScuController;
import com.xiaopeng.carcontrol.carmanager.controller.IXpuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.direct.ElementDirectManager;
import com.xiaopeng.carcontrol.direct.IElementDirect;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.model.ApaExamModel;
import com.xiaopeng.carcontrol.model.CngpExamModel;
import com.xiaopeng.carcontrol.model.DataSyncModel;
import com.xiaopeng.carcontrol.model.LccExamModel;
import com.xiaopeng.carcontrol.model.MemParkExamModel;
import com.xiaopeng.carcontrol.model.NgpExamModel;
import com.xiaopeng.carcontrol.model.SuperLccExamModel;
import com.xiaopeng.carcontrol.model.SuperVpaExamModel;
import com.xiaopeng.carcontrol.model.XngpExamModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.SafeExamUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.account.bean.ExamUrlResult;
import com.xiaopeng.carcontrol.viewmodel.account.bean.TaskResultRequest;
import com.xiaopeng.carcontrol.viewmodel.account.response.ExamInfoResponseCallback;
import com.xiaopeng.carcontrol.viewmodel.account.response.ExamResultResponseCallback;
import com.xiaopeng.carcontrol.viewmodel.account.response.impl.ExamGroupResultProcess;
import com.xiaopeng.carcontrol.viewmodel.account.response.impl.ExamInfoResultProcess;
import com.xiaopeng.carcontrol.viewmodel.account.response.impl.ExamResultProcess;
import com.xiaopeng.carcontrol.viewmodel.account.response.impl.SimpleProcess;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuSmartControl;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.lib.bughunter.anr.UILooperObserver;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest;
import com.xiaopeng.lludancemanager.Constant;
import com.xiaopeng.xvs.xid.XId;
import com.xiaopeng.xvs.xid.account.api.IAccount;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class AccountViewModel extends AccountBaseViewModel implements IAccountViewModel, DataSyncModel.GroupChangeListener {
    public static final int EXAM_TASK_NET_FAILURE = 2;
    public static final int EXAM_TASK_OTHER_FAILURE = 3;
    public static final int EXAM_TASK_OTP_FAILURE = 1;
    public static boolean PENDING_FOR_CLOSE_SMART_DRIVE_SCORE = false;
    public static final long TASK_ID_APA = 4;
    public static final long TASK_ID_CNGP = 6;
    public static final long TASK_ID_LCC = 2;
    public static final long TASK_ID_LCC_L = 8;
    public static final long TASK_ID_MEM = 3;
    public static final long TASK_ID_NGP = 1;
    public static final long TASK_ID_SUPER_VPA = 7;
    public static final long TASK_ID_XNGP = 9;
    private Disposable mDisposable;
    private String mOtp;
    private String mUid;
    private final MutableLiveData<Boolean> isLogin = new MutableLiveData<>();
    private final MutableLiveData<SyncGroupInfo> mSyncGroupInfo = new MutableLiveData<>();
    private final MutableLiveData<Integer> mCreateSyncGroup = new MutableLiveData<>();
    private final MutableLiveData<ExamUrlResult> mTaskQrCode = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTaskResult = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTaskListResult = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTaskLoopResult = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTaskFailure = new MutableLiveData<>();
    private final ExamUrlResult mExamUrlExam = new ExamUrlResult();
    private final List<Long> mLccGroupIds = Arrays.asList(8L, 2L);
    private final List<Long> mLccNoLidarGroupIds = Arrays.asList(2L);
    private final List<Long> mMemParkIds = Arrays.asList(3L, 7L);
    private final List<Long> mMemParkNoLidarIds = Arrays.asList(3L);
    private final Runnable mCreateSyncGroupDelayTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$Kbl9G3LLnJL7aZD8mja4YYod5xs
        @Override // java.lang.Runnable
        public final void run() {
            AccountViewModel.this.lambda$new$0$AccountViewModel();
        }
    };
    private final Runnable mCheckXPilotExamResultTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$M1xa5DZ8_fh-Evk4ldhEdDFrY6Q
        @Override // java.lang.Runnable
        public final void run() {
            AccountViewModel.this.checkXPilotExamResult();
        }
    };
    private final Runnable mLogoutTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$nj9hVRtgMVSHIziWQwHYXyECV3M
        @Override // java.lang.Runnable
        public final void run() {
            AccountViewModel.this.handleXPilotSwWhenLogout();
        }
    };
    private final IScuController mScuController = (IScuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_SCU_SERVICE);
    private final IXpuController mXpuController = (IXpuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_XPU_SERVICE);
    private final IMcuController mMcuController = (IMcuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_MCU_SERVICE);
    private final IAccount mAccount = XId.getAccountApi();

    public /* synthetic */ void lambda$new$0$AccountViewModel() {
        handleCreateSyncGroupState(-1);
    }

    public AccountViewModel() {
        handleCreateSyncGroupState(0);
        DataSyncModel.getInstance().setGroupChangeListener(this);
    }

    public LiveData<Integer> getTaskResult() {
        this.mTaskResult.setValue(null);
        return this.mTaskResult;
    }

    public LiveData<Integer> getTaskListResult() {
        this.mTaskListResult.setValue(null);
        return this.mTaskListResult;
    }

    public LiveData<Integer> getTaskLoopResult() {
        this.mTaskLoopResult.setValue(null);
        return this.mTaskLoopResult;
    }

    public LiveData<Boolean> getIsLogin() {
        this.isLogin.setValue(null);
        return this.isLogin;
    }

    public LiveData<Integer> getTaskFailure() {
        this.mTaskFailure.setValue(null);
        return this.mTaskFailure;
    }

    public LiveData<ExamUrlResult> getTaskQrCode() {
        this.mTaskQrCode.setValue(null);
        return this.mTaskQrCode;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel
    public boolean checkLogin() {
        return this.mAccount.isLogin() || CarBaseConfig.getInstance().isSupportOfflineUserPortfolioPage();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel
    public String getCurrentSyncGroupId() {
        if (this.mAccount.getAccountInfo() != null) {
            return this.mAccount.getAccountInfo().getSyncGroupId();
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel
    public String getCurrentSyncGroupName() {
        if (this.mAccount.getAccountInfo() != null) {
            return this.mAccount.getAccountInfo().getSyncGroupName();
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel
    public void requestCreateNewSyncGroup() {
        if (App.isMainProcess()) {
            try {
                XId.getSyncApi().requestCreateNewSyncGroup();
                handleCreateSyncGroupState(1);
                ThreadUtils.postDelayed(0, this.mCreateSyncGroupDelayTask, UILooperObserver.ANR_TRIGGER_TIME);
                return;
            } catch (Exception e) {
                LogUtils.e("X_EXAM_AccountViewModel", "requestCreateNewSyncGroup failed", (Throwable) e, false);
                return;
            }
        }
        LogUtils.w("X_EXAM_AccountViewModel", "Please invoke requestCreateNewSyncGroup in main process", false);
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel.GroupChangeListener
    public void onAccountOrGroupChange(final SyncGroupEventMsg msg) {
        if (msg == null) {
            LogUtils.w("X_EXAM_AccountViewModel", "onReceiveSyncGroupEventMsg with null");
        } else {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$j4qMC3duHAL6sl5Rg_3T-vbnaVU
                @Override // java.lang.Runnable
                public final void run() {
                    AccountViewModel.this.lambda$onAccountOrGroupChange$1$AccountViewModel(msg);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onAccountOrGroupChange$1$AccountViewModel(final SyncGroupEventMsg msg) {
        LogUtils.i("X_EXAM_AccountViewModel", "onReceiveSyncGroupEventMsg : " + msg.toString(), false);
        if (SyncGroupEventMsg.TYPE_SYNC_GROUP_CREATED.equals(msg.getName())) {
            ThreadUtils.removeRunnable(this.mCreateSyncGroupDelayTask);
            handleCreateSyncGroupState(2);
            handleSyncGroupInfo();
        } else if (SyncGroupEventMsg.TYPE_SYNC_GROUP_SELECTED.equals(msg.getName())) {
            ThreadUtils.removeRunnable(this.mCreateSyncGroupDelayTask);
            handleCreateSyncGroupState(0);
            handleSyncGroupInfo();
        } else if (SyncGroupEventMsg.TYPE_SYNC_ACCOUNT_CHANGE.equals(msg.getName())) {
            String valueOf = String.valueOf(msg.getUid());
            this.mUid = valueOf;
            this.mOtp = null;
            if (valueOf.equals(Constant.DEFAULT_ERROR_RSC_ID)) {
                LogUtils.i("X_EXAM_AccountViewModel", "user log-out!");
                if (App.isMainProcess() && CarBaseConfig.getInstance().isSupportXPilotSafeExam()) {
                    ThreadUtils.execute(this.mLogoutTask);
                    return;
                }
                return;
            }
            if (App.isMainProcess() && CarBaseConfig.getInstance().isSupportXPilotSafeExam()) {
                LogUtils.i("X_EXAM_AccountViewModel", "onAccountsUpdated Uid: " + this.mUid);
                ThreadUtils.execute(this.mCheckXPilotExamResultTask);
            }
            this.isLogin.postValue(true);
            handleSyncGroupInfo();
            LogUtils.i("X_EXAM_AccountViewModel", "user login success!");
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel
    public void getTaskQrCode(final long taskId) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$QvDQ3GthICWf47IZ473TEmEz1AA
            @Override // java.lang.Runnable
            public final void run() {
                AccountViewModel.this.lambda$getTaskQrCode$2$AccountViewModel(taskId);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel
    public void getTaskResult(final long taskId, boolean loop) {
        if (loop) {
            if (this.mDisposable != null) {
                LogUtils.i("X_EXAM_AccountViewModel", "dispose the loop first!", false);
                this.mDisposable.dispose();
            }
            this.mDisposable = Observable.interval(0L, 5L, TimeUnit.SECONDS).subscribe(new Consumer() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$ieP8lzZjsjY80TYOVCJyTQRY-zc
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AccountViewModel.this.lambda$getTaskResult$3$AccountViewModel(taskId, (Long) obj);
                }
            });
            return;
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$jsiz6zwZxmn5sr0gwoeV0AJQb5M
            @Override // java.lang.Runnable
            public final void run() {
                AccountViewModel.this.lambda$getTaskResult$4$AccountViewModel(taskId);
            }
        });
    }

    public /* synthetic */ void lambda$getTaskResult$3$AccountViewModel(final long taskId, Long aLong) throws Exception {
        requestExamResult(taskId, 2);
    }

    public /* synthetic */ void lambda$getTaskResult$4$AccountViewModel(final long taskId) {
        requestExamResult(taskId, 1);
    }

    public void requestNgpLccRelateExamResult() {
        if (BaseFeatureOption.getInstance().isSupportLidarSafeExam()) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$aHuYLf-a1p9PVbTzgmttifm_WrQ
                @Override // java.lang.Runnable
                public final void run() {
                    AccountViewModel.this.lambda$requestNgpLccRelateExamResult$5$AccountViewModel();
                }
            });
        } else {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$kOEqhzBg86aelHU7aiHtEHgRvQs
                @Override // java.lang.Runnable
                public final void run() {
                    AccountViewModel.this.lambda$requestNgpLccRelateExamResult$6$AccountViewModel();
                }
            });
        }
    }

    public /* synthetic */ void lambda$requestNgpLccRelateExamResult$5$AccountViewModel() {
        requestGroupRequest(this.mLccGroupIds, 1);
    }

    public /* synthetic */ void lambda$requestNgpLccRelateExamResult$6$AccountViewModel() {
        requestGroupRequest(this.mLccNoLidarGroupIds, 1);
    }

    public /* synthetic */ void lambda$requestMemRelateExamResult$7$AccountViewModel() {
        requestGroupRequest(this.mMemParkIds, 1);
    }

    public void requestMemRelateExamResult() {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$YWfTYHTGtBeL1NvM5Khodj4RArk
            @Override // java.lang.Runnable
            public final void run() {
                AccountViewModel.this.lambda$requestMemRelateExamResult$7$AccountViewModel();
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel
    public void updateLccSafeExamResult() {
        this.mScuController.setLccSafeExamResult(this.mUid, true);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$GDiJM42sEbFugSeba7Dd9Nxe8-s
            @Override // java.lang.Runnable
            public final void run() {
                AccountViewModel.this.lambda$updateLccSafeExamResult$8$AccountViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$updateLccSafeExamResult$8$AccountViewModel() {
        postResultRequest(2L, TaskResultRequest.TASK_COMPLETE);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel
    public void updateXngpSafeExamResult() {
        this.mXpuController.setXngpSafeExamResult(this.mUid, true);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$WCpoxsUPNTdbJbmUC9iHdb2x4rw
            @Override // java.lang.Runnable
            public final void run() {
                AccountViewModel.this.lambda$updateXngpSafeExamResult$9$AccountViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$updateXngpSafeExamResult$9$AccountViewModel() {
        postResultRequest(9L, TaskResultRequest.TASK_COMPLETE);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel
    public void updateApaSafeExamResult() {
        this.mScuController.setApaSafeExamResult(this.mUid, true);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$QYLDLtVxNWT_LqvALanOdi-2VQM
            @Override // java.lang.Runnable
            public final void run() {
                AccountViewModel.this.lambda$updateApaSafeExamResult$10$AccountViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$updateApaSafeExamResult$10$AccountViewModel() {
        postResultRequest(4L, TaskResultRequest.TASK_COMPLETE);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel
    public void updateSuperVpaSafeExamResult() {
        this.mScuController.setSuperVpaSafeExamResult(this.mUid, true);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$vajNKdzb72umGe0xq9dtt81jlMc
            @Override // java.lang.Runnable
            public final void run() {
                AccountViewModel.this.lambda$updateSuperVpaSafeExamResult$11$AccountViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$updateSuperVpaSafeExamResult$11$AccountViewModel() {
        postResultRequest(7L, TaskResultRequest.TASK_COMPLETE);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel
    public void updateSuperLccSafeExamResult() {
        this.mScuController.setSuperLccSafeExamResult(this.mUid, true);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$kMB06f6IsM0azWHY5Ye2k4OYP00
            @Override // java.lang.Runnable
            public final void run() {
                AccountViewModel.this.lambda$updateSuperLccSafeExamResult$12$AccountViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$updateSuperLccSafeExamResult$12$AccountViewModel() {
        postResultRequest(8L, TaskResultRequest.TASK_COMPLETE);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel
    public void closeSdsEnterPGear() {
        LogUtils.d("X_EXAM_AccountViewModel", "Remind user close smart drive score when enter p gear.");
        smartDriveScoreRemind();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: requestExamUrl */
    public void lambda$getTaskQrCode$2$AccountViewModel(long taskId) {
        LogUtils.i("X_EXAM_AccountViewModel", "requestExamUrl,taskId:" + taskId);
        this.mExamUrlExam.examState = 2;
        if (!checkOtpAndUid()) {
            this.mTaskQrCode.postValue(this.mExamUrlExam);
            LogUtils.w("X_EXAM_AccountViewModel", "check otp and uid failure!", false);
            return;
        }
        IRequest buildFetchExamRequest = SafeExamUtils.buildFetchExamRequest(this.mUid, this.mOtp, taskId);
        if (buildFetchExamRequest == null) {
            this.mTaskQrCode.postValue(this.mExamUrlExam);
            LogUtils.w("X_EXAM_AccountViewModel", "Request build failure in requestExamUrl!", false);
            return;
        }
        buildFetchExamRequest.execute(new ExamInfoResultProcess(new ExamInfoResponseCallback() { // from class: com.xiaopeng.carcontrol.viewmodel.account.AccountViewModel.1
            @Override // com.xiaopeng.carcontrol.viewmodel.account.response.ExamInfoResponseCallback
            public void onExamInfoSuccess(long taskId2, String url) {
                LogUtils.i("X_EXAM_AccountViewModel", "onExamInfoSuccess taskId: " + taskId2 + " url: " + url);
                AccountViewModel.this.mExamUrlExam.examState = 1;
                AccountViewModel.this.mExamUrlExam.examUrl = SafeExamUtils.wrapCarTypeParam(url);
                AccountViewModel.this.mTaskQrCode.postValue(AccountViewModel.this.mExamUrlExam);
            }

            @Override // com.xiaopeng.carcontrol.viewmodel.account.response.ResponseCallback
            public void onExamFailure(int code, String reason) {
                LogUtils.w("X_EXAM_AccountViewModel", "Request qr code from server failure, code :" + code + " reason: " + reason);
                AccountViewModel.this.mTaskQrCode.postValue(AccountViewModel.this.mExamUrlExam);
            }
        }));
    }

    private void requestExamResult(long taskId, final int origin) {
        LogUtils.i("X_EXAM_AccountViewModel", "requestExamResult,taskId:" + taskId + " origin:" + origin);
        if (!checkOtpAndUid()) {
            LogUtils.w("X_EXAM_AccountViewModel", "check otp and uid failure!", false);
            if (origin == 1) {
                this.mTaskFailure.postValue(2);
                return;
            }
            return;
        }
        SafeExamUtils.buildFetchResultRequest(this.mUid, this.mOtp, taskId).execute(new ExamResultProcess(new ExamResultResponseCallback() { // from class: com.xiaopeng.carcontrol.viewmodel.account.AccountViewModel.2
            @Override // com.xiaopeng.carcontrol.viewmodel.account.response.ExamResultResponseCallback
            public void onExamTaskSuccess(long taskId2, int result, int origin2) {
                LogUtils.i("X_EXAM_AccountViewModel", "Exam  single result, taskId: " + taskId2 + " result: " + result + " origin: " + origin2);
                if (origin2 == 2) {
                    if (result == 1) {
                        AccountViewModel.this.mTaskLoopResult.postValue(1);
                        if (AccountViewModel.this.mDisposable != null) {
                            AccountViewModel.this.mDisposable.dispose();
                        }
                        AccountViewModel.this.saveExamResultToLocal(taskId2, result);
                    }
                } else if (origin2 == 1) {
                    AccountViewModel.this.mTaskResult.postValue(Integer.valueOf(result));
                    AccountViewModel.this.saveExamResultToLocal(taskId2, result);
                } else if (origin2 == 0) {
                    if (result != 1) {
                        AccountViewModel.this.turnOffByExamNotPassed(taskId2);
                    }
                    AccountViewModel.this.saveExamResultToLocal(taskId2, result);
                }
            }

            @Override // com.xiaopeng.carcontrol.viewmodel.account.response.ResponseCallback
            public void onExamFailure(int code, String reason) {
                if (origin == 1) {
                    AccountViewModel.this.mTaskFailure.postValue(2);
                }
            }
        }, origin));
    }

    private void requestGroupRequest(List<Long> taskIds, int origin) {
        LogUtils.i("X_EXAM_AccountViewModel", "requestGroupRequest,taskIds:" + taskIds + " origin: " + origin);
        boolean z = origin == 1;
        if (taskIds == null || CollectionUtils.isEmpty(taskIds)) {
            LogUtils.e("X_EXAM_AccountViewModel", "task id is empty!");
            return;
        }
        LogUtils.i("X_EXAM_AccountViewModel", "get task list...", false);
        if (!checkOtpAndUid()) {
            if (z) {
                this.mTaskFailure.postValue(1);
            }
            LogUtils.i("X_EXAM_AccountViewModel", "check otp and uid failure!", false);
            return;
        }
        IRequest buildFetchGroupResultRequest = SafeExamUtils.buildFetchGroupResultRequest(this.mUid, this.mOtp, taskIds);
        if (buildFetchGroupResultRequest == null) {
            LogUtils.i("X_EXAM_AccountViewModel", "Build Fetch group request failure.");
        } else {
            buildFetchGroupResultRequest.execute(new ExamGroupResultProcess(new ExamResultResponseCallback() { // from class: com.xiaopeng.carcontrol.viewmodel.account.AccountViewModel.3
                @Override // com.xiaopeng.carcontrol.viewmodel.account.response.ExamResultResponseCallback
                public void onExamTaskSuccess(long taskId, int result, int origin2) {
                    LogUtils.i("X_EXAM_AccountViewModel", "Exam  group result, taskId: " + taskId + " result: " + result + " origin: " + origin2);
                    if (origin2 != 1) {
                        if (origin2 == 0) {
                            if (result != 1) {
                                AccountViewModel.this.turnOffByExamNotPassed(taskId);
                            }
                            AccountViewModel.this.saveExamResultToLocal(taskId, result);
                            return;
                        }
                        return;
                    }
                    if (CarBaseConfig.getInstance().isSupportLidar() && BaseFeatureOption.getInstance().isSupportLidarSafeExam()) {
                        if (taskId == 7) {
                            AccountViewModel.this.mTaskListResult.postValue(Integer.valueOf(result));
                        } else if (taskId == 8) {
                            AccountViewModel.this.mTaskListResult.postValue(Integer.valueOf(result));
                        }
                    } else if (taskId == 3) {
                        AccountViewModel.this.mTaskListResult.postValue(Integer.valueOf(result));
                    } else if (taskId == 2) {
                        AccountViewModel.this.mTaskListResult.postValue(Integer.valueOf(result));
                    }
                    AccountViewModel.this.saveExamResultToLocal(taskId, result);
                }

                @Override // com.xiaopeng.carcontrol.viewmodel.account.response.ResponseCallback
                public void onExamFailure(int code, String reason) {
                    AccountViewModel.this.mTaskFailure.postValue(1);
                }
            }, origin));
        }
    }

    private void postResultRequest(long taskId, int result) {
        LogUtils.i("X_EXAM_AccountViewModel", "post test result...,uid: " + this.mUid + " taskId: " + taskId + " result: " + result, false);
        if (!checkOtpAndUid()) {
            LogUtils.i("X_EXAM_AccountViewModel", "check otp and uid failure!", false);
            return;
        }
        IRequest buildPostResultRequest = SafeExamUtils.buildPostResultRequest(this.mUid, this.mOtp, taskId, result);
        if (buildPostResultRequest == null) {
            LogUtils.i("X_EXAM_AccountViewModel", "Build post result request failure.");
        } else {
            buildPostResultRequest.execute(new SimpleProcess());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveExamResultToLocal(long taskId, int result) {
        boolean z = result == 1;
        if (taskId == 1) {
            this.mScuController.setNgpSafeExamResult(this.mUid, z);
        } else if (taskId == 3) {
            this.mScuController.setMemParkSafeExamResult(this.mUid, z);
        } else if (taskId == 2) {
            this.mScuController.setLccSafeExamResult(this.mUid, z);
        } else if (taskId == 4) {
            this.mScuController.setApaSafeExamResult(this.mUid, z);
        } else if (taskId == 6) {
            this.mXpuController.setCngpSafeExamResult(this.mUid, z);
        } else if (taskId == 9) {
            this.mXpuController.setXngpSafeExamResult(this.mUid, z);
        } else if (taskId == 8) {
            this.mScuController.setSuperLccSafeExamResult(this.mUid, z);
            if (z) {
                this.mScuController.setLccSafeExamResult(this.mUid, true);
            }
        } else if (taskId == 7) {
            this.mScuController.setSuperVpaSafeExamResult(this.mUid, z);
            if (z) {
                this.mScuController.setMemParkSafeExamResult(this.mUid, true);
            }
        } else {
            LogUtils.w("X_EXAM_AccountViewModel", "Save exam result to local, invalid task id:" + taskId);
            return;
        }
        LogUtils.w("X_EXAM_AccountViewModel", "Save exam result to local, taskId:" + taskId + " result:" + result);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void turnOffByExamNotPassed(long taskId) {
        boolean isParkStall = VcuSmartControl.getInstance().isParkStall();
        LogUtils.i("X_EXAM_AccountViewModel", "turnOffByExamNotPassed isParkStall: " + isParkStall + "taskId:" + taskId, false);
        if (isParkStall) {
            if (taskId == 1) {
                this.mScuController.setNgpEnable(false);
                if (CarBaseConfig.getInstance().isSupportCNgp()) {
                    this.mXpuController.setCityNgpSw(false);
                }
            } else if (taskId == 3) {
                this.mScuController.setMemoryParkSw(false);
            } else if (taskId == 7) {
                if (CarBaseConfig.getInstance().isSupportLidar()) {
                    this.mScuController.setMemoryParkSw(false);
                }
            } else if (taskId == 2 || (CarBaseConfig.getInstance().isSupportLidar() && (taskId == 8 || taskId == 9))) {
                this.mScuController.setLccState(false);
                this.mScuController.setAlcState(false);
                if (CarBaseConfig.getInstance().isSupportNgp()) {
                    this.mScuController.setNgpEnable(false);
                }
                if (CarBaseConfig.getInstance().isSupportCNgp()) {
                    this.mXpuController.setCityNgpSw(false);
                }
            } else if (taskId != 4) {
                if (taskId == 6) {
                    this.mXpuController.setCityNgpSw(false);
                }
            } else {
                this.mScuController.setAutoParkSw(false);
                if (CarBaseConfig.getInstance().isSupportMemPark()) {
                    this.mScuController.setMemoryParkSw(false);
                }
            }
        }
    }

    public void resetExamResultBySDS(List<Long> taskList, String uid) {
        Account[] accountsByType = getAccountManager().getAccountsByType("com.xiaopeng.accountservice.ACCOUNT_TYPE_XP_VEHICLE");
        if (accountsByType.length > 0) {
            this.mUid = getAccountManager().getUserData(accountsByType[0], "uid");
        }
        if (!uid.equals(this.mUid)) {
            LogUtils.d("X_EXAM_AccountViewModel", "Receive push message on uid: " + uid + " ,and now login user uid is " + this.mUid + " Just save the result to local sp");
            for (int i = 0; i < taskList.size(); i++) {
                saveCloseResultToLocalByUid(taskList.get(i), uid);
            }
            return;
        }
        LogUtils.d("X_EXAM_AccountViewModel", "Will close task in taskid: " + taskList);
        for (int i2 = 0; i2 < taskList.size(); i2++) {
            saveExamResultToLocal(taskList.get(i2).longValue(), 0);
            turnOffByExamNotPassed(taskList.get(i2).longValue());
        }
        if (VcuSmartControl.getInstance().isParkStall()) {
            smartDriveScoreRemind();
        } else {
            PENDING_FOR_CLOSE_SMART_DRIVE_SCORE = true;
        }
    }

    private void saveCloseResultToLocalByUid(Long taskId, String uid) {
        if (taskId.longValue() == 1) {
            NgpExamModel.getInstance().setNgpExamResult(uid, false);
        } else if (taskId.longValue() == 3) {
            MemParkExamModel.getInstance().setMemParkExamResult(uid, false);
        } else if (taskId.longValue() == 2) {
            LccExamModel.getInstance().setLccExamResult(uid, false);
        } else if (taskId.longValue() == 4) {
            ApaExamModel.getInstance().setApaExamResult(uid, false);
        } else if (taskId.longValue() == 6) {
            CngpExamModel.getInstance().setCngpExamResult(uid, false);
        } else if (taskId.longValue() == 7) {
            SuperVpaExamModel.getInstance().setSuperVpaExamResult(uid, false);
        } else if (taskId.longValue() == 8) {
            SuperLccExamModel.getInstance().setSuperLccExamResult(uid, false);
        } else if (taskId.longValue() == 9) {
            XngpExamModel.getInstance().setXngpExamResult(uid, false);
        } else {
            LogUtils.w("X_EXAM_AccountViewModel", "Save close result to local, invalid task id:" + taskId + "uid:" + uid);
            return;
        }
        LogUtils.w("X_EXAM_AccountViewModel", "Save close result to local, taskId:" + taskId + "uid:" + uid);
    }

    private void smartDriveScoreRemind() {
        NotificationHelper.getInstance().sendMessageToMessageCenter(NotificationHelper.SCENE_SMART_DRIVE_SCORE, ResUtils.getString(R.string.smart_drive_score_remind_title), ResUtils.getString(R.string.smart_drive_score_remind_content), ResUtils.getString(R.string.smart_drive_score_remind_prompt_tts), ResUtils.getString(R.string.smart_drive_score_remind_wake_words), ResUtils.getString(R.string.smart_drive_score_remind_response_tts), ResUtils.getString(R.string.smart_drive_score_remind_btn_title), false, 0L, new IpcRouterService.ICallback() { // from class: com.xiaopeng.carcontrol.viewmodel.account.-$$Lambda$AccountViewModel$S2zRchQz--4_Rl5FVvJe4lcpWk4
            @Override // com.xiaopeng.carcontrol.IpcRouterService.ICallback
            public final void onCallback(String str) {
                AccountViewModel.this.lambda$smartDriveScoreRemind$13$AccountViewModel(str);
            }
        });
    }

    public /* synthetic */ void lambda$smartDriveScoreRemind$13$AccountViewModel(String content) {
        gotoXPilotPage();
    }

    private void gotoXPilotPage() {
        IElementDirect elementDirect = ElementDirectManager.getElementDirect();
        if (elementDirect != null) {
            String convertUrl = elementDirect.convertUrl("xiaopeng://carcontrol/xpilotcontrol");
            if (elementDirect.checkSupport(convertUrl)) {
                LogUtils.i("X_EXAM_AccountViewModel", "getGuiPageOpenState 0 : " + convertUrl, false);
                elementDirect.showPageDirect(App.getInstance(), convertUrl);
            }
        }
    }

    private boolean checkOtpAndUid() {
        Account[] accountsByType = getAccountManager().getAccountsByType("com.xiaopeng.accountservice.ACCOUNT_TYPE_XP_VEHICLE");
        if (accountsByType.length > 0) {
            String userData = getAccountManager().getUserData(accountsByType[0], "uid");
            Bundle bundle = new Bundle();
            bundle.putString("app_id", "xp_car_setting_car");
            try {
                this.mOtp = getAccountManager().getAuthToken(accountsByType[0], "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_OTP", bundle, false, (AccountManagerCallback<Bundle>) null, (Handler) null).getResult().getString("authtoken");
                this.mUid = userData;
                LogUtils.i("X_EXAM_AccountViewModel", "fetch the otp and uid otp = " + this.mOtp + " uid = " + userData, false);
                if (TextUtils.isEmpty(this.mOtp)) {
                    return false;
                }
                return !TextUtils.isEmpty(userData);
            } catch (AuthenticatorException | OperationCanceledException | IOException e) {
                LogUtils.i("X_EXAM_AccountViewModel", "check otp occurs an exception!", false);
                e.printStackTrace();
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkXPilotExamResult() {
        if (this.mUid != null) {
            if (CarBaseConfig.getInstance().isSupportNgp()) {
                LogUtils.i("X_EXAM_AccountViewModel", "Request NGP exam result for Uid: " + this.mUid, false);
                requestExamResult(1L, 0);
            }
            if (CarBaseConfig.getInstance().isSupportMemPark()) {
                LogUtils.i("X_EXAM_AccountViewModel", "Request VPA exam result for Uid: " + this.mUid, false);
                if (BaseFeatureOption.getInstance().isSupportLidarSafeExam()) {
                    requestGroupRequest(this.mMemParkIds, 0);
                } else {
                    requestGroupRequest(this.mMemParkNoLidarIds, 0);
                }
            }
            if (CarBaseConfig.getInstance().isSupportAutoPark()) {
                LogUtils.i("X_EXAM_AccountViewModel", "Request APA exam result for Uid: " + this.mUid, false);
                requestExamResult(4L, 0);
            }
            if (CarBaseConfig.getInstance().isSupportLcc()) {
                if (CarBaseConfig.getInstance().isSupportXNgp()) {
                    LogUtils.i("X_EXAM_AccountViewModel", "Request XNGP exam result for Uid: " + this.mUid, false);
                    requestExamResult(9L, 0);
                } else {
                    LogUtils.i("X_EXAM_AccountViewModel", "Request LCC exam result for Uid: " + this.mUid, false);
                    if (BaseFeatureOption.getInstance().isSupportLidarSafeExam()) {
                        requestGroupRequest(this.mLccGroupIds, 0);
                    } else {
                        requestGroupRequest(this.mLccNoLidarGroupIds, 0);
                    }
                }
            }
            if (CarBaseConfig.getInstance().isSupportCNgp()) {
                LogUtils.i("X_EXAM_AccountViewModel", "Request CNGP exam result for Uid: " + this.mUid, false);
                requestExamResult(6L, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleXPilotSwWhenLogout() {
        boolean isParkStall = VcuSmartControl.getInstance().isParkStall();
        LogUtils.i("X_EXAM_AccountViewModel", "handleXPilotSwWhenLogout isParkStall: " + isParkStall, false);
        if (isParkStall) {
            if (CarBaseConfig.getInstance().isSupportNgp()) {
                this.mScuController.setNgpEnable(false);
            }
            if (CarBaseConfig.getInstance().isSupportLcc()) {
                this.mScuController.setLccState(false);
                this.mScuController.setAlcState(false);
            }
            if (CarBaseConfig.getInstance().isSupportMemPark()) {
                this.mScuController.setMemoryParkSw(false);
            }
            if (CarBaseConfig.getInstance().isSupportAutoPark()) {
                this.mScuController.setAutoParkSw(false);
            }
            if (CarBaseConfig.getInstance().isSupportCNgp()) {
                this.mXpuController.setCityNgpSw(false);
            }
        }
    }

    public void cancelLoop() {
        Disposable disposable = this.mDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public boolean skipXpilotExam() {
        boolean isFactoryModeActive = this.mMcuController.isFactoryModeActive();
        LogUtils.i("X_EXAM_AccountViewModel", "Skip xpilot exam , in factory mode :" + isFactoryModeActive);
        return isFactoryModeActive;
    }

    public LiveData<SyncGroupInfo> getSyncGroupInfoData() {
        return this.mSyncGroupInfo;
    }

    public LiveData<Integer> getCreateSyncGroupData() {
        return this.mCreateSyncGroup;
    }

    private void handleSyncGroupInfo() {
        SyncGroupInfo syncGroupInfo = new SyncGroupInfo();
        syncGroupInfo.groupId = getCurrentSyncGroupId();
        syncGroupInfo.groupName = getCurrentSyncGroupName();
        LogUtils.i("X_EXAM_AccountViewModel", "handleSyncGroupInfo: " + syncGroupInfo.toString());
        this.mSyncGroupInfo.postValue(syncGroupInfo);
    }

    private void handleCreateSyncGroupState(int state) {
        this.mCreateSyncGroup.postValue(Integer.valueOf(state));
    }
}
