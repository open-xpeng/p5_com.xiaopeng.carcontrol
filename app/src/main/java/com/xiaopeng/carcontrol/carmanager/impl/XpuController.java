package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.xpu.CarXpuManager;
import android.car.hardware.xpu.XpuProtoMessage;
import android.car.intelligent.CarIntelligentEngineManager;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.bean.xpilot.map.CngpMapCityItem;
import com.xiaopeng.carcontrol.bean.xpilot.map.CngpMapItem;
import com.xiaopeng.carcontrol.bean.xpilot.map.CngpMapName;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IXpuController;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class XpuController extends BaseCarController<CarXpuManager, IXpuController.Callback> implements IXpuController {
    private static final String KEY_TURN_ASSISTANT_SW = "turn_assistant_sw";
    protected static final String TAG = "XpuController";
    private static final int TURN_ASSISTANT_OFF = 0;
    private static final int TURN_ASSISTANT_ON = 1;
    private CarIntelligentEngineManager mCarIntelligentManager;
    private ContentObserver mContentObserver;
    private Boolean mCngpSafeExamResult = null;
    private Boolean mXngpSafeExamResult = null;
    private final CarXpuManager.CarXpuEventCallback mCarXpuEventCallback = new CarXpuManager.CarXpuEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.XpuController.1
        public void onErrorEvent(int propertyId, int errorCode) {
        }

        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            LogUtils.i(XpuController.TAG, "onChangeEvent: " + carPropertyValue, false);
            XpuController.this.handleCarEventsUpdate(carPropertyValue);
        }
    };

    private int convertCngpState(int state) {
        if (state != 0) {
            if (state != 2) {
                return state != 3 ? 1 : 10;
            }
            return 9;
        }
        return 0;
    }

    private static int convertCrossBarriersState(int state) {
        return state != 0 ? 1 : 0;
    }

    private static int convertRaebState(int state) {
        if (state != 0) {
            if (state == 2 || state == 3 || state == 4) {
                return 2;
            }
            if (state != 5) {
                return state != 6 ? 1 : 9;
            }
            return 8;
        }
        return state;
    }

    private static int convertTrafficLightState(int state) {
        return state != 0 ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    public XpuController(Car carClient) {
        if (this.mIsMainProcess) {
            return;
        }
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$CcO25jkjOM_rr8DrYyOdTdJjSHw
            @Override // java.lang.Runnable
            public final void run() {
                XpuController.this.lambda$new$0$XpuController();
            }
        }, 2000L);
    }

    public /* synthetic */ void lambda$new$0$XpuController() {
        ContentResolver contentResolver = App.getInstance().getContentResolver();
        this.mCngpSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.CNGP_SAFE_EXAM_RESULT, false));
        LogUtils.i(TAG, "getCngpSafeExamResult: " + this.mCngpSafeExamResult);
        this.mXngpSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.XNGP_SAFE_EXAM_RESULT, false));
        LogUtils.i(TAG, "getXngpSafeExamResult: " + this.mXngpSafeExamResult);
        registerContentObserver();
    }

    private void registerContentObserver() {
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.carmanager.impl.XpuController.2
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    String lastPathSegment = uri.getLastPathSegment();
                    if (CarControl.PrivateControl.CNGP_SAFE_EXAM_RESULT_NOTIFY.equals(lastPathSegment)) {
                        XpuController.this.mCngpSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.CNGP_SAFE_EXAM_RESULT, false));
                        LogUtils.i(XpuController.TAG, "onChange: " + lastPathSegment + ", value = " + XpuController.this.mCngpSafeExamResult);
                    }
                    if (CarControl.PrivateControl.XNGP_SAFE_EXAM_RESULT_NOTIFY.equals(lastPathSegment)) {
                        XpuController.this.mXngpSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.XNGP_SAFE_EXAM_RESULT, false));
                        LogUtils.i(XpuController.TAG, "onChange: " + lastPathSegment + ", value = " + XpuController.this.mXngpSafeExamResult);
                    }
                    if (CarControl.PrivateControl.SET_NRA_STATE.equals(lastPathSegment)) {
                        int i = CarControl.PrivateControl.getInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.SET_NRA_STATE, 2);
                        LogUtils.i(XpuController.TAG, "onChange: " + lastPathSegment + ", value = " + i);
                        XpuController.this.handleNraUpdate(i);
                    }
                }
            };
        }
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.CNGP_SAFE_EXAM_RESULT_NOTIFY), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.XNGP_SAFE_EXAM_RESULT_NOTIFY), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.SET_NRA_STATE), false, this.mContentObserver);
    }

    /* loaded from: classes.dex */
    public static class XpuControllerFactory {
        public static XpuController createCarController(Car carClient) {
            return new XpuController(carClient);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.d(TAG, "Init start");
        try {
            this.mCarManager = (CarXpuManager) carClient.getCarManager(CarClientWrapper.XP_XPU_SERVICE);
            if (this.mCarManager != 0 && this.mPropertyIds != null && !this.mPropertyIds.isEmpty()) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarXpuEventCallback);
            }
            this.mCarIntelligentManager = (CarIntelligentEngineManager) carClient.getCarManager("intelligent");
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.d(TAG, "Init end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        if (this.mCarConfig.isSupportRaeb()) {
            arrayList.add(557852398);
        }
        if (this.mCarConfig.isSupportLka()) {
            arrayList.add(557856835);
        }
        if ((this.mCarConfig.isSupportSimpleSas() || this.mCarConfig.isLssCertification()) && ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess))) {
            arrayList.add(557856816);
        }
        if (this.mCarConfig.isSupportNra()) {
            arrayList.add(557856801);
        }
        if (this.mCarConfig.isSupportMemPark() && BaseFeatureOption.getInstance().isSupportEnhancedParkFunc()) {
            arrayList.add(557856788);
        }
        if (this.mCarConfig.isSupportXpu() && this.mCarConfig.isSupportXpuNedc()) {
            arrayList.add(557856775);
        }
        if (this.mCarConfig.isSupportNgp() && this.mIsMainProcess) {
            arrayList.add(557856811);
            arrayList.add(557856812);
        }
        if (this.mCarConfig.isSupportCNgp()) {
            arrayList.add(557856803);
            arrayList.add(561002569);
            arrayList.add(561002572);
            arrayList.add(557856773);
            arrayList.add(557856851);
            arrayList.add(557856852);
            arrayList.add(557856853);
        }
        if (this.mCarConfig.isSupportSpecialSas()) {
            arrayList.add(557856806);
            arrayList.add(557856807);
        }
        if (this.mCarConfig.isSupportCrossBarriers()) {
            arrayList.add(557856842);
        }
        if (this.mCarConfig.isSupportTrafficLight()) {
            arrayList.add(557856845);
        }
        return arrayList;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarXpuEventCallback);
            } catch (CarNotConnectedException e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 557852398:
                handleSignal(Integer.valueOf(convertRaebState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$dR9HxOa5D67YX9mF7OqFOgOOEog
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onRaebSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557856773:
                handleSignal(Boolean.valueOf(((Integer) getValue(value)).intValue() == 1), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$d85psdyFkCZ7NyNWew1dqNLNST4
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onXpuConnectedChanged(((Boolean) obj2).booleanValue());
                    }
                });
                return;
            case 557856775:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$3SFBCZHHFE9oxbhw8rh5BRg-14E
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onNedcStateChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557856788:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$6MKcWRq_fkmTM-Bt9ZmGbMY_q-8
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onRemoteCarCallStChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557856801:
                handleSignal(Boolean.valueOf(((Integer) getValue(value)).intValue() == 1), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$TpaOKSgHYKAr5JHssp2b2E9QPFQ
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onNraSwChanged(((Boolean) obj2).booleanValue());
                    }
                });
                return;
            case 557856803:
                handleSignal(Integer.valueOf(convertCngpState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$8wArnYgoce6_LkwX6485TaXycck
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onCityNgpSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557856806:
                handleSignal(Boolean.valueOf(((Integer) getValue(value)).intValue() == 1), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$1E9WysPd80jwDd4slQZgrWTALbw
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onSoundSwChanged(((Boolean) obj2).booleanValue());
                    }
                });
                return;
            case 557856807:
                handleSignal(Boolean.valueOf(((Integer) getValue(value)).intValue() == 1), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$MiJ30ESHw6PiTE3t8hHIZ-outHU
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onVoiceSwChanged(((Boolean) obj2).booleanValue());
                    }
                });
                return;
            case 557856811:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$LMUFPPNJnCyoI0wuLRdTMNp8Wvc
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onNgpOvertakeChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557856812:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$-B6VO4ruiMwcNXv-r9GY4BUZ5-8
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onNgpPreferLaneChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557856816:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$hQhNO6vdDvORUY62y2ptxZtRgWg
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onSimpleSasStChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557856835:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$k6mJH4Uhsvu_PNKLUei54jqiZV0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onLssSenChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557856842:
                handleSignal(Integer.valueOf(convertCrossBarriersState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$_DgEkLRqZsj0a3zBIHqtZIe-gVg
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).handleLccBarriersChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557856845:
                handleSignal(Integer.valueOf(convertTrafficLightState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$vVEabZZvHGf5hx3FInsfwzOWQew
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).handleLccTrafficLightChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557856851:
                handleSignal((Integer) getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$gtYh3dbZbW5_5SK_lgxdf_hZU2w
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onNgpCustomSpeedModeChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557856852:
                handleSignal((Integer) getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$As0SnZnSYEjStGqpccT_6q2he5Q
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onNgpCustomSpeedKphChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557856853:
                handleSignal((Integer) getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$JBRFSRFusk7ZrTZa2YdRLf-KAU8
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IXpuController.Callback) obj).onNgpCustomSpeedPercentChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 561002569:
                handleCityNgpMapCtrlResponse((byte[]) getValue(value));
                return;
            case 561002572:
                handleCityNgpMapFinishNotify((byte[]) getValue(value));
                return;
            default:
                LogUtils.w(TAG, "handle unknown event: " + value);
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getRaebState() {
        if (this.mCarConfig.isSupportRaeb()) {
            int intValue = ((Integer) getValue(557852398, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$ZT5cPeF6u03BhxPHnP1jTo70a5g
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
                public final Object apply(Object obj) {
                    return XpuController.this.lambda$getRaebState$1$XpuController((Void) obj);
                }
            }, 2)).intValue();
            LogUtils.i(TAG, "getRaebState: " + intValue, false);
            return convertRaebState(intValue);
        }
        return 0;
    }

    public /* synthetic */ Integer lambda$getRaebState$1$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getRaebSwitchStatus());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setRaebState(final boolean enable) {
        if (this.mCarConfig.isSupportRaeb()) {
            LogUtils.i(TAG, "setRaebState: " + enable);
            setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$rlGKkGO0i9lAyWDXLcEFi7bIfCE
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
                public final void accept(Object obj) {
                    XpuController.this.lambda$setRaebState$2$XpuController(enable, (Void) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setRaebState$2$XpuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setRaebSwitchStatus(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getLssSensitivity() {
        if (this.mCarConfig.isSupportLka()) {
            int intValue = ((Integer) getValue(557856835, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$7NXQkVGNjzV4pT4i0E5K2zJAJlY
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
                public final Object apply(Object obj) {
                    return XpuController.this.lambda$getLssSensitivity$3$XpuController((Void) obj);
                }
            }, 0)).intValue();
            LogUtils.i(TAG, "getLssSensitivity: " + intValue, false);
            return intValue;
        }
        return 0;
    }

    public /* synthetic */ Integer lambda$getLssSensitivity$3$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getLssSensitivitySwitchStatus());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setLssSensitivity(final int value) {
        if (this.mCarConfig.isSupportLka()) {
            LogUtils.i(TAG, "setLssSensitivity: " + value);
            setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$y9N-G1MXdpLCyr8pfg2pZOPVLSk
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
                public final void accept(Object obj) {
                    XpuController.this.lambda$setLssSensitivity$4$XpuController(value, (Void) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setLssSensitivity$4$XpuController(final int value, Void t) throws Exception {
        this.mCarManager.setLssSensitivitySwitchStatus(value);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getSimpleSasSw() {
        int intValue = ((Integer) getValue(557856816, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$wW-NpyqDhl40aN0ZmJtcMDI3hDQ
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getSimpleSasSw$5$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getSimpleSasSw: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getSimpleSasSw$5$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getISLCDriverSet());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setSimpleSasSw(final int mode) {
        LogUtils.i(TAG, "setSimpleSasSw: " + mode);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$akOYH1m7VzksV8_wwCMjHeECrzM
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setSimpleSasSw$6$XpuController(mode, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setSimpleSasSw$6$XpuController(final int mode, Void t) throws Exception {
        this.mCarManager.setISLCDriverSet(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getNraState() {
        int i;
        try {
            i = this.mCarIntelligentManager.getCarDrivingSceneNRALevel();
        } catch (Error | Exception e) {
            LogUtils.e(TAG, "getNraState failed: " + e.getMessage(), false);
            i = 0;
        }
        LogUtils.i(TAG, "getNraState: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setNraState(int state) {
        try {
            this.mCarIntelligentManager.setCarDrivingSceneNRALevel(state);
            if (this.mCarConfig.isSupportUnity3D()) {
                CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.SET_NRA_STATE, state);
                if (this.mIsMainProcess) {
                    LogUtils.i(TAG, "setNraState mIsMainProcess: " + state, false);
                    this.mDataSync.setNraState(state);
                } else {
                    LogUtils.i(TAG, "setNraState mIsUnityProcess: " + state, false);
                    handleNraUpdate(state);
                }
            } else {
                this.mDataSync.setNraState(state);
                handleNraUpdate(state);
            }
        } catch (Error | Exception e) {
            LogUtils.e(TAG, "setNraState failed: " + e.getMessage(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNraUpdate(int state) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IXpuController.Callback) it.next()).onNraStateChanged(state);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public boolean getNraSw() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$Xv5K2IeRhq342JtWqvTNbE53RY0
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getNraSw$7$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getNraSw: " + intValue, false);
        return intValue == 1;
    }

    public /* synthetic */ Integer lambda$getNraSw$7$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNraSwitchStatus());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setNraSwEnable(final boolean enable) {
        LogUtils.i(TAG, "setNraSwEnable: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$jCA-4SKvrJEXvjdiOVRBQ9_uftk
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setNraSwEnable$8$XpuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setNraSwEnable$8$XpuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setNraSwitchStatus(enable ? 1 : 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0019, code lost:
        if (android.provider.Settings.System.getInt(com.xiaopeng.carcontrol.App.getInstance().getContentResolver(), com.xiaopeng.carcontrol.carmanager.impl.XpuController.KEY_TURN_ASSISTANT_SW, 0) == 1) goto L5;
     */
    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getTurnAssistantSw() {
        /*
            r4 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r0 = r4.mCarConfig
            boolean r0 = r0.isSupportTurnAssist()
            r1 = 0
            if (r0 == 0) goto L1c
            com.xiaopeng.carcontrol.App r0 = com.xiaopeng.carcontrol.App.getInstance()
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r2 = "turn_assistant_sw"
            int r0 = android.provider.Settings.System.getInt(r0, r2, r1)
            r2 = 1
            if (r0 != r2) goto L1c
            goto L1d
        L1c:
            r2 = r1
        L1d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "getTurnAssistantSw: "
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            java.lang.String r3 = "XpuController"
            com.xiaopeng.carcontrol.util.LogUtils.i(r3, r0, r1)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.XpuController.getTurnAssistantSw():boolean");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setTurnAssistantEnable(boolean enable) {
        if (this.mCarConfig.isSupportTurnAssist()) {
            LogUtils.i(TAG, "setTurnAssistantEnable: " + enable);
            Settings.System.putInt(App.getInstance().getContentResolver(), KEY_TURN_ASSISTANT_SW, enable ? 1 : 0);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getRemoteCarCallSw() {
        int intValue = ((Integer) getValue(557856788, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$X571hZZi3eg0zgvteJbUk87SIlA
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getRemoteCarCallSw$9$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.debug(TAG, "getRemoteCarCallSw: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getRemoteCarCallSw$9$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getApRemoteSw());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setRemoteCalCallSw(final boolean enable) {
        LogUtils.i(TAG, "setRemoteCalCallSw: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$lvYNr4D4z3lKURQwVHMAm_R9Uww
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setRemoteCalCallSw$10$XpuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setRemoteCalCallSw$10$XpuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setApRemoteSw(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setNedcSwitch(final boolean active) {
        LogUtils.i(TAG, "setNedcSwitch: " + active);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$uK1Xf094oYY8koJ0mT4aR_uvSd4
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setNedcSwitch$11$XpuController(active, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setNedcSwitch$11$XpuController(final boolean active, Void t) throws Exception {
        this.mCarManager.setNedcSwitch(active ? 1 : 0);
        Settings.System.putInt(App.getInstance().getContentResolver(), IXpuController.SETTING_KEY_NEDC_SW, active ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getNedcSwitchStatus() {
        int intValue = ((Integer) getValue(557856775, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$xBbx41MZFbvy__qL1FZzfbfPv2w
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getNedcSwitchStatus$12$XpuController((Void) obj);
            }
        }, 2)).intValue();
        LogUtils.debug(TAG, "getNedcSwitchStatus: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getNedcSwitchStatus$12$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNedcSwitchStatus());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public boolean getCngpSafeExamResult(String uid) {
        if (this.mIsMainProcess) {
            if (uid == null) {
                return this.mDataSync.getCngpSafeExamResult();
            }
            return this.mDataSync.getCngpSafeExamResult(uid);
        }
        LogUtils.i(TAG, "getCngpSafeExamResult: " + this.mCngpSafeExamResult + " uid:" + uid);
        if (uid == null) {
            return CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.CNGP_SAFE_EXAM_RESULT, false);
        }
        return this.mCngpSafeExamResult.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setCngpSafeExamResult(String uid, boolean result) {
        if (this.mIsMainProcess) {
            if (uid == null) {
                this.mDataSync.setCngpSafeExamResult(result);
            } else {
                this.mDataSync.setCngpSafeExamResult(uid, result);
            }
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.CNGP_SAFE_EXAM_RESULT_NOTIFY, result);
            return;
        }
        LogUtils.i(TAG, "setCngpSafeExamResult: " + result);
        this.mCngpSafeExamResult = Boolean.valueOf(result);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.CNGP_SAFE_EXAM_RESULT, result);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public boolean getXngpSafeExamResult(String uid) {
        if (this.mIsMainProcess) {
            if (uid == null) {
                return this.mDataSync.getXngpSafeExamResult();
            }
            return this.mDataSync.getXngpSafeExamResult(uid);
        }
        LogUtils.i(TAG, "getXngpSafeExamResult: " + this.mXngpSafeExamResult + " uid:" + uid);
        if (uid == null) {
            return CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.XNGP_SAFE_EXAM_RESULT, false);
        }
        return this.mXngpSafeExamResult.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setXngpSafeExamResult(String uid, boolean result) {
        if (this.mIsMainProcess) {
            if (uid == null) {
                this.mDataSync.setXngpSafeExamResult(result);
            } else {
                this.mDataSync.setXngpSafeExamResult(uid, result);
            }
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.XNGP_SAFE_EXAM_RESULT_NOTIFY, result);
            return;
        }
        LogUtils.i(TAG, "setXngpSafeExamResult: " + result);
        this.mXngpSafeExamResult = Boolean.valueOf(result);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.XNGP_SAFE_EXAM_RESULT, result);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setCityNgpSw(final boolean enable) {
        LogUtils.i(TAG, "setCityNgpSw: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$_QPGAMi2DDfMnta-sivnR1o6gyg
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setCityNgpSw$13$XpuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setCityNgpSw$13$XpuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setCityNgpSwitchStatus(enable ? 1 : 0);
        if (this.mIsMainProcess) {
            this.mDataSync.setCityNgpSw(enable);
        } else {
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SET_CITY_NGP_SW, enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getCityNgpState() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$mXsPLFTXhoaoCVz71kfFlAO0TSE
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getCityNgpState$14$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getCityNgpState: " + intValue, false);
        return convertCngpState(intValue);
    }

    public /* synthetic */ Integer lambda$getCityNgpState$14$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getCityNgpSwitchStatus());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getNgpCustomSpeedMode() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$qOrJYdCJU5Idm756nItHa_5rf8A
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getNgpCustomSpeedMode$15$XpuController((Void) obj);
            }
        }, 1)).intValue();
        LogUtils.i(TAG, "getNgpCustomSpeedMode: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getNgpCustomSpeedMode$15$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNgpCustomSpeedSwitchStatus());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setNgpCustomSpeedMode(final int mode) {
        LogUtils.i(TAG, "setNgpCustomSpeedMode: " + mode);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$ShN2uMhrCQyfSLOCsw0awaQLw8Y
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setNgpCustomSpeedMode$16$XpuController(mode, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setNgpCustomSpeedMode$16$XpuController(final int mode, Void t) throws Exception {
        this.mCarManager.setNgpCustomSpeedSwitchStatus(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getNgpCustomSpeedKph() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$oHPbfVk95IgkQn89s81flmph1UM
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getNgpCustomSpeedKph$17$XpuController((Void) obj);
            }
        }, 3)).intValue();
        LogUtils.i(TAG, "getNgpCustomSpeedKph: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getNgpCustomSpeedKph$17$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNgpCustomSpeedCountLever());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setNgpCustomSpeedKph(final int value) {
        LogUtils.i(TAG, "setNgpCustomSpeedKph: " + value);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$py4TVeAa6InslYbaBqevDT4cVJ4
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setNgpCustomSpeedKph$18$XpuController(value, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setNgpCustomSpeedKph$18$XpuController(final int value, Void t) throws Exception {
        this.mCarManager.setNgpCustomSpeedCountLever(value);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getNgpCustomSpeedPercent() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$9V8f49DNJUvkMgqcsBNRS1dT_pQ
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getNgpCustomSpeedPercent$19$XpuController((Void) obj);
            }
        }, 3)).intValue();
        LogUtils.i(TAG, "getNgpCustomSpeedPercent: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getNgpCustomSpeedPercent$19$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNgpCustomSpeedCountPercent());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setNgpCustomSpeedPercent(final int value) {
        LogUtils.i(TAG, "setNgpCustomSpeedPercent: " + value);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$lUmdAWtwWsEv3iVutNziSlS7b5E
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setNgpCustomSpeedPercent$20$XpuController(value, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setNgpCustomSpeedPercent$20$XpuController(final int value, Void t) throws Exception {
        this.mCarManager.setNgpCustomSpeedCountPercent(value);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getNgpOvertakeMode() {
        int intValue = ((Integer) getValue(557856811, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$WGIsEap35enRAHVuhs-8VyVxvxE
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getNgpOvertakeMode$21$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getNgpOvertakeMode: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getNgpOvertakeMode$21$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNgpULCSwMode());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setNgpOvertakeMode(final int mode) {
        LogUtils.i(TAG, "setNgpOvertakeMode: " + mode);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$ODauUc-TzHLh609hS32m6HvsDzo
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setNgpOvertakeMode$22$XpuController(mode, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setNgpOvertakeMode$22$XpuController(final int mode, Void t) throws Exception {
        this.mCarManager.setNgpULCSwMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getNgpPreferLaneCfg() {
        int intValue = ((Integer) getValue(557856812, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$miBfVj-6O7rRlzcpK3mKEbUjC28
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getNgpPreferLaneCfg$23$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getNgpPreferLaneCfg: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getNgpPreferLaneCfg$23$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNgpOptimalLaneSw());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setNgpPreferLaneCfg(final int mode) {
        LogUtils.i(TAG, "setNgpPreferLaneCfg: " + mode);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$5WeNb3i9xZMFbkmTvz7Kz-V_Jx0
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setNgpPreferLaneCfg$24$XpuController(mode, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setNgpPreferLaneCfg$24$XpuController(final int mode, Void t) throws Exception {
        this.mCarManager.setNgpOptimalLaneSw(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public boolean getSoundSw() {
        int intValue = ((Integer) getValue(557856806, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$D8Sss2YIA4LQtLk8sQJB_fXqs4M
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getSoundSw$25$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getSoundSw: " + intValue, false);
        return intValue == 1;
    }

    public /* synthetic */ Integer lambda$getSoundSw$25$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getSlifSoundStatus());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setSoundSw(final boolean enable) {
        LogUtils.i(TAG, "setSoundSw: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$kSSkZ2DIigQMgqUuIHWepqcJ6hM
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setSoundSw$26$XpuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setSoundSw$26$XpuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setSlifSoundStatus(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public boolean getVoiceSw() {
        int intValue = ((Integer) getValue(557856807, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$nM7p1-zHlqmLz-oMAHbWg4fjgT8
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getVoiceSw$27$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getVoiceSw: " + intValue, false);
        return intValue == 1;
    }

    public /* synthetic */ Integer lambda$getVoiceSw$27$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getSlwfVoiceStatus());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setVoiceSw(final boolean enable) {
        LogUtils.i(TAG, "setVoiceSw: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$SFR3n5F4-2Lj9ac0axiQ7guGbUY
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setVoiceSw$28$XpuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setVoiceSw$28$XpuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setSlwfVoiceStatus(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getAsLockScenario() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$vZfb-QT05Zqc7VEmdNpc9ht4FoY
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getAsLockScenario$29$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getAsLockScenario: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getAsLockScenario$29$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getAsLockScenario());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getAsTargetMinHeightRequest() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$vlaNX10c1fzMHcBzelz3YF6cLQo
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getAsTargetMinHeightRequest$30$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getAsTargetMinHeightRequest: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getAsTargetMinHeightRequest$30$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getAsTargetMinimumHeightRequest());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getAsTargetMaxHeightRequest() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$XoCQvFyI29vAPCkkUuGn7VbQQTQ
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getAsTargetMaxHeightRequest$31$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getAsTargetMaxHeightRequest: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getAsTargetMaxHeightRequest$31$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getAsTargetMaximumHeightRequest());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getCngpModeStatus() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$JCl3mlSwIFwmAHMep3F9rHzRxXg
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getCngpModeStatus$32$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getCngpModeStatus: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getCngpModeStatus$32$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNgpModeIndexMode());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getLccCrossBarriersSw() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$W_72ptIeDCDTod2MQTy1TsjN_5Q
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getLccCrossBarriersSw$33$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getLccCrossBarriersSw: " + intValue, false);
        return convertCrossBarriersState(intValue);
    }

    public /* synthetic */ Integer lambda$getLccCrossBarriersSw$33$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getLLCCDetourSw());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setLccCrossBarriersSw(final boolean enable) {
        LogUtils.i(TAG, "setLccCrossBarriersSw: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$h_L9c5zuEBY-5dymaqIU4d8_zGU
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setLccCrossBarriersSw$34$XpuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setLccCrossBarriersSw$34$XpuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setLLCCDetourSw(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public int getLccTrafficLightSw() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$tjcGHUvqw-q4BW7JXILt5V1Tt9A
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getLccTrafficLightSw$35$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getLccTrafficLightSw: " + intValue, false);
        return convertTrafficLightState(intValue);
    }

    public /* synthetic */ Integer lambda$getLccTrafficLightSw$35$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getLccLStraightSw());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setLccTrafficLightSw(final boolean enable) {
        LogUtils.i(TAG, "setLccTrafficLightSw: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$ZdBRDZTK4riLKWY5HOBaXVLC1yY
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setLccTrafficLightSw$36$XpuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setLccTrafficLightSw$36$XpuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setLccLStraightSw(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void setMrrEnable(final boolean enable) {
        LogUtils.i(TAG, "setMrrEnable: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$ds-pQlHCBI-pSVd7kFg0ysMOedE
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                XpuController.this.lambda$setMrrEnable$37$XpuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setMrrEnable$37$XpuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setRadarEmissionSwitchStatus(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void getAllCities() {
        LogUtils.i(TAG, "getAllCities", false);
        try {
            this.mCarManager.CNGPCityMapCtrlRequest(20, 0);
        } catch (Exception e) {
            LogUtils.e(TAG, "getAllCities failed: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void downloadCity(int id) {
        LogUtils.i(TAG, "downloadCity " + id, false);
        try {
            this.mCarManager.CNGPCityMapCtrlRequest(id, 2);
        } catch (Exception e) {
            LogUtils.e(TAG, "downloadCity failed: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public void deleteCity(int id) {
        LogUtils.i(TAG, "deleteCity " + id, false);
        try {
            this.mCarManager.CNGPCityMapCtrlRequest(id, 1);
        } catch (Exception e) {
            LogUtils.e(TAG, "deleteCity failed: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController
    public boolean getXpuConnected() {
        int intValue = ((Integer) getValue(0, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$XpuController$H46rQ0LZWfBebl8RkgHZrJ5HaK0
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return XpuController.this.lambda$getXpuConnected$38$XpuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getXpuConnected " + intValue, false);
        return intValue == 1;
    }

    public /* synthetic */ Integer lambda$getXpuConnected$38$XpuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getXpuConnectionStatus());
    }

    private void handleCityNgpMapCtrlResponse(byte[] callback_data) {
        LogUtils.i(TAG, "handleCityNgpMapCtrlResponse callback", false);
        try {
            XpuProtoMessage.CNGPCityMapResponse CNGPCityMapCtrlResponse = CarXpuManager.CNGPCityMapCtrlResponse(callback_data);
            if (CNGPCityMapCtrlResponse == null || CNGPCityMapCtrlResponse.getErrorCode() != 0) {
                return;
            }
            List<CngpMapItem> convertToCngpMapItemList = convertToCngpMapItemList(CNGPCityMapCtrlResponse);
            synchronized (this.mCallbackLock) {
                Iterator it = this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((IXpuController.Callback) it.next()).onCngpMapCtrlResponse(convertToCngpMapItemList);
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "CNGPCityMapCtrlResponse failed: " + e.getMessage(), false);
        }
    }

    private void handleCityNgpMapFinishNotify(byte[] callback_data) {
        LogUtils.i(TAG, "handleCityNgpMapFinishNotify callback", false);
        try {
            XpuProtoMessage.CNGPCityMapFinNotify CNGPCityMapFinishNotify = CarXpuManager.CNGPCityMapFinishNotify(callback_data);
            if (CNGPCityMapFinishNotify != null) {
                LogUtils.i(TAG, "handleCityNgpMapFinishNotify: " + CNGPCityMapFinishNotify.getCityCode() + " " + CNGPCityMapFinishNotify.getResult(), false);
                synchronized (this.mCallbackLock) {
                    Iterator it = this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        IXpuController.Callback callback = (IXpuController.Callback) it.next();
                        int result = CNGPCityMapFinishNotify.getResult();
                        int i = -1;
                        if (result == 0) {
                            i = 2;
                        } else if (result == 1) {
                            i = 3;
                        }
                        callback.onCngpMapFinishNotify(CNGPCityMapFinishNotify.getCityCode(), i);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "CNGPCityMapFinishNotify failed: " + e.getMessage(), false);
        }
    }

    private List<CngpMapItem> convertToCngpMapItemList(XpuProtoMessage.CNGPCityMapResponse response) {
        CngpMapCityItem cngpMapCityItem;
        ArrayList arrayList = new ArrayList(response.getCitiesCount());
        for (XpuProtoMessage.CNGPCityMapResponse.CityResponse cityResponse : response.getCitiesList()) {
            if (cityResponse.getMapProgress() < 0) {
                cngpMapCityItem = new CngpMapCityItem(cityResponse.getCityCode(), CngpMapName.convertIdToName(cityResponse.getCityCode()), 3, cityResponse.getMapProgress());
            } else {
                cngpMapCityItem = new CngpMapCityItem(cityResponse.getCityCode(), CngpMapName.convertIdToName(cityResponse.getCityCode()), cityResponse.getMapStatus(), cityResponse.getMapProgress());
            }
            LogUtils.i(TAG, cngpMapCityItem.toString(), false);
            arrayList.add(cngpMapCityItem);
        }
        return arrayList;
    }
}
