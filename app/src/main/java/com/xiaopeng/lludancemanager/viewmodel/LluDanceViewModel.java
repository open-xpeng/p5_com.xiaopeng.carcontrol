package com.xiaopeng.lludancemanager.viewmodel;

import android.media.AudioManager;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceBean;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceDownloadInfo;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceType;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.util.GsonUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.TurnLampState;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.lludancemanager.Constant;
import com.xiaopeng.lludancemanager.bean.LluDanceInstalledInfo;
import com.xiaopeng.lludancemanager.bean.LluDanceViewData;
import com.xiaopeng.lludancemanager.helper.LluDanceAlarmHelper;
import com.xiaopeng.lludancemanager.model.LluDanceRepository;
import com.xiaopeng.lludancemanager.viewmodel.LluDanceViewModel;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.speechwidget.ListWidget;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class LluDanceViewModel extends LluDanceBaseViewModel {
    private static final int DEFAULT_POSITION = 0;
    private static final int INVALID_POSITION = -1;
    public static final int LLU_DANCE_VOLUME_VALUE = 25;
    private static final String TAG = "LluDanceViewModel";
    private boolean mAutoWindowExecuted;
    private Observer<GearLevel> mGearLevelChangedObserver;
    private final MediatorLiveData<Integer> mIgOffEvent;
    private String mIntentRscId;
    private final LampViewModel mLampVM;
    private final MediatorLiveData<List<LluDanceViewData>> mLiveDataList;
    private final MediatorLiveData<Boolean> mLluDanceBreak;
    private LluViewModel mLluViewModel;
    private final MutableLiveData<Boolean> mLoadingState;
    private HashMap<String, Integer> mPositionMap;
    private LluDanceRepository mRepository;
    private Observer<TurnLampState> mTurnLampChangedObserver;
    private final VcuViewModel mVcuVM;
    private final IBinder mWakeupBinder;
    private final MutableLiveData<String> mOrderedPlayItemIdData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDefaultSelectedOption = new MutableLiveData<>();

    @Override // com.xiaopeng.lludancemanager.viewmodel.ILluDanceViewModel
    public void destroy() {
    }

    public LluDanceViewModel() {
        MediatorLiveData<List<LluDanceViewData>> mediatorLiveData = new MediatorLiveData<>();
        this.mLiveDataList = mediatorLiveData;
        this.mLoadingState = new MutableLiveData<>();
        MediatorLiveData<Boolean> mediatorLiveData2 = new MediatorLiveData<>();
        this.mLluDanceBreak = mediatorLiveData2;
        this.mIgOffEvent = new MediatorLiveData<>();
        this.mPositionMap = new HashMap<>();
        this.mIntentRscId = null;
        this.mWakeupBinder = new Binder();
        this.mAutoWindowExecuted = false;
        this.mRepository = new LluDanceRepository();
        this.mLluViewModel = (LluViewModel) ViewModelManager.getInstance().getViewModelImpl(ILluViewModel.class);
        VcuViewModel vcuViewModel = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
        this.mVcuVM = vcuViewModel;
        LampViewModel lampViewModel = (LampViewModel) ViewModelManager.getInstance().getViewModelImpl(ILampViewModel.class);
        this.mLampVM = lampViewModel;
        initMcuCallback();
        if (this.mTurnLampChangedObserver == null) {
            this.mTurnLampChangedObserver = new Observer() { // from class: com.xiaopeng.lludancemanager.viewmodel.-$$Lambda$LluDanceViewModel$KNTvuVKWB8qmY0_AHD0PbTYow3U
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LluDanceViewModel.this.lambda$new$0$LluDanceViewModel((TurnLampState) obj);
                }
            };
        }
        if (this.mGearLevelChangedObserver == null) {
            this.mGearLevelChangedObserver = new Observer() { // from class: com.xiaopeng.lludancemanager.viewmodel.-$$Lambda$LluDanceViewModel$76tCQID4QDYL2gKR15nXC6IyHEo
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LluDanceViewModel.this.lambda$new$1$LluDanceViewModel((GearLevel) obj);
                }
            };
        }
        mediatorLiveData2.addSource(lampViewModel.getTurnLampData(), this.mTurnLampChangedObserver);
        mediatorLiveData2.addSource(vcuViewModel.getGearLevelData(), this.mGearLevelChangedObserver);
        mediatorLiveData.addSource(this.mRepository.getResourcesMutableLiveData(), new Observer() { // from class: com.xiaopeng.lludancemanager.viewmodel.-$$Lambda$Etgjzg3luWLfNgFAP0SA1VTlEKc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LluDanceViewModel.this.updateAllData((List) obj);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$LluDanceViewModel(TurnLampState turnLampState) {
        LogUtils.d(TAG, "turn lamp changed , turnLampState = " + turnLampState);
        if (turnLampState != TurnLampState.Off) {
            this.mLluDanceBreak.postValue(true);
        }
    }

    public /* synthetic */ void lambda$new$1$LluDanceViewModel(GearLevel gearLevel) {
        LogUtils.d(TAG, "gearLevel changed , gearLevel = " + gearLevel);
        if (GearLevel.P != gearLevel) {
            this.mLluDanceBreak.postValue(true);
        }
    }

    public MutableLiveData<Integer> getDefaultSelectedOption() {
        return this.mDefaultSelectedOption;
    }

    public void setDefaultSelectedOption(int userSelectedPosition) {
        this.mDefaultSelectedOption.setValue(Integer.valueOf(userSelectedPosition));
        LogUtils.d(TAG, "setDefaultSelectedOption = " + userSelectedPosition);
    }

    public MutableLiveData<String> getOrderedPlayItemIdData() {
        return this.mOrderedPlayItemIdData;
    }

    public MediatorLiveData<Integer> getIgEvent() {
        return this.mIgOffEvent;
    }

    public boolean isAutoWindowExecuted() {
        return this.mAutoWindowExecuted;
    }

    public void resetAutoWindowFlag() {
        this.mAutoWindowExecuted = false;
    }

    private void initMcuCallback() {
        ((IMcuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_MCU_SERVICE)).registerCallback(new McuCallbackImplForLlu(this));
    }

    public MediatorLiveData<Boolean> getLluDanceBreak() {
        return this.mLluDanceBreak;
    }

    public LiveData<Boolean> getLluDanceStartPlayData() {
        return this.mLluViewModel.getLluDanceStartPlayData();
    }

    public int getLightDanceAvailable() {
        return this.mLluViewModel.isLightDanceAvailable();
    }

    public MutableLiveData<List<LluDanceViewData>> getLiveDataList() {
        return this.mLiveDataList;
    }

    public void setIntentRscId(String intentRscId) {
        this.mIntentRscId = intentRscId;
    }

    @Override // com.xiaopeng.lludancemanager.viewmodel.ILluDanceViewModel
    public void getAllData() {
        this.mRepository.requestResources();
    }

    @Override // com.xiaopeng.lludancemanager.viewmodel.ILluDanceViewModel
    public void updateAllData(List<LluDanceViewData> list) {
        LogUtils.d(TAG, "updateAllData" + list);
        this.mPositionMap.clear();
        for (int i = 0; i < list.size(); i++) {
            this.mPositionMap.put(list.get(i).getId(), Integer.valueOf(i));
        }
        LogUtils.d(TAG, " mPositionMap =  " + this.mPositionMap);
        if (list.size() < 7) {
            list.add(new LluDanceViewData(App.getInstance().getString(R.string.llu_dance_activity_dance_item_more_title), App.getInstance().getString(R.string.llu_dance_activity_dance_item_more_author), "", Constant.END_ITEM_MORE_ID, "", -1, -1L, -1.0d, "", ""));
        }
        this.mLiveDataList.postValue(list);
        chooseSelectPositionNew(list);
        updateLoadingState(false);
    }

    public void chooseSelectPositionNew(List<LluDanceViewData> list) {
        int i;
        if (list == null || list.isEmpty()) {
            return;
        }
        String str = TAG;
        LogUtils.d(str, "mIntentRscId =  " + this.mIntentRscId);
        int i2 = 0;
        if (!TextUtils.isEmpty(this.mIntentRscId)) {
            i = 0;
            while (i < list.size()) {
                if (this.mIntentRscId.equals(list.get(i).getId())) {
                    LogUtils.d(TAG, "chooseSelectPosition open from dropdown menu, position = " + i);
                    SharedPreferenceUtil.setLLuDanceCurrentSelectRscId(this.mIntentRscId);
                    this.mIntentRscId = null;
                    i2 = i;
                    break;
                }
                i++;
            }
            this.mDefaultSelectedOption.postValue(Integer.valueOf(i2));
        }
        String lLuDanceCurrentSelectRscId = SharedPreferenceUtil.getLLuDanceCurrentSelectRscId();
        LogUtils.d(str, "chooseSelectPosition getLLuDanceCurrentSelectRscId = " + lLuDanceCurrentSelectRscId);
        if (!TextUtils.isEmpty(lLuDanceCurrentSelectRscId)) {
            i = 0;
            while (i < list.size()) {
                if (lLuDanceCurrentSelectRscId.equals(list.get(i).getId())) {
                    LogUtils.d(TAG, "chooseSelectPosition from last select , position = " + i);
                    i2 = i;
                    break;
                }
                i++;
            }
        } else {
            LogUtils.d(str, "chooseSelectPosition from selected item = null, position = 0");
        }
        this.mDefaultSelectedOption.postValue(Integer.valueOf(i2));
    }

    @Override // com.xiaopeng.lludancemanager.viewmodel.ILluDanceViewModel
    public void updateLoadingState(boolean isLoading) {
        this.mLoadingState.postValue(Boolean.valueOf(isLoading));
    }

    @Override // com.xiaopeng.lludancemanager.viewmodel.ILluDanceViewModel
    public int startPlay(LluDanceViewData lluDanceViewData) {
        SharedPreferenceUtil.setLLuDanceLastPlayRscId(lluDanceViewData.getId());
        return this.mLluViewModel.setLluDancePreview(lluDanceViewData.getEffectName());
    }

    public void startDownload(LluDanceViewData actionData) {
        ResourceBean transToResourceBean = transToResourceBean(actionData);
        actionData.setState(3);
        this.mRepository.startDownload(transToResourceBean);
    }

    public void resumeDownload(String id) {
        this.mRepository.resumeDownload(id);
    }

    public void updateDownloadView(int state, ResourceDownloadInfo resourceDownloadInfo) {
        switch (state) {
            case 2:
                updateWaiting(resourceDownloadInfo);
                return;
            case 3:
                updateDownloading(resourceDownloadInfo);
                return;
            case 4:
            default:
                return;
            case 5:
                updatePause(resourceDownloadInfo);
                return;
            case 6:
                updateDownloadFinished(resourceDownloadInfo);
                return;
            case 7:
            case 9:
                updateFail(resourceDownloadInfo);
                return;
            case 8:
                updateInstallComplete(resourceDownloadInfo);
                return;
        }
    }

    private void updateDownloadFinished(ResourceDownloadInfo resourceDownloadInfo) {
        String rscId = resourceDownloadInfo.getRscId();
        if (isLegalRscId(rscId)) {
            int positionFromMap = getPositionFromMap(rscId);
            LogUtils.d(TAG, "position :" + positionFromMap);
            if (this.mLiveDataList.getValue() == null || positionFromMap == -1) {
                return;
            }
            this.mLiveDataList.getValue().get(positionFromMap).setState(6);
            MediatorLiveData<List<LluDanceViewData>> mediatorLiveData = this.mLiveDataList;
            mediatorLiveData.postValue(mediatorLiveData.getValue());
        }
    }

    private void updateFail(ResourceDownloadInfo resourceDownloadInfo) {
        String rscId = resourceDownloadInfo.getRscId();
        if (isLegalRscId(rscId)) {
            int positionFromMap = getPositionFromMap(rscId);
            LogUtils.d(TAG, "position :" + positionFromMap);
            if (this.mLiveDataList.getValue() == null || positionFromMap == -1) {
                return;
            }
            this.mLiveDataList.getValue().get(positionFromMap).setState(7);
            MediatorLiveData<List<LluDanceViewData>> mediatorLiveData = this.mLiveDataList;
            mediatorLiveData.postValue(mediatorLiveData.getValue());
        }
    }

    private void updateInstallComplete(ResourceDownloadInfo resourceDownloadInfo) {
        String rscId = resourceDownloadInfo.getRscId();
        if (isLegalRscId(rscId)) {
            int positionFromMap = getPositionFromMap(rscId);
            LogUtils.d(TAG, "updateComplete position :" + positionFromMap);
            if (this.mLiveDataList.getValue() == null || positionFromMap == -1) {
                return;
            }
            LluDanceViewData lluDanceViewData = this.mLiveDataList.getValue().get(positionFromMap);
            String expandInstalledContent = resourceDownloadInfo.getExpandInstalledContent();
            if (!TextUtils.isEmpty(expandInstalledContent)) {
                LluDanceInstalledInfo lluDanceInstalledInfo = (LluDanceInstalledInfo) GsonUtil.fromJson(expandInstalledContent, (Class<Object>) LluDanceInstalledInfo.class);
                lluDanceViewData.setVideoPath(lluDanceInstalledInfo.getVideoPath());
                lluDanceViewData.setEffectName(lluDanceInstalledInfo.getEffectName());
            }
            lluDanceViewData.setState(8);
            MediatorLiveData<List<LluDanceViewData>> mediatorLiveData = this.mLiveDataList;
            mediatorLiveData.postValue(mediatorLiveData.getValue());
        }
    }

    private boolean isLegalRscId(String rscId) {
        if (rscId != null && !rscId.equals(Constant.DEFAULT_ERROR_RSC_ID)) {
            return this.mPositionMap.get(rscId) != null;
        }
        LogUtils.d(TAG, "isLegalRscId, rscId = " + rscId);
        return false;
    }

    private void updatePause(ResourceDownloadInfo resourceDownloadInfo) {
        String rscId = resourceDownloadInfo.getRscId();
        if (isLegalRscId(rscId)) {
            int positionFromMap = getPositionFromMap(rscId);
            String str = TAG;
            LogUtils.d(str, "updatePause position :" + positionFromMap);
            if (this.mLiveDataList.getValue() == null || positionFromMap == -1) {
                return;
            }
            long calculateProgress = calculateProgress(resourceDownloadInfo);
            LogUtils.d(str, "updatePause position :" + positionFromMap + " progress:" + calculateProgress);
            this.mLiveDataList.getValue().get(positionFromMap).setState(5);
            this.mLiveDataList.getValue().get(positionFromMap).setDownloadedPercentage(calculateProgress);
            MediatorLiveData<List<LluDanceViewData>> mediatorLiveData = this.mLiveDataList;
            mediatorLiveData.postValue(mediatorLiveData.getValue());
        }
    }

    private void updateWaiting(ResourceDownloadInfo resourceDownloadInfo) {
        String rscId = resourceDownloadInfo.getRscId();
        if (isLegalRscId(rscId)) {
            int positionFromMap = getPositionFromMap(rscId);
            LogUtils.d(TAG, "updateWaiting position :" + positionFromMap);
            if (this.mLiveDataList.getValue() == null || positionFromMap == -1) {
                return;
            }
            this.mLiveDataList.getValue().get(positionFromMap).setState(2);
            MediatorLiveData<List<LluDanceViewData>> mediatorLiveData = this.mLiveDataList;
            mediatorLiveData.postValue(mediatorLiveData.getValue());
        }
    }

    private void updateDownloading(ResourceDownloadInfo resourceDownloadInfo) {
        long calculateProgress = calculateProgress(resourceDownloadInfo);
        if (this.mPositionMap != null) {
            String rscId = resourceDownloadInfo.getRscId();
            if (isLegalRscId(rscId)) {
                int positionFromMap = getPositionFromMap(rscId);
                LogUtils.d(TAG, "updateDownloading ---> rscId" + rscId + "  download byte:" + resourceDownloadInfo.getDownloadedBytes() + "  total byte:" + resourceDownloadInfo.getTotalBytes() + "  progress :" + calculateProgress + " current position :" + positionFromMap);
                if (this.mLiveDataList.getValue() == null || positionFromMap == -1) {
                    return;
                }
                this.mLiveDataList.getValue().get(positionFromMap).setState(3);
                this.mLiveDataList.getValue().get(positionFromMap).setDownloadedPercentage(calculateProgress);
                MediatorLiveData<List<LluDanceViewData>> mediatorLiveData = this.mLiveDataList;
                mediatorLiveData.postValue(mediatorLiveData.getValue());
            }
        }
    }

    private long calculateProgress(ResourceDownloadInfo info) {
        long calculateProgress = LluDanceRepository.calculateProgress(info);
        if (isLegalRscId(info != null ? info.getRscId() : Constant.DEFAULT_ERROR_RSC_ID)) {
            return calculateProgress;
        }
        return 0L;
    }

    private int getPositionFromMap(String rscId) {
        Integer num = this.mPositionMap.get(rscId);
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public void stopPlayLlu() {
        this.mLluViewModel.stopLluEffectPreview();
    }

    @Override // com.xiaopeng.lludancemanager.viewmodel.ILluDanceViewModel
    public void preliminaryWork() {
        if (this.mLluViewModel.isLLuSwEnabled()) {
            boolean lluAutoVolume = SharedPreferenceUtil.getLluAutoVolume();
            boolean lluAutoWindow = SharedPreferenceUtil.getLluAutoWindow();
            String str = TAG;
            LogUtils.i(str, "preliminaryWork  auto volume = " + lluAutoVolume + "   auto window = " + lluAutoWindow);
            if (lluAutoVolume) {
                LogUtils.i(str, "preliminaryWork  auto volume = 25");
                ((AudioManager) App.getInstance().getSystemService(ListWidget.EXTRA_TYPE_AUDIO)).setStreamVolume(3, 25, 0);
            }
            if (lluAutoWindow) {
                LogUtils.i(str, "preliminaryWork  auto window open");
                WindowDoorViewModel windowDoorViewModel = (WindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class);
                if (!windowDoorViewModel.isWindowLockActive()) {
                    LogUtils.i(str, "preliminaryWork windowLockActive = false");
                    this.mAutoWindowExecuted = true;
                    windowDoorViewModel.controlWindowOpen();
                    return;
                }
                NotificationHelper.getInstance().showToast(R.string.llu_dance_double_confirm_dialog_auto_window_lock_active);
                return;
            }
            return;
        }
        NotificationHelper.getInstance().showToast(R.string.llu_effect_sw_disable);
    }

    private ResourceBean transToResourceBean(LluDanceViewData data) {
        ResourceBean resourceBean = new ResourceBean();
        resourceBean.setRscId(data.getId());
        resourceBean.setRscIcon(data.getImgUrl());
        resourceBean.setDownloadUrl(data.getDownloadUrl());
        resourceBean.setType(ResourceType.LLU_DANCE);
        resourceBean.setRscName(data.getTitle());
        return resourceBean;
    }

    public void pauseDownLoad(LluDanceViewData lluDanceViewData) {
        this.mRepository.pauseDownload(lluDanceViewData.getId());
        LogUtils.d(TAG, "暂停下载--->" + lluDanceViewData.toString());
    }

    public void enableWakeup() {
        try {
            SpeechClient.instance().getWakeupEngine().enableWakeupWithInfo(this.mWakeupBinder, 1, "灯舞", 2);
        } catch (Exception e) {
            LogUtils.e(TAG, "enableWakeup exception " + e);
        }
    }

    public void disableWakeup() {
        try {
            SpeechClient.instance().getWakeupEngine().disableWakeupWithInfo(this.mWakeupBinder, 1, "灯舞", "当前场景不支持语音", 2);
        } catch (Exception e) {
            LogUtils.e(TAG, "disableWakeup exception " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class McuCallbackImplForLlu implements IMcuController.Callback {
        private static final String TAG = "McuCallbackImplForLlu";
        private final WeakReference<LluDanceViewModel> mLluDanceViewModelWeakReference;

        public McuCallbackImplForLlu(LluDanceViewModel lluDanceViewModel) {
            this.mLluDanceViewModelWeakReference = new WeakReference<>(lluDanceViewModel);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController.Callback
        public void onIgStatusChanged(final int state) {
            LogUtils.d(TAG, "ig status changed = " + state);
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.lludancemanager.viewmodel.-$$Lambda$LluDanceViewModel$McuCallbackImplForLlu$b1XL9G93BAtwMS5X2hReTXH-gwk
                @Override // java.lang.Runnable
                public final void run() {
                    LluDanceViewModel.McuCallbackImplForLlu.this.lambda$onIgStatusChanged$0$LluDanceViewModel$McuCallbackImplForLlu(state);
                }
            });
        }

        public /* synthetic */ void lambda$onIgStatusChanged$0$LluDanceViewModel$McuCallbackImplForLlu(final int state) {
            LluDanceViewModel lluDanceViewModel = this.mLluDanceViewModelWeakReference.get();
            if (lluDanceViewModel != null) {
                if (state == 0) {
                    LluDanceAlarmHelper.cancelAlarmForDance(App.getInstance());
                }
                lluDanceViewModel.getIgEvent().postValue(Integer.valueOf(state));
            }
        }
    }
}
