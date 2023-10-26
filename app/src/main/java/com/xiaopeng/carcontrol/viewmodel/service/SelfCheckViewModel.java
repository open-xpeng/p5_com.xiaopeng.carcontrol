package com.xiaopeng.carcontrol.viewmodel.service;

import android.provider.Settings;
import android.text.TextUtils;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.IpcRouterService;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckCategoryItem;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckDetailItem;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.selfcheck.CheckCarMode;
import com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarListener;
import com.xiaopeng.carcontrol.viewmodel.selfcheck.SelfCheckUtil;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class SelfCheckViewModel implements ISelfCheckViewModel {
    private static final int ALLOW_AUTO_SELF_CHECK_OFF = 0;
    private static final int ALLOW_AUTO_SELF_CHECK_ON = 1;
    private static final String KEY_ALLOW_AUTO_SELF_CHECK = "XP_Allow_Self_Check";
    private static final String TAG = "SelfCheckViewModel";
    private IMcuController mMcuController;
    private IVcuController mVcuController;
    private final MutableLiveData<String> showAllFaultViewData = new MutableLiveData<>();
    private final MutableLiveData<String> showFaultDetailViewData = new MutableLiveData<>();
    private ICheckCarListener mSelfCheckListener = new ICheckCarListener() { // from class: com.xiaopeng.carcontrol.viewmodel.service.SelfCheckViewModel.1
        @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarListener
        public void onCheckCancel() {
        }

        @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarListener
        public void onChecking(CheckDetailItem item) {
        }

        @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarListener
        public void onCheckStart() {
            LogUtils.d(SelfCheckViewModel.TAG, "onCheckStart", false);
            SelfCheckViewModel.this.clearCheckInfoCard();
        }

        @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarListener
        public void onMockChecking(int progress) {
            if (BaseFeatureOption.getInstance().isSupportShowSelfCheckCard()) {
                CheckCategoryItem categoryItem = CheckCarMode.getInstance().getCategoryItem(progress);
                if (categoryItem == null) {
                    LogUtils.w(SelfCheckViewModel.TAG, "Check_onMockChecking categoryItem is null");
                    return;
                }
                NotificationHelper.getInstance().showSelfCheckCard(18, ResUtils.getString(R.string.selfcheck_status), String.format(ResUtils.getString(R.string.selfcheck_progress), categoryItem.getTitle()), CheckCarMode.getInstance().generateMockExtrasForInfoflow(progress));
            }
        }

        @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarListener
        public void onCheckComplete() {
            String format;
            String faultStr;
            boolean isSeverity = CheckCarMode.getInstance().isSeverity();
            boolean hasIssue = CheckCarMode.getInstance().hasIssue();
            String generateJsonForUi = CheckCarMode.getInstance().generateJsonForUi(1, true);
            LogUtils.d(SelfCheckViewModel.TAG, "onCheckComplete, isServerity=" + isSeverity + ", hasIssue = " + hasIssue + "extras = " + generateJsonForUi, false);
            int i = 18;
            if (!isSeverity) {
                NotificationHelper.getInstance().cancelInfoflowCard(18);
            }
            if (BaseFeatureOption.getInstance().isSupportNewSelfCheckArch()) {
                if (hasIssue) {
                    if (BaseFeatureOption.getInstance().isSupportShowSelfCheckCard()) {
                        if (TextUtils.isEmpty(generateJsonForUi)) {
                            LogUtils.w(SelfCheckViewModel.TAG, "Check_onCheckComplete data is wrong!");
                            return;
                        }
                        if (isSeverity) {
                            format = ResUtils.getString(R.string.selfcheck_finish);
                            faultStr = String.format(ResUtils.getString(R.string.selfcheck_finish_have_serious_issue), Integer.valueOf(CheckCarMode.getInstance().getFaultCount()));
                        } else {
                            i = 19;
                            format = String.format(ResUtils.getString(R.string.selfcheck_finish_have_issue), Integer.valueOf(CheckCarMode.getInstance().getFaultCount()));
                            faultStr = CheckCarMode.getInstance().getFaultStr();
                        }
                        NotificationHelper.getInstance().showSelfCheckCard(i, format, faultStr, generateJsonForUi);
                    }
                } else if (BaseFeatureOption.getInstance().isSupportNotifyNormalCheckResult()) {
                    NotificationHelper.getInstance().showSelfCheckCard(18, ResUtils.getString(R.string.selfcheck_finish), "", CheckCarMode.getInstance().generateJsonForUi(2, false));
                }
            }
        }

        @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarListener
        public void onCheckStop() {
            boolean z = false;
            LogUtils.i(SelfCheckViewModel.TAG, "Check_onCheckStop", false);
            if (CheckCarMode.getInstance().isChecking() || (CheckCarMode.getInstance().isCheckComplete() && !CheckCarMode.getInstance().isSeverity())) {
                z = true;
            }
            if (z) {
                LogUtils.i(SelfCheckViewModel.TAG, "stopSelfCheck clear info card...");
                SelfCheckViewModel.this.clearCheckInfoCard();
            }
        }

        @Override // com.xiaopeng.carcontrol.viewmodel.selfcheck.ICheckCarListener
        public void onCheckError(Throwable e) {
            if (BaseFeatureOption.getInstance().isSupportShowSelfCheckCard()) {
                LogUtils.i(SelfCheckViewModel.TAG, "Check_onCheckError ");
                e.printStackTrace();
                SelfCheckViewModel.this.clearCheckInfoCard();
                NotificationHelper.getInstance().showToast(R.string.selfcheck_exception);
            }
        }
    };
    private IMcuController.Callback mMcuCallback = new IMcuController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.service.SelfCheckViewModel.2
        @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController.Callback
        public void onIgStatusChanged(int state) {
            if (state == 1 || !CheckCarMode.getInstance().isChecking()) {
                return;
            }
            SelfCheckViewModel.this.stopSelfCheck();
        }
    };

    public SelfCheckViewModel() {
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
        IMcuController iMcuController = (IMcuController) carClientWrapper.getController(CarClientWrapper.XP_MCU_SERVICE);
        this.mMcuController = iMcuController;
        iMcuController.registerCallback(this.mMcuCallback);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ISelfCheckViewModel
    public boolean isAutoSelfCheck() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), KEY_ALLOW_AUTO_SELF_CHECK, 1) == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ISelfCheckViewModel
    public void setAutoSelfCheck(boolean enable) {
        Settings.System.putInt(App.getInstance().getContentResolver(), KEY_ALLOW_AUTO_SELF_CHECK, enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ISelfCheckViewModel
    public void startSelfCheck() {
        if (ThreadUtils.isMainThread()) {
            startSelfCheckOnMain();
        } else {
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$SelfCheckViewModel$Vn9BfxNS1uJaBbzXXY8D8xq31t4
                @Override // java.lang.Runnable
                public final void run() {
                    SelfCheckViewModel.this.startSelfCheckOnMain();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSelfCheckOnMain() {
        LogUtils.i(TAG, "start self check...", false);
        if (App.isMainProcess() && !CheckCarMode.getInstance().isChecking()) {
            if (this.mVcuController.getGearLevel() != 4) {
                LogUtils.w(TAG, "not GEAR_LEVEL_P, self only check on GEAR_LEVEL_P");
                return;
            }
            CheckCarMode.getInstance().setViewListener(this.mSelfCheckListener);
            CheckCarMode.getInstance().startCheck();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ISelfCheckViewModel
    public void stopSelfCheck() {
        if (ThreadUtils.isMainThread()) {
            stopSelfCheckOnMain();
        } else {
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$SelfCheckViewModel$-8mZcpyME4Fj7Sum8-ziboJgKR4
                @Override // java.lang.Runnable
                public final void run() {
                    SelfCheckViewModel.this.stopSelfCheckOnMain();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopSelfCheckOnMain() {
        if (CheckCarMode.getInstance().isCheckComplete() && !CheckCarMode.getInstance().isSeverity()) {
            LogUtils.i(TAG, "stopSelfCheck clear info card...", false);
            clearCheckInfoCard();
            return;
        }
        CheckCarMode.getInstance().stopCheck();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearCheckInfoCard() {
        NotificationHelper.getInstance().cancelInfoflowCard(18);
        NotificationHelper.getInstance().cancelInfoflowCard(19);
    }

    private String getTTS() {
        int faultCount = CheckCarMode.getInstance().getFaultCount();
        if (faultCount != 1) {
            return faultCount > 1 ? ResUtils.getString(R.string.selfcheck_tts_multi) : "";
        }
        CheckDetailItem firstFaultItem = CheckCarMode.getInstance().getFirstFaultItem();
        if (firstFaultItem == null) {
            LogUtils.e(TAG, " ERROR: fault num do not match falut items");
            return "";
        }
        return ResUtils.getString(R.string.selfcheck_tts_one, firstFaultItem.getTitle());
    }

    private void playTTS() {
        String tts = getTTS();
        if (TextUtils.isEmpty(tts)) {
            return;
        }
        SelfCheckUtil.playTts(tts);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ISelfCheckViewModel
    public void getAllFaultList(final IDiagnosticCallback callback) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$SelfCheckViewModel$HQXppMqnqwDLSIo8Mdo9LPndtmU
            @Override // java.lang.Runnable
            public final void run() {
                IpcRouterService.getAllFaultCode(IDiagnosticCallback.this);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ISelfCheckViewModel
    public void getFaultDetail(final int type, final String code, final IDiagnosticCallback callback) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$SelfCheckViewModel$dGZYcfJoOCNJExppPqCTpxhaOoA
            @Override // java.lang.Runnable
            public final void run() {
                IpcRouterService.getFaultCode(type, code, callback);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ISelfCheckViewModel
    public void getMobilePhone(final IDiagnosticCallback callback) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$SelfCheckViewModel$oT_MKr1DSdjLIH73ozB7Q98Xth8
            @Override // java.lang.Runnable
            public final void run() {
                IpcRouterService.getMobilePhone(IDiagnosticCallback.this);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ISelfCheckViewModel
    public void reserveRemoteDiagnose(final String phone, final IDiagnosticCallback callback) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$SelfCheckViewModel$HweOR8J7De4b4Fsfion5_gUk3ac
            @Override // java.lang.Runnable
            public final void run() {
                IpcRouterService.reserveRemoteDiagnose(phone, callback);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ISelfCheckViewModel
    public void handleAllFaultData(String result) {
        this.showAllFaultViewData.postValue(result);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ISelfCheckViewModel
    public void handleFaultDetailData(String result) {
        this.showFaultDetailViewData.postValue(result);
    }

    public LiveData<String> getAllFaultData() {
        return this.showAllFaultViewData;
    }

    public LiveData<String> getFaultDetailData() {
        return this.showFaultDetailViewData;
    }
}
