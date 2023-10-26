package com.xiaopeng.carcontrol.viewmodel.xpu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.bean.xpilot.map.CngpMapItem;
import com.xiaopeng.carcontrol.bean.xpilot.map.CngpMapName;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IXpuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuIslaMode;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;
import com.xiaopeng.carcontrolmodule.R;
import java.util.List;

/* loaded from: classes2.dex */
public class XpuViewModel implements IXpuViewModel, IXpuController.Callback {
    private static final String TAG = "XpuViewModel";
    private IXpuController mXpuController;
    private final MutableLiveData<ScuResponse> mRaeb = new MutableLiveData<>();
    private final MutableLiveData<LssSensitivity> mLssSenData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mNra = new MutableLiveData<>();
    private final MutableLiveData<Integer> mNraState = new MutableLiveData<>();
    private final MutableLiveData<ScuIslaMode> mIsla = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mRemoteCarCallSw = new MutableLiveData<>();
    private final MutableLiveData<NedcState> mNedcStateData = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mCityNgp = new MutableLiveData<>();
    private final MutableLiveData<Integer> mNgpOvertakeMode = new MutableLiveData<>();
    private final MutableLiveData<Integer> mNgpPreferLaneMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mSoundSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mVoiceSwData = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mLccCrossBarriers = new MutableLiveData<>();
    private final MutableLiveData<ScuResponse> mLccTrafficLight = new MutableLiveData<>();
    private final MutableLiveData<List<CngpMapItem>> mCngpMapItemList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mXpuConnected = new MutableLiveData<>();
    private final MutableLiveData<Integer> mNgpCustomSpeedMode = new MutableLiveData<>();
    private final MutableLiveData<Integer> mNgpCustomSpeedKph = new MutableLiveData<>();
    private final MutableLiveData<Integer> mNgpCustomSpeedPercent = new MutableLiveData<>();

    private int converSpeedFromXpu2UI(int speed) {
        if (speed != 1) {
            if (speed != 2) {
                if (speed != 4) {
                    return speed != 5 ? 0 : 10;
                }
                return 5;
            }
            return -5;
        }
        return -10;
    }

    private int convertLssSensitivityFromUiCmd(int value) {
        if (value != 0) {
            return value != 2 ? 0 : 2;
        }
        return 1;
    }

    private int convertLssSensitivityToUiCmd(int value) {
        if (value != 1) {
            return value != 2 ? 1 : 2;
        }
        return 0;
    }

    private int convertSpeedFromUI2Xpu(int speed) {
        if (speed != -10) {
            if (speed != -5) {
                if (speed != 5) {
                    return speed != 10 ? 3 : 5;
                }
                return 4;
            }
            return 2;
        }
        return 1;
    }

    public XpuViewModel() {
        IXpuController iXpuController = (IXpuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_XPU_SERVICE);
        this.mXpuController = iXpuController;
        iXpuController.registerCallback(this);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setRaebEnable(boolean enable) {
        ScuResponse raebState = getRaebState();
        LogUtils.i(TAG, "setRaebEnable currentState = " + raebState, false);
        if (raebState != null) {
            int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[raebState.ordinal()];
            if (i == 1) {
                this.mXpuController.setRaebState(false);
                return;
            } else if (i == 2) {
                this.mXpuController.setRaebState(true);
                return;
            } else {
                NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
                return;
            }
        }
        this.mXpuController.setRaebState(true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public int getLssSensitivity() {
        return convertLssSensitivityToUiCmd(this.mXpuController.getLssSensitivity());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setLssSensitivity(int value) {
        int convertLssSensitivityFromUiCmd = convertLssSensitivityFromUiCmd(value);
        if (value < 0 || value > 2) {
            LogUtils.w(TAG, "setLssSensitivity failed, invalid sensitivity: " + value, false);
        }
        this.mXpuController.setLssSensitivity(convertLssSensitivityFromUiCmd);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public ScuResponse getRaebState() {
        return ScuResponse.fromScuState(this.mXpuController.getRaebState());
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.xpu.XpuViewModel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse;

        static {
            int[] iArr = new int[ScuIslaMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode = iArr;
            try {
                iArr[ScuIslaMode.OFF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode[ScuIslaMode.SLA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode[ScuIslaMode.ISLA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[ScuResponse.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse = iArr2;
            try {
                iArr2[ScuResponse.ON.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[ScuResponse.OFF.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setSimpleSasMode(ScuIslaMode islaMode) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode[islaMode.ordinal()];
        if (i == 1) {
            this.mXpuController.setSimpleSasSw(1);
        } else if (i == 2) {
            this.mXpuController.setSimpleSasSw(2);
        } else if (i != 3) {
        } else {
            this.mXpuController.setSimpleSasSw(3);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public ScuIslaMode getSimpleSasMode() {
        return ScuIslaMode.fromScuState(this.mXpuController.getSimpleSasSw());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setNraSwEnable(boolean enable) {
        this.mXpuController.setNraSwEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public boolean getTurnAssistantSw() {
        return this.mXpuController.getTurnAssistantSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setTurnAssistantEnable(boolean enable) {
        this.mXpuController.setTurnAssistantEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public ScuResponse getRemoteCarCallSw() {
        return ScuResponse.fromScuState(this.mXpuController.getRemoteCarCallSw());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setRemoteCalCallSw(boolean enable) {
        this.mXpuController.setRemoteCalCallSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public boolean getNraSw() {
        return this.mXpuController.getNraSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setNraState(int state) {
        this.mXpuController.setNraState(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public int getNraState() {
        return this.mXpuController.getNraState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setNedcSwitch(boolean active) {
        this.mXpuController.setNedcSwitch(active);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public NedcState getNedcState() {
        try {
            NedcState fromXpuCmd = NedcState.fromXpuCmd(this.mXpuController.getNedcSwitchStatus());
            if (fromXpuCmd == null) {
                LogUtils.e(TAG, "getNedcState on Error Nedc State: " + fromXpuCmd, false);
                return NedcState.Off;
            }
            return fromXpuCmd;
        } catch (Error | Exception unused) {
            LogUtils.e(TAG, "getNedcState failed, return off", false);
            return NedcState.Off;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public boolean isXpuNedcActivated() {
        NedcState nedcState;
        return (!CarBaseConfig.getInstance().isSupportXpuNedc() || (nedcState = getNedcState()) == NedcState.Off || nedcState == NedcState.Failure) ? false : true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public ScuResponse getLccCrossBarriersSw() {
        return ScuResponse.fromScuState(this.mXpuController.getLccCrossBarriersSw());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setLccCrossBarriersSw(boolean enable) {
        this.mXpuController.setLccCrossBarriersSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public ScuResponse getLccTrafficLightSw() {
        return ScuResponse.fromScuState(this.mXpuController.getLccTrafficLightSw());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setLccTrafficLightSw(boolean enable) {
        this.mXpuController.setLccTrafficLightSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void getAllCities() {
        this.mXpuController.getAllCities();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void downloadCity(int id) {
        this.mXpuController.downloadCity(id);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void deleteCity(int id) {
        this.mXpuController.deleteCity(id);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public boolean getCngpSafeExamResult() {
        return this.mXpuController.getCngpSafeExamResult(null);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public boolean getXngpSafeExamResult() {
        return this.mXpuController.getXngpSafeExamResult(null);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setCityNgpSw(boolean enable) {
        this.mXpuController.setCityNgpSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public ScuResponse getCityNgpSt() {
        return ScuResponse.fromScuState(this.mXpuController.getCityNgpState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public boolean getXpuConnected() {
        return this.mXpuController.getXpuConnected();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public int getNgpCustomSpeedMode() {
        return this.mXpuController.getNgpCustomSpeedMode() - 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setNgpCustomSpeedMode(int mode) {
        this.mXpuController.setNgpCustomSpeedMode(mode + 1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public int getNgpCustomSpeedKph() {
        return converSpeedFromXpu2UI(this.mXpuController.getNgpCustomSpeedKph());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setNgpCustomSpeedKph(int value) {
        this.mXpuController.setNgpCustomSpeedKph(convertSpeedFromUI2Xpu(value));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public int getNgpCustomSpeedPercent() {
        return converSpeedFromXpu2UI(this.mXpuController.getNgpCustomSpeedPercent());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setNgpCustomSpeedPercent(int value) {
        this.mXpuController.setNgpCustomSpeedPercent(convertSpeedFromUI2Xpu(value));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public int getNgpOvertakeMode() {
        return this.mXpuController.getNgpOvertakeMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setNgpOvertakeMode(int mode) {
        this.mXpuController.setNgpOvertakeMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public int getNgpPreferLaneCfg() {
        return this.mXpuController.getNgpPreferLaneCfg();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setNgpPreferLaneCfg(int mode) {
        this.mXpuController.setNgpPreferLaneCfg(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public boolean getSoundSw() {
        return this.mXpuController.getSoundSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setSoundSw(boolean enable) {
        this.mXpuController.setSoundSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public boolean getVoiceSw() {
        return this.mXpuController.getVoiceSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public void setVoiceSw(boolean enable) {
        this.mXpuController.setVoiceSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public int getAsLockScenario() {
        return this.mXpuController.getAsLockScenario();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public int getAsTargetMinHeightRequest() {
        return this.mXpuController.getAsTargetMinHeightRequest();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel
    public int getAsTargetMaxHeightRequest() {
        return this.mXpuController.getAsTargetMaxHeightRequest();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onNedcStateChanged(int state) {
        try {
            NedcState fromXpuCmd = NedcState.fromXpuCmd(state);
            if (fromXpuCmd != null) {
                this.mNedcStateData.postValue(fromXpuCmd);
                if (fromXpuCmd == NedcState.TurnOnFailure) {
                    NotificationHelper.getInstance().showToastLong(R.string.xpilot_xpu_start_fail, true);
                }
            } else {
                LogUtils.e(TAG, "on Error Nedc State: " + state, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onCityNgpSwChanged(int state) {
        this.mCityNgp.postValue(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onRaebSwChanged(int state) {
        this.mRaeb.postValue(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onLssSenChanged(int sensitivity) {
        this.mLssSenData.postValue(LssSensitivity.fromXpuState(sensitivity));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onNraSwChanged(boolean enabled) {
        this.mNra.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onNraStateChanged(int state) {
        this.mNraState.postValue(Integer.valueOf(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onSimpleSasStChanged(int state) {
        handleIslaStateChanged(ScuIslaMode.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onRemoteCarCallStChanged(int state) {
        handleRemoteCarCallStChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onNgpOvertakeChanged(int mode) {
        this.mNgpOvertakeMode.postValue(Integer.valueOf(mode));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onNgpPreferLaneChanged(int mode) {
        this.mNgpPreferLaneMode.postValue(Integer.valueOf(mode));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onSoundSwChanged(boolean enable) {
        this.mSoundSwData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onVoiceSwChanged(boolean enable) {
        this.mVoiceSwData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void handleLccBarriersChanged(int state) {
        this.mLccCrossBarriers.postValue(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void handleLccTrafficLightChanged(int state) {
        this.mLccTrafficLight.postValue(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onCngpMapCtrlResponse(List<CngpMapItem> list) {
        this.mCngpMapItemList.postValue(list);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onCngpMapFinishNotify(int id, int status) {
        if (status == 2) {
            LogUtils.i(TAG, "NotificationHelper show SCENE_CNGP_MAP_DOWNLOAD");
            NotificationHelper.getInstance().sendMessageToMessageCenter(NotificationHelper.SCENE_CNGP_MAP_DOWNLOAD, String.format(ResUtils.getString(R.string.cngp_map_download_finish_remind_title), CngpMapName.convertIdToName(id)), ResUtils.getString(R.string.cngp_map_download_finish_content), ResUtils.getString(R.string.cngp_map_download_finish_content), ResUtils.getString(R.string.smart_drive_score_remind_wake_words), ResUtils.getString(R.string.smart_drive_score_remind_response_tts), ResUtils.getString(R.string.smart_drive_score_remind_btn_title), false, 0L, null);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onXpuConnectedChanged(boolean connected) {
        this.mXpuConnected.postValue(Boolean.valueOf(connected));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onNgpCustomSpeedModeChanged(int mode) {
        this.mNgpCustomSpeedMode.postValue(Integer.valueOf(mode - 1));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onNgpCustomSpeedKphChanged(int value) {
        this.mNgpCustomSpeedKph.postValue(Integer.valueOf(converSpeedFromXpu2UI(value)));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
    public void onNgpCustomSpeedPercentChanged(int value) {
        this.mNgpCustomSpeedPercent.postValue(Integer.valueOf(converSpeedFromXpu2UI(value)));
    }

    public LiveData<NedcState> getNedcStateData() {
        return this.mNedcStateData;
    }

    public LiveData<ScuResponse> getCityNgpData() {
        return this.mCityNgp;
    }

    public LiveData<ScuResponse> getRaebData() {
        return this.mRaeb;
    }

    public LiveData<LssSensitivity> getLssSenData() {
        return this.mLssSenData;
    }

    public LiveData<Boolean> getNraData() {
        return this.mNra;
    }

    public LiveData<Integer> getNraStateData() {
        return this.mNraState;
    }

    public LiveData<ScuIslaMode> getIslaData() {
        return this.mIsla;
    }

    public LiveData<ScuResponse> getRemoteCarCallData() {
        return this.mRemoteCarCallSw;
    }

    public LiveData<Integer> getNgpOvertakeData() {
        return this.mNgpOvertakeMode;
    }

    public LiveData<Integer> getNgpPreferLaneData() {
        return this.mNgpPreferLaneMode;
    }

    public LiveData<Boolean> getSoundSwData() {
        return this.mSoundSwData;
    }

    public LiveData<Boolean> getVoiceSwData() {
        return this.mVoiceSwData;
    }

    public LiveData<ScuResponse> getLccCrossBarriersData() {
        return this.mLccCrossBarriers;
    }

    public LiveData<ScuResponse> getLccTrafficLightData() {
        return this.mLccTrafficLight;
    }

    public LiveData<List<CngpMapItem>> getCngpMapItemList() {
        return this.mCngpMapItemList;
    }

    public LiveData<Boolean> getXpuConnectedData() {
        return this.mXpuConnected;
    }

    public LiveData<Integer> getNgpCustomSpeedModeData() {
        return this.mNgpCustomSpeedMode;
    }

    public LiveData<Integer> getNgpCustomSpeedKphData() {
        return this.mNgpCustomSpeedKph;
    }

    public LiveData<Integer> getNgpCustomSpeedPercentData() {
        return this.mNgpCustomSpeedPercent;
    }

    private void handleIslaStateChanged(ScuIslaMode islaMode) {
        this.mIsla.postValue(islaMode);
    }

    private void handleRemoteCarCallStChanged(ScuResponse state) {
        this.mRemoteCarCallSw.postValue(state);
    }
}
