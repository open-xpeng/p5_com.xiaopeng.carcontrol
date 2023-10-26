package com.xiaopeng.carcontrol.speech;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.direct.ElementDirectManager;
import com.xiaopeng.carcontrol.direct.IElementDirect;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.dialog.panel.ControlPanelManager;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasEffect;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasViewModel;
import com.xiaopeng.carcontrol.viewmodel.avas.BootSoundEffect;
import com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.ArsFoldState;
import com.xiaopeng.carcontrol.viewmodel.cabin.ArsWorkMode;
import com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.MirrorFoldState;
import com.xiaopeng.carcontrol.viewmodel.cabin.MirrorReverseMode;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.TrunkType;
import com.xiaopeng.carcontrol.viewmodel.carsettings.AsEngineerMode;
import com.xiaopeng.carcontrol.viewmodel.carsettings.AsHeight;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.WiperInterval;
import com.xiaopeng.carcontrol.viewmodel.chassis.ChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampHeightLevel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluEffect;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluPreviewState;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluSayHiEffect;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModel;
import com.xiaopeng.carcontrol.viewmodel.light.AtlEffect;
import com.xiaopeng.carcontrol.viewmodel.light.AtlViewModel;
import com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.DoorKeyForCustomer;
import com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.MeterViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.XKeyForCustomer;
import com.xiaopeng.carcontrol.viewmodel.scu.ApResponse;
import com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;
import com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.DriveMode;
import com.xiaopeng.carcontrol.viewmodel.vcu.EnergyRecycleGrade;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.XSportDriveMode;
import com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.NedcState;
import com.xiaopeng.carcontrol.viewmodel.xpu.XpuViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.datalog.MoleEvent;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.speech.common.SpeechConstant;
import com.xiaopeng.speech.protocol.bean.ChangeValue;
import com.xiaopeng.speech.protocol.bean.base.CommandValue;
import com.xiaopeng.speech.protocol.event.CarcontrolEvent;
import com.xiaopeng.speech.protocol.event.MeditationMusicEvent;
import com.xiaopeng.speech.protocol.event.query.QueryCarControlEvent;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.ControlReason;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.SeatValue;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.UserBookValue;
import com.xiaopeng.speech.protocol.node.dialog.bean.DialogEndReason;
import com.xiaopeng.speech.protocol.node.dialog.bean.WakeupReason;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class CarControlSpeechModel implements ISpeechModel {
    private static final int CHARGE_PORT_CLOSED = 5;
    private static final int CHARGE_PORT_ENABLE = 1;
    private static final int CHARGE_PORT_OPENED = 4;
    private static final int CHARGE_PORT_RUNNING = 2;
    static final int CHARGE_PORT_UNABLE = 3;
    private static final int DIRECTION_BACK_DOWN = 2;
    private static final int DIRECTION_FRONT_UP = 1;
    private static final int ENERGY_RECYCLE_DISABLE = 0;
    private static final int ENERGY_RECYCLE_NORMAL = 1;
    private static final int ENERGY_RECYCLE_WITH_ABS_FAULT = 3;
    private static final int ENERGY_RECYCLE_WITH_SNOW_MODE = 2;
    private static final int H_ONE_STEP = 16;
    private static final int H_POS_MAX = 90;
    private static final int H_POS_MIN = 0;
    private static final int LEG_H_POS_MAX = 100;
    private static final int LEG_H_POS_MIN = 0;
    private static final String[] MASSAGE_MODES = {"wave", "serpentine", "pulse", "catwalk", "double", "waist"};
    static final int MIRROR_CAR_IS_RUNNING = 2;
    static final int MIRROR_FOLD_UNFOLD_UNKNOWN = 0;
    static final int MIRROR_IS_FOLD = 4;
    static final int MIRROR_IS_UNFOLD = 5;
    static final int MIRROR_IS_WORKING = 3;
    private static final int MIRROR_NO_CFG = 1;
    private static final int R_POS_MAX = 85;
    private static final int R_POS_MIN = 20;
    private static final int SDC_CONTROL_DISABLE_CAR_IS_MOVIING = 3;
    private static final int SDC_CONTROL_DISABLE_PAUSE = 2;
    private static final int SDC_CONTROL_DISABLE_PREVENT_PLAYING = 2;
    private static final int SDC_CONTROL_DISABLE_SDC_CLOSED = 7;
    private static final int SDC_CONTROL_DISABLE_SDC_IS_RUNNING = 5;
    private static final int SDC_CONTROL_DISABLE_SDC_OPEN = 6;
    private static final int SDC_CONTROL_ENABLE = 1;
    private static final int SDC_CONTROL_NOT_SUPPORT = 0;
    static final int SEAT_MAX_ERROR = 2;
    private static final int SLIDE_DOOR_CONTROL_CLOSE = 0;
    private static final int SLIDE_DOOR_CONTROL_OPEN = 1;
    private static final int SLIDE_DOOR_CONTROL_PAUSE = 2;
    private static final int SLIDE_DOOR_NOT_SUPPORT = 0;
    private static final int SLIDE_DOOR_STATUS_CLOSED = 7;
    private static final int SLIDE_DOOR_STATUS_CLOSING = 4;
    private static final int SLIDE_DOOR_STATUS_LOCKED = 1;
    private static final int SLIDE_DOOR_STATUS_MANUAL = 2;
    private static final int SLIDE_DOOR_STATUS_OPENED = 6;
    private static final int SLIDE_DOOR_STATUS_OPENING = 3;
    private static final int SLIDE_DOOR_STATUS_PAUSED = 5;
    private static final int SLIDE_DOOR_STATUS_RUNNING = 0;
    private static final int SLIDE_DOOR_SUPPORT = 1;
    private static final int SOURCE_SMART_SCENE = 1;
    private static final int SOURCE_SPEECH = 0;
    private static final String SPACE_CAPSULE_MODE_COMMON = "spacecapsule_mode";
    private static final String SPACE_CAPSULE_MODE_MOVIE = "spacecapsule_mode_movie";
    private static final String SPACE_CAPSULE_MODE_SLEEP = "spacecapsule_mode_sleep";
    private static final int STEERING_ENABLE = 1;
    private static final int STEERING_UNABLE = 0;
    private static final int STEERING_UNABLE_WITH_SPEED = 2;
    private static final String TAG = "CarControlSpeechModel";
    private static final int V_ONE_STEP = 20;
    private static final int V_POS_MAX = 100;
    private static final int V_POS_MIN = 0;
    private IArsViewModel mArsVm;
    private AtlViewModel mAtlVm;
    private AvasViewModel mAvasVm;
    private CarBodyViewModel mCarBodyVm;
    private ChassisViewModel mChassisVm;
    ILampViewModel mLampVm;
    private LluViewModel mLluVm;
    private IMeditationViewModel mMeditationViewModel;
    private MeterViewModel mMeterVm;
    IMirrorViewModel mMirrorVm;
    private IScuViewModel mScuVm;
    private SeatViewModel mSeatVm;
    private SpaceCapsuleViewModel mSpaceCapsuleViewModel;
    private IServiceViewModel mSvcVm;
    VcuViewModel mVcuVm;
    IWindowDoorViewModel mWinDoorVm;
    XpuViewModel mXpuVm;
    private final int CHARGE_PORT_CONTROL_OPEN = 0;
    private final int CHARGE_PORT_CONTROL_CLOSE = 1;

    private void onSaveSuspensionPosition() {
    }

    protected boolean checkDriverSeatOccupied() {
        return false;
    }

    protected boolean checkPsnSeatOccupied() {
        return false;
    }

    protected boolean isSupportDrivingMode() {
        return true;
    }

    protected boolean seatCheckDoorClose() {
        return false;
    }

    static /* synthetic */ CarControlSpeechModel access$000() {
        return create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        private static final CarControlSpeechModel sInstance = CarControlSpeechModel.access$000();

        private Holder() {
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static CarControlSpeechModel create() {
        char c;
        String xpCduType = CarStatusUtils.getXpCduType();
        switch (xpCduType.hashCode()) {
            case 2560:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q1)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 2561:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q2)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 2562:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q3)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2563:
            default:
                c = 65535;
                break;
            case 2564:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q5)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 2565:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q6)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 2566:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q7)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 2567:
                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q8)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 2568:
                if (xpCduType.equals("Q9")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
        }
        if (c != 0) {
            if (c == 1 || c == 2 || c == 3) {
                return new D2CarControlSpeechModel();
            }
            return new CarControlSpeechModel();
        }
        return new D55CarcontrolSpeechModel();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static CarControlSpeechModel getInstance() {
        return Holder.sInstance;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CarControlSpeechModel() {
        initViewModel();
    }

    private void initViewModel() {
        ViewModelManager viewModelManager = ViewModelManager.getInstance();
        this.mLampVm = (ILampViewModel) viewModelManager.getViewModelImpl(ILampViewModel.class);
        this.mVcuVm = (VcuViewModel) viewModelManager.getViewModelImpl(IVcuViewModel.class);
        this.mMirrorVm = (IMirrorViewModel) viewModelManager.getViewModelImpl(IMirrorViewModel.class);
        this.mChassisVm = (ChassisViewModel) viewModelManager.getViewModelImpl(IChassisViewModel.class);
        this.mSeatVm = (SeatViewModel) viewModelManager.getViewModelImpl(ISeatViewModel.class);
        this.mWinDoorVm = (IWindowDoorViewModel) viewModelManager.getViewModelImpl(IWindowDoorViewModel.class);
        this.mCarBodyVm = (CarBodyViewModel) viewModelManager.getViewModelImpl(ICarBodyViewModel.class);
        this.mSvcVm = (IServiceViewModel) viewModelManager.getViewModelImpl(IServiceViewModel.class);
        this.mMeterVm = (MeterViewModel) viewModelManager.getViewModelImpl(IMeterViewModel.class);
        this.mAvasVm = (AvasViewModel) viewModelManager.getViewModelImpl(IAvasViewModel.class);
        this.mLluVm = (LluViewModel) viewModelManager.getViewModelImpl(ILluViewModel.class);
        this.mScuVm = (IScuViewModel) viewModelManager.getViewModelImpl(IScuViewModel.class);
        this.mAtlVm = (AtlViewModel) viewModelManager.getViewModelImpl(IAtlViewModel.class);
        this.mSpaceCapsuleViewModel = (SpaceCapsuleViewModel) viewModelManager.getViewModelImpl(ISpaceCapsuleViewModel.class);
        this.mMeditationViewModel = (IMeditationViewModel) viewModelManager.getViewModelImpl(IMeditationViewModel.class);
        this.mArsVm = (IArsViewModel) viewModelManager.getViewModelImpl(IArsViewModel.class);
        initExtViewModel(viewModelManager);
    }

    protected void initExtViewModel(ViewModelManager manager) {
        this.mXpuVm = (XpuViewModel) manager.getViewModelImpl(IXpuViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.speech.ISpeechModel
    public void onEvent(String event, String data, int source) {
        event.hashCode();
        char c = 65535;
        switch (event.hashCode()) {
            case -2120833774:
                if (event.equals(CarcontrolEvent.PSN_SEAT_MOVE_UP)) {
                    c = 0;
                    break;
                }
                break;
            case -2066594564:
                if (event.equals(CarcontrolEvent.WINDOW_PASSENGER_OPEN)) {
                    c = 1;
                    break;
                }
                break;
            case -2033550253:
                if (event.equals("command://control.psn.welcome.mode.off")) {
                    c = 2;
                    break;
                }
                break;
            case -2031117940:
                if (event.equals(MeditationMusicEvent.MEDITATION_MUSIC_CONTROL_PAUSE)) {
                    c = 3;
                    break;
                }
                break;
            case -2003475528:
                if (event.equals(CarcontrolEvent.SEAT_ADJUST)) {
                    c = 4;
                    break;
                }
                break;
            case -1958203503:
                if (event.equals("command://control.light.top.set")) {
                    c = 5;
                    break;
                }
                break;
            case -1932172590:
                if (event.equals(CarcontrolEvent.DIRECT_PORT_ON)) {
                    c = 6;
                    break;
                }
                break;
            case -1917375779:
                if (event.equals(CarcontrolEvent.MIRROR_REAR_CLOSE)) {
                    c = 7;
                    break;
                }
                break;
            case -1896169465:
                if (event.equals("command://control.modes.driving.comfort")) {
                    c = '\b';
                    break;
                }
                break;
            case -1881291550:
                if (event.equals(CarcontrolEvent.LIGHT_POSITION_OFF)) {
                    c = '\t';
                    break;
                }
                break;
            case -1839847196:
                if (event.equals(CarcontrolEvent.LEG_HIGHEST)) {
                    c = '\n';
                    break;
                }
                break;
            case -1768222068:
                if (event.equals(CarcontrolEvent.WIPER_SPEED_DOWN)) {
                    c = 11;
                    break;
                }
                break;
            case -1721073980:
                if (event.equals(CarcontrolEvent.CONTROL_SCISSOR_LEFT_DOOR_OFF)) {
                    c = '\f';
                    break;
                }
                break;
            case -1720472051:
                if (event.equals("command://control.light.language.playtogether.off")) {
                    c = '\r';
                    break;
                }
                break;
            case -1711827085:
                if (event.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_ON)) {
                    c = 14;
                    break;
                }
                break;
            case -1710397550:
                if (event.equals("command://control.cushion.move.set")) {
                    c = 15;
                    break;
                }
                break;
            case -1698181319:
                if (event.equals(CarcontrolEvent.LOW_VOLUME_ON)) {
                    c = 16;
                    break;
                }
                break;
            case -1675857019:
                if (event.equals(CarcontrolEvent.TIRE_PRESSURE_SHOW)) {
                    c = 17;
                    break;
                }
                break;
            case -1671052557:
                if (event.equals(CarcontrolEvent.ENERGY_RECYCLE_LOW)) {
                    c = 18;
                    break;
                }
                break;
            case -1653284995:
                if (event.equals(CarcontrolEvent.CONTROL_LIGHT_LANGUAGE_ON)) {
                    c = 19;
                    break;
                }
                break;
            case -1629961687:
                if (event.equals("command://control.xkey.setting")) {
                    c = 20;
                    break;
                }
                break;
            case -1607528609:
                if (event.equals("command://control.waist.move")) {
                    c = 21;
                    break;
                }
                break;
            case -1606320269:
                if (event.equals(CarcontrolEvent.SEAT_MOVE_FOREMOST)) {
                    c = 22;
                    break;
                }
                break;
            case -1593254932:
                if (event.equals("command://control.light.atmosphere.speaker.off")) {
                    c = 23;
                    break;
                }
                break;
            case -1527032229:
                if (event.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_OFF)) {
                    c = 24;
                    break;
                }
                break;
            case -1515588522:
                if (event.equals(CarcontrolEvent.CONTROL_SEAT_RESUME)) {
                    c = 25;
                    break;
                }
                break;
            case -1513394514:
                if (event.equals(CarcontrolEvent.PSN_SEAT_MOVE_FORWARD)) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case -1512552546:
                if (event.equals("command://control.welcome.mode.on")) {
                    c = 27;
                    break;
                }
                break;
            case -1494382542:
                if (event.equals(CarcontrolEvent.SEAT_BACKREST_FORWARD)) {
                    c = 28;
                    break;
                }
                break;
            case -1480987763:
                if (event.equals("command://control.light.language.setting.effect")) {
                    c = 29;
                    break;
                }
                break;
            case -1468788214:
                if (event.equals("command://control.new.key.parking.enhanced.off")) {
                    c = 30;
                    break;
                }
                break;
            case -1468297723:
                if (event.equals("command://control.swing.strength.set")) {
                    c = 31;
                    break;
                }
                break;
            case -1463564858:
                if (event.equals(CarcontrolEvent.LIGHT_AUTO_ON)) {
                    c = ' ';
                    break;
                }
                break;
            case -1458343142:
                if (event.equals(CarcontrolEvent.MIRROR_REAR_ON)) {
                    c = '!';
                    break;
                }
                break;
            case -1457870836:
                if (event.equals("command://control.wireless.charging.on")) {
                    c = '\"';
                    break;
                }
                break;
            case -1445203636:
                if (event.equals("command://control.light.language.setting.preview")) {
                    c = '#';
                    break;
                }
                break;
            case -1440972415:
                if (event.equals("command://control.light.language.playtogether.on")) {
                    c = '$';
                    break;
                }
                break;
            case -1434099669:
                if (event.equals("command://control.light.atmosphere.mode.set")) {
                    c = '%';
                    break;
                }
                break;
            case -1430827874:
                if (event.equals(CarcontrolEvent.MIST_LIGHT_ON)) {
                    c = '&';
                    break;
                }
                break;
            case -1409157502:
                if (event.equals("command://control.welcome.mode.soundeffect.off")) {
                    c = '\'';
                    break;
                }
                break;
            case -1407167676:
                if (event.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_UP)) {
                    c = '(';
                    break;
                }
                break;
            case -1405991280:
                if (event.equals(CarcontrolEvent.MIST_LIGHT_OFF)) {
                    c = ')';
                    break;
                }
                break;
            case -1375267742:
                if (event.equals(CarcontrolEvent.LEG_DOWN)) {
                    c = '*';
                    break;
                }
                break;
            case -1374999663:
                if (event.equals("command://control.leg.move")) {
                    c = '+';
                    break;
                }
                break;
            case -1350545174:
                if (event.equals("command://control.back.welcome.mode.set")) {
                    c = ',';
                    break;
                }
                break;
            case -1321992384:
                if (event.equals("command://control.park.auto.unlock.off")) {
                    c = '-';
                    break;
                }
                break;
            case -1281862925:
                if (event.equals(CarcontrolEvent.CONTROL_SCISSOR_RIGHT_DOOR_OFF)) {
                    c = '.';
                    break;
                }
                break;
            case -1187231565:
                if (event.equals("command://control.light.atmosphere.color.switch")) {
                    c = '/';
                    break;
                }
                break;
            case -1178928744:
                if (event.equals(CarcontrolEvent.WIPER_MEDIUM)) {
                    c = '0';
                    break;
                }
                break;
            case -1148601985:
                if (event.equals("command://control.child.safety.locks.off")) {
                    c = '1';
                    break;
                }
                break;
            case -1127626500:
                if (event.equals("command://control.new.key.parking.off")) {
                    c = '2';
                    break;
                }
                break;
            case -1104013483:
                if (event.equals(CarcontrolEvent.LOW_VOLUME_OFF)) {
                    c = '3';
                    break;
                }
                break;
            case -1026240870:
                if (event.equals(CarcontrolEvent.SEAT_BACKREST_BACK)) {
                    c = '4';
                    break;
                }
                break;
            case -1025760425:
                if (event.equals(CarcontrolEvent.SEAT_BACKREST_REAR)) {
                    c = '5';
                    break;
                }
                break;
            case -1001296170:
                if (event.equals(CarcontrolEvent.CLOSE_USER_BOOK)) {
                    c = '6';
                    break;
                }
                break;
            case -974172818:
                if (event.equals("command://control.doorknob.auto.open.on")) {
                    c = '7';
                    break;
                }
                break;
            case -968493410:
                if (event.equals(CarcontrolEvent.WINDOW_RIGHT_REAR_CLOSE)) {
                    c = '8';
                    break;
                }
                break;
            case -872634405:
                if (event.equals(CarcontrolEvent.CONTROL_SCISSOR_RIGHT_DOOR_ON)) {
                    c = '9';
                    break;
                }
                break;
            case -868335665:
                if (event.equals("command://control.child.safety.locks.on")) {
                    c = ':';
                    break;
                }
                break;
            case -846743383:
                if (event.equals("command://control.psn.seat.move.lowest")) {
                    c = ';';
                    break;
                }
                break;
            case -802394656:
                if (event.equals("command://control.child.safety.mode.on")) {
                    c = '<';
                    break;
                }
                break;
            case -792915409:
                if (event.equals(CarcontrolEvent.WINDOW_LEFT_CLOSE)) {
                    c = '=';
                    break;
                }
                break;
            case -785280126:
                if (event.equals("command://control.intelligent.high.light.off")) {
                    c = '>';
                    break;
                }
                break;
            case -753947988:
                if (event.equals("command://control.massage.off")) {
                    c = '?';
                    break;
                }
                break;
            case -678703178:
                if (event.equals(CarcontrolEvent.WINDOW_REAR_OPEN)) {
                    c = '@';
                    break;
                }
                break;
            case -672533029:
                if (event.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_MAX)) {
                    c = 'A';
                    break;
                }
                break;
            case -672532791:
                if (event.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_MIN)) {
                    c = 'B';
                    break;
                }
                break;
            case -672527143:
                if (event.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_SET)) {
                    c = 'C';
                    break;
                }
                break;
            case -665457148:
                if (event.equals("command://control.light.position.mode.set")) {
                    c = 'D';
                    break;
                }
                break;
            case -620002367:
                if (event.equals("command://control.suspension.auto.off")) {
                    c = 'E';
                    break;
                }
                break;
            case -614876148:
                if (event.equals(CarcontrolEvent.LIGHT_POSITION_ON)) {
                    c = 'F';
                    break;
                }
                break;
            case -509776894:
                if (event.equals(CarcontrolEvent.MODES_DRIVING_CONSERVATION)) {
                    c = 'G';
                    break;
                }
                break;
            case -466933999:
                if (event.equals(CarcontrolEvent.SEAT_MOVE_FORWARD)) {
                    c = 'H';
                    break;
                }
                break;
            case -461837539:
                if (event.equals(CarcontrolEvent.LIGHT_LOW_OFF)) {
                    c = 'I';
                    break;
                }
                break;
            case -390627019:
                if (event.equals(CarcontrolEvent.PSN_SEAT_BACKREST_FORWARD)) {
                    c = 'J';
                    break;
                }
                break;
            case -388893173:
                if (event.equals(CarcontrolEvent.CONTROL_SCISSOR_LEFT_DOOR_PAUSE)) {
                    c = 'K';
                    break;
                }
                break;
            case -310219003:
                if (event.equals(CarcontrolEvent.WIPER_SPEED_UP)) {
                    c = 'L';
                    break;
                }
                break;
            case -308548080:
                if (event.equals(CarcontrolEvent.WINDOW_DRIVER_CLOSE)) {
                    c = 'M';
                    break;
                }
                break;
            case -304980732:
                if (event.equals(CarcontrolEvent.ALTERNATING_PORT_OFF)) {
                    c = 'N';
                    break;
                }
                break;
            case -284285602:
                if (event.equals(CarcontrolEvent.TRUNK_CLOSE)) {
                    c = 'O';
                    break;
                }
                break;
            case -263259933:
                if (event.equals(CarcontrolEvent.ENERGY_RECYCLE_DOWN)) {
                    c = 'P';
                    break;
                }
                break;
            case -263147037:
                if (event.equals(CarcontrolEvent.ENERGY_RECYCLE_HIGH)) {
                    c = 'Q';
                    break;
                }
                break;
            case -134586432:
                if (event.equals("command://control.doorknob.auto.open.off")) {
                    c = 'R';
                    break;
                }
                break;
            case -127918861:
                if (event.equals("command://control.light.language.setting.off")) {
                    c = 'S';
                    break;
                }
                break;
            case -68993860:
                if (event.equals("command://control.modes.driving.racer")) {
                    c = 'T';
                    break;
                }
                break;
            case -67611537:
                if (event.equals(CarcontrolEvent.MODES_DRIVING_SPORT)) {
                    c = 'U';
                    break;
                }
                break;
            case -50794088:
                if (event.equals(CarcontrolEvent.LIGHT_HOME_OFF)) {
                    c = 'V';
                    break;
                }
                break;
            case 19301528:
                if (event.equals("command://control.massage.strength.set")) {
                    c = 'W';
                    break;
                }
                break;
            case 79236237:
                if (event.equals("command://control.psn.seat.move.highest")) {
                    c = 'X';
                    break;
                }
                break;
            case 114226434:
                if (event.equals("command://control.massage.on")) {
                    c = 'Y';
                    break;
                }
                break;
            case 128955186:
                if (event.equals(CarcontrolEvent.WINDOW_DRIVER_OPEN)) {
                    c = 'Z';
                    break;
                }
                break;
            case 145913282:
                if (event.equals("command://control.sliding.door.set")) {
                    c = '[';
                    break;
                }
                break;
            case 168840488:
                if (event.equals("command://control.suspension.rigidity.set")) {
                    c = '\\';
                    break;
                }
                break;
            case 169414246:
                if (event.equals(CarcontrolEvent.MODES_STEERING_NORMAL)) {
                    c = ']';
                    break;
                }
                break;
            case 232191708:
                if (event.equals(CarcontrolEvent.DIRECT_PORT_OFF)) {
                    c = '^';
                    break;
                }
                break;
            case 244379227:
                if (event.equals(CarcontrolEvent.LEG_UP)) {
                    c = '_';
                    break;
                }
                break;
            case 251763052:
                if (event.equals("command://control.intelligent.high.light.on")) {
                    c = '`';
                    break;
                }
                break;
            case 261368709:
                if (event.equals(CarcontrolEvent.SEAT_RESTORE)) {
                    c = 'a';
                    break;
                }
                break;
            case 275456150:
                if (event.equals(CarcontrolEvent.LIGHT_HOME_ON)) {
                    c = 'b';
                    break;
                }
                break;
            case 284513946:
                if (event.equals(CarcontrolEvent.WIPER_SUPERHIGH)) {
                    c = 'c';
                    break;
                }
                break;
            case 287772561:
                if (event.equals(CarcontrolEvent.CONTROL_LIGHT_LANGUAGE_OFF)) {
                    c = 'd';
                    break;
                }
                break;
            case 294159518:
                if (event.equals(CarcontrolEvent.TRUNK_UNLOCK)) {
                    c = 'e';
                    break;
                }
                break;
            case 304221885:
                if (event.equals("command://exit.meditation.copilot")) {
                    c = 'f';
                    break;
                }
                break;
            case 313142134:
                if (event.equals("command://control.psn.seat.move.foremost")) {
                    c = 'g';
                    break;
                }
                break;
            case 342341845:
                if (event.equals(CarcontrolEvent.WINDOW_FRONT_CLOSE)) {
                    c = 'h';
                    break;
                }
                break;
            case 348886406:
                if (event.equals(CarcontrolEvent.WINDOW_PASSENGER_CLOSE)) {
                    c = 'i';
                    break;
                }
                break;
            case 355511184:
                if (event.equals("command://control.welcome.mode.off")) {
                    c = 'j';
                    break;
                }
                break;
            case 378511493:
                if (event.equals("command://control.leg.move.set")) {
                    c = 'k';
                    break;
                }
                break;
            case 404106359:
                if (event.equals(CarcontrolEvent.PSN_SEAT_BACKREST_BACK)) {
                    c = 'l';
                    break;
                }
                break;
            case 404586804:
                if (event.equals("command://control.psn.seat.backrest.move.rear")) {
                    c = 'm';
                    break;
                }
                break;
            case 407370447:
                if (event.equals(CarcontrolEvent.SEAT_MOVE_UP)) {
                    c = 'n';
                    break;
                }
                break;
            case 423846412:
                if (event.equals(CarcontrolEvent.WINDOW_REAR_CLOSE)) {
                    c = 'o';
                    break;
                }
                break;
            case 439725323:
                if (event.equals(CarcontrolEvent.WINDOW_LEFT_REAR_OPEN)) {
                    c = 'p';
                    break;
                }
                break;
            case 484089976:
                if (event.equals("command://control.suspension.height")) {
                    c = 'q';
                    break;
                }
                break;
            case 512575995:
                if (event.equals("command://control.modes.driving.crosscountry")) {
                    c = 'r';
                    break;
                }
                break;
            case 531191715:
                if (event.equals("command://control.seat.lumber.control")) {
                    c = 's';
                    break;
                }
                break;
            case 540172977:
                if (event.equals("command://control.tail.off")) {
                    c = 't';
                    break;
                }
                break;
            case 558385326:
                if (event.equals("command://control.tail.auto.off")) {
                    c = 'u';
                    break;
                }
                break;
            case 561507053:
                if (event.equals(CarcontrolEvent.WINDOWS_CLOSE)) {
                    c = 'v';
                    break;
                }
                break;
            case 572663477:
                if (event.equals(CarcontrolEvent.WINDOWS_OPEN)) {
                    c = 'w';
                    break;
                }
                break;
            case 579058598:
                if (event.equals(CarcontrolEvent.CONTROL_COMFORTABLE_DRIVING_MODE_OPEN)) {
                    c = 'x';
                    break;
                }
                break;
            case 626057995:
                if (event.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_BRIGHTNESS_DOWN)) {
                    c = 'y';
                    break;
                }
                break;
            case 640398363:
                if (event.equals(CarcontrolEvent.SEAT_MOVE_BACK)) {
                    c = 'z';
                    break;
                }
                break;
            case 640472022:
                if (event.equals(CarcontrolEvent.SEAT_MOVE_DOWN)) {
                    c = '{';
                    break;
                }
                break;
            case 640878808:
                if (event.equals(CarcontrolEvent.SEAT_MOVE_REAR)) {
                    c = '|';
                    break;
                }
                break;
            case 658134902:
                if (event.equals(CarcontrolEvent.ENERGY_RECYCLE_MEDIA)) {
                    c = '}';
                    break;
                }
                break;
            case 672736589:
                if (event.equals("command://control.suspension.auto.on")) {
                    c = '~';
                    break;
                }
                break;
            case 673093091:
                if (event.equals("command://control.welcome.mode.soundeffect.setting")) {
                    c = 127;
                    break;
                }
                break;
            case 731254135:
                if (event.equals(CarcontrolEvent.CONTROL_ELECTRIC_CURTAIN_OPEN)) {
                    c = 128;
                    break;
                }
                break;
            case 735391575:
                if (event.equals(CarcontrolEvent.WINDOW_LEFT_REAR_CLOSE)) {
                    c = 129;
                    break;
                }
                break;
            case 741032556:
                if (event.equals("command://control.light.language.autoplay.on")) {
                    c = 130;
                    break;
                }
                break;
            case 759196111:
                if (event.equals("command://control.psn.seat.backrest.move.foremost")) {
                    c = 131;
                    break;
                }
                break;
            case 759755804:
                if (event.equals(CarcontrolEvent.CONTROL_COMFORTABLE_DRIVING_MODE_CLOSE)) {
                    c = 132;
                    break;
                }
                break;
            case 775765482:
                if (event.equals(CarcontrolEvent.CONTROL_SCISSOR_LEFT_DOOR_ON)) {
                    c = 133;
                    break;
                }
                break;
            case 786135674:
                if (event.equals(CarcontrolEvent.CONTROL_SCISSOR_RIGHT_DOOR_PAUSE)) {
                    c = 134;
                    break;
                }
                break;
            case 829912197:
                if (event.equals(CarcontrolEvent.WIPER_HIGH)) {
                    c = 135;
                    break;
                }
                break;
            case 830243044:
                if (event.equals(CarcontrolEvent.WIPER_SLOW)) {
                    c = 136;
                    break;
                }
                break;
            case 895569294:
                if (event.equals("command://control.child.safety.mode.off")) {
                    c = 137;
                    break;
                }
                break;
            case 902513266:
                if (event.equals(CarcontrolEvent.SEAT_BACKREST_FOREMOST)) {
                    c = 138;
                    break;
                }
                break;
            case 910047412:
                if (event.equals("command://control.suspension.welcome.mode.set")) {
                    c = 139;
                    break;
                }
                break;
            case 924374636:
                if (event.equals("command://control.welcome.mode.soundeffect.on")) {
                    c = 140;
                    break;
                }
                break;
            case 927186414:
                if (event.equals("command://control.park.auto.unlock.on")) {
                    c = 141;
                    break;
                }
                break;
            case 1026004922:
                if (event.equals(CarcontrolEvent.WINDOW_RIGHT_OPEN)) {
                    c = 142;
                    break;
                }
                break;
            case 1054474012:
                if (event.equals(CarcontrolEvent.ENERGY_RECYCLE_UP)) {
                    c = 143;
                    break;
                }
                break;
            case 1083473887:
                if (event.equals("jarvis.dm.end")) {
                    c = 144;
                    break;
                }
                break;
            case 1125696752:
                if (event.equals(CarcontrolEvent.SEAT_MOVE_HIGHEST)) {
                    c = 145;
                    break;
                }
                break;
            case 1146057391:
                if (event.equals("command://control.swing.on")) {
                    c = 146;
                    break;
                }
                break;
            case 1168040607:
                if (event.equals("command://control.swing.off")) {
                    c = 147;
                    break;
                }
                break;
            case 1182850155:
                if (event.equals(CarcontrolEvent.CONTROL_ELECTRIC_CURTAIN_CLOSE)) {
                    c = 148;
                    break;
                }
                break;
            case 1197692826:
                if (event.equals("command://control.light.atmosphere.double.color")) {
                    c = 149;
                    break;
                }
                break;
            case 1216045284:
                if (event.equals(CarcontrolEvent.WINDOW_RIGHT_REAR_OPEN)) {
                    c = 150;
                    break;
                }
                break;
            case 1242799579:
                if (event.equals("command://control.light.language.setting.on")) {
                    c = 151;
                    break;
                }
                break;
            case 1376437035:
                if (event.equals("command://control.xsport.close.mode")) {
                    c = 152;
                    break;
                }
                break;
            case 1382836745:
                if (event.equals("command://control.suspension.save.position")) {
                    c = 153;
                    break;
                }
                break;
            case 1385008351:
                if (event.equals("command://control.light.height")) {
                    c = 154;
                    break;
                }
                break;
            case 1390038351:
                if (event.equals(CarcontrolEvent.LIGHT_ATMOSPHERE_COLOR)) {
                    c = 155;
                    break;
                }
                break;
            case 1414376552:
                if (event.equals(CarcontrolEvent.OPEN_USER_BOOK)) {
                    c = 156;
                    break;
                }
                break;
            case 1439290046:
                if (event.equals(CarcontrolEvent.WINDOWS_VENTILATE_ON)) {
                    c = 157;
                    break;
                }
                break;
            case 1446655346:
                if (event.equals(CarcontrolEvent.LEG_LOWEST)) {
                    c = 158;
                    break;
                }
                break;
            case 1475254706:
                if (event.equals("command://control.leg.movest")) {
                    c = 159;
                    break;
                }
                break;
            case 1497172610:
                if (event.equals("command://control.light.language.autoplay.off")) {
                    c = 160;
                    break;
                }
                break;
            case 1509122673:
                if (event.equals(CarcontrolEvent.LIGHT_LOW_ON)) {
                    c = 161;
                    break;
                }
                break;
            case 1514182570:
                if (event.equals(CarcontrolEvent.ALTERNATING_PORT_ON)) {
                    c = 162;
                    break;
                }
                break;
            case 1520748375:
                if (event.equals(MeditationMusicEvent.MEDITATION_MUSIC_CONTROL_RESUME)) {
                    c = 163;
                    break;
                }
                break;
            case 1542033088:
                if (event.equals("command://control.tail.auto.on")) {
                    c = 164;
                    break;
                }
                break;
            case 1542318054:
                if (event.equals(CarcontrolEvent.SEAT_MOVE_LOWEST)) {
                    c = 165;
                    break;
                }
                break;
            case 1596969595:
                if (event.equals("command://control.psn.welcome.mode.on")) {
                    c = 166;
                    break;
                }
                break;
            case 1621377921:
                if (event.equals("command://enter.meditation.copilot")) {
                    c = 167;
                    break;
                }
                break;
            case 1653758500:
                if (event.equals(CarcontrolEvent.TRUNK_OPEN)) {
                    c = 168;
                    break;
                }
                break;
            case 1668318320:
                if (event.equals(CarcontrolEvent.WINDOWS_VENTILATE_OFF)) {
                    c = 169;
                    break;
                }
                break;
            case 1671611365:
                if (event.equals(CarcontrolEvent.CONTROL_LIGHT_RESUME)) {
                    c = 170;
                    break;
                }
                break;
            case 1679992925:
                if (event.equals("command://control.tail.on")) {
                    c = 171;
                    break;
                }
                break;
            case 1730189960:
                if (event.equals(CarcontrolEvent.WINDOW_RIGHT_CLOSE)) {
                    c = 172;
                    break;
                }
                break;
            case 1749720002:
                if (event.equals("command://control.light.atmosphere.speaker.on")) {
                    c = 173;
                    break;
                }
                break;
            case 1764740274:
                if (event.equals("command://control.new.key.parking.on")) {
                    c = 174;
                    break;
                }
                break;
            case 1775898419:
                if (event.equals(CarcontrolEvent.WINDOW_LEFT_OPEN)) {
                    c = 175;
                    break;
                }
                break;
            case 1787739927:
                if (event.equals("command://control.rearviewmirror.reverse.down.off.setting")) {
                    c = 176;
                    break;
                }
                break;
            case 1829348592:
                if (event.equals(CarcontrolEvent.CHECK_USER_BOOK)) {
                    c = 177;
                    break;
                }
                break;
            case 1849428582:
                if (event.equals("jarvis.dm.start")) {
                    c = 178;
                    break;
                }
                break;
            case 1851379043:
                if (event.equals(CarcontrolEvent.CONTROL_XPEDAL_OFF)) {
                    c = 179;
                    break;
                }
                break;
            case 1860837227:
                if (event.equals(CarcontrolEvent.CONTROL_XPEDAL_ON)) {
                    c = 180;
                    break;
                }
                break;
            case 1874087069:
                if (event.equals(MeditationMusicEvent.MEDITATION_MUSIC_CONTROL_NEXT)) {
                    c = 181;
                    break;
                }
                break;
            case 1874129512:
                if (event.equals(CarcontrolEvent.LIGHT_AUTO_OFF)) {
                    c = 182;
                    break;
                }
                break;
            case 1874158557:
                if (event.equals(MeditationMusicEvent.MEDITATION_MUSIC_CONTROL_PREV)) {
                    c = 183;
                    break;
                }
                break;
            case 1915018715:
                if (event.equals("command://control.shortcutkey.setting")) {
                    c = 184;
                    break;
                }
                break;
            case 1915071035:
                if (event.equals("command://control.rearviewmirror.reverse.down.on.setting")) {
                    c = 185;
                    break;
                }
                break;
            case 1949772309:
                if (event.equals(CarcontrolEvent.MODES_STEERING_SPORT)) {
                    c = 186;
                    break;
                }
                break;
            case 1987631518:
                if (event.equals(CarcontrolEvent.PSN_SEAT_MOVE_BACK)) {
                    c = 187;
                    break;
                }
                break;
            case 1987705177:
                if (event.equals(CarcontrolEvent.PSN_SEAT_MOVE_DOWN)) {
                    c = 188;
                    break;
                }
                break;
            case 1988111963:
                if (event.equals("command://control.psn.seat.move.rear")) {
                    c = 189;
                    break;
                }
                break;
            case 2002557289:
                if (event.equals(CarcontrolEvent.MODES_STEERING_SOFT)) {
                    c = 190;
                    break;
                }
                break;
            case 2036006535:
                if (event.equals(CarcontrolEvent.MIRROR_REAR_SET)) {
                    c = 191;
                    break;
                }
                break;
            case 2048477878:
                if (event.equals("command://control.light.top.brightness.set")) {
                    c = 192;
                    break;
                }
                break;
            case 2050644194:
                if (event.equals("command://control.wireless.charging.off")) {
                    c = 193;
                    break;
                }
                break;
            case 2055024460:
                if (event.equals(CarcontrolEvent.MODES_DRIVING_NORMAL)) {
                    c = 194;
                    break;
                }
                break;
            case 2089614285:
                if (event.equals(CarcontrolEvent.WINDOW_FRONT_OPEN)) {
                    c = 195;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                onPsnSeatMoveUp(data);
                return;
            case 1:
                onWindowsControl(1, 0, getPercent(data));
                return;
            case 2:
                onPsnWelcomeModeOff();
                return;
            case 3:
                onPause();
                return;
            case 4:
                onSeatAdjust(SeatValue.fromJson(data));
                return;
            case 5:
                onLightTopSet(getPilot(data), getTarget(data));
                return;
            case 6:
                onChargePortControl(1, 0);
                return;
            case 7:
                onMirrorRearClose();
                return;
            case '\b':
                onModesDrivingComfort();
                return;
            case '\t':
                onLightPositionOff();
                return;
            case '\n':
                onLegHighest();
                return;
            case 11:
                onWiperSpeedDown();
                return;
            case '\f':
                onControlScissorLeftDoorOff();
                return;
            case '\r':
                onAvasSayHiDisable();
                return;
            case 14:
                onLightAtmosphereOn();
                return;
            case 15:
                onCushionMoveSet(data);
                return;
            case 16:
                onLowVolumeOn();
                return;
            case 17:
                onTirePressureShow();
                return;
            case 18:
                onEnergyRecycleLow(source);
                return;
            case 19:
                setLluSw(true);
                return;
            case 20:
                onXkeySetting(Integer.valueOf(getMode(data)));
                return;
            case 21:
                onWaistMove(getPilot(data), getDirection(data));
                return;
            case 22:
                onSeatMoveForemost();
                return;
            case 23:
                onAtmosphereSpeakerOff();
                return;
            case 24:
                onLightAtmosphereOff();
                return;
            case 25:
                onControlSeatResume();
                return;
            case 26:
                onPsnSeatMoveForward(data);
                return;
            case 27:
                onWelcomeModeOn();
                return;
            case 28:
                onSeatBackrestForward(data);
                return;
            case 29:
                onLluEffectSet(getMode(data));
                return;
            case 30:
                onControlNewKeyParkingEnhancedOff();
                return;
            case 31:
                onSwingStrengthSet(getPilot(data), getTarget(data));
                return;
            case ' ':
                onLightAutoOn();
                return;
            case '!':
                onMirrorRearOn();
                return;
            case '\"':
                onsCwcSwOn(source);
                return;
            case '#':
                onLluEffectPreview(getMode(data));
                return;
            case '$':
                onAvasSayHiEnable();
                return;
            case '%':
                onLightAtmosphereModeSet(getTarget(data));
                return;
            case '&':
                onMistLightOn();
                return;
            case '\'':
                onBootSoundOff();
                return;
            case '(':
                onLightAtmosphereBrightnessUp();
                return;
            case ')':
                onMistLightOff();
                return;
            case '*':
                onLegDown();
                return;
            case '+':
                onLegMove(data);
                return;
            case ',':
                onBackWelcomeModeSet(getTarget(data));
                return;
            case '-':
                onParkAutoUnlockOff();
                return;
            case '.':
                onControlScissorRightDoorOff();
                return;
            case '/':
                onLightAtmosphereColorSwitch(getTarget(data));
                return;
            case '0':
                onWiperMedium();
                return;
            case '1':
                onChildSafetyLocksOff(getTarget(data), source);
                return;
            case '2':
                onControlNewKeyParkingOff();
                return;
            case '3':
                onLowVolumeOff();
                return;
            case '4':
                onSeatBackrestBack(data);
                return;
            case '5':
                onSeatBackrestRear();
                return;
            case '6':
                onCloseUserBook();
                return;
            case '7':
                onDoorknobAutoOn();
                return;
            case '8':
                onWindowsControl(3, 1, getPercent(data));
                return;
            case '9':
                onControlScissorRightDoorOn();
                return;
            case ':':
                onChildSafetyLocksOn(getTarget(data), source);
                return;
            case ';':
                onPsnSeatMoveLowest();
                return;
            case '<':
                onChildSafetyModeOn();
                return;
            case '=':
                onWindowsControl(4, 1, getPercent(data));
                return;
            case '>':
                onIhbOff();
                return;
            case '?':
                onMassageOff(getPilot(data));
                return;
            case '@':
                onWindowsControl(8, 0, getPercent(data));
                return;
            case 'A':
                onLightAtmosphereBrightnessMax();
                return;
            case 'B':
                onLightAtmosphereBrightnessMin();
                return;
            case 'C':
                onLightAtmosphereBrightnessSet(new CommandValue(data));
                return;
            case 'D':
                onLightPositionModeSet(getTarget(data));
                return;
            case 'E':
                onSuspensionAutoOff(source);
                return;
            case 'F':
                onLightPositionOn();
                return;
            case 'G':
                onModesDrivingConservation(source);
                return;
            case 'H':
                onSeatMoveForward(data);
                return;
            case 'I':
                onLightLowOff(ControlReason.fromJson(data));
                return;
            case 'J':
                onPsnSeatBackrestForward(data);
                return;
            case 'K':
                onControlScissorLeftDoorPause();
                return;
            case 'L':
                onWiperSpeedUp();
                return;
            case 'M':
                onWindowsControl(0, 1, getPercent(data));
                return;
            case 'N':
                onChargePortControl(0, 1);
                return;
            case 'O':
                onTrunkClose();
                return;
            case 'P':
                onEnergyRecycleDown();
                return;
            case 'Q':
                onEnergyRecycleHigh(source);
                return;
            case 'R':
                onDoorknobAutoOff();
                return;
            case 'S':
                onLluEffectPlayDisable(getMode(data));
                return;
            case 'T':
                onModesDrivingRacer();
                return;
            case 'U':
                onModesDrivingSport(source);
                return;
            case 'V':
                onLightHomeOff();
                return;
            case 'W':
                onMassageStrengthSet(getPilot(data), getTarget(data));
                return;
            case 'X':
                onPsnSeatMoveHighest();
                return;
            case 'Y':
                onMassageOn(getPilot(data), getMode(data));
                return;
            case 'Z':
                onWindowsControl(0, 0, getPercent(data));
                return;
            case '[':
                onControlSlidingDoorSet(getTarget(data), getMode(data));
                return;
            case '\\':
                onSuspensionRigiditySet(getMode(data));
                return;
            case ']':
                onModesSteeringNormal(source);
                return;
            case '^':
                onChargePortControl(1, 1);
                return;
            case '_':
                onLegUp();
                return;
            case '`':
                onIhbOn();
                return;
            case 'a':
                onSeatRestore();
                return;
            case 'b':
                onLightHomeOn();
                return;
            case 'c':
                onWiperSuperhigh();
                return;
            case 'd':
                setLluSw(false);
                return;
            case 'e':
                onTrunkUnlock();
                return;
            case 'f':
                onPnsSateChange(false);
                return;
            case 'g':
                onPsnSeatMoveForemost();
                return;
            case 'h':
                onWindowsControl(7, 1, getPercent(data));
                return;
            case 'i':
                onWindowsControl(1, 1, getPercent(data));
                return;
            case 'j':
                onWelcomeModeOff();
                return;
            case 'k':
                onLegMoveSet(data);
                return;
            case 'l':
                onPsnSeatBackrestBack(data);
                return;
            case 'm':
                onPsnSeatBackrestRear();
                return;
            case 'n':
                onSeatMoveUp(data);
                return;
            case 'o':
                onWindowsControl(8, 1, getPercent(data));
                return;
            case 'p':
                onWindowsControl(2, 0, getPercent(data));
                return;
            case 'q':
                onSuspensionHeight(getMode(data), source);
                return;
            case 'r':
                onModesDrivingCrosscountry(source);
                return;
            case 's':
                onSeatLumberControl(getPilot(data), getMode(data));
                return;
            case 't':
                onArsFold();
                return;
            case 'u':
                onArsModeAutoOff();
                return;
            case 'v':
                onWindowsControl(6, 1, getPercent(data));
                return;
            case 'w':
                onWindowsControl(6, 0, getPercent(data));
                return;
            case 'x':
                onControlComfortableDrivingModeOpen(source);
                return;
            case 'y':
                onLightAtmosphereBrightnessDown();
                return;
            case 'z':
                onSeatMoveBack(data);
                return;
            case '{':
                onSeatMoveDown(data);
                return;
            case '|':
                onSeatMoveRear();
                return;
            case '}':
                onEnergyRecycleMedium(source);
                return;
            case '~':
                onSuspensionAutoOn(source);
                return;
            case 127:
                onBootSoundEffectSet(Integer.valueOf(getMode(data)));
                return;
            case 128:
                onControlSunShade(0, getPercent(data));
                return;
            case 129:
                onWindowsControl(2, 1, getPercent(data));
                return;
            case 130:
                onSayHiEnable();
                return;
            case TarConstants.PREFIXLEN_XSTAR /* 131 */:
                onPsnSeatBackrestForemost();
                return;
            case 132:
                onControlComfortableDrivingModeClose(source);
                return;
            case 133:
                onControlScissorLeftDoorOn();
                return;
            case 134:
                onControlScissorRightDoorPause();
                return;
            case 135:
                onWiperHigh();
                return;
            case 136:
                onWiperSlow();
                return;
            case 137:
                onChildSafetyModeOff();
                return;
            case 138:
                onSeatBackrestForemost();
                return;
            case 139:
                onSuspensionWelcomeModeSet(getTarget(data));
                return;
            case 140:
                onBootSoundOn();
                return;
            case 141:
                onParkAutoUnlockOn();
                return;
            case 142:
                onWindowsControl(5, 0, getPercent(data));
                return;
            case 143:
                onEnergyRecycleUp();
                return;
            case 144:
                onDialogEnd(DialogEndReason.fromJson(data));
                return;
            case SpeechConstant.SoundLocation.PASSENGER_END_ANGLE /* 145 */:
                onSeatMoveHighest();
                return;
            case 146:
                onSwingOn(getPilot(data), getMode(data));
                return;
            case 147:
                onSwingOff(getPilot(data));
                return;
            case TarConstants.CHKSUM_OFFSET /* 148 */:
                onControlSunShade(1, getPercent(data));
                return;
            case 149:
                onLightAtmosphereDualColor(new CommandValue(data), source);
                return;
            case IHvacViewModel.HVAC_INNER_PM25_LEVEL_MIDDLE /* 150 */:
                onWindowsControl(3, 0, getPercent(data));
                return;
            case 151:
                onLluEffectPlayEnable(getMode(data));
                return;
            case 152:
                onControlXpowerOff(source);
                return;
            case 153:
                onSaveSuspensionPosition();
                return;
            case 154:
                onSetLightHeight(getMode(data), source);
                return;
            case TarConstants.PREFIXLEN /* 155 */:
                onLightAtmosphereColor(new CommandValue(data), source);
                return;
            case 156:
                onOpenUserBook();
                return;
            case 157:
                onWindowsVentilateOn();
                return;
            case 158:
                onLegLowest();
                return;
            case 159:
                onLegMovest(getPilot(data), getDirection(data));
                return;
            case SpeechConstant.SoundLocation.MAX_ANGLE /* 160 */:
                onSayHiDisable();
                return;
            case 161:
                onLightLowOn();
                return;
            case 162:
                onChargePortControl(0, 0);
                return;
            case 163:
                onResume();
                return;
            case 164:
                onArsModeAutoOn();
                return;
            case 165:
                onSeatMoveLowest();
                return;
            case 166:
                onPsnWelcomeModeOn();
                return;
            case 167:
                onPnsSateChange(true);
                return;
            case 168:
                onTrunkOpen();
                return;
            case 169:
                onWindowsVentilateOff();
                return;
            case 170:
                onControlLightResume();
                return;
            case 171:
                onArsUnfold();
                return;
            case 172:
                onWindowsControl(5, 1, getPercent(data));
                return;
            case 173:
                onAtmosphereSpeakerOn();
                return;
            case 174:
                onControlNewKeyParkingOn();
                return;
            case 175:
                onWindowsControl(4, 0, getPercent(data));
                return;
            case 176:
                onSetReverseMirrorModeOff(getMode(data));
                return;
            case 177:
                onCheckUserBook(UserBookValue.fromJson(data));
                return;
            case 178:
                onDialogStart(WakeupReason.fromJson(data));
                return;
            case 179:
                onControlXpedalOff(source);
                return;
            case 180:
                onControlXpedalOn(source);
                return;
            case 181:
                onNext();
                return;
            case 182:
                onLightAutoOff();
                return;
            case 183:
                onPrev();
                return;
            case 184:
                onDoorKeySetting(Integer.valueOf(getMode(data)));
                return;
            case 185:
                onSetReverseMirrorModeOn(getMode(data));
                return;
            case 186:
                onModesSteeringSport(source);
                return;
            case 187:
                onPsnSeatMoveBack(data);
                return;
            case 188:
                onPsnSeatMoveDown(data);
                return;
            case 189:
                onPsnSeatMoveRear();
                return;
            case 190:
                onModesSteeringSoft(source);
                return;
            case 191:
                onMirrorRearSet();
                return;
            case ThemeManager.UI_MODE_THEME_MASK /* 192 */:
                onLightTopBrightnessSet(getTarget(data), source);
                return;
            case 193:
                onsCwcSwOff(source);
                return;
            case 194:
                onModesDrivingNormal(source);
                return;
            case 195:
                onWindowsControl(7, 0, getPercent(data));
                return;
            default:
                return;
        }
    }

    private void onControlNewKeyParkingOn() {
        LogUtils.i(TAG, "onControlNewKeyParkingOn");
        this.mScuVm.setKeyParkSw(true);
    }

    private void onControlNewKeyParkingOff() {
        LogUtils.i(TAG, "onControlNewKeyParkingOff");
        this.mScuVm.setKeyParkSw(false);
    }

    private void onControlNewKeyParkingEnhancedOff() {
        LogUtils.i(TAG, "onControlNewKeyParkingEnhancedOff");
        this.mScuVm.setPhoneParkSw(false);
    }

    private void onChildSafetyModeOff() {
        this.mCarBodyVm.setChildModeEnable(false);
    }

    private void onChildSafetyModeOn() {
        this.mCarBodyVm.setChildModeEnable(true);
    }

    private void onChildSafetyLocksOn(int target, int source) {
        boolean z = source != 1;
        if (target == 1) {
            this.mCarBodyVm.setChildLock(true, true, z);
        } else if (target == 2) {
            this.mCarBodyVm.setChildLock(false, true, z);
        } else if (target == 3) {
            this.mCarBodyVm.setAllChildLock(true, z);
        }
    }

    private void onChildSafetyLocksOff(int target, int source) {
        boolean z = source != 1;
        if (target == 1) {
            this.mCarBodyVm.setChildLock(true, false, z);
        } else if (target == 2) {
            this.mCarBodyVm.setChildLock(false, false, z);
        } else if (target == 3) {
            this.mCarBodyVm.setAllChildLock(false, z);
        }
    }

    private void onSuspensionHeight(int mode, int source) {
        LogUtils.d(TAG, "onSuspensionHeight: " + mode, false);
        boolean z = source != 1;
        if (mode == 0) {
            this.mChassisVm.setAsHeightMode(AsHeight.HL2, z);
        } else if (mode == 1) {
            this.mChassisVm.setAsHeightMode(AsHeight.HL1, z);
        } else if (mode == 2) {
            this.mChassisVm.setAsHeightMode(AsHeight.H0, z);
        } else if (mode == 3) {
            this.mChassisVm.setAsHeightMode(AsHeight.LL1, z);
        } else if (mode != 4) {
        } else {
            this.mChassisVm.setAsHeightMode(AsHeight.LL3, z);
        }
    }

    private void onPsnWelcomeModeOff() {
        this.mSeatVm.setPsnWelcomeMode(false);
    }

    private void onPsnWelcomeModeOn() {
        this.mSeatVm.setPsnWelcomeMode(true);
    }

    private void onPsnSeatMoveForemost() {
        int pSeatHorzPos = this.mSeatVm.getPSeatHorzPos();
        LogUtils.d(TAG, "onPsnSeatMoveForemost:" + pSeatHorzPos, false);
        if (Math.abs(90 - pSeatHorzPos) >= 2) {
            this.mSeatVm.setPSeatHorzPos(90);
        } else {
            speak(R.string.speech_seat_control_foremost);
        }
    }

    private void onPsnSeatMoveRear() {
        int pSeatHorzPos = this.mSeatVm.getPSeatHorzPos();
        LogUtils.d(TAG, "onPsnSeatMoveRear:" + pSeatHorzPos, false);
        if (Math.abs(0 - pSeatHorzPos) >= 2) {
            this.mSeatVm.setPSeatHorzPos(0);
        } else {
            speak(R.string.speech_seat_control_rear);
        }
    }

    private void onPsnSeatMoveLowest() {
        int pSeatVerPos = this.mSeatVm.getPSeatVerPos();
        LogUtils.d(TAG, "onSeatMoveLowest:" + pSeatVerPos, false);
        if (Math.abs(0 - pSeatVerPos) >= 2) {
            this.mSeatVm.setPSeatVerPos(0);
        } else {
            speak(R.string.speech_seat_control_lowest);
        }
    }

    private void onPsnSeatMoveHighest() {
        int pSeatVerPos = this.mSeatVm.getPSeatVerPos();
        LogUtils.d(TAG, "onPsnSeatMoveHighest:" + pSeatVerPos, false);
        if (Math.abs(100 - pSeatVerPos) >= 2) {
            this.mSeatVm.setPSeatVerPos(100);
        } else {
            speak(R.string.speech_seat_control_highest);
        }
    }

    private void onLegMovest(int pilot, int direction) {
        int legPosition = getLegPosition(pilot, direction);
        LogUtils.d(TAG, "onLegMovest--->pilot:" + pilot + ", direction:" + direction + ", pos:" + legPosition, false);
        if (direction == 1) {
            if (Math.abs(100 - legPosition) >= 2) {
                setLegPos(pilot, direction, 100);
            } else {
                speak(R.string.speech_leg_control_foremost);
            }
        } else if (direction == 2) {
            if (Math.abs(0 - legPosition) >= 2) {
                setLegPos(pilot, direction, 0);
            } else {
                speak(R.string.speech_leg_control_rear);
            }
        } else if (direction == 3) {
            if (Math.abs(100 - legPosition) >= 2) {
                setLegPos(pilot, direction, 100);
            } else {
                speak(R.string.speech_cushion_control_highest);
            }
        } else if (direction != 4) {
        } else {
            if (Math.abs(0 - legPosition) >= 2) {
                setLegPos(pilot, direction, 0);
            } else {
                speak(R.string.speech_cushion_control_lowest);
            }
        }
    }

    private void onLegMove(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        int pilot = getPilot(data);
        int direction = getDirection(data);
        if (pilot != 1 && pilot != 2) {
            if (pilot == 3) {
                if (direction == 1 || direction == 2) {
                    this.mSeatVm.controlRearLeftSeatStart(4, direction, 3);
                    return;
                }
                return;
            } else if (pilot != 4) {
                return;
            } else {
                if (direction == 1 || direction == 2) {
                    this.mSeatVm.controlRearRightSeatStart(4, direction, 3);
                    return;
                }
                return;
            }
        }
        int legPosition = getLegPosition(pilot, direction);
        if (legPosition != -1) {
            String str = TAG;
            LogUtils.d(str, "onLegMove--->pilot:" + pilot + ", direction:" + direction + ", pos:" + legPosition, false);
            setLegPos(pilot, direction, calculateLegPos(legPosition, direction));
            if (getSource(data) == 1) {
                long keepTime = getKeepTime(data);
                LogUtils.d(str, "onLegMove, keep: " + keepTime, false);
                if (keepTime != 0) {
                    this.mSeatVm.setSeatMenuNotShowKeepTime(pilot == 1, System.currentTimeMillis() + keepTime);
                }
            }
        }
    }

    private void onLegMoveSet(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        int pilot = getPilot(data);
        int target = getTarget(data);
        if (target > 100) {
            target = 100;
        }
        if (target < 0) {
            target = 0;
        }
        if (pilot == 1 || pilot == 2) {
            String str = TAG;
            LogUtils.d(str, "onLegMoveSet--->pilot:" + pilot + ", target:" + target, false);
            setLegPos(pilot, 1, target);
            if (getSource(data) == 1) {
                long keepTime = getKeepTime(data);
                LogUtils.d(str, "onLegMoveSet, keep: " + keepTime, false);
                if (keepTime != 0) {
                    this.mSeatVm.setSeatMenuNotShowKeepTime(pilot == 1, System.currentTimeMillis() + keepTime);
                }
            }
        }
    }

    private void onCushionMoveSet(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        int target = getTarget(data);
        int pilot = getPilot(data);
        if (target > 100) {
            target = 100;
        }
        if (target < 0) {
            target = 0;
        }
        if (pilot != 1) {
            return;
        }
        String str = TAG;
        LogUtils.d(str, "onCushionMoveSet--->pilot:" + pilot + ", target:" + target, false);
        setLegPos(pilot, 3, target);
        if (getSource(data) == 1) {
            long keepTime = getKeepTime(data);
            LogUtils.d(str, "onLegMoveSet, keep: " + keepTime, false);
            if (keepTime != 0) {
                this.mSeatVm.setSeatMenuNotShowKeepTime(pilot == 1, System.currentTimeMillis() + keepTime);
            }
        }
    }

    private void setLegPos(int pilot, int direction, int pos) {
        if (pos >= 0) {
            if (pilot != 1) {
                if (pilot != 2) {
                    return;
                }
                if (direction == 1 || direction == 2) {
                    this.mSeatVm.setPSeatLegPos(pos);
                }
            } else if (direction == 1 || direction == 2) {
                this.mSeatVm.setDSeatCushionPos(pos);
            } else if (direction == 3 || direction == 4) {
                this.mSeatVm.setDSeatLegPos(pos);
            }
        }
    }

    private int calculateLegPos(int pos, int direction) {
        if (direction != 1) {
            if (direction != 2) {
                if (direction != 3) {
                    if (direction == 4) {
                        if (Math.abs(0 - pos) >= 2) {
                            int i = pos - 20;
                            if (i >= 0) {
                                return i;
                            }
                            return 0;
                        }
                        speak(R.string.speech_leg_control_lowest);
                    }
                } else if (Math.abs(100 - pos) >= 2) {
                    int i2 = pos + 20;
                    if (i2 > 100) {
                        return 100;
                    }
                    return i2;
                } else {
                    speak(R.string.speech_leg_control_highest);
                }
            } else if (Math.abs(0 - pos) >= 2) {
                int i3 = pos - 16;
                if (i3 >= 0) {
                    return i3;
                }
                return 0;
            } else {
                speak(R.string.speech_leg_control_rear);
            }
        } else if (Math.abs(90 - pos) >= 2) {
            int i4 = pos + 16;
            if (i4 > 90) {
                return 90;
            }
            return i4;
        } else {
            speak(R.string.speech_leg_control_foremost);
        }
        return -1;
    }

    private void onPsnSeatBackrestForemost() {
        int pSeatTiltPos = this.mSeatVm.getPSeatTiltPos();
        LogUtils.d(TAG, "onPsnSeatBackrestForemost:" + pSeatTiltPos, false);
        int i = CarBaseConfig.getInstance().isSupportTiltPosReversed() ? 85 : 20;
        if (Math.abs(i - pSeatTiltPos) >= 2) {
            this.mSeatVm.setPSeatTiltPos(i);
        } else {
            speak(R.string.speech_seat_control_tilt);
        }
    }

    private void onPsnSeatBackrestRear() {
        int pSeatTiltPos = this.mSeatVm.getPSeatTiltPos();
        LogUtils.d(TAG, "onPsnSeatBackrestRear:" + pSeatTiltPos, false);
        int i = CarBaseConfig.getInstance().isSupportTiltPosReversed() ? 20 : 85;
        if (Math.abs(i - pSeatTiltPos) >= 2) {
            this.mSeatVm.setPSeatTiltPos(i);
        } else {
            speak(R.string.speech_seat_control_tilt);
        }
    }

    private void onWaistMove(int pilot, int direction) {
        LogUtils.d(TAG, "onWaistMove, pilot: " + pilot + ", direction: " + direction, false);
        int i = (direction == 1 || direction == 2) ? 5 : (direction == 3 || direction == 4) ? 6 : -1;
        if (i != -1) {
            int i2 = direction % 2 != 0 ? 1 : 2;
            if (pilot == 1) {
                this.mSeatVm.controlDriverSeatStart(i, i2, 3);
            } else {
                this.mSeatVm.controlPsnSeatStart(i, i2, 3);
            }
        }
    }

    private void onModesDrivingCrosscountry(int source) {
        LogUtils.d(TAG, "onModesDrivingCrosscountry", false);
        this.mVcuVm.setDriveModeByUser(DriveMode.OffRoad, source != 1);
    }

    private void onModesDrivingComfort() {
        LogUtils.d(TAG, "onModesDrivingComfort", false);
        this.mVcuVm.setDriveModeByUser(DriveMode.Comfort);
    }

    private void onModesDrivingRacer() {
        LogUtils.d(TAG, "onModesDrivingRacer", false);
        this.mVcuVm.setXSportDriveModeByUser(XSportDriveMode.Race);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.carcontrol.speech.ISpeechModel
    public void onQuery(String event, String data, String callback) {
        event.hashCode();
        char c = 65535;
        switch (event.hashCode()) {
            case -2121187452:
                if (event.equals(QueryCarControlEvent.CONTROL_COMFORTABLE_DRIVING_MODE_SUPPORT)) {
                    c = 0;
                    break;
                }
                break;
            case -2077422469:
                if (event.equals("control.massage.current.status")) {
                    c = 1;
                    break;
                }
                break;
            case -2054773087:
                if (event.equals(QueryCarControlEvent.GET_PAGE_OPEN_STATUS)) {
                    c = 2;
                    break;
                }
                break;
            case -2034277098:
                if (event.equals(QueryCarControlEvent.CONTROL_SCISSOR_DOOR_LEFT_RUNNING_SUPPORT)) {
                    c = 3;
                    break;
                }
                break;
            case -2023910910:
                if (event.equals("control.auto.tail.status")) {
                    c = 4;
                    break;
                }
                break;
            case -1930591132:
                if (event.equals("control.suspension.rigidity.support")) {
                    c = 5;
                    break;
                }
                break;
            case -1912684481:
                if (event.equals("control.tail.status")) {
                    c = 6;
                    break;
                }
                break;
            case -1897516928:
                if (event.equals(QueryCarControlEvent.GET_STATUS_LIGHT_ATMOSPHERE_COLOR)) {
                    c = 7;
                    break;
                }
                break;
            case -1870446874:
                if (event.equals("control.back.welcome.mode.support")) {
                    c = '\b';
                    break;
                }
                break;
            case -1790809004:
                if (event.equals("control.available.mileage")) {
                    c = '\t';
                    break;
                }
                break;
            case -1763952918:
                if (event.equals("control.driving.racer.support")) {
                    c = '\n';
                    break;
                }
                break;
            case -1699199845:
                if (event.equals("control.light.atmosphere.mode.set.support")) {
                    c = 11;
                    break;
                }
                break;
            case -1678437728:
                if (event.equals(QueryCarControlEvent.CONTROL_LEG_HEIGHT_GET)) {
                    c = '\f';
                    break;
                }
                break;
            case -1657837042:
                if (event.equals("control.swing.support")) {
                    c = '\r';
                    break;
                }
                break;
            case -1656659569:
                if (event.equals(QueryCarControlEvent.GET_EXTRA_TRUNK_STATUS)) {
                    c = 14;
                    break;
                }
                break;
            case -1581245114:
                if (event.equals("control.new.drive.mode")) {
                    c = 15;
                    break;
                }
                break;
            case -1550136303:
                if (event.equals(QueryCarControlEvent.CONTROL_SCISSOR_DOOR_RIGHT_RUNNING_SUPPORT)) {
                    c = 16;
                    break;
                }
                break;
            case -1546994368:
                if (event.equals("control.sliding.door.support")) {
                    c = 17;
                    break;
                }
                break;
            case -1521777381:
                if (event.equals(QueryCarControlEvent.GET_CURR_WIPER_LEVEL)) {
                    c = 18;
                    break;
                }
                break;
            case -1519100965:
                if (event.equals("control.massage.support")) {
                    c = 19;
                    break;
                }
                break;
            case -1482534751:
                if (event.equals(QueryCarControlEvent.GET_SUPPORT_SEAT)) {
                    c = 20;
                    break;
                }
                break;
            case -1431154399:
                if (event.equals(QueryCarControlEvent.CONTROL_SUPPORT_ENERGY_RECYCLE_REASON)) {
                    c = 21;
                    break;
                }
                break;
            case -1414817355:
                if (event.equals(QueryCarControlEvent.CONTROL_CAPSULE_MODE)) {
                    c = 22;
                    break;
                }
                break;
            case -1412321594:
                if (event.equals(QueryCarControlEvent.GET_SET_SPEECH_WAKEUP_STATUS)) {
                    c = 23;
                    break;
                }
                break;
            case -1405928226:
                if (event.equals("control.leg.support")) {
                    c = 24;
                    break;
                }
                break;
            case -1392585057:
                if (event.equals(QueryCarControlEvent.GET_STATUS_OPEN_R_CHARGE_PORT)) {
                    c = 25;
                    break;
                }
                break;
            case -1322129327:
                if (event.equals("control.wireless.charging.support")) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case -1268774449:
                if (event.equals(QueryCarControlEvent.CONTROL_SCISSOR_DOOR_LEFT_CLOSE_SUPPORT)) {
                    c = 27;
                    break;
                }
                break;
            case -1207201665:
                if (event.equals("control.apa.status")) {
                    c = 28;
                    break;
                }
                break;
            case -1200577133:
                if (event.equals("control.dc.charge.support")) {
                    c = 29;
                    break;
                }
                break;
            case -1193245651:
                if (event.equals(QueryCarControlEvent.GET_SUPPORT_OPEN_TRUNK)) {
                    c = 30;
                    break;
                }
                break;
            case -1176403502:
                if (event.equals("control.current.suspension.height")) {
                    c = 31;
                    break;
                }
                break;
            case -1144792468:
                if (event.equals(QueryCarControlEvent.GET_STATUS_LIGHT_ATMOSPHERE_BRIGHTNESS)) {
                    c = ' ';
                    break;
                }
                break;
            case -1139999239:
                if (event.equals(QueryCarControlEvent.IS_SUPPORT_CLOSE_MIRROR)) {
                    c = '!';
                    break;
                }
                break;
            case -1102263664:
                if (event.equals(QueryCarControlEvent.IS_SUPPORT_UNLOCK_TRUNK)) {
                    c = '\"';
                    break;
                }
                break;
            case -1098292021:
                if (event.equals("control.suspension.fix.status")) {
                    c = '#';
                    break;
                }
                break;
            case -1039017974:
                if (event.equals("control.vpa.status")) {
                    c = '$';
                    break;
                }
                break;
            case -1037296857:
                if (event.equals("control.suspension.flat.status")) {
                    c = '%';
                    break;
                }
                break;
            case -1033029837:
                if (event.equals("control.intelligent.high.light.support")) {
                    c = '&';
                    break;
                }
                break;
            case -1021284083:
                if (event.equals("control.light.top.support")) {
                    c = '\'';
                    break;
                }
                break;
            case -988561248:
                if (event.equals("control.window.init.state")) {
                    c = '(';
                    break;
                }
                break;
            case -970629393:
                if (event.equals("control.suspension.support")) {
                    c = ')';
                    break;
                }
                break;
            case -952459949:
                if (event.equals(QueryCarControlEvent.GET_STATUS_CLOSE_R_CHARGE_PORT)) {
                    c = '*';
                    break;
                }
                break;
            case -945429027:
                if (event.equals(QueryCarControlEvent.CONTROL_VIP_CHAIR_STATUS)) {
                    c = '+';
                    break;
                }
                break;
            case -932480699:
                if (event.equals(QueryCarControlEvent.GET_STATUS_OPEN_L_CHARGE_PORT)) {
                    c = ',';
                    break;
                }
                break;
            case -922734476:
                if (event.equals("control.rearview.doorknob.auto.open.support")) {
                    c = '-';
                    break;
                }
                break;
            case -899471618:
                if (event.equals("control.light.language.setting.preview.support")) {
                    c = '.';
                    break;
                }
                break;
            case -894608748:
                if (event.equals(QueryCarControlEvent.CONTROL_XPEDAL_SUPPORT)) {
                    c = '/';
                    break;
                }
                break;
            case -885502693:
                if (event.equals("control.xkey.setting.support")) {
                    c = '0';
                    break;
                }
                break;
            case -760888941:
                if (event.equals("control.swing.running.mode")) {
                    c = '1';
                    break;
                }
                break;
            case -734332941:
                if (event.equals("control.light.language.autoplay.state")) {
                    c = '2';
                    break;
                }
                break;
            case -678632592:
                if (event.equals("control.suspension.auto.support")) {
                    c = '3';
                    break;
                }
                break;
            case -598119942:
                if (event.equals(QueryCarControlEvent.XPU_NGP_STATUS)) {
                    c = '4';
                    break;
                }
                break;
            case -525022608:
                if (event.equals("control.suspension.welcome.mode.support")) {
                    c = '5';
                    break;
                }
                break;
            case -516512003:
                if (event.equals("control.child.safety.mode.support")) {
                    c = '6';
                    break;
                }
                break;
            case -432618288:
                if (event.equals("carcontrol.support.driving.comfort.mode")) {
                    c = '7';
                    break;
                }
                break;
            case -347755187:
                if (event.equals("control.esp.fault.get")) {
                    c = '8';
                    break;
                }
                break;
            case -224335898:
                if (event.equals("control.massage.running.mode")) {
                    c = '9';
                    break;
                }
                break;
            case -215350831:
                if (event.equals("control.light.height.support")) {
                    c = ':';
                    break;
                }
                break;
            case -189816735:
                if (event.equals("control.sliding.door.status")) {
                    c = ';';
                    break;
                }
                break;
            case -178106686:
                if (event.equals(QueryCarControlEvent.IS_SUPPORT_OPEN_R_CHARGE_PORT)) {
                    c = '<';
                    break;
                }
                break;
            case -173237234:
                if (event.equals("control.intelligent.high.light.status")) {
                    c = '=';
                    break;
                }
                break;
            case -122970160:
                if (event.equals("carcontrol.support.driving.crosscountry.mode")) {
                    c = '>';
                    break;
                }
                break;
            case -112590780:
                if (event.equals("control.psn.welcome.mode.support")) {
                    c = '?';
                    break;
                }
                break;
            case -34469682:
                if (event.equals("unity.page.support.carcontrol")) {
                    c = '@';
                    break;
                }
                break;
            case 6319454:
                if (event.equals(QueryCarControlEvent.CONTROL_GET_WINDOWS_STATE_SUPPORT)) {
                    c = 'A';
                    break;
                }
                break;
            case 41162922:
                if (event.equals("control.rearview.mirror.reverse.down.support")) {
                    c = 'B';
                    break;
                }
                break;
            case 63140954:
                if (event.equals("control.as.xpilot.mode.status")) {
                    c = 'C';
                    break;
                }
                break;
            case 129192553:
                if (event.equals("control.waist.position.support")) {
                    c = 'D';
                    break;
                }
                break;
            case 170310017:
                if (event.equals("control.welcome.mode.support")) {
                    c = 'E';
                    break;
                }
                break;
            case 174239245:
                if (event.equals("control.as.transport.mode.status")) {
                    c = 'F';
                    break;
                }
                break;
            case 264514798:
                if (event.equals(QueryCarControlEvent.IS_SUPPORT_DRIVING_MODE)) {
                    c = 'G';
                    break;
                }
                break;
            case 325191539:
                if (event.equals(QueryCarControlEvent.GET_WINDOW_STATUS)) {
                    c = 'H';
                    break;
                }
                break;
            case 355863260:
                if (event.equals(QueryCarControlEvent.GET_MIRROR_STATUS)) {
                    c = 'I';
                    break;
                }
                break;
            case 359526324:
                if (event.equals(QueryCarControlEvent.IS_SUPPORT_OPEN_L_CHARGE_PORT)) {
                    c = 'J';
                    break;
                }
                break;
            case 423704689:
                if (event.equals("control.child.safety.locks.status")) {
                    c = 'K';
                    break;
                }
                break;
            case 425873261:
                if (event.equals(QueryCarControlEvent.GET_STATUS_CLOSE_L_CHARGE_PORT)) {
                    c = 'L';
                    break;
                }
                break;
            case 437797006:
                if (event.equals("control.first.time.light.geek")) {
                    c = 'M';
                    break;
                }
                break;
            case 484711863:
                if (event.equals(QueryCarControlEvent.IS_SUPPORT_CONTROL_MIRROR)) {
                    c = 'N';
                    break;
                }
                break;
            case 499515048:
                if (event.equals("control.current.light.top.brightness")) {
                    c = 'O';
                    break;
                }
                break;
            case 548112633:
                if (event.equals(QueryCarControlEvent.IS_STEERING_MODE_ADJUSTABLE)) {
                    c = 'P';
                    break;
                }
                break;
            case 570315103:
                if (event.equals("meditation.copilot.state")) {
                    c = 'Q';
                    break;
                }
                break;
            case 580869774:
                if (event.equals(QueryCarControlEvent.IS_SUPPORT_CLOSE_R_CHARGE_PORT)) {
                    c = 'R';
                    break;
                }
                break;
            case 596178904:
                if (event.equals("status.spacecapsule.submode")) {
                    c = 'S';
                    break;
                }
                break;
            case 631100506:
                if (event.equals(QueryCarControlEvent.CONTROL_SCISSOR_DOOR_RIGHT_OPEN_SUPPORT)) {
                    c = 'T';
                    break;
                }
                break;
            case 676171332:
                if (event.equals(QueryCarControlEvent.CONTROL_LOW_SPEED_ANALOG_SOUND_SUPPORT)) {
                    c = 'U';
                    break;
                }
                break;
            case 689991955:
                if (event.equals(QueryCarControlEvent.CONTROL_ELECTRIC_CURTAIN_SUPPORT)) {
                    c = 'V';
                    break;
                }
                break;
            case 700759210:
                if (event.equals(QueryCarControlEvent.IS_SUPPORT_LITE_ATMOSPHERE)) {
                    c = 'W';
                    break;
                }
                break;
            case 732967566:
                if (event.equals("control.xsport.support")) {
                    c = 'X';
                    break;
                }
                break;
            case 753156555:
                if (event.equals("gui.page.close.carcontrol")) {
                    c = 'Y';
                    break;
                }
                break;
            case 862019665:
                if (event.equals("control.trailer.mode.status")) {
                    c = 'Z';
                    break;
                }
                break;
            case 929878507:
                if (event.equals("control.shortcut.key.setting.support")) {
                    c = '[';
                    break;
                }
                break;
            case 956386737:
                if (event.equals(QueryCarControlEvent.IS_SUPPORT_ENERGY_RECYCLE)) {
                    c = '\\';
                    break;
                }
                break;
            case 958945394:
                if (event.equals("control.modes.driving.xsport")) {
                    c = ']';
                    break;
                }
                break;
            case 960380480:
                if (event.equals("control.massage.strength.current")) {
                    c = '^';
                    break;
                }
                break;
            case 1018980706:
                if (event.equals("control.leg.position.get")) {
                    c = '_';
                    break;
                }
                break;
            case 1105032605:
                if (event.equals("carcontrol.support.key.park")) {
                    c = '`';
                    break;
                }
                break;
            case 1200226524:
                if (event.equals(QueryCarControlEvent.IS_SUPPORT_CLOSE_L_CHARGE_PORT)) {
                    c = 'a';
                    break;
                }
                break;
            case 1333952193:
                if (event.equals(QueryCarControlEvent.GET_SUPPORT_CLOSE_TRUNK)) {
                    c = 'b';
                    break;
                }
                break;
            case 1338946660:
                if (event.equals(QueryCarControlEvent.GET_PSN_SUPPORT_SEAT)) {
                    c = 'c';
                    break;
                }
                break;
            case 1348874497:
                if (event.equals("control.light.atmosphere.color.support")) {
                    c = 'd';
                    break;
                }
                break;
            case 1364128650:
                if (event.equals(QueryCarControlEvent.CONTROL_SCISSOR_DOOR_RIGHT_CLOSE_SUPPORT)) {
                    c = 'e';
                    break;
                }
                break;
            case 1382966729:
                if (event.equals("control.current.light.top.status")) {
                    c = 'f';
                    break;
                }
                break;
            case 1397046919:
                if (event.equals(QueryCarControlEvent.IS_TAIRPRESSURE_NORMAL)) {
                    c = 'g';
                    break;
                }
                break;
            case 1654546805:
                if (event.equals(QueryCarControlEvent.CONTROL_SCISSOR_DOOR_LEFT_OPEN_SUPPORT)) {
                    c = 'h';
                    break;
                }
                break;
            case 1697319758:
                if (event.equals(QueryCarControlEvent.CONTROL_LAMP_SIGNAL_SUPPORT)) {
                    c = 'i';
                    break;
                }
                break;
            case 1746577907:
                if (event.equals("control.welcome.mode.soundeffect.support")) {
                    c = 'j';
                    break;
                }
                break;
            case 1803743645:
                if (event.equals("control.show.mode.status")) {
                    c = 'k';
                    break;
                }
                break;
            case 1821396103:
                if (event.equals("control.as.hoist.mode.status")) {
                    c = 'l';
                    break;
                }
                break;
            case 1924430189:
                if (event.equals("control.swing.strength.current")) {
                    c = 'm';
                    break;
                }
                break;
            case 1942464667:
                if (event.equals("control.light.atmosphere.current.mode")) {
                    c = 'n';
                    break;
                }
                break;
            case 1961007153:
                if (event.equals(QueryCarControlEvent.GET_TRUNK_STATUS)) {
                    c = 'o';
                    break;
                }
                break;
            case 1986146024:
                if (event.equals("control.swing.current.status")) {
                    c = 'p';
                    break;
                }
                break;
            case 2068716062:
                if (event.equals("gui.page.open.state.carcontrol")) {
                    c = 'q';
                    break;
                }
                break;
            case 2133184846:
                if (event.equals("control.welcome.mode.soundeffect.status")) {
                    c = 'r';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                reply(event, callback, Integer.valueOf(getControlComfortableDrivingModeSupport()));
                return;
            case 1:
                reply(event, callback, Integer.valueOf(getMassageCurrentStatus(getPilot(data))));
                return;
            case 2:
                reply(event, callback, Integer.valueOf(getGuiPageOpenState(data)));
                return;
            case 3:
                reply(event, callback, Integer.valueOf(getControlScissorDoorLeftRunningSupport()));
                return;
            case 4:
                reply(event, callback, Integer.valueOf(getArsWorkMode()));
                return;
            case 5:
                reply(event, callback, Integer.valueOf(getSuspensionRigiditySupport()));
                return;
            case 6:
                reply(event, callback, Integer.valueOf(getArsStatus()));
                return;
            case 7:
                reply(event, callback, Integer.valueOf(getAtmosphereColorStatus()));
                return;
            case '\b':
                reply(event, callback, Integer.valueOf(getSupportBackWelcomeMode()));
                return;
            case '\t':
                reply(event, callback, Integer.valueOf(getAvailableMileage()));
                return;
            case '\n':
                reply(event, callback, Integer.valueOf(getDrivingRacerSupport()));
                return;
            case 11:
                reply(event, callback, Integer.valueOf(getLightAtmosphereModeSupport()));
                return;
            case '\f':
                reply(event, callback, Integer.valueOf(getLegHeight()));
                return;
            case '\r':
                reply(event, callback, Integer.valueOf(getSupportSwing()));
                return;
            case 14:
                reply(event, callback, Integer.valueOf(getExtraTrunkStatus()));
                return;
            case 15:
                reply(event, callback, Integer.valueOf(getNewDrivingMode()));
                return;
            case 16:
                reply(event, callback, Integer.valueOf(getControlScissorDoorRightRunningSupport()));
                return;
            case 17:
                reply(event, callback, Integer.valueOf(getSlideDoorSupport()));
                break;
            case 18:
                reply(event, callback, Integer.valueOf(getWiperInterval()));
                return;
            case 19:
                reply(event, callback, Integer.valueOf(getSupportMassage()));
                return;
            case 20:
                reply(event, callback, Integer.valueOf(getSupportSeat()));
                return;
            case 21:
                reply(event, callback, Integer.valueOf(getControlSupportEnergyRecycleReason()));
                return;
            case 22:
                reply(event, callback, Integer.valueOf(getCapsuleCurrentMode()));
                return;
            case 23:
                reply(event, callback, Integer.valueOf(getDoorKeyValue()));
                return;
            case 24:
                reply(event, callback, Integer.valueOf(getSupportLeg(getPilot(data), getDirection(data))));
                return;
            case 25:
                reply(event, callback, Integer.valueOf(getStatusChargePortControl(1, 0)));
                return;
            case 26:
                reply(event, callback, Integer.valueOf(getSupportCwc()));
                return;
            case 27:
                reply(event, callback, Integer.valueOf(getControlScissorDoorLeftCloseSupport()));
                return;
            case 28:
                reply(event, callback, Integer.valueOf(getApaStatus()));
                return;
            case 29:
                reply(event, callback, Boolean.valueOf(getInDcCharge()));
                return;
            case 30:
                reply(event, callback, Integer.valueOf(getSupportOpenTrunk()));
                return;
            case 31:
                reply(event, callback, Integer.valueOf(getCurrentSuspensionHeight()));
                return;
            case ' ':
                reply(event, callback, Integer.valueOf(getAtmosphereBrightnessStatus()));
                return;
            case '!':
                reply(event, callback, Boolean.valueOf(isSupportCloseMirror()));
                return;
            case '\"':
                reply(event, callback, Boolean.valueOf(isSupportUnlockTrunk()));
                return;
            case '#':
                reply(event, callback, Integer.valueOf(getSuspensionFixStatus()));
                return;
            case '$':
                reply(event, callback, Integer.valueOf(getVpaStatus()));
                return;
            case '%':
                reply(event, callback, Integer.valueOf(getSuspensionFlatStatus()));
                return;
            case '&':
                reply(event, callback, Integer.valueOf(getIhbSupport()));
                return;
            case '\'':
                reply(event, callback, Integer.valueOf(getLightTopSupport()));
                return;
            case '(':
                reply(event, callback, Integer.valueOf(getCarWindowCanControl(getMode(data))));
                return;
            case ')':
                reply(event, callback, Integer.valueOf(getSuspensionSupport()));
                return;
            case '*':
                reply(event, callback, Integer.valueOf(getStatusChargePortControl(1, 1)));
                return;
            case '+':
                reply(event, callback, Integer.valueOf(getVipChairStatus()));
                return;
            case ',':
                reply(event, callback, Integer.valueOf(getStatusChargePortControl(0, 0)));
                return;
            case '-':
                reply(event, callback, Integer.valueOf(getSupportDoorKnobAutoOpen()));
                return;
            case '.':
                reply(event, callback, Integer.valueOf(getControlLightLanguageSettingPreviewSupport()));
                return;
            case '/':
                reply(event, callback, Integer.valueOf(getControlXpedalStatus()));
                return;
            case '0':
                reply(event, callback, Integer.valueOf(getControlXkeySettingSupport(Integer.valueOf(getMode(data)))));
                return;
            case '1':
                reply(event, callback, Integer.valueOf(getSwingRunningMode(getPilot(data))));
                return;
            case '2':
                reply(event, callback, Integer.valueOf(getControlLightLanguageAutoPlayState()));
                return;
            case '3':
                reply(event, callback, Integer.valueOf(getSupportSuspensionAuto()));
                return;
            case '4':
                reply(event, callback, Integer.valueOf(getNgpStatus()));
                return;
            case '5':
                reply(event, callback, Integer.valueOf(getSupportSuspensionWelcomeMode()));
                return;
            case '6':
                reply(event, callback, Integer.valueOf(getSupportChildSafetyMode()));
                return;
            case '7':
                reply(event, callback, Integer.valueOf(isSupportDrivingComfortMode()));
                return;
            case '8':
                reply(event, callback, Integer.valueOf(getEspFault()));
                return;
            case '9':
                reply(event, callback, Integer.valueOf(getMassageRunningMode(getPilot(data))));
                return;
            case ':':
                reply(event, callback, Integer.valueOf(getLightHeightSupport()));
                return;
            case ';':
                break;
            case '<':
                reply(event, callback, Boolean.valueOf(isSupportControlChargePort(1, 0)));
                return;
            case '=':
                reply(event, callback, Integer.valueOf(getIhbStatus()));
                return;
            case '>':
                reply(event, callback, Integer.valueOf(isSupportDrivingCrossCountryMode()));
                return;
            case '?':
                reply(event, callback, Integer.valueOf(getSupportPsnWelcomeMode()));
                return;
            case '@':
                reply(event, callback, Integer.valueOf(getUnityPageOpenState(data)));
                return;
            case 'A':
                reply(event, callback, getControlWindowsStateSupport());
                return;
            case 'B':
                reply(event, callback, Integer.valueOf(getSupportMirrorReverseMode()));
                return;
            case 'C':
                reply(event, callback, Integer.valueOf(getAsXPilotModeStatus(getMode(data))));
                return;
            case 'D':
                reply(event, callback, Integer.valueOf(getSupportWaistPosition(getPilot(data))));
                return;
            case 'E':
                reply(event, callback, Integer.valueOf(getSupportWelcomeMode()));
                return;
            case 'F':
                reply(event, callback, Integer.valueOf(getAsTransportModeStatus()));
                return;
            case 'G':
                reply(event, callback, Boolean.valueOf(isSupportDrivingMode()));
                return;
            case 'H':
                reply(event, callback, Integer.valueOf(getWindowStatus()));
                return;
            case 'I':
                reply(event, callback, Integer.valueOf(getMirrorStatus()));
                return;
            case 'J':
                reply(event, callback, Boolean.valueOf(isSupportControlChargePort(0, 0)));
                return;
            case 'K':
                reply(event, callback, Integer.valueOf(getChildSafetyLocksStatus(getTarget(data))));
                return;
            case 'L':
                reply(event, callback, Integer.valueOf(getStatusChargePortControl(0, 1)));
                return;
            case 'M':
                reply(event, callback, Integer.valueOf(getFirsTimeLightGeek(getMode(data))));
                return;
            case 'N':
                reply(event, callback, Boolean.valueOf(isSupportControlMirror()));
                return;
            case 'O':
                reply(event, callback, Integer.valueOf(getLightTopBrightness()));
                return;
            case 'P':
                reply(event, callback, Integer.valueOf(isSteeringModeAdjustable()));
                return;
            case 'Q':
                reply(event, callback, Boolean.valueOf(getCopilotState()));
                return;
            case 'R':
                reply(event, callback, Boolean.valueOf(isSupportControlChargePort(1, 1)));
                return;
            case 'S':
                reply(event, callback, Integer.valueOf(getSleepMode()));
                return;
            case 'T':
                reply(event, callback, Integer.valueOf(getControlScissorDoorRightOpenSupport()));
                return;
            case 'U':
                reply(event, callback, Integer.valueOf(getControlLowSpeedAnalogSoundSupport()));
                return;
            case 'V':
                reply(event, callback, Integer.valueOf(getControlElectricCurtainSupport()));
                return;
            case 'W':
                reply(event, callback, Boolean.valueOf(isSupportAtmosphere()));
                return;
            case 'X':
                reply(event, callback, Integer.valueOf(getXSportSupport()));
                return;
            case 'Y':
                reply(event, callback, Integer.valueOf(closeNapa2DPageUI(getPageId(data))));
                return;
            case 'Z':
                reply(event, callback, Integer.valueOf(getTrailerModeStatus()));
                return;
            case '[':
                reply(event, callback, Integer.valueOf(getSupportDoorKeySetting(getMode(data))));
                return;
            case '\\':
                reply(event, callback, Boolean.valueOf(isSupportEnergyRecycle()));
                return;
            case ']':
                reply(event, callback, Integer.valueOf(onModesDrivingXsport(getMode(data))));
                return;
            case '^':
                reply(event, callback, Integer.valueOf(getMassageCurrentStrength(getPilot(data))));
                return;
            case '_':
                reply(event, callback, Integer.valueOf(getLegPosition(getPilot(data), getDirection(data))));
                return;
            case '`':
                reply(event, callback, Integer.valueOf(getKeyParkSupport(getMode(data))));
                return;
            case 'a':
                reply(event, callback, Boolean.valueOf(isSupportControlChargePort(0, 1)));
                return;
            case 'b':
                reply(event, callback, Integer.valueOf(getSupportCloseTrunk()));
                return;
            case 'c':
                reply(event, callback, Integer.valueOf(getSupportPsnSeat()));
                return;
            case 'd':
                reply(event, callback, Integer.valueOf(getLightAtmosphereColorSupport()));
                return;
            case 'e':
                reply(event, callback, Integer.valueOf(getControlScissorDoorRightCloseSupport()));
                return;
            case 'f':
                reply(event, callback, Integer.valueOf(getLightTopStatus(getPilot(data))));
                return;
            case 'g':
                reply(event, callback, Boolean.valueOf(isTirePressureNormal()));
                return;
            case 'h':
                reply(event, callback, Integer.valueOf(getControlScissorDoorLeftOpenSupport()));
                return;
            case 'i':
                reply(event, callback, Integer.valueOf(getControlLampSignalSupport()));
                return;
            case 'j':
                reply(event, callback, Integer.valueOf(getSupportWelcomeModeSoundEffect()));
                return;
            case 'k':
                reply(event, callback, Integer.valueOf(getShowModeStatus()));
                return;
            case 'l':
                reply(event, callback, Integer.valueOf(getAsHoistModeStatus()));
                return;
            case 'm':
                reply(event, callback, Integer.valueOf(getSwingCurrentStrength(getPilot(data))));
                return;
            case 'n':
                reply(event, callback, Integer.valueOf(getLightAtmosphereCurrentMode()));
                return;
            case 'o':
                reply(event, callback, Integer.valueOf(getTrunkStatus()));
                return;
            case 'p':
                reply(event, callback, Integer.valueOf(getSwingCurrentStatus(getPilot(data))));
                return;
            case 'q':
                reply(event, callback, Integer.valueOf(getNapa2DPageOpenState(getPageId(data))));
                return;
            case 'r':
                reply(event, callback, Integer.valueOf(getWelcomeModeSoundEffectStatus()));
                return;
            default:
                return;
        }
        reply(event, callback, Integer.valueOf(getSlideDoorStatus(getMode(data))));
    }

    private int getApaStatus() {
        return this.mScuVm.getAutoParkSw() == ScuResponse.ON ? 1 : 0;
    }

    private int getVpaStatus() {
        return this.mScuVm.getMemoryParkSw() == ApResponse.ON ? 1 : 0;
    }

    private int getSupportWaistPosition(int pilot) {
        if (pilot != 1) {
            if (pilot != 2) {
                return 0;
            }
            return CarBaseConfig.getInstance().isSupportPsnLumbar() ? 1 : 0;
        }
        return CarBaseConfig.getInstance().isSupportDrvLumbar() ? 1 : 0;
    }

    private int getSupportPsnWelcomeMode() {
        return CarBaseConfig.getInstance().isSupportPsnWelcomeMode() ? 1 : 0;
    }

    private int getSupportChildSafetyMode() {
        return CarBaseConfig.getInstance().isSupportChildMode() ? 1 : 0;
    }

    private int getChildSafetyLocksStatus(int target) {
        if (target == 1) {
            return this.mCarBodyVm.isLeftChildLocked() ? 1 : 0;
        }
        if (target == 2) {
            return this.mCarBodyVm.isRightChildLocked() ? 1 : 0;
        }
        return 0;
    }

    private int getCurrentSuspensionHeight() {
        AsHeight asHeightMode = this.mChassisVm.getAsHeightMode();
        if (asHeightMode != null) {
            int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight[asHeightMode.ordinal()];
            int i2 = 1;
            if (i != 1) {
                if (i != 2) {
                    i2 = 3;
                    if (i != 3) {
                        i2 = 4;
                        if (i != 4) {
                            return 2;
                        }
                    }
                }
                return i2;
            }
            return 0;
        }
        return 2;
    }

    private int getSuspensionSupport() {
        int i;
        if (CarBaseConfig.getInstance().isSupportAirSuspension()) {
            i = this.mChassisVm.getAsFaultStatus() ? 2 : 1;
        } else {
            i = 0;
        }
        LogUtils.d(TAG, "getSuspensionSupport, value: " + i, false);
        return i;
    }

    private int getAsXPilotModeStatus(int mode) {
        int bcmCmd;
        if (CarBaseConfig.getInstance().isSupportAirSuspension()) {
            int asTargetMinHeightRequest = this.mXpuVm.getAsTargetMinHeightRequest();
            int asTargetMaxHeightRequest = this.mXpuVm.getAsTargetMaxHeightRequest();
            if (mode == 0) {
                bcmCmd = AsHeight.HL2.toBcmCmd();
            } else if (mode == 1) {
                bcmCmd = AsHeight.HL1.toBcmCmd();
            } else if (mode == 2) {
                bcmCmd = AsHeight.H0.toBcmCmd();
            } else if (mode == 3) {
                bcmCmd = AsHeight.LL1.toBcmCmd();
            } else {
                bcmCmd = mode != 4 ? -1 : AsHeight.LL3.toBcmCmd();
            }
            LogUtils.i(TAG, "getAsXPilotModeStatus, mode: " + mode + ", mAsMaxTarget: " + asTargetMaxHeightRequest + ", mAsMinTarget: " + asTargetMinHeightRequest, false);
            if (bcmCmd != -1 && asTargetMaxHeightRequest <= asTargetMinHeightRequest && bcmCmd >= asTargetMaxHeightRequest && bcmCmd <= asTargetMinHeightRequest) {
                return 1;
            }
        }
        return 0;
    }

    private int getAsTransportModeStatus() {
        return (CarBaseConfig.getInstance().isSupportAirSuspension() && AsEngineerMode.TRANSPORT == this.mChassisVm.getAsEngineerMode()) ? 1 : 0;
    }

    private int getAsHoistModeStatus() {
        if (CarBaseConfig.getInstance().isSupportAirSuspension()) {
            return (this.mChassisVm.getAsLockModeStatus() || this.mChassisVm.getAsHoistModeSwitchStatus()) ? 1 : 0;
        }
        return 0;
    }

    private int getLegPosition(int pilot, int direction) {
        int dSeatCushionPos;
        if (pilot != 1) {
            if (pilot == 2) {
                if ((direction == 1 || direction == 2) && CarBaseConfig.getInstance().isSupportPsnLeg()) {
                    dSeatCushionPos = this.mSeatVm.getPSeatLegPos();
                }
            } else {
                LogUtils.d(TAG, "getLegPosition unknown seat " + pilot, false);
            }
            dSeatCushionPos = -1;
        } else if (direction == 1 || direction == 2) {
            if (CarBaseConfig.getInstance().isSupportDrvCushion()) {
                dSeatCushionPos = this.mSeatVm.getDSeatCushionPos();
            }
            dSeatCushionPos = -1;
        } else {
            if ((direction == 3 || direction == 4) && CarBaseConfig.getInstance().isSupportDrvLeg()) {
                dSeatCushionPos = this.mSeatVm.getDSeatLegPos();
            }
            dSeatCushionPos = -1;
        }
        LogUtils.d(TAG, "getLegPosition--->pilot:" + pilot + ", direction=" + direction, false);
        return dSeatCushionPos;
    }

    private int getSupportLeg(int pilot, int direction) {
        String str = TAG;
        LogUtils.d(str, "getSupportLeg--->pilot:" + pilot + ", direction=" + direction, false);
        if (pilot == 1) {
            if (direction == 1 || direction == 2) {
                return CarBaseConfig.getInstance().isSupportDrvCushion() ? 1 : 0;
            }
            if (direction == 3 || direction == 4) {
                return CarBaseConfig.getInstance().isSupportDrvLeg() ? 1 : 0;
            }
            return 0;
        } else if (pilot != 2) {
            boolean isSupportRearSeatLeg = ((pilot == 3 || pilot == 4) && (direction == 1 || direction == 2)) ? CarBaseConfig.getInstance().isSupportRearSeatLeg() : false;
            LogUtils.d(str, "getLegPosition unknown seat " + pilot, false);
            return isSupportRearSeatLeg ? 1 : 0;
        } else if (direction == 1 || direction == 2) {
            return CarBaseConfig.getInstance().isSupportPsnLeg() ? 1 : 0;
        } else {
            return 0;
        }
    }

    private int getEspFault() {
        return this.mChassisVm.getEspFault() ? 1 : 0;
    }

    private int isSupportDrivingCrossCountryMode() {
        return CarBaseConfig.getInstance().isSupportEspMudMode() ? 1 : 0;
    }

    private int isSupportDrivingComfortMode() {
        return CarBaseConfig.getInstance().isSupportDriveModeNewArch() ? 1 : 0;
    }

    private boolean getCopilotState() {
        return this.mMeditationViewModel.getPnsState();
    }

    protected void onLightHomeOff() {
        LogUtils.d(TAG, "onLightHomeOff", false);
        this.mLampVm.setLightMeHome(false);
    }

    protected void onLightHomeOn() {
        LogUtils.d(TAG, "onLightHomeOn", false);
        this.mLampVm.setLightMeHome(true);
    }

    protected void onLightLowOff(ControlReason controlReason) {
        LogUtils.d(TAG, "onLightLowOff reason:" + controlReason.getControlReason(), false);
        if (controlReason.getControlReason() == 0) {
            this.mLampVm.setHeadLampGroup(0);
        }
    }

    protected void onLightLowOn() {
        LogUtils.d(TAG, "onLightLowOn", false);
        this.mLampVm.setHeadLampGroup(2);
    }

    protected void onLightPositionOn() {
        LogUtils.d(TAG, "onLightPositionOn", false);
        this.mLampVm.setHeadLampGroup(1);
    }

    protected void onLightPositionOff() {
        LogUtils.d(TAG, "onLightPositionOff", false);
        this.mLampVm.setHeadLampGroup(0);
    }

    protected void onLightAutoOn() {
        LogUtils.d(TAG, "onLightAutoOn", false);
        this.mLampVm.setHeadLampGroup(3);
    }

    protected void onLightAutoOff() {
        LogUtils.d(TAG, "onLightAutoOff", false);
        this.mLampVm.setHeadLampGroup(0);
    }

    private void onSetLightHeight(int mode, int source) {
        LogUtils.d(TAG, "onSetLightHeight:" + mode + ", source: " + source, false);
        boolean z = source != 1;
        if (mode == 0) {
            this.mLampVm.setAutoLampHeight(true, z);
        } else if (mode == 1 || mode == 2 || mode == 3 || mode == 4) {
            this.mLampVm.setLampHeightLevel(LampHeightLevel.values()[mode - 1].toBcmValue(), z);
        }
    }

    private int getLightHeightSupport() {
        int i;
        if (CarBaseConfig.getInstance().isSupportAutoLampHeight()) {
            i = 2;
        } else {
            i = CarBaseConfig.getInstance().isSupportLampHeight() ? 1 : 0;
        }
        LogUtils.d(TAG, "getLightHeightSupport:" + i, false);
        return i;
    }

    protected boolean isSupportCloseMirror() {
        boolean isSupportMirrorFold = CarBaseConfig.getInstance().isSupportMirrorFold();
        MirrorFoldState mirrorFoldState = this.mMirrorVm.getMirrorFoldState();
        LogUtils.d(TAG, "isSupportCloseMirror: hasFeature = " + isSupportMirrorFold + ", state=" + mirrorFoldState.name(), false);
        if (isSupportMirrorFold) {
            return mirrorFoldState == MirrorFoldState.Unfolded || mirrorFoldState == MirrorFoldState.Folded;
        }
        return false;
    }

    protected int getMirrorStatus() {
        if (CarBaseConfig.getInstance().isSupportMirrorFold()) {
            if (this.mVcuVm.getCarSpeed() >= 3.0f) {
                return 2;
            }
            int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorFoldState[this.mMirrorVm.getMirrorFoldState().ordinal()];
            if (i != 1) {
                return i != 2 ? BaseFeatureOption.getInstance().isSupportMirrorFoldUnfoldFuzzySpeech() ? 0 : 5 : BaseFeatureOption.getInstance().isSupportMirrorFoldUnfoldFuzzySpeech() ? 0 : 4;
            }
            return 3;
        }
        return 1;
    }

    protected void onMirrorRearClose() {
        LogUtils.d(TAG, "onMirrorRearClose", false);
        this.mMirrorVm.controlMirror(true);
    }

    protected void onMirrorRearOn() {
        LogUtils.d(TAG, "onMirrorRearOn", false);
        this.mMirrorVm.controlMirror(false);
    }

    protected boolean isSupportControlMirror() {
        MirrorFoldState mirrorFoldState = this.mMirrorVm.getMirrorFoldState();
        LogUtils.d(TAG, "isSupportControlMirror: state = " + mirrorFoldState, false);
        return mirrorFoldState != MirrorFoldState.Middle;
    }

    protected void onMirrorRearSet() {
        LogUtils.d(TAG, "onMirrorRearSet", false);
        ControlPanelManager.getInstance().show(GlobalConstant.ACTION.ACTION_SHOW_MIRROR_CONTROL_PANEL, 2008);
    }

    protected void onMistLightOff() {
        LogUtils.d(TAG, "onMistLightOff", false);
        this.mLampVm.setRearFogLamp(false);
    }

    protected void onMistLightOn() {
        LogUtils.d(TAG, "onMistLightOn", false);
        this.mLampVm.setRearFogLamp(true);
    }

    protected void onWindowsVentilateOn() {
        LogUtils.d(TAG, "onWindowsVentilateOn", false);
        this.mWinDoorVm.controlWindowVent();
    }

    protected void onWindowsVentilateOff() {
        LogUtils.d(TAG, "onWindowsVentilateOff", false);
        this.mWinDoorVm.setAllWinPos(100.0f);
    }

    protected int getWindowStatus() {
        boolean isWindowOpened = this.mWinDoorVm.isWindowOpened();
        LogUtils.d(TAG, "getWindowStatus, isWindowOpen: " + isWindowOpened, false);
        return isWindowOpened ? 1 : 0;
    }

    protected void onWindowsControl(int pos, int mode, int percent) {
        LogUtils.d(TAG, "onWindowsControl: pos=" + pos + ", mode=" + mode + ", percent=" + percent, false);
        if (this.mWinDoorVm.isWindowLockActive()) {
            NotificationHelper.getInstance().showToast(R.string.win_lock_is_active);
        } else if (percent < 0 || percent > 100) {
        } else {
            if (mode == 0) {
                percent = 100 - percent;
            }
            switch (pos) {
                case 0:
                    this.mWinDoorVm.setDrvWinMovePos(percent);
                    return;
                case 1:
                    this.mWinDoorVm.setFRWinPos(percent);
                    return;
                case 2:
                    this.mWinDoorVm.setRLWinPos(percent);
                    return;
                case 3:
                    this.mWinDoorVm.setRRWinPos(percent);
                    return;
                case 4:
                    float f = percent;
                    this.mWinDoorVm.setFLWinPos(f);
                    this.mWinDoorVm.setRLWinPos(f);
                    return;
                case 5:
                    float f2 = percent;
                    this.mWinDoorVm.setFRWinPos(f2);
                    this.mWinDoorVm.setRRWinPos(f2);
                    return;
                case 6:
                    this.mWinDoorVm.setAllWinPos(percent);
                    return;
                case 7:
                    float f3 = percent;
                    this.mWinDoorVm.setFLWinPos(f3);
                    this.mWinDoorVm.setFRWinPos(f3);
                    return;
                case 8:
                    float f4 = percent;
                    this.mWinDoorVm.setRLWinPos(f4);
                    this.mWinDoorVm.setRRWinPos(f4);
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesDrivingSport(int source) {
        LogUtils.d(TAG, "onModesDrivingSport", false);
        this.mVcuVm.setDriveModeByUser(DriveMode.Sport, source != 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesDrivingConservation(int source) {
        LogUtils.d(TAG, "onModesDrivingConservation", false);
        if (BaseFeatureOption.getInstance().isSupportEcoDriveMode()) {
            this.mVcuVm.setDriveModeByUser(DriveMode.Eco, source != 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onModesDrivingNormal(int source) {
        LogUtils.d(TAG, "onModesDrivingNormal", false);
        this.mVcuVm.setDriveModeByUser(DriveMode.Normal, source != 1);
    }

    protected void onModesSteeringSoft(int source) {
        LogUtils.d(TAG, "onModesSteeringSoft", false);
        this.mChassisVm.setSteeringEps(1, source != 1);
    }

    protected void onModesSteeringNormal(int source) {
        LogUtils.d(TAG, "onModesSteeringNormal", false);
        this.mChassisVm.setSteeringEps(0, source != 1);
    }

    protected void onModesSteeringSport(int source) {
        LogUtils.d(TAG, "onModesSteeringSport", false);
        this.mChassisVm.setSteeringEps(2, source != 1);
    }

    protected boolean isSupportEnergyRecycle() {
        return this.mVcuVm.isEnergyEnable(false);
    }

    protected int getControlSupportEnergyRecycleReason() {
        if (CarBaseConfig.getInstance().isSupportSnowMode() && this.mVcuVm.getSnowMode()) {
            return 2;
        }
        return (!CarBaseConfig.getInstance().isSupportDriveModeNewArch() && CarBaseConfig.getInstance().isSupportAbsFault() && this.mVcuVm.isAbsFault()) ? 3 : 1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onEnergyRecycleHigh(int source) {
        LogUtils.d(TAG, "onEnergyRecycleHigh", false);
        boolean z = source != 1;
        this.mVcuVm.setEnergyRecycleGrade(EnergyRecycleGrade.High, z);
        if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            this.mVcuVm.setNewDriveArchXPedalMode(false, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onEnergyRecycleLow(int source) {
        LogUtils.d(TAG, "onEnergyRecycleLow", false);
        boolean z = source != 1;
        this.mVcuVm.setEnergyRecycleGrade(EnergyRecycleGrade.Low, z);
        if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            this.mVcuVm.setNewDriveArchXPedalMode(false, z);
        }
    }

    protected void onEnergyRecycleUp() {
        LogUtils.d(TAG, "onEnergyRecycleUp", false);
        this.mVcuVm.setEnergyRecycleGrade(EnergyRecycleGrade.High);
        if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            this.mVcuVm.setNewDriveArchXPedalMode(false);
        }
    }

    protected void onEnergyRecycleDown() {
        LogUtils.d(TAG, "onEnergyRecycleDown", false);
        this.mVcuVm.setEnergyRecycleGrade(EnergyRecycleGrade.Low);
        if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
            this.mVcuVm.setNewDriveArchXPedalMode(false);
        }
    }

    protected int getSupportSeat() {
        if (CarBaseConfig.getInstance().isSupportSeatCtrl() || CarBaseConfig.getInstance().isSupportMsmD()) {
            if (!seatCheckDoorClose() || this.mWinDoorVm.isDoorClosed(0)) {
                if (!checkDriverSeatOccupied() || this.mSvcVm.isDrvSeatOccupied()) {
                    if (this.mVcuVm.getCarSpeed() >= 3.0f) {
                        return 3;
                    }
                    if (this.mSeatVm.isDrvTiltMovingSafe()) {
                        return (!CarBaseConfig.getInstance().isSupportSeatCtrl() || CarBaseConfig.getInstance().isSupportMsmD()) ? 1 : 6;
                    }
                    return 5;
                }
                return 4;
            }
            return 2;
        }
        return 0;
    }

    protected void onSeatMoveUp(String data) {
        int i;
        if (CarBaseConfig.getInstance().isSupportSeatCtrl() && !CarBaseConfig.getInstance().isSupportMsmD()) {
            this.mSeatVm.controlDriverSeatStart(2, 1, 3);
            return;
        }
        int dSeatVerPos = this.mSeatVm.getDSeatVerPos();
        int source = getSource(data);
        String str = TAG;
        LogUtils.d(str, "onSeatMoveUp:" + dSeatVerPos + ", source: " + source, false);
        if (Math.abs(100 - dSeatVerPos) >= 2 || source == 1) {
            ChangeValue changeValue = getChangeValue(data);
            if (changeValue == null) {
                i = dSeatVerPos + 20;
            } else if (changeValue.isScale()) {
                i = dSeatVerPos + changeValue.getValue();
            } else {
                i = changeValue.getValue();
            }
            if (i > 100) {
                i = 100;
            }
            this.mSeatVm.setDSeatVerPos(i);
            if (source == 1) {
                long keepTime = getKeepTime(data);
                LogUtils.d(str, "onSeatMoveUp, keep: " + keepTime, false);
                if (keepTime != 0) {
                    this.mSeatVm.setSeatMenuNotShowKeepTime(true, System.currentTimeMillis() + keepTime);
                    return;
                }
                return;
            }
            return;
        }
        speak(R.string.speech_seat_control_highest);
    }

    protected void onSeatMoveDown(String data) {
        int i;
        if (CarBaseConfig.getInstance().isSupportSeatCtrl() && !CarBaseConfig.getInstance().isSupportMsmD()) {
            this.mSeatVm.controlDriverSeatStart(2, 2, 3);
            return;
        }
        int dSeatVerPos = this.mSeatVm.getDSeatVerPos();
        int source = getSource(data);
        String str = TAG;
        LogUtils.d(str, "onSeatMoveDown:" + dSeatVerPos + ", source: " + source, false);
        if (Math.abs(0 - dSeatVerPos) >= 2 || source == 1) {
            ChangeValue changeValue = getChangeValue(data);
            if (changeValue == null) {
                i = dSeatVerPos - 20;
            } else if (changeValue.isScale()) {
                i = dSeatVerPos - changeValue.getValue();
            } else {
                i = changeValue.getValue();
            }
            if (i < 0) {
                i = 0;
            }
            this.mSeatVm.setDSeatVerPos(i);
            if (source == 1) {
                long keepTime = getKeepTime(data);
                LogUtils.d(str, "onSeatMoveUp, keep: " + keepTime, false);
                if (keepTime != 0) {
                    this.mSeatVm.setSeatMenuNotShowKeepTime(true, System.currentTimeMillis() + keepTime);
                    return;
                }
                return;
            }
            return;
        }
        speak(R.string.speech_seat_control_lowest);
    }

    protected void onSeatMoveHighest() {
        int dSeatVerPos = this.mSeatVm.getDSeatVerPos();
        LogUtils.d(TAG, "onSeatMoveHighest:" + dSeatVerPos, false);
        if (Math.abs(100 - dSeatVerPos) >= 2) {
            this.mSeatVm.setDSeatVerPos(100);
        } else {
            speak(R.string.speech_seat_control_highest);
        }
    }

    protected void onSeatMoveLowest() {
        int dSeatVerPos = this.mSeatVm.getDSeatVerPos();
        LogUtils.d(TAG, "onSeatMoveLowest:" + dSeatVerPos, false);
        if (Math.abs(0 - dSeatVerPos) >= 2) {
            this.mSeatVm.setDSeatVerPos(0);
        } else {
            speak(R.string.speech_seat_control_lowest);
        }
    }

    protected void onSeatMoveBack(String data) {
        int i;
        if (CarBaseConfig.getInstance().isSupportSeatCtrl() && !CarBaseConfig.getInstance().isSupportMsmD()) {
            this.mSeatVm.controlDriverSeatStart(1, 2, 3);
            return;
        }
        int dSeatHorzPos = this.mSeatVm.getDSeatHorzPos();
        int source = getSource(data);
        String str = TAG;
        LogUtils.d(str, "onSeatMoveBack:" + dSeatHorzPos + ", source: " + source, false);
        if (Math.abs(0 - dSeatHorzPos) >= 2 || source == 1) {
            ChangeValue changeValue = getChangeValue(data);
            if (changeValue == null) {
                i = dSeatHorzPos - 16;
            } else if (changeValue.isScale()) {
                i = dSeatHorzPos - changeValue.getValue();
            } else {
                i = changeValue.getValue();
            }
            if (i < 0) {
                i = 0;
            }
            this.mSeatVm.setDSeatHorzPos(i);
            if (source == 1) {
                long keepTime = getKeepTime(data);
                LogUtils.d(str, "onSeatMoveBack, keep: " + keepTime, false);
                if (keepTime != 0) {
                    this.mSeatVm.setSeatMenuNotShowKeepTime(true, System.currentTimeMillis() + keepTime);
                    return;
                }
                return;
            }
            return;
        }
        speak(R.string.speech_seat_control_rear);
    }

    protected void onSeatMoveForward(String data) {
        int i;
        if (CarBaseConfig.getInstance().isSupportSeatCtrl() && !CarBaseConfig.getInstance().isSupportMsmD()) {
            this.mSeatVm.controlDriverSeatStart(1, 1, 3);
            return;
        }
        int dSeatHorzPos = this.mSeatVm.getDSeatHorzPos();
        int source = getSource(data);
        String str = TAG;
        LogUtils.d(str, "onSeatMoveForward:" + dSeatHorzPos + ", source: " + source, false);
        if (Math.abs(90 - dSeatHorzPos) >= 2 || source == 1) {
            ChangeValue changeValue = getChangeValue(data);
            if (changeValue == null) {
                i = dSeatHorzPos + 16;
            } else if (changeValue.isScale()) {
                i = dSeatHorzPos + changeValue.getValue();
            } else {
                i = changeValue.getValue();
            }
            if (i > 90) {
                i = 90;
            }
            this.mSeatVm.setDSeatHorzPos(i);
            if (source == 1) {
                long keepTime = getKeepTime(data);
                LogUtils.d(str, "onSeatMoveForward, keep: " + keepTime, false);
                if (keepTime != 0) {
                    this.mSeatVm.setSeatMenuNotShowKeepTime(true, System.currentTimeMillis() + keepTime);
                    return;
                }
                return;
            }
            return;
        }
        speak(R.string.speech_seat_control_foremost);
    }

    protected void onSeatMoveRear() {
        int dSeatHorzPos = this.mSeatVm.getDSeatHorzPos();
        LogUtils.d(TAG, "onSeatMoveRear:" + dSeatHorzPos, false);
        if (Math.abs(0 - dSeatHorzPos) >= 2) {
            this.mSeatVm.setDSeatHorzPos(0);
        } else {
            speak(R.string.speech_seat_control_rear);
        }
    }

    protected void onSeatMoveForemost() {
        int dSeatHorzPos = this.mSeatVm.getDSeatHorzPos();
        LogUtils.d(TAG, "onSeatMoveForemost:" + dSeatHorzPos, false);
        if (Math.abs(90 - dSeatHorzPos) >= 2) {
            this.mSeatVm.setDSeatHorzPos(90);
        } else {
            speak(R.string.speech_seat_control_foremost);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onSeatBackrestBack(java.lang.String r10) {
        /*
            r9 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r0 = r0.isSupportSeatCtrl()
            if (r0 == 0) goto L1d
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r0 = r0.isSupportMsmD()
            if (r0 != 0) goto L1d
            com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel r10 = r9.mSeatVm
            r0 = 2
            r1 = 3
            r10.controlDriverSeatStart(r1, r0, r1)
            goto Ld6
        L1d:
            com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel r0 = r9.mSeatVm
            int r0 = r0.getDSeatTiltPos()
            int r1 = r9.getSource(r10)
            java.lang.String r2 = com.xiaopeng.carcontrol.speech.CarControlSpeechModel.TAG
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "onSeatBackrestBack:"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r4 = ", source: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r1)
            java.lang.String r3 = r3.toString()
            r4 = 0
            com.xiaopeng.carcontrol.util.LogUtils.d(r2, r3, r4)
            com.xiaopeng.carcontrol.config.CarBaseConfig r3 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r3 = r3.isSupportTiltPosReversed()
            r5 = 1
            r6 = 85
            r7 = 20
            if (r3 == 0) goto L5c
            if (r0 <= r7) goto L61
            goto L68
        L5c:
            if (r0 < r6) goto L68
            if (r1 != r5) goto L61
            goto L68
        L61:
            int r10 = com.xiaopeng.carcontrolmodule.R.string.speech_seat_control_tilt
            r9.speak(r10)
            goto Ld6
        L68:
            com.xiaopeng.speech.protocol.bean.ChangeValue r3 = r9.getChangeValue(r10)
            if (r3 == 0) goto L8e
            boolean r8 = r3.isScale()
            if (r8 == 0) goto L89
            com.xiaopeng.carcontrol.config.CarBaseConfig r8 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r8 = r8.isSupportTiltPosReversed()
            if (r8 == 0) goto L84
            int r3 = r3.getValue()
            int r3 = -r3
            goto L9c
        L84:
            int r3 = r3.getValue()
            goto L9c
        L89:
            int r0 = r3.getValue()
            goto L9d
        L8e:
            com.xiaopeng.carcontrol.config.CarBaseConfig r3 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r3 = r3.isSupportTiltPosReversed()
            if (r3 == 0) goto L9b
            r3 = -20
            goto L9c
        L9b:
            r3 = r7
        L9c:
            int r0 = r0 + r3
        L9d:
            if (r0 <= r6) goto La0
            goto La1
        La0:
            r6 = r0
        La1:
            if (r6 >= r7) goto La4
            goto La5
        La4:
            r7 = r6
        La5:
            com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel r0 = r9.mSeatVm
            r0.setDSeatTiltPos(r7)
            if (r1 != r5) goto Ld6
            long r0 = r9.getKeepTime(r10)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r3 = "onSeatBackrestBack, keep: "
            java.lang.StringBuilder r10 = r10.append(r3)
            java.lang.StringBuilder r10 = r10.append(r0)
            java.lang.String r10 = r10.toString()
            com.xiaopeng.carcontrol.util.LogUtils.d(r2, r10, r4)
            r2 = 0
            int r10 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r10 == 0) goto Ld6
            com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel r10 = r9.mSeatVm
            long r2 = java.lang.System.currentTimeMillis()
            long r2 = r2 + r0
            r10.setSeatMenuNotShowKeepTime(r5, r2)
        Ld6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.speech.CarControlSpeechModel.onSeatBackrestBack(java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onSeatBackrestForward(java.lang.String r10) {
        /*
            r9 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r0 = r0.isSupportSeatCtrl()
            r1 = 1
            if (r0 == 0) goto L1d
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r0 = r0.isSupportMsmD()
            if (r0 != 0) goto L1d
            com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel r10 = r9.mSeatVm
            r0 = 3
            r10.controlDriverSeatStart(r0, r1, r0)
            goto Ld5
        L1d:
            com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel r0 = r9.mSeatVm
            int r0 = r0.getDSeatTiltPos()
            int r2 = r9.getSource(r10)
            java.lang.String r3 = com.xiaopeng.carcontrol.speech.CarControlSpeechModel.TAG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "onSeatBackrestForward:"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r5 = ", source: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r2)
            java.lang.String r4 = r4.toString()
            r5 = 0
            com.xiaopeng.carcontrol.util.LogUtils.d(r3, r4, r5)
            com.xiaopeng.carcontrol.config.CarBaseConfig r4 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r4 = r4.isSupportTiltPosReversed()
            r6 = 85
            r7 = 20
            if (r4 == 0) goto L5b
            if (r0 >= r6) goto L60
            goto L67
        L5b:
            if (r0 > r7) goto L67
            if (r2 != r1) goto L60
            goto L67
        L60:
            int r10 = com.xiaopeng.carcontrolmodule.R.string.speech_seat_control_tilt
            r9.speak(r10)
            goto Ld5
        L67:
            com.xiaopeng.speech.protocol.bean.ChangeValue r4 = r9.getChangeValue(r10)
            if (r4 == 0) goto L8d
            boolean r8 = r4.isScale()
            if (r8 == 0) goto L88
            com.xiaopeng.carcontrol.config.CarBaseConfig r8 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r8 = r8.isSupportTiltPosReversed()
            if (r8 == 0) goto L82
            int r4 = r4.getValue()
            goto L9b
        L82:
            int r4 = r4.getValue()
            int r4 = -r4
            goto L9b
        L88:
            int r0 = r4.getValue()
            goto L9c
        L8d:
            com.xiaopeng.carcontrol.config.CarBaseConfig r4 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r4 = r4.isSupportTiltPosReversed()
            if (r4 == 0) goto L99
            r4 = r7
            goto L9b
        L99:
            r4 = -20
        L9b:
            int r0 = r0 + r4
        L9c:
            if (r0 <= r6) goto L9f
            goto La0
        L9f:
            r6 = r0
        La0:
            if (r6 >= r7) goto La3
            goto La4
        La3:
            r7 = r6
        La4:
            com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel r0 = r9.mSeatVm
            r0.setDSeatTiltPos(r7)
            if (r2 != r1) goto Ld5
            long r6 = r9.getKeepTime(r10)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r0 = "onSeatBackrestForward, keep: "
            java.lang.StringBuilder r10 = r10.append(r0)
            java.lang.StringBuilder r10 = r10.append(r6)
            java.lang.String r10 = r10.toString()
            com.xiaopeng.carcontrol.util.LogUtils.d(r3, r10, r5)
            r2 = 0
            int r10 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r10 == 0) goto Ld5
            com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel r10 = r9.mSeatVm
            long r2 = java.lang.System.currentTimeMillis()
            long r2 = r2 + r6
            r10.setSeatMenuNotShowKeepTime(r1, r2)
        Ld5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.speech.CarControlSpeechModel.onSeatBackrestForward(java.lang.String):void");
    }

    protected void onSeatBackrestRear() {
        int dSeatTiltPos = this.mSeatVm.getDSeatTiltPos();
        LogUtils.d(TAG, "onSeatBackrestRear:" + dSeatTiltPos, false);
        int i = CarBaseConfig.getInstance().isSupportTiltPosReversed() ? 20 : 85;
        if (Math.abs(i - dSeatTiltPos) >= 2) {
            this.mSeatVm.setDSeatTiltPos(i);
        } else {
            speak(R.string.speech_seat_control_tilt);
        }
    }

    protected void onSeatBackrestForemost() {
        int dSeatTiltPos = this.mSeatVm.getDSeatTiltPos();
        LogUtils.d(TAG, "onSeatBackrestForemost:" + dSeatTiltPos, false);
        int i = CarBaseConfig.getInstance().isSupportTiltPosReversed() ? 85 : 20;
        if (Math.abs(i - dSeatTiltPos) >= 2) {
            this.mSeatVm.setDSeatTiltPos(i);
        } else {
            speak(R.string.speech_seat_control_tilt);
        }
    }

    protected void onLegUp() {
        int dSeatLegPos = this.mSeatVm.getDSeatLegPos();
        LogUtils.d(TAG, "onLegUp: pos = " + dSeatLegPos);
        if (100 - dSeatLegPos >= 2) {
            this.mSeatVm.setDSeatLegMove(1, 1);
        } else {
            speak(R.string.speech_cushion_control_highest);
        }
    }

    protected void onLegDown() {
        int dSeatLegPos = this.mSeatVm.getDSeatLegPos();
        LogUtils.d(TAG, "onLegDown: pos = " + dSeatLegPos);
        if (dSeatLegPos >= 2) {
            this.mSeatVm.setDSeatLegMove(1, 2);
        } else {
            speak(R.string.speech_cushion_control_lowest);
        }
    }

    protected void onLegHighest() {
        int dSeatLegPos = this.mSeatVm.getDSeatLegPos();
        LogUtils.d(TAG, "onLegHighest: pos = " + dSeatLegPos, false);
        if (100 - dSeatLegPos >= 2) {
            this.mSeatVm.setDSeatLegPos(100);
        } else {
            speak(R.string.speech_cushion_control_highest);
        }
    }

    protected void onLegLowest() {
        int dSeatLegPos = this.mSeatVm.getDSeatLegPos();
        LogUtils.d(TAG, "onLegLowest: pos = " + dSeatLegPos, false);
        if (dSeatLegPos >= 2) {
            this.mSeatVm.setDSeatLegPos(0);
        } else {
            speak(R.string.speech_cushion_control_lowest);
        }
    }

    protected int getLegHeight() {
        int dSeatLegPos = this.mSeatVm.getDSeatLegPos();
        if (dSeatLegPos > 100) {
            dSeatLegPos = 100;
        } else if (dSeatLegPos < 0) {
            dSeatLegPos = 0;
        }
        LogUtils.d(TAG, "getLegHeight: " + dSeatLegPos, false);
        return dSeatLegPos;
    }

    protected void onSeatRestore() {
        LogUtils.d(TAG, "onSeatRestore", false);
        this.mSeatVm.restoreDrvSeatPos();
    }

    protected boolean isSupportAtmosphere() {
        LogUtils.d(TAG, "isSupportAtmosphere", false);
        return CarBaseConfig.getInstance().isSupportAtl();
    }

    protected void onCheckUserBook(UserBookValue userBookValue) {
        LogUtils.d(TAG, "onCheckUserBook not support");
    }

    protected void onOpenUserBook() {
        LogUtils.d(TAG, "onOpenUserBook not support");
    }

    protected void onCloseUserBook() {
        LogUtils.d(TAG, "onCloseUserBook not support");
    }

    protected void onLightAtmosphereOn() {
        LogUtils.d(TAG, "onLightAtmosphereOn", false);
        this.mAtlVm.setAtlSwitch(true);
    }

    protected void onLightAtmosphereOff() {
        LogUtils.d(TAG, "onLightAtmosphereOff", false);
        this.mAtlVm.setAtlSwitch(false);
    }

    protected void onAtmosphereSpeakerOn() {
        LogUtils.d(TAG, "onAtmosphereSpeakerOn", false);
        this.mAtlVm.setAtlSpeakerSwEnabled(true);
    }

    protected void onAtmosphereSpeakerOff() {
        LogUtils.d(TAG, "onAtmosphereSpeakerOff", false);
        this.mAtlVm.setAtlSpeakerSwEnabled(false);
    }

    protected void onWiperSpeedUp() {
        WiperInterval wiperIntervalValue = this.mCarBodyVm.getWiperIntervalValue();
        LogUtils.d(TAG, "onWiperSpeedUp: current level = " + wiperIntervalValue, false);
        if (wiperIntervalValue == null || wiperIntervalValue.ordinal() >= WiperInterval.values().length - 1) {
            return;
        }
        this.mCarBodyVm.setWiperInterval(WiperInterval.values()[wiperIntervalValue.ordinal() + 1]);
    }

    protected void onWiperSpeedDown() {
        WiperInterval wiperIntervalValue = this.mCarBodyVm.getWiperIntervalValue();
        LogUtils.d(TAG, "onWiperSpeedDown: current level = " + wiperIntervalValue, false);
        if (wiperIntervalValue == null || wiperIntervalValue.ordinal() <= 0) {
            return;
        }
        this.mCarBodyVm.setWiperInterval(WiperInterval.values()[wiperIntervalValue.ordinal() - 1]);
    }

    protected void onWiperSlow() {
        LogUtils.d(TAG, "onWiperSlow", false);
        this.mCarBodyVm.setWiperInterval(WiperInterval.Slow);
    }

    protected void onWiperHigh() {
        LogUtils.d(TAG, "onWiperHigh", false);
        this.mCarBodyVm.setWiperInterval(WiperInterval.Fast);
    }

    protected void onWiperMedium() {
        LogUtils.d(TAG, "onWiperMedium", false);
        this.mCarBodyVm.setWiperInterval(WiperInterval.Medium);
    }

    protected void onWiperSuperhigh() {
        LogUtils.d(TAG, "onWiperSuperhigh", false);
        this.mCarBodyVm.setWiperInterval(WiperInterval.Ultra);
    }

    protected boolean isSupportUnlockTrunk() {
        boolean z = !CarBaseConfig.getInstance().isSupportElcTrunk() && this.mVcuVm.getGearLevel() == 4;
        LogUtils.d(TAG, "isSupportUnlockTrunk: " + z, false);
        return z;
    }

    protected void onTrunkUnlock() {
        LogUtils.d(TAG, "onTrunkUnlock", false);
        this.mWinDoorVm.openRearTrunk();
    }

    protected int getExtraTrunkStatus() {
        boolean hasFeature = CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_ELC_TRUNK);
        LogUtils.d(TAG, "getExtraTrunkStatus: " + (hasFeature ? 1 : 0), false);
        return hasFeature ? 1 : 0;
    }

    protected int getTrunkStatus() {
        int i = !this.mWinDoorVm.isTrunkClosed();
        LogUtils.d(TAG, "getTrunkStatus: " + i, false);
        return i;
    }

    protected int getDoorKeyValue() {
        return this.mMeterVm.getDoorKeyForCustomer();
    }

    protected boolean isSupportControlChargePort(int chargePort, int mode) {
        LogUtils.d(TAG, "isSupportControlChargePort: chargePort=" + chargePort + ", mode=" + mode, false);
        return this.mCarBodyVm.isChargePortEnable(chargePort == 0, false);
    }

    protected int getStatusChargePortControl(int chargePort, int controlType) {
        boolean z;
        boolean z2 = false;
        LogUtils.d(TAG, "getStatusChargePortControl: chargePort=" + chargePort + ", controlType=" + controlType, false);
        if (controlType != 0 || ((!BaseFeatureOption.getInstance().isSupportChargePortPGearConstrain() || this.mVcuVm.getGearLevel() == 4) && this.mVcuVm.getCarSpeed() < 3.0f)) {
            int chargePortUnlock = this.mCarBodyVm.getChargePortUnlock(chargePort == 0 ? 1 : 2);
            if (!this.mCarBodyVm.isChargeGunUnlinked(z)) {
                return 3;
            }
            if (BaseFeatureOption.getInstance().isSupportChargePortOnOffConstraint()) {
                if (chargePortUnlock == 0 && controlType == 0) {
                    return 4;
                }
                if (chargePortUnlock == 2 && controlType == 1) {
                    return 5;
                }
            }
            if ((chargePortUnlock == 2 && controlType == 0) || (chargePortUnlock == 0 && controlType == 1)) {
                z2 = true;
            }
            return z2 ? 1 : 3;
        }
        return 2;
    }

    protected void onChargePortControl(int chargePort, int mode) {
        LogUtils.d(TAG, "onChargePortControl: chargePort=" + chargePort + ", mode=" + mode, false);
        this.mCarBodyVm.setChargePortUnlock(chargePort == 0 ? 1 : 2, mode == 0);
    }

    protected boolean isTirePressureNormal() {
        return this.mChassisVm.isTpmsPressureNormal();
    }

    protected void onTirePressureShow() {
        LogUtils.d(TAG, "onTirePressureShow", false);
        Intent intent = new Intent(GlobalConstant.ACTION.ACTION_SHOW_CAR_CONTROL);
        intent.putExtra(GlobalConstant.EXTRA.MAIN_CATEGORY_INDEX, 4);
        intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
        App.getInstance().startActivity(intent);
    }

    private void speak(int stringId) {
        LogUtils.d(TAG, App.getInstance().getResources().getString(stringId));
    }

    protected void onSeatAdjust(SeatValue seatValue) {
        if (seatValue != null) {
            int i = CarBaseConfig.getInstance().isSupportTiltPosReversed() ? 100 - seatValue.getzValue() : seatValue.getzValue();
            LogUtils.d(TAG, "Enter meditation mode, Tilt need move to = " + i, false);
            this.mSeatVm.enterMeditationState(i);
        }
    }

    protected int getSupportOpenTrunk() {
        if (CarBaseConfig.getInstance().isSupportElcTrunk()) {
            if (this.mVcuVm.getCarSpeed() >= 3.0f) {
                return 2;
            }
            if (CarBaseConfig.getInstance().isSupportTrunkOverHeatingProtected()) {
                int trunkWorkStatus = this.mWinDoorVm.getTrunkWorkStatus();
                if (trunkWorkStatus == 1) {
                    return 3;
                }
                if (trunkWorkStatus == 2) {
                    return 4;
                }
            }
            return 1;
        }
        return 0;
    }

    protected void onTrunkOpen() {
        if (!CarBaseConfig.getInstance().isSupportElcTrunk() || this.mVcuVm.getCarSpeed() >= 3.0f) {
            return;
        }
        this.mWinDoorVm.controlRearTrunk(TrunkType.Open);
    }

    protected int getSupportCloseTrunk() {
        if (CarBaseConfig.getInstance().isSupportElcTrunk()) {
            if (CarBaseConfig.getInstance().isSupportTrunkOverHeatingProtected()) {
                int trunkWorkStatus = this.mWinDoorVm.getTrunkWorkStatus();
                if (trunkWorkStatus == 1) {
                    return 3;
                }
                if (trunkWorkStatus == 2) {
                    return 4;
                }
            }
            return 1;
        }
        return 0;
    }

    protected void onTrunkClose() {
        if (CarBaseConfig.getInstance().isSupportElcTrunk()) {
            this.mWinDoorVm.controlRearTrunk(TrunkType.Close);
        }
    }

    protected void onEnergyRecycleMedium(int source) {
        LogUtils.d(TAG, "onEnergyRecycleMedium ", false);
        if (CarBaseConfig.getInstance().isSupportEnergyRecycleMediumLevel()) {
            boolean z = source != 1;
            this.mVcuVm.setEnergyRecycleGrade(EnergyRecycleGrade.Middle, z);
            if (CarBaseConfig.getInstance().isSupportDriveModeNewArch()) {
                this.mVcuVm.setNewDriveArchXPedalMode(false, z);
            }
        }
    }

    protected void onControlLightResume() {
        LogUtils.d(TAG, "onControlLightResume not support");
    }

    protected void onControlSeatResume() {
        LogUtils.d(TAG, "Exit meditation mode", false);
        this.mSeatVm.exitMeditationState();
    }

    protected void onLightAtmosphereColor(CommandValue commandValue, int source) {
        if (commandValue != null) {
            boolean z = source != 1;
            int color = commandValue.getColor();
            LogUtils.d(TAG, "onLightAtmosphereColor color:" + color + "source: " + source, false);
            if (color < 1 || color > 20) {
                return;
            }
            setAtlEnable();
            if (this.mAtlVm.getAtlThemeColorMode()) {
                this.mAtlVm.setAtlThemeColorMode(false);
            }
            this.mAtlVm.setAtlSingleColor(commandValue.getColor(), z);
            return;
        }
        LogUtils.d(TAG, "onLightAtmosphereColor is null", false);
    }

    protected void onLightAtmosphereDualColor(CommandValue commandValue, int source) {
        if (commandValue != null) {
            boolean z = source != 1;
            int color = commandValue.getColor();
            int[] convertDoubleColor = this.mAtlVm.convertDoubleColor(color);
            LogUtils.d(TAG, "onLightAtmosphereDualColor color:" + color + "source: " + source, false);
            if (color < 0 || color > 6) {
                return;
            }
            setAtlEnable();
            if (!this.mAtlVm.getAtlThemeColorMode()) {
                this.mAtlVm.setAtlThemeColorMode(true);
            }
            this.mAtlVm.setAtlDualColor(convertDoubleColor[0], convertDoubleColor[1], z);
            return;
        }
        LogUtils.d(TAG, "onLightAtmosphereColor is null", false);
    }

    protected void onLightAtmosphereBrightnessSet(CommandValue commandValue) {
        if (commandValue != null) {
            LogUtils.d(TAG, "onLightAtmosphereBrightnessSet umber:" + commandValue.getNumber() + ", percent:" + commandValue.getPercent(), false);
            setAtlEnable();
            int number = commandValue.getNumber();
            int percent = commandValue.getPercent();
            if (number > 0) {
                this.mAtlVm.setAtlBrightnessValue(number);
                return;
            } else {
                this.mAtlVm.setAtlBrightnessValue(((percent * 90) / 100) + 10);
                return;
            }
        }
        LogUtils.d(TAG, "onLightAtmosphereBrightnessSet is null", false);
    }

    protected void onLightAtmosphereBrightnessUp() {
        LogUtils.d(TAG, "onLightAtmosphereBrightnessUp", false);
        setAtlEnable();
        int atlBrightnessValue = this.mAtlVm.getAtlBrightnessValue() + 10;
        if (atlBrightnessValue > 100) {
            atlBrightnessValue = 100;
        }
        this.mAtlVm.setAtlBrightnessValue(atlBrightnessValue);
    }

    protected void onLightAtmosphereBrightnessDown() {
        LogUtils.d(TAG, "onLightAtmosphereBrightnessDown", false);
        setAtlEnable();
        int atlBrightnessValue = this.mAtlVm.getAtlBrightnessValue() - 10;
        if (atlBrightnessValue < 10) {
            atlBrightnessValue = 10;
        }
        this.mAtlVm.setAtlBrightnessValue(atlBrightnessValue);
    }

    protected void onLightAtmosphereBrightnessMax() {
        LogUtils.d(TAG, "onLightAtmosphereBrightnessMax", false);
        setAtlEnable();
        this.mAtlVm.setAtlBrightnessValue(100);
    }

    protected void onLightAtmosphereBrightnessMin() {
        LogUtils.d(TAG, "onLightAtmosphereBrightnessMin", false);
        setAtlEnable();
        this.mAtlVm.setAtlBrightnessValue(10);
    }

    protected void onSuspensionAutoOn(int source) {
        LogUtils.i(TAG, "onSuspensionAutoOn", false);
        this.mChassisVm.setAsLocationSw(true, source != 1);
    }

    protected void onSuspensionAutoOff(int source) {
        LogUtils.i(TAG, "onSuspensionAutoOff", false);
        this.mChassisVm.setAsLocationSw(false, source != 1);
    }

    protected void onMassageOn(int pilot, int mode) {
        LogUtils.i(TAG, "onMassageOn, pilot: " + pilot + ", mode: " + mode, false);
        this.mSeatVm.startMassager(pilot - 1, MASSAGE_MODES[mode - 1]);
    }

    protected void onMassageOff(int pilot) {
        LogUtils.i(TAG, "onMassageOff, pilot: " + pilot, false);
        this.mSeatVm.stopMassager(pilot - 1);
    }

    protected void onMassageStrengthSet(int pilot, int target) {
        LogUtils.i(TAG, "onMassageStrengthSet, pilot: " + pilot + ", target: " + target, false);
        int i = pilot - 1;
        if (!this.mSeatVm.isMassagerRunning(i)) {
            onMassageOn(pilot, 1);
        }
        this.mSeatVm.setMassagerIntensity(i, target);
    }

    protected void onSwingOn(int pilot, int mode) {
        LogUtils.i(TAG, "onSwingOn, pilot: " + pilot + ", mode: " + mode, false);
        this.mSeatVm.startRhythm(pilot - 1, mode - 1, true);
    }

    protected void onSwingOff(int pilot) {
        LogUtils.i(TAG, "onSwingOff, pilot: " + pilot, false);
        this.mSeatVm.stopRhythm(pilot - 1);
    }

    protected void onSwingStrengthSet(int pilot, int target) {
        LogUtils.i(TAG, "onSwingStrengthSet, pilot: " + pilot + ", target: " + target, false);
        if (!this.mSeatVm.isRhythmRunning(pilot)) {
            onSwingOn(pilot, 1);
        }
        this.mSeatVm.setRhythmIntensity(pilot - 1, target);
    }

    protected void onBackWelcomeModeSet(int target) {
        LogUtils.i(TAG, "onBackWelcomeModeSet, target:" + target, false);
        this.mSeatVm.setRearSeatWelcomeMode(target == 1);
    }

    protected void onSuspensionWelcomeModeSet(int target) {
        LogUtils.i(TAG, "onSuspensionWelcomeModeSet, target:" + target, false);
        this.mChassisVm.setAirSuspensionWelcome(target == 1);
    }

    protected void onLightPositionModeSet(int target) {
        LogUtils.i(TAG, "onLightPositionModeSet, target:" + target, false);
        this.mLampVm.setParkLampIncludeFmB(target == 0);
    }

    protected void onLightAtmosphereColorSwitch(int target) {
        LogUtils.i(TAG, "onLightAtmosphereColorSwitch, target:" + target, false);
        this.mAtlVm.setAtlThemeColorMode(target == 1);
    }

    protected void onLightAtmosphereModeSet(int target) {
        LogUtils.i(TAG, "onLightAtmosphereModeSet, target:" + target, false);
        if (target == 1) {
            this.mAtlVm.setAtlEffect(AtlEffect.None);
        } else if (target == 2) {
            this.mAtlVm.setAtlEffect(AtlEffect.Breathing);
        } else if (target == 3) {
            this.mAtlVm.setAtlEffect(AtlEffect.Speed);
        } else if (target != 4) {
        } else {
            this.mAtlVm.setAtlEffect(AtlEffect.Music);
        }
    }

    protected void onLightTopBrightnessSet(int target, int source) {
        LogUtils.i(TAG, "onLightTopBrightnessSet, target:" + target, false);
        boolean z = source != 1;
        int i = target != 0 ? target != 1 ? target != 2 ? -1 : 5 : 3 : 1;
        if (i != -1) {
            this.mLampVm.setDomeLightBrightness(i, z);
        }
    }

    protected void onLightTopSet(int pilot, int target) {
        int i;
        LogUtils.i(TAG, "onLightTopSet, pilot: " + pilot + ", target:" + target, false);
        switch (pilot) {
            case 0:
                i = 4;
                break;
            case 1:
                i = 0;
                break;
            case 2:
                i = 1;
                break;
            case 3:
                i = 2;
                break;
            case 4:
                i = 3;
                break;
            case 5:
                i = 5;
                break;
            case 6:
                i = 6;
                break;
            default:
                i = -1;
                break;
        }
        if (i == -1) {
            return;
        }
        this.mLampVm.setDomeLightSw(i, target == 1);
    }

    protected void onSuspensionRigiditySet(int mode) {
        if (mode == 0) {
            this.mChassisVm.setCdcMode(2);
        } else if (mode == 1) {
            this.mChassisVm.setCdcMode(1);
        } else if (mode != 2) {
        } else {
            this.mChassisVm.setCdcMode(3);
        }
    }

    protected void onSeatLumberControl(int pilot, int mode) {
        LogUtils.d(TAG, "onSeatLumberControl pilot: " + pilot + ", mode: " + mode, false);
        if (pilot == 1 || pilot == 2) {
            if (mode == 0 || mode == 1) {
                if (pilot == 1) {
                    this.mSeatVm.setDrvLumbControlSw(mode == 1);
                } else {
                    this.mSeatVm.setPsnLumbControlSw(mode == 1);
                }
                ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.speech.-$$Lambda$CarControlSpeechModel$nYIOOGNvdngxplXAqgN6_lnrVRs
                    @Override // java.lang.Runnable
                    public final void run() {
                        CarControlSpeechModel.this.lambda$onSeatLumberControl$0$CarControlSpeechModel();
                    }
                }, 200L);
            }
        }
    }

    public /* synthetic */ void lambda$onSeatLumberControl$0$CarControlSpeechModel() {
        this.mSeatVm.handleSeatLumbControlViewShow(true);
    }

    protected int getAtmosphereBrightnessStatus() {
        LogUtils.d(TAG, "getAtmosphereBrightnessStatus", false);
        return this.mAtlVm.getAtlBrightnessValue();
    }

    protected int getAtmosphereColorStatus() {
        LogUtils.d(TAG, "getAtmosphereColorStatus", false);
        if (!this.mAtlVm.isAtlEnabled() || this.mAtlVm.getAtlThemeColorMode()) {
            return 0;
        }
        return this.mAtlVm.getAtlSingleColor();
    }

    private void setAtlEnable() {
        if (this.mAtlVm.isAtlEnabled()) {
            return;
        }
        this.mAtlVm.setAtlSwitch(true);
    }

    protected int isSteeringModeAdjustable() {
        if (this.mVcuVm.getCarSpeed() >= CarBaseConfig.getInstance().getEpsSpdThreshold() || Math.abs(this.mChassisVm.getTorsionBarTorque()) > CarBaseConfig.getInstance().getEpsTorsionBarThreshold()) {
            return 2;
        }
        if (CarBaseConfig.getInstance().isSupportNgp() && this.mScuVm.isNgpRunning()) {
            return 0;
        }
        return (CarBaseConfig.getInstance().isSupportLcc() && this.mScuVm.getLccWorkSt() == ScuResponse.ON) ? 0 : 1;
    }

    protected int getWiperInterval() {
        WiperInterval wiperIntervalValue = this.mCarBodyVm.getWiperIntervalValue();
        LogUtils.i(TAG, "getWiperInterval return " + wiperIntervalValue);
        return wiperIntervalValue.toSensitivityBcmCmd();
    }

    protected int getGuiPageOpenState(String pageUrl) {
        String str = TAG;
        LogUtils.i(str, "getGuiPageOpenState 1 : " + pageUrl, false);
        if (BaseFeatureOption.getInstance().isSupportVuiOpenCarControl()) {
            if (SharedPreferenceUtil.isCarControlMoveBack()) {
                Intent intent = new Intent(GlobalConstant.ACTION.ACTION_SHOW_CAR_CONTROL);
                intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
                App.getInstance().startActivity(intent);
                return 0;
            }
            return 2;
        } else if (!BaseFeatureOption.getInstance().isSupportGuiPageOpen()) {
            LogUtils.i(str, "getGuiPageOpenState not support", false);
            return 1;
        } else {
            IElementDirect elementDirect = ElementDirectManager.getElementDirect();
            if (elementDirect != null) {
                String convertUrl = elementDirect.convertUrl(pageUrl);
                if (elementDirect.checkSupport(convertUrl)) {
                    LogUtils.i(str, "getGuiPageOpenState 0 : " + convertUrl, false);
                    elementDirect.showPageDirect(App.getInstance(), convertUrl);
                    return 0;
                }
            }
            return 1;
        }
    }

    protected int getNapa2DPageOpenState(String pageUrl) {
        LogUtils.i(TAG, "getNapa2DPageOpenState : " + pageUrl, false);
        IElementDirect elementDirect = ElementDirectManager.getElementDirect();
        if (elementDirect != null) {
            return !elementDirect.isPageOpen(Uri.parse(elementDirect.convertUrl(pageUrl)));
        }
        return 1;
    }

    protected int closeNapa2DPageUI(String pageUrl) {
        LogUtils.i(TAG, "closeNapa2DPageUI  : " + pageUrl, false);
        IElementDirect elementDirect = ElementDirectManager.getElementDirect();
        if (elementDirect != null) {
            return !elementDirect.closePageDirect(App.getInstance(), elementDirect.convertUrl(pageUrl));
        }
        return 1;
    }

    protected int getUnityPageOpenState(String pageUrl) {
        IElementDirect elementDirect;
        String str = TAG;
        LogUtils.i(str, "getUnityPageOpenState : " + pageUrl, false);
        if (!BaseFeatureOption.getInstance().isSupportNapa() || (elementDirect = ElementDirectManager.getElementDirect()) == null) {
            return 1;
        }
        String convertUrl = elementDirect.convertUrl(pageUrl);
        if (elementDirect.checkSupport(convertUrl)) {
            LogUtils.i(str, "getUnityPageOpenState 0 : " + convertUrl, false);
            return 0;
        }
        return 1;
    }

    protected void onPsnSeatMoveForward(String data) {
        int value;
        ChangeValue changeValue = getChangeValue(data);
        if (changeValue != null) {
            int pSeatHorzPos = this.mSeatVm.getPSeatHorzPos();
            int source = getSource(data);
            String str = TAG;
            LogUtils.d(str, "onPsnSeatMoveForward:" + pSeatHorzPos + ", source: " + source, false);
            if (Math.abs(90 - pSeatHorzPos) >= 2 || source == 1) {
                if (changeValue.isScale()) {
                    value = pSeatHorzPos + changeValue.getValue();
                } else {
                    value = changeValue.getValue();
                }
                if (value > 90) {
                    value = 90;
                }
                this.mSeatVm.setPSeatHorzPos(value);
                if (source == 1) {
                    long keepTime = getKeepTime(data);
                    LogUtils.d(str, "onPsnSeatMoveForward, keep: " + keepTime, false);
                    if (keepTime != 0) {
                        this.mSeatVm.setSeatMenuNotShowKeepTime(false, System.currentTimeMillis() + keepTime);
                        return;
                    }
                    return;
                }
                return;
            }
            speak(R.string.speech_seat_control_foremost);
            return;
        }
        LogUtils.d(TAG, "onPsnSeatMoveForward", false);
        this.mSeatVm.controlPsnSeatStart(1, 1, 3);
    }

    protected void onPsnSeatMoveBack(String data) {
        int value;
        ChangeValue changeValue = getChangeValue(data);
        if (changeValue != null) {
            int pSeatHorzPos = this.mSeatVm.getPSeatHorzPos();
            int source = getSource(data);
            String str = TAG;
            LogUtils.d(str, "onPsnSeatMoveBack:" + pSeatHorzPos + ", source: " + source, false);
            if (Math.abs(0 - pSeatHorzPos) >= 2 || source == 1) {
                if (changeValue.isScale()) {
                    value = pSeatHorzPos - changeValue.getValue();
                } else {
                    value = changeValue.getValue();
                }
                if (value < 0) {
                    value = 0;
                }
                this.mSeatVm.setPSeatHorzPos(value);
                if (source == 1) {
                    long keepTime = getKeepTime(data);
                    LogUtils.d(str, "onPsnSeatMoveForward, keep: " + keepTime, false);
                    if (keepTime != 0) {
                        this.mSeatVm.setSeatMenuNotShowKeepTime(false, System.currentTimeMillis() + keepTime);
                        return;
                    }
                    return;
                }
                return;
            }
            speak(R.string.speech_seat_control_rear);
            return;
        }
        LogUtils.d(TAG, "onPsnSeatMoveBack", false);
        this.mSeatVm.controlPsnSeatStart(1, 2, 3);
    }

    protected void onPsnSeatMoveUp(String data) {
        int value;
        ChangeValue changeValue = getChangeValue(data);
        if (changeValue != null) {
            int pSeatVerPos = this.mSeatVm.getPSeatVerPos();
            int source = getSource(data);
            String str = TAG;
            LogUtils.d(str, "onPsnSeatMoveUp:" + pSeatVerPos + ", source: " + source, false);
            if (Math.abs(100 - pSeatVerPos) >= 2 || source == 1) {
                if (changeValue.isScale()) {
                    value = pSeatVerPos + changeValue.getValue();
                } else {
                    value = changeValue.getValue();
                }
                if (value > 100) {
                    value = 100;
                }
                this.mSeatVm.setPSeatVerPos(value);
                if (source == 1) {
                    long keepTime = getKeepTime(data);
                    LogUtils.d(str, "onPsnSeatMoveUp, keep: " + keepTime, false);
                    if (keepTime != 0) {
                        this.mSeatVm.setSeatMenuNotShowKeepTime(false, System.currentTimeMillis() + keepTime);
                        return;
                    }
                    return;
                }
                return;
            }
            speak(R.string.speech_seat_control_highest);
            return;
        }
        LogUtils.d(TAG, "onPsnSeatMoveUp", false);
        this.mSeatVm.controlPsnSeatStart(2, 1, 3);
    }

    protected void onPsnSeatMoveDown(String data) {
        int value;
        ChangeValue changeValue = getChangeValue(data);
        if (changeValue != null) {
            int pSeatVerPos = this.mSeatVm.getPSeatVerPos();
            int source = getSource(data);
            String str = TAG;
            LogUtils.d(str, "onPsnSeatMoveDown:" + pSeatVerPos + ", source: " + source, false);
            if (Math.abs(0 - pSeatVerPos) >= 2 || source == 1) {
                if (changeValue.isScale()) {
                    value = pSeatVerPos - changeValue.getValue();
                } else {
                    value = changeValue.getValue();
                }
                if (value < 0) {
                    value = 0;
                }
                this.mSeatVm.setPSeatVerPos(value);
                if (source == 1) {
                    long keepTime = getKeepTime(data);
                    LogUtils.d(str, "onPsnSeatMoveDown, keep: " + keepTime, false);
                    if (keepTime != 0) {
                        this.mSeatVm.setSeatMenuNotShowKeepTime(false, System.currentTimeMillis() + keepTime);
                        return;
                    }
                    return;
                }
                return;
            }
            speak(R.string.speech_seat_control_lowest);
            return;
        }
        LogUtils.d(TAG, "onPsnSeatMoveDown", false);
        this.mSeatVm.controlPsnSeatStart(2, 2, 3);
    }

    protected void onPsnSeatBackrestForward(String data) {
        int value;
        ChangeValue changeValue = getChangeValue(data);
        if (changeValue != null) {
            int pSeatTiltPos = this.mSeatVm.getPSeatTiltPos();
            int source = getSource(data);
            String str = TAG;
            LogUtils.d(str, "onPsnSeatBackrestForward:" + pSeatTiltPos + ", source: " + source, false);
            if (!CarBaseConfig.getInstance().isSupportTiltPosReversed() ? pSeatTiltPos > 20 || source == 1 : pSeatTiltPos < 85) {
                if (changeValue.isScale()) {
                    value = pSeatTiltPos + (CarBaseConfig.getInstance().isSupportTiltPosReversed() ? changeValue.getValue() : -changeValue.getValue());
                } else {
                    value = changeValue.getValue();
                }
                int i = value <= 85 ? value : 85;
                this.mSeatVm.setPSeatTiltPos(i >= 20 ? i : 20);
                if (source == 1) {
                    long keepTime = getKeepTime(data);
                    LogUtils.d(str, "onPsnSeatBackrestForward, keep: " + keepTime, false);
                    if (keepTime != 0) {
                        this.mSeatVm.setSeatMenuNotShowKeepTime(false, System.currentTimeMillis() + keepTime);
                        return;
                    }
                    return;
                }
                return;
            }
            speak(R.string.speech_seat_control_tilt);
            return;
        }
        LogUtils.d(TAG, "onPsnSeatBackrestForward", false);
        this.mSeatVm.controlPsnSeatStart(3, 1, 3);
    }

    protected void onPsnSeatBackrestBack(String data) {
        int value;
        ChangeValue changeValue = getChangeValue(data);
        if (changeValue != null) {
            int pSeatTiltPos = this.mSeatVm.getPSeatTiltPos();
            int source = getSource(data);
            String str = TAG;
            LogUtils.d(str, "onPsnSeatBackrestBack:" + pSeatTiltPos + ", source: " + source, false);
            if (!CarBaseConfig.getInstance().isSupportTiltPosReversed() ? pSeatTiltPos < 85 || source == 1 : pSeatTiltPos > 20) {
                if (changeValue.isScale()) {
                    value = pSeatTiltPos + (CarBaseConfig.getInstance().isSupportTiltPosReversed() ? -changeValue.getValue() : changeValue.getValue());
                } else {
                    value = changeValue.getValue();
                }
                int i = value <= 85 ? value : 85;
                this.mSeatVm.setPSeatTiltPos(i >= 20 ? i : 20);
                if (source == 1) {
                    long keepTime = getKeepTime(data);
                    LogUtils.d(str, "onPsnSeatBackrestBack, keep: " + keepTime, false);
                    if (keepTime != 0) {
                        this.mSeatVm.setSeatMenuNotShowKeepTime(false, System.currentTimeMillis() + keepTime);
                        return;
                    }
                    return;
                }
                return;
            }
            speak(R.string.speech_seat_control_tilt);
            return;
        }
        LogUtils.d(TAG, "onPsnSeatBackrestBack", false);
        this.mSeatVm.controlPsnSeatStart(3, 2, 3);
    }

    protected int getSupportPsnSeat() {
        if (CarBaseConfig.getInstance().isSupportPsnSeatControl() || CarBaseConfig.getInstance().isSupportMsmP()) {
            if (!seatCheckDoorClose() || this.mWinDoorVm.isDoorClosed(1)) {
                if (!checkPsnSeatOccupied() || this.mSeatVm.isPsnSeatOccupied()) {
                    if (this.mSeatVm.isPsnTiltMovingSafe()) {
                        return (!CarBaseConfig.getInstance().isSupportPsnSeatControl() || CarBaseConfig.getInstance().isSupportMsmP()) ? 1 : 6;
                    }
                    return 5;
                }
                return 4;
            }
            return 2;
        }
        return 0;
    }

    protected void onDialogStart(WakeupReason wakeupReason) {
        LogUtils.i(TAG, "onDialogStart " + wakeupReason.reason);
        this.mSpaceCapsuleViewModel.setSpeechStart();
    }

    protected void onDialogEnd(DialogEndReason dialogEndReason) {
        LogUtils.i(TAG, "onDialogEnd " + dialogEndReason.reason);
        this.mSpaceCapsuleViewModel.setSpeechStop();
    }

    protected int getControlLowSpeedAnalogSoundSupport() {
        return this.mAvasVm.isLowSpdEnabled() ? 1 : 0;
    }

    protected void onLowVolumeOn() {
        LogUtils.d(TAG, "onLowVolumeOn", false);
        this.mAvasVm.setLowSpdEnable(true);
    }

    protected void onLowVolumeOff() {
        LogUtils.d(TAG, "onLowVolumeOff", false);
        this.mAvasVm.setLowSpdEnable(false);
    }

    protected int getControlXpedalStatus() {
        return CarBaseConfig.getInstance().isSupportXPedalNewArch() ? this.mVcuVm.getNewDriveArchXPedalMode() ? 1 : 0 : DriveMode.fromVcuDriveMode(this.mVcuVm.getDriveMode()) == DriveMode.EcoPlus ? 1 : 0;
    }

    protected void onControlXpedalOn(int source) {
        LogUtils.d(TAG, "onControlXpedalOn", false);
        boolean z = source != 1;
        if (CarBaseConfig.getInstance().isSupportXPedalNewArch()) {
            this.mVcuVm.setNewDriveArchXPedalMode(true, z);
        } else {
            this.mVcuVm.setDriveModeByUser(DriveMode.EcoPlus, z);
        }
    }

    protected void onControlXpedalOff(int source) {
        LogUtils.d(TAG, "onControlXpedalOff", false);
        boolean z = source != 1;
        if (CarBaseConfig.getInstance().isSupportXPedalNewArch()) {
            this.mVcuVm.setNewDriveArchXPedalMode(false, z);
        } else {
            this.mVcuVm.setDriveModeByUser(DriveMode.EcoPlusOff, z);
        }
    }

    protected void onControlXpowerOff(int source) {
        LogUtils.d(TAG, "onControlXpowerOff", false);
        if (BaseFeatureOption.getInstance().isSupportXSportApp()) {
            return;
        }
        VcuViewModel vcuViewModel = this.mVcuVm;
        vcuViewModel.setDriveModeByUser(DriveMode.fromVcuDriveMode(vcuViewModel.getDriveModeByUser()));
    }

    protected int getControlScissorDoorLeftOpenSupport() {
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            if ((this.mVcuVm.getGearLevel() == 4 || this.mVcuVm.getGearLevel() == 2) && this.mVcuVm.getCarSpeed() <= 3.0f) {
                int leftSdcSysRunningState = this.mWinDoorVm.getLeftSdcSysRunningState();
                LogUtils.d(TAG, "getControlScissorDoorLeftOpenSupport, LeftSdcRunningState: " + leftSdcSysRunningState, false);
                if (leftSdcSysRunningState == 6) {
                    return 2;
                }
                if (leftSdcSysRunningState == 2 || leftSdcSysRunningState == 3) {
                    return 5;
                }
                return leftSdcSysRunningState == 5 ? 6 : 1;
            }
            return 3;
        }
        return 0;
    }

    protected int getControlScissorDoorRightOpenSupport() {
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            if ((this.mVcuVm.getGearLevel() == 4 || this.mVcuVm.getGearLevel() == 2) && this.mVcuVm.getCarSpeed() <= 3.0f) {
                int rightSdcSysRunningState = this.mWinDoorVm.getRightSdcSysRunningState();
                LogUtils.d(TAG, "getControlScissorDoorRightOpenSupport, RightSdcRunningState: " + rightSdcSysRunningState, false);
                if (rightSdcSysRunningState == 6) {
                    return 2;
                }
                if (rightSdcSysRunningState == 2 || rightSdcSysRunningState == 3) {
                    return 5;
                }
                return rightSdcSysRunningState == 5 ? 6 : 1;
            }
            return 3;
        }
        return 0;
    }

    protected int getControlScissorDoorLeftCloseSupport() {
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            int leftSdcSysRunningState = this.mWinDoorVm.getLeftSdcSysRunningState();
            LogUtils.d(TAG, "getControlScissorDoorLeftCloseSupport, LeftSdcRunningState: " + leftSdcSysRunningState, false);
            if (leftSdcSysRunningState == 6) {
                return 2;
            }
            if (leftSdcSysRunningState == 2 || leftSdcSysRunningState == 3) {
                return 5;
            }
            return leftSdcSysRunningState == 1 ? 7 : 1;
        }
        return 0;
    }

    protected int getControlScissorDoorRightCloseSupport() {
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            int rightSdcSysRunningState = this.mWinDoorVm.getRightSdcSysRunningState();
            LogUtils.d(TAG, "getControlScissorDoorRightCloseSupport, RightSdcRunningState: " + rightSdcSysRunningState, false);
            if (rightSdcSysRunningState == 6) {
                return 2;
            }
            if (rightSdcSysRunningState == 2 || rightSdcSysRunningState == 3) {
                return 5;
            }
            return rightSdcSysRunningState == 1 ? 7 : 1;
        }
        return 0;
    }

    protected int getControlScissorDoorLeftRunningSupport() {
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            int leftSdcSysRunningState = this.mWinDoorVm.getLeftSdcSysRunningState();
            LogUtils.d(TAG, "getControlScissorDoorLeftRunningSupport, LeftSdcRunningState: " + leftSdcSysRunningState, false);
            return (leftSdcSysRunningState == 2 || leftSdcSysRunningState == 3) ? 1 : 2;
        }
        return 0;
    }

    protected int getControlScissorDoorRightRunningSupport() {
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            int rightSdcSysRunningState = this.mWinDoorVm.getRightSdcSysRunningState();
            LogUtils.d(TAG, "getControlScissorDoorRightRunningSupport, RightSdcRunningState: " + rightSdcSysRunningState, false);
            return (rightSdcSysRunningState == 2 || rightSdcSysRunningState == 3) ? 1 : 2;
        }
        return 0;
    }

    protected void onControlScissorLeftDoorOn() {
        LogUtils.d(TAG, "onControlScissorLeftDoorOn", false);
        this.mWinDoorVm.controlSdc(true, 2);
    }

    protected void onControlScissorRightDoorOn() {
        LogUtils.d(TAG, "onControlScissorRightDoorOn", false);
        this.mWinDoorVm.controlSdc(false, 2);
    }

    protected void onControlScissorLeftDoorOff() {
        LogUtils.d(TAG, "onControlScissorLeftDoorOff", false);
        this.mWinDoorVm.controlSdc(true, 1);
    }

    protected void onControlScissorRightDoorOff() {
        LogUtils.d(TAG, "onControlScissorRightDoorOff", false);
        this.mWinDoorVm.controlSdc(false, 1);
    }

    protected void onControlScissorLeftDoorPause() {
        LogUtils.d(TAG, "onControlScissorLeftDoorPause", false);
        this.mWinDoorVm.controlSdc(true, 3);
    }

    protected void onControlScissorRightDoorPause() {
        LogUtils.d(TAG, "onControlScissorRightDoorPause", false);
        this.mWinDoorVm.controlSdc(false, 3);
    }

    protected int getSlideDoorSupport() {
        return CarBaseConfig.getInstance().isSupportSlideDoor() ? 1 : 0;
    }

    protected int getSlideDoorStatus(int mode) {
        int rightSlideDoorState;
        if (mode == 1) {
            rightSlideDoorState = this.mWinDoorVm.getLeftSlideDoorState();
        } else if (mode == 2) {
            rightSlideDoorState = this.mWinDoorVm.getRightSlideDoorState();
        } else {
            LogUtils.d(TAG, "getSlideDoorStatus, mode is invalid");
            return 7;
        }
        if (this.mVcuVm.getGearLevel() != 4 || this.mVcuVm.getCarSpeed() > 3.0f) {
            return 0;
        }
        if (this.mWinDoorVm.isCentralLocked() || ((mode == 1 && this.mWinDoorVm.isLeftSlideDoorLocked()) || (mode == 2 && this.mWinDoorVm.isRightSlideDoorLocked()))) {
            return 1;
        }
        switch (rightSlideDoorState) {
            case 1:
                return 6;
            case 2:
                return 7;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
            case 7:
                return 2;
            default:
                LogUtils.d(TAG, "getSlideDoorStatus, status is invalid");
                return 7;
        }
    }

    protected void onControlSlidingDoorSet(int target, int mode) {
        int i;
        String str = TAG;
        LogUtils.d(str, "onControlSlidingDoorSet, target: " + target + ", mode: " + mode);
        if (target == 0) {
            i = 2;
        } else if (target == 1) {
            i = 1;
        } else if (target != 2) {
            LogUtils.d(str, "onControlSlidingDoorSet, target is invalid");
            i = 0;
        } else {
            i = 3;
        }
        if (mode == 1) {
            this.mWinDoorVm.controlLeftSlideDoor(i);
        } else if (mode == 2) {
            this.mWinDoorVm.controlRightSlideDoor(i);
        } else {
            LogUtils.d(str, "onControlSlidingDoorSet, mode is invalid");
        }
    }

    protected float[] getControlWindowsStateSupport() {
        String str = TAG;
        LogUtils.d(str, "getControlWindowsStateSupport");
        float[] windowsPos = this.mWinDoorVm.getWindowsPos();
        if (windowsPos == null || windowsPos.length < 4) {
            LogUtils.d(str, "WindowPos array is error");
            return null;
        }
        return windowsPos;
    }

    protected int getControlComfortableDrivingModeSupport() {
        int driveMode = this.mVcuVm.getDriveMode();
        LogUtils.d(TAG, "getControlComfortableDrivingModeSupport, driveMode: " + driveMode);
        return driveMode == 7 ? 1 : 0;
    }

    protected void onControlComfortableDrivingModeClose(int source) {
        LogUtils.d(TAG, "onControlComfortableDrivingModeClose");
        this.mVcuVm.setDriveModeByUser(DriveMode.ComfortOff, source != 1);
    }

    protected void onControlComfortableDrivingModeOpen(int source) {
        LogUtils.d(TAG, "onControlComfortableDrivingModeClose");
        this.mVcuVm.setDriveModeByUser(DriveMode.Comfort, source != 1);
    }

    protected int getControlLampSignalSupport() {
        int i;
        if (CarBaseConfig.getInstance().isSupportLlu()) {
            i = this.mLluVm.isLLuSwEnabled() ? 1 : 2;
        } else {
            i = 0;
        }
        LogUtils.d(TAG, "getControlLampSignalSupport, value: " + i);
        return i;
    }

    protected int getNgpStatus() {
        String str = TAG;
        LogUtils.d(str, "getNgpStatus", false);
        if (CarBaseConfig.getInstance().isSupportNgp()) {
            int xpuXpilotState = this.mScuVm.getXpuXpilotState();
            ScuResponse ngpState = this.mScuVm.getNgpState();
            boolean ngpVoiceChangeLane = this.mScuVm.getNgpVoiceChangeLane();
            LogUtils.d(str, "XpuXpilotState: " + xpuXpilotState + ", NgpState: " + ngpState + ", VoiceChangeLane: " + ngpVoiceChangeLane, false);
            if (xpuXpilotState != 0) {
                if (xpuXpilotState != 2) {
                    if (xpuXpilotState != 3) {
                        NedcState nedcState = this.mXpuVm.getNedcState();
                        if (nedcState == NedcState.On || nedcState == NedcState.TurnOning || nedcState == NedcState.TurnOnFailure) {
                            return 9;
                        }
                        if (nedcState == NedcState.TurnOffing) {
                            return 10;
                        }
                        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[ngpState.ordinal()];
                        if (i != 1) {
                            if (i != 2) {
                                return ngpVoiceChangeLane ? 1 : 8;
                            }
                            return 7;
                        }
                        return 6;
                    }
                    return 4;
                }
                return 3;
            }
            return 5;
        }
        return 2;
    }

    protected void setLluSw(boolean isOpen) {
        LogUtils.d(TAG, "setLluSw:" + isOpen, false);
        this.mLluVm.setLluMultiSwEnable(isOpen);
    }

    protected int getControlElectricCurtainSupport() {
        int i = CarBaseConfig.getInstance().isSupportSunShade() ? 0 : -1;
        if (!this.mWinDoorVm.isSunShadeInitialized()) {
            i = 103;
        }
        if (this.mWinDoorVm.isSunShadeHotProtect()) {
            i = 101;
        }
        if (this.mWinDoorVm.isSunShadeBlocked()) {
            i = 102;
        }
        if (i == 0) {
            i = this.mWinDoorVm.getSunShadePos();
        }
        LogUtils.d(TAG, "getControlElectricCurtainSupport: result: " + i, false);
        return i;
    }

    protected void onControlSunShade(int mode, int pos) {
        LogUtils.d(TAG, "onControlSunShade: mode=" + mode + ", pos=" + pos);
        if (pos < 0 || pos > 100 || pos % 5 != 0) {
            return;
        }
        if (mode == 1) {
            pos = 100 - pos;
        }
        this.mWinDoorVm.setSunShadePos(pos);
    }

    protected int getVipChairStatus() {
        return CarBaseConfig.getInstance().isSupportVipSeat() ? 1 : 0;
    }

    protected int getCapsuleCurrentMode() {
        if (CarBaseConfig.getInstance().isSupportVipSeat()) {
            int currentSubMode = this.mSpaceCapsuleViewModel.getCurrentSubMode();
            LogUtils.d(TAG, "SpaceCapsule subMode is " + currentSubMode, false);
            if (currentSubMode == 1) {
                return 2;
            }
            if (currentSubMode == 2) {
                return 3;
            }
            if (currentSubMode == 3 || currentSubMode == 5) {
                return 4;
            }
            if (currentSubMode == 4 || currentSubMode == 6) {
                return 5;
            }
        }
        return 0;
    }

    protected boolean getInDcCharge() {
        return this.mVcuVm.isInDcCharge();
    }

    protected int getAvailableMileage() {
        return this.mVcuVm.getAvailableMileage();
    }

    private int getPercent(String data) {
        try {
            JSONObject jSONObject = new JSONObject(data);
            if (jSONObject.has("percent")) {
                return jSONObject.getInt("percent");
            }
            return 100;
        } catch (Exception e) {
            LogUtils.e(TAG, "getPercent: " + e.getMessage());
            return 100;
        }
    }

    protected void onPrev() {
        LogUtils.d(TAG, "onPrev", false);
        this.mMeditationViewModel.playPre();
    }

    protected void onNext() {
        LogUtils.d(TAG, "onNext", false);
        this.mMeditationViewModel.playNext();
    }

    protected void onPause() {
        LogUtils.d(TAG, "onPause", false);
        this.mMeditationViewModel.pause();
    }

    protected void onResume() {
        LogUtils.d(TAG, "onResume", false);
        this.mMeditationViewModel.play();
    }

    protected void onPnsSateChange(boolean state) {
        LogUtils.d(TAG, "onPnsSateChange", false);
        this.mMeditationViewModel.handlePnsStateChange(state);
    }

    protected void onLluEffectPlayEnable(int mode) {
        LogUtils.d(TAG, "onLluEffectPlayEnable:" + mode);
        if (mode == 0) {
            this.mLluVm.setLluWakeWaitSwitch(true);
        } else if (mode == 1) {
            this.mLluVm.setLluSleepSwitch(true);
        } else if (mode != 2) {
        } else {
            this.mLluVm.setLluChargingSwitch(true);
        }
    }

    protected void onLluEffectPlayDisable(int mode) {
        LogUtils.d(TAG, "onLluEffectPlayDisable:" + mode);
        if (mode == 0) {
            this.mLluVm.setLluWakeWaitSwitch(false);
        } else if (mode == 1) {
            this.mLluVm.setLluSleepSwitch(false);
        } else if (mode != 2) {
        } else {
            this.mLluVm.setLluChargingSwitch(false);
        }
    }

    protected void onLluEffectPreview(int mode) {
        LogUtils.d(TAG, "onLluEffectPreview:" + mode);
        if (mode == 0) {
            this.mLluVm.setLluEffectPreview(getLluSayHiEffect());
        } else if (mode == 1) {
            this.mLluVm.setLluEffectPreview(LluEffect.AwakeWait);
        } else if (mode == 2) {
            this.mLluVm.setLluEffectPreview(LluEffect.Sleep);
        } else if (mode == 3) {
            this.mLluVm.setLluEffectPreview(LluEffect.DcCharged);
        } else if (mode == 4) {
            this.mLluVm.setLluEffectPreview(LluEffect.AcCharged);
        } else if (mode != 5) {
        } else {
            this.mLluVm.setLluEffectPreview(LluEffect.FindCar);
        }
    }

    private LluEffect getLluSayHiEffect() {
        AvasEffect fromAvasType = AvasEffect.fromAvasType(this.mAvasVm.getFriendEffect());
        LluSayHiEffect lluSayHiEffect = LluSayHiEffect.EffectA;
        if (fromAvasType == null) {
            return LluEffect.SayHi;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect[fromAvasType.ordinal()];
        if (i == 1) {
            lluSayHiEffect = LluSayHiEffect.EffectB;
        } else if (i == 2) {
            lluSayHiEffect = LluSayHiEffect.EffectC;
        }
        LluEffect lluEffect = LluEffect.SayHi;
        int i2 = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[lluSayHiEffect.ordinal()];
        if (i2 == 1) {
            lluEffect = LluEffect.SayHi2;
        } else if (i2 == 2) {
            lluEffect = LluEffect.SayHi3;
        }
        LogUtils.d(TAG, "getLluEffect: avasEffect=" + fromAvasType + ", sayHiEffect=" + lluSayHiEffect + ", effect=" + lluEffect);
        return lluEffect;
    }

    protected void onLluEffectSet(int mode) {
        AvasEffect avasEffect;
        LogUtils.d(TAG, "onLluEffectSet:" + mode);
        final LluSayHiEffect lluSayHiEffect = null;
        if (mode == 0) {
            lluSayHiEffect = LluSayHiEffect.EffectA;
            avasEffect = AvasEffect.SoundEffect1;
        } else if (mode == 1) {
            lluSayHiEffect = LluSayHiEffect.EffectB;
            avasEffect = AvasEffect.SoundEffect2;
        } else if (mode != 2) {
            avasEffect = null;
        } else {
            lluSayHiEffect = LluSayHiEffect.EffectC;
            avasEffect = AvasEffect.SoundEffect3;
        }
        if (lluSayHiEffect != null) {
            this.mLluVm.setLluEffect(LluEffect.SayHi, lluSayHiEffect.toLluCmd());
            this.mAvasVm.setAvasFriendEffect(avasEffect);
            if (this.mLluVm.getLluPreviewStateData().getValue() == LluPreviewState.Previewing) {
                ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.speech.-$$Lambda$CarControlSpeechModel$6moZqLiHthrvYnjdvNrt-lJCjE0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CarControlSpeechModel.this.lambda$onLluEffectSet$1$CarControlSpeechModel(lluSayHiEffect);
                    }
                }, 450L);
                if (getControlLightLanguageSettingPreviewSupport() == 1) {
                    ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.speech.-$$Lambda$CarControlSpeechModel$eaY48YghvT23TKelrtzV1ErgRDI
                        @Override // java.lang.Runnable
                        public final void run() {
                            CarControlSpeechModel.this.lambda$onLluEffectSet$2$CarControlSpeechModel(lluSayHiEffect);
                        }
                    }, 50L);
                }
            }
        }
    }

    public /* synthetic */ void lambda$onLluEffectSet$1$CarControlSpeechModel(final LluSayHiEffect finalSayHiEffect) {
        String soundPath = getSoundPath(finalSayHiEffect);
        if (TextUtils.isEmpty(soundPath)) {
            return;
        }
        SoundHelper.play(soundPath, true, false);
    }

    public /* synthetic */ void lambda$onLluEffectSet$2$CarControlSpeechModel(final LluSayHiEffect finalSayHiEffect) {
        this.mLluVm.setLluEffectPreview(convertSayHiEffect2LluEffect(finalSayHiEffect));
    }

    protected void onSayHiEnable() {
        LogUtils.d(TAG, "onSayHiEnable");
        this.mLluVm.setSayHiEnable(true);
        this.mAvasVm.setAvasSayHiSw(true);
    }

    protected void onSayHiDisable() {
        LogUtils.d(TAG, "onSayHiDisable");
        this.mLluVm.setSayHiEnable(false);
        this.mAvasVm.setAvasSayHiSw(false);
    }

    protected void onAvasSayHiEnable() {
        LogUtils.d(TAG, "onAvasSayHiEnable");
        this.mAvasVm.setAvasSayHiSw(true);
    }

    protected void onAvasSayHiDisable() {
        LogUtils.d(TAG, "onAvasSayHiDisable");
        this.mAvasVm.setAvasSayHiSw(false);
    }

    protected void onIhbOn() {
        LogUtils.d(TAG, "onIhbOn");
        this.mScuVm.setIhbEnable(true, false);
    }

    protected void onIhbOff() {
        LogUtils.d(TAG, "onIhbOff");
        this.mScuVm.setIhbEnable(false, false);
    }

    protected void onXkeySetting(Integer mode) {
        LogUtils.d(TAG, "onXkeySetting:" + mode);
        if (mode != null) {
            int intValue = mode.intValue();
            if (intValue == 0) {
                this.mMeterVm.setXKeyForCustomerValue(XKeyForCustomer.UnlockTrunk);
            } else if (intValue == 1) {
                this.mMeterVm.setXKeyForCustomerValue(XKeyForCustomer.ShowOff);
            } else if (intValue == 2) {
                this.mMeterVm.setXKeyForCustomerValue(XKeyForCustomer.SwitchMedia);
            } else if (intValue != 3) {
            } else {
                this.mMeterVm.setXKeyForCustomerValue(XKeyForCustomer.AutoPark);
            }
        }
    }

    protected void onDoorKeySetting(Integer mode) {
        LogUtils.d(TAG, "onDoorKeySetting:" + mode);
        if (mode != null) {
            int intValue = mode.intValue();
            if (intValue == 0) {
                this.mMeterVm.setDoorKeyForCustomerValue(DoorKeyForCustomer.Disable);
            } else if (intValue == 1) {
                this.mMeterVm.setDoorKeyForCustomerValue(DoorKeyForCustomer.Speech);
            } else if (intValue == 2) {
                this.mMeterVm.setDoorKeyForCustomerValue(DoorKeyForCustomer.Mute);
            } else if (intValue != 3) {
            } else {
                this.mMeterVm.setDoorKeyForCustomerValue(DoorKeyForCustomer.SwitchMedia);
            }
        }
    }

    protected void onWelcomeModeOn() {
        LogUtils.d(TAG, "onWelcomeModeOn");
        this.mSeatVm.setWelcomeMode(true);
    }

    protected void onWelcomeModeOff() {
        LogUtils.d(TAG, "onWelcomeModeOff");
        this.mSeatVm.setWelcomeMode(false);
    }

    protected void onBootSoundOn() {
        LogUtils.d(TAG, "onBootSoundOn");
        AvasViewModel avasViewModel = this.mAvasVm;
        avasViewModel.setBootSoundEffectValue(BootSoundEffect.fromXuiCmd(avasViewModel.getBootEffectBeforeSw()));
    }

    protected void onBootSoundOff() {
        LogUtils.d(TAG, "onBootSoundOff");
        this.mAvasVm.setBootSoundEffectValue(BootSoundEffect.Off);
    }

    protected void onBootSoundEffectSet(Integer mode) {
        LogUtils.d(TAG, "onBootSoundEffectSet:" + mode);
        if (mode != null) {
            switch (mode.intValue()) {
                case 0:
                case 4:
                    this.mAvasVm.setBootSoundEffectValue(BootSoundEffect.EffectA);
                    return;
                case 1:
                case 5:
                    this.mAvasVm.setBootSoundEffectValue(BootSoundEffect.EffectB);
                    return;
                case 2:
                case 6:
                    this.mAvasVm.setBootSoundEffectValue(BootSoundEffect.EffectC);
                    return;
                case 3:
                case 7:
                    this.mAvasVm.setBootSoundEffectValue(BootSoundEffect.EffectD);
                    return;
                default:
                    return;
            }
        }
    }

    protected void onsCwcSwOn(int source) {
        LogUtils.d(TAG, "onsCwcSwOn");
        this.mCarBodyVm.setCwcSwEnable(true, source != 1);
    }

    protected void onsCwcSwOff(int source) {
        LogUtils.d(TAG, "onsCwcSwOff");
        this.mCarBodyVm.setCwcSwEnable(false, source != 1);
    }

    protected void onSetReverseMirrorModeOn(int mode) {
        if (CarBaseConfig.getInstance().getCduMirrorReverseMode() == 3) {
            LogUtils.d(TAG, "onSetReverseMirrorModeOn:" + mode + ", currentMode=" + this.mMirrorVm.isMirrorAutoDown());
            this.mMirrorVm.setMirrorAutoDown(true);
            return;
        }
        MirrorReverseMode reverseMirrorMode = this.mMirrorVm.getReverseMirrorMode();
        LogUtils.d(TAG, "onSetReverseMirrorModeOn:" + mode + ", currentMode=" + reverseMirrorMode);
        if (mode == 0) {
            this.mMirrorVm.setReverseMirrorMode(MirrorReverseMode.Both.toBcmMirrorCmd());
        } else if (mode == 1) {
            if (reverseMirrorMode == MirrorReverseMode.Off) {
                this.mMirrorVm.setReverseMirrorMode(MirrorReverseMode.Left.toBcmMirrorCmd());
            } else if (reverseMirrorMode == MirrorReverseMode.Right) {
                this.mMirrorVm.setReverseMirrorMode(MirrorReverseMode.Both.toBcmMirrorCmd());
            }
        } else if (mode != 2) {
        } else {
            if (reverseMirrorMode == MirrorReverseMode.Off) {
                this.mMirrorVm.setReverseMirrorMode(MirrorReverseMode.Right.toBcmMirrorCmd());
            } else if (reverseMirrorMode == MirrorReverseMode.Left) {
                this.mMirrorVm.setReverseMirrorMode(MirrorReverseMode.Both.toBcmMirrorCmd());
            }
        }
    }

    protected void onSetReverseMirrorModeOff(int mode) {
        if (CarBaseConfig.getInstance().getCduMirrorReverseMode() == 3) {
            LogUtils.d(TAG, "onSetReverseMirrorModeOn:" + mode + ", currentMode=" + this.mMirrorVm.isMirrorAutoDown());
            this.mMirrorVm.setMirrorAutoDown(false);
            return;
        }
        MirrorReverseMode reverseMirrorMode = this.mMirrorVm.getReverseMirrorMode();
        LogUtils.d(TAG, "onSetReverseMirrorModeOff:" + mode + ", currentMode=" + reverseMirrorMode);
        if (mode == 0) {
            this.mMirrorVm.setReverseMirrorMode(MirrorReverseMode.Off.toBcmMirrorCmd());
        } else if (mode == 1) {
            if (reverseMirrorMode == MirrorReverseMode.Both) {
                this.mMirrorVm.setReverseMirrorMode(MirrorReverseMode.Right.toBcmMirrorCmd());
            } else if (reverseMirrorMode == MirrorReverseMode.Left) {
                this.mMirrorVm.setReverseMirrorMode(MirrorReverseMode.Off.toBcmMirrorCmd());
            }
        } else if (mode != 2) {
        } else {
            if (reverseMirrorMode == MirrorReverseMode.Both) {
                this.mMirrorVm.setReverseMirrorMode(MirrorReverseMode.Left.toBcmMirrorCmd());
            } else if (reverseMirrorMode == MirrorReverseMode.Right) {
                this.mMirrorVm.setReverseMirrorMode(MirrorReverseMode.Off.toBcmMirrorCmd());
            }
        }
    }

    protected void onDoorknobAutoOn() {
        LogUtils.d(TAG, "onDoorknobAutoOn");
        this.mWinDoorVm.setAutoDoorHandleEnable(true);
    }

    protected void onDoorknobAutoOff() {
        LogUtils.d(TAG, "onDoorknobAutoOff");
        this.mWinDoorVm.setAutoDoorHandleEnable(false);
    }

    private void onParkAutoUnlockOn() {
        LogUtils.d(TAG, "onParkAutoUnlockOn");
        this.mCarBodyVm.setParkingAutoUnlock(true);
    }

    private void onParkAutoUnlockOff() {
        LogUtils.d(TAG, "onParkAutoUnlockOff");
        this.mCarBodyVm.setParkingAutoUnlock(false);
    }

    private void onArsFold() {
        LogUtils.i(TAG, "onArsFold");
        this.mArsVm.fold();
    }

    private void onArsUnfold() {
        LogUtils.i(TAG, "onArsUnfold");
        this.mArsVm.unfold();
    }

    private void onArsModeAutoOn() {
        LogUtils.i(TAG, "onArsModeAutoOn");
        this.mArsVm.setArsWorkMode(ArsWorkMode.AUTO);
    }

    private void onArsModeAutoOff() {
        LogUtils.i(TAG, "onArsModeAutoOff");
        this.mArsVm.setArsWorkMode(ArsWorkMode.MANUAL);
    }

    protected int getControlLightLanguageSettingPreviewSupport() {
        LogUtils.d(TAG, "getControlLightLanguageSettingPreviewSupport");
        if (CarBaseConfig.getInstance().isSupportLluPreview()) {
            if (this.mLluVm.isLLuSwEnabled()) {
                if (this.mVcuVm.getGearLevelValue() != GearLevel.P) {
                    return 3;
                }
                if (this.mLampVm.isTurnLampOn()) {
                    return 4;
                }
                return this.mLampVm.getRearFogLampState() ? 5 : 1;
            }
            return 2;
        }
        return 0;
    }

    protected int getControlLightLanguageAutoPlayState() {
        LogUtils.d(TAG, "getControlLightLanguageAutoPlayState");
        return this.mLluVm.isSayHiEnabled() ? 1 : 0;
    }

    protected int getIhbSupport() {
        LogUtils.d(TAG, "getIhbSupport");
        if (CarBaseConfig.getInstance().isSupportIhb()) {
            if (CarBaseConfig.getInstance().isVpmNotReady()) {
                return 8;
            }
            NedcState nedcState = this.mXpuVm.getNedcState();
            if (nedcState == NedcState.On || nedcState == NedcState.TurnOning || nedcState == NedcState.TurnOnFailure) {
                return 5;
            }
            if (nedcState == NedcState.TurnOffing) {
                return 6;
            }
            return this.mScuVm.getIhbState() == ScuResponse.FAIL ? 7 : 1;
        }
        return 0;
    }

    protected int getIhbStatus() {
        return this.mScuVm.getIhbState() == ScuResponse.ON ? 1 : 0;
    }

    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    protected int getControlXkeySettingSupport(Integer mode) {
        ?? r0;
        if (mode != null) {
            int intValue = mode.intValue();
            if (intValue == 0) {
                r0 = CarBaseConfig.getInstance().isSupportXUnlockTrunk();
            } else if (intValue == 1) {
                r0 = CarBaseConfig.getInstance().isSupportXSayHi();
            } else if (intValue == 2) {
                r0 = CarBaseConfig.getInstance().isSupportSwitchMedia();
            } else if (intValue == 3) {
                r0 = CarBaseConfig.getInstance().isSupportAutoPark();
            }
            LogUtils.d(TAG, "getControlXkeySettingSupport: mode=" + mode + ", result=" + ((boolean) r0));
            return r0;
        }
        r0 = 0;
        LogUtils.d(TAG, "getControlXkeySettingSupport: mode=" + mode + ", result=" + ((boolean) r0));
        return r0;
    }

    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5, types: [boolean, int] */
    protected int getSupportDoorKeySetting(int mode) {
        if (CarBaseConfig.getInstance().isSupportDoorKeySetting()) {
            ?? r0 = 0;
            if (mode != 0) {
                if (mode == 1) {
                    r0 = BaseFeatureOption.getInstance().isShowDoorKeySpeech();
                } else if (mode != 2) {
                    if (mode == 3) {
                        r0 = CarBaseConfig.getInstance().isSupportSwitchMedia();
                    }
                }
                LogUtils.d(TAG, "getSupportDoorKeySetting: mode=" + mode + ", result=" + ((boolean) r0));
                return r0;
            }
            r0 = 1;
            LogUtils.d(TAG, "getSupportDoorKeySetting: mode=" + mode + ", result=" + ((boolean) r0));
            return r0;
        }
        LogUtils.d(TAG, "getSupportDoorKeySetting: mode=" + mode + ", door key not support");
        return -1;
    }

    protected int getSupportWelcomeMode() {
        LogUtils.d(TAG, "getSupportWelcomeMode");
        return CarBaseConfig.getInstance().isSupportWelcomeMode() ? 1 : 0;
    }

    protected int getSupportWelcomeModeSoundEffect() {
        LogUtils.d(TAG, "getSupportWelcomeModeSoundEffect");
        return CarBaseConfig.getInstance().isSupportBootSoundEffect() ? 1 : 0;
    }

    protected int getWelcomeModeSoundEffectStatus() {
        int bootSoundEffect = this.mAvasVm.getBootSoundEffect();
        LogUtils.d(TAG, "getWelcomeModeSoundEffectStatus: " + bootSoundEffect);
        return bootSoundEffect <= 0 ? 0 : 1;
    }

    protected int getSupportCwc() {
        LogUtils.d(TAG, "getSupportCwc");
        return CarBaseConfig.getInstance().isSupportCwc() ? 1 : 0;
    }

    protected int getSupportMirrorReverseMode() {
        LogUtils.d(TAG, "getSupportMirrorReverseMode");
        return CarBaseConfig.getInstance().getCduMirrorReverseMode();
    }

    protected int getCarWindowCanControl(int pos) {
        int i;
        switch (pos) {
            case 0:
                i = 0;
                break;
            case 1:
                i = 1;
                break;
            case 2:
                i = 2;
                break;
            case 3:
                i = 3;
                break;
            case 4:
                i = 7;
                break;
            case 5:
                i = 8;
                break;
            case 6:
            default:
                i = 4;
                break;
            case 7:
                i = 5;
                break;
            case 8:
                i = 6;
                break;
        }
        int isWindowInitFailed = 1 ^ this.mWinDoorVm.isWindowInitFailed(i);
        LogUtils.d(TAG, "getCarWindowCanControl:pos=" + pos + ", state=" + isWindowInitFailed);
        return isWindowInitFailed;
    }

    protected int getSupportDoorKnobAutoOpen() {
        return CarBaseConfig.getInstance().isSupportDhc() ? 1 : 0;
    }

    private int getMode(String data) {
        try {
            JSONObject jSONObject = new JSONObject(data);
            if (jSONObject.has("mode")) {
                return jSONObject.getInt("mode");
            }
            return 100;
        } catch (Exception e) {
            LogUtils.e(TAG, "getMode: " + e.getMessage());
            return 100;
        }
    }

    private int getPilot(String data) {
        try {
            JSONObject jSONObject = new JSONObject(data);
            if (jSONObject.has("pilot")) {
                return jSONObject.getInt("pilot");
            }
            return 1;
        } catch (Exception e) {
            LogUtils.e(TAG, "getPilot: " + e.getMessage());
            return 1;
        }
    }

    private int getDirection(String data) {
        try {
            JSONObject jSONObject = new JSONObject(data);
            if (jSONObject.has(VuiConstants.EVENT_VALUE_DIRECTION)) {
                return jSONObject.getInt(VuiConstants.EVENT_VALUE_DIRECTION);
            }
            return 1;
        } catch (Exception e) {
            LogUtils.e(TAG, "getDirection: " + e.getMessage());
            return 1;
        }
    }

    private int getTarget(String data) {
        try {
            JSONObject jSONObject = new JSONObject(data);
            if (jSONObject.has(TypedValues.Attributes.S_TARGET)) {
                return jSONObject.getInt(TypedValues.Attributes.S_TARGET);
            }
            return 1;
        } catch (Exception e) {
            LogUtils.e(TAG, "getTarget: " + e.getMessage());
            return 1;
        }
    }

    private String getPageId(String data) {
        LogUtils.d(TAG, "getPageId data: " + data, false);
        try {
            JSONObject jSONObject = new JSONObject(data);
            return jSONObject.has(MoleEvent.KEY_PAGE_ID) ? jSONObject.getString(MoleEvent.KEY_PAGE_ID) : "";
        } catch (Exception e) {
            LogUtils.e(TAG, "getPageId: " + e.getMessage());
            return "";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getNewDrivingMode() {
        /*
            r5 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r0 = r0.isSupportXSport()
            r1 = 0
            if (r0 == 0) goto L25
            com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel r0 = r5.mVcuVm
            com.xiaopeng.carcontrol.viewmodel.vcu.XSportDriveMode r0 = r0.getXSportDriveModeValue()
            com.xiaopeng.carcontrol.viewmodel.vcu.XSportDriveMode r2 = com.xiaopeng.carcontrol.viewmodel.vcu.XSportDriveMode.Geek
            if (r2 != r0) goto L18
            r0 = 8
            goto L26
        L18:
            com.xiaopeng.carcontrol.viewmodel.vcu.XSportDriveMode r2 = com.xiaopeng.carcontrol.viewmodel.vcu.XSportDriveMode.XPower
            if (r2 != r0) goto L1e
            r0 = 7
            goto L26
        L1e:
            com.xiaopeng.carcontrol.viewmodel.vcu.XSportDriveMode r2 = com.xiaopeng.carcontrol.viewmodel.vcu.XSportDriveMode.Race
            if (r2 != r0) goto L25
            r0 = 9
            goto L26
        L25:
            r0 = r1
        L26:
            if (r0 != 0) goto L53
            com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel r2 = r5.mVcuVm
            com.xiaopeng.carcontrol.viewmodel.vcu.DriveMode r2 = r2.getDriveModeValue()
            if (r2 == 0) goto L53
            int[] r3 = com.xiaopeng.carcontrol.speech.CarControlSpeechModel.AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode
            int r2 = r2.ordinal()
            r2 = r3[r2]
            switch(r2) {
                case 1: goto L52;
                case 2: goto L50;
                case 3: goto L4e;
                case 4: goto L40;
                case 5: goto L3e;
                case 6: goto L3c;
                default: goto L3b;
            }
        L3b:
            goto L53
        L3c:
            r0 = 5
            goto L53
        L3e:
            r0 = 4
            goto L53
        L40:
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r0 = r0.isSupportDriveModeNewArch()
            if (r0 == 0) goto L4c
            r0 = 3
            goto L53
        L4c:
            r0 = 6
            goto L53
        L4e:
            r0 = 2
            goto L53
        L50:
            r0 = 1
            goto L53
        L52:
            r0 = r1
        L53:
            java.lang.String r2 = com.xiaopeng.carcontrol.speech.CarControlSpeechModel.TAG
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "getNewDrivingMode: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            com.xiaopeng.carcontrol.util.LogUtils.d(r2, r3, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.speech.CarControlSpeechModel.getNewDrivingMode():int");
    }

    private int getDrivingRacerSupport() {
        int i = 4;
        if (!BaseFeatureOption.getInstance().isSupportXSportRacerMode()) {
            i = 0;
        } else if (this.mChassisVm.getEspFault()) {
            i = 2;
        } else if (this.mVcuVm.getGearLevel() != 4) {
            i = 3;
        } else if (this.mVcuVm.getAvailableMileage() >= 30) {
            i = 1;
        }
        LogUtils.d(TAG, "getDrivingRacerSupport: " + i, false);
        return i;
    }

    private int getTrailerModeStatus() {
        return this.mChassisVm.getTrailerModeStatus() ? 1 : 0;
    }

    private int getSuspensionFixStatus() {
        return this.mChassisVm.getAirSuspensionRepairMode() ? 1 : 0;
    }

    private int getSuspensionFlatStatus() {
        return this.mChassisVm.getAsCampingMode() ? 1 : 0;
    }

    private int getSupportSuspensionAuto() {
        int i = 3;
        if (CarBaseConfig.getInstance().isSupportXpu()) {
            int xpuXpilotState = this.mScuVm.getXpuXpilotState();
            if (xpuXpilotState == 0) {
                i = 4;
            } else if (xpuXpilotState == 1) {
                i = 1;
            } else if (xpuXpilotState == 3) {
                i = 2;
            }
        } else {
            i = 0;
        }
        LogUtils.i(TAG, "getSuspensionFlatStatus: ");
        return i;
    }

    private int getSupportMassage() {
        if (CarBaseConfig.getInstance().isSupportRearSeatMassage()) {
            return 2;
        }
        return CarBaseConfig.getInstance().isSupportSeatMassage() ? 1 : 0;
    }

    private int getMassageCurrentStatus(int pilot) {
        return this.mSeatVm.isMassagerRunning(pilot + (-1)) ? 1 : 0;
    }

    private int getMassageRunningMode(int pilot) {
        int i = 1;
        String massagerRunningEffectId = this.mSeatVm.getMassagerRunningEffectId(pilot - 1);
        int i2 = 0;
        while (true) {
            String[] strArr = MASSAGE_MODES;
            if (i2 >= strArr.length) {
                return i;
            }
            if (strArr[i2].equals(massagerRunningEffectId)) {
                i = i2 + 1;
            }
            i2++;
        }
    }

    private int getMassageCurrentStrength(int pilot) {
        return this.mSeatVm.getMassagerIntensity(pilot - 1);
    }

    private int getSupportSwing() {
        return CarBaseConfig.getInstance().isSupportSeatRhythm() ? 1 : 0;
    }

    private int getSwingCurrentStatus(int pilot) {
        return this.mSeatVm.isRhythmRunning(pilot + (-1)) ? 1 : 0;
    }

    private int getSwingRunningMode(int pilot) {
        return this.mSeatVm.getRhythmMode() + 1;
    }

    private int getSwingCurrentStrength(int pilot) {
        return this.mSeatVm.getRhythmIntensity(pilot - 1);
    }

    private int getSupportBackWelcomeMode() {
        return CarBaseConfig.getInstance().isSupportRearSeatWelcomeMode() ? 1 : 0;
    }

    private int getSupportSuspensionWelcomeMode() {
        return CarBaseConfig.getInstance().isSupportAirSuspension() ? 1 : 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v3, types: [int] */
    private int getKeyParkSupport(int mode) {
        ?? r0;
        if (mode == 1) {
            r0 = CarBaseConfig.getInstance().isSupportKeyPark();
        } else if (mode == 2) {
            r0 = CarBaseConfig.getInstance().isSupportKeyParkAdvanced();
        } else {
            r0 = mode != 3 ? 0 : CarBaseConfig.getInstance().isSupportCarCallAdvanced();
        }
        LogUtils.i(TAG, "getKeyParkSupport mode:" + mode + " support:" + ((int) r0));
        return r0;
    }

    private int onModesDrivingXsport(int mode) {
        int i;
        if (!BaseFeatureOption.getInstance().isSupportXSportApp()) {
            XSportDriveMode fromVcuState = XSportDriveMode.fromVcuState(mode);
            if (fromVcuState == null) {
                return -1;
            }
            if (CarBaseConfig.getInstance().isSupportXPedal() && this.mVcuVm.getNewDriveArchXPedalMode()) {
                i = 104;
            } else if (this.mVcuVm.getAvailableMileage() < 30) {
                i = 102;
            } else if (!this.mChassisVm.isTpmsPressureNormal()) {
                i = 103;
            } else if (this.mVcuVm.getFirstEnterXpowerFlag()) {
                this.mVcuVm.handleShowXpowerDialog(true);
                i = 101;
            } else {
                this.mVcuVm.setXSportDriveModeByUser(fromVcuState);
                i = 0;
            }
            LogUtils.d(TAG, "onModesDrivingXsport, mode: " + mode + ", result: " + i, false);
            return i;
        }
        return this.mVcuVm.onModesDrivingXSport(mode);
    }

    private int getLightAtmosphereModeSupport() {
        boolean isSupportFullAtl = CarBaseConfig.getInstance().isSupportFullAtl();
        LogUtils.d(TAG, "getLightAtmosphereModeSupport, result: " + (isSupportFullAtl ? 1 : 0), false);
        return isSupportFullAtl ? 1 : 0;
    }

    private int getLightAtmosphereColorSupport() {
        AtlViewModel atlViewModel = this.mAtlVm;
        boolean isSupportDoubleThemeColor = atlViewModel.isSupportDoubleThemeColor(atlViewModel.getAtlEffect());
        LogUtils.d(TAG, "getLightAtmosphereColorSupport, supportDouble: " + isSupportDoubleThemeColor, false);
        return isSupportDoubleThemeColor ? 1 : 0;
    }

    private int getLightAtmosphereCurrentMode() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$light$AtlEffect[this.mAtlVm.getAtlEffect(AtlEffect.None).ordinal()];
        int i2 = 4;
        if (i == 1) {
            i2 = 1;
        } else if (i == 2) {
            i2 = 2;
        } else if (i == 3) {
            i2 = 3;
        } else if (i != 4) {
            i2 = 0;
        }
        LogUtils.d(TAG, "getLightAtmosphereCurrentMode, result: " + i2, false);
        return i2;
    }

    private int getLightTopBrightness() {
        int domeLightBrightness = this.mLampVm.getDomeLightBrightness();
        LogUtils.d(TAG, "getLightTopBrightness, result: " + domeLightBrightness, false);
        return domeLightBrightness;
    }

    private int getLightTopStatus(int pilot) {
        int i;
        switch (pilot) {
            case 0:
                i = 4;
                break;
            case 1:
                i = 0;
                break;
            case 2:
                i = 1;
                break;
            case 3:
                i = 2;
                break;
            case 4:
                i = 3;
                break;
            case 5:
                i = 5;
                break;
            case 6:
                i = 6;
                break;
            default:
                i = -1;
                break;
        }
        boolean domeLightSw = this.mLampVm.getDomeLightSw(i);
        LogUtils.d(TAG, "getLightTopStatus, pilot: " + pilot + ", result: " + (domeLightSw ? 1 : 0), false);
        return domeLightSw ? 1 : 0;
    }

    private int getLightTopSupport() {
        boolean isSupportDomeLightIndependentCtrl = CarBaseConfig.getInstance().isSupportDomeLightIndependentCtrl();
        LogUtils.d(TAG, "getLightTopSupport, result: " + (isSupportDomeLightIndependentCtrl ? 1 : 0), false);
        return isSupportDomeLightIndependentCtrl ? 1 : 0;
    }

    private int getFirsTimeLightGeek(int mode) {
        int firsTimeXSport = this.mVcuVm.getFirsTimeXSport(mode);
        LogUtils.d(TAG, "getFirsTimeLightGeek, pilot: " + mode + ", result: " + firsTimeXSport, false);
        return firsTimeXSport;
    }

    private int getXSportSupport() {
        return CarBaseConfig.getInstance().isSupportXSport() ? 1 : 0;
    }

    private int getSleepMode() {
        return this.mSpaceCapsuleViewModel.getSleepMode();
    }

    private int getSuspensionRigiditySupport() {
        return CarBaseConfig.getInstance().isSupportCdcControl() ? 1 : 0;
    }

    private int getShowModeStatus() {
        return this.mVcuVm.isExhibitionModeOn() ? 1 : 0;
    }

    private ChangeValue getChangeValue(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(data);
            if (jSONObject.has(TypedValues.Attributes.S_TARGET) || jSONObject.has("scale") || jSONObject.has("percent")) {
                return ChangeValue.fromJson(data);
            }
        } catch (Exception unused) {
            LogUtils.d(TAG, "getChangeValue failed: " + data);
        }
        return null;
    }

    private int getSource(String data) {
        try {
            JSONObject jSONObject = new JSONObject(data);
            if (jSONObject.has("source")) {
                return jSONObject.getInt("source");
            }
            return 0;
        } catch (Exception e) {
            LogUtils.e(TAG, "getSource: " + e.getMessage());
            return 0;
        }
    }

    private long getKeepTime(String data) {
        try {
            JSONObject jSONObject = new JSONObject(data);
            if (jSONObject.has("keep")) {
                return jSONObject.getLong("keep");
            }
            return 0L;
        } catch (Exception e) {
            LogUtils.e(TAG, "getSource: " + e.getMessage());
            return 0L;
        }
    }

    public int getArsStatus() {
        if (CarBaseConfig.getInstance().isSupportElecTail()) {
            int checkError = this.mArsVm.checkError();
            ArsFoldState arsFoldState = this.mArsVm.getArsFoldState();
            LogUtils.i(TAG, "getArsStatus: error: " + checkError + ", foldState: " + arsFoldState);
            if (checkError == 2) {
                this.mArsVm.startInitialize(true);
            }
            if (checkError == 6) {
                this.mArsVm.startReset(true);
            }
            switch (checkError) {
                case 1:
                    return 4;
                case 2:
                    return 5;
                case 3:
                    return 6;
                case 4:
                    return 7;
                case 5:
                    return 3;
                case 6:
                    return 8;
                case 7:
                    return 9;
                case 8:
                    return 10;
                case 9:
                    return 12;
                case 10:
                    return 11;
                default:
                    if (arsFoldState != null) {
                        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$ArsFoldState[arsFoldState.ordinal()];
                        if (i == 1) {
                            return 1;
                        }
                        if (i == 3) {
                            return 3;
                        }
                    }
                    return 2;
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.speech.CarControlSpeechModel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$ArsFoldState;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorFoldState;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$light$AtlEffect;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode;

        static {
            int[] iArr = new int[ArsFoldState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$ArsFoldState = iArr;
            try {
                iArr[ArsFoldState.UNFOLDED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$ArsFoldState[ArsFoldState.FOLDED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$ArsFoldState[ArsFoldState.MOVING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[AtlEffect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$light$AtlEffect = iArr2;
            try {
                iArr2[AtlEffect.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$light$AtlEffect[AtlEffect.Breathing.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$light$AtlEffect[AtlEffect.Speed.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$light$AtlEffect[AtlEffect.Music.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr3 = new int[DriveMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode = iArr3;
            try {
                iArr3[DriveMode.Normal.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Eco.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Sport.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.Comfort.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.OffRoad.ordinal()] = 5;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$vcu$DriveMode[DriveMode.EcoPlus.ordinal()] = 6;
            } catch (NoSuchFieldError unused13) {
            }
            int[] iArr4 = new int[LluSayHiEffect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect = iArr4;
            try {
                iArr4[LluSayHiEffect.EffectB.ordinal()] = 1;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[LluSayHiEffect.EffectC.ordinal()] = 2;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[LluSayHiEffect.EffectA.ordinal()] = 3;
            } catch (NoSuchFieldError unused16) {
            }
            int[] iArr5 = new int[AvasEffect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect = iArr5;
            try {
                iArr5[AvasEffect.SoundEffect2.ordinal()] = 1;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$avas$AvasEffect[AvasEffect.SoundEffect3.ordinal()] = 2;
            } catch (NoSuchFieldError unused18) {
            }
            int[] iArr6 = new int[ScuResponse.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse = iArr6;
            try {
                iArr6[ScuResponse.FAIL.ordinal()] = 1;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[ScuResponse.OFF.ordinal()] = 2;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[ScuResponse.ON.ordinal()] = 3;
            } catch (NoSuchFieldError unused21) {
            }
            int[] iArr7 = new int[MirrorFoldState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorFoldState = iArr7;
            try {
                iArr7[MirrorFoldState.Middle.ordinal()] = 1;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorFoldState[MirrorFoldState.Folded.ordinal()] = 2;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorFoldState[MirrorFoldState.Unfolded.ordinal()] = 3;
            } catch (NoSuchFieldError unused24) {
            }
            int[] iArr8 = new int[AsHeight.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight = iArr8;
            try {
                iArr8[AsHeight.HL2.ordinal()] = 1;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight[AsHeight.HL1.ordinal()] = 2;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight[AsHeight.LL1.ordinal()] = 3;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight[AsHeight.LL3.ordinal()] = 4;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$AsHeight[AsHeight.H0.ordinal()] = 5;
            } catch (NoSuchFieldError unused29) {
            }
        }
    }

    public int getArsWorkMode() {
        if (CarBaseConfig.getInstance().isSupportArs()) {
            ArsWorkMode arsWorkMode = this.mArsVm.getArsWorkMode();
            LogUtils.i(TAG, "getArsWorkMode:  " + arsWorkMode);
            if (arsWorkMode == ArsWorkMode.AUTO) {
                return 1;
            }
            return arsWorkMode == ArsWorkMode.MANUAL ? 2 : 0;
        }
        return 0;
    }

    private AvasEffect convertSayHiEffect2AvasEffect(LluSayHiEffect sayHiEffect) {
        AvasEffect avasEffect = AvasEffect.SoundEffect1;
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[sayHiEffect.ordinal()];
        if (i != 1) {
            return i != 2 ? avasEffect : AvasEffect.SoundEffect3;
        }
        return AvasEffect.SoundEffect2;
    }

    private LluEffect convertSayHiEffect2LluEffect(LluSayHiEffect sayHiEffect) {
        LluEffect lluEffect = LluEffect.SayHi;
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[sayHiEffect.ordinal()];
        if (i != 1) {
            return i != 2 ? lluEffect : LluEffect.SayHi3;
        }
        return LluEffect.SayHi2;
    }

    private String getSoundPath(LluSayHiEffect effect) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LluSayHiEffect[effect.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return null;
                }
                return SoundHelper.PATH_AVAS_SAYHI_1;
            }
            return SoundHelper.PATH_AVAS_SAYHI_3;
        }
        return SoundHelper.PATH_AVAS_SAYHI_2;
    }
}
