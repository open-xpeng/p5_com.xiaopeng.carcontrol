package com.xiaopeng.carcontrol.viewmodel.scenario;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class ScenarioViewModel implements IScenarioViewModel {
    private static final String TAG = "ScenarioViewModel";
    private final IScenarioController.Callback mScenarioCallback;
    private IScenarioController mScenarioController;
    private final MutableLiveData<Boolean> mScenarioStateData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mMeditationModeData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mRescueModeData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mVipSeatModeStartData = new MutableLiveData<>();
    private final MutableLiveData<String> mVipSeatModeEndData = new MutableLiveData<>();
    private final MutableLiveData<String> mSleepModeStartData = new MutableLiveData<>();
    private final MutableLiveData<String> mSleepModeEndData = new MutableLiveData<>();
    private final MutableLiveData<String> mCinemaModeStartData = new MutableLiveData<>();
    private final MutableLiveData<String> mCinemaModeEndData = new MutableLiveData<>();

    public ScenarioViewModel() {
        IScenarioController.Callback callback = new IScenarioController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioViewModel.1
            @Override // com.xiaopeng.carcontrol.carmanager.controller.IScenarioController.Callback
            public void onMeditationModeStateChanged(boolean start) {
                ScenarioViewModel.this.handleScenarioStateChanged(start);
            }

            @Override // com.xiaopeng.carcontrol.carmanager.controller.IScenarioController.Callback
            public void onSpaceCapsuleSleepStateChanged(boolean start) {
                ScenarioViewModel.this.handleScenarioStateChanged(start);
            }

            @Override // com.xiaopeng.carcontrol.carmanager.controller.IScenarioController.Callback
            public void onSpaceCapsuleCinemaStateChanged(boolean start) {
                ScenarioViewModel.this.handleScenarioStateChanged(start);
            }
        };
        this.mScenarioCallback = callback;
        IScenarioController iScenarioController = (IScenarioController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_USER_SCENARIO_SERVICE);
        this.mScenarioController = iScenarioController;
        iScenarioController.registerCallback(callback);
    }

    public void startScenario(boolean start, ScenarioMode mode) {
        String startScenario = startScenario(start, mode.toScenarioStr());
        if (startScenario != null && mode == ScenarioMode.VipSeat) {
            startScenario.hashCode();
            if (startScenario.equals("gearNotP") && start) {
                NotificationHelper.getInstance().showToast(R.string.vip_seat_notice_gear_p);
            }
        }
        LogUtils.i(TAG, "startScenario, start: " + start + ", ScenarioMode: " + mode.toScenarioStr() + ", result: " + startScenario, false);
    }

    public ScenarioMode getCurrentUserScenarioValue() {
        return ScenarioMode.fromScenario(getCurrentUserScenario());
    }

    public boolean isScenarioModeRunning(ScenarioMode mode) {
        if (mode != null) {
            int userScenarioStatus = this.mScenarioController.getUserScenarioStatus(mode.toScenarioStr());
            return userScenarioStatus == 1 || userScenarioStatus == 2;
        }
        return false;
    }

    public void handleNapaMeditationMode(boolean show, boolean vice, boolean switchMode) {
        int i = show ? 4 : 0;
        if (vice) {
            i |= 2;
        }
        if (switchMode) {
            i |= 1;
        }
        this.mMeditationModeData.postValue(Integer.valueOf(i));
    }

    public void handleNapaRescueMode(boolean show) {
        this.mRescueModeData.postValue(Boolean.valueOf(show));
    }

    public void handleNapaVipSeatModeStart(boolean isVoice) {
        this.mVipSeatModeStartData.postValue(Boolean.valueOf(isVoice));
    }

    public void handleNapaVipSeatModeEnd(String reason) {
        this.mVipSeatModeEndData.postValue(reason);
    }

    public void handleNapaSleepModeStart(String info) {
        this.mSleepModeStartData.postValue(info);
    }

    public void handleNapaSleepModeEnd(String reason) {
        this.mSleepModeEndData.postValue(reason);
    }

    public void handleNapaCinemaModeStart(String info) {
        this.mCinemaModeStartData.postValue(info);
    }

    public void handleNapaCinemaModeEnd(String reason) {
        this.mCinemaModeEndData.postValue(reason);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel
    public String startScenario(boolean start, String scenario) {
        return this.mScenarioController.startScenario(start, scenario);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel
    public String getCurrentUserScenario() {
        return this.mScenarioController.getCurrentUserScenario();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel
    public int getUserScenarioStatus(String scenario) {
        return this.mScenarioController.getUserScenarioStatus(scenario);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel
    public void reportScenarioStatus(String scenario, int state) {
        this.mScenarioController.reportScenarioState(scenario, state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel
    public void registerBinderObserver() {
        this.mScenarioController.registerBinderObserver();
    }

    public MutableLiveData<Boolean> getScenarioStateData() {
        return this.mScenarioStateData;
    }

    public MutableLiveData<Integer> getMeditationModeData() {
        return this.mMeditationModeData;
    }

    public MutableLiveData<Boolean> getRescueModeData() {
        return this.mRescueModeData;
    }

    public MutableLiveData<Boolean> getVipSeatModeStartData() {
        return this.mVipSeatModeStartData;
    }

    public MutableLiveData<String> getVipSeatModeEndData() {
        return this.mVipSeatModeEndData;
    }

    public MutableLiveData<String> getSleepModeStartData() {
        return this.mSleepModeStartData;
    }

    public MutableLiveData<String> getSleepModeEndData() {
        return this.mSleepModeEndData;
    }

    public MutableLiveData<String> getCinemaModeStartData() {
        return this.mCinemaModeStartData;
    }

    public MutableLiveData<String> getCinemaModeEndData() {
        return this.mCinemaModeEndData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScenarioStateChanged(boolean start) {
        this.mScenarioStateData.postValue(Boolean.valueOf(start));
        if (start) {
            return;
        }
        this.mScenarioStateData.postValue(null);
    }
}
