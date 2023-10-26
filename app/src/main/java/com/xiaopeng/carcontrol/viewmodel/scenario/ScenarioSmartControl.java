package com.xiaopeng.carcontrol.viewmodel.scenario;

import android.content.Intent;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.jarvisproto.DMWait;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* loaded from: classes2.dex */
public class ScenarioSmartControl {
    private static final String TAG = "ScenarioSmartControl";
    private volatile boolean mInScenario;
    private ScenarioViewModel mScenarioViewModel;
    private SpaceCapsuleViewModel mSpaceViewModel;
    private VcuViewModel mVcuViewModel;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingleHolder {
        private static final ScenarioSmartControl INSTANCE = new ScenarioSmartControl();

        private SingleHolder() {
        }
    }

    private ScenarioSmartControl() {
        this.mInScenario = false;
        this.mSpaceViewModel = (SpaceCapsuleViewModel) ViewModelManager.getInstance().getViewModelImpl(ISpaceCapsuleViewModel.class);
        this.mVcuViewModel = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
        this.mScenarioViewModel = (ScenarioViewModel) ViewModelManager.getInstance().getViewModelImpl(IScenarioViewModel.class);
        this.mInScenario = false;
    }

    public static ScenarioSmartControl getInstance() {
        return SingleHolder.INSTANCE;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void parseScenarioIntent(Intent intent) {
        String stringExtra;
        String stringExtra2;
        String stringExtra3;
        String stringExtra4;
        String stringExtra5;
        String stringExtra6;
        boolean equals;
        char c;
        boolean z;
        boolean z2;
        try {
            stringExtra = intent.getStringExtra("scenario_name");
            stringExtra2 = intent.getStringExtra("status");
            stringExtra3 = intent.getStringExtra("source");
            stringExtra4 = intent.getStringExtra("exitReason");
            stringExtra5 = intent.getStringExtra("extraInfo");
            stringExtra6 = intent.getStringExtra("friendScenario");
            LogUtils.d(TAG, "friendScenario: " + stringExtra6);
            equals = "voice".equals(stringExtra3);
            switch (stringExtra.hashCode()) {
                case -1943876483:
                    if (stringExtra.equals(IScenarioController.SCENARIO_TRAILED_MODE)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -631527828:
                    if (stringExtra.equals(IScenarioController.SPACE_CAPSULE_MOVIE_MODE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -626092525:
                    if (stringExtra.equals(IScenarioController.SPACE_CAPSULE_SLEEP_MODE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -352180192:
                    if (stringExtra.equals(IScenarioController.SCENARIO_VIPSEAT_MODE)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 885092324:
                    if (stringExtra.equals(IScenarioController.MEDITATION_MODE)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1378112983:
                    if (stringExtra.equals(IScenarioController.VICE_MEDITATION_MODE)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "parse exception=" + e);
            return;
        }
        if (c == 0) {
            z = false;
            if (DMWait.STATUS_ENTER.equals(stringExtra2)) {
                startSeatPrepare(true, false, equals, 1, null, stringExtra5);
            } else if ("exit".equals(stringExtra2)) {
                startSeatPrepare(false, false, equals, 1, stringExtra4, stringExtra5);
            }
        } else if (c == 1) {
            z = false;
            if (DMWait.STATUS_ENTER.equals(stringExtra2)) {
                startSeatPrepare(true, false, equals, 2, null, stringExtra5);
            } else if ("exit".equals(stringExtra2)) {
                startSeatPrepare(false, false, equals, 2, stringExtra4, stringExtra5);
            }
        } else {
            if (c != 2) {
                if (c == 3) {
                    boolean equals2 = IScenarioController.VICE_MEDITATION_MODE.equals(stringExtra6);
                    if (DMWait.STATUS_ENTER.equals(stringExtra2)) {
                        z2 = false;
                        startMeditationMode(true, false, equals2);
                    } else {
                        z2 = false;
                        if ("exit".equals(stringExtra2)) {
                            startMeditationMode(false, false, equals2);
                        }
                    }
                    z = z2;
                } else if (c == 4) {
                    boolean equals3 = IScenarioController.MEDITATION_MODE.equals(stringExtra6);
                    if (DMWait.STATUS_ENTER.equals(stringExtra2)) {
                        startMeditationMode(true, true, equals3);
                    } else if ("exit".equals(stringExtra2)) {
                        startMeditationMode(false, true, equals3);
                    }
                } else if (c == 5) {
                    if (DMWait.STATUS_ENTER.equals(stringExtra2)) {
                        startRescueMode(true);
                    } else if ("exit".equals(stringExtra2)) {
                        startRescueMode(false);
                    }
                }
                z = false;
            } else {
                if (DMWait.STATUS_ENTER.equals(stringExtra2)) {
                    startVipSeat(true, equals, null);
                } else if ("exit".equals(stringExtra2)) {
                    z = false;
                    startVipSeat(false, equals, stringExtra4);
                }
                z = false;
            }
            LogUtils.e(TAG, "parse exception=" + e);
            return;
        }
        LogUtils.i(TAG, "scenario request: " + stringExtra + ", status: " + stringExtra2 + ", source: " + stringExtra3 + ", exitReason: " + stringExtra4, z);
    }

    private void startVipSeat(final boolean start, final boolean isVoiceSource, final String reason) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.scenario.-$$Lambda$ScenarioSmartControl$c1r4q9DNDOX-6vxcJ3DzCTm-SEQ
            @Override // java.lang.Runnable
            public final void run() {
                ScenarioSmartControl.this.lambda$startVipSeat$0$ScenarioSmartControl(start, isVoiceSource, reason);
            }
        });
    }

    public /* synthetic */ void lambda$startVipSeat$0$ScenarioSmartControl(final boolean start, final boolean isVoiceSource, final String reason) {
        LogUtils.d(TAG, "startVipSeat start: " + start + ", mInScenario: " + this.mInScenario);
        if (this.mInScenario == start) {
            return;
        }
        this.mInScenario = start;
        if (BaseFeatureOption.getInstance().isSupportNapa()) {
            if (start) {
                this.mScenarioViewModel.handleNapaVipSeatModeStart(isVoiceSource);
            } else {
                this.mScenarioViewModel.handleNapaVipSeatModeEnd(reason);
            }
        } else if (!start && this.mSpaceViewModel.getCurrentSubMode() == -1) {
            LogUtils.i(TAG, "Current Scenario already is idle, and no need to exit", false);
            this.mScenarioViewModel.reportScenarioStatus(ScenarioMode.VipSeat.toScenarioStr(), 0);
        } else {
            Intent intent = new Intent(GlobalConstant.ACTION.ACTION_VIP_SEAT);
            intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_MODE_STATUS, start);
            if (!start) {
                intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_EXIT_REASON, "rGearNotP".equals(reason));
            }
            intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
            intent.addFlags(1024);
            App.getInstance().startActivity(intent);
        }
    }

    private void startSeatPrepare(final boolean start, final boolean vipSeat, final boolean isVoiceSource, final int modeIndex, final String reason, final String info) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.scenario.-$$Lambda$ScenarioSmartControl$ZnUv3zZRKi2xQ2rxfyLZyEd--xw
            @Override // java.lang.Runnable
            public final void run() {
                ScenarioSmartControl.this.lambda$startSeatPrepare$1$ScenarioSmartControl(start, modeIndex, info, reason, vipSeat, isVoiceSource);
            }
        });
    }

    public /* synthetic */ void lambda$startSeatPrepare$1$ScenarioSmartControl(final boolean start, final int modeIndex, final String info, final String reason, final boolean vipSeat, final boolean isVoiceSource) {
        String scenarioStr;
        LogUtils.d(TAG, "startSeatPrepare start: " + start + ", mInScenario: " + this.mInScenario);
        if (this.mInScenario == start) {
            return;
        }
        this.mInScenario = start;
        if (BaseFeatureOption.getInstance().isSupportNapa()) {
            if (start) {
                if (modeIndex == 1) {
                    this.mScenarioViewModel.handleNapaSleepModeStart(info != null ? info : "");
                } else if (modeIndex == 2) {
                    this.mScenarioViewModel.handleNapaCinemaModeStart(info != null ? info : "");
                }
            } else if (modeIndex == 1) {
                this.mScenarioViewModel.handleNapaSleepModeEnd(reason);
            } else if (modeIndex == 2) {
                this.mScenarioViewModel.handleNapaCinemaModeEnd(reason);
            }
        } else if (!start && this.mSpaceViewModel.getCurrentSubMode() == -1) {
            LogUtils.i(TAG, "Current Scenario already is idle, and no need to exit", false);
            ScenarioViewModel scenarioViewModel = this.mScenarioViewModel;
            if (vipSeat) {
                scenarioStr = ScenarioMode.VipSeat.toScenarioStr();
            } else {
                scenarioStr = (modeIndex == 1 ? ScenarioMode.SpaceCapsuleSleep : ScenarioMode.SpaceCapsuleCinema).toScenarioStr();
            }
            scenarioViewModel.reportScenarioStatus(scenarioStr, 0);
        } else {
            if (start && !vipSeat && this.mSpaceViewModel.getCurrentSubMode() == -1) {
                if (this.mVcuViewModel.isInDcCharge()) {
                    NotificationHelper.getInstance().showToast(modeIndex == 1 ? R.string.space_capsule_sleep_dc_charge_no_enter_tips : R.string.space_capsule_cinema_dc_charge_no_enter_tips);
                    SpeechHelper.getInstance().speak(ResUtils.getString(modeIndex == 1 ? R.string.space_capsule_sleep_dc_charge_no_enter_tips : R.string.space_capsule_cinema_dc_charge_no_enter_tips));
                    if (this.mSpaceViewModel.getCurrentSubMode() == -1) {
                        exitSpaceCapsule(modeIndex == 1, "rInDcCharge");
                        return;
                    }
                    return;
                } else if (this.mVcuViewModel.getAvailableMileage() < 60) {
                    NotificationHelper.getInstance().showToast(R.string.space_capsule_low_battery_no_enter_tips);
                    SpeechHelper.getInstance().speak(ResUtils.getString(R.string.space_capsule_low_battery_no_enter_tips));
                    if (this.mSpaceViewModel.getCurrentSubMode() == -1) {
                        exitSpaceCapsule(modeIndex == 1, "rBatteryLow");
                        return;
                    }
                    return;
                }
            }
            startCapsuleActivity(start, vipSeat, isVoiceSource, modeIndex, reason, -1);
        }
    }

    public void startCapsuleActivity(boolean start, boolean vipSeat, boolean isVoiceSource, int modeIndex, String reason, int chooseBedType) {
        Intent intent = new Intent(GlobalConstant.ACTION.ACTION_SPACE_CAPSULE);
        intent.putExtra(GlobalConstant.EXTRA.KEY_VIP_SEAT, vipSeat);
        intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_MODE_STATUS, start);
        intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_SOURCE, isVoiceSource);
        intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_CHOOSE_BED, chooseBedType);
        if (start) {
            intent.putExtra(GlobalConstant.EXTRA.KEY_MODE_TYPE, modeIndex);
        } else {
            intent.putExtra(GlobalConstant.EXTRA.KEY_SPACE_EXIT_REASON, "rGearNotP".equals(reason));
        }
        intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
        intent.addFlags(1024);
        App.getInstance().startActivity(intent);
    }

    private void startMeditationMode(final boolean start, final boolean vice, final boolean switchMode) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.scenario.-$$Lambda$ScenarioSmartControl$QdZ7hrTByfqY1QefD-iImSrmWuI
            @Override // java.lang.Runnable
            public final void run() {
                ScenarioSmartControl.this.lambda$startMeditationMode$2$ScenarioSmartControl(start, vice, switchMode);
            }
        });
    }

    public /* synthetic */ void lambda$startMeditationMode$2$ScenarioSmartControl(final boolean start, final boolean vice, final boolean switchMode) {
        LogUtils.d(TAG, "startMeditationMode start: " + start + ", mInScenario: " + this.mInScenario + ", vice: " + vice + ", switchMode: " + switchMode);
        if (this.mInScenario == start) {
            return;
        }
        this.mInScenario = start;
        if (BaseFeatureOption.getInstance().isSupportNapa()) {
            this.mScenarioViewModel.handleNapaMeditationMode(start, vice, switchMode);
        } else if (start) {
            Intent intent = new Intent(BaseFeatureOption.getInstance().isSupportMeditationPlus() ? GlobalConstant.ACTION.ACTION_MEDITATION_MODE_PLUS : GlobalConstant.ACTION.ACTION_MEDITATION_MODE);
            intent.putExtra(GlobalConstant.EXTRA.KEY_MEDITATION_MODE_STATUS, true);
            intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
            intent.addFlags(1024);
            App.getInstance().startActivity(intent);
        } else {
            App.getInstance().getApplicationContext().sendBroadcast(new Intent(GlobalConstant.ACTION.ACTION_MEDITATION_MODE_EXIT));
            this.mScenarioViewModel.reportScenarioStatus(ScenarioMode.Meditation.toScenarioStr(), 0);
        }
    }

    private void exitSpaceCapsule(boolean isSleep, String reason) {
        StringBuilder sb;
        ScenarioMode scenarioMode;
        ScenarioViewModel scenarioViewModel = this.mScenarioViewModel;
        if (isSleep) {
            sb = new StringBuilder();
            scenarioMode = ScenarioMode.SpaceCapsuleSleep;
        } else {
            sb = new StringBuilder();
            scenarioMode = ScenarioMode.SpaceCapsuleCinema;
        }
        scenarioViewModel.startScenario(false, sb.append(scenarioMode.toScenarioStr()).append(MqttTopic.MULTI_LEVEL_WILDCARD).append(reason).toString());
        this.mScenarioViewModel.reportScenarioStatus((isSleep ? ScenarioMode.SpaceCapsuleSleep : ScenarioMode.SpaceCapsuleCinema).toScenarioStr(), 0);
    }

    private void startRescueMode(final boolean start) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.scenario.-$$Lambda$ScenarioSmartControl$kaWaWbyJ1ktv_IJ0rMe7X_qpBuk
            @Override // java.lang.Runnable
            public final void run() {
                ScenarioSmartControl.this.lambda$startRescueMode$3$ScenarioSmartControl(start);
            }
        });
    }

    public /* synthetic */ void lambda$startRescueMode$3$ScenarioSmartControl(final boolean start) {
        LogUtils.d(TAG, "startRescueMode start: " + start + ", mInScenario: " + this.mInScenario);
        if (this.mInScenario == start) {
            return;
        }
        this.mInScenario = start;
        if (BaseFeatureOption.getInstance().isSupportNapa()) {
            this.mScenarioViewModel.handleNapaRescueMode(start);
        } else if (start) {
            Intent intent = new Intent(GlobalConstant.ACTION.ACTION_TRAILER_MODE);
            intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
            intent.addFlags(1024);
            App.getInstance().startActivity(intent);
            this.mScenarioViewModel.registerBinderObserver();
            this.mScenarioViewModel.reportScenarioStatus(ScenarioMode.Rescue.toScenarioStr(), 2);
        } else {
            App.getInstance().getApplicationContext().sendBroadcast(new Intent(GlobalConstant.ACTION.ACTION_EXIT_TRAILER_MODE));
            this.mScenarioViewModel.reportScenarioStatus(ScenarioMode.Rescue.toScenarioStr(), 0);
        }
    }
}
