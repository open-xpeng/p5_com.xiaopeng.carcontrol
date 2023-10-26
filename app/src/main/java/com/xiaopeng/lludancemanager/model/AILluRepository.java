package com.xiaopeng.lludancemanager.model;

import androidx.lifecycle.MutableLiveData;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.util.GsonUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.lludancemanager.bean.LluAiLampBean;
import com.xiaopeng.lludancemanager.bean.LluAiUserManualBean;
import com.xiaopeng.lludancemanager.util.FileUtil;
import com.xiaopeng.lludancemanager.util.ResUtil;
import com.xiaopeng.xuimanager.smart.SmartManager;
import java.util.List;

/* loaded from: classes2.dex */
public class AILluRepository {
    private static final String ASSETS_LLU_AI_CONFIG = "lludance/ai_llu_config.json";
    private static final String ASSETS_LLU_AI_USER_MANUAL = "lludance/ai_llu_welcome.json";
    private String TAG = AILluRepository.class.getSimpleName();
    private SmartManager mSmartManager = XuiClientWrapper.getInstance().getSmartManager();
    private MutableLiveData<List<LluAiLampBean>> mLluAiLampMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<LluAiUserManualBean>> mUserManualMutableLiveData = new MutableLiveData<>();

    public AILluRepository() {
        requestAiLluUserManual();
        requestAiLluResourceData();
    }

    public MutableLiveData<List<LluAiUserManualBean>> getUserManualMutableLiveData() {
        return this.mUserManualMutableLiveData;
    }

    public MutableLiveData<List<LluAiLampBean>> getLluAiLampMutableLiveData() {
        return this.mLluAiLampMutableLiveData;
    }

    private void requestAiLluUserManual() {
        List<LluAiUserManualBean> list;
        try {
            list = (List) GsonUtil.fromJson(FileUtil.loadFromAssets(ASSETS_LLU_AI_USER_MANUAL), new TypeToken<List<LluAiUserManualBean>>() { // from class: com.xiaopeng.lludancemanager.model.AILluRepository.1
            }.getType());
        } catch (Exception e) {
            LogUtils.d(this.TAG, "requestAiLluUserManual --> " + e.getMessage());
            e.printStackTrace();
            list = null;
        }
        if (list == null) {
            LogUtils.d(this.TAG, "requestAiLluUserManual --> lluAiLampBeans is null ");
        } else {
            this.mUserManualMutableLiveData.postValue(list);
        }
    }

    private void requestAiLluResourceData() {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.lludancemanager.model.AILluRepository.2
            @Override // java.lang.Runnable
            public void run() {
                AILluRepository.this.parseData();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseData() {
        List<LluAiLampBean> list;
        try {
            list = (List) GsonUtil.fromJson(FileUtil.loadFromAssets(ASSETS_LLU_AI_CONFIG), new TypeToken<List<LluAiLampBean>>() { // from class: com.xiaopeng.lludancemanager.model.AILluRepository.3
            }.getType());
        } catch (Exception e) {
            LogUtils.d(this.TAG, "requestAiLluData --> " + e.getMessage());
            e.printStackTrace();
            list = null;
        }
        if (list == null) {
            LogUtils.d(this.TAG, "requestAiLluData --> lluAiLampBeans is null ");
            return;
        }
        for (LluAiLampBean lluAiLampBean : list) {
            if (lluAiLampBean != null) {
                lluAiLampBean.setLampBgResId(ResUtil.getDrawableResByName(lluAiLampBean.getLampBg()));
                lluAiLampBean.setNoteAudioResId(ResUtil.getRawResByName(lluAiLampBean.getNoteAudio()));
                lluAiLampBean.setNoteTextResId(ResUtil.getDrawableResByName(lluAiLampBean.getNoteText()));
                LogUtils.d(this.TAG, lluAiLampBean.toString());
            }
        }
        this.mLluAiLampMutableLiveData.postValue(list);
    }

    public void startAILlu(int type) {
        SmartManager smartManager = this.mSmartManager;
        if (smartManager == null) {
            LogUtils.d(this.TAG, "startAILlu  --> SmartManager is null ");
            return;
        }
        try {
            smartManager.startAiLluMode(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopAILlu() {
        SmartManager smartManager = this.mSmartManager;
        if (smartManager == null) {
            LogUtils.d(this.TAG, "stopAILlu  --> SmartManager is null ");
            return;
        }
        try {
            smartManager.stopAiLluMode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerAILluListener(SmartManager.SmartCommonEventListener listener) {
        if (this.mSmartManager == null) {
            LogUtils.d(this.TAG, "registerAILluListener  --> SmartManager is null ");
            return;
        }
        try {
            XuiClientWrapper.getInstance().getSmartManager().registerCommonListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unRegisterAILluListener(SmartManager.SmartCommonEventListener listener) {
        SmartManager smartManager = this.mSmartManager;
        if (smartManager == null) {
            LogUtils.d(this.TAG, "unRegisterAILluListener  --> SmartManager is null ");
            return;
        }
        try {
            smartManager.unregisterCommonListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
