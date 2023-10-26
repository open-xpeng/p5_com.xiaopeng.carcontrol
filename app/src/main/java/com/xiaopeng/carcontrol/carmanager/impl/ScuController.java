package com.xiaopeng.carcontrol.carmanager.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarEcuManager;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.scu.CarScuManager;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.BusinessConstant;
import com.xiaopeng.carcontrol.carmanager.BaseCarController;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IScuController;
import com.xiaopeng.carcontrol.carmanager.impl.oldarch.ScuOldController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class ScuController extends BaseCarController<CarScuManager, IScuController.Callback> implements IScuController {
    private static final String REMOTE_CAMERA_SW = "remote_camera_sw";
    protected static final String TAG = "ScuController";
    private ContentObserver mContentObserver;
    private final CarScuManager.CarScuEventCallback mCarScuEventCallback = new CarScuManager.CarScuEventCallback() { // from class: com.xiaopeng.carcontrol.carmanager.impl.ScuController.1
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            int propertyId = carPropertyValue.getPropertyId();
            if (propertyId != 557852187 && propertyId != 557852212) {
                LogUtils.i(ScuController.TAG, "onChangeEvent: " + carPropertyValue, false);
            }
            ScuController.this.handleCarEventsUpdate(carPropertyValue);
        }

        public void onErrorEvent(int propertyId, int errorCode) {
            if (errorCode == 6) {
                ScuController.this.handleLossEventsUpdate(propertyId);
            }
        }
    };
    private Boolean mIsLccVideoWatched = null;
    private Boolean mLccSafeExamResult = null;
    private Boolean mApaSafeExamResult = null;
    private Boolean mIsMemParkVideoWatched = null;
    private Boolean mNgpSafeExamResult = null;
    private Boolean mMemParkSafeExamResult = null;
    private Boolean mSuperVpaSafeExamResult = null;
    private Boolean mSuperLccSafeExamResult = null;
    private Integer mIslaMode = null;

    private int convertAccState(int state) {
        if (state != 0) {
            return (state == 8 || state == 9) ? 2 : 1;
        }
        return 0;
    }

    private int convertAccStateForMirror(int state) {
        if (state == 0 || state == 1 || state == 2) {
            return 0;
        }
        return (state == 8 || state == 9) ? 2 : 1;
    }

    private static int convertApaState(int state) {
        if (state != 0) {
            return state != 2 ? 1 : 9;
        }
        return 0;
    }

    private static int convertDsmState(int state) {
        if (state != 0) {
            return (state == 4 || state == 5) ? 2 : 1;
        }
        return 0;
    }

    private static int convertElkState(int state) {
        if (state != 0) {
            if (state == 4 || state == 5) {
                return 2;
            }
            return state != 6 ? 1 : 9;
        }
        return 0;
    }

    private static int convertIhbState(int state) {
        if (state != 0) {
            return (state == 4 || state == 5) ? 2 : 1;
        }
        return 0;
    }

    private static int convertKeyParkState(int state) {
        if (state != 0) {
            return state != 2 ? 1 : 9;
        }
        return 0;
    }

    private int convertLccWorkSt(int state) {
        return (state == 5 || state == 6 || state == 7 || state == 8) ? 1 : 0;
    }

    private static int convertLkaState(int state) {
        if (state != 4) {
            switch (state) {
                case 8:
                case 9:
                    return 2;
                case 10:
                    return 9;
                default:
                    return 1;
            }
        }
        return 0;
    }

    private static int convertMemoryParkState(int state) {
        if (state != 1) {
            if (state != 4) {
                return state != 5 ? 0 : 10;
            }
            return 9;
        }
        return 1;
    }

    protected static int convertNewLdwState(int state) {
        if (state != 4) {
            return (state == 8 || state == 9) ? 2 : 1;
        }
        return 0;
    }

    private static int convertNgpState(int state) {
        if (state != 0) {
            if (state != 2) {
                if (state != 3) {
                    return (state == 22 || state == 23) ? 2 : 1;
                }
                return 10;
            }
            return 9;
        }
        return 0;
    }

    private static int convertRcwState(int state) {
        if (state == 0 || state == 1 || state == 2) {
            return state;
        }
        return 1;
    }

    private static int convertRemindModeValue(int mode) {
        return mode != 1 ? 1 : 0;
    }

    private static int convertRemindModeValueFromSave(int mode) {
        return mode != 0 ? 0 : 1;
    }

    protected int convertBsdState(int state) {
        if (state == 0 || state == 1 || state == 2) {
            return state;
        }
        return 1;
    }

    protected int convertDowState(int state) {
        if (state == 0 || state == 1 || state == 2) {
            return state;
        }
        return 1;
    }

    protected int convertIslcState(int state) {
        if (state != 0) {
            return (state == 6 || state == 7) ? 2 : 1;
        }
        return 0;
    }

    protected int convertLdwState(int state) {
        if (state != 0) {
            if (state == 4 || state == 5) {
                return 2;
            }
            return state != 6 ? 1 : 9;
        }
        return 0;
    }

    protected int convertRctaState(int state) {
        if (state == 0 || state == 1 || state == 2) {
            return state;
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initXuiManager() {
    }

    public ScuController(Car carClient) {
        if (this.mIsMainProcess) {
            return;
        }
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$Q5FR8L0sPaIrWiPKFjpcXiO8Y-g
            @Override // java.lang.Runnable
            public final void run() {
                ScuController.this.lambda$new$0$ScuController();
            }
        }, 2000L);
    }

    public /* synthetic */ void lambda$new$0$ScuController() {
        ContentResolver contentResolver = App.getInstance().getContentResolver();
        if (this.mCarConfig.isSupportXPilotSafeExam()) {
            this.mLccSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.LCC_SAFE_EXAM_RESULT, false));
            LogUtils.i(TAG, "getLccSafeExamResult: " + this.mLccSafeExamResult);
        } else {
            this.mIsLccVideoWatched = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.LCC_VIDEO_WATCH_STATE, false));
        }
        this.mIsMemParkVideoWatched = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.MEM_PARK_VIDEO_WATCH_STATE, false));
        this.mNgpSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.NGP_SAFE_EXAM_RESULT, false));
        this.mMemParkSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.MEM_PARK_SAFE_EXAM_RESULT, false));
        this.mApaSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.APA_SAFE_EXAM_RESULT, false));
        this.mSuperVpaSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.SUPER_VPA_SAFE_EXAM_RESULT, false));
        this.mSuperLccSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(contentResolver, CarControl.PrivateControl.SUPER_LCC_SAFE_EXAM_RESULT, false));
        LogUtils.i(TAG, "getNgpSafeExamResult: " + this.mNgpSafeExamResult);
        LogUtils.i(TAG, "getMemParkSafeExamResult: " + this.mMemParkSafeExamResult);
        LogUtils.i(TAG, "getApaSafeExamResult: " + this.mApaSafeExamResult);
        LogUtils.i(TAG, "getSuperVpaSafeExamResult: " + this.mSuperVpaSafeExamResult);
        LogUtils.i(TAG, "getSuperLccSafeExamResult: " + this.mSuperLccSafeExamResult);
        this.mIslaMode = Integer.valueOf(CarControl.PrivateControl.getInt(contentResolver, CarControl.PrivateControl.XPILOT_ISLA_SW, 0));
        registerContentObserver();
    }

    /* loaded from: classes.dex */
    public static class ScuControllerFactory {
        public static ScuController createCarController(Car carClient) {
            if (CarBaseConfig.getInstance().isNewScuArch()) {
                return new ScuController(carClient);
            }
            return new ScuOldController(carClient);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        LogUtils.i(TAG, "Init start");
        try {
            this.mCarManager = (CarScuManager) carClient.getCarManager(CarClientWrapper.XP_SCU_SERVICE);
            if (this.mCarManager != 0) {
                this.mCarManager.registerPropCallback(this.mPropertyIds, this.mCarScuEventCallback);
            }
        } catch (CarNotConnectedException e) {
            LogUtils.e(TAG, (String) null, (Throwable) e, false);
        }
        LogUtils.i(TAG, "Init end");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        ArrayList arrayList = new ArrayList();
        addExtPropertyIds(arrayList);
        return arrayList;
    }

    protected void addExtPropertyIds(List<Integer> propertyIds) {
        boolean isSupportSdc = CarBaseConfig.getInstance().isSupportSdc();
        if ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess)) {
            propertyIds.add(557852161);
            if (this.mCarConfig.isSupportFcwSen()) {
                propertyIds.add(557852413);
            }
            if (this.mCarConfig.isSupportXpuDomainCtrl()) {
                propertyIds.add(557917935);
                propertyIds.add(557917937);
            } else {
                propertyIds.add(557852165);
                propertyIds.add(557852168);
            }
            if (this.mCarConfig.isSupportIslc()) {
                propertyIds.add(557852173);
            }
            if (this.mCarConfig.isSupportIsla()) {
                propertyIds.add(557852173);
                if (this.mCarConfig.isNewScuArch()) {
                    propertyIds.add(557852385);
                    propertyIds.add(557852381);
                    propertyIds.add(557852380);
                } else {
                    propertyIds.add(557852378);
                }
            }
            if (this.mCarConfig.isSupportRcw()) {
                if (this.mCarConfig.isSupportXpuDomainCtrl()) {
                    propertyIds.add(557917936);
                } else {
                    propertyIds.add(557852167);
                }
            }
            if (this.mCarConfig.isNewScuArch()) {
                if (this.mCarConfig.isSupportXpuDomainCtrl()) {
                    propertyIds.add(557917938);
                } else {
                    propertyIds.add(557852166);
                }
            } else {
                propertyIds.add(557852202);
            }
            if (BaseFeatureOption.getInstance().isNewLssSignal()) {
                propertyIds.add(557852412);
            } else {
                if (this.mCarConfig.isSupportOldLka()) {
                    propertyIds.add(557852371);
                } else if (!BaseFeatureOption.getInstance().isSignalRegisterDynamically()) {
                    propertyIds.add(557852162);
                }
                if (this.mCarConfig.isSupportLka() && !this.mCarConfig.isSupportOldLka()) {
                    propertyIds.add(557852302);
                } else if (this.mCarConfig.isSupportLka() && this.mCarConfig.isSupportOldLka()) {
                    propertyIds.add(557852372);
                }
                if (this.mCarConfig.isSupportElk()) {
                    propertyIds.add(557852333);
                }
            }
            if (this.mCarConfig.isSupportAutoPark()) {
                propertyIds.add(557852184);
                if (BaseFeatureOption.getInstance().isSupportEnhancedParkFunc()) {
                    propertyIds.add(557852186);
                    propertyIds.add(557852183);
                }
            }
            if (this.mCarConfig.isSupportMemPark()) {
                propertyIds.add(557852369);
            }
            propertyIds.add(557852174);
            propertyIds.add(557852169);
            if (isSupportSdc) {
                propertyIds.add(557852404);
                propertyIds.add(557852405);
            }
        }
        if (this.mIsMainProcess) {
            propertyIds.add(557852212);
            propertyIds.add(557852240);
            propertyIds.add(557852187);
            if (isSupportSdc) {
                propertyIds.add(557852329);
                if (BaseFeatureOption.getInstance().isSupportSdcNarrowSpaceTips()) {
                    propertyIds.add(557852330);
                }
                propertyIds.add(557917851);
                propertyIds.add(557852335);
                propertyIds.add(557852334);
            }
        }
        if (this.mCarConfig.isSupportXpu()) {
            propertyIds.add(557852318);
            propertyIds.add(557852175);
            if (this.mIsMainProcess) {
                propertyIds.add(557852163);
                if (this.mCarConfig.isSupportXpuDomainCtrl()) {
                    propertyIds.add(557852320);
                } else {
                    propertyIds.add(557852319);
                }
                propertyIds.add(557852314);
                propertyIds.add(557852322);
                propertyIds.add(557852323);
            }
        }
        if (this.mCarConfig.isSupportDsmCamera() && ((this.mCarConfig.isSupportUnity3D() && !this.mIsMainProcess) || (!this.mCarConfig.isSupportUnity3D() && this.mIsMainProcess))) {
            propertyIds.add(557852395);
        }
        if (this.mCarConfig.isSupportSpecialSas()) {
            propertyIds.add(557852173);
            propertyIds.add(557852385);
            propertyIds.add(557852380);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void disconnect() {
        if (this.mCarManager != 0) {
            try {
                this.mCarManager.unregisterPropCallback(this.mPropertyIds, this.mCarScuEventCallback);
            } catch (CarNotConnectedException e) {
                LogUtils.e(TAG, (String) null, (Throwable) e, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void handleCarEventsUpdate(final CarPropertyValue<?> value) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$Ut2RZzkAXrAJrhu8TSVMjkFt3nk
            @Override // java.lang.Runnable
            public final void run() {
                ScuController.this.lambda$handleCarEventsUpdate$1$ScuController(value);
            }
        });
    }

    public /* synthetic */ void lambda$handleCarEventsUpdate$1$ScuController(final CarPropertyValue value) {
        this.mCarPropertyMap.put(Integer.valueOf(value.getPropertyId()), value);
        handleEventsUpdate(value);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void handleEventsUpdate(CarPropertyValue<?> value) {
        switch (value.getPropertyId()) {
            case 557852161:
                handleSignal(Integer.valueOf(convertFcwState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$uwYVomPz7i430yscNAT7uHGD1dk
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onFcwSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852162:
                handleSignal(Integer.valueOf(convertLdwState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$9-rsAFKtqP2DHPloHjGvjbEdfPc
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onLdwSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852163:
                handleSignal(Integer.valueOf(convertIhbState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$qEFhujhW7jcZXU69o9ACJ7V-4_s
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onIhbStateChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852165:
                handleSignal(Integer.valueOf(convertBsdState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$SJaqDoDJajUHJ9o0oDquC1S1jTI
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onBsdStateChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852166:
                handleSignal(Integer.valueOf(convertRctaState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$sYIaL9_uF6nTX3VlcePzkVnxIDk
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onRctaSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852167:
                break;
            case 557852168:
                handleSignal(Integer.valueOf(convertDowState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$32IUIb065A8inUzi36nnZBxSdT4
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onDowSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852169:
                handleSignal(Integer.valueOf(convertLcaState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$oiDnkgTtDegHsU11ifY61jsgjkA
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onLcsStateChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852173:
                if (this.mCarConfig.isSupportIsla()) {
                    if (this.mCarConfig.isNewScuArch()) {
                        handleSignal(Integer.valueOf(convertIslaState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$Bp0ocjugrm34v2_Zx0BjMw5WqoM
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ((IScuController.Callback) obj).onIslaStateChanged(((Integer) obj2).intValue());
                            }
                        });
                        return;
                    } else {
                        handleSignal(Integer.valueOf(convertSlaState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$Vs5BrKFWIyuf6_6aXf0RP3Jwm54
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ((IScuController.Callback) obj).onSlaStateChanged(((Integer) obj2).intValue());
                            }
                        });
                        return;
                    }
                }
                handleSignal(Integer.valueOf(convertIslcState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$uiW5Lhz7TSy3d572HZgE3PC8JOI
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onIslcSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852174:
                handleSignal(Integer.valueOf(convertLccState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$rCotx7YN3eSrSMvJl7EsAMtmls8
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onLccStateChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852175:
                handleSignal(Integer.valueOf(convertNgpState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$7sLr2iOebHKujhBpCmg7gu87NwU
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onNgpStateChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852183:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$DHknXvW8U4Eh9_spgXgOlP1WzSU
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onPhoneParkSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852184:
                handleSignal(Integer.valueOf(convertApaState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$RmpY4bwPYZiv3qG_SShWLysNsQg
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onAutoParkSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852186:
                handleSignal(Integer.valueOf(convertKeyParkState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$okwKbUqCqtDTBmpmzhRtwZvrx2c
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onKeyParkSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852187:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$LE4bps2_KF5aaveoLEeZ4Dbf9U8
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onScuOperationTips(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852212:
                handleSignal(Integer.valueOf(convertAccState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$S4mVPwBLVx5HSgzUUVdJCNnP47o
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onAccStateChanged(((Integer) obj2).intValue());
                    }
                });
                handleSignal(Integer.valueOf(convertAccStateForMirror(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$-D6X5rksKHXEXXcMnU9GZyELmhI
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onAccStateForMirrorChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852240:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$fPXJnC101kWOO8bhRsox2KezfDg
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onMirrorCtrl(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852302:
            case 557852372:
                handleSignal(Integer.valueOf(convertLkaState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$v6WADhRYDc5OhF1qv8CFKC-cW54
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onLkaSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852314:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$756fvcx1Zyt9LC_geH0D7Y5MZBE
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onNgpTipWindowChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852318:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$SuVnY-7fBU5peBXv2Qx1e63TNks
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onXpuXPilotStateChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852319:
            case 557852320:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$UtgqOiQFkqJmOWkR0x3ORjqA4eU
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onNgpTruckOffsetChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852322:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$SGSYhsvEDjRmnSvM3oPVceCdNDo
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onNgpRemindModeChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852329:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$hF1y1Iq-7nnvzHO_Qe6BfuvDaM4
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onShowSdcObstacleTips(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852330:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$xgXBL8RiLDiwYfECEzEcEtaDtgA
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onShowSdcNarrowSpaceTips(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852333:
                handleSignal(Integer.valueOf(convertElkState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$nG9mo5gcKW9djqFNsZ77u_e0VJ4
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onElkSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852369:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$EDX2ONrOMyFVyS8aTGcYj-8z44M
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onMemParkSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852371:
                handleSignal(Integer.valueOf(convertNewLdwState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$9-rsAFKtqP2DHPloHjGvjbEdfPc
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onLdwSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852378:
                handleSignal(Integer.valueOf(convertIslaState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$Bp0ocjugrm34v2_Zx0BjMw5WqoM
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onIslaStateChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852380:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$p5MPDBwOEE6_HeM3qKgv2fGBZTo
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onIslaConfirmModeChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852381:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$8N9QzwCZtgmZUjq6pvlLfF_iM0s
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onIslaSpdRangeChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852385:
                if (this.mCarConfig.isNewScuArch()) {
                    handleSignal(Integer.valueOf(convertSlaState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$Vs5BrKFWIyuf6_6aXf0RP3Jwm54
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            ((IScuController.Callback) obj).onSlaStateChanged(((Integer) obj2).intValue());
                        }
                    });
                    return;
                }
                return;
            case 557852395:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$F__MHU_dvfp20Fx6KjkrqBc4a24
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onDsmSwStateChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852404:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$L5iX1TuE-OGsul5h5uXnJbTq70k
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onSdcLeftRadarDisLevelChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852405:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$CXY_s2wGBPaVBv6swwlN3TXMreY
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onSdcRightRadarDisLevelChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852412:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$plRiDkd-egqVl9wyY045Q60bKs4
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onLssSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557852413:
                handleSignal(getValue(value), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$yVoahyTm-lMiWgewH6wzREbbuWs
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onFcwSenChanged(((Integer) obj2).intValue());
                    }
                });
                break;
            case 557917935:
                handleSignal(Integer.valueOf(convertDomainBsdState(getIntArrayProperty(value))), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$SJaqDoDJajUHJ9o0oDquC1S1jTI
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onBsdStateChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557917936:
                handleSignal(Integer.valueOf(convertDomainRcwState(getIntArrayProperty(value))), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ZiZ4byys5kHJKBIWTPV5ZEth_38
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onRcwSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557917937:
                handleSignal(Integer.valueOf(convertDomainDowState(getIntArrayProperty(value))), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$32IUIb065A8inUzi36nnZBxSdT4
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onDowSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            case 557917938:
                handleSignal(Integer.valueOf(convertDomainRctaState(getIntArrayProperty(value))), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$sYIaL9_uF6nTX3VlcePzkVnxIDk
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((IScuController.Callback) obj).onRctaSwChanged(((Integer) obj2).intValue());
                    }
                });
                return;
            default:
                LogUtils.w(TAG, "handle unknown event: " + value);
                return;
        }
        handleSignal(Integer.valueOf(convertRcwState(((Integer) getValue(value)).intValue())), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ZiZ4byys5kHJKBIWTPV5ZEth_38
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((IScuController.Callback) obj).onRcwSwChanged(((Integer) obj2).intValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0019, code lost:
        if (r6.mCarConfig.isSupportIsla() != false) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handleLossEventsUpdate(int r7) {
        /*
            r6 = this;
            r0 = 1
            r1 = 7
            r2 = -1
            r3 = 9
            r4 = 5
            r5 = 2
            switch(r7) {
                case 557852161: goto L22;
                case 557852162: goto L20;
                case 557852163: goto L20;
                case 557852165: goto L1e;
                case 557852166: goto L1e;
                case 557852167: goto L1e;
                case 557852168: goto L1e;
                case 557852169: goto L1c;
                case 557852173: goto L13;
                case 557852174: goto L20;
                case 557852175: goto L10;
                case 557852182: goto L1e;
                case 557852183: goto L1e;
                case 557852184: goto L1e;
                case 557852186: goto L1e;
                case 557852212: goto Le;
                case 557852302: goto Le;
                case 557852333: goto L20;
                case 557852371: goto Le;
                case 557852372: goto Le;
                case 557852378: goto L23;
                case 557852385: goto L23;
                case 557852395: goto Lc;
                default: goto La;
            }
        La:
            r0 = r2
            goto L23
        Lc:
            r0 = 4
            goto L23
        Le:
            r0 = r3
            goto L23
        L10:
            r0 = 22
            goto L23
        L13:
            com.xiaopeng.carcontrol.config.CarBaseConfig r3 = r6.mCarConfig
            boolean r3 = r3.isSupportIsla()
            if (r3 == 0) goto L1c
            goto L23
        L1c:
            r0 = r1
            goto L23
        L1e:
            r0 = r5
            goto L23
        L20:
            r0 = r4
            goto L23
        L22:
            r0 = 3
        L23:
            if (r0 == r2) goto L31
            android.car.hardware.CarPropertyValue r1 = new android.car.hardware.CarPropertyValue
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r1.<init>(r7, r0)
            r6.handleCarEventsUpdate(r1)
        L31:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.carmanager.impl.ScuController.handleLossEventsUpdate(int):void");
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getFcwState() {
        int intValue = ((Integer) getValue(557852161, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$DwJCos18TXputSiRMmMWrUa_lns
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getFcwState$2$ScuController((Void) obj);
            }
        }, 2)).intValue();
        LogUtils.i(TAG, "getFcwState: " + intValue, false);
        return convertFcwState(intValue);
    }

    public /* synthetic */ Integer lambda$getFcwState$2$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getFrontCollisionSecurity());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setFcwState(final boolean enable) {
        LogUtils.i(TAG, "setFcwState: " + enable, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$NezU8jUSSAj8PAKyl9LZnG-q_ps
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setFcwState$3$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setFcwState$3$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setFrontCollisionSecurity(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getFcwSensitivity() {
        int i = 0;
        try {
            try {
                i = getIntProperty(557852413);
            } catch (Exception e) {
                LogUtils.e(TAG, "getFcwSensitivity failed: " + e.getMessage(), false);
            }
        } catch (Exception unused) {
            i = this.mCarManager.getFcwAebSensitivitySwitchStatus();
        }
        LogUtils.e(TAG, "getFcwSensitivity:" + i);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setFcwSensitivity(int value) {
        LogUtils.e(TAG, "setFcwSensitivity:" + value);
        try {
            this.mCarManager.SetFcwAebSensitivitySwitchStatus(value);
        } catch (Exception e) {
            LogUtils.e(TAG, "setFcwSensitivity failed: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getRcwState() {
        if (this.mCarConfig.isSupportXpuDomainCtrl()) {
            int[] iArr = (int[]) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$8YBVr2NO1lZnAI_D9Sa35VjVWYQ
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
                public final Object apply(Object obj) {
                    return ScuController.this.lambda$getRcwState$4$ScuController((Void) obj);
                }
            }, null);
            LogUtils.i(TAG, "getRcwState: " + iArr, false);
            return convertDomainRcwState(iArr);
        }
        int intValue = ((Integer) getValue(557852167, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$A15lqEGzwsda6YOZydUqrGaMGR8
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getRcwState$5$ScuController((Void) obj);
            }
        }, 2)).intValue();
        LogUtils.i(TAG, "getRcwState: " + intValue, false);
        return convertRcwState(intValue);
    }

    public /* synthetic */ int[] lambda$getRcwState$4$ScuController(Void t) throws Exception {
        return this.mCarManager.getLeftRightRearCollisionSwitchStatus();
    }

    public /* synthetic */ Integer lambda$getRcwState$5$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getRearCollisionSecurity());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setRcwState(final boolean enable) {
        if (this.mCarConfig.isSupportRcw()) {
            LogUtils.i(TAG, "setRcwState: " + enable, false);
            setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$dGbgfxzvgGw97-t8XnXaXxqeu-Q
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
                public final void accept(Object obj) {
                    ScuController.this.lambda$setRcwState$6$ScuController(enable, (Void) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setRcwState$6$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setRearCollisionSecurity(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getRctaState() {
        if (this.mCarConfig.isSupportXpuDomainCtrl()) {
            int[] iArr = (int[]) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$9RlPcGrppU-nkMnAJ45YBTk3H0A
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
                public final Object apply(Object obj) {
                    return ScuController.this.lambda$getRctaState$7$ScuController((Void) obj);
                }
            }, null);
            LogUtils.i(TAG, "getRctaState: " + iArr, false);
            return convertDomainRctaState(iArr);
        }
        int intValue = ((Integer) getValue(557852166, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$p3ilg7PHcBq9G0hc1n-HXtbTmgE
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getRctaState$8$ScuController((Void) obj);
            }
        }, 2)).intValue();
        LogUtils.i(TAG, "getRctaState: " + intValue, false);
        return convertRctaState(intValue);
    }

    public /* synthetic */ int[] lambda$getRctaState$7$ScuController(Void t) throws Exception {
        return this.mCarManager.getLeftRightRearCrossTrafficAlertStatus();
    }

    public /* synthetic */ Integer lambda$getRctaState$8$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getRearCrossEmergencyWarning());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setRctaState(final boolean enable) {
        LogUtils.i(TAG, "setRctaState: " + enable, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$-D6bRltZL-sWi6VJvMWuv_NAouM
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setRctaState$9$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setRctaState$9$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setRearCrossEmergencyWarning(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getLdwState() {
        if (this.mCarConfig.isSupportOldLka()) {
            int intValue = ((Integer) getValue(557852371, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$g0tS6t2RuWbdQ2-5TLMtSj_qkb8
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
                public final Object apply(Object obj) {
                    return ScuController.this.lambda$getLdwState$10$ScuController((Void) obj);
                }
            }, 8)).intValue();
            LogUtils.i(TAG, "getLdwState: " + intValue, false);
            return convertNewLdwState(intValue);
        }
        int intValue2 = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$jScqrTyUGYmdlzVMCvIdECEQETQ
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getLdwState$11$ScuController((Void) obj);
            }
        }, 4)).intValue();
        LogUtils.i(TAG, "getLdwState: " + intValue2, false);
        return convertLdwState(intValue2);
    }

    public /* synthetic */ Integer lambda$getLdwState$10$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getScuLdwLkaSwitchStatus());
    }

    public /* synthetic */ Integer lambda$getLdwState$11$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getLaneDepartureWarning());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setLdwState(final boolean enable) {
        LogUtils.i(TAG, "setLdwState: " + enable, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$pBwvlyUYPJWczP-jibSQsd_q_mc
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setLdwState$12$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setLdwState$12$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setLaneDepartureWarning(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getLkaState() {
        int intValue = this.mCarConfig.isSupportOldLka() ? ((Integer) getValue(557852372, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$8E8tuaMvxis5o9QKdb0oGq3KZG4
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getLkaState$13$ScuController((Void) obj);
            }
        }, 8)).intValue() : ((Integer) getValue(557852302, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$BR3JfHGI6emhmHexSdUoiFtAiwg
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getLkaState$14$ScuController((Void) obj);
            }
        }, 8)).intValue();
        LogUtils.i(TAG, "getLkaState: " + intValue, false);
        return convertLkaState(intValue);
    }

    public /* synthetic */ Integer lambda$getLkaState$13$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getScuLkaSwitchState());
    }

    public /* synthetic */ Integer lambda$getLkaState$14$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getScuLkaState());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getElkState() {
        if (this.mCarConfig.isSupportElk()) {
            int intValue = ((Integer) getValue(557852333, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$d5EomGCubMRbENcj9bM4De89XWA
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
                public final Object apply(Object obj) {
                    return ScuController.this.lambda$getElkState$15$ScuController((Void) obj);
                }
            }, 4)).intValue();
            LogUtils.i(TAG, "getElkState: " + intValue, false);
            return convertElkState(intValue);
        }
        return 0;
    }

    public /* synthetic */ Integer lambda$getElkState$15$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getElkSwitchState());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setElkState(final boolean enable) {
        if (this.mCarConfig.isSupportElk()) {
            LogUtils.i(TAG, "setElkState: " + enable, false);
            setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$Taz7dCMMnzv-sqspbCaSI4WCzFw
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
                public final void accept(Object obj) {
                    ScuController.this.lambda$setElkState$16$ScuController(enable, (Void) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setElkState$16$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setElkSwitch(enable ? 1 : 0);
        if (this.mIsMainProcess) {
            this.mDataSync.setElkSw(enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getLssMode() {
        int i;
        int i2 = 0;
        if (BaseFeatureOption.getInstance().isNewLssSignal()) {
            try {
                try {
                    i = getIntProperty(557852412);
                } catch (Exception unused) {
                    i = this.mCarManager.getLaneSupportSystemStateAndWarning();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getLssMode failed: " + e.getMessage(), false);
                i = 0;
            }
            LogUtils.i(TAG, "getLssMode: " + i, false);
            return i;
        } else if (CarBaseConfig.getInstance().isLssCertification()) {
            if (getLdwState() == 1 && getLkaState() == 1 && getElkState() == 1) {
                return 3;
            }
            if (getLdwState() == 1 && getLkaState() == 0 && getElkState() == 0) {
                return 1;
            }
            return (getLdwState() == 0 && getLkaState() == 1 && getElkState() == 1) ? 2 : 0;
        } else {
            if (getLdwState() != 1 || getLkaState() != 1) {
                if (getLdwState() == 1) {
                    i2 = 1;
                } else if (getLkaState() == 1) {
                    i2 = 2;
                }
            }
            LogUtils.i(TAG, "getLssMode: " + i2);
            return i2;
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setLssMode(int mode) {
        LogUtils.i(TAG, "setLssMode: " + mode, false);
        if (BaseFeatureOption.getInstance().isNewLssSignal()) {
            try {
                this.mCarManager.setLaneSupportSystemStateAndWarning(mode);
            } catch (Exception e) {
                LogUtils.e(TAG, "setLssMode: " + e.getMessage(), false);
            }
        } else if (!CarBaseConfig.getInstance().isLssCertification()) {
            if (mode == 1) {
                try {
                    this.mCarManager.setLaneDepartureWarning(1);
                    if (this.mCarConfig.isSupportOldLka()) {
                        this.mCarManager.setScuLdwLkaSwitchStatus(1);
                    }
                } catch (Exception e2) {
                    LogUtils.e(TAG, "setLssMode: " + e2.getMessage(), false);
                }
            } else if (mode == 2) {
                try {
                    if (this.mCarConfig.isSupportOldLka()) {
                        this.mCarManager.setLaneDepartureWarning(0);
                        this.mCarManager.setScuLdwLkaSwitchStatus(2);
                    } else {
                        this.mCarManager.setLaneDepartureWarning(2);
                    }
                } catch (Exception e3) {
                    LogUtils.e(TAG, "setLssMode: " + e3.getMessage(), false);
                }
            } else {
                try {
                    this.mCarManager.setLaneDepartureWarning(0);
                    if (this.mCarConfig.isSupportOldLka()) {
                        this.mCarManager.setScuLdwLkaSwitchStatus(0);
                    }
                } catch (Exception e4) {
                    LogUtils.e(TAG, "setLssMode: " + e4.getMessage(), false);
                }
            }
        } else if (mode == 1) {
            try {
                this.mCarManager.setLaneDepartureWarning(1);
                this.mCarManager.setElkSwitch(0);
            } catch (Exception e5) {
                LogUtils.e(TAG, "setLssMode: " + e5.getMessage(), false);
            }
        } else if (mode == 2) {
            try {
                this.mCarManager.setLaneDepartureWarning(2);
                this.mCarManager.setElkSwitch(1);
            } catch (Exception e6) {
                LogUtils.e(TAG, "setLssMode: " + e6.getMessage(), false);
            }
        } else if (mode == 3) {
            try {
                this.mCarManager.setLaneDepartureWarning(1);
                this.mCarManager.setElkSwitch(1);
            } catch (Exception e7) {
                LogUtils.e(TAG, "setLssMode: " + e7.getMessage(), false);
            }
        } else {
            try {
                this.mCarManager.setLaneDepartureWarning(0);
                this.mCarManager.setElkSwitch(0);
            } catch (Exception e8) {
                LogUtils.e(TAG, "setLssMode: " + e8.getMessage(), false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void saveLssState(int state) {
        if (this.mIsMainProcess) {
            this.mDataSync.setLssState(state);
        } else {
            CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.SET_LSS_STATE, state);
        }
        LogUtils.i(TAG, "save lss state to cdu(sp) or account, state: " + state, false);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getBsdState() {
        if (this.mCarConfig.isSupportXpuDomainCtrl()) {
            int[] iArr = (int[]) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$5XYf3mTBvF22WL6A9thYs24_gK0
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
                public final Object apply(Object obj) {
                    return ScuController.this.lambda$getBsdState$17$ScuController((Void) obj);
                }
            }, null);
            LogUtils.i(TAG, "getBsdState: " + iArr);
            return convertDomainBsdState(iArr);
        }
        int intValue = ((Integer) getValue(557852165, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$xQzabsqUBWxzSeqagZ9AYGO0SVQ
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getBsdState$18$ScuController((Void) obj);
            }
        }, 2)).intValue();
        LogUtils.i(TAG, "getBsdState: " + intValue);
        return convertBsdState(intValue);
    }

    public /* synthetic */ int[] lambda$getBsdState$17$ScuController(Void t) throws Exception {
        return this.mCarManager.getLeftRightBlindSpotDetectionSwitchStatus();
    }

    public /* synthetic */ Integer lambda$getBsdState$18$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getBlindAreaDetectionWarning());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setBsdState(final boolean enable) {
        LogUtils.i(TAG, "setBsdState: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$YYfbBDMAiGhjLVYarGRD6Okqubc
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setBsdState$19$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setBsdState$19$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setBlindAreaDetectionWarning(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getDowState() {
        if (this.mCarConfig.isSupportXpuDomainCtrl()) {
            int[] iArr = (int[]) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$I7_I-HEB5IX4nM_-L4vkR9YQRDY
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
                public final Object apply(Object obj) {
                    return ScuController.this.lambda$getDowState$20$ScuController((Void) obj);
                }
            }, null);
            LogUtils.i(TAG, "getDowState: " + iArr);
            return convertDomainDowState(iArr);
        }
        int intValue = ((Integer) getValue(557852168, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$s5tM0lvWSxdr3N8TSgVzSFIeSAs
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getDowState$21$ScuController((Void) obj);
            }
        }, 2)).intValue();
        LogUtils.i(TAG, "getDowState: " + intValue);
        return convertDowState(intValue);
    }

    public /* synthetic */ int[] lambda$getDowState$20$ScuController(Void t) throws Exception {
        return this.mCarManager.getLeftRightDoorOpenWarningSwitchStatus();
    }

    public /* synthetic */ Integer lambda$getDowState$21$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getDoorOpenWarning());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setDowSw(final boolean enable, boolean needSp) {
        LogUtils.i(TAG, "setDowSw: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$FOayV_YwohgqkXya1f5-p0s6j-A
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setDowSw$22$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setDowSw$22$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setDoorOpenWarning(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getIhbState() {
        int intValue = ((Integer) getValue(557852163, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$mnuDFmDzABjg4eZppa_2TeFaIwI
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getIhbState$23$ScuController((Void) obj);
            }
        }, 4)).intValue();
        LogUtils.i(TAG, "getIhbState: " + intValue);
        return convertIhbState(intValue);
    }

    public /* synthetic */ Integer lambda$getIhbState$23$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getFarLampAutoSwitch());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setIhbState(final boolean enable) {
        LogUtils.i(TAG, "setIhbState: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$keUL2Vo3oSfXT8MlDdS7zSFPNN8
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setIhbState$24$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setIhbState$24$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setFarLampAutoSwitch(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getLccState() {
        int intValue = ((Integer) getValue(557852174, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$T362byBwgA61YXv1P3eqBYiva90
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getLccState$25$ScuController((Void) obj);
            }
        }, 4)).intValue();
        LogUtils.i(TAG, "getLccState: " + intValue, false);
        return convertLccState(intValue);
    }

    public /* synthetic */ Integer lambda$getLccState$25$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getLaneAlignmentAssist());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setLccState(boolean enable) {
        setLccState(enable, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setLccState(final boolean enable, final boolean needSave) {
        LogUtils.i(TAG, "setLccState: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$G9ri5VhsJ5Z6DD81fFw_0IPb_wE
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setLccState$26$ScuController(enable, needSave, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setLccState$26$ScuController(final boolean enable, final boolean needSave, Void t) throws Exception {
        this.mCarManager.setLaneAlignmentAssist(enable ? 1 : 0);
        if (this.mIsMainProcess) {
            this.mDataSync.setLccSw(enable, needSave);
        } else {
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SET_LCC_SW, enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getAccState() {
        int intValue = ((Integer) getValue(557852212, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$Jkqmiw28lV-pNrEQKFpryxbBiHo
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getAccState$27$ScuController((Void) obj);
            }
        }, 8)).intValue();
        LogUtils.i(TAG, "getAccState: " + intValue, false);
        return convertAccState(intValue);
    }

    public /* synthetic */ Integer lambda$getAccState$27$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getAccStatus());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getAccStateForMirror() {
        int intValue = ((Integer) getValue(557852212, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$iu99qRoiwG5eCgyWIRh44gToB7M
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getAccStateForMirror$28$ScuController((Void) obj);
            }
        }, 8)).intValue();
        LogUtils.i(TAG, "getAccStateForMirror: " + intValue, false);
        return convertAccStateForMirror(intValue);
    }

    public /* synthetic */ Integer lambda$getAccStateForMirror$28$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getAccStatus());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getLccWorkSt() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$zVgMbj0Ut4DmPliLbCJcqleJh_A
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getLccWorkSt$29$ScuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getLccWorkSt: " + intValue, false);
        return convertLccWorkSt(intValue);
    }

    public /* synthetic */ Integer lambda$getLccWorkSt$29$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getScuModeIndex());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getIslcState() {
        int intValue = ((Integer) getValue(557852173, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$du0DT6Youiho_xRL0gYb9BnkCfw
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getIslcState$30$ScuController((Void) obj);
            }
        }, 6)).intValue();
        LogUtils.i(TAG, "getIslcState: " + intValue, false);
        return convertIslcState(intValue);
    }

    public /* synthetic */ Integer lambda$getIslcState$30$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getIntelligentSpeedLimit());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setIslcState(final boolean enable) {
        LogUtils.i(TAG, "setIslcState: " + enable, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$UivcbaqVLBY1-7Ds-XkOgRJmdHg
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setIslcState$31$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setIslcState$31$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setIntelligentSpeedLimit(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getSlaState() {
        if (this.mCarConfig.isSupportOldIsla()) {
            return getIslcState();
        }
        int intValue = ((Integer) getValue(557852385, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$0XcXGQ-QRs-wAtNT2z6F0DsvoHY
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getSlaState$32$ScuController((Void) obj);
            }
        }, 6)).intValue();
        LogUtils.i(TAG, "getSlaState: " + intValue, false);
        return convertSlaState(intValue);
    }

    public /* synthetic */ Integer lambda$getSlaState$32$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getSpeedLimitControlSystemState());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getIslaState() {
        int intValue = ((Integer) getValue(557852173, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$r9icBOMhXSshdhVQSxqCTdwReZQ
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getIslaState$33$ScuController((Void) obj);
            }
        }, 6)).intValue();
        LogUtils.i(TAG, "getIslaState: " + intValue, false);
        return convertIslaState(intValue);
    }

    public /* synthetic */ Integer lambda$getIslaState$33$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getIntelligentSpeedLimit());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getOldIslaSw() {
        int islaSw = this.mIsMainProcess ? this.mDataSync.getIslaSw() : this.mIslaMode.intValue();
        LogUtils.i(TAG, "getOldIslaSw: " + islaSw, false);
        return islaSw;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setIslaSw(final int mode) {
        LogUtils.i(TAG, "setIslaSw: " + mode, false);
        if (BaseFeatureOption.getInstance().isSupportIslaMemoryFunc()) {
            if (this.mIsMainProcess) {
                this.mDataSync.setIslaSw(mode);
            } else if (this.mCarConfig.isSupportOldIsla()) {
                this.mIslaMode = Integer.valueOf(mode);
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$FeIRbtIzXFpy1ip4fL4hbGilqRk
                    @Override // java.lang.Runnable
                    public final void run() {
                        CarControl.PrivateControl.putInt(App.getInstance().getContentResolver(), CarControl.PrivateControl.XPILOT_ISLA_SW, mode);
                    }
                });
            }
        }
        try {
            if (mode != 0) {
                if (mode != 1) {
                    if (mode != 2) {
                        return;
                    }
                    if (this.mCarConfig.isNewScuArch()) {
                        this.mCarManager.setIntelligentSpeedLimit(1);
                    } else {
                        this.mCarManager.setIntelligentSpeedLimit(1);
                        this.mCarManager.setSpeedLimitSwitchState(1);
                    }
                } else if (this.mCarConfig.isNewScuArch()) {
                    this.mCarManager.setIntelligentSpeedLimit(2);
                } else {
                    this.mCarManager.setIntelligentSpeedLimit(1);
                    this.mCarManager.setSpeedLimitSwitchState(0);
                }
            } else if (this.mCarConfig.isNewScuArch()) {
                this.mCarManager.setIntelligentSpeedLimit(0);
            } else {
                this.mCarManager.setIntelligentSpeedLimit(0);
                this.mCarManager.setSpeedLimitSwitchState(0);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "setIslaSw failed: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getIslaSpdRange() {
        int intValue = ((Integer) getValue(557852381, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$Wfngoe2mTItTAPndp-5ygJQGLls
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getIslaSpdRange$35$ScuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getIslaSpdRange: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getIslaSpdRange$35$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getSpeedLimitRange());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setIslaSpdRange(final int range) {
        LogUtils.i(TAG, "setIslaSpdRange: " + range, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$HN2Xvh37IzgcvWst8VrxOLcxTks
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setIslaSpdRange$36$ScuController(range, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setIslaSpdRange$36$ScuController(final int range, Void t) throws Exception {
        this.mCarManager.setSpeedLimitRange(range);
        if (!this.mIsMainProcess || this.mCarConfig.isNewScuArch()) {
            return;
        }
        this.mDataSync.setIslaSpdRange(range);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getIslaConfirmMode() {
        int intValue = ((Integer) getValue(557852380, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$u7Zkr87rgSeh4fBHvZU8LNJgSqo
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getIslaConfirmMode$37$ScuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getIslaConfirmMode: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getIslaConfirmMode$37$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getSpeedLimitDriverConfirmStatus());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setIslaConfirmMode(final boolean enable) {
        LogUtils.i(TAG, "setIslaConfirmMode: " + enable, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$YnpyCWo1ycsEMJKtSmpiwhLzRpc
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setIslaConfirmMode$38$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setIslaConfirmMode$38$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setSpeedLimitDriverConfirmSwitch(enable ? 1 : 0);
        if (!this.mIsMainProcess || this.mCarConfig.isNewScuArch()) {
            return;
        }
        this.mDataSync.setIslaConfirmMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getAlcState() {
        int intValue = ((Integer) getValue(557852169, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$765kqUYPYFmLjM4jUQOW2nUoo3w
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getAlcState$39$ScuController((Void) obj);
            }
        }, 7)).intValue();
        LogUtils.i(TAG, "getAlcState: " + intValue, false);
        return convertLcaState(intValue);
    }

    public /* synthetic */ Integer lambda$getAlcState$39$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getLaneChangeAssist());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setAlcState(boolean enable) {
        setAlcState(enable, true);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setAlcState(final boolean enable, final boolean needSave) {
        LogUtils.i(TAG, "getAlcState: " + enable, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$ZgC-mcbrZT-mYinviZxdRL-l3cU
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setAlcState$40$ScuController(enable, needSave, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setAlcState$40$ScuController(final boolean enable, final boolean needSave, Void t) throws Exception {
        this.mCarManager.setLaneChangeAssist(enable ? 1 : 0);
        if (this.mIsMainProcess) {
            this.mDataSync.setAlcSw(enable, needSave);
        } else {
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SET_ALC_SW, enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public boolean isXpuXpilotActivated() {
        return getXpuXpilotState() == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getXpuXpilotState() {
        int intValue = ((Integer) getValue(557852318, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$YQyVE00iDn0EMFupBXjmzGNyRvk
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getXpuXpilotState$41$ScuController((Void) obj);
            }
        }, 2)).intValue();
        LogUtils.i(TAG, "getXpuXpilotState st: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getXpuXpilotState$41$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getXpilot3St());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public boolean getNgpSafeExamResult(String uid) {
        if (this.mIsMainProcess) {
            if (uid == null) {
                return this.mDataSync.getNgpSafeExamResult();
            }
            return this.mDataSync.getNgpSafeExamResult(uid);
        }
        LogUtils.i(TAG, "getNgpSafeExamResult: " + this.mNgpSafeExamResult + " uid:" + uid, false);
        if (uid == null) {
            return CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.NGP_SAFE_EXAM_RESULT, false);
        }
        return this.mNgpSafeExamResult.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public boolean getMemParkExamResult(String uid) {
        if (this.mIsMainProcess) {
            if (uid == null) {
                return this.mDataSync.getMemParkSafeExamResult();
            }
            return this.mDataSync.getMemParkSafeExamResult(uid);
        }
        LogUtils.i(TAG, "getMemParkSafeExamResult: " + this.mMemParkSafeExamResult + " uid:" + uid, false);
        if (uid == null) {
            return CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.MEM_PARK_SAFE_EXAM_RESULT, false);
        }
        return this.mMemParkSafeExamResult.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public boolean getSuperVpaExamResult(String uid) {
        if (this.mIsMainProcess) {
            if (uid == null) {
                return this.mDataSync.getSuperVpaSafeExamResult();
            }
            return this.mDataSync.getSuperVpaSafeExamResult(uid);
        }
        LogUtils.i(TAG, "getSuperVpaSafeExamResult: " + this.mSuperVpaSafeExamResult + " uid:" + uid, false);
        if (uid == null) {
            return CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SUPER_VPA_SAFE_EXAM_RESULT, false);
        }
        return this.mSuperVpaSafeExamResult.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public boolean getSuperLccExamResult(String uid) {
        if (this.mIsMainProcess) {
            if (uid == null) {
                return this.mDataSync.getSuperLccSafeExamResult();
            }
            return this.mDataSync.getSuperLccSafeExamResult(uid);
        }
        LogUtils.i(TAG, "getSuperLccSafeExamResult: " + this.mSuperLccSafeExamResult + " uid:" + uid, false);
        if (uid == null) {
            return CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SUPER_LCC_SAFE_EXAM_RESULT, false);
        }
        return this.mSuperLccSafeExamResult.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setNgpSafeExamResult(String uid, boolean result) {
        if (this.mIsMainProcess) {
            this.mDataSync.setNgpSafeExamResult(uid, result);
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.NGP_SAFE_EXAM_RESULT_NOTIFY, result);
            return;
        }
        LogUtils.i(TAG, "setNgpSafeExamResult: " + result);
        this.mNgpSafeExamResult = Boolean.valueOf(result);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.NGP_SAFE_EXAM_RESULT, result);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setMemParkSafeExamResult(String uid, boolean result) {
        if (this.mIsMainProcess) {
            this.mDataSync.setMemParkSafeExamResult(uid, result);
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.MEM_PARK_SAFE_EXAM_RESULT_NOTIFY, result);
            return;
        }
        LogUtils.i(TAG, "setMemParkResult: " + result);
        this.mMemParkSafeExamResult = Boolean.valueOf(result);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.MEM_PARK_SAFE_EXAM_RESULT, result);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setSuperVpaSafeExamResult(String uid, boolean result) {
        if (this.mIsMainProcess) {
            this.mDataSync.setSuperVpaSafeExamResult(uid, result);
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SUPER_VPA_SAFE_EXAM_RESULT_NOTIFY, result);
            return;
        }
        LogUtils.i(TAG, "setSuperVpaResult: " + result);
        this.mSuperVpaSafeExamResult = Boolean.valueOf(result);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SUPER_VPA_SAFE_EXAM_RESULT, result);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setSuperLccSafeExamResult(String uid, boolean result) {
        if (this.mIsMainProcess) {
            this.mDataSync.setSuperLccSafeExamResult(uid, result);
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SUPER_LCC_SAFE_EXAM_RESULT_NOTIFY, result);
            return;
        }
        LogUtils.i(TAG, "setSuperVpaResult: " + result);
        this.mSuperLccSafeExamResult = Boolean.valueOf(result);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SUPER_LCC_SAFE_EXAM_RESULT, result);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getNgpState() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$JoYqU3VPDD7czzqtIAYW0pYN6NA
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getNgpState$42$ScuController((Void) obj);
            }
        }, 22)).intValue();
        LogUtils.i(TAG, "getNgpState: " + intValue);
        return convertNgpState(intValue);
    }

    public /* synthetic */ Integer lambda$getNgpState$42$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getHighSpeedNavigation());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setNgpEnable(final boolean enable) {
        LogUtils.i(TAG, "setNgpEnable: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$FYeWlbuX5b4_3zDh3UrO5nsIu9o
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setNgpEnable$43$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setNgpEnable$43$ScuController(final boolean enable, Void t) throws Exception {
        if (this.mCarConfig.isSupportXpuDomainCtrl()) {
            this.mCarManager.setHighSpeedNavgation(enable ? 1 : 0);
        } else {
            this.mCarManager.setNgpAvoidTruckSw(enable ? 1 : 2);
        }
        if (this.mIsMainProcess) {
            this.mDataSync.setNgpSw(enable);
        } else {
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SET_NGP_SW, enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getNgpFastLaneSw() {
        int intValue = ((Integer) getValue(557852319, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$Gaaz9GiTTcNOt9XHktQxhwdbBs0
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getNgpFastLaneSw$44$ScuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getNgpFastLaneSw: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getNgpFastLaneSw$44$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNgpPreferFastLaneSwSt());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setNgpFastLaneSw(final boolean enable) {
        LogUtils.i(TAG, "setNgpFastLaneSw: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$yXtYqiWkL-XMTdApoMj_9F0HI7I
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setNgpFastLaneSw$45$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setNgpFastLaneSw$45$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setNgpPreferFastLaneSw(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getNgpTruckOffsetSw() {
        int intValue = this.mCarConfig.isSupportXpuDomainCtrl() ? ((Integer) getValue(557852320, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$ctGm1oN39paeYqAP3GBNTikNfe4
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getNgpTruckOffsetSw$46$ScuController((Void) obj);
            }
        }, 0)).intValue() : ((Integer) getValue(557852319, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$fty0WhUxQI4l8oI65ogYfYRFlWg
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getNgpTruckOffsetSw$47$ScuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getNgpTruckOffsetSw: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getNgpTruckOffsetSw$46$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNgpAvoidTruckSwSt());
    }

    public /* synthetic */ Integer lambda$getNgpTruckOffsetSw$47$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNgpPreferFastLaneSwSt());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setNgpTruckOffsetSw(final boolean enable) {
        LogUtils.i(TAG, "setNgpTruckOffsetSw: " + enable);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$Ffg3Izjfb-3TMLjQvaYuUhqrPgE
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setNgpTruckOffsetSw$48$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setNgpTruckOffsetSw$48$ScuController(final boolean enable, Void t) throws Exception {
        if (this.mCarConfig.isSupportXpuDomainCtrl()) {
            this.mCarManager.setNgpAvoidTruckSw(enable ? 1 : 0);
        } else {
            this.mCarManager.setNgpPreferFastLaneSw(enable ? 1 : 0);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getNgpTipsWinSw() {
        int intValue = ((Integer) getValue(557852314, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$_-tMjzE_VYJC3leQ3-K2nwzNVFU
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getNgpTipsWinSw$49$ScuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getNgpTipsWinSw: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getNgpTipsWinSw$49$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNgpTipsWindowsSwSt());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setNgpTipsWin(final boolean enable) {
        LogUtils.i(TAG, "setNgpTipsWin: " + enable, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$9glSZn41xeOSPy9Fr_xpYJ6Y-JY
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setNgpTipsWin$50$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setNgpTipsWin$50$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setNgpTipsWindowsSw(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getNgpVoiceChangeLane() {
        int i = this.mIsMainProcess ? Settings.System.getInt(App.getInstance().getContentResolver(), IScuController.NGP_VOICE_CHANGE_LANE, 0) : 0;
        LogUtils.i(TAG, "getNgpVoiceChangeLane: " + i, false);
        return i;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setNgpVoiceChangeLane(boolean enable) {
        if (this.mIsMainProcess) {
            Settings.System.putInt(App.getInstance().getContentResolver(), IScuController.NGP_VOICE_CHANGE_LANE, enable ? 1 : 0);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getNgpChangeLaneMode() {
        int intValue = ((Integer) getValue(557852322, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$r28flKqZYXBZL2KjCgcRZR7znvI
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getNgpChangeLaneMode$51$ScuController((Void) obj);
            }
        }, 2)).intValue();
        LogUtils.i(TAG, "getNgpChangeLaneMode: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getNgpChangeLaneMode$51$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNgpLaneChangeMode());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setNgpChangeLaneMode(final int mode) {
        LogUtils.i(TAG, "setNgpChangeLaneMode: " + mode, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$WxP9xaGHR2qCxCB3hGwwDIf2yaY
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setNgpChangeLaneMode$52$ScuController(mode, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setNgpChangeLaneMode$52$ScuController(final int mode, Void t) throws Exception {
        this.mCarManager.setNgpLaneChangeMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getNgpRemindMode() {
        int convertRemindModeValueFromSave = convertRemindModeValueFromSave(Settings.System.getInt(App.getInstance().getContentResolver(), IScuController.NGP_REMIND_MODE, 1));
        LogUtils.i(TAG, "getNgpRemindMode: " + convertRemindModeValueFromSave, false);
        return convertRemindModeValueFromSave;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setNgpRemindMode(final int mode) {
        LogUtils.i(TAG, "setNgpRemindMode: " + mode);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$sH4ZHnvV3bsLB8ISDlhf74nSEFc
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setNgpRemindMode$53$ScuController(mode, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setNgpRemindMode$53$ScuController(final int mode, Void t) throws Exception {
        this.mCarManager.setNgpRemindMode(mode);
        Settings.System.putInt(App.getInstance().getContentResolver(), IScuController.NGP_REMIND_MODE, convertRemindModeValue(mode));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getNgpModeStatus() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$Oy8S4qHL3_irm3u3GPw_j1F0ev4
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getNgpModeStatus$54$ScuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getNgpModeStatus: " + intValue);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getNgpModeStatus$54$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getNgpModeStatus());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getAutoParkSw() {
        int intValue = ((Integer) getValue(new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$eq7JPac539zvZQdHDPqHjQDY8W4
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getAutoParkSw$55$ScuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getAutoParkSw: " + intValue, false);
        return convertApaState(intValue);
    }

    public /* synthetic */ Integer lambda$getAutoParkSw$55$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getAutoParkSwitch());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setAutoParkSw(final boolean enable) {
        LogUtils.i(TAG, "setAutoParkSw: " + enable, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$DHUT0ZPWvHdzT3A-JbrUPwH5vzE
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setAutoParkSw$56$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setAutoParkSw$56$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setAutoParkSwitch(enable ? 1 : 0);
        if (this.mIsMainProcess) {
            this.mDataSync.setAutoParkSw(enable);
        } else {
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SET_AUTO_PARK_SW, enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getMemoryParkSw() {
        if (this.mCarConfig.isSupportMemPark()) {
            int intValue = ((Integer) getValue(557852369, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$ZII6AgCMRr9b5AXxTXvk_cINUTs
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
                public final Object apply(Object obj) {
                    return ScuController.this.lambda$getMemoryParkSw$57$ScuController((Void) obj);
                }
            }, 2)).intValue();
            LogUtils.i(TAG, "getMemoryParkSw: " + intValue, false);
            return intValue;
        }
        return 2;
    }

    public /* synthetic */ Integer lambda$getMemoryParkSw$57$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getParkByMemorySwSt());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setMemoryParkSw(final boolean enable) {
        LogUtils.i(TAG, "setMemoryParkSw: " + enable, false);
        if (this.mCarConfig.isSupportMemPark()) {
            setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$k24HEgtoktOvCAUfMY8myLkT2-Q
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
                public final void accept(Object obj) {
                    ScuController.this.lambda$setMemoryParkSw$58$ScuController(enable, (Void) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setMemoryParkSw$58$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setParkByMemorySw(enable ? 2 : 1);
        if (this.mIsMainProcess) {
            this.mDataSync.setMemParkSw(enable);
        } else {
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SET_MEM_PARK_SW, enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getPhoneParkSw() {
        int intValue = ((Integer) getValue(557852183, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$mXha3gWbBLvf9qdOQXd70xgZ5z0
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getPhoneParkSw$59$ScuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getPhoneParkSw: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getPhoneParkSw$59$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getPhoneSmButton());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setPhoneParkSw(final boolean enable) {
        LogUtils.i(TAG, "setPhoneParkSw: " + enable, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$6hNbcIgAaEEvbj0oXY7TO15Ez0I
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setPhoneParkSw$60$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setPhoneParkSw$60$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setPhoneSmButton(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getKeyParkSw() {
        int intValue = ((Integer) getValue(557852186, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$a4TM5DcmioM2q8AnGFmL21AiMJI
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getKeyParkSw$61$ScuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getPhoneParkSw: " + intValue, false);
        return convertKeyParkState(intValue);
    }

    public /* synthetic */ Integer lambda$getKeyParkSw$61$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getKeyRemoteSmButton());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setKeyParkSw(final boolean enable) {
        LogUtils.i(TAG, "setKeyParkSw: " + enable, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$hlFfvPAwuTK_xxFyk7T4zCBxb38
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setKeyParkSw$62$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setKeyParkSw$62$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setKeyRemoteSmButton(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getScuRearMirrorControlState() {
        int intValue = ((Integer) getValue(557852240, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$BsQ-90ealNjTpdSUWSHqGr6b3fY
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getScuRearMirrorControlState$63$ScuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getScuRearMirrorControlState: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getScuRearMirrorControlState$63$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getScuRearMirrorControlState());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setDistractionSwitch(final boolean enable) {
        LogUtils.i(TAG, "setDistractionSwitch: " + enable, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$KxZazKvFB1lnO9DEnBrJtsFMX78
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setDistractionSwitch$64$ScuController(enable, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setDistractionSwitch$64$ScuController(final boolean enable, Void t) throws Exception {
        this.mCarManager.setDistractionSwitch(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public boolean isAutoPilotNeedTts() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), IScuController.KEY_AUTO_PILOT_TTS, 1) == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setAutoPilotNeedTts(boolean enable) {
        Settings.System.putInt(App.getInstance().getContentResolver(), IScuController.KEY_AUTO_PILOT_TTS, enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setTtsBroadcastType(int type) {
        Settings.System.putInt(App.getInstance().getContentResolver(), IScuController.XP_TTS_BROADCAST_TYPE, type);
        LogUtils.i(TAG, "setTtsBroadcastType: " + type);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getTtsBroadcastType() {
        int i = Settings.System.getInt(App.getInstance().getContentResolver(), IScuController.XP_TTS_BROADCAST_TYPE, 0);
        LogUtils.i(TAG, "getTtsBroadcastType value: " + i, false);
        if (i == 0 || i == 1) {
            return i;
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setDsmSw(final boolean on) {
        LogUtils.i(TAG, "setDsmSw: " + on, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$X3Qw6ByFxSVcDzODWkpE75J1pJI
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setDsmSw$65$ScuController(on, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setDsmSw$65$ScuController(final boolean on, Void t) throws Exception {
        this.mCarManager.setDsmStatus(on ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getDsmSwStatus() {
        int intValue = ((Integer) getValue(557852395, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$wJeAN0ap7jYi7a4eCjkte_TZVzo
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
            public final Object apply(Object obj) {
                return ScuController.this.lambda$getDsmSwStatus$66$ScuController((Void) obj);
            }
        }, 0)).intValue();
        LogUtils.i(TAG, "getDsmSwStatus: " + intValue, false);
        return intValue;
    }

    public /* synthetic */ Integer lambda$getDsmSwStatus$66$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getDsmStatus());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setRemoteCameraSw(boolean on) {
        Settings.System.putInt(App.getInstance().getContentResolver(), REMOTE_CAMERA_SW, on ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public boolean getRemoteCameraSw() {
        return Settings.System.getInt(App.getInstance().getContentResolver(), REMOTE_CAMERA_SW, 0) == 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getSdcLeftRadarDisLevel() {
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            int intValue = ((Integer) getValue(557852404, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$oQOX6rL7uJV5wX50trIS_mgJTmc
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
                public final Object apply(Object obj) {
                    return ScuController.this.lambda$getSdcLeftRadarDisLevel$67$ScuController((Void) obj);
                }
            }, 0)).intValue();
            LogUtils.i(TAG, "getSdcLeftRadarDisLevel: " + intValue, false);
            return intValue;
        }
        return 0;
    }

    public /* synthetic */ Integer lambda$getSdcLeftRadarDisLevel$67$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getDoorsLRadarDisplayLevel());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getSdcRightRadarDisLevel() {
        if (CarBaseConfig.getInstance().isSupportSdc()) {
            int intValue = ((Integer) getValue(557852405, new BaseCarController.ThrowingFunction() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$SHOgrD4v90IH9t1gKtwqErniz4A
                @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingFunction
                public final Object apply(Object obj) {
                    return ScuController.this.lambda$getSdcRightRadarDisLevel$68$ScuController((Void) obj);
                }
            }, 0)).intValue();
            LogUtils.i(TAG, "getSdcRightRadarDisLevel: " + intValue, false);
            return intValue;
        }
        return 0;
    }

    public /* synthetic */ Integer lambda$getSdcRightRadarDisLevel$68$ScuController(Void t) throws Exception {
        return Integer.valueOf(this.mCarManager.getDoorsRRadarDisplayLevel());
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setScuOtaTagStatus(final int tag) {
        LogUtils.i(TAG, "setScuOtaTagStatus: " + tag, false);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$rFtzw-UD1ZhewfoXq5NERDZmOz4
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setScuOtaTagStatus$69$ScuController(tag, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setScuOtaTagStatus$69$ScuController(final int tag, Void t) throws Exception {
        this.mCarManager.setScuOtaTagStatus(tag);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public boolean getLccSafeExamResult(String uid) {
        if (this.mIsMainProcess) {
            if (uid == null) {
                return this.mDataSync.getLccSafeExamResult();
            }
            return this.mDataSync.getLccSafeExamResult(uid);
        }
        LogUtils.i(TAG, "getLccSafeExamResult: " + this.mLccSafeExamResult + " uid:" + uid);
        if (uid == null) {
            return CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.LCC_SAFE_EXAM_RESULT, false);
        }
        return this.mLccSafeExamResult.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setLccSafeExamResult(String uid, boolean result) {
        if (this.mIsMainProcess) {
            if (uid == null) {
                this.mDataSync.setLccSafeExamResult(result);
            } else {
                this.mDataSync.setLccSafeExamResult(uid, result);
            }
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.LCC_SAFE_EXAM_RESULT_NOTIFY, result);
            return;
        }
        LogUtils.i(TAG, "setLccSafeExamResult: " + result);
        this.mLccSafeExamResult = Boolean.valueOf(result);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.LCC_SAFE_EXAM_RESULT, result);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public boolean getApaSafeExamResult(String uid) {
        if (this.mIsMainProcess) {
            if (uid == null) {
                return this.mDataSync.getApaSafeExamResult();
            }
            return this.mDataSync.getApaSafeExamResult(uid);
        }
        LogUtils.i(TAG, "getApaSafeExamResult: " + this.mApaSafeExamResult + " uid:" + uid);
        if (uid == null) {
            return CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.APA_SAFE_EXAM_RESULT, false);
        }
        return this.mApaSafeExamResult.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setApaSafeExamResult(String uid, boolean result) {
        if (this.mIsMainProcess) {
            if (uid == null) {
                this.mDataSync.setApaSafeExamResult(result);
            } else {
                this.mDataSync.setApaSafeExamResult(uid, result);
            }
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.APA_SAFE_EXAM_RESULT_NOTIFY, result);
            return;
        }
        LogUtils.i(TAG, "setApaSafeExamResult: " + result);
        this.mApaSafeExamResult = Boolean.valueOf(result);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.APA_SAFE_EXAM_RESULT, result);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public boolean isLccVideoWatched() {
        if (this.mIsMainProcess) {
            return this.mDataSync.isLccVideoWatched();
        }
        return this.mIsLccVideoWatched.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setLccVideoWatched(boolean watched) {
        if (this.mIsMainProcess) {
            this.mDataSync.setLccVideoWatched(watched);
            return;
        }
        this.mIsLccVideoWatched = Boolean.valueOf(watched);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.LCC_VIDEO_WATCH_STATE, watched);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public boolean isMemParkVideoWatched() {
        if (this.mIsMainProcess) {
            return this.mDataSync.isMemParkVideoWatched();
        }
        return this.mIsMemParkVideoWatched.booleanValue();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setMemParkVideoWatched(boolean watched) {
        if (this.mIsMainProcess) {
            this.mDataSync.setMemParkVideoWatched(watched);
            return;
        }
        this.mIsMemParkVideoWatched = Boolean.valueOf(watched);
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.MEM_PARK_VIDEO_WATCH_STATE, watched);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void syncXPilotMemParkSw() {
        boolean z;
        if (this.mIsMainProcess) {
            if (this.mDataSync.isGuest()) {
                LogUtils.w(TAG, "Current not login, do not resume XPilot MemPark sw", false);
                return;
            }
            int xpuXpilotState = getXpuXpilotState();
            LogUtils.i(TAG, "Current xpilot st=" + xpuXpilotState, false);
            boolean z2 = true;
            if (xpuXpilotState != 1) {
                if (xpuXpilotState != 2) {
                    return;
                }
                this.mDataSync.setMemParkSw(false);
                return;
            }
            int memoryParkSw = getMemoryParkSw();
            LogUtils.i(TAG, "Current XPU MemPark state=" + memoryParkSw, false);
            if (memoryParkSw == 3) {
                this.mDataSync.setMemParkSw(false);
            } else if (memoryParkSw == 0) {
            } else {
                if (this.mCarConfig.isSupportLidar() && BaseFeatureOption.getInstance().isSupportLidarSafeExam()) {
                    z = this.mDataSync.getSuperVpaSafeExamResult();
                } else {
                    z = this.mDataSync.getMemParkSafeExamResult() || this.mDataSync.getSuperVpaSafeExamResult();
                }
                boolean autoParkSw = this.mDataSync.getAutoParkSw();
                LogUtils.i(TAG, "Current MemPark exam result=" + z + ", AutoParkSw=" + autoParkSw, false);
                if (!z || !autoParkSw) {
                    z2 = false;
                }
                if (memoryParkSw == 2) {
                    boolean memParkSw = this.mDataSync.getMemParkSw();
                    LogUtils.i(TAG, "Current MemPark saved sw=" + memParkSw, false);
                    z2 &= memParkSw;
                }
                LogUtils.i(TAG, "XPilot Mem Park Sw, resume saved value: " + z2, false);
                setMemoryParkSw(z2);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setSpecialSasMode(final int mode) {
        LogUtils.i(TAG, "setSpecialSasMode: " + mode);
        setValue(new BaseCarController.ThrowingConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.-$$Lambda$ScuController$pfg6mYLgj4q8Sz8V2jOweSFjVDE
            @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController.ThrowingConsumer
            public final void accept(Object obj) {
                ScuController.this.lambda$setSpecialSasMode$70$ScuController(mode, (Void) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setSpecialSasMode$70$ScuController(final int mode, Void t) throws Exception {
        this.mCarManager.setIntelligentSpeedLimit(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public boolean isFirstOpenXngpSw() {
        return SharedPreferenceUtil.isFirstOpenXngpSw();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setFirstOpenXngpSw(boolean enable) {
        SharedPreferenceUtil.setFirstOpenXngpSw(enable);
    }

    protected void registerContentObserver() {
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.carmanager.impl.ScuController.2
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    String lastPathSegment = uri.getLastPathSegment();
                    if (CarControl.PrivateControl.NGP_SAFE_EXAM_RESULT_NOTIFY.equals(lastPathSegment)) {
                        ScuController.this.mNgpSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.NGP_SAFE_EXAM_RESULT, false));
                        LogUtils.i(ScuController.TAG, "onChange: " + lastPathSegment + ", value = " + ScuController.this.mNgpSafeExamResult);
                    } else if (CarControl.PrivateControl.LCC_SAFE_EXAM_RESULT_NOTIFY.equals(lastPathSegment)) {
                        ScuController.this.mLccSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.LCC_SAFE_EXAM_RESULT, false));
                        LogUtils.i(ScuController.TAG, "onChange: " + lastPathSegment + ", value = " + ScuController.this.mLccSafeExamResult);
                    } else if (CarControl.PrivateControl.MEM_PARK_SAFE_EXAM_RESULT_NOTIFY.equals(lastPathSegment)) {
                        ScuController.this.mMemParkSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.MEM_PARK_SAFE_EXAM_RESULT, false));
                        LogUtils.i(ScuController.TAG, "onChange: " + lastPathSegment + ", value = " + ScuController.this.mMemParkSafeExamResult);
                    } else if (CarControl.PrivateControl.SUPER_VPA_SAFE_EXAM_RESULT_NOTIFY.equals(lastPathSegment)) {
                        ScuController.this.mSuperVpaSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SUPER_VPA_SAFE_EXAM_RESULT, false));
                        LogUtils.i(ScuController.TAG, "onChange: " + lastPathSegment + ", value = " + ScuController.this.mSuperVpaSafeExamResult);
                    } else if (CarControl.PrivateControl.APA_SAFE_EXAM_RESULT_NOTIFY.equals(lastPathSegment)) {
                        ScuController.this.mApaSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.APA_SAFE_EXAM_RESULT, false));
                        LogUtils.i(ScuController.TAG, "onChange: " + lastPathSegment + ", value = " + ScuController.this.mApaSafeExamResult);
                    } else if (CarControl.PrivateControl.SUPER_LCC_SAFE_EXAM_RESULT_NOTIFY.equals(lastPathSegment)) {
                        ScuController.this.mSuperLccSafeExamResult = Boolean.valueOf(CarControl.PrivateControl.getBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.SUPER_LCC_SAFE_EXAM_RESULT, false));
                        LogUtils.i(ScuController.TAG, "onChange: " + lastPathSegment + ", value = " + ScuController.this.mSuperLccSafeExamResult);
                    }
                }
            };
        }
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.NGP_SAFE_EXAM_RESULT_NOTIFY), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.LCC_SAFE_EXAM_RESULT_NOTIFY), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.MEM_PARK_SAFE_EXAM_RESULT_NOTIFY), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.APA_SAFE_EXAM_RESULT_NOTIFY), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.SUPER_VPA_SAFE_EXAM_RESULT_NOTIFY), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(CarControl.PrivateControl.getUriFor(CarControl.PrivateControl.SUPER_LCC_SAFE_EXAM_RESULT_NOTIFY), false, this.mContentObserver);
    }

    protected int convertFcwState(int state) {
        if (BaseFeatureOption.getInstance().isNewFcwSignal()) {
            if (state != 0) {
                if (state != 3) {
                    if (state != 7) {
                        if (state != 8) {
                            return state != 9 ? 1 : 10;
                        }
                        return 8;
                    }
                    return 11;
                }
                return 2;
            }
            return 0;
        }
        if (state != 0) {
            if (state == 2 || state == 3 || state == 4) {
                return 2;
            }
            if (state != 5) {
                if (state != 7) {
                    if (state != 8) {
                        return state != 9 ? 1 : 10;
                    }
                    return 8;
                }
                return 11;
            }
        }
        return 0;
    }

    private static int convertDomainRcwState(int[] states) {
        if (states == null || states.length < 2 || states[0] == 2 || states[1] == 2) {
            return 2;
        }
        if (states[0] == 0 || states[1] == 0) {
            return 0;
        }
        if (states[0] == 3 || states[1] == 3) {
            return 10;
        }
        return (states[0] == 4 || states[1] == 4) ? 9 : 1;
    }

    protected int convertDomainRctaState(int[] states) {
        if (states == null || states.length < 2 || states[0] == 2 || states[1] == 2) {
            return 2;
        }
        if (states[0] == 0 || states[1] == 0) {
            return 0;
        }
        if (states[0] == 3 || states[1] == 3) {
            return 10;
        }
        return (states[0] == 4 || states[1] == 4) ? 9 : 1;
    }

    protected int convertDomainBsdState(int[] states) {
        if (states == null || states.length < 2 || states[0] == 2 || states[1] == 2) {
            return 2;
        }
        if (states[0] == 0 || states[1] == 0) {
            return 0;
        }
        if (states[0] == 3 || states[1] == 3) {
            return 10;
        }
        return (states[0] == 4 || states[1] == 4) ? 9 : 1;
    }

    protected int convertDomainDowState(int[] states) {
        if (states == null || states.length < 2 || states[0] == 2 || states[1] == 2) {
            return 2;
        }
        if (states[0] == 0 || states[1] == 0) {
            return 0;
        }
        if (states[0] == 3 || states[1] == 3) {
            return 10;
        }
        return (states[0] == 4 || states[1] == 4) ? 9 : 1;
    }

    private static int convertLccState(int state) {
        if (state == 6 && BaseFeatureOption.getInstance().isSupportGeoFence()) {
            return 6;
        }
        if (state != 0) {
            if (state != 2) {
                if (state != 7) {
                    return (state == 4 || state == 5) ? 2 : 1;
                }
                return 9;
            }
            return 10;
        }
        return 0;
    }

    protected int convertSlaState(int state) {
        if (this.mCarConfig.isSupportSpecialSas()) {
            return state;
        }
        if (state != 0) {
            return (state == 6 || state == 7) ? 2 : 1;
        }
        return 0;
    }

    protected int convertIslaState(int state) {
        if (this.mCarConfig.isSupportSpecialSas()) {
            return state;
        }
        if (state != 0) {
            return (state == 6 || state == 7) ? 2 : 1;
        }
        return 0;
    }

    private static int convertLcaState(int state) {
        if (state == 8 && BaseFeatureOption.getInstance().isSupportGeoFence()) {
            return 6;
        }
        if (state != 6) {
            if (state != 7) {
                int i = 9;
                if (state != 9) {
                    i = 10;
                    if (state != 10) {
                        return 1;
                    }
                }
                return i;
            }
            return 2;
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected CarEcuManager.CarEcuEventCallback getCarEventCallback() {
        return this.mCarScuEventCallback;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.BaseCarController
    protected void buildPropIdList(List<Integer> container, String key) {
        key.hashCode();
        if (key.equals(BusinessConstant.KEY_XPILOT)) {
            container.addAll(buildXpilotIdList());
        }
    }

    private List<Integer> buildXpilotIdList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(557852162);
        return arrayList;
    }
}
