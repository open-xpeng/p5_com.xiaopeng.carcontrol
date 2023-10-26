package com.xiaopeng.lludancemanager.viewmodel;

import android.util.Log;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel;
import com.xiaopeng.lludancemanager.bean.LluAiLampBean;
import com.xiaopeng.lludancemanager.bean.LluAiUserManualBean;
import com.xiaopeng.lludancemanager.model.AILluRepository;
import com.xiaopeng.xuimanager.smart.SmartManager;
import java.util.List;

/* loaded from: classes2.dex */
public class AILluViewModel extends ViewModel implements SmartManager.SmartCommonEventListener {
    private static final int DEFAULT_CODE = 0;
    public static final int MAX_LAMP_BEAM_INDEX = 13;
    private static final String TAG = "AILluViewModel";
    private int mCurrentLampIndex;
    private final MutableLiveData<Boolean> mExperienceState = new MutableLiveData<>();
    private final MutableLiveData<LluAiLampBean> mLampMusicState = new MutableLiveData<>();
    private List<LluAiLampBean> mLluAiLampBeans;
    private final MediatorLiveData<List<LluAiLampBean>> mLluAiLampMediatorLiveData;
    private final MediatorLiveData<List<LluAiUserManualBean>> mLluAiUserManualLiveData;
    private LluViewModel mLluViewModel;
    private AILluRepository mRepository;

    public AILluViewModel() {
        MediatorLiveData<List<LluAiLampBean>> mediatorLiveData = new MediatorLiveData<>();
        this.mLluAiLampMediatorLiveData = mediatorLiveData;
        MediatorLiveData<List<LluAiUserManualBean>> mediatorLiveData2 = new MediatorLiveData<>();
        this.mLluAiUserManualLiveData = mediatorLiveData2;
        this.mCurrentLampIndex = -1;
        this.mRepository = new AILluRepository();
        this.mLluViewModel = (LluViewModel) ViewModelManager.getInstance().getViewModelImpl(ILluViewModel.class);
        mediatorLiveData.addSource(this.mRepository.getLluAiLampMutableLiveData(), new Observer() { // from class: com.xiaopeng.lludancemanager.viewmodel.-$$Lambda$AILluViewModel$lLRzb_JwlU8EogMNbJW0o3zqclk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AILluViewModel.this.updateLluAiLampData((List) obj);
            }
        });
        mediatorLiveData2.addSource(this.mRepository.getUserManualMutableLiveData(), new Observer() { // from class: com.xiaopeng.lludancemanager.viewmodel.-$$Lambda$AILluViewModel$JrO8IgnetXzxe2hz-KX8c9obsgE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AILluViewModel.this.updateLluUserManual((List) obj);
            }
        });
    }

    public MediatorLiveData<List<LluAiUserManualBean>> getLluAiUserManualLiveData() {
        return this.mLluAiUserManualLiveData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLluUserManual(List<LluAiUserManualBean> lluAiUserManualBeans) {
        if (lluAiUserManualBeans != null) {
            this.mLluAiUserManualLiveData.postValue(lluAiUserManualBeans);
        }
    }

    public MediatorLiveData<List<LluAiLampBean>> getLluAiLampMediatorLiveData() {
        return this.mLluAiLampMediatorLiveData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLluAiLampData(List<LluAiLampBean> aiLampBeans) {
        if (aiLampBeans != null) {
            this.mLluAiLampBeans = aiLampBeans;
            this.mLluAiLampMediatorLiveData.postValue(aiLampBeans);
        }
    }

    public MutableLiveData<LluAiLampBean> getLampMusicState() {
        return this.mLampMusicState;
    }

    public MutableLiveData<Boolean> getExperienceState() {
        return this.mExperienceState;
    }

    public void updateExperienceState(boolean isExperiencing) {
        LogUtils.d("updateExperienceState", "isExperiencing:" + isExperiencing);
        this.mExperienceState.postValue(Boolean.valueOf(isExperiencing));
    }

    public void startAiLlu(int type) {
        this.mRepository.startAILlu(type);
    }

    public void stopAiLlu() {
        this.mRepository.stopAILlu();
    }

    public void registerAILluListener() {
        this.mRepository.registerAILluListener(this);
    }

    public void unRegisterAILluListener() {
        this.mRepository.unRegisterAILluListener(this);
    }

    public void onCommonEvent(int lluType, int aiAlgorithmType, int code) {
        Log.d(TAG, "onCommonEvent:llu_type:" + lluType + " aiAlgorithmType:" + aiAlgorithmType + " code:" + code);
        if (lluType == 4096) {
            parseCode(code);
        }
    }

    private void parseCode(int code) {
        if (code == 0) {
            updateExperienceState(false);
        } else if (code > 0) {
            updateLampValue(code);
        }
    }

    private void updateLampValue(int index) {
        if (index > 13) {
            Log.e(TAG, "updateLamp : invalid index = " + index);
            return;
        }
        int i = index - 1;
        if (i == this.mCurrentLampIndex) {
            Log.e(TAG, "updateLamp；current index is same");
            return;
        }
        this.mCurrentLampIndex = i;
        List<LluAiLampBean> list = this.mLluAiLampBeans;
        if (list == null || list.size() == 0) {
            Log.e(TAG, "onCommonEvent current mLluAiLampBeans is null");
        } else {
            this.mLampMusicState.postValue(this.mLluAiLampBeans.get(this.mCurrentLampIndex));
        }
    }
}
